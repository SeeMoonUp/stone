package com.javalemon.stone.common.utils.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Object {
	private MessageDigest md = null;
	private MessageDigest sha = null;
	private long jishu[] = new long[8];
	
	public MD5Object(){
		try {
			md = MessageDigest.getInstance("MD5");
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		jishu[0] = 1;
		for ( int i = 1; i < 8; i++) {
			jishu[i] = jishu[i-1]*256;
		}		
	}
	
	public String SHASum(String input) {
		if (sha == null || input == null) {
			return null;
		}
		sha.reset();
		sha.update(input.getBytes());
		byte[] sign = sha.digest();
		
		return bytes2Hex(sign);
	}
	
	public String MD5Policy(String input) {
		if ( md == null ) {
			return null;
		}
		if ( input == null) {
			return null;
		}
		
		md.reset();
		md.update(input.getBytes());
		byte[] sign = md.digest();
		
		return bytes2HexPolicy(sign);
	}	

	public String MD5Sum(String input) {
		if ( md == null ) {
			return null;
		}
		if ( input == null) {
			return null;
		}

		md.reset();
		md.update(input.getBytes());
		byte[] sign = md.digest();

		return bytes2Hex(sign);
	}

	public Long MD5To64Bit(String input) {
		if ( md == null ) {
			return null;
		}
		if ( input == null) {
			return null;
		}
		
		md.reset();
		md.update(input.getBytes());
		byte[] sign = md.digest();
		long fst = transfer64bitsTolong(sign, 0);
		long second = transfer64bitsTolong(sign, 8);
		
		return Long.valueOf(fst + second);
	}
	
	private long transfer64bitsTolong(byte[] input, int start) {
		long result = 0;

		if (start + 8 <= input.length) {
			int tmp = 0;
			for ( int i = 0; i < 7 ; i++ ) {
				tmp = Byte.valueOf(input[start + i]).intValue();
				if ( tmp < 0) {
					tmp = tmp & 0x7f + 0x80;
				}	
				result = result + tmp*jishu[i];
			}
			int last = Byte.valueOf(input[start + 7]).intValue();
			if ( last < 0 ) {
				last = last & 0x7f;
				result = last * jishu[7] + result;
				result = Long.MIN_VALUE + result;
			} else {
				result = last * jishu[7] + result; 
			}
		}
		
		return result;
	}	
	
	/**
	 * 将字节数组转换成16进制的字符串
	 * @param bts
	 * @return
	 */
	private String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;

		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	private static String bytes2HexPolicy(byte[] bts) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f' };
		try {
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = bts[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void main(String[] args) {
		MD5Object md5 = new MD5Object();
		try {
			MessageDigest md5temp = MessageDigest.getInstance("MD5");
			md5temp.reset();
			String key = "fb_acd8c28705a6dde9attach=lwtpolicy&endtime=2020-07-25&mobile=15821842880&name=星安定寿&paymoney=255.00&paytimelimit=30年&period=至60岁&policymoney=100万元&policyno=123456789&starttime=2018-07-26&status=生效";
			md5temp.update(key.getBytes());
			byte[] sign = md5temp.digest();

			System.out.println(bytes2HexPolicy(sign));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}


		System.out.println(md5.SHASum(""));
	}
}