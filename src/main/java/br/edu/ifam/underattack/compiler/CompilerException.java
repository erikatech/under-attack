package br.edu.ifam.underattack.compiler;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

public class CompilerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5423405947190842563L;
	
	private String message;

	private DiagnosticCollector<JavaFileObject> diagnostics;
	
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	public CompilerException(String message) {
		this.message = message;
	}
	
	public CompilerException(String message, DiagnosticCollector<JavaFileObject> diagnostics) {
		this.message = message;
		this.diagnostics = diagnostics;
	}
	
	public CompilerException(Exception e, DiagnosticCollector<JavaFileObject> diagnostics) {
		super(e);
		this.diagnostics = diagnostics;
	}
	
	public DiagnosticCollector<JavaFileObject> getDiagnostics() {
		return diagnostics;
	}

}
