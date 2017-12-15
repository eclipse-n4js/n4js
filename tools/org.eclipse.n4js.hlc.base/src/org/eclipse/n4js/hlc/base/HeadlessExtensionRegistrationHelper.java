/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.hlc.base;

import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.fileextensions.FileExtensionType;
import org.eclipse.n4js.fileextensions.FileExtensionsRegistry;
import org.eclipse.n4js.generator.SubGeneratorRegistry;
import org.eclipse.n4js.runner.extension.RunnerRegistry;
import org.eclipse.n4js.runner.nodejs.NodeRunner.NodeRunnerDescriptorProvider;
import org.eclipse.n4js.tester.extension.TesterRegistry;
import org.eclipse.n4js.tester.nodejs.NodeTester.NodeTesterDescriptorProvider;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;

import com.google.inject.Inject;

/**
 * This class provides helper methods for registering extensions in headless case. This should become obsolete when
 * extension points fully works for headless case.
 */
public class HeadlessExtensionRegistrationHelper {

	@Inject
	private FileExtensionsRegistry n4jsFileExtensionsRegistry;

	@Inject
	private SubGeneratorRegistry subGeneratorRegistry;

	@Inject
	private EcmaScriptSubGenerator ecmaScriptSubGenerator;

	@Inject
	private RunnerRegistry runnerRegistry;

	@Inject
	private TesterRegistry testerRegistry;

	@Inject
	private NodeRunnerDescriptorProvider nodeRunnerDescriptorProvider;

	@Inject
	private NodeTesterDescriptorProvider nodeTesterDescriptorProvider;

	/**
	 * Register extensions manually. This method should become obsolete when extension point fully works in headless
	 * case.
	 */
	public void registerExtensions() {
		// Wire registers related to the extension points
		// in non-OSGI mode extension points are not automatically populated
		if (!Platform.isRunning()) {
			runnerRegistry.register(nodeRunnerDescriptorProvider.get());
			testerRegistry.register(nodeTesterDescriptorProvider.get());
		}

		// Register file extensions
		registerTestableFiles(N4JSGlobals.N4JS_FILE_EXTENSION, N4JSGlobals.N4JSX_FILE_EXTENSION);
		registerRunnableFiles(N4JSGlobals.N4JS_FILE_EXTENSION, N4JSGlobals.JS_FILE_EXTENSION,
				N4JSGlobals.N4JSX_FILE_EXTENSION, N4JSGlobals.JSX_FILE_EXTENSION);
		registerTranspilableFiles(N4JSGlobals.N4JS_FILE_EXTENSION, N4JSGlobals.N4JSX_FILE_EXTENSION,
				N4JSGlobals.JS_FILE_EXTENSION, N4JSGlobals.JSX_FILE_EXTENSION);
		registerTypableFiles(N4JSGlobals.N4JSD_FILE_EXTENSION, N4JSGlobals.N4JS_FILE_EXTENSION,
				N4JSGlobals.N4JSX_FILE_EXTENSION, N4JSGlobals.JS_FILE_EXTENSION,
				N4JSGlobals.JSX_FILE_EXTENSION);
		registerRawFiles(N4JSGlobals.JS_FILE_EXTENSION, N4JSGlobals.JSX_FILE_EXTENSION);

		// Register ECMAScript subgenerator
		subGeneratorRegistry.register(ecmaScriptSubGenerator, N4JSGlobals.N4JS_FILE_EXTENSION);
		subGeneratorRegistry.register(ecmaScriptSubGenerator, N4JSGlobals.JS_FILE_EXTENSION);
		subGeneratorRegistry.register(ecmaScriptSubGenerator, N4JSGlobals.N4JSX_FILE_EXTENSION);
		subGeneratorRegistry.register(ecmaScriptSubGenerator, N4JSGlobals.JSX_FILE_EXTENSION);
	}

	/** Unregister all extensions */
	public void unregisterExtensions() {
		runnerRegistry.reset();
		testerRegistry.reset();
		n4jsFileExtensionsRegistry.reset();
		subGeneratorRegistry.reset();
	}

	/**
	 * Registers files to {@link FileExtensionType#TESTABLE_FILE_EXTENSION}
	 */
	private void registerTestableFiles(String... extensions) {
		for (String extension : extensions) {
			n4jsFileExtensionsRegistry.register(extension, FileExtensionType.TESTABLE_FILE_EXTENSION);
		}
	}

	/**
	 * Registers files to {@link FileExtensionType#RUNNABLE_FILE_EXTENSION}
	 */
	private void registerRunnableFiles(String... extensions) {
		for (String extension : extensions) {
			n4jsFileExtensionsRegistry.register(extension, FileExtensionType.RUNNABLE_FILE_EXTENSION);
		}
	}

	/**
	 * Registers files to {@link FileExtensionType#TRANSPILABLE_FILE_EXTENSION}
	 */
	private void registerTranspilableFiles(String... extensions) {
		for (String extension : extensions) {
			n4jsFileExtensionsRegistry.register(extension, FileExtensionType.TRANSPILABLE_FILE_EXTENSION);
		}
	}

	/**
	 * Registers files to {@link FileExtensionType#TYPABLE_FILE_EXTENSION}
	 */
	private void registerTypableFiles(String... extensions) {
		for (String extension : extensions) {
			n4jsFileExtensionsRegistry.register(extension, FileExtensionType.TYPABLE_FILE_EXTENSION);
		}
	}

	/**
	 * Registers files to {@link FileExtensionType#RAW_FILE_EXTENSION}
	 */
	private void registerRawFiles(String... extensions) {
		for (String extension : extensions) {
			n4jsFileExtensionsRegistry.register(extension, FileExtensionType.TESTABLE_FILE_EXTENSION);
		}
	}

}
