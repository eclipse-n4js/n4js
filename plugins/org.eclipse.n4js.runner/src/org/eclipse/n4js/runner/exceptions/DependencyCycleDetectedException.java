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

import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 * Runtime exception indicating that a dependency cycle was detected for a particular project.
 */
public class DependencyCycleDetectedException extends RuntimeException {

	private static final String MSG_TEMPLATE = "A dependency cycle was detected for project {0}.";

	/**
	 * Creates a new exception instance for the {@link IN4JSProject project} argument.
	 *
	 * @param project
	 *            the project which has an invalid N4JS manifest file.
	 */
	public DependencyCycleDetectedException(final IN4JSProject project) {
		super(getMessage(checkNotNull(project, "project")));
	}

	private static final String getMessage(final IN4JSProject project) {
		return format(MSG_TEMPLATE, project.getProjectId());
	}
}
