# 项目简介

请在开发前，一定要先阅读完本文档

本项目是基于[eladmin](https://github.com/elunez/eladmin) 的基础上，二次开发的项目，欢迎贡献代码

演示地址：[http://eladmin.remember5.top](http://eladmin.remember5.top)

已同步官方的版本`f78adec4ec757b6e22f116abd60eba0ae081bac7`

```
DB_HOST=127.0.0.1;DB_PORT=3306;DB_NAME=eladmin;DB_USER=root;DB_PWD=123456,DB_SCHEMA=eladmin_schema;REDIS_HOST=127.0.0.1;REDIS_PORT=6379;REDIS_PWD=;REDIS_DB=0;DRUID_USER=admin;DRUID_PWD=123456;
```

启动前，请加入环境变量：

| 变量名称         | 说明              | 默认值          |
|:-------------|-----------------|--------------|
| DB_HOST      | 数据库地址           | 默认 127.0.0.1 |
| DB_PORT      | 数据库端口           | 默认 3306      |
| DB_NAME      | 数据库名称           | 默认 eladmin   |
| DB_USER      | 数据库用户名          | 默认 root      |
| DB_PWD       | 用户名密码           | 默认 123456    |
| DB_SCHEMA    | schema          | 默认 eladmin   |
| REDIS_HOST   | Redis地址         | 默认127.0.0.1  |
| REDIS_PORT   | Redis端口         | 默认6379       |
| REDIS_PWD    | Redis密码         | 默认没有密码       |
| REDIS_DB     | Redis的DB        | 默认 0         |
| DRUID_USER   | druid用户         | 默认 admin     |
| DRUID_PWD    | druid密码         | 默认 123456    |
| MINIO_HOST   | minio地址         | 默认 无         |
| MINIO_BUCKET | minio桶          | 默认 无         |
| MINIO_AK     | minio的AccessKey | 默认 无         |
| MINIO_SK     | minio的SecretKey | 默认 无         |


## 技术选型
- 核心框架：SpringBoot
- ORM框架：Mybatis
- 任务调度：Spring Task + Quartz
- 权限安全：Spring Security
- 网页即时通讯：Netty(WebSocket)
- 连接池：Druid（阿里开源）
- 日志处理：SLF4J(日志门面框架)
- 缓存处理：Redis
- Excel表处理：POI+EasyExcel
- 在线文档：Knife4j
- 实体转换：Mapstruct 

## 贡献者列表
感谢以下伙伴的付出(排名不分先后)
* [wangjiahao](https://github.com/remember-5)
* [fly](https://github.com/Y914612354)
* [tianhh](https://github.com/tianhhuan)

## 主要特性
- 使用最新技术栈，社区资源丰富。
- 高效率开发，代码生成器可一键生成前后端代码
- 支持数据字典，可方便地对一些状态进行管理
- 支持接口限流，避免恶意请求导致服务层压力过大
- 支持接口级别的功能权限与数据权限，可自定义操作
- 自定义权限注解与匿名接口注解，可快速对接口拦截与放行
- 对一些常用地前端组件封装：表格数据请求、数据字典等
- 前后端统一异常拦截处理，统一输出异常，避免繁琐的判断
- 支持在线用户管理与服务器性能监控，支持限制单用户登录
- 支持运维管理，可方便地对远程服务器的应用进行部署与管理
- 支持多数据源，可以同时连接多个不同的数据库
- 支持离线api文档下载，方便传输接口文档
- 支持websocket消息个性推送，安全加密传输
- 集成CMS文章发布，采用更高效好用的编辑器，让文章管理变得更简单
- 支持docker镜像发布运行
- 可动态更换minio、七牛云等存储管理服务，支持个性化上传文件
- 项目同时兼容jpa和mybatis，使开发人员的选择变得更多，不再有选择焦虑症
- 支持各模块的批量上传和下载功能
- 已适配gitlab-runner，做CI/CD的自动部署，多元化配置

##  系统功能
- 用户管理：提供用户的相关配置，新增用户后，默认密码为123456
- 角色管理：对权限与菜单进行分配，可根据部门设置角色的数据权限
- 菜单管理：已实现菜单动态路由，后端可配置化，支持多级菜单
- 部门管理：可配置系统组织架构，树形表格展示
- 岗位管理：配置各个部门的职位
- 字典管理：可维护常用一些固定的数据，如：状态，性别等
- 系统日志：记录用户操作日志与异常日志，方便开发人员定位排错
- SQL监控：采用druid 监控数据库访问性能，默认用户名admin，密码123456
- 定时任务：整合Quartz做定时任务，加入任务日志，任务运行情况一目了然
- 代码生成：高灵活度生成前后端代码，减少大量重复的工作任务
- 邮件工具：配合富文本，发送html格式的邮件
- 七牛云存储：可同步七牛云存储的数据到系统，无需登录七牛云直接操作云数据
- 支付宝支付：整合了支付宝支付并且提供了测试账号，可自行测试
- 服务监控：监控服务器的负载情况
- 运维管理：一键部署你的应用
- 存储资源管理：管理minio、七牛云、阿里云OSS
- 文章列表：内置功能强大的高效编辑器，提高书写速度，集成了文件上传和审核功能。
- 栏目管理：管理文章的栏目，可以随心配置栏目

### 官方文档
see https://el-admin.vip/
