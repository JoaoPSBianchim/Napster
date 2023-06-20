package NapsterSimplificado.Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicoRequisicoesImp extends UnicastRemoteObject implements ServicoRequisicoes {

    public ServicoRequisicoesImp() throws RemoteException {
        super(1099);
    }

 

    @Override
    public String join(String nome, String[] arquivos) {
        return "JOIN_OK";
    }
    
}
