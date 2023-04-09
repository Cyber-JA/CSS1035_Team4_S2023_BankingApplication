import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;

 class testCryptographicServices {
	private static SecretKey secretKey;

	@Test
	void testMoreRandomSetKey() {

		System.out.println("-----Testing set Random Key-----");
		try {
			SecureRandom secureRandom = new SecureRandom();
			byte[] key = new byte[16];
			secureRandom.nextBytes(key);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(secretKey);
		System.out.println("secretKey address: " + secretKey);
		System.out.println("-----End set Random Key Test-----\n");
	}

	@Test
	void testEncryptAndDecryptPasswordToAccessDatabase() {

		System.out.println("-----Testing Encrypt And Decrypt Secret Password To Database-----");
		try {
			String file = "./testFile.txt";
			// the file in which the password used to access the database is stored
			SecureRandom secureRandom = new SecureRandom();
			byte[] key = new byte[16];
			secureRandom.nextBytes(key);
			secretKey = new SecretKeySpec(key, "AES");
			FileWriter myWriter = new FileWriter(file);
			myWriter.write(encrypt("TestingPassword", secretKey));
			myWriter.close();
			// testing phase
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String string = reader.readLine();
			String decryptedKey = decrypt(string, secretKey);
			reader.close();
			// rebuild key using SecretKeySpec
			assertEquals("TestingPassword", decryptedKey);
			System.out.println("Expected: TestingPassword");
			System.out.println("Actual:" + decryptedKey);
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		System.out.println("-----End Encrypt And Decrypt Secret Password To Database Test-----\n");
	}

	@Test
	void testEncryptAndDecrypt() {

		System.out.println("-----Testing Encryption and Decryption-----");
		String toEncrypt = "Testing The Encryption and Decryption Functionality";
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[16];
		secureRandom.nextBytes(key);
		System.out.println("Message: " + toEncrypt);
		secretKey = new SecretKeySpec(key, "AES");
		String encrypted = encrypt(toEncrypt, secretKey);
		System.out.println("Message encrypted: " + encrypted);
		assertEquals(toEncrypt, decrypt(encrypted, secretKey));
		System.out.println("Message decrypted: " + decrypt(encrypted, secretKey));
		System.out.println("-----End Encryption and Decryption Test-----\n");
	}

	/* Method used to encrypt data with AES/CBC mode */
	public static String encrypt(String strToEncrypt, SecretKey key) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	/* Method used to decrypt data */
	public static String decrypt(String strToDecrypt, SecretKey key) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
}
