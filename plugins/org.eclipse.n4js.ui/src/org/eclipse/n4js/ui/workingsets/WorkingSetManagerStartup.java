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
package org.eclipse.n4js.ui.workingsets;

import static org.eclipse.core.resources.IResourceDelta.ADDED;
import static org.eclipse.core.resources.IResourceDelta.CHANGED;
import static org.eclipse.core.resources.IResourceDelta.OPEN;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IStartup;

import com.google.inject.Inject;

/**
 * IDE start-up hook to register working set manager related listeners.
 */
public class WorkingSetManagerStartup implements IStartup {

	private static final Logger LOGGER = Logger.getLogger(WorkingSetManagerStartup.class);

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	@Inject
	private ManualAssociationAwareWorkingSetManager associationAwareWorkingSetManager;

	@Override
	public void earlyStartup() {
		associationAwareWorkingSetManager.registerToPropertyChangeListener();

		// This will automatically register the proper listeners to keep the different working set managers in sync.
		workingSetManagerBroker.getWorkingSetManagers();

		// Listener to refresh navigator on project creation or on open/close event.
		getWorkspace().addResourceChangeListener(event -> {
			if (event != null && event.getDelta() != null) {
				try {
					event.getDelta().accept(delta -> {
						final IResource resource = delta.getResource();
						if (resource instanceof IWorkspaceRoot) {
							return true;
						} else if (resource instanceof IProject) {
							if (ADDED == delta.getKind()
									|| (CHANGED == delta.getKind() && (delta.getFlags() & OPEN) != 0)) {
								workingSetManagerBroker.refreshNavigator();
							}
						}
						return false;
					});
				} catch (final CoreException e) {
					LOGGER.error("Error occurred while visiting resource delta.", e);
				}
			}
		});
	}

}
