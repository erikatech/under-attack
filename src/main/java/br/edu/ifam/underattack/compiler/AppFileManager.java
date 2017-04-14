package br.edu.ifam.underattack.compiler;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class AppFileManager extends ForwardingJavaFileManager<JavaFileManager> {

	private AppClassLoader classLoader;

	private AppJavaFileObject sourceObject;

	private AppJavaFileObject compiledObject;

	public AppFileManager(JavaFileManager fileManager, AppClassLoader classLoader) {
		super(fileManager);
		this.classLoader = classLoader;
	}

	public void setObjects(AppJavaFileObject sourceObject, AppJavaFileObject compiledObject) {
		this.sourceObject = sourceObject;
		this.compiledObject = compiledObject;
		this.classLoader.setCompiledObj(compiledObject);
	}
	
	@Override
	public FileObject getFileForOutput(Location location, String packageName,
			String relativeName, FileObject sibling) throws IOException {
		return sourceObject;
	}
	
	@Override
	public JavaFileObject getJavaFileForOutput(Location location,
			String className, Kind kind,
			FileObject sibling) throws IOException {
		return compiledObject;
	}


	public ClassLoader getClassLoader(Location location) {
		return classLoader;
	}

}
