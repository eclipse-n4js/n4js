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

import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;
import org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInSchemeProvider;
import org.eclipse.n4js.ts.ui.navigation.BuiltInSchemeResourceSetInitializer;
import org.eclipse.n4js.ts.ui.navigation.BuiltinSchemeUriMapperContribution;
import org.eclipse.n4js.ts.ui.navigation.ContributionAwareResourceSetWithBuiltInSchemeProvider;
import org.eclipse.n4js.ts.ui.navigation.EffectiveRegistrarProvider;
import org.eclipse.xtext.ui.resource.IResourceSetInitializer;
import org.eclipse.xtext.ui.resource.IStorage2UriMapperContribution;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.PrivateModule;

/**
 * Registers a {@link BuiltinSchemeUriMapperContribution} and a {@link BuiltInSchemeResourceSetInitializer} to be
 * available via a {@link ISharedStateContributionRegistry}.
 */
public class ContributingModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.install(new PrivateModule() {
			@Override
			protected void configure() {
				// we bind the internally required services of the two exposed bindings
				// in a private module. This is to avoid them being available via
				// ISharedStateContributionRegistry.getContributedInstances
				//
				// This is effectively a means to encapsulate the implementation details and hide
				// them from the globally available services
				bind(IStorage2UriMapperContribution.class).to(BuiltinSchemeUriMapperContribution.class);
				bind(IResourceSetInitializer.class).to(BuiltInSchemeResourceSetInitializer.class);
				bind(EffectiveRegistrarProvider.class);
				bind(BuiltInSchemeRegistrar.class);
				bind(ResourceSetWithBuiltInSchemeProvider.class)
						.to(ContributionAwareResourceSetWithBuiltInSchemeProvider.class);
				bind(UriExtensions.class);
				bind(ClassLoader.class).toInstance(getClass().getClassLoader());

				// Here we expose the two services that are supposed to be publicly available.
				expose(IResourceSetInitializer.class);
				expose(IStorage2UriMapperContribution.class);
			}
		});
	}

}
