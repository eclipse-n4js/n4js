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
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.util.UriExtensions;

@SuppressWarnings("javadoc")
public class FileURITest extends AbstractSafeURITest<FileURI> {

	@Override
	protected URI createRawURI(String withoutScheme) {
		URI result = URI.createFileURI(withoutScheme);
		return new UriExtensions().withEmptyAuthority(result);
	}

	@Override
	protected FileURI doCreate(URI from) {
		return new FileURI(from);
	}

}
