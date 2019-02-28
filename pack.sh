#!/bin/bash

svn up

date=`date +"2018%m%d"`
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

export LANG=en_US.UTF-8
export JAVA_HOME=/opt/jdk1.8.0_131/
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export LD_LIBRARY_PATH=/usr/lib:/usr/local/lib:/lib

tail -n +24 \$0 > /opt/$tgz
cd /opt
tar -zxvf $tgz
cd $dir
cp *.sql /usr/local/var
mysql -uroot -p123456 ipm < ipm_table.sql
mysql -uroot -p123456 ipm < ipm_data.sql
mysql -uroot -p123456 ipm < ip_cn.sql
mysql -uroot -p123456 ipm < ipm_proto_plan.sql
mysql -uroot -p123456 ipm < ipm_public_proto_plan.sql 
killall java -9; killall ipmcoll; killall ipmipc
rm -r /opt/apache-tomcat-8.0.45/webapps/ROOT*
cp ROOT.war /opt/apache-tomcat-8.0.45/webapps/
/opt/apache-tomcat-8.0.45/bin/startup.sh
exit" > $sh

cat $tgz >> $sh
chmod +x *.sh
