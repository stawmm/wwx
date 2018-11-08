package com.xgw.wwx.common.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

public class ByteUtil {

	public static String encode(String str) {
		try {
			if (str == null || str.length() == 0) {
				return "";
			}
			byte[] bytes = str.getBytes("utf-8");
			return byteToHexString(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("--encode--");
		}
	}

	public static String decode(String str) {
		try {
			if (str == null || str.length() == 0) {
				return "";
			}
			byte[] bytes = hexStringToByte(str);
			return new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("--decode--");
		}
	}

	/**
	 * 2进制转16进制字符串
	 *
	 * @param bytes
	 * @return
	 */
	public static String byteToHexString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String strHex = Integer.toHexString(bytes[i]);
			if (strHex.length() > 3) {
				sb.append(strHex.substring(6));
			} else {
				if (strHex.length() < 2) {
					sb.append("0" + strHex);
				} else {
					sb.append(strHex);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转2进制
	 *
	 * @param
	 * @return
	 */
	public static byte[] hexStringToByte(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * 字符转为byte
	 *
	 * @param c
	 * @return
	 */
	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * int to byte
	 *
	 * @param i
	 * @return
	 */
	public static byte[] intToByte(int i) {
		byte[] bt = new byte[4];
		bt[0] = (byte) (0xff & i);
		bt[1] = (byte) ((0xff00 & i) >> 8);
		bt[2] = (byte) ((0xff0000 & i) >> 16);
		bt[3] = (byte) ((0xff000000 & i) >> 24);
		return bt;
	}

	/**
	 * byte to int
	 *
	 * @param bytes
	 * @return
	 */
	public static int bytesToInt(byte[] bytes) {
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}

	public static String bytesToString(byte[] bytes) {

		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("-- bytesToString --", e);
		}
	}

	/**
	 * char转化为byte
	 *
	 * @param c
	 * @return byte[]数组
	 */
	public static byte[] charToByteArr(char c) {
		byte[] b = new byte[2];
		b[0] = (byte) ((c & 0xFF00) >> 8);
		b[1] = (byte) (c & 0xFF);
		return b;
	}

	/**
	 * byte转换为char
	 *
	 * @param b
	 * @return
	 */
	public static char byteToChar(byte[] b) {
		char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
		return c;
	}

	public static byte[] stringToByteLength(String str, int length) {
		byte[] returnByte = new byte[length];
		try {
			if (null != str && !"".equals(str)) {
				byte[] bytes = str.getBytes("UTF-8");
				for (int i = 0; i < bytes.length; i++) {
					returnByte[i] = bytes[i];
				}
				for (int i = bytes.length; i < length; i++) {
					returnByte[i] = 0;
				}
			} else {
				for (int i = 0; i < length; i++) {
					returnByte[i] = new Byte((byte) 0);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("-- stringToByteLength --", e);
		}
		return returnByte;
	}

	public static String[] byteToStringArray(byte[] bytes) {
		String[] strArray = null;
		try {
			strArray = new String[8];
			byte[] byteArray = null;
			for (int i = 0; i < bytes.length; i++) {
				if (i % 64 == 0) {
					byteArray = new byte[64];
				}
				byteArray[i % 64] = bytes[i];
				if (i % 64 == 63) {
					strArray[i / 64] = new String(byteArray, "utf-8").trim();
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("--byteToStringArray--");
		}
		return strArray;
	}

	public static byte[] currentMaskToByte(String str, int length) {
		if (StringUtils.isBlank(str) || "0x00".equals(str)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length - 1; i++) {
				sb.append("0");
			}
			return stringToByteLength(sb.toString(), length);
		}
		return stringToByteLength(str, length);
	}

}