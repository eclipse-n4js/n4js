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

import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.es.transform.ApiImplStubGenerationTransformation;
import org.eclipse.n4js.transpiler.es.transform.ArrowFunction_Part1_Transformation;
import org.eclipse.n4js.transpiler.es.transform.ArrowFunction_Part2_Transformation;
import org.eclipse.n4js.transpiler.es.transform.BlockTransformation;
import org.eclipse.n4js.transpiler.es.transform.ClassDeclarationTransformation;
import org.eclipse.n4js.transpiler.es.transform.CommonJsImportsTransformation;
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
	private Provider<MemberPatchingTransformation> memberPatchingTransformation;
	@Inject
	private Provider<ApiImplStubGenerationTransformation> apiImplStubGenerationTransformation;
	@Inject
	private Provider<StaticPolyfillTransformation> staticPolyfillTransformation;
	@Inject
	private Provider<TemplateStringTransformation> templateStringTransformation;
	@Inject
	private Provider<ExpressionTransformation> expressionTransformation;
	@Inject
	private Provider<EnumAccessTransformation> enumAccessTransformation;
	@Inject
	private Provider<DependencyInjectionTransformation> dependencyInjectionTransformation;
	@Inject
	private Provider<ClassDeclarationTransformation> classDeclarationTransformation;
	@Inject
	private Provider<InterfaceDeclarationTransformation> interfaceDeclarationTransformation;
	@Inject
	private Provider<EnumDeclarationTransformation> enumDeclarationTransformation;
	@Inject
	private Provider<SimplifyTransformation> simplifyTransformation;
	@Inject
	private Provider<TrimTransformation> trimTransformation;
	@Inject
	private Provider<SanitizeImportsTransformation> sanitizeImportsTransformation;
	@Inject
	private Provider<CommonJsImportsTransformation> commonJsImportsTransformation;
	@Inject
	private Provider<ModuleWrappingTransformation> moduleWrappingTransformation;
	@Inject
	private Provider<ModuleSpecifierTransformation> moduleSpecifierTransformation;
	@Inject
	private Provider<BlockTransformation> blockTransformation;
	@Inject
	private Provider<RestParameterTransformation> restParameterTransformation;
	@Inject
	private Provider<ArrowFunction_Part1_Transformation> arrowFunction_Part1_Transformation;
	@Inject
	private Provider<ArrowFunction_Part2_Transformation> arrowFunction_Part2_Transformation;
	@Inject
	private Provider<JSXTransformation> jsxTransformation;

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
				// ---- preparatory transformations (e.g. getting rid of certain language features)
				jsxTransformation.get(),
				staticPolyfillTransformation.get(),
				// ---- main transformations related to individual language features
				memberPatchingTransformation.get(),
				apiImplStubGenerationTransformation.get(), // preparatory transformation moved here due to requirement
				destructuringTransformation.get(),
				templateStringTransformation.get(),
				expressionTransformation.get(),
				enumAccessTransformation.get(),
				dependencyInjectionTransformation.get(),
				enumDeclarationTransformation.get(),
				classDeclarationTransformation.get(),
				interfaceDeclarationTransformation.get(),
				arrowFunction_Part1_Transformation.get(),
				blockTransformation.get(),
				restParameterTransformation.get(),
				arrowFunction_Part2_Transformation.get(),
				// ---- clean up / generic / technical transformations
				simplifyTransformation.get(),
				trimTransformation.get(),
				sanitizeImportsTransformation.get(),
				commonJsImportsTransformation.get(),
				moduleSpecifierTransformation.get(),
				moduleWrappingTransformation.get()
		};
	}

	@Override
	protected boolean isPerformanceDataCollectionActive() {
		return true;
	}
}
