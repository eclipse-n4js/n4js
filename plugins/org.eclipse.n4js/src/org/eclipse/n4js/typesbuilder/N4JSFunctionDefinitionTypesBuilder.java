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
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Type builder for function declaration or expression builder.
 */
// TODO we temporarily create a BuiltInTypeScope in order to get primitive types. This may be changed by passing in this
// scope, one this method is called by the type system
@Singleton
public class N4JSFunctionDefinitionTypesBuilder extends AbstractFunctionDefinitionTypesBuilder {

	@Inject
	N4JSFormalParameterTypesBuilder _n4JSFormalParameterTypesBuilder;
	@Inject
	N4JSTypeVariableTypesBuilder _n4JSTypeVariableTypesBuilder;
	@Inject
	N4JSVariableStatementTypesBuilder _n4JSVariableStatementTypesBuilder;
	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	boolean relinkTFunction(FunctionDeclaration functionDecl, AbstractNamespace target, boolean preLinkingPhase,
			int idx) {
		EObject functionDefinedType = (EObject) functionDecl
				.eGet(N4JSPackage.eINSTANCE.getTypeDefiningElement_DefinedType(), false);
		if (functionDefinedType != null && !functionDefinedType.eIsProxy()) {
			throw new IllegalStateException("TFunction already created for FunctionDeclaration");
		}

		if (functionDecl.getName() == null) {
			return false;
		}

		TFunction functionType = target.getFunctions().get(idx);
		_n4JSTypesBuilderHelper.ensureEqualName(functionDecl, functionType);

		relinkFormalParameters(functionType, functionDecl, preLinkingPhase);
		functionType.setAstElement(functionDecl);
		functionDecl.setDefinedType(functionType);

		return true;
	}

	/**
	 * Creates TFunction for the given function declaration and adds it to the modules top level types (as function
	 * declarations are only allowed on top level).
	 *
	 * @param functionDecl
	 *            declaration for which the TFunction is created, must not be linked to a TFunction yet (i.e. its
	 *            defined type must be null).
	 * @param target
	 *            the module to which the newly created TFunction is added
	 */
	void createTFunction(FunctionDeclaration functionDecl, AbstractNamespace target, boolean preLinkingPhase) {
		EObject functionDefinedType = (EObject) functionDecl
				.eGet(N4JSPackage.eINSTANCE.getTypeDefiningElement_DefinedType(), false);
		if (functionDefinedType != null && !functionDefinedType.eIsProxy()) {
			throw new IllegalStateException("TFunction already created for FunctionDeclaration");
		}

		if (functionDecl.getName() == null) {
			return;
		}

		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(functionDecl.eResource().getResourceSet());
		TFunction functionType = createAndLinkTFunction(functionDecl);
		_n4JSVariableStatementTypesBuilder.createImplicitArgumentsVariable(functionDecl, target, builtInTypeScope,
				preLinkingPhase);

		addFormalParameters(functionType, functionDecl, builtInTypeScope, preLinkingPhase);
		_n4JSTypesBuilderHelper.setTypeAccessModifier(functionType, functionDecl);
		_n4JSTypesBuilderHelper.setProvidedByRuntime(functionType, functionDecl, preLinkingPhase);
		setReturnType(functionType, functionDecl, builtInTypeScope, preLinkingPhase);
		_n4JSTypeVariableTypesBuilder.addTypeParameters(functionType, functionDecl, preLinkingPhase);
		_n4JSTypesBuilderHelper.setDeclaredThisTypeFromAnnotation(functionType, functionDecl, preLinkingPhase);
		_n4JSTypesBuilderHelper.copyAnnotations(functionType, functionDecl, preLinkingPhase);
		functionType.setDeclaredAsync(functionDecl.isAsync());// TODO change to declaredAsync once the annotation is
																// gone
		functionType.setDeclaredGenerator(functionDecl.isGenerator());

		// set container
		target.getFunctions().add(functionType);
	}

	/**
	 * Creates TFunction for the given function expression and adds it to the module. Note that this method applies only
	 * to expressions that define a function, not to function type expressions that merely define a function type (the
	 * latter are represented in the AST <em>and</em> TModule by a node of type <code>FunctionTypeExpression</code> from
	 * Types.xcore).
	 * <p>
	 * Creating a TFunction for a function expression becomes a bit tricky when type inference has to be used to infer
	 * the types of one or more formal parameters and/or the return value. These are the steps involved:
	 * <ol>
	 * <li>method {@link #createTFunction(FunctionExpression,AbstractNamespace,boolean)} creates an initial TFunction in
	 * which the type of every fpar may(!) be a ComputedTypeRef and the return type may(!) be a ComputedTypeRef.
	 * ComputedTypeRefs are only used if there is no declared type available.
	 * <li>when the first(!) of these ComputedTypeRefs is resolved (and only for the first!), then method
	 * resolveTFunction(ComputedTypeRef,TFunction,FunctionExpression,BuiltInTypeScope) is invoked. This method will
	 * handle the resolution of all ComputedTypeRefs of the given TFunction in one step (to avoid unnecessary repeated
	 * inference of the expected type; note: caching does not help here, because we call judgment 'expectedTypeIn' and
	 * not 'type').
	 * </ol>
	 */
	void createTFunction(FunctionExpression functionExpr, AbstractNamespace target, boolean preLinkingPhase) {
		EObject functionDefinedType = (EObject) functionExpr
				.eGet(N4JSPackage.eINSTANCE.getTypeDefiningElement_DefinedType(), false);
		if (functionDefinedType != null && !functionDefinedType.eIsProxy()) {
			throw new IllegalStateException("TFunction already created for FunctionExpression");
		}

		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(functionExpr.eResource().getResourceSet());
		TFunction functionType = createAndLinkTFunction(functionExpr);
		_n4JSVariableStatementTypesBuilder.createImplicitArgumentsVariable(functionExpr, target, builtInTypeScope,
				preLinkingPhase);

		addFormalParametersWithInferredType(functionType, functionExpr, builtInTypeScope, preLinkingPhase);
		setReturnTypeWithInferredType(functionType, functionExpr, preLinkingPhase);
		_n4JSTypeVariableTypesBuilder.addTypeParameters(functionType, functionExpr, preLinkingPhase);
		_n4JSTypesBuilderHelper.setDeclaredThisTypeFromAnnotation(functionType, functionExpr, preLinkingPhase);

		_n4JSTypesBuilderHelper.copyAnnotations(functionType, functionExpr, preLinkingPhase);

		// set container
		target.getContainingModule().getInternalTypes().add(functionType);
	}

	/**
	 * Same as
	 * {@link AbstractFunctionDefinitionTypesBuilder#addFormalParameters(TFunction,FunctionDefinition,BuiltInTypeScope,boolean)},
	 * but uses a ComputedTypeRef as the fpar's type if the type has to be inferred.
	 */
	private void addFormalParametersWithInferredType(TFunction functionType, FunctionExpression functionExpr,
			BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {

		functionType.getFpars().addAll(
				toList(filterNull(map(functionExpr.getFpars(),
						fpar -> _n4JSFormalParameterTypesBuilder.createFormalParameter(fpar,
								TypeUtils.createDeferredTypeRef(),
								// TypeUtils.createComputedTypeRef([resolveAllComputedTypeRefsInTFunction(functionType,functionExpr,builtInTypeScope)]),
								builtInTypeScope,
								preLinkingPhase)))));
	}

	/**
	 * Same as
	 * {@link AbstractFunctionDefinitionTypesBuilder#setReturnType(TFunction,FunctionDefinition,BuiltInTypeScope,boolean)},
	 * but uses a ComputedTypeRef as the return type if the type has to be inferred.
	 */
	private void setReturnTypeWithInferredType(TFunction functionType, FunctionExpression functionExpr,
			boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			/*
			 * TODO IDE-1579 this branch skips makePromiseIfAsync. Question: could this result in 'void' as inferred
			 * return type (for an async method)?
			 */
			TypeRef copy = TypeUtils.copyWithProxies(functionExpr.getDeclaredReturnTypeRefInAST());
			functionType.setReturnTypeRef(copy != null ? copy : TypeUtils.createDeferredTypeRef());
			// note: handling of the return type of async functions not done here, see
			// TypeProcessor#handleAsyncFunctionDeclaration()
		}
	}

	private TFunction createAndLinkTFunction(FunctionDefinition functionDef) {
		TFunction functionType = this.createTFunction();
		if (functionDef instanceof FunctionDeclaration) {
			functionType.setExternal(((FunctionDeclaration) functionDef).isExternal());
		}
		functionType.setName(functionDef.getName()); // maybe null in case of function expression
		functionType.setDeclaredAsync(functionDef.isAsync()); // TODO change to declaredAsync when annotation is removed
		functionType.setDeclaredGenerator(functionDef.isGenerator());// TODO change to declaredAsync when annotation is
																		// removed

		// link
		functionType.setAstElement(functionDef);
		functionDef.setDefinedType(functionType);

		return functionType;
	}

	/**
	 * Creates a new plain instance of {@link TFunction}.
	 */
	private TFunction createTFunction() {
		return TypesFactory.eINSTANCE.createTFunction();
	}
}
