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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.dts.utils.DtsUtils;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.Type;

/**
 * Remove from extends/implements clauses of classifiers all references to types that are not .d.ts exportable.
 */
public class CutOffTransformation extends Transformation {

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
		for (N4ClassifierDeclaration decl : collectNodes(getState().im, N4ClassifierDeclaration.class, false)) {
			cutOffReferencesInExtendsImplements(decl);
		}
	}

	private void cutOffReferencesInExtendsImplements(N4ClassifierDeclaration classifierDecl) {
		List<TypeReferenceNode<ParameterizedTypeRef>> toBeRemoved = new ArrayList<>();
		for (TypeReferenceNode<ParameterizedTypeRef> superTypeRefNode : classifierDecl.getSuperClassifierRefs()) {
			TypeRef superTypeRef = getState().info.getOriginalProcessedTypeRef(superTypeRefNode);
			Type superDeclType = superTypeRef == null ? null : superTypeRef.getDeclaredType();
			if (!DtsUtils.isDtsExportableReference(superDeclType, getState())) {
				toBeRemoved.add(superTypeRefNode);
			}
		}
		for (TypeReferenceNode<ParameterizedTypeRef> tbr : toBeRemoved) {
			remove(tbr);
		}
	}
}
