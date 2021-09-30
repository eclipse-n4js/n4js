/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.utils;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;

/**
 * Some utilities for .d.ts export.
 */
public class DtsUtils {

	/** @see #isDtsExportableReference(Resource, TranspilerState) */
	public static boolean isDtsExportableReference(Type type, TranspilerState state) {
		return isDtsExportableReference(type != null ? type.eResource() : null, state);
	}

	/**
	 * Tells whether references (e.g. imports, type references) to the given resource and its contents can be exported
	 * to .d.ts. Iff this returns <code>false</code>, the .d.ts export will be cut off at this reference.
	 */
	public static boolean isDtsExportableReference(Resource resource, TranspilerState state) {
		if (resource == null) {
			return false;
		}
		if (N4Scheme.isResourceWithN4Scheme(resource)) {
			return true;
		}
		N4JSProjectConfigSnapshot project = state.workspaceAccess.findProjectContaining(resource);
		ProjectDescription pd = project != null ? project.getProjectDescription() : null;
		return pd != null
				&& pd.hasN4JSNature()
				&& (N4JSLanguageUtils.isDtsGenerationActive(pd) || isDefinition(pd) || isRuntime(pd));
	}

	private static boolean isDefinition(ProjectDescription pd) {
		ProjectType type = pd.getType();
		return type == ProjectType.DEFINITION;
	}

	private static boolean isRuntime(ProjectDescription pd) {
		ProjectType type = pd.getType();
		return type == ProjectType.RUNTIME_ENVIRONMENT
				|| type == ProjectType.RUNTIME_LIBRARY;
	}
}
