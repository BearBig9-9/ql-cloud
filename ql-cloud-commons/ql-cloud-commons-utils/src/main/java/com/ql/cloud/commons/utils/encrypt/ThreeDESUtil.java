package com.ql.cloud.commons.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author qilong
 *
 */
public class ThreeDESUtil {
    // 算法名称
    public static final String KEY_ALGORITHM = "desede";
    // 算法名称/加密模式/填充方式
    public static final String CIPHER_ALGORITHM = "desede/CBC/PKCS5Padding";

    private static byte[] key = "6C4E60E55552386C759569836DC0F83869836DC0F838C0F7".getBytes();

    private static byte[] keyiv = {1, 2, 3, 4, 5, 6, 7, 8};


    /**
     * 加密并base64
     * 
     * @param source 原文
     * @return
     */
    public static String des3EncodeWithBase64(String source) {
        byte[] str = null;
        try {
            str = des3EncodeCBC(key, keyiv, source.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return Base64.encodeBase64String(str);
    }

    public static String des3DecodeWithBase64(String base64Source) {
        String result = null;
        try {
            byte[] str = des3DecodeCBC(key, keyiv, Base64.decodeBase64(base64Source));
            result = new String(str, "UTF-8");
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

    /**
     * CBC加密
     * 
     * @param key 密钥
     * @param keyiv IV
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        /*
         * for (int k = 0; k < bOut.length; k++) { System.out.print(bOut[k] + " "); }
         * System.out.println("");
         */
        return bOut;
    }

    /**
     * 
     * 生成密钥key对象
     * 
     * @param keyStr 密钥字符串
     * @return 密钥对象
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws Exception
     */
    private static Key keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESedeKeySpec KeySpec = new DESedeKeySpec(input);
        SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    /**
     * CBC解密
     * 
     * @param key 密钥
     * @param keyiv IV
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    public static void main(String[] args) throws Exception {
        /*
         * byte[] key = "6C4E60E55552386C759569836DC0F83869836DC0F838C0F7".getBytes(); byte[] keyiv
         * = { 1, 2, 3, 4, 5, 6, 7, 8 }; byte[] data = "123456789,1448349469000".getBytes("UTF-8");
         * System.out.println("data.length=" + data.length); System.out.println("CBC加密解密"); byte[]
         * str5 = des3EncodeCBC(key, keyiv, data);
         * System.out.println(Base64.encodeBase64String(str5));
         * System.out.println(Base64.encodeBase64String(str5).length());
         * System.out.println("3D79567D0E4E42FC565C7E87AA18C789".length()); byte[] str6 =
         * des3DecodeCBC(key, keyiv, str5); System.out.println(new String(str6, "UTF-8"));
         */
        String source = "12345612345612345612345612345612";
        String str = des3EncodeWithBase64(source);
        System.out.println("加密后:" + str);
        System.out.println("解密后:" + des3DecodeWithBase64(str));
    }
}
