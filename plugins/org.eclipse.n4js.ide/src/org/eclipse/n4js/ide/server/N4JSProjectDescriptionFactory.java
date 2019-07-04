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

import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.ide.server.DefaultProjectDescriptionFactory;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSProjectDescriptionFactory extends DefaultProjectDescriptionFactory {

	@Override
	public ProjectDescription getProjectDescription(IProjectConfig config) {
		ProjectDescription projectDescription = super.getProjectDescription(config);
		IN4JSProject casted = (IN4JSProject) config;
		ImmutableList<String> dependencies = FluentIterable.from(
				casted.getDependencies()).transform(IN4JSProject::getProjectName).toList();
		projectDescription.setDependencies(dependencies);
		return projectDescription;
	}

}
