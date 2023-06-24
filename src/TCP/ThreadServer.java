package TCP;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class ThreadServer extends Thread {
    
    private Socket no = null;

    public ThreadServer(Socket valor){
        no = valor;
    }

    @Override
    public void run() {
        try {
            String source = "src/TCP/ArquivoServ/Nature.mp4";
            byte[] buffer =  new byte[4*1024];

            FileInputStream is = new FileInputStream(source);
            OutputStream os = no.getOutputStream();

            int nByteslidos;

            while((nByteslidos = is.read(buffer)) != -1) {
                os.write(buffer, 0, nByteslidos);
            }

            System.out.println("Arquivo Enviado!");

            is.close();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
