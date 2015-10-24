package com.enter4ward.mystream;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.TreeMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
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
	private int readBytes = 0;
	private ByteArrayOutputStream dataBuffer = new ByteArrayOutputStream();
	private ChunkHandler handler;
	private HttpRequest request;
	private QueryStringDecoder queryString;

	public StreamHandler(ChunkHandler handler) {
		super();
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
		if (readBytes == 0) {
			handler.onChunkArrived(queryString.path(), new TreeMap<String, List<String>>(), dataBuffer.toByteArray());
			dataBuffer.reset();
		} 
		readBytes = 0;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) { // (1)
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)  {
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
							ctx.writeAndFlush(chunk);
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
							dataBuffer.write(in.readByte());
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