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
package org.eclipse.n4js.ui.organize.imports

import org.eclipse.n4js.organize.imports.ImportProvidedElement
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.resource.IEObjectDescription

/**
 * Enhanced information, not deducible from the IEObjectDescription:
 * <ul>
 *  <li>how to import {@link #exportedAsDefault}</li>
 *  <li>name used in script
 * </ul>
 * Mainly it provides information used in cases of default exports,
 * where the IEObjectdescription must be handled differently.
 *
 * Also overrides {@code equals()} and {@code hashCode()} to enable set-based operations.
 *
 * It differs from {@link ImportProvidedElement} as this light-weight structure only describes potential imports,
 * while instances of {@link ImportProvidedElement} are objects for tracking usage of already imported elements.
 *
 */
@Data
public final class ImportableObject {
	String name;
	IEObjectDescription eobj;
	boolean exportedAsDefault;

	override equals(Object o) {
		if (o instanceof ImportableObject) {
			return name == o.name && exportedAsDefault == o.exportedAsDefault &&
				eobj.EObjectURI.equals(o.eobj.EObjectURI);
		} else
			return false;
	}

	override hashCode() {
		return name.hashCode + (if (exportedAsDefault) 13 else 7) + eobj.EObjectURI.hashCode;
	}
}
