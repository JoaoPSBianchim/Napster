package RMI.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import RMI.Model.ServicoHoraImp;

public class Servidor {
    
    public static void main(String[] args) throws Exception {

        ServicoHoraImp sh = new ServicoHoraImp();    
    
        LocateRegistry.createRegistry(1099);

        Registry reg = LocateRegistry.getRegistry();

        reg.bind("rmi://127.0.0.1/servicohora", sh);
        System.out.println("Servidor no Ar\n");
    }
}
