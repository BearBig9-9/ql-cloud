package com.ql.cloud.commons.utils.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

/**
 * @author liusn
 * @version 创建时间：2014年11月13日 下午19:12:35 类说明: 通过DES加密解密实现一个String字符串的加密和解密.
 */
public class DesEncrypt {

    public static final String key = "leying365@bjsdcqgqmndj80";

    public static DesEncrypt desObj = new DesEncrypt();

    /****************** 单例 *******************/
    private DesEncrypt() {}

    public static DesEncrypt getInstance() {
        if (null == desObj) {
            return new DesEncrypt();
        } else {
            return desObj;
        }
    }

    /****************************************/

    public byte[] _3des(int option, byte[] inkey1, byte[] inkey2, byte[] source) throws InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException {
        byte[] dest;
        if (option == 'E') {
            dest = encrypt(inkey1, source);
            dest = decrypt(inkey2, dest);
            dest = encrypt(inkey1, dest);
        } else {
            dest = decrypt(inkey1, source);
            dest = encrypt(inkey2, dest);
            dest = decrypt(inkey1, dest);
        }
        return dest;
    }

    /**
     * Des加密
     * 
     * @param str:需要加密的数据
     * @return 密文数据
     * */
    public String desEncrypt(String str) throws InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException, InvalidKeySpecException {
        byte[] encData = encrypt(key.getBytes(), str.getBytes());
        return bytesToHexString(encData);
    }

    /**
     * Des解密
     * 
     * @param str：需要解密的数据
     * @return 明文数据
     * */
    public String desDecrypt(String str) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        byte[] decData = decrypt(key.getBytes(), hexStringToBytes(str));
        return new String(decData);
    }


    /**
     * 加密方法
     * 
     * @param rawKeyData
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    private byte[] encrypt(byte rawKeyData[], byte[] source) throws InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        // 现在，获取数据并加密
        // byte data[] = str.getBytes();
        // 正式执行加密操作
        byte[] encryptedData = cipher.doFinal(source);
        return encryptedData;
    }

    /**
     * 解密方法
     * 
     * @param rawKeyData
     * @param encryptedData
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    private byte[] decrypt(byte rawKeyData[], byte[] encryptedData) throws IllegalBlockSizeException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        // 正式执行解密操作
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return decryptedData;
    }

    public String bytesToHexString(byte[] src) {
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

    /**
     * Convert hex string to byte[]
     * 
     * @param hexString the hex string
     * @return byte[]
     */
    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     * 
     * @param c char
     * @return byte
     */
    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
            InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

        DesEncrypt des = new DesEncrypt();
        String enc = des.desEncrypt("601108868");
        System.out.println(enc);
        String dec = des.desDecrypt(enc);
        System.out.println(dec);
    }
}
