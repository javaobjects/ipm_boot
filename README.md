<p align="center">
    <a href='https://docs.oracle.com/en/java/javase/8'><img alt="Java 8" src="./src/main/webapp/img/Java8.png"></a>
    <a href='https://docs.spring.io/spring-boot/docs/2.6.2-SNAPSHOT/reference/html'><img alt="Spring Boot 2" src="https://img.shields.io/badge/Spring%20Boot%202-%23000000.svg?logo=springboot"></a>
    <a href='https://staging-cn.vuejs.org'><img alt="Vue 3" src="https://img.shields.io/badge/Vue%202%20-%232b3847.svg?logo=vue.js"></a><br/>
    <a href='#'><img alt="Github stars" src="https://img.shields.io/github/stars/201206030/novel?logo=github"></a>
    <a href='#'><img alt="Github forks" src="https://img.shields.io/github/forks/201206030/novel?logo=github"></a>
    <a href='#'><img alt="Gitee stars" src="https://gitee.com/novel_dev_team/novel/badge/star.svg?theme=gitee"></a>
    <a href='#'><img alt="Gitee forks" src="https://gitee.com/novel_dev_team/novel/badge/fork.svg?theme=gitee"></a>
</p>

# ipm_boot

###  编码规范

- 规范方式：严格遵守阿里编码规约。
- 命名统一：简介最大程度上达到了见名知意。
- 分包明确：层级分明可快速定位到代码位置。
- 注释完整：描述性高大量减少了开发人员的代码阅读工作量。
- 工具规范：使用统一jar包避免出现内容冲突。
- 代码整洁：可读性、维护性高。

### 包结构

```
+- ipm_boot
|   +- .checkstyle
|   +- .classpath
|   +- .gitignore
|   +- .project
|   +- build.gradle
|   +- doc
|   |   +- alarm协同.txt
|   |   +- enlang-java.data
|   |   +- enlang-js.data
|   |   +- iPM-DOC.xls
|   |   +- ipm_data.sql
|   |   +- ipm_data_en.sql
|   |   +- ipm_proto_plan.sql
|   |   +- ipm_public_proto_plan.sql
|   |   +- ipm_table.sql
|   |   +- ip_cn.sql
|   |   +- npm.ndm
|   |   +- tomcat-conf
|   |   |   +- Catalina
|   |   |   |   +- localhost
|   |   |   +- catalina.policy
|   |   |   +- catalina.properties
|   |   |   +- context.xml
|   |   |   +- logging.properties
|   |   |   +- server.xml
|   |   |   +- tomcat-users.xml
|   |   |   +- tomcat-users.xsd
|   |   |   +- tomcat.keystore
|   |   |   +- web.xml
|   |   +- webdeb-conf
|   |   |   +- control
|   |   |   +- postinst
|   |   +- xr_checkstyle.xml
|   |   +- 告警设置接口与格式说明.docx
|   |   +- 智能告警比率类基线展示对接说明.docx
|   |   +- 通信对说明.txt
|   |   +- 项目变动需要协调内容
|   +- dolphin.sh
|   +- enlang.sh
|   +- gradle
|   |   +- wrapper
|   |   |   +- gradle-wrapper.jar
|   |   |   +- gradle-wrapper.properties
|   +- gradlew
|   +- gradlew.bat
|   +- ipm_boot.iml
|   +- ipm_new.iml
|   +- LICENSE
|   +- pack.sh
|   +- pom.xml
|   +- README.md
|   +- src
|   |   +- main
|   |   |   +- java
|   |   |   |   +- com
|   |   |   |   |   +- protocolsoft
|   |   |   |   |   |   +- alarm
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- enumeration
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   +- task
|   |   |   |   |   |   |   +- thread
|   |   |   |   |   |   |   +- util
|   |   |   |   |   |   +- app
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- enumeration
|   |   |   |   |   |   |   +- provider
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   +- util
|   |   |   |   |   |   +- datapush
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   |   +- task
|   |   |   |   |   |   +- depthanaly
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- provider
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   +- email
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   +- jtopo
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   +- kpi
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- enumeration
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   +- log
|   |   |   |   |   |   |   +- annotation
|   |   |   |   |   |   |   +- aop
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   +- map
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- provider
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   +- protoplan
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   +- sendemail
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- server
|   |   |   |   |   |   +- servers
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- provider
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   +- system
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- filter
|   |   |   |   |   |   |   +- listener
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   +- url
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- provider
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   +- thread
|   |   |   |   |   |   +- usability
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   |   +- task
|   |   |   |   |   |   +- user
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- provider
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   +- utils
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   +- view
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   +- watchpoint
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- provider
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   +- word
|   |   |   |   |   |   |   +- bean
|   |   |   |   |   |   |   +- controller
|   |   |   |   |   |   |   +- dao
|   |   |   |   |   |   |   +- daoprovider
|   |   |   |   |   |   |   +- service
|   |   |   |   |   |   |   |   +- impl
|   |   |   |   |   |   |   +- task
|   |   |   |   |   |   |   +- util
|   |   |   +- webapp
|   |   |   |   +- css
|   |   |   |   |   +- bootstrap
|   |   |   |   |   +- fonts
|   |   |   |   |   +- form.css
|   |   |   |   |   +- generics.css
|   |   |   |   |   +- icons.css
|   |   |   |   |   +- jedate-6.0.0
|   |   |   |   |   |   +- jedate.css
|   |   |   |   |   +- jquery-dad
|   |   |   |   |   |   +- jquery.dad.css
|   |   |   |   |   +- jquery-dad-v1
|   |   |   |   |   |   +- jquery.dad.css
|   |   |   |   +- customcss
|   |   |   |   |   +- alarm
|   |   |   |   |   |   +- alarm.css
|   |   |   |   |   +- bootstrap-table-self.css
|   |   |   |   |   +- common.css
|   |   |   |   |   +- custjedate.css
|   |   |   |   |   +- enjoyhint
|   |   |   |   |   |   +- enjoyhint.css
|   |   |   |   |   +- form
|   |   |   |   |   |   +- form.css
|   |   |   |   |   +- jebox.css
|   |   |   |   |   +- jnode
|   |   |   |   |   |   +- jnode.css
|   |   |   |   |   +- Linearicons.css
|   |   |   |   |   +- login.css
|   |   |   |   |   +- style.css
|   |   |   |   |   +- themify-icons.css
|   |   |   |   +- customjs
|   |   |   |   |   +- alarm
|   |   |   |   |   |   +- alarm.info.js
|   |   |   |   |   |   +- alarm.js
|   |   |   |   |   |   +- alarmNoticeSet.js
|   |   |   |   |   +- bsTable
|   |   |   |   |   |   +- handlebsTable.js
|   |   |   |   |   +- busiIdSer.js
|   |   |   |   |   +- changeBackground.js
|   |   |   |   |   +- cockpit.js
|   |   |   |   |   +- cockpitmanage.js
|   |   |   |   |   +- colose.js
|   |   |   |   |   +- d3
|   |   |   |   |   |   +- d3graph.js
|   |   |   |   |   |   +- d3graph_util.js
|   |   |   |   |   +- decUtil.js
|   |   |   |   |   +- draw.js
|   |   |   |   |   +- enjoyhint
|   |   |   |   |   |   +- enjoyhint.min.js
|   |   |   |   |   |   +- hint-cock.js
|   |   |   |   |   |   +- hint-form.js
|   |   |   |   |   |   +- hint-sequence.js
|   |   |   |   |   +- flowmanageSetting
|   |   |   |   |   |   +- flowmanageSetting.js
|   |   |   |   |   +- from
|   |   |   |   |   |   +- fromHistory.js
|   |   |   |   |   |   +- fromPlanAdd.js
|   |   |   |   |   |   +- fromPlanChange.js
|   |   |   |   |   |   +- fromPlanManger.js
|   |   |   |   |   |   +- fromSetAdd.js
|   |   |   |   |   |   +- fromSetChange.js
|   |   |   |   |   +- header.js
|   |   |   |   |   +- heatMap.js
|   |   |   |   |   +- highchartUtil
|   |   |   |   |   |   +- highchart.util.js
|   |   |   |   |   +- IE9
|   |   |   |   |   |   +- IeBlock.js
|   |   |   |   |   +- jnode
|   |   |   |   |   |   +- jnode.js
|   |   |   |   |   +- jsc.js
|   |   |   |   |   +- login-dns
|   |   |   |   |   |   +- mode-ecb.js
|   |   |   |   |   |   +- tripledes.js
|   |   |   |   |   +- login.js
|   |   |   |   |   +- loginBackground.js
|   |   |   |   |   +- logout.js
|   |   |   |   |   +- number-format.js
|   |   |   |   |   +- queue
|   |   |   |   |   |   +- bssSession.js
|   |   |   |   |   |   +- queue.js
|   |   |   |   |   +- queueTimeChoose.js
|   |   |   |   |   +- search.js
|   |   |   |   |   +- setSq.js
|   |   |   |   |   +- setting
|   |   |   |   |   |   +- network.card.js
|   |   |   |   |   |   +- setTing.js
|   |   |   |   |   |   +- sysDataExportAdd.js
|   |   |   |   |   |   +- sysDataExportChange.js
|   |   |   |   |   |   +- user.js
|   |   |   |   |   |   +- userPassword.js
|   |   |   |   |   +- sidebar.js
|   |   |   |   |   +- table
|   |   |   |   |   |   +- bootstrap.table.util.js
|   |   |   |   |   +- timeTogoback.js
|   |   |   |   |   +- utils
|   |   |   |   |   |   +- shrink.js
|   |   |   |   +- flowmanageSetting.html
|   |   |   |   +- flowmanageSetting2.html
|   |   |   |   +- flowmanageSetting3.html
|   |   |   |   +- flowmanageSetting4.html
|   |   |   |   +- fonts
|   |   |   |   |   +- fontawesome
|   |   |   |   |   +- icons
|   |   |   |   |   +- Linearicons
|   |   |   |   |   +- opan-sans
|   |   |   |   |   +- themify.eot
|   |   |   |   |   +- themify.svg
|   |   |   |   |   +- themify.ttf
|   |   |   |   |   +- themify.woff
|   |   |   |   +- fromHistory.html
|   |   |   |   +- fromPlan.html
|   |   |   |   +- fromPlanManage.html
|   |   |   |   +- fromSetting.html
|   |   |   |   +- heatMap.html
|   |   |   |   +- help
|   |   |   |   |   +- help-cn.pdf
|   |   |   |   |   +- help-en.pdf
|   |   |   |   |   +- help.files
|   |   |   |   |   |   +- colorschememapping.xml
|   |   |   |   |   |   +- filelist.xml
|   |   |   |   |   |   +- header.html
|   |   |   |   |   |   +- themedata.thmx
|   |   |   |   |   +- help.html
|   |   |   |   +- httpSerkpi.html
|   |   |   |   +- images
|   |   |   |   +- img
|   |   |   |   |   +- alarm
|   |   |   |   |   +- body
|   |   |   |   |   +- browsers
|   |   |   |   |   +- click_lefticon
|   |   |   |   |   +- cover-bg.jpg
|   |   |   |   |   +- demo-Img
|   |   |   |   |   |   +- 拓朴图
|   |   |   |   |   +- icon
|   |   |   |   |   +- netcockpit
|   |   |   |   |   +- topo
|   |   |   |   +- index.html
|   |   |   |   +- jnode.html
|   |   |   |   +- jnode_new.html
|   |   |   |   +- jnode_old1011.html
|   |   |   |   +- js
|   |   |   |   |   +- bootstrap
|   |   |   |   |   |   +- bootstrap-select-zh_CN.min.js
|   |   |   |   |   |   +- bootstrap-select.min.js
|   |   |   |   |   |   +- bootstrap-table-export.js
|   |   |   |   |   |   +- bootstrap-table-zh-CN.js
|   |   |   |   |   |   +- bootstrap-table.js
|   |   |   |   |   |   +- bootstrap.min.js
|   |   |   |   |   |   +- html5shiv.min.js
|   |   |   |   |   |   +- respond.min.js
|   |   |   |   |   |   +- tableExport.js
|   |   |   |   |   +- chosen.min.js
|   |   |   |   |   +- d3
|   |   |   |   |   |   +- d3.v4.js
|   |   |   |   |   +- fileupload.min.js
|   |   |   |   |   +- functions.js
|   |   |   |   |   +- highcharts
|   |   |   |   |   |   +- code
|   |   |   |   |   |   |   +- drilldown.js
|   |   |   |   |   |   |   +- highcharts.js
|   |   |   |   |   |   |   +- map.js
|   |   |   |   |   |   |   +- modules
|   |   |   |   |   |   |   |   +- exporting.js
|   |   |   |   |   |   |   |   +- no-data-to-display.js
|   |   |   |   |   |   |   |   +- series-label.js
|   |   |   |   |   |   +- heatmap.js
|   |   |   |   |   |   +- mapdata
|   |   |   |   |   |   |   +- countries
|   |   |   |   |   |   |   |   +- cn
|   |   |   |   |   |   |   |   |   +- china.js
|   |   |   |   |   |   |   |   |   +- geomap
|   |   |   |   |   |   |   |   |   |   +- json
|   |   |   |   |   +- icheck.js
|   |   |   |   |   +- jQuery
|   |   |   |   |   |   +- drage.js
|   |   |   |   |   |   +- jedate-6.0.0
|   |   |   |   |   |   |   +- jquery.jedate.js
|   |   |   |   |   |   +- jquery-dad-v1
|   |   |   |   |   |   |   +- jquery.dad.min.js
|   |   |   |   |   |   +- jquery-gridly-1.6.3
|   |   |   |   |   |   |   +- jquery.gridly.js
|   |   |   |   |   |   +- jquery-mousewheel-3.1.9
|   |   |   |   |   |   |   +- jquery.mousewheel.js
|   |   |   |   |   |   +- jquery.jsPlumb-1.62
|   |   |   |   |   |   |   +- jquery.jsPlumb-1.6.2-min.js
|   |   |   |   |   |   +- jquery.min.js
|   |   |   |   |   |   +- tdrage
|   |   |   |   |   |   |   +- Tdrag.js
|   |   |   |   |   |   +- timeStamp.js
|   |   |   |   |   +- jquery-ui-1.9.2
|   |   |   |   |   |   +- jquery-ui-1.9.2.min.js
|   |   |   |   |   +- jquery.jebox.min.js
|   |   |   |   |   +- jquerydad
|   |   |   |   |   |   +- jquery.dad.min.js
|   |   |   |   |   +- jtopo
|   |   |   |   |   |   +- jtopo-0.4.8-min.js
|   |   |   |   |   +- select.min.js
|   |   |   |   +- json
|   |   |   |   |   +- jtopo
|   |   |   |   |   |   +- noempty
|   |   |   |   +- login.html
|   |   |   |   +- map.html
|   |   |   |   +- META-INF
|   |   |   |   |   +- MANIFEST.MF
|   |   |   |   +- mysqlSerkpi.html
|   |   |   |   +- netCockpit.html
|   |   |   |   +- observationPointkpi.html
|   |   |   |   +- oracleSerkpi.html
|   |   |   |   +- serverSidekpi.html
|   |   |   |   +- setSq.html
|   |   |   |   +- settingindex.html
|   |   |   |   +- settingindex2.html
|   |   |   |   +- settingindex3.html
|   |   |   |   +- settingPassword.html
|   |   |   |   +- sqlSerkpi.html
|   |   |   |   +- sysDataExport.html
|   |   |   |   +- systemCapital.html
|   |   |   |   +- systemLogs.html
|   |   |   |   +- systemTool.html
|   |   |   |   +- urlkpi.html
|   |   |   |   +- userSidekpi.html
|   |   |   |   +- WEB-INF
|   |   |   |   |   +- cgi
|   |   |   |   |   +- lib
|   |   |   |   |   |   +- rrd-java-drive.jar
|   |   |   |   |   +- web.xml
|   |   +- test
|   |   |   +- java
|   |   |   |   +- com
|   |   |   |   |   +- protocolsoft
|   |   |   |   |   |   +- system
|   |   |   |   |   |   |   +- SystemNetworkSetServiceTest.java
|   |   |   |   |   |   |   +- SystemSetServiceImplTest.java
|   |   |   |   |   |   +- utils
|   |   |   |   |   |   |   +- DateUtilsTest.java
|   |   |   |   |   |   |   +- PropertiesUtilTest.java
|   +- version.sh

```

### 前端技术：

* jQuery Version: 1.10.2
* jQuery UI - Version: 1.9.2
* jquery.jeBox Version: 1.6 
* jQuery.jeDate Version: 6.0.0
* jquery.dad Version: 1
* jqeury.mosewheel Version: 3.1.9
* jQuery Gridly Version: 1.6.3
* jQuery.jsPlumb Version: 1.62
* jtopo Version:0.4.8
* Highcharts Version:6.0.2
* Bootstrap Version: 3.1.1
* Bootstrap-select Version: 1.12.4
* Bootstrap-select-cn Version: 1.12.4
* Bootstrap-table version: 1.11.1
* Bootstrap-table-zh-CN
* Bootstrap-table-export
* HTML5Shiv Version: 3.7.2 
* Respond.js Version: 1.4.2
* d3 Version:4
* tableExport
* chosen
* icheck
* select
* Highcharts.until 自写二次封装插件
* d3graph 自写二次封装插件
* Bootstrap.table.until 自写二次封装插件
* shrink 自写表单伸缩功能插件
* header 自写头部功能插件
* sidebar 自写左侧边栏插件
* timeStamp 自写时间戳相互转换插件

### 部分页面效果

![](src/main/webapp/img/demo-Img/1.png)

![](src/main/webapp/img/demo-Img/2.png)

![](src/main/webapp/img/demo-Img/3.png)

![](src/main/webapp/img/demo-Img/4.png)

![](src/main/webapp/img/demo-Img/4-1.png)

![](src/main/webapp/img/demo-Img/5.png)

![](src/main/webapp/img/demo-Img/6.png)

![](src/main/webapp/img/demo-Img/7.png)

![](src/main/webapp/img/demo-Img/8.png)

![](src/main/webapp/img/demo-Img/9.png)

![](src/main/webapp/img/demo-Img/10.png)

![](src/main/webapp/img/demo-Img/11.png)

![](src/main/webapp/img/demo-Img/12.png)

![](src/main/webapp/img/demo-Img/13.png)

![](src/main/webapp/img/demo-Img/14.png)

![](src/main/webapp/img/demo-Img/15.png)

![](src/main/webapp/img/demo-Img/16.png)

![](src/main/webapp/img/demo-Img/17.png)

![](src/main/webapp/img/demo-Img/18.png)

![](src/main/webapp/img/demo-Img/19.png)

![](src/main/webapp/img/demo-Img/20.png)

![](src/main/webapp/img/demo-Img/21.png)

![](src/main/webapp/img/demo-Img/22.png)

![](src/main/webapp/img/demo-Img/23.png)

![](src/main/webapp/img/demo-Img/24.png)

![](src/main/webapp/img/demo-Img/25.png)

+ 拓朴图 

![](src/main/webapp/img/demo-Img/拓朴图/1.png)

![](src/main/webapp/img/demo-Img/拓朴图/2.png)

![](src/main/webapp/img/demo-Img/拓朴图/3.png)

![](src/main/webapp/img/demo-Img/拓朴图/4.png)

+ [用d3实现的拓朴图示意图](https://gitee.com/d3Object/d3ForceDemo)

[项目地址](http://175.102.15.166/)
