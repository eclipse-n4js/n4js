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
package org.eclipse.n4js.n4mf.ui.internal

import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.ui.editor.DirtyStateEditorSupport

import static org.eclipse.n4js.n4mf.utils.N4MFConstants.N4MF_MANIFEST

/**
 * Dirty state editor support for N4JS manifests. Resolves the resource while loading from the resource set
 * if pointing to a manifest file/resource.
 */
class N4MFDirtyStateEditorSupport extends DirtyStateEditorSupport {


		override def processDelta(IResourceDescription.Delta delta, Resource context, List<Resource> result) {
			val resourceSet = context.resourceSet;
			// If represents a N4JS manifest file and the new resource description is not null, in other words, not deleted.
			val resolveResource = N4MF_MANIFEST.equals(delta.uri.lastSegment) && null !== delta.^new;
			val resourceInResourceSet = resourceSet.getResource(delta.uri, resolveResource);
			if (null !== resourceInResourceSet && resourceInResourceSet !== context) {
				result.add(resourceInResourceSet);
			}
		}

}
