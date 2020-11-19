package es.curso;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Programa {
    
    public static void main( String[] args ) throws IOException{
        
        String TOPICO="topicA";
        
        MiProductor.getInstance().enviarMensaje( TOPICO , "1", "MENSAJE 1");
        MiProductor.getInstance().enviarMensaje( TOPICO , "2", "MENSAJE 2");
        MiProductor.getInstance().enviarMensaje( TOPICO , "3", "MENSAJE 3");

    // Esperar a que el usuario pulse ENTER para cerrarme
    System.out.println( "Esperando respuesta... (ENTER para salir)" );
        BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in)); 
        /*String textoIntroducidoPorElUsuario=*/reader.readLine(); // Despues de dar a ENTER
        
    // Le doy a CTRL+C
        MiProductor.getInstance().cerrarProductor();

    }
    
    
}