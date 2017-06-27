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
package org.eclipse.n4js.ts.ui.internal;

import org.eclipse.xtext.ui.resource.IStorage2UriMapperContribution;

import com.google.inject.Binder;
import com.google.inject.Module;

import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;
import org.eclipse.n4js.ts.ui.navigation.BuiltinSchemeUriMapperContribution;

/**
 */
public class ContributingModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(IStorage2UriMapperContribution.class).to(BuiltinSchemeUriMapperContribution.class);
		binder.bind(BuiltInSchemeRegistrar.class);
		binder.bind(ClassLoader.class).toInstance(getClass().getClassLoader());
	}

}
