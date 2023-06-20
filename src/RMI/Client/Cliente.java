package RMI.Client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import RMI.Model.Hora;
import RMI.Model.ServicoHora;

public class Cliente {
    public static void main(String[] args) throws Exception{
        Registry reg = LocateRegistry.getRegistry();
        ServicoHora shc = (ServicoHora)reg.lookup("rmi://127.0.0.1/servicohora");
        Hora h = shc.obterHora("Cli1");

        System.out.println(h.nomeCliente + "  " + h.timestamp);

    }
}
