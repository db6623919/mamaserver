package com.mmzb.house.app.web.util;   

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * @author alun (http://alunblog.duapp.com)
 * @version 1.0
 * @created 2013-5-17
 */
public class RSA_PEM_KEY {

    private static final String ALGORITHM = "RSA";
    
//	public static final String PRIVATE_KEY_FILE = "com/netfinworks/site/wap/util/private_key.pem";
    public static final String PRIVATE_KEY_FILE = "pkcs8_rsa_private_key.pem";
	public static final String PUBLIC_KEY_FILE = "com/netfinworks/site/wap/util/public_key.pem";
 
    private static String getPublicKey(){
    	
    	 String strPublicKey = "";
		 String line = "";
		 String publicRoad = CommonUtil.getRealRootPath();
		 publicRoad =publicRoad+PUBLIC_KEY_FILE;
		 System.out.println(publicRoad);
		 try {
			  BufferedReader publicKey = new BufferedReader(new FileReader(
					  publicRoad));	
				while((line = publicKey.readLine()) != null){
					strPublicKey += line;
				}
				publicKey.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 私钥需要使用pkcs8格式的，公钥使用x509格式的
			
		String strPubKey = strPublicKey.replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "");
    	return strPubKey;
    }
    
    private static String getPrivateKey(){
 		String strPrivateKey = "";
 		 String privateRoad = CommonUtil.getRealRootPath();
 		privateRoad =privateRoad+PRIVATE_KEY_FILE;
    	try {
    		BufferedReader privateKey = new BufferedReader(new FileReader(
    				privateRoad));
    		String line = "";
    		while((line = privateKey.readLine()) != null){
    			strPrivateKey += line;
    		}
    		privateKey.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 私钥需要使用pkcs8格式的，公钥使用x509格式的
		String strPrivKey = strPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "");				
    	return strPrivKey;
    }
    /**
     * 得到公钥
     * @param algorithm
     * @param bysKey
     * @return
     */
    private static PublicKey getPublicKeyFromX509(String algorithm, String bysKey) throws NoSuchAlgorithmException, Exception {
       
        byte[] decodedKey = Base64.decodeBase64(bysKey); 
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(x509);
    }
 
    /**
     * 得到私钥
     * @param algorithm
     * @param bysKey
     * @return
     */
	private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, String bysKey) throws Exception {
    	
    	 byte[] decodedKey = Base64.decodeBase64(bysKey); 
    	PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decodedKey);
    	KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    	return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 使用公钥加密
     * @param content
     * @param key
     * @return
     */
    public static String encryptByPublic(String content) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, getPublicKey());

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);

            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);

           // String s = new String(Base64.encodeBase64(output));
            String s = bytesToHexString(output);
            return s;

        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用公钥解密
     * @param content 密文
     * @param key 公钥
     * @return 解密后的字符串
     */
    public static String decryptByPublic(String content) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, getPublicKey());
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pubkey);
         //   InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));
            InputStream ins = new ByteArrayInputStream(hexStringToBytes(content));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            byte[] buf = new byte[128];
            int bufl;
            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;
                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }
                writer.write(cipher.doFinal(block));
            }
            return new String(writer.toByteArray(), "utf-8");
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用私钥加密
     * @param content
     * @param key 私钥
     * @return
     */
    public static String encryptByPrivate(String content) {
        try {
            PrivateKey prikey = getPrivateKeyFromPKCS8(ALGORITHM, getPrivateKey());

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, prikey);

            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);

           // String s = new String(Base64.encodeBase64(output));
            String s = bytesToHexString(output);
            return s;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 使用私钥解密
     * @param content 密文
     * @param key 私钥
     * @return 解密后的字符串
     */
    public static String decryptByPrivate(String content) {
        try {
        	PrivateKey prikey = getPrivateKeyFromPKCS8(ALGORITHM, getPrivateKey());
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, prikey);
           // InputStream ins = new ByteArrayInputStream( Base64.decodeBase64(content));
            InputStream ins = new ByteArrayInputStream( hexStringToBytes(content));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            byte[] buf = new byte[128];
            int bufl;
            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;
                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }
                writer.write(cipher.doFinal(block));
            }
            return new String(writer.toByteArray(), "utf-8");
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    
    public static byte[] hexStringToBytes(String hexString) {
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
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    
    public static String bytesToHexString(byte[] src){
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
public static void main(String[] args) {
	RSA_PEM_KEY rpk =new RSA_PEM_KEY();

	String s="123456";

	//---------------------------------------------------
	String oo = rpk.encryptByPublic(s);
	System.out.println(oo);
	String oop =rpk.decryptByPrivate(oo);
	//String ooop=rpk.decryptByPublic("5587016694843463576037983163612661458370484503098095510966393513528608274532986295857173492325592940063347323383637046057110247414741883726668358537730094718208565134688972577316055369384841003735778469038710523961328619323030595452434243993906037387403818225794093142990581437616797975959495022831084508557196351576801285170194656677353102364171507853346468158776713238702988278071897662973653004397976329839135968167026851311989241136587751010879353693590332916118216610786493489852811665530414546357542103489636130127640970515372858108216341980765474473689740038816147675968028707580522597972575283156203679783594");
	System.out.println(oop);
}
}
