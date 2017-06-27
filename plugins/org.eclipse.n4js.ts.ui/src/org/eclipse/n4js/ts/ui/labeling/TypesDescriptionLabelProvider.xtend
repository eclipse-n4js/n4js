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
package org.eclipse.n4js.ts.ui.labeling

import org.eclipse.n4js.ts.ui.search.LabelledReferenceDescription
import org.eclipse.xtext.resource.IEObjectDescription

//import org.eclipse.xtext.resource.IEObjectDescription

/**
 * Provides labels for a IEObjectDescriptions and IResourceDescriptions.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#labelProvider
 */
class TypesDescriptionLabelProvider extends org.eclipse.xtext.ui.label.DefaultDescriptionLabelProvider {

	def text(LabelledReferenceDescription description) {
		return description.getLabel
	}

	override text(IEObjectDescription ele) {
		ele.name.toString
	}

}
