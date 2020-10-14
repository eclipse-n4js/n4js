/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.util;

import org.eclipse.n4js.ide.xtext.server.build.WorkspaceAwareResourceSet;
import org.eclipse.n4js.ts.scoping.builtin.ConfiguredResourceSetProvider;
import org.eclipse.n4js.ts.scoping.builtin.DelegatingConfiguredResourceSetProvider;

import com.google.inject.Inject;

/**
 * A provider creating new {@link WorkspaceAwareResourceSet}s and configuring them using the singleton
 * {@link ConfiguredResourceSetProvider}.
 */
public class ConfiguredWorkspaceAwareResourceSetProvider
		extends DelegatingConfiguredResourceSetProvider<WorkspaceAwareResourceSet> {

	/** Creates a new {@link ConfiguredWorkspaceAwareResourceSetProvider}. */
	@Inject
	public ConfiguredWorkspaceAwareResourceSetProvider(ConfiguredResourceSetProvider delegate) {
		super(delegate);
	}

	@Override
	protected Class<WorkspaceAwareResourceSet> getType() {
		return WorkspaceAwareResourceSet.class;
	}
}
