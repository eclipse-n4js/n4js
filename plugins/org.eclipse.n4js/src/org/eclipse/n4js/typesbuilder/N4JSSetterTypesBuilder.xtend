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
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TypesFactory

/**
 */
package class N4JSSetterTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper
	@Inject extension N4JSFormalParameterTypesBuilder
	@Inject extension N4JSVariableStatementTypesBuilder
	
	def boolean canCreate(N4SetterDeclaration n4Setter) {
		return n4Setter.name !== null || n4Setter.hasComputedPropertyName;
	}

	def package boolean relinkSetter(N4SetterDeclaration n4Setter, TClassifier classifierType, boolean preLinkingPhase, int idx) {
		if (!canCreate(n4Setter)) {
			return false
		}
		if (!hasValidName(n4Setter)) {
			return false;
		}

		val setterType = classifierType.ownedMembers.get(idx) as TSetter;
		ensureEqualName(n4Setter, setterType);
		setterType.linkFormalParameters(n4Setter, preLinkingPhase)

		setterType.astElement = n4Setter
		n4Setter.definedSetter = setterType
		return true
	}

	def package TSetter createSetter(N4SetterDeclaration n4Setter, TClassifier classifierType, AbstractNamespace target, boolean preLinkingPhase) {
		if (!canCreate(n4Setter)) {
			return null
		}

		val builtInTypeScope = BuiltInTypeScope.get(n4Setter.eResource.resourceSet)
		n4Setter.createImplicitArgumentsVariable(target, builtInTypeScope, preLinkingPhase);

		val setterType = TypesFactory::eINSTANCE.createTSetter
		setterType.setMemberName(n4Setter);
		setterType.declaredAbstract = n4Setter.abstract
		setterType.declaredStatic = n4Setter.declaredStatic
		setterType.declaredFinal = n4Setter.declaredFinal
		setterType.optional = n4Setter.optional;
		setterType.declaredOverride = AnnotationDefinition.OVERRIDE.hasAnnotation(n4Setter);

		setterType.hasNoBody = n4Setter.body===null && !AnnotationDefinition.PROVIDES_DEFAULT_IMPLEMENTATION.hasAnnotation(n4Setter);

		setterType.setMemberAccessModifier(n4Setter)
		setterType.addFormalParameters(n4Setter, builtInTypeScope, preLinkingPhase)
		setterType.setDeclaredThisTypeFromAnnotation(n4Setter, preLinkingPhase)

		setterType.copyAnnotations(n4Setter, preLinkingPhase)

		setterType.astElement = n4Setter
		n4Setter.definedSetter = setterType
		setterType;
	}

	def private void setMemberAccessModifier(TSetter setterType, N4SetterDeclaration n4Setter) {
		setMemberAccessModifier([MemberAccessModifier modifier|setterType.declaredMemberAccessModifier = modifier],
			n4Setter.declaredModifiers, n4Setter.annotations)
	}

	def private void addFormalParameters(TSetter setterType, N4SetterDeclaration n4Setter, BuiltInTypeScope builtInTypeScope,
		boolean preLinkingPhase) {
		if (n4Setter.fpar !== null)
			setterType.fpar = n4Setter.fpar.createFormalParameter(builtInTypeScope, preLinkingPhase);
	}
	
	def private boolean linkFormalParameters(TSetter setterType, N4SetterDeclaration n4Setter, boolean preLinkingPhase) {
		if (n4Setter.fpar === null) {
			return false;
		}
		val formalParameterType = setterType.fpar;
		ensureEqualName(n4Setter.fpar, formalParameterType);
		formalParameterType.astElement = n4Setter.fpar;
		n4Setter.fpar.definedVariable = formalParameterType;
		return true;
	}

}
