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
package org.eclipse.n4js.ui.building;

import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.xtext.builder.IXtextBuilderParticipant.BuildType;

/**
 * Allows to track the current build type for a given project, i.e. set and remember the build type of the current
 * project.
 */
public class N4JSBuildTypeTracker {
	private static ConcurrentHashMap<IProject, BuildType> buildTypes = new ConcurrentHashMap<>();

	/**
	 * Store the build type for the given project.
	 * 
	 * @param project
	 *            the built project. May not be null.
	 * @param type
	 *            the build type. May not be null.
	 */
	public static void setBuildType(IProject project, BuildType type) {
		buildTypes.put(project, type);
	}

	/**
	 * Obtain the build type for the given project.
	 *
	 * @param project
	 *            the built project. May not be null.
	 */
	public static BuildType getBuildType(IProject project) {
		return buildTypes.get(project);
	}

	/**
	 * Remove the project from the build type cache.
	 *
	 * @param project
	 *            the built project. May not be null.
	 */
	public static void clearBuildType(IProject project) {
		buildTypes.remove(project);
	}
}
