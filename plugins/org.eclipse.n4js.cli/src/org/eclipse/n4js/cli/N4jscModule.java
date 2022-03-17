/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli;

import org.eclipse.n4js.ide.AbstractN4JSIdeModule;
import org.eclipse.n4js.xtext.ide.server.build.DefaultBuildRequestFactory;

/**
 * Use this class to override bindings for the cli use case.
 */
@SuppressWarnings({ "javadoc" })
public class N4jscModule extends AbstractN4JSIdeModule {

	public Class<? extends DefaultBuildRequestFactory> bindDocumentExtensions() {
		return N4jscBuildRequestFactory.class;
	}
}
