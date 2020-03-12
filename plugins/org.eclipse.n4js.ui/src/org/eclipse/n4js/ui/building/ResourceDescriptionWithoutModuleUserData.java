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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.xtext.scoping.ForwardingEObjectDescription;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;

import com.google.common.collect.Iterables;

/**
 * Wraps an existing {@link IResourceDescription} in order to hide the serialized TModule in the user data (see
 * {@link UserDataMapper}).
 * <p>
 * This is used by the incremental builder to enforce a fully re-load of certain resources for which the TModule info in
 * the user data is out-dated. See {@link N4JSGenerateImmediatelyBuilderState#queueAffectedResources} for details.
 */
public class ResourceDescriptionWithoutModuleUserData implements IResourceDescription {

	private final IResourceDescription delegate;

	/**
	 * Create instance.
	 */
	public ResourceDescriptionWithoutModuleUserData(IResourceDescription delegate) {
		this.delegate = delegate;
	}

	@Override
	public URI getURI() {
		return delegate.getURI();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects(EClass type, QualifiedName name, boolean ignoreCase) {
		return Iterables.transform(delegate.getExportedObjects(type, name, ignoreCase), this::wrapIfModule);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EClass type) {
		return Iterables.transform(delegate.getExportedObjectsByType(type), this::wrapIfModule);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByObject(EObject object) {
		return Iterables.transform(delegate.getExportedObjectsByObject(object), this::wrapIfModule);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects() {
		return Iterables.transform(delegate.getExportedObjects(), this::wrapIfModule);
	}

	@Override
	public Iterable<QualifiedName> getImportedNames() {
		return delegate.getImportedNames();
	}

	@Override
	public Iterable<IReferenceDescription> getReferenceDescriptions() {
		return delegate.getReferenceDescriptions();
	}

	/**
	 * (non-API)
	 *
	 * Returns with the delegate resource description which is actually an instance of {@code ReferenceDescriptionImpl},
	 * hence {@link EObject}. Used by the persister at graceful application shutdown.
	 *
	 * @return the delegate resource description.
	 */
	public IResourceDescription getDelegate() {
		return delegate;
	}

	private IEObjectDescription wrapIfModule(IEObjectDescription desc) {
		if (desc.getEClass() == TypesPackage.eINSTANCE.getTModule())
			return new EObjectDescriptionWithoutModuleUserData(desc);
		return desc;
	}

	private static final class EObjectDescriptionWithoutModuleUserData extends ForwardingEObjectDescription {

		public EObjectDescriptionWithoutModuleUserData(IEObjectDescription delegate) {
			super(delegate);
		}

		@Override
		public String getUserData(String key) {
			if (UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT.equals(key))
				return null;
			return delegate().getUserData(key);
		}

		@Override
		public String[] getUserDataKeys() {
			// no need to remove UserDataMapper#USER_DATA_KEY_SERIALIZED_SCRIPT here, UserDataMapper can deal with the
			// situation that the key exists but the value is null
			return delegate().getUserDataKeys();
		}
	}
}
