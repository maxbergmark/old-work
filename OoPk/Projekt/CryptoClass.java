import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
 

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoClass{ //Krypteringsklass
	static String IV = "AAAAAAAAAAAAAAAA"; //16 bytes lång
	private String message = null;

	public CryptoClass(String text, String cryptoType, String key, boolean state){ //Konstruktor för kryptoklassen.
		
		if(cryptoType.equals("Caesar")){
			if(state) { //Kollar om krypterat eller ej. Anropar metoder med olika argument beroende på om vi har ett redan krypterat meddelande eller ej. 
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

	public String getMessage() { //Metod som returnerar meddelandet. 
		return message;
	}

	public String caesarCrypt(String text, String key) { //Metod som skapar ett caesar-krypto.
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
			hexString += String.format("%02x", (byte)(((byte) text.charAt(x))+keyc)); //Ville ha allt som hexString men måste se till att kryptot blir rätt. 
		}

		return hexString;
	}

	public String caesarDecrypt(String text, String key) { //Dekrypteringsmetod
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
	
	public String AESCrypt(String text, String key, boolean state) { //Metod för AES-krypto.
		try{
			if(state){ //Om icke-krypterat.
				byte[] cipher = encrypt(text, key); //Skapar byte[] med encrypted bytes. 
				String hexString = "";
				for(int x = 0; x<cipher.length; x++){
					hexString += String.format("%02x", cipher[x]); //Hexomvandling
				}
				return hexString;
			}
			else {
				byte[] byteArray = new byte[text.length()/2]; //Skapar ny byte
				for (int i = 0; i < text.length(); i += 2) {
					int tempbyte = Integer.parseInt(text.substring(i,i+2), 16);
					byteArray[i/2] = (byte)(tempbyte);
				}
				String decrypted; //decrypted-metoden vill ha en sträng som input. Måste få det rätt med bytes och strängar.
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

	public static byte[] encrypt(String text, String key) throws Exception { //Ren krypteringsmetod
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec keyaes = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, keyaes,new IvParameterSpec(IV.getBytes("UTF-8")));
		return cipher.doFinal(text.getBytes("UTF-8"));
	}
 
	public static String decrypt(byte[] text, String key) throws Exception { //Ren avkrypteringsmetod
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