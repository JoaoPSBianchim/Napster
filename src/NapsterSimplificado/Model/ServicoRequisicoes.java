package NapsterSimplificado.Model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface ServicoRequisicoes extends Remote{

    public String join(String nome, String[] arquivos) throws RemoteException;

    public LinkedList<String> search(String arquivo) throws RemoteException;
    
    public String update(String nome, String arquivos) throws RemoteException;

}
