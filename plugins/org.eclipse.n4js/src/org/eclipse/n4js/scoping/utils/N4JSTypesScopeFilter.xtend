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
package org.eclipse.n4js.scoping.utils

import org.eclipse.n4js.ts.scoping.TypesScopeFilter
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.PropertyAssignment
import org.eclipse.n4js.n4JS.FormalParameter

/**
 * Poor mans filter, for content assist more elaborated solutions are necessary anyway. Reduces the number of elements of
 * a scope returned by getAllElements, although scope might contain more elements. Simplifies tests etc. as less elements
 * are to be checked.
 */
class N4JSTypesScopeFilter extends TypesScopeFilter {

	override protected getTypeReferenceFilterCriteria(EObject container, EReference containmentFeature) {
		switch(container) {
			FunctionDefinition:
				returnTypeCriteria
			N4FieldDeclaration:
				fieldTypeCriteria
			VariableDeclaration:
				fieldTypeCriteria
			PropertyAssignment:
				fieldTypeCriteria
			FormalParameter:
				parameterTypeCriteria
			default:
				super.getTypeReferenceFilterCriteria(container, containmentFeature)
		}
	}

}
