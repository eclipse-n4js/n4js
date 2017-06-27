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
package org.eclipse.n4js.runner.nodejs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.n4js.external.libraries.BootstrapCodeAccess;

/**
 * Helper for accessing default bootstrap code without workspace setup. Assumes a lot of knowledge about design and
 * implementation of the IDE and concrete bootstrap code itself. It is (supposed to be) used only as workarounds for
 * contexts were we do not available workspace (Xpect output tests with resource setup) or available workspace is not
 * proper full workspace (plugin tests running without library manager).
 */
public final class NodeEngineDefaultBootstrap {

	private static String DEFAULT_RUNTIME_ACCESS_PATH = BootstrapCodeAccess.getDefaultNodeEnvPath();

	/**
	 * Value for native require, points to entry point of the nodejs bootstrap code.
	 *
	 * The value mimics a behaviour provided by the whole IDE and returns information that normally would be obtained
	 * via workspace and related utilities.
	 *
	 * <pre>
	 * Assuming runtime value is <i>/path_to_shipped_code/n4js-node/src-gen/es/n4js-node/n4js-cli.js</i> explanation of its parts is as follows:
	 *  - <i>/path_to_shipped_code</i> is runtime specific absolute path to the shipped code provided by the library manager
	 *  - <i>n4js-node</i> is the runtime bootstrap project name for this environment,
	 *        see <i>org.eclipse.n4js.runner.extension.RuntimeEnvironment.NODEJS</i>
	 *  - <i>src-gen</i> is project output path defined in the manifest of the used project (see previous segment)
	 *  - <i>es</i> is the id of used compiler (that by design is put into compiled code output),
	 *        see <i>org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator.COMPILER_ID</i>
	 *  - <i>n4js-node/n4js-cli.js</i> is the "generated/compiled" file path created based on concrete project name and FQN of the resource,
	 *        see <i>org.eclipse.n4js.generator.common.CompilerUtils.getTargetFileName(URI, String)</i>
	 * </pre>
	 */
	public static final String DEFAULT_BOOTSTRAP_ENTRY_POINT = DEFAULT_RUNTIME_ACCESS_PATH
			+ "/n4js-node/src-gen/es/n4js-node/n4js-cli.js";

	/**
	 * Paths for the bootstrap code projects (to the compiled sources).
	 *
	 * This is static information that normally is dynamically calculated from the dependency graph of the project
	 * executed. Concrete values environment "dependencies" of the projects executed with this runner. Concrete segments
	 * of each value follow the same pattern as value of {@link #DEFAULT_BOOTSTRAP_ENTRY_POINT}
	 */
	public static final Collection<String> DEFAULT_BOOTSTRAP_PATHS = Collections.unmodifiableCollection(
			Arrays.asList(BootstrapCodeAccess.getDefaultNodeEnvPath() + "/n4js-es5/src-gen/es/",
					BootstrapCodeAccess.getDefaultNodeEnvPath() + "/n4js-node/src-gen/es/"));

}
