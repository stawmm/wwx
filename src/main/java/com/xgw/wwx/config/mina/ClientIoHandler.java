package com.xgw.wwx.config.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *用来处理请求连接的。即客户端
 */
public class ClientIoHandler extends IoHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(IoHandlerAdapter.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// 从服务器中接收到消息后的处理
		log.info("client receive message: {}" + message.toString());
		System.out.println("client receive message: {}" + message.toString());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("client opened session id: {}" + session.getId());
		log.info("client opened session id: {}", session.getId());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("client closed session id: {}" + session.getId());
		log.info("client closed session id: {}", session.getId());
	}

}
