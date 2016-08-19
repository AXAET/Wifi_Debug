package com.axaet.wifi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conversion {

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static byte[] stringToAscii(String string) {
		char[] cs = string.toCharArray();
		byte[] bs = new byte[cs.length];
		for (int i = 0; i < cs.length; i++) {
			bs[i] = (byte) cs[i];
		}
		return bs;
	}

	public static byte[] wifiToByte(String wifiName, String wifiPass) {
		byte[] passBy = Conversion.stringToAscii(wifiPass);
		byte[] nameBy = Conversion.stringToAscii(wifiName);
		byte[] bs = new byte[passBy.length + nameBy.length + 3];
		bs[0] = 0x23;
		bs[1] = (byte) nameBy.length;
		System.arraycopy(nameBy, 0, bs, 2, nameBy.length);
		bs[nameBy.length + 2] = (byte) passBy.length;
		System.arraycopy(passBy, 0, bs, nameBy.length + 3, passBy.length);
		return bs;
	}

	public static boolean isboolIp(String ipAddress) {
		String ip = "([1-9]|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])(//.(//d|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])){3}";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}
}
