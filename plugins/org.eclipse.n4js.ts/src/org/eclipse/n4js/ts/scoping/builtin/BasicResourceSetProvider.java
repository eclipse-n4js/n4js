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
package org.eclipse.n4js.ts.scoping.builtin;

import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;

import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class BasicResourceSetProvider implements Provider<SynchronizedXtextResourceSet> {

	@Override
	public SynchronizedXtextResourceSet get() {
		return new SynchronizedXtextResourceSet();
	}

}
