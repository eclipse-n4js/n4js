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
package org.eclipse.n4js.utils.resources

import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.runtime.Platform
import org.eclipse.xtend.lib.annotations.Delegate

import static org.eclipse.n4js.utils.resources.ProxyWorkspaceInvocationHandler.*
import static org.eclipse.core.resources.ResourcesPlugin.*

/**
 * Delegating {@link IWorkspace workspace} implementation, that simply delegates to the Eclipse workspace
 * if the {@link Platform platform} is running or delegates into a NOOP proxy workspace instance.
 */
class DelegatingWorkspace implements IWorkspace {

	@Delegate
	val IWorkspace delegate = if (Platform.running) workspace else createNewProxyWorkspace

	@Override
	override void checkpoint(boolean build) {
		// Delegating workspace is used only for external projects. It must not prepare any operation in the Eclipse workspace.
	}
}
