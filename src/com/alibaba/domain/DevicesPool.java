package com.alibaba.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class DevicesPool implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  ArrayList<DeviceStatus> statusPool;
	
	public ArrayList<DeviceStatus> getStatusPool() {
		return statusPool;
	}

	public DevicesPool(ArrayList<DeviceStatus> statusPool){
		this.statusPool = statusPool;
	}
}
