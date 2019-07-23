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
package org.eclipse.n4js.validation.validators.packagejson;

import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Validation-only intermediate representation of a polyfill.
 */
public class PolyFilledProvision {

	/**
	 * ~ "a.b.ModulName.!POLY.ElementName"
	 */
	public final String descriptionPolyfill;

	/**
	 * ~ "a.b.ModuleName.ElementName"
	 */
	public final String descriptionStandard;

	/**
	 * library entry as referenced in the project description
	 */
	public final N4JSProjectName library;

	/**
	 * The actual Polyfill- Objectdescription from the library
	 */
	public final IEObjectDescription ieoDescrOfPolyfill;

	/**
	 * The {@link ProjectReference} as the place for the Error-Marker
	 */
	public final JSONStringLiteral libraryProjectReferenceLiteral;

	/**
	 *
	 */
	public PolyFilledProvision(N4JSProjectName library, JSONStringLiteral libProjectDescription,
			IEObjectDescription ieoDescrOfPolyfill) {
		this.library = library;
		this.libraryProjectReferenceLiteral = libProjectDescription;
		this.ieoDescrOfPolyfill = ieoDescrOfPolyfill;
		descriptionPolyfill = ieoDescrOfPolyfill.toString();
		descriptionStandard = withoutPolyfillAsString(ieoDescrOfPolyfill.getQualifiedName());
	}

	// Helper Methods
	/**
	 * @param qualifiedName
	 *            with {@code #POLY}-marker to be converted to a String without the polyfill-marker.
	 * @return string representation without polyfill marker
	 */
	public static String withoutPolyfillAsString(QualifiedName qualifiedName) {
		// Assumption: 2nd-last segment is "!POLY"
		String last = qualifiedName.getLastSegment();
		String poly = qualifiedName.skipLast(1).getLastSegment();
		assert (N4TSQualifiedNameProvider.POLYFILL_SEGMENT.equals(poly));
		QualifiedName ret = qualifiedName.skipLast(2).append(last);
		return ret.toString();
	}

	/**
	 * @param description
	 *            an Xtext-Object description
	 * @return true if there is a "!POLY" segment
	 */
	public static boolean isPolyfill(IEObjectDescription description) {
		return description.getQualifiedName().getSegments().stream()
				.anyMatch(it -> N4TSQualifiedNameProvider.POLYFILL_SEGMENT.equals(it));
	}

}
