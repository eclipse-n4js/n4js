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
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.ui.search.LabelledReferenceDescription
import org.eclipse.n4js.ui.labeling.helper.ImageCalculationHelper
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.ui.IImageHelper
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
	private IImageHelper imageHelper;

	@Inject
	private ImageCalculationHelper imageCalculatorHelper;

	@Inject
	private N4JSLabelProvider labelProvider;

	override text(IEObjectDescription ele) {
		ele.name.toString
	}

	def text(LabelledReferenceDescription description) {
		return description.getLabel
	}

	override Object doGetImage(Object element) {
		imageCalculatorHelper.labelProvider = this.labelProvider;
		if (element instanceof IResourceDescription) {
			// GH-73: TODO Handle this case
		} else if (element instanceof LabelledReferenceDescription) {
			var src = element.source;
			if (src instanceof ParameterizedTypeRef) {
				src = element.target;
			} else if (src instanceof IdentifierRef) {
				src = element.target;
			} else if (src instanceof ParameterizedPropertyAccessExpression) {
				src = element.target;
			}
			val imgDsc = imageCalculatorHelper.dispatchDoGetImage(src);
			val image =  imageHelper.getImage(imgDsc);
			return image;
		}
		return null;
	}
}
