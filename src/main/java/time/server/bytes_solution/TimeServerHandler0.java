package time.server.bytes_solution;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler0 extends ChannelInboundHandlerAdapter {

  @Override
  public void channelActive(final ChannelHandlerContext context) {
//    As explained, the channelActive() method will be invoked when a connection is established
//    and ready to generate traffic. Let's write a 32-bit integer that represents the current time in this method.

    System.out.println("Client " + context.channel().remoteAddress() + " connected");

    final ByteBuf time = context.alloc().buffer(4);

//    To send a new message, we need to allocate a new buffer which will contain the message.
//    We are going to write a 32-bit integer,
//    and therefore we need a ByteBuf whose capacity is at least 4 bytes.
//    Get the current ByteBufAllocator via ChannelHandlerContext.alloc() and allocate a new buffer.

    time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

    final ChannelFuture f = context.writeAndFlush(time);
//    As usual, we write the constructed message

    f.addListener(new ChannelFutureListener() {
      public void operationComplete(ChannelFuture channelFuture) throws Exception {
        assert f == channelFuture;
        context.close();
      }
    });
//    Another point to note is that the ChannelHandlerContext.write()
//    (and writeAndFlush()) method returns a ChannelFuture.
//    A ChannelFuture represents an I/O operation which has not yet occurred.
//    It means, any requested operation might not have been performed yet
//    because all operations are asynchronous in Netty.
//    For example, the following code might close the connection even before a message is sent:
//    Channel ch = ...;
//    ch.writeAndFlush(message);
//    ch.close();
//    How do we get notified when a write request is finished then?
//    This is as simple as adding a ChannelFutureListener to the returned ChannelFuture.
//    Here, we created a new anonymous ChannelFutureListener which closes the Channel when the operation is done.
//    Alternatively, you could simplify the code using a pre-defined listener:
//    f.addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
    cause.printStackTrace();
    context.close();
  }

}
