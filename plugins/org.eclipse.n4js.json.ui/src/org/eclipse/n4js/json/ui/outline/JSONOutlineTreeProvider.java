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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;

/**
 * Provides an outline tree for JSON documents, based on the containment
 * hierarchy that is defined by JSON arrays and objects.
 */
public class JSONOutlineTreeProvider extends DefaultOutlineTreeProvider {
	@Override
	public void createChildren(IOutlineNode parent, EObject modelElement) {
		if (modelElement instanceof JSONDocument) {
			JSONValue content = ((JSONDocument) modelElement).getContent();
			if (content.isContainer()) {
				List<? extends EObject> children = content.getChildren();
				for (EObject child : children) {
					createNode(parent, child);
				}
				return;
			} else {
				if (content != null) {
					// create node without children, given content is not null
					createNode(parent, content);
				}
				return;
			}
		}
		if (modelElement instanceof NameValuePair) {
			final NameValuePair pair = (NameValuePair) modelElement;
			JSONValue pairValue = pair.getValue();
			if (!pairValue.isContainer()) {
				// if value is not a container do not further create any outline elements
				return;
			} else {
				for (EObject child : pairValue.getChildren()) {
					createNode(parent, child);
				}
				return;
			}
		}
		super.createChildren(parent, modelElement);
	}

	@Override
	protected void createNode(IOutlineNode parent, EObject modelElement) {
		// make sure that non-container name-value-pairs are marked as leaf nodes
		if (modelElement instanceof NameValuePair && !((NameValuePair) modelElement).getValue().isContainer()) {
			createEObjectNode(parent, modelElement, this.imageDispatcher.invoke(modelElement),
					this.textDispatcher.invoke(modelElement),
					// mark as leaf node
					true);
			return;
		}

		// otherwise delegate to default behavior
		super.createNode(parent, modelElement);
	}
}
