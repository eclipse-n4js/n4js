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
package org.eclipse.n4js.utils.resources;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.eclipse.core.resources.IWorkspace;

/**
 * Proxy invocation handler for {@link IWorkspace workspace}.
 */
/* default */ class ProxyWorkspaceInvocationHandler implements InvocationHandler {

	/* default */ static IWorkspace createNewProxyWorkspace() {
		return (IWorkspace) Proxy.newProxyInstance(
				ProxyWorkspaceInvocationHandler.class.getClassLoader(),
				new Class<?>[] { IWorkspace.class },
				new ProxyWorkspaceInvocationHandler());
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return null;
	}

}
