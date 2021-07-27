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

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.assistants.TypeAssistant

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Implicit return types are made explicit.
 */
class ReturnTypeTransformation extends Transformation {
	
	@Inject
	private TypeAssistant typeAssistant;

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
		
		val returnTypeRef = typeAssistant.getReturnTypeRef(state, funDef);
		val typeRefNode = _TypeReferenceNode(state, returnTypeRef);
		funDef.declaredReturnTypeRefNode = typeRefNode;
	}
}
