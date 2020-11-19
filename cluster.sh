clear

(
    cd ~/environment/kafka
    if [[ "$1" == "stop" ]]
    then
        echo Parando servidores
        echo   · Parando servidor 1...
        bin/kafka-server-stop.sh /home/ubuntu/environment/curso/cluster/server1.properties >/dev/null 
        echo   · Parando servidor 2...
        bin/kafka-server-stop.sh /home/ubuntu/environment/curso/cluster/server2.properties >/dev/null 
        echo   · Parando servidor 3...
        bin/kafka-server-stop.sh /home/ubuntu/environment/curso/cluster/server3.properties >/dev/null 
        echo   · Servidores parados
        echo 
        echo Parando Zookeeper
        sleep 5
        bin/zookeeper-server-stop.sh /home/ubuntu/environment/curso/cluster/zookeeper.properties >/dev/null &
        exit 0
    fi
    
    if [[ "$1" == "reset" ]]
    then
        echo Borrando carpetas de datos...
        rm -rf /home/ubuntu/environment/curso/cluster/data/*
    fi
    
    echo Arrancando ZooKeeper...
    bin/zookeeper-server-start.sh /home/ubuntu/environment/curso/cluster/zookeeper.properties >/dev/null &
    sleep 5
    echo   · Zookeeper arrancado

    echo Arrancando Servidores
    bin/kafka-server-start.sh /home/ubuntu/environment/curso/cluster/server1.properties >/dev/null  &
    bin/kafka-server-start.sh /home/ubuntu/environment/curso/cluster/server2.properties >/dev/null  &
    bin/kafka-server-start.sh /home/ubuntu/environment/curso/cluster/server3.properties >/dev/null &
    sleep 5
    echo Servidores arrancados
)