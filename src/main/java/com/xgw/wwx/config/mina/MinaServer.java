package com.xgw.wwx.config.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 */
public class MinaServer {

	private static final Logger logger = LoggerFactory.getLogger(MinaServer.class);

	private static final String host = "127.0.0.1";

	private static final int port = 8089;// 定义监听端口

	public static void main(String[] args) {
		//初始化了服务端的TCP/IP 的基于NIO 的套接字，创建一个非阻塞的Server端socket，用NIO
		IoAcceptor acceptor = new NioSocketAcceptor();
		//传输的以换行符为标识的数据，所以使用了Mina 自带的换行符编解码器工厂。
		//第二步：编写过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());//日志配置
		// 指定编码utf-8过滤器
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		//然后调用IoSessionConfig设置读取数据的缓冲区大小、读写通道均在10 秒内无任何操作就进入空闲状态。

		//第三步：把这个ServerIoHandler 注册到MinaServer：指定业务逻辑处理器
		acceptor.setHandler(new ServerIoHandler());
		//第一步：编写IoService
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		try {
			//启动监听
			acceptor.bind(new InetSocketAddress(host, port));
		} catch (IOException e) {
			logger.error("-- minaServer error --", e);
		}

	}

}
