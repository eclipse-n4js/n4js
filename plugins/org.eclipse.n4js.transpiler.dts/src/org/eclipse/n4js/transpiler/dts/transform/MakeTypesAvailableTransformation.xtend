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
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.dts.utils.DtsUtils
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.workspace.WorkspaceAccess

/**
 * For all types referenced from a type reference in the intermediate model, this transformation is responsible for
 * making them available in the current module (if not available already) by adding an additional import.
 */
class MakeTypesAvailableTransformation extends Transformation {

	@Inject
	private WorkspaceAccess workspaceAccess;

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, TypeReferenceNode, false).forEach[ typeRefNode |
			makeAllTypesAvailable(typeRefNode)
		];
	}

	def private void makeAllTypesAvailable(TypeReferenceNode<?> typeRefNode) {
		val typeRef = state.info.getOriginalProcessedTypeRef(typeRefNode);
		if (typeRef !== null) {
			makeAllTypesAvailable(typeRef);
		}
	}

	def private void makeAllTypesAvailable(TypeRef typeRef) {
		TypeUtils.forAllTypeRefs(typeRef, ParameterizedTypeRef, true, true, [ ptr |
			val declType = ptr.declaredType;
			if (declType !== null) {
				makeTypeAvailable(declType);
			}
			for (typeArg : ptr.typeArgs) {
				if (typeArg instanceof Wildcard) {
					val upperBound = typeArg.getDeclaredOrImplicitUpperBound();
					if (upperBound !== null) {
						makeAllTypesAvailable(upperBound); // FIXME infinite recursion???
					}
				}
			}
			return true; // always continue
		], null);
	}

	def private void makeTypeAvailable(Type type) {
		val isAlreadyAvailable = DtsUtils.getReferenceToTypeIfLocallyAvailable(type, state) !== null;
		if (isAlreadyAvailable) {
			// the type is already available, but we have to make sure its import (if any) won't be removed as unused
			// even if all other usages will be removed (e.g. if they are in expressions/statements):
			val ste = getSymbolTableEntryOriginal(type, false);
			val importSpecifier = ste?.importSpecifier;
			if (importSpecifier !== null) {
				state.info.markAsRetainedIfUnused(importSpecifier);
			}
			return;
		}
		// the type is not yet available
		if (type.exported) {
			// no need to check accessibility modifiers in addition to #isExported(), because on TypeScript-side we bump up the accessibility
			val declTypeProject = workspaceAccess.findProjectContaining(type);
			if (declTypeProject !== null) {
				if (declTypeProject === state.project
					|| state.project.dependencies.contains(declTypeProject.name)) {
					// the type reference points to an exported type in the same project or a project we directly depend on
					// --> we can add an import for this type
					val ste = addNamedImport(type, null); // FIXME use alias if name already in use!
					// we cannot add an actual reference to the newly created import, so we have to tell SanitizeImportTransformation
					// to never remove it:
					state.info.markAsRetainedIfUnused(ste.importSpecifier);
					return;
				}
			}
		}
		// we tried our best, but this type cannot be made available
		// --> the PrettyPrinterTypeRef will replace it with 'any'
	}
}
