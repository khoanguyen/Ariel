package eid.ariel.core;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class ArielUtils {
	private ArielUtils() {
	}

	public static String toBase64(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	public static byte[] toBytes(String base64) throws IOException {
		return new BASE64Decoder().decodeBuffer(base64);
	}

	public static String toHexString(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		char[] hexdigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		for (byte b : bytes) {
			int num = b & 0xff;
			builder.append(hexdigits[num >>> 4]);
			builder.append(hexdigits[num & 0x0000000F]);
		}
		return builder.toString();
	}

	public static String toHexString(String base64) throws IOException {
		byte[] bytes = toBytes(base64);
		return toHexString(bytes);
	}

	public static byte[] hexToBytes(String hexString) {
		byte[] result = new byte[hexString.length() / 2];
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			result[i] = Byte.parseByte(hexString.substring(index, index + 2),
					16);
			index += 2;
		}
		return result;
	}

	public static String hexToBase64(String hexString) {
		byte[] bytes = hexToBytes(hexString);
		return toBase64(bytes);
	}
	
	public static String computeToken(long salt, String userId)
			throws CryptographyException, IOException {
		Sha512Provider provider = new Sha512Provider();
		String input = userId + salt;
		return ArielUtils.toHexString(provider.hash(input));
	}
	
	public static String fixJsonString(String source){
		String result = source.replace("\\", "\\\\");
		result = "\"" + result.replace("\"", "\\\"") + "\"";
		return result;
	}
}
