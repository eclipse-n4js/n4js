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

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * The implicit return type is 'void' in N4JS but 'any' in TypeScript, so we have to explicitly emit 'void'
 * whenever the return type is not explicitly given.
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
		val typeRefNode = _TypeReferenceNode(state, state.G.voidTypeRef);
		funDef.declaredReturnTypeRefNode = typeRefNode;
	}
}
