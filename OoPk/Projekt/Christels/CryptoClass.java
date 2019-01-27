import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
 

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoClass{
	static String IV = "AAAAAAAAAAAAAAAA";
	private String message = null;

	public CryptoClass(String text, String cryptoType, String key, boolean state){
		
		if(cryptoType.equals("Caesar")){
			if(state) {
				message = caesarCrypt(text, key);	
			}
			else {
				message = caesarDecrypt(text, key);
			}
		}
		else if(cryptoType.equals("AES")){
			message = AESCrypt(text, key, state);
		}
		else {
			message = text;
		}
	}

	public String getMessage() {
		return message;
	}

	public String caesarCrypt(String text, String key) {
		int keyc;
		try{
			keyc = (int) Integer.parseInt(key); 
		}
		catch (Exception e) {
			return text;
		}
		int length = text.length();
		String hexString = "";
		for(int x = 0; x<length; x++){
			hexString += String.format("%02x", (byte)(((byte) text.charAt(x))+keyc));
		}

		return hexString;
	}

	public String caesarDecrypt(String text, String key) {
		int keyc;
		try{
			keyc = (int) Integer.parseInt(key); 
		}
		catch (Exception e) {
			return text;
		}
		int length = text.length();
		byte[] byteArray = new byte[length/2];
		for (int i = 0; i < length; i += 2) {
			int temp = Integer.parseInt(text.substring(i,i+2), 16);
			byteArray[i/2] = (byte)(temp-keyc);
		}
		String testtemp = new String(byteArray);
		return testtemp;
	}
	
	public String AESCrypt(String text, String key, boolean state) {
		try{
			if(state){
				byte[] cipher = encrypt(text, key);
				String hexString = "";
				for(int x = 0; x<cipher.length; x++){
					hexString += String.format("%02x", cipher[x]);
				}
				return hexString;
			}
			else {
				byte[] byteArray = new byte[text.length()/2];
				for (int i = 0; i < text.length(); i += 2) {
					int tempbyte = Integer.parseInt(text.substring(i,i+2), 16);
					byteArray[i/2] = (byte)(tempbyte);
				}
				String decrypted;
				if (key.length() == 16 && isInteger(key, 16)) {
					decrypted = decrypt(byteArray, key);
				} else {
					decrypted = text;
				}
				return decrypted;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return text;
		} 
    } 

	public static byte[] encrypt(String text, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec keyaes = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, keyaes,new IvParameterSpec(IV.getBytes("UTF-8")));
		return cipher.doFinal(text.getBytes("UTF-8"));
	}
 
	public static String decrypt(byte[] text, String key) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE"); //Har valt CBC/PKCS5Padding för att kunna ta in saker som inte är multiplar av 16-bytes. Testa CBC/NoPadding för att få det som en ström.
		SecretKeySpec keyaes = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.DECRYPT_MODE, keyaes,new IvParameterSpec(IV.getBytes("UTF-8")));
		return new String(cipher.doFinal(text),"UTF-8");
	}

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}