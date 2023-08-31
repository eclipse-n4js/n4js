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
import com.google.inject.Singleton
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.SuperLiteral
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.N4JSLanguageUtils

@Singleton
package class N4JSMethodTypesBuilder extends AbstractFunctionDefinitionTypesBuilder {

	@Inject extension N4JSTypeVariableTypesBuilder
	@Inject extension N4JSVariableStatementTypesBuilder
	@Inject extension N4JSTypesBuilderHelper
	
	def boolean canCreate(N4MethodDeclaration methodDecl) {
		val methodDefinedType = methodDecl.eGet(N4JSPackage.eINSTANCE.typeDefiningElement_DefinedType, false) as EObject;
		if (methodDefinedType !== null && !methodDefinedType.eIsProxy) {
			throw new IllegalStateException("TMethod already created for N4MethodDeclaration");
		}
		if (methodDecl.name === null && !methodDecl.hasComputedPropertyName && !methodDecl.callSignature) {
			return false;
		}
		return true;
	}

	def package boolean relinkMethod(N4MethodDeclaration methodDecl, TClassifier classifier, boolean preLinkingPhase, int idx) {
		if (!canCreate(methodDecl)) {
			return false;
		}
		if (!hasValidName(methodDecl)) {
			return false;
		}
		relinkMethod(methodDecl, classifier.ownedMembers.get(idx) as TMethod, preLinkingPhase);
	}

	def package boolean relinkMethod(N4MethodDeclaration methodDecl, TMethod tMethod, boolean preLinkingPhase) {
		val methodType = tMethod;
		ensureEqualName(methodDecl, methodType);

		methodType.relinkFormalParameters(methodDecl, preLinkingPhase)

		// link
		methodType.astElement = methodDecl
		methodDecl.definedType = methodType

		return true;
	}

	/**
	 * Creates TMethod for the given method declaration (and links it to that method).
	 *
	 * @param methodDecl declaration for which the TMethod is created, must not be linked to a TMethod yet (i.e. its defined type must be null).
	 * @param preLinkingPhase
	 */
	def package TMethod createMethod(N4MethodDeclaration methodDecl, AbstractNamespace target, boolean preLinkingPhase) {
		if (!canCreate(methodDecl)) {
			return null;
		}
		val methodType = TypesFactory::eINSTANCE.createTMethod();
		if (methodDecl.isCallSignature) {
			methodType.name = N4JSLanguageUtils.CALL_SIGNATURE_NAME;
		} else {
			methodType.setMemberName(methodDecl);
		}
		methodType.declaredAbstract = methodDecl.abstract
		methodType.declaredStatic = methodDecl.declaredStatic
		methodType.declaredFinal = methodDecl.declaredFinal
		methodType.declaredOverride = AnnotationDefinition.OVERRIDE.hasAnnotation(methodDecl);
		methodType.constructor = methodDecl.constructor
		methodType.declaredAsync = methodDecl.async
		methodType.declaredGenerator = methodDecl.generator

		val providesDefaultImpl = AnnotationDefinition.PROVIDES_DEFAULT_IMPLEMENTATION.hasAnnotation(methodDecl);
		methodType.hasNoBody = methodDecl.body===null && !providesDefaultImpl;

		methodType.lacksThisOrSuperUsage = hasNonNullBody(methodDecl.body) && !containsThisOrSuperUsage(methodDecl.body)

		val builtInTypeScope = BuiltInTypeScope.get(methodDecl.eResource.resourceSet)
		methodDecl.createImplicitArgumentsVariable(target, builtInTypeScope, preLinkingPhase);

		methodType.setMemberAccessModifier(methodDecl)
		methodType.addTypeParameters(methodDecl, preLinkingPhase)
		methodType.addFormalParameters(methodDecl, builtInTypeScope, preLinkingPhase)
		methodType.setReturnTypeConsideringThis(methodDecl, builtInTypeScope, preLinkingPhase)
		methodType.setDeclaredThisTypeFromAnnotation(methodDecl, preLinkingPhase)

		methodType.copyAnnotations(methodDecl, preLinkingPhase)

		// link
		methodType.astElement = methodDecl
		methodDecl.definedType = methodType

		return methodType;
	}

	def private void setMemberAccessModifier(TMethod methodType, N4MethodDeclaration n4Method) {
		setMemberAccessModifier([MemberAccessModifier modifier|methodType.declaredMemberAccessModifier = modifier],
			n4Method.declaredModifiers, n4Method.annotations)
	}

	/**
	 * Sets the return type. If the declared return type is 'this', a ComputedTypeRef will
	 * be created to generate a bound this type.
	 */
	def private void setReturnTypeConsideringThis(TMethod methodType, N4MethodDeclaration methodDecl,
		BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		if (methodDecl.isConstructor || methodDecl.declaredReturnTypeRefInAST instanceof ThisTypeRef) {
			// special case: TypeDeferredProcessor will create a BoundThisTypeRef via Xsemantics judgment 'thisTypeRef'
			methodType.returnTypeRef = TypeUtils.createDeferredTypeRef
		} else {
			// standard case
			methodType.setReturnType(methodDecl, builtInTypeScope, preLinkingPhase)
		}
	}

	private def boolean hasNonNullBody(Block body) {
		(null !== body) &&
		(null !== body.allStatements)
	}

	/**
	 * Checks for the presence of 'this' or 'super' usages in the given body,
	 * also including sub-expressions (eg, 'if (sub-expr)'),
	 * without delving inside function definitions or declarations.
	 * <p>
	 * Static methods refer to static members via ThisLiteral.
	 */
	private def boolean containsThisOrSuperUsage(Block body) {
		body.allStatements.exists[ stmt |
			isThisOrSuperUsage(stmt) ||
			EcoreUtilN4.getAllContentsFiltered(stmt, [!isFnDefOrDecl(it)]).exists[isThisOrSuperUsage(it)];
		]
	}

	private def boolean isFnDefOrDecl(EObject ast) {
		ast instanceof FunctionDeclaration || ast instanceof FunctionDefinition
	}

	private def boolean isThisOrSuperUsage(EObject expr) {
		expr instanceof SuperLiteral || expr instanceof ThisLiteral
	}
}
