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
package org.eclipse.n4js.transpiler.dts.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._TypeReferenceNode;

import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.ts.typeRefs.TypeRef;

import com.google.inject.Inject;

/**
 * Implicit return types are made explicit.
 */
public class ReturnTypeTransformation extends Transformation {

	@Inject
	private TypeAssistant typeAssistant;

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		for (FunctionDefinition fDef : collectNodes(getState().im, FunctionDefinition.class, false)) {
			makeReturnTypeExplicit(fDef);
		}
	}

	private void makeReturnTypeExplicit(FunctionDefinition funDef) {
		if (funDef.getDeclaredReturnTypeRefNode() != null) {
			// return type already provided explicitly, so nothing to do here
			return;
		}

		TypeRef returnTypeRef = typeAssistant.getReturnTypeRef(getState(), funDef);
		TypeReferenceNode<TypeRef> typeRefNode = _TypeReferenceNode(getState(), returnTypeRef);
		funDef.setDeclaredReturnTypeRefNode(typeRefNode);
	}
}
