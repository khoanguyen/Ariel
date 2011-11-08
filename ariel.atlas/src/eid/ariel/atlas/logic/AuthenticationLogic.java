package eid.ariel.atlas.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.MongoException;

import eid.ariel.atlas.AtlasConst;
import eid.ariel.atlas.AtlasUtils;
import eid.ariel.atlas.exception.FailedLoginException;
import eid.ariel.core.ArielConst;
import eid.ariel.core.ArielUtils;
import eid.ariel.core.CryptographyException;
import eid.ariel.schema.SecurityUser;
import eid.ariel.schema.helper.SecurityUserHelper;

public class AuthenticationLogic {

	public Map<String, Object> authenticate(Map<String, Object> params)
			throws MongoException, CryptographyException, IOException, InterruptedException {
		Thread.sleep(1000);
		String loginId = (String) params.get(AtlasConst.LOGINID_KEY);
		String passphrase = (String) params.get(AtlasConst.PASSPHRASE_KEY);

		Map<String, Object> user = SecurityUserHelper.getUserByLoginId(loginId);

		Map<String, Object> result = new HashMap<String, Object>();
		if (user != null) {
			String id = (String) user.get(SecurityUser.FIELD_ID);
			if (((Double) user.get(SecurityUser.FIELD_STATUS)) != 1) {
				throw new FailedLoginException(
						AtlasConst.ERROR_USER_LOCKED_CODE,
						AtlasConst.ERROR_USER_LOCKED_MESSAGE, id);
			} else if (comparePassphrase(passphrase, user)) {
				String token = updateUserLogin(user);
				result.put(AtlasConst.ID_KEY, id);
				result.put(AtlasConst.TOKEN_KEY, token);
				result.put(ArielConst.RESULT_KEY, 0);
			} else {
				throw new FailedLoginException(
						AtlasConst.ERROR_INVALID_PASSWORD_CODE,
						AtlasConst.ERROR_INVALID_PASSWORD_MESSAGE, id);
			}
		} else {
			throw new FailedLoginException(AtlasConst.ERROR_USER_NOTFOUND_CODE,
					AtlasConst.ERROR_USER_NOTFOUND_MESSAGE, "");
		}
		return result;
	}

	public void unauthenticate(String userId) throws UnknownHostException,
			MongoException, FileNotFoundException {
		Map<String, Object> user = SecurityUserHelper.getUserById(userId);
		user.put(SecurityUser.FIELD_TOKEN, "");
		SecurityUserHelper.saveUser(user);
	}

	private String updateUserLogin(Map<String, Object> user)
			throws MongoException, CryptographyException, IOException {
		Calendar calendar = Calendar.getInstance();
		long tick = calendar.getTimeInMillis();
		String securityToken = ArielUtils.computeToken(tick,
				(String) user.get(SecurityUser.FIELD_ID));
		user.put(SecurityUser.FIELD_LASTLOGIN, calendar.getTime());
		user.put(SecurityUser.FIELD_TOKEN, securityToken);
		SecurityUserHelper.saveUser(user);
		return securityToken;
	}

	@SuppressWarnings("unchecked")
	private boolean comparePassphrase(String userPhrase,
			Map<String, Object> user) throws CryptographyException {
		String hash = "";
		String type = (String) user.get(SecurityUser.FIELD_PASSTYPE);
		String passphrase = "";
		if (type.equals(AtlasConst.PASSTYPE_PLAIN)) {
			hash = userPhrase;
			passphrase = (String) user.get(SecurityUser.FIELD_PASSPHRASE);
		} else if (type.equals(AtlasConst.PASSTYPE_SHA)) {
			Map<String, Object> ppMap = (Map<String, Object>) user
					.get(SecurityUser.FIELD_PASSPHRASE);
			String salt = (String) ppMap
					.get(SecurityUser.FIELD_PASSPHRASE_SALT);
			passphrase = (String) ppMap.get(SecurityUser.FIELD_PASSPHRASE_HASH);
			hash = AtlasUtils.generateShaPasshash(salt, userPhrase);
		}
		return passphrase.compareTo(hash) == 0;
	}
}
