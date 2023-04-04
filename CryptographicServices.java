import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class CryptographicServices {

	private static SecretKey secretKey;

	/*
	 * Method used to generate and set the secret key used to encrypt the password
	 * used to access the database
	 */
	public static void moreRandomSetKey() {

		try {
			SecureRandom secureRandom = new SecureRandom();
			byte[] key = new byte[16];
			secureRandom.nextBytes(key);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Generic method used to read a file stored in the current working directory */
	public static String readFile(String fileName) {
		String file = "./" + fileName;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String currentLine = reader.readLine();
			reader.close();
			return currentLine;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/* method used to read the key from a file OS protected */
	public static void readSecretKey(String fileName) {
		String file = "./" + fileName;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String string = reader.readLine();
			byte[] decodedKey = Base64.getDecoder().decode(string);
			reader.close();
			// rebuild key using SecretKeySpec
			secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method used to generate the key and encrypt the password used to access the
	 * database
	 */
	public static void encryptAndStorePasswordToAccessDatabase(String passwordToEncrypt) {
		// the secretKey is generated and set
		moreRandomSetKey();
		try {
			// the file in which the password used to access the database is stored
			FileWriter myWriter = new FileWriter("./passwordDB.txt");
			myWriter.write(encrypt(passwordToEncrypt));
			myWriter.close();
			// the file in which the key used to encrypt is decoded and stored. This is OS
			// protected
			FileWriter myWriter2 = new FileWriter("./secretKey.txt");
			myWriter2.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
			myWriter2.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/*
	 * Method used to access the file in which the encrypted password is stored,
	 * retrieved and decrypted
	 */
	public static String decryptAndRetrievePasswordToAccessDatabase() {
		// the password used to access the database is retrieved
		String encryptedPassword = readFile("passwordDB.txt");
		// the key used to decrypt is retrieved
		readSecretKey("secretKey.txt");
		// the password is decrypted
		String passwordDB = decrypt(encryptedPassword);
		return passwordDB;
	}

	/* Method used to encrypt data with AES/CBC mode */
	public static String encrypt(String strToEncrypt) {
		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	/* Method used to decrypt data */
	public static String decrypt(String strToDecrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

}
