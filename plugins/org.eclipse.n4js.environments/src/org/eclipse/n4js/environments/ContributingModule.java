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
package org.eclipse.n4js.environments;

import org.eclipse.xtext.ui.resource.IResourceSetInitializer;

import com.google.inject.Binder;
import com.google.inject.Module;

import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;

/**
 * Registered in plugin.xml ("org.eclipse.xtext.ui.shared.sharedStateContributingModule"), most importantly it binds the
 * {@link BuiltInSchemeResourceSetInitializer}.
 */
public class ContributingModule implements Module {

	/**
	 * Every binding must explicitly mentioned here.
	 */
	@Override
	public void configure(Binder binder) {
		binder.bind(IResourceSetInitializer.class).to(BuiltInSchemeResourceSetInitializer.class);
		binder.bind(BuiltInSchemeRegistrar.class);
		binder.bind(ClassLoader.class).toInstance(ContributingModule.class.getClassLoader());
	}

}
