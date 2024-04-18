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
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;

import com.google.inject.Inject;

/**
 */
class N4JSGetterTypesBuilder extends AbstractFunctionDefinitionTypesBuilder {

	@Inject
	N4JSVariableStatementTypesBuilder _n4JSVariableStatementTypesBuilder;
	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	boolean canCreate(N4GetterDeclaration n4Getter) {
		return n4Getter.getName() != null || n4Getter.hasComputedPropertyName();
	}

	boolean relinkGetter(N4GetterDeclaration n4Getter, TGetter tGetter,
			@SuppressWarnings("unused") boolean preLinkingPhase) {

		if (!canCreate(n4Getter)) {
			return false;
		}

		_n4JSTypesBuilderHelper.ensureEqualName(n4Getter, tGetter);
		tGetter.setAstElement(n4Getter);
		n4Getter.setDefinedGetter(tGetter);
		return true;
	}

	TGetter createGetter(N4GetterDeclaration n4Getter, @SuppressWarnings("unused") TClassifier classifierType,
			AbstractNamespace target, boolean preLinkingPhase) {

		if (!canCreate(n4Getter)) {
			return null;
		}
		TGetter getterType = TypesFactory.eINSTANCE.createTGetter();
		_n4JSTypesBuilderHelper.setMemberName(getterType, n4Getter);

		getterType.setDeclaredAbstract(n4Getter.isAbstract());
		getterType.setDeclaredStatic(n4Getter.isDeclaredStatic());
		getterType.setDeclaredFinal(n4Getter.isDeclaredFinal());
		getterType.setOptional(n4Getter.isOptional());
		getterType.setDeclaredOverride(AnnotationDefinition.OVERRIDE.hasAnnotation(n4Getter));

		getterType.setHasNoBody(n4Getter.getBody() == null
				&& !AnnotationDefinition.PROVIDES_DEFAULT_IMPLEMENTATION.hasAnnotation(n4Getter));

		// TODO if possible, remove, see AbstractFunctionDefinitionTypesBuilder
		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(n4Getter.eResource().getResourceSet());
		_n4JSVariableStatementTypesBuilder.createImplicitArgumentsVariable(n4Getter, target, builtInTypeScope,
				preLinkingPhase);

		setMemberAccessModifier(getterType, n4Getter);
		setReturnTypeConsideringThis(getterType, n4Getter, builtInTypeScope, preLinkingPhase);
		_n4JSTypesBuilderHelper.setDeclaredThisTypeFromAnnotation(getterType, n4Getter, preLinkingPhase);

		_n4JSTypesBuilderHelper.copyAnnotations(getterType, n4Getter, preLinkingPhase);

		getterType.setAstElement(n4Getter);
		n4Getter.setDefinedGetter(getterType);
		return getterType;
	}

	private void setMemberAccessModifier(TGetter getterType, N4GetterDeclaration n4Getter) {
		_n4JSTypesBuilderHelper.setMemberAccessModifier(
				modifier -> getterType.setDeclaredMemberAccessModifier(modifier),
				n4Getter.getDeclaredModifiers(), n4Getter.getAnnotations());
	}

	/**
	 * Sets the return type. If the declared return type is 'this', a ComputedTypeRef will be created to generate a
	 * bound this type.
	 */
	private void setReturnTypeConsideringThis(TGetter getterType, N4GetterDeclaration getterDecl,
			BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		// TODO: explicitly differentiate between declared and inferred type
		if (getterDecl.getDeclaredTypeRefInAST() instanceof ThisTypeRef) {
			// special case: TypingASTWalker will create a BoundThisTypeRef via Xsemantics judgment 'thisTypeRef'
			getterType.setTypeRef(TypeUtils.createDeferredTypeRef());
		} else {
			// standard case
			setReturnType(getterType, getterDecl, builtInTypeScope, preLinkingPhase);
		}
	}
}
