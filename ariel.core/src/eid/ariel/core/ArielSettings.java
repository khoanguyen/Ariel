package eid.ariel.core;

public class ArielSettings {

	public final static String fileSeparator = System.getProperty("file.separator");
	public final static String ROOT = System.getProperty("eid.ariel.root");	
	public final static String EXTENSION_DIR = fromRoot("ext");
	
	private ArielSettings(){
		
	}
	
	private static String fromRoot(String tail) {
		if (ROOT == null) throw new RuntimeException("eid.ariel.root is missing");
		return join(ROOT, tail);
	}
	
	private static String join(String path1, String path2){
		return trimEnd(path1, fileSeparator) + fileSeparator + path2;
	}
	
	private static String trimEnd(String source, String separator){
		String result = source;
		while (result.endsWith(separator)) 
			result = result.substring(0, result.lastIndexOf(separator));
		return result;
	}
	
}
