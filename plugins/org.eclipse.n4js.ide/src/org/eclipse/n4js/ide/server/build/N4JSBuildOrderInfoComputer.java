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
package org.eclipse.n4js.ide.server.build;

import java.util.Set;

import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.BuildOrderFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;

import com.google.common.collect.Sets;

/**
 * Customized in order to ignore dependencies of {@link ProjectType#PLAINJS plain-JS} projects.
 */
public class N4JSBuildOrderInfoComputer extends BuildOrderFactory.BuildOrderInfoComputer {

	@Override
	protected Set<String> getDependencies(ProjectConfigSnapshot pc) {
		Set<String> dependencies = super.getDependencies(pc);
		N4JSProjectConfigSnapshot n4pcs = pc instanceof N4JSProjectConfigSnapshot
				? (N4JSProjectConfigSnapshot) pc
				: null;

		if (n4pcs != null) {
			ProjectDescription prjDescr = n4pcs.getProjectDescription();

			// keep in sync with ProjectDiscoveryHelper#findDependencies()
			if (!prjDescr.hasN4JSNature() && !prjDescr.isWorkspaceRoot() && prjDescr.getTypes() == null) {
				// ignore dependencies of plain-JS projects to non-n4js-lib projects, because
				// (1) they are irrelevant for the build order of N4JS code,
				// (2) npm packages sometimes declare cyclic dependencies (and we must not show errors for those cycles)
				Set<String> n4jsDeps = Sets.filter(dependencies, dep -> n4pcs.isKnownDependency(dep));
				return n4jsDeps;
			}
		}
		return dependencies;
	}
}
