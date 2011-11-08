package eid.ariel.core;


public interface IHashProvider {
	byte[] hash(byte[] input) throws CryptographyException;

	String hash(String input) throws CryptographyException;
}
