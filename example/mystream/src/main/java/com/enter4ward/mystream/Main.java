package com.enter4ward.mystream;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import com.enter4ward.myserver.ImageOverlay;
import com.enter4ward.webserver.ChunkHandler;
import com.enter4ward.webserver.Payload;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Discards any incoming data.
 */
public class Main implements ChunkHandler {

	private static Map<String, Payload> globalBuffer = new TreeMap<String, Payload>();
	private static BufferedImage LOGO;

	private Payload getPayload(String path) {
		Payload payload = null;
		synchronized (globalBuffer) {
			payload = globalBuffer.get(path);
			if (payload == null) {
				payload = new Payload();
				globalBuffer.put(path, payload);
			}
		}
		return payload;
	}

	@Override
	public void onChunkArrived(String path, Map<String, List<String>> args, byte[] bytes) {

		Payload payload = getPayload(path);
		payload.setData(bytes);
		payload.signal();
	}

	@Override
	public byte[] onChunkRequest(String path, Map<String, List<String>> args)  {
		Payload payload = getPayload(path);
		payload.await();
		byte[] ret = null;
		ret = payload.getData();
		if (args.containsKey("info")) {
			ret = ImageOverlay.editImage(ret, LOGO);
		}
		return ret;
	}

	@Override
	public Map<String, String> onStartReceiving(String path) {
		Payload payload = getPayload(path);
		return payload.getParams();
	}

	private int port;

	public Main(int port) {

		try {
			LOGO = ImageIO.read(getClass().getResourceAsStream("/logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.port = port;
		ServerSocket httpServer;
		try {
			httpServer = new ServerSocket(1991);

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						while (true) {
							new Thread(new HTTPStreamRunnable(httpServer.accept(), Main.this)).start();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap(); // (2)
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
					.childHandler(new StreamInitializer(this)).option(ChannelOption.SO_BACKLOG, 128) // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync(); // (7)

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to
			// gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {

		new Main(8080).run();
	}
}