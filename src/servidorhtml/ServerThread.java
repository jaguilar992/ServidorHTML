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
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            //
            String request = input.readLine();
            if(request!=null){
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
            }
            this.client.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
      
        
    }
    
     public static String process(String request) throws IOException, FileNotFoundException, NoSuchFileException{
        String result="";        
        String method = Regexp.get_method(request);
        
        if(method!=null) switch(method){
	    case "GET":
		String DIR="html";
		String FILE = Regexp.get_filename(request);
		if(!FILE.equals("/") && !FILE.equals(null)){
		    
                    Path p = Paths.get(DIR+FILE);
		    try{
			BufferedReader reader = Files.newBufferedReader(p);
                        String linea;
                        while ((linea = reader.readLine()) != null) {
                        result=result+linea+"\n";
                        }
		    }catch(NoSuchFileException e){
                        System.out.println("Error 404");
                        Path p1 = Paths.get(DIR+"/404.html");
                        BufferedReader reader1 = Files.newBufferedReader(p1);
                        String linea;
                        while ((linea = reader1.readLine()) != null) {
                        result=result+linea+"\n";
                        }
                    }
                    catch(IOException e){
                        System.out.println("Error 500");
			Path p2 = Paths.get(DIR+"/500.html");
                        BufferedReader reader2 = Files.newBufferedReader(p2);
                        String linea;
                        while ((linea = reader2.readLine()) != null) {
                        result=result+linea+"\n";
                        }
                        
		    }
		    
		}else{
		    System.out.println("Error 500: indice por defecto");
			Path p2 = Paths.get(DIR+"/500.html");
                        BufferedReader reader2 = Files.newBufferedReader(p2);
                        String linea;
                        while ((linea = reader2.readLine()) != null) {
                        result=result+linea+"\n";
                        }
		}
		
		
	    break;default:
                result = "La peticion no se puede resolver.";
                break;
        }
        return result;
    }
    
}
