package RMI.Model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoHora extends Remote {
    
    public Hora obterHora(String nomeCliente) throws RemoteException;
}
