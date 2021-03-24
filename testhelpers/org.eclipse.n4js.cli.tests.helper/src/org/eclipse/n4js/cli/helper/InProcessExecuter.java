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
package org.eclipse.n4js.cli.helper;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscMain;
import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.cli.helper.SystemExitRedirecter.SystemExitException;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.ide.server.build.XWorkspaceManager;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.inject.Injector;

/**
 * Abstract test class to be used when testing N4JS CLI related things.
 */
public class InProcessExecuter {
	final private SystemOutRedirecter systemOutRedirecter = new SystemOutRedirecter();
	final private SystemExitRedirecter systemExitRedirecter = new SystemExitRedirecter();
	private N4jscTestFactory.State originalFactoryState = null;

	interface N4jscProcess<ArgType> {
		/** Invokes the starting method of this test class */
		abstract public void doN4jsc(ArgType arg, CliCompileResult cliResult) throws Exception;
	}

	final private boolean isEnabledBackend;
	final private boolean isMirrorSystemOut;

	InProcessExecuter(boolean isEnabledBackend, boolean isMirrorSystemOut) {
		this.isEnabledBackend = isEnabledBackend;
		this.isMirrorSystemOut = isMirrorSystemOut;
	}

	/**
	 * Calls main entry point of N4jsc with the given args. Checks that the given exit code equals the actual exit code
	 * of the invocation. Removes {@link N4jscOptions#USAGE} text if desired.
	 */
	protected CliCompileResult n4jsc(File workspaceRoot, String[] args, CliCompileResult cliResult) {
		Stopwatch sw = Stopwatch.createStarted();

		try {
			setRedirections();
			cliResult.workingDir = workspaceRoot.toString();
			N4jscMain.main(args);

			cliResult.exitCode = 0;

		} catch (SystemExitException e) {
			cliResult.exitCode = e.status;

		} catch (Exception e) {
			cliResult.exception = e;
			if (cliResult.exitCode == ProcessResult.NO_EXIT_CODE) {
				cliResult.exitCode = -1;
			}

		} finally {
			cliResult.duration = sw.stop().elapsed(TimeUnit.MILLISECONDS);
			cliResult.stdOut = systemOutRedirecter.getSystemOut();
			cliResult.errOut = systemOutRedirecter.getSystemErr();

			if (isEnabledBackend) {
				N4jscTestLanguageClient callback = (N4jscTestLanguageClient) N4jscFactory.getLanguageClient();
				cliResult.issues = callback.issues;
				cliResult.errors = callback.errors;
				cliResult.warnings = callback.warnings;
				cliResult.transpiledFiles = callback.transpiledFiles;
				cliResult.deletedFilesCount = callback.getDeletionsCount();

				// save projects
				Injector injector = N4jscFactory.getOrCreateInjector();
				XWorkspaceManager workspaceManager = injector.getInstance(XWorkspaceManager.class);
				Set<? extends ProjectConfigSnapshot> projects = workspaceManager.getProjectConfigs();
				Map<String, String> projectMap = new TreeMap<>();
				for (ProjectConfigSnapshot pConfig : projects) {
					Path projectPath = URIUtils.toPath(pConfig.getPath());
					Path relativeProjectPath = workspaceRoot.toPath().relativize(projectPath);
					projectMap.put(pConfig.getName(), relativeProjectPath.toString());
				}
				cliResult.projects = projectMap;
			}

			unsetRedirections();
		}

		return cliResult;
	}

	void setRedirections() {
		originalFactoryState = N4jscTestFactory.setAfterStoringState(isEnabledBackend, Optional.absent());
		systemOutRedirecter.set(isMirrorSystemOut);
		systemExitRedirecter.set();
	}

	void unsetRedirections() {
		systemOutRedirecter.unset();
		systemExitRedirecter.unset();
		N4jscTestFactory.unsetAndRestoreState(originalFactoryState);
	}
}
