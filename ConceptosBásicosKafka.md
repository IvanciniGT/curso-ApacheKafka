# Arquitectura Kafka

## Componentes, Objetos, ..

### Cluster Kafka
Grupo de servidores que operan en conjunto para:
* Dar un servicio
* Ofrecer Alta disponibilidad / Tolerancia  a fallos en datos
* Escalabilidad

### Zookeeper
Encargado de el funcionamiento del cluster y la coordinación de los servidores de Kafka

### Brokers
Cada uno de los servidores de Kafka. 
Identificador por un broker.id

### Topic
Un conjunto de mensajes.
Los mensajes se dividen en Particiones

### Partitions
Una partición es un conjunto de mensaje pertenecientes a un topic.
Los mensajes de una partición son únicos entre las particiones.
Las particiones ofrecen Escalabilidad en el almacenamiento de los datos.
Los mensajes en las particiones se almacenan en ficheros denominados logs.
Pero, de cada partición, podemos tener Replicas.

### Replicas
Una copia de seguridad de los mensajes de una partición.
Podemos generar tantas replicas como queramos.
Las réplicas ofrecen: Alta disponibilidad

### Mensajes/Eventos
Conjunto de bytes recibidos por Kafka desde un Productor.
Cada mensaje se almacena en una partición de un topic.

Cuando yo hago un programa, lo que manejo no son bytes, son OBJETOS, que referenciamos desde variables.
Por ejemplo, un texto: "ESTE ES MI MENSAJE" ---> BYTES
"Á" ----> UTF-8 --->

1 Byte = 8 bits            TIPO DE DATO
                                                        BYTE       SHORT     INT
00000001 00000000 00000000 00000000            --->    1 0 0 0     256 0    16777216
                                                        STRING (ASCII)
00000001 00000000 00000000 00000000            --->     á    h7     

Mis mensajes van a ser traducidor a Bytes por un Serializador (función)

Un mensaje puede ser: 
* Solo una secuencia de bytes 
* 2 secuencias de bytes:
    - Una se interpreta como una clave/identificador   
    - Una se interpreta como un valor   

### Logs
Ficheros que almacenan mensajes o eventos. 
Tenemos muchos ficheros por cada partición, que contendrán mensajes 
almacenados en orden secuencial.

### Producers
Programas que generan mensajes en Kafka.
Cuando un producer manda un mensaje a kafka, le dice en que topic/partición se debe almacenar.
Ejemplo:
Topic A (partición 0 y 1)
Productor 1   --->  Topic A (Los mensajes llegarán a la partición 0 o 1, dependiendo del algoritmo PARTICIONADOR utilizado)
Productor 2   --->      Server 1
                            Particion 0
                        Server2
                            Particion 1

Si el productor 1 se vuelve loco... y manda miles y miles de mensajes, que ocurre?
Podría saturar al servidor... ¿dónde -normalmente- estaría el cuello de botella en el servidor? en el ALMACENAMIENTO
¿Qué haría en esta situación? Más particiones y más brokers

### Consumers
Programas que reciben mensajes de kafka.
Para que un consumer reciba mensajes de un topic de kafka debe de subscribirse.
Un consumer se subscribe a un topic o a un topic/partición.

### Offset
Un identificador que sirve para saber hasta qué mensaje ha sido consumido por un Consumer.

### Consumer Group
Un grupo de consumidores, que todos consumen del mismo topic.
Cada uno de los consumidores de un consumer group procesan distintas particiones, y todos operan en paralelo.
Cada consumidor dentro de un grupo tiene asignadas algunas de las particionesdel topic, pero en EXLUSIVA dentro del grupo.

Ejemplo:
Topic A (Partición 0, 1 y 2) ---  Consumer Group 1   --- Consumidor A --- 0
                                                     --- Consumidor B --- 1 y 2
                                  Consumer Group 2   --- Consumidor C --- 0, 1 y 2
                                  Consumer Group 3   --- Consumidor D --- 0
                                                     --- Consumidor E --- 2
                                                     --- Consumidor F --- 1
                                                     --- Consumidor G (INACTIVO, no consume)

¿Quién está procesando los mensajes de la partición 0? Consumidor A, C, D
¿Quién está procesando los mensajes de la partición 1? Consumidor B, C, F
¿Quién está procesando los mensajes de la partición 2? Consumidor B, C, E
¿Cada mensaje cúantas veces se consume? 3 veces, una por cada consumer group.
¿Cuántos mensajes leo simultaneamente desde el consumer group 1? 2, ya que ese consumer group tiene 2 consumidores
¿Cuántos mensajes leo simultaneamente desde el consumer group 2? 1, ya que ese consumer group tiene 1 consumidor
¿Cuántos mensajes leo simultaneamente desde el consumer group 3? 3, ya que ese consumer group, aunque tiene 4 consumidores, 
    el topic solo tiene 3 particiones.

Por lo que cada uno de los consumidores de un consumer group procesan distintos mensajes.
Configurar consumer groups me aporta escalabilidad en la lectura de los datos.

# Instalación

## Prerequisitos
Para poder instalar Kafka necesitamos:
* Java instalado. Al menos 1.8.

## Procedimiento de instalación
* Descargar kafka (tgz... como un zip)
* Descomprimirlo

**Nota** Este procedimiento de instalación NO LO VAIS A ENCONTRAR EN NNGUN ENTORNO DE PRODUCCION.
En la realidad, dentro de un entorno de producción, Kafka va a ejecutarse dentro de un Contenedor.
dichos contenedores estarán gestionados por una herramienta como Kubernetes/Openshift.

#####################################################################################
            INSTALACION DE KAFKA EN UN ENTORNO DE PRODUCCION REAL
######################################################################################
Contenedor 1   Volumen 1    - |
    Kafka                     |
X Contenedor 2 Volumen 2    - |     Almacenamiento EXTERNO REDUNDANTE    RAM GIGANTE
    Kafka                     |            fibre-channel 8Gb/seg x 20    x 50 ssd
Contenedor 3   Volumen 3    - |
    Kafka                     |
Contenedor 4   Volumen 2    - |
    Kafka                     |
    
    
    SDD ---> 3000 Mbs
    SDD ---> 500 Mbs
    HDD ---> 150 Mbs
    
KUBERNETES  <-  OPENSHIFT = (Kubernetes+Modulos desarrollados por redhat)
Opensource              Opensource (DE PAGO)
  Google                    Redhat
  
Kubernetes | Openshift : Orquestadores de Contenedores
   Contenedor ~ Máquina virtual


## Instalación en cluster

### Quíen gestiona el cluster

El cluster es gestionado por Zookeeper.

Al arrancar zookeeper POR PRIMERA VEZ genera un cluster con un IDENTIFICADOR ALEATORIO.

Si se cae Zookeeper y volvemos a arrancarlo, qué ocurre?

Zookeeper va a su carpeta de datos (configurada dentro del fichero zookeeper.properties). 
Ahí tendría almacenado el ID de cluster que estaba ejecutando anteriormente. Arrancaría con ese.

### Agragar nodos (broker) de Kafka al cluster de zookeeper 

El broker (nuestro kafka) guarda en su carpeta (que se configuran en el server.properties, con el parámetro: ```logs.dirs```).
información del cluster al que se ha conectado.

Para poder añadir varios brokers a un cluster, que debemos tener cuenta:
* Cada broker va a tener su propio fichero de configuración.
* Los ficheros deben compartir la mayor parte (casi todo) de la configuración, salvo:
    - Cada broker tiene un id que debe serúnico en el cluster
    - Cada broker **puede** trabajar en su propio puerto, pero deben tener URLs de conexión diferentes entre si.
    Esto se configuraba dentro de la propiedad ``listeners``.

## Carpetas dentro del directorio de instalación
### bin
Contenía programas / comandos para gestionar/operar con kafka

### config
Contenía ficheros de configuración de Kafka: server.properties
Contenía ficheros de configuración de Zookeeper: zookeeper.properties
Muchos más ficheros (consumidor de consola, productor de consola,....)

## lib
Contenía librearias que usa Kafka.
Puede ser que en algún caso queramos añadir alguna libreria para ampliar la funcionalidad del cluster

# Gestión del cluster

## Puesta en marcha
### Zookeeper
``` sh
kafka$ bin/zookeeper-server-start.sh config/zookeeper.properties
```
### Kafka
``` sh
kafka$ bin/kafka-server-start.sh config/server.properties
```
## Parada del cluster
Podíamos hacer CTRL+C en la propia patalla donse se estaban ejecutando esos comandos.

### Zookeeper
``` sh
kafka$ bin/zookeeper-server-stop.sh config/zookeeper.properties
```
### Kafka
``` sh
kafka$ bin/kafka-server-stop.sh config/server.properties
```

## Gestión de topics

Para toda la gestión de los topics, el comando es:
``` sh
kafka$ bin/kafka-topic.sh
```

### Listado de los topics
``` sh
kafka$ bin/kafka-topic.sh --list --bootstrap-server <URL-SERVIDOR-KAFKA>
```

### Información de un topic
``` sh
kafka$ bin/kafka-topic.sh --describe --topic NOMBRE_TOPIC --bootstrap-server <URL-SERVIDOR-KAFKA>
```

### Crear un topic
``` sh
kafka$ bin/kafka-topic.sh --create --topic NOMBRE_TOPIC --bootstrap-server <URL-SERVIDOR-KAFKA>
```
### Crear un topic indicándole número de replicas y particiones
``` sh
kafka$ bin/kafka-topic.sh --create --topic NOMBRE_TOPIC --bootstrap-server <URL-SERVIDOR-KAFKA> \
    --replication-factor <NUMERO_REPLICAS> --partitions <NUMERO_PARTICIONES>
```

### Borrar un topic
``` sh
kafka$ bin/kafka-topic.sh --delete --topic NOMBRE_TOPIC --bootstrap-server <URL-SERVIDOR-KAFKA>
```

# Replicación y Particionado

## Particiones. 

Las particiones ofrecen ESCALABILIDAD: ALMACENAMIENTO y de CONSUMICION
En broker puede contener de un mismo topic todas las particiones que quiera.

### Número de particiones

En principio podemos crear las particiones que queramos. De qué depende el número de particiones que queramos crear?
* Del número de consumidores PARALELOS que quería que procesasen los mensajes.
* De la presión en el almacenamiento

Nunca puedo disminuir el número de particiones de un topic. SI AUMENTARLO.

### Reasignación de particiones
La Reasignación de particiones se realiza MANUALMENTE POR EL ADMINSITRADOR DEL CLUSTER DE KAFKA.

## Replicas.

Las replicas ofrecen TOLERANCIA A FALLOS - ALTA DISPONIBILIDAD.

### Número de replicas

Como mucho podemos crear tantas como brokers.
En broker puede contener de un mismo topic sólo una replica de cada partición.

### Sincronización de Replicas

La sincronización de replicas se realiza AUTOMATICAMENTE por kafka.

### Generación del JSON con la definición de la reasignación. 
``` sh
bin/kafka-reassign-partitions.sh \
    --bootstrap-server localhost:9093 \
    --topics-to-move-json-file /home/ubuntu/environment/curso/reasignacion.lista.topicos.json \
    --broker-list 1,3 \
    --generate    
```
### Iniciaba la reasignación... que podía tardar tiempo.
``` sh
bin/kafka-reassign-partitions.sh \
    --bootstrap-server localhost:9093 \
    --reassignment-json-file /home/ubuntu/environment/curso/reasignacion.topic3.json \
    --execute
```

### Comprobar si ya había terminado una reasignación
``` sh
bin/kafka-reassign-partitions.sh \
    --bootstrap-server localhost:9093 \
    --reassignment-json-file /home/ubuntu/environment/curso/reasignacion.topic3.json \
    --verify
```
### Listar las reasignaciones pendientes/ en tramite
``` sh
bin/kafka-reassign-partitions.sh \
    --bootstrap-server localhost:9093 \
    --list
```










PRODUCTOR -> MENSAJE -> KAFKA -> TOPIC A, Particion 1, 4 replicas
                     ^ Tienes libertad para decidir cúando te van a dar por validada la transacción (el envío)
                        En cuanto se haya guardado el mensaje en una replica, quiero el OK
                        En cuanto se haya guardado el mensaje en 2 replicas, quiero el OK
                        Cuando se haya guardad el mensaje en todas las replicas quiero el OK
            √   CALLBACK  <--