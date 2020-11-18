package es.curso;
//import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MiProductor {
    
    public static void main( String[] args ){
        
        String TOPICO="topicA";
        
        System.out.println( "Mensaje por consola" );

    // Aqui es donde definimos la configuración del Productor
        // EQUIVALENTE A DEFINIR EL FICHERO .PROPERTIES
        Properties configuracion = new Properties();
            // El servidor al que conectarse de Kafka
        configuracion.put(   ProducerConfig.BOOTSTRAP_SERVERS_CONFIG ,      "localhost:9092");
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
        Producer<String, String> producer = new KafkaProducer<>(configuracion);
              // ^^^^^    ^^^^^
              //  KEY     VALUE    que en nuestro caso concreto son TEXTOS
        
    // Escribimos mensajes
        // Me creo una variable donde poner el mensaje
        ProducerRecord<String, String> mensaje;
        // Asigno a la variable un nuvo mensaje que voy  a crear:
        mensaje=new ProducerRecord<String, String>(TOPICO, "CLAVE1", "Mensaje 1");
    
    // Como cuando le doy a ENTER en la consola
        producer.send(mensaje);

    // Le doy a CTRL+C
        producer.close();

    }
    
}