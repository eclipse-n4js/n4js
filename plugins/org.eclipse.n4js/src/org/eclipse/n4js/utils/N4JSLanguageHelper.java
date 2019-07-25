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
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.services.N4JSGrammarAccess;
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

	/**
	 * Opaque modules have empty Script nodes in their AST. Other than that they behave normally.
	 */
	public static boolean OPAQUE_JS_MODULES = true;

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private IN4JSCore n4jsCore;

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

	/**
	 * Opaque resources are not post processed neither validated. The transpiler will wrap opaque resources only.
	 *
	 * @param resourceURI
	 *            The URI of a resource
	 * @return true if the given resource is opaque.
	 */
	public boolean isOpaqueModule(URI resourceURI) {
		ResourceType resourceType = ResourceType.getResourceType(resourceURI);

		switch (resourceType) {
		case JS:
		case JSX:
			return OPAQUE_JS_MODULES; // JavaScript modules are not processed iff OPAQUE_JS_MODULES is true

		case N4JS:
		case N4JSX:
		case N4IDL:
			IN4JSProject project = n4jsCore.findProject(resourceURI).orNull();
			if (project == null) {
				return false; // happens in tests
			}
			ProjectType projectType = project.getProjectType();
			// N4JS files of definition projects are not processed.
			return projectType == ProjectType.DEFINITION;

		case N4JSD:
		case UNKOWN:
		case XT:
			// default
		}

		// default: process file
		return false;
	}
}
