package com.xgw.wwx.config.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * 我们实现Mina 中的TCPClient，因为前面说过无论是Server 端还是Client 端，在Mina中的执行流程都是一样的。唯一不同的就是MinaServer 的Client 端实现是IoConnector。
 */
public class MinaClient {
	private SocketConnector connector;
	private ConnectFuture future;
	private IoSession session;
	private String host = "127.0.0.1";
	private int port = 8089;

	public boolean connect() {

		connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		chain.addLast("logger", new LoggingFilter());//日志配置
		chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		//注册ServerIoHandler
		connector.setHandler(new ClientIoHandler());

		future = connector.connect(new InetSocketAddress(host, port));
		// 等待是否连接成功，相当于是转异步执行为同步执行。
		future.awaitUninterruptibly();
		// 连接成功后获取会话对象。如果没有上面的等待，由于connect()方法是异步的，session可能会无法获取。
		session = future.getSession();
		session.getConfig().setUseReadOperation(true);
		return future.isConnected();
	}

	/**
	 * 往服务器发送消息
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		session.write(message);
	}

	/**
	 * 关闭与服务器的连接
	 * 
	 * @return
	 */
	public boolean close() {
		CloseFuture future = session.getCloseFuture();
		future.awaitUninterruptibly(1000);
		connector.dispose();
		return true;
	}
}
