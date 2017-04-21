package com.zekers.utils.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSACipher {

	private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	
	private PublicKey pubKey;
	private PrivateKey prvKey;
	
	public RSACipher(PublicKey pubKey, PrivateKey prvKey) {
		this.pubKey = pubKey;
		this.prvKey = prvKey;
	}
	
	/**
	 * 公钥加密
	 * 
	 * @param plaintext
	 * @return
	 */
	public byte[] encipher(byte[] plaintext) {

		byte[] ciphertext = null;
		
		try {

			Cipher c = Cipher.getInstance(CIPHER_ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, pubKey);
			ciphertext = c.doFinal(plaintext);

		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();

		} catch (BadPaddingException e) {
			e.printStackTrace();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		} catch (NoSuchPaddingException e) {
			e.printStackTrace();

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		return ciphertext;
	}

	public byte[] encipher(String plaintext) {

		return encipher(plaintext.getBytes());
	}

	/**
	 * 私钥解密
	 * 
	 * @param ciphertext
	 * @return
	 */
	public byte[] decipher(byte[] ciphertext) {

		byte[] plaintext = null;
		
		try {

			Cipher c = Cipher.getInstance(CIPHER_ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, prvKey);
			plaintext = c.doFinal(ciphertext);

		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();

		} catch (BadPaddingException e) {
			e.printStackTrace();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		} catch (NoSuchPaddingException e) {
			e.printStackTrace();

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		return plaintext;
	}
}
