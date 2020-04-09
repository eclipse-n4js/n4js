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
package org.eclipse.n4js.transpiler;

import java.io.Writer;
import java.nio.file.Path;

import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.transpiler.print.PrettyPrinter;
import org.eclipse.n4js.transpiler.utils.TranspilerDebugUtils;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.n4js.utils.di.scopes.ScopeManager;
import org.eclipse.n4js.utils.di.scopes.TransformationScoped;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Base implementation of all (new) transpilers driving the main work-flow during transpilation, which consists of the
 * following steps<sup>1</sup>:
 * <ol>
 * <li>create initial transpiler state (i.e. create intermediate model, etc.)
 * <li>execute all transformations on the transpiler state
 * <ol type="a">
 * <li>ask transformation manager for transformations to execute
 * <li>give each transformation a chance to perform early analysis on the initial (unchanged!) state
 * <li>actually perform the transformations (in the order defined by transformation manager)
 * </ol>
 * <li>pretty-print the intermediate model + emit source maps (optional)
 * </ol>
 * <p>
 * <sup>1</sup> because we use the term "phases" for something else, we refer to the above as "steps".
 */
public abstract class AbstractTranspiler {

	/** */
	public static final boolean DEBUG_DUMP_STATE = false;
	/** */
	public static final boolean DEBUG_DRAW_STATE = false;
	/** */
	public static final boolean DEBUG_PERFORM_ASSERTIONS = false;
	/** */
	public static final boolean DEBUG_PERFORM_VALIDATIONS = false;

	@Inject
	private PreparationStep preparationStep;
	@Inject
	private PrettyPrintingStep prettyPrintingStep;
	@Inject
	private ScopeManager scopeManager;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private TranspilerDebugUtils transpilerDebugUtils;

	/**
	 * Returns an optional preamble that will be prepended to the output code in each output file. Use '\n' as line
	 * separator. See {@link PrettyPrinter#print(TranspilerState, Writer, Optional, Optional)} for details.
	 */
	protected abstract Optional<String> getPreamble();

	/**
	 * To customize the transpilation process, subclasses should here provide the concrete AST transformations to be
	 * executed for the given initial transpiler state in the order they should be executed.
	 */
	protected abstract Transformation[] computeTransformationsToBeExecuted(TranspilerState state);

	/**
	 * Internal class for providing all necessary information about source-map generation.
	 */
	public class SourceMapInfo {

		/** pure file extension for source map, e.g. "map" */
		public String sourceMapFileExtension;

		/** Name of the file which is generated. "A.js" */
		public String simpleCompiledFileName;

		/** Name of the source-map file, e.g. "A.map" */
		public String simpleSourceMapFileName;

		/** Path to the original input-file,relative to output-location. e.g. "A.n4js" */
		public String n4jsFilePath;

		/** */
		public Writer sourceMapBuff;

		/**
		 * if true indicates the lookup in absolute form "/sources/Project-0.0.1/a/b/M.n4js" else lookup relative to
		 * source-map in compile-result file "../../../src/a/b/M.n4js"
		 */
		public boolean isExplicitSourceRef;
		/**
		 * in case of {@code isExplicitSourceRef == true} this path is used as the prefix (e.g. "/sources") for the
		 * Full-Module-Path (e.g."Project-0.0.1/a/b/M.n4js")
		 */
		public Path explicitNavigationToSrc;

		/**
		 * Depending on {@link #isExplicitSourceRef} calculate the Path
		 *
		 * @param eResource
		 *            Module to point to
		 * @return path to the Module.
		 */
		public String resolve(N4JSResource eResource) {
			if (isExplicitSourceRef) {
				//
				String completeSpecifier = resourceNameComputer.getCompleteModuleSpecifier(eResource.getModule());
				String fileExtension = eResource.getURI().fileExtension();
				String specifierAsFile = fileExtension == null ? completeSpecifier
						: completeSpecifier + "." + fileExtension;
				return explicitNavigationToSrc.resolve(specifierAsFile).toString();
			} else {
				// relative resolution, we do not use the actual eResource since we computed the path to the original
				// name.
				return n4jsFilePath;
			}
		}

	}

	/**
	 * Transpile the given resource to the given writers.
	 *
	 * @param resource
	 *            the resource to transpile.
	 * @param options
	 *            the {@link GeneratorOption generator options} to use during generation.
	 * @param outCode
	 *            the writer where the generated output code should be sent.
	 * @param optSourceMapInfo
	 *            meta-info including the writer to create source-maps. If absent do not create source-maps.
	 */
	public void transpile(N4JSResource resource, GeneratorOption[] options, Writer outCode,
			Optional<SourceMapInfo> optSourceMapInfo) {

		// step 1: create initial transpiler state (i.e. create intermediate model, etc.)
		final TranspilerState state = prepare(resource, options);

		try {

			// step 2: execute all transformations on the transpiler state
			transform(state);

			// step 3: pretty-print the intermediate model + emit source maps (optional)
			final Optional<String> optPreamble = getPreamble();
			prettyPrint(state, outCode, optPreamble, optSourceMapInfo);

		} finally {

			// step 4: clean up temporary types (if any)
			cleanUpTemporaryTypes(state);
		}
	}

	/**
	 * First step during transpilation. Creates initial transpiler state.
	 *
	 * @param options
	 *            the {@link GeneratorOption generator options} to use during generation.
	 */
	protected TranspilerState prepare(N4JSResource resource, GeneratorOption[] options) {
		final Script script = resource.getScript();
		if (script == null || script.eIsProxy()) {
			throw new IllegalArgumentException("given N4JSResource does not contain a script or script is a proxy");
		}
		return preparationStep.prepare(script, options);
	}

	/**
	 * Second and main step during transpilation, where the transformations are executed on the intermediate model.
	 */
	protected void transform(TranspilerState state) {
		scopeManager.enter(TransformationScoped.class);
		try {
			scopeManager.bind(TransformationScoped.class, TranspilerState.class, state);

			if (DEBUG_DUMP_STATE) {
				System.out.println("============================== RESOURCE: " + state.resource.getURI()
						+ " ==============================");
			}

			// step 1: ask transformation manager for transformations to execute
			Transformation[] transformationsPreFiler = null;
			Transformation[] transformations = null;
			try (Measurement m = N4JSDataCollectors.dcTranspilationStep1.getMeasurement()) {
				transformationsPreFiler = computeTransformationsToBeExecuted(state);
				transformations = TransformationDependency
						.filterByTranspilerOptions(transformationsPreFiler, state.options);
				TransformationDependency.assertDependencies(transformations);
			}

			// step 2: give each transformation a chance to perform early analysis on the initial (unchanged!) state
			try (Measurement m = N4JSDataCollectors.dcTranspilationStep2.getMeasurement()) {
				for (Transformation currT : transformations) {
					String name = "T2_" + currT.getClass().getSimpleName();
					DataCollector dcT2_ct = DataCollectors.INSTANCE
							.getOrCreateDataCollector(name, N4JSDataCollectors.dcTranspilationStep2);

					try (Measurement m2 = dcT2_ct.getMeasurement(name);) {
						currT.analyze();
					}
				}
			}

			// step 3: actually perform the transformations (in the order defined by transformation manager)
			try (Measurement m = N4JSDataCollectors.dcTranspilationStep3.getMeasurement()) {
				for (Transformation currT : transformations) {
					String name = "T3_" + currT.getClass().getSimpleName();
					DataCollector dcT3_ct = DataCollectors.INSTANCE
							.getOrCreateDataCollector(name, N4JSDataCollectors.dcTranspilationStep3);

					try (Measurement m2 = dcT3_ct.getMeasurement(name);) {

						if (DEBUG_DUMP_STATE) {
							System.out
									.println("============================== PRE " + currT.getClass().getSimpleName());
							TranspilerDebugUtils.dump(state);
						}
						if (DEBUG_DRAW_STATE) {
							TranspilerDebugUtils.dumpGraph(state, "PRE " + currT.getClass().getSimpleName());
						}

						if (DEBUG_PERFORM_ASSERTIONS) {
							currT.assertPreConditions();
						}

						currT.transform();

						if (DEBUG_PERFORM_ASSERTIONS) {
							currT.assertPostConditions();
						}

						if (DEBUG_PERFORM_VALIDATIONS) {
							transpilerDebugUtils.validateState(state, true);
						}
					}
				}
			}

			if (DEBUG_DUMP_STATE) {
				System.out.println("============================== POST all transformations");
				TranspilerDebugUtils.dump(state);
			}
			if (DEBUG_DRAW_STATE) {
				TranspilerDebugUtils.dumpGraph(state, "POST all transformations");
			}

		} finally {
			scopeManager.exit(TransformationScoped.class);
		}
	}

	/**
	 * Third and last step during transpilation. Generating output code and source maps (optional).
	 */
	protected void prettyPrint(TranspilerState state, Writer outCode, Optional<String> optPreamble,
			Optional<SourceMapInfo> optSourceMapInfo) {
		prettyPrintingStep.print(state, outCode, optPreamble, optSourceMapInfo);
	}

	/**
	 * Clears reference {@link TModule#getTemporaryTypes() temporaryTypes} of the <code>TModule</code> of the given
	 * state's resource.
	 * <p>
	 * Motivation: transformations often have to create temporary types (e.g., when creating new namespace imports) and
	 * by clearing all temporary types after transpilation we relieve the individual transformations of the burden of
	 * handling removal themselves.
	 */
	protected void cleanUpTemporaryTypes(TranspilerState state) {
		state.resource.clearTemporaryTypes();
	}

	/**
	 * Performs preparation step. Only intended for testing.
	 *
	 * @param options
	 *            the {@link GeneratorOption generator options} to use during generation.
	 */
	public TranspilerState testPrepare(N4JSResource resource, GeneratorOption[] options) {
		return prepare(resource, options);
	}

	/**
	 * Performs transformation step. Only intended for testing.
	 */
	public void testTransform(TranspilerState state) {
		transform(state);
	}
}
