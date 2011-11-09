package eid.ariel.schema.helper;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.Map;

import com.mongodb.MongoException;

import eid.ariel.data.MongoDbManager;
import eid.ariel.data.MongoQueryBuilder;
import eid.ariel.schema.SecurityUser;

public final class SecurityUserHelper {
	private SecurityUserHelper() {
	}

	public static Map<String, Object> getUserById(String userId)
			throws UnknownHostException, MongoException, FileNotFoundException {
		MongoQueryBuilder builder = new MongoQueryBuilder();
		builder.put(SecurityUser.FIELD_ID, userId);

		Map<String, Object> user = MongoDbManager.securityAdapter()
				.from(SecurityUser.COLLECTION).findScalar(builder.getQuery());

		return user;
	}

	public static Map<String, Object> getUserByLoginId(String userLoginId)
			throws UnknownHostException, MongoException, FileNotFoundException {
		MongoQueryBuilder builder = new MongoQueryBuilder();
		builder.put(SecurityUser.FIELD_LOGINID, userLoginId);

		Map<String, Object> user = MongoDbManager.securityAdapter()
				.from(SecurityUser.COLLECTION).findScalar(builder.getQuery());

		return user;
	}

	public static void saveUser(Map<String, Object> user)
			throws UnknownHostException, MongoException, FileNotFoundException {
		MongoDbManager.securityAdapter().from(SecurityUser.COLLECTION)
				.save(user);
	}

	public static void deleteUser(Map<String, Object> user)
			throws UnknownHostException, MongoException, FileNotFoundException {
		MongoDbManager.securityAdapter().from(SecurityUser.COLLECTION)
				.delete((String)user.get(SecurityUser.FIELD_ID));
	}
}
