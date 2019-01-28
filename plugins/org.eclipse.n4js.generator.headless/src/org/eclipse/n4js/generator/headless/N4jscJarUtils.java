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

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.nio.file.FileSystems;
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
import org.eclipse.n4js.binaries.BinariesCommandFactory;
import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.utils.process.ProcessResult;

import com.google.common.base.Joiner;

/**
 * Utility methods for accessing the {@code n4jsc.jar} built in maven project {@code org.eclipse.n4js.hlc} during the
 * same overall maven build. This is required mainly for integration tests. In the future, it might be used for building
 * the runtime/bootstrap code.
 */
public class N4jscJarUtils {

	private static final Logger LOGGER = Logger.getLogger(N4jscJarUtils.class);

	/**
	 * Environment variable to pass in the location of the {@code n4jsc.jar}.
	 */
	public static final String PROVIDED_N4JSC_JAR_ENV = "PROVIDED_N4JSC_JAR";

	/**
	 * Path and file name of default n4jsc.jar to use if environment variable {@link #PROVIDED_N4JSC_JAR_ENV} is unset.
	 */
	public static final String DEFAULT_N4JSC_JAR = "target/n4jsc.jar";

	private static final long PROCESS_TIMEOUT_IN_MINUTES = 60L;

	/**
	 * Returns with the absolute file resource representing the location of the {@code n4jsc.jar}. If no location is
	 * provided by the {@value #PROVIDED_N4JSC_JAR_ENV} variable then it will fall back to the {@code /target/n4jsc.jar}
	 * location. This method never returns with a file pointing to an non-existing resource but throws a runtime
	 * exception instead.
	 *
	 * @return the runnable file of n4jsc.
	 */
	public static File getAbsoluteRunnableN4jsc() {
		final File jar;
		final String providedJar = System.getenv(PROVIDED_N4JSC_JAR_ENV);
		if (null == providedJar || "".equals(providedJar.trim()) || "null".equals(providedJar)) {
			LOGGER.info("Environment variable \"" + PROVIDED_N4JSC_JAR_ENV + "\" is unset; using default \""
					+ DEFAULT_N4JSC_JAR + "\"");
			jar = new File(DEFAULT_N4JSC_JAR).getAbsoluteFile();
		} else {
			LOGGER.info("Environment variable \"" + PROVIDED_N4JSC_JAR_ENV + "\" is set to: " + providedJar);
			jar = new File(providedJar).getAbsoluteFile();
		}
		LOGGER.info("Using n4jsc.jar at: " + jar.getAbsolutePath());
		checkState(jar.exists(), "n4jsc.jar does not exist at location: " + jar);
		return jar;
	}

	/**
	 * Calls {@link #buildHeadlessWithN4jscJar(File, List, List)} with <code>-Xmx2000m</code> and not N4JSC options.
	 */
	public static void buildHeadlessWithN4jscJar(File workspaceRoot) {
		List<String> javaOpts = Arrays.asList("-Xmx2000m");
		List<String> n4jscOpts = Collections.emptyList();
		buildHeadlessWithN4jscJar(Collections.singletonList(workspaceRoot), javaOpts, n4jscOpts);
	}

	/**
	 * Same as {@link #buildHeadlessWithN4jscJar(Collection, List, List)}, but for a single workspace root.
	 */
	public static void buildHeadlessWithN4jscJar(File workspaceRoot, List<String> javaOpts,
			List<String> n4jscOpts) {
		buildHeadlessWithN4jscJar(Collections.singletonList(workspaceRoot), javaOpts, n4jscOpts);
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
	public static void buildHeadlessWithN4jscJar(Collection<? extends File> workspaceRoots, List<String> javaOpts,
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
		cmdline.addAll(Arrays.asList(
				"-jar", getAbsoluteRunnableN4jsc().getAbsolutePath(),
				"--buildType", "allprojects"));
		cmdline.addAll(n4jscOpts);
		cmdline.add("--projectlocations");
		cmdline.addAll(workspaceRootsAbsolute);

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

	/**
	 * Build headless with N4jscli
	 */
	public static void buildHeadlessWithN4jscli(BinariesCommandFactory commandFactory, NodeJsBinary nodeJsBinary,
			File workspaceRoot,
			List<String> javaOpts,
			List<String> n4jscOpts) {
		Objects.requireNonNull(workspaceRoot);
		Objects.requireNonNull(javaOpts);
		Objects.requireNonNull(n4jscOpts);

		// Install n4js-cli from npm registry -> node_modules folder
		final File parentFolder = workspaceRoot.getParentFile();
		final ProcessResult result = commandFactory
				.createInstallPackageCommand(parentFolder, "n4js-cli@test", false)
				.execute();
		if (result.getExitCode() != 0) {
			final String msg = "Cannot install n4js-cli@test";
			LOGGER.error(msg);
			throw new IllegalStateException(msg);
		}

		// Call n4js-cli to build the workspace
		final List<String> cmdline = new ArrayList<>();
		cmdline.add(nodeJsBinary.getBinaryAbsolutePath());
		Path n4jscliAbsolutePath = FileSystems.getDefault()
				.getPath(parentFolder.getPath() + File.separatorChar + "node_modules/n4js-cli/bin/n4jsc.js")
				.normalize().toAbsolutePath();
		cmdline.add(n4jscliAbsolutePath.toString());

		cmdline.addAll(javaOpts);

		cmdline.addAll(Arrays.asList("--buildType", "allprojects"));
		cmdline.addAll(n4jscOpts);
		cmdline.add("--projectlocations");
		cmdline.add(workspaceRoot.getAbsolutePath());

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
