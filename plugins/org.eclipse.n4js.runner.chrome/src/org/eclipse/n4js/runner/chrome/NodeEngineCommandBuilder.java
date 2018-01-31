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
package org.eclipse.n4js.runner.chrome;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;

/**
 * Builds command line command for executing the node.js engine. Clients must call command on their own. Clients must
 * configure runtime environment on their own.
 */
public class NodeEngineCommandBuilder {

	/** System-environment key for user NODE_OPTIONS. */
	private static final String NODE_OPTIONS = "NODE_OPTIONS";

	@Inject
	private Provider<NodeJsBinary> nodeJsBinary;

	/**
	 * Creates commands for calling Node.js on command line. Data wrapped in passed parameter is used to configure node
	 * itself, and to generate file that will be executed by Node.
	 */
	public String[] createCmds(ChromeRunnerRunOptions runConfiguration) throws IOException {

		final ArrayList<String> commands = new ArrayList<>();

		commands.add(nodeJsBinary.get().getBinaryAbsolutePath());

		// allow user flags
		final String nodeOptions = System.getProperty(NODE_OPTIONS);
		if (nodeOptions != null) {
			for (String nodeOption : nodeOptions.split(" ")) {
				commands.add(nodeOption);
			}
		}

		final StringBuilder elfData = getELFCode(runConfiguration.getInitModules(),
				runConfiguration.getExecModule(), runConfiguration.getExecutionData());

		final File elf = createTempFileFor(elfData.toString());

		commands.add(elf.getCanonicalPath());

		return commands.toArray(new String[] {});
	}

	/**
	 * generates ELF code, according to N4JSDesign document, chap. 15 : Execution, section 15.2 N4JS Execution And
	 * Linking File
	 *
	 * @param bootstrapModules
	 *            modules list to be bootstrapped
	 * @param entryPoint
	 *            of the code to be executed
	 * @param executionData
	 *            that is expected by execution module
	 * @return elf data in format for used JS engine
	 */
	private StringBuilder getELFCode(List<String> bootstrapModules, String entryPoint, String executionData) {
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

	private String generateNativeLoad(String moduleName) {
		return "require('" + moduleName + "');";
	}

	/**
	 * Writes a file to a temporary file, returning the file path.
	 *
	 * @param content
	 *            file content
	 * @return file
	 */
	private static File createTempFileFor(String content) throws IOException {
		final File temp = File.createTempFile("n4jsnode", N4JSGlobals.JS_FILE_EXTENSION);
		final BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

		try {
			writer.write(content);
		} finally {
			writer.close();
		}

		temp.deleteOnExit();

		return temp;
	}
}
