package TCP;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClienteTCP {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 9000);
        byte[] buffer = new byte[4*1024];

        InputStream is = s.getInputStream();

        FileOutputStream os = new FileOutputStream("D:\\Jo\u00E3o\\Facul\\SisDis\\Napster\\src\\TCP\\ArquivoCliente" + "Nature.mp4");

        int nByteslidos;

        while((nByteslidos = is.read(buffer)) != -1) {
            os.write(buffer, 0, nByteslidos);
        }
        /*OutputStream os = s.getOutputStream();
        DataOutputStream writer = new DataOutputStream(os);

        InputStreamReader is = new InputStreamReader(s.getInputStream());
        BufferedReader reader = new BufferedReader(is);
        
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        String texto = inFromUser.readLine();

        writer.writeBytes(texto + "\n");

        String resposta = reader.readLine();
        System.out.println("Do Servidor: " + resposta + "\n");*/

        is.close();
        os.close();

        s.close();
    }
}
