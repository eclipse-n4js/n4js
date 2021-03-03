/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.internal.lsp;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;

/**
 * N4JS-specific adjustments to {@link SourceFolderSnapshot}.
 */
public class N4JSSourceFolderSnapshot extends SourceFolderSnapshot {

	/** Creates a new {@link N4JSSourceFolderSnapshot}. */
	public N4JSSourceFolderSnapshot(String name, URI path) {
		super(name, path);
	}

	@Override
	protected int computeHashCode() {
		return super.computeHashCode(); // no additional data in this class, so simply use super implementation
	}

	@Override
	protected boolean computeEquals(Object obj) {
		return super.computeEquals(obj); // no additional data in this class, so simply use super implementation
	}

	@Override
	public String toString() {
		return "N4JS" + super.toString();
	}

	// ==============================================================================================================
	// Convenience and utility methods (do not introduce additional data)

}
