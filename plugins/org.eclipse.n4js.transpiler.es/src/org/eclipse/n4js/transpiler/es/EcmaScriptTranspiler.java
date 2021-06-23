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
package org.eclipse.n4js.transpiler.es;

import java.io.Writer;

import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.es.transform.ApiImplStubGenerationTransformation;
import org.eclipse.n4js.transpiler.es.transform.ArrowFunction_Part1_Transformation;
import org.eclipse.n4js.transpiler.es.transform.ArrowFunction_Part2_Transformation;
import org.eclipse.n4js.transpiler.es.transform.BlockTransformation;
import org.eclipse.n4js.transpiler.es.transform.ClassDeclarationTransformation;
import org.eclipse.n4js.transpiler.es.transform.DependencyInjectionTransformation;
import org.eclipse.n4js.transpiler.es.transform.DestructuringTransformation;
import org.eclipse.n4js.transpiler.es.transform.EnumAccessTransformation;
import org.eclipse.n4js.transpiler.es.transform.EnumDeclarationTransformation;
import org.eclipse.n4js.transpiler.es.transform.ExpressionTransformation;
import org.eclipse.n4js.transpiler.es.transform.InterfaceDeclarationTransformation;
import org.eclipse.n4js.transpiler.es.transform.JSXTransformation;
import org.eclipse.n4js.transpiler.es.transform.MemberPatchingTransformation;
import org.eclipse.n4js.transpiler.es.transform.ModuleSpecifierTransformation;
import org.eclipse.n4js.transpiler.es.transform.ModuleWrappingTransformation;
import org.eclipse.n4js.transpiler.es.transform.RestParameterTransformation;
import org.eclipse.n4js.transpiler.es.transform.SanitizeImportsTransformation;
import org.eclipse.n4js.transpiler.es.transform.SimplifyTransformation;
import org.eclipse.n4js.transpiler.es.transform.StaticPolyfillTransformation;
import org.eclipse.n4js.transpiler.es.transform.TemplateStringTransformation;
import org.eclipse.n4js.transpiler.es.transform.TrimTransformation;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Transpiles N4JS to ECMAScript. The exact language version of the target code depends on configuration parameters, so
 * this class covers transpilation to plain ES5, ES5 with selected additional ES6 features and full ES6.
 * <p>
 * NOTE: the current implementation only supports transpilation to "ES5 with selected additional ES6 features".
 */
public class EcmaScriptTranspiler extends AbstractTranspiler {

	@Inject
	private Provider<DestructuringTransformation> destructuringTransformation;
	@Inject
	private Provider<MemberPatchingTransformation> memberPatchingTransformationProvider;
	@Inject
	private Provider<ApiImplStubGenerationTransformation> apiImplStubGenerationTransformationProvider;
	@Inject
	private Provider<StaticPolyfillTransformation> staticPolyfillTransformationProvider;
	@Inject
	private Provider<TemplateStringTransformation> templateStringTransformationProvider;
	@Inject
	private Provider<ExpressionTransformation> expressionTransformationProvider;
	@Inject
	private Provider<EnumAccessTransformation> enumAccessTransformationProvider;
	@Inject
	private Provider<DependencyInjectionTransformation> dependencyInjectionTransformation;
	@Inject
	private Provider<ClassDeclarationTransformation> classDeclarationTransformationProvider;
	@Inject
	private Provider<InterfaceDeclarationTransformation> interfaceDeclarationTransformationProvider;
	@Inject
	private Provider<EnumDeclarationTransformation> enumDeclarationTransformationProvider;
	@Inject
	private Provider<SimplifyTransformation> simplifyTransformation;
	@Inject
	private Provider<TrimTransformation> trimTransformation;
	@Inject
	private Provider<SanitizeImportsTransformation> sanitizeImportsTransformationProvider;
	@Inject
	private Provider<ModuleSpecifierTransformation> moduleSpecifierTransformationProvider;
	@Inject
	private Provider<ModuleWrappingTransformation> moduleWrappingTransformationProvider;
	@Inject
	private Provider<BlockTransformation> blockTransformationProvider;
	@Inject
	private Provider<RestParameterTransformation> restParameterTransformationProvider;
	@Inject
	private Provider<ArrowFunction_Part1_Transformation> arrowFunction_Part1_TransformationProvider;
	@Inject
	private Provider<ArrowFunction_Part2_Transformation> arrowFunction_Part2_TransformationProvider;
	@Inject
	private Provider<JSXTransformation> jsxTransformationProvider;

	/**
	 * Returns the AST transformations to be executed for the resource to transpile in the given transpiler state, in
	 * the order they should be executed.
	 * <ul>
	 * <li>For now, we always execute the same transformations in the same order and transformations are hard-coded.
	 * <li>Later, we will have some form of registration and order and selection of transformation to execute may depend
	 * on the given transpiler state (i.e. the resource to transpile) or some configuration parameters.
	 * </ul>
	 */
	@Override
	protected Transformation[] computeTransformationsToBeExecuted(TranspilerState state) {
		return new Transformation[] {
				jsxTransformationProvider.get(),
				staticPolyfillTransformationProvider.get(),
				memberPatchingTransformationProvider.get(),
				apiImplStubGenerationTransformationProvider.get(),
				destructuringTransformation.get(),
				templateStringTransformationProvider.get(),
				expressionTransformationProvider.get(),
				enumAccessTransformationProvider.get(),
				dependencyInjectionTransformation.get(),
				enumDeclarationTransformationProvider.get(),
				classDeclarationTransformationProvider.get(),
				interfaceDeclarationTransformationProvider.get(),
				arrowFunction_Part1_TransformationProvider.get(),
				blockTransformationProvider.get(),
				restParameterTransformationProvider.get(),
				arrowFunction_Part2_TransformationProvider.get(),
				simplifyTransformation.get(),
				trimTransformation.get(),
				sanitizeImportsTransformationProvider.get(),
				moduleSpecifierTransformationProvider.get(),
				moduleWrappingTransformationProvider.get()
		};
	}

	/**
	 * General entry-point. Overridden to handle plain-JS-wrapping without transforming.
	 */
	@Override
	public void transpile(N4JSResource resource, GeneratorOption[] options, Writer outCode,
			Optional<SourceMapInfo> optSourceMapInfo) {
		try (Measurement m = N4JSDataCollectors.dcTranspilation.getMeasurement()) {
			super.transpile(resource, options, outCode, optSourceMapInfo);
		}
	}

}
