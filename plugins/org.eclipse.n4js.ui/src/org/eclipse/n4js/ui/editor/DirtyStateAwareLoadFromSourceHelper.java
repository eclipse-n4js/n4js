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

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.scoping.utils.LoadFromSourceHelper;
import org.eclipse.xtext.resource.ResourceSetContext;
import org.eclipse.xtext.ui.editor.IDirtyStateManager;
import org.eclipse.xtext.ui.editor.IDirtyStateManagerExtension;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class DirtyStateAwareLoadFromSourceHelper extends LoadFromSourceHelper {

	private IDirtyStateManagerExtension dirtyStateManager = Collections::emptyList;

	@Inject
	private void setDirtyStateManager(IDirtyStateManager dirtyStateManager) {
		if (dirtyStateManager instanceof IDirtyStateManagerExtension) {
			this.dirtyStateManager = (IDirtyStateManagerExtension) dirtyStateManager;
		}
	}

	@Override
	public boolean mustLoadFromSource(URI resourceURI, ResourceSet resourceSet) {
		if (super.mustLoadFromSource(resourceURI, resourceSet)) {
			return true;
		}
		ResourceSetContext context = ResourceSetContext.get(resourceSet);
		if (context.isEditor()) {
			if (isTransitivlyDependingOn(resourceURI, Sets.newHashSet(dirtyStateManager.getDirtyResourceURIs()),
					getIndex(resourceSet), false)) {
				return true;
			}
		}
		return false;
	}

}
