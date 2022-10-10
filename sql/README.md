# .sql生成
1. navicat点击"数据传输" -> 传输到文件。选择postgres数据库
2. 移动到sql路径下
3. 修改原本sql文件名,在后面加1
4. 修改新的sql为刚才修改到文件名，查看sql差异
经过测试，Navicat(16.0.9)中Postgres(140002) to Mysql(5.7.28) 会有Bool格式转换成Varchar(5)的情况

另外在postgres中到自增主键，到mysql也会变成not null的字段，没有添加自增属性

适配mysql的方案，修改pg-bool 为 pg-int 0=false 1=true 同时 mysql 也更改int为0=false 1=true
# 使用
- `postgres` 使用 `postgres-init.sql`
- `mysql` 使用 `mysql-init.sql`



# 问题
有问题的表

sys_log 增加channel_id `channel_id` int NOT NULL COMMENT '渠道类型 1=sys 2=app'
