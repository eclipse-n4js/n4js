/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;

/**
 * Data collectors used throughout the N4JS implementation.
 */
@SuppressWarnings("javadoc")
public final class N4JSDataCollectors {

	public static final DataCollector dcN4JSResource = create("N4JSResource");

	public static final DataCollector dcTranspiler = create("Transpiler");

	public static final DataCollector dcBuild = create("Build");
	public static final DataCollector dcAstPostprocess = create("AstPostprocess", dcBuild);
	public static final DataCollector dcValidations = create("Validations", dcBuild);
	public static final DataCollector dcValidationsPackageJson = create("Validations (package.json)", dcBuild);
	public static final DataCollector dcTranspilation = create("Transpilation", dcBuild);
	public static final DataCollector dcTranspilationStep1 = create("T1", dcTranspilation);
	public static final DataCollector dcTranspilationStep2 = create("T2", dcTranspilation);
	public static final DataCollector dcTranspilationStep3 = create("T3", dcTranspilation);

	public static final DataCollector dcLibMngr = create("Library Manager");
	public static final DataCollector dcNpmInstall = create("Install NPMs", dcLibMngr);
	public static final DataCollector dcNpmUninstall = create("Uninstall NPMs", dcLibMngr);
	public static final DataCollector dcIndexSynchronizer = create("Index Synchronizer", dcLibMngr);

	public static final DataCollector dcInstallHelper = create("External Libraries Install Helper");
	public static final DataCollector dcCollectMissingDeps = create("Collect missing dependencies", dcInstallHelper);
	public static final DataCollector dcInstallMissingDeps = create("Install missing dependencies", dcInstallHelper);

	public static final DataCollector dcExtLibBuilder = create("External Library Builder");

	public static final DataCollector dcFlowGraphs = create("Flow Graphs");
	public static final DataCollector dcCreateGraph = create("Create Graphs", dcFlowGraphs);
	public static final DataCollector dcAugmentEffectInfo = create("Augment Effect Information", dcCreateGraph);
	public static final DataCollector dcCreateNodes = create("Create Nodes", dcCreateGraph);
	public static final DataCollector dcConnectNodes = create("Connect Nodes", dcCreateGraph);
	public static final DataCollector dcJumpEdges = create("Jump Edges", dcCreateGraph);
	public static final DataCollector dcPerformAnalyses = create("Perform Analyses", dcFlowGraphs);
	public static final DataCollector dcForwardAnalyses = create("Forward", dcPerformAnalyses);
	public static final DataCollector dcBackwardAnalyses = create("Backward", dcPerformAnalyses);
	public static final DataCollector dcFlowGraphPostprocessing = create("PostProcessing", dcFlowGraphs);

	public static final DataCollector dcManifestAwareResourceValidator = create("ManifestAwareResourceValidator");

	public static final String HEADLESS_N4JS_COMPILER_COLLECTOR_NAME = "Headless N4JS Compiler";
	public static final DataCollector dcHeadless = create(HEADLESS_N4JS_COMPILER_COLLECTOR_NAME);
	public static final DataCollector dcHeadlessBuildSetComputation = create("Compute build set", dcHeadless);
	public static final DataCollector dcHeadlessProjectRegistration = create("Register project", dcHeadless);
	public static final DataCollector dcHeadlessInstallMissingDeps = create("Install missing dependencies", dcHeadless);
	public static final DataCollector dcHeadlessCompilation = create("Compilation", dcHeadless);
	public static final DataCollector dcHeadlessRunnerTester = create("Execute runner/tester", dcHeadless);

	public static DataCollector createDataCollectorForCheckMethod(String methodName) {
		final DataCollector parent;
		if (N4JSDataCollectors.dcValidationsPackageJson.hasActiveMeasurement()) {
			parent = N4JSDataCollectors.dcValidationsPackageJson;
		} else if (N4JSDataCollectors.dcValidations.hasActiveMeasurement()) {
			parent = N4JSDataCollectors.dcValidations;
		} else {
			if (!N4JSDataCollectors.dcValidations.isPaused()) {
				DataCollectors.INSTANCE.warn("check method " + methodName
						+ " invoked without data collector " + N4JSDataCollectors.dcValidations
						+ " being active");
			}
			parent = N4JSDataCollectors.dcValidations;
		}
		return DataCollectors.INSTANCE.getOrCreateDataCollector(methodName, parent);
	}

	private static DataCollector create(String key) {
		return DataCollectors.INSTANCE.getOrCreateDataCollector(key);
	}

	private static DataCollector create(String key, DataCollector parent) {
		return DataCollectors.INSTANCE.getOrCreateDataCollector(key, parent);
	}

	private N4JSDataCollectors() {
		// disallow instantiation
	}
}
