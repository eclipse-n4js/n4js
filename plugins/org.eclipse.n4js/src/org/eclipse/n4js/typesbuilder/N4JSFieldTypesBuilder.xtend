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
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils

package class N4JSFieldTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper

	def package boolean relinkField(N4FieldDeclaration n4Field, TClassifier classifierType, boolean preLinkingPhase, int idx) {
		if (n4Field.name === null && !n4Field.hasComputedPropertyName)
			return false;

		val field = classifierType.ownedMembers.get(idx) as TField
		ensureEqualName(n4Field, field);
		field.astElement = n4Field;
		n4Field.definedField = field

		return true;
	}

	def package TField createField(N4FieldDeclaration n4Field, TClassifier classifierType, boolean preLinkingPhase) {
		if (n4Field.name === null && !n4Field.hasComputedPropertyName)
			return null;

		val field = TypesFactory::eINSTANCE.createTField();
		field.setMemberName(n4Field);
		field.const = n4Field.const
		field.declaredStatic = n4Field.declaredStatic;
		field.declaredFinal = n4Field.declaredFinal;
		field.optional = n4Field.declaredOptional;
		field.declaredOverride = AnnotationDefinition.OVERRIDE.hasAnnotation(n4Field);

		val providesInitializer = AnnotationDefinition.PROVIDES_INITIALZER.hasAnnotation(n4Field);
		field.hasExpression = n4Field.expression!==null || providesInitializer;

		field.copyAnnotations(n4Field, preLinkingPhase)

		field.setMemberAccessModifier(n4Field)
		field.setFieldType(n4Field, preLinkingPhase)

		field.astElement = n4Field;
		n4Field.definedField = field

		return field;
	}

	def private void setFieldType(TField field, N4FieldDeclaration n4Field, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			if(n4Field.declaredTypeRefInAST!==null) {
				// type of field was declared explicitly
				field.typeRef = TypeUtils.copyWithProxies(n4Field.declaredTypeRefInAST)
			}
			else {
				// in all other cases:
				// leave it to the TypingASTWalker to infer the type (e.g. from the initializer expression, if given)
				field.typeRef = TypeUtils.createDeferredTypeRef;
			}
		}
	}

	def private void setMemberAccessModifier(TField fieldType, N4FieldDeclaration n4Field) {
		setMemberAccessModifier([MemberAccessModifier modifier | fieldType.declaredMemberAccessModifier = modifier],
			n4Field.declaredModifiers, n4Field.annotations)
	}
}
