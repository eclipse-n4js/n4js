/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping;

import org.eclipse.n4js.utils.TameAutoClosable;

/**
 *
 */
// is not a Singleton due to field 'suppressCrossFileResolutionOfIdentifierRef'
public class N4JSScopeProviderLocalOnly extends N4JSScopeProvider {

	/** True: Proxies of IdentifierRefs are only resolved within the resource. Otherwise, the proxy is returned. */
	private boolean suppressCrossFileResolutionOfIdentifierRef = false;

	/** Returns an {@link TameAutoClosable} that suppresses cross-file resolution while it is open. */
	public TameAutoClosable newCrossFileResolutionSuppressor() {
		TameAutoClosable tac = new TameAutoClosable() {
			private boolean tmpSuppressCrossFileResolutionOfIdentifierRef = init();

			private boolean init() {
				this.tmpSuppressCrossFileResolutionOfIdentifierRef = suppressCrossFileResolutionOfIdentifierRef;
				suppressCrossFileResolutionOfIdentifierRef = true;
				return tmpSuppressCrossFileResolutionOfIdentifierRef;
			}

			@Override
			public void close() {
				suppressCrossFileResolutionOfIdentifierRef = tmpSuppressCrossFileResolutionOfIdentifierRef;
			}
		};
		return tac;
	}

	@Override
	protected boolean isSuppressCrossFileResolutionOfIdentifierRefs() {
		return suppressCrossFileResolutionOfIdentifierRef;
	}
}
