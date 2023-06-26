package NapsterSimplificado.Model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;

public class ServicoRequisicoesImp extends UnicastRemoteObject implements ServicoRequisicoes {

    private static final long serialVersionUID = 1L;
    private HashMap<String, String> ls;

	public ServicoRequisicoesImp() throws RemoteException {
		super(1099);
        ls = new HashMap<String, String>();
    }


    @Override
    public String join(String nome, String[] arquivos, int porta)  throws RemoteException{

        if (arquivos != null) {
            for(int i = 0; i<arquivos.length;i++) {
                addToHash(arquivos[i], nome);
            }
            //this.ls.forEach((k,v) -> System.out.println("key: "+k+" value:"+v));

            System.out.println("Peer [" + nome + "]:[" + porta + "] adicionado com arquivos" + arquivos.toString());
        } else {
            System.out.println("Peer ["+ nome +"]:[" + porta + "] adicionado sem ter arquivos");
        }

        return "JOIN_OK";
    }
    
    
    private void addToHash(String key, String value) {
		this.ls.put(key, value);
	}
    
    private String getFromHash(String key) {
    	return this.ls.get(key);
    }



    @Override
    public LinkedList<String> search(String arquivo, String id, int porta) throws RemoteException {
        LinkedList<String> donosDosArquivos = new LinkedList<>();
        
        for(HashMap.Entry<String, String> entrada: this.ls.entrySet()) {
            if(entrada.getKey().equals(arquivo)) {
                donosDosArquivos.add(entrada.getValue());
            }
        }
        
        System.out.println("Peer [" + id + "]:[" + porta + "] solicitou arquivo []");

        return donosDosArquivos;
    }


    @Override
    public String update(String nome, String arquivos, int porta) throws RemoteException {
        addToHash(arquivos, nome);

        return "UPDATE_OK";
    }
}