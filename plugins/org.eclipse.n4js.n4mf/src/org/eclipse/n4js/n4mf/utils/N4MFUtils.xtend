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
package org.eclipse.n4js.n4mf.utils

import java.util.List

import static org.eclipse.n4js.n4mf.SourceFragmentType.*
import org.eclipse.n4js.n4mf.SourceFragmentType
import org.eclipse.n4js.n4mf.SourceFragment

/**
 */
class N4MFUtils {

	def static filterGenerationAwareN4JSContainer(List<SourceFragment> sourceFragments) {
		sourceFragments.filter[isGenerationAwareN4JSContainer(sourceFragmentType)].toList
	}

	def static isGenerationAwareN4JSContainer(SourceFragmentType sourceFragmentType) {
		sourceFragmentType.isInTypes(#[SOURCE, EXTERNAL, TEST])
	}


	def static isInTypes(SourceFragmentType sourceFragmentType, SourceFragmentType... relevantTypes) {
		relevantTypes.contains(sourceFragmentType)
	}

	def static isNotInTypes(SourceFragmentType sourceFragmentType, SourceFragmentType... relevantTypes) {
		!relevantTypes.contains(sourceFragmentType)
	}
}
