package eid.ariel.core;


public interface IEncryptionProvider {
	byte[] encrypt(byte[] input) throws CryptographyException;

	byte[] decrypt(byte[] input) throws CryptographyException;

	String encrypt(String input) throws CryptographyException;
	
	String decrypt(String base64) throws CryptographyException;
}
