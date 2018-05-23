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
package org.eclipse.n4js.ui.labeling.helper

import org.eclipse.jface.resource.ImageDescriptor

/**
 * Returns image descriptions for overlays used in this project.
 */
class N4JSImageDescriptionLibrary {
	private extension ImageDescriptionHelper imageDescriptionHelper

	def void setImageDescriptionHelper(ImageDescriptionHelper imageDescriptionHelper) {
		this.imageDescriptionHelper = imageDescriptionHelper
	}

	def ImageDescriptor createAbstractImageDecorator() {
		createSimpleImageDescriptor("abstract_co.gif")
	}

	def ImageDescriptor createStaticImageDecorator() {
		createSimpleImageDescriptor("static_co.gif")
	}

	def ImageDescriptor createFinalImageDecorator() {
		createSimpleImageDescriptor("final_co.gif")
	}

	def ImageDescriptor createConstImageDecorator() {
		createSimpleImageDescriptor("c_ovr.gif")
	}

	def ImageDescriptor createPublicInternalVisibleImageDecorator() {
		createSimpleImageDescriptor("protected_co.gif")
	}

	def ImageDescriptor createProjectVisibleImageDecorator() {
		createSimpleImageDescriptor("default_co.gif")
	}

	def ImageDescriptor createPrivateVisibleImageDecorator() {
		createSimpleImageDescriptor("private_co.gif")
	}

	def ImageDescriptor createConstructorImageDecorator() {
		createSimpleImageDescriptor("c_ovr.gif")
	}

	def ImageDescriptor createSetterImageDecorator() {
		createSimpleImageDescriptor("function_ovr.gif")
	}

	def ImageDescriptor createGetterImageDecorator() {
		createSimpleImageDescriptor("function_inv_ovr.gif")
	}
}
