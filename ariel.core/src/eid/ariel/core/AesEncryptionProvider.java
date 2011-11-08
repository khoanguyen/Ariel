package eid.ariel.core;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryptionProvider implements IEncryptionProvider {

	private final static int AES_KEY_SIZE = 128;
	private final static String AES_ALGORITHM = "AES";

	private byte[] key;

	public byte[] getKey() {
		return key;
	}

	public AesEncryptionProvider() throws NoSuchAlgorithmException {
		generateKey(AES_KEY_SIZE);
	}

	public AesEncryptionProvider(byte[] rawKey) {
		this.key = rawKey;
	}

	@Override
	public byte[] encrypt(byte[] input) throws CryptographyException {
		try {
			// TODO Auto-generated method stub
			SecretKeySpec kspec = new SecretKeySpec(key, AES_ALGORITHM);
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, kspec);
			return cipher.doFinal(input);
		} catch (Exception ex) {
			throw new CryptographyException(ex);
		}
	}

	@Override
	public byte[] decrypt(byte[] input) throws CryptographyException {
		try {
			// TODO Auto-generated method stub
			SecretKeySpec kspec = new SecretKeySpec(key, AES_ALGORITHM);
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, kspec);
			return cipher.doFinal(input);
		} catch (Exception ex) {
			// TODO: handle exception
			throw new CryptographyException(ex);
		}
	}

	@Override
	public String encrypt(String input) throws CryptographyException {
		// TODO Auto-generated method stub
		return ArielUtils.toBase64(encrypt(input.getBytes()));
	}

	@Override
	public String decrypt(String base64) throws CryptographyException {
		// TODO Auto-generated method stub
		try {
			return new String(decrypt(ArielUtils.toBytes(base64)));
		} catch (Exception ex) {
			// TODO: handle exception
			throw new CryptographyException(ex);
		}
	}

	private void generateKey(int keySize) throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance(AES_ALGORITHM);
		kgen.init(keySize);
		SecretKey key = kgen.generateKey();
		this.key = key.getEncoded();
	}

}
