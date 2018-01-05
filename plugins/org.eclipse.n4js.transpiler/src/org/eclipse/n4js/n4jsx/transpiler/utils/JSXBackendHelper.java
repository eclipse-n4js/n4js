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
package org.eclipse.n4js.n4jsx.transpiler.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4jsx.ReactHelper;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.ProjectResolveHelper;
import org.eclipse.n4js.projectModel.ResourceNameComputer;
import org.eclipse.n4js.transpiler.InformationRegistry;
import org.eclipse.n4js.ts.types.TModule;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Helper for working with JSX backends, e.g. Ract, Preact, etc. Internally it supports only React, but API wise should
 * work for other backends once their support is added.
 */
public final class JSXBackendHelper {
	private final static String JSX_BACKEND_FACADE_NAME = "React";
	private final static String JSX_BACKEND_ELEMENT_FACTORY_NAME = "createElement";

	/**
	 * Local cache of JSX backends.
	 *
	 * We don't bother with proper caching, due to the way this helper is currently used in the transpiler.
	 */
	private final Map<String, URI> jsxBackends = new HashMap<>();

	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private ResourceNameComputer projectUtils;
	@Inject
	private ProjectResolveHelper projectResolver;

	@Inject
	private ReactHelper reactHelper;

	/** @return name of the JSX backend facade, i.e "React" */
	public String getBackendFacadeName() {
		return JSX_BACKEND_FACADE_NAME;
	}

	/** @return name of the JSX element factory name, i.e "createElement" */
	public String getBackendElementFactoryMethodName() {
		return JSX_BACKEND_ELEMENT_FACTORY_NAME;
	}

	/** Checks if given module looks like JSX backend module, e.g. "react" */
	public boolean isJsxBackendModule(TModule module) {
		return reactHelper.isReactModule(module);
	}

	/** Checks if given import declaration looks like JSX backend import, e.g. "(...) from "react" */
	public boolean isJsxBackendImportDeclaration(ImportDeclaration declaration, InformationRegistry info) {
		// 'false' here means: we turn off checking intermediate model element.
		return isJsxBackendModule(info.getImportedModule(declaration, false));
	}

	/** Checks if given import specifier looks like JSX backend import, e.g. "import * as React from "react" */
	public boolean isJsxBackendImportSpecifier(ImportSpecifier specifier, InformationRegistry info) {
		return isJsxBackendImportDeclaration((ImportDeclaration) specifier.eContainer(), info);
	}

	/**
	 * Delegates to {@link ResourceNameComputer#getCompleteModuleSpecifier(IN4JSProject, TModule)} but for artificial
	 * modules that were patched in by the transpiler for JSX backend.
	 */
	public String jsxBackendModuleSpecifier(TModule module, Resource resource) {
		URI uri = getOrFindJSXBackend(resource, module.getQualifiedName());

		Optional<? extends IN4JSProject> optionalProject = n4jsCore.findProject(uri);
		if (!optionalProject.isPresent()) {
			throw new RuntimeException(
					"Cannot handle resource without containing project. Resource URI was: " + uri);
		}
		return projectUtils.getCompleteModuleSpecifier(optionalProject.get(), module);
	}

	/**
	 * Delegates to {@link ResourceNameComputer#getCompleteModuleSpecifierAsIdentifier(IN4JSProject, TModule)} but for
	 * artificial modules that were patched in by the transpiler for JSX backend.
	 */
	public String getJsxBackendCompleteModuleSpecifierAsIdentifier(TModule module) {
		URI uri = getOrFindJSXBackend(module.eResource(), module.getQualifiedName());
		IN4JSProject resolveProject = projectResolver.resolveProject(uri);
		return projectUtils.getCompleteModuleSpecifierAsIdentifier(resolveProject, module);
	}

	/**
	 * Looks up JSX backend for a provided resource. If more than one available, picks one at <b>random</b>. When no
	 * backend is available throws {@link NoSuchElementException}.
	 *
	 * Note that this method is expected to be called only from <code>SanitizeImportsTransformation</code>, and only in
	 * case patching react import is required. This is expected to be a corner case. If this becomes normal execution
	 * path, this method has to be redesigned to work in more reliable way, i.e. it needs more information to reliably
	 * select proper JSX backend.
	 *
	 * @return qualified name of the selected JSX backend module
	 */
	public String jsxBackendModuleQualifiedName(Resource resource) {
		Objects.requireNonNull(resource);

		// maybe this is first call to the helper, populate cached backends
		if (jsxBackends.isEmpty()) {
			populateBackendsCache(resource);
		}

		// since we have no info about which backend to use, use any
		return getAnyBackend();
	}

	/**
	 * Selects JSX backend at random from {@link #jsxBackends} cache. If we get exception below that means that either:
	 * method was called before cache was populated, or it was not populated correctly. Latter is unusual and means that
	 * either resource validation is broken and did not put error marker on compiled resource (error should say that
	 * there is no JSX backend available), or custom scope used to populate cache is broken and is not finding any JSX
	 * backend.
	 *
	 * @throws RuntimeException
	 *             when no JSX backend is available
	 */
	private String getAnyBackend() {
		return jsxBackends.keySet().stream().findAny()
				.orElseThrow(() -> new RuntimeException("Compiler cannot locate JSX backend to use for this resource"));
	}

	/**
	 * Populates {@link #jsxBackends} with backends visible from provided resource.
	 */
	private void populateBackendsCache(Resource resource) {
		jsxBackends.clear();
		addReactToBackendsCache(resource);
	}

	private void addReactToBackendsCache(Resource resource) {
		TModule reactModule = reactHelper.lookUpReactTModule(resource);
		if (reactModule != null) {
			URI reactModuleURI = reactModule.eResource().getURI();
			jsxBackends.put(ReactHelper.REACT_PROJECT_ID, reactModuleURI);
		}
	}

	/**
	 * Provides URI for JSX Backend. URI is cached in local map {@link JSXBackendHelper#jsxBackends}. If there is no URI
	 * for given QN, performs lookup via scope of the provided resource. and returned.
	 *
	 */
	private URI getOrFindJSXBackend(Resource resource, String qualifiedName) {
		if (jsxBackends.isEmpty()) {
			populateBackendsCache(resource);
		}
		URI backendURI = jsxBackends.get(qualifiedName);
		if (backendURI == null) {
			// Normally we would throw error here, but there are few grey areas with JSX support.
			// To avoid blocking other teams using JSX, we are a bit defensive and try to keep system running.
			// With less complicated setups, this will HAPPEN to be correct.
			backendURI = jsxBackends.get(getAnyBackend());
		}

		return backendURI;
	}
}
