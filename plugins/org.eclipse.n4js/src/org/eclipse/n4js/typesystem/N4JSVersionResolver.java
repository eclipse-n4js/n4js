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
package org.eclipse.n4js.typesystem;

import org.eclipse.n4js.ts.typeRefs.TypeArgument;

/**
 * Default implementation of {@link VersionResolver} that always returns the actual parameter. This implementation can
 * be used in languages where versioning does not apply.
 */
public class N4JSVersionResolver implements VersionResolver {
	@Override
	public <T extends TypeArgument, S> T resolveVersion(T typeRef, S versionedReference) {
		return typeRef;
	}

	@Override
	public <T extends TypeArgument> T resolveVersion(T typeRef, int contextVersion) {
		return typeRef;
	}
}
