package com.alibaba.domain;

import java.io.FileInputStream;
import java.io.Serializable;

public class FileResponseObject implements Serializable{
	
	private String order;
	
	private FileInputStream fis;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public FileInputStream getFis() {
		return fis;
	}

	public void setFis(FileInputStream fis) {
		this.fis = fis;
	}

	public FileResponseObject(String order, FileInputStream fis) {
		super();
		this.order = order;
		this.fis = fis;
	}
	

}
