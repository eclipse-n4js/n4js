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
package org.eclipse.n4js.utils;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.Inject;

/**
 * This helper class offers access to ECMAScript as well as N4JS keywords. Its implementation directly accesses the
 * grammar which means that any grammar changes will be reflected by this class.
 */
public final class N4JSLanguageHelper {

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private WorkspaceAccess workspaceAccess;

	/**
	 * Returns the reserved ECMAScript keywords which are defined in the grammar. The result is cached.
	 * <a href="https://people.mozilla.org/~jorendorff/es6-draft.html#sec-keywords">ECMAScript ver. [6 11.6.2.1]
	 * Keywords</a>.
	 */
	public Collection<String> getECMAKeywords() {
		return reservedKeywordSupplier.get();
	}

	/**
	 * Returns the N4JS keywords which are defined in the grammar.
	 *
	 * The result is cached.
	 */
	public Collection<String> getN4Keywords() {
		return n4KeywordSupplier.get();
	}

	/**
	 * Returns {@code true} if the given identifier is a reserved one.
	 *
	 * This includes all ECMAScript and some additional N4JS keywords as well as base type names and boolean literals.
	 */
	public boolean isReservedIdentifier(String identifier) {
		return getECMAKeywords().contains(identifier)
				|| N4JSLanguageConstants.ACCESS_MODIFIERS.contains(identifier)
				|| N4JSLanguageConstants.GETTER_SETTER.contains(identifier)
				|| N4JSLanguageConstants.BASE_TYPES.contains(identifier)
				|| N4JSLanguageConstants.BOOLEAN_LITERALS.contains(identifier);
	}

	private final Supplier<Collection<String>> reservedKeywordSupplier = Suppliers.memoize(() -> {
		return getKeywordAlternatives(grammarAccess.getReservedWordRule());
	});

	private final Supplier<Collection<String>> n4KeywordSupplier = Suppliers.memoize(() -> {
		return getKeywordAlternatives(grammarAccess.getN4KeywordRule());
	});

	private Collection<String> getKeywordAlternatives(ParserRule rule) {
		EList<EObject> ruleAlternatives = rule.getAlternatives().eContents();
		return ruleAlternatives.stream()
				.filter(alternative -> alternative instanceof Keyword)
				.map(keyword -> ((Keyword) keyword).getValue())
				.collect(Collectors.toList());
	}

	/** Convenience method for {@link N4JSLanguageUtils#isOpaqueModule(ProjectType, URI)}. */
	public boolean isOpaqueModule(Resource resource) {
		N4JSWorkspaceConfigSnapshot workspaceConfig = workspaceAccess.getWorkspaceConfig(resource);
		return N4JSLanguageUtils.isOpaqueModule(workspaceConfig, resource.getURI());
	}

	/**
	 * Tells whether the given resource represents an ES6 module (otherwise, CommonJS is assumed).
	 *
	 * @see ProjectDescription#isESM()
	 */
	public boolean isES6Module(Resource resource) {
		String targetURILastSegment = resource.getURI() != null ? resource.getURI().lastSegment() : null;
		if (targetURILastSegment != null) {
			if (targetURILastSegment.endsWith("." + N4JSGlobals.MJS_FILE_EXTENSION)) {
				return true;
			} else if (targetURILastSegment.endsWith("." + N4JSGlobals.CJS_FILE_EXTENSION)) {
				return false;
			} else if (targetURILastSegment.endsWith("." + N4JSGlobals.N4JS_FILE_EXTENSION) // not for .n4jsd!!
					|| targetURILastSegment.endsWith("." + N4JSGlobals.N4JSX_FILE_EXTENSION)) {
				// since the N4JS transpiler uses ".js" as extension for output files (not ".mjs") we have to return
				// 'true' here (in practice this should not matter much, because a validation enforces the use of
				// "type":"module" in the package.json of N4JS projects and therefore the below call to
				// targetProject.isEMS() would return 'true' anyway, leading to the same result)
				return true;
			}
		}

		N4JSProjectConfigSnapshot targetProject = workspaceAccess.findProjectContaining(resource);
		if (targetProject != null && targetProject.getType() == ProjectType.DEFINITION) {
			N4JSPackageName definedPackageName = targetProject.getDefinesPackage();
			if (definedPackageName != null) {
				String definedProjectId = targetProject.getProjectIdForPackageName(definedPackageName.getRawName());
				if (definedProjectId != null) {
					targetProject = workspaceAccess.findProjectByName(resource, definedProjectId);
				}
			}
		}
		if (targetProject == null) {
			return true;
		}

		if (targetProject.isESM()) {
			return true;
		}

		return false;
	}
}
