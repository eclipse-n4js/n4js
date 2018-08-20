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
package org.eclipse.n4js.generator.headless;

/**
 * Signaling severe Problems in Compilation
 */
public class N4JSCompileErrorException extends N4JSCompileException implements N4ProgressStateRecorder.IProgressState {

	private final String projectName;

	/**
	 * @param message
	 *            user-message
	 * @param projectName
	 *            erroneous project
	 */
	public N4JSCompileErrorException(String message, String projectName) {
		super(message);
		this.projectName = projectName;
	}

	/**
	 * @param message
	 *            user-message
	 * @param projectName
	 *            erroneous project
	 * @param t
	 *            nested cause
	 */
	public N4JSCompileErrorException(String message, String projectName, Throwable t) {
		super(message, t);
		this.projectName = projectName;
	}

	/**
	 * @return name of erroneous project
	 */
	public String getProjectName() {
		return projectName;
	}
}
