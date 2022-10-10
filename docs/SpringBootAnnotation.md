# SpringBoot注解
## Spring条件注解@Conditional
Spring 4提供了一个更通用的基于条件的Bean的创建方式，即使用@Conditional注解，我们可以通过 @Conditional 注解来实现这类操作
 
see [https://juejin.cn/post/6951223104689569806](https://juejin.cn/post/6951223104689569806)

# Swagger注解
https://doc.xiaominfo.com/knife4j/

# Validation注解
https://www.iocoder.cn/Spring-Boot/Validation/?self
https://www.cnblogs.com/sueyyyy/p/12865578.html

# Restful API 版本号控制
https://blog.csdn.net/weixin_39255905/article/details/110391515


# Spring注解@EnableWebMvc使用坑点解析
https://blog.csdn.net/z69183787/article/details/108587150
https://blog.csdn.net/zxc123e/article/details/84636521
https://weixiao.blog.csdn.net/article/details/80249894
https://www.cnblogs.com/hongdada/p/9120899.html

1. @EnableWebMvc+extends WebMvcConfigurationAdapter，在扩展的类中重写父类的方法即可，这种方式会屏蔽springboot的@EnableAutoConfiguration中的设置
2. extends WebMvcConfigurationSupport，在扩展的类中重写父类的方法即可，这种方式会屏蔽springboot的@EnableAutoConfiguration中的设置
3. extends WebMvcConfigurationAdapter/WebMvcConfigurer，在扩展的类中重写父类的方法即可，这种方式依旧使用springboot的@EnableAutoConfiguration中的设置


@EnableWebMvc
表示完全自己控制mvc配置，也就是说所有配置自己重写，所有默认配置都没了！
有时会导致很多请求进不来，或者参数转换出错之类的，因为spring mvc默认的转换器已经不生效了,包括全局配置的Jackson也会失效，
所以在大多数情况下我们需要的是在其基础配置上添加自定义配置


# Postgres索引
【强制】主键索引名为 pk_表名_字段名；唯一索引名为 uk_表名_字段名；普通索引名则为 idx_表名_字段名。
【推荐】临时表以 tmp_ 开头，子表以规则结尾，例如按年分区的主表如果为tbl, 则子表为tbl_2016，tbl_2017，。。。
