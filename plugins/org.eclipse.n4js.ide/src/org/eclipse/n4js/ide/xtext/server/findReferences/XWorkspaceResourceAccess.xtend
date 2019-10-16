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
package org.eclipse.n4js.ide.xtext.server.findReferences


import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess
import org.eclipse.xtext.util.concurrent.IUnitOfWork
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager

/**
 * @author kosyakov - Initial contribution and API
 * @since 2.11
 */
@FinalFieldsConstructor
class XWorkspaceResourceAccess implements IResourceAccess {

	val XWorkspaceManager workspaceManager

	override <R> readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
		return workspaceManager.doRead(targetURI) [ document, resource |
			if (resource === null) {
				return null
			}
			return work.exec(resource.resourceSet)
		]
	}

}