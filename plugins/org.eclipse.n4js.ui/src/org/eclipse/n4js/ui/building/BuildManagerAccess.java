/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.building;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.internal.events.BuildManager;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * Utility that allows to access the build managers internals.
 */
@SuppressWarnings("restriction")
public class BuildManagerAccess {

	private static final Method requestRebuild;
	private static final Field autoBuildJob;
	private static final Method forceBuild;
	static {
		try {
			requestRebuild = BuildManager.class.getDeclaredMethod("requestRebuild");
			requestRebuild.setAccessible(true);
			autoBuildJob = BuildManager.class.getDeclaredField("autoBuildJob");
			autoBuildJob.setAccessible(true);
			Class<?> autoBuildJobClass = autoBuildJob.getType();
			forceBuild = autoBuildJobClass.getDeclaredMethod("forceBuild");
			forceBuild.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Enforce a build.
	 */
	public static void needBuild() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if (workspace instanceof Workspace) {
			BuildManager buildManager = ((Workspace) workspace).getBuildManager();
			try {
				requestRebuild.invoke(buildManager);
				Object job = autoBuildJob.get(buildManager);
				forceBuild.invoke(job);
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new RuntimeException(e);
			}

		} else {
			throw new RuntimeException("Unexpected workspace implementation");
		}
	}
}
