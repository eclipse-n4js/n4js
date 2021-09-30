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
package org.eclipse.n4js.tooling.findReferences;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * Simple implementation of the {@link IResourceAccess} that uses a pre assigned resource set.
 */
@SuppressWarnings("restriction")
public class SimpleResourceAccess implements IReferenceFinder.IResourceAccess {
	private final ResourceSet resourceSet;

	/**
	 * Creates a new instance.
	 */
	public SimpleResourceAccess(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	@Override
	public <R> R readOnly(URI resourceURI, IUnitOfWork<R, ResourceSet> work) {
		try {
			return work.exec(resourceSet);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
