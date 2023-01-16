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
package org.eclipse.n4js.smith;

import java.util.function.Supplier;

/**
 * Data collectors used throughout the N4JS implementation.
 */
@SuppressWarnings("javadoc")
public final class N4JSDataCollectors {

	public static final DataCollector dcN4JSResource = create("N4JSResource");

	public static final DataCollector dcBuild = create("Build");

	public static final DataCollector dcParser = create("Parser", dcBuild);

	public static final DataCollector dcDtsParser = create("DTS-Parser", dcBuild);

	public static final DataCollector dcModuleRefsToOtherModules = create("ModuleRefsToOtherModules", dcBuild);

	public static final DataCollector dcPreProcess = create("Pre-Processing (Lazy Linking, etc.)", dcBuild);

	public static final DataCollector dcTypesBuilder = create("Types Builder", dcBuild);
	public static final DataCollector dcTypesBuilderCreate = create("Create (standard case)", dcTypesBuilder);
	public static final DataCollector dcTypesBuilderRelink = create("Relink TModule to Source", dcTypesBuilder);

	public static final DataCollector dcAstPostProcess = create("AstPostProcess", dcBuild);
	public static final DataCollector dcRuntimeDepsCollect = create("RuntimeDepsCollect", dcAstPostProcess);
	public static final DataCollector dcRuntimeDepsFindCycles = create("RuntimeDepsFindCycles", dcAstPostProcess);

	public static final DataCollector dcValidations = create("Validations", dcBuild);

	public static final DataCollector dcValidationsPackageJson = create("Validations (package.json)", dcBuild);

	public static final DataCollector dcTranspilation = create("Transpilation (ECMAScript)", dcBuild);
	public static final DataCollector dcTranspilationPreparation = create("Preparation (create IM)", dcTranspilation);
	public static final DataCollector dcTranspilationStep1 = create("Step 1 (get transformations)", dcTranspilation);
	public static final DataCollector dcTranspilationStep2 = create("Step 2 (analyze)", dcTranspilation);
	public static final DataCollector dcTranspilationStep3 = create("Step 3 (transform)", dcTranspilation);
	public static final DataCollector dcTranspilationPrettyPrint = create("Pretty Print", dcTranspilation);

	public static final DataCollector dcDtsGeneration = create("d.ts generation", dcBuild);

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

	public static final DataCollector dcCliClean = create("n4jsc clean");

	public static final DataCollector dcTemp = create("TEMP");

	public static final DataCollector dcCache = create("Cache");
	public static final DataCollector dcCacheGet = create("Get", dcCache);
	public static final DataCollector dcCachePut = create("Put", dcCache);
	public static final DataCollector dcCacheMakeKeys = create("MakeKeys", dcCache);
	public static final DataCollector dcCacheSetTarget = create("SetTarget", dcCache);

	public static final DataCollector dcTypeHierachyTraverser = create("TypeHierachyTraverser");
	public static final DataCollector dcTHT_AllMembersCollector = create("AllMembersCollector",
			dcTypeHierachyTraverser);
	public static final DataCollector dcTHT_CollectMembersHelper = create("CollectMembersHelper",
			dcTypeHierachyTraverser);
	public static final DataCollector dcTHT_MemberEntriesCollector = create("MemberEntriesCollector",
			dcTypeHierachyTraverser);
	public static final DataCollector dcTHT_FindCallConstructSignatureHelper = create(
			"FindCallConstructSignatureHelper", dcTypeHierachyTraverser);
	public static final DataCollector dcTHT_FindMemberHelper = create("FindMemberHelper", dcTypeHierachyTraverser);
	public static final DataCollector dcTHT_LazyOverrideAwareMemberCollectorX = create(
			"LazyOverrideAwareMemberCollectorX", dcTypeHierachyTraverser);
	public static final DataCollector dcTHT_AllSuperTypeRefsCollector = create("AllSuperTypeRefsCollector",
			dcTypeHierachyTraverser);
	public static final DataCollector dcTHT_AllSuperTypesCollector = create("AllSuperTypesCollector",
			dcTypeHierachyTraverser);
	public static final DataCollector dcTHT_FindElementTypeHelper = create("FindElementTypeHelper",
			dcTypeHierachyTraverser);
	public static final DataCollector dcTHT_SuperTypesMapper = create("SuperTypesMapper", dcTypeHierachyTraverser);

	public static DataCollector createDataCollectorForCheckMethod(String methodName) {
		final DataCollector parent;
		if (N4JSDataCollectors.dcValidationsPackageJson.hasActiveMeasurement()) {
			parent = N4JSDataCollectors.dcValidationsPackageJson;
		} else if (N4JSDataCollectors.dcValidations.hasActiveMeasurement()) {
			parent = N4JSDataCollectors.dcValidations;
		} else {
			if (!N4JSDataCollectors.dcValidations.isPaused()) {
				DataCollectors.INSTANCE.warn("check method " + methodName
						+ " invoked without data collector " + N4JSDataCollectors.dcValidations.getId()
						+ " being active");
			}
			parent = N4JSDataCollectors.dcValidations;
		}
		return DataCollectors.INSTANCE.getOrCreateDataCollector(methodName, parent);
	}

	/**
	 * Like {@link #measure(String, Supplier)}, but for operations that do not provide a result value.
	 */
	public static void measure(String key, Runnable operation) {
		final DataCollector dc = DataCollectors.INSTANCE.getOrCreateDataCollector("TEMP " + key);
		try (Measurement m = dc.getMeasurement()) {
			operation.run();
		}
	}

	/**
	 * Measures the given operation in the context of the data collector with the given {@code key}.
	 *
	 * ONLY INTEDED FOR TEMPOARAY DEBUGGING. For a measuring that is supposed to be merged to master, add a data
	 * collector constant to {@link N4JSDataCollectors} and use the try-with-resource pattern.
	 */
	public static <T> T measure(String key, Supplier<T> operation) {
		final DataCollector dc = DataCollectors.INSTANCE.getOrCreateDataCollector("TEMP " + key);
		try (Measurement m = dc.getMeasurement()) {
			return operation.get();
		}
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
