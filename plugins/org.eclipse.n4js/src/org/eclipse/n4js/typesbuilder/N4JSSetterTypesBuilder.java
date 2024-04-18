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

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypesFactory;

import com.google.inject.Inject;

/**
 */
class N4JSSetterTypesBuilder {

	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;
	@Inject
	N4JSFormalParameterTypesBuilder _n4JSFormalParameterTypesBuilder;
	@Inject
	N4JSVariableStatementTypesBuilder _n4JSVariableStatementTypesBuilder;

	boolean canCreate(N4SetterDeclaration n4Setter) {
		return n4Setter.getName() != null || n4Setter.hasComputedPropertyName();
	}

	boolean relinkSetter(N4SetterDeclaration n4Setter, TSetter tSetter,
			@SuppressWarnings("unused") boolean preLinkingPhase) {
		if (!canCreate(n4Setter)) {
			return false;
		}

		_n4JSTypesBuilderHelper.ensureEqualName(n4Setter, tSetter);
		linkFormalParameters(tSetter, n4Setter);

		tSetter.setAstElement(n4Setter);
		n4Setter.setDefinedSetter(tSetter);
		return true;
	}

	TSetter createSetter(N4SetterDeclaration n4Setter, @SuppressWarnings("unused") TClassifier classifierType,
			AbstractNamespace target,
			boolean preLinkingPhase) {
		if (!canCreate(n4Setter)) {
			return null;
		}

		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(n4Setter.eResource().getResourceSet());
		_n4JSVariableStatementTypesBuilder.createImplicitArgumentsVariable(n4Setter, target, builtInTypeScope,
				preLinkingPhase);

		TSetter setterType = TypesFactory.eINSTANCE.createTSetter();
		_n4JSTypesBuilderHelper.setMemberName(setterType, n4Setter);
		setterType.setDeclaredAbstract(n4Setter.isAbstract());
		setterType.setDeclaredStatic(n4Setter.isDeclaredStatic());
		setterType.setDeclaredFinal(n4Setter.isDeclaredFinal());
		setterType.setOptional(n4Setter.isOptional());
		setterType.setDeclaredOverride(AnnotationDefinition.OVERRIDE.hasAnnotation(n4Setter));

		setterType.setHasNoBody(n4Setter.getBody() == null
				&& !AnnotationDefinition.PROVIDES_DEFAULT_IMPLEMENTATION.hasAnnotation(n4Setter));

		setMemberAccessModifier(setterType, n4Setter);
		addFormalParameters(setterType, n4Setter, builtInTypeScope, preLinkingPhase);
		_n4JSTypesBuilderHelper.setDeclaredThisTypeFromAnnotation(setterType, n4Setter, preLinkingPhase);

		_n4JSTypesBuilderHelper.copyAnnotations(setterType, n4Setter, preLinkingPhase);

		setterType.setAstElement(n4Setter);
		n4Setter.setDefinedSetter(setterType);
		return setterType;
	}

	private void setMemberAccessModifier(TSetter setterType, N4SetterDeclaration n4Setter) {
		_n4JSTypesBuilderHelper.setMemberAccessModifier(
				modifier -> setterType.setDeclaredMemberAccessModifier(modifier),
				n4Setter.getDeclaredModifiers(), n4Setter.getAnnotations());
	}

	private void addFormalParameters(TSetter setterType, N4SetterDeclaration n4Setter,
			BuiltInTypeScope builtInTypeScope,
			boolean preLinkingPhase) {
		if (n4Setter.getFpar() != null) {
			setterType.setFpar(_n4JSFormalParameterTypesBuilder.createFormalParameter(n4Setter.getFpar(),
					builtInTypeScope, preLinkingPhase));
		}
	}

	private boolean linkFormalParameters(TSetter setterType, N4SetterDeclaration n4Setter) {
		if (n4Setter.getFpar() == null) {
			return false;
		}
		TFormalParameter formalParameterType = setterType.getFpar();
		_n4JSTypesBuilderHelper.ensureEqualName(n4Setter.getFpar(), formalParameterType);
		formalParameterType.setAstElement(n4Setter.getFpar());
		n4Setter.getFpar().setDefinedVariable(formalParameterType);
		return true;
	}

}
