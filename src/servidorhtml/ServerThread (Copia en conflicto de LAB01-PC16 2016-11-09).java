/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorhtml;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;

/**
 *
 * @author usuario
 */
public class ServerThread extends Thread{
    private Socket client;
    //private int id;
    
    public ServerThread(Socket client){
        this.client = client;
        //this.id = n;
    }
    
    @Override
    public void run(){
        try {
            System.out.println("Cliente Conectado");
            //Para leer lo que envie el cliente
            BufferedReader input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            //para imprimir datos de salida                
            PrintStream output = new PrintStream(this.client.getOutputStream());
            //se lee peticion del cliente
            String request = input.readLine();
            System.out.println("Cliente> " + request );
            //se procesa la peticion y se espera resultado
            String strOutput = process(request);
            //Se imprime en consola "servidor"
//            System.out.println("Servidor> Resultado de petición");
//            System.out.println("Servidor> \"" + strOutput + "\"");
            //se imprime en cliente
            output.flush();//vacia contenido
            output.println(strOutput);
            //cierra conexion
            this.client.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
     public static String process(String request) throws IOException, FileNotFoundException{
        String result="";        
        String method = Regexp.get_method(request);
        
        if(method!=null) switch(method){
	    case "GET":
		String DIR="html";
		String FILE = Regexp.get_filename(request);
		if(!FILE.equals("/")){
		    
                    Path p = Paths.get(DIR+FILE);
		    try{
			BufferedReader reader = Files.newBufferedReader(p);
		    }catch(FileNotFoundException e){
                        System.out.println("No se encontro el archivo");
                    }
                    catch(IOException e){
			System.out.println("No se pudo abrir el archivo ");
		    }
		    result="Hola";
		}else{
		    result="500";
		}
		
		
	    break;default:
                result = "La peticion no se puede resolver.";
                break;
        }
        return result;
    }
    
}
