# CURSO: APACHE KAFKA
Profesor: https://www.linkedin.com/in/ivan-osuna-ayuste/

## PRACTICA 4: Creación de un topic tolerante a fallos y escalable

En esta práctica vamos a:
* Crear un topic tolerante a llafos y escalable

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

Además, es necesario tener un cluster de kafka en ejecución.


### PASO 1: Crear un topic

En este caso también trabajaremos con el comando `bin/kafka-topics.sh`para la 
creación del topic.

Suministraremos algunos parámetros adicionales durante su ejecución:
* `--partitions 5`: Número de particiones en que se dividirá el topic.
* `--replication-factor 3`: Número de réplicas de cada partición.

De tal forma que el comando queda:
``` sh
bin/kafka-topics.sh --create --topic practica4 --bootstrap-server \
localhost:9092 --partitions 5 --replication-factor 3
```
A continuación se solicitarán los detalles del topic, para ver si la configuración
se ha aplicado correctamente, tal y como se hizo en la práctica 2.

Por último, se crearán y leeran unos mensajes/eventos en el topic, para comprobar 
su correcto funcionamiento.