package com.penngo.http;

import java.util.List;
import java.util.Map;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class WebSocketServer {

	private int port = 0;
	public WebSocketServer( int port ){
		this.port = port;
	}
	
	public void run() throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new WebSocketServerInitializer())
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true );
			System.out.println("WebsocketChatServer start");
			
			ChannelFuture f = b.bind(port).sync();
		}finally{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			System.out.println("WebsocketChatServer stop");
		}
	}
	
	public static void main(String []args )throws Exception{
		int port = 0;
		if( args.length > 0){
			port = Integer.parseInt(args[0]);
		}else{
			port = 8080;
		}
		
		new WebSocketServer(port).run();
	}
	
}
