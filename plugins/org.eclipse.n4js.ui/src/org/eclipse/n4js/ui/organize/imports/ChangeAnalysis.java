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
package org.eclipse.n4js.ui.organize.imports;

import java.util.List;

import org.eclipse.n4js.ui.changes.IAtomicChange;
import org.eclipse.n4js.ui.changes.Replacement;

/**
 * Helper value object for imports changes.
 */
class ChangeAnalysis {

	List<IAtomicChange> changes;
	boolean noRealChanges;
	Replacement newText = null;
	Replacement deletion = null;

	ChangeAnalysis(List<IAtomicChange> changes, boolean noRealChanges) {
		this.changes = changes;
		this.noRealChanges = noRealChanges;
	}
}
