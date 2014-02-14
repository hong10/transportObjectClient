package com.alibaba.codec;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.alibaba.domain.FileResponse;

/**
 * 文件传输服务端解码类
 * 
 */
public class FileResponseDecoder extends CumulativeProtocolDecoder {

	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		//设置一个缓存区大小为in.limit()
				ByteBuffer bf = ByteBuffer.allocate(in.limit());

				//获取in开头的自定义的指令
				int order = in.getInt();

				//设置当前读取iobuffer的位置（自定义指令已经读完，并写入了bf中，现在需要将游标移动到自定义指令的后面，所以要用减法）
				int len = in.limit() - bf.limit() ;

				//通过上面的代码已经将iobuffer中的游标移到了发送对象的第二部分（也就是FileResponse中的fileContents这个字段）
				bf = in.position(len).buf();

				FileResponse fr = new FileResponse(bf, order);
				out.write(fr);
				return false;
	}

}
