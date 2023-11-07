# 如何用http发送消息给用户

添加spring-boot-webflux包

```xml
<dependencies>
    <!-- 其他依赖项 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
</dependencies>
```


```java
public class TestController {
    @PostMapping()
    @ApiOperation(value = "发送弹窗消息")
    public Mono dialog(@RequestBody String body) {
        log.info("dialogVO:{}", body);
        // 创建一个Mono，表示异步操作的结果
        CompletableFuture<R> sendFuture = new CompletableFuture<>();
        // 发送请求给websocket
        final ConcurrentHashMap<String, Channel> userChannelMap = NettyProperties.getUserChannelMap();
        final Channel channel = userChannelMap.get(body.getUserId());
        if (Objects.isNull(channel)) {
            sendFuture.complete(R.fail(REnum.A0001.code, "", "用户不在线"));
        } else {
            // 如果该用户的客户端是与本服务器建立的channel,直接推送消息
            final String jsonString = JSONObject.toJSONString(body);
            final ChannelFuture writeFuture = channel.writeAndFlush(new TextWebSocketFrame(jsonString));
            writeFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    // 发送成功
                    sendFuture.complete(R.success());
                } else {
                    // 发送失败
                    sendFuture.complete(R.fail(REnum.A0001.code, "", "消息推送失败"));
                }
            });
        }

        return Mono.fromFuture(sendFuture).flatMap(Mono::just);
    }
}
```
