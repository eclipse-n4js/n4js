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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskContext;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager;
import org.eclipse.n4js.xtext.ide.server.util.XChunkedResourceDescriptions;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Overwritten to reference {@link N4JSResource}
 */
public class N4JSResourceTaskContext extends ResourceTaskContext {

	@Override
	protected Map<?, ?> getLoadOptions(XtextResource resource) {
		Map<Object, Object> options = new HashMap<>();
		options.put(N4JSResource.OPTION_CLEAR_FUNCTION_BODIES, false);
		return options;
	}

	@Override
	public synchronized void initialize(
			ResourceTaskManager parent,
			URI uri,
			boolean isTemporary,
			XChunkedResourceDescriptions index,
			WorkspaceConfigSnapshot workspaceConfig) {

		super.initialize(parent, uri, isTemporary, index, workspaceConfig);
		BuiltInTypeScope.get(getResourceSet()).getNullType(); // force loading built-ins to avoid deadlock later
	}
}
