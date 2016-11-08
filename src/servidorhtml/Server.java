package servidorhtml;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * @see http://www.jc-mouse.net/
 * @author mouse
 */
public class Server {

    /**
     * Puerto 
     */
    private final static int PORT = 8000;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            //Socket de servidor para esperar peticiones de la red
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor> Servidor iniciado");    
            System.out.println("Servidor> En espera de cliente...");    
            //Socket de cliente
            Socket clientSocket;
            while(true){
                //en espera de conexion, si existe la acepta
                clientSocket = serverSocket.accept();
                ServerThread hilo = new ServerThread(clientSocket);
                hilo.start();
            }    
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * procesa peticion del cliente y retorna resultado
     * @param request peticion del cliente
     * @return String
     */
   
}