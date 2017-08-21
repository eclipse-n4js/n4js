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
import org.eclipse.n4js.internal.N4JSProject;

/**
 */
public final class MockProject extends N4JSProject {

	/***/
	public static final URI MOCK_URI = URI.createURI("mock://should.be.never.used");

	/***/
	public MockProject(URI location, MockN4JSModel model) {
		super(location, false, model);
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public String getProjectId() {
		return ((MockN4JSModel) getModel()).getInternalWorkspaceForMocks().getProjectDescription(MOCK_URI)
				.getProjectId();
	}
}
