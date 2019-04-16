package echo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
    context.write(message);
//    A ChannelHandlerContext object provides various operations
//    that enable you to trigger various I/O events and operations.
//    Here, we invoke write(Object) to write the received message in verbatim.
//    Please note that we did not release the received message unlike we did in the DISCARD example.
//    It is because Netty releases it for you when it is written out to the wire.
    context.flush();
//    ctx.write(Object) does not make the message written out to the wire.
//    It is buffered internally, and then flushed out to the wire by ctx.flush().
//    Alternatively, you could call ctx.writeAndFlush(msg) for brevity.
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
    cause.printStackTrace();
    context.close();
  }

}
