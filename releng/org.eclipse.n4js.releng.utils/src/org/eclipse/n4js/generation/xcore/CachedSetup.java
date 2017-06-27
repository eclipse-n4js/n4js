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
package org.eclipse.n4js.generation.xcore;

import org.eclipse.emf.mwe2.runtime.Mandatory;
import org.eclipse.xtext.ISetup;

import com.google.inject.Injector;

/**
 */
public class CachedSetup implements ISetup {

	private ISetup delegate;

	private Injector injector;

	/**
	 * @return the delegate
	 */
	public ISetup getDelegate() {
		return delegate;
	}

	/**
	 * @param delegate the delegate to set
	 */
	@Mandatory
	public void setDelegate(ISetup delegate) {
		this.delegate = delegate;
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		if (injector == null) {
			injector = delegate.createInjectorAndDoEMFRegistration();
		}
		return injector;
	}

}
