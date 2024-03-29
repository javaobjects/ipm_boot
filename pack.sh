#!/bin/bash

svn up

date=`date +"2019%m%d"`
svn=`svn info | grep "Last Changed Rev:" | cut -d " " -f 4`
dir="pack-xpm-$date-$svn-java"
tgz="$dir.tar.gz"
sh="$dir.sh"

rm pack-xpm* -r

mkdir $dir
cp ROOT.war $dir
cp doc/*.sql $dir
tar -zcvf $tgz $dir

echo "#!/bin/bash

source /usr/local/var/chkdef.sh

export LANG=en_US.UTF-8
export JAVA_HOME=/opt/jdk1.8.0_131/
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export LD_LIBRARY_PATH=/usr/lib:/usr/local/lib:/lib

tail -n +36 \$0 > /opt/$tgz
cd /opt
tar -zxvf $tgz
cd $dir
cp *.sql /usr/local/var
crontab -r; 
killall java -9; 
/usr/local/var/chkxpm.sh kill
mysql -uroot -p123456 ipm < ipm_table.sql
mysql -uroot -p123456 ipm < ipm_data.sql
mysql -uroot -p123456 ipm < ip_cn.sql
mysql -uroot -p123456 ipm < ipm_proto_plan.sql
mysql -uroot -p123456 ipm < ipm_public_proto_plan.sql 
mv \$sysinfo /data/; mv \$sysset /data/
rm -r /opt/apache-tomcat-8.0.45/webapps/ROOT*
cp ROOT.war /opt/apache-tomcat-8.0.45/webapps/
crontab /usr/local/var/monitor.cron
while [ ! -f \"\$sysinfo\" -o ! -f \"\$sysset\" ]; do
  echo wait for $sysinfo and $sysset to uncompress ... >> /usr/local/var/monitor.log
  sleep 5
done
mv -f /data/sysset.properties \$sysset
mv -f /data/sysinfo.properties \$sysinfo
chklicense -r \"iPilot-XPM-6.0.$svn\"
exit" > $sh

cat $tgz >> $sh
chmod +x *.sh

#./src/main/java/com/protocolsoft/system/service/impl/SystemSetServiceImpl.java:223:
