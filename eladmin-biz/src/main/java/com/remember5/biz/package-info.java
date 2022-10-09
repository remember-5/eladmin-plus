// 业务层代码 包含api和system两个模块公用的代码

// TODO 把公共的代码挪到这里,如AppVersion等 system模块和api模块重复代码 (问：为啥不放到core，效果是一样，答：因为有依赖分离打包情况，core模块尽量减少变更)

// TODO 经过测试，Navicat(16.0.9)中Postgres(140002) to Mysql(5.7.28) 会有Bool格式转换成Varchar(5)的情况

// TODO 适配mysql的方案，修改pg-bool 为 pg-int 0=false 1=true 同时 mysql 也更改int为0=false 1=true
package com.remember5.biz;