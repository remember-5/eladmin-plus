# 项目简介

请在开发前，一定要先阅读完本文档

本项目是基于[eladmin](https://github.com/elunez/eladmin) 的基础上，二次开发的项目，欢迎各位大佬贡献代码

演示地址：[http://eladmin.remember5.top](http://eladmin.remember5.top)

已同步官方的版本`aadeb2504dfded5e713891bdeebf6fbd10a7d719`

注意！！！
注意！！！
注意！！！

```
DB_HOST=127.0.0.1;DB_PORT=3306;DB_NAME=eladmin;DB_USER=root;DB_PWD=123456,DB_SCHEMA=eladmin_schema;REDIS_HOST=127.0.0.1;REDIS_PORT=6379;REDIS_PWD=;REDIS_DB=0;DRUID_USER=admin;DRUID_PWD=123456;
```

启动前，请加入环境变量：

| 变量名称   | 说明         | 默认值         |
| :--------- | ------------ | -------------- |
| DB_HOST    | 数据库地址   | 默认 127.0.0.1 |
| DB_PORT    | 数据库端口   | 默认 3306      |
| DB_NAME    | 数据库名称   | 默认 eladmin   |
| DB_USER    | 数据库用户名 | 默认 root      |
| DB_PWD     | 用户名密码   | 默认 123456    |
| DB_SCHEMA  | chema       | 默认 eladmin    |
| REDIS_HOST | Redis地址    | 默认127.0.0.1  |
| REDIS_PORT | Redis端口    | 默认6379       |
| REDIS_PWD  | Redis密码    | 默认没有密码   |
| REDIS_DB   | Redis的DB    | 默认 0         |
| DRUID_USER | druid用户    | 默认 admin     |
| DRUID_PWD  | druid密码    | 默认 123456     |



## 新增功能列表

- [x] admin重置/修改普通用户密码
- [x] 安全拦截配置更改到菜单中
- [x] 支持生成excel模版和上传excel批量导入
- [x] 生成代码时可选择生成菜单
- [x] 自动发布
- [x] 多样化配置CI/CD
- [x] 集成minio
- [x] 可动态配置的资源存储
- [x] 支持图片上传及base64图片上传
- [x] 支持mybatis-plus
- [x] 自定义上传头像
- [x] websocket消息通知
- [x] 集成dahan短信接口
- [x] 支持打包为docker镜像
- [x] 多数据源支持
- [x] redis-utils支持lset，zset，geo方法
- [x] 可动态配置获取IP地址
- [x] 升级为使用knife4j
- [x] 支持离线导出接口文档
- [x] 更丰富的权限划分(基础的菜单权限)
- [x] 增加CMS功能
- [x] RSA密码加密解密分不同环境的配置
- [x] 使用p6spy更高效的记录日志
- [x] validation 参数校验 
- [x] 集成Netty
- [x] 依赖和代码分离打包
- [x] 使用最新版log4j2
- [ ] 多租户
- [ ] 导入导出模版支持多表
- [ ] 代码生成支持动态数据源
- [ ] 增加oauth2的支持(单独分支)
- [ ] 支持工作流
- [ ] postgres 生成代码

## 技术选型

- 核心框架：SpringBoot
- ORM框架：Mybatis
- 任务调度：Spring Task + Quartz
- 权限安全：Spring Security
- 网页即时通讯：Netty(WebSocket)
- 连接池：Druid（阿里开源）
- 日志处理：SLF4J(日志门面框架)、logback
- 缓存处理：Redis
- Excel表处理：POI+EasyExcel
- 在线文档：Knife4j
- 实体转换：mapstruct 

## 规定

1. 代码可读性
2. 代码健壮性
3. 代码可运行性

### 分支定义

单独的模块，请单独开启分支, 注意每个分支的`SQL`有差异，详可查看具体分支下的`sql`->`eladmin.sql`

维护人负责代码的维护，修复和发布,代码

![分支图示](docs/image.png)


* `main` postgres版本的eladmin，包含api 的服务
* `el-mysql` mysql版本的eladmin ，包含api 的服务

## 多分支


## 贡献者列表

感谢以下伙伴的付出(排名不分先后)
* [wangjiahao](https://github.com/remember-5)
* [fly](https://github.com/Y914612354)
* [tianhh](https://github.com/tianhhuan)
* [jinjun]()



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

## 项目结构
项目采用按功能分模块的开发方式，结构如下

- `protal` 为门户页面，需要配合nginx或者其他服务一起启用

- `admin-page` 为后台系统的页面，需要配合nginx或者其他服务一起启用

- `eladmin-common` 为系统的公共模块，各种工具类，公共配置存在该模块

- `eladmin-system` 为系统核心模块也是项目入口模块，也是最终需要打包部署的模块

- `eladmin-logging` 为系统的日志模块，其他模块如果需要记录日志需要引入该模块

- `eladmin-tools` 为第三方工具模块，包含：图床、邮件、云存储、本地存储、支付宝

- `eladmin-generator` 为系统的代码生成模块，代码生成的模板在 system 模块中

## 详细结构

```

- admin-page
  - static 静态文件
    - css css样式文件
    - js js代码文件
    - font 字体库
    - img 图片文件
  - index.html 主页面
  - favicon.ico 图标
- portal
    - static 静态文件
      - css css样式文件
      - js js代码文件
      - font 字体库
      - img 图片文件
    - index.html 主页面
    - favicon.ico 图标
- SmartMilitary
    - eladmin-common 公共模块
        - annotation 为系统自定义注解
        - aspect 自定义注解的切面
        - base 提供了Entity、DTO基类和mapstruct的通用mapper
        - config 自定义权限实现、redis配置、swagger配置、Rsa配置等
        - exception 项目统一异常的处理
        - utils 系统通用工具类
    - eladmin-system 系统核心模块（系统启动入口）
        - config 配置跨域与静态资源，与数据权限
            - thread 线程池相关
        - modules 系统相关模块(登录授权、系统监控、定时任务、运维管理等)
    - eladmin-logging 系统日志模块
    - eladmin-tools 系统第三方工具模块
    - eladmin-generator 系统代码生成模块
- 
```


## 使用教程

### 官方文档
see https://el-admin.vip/

### wiki
see https://gitlab.remember5.top/nt1/wiki/-/wikis/eladmin/EL-ADMIN-%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9
