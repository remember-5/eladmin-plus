PID=$(ps -ef | grep eladmin-template.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
echo Application is already stopped
else
# 通过使用-15 而不是-9 来停止线程
echo kill -15 $PID
kill -15 $PID
fi
