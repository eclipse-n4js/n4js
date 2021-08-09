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
package org.eclipse.n4js.transpiler.dts;

import java.io.Writer;

import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.tooling.N4JSDocumentationProvider;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.dts.print.PrettyPrinterDts;
import org.eclipse.n4js.transpiler.dts.transform.CutOffTransformation;
import org.eclipse.n4js.transpiler.dts.transform.EnumAddMissingInitializersTransformation;
import org.eclipse.n4js.transpiler.dts.transform.ImplementedMemberTransformation;
import org.eclipse.n4js.transpiler.dts.transform.InferredTypesTransformation;
import org.eclipse.n4js.transpiler.dts.transform.OverriddenAccessorsTransformation;
import org.eclipse.n4js.transpiler.dts.transform.ReturnTypeTransformation;
import org.eclipse.n4js.transpiler.dts.transform.ThisTypeTransformation;
import org.eclipse.n4js.transpiler.dts.transform.TrimTransformationDts;
import org.eclipse.n4js.transpiler.dts.transform.TypeReferenceTransformation;
import org.eclipse.n4js.transpiler.es.transform.ModuleSpecifierTransformation;
import org.eclipse.n4js.transpiler.es.transform.SanitizeImportsTransformation;
import org.eclipse.n4js.transpiler.es.transform.StaticPolyfillTransformation;
import org.eclipse.n4js.transpiler.print.LineColTrackingAppendable;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Transpiles N4JS to d.ts.
 */
public class DtsTranspiler extends AbstractTranspiler {

	@Inject
	private Provider<StaticPolyfillTransformation> staticPolyfillTransformation;
	@Inject
	private Provider<CutOffTransformation> cutOffTransformation;
	@Inject
	private Provider<ImplementedMemberTransformation> implementedMemberTransformation;
	@Inject
	private Provider<EnumAddMissingInitializersTransformation> enumAddMissingInitializersTransformation;
	@Inject
	private Provider<ReturnTypeTransformation> returnTypeTransformation;
	@Inject
	private Provider<ThisTypeTransformation> thisTypeTransformation;
	@Inject
	private Provider<InferredTypesTransformation> inferredTypesTransformation;
	@Inject
	private Provider<OverriddenAccessorsTransformation> overriddenAccessorsTransformation;
	@Inject
	private Provider<TrimTransformationDts> trimTransformationDts;
	@Inject
	private Provider<TypeReferenceTransformation> typeReferenceTransformation;
	@Inject
	private Provider<SanitizeImportsTransformation> sanitizeImportsTransformation;
	@Inject
	private Provider<ModuleSpecifierTransformation> moduleSpecifierTransformation;

	@Inject
	private N4JSDocumentationProvider documentationProvider;

	/**
	 * Returns the AST transformations to be executed for the resource to transpile in the given transpiler state, in
	 * the order they should be executed.
	 */
	@Override
	protected Transformation[] computeTransformationsToBeExecuted(TranspilerState state) {
		return new Transformation[] {
				// ---- preparatory transformations (e.g. getting rid of certain language features)
				staticPolyfillTransformation.get(),
				// ---- main transformations related to individual language features
				cutOffTransformation.get(),
				implementedMemberTransformation.get(),
				enumAddMissingInitializersTransformation.get(),
				returnTypeTransformation.get(),
				inferredTypesTransformation.get(), // technical transformation moved here only due to requirement
				overriddenAccessorsTransformation.get(),
				thisTypeTransformation.get(),
				// ---- clean up / generic / technical transformations
				trimTransformationDts.get(),
				typeReferenceTransformation.get(),
				sanitizeImportsTransformation.get(),
				moduleSpecifierTransformation.get()
		};
	}

	/**
	 * General entry-point. Overridden to start .d.ts-specific performance data collector.
	 */
	@Override
	public void transpile(N4JSResource resource, GeneratorOption[] options, Writer outCode,
			Optional<SourceMapInfo> optSourceMapInfo) {

		try (Measurement m = N4JSDataCollectors.dcDtsGeneration.getMeasurement()) {
			super.transpile(resource, options, outCode, optSourceMapInfo);
		}
	}

	/**
	 * Third and last step during transpilation. Generating output code and source maps (optional).
	 */
	@Override
	protected void prettyPrint(TranspilerState state, Writer outCode, Optional<String> optPreamble,
			Optional<SourceMapInfo> optSourceMapInfo) {

		final LineColTrackingAppendable out = new LineColTrackingAppendable(outCode, "\t");
		PrettyPrinterDts.append(out, state, optPreamble, documentationProvider);
	}
}
