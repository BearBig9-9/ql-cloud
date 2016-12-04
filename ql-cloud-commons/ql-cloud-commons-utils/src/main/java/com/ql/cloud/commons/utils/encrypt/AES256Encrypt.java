/**
 * AesEncrypt.java 2016年6月30日 下午2:57:24
 *
 * Copyright (c) 2010-2061 leying.com, Inc. All rights reserved.
 *
 * @Description TODO
 * @version 3.0
 *
 */


package com.ql.cloud.commons.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256Encrypt {

  /**
   * 密钥算法 java6支持56位密钥，bouncycastle支持64位
   * */
  public static final String KEY_ALGORITHM = "leying365@AES256USERCARDjyl888";

  /**
   * 加密/解密算法/工作模式/填充方式
   * 
   * JAVA6 支持PKCS5PADDING填充方式 Bouncy castle支持PKCS7Padding填充方式 AES/ECB/PKCS7Padding
   * */
  public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
  
  private final static String HEX = "0123456789ABCDEF";
  
  /**
   * 
   * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
   * 
   * @return byte[] 二进制密钥
   * */
  public static byte[] initkey() throws Exception {

    // //实例化密钥生成器
    // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    // KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
    // //初始化密钥生成器，AES要求密钥长度为128位、192位、256位
    // // kg.init(256);
    // kg.init(128);
    // //生成密钥
    // SecretKey secretKey=kg.generateKey();
    // //获取二进制密钥编码形式
    // return secretKey.getEncoded();
    // 为了便于测试，这里我把key写死了，如果大家需要自动生成，可用上面注释掉的代码
    return new byte[] {0x08, 0x08, 0x04, 0x0b, 0x02, 0x0f, 0x0b, 0x0c, 0x01, 0x03, 0x09, 0x07,
        0x0c, 0x03, 0x07, 0x0a, 0x04, 0x0f, 0x06, 0x0f, 0x0e, 0x09, 0x05, 0x01, 0x0a, 0x0a, 0x01,
        0x09, 0x06, 0x07, 0x09, 0x0d};
  }

  /**
   * 转换密钥
   * 
   * @param key 二进制密钥
   * @return Key 密钥
   * */
  public static Key toKey(byte[] key) throws Exception {
    // 实例化DES密钥
    // 生成密钥
    SecretKey secretKey = new SecretKeySpec(key, "AES");
    return secretKey;
  }

  /**
   * 加密数据
   * 
   * @param data 待加密数据
   * @param key 密钥
   * @return byte[] 加密后的数据
   * */
  public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
    // 还原密钥
    Key k = toKey(key);
    /**
     * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
     * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
     */
    // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

    IvParameterSpec ips = new IvParameterSpec(key);

    // 初始化，设置为加密模式
    cipher.init(Cipher.ENCRYPT_MODE, k, ips);
    // 执行操作
    return cipher.doFinal(data);
  }

  /**
   * 解密数据
   * 
   * @param data 待解密数据
   * @param key 密钥
   * @return byte[] 解密后的数据
   * */
  public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
    // 欢迎密钥
    Key k = toKey(key);
    /**
     * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
     * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
     */
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

    IvParameterSpec ips = new IvParameterSpec(key);

    // 初始化，设置为解密模式
    cipher.init(Cipher.DECRYPT_MODE, k, ips);
    // 执行操作
    return cipher.doFinal(data);
  }

  public static String toHex(byte[] buf) {
    if (buf == null)
      return "";
    StringBuffer result = new StringBuffer(2 * buf.length);
    for (int i = 0; i < buf.length; i++) {
      appendHex(result, buf[i]);
    }
    return result.toString();
  }

  private static void appendHex(StringBuffer sb, byte b) {
    sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
  }

  public static byte[] toByte(String hexString) {
    if (hexString == null || "".equals(hexString)) {
      return null;
    }
    int len = hexString.length() / 2;
    byte[] result = new byte[len];
    for (int i = 0; i < len; i++) {
      result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
    }
    return result;
  }

  /**
   * @param args
   * @throws UnsupportedEncodingException
   * @throws Exception
   */
  public static void main(String[] args) throws UnsupportedEncodingException {

    String str = "987654";
    System.out.println("原文：" + str);
    String keyStr = KEY_ALGORITHM.substring(0, 16);
    System.out.println("密码截取或者补0：" + keyStr + " length = " + keyStr.length());
    // 初始化密钥
    byte[] key;
    try {
      key = keyStr.getBytes("utf-8");
      /*
       * System.out.print("密钥："); for(int i = 0;i<key.length;i++){ System.out.printf("%x", key[i]);
       * } System.out.print("\n");
       */
      // 加密数据
      byte[] data = encrypt(str.getBytes(), key);
      System.out.print("加密后：" + toHex(data));
      String encryData = toHex(data);
      System.out.print("\n");

      // 解密数据
      data = AES256Encrypt.decrypt(toByte(encryData), key);
      System.out.println("解密后：" + new String(data));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
