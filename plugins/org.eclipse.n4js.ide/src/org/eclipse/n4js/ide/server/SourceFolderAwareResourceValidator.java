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
package org.eclipse.n4js.ide.server;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ide.xtext.server.ResourceTaskManager;
import org.eclipse.n4js.validation.N4JSResourceValidator;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.IProjectConfigProvider;

import com.google.inject.Inject;

/**
 * A custom resource validator that will not create issues for resources which are contained in folders that are not
 * marked as source containers.
 */
@SuppressWarnings("restriction")
public class SourceFolderAwareResourceValidator extends N4JSResourceValidator {

	@Inject
	private IProjectConfigProvider projectConfigProvider;

	@Inject
	private ResourceTaskManager resourceTaskManager;

	/**
	 * Suppresses issues of resources that are not from a source folder. This is intended for things like "external
	 * libraries" which might be located inside the workspace but are not actively being developed (e.g. contents of
	 * "node_modules" folders in a Javascript/npm workspace).
	 * <p>
	 * Note that this affects the builder and therefore closed files only; once a file is being opened issues will
	 * always become visible.
	 */
	@Override
	public List<Issue> validate(Resource resource, CheckMode mode, CancelIndicator cancelIndicator) {
		if (!isInSourceFolder(resource) && resourceTaskManager.currentContext() == null) {
			return Collections.emptyList();
		}
		return super.validate(resource, mode, cancelIndicator);
	}

	private boolean isInSourceFolder(Resource resource) {
		IProjectConfig config = projectConfigProvider.getProjectConfig(resource.getResourceSet());
		if (config != null) {
			return config.findSourceFolderContaining(resource.getURI()) != null;
		}
		return false;
	}
}
