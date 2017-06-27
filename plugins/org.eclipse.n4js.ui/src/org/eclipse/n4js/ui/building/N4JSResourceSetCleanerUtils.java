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
package org.eclipse.n4js.ui.building;

import static org.eclipse.n4js.ts.scoping.builtin.N4Scheme.isN4Scheme;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Simple helper class to configure the behavior of resource set clearing. This utility class was introduced to avoid
 * the deletion of those resources that were created to hold all built-in types.
 *
 * This class is being called by {@link N4JSBuilderParticipant} and {@link N4JSGenerateImmediatelyBuilderState} when
 * running low of memory and clustering builder removes the resources from the resource set to free up some memory.
 */
/* default */abstract class N4JSResourceSetCleanerUtils {

	private static final Logger LOGGER = Logger.getLogger(N4JSResourceSetCleanerUtils.class);

	/**
	 * Removes all non-N4 resources from the given resource set without sending any notification about the removal. This
	 * specific resource set cleaning is required to avoid the accidental removal of the resources holding the built-in
	 * types. For more details reference: IDEBUG-491.
	 *
	 * @param resourceSet
	 *            the resource set to clean. Optional, can be {@code null}. If {@code null} this method has no effect.
	 */
	/* default */static void clearResourceSet(final ResourceSet resourceSet) {
		boolean wasDeliver = resourceSet.eDeliver();
		try {
			resourceSet.eSetDeliver(false);
			for (final Iterator<Resource> itr = resourceSet.getResources().iterator(); itr.hasNext(); /**/) {
				final Resource resource = itr.next();
				final URI uri = resource.getURI();
				if (!isN4Scheme(uri)) {
					itr.remove();
				} else {
					LOGGER.info("Intentionally skipping the removal of N4 resource: " + uri);
				}
			}
		} finally {
			resourceSet.eSetDeliver(wasDeliver);
		}
	}

}
