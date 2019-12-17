/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.Singleton;

/**
 * A global, well known map in the injector that contains all the resources per URI that are currently loaded in the LSP
 * workspace.
 */
@Singleton
public class WorkspaceUriResourceMap extends ConcurrentHashMap<URI, Resource> {
	// nothing to do
}
