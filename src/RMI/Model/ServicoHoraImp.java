package RMI.Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicoHoraImp extends UnicastRemoteObject implements ServicoHora{

    public ServicoHoraImp() throws RemoteException {
        super();
    }

    @Override
    public Hora obterHora(String nomeCliente) throws RemoteException {
        
        Hora horaServidor = new Hora(nomeCliente, System.currentTimeMillis());

        return horaServidor;
    }
    
}
