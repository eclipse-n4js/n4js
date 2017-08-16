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
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.xtext.resource.ResourceSetContext;
import org.eclipse.xtext.ui.editor.IDirtyStateManager;
import org.eclipse.xtext.ui.editor.IDirtyStateManagerExtension;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Dirty editor aware extension to the {@link CanLoadFromDescriptionHelper}.
 */
@Singleton
public class EditorAwareCanLoadFromDescriptionHelper extends CanLoadFromDescriptionHelper {

	/*
	 * Actually injected but we do not have a binding for the IDirtyStateManagerExtension thus we use a setter and do
	 * the instanceof / cast only once.
	 */
	private IDirtyStateManagerExtension dirtyStateManager = Collections::emptyList;

	@Inject
	private void setDirtyStateManager(IDirtyStateManager dirtyStateManager) {
		if (dirtyStateManager instanceof IDirtyStateManagerExtension) {
			this.dirtyStateManager = (IDirtyStateManagerExtension) dirtyStateManager;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * This implementation is aware of open editors and in the context of dirty resource / multiple open editors, we
	 * need to consider also resources across project boundaries.
	 */
	@Override
	public boolean mustLoadFromSource(URI resourceURI, ResourceSet resourceSet) {
		if (super.mustLoadFromSource(resourceURI, resourceSet)) {
			return true;
		}
		ResourceSetContext context = ResourceSetContext.get(resourceSet);
		// check that we are not in the builder scope (which has strict boundaries
		// wrt project dependencies
		if (!context.isBuilder()) {
			ImmutableSet<URI> openEditors = ImmutableSet.copyOf(dirtyStateManager.getDirtyResourceURIs());
			/* ignore project boundaries */
			return dependsOnAny(resourceURI, openEditors, getIndex(resourceSet), false);
		}
		return false;
	}

}
