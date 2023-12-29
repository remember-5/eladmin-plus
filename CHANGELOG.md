# 更新日志
## 2.3.2
- Added: 增加依赖分离打包
- Added: 增加MybatisPlus自定义Handler,可对部分数据库字段加密
- Fixed: 修复springboot 2.7.x 和 Swagger2.x 版本不兼容
- Fixed: 修复swagger 文档访问异常
- Changed: 优化生成模版代码
- Changed: 优化websocket认证
- Changed: 优化MybatisPlus分页拦截器不再传入数据库类型
- Changed: 优化适配SpringBoot2.7新版自动装配文件
- Removed: 删除多余的p6spy

## 2.3.1
2023/09/21
- 修复AuditorConfiguration缺失
- 完善redisUtil
- 完善pdfbox生成图片(一页)

## 2.3.0
2023/09/11
- 升级jdk11
- 分离项目为单独的模块
- 优化代码生成
- 优化dockerfile

## 2.2.2
2023/4/26
- 优化微信登录获取openid的方式
- 修复生成代码的bug
- 优化文件上传和minio
- 升级websocket
- 完善黑白名单
- 优化jwt
- 升级部分依赖


## 2.2.1
2022/12/15
- 增加上传文件，校验文件格式
- 增加websocket测试用例
- 增加对app热更的增强支持
- 修复微信一键登录导致泄漏sessionKey的问题
- 修复pg生成代码因为联合索引导致的查询多个字段的问题


## 2.2.0
2022/10/19
- 修复生成代码的bug
- 新增mysql数据库的适配
- 优化认证拦截配置
- 优化微信登录
- 优化minio上传后返回信息



## 2.1.0
2022/10/09
- 新增版本控制'
- @Log注解增加channel标识
- 修复redisson中db问题
- 修复timestamp显示错误
- 修复FetchType.LAZY未生效问题
- 优化Token为同一套颁发
- 升级部分依赖

## 2.0.2
2022/09/29
- 新增微信登录
- 新增log注解中的channel(区分sys和api)
- 修改包命名 `common` 更改为 `core`
- 修改`redis`和`captcha`到`core`模块
- 修改跨域配置到`core`模块
- 修复redisson中db不是动态注入

## 2.0.1
2022/09/23
- 修改minio上传不识别.wgt等格式，设置默认的`contentType`
- 更新hutool版本
- 修改jwt由hutool提供
- 新增密码生成工具

## 2.0.0
2022/04/26
- 增加pg数据库支持，不再支持mysql，mysql可以使用`el-mysql`分支
- 支持pg代码生成
- 拆分redis，minio为独立原子性模块


## 1.4.2
2022/01/17
- 升级minio为最新版本
- 优化pom的格式


## 1.4.1
2022/01/06
- 优化minio的代码
- 升级其他依赖版本
- 升级hutool，解决之前excel冲突的问题
- 删除老版本websocket,采用新版本的netty

## 1.4.0
2021/12/31
- 增加netty通信
- 增加数据校验
- 升级springboot version to 2.5.7
- 超管可修改用户密码
- 修改R类型的公共方法
- 修改logback为log4j2

## 1.3.1
2021/12/10
- 优化生成代码缺少主键的提示错误
- README文件中增加一些技术说明
- 删除多余代码
- 更换记录sql日志插件为p6spy
- 更丰富的权限划分

## 1.3.0
2021/12/03
- 升级swagger为knife4j
- 增加多数据源配置
- 优化验证码配置类
- 优化部分warn和注入等问题


## 1.2.2
2021/11/30
- 增加CMS功能


## 1.2.1
2021/11/25
- 支持docker部署
- 修改RSA和AES的使用
- 删除多余代码


## 1.2.0
2021/11/09
新增
- admin可以重置用户密码
- 模版增加导入功能
- 模版增加导出模版功能
- 生成代码时可选择配置菜单

修改
- 修改ci/cd到.gitlab文件下
- 修改拦截器过滤地址，现在放到config配置文件中
- 修改RestResult为R
- 优化存储资源，修复在修改后没能及时生效的情况

升级
- 升级fastjson版本为1.2.76
- 升级mybatis-plus为3.4.3


## 1.1.0
2021/08/23
新增
- 新增动态注入minio
- 新增大汉接口通用工具


## 1.0.2.1
2021/05/11

修复bug
- 修复`minIO`→`Base64`格式上传方法→接口接参参数问题


## 1.0.2
2021/04/20

新增
- 增加消息通知(websocket集成)



## 1.0.1
2021/04/14

新增
- 资源管理 by @yangjianfei
