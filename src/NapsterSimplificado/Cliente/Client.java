package NapsterSimplificado.Cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import NapsterSimplificado.Model.ListaArquivos;
import NapsterSimplificado.Model.ServicoRequisicoes;

public class Client {
    public static void main(String[] args) throws Exception {
        Registry reg = LocateRegistry.getRegistry();
        ServicoRequisicoes shc = (ServicoRequisicoes)reg.lookup("rmi://127.0.0.1/servicoNapster");
        
        String [] meusArquivos = {"java.mp3", "oi.oi"};
        
        ListaArquivos ls = shc.join("cliente1", meusArquivos);

        //System.out.println(h.nomeCliente + "  " + h.timestamp);
    }
}
