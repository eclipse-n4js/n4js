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

import java.util.Map;

import org.eclipse.n4js.jsdoc.N4JSDocHelper;

import com.google.inject.Inject;

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
	public CharSequence createSpecRegionString(SpecRequirementSection spec,
			@SuppressWarnings("unused") Map<String, SpecSection> specsByKey) {
		return ADocSerializer.process(spec);
	}

	/**
	 * Creates the spec of the given entry for the AsciiDoc document.
	 */
	public CharSequence createSpecRegionString(SpecIdentifiableElementSection spec,
			Map<String, SpecSection> specsByKey) {
		if (spec.getDoclet() == null) {
			spec.setDoclet(n4jsDocHelper.getDoclet(spec.getIdentifiableElement()));
		}
		return ADocSerializer.process(spec, specsByKey);
	}

}
