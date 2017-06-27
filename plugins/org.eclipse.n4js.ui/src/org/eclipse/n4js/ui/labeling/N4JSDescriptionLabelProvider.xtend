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

import org.eclipse.xtext.ui.label.DefaultDescriptionLabelProvider
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.n4js.ts.ui.search.LabelledReferenceDescription

/**
 * Provides labels for a IEObjectDescriptions and IResourceDescriptions.
 * <br/><br/>
 * Everytime \@org.eclipse.xtext.ui.resource.ResourceServiceDescriptionLabelProvider private ILabelProvider labelProvider;
 * is used this label provider is injected.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#labelProvider
 */
class N4JSDescriptionLabelProvider extends DefaultDescriptionLabelProvider {

	override text(IEObjectDescription ele) {
		ele.name.toString
	}

	def text(LabelledReferenceDescription description) {
		return description.getLabel
	}

//	override text(IEObjectDescription ele) {
//		ele.name.toString
//	}
//
//	override image(IEObjectDescription ele) {
//		ele.EClass.name + '.gif'
//	}
}
