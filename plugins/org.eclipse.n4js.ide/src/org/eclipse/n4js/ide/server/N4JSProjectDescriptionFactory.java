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

import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.xtext.server.XDefaultProjectDescriptionFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.xtext.resource.impl.ProjectDescription;

/**
 * Creates {@link ProjectDescription}s for {@link ProjectConfigSnapshot}s: Adds dependencies.
 */
public class N4JSProjectDescriptionFactory extends XDefaultProjectDescriptionFactory {

	@Override
	public ProjectDescription getProjectDescription(ProjectConfigSnapshot config) {
		ProjectDescription projectDescription = super.getProjectDescription(config);
		projectDescription.getDependencies().clear();
		N4JSProjectConfigSnapshot casted = (N4JSProjectConfigSnapshot) config;
		if (casted.getType() == ProjectType.PLAINJS) {
			// see N4JSProjectBuildOrderInfo for why we ignore dependencies of PLAINJS projects:
			return projectDescription;
		}
		projectDescription.getDependencies().addAll(casted.getDependencies());
		return projectDescription;
	}

}
