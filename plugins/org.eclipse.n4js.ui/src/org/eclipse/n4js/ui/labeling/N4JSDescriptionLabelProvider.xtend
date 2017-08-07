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
package org.eclipse.n4js.ui.labeling

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.ui.search.LabelledReferenceDescription
import org.eclipse.n4js.ui.search.LabellingReferenceFinder
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.ui.label.DefaultDescriptionLabelProvider

/**
 * Provides labels for a IEObjectDescriptions and IResourceDescriptions.
 * <br/><br/>
 * Everytime \@org.eclipse.xtext.ui.resource.ResourceServiceDescriptionLabelProvider private ILabelProvider labelProvider;
 * is used this label provider is injected.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#labelProvider
 */
class N4JSDescriptionLabelProvider extends DefaultDescriptionLabelProvider {

	@Inject
	private N4JSLabelProvider labelProvider;

	def text(LabelledReferenceDescription description) {
		// Calculate hierarchical logical name, e.g. C.m
		var text = labelProvider.getText(description.displayEObject);
		var currContainer = description.displayEObject.eContainer;
		while (currContainer !== null && !(currContainer instanceof Script)) {
			if (LabellingReferenceFinder.isShowable(currContainer)) {
				text = labelProvider.getText(currContainer) + "." + text;
			}
			currContainer = currContainer.eContainer;
		}
		return text;
	}

	override Object doGetImage(Object element) {
		if (element instanceof IResourceDescription) {
			// No image for resource for now.
		} else if (element instanceof LabelledReferenceDescription) {
			val image = labelProvider.getImage(element.displayEObject);
			return image;
		}
		return null;
	}
}
