package eid.ariel.security;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ArielSecurityApp extends Application {
	private Set<Class<?>> classes = new HashSet<Class<?>>();
	private Set<Object> singletons = new HashSet<Object>();

	public ArielSecurityApp() {
		singletons.add(new TestService());
	}

	@Override
	public Set<Class<?>> getClasses() {
		// TODO Auto-generated method stub
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		// TODO Auto-generated method stub
		return singletons;
	}
}
