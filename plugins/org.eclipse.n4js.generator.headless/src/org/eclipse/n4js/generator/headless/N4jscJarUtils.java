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
package org.eclipse.n4js.generator.headless;

import static org.apache.log4j.Logger.getLogger;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;

/**
 * Utility methods for accessing the {@code n4jsc.jar} built in maven project {@code org.eclipse.n4js.hlc} during the
 * same overall maven build. This is required mainly for integration tests. In the future, it might be used for building
 * the runtime/bootstrap code.
 */
public class N4jscJarUtils {

	private static final Logger LOGGER = getLogger(N4jscJarUtils.class);

	private static final long PROCESS_TIMEOUT_IN_MINUTES = 60L;

	/**
	 * Calls {@link #buildHeadlessWithN4jscJar(Path, File, List, List)} with <code>-Xmx2000m</code> and not N4JSC
	 * options.
	 */
	public static void buildHeadlessWithN4jscJar(Path pathToN4JSCJar, File workspaceRoot) {
		List<String> javaOpts = Arrays.asList("-Xmx2000m");
		List<String> n4jscOpts = Collections.emptyList();
		buildHeadlessWithN4jscJar(pathToN4JSCJar, Collections.singletonList(workspaceRoot), javaOpts, n4jscOpts);
	}

	/**
	 * Same as {@link #buildHeadlessWithN4jscJar(Path, Collection, List, List)}, but for a single workspace root.
	 */
	public static void buildHeadlessWithN4jscJar(Path pathToN4JSCJar, File workspaceRoot, List<String> javaOpts,
			List<String> n4jscOpts) {
		buildHeadlessWithN4jscJar(pathToN4JSCJar, Collections.singletonList(workspaceRoot), javaOpts, n4jscOpts);
	}

	/**
	 * Builds the given workspace roots with the external n4jsc.jar.
	 *
	 * @param workspaceRoots
	 *            one or more workspace roots to build.
	 * @param javaOpts
	 *            zero or more additional command line options that will be sent to the JVM
	 * @param n4jscOpts
	 *            zero or more additional command line options that will be sent to the {@code n4jsc.jar}.
	 */
	public static void buildHeadlessWithN4jscJar(Path pathToN4JSCJar, Collection<? extends File> workspaceRoots,
			List<String> javaOpts,
			List<String> n4jscOpts) {
		Objects.requireNonNull(workspaceRoots);
		Objects.requireNonNull(javaOpts);
		Objects.requireNonNull(n4jscOpts);
		if (workspaceRoots.isEmpty())
			throw new IllegalArgumentException("at least one workspace root must be given");

		final List<String> workspaceRootsAbsolute = workspaceRoots.stream()
				.map(f -> f.getAbsolutePath())
				.collect(Collectors.toList());

		final List<String> cmdline = new ArrayList<>();
		cmdline.add("java");
		cmdline.addAll(javaOpts);
		List<String> debugOpts = new ArrayList<>();
		debugOpts.add("-Xdebug");
		debugOpts.add("-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=1044");

		cmdline.addAll(debugOpts);
		cmdline.addAll(Arrays.asList(
				"-jar", pathToN4JSCJar.toAbsolutePath().toString(),
				// "--debug", "-v", // generate more output
				"--buildType", "allprojects"));
		cmdline.addAll(n4jscOpts);
		cmdline.add("--projectlocations");
		cmdline.addAll(workspaceRootsAbsolute);

		String cmdString = Joiner.on(" ").join(cmdline);

		ProcessBuilder pb = new ProcessBuilder(cmdline);
		pb.directory(null); // set to home of current process, which should be the module (mvn: ${project.basedir})
		pb.inheritIO();

		LOGGER.info("current directory is: " + new File("").getAbsolutePath());
		LOGGER.info("spawning process with command: " + Joiner.on(" ").join(cmdline));

		boolean timeout = false;
		int exitCode = 0;
		boolean gotExitCode = false;
		Exception ex = null;

		System.out.println("--- start of output of external process");
		try {
			Process ps = pb.start();
			timeout = !ps.waitFor(PROCESS_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES);
			if (!timeout) {
				exitCode = ps.exitValue();
				gotExitCode = true;
			}
		} catch (Exception e) {
			ex = e;
		}
		System.out.println("--- end of output of external process");

		LOGGER.info("external process done (exit code: " + (gotExitCode ? exitCode : "<none>") + ")");
		if (ex != null) {
			final String msg = "exception while running external process";
			LOGGER.error(msg, ex);
			ex.printStackTrace();
			throw new IllegalStateException(msg, ex);
		} else if (timeout) {
			final String msg = "external process timed out (after " + PROCESS_TIMEOUT_IN_MINUTES + " minutes)";
			LOGGER.error(msg);
			throw new IllegalStateException(msg);
		} else if (exitCode != 0) {
			final String msg = "external process returned non-zero exit code: " + exitCode;
			LOGGER.error(msg);
			throw new IllegalStateException(msg);
		}
	}
}
