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
package org.eclipse.n4js.jsdoc2spec.adoc;

import com.google.inject.Inject
import org.eclipse.n4js.jsdoc.N4JSDocHelper
import java.util.Map

/**
 * Creates AsciiDoc spec fragments for spec region entries.
 */
public class ADocFactory {

	@Inject
	N4JSDocHelper n4jsDocHelper;

	@Inject
	ADocSerializer ADocSerializer;


	/**
	 * Creates the spec of the given entry for the AsciiDoc document.
	 */
	public def CharSequence createSpecRegionString(SpecRequirementSection spec, Map<String, SpecSection> specsByKey) {
		return ADocSerializer.process(spec, specsByKey);
	}

	/**
	 * Creates the spec of the given entry for the AsciiDoc document.
	 */
	public def CharSequence createSpecRegionString(SpecIdentifiableElementSection spec, Map<String, SpecSection> specsByKey) {
		if (spec.getDoclet === null) {
			spec.doclet = n4jsDocHelper.getDoclet(spec.identifiableElement);
		}
		return ADocSerializer.process(spec, specsByKey);
	}

}
