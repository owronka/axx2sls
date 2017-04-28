package com.axxessio.axx2sls.login.common;


import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha384Hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class Security {

	// number of iteration for sha encryption with apacheShiro
	private static final int HASH_ITERATION = 100;

	/**
	 * @return random generated String as hexadecimal, this is called Salt
	 */
	public static String generateSalt() {
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		String salt = rng.nextBytes().toHex();
		return salt;
	}

	public static String getSHA (String value, String algorithm) throws NoSuchAlgorithmException{
		if (Helper.isEmpty(value) || Helper.isEmpty(algorithm)) return null;
		
		MessageDigest md = MessageDigest.getInstance(algorithm);

		md.update(value.toString().getBytes());
	
		return Base64.getEncoder().encodeToString(md.digest());
	 }
	
	public static String getSaltedPwdHash (String password, String salt) {
		Sha384Hash sh = new Sha384Hash(password, salt, HASH_ITERATION);
		return sh.toHex();
	}
}
