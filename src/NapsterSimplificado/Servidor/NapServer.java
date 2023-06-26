package NapsterSimplificado.Servidor;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import NapsterSimplificado.Model.ServicoRequisicoesImp;

public class NapServer {
    public static void main(String[] args) throws Exception{
        int porta = 1099;
 
        if(args.length > 0) {
            porta = Integer.parseInt(args[0]);
        }
    
        ServicoRequisicoesImp sr = new ServicoRequisicoesImp();
 
        LocateRegistry.createRegistry(porta);

        Registry reg = LocateRegistry.getRegistry();

        reg.bind("rmi://127.0.0.1/Napster", sr);
        
        System.out.println("Servidor no Ar");
    }
}
