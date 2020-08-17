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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;

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
			List<FormalParameter> fpars = N4JSASTUtils.getFormalParameters((FunctionOrFieldAccessor) result);
			for (FormalParameter fpar : fpars) {
				List<INode> nodes = NodeModelUtils.findNodesForFeature(fpar,
						TypesPackage.eINSTANCE.getIdentifiableElement_Name());
				for (INode node : nodes) {
					if (node.getEndOffset() == offset) {
						return fpar;
					}
				}
			}
		}

		return result;
	}
}
