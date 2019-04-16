package discard.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
//  DiscardServerHandler extends ChannelInboundHandlerAdapter,
//  which is an implementation of ChannelInboundHandler.
//  ChannelInboundHandler provides various event handler methods that you can override.
//  For now, it is just enough to extend ChannelInboundHandlerAdapter rather
//  than to implement the handler interface by yourself.

  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
//    We override the channelRead() event handler method here.
//    This method is called with the received message,
//    whenever new data is received from a client.
//    In this example, the type of the received message is ByteBuf.

    //first realisation
//    discard((ByteBuf) message);

    //second realisation
    printAndDiscard(message);

  }

  private void printAndDiscard(Object message) {
    ByteBuf in = (ByteBuf) message;
    try {
      while (in.isReadable()) {
//        This inefficient loop can actually be simplified to:
//        System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII))
        System.out.println((char) in.readByte());
        System.out.flush();
      }
    } finally {
      ReferenceCountUtil.release(message);
//      Alternatively, you could do in.release() here.
    }
  }

  private void discard(ByteBuf message) {
    message.release();
//    To implement the DISCARD protocol, the handler has to ignore the received message.
//    ByteBuf is a reference-counted object which has to be released explicitly via the release() method.
//    Please keep in mind that it is the handler's responsibility
//    to release any reference-counted object passed to the handler.
//    Usually, channelRead() handler method is implemented like the following:
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//      try {
//        // Do something with msg
//      } finally {
//        ReferenceCountUtil.release(msg);
//      }
//    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
    cause.printStackTrace();
    context.close();
  }

}
