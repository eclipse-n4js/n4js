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
package org.eclipse.n4js.ide.tests.helper.server.xt;

import org.eclipse.emf.ecore.EObject;

/***/
public class EObjectCoveringRegion implements IEObjectCoveringRegion {
	final EObject eObj;
	int offset;

	/***/
	public EObjectCoveringRegion(EObject eObj, int offset) {
		this.eObj = eObj;
		this.offset = offset;
	}

	@Override
	public EObject getEObject() {
		return eObj;
	}

	@Override
	public int getOffset() {
		return offset;
	}
}