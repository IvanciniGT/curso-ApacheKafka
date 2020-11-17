# Qué es Kafka?
Gestor de mensajería (y algo más)

## Ejemplo de gestor de mensajería de uso diario: WHATSAPP
### Para que sirve un gestor de mensajería?
Comunicar algo a alguien ASINCRONAMENTE

### Que significa ASINCRONAMENTE?
2 tipos de comunicaciones:
* Sincrona: Requiero que emisor y receptor, ambos estén conectados a la vez para que se produzca la comunicación
* Asincrona: NO requiero que emisor y receptor, ambos estén conectados a la vez para que se produzca la comunicación

Una comunicación asíncrona tenermos 3 involucrados: EMISOR -> SISTEMA DE MENSAJERIA -> RECEPTOR

El SISTEMA DE MENSAJERIA SIEMPRE DEBE ESTAR OPERATIVO <= IMPLICACIONES!

Kafka no esta pensado para ser utilizado por seres humanos.

### Quienes son los emisores y receptores de mensajes en KAFKA?
Programas

## Comunicaciones entre programas:
PROGRAMA A => PROGRAMA B                                (Comunicación síncrona)
PROGRAMA A => SISTEMA DE MENSAJERIA <= PROGRAMA B       (Comunicación Asíncrona)

# Comunicación sincrona o asincrona?
DEPENDE de lo quiera montar.

Voy a pagar la compra en el super.... Y hay un programa dentro del TPV que manda al banco el cargo
* ¿Queremos que esta comunicación sea SINCRONA o ASINCRONA? 
SINCRONA: Necesito respuesta en el momento

Voy a pagar el peaje .... Y hay un programa dentro del TPV que manda al banco el cargo
* ¿Queremos que esta comunicación sea SINCRONA o ASINCRONA? 
ASINCRONA: No requiero respuesta... Mando orden de pago.
    Hay mucho peajes que no admiten tarjetas PREPAGO
    
# Cuando mandais un mensaje a través de WAHTSAPP, a donde lo mandais?
Al servidor... y dentro?
1 Destinatario? o un GRUPO

Kafka existe el concepto de "TOPIC" -> COLA "FIFO" de mensajes?
FIFO: First in -> First out

                                        TOPIC 1
                          -----------------------------------
Productor 1 de mensajes => M1, M2, M3, M4, M5                => Consumidor 1 de mensajes
                          -----------------------------------
                          
    El consumidor 1 de mensajes se subscribe al Topic 1
    El consumidor 2 de mensajes se subscribe al Topic 2                          
                          

                                        TOPIC 2
                          -----------------------------------
Productor 2 de mensajes => M1, M2, M3, M4, M5, M6            => Consumidor 2 (M2) offset
                                                             => Consumidor 3 (M3)
                                                             => Consumidor 4 (DESDE EL PRINCIPIO)
                          -----------------------------------



### El sistema debe estar siempre Operativo

¿Comó lo garantizo? ALTA DISPONIBILIDAD --> Un cluster

Cluster? grupo de "maquinas" (servidores de kafka) trabajando como si fueran 1.

Cuando hagamos una instalación de KAFKA.... Cuantos servidores voy a tener al menos? Al menos 2

                                       TOPIC 1
                          -----------------------------------
Productor 1 de mensajes => M1, M2, M3, M4, M5                => Consumidor 1 de mensajes
                          -----------------------------------
                                            Servidor 1 FENECE, Muere, puff!!!
                                M1..5->       Topic1
                                 VVV        Servidor 2             <= Consumidor 1
                                M1..5->       Topic1
                                 (Necesito replicar los datos)

### El sistema debe poder atender a un número variable de usuarios.... creciente
    ESCALABILIDAD
                                     Topic1
                                       VV            ¿Por que tengo varios consumidores?
                        M1.5       Servidor1         Consumidor1
                        M1.5       Servidor2         Consumidor2
                        M1.5       Servidor3         Consumidor3
                        M1.5       Servidor4         Consumidor4
Ventajas?
* Tengo garantizados los datos
* Cualquier ordenador puede ACCEDER a todos los datos y por tanto iran rápidas las cosas

Inconvenientes?
* Necesitamos MUCHO espacion de almacenamiento
* El almacenamiento de los DATOS SERÁ MAS LENTO

## PARTICIONADO DE LOS TOPICS
El topic 1 tiene dos particiones: Particion A (guarda los mensajes pares) 
                                  Particion B (guarda los mensajes impares) 
                                  
                                           Topic1         Escalabilidad
                                             VV                VV
                M2, M4        T1.A       Servidor1 X   Consumidor 1 (pares)
Productor       M1, M3, M5    T1.B       Servidor2
1,2,3,4,5       M2, M4        T1.A       Servidor3 X   Consumidor 2 (impares)
                M1, M3, M5    T1.B       Servidor4
                
Ventajas?
* Tengo garantizados los datos
* Cualquier ordenador puede ACCEDER a todos los datos y por tanto iran rápidas las cosas

Inconvenientes?
* Necesitamos MUCHO espacion de almacenamiento
* El almacenamiento de los DATOS SERÁ MAS LENTO

Alta disponibilidad / SEGURIDAD
    Medimos en 90% ->    El 90% del tiempo tiene que estar funcionando - 35 dias caido el servicio al año
               99% ->    El 99% del tiempo tiene que estar funcionando - 3,5 dias caido el servicio al año
               99,9% ->  El 99,9% del tiempo tiene que estar funcionando - 7 horas caido el servicio al año              
               99,99% -> El 99,99% del tiempo tiene que estar funcionando - minutos caido el servicio al año
    
    Quiero una disponibilidad del 99,9% del tiempo de servicio        
        CARTA A LOS REYES MAGOS -> Declaración de deseos
    A final de año mido y miro que ha pasado?
    
### ¿Por que tengo varios consumidores?
El mismo mensaje quiero que sea procesado por distintos programas <-
Escalabilidad



Topic1: 2 Particiones (Con 1 replica cada partición)

                                          Topic1         
                M2, M4        T1.A       Broker 1      Grupo de consumidores (a) | Consumidor 1 (A)
Productor       M1, M3, M5    T1.B       Broker 2                                | Consumidor 2 (B)
1,2,3,4,5       M2, M4        T1.A       Broker 3      
                M1, M3, M5    T1.B       Broker 4      Grupo de consumidores (b) | Consumidor 3 (A)
                                                                                 | Consumidor 4 (B)                                                                                 
                                                                                 | Consumidor 5 No recibe nada
                                                                                 
# Ejemplos:
## Caso de uso número 1: SOA

PROVEEDORES                                   CONSUMIDORES
Petición de cargo en cuenta bancaria    ->    A un programa que quite el dinero de la cuenta corriente
                                        ->    A un programa que va a dejar un registro de la operación
                                        ->    A un programa que mande un SMS al titular de la cuenta

Esta no era la forma de desarrollar más antigua.
Antiguamente tendíamos a los sistemas MONOLITICOS (MEGA-APLICACION)
                                        >>>>>     INMANTENIBLE, COMPLEJOS, FUERTEMENTE ACOPLADOS

Hoy en día preferimos las arquitecturas basadas en SERVICIOS: SOA


ActiveMQ
RabbitMQ

## Caso de uso número 2: BIGDATA
ETL: Extracción, transformación y carga de datos
BIGDATA: Manejo de grandes volumenes de información

Guerras 2vs2
1   2
KAFKA
3   4

Cada personas facilmente puede hacer 2 movimientos por segundo
1 segundo: Por una partida se hacen 8 movimientos x 3 = 24 mensajes (en menos de 1 segundo)
100000 partidas simultaneas en el mundo: 1 segundo: 2,4 Millones de mensajes
Ponemos muchos ordenadores a trabajar simultaneamente (CLUSTER)

## Caso de uso número 2: ETL + BIGDATA

### La tienda online de amazón

Cuantos servidores web (de apliciaciones) => Tropecientos-y Tropecientos cincuenta
                    VVVVV
                fichero de log (errores)
                
Servidor 1 LALARAMAZON            COMUNICACIONES ASINCRONAS
    Log  -> FILEBEAT                                                        | ElasticSearch > Control de peticiones
Servidor 2 Amazon                                                           | BD > Analisis
    Log  -> FILEBEAT        Conectores   KAFKA    Conectores                | Identificar errores > MONITORIZACION
Servidor 3 Amazon
    Log  -> FILEBEAT 
Servidor 4 Amazon
    Log  -> FILEBEAT 
...
Servidor tropecientos ciencuenta Amazon
    Log
