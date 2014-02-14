package com.alibaba.codec;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.core.session.IoSession;

/**
 * 文件传输解码工厂
 */
public class FileCodecFactory implements ProtocolCodecFactory {
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	/*public FileCodecFactory(boolean client) {
		if (client) {
			encoder = new FileRequestEncoder(Charset.forName("UTF-8"));
			decoder = new FileResponseDecoder();
		} else {
			encoder = new FileResponseEncoder();
			decoder = new FileRequestDecoder(Charset.forName("UTF-8"));
		}
	}*/
	public FileCodecFactory() {
			decoder = new FileResponseDecoder();
			encoder = new FileResponseEncoder();
	}

	public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
		return decoder;
	}
}
