package com.alibaba.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

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
				//session.close(true);
				/*
				//logger.info("�յ�����˵���Ϣ:"+message);
				//session.close(true);
				
				
				if(message.toString().trim() == "GET_DEVICES_LIST"){
					//��������
					DeviceStatus ds1 = new DeviceStatus("6585684", "aaa", "2R4F23TFW3QRF3Q",
							"1.2.0-D-20140132.2341", "1.2.3.7", "occupied", "cat");
					DeviceStatus ds2 = new DeviceStatus("4546346", "bbb", "983RJOFI8JRQE4RDQW3",
							"1.2.0-D-20140350.5464", "1.2.3.34", "online", "mouse");
					DeviceStatus ds3 = new DeviceStatus("578678", "ccc", "983RJOFI8JRQE4RDQW3",
							"1.2.0-D-20140350.5464", "1.2.3.34", "idle", "mouse");
					DeviceStatus ds4 = new DeviceStatus("986797678", "ddd", "983RJOFI8JRQE4RDQW3",
							"1.2.0-D-20140350.5464", "1.2.3.34", "online", "mouse");
					DeviceStatus ds5 = new DeviceStatus("986797678", "eee", "983RJOFI8JRQE4RDQW3",
							"1.2.0-D-20140350.5464", "1.2.3.34", "online", "mouse");
					DeviceStatus ds6 = new DeviceStatus("986797678", "fff", "983RJOFI8JRQE4RDQW3",
							"1.2.0-D-20140350.5464", "1.2.3.34", "online", "mouse");
					DeviceStatus ds7 = new DeviceStatus("986797678", "ggg", "983RJOFI8JRQE4RDQW3",
							"1.2.0-D-20140350.5464", "1.2.3.34", "online", "mouse");
					DeviceStatus ds8 = new DeviceStatus("986797678", "hhh", "983RJOFI8JRQE4RDQW3",
							"1.2.0-D-20140350.5464", "1.2.3.34", "online", "mouse");
					ArrayList<DeviceStatus> al = new ArrayList<DeviceStatus>();
					al.add(ds1);
					al.add(ds2);
					al.add(ds3);
					al.add(ds4);
					al.add(ds5);
					al.add(ds6);
					al.add(ds7);
					al.add(ds8);
					
//					DevicesPool dp = new DevicesPool(al);
					session.write(al);
				}*/
				
				
				
				
				
				
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
					"localhost", 9999));
			future.awaitUninterruptibly();
			session = future.getSession();
			
			// ������Ϣ�������
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
