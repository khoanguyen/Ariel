package eid.ariel.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

public class PackageReader {

	private String filePath;
	private URLClassLoader urlClassLoader;
	private Class<?>[] routedClasses;
	private Class<?>[] providerClasses;

	public String getFilePath() {
		return filePath;
	}

	public PackageReader(String filePath) throws MalformedURLException {
		this.filePath = filePath;
		urlClassLoader = new URLClassLoader(new URL[] { new File(filePath)
				.toURI().toURL() }, getClass().getClassLoader());
		scanPackage();
	}

	public Class<?>[] getRoutedClass() {
		return routedClasses;
	}

	public Class<?>[] getProviderClasses() {
		return providerClasses;
	}

	private void scanPackage() {
		List<Class<?>> routed = new ArrayList<Class<?>>();
		List<Class<?>> provider = new ArrayList<Class<?>>();

		try {
			JarInputStream stream = new JarInputStream(new FileInputStream(
					this.filePath));
			JarEntry entry = null;
			entry = stream.getNextJarEntry();

			while (entry != null) {
				try {
					Class<?> entryClass = getClassFromJarEntry(entry);
					if (entryClass != Object.class) {
						if (containsPathAnnotation(entryClass)) {
							routed.add(entryClass);
						} 
						if (containsProviderAnnotation(entryClass)){
							provider.add(entryClass);
						}
					}

				} catch (ClassNotFoundException classNotFoundException) {
					// TODO: handle exception
				}
				entry = stream.getNextJarEntry();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.routedClasses = routed.toArray(new Class<?>[0]);
		this.providerClasses = provider.toArray(new Class<?>[0]);
	}

	private Class<?> getClassFromJarEntry(JarEntry entry)
			throws ClassNotFoundException {
		String entryName = entry.getName();
		if (entryName.endsWith(".class")) {
			entryName = entryName.substring(0, entryName.lastIndexOf(".class"))
					.replace(System.getProperty("file.separator"), ".");
			return Class.forName(entryName, true, urlClassLoader);
		}
		return Object.class;
	}

	private boolean containsPathAnnotation(Class<?> target) {
		return target.getAnnotation(Path.class) != null;
	}

	private boolean containsProviderAnnotation(Class<?> target) {
		return target.getAnnotation(Provider.class) != null;
	}
}
