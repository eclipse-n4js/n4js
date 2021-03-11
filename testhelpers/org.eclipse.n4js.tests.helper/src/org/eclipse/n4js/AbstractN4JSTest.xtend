/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.List
import java.util.stream.Collectors
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.n4js.projectModel.IN4JSCoreNEW
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.resource.UserDataMapper
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.runner.RunWith

/**
 * Base class for simple N4JS unit tests. In more special cases, consider using base classes such as
 * {@code org.eclipse.n4js.tests.parser.AbstractParserTest}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public abstract class AbstractN4JSTest extends Assert {

	@Inject protected extension N4JSTestHelper;
	@Inject protected extension N4JSParseHelper;
	@Inject protected extension N4JSValidationTestHelper;


	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider

	@Inject
	private IN4JSCoreNEW n4jsCore;


	/**
	 * Creates a new N4JSResource for the given URI inside a newly created {@link  ResourceSet}.
	 *  
	 * The returned resource is not loaded yet.
	 */
	def protected N4JSResource createFromFile(URI uri) {
		return createFromFiles(uri).head;
	}

	/**
	 * Creates a new N4JSResource for each of the given URIs inside the same, newly created {@link ResourceSet}.
	 *  
	 * The returned resources are not loaded yet.
	 */
	def protected List<N4JSResource> createFromFiles(URI... uris) {
		val resSet = resourceSetProvider.get();
		val resources = uris.stream.map[u |
			val normalizedURI = resSet.URIConverter.normalize(u);
			return resSet.createResource(normalizedURI) as N4JSResource;
		].collect(Collectors.toList) // eagerly collect created resources
		
		return resources;
	}


	/**
	 * Creates and loads a new N4JSResource inside a newly created resource set for the given URI. The resource returned
	 * is loaded, but installation of derived state (i.e. types builder) has not yet been triggered.
	 */
	def protected N4JSResource loadFromFile(URI uri) {
		val res = createFromFile(uri);
		res.load(emptyMap);
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
	def protected N4JSResource parseFromFile(URI uri) {
		val res = loadFromFile(uri);
		res.contents; // trigger installation of derived state (i.e. types builder), so that it is in same state as when using org.eclipse.xtext.testing.util.ParseHelper#parse(CharSequence)
		return res;
	}
	
	/**
	 * Simulates re-loading the resource with the given URI
	 * from the index.
	 * 
	 * @See #loadFromDescriptions(URI...)
	 */
	def protected N4JSResource loadFromDescription(URI uri) {
		return loadFromDescriptions(uri).head;
	}
	
	/**
	 * Simulates loading resources for the given URIs, serializing them
	 * into the index, unloading them, reloading them from index and returning
	 * the resulting reloaded resources.
	 * 
	 * During the execution this method also asserts that no validation 
	 * issues can be detected. 
	 * 
	 * This method triggers the reconciliation of the {@link TModule} instances 
	 * that can be deserialized from the index.
	 */
	def protected List<N4JSResource> loadFromDescriptions(URI... uris) {
		// early exit, if no URIs are given
		if (uris.empty) {
			return #[];
		}
		
		// load normally
		val resources = createFromFiles(uris);
		
		resources.forEach[res |
			// trigger installation of derived state (i.e. types builder), so that 
			// it is in same state as when using org.eclipse.xtext.testing.util.ParseHelper#parse(CharSequence) 
			res.load(emptyMap);
			res.contents;
			
			// perform post processing
			res.performPostProcessing
		]
		
		// obtain common resource set
		val resSet = resources.head.resourceSet;

		// obtain descriptions of all resources
		val descriptions = resources.stream.map[ res |
			// ensure that there are no validation errors
			res.assertNoErrors
			
			val uri = res.URI
			
			// force serialization into index
			val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(res);
			assertFalse("index has not been filled", resourceDescriptions.allResourceDescriptions.empty);
			val description = resourceDescriptions.getResourceDescription(uri);
			description.exportedObjects.head.getUserData(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT); // trigger actual serialization into index
			
			// ensure that the resource is in the expected state
			assertTrue(res.fullyInitialized);
			assertTrue(res.fullyProcessed);
			
			return description;
		].collect(Collectors.toList) // eagerly collect resource descriptions

		// unload and remove all resources
		resources.forEach[res | 
			res.unload();
			resSet.resources.remove(res);
		]
		
		// re-load all resources from index
		val reloadedResources = descriptions.stream.map[resDesc |
			val resFromIndex = n4jsCore.loadModuleFromIndex(resSet, resDesc, false).eResource as N4JSResource;
			
			assertTrue(resFromIndex.script.eIsProxy);
			assertFalse(resFromIndex.module.eIsProxy);
			
			return resFromIndex;
		].collect(Collectors.toList) // eagerly collect re-loaded resources
		
		return reloadedResources;
	}
}
