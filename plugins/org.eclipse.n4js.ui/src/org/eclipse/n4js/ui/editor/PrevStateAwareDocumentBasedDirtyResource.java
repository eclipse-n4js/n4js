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
package org.eclipse.n4js.ui.editor;

import org.eclipse.xtext.builder.impl.PersistentDataAwareDirtyResource;
import org.eclipse.xtext.resource.IResourceDescription;

/**
 * A dirty resource that allows to obtain the previous state to fire proper events from the DirtyStateManager
 */
@SuppressWarnings("restriction")
public class PrevStateAwareDocumentBasedDirtyResource extends PersistentDataAwareDirtyResource {

	private IResourceDescription prevDescription;

	@Override
	public void copyState(IResourceDescription original) {
		this.prevDescription = getDescriptionIfInitialized();
		super.copyState(original);
	}

	/**
	 * The previous state of this resource.
	 */
	public IResourceDescription getPrevDescription() {
		return prevDescription;
	}
}
