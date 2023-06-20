package NapsterSimplificado.Model;

import java.rmi.Remote;

public interface ServicoRequisicoes extends Remote{

    public String join(String nome, String [] arquivos);
    
}
