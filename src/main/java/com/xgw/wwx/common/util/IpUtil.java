package com.xgw.wwx.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpUtil {

	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

	public static long ipToLong2(String ipAddress) {
		String[] ipAddressInArray = ipAddress.split("\\.");
		long result = 0;
		for (int i = 0; i < ipAddressInArray.length; i++) {
			int power = 3 - i;
			int ip = Integer.parseInt(ipAddressInArray[i]);
			result += ip * Math.pow(256, power);
		}
		return result;
	}

	public static long ipToLong(String ipAddress) {
		long result = 0;
		String[] ipAddressInArray = ipAddress.split("\\.");
		for (int i = 3; i >= 0; i--) {
			long ip = Long.parseLong(ipAddressInArray[3 - i]);
			result |= ip << (i * 8);
		}
		return result;
	}

	public static String longToIp(long i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
	}

	public static String longToIp2(long ip) {
		StringBuilder sb = new StringBuilder(15);
		for (int i = 0; i < 4; i++) {
			sb.insert(0, Long.toString(ip & 0xff));
			if (i < 3) {
				sb.insert(0, '.');
			}
			ip = ip >> 8;
		}
		return sb.toString();
	}

	public static String getLocalHost() {
		try {
			String address = InetAddress.getLocalHost().getHostAddress().toString();
			return address;
		} catch (UnknownHostException e) {
			logger.error("-- IpUtil getLocalHost error --", e);
		}
		return null;

	}

	public static String getLocalHost(String eth0) {
		try {
			Enumeration<?> enumeration = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (enumeration.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) enumeration.nextElement();
				if (eth0.equalsIgnoreCase(netInterface.getName())) {
					Enumeration<?> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = (InetAddress) addresses.nextElement();
						if (ip != null && ip instanceof Inet4Address && !ip.isLoopbackAddress()) {
							return ip.getHostAddress();
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("-- 获取IP异常 --");
		}
		return null;
	}

}
