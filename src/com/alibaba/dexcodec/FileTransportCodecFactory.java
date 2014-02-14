package com.alibaba.dexcodec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.alibaba.domain.FileResponse;

public class FileTransportCodecFactory extends DemuxingProtocolCodecFactory {
	
	private MessageEncoder<FileResponse> encoder;
	
	private MessageDecoder decoder;

	public FileTransportCodecFactory(MessageEncoder<FileResponse> encoder,
			MessageDecoder decoder) {
		super();
		this.encoder = encoder;
		this.decoder = decoder;
		addMessageDecoder(this.decoder);
		addMessageEncoder(FileResponse.class, this.encoder);
		
		
	}
	
	
	
	
}
