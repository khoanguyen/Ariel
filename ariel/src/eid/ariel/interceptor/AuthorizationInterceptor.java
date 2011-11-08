package eid.ariel.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import com.mongodb.MongoException;

import eid.ariel.annotation.LoginIgnored;
import eid.ariel.annotation.LoginRequired;
import eid.ariel.core.ArielConst;
import eid.ariel.core.ArielUtils;
import eid.ariel.core.CryptographyException;
import eid.ariel.exception.AccessDeniedException;
import eid.ariel.exception.AccessDeniedExceptionMapper;
import eid.ariel.schema.SecurityUser;
import eid.ariel.schema.helper.SecurityUserHelper;

@Provider
@ServerInterceptor
public class AuthorizationInterceptor implements PreProcessInterceptor {

	private class AuthHeader {
		private String arielUser, arielToken;
		private boolean valid;

		public String getArielToken() {
			return arielToken;
		}

		public String getArielUser() {
			return arielUser;
		}

		public boolean isValid() {
			return valid;
		}

		public AuthHeader(String user, String token, boolean valid) {
			arielUser = user;
			arielToken = token;
			this.valid = valid;
		}
	}

	@Override
	public ServerResponse preProcess(HttpRequest request,
			ResourceMethod resourceMethod) throws Failure,
			WebApplicationException {
		// TODO Auto-generated method stub
		ServerResponse response = null;
		try {
			response = authenticate(request, resourceMethod);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		if (response == null)
			response = authorize(request, resourceMethod);
		return response;
	}

	private ServerResponse authenticate(HttpRequest request,
			ResourceMethod resourceMethod) throws MongoException,
			CryptographyException, IOException {
		if (isLoginRequired(resourceMethod)) {
			AuthHeader authHeader = getAuthHeader(request);
			if (authHeader.isValid()) {
				Map<String, Object> user = SecurityUserHelper
						.getUserById(authHeader.getArielUser());
				if (user != null) {
					Date lastLogin = (Date) user
							.get(SecurityUser.FIELD_LASTLOGIN);
					String userId = (String) user.get(SecurityUser.FIELD_ID);
					String verifiedToken = ArielUtils.computeToken(
							lastLogin.getTime(), userId);
					String token = (String) user.get(SecurityUser.FIELD_TOKEN);
					if ((token != null) && (token.equals(verifiedToken))
							&& (token.equals(authHeader.getArielToken())))
						return null;
				}
			}
			return (ServerResponse) (new AccessDeniedExceptionMapper()
					.toResponse(new AccessDeniedException()));
		}
		return null;
	}

	private ServerResponse authorize(HttpRequest request,
			ResourceMethod resourceMethod) {
		return null;
	}

	private boolean isLoginRequired(ResourceMethod resourceMethod) {
		boolean loginRequired = isLoginRequiredForClass(resourceMethod
				.getResourceClass());
		if (loginRequired) {
			if (isLoginIgnoredForMethod(resourceMethod.getMethod())) {
				loginRequired = false;
			}
		} else {
			loginRequired = isLoginRequiredForMethod(resourceMethod.getMethod());
		}
		return loginRequired;
	}

	private boolean isLoginRequiredForClass(Class<?> target) {
		return target.getAnnotation(LoginRequired.class) != null;
	}

	private boolean isLoginRequiredForMethod(Method method) {
		return method.getAnnotation(LoginRequired.class) != null;
	}

	private boolean isLoginIgnoredForMethod(Method method) {
		return method.getAnnotation(LoginIgnored.class) != null;
	}

	private AuthHeader getAuthHeader(HttpRequest request) {
		String user = null, token = null;
		List<String> tokenHeaderValues = request.getHttpHeaders()
				.getRequestHeader(ArielConst.HEADER_TOKEN_KEY);
		List<String> userHeaderValues = request.getHttpHeaders()
				.getRequestHeader(ArielConst.HEADER_USER_ID_KEY);
		boolean isValid = (tokenHeaderValues != null)
				&& (userHeaderValues != null)
				&& (tokenHeaderValues.size() == 1)
				&& (userHeaderValues.size() == 1);
		if (isValid) {
			user = userHeaderValues.get(0);
			token = tokenHeaderValues.get(0);
		}
		return new AuthHeader(user, token, isValid);
	}

}
