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
package org.eclipse.n4js.tests.projectModel.locations;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;

@SuppressWarnings("javadoc")
public class PlatformResourceURITest extends AbstractSafeURITest<PlatformResourceURI> {

	@Override
	protected URI createRawURI(String withoutScheme) {
		return URI.createPlatformResourceURI(withoutScheme, true);
	}

	@Override
	protected PlatformResourceURI doCreate(URI from) {
		return new PlatformResourceURI(from);
	}

}
