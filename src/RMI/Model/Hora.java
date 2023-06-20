package RMI.Model;

import java.io.Serializable;

public class Hora implements Serializable{
    
    public String nomeCliente;
    public long timestamp;

    public Hora(String nome, long timestmp) {
        this.nomeCliente = nome;
        this.timestamp = timestmp;
    }

}
