/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform

import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.types.TFunction

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Implicit return types are made explicit.
 */
class ReturnTypeTransformation extends Transformation {

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, FunctionDefinition, false).forEach[makeReturnTypeExplicit];
	}

	def private void makeReturnTypeExplicit(FunctionDefinition funDef) {
		if (funDef.declaredReturnTypeRefNode !== null) {
			// return type already provided explicitly, so nothing to do here
			return;
		}
		
		val elemInAST = state.tracer.getOriginalASTNode(funDef) as FunctionDefinition;
		val tFunction = elemInAST.definedType as TFunction;
		val typeRefNode = _TypeReferenceNode(state, tFunction.returnTypeRef);
		funDef.declaredReturnTypeRefNode = typeRefNode;
	}
}
