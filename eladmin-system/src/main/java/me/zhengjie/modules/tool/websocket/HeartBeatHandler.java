package me.zhengjie.modules.tool.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 若连续2次无读事件，则关闭这个客户端channel
 *
 * @author fly
 * @date 2021-12-24
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private int lossConnectCount = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("监听心跳： {}", ctx.channel().id().asLongText());

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                lossConnectCount++;
                if (lossConnectCount > 2) {
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
