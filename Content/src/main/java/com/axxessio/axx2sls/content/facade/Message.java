package com.axxessio.axx2sls.content.facade;

public class Message {
	public static String toJSON (int code, String level, String msg) {
		return "{\"code\" : \"" + code + "\",\"level\" : \"" + level + "\",\"message\" : \"" + msg + "\"}"; 
	}
}
