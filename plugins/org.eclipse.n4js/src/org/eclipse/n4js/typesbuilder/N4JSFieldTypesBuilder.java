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
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;

import com.google.inject.Inject;

class N4JSFieldTypesBuilder {

	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	boolean canCreate(N4FieldDeclaration n4Field) {
		return n4Field.getName() != null || n4Field.hasComputedPropertyName();
	}

	boolean relinkField(N4FieldDeclaration n4Field, TField tField,
			@SuppressWarnings("unused") boolean preLinkingPhase) {
		if (!canCreate(n4Field)) {
			return false;
		}

		_n4JSTypesBuilderHelper.ensureEqualName(n4Field, tField);
		tField.setAstElement(n4Field);
		n4Field.setDefinedField(tField);

		return true;
	}

	TField createField(N4FieldDeclaration n4Field, @SuppressWarnings("unused") TClassifier classifierType,
			boolean preLinkingPhase) {
		if (!canCreate(n4Field)) {
			return null;
		}

		TField field = TypesFactory.eINSTANCE.createTField();
		_n4JSTypesBuilderHelper.setMemberName(field, n4Field);
		field.setConst(n4Field.isConst());
		field.setDeclaredStatic(n4Field.isDeclaredStatic());
		field.setDeclaredFinal(n4Field.isDeclaredFinal());
		field.setOptional(n4Field.isDeclaredOptional());
		field.setDeclaredOverride(AnnotationDefinition.OVERRIDE.hasAnnotation(n4Field));

		boolean providesInitializer = AnnotationDefinition.PROVIDES_INITIALZER.hasAnnotation(n4Field);
		field.setHasExpression(n4Field.getExpression() != null || providesInitializer);

		_n4JSTypesBuilderHelper.copyAnnotations(field, n4Field, preLinkingPhase);

		setMemberAccessModifier(field, n4Field);
		setFieldType(field, n4Field, preLinkingPhase);

		field.setAstElement(n4Field);
		n4Field.setDefinedField(field);

		return field;
	}

	private void setFieldType(TField field, N4FieldDeclaration n4Field, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			if (n4Field.getDeclaredTypeRefInAST() != null) {
				// type of field was declared explicitly
				field.setTypeRef(TypeUtils.copyWithProxies(n4Field.getDeclaredTypeRefInAST()));
			} else {
				// in all other cases:
				// leave it to the TypingASTWalker to infer the type (e.g. from the initializer expression, if given)
				field.setTypeRef(TypeUtils.createDeferredTypeRef());
			}
		}
	}

	private void setMemberAccessModifier(TField fieldType, N4FieldDeclaration n4Field) {
		_n4JSTypesBuilderHelper.setMemberAccessModifier(
				(modifier) -> fieldType.setDeclaredMemberAccessModifier(modifier),
				n4Field.getDeclaredModifiers(), n4Field.getAnnotations());
	}
}
