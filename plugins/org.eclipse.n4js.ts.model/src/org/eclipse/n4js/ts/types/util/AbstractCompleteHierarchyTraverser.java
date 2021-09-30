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
package org.eclipse.n4js.ts.types.util;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;

/**
 * A hierarchy traverser that will always traverse the complete hierarchy.
 */
public abstract class AbstractCompleteHierarchyTraverser<Result> extends AbstractHierachyTraverser<Result> {

	/**
	 * Creates a new traverser that is used to safely process a potentially cyclic inheritance tree. The complete
	 * hierarchy is traversed.
	 */
	protected AbstractCompleteHierarchyTraverser(ContainerType<?> type) {
		super(type);
	}

	@Override
	protected final boolean process(ContainerType<?> currentType) {
		doProcess(currentType);
		return false;
	}

	@Override
	protected boolean process(PrimitiveType currentType) {
		doProcess(currentType);
		return false;
	}

	/**
	 * Process the given container type. The traversal itself is handled by the super type.
	 *
	 * @param currentType
	 *            the processed type.
	 */
	protected abstract void doProcess(ContainerType<?> currentType);

	/**
	 * Process the given primitive type. The traversal itself is handled by the super type.
	 *
	 * @param currentType
	 *            the processed type.
	 */
	protected abstract void doProcess(PrimitiveType currentType);

}
