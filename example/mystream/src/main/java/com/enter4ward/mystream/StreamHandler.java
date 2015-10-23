package com.enter4ward.mystream;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.TreeMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * Handles a server-side channel.
 */
public class StreamHandler extends SimpleChannelInboundHandler<Object> { // (1)

	private ByteArrayOutputStream chunkBuffer = new ByteArrayOutputStream();
	private ByteArrayOutputStream dataBuffer = new ByteArrayOutputStream();
	private ChunkHandler handler;
	private HttpRequest request;
	private QueryStringDecoder queryString;
	private ChannelPipeline channel;

	public StreamHandler(ChannelPipeline channel, ChunkHandler handler) {
		super();
		this.channel = channel;

		this.handler = handler;

	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);

		System.out.println("NEW CHANNEL | " + ctx.toString());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		if (chunkBuffer.size() == 0) {
			handler.onChunkArrived(queryString.path(), new TreeMap<String, List<String>>(), dataBuffer.toByteArray());
			dataBuffer.reset();
		} else {
			dataBuffer.write(chunkBuffer.toByteArray());
		}
		chunkBuffer.reset();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) { // (1)
		final ByteBuf time = ctx.alloc().buffer(4); // (2)
		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
		ctx.writeAndFlush(time);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			if (msg instanceof HttpRequest) {
				request = (HttpRequest) msg;
				queryString = new QueryStringDecoder(request.getUri());

				if (HttpMethod.GET.equals(request.getMethod())) {
					FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
					// res.headers().set("Content-Type", "text/plain");
					res.headers().set(Names.TRANSFER_ENCODING, Values.CHUNKED);
					ctx.writeAndFlush(res);
					
					while (true) {
						byte[] b = handler.onChunkRequest(queryString.path(), new TreeMap<String, List<String>>());
						if (b != null) {
							HttpContent chunk = new DefaultHttpContent(Unpooled.wrappedBuffer(b));
							channel.writeAndFlush(chunk);
							System.out.println(b.length);
						}
					}
				}

			} else if (msg instanceof DefaultHttpContent) {
				if (request != null) {

					if (HttpMethod.POST.equals(request.getMethod())) {
						DefaultHttpContent content = (DefaultHttpContent) msg;
						ByteBuf in = content.content();
						while (in.isReadable()) {
							chunkBuffer.write(in.readByte());
						}
					} else {
						System.out.println(request.getMethod());
					}
				}
			}

		} finally {
			// ReferenceCountUtil.release(msg); // (2)
		}

	}

}