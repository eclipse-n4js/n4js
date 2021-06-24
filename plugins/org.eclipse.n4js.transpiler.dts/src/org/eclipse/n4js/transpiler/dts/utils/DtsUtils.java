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

import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;

import com.google.common.collect.ImmutableSet;

/**
 * Some utilities for .d.ts export.
 */
public class DtsUtils {

	/**
	 * White list for 3rd party libraries with an <code>@n4jsd</code> project that is compatible with their
	 * <code>@types</code> project. References to such projects will be exported to .d.ts.
	 */
	private static final Set<String> DTS_EXPORTABLE_THIRD_PARTY_LIBS = ImmutableSet.of(/* "immutable" */);

	/** @see #isDtsExportableReference(Resource, TranspilerState) */
	public static boolean isDtsExportableDependency(Type type, TranspilerState state) {
		return isDtsExportableReference(type != null ? type.eResource() : null, state);
	}

	/** @see #isDtsExportableReference(Resource, TranspilerState) */
	public static boolean isDtsExportableDependency(TModule module, TranspilerState state) {
		return isDtsExportableReference(module != null ? module.eResource() : null, state);
	}

	/**
	 * Tells whether references (e.g. imports, type references) to the given resource and its contents can be exported
	 * to .d.ts. Iff this returns <code>false</code>, the .d.ts export will be cut off at this reference.
	 */
	public static boolean isDtsExportableReference(Resource resource, TranspilerState state) {
		if (N4Scheme.isResourceWithN4Scheme(resource)) {
			return true;
		}
		N4JSProjectConfigSnapshot project = resource != null
				? state.workspaceAccess.findProjectContaining(resource)
				: null;
		if (project != null && DTS_EXPORTABLE_THIRD_PARTY_LIBS.contains(project.getName())) {
			return true;
		}
		ProjectDescription pd = project != null
				? project.getProjectDescription()
				: null;
		return pd != null
				&& pd.hasN4JSNature()
				&& (pd.isGeneratorEnabledDts() || isRuntime(pd));
	}

	private static boolean isRuntime(ProjectDescription pd) {
		ProjectType type = pd.getType();
		return type == ProjectType.RUNTIME_ENVIRONMENT
				|| type == ProjectType.RUNTIME_LIBRARY;
	}

	/**
	 * Returns the textual reference that can be used in the local file to refer to the given type, or <code>null</code>
	 * if the type cannot be referred to without adding new imports, etc.
	 * <p>
	 * The returned string is usually simply the local name of the given type, but includes, if required, also the name
	 * of a namespace and "." as separator.
	 */
	public static String getReferenceToTypeIfLocallyAvailable(Type type, TranspilerState state) {
		if (N4Scheme.isFromResourceWithN4Scheme(type)) {
			// simple case: the type reference points to a built-in type
			// -> can simply use its name in output code, because they are global and available everywhere
			if (type == RuleEnvironmentExtensions.intType(state.G)) {
				type = RuleEnvironmentExtensions.numberType(state.G);
			} else if (type == RuleEnvironmentExtensions.iteratorEntryType(state.G)) {
				return "IteratorReturnResult";
			}
			return type.getName();
		}
		SymbolTableEntryOriginal ste = state.steCache.mapOriginal.get(type);
		if (ste != null) {
			// the type reference points to a type contained in or already imported into the current module
			ImportSpecifier importSpec = ste.getImportSpecifier();
			if (importSpec instanceof NamespaceImportSpecifier) {
				String namespaceName = ((NamespaceImportSpecifier) importSpec).getAlias();
				return namespaceName + "." + ste.getName();
			}
			return ste.getName();
		}
		return null;
	}
}
