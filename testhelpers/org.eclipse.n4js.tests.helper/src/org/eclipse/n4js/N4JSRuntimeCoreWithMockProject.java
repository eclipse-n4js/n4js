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
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.projectModel.IN4JSProject;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class N4JSRuntimeCoreWithMockProject extends N4JSRuntimeCore {

	final IN4JSProject mockProject;

	/***/
	@Inject
	public N4JSRuntimeCoreWithMockProject(FileBasedWorkspace workspace, MockN4JSModel model) {
		super(workspace, model);
		mockProject = createMockProject(model);
	}

	private IN4JSProject createMockProject(MockN4JSModel model) {
		N4JSProject project = new MockProject(MockProject.MOCK_URI, model);
		model.setMockProject(project);
		return project;

	}

	@Override
	public Optional<? extends IN4JSProject> findProject(URI nestedLocation) {
		if (nestedLocation == null) {
			return Optional.absent();
		}
		IN4JSProject result = mockProject;
		return Optional.fromNullable(result);
	}

}
