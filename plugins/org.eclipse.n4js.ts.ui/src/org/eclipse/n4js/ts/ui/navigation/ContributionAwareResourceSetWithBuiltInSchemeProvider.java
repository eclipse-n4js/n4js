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
package org.eclipse.n4js.ts.ui.navigation;

import org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInSchemeProvider;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Inject;

/**
 * Provides a resource set with builtin scheme.
 */
public class ContributionAwareResourceSetWithBuiltInSchemeProvider extends ResourceSetWithBuiltInSchemeProvider {

	/**
	 * Standard constructor.
	 */
	@Inject
	public ContributionAwareResourceSetWithBuiltInSchemeProvider(
			EffectiveRegistrarProvider registrar,
			ClassLoader classLoader, UriExtensions uriExtensions) {
		super(registrar.get(), classLoader, uriExtensions);
	}

}
