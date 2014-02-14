package com.alibaba.dexcodec;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.alibaba.domain.FileResponse;

public class FileTransportEncoder implements MessageEncoder<FileResponse> {

	@Override
	public void encode(IoSession session, FileResponse message, ProtocolEncoderOutput out)
			throws Exception {
		
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
