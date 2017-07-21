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
package org.eclipse.n4js.ui.labeling;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ui.outline.N4JSOutlineTreeProvider;

/**
 * Pair of eobject and context, used in label provider to distinguish between inherited and owned members. See
 * {@link N4JSOutlineTreeProvider#createNodeForObjectWithContext(org.eclipse.xtext.ui.editor.outline.IOutlineNode, EObjectWithContext)}
 */
public class EObjectWithContext {
	/**
	 * The object which is to be labeled.
	 */
	public final EObject obj;
	/**
	 * The context of the object, such as the class (which is not necessairly the containing class).
	 */
	public final EObject context;

	/**
	 * Creates pair. Usually, object is a member, context the class which is to be displayed in the outline.
	 */
	public EObjectWithContext(EObject obj, EObject context) {
		this.obj = obj;
		this.context = context;
	}
}
