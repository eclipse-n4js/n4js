/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.xtext.ide.server.XLanguageServerImpl;
import org.eclipse.n4js.xtext.ide.server.build.XStatefulIncrementalBuilder;

/**
 * N4JS-specific adjustments to {@link XLanguageServerImpl}.
 */
public class N4JSStatefulIncrementalBuilder extends XStatefulIncrementalBuilder {

	/**
	 * Never unload built-in resources for performance considerations.
	 */
	@Override
	protected void unloadResource(URI uri) {
		if (!N4Scheme.isN4Scheme(uri)) {
			super.unloadResource(uri);
		}
	}
}
