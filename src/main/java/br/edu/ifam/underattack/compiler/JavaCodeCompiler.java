package br.edu.ifam.underattack.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaCodeCompiler<T> {

	private JavaCompiler compiler;
	private AppFileManager fileManager;
	private AppClassLoader classLoader;
	private DiagnosticCollector<JavaFileObject> diagnostics;

	public JavaCodeCompiler() throws CompilerException {
		compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null) {
			throw new CompilerException("Compilador não encontrado");
		}
		classLoader = new AppClassLoader(getClass().getClassLoader());
		diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager standardFileManager = compiler
				.getStandardFileManager(diagnostics, null, null);
		fileManager = new AppFileManager(standardFileManager, classLoader);
	}

	@SuppressWarnings("unchecked")
	public synchronized Class<T> compile(String packageName, String className,
			String javaSource) throws CompilerException {
		try {
			String qualifiedClassName = CompilerUtils.getQualifiedClassName(
					packageName, className);
			AppJavaFileObject sourceObj = new AppJavaFileObject(className,
					javaSource);
			AppJavaFileObject compiledObj = new AppJavaFileObject(
					qualifiedClassName);
			fileManager.setObjects(sourceObj, compiledObj);
			
			List<String> optionList = new ArrayList<String>();
			optionList.addAll(Arrays.asList("-cp","C:\\Users\\Erikaa\\.m2\\repository\\junit\\junit\\4.12\\junit-4.12.jar;"
					+ "C:\\Users\\Erikaa\\Documents\\suporte_v2.jar"));			
			CompilationTask task = compiler.getTask(null, fileManager,
					diagnostics, optionList, null, Arrays.asList(sourceObj));
			boolean result = task.call();
			if (!result) {
				throw new CompilerException("A compilação falhou", diagnostics);
			}

			Class<T> newClass = (Class<T>) classLoader.loadClass(qualifiedClassName);
			return newClass;
		} catch (Exception e) {
			throw new CompilerException(e, diagnostics);
		}
	}

}
