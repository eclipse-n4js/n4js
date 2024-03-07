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
package org.eclipse.n4js.typesbuilder;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.fold;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.types.utils.TypeUtils;

import com.google.inject.Inject;

/**
 * Base class for functions and methods
 */
abstract class AbstractFunctionDefinitionTypesBuilder {

	@Inject
	N4JSFormalParameterTypesBuilder formalParameterTypesBuilder;

	protected void relinkFormalParameters(TFunction functionType, FunctionDefinition functionDef,
			boolean preLinkingPhase) {
		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(functionDef.eResource().getResourceSet());
		fold(functionDef.getFpars(), 0, (idx, fpar) -> {
			if (formalParameterTypesBuilder.relinkFormalParameter(fpar, functionType, builtInTypeScope, preLinkingPhase,
					idx)) {
				return idx + 1;
			}
			return idx;
		});
	}

	protected void addFormalParameters(TFunction functionType, FunctionDefinition functionDef,
			BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		functionType.getFpars().addAll(
				toList(filterNull(map(functionDef.getFpars(),
						fp -> formalParameterTypesBuilder.createFormalParameter(fp, builtInTypeScope,
								preLinkingPhase)))));
	}

	protected void setReturnType(TGetter getterType, N4GetterDeclaration getterDef,
			BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			TypeRef inferredReturnTypeRef = null;
			if (getterDef.getDeclaredTypeRefInAST() == null) {
				if (!preLinkingPhase) {
					if (getterType.isAbstract()) {
						inferredReturnTypeRef = builtInTypeScope.getAnyTypeRef();
					} else {
						inferredReturnTypeRef = inferReturnTypeFromReturnStatements(getterDef, builtInTypeScope);
					}
				}
			} else {
				inferredReturnTypeRef = getterDef.getDeclaredTypeRefInAST();
			}
			getterType.setTypeRef(TypeUtils.copyWithProxies(inferredReturnTypeRef));
		}
	}

	protected void setReturnType(TFunction functionType, FunctionDefinition functionDef,
			BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			TypeRef inferredReturnTypeRef = null;
			if (functionDef.getDeclaredReturnTypeRefInAST() == null) {
				if (!preLinkingPhase) {
					inferredReturnTypeRef = inferReturnTypeFromReturnStatements(functionDef, builtInTypeScope);
				}
			} else {
				inferredReturnTypeRef = functionDef.getDeclaredReturnTypeRefInAST();
			}
			functionType.setReturnTypeRef(TypeUtils.copyWithProxies(inferredReturnTypeRef));
			// note: handling of the return type of async functions not done here, see
			// TypeProcessor#handleAsyncFunctionDeclaration()
		}
	}

	/**
	 * Poor man's return type inferencer
	 */
	// TODO improve that
	protected ParameterizedTypeRef inferReturnTypeFromReturnStatements(FunctionDefinition definition,
			BuiltInTypeScope builtInTypeScope) {
		boolean hasNonVoidReturn = definition.getBody() != null && definition.getBody().hasNonVoidReturn();
		if (hasNonVoidReturn) {
			return builtInTypeScope.getAnyTypeRef();
		} else {
			/*
			 * No Return statements usually implies void as result type for the FunctionDefinition, except for those
			 * representing arrow functions of the single-expression variety, whose result type is heuristically
			 * approximated as 'any'.
			 *
			 * FIXME that single-expr in an arrow function may well be an invocation to a void-method, in which case the
			 * 'any' choice is wrong.
			 */
			if (isSingleExprArrowFunction(definition)) {
				return builtInTypeScope.getAnyTypeRef();
			} else {
				return builtInTypeScope.getVoidTypeRef();
			}
		}
	}

	private boolean isSingleExprArrowFunction(FunctionDefinition definition) {
		if (definition instanceof ArrowFunction) {
			return ((ArrowFunction) definition).isSingleExprImplicitReturn();
		}
		return false;
	}

	/**
	 * Poor man's return type inferencer
	 */
	// TODO improve that
	protected ParameterizedTypeRef inferReturnTypeFromReturnStatements(N4GetterDeclaration definition,
			BuiltInTypeScope builtInTypeScope) {
		boolean hasNonVoidReturn = definition.getBody() != null && definition.getBody().hasNonVoidReturn();
		if (hasNonVoidReturn) {
			return builtInTypeScope.getAnyTypeRef();
		} else {
			return builtInTypeScope.getVoidTypeRef();
		}
	}
}
