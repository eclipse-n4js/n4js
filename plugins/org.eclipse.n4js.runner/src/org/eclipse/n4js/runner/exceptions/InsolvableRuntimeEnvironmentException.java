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
package org.eclipse.n4js.runner.exceptions;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 * Runtime exception indicating that the runtime environment resolution failed for a particular N4JS project.
 */
public class InsolvableRuntimeEnvironmentException extends RuntimeException {

	private static final String MSG_TEMPLATE = "Cannot resolve execution environment for the project {0} of type {1}.";

	/**
	 * Creates a new runtime exception indicating that the runtime environment cannot be solved for the project
	 * argument.
	 *
	 * @param project
	 *            the project where the RE resolution failed.
	 */
	public InsolvableRuntimeEnvironmentException(final IN4JSProject project) {
		super(getMessage(checkNotNull(project, "project")));
	}

	private static final String getMessage(final IN4JSProject project) {
		return format(MSG_TEMPLATE, project.getProjectName(),
				PackageJsonUtils.getProjectTypeStringRepresentation(project.getProjectType()));
	}
}
