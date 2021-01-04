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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.xtext.workspace.BuildOrderFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;

/**
 * Customized in order to ignore dependencies of {@link ProjectType#PLAINJS plain-JS} projects.
 */
public class N4JSBuildOrderInfoComputer extends BuildOrderFactory.BuildOrderInfoComputer {

	@Override
	protected Set<String> getDependencies(ProjectConfigSnapshot pc) {
		Set<String> dependencies = super.getDependencies(pc);
		ProjectType type = pc instanceof N4JSProjectConfigSnapshot ? ((N4JSProjectConfigSnapshot) pc).getType() : null;
		if (type == ProjectType.PLAINJS) {
			// ignore plainJS dependencies of plain-JS projects, because
			// (1) they are irrelevant for the build order of N4JS code,
			// (2) npm packages sometimes declare cyclic dependencies (and we must not show errors for those cycles)
			Set<String> depsToScopeN4JSD = new HashSet<>();
			for (String depName : dependencies) {
				if (depName.startsWith(N4JSGlobals.N4JSD_SCOPE)) {
					depsToScopeN4JSD.add(depName);
				}
			}
			return depsToScopeN4JSD;
		}
		return dependencies;
	}
}
