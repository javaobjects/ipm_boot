use ipm;

DROP TABLE IF EXISTS `ipm_proto_plan`;
CREATE TABLE ipm_proto_plan (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `appid` int(11),
  `ip` int(10) unsigned NOT NULL,
  `port` int(11) NOT NULL,
  `proto` int(11) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `display_name` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert  into `ipm_proto_plan`(`id`,`appid`,`ip`,`port`,`proto`,`name`,`display_name`) values (20,NULL,0,18,6,'msp','msp'),(24,NULL,0,20,6,'ftp','ftp'),(26,NULL,0,21,6,'ftp','ftp'),(28,NULL,0,22,6,'ssh','ssh'),(30,NULL,0,23,6,'telnet','telnet'),(32,NULL,0,25,6,'smtp','smtp'),(73,NULL,0,53,17,'dns','dns'),(95,NULL,0,67,17,'dhcp','dhcp'),(97,NULL,0,68,17,'dhcp','dhcp'),(99,NULL,0,69,17,'tftp','tftp'),(116,NULL,0,80,6,'http','http'),(174,NULL,0,109,6,'pop2','pop2'),(176,NULL,0,110,6,'pop3','pop3'),(178,NULL,0,111,6,'rpc','rpc'),(202,NULL,0,123,17,'ntp','ntp'),(230,NULL,0,137,17,'netbios','netbios'),(232,NULL,0,138,17,'netbios','netbios'),(233,NULL,0,139,6,'netbios','netbios'),(278,NULL,0,161,17,'snmp','snmp'),(280,NULL,0,162,17,'snmp','snmp'),(589,NULL,0,389,6,'ldap','ldap'),(590,NULL,0,389,17,'ldap','ldap'),(697,NULL,0,443,6,'https','https'),(841,NULL,0,514,17,'syslog','syslog'),(1072,NULL,0,631,6,'ipp','ipp'),(1082,NULL,0,636,6,'ldaps','ldaps'),(1158,NULL,0,674,6,'acap','acap'),(1159,NULL,0,674,17,'acap','acap'),(1369,NULL,0,992,6,'telnets','telnets'),(2198,NULL,0,1433,6,'sqlserver','sqlserver'),(2200,NULL,0,1434,6,'sqlserver','sqlserver'),(2372,NULL,0,1521,6,'oracle','oracle'),(2378,NULL,0,1524,6,'ingreslock','ingreslock'),(2734,NULL,0,1701,6,'l2tp','l2tp'),(3127,NULL,0,1900,17,'ssdp','ssdp'),(3149,NULL,0,1911,17,'mtp','mtp'),(3296,NULL,0,1985,6,'hsrp','hsrp'),(3297,NULL,0,1985,17,'hsrp','hsrp'),(3298,NULL,0,1986,6,'licensedaemon','licensedaemon'),(3299,NULL,0,1986,17,'licensedaemon','licensedaemon'),(3324,NULL,0,1997,6,'gdpport','gdpport'),(3325,NULL,0,1997,17,'gdpport','gdpport'),(3431,NULL,0,2049,6,'nfs','nfs'),(3432,NULL,0,2049,17,'nfs','nfs'),(5443,NULL,0,3076,6,'xun','迅雷'),(5444,NULL,0,3076,17,'xun','迅雷'),(5445,NULL,0,3077,6,'xun','迅雷'),(5446,NULL,0,3077,17,'xun','迅雷'),(5892,NULL,0,3306,6,'mysql','mysql'),(6040,NULL,0,3389,6,'winrdc','远程桌面'),(6215,NULL,0,3478,17,'stun','stun'),(6638,NULL,0,3690,6,'svn','svn'),(8132,NULL,0,5000,6,'db2','db2'),(9227,NULL,0,7001,6,'weblogic','weblogic'),(9575,NULL,0,8000,17,'qq','qq'),(9584,NULL,0,8008,6,'httpalt','httpalt'),(9632,NULL,0,8081,6,'tproxy','tproxy'),(9915,NULL,0,9080,6,'websphere','websphere'),(11106,NULL,0,0,6,'unknown','未识别协议'),(11107,NULL,0,65536,6,'icmp','icmp'),(11108,NULL,0,4505,6,'saltstack','saltstack'),(11109,NULL,0,4506,6,'saltstack','saltstack'),(11110,NULL,0,6379,6,'redis','redis'),(11111,NULL,0,8140,6,'puppet','puppet'),(11112,NULL,0,27017,6,'mongodb','mongodb');

