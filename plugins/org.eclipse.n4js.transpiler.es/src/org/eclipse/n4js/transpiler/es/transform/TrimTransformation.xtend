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

import java.util.List
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration
import org.eclipse.n4js.n4JS.TypeReferenceInAST
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeVariable

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
		// FIXME do not iterate as often!!!
		// 1) remove all typeRefs
		collectNodes(state.im, TypeRef, false).forEach[remove(it)];
		((collectNodes(state.im, TypeReferenceInAST, false) as Object) as List<TypeReferenceInAST<?>>).forEach[remove(it)]; // cast required to avoid a warning
		collectNodes(state.im, TypedElement, true).forEach[it.declaredTypeRef = null];
		collectNodes(state.im, FunctionDefinition, true).forEach[it.declaredReturnTypeRef = null];
		collectNodes(state.im, CastExpression, true).forEach[it.targetTypeRef = null];
		// 2) remove all type typeVars:
		collectNodes(state.im, TypeVariable, false).forEach[remove(it)]
		// 3) remove all type alias declarations
		collectNodes(state.im, N4TypeAliasDeclaration, false).forEach[remove(it)]
	}
}
