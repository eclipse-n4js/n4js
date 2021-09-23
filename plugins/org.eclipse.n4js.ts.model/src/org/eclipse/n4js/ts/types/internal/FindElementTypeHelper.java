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
package org.eclipse.n4js.ts.types.internal;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ArrayLike;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.util.AbstractHierachyTraverser;

/**
 * Implements the logic that traverses a type hierarchy until it finds a type that declared an element type, e.g.
 * {@code Array<T>} declared element type {@code T}.
 */
public class FindElementTypeHelper extends AbstractHierachyTraverser<TypeRef> {

	private TypeRef result;

	/**
	 * @param type
	 *            the initial type that should be processed.
	 */
	public FindElementTypeHelper(ContainerType<?> type) {
		super(type);
	}

	/**
	 * @param type
	 *            the initial type that should be processed.
	 */
	public FindElementTypeHelper(PrimitiveType type) {
		super(type);
	}

	@Override
	protected TypeRef doGetResult() {
		return result;
	}

	@Override
	protected boolean process(ContainerType<?> currentType) {
		if (currentType instanceof ArrayLike) {
			result = ((ArrayLike) currentType).getDeclaredElementType();
		}
		return result != null;
	}

	@Override
	protected boolean process(PrimitiveType currentType) {
		result = currentType.getDeclaredElementType();
		return result != null;
	}
}
