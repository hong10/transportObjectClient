package com.alibaba.domain;

import java.io.Serializable;

public class DeviceStatus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ADB serial number
	private String serialNumber = "";
	// device name = ro.product.manufacturer + ro.product.model
	private String deviceName = "";
	// uuid = ro.aliyun.clouduuid
	private String uuid = "";
	// version = ro.build.version.release
	private String softVersion = "";
	// adapter's ip address
	private String adapter = "";
	// IDLE - 空闲 OCCUPIED - 占用 OFFLINE - 无效状�?״̬
	private String status = "";
	// owner name
	private String owner = "";

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	public String getAdapter() {
		return adapter;
	}

	public void setAdapter(String adapter) {
		this.adapter = adapter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public DeviceStatus(String serialNumber, String deviceName, String uuid,
			String softVersion, String adapter, String status, String owner) {
		super();
		this.serialNumber = serialNumber;
		this.deviceName = deviceName;
		this.uuid = uuid;
		this.softVersion = softVersion;
		this.adapter = adapter;
		this.status = status;
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "DeviceStatus [serialNumber=" + serialNumber + ", deviceName="
				+ deviceName + ", uuid=" + uuid + ", softVersion="
				+ softVersion + ", adapter=" + adapter + ", status=" + status
				+ ", owner=" + owner + "]";
	}

}
