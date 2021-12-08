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
package org.eclipse.n4js.ts.types;

import org.eclipse.n4js.ts.types.internal.FindElementTypeHelper;

/**
 * Utility to identify element types of types inheriting from an {@link ArrayLike array-like} type.
 */
public class ArrayLikes {

	/**
	 * Returns the element type that is valid for the given type.
	 *
	 * This is a workaround for a bug in Xcore where a clean build fails due to a circular reference from Xcore to
	 * ArrayLikes and back. In fact the argument type should be Type rather than Object.
	 *
	 * @param type
	 *            the type to inspect
	 * @return the element type or null
	 */
	public static Object getElementType(Object type) {
		if (type instanceof ContainerType) {
			return new FindElementTypeHelper((ContainerType<?>) type).getResult();
		} else if (type instanceof PrimitiveType) {
			return new FindElementTypeHelper((PrimitiveType) type).getResult();
		}
		return null;
	}

}
