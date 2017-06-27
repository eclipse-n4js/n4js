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

import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import org.eclipse.n4js.ui.changes.ICompositeChange.IdentityChange;

/**
 * A textual change in an {@link IXtextDocument}.
 */
public interface IChange {

	/**
	 * The singleton identity change.
	 */
	public static final ICompositeChange IDENTITY = new IdentityChange();

	/**
	 * Base class for all {@link IChange}s.
	 */
	public static abstract class AbstractChange implements IChange {

		/**
		 *
		 */
		/* package */AbstractChange() {
		}
	}
}
