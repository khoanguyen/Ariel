package eid.ariel.atlas;

import eid.ariel.core.CryptographyException;
import eid.ariel.core.Sha512Provider;

public final class AtlasUtils {
	private AtlasUtils(){	
	}
	
	public static String generateShaPasshash(String salt, String passphrase) throws CryptographyException{
		Sha512Provider sha = new Sha512Provider();			
		return sha.hash(salt + passphrase);
	}
	
}
