package NapsterSimplificado.Model;

import java.io.Serializable;
import java.util.Hashtable;

public class ListaArquivos implements Serializable {
    
    private Hashtable<String, String> hashtable =  new Hashtable<>();

    public ListaArquivos() {

    }

    public ListaArquivos(String nomeArquivo, String nomeCliente) {
        this.hashtable.put(nomeArquivo, nomeCliente);
    }

    //https://www.geeksforgeeks.org/hashtable-in-java/

}
