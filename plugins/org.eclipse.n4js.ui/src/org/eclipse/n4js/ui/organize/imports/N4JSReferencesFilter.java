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
package org.eclipse.n4js.ui.organize.imports;

/**
 * N4JS is not doing any extra filtering on proxy cross refs, but sub languages might need to take advantage of this
 * variation point.
 */
public class N4JSReferencesFilter implements IReferenceFilter {

	@Override
	public boolean test(ReferenceProxyInfo proxyInfo) {
		return true;
	}
}
