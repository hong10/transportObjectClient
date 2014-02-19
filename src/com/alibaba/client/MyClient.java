package com.alibaba.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.domain.MyRequestObject;
import com.alibaba.domain.MyResponseObject;

public class MyClient {
	private static final Logger logger = LoggerFactory
			.getLogger(MyClient.class);

	public static void main(String[] args) {
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(10 * 1000);

		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		

		connector.setHandler(new IoHandlerAdapter() {

			@Override
			public void sessionCreated(IoSession session) throws Exception {
			}

			@Override
			public void sessionOpened(IoSession session) throws Exception {
				MyRequestObject myObj = new MyRequestObject("my name",
						"my value");

				session.write(myObj);
				
				
				System.out.println("客户端连接打开");
			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
			}

			@Override
			public void sessionIdle(IoSession session, IdleStatus status)
					throws Exception {
			}

			@Override
			public void exceptionCaught(IoSession session, Throwable cause)
					throws Exception {
				logger.error(cause.getMessage(), cause);
				session.close(true);
			}

			@Override
			public void messageReceived(IoSession session, Object message)
					throws Exception {

				
					MyResponseObject myResObj = (MyResponseObject) message;
					logger.info("Received " + myResObj);
					// session.close(true);
				
			}

			@Override
			public void messageSent(IoSession session, Object message)
					throws Exception {
				logger.info("Sent " + message);
			}
		});

		IoSession session = null;
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(
					"localhost", 10001));
			future.awaitUninterruptibly();
			session = future.getSession();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(System.in));
			while (true) {
				String inString = bufferedReader.readLine();
				session.write(inString);
				if (inString.equals("quit")) {
					break;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}
}
