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
package org.eclipse.n4js.ide.server;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.ide.server.DefaultProjectDescriptionFactory;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.inject.Inject;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSProjectDescriptionFactory extends DefaultProjectDescriptionFactory {

	@Inject
	private N4JSModel model;

	@Override
	public ProjectDescription getProjectDescription(IProjectConfig config) {
		ProjectDescription projectDescription = super.getProjectDescription(config);
		List<? extends IN4JSProject> deps = model.getDependencies((N4JSProject) config, false);

		Set<String> depNames = new HashSet<>();
		for (IN4JSProject dep : deps) {
			depNames.add(dep.getName());
		}
		projectDescription.setDependencies(new LinkedList<>(depNames));
		return projectDescription;
	}

}
