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
package org.eclipse.n4js.jsdoc2spec;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;

/**
 *
 */
public class N4JSUtils {

	/**
	 * Returns the spec region name for a given element.
	 */
	public static String nameFromElement(IdentifiableElement element) {
		if (element instanceof TMember) {
			return nameFromElement(((TMember) element).getContainingType()) + "#" + element.getName();
		}
		TModule module = element.getContainingModule();
		if (module == null) {
			return "##global##." + element.getName();
		} else {
			return module.getModuleSpecifier() + "." + element.getName();
		}
	}

}
