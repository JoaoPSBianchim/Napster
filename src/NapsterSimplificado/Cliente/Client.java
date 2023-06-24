package NapsterSimplificado.Cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.Scanner;

import NapsterSimplificado.Model.ServicoRequisicoes;

public class Client {

    public static void main(String[] args) throws Exception {

        // Criação de uma thread para lidar com a comunicação cliente-servidor:
        ClienteThread clienteThread = new ClienteThread();
        clienteThread.start();

        try (ServerSocket serverSocket = new ServerSocket(9000)) {
            while (true) {

                System.out.println("Esperando Conexão");
                Socket no = serverSocket.accept();
                System.out.println("Conexão Aceita");

                ThreadTransmissao thread = new ThreadTransmissao(no);
                thread.start();
            }
        }

    }

    private static class ClienteThread extends Thread {

        @Override
        public void run() {
            try {

                LinkedList<String> donosArquivos = new LinkedList<>();
                String[] meusArquivoStrings;

                Scanner scanner = new Scanner(System.in);
                var pedido = scanner.next();

                // Prepara o Registry para a comunicação com o servidor
                Registry reg = LocateRegistry.getRegistry();
                ServicoRequisicoes serv = (ServicoRequisicoes) reg.lookup("rmi://127.0.0.1/Napster");

                while (pedido != "t") {
                    if (pedido.toLowerCase().equals("join")) {
                        meusArquivoStrings = new File(scanner.next()).list();
                        if (serv.join(InetAddress.getLocalHost().getHostAddress().toString(), meusArquivoStrings)
                                .equals("JOIN_OK")) {
                            System.out.print(
                                    "Sou peer " + InetAddress.getLocalHost().getHostAddress() + " com arquivos ");
                            for (String a : meusArquivoStrings) {
                                System.out.print(a + " ");
                            }
                            System.out.println("\n");
                        }
                    } else if (pedido.toLowerCase().equals("search")) {
                        pedido = scanner.next();

                        donosArquivos = serv.search(pedido);
                        System.out.println(donosArquivos.toString());
                    }

                    pedido = scanner.next();
                }

                scanner.close();

            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }

    private static class ThreadTransmissao extends Thread {
        private Socket no = null;

        public ThreadTransmissao(Socket node) {
            this.no = node;
        }

        @Override
        public void run() {
            try {
            InputStreamReader is =  new InputStreamReader(no.getInputStream());
            BufferedReader reader = new BufferedReader(is);

            OutputStream os = no.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);

            String texto = reader.readLine();

            writer.writeBytes(texto.toUpperCase() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }
}
