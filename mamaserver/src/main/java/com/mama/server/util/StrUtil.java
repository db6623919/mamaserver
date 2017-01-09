package com.mama.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.RandomStringUtils;

public class StrUtil {

	private static int FRIEND_CODE_LENGTH = 4;
	
	public static String readUnicode(String unicode) {
		try {
			return new String(unicode.getBytes("UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static String readInputStream(InputStream in, int bufferSize) {
		byte[] buf = new byte[bufferSize];

		int count = 0;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			while ((count = in.read(buf)) > 0) {
				out.write(buf, 0, count);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return out.toString();
	}

	/**
	 * 生成随机的朋友码，格式是四位大写字母
	 * 
	 * @return 朋友码
	 */
	public static String generateFriendCode() {
		return RandomStringUtils.randomAlphabetic(FRIEND_CODE_LENGTH).toUpperCase();
	}
}
