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

import java.lang.reflect.Field;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionChangeEvent;
import org.eclipse.xtext.ui.editor.DirtyStateManager;
import org.eclipse.xtext.ui.editor.DocumentBasedDirtyResource;
import org.eclipse.xtext.ui.editor.IDirtyResource;

/**
 * A specialized DirtyStateManager that is aware of the state of a dirty resource as it was announced in the prev run of
 * notifications.
 */
public class PrevStateAwareDirtyStateManager extends DirtyStateManager {

	@Override
	protected void notifyListeners(final IDirtyResource dirtyResource, boolean managed) {
		if (managed) {
			IResourceDescription prevState = extractOldDescription(dirtyResource);
			IResourceDescription.Delta delta = new IResourceDescription.Delta() {
				@Override
				public boolean haveEObjectDescriptionsChanged() {
					// Same as in base class - we assume the delta to be a changed delta
					return true;
				}

				@Override
				public IResourceDescription getOld() {
					return prevState;
				}

				@Override
				public IResourceDescription getNew() {
					return dirtyResource.getDescription();
				}

				@Override
				public URI getUri() {
					return dirtyResource.getURI();
				}
			};
			notifyListeners(delta);
		} else {
			IResourceDescription.Delta delta = new IResourceDescription.Delta() {
				@Override
				public boolean haveEObjectDescriptionsChanged() {
					return true;
				}

				@Override
				public IResourceDescription getOld() {
					return dirtyResource.getDescription();
				}

				@Override
				public IResourceDescription getNew() {
					return null;
				}

				@Override
				public URI getUri() {
					return dirtyResource.getURI();
				}
			};
			notifyListeners(delta);
		}
	}

	private void notifyListeners(IResourceDescription.Delta delta) {
		notifyListeners(new ResourceDescriptionChangeEvent(Collections.singletonList(delta)));
	}

	private IResourceDescription extractOldDescription(final IDirtyResource dirtyResource) {
		IDirtyResource myDirtyResource = reflectiveGetInnerResource(dirtyResource);
		if (myDirtyResource instanceof PrevStateAwareDocumentBasedDirtyResource) {
			return ((PrevStateAwareDocumentBasedDirtyResource) myDirtyResource).getPrevDescription();
		}
		return null;
	}

	private DocumentBasedDirtyResource reflectiveGetInnerResource(final IDirtyResource dirtyResource) {
		Field[] declaredFields = dirtyResource.getClass().getDeclaredFields();
		DocumentBasedDirtyResource myDirtyResource = null;
		try {
			Field field = declaredFields[0];
			field.setAccessible(true);
			N4JSDirtyStateEditorSupport thingy = (N4JSDirtyStateEditorSupport) field.get(dirtyResource);
			myDirtyResource = thingy.getDirtyResource();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// ignore
		}
		return myDirtyResource;
	}

}
