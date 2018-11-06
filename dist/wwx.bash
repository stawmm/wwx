# description: springbooot linux shell 
# author: zhongjun
# define app var
APP_NAME=wwx
APP_PORT=8080
JAR_NAME=$APP_NAME\.jar
APP_PROP=$APP_NAME\.properties
APP_PID=$APP_NAME\.pid

# app instructions
usage(){
	echo "Usage: sh ${APP_NAME}.sh [start|stop|restart|status]"
	exit 1
}

# app check status
is_exist(){

	pid=`ps -ef|grep $JAR_NAME|grep -v grep|awk '{print $2}'`
	if [ -z "${pid}" ]; then
		return 1
	else
		return 0
	fi
}

# app start
start(){
	is_exist
	if [ $? -eq "0" ]; then 
		echo ">>> ${JAR_NAME} is already running PID=${pid} <<<" 
	else
		nohup java -Xmx1024M -jar ${JAR_NAME} --server.port=${APP_PORT} --spring.config.location=${APP_PROP} --logging.config=./log4j2.xml >/dev/null 2>&1 &
		echo $! > $APP_PID
		echo ">>> start $JAR_NAME successed PID=$! <<<" 
	fi  
}

# app stop
stop(){

	# is_exist
	pidf=$(cat $APP_PID)
	#echo "$pidf"  
	echo ">>> api PID = $pidf begin kill $pidf <<<"
	
	kill $pidf
	rm -rf $APP_PID
	sleep 2
	
	is_exist
	if [ $? -eq "0" ]; then 
		echo ">>> api 2 PID = $pid begin kill -9 $pid  <<<"
		kill -9  $pid
		sleep 2
		echo ">>> $JAR_NAME process stopped <<<"  
	else
		echo ">>> ${JAR_NAME} is not running <<<"
	fi  
}


# app status 
status(){
	is_exist
	if [ $? -eq "0" ]; then
		echo ">>> ${JAR_NAME} is running PID is ${pid} <<<"
	else
		echo ">>> ${JAR_NAME} is not running <<<"
	fi
}

# app restart
restart(){
	stop
	start
}

# switch
case "$1" in
	"start")
		start
		;;
	"stop")
		stop
		;;
	"status")
		status
		;;
	"restart")
		restart
		;;
	"start")
		start
		;;
	*)
		usage
		;;
esac

exit 0
