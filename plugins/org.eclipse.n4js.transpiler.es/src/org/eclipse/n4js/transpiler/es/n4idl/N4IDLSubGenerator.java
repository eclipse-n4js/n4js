package org.eclipse.n4js.transpiler.es.n4idl;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.generator.CompilerDescriptor;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;
import org.eclipse.xtext.generator.OutputConfiguration;

import com.google.inject.Inject;

/**
 * A sub-generator based on {@link EcmaScriptSubGenerator} that compiles N4IDL to ECMAScript.
 */
public class N4IDLSubGenerator extends EcmaScriptSubGenerator {
	private static String COMPILER_ID = "es-n4idl";
	private static CompilerDescriptor DEFAULT_DESCRIPTOR = createDescriptor();

	@Inject
	N4IDLTranspiler n4idlTranspiler;

	private static CompilerDescriptor createDescriptor() {
		final CompilerDescriptor result = new CompilerDescriptor();
		result.setIdentifier(COMPILER_ID);
		result.setName("N4IDL to ECMAScript transpiler");
		result.setDescription(
				"Transpiles N4IDL to ECMAScript, currently ES5 plus some selected ES6 features supported by V8.");
		result.setActive(true);
		result.setCompiledFileExtension("js");
		result.setCompiledFileSourceMapExtension("map");
		final OutputConfiguration outCfg = new OutputConfiguration(COMPILER_ID);
		outCfg.setDescription("N4IDL to ECMAScript transpiler");
		outCfg.setOutputDirectory(N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT);
		outCfg.setOverrideExistingResources(true);
		outCfg.setCreateOutputDirectory(true);
		outCfg.setCleanUpDerivedResources(true);
		outCfg.setSetDerivedProperty(true);
		outCfg.setKeepLocalHistory(true);
		outCfg.setCanClearOutputDirectory(true);
		result.setOutputConfiguration(outCfg);
		return result;
	}

	@Override
	public String getCompilerID() {
		return COMPILER_ID;
	}

	@Override
	public CompilerDescriptor getDefaultDescriptor() {
		return DEFAULT_DESCRIPTOR;
	}

	@Override
	protected AbstractTranspiler getTranspiler() {
		return n4idlTranspiler;
	}
}
