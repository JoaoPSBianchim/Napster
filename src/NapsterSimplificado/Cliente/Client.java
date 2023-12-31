package NapsterSimplificado.Cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        int porta = Integer.parseInt(args[0]);
        String fonteDosArquivos = "";
        BufferedReader infoInicio = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Informe o caminho da Pasta para a Conexao:");

        System.out.println("Escreva JOIN para a Conexao:");
        String requisicao = infoInicio.readLine();

        System.out.println("Informe a sua pasta Compartilhada");
        fonteDosArquivos = infoInicio.readLine();

       /* String requisicao = scanner.next();
        fonteDosArquivos = scanner.next();*/

        if (requisicao.toLowerCase().equals("join")) {

            // Criação de uma thread para lidar com a comunicação cliente-servidor:
            ClienteThread clienteThread = new ClienteThread(fonteDosArquivos, porta);
            clienteThread.start();


            //Criação da Funcionalidade que Permite que Cada Cliente transfira seus arquivos para quem requisitar
            //No modelo TCP, com o uso de Threads
            try (ServerSocket serverSocket = new ServerSocket(porta)) {
                while (true) {

                    System.out.println("Esperando Conexão");
                    Socket no = serverSocket.accept();
                    System.out.println("Conexão Aceita");

                    ThreadTransmissao thread = new ThreadTransmissao(no, fonteDosArquivos);
                    thread.start();
                }
            }
        }

    }

    private static class ClienteThread extends Thread {
        private String mPasta;
        private int porta;

        ClienteThread(String caminho, int port) {
            this.mPasta = caminho;
            this.porta = port;
        }

        @Override
        public void run() {
            try {
                LinkedList<String[]> donosArquivos = new LinkedList<>();
                String[] meusArquivoStrings;

            
                // Prepara o Registry para a comunicação com o servidor
                Registry reg = LocateRegistry.getRegistry();
                ServicoRequisicoes serv = (ServicoRequisicoes) reg.lookup("rmi://127.0.0.1/Napster");

                meusArquivoStrings = new File(mPasta).list();

                //Confirmação da Requisição Join feita lá na main
                if (serv.join(InetAddress.getLocalHost().getHostAddress(), meusArquivoStrings, porta)
                        .equals("JOIN_OK")) {
                    System.out.print(
                            "Sou peer " + InetAddress.getLocalHost().getHostAddress() + " com arquivos ");
                    for (String a : meusArquivoStrings) {
                        System.out.print(a + " ");
                    }
                    System.out.println("\n");
                }


                Scanner scanner = new Scanner(System.in);
                System.out.println("Qual sera a requisicao?\n");
                var pedido = scanner.next();

                //Para fins de teste, o While acaba ao receber t
                while (!pedido.equals("t")) {

                    //Pedido de Search por um arquivo
                    if (pedido.toLowerCase().equals("search")) {
                        pedido = scanner.next();
                        donosArquivos = serv.search(pedido, InetAddress.getLocalHost().getHostAddress(), porta);
                        
                        for(String[] peer: donosArquivos) {
                            System.out.println("[" + peer[0] + ":" + peer[1] + "]");
                        }
                        

                    //Pedido de Download de Um Arquivo
                    } else if (pedido.toLowerCase().equals("download")) {
                        if (!donosArquivos.isEmpty()) {

                            //Se conecta Com Outro Cliente por TCP
                            //A transferência de arquivos foi feita com base nesse link: https://www.amitph.com/java-read-write-large-files-efficiently/
                            Socket s = new Socket(donosArquivos.getFirst()[0],
                                    Integer.parseInt(donosArquivos.getFirst()[1]));

                            OutputStream os = s.getOutputStream();
                            DataOutputStream writer = new DataOutputStream(os);

                            pedido = scanner.next();
                            writer.writeBytes(pedido + "\n");

                            InputStream is = s.getInputStream();
                            byte[] buffer = new byte[4 * 1024];

                            FileOutputStream download = new FileOutputStream(mPasta + "/" + pedido);
                            int nByteslidos;

                            while ((nByteslidos = is.read(buffer)) != -1) {
                                download.write(buffer, 0, nByteslidos);
                            }
                            System.out.println("Download " + pedido + " baixado com sucesso na pasta " + mPasta);
                            
                            serv.update(InetAddress.getLocalHost().getHostAddress(), pedido, porta);
                            download.close();
                            s.close();


                        //Faz o Update
                        } else if (pedido.toLowerCase().equals("update")) {
                            pedido = scanner.next();
                            if (serv.update(InetAddress.getLocalHost().getHostAddress(), pedido, porta)
                                    .equals("UPDATE_OK")) {
                                System.out.println("Pronto");
                            }
                        }
                    }

                    pedido = scanner.next();
                }

                scanner.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static class ThreadTransmissao extends Thread {
        private Socket no = null;
        private String minhaPasta;

        public ThreadTransmissao(Socket node, String caminho) {
            this.minhaPasta = caminho;
            this.no = node;
        }

        @Override
        public void run() {
            try {
                InputStreamReader is = new InputStreamReader(no.getInputStream());
                BufferedReader reader = new BufferedReader(is);

                String arquivoDesejado = reader.readLine();
                String caminhoFonte = minhaPasta + "/" + arquivoDesejado;

                FileInputStream fis = new FileInputStream(caminhoFonte);
                OutputStream os = no.getOutputStream();

                byte[] buffer = new byte[4 * 1024];
                int nByteslidos;

                while ((nByteslidos = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, nByteslidos);
                }

                System.out.println("Arquivo Enviado!");

                fis.close();
                os.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
