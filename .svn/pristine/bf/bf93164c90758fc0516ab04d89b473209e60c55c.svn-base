#!/bin/bash

source /usr/local/var/chkdef.sh

bak=$IFS
IFS=$'\n'

# 替换java
sedstr=";"
for line in `cat ./doc/enlang-java.data`; do
  test -z "$line" && continue
  cn=`echo $line | awk -F "," '{print $1}'`
  en=`echo $line | awk -F "," '{print $2}'`
  sedstr=$sedstr"s/$cn/$en/g;"
done
sed -i "$sedstr" `find ./src/main/java/ -name "*.java"`

# 替换js html
sedstr=";"
for line in `cat ./doc/enlang-js.data`; do
  test -z "$line" && continue
  cn=`echo $line | awk -F "," '{print $1}'`
  en=`echo $line | awk -F "," '{print $2}'`
  sedstr=$sedstr"s/$cn/$en/g;"
done
sed -i "$sedstr" `find ./src/main/webapp/ -name "*.js"`
sed -i "$sedstr" `find ./src/main/webapp/ -name "*.html"`

IFS=$bak

# 替换登录页图片
mv src/main/webapp/images/slogina-en.png src/main/webapp/images/slogina.png
# 替换报表模板
mv src/main/resources/wordmodle/joanna-en.docx src/main/resources/wordmodle/joanna.docx
# 替换报表模板
mv doc/ipm_data_en.sql doc/ipm_data.sql
