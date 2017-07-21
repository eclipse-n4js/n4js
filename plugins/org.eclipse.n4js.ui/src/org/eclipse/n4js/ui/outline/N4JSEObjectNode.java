/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.outline;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

/**
 * Special EObjectNode used to carry additional information, such as "isInherited".
 */
public class N4JSEObjectNode extends EObjectNode {

	/**
	 * Set to true of node is inherited, consumed, or polyfilled.
	 */
	public boolean isInherited = false;

	/**
	 * Creates this node, required for super call.
	 */
	public N4JSEObjectNode(EObject eObject, IOutlineNode parent, ImageDescriptor imageDescriptor, Object text,
			boolean isLeaf) {
		super(eObject, parent, imageDescriptor, text, isLeaf);
	}
}
