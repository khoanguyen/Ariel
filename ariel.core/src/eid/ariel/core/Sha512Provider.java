package eid.ariel.core;

import java.security.MessageDigest;

public class Sha512Provider implements IHashProvider {

	private final static String SHA_512_ALGORITHM = "SHA-512";

	@Override
	public byte[] hash(byte[] input) throws CryptographyException {
		try {
			// TODO Auto-generated method stub
			MessageDigest md = MessageDigest.getInstance(SHA_512_ALGORITHM);
			md.update(input);
			return md.digest();
		} catch (Exception ex) {
			// TODO: handle exception
			throw new CryptographyException(ex);
		}
	}

	@Override
	public String hash(String input) throws CryptographyException {
		// TODO Auto-generated method stub
		return ArielUtils.toBase64(hash(input.getBytes()));
	}

}
