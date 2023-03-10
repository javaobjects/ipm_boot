#!/bin/bash

svn up
svn=`svn info | grep "Last Changed Rev:" | cut -d " " -f 4`
sed -i "s/iPilot-XPM-6.0.[0-9][0-9][0-9][0-9][0-9]/iPilot-XPM-6.0.$svn/" ./src/main/java/com/protocolsoft/system/service/impl/SystemSetServiceImpl.java
cat ./src/main/java/com/protocolsoft/system/service/impl/SystemSetServiceImpl.java | grep XPM
