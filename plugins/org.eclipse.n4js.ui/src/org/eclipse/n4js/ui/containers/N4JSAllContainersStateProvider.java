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
package org.eclipse.n4js.ui.containers;

import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Provider to deliver container state to use. We have to use provider as elsewhere in the Xtext framework the container
 * state is accessed via provider.
 */
public class N4JSAllContainersStateProvider implements Provider<IAllContainersState> {
	@Inject
	N4JSAllContainersState instance;

	@Override
	public IAllContainersState get() {
		return instance;
	}
}
