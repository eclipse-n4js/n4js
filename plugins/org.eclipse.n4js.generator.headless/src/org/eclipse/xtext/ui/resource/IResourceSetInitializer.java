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
package org.eclipse.xtext.ui.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;

/**
 * Copied from {@code org.eclipse.xtext.ui.resource.IResourceSetInitializer} to workaround mixed UI and non-UI
 * dependencies.
 *
 * @see <a href="https://github.com/eclipse/xtext-eclipse/issues/311">xtext-eclipse mixes UI and non-UI dependencies</a>
 */
public interface IResourceSetInitializer {

	/**
	 * Initializes the resource set in the context of the given project. If the resource set is an
	 * {@link XtextResourceSet}, the implementor may choose the set the
	 * {@link XtextResourceSet#setClasspathURIContext(Object) classpath context}. Clients should be careful to not
	 * override the settings of other clients, though.
	 */
	void initialize(ResourceSet resourceSet, IProject project);

}
