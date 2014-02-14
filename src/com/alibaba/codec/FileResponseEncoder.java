package com.alibaba.codec;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.alibaba.domain.FileResponse;

/**
 * 文件传输服务端编码类
 */
public class FileResponseEncoder extends ProtocolEncoderAdapter {

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		IoBuffer buffer = IoBuffer.allocate(1024).setAutoExpand(true);

		FileResponse fr = (FileResponse) message;
		buffer.putInt(fr.getOrder());

		if (fr.getFileContents() == null) {

			buffer.flip();
			out.write(buffer);
			return;
		}

		ByteBuffer bb = fr.getFileContents();
		for (int i = 0; i < bb.limit(); i++) {
			bb.position(i);
			byte s = bb.get();
			buffer.put(s);
		}

		buffer.flip();
		out.write(buffer);
	}

}
