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
package org.eclipse.n4js.scoping;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

/**
 * Delegate implementation of {@link IUsageAwareEObjectDescription} that notifies the description when it is bound to a
 * name by the scoping.
 */
public abstract class UsageAwareObjectDescription<T extends IEObjectDescription>
		implements IUsageAwareEObjectDescription {

	private final T delegate;
	private final ImportSpecifier origin;

	/**
	 * Instantiates a new {@link UsageAwareObjectDescription} with the given description delegate.
	 */
	public UsageAwareObjectDescription(T delegate, ImportSpecifier origin) {
		this.delegate = delegate;
		this.origin = origin;
	}

	@Override
	public QualifiedName getName() {
		return delegate.getName();
	}

	@Override
	public QualifiedName getQualifiedName() {
		return delegate.getQualifiedName();
	}

	@Override
	public EObject getEObjectOrProxy() {
		return delegate.getEObjectOrProxy();
	}

	@Override
	public URI getEObjectURI() {
		return delegate.getEObjectURI();
	}

	@Override
	public EClass getEClass() {
		return delegate.getEClass();
	}

	@Override
	public String getUserData(String key) {
		return delegate.getUserData(key);
	}

	@Override
	public String[] getUserDataKeys() {
		return delegate.getUserDataKeys();
	}

	/**
	 * Returns the delegate description.
	 */
	protected T getDelegate() {
		return this.delegate;
	}

	@Override
	public ImportSpecifier getOrigin() {
		return this.origin;
	}

	/**
	 * This method is invoked when this {@link IEObjectDescription} used to bind a name.
	 */
	@Override
	public abstract void markAsUsed();

	@Override
	public abstract void recordOrigin(IdentifierRef identRef);
}
