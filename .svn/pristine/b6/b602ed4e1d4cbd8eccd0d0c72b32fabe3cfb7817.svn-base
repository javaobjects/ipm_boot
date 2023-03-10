-- --------------------
-- 授权模块数据 START (根据授权文件内容添加，测试先全部) 
-- --------------------
INSERT INTO `ipm_authorize_module` VALUES (1, 'USABILITY', 'Application usability', true, true, 15);
INSERT INTO `ipm_authorize_module` VALUES (2, 'SYSTEM', 'System resource consumption', true, true, 16);
INSERT INTO `ipm_authorize_module` VALUES (3, 'CENTER', 'User management', true, false, 17);
INSERT INTO `ipm_authorize_module` VALUES (4, 'HTTP', 'HTTP service', true, false, 7);
INSERT INTO `ipm_authorize_module` VALUES (5, 'ORACLE', 'ORACLE service', true, false, 8);
INSERT INTO `ipm_authorize_module` VALUES (6, 'MYSQL', 'MYSQL service', true, false, 9);
INSERT INTO `ipm_authorize_module` VALUES (7, 'SQLSERVER', 'SQLSERVER service', true, false, 10);
INSERT INTO `ipm_authorize_module` VALUES (8, 'URL', 'URL service', true, false, 11);
INSERT INTO `ipm_authorize_module` VALUES (9, 'MESSAGE', 'Message service', true, false, 12);
INSERT INTO `ipm_authorize_module` VALUES (10, 'WATCHPOINT', 'Watchpoint', true, true, 1);
INSERT INTO `ipm_authorize_module` VALUES (11, 'CLIENT', 'Client', true, false, 3);
INSERT INTO `ipm_authorize_module` VALUES (12, 'SERVER', 'Server', true, false, 2);
INSERT INTO `ipm_authorize_module` VALUES (13, 'NETWORK', 'Network', true, true, 13);

INSERT INTO `ipm_authorize_module` VALUES (14, 'MANYWATCHPOINT', 'Multiwatchpoint', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (15, 'FLOWSTORAGE', 'Flowstorage', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (16, 'MAP', 'Map', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (17, 'TOPO', 'Topology', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (18, 'TRAFFICPAIR', 'CommunicationPair', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (19, 'DIGGER', 'iDigger', false, false, 0);
-- --------------------
-- 授权模块数据 END (根据授权文件内容添加，测试先全部)
-- --------------------


-- --------------------
-- service器接入 START 
-- --------------------
INSERT INTO `ipm_center_ip` VALUES (1, 'Local', '127.0.0.1', 80, 'Default Settings', 0);
-- --------------------
-- service器接入 END 
-- --------------------


-- --------------------
-- 用户信息 START 
-- --------------------
INSERT INTO `ipm_center_user` VALUES (1, 1, '', '', '', unix_timestamp(now()), unix_timestamp(now()), 1, 4);
-- --------------------
-- 用户信息 END 
-- --------------------


-- --------------------
-- 权限模块数据 START 
-- --------------------
INSERT INTO `ipm_juris_module` VALUES (101, 'businessManager', 'Monitoring object Settings', 1);
INSERT INTO `ipm_juris_module` VALUES (102, 'topology', 'Topology', 2);
INSERT INTO `ipm_juris_module` VALUES (103, 'flowStore', 'Flowstore', 3);
INSERT INTO `ipm_juris_module` VALUES (104, 'alarmSet', 'Alarm statistics', 4);
INSERT INTO `ipm_juris_module` VALUES (105, 'reportManager', 'Report management', 5);
INSERT INTO `ipm_juris_module` VALUES (106, 'systemSet', 'SystemSettings', 6);
INSERT INTO `ipm_juris_module` VALUES (107, 'view', 'Cockpit', 7);
INSERT INTO `ipm_juris_module` VALUES (13, 'network', 'Network', 8);
INSERT INTO `ipm_juris_module` VALUES (10, 'watchpoint', 'Watchpoint', 9);
INSERT INTO `ipm_juris_module` VALUES (12, 'server', 'Server', 10);
INSERT INTO `ipm_juris_module` VALUES (11, 'client', 'Client', 11);
INSERT INTO `ipm_juris_module` VALUES (4, 'http', 'HTTP Service', 12);
INSERT INTO `ipm_juris_module` VALUES (5, 'oracle', 'ORACLE Service', 13);
INSERT INTO `ipm_juris_module` VALUES (6, 'mysql', 'MYSQL Service', 14);
INSERT INTO `ipm_juris_module` VALUES (7, 'sqlserver', 'SQLSERVER Service', 15);
INSERT INTO `ipm_juris_module` VALUES (8, 'url', 'URL Service', 16);
INSERT INTO `ipm_juris_module` VALUES (9, 'message', 'Message Service', 17);
-- --------------------
-- 权限模块数据 END 
-- --------------------


-- --------------------
-- 系统用户数据 START 
-- --------------------
INSERT INTO `ipm_system_user` VALUES (1, 1, 'admin', '09ebcce7c80dd0e1bd1abe7a72c450df', 'info', 'info@protocolsoft.com', '13141402442', unix_timestamp(now()), false, false, false);
-- --------------------
-- 系统用户数据 END 
-- --------------------


-- --------------------
-- 系统用户配置数据 START 
-- --------------------
INSERT INTO `ipm_user_configure` VALUES (1, 1, 'name', '1');
INSERT INTO `ipm_user_configure` VALUES (2, 1, 'openIpLocationFlag', '0');
INSERT INTO `ipm_user_configure` VALUES (3, 1, 'dataRefreshTime', '30');
INSERT INTO `ipm_user_configure` VALUES (4, 1, 'cockpitConfig', '');
INSERT INTO `ipm_user_configure` VALUES (5, 1, 'netcockpit', '0');
INSERT INTO `ipm_user_configure` VALUES (6, 1, 'cockpitmanna', '0');
INSERT INTO `ipm_user_configure` VALUES (7, 1, 'formplan', '0');
-- --------------------
-- 系统用户配置数据 END 
-- --------------------


-- --------------------
-- 驾驶舱数据 START 
-- --------------------
INSERT INTO `ipm_monitor_view` VALUES (1, 'Network', 'Network', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (2, 'Watchpoint', 'Watchpoint', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (3, 'Client', 'Client', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (4, 'Server', 'Server', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (5, 'HTTPService', 'HTTPservice', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (6, 'ORACLEservice', 'ORACLEservice', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (7, 'MYSQLservice', 'MYSQLservice', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (8, 'SQLSERVERservice', 'SQLSERVERservice', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (9, 'URLservice', 'URLservice', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (10, 'Messageservice', 'Messageservice', 1, UNIX_TIMESTAMP(NOW()), 0);
-- 无用数据,解决AUTO_INCREMENT问题
INSERT INTO `ipm_monitor_view` VALUES (100, '', '', 1, UNIX_TIMESTAMP(NOW()), 0);
-- --------------------
-- 驾驶舱数据 END 
-- --------------------


-- --------------------
-- 系统角色数据 START 
-- --------------------
INSERT INTO `ipm_system_role` VALUES (1, 'System administrator', 'System administrator account,Has all functional permissions！');
INSERT INTO `ipm_system_role` VALUES (2, 'General user', 'General user,is required to grant the specified function permissions by the system administrator！');
-- --------------------
-- 系统角色数据 END 
-- --------------------

-- --------------------
-- 列表类型数据 START 
-- --------------------
INSERT INTO `ipm_list_type` VALUES (4, 'HTTPService status list', 4);
INSERT INTO `ipm_list_type` VALUES (5, 'OracleService status list', 5);
INSERT INTO `ipm_list_type` VALUES (6, 'MySqlService status list', 6);
INSERT INTO `ipm_list_type` VALUES (7, 'SQLServerService status list', 7);
INSERT INTO `ipm_list_type` VALUES (8, 'URLService status list', 8);
INSERT INTO `ipm_list_type` VALUES (9, 'Message status list', 9);
INSERT INTO `ipm_list_type` VALUES (10, 'Overview of key KPI to wathcpoint', 10);
INSERT INTO `ipm_list_type` VALUES (11, 'Overview of key KPI to client', 11);
INSERT INTO `ipm_list_type` VALUES (12, 'Overview of key KPI to server', 12);
INSERT INTO `ipm_list_type` VALUES (13, 'CommunicationPair', 0);
INSERT INTO `ipm_list_type` VALUES (14, 'HTTP SessionList', 4);
INSERT INTO `ipm_list_type` VALUES (15, 'Oracle SessionList', 5);
INSERT INTO `ipm_list_type` VALUES (16, 'MySql SessionList', 6);
INSERT INTO `ipm_list_type` VALUES (17, 'SQLServer SessionList', 7);
INSERT INTO `ipm_list_type` VALUES (18, 'URL PerformanceList', 8);
INSERT INTO `ipm_list_type` VALUES (19, 'Message SessionList', 9);
-- --------------------
-- 列表类型数据 END 
-- --------------------


-- --------------------
-- 列表字段数据 START 
-- --------------------
INSERT INTO `ipm_list_column` VALUES (1001, 10, 'name', 'Tenant/Watchpoint', '', 1);
INSERT INTO `ipm_list_column` VALUES (1002, 10, 'load_5', 'Equipment load', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1003, 10, 'ip', 'TenantIP', '', 0);
INSERT INTO `ipm_list_column` VALUES (1004, 10, 'contacts', 'Contact', '', 0);
INSERT INTO `ipm_list_column` VALUES (1005, 10, 'telephone', 'Telephone', '', 0);
INSERT INTO `ipm_list_column` VALUES (1006, 10, 'email', 'Email', '', 0);
INSERT INTO `ipm_list_column` VALUES (1007, 10, 'validterm', 'Authorization expiration time', '', 0);
INSERT INTO `ipm_list_column` VALUES (1008, 10, 'macAddr', 'MACaddress', '', 0);
INSERT INTO `ipm_list_column` VALUES (1009, 10, 'productNo', 'System version number', '', 0);
INSERT INTO `ipm_list_column` VALUES (1010, 10, 'syncTime', 'Final synchronization time', '', 0);
INSERT INTO `ipm_list_column` VALUES (1011, 10, 'alarmLevel', 'Number of no response alarms', '', 1);
INSERT INTO `ipm_list_column` VALUES (1012, 10, 'qos', 'Access Quality', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1013, 10, 'serverDurTime', 'Network delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1014, 10, 'clientDurTime', 'Network delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1015, 10, 'rtt', 'Round trip time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1016, 10, 'serverConTime', 'Server handshake delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1017, 10, 'clientConTime', 'Client handshake delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1018, 10, 'responseTime', 'Application processing delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1019, 10, 'loadtranstime', 'Load transfer delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1020, 10, 'serverRetransTime', 'Retransmission delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1021, 10, 'clientRetransTime', 'Retransmission delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1022, 10, 'ethernetTraffic', 'Network traffic', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1023, 10, 'tcpTraffic', 'TCP traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1024, 10, 'udpTraffic', 'UDP traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1025, 10, 'inTraffic', 'Downstream traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1026, 10, 'outTraffic', 'Upstream traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1027, 10, 'unKnowSerTraffic', 'Undefined sever flow', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1028, 10, 'unKnowCliTraffic', 'Undefined client traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1029, 10, 'sessionNum', 'Max Session Number', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1030, 10, 'synPkts', 'Attempt connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1031, 10, 'rstPkts', 'TCP connection reset', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1032, 10, 'synAckPkts', 'Response connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1033, 10, 'finPkts', 'Closed connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1034, 10, 'fin1Pkts', 'Active close connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1035, 10, 'conRespRatio', 'Connection unresponse Ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1036, 10, 'fin2Pkts', 'Passive close connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1037, 10, 'ethernetPkts', 'Data packet rate', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1038, 10, 'netPktLostRatio', 'Packet loss rate', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1039, 10, 'serverPktLostRatio', 'Packet loss rate at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1040, 10, 'clientPktLostRatio', 'Packet loss rate at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1041, 10, 'tinyPkts', 'Small packet rate', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1042, 10, 'tinyPktsRatio', 'Small packet ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1043, 10, 'avgPktsLen', 'Averager packet length', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1044, 10, 'zeroWinCount', 'Number of Zero window packet', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1045, 10, 'upBandWidthRatio', 'Upstream bandwidth utilization', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1046, 10, 'downBandWidthRatio', 'Downstream bandwidth utilization', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1047, 10, 'arpTraffic', 'ARP traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1048, 10, 'arpPkts', 'ARPpacket rate', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (1102, 11, 'name', 'Client name', '', 1);
INSERT INTO `ipm_list_column` VALUES (1103, 11, 'alarmLevel', 'Unresponsed alarm', '', 1);
INSERT INTO `ipm_list_column` VALUES (1104, 11, 'serverDurTime', 'Network delay at server', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1105, 11, 'clientDurTime', 'Network delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1106, 11, 'rtt', 'Round trip time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1107, 11, 'serverConTime', 'Server handshake delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1108, 11, 'clientConTime', 'Client handshake delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1109, 11, 'responseTime', 'Application processing delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1110, 11, 'serverRetransTime', 'Retransmission delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1111, 11, 'clientRetransTime', 'Retransmission delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1112, 11, 'ethernetTraffic', 'Networkflow', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1113, 11, 'tcpTraffic', 'TCP traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1114, 11, 'udpTraffic', 'UDP traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1115, 11, 'inTraffic', 'Downstream traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1116, 11, 'outTraffic', 'Upstream traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1117, 11, 'unKnowSerTraffic', 'Undefined server traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1118, 11, 'sessionNum', 'Max Session Number', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1119, 11, 'synPkts', 'Attempt connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1120, 11, 'rstPkts', 'TCP connection reset', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1121, 11, 'synAckPkts', 'Response connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1122, 11, 'finPkts', 'Closed connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1123, 11, 'fin1Pkts', 'Active close connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1124, 11, 'fin2Pkts', 'Passive close connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1125, 11, 'conRespRatio', 'Connection unresponse Ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1126, 11, 'ethernetPkts', 'Packet rate', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1127, 11, 'netPktLostRatio', 'Packet loss rate', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1128, 11, 'serverPktLostRatio', 'Packet loss rate at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1129, 11, 'clientPktLostRatio', 'Packet loss rate at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1130, 11, 'tinyPkts', 'Small packet rate', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1131, 11, 'tinyPktsRatio', 'Small packet ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1132, 11, 'avgPktsLen', 'Averager packet length', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1133, 11, 'zeroWinCount', 'Zero window packet', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1134, 11, 'bandWidthRatio', 'Bandwidth utilization', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (1202, 12, 'name', 'Server name', '', 1);
INSERT INTO `ipm_list_column` VALUES (1203, 12, 'alarmLevel', 'Unresponsed alarm', '', 1);
INSERT INTO `ipm_list_column` VALUES (1204, 12, 'qos', 'Access Quality', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1205, 12, 'serverDurTime', 'Network delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1206, 12, 'clientDurTime', 'Network delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1207, 12, 'rtt', 'Round trip time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1208, 12, 'serverConTime', 'Server handshake delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1209, 12, 'clientConTime', 'Client handshake delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1210, 12, 'responseTime', 'Application processing delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1211, 12, 'loadtranstime', 'Load transfer delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1212, 12, 'serverRetransTime', 'Retransmission delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1213, 12, 'clientRetransTime', 'Retransmission delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1214, 12, 'ethernetTraffic', 'Network traffic', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1215, 12, 'tcpTraffic', 'TCP traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1216, 12, 'udpTraffic', 'UDP traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1217, 12, 'inTraffic', 'Downstream traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1218, 12, 'outTraffic', 'Upstream traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1219, 12, 'unKnowCliTraffic', 'Undefined client traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1220, 12, 'sessionNum', 'Max Session Number', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1221, 12, 'synPkts', 'Attempt connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1222, 12, 'rstPkts', 'TCP connection reset', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1223, 12, 'synAckPkts', 'Response connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1224, 12, 'finPkts', 'Closed connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1225, 12, 'fin1Pkts', 'Active close connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1226, 12, 'fin2Pkts', 'Passive close connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1227, 12, 'conRespRatio', 'Connection unresponse Ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1228, 12, 'ethernetPkts', 'Packet rate', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1229, 12, 'netPktLostRatio', 'Packet loss rate', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1230, 12, 'serverPktLostRatio', 'Packet loss rate at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1231, 12, 'clientPktLostRatio', 'Packet loss rate at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1232, 12, 'tinyPkts', 'Small packet rate', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1233, 12, 'tinyPktsRatio', 'Small packet ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1234, 12, 'avgPktsLen', 'Averager packet length', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1235, 12, 'zeroWinCount', 'Zero window packet', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1236, 12, 'bandWidthRatio', 'Bandwidth utilization', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (1301, 13, 'startEndstr', 'Starting time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1302, 13, 'ipmCenterName', 'XPMserver name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1303, 13, 'watchpointName', 'Watchpoint name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1304, 13, 'protocol', 'Protocol', '', 0);
INSERT INTO `ipm_list_column` VALUES (1305, 13, 'serverip', 'ServerIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1306, 13, 'serverport', 'Server port', '', 0);
INSERT INTO `ipm_list_column` VALUES (1307, 13, 'serverName', 'Server name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1308, 13, 'clientip', 'ClientIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1309, 13, 'clientName', 'Client name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1310, 13, 'ethernetTraffic', 'Network traffic', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1311, 13, 'synPkts', 'Attempt connection', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1312, 13, 'qos', 'Access Quality', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1313, 13, 'serverDurTime', 'Network delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1314, 13, 'clientDurTime', 'Network delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1315, 13, 'rtt', 'Round trip time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1316, 13, 'serverConTime', 'Server handshake delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1317, 13, 'clientConTime', 'Client handshake delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1318, 13, 'responseTime', 'Application processing delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1319, 13, 'loadtranstime', 'Load transfer delay', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1320, 13, 'serverRetransTime', 'Retransmission delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1321, 13, 'clientRetransTime', 'Retransmission delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1322, 13, 'tcpTraffic', 'TCP traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1323, 13, 'udpTraffic', 'UDP traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1324, 13, 'inTraffic', 'Downstream traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1325, 13, 'outTraffic', 'Upstream traffic', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1326, 13, 'rstPkts', 'TCP connection reset', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1327, 13, 'synAckPkts', 'Response connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1328, 13, 'finPkts', 'Closed connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1329, 13, 'fin1Pkts', 'Active close connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1330, 13, 'fin2Pkts', 'Passive close connection', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1331, 13, 'conRespRatio', 'Connection unresponse Ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1332, 13, 'ethernetPkts', 'Number of packets', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1333, 13, 'netPktLostRatio', 'Packet loss rate', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1334, 13, 'serverPktLostRatio', 'Packet loss rate at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1335, 13, 'clientPktLostRatio', 'Packet loss rate at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1336, 13, 'tinyPkts', 'Number of  tiny packets', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1337, 13, 'tinyPktsRatio', 'Small packet ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1338, 13, 'avgPktsLen', 'Averager packet length', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1339, 13, 'zeroWinCount', 'Zero window packet', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1340, 13, 'communicationProtocol', 'Communication Protocol', '', 0);
INSERT INTO `ipm_list_column` VALUES (1341, 13, 'countryCn', 'Country', '', 0);
INSERT INTO `ipm_list_column` VALUES (1342, 13, 'regionCn', 'Province', '', 0);
INSERT INTO `ipm_list_column` VALUES (1343, 13, 'cityCn', 'City', '', 0);
INSERT INTO `ipm_list_column` VALUES (1344, 13, 'ispCn', 'Internet Service Provider', '', 0);

INSERT INTO `ipm_list_column` VALUES (402, 4, 'name', 'Service name', '', 1);
INSERT INTO `ipm_list_column` VALUES (403, 4, 'alarmLevel', 'Unresponsed alarm', '', 1);
INSERT INTO `ipm_list_column` VALUES (404, 4, 'serverDurTime', 'Network delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (405, 4, 'clientDurTime', 'Network delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (406, 4, 'rtt', 'Round trip time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (407, 4, 'httpResponseTime', 'HTTP processing time', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (408, 4, 'pageloadTime', 'URL payload transfer delay', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (409, 4, 'ethernetTraffic', 'HTTP service traffic', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (410, 4, 'l7SessionCountTotal', 'HTTP session', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (412, 4, 'failRespRatio', 'Ratio of HTTP error return code', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (414, 4, 'noRespRatio', 'Ratio of HTTP upnresponse', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (415, 4, 'netPktLostRatio', 'Packet loss rate', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (502, 5, 'name', 'Service name', '', 1);
INSERT INTO `ipm_list_column` VALUES (503, 5, 'alarmLevel', 'Unresponsed alarm', '', 1);
INSERT INTO `ipm_list_column` VALUES (504, 5, 'serverDurTime', 'Network delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (505, 5, 'clientDurTime', 'Network delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (506, 5, 'rtt', 'Round trip time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (507, 5, 'httpResponseTime', 'SQL processing time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (508, 5, 'ethernetTraffic', 'Oracle service traffic', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (509, 5, 'l7SessionCountTotal', 'SQL session', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (510, 5, 'http400Count', 'SQL return code', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (511, 5, 'failRespRatio', 'Ratio of SQL error return code', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (512, 5, 'noRespCount', 'SQL unresponse', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (513, 5, 'noRespRatio', 'Ratio of SQL unresponse', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (514, 5, 'netPktLostRatio', 'Packet loss rate', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (602, 6, 'name', 'Service name', '', 1);
INSERT INTO `ipm_list_column` VALUES (603, 6, 'alarmLevel', 'Unresponsed alarm', '', 1);
INSERT INTO `ipm_list_column` VALUES (604, 6, 'serverDurTime', 'Network delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (605, 6, 'clientDurTime', 'Network delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (606, 6, 'rtt', 'Round trip time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (607, 6, 'httpResponseTime', 'SQL processing time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (608, 6, 'ethernetTraffic', 'MySqlserviceFlow', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (609, 6, 'l7SessionCountTotal', 'SQL session', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (610, 6, 'http400Count', 'SQL return code', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (611, 6, 'failRespRatio', 'SQL error return code ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (612, 6, 'noRespCount', 'SQL unresponse', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (613, 6, 'noRespRatio', 'Ratio of SQL unresponse', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (614, 6, 'netPktLostRatio', 'Packet loss rate', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (702, 7, 'name', 'Service name', '', 1);
INSERT INTO `ipm_list_column` VALUES (703, 7, 'alarmLevel', 'Unresponsed alarm', '', 1);
INSERT INTO `ipm_list_column` VALUES (704, 7, 'serverDurTime', 'Network delay at server', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (705, 7, 'clientDurTime', 'Network delay at client', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (706, 7, 'rtt', 'Round trip time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (707, 7, 'httpResponseTime', 'SQL processing time', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (708, 7, 'ethernetTraffic', 'SQLServer service flow', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (709, 7, 'l7SessionCountTotal', 'SQL session', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (710, 7, 'http400Count', 'SQL return code', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (711, 7, 'failRespRatio', 'SQL error return code ratio', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (712, 7, 'noRespCount', 'Number of SQL noresponse', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (713, 7, 'noRespRatio', 'Ratio of SQL unresponse', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (714, 7, 'netPktLostRatio', 'Packet loss rate', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (1401, 14, 'begintime', 'Begin time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1402, 14, 'endtimewithpayload', 'End time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1403, 14, 'ipmCenterName', 'XPMserver name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1404, 14, 'name', 'Watchpoint name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1405, 14, 'serverip', 'ServerIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1406, 14, 'port', 'Server port', '', 0);
INSERT INTO `ipm_list_column` VALUES (1407, 14, 'client', 'ClientIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1408, 14, 'clientport', 'Client port', '', 0);
INSERT INTO `ipm_list_column` VALUES (1409, 14, 'url', 'URL', '', 1);
INSERT INTO `ipm_list_column` VALUES (1410, 14, 'bytes', 'Bytes', '', 1);
INSERT INTO `ipm_list_column` VALUES (1411, 14, 'protocol', 'Protocol', '', 0);
INSERT INTO `ipm_list_column` VALUES (1412, 14, 'method', 'CommandType', '', 0);
INSERT INTO `ipm_list_column` VALUES (1413, 14, 'httpreturncode', 'HTTP return code', '', 1);
INSERT INTO `ipm_list_column` VALUES (1414, 14, 'contenttype', 'Load type', '', 0);
INSERT INTO `ipm_list_column` VALUES (1415, 14, 'pageload', 'URL payload transfer delay', '', 1);
INSERT INTO `ipm_list_column` VALUES (1416, 14, 'reponsetime', 'Response delay', '', 1);
INSERT INTO `ipm_list_column` VALUES (1417, 14, 'clientlatency', 'Network delay at client', '', 1);
INSERT INTO `ipm_list_column` VALUES (1418, 14, 'serverlatency', 'Network delay at server', '', 1);
INSERT INTO `ipm_list_column` VALUES (1419, 14, 'rtt', 'Network communication delay RTT', '', 1);
INSERT INTO `ipm_list_column` VALUES (1420, 14, 'forward', 'The source client', '', 0);
INSERT INTO `ipm_list_column` VALUES (1421, 14, 'vlanId', 'VLAN ID', '', 0);
INSERT INTO `ipm_list_column` VALUES (1422, 14, 'req', 'Request message', '', 0);
INSERT INTO `ipm_list_column` VALUES (1423, 14, 'res', 'Response message', '', 0);

INSERT INTO `ipm_list_column` VALUES (1501, 15, 'begintime', 'Begin time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1502, 15, 'endtime', 'End time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1503, 15, 'ipmCenterName', 'XPMserver name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1504, 15, 'name', 'Watchpoint name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1505, 15, 'server', 'ServerIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1506, 15, 'port', 'Server port', '', 0);
INSERT INTO `ipm_list_column` VALUES (1507, 15, 'client', 'ClientIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1508, 15, 'clientport', 'Client port', '', 0);
INSERT INTO `ipm_list_column` VALUES (1509, 15, 'dbname', 'Database name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1510, 15, 'user', 'User name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1511, 15, 'sqlquery', 'SQLstatements', '', 1);
INSERT INTO `ipm_list_column` VALUES (1512, 15, 'responsecode', 'Return code', '', 1);
INSERT INTO `ipm_list_column` VALUES (1513, 15, 'responsemsg', 'Return message', '', 0);
INSERT INTO `ipm_list_column` VALUES (1514, 15, 'bytes', 'Bytes', '', 0);
INSERT INTO `ipm_list_column` VALUES (1515, 15, 'queryduration', 'SQL processing time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1516, 15, 'clientlatency', 'Network delay at client', '', 1);
INSERT INTO `ipm_list_column` VALUES (1517, 15, 'serverlatency', 'Network delay at server', '', 1);
INSERT INTO `ipm_list_column` VALUES (1518, 15, 'rtt', 'Network communication delay RTT', '', 1);

INSERT INTO `ipm_list_column` VALUES (1601, 16, 'begintime', 'Begin time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1602, 16, 'endtime', 'End time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1603, 16, 'ipmCenterName', 'XPMserver name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1604, 16, 'name', 'Watchpoint name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1605, 16, 'server', 'ServerIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1606, 16, 'port', 'Server port', '', 0);
INSERT INTO `ipm_list_column` VALUES (1607, 16, 'client', 'ClientIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1608, 16, 'clientport', 'Client port', '', 0);
INSERT INTO `ipm_list_column` VALUES (1609, 16, 'dbname', 'Database name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1610, 16, 'user', 'User name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1611, 16, 'sqlquery', 'SQLstatements', '', 1);
INSERT INTO `ipm_list_column` VALUES (1612, 16, 'responsecode', 'Return code', '', 1);
INSERT INTO `ipm_list_column` VALUES (1613, 16, 'responsemsg', 'Return message', '', 0);
INSERT INTO `ipm_list_column` VALUES (1614, 16, 'bytes', 'Bytes', '', 0);
INSERT INTO `ipm_list_column` VALUES (1615, 16, 'queryduration', 'SQL processing time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1616, 16, 'clientlatency', 'Network delay at client', '', 1);
INSERT INTO `ipm_list_column` VALUES (1617, 16, 'serverlatency', 'Network delay at server', '', 1);
INSERT INTO `ipm_list_column` VALUES (1618, 16, 'rtt', 'Network communication delay RTT', '', 1);

INSERT INTO `ipm_list_column` VALUES (1701, 17, 'begintime', 'Begin time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1702, 17, 'endtime', 'End time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1703, 17, 'ipmCenterName', 'XPMserver name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1704, 17, 'name', 'Watchpoint name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1705, 17, 'server', 'ServerIP', '', 1); 
INSERT INTO `ipm_list_column` VALUES (1706, 17, 'port', 'Server port', '', 0);
INSERT INTO `ipm_list_column` VALUES (1707, 17, 'client', 'ClientIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1708, 17, 'clientport', 'Client port', '', 0);
INSERT INTO `ipm_list_column` VALUES (1709, 17, 'dbname', 'Database name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1710, 17, 'user', 'User name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1711, 17, 'sqlquery', 'SQLstatements', '', 1);
INSERT INTO `ipm_list_column` VALUES (1712, 17, 'responsecode', 'Return code', '', 1);
INSERT INTO `ipm_list_column` VALUES (1713, 17, 'responsemsg', 'Return message', '', 0);
INSERT INTO `ipm_list_column` VALUES (1714, 17, 'bytes', 'Bytes', '', 0);
INSERT INTO `ipm_list_column` VALUES (1715, 17, 'queryduration', 'SQL processing time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1716, 17, 'clientlatency', 'Network delay at client', '', 1);
INSERT INTO `ipm_list_column` VALUES (1717, 17, 'serverlatency', 'Network delay at server', '', 1);
INSERT INTO `ipm_list_column` VALUES (1718, 17, 'rtt', 'Network communication delay RTT', '', 1);

INSERT INTO `ipm_list_column` VALUES (802, 8, 'name', 'Service name', '', 1);
INSERT INTO `ipm_list_column` VALUES (803, 8, 'alarmLevel', 'Unresponsed alarm', '', 1);
INSERT INTO `ipm_list_column` VALUES (804, 8, 'sessionNum', 'Number of URL sessions', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (805, 8, 'ethernetTraffic', 'Flow', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (806, 8, 'pageloadTime', 'URL payload transfer delay', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (807, 8, 'responseTime', 'WebHTTP processing time', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (808, 8, 'rtt', 'Network communication delay', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (809, 8, 'url400Count', 'Number of 400 error', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (810, 8, 'http500Count', 'Number of 500 error', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (811, 8, 'failRespRatio', 'Ratio of HTTP error return code', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (1802, 18, 'name', 'Service name', '', 1);
INSERT INTO `ipm_list_column` VALUES (1803, 18, 'url', 'URL', '', 1);
INSERT INTO `ipm_list_column` VALUES (1804, 18, 'sessionNum', 'Number of URL sessions', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1805, 18, 'ethernetTraffic', 'Flow', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1806, 18, 'pageloadTime', 'URL payload transfer delay', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1807, 18, 'responseTime', 'WebHTTP processing time', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1808, 18, 'rtt', 'Network communication delay', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1809, 18, 'url400Count', 'Number of 400 error', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1810, 18, 'http500Count', 'Number of 500 error', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1811, 18, 'failRespRatio', 'Ratio of HTTP error return code', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (902, 9, 'name', 'Service name', '', 1);
INSERT INTO `ipm_list_column` VALUES (903, 9, 'alarmLevel', 'Unresponsed alarm', '', 1);
INSERT INTO `ipm_list_column` VALUES (904, 9, 'transCount', 'Number of transactions', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (905, 9, 'transTime', 'Transactions delay', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (906, 9, 'successRatio', 'Ratio of successful Transactions', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (907, 9, 'respRatio', 'Ratio of transactional response', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (1902, 19, 'transTime', 'Time', '', 1);
INSERT INTO `ipm_list_column` VALUES (1903, 19, 'ipmCenterName', 'XPMserver name', '', 0);
INSERT INTO `ipm_list_column` VALUES (1904, 19, 'name', 'Watchpoint name', '', 1);
INSERT INTO `ipm_list_column` VALUES (1905, 19, 'server', 'ServerIP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1906, 19, 'serverPort', 'Server port', '', 1);
INSERT INTO `ipm_list_column` VALUES (1907, 19, 'client', 'Client', '', 1);
INSERT INTO `ipm_list_column` VALUES (1908, 19, 'clientPort', 'Client port', '', 1);
INSERT INTO `ipm_list_column` VALUES (1909, 19, 'delay', 'Response delay', '', 1);
INSERT INTO `ipm_list_column` VALUES (1910, 19, 'source', 'Message', '', 1);
-- --------------------
-- 列表字段数据 END
-- --------------------


-- --------------------
-- 系统用户配置数据 START 
-- --------------------
INSERT INTO `ipm_user_list_column` VALUES (1, 1, 402);
INSERT INTO `ipm_user_list_column` VALUES (2, 1, 403);
INSERT INTO `ipm_user_list_column` VALUES (3, 1, 407);
INSERT INTO `ipm_user_list_column` VALUES (4, 1, 408);
INSERT INTO `ipm_user_list_column` VALUES (5, 1, 409);
INSERT INTO `ipm_user_list_column` VALUES (6, 1, 410);
INSERT INTO `ipm_user_list_column` VALUES (7, 1, 412);
INSERT INTO `ipm_user_list_column` VALUES (8, 1, 502);
INSERT INTO `ipm_user_list_column` VALUES (9, 1, 503);
INSERT INTO `ipm_user_list_column` VALUES (10, 1, 508);
INSERT INTO `ipm_user_list_column` VALUES (11, 1, 509);
INSERT INTO `ipm_user_list_column` VALUES (12, 1, 510);
INSERT INTO `ipm_user_list_column` VALUES (13, 1, 602);
INSERT INTO `ipm_user_list_column` VALUES (14, 1, 603);
INSERT INTO `ipm_user_list_column` VALUES (15, 1, 608);
INSERT INTO `ipm_user_list_column` VALUES (16, 1, 609);
INSERT INTO `ipm_user_list_column` VALUES (17, 1, 610);
INSERT INTO `ipm_user_list_column` VALUES (18, 1, 702);
INSERT INTO `ipm_user_list_column` VALUES (19, 1, 703);
INSERT INTO `ipm_user_list_column` VALUES (20, 1, 708);
INSERT INTO `ipm_user_list_column` VALUES (21, 1, 709);
INSERT INTO `ipm_user_list_column` VALUES (22, 1, 710);
INSERT INTO `ipm_user_list_column` VALUES (23, 1, 802);
INSERT INTO `ipm_user_list_column` VALUES (24, 1, 803);
INSERT INTO `ipm_user_list_column` VALUES (25, 1, 804);
INSERT INTO `ipm_user_list_column` VALUES (26, 1, 805);
INSERT INTO `ipm_user_list_column` VALUES (27, 1, 806);
INSERT INTO `ipm_user_list_column` VALUES (28, 1, 807);
INSERT INTO `ipm_user_list_column` VALUES (29, 1, 808);
INSERT INTO `ipm_user_list_column` VALUES (30, 1, 809);
INSERT INTO `ipm_user_list_column` VALUES (31, 1, 810);
INSERT INTO `ipm_user_list_column` VALUES (32, 1, 811);
INSERT INTO `ipm_user_list_column` VALUES (33, 1, 902);
INSERT INTO `ipm_user_list_column` VALUES (34, 1, 903);
INSERT INTO `ipm_user_list_column` VALUES (35, 1, 904);
INSERT INTO `ipm_user_list_column` VALUES (36, 1, 905);
INSERT INTO `ipm_user_list_column` VALUES (37, 1, 906);
INSERT INTO `ipm_user_list_column` VALUES (38, 1, 907);
INSERT INTO `ipm_user_list_column` VALUES (39, 1, 1001);
INSERT INTO `ipm_user_list_column` VALUES (40, 1, 1011);
INSERT INTO `ipm_user_list_column` VALUES (41, 1, 1012);
INSERT INTO `ipm_user_list_column` VALUES (42, 1, 1022);
INSERT INTO `ipm_user_list_column` VALUES (43, 1, 1029);
INSERT INTO `ipm_user_list_column` VALUES (44, 1, 1038);
INSERT INTO `ipm_user_list_column` VALUES (45, 1, 1045);
INSERT INTO `ipm_user_list_column` VALUES (46, 1, 1046);
INSERT INTO `ipm_user_list_column` VALUES (47, 1, 1102);
INSERT INTO `ipm_user_list_column` VALUES (48, 1, 1103);
INSERT INTO `ipm_user_list_column` VALUES (49, 1, 1104);
INSERT INTO `ipm_user_list_column` VALUES (50, 1, 1112);
INSERT INTO `ipm_user_list_column` VALUES (51, 1, 1118);
INSERT INTO `ipm_user_list_column` VALUES (52, 1, 1127);
INSERT INTO `ipm_user_list_column` VALUES (53, 1, 1134);
INSERT INTO `ipm_user_list_column` VALUES (54, 1, 1202);
INSERT INTO `ipm_user_list_column` VALUES (55, 1, 1203);
INSERT INTO `ipm_user_list_column` VALUES (56, 1, 1204);
INSERT INTO `ipm_user_list_column` VALUES (57, 1, 1214);
INSERT INTO `ipm_user_list_column` VALUES (58, 1, 1220);
INSERT INTO `ipm_user_list_column` VALUES (59, 1, 1229);
INSERT INTO `ipm_user_list_column` VALUES (60, 1, 1236);
INSERT INTO `ipm_user_list_column` VALUES (61, 1, 1301);
INSERT INTO `ipm_user_list_column` VALUES (62, 1, 1305);
INSERT INTO `ipm_user_list_column` VALUES (63, 1, 1308);
INSERT INTO `ipm_user_list_column` VALUES (64, 1, 1310);
INSERT INTO `ipm_user_list_column` VALUES (65, 1, 1311);
INSERT INTO `ipm_user_list_column` VALUES (66, 1, 1401);
INSERT INTO `ipm_user_list_column` VALUES (67, 1, 1402);
INSERT INTO `ipm_user_list_column` VALUES (68, 1, 1405);
INSERT INTO `ipm_user_list_column` VALUES (69, 1, 1407);
INSERT INTO `ipm_user_list_column` VALUES (70, 1, 1409);
INSERT INTO `ipm_user_list_column` VALUES (71, 1, 1410);
INSERT INTO `ipm_user_list_column` VALUES (72, 1, 1413);
INSERT INTO `ipm_user_list_column` VALUES (73, 1, 1415);
INSERT INTO `ipm_user_list_column` VALUES (74, 1, 1416);
INSERT INTO `ipm_user_list_column` VALUES (75, 1, 1417);
INSERT INTO `ipm_user_list_column` VALUES (76, 1, 1418);
INSERT INTO `ipm_user_list_column` VALUES (77, 1, 1419);
INSERT INTO `ipm_user_list_column` VALUES (78, 1, 1501);
INSERT INTO `ipm_user_list_column` VALUES (79, 1, 1502);
INSERT INTO `ipm_user_list_column` VALUES (80, 1, 1505);
INSERT INTO `ipm_user_list_column` VALUES (81, 1, 1507);
INSERT INTO `ipm_user_list_column` VALUES (82, 1, 1511);
INSERT INTO `ipm_user_list_column` VALUES (83, 1, 1512);
INSERT INTO `ipm_user_list_column` VALUES (84, 1, 1515);
INSERT INTO `ipm_user_list_column` VALUES (85, 1, 1516);
INSERT INTO `ipm_user_list_column` VALUES (86, 1, 1517);
INSERT INTO `ipm_user_list_column` VALUES (87, 1, 1518);
INSERT INTO `ipm_user_list_column` VALUES (88, 1, 1601);
INSERT INTO `ipm_user_list_column` VALUES (89, 1, 1602);
INSERT INTO `ipm_user_list_column` VALUES (90, 1, 1605);
INSERT INTO `ipm_user_list_column` VALUES (91, 1, 1607);
INSERT INTO `ipm_user_list_column` VALUES (92, 1, 1611);
INSERT INTO `ipm_user_list_column` VALUES (93, 1, 1612);
INSERT INTO `ipm_user_list_column` VALUES (94, 1, 1615);
INSERT INTO `ipm_user_list_column` VALUES (95, 1, 1616);
INSERT INTO `ipm_user_list_column` VALUES (96, 1, 1617);
INSERT INTO `ipm_user_list_column` VALUES (97, 1, 1618);
INSERT INTO `ipm_user_list_column` VALUES (98, 1, 1701);
INSERT INTO `ipm_user_list_column` VALUES (99, 1, 1702);
INSERT INTO `ipm_user_list_column` VALUES (100, 1, 1705);
INSERT INTO `ipm_user_list_column` VALUES (101, 1, 1707);
INSERT INTO `ipm_user_list_column` VALUES (102, 1, 1711);
INSERT INTO `ipm_user_list_column` VALUES (103, 1, 1712);
INSERT INTO `ipm_user_list_column` VALUES (104, 1, 1715);
INSERT INTO `ipm_user_list_column` VALUES (105, 1, 1716);
INSERT INTO `ipm_user_list_column` VALUES (106, 1, 1717);
INSERT INTO `ipm_user_list_column` VALUES (107, 1, 1718);
INSERT INTO `ipm_user_list_column` VALUES (108, 1, 1802);
INSERT INTO `ipm_user_list_column` VALUES (109, 1, 1803);
INSERT INTO `ipm_user_list_column` VALUES (110, 1, 1804);
INSERT INTO `ipm_user_list_column` VALUES (111, 1, 1805);
INSERT INTO `ipm_user_list_column` VALUES (112, 1, 1806);
INSERT INTO `ipm_user_list_column` VALUES (113, 1, 1807);
INSERT INTO `ipm_user_list_column` VALUES (114, 1, 1808);
INSERT INTO `ipm_user_list_column` VALUES (115, 1, 1809);
INSERT INTO `ipm_user_list_column` VALUES (116, 1, 1810);
INSERT INTO `ipm_user_list_column` VALUES (117, 1, 1811);
INSERT INTO `ipm_user_list_column` VALUES (118, 1, 1902);
INSERT INTO `ipm_user_list_column` VALUES (119, 1, 1904);
INSERT INTO `ipm_user_list_column` VALUES (120, 1, 1905);
INSERT INTO `ipm_user_list_column` VALUES (121, 1, 1906);
INSERT INTO `ipm_user_list_column` VALUES (122, 1, 1907);
INSERT INTO `ipm_user_list_column` VALUES (123, 1, 1908);
INSERT INTO `ipm_user_list_column` VALUES (124, 1, 1909);
INSERT INTO `ipm_user_list_column` VALUES (125, 1, 1910);
-- --------------------
-- 系统用户配置数据 END 
-- --------------------


-- --------------------
-- kpi数据 START 
-- 单位说明: 
--  ratio:代表百分比类
--  flow:代表Flow类
--  pps:代表速率类
--  num:代表数量类
--  ms:代表时延类
-- --------------------
INSERT INTO `ipm_kpis` VALUES (1, 'ethernetTraffic', 'Network traffic', 'flow', 'Increased network visits，Probably caused by service、network attack,etc.');
INSERT INTO `ipm_kpis` VALUES (2, 'sessionNum', 'Session rate', 'num', 'Increased access，Increased concurrency。Probably caused by hot point、business centralization、network congestion、network attack,etc.');
INSERT INTO `ipm_kpis` VALUES (3, 'ethernetPkts', 'Packet rate', 'pps', 'Packet rate exceeds the set threshold，the possible reasons：1、Traffic surge；2、Tine packet suerge,etc.');
INSERT INTO `ipm_kpis` VALUES (4, 'serverDurTime', 'Network delay at server', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、network attack,etc.');
INSERT INTO `ipm_kpis` VALUES (5, 'clientDurTime', 'Network delay at client', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、network attack,etc.');
INSERT INTO `ipm_kpis` VALUES (6, 'rtt', 'Round trip time', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、network attack,etc.');
INSERT INTO `ipm_kpis` VALUES (7, 'serverConTime', 'Server handshake delay', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、network attack,etc.');
INSERT INTO `ipm_kpis` VALUES (8, 'clientConTime', 'Client handshake delay', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、network attack,etc.');
INSERT INTO `ipm_kpis` VALUES (9, 'netPktLostRatio', 'Packet loss rate', 'ratio', 'Network packet dropout ratio exceeds the set threshold。Probably caused by overloaded network links，network equipment failure，wrong Network device configuration、zero window phenomenon of server or client,etc.');
INSERT INTO `ipm_kpis` VALUES (10, 'serverPktLostRatio', 'Packet loss rate at server', 'ratio', 'Packet loss rate at server exceeds the set threshold。Probably caused by overloaded network links，network equipment failure，wrong Network device configuration、zero window phenomenon of server,etc.');
INSERT INTO `ipm_kpis` VALUES (11, 'clientPktLostRatio', 'Packet loss rate at client', 'ratio', 'Packet loss rate at client exceeds the set threshold。Probably caused by overloaded network links，network equipment failure，wrong Network device configuration、zero window phenomenon of client ,etc.');
INSERT INTO `ipm_kpis` VALUES (12, 'rstPkts', 'TCP connection reset', 'num', 'the possible reasons link quality、Server application capabilities,etc.');
INSERT INTO `ipm_kpis` VALUES (13, 'synPkts', 'Attempt connection', 'num', 'Increased access，Increased concurrency。Probably caused by hot point、business centralization、network congestion、network attack,etc.');
INSERT INTO `ipm_kpis` VALUES (14, 'qos', 'Access Quality', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、network attack,etc.');
INSERT INTO `ipm_kpis` VALUES (16, 'loadtranstime', 'Load transfer delay', 'ms', 'the possible reasons include link quality、Server application capabilities,etc.');
INSERT INTO `ipm_kpis` VALUES (17, 'finPkts', 'Number of closing', 'num', 'the possible reasons include link quality、Server application capabilities,etc.');
INSERT INTO `ipm_kpis` VALUES (19, 'tinyPkts', 'Small packet rate', 'pps', 'Small packet rate exceeds the set threshold，probably caused by traffic surge、attack、ARP traffic、ICMPFlow,etc.');
INSERT INTO `ipm_kpis` VALUES (20, 'tinyPktsRatio', 'Small packet ratio', 'ratio', 'Small packet ratio exceeds the set threshold，probably caused by traffic surge、attack、ARP traffic、ICMPFlow,etc.');
INSERT INTO `ipm_kpis` VALUES (21, 'tcpTraffic', 'TCP traffic', 'flow', 'The accesses though TCP Protocol increased');
INSERT INTO `ipm_kpis` VALUES (22, 'udpTraffic', 'UDP traffic', 'flow', 'The accesses though UDP Protocol increased');
INSERT INTO `ipm_kpis` VALUES (23, 'netPktLost', 'Number of network packet loss', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (24, 'serverPktLost', 'Number of service packet loss', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (25, 'serverPkt', 'Rate of service packet', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (26, 'clientPktLost', 'Number of Client packet loss', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (27, 'clientPkt', 'Client packet rate', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (28, 'unKnowSerTraffic', 'Undefined server traffic', 'flow', 'Traffic to unknown services increases');
INSERT INTO `ipm_kpis` VALUES (29, 'unKnowCliTraffic', 'Undefined client traffic', 'flow', 'Traffic to unknown clients increases');
INSERT INTO `ipm_kpis` VALUES (30, 'arpTraffic', 'ARP traffic', 'flow', 'ARP traffic exceeds the set threshold，Probably caused by an ARP storm');
INSERT INTO `ipm_kpis` VALUES (31, 'arpPkts', 'ARP packet rate', 'pps', 'ARP packet PPS exceeds the set threshold，Probably caused by an ARP storm');
INSERT INTO `ipm_kpis` VALUES (32, 'bandWidthRatio', 'Bandwidth utilization ', 'ratio', 'bandwidth occupancy exceeds the set threshold，Caused by Flow surge');
INSERT INTO `ipm_kpis` VALUES (33, 'unknowUserTraffic', 'Unidentified user ProtocolFlow', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (34, 'unknowPublicTraffic', 'Unidentified public protocolFlow', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (35, 'l7SessionCountTotal', 'Number of application sessions', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (36, 'responseTime', 'Application processing delay', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、Network attack, etc.');
INSERT INTO `ipm_kpis` VALUES (37, 'pageloadTime', 'Loading time delay', 'ms', NULL);
INSERT INTO `ipm_kpis` VALUES (38, 'http400Count', 'Number of error return code', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (39, 'bandWidth', 'Set the bandwidth', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (43, 'serverRetransTime', 'Retransmission delay at server', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、Network attack, etc.');
INSERT INTO `ipm_kpis` VALUES (44, 'clientRetransTime', 'Retransmission delay at client', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、Network attack, etc.');
INSERT INTO `ipm_kpis` VALUES (45, 'failRespRatio', 'Ratio of  error return code', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (46, 'noRespCount', 'Number of non response', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (47, 'noRespRatio', 'Ratio of non response', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (48, 'http500Count', 'Number of 500 error return code', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (49, 'usability', 'Application usability', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (50, 'avgPktsLen', 'Averager packet length', 'flowB', 'Averager packet length exceed the set threshold');
INSERT INTO `ipm_kpis` VALUES (51, 'zeroWinCount', 'Zero window packet', 'num', 'The buffer of server or Client for receiving packets is full');
-- Message专用KPI START
INSERT INTO `ipm_kpis` VALUES (52, 'transCount', 'Number of transactions', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (53, 'transTime', 'Transactions delay', 'ms', NULL);
INSERT INTO `ipm_kpis` VALUES (54, 'successRatio', 'Ratio of successful Transactions', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (55, 'respRatio', 'Ratio of  transactional response', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (56, 'failCount', 'Amount of failure', 'num', NULL);
-- Message专用KPI END
INSERT INTO `ipm_kpis` VALUES (57, 'synAckPkts', 'Response connection', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (58, 'fin1Pkts', 'Active close connection', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (59, 'fin2Pkts', 'Passive close connection', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (60, 'inTraffic', 'Downstream traffic', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (61, 'outTraffic', 'Upstream traffic', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (62, 'url400Count', 'Number of 400 error return code', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (63, 'httpResponseTime', 'Response time', 'ms', 'the possible reasons：Hardware and software failure、Increased concurrency、Poor link quality、Network attack, etc.');
INSERT INTO `ipm_kpis` VALUES (64, 'upBandWidth', 'Uplink bandwidth ', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (65, 'downBandWidth', 'Downlink bandwidth ', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (66, 'upBandWidthRatio', 'Upstream bandwidth utilization ', 'ratio', 'Upstream bandwidth utilization exceeds the set threshold，Caused by Flow surge');
INSERT INTO `ipm_kpis` VALUES (67, 'downBandWidthRatio', 'Downstream bandwidth utilization ', 'ratio', 'Downstream bandwidth utilization exceeds the set threshold，Caused by Flow surge');
INSERT INTO `ipm_kpis` VALUES (68, 'conRespRatio', 'Connection unresponse Ratio', 'ratio', 'The ratio of handshake responses to handshake requests per unit of time');

INSERT INTO `ipm_kpis` VALUES (69, 'conResetRatio', 'Connection reset rate', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (70, 'conCloseRatio', 'Connection closed rate', 'ratio', NULL);

-- 新功能 START
INSERT INTO `ipm_kpis` VALUES (90, 'probeSynchronous', 'Probe synchronization', 'num', 'the possible reason：Network problems, etc.');
INSERT INTO `ipm_kpis` VALUES (91, 'probeNetwork', 'Probe the network', 'num', 'the possible reason：Network problems, etc.');
-- 新功能 END

-- 系统监控专用KPI START
INSERT INTO `ipm_kpis` VALUES (100, 'cpu_us', 'CPU_US', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (101, 'cpu_sy', 'CPU_SY', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (102, 'cpu_ni', 'CPU_NI', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (103, 'cpu_id', 'CPU_ID', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (104, 'cpu_wa', 'CPU_WA', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (105, 'cpu_hi', 'CPU_HI', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (106, 'cpu_si', 'CPU_SI', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (107, 'cpu_st', 'CPU_ST', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (108, 'mem_total', 'Total memory', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (109, 'mem_used', 'Used memory', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (110, 'mem_free', 'Free memory', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (111, 'mem_buffers', 'Kernel cache', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (112, 'swap_total', 'SWAP total', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (113, 'swap_used', 'Used SWAP', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (114, 'swap_free', 'Free SWAP', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (115, 'swap_cached', 'Disk cache', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (116, 'ipmipc_cpu', 'IPC_CPU', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (117, 'ipmipc_mem', 'IPC_MEM', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (118, 'ipmbss_cpu', 'BSS_CPU', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (119, 'ipmbss_mem', 'BSS_MEM', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (120, 'ipmcomm_cpu', 'COMM_CPU', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (121, 'ipmcomm_mem', 'COMM_MEM', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (122, 'ipmdsas_cpu', 'DSAS_CPU', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (123, 'ipmdsas_mem', 'DSAS_MEM', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (124, 'ipmcoll_cpu', 'COLL_CPU', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (125, 'ipmcoll_mem', 'COLL_MEM', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (126, 'java_cpu', 'JAVA_CPU', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (127, 'java_mem', 'JAVA_MEM', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (128, 'mysql_cpu', 'MYSQL_CPU', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (129, 'mysql_mem', 'MYSQL_MEM', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (130, 'rrdcached_cpu', 'RRD_CPU', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (131, 'rrdcached_mem', 'RRD_MEM', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (132, 'load_1', 'One minute load', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (133, 'load_5', 'Five minutes load', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (134, 'load_15', 'Fifteen minutes load', 'ratio', NULL);
-- 系统监控专用KPI END
-- --------------------
-- kpi数据 END 
-- --------------------


-- --------------------
-- 绘图选项数据 START 
-- --------------------
INSERT INTO `ipm_plot_option` VALUES (1, 10, 3, 1, 'Network traffic', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (2, 10, 4, 2, 'Session rate', 1, 0, 'SUM', 3);
INSERT INTO `ipm_plot_option` VALUES (3, 10, 5, 3, 'Packet rate', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (4, 10, 2, 4, 'Network delay at server', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (5, 10, 2, 5, 'Network delay at client', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (6, 10, 2, 6, 'Round trip time', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (7, 10, 2, 7, 'Server handshake time', 1, 1, 'AVG', 8);
INSERT INTO `ipm_plot_option` VALUES (8, 10, 2, 8, 'Client handshake time', 1, 1, 'AVG', 9);
INSERT INTO `ipm_plot_option` VALUES (9, 10, 5, 9, 'Packet loss rate', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (10, 10, 5, 10, 'Packet loss rate at server', 1, 1, 'AVG', 11);
INSERT INTO `ipm_plot_option` VALUES (11, 10, 5, 11, 'Packet loss rate at client', 1, 1, 'AVG', 12);
INSERT INTO `ipm_plot_option` VALUES (12, 10, 4, 12, 'TCP connection reset', 1, 1, 'SUM', 13);
INSERT INTO `ipm_plot_option` VALUES (15, 10, 4, 13, 'Attempt connection', 1, 1, 'SUM', 16);
INSERT INTO `ipm_plot_option` VALUES (17, 10, 4, 17, 'Closed connection', 1, 1, 'SUM', 18);
INSERT INTO `ipm_plot_option` VALUES (19, 10, 5, 19, 'Small packet rate', 1, 1, 'AVG', 20);
INSERT INTO `ipm_plot_option` VALUES (20, 10, 5, 20, 'Small packet ratio', 1, 1, 'AVG', 21);
INSERT INTO `ipm_plot_option` VALUES (21, 10, 3, 21, 'TCP traffic', 1, 1, 'SUM', 22);
INSERT INTO `ipm_plot_option` VALUES (22, 10, 3, 22, 'UDP traffic', 1, 1, 'SUM', 23);
INSERT INTO `ipm_plot_option` VALUES (23, 10, 2, 36, 'Application processing time', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (28, 10, 3, 28, 'Undefined server traffic', 1, 1, 'SUM', 29);
INSERT INTO `ipm_plot_option` VALUES (29, 10, 3, 29, 'Undefined client traffic', 1, 1, 'SUM', 30);
INSERT INTO `ipm_plot_option` VALUES (30, 10, 6, 1, 'Defined user protocol proportion', 1, 0, NULL, 31);
INSERT INTO `ipm_plot_option` VALUES (31, 10, 6, 1, 'Standart protocol proportion', 1, 0, NULL, 32);
INSERT INTO `ipm_plot_option` VALUES (32, 11, 3, 1, 'Network traffic', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (33, 11, 4, 2, 'Session rate', 1, 0, 'SUM', 3);
INSERT INTO `ipm_plot_option` VALUES (34, 11, 5, 3, 'Packet rate', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (35, 11, 2, 4, 'Network delay at server', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (36, 11, 2, 5, 'Network delay at client', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (37, 11, 2, 6, 'Round trip time', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (38, 11, 2, 7, 'Server handshake time', 1, 1, 'AVG', 8);
INSERT INTO `ipm_plot_option` VALUES (39, 11, 2, 8, 'Client handshake time', 1, 1, 'AVG', 9);
INSERT INTO `ipm_plot_option` VALUES (40, 11, 5, 9, 'Packet loss rate', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (41, 11, 5, 10, 'Packet loss rate at server', 1, 1, 'AVG', 11);
INSERT INTO `ipm_plot_option` VALUES (42, 11, 5, 11, 'Packet loss rate at client', 1, 1, 'AVG', 12);
INSERT INTO `ipm_plot_option` VALUES (43, 11, 4, 12, 'TCP connection reset', 1, 1, 'SUM', 13);
INSERT INTO `ipm_plot_option` VALUES (46, 11, 4, 13, 'Attempt connection', 1, 1, 'SUM', 16);
INSERT INTO `ipm_plot_option` VALUES (48, 11, 4, 17, 'Closed connection', 1, 1, 'SUM', 18);
INSERT INTO `ipm_plot_option` VALUES (50, 11, 5, 19, 'Small packet rate', 1, 1, 'AVG', 20);
INSERT INTO `ipm_plot_option` VALUES (51, 11, 5, 20, 'Small packet ratio', 1, 1, 'AVG', 21);
INSERT INTO `ipm_plot_option` VALUES (52, 11, 3, 21, 'TCP traffic', 1, 1, 'SUM', 22);
INSERT INTO `ipm_plot_option` VALUES (53, 11, 3, 22, 'UDP traffic', 1, 1, 'SUM', 23);
INSERT INTO `ipm_plot_option` VALUES (54, 11, 2, 36, 'Application processing time', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (58, 11, 6, 32, 'Bandwidth utilization', 1, 1, 'AVG', 28);
INSERT INTO `ipm_plot_option` VALUES (59, 11, 6, 1, 'Defined user protocol proportion', 1, 0, NULL, 31);
INSERT INTO `ipm_plot_option` VALUES (60, 12, 3, 1, 'Network traffic', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (61, 12, 4, 2, 'Session rate', 1, 0, 'SUM', 3);
INSERT INTO `ipm_plot_option` VALUES (62, 12, 5, 3, 'Packet rate', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (63, 12, 2, 4, 'Network delay at server', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (64, 12, 2, 5, 'Network delay at client', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (65, 12, 2, 6, 'Round trip time', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (66, 12, 2, 7, 'Server handshake time', 1, 1, 'AVG', 8);
INSERT INTO `ipm_plot_option` VALUES (67, 12, 2, 8, 'Client handshake time', 1, 1, 'AVG', 9);
INSERT INTO `ipm_plot_option` VALUES (68, 12, 5, 9, 'Packet loss rate', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (69, 12, 5, 10, 'Packet loss rate at server', 1, 1, 'AVG', 11);
INSERT INTO `ipm_plot_option` VALUES (70, 12, 5, 11, 'Packet loss rate at client', 1, 1, 'AVG', 12);
INSERT INTO `ipm_plot_option` VALUES (71, 12, 4, 12, 'TCP connection reset', 1, 1, 'SUM', 13);
INSERT INTO `ipm_plot_option` VALUES (74, 12, 4, 13, 'Attempt connection', 1, 1, 'SUM', 16);
INSERT INTO `ipm_plot_option` VALUES (76, 12, 4, 17, 'Closed connection', 1, 1, 'SUM', 18);
INSERT INTO `ipm_plot_option` VALUES (78, 12, 5, 19, 'Small packet rate', 1, 1, 'AVG', 20);
INSERT INTO `ipm_plot_option` VALUES (79, 12, 5, 20, 'Small packet ratio', 1, 1, 'AVG', 21);
INSERT INTO `ipm_plot_option` VALUES (80, 12, 3, 21, 'TCP traffic', 1, 1, 'SUM', 22);
INSERT INTO `ipm_plot_option` VALUES (81, 12, 3, 22, 'UDP traffic', 1, 1, 'SUM', 23);
INSERT INTO `ipm_plot_option` VALUES (82, 12, 2, 36, 'Application processing time', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (84, 10, 6, 30, 'ARP traffic', 1, 1, 'SUM', 33);
INSERT INTO `ipm_plot_option` VALUES (85, 10, 6, 31, 'ARP packet rate', 1, 1, 'AVG', 34);
INSERT INTO `ipm_plot_option` VALUES (86, 10, 1, 0, 'Unresponsed alarm', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (87, 11, 1, 0, 'Unresponsed alarm', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (88, 12, 1, 0, 'Unresponsed alarm', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (89, 11, 6, 1, 'Standart protocol proportion', 1, 0, NULL, 30);
INSERT INTO `ipm_plot_option` VALUES (90, 11, 3, 28, 'Undefined server traffic', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (94, 12, 3, 29, 'Undefined client traffic', 1, 1, 'SUM', 27);
INSERT INTO `ipm_plot_option` VALUES (97, 12, 6, 32, 'Bandwidth utilization', 1, 1, 'AVG', 30);
INSERT INTO `ipm_plot_option` VALUES (101, 4, 1, 0, 'Unresponsed alarm', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (102, 4, 3, 1, 'HTTP service traffic', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (105, 4, 2, 4, 'Network delay at server', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (106, 4, 2, 5, 'Network delay at client', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (107, 4, 2, 6, 'Round trip time', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (110, 4, 5, 9, 'Packet loss rate', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (131, 4, 4, 35, 'HTTP session', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (132, 4, 2, 63, 'HTTP processing time', 1, 1, 'AVG', 32);
INSERT INTO `ipm_plot_option` VALUES (133, 4, 2, 37, 'URL payload transfer time', 1, 1, 'AVG', 33);
INSERT INTO `ipm_plot_option` VALUES (135, 5, 1, 0, 'Unresponsed alarm', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (136, 5, 3, 1, 'Oracle service traffic', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (139, 5, 2, 4, 'Network delay at server', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (140, 5, 2, 5, 'Network delay at client', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (141, 5, 2, 6, 'Round trip time', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (144, 5, 5, 9, 'Packet loss rate', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (158, 5, 2, 63, 'SQL processing time', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (165, 5, 4, 35, 'SQL session', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (168, 5, 4, 38, 'SQL return code', 1, 1, 'SUM', 34);
INSERT INTO `ipm_plot_option` VALUES (169, 6, 1, 0, 'Unresponsed alarm', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (170, 6, 3, 1, 'MySqlserviceFlow', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (173, 6, 2, 4, 'Network delay at server', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (174, 6, 2, 5, 'Network delay at client', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (175, 6, 2, 6, 'Round trip time', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (178, 6, 5, 9, 'Packet loss rate', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (192, 6, 2, 63, 'SQL processing time', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (199, 6, 4, 35, 'SQL session', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (202, 6, 4, 38, 'SQL return code', 1, 1, 'SUM', 34);
INSERT INTO `ipm_plot_option` VALUES (203, 7, 1, 0, 'Unresponsed alarm', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (204, 7, 3, 1, 'SQLServer service flow', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (207, 7, 2, 4, 'Network delay at server', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (208, 7, 2, 5, 'Network delay at client', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (209, 7, 2, 6, 'Round trip time', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (212, 7, 5, 9, 'Packet loss rate', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (226, 7, 2, 63, 'SQL processing time', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (233, 7, 4, 35, 'SQL session', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (236, 7, 4, 38, 'SQL return code', 1, 1, 'SUM', 34);
INSERT INTO `ipm_plot_option` VALUES (237, 4, 6, 0, 'Ranking of URL visit times', 1, 0, 'SUM', 35);
INSERT INTO `ipm_plot_option` VALUES (238, 4, 6, 0, 'Ranking of URL payload transfer time', 1, 0, 'AVG', 36);
INSERT INTO `ipm_plot_option` VALUES (239, 4, 6, 0, 'Ranking of 400 error most URL', 1, 0, 'SUM', 37);
INSERT INTO `ipm_plot_option` VALUES (240, 4, 6, 0, 'Ranking of 500 error most URL', 1, 0, 'SUM', 38);
INSERT INTO `ipm_plot_option` VALUES (241, 5, 6, 0, 'SQL ranking of processing time', 1, 0, 'AVG', 35);
INSERT INTO `ipm_plot_option` VALUES (242, 5, 6, 0, 'SQL ranking of non-zero return codes', 1, 0, 'SUM', 36);
INSERT INTO `ipm_plot_option` VALUES (243, 6, 6, 0, 'SQL ranking of processing time', 1, 0, 'AVG', 35);
INSERT INTO `ipm_plot_option` VALUES (244, 6, 6, 0, 'SQL ranking of non-zero return codes', 1, 0, 'SUM', 36);
INSERT INTO `ipm_plot_option` VALUES (245, 7, 6, 0, 'SQL ranking of processing time', 1, 0, 'AVG', 35);
INSERT INTO `ipm_plot_option` VALUES (246, 7, 6, 0, 'SQL ranking of non-zero return codes', 1, 0, 'SUM', 36);
INSERT INTO `ipm_plot_option` VALUES (267, 10, 2, 43, 'Retransmission time at server', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (268, 10, 2, 44, 'Retransmission time at client', 1, 1, 'AVG', 40);
INSERT INTO `ipm_plot_option` VALUES (269, 11, 2, 43, 'Retransmission time at server', 1, 1, 'AVG', 38);
INSERT INTO `ipm_plot_option` VALUES (270, 11, 2, 44, 'Retransmission time at client', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (271, 12, 2, 43, 'Retransmission time at server', 1, 1, 'AVG', 38);
INSERT INTO `ipm_plot_option` VALUES (272, 12, 2, 44, 'Retransmission time at client', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (273, 4, 4, 45, 'Ratio of HTTP error return code', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (275, 4, 4, 47, 'Ratio of HTTP upnresponse', 1, 1, 'AVG', 41);
INSERT INTO `ipm_plot_option` VALUES (276, 10, 6, 66, 'Upstream bandwidth utilization', 1, 1, 'AVG', 26);
INSERT INTO `ipm_plot_option` VALUES (277, 5, 4, 45, 'SQL error return code ratio', 1, 1, 'AVG', 37);
INSERT INTO `ipm_plot_option` VALUES (278, 5, 4, 46, 'SQL unresponse', 1, 1, 'SUM', 38);
INSERT INTO `ipm_plot_option` VALUES (279, 5, 4, 47, 'Ratio of SQL unresponse', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (280, 6, 4, 45, 'SQL error return code ratio', 1, 1, 'AVG', 37);
INSERT INTO `ipm_plot_option` VALUES (281, 6, 4, 46, 'SQL unresponse', 1, 1, 'SUM', 38);
INSERT INTO `ipm_plot_option` VALUES (282, 6, 4, 47, 'Ratio of SQL unresponse', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (283, 7, 4, 45, 'SQL error return code ratio', 1, 1, 'AVG', 37);
INSERT INTO `ipm_plot_option` VALUES (284, 7, 4, 46, 'SQL unresponse', 1, 1, 'SUM', 38);
INSERT INTO `ipm_plot_option` VALUES (285, 7, 4, 47, 'Ratio of SQL unresponse', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (286, 8, 1, 0, 'Unresponsed alarm', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (287, 8, 2, 6, 'Network communication time', 1, 1, 'AVG', 2);
INSERT INTO `ipm_plot_option` VALUES (288, 8, 2, 36, 'WebHTTP processing time', 1, 1, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (289, 8, 2, 37, 'URL payload transfer time', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (290, 8, 3, 1, 'HTTP service traffic', 1, 1, 'SUM', 5);
INSERT INTO `ipm_plot_option` VALUES (291, 8, 4, 2, 'Page view', 1, 1, 'SUM', 6);
INSERT INTO `ipm_plot_option` VALUES (292, 8, 4, 62, 'Number of 400 error Return code', 1, 1, 'SUM', 7);
INSERT INTO `ipm_plot_option` VALUES (293, 8, 4, 48, 'Number of 500 error Return code', 1, 1, 'SUM', 8);
INSERT INTO `ipm_plot_option` VALUES (294, 8, 4, 45, 'Ratio of HTTP error return code', 1, 1, 'AVG', 9);
INSERT INTO `ipm_plot_option` VALUES (295, 10, 5, 50, 'Averager packet length', 1, 1, 'AVG', 41);
INSERT INTO `ipm_plot_option` VALUES (296, 10, 5, 51, 'Zero window packet', 1, 1, 'SUM', 42);
INSERT INTO `ipm_plot_option` VALUES (297, 11, 5, 50, 'Averager packet length', 1, 1, 'AVG', 40);
INSERT INTO `ipm_plot_option` VALUES (298, 11, 5, 51, 'Zero window packet', 1, 1, 'SUM', 41);
INSERT INTO `ipm_plot_option` VALUES (299, 12, 5, 50, 'Averager packet length', 1, 1, 'AVG', 40);
INSERT INTO `ipm_plot_option` VALUES (300, 12, 5, 51, 'Zero window packet', 1, 1, 'SUM', 41);
INSERT INTO `ipm_plot_option` VALUES (301, 10, 5, 1, 'Packet size proportion', 1, 0, 'SUM', 43);
INSERT INTO `ipm_plot_option` VALUES (302, 11, 5, 1, 'Packet size proportion', 1, 0, 'SUM', 42);
INSERT INTO `ipm_plot_option` VALUES (303, 9, 1, 0, 'Unresponsed alarm', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (304, 9, 4, 52, 'Number of transactions', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (305, 9, 2, 53, 'Transactions time', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (306, 9, 4, 54, 'Ratio of successful Transactions', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (307, 9, 4, 55, 'Ratio of  transactional response', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (308, 10, 1, 0, 'Health degree', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (309, 11, 1, 0, 'Health degree', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (310, 12, 1, 0, 'Health degree', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (311, 4, 1, 0, 'Health degree', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (312, 5, 1, 0, 'Health degree', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (313, 6, 1, 0, 'Health degree', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (314, 7, 1, 0, 'Health degree', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (315, 8, 1, 0, 'Health degree', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (316, 9, 1, 0, 'Health degree', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (317, 10, 2, 14, 'Access Quality', 1, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (318, 10, 2, 16, 'Load transfer time', 1, 1, 'AVG', 1);
INSERT INTO `ipm_plot_option` VALUES (319, 12, 2, 14, 'Access Quality', 1, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (320, 12, 2, 16, 'Load transfer time', 1, 1, 'AVG', 1);
INSERT INTO `ipm_plot_option` VALUES (321, 10, 6, 14, 'Access Quality+Session rate', 1, 0, NULL, 44);
INSERT INTO `ipm_plot_option` VALUES (322, 9, 4, 56, 'Amount of failure', 1, 1, 'SUM', 3);
INSERT INTO `ipm_plot_option` VALUES (323, 10, 4, 2, 'Max Session Number', 0, 1, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (324, 11, 4, 2, 'Max Session Number', 0, 1, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (325, 12, 4, 2, 'Max Session Number', 0, 1, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (326, 10, 4, 57, 'Response connection', 1, 1, 'SUM', 17);
INSERT INTO `ipm_plot_option` VALUES (327, 10, 4, 58, 'Active close connection', 1, 1, 'SUM', 19);
INSERT INTO `ipm_plot_option` VALUES (328, 10, 4, 59, 'Passive close connection', 1, 1, 'SUM', 20);
INSERT INTO `ipm_plot_option` VALUES (329, 10, 3, 60, 'Downstream traffic', 1, 1, 'SUM', 24);
INSERT INTO `ipm_plot_option` VALUES (330, 10, 3, 61, 'Upstream traffic', 1, 1, 'SUM', 25);
INSERT INTO `ipm_plot_option` VALUES (331, 11, 4, 57, 'Response connection', 1, 1, 'SUM', 17);
INSERT INTO `ipm_plot_option` VALUES (332, 11, 4, 58, 'Active close connection', 1, 1, 'SUM', 19);
INSERT INTO `ipm_plot_option` VALUES (333, 11, 4, 59, 'Passive close connection', 1, 1, 'SUM', 20);
INSERT INTO `ipm_plot_option` VALUES (334, 11, 3, 60, 'Downstream traffic', 1, 1, 'SUM', 24);
INSERT INTO `ipm_plot_option` VALUES (335, 11, 3, 61, 'Upstream traffic', 1, 1, 'SUM', 25);
INSERT INTO `ipm_plot_option` VALUES (336, 12, 4, 57, 'Response connection', 1, 1, 'SUM', 17);
INSERT INTO `ipm_plot_option` VALUES (337, 12, 4, 58, 'Active close connection', 1, 1, 'SUM', 19);
INSERT INTO `ipm_plot_option` VALUES (338, 12, 4, 59, 'Passive close connection', 1, 1, 'SUM', 20);
INSERT INTO `ipm_plot_option` VALUES (339, 12, 3, 60, 'Downstream traffic', 1, 1, 'SUM', 24);
INSERT INTO `ipm_plot_option` VALUES (340, 12, 3, 61, 'Upstream traffic', 1, 1, 'SUM', 25);
INSERT INTO `ipm_plot_option` VALUES (341, 10, 6, 67, 'Downstream bandwidth utilization', 1, 1, 'AVG', 27);
INSERT INTO `ipm_plot_option` VALUES (342, 10, 4, 68, 'Connection unresponse Ratio', 1, 1, 'AVG', 27);
INSERT INTO `ipm_plot_option` VALUES (343, 11, 4, 68, 'Connection unresponse Ratio', 1, 1, 'AVG', 27);
INSERT INTO `ipm_plot_option` VALUES (344, 12, 4, 68, 'Connection unresponse Ratio', 1, 1, 'AVG', 27);
INSERT INTO `ipm_plot_option` VALUES (345, 10, 1, 2, 'KPI alarm proportion', 1, 0, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (346, 11, 1, 2, 'KPI alarm proportion', 1, 0, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (347, 12, 1, 2, 'KPI alarm proportion', 1, 0, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (348, 4, 1, 2, 'KPI alarm proportion', 1, 0, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (349, 5, 1, 2, 'KPI alarm proportion', 1, 0, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (350, 6, 1, 2, 'KPI alarm proportion', 1, 0, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (351, 7, 1, 2, 'KPI alarm proportion', 1, 0, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (352, 8, 1, 2, 'KPI alarm proportion', 1, 0, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (353, 9, 1, 2, 'KPI alarm proportion', 1, 0, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (354, 8, 0, 0, 'Business traffic distribution', 1, 0, 'SUM', 0);
INSERT INTO `ipm_plot_option` VALUES (355, 10, 4, 69, 'Connection reset rate', 1, 1, 'AVG', 28);
INSERT INTO `ipm_plot_option` VALUES (356, 10, 4, 70, 'Connection closed rate', 1, 1, 'AVG', 29);
INSERT INTO `ipm_plot_option` VALUES (357, 11, 4, 69, 'Connection reset rate', 1, 1, 'AVG', 28);
INSERT INTO `ipm_plot_option` VALUES (358, 11, 4, 70, 'Connection closed rate', 1, 1, 'AVG', 29);
INSERT INTO `ipm_plot_option` VALUES (359, 12, 4, 69, 'Connection reset rate', 1, 1, 'AVG', 28);
INSERT INTO `ipm_plot_option` VALUES (360, 12, 4, 70, 'Connection closed rate', 1, 1, 'AVG', 29);
-- 硬件信息配置    START 
INSERT INTO `ipm_plot_option` VALUES (1000, 0, 7, 100, 'CPU_US' , 1, 0, 'AVG', 1);
INSERT INTO `ipm_plot_option` VALUES (1001, 0, 7, 101, 'CPU_SY' , 1, 0, 'AVG', 2);
INSERT INTO `ipm_plot_option` VALUES (1002, 0, 7, 102, 'CPU_NI' , 1, 0, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (1003, 0, 7, 103, 'CPU_ID' , 1, 0, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (1004, 0, 7, 104, 'CPU_WA' , 1, 0, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (1005, 0, 7, 105, 'CPU_HI' , 1, 0, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (1006, 0, 7, 106, 'CPU_SI' , 1, 0, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (1007, 0, 7, 107, 'CPU_ST' , 1, 0, 'AVG', 8);
INSERT INTO `ipm_plot_option` VALUES (1008, 0, 7, 108, 'Total memory' , 1, 0, 'SUM', 9);
INSERT INTO `ipm_plot_option` VALUES (1009, 0, 7, 109, 'Used memory' , 1, 0, 'SUM', 10);
INSERT INTO `ipm_plot_option` VALUES (1010, 0, 7, 110, 'Free memory' , 1, 0, 'SUM', 11);
INSERT INTO `ipm_plot_option` VALUES (1011, 0, 7, 111, 'Kernel cache' , 1, 0, 'SUM', 12);
INSERT INTO `ipm_plot_option` VALUES (1012, 0, 7, 112, 'SWAPtotal' , 1, 0, 'SUM', 13);
INSERT INTO `ipm_plot_option` VALUES (1013, 0, 7, 113, 'Used SWAP' , 1, 0, 'SUM', 14);
INSERT INTO `ipm_plot_option` VALUES (1014, 0, 7, 114, 'Free SWAP' , 1, 0, 'SUM', 15);
INSERT INTO `ipm_plot_option` VALUES (1015, 0, 7, 115, 'Memory buffer' , 1, 0, 'SUM', 16);
INSERT INTO `ipm_plot_option` VALUES (1016, 0, 7, 116, 'IPC_CPU' , 1, 0, 'AVG', 17);
INSERT INTO `ipm_plot_option` VALUES (1017, 0, 7, 117, 'IPC_MEM' , 1, 0, 'AVG', 18);
INSERT INTO `ipm_plot_option` VALUES (1018, 0, 7, 118, 'BSS_CPU' , 1, 0, 'AVG', 19);
INSERT INTO `ipm_plot_option` VALUES (1019, 0, 7, 119, 'BSS_MEM' , 1, 0, 'AVG', 20);
INSERT INTO `ipm_plot_option` VALUES (1020, 0, 7, 120, 'COMM_CPU' , 1, 0, 'AVG', 21);
INSERT INTO `ipm_plot_option` VALUES (1021, 0, 7, 121, 'COMM_MEM' , 1, 0, 'AVG', 22);
INSERT INTO `ipm_plot_option` VALUES (1022, 0, 7, 122, 'DSAS_CPU' , 1, 0, 'AVG', 23);
INSERT INTO `ipm_plot_option` VALUES (1023, 0, 7, 123, 'DSAS_MEM' , 1, 0, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (1024, 0, 7, 124, 'COLL_CPU' , 1, 0, 'AVG', 25);
INSERT INTO `ipm_plot_option` VALUES (1025, 0, 7, 125, 'COLL_MEM' , 1, 0, 'AVG', 26);
INSERT INTO `ipm_plot_option` VALUES (1026, 0, 7, 126, 'JAVA_CPU' , 1, 0, 'AVG', 27);
INSERT INTO `ipm_plot_option` VALUES (1027, 0, 7, 127, 'JAVA_MEM' , 1, 0, 'AVG', 28);
INSERT INTO `ipm_plot_option` VALUES (1028, 0, 7, 128, 'MYSQL_CPU' , 1, 0, 'AVG', 29);
INSERT INTO `ipm_plot_option` VALUES (1029, 0, 7, 129, 'MYSQL_MEM' , 1, 0, 'AVG', 30);
INSERT INTO `ipm_plot_option` VALUES (1030, 0, 7, 130, 'RRD_CPU' , 1, 0, 'AVG', 31);
INSERT INTO `ipm_plot_option` VALUES (1031, 0, 7, 131, 'RRD_MEM' , 1, 0, 'AVG', 32);
INSERT INTO `ipm_plot_option` VALUES (1032, 0, 7, 132, 'One minute load' , 1, 0, 'SUM', 33);
INSERT INTO `ipm_plot_option` VALUES (1033, 0, 7, 133, 'Five minutes load' , 1, 0, 'SUM', 34);
INSERT INTO `ipm_plot_option` VALUES (1034, 0, 7, 134, 'Fifteen minutes load' , 1, 0, 'SUM', 35);
-- 硬件信息配置    END

-- --------------------
-- 绘图选项数据 END 
-- --------------------


-- --------------------
-- 绘图选项类型对应数据 START 
-- --------------------
INSERT INTO `ipm_plot_option_type` VALUES (1, 1, 1);
INSERT INTO `ipm_plot_option_type` VALUES (2, 2, 1);
INSERT INTO `ipm_plot_option_type` VALUES (3, 3, 1);
INSERT INTO `ipm_plot_option_type` VALUES (4, 4, 1);
INSERT INTO `ipm_plot_option_type` VALUES (5, 5, 1);
INSERT INTO `ipm_plot_option_type` VALUES (6, 6, 1);
INSERT INTO `ipm_plot_option_type` VALUES (7, 7, 1);
INSERT INTO `ipm_plot_option_type` VALUES (8, 8, 1);
INSERT INTO `ipm_plot_option_type` VALUES (9, 9, 1);
INSERT INTO `ipm_plot_option_type` VALUES (10, 10, 1);
INSERT INTO `ipm_plot_option_type` VALUES (11, 11, 1);
INSERT INTO `ipm_plot_option_type` VALUES (12, 12, 1);
INSERT INTO `ipm_plot_option_type` VALUES (15, 15, 1);
INSERT INTO `ipm_plot_option_type` VALUES (17, 17, 1);
INSERT INTO `ipm_plot_option_type` VALUES (19, 19, 1);
INSERT INTO `ipm_plot_option_type` VALUES (20, 20, 1);
INSERT INTO `ipm_plot_option_type` VALUES (21, 21, 1);
INSERT INTO `ipm_plot_option_type` VALUES (22, 22, 1);
INSERT INTO `ipm_plot_option_type` VALUES (23, 23, 1);
INSERT INTO `ipm_plot_option_type` VALUES (28, 28, 1);
INSERT INTO `ipm_plot_option_type` VALUES (29, 29, 1);
INSERT INTO `ipm_plot_option_type` VALUES (30, 30, 3);
INSERT INTO `ipm_plot_option_type` VALUES (31, 31, 3);
INSERT INTO `ipm_plot_option_type` VALUES (32, 32, 1);
INSERT INTO `ipm_plot_option_type` VALUES (33, 33, 1);
INSERT INTO `ipm_plot_option_type` VALUES (34, 34, 1);
INSERT INTO `ipm_plot_option_type` VALUES (35, 35, 1);
INSERT INTO `ipm_plot_option_type` VALUES (36, 36, 1);
INSERT INTO `ipm_plot_option_type` VALUES (37, 37, 1);
INSERT INTO `ipm_plot_option_type` VALUES (38, 38, 1);
INSERT INTO `ipm_plot_option_type` VALUES (39, 39, 1);
INSERT INTO `ipm_plot_option_type` VALUES (40, 40, 1);
INSERT INTO `ipm_plot_option_type` VALUES (41, 41, 1);
INSERT INTO `ipm_plot_option_type` VALUES (42, 42, 1);
INSERT INTO `ipm_plot_option_type` VALUES (43, 43, 1);
INSERT INTO `ipm_plot_option_type` VALUES (46, 46, 1);
INSERT INTO `ipm_plot_option_type` VALUES (48, 48, 1);
INSERT INTO `ipm_plot_option_type` VALUES (50, 50, 1);
INSERT INTO `ipm_plot_option_type` VALUES (51, 51, 1);
INSERT INTO `ipm_plot_option_type` VALUES (52, 52, 1);
INSERT INTO `ipm_plot_option_type` VALUES (53, 53, 1);
INSERT INTO `ipm_plot_option_type` VALUES (54, 54, 1);
INSERT INTO `ipm_plot_option_type` VALUES (58, 58, 1);
INSERT INTO `ipm_plot_option_type` VALUES (59, 59, 3);
INSERT INTO `ipm_plot_option_type` VALUES (60, 60, 1);
INSERT INTO `ipm_plot_option_type` VALUES (61, 61, 1);
INSERT INTO `ipm_plot_option_type` VALUES (62, 62, 1);
INSERT INTO `ipm_plot_option_type` VALUES (63, 63, 1);
INSERT INTO `ipm_plot_option_type` VALUES (64, 64, 1);
INSERT INTO `ipm_plot_option_type` VALUES (65, 65, 1);
INSERT INTO `ipm_plot_option_type` VALUES (66, 66, 1);
INSERT INTO `ipm_plot_option_type` VALUES (67, 67, 1);
INSERT INTO `ipm_plot_option_type` VALUES (68, 68, 1);
INSERT INTO `ipm_plot_option_type` VALUES (69, 69, 1);
INSERT INTO `ipm_plot_option_type` VALUES (70, 70, 1);
INSERT INTO `ipm_plot_option_type` VALUES (71, 71, 1);
INSERT INTO `ipm_plot_option_type` VALUES (74, 74, 1);
INSERT INTO `ipm_plot_option_type` VALUES (76, 76, 1);
INSERT INTO `ipm_plot_option_type` VALUES (78, 78, 1);
INSERT INTO `ipm_plot_option_type` VALUES (79, 79, 1);
INSERT INTO `ipm_plot_option_type` VALUES (80, 80, 1);
INSERT INTO `ipm_plot_option_type` VALUES (81, 81, 1);
INSERT INTO `ipm_plot_option_type` VALUES (82, 82, 1);
INSERT INTO `ipm_plot_option_type` VALUES (84, 84, 1);
INSERT INTO `ipm_plot_option_type` VALUES (85, 85, 1);
INSERT INTO `ipm_plot_option_type` VALUES (86, 86, 2);
INSERT INTO `ipm_plot_option_type` VALUES (87, 87, 2);
INSERT INTO `ipm_plot_option_type` VALUES (88, 88, 2);
INSERT INTO `ipm_plot_option_type` VALUES (89, 89, 3);
INSERT INTO `ipm_plot_option_type` VALUES (90, 90, 1);
INSERT INTO `ipm_plot_option_type` VALUES (94, 94, 1);
INSERT INTO `ipm_plot_option_type` VALUES (97, 97, 1);
INSERT INTO `ipm_plot_option_type` VALUES (101, 101, 3);
INSERT INTO `ipm_plot_option_type` VALUES (102, 102, 1);
INSERT INTO `ipm_plot_option_type` VALUES (105, 105, 1);
INSERT INTO `ipm_plot_option_type` VALUES (106, 106, 1);
INSERT INTO `ipm_plot_option_type` VALUES (107, 107, 1);
INSERT INTO `ipm_plot_option_type` VALUES (110, 110, 1);
INSERT INTO `ipm_plot_option_type` VALUES (131, 131, 1);
INSERT INTO `ipm_plot_option_type` VALUES (132, 132, 1);
INSERT INTO `ipm_plot_option_type` VALUES (133, 133, 1);
INSERT INTO `ipm_plot_option_type` VALUES (134, 134, 1);
INSERT INTO `ipm_plot_option_type` VALUES (135, 135, 3);
INSERT INTO `ipm_plot_option_type` VALUES (136, 136, 1);
INSERT INTO `ipm_plot_option_type` VALUES (139, 139, 1);
INSERT INTO `ipm_plot_option_type` VALUES (140, 140, 1);
INSERT INTO `ipm_plot_option_type` VALUES (141, 141, 1);
INSERT INTO `ipm_plot_option_type` VALUES (144, 144, 1);
INSERT INTO `ipm_plot_option_type` VALUES (158, 158, 1);
INSERT INTO `ipm_plot_option_type` VALUES (165, 165, 1);
INSERT INTO `ipm_plot_option_type` VALUES (168, 168, 1);
INSERT INTO `ipm_plot_option_type` VALUES (169, 169, 3);
INSERT INTO `ipm_plot_option_type` VALUES (170, 170, 1);
INSERT INTO `ipm_plot_option_type` VALUES (173, 173, 1);
INSERT INTO `ipm_plot_option_type` VALUES (174, 174, 1);
INSERT INTO `ipm_plot_option_type` VALUES (175, 175, 1);
INSERT INTO `ipm_plot_option_type` VALUES (178, 178, 1);
INSERT INTO `ipm_plot_option_type` VALUES (192, 192, 1);
INSERT INTO `ipm_plot_option_type` VALUES (199, 199, 1);
INSERT INTO `ipm_plot_option_type` VALUES (202, 202, 1);
INSERT INTO `ipm_plot_option_type` VALUES (203, 203, 3);
INSERT INTO `ipm_plot_option_type` VALUES (204, 204, 1);
INSERT INTO `ipm_plot_option_type` VALUES (207, 207, 1);
INSERT INTO `ipm_plot_option_type` VALUES (208, 208, 1);
INSERT INTO `ipm_plot_option_type` VALUES (209, 209, 1);
INSERT INTO `ipm_plot_option_type` VALUES (212, 212, 1);
INSERT INTO `ipm_plot_option_type` VALUES (226, 226, 1);
INSERT INTO `ipm_plot_option_type` VALUES (233, 233, 1);
INSERT INTO `ipm_plot_option_type` VALUES (236, 236, 1);
INSERT INTO `ipm_plot_option_type` VALUES (237, 237, 2);
INSERT INTO `ipm_plot_option_type` VALUES (238, 238, 2);
INSERT INTO `ipm_plot_option_type` VALUES (239, 239, 2);
INSERT INTO `ipm_plot_option_type` VALUES (240, 240, 2);
INSERT INTO `ipm_plot_option_type` VALUES (241, 241, 2);
INSERT INTO `ipm_plot_option_type` VALUES (242, 242, 2);
INSERT INTO `ipm_plot_option_type` VALUES (243, 243, 2);
INSERT INTO `ipm_plot_option_type` VALUES (244, 244, 2);
INSERT INTO `ipm_plot_option_type` VALUES (245, 245, 2);
INSERT INTO `ipm_plot_option_type` VALUES (246, 246, 2);
INSERT INTO `ipm_plot_option_type` VALUES (267, 267, 1);
INSERT INTO `ipm_plot_option_type` VALUES (268, 268, 1);
INSERT INTO `ipm_plot_option_type` VALUES (269, 269, 1);
INSERT INTO `ipm_plot_option_type` VALUES (270, 270, 1);
INSERT INTO `ipm_plot_option_type` VALUES (271, 271, 1);
INSERT INTO `ipm_plot_option_type` VALUES (272, 272, 1);
INSERT INTO `ipm_plot_option_type` VALUES (273, 273, 1);
INSERT INTO `ipm_plot_option_type` VALUES (274, 274, 1);
INSERT INTO `ipm_plot_option_type` VALUES (275, 275, 1);
INSERT INTO `ipm_plot_option_type` VALUES (276, 276, 1);
INSERT INTO `ipm_plot_option_type` VALUES (277, 277, 1);
INSERT INTO `ipm_plot_option_type` VALUES (278, 278, 1);
INSERT INTO `ipm_plot_option_type` VALUES (279, 279, 1);
INSERT INTO `ipm_plot_option_type` VALUES (280, 280, 1);
INSERT INTO `ipm_plot_option_type` VALUES (281, 281, 1);
INSERT INTO `ipm_plot_option_type` VALUES (282, 282, 1);
INSERT INTO `ipm_plot_option_type` VALUES (283, 283, 1);
INSERT INTO `ipm_plot_option_type` VALUES (284, 284, 1);
INSERT INTO `ipm_plot_option_type` VALUES (285, 285, 1);
INSERT INTO `ipm_plot_option_type` VALUES (286, 286, 1);
INSERT INTO `ipm_plot_option_type` VALUES (287, 287, 1);
INSERT INTO `ipm_plot_option_type` VALUES (288, 288, 1);
INSERT INTO `ipm_plot_option_type` VALUES (289, 289, 1);
INSERT INTO `ipm_plot_option_type` VALUES (290, 290, 1);
INSERT INTO `ipm_plot_option_type` VALUES (291, 291, 1);
INSERT INTO `ipm_plot_option_type` VALUES (292, 292, 1);
INSERT INTO `ipm_plot_option_type` VALUES (293, 293, 1);
INSERT INTO `ipm_plot_option_type` VALUES (294, 294, 1);
INSERT INTO `ipm_plot_option_type` VALUES (295, 295, 1);
INSERT INTO `ipm_plot_option_type` VALUES (296, 296, 1);
INSERT INTO `ipm_plot_option_type` VALUES (297, 297, 1);
INSERT INTO `ipm_plot_option_type` VALUES (298, 298, 1);
INSERT INTO `ipm_plot_option_type` VALUES (299, 299, 1);
INSERT INTO `ipm_plot_option_type` VALUES (300, 300, 1);
INSERT INTO `ipm_plot_option_type` VALUES (301, 301, 3);
INSERT INTO `ipm_plot_option_type` VALUES (302, 302, 3);
INSERT INTO `ipm_plot_option_type` VALUES (303, 303, 4);
INSERT INTO `ipm_plot_option_type` VALUES (304, 304, 1);
INSERT INTO `ipm_plot_option_type` VALUES (305, 305, 1);
INSERT INTO `ipm_plot_option_type` VALUES (306, 306, 4);
INSERT INTO `ipm_plot_option_type` VALUES (307, 307, 4);
INSERT INTO `ipm_plot_option_type` VALUES (308, 308, 5);
INSERT INTO `ipm_plot_option_type` VALUES (309, 309, 5);
INSERT INTO `ipm_plot_option_type` VALUES (310, 310, 5);
INSERT INTO `ipm_plot_option_type` VALUES (311, 311, 5);
INSERT INTO `ipm_plot_option_type` VALUES (312, 312, 5);
INSERT INTO `ipm_plot_option_type` VALUES (313, 313, 5);
INSERT INTO `ipm_plot_option_type` VALUES (314, 314, 5);
INSERT INTO `ipm_plot_option_type` VALUES (315, 315, 5);
INSERT INTO `ipm_plot_option_type` VALUES (316, 316, 5);
INSERT INTO `ipm_plot_option_type` VALUES (317, 317, 1);
INSERT INTO `ipm_plot_option_type` VALUES (318, 318, 1);
INSERT INTO `ipm_plot_option_type` VALUES (319, 319, 1);
INSERT INTO `ipm_plot_option_type` VALUES (320, 320, 1);
INSERT INTO `ipm_plot_option_type` VALUES (321, 321, 6);
INSERT INTO `ipm_plot_option_type` VALUES (322, 322, 1);
INSERT INTO `ipm_plot_option_type` VALUES (323, 323, 1);
INSERT INTO `ipm_plot_option_type` VALUES (324, 324, 1);
INSERT INTO `ipm_plot_option_type` VALUES (325, 325, 1);
INSERT INTO `ipm_plot_option_type` VALUES (326, 326, 1);
INSERT INTO `ipm_plot_option_type` VALUES (327, 327, 1);
INSERT INTO `ipm_plot_option_type` VALUES (328, 328, 1);
INSERT INTO `ipm_plot_option_type` VALUES (329, 329, 1);
INSERT INTO `ipm_plot_option_type` VALUES (330, 330, 1);
INSERT INTO `ipm_plot_option_type` VALUES (331, 331, 1);
INSERT INTO `ipm_plot_option_type` VALUES (332, 332, 1);
INSERT INTO `ipm_plot_option_type` VALUES (333, 333, 1);
INSERT INTO `ipm_plot_option_type` VALUES (334, 334, 1);
INSERT INTO `ipm_plot_option_type` VALUES (335, 335, 1);
INSERT INTO `ipm_plot_option_type` VALUES (336, 336, 1);
INSERT INTO `ipm_plot_option_type` VALUES (337, 337, 1);
INSERT INTO `ipm_plot_option_type` VALUES (338, 338, 1);
INSERT INTO `ipm_plot_option_type` VALUES (339, 339, 1);
INSERT INTO `ipm_plot_option_type` VALUES (340, 340, 1);
INSERT INTO `ipm_plot_option_type` VALUES (341, 341, 1);
INSERT INTO `ipm_plot_option_type` VALUES (342, 342, 1);
INSERT INTO `ipm_plot_option_type` VALUES (343, 343, 1);
INSERT INTO `ipm_plot_option_type` VALUES (344, 344, 1);
INSERT INTO `ipm_plot_option_type` VALUES (345, 345, 3);
INSERT INTO `ipm_plot_option_type` VALUES (346, 346, 3);
INSERT INTO `ipm_plot_option_type` VALUES (347, 347, 3);
INSERT INTO `ipm_plot_option_type` VALUES (348, 348, 3);
INSERT INTO `ipm_plot_option_type` VALUES (349, 349, 3);
INSERT INTO `ipm_plot_option_type` VALUES (350, 350, 3);
INSERT INTO `ipm_plot_option_type` VALUES (351, 351, 3);
INSERT INTO `ipm_plot_option_type` VALUES (352, 352, 3);
INSERT INTO `ipm_plot_option_type` VALUES (353, 353, 3);
INSERT INTO `ipm_plot_option_type` VALUES (354, 354, 3);
INSERT INTO `ipm_plot_option_type` VALUES (355, 355, 1);
INSERT INTO `ipm_plot_option_type` VALUES (356, 356, 1);
INSERT INTO `ipm_plot_option_type` VALUES (357, 357, 1);
INSERT INTO `ipm_plot_option_type` VALUES (358, 358, 1);
INSERT INTO `ipm_plot_option_type` VALUES (359, 359, 1);
INSERT INTO `ipm_plot_option_type` VALUES (360, 360, 1);
-- 硬件信息配置    START
INSERT INTO `ipm_plot_option_type` VALUES (1000, 1000, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1001, 1001, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1002, 1002, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1003, 1003, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1004, 1004, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1005, 1005, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1006, 1006, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1007, 1007, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1008, 1008, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1009, 1009, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1010, 1010, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1011, 1011, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1012, 1012, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1013, 1013, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1014, 1014, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1015, 1015, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1016, 1016, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1017, 1017, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1018, 1018, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1019, 1019, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1020, 1020, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1021, 1021, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1022, 1022, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1023, 1023, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1024, 1024, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1025, 1025, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1026, 1026, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1027, 1027, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1028, 1028, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1029, 1029, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1030, 1030, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1031, 1031, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1032, 1032, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1033, 1033, 1);
INSERT INTO `ipm_plot_option_type` VALUES (1034, 1034, 1);
-- 硬件信息配置    END

-- --------------------
-- 绘图选项类型对应数据 END
-- --------------------


-- --------------------
-- 绘图类型数据 START 
-- --------------------
INSERT INTO `ipm_plot_type` VALUES (1, 'line', 'diagram');
INSERT INTO `ipm_plot_type` VALUES (2, 'bar', 'Horizontal bar');
INSERT INTO `ipm_plot_type` VALUES (3, 'pie', 'Pie chart');
INSERT INTO `ipm_plot_type` VALUES (4, 'column', 'Longitudinal bar');
INSERT INTO `ipm_plot_type` VALUES (5, 'heatmap', 'Healthy figure');
INSERT INTO `ipm_plot_type` VALUES (6, 'multimarkline', 'Multicoordinate diagram');
-- --------------------
-- 绘图类型数据 END 
-- --------------------


-- --------------------
-- 绘图分组类型数据 START 
-- --------------------
INSERT INTO `ipm_plot_group_type` VALUES (-1, 'Module Unit');
update `ipm_plot_group_type` set id = 0 where id = -1;
INSERT INTO `ipm_plot_group_type` VALUES (1, 'Alarm Unit');
INSERT INTO `ipm_plot_group_type` VALUES (2, 'Time Unit');
INSERT INTO `ipm_plot_group_type` VALUES (3, 'Traffic Unit');
INSERT INTO `ipm_plot_group_type` VALUES (4, 'Session Unit');
INSERT INTO `ipm_plot_group_type` VALUES (5, 'Packet Unit');
INSERT INTO `ipm_plot_group_type` VALUES (6, 'Others');
INSERT INTO `ipm_plot_group_type` VALUES (7, 'Hardware Unit');
-- --------------------
-- 绘图分组类型数据 END 
-- --------------------


-- --------------------
-- 小模块数据 START 
-- --------------------
INSERT INTO `ipm_small_module` VALUES (1, 12, 'Server setting');
INSERT INTO `ipm_small_module` VALUES (2, 0, 'System Time setting');
INSERT INTO `ipm_small_module` VALUES (3, 0, 'Data storage Setting');
INSERT INTO `ipm_small_module` VALUES (4, 0, 'User management');
INSERT INTO `ipm_small_module` VALUES (5, 10, 'Watchpoint setting'); 
INSERT INTO `ipm_small_module` VALUES (6, 0, 'User configuration setting');
INSERT INTO `ipm_small_module` VALUES (7, 11, 'Client setting');
INSERT INTO `ipm_small_module` VALUES (8, 0, 'Cockpit setting');
INSERT INTO `ipm_small_module` VALUES (9, 0, 'Original Flow download');
INSERT INTO `ipm_small_module` VALUES (10, 0, 'Alarm');
INSERT INTO `ipm_small_module` VALUES (11, 0, 'System settin');
INSERT INTO `ipm_small_module` VALUES (12, 1, 'Application availability Setting');
INSERT INTO `ipm_small_module` VALUES (13, 8, 'URL service');
INSERT INTO `ipm_small_module` VALUES (14, 9, 'Message service');
INSERT INTO `ipm_small_module` VALUES (15, 4, 'HTTP service');
INSERT INTO `ipm_small_module` VALUES (16, 5, 'ORACLE service');
INSERT INTO `ipm_small_module` VALUES (17, 6, 'MYSQL service');
INSERT INTO `ipm_small_module` VALUES (18, 7, 'SQLSERVER service');
INSERT INTO `ipm_small_module` VALUES (19, 0, 'Report');
-- --------------------
-- 小模块数据 END 
-- --------------------


-- --------------------
-- Alarm class型数据 START 
-- --------------------
INSERT INTO `ipm_alarm_type` VALUES (1,'BaseLineThreshold','Baseline');
INSERT INTO `ipm_alarm_type` VALUES (2,'CustomThreshold','Custom alarms');
INSERT INTO `ipm_alarm_type` VALUES (3,'AppAvailability','Application availability');
-- --------------------
-- Alarm class型数据 END 
-- --------------------


-- --------------------
-- 告警级别数据 START 
-- --------------------
INSERT INTO `ipm_alarm_level` VALUES (1,1,'DEVIATE','Intelligent');
INSERT INTO `ipm_alarm_level` VALUES (2,2,'NORMAL','Normal');
INSERT INTO `ipm_alarm_level` VALUES (3,2,'IMPORTANT','Important');
INSERT INTO `ipm_alarm_level` VALUES (4,2,'URGENT','Emergency');
-- INSERT INTO `ipm_alarm_level` VALUES (5,3,'APPAVAILA','应用不可用告警');
-- --------------------
-- 告警级别数据 END 
-- --------------------


-- --------------------
-- 告警KPI数据 START 
-- --------------------
INSERT INTO `ipm_alarm_kpi` VALUES (1, 10, 1);
INSERT INTO `ipm_alarm_kpi` VALUES (2, 10, 2);
INSERT INTO `ipm_alarm_kpi` VALUES (3, 10, 3);
INSERT INTO `ipm_alarm_kpi` VALUES (4, 10, 4);
INSERT INTO `ipm_alarm_kpi` VALUES (5, 10, 5);
INSERT INTO `ipm_alarm_kpi` VALUES (6, 10, 6);
INSERT INTO `ipm_alarm_kpi` VALUES (7, 10, 7);
INSERT INTO `ipm_alarm_kpi` VALUES (8, 10, 8);
INSERT INTO `ipm_alarm_kpi` VALUES (9, 10, 9);
INSERT INTO `ipm_alarm_kpi` VALUES (10, 10, 10);
INSERT INTO `ipm_alarm_kpi` VALUES (11, 10, 11);
INSERT INTO `ipm_alarm_kpi` VALUES (12, 10, 12);
INSERT INTO `ipm_alarm_kpi` VALUES (13, 10, 13);
INSERT INTO `ipm_alarm_kpi` VALUES (14, 10, 14);
INSERT INTO `ipm_alarm_kpi` VALUES (15, 10, 15);
INSERT INTO `ipm_alarm_kpi` VALUES (16, 10, 16);
INSERT INTO `ipm_alarm_kpi` VALUES (17, 10, 17);
INSERT INTO `ipm_alarm_kpi` VALUES (18, 10, 18);
INSERT INTO `ipm_alarm_kpi` VALUES (19, 10, 19);
INSERT INTO `ipm_alarm_kpi` VALUES (20, 10, 20);
INSERT INTO `ipm_alarm_kpi` VALUES (21, 10, 21);
INSERT INTO `ipm_alarm_kpi` VALUES (22, 10, 22);
INSERT INTO `ipm_alarm_kpi` VALUES (23, 10, 23);
INSERT INTO `ipm_alarm_kpi` VALUES (24, 10, 24);
INSERT INTO `ipm_alarm_kpi` VALUES (25, 10, 25);
INSERT INTO `ipm_alarm_kpi` VALUES (26, 10, 26);
INSERT INTO `ipm_alarm_kpi` VALUES (27, 10, 28);
INSERT INTO `ipm_alarm_kpi` VALUES (28, 10, 29);
INSERT INTO `ipm_alarm_kpi` VALUES (29, 10, 30);
INSERT INTO `ipm_alarm_kpi` VALUES (30, 10, 31);
-- INSERT INTO `ipm_alarm_kpi` VALUES (31, 10, 32);
INSERT INTO `ipm_alarm_kpi` VALUES (32, 10, 39);
-- INSERT INTO `ipm_alarm_kpi` VALUES (33, 10, 40);
-- INSERT INTO `ipm_alarm_kpi` VALUES (34, 10, 41);
-- INSERT INTO `ipm_alarm_kpi` VALUES (35, 10, 42);
INSERT INTO `ipm_alarm_kpi` VALUES (36, 10, 43);
INSERT INTO `ipm_alarm_kpi` VALUES (37, 10, 44);
INSERT INTO `ipm_alarm_kpi` VALUES (38, 10, 50);
INSERT INTO `ipm_alarm_kpi` VALUES (39, 10, 51);

INSERT INTO `ipm_alarm_kpi` VALUES (40, 11, 1);
INSERT INTO `ipm_alarm_kpi` VALUES (41, 11, 2);
INSERT INTO `ipm_alarm_kpi` VALUES (42, 11, 3);
INSERT INTO `ipm_alarm_kpi` VALUES (43, 11, 4);
INSERT INTO `ipm_alarm_kpi` VALUES (44, 11, 5);
INSERT INTO `ipm_alarm_kpi` VALUES (45, 11, 6);
INSERT INTO `ipm_alarm_kpi` VALUES (46, 11, 7);
INSERT INTO `ipm_alarm_kpi` VALUES (47, 11, 8);
INSERT INTO `ipm_alarm_kpi` VALUES (48, 11, 9);
INSERT INTO `ipm_alarm_kpi` VALUES (49, 11, 10);
INSERT INTO `ipm_alarm_kpi` VALUES (50, 11, 11);
INSERT INTO `ipm_alarm_kpi` VALUES (51, 11, 12);
INSERT INTO `ipm_alarm_kpi` VALUES (52, 11, 13);
INSERT INTO `ipm_alarm_kpi` VALUES (53, 11, 14);
INSERT INTO `ipm_alarm_kpi` VALUES (54, 11, 15);
INSERT INTO `ipm_alarm_kpi` VALUES (55, 11, 16);
INSERT INTO `ipm_alarm_kpi` VALUES (56, 11, 17);
INSERT INTO `ipm_alarm_kpi` VALUES (57, 11, 18);
INSERT INTO `ipm_alarm_kpi` VALUES (58, 11, 19);
INSERT INTO `ipm_alarm_kpi` VALUES (59, 11, 20);
INSERT INTO `ipm_alarm_kpi` VALUES (60, 11, 21);
INSERT INTO `ipm_alarm_kpi` VALUES (61, 11, 22);
INSERT INTO `ipm_alarm_kpi` VALUES (62, 11, 23);
INSERT INTO `ipm_alarm_kpi` VALUES (63, 11, 24);
INSERT INTO `ipm_alarm_kpi` VALUES (64, 11, 25);
INSERT INTO `ipm_alarm_kpi` VALUES (65, 11, 26);
INSERT INTO `ipm_alarm_kpi` VALUES (66, 11, 28);
INSERT INTO `ipm_alarm_kpi` VALUES (67, 11, 30);
INSERT INTO `ipm_alarm_kpi` VALUES (68, 11, 31);
INSERT INTO `ipm_alarm_kpi` VALUES (69, 11, 32);
INSERT INTO `ipm_alarm_kpi` VALUES (70, 11, 39);
-- INSERT INTO `ipm_alarm_kpi` VALUES (71, 11, 40);
-- INSERT INTO `ipm_alarm_kpi` VALUES (72, 11, 41);
-- INSERT INTO `ipm_alarm_kpi` VALUES (73, 11, 42);
INSERT INTO `ipm_alarm_kpi` VALUES (74, 11, 43);
INSERT INTO `ipm_alarm_kpi` VALUES (75, 11, 44);
INSERT INTO `ipm_alarm_kpi` VALUES (76, 11, 50);
INSERT INTO `ipm_alarm_kpi` VALUES (77, 11, 51);

INSERT INTO `ipm_alarm_kpi` VALUES (80, 12, 1);
INSERT INTO `ipm_alarm_kpi` VALUES (81, 12, 2);
INSERT INTO `ipm_alarm_kpi` VALUES (82, 12, 3);
INSERT INTO `ipm_alarm_kpi` VALUES (83, 12, 4);
INSERT INTO `ipm_alarm_kpi` VALUES (84, 12, 5);
INSERT INTO `ipm_alarm_kpi` VALUES (85, 12, 6);
INSERT INTO `ipm_alarm_kpi` VALUES (86, 12, 7);
INSERT INTO `ipm_alarm_kpi` VALUES (87, 12, 8);
INSERT INTO `ipm_alarm_kpi` VALUES (88, 12, 9);
INSERT INTO `ipm_alarm_kpi` VALUES (89, 12, 10);
INSERT INTO `ipm_alarm_kpi` VALUES (90, 12, 11);
INSERT INTO `ipm_alarm_kpi` VALUES (91, 12, 12);
INSERT INTO `ipm_alarm_kpi` VALUES (92, 12, 13);
INSERT INTO `ipm_alarm_kpi` VALUES (93, 12, 14);
INSERT INTO `ipm_alarm_kpi` VALUES (94, 12, 15);
INSERT INTO `ipm_alarm_kpi` VALUES (95, 12, 16);
INSERT INTO `ipm_alarm_kpi` VALUES (96, 12, 17);
INSERT INTO `ipm_alarm_kpi` VALUES (97, 12, 18);
INSERT INTO `ipm_alarm_kpi` VALUES (98, 12, 19);
INSERT INTO `ipm_alarm_kpi` VALUES (99, 12, 20);
INSERT INTO `ipm_alarm_kpi` VALUES (100, 12, 21);
INSERT INTO `ipm_alarm_kpi` VALUES (101, 12, 22);
INSERT INTO `ipm_alarm_kpi` VALUES (102, 12, 23);
INSERT INTO `ipm_alarm_kpi` VALUES (103, 12, 24);
INSERT INTO `ipm_alarm_kpi` VALUES (104, 12, 25);
INSERT INTO `ipm_alarm_kpi` VALUES (105, 12, 26);
INSERT INTO `ipm_alarm_kpi` VALUES (106, 12, 29);
INSERT INTO `ipm_alarm_kpi` VALUES (107, 12, 30);
INSERT INTO `ipm_alarm_kpi` VALUES (108, 12, 31);
INSERT INTO `ipm_alarm_kpi` VALUES (109, 12, 32);
INSERT INTO `ipm_alarm_kpi` VALUES (110, 12, 39);
-- INSERT INTO `ipm_alarm_kpi` VALUES (111, 12, 40);
-- INSERT INTO `ipm_alarm_kpi` VALUES (112, 12, 41);
-- INSERT INTO `ipm_alarm_kpi` VALUES (113, 12, 42);
INSERT INTO `ipm_alarm_kpi` VALUES (114, 12, 43);
INSERT INTO `ipm_alarm_kpi` VALUES (115, 12, 44);
INSERT INTO `ipm_alarm_kpi` VALUES (116, 12, 50);
INSERT INTO `ipm_alarm_kpi` VALUES (117, 12, 51);

INSERT INTO `ipm_alarm_kpi` VALUES (120, 4, 1);
INSERT INTO `ipm_alarm_kpi` VALUES (121, 4, 4);
INSERT INTO `ipm_alarm_kpi` VALUES (122, 4, 5);
INSERT INTO `ipm_alarm_kpi` VALUES (123, 4, 6);
INSERT INTO `ipm_alarm_kpi` VALUES (124, 4, 9);
INSERT INTO `ipm_alarm_kpi` VALUES (125, 4, 35);
INSERT INTO `ipm_alarm_kpi` VALUES (126, 4, 63);
INSERT INTO `ipm_alarm_kpi` VALUES (127, 4, 37);
INSERT INTO `ipm_alarm_kpi` VALUES (128, 4, 38);
INSERT INTO `ipm_alarm_kpi` VALUES (129, 4, 45);
INSERT INTO `ipm_alarm_kpi` VALUES (130, 4, 46);
INSERT INTO `ipm_alarm_kpi` VALUES (131, 4, 47);

INSERT INTO `ipm_alarm_kpi` VALUES (140, 5, 1);
INSERT INTO `ipm_alarm_kpi` VALUES (141, 5, 4);
INSERT INTO `ipm_alarm_kpi` VALUES (142, 5, 5);
INSERT INTO `ipm_alarm_kpi` VALUES (143, 5, 6);
INSERT INTO `ipm_alarm_kpi` VALUES (144, 5, 9);
INSERT INTO `ipm_alarm_kpi` VALUES (145, 5, 63);
INSERT INTO `ipm_alarm_kpi` VALUES (146, 5, 35);
INSERT INTO `ipm_alarm_kpi` VALUES (147, 5, 38);
INSERT INTO `ipm_alarm_kpi` VALUES (148, 5, 45);
INSERT INTO `ipm_alarm_kpi` VALUES (149, 5, 46);
INSERT INTO `ipm_alarm_kpi` VALUES (150, 5, 47);

INSERT INTO `ipm_alarm_kpi` VALUES (160, 6, 1);
INSERT INTO `ipm_alarm_kpi` VALUES (161, 6, 4);
INSERT INTO `ipm_alarm_kpi` VALUES (162, 6, 5);
INSERT INTO `ipm_alarm_kpi` VALUES (163, 6, 6);
INSERT INTO `ipm_alarm_kpi` VALUES (164, 6, 9);
INSERT INTO `ipm_alarm_kpi` VALUES (165, 6, 63);
INSERT INTO `ipm_alarm_kpi` VALUES (166, 6, 35);
INSERT INTO `ipm_alarm_kpi` VALUES (167, 6, 38);
INSERT INTO `ipm_alarm_kpi` VALUES (168, 6, 45);
INSERT INTO `ipm_alarm_kpi` VALUES (169, 6, 46);
INSERT INTO `ipm_alarm_kpi` VALUES (170, 6, 47);

INSERT INTO `ipm_alarm_kpi` VALUES (180, 7, 1);
INSERT INTO `ipm_alarm_kpi` VALUES (181, 7, 4);
INSERT INTO `ipm_alarm_kpi` VALUES (182, 7, 5);
INSERT INTO `ipm_alarm_kpi` VALUES (183, 7, 6);
INSERT INTO `ipm_alarm_kpi` VALUES (184, 7, 9);
INSERT INTO `ipm_alarm_kpi` VALUES (185, 7, 63);
INSERT INTO `ipm_alarm_kpi` VALUES (186, 7, 35);
INSERT INTO `ipm_alarm_kpi` VALUES (187, 7, 38);
INSERT INTO `ipm_alarm_kpi` VALUES (188, 7, 45);
INSERT INTO `ipm_alarm_kpi` VALUES (189, 7, 46);
INSERT INTO `ipm_alarm_kpi` VALUES (190, 7, 47);

INSERT INTO `ipm_alarm_kpi` VALUES (210, 8, 1);
INSERT INTO `ipm_alarm_kpi` VALUES (211, 8, 2);
INSERT INTO `ipm_alarm_kpi` VALUES (212, 8, 6);
INSERT INTO `ipm_alarm_kpi` VALUES (213, 8, 36);
INSERT INTO `ipm_alarm_kpi` VALUES (214, 8, 37);
INSERT INTO `ipm_alarm_kpi` VALUES (215, 8, 38);
INSERT INTO `ipm_alarm_kpi` VALUES (216, 8, 45);
INSERT INTO `ipm_alarm_kpi` VALUES (217, 8, 48);

INSERT INTO `ipm_alarm_kpi` VALUES (300, 1, 49);
INSERT INTO `ipm_alarm_kpi` VALUES (301, 1, 90);
INSERT INTO `ipm_alarm_kpi` VALUES (302, 1, 91);

INSERT INTO `ipm_alarm_kpi` VALUES (350, 9, 52);
INSERT INTO `ipm_alarm_kpi` VALUES (351, 9, 53);
INSERT INTO `ipm_alarm_kpi` VALUES (352, 9, 54);
INSERT INTO `ipm_alarm_kpi` VALUES (353, 9, 55);

INSERT INTO `ipm_alarm_kpi` VALUES (354, 10, 36);
INSERT INTO `ipm_alarm_kpi` VALUES (355, 11, 36);
INSERT INTO `ipm_alarm_kpi` VALUES (356, 12, 36);

-- INSERT INTO `ipm_alarm_kpi` VALUES (600, 2, 100);
-- INSERT INTO `ipm_alarm_kpi` VALUES (601, 2, 101);
-- INSERT INTO `ipm_alarm_kpi` VALUES (602, 2, 102);
-- INSERT INTO `ipm_alarm_kpi` VALUES (603, 2, 103);
-- INSERT INTO `ipm_alarm_kpi` VALUES (604, 2, 104);
-- INSERT INTO `ipm_alarm_kpi` VALUES (605, 2, 105);
-- INSERT INTO `ipm_alarm_kpi` VALUES (606, 2, 106);
-- INSERT INTO `ipm_alarm_kpi` VALUES (607, 2, 107);
-- INSERT INTO `ipm_alarm_kpi` VALUES (608, 2, 108);
-- INSERT INTO `ipm_alarm_kpi` VALUES (609, 2, 109);
-- INSERT INTO `ipm_alarm_kpi` VALUES (610, 2, 110);
-- INSERT INTO `ipm_alarm_kpi` VALUES (611, 2, 111);
-- INSERT INTO `ipm_alarm_kpi` VALUES (612, 2, 112);
-- INSERT INTO `ipm_alarm_kpi` VALUES (613, 2, 113);
-- INSERT INTO `ipm_alarm_kpi` VALUES (614, 2, 114);
-- INSERT INTO `ipm_alarm_kpi` VALUES (615, 2, 115);
-- INSERT INTO `ipm_alarm_kpi` VALUES (616, 2, 116);
-- INSERT INTO `ipm_alarm_kpi` VALUES (617, 2, 117);
-- INSERT INTO `ipm_alarm_kpi` VALUES (618, 2, 118);
-- INSERT INTO `ipm_alarm_kpi` VALUES (619, 2, 119);
-- INSERT INTO `ipm_alarm_kpi` VALUES (620, 2, 120);
-- INSERT INTO `ipm_alarm_kpi` VALUES (621, 2, 121);
-- INSERT INTO `ipm_alarm_kpi` VALUES (622, 2, 122);
-- INSERT INTO `ipm_alarm_kpi` VALUES (623, 2, 123);
-- INSERT INTO `ipm_alarm_kpi` VALUES (624, 2, 124);
-- INSERT INTO `ipm_alarm_kpi` VALUES (625, 2, 125);
-- INSERT INTO `ipm_alarm_kpi` VALUES (626, 2, 126);
-- INSERT INTO `ipm_alarm_kpi` VALUES (627, 2, 127);
-- INSERT INTO `ipm_alarm_kpi` VALUES (628, 2, 128);
-- INSERT INTO `ipm_alarm_kpi` VALUES (629, 2, 129);
-- INSERT INTO `ipm_alarm_kpi` VALUES (630, 2, 130);
-- INSERT INTO `ipm_alarm_kpi` VALUES (631, 2, 131);
INSERT INTO `ipm_alarm_kpi` VALUES (632, 2, 132);
-- INSERT INTO `ipm_alarm_kpi` VALUES (633, 2, 133);
-- INSERT INTO `ipm_alarm_kpi` VALUES (634, 2, 134);


INSERT INTO `ipm_alarm_kpi` VALUES (700, 10, 57);
INSERT INTO `ipm_alarm_kpi` VALUES (701, 10, 58);
INSERT INTO `ipm_alarm_kpi` VALUES (702, 10, 59);
INSERT INTO `ipm_alarm_kpi` VALUES (703, 10, 60);
INSERT INTO `ipm_alarm_kpi` VALUES (704, 10, 61);
INSERT INTO `ipm_alarm_kpi` VALUES (705, 10, 66);
INSERT INTO `ipm_alarm_kpi` VALUES (706, 10, 67);
INSERT INTO `ipm_alarm_kpi` VALUES (707, 10, 68);
INSERT INTO `ipm_alarm_kpi` VALUES (708, 10, 69);
INSERT INTO `ipm_alarm_kpi` VALUES (709, 10, 70);

INSERT INTO `ipm_alarm_kpi` VALUES (710, 11, 57);
INSERT INTO `ipm_alarm_kpi` VALUES (711, 11, 58);
INSERT INTO `ipm_alarm_kpi` VALUES (712, 11, 59);
INSERT INTO `ipm_alarm_kpi` VALUES (713, 11, 60);
INSERT INTO `ipm_alarm_kpi` VALUES (714, 11, 61);
INSERT INTO `ipm_alarm_kpi` VALUES (715, 11, 68);
INSERT INTO `ipm_alarm_kpi` VALUES (716, 11, 69);
INSERT INTO `ipm_alarm_kpi` VALUES (717, 11, 70);

INSERT INTO `ipm_alarm_kpi` VALUES (720, 12, 57);
INSERT INTO `ipm_alarm_kpi` VALUES (721, 12, 58);
INSERT INTO `ipm_alarm_kpi` VALUES (722, 12, 59);
INSERT INTO `ipm_alarm_kpi` VALUES (723, 12, 60);
INSERT INTO `ipm_alarm_kpi` VALUES (724, 12, 61);
INSERT INTO `ipm_alarm_kpi` VALUES (725, 12, 68);
INSERT INTO `ipm_alarm_kpi` VALUES (726, 12, 69);
INSERT INTO `ipm_alarm_kpi` VALUES (727, 12, 70);

-- --------------------
-- 告警KPI数据 END 
-- --------------------

-- --------------------
-- 告警KPI对应算法数据 START (当ipm_alarm_kpi增加或者删除数据时，该表数据应有响应变动)
-- --------------------
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (1, 1, 1, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (2, 1, 2, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (3, 1, 3, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (4, 1, 4, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (5, 1, 5, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (6, 1, 6, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (7, 1, 7, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (8, 1, 8, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (9, 1, 9, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (10, 1, 10, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (11, 1, 11, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (12, 1, 12, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (13, 1, 13, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (14, 1, 14, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (15, 1, 15, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (16, 1, 16, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (17, 1, 17, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (18, 1, 18, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (19, 1, 19, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (20, 1, 20, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (21, 1, 21, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (22, 1, 22, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (23, 1, 23, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (24, 1, 24, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (25, 1, 25, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (26, 1, 26, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (27, 1, 27, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (28, 1, 28, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (29, 1, 29, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (30, 1, 30, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (31, 1, 31, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (32, 1, 32, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (33, 1, 33, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (34, 1, 34, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (35, 1, 35, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (36, 1, 36, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (37, 1, 37, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (38, 1, 38, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (39, 1, 39, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (40, 1, 40, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (41, 1, 41, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (42, 1, 42, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (43, 1, 43, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (44, 1, 44, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (45, 1, 45, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (46, 1, 46, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (47, 1, 47, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (48, 1, 48, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (49, 1, 49, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (50, 1, 50, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (51, 1, 51, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (52, 1, 52, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (53, 1, 53, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (54, 1, 54, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (55, 1, 55, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (56, 1, 56, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (57, 1, 57, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (58, 1, 58, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (59, 1, 59, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (60, 1, 60, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (61, 1, 61, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (62, 1, 62, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (63, 1, 63, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (64, 1, 64, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (65, 1, 65, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (66, 1, 66, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (67, 1, 67, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (68, 1, 68, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (69, 1, 69, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (70, 1, 70, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (71, 1, 71, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (72, 1, 72, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (73, 1, 73, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (74, 1, 74, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (75, 1, 75, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (76, 1, 76, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (77, 1, 77, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (80, 1, 80, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (81, 1, 81, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (82, 1, 82, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (83, 1, 83, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (84, 1, 84, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (85, 1, 85, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (86, 1, 86, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (87, 1, 87, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (88, 1, 88, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (89, 1, 89, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (90, 1, 90, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (91, 1, 91, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (92, 1, 92, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (93, 1, 93, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (94, 1, 94, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (95, 1, 95, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (96, 1, 96, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (97, 1, 97, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (98, 1, 98, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (99, 1, 99, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (100, 1, 100, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (101, 1, 101, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (102, 1, 102, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (103, 1, 103, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (104, 1, 104, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (105, 1, 105, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (106, 1, 106, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (107, 1, 107, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (108, 1, 108, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (109, 1, 109, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (110, 1, 110, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (111, 1, 111, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (112, 1, 112, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (113, 1, 113, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (114, 1, 114, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (115, 1, 115, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (116, 1, 116, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (117, 1, 117, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (120, 1, 120, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (121, 1, 121, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (122, 1, 122, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (123, 1, 123, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (124, 1, 124, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (125, 1, 125, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (126, 1, 126, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (127, 1, 127, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (128, 1, 128, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (129, 1, 129, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (130, 1, 130, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (131, 1, 131, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (140, 1, 140, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (141, 1, 141, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (142, 1, 142, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (143, 1, 143, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (144, 1, 144, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (145, 1, 145, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (146, 1, 146, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (147, 1, 147, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (148, 1, 148, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (149, 1, 149, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (150, 1, 150, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (160, 1, 160, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (161, 1, 161, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (162, 1, 162, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (163, 1, 163, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (164, 1, 164, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (165, 1, 165, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (166, 1, 166, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (167, 1, 167, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (168, 1, 168, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (169, 1, 169, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (170, 1, 170, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (180, 1, 180, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (181, 1, 181, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (182, 1, 182, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (183, 1, 183, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (184, 1, 184, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (185, 1, 185, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (186, 1, 186, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (187, 1, 187, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (188, 1, 188, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (189, 1, 189, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (190, 1, 190, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (210, 1, 210, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (211, 1, 211, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (212, 1, 212, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (213, 1, 213, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (214, 1, 214, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (215, 1, 215, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (216, 1, 216, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (217, 1, 217, 2);
 
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (300, 1, 300, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (301, 1, 301, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (302, 1, 302, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (350, 1, 350, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (351, 1, 351, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (352, 1, 352, 2);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (353, 1, 353, 2);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (354, 1, 354, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (355, 1, 355, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (356, 1, 356, 1);

-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (600, 1, 600, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (601, 1, 601, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (602, 1, 602, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (603, 1, 603, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (604, 1, 604, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (605, 1, 605, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (606, 1, 606, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (607, 1, 607, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (608, 1, 608, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (609, 1, 609, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (610, 1, 610, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (611, 1, 611, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (612, 1, 612, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (613, 1, 613, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (614, 1, 614, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (615, 1, 615, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (616, 1, 616, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (617, 1, 617, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (618, 1, 618, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (619, 1, 619, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (620, 1, 620, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (621, 1, 621, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (622, 1, 622, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (623, 1, 623, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (624, 1, 624, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (625, 1, 625, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (626, 1, 626, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (627, 1, 627, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (628, 1, 628, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (629, 1, 629, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (630, 1, 630, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (631, 1, 631, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (632, 1, 632, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (633, 1, 633, 1);
-- INSERT INTO `ipm_alarm_kpialgorithm` VALUES (634, 1, 634, 1);


INSERT INTO `ipm_alarm_kpialgorithm` VALUES (700, 1, 700, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (701, 1, 701, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (702, 1, 702, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (703, 1, 703, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (704, 1, 704, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (705, 1, 705, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (706, 1, 706, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (707, 1, 707, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (708, 1, 708, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (709, 1, 709, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (710, 1, 710, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (711, 1, 711, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (712, 1, 712, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (713, 1, 713, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (714, 1, 714, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (715, 1, 715, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (716, 1, 716, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (717, 1, 717, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (720, 1, 720, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (721, 1, 721, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (722, 1, 722, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (723, 1, 723, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (724, 1, 724, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (725, 1, 725, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (726, 1, 726, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (727, 1, 727, 1);

-- --------------------
-- 告警KPI对应算法数据 END 
-- --------------------

-- --------------------
-- 告警算法  START 
-- --------------------
INSERT INTO `ipm_alarm_algorithm` VALUES (1,'custombasic','Custom basic alarm algorithm',1,'5,5','Custom basic alarm algorithm,The warning rule is to exceed the warning threshold more than 5 times within three minutes(No distinction of rank),Alert at highest level');
INSERT INTO `ipm_alarm_algorithm` VALUES (2,'customurl','Custom URLMessage algorithm',2,'1,1','Custom URLMessage algorithm,The warning rule is to exceed the warning threshold more than 3 times within three minutes(No distinction of rank),Alert at highest level');
INSERT INTO `ipm_alarm_algorithm` VALUES (3,'custombaseline','Custom baseline algorithm',3,'5,5','Custom baseline algorithm,Alarms are given that exceed the baseline high or low threshold more than 5 times within 3 minutes');
-- --------------------
-- 告警算法 END 
-- --------------------

-- --------------------
-- 数据存储  START 
-- --------------------
INSERT INTO `ipm_data_storage` VALUES (1, '标准存储方案', null, null, null, null, null, null, null, false);
-- --------------------
-- 数据存储 END 
-- --------------------

-- --------------------
-- TOP配置  START
-- --------------------
INSERT INTO `ipm_app_topn_config` VALUES (1, 237, '`key` `name`, sum(`value`) `value`', 'concat(`server`, url) `key`, count(1) `value`', NULL, '`key`', '`value` desc', 10);
INSERT INTO `ipm_app_topn_config` VALUES (2, 238, '`key` `name`, avg(`value`) `value`', 'concat(`server`, url) `key`, avg(endtimewithpayload - begintime) `value`', NULL, '`key`', '`value` desc', 10);
INSERT INTO `ipm_app_topn_config` VALUES (3, 239, '`key` `name`, sum(`value`) `value`', 'concat(`server`, url) `key`, count(1) `value`', 'httpreturncode like \'4%\'', '`key`', '`value` desc', 10);
INSERT INTO `ipm_app_topn_config` VALUES (4, 240, '`key` `name`, sum(`value`) `value`', 'concat(`server`, url) `key`, count(1) `value`', 'httpreturncode like \'5%\'', '`key`', '`value` desc', 10);
INSERT INTO `ipm_app_topn_config` VALUES (5, 241, '`key` `name`, avg(`value`) `value`', 'sqlquery `key`, avg(queryduration) `value`', NULL, '`key`', '`value` desc', 10);
INSERT INTO `ipm_app_topn_config` VALUES (6, 242, '`key` `name`, sum(`value`) `value`', 'sqlquery `key`, count(1) `value`', 'responsecode != 0', '`key`', '`value` desc', 10);
INSERT INTO `ipm_app_topn_config` VALUES (7, 243, '`key` `name`, avg(`value`) `value`', 'sqlquery `key`, avg(queryduration) `value`', NULL, '`key`', '`value` desc', 10);
INSERT INTO `ipm_app_topn_config` VALUES (8, 244, '`key` `name`, sum(`value`) `value`', 'sqlquery `key`, count(1) `value`', 'responsecode != 0', '`key`', '`value` desc', 10);
INSERT INTO `ipm_app_topn_config` VALUES (9, 245, '`key` `name`, avg(`value`) `value`', 'sqlquery `key`, avg(queryduration) `value`', NULL, '`key`', '`value` desc', 10);
INSERT INTO `ipm_app_topn_config` VALUES (10, 246, '`key` `name`, sum(`value`) `value`', 'sqlquery `key`, count(1) `value`', 'responsecode != 0', '`key`', '`value` desc', 10);
-- --------------------
-- TOP配置 END 
-- --------------------

-- --------------------
-- 报表类型数据  START
-- --------------------
INSERT INTO `ipm_report_type` VALUES (1, 'User-defined');
INSERT INTO `ipm_report_type` VALUES (2, 'Daily report');
INSERT INTO `ipm_report_type` VALUES (3, 'Weekly report');
INSERT INTO `ipm_report_type` VALUES (4, 'Monthly report');
-- --------------------
-- 报表类型数据  START
-- --------------------

-- --------------------
-- 地址库area信息  START
-- --------------------
INSERT INTO `ipm_area_dict` VALUES ('1', '0', 'continent', '0', 'Asia');
INSERT INTO `ipm_area_dict` VALUES ('2', '0', 'continent', '0', 'Africa');
INSERT INTO `ipm_area_dict` VALUES ('3', '0', 'continent', '0', 'Europe');
INSERT INTO `ipm_area_dict` VALUES ('4', '0', 'continent', '0', 'North America');
INSERT INTO `ipm_area_dict` VALUES ('5', '0', 'continent', '0', 'South America');
INSERT INTO `ipm_area_dict` VALUES ('6', '0', 'continent', '0', 'Oceania');
INSERT INTO `ipm_area_dict` VALUES ('7', '0', 'continent', '0', 'Antarctica');
INSERT INTO `ipm_area_dict` VALUES ('10', '1', 'country', '1', 'China');
INSERT INTO `ipm_area_dict` VALUES ('301', '2', 'region_cn', '10', 'Beijing');
INSERT INTO `ipm_area_dict` VALUES ('302', '2', 'region_cn', '10', 'Tianjin');
INSERT INTO `ipm_area_dict` VALUES ('303', '2', 'region_cn', '10', 'Shanghai');
INSERT INTO `ipm_area_dict` VALUES ('304', '2', 'region_cn', '10', 'Chonging');
INSERT INTO `ipm_area_dict` VALUES ('305', '2', 'region_cn', '10', 'Hebei');
INSERT INTO `ipm_area_dict` VALUES ('306', '2', 'region_cn', '10', 'Shanxi');
INSERT INTO `ipm_area_dict` VALUES ('307', '2', 'region_cn', '10', 'Liaoning');
INSERT INTO `ipm_area_dict` VALUES ('308', '2', 'region_cn', '10', 'Jilin');
INSERT INTO `ipm_area_dict` VALUES ('309', '2', 'region_cn', '10', 'Heilongjiang');
INSERT INTO `ipm_area_dict` VALUES ('310', '2', 'region_cn', '10', 'Jiangsu');
INSERT INTO `ipm_area_dict` VALUES ('311', '2', 'region_cn', '10', 'Zhejiang');
INSERT INTO `ipm_area_dict` VALUES ('312', '2', 'region_cn', '10', 'Anhui');
INSERT INTO `ipm_area_dict` VALUES ('313', '2', 'region_cn', '10', 'Fujian');
INSERT INTO `ipm_area_dict` VALUES ('314', '2', 'region_cn', '10', 'Jiangxi');
INSERT INTO `ipm_area_dict` VALUES ('315', '2', 'region_cn', '10', 'Shandong');
INSERT INTO `ipm_area_dict` VALUES ('316', '2', 'region_cn', '10', 'Henan');
INSERT INTO `ipm_area_dict` VALUES ('317', '2', 'region_cn', '10', 'Hubei');
INSERT INTO `ipm_area_dict` VALUES ('318', '2', 'region_cn', '10', 'Hunan');
INSERT INTO `ipm_area_dict` VALUES ('319', '2', 'region_cn', '10', 'Guangdong');
INSERT INTO `ipm_area_dict` VALUES ('320', '2', 'region_cn', '10', 'Hainan');
INSERT INTO `ipm_area_dict` VALUES ('321', '2', 'region_cn', '10', 'Sichun');
INSERT INTO `ipm_area_dict` VALUES ('322', '2', 'region_cn', '10', 'Guizhou');
INSERT INTO `ipm_area_dict` VALUES ('323', '2', 'region_cn', '10', 'Yunnan');
INSERT INTO `ipm_area_dict` VALUES ('324', '2', 'region_cn', '10', 'Shan‘xi');
INSERT INTO `ipm_area_dict` VALUES ('325', '2', 'region_cn', '10', 'Gansu');
INSERT INTO `ipm_area_dict` VALUES ('326', '2', 'region_cn', '10', 'Qinghai');
INSERT INTO `ipm_area_dict` VALUES ('327', '2', 'region_cn', '10', 'Inner Mongolia');
INSERT INTO `ipm_area_dict` VALUES ('328', '2', 'region_cn', '10', 'Guangxi');
INSERT INTO `ipm_area_dict` VALUES ('329', '2', 'region_cn', '10', 'Tibet');
INSERT INTO `ipm_area_dict` VALUES ('330', '2', 'region_cn', '10', 'Ningxia');
INSERT INTO `ipm_area_dict` VALUES ('331', '2', 'region_cn', '10', 'Xinjiang');
INSERT INTO `ipm_area_dict` VALUES ('332', '2', 'region_cn', '10', 'Hongkong');
INSERT INTO `ipm_area_dict` VALUES ('333', '2', 'region_cn', '10', 'Macau');
INSERT INTO `ipm_area_dict` VALUES ('334', '2', 'region_cn', '10', 'Taiwan');
INSERT INTO `ipm_area_dict` VALUES ('401', '3', 'city_cn', '301', 'Dongcheng');
INSERT INTO `ipm_area_dict` VALUES ('402', '3', 'city_cn', '301', 'Fengtai');
INSERT INTO `ipm_area_dict` VALUES ('403', '3', 'city_cn', '301', 'Daxing');
INSERT INTO `ipm_area_dict` VALUES ('404', '3', 'city_cn', '301', 'Miyun');
INSERT INTO `ipm_area_dict` VALUES ('405', '3', 'city_cn', '301', 'Pinggu');
INSERT INTO `ipm_area_dict` VALUES ('406', '3', 'city_cn', '301', 'Yanqing');
INSERT INTO `ipm_area_dict` VALUES ('407', '3', 'city_cn', '301', 'Huairou');
INSERT INTO `ipm_area_dict` VALUES ('408', '3', 'city_cn', '301', 'Fangshan');
INSERT INTO `ipm_area_dict` VALUES ('409', '3', 'city_cn', '301', 'Changping');
INSERT INTO `ipm_area_dict` VALUES ('410', '3', 'city_cn', '301', 'Chaoyang');
INSERT INTO `ipm_area_dict` VALUES ('411', '3', 'city_cn', '301', 'Haidian');
INSERT INTO `ipm_area_dict` VALUES ('412', '3', 'city_cn', '301', 'Shijingshan');
INSERT INTO `ipm_area_dict` VALUES ('413', '3', 'city_cn', '301', 'Xicheng');
INSERT INTO `ipm_area_dict` VALUES ('414', '3', 'city_cn', '301', 'Tongzhou');
INSERT INTO `ipm_area_dict` VALUES ('415', '3', 'city_cn', '301', 'Mentougou');
INSERT INTO `ipm_area_dict` VALUES ('416', '3', 'city_cn', '301', 'Shunyi');
INSERT INTO `ipm_area_dict` VALUES ('421', '3', 'city_cn', '302', 'Dongli');
INSERT INTO `ipm_area_dict` VALUES ('422', '3', 'city_cn', '302', 'Beichen');
INSERT INTO `ipm_area_dict` VALUES ('423', '3', 'city_cn', '302', 'Nankai');
INSERT INTO `ipm_area_dict` VALUES ('424', '3', 'city_cn', '302', 'Heping');
INSERT INTO `ipm_area_dict` VALUES ('425', '3', 'city_cn', '302', 'NInghe');
INSERT INTO `ipm_area_dict` VALUES ('426', '3', 'city_cn', '302', 'Baodi');
INSERT INTO `ipm_area_dict` VALUES ('427', '3', 'city_cn', '302', 'Wuqing');
INSERT INTO `ipm_area_dict` VALUES ('428', '3', 'city_cn', '302', 'Hedong');
INSERT INTO `ipm_area_dict` VALUES ('429', '3', 'city_cn', '302', 'Hebei');
INSERT INTO `ipm_area_dict` VALUES ('430', '3', 'city_cn', '302', 'Hexi');
INSERT INTO `ipm_area_dict` VALUES ('431', '3', 'city_cn', '302', 'Jinnan');
INSERT INTO `ipm_area_dict` VALUES ('432', '3', 'city_cn', '302', 'Binhai');
INSERT INTO `ipm_area_dict` VALUES ('433', '3', 'city_cn', '302', 'Hongqiao');
INSERT INTO `ipm_area_dict` VALUES ('434', '3', 'city_cn', '302', 'Jizhou');
INSERT INTO `ipm_area_dict` VALUES ('435', '3', 'city_cn', '302', 'Xiqing');
INSERT INTO `ipm_area_dict` VALUES ('436', '3', 'city_cn', '302', 'Jinghai');
INSERT INTO `ipm_area_dict` VALUES ('441', '3', 'city_cn', '303', 'Jiading');
INSERT INTO `ipm_area_dict` VALUES ('442', '3', 'city_cn', '303', 'Fengxian');
INSERT INTO `ipm_area_dict` VALUES ('443', '3', 'city_cn', '303', 'Baoshan');
INSERT INTO `ipm_area_dict` VALUES ('444', '3', 'city_cn', '303', 'CHongming');
INSERT INTO `ipm_area_dict` VALUES ('445', '3', 'city_cn', '303', 'Xuhui');
INSERT INTO `ipm_area_dict` VALUES ('446', '3', 'city_cn', '303', 'Putuo');
INSERT INTO `ipm_area_dict` VALUES ('447', '3', 'city_cn', '303', 'Yangpu');
INSERT INTO `ipm_area_dict` VALUES ('448', '3', 'city_cn', '303', 'Songjiang');
INSERT INTO `ipm_area_dict` VALUES ('449', '3', 'city_cn', '303', 'Pudong');
INSERT INTO `ipm_area_dict` VALUES ('450', '3', 'city_cn', '303', 'Hongkou');
INSERT INTO `ipm_area_dict` VALUES ('451', '3', 'city_cn', '303', 'Jinshan');
INSERT INTO `ipm_area_dict` VALUES ('452', '3', 'city_cn', '303', 'Changning');
INSERT INTO `ipm_area_dict` VALUES ('453', '3', 'city_cn', '303', 'Minhang');
INSERT INTO `ipm_area_dict` VALUES ('454', '3', 'city_cn', '303', 'Qingpu');
INSERT INTO `ipm_area_dict` VALUES ('455', '3', 'city_cn', '303', 'Jing`an');
INSERT INTO `ipm_area_dict` VALUES ('456', '3', 'city_cn', '303', 'Huangpu');
INSERT INTO `ipm_area_dict` VALUES ('461', '3', 'city_cn', '304', 'Wanzhou');
INSERT INTO `ipm_area_dict` VALUES ('462', '3', 'city_cn', '304', 'Fengdu');
INSERT INTO `ipm_area_dict` VALUES ('463', '3', 'city_cn', '304', 'Jiulongpo');
INSERT INTO `ipm_area_dict` VALUES ('464', '3', 'city_cn', '304', 'YUnyang');
INSERT INTO `ipm_area_dict` VALUES ('465', '3', 'city_cn', '304', 'Beibei');
INSERT INTO `ipm_area_dict` VALUES ('466', '3', 'city_cn', '304', 'Nan`an');
INSERT INTO `ipm_area_dict` VALUES ('467', '3', 'city_cn', '304', 'Nanchuan');
INSERT INTO `ipm_area_dict` VALUES ('468', '3', 'city_cn', '304', 'Hechuan');
INSERT INTO `ipm_area_dict` VALUES ('469', '3', 'city_cn', '304', 'Dianjiang');
INSERT INTO `ipm_area_dict` VALUES ('470', '3', 'city_cn', '304', 'Chengkou');
INSERT INTO `ipm_area_dict` VALUES ('471', '3', 'city_cn', '304', 'Dadukou');
INSERT INTO `ipm_area_dict` VALUES ('472', '3', 'city_cn', '304', 'Dazu');
INSERT INTO `ipm_area_dict` VALUES ('473', '3', 'city_cn', '304', 'Fengjie');
INSERT INTO `ipm_area_dict` VALUES ('474', '3', 'city_cn', '304', 'Wushan');
INSERT INTO `ipm_area_dict` VALUES ('475', '3', 'city_cn', '304', 'Wuxi');
INSERT INTO `ipm_area_dict` VALUES ('476', '3', 'city_cn', '304', 'Ba`nan');
INSERT INTO `ipm_area_dict` VALUES ('477', '3', 'city_cn', '304', 'Kaizhou');
INSERT INTO `ipm_area_dict` VALUES ('478', '3', 'city_cn', '304', 'Pengshui');
INSERT INTO `ipm_area_dict` VALUES ('479', '3', 'city_cn', '304', 'Zhouxian');
INSERT INTO `ipm_area_dict` VALUES ('480', '3', 'city_cn', '304', 'Liangping');
INSERT INTO `ipm_area_dict` VALUES ('481', '3', 'city_cn', '304', 'Wuling');
INSERT INTO `ipm_area_dict` VALUES ('482', '3', 'city_cn', '304', 'Yongzhou');
INSERT INTO `ipm_area_dict` VALUES ('483', '3', 'city_cn', '304', 'Jiangbei');
INSERT INTO `ipm_area_dict` VALUES ('484', '3', 'city_cn', '304', 'Jiangjin');
INSERT INTO `ipm_area_dict` VALUES ('485', '3', 'city_cn', '304', 'Shapingba');
INSERT INTO `ipm_area_dict` VALUES ('486', '3', 'city_cn', '304', 'Fuling');
INSERT INTO `ipm_area_dict` VALUES ('487', '3', 'city_cn', '304', 'Yuzhong');
INSERT INTO `ipm_area_dict` VALUES ('488', '3', 'city_cn', '304', 'Yubei');
INSERT INTO `ipm_area_dict` VALUES ('489', '3', 'city_cn', '304', 'Tongnan');
INSERT INTO `ipm_area_dict` VALUES ('490', '3', 'city_cn', '304', 'Bishan');
INSERT INTO `ipm_area_dict` VALUES ('491', '3', 'city_cn', '304', 'Shizhu');
INSERT INTO `ipm_area_dict` VALUES ('492', '3', 'city_cn', '304', 'Xiushan');
INSERT INTO `ipm_area_dict` VALUES ('493', '3', 'city_cn', '304', 'Qijiang');
INSERT INTO `ipm_area_dict` VALUES ('494', '3', 'city_cn', '304', 'Rongchang');
INSERT INTO `ipm_area_dict` VALUES ('495', '3', 'city_cn', '304', 'Youyang');
INSERT INTO `ipm_area_dict` VALUES ('496', '3', 'city_cn', '304', 'Tongliang');
INSERT INTO `ipm_area_dict` VALUES ('497', '3', 'city_cn', '304', 'Changshou');
INSERT INTO `ipm_area_dict` VALUES ('498', '3', 'city_cn', '304', 'Qianjiang');
INSERT INTO `ipm_area_dict` VALUES ('510', '3', 'city_cn', '305', 'Baoding');
INSERT INTO `ipm_area_dict` VALUES ('511', '3', 'city_cn', '305', 'Tangshan');
INSERT INTO `ipm_area_dict` VALUES ('512', '3', 'city_cn', '305', 'Langfang');
INSERT INTO `ipm_area_dict` VALUES ('513', '3', 'city_cn', '305', 'Zhangjiakou');
INSERT INTO `ipm_area_dict` VALUES ('514', '3', 'city_cn', '305', 'Chengde');
INSERT INTO `ipm_area_dict` VALUES ('515', '3', 'city_cn', '305', 'Cangzhou');
INSERT INTO `ipm_area_dict` VALUES ('516', '3', 'city_cn', '305', 'Shijiazhuang');
INSERT INTO `ipm_area_dict` VALUES ('517', '3', 'city_cn', '305', 'Qinhuangdao');
INSERT INTO `ipm_area_dict` VALUES ('518', '3', 'city_cn', '305', 'Hengshui');
INSERT INTO `ipm_area_dict` VALUES ('519', '3', 'city_cn', '305', 'Xingtai');
INSERT INTO `ipm_area_dict` VALUES ('520', '3', 'city_cn', '305', 'Handan');
INSERT INTO `ipm_area_dict` VALUES ('521', '3', 'city_cn', '306', 'Linfen');
INSERT INTO `ipm_area_dict` VALUES ('522', '3', 'city_cn', '306', 'Lvliang');
INSERT INTO `ipm_area_dict` VALUES ('523', '3', 'city_cn', '306', 'Datong');
INSERT INTO `ipm_area_dict` VALUES ('524', '3', 'city_cn', '306', 'Taiyuan');
INSERT INTO `ipm_area_dict` VALUES ('525', '3', 'city_cn', '306', 'Xinzhou');
INSERT INTO `ipm_area_dict` VALUES ('526', '3', 'city_cn', '306', 'Jinzhong');
INSERT INTO `ipm_area_dict` VALUES ('527', '3', 'city_cn', '306', 'Jincheng');
INSERT INTO `ipm_area_dict` VALUES ('528', '3', 'city_cn', '306', 'Shuozhou');
INSERT INTO `ipm_area_dict` VALUES ('529', '3', 'city_cn', '306', 'Yuncheng');
INSERT INTO `ipm_area_dict` VALUES ('530', '3', 'city_cn', '306', 'Changzhi');
INSERT INTO `ipm_area_dict` VALUES ('531', '3', 'city_cn', '306', 'Yangquan');
INSERT INTO `ipm_area_dict` VALUES ('541', '3', 'city_cn', '307', 'Dandong');
INSERT INTO `ipm_area_dict` VALUES ('542', '3', 'city_cn', '307', 'Dalian');
INSERT INTO `ipm_area_dict` VALUES ('543', '3', 'city_cn', '307', 'Fushun');
INSERT INTO `ipm_area_dict` VALUES ('544', '3', 'city_cn', '307', 'CHaoyang');
INSERT INTO `ipm_area_dict` VALUES ('545', '3', 'city_cn', '307', 'Benxi');
INSERT INTO `ipm_area_dict` VALUES ('546', '3', 'city_cn', '307', 'Shenyang');
INSERT INTO `ipm_area_dict` VALUES ('547', '3', 'city_cn', '307', 'Panjin');
INSERT INTO `ipm_area_dict` VALUES ('548', '3', 'city_cn', '307', 'Yingkou');
INSERT INTO `ipm_area_dict` VALUES ('549', '3', 'city_cn', '307', 'Huludao');
INSERT INTO `ipm_area_dict` VALUES ('550', '3', 'city_cn', '307', 'Liaoyang');
INSERT INTO `ipm_area_dict` VALUES ('551', '3', 'city_cn', '307', 'Tieliang');
INSERT INTO `ipm_area_dict` VALUES ('552', '3', 'city_cn', '307', 'Jinzhou');
INSERT INTO `ipm_area_dict` VALUES ('553', '3', 'city_cn', '307', 'Fuxin');
INSERT INTO `ipm_area_dict` VALUES ('554', '3', 'city_cn', '307', 'Anshan');
INSERT INTO `ipm_area_dict` VALUES ('561', '3', 'city_cn', '308', 'Siping');
INSERT INTO `ipm_area_dict` VALUES ('562', '3', 'city_cn', '308', 'Yanbian');
INSERT INTO `ipm_area_dict` VALUES ('563', '3', 'city_cn', '308', 'Songyuan');
INSERT INTO `ipm_area_dict` VALUES ('564', '3', 'city_cn', '308', 'Baicheng');
INSERT INTO `ipm_area_dict` VALUES ('565', '3', 'city_cn', '308', 'Baishan');
INSERT INTO `ipm_area_dict` VALUES ('566', '3', 'city_cn', '308', 'Liaoyuan');
INSERT INTO `ipm_area_dict` VALUES ('567', '3', 'city_cn', '308', 'Tonghua');
INSERT INTO `ipm_area_dict` VALUES ('568', '3', 'city_cn', '308', 'Changchun');
INSERT INTO `ipm_area_dict` VALUES ('571', '3', 'city_cn', '309', 'Qitaihe');
INSERT INTO `ipm_area_dict` VALUES ('572', '3', 'city_cn', '309', 'Yichuan');
INSERT INTO `ipm_area_dict` VALUES ('573', '3', 'city_cn', '309', 'Jiamusi');
INSERT INTO `ipm_area_dict` VALUES ('574', '3', 'city_cn', '309', 'Shuangyashan');
INSERT INTO `ipm_area_dict` VALUES ('575', '3', 'city_cn', '309', 'Haerbin');
INSERT INTO `ipm_area_dict` VALUES ('576', '3', 'city_cn', '309', 'Daxinganling');
INSERT INTO `ipm_area_dict` VALUES ('577', '3', 'city_cn', '309', 'Daqing');
INSERT INTO `ipm_area_dict` VALUES ('578', '3', 'city_cn', '309', 'Mudanjiang');
INSERT INTO `ipm_area_dict` VALUES ('579', '3', 'city_cn', '309', 'Suihua');
INSERT INTO `ipm_area_dict` VALUES ('580', '3', 'city_cn', '309', 'Jixi');
INSERT INTO `ipm_area_dict` VALUES ('581', '3', 'city_cn', '309', 'Hegang');
INSERT INTO `ipm_area_dict` VALUES ('582', '3', 'city_cn', '309', 'Heihe');
INSERT INTO `ipm_area_dict` VALUES ('583', '3', 'city_cn', '309', 'Qiqihaer');
INSERT INTO `ipm_area_dict` VALUES ('591', '3', 'city_cn', '310', 'Nantong');
INSERT INTO `ipm_area_dict` VALUES ('592', '3', 'city_cn', '310', 'Suqian');
INSERT INTO `ipm_area_dict` VALUES ('593', '3', 'city_cn', '310', 'Changzhou');
INSERT INTO `ipm_area_dict` VALUES ('594', '3', 'city_cn', '310', 'Xuzhou');
INSERT INTO `ipm_area_dict` VALUES ('595', '3', 'city_cn', '310', 'Yangzhou');
INSERT INTO `ipm_area_dict` VALUES ('596', '3', 'city_cn', '310', 'Wuxi');
INSERT INTO `ipm_area_dict` VALUES ('597', '3', 'city_cn', '310', 'Taian');
INSERT INTO `ipm_area_dict` VALUES ('598', '3', 'city_cn', '310', 'Huaian');
INSERT INTO `ipm_area_dict` VALUES ('599', '3', 'city_cn', '310', 'Yancheng');
INSERT INTO `ipm_area_dict` VALUES ('600', '3', 'city_cn', '310', 'Suzhou');
INSERT INTO `ipm_area_dict` VALUES ('601', '3', 'city_cn', '310', 'Lianyungang');
INSERT INTO `ipm_area_dict` VALUES ('602', '3', 'city_cn', '310', 'Zhenjiang');
INSERT INTO `ipm_area_dict` VALUES ('611', '3', 'city_cn', '311', 'Lishui');
INSERT INTO `ipm_area_dict` VALUES ('612', '3', 'city_cn', '311', 'Taizhou');
INSERT INTO `ipm_area_dict` VALUES ('613', '3', 'city_cn', '311', 'Jiaxing');
INSERT INTO `ipm_area_dict` VALUES ('614', '3', 'city_cn', '311', 'Ningbo');
INSERT INTO `ipm_area_dict` VALUES ('615', '3', 'city_cn', '311', 'Hangzhou');
INSERT INTO `ipm_area_dict` VALUES ('616', '3', 'city_cn', '311', 'Wenzhou');
INSERT INTO `ipm_area_dict` VALUES ('617', '3', 'city_cn', '311', 'Huzhou');
INSERT INTO `ipm_area_dict` VALUES ('618', '3', 'city_cn', '311', 'Shaoxing');
INSERT INTO `ipm_area_dict` VALUES ('619', '3', 'city_cn', '311', 'Zhoushan');
INSERT INTO `ipm_area_dict` VALUES ('620', '3', 'city_cn', '311', 'Henzhou');
INSERT INTO `ipm_area_dict` VALUES ('621', '3', 'city_cn', '311', 'Jinhua');
INSERT INTO `ipm_area_dict` VALUES ('631', '3', 'city_cn', '312', 'Bozhou');
INSERT INTO `ipm_area_dict` VALUES ('632', '3', 'city_cn', '312', 'Lu`an');
INSERT INTO `ipm_area_dict` VALUES ('633', '3', 'city_cn', '312', 'Hefei');
INSERT INTO `ipm_area_dict` VALUES ('634', '3', 'city_cn', '312', 'Anqidng');
INSERT INTO `ipm_area_dict` VALUES ('635', '3', 'city_cn', '312', 'Yicheng');
INSERT INTO `ipm_area_dict` VALUES ('636', '3', 'city_cn', '312', 'Suzhou');
INSERT INTO `ipm_area_dict` VALUES ('637', '3', 'city_cn', '312', 'Chizhou');
INSERT INTO `ipm_area_dict` VALUES ('638', '3', 'city_cn', '312', 'Huaibei');
INSERT INTO `ipm_area_dict` VALUES ('639', '3', 'city_cn', '312', 'Huainan');
INSERT INTO `ipm_area_dict` VALUES ('640', '3', 'city_cn', '312', 'Chuzhou');
INSERT INTO `ipm_area_dict` VALUES ('641', '3', 'city_cn', '312', 'Wuhu');
INSERT INTO `ipm_area_dict` VALUES ('642', '3', 'city_cn', '312', 'Bengbu');
INSERT INTO `ipm_area_dict` VALUES ('643', '3', 'city_cn', '312', 'Tongling');
INSERT INTO `ipm_area_dict` VALUES ('644', '3', 'city_cn', '312', 'Fuyang');
INSERT INTO `ipm_area_dict` VALUES ('645', '3', 'city_cn', '312', 'Maanshan');
INSERT INTO `ipm_area_dict` VALUES ('646', '3', 'city_cn', '312', 'Huangshan');
INSERT INTO `ipm_area_dict` VALUES ('651', '3', 'city_cn', '313', 'Sanping');
INSERT INTO `ipm_area_dict` VALUES ('652', '3', 'city_cn', '313', 'Nanping');
INSERT INTO `ipm_area_dict` VALUES ('653', '3', 'city_cn', '313', 'Xiamen');
INSERT INTO `ipm_area_dict` VALUES ('654', '3', 'city_cn', '313', 'Ningde');
INSERT INTO `ipm_area_dict` VALUES ('655', '3', 'city_cn', '313', 'Quanzhou');
INSERT INTO `ipm_area_dict` VALUES ('656', '3', 'city_cn', '313', 'Zhangzhou');
INSERT INTO `ipm_area_dict` VALUES ('657', '3', 'city_cn', '313', 'Fuzhou');
INSERT INTO `ipm_area_dict` VALUES ('658', '3', 'city_cn', '313', 'Putian');
INSERT INTO `ipm_area_dict` VALUES ('659', '3', 'city_cn', '313', 'Longyan');
INSERT INTO `ipm_area_dict` VALUES ('661', '3', 'city_cn', '314', 'Shangrao');
INSERT INTO `ipm_area_dict` VALUES ('662', '3', 'city_cn', '314', 'Jiujiang');
INSERT INTO `ipm_area_dict` VALUES ('663', '3', 'city_cn', '314', 'Nanchang');
INSERT INTO `ipm_area_dict` VALUES ('664', '3', 'city_cn', '314', 'Ji`an');
INSERT INTO `ipm_area_dict` VALUES ('665', '3', 'city_cn', '314', 'Yichun');
INSERT INTO `ipm_area_dict` VALUES ('666', '3', 'city_cn', '314', 'Fuzhou');
INSERT INTO `ipm_area_dict` VALUES ('667', '3', 'city_cn', '314', 'Xinyu');
INSERT INTO `ipm_area_dict` VALUES ('668', '3', 'city_cn', '314', 'Jingdezhen');
INSERT INTO `ipm_area_dict` VALUES ('669', '3', 'city_cn', '314', 'Pingxiang');
INSERT INTO `ipm_area_dict` VALUES ('670', '3', 'city_cn', '314', 'Ganzhou');
INSERT INTO `ipm_area_dict` VALUES ('671', '3', 'city_cn', '314', 'Yingtan');
INSERT INTO `ipm_area_dict` VALUES ('681', '3', 'city_cn', '315', 'Dongying');
INSERT INTO `ipm_area_dict` VALUES ('682', '3', 'city_cn', '315', 'Linyi');
INSERT INTO `ipm_area_dict` VALUES ('683', '3', 'city_cn', '315', 'Weihai');
INSERT INTO `ipm_area_dict` VALUES ('684', '3', 'city_cn', '315', 'Dezhou');
INSERT INTO `ipm_area_dict` VALUES ('685', '3', 'city_cn', '315', 'Rizhao');
INSERT INTO `ipm_area_dict` VALUES ('686', '3', 'city_cn', '315', 'Zaozhuang');
INSERT INTO `ipm_area_dict` VALUES ('687', '3', 'city_cn', '315', 'Taian');
INSERT INTO `ipm_area_dict` VALUES ('688', '3', 'city_cn', '315', 'Jinan');
INSERT INTO `ipm_area_dict` VALUES ('689', '3', 'city_cn', '315', 'Jining');
INSERT INTO `ipm_area_dict` VALUES ('690', '3', 'city_cn', '315', 'Zibo');
INSERT INTO `ipm_area_dict` VALUES ('691', '3', 'city_cn', '315', 'Bingzhouj');
INSERT INTO `ipm_area_dict` VALUES ('692', '3', 'city_cn', '315', 'Weifang');
INSERT INTO `ipm_area_dict` VALUES ('693', '3', 'city_cn', '315', 'Yantai');
INSERT INTO `ipm_area_dict` VALUES ('694', '3', 'city_cn', '315', 'Liaocheng');
INSERT INTO `ipm_area_dict` VALUES ('695', '3', 'city_cn', '315', 'Laiwu');
INSERT INTO `ipm_area_dict` VALUES ('696', '3', 'city_cn', '315', 'Heze');
INSERT INTO `ipm_area_dict` VALUES ('697', '3', 'city_cn', '315', 'Qingdao');
INSERT INTO `ipm_area_dict` VALUES ('701', '3', 'city_cn', '316', 'Sanmenxia');
INSERT INTO `ipm_area_dict` VALUES ('702', '3', 'city_cn', '316', 'Xinyang');
INSERT INTO `ipm_area_dict` VALUES ('703', '3', 'city_cn', '316', 'Nanyang');
INSERT INTO `ipm_area_dict` VALUES ('704', '3', 'city_cn', '316', 'Zhoukou');
INSERT INTO `ipm_area_dict` VALUES ('705', '3', 'city_cn', '316', 'Shanqiu');
INSERT INTO `ipm_area_dict` VALUES ('706', '3', 'city_cn', '316', 'Anyang');
INSERT INTO `ipm_area_dict` VALUES ('707', '3', 'city_cn', '316', 'Pingdingshan');
INSERT INTO `ipm_area_dict` VALUES ('708', '3', 'city_cn', '316', 'Kaifeng');
INSERT INTO `ipm_area_dict` VALUES ('709', '3', 'city_cn', '316', 'Xinxiang');
INSERT INTO `ipm_area_dict` VALUES ('710', '3', 'city_cn', '316', 'Luoyang');
INSERT INTO `ipm_area_dict` VALUES ('711', '3', 'city_cn', '316', 'Jiyuan');
INSERT INTO `ipm_area_dict` VALUES ('712', '3', 'city_cn', '316', 'Luohe');
INSERT INTO `ipm_area_dict` VALUES ('713', '3', 'city_cn', '316', 'Puyang');
INSERT INTO `ipm_area_dict` VALUES ('714', '3', 'city_cn', '316', 'Jiaozuo');
INSERT INTO `ipm_area_dict` VALUES ('715', '3', 'city_cn', '316', 'Xuchang');
INSERT INTO `ipm_area_dict` VALUES ('716', '3', 'city_cn', '316', 'Zhengzhou');
INSERT INTO `ipm_area_dict` VALUES ('717', '3', 'city_cn', '316', 'Zhumadian');
INSERT INTO `ipm_area_dict` VALUES ('718', '3', 'city_cn', '316', 'Hebi');
INSERT INTO `ipm_area_dict` VALUES ('721', '3', 'city_cn', '317', 'Xiantao');
INSERT INTO `ipm_area_dict` VALUES ('722', '3', 'city_cn', '317', 'Shiyan');
INSERT INTO `ipm_area_dict` VALUES ('723', '3', 'city_cn', '317', 'Xianning');
INSERT INTO `ipm_area_dict` VALUES ('724', '3', 'city_cn', '317', 'Tianmen');
INSERT INTO `ipm_area_dict` VALUES ('725', '3', 'city_cn', '317', 'Xiaogan');
INSERT INTO `ipm_area_dict` VALUES ('726', '3', 'city_cn', '317', 'YIchang');
INSERT INTO `ipm_area_dict` VALUES ('727', '3', 'city_cn', '317', 'Enshi');
INSERT INTO `ipm_area_dict` VALUES ('728', '3', 'city_cn', '317', 'Wuhan');
INSERT INTO `ipm_area_dict` VALUES ('729', '3', 'city_cn', '317', 'Qianjiang');
INSERT INTO `ipm_area_dict` VALUES ('730', '3', 'city_cn', '317', 'Shennongjia');
INSERT INTO `ipm_area_dict` VALUES ('731', '3', 'city_cn', '317', 'Jingzhou');
INSERT INTO `ipm_area_dict` VALUES ('732', '3', 'city_cn', '317', 'Jingmen');
INSERT INTO `ipm_area_dict` VALUES ('733', '3', 'city_cn', '317', 'Xiangyang');
INSERT INTO `ipm_area_dict` VALUES ('734', '3', 'city_cn', '317', 'Ezhou');
INSERT INTO `ipm_area_dict` VALUES ('735', '3', 'city_cn', '317', 'Suizhou');
INSERT INTO `ipm_area_dict` VALUES ('736', '3', 'city_cn', '317', 'Huanggang');
INSERT INTO `ipm_area_dict` VALUES ('737', '3', 'city_cn', '317', 'Huangshi');
INSERT INTO `ipm_area_dict` VALUES ('741', '3', 'city_cn', '318', 'Loudi');
INSERT INTO `ipm_area_dict` VALUES ('742', '3', 'city_cn', '318', 'Yueyang');
INSERT INTO `ipm_area_dict` VALUES ('743', '3', 'city_cn', '318', 'Changde');
INSERT INTO `ipm_area_dict` VALUES ('744', '3', 'city_cn', '318', 'Zhangjiajie');
INSERT INTO `ipm_area_dict` VALUES ('745', '3', 'city_cn', '318', 'Huaihua');
INSERT INTO `ipm_area_dict` VALUES ('746', '3', 'city_cn', '318', 'Zhuzhou');
INSERT INTO `ipm_area_dict` VALUES ('747', '3', 'city_cn', '318', 'Yongzhou');
INSERT INTO `ipm_area_dict` VALUES ('748', '3', 'city_cn', '318', 'Xiangtan');
INSERT INTO `ipm_area_dict` VALUES ('749', '3', 'city_cn', '318', 'Xiangxi');
INSERT INTO `ipm_area_dict` VALUES ('750', '3', 'city_cn', '318', 'Yiyang');
INSERT INTO `ipm_area_dict` VALUES ('751', '3', 'city_cn', '318', 'Hengyang');
INSERT INTO `ipm_area_dict` VALUES ('752', '3', 'city_cn', '318', 'Shanyang');
INSERT INTO `ipm_area_dict` VALUES ('753', '3', 'city_cn', '318', 'Chenzhou');
INSERT INTO `ipm_area_dict` VALUES ('754', '3', 'city_cn', '318', 'Changsha');
INSERT INTO `ipm_area_dict` VALUES ('761', '3', 'city_cn', '319', 'Dongguan');
INSERT INTO `ipm_area_dict` VALUES ('762', '3', 'city_cn', '319', 'Zhongshan');
INSERT INTO `ipm_area_dict` VALUES ('763', '3', 'city_cn', '319', 'Yunfu');
INSERT INTO `ipm_area_dict` VALUES ('764', '3', 'city_cn', '319', 'Foshan');
INSERT INTO `ipm_area_dict` VALUES ('765', '3', 'city_cn', '319', 'Guangzhou');
INSERT INTO `ipm_area_dict` VALUES ('766', '3', 'city_cn', '319', 'Huizhou');
INSERT INTO `ipm_area_dict` VALUES ('767', '3', 'city_cn', '319', 'Jieyang');
INSERT INTO `ipm_area_dict` VALUES ('768', '3', 'city_cn', '319', 'Meizhou');
INSERT INTO `ipm_area_dict` VALUES ('769', '3', 'city_cn', '319', 'Shantou');
INSERT INTO `ipm_area_dict` VALUES ('770', '3', 'city_cn', '319', 'Shanwei');
INSERT INTO `ipm_area_dict` VALUES ('771', '3', 'city_cn', '319', 'Jiangmen');
INSERT INTO `ipm_area_dict` VALUES ('772', '3', 'city_cn', '319', 'Heyuan');
INSERT INTO `ipm_area_dict` VALUES ('773', '3', 'city_cn', '319', 'Shenzhen');
INSERT INTO `ipm_area_dict` VALUES ('774', '3', 'city_cn', '319', 'Qingyuan');
INSERT INTO `ipm_area_dict` VALUES ('775', '3', 'city_cn', '319', 'Zhanjiang');
INSERT INTO `ipm_area_dict` VALUES ('776', '3', 'city_cn', '319', 'Chaozhou');
INSERT INTO `ipm_area_dict` VALUES ('777', '3', 'city_cn', '319', 'Zhuhai');
INSERT INTO `ipm_area_dict` VALUES ('778', '3', 'city_cn', '319', 'Zhaoqing');
INSERT INTO `ipm_area_dict` VALUES ('779', '3', 'city_cn', '319', 'Maoming');
INSERT INTO `ipm_area_dict` VALUES ('780', '3', 'city_cn', '319', 'Yangjiang');
INSERT INTO `ipm_area_dict` VALUES ('781', '3', 'city_cn', '319', 'Shaoguan');
INSERT INTO `ipm_area_dict` VALUES ('791', '3', 'city_cn', '320', 'Wanning');
INSERT INTO `ipm_area_dict` VALUES ('792', '3', 'city_cn', '320', 'Sanya');
INSERT INTO `ipm_area_dict` VALUES ('793', '3', 'city_cn', '320', 'Dongfang');
INSERT INTO `ipm_area_dict` VALUES ('794', '3', 'city_cn', '320', 'Lin`gao');
INSERT INTO `ipm_area_dict` VALUES ('795', '3', 'city_cn', '320', 'Ledong');
INSERT INTO `ipm_area_dict` VALUES ('796', '3', 'city_cn', '320', 'Wuzhishan');
INSERT INTO `ipm_area_dict` VALUES ('797', '3', 'city_cn', '320', 'Baoting');
INSERT INTO `ipm_area_dict` VALUES ('798', '3', 'city_cn', '320', 'Danzhou');
INSERT INTO `ipm_area_dict` VALUES ('799', '3', 'city_cn', '320', 'Ding`an');
INSERT INTO `ipm_area_dict` VALUES ('800', '3', 'city_cn', '320', 'Tunchang');
INSERT INTO `ipm_area_dict` VALUES ('801', '3', 'city_cn', '320', 'Wenchang');
INSERT INTO `ipm_area_dict` VALUES ('802', '3', 'city_cn', '320', 'Changjiang');
INSERT INTO `ipm_area_dict` VALUES ('803', '3', 'city_cn', '320', 'Haikou');
INSERT INTO `ipm_area_dict` VALUES ('804', '3', 'city_cn', '320', 'Chengmai');
INSERT INTO `ipm_area_dict` VALUES ('805', '3', 'city_cn', '320', 'Qiongzhong');
INSERT INTO `ipm_area_dict` VALUES ('806', '3', 'city_cn', '320', 'Qionghai');
INSERT INTO `ipm_area_dict` VALUES ('807', '3', 'city_cn', '320', 'Baisha');
INSERT INTO `ipm_area_dict` VALUES ('808', '3', 'city_cn', '320', 'Lingshui');
INSERT INTO `ipm_area_dict` VALUES ('811', '3', 'city_cn', '321', 'Leshan');
INSERT INTO `ipm_area_dict` VALUES ('812', '3', 'city_cn', '321', 'Neijiang');
INSERT INTO `ipm_area_dict` VALUES ('813', '3', 'city_cn', '321', 'Liangshan');
INSERT INTO `ipm_area_dict` VALUES ('814', '3', 'city_cn', '321', 'Nanchong');
INSERT INTO `ipm_area_dict` VALUES ('815', '3', 'city_cn', '321', 'Yibin');
INSERT INTO `ipm_area_dict` VALUES ('816', '3', 'city_cn', '321', 'Bazhong');
INSERT INTO `ipm_area_dict` VALUES ('817', '3', 'city_cn', '321', 'Guangyuan');
INSERT INTO `ipm_area_dict` VALUES ('818', '3', 'city_cn', '321', 'Guang`an');
INSERT INTO `ipm_area_dict` VALUES ('819', '3', 'city_cn', '321', 'Deyang');
INSERT INTO `ipm_area_dict` VALUES ('820', '3', 'city_cn', '321', 'Chengdu');
INSERT INTO `ipm_area_dict` VALUES ('821', '3', 'city_cn', '321', 'Panzhihua');
INSERT INTO `ipm_area_dict` VALUES ('822', '3', 'city_cn', '321', 'Luzhou');
INSERT INTO `ipm_area_dict` VALUES ('823', '3', 'city_cn', '321', 'Ganzi');
INSERT INTO `ipm_area_dict` VALUES ('824', '3', 'city_cn', '321', 'Meishan');
INSERT INTO `ipm_area_dict` VALUES ('825', '3', 'city_cn', '321', 'Mianyang');
INSERT INTO `ipm_area_dict` VALUES ('826', '3', 'city_cn', '321', 'Zigong');
INSERT INTO `ipm_area_dict` VALUES ('827', '3', 'city_cn', '321', 'Ziyang');
INSERT INTO `ipm_area_dict` VALUES ('828', '3', 'city_cn', '321', 'Dazhou');
INSERT INTO `ipm_area_dict` VALUES ('829', '3', 'city_cn', '321', 'Suining');
INSERT INTO `ipm_area_dict` VALUES ('830', '3', 'city_cn', '321', 'Aba');
INSERT INTO `ipm_area_dict` VALUES ('831', '3', 'city_cn', '321', 'Yaan');
INSERT INTO `ipm_area_dict` VALUES ('841', '3', 'city_cn', '322', 'Liupanshui');
INSERT INTO `ipm_area_dict` VALUES ('842', '3', 'city_cn', '322', 'Anshun');
INSERT INTO `ipm_area_dict` VALUES ('843', '3', 'city_cn', '322', 'Bijie');
INSERT INTO `ipm_area_dict` VALUES ('844', '3', 'city_cn', '322', 'Guiyang');
INSERT INTO `ipm_area_dict` VALUES ('845', '3', 'city_cn', '322', 'Zunyi');
INSERT INTO `ipm_area_dict` VALUES ('846', '3', 'city_cn', '322', 'Tongren');
INSERT INTO `ipm_area_dict` VALUES ('847', '3', 'city_cn', '322', 'Qiandongnan');
INSERT INTO `ipm_area_dict` VALUES ('848', '3', 'city_cn', '322', 'Qiannan');
INSERT INTO `ipm_area_dict` VALUES ('849', '3', 'city_cn', '322', 'Qianxi`nan');
INSERT INTO `ipm_area_dict` VALUES ('851', '3', 'city_cn', '323', 'Lincang');
INSERT INTO `ipm_area_dict` VALUES ('852', '3', 'city_cn', '323', 'Lijiang');
INSERT INTO `ipm_area_dict` VALUES ('853', '3', 'city_cn', '323', 'Baoshan');
INSERT INTO `ipm_area_dict` VALUES ('854', '3', 'city_cn', '323', 'Dali');
INSERT INTO `ipm_area_dict` VALUES ('855', '3', 'city_cn', '323', 'Dehong');
INSERT INTO `ipm_area_dict` VALUES ('856', '3', 'city_cn', '323', 'Nujiang');
INSERT INTO `ipm_area_dict` VALUES ('857', '3', 'city_cn', '323', 'Wenshan');
INSERT INTO `ipm_area_dict` VALUES ('858', '3', 'city_cn', '323', 'Kunming');
INSERT INTO `ipm_area_dict` VALUES ('859', '3', 'city_cn', '323', 'Zhaotong');
INSERT INTO `ipm_area_dict` VALUES ('860', '3', 'city_cn', '323', 'Puer');
INSERT INTO `ipm_area_dict` VALUES ('861', '3', 'city_cn', '323', 'Qujin');
INSERT INTO `ipm_area_dict` VALUES ('862', '3', 'city_cn', '323', 'Chuxiong');
INSERT INTO `ipm_area_dict` VALUES ('863', '3', 'city_cn', '323', 'Yuxi');
INSERT INTO `ipm_area_dict` VALUES ('864', '3', 'city_cn', '323', 'Honghe');
INSERT INTO `ipm_area_dict` VALUES ('865', '3', 'city_cn', '323', 'Xishuangbanna');
INSERT INTO `ipm_area_dict` VALUES ('866', '3', 'city_cn', '323', 'Diqing');
INSERT INTO `ipm_area_dict` VALUES ('871', '3', 'city_cn', '324', 'Xianyang');
INSERT INTO `ipm_area_dict` VALUES ('872', '3', 'city_cn', '324', 'Shangluo');
INSERT INTO `ipm_area_dict` VALUES ('873', '3', 'city_cn', '324', 'Ankang');
INSERT INTO `ipm_area_dict` VALUES ('874', '3', 'city_cn', '324', 'Baoji');
INSERT INTO `ipm_area_dict` VALUES ('875', '3', 'city_cn', '324', 'Yan`an');
INSERT INTO `ipm_area_dict` VALUES ('876', '3', 'city_cn', '324', 'Yulin');
INSERT INTO `ipm_area_dict` VALUES ('877', '3', 'city_cn', '324', 'Hanzhong');
INSERT INTO `ipm_area_dict` VALUES ('878', '3', 'city_cn', '324', 'Weinan');
INSERT INTO `ipm_area_dict` VALUES ('879', '3', 'city_cn', '324', 'Xi`an');
INSERT INTO `ipm_area_dict` VALUES ('880', '3', 'city_cn', '324', 'Tongchuan');
INSERT INTO `ipm_area_dict` VALUES ('891', '3', 'city_cn', '325', 'Linxia');
INSERT INTO `ipm_area_dict` VALUES ('892', '3', 'city_cn', '325', 'Lanzhou');
INSERT INTO `ipm_area_dict` VALUES ('893', '3', 'city_cn', '325', 'Jiayuguan');
INSERT INTO `ipm_area_dict` VALUES ('894', '3', 'city_cn', '325', 'Tianshui');
INSERT INTO `ipm_area_dict` VALUES ('895', '3', 'city_cn', '325', 'Dingxi');
INSERT INTO `ipm_area_dict` VALUES ('896', '3', 'city_cn', '325', 'Pingliang');
INSERT INTO `ipm_area_dict` VALUES ('897', '3', 'city_cn', '325', 'Qingyang');
INSERT INTO `ipm_area_dict` VALUES ('898', '3', 'city_cn', '325', 'Zhangye');
INSERT INTO `ipm_area_dict` VALUES ('899', '3', 'city_cn', '325', 'Wuwei');
INSERT INTO `ipm_area_dict` VALUES ('900', '3', 'city_cn', '325', 'Gannan');
INSERT INTO `ipm_area_dict` VALUES ('901', '3', 'city_cn', '325', 'Baiyin');
INSERT INTO `ipm_area_dict` VALUES ('902', '3', 'city_cn', '325', 'Jiuquan');
INSERT INTO `ipm_area_dict` VALUES ('903', '3', 'city_cn', '325', 'Jinchang');
INSERT INTO `ipm_area_dict` VALUES ('904', '3', 'city_cn', '325', 'Longnan');
INSERT INTO `ipm_area_dict` VALUES ('911', '3', 'city_cn', '326', 'Guoluo');
INSERT INTO `ipm_area_dict` VALUES ('912', '3', 'city_cn', '326', 'Haidong');
INSERT INTO `ipm_area_dict` VALUES ('913', '3', 'city_cn', '326', 'Haibei');
INSERT INTO `ipm_area_dict` VALUES ('914', '3', 'city_cn', '326', 'Hainan');
INSERT INTO `ipm_area_dict` VALUES ('915', '3', 'city_cn', '326', 'Haixi');
INSERT INTO `ipm_area_dict` VALUES ('916', '3', 'city_cn', '326', 'Yushu');
INSERT INTO `ipm_area_dict` VALUES ('917', '3', 'city_cn', '326', 'Xining');
INSERT INTO `ipm_area_dict` VALUES ('918', '3', 'city_cn', '326', 'Huangnan');
INSERT INTO `ipm_area_dict` VALUES ('921', '3', 'city_cn', '327', 'Wulanchabu');
INSERT INTO `ipm_area_dict` VALUES ('922', '3', 'city_cn', '327', 'Wuhai');
INSERT INTO `ipm_area_dict` VALUES ('923', '3', 'city_cn', '327', 'Xing`anmeng');
INSERT INTO `ipm_area_dict` VALUES ('924', '3', 'city_cn', '327', 'Baotou');
INSERT INTO `ipm_area_dict` VALUES ('925', '3', 'city_cn', '327', 'Hulunbeier');
INSERT INTO `ipm_area_dict` VALUES ('926', '3', 'city_cn', '327', 'Huhehaote');
INSERT INTO `ipm_area_dict` VALUES ('927', '3', 'city_cn', '327', 'Bayannaoer');
INSERT INTO `ipm_area_dict` VALUES ('928', '3', 'city_cn', '327', 'Chifeng');
INSERT INTO `ipm_area_dict` VALUES ('929', '3', 'city_cn', '327', 'Tongliao');
INSERT INTO `ipm_area_dict` VALUES ('930', '3', 'city_cn', '327', 'Eerduosi');
INSERT INTO `ipm_area_dict` VALUES ('931', '3', 'city_cn', '327', 'Xilinguolemeng');
INSERT INTO `ipm_area_dict` VALUES ('932', '3', 'city_cn', '327', 'Alashanmeng');
INSERT INTO `ipm_area_dict` VALUES ('941', '3', 'city_cn', '328', 'Beihai');
INSERT INTO `ipm_area_dict` VALUES ('942', '3', 'city_cn', '328', 'Nanning');
INSERT INTO `ipm_area_dict` VALUES ('943', '3', 'city_cn', '328', 'Chongzuo');
INSERT INTO `ipm_area_dict` VALUES ('944', '3', 'city_cn', '328', 'Laibin');
INSERT INTO `ipm_area_dict` VALUES ('945', '3', 'city_cn', '328', 'Liuzhou');
INSERT INTO `ipm_area_dict` VALUES ('946', '3', 'city_cn', '328', 'Guuilin');
INSERT INTO `ipm_area_dict` VALUES ('947', '3', 'city_cn', '328', 'Wuzhou');
INSERT INTO `ipm_area_dict` VALUES ('948', '3', 'city_cn', '328', 'Hechi');
INSERT INTO `ipm_area_dict` VALUES ('949', '3', 'city_cn', '328', 'Yulin');
INSERT INTO `ipm_area_dict` VALUES ('950', '3', 'city_cn', '328', 'Bose');
INSERT INTO `ipm_area_dict` VALUES ('951', '3', 'city_cn', '328', 'Guigang');
INSERT INTO `ipm_area_dict` VALUES ('952', '3', 'city_cn', '328', 'Hezhou');
INSERT INTO `ipm_area_dict` VALUES ('953', '3', 'city_cn', '328', 'Qinzhou');
INSERT INTO `ipm_area_dict` VALUES ('954', '3', 'city_cn', '328', 'Fangchenggang');
INSERT INTO `ipm_area_dict` VALUES ('961', '3', 'city_cn', '329', 'Shannan');
INSERT INTO `ipm_area_dict` VALUES ('962', '3', 'city_cn', '329', 'Lasa');
INSERT INTO `ipm_area_dict` VALUES ('963', '3', 'city_cn', '329', 'Rikaze');
INSERT INTO `ipm_area_dict` VALUES ('964', '3', 'city_cn', '329', 'Changdu');
INSERT INTO `ipm_area_dict` VALUES ('965', '3', 'city_cn', '329', 'Linzhi');
INSERT INTO `ipm_area_dict` VALUES ('966', '3', 'city_cn', '329', 'Naqu');
INSERT INTO `ipm_area_dict` VALUES ('967', '3', 'city_cn', '329', 'Ali');
INSERT INTO `ipm_area_dict` VALUES ('971', '3', 'city_cn', '330', 'Zhongwei');
INSERT INTO `ipm_area_dict` VALUES ('972', '3', 'city_cn', '330', 'Wuzhong');
INSERT INTO `ipm_area_dict` VALUES ('973', '3', 'city_cn', '330', 'Guyuan');
INSERT INTO `ipm_area_dict` VALUES ('974', '3', 'city_cn', '330', 'Shizuishan');
INSERT INTO `ipm_area_dict` VALUES ('975', '3', 'city_cn', '330', 'Yinchuan');
INSERT INTO `ipm_area_dict` VALUES ('981', '3', 'city_cn', '331', 'Wulumuqi');
INSERT INTO `ipm_area_dict` VALUES ('982', '3', 'city_cn', '331', 'Yili');
INSERT INTO `ipm_area_dict` VALUES ('983', '3', 'city_cn', '331', 'Kezilesu');
INSERT INTO `ipm_area_dict` VALUES ('984', '3', 'city_cn', '331', 'Kelamayi');
INSERT INTO `ipm_area_dict` VALUES ('985', '3', 'city_cn', '331', 'Boertala');
INSERT INTO `ipm_area_dict` VALUES ('986', '3', 'city_cn', '331', 'Tulufan');
INSERT INTO `ipm_area_dict` VALUES ('987', '3', 'city_cn', '331', 'Hetian');
INSERT INTO `ipm_area_dict` VALUES ('988', '3', 'city_cn', '331', 'Hami');
INSERT INTO `ipm_area_dict` VALUES ('989', '3', 'city_cn', '331', 'Kashi');
INSERT INTO `ipm_area_dict` VALUES ('990', '3', 'city_cn', '331', 'Tacheng');
INSERT INTO `ipm_area_dict` VALUES ('991', '3', 'city_cn', '331', 'Bayinguoleng');
INSERT INTO `ipm_area_dict` VALUES ('992', '3', 'city_cn', '331', 'Changji');
INSERT INTO `ipm_area_dict` VALUES ('993', '3', 'city_cn', '331', 'Shihezi');
INSERT INTO `ipm_area_dict` VALUES ('994', '3', 'city_cn', '331', 'Akesu');
INSERT INTO `ipm_area_dict` VALUES ('995', '3', 'city_cn', '331', 'Aletai');
INSERT INTO `ipm_area_dict` VALUES ('1011', '3', 'city_cn', '332', 'Jiulong');
INSERT INTO `ipm_area_dict` VALUES ('1012', '3', 'city_cn', '332', 'Xinjie');
INSERT INTO `ipm_area_dict` VALUES ('1013', '3', 'city_cn', '332', 'Hongkong island');
INSERT INTO `ipm_area_dict` VALUES ('1021', '3', 'city_cn', '333', 'Macau');
INSERT INTO `ipm_area_dict` VALUES ('1031', '3', 'city_cn', '334', 'Yunlin');
INSERT INTO `ipm_area_dict` VALUES ('1032', '3', 'city_cn', '334', 'Natou');
INSERT INTO `ipm_area_dict` VALUES ('1033', '3', 'city_cn', '334', 'Taidong');
INSERT INTO `ipm_area_dict` VALUES ('1034', '3', 'city_cn', '334', 'Taizhong');
INSERT INTO `ipm_area_dict` VALUES ('1035', '3', 'city_cn', '334', 'Taibei');
INSERT INTO `ipm_area_dict` VALUES ('1036', '3', 'city_cn', '334', 'Tainan');
INSERT INTO `ipm_area_dict` VALUES ('1037', '3', 'city_cn', '334', 'Jiayi');
INSERT INTO `ipm_area_dict` VALUES ('1038', '3', 'city_cn', '334', 'Jilong');
INSERT INTO `ipm_area_dict` VALUES ('1039', '3', 'city_cn', '334', 'Yilan');
INSERT INTO `ipm_area_dict` VALUES ('1040', '3', 'city_cn', '334', 'Pingdong');
INSERT INTO `ipm_area_dict` VALUES ('1041', '3', 'city_cn', '334', 'Penghua');
INSERT INTO `ipm_area_dict` VALUES ('1042', '3', 'city_cn', '334', 'Hualian');
INSERT INTO `ipm_area_dict` VALUES ('1043', '3', 'city_cn', '334', 'Miaoli');
INSERT INTO `ipm_area_dict` VALUES ('1044', '3', 'city_cn', '334', 'Jinmen');
INSERT INTO `ipm_area_dict` VALUES ('1045', '3', 'city_cn', '334', 'Gaoxiong');
-- --------------------
-- 地址库area信息  END
-- --------------------

-- --------------------
-- 邮件发送设置 START
-- --------------------
INSERT INTO `ipm_email_control` VALUES (1, '', '', '', '', '', '');
-- --------------------
-- 邮件发送设置 END
-- --------------------

-- --------------------
-- 增加系统资源消耗告警的基础数据 START 
-- --------------------
INSERT INTO `ipm_app_business` VALUES (1, 2, 'System resource consumption', NULL, NULL, NULL);

INSERT INTO `ipm_alarm_set` VALUES (1, 0, 1, 1, 632, 2, 'Y', NULL, 70);
INSERT INTO `ipm_alarm_set` VALUES (2, 0, 1, 1, 632, 3, 'Y', NULL, 80);
INSERT INTO `ipm_alarm_set` VALUES (3, 0, 1, 1, 632, 4, 'Y', NULL, 90);
INSERT INTO `ipm_alarm_set` VALUES (4, 0, 1, 1, 632, 1, 'N', NULL, NULL);

INSERT INTO `ipm_alarm_trigger` VALUES ('1', '1', '1', '1', null, 'ratio', UNIX_TIMESTAMP(NOW()));
INSERT INTO `ipm_alarm_trigger` VALUES ('2', '1', '0', '0', 70, 'ratio', UNIX_TIMESTAMP(NOW()));
INSERT INTO `ipm_alarm_trigger` VALUES ('3', '2', '1', '1', null, 'ratio', UNIX_TIMESTAMP(NOW()));
INSERT INTO `ipm_alarm_trigger` VALUES ('4', '2', '0', '0', 80, 'ratio', UNIX_TIMESTAMP(NOW()));
INSERT INTO `ipm_alarm_trigger` VALUES ('5', '3', '1', '1', null, 'ratio', UNIX_TIMESTAMP(NOW()));
INSERT INTO `ipm_alarm_trigger` VALUES ('6', '3', '0', '0', 90, 'ratio', UNIX_TIMESTAMP(NOW()));
INSERT INTO `ipm_alarm_trigger` VALUES ('7', '4', '1', '1', null, 'ratio', UNIX_TIMESTAMP(NOW()));
INSERT INTO `ipm_alarm_trigger` VALUES ('8', '4', '0', '1', null, 'ratio', UNIX_TIMESTAMP(NOW()));
-- --------------------
-- 增加系统资源消耗告警的基础数据 END 
-- --------------------


-- --------------------
-- 报表模版默认添加 START
-- --------------------
insert into ipm_report_modal (id,name,user_id,description,create_time,module_ids) VALUES
(null,'Alarm template',1,'This is a report with a list of alarm type statistics, including alarm statistics, Flow trend changes, and session number trend changes。',
1532919949,'10,11,12');
insert into ipm_timer_report_detail (id,report_id,module_id,plot_id,plot_type_id) VALUES(null,1,10,1,1),(null,1,10,2,1),(null,1,10,317,1),(null,1,11,32,1),(null,1,11,33,1),(null,1,12,60,1),(null,1,12,61,1),(null,1,12,319,1);
insert into ipm_modal_tableandalarm (id,modal_id,module_id,table_having,warning_having) values (null,1,10,null,1),(null,1,11,null,1),(null,1,12,null,1);

insert into ipm_report_modal (id,name,user_id,description,create_time,module_ids) VALUES
(null,'Network main indicator template',1,'This template centralizes all the key KPI metrics for the network and includes a list of alarm statistics for this watch point, with a comprehensive ranking of alarms in future releases.',
1532919949,'10');
insert into ipm_timer_report_detail (id,report_id,module_id,plot_id,plot_type_id) VALUES(null,2,10,276,1),(null,2,10,3,1),(null,2,10,1,1),(null,2,10,317,1),(null,2,10,2,1),(null,2,10,9,1);
insert into ipm_modal_tableandalarm (id,modal_id,module_id,table_having,warning_having) values (null,2,10,null,1),(null,2,10,1,null);
-- --------------------
-- 报表模版默认添加 END
-- --------------------
