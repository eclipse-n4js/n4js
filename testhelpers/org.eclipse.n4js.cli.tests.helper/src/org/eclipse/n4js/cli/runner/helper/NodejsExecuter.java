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
package org.eclipse.n4js.cli.runner.helper;

import java.io.InputStream;
import java.nio.file.Path;

import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.runner.RunConfiguration;

import com.google.inject.Injector;

/**
 * Test for checking whether plain JS files have the proper module export.
 */
public class NodejsExecuter {
	final private NodejsRunner runner;

	/** Constructor */
	public NodejsExecuter() {
		Injector injector = N4jscFactory.createInjector();
		NodeJsBinary nodeJsBinary = injector.getInstance(NodeJsBinary.class);
		runner = new NodejsRunner(nodeJsBinary);
	}

	/** Executes the given {@code runFile} in the given {@code workingDir} using nodejs */
	public NodejsResult run(Path workingDir, Path runFile) {
		NodejsResult result = new NodejsResult();

		RunConfiguration config = new RunConfiguration();
		config.setWorkingDirectory(workingDir);
		config.setFileToRun(runFile);

		try {
			Process process = runner.run(config);

			result.exitCode = process.waitFor();
			result.stdOut = getInputAsString(process.getInputStream());
			result.errOut = getInputAsString(process.getErrorStream());

		} catch (Exception e) {
			result.exception = e;
		}

		return result;
	}

	private String getInputAsString(InputStream is) {
		try (java.util.Scanner scanner = new java.util.Scanner(is)) {
			String string = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			return string.trim();
		}
	}

}
