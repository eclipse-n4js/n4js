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
package org.eclipse.n4js.semver.formatting2

import org.eclipse.n4js.semver.SEMVER.HyphenVersionRange
import org.eclipse.n4js.semver.SEMVER.VersionRange
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument
import org.eclipse.n4js.semver.SEMVER.VersionRangeSetRequirement

class SEMVERFormatter extends AbstractFormatter2 {

	def dispatch void format(VersionRangeSetRequirement versionRangeSet, extension IFormattableDocument document) {
		for (VersionRange versionRange : versionRangeSet.getRanges()) {
			versionRange.format;
		}
	}

	def dispatch void format(HyphenVersionRange hyphenVersionRange, extension IFormattableDocument document) {
		hyphenVersionRange.getFrom.format;
		hyphenVersionRange.getTo.format;
	}

}
