USE ipm;

DROP TABLE IF EXISTS `ipm_nic`;
CREATE TABLE `ipm_nic` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(100) NOT NULL,
	`vid` varchar(100),
	`vxid` varchar(100),
	`lid1` varchar(100),
	`lid2` varchar(100),
	`lid3` varchar(100),
	`lid4` varchar(100),
	`lid5` varchar(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_watchpoint`;
CREATE TABLE `ipm_watchpoint` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(100) NOT NULL,
	`did` varchar(100) NOT NULL,
    `vid` varchar(100),
    `vxid` varchar(100),
    `lid1` varchar(100),
    `lid2` varchar(100),
    `lid3` varchar(100),
    `lid4` varchar(100),
    `lid5` varchar(100),
    `bandwidth` bigint,
    `ip` varchar(50),
    `port` int(5),
    `contacts` varchar(200),
    `telephone` varchar(50),
    `email` varchar(100),
    `validterm` int,
    `isLocal` boolean default true
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_system_user`;
CREATE TABLE `ipm_system_user` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`role_id` int NOT NULL,
	`username` varchar(32) NOT NULL,
	`password` varchar(64) NOT NULL,
	`realname` varchar(32) NOT NULL,
	`email` varchar(64) DEFAULT NULL,
	`telephone` varchar(32) NOT NULL,
	`createtime` int,
	`loginState` boolean DEFAULT false,
	`loginOvertime` boolean DEFAULT false
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_usability_set`;
CREATE TABLE `ipm_usability_set` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT ,
  `name` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `ip` varchar(32) NOT NULL,
  `port` varchar(32) NOT NULL,
  `interval` varchar(32) NOT NULL,
  `lastExecTime` varchar(32) DEFAULT NULL,
  `status` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `des` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `app_id` int(11) ,
  `busiId` int(11) 
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_user_configure`;
CREATE TABLE `ipm_user_configure` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`user_id` int NOT NULL,
	`key` varchar(64) NOT NULL,
	`value` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_juris_module`;
CREATE TABLE `ipm_juris_module` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`nameen` varchar(64) NOT NULL,
	`namezh` varchar(64) NOT NULL,
	`order` int
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_authorize_juris`;
CREATE TABLE `ipm_authorize_juris` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`user_id` int NOT NULL,
	`module_id` int NOT NULL,
	`center_id` int NOT NULL,
	`app_id` int NOT NULL,
	`order` int
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_system_role`;
CREATE TABLE `ipm_system_role` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(32) NOT NULL,
	`descrption` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_monitor_view`;
CREATE TABLE `ipm_monitor_view` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(32) NOT NULL,
	`descrption` varchar(256) DEFAULT NULL,
	`createuserid` int NOT NULL,
	`updatetime` int,
	`lockstatus` boolean
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_authorize_module`;
CREATE TABLE `ipm_authorize_module` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`nameen` varchar(32) NOT NULL,
	`namezh` varchar(32) NOT NULL,
	`iscore` boolean NOT NULL,
	`isopen` boolean NOT NULL,
	`order` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_app_business`;
CREATE TABLE `ipm_app_business` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`module_id` int NOT NULL,
	`name` varchar(40) NOT NULL,
	`displayip` varchar(1024),
	`bandwidth` bigint,
	`descrption` varchar(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_app_ip_port`;
CREATE TABLE `ipm_app_ip_port` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`app_id` int NOT NULL,
	`ip` bigint,
	`port` int
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_app_column`;
CREATE TABLE `ipm_app_column` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`app_id` int NOT NULL,
	`columnen` varchar(100) NOT NULL,
	`columnzh` varchar(100),
	`displayorder` int
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_msg_fixed`;
CREATE TABLE `ipm_msg_fixed` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `app_id` int NOT NULL,
  `client_ipport` varchar(100) DEFAULT NULL,
  `status_code` varchar(100) DEFAULT NULL,
  `success_code` varchar(100) DEFAULT NULL,
  `failed_code` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_bus_tags`;
CREATE TABLE `ipm_bus_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL,
  `bus_tag_0` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_1` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_2` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_3` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_4` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_5` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_6` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_7` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_8` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_9` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_10` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_11` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_12` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_13` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_14` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_15` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_16` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_17` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_18` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bus_tag_19` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_list_type`;
CREATE TABLE `ipm_list_type` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(31) NOT NULL,
	`module_id` int
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_list_column`;
CREATE TABLE `ipm_list_column` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`type_id` int NOT NULL,
	`columnen` varchar(64) NOT NULL,
	`columnzh` varchar(64) NOT NULL,
	`calcul` varchar(3),
	`isdefault` boolean NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_user_list_column`;
CREATE TABLE `ipm_user_list_column` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`user_id` int NOT NULL,
	`column_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_logs`;
CREATE TABLE `ipm_logs` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`user_id` int NOT NULL,
	`time` int NOT NULL,
	`module_id` int NOT NULL,
	`msg` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_small_module`;
CREATE TABLE `ipm_small_module` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`module_id` int NOT NULL,
	`name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_plot_option`;
CREATE TABLE `ipm_plot_option` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`module_id` int NOT NULL,
	`group_id` int NOT NULL,
	`kpi_id` int,
	`name` varchar(128) NOT NULL,
	`graph` boolean NOT NULL,
	`number` boolean NOT NULL,
	`calcul` varchar(3),
	`order` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_plot_option_type`;
CREATE TABLE `ipm_plot_option_type` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`option_id` int NOT NULL,
	`type_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_plot_type`;
CREATE TABLE `ipm_plot_type` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(64) NOT NULL,
	`type` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_plot_group_type`;
CREATE TABLE `ipm_plot_group_type` (
    `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_ip_location_cn`;
CREATE TABLE `ipm_ip_location_cn` (
	`loc_id_cn` int NOT NULL PRIMARY KEY,
	`start_ip_cn` varchar(45) DEFAULT NULL,
	`end_ip_cn` varchar(45) DEFAULT NULL,
	`ip_start_num` bigint(20) DEFAULT NULL,
	`ip_end_num` bigint(20) DEFAULT NULL,
	`continent` varchar(45) DEFAULT NULL,
	`country` varchar(45) DEFAULT NULL,
	`region_cn` varchar(45) DEFAULT NULL,
	`city_cn` varchar(45) DEFAULT NULL,
	`district` varchar(45) DEFAULT NULL,
	`isp_cn` varchar(45) DEFAULT NULL,
	`country_english` varchar(45) DEFAULT NULL,
	`country_code` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_area_dict`;
CREATE TABLE `ipm_area_dict` (
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`level_id` INT ,
	`level_name` VARCHAR(50) ,
	`parent_id` INT ,
	`name_cn` VARCHAR(50) 
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_kpis`;
CREATE TABLE `ipm_kpis` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(64) NOT NULL,
	`display_name` varchar(64) NOT NULL,
	`unit` varchar(9),
	`cause` varchar(1024)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_kpi`;
CREATE TABLE `ipm_alarm_kpi` (
    `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `module_id` int NOT NULL,
    `kpi_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_type`;
CREATE TABLE `ipm_alarm_type` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`nameen` varchar(20) NOT NULL,
	`namezh` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_level`;
CREATE TABLE `ipm_alarm_level` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `type_id` int NOT NULL,
	`nameen` varchar(20) NOT NULL,
	`namezh` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_kpialgorithm`;
CREATE TABLE `ipm_alarm_kpialgorithm` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `kpitype` int NOT NULL,
    `kpi_id` int NOT NULL,
    `algorithm_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_algorithm`;
CREATE TABLE `ipm_alarm_algorithm` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nameen` varchar(20) NOT NULL,
	`namezh` varchar(40) NOT NULL,
	`algorithm` int NOT NULL,
	`algorithminfo` varchar(50) ,
	`descrption` varchar(60) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_customkpi`;
CREATE TABLE `ipm_alarm_customkpi` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`module_id` int NOT NULL,
    `business_id` int NOT NULL,
	`namezh` varchar(100),
	`nameen` varchar(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_customkpigroup`;
CREATE TABLE `ipm_alarm_customkpigroup` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`customkpi_id` int NOT NULL,
    `kpi_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_log`;
CREATE TABLE `ipm_alarm_log` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`watchpoint_id` int NOT NULL,
    `alarmset_id` int NOT NULL,
	`starttime` int NOT NULL,
	`endtime` int NOT NULL,
    `handledflag` char(1) NOT NULL,
	`finishflag` char(1) NOT NULL,
    `triggerflag`  int ,
    `responseuser` int ,
	`responsetime` int
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_set`;
CREATE TABLE `ipm_alarm_set` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `watchpoint_id` int,
	`business_id` int NOT NULL,
	`kpitype` int NOT NULL,
    `kpi_id`  int NOT NULL,
	`level_id`  int NOT NULL,
	`updateflag` char(1) NOT NULL,
	`lowthresh` double,
	`highthresh` double
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_globalset`;
CREATE TABLE `ipm_alarm_globalset` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`module_id` int NOT NULL,
    `watchpoint_id` int,
	`kpitype` int NOT NULL,
    `kpi_id`  int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_globalvalue`;
CREATE TABLE `ipm_alarm_globalvalue` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`globalset_id` int NOT NULL,
	`level_id` int NOT NULL,
	`triggerflag` int,
    `triggerusestatus` int,
	`triggervalue` double
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_trigger`;
CREATE TABLE `ipm_alarm_trigger` (
	`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `alarmset_id` int,
    `triggerflag` int,
    `triggerusestatus` int,
    `triggervalue` double,
	`triggerunit` varchar(14) NOT NULL,
	`triggertime` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_alarm_baseline`;
CREATE TABLE `ipm_alarm_baseline` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `watchpoint_id` INT,
    `module_id` INT,
    `business_id` INT,
    `kpi_id` INT,
    `temp_flag` INT,
    `start_time` INT,
    `end_time` INT
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_data_storage`;
CREATE TABLE `ipm_data_storage` (
    `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `watchpoint_id` varchar(40),
    `starttime` varchar(8),
    `endtime` varchar(8),
    `ip` varchar(128),
    `port` int,
    `bpf` varchar(2048),
    `size` int,
    `state` boolean NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_app_topn_config`;
CREATE TABLE `ipm_app_topn_config` (
    `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `plot_id` int NOT NULL,
    `summary` varchar(128) NOT NULL,
    `column` varchar(128) NOT NULL,
    `where` varchar(128),
    `group` varchar(128),
    `order` varchar(128),
    `limit` int
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_url_set`;
CREATE TABLE `ipm_url_set` (
    `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `app_id` int(20) NOT NULL,
    `num` int(20) NOT NULL,
    `name` varchar(100) NOT NULL,
    `url` varchar(1024) NOT NULL,
    `isStored` boolean default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_center_ip`;
CREATE TABLE `ipm_center_ip` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `port` int(5) NOT NULL,
  `descrption` varchar(500),
  `delay` int(5) NOT NULL default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_syslog_set`;
CREATE TABLE `ipm_syslog_set` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `port` int(5) NOT NULL,
  `descrption` varchar(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_report_type`;
CREATE TABLE `ipm_report_type` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_timer_report`;
CREATE TABLE `ipm_timer_report` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `type_id` int NOT NULL,
  `user_id` int NOT NULL,
  `name` varchar(60) NOT NULL,
  `createtime` int NOT NULL,
  `state` boolean NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_timer_report_detail`;
CREATE TABLE `ipm_timer_report_detail` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `report_id` int NOT NULL,
  `module_id` int NOT NULL,
  `watchpoint_id` int,
  `app_id` int,
  `plot_id` int NOT NULL,
  `plot_type_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_report_history`;
CREATE TABLE `ipm_report_history` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `type_id` int NOT NULL,
  `name` varchar(60) NOT NULL,
  `createtime` int NOT NULL,
  `datastime` int NOT NULL,
  `dataetime` int NOT NULL,
  `user_id` int(11) NOT NULL,
  `send_time` int(11),
  `send_names` varchar(255),
  `recive_names` varchar(255),
  `send_state` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_data_push`;
CREATE TABLE `ipm_data_push` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `granularity` varchar(256) NOT NULL,
  `moduleType` int(11) NOT NULL,
  `watchpointId` int(11)  DEFAULT NULL,
  `busiId` int(11)  DEFAULT NULL,
  `kpiIds` varchar(256)  DEFAULT NULL,
  `brokerIp` varchar(32) NOT NULL,
  `port` varchar(32) NOT NULL,
  `topic` varchar(64) NOT NULL,
  `type` varchar(32) NOT NULL,
  `serverIp` varchar(32)  DEFAULT NULL,
  `clientIp` varchar(32)  DEFAULT NULL,
  `pushType` varchar(32)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_report_modal`;
CREATE TABLE `ipm_report_modal` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(127) NOT NULL,
  `user_id` int(11) NOT NULL,
  `description` varchar(255),
  `create_time` int(16) NOT NULL,
  `module_ids` varchar(127)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_report_plan`;
CREATE TABLE `ipm_report_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(127) NOT NULL,
  `user_id` int(11) NOT NULL,
  `modal_id` int(11) NOT NULL,
  `type_id` tinyint(1) NOT NULL,
  `create_time` int(16) NOT NULL,
  `send_times` int(11) DEFAULT NULL,
  `next_sendtime` int(16) DEFAULT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '1',
  `starttime` int(16) DEFAULT NULL,
  `endtime` int(16) DEFAULT NULL,
  `compare_to_last` tinyint(1) DEFAULT NULL,
  `watchpoint_ids` varchar(127) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_report_email`;
CREATE TABLE `ipm_report_email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_id` int(11) NOT NULL,
  `sender` varchar(127) DEFAULT NULL,
  `reciver` varchar(127) DEFAULT NULL,
  `email` varchar(127) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_report_business`;
CREATE TABLE `ipm_report_business` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_id` int(11) NOT NULL,
  `module_id` int(11),
  `name` varchar(127),
  `business_id` int(11),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_email_control`;
CREATE TABLE `ipm_email_control` (
  `id` tinyint(1) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `email_server` varchar(127) NOT NULL,
  `email_port` varchar(5) NOT NULL,
  `email_user_name` varchar(127) NOT NULL,
  `email_password` varchar(127) NOT NULL,
  `email_author_code` varchar(64) NOT NULL,
  `user_name` varchar(127) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_modal_tableandalarm`;
CREATE TABLE `ipm_modal_tableandalarm` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `modal_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  `table_having` tinyint(1) DEFAULT NULL,
  `warning_having` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `ipm_center_user`;
CREATE TABLE `ipm_center_user` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `center_id` int NOT NULL,
  `contacts` varchar(14) NOT NULL,
  `telephone` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `uptime` int NOT NULL,
  `limitdate` int NOT NULL,
  `state` tinyint(1) NOT NULL,
  `version` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;