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

import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.dts.utils.DtsUtils

/**
 * Remove from extends/implements clauses of classifiers all references to types that are not .d.ts exportable.
 */
class CutOffTransformation extends Transformation {
	
	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, N4ClassifierDeclaration, false).forEach[cutOffReferencesInExtendsImplements];
	}

	def private void cutOffReferencesInExtendsImplements(N4ClassifierDeclaration classifierDecl) {
		val toBeRemoved = newArrayList;
		for (superTypeRefNode : classifierDecl.superClassifierRefs) {
			val superTypeRef = state.info.getOriginalProcessedTypeRef(superTypeRefNode);
			val superDeclType = superTypeRef?.declaredType;
			if (!DtsUtils.isDtsExportableReference(superDeclType, state)) {
				toBeRemoved += superTypeRefNode;
			}
		}

		toBeRemoved.forEach[remove];
	}
}
