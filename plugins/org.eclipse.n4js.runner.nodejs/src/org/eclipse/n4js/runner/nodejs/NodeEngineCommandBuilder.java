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
package org.eclipse.n4js.runner.nodejs;

import static com.google.common.base.CharMatcher.BREAKING_WHITESPACE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.runner.SystemLoaderInfo;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Builds command line command for executing the node.js engine. Clients must call command on their own. Clients must
 * configure runtime environment on their own.
 */
public class NodeEngineCommandBuilder {
	/** GH-394, with new compilation we generate different boot code. */
	private static final boolean USE_NEW_BOOTSTRAP = true;

	/** Command line option to signal COMMON_JS */
	private static final String CJS_COMMAND = "cjs";

	private final static String NODE_PATH_SEP = File.pathSeparator;

	@Inject
	private Provider<NodeJsBinary> nodeJsBinary;

	/**
	 * Creates commands for calling Node.js on command line. Data wrapped in passed parameter is used to configure node
	 * itself, and to generate file that will be executed by Node.
	 */
	public String[] createCmds(NodeRunOptions nodeRunOptions, Path workDir) throws IOException {

		final ArrayList<String> commands = new ArrayList<>();

		commands.add(nodeJsBinary.get().getBinaryAbsolutePath());

		// allow user flags
		final String nodeOptions = nodeRunOptions.getEngineOptions();
		if (nodeOptions != null) {
			for (String nodeOption : Splitter.on(BREAKING_WHITESPACE).omitEmptyStrings().split(nodeOptions)) {
				commands.add(nodeOption);
			}
		}

		final String bootScript = generateBootCode(nodeRunOptions, workDir);
		commands.add(bootScript);

		if (nodeRunOptions.getSystemLoader() == SystemLoaderInfo.COMMON_JS) {
			commands.add(CJS_COMMAND);
		}

		return commands.toArray(new String[] {});
	}

	/**
	 * Generates JS code to the this that will be configured with data for execution.
	 *
	 * @param nodeRunOptions
	 *            options used to generate boot code
	 * @return path to the script that has to be called by node
	 * @throws IOException
	 *             for IO operations
	 */
	private String generateBootCode(NodeRunOptions nodeRunOptions, Path workDir) throws IOException {

		if (USE_NEW_BOOTSTRAP) {
			// 1 generate fake node project / folder
			Path projectRootPath = workDir;
			// Path projectRootPath = Files.createTempDirectory("N4JSNodeBoot");
			// 2 generate ELF code in #1
			final Path elf = Files.createTempFile(projectRootPath, "n4jsnode", "." + N4JSGlobals.JS_FILE_EXTENSION);
			final StringBuilder elfData = getELFCode(nodeRunOptions.getInitModules(),
					nodeRunOptions.getExecModule(), nodeRunOptions.getExecutionData());
			writeContentToFile(elfData.toString(), elf.toFile());

			// 3 create 'node_modules' to the #1
			final File node_modules = new File(projectRootPath.toFile(), "node_modules");
			node_modules.mkdirs();
			// 4 generate boot script in #1
			final Path boot = Files.createTempFile(projectRootPath, "n4jsnodeRun", "." + N4JSGlobals.JS_FILE_EXTENSION);
			String[] paths = nodeRunOptions.getCoreProjectPaths().split(NODE_PATH_SEP);
			// - script has to configure symlinks to the 'node_modules' (#3)
			// - script has to call elf code
			writeContentToFile(NodeRunScriptTemplate.getRunScriptCore(node_modules.getCanonicalPath(),
					elf.getFileName().toString(), paths), boot.toFile());

			return boot.toAbsolutePath().toString();
		} else {
			// generate ELF code in temp location
			final Path elf = Files.createTempFile("n4jsnode", "." + N4JSGlobals.JS_FILE_EXTENSION);
			final StringBuilder elfData = getELFCode(nodeRunOptions.getInitModules(),
					nodeRunOptions.getExecModule(), nodeRunOptions.getExecutionData());
			writeContentToFile(elfData.toString(), elf.toFile());
			return elf.toAbsolutePath().toString();
		}
	}

	/**
	 * generates ELF code, according to N4JSDesign document, chap. 17 : Execution, section 17.2 N4JS Execution And
	 * Linking File
	 *
	 * @param list
	 *            of runtime modules to be bootstrapped
	 * @param entryPoint
	 *            of the code to be executed
	 * @param executionData
	 *            that is expected by execution module
	 * @return elf data in format for used JS engine
	 */
	private StringBuilder getELFCode(List<String> list, String entryPoint, String executionData) {
		final StringBuilder elfCode = new StringBuilder();
		elfCode.append(generateExecutionData(executionData)).append("\n");
		elfCode.append(generateNativeLoad(entryPoint)).append("\n");
		return elfCode;
	}

	/**
	 * This is contract between concrete execution environment and run/test environment.
	 */
	private String generateExecutionData(String data) {
		/*
		 * In this form execution module needs to read prop '_executionData' from global scope (also would be good idea
		 * to remove it). It would be possible that execution module exports function that takes this data as parameter
		 * but then we need to change order of things in ELF file, that is execution module has to be loaded, its export
		 * function assigned to variable and called with this data below.
		 *
		 * keep it in sync
		 */
		return "global.$executionData = " + data + ";";
	}

	/**
	 * Sets native load for execution module (e.g. entry point to the bootstrap code).
	 *
	 * @param moduleName
	 *            value for native load pointing to the bootstrap code entry point
	 * @return native code
	 */
	private String generateNativeLoad(String moduleName) {
		if (Strings.isNullOrEmpty(moduleName))
			throw new RuntimeException("Execution module not provided.");
		return "require('" + moduleName + "');";
	}

	/** Writes given content to a given file. */
	private static void writeContentToFile(String content, File file) throws IOException {
		try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
			writer.write(content);
		}
	}
}
