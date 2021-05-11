package me.zhengjie.modules.smsServer.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密
 */
public class Encryption {

	public static byte[] md5Digest(byte[] src) throws Exception {
		MessageDigest alg = MessageDigest
				.getInstance("MD5"); // MD5 is 16 bit message digest
		return alg.digest(src);
	}


	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex;
	}

	public static String md5Digest(String src) throws Exception {
		return byteArrayToHexString(md5Digest(src.getBytes()));
	}

	/**
	 * 重载  给定编码进行转码操作
	 * @param src
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public String md5Digest(String src,String charsetName) throws Exception {
		return byteArrayToHexString(md5Digest(src.getBytes(charsetName)));
	}


	/**
    *
    * @param plainText
    *            明文
    * @return 32位密文
    */
   public static String md5Digest32(String plainText) {
       String re_md5 = new String();
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");
		   md.update(plainText.getBytes("UTF-8"));
           byte b[] = md.digest();

           int i;

           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
               i = b[offset];
               if (i < 0)
                   i += 256;
               if (i < 16)
                   buf.append("0");
               buf.append(Integer.toHexString(i));
           }

           re_md5 = buf.toString();

       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       } catch (UnsupportedEncodingException e) {
		   e.printStackTrace();
	   }
       return re_md5;
   }
}
