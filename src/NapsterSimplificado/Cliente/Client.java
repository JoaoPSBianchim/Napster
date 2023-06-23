package NapsterSimplificado.Cliente;

import java.io.File;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.Scanner;

import NapsterSimplificado.Model.ServicoRequisicoes;

public class Client {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] meusArquivoStrings = new File("src\\NapsterSimplificado\\ArquivosCliente").list();
        
        var pedido = scanner.next();
        LinkedList<String> donosArquivos = new LinkedList<>();

        Registry reg = LocateRegistry.getRegistry();
        ServicoRequisicoes serv = (ServicoRequisicoes)reg.lookup("rmi://127.0.0.1/Napster");
        
        for(String arquivo:meusArquivoStrings) {
        		System.out.println(arquivo + "\n");
        }

        while (pedido != "t") {       
            if (pedido.toLowerCase().equals("join")) {
                if(serv.join(InetAddress.getLocalHost().getHostAddress().toString(), meusArquivoStrings).equals("JOIN_OK")) {
                    System.out.print("Sou peer " + InetAddress.getLocalHost().getHostAddress() + " com arquivos " );
                    for (String a : meusArquivoStrings) {
                        System.out.print(a + " ");
                    }
                    System.out.println("\n");
                }
            } else if(pedido.toLowerCase().equals("search")) {
                pedido = scanner.next();

                donosArquivos = serv.search(pedido);
            }

            pedido = scanner.next();
        }

        scanner.close();

    }
    
}
