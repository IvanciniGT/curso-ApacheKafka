# CURSO: APACHE KAFKA
Profesor: https://www.linkedin.com/in/ivan-osuna-ayuste/

## PRACTICA 2: Creación y uso de un TOPIC

En esta práctica vamos a:
1. Crear un topic
2. Mandar contenido al topic
3. Leer contenido del topic

Durante esta práctica, se trabajará en una terminal, dentro de la carpeta
de instalación de kafka. En nuestro caso será:
**/home/ubuntu/environment/kafka**.
Para acceder a la misma se puede ejecutar el comando:
``` sh
cd /home/ubuntu/environment/kafka
```
Para comprobar que rfectivamente nos encontramos dentro de esa carpeta podemos 
ejecutar el comando:
``` sh
pwd
```
o mirar el prompt situado a la izquierda del cursor, donde se visualizará algo como:
``` sh
<USUARIO>:/home/ubuntu/environment/kafka $
```
o
``` sh
<USUARIO>:~/environment/kafka $
```

Además, es necesario tener zookeeper y el servidor kafka en ejecución.

### PASO 1: Crear un topic en Kafka

Para crear un topic en Kafka, ejecutaremos el comando `bin/kafka-topics.sh`.

Todas las herramientas de línea de comandos de Kafka tienen opciones adicionales.
Por ejemplo, si se ejecuta el comando `bin/kafka-topics.sh` sin ninguna opción adicional
se mostará un listado de las opciones permitidas:
``` sh
bin/kafka-topics.sh
```
Para crear un topic, utilizaremos la opcion `--create` , a la  
que suministraremos el nombre del topic y el servidor al que conectarse.
``` sh
bin/kafka-topics.sh --create --topic practica2 --bootstrap-server localhost:9092
```

Para mostrar detalles del topic, podemos ejecutar la opción `--describe`:
``` sh
bin/kafka-topics.sh --describe --topic practica2 --bootstrap-server localhost:9092
```
Que producirá la siguiente salida:
    Topic:practica2     PartitionCount:1    ReplicationFactor:1     Configs:
        Topic: practica2    Partition: 0    Leader: 0   Replicas: 0     Isr: 0

### PASO 2: Escribir en el topic

Un cliente de Kafka se comunica con los ejecutores de Kafka a través de la red 
para escribir (o leer) eventos. 

Una vez recibidos, los ejecutores almacenarán los eventos de una manera duradera 
y tolerante a fallos durante el tiempo que se necesite, incluso para siempre.

Dentro de Kafka, y a modo de ejemplo, viene un productor de mensajes que se 
ejecuta desde el terminal. Este productor permite escribir eventos en un topic. 
De forma predeterminada, cada línea que se escriba en la terminal generará un 
evento separado que se escribirá en el tema.

Para crear eventos en el topic, podemos ejecutar el comando `bin/kafka-console-producer.sh`,
al que indicaremos el topic y el servidor al que conectarse:
``` sh
bin/kafka-console-producer.sh --topic practica2 --bootstrap-server localhost:9092
```
Y al ejecutar el comando, podemos comenzar a mandar mensajes.

Para detener el cliente productor de mensajes, basta con pulsar `CTRL-C` en cualquier momento.

Las lineas escritas en el terminal se habrán guardado como eventos independientes en el topic.

### PASO 3: Leer eventos del topic

Dentro de Kafka, y a modo de ejemplo, también viene un consumidor de mensajes que se 
ejecuta desde el terminal. Este consumidor permite leer eventos de un topic. 

En otro terminal, ejecutamos el comando del consimidor de consola `bin/kafka-console-consumer.sh`,
para leer los eventos que contiene el topic hasta el momento:
``` sh
bin/kafka-console-consumer.sh --topic practica2 --from-beginning --bootstrap-server localhost:9092
```

Para detener el cliente consumidor de mensajes, basta con pulsar `CTRL-C` en cualquier momento.

Como los eventos se almacenan de forma duradera en Kafka, pueden ser leídos tantas 
veces y por tantos consumidores como sea necesario.

Para comprobarlo, basta con abrir el cliente de consola de kafka desde otro terminal con el mismo comando.

### PASO 3: Eliminar todo

Para eliminar cualquier dato de la máquina, incluidos los eventos que se hayan
creado bastará con eliminar el contenido de las carpetas /tmp/kafka-logs y /tmp/zookeeper.

Para ello ejecutaremos el comando:
``` sh
$ rm -rf /tmp/kafka-logs /tmp/zookeeper
```
