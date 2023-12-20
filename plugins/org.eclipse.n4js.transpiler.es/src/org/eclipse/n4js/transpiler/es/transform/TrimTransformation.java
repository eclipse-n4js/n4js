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
package org.eclipse.n4js.transpiler.es.transform;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.TypedElement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.Type;

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
public class TrimTransformation extends Transformation {

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
		for (EObject node : collectNodes(getState().im, false,
				TypeRef.class,
				TypeReferenceNode.class,
				N4TypeVariable.class,
				N4TypeAliasDeclaration.class)) {

			removeNode(node);
		}
	}

	private void removeNode(EObject nodeInIM) {
		EObject actualNodeToRemove = TranspilerUtils.orContainingExportDeclaration(nodeInIM);
		remove(actualNodeToRemove);
	}
}
