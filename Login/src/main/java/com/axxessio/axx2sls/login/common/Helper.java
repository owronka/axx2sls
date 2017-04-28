package com.axxessio.axx2sls.login.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.apache.shiro.crypto.hash.Sha384Hash;

public class Helper {

	/**
	 * Simple helper method to validate if string is empty or not.
	 * 
	 * @param s:
	 *            String to validate
	 * @return true if string is not empty
	 */
	public static boolean isEmpty(String s) {
		return (s == null | "".equals(s)) ? true : false;
	}

	public static String getSHA(String value, String algorithm) throws NoSuchAlgorithmException {
		if (isEmpty(value) || isEmpty(algorithm))
			return null;

		MessageDigest md = MessageDigest.getInstance(algorithm);

		md.update(value.toString().getBytes());

		return Base64.getEncoder().encodeToString(md.digest());
	}

	public static String getSaltedPwdHash(String password, String salt) {
		Sha384Hash sh = new Sha384Hash(password, salt, 100);
		return sh.toHex();
	}

	public static String uriEncode(CharSequence input, boolean encodeSlash) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == '_'
					|| ch == '-' || ch == '~' || ch == '.') {
				result.append(ch);
			} else if (ch == '/') {
				result.append(encodeSlash ? "%2F" : ch);
			} else {
				result.append(charToHex(ch));
			}
		}
		return result.toString();
	}

	public static String getISO8601StringFromDate(Date d) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

		return df.format(d);
	}

	public static String getStringFromDate(Date d) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		return df.format(d);
	}

	public static String byteToHex(byte b) {
		// Returns hex String representation of byte b
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return new String(array);
	}

	public static String charToHex(char c) {
		// Returns hex String representation of char c
		byte hi = (byte) (c >>> 8);
		byte lo = (byte) (c & 0xff);
		return byteToHex(hi) + byteToHex(lo);
	}

}
