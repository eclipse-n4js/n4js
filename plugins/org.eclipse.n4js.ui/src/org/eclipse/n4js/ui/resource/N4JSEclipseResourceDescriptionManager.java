/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.resource;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.resource.N4JSResourceDescriptionManager;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.inject.Singleton;

/**
 * Specialized to optimize affected calculation for file URIs.
 */
@Singleton
public class N4JSEclipseResourceDescriptionManager extends N4JSResourceDescriptionManager {

	@Override
	public boolean isAffected(Collection<Delta> deltas, IResourceDescription candidate, IResourceDescriptions context) {
		URI candidateURI = candidate.getURI();
		if (candidateURI.isFile()) {
			return false;
		}
		return super.isAffected(deltas, candidate, context);
	}

}
