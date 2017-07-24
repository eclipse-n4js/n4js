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
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineNodeFactory;

/**
 * Needs to be bound in module. Used by {@link N4JSOutlineTreeProvider} to create N4JS specific nodes with additional
 * information not found in the element in order to improve visualization.
 */
public class N4JSOutlineNodeFactory extends OutlineNodeFactory {

	/**
	 * Returns N4JSEObjectNode instead of simple EObjectNode to allow for attaching additional information such as
	 * inheritance state of members.
	 */
	@Override
	public N4JSEObjectNode createEObjectNode(IOutlineNode parentNode, EObject modelElement,
			ImageDescriptor imageDescriptor,
			Object text,
			boolean isLeaf) {
		N4JSEObjectNode eObjectNode = new N4JSEObjectNode(modelElement, parentNode, imageDescriptor, text, isLeaf);
		ICompositeNode parserNode = NodeModelUtils.getNode(modelElement);
		if (parserNode != null)
			eObjectNode.setTextRegion(parserNode.getTextRegion());
		if (isLocalElement(parentNode, modelElement))
			eObjectNode.setShortTextRegion(getLocationInFileProvider().getSignificantTextRegion(modelElement));
		return eObjectNode;
	}
}
