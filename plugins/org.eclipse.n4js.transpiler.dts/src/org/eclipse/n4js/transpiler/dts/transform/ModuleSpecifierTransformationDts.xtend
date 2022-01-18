/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform

import org.eclipse.n4js.transpiler.es.transform.ModuleSpecifierTransformation
import org.eclipse.n4js.ts.types.TModule

/**
 * Minor adjustments over {@link ModuleSpecifierTransformation} for .d.ts files.
 */
class ModuleSpecifierTransformationDts extends ModuleSpecifierTransformation {

	override protected String getActualFileExtension(TModule targetModule) {
		// file extensions are not required in module specifiers inside .d.ts files
		return null;
	}
}
