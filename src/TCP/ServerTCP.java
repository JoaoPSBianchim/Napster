package TCP;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9000);

        while (true) {

            System.out.println("Esperando Conexão");
            Socket no = serverSocket.accept();
            System.out.println("Conexão Aceita");

            ThreadServer thread = new ThreadServer(no);
            thread.start();
        }

    }
}
