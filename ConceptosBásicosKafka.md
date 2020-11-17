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

### Logs
Ficheros que almacenan mensajes o eventos. 
Tenemos muchos ficheros por cada partición, que contendrán mensajes 
almacenados en orden secuencial.

### Producers
Programas que generan mensajes en Kafka.
Cuando un producer manda un mensaje a kafka, le dice en que topic/partición se debe almacenar.

### Consumers
Programas que reciben mensajes de kafka.
Para que un consumer reciba mensajes de un topic de kafka debe de subscribirse.
Un consumer se subscribe a un topic o a un topic/partición.

### Offset
Un identificador que sirve para saber hasta qué mensaje ha sido consumido por un Consumer.

### Consumer Group
Un grupo de consumidores, que todos consumen del mismo topic.
Cada uno de los consumidores de un consumer group procesan distintas particiones.
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

### Borrar un topic
``` sh
kafka$ bin/kafka-topic.sh --delete --topic NOMBRE_TOPIC --bootstrap-server <URL-SERVIDOR-KAFKA>
```
