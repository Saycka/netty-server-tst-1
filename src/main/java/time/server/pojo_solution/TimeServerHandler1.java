package time.server.pojo_solution;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler1 extends ChannelInboundHandlerAdapter {

  @Override
  public void channelActive(final ChannelHandlerContext context) {

    ChannelFuture f = context.writeAndFlush(new UnixTime());
    f.addListener(ChannelFutureListener.CLOSE);

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
    cause.printStackTrace();
    context.close();
  }

}
