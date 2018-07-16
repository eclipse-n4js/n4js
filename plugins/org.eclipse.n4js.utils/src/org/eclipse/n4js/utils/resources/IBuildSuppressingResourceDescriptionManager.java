/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * If an Xtext {@link org.eclipse.xtext.resource.IResourceDescription.Manager IResourceDescription.Manager} implements
 * this interface, resources of the corresponding Xtext language will only be built if method
 * {@link #isToBeBuilt(URI, Resource)} returns <code>true</code>.
 */
public interface IBuildSuppressingResourceDescriptionManager {

	/**
	 * Tells if the given resource with the given URI should be built or ignored by Xtext's incremental builder.
	 * Returning <code>false</code> here only affects the incremental builder (e.g. turns off creation of issue markers
	 * in the problems view) and does not turn off syntax highlighting, validation, etc. in an open Xtext editor.
	 */
	public boolean isToBeBuilt(URI uri, Resource resource);
}
