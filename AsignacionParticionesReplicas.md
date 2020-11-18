Topic: Practica2
       Particiones: 1

               - Server1
  CLUSTER      - Server2  <<<--- Crea el topic Practica2
(ZOOKEEPER)    - Server3

Yo he pedido al cluster que cree el topic practica2 ----> server3
^^^ SIMPLIFICACION

vvv DETALLE
Yo he pedido al cluster que cree el topic practica2 (con 1 particion)
Particion 0 - Topic practica2  ----> server3 CTRL+C PUF!!!!!
    Anotar en un fichero los mensajes recibidos a la particion 0 del topic practica2


Yo he pedido al cluster que cree el topic practica2 (con 2 particion)
Particion 0 - Topic practica2  ----> server3
    Anotar en un fichero los mensajes recibidos a la particion 0 del topic practica2
Particion 1 - Topic practica2  ----> CUALQUIER SITIO, INCLUIDO EL SERVER 3 
    Anotar en un fichero los mensajes recibidos a la particion 0 del topic practica2


Cada particion tiene un LOG<- Que está compuesto de muchos ficheros
    Cada uno de los ficheros se denomina LOG SEGMENT
    
SIEMPRE UNA PARTICION TIENE AL MENOS CUANTAS REPLICAS ??? Al menos 1 (LA PRINCIPAL)
Que significa esto? Solo están guardados los datos 1 vez, si hay 1 replica


vvv MAS DETALLE
Yo he pedido al cluster que cree el topic practica2 (con 1 particion con 2 replicas)
Particion 0. Replica 0 - Topic practica2  ----> server3 <<<--- LIDER PUFFF
Particion 0. Replica 1 - Topic practica2  ----> server2 <<<---          ---> LIDER
    Anotar en un fichero los mensajes recibidos a la particion 0 del topic practica2


vvv MAS DETALLE
Yo he pedido al cluster que cree el topic practica2 (con 1 particion con 4 replicas)
SOLO TENGO 3 SERVIDORES
Particion 0. Replica 0 - Topic practica2  ----> server1 ---> PUF !!!!
Particion 0. Replica 1 - Topic practica2  ----> server2
Particion 0. Replica 2 - Topic practica2  ----> server3
Particion 0. Replica 3 - Topic practica2  ----> SIN ASIGNAR.... pte
    Anotar en un fichero los mensajes recibidos a la particion 0 del topic practica2

Topic: practica2        PartitionCount: 1       ReplicationFactor: 4    Configs: segment.bytes=1073741824
        Topic: practica2        Partition: 0    Leader: 1     Replicas: 1,2,3     Isr: 1,2,3

SITUACION POST PUFF
Topic: practica2        PartitionCount: 1       ReplicationFactor: 4    Configs: segment.bytes=1073741824
        Topic: practica2        Partition: 0    Leader: 2     Replicas: 1,2,3     Isr: 2,3
        
        
ZOOKEEPER        -----  
        Server1 (Broker1) Programa JAVA    config2 RAM <---fichero de props1
        Server2 (Broker2) Programa JAVA    config2 RAM <---fichero de props2
        Server3 (Broker3) Programa JAVA    config2 RAM <---fichero de props3