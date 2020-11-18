echo TOPIC1 - 1 particion 1 replica   --- Comprometidos en el sentido que no tenemos Tolerancia a fallos
bin/kafka-topics.sh \
    --create \
    --if-not-exists \
    --topic topic1 \
    --bootstrap-server localhost:9093 \
    --replication-factor 1 \
    --partitions 1

echo TOPIC2 - 2 particion 1 replica   --- Comprometidos en el sentido que no tenemos Tolerancia a fallos
bin/kafka-topics.sh \
    --create \
    --if-not-exists \
    --topic topic2 \
    --bootstrap-server localhost:9093 \
    --replication-factor 1 \
    --partitions 2

echo TOPIC3 - 1 particion 2 replica
bin/kafka-topics.sh \
    --create \
    --if-not-exists \
    --topic topic3 \
    --bootstrap-server localhost:9093 \
    --replication-factor 2 \
    --partitions 1
    
echo TOPIC4 - 1 particion 4 replica   --- Comprometidos en el sentido que no tenemos tantas máquinas
bin/kafka-topics.sh \
    --create \
    --if-not-exists \
    --topic topic4 \
    --bootstrap-server localhost:9093 \
    --replication-factor 4 \
    --partitions 1
    
echo TOPIC5 - 4 particion 1 replica   --- Comprometidos en el sentido que no tenemos Tolerancia a fallos
 bin/kafka-topics.sh \
    --create \
    --if-not-exists \
    --topic topic5 \
    --bootstrap-server localhost:9093 \
    --replication-factor 1 \
    --partitions 4
    
echo TOPIC6 - 40 particion 3 replica  --- ESTO FUNCIONA GUAY
bin/kafka-topics.sh \
    --create \
    --if-not-exists \
    --topic topic6 \
    --bootstrap-server localhost:9093 \
    --replication-factor 3 \
    --partitions 40
## Tiene sentido hacer 40 particiones???
#   Si voy a tener 40 consumidores paralelos 
#   Si voy a tener 40 servidores donde almacenar independientes
# Escalabilidad: Almacenamiento | Consumición
# Donde está lo pesado??? Depende de cada caso

echo Listado de todos los topics
bin/kafka-topics.sh \
    --list \
    --bootstrap-server localhost:9093

echo Destalle de los topics

for topico in $(bin/kafka-topics.sh --list --bootstrap-server localhost:9093)
do
    bin/kafka-topics.sh \
        --describe \
        --topic $topico \
        --bootstrap-server localhost:9093
done