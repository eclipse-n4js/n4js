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
package org.eclipse.n4js.ide.xtext.server;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.Iterables;

public class ShadowingResourceDescriptionsData extends ResourceDescriptionsData {

	protected final ResourceDescriptionsData delegate;

	public ShadowingResourceDescriptionsData(ResourceDescriptionsData data,
			ResourceDescriptionsData delegate) {
		// we cannot use the more efficient constructor here, because field 'resourceDescriptionMap' is private
		super(data.getAllResourceDescriptions());
		this.delegate = delegate;
	}

	public ShadowingResourceDescriptionsData(Iterable<IResourceDescription> descriptions,
			ResourceDescriptionsData delegate) {
		super(descriptions);
		this.delegate = delegate;
	}

	protected ShadowingResourceDescriptionsData(Map<URI, IResourceDescription> resourceDescriptionMap,
			Map<QualifiedName, Object> lookupMap, ResourceDescriptionsData delegate) {
		super(resourceDescriptionMap, lookupMap);
		this.delegate = delegate;
	}

	public boolean isLocal(URI uri) {
		return super.getAllURIs().contains(uri);
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty() && delegate.isEmpty();
	}

	@Override
	public Set<URI> getAllURIs() {
		Set<URI> result = new HashSet<>(delegate.getAllURIs());
		result.addAll(super.getAllURIs());
		return result;
	}

	@Override
	public IResourceDescription getResourceDescription(URI uri) {
		IResourceDescription result = super.getResourceDescription(uri);
		if (result == null) {
			result = delegate.getResourceDescription(uri);
		}
		return result;
	}

	@Override
	public Iterable<IResourceDescription> getAllResourceDescriptions() {
		Set<URI> allLocalURIs = super.getAllURIs();
		Iterable<IResourceDescription> localDescriptions = super.getAllResourceDescriptions();
		Iterable<IResourceDescription> nonShadowedFromDelegate = Iterables.filter(
				delegate.getAllResourceDescriptions(),
				desc -> !allLocalURIs.contains(desc.getURI()));
		return Iterables.concat(localDescriptions, nonShadowedFromDelegate);
	}

	@Override
	public ShadowingResourceDescriptionsData copy() {
		// we cannot use the more efficient constructor here, because field 'resourceDescriptionMap' is private
		return new ShadowingResourceDescriptionsData(super.getAllResourceDescriptions(), delegate);
	}

	@Override
	protected Iterable<IResourceDescription> getSelectables() {
		return getAllResourceDescriptions();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects() {
		// no special handling required, because super implementation is based on #getSelectables()
		return super.getExportedObjects();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects(final EClass type, final QualifiedName qualifiedName,
			final boolean ignoreCase) {
		return concatEObjectDescriptionsWithShadowing(super.getAllURIs(),
				super.getExportedObjects(type, qualifiedName, ignoreCase),
				delegate.getExportedObjects(type, qualifiedName, ignoreCase));
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EClass type) {
		// no special handling required, because super implementation is based on #getSelectables()
		return super.getExportedObjectsByType(type);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByObject(EObject object) {
		return concatEObjectDescriptionsWithShadowing(super.getAllURIs(),
				super.getExportedObjectsByObject(object),
				delegate.getExportedObjectsByObject(object));
	}

	/* package */ static Iterable<IResourceDescription> concatResourceDescriptionsWithShadowing(
			Set<URI> primaryResourceURIs, Iterable<IResourceDescription> primaryDescriptions,
			Iterable<IResourceDescription> secondaryDescriptions) {

		Iterable<IResourceDescription> nonShadowedSecondaryDescriptions = Iterables.filter(
				secondaryDescriptions,
				desc -> !primaryResourceURIs.contains(desc.getURI()));
		return Iterables.concat(primaryDescriptions, nonShadowedSecondaryDescriptions);
	}

	private static Iterable<IEObjectDescription> concatEObjectDescriptionsWithShadowing(
			Set<URI> primaryResourceURIs, Iterable<IEObjectDescription> primaryDescriptions,
			Iterable<IEObjectDescription> secondaryDescriptions) {

		Iterable<IEObjectDescription> nonShadowedSecondaryDescriptions = Iterables.filter(
				secondaryDescriptions,
				desc -> !primaryResourceURIs.contains(desc.getEObjectURI().trimFragment()));
		return Iterables.concat(primaryDescriptions, nonShadowedSecondaryDescriptions);
	}
}
