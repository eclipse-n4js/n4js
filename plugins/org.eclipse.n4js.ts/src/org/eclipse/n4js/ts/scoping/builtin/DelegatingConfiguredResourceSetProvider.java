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
package org.eclipse.n4js.ts.scoping.builtin;

import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Delegates to the singleton {@link ConfiguredResourceSetProvider} for configuring the newly created resource sets.
 */
// no need to make this @Singleton (it's sufficient that the delegate ConfiguredResourceSetProvider is a singleton)
public abstract class DelegatingConfiguredResourceSetProvider<T extends SynchronizedXtextResourceSet>
		implements Provider<T> {

	private final ConfiguredResourceSetProvider delegate;

	/** Creates a new {@link DelegatingConfiguredResourceSetProvider}. */
	@Inject
	public DelegatingConfiguredResourceSetProvider(ConfiguredResourceSetProvider delegate) {
		this.delegate = delegate;
	}

	/** Returns the type of resource set to be created. */
	protected abstract Class<T> getType();

	@Override
	public T get() {
		Class<T> type = getType();
		return delegate.getOfType(type);
	}
}
