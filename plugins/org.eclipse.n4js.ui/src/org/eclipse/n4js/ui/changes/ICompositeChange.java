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
package org.eclipse.n4js.ui.changes;

import java.util.Collections;
import java.util.List;

/**
 * An {@link IChange} composed of zero or more other changes. The changes may span multiple files.
 */
public interface ICompositeChange extends IChange {

	/**
	 * Returns list of {@link IChange}s directly contained in the receiving composite change.
	 */
	List<IChange> getChildren();

	/**
	 * Base class for all composite changes.
	 */
	public static abstract class AbstractCompositeChange extends AbstractChange implements ICompositeChange {

		// leave management of the list of children to subclasses, because it might be very
		// different from case to case (e.g. subclasses might want to compute them lazily)

		/**
		 *
		 */
		public AbstractCompositeChange() {
		}
	}

	/**
	 * An identity change.
	 */
	public static class IdentityChange extends AbstractCompositeChange {

		/**
		 * Do not instantiate this class. Use value {@link IChange#IDENTITY} instead.
		 */
		/* package */IdentityChange() {
		}

		@Override
		public List<IChange> getChildren() {
			return Collections.emptyList();
		}
	}
}
