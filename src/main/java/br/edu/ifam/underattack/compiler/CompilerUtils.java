package br.edu.ifam.underattack.compiler;

import java.net.URI;
import java.net.URISyntaxException;

import javax.tools.JavaFileObject.Kind;

public class CompilerUtils {

	public static URI createURI(String str) {
		try {
			return new URI(str);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getQualifiedClassName(String packageName,
			String className) {
		if (packageName == null || packageName.trim().isEmpty()) {
			return className;
		} else {
			return packageName + "." + className;
		}
	}

	public static String getClassNameWithExt(String className) {
		return className + Kind.SOURCE.extension;
	}

}
