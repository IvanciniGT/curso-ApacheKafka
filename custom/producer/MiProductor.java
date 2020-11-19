package es.curso;
//import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class MiProductor implements Callback {
    
    private static volatile MiProductor productor=null;
    
    public static MiProductor getInstance(  ){
        synchronized(MiProductor.class){  // Semaforo
            if(productor==null){
                synchronized(MiProductor.class){
                    // Obtener la dirección del cluster
                    String CLUSTER="127.0.0.1:9092";
                    productor=new MiProductor( CLUSTER );
                }
            }
        }
        return productor;
    }
    
    private Producer<String, String> producer; // Generar una variable privada
    
    private MiProductor(String cluster){  // Java Sin nombre: Constructor
        
    // Aqui es donde definimos la configuración del Productor
    System.out.println( "Generando properties..." );
        // EQUIVALENTE A DEFINIR EL FICHERO .PROPERTIES
        Properties configuracion = new Properties();
            // El servidor al que conectarse de Kafka
        configuracion.put(   ProducerConfig.BOOTSTRAP_SERVERS_CONFIG ,      cluster);
            // Número de replicas en las que se debe haber almacenado el mensaje antes de llamar a mi función de callback
        configuracion.put(   ProducerConfig.ACKS_CONFIG,                    "all");
            // Un mensaje son bytes. Hace falta una función para pasar de loq que sea mi menaje 
            // (un entero, un texto, un decimal...) a bytes. Eso es el serializador
            // Un mensaje puede llevar asociado un identificador: KEY
                // Si pongo clave, tendré que decir, como serializarla (Convertirla a bytes)
        configuracion.put(   ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,    "org.apache.kafka.common.serialization.StringSerializer");
            // Un mensaje siempre lleva un valor: VALUE
        configuracion.put(   ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,  "org.apache.kafka.common.serialization.StringSerializer");
        // Aquí acaba la configuración del productor de mensajes
        
    // Creamos y arrancamos el producer.
        // EQUIVALENTE A CUANDO EJECUTABAMOS bin/kafka-console-producer.sh
    System.out.println( "Arrancando producer..." );
        producer = new KafkaProducer<>(configuracion);
              // ^^^^^    ^^^^^
              //  KEY     VALUE    que en nuestro caso concreto son TEXTOS
    
    }
    
    public void enviarMensaje(String topico, String clave, String textoDelMensaje){
        
        // Escribimos mensajes
        System.out.println( "Generando mensaje..." );
            // Me creo una variable donde poner el mensaje
            ProducerRecord<String, String> mensaje;
            // Asigno a la variable un nuvo mensaje que voy  a crear:
            mensaje=new ProducerRecord<String, String>(topico, clave, textoDelMensaje);
        
        // Como cuando le doy a ENTER en la consola
        System.out.println( "Enviando mensaje..." );
            producer.send(mensaje, this);
    
    }
    
    public void cerrarProductor(){
        // Le doy a CTRL+C
            producer.close();
    }
    
    public void onCompletion(   RecordMetadata metadata    ,    Exception exception   ){
        if (exception != null){ 
            // Esto significa que ha petao
            // El mensaje no se ha introducido en kafka
            System.out.println( "El mensaje NO se ha podido guardar en Kafka:" );
            System.out.println( "MENSAJE: " + metadata );             // Muestra información del mensaje que había mandado
            System.out.println( "ERROR: " + exception.getMessage() ); // Muestra el mensaje de error al dar de alta el mensaje enviado
        }else{
            // TODO HA IDO BIEN
            System.out.println( "El mensaje SI se ha podido guardar en Kafka:" );
            System.out.println( "  OFFSET:    " + metadata.offset() );
            System.out.println( "  PARTITION: " + metadata.partition() );
            System.out.println( "  TOPIC:     " + metadata.topic() );
        }
    }
    
}