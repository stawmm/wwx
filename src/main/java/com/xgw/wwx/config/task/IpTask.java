package com.xgw.wwx.config.task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xgw.wwx.dto.common.IpResultDTO;
import com.xgw.wwx.dto.db.DeviceDTO;

public class IpTask implements Callable<IpResultDTO> {

	private static Logger logger = LoggerFactory.getLogger(IpTask.class);

	@Override
	public IpResultDTO call() throws Exception {
		Socket connect = null;
		Boolean flag = Boolean.FALSE;
		try {
			connect = new Socket();
			connect.connect(new InetSocketAddress(host, port), 100);
			if (connect.isConnected()) {
				flag = Boolean.TRUE;
			}
		} catch (IOException e) {
			logger.error("-- IpTask call error --");
		} finally {
			if (null != connect) {
				connect.close();
			}
		}
		IpResultDTO result = new IpResultDTO();
		result.setDeviceDTO(deviceDTO);
		result.setHost(host);
		result.setPort(port);
		result.setResult(flag);
		return result;
	}

	private String host;

	private int port;

	private DeviceDTO deviceDTO;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public DeviceDTO getDeviceDTO() {
		return deviceDTO;
	}

	public void setDeviceDTO(DeviceDTO deviceDTO) {
		this.deviceDTO = deviceDTO;
	}

	public IpTask(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public IpTask(String host, int port, DeviceDTO deviceDTO) {
		super();
		this.host = host;
		this.port = port;
		this.deviceDTO = deviceDTO;
	}

}
