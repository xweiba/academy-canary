# academy-canary
该项目为微信公众号 `求学大作战` 后端, 框架采用 `sping-boot` + `mybatis` + `redis` + `shiro` + `mysql` 组合而成.

API 文档使用`swagger2`生成, 访问地址: `/a/swagger-ui.html`

## 项目约定  
分层领域模型约定(遵守阿里巴巴Java开发手册): 
- DO (Data Object): 与数据库表结构一一对应, 通过DAO层向上传输数据源对象.
- DTO (Data Transfer Object): 数据传输对象, Service或Manager向外传输的对象.
- BO (Business Object): 业务对象. 由Service层输出的封装业务逻辑的对象.
- AO (Application Object): 应用对象. 在Web层与Service层之间抽象的复用对象模型, 极为贴近展示层, 复用度不高.
- VO (View Object): 显示层对象, 通常是Web向模板渲染引擎层传输的对象.
- POJO (Plain Ordinary Java Object): 在本手册中,  POJO专指只有setter/getter/toString的简单类, 包括DO/DTO/BO/VO等.
- Query: 数据查询对象, 各层接收上层的查询请求. 注意超过2个参数的查询封装, 禁止使用Map类来传输.

领域模型命名规约(同上):
- 数据对象: xxxDO, xxx即为数据表名.
- 数据传输对象: xxxDTO, xxx为业务领域相关的名称.
- 展示对象: xxxVO, xxx一般为网页名称.
- POJO是DO/DTO/BO/VO的统称, 禁止命名成xxxPOJO.

## 目录说明
本项目分为前台业务和后台业务, 采用前后端分离模式开发, 后端使用模块化开发, 将`Service层`与`WEB层`分离, `Core` 为项目核心, 定义整个项目的框架. 

- 前台
    - Service层, 为`WEB层`提供调用接口  
    目录: `academy-canary-home-service`
    - WEB层, 为前端提供`RESTful`风格远程调用接口   
    目录: `academy-canary-home-web`
- 后台
    - Service层, 为`WEB层`提供调用接口  
     目录: `academy-canary-admin-service`
    - WEB层, 为前端提供`RESTful`风格远程调用接口  
     目录: `academy-canary-admin-web`
- 核心
    - Core层, 提供整个项目的框架, `数据持久化`, `全局异常处理`, `常量`, `全局工具类`等均由`Core层`提供给`Service层` 和 `WEB` 层调用  
    目录: `academy-canary-core`
    
- 各节点说明
    - `service` : 定义业务处理接口
    - `controller` : 定义`RESTful` 控制接口
    - `business` : 定义各层数据对象
    - `consts` : 定义公共常量类
    - `enums` : 定义全局枚举类
    - `framework` : 项目框架, 包含各个组件
    - `config` : 项目采用注解方式配置各个组件
    - `exception`: 异常处理
    - `persistence` : 数据持久层
    - `plugin` : 外部插件
    - `util` : 工具

## 服务器端目录
源码目录:
- 前端前台目录: `/data/svn/academy-canary-home-html` 
- 前端后台目录: `/data/svn/academy-canary-admin-html`
- 后端目录: `/data/svn/academy-canary`

运行目录: 
- 前端, 由`Nginx`运行
    - 前台目录: `/data/webs/academy-canary-home-html/run` 
    - 后台目录: `/data/webs/academy-canary-admin-html/run`
    
- 后端, 使用`SpringBoot`的`jar`方式运行
    - 前台目录: `/data/services/academy-canary-server/home/run`
    - 后台目录: `/data/services/academy-canary-server/admin/run`

项目备份目录:
- 后端
    - 前台目录: `/data/services/academy-canary-service/home/backups`
    - 后台目录: `/data/services/academy-canary-service/admin/backups`

日志目录: 
- 后端
    - 前台日志: `/data/services/academy-canary-server/home/logs`
    - 后台日志: `/data/services/academy-canary-server/admin/logs`
    
部署脚本:
- 目录: `/data/deploy/academy-canary` 
- 脚本: `all-deploy.sh`
    - 部署所有代码: `all-deploy.sh 5`
    - 前端, 源码使用svn同步后复制到运行目录后重载Nginx
        - 前台部署: `all-deploy.sh 1`
        - 后台部署: `all-deploy.sh 2`
    - 后端, 源码使用svn同步后用maven编译好将原项目备份后复制到运行文件夹到运行项目) 
        - 前台部署: `all-deploy.sh 3`
        - 后台部署: `all-deploy.sh 4`

按照约定重新写了接口. 
