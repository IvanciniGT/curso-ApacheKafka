package es.curso;

import org.apache.kafka.clients.consumer.Consumer ;
import org.apache.kafka.clients.consumer.ConsumerConfig ;
import org.apache.kafka.clients.consumer.KafkaConsumer ;
import org.apache.kafka.clients.consumer.ConsumerRecord ;
import org.apache.kafka.clients.consumer.ConsumerRecords ;

import java.util.Properties;
import java.util.Arrays;
import java.time.Duration;

public class MiConsumidor  {
    
    public static void main( String[] args ) throws IOException{
        boolean leer = true;
        
        // Crear un properties
        Properties props = new Properties();
            // Cluster al que conectarnos
        props.setProperty( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            // Grupo de consumidores al que pertenece este consumidor
        props.setProperty( ConsumerConfig.GROUP_ID_CONFIG, "grupo3");
            // Si quiero confirmación de recepción automática
        props.setProperty( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
            // Si después de 1 seg de haber recibido el mensaje, 
            // el consumidor va a confirma AUTOMATICAMENTE la recepcion
//        props.setProperty( ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            // Deserializador de claves   ( bytes >>> claves )
        props.setProperty( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            // Deserializador de mensaje  ( bytes >>> valores )
        props.setProperty( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // Arrancar un consumidor
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribirnos a uno a varios TOPICS
        consumer.subscribe(Arrays.asList("topicA"));

        // Hasta el infinito y más allá !
        while(leer){
            // Que le pida mensajes a kafka ---> Listado de mensaje
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            // Procesar cada mensaje del listado de mensajes
            for (ConsumerRecord<String, String> record : records){
                // Procesaría el mensaje. Lógica de negocio.
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            consumer.commitSync();
        }        

        // Cerrariamos el consumidor
        consumer.close();
    }
    
}