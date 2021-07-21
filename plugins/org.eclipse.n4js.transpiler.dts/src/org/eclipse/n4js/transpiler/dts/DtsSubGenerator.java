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
package org.eclipse.n4js.transpiler.dts;

import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.CancelIndicatorBaseExtractor;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.generator.AbstractSubGenerator;
import org.eclipse.n4js.generator.CompilerDescriptor;
import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Sub generator for the d.ts transpiler.
 */
public class DtsSubGenerator extends AbstractSubGenerator {
	private static String COMPILER_ID = "dts";
	private static CompilerDescriptor DEFAULT_DESCRIPTOR = createDefaultDescriptor();

	@Inject
	private DtsTranspiler dtsTranspiler;

	@Inject
	private CancelIndicatorBaseExtractor ciExtractor;

	/** Creates a default CompilerDescriptor */
	public static CompilerDescriptor createDefaultDescriptor() {
		final CompilerDescriptor result = new CompilerDescriptor();
		result.setIdentifier(COMPILER_ID);
		result.setName("N4JS to d.ts transpiler");
		result.setDescription(
				"Transpiles N4JS to d.ts.");
		result.setActive(true);
		result.setCompiledFileExtension("d.ts");
		final OutputConfiguration outCfg = createDefaultOutputConfiguration();
		result.setOutputConfiguration(outCfg);
		return result;
	}

	/** Creates a default OutputConfiguration */
	public static OutputConfiguration createDefaultOutputConfiguration() {
		final OutputConfiguration outCfg = new OutputConfiguration(COMPILER_ID);
		outCfg.setDescription("N4JS to d.ts transpiler");
		outCfg.setOutputDirectory(N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT);
		outCfg.setOverrideExistingResources(true);
		outCfg.setCreateOutputDirectory(true);
		outCfg.setCleanUpDerivedResources(true);
		outCfg.setSetDerivedProperty(true);
		outCfg.setKeepLocalHistory(true);
		outCfg.setCanClearOutputDirectory(true);
		return outCfg;
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
	public boolean isActive(Resource input) {
		boolean superIsActive = super.isActive(input);
		if (!superIsActive) {
			return false;
		}

		N4JSWorkspaceConfigSnapshot ws = workspaceAccess.getWorkspaceConfig(input);
		N4JSProjectConfigSnapshot project = ws.findProjectContaining(input.getURI());
		if (project != null) {
			ProjectDescription pd = project.getProjectDescription();
			return N4JSLanguageUtils.isDtsGenerationActive(pd);
		}
		return false;
	}

	@Override
	protected void internalDoGenerate(N4JSWorkspaceConfigSnapshot ws, Resource resource, GeneratorOption[] options,
			IFileSystemAccess fsa) {
		if (!(resource instanceof N4JSResource)) {
			if (N4JSGlobals.PACKAGE_JSON.equals(resource.getURI().lastSegment())) {
				return;
			}
			throw new IllegalArgumentException("Given resource is not an N4JSResource. " + resource);
		}
		final N4JSResource resourceCasted = (N4JSResource) resource;

		if (resourceCasted.getModule().isStaticPolyfillModule()) {
			return; // do not transpile static polyfill modules (i.e. the fillers)
		}

		/*
		 * In addition to here, check for cancellation is done also on file-emit boundaries, see fsa.generateFile().
		 */
		CancelIndicator monitor = ciExtractor.extractCancelIndicator(fsa);

		// if the transpile-conditions are all met, then transpile:
		if (shouldBeCompiled(resource, monitor)) {
			final String compiledFileExtension = getCompiledFileExtension(resource);
			final String filename = getTargetFileName(resource, compiledFileExtension);

			if (filename != null) {
				final EObject root = rootElement(resource);
				if (root != null) {
					final StringWriter buffCode = new StringWriter();
					getTranspiler().transpile(resourceCasted, options, buffCode, Optional.absent());
					fsa.generateFile(filename, COMPILER_ID, buffCode.toString());
				}
			}
		}
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
		getTranspiler().transpile(resourceCasted, options, buffCode, Optional.absent());
		return buffCode.toString();
	}

	/**
	 * Returns the {@link AbstractTranspiler} to use to transpile resources.
	 */
	protected AbstractTranspiler getTranspiler() {
		return dtsTranspiler;
	}
}
