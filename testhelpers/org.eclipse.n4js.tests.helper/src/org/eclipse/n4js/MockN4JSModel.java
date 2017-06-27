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
package org.eclipse.n4js;

import org.eclipse.emf.common.util.URI;

import com.google.inject.Inject;

import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSProject;

/**
 */
public class MockN4JSModel extends N4JSModel {

	private N4JSProject project;

	/***/
	@Inject
	public MockN4JSModel(InternalN4JSWorkspace workspace) {
		super(workspace);
	}

	/**
	 * Called during initialization of mock project.
	 */
	public void setMockProject(N4JSProject project) {
		this.project = project;
	}

	@Override
	public N4JSProject findProjectWith(URI nestedLocation) {
		return project;
	}

	/***/
	public InternalN4JSWorkspace getInternalWorkspaceForMocks() {
		return this.getInternalWorkspace();
	}

}
