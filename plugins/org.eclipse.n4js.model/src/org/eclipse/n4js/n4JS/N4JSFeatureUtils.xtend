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
package org.eclipse.n4js.n4JS

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.ts.types.IdentifiableElement

import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.*
import static org.eclipse.n4js.ts.types.TypesPackage.Literals.*

/**
 * Static utility methods for retrieving features of AST elements.
 */
class N4JSFeatureUtils {

	/**
	 * Returns attribute feature actually holding the name or null, if no such attribute exists.
	 */
	public static def EStructuralFeature attributeOfNameFeature(EObject elementWithName) {
		switch (elementWithName) {
			Annotation: ANNOTATION__NAME
			FunctionDeclaration: FUNCTION_DECLARATION__NAME
			FunctionExpression: FUNCTION_EXPRESSION__NAME
			LabelledStatement: LABELLED_STATEMENT__NAME
			N4TypeDeclaration: N4_TYPE_DECLARATION__NAME
			N4ClassExpression: N4_CLASS_EXPRESSION__NAME
			N4EnumLiteral: N4_ENUM_LITERAL__NAME
			PropertyNameOwner: PROPERTY_NAME_OWNER__DECLARED_NAME
			IdentifiableElement: IDENTIFIABLE_ELEMENT__NAME
			default: null
		}
	}
}
