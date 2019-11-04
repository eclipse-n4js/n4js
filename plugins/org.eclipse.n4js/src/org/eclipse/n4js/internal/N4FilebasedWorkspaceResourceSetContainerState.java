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
package org.eclipse.n4js.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersState;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 */
public class N4FilebasedWorkspaceResourceSetContainerState extends ResourceSetBasedAllContainersState {

	@Inject
	private IN4JSCore core;

	/**
	 * @param handle
	 *            uri for the current project prefixed with {@code FileBasedWorkspace#N4FBPRJ}
	 * @return a list of visible projects in form of handles.
	 */
	@Override
	public List<String> getVisibleContainerHandles(String handle) {

		URI containerURI = FileBasedWorkspace.uriFrom(handle);

		List<String> visibleContainers = new ArrayList<>();
		// add self
		visibleContainers.add(handle);

		Optional<? extends IN4JSProject> project = core.findProject(containerURI);

		if (!project.isPresent()) {
			throw new IllegalStateException("No project with handle '" + handle + "' known in current In4jscore.");
		}

		Iterable<? extends IN4JSProject> dps = project.get().getSortedDependencies();
		// map uri to handle-form and add.
		dps.forEach(d -> visibleContainers.add(FileBasedWorkspace.handleFrom(d.getLocation().toURI())));

		return visibleContainers;
	}
}
