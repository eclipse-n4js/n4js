/**
 * Copyright (c) 2023 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 *
 */

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public abstract class AbstractN4JSTest extends Assert {

	/***/
	@Inject
	protected N4JSTestHelper testHelper;
	/***/
	@Inject
	protected N4JSParseHelper parserHelper;
	/***/
	@Inject
	protected N4JSValidationTestHelper validationTestHelper;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;
	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	private WorkspaceAccess workspaceAccess;

	/**
	 * Creates a new N4JSResource for the given URI inside a newly created {@link ResourceSet}.
	 *
	 * The returned resource is not loaded yet.
	 */
	protected N4JSResource createFromFile(URI uri) {
		return createFromFiles(uri).get(0);
	}

	/**
	 * Creates a new N4JSResource for each of the given URIs inside the same, newly created {@link ResourceSet}.
	 *
	 * The returned resources are not loaded yet.
	 */
	protected List<N4JSResource> createFromFiles(URI... uris) {
		XtextResourceSet resSet = resourceSetProvider.get();
		List<N4JSResource> resources = new ArrayList<>();
		for (URI u : uris) {
			URI normalizedURI = resSet.getURIConverter().normalize(u);
			resources.add((N4JSResource) resSet.createResource(normalizedURI));
		} // eagerly collect created resources

		return resources;
	}

	/**
	 * Creates and loads a new N4JSResource inside a newly created resource set for the given URI. The resource returned
	 * is loaded, but installation of derived state (i.e. types builder) has not yet been triggered.
	 */
	protected N4JSResource loadFromFile(URI uri) throws IOException {
		N4JSResource res = createFromFile(uri);
		res.load(Collections.emptyMap());
		return res;
	}

	/**
	 * Creates, loads, and installs derived state of a new N4JSResource inside a newly created resource set. The
	 * resource returned is loaded and has its derived state installed (i.e. types builder completed) but is not yet
	 * post-processed.
	 * <p>
	 * Same as methods such as {@link N4JSParseHelper#parseN4js(CharSequence)}, {@link ParseHelper#parse(CharSequence)},
	 * but reads source from a file instead of a string.
	 */
	protected N4JSResource parseFromFile(URI uri) throws IOException {
		N4JSResource res = loadFromFile(uri);
		res.getContents(); // trigger installation of derived state (i.e. types builder), so that it is in same state as
							// when using org.eclipse.xtext.testing.util.ParseHelper#parse(CharSequence)
		return res;
	}

	/**
	 * Simulates re-loading the resource with the given URI from the index.
	 *
	 * @See #loadFromDescriptions(URI...)
	 */
	protected N4JSResource loadFromDescription(URI uri) throws IOException {
		return loadFromDescriptions(uri).get(0);
	}

	/**
	 * Simulates loading resources for the given URIs, serializing them into the index, unloading them, reloading them
	 * from index and returning the resulting reloaded resources.
	 *
	 * During the execution this method also asserts that no validation issues can be detected.
	 *
	 * This method triggers the reconciliation of the {@link TModule} instances that can be deserialized from the index.
	 */
	protected List<N4JSResource> loadFromDescriptions(URI... uris) throws IOException {
		// early exit, if no URIs are given
		if (uris == null || uris.length == 0) {
			return Collections.emptyList();
		}

		// load normally
		List<N4JSResource> resources = createFromFiles(uris);
		for (N4JSResource res : resources) {
			// trigger installation of derived state (i.e. types builder), so that
			// it is in same state as when using org.eclipse.xtext.testing.util.ParseHelper#parse(CharSequence)
			res.load(Collections.emptyMap());
			res.getContents();

			// perform post processing
			res.performPostProcessing();
		}

		// obtain common resource set
		ResourceSet resSet = resources.get(0).getResourceSet();

		// obtain descriptions of all resources
		List<IResourceDescription> descriptions = new ArrayList<>();
		for (N4JSResource res : resources) {
			// ensure that there are no validation errors
			validationTestHelper.assertNoErrors(res);

			URI uri = res.getURI();

			// force serialization into index
			IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(res);
			assertFalse("index has not been filled",
					!resourceDescriptions.getAllResourceDescriptions().iterator().hasNext());
			IResourceDescription description = resourceDescriptions.getResourceDescription(uri);
			description.getExportedObjects().iterator().next()
					.getUserData(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT); // trigger actual serialization into
																					// index

			// ensure that the resource is in the expected state
			assertTrue(res.isFullyInitialized());
			assertTrue(res.isFullyProcessed());

			descriptions.add(description);
		}

		// unload and remove all resources
		for (N4JSResource res : resources) {
			res.unload();
			resSet.getResources().remove(res);
		}

		// re-load all resources from index
		List<N4JSResource> reloadedResources = new ArrayList<>();
		for (IResourceDescription resDesc : descriptions) {
			N4JSResource resFromIndex = (N4JSResource) workspaceAccess.loadModuleFromIndex(resSet, resDesc, false)
					.eResource();

			assertTrue(resFromIndex.getScript().eIsProxy());
			assertFalse(resFromIndex.getModule().eIsProxy());

			reloadedResources.add(resFromIndex);
		} // eagerly collect re-loaded resources

		return reloadedResources;
	}
}