/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.CancelIndicatorBaseExtractor;
import org.eclipse.n4js.generator.common.AbstractSubGenerator;
import org.eclipse.n4js.generator.common.CompilerDescriptor;
import org.eclipse.n4js.generator.common.GeneratorOption;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.transpiler.AbstractTranspiler.SourceMapInfo;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Sub generator for the EcmaScript transpiler.
 */
public class EcmaScriptSubGenerator extends AbstractSubGenerator {
	private static String COMPILER_ID = "es";
	private static CompilerDescriptor DEFAULT_DESCRIPTOR = createDescriptor();

	@Inject
	private EcmaScriptTranspiler ecmaScriptTranspiler;

	@Inject
	private CancelIndicatorBaseExtractor ciExtractor;

	private final DataCollector collector = DataCollectors.INSTANCE.getOrCreateDataCollector("Transpiler");

	private static CompilerDescriptor createDescriptor() {
		final CompilerDescriptor result = new CompilerDescriptor();
		result.setIdentifier(COMPILER_ID);
		result.setName("N4JS to ECMAScript transpiler");
		result.setDescription(
				"Transpiles N4JS to ECMAScript, currently ES5 plus some selected ES6 features supported by V8.");
		result.setActive(true);
		result.setCompiledFileExtension("js");
		result.setCompiledFileSourceMapExtension("map");
		final OutputConfiguration outCfg = new OutputConfiguration(COMPILER_ID);
		outCfg.setDescription("N4JS to ECMAScript transpiler");
		outCfg.setOutputDirectory(calculateOutputDirectory("src-gen", COMPILER_ID));
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
	protected void internalDoGenerate(Resource resource, GeneratorOption[] options, IFileSystemAccess fsa) {
		if (!(resource instanceof N4JSResource)) {
			if (IN4JSProject.N4MF_MANIFEST.equals(resource.getURI().lastSegment())) {
				return;
			}
			throw new IllegalArgumentException("Given resource is not an N4JSResource. " + resource);
		}
		final N4JSResource resourceCasted = (N4JSResource) resource;

		if (resourceCasted.getModule().isStaticPolyfillModule()) {
			return; // do not transpile static polyfill modules (i.e. the fillers)
		}

		Measurement measurement = this.collector.getMeasurement(resource.getURI().toString());
		/*
		 * In addition to here, check for cancellation is done also on file-emit boundaries, see fsa.generateFile().
		 */
		CancelIndicator monitor = ciExtractor.extractCancelIndicator(fsa);

		// if the transpile-conditions are all met, then transpile:
		if (shouldBeCompiled(resource, monitor)) {

			final String compiledFileExtension = getCompiledFileExtension(resource);
			final String filename = getTargetFileName(resource, compiledFileExtension);
			final String sourceMapFileExtension = getCompiledFileSourceMapExtension(resource);
			final String sourceMapFileName = getTargetFileName(resource, sourceMapFileExtension);

			// used within the file-content to refer to sibling-file:
			final String simpleSourceMapFileName = new File(sourceMapFileName).toPath().getFileName().toString();
			final String simpleCompiledFileName = new File(filename).toPath().getFileName().toString();

			// the next two variables store the navigation-prefix to get to the sources
			final Path relativeNavigationToSrc = calculateNavigationFromOutputToSourcePath(fsa, getCompilerID(),
					resourceCasted);
			final Path explicitNavigationToSrc = Paths.get("/sources");
			boolean useExplicitSourceRef = true; // true use explicitNavigationToSrc | false use relativeNavigationToSrc

			boolean createSourceMap = true;

			if (filename != null) {
				final EObject root = rootElement(resource);
				if (root != null) {
					final Writer buffCode = new StringWriter();

					Optional<SourceMapInfo> optSourceMapData = Optional.absent();

					if (createSourceMap) {
						SourceMapInfo sourceMapDataInstance = ecmaScriptTranspiler.new SourceMapInfo();
						sourceMapDataInstance.sourceMapBuff = new StringWriter();

						sourceMapDataInstance.simpleSourceMapFileName = simpleSourceMapFileName;
						sourceMapDataInstance.simpleCompiledFileName = simpleCompiledFileName;

						sourceMapDataInstance.isExplicitSourceRef = useExplicitSourceRef;
						sourceMapDataInstance.explicitNavigationToSrc = explicitNavigationToSrc;

						sourceMapDataInstance.n4jsFilePath = relativeNavigationToSrc
								.resolve(resourceCasted.getURI().lastSegment()).toString();

						sourceMapDataInstance.sourceMapFileExtension = sourceMapFileExtension;

						optSourceMapData = Optional.of(sourceMapDataInstance);
					}

					ecmaScriptTranspiler.transpile(resourceCasted, options, buffCode, optSourceMapData);
					fsa.generateFile(filename, COMPILER_ID, buffCode.toString());

					if (createSourceMap) {
						fsa.generateFile(sourceMapFileName, COMPILER_ID,
								optSourceMapData.get().sourceMapBuff.toString());
					}
				}
			}
		}
		measurement.end();
	}

	// note: following method is only used for testing
	@Override
	public String getCompileResultAsText(Script root, GeneratorOption[] options) {
		final Resource resource = root.eResource();
		if (!(resource instanceof N4JSResource)) {
			throw new IllegalArgumentException("given script must be contained in an N4JSResource");
		}
		final N4JSResource resourceCasted = (N4JSResource) resource;

		final Writer buffCode = new StringWriter();
		ecmaScriptTranspiler.transpile(resourceCasted, options, buffCode, Optional.absent());
		return buffCode.toString();
	}
}
