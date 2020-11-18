
# CURSO: APACHE KAFKA
Profesor: https://www.linkedin.com/in/ivan-osuna-ayuste/

## PRACTICA 3: Creación de un cluster Kafka

En esta práctica vamos a:
* Montar un cluster Kafka

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

Además, es necesario tener zookeeper en ejecución.

**EL SERVIDOR KAFKA NO DEBE ESTAR EN EJECUCION**

### PASO 1: Configurar el Servidor 1 de Kafka

Duplicaremos el fichero `server.properties` situado en la carpeta `config` del 
directorio de instalación de kafka.

Al nuevo fichero lo llamaremos `server1.properties`.

En dicho fichero, realizaremos los siguientes cambios:
1. Asegurarse que la propiedad broker.id tiene el valor 0:
``` properties
broker.id=1
```
2. Añadir la propiedad listeners con el valor PLAINTEXT://localhost:9091:
``` properties
listeners=PLAINTEXT://localhost:9091
```
3. Crear una carpeta dentro del directorio de 
instalación de kafka, llamada `data`. Dentro de está, otra llamada `server1`.

4. Añadir la propiedad log.dirs con el valor ~/environment/kafka/logs/server1:
``` properties
log.dirs=~/environment/kafka/data/server1
```

### PASO 2: Ejecutar el Servidor 1 de Kafka

Estando zookeeper en ejecución, podemos arrancar el servidor 1 de kafka a través del comando:
``` sh
bin/kafka-server-start.sh config/server1.properties
```
Esto será necesario ejecutarlo en una terminal, posicionados en 
la carpeta de instalación de Kafka.

Esto dejará kafka funcionando en la terminal, que quedará bloqueada.

Para detener la ejecución de kafka, bastará con pulsa `CTRL+C` en la terminal.

### PASO 3: Configurar el Servidor 2 de Kafka
Realizar de nuevo los pasos del PASO 1, pero en esta ocasión con los valores:
``` properties
broker.id=2
listeners=PLAINTEXT://localhost:9092
log.dirs=~/environment/kafka/logs/server2
```
### PASO 4: Ejecutar el Servidor 2 de Kafka
En este caso, se lanzará el mismo comando que en el paso 2, pero con el nuevo 
fichero de configuración:
``` sh
bin/kafka-server-start.sh config/server2.properties
```

### PASO 5: Configurar el Servidor 3 de Kafka
Realizar de nuevo los pasos del PASO 1, pero en esta ocasión con los valores:
``` properties
broker.id=3
listeners=PLAINTEXT://localhost:9093
log.dirs=~/environment/kafka/logs/server3
```

### PASO 6: Ejecutar el Servidor 3 de Kafka
En este caso, se lanzará el mismo comando que en el paso 2, pero con el nuevo 
fichero de configuración:
``` sh
bin/kafka-server-start.sh config/server3.properties
```

### PASO 7: Crear un topic, escribir y leer de él

Tal y como se hizo en la práctica 2, pero en este caso trabajando contra 
cualquier nodo del cluster.

Es interesante probar como se puede escribir en un servidor y leer desde otro.