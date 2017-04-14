package br.edu.ifam.underattack.compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.tools.SimpleJavaFileObject;

public class AppJavaFileObject extends SimpleJavaFileObject {
	
	private String source;
	
	private ByteArrayOutputStream byteCode = new ByteArrayOutputStream();

	public AppJavaFileObject(String baseName, String source) {
		super(CompilerUtils.createURI(CompilerUtils.getClassNameWithExt(baseName)),Kind.SOURCE);
		this.source = source;
	}
	
	public AppJavaFileObject(String name){
		super(CompilerUtils.createURI(name),Kind.CLASS);
	}
	
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		return source;
	}
	
	@Override
	public OutputStream openOutputStream() throws IOException {
		return byteCode;
	}
	
	public byte[] getBytes(){
		return byteCode.toByteArray();
	}
	
	

}
