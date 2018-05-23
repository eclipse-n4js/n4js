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
 * Special EObjectNode used to carry additional information, such as "isInherited". Created by
 * {@link N4JSOutlineNodeFactory}.
 */
public class N4JSEObjectNode extends EObjectNode {

	/**
	 * Set to true of node is inherited, consumed, or polyfilled.
	 */
	public boolean isInherited = false;

	/**
	 * True iff node represents a member.
	 */
	public boolean isMember = false;

	/**
	 * True iff node represents a static member.
	 */
	public boolean isStatic = false;

	/**
	 * True iff node represents a public member.
	 */
	public boolean isPublic = false;

	/**
	 * True iff node represents a local type.
	 */
	public boolean isLocal = false;

	/**
	 * True iff node represents a constuctor method.
	 */
	public boolean isConstructor;

	/**
	 * Creates this node, required for super call.
	 */
	public N4JSEObjectNode(EObject eObject, IOutlineNode parent, ImageDescriptor imageDescriptor, Object text,
			boolean isLeaf) {
		super(eObject, parent, imageDescriptor, text, isLeaf);
	}
}
