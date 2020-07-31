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
package org.eclipse.n4js.ide.xtext.server.index;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

/**
 *
 */
class ForwardingResourceDescriptions implements IResourceDescriptions.IResourceSetAware {

	private final IResourceDescriptions delegate;
	private final ResourceSet resourceSet;

	ForwardingResourceDescriptions(IResourceDescriptions delegate, ResourceSet resourceSet) {
		this.delegate = delegate;
		this.resourceSet = resourceSet;
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Iterable<IResourceDescription> getAllResourceDescriptions() {
		return delegate.getAllResourceDescriptions();
	}

	@Override
	public IResourceDescription getResourceDescription(URI uri) {
		return delegate.getResourceDescription(uri);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects() {
		return delegate.getExportedObjects();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects(EClass type, QualifiedName name, boolean ignoreCase) {
		return delegate.getExportedObjects(type, name, ignoreCase);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EClass type) {
		return delegate.getExportedObjectsByType(type);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByObject(EObject object) {
		return delegate.getExportedObjectsByObject(object);
	}

	@Override
	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	IResourceDescriptions getDelegate() {
		return delegate;
	}

}
