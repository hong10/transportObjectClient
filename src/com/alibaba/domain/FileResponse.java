package com.alibaba.domain;

import java.nio.ByteBuffer;

/**
 * 服务端响应类
 */
public class FileResponse {
	//传输的文件内容
    private ByteBuffer fileContents;
    //指令
    
    private int order;
    public FileResponse(ByteBuffer fileContents, int order) {
        this.fileContents = fileContents;
        this.order = order;
    }

    public ByteBuffer getFileContents() {
		return fileContents;
	}

	public void setFileContents(ByteBuffer fileContents) {
		this.fileContents = fileContents;
	}
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

	
	
	
}
