/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Fixes an N4JS-specific problem with the default implementation of the super class.
 */
public class N4JSEObjectAtOffsetHelper extends EObjectAtOffsetHelper {

	@Override
	public EObject resolveElementAt(XtextResource resource, int offset) {
		EObject result = super.resolveElementAt(resource, offset);

		if (result instanceof FunctionOrFieldAccessor) {
			// special case: when the cursor is located at the end of a formal parameter, the default implementation
			// incorrectly returns the containing function instead of the formal parameter
			result = findBetterMatch(result, offset,
					N4JSASTUtils.getFormalParameters((FunctionOrFieldAccessor) result),
					TypesPackage.eINSTANCE.getIdentifiableElement_Name());
		} else if (result instanceof ImportDeclaration) {
			// special case: when the cursor is located at the end of an alias, the default implementation
			// incorrectly returns the containing import declaration instead of the named import specifier
			result = findBetterMatch(result, offset,
					IterableExtensions.filter(((ImportDeclaration) result).getImportSpecifiers(),
							NamedImportSpecifier.class),
					N4JSPackage.eINSTANCE.getNamedImportSpecifier_Alias());
		}

		return result;
	}

	private EObject findBetterMatch(EObject defaultResult, int offset, Iterable<? extends EObject> candidates,
			EAttribute candidateNameAttribute) {

		for (EObject candidate : candidates) {
			if (candidate.eGet(candidateNameAttribute) == null) {
				continue; // ignore candidates with undefined name
			}
			List<INode> nodes = NodeModelUtils.findNodesForFeature(candidate, candidateNameAttribute);
			for (INode node : nodes) {
				if (node.getEndOffset() == offset) {
					return candidate;
				}
			}
		}
		return defaultResult;
	}
}
