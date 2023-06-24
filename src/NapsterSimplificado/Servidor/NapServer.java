package NapsterSimplificado.Servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import NapsterSimplificado.Model.ServicoRequisicoesImp;

public class NapServer {
    public static void main(String[] args) throws Exception{
            	
        ServicoRequisicoesImp sr = new ServicoRequisicoesImp();
 
        LocateRegistry.createRegistry(1099);

        Registry reg = LocateRegistry.getRegistry();

        reg.bind("rmi://127.0.0.1/Napster", sr);
        
        System.out.println("Servidor no Ar");
    }
}
