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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xcore.XcoreStandaloneSetup;
import org.eclipse.emf.ecore.xcore.XcoreStandaloneSetup.XcoreStandaloneRuntimeModule.XcoreResourceSetInitializer;
import org.eclipse.xtext.formatting.ILineSeparatorInformation;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 */
public class NewlineAwareXcoreStandaloneSetup extends XcoreStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(new XcoreStandaloneRuntimeModule() {
			@SuppressWarnings("unused")
			public Class<? extends ILineSeparatorInformation> bindILineSeparatorInformation() {
				return UnixLineSeparator.class;
			}

			@SuppressWarnings("unused")
			public Class<? extends XcoreResourceSetInitializer> bindXcoreResourceSetInitializer() {
				return CreateResourceGuardedResourceSetInitializer.class;
			}
		});
	}

	private static class CreateResourceGuardedResourceSetInitializer extends XcoreResourceSetInitializer {
		@Inject
		Injector injector;

		@Override
		public XtextResourceSet getInitializedResourceSet() {
			XtextResourceSet resourceSet = new XtextResourceSet() {
				@Override
				public Resource createResource(URI uri) {
					Resource result = getResource(uri, false);
					if (result == null)
						return super.createResource(uri);
					return result;
				}
			};
			injector.injectMembers(resourceSet);
			resourceSet.eAdapters().add(new AllContainerAdapter(resourceSet));
			return resourceSet;
		}
	}

	private static class UnixLineSeparator implements ILineSeparatorInformation {

		@Override
		public String getLineSeparator() {
			return "\n";
		}

	}

}
