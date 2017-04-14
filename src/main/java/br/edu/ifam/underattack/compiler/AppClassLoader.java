package br.edu.ifam.underattack.compiler;

public class AppClassLoader extends ClassLoader {

	private AppJavaFileObject compiledObj;

	public AppClassLoader(ClassLoader parentClassLoader) {
		super(parentClassLoader);
	}

	public void setCompiledObj(AppJavaFileObject compiledObj) {
		this.compiledObj = compiledObj;
	}

	public Class<?> findClass(String qualifiedClassName)
			throws ClassNotFoundException {
		byte[] bytes = compiledObj.getBytes();
		return defineClass(qualifiedClassName, bytes, 0, bytes.length);
	}

}
