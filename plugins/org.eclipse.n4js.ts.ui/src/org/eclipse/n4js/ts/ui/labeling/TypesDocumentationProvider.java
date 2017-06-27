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
package org.eclipse.n4js.ts.ui.labeling;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.documentation.impl.MultiLineCommentDocumentationProvider;
import org.eclipse.xtext.nodemodel.INode;

/**
 */
public class TypesDocumentationProvider extends MultiLineCommentDocumentationProvider {

	@Override
	public List<INode> getDocumentationNodes(EObject object) {
		// final EObject astNode = N4JSASTUtils.getCorrespondingASTNode(object);
		// if (astNode != null)
		// return super.getDocumentationNodes(astNode);
		return super.getDocumentationNodes(object);
	}
}
