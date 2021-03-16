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
package org.eclipse.n4js.ide;

import org.eclipse.n4js.projectModel.internal.N4JSCoreNEW;
import org.eclipse.n4js.xtext.server.ResourceTaskManager;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class N4JSIdeCoreNEW extends N4JSCoreNEW {

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Override
	public XtextResourceSet createResourceSet() {
		return resourceTaskManager.createTemporaryResourceSet();
	}
}
