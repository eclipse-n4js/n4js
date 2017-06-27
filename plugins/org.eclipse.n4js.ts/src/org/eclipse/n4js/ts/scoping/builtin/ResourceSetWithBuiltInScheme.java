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
package org.eclipse.n4js.ts.scoping.builtin;

import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;

import com.google.inject.Inject;

/**
 * Default binding in case of standalone mode, in UI (OSGi mode) the super type {@link SynchronizedXtextResourceSet} is
 * used with the extension point (cf. IResourceSetInitializer and n4 implementations)
 */
public class ResourceSetWithBuiltInScheme extends SynchronizedXtextResourceSet {

	@Inject
	private void configureWith(BuiltInSchemeRegistrar registrar) {
		registrar.registerScheme(this);
	}

}
