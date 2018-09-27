package com.xgw.wwx.config.mina;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编写IoHandler即ServerIoHandler
 * 服务器端
 */

public class ServerIoHandler extends IoHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(IoHandlerAdapter.class);

	private static ConcurrentHashMap<Long, IoSession> sessionMap = new ConcurrentHashMap<Long, IoSession>();

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String messageStr = message.toString();
		if (messageStr.trim().equalsIgnoreCase("quit")) {
			session.close(true);
			return;
		}
		log.info("server recevie message: {}" + messageStr);
		System.out.println("server recevie message: {}" + messageStr);
		session.write("hi, i am server");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("server closed session id: {}", session.getId());
		System.out.println("server closed session id: {}" + session.getId());
		sessionMap.remove(session.getId());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.info("server opened session id: {}", session.getId());
		System.out.println("server opened session id: {}" + session.getId());
		sessionMap.put(session.getId(), session);
	}

}
