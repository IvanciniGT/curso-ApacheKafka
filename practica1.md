# CURSO: APACHE KAFKA
Profesor: https://www.linkedin.com/in/ivan-osuna-ayuste/

## PRACTICA 1: Descargar e Instalar Kafka

En esta práctica vamos a:
* Descargar Apache Kafka
* Instalar Apache Kafka
* Iniciar y parar Apache Kafka.

Durante esta práctica, se trabajará en una terminal, dentro de la carpeta:
**/home/ubuntu/environment**.
Para acceder a la misma se puede ejecutar el comando:
``` sh
cd /home/ubuntu/environment
```
Para comprobar que rfectivamente nos encontramos dentro de esa carpeta podemos 
ejecutar el comando:
``` sh
pwd
```
o mirar el prompt situado a la izquierda del cursor, donde se visualizará algo como:
``` sh
<USUARIO>:/home/ubuntu/environment $
```
o
``` sh
<USUARIO>:~/environment $
```

### PASO 1: Descargar Kafka
Descargar la última versión de Kafka. Se puede mirar el enlace desde la página 
oficial de Apache Kafka, dentro del apartado descargas: 
[Descargas de Kafka](https://kafka.apache.org/downloads).

Una vez obtenido el enlace, desde una terminal de comandos, ejecutar el 
siguiente comando para su descarga:
`wget <URL-de-descarga>`.

Por ejemplo, para la descarga de la versión 2.13-2.6.0, podríamos ejecutar:
``` sh
wget https://apache.brunneis.com/kafka/2.6.0/kafka_2.13-2.6.0.tgz
```

Para comprobar que la descarga se realizó satisfactoriamente ejecutaremos:
``` sh
ls -l kafka*
```
### PASO 2: Descomprimir Kafka
El fichero de descarga está comprimido con formato tgz. Para descomprimirlo 
ejecutar el comando `tar -xzf <Nombre-del-archivo>.tgz`.

Por ejemplo, para el fichero descargado anteriormente ejecutaríamos:
``` sh
tar -xzf /home/ubuntu/environment/kafka_2.13-2.6.0.tgz
```
Esto genera una carpeta con el nombre del fichero comprimido, que además de la 
palabra Kafka, incluye también la versión.

En los entornos Linux, es muy habitual mantener las carpetas con el nombre de
la versión del programa, si bien, para facilitar la utilización del mismo, se
suele crear un enlace simbólíco (acceso directo), con un nombre simplificado.
Esto permitiría cambiar la versión de kafka sin tener que modificar las variables
de entorno, u otras rutas configuradas en ficheros de script.

Para generar un enlace simbólico ejecutaremos `ln -s kafka <carpeta_con_version_en_el_nombre>`

Por ejemplo, en el caso anterior haríamos:
``` sh
ln -s /home/ubuntu/environment/kafka /home/ubuntu/environment/kafka_2.13-2.6.0 
```

Para acceder a la carpeta de kafka y ver sus archivos ejecutaríamos:
``` sh
cd /home/ubuntu/environment/kafka
ls -l
```

### PASO 3: Carpetas y archivos de kafka
Dentro de la carpeta de Kafka econtramos los siguientes directorios:
* bin: Contiene los scripts de ejecución de los distintos comandos de Kafka.
* config: Contiene los archivos de configuración de los distintos módulos de Kafka.
* libs: Son librerías que usa Kafka internamente.
* logs: Carpeta en la que se irán creando archivos de log con información de
los comandos que se vayan ejecutando.
* site-docs: Documentación de Kafka.

Además existen dos archivos: LICENSE y NOTICE, con información acerca de las licencias 
de Kafka y de las librerías que utiliza.

Por ahora, nos centrasemos en las carpetas: bin y config, para realizar todas las 
básicas de Kafka.


### PASO 4: Instalación de JAVA +1.8
Para poder ejecutar Kafka, es necesario disponer de Java +1.8 instalado y 
configurado en la máquina.

Para comprobar si tenemos java instalado y configurado en el ordenador, podemos
ejecutar desde el terminal:
``` sh
java -version
```
En caso de no tener instalado JAVA +1.8, Kafka no arrancará.

### PASO 5: Arranque de zookeeper

Actualmente, Apache Kafka utiliza zookeeper para la gestión del cluster de servicios.
Lo primero que es necesario para poder ejecutar Kafka es que zookeeper esté 
arrancado y funcionando.

Para ello hay que ejecutar el siguiente comando dentro de la carpeta donde esté instalado kafka:
``` sh
bin/zookeeper-server-start.sh config/zookeeper.properties
```
Esto dejará zookeeper funcionando en la terminal, que quedará bloqueada.

Para detener la ejecución de zookeeper, bastará con pulsa `CTRL+C` en la terminal.


### PASO 6: Arrancar Kafka

Una vez ejecutándose zookeeper, podemos arrancar kafka a través del comando:
``` sh
bin/kafka-server-start.sh config/server.properties
```
Esto será necesario ejecutarlo desde otra terminal, también posicionados en 
la carpeta de instalación de Kafka.

Esto dejará kafka funcionando en la terminal, que quedará bloqueada.

Para detener la ejecución de kafka, bastará con pulsa `CTRL+C` en la terminal.
