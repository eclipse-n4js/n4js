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
package org.eclipse.n4js.xtext.server.util;

import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Manager;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;

/**
 * A {@link Delta resource description delta} used for resources in removed projects. It is the same as an ordinary
 * {@link DefaultResourceDescriptionDelta}, except that {@link Delta#getNew() new} will always be <code>null</code> and
 * that the name of the containing project is provided.
 * <p>
 * The motivation for this class is that language-specific {@link Manager IResourceDescription.Manager#isAffected(Delta,
 * IResourceDescription) isAffected-computations} will often need to know the containing project of deltas but might not
 * be able to obtain this information for contents of removed projects.
 */
public class RemovedProjectResourceDescriptionDelta extends DefaultResourceDescriptionDelta {

	private final String removedProjectName;

	/** Creates a new {@link RemovedProjectResourceDescriptionDelta}. */
	public RemovedProjectResourceDescriptionDelta(String removedProjectName, IResourceDescription old) {
		super(old, null);
		this.removedProjectName = removedProjectName;
	}

	/**
	 * Returns the name of the (now removed) project that contained the (now removed) source file represented by this
	 * delta.
	 */
	public String getRemovedProjectName() {
		return removedProjectName;
	}
}
