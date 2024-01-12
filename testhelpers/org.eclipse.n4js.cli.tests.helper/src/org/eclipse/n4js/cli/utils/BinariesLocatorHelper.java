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
package org.eclipse.n4js.cli.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.ExecutableLookupUtil;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.utils.io.OutputRedirection;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.n4js.utils.process.ProcessResult;

import com.google.inject.Inject;

/**
 * Helper used to locate various default binaries in the system. This helper looks only for specific JVM arguments,
 * environment and underlying OS configurations. Checking other sources, e.g. eclipse preference store, is up to the
 * caller.
 */
public class BinariesLocatorHelper {

	private static final Logger LOGGER = Logger.getLogger(BinariesLocatorHelper.class);

	/** debug api */
	private static final boolean LOG_SYSTEM_PROPERTIES = false;
	/** debug api */
	private static final boolean LOG_ENV_VARIABLES = false;
	/** debug api */
	private static final boolean LOG_TO_STD_OUT = false;

	@Inject
	private ProcessExecutor processExecutor;

	private Path memoizedJavaPath = null;
	private Path memoizedNodePath = null;
	private Path memoizedNpmPath = null;
	private Path memoizedYarnPath = null;
	private Path memoizedGitPath = null;

	/** Returns the path to the Java binary. */
	public Path getJavaBinary() {
		if (memoizedJavaPath == null) {
			memoizedJavaPath = findJavaPath().resolve(BinariesConstants.JAVA_BINARY_NAME);
		}
		return memoizedJavaPath;
	}

	/** Returns the path to the node binary. */
	public Path getNodeBinary() {
		if (memoizedNodePath == null) {
			memoizedNodePath = findNodePath().resolve(BinariesConstants.NODE_BINARY_NAME);
		}
		return memoizedNodePath;
	}

	/** Returns the path to the npm binary. */
	public Path getNpmBinary() {
		if (memoizedNpmPath == null) {
			memoizedNpmPath = getNodeBinary().getParent().resolve(BinariesConstants.NPM_BINARY_NAME);
		}
		return memoizedNpmPath;
	}

	/** Returns the path to the yarn binary. */
	public Path getYarnBinary() {
		if (memoizedYarnPath == null) {
			memoizedYarnPath = findYarnPath().resolve(BinariesConstants.YARN_BINARY_NAME);
		}
		return memoizedYarnPath;
	}

	/** Returns the path to the yarn binary. */
	public Path getPnpmBinary() {
		if (memoizedYarnPath == null) {
			memoizedYarnPath = findPnpmPath().resolve(BinariesConstants.YARN_BINARY_NAME);
		}
		return memoizedYarnPath;
	}

	/** Returns the path to the yarn binary. */
	public Path getGitBinary() {
		if (memoizedGitPath == null) {
			memoizedGitPath = findGitPath().resolve(BinariesConstants.GIT_BINARY_NAME);
		}
		return memoizedGitPath;
	}

	/**
	 * Performs lookup of the node binary. Uses {@link BinariesConstants} properties to perform lookup. When binary not
	 * found, will ask OS to locate node binary via {@link #lookForBinary}. If everything else fails returns (not
	 * verified) path configured by {@link BinariesConstants#BUILT_IN_DEFAULT_NODE_PATH}
	 *
	 * @return absolute path to the binary.
	 */
	private Path findNodePath() {

		logSystemProperties();
		logEnvironmentVariables();

		Path nodePathCandidate = null;

		// 1. lookup by DEFAULT_NODE_PATH_VM_ARG
		nodePathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.DEFAULT_NODE_PATH_VM_ARG));
		if (nodePathCandidate != null) {
			info("User specified default Node.js path will be used: '" + nodePathCandidate
					+ ".' based on the '" + BinariesConstants.DEFAULT_NODE_PATH_VM_ARG + "' VM argument.");
			return nodePathCandidate;
		}
		debug("Could not resolve node path from '" + BinariesConstants.DEFAULT_NODE_PATH_VM_ARG
				+ "' VM argument.");

		// 2. lookup by NODEJS_PATH_ENV
		nodePathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.NODEJS_PATH_ENV));
		if (nodePathCandidate != null) {
			info("User specified default Node.js path will be used: '" + nodePathCandidate
					+ ".' based on the '" + BinariesConstants.NODEJS_PATH_ENV + "' VM argument.");
			return nodePathCandidate;
		}
		debug("Could not resolve node path from '" + BinariesConstants.NODEJS_PATH_ENV
				+ "' VM argument.");

		// 3. lookup by PATH
		nodePathCandidate = resolveFolderContaingBinary(
				ExecutableLookupUtil.findInPath(BinariesConstants.NODE_BINARY_NAME));
		if (nodePathCandidate != null) {
			info("Obtained default Node.js path will be used: '" + nodePathCandidate
					+ ".' based on the OS PATH.");
			return nodePathCandidate;
		}
		debug("Could not resolve node path from OS PATH variable.");

		// 4. lookup by OS query
		nodePathCandidate = resolveFolderContaingBinary(
				lookForBinary(BinariesConstants.NODE_BINARY_NAME));
		if (nodePathCandidate != null) {
			info("Obtained default Node.js path will be used: '" + nodePathCandidate
					+ ".' based on the OS dynamic lookup.");
			return nodePathCandidate;

		}
		debug("Could not resolve node path from OS dynamic lookup.");

		// 5. as a last resort iff the default does not exist, try to find a node binary managed by nvm
		Path defaultNodePath = Path.of(BinariesConstants.BUILT_IN_DEFAULT_NODE_PATH);
		if (!Files.exists(defaultNodePath.resolve(BinariesConstants.NODE_BINARY_NAME))) {
			nodePathCandidate = findNodeManagedByNVM();
			if (nodePathCandidate != null) {
				info("Obtained Node.js binary managed by NVM: " + nodePathCandidate);
				return nodePathCandidate;
			}
			debug("Could not find a Node.js binary managed by NVM.");
		}

		// 6. use default, whether it is correct or not.
		info("Could not resolve node path. Falling back to default path: " + defaultNodePath);
		nodePathCandidate = defaultNodePath;

		return nodePathCandidate;
	}

	/**
	 * Like {@link #findNodePath()}, but for the yarn binary.
	 *
	 * @return string with absolute path to the binary
	 */
	private Path findYarnPath() {
		Path yarnPathCandidate = null;

		// 1. lookup by DEFAULT_YARN_PATH_VM_ARG
		yarnPathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.DEFAULT_YARN_PATH_VM_ARG));
		if (yarnPathCandidate != null) {
			info("User specified default Yarn path will be used: '" + yarnPathCandidate
					+ ".' based on the '" + BinariesConstants.DEFAULT_YARN_PATH_VM_ARG + "' VM argument.");
			return yarnPathCandidate;
		}
		debug("Could not resolve yarn path from '" + BinariesConstants.DEFAULT_YARN_PATH_VM_ARG
				+ "' VM argument.");

		// 2. lookup by YARN_PATH_ENV
		yarnPathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.YARN_PATH_ENV));
		if (yarnPathCandidate != null) {
			info("User specified default yarn path will be used: '" + yarnPathCandidate
					+ ".' based on the '" + BinariesConstants.YARN_PATH_ENV + "' environment argument.");
			return yarnPathCandidate;
		}
		debug("Could not resolve yarn path from '" + BinariesConstants.YARN_PATH_ENV);

		// 3. lookup by PATH
		yarnPathCandidate = resolveFolderContaingBinary(
				ExecutableLookupUtil.findInPath(BinariesConstants.YARN_BINARY_NAME));
		if (yarnPathCandidate != null) {
			info("Obtained yarn path will be used: '" + yarnPathCandidate
					+ ".' based on the OS PATH.");
			return yarnPathCandidate;
		}
		debug("Could not resolve yarn path from OS PATH variable.");

		// 4. lookup by OS query
		yarnPathCandidate = resolveFolderContaingBinary(
				lookForBinary(BinariesConstants.YARN_BINARY_NAME));
		if (yarnPathCandidate != null) {
			info("Obtained yarn path will be used: '" + yarnPathCandidate
					+ ".' based on the OS dynamic lookup.");
			return yarnPathCandidate;

		}
		debug("Could not resolve yarn path from OS dynamic lookup.");

		// 5. use default, whether it is correct or not.
		info("Could not resolve yarn path. Falling back to default path: " + yarnPathCandidate);
		yarnPathCandidate = new File(BinariesConstants.BUILT_IN_DEFAULT_YARN_PATH).toPath();

		return yarnPathCandidate;
	}

	/**
	 * Like {@link #findNodePath()}, but for the pnpm binary.
	 *
	 * @return string with absolute path to the binary
	 */
	private Path findPnpmPath() {
		Path pnpmPathCandidate = null;

		// 1. lookup by DEFAULT_YARN_PATH_VM_ARG
		pnpmPathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.DEFAULT_PNPM_PATH_VM_ARG));
		if (pnpmPathCandidate != null) {
			info("User specified default pnpm path will be used: '" + pnpmPathCandidate
					+ ".' based on the '" + BinariesConstants.DEFAULT_PNPM_PATH_VM_ARG + "' VM argument.");
			return pnpmPathCandidate;
		}
		debug("Could not resolve pnpm path from '" + BinariesConstants.DEFAULT_PNPM_PATH_VM_ARG
				+ "' VM argument.");

		// 2. lookup by YARN_PATH_ENV
		pnpmPathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.PNPM_PATH_ENV));
		if (pnpmPathCandidate != null) {
			info("User specified default pnpm path will be used: '" + pnpmPathCandidate
					+ ".' based on the '" + BinariesConstants.PNPM_PATH_ENV + "' environment argument.");
			return pnpmPathCandidate;
		}
		debug("Could not resolve pnpm path from '" + BinariesConstants.PNPM_PATH_ENV);

		// 3. lookup by PATH
		pnpmPathCandidate = resolveFolderContaingBinary(
				ExecutableLookupUtil.findInPath(BinariesConstants.PNPM_BINARY_NAME));
		if (pnpmPathCandidate != null) {
			info("Obtained pnpm path will be used: '" + pnpmPathCandidate
					+ ".' based on the OS PATH.");
			return pnpmPathCandidate;
		}
		debug("Could not resolve pnpm path from OS PATH variable.");

		// 4. lookup by OS query
		pnpmPathCandidate = resolveFolderContaingBinary(
				lookForBinary(BinariesConstants.PNPM_BINARY_NAME));
		if (pnpmPathCandidate != null) {
			info("Obtained pnpm path will be used: '" + pnpmPathCandidate
					+ ".' based on the OS dynamic lookup.");
			return pnpmPathCandidate;

		}
		debug("Could not resolve pnpm path from OS dynamic lookup.");

		// 5. use default, whether it is correct or not.
		info("Could not resolve pnpm path. Falling back to default path: " + pnpmPathCandidate);
		pnpmPathCandidate = new File(BinariesConstants.BUILT_IN_DEFAULT_PNPM_PATH).toPath();

		return pnpmPathCandidate;
	}

	/**
	 * Like {@link #findNodePath()}, but for the git binary.
	 *
	 * @return string with absolute path to the binary
	 */
	private Path findGitPath() {
		Path gitPathCandidate = null;

		// 1. lookup by DEFAULT_GIT_PATH_VM_ARG
		gitPathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.DEFAULT_GIT_PATH_VM_ARG));
		if (gitPathCandidate != null) {
			info("User specified default git path will be used: '" + gitPathCandidate
					+ ".' based on the '" + BinariesConstants.DEFAULT_GIT_PATH_VM_ARG + "' VM argument.");
			return gitPathCandidate;
		}
		debug("Could not resolve git path from '" + BinariesConstants.DEFAULT_GIT_PATH_VM_ARG
				+ "' VM argument.");

		// 2. lookup by GIT_PATH_ENV
		gitPathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.GIT_PATH_ENV));
		if (gitPathCandidate != null) {
			info("User specified default git path will be used: '" + gitPathCandidate
					+ ".' based on the '" + BinariesConstants.GIT_PATH_ENV + "' environment argument.");
			return gitPathCandidate;
		}
		debug("Could not resolve git path from '" + BinariesConstants.GIT_PATH_ENV);

		// 3. lookup by PATH
		gitPathCandidate = resolveFolderContaingBinary(
				ExecutableLookupUtil.findInPath(BinariesConstants.GIT_BINARY_NAME));
		if (gitPathCandidate != null) {
			info("Obtained git path will be used: '" + gitPathCandidate
					+ ".' based on the OS PATH.");
			return gitPathCandidate;
		}
		debug("Could not resolve git path from OS PATH variable.");

		// 4. lookup by OS query
		gitPathCandidate = resolveFolderContaingBinary(
				lookForBinary(BinariesConstants.GIT_BINARY_NAME));
		if (gitPathCandidate != null) {
			info("Obtained git path will be used: '" + gitPathCandidate
					+ ".' based on the OS dynamic lookup.");
			return gitPathCandidate;

		}
		debug("Could not resolve git path from OS dynamic lookup.");

		// 5. use default, whether it is correct or not.
		info("Could not resolve git path. Falling back to default path: " + gitPathCandidate);
		gitPathCandidate = new File(BinariesConstants.BUILT_IN_DEFAULT_GIT_PATH).toPath();

		return gitPathCandidate;
	}

	/**
	 * Like {@link #findNodePath()}, but for the java binary.
	 *
	 * @return string with absolute path to the binary
	 */
	private Path findJavaPath() {
		Path javaPathCandidate = null;

		// 1. lookup by DEFAULT_JAVA_PATH_VM_ARG
		javaPathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.DEFAULT_JAVA_PATH_VM_ARG));
		if (javaPathCandidate != null) {
			info("User specified default java path will be used: '" + javaPathCandidate
					+ ".' based on the '" + BinariesConstants.DEFAULT_JAVA_PATH_VM_ARG + "' VM argument.");
			return javaPathCandidate;
		}
		debug("Could not resolve java path from '" + BinariesConstants.DEFAULT_JAVA_PATH_VM_ARG
				+ "' VM argument.");

		// 2. lookup by JAVA_PATH_ENV
		javaPathCandidate = resolveFolderContaingBinary(
				tryGetEnvOrSystemVariable(BinariesConstants.JAVA_PATH_ENV));
		if (javaPathCandidate != null) {
			javaPathCandidate = javaPathCandidate.resolve("bin");
			if (javaPathCandidate.toFile().isDirectory()) {
				info("User specified default java path will be used: '" + javaPathCandidate
						+ ".' based on the '" + BinariesConstants.JAVA_PATH_ENV + "' environment argument.");
				return javaPathCandidate;
			}
		}
		debug("Could not resolve java path from '" + BinariesConstants.JAVA_PATH_ENV);

		// 3. lookup by PATH
		javaPathCandidate = resolveFolderContaingBinary(
				ExecutableLookupUtil.findInPath(BinariesConstants.JAVA_BINARY_NAME));
		if (javaPathCandidate != null) {
			info("Obtained java path will be used: '" + javaPathCandidate
					+ ".' based on the OS PATH.");
			return javaPathCandidate;
		}
		debug("Could not resolve java path from OS PATH variable.");

		// 4. lookup by OS query
		javaPathCandidate = resolveFolderContaingBinary(
				lookForBinary(BinariesConstants.JAVA_BINARY_NAME));
		if (javaPathCandidate != null) {
			info("Obtained java path will be used: '" + javaPathCandidate
					+ ".' based on the OS dynamic lookup.");
			return javaPathCandidate;

		}
		debug("Could not resolve java path from OS dynamic lookup.");

		// 5. use default, whether it is correct or not.
		info("Could not resolve java path. Falling back to default path: " + javaPathCandidate);
		javaPathCandidate = new File(BinariesConstants.BUILT_IN_DEFAULT_JAVA_PATH).toPath();

		return javaPathCandidate;
	}

	/**
	 * Returns the node binary selected via nvm's environment variable {@code NVM_BIN} or the binary of the latest node
	 * version managed by nvm.
	 */
	private Path findNodeManagedByNVM() {
		String nvmBin = System.getenv("NVM_BIN");
		Path nvmBinPath = nvmBin != null ? Path.of(nvmBin) : null;
		if (nvmBinPath != null && Files.isExecutable(nvmBinPath)) {
			return nvmBinPath.getParent();
		} else {
			String nvmDir = System.getenv("NVM_DIR");
			Path nvmDirPath = nvmDir != null ? Path.of(nvmDir) : null;
			if (nvmDirPath == null) {
				Path homePath = FileUtils.getUserHomeFolderFailSafe();
				nvmDirPath = homePath != null ? homePath.resolve(".nvm") : null;
			}
			Path nvmInstallPath = nvmDirPath != null ? nvmDirPath.resolve("versions").resolve("node") : null;
			if (nvmInstallPath != null && Files.isDirectory(nvmInstallPath)) {
				// find all installed node versions managed by nvm
				List<Path> installedVersions;
				try {
					installedVersions = Files.list(nvmInstallPath)
							.filter(p -> p.getFileName().toString().startsWith("v"))
							.collect(Collectors.toList());
				} catch (IOException e) {
					installedVersions = Collections.emptyList();
				}
				// filter down to compatible versions
				List<Path> compatibleVersions = installedVersions.stream()
						.filter(p -> N4JSGlobals.isCompatibleNodeVersion(p.getFileName().toString()))
						.collect(Collectors.toList());
				if (!compatibleVersions.isEmpty()) {
					// choose the latest compatible version
					Collections.sort(compatibleVersions, new Comparator<Path>() {
						@Override
						public int compare(Path p1, Path p2) {
							return compareNodeVersionStrings(
									p1.getFileName().toString(),
									p2.getFileName().toString());
						}
					});
					Path latestVersion = compatibleVersions.get(compatibleVersions.size() - 1);
					Path latestVersionBinary = latestVersion.resolve("bin").resolve(BinariesConstants.NODE_BINARY_NAME);
					if (Files.isExecutable(latestVersionBinary)) {
						return latestVersionBinary.getParent();
					}
				}
			}
		}
		return null;
	}

	private String lookForBinary(String binaryName) {
		ProcessResult processResult = processExecutor.createAndExecute(
				ExecutableLookupUtil.getExebutableLookupProcessBuilder(binaryName), "look for " + binaryName,
				OutputRedirection.SUPPRESS);
		// if found, result will have trailing new line, sanitize whole result
		return processResult.getStdOut().trim();
	}

	/**
	 * Tries to get the {@code propertyName} variable from the available environment variables, if it does not exist,
	 * then falls back to system properties.
	 */
	private static String tryGetEnvOrSystemVariable(String propertyName) {
		final String nodeJsPath = System.getenv(propertyName);
		if (isNullOrEmptyOrNullString(nodeJsPath)) {
			return System.getProperty(propertyName);
		}
		return nodeJsPath;
	}

	/** Tries to resolve binary folder from provided string. Returns absolute path to the folder or null. */
	private static Path resolveFolderContaingBinary(String binaryPathCandidate) {
		if (isNullOrEmptyOrNullString(binaryPathCandidate)) {
			return null;
		}
		File binaryDir = new File(binaryPathCandidate);
		if (binaryDir.isFile()) {
			binaryDir = binaryDir.getParentFile();

		} else if (!binaryDir.exists()) {
			return null;

		} else if (!binaryDir.isDirectory()) {
			return null;
		}

		return binaryDir.getAbsoluteFile().toPath();
	}

	/**
	 * Returns with {@code true} if one of the followings are {@code true}
	 * <ul>
	 * <li>Argument {@code s} is {@code null} or</li>
	 * <li>Argument {@code s} is an empty string or</li>
	 * <li>Argument {@code s} contains only whitespaces or</li>
	 * <li>Argument {@code s} equals with the <i>null</i> string.</li>
	 * </ul>
	 * Otherwise returns with {@code false}.
	 */
	private static boolean isNullOrEmptyOrNullString(final String s) {
		return null == s || 0 == s.trim().length() || "null".equals(s);
	}

	private static void logSystemProperties() {
		if (LOG_SYSTEM_PROPERTIES) {
			info("---------------------- System Properties ----------------------------------");
			final Properties p = System.getProperties();
			final Enumeration<Object> keys = p.keys();
			while (keys.hasMoreElements()) {
				final String key = (String) keys.nextElement();
				final String value = (String) p.get(key);
				info(key + ": " + value);
			}
			info("---------------------- End of System Properties ----------------------------------");
		}
	}

	private static void logEnvironmentVariables() {
		if (LOG_ENV_VARIABLES) {
			info("---------------------- Environment Variables ----------------------------------");
			for (final Entry<String, String> e : System.getenv().entrySet()) {
				info(e.getKey() + ": " + e.getValue());
			}
			info("---------------------- End of Environment Variables ----------------------------------");
		}
	}

	private static void info(final Object message) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.info(message);
		}
		if (LOG_TO_STD_OUT) {
			System.out.println(message);
		}
	}

	private static void debug(final Object message) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message);
		}
		if (LOG_TO_STD_OUT) {
			System.out.println(message);
		}
	}

	private static int compareNodeVersionStrings(String version1, String version2) {
		if (version1.startsWith("v")) {
			version1 = version1.substring(1);
		}
		if (version2.startsWith("v")) {
			version2 = version2.substring(1);
		}
		String[] segments1 = version1.split("\\.");
		String[] segments2 = version2.split("\\.");
		int l1 = segments1.length;
		int l2 = segments2.length;
		int l = Math.max(l1, l2);
		for (int i = 0; i < l; i++) {
			long num1 = i < l1 ? Long.parseUnsignedLong(segments1[i].trim()) : 0L;
			long num2 = i < l2 ? Long.parseUnsignedLong(segments2[i].trim()) : 0L;
			int cmp = Long.compare(num1, num2);
			if (cmp != 0) {
				return cmp;
			}
		}
		return 0;
	}
}
