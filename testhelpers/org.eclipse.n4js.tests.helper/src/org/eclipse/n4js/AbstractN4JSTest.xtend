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
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.resource.UserdataMapper
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
public class AbstractN4JSTest extends Assert {

	@Inject protected extension N4JSTestHelper;
	@Inject protected extension N4JSParseHelper;
	@Inject protected extension N4JSValidationTestHelper;


	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider

	@Inject
	private IN4JSCore n4jsCore;


	/**
	 * Creates a new N4JSResource inside a newly created resource set for the given URI. The resource returned is not
	 * loaded yet.
	 */
	def protected N4JSResource createFromFile(URI uri) {
		val resSet = resourceSetProvider.get();
		val resURI = resSet.URIConverter.normalize(uri);
		val res = resSet.createResource(resURI) as N4JSResource;
		return res;
	}

	/**
	 * Creates two new N4JSResource instances for the two given URIs.
	 * 
	 * Only returns the N4JSResource created for the first {@code uri}.
	 */
	def protected N4JSResource createFromFile(URI uri, URI other) {
		val resSet = resourceSetProvider.get();
		
		val resURI = resSet.URIConverter.normalize(uri);
		val res = resSet.createResource(resURI) as N4JSResource;
		
		val otherResURI = resSet.URIConverter.normalize(other);
		resSet.createResource(otherResURI);
		
		return res;
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
	 * Simulates loading the resource for the given URI from the index, inside a newly created resource set.
	 */
	def protected N4JSResource loadFromDescription(URI uri) {
		// load normally
		val res = parseFromFile(uri);
		val resURI = res.URI;
		val resSet = res.resourceSet;

		// force serialization into index
		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(res);
		assertFalse("index has not been filled", resourceDescriptions.allResourceDescriptions.empty);
		val resDesc = resourceDescriptions.getResourceDescription(resURI);
		resDesc.exportedObjects.head.getUserData(UserdataMapper.USERDATA_KEY_SERIALIZED_SCRIPT); // trigger actual serialization into index

		assertTrue(res.fullyInitialized);
		assertTrue(res.fullyProcessed);

		// remove from resource set
		res.unload();
		resSet.resources.remove(res);

		// now re-load from index
		val resFromIndex = n4jsCore.loadModuleFromIndex(resSet, resDesc, false).eResource as N4JSResource;
		assertTrue(resFromIndex.script.eIsProxy);
		assertFalse(resFromIndex.module.eIsProxy);

		return resFromIndex;
	}
	
	/**
	 * Simulates loading the resources for the two given URIs from the index, 
	 * inside a newly created resource set.
	 */
	def protected N4JSResource loadFromDescription(URI uri, URI other) {
		// load normally
		val res = createFromFile(uri, other);
		res.load(emptyMap);
		res.contents; // trigger installation of derived state (i.e. types builder), so that it is in same state as when using org.eclipse.xtext.testing.util.ParseHelper#parse(CharSequence)
		val resOther = res.resourceSet.getResource(other, false) as N4JSResource;
		resOther.load(emptyMap);
		resOther.contents; // trigger installation of derived state (i.e. types builder), so that it is in same state as when using org.eclipse.xtext.testing.util.ParseHelper#parse(CharSequence)
		
		res.performPostProcessing;
		resOther.performPostProcessing;
		
		val resURI = res.URI;
		val resSet = res.resourceSet;

		res.assertNoErrors

		// force serialization into index
		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(res);
		assertFalse("index has not been filled", resourceDescriptions.allResourceDescriptions.empty);
		val resDesc = resourceDescriptions.getResourceDescription(resURI);
		resDesc.exportedObjects.head.getUserData(UserdataMapper.USERDATA_KEY_SERIALIZED_SCRIPT); // trigger actual serialization into index
		
		val resourceDescriptionsOther = resourceDescriptionsProvider.getResourceDescriptions(resOther);
		assertFalse("index has not been filled", resourceDescriptionsOther.allResourceDescriptions.empty);
		val resDescOther = resourceDescriptionsOther.getResourceDescription(resURI);
		resDescOther.exportedObjects.head.getUserData(UserdataMapper.USERDATA_KEY_SERIALIZED_SCRIPT); // trigger actual serialization into index

		assertTrue(res.fullyInitialized);
		assertTrue(res.fullyProcessed);

		// remove from resource set
		res.unload();
		resSet.resources.remove(res);
		resOther.unload();
		resSet.resources.remove(resOther);

		// now re-load from index
		val resFromIndex = n4jsCore.loadModuleFromIndex(resSet, resDesc, false).eResource as N4JSResource;
		assertTrue(resFromIndex.script.eIsProxy);
		assertFalse(resFromIndex.module.eIsProxy);

		return resFromIndex;
	}
}
