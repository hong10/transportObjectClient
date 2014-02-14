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

import com.alibaba.dexcodec.FileTransportCodecFactory;
import com.alibaba.dexcodec.FileTransportDecoder;
import com.alibaba.dexcodec.FileTransportEncoder;
import com.alibaba.domain.FileResponse;
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
		
		connector.getFilterChain()
		.addLast(
				"protocol",
				new ProtocolCodecFilter(new FileTransportCodecFactory(
						new FileTransportEncoder(),
						new FileTransportDecoder())));

		connector.setHandler(new IoHandlerAdapter() {

			@Override
			public void sessionCreated(IoSession session) throws Exception {
			}

			@Override
			public void sessionOpened(IoSession session) throws Exception {
//				MyRequestObject myObj = new MyRequestObject("my name",
//						"my value");
//
//				session.write(myObj);
				
				
				System.out.println("客户端连接打开");
				/*FileRequest fr = new FileRequest();
				fr.setFileName("123.jpg");
				session.write(fr);*/
			FileResponse fr = new FileResponse(null, 0x11);
				session.write(fr);
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


				if(message instanceof FileResponse){
					/*接收信息转化为FileResponse*/
					FileResponse frs = (FileResponse)message;
					
//					frs.setFileName("aaa.png");
					String newFile = "D:\\tasklist_client";
					
					int order = frs.getOrder();
					
					System.out.println(order);
					
					if(order == 17){
						/*打开文件并创建输出流*/
						File file = new File(newFile);
						FileOutputStream fos = null;
						try {
						/*文件不存在创建一个新的文件*/
						if(!file.exists())
							file.createNewFile(); 
							fos = new FileOutputStream(file, false);
							ByteBuffer bf = frs.getFileContents();
							bf.flip();
							byte[] b = new byte[bf.limit()];
							
							System.out.println(b.length);
							
							for(int i=0;i<bf.limit();i++){
								b[i] = bf.get();
							}
							System.out.println("传输长度： "+bf.limit());
							fos.write(b,4,b.length-4);
							fos.flush();
							//System.out.println("客户端请求成功");
							
							System.out.println("传输完毕");
						}catch(Exception e){
							e.printStackTrace();
						}finally{
							try {
								fos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else{
						try {
							throw new Exception("指令错误");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
				
				
				
					MyResponseObject myResObj = (MyResponseObject) message;
					logger.info("Received " + myResObj);
					// session.close(true);

				}
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
