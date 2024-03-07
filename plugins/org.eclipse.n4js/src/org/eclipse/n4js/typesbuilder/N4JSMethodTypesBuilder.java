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

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.exists;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
class N4JSMethodTypesBuilder extends AbstractFunctionDefinitionTypesBuilder {

	@Inject
	N4JSTypeVariableTypesBuilder _n4JSTypeVariableTypesBuilder;
	@Inject
	N4JSVariableStatementTypesBuilder _n4JSVariableStatementTypesBuilder;
	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	boolean canCreate(N4MethodDeclaration methodDecl) {
		EObject methodDefinedType = (EObject) methodDecl.eGet(
				N4JSPackage.eINSTANCE.getTypeDefiningElement_DefinedType(),
				false);
		if (methodDefinedType != null && !methodDefinedType.eIsProxy()) {
			throw new IllegalStateException("TMethod already created for N4MethodDeclaration");
		}
		if (methodDecl.getName() == null && !methodDecl.hasComputedPropertyName() && !methodDecl.isCallSignature()) {
			return false;
		}
		return true;
	}

	boolean relinkMethod(N4MethodDeclaration methodDecl, TClassifier classifier, boolean preLinkingPhase, int idx) {
		if (!canCreate(methodDecl)) {
			return false;
		}
		return relinkMethod(methodDecl, (TMethod) classifier.getOwnedMembers().get(idx), preLinkingPhase);
	}

	boolean relinkMethod(N4MethodDeclaration methodDecl, TMethod tMethod, boolean preLinkingPhase) {
		TMethod methodType = tMethod;
		_n4JSTypesBuilderHelper.ensureEqualName(methodDecl, methodType);

		relinkFormalParameters(methodType, methodDecl, preLinkingPhase);

		// link
		methodType.setAstElement(methodDecl);
		methodDecl.setDefinedType(methodType);

		return true;
	}

	/**
	 * Creates TMethod for the given method declaration (and links it to that method).
	 *
	 * @param methodDecl
	 *            declaration for which the TMethod is created, must not be linked to a TMethod yet (i.e. its defined
	 *            type must be null).
	 */
	TMethod createMethod(N4MethodDeclaration methodDecl, AbstractNamespace target, boolean preLinkingPhase) {
		if (!canCreate(methodDecl)) {
			return null;
		}
		TMethod methodType = TypesFactory.eINSTANCE.createTMethod();
		if (methodDecl.isCallSignature()) {
			methodType.setName(N4JSLanguageUtils.CALL_SIGNATURE_NAME);
		} else {
			_n4JSTypesBuilderHelper.setMemberName(methodType, methodDecl);
		}
		methodType.setDeclaredAbstract(methodDecl.isAbstract());
		methodType.setDeclaredStatic(methodDecl.isDeclaredStatic());
		methodType.setDeclaredFinal(methodDecl.isDeclaredFinal());
		methodType.setDeclaredOverride(AnnotationDefinition.OVERRIDE.hasAnnotation(methodDecl));
		methodType.setConstructor(methodDecl.isConstructor());
		methodType.setDeclaredAsync(methodDecl.isAsync());
		methodType.setDeclaredGenerator(methodDecl.isGenerator());

		boolean providesDefaultImpl = AnnotationDefinition.PROVIDES_DEFAULT_IMPLEMENTATION.hasAnnotation(methodDecl);
		methodType.setHasNoBody(methodDecl.getBody() == null && !providesDefaultImpl);

		methodType.setLacksThisOrSuperUsage(
				hasNonNullBody(methodDecl.getBody()) && !containsThisOrSuperUsage(methodDecl.getBody()));

		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(methodDecl.eResource().getResourceSet());
		_n4JSVariableStatementTypesBuilder.createImplicitArgumentsVariable(methodDecl, target, builtInTypeScope,
				preLinkingPhase);

		setMemberAccessModifier(methodType, methodDecl);
		_n4JSTypeVariableTypesBuilder.addTypeParameters(methodType, methodDecl, preLinkingPhase);
		addFormalParameters(methodType, methodDecl, builtInTypeScope, preLinkingPhase);
		setReturnTypeConsideringThis(methodType, methodDecl, builtInTypeScope, preLinkingPhase);
		_n4JSTypesBuilderHelper.setDeclaredThisTypeFromAnnotation(methodType, methodDecl, preLinkingPhase);

		_n4JSTypesBuilderHelper.copyAnnotations(methodType, methodDecl, preLinkingPhase);

		// link
		methodType.setAstElement(methodDecl);
		methodDecl.setDefinedType(methodType);

		return methodType;
	}

	private void setMemberAccessModifier(TMethod methodType, N4MethodDeclaration n4Method) {
		_n4JSTypesBuilderHelper.setMemberAccessModifier(
				modifier -> methodType.setDeclaredMemberAccessModifier(modifier),
				n4Method.getDeclaredModifiers(), n4Method.getAnnotations());
	}

	/**
	 * Sets the return type. If the declared return type is 'this', a ComputedTypeRef will be created to generate a
	 * bound this type.
	 */
	private void setReturnTypeConsideringThis(TMethod methodType, N4MethodDeclaration methodDecl,
			BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		if (methodDecl.isConstructor() || methodDecl.getDeclaredReturnTypeRefInAST() instanceof ThisTypeRef) {
			// special case: TypeDeferredProcessor will create a BoundThisTypeRef via Xsemantics judgment 'thisTypeRef'
			methodType.setReturnTypeRef(TypeUtils.createDeferredTypeRef());
		} else {
			// standard case
			setReturnType(methodType, methodDecl, builtInTypeScope, preLinkingPhase);
		}
	}

	private boolean hasNonNullBody(Block body) {
		return (null != body) && (null != body.getAllStatements());
	}

	/**
	 * Checks for the presence of 'this' or 'super' usages in the given body, also including sub-expressions (eg, 'if
	 * (sub-expr)'), without delving inside function definitions or declarations.
	 * <p>
	 * Static methods refer to static members via ThisLiteral.
	 */
	private boolean containsThisOrSuperUsage(Block body) {
		return exists(body.getAllStatements(), stmt -> isThisOrSuperUsage(stmt) ||
				exists(EcoreUtilN4.getAllContentsFiltered(stmt, s -> !isFnDefOrDecl(s)), s -> isThisOrSuperUsage(s)));
	}

	private boolean isFnDefOrDecl(EObject ast) {
		return ast instanceof FunctionDeclaration || ast instanceof FunctionDefinition;
	}

	private boolean isThisOrSuperUsage(EObject expr) {
		return expr instanceof SuperLiteral || expr instanceof ThisLiteral;
	}
}
