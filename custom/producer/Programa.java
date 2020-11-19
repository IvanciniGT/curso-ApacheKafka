package es.curso;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Programa {
    
    public static void main( String[] args ) throws IOException{
        
        String TOPICO="topicA";
        String CLUSTER="127.0.0.1:9092";
        
        MiProductor productor=new MiProductor( CLUSTER );
        productor.enviarMensaje( TOPICO , "CLAVE1", "MENSAJE 1");
        productor.enviarMensaje( TOPICO , "CLAVE2", "MENSAJE 2");
        productor.enviarMensaje( TOPICO , "CLAVE3", "MENSAJE 3");

    // Esperar a que el usuario pulse ENTER para cerrarme
    System.out.println( "Esperando respuesta... (ENTER para salir)" );
        BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in)); 
        /*String textoIntroducidoPorElUsuario=*/reader.readLine(); // Despues de dar a ENTER
        
    // Le doy a CTRL+C
        productor.cerrarProductor();

    }
    
    
}