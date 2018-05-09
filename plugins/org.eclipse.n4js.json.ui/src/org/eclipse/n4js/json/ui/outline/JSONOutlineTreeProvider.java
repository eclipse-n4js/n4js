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
package org.eclipse.n4js.json.ui.outline;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.ui.JSONUIModelUtils;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

/**
 * Customization of the default outline structure.
 *
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#outline
 */
public class JSONOutlineTreeProvider extends DefaultOutlineTreeProvider {
	@Override
	public void createChildren(IOutlineNode parent, EObject modelElement) {
		if (modelElement instanceof JSONDocument) {
			if(JSONUIModelUtils.isContainer(((JSONDocument) modelElement).getContent())) {
				createChildren(parent, ((JSONDocument) modelElement).getContent());
				return;
			}
		}
		if (modelElement instanceof NameValuePair) {
			final NameValuePair pair = (NameValuePair) modelElement;
			JSONValue pairValue = pair.getValue();
			if (!JSONUIModelUtils.isContainer(pairValue)) {
				// if value is not a container do not further create any outline elements
				return;
			} else {
				for (EObject child : JSONUIModelUtils.getChildren(pairValue)) {
					createNode(parent, child);
				}
				return;
			}
		}
		super.createChildren(parent, modelElement);
	}
	
	@Override
	protected void createNode(IOutlineNode parent, EObject modelElement) {
		if (modelElement.eContainer() instanceof JSONDocument) {
			new EObjectNode(modelElement, parent, imageDispatcher.invoke(modelElement), 
					modelElement.eResource().getURI().lastSegment(), false);
			return;
		}
		
		super.createNode(parent, modelElement);
	}
}
