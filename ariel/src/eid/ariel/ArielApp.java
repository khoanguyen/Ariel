package eid.ariel;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import eid.ariel.core.ArielSettings;
import eid.ariel.core.PackageReader;
import eid.ariel.exception.InvalidParamExceptionMapper;
import eid.ariel.interceptor.AuthorizationInterceptor;

public class ArielApp extends Application {
	private Set<Class<?>> classes = new HashSet<Class<?>>();
	private Set<Object> singletons = new HashSet<Object>();

	public ArielApp() {

		File f = new File(ArielSettings.EXTENSION_DIR);
		File[] jarFiles = f.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				String path = pathname.getPath();
				String ext = path.substring(path.lastIndexOf(".") + 1);
				return ext.equalsIgnoreCase("jar");
			}
		});

		for (File file : jarFiles) {
			try {
				PackageReader reader = new PackageReader(file.getPath());
				Class<?>[] serviceClasses = reader.getRoutedClass();
				for (Class<?> serviceClass : serviceClasses) {
					try {
						singletons.add(serviceClass.newInstance());
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(Class<?> providerClass : reader.getProviderClasses())
					classes.add(providerClass);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		registerInternal();
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
	
	private void registerInternal(){
		classes.add(InvalidParamExceptionMapper.class);
		classes.add(AuthorizationInterceptor.class);
		
		// Test
		singletons.add(new TestService());
	}
}
