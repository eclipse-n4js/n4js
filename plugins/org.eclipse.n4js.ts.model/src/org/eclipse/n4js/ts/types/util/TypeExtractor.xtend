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
package org.eclipse.n4js.ts.types.util

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.Type

/**
 * This class is a utility methods that extract types from type refs. 
 */
class TypeExtractor {
	def Type extractType (ParameterizedTypeRef parameterizedTypeRef) {
		return parameterizedTypeRef.declaredType;
	}

}