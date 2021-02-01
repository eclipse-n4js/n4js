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
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.utils.TypeUtils

/**
 * Base class for functions and methods
 */
package abstract class AbstractFunctionDefinitionTypesBuilder {

	@Inject extension N4JSFormalParameterTypesBuilder

	def protected void relinkFormalParameters(TFunction functionType, FunctionDefinition functionDef, boolean preLinkingPhase) {
		functionDef.fpars.fold(0) [ idx, fpar |
			if (relinkFormalParameter(fpar, functionType, preLinkingPhase, idx)) {
				return idx + 1;
			}
			return idx;
		]
	}

	def protected void addFormalParameters(TFunction functionType, FunctionDefinition functionDef,
				BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		functionType.fpars.addAll(
			functionDef.fpars.map[createFormalParameter(builtInTypeScope, preLinkingPhase)].filterNull);
	}

	def protected void setReturnType(TGetter getterType, N4GetterDeclaration getterDef,
				BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			val inferredReturnTypeRef =
				if (getterDef.declaredTypeRefInAST === null) {
					if (!preLinkingPhase) {
						if(getterType.isAbstract) {
							builtInTypeScope.anyTypeRef
						} else {
							inferReturnTypeFromReturnStatements(getterDef, builtInTypeScope)
						}
					}
				} else {
					getterDef.declaredTypeRefInAST
				};
			getterType.typeRef = TypeUtils.copyWithProxies(inferredReturnTypeRef);
		}
	}

	def protected void setReturnType(TFunction functionType, FunctionDefinition functionDef,
				BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			val inferredReturnTypeRef =
				if (functionDef.declaredReturnTypeRefInAST === null) {
					if (!preLinkingPhase) {
						inferReturnTypeFromReturnStatements(functionDef, builtInTypeScope)
					}
				} else {
					functionDef.declaredReturnTypeRefInAST
				};
			functionType.returnTypeRef = TypeUtils.copyWithProxies(inferredReturnTypeRef);
			// note: handling of the return type of async functions not done here, see TypeProcessor#handleAsyncFunctionDeclaration()
		}
	}

	/**
	 * Poor man's return type inferencer
	 */
	// TODO improve that
	def protected ParameterizedTypeRef inferReturnTypeFromReturnStatements(FunctionDefinition definition, BuiltInTypeScope builtInTypeScope) {
		val hasNonVoidReturn = definition.body!==null && definition.body.hasNonVoidReturn;
		if (hasNonVoidReturn) {
			return builtInTypeScope.anyTypeRef
		} else {
			/*
			 * No Return statements usually implies void as result type for the FunctionDefinition,
			 * except for those representing arrow functions of the single-expression variety,
			 * whose result type is heuristically approximated as 'any'.
			 *
			 * FIXME that single-expr in an arrow function may well be an invocation to
			 * a void-method, in which case the 'any' choice is wrong.
			 */
			if (isSingleExprArrowFunction(definition)) {
				return builtInTypeScope.anyTypeRef
			} else {
				return builtInTypeScope.voidTypeRef
			}
		}
	}

	private def boolean isSingleExprArrowFunction(FunctionDefinition definition) {
		switch definition {
			ArrowFunction: definition.isSingleExprImplicitReturn
			default: false
		}
	}

	/**
	 * Poor man's return type inferencer
	 */
	// TODO improve that
	def protected ParameterizedTypeRef inferReturnTypeFromReturnStatements(N4GetterDeclaration definition, BuiltInTypeScope builtInTypeScope) {
		val hasNonVoidReturn = definition.body!==null && definition.body.hasNonVoidReturn;
		if (hasNonVoidReturn) {
			builtInTypeScope.anyTypeRef
		} else {
			builtInTypeScope.voidTypeRef
		}
	}
}
