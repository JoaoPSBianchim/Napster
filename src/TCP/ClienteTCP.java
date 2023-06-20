package TCP;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClienteTCP {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 9000);

        OutputStream os = s.getOutputStream();
        DataOutputStream writer = new DataOutputStream(os);

        InputStreamReader is = new InputStreamReader(s.getInputStream());
        BufferedReader reader = new BufferedReader(is);
        
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        String texto = inFromUser.readLine();

        writer.writeBytes(texto + "\n");

        String resposta = reader.readLine();
        System.out.println("Do Servidor: " + resposta + "\n");

        s.close();
    }
}
