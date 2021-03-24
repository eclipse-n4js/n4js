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
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
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
		N4JSWorkspaceConfigSnapshot workspaceConfig = workspaceAccess.getWorkspaceConfig(resource).orNull();
		return N4JSLanguageUtils.isOpaqueModule(workspaceConfig, resource.getURI());
	}
}
