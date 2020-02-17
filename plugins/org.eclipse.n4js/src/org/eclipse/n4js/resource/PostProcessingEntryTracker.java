/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.resource;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.xtext.util.CancelIndicator;

/**
 * Simple utility class for associating a resource set with a single {@link PostProcessingAwareResource}.
 * <p>
 * This class is kept as simple as possible; you can only get / set / unset a single {@code PostProcessingAwareResource}
 * per resource set. All logic is in {@link PostProcessingAwareResource#performPostProcessing(CancelIndicator)}.
 */
public final class PostProcessingEntryTracker extends AdapterImpl {
	final PostProcessingAwareResource entryResource;

	private PostProcessingEntryTracker(PostProcessingAwareResource entryResource) {
		this.entryResource = entryResource;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == PostProcessingEntryTracker.class;
	}

	/** Returns the "entry resource" for the given resource set. */
	public static PostProcessingAwareResource getEntryResource(ResourceSet resourceSet) {
		if (resourceSet != null) {
			PostProcessingEntryTracker tracker = (PostProcessingEntryTracker) EcoreUtil.getAdapter(
					resourceSet.eAdapters(), PostProcessingEntryTracker.class);
			if (tracker != null) {
				return tracker.entryResource;
			}
		}
		return null;
	}

	/** Sets the "entry resource" for the given resource set. */
	public static void setEntryResource(PostProcessingAwareResource resource) {
		ResourceSet resourceSet = resource.getResourceSet();
		if (resourceSet != null) {
			unsetEntryResource(resourceSet);
			EcoreUtilN4.doWithDeliver(false, () -> {
				resourceSet.eAdapters().add(new PostProcessingEntryTracker(resource));
			}, resourceSet);
		}
	}

	/** Clears the "entry resource" for the given resource set. */
	public static void unsetEntryResource(ResourceSet resourceSet) {
		if (resourceSet != null) {
			EcoreUtilN4.doWithDeliver(false, () -> {
				resourceSet.eAdapters().removeIf(a -> a instanceof PostProcessingEntryTracker);
			}, resourceSet);
		}
	}
}
