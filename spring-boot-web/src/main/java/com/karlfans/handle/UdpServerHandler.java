package com.karlfans.handle;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UDP服务器处理
 */
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final Logger log = LoggerFactory.getLogger(UdpServerHandler.class);

    //用来计算server接收到多少UDP消息
    private static int count = 0;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String receiveMsg = packet.content().toString(CharsetUtil.UTF_8);
        log.info("Received UDP Msg:" + receiveMsg);

        //判断接受到的UDP消息是否正确（未实现）
        if (StringUtils.isNotEmpty(receiveMsg)) {
            //计算接收到的UDP消息的数量
            count++;

            // TODO UDP数据处理逻辑


            // 响应数据
            ctx.write(new DatagramPacket(
                    Unpooled.copiedBuffer("QOTM: " + "Got UDP Message!", CharsetUtil.UTF_8), packet.sender()));
        } else {
            log.error("Received Error UDP Messsage:" + receiveMsg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }

}