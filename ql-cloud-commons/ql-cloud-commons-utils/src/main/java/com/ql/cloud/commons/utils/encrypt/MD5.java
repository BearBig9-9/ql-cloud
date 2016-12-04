package com.ql.cloud.commons.utils.encrypt;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * @author liusn
 * @version 创建时间：2014年11月14日 下午2:30:13 类说明 MD5加密工具
 */
public class MD5 {
	private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static MD5 md5Obj = new MD5();

	private MD5() {

	}

	public static MD5 getInstance() {
		if (null == md5Obj) {
			return new MD5();
		} else {
			return md5Obj;
		}
	}

	private String bytesToHex(byte[] bytes) throws Exception {
		StringBuffer sb = new StringBuffer();
		int t;
		for (int i = 0; i < 16; i++) {
			t = bytes[i];
			if (t < 0)
				t += 256;
			sb.append(hexDigits[(t >>> 4)]);
			sb.append(hexDigits[(t % 16)]);
		}
		return sb.toString();
	}

	public String md5Encrypt32(String input) throws Exception {
		return md5EncryptByBit(input, 32);
	}

	public String md5EncryptByBit(String input, int bit) throws Exception {
		try {
			MessageDigest md = MessageDigest.getInstance(System.getProperty(
					"MD5.algorithm", "MD5"));
			if (bit == 16)
				return bytesToHex(md.digest(input.getBytes("utf-8")))
						.substring(8, 24);
			return bytesToHex(md.digest(input.getBytes("utf-8")));
		} catch (Exception e) {
			throw new Exception("Could not found MD5 algorithm.", e);
		}
	}

	public String md5_3(String b) throws Exception {
		MessageDigest md = MessageDigest.getInstance(System.getProperty(
				"MD5.algorithm", "MD5"));
		byte[] a = md.digest(b.getBytes());
		a = md.digest(a);
		a = md.digest(a);

		return bytesToHex(a);
	}

	
	/*********
	 *  以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 * 该代码仅供学习和研究支付宝接口使用，只是提供一个
	 * ********/
	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public  String sign(String text, String key, String input_charset) {
		text = text + key;
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param sign
	 *            签名结果
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public  boolean verify(String text, String sign, String key,
			String input_charset) {
		text = text + key;
		String mysign = DigestUtils
				.md5Hex(getContentBytes(text, input_charset));
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private  byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
					+ charset);
		}
	}
	
	/*** 
     * MD5加码 生成32位md5码 
     */  
	public String MD5Encode(String origin, String charsetname) {
      String resultString = null;
      try {
          resultString = new String(origin);
          MessageDigest md = MessageDigest.getInstance("MD5");
          if (charsetname == null || "".equals(charsetname))
              resultString = bytesToHex(md.digest(resultString
                      .getBytes()));
          else
              resultString = bytesToHex(md.digest(resultString
                      .getBytes(charsetname)));
      } catch (Exception exception) {
      }
      return resultString;
  }
}
