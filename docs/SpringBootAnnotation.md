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


# @EnableWebMvc
表示完全自己控制mvc配置，也就是说所有配置自己重写，所有默认配置都没了！
有时会导致很多请求进不来，或者参数转换出错之类的，因为spring mvc默认的转换器已经不生效了,包括全局配置的Jackson也会失效，
所以在大多数情况下我们需要的是在其基础配置上添加自定义配置