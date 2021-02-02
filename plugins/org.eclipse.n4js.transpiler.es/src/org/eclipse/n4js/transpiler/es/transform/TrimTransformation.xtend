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
package org.eclipse.n4js.transpiler.es.transform

import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration
import org.eclipse.n4js.n4JS.N4TypeVariable
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument
import org.eclipse.n4js.n4JS.TypeReferenceInAST
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.Type

/**
 * Removes all nodes from the intermediate model that are not required in the final output. Since such nodes might
 * provide important information to other transformations, this transformation should run rather late.
 * <p>
 * Examples of removed stuff:
 * <ul>
 * <li>declared type references of {@link TypedElement}s
 * <li>type variable references of {@link Type}
 * <li>type alias declarations
 * </ul>
 * Note: cast expressions are already removed by {@link ExpressionTransformation}.
 */
class TrimTransformation extends Transformation {


	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		val toBeRemoved = newArrayList;
		val iter = state.im.eAllContents;
		while (iter.hasNext) {
			val curr = iter.next;
			if (curr instanceof TypeRef
				|| curr instanceof TypeReferenceInAST
				|| curr instanceof N4TypeVariable
				|| curr instanceof N4TypeAliasDeclaration) {

				toBeRemoved += curr;
				iter.prune();
			} else if (curr instanceof N4TypeVariable) {
				curr.declaredUpperBound = null; // cross-reference -> won't confuse tree iterator
			} else if (curr instanceof CastExpression) {
				curr.targetTypeRef = null; // cross-reference -> won't confuse tree iterator
			} else if (curr instanceof TypeRefAnnotationArgument) {
				curr.typeRef = null; // cross-reference -> won't confuse tree iterator
			} else {
				if (curr instanceof TypedElement) {
					curr.declaredTypeRef = null; // cross-reference -> won't confuse tree iterator
				}
				if (curr instanceof FunctionDefinition) {
					curr.declaredReturnTypeRef = null; // cross-reference -> won't confuse tree iterator
				}
			}
		}

		for (obj : toBeRemoved) {
			remove(obj);
		}
	}
}
