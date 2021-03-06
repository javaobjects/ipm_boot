USE ipm;

-- --------------------
-- 授权模块数据 START (根据授权文件内容添加，测试先全部) 
-- --------------------
INSERT INTO `ipm_authorize_module` VALUES (1, 'USABILITY', '应用可用性', true, true, 15);
INSERT INTO `ipm_authorize_module` VALUES (2, 'SYSTEM', '系统资源消耗', true, true, 16);
INSERT INTO `ipm_authorize_module` VALUES (3, 'CENTER', '用户管理', true, false, 17);
INSERT INTO `ipm_authorize_module` VALUES (4, 'HTTP', 'HTTP服务', true, false, 7);
INSERT INTO `ipm_authorize_module` VALUES (5, 'ORACLE', 'ORACLE服务', true, false, 8);
INSERT INTO `ipm_authorize_module` VALUES (6, 'MYSQL', 'MYSQL服务', true, false, 9);
INSERT INTO `ipm_authorize_module` VALUES (7, 'SQLSERVER', 'SQLSERVER服务', true, false, 10);
INSERT INTO `ipm_authorize_module` VALUES (8, 'URL', 'URL服务', true, false, 11);
INSERT INTO `ipm_authorize_module` VALUES (9, 'MESSAGE', '报文服务', true, false, 12);
INSERT INTO `ipm_authorize_module` VALUES (10, 'WATCHPOINT', '观察点', true, true, 1);
INSERT INTO `ipm_authorize_module` VALUES (11, 'CLIENT', '客户端', true, false, 3);
INSERT INTO `ipm_authorize_module` VALUES (12, 'SERVER', '服务端', true, false, 2);
INSERT INTO `ipm_authorize_module` VALUES (13, 'NETWORK', '网络', true, true, 13);

INSERT INTO `ipm_authorize_module` VALUES (14, 'MANYWATCHPOINT', '多观察点', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (15, 'FLOWSTORAGE', '流量储存', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (16, 'MAP', '地图', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (17, 'TOPO', '拓扑图', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (18, 'TRAFFICPAIR', '通信队', false, false, 0);
INSERT INTO `ipm_authorize_module` VALUES (19, 'DIGGER', 'iDigger', false, false, 0);
-- --------------------
-- 授权模块数据 END (根据授权文件内容添加，测试先全部)
-- --------------------


-- --------------------
-- 服务器接入 START 
-- --------------------
INSERT INTO `ipm_center_ip` VALUES (1, '本机', '127.0.0.1', 80, '默认设置', 0);
-- --------------------
-- 服务器接入 END 
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
INSERT INTO `ipm_juris_module` VALUES (101, 'businessManager', '业务管理', 1);
INSERT INTO `ipm_juris_module` VALUES (102, 'topology', '拓扑图', 2);
INSERT INTO `ipm_juris_module` VALUES (103, 'flowStore', '流量存储', 3);
INSERT INTO `ipm_juris_module` VALUES (104, 'alarmSet', '告警统计', 4);
INSERT INTO `ipm_juris_module` VALUES (105, 'reportManager', '报表管理', 5);
INSERT INTO `ipm_juris_module` VALUES (106, 'systemSet', '系统设置', 6);
INSERT INTO `ipm_juris_module` VALUES (107, 'view', '驾驶舱', 7);
INSERT INTO `ipm_juris_module` VALUES (13, 'network', '网络', 8);
INSERT INTO `ipm_juris_module` VALUES (10, 'watchpoint', '观察点', 9);
INSERT INTO `ipm_juris_module` VALUES (12, 'server', '服务端', 10);
INSERT INTO `ipm_juris_module` VALUES (11, 'client', '客户端', 11);
INSERT INTO `ipm_juris_module` VALUES (4, 'http', 'HTTP服务', 12);
INSERT INTO `ipm_juris_module` VALUES (5, 'oracle', 'ORACLE服务', 13);
INSERT INTO `ipm_juris_module` VALUES (6, 'mysql', 'MYSQL服务', 14);
INSERT INTO `ipm_juris_module` VALUES (7, 'sqlserver', 'SQLSERVER服务', 15);
INSERT INTO `ipm_juris_module` VALUES (8, 'url', 'URL服务', 16);
INSERT INTO `ipm_juris_module` VALUES (9, 'message', '报文服务', 17);
-- --------------------
-- 权限模块数据 END 
-- --------------------


-- --------------------
-- 系统用户数据 START 
-- --------------------
INSERT INTO `ipm_system_user` VALUES (1, 1, 'admin', 'bf4fc4d6598e34ae', 'info', 'info@protocolsoft.com', '13141402442', unix_timestamp(now()), false, false);
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
-- --------------------
-- 系统用户配置数据 END 
-- --------------------


-- --------------------
-- 驾驶舱数据 START 
-- --------------------
INSERT INTO `ipm_monitor_view` VALUES (1, '网络', '网络', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (2, '观察点', '观察点', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (3, '客户端', '客户端', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (4, '服务端', '服务端', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (5, 'HTTP服务', 'HTTP服务', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (6, 'ORACLE服务', 'ORACLE服务', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (7, 'MYSQL服务', 'MYSQL服务', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (8, 'SQLSERVER服务', 'SQLSERVER服务', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (9, 'URL服务', 'URL服务', 1, UNIX_TIMESTAMP(NOW()), 0);
INSERT INTO `ipm_monitor_view` VALUES (10, '报文服务', '报文服务', 1, UNIX_TIMESTAMP(NOW()), 0);
-- 无用数据,解决AUTO_INCREMENT问题
INSERT INTO `ipm_monitor_view` VALUES (100, '', '', 1, UNIX_TIMESTAMP(NOW()), 0);
-- --------------------
-- 驾驶舱数据 END 
-- --------------------


-- --------------------
-- 系统角色数据 START 
-- --------------------
INSERT INTO `ipm_system_role` VALUES (1, '系统管理员', '系统管理员帐号,拥有所有功能权限！');
INSERT INTO `ipm_system_role` VALUES (2, '普通用户', '系统普通用户,需要系统管理员赋予指定功能权限！');
-- --------------------
-- 系统角色数据 END 
-- --------------------


-- --------------------
-- 列表类型数据 START 
-- --------------------
INSERT INTO `ipm_list_type` VALUES (4, 'HTTP服务状态列表', 4);
INSERT INTO `ipm_list_type` VALUES (5, 'Oracle服务状态列表', 5);
INSERT INTO `ipm_list_type` VALUES (6, 'MySql服务状态列表', 6);
INSERT INTO `ipm_list_type` VALUES (7, 'SQLServer服务状态列表', 7);
INSERT INTO `ipm_list_type` VALUES (8, 'URL状态列表', 8);
INSERT INTO `ipm_list_type` VALUES (9, '报文状态列表', 9);
INSERT INTO `ipm_list_type` VALUES (10, '观察点重点KPI概要', 10);
INSERT INTO `ipm_list_type` VALUES (11, '客户端重点KPI概要', 11);
INSERT INTO `ipm_list_type` VALUES (12, '服务端重点KPI概要', 12);
INSERT INTO `ipm_list_type` VALUES (13, '通信对', 0);
INSERT INTO `ipm_list_type` VALUES (14, 'HTTP会话列表', 4);
INSERT INTO `ipm_list_type` VALUES (15, 'Oracle会话列表', 5);
INSERT INTO `ipm_list_type` VALUES (16, 'MySql会话列表', 6);
INSERT INTO `ipm_list_type` VALUES (17, 'SQLServer会话列表', 7);
INSERT INTO `ipm_list_type` VALUES (18, 'URL性能列表', 8);
INSERT INTO `ipm_list_type` VALUES (19, '报文会话列表', 9);
-- --------------------
-- 列表类型数据 END 
-- --------------------


-- --------------------
-- 列表字段数据 START 
-- --------------------
INSERT INTO `ipm_list_column` VALUES (1001, 10, 'name', '租户/观察点', '', 1);
INSERT INTO `ipm_list_column` VALUES (1002, 10, 'load_5', '设备负载', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1003, 10, 'ip', '租户IP', '', 0);
INSERT INTO `ipm_list_column` VALUES (1004, 10, 'contacts', '联系人', '', 0);
INSERT INTO `ipm_list_column` VALUES (1005, 10, 'telephone', '电话', '', 0);
INSERT INTO `ipm_list_column` VALUES (1006, 10, 'email', 'Email', '', 0);
INSERT INTO `ipm_list_column` VALUES (1007, 10, 'alarmLevel', '未响应告警数量', '', 1);
INSERT INTO `ipm_list_column` VALUES (1008, 10, 'qos', '服务质量', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1009, 10, 'serverDurDelay', '服务端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1010, 10, 'clientDurDelay', '客户端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1011, 10, 'rtt', '链路时延RTT', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1012, 10, 'serverConDelay', '服务端握手时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1013, 10, 'clientConDelay', '客户端握手时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1014, 10, 'responseDelay', '应用处理时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1015, 10, 'loadDelay', '负载传输时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1016, 10, 'serverRetransDelay', '服务端重传时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1017, 10, 'clientRetransDelay', '客户端重传时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1018, 10, 'ethernetTraffic', '网络流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1019, 10, 'tcpTraffic', 'TCP流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1020, 10, 'udpTraffic', 'UDP流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1021, 10, 'inTraffic', '进流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1022, 10, 'outTraffic', '出流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1023, 10, 'unKnowSerTraffic', '未定义服务端流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1024, 10, 'unKnowCliTraffic', '未定义客户端流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1025, 10, 'sessionNum', '最大会话数量', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1026, 10, 'synPkts', 'TCP连接发起', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1027, 10, 'rstPkts', 'TCP连接重置', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1028, 10, 'synAckPkts', 'TCP连接响应', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1029, 10, 'finPkts', 'FIN包数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1030, 10, 'fin1Pkts', '主动关闭连接数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1031, 10, 'fin2Pkts', '被动关闭连接数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1032, 10, 'ethernetPkts', '数据包速率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1033, 10, 'netPktLostRatio', '网络丢包率', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1034, 10, 'serverPktLostRatio', '服务端丢包率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1035, 10, 'clientPktLostRatio', '客户端丢包率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1036, 10, 'tinyPkts', '小包速率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1037, 10, 'tinyPktsRatio', '小包比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1038, 10, 'avgPktsLen', '平均包长', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1039, 10, 'zeroWinCount', '零窗口包数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1040, 10, 'bandWidthRatio', '流控带宽占用率', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1041, 10, 'arpTraffic', 'ARP流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1042, 10, 'arpPkts', 'ARP包速率', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (1102, 11, 'name', '客户端名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (1103, 11, 'alarmLevel', '未响应告警数量', '', 1);
INSERT INTO `ipm_list_column` VALUES (1104, 11, 'serverDurDelay', '服务端通信时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1105, 11, 'clientDurDelay', '客户端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1106, 11, 'rtt', '链路时延RTT', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1107, 11, 'serverConDelay', '服务端握手时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1108, 11, 'clientConDelay', '客户端握手时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1109, 11, 'responseDelay', '应用处理时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1110, 11, 'serverRetransDelay', '服务端重传时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1111, 11, 'clientRetransDelay', '客户端重传时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1112, 11, 'ethernetTraffic', '网络流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1113, 11, 'tcpTraffic', 'TCP流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1114, 11, 'udpTraffic', 'UDP流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1115, 11, 'inTraffic', '进流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1116, 11, 'outTraffic', '出流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1117, 11, 'unKnowSerTraffic', '未定义服务端流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1118, 11, 'sessionNum', '最大会话数量', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1119, 11, 'synPkts', 'TCP连接发起', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1120, 11, 'rstPkts', 'TCP连接重置', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1121, 11, 'synAckPkts', 'TCP连接响应', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1122, 11, 'finPkts', 'FIN包数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1123, 11, 'fin1Pkts', '主动关闭连接数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1124, 11, 'fin2Pkts', '被动关闭连接数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1125, 11, 'ethernetPkts', '数据包速率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1126, 11, 'netPktLostRatio', '网络丢包率', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1127, 11, 'serverPktLostRatio', '服务端丢包率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1128, 11, 'clientPktLostRatio', '客户端丢包率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1129, 11, 'tinyPkts', '小包速率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1130, 11, 'tinyPktsRatio', '小包比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1131, 11, 'avgPktsLen', '平均包长', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1132, 11, 'zeroWinCount', '零窗口包数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1133, 11, 'bandWidthRatio', '流控带宽占用率', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (1202, 12, 'name', '服务端名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (1203, 12, 'alarmLevel', '未响应告警数量', '', 1);
INSERT INTO `ipm_list_column` VALUES (1204, 12, 'qos', '服务质量', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1205, 12, 'serverDurDelay', '服务端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1206, 12, 'clientDurDelay', '客户端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1207, 12, 'rtt', '链路时延RTT', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1208, 12, 'serverConDelay', '服务端握手时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1209, 12, 'clientConDelay', '客户端握手时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1210, 12, 'responseDelay', '应用处理时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1211, 12, 'loadDelay', '负载传输时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1212, 12, 'serverRetransDelay', '服务端重传时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1213, 12, 'clientRetransDelay', '客户端重传时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1214, 12, 'ethernetTraffic', '网络流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1215, 12, 'tcpTraffic', 'TCP流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1216, 12, 'udpTraffic', 'UDP流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1217, 12, 'inTraffic', '进流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1218, 12, 'outTraffic', '出流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1219, 12, 'unKnowCliTraffic', '未定义客户端流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1220, 12, 'sessionNum', '最大会话数量', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1221, 12, 'synPkts', 'TCP连接发起', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1222, 12, 'rstPkts', 'TCP连接重置', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1223, 12, 'synAckPkts', 'TCP连接响应', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1224, 12, 'finPkts', 'FIN包数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1225, 12, 'fin1Pkts', '主动关闭连接数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1226, 12, 'fin2Pkts', '被动关闭连接数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1227, 12, 'ethernetPkts', '数据包速率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1228, 12, 'netPktLostRatio', '网络丢包率', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1229, 12, 'serverPktLostRatio', '服务端丢包率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1230, 12, 'clientPktLostRatio', '客户端丢包率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1231, 12, 'tinyPkts', '小包速率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1232, 12, 'tinyPktsRatio', '小包比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1233, 12, 'avgPktsLen', '平均包长', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1234, 12, 'zeroWinCount', '零窗口包数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1235, 12, 'bandWidthRatio', '流控带宽占用率', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (1301, 13, 'startEndstr', '发起时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1302, 13, 'ipmCenterName', 'XPM服务器名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1303, 13, 'watchpointName', '观察点名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1304, 13, 'protocol', '协议', '', 0);
INSERT INTO `ipm_list_column` VALUES (1305, 13, 'serverip', '服务端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1306, 13, 'serverport', '服务端端口', '', 0);
INSERT INTO `ipm_list_column` VALUES (1307, 13, 'serverName', '服务端名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1308, 13, 'clientip', '客户端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1309, 13, 'clientName', '客户端名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1310, 13, 'ethernetTraffic', '网络流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1311, 13, 'synPkts', 'TCP连接发起', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1312, 13, 'qos', '服务质量', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1313, 13, 'serverDurDelay', '服务端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1314, 13, 'clientDurDelay', '客户端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1315, 13, 'rtt', '链路时延RTT', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1316, 13, 'serverConDelay', '服务端握手时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1317, 13, 'clientConDelay', '客户端握手时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1318, 13, 'responseDelay', '应用处理时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1319, 13, 'loadDelay', '负载传输时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1320, 13, 'serverRetransDelay', '服务端重传时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1321, 13, 'clientRetransDelay', '客户端重传时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1322, 13, 'tcpTraffic', 'TCP流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1323, 13, 'udpTraffic', 'UDP流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1324, 13, 'inTraffic', '进流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1325, 13, 'outTraffic', '出流量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1326, 13, 'rstPkts', 'TCP连接重置', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1327, 13, 'synAckPkts', 'TCP连接响应', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1328, 13, 'finPkts', 'FIN包数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1329, 13, 'fin1Pkts', '主动关闭连接数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1330, 13, 'fin2Pkts', '被动关闭连接数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1331, 13, 'ethernetPkts', '数据包个数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1332, 13, 'netPktLostRatio', '网络丢包率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1333, 13, 'serverPktLostRatio', '服务端丢包率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1334, 13, 'clientPktLostRatio', '客户端丢包率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1335, 13, 'tinyPkts', '小包个数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1336, 13, 'tinyPktsRatio', '小包比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1337, 13, 'avgPktsLen', '平均包长', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (1338, 13, 'zeroWinCount', '零窗口包数', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (1339, 13, 'communicationProtocol', '通信协议', '', 0);
INSERT INTO `ipm_list_column` VALUES (1340, 13, 'countryCn', '国家', '', 0);
INSERT INTO `ipm_list_column` VALUES (1341, 13, 'regionCn', '省份', '', 0);
INSERT INTO `ipm_list_column` VALUES (1342, 13, 'cityCn', '城市', '', 0);
INSERT INTO `ipm_list_column` VALUES (1343, 13, 'ispCn', '运营商', '', 0);

INSERT INTO `ipm_list_column` VALUES (402, 4, 'name', '业务名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (403, 4, 'alarmLevel', '未响应告警数量', '', 1);
INSERT INTO `ipm_list_column` VALUES (404, 4, 'serverDurDelay', '服务端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (405, 4, 'clientDurDelay', '客户端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (406, 4, 'rtt', '链路时延RTT', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (407, 4, 'httpResponseDelay', '服务响应时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (408, 4, 'pageloadDelay', 'URL负载传输时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (409, 4, 'ethernetTraffic', '网络流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (410, 4, 'l7SessionCountTotal', 'HTTP应用会话数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (412, 4, 'failRespRatio', 'HTTP错误返回码比率', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (414, 4, 'noRespRatio', 'HTTP未响应比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (415, 4, 'netPktLostRatio', '网络通信丢包率', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (502, 5, 'name', '业务名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (503, 5, 'alarmLevel', '未响应告警数量', '', 1);
INSERT INTO `ipm_list_column` VALUES (504, 5, 'serverDurDelay', '服务端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (505, 5, 'clientDurDelay', '客户端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (506, 5, 'rtt', '链路时延RTT', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (507, 5, 'httpResponseDelay', 'SQL处理时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (508, 5, 'ethernetTraffic', 'Oracle服务流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (509, 5, 'l7SessionCountTotal', 'SQL应用会话数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (510, 5, 'http400Count', 'SQL错误返回码数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (511, 5, 'failRespRatio', 'SQL错误返回码比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (512, 5, 'noRespCount', 'SQL未响应数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (513, 5, 'noRespRatio', 'SQL未响应比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (514, 5, 'netPktLostRatio', '网络通信丢包率', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (602, 6, 'name', '业务名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (603, 6, 'alarmLevel', '未响应告警数量', '', 1);
INSERT INTO `ipm_list_column` VALUES (604, 6, 'serverDurDelay', '服务端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (605, 6, 'clientDurDelay', '客户端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (606, 6, 'rtt', '链路时延RTT', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (607, 6, 'httpResponseDelay', 'SQL处理时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (608, 6, 'ethernetTraffic', 'MySql服务流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (609, 6, 'l7SessionCountTotal', 'SQL应用会话数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (610, 6, 'http400Count', 'SQL错误返回码数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (611, 6, 'failRespRatio', 'SQL错误返回码比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (612, 6, 'noRespCount', 'SQL未响应数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (613, 6, 'noRespRatio', 'SQL未响应比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (614, 6, 'netPktLostRatio', '网络通信丢包率', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (702, 7, 'name', '业务名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (703, 7, 'alarmLevel', '未响应告警数量', '', 1);
INSERT INTO `ipm_list_column` VALUES (704, 7, 'serverDurDelay', '服务端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (705, 7, 'clientDurDelay', '客户端通信时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (706, 7, 'rtt', '链路时延RTT', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (707, 7, 'httpResponseDelay', 'SQL处理时延', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (708, 7, 'ethernetTraffic', 'SQLServer服务流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (709, 7, 'l7SessionCountTotal', 'SQL应用会话数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (710, 7, 'http400Count', 'SQL错误返回码数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (711, 7, 'failRespRatio', 'SQL错误返回码比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (712, 7, 'noRespCount', 'SQL未响应数量', 'SUM', 0);
INSERT INTO `ipm_list_column` VALUES (713, 7, 'noRespRatio', 'SQL未响应比率', 'AVG', 0);
INSERT INTO `ipm_list_column` VALUES (714, 7, 'netPktLostRatio', '网络通信丢包率', 'AVG', 0);

INSERT INTO `ipm_list_column` VALUES (1401, 14, 'begintime', '开始时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1402, 14, 'endtimewithpayload', '结束时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1403, 14, 'ipmCenterName', 'XPM服务器名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1404, 14, 'name', '观察点名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1405, 14, 'serverip', '服务端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1406, 14, 'port', '服务端端口', '', 0);
INSERT INTO `ipm_list_column` VALUES (1407, 14, 'client', '客户端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1408, 14, 'clientport', '客户端端口', '', 0);
INSERT INTO `ipm_list_column` VALUES (1409, 14, 'url', 'URL', '', 1);
INSERT INTO `ipm_list_column` VALUES (1410, 14, 'bytes', '字节数', '', 1);
INSERT INTO `ipm_list_column` VALUES (1411, 14, 'protocol', '协议', '', 0);
INSERT INTO `ipm_list_column` VALUES (1412, 14, 'method', '命令类型', '', 0);
INSERT INTO `ipm_list_column` VALUES (1413, 14, 'httpreturncode', 'HTTP返回码', '', 1);
INSERT INTO `ipm_list_column` VALUES (1414, 14, 'contenttype', '加载类型', '', 0);
INSERT INTO `ipm_list_column` VALUES (1415, 14, 'pageload', 'URL负载传输时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1416, 14, 'appllatency', '响应时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1417, 14, 'clientlatency', '客户端通信时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1418, 14, 'serverlatency', '服务端通信时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1419, 14, 'rtt', '网络通信时延RTT', '', 1);
INSERT INTO `ipm_list_column` VALUES (1420, 14, 'forward', '源客户端', '', 0);
INSERT INTO `ipm_list_column` VALUES (1421, 14, 'vlanId', 'VLAN ID', '', 0);
INSERT INTO `ipm_list_column` VALUES (1422, 14, 'req', '请求报文', '', 0);
INSERT INTO `ipm_list_column` VALUES (1423, 14, 'res', '响应报文', '', 0);

INSERT INTO `ipm_list_column` VALUES (1501, 15, 'begintime', '开始时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1502, 15, 'endtime', '结束时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1503, 15, 'ipmCenterName', 'XPM服务器名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1504, 15, 'name', '观察点名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1505, 15, 'server', '服务端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1506, 15, 'port', '服务端端口', '', 0);
INSERT INTO `ipm_list_column` VALUES (1507, 15, 'client', '客户端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1508, 15, 'clientport', '客户端端口', '', 0);
INSERT INTO `ipm_list_column` VALUES (1509, 15, 'dbname', '数据库名', '', 0);
INSERT INTO `ipm_list_column` VALUES (1510, 15, 'user', '用户名', '', 0);
INSERT INTO `ipm_list_column` VALUES (1511, 15, 'sqlquery', 'SQL语句', '', 1);
INSERT INTO `ipm_list_column` VALUES (1512, 15, 'responsecode', '返回码', '', 1);
INSERT INTO `ipm_list_column` VALUES (1513, 15, 'responsemsg', '返回消息', '', 0);
INSERT INTO `ipm_list_column` VALUES (1514, 15, 'bytes', '字节数', '', 0);
INSERT INTO `ipm_list_column` VALUES (1515, 15, 'queryduration', 'SQL处理时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1516, 15, 'clientlatency', '客户端通信时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1517, 15, 'serverlatency', '服务端通信时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1518, 15, 'rtt', '网络通信时延RTT', '', 1);

INSERT INTO `ipm_list_column` VALUES (1601, 16, 'begintime', '开始时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1602, 16, 'endtime', '结束时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1603, 16, 'ipmCenterName', 'XPM服务器名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1604, 16, 'name', '观察点名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1605, 16, 'server', '服务端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1606, 16, 'port', '服务端端口', '', 0);
INSERT INTO `ipm_list_column` VALUES (1607, 16, 'client', '客户端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1608, 16, 'clientport', '客户端端口', '', 0);
INSERT INTO `ipm_list_column` VALUES (1609, 16, 'dbname', '数据库名', '', 0);
INSERT INTO `ipm_list_column` VALUES (1610, 16, 'user', '用户名', '', 0);
INSERT INTO `ipm_list_column` VALUES (1611, 16, 'sqlquery', 'SQL语句', '', 1);
INSERT INTO `ipm_list_column` VALUES (1612, 16, 'responsecode', '返回码', '', 1);
INSERT INTO `ipm_list_column` VALUES (1613, 16, 'responsemsg', '返回消息', '', 0);
INSERT INTO `ipm_list_column` VALUES (1614, 16, 'bytes', '字节数', '', 0);
INSERT INTO `ipm_list_column` VALUES (1615, 16, 'queryduration', 'SQL处理时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1616, 16, 'clientlatency', '客户端通信时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1617, 16, 'serverlatency', '服务端通信时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1618, 16, 'rtt', '网络通信时延RTT', '', 1);

INSERT INTO `ipm_list_column` VALUES (1701, 17, 'begintime', '开始时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1702, 17, 'endtime', '结束时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1703, 17, 'ipmCenterName', 'XPM服务器名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1704, 17, 'name', '观察点名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1705, 17, 'server', '服务端IP', '', 1); 
INSERT INTO `ipm_list_column` VALUES (1706, 17, 'port', '服务端端口', '', 0);
INSERT INTO `ipm_list_column` VALUES (1707, 17, 'client', '客户端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1708, 17, 'clientport', '客户端端口', '', 0);
INSERT INTO `ipm_list_column` VALUES (1709, 17, 'dbname', '数据库名', '', 0);
INSERT INTO `ipm_list_column` VALUES (1710, 17, 'user', '用户名', '', 0);
INSERT INTO `ipm_list_column` VALUES (1711, 17, 'sqlquery', 'SQL语句', '', 1);
INSERT INTO `ipm_list_column` VALUES (1712, 17, 'responsecode', '返回码', '', 1);
INSERT INTO `ipm_list_column` VALUES (1713, 17, 'responsemsg', '返回消息', '', 0);
INSERT INTO `ipm_list_column` VALUES (1714, 17, 'bytes', '字节数', '', 0);
INSERT INTO `ipm_list_column` VALUES (1715, 17, 'queryduration', 'SQL处理时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1716, 17, 'clientlatency', '客户端通信时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1717, 17, 'serverlatency', '服务端通信时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1718, 17, 'rtt', '网络通信时延RTT', '', 1);

INSERT INTO `ipm_list_column` VALUES (802, 8, 'name', '业务名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (803, 8, 'alarmLevel', '未响应告警数量', '', 1);
INSERT INTO `ipm_list_column` VALUES (804, 8, 'sessionNum', 'URL访问量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (805, 8, 'ethernetTraffic', '流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (806, 8, 'pageloadDelay', 'URL负载传输时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (807, 8, 'responseDelay', 'Web服务响应时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (808, 8, 'rtt', '网络通信时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (809, 8, 'url400Count', '400错误数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (810, 8, 'http500Count', '500错误数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (811, 8, 'failRespRatio', 'HTTP错误返回码比率', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (1802, 18, 'name', '业务名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (1803, 18, 'url', 'URL', '', 1);
INSERT INTO `ipm_list_column` VALUES (1804, 18, 'sessionNum', 'URL访问量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1805, 18, 'ethernetTraffic', '流量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1806, 18, 'pageloadDelay', 'URL负载传输时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1807, 18, 'responseDelay', 'Web服务响应时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1808, 18, 'rtt', '网络通信时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (1809, 18, 'url400Count', '400错误数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1810, 18, 'http500Count', '500错误数量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (1811, 18, 'failRespRatio', 'HTTP错误返回码比率', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (902, 9, 'name', '业务名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (903, 9, 'alarmLevel', '未响应告警数量', '', 1);
INSERT INTO `ipm_list_column` VALUES (904, 9, 'transCount', '交易量', 'SUM', 1);
INSERT INTO `ipm_list_column` VALUES (905, 9, 'transDelay', '交易时延', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (906, 9, 'successRatio', '交易成功率', 'AVG', 1);
INSERT INTO `ipm_list_column` VALUES (907, 9, 'respRatio', '交易响应率', 'AVG', 1);

INSERT INTO `ipm_list_column` VALUES (1902, 19, 'transTime', '时间', '', 1);
INSERT INTO `ipm_list_column` VALUES (1903, 19, 'ipmCenterName', 'XPM服务器名称', '', 0);
INSERT INTO `ipm_list_column` VALUES (1904, 19, 'name', '观察点名称', '', 1);
INSERT INTO `ipm_list_column` VALUES (1905, 19, 'server', '服务端IP', '', 1);
INSERT INTO `ipm_list_column` VALUES (1906, 19, 'serverPort', '服务端端口', '', 1);
INSERT INTO `ipm_list_column` VALUES (1907, 19, 'client', '客户端', '', 1);
INSERT INTO `ipm_list_column` VALUES (1908, 19, 'clientPort', '客户端端口', '', 1);
INSERT INTO `ipm_list_column` VALUES (1909, 19, 'delay', '响应时延', '', 1);
INSERT INTO `ipm_list_column` VALUES (1910, 19, 'source', '报文', '', 1);
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
INSERT INTO `ipm_user_list_column` VALUES (40, 1, 1007);
INSERT INTO `ipm_user_list_column` VALUES (41, 1, 1008);
INSERT INTO `ipm_user_list_column` VALUES (42, 1, 1018);
INSERT INTO `ipm_user_list_column` VALUES (43, 1, 1025);
INSERT INTO `ipm_user_list_column` VALUES (44, 1, 1033);
INSERT INTO `ipm_user_list_column` VALUES (45, 1, 1040);
INSERT INTO `ipm_user_list_column` VALUES (46, 1, 1102);
INSERT INTO `ipm_user_list_column` VALUES (47, 1, 1103);
INSERT INTO `ipm_user_list_column` VALUES (48, 1, 1104);
INSERT INTO `ipm_user_list_column` VALUES (49, 1, 1112);
INSERT INTO `ipm_user_list_column` VALUES (50, 1, 1118);
INSERT INTO `ipm_user_list_column` VALUES (51, 1, 1126);
INSERT INTO `ipm_user_list_column` VALUES (52, 1, 1133);
INSERT INTO `ipm_user_list_column` VALUES (53, 1, 1202);
INSERT INTO `ipm_user_list_column` VALUES (54, 1, 1203);
INSERT INTO `ipm_user_list_column` VALUES (55, 1, 1204);
INSERT INTO `ipm_user_list_column` VALUES (56, 1, 1214);
INSERT INTO `ipm_user_list_column` VALUES (57, 1, 1220);
INSERT INTO `ipm_user_list_column` VALUES (58, 1, 1228);
INSERT INTO `ipm_user_list_column` VALUES (59, 1, 1235);
INSERT INTO `ipm_user_list_column` VALUES (60, 1, 1301);
INSERT INTO `ipm_user_list_column` VALUES (61, 1, 1305);
INSERT INTO `ipm_user_list_column` VALUES (62, 1, 1308);
INSERT INTO `ipm_user_list_column` VALUES (63, 1, 1310);
INSERT INTO `ipm_user_list_column` VALUES (64, 1, 1311);
INSERT INTO `ipm_user_list_column` VALUES (65, 1, 1401);
INSERT INTO `ipm_user_list_column` VALUES (66, 1, 1402);
INSERT INTO `ipm_user_list_column` VALUES (67, 1, 1405);
INSERT INTO `ipm_user_list_column` VALUES (68, 1, 1407);
INSERT INTO `ipm_user_list_column` VALUES (69, 1, 1409);
INSERT INTO `ipm_user_list_column` VALUES (70, 1, 1410);
INSERT INTO `ipm_user_list_column` VALUES (71, 1, 1413);
INSERT INTO `ipm_user_list_column` VALUES (72, 1, 1415);
INSERT INTO `ipm_user_list_column` VALUES (73, 1, 1416);
INSERT INTO `ipm_user_list_column` VALUES (74, 1, 1417);
INSERT INTO `ipm_user_list_column` VALUES (75, 1, 1418);
INSERT INTO `ipm_user_list_column` VALUES (76, 1, 1419);
INSERT INTO `ipm_user_list_column` VALUES (77, 1, 1501);
INSERT INTO `ipm_user_list_column` VALUES (78, 1, 1502);
INSERT INTO `ipm_user_list_column` VALUES (79, 1, 1505);
INSERT INTO `ipm_user_list_column` VALUES (80, 1, 1507);
INSERT INTO `ipm_user_list_column` VALUES (81, 1, 1511);
INSERT INTO `ipm_user_list_column` VALUES (82, 1, 1512);
INSERT INTO `ipm_user_list_column` VALUES (83, 1, 1515);
INSERT INTO `ipm_user_list_column` VALUES (84, 1, 1516);
INSERT INTO `ipm_user_list_column` VALUES (85, 1, 1517);
INSERT INTO `ipm_user_list_column` VALUES (86, 1, 1518);
INSERT INTO `ipm_user_list_column` VALUES (87, 1, 1601);
INSERT INTO `ipm_user_list_column` VALUES (88, 1, 1602);
INSERT INTO `ipm_user_list_column` VALUES (89, 1, 1605);
INSERT INTO `ipm_user_list_column` VALUES (90, 1, 1607);
INSERT INTO `ipm_user_list_column` VALUES (91, 1, 1611);
INSERT INTO `ipm_user_list_column` VALUES (92, 1, 1612);
INSERT INTO `ipm_user_list_column` VALUES (93, 1, 1615);
INSERT INTO `ipm_user_list_column` VALUES (94, 1, 1616);
INSERT INTO `ipm_user_list_column` VALUES (95, 1, 1617);
INSERT INTO `ipm_user_list_column` VALUES (96, 1, 1618);
INSERT INTO `ipm_user_list_column` VALUES (97, 1, 1701);
INSERT INTO `ipm_user_list_column` VALUES (98, 1, 1702);
INSERT INTO `ipm_user_list_column` VALUES (99, 1, 1705);
INSERT INTO `ipm_user_list_column` VALUES (100, 1, 1707);
INSERT INTO `ipm_user_list_column` VALUES (101, 1, 1711);
INSERT INTO `ipm_user_list_column` VALUES (102, 1, 1712);
INSERT INTO `ipm_user_list_column` VALUES (103, 1, 1715);
INSERT INTO `ipm_user_list_column` VALUES (104, 1, 1716);
INSERT INTO `ipm_user_list_column` VALUES (105, 1, 1717);
INSERT INTO `ipm_user_list_column` VALUES (106, 1, 1718);
INSERT INTO `ipm_user_list_column` VALUES (107, 1, 1802);
INSERT INTO `ipm_user_list_column` VALUES (108, 1, 1803);
INSERT INTO `ipm_user_list_column` VALUES (109, 1, 1804);
INSERT INTO `ipm_user_list_column` VALUES (110, 1, 1805);
INSERT INTO `ipm_user_list_column` VALUES (111, 1, 1806);
INSERT INTO `ipm_user_list_column` VALUES (112, 1, 1807);
INSERT INTO `ipm_user_list_column` VALUES (113, 1, 1808);
INSERT INTO `ipm_user_list_column` VALUES (114, 1, 1809);
INSERT INTO `ipm_user_list_column` VALUES (115, 1, 1810);
INSERT INTO `ipm_user_list_column` VALUES (116, 1, 1811);
INSERT INTO `ipm_user_list_column` VALUES (117, 1, 1902);
INSERT INTO `ipm_user_list_column` VALUES (118, 1, 1904);
INSERT INTO `ipm_user_list_column` VALUES (119, 1, 1905);
INSERT INTO `ipm_user_list_column` VALUES (120, 1, 1906);
INSERT INTO `ipm_user_list_column` VALUES (121, 1, 1907);
INSERT INTO `ipm_user_list_column` VALUES (122, 1, 1908);
INSERT INTO `ipm_user_list_column` VALUES (123, 1, 1909);
INSERT INTO `ipm_user_list_column` VALUES (124, 1, 1910);
-- --------------------
-- 系统用户配置数据 END 
-- --------------------


-- --------------------
-- kpi数据 START 
-- 单位说明: 
--  ratio:代表百分比类
--  flow:代表流量类
--  pps:代表速率类
--  num:代表数量类
--  ms:代表时延类
-- --------------------
INSERT INTO `ipm_kpis` VALUES (1, 'ethernetTraffic', '网络流量', 'flow', '网络访问量增大，可能的原因是服务导致、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (2, 'sessionNum', '会话数量', 'num', '访问增多，并发量增大。可能的原因是热点、业务集中、网络拥塞、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (3, 'ethernetPkts', '数据包速率', 'pps', '数据包速率超过设定阈值，可能的原因是：1、访问量突增；2、小包突增等引起');
INSERT INTO `ipm_kpis` VALUES (4, 'serverDurDelay', '服务端通信时延', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (5, 'clientDurDelay', '客户端通信时延', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (6, 'rtt', '链路时延RTT', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (7, 'serverConDelay', '服务端握手时延', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (8, 'clientConDelay', '客户端握手时延', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (9, 'netPktLostRatio', '网络丢包率', 'ratio', '服务端丢包率超过设定阈值。可能由于网络链路负载过高，网络设备故障，网络设备配置错误、服务端或者客户端零窗口现象等引起');
INSERT INTO `ipm_kpis` VALUES (10, 'serverPktLostRatio', '服务端丢包率', 'ratio', '服务端丢包率超过设定阈值。可能由于网络链路负载过高，网络设备故障，网络设备配置错误、服务端零窗口现象等引起');
INSERT INTO `ipm_kpis` VALUES (11, 'clientPktLostRatio', '客户端丢包率', 'ratio', '客户端丢包率超过设定阈值。可能由于网络链路负载过高，网络设备故障，网络设备配置错误、客户端零窗口现象等引起');
INSERT INTO `ipm_kpis` VALUES (12, 'rstPkts', 'TCP连接重置', 'num', '可能的原因是链路质量、服务端应用处理能力等');
INSERT INTO `ipm_kpis` VALUES (13, 'synPkts', 'TCP连接发起', 'num', '访问增多，并发量增大。可能的原因是热点、业务集中、网络拥塞、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (14, 'qos', '服务质量', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (16, 'loadDelay', '负载传输时延', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (17, 'finPkts', 'FIN包数', 'num', '可能的原因是链路质量、服务端应用处理能力等');
INSERT INTO `ipm_kpis` VALUES (19, 'tinyPkts', '小包速率', 'pps', '小包速率超过设定阈值，可能由访问量突增、遭受攻击、ARP流量、ICMP流量等引起');
INSERT INTO `ipm_kpis` VALUES (20, 'tinyPktsRatio', '小包比率', 'ratio', '小包比率超过设定阈值，可能由访问量突增、遭受攻击、ARP流量、ICMP流量等引起');
INSERT INTO `ipm_kpis` VALUES (21, 'tcpTraffic', 'TCP流量', 'flow', '通过TCP协议的访问量增大');
INSERT INTO `ipm_kpis` VALUES (22, 'udpTraffic', 'UDP流量', 'flow', '通过UDP协议的访问量增大');
INSERT INTO `ipm_kpis` VALUES (23, 'netPktLost', '网络丢包数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (24, 'serverPktLost', '服务端丢包数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (25, 'serverPkt', '服务端包速率', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (26, 'clientPktLost', '客户端丢包数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (27, 'clientPkt', '客户端包速率', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (28, 'unKnowSerTraffic', '未定义服务端流量', 'flow', '未知服务的访问量增大');
INSERT INTO `ipm_kpis` VALUES (29, 'unKnowCliTraffic', '未定义客户端流量', 'flow', '未知客户端的访问量增大');
INSERT INTO `ipm_kpis` VALUES (30, 'arpTraffic', 'ARP流量', 'flow', 'ARP流量超过设定阈值，可能由ARP风暴引起');
INSERT INTO `ipm_kpis` VALUES (31, 'arpPkts', 'ARP包速率', 'pps', 'ARP包PPS超过设定阈值，可能由ARP风暴引起');
INSERT INTO `ipm_kpis` VALUES (32, 'bandWidthRatio', '流控带宽占用率 ', 'ratio', '带宽占用率超过设定阈值，由流量突增引起');
INSERT INTO `ipm_kpis` VALUES (33, 'unknowUserTraffic', '未识别用户协议流量', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (34, 'unknowPublicTraffic', '未识别公有协议流量', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (35, 'l7SessionCountTotal', '应用会话数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (36, 'responseDelay', '应用处理时延', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (37, 'pageloadDelay', '加载时延', 'ms', NULL);
INSERT INTO `ipm_kpis` VALUES (38, 'http400Count', '错误返回码数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (39, 'bandWidth', '设置带宽', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (43, 'serverRetransDelay', '服务端重传时延', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (44, 'clientRetransDelay', '客户端重传时延', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
INSERT INTO `ipm_kpis` VALUES (45, 'failRespRatio', '错误返回码比率', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (46, 'noRespCount', '未响应数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (47, 'noRespRatio', '未响应比率', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (48, 'http500Count', '500错误返回码数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (49, 'usability', '应用可用性', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (50, 'avgPktsLen', '平均包长', 'flowB', '平均包长超过设定阈值');
INSERT INTO `ipm_kpis` VALUES (51, 'zeroWinCount', '零窗口包数', 'num', '服务端或者客户端的接收数据包的缓冲区已满');
-- 报文专用KPI START
INSERT INTO `ipm_kpis` VALUES (52, 'transCount', '交易量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (53, 'transDelay', '交易时延', 'ms', NULL);
INSERT INTO `ipm_kpis` VALUES (54, 'successRatio', '交易成功率', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (55, 'respRatio', '交易响应率', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (56, 'failCount', '失败量', 'num', NULL);
-- 报文专用KPI END
INSERT INTO `ipm_kpis` VALUES (57, 'synAckPkts', 'TCP连接响应', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (58, 'fin1Pkts', '主动关闭连接数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (59, 'fin2Pkts', '被动关闭连接数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (60, 'inTraffic', '进流量', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (61, 'outTraffic', '出流量', 'flow', NULL);
INSERT INTO `ipm_kpis` VALUES (62, 'url400Count', '400错误返回码数量', 'num', NULL);
INSERT INTO `ipm_kpis` VALUES (63, 'httpResponseDelay', '响应时延', 'ms', '可能的原因是：软硬件故障、并发量增加、链路质量变差、网络攻击等');
-- 新功能 START
INSERT INTO `ipm_kpis` VALUES (90, 'probeSynchronous', '探针同步', 'num', '可能的原因是：同步超时等');
INSERT INTO `ipm_kpis` VALUES (91, 'probeNetwork', '探针网络', 'num', '可能的原因是：网络问题等');
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
INSERT INTO `ipm_kpis` VALUES (108, 'mem_total', '内存总量', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (109, 'mem_used', '已用内存', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (110, 'mem_free', '空闲内存', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (111, 'mem_buffers', '内核缓存', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (112, 'swap_total', 'SWAP总量', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (113, 'swap_used', 'SWAP使用', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (114, 'swap_free', 'SWAP空闲', 'flowB1024', NULL);
INSERT INTO `ipm_kpis` VALUES (115, 'swap_cached', '磁盘缓冲', 'flowB1024', NULL);
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
INSERT INTO `ipm_kpis` VALUES (132, 'load_1', '1分钟负载', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (133, 'load_5', '5分钟负载', 'ratio', NULL);
INSERT INTO `ipm_kpis` VALUES (134, 'load_15', '15分钟负载', 'ratio', NULL);
-- 系统监控专用KPI END
-- --------------------
-- kpi数据 END 
-- --------------------


-- --------------------
-- 绘图选项数据 START 
-- --------------------
INSERT INTO `ipm_plot_option` VALUES (1, 10, 3, 1, '网络流量', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (2, 10, 4, 2, '会话数量', 1, 0, 'SUM', 3);
INSERT INTO `ipm_plot_option` VALUES (3, 10, 5, 3, '数据包速率', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (4, 10, 2, 4, '服务端通信时延', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (5, 10, 2, 5, '客户端通信时延', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (6, 10, 2, 6, '链路时延RTT', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (7, 10, 2, 7, '服务端握手时延', 1, 1, 'AVG', 8);
INSERT INTO `ipm_plot_option` VALUES (8, 10, 2, 8, '客户端握手时延', 1, 1, 'AVG', 9);
INSERT INTO `ipm_plot_option` VALUES (9, 10, 5, 9, '网络丢包率', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (10, 10, 5, 10, '服务端丢包率', 1, 1, 'AVG', 11);
INSERT INTO `ipm_plot_option` VALUES (11, 10, 5, 11, '客户端丢包率', 1, 1, 'AVG', 12);
INSERT INTO `ipm_plot_option` VALUES (12, 10, 4, 12, 'TCP连接重置', 1, 1, 'SUM', 13);
INSERT INTO `ipm_plot_option` VALUES (15, 10, 4, 13, 'TCP连接发起', 1, 1, 'SUM', 16);
INSERT INTO `ipm_plot_option` VALUES (17, 10, 4, 17, 'FIN包数', 1, 1, 'SUM', 18);
INSERT INTO `ipm_plot_option` VALUES (19, 10, 5, 19, '小包速率', 1, 1, 'AVG', 20);
INSERT INTO `ipm_plot_option` VALUES (20, 10, 5, 20, '小包比率', 1, 1, 'AVG', 21);
INSERT INTO `ipm_plot_option` VALUES (21, 10, 3, 21, 'TCP流量', 1, 1, 'SUM', 22);
INSERT INTO `ipm_plot_option` VALUES (22, 10, 3, 22, 'UDP流量', 1, 1, 'SUM', 23);
INSERT INTO `ipm_plot_option` VALUES (23, 10, 2, 36, '应用处理时延', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (28, 10, 3, 28, '未定义服务端流量', 1, 1, 'SUM', 29);
INSERT INTO `ipm_plot_option` VALUES (29, 10, 3, 29, '未定义客户端流量', 1, 1, 'SUM', 30);
INSERT INTO `ipm_plot_option` VALUES (30, 10, 6, 1, '已定义用户协议分布', 1, 0, NULL, 31);
INSERT INTO `ipm_plot_option` VALUES (31, 10, 6, 1, '公有协议分布', 1, 0, NULL, 32);
INSERT INTO `ipm_plot_option` VALUES (32, 11, 3, 1, '网络流量', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (33, 11, 4, 2, '会话数量', 1, 0, 'SUM', 3);
INSERT INTO `ipm_plot_option` VALUES (34, 11, 5, 3, '数据包速率', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (35, 11, 2, 4, '服务端通信时延', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (36, 11, 2, 5, '客户端通信时延', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (37, 11, 2, 6, '链路时延RTT', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (38, 11, 2, 7, '服务端握手时延', 1, 1, 'AVG', 8);
INSERT INTO `ipm_plot_option` VALUES (39, 11, 2, 8, '客户端握手时延', 1, 1, 'AVG', 9);
INSERT INTO `ipm_plot_option` VALUES (40, 11, 5, 9, '网络丢包率', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (41, 11, 5, 10, '服务端丢包率', 1, 1, 'AVG', 11);
INSERT INTO `ipm_plot_option` VALUES (42, 11, 5, 11, '客户端丢包率', 1, 1, 'AVG', 12);
INSERT INTO `ipm_plot_option` VALUES (43, 11, 4, 12, 'TCP连接重置', 1, 1, 'SUM', 13);
INSERT INTO `ipm_plot_option` VALUES (46, 11, 4, 13, 'TCP连接发起', 1, 1, 'SUM', 16);
INSERT INTO `ipm_plot_option` VALUES (48, 11, 4, 17, 'FIN包数', 1, 1, 'SUM', 18);
INSERT INTO `ipm_plot_option` VALUES (50, 11, 5, 19, '小包速率', 1, 1, 'AVG', 20);
INSERT INTO `ipm_plot_option` VALUES (51, 11, 5, 20, '小包比率', 1, 1, 'AVG', 21);
INSERT INTO `ipm_plot_option` VALUES (52, 11, 3, 21, 'TCP流量', 1, 1, 'SUM', 22);
INSERT INTO `ipm_plot_option` VALUES (53, 11, 3, 22, 'UDP流量', 1, 1, 'SUM', 23);
INSERT INTO `ipm_plot_option` VALUES (54, 11, 2, 36, '应用处理时延', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (58, 11, 6, 32, '流控带宽占用率', 1, 1, 'AVG', 28);
INSERT INTO `ipm_plot_option` VALUES (59, 11, 6, 1, '已定义用户协议分布', 1, 0, NULL, 31);
INSERT INTO `ipm_plot_option` VALUES (60, 12, 3, 1, '网络流量', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (61, 12, 4, 2, '会话数量', 1, 0, 'SUM', 3);
INSERT INTO `ipm_plot_option` VALUES (62, 12, 5, 3, '数据包速率', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (63, 12, 2, 4, '服务端通信时延', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (64, 12, 2, 5, '客户端通信时延', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (65, 12, 2, 6, '链路时延RTT', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (66, 12, 2, 7, '服务端握手时延', 1, 1, 'AVG', 8);
INSERT INTO `ipm_plot_option` VALUES (67, 12, 2, 8, '客户端握手时延', 1, 1, 'AVG', 9);
INSERT INTO `ipm_plot_option` VALUES (68, 12, 5, 9, '网络丢包率', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (69, 12, 5, 10, '服务端丢包率', 1, 1, 'AVG', 11);
INSERT INTO `ipm_plot_option` VALUES (70, 12, 5, 11, '客户端丢包率', 1, 1, 'AVG', 12);
INSERT INTO `ipm_plot_option` VALUES (71, 12, 4, 12, 'TCP连接重置', 1, 1, 'SUM', 13);
INSERT INTO `ipm_plot_option` VALUES (74, 12, 4, 13, 'TCP连接发起', 1, 1, 'SUM', 16);
INSERT INTO `ipm_plot_option` VALUES (76, 12, 4, 17, 'FIN包数', 1, 1, 'SUM', 18);
INSERT INTO `ipm_plot_option` VALUES (78, 12, 5, 19, '小包速率', 1, 1, 'AVG', 20);
INSERT INTO `ipm_plot_option` VALUES (79, 12, 5, 20, '小包比率', 1, 1, 'AVG', 21);
INSERT INTO `ipm_plot_option` VALUES (80, 12, 3, 21, 'TCP流量', 1, 1, 'SUM', 22);
INSERT INTO `ipm_plot_option` VALUES (81, 12, 3, 22, 'UDP流量', 1, 1, 'SUM', 23);
INSERT INTO `ipm_plot_option` VALUES (82, 12, 2, 36, '应用处理时延', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (84, 10, 6, 30, 'ARP流量', 1, 1, 'SUM', 33);
INSERT INTO `ipm_plot_option` VALUES (85, 10, 6, 31, 'ARP包速率', 1, 1, 'AVG', 34);
INSERT INTO `ipm_plot_option` VALUES (86, 10, 1, 0, '未响应告警数量', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (87, 11, 1, 0, '未响应告警数量', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (88, 12, 1, 0, '未响应告警数量', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (89, 11, 6, 1, '公有协议分布', 1, 0, NULL, 30);
INSERT INTO `ipm_plot_option` VALUES (90, 11, 3, 28, '未定义服务端流量', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (94, 12, 3, 29, '未定义客户端流量', 1, 1, 'SUM', 27);
INSERT INTO `ipm_plot_option` VALUES (97, 12, 6, 32, '流控带宽占用率', 1, 1, 'AVG', 30);
INSERT INTO `ipm_plot_option` VALUES (101, 4, 1, 0, '未响应告警数量', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (102, 4, 3, 1, '网络流量', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (105, 4, 2, 4, '服务端通信时延', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (106, 4, 2, 5, '客户端通信时延', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (107, 4, 2, 6, '链路时延RTT', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (110, 4, 5, 9, '网络通信丢包率', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (131, 4, 4, 35, 'HTTP应用会话数量', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (132, 4, 2, 63, '服务响应时延', 1, 1, 'AVG', 32);
INSERT INTO `ipm_plot_option` VALUES (133, 4, 2, 37, 'URL负载传输时延', 1, 1, 'AVG', 33);
INSERT INTO `ipm_plot_option` VALUES (135, 5, 1, 0, '未响应告警数量', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (136, 5, 3, 1, 'Oracle服务流量', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (139, 5, 2, 4, '服务端通信时延', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (140, 5, 2, 5, '客户端通信时延', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (141, 5, 2, 6, '链路时延RTT', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (144, 5, 5, 9, '网络通信丢包率', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (158, 5, 2, 63, 'SQL处理时延', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (165, 5, 4, 35, 'SQL应用会话数量', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (168, 5, 4, 38, 'SQL错误返回码数量', 1, 1, 'SUM', 34);
INSERT INTO `ipm_plot_option` VALUES (169, 6, 1, 0, '未响应告警数量', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (170, 6, 3, 1, 'MySql服务流量', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (173, 6, 2, 4, '服务端通信时延', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (174, 6, 2, 5, '客户端通信时延', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (175, 6, 2, 6, '链路时延RTT', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (178, 6, 5, 9, '网络通信丢包率', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (192, 6, 2, 63, 'SQL处理时延', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (199, 6, 4, 35, 'SQL应用会话数量', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (202, 6, 4, 38, 'SQL错误返回码数量', 1, 1, 'SUM', 34);
INSERT INTO `ipm_plot_option` VALUES (203, 7, 1, 0, '未响应告警数量', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (204, 7, 3, 1, 'SQLServer服务流量', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (207, 7, 2, 4, '服务端通信时延', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (208, 7, 2, 5, '客户端通信时延', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (209, 7, 2, 6, '链路时延RTT', 1, 1, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (212, 7, 5, 9, '网络通信丢包率', 1, 1, 'AVG', 10);
INSERT INTO `ipm_plot_option` VALUES (226, 7, 2, 63, 'SQL处理时延', 1, 1, 'AVG', 24);
INSERT INTO `ipm_plot_option` VALUES (233, 7, 4, 35, 'SQL应用会话数量', 1, 1, 'SUM', 31);
INSERT INTO `ipm_plot_option` VALUES (236, 7, 4, 38, 'SQL错误返回码数量', 1, 1, 'SUM', 34);
INSERT INTO `ipm_plot_option` VALUES (237, 4, 6, 0, 'URL被访问次数排名', 1, 0, 'SUM', 35);
INSERT INTO `ipm_plot_option` VALUES (238, 4, 6, 0, 'URL加载时延排名', 1, 0, 'AVG', 36);
INSERT INTO `ipm_plot_option` VALUES (239, 4, 6, 0, '400错误最多的URL排名', 1, 0, 'SUM', 37);
INSERT INTO `ipm_plot_option` VALUES (240, 4, 6, 0, '500错误最多的URL排名', 1, 0, 'SUM', 38);
INSERT INTO `ipm_plot_option` VALUES (241, 5, 6, 0, 'SQL语句耗时排名', 1, 0, 'AVG', 35);
INSERT INTO `ipm_plot_option` VALUES (242, 5, 6, 0, '非零返回码最多的SQL语句排名', 1, 0, 'SUM', 36);
INSERT INTO `ipm_plot_option` VALUES (243, 6, 6, 0, 'SQL语句耗时排名', 1, 0, 'AVG', 35);
INSERT INTO `ipm_plot_option` VALUES (244, 6, 6, 0, '非零返回码最多的SQL语句排名', 1, 0, 'SUM', 36);
INSERT INTO `ipm_plot_option` VALUES (245, 7, 6, 0, 'SQL语句耗时排名', 1, 0, 'AVG', 35);
INSERT INTO `ipm_plot_option` VALUES (246, 7, 6, 0, '非零返回码最多的SQL语句排名', 1, 0, 'SUM', 36);
INSERT INTO `ipm_plot_option` VALUES (267, 10, 2, 43, '服务端重传时延', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (268, 10, 2, 44, '客户端重传时延', 1, 1, 'AVG', 40);
INSERT INTO `ipm_plot_option` VALUES (269, 11, 2, 43, '服务端重传时延', 1, 1, 'AVG', 38);
INSERT INTO `ipm_plot_option` VALUES (270, 11, 2, 44, '客户端重传时延', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (271, 12, 2, 43, '服务端重传时延', 1, 1, 'AVG', 38);
INSERT INTO `ipm_plot_option` VALUES (272, 12, 2, 44, '客户端重传时延', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (273, 4, 4, 45, 'HTTP错误返回码比率', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (275, 4, 4, 47, 'HTTP未响应比率', 1, 1, 'AVG', 41);
INSERT INTO `ipm_plot_option` VALUES (276, 10, 6, 32, '流控带宽占用率', 1, 1, 'AVG', 26);
INSERT INTO `ipm_plot_option` VALUES (277, 5, 4, 45, 'SQL错误返回码比率', 1, 1, 'AVG', 37);
INSERT INTO `ipm_plot_option` VALUES (278, 5, 4, 46, 'SQL未响应数量', 1, 1, 'SUM', 38);
INSERT INTO `ipm_plot_option` VALUES (279, 5, 4, 47, 'SQL未响应比率', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (280, 6, 4, 45, 'SQL错误返回码比率', 1, 1, 'AVG', 37);
INSERT INTO `ipm_plot_option` VALUES (281, 6, 4, 46, 'SQL未响应数量', 1, 1, 'SUM', 38);
INSERT INTO `ipm_plot_option` VALUES (282, 6, 4, 47, 'SQL未响应比率', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (283, 7, 4, 45, 'SQL错误返回码比率', 1, 1, 'AVG', 37);
INSERT INTO `ipm_plot_option` VALUES (284, 7, 4, 46, 'SQL未响应数量', 1, 1, 'SUM', 38);
INSERT INTO `ipm_plot_option` VALUES (285, 7, 4, 47, 'SQL未响应比率', 1, 1, 'AVG', 39);
INSERT INTO `ipm_plot_option` VALUES (286, 8, 1, 0, '未响应告警数量', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (287, 8, 2, 6, '网络通信时延', 1, 1, 'AVG', 2);
INSERT INTO `ipm_plot_option` VALUES (288, 8, 2, 36, 'Web服务响应时延', 1, 1, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (289, 8, 2, 37, 'URL负载传输时延', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (290, 8, 3, 1, '网络流量', 1, 1, 'SUM', 5);
INSERT INTO `ipm_plot_option` VALUES (291, 8, 4, 2, '访问量', 1, 1, 'SUM', 6);
INSERT INTO `ipm_plot_option` VALUES (292, 8, 4, 62, '400错误返回码数量', 1, 1, 'SUM', 7);
INSERT INTO `ipm_plot_option` VALUES (293, 8, 4, 48, '500错误返回码数量', 1, 1, 'SUM', 8);
INSERT INTO `ipm_plot_option` VALUES (294, 8, 4, 45, 'HTTP错误返回码比率', 1, 1, 'AVG', 9);
INSERT INTO `ipm_plot_option` VALUES (295, 10, 5, 50, '平均包长', 1, 1, 'AVG', 41);
INSERT INTO `ipm_plot_option` VALUES (296, 10, 5, 51, '零窗口包数', 1, 1, 'SUM', 42);
INSERT INTO `ipm_plot_option` VALUES (297, 11, 5, 50, '平均包长', 1, 1, 'AVG', 40);
INSERT INTO `ipm_plot_option` VALUES (298, 11, 5, 51, '零窗口包数', 1, 1, 'SUM', 41);
INSERT INTO `ipm_plot_option` VALUES (299, 12, 5, 50, '平均包长', 1, 1, 'AVG', 40);
INSERT INTO `ipm_plot_option` VALUES (300, 12, 5, 51, '零窗口包数', 1, 1, 'SUM', 41);
INSERT INTO `ipm_plot_option` VALUES (301, 10, 5, 1, '包大小分布', 1, 0, 'SUM', 43);
INSERT INTO `ipm_plot_option` VALUES (302, 11, 5, 1, '包大小分布', 1, 0, 'SUM', 42);
INSERT INTO `ipm_plot_option` VALUES (303, 9, 1, 0, '未响应告警数量', 0, 1, 'SUM', 1);
INSERT INTO `ipm_plot_option` VALUES (304, 9, 4, 52, '交易量', 1, 1, 'SUM', 2);
INSERT INTO `ipm_plot_option` VALUES (305, 9, 2, 53, '交易时延', 1, 1, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (306, 9, 4, 54, '交易成功率', 1, 1, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (307, 9, 4, 55, '交易响应率', 1, 1, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (308, 10, 1, 0, '健康度', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (309, 11, 1, 0, '健康度', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (310, 12, 1, 0, '健康度', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (311, 4, 1, 0, '健康度', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (312, 5, 1, 0, '健康度', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (313, 6, 1, 0, '健康度', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (314, 7, 1, 0, '健康度', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (315, 8, 1, 0, '健康度', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (316, 9, 1, 0, '健康度', 0, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (317, 10, 2, 14, '服务质量', 1, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (318, 10, 2, 16, '负载传输时延', 1, 1, 'AVG', 1);
INSERT INTO `ipm_plot_option` VALUES (319, 12, 2, 14, '服务质量', 1, 1, 'AVG', 0);
INSERT INTO `ipm_plot_option` VALUES (320, 12, 2, 16, '负载传输时延', 1, 1, 'AVG', 1);
INSERT INTO `ipm_plot_option` VALUES (321, 10, 6, 14, '服务质量+会话数量', 1, 0, NULL, 44);
INSERT INTO `ipm_plot_option` VALUES (322, 9, 4, 56, '失败量', 1, 1, 'SUM', 3);
INSERT INTO `ipm_plot_option` VALUES (323, 10, 4, 2, '最大会话数量', 0, 1, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (324, 11, 4, 2, '最大会话数量', 0, 1, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (325, 12, 4, 2, '最大会话数量', 0, 1, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (326, 10, 4, 57, 'TCP连接响应', 1, 1, 'SUM', 17);
INSERT INTO `ipm_plot_option` VALUES (327, 10, 4, 58, '主动关闭连接数量', 1, 1, 'SUM', 19);
INSERT INTO `ipm_plot_option` VALUES (328, 10, 4, 59, '被动关闭连接数量', 1, 1, 'SUM', 20);
INSERT INTO `ipm_plot_option` VALUES (329, 10, 3, 60, '进流量', 1, 1, 'SUM', 24);
INSERT INTO `ipm_plot_option` VALUES (330, 10, 3, 61, '出流量', 1, 1, 'SUM', 25);
INSERT INTO `ipm_plot_option` VALUES (331, 11, 4, 57, 'TCP连接响应', 1, 1, 'SUM', 17);
INSERT INTO `ipm_plot_option` VALUES (332, 11, 4, 58, '主动关闭连接数量', 1, 1, 'SUM', 19);
INSERT INTO `ipm_plot_option` VALUES (333, 11, 4, 59, '被动关闭连接数量', 1, 1, 'SUM', 20);
INSERT INTO `ipm_plot_option` VALUES (334, 11, 3, 60, '进流量', 1, 1, 'SUM', 24);
INSERT INTO `ipm_plot_option` VALUES (335, 11, 3, 61, '出流量', 1, 1, 'SUM', 25);
INSERT INTO `ipm_plot_option` VALUES (336, 12, 4, 57, 'TCP连接响应', 1, 1, 'SUM', 17);
INSERT INTO `ipm_plot_option` VALUES (337, 12, 4, 58, '主动关闭连接数量', 1, 1, 'SUM', 19);
INSERT INTO `ipm_plot_option` VALUES (338, 12, 4, 59, '被动关闭连接数量', 1, 1, 'SUM', 20);
INSERT INTO `ipm_plot_option` VALUES (339, 12, 3, 60, '进流量', 1, 1, 'SUM', 24);
INSERT INTO `ipm_plot_option` VALUES (340, 12, 3, 61, '出流量', 1, 1, 'SUM', 25);
-- 硬件信息配置    START 
INSERT INTO `ipm_plot_option` VALUES (1000, 0, 7, 100, 'CPU_US' , 1, 0, 'AVG', 1);
INSERT INTO `ipm_plot_option` VALUES (1001, 0, 7, 101, 'CPU_SY' , 1, 0, 'AVG', 2);
INSERT INTO `ipm_plot_option` VALUES (1002, 0, 7, 102, 'CPU_NI' , 1, 0, 'AVG', 3);
INSERT INTO `ipm_plot_option` VALUES (1003, 0, 7, 103, 'CPU_ID' , 1, 0, 'AVG', 4);
INSERT INTO `ipm_plot_option` VALUES (1004, 0, 7, 104, 'CPU_WA' , 1, 0, 'AVG', 5);
INSERT INTO `ipm_plot_option` VALUES (1005, 0, 7, 105, 'CPU_HI' , 1, 0, 'AVG', 6);
INSERT INTO `ipm_plot_option` VALUES (1006, 0, 7, 106, 'CPU_SI' , 1, 0, 'AVG', 7);
INSERT INTO `ipm_plot_option` VALUES (1007, 0, 7, 107, 'CPU_ST' , 1, 0, 'AVG', 8);
INSERT INTO `ipm_plot_option` VALUES (1008, 0, 7, 108, '内存总量' , 1, 0, 'SUM', 9);
INSERT INTO `ipm_plot_option` VALUES (1009, 0, 7, 109, '已用内存' , 1, 0, 'SUM', 10);
INSERT INTO `ipm_plot_option` VALUES (1010, 0, 7, 110, '空闲内存' , 1, 0, 'SUM', 11);
INSERT INTO `ipm_plot_option` VALUES (1011, 0, 7, 111, '内核缓存' , 1, 0, 'SUM', 12);
INSERT INTO `ipm_plot_option` VALUES (1012, 0, 7, 112, 'SWAP总量' , 1, 0, 'SUM', 13);
INSERT INTO `ipm_plot_option` VALUES (1013, 0, 7, 113, 'SWAP使用' , 1, 0, 'SUM', 14);
INSERT INTO `ipm_plot_option` VALUES (1014, 0, 7, 114, 'SWAP空闲' , 1, 0, 'SUM', 15);
INSERT INTO `ipm_plot_option` VALUES (1015, 0, 7, 115, '内存缓冲' , 1, 0, 'SUM', 16);
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
INSERT INTO `ipm_plot_option` VALUES (1032, 0, 7, 132, '1分钟负载' , 1, 0, 'SUM', 33);
INSERT INTO `ipm_plot_option` VALUES (1033, 0, 7, 133, '5分钟负载' , 1, 0, 'SUM', 34);
INSERT INTO `ipm_plot_option` VALUES (1034, 0, 7, 134, '15分钟负载' , 1, 0, 'SUM', 35);
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
INSERT INTO `ipm_plot_type` VALUES (1, 'line', '线图');
INSERT INTO `ipm_plot_type` VALUES (2, 'bar', '横向柱图');
INSERT INTO `ipm_plot_type` VALUES (3, 'pie', '饼图');
INSERT INTO `ipm_plot_type` VALUES (4, 'column', '纵向柱图');
INSERT INTO `ipm_plot_type` VALUES (5, 'heatmap', '健康图');
INSERT INTO `ipm_plot_type` VALUES (6, 'multimarkline', '多坐标线图');
-- --------------------
-- 绘图类型数据 END 
-- --------------------


-- --------------------
-- 绘图分组类型数据 START 
-- --------------------
INSERT INTO `ipm_plot_group_type` VALUES (1, '告警类');
INSERT INTO `ipm_plot_group_type` VALUES (2, '时间类');
INSERT INTO `ipm_plot_group_type` VALUES (3, '流量类');
INSERT INTO `ipm_plot_group_type` VALUES (4, '会话类');
INSERT INTO `ipm_plot_group_type` VALUES (5, '数据包类');
INSERT INTO `ipm_plot_group_type` VALUES (6, '其他');
INSERT INTO `ipm_plot_group_type` VALUES (7, '硬件类');
-- --------------------
-- 绘图分组类型数据 END 
-- --------------------


-- --------------------
-- 小模块数据 START 
-- --------------------
INSERT INTO `ipm_small_module` VALUES (1, 12, '服务端设置');
INSERT INTO `ipm_small_module` VALUES (2, 0, '系统时间设置');
INSERT INTO `ipm_small_module` VALUES (3, 0, '数据存储设置');
INSERT INTO `ipm_small_module` VALUES (4, 0, '用户管理');
INSERT INTO `ipm_small_module` VALUES (5, 10, '观察点设置');
INSERT INTO `ipm_small_module` VALUES (6, 0, '用户配置设置');
INSERT INTO `ipm_small_module` VALUES (7, 11, '客户端设置');
INSERT INTO `ipm_small_module` VALUES (8, 0, '驾驶舱设置');
INSERT INTO `ipm_small_module` VALUES (9, 0, '原始流量下载');
INSERT INTO `ipm_small_module` VALUES (10, 0, '告警');
INSERT INTO `ipm_small_module` VALUES (11, 0, '系统设置');
INSERT INTO `ipm_small_module` VALUES (12, 1, '应用可用性设置');
INSERT INTO `ipm_small_module` VALUES (13, 8, 'URL服务');
INSERT INTO `ipm_small_module` VALUES (14, 9, '报文服务');
INSERT INTO `ipm_small_module` VALUES (15, 4, 'HTTP服务');
INSERT INTO `ipm_small_module` VALUES (16, 5, 'ORACLE服务');
INSERT INTO `ipm_small_module` VALUES (17, 6, 'MYSQL服务');
INSERT INTO `ipm_small_module` VALUES (18, 7, 'SQLSERVER服务');
INSERT INTO `ipm_small_module` VALUES (19, 0, '报表');
-- --------------------
-- 小模块数据 END 
-- --------------------


-- --------------------
-- 告警类型数据 START 
-- --------------------
INSERT INTO `ipm_alarm_type` VALUES (1,'BaseLineThreshold','基线');
INSERT INTO `ipm_alarm_type` VALUES (2,'CustomThreshold','自定义告警');
INSERT INTO `ipm_alarm_type` VALUES (3,'AppAvailability','应用可用性');
-- --------------------
-- 告警类型数据 END 
-- --------------------


-- --------------------
-- 告警级别数据 START 
-- --------------------
INSERT INTO `ipm_alarm_level` VALUES (1,1,'DEVIATE','智能告警');
INSERT INTO `ipm_alarm_level` VALUES (2,2,'NORMAL','普通告警');
INSERT INTO `ipm_alarm_level` VALUES (3,2,'IMPORTANT','重要告警');
INSERT INTO `ipm_alarm_level` VALUES (4,2,'URGENT','紧急告警');
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
INSERT INTO `ipm_alarm_kpi` VALUES (31, 10, 32);
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

INSERT INTO `ipm_alarm_kpi` VALUES (710, 11, 57);
INSERT INTO `ipm_alarm_kpi` VALUES (711, 11, 58);
INSERT INTO `ipm_alarm_kpi` VALUES (712, 11, 59);
INSERT INTO `ipm_alarm_kpi` VALUES (713, 11, 60);
INSERT INTO `ipm_alarm_kpi` VALUES (714, 11, 61);

INSERT INTO `ipm_alarm_kpi` VALUES (720, 12, 57);
INSERT INTO `ipm_alarm_kpi` VALUES (721, 12, 58);
INSERT INTO `ipm_alarm_kpi` VALUES (722, 12, 59);
INSERT INTO `ipm_alarm_kpi` VALUES (723, 12, 60);
INSERT INTO `ipm_alarm_kpi` VALUES (724, 12, 61);

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
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (31, 1, 31, 1);
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

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (710, 1, 710, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (711, 1, 711, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (712, 1, 712, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (713, 1, 713, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (714, 1, 714, 1);

INSERT INTO `ipm_alarm_kpialgorithm` VALUES (720, 1, 720, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (721, 1, 721, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (722, 1, 722, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (723, 1, 723, 1);
INSERT INTO `ipm_alarm_kpialgorithm` VALUES (724, 1, 724, 1);

-- --------------------
-- 告警KPI对应算法数据 END 
-- --------------------

-- --------------------
-- 告警算法  START 
-- --------------------
INSERT INTO `ipm_alarm_algorithm` VALUES (1,'custombasic','自定义告警基础算法',1,'5,5','自定义告警基础算法,告警规则为三分钟内超过告警阈值5次以上(不区分级别),按照最高级别告警');
INSERT INTO `ipm_alarm_algorithm` VALUES (2,'customurl','自定义URL报文算法',2,'1,1','自定义URL报文基础算法,告警规则为三分钟超过阈值3次以上(不区分级别),按照最高级别告警');
INSERT INTO `ipm_alarm_algorithm` VALUES (3,'custombaseline','自定义基线算法',3,'5,5','自定义基线算法,三分钟内超过基线高阈值或低阈值5次以上即为告警');
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
INSERT INTO `ipm_report_type` VALUES (1, '自定义');
INSERT INTO `ipm_report_type` VALUES (2, '日报');
INSERT INTO `ipm_report_type` VALUES (3, '周报');
INSERT INTO `ipm_report_type` VALUES (4, '月报');
-- --------------------
-- 报表类型数据  START
-- --------------------

-- --------------------
-- 地址库area信息  START
-- --------------------
INSERT INTO `ipm_area_dict` VALUES ('1', '0', 'continent', '0', '亚洲');
INSERT INTO `ipm_area_dict` VALUES ('2', '0', 'continent', '0', '非洲');
INSERT INTO `ipm_area_dict` VALUES ('3', '0', 'continent', '0', '欧洲');
INSERT INTO `ipm_area_dict` VALUES ('4', '0', 'continent', '0', '北美洲');
INSERT INTO `ipm_area_dict` VALUES ('5', '0', 'continent', '0', '南美洲');
INSERT INTO `ipm_area_dict` VALUES ('6', '0', 'continent', '0', '大洋洲');
INSERT INTO `ipm_area_dict` VALUES ('7', '0', 'continent', '0', '南极洲');
INSERT INTO `ipm_area_dict` VALUES ('10', '1', 'country', '1', '中国');
INSERT INTO `ipm_area_dict` VALUES ('301', '2', 'region_cn', '10', '北京市');
INSERT INTO `ipm_area_dict` VALUES ('302', '2', 'region_cn', '10', '天津市');
INSERT INTO `ipm_area_dict` VALUES ('303', '2', 'region_cn', '10', '上海市');
INSERT INTO `ipm_area_dict` VALUES ('304', '2', 'region_cn', '10', '重庆市');
INSERT INTO `ipm_area_dict` VALUES ('305', '2', 'region_cn', '10', '河北省');
INSERT INTO `ipm_area_dict` VALUES ('306', '2', 'region_cn', '10', '山西省');
INSERT INTO `ipm_area_dict` VALUES ('307', '2', 'region_cn', '10', '辽宁省');
INSERT INTO `ipm_area_dict` VALUES ('308', '2', 'region_cn', '10', '吉林省');
INSERT INTO `ipm_area_dict` VALUES ('309', '2', 'region_cn', '10', '黑龙江省');
INSERT INTO `ipm_area_dict` VALUES ('310', '2', 'region_cn', '10', '江苏省');
INSERT INTO `ipm_area_dict` VALUES ('311', '2', 'region_cn', '10', '浙江省');
INSERT INTO `ipm_area_dict` VALUES ('312', '2', 'region_cn', '10', '安徽省');
INSERT INTO `ipm_area_dict` VALUES ('313', '2', 'region_cn', '10', '福建省');
INSERT INTO `ipm_area_dict` VALUES ('314', '2', 'region_cn', '10', '江西省');
INSERT INTO `ipm_area_dict` VALUES ('315', '2', 'region_cn', '10', '山东省');
INSERT INTO `ipm_area_dict` VALUES ('316', '2', 'region_cn', '10', '河南省');
INSERT INTO `ipm_area_dict` VALUES ('317', '2', 'region_cn', '10', '湖北省');
INSERT INTO `ipm_area_dict` VALUES ('318', '2', 'region_cn', '10', '湖南省');
INSERT INTO `ipm_area_dict` VALUES ('319', '2', 'region_cn', '10', '广东省');
INSERT INTO `ipm_area_dict` VALUES ('320', '2', 'region_cn', '10', '海南省');
INSERT INTO `ipm_area_dict` VALUES ('321', '2', 'region_cn', '10', '四川省');
INSERT INTO `ipm_area_dict` VALUES ('322', '2', 'region_cn', '10', '贵州省');
INSERT INTO `ipm_area_dict` VALUES ('323', '2', 'region_cn', '10', '云南省');
INSERT INTO `ipm_area_dict` VALUES ('324', '2', 'region_cn', '10', '陕西省');
INSERT INTO `ipm_area_dict` VALUES ('325', '2', 'region_cn', '10', '甘肃省');
INSERT INTO `ipm_area_dict` VALUES ('326', '2', 'region_cn', '10', '青海省');
INSERT INTO `ipm_area_dict` VALUES ('327', '2', 'region_cn', '10', '内蒙古');
INSERT INTO `ipm_area_dict` VALUES ('328', '2', 'region_cn', '10', '广西');
INSERT INTO `ipm_area_dict` VALUES ('329', '2', 'region_cn', '10', '西藏');
INSERT INTO `ipm_area_dict` VALUES ('330', '2', 'region_cn', '10', '宁夏');
INSERT INTO `ipm_area_dict` VALUES ('331', '2', 'region_cn', '10', '新疆');
INSERT INTO `ipm_area_dict` VALUES ('332', '2', 'region_cn', '10', '香港');
INSERT INTO `ipm_area_dict` VALUES ('333', '2', 'region_cn', '10', '澳门');
INSERT INTO `ipm_area_dict` VALUES ('334', '2', 'region_cn', '10', '台湾');
INSERT INTO `ipm_area_dict` VALUES ('401', '3', 'city_cn', '301', '东城');
INSERT INTO `ipm_area_dict` VALUES ('402', '3', 'city_cn', '301', '丰台');
INSERT INTO `ipm_area_dict` VALUES ('403', '3', 'city_cn', '301', '大兴');
INSERT INTO `ipm_area_dict` VALUES ('404', '3', 'city_cn', '301', '密云');
INSERT INTO `ipm_area_dict` VALUES ('405', '3', 'city_cn', '301', '平谷');
INSERT INTO `ipm_area_dict` VALUES ('406', '3', 'city_cn', '301', '延庆');
INSERT INTO `ipm_area_dict` VALUES ('407', '3', 'city_cn', '301', '怀柔');
INSERT INTO `ipm_area_dict` VALUES ('408', '3', 'city_cn', '301', '房山');
INSERT INTO `ipm_area_dict` VALUES ('409', '3', 'city_cn', '301', '昌平');
INSERT INTO `ipm_area_dict` VALUES ('410', '3', 'city_cn', '301', '朝阳');
INSERT INTO `ipm_area_dict` VALUES ('411', '3', 'city_cn', '301', '海淀');
INSERT INTO `ipm_area_dict` VALUES ('412', '3', 'city_cn', '301', '石景山');
INSERT INTO `ipm_area_dict` VALUES ('413', '3', 'city_cn', '301', '西城');
INSERT INTO `ipm_area_dict` VALUES ('414', '3', 'city_cn', '301', '通州');
INSERT INTO `ipm_area_dict` VALUES ('415', '3', 'city_cn', '301', '门头沟');
INSERT INTO `ipm_area_dict` VALUES ('416', '3', 'city_cn', '301', '顺义');
INSERT INTO `ipm_area_dict` VALUES ('421', '3', 'city_cn', '302', '东丽');
INSERT INTO `ipm_area_dict` VALUES ('422', '3', 'city_cn', '302', '北辰');
INSERT INTO `ipm_area_dict` VALUES ('423', '3', 'city_cn', '302', '南开');
INSERT INTO `ipm_area_dict` VALUES ('424', '3', 'city_cn', '302', '和平');
INSERT INTO `ipm_area_dict` VALUES ('425', '3', 'city_cn', '302', '宁河');
INSERT INTO `ipm_area_dict` VALUES ('426', '3', 'city_cn', '302', '宝坻');
INSERT INTO `ipm_area_dict` VALUES ('427', '3', 'city_cn', '302', '武清');
INSERT INTO `ipm_area_dict` VALUES ('428', '3', 'city_cn', '302', '河东');
INSERT INTO `ipm_area_dict` VALUES ('429', '3', 'city_cn', '302', '河北');
INSERT INTO `ipm_area_dict` VALUES ('430', '3', 'city_cn', '302', '河西');
INSERT INTO `ipm_area_dict` VALUES ('431', '3', 'city_cn', '302', '津南');
INSERT INTO `ipm_area_dict` VALUES ('432', '3', 'city_cn', '302', '滨海');
INSERT INTO `ipm_area_dict` VALUES ('433', '3', 'city_cn', '302', '红桥');
INSERT INTO `ipm_area_dict` VALUES ('434', '3', 'city_cn', '302', '蓟州');
INSERT INTO `ipm_area_dict` VALUES ('435', '3', 'city_cn', '302', '西青');
INSERT INTO `ipm_area_dict` VALUES ('436', '3', 'city_cn', '302', '静海');
INSERT INTO `ipm_area_dict` VALUES ('441', '3', 'city_cn', '303', '嘉定');
INSERT INTO `ipm_area_dict` VALUES ('442', '3', 'city_cn', '303', '奉贤');
INSERT INTO `ipm_area_dict` VALUES ('443', '3', 'city_cn', '303', '宝山');
INSERT INTO `ipm_area_dict` VALUES ('444', '3', 'city_cn', '303', '崇明');
INSERT INTO `ipm_area_dict` VALUES ('445', '3', 'city_cn', '303', '徐汇');
INSERT INTO `ipm_area_dict` VALUES ('446', '3', 'city_cn', '303', '普陀');
INSERT INTO `ipm_area_dict` VALUES ('447', '3', 'city_cn', '303', '杨浦');
INSERT INTO `ipm_area_dict` VALUES ('448', '3', 'city_cn', '303', '松江');
INSERT INTO `ipm_area_dict` VALUES ('449', '3', 'city_cn', '303', '浦东');
INSERT INTO `ipm_area_dict` VALUES ('450', '3', 'city_cn', '303', '虹口');
INSERT INTO `ipm_area_dict` VALUES ('451', '3', 'city_cn', '303', '金山');
INSERT INTO `ipm_area_dict` VALUES ('452', '3', 'city_cn', '303', '长宁');
INSERT INTO `ipm_area_dict` VALUES ('453', '3', 'city_cn', '303', '闵行');
INSERT INTO `ipm_area_dict` VALUES ('454', '3', 'city_cn', '303', '青浦');
INSERT INTO `ipm_area_dict` VALUES ('455', '3', 'city_cn', '303', '静安');
INSERT INTO `ipm_area_dict` VALUES ('456', '3', 'city_cn', '303', '黄埔');
INSERT INTO `ipm_area_dict` VALUES ('461', '3', 'city_cn', '304', '万州');
INSERT INTO `ipm_area_dict` VALUES ('462', '3', 'city_cn', '304', '丰都');
INSERT INTO `ipm_area_dict` VALUES ('463', '3', 'city_cn', '304', '九龙坡');
INSERT INTO `ipm_area_dict` VALUES ('464', '3', 'city_cn', '304', '云阳');
INSERT INTO `ipm_area_dict` VALUES ('465', '3', 'city_cn', '304', '北碚');
INSERT INTO `ipm_area_dict` VALUES ('466', '3', 'city_cn', '304', '南岸');
INSERT INTO `ipm_area_dict` VALUES ('467', '3', 'city_cn', '304', '南川');
INSERT INTO `ipm_area_dict` VALUES ('468', '3', 'city_cn', '304', '合川');
INSERT INTO `ipm_area_dict` VALUES ('469', '3', 'city_cn', '304', '垫江');
INSERT INTO `ipm_area_dict` VALUES ('470', '3', 'city_cn', '304', '城口');
INSERT INTO `ipm_area_dict` VALUES ('471', '3', 'city_cn', '304', '大渡口');
INSERT INTO `ipm_area_dict` VALUES ('472', '3', 'city_cn', '304', '大足');
INSERT INTO `ipm_area_dict` VALUES ('473', '3', 'city_cn', '304', '奉节');
INSERT INTO `ipm_area_dict` VALUES ('474', '3', 'city_cn', '304', '巫山');
INSERT INTO `ipm_area_dict` VALUES ('475', '3', 'city_cn', '304', '巫溪');
INSERT INTO `ipm_area_dict` VALUES ('476', '3', 'city_cn', '304', '巴南');
INSERT INTO `ipm_area_dict` VALUES ('477', '3', 'city_cn', '304', '开州');
INSERT INTO `ipm_area_dict` VALUES ('478', '3', 'city_cn', '304', '彭水');
INSERT INTO `ipm_area_dict` VALUES ('479', '3', 'city_cn', '304', '忠县');
INSERT INTO `ipm_area_dict` VALUES ('480', '3', 'city_cn', '304', '梁平');
INSERT INTO `ipm_area_dict` VALUES ('481', '3', 'city_cn', '304', '武陵');
INSERT INTO `ipm_area_dict` VALUES ('482', '3', 'city_cn', '304', '永州');
INSERT INTO `ipm_area_dict` VALUES ('483', '3', 'city_cn', '304', '江北');
INSERT INTO `ipm_area_dict` VALUES ('484', '3', 'city_cn', '304', '江津');
INSERT INTO `ipm_area_dict` VALUES ('485', '3', 'city_cn', '304', '沙坪坝');
INSERT INTO `ipm_area_dict` VALUES ('486', '3', 'city_cn', '304', '涪陵');
INSERT INTO `ipm_area_dict` VALUES ('487', '3', 'city_cn', '304', '渝中');
INSERT INTO `ipm_area_dict` VALUES ('488', '3', 'city_cn', '304', '渝北');
INSERT INTO `ipm_area_dict` VALUES ('489', '3', 'city_cn', '304', '潼南');
INSERT INTO `ipm_area_dict` VALUES ('490', '3', 'city_cn', '304', '璧山');
INSERT INTO `ipm_area_dict` VALUES ('491', '3', 'city_cn', '304', '石柱');
INSERT INTO `ipm_area_dict` VALUES ('492', '3', 'city_cn', '304', '秀山');
INSERT INTO `ipm_area_dict` VALUES ('493', '3', 'city_cn', '304', '綦江');
INSERT INTO `ipm_area_dict` VALUES ('494', '3', 'city_cn', '304', '荣昌');
INSERT INTO `ipm_area_dict` VALUES ('495', '3', 'city_cn', '304', '酉阳');
INSERT INTO `ipm_area_dict` VALUES ('496', '3', 'city_cn', '304', '铜梁');
INSERT INTO `ipm_area_dict` VALUES ('497', '3', 'city_cn', '304', '长寿');
INSERT INTO `ipm_area_dict` VALUES ('498', '3', 'city_cn', '304', '黔江');
INSERT INTO `ipm_area_dict` VALUES ('510', '3', 'city_cn', '305', '保定');
INSERT INTO `ipm_area_dict` VALUES ('511', '3', 'city_cn', '305', '唐山');
INSERT INTO `ipm_area_dict` VALUES ('512', '3', 'city_cn', '305', '廊坊');
INSERT INTO `ipm_area_dict` VALUES ('513', '3', 'city_cn', '305', '张家口');
INSERT INTO `ipm_area_dict` VALUES ('514', '3', 'city_cn', '305', '承德');
INSERT INTO `ipm_area_dict` VALUES ('515', '3', 'city_cn', '305', '沧州');
INSERT INTO `ipm_area_dict` VALUES ('516', '3', 'city_cn', '305', '石家庄');
INSERT INTO `ipm_area_dict` VALUES ('517', '3', 'city_cn', '305', '秦皇岛');
INSERT INTO `ipm_area_dict` VALUES ('518', '3', 'city_cn', '305', '衡水');
INSERT INTO `ipm_area_dict` VALUES ('519', '3', 'city_cn', '305', '邢台');
INSERT INTO `ipm_area_dict` VALUES ('520', '3', 'city_cn', '305', '邯郸');
INSERT INTO `ipm_area_dict` VALUES ('521', '3', 'city_cn', '306', '临汾');
INSERT INTO `ipm_area_dict` VALUES ('522', '3', 'city_cn', '306', '吕梁');
INSERT INTO `ipm_area_dict` VALUES ('523', '3', 'city_cn', '306', '大同');
INSERT INTO `ipm_area_dict` VALUES ('524', '3', 'city_cn', '306', '太原');
INSERT INTO `ipm_area_dict` VALUES ('525', '3', 'city_cn', '306', '忻州');
INSERT INTO `ipm_area_dict` VALUES ('526', '3', 'city_cn', '306', '晋中');
INSERT INTO `ipm_area_dict` VALUES ('527', '3', 'city_cn', '306', '晋城');
INSERT INTO `ipm_area_dict` VALUES ('528', '3', 'city_cn', '306', '朔州');
INSERT INTO `ipm_area_dict` VALUES ('529', '3', 'city_cn', '306', '运城');
INSERT INTO `ipm_area_dict` VALUES ('530', '3', 'city_cn', '306', '长治');
INSERT INTO `ipm_area_dict` VALUES ('531', '3', 'city_cn', '306', '阳泉');
INSERT INTO `ipm_area_dict` VALUES ('541', '3', 'city_cn', '307', '丹东');
INSERT INTO `ipm_area_dict` VALUES ('542', '3', 'city_cn', '307', '大连');
INSERT INTO `ipm_area_dict` VALUES ('543', '3', 'city_cn', '307', '抚顺');
INSERT INTO `ipm_area_dict` VALUES ('544', '3', 'city_cn', '307', '朝阳');
INSERT INTO `ipm_area_dict` VALUES ('545', '3', 'city_cn', '307', '本溪');
INSERT INTO `ipm_area_dict` VALUES ('546', '3', 'city_cn', '307', '沈阳');
INSERT INTO `ipm_area_dict` VALUES ('547', '3', 'city_cn', '307', '盘锦');
INSERT INTO `ipm_area_dict` VALUES ('548', '3', 'city_cn', '307', '营口');
INSERT INTO `ipm_area_dict` VALUES ('549', '3', 'city_cn', '307', '葫芦岛');
INSERT INTO `ipm_area_dict` VALUES ('550', '3', 'city_cn', '307', '辽阳');
INSERT INTO `ipm_area_dict` VALUES ('551', '3', 'city_cn', '307', '铁岭');
INSERT INTO `ipm_area_dict` VALUES ('552', '3', 'city_cn', '307', '锦州');
INSERT INTO `ipm_area_dict` VALUES ('553', '3', 'city_cn', '307', '阜新');
INSERT INTO `ipm_area_dict` VALUES ('554', '3', 'city_cn', '307', '鞍山');
INSERT INTO `ipm_area_dict` VALUES ('561', '3', 'city_cn', '308', '四平');
INSERT INTO `ipm_area_dict` VALUES ('562', '3', 'city_cn', '308', '延边');
INSERT INTO `ipm_area_dict` VALUES ('563', '3', 'city_cn', '308', '松原');
INSERT INTO `ipm_area_dict` VALUES ('564', '3', 'city_cn', '308', '白城');
INSERT INTO `ipm_area_dict` VALUES ('565', '3', 'city_cn', '308', '白山');
INSERT INTO `ipm_area_dict` VALUES ('566', '3', 'city_cn', '308', '辽源');
INSERT INTO `ipm_area_dict` VALUES ('567', '3', 'city_cn', '308', '通化');
INSERT INTO `ipm_area_dict` VALUES ('568', '3', 'city_cn', '308', '长春');
INSERT INTO `ipm_area_dict` VALUES ('571', '3', 'city_cn', '309', '七台河');
INSERT INTO `ipm_area_dict` VALUES ('572', '3', 'city_cn', '309', '伊春');
INSERT INTO `ipm_area_dict` VALUES ('573', '3', 'city_cn', '309', '佳木斯');
INSERT INTO `ipm_area_dict` VALUES ('574', '3', 'city_cn', '309', '双鸭山');
INSERT INTO `ipm_area_dict` VALUES ('575', '3', 'city_cn', '309', '哈尔滨');
INSERT INTO `ipm_area_dict` VALUES ('576', '3', 'city_cn', '309', '大兴安岭');
INSERT INTO `ipm_area_dict` VALUES ('577', '3', 'city_cn', '309', '大庆');
INSERT INTO `ipm_area_dict` VALUES ('578', '3', 'city_cn', '309', '牡丹江');
INSERT INTO `ipm_area_dict` VALUES ('579', '3', 'city_cn', '309', '绥化');
INSERT INTO `ipm_area_dict` VALUES ('580', '3', 'city_cn', '309', '鸡西');
INSERT INTO `ipm_area_dict` VALUES ('581', '3', 'city_cn', '309', '鹤岗');
INSERT INTO `ipm_area_dict` VALUES ('582', '3', 'city_cn', '309', '黑河');
INSERT INTO `ipm_area_dict` VALUES ('583', '3', 'city_cn', '309', '齐齐哈尔');
INSERT INTO `ipm_area_dict` VALUES ('591', '3', 'city_cn', '310', '南通');
INSERT INTO `ipm_area_dict` VALUES ('592', '3', 'city_cn', '310', '宿迁');
INSERT INTO `ipm_area_dict` VALUES ('593', '3', 'city_cn', '310', '常州');
INSERT INTO `ipm_area_dict` VALUES ('594', '3', 'city_cn', '310', '徐州');
INSERT INTO `ipm_area_dict` VALUES ('595', '3', 'city_cn', '310', '扬州');
INSERT INTO `ipm_area_dict` VALUES ('596', '3', 'city_cn', '310', '无锡');
INSERT INTO `ipm_area_dict` VALUES ('597', '3', 'city_cn', '310', '泰州');
INSERT INTO `ipm_area_dict` VALUES ('598', '3', 'city_cn', '310', '淮安');
INSERT INTO `ipm_area_dict` VALUES ('599', '3', 'city_cn', '310', '盐城');
INSERT INTO `ipm_area_dict` VALUES ('600', '3', 'city_cn', '310', '苏州');
INSERT INTO `ipm_area_dict` VALUES ('601', '3', 'city_cn', '310', '连云港');
INSERT INTO `ipm_area_dict` VALUES ('602', '3', 'city_cn', '310', '镇江');
INSERT INTO `ipm_area_dict` VALUES ('611', '3', 'city_cn', '311', '丽水');
INSERT INTO `ipm_area_dict` VALUES ('612', '3', 'city_cn', '311', '台州');
INSERT INTO `ipm_area_dict` VALUES ('613', '3', 'city_cn', '311', '嘉兴');
INSERT INTO `ipm_area_dict` VALUES ('614', '3', 'city_cn', '311', '宁波');
INSERT INTO `ipm_area_dict` VALUES ('615', '3', 'city_cn', '311', '杭州');
INSERT INTO `ipm_area_dict` VALUES ('616', '3', 'city_cn', '311', '温州');
INSERT INTO `ipm_area_dict` VALUES ('617', '3', 'city_cn', '311', '湖州');
INSERT INTO `ipm_area_dict` VALUES ('618', '3', 'city_cn', '311', '绍兴');
INSERT INTO `ipm_area_dict` VALUES ('619', '3', 'city_cn', '311', '舟山');
INSERT INTO `ipm_area_dict` VALUES ('620', '3', 'city_cn', '311', '衡州');
INSERT INTO `ipm_area_dict` VALUES ('621', '3', 'city_cn', '311', '金华');
INSERT INTO `ipm_area_dict` VALUES ('631', '3', 'city_cn', '312', '亳州');
INSERT INTO `ipm_area_dict` VALUES ('632', '3', 'city_cn', '312', '六安');
INSERT INTO `ipm_area_dict` VALUES ('633', '3', 'city_cn', '312', '合肥');
INSERT INTO `ipm_area_dict` VALUES ('634', '3', 'city_cn', '312', '安庆');
INSERT INTO `ipm_area_dict` VALUES ('635', '3', 'city_cn', '312', '宜城');
INSERT INTO `ipm_area_dict` VALUES ('636', '3', 'city_cn', '312', '宿州');
INSERT INTO `ipm_area_dict` VALUES ('637', '3', 'city_cn', '312', '池州');
INSERT INTO `ipm_area_dict` VALUES ('638', '3', 'city_cn', '312', '淮北');
INSERT INTO `ipm_area_dict` VALUES ('639', '3', 'city_cn', '312', '淮南');
INSERT INTO `ipm_area_dict` VALUES ('640', '3', 'city_cn', '312', '滁州');
INSERT INTO `ipm_area_dict` VALUES ('641', '3', 'city_cn', '312', '芜湖');
INSERT INTO `ipm_area_dict` VALUES ('642', '3', 'city_cn', '312', '蚌埠');
INSERT INTO `ipm_area_dict` VALUES ('643', '3', 'city_cn', '312', '铜陵');
INSERT INTO `ipm_area_dict` VALUES ('644', '3', 'city_cn', '312', '阜阳');
INSERT INTO `ipm_area_dict` VALUES ('645', '3', 'city_cn', '312', '马鞍山');
INSERT INTO `ipm_area_dict` VALUES ('646', '3', 'city_cn', '312', '黄山');
INSERT INTO `ipm_area_dict` VALUES ('651', '3', 'city_cn', '313', '三明');
INSERT INTO `ipm_area_dict` VALUES ('652', '3', 'city_cn', '313', '南平');
INSERT INTO `ipm_area_dict` VALUES ('653', '3', 'city_cn', '313', '厦门');
INSERT INTO `ipm_area_dict` VALUES ('654', '3', 'city_cn', '313', '宁德');
INSERT INTO `ipm_area_dict` VALUES ('655', '3', 'city_cn', '313', '泉州');
INSERT INTO `ipm_area_dict` VALUES ('656', '3', 'city_cn', '313', '漳州');
INSERT INTO `ipm_area_dict` VALUES ('657', '3', 'city_cn', '313', '福州');
INSERT INTO `ipm_area_dict` VALUES ('658', '3', 'city_cn', '313', '莆田');
INSERT INTO `ipm_area_dict` VALUES ('659', '3', 'city_cn', '313', '龙岩');
INSERT INTO `ipm_area_dict` VALUES ('661', '3', 'city_cn', '314', '上饶');
INSERT INTO `ipm_area_dict` VALUES ('662', '3', 'city_cn', '314', '九江');
INSERT INTO `ipm_area_dict` VALUES ('663', '3', 'city_cn', '314', '南昌');
INSERT INTO `ipm_area_dict` VALUES ('664', '3', 'city_cn', '314', '吉安');
INSERT INTO `ipm_area_dict` VALUES ('665', '3', 'city_cn', '314', '宜春');
INSERT INTO `ipm_area_dict` VALUES ('666', '3', 'city_cn', '314', '抚州');
INSERT INTO `ipm_area_dict` VALUES ('667', '3', 'city_cn', '314', '新余');
INSERT INTO `ipm_area_dict` VALUES ('668', '3', 'city_cn', '314', '景德镇');
INSERT INTO `ipm_area_dict` VALUES ('669', '3', 'city_cn', '314', '萍乡');
INSERT INTO `ipm_area_dict` VALUES ('670', '3', 'city_cn', '314', '赣州');
INSERT INTO `ipm_area_dict` VALUES ('671', '3', 'city_cn', '314', '鹰潭');
INSERT INTO `ipm_area_dict` VALUES ('681', '3', 'city_cn', '315', '东营');
INSERT INTO `ipm_area_dict` VALUES ('682', '3', 'city_cn', '315', '临沂');
INSERT INTO `ipm_area_dict` VALUES ('683', '3', 'city_cn', '315', '威海');
INSERT INTO `ipm_area_dict` VALUES ('684', '3', 'city_cn', '315', '德州');
INSERT INTO `ipm_area_dict` VALUES ('685', '3', 'city_cn', '315', '日照');
INSERT INTO `ipm_area_dict` VALUES ('686', '3', 'city_cn', '315', '枣庄');
INSERT INTO `ipm_area_dict` VALUES ('687', '3', 'city_cn', '315', '泰安');
INSERT INTO `ipm_area_dict` VALUES ('688', '3', 'city_cn', '315', '济南');
INSERT INTO `ipm_area_dict` VALUES ('689', '3', 'city_cn', '315', '济宁');
INSERT INTO `ipm_area_dict` VALUES ('690', '3', 'city_cn', '315', '淄博');
INSERT INTO `ipm_area_dict` VALUES ('691', '3', 'city_cn', '315', '滨州');
INSERT INTO `ipm_area_dict` VALUES ('692', '3', 'city_cn', '315', '潍坊');
INSERT INTO `ipm_area_dict` VALUES ('693', '3', 'city_cn', '315', '烟台');
INSERT INTO `ipm_area_dict` VALUES ('694', '3', 'city_cn', '315', '聊城');
INSERT INTO `ipm_area_dict` VALUES ('695', '3', 'city_cn', '315', '莱芜');
INSERT INTO `ipm_area_dict` VALUES ('696', '3', 'city_cn', '315', '菏泽');
INSERT INTO `ipm_area_dict` VALUES ('697', '3', 'city_cn', '315', '青岛');
INSERT INTO `ipm_area_dict` VALUES ('701', '3', 'city_cn', '316', '三门峡');
INSERT INTO `ipm_area_dict` VALUES ('702', '3', 'city_cn', '316', '信阳');
INSERT INTO `ipm_area_dict` VALUES ('703', '3', 'city_cn', '316', '南阳');
INSERT INTO `ipm_area_dict` VALUES ('704', '3', 'city_cn', '316', '周口');
INSERT INTO `ipm_area_dict` VALUES ('705', '3', 'city_cn', '316', '商丘');
INSERT INTO `ipm_area_dict` VALUES ('706', '3', 'city_cn', '316', '安阳');
INSERT INTO `ipm_area_dict` VALUES ('707', '3', 'city_cn', '316', '平顶山');
INSERT INTO `ipm_area_dict` VALUES ('708', '3', 'city_cn', '316', '开封');
INSERT INTO `ipm_area_dict` VALUES ('709', '3', 'city_cn', '316', '新乡');
INSERT INTO `ipm_area_dict` VALUES ('710', '3', 'city_cn', '316', '洛阳');
INSERT INTO `ipm_area_dict` VALUES ('711', '3', 'city_cn', '316', '济源');
INSERT INTO `ipm_area_dict` VALUES ('712', '3', 'city_cn', '316', '漯河');
INSERT INTO `ipm_area_dict` VALUES ('713', '3', 'city_cn', '316', '濮阳');
INSERT INTO `ipm_area_dict` VALUES ('714', '3', 'city_cn', '316', '焦作');
INSERT INTO `ipm_area_dict` VALUES ('715', '3', 'city_cn', '316', '许昌');
INSERT INTO `ipm_area_dict` VALUES ('716', '3', 'city_cn', '316', '郑州');
INSERT INTO `ipm_area_dict` VALUES ('717', '3', 'city_cn', '316', '驻马店');
INSERT INTO `ipm_area_dict` VALUES ('718', '3', 'city_cn', '316', '鹤壁');
INSERT INTO `ipm_area_dict` VALUES ('721', '3', 'city_cn', '317', '仙桃');
INSERT INTO `ipm_area_dict` VALUES ('722', '3', 'city_cn', '317', '十堰');
INSERT INTO `ipm_area_dict` VALUES ('723', '3', 'city_cn', '317', '咸宁');
INSERT INTO `ipm_area_dict` VALUES ('724', '3', 'city_cn', '317', '天门');
INSERT INTO `ipm_area_dict` VALUES ('725', '3', 'city_cn', '317', '孝感');
INSERT INTO `ipm_area_dict` VALUES ('726', '3', 'city_cn', '317', '宜昌');
INSERT INTO `ipm_area_dict` VALUES ('727', '3', 'city_cn', '317', '恩施');
INSERT INTO `ipm_area_dict` VALUES ('728', '3', 'city_cn', '317', '武汉');
INSERT INTO `ipm_area_dict` VALUES ('729', '3', 'city_cn', '317', '潜江');
INSERT INTO `ipm_area_dict` VALUES ('730', '3', 'city_cn', '317', '神农架');
INSERT INTO `ipm_area_dict` VALUES ('731', '3', 'city_cn', '317', '荆州');
INSERT INTO `ipm_area_dict` VALUES ('732', '3', 'city_cn', '317', '荆门');
INSERT INTO `ipm_area_dict` VALUES ('733', '3', 'city_cn', '317', '襄阳');
INSERT INTO `ipm_area_dict` VALUES ('734', '3', 'city_cn', '317', '鄂州');
INSERT INTO `ipm_area_dict` VALUES ('735', '3', 'city_cn', '317', '随州');
INSERT INTO `ipm_area_dict` VALUES ('736', '3', 'city_cn', '317', '黄冈');
INSERT INTO `ipm_area_dict` VALUES ('737', '3', 'city_cn', '317', '黄石');
INSERT INTO `ipm_area_dict` VALUES ('741', '3', 'city_cn', '318', '娄底');
INSERT INTO `ipm_area_dict` VALUES ('742', '3', 'city_cn', '318', '岳阳');
INSERT INTO `ipm_area_dict` VALUES ('743', '3', 'city_cn', '318', '常德');
INSERT INTO `ipm_area_dict` VALUES ('744', '3', 'city_cn', '318', '张家界');
INSERT INTO `ipm_area_dict` VALUES ('745', '3', 'city_cn', '318', '怀化');
INSERT INTO `ipm_area_dict` VALUES ('746', '3', 'city_cn', '318', '株洲');
INSERT INTO `ipm_area_dict` VALUES ('747', '3', 'city_cn', '318', '永州');
INSERT INTO `ipm_area_dict` VALUES ('748', '3', 'city_cn', '318', '湘潭');
INSERT INTO `ipm_area_dict` VALUES ('749', '3', 'city_cn', '318', '湘西');
INSERT INTO `ipm_area_dict` VALUES ('750', '3', 'city_cn', '318', '益阳');
INSERT INTO `ipm_area_dict` VALUES ('751', '3', 'city_cn', '318', '衡阳');
INSERT INTO `ipm_area_dict` VALUES ('752', '3', 'city_cn', '318', '邵阳');
INSERT INTO `ipm_area_dict` VALUES ('753', '3', 'city_cn', '318', '郴州');
INSERT INTO `ipm_area_dict` VALUES ('754', '3', 'city_cn', '318', '长沙');
INSERT INTO `ipm_area_dict` VALUES ('761', '3', 'city_cn', '319', '东莞');
INSERT INTO `ipm_area_dict` VALUES ('762', '3', 'city_cn', '319', '中山');
INSERT INTO `ipm_area_dict` VALUES ('763', '3', 'city_cn', '319', '云浮');
INSERT INTO `ipm_area_dict` VALUES ('764', '3', 'city_cn', '319', '佛山');
INSERT INTO `ipm_area_dict` VALUES ('765', '3', 'city_cn', '319', '广州');
INSERT INTO `ipm_area_dict` VALUES ('766', '3', 'city_cn', '319', '惠州');
INSERT INTO `ipm_area_dict` VALUES ('767', '3', 'city_cn', '319', '揭阳');
INSERT INTO `ipm_area_dict` VALUES ('768', '3', 'city_cn', '319', '梅州');
INSERT INTO `ipm_area_dict` VALUES ('769', '3', 'city_cn', '319', '汕头');
INSERT INTO `ipm_area_dict` VALUES ('770', '3', 'city_cn', '319', '汕尾');
INSERT INTO `ipm_area_dict` VALUES ('771', '3', 'city_cn', '319', '江门');
INSERT INTO `ipm_area_dict` VALUES ('772', '3', 'city_cn', '319', '河源');
INSERT INTO `ipm_area_dict` VALUES ('773', '3', 'city_cn', '319', '深圳');
INSERT INTO `ipm_area_dict` VALUES ('774', '3', 'city_cn', '319', '清远');
INSERT INTO `ipm_area_dict` VALUES ('775', '3', 'city_cn', '319', '湛江');
INSERT INTO `ipm_area_dict` VALUES ('776', '3', 'city_cn', '319', '潮州');
INSERT INTO `ipm_area_dict` VALUES ('777', '3', 'city_cn', '319', '珠海');
INSERT INTO `ipm_area_dict` VALUES ('778', '3', 'city_cn', '319', '肇庆');
INSERT INTO `ipm_area_dict` VALUES ('779', '3', 'city_cn', '319', '茂名');
INSERT INTO `ipm_area_dict` VALUES ('780', '3', 'city_cn', '319', '阳江');
INSERT INTO `ipm_area_dict` VALUES ('781', '3', 'city_cn', '319', '韶关');
INSERT INTO `ipm_area_dict` VALUES ('791', '3', 'city_cn', '320', '万宁');
INSERT INTO `ipm_area_dict` VALUES ('792', '3', 'city_cn', '320', '三亚');
INSERT INTO `ipm_area_dict` VALUES ('793', '3', 'city_cn', '320', '东方');
INSERT INTO `ipm_area_dict` VALUES ('794', '3', 'city_cn', '320', '临高');
INSERT INTO `ipm_area_dict` VALUES ('795', '3', 'city_cn', '320', '乐东');
INSERT INTO `ipm_area_dict` VALUES ('796', '3', 'city_cn', '320', '五指山');
INSERT INTO `ipm_area_dict` VALUES ('797', '3', 'city_cn', '320', '保亭');
INSERT INTO `ipm_area_dict` VALUES ('798', '3', 'city_cn', '320', '儋州');
INSERT INTO `ipm_area_dict` VALUES ('799', '3', 'city_cn', '320', '定安');
INSERT INTO `ipm_area_dict` VALUES ('800', '3', 'city_cn', '320', '屯昌');
INSERT INTO `ipm_area_dict` VALUES ('801', '3', 'city_cn', '320', '文昌');
INSERT INTO `ipm_area_dict` VALUES ('802', '3', 'city_cn', '320', '昌江');
INSERT INTO `ipm_area_dict` VALUES ('803', '3', 'city_cn', '320', '海口');
INSERT INTO `ipm_area_dict` VALUES ('804', '3', 'city_cn', '320', '澄迈');
INSERT INTO `ipm_area_dict` VALUES ('805', '3', 'city_cn', '320', '琼中');
INSERT INTO `ipm_area_dict` VALUES ('806', '3', 'city_cn', '320', '琼海');
INSERT INTO `ipm_area_dict` VALUES ('807', '3', 'city_cn', '320', '白沙');
INSERT INTO `ipm_area_dict` VALUES ('808', '3', 'city_cn', '320', '陵水');
INSERT INTO `ipm_area_dict` VALUES ('811', '3', 'city_cn', '321', '乐山');
INSERT INTO `ipm_area_dict` VALUES ('812', '3', 'city_cn', '321', '内江');
INSERT INTO `ipm_area_dict` VALUES ('813', '3', 'city_cn', '321', '凉山');
INSERT INTO `ipm_area_dict` VALUES ('814', '3', 'city_cn', '321', '南充');
INSERT INTO `ipm_area_dict` VALUES ('815', '3', 'city_cn', '321', '宜宾');
INSERT INTO `ipm_area_dict` VALUES ('816', '3', 'city_cn', '321', '巴中');
INSERT INTO `ipm_area_dict` VALUES ('817', '3', 'city_cn', '321', '广元');
INSERT INTO `ipm_area_dict` VALUES ('818', '3', 'city_cn', '321', '广安');
INSERT INTO `ipm_area_dict` VALUES ('819', '3', 'city_cn', '321', '德阳');
INSERT INTO `ipm_area_dict` VALUES ('820', '3', 'city_cn', '321', '成都');
INSERT INTO `ipm_area_dict` VALUES ('821', '3', 'city_cn', '321', '攀枝花');
INSERT INTO `ipm_area_dict` VALUES ('822', '3', 'city_cn', '321', '泸州');
INSERT INTO `ipm_area_dict` VALUES ('823', '3', 'city_cn', '321', '甘孜');
INSERT INTO `ipm_area_dict` VALUES ('824', '3', 'city_cn', '321', '眉山');
INSERT INTO `ipm_area_dict` VALUES ('825', '3', 'city_cn', '321', '绵阳');
INSERT INTO `ipm_area_dict` VALUES ('826', '3', 'city_cn', '321', '自贡');
INSERT INTO `ipm_area_dict` VALUES ('827', '3', 'city_cn', '321', '资阳');
INSERT INTO `ipm_area_dict` VALUES ('828', '3', 'city_cn', '321', '达州');
INSERT INTO `ipm_area_dict` VALUES ('829', '3', 'city_cn', '321', '遂宁');
INSERT INTO `ipm_area_dict` VALUES ('830', '3', 'city_cn', '321', '阿坝');
INSERT INTO `ipm_area_dict` VALUES ('831', '3', 'city_cn', '321', '雅安');
INSERT INTO `ipm_area_dict` VALUES ('841', '3', 'city_cn', '322', '六盘水');
INSERT INTO `ipm_area_dict` VALUES ('842', '3', 'city_cn', '322', '安顺');
INSERT INTO `ipm_area_dict` VALUES ('843', '3', 'city_cn', '322', '毕节');
INSERT INTO `ipm_area_dict` VALUES ('844', '3', 'city_cn', '322', '贵阳');
INSERT INTO `ipm_area_dict` VALUES ('845', '3', 'city_cn', '322', '遵义');
INSERT INTO `ipm_area_dict` VALUES ('846', '3', 'city_cn', '322', '铜仁');
INSERT INTO `ipm_area_dict` VALUES ('847', '3', 'city_cn', '322', '黔东南');
INSERT INTO `ipm_area_dict` VALUES ('848', '3', 'city_cn', '322', '黔南');
INSERT INTO `ipm_area_dict` VALUES ('849', '3', 'city_cn', '322', '黔西南');
INSERT INTO `ipm_area_dict` VALUES ('851', '3', 'city_cn', '323', '临沧');
INSERT INTO `ipm_area_dict` VALUES ('852', '3', 'city_cn', '323', '丽江');
INSERT INTO `ipm_area_dict` VALUES ('853', '3', 'city_cn', '323', '保山');
INSERT INTO `ipm_area_dict` VALUES ('854', '3', 'city_cn', '323', '大理');
INSERT INTO `ipm_area_dict` VALUES ('855', '3', 'city_cn', '323', '德宏');
INSERT INTO `ipm_area_dict` VALUES ('856', '3', 'city_cn', '323', '怒江');
INSERT INTO `ipm_area_dict` VALUES ('857', '3', 'city_cn', '323', '文山');
INSERT INTO `ipm_area_dict` VALUES ('858', '3', 'city_cn', '323', '昆明');
INSERT INTO `ipm_area_dict` VALUES ('859', '3', 'city_cn', '323', '昭通');
INSERT INTO `ipm_area_dict` VALUES ('860', '3', 'city_cn', '323', '普洱');
INSERT INTO `ipm_area_dict` VALUES ('861', '3', 'city_cn', '323', '曲靖');
INSERT INTO `ipm_area_dict` VALUES ('862', '3', 'city_cn', '323', '楚雄');
INSERT INTO `ipm_area_dict` VALUES ('863', '3', 'city_cn', '323', '玉溪');
INSERT INTO `ipm_area_dict` VALUES ('864', '3', 'city_cn', '323', '红河');
INSERT INTO `ipm_area_dict` VALUES ('865', '3', 'city_cn', '323', '西双版纳');
INSERT INTO `ipm_area_dict` VALUES ('866', '3', 'city_cn', '323', '迪庆');
INSERT INTO `ipm_area_dict` VALUES ('871', '3', 'city_cn', '324', '咸阳');
INSERT INTO `ipm_area_dict` VALUES ('872', '3', 'city_cn', '324', '商洛');
INSERT INTO `ipm_area_dict` VALUES ('873', '3', 'city_cn', '324', '安康');
INSERT INTO `ipm_area_dict` VALUES ('874', '3', 'city_cn', '324', '宝鸡');
INSERT INTO `ipm_area_dict` VALUES ('875', '3', 'city_cn', '324', '延庆');
INSERT INTO `ipm_area_dict` VALUES ('876', '3', 'city_cn', '324', '榆林');
INSERT INTO `ipm_area_dict` VALUES ('877', '3', 'city_cn', '324', '汉中');
INSERT INTO `ipm_area_dict` VALUES ('878', '3', 'city_cn', '324', '渭南');
INSERT INTO `ipm_area_dict` VALUES ('879', '3', 'city_cn', '324', '西安');
INSERT INTO `ipm_area_dict` VALUES ('880', '3', 'city_cn', '324', '铜川');
INSERT INTO `ipm_area_dict` VALUES ('891', '3', 'city_cn', '325', '临夏');
INSERT INTO `ipm_area_dict` VALUES ('892', '3', 'city_cn', '325', '兰州');
INSERT INTO `ipm_area_dict` VALUES ('893', '3', 'city_cn', '325', '嘉峪关');
INSERT INTO `ipm_area_dict` VALUES ('894', '3', 'city_cn', '325', '天水');
INSERT INTO `ipm_area_dict` VALUES ('895', '3', 'city_cn', '325', '定西');
INSERT INTO `ipm_area_dict` VALUES ('896', '3', 'city_cn', '325', '平凉');
INSERT INTO `ipm_area_dict` VALUES ('897', '3', 'city_cn', '325', '庆阳');
INSERT INTO `ipm_area_dict` VALUES ('898', '3', 'city_cn', '325', '张掖');
INSERT INTO `ipm_area_dict` VALUES ('899', '3', 'city_cn', '325', '武威');
INSERT INTO `ipm_area_dict` VALUES ('900', '3', 'city_cn', '325', '甘南');
INSERT INTO `ipm_area_dict` VALUES ('901', '3', 'city_cn', '325', '白银');
INSERT INTO `ipm_area_dict` VALUES ('902', '3', 'city_cn', '325', '酒泉');
INSERT INTO `ipm_area_dict` VALUES ('903', '3', 'city_cn', '325', '金昌');
INSERT INTO `ipm_area_dict` VALUES ('904', '3', 'city_cn', '325', '陇南');
INSERT INTO `ipm_area_dict` VALUES ('911', '3', 'city_cn', '326', '果洛');
INSERT INTO `ipm_area_dict` VALUES ('912', '3', 'city_cn', '326', '海东');
INSERT INTO `ipm_area_dict` VALUES ('913', '3', 'city_cn', '326', '海北');
INSERT INTO `ipm_area_dict` VALUES ('914', '3', 'city_cn', '326', '海南');
INSERT INTO `ipm_area_dict` VALUES ('915', '3', 'city_cn', '326', '海西');
INSERT INTO `ipm_area_dict` VALUES ('916', '3', 'city_cn', '326', '玉树');
INSERT INTO `ipm_area_dict` VALUES ('917', '3', 'city_cn', '326', '西宁');
INSERT INTO `ipm_area_dict` VALUES ('918', '3', 'city_cn', '326', '黄南');
INSERT INTO `ipm_area_dict` VALUES ('921', '3', 'city_cn', '327', '乌兰察布');
INSERT INTO `ipm_area_dict` VALUES ('922', '3', 'city_cn', '327', '乌海');
INSERT INTO `ipm_area_dict` VALUES ('923', '3', 'city_cn', '327', '兴安盟');
INSERT INTO `ipm_area_dict` VALUES ('924', '3', 'city_cn', '327', '包头');
INSERT INTO `ipm_area_dict` VALUES ('925', '3', 'city_cn', '327', '呼伦贝尔');
INSERT INTO `ipm_area_dict` VALUES ('926', '3', 'city_cn', '327', '呼和浩特');
INSERT INTO `ipm_area_dict` VALUES ('927', '3', 'city_cn', '327', '巴彦淖尔');
INSERT INTO `ipm_area_dict` VALUES ('928', '3', 'city_cn', '327', '赤峰');
INSERT INTO `ipm_area_dict` VALUES ('929', '3', 'city_cn', '327', '通辽');
INSERT INTO `ipm_area_dict` VALUES ('930', '3', 'city_cn', '327', '鄂尔多斯');
INSERT INTO `ipm_area_dict` VALUES ('931', '3', 'city_cn', '327', '锡林郭勒盟');
INSERT INTO `ipm_area_dict` VALUES ('932', '3', 'city_cn', '327', '阿拉善盟');
INSERT INTO `ipm_area_dict` VALUES ('941', '3', 'city_cn', '328', '北海');
INSERT INTO `ipm_area_dict` VALUES ('942', '3', 'city_cn', '328', '南宁');
INSERT INTO `ipm_area_dict` VALUES ('943', '3', 'city_cn', '328', '崇左');
INSERT INTO `ipm_area_dict` VALUES ('944', '3', 'city_cn', '328', '来宾');
INSERT INTO `ipm_area_dict` VALUES ('945', '3', 'city_cn', '328', '柳州');
INSERT INTO `ipm_area_dict` VALUES ('946', '3', 'city_cn', '328', '桂林');
INSERT INTO `ipm_area_dict` VALUES ('947', '3', 'city_cn', '328', '梧州');
INSERT INTO `ipm_area_dict` VALUES ('948', '3', 'city_cn', '328', '河池');
INSERT INTO `ipm_area_dict` VALUES ('949', '3', 'city_cn', '328', '玉林');
INSERT INTO `ipm_area_dict` VALUES ('950', '3', 'city_cn', '328', '百色');
INSERT INTO `ipm_area_dict` VALUES ('951', '3', 'city_cn', '328', '贵港');
INSERT INTO `ipm_area_dict` VALUES ('952', '3', 'city_cn', '328', '贺州');
INSERT INTO `ipm_area_dict` VALUES ('953', '3', 'city_cn', '328', '钦州');
INSERT INTO `ipm_area_dict` VALUES ('954', '3', 'city_cn', '328', '防城港');
INSERT INTO `ipm_area_dict` VALUES ('961', '3', 'city_cn', '329', '山南');
INSERT INTO `ipm_area_dict` VALUES ('962', '3', 'city_cn', '329', '拉撒');
INSERT INTO `ipm_area_dict` VALUES ('963', '3', 'city_cn', '329', '日喀则');
INSERT INTO `ipm_area_dict` VALUES ('964', '3', 'city_cn', '329', '昌都');
INSERT INTO `ipm_area_dict` VALUES ('965', '3', 'city_cn', '329', '林芝');
INSERT INTO `ipm_area_dict` VALUES ('966', '3', 'city_cn', '329', '那曲');
INSERT INTO `ipm_area_dict` VALUES ('967', '3', 'city_cn', '329', '阿里');
INSERT INTO `ipm_area_dict` VALUES ('971', '3', 'city_cn', '330', '中卫');
INSERT INTO `ipm_area_dict` VALUES ('972', '3', 'city_cn', '330', '昊忠');
INSERT INTO `ipm_area_dict` VALUES ('973', '3', 'city_cn', '330', '固原');
INSERT INTO `ipm_area_dict` VALUES ('974', '3', 'city_cn', '330', '石嘴山');
INSERT INTO `ipm_area_dict` VALUES ('975', '3', 'city_cn', '330', '银川');
INSERT INTO `ipm_area_dict` VALUES ('981', '3', 'city_cn', '331', '乌鲁木齐');
INSERT INTO `ipm_area_dict` VALUES ('982', '3', 'city_cn', '331', '伊犁');
INSERT INTO `ipm_area_dict` VALUES ('983', '3', 'city_cn', '331', '克孜勒苏');
INSERT INTO `ipm_area_dict` VALUES ('984', '3', 'city_cn', '331', '克拉玛依');
INSERT INTO `ipm_area_dict` VALUES ('985', '3', 'city_cn', '331', '博尔塔拉');
INSERT INTO `ipm_area_dict` VALUES ('986', '3', 'city_cn', '331', '吐鲁番');
INSERT INTO `ipm_area_dict` VALUES ('987', '3', 'city_cn', '331', '和田');
INSERT INTO `ipm_area_dict` VALUES ('988', '3', 'city_cn', '331', '哈密');
INSERT INTO `ipm_area_dict` VALUES ('989', '3', 'city_cn', '331', '喀什');
INSERT INTO `ipm_area_dict` VALUES ('990', '3', 'city_cn', '331', '塔城');
INSERT INTO `ipm_area_dict` VALUES ('991', '3', 'city_cn', '331', '巴音郭楞');
INSERT INTO `ipm_area_dict` VALUES ('992', '3', 'city_cn', '331', '昌吉');
INSERT INTO `ipm_area_dict` VALUES ('993', '3', 'city_cn', '331', '石河子');
INSERT INTO `ipm_area_dict` VALUES ('994', '3', 'city_cn', '331', '阿克苏');
INSERT INTO `ipm_area_dict` VALUES ('995', '3', 'city_cn', '331', '阿勒泰');
INSERT INTO `ipm_area_dict` VALUES ('1011', '3', 'city_cn', '332', '九龙');
INSERT INTO `ipm_area_dict` VALUES ('1012', '3', 'city_cn', '332', '新界');
INSERT INTO `ipm_area_dict` VALUES ('1013', '3', 'city_cn', '332', '香港岛');
INSERT INTO `ipm_area_dict` VALUES ('1021', '3', 'city_cn', '333', '澳门半岛');
INSERT INTO `ipm_area_dict` VALUES ('1031', '3', 'city_cn', '334', '云林');
INSERT INTO `ipm_area_dict` VALUES ('1032', '3', 'city_cn', '334', '南投');
INSERT INTO `ipm_area_dict` VALUES ('1033', '3', 'city_cn', '334', '台东');
INSERT INTO `ipm_area_dict` VALUES ('1034', '3', 'city_cn', '334', '台中');
INSERT INTO `ipm_area_dict` VALUES ('1035', '3', 'city_cn', '334', '台北');
INSERT INTO `ipm_area_dict` VALUES ('1036', '3', 'city_cn', '334', '台南');
INSERT INTO `ipm_area_dict` VALUES ('1037', '3', 'city_cn', '334', '嘉义');
INSERT INTO `ipm_area_dict` VALUES ('1038', '3', 'city_cn', '334', '基隆');
INSERT INTO `ipm_area_dict` VALUES ('1039', '3', 'city_cn', '334', '宜兰');
INSERT INTO `ipm_area_dict` VALUES ('1040', '3', 'city_cn', '334', '屏东');
INSERT INTO `ipm_area_dict` VALUES ('1041', '3', 'city_cn', '334', '澎化');
INSERT INTO `ipm_area_dict` VALUES ('1042', '3', 'city_cn', '334', '花莲');
INSERT INTO `ipm_area_dict` VALUES ('1043', '3', 'city_cn', '334', '苗栗');
INSERT INTO `ipm_area_dict` VALUES ('1044', '3', 'city_cn', '334', '金门');
INSERT INTO `ipm_area_dict` VALUES ('1045', '3', 'city_cn', '334', '高雄');
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
INSERT INTO `ipm_app_business` VALUES (1, 2, '系统资源消耗', NULL, NULL, NULL);

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
(null,'告警模板',1,'这是一个带有告警类型统计列表的报表，包含告警统计，流量趋势变化和会话数量趋势的变化。',
1532919949,'10,11,12');
insert into ipm_timer_report_detail (id,report_id,module_id,plot_id,plot_type_id) VALUES(null,1,10,1,1),(null,1,10,2,1),(null,1,10,317,1),(null,1,11,32,1),(null,1,11,33,1),(null,1,12,60,1),(null,1,12,61,1),(null,1,12,319,1);
insert into ipm_modal_tableandalarm (id,modal_id,module_id,table_having,warning_having) values (null,1,10,null,1),(null,1,11,null,1),(null,1,12,null,1);

insert into ipm_report_modal (id,name,user_id,description,create_time,module_ids) VALUES
(null,'网络主要指标模板',1,'该模板集中了有关于网络的所有主要KPI指标，并涵盖该观察点的告警统计列表，在以后的版本中，还会对告警进行综合排名.',
1532919949,'10');
insert into ipm_timer_report_detail (id,report_id,module_id,plot_id,plot_type_id) VALUES(null,2,10,276,1),(null,2,10,3,1),(null,2,10,1,1),(null,2,10,317,1),(null,2,10,2,1),(null,2,10,9,1);
insert into ipm_modal_tableandalarm (id,modal_id,module_id,table_having,warning_having) values (null,2,10,null,1),(null,2,10,1,null);
-- --------------------
-- 报表模版默认添加 END
-- --------------------