package es.curso;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import java.util.Map;

public class MiParticionador implements Partitioner{
    
    public int partition(String topic, Object key, byte[] keyBytes,
                     Object value, byte[] valueBytes, Cluster cluster){
        return 1; 
    }
    
    public void close(){}

    public void configure(Map<String,?> configs){}
    
    //public void onNewBatch(String topic, Cluster cluster, int prevPartition){}
}