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
package org.eclipse.n4js.tests.dirtystate

import com.google.inject.Inject
import java.util.Set
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicReference
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.ide.xtext.server.ResourceTaskManager
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.util.concurrent.IUnitOfWork

import static org.junit.Assert.*

/**
 * Base class for testing logic in {@link CanLoadFromDescriptionHelper}.
 */
abstract class AbstractCanLoadFromDescriptionTest extends AbstractIdeTest {

	@Inject
	protected ResourceTaskManager resourceTaskManager;

	override protected getIgnoredIssueCodes() {
		return (super.getIgnoredIssueCodes() + #[
			IssueCodes.LTD_ILLEGAL_LOADTIME_REFERENCE
		]).toSet;
	}

	/**
	 * Execute the given unit of work with the resource of the given editor.
	 */
	def protected void assertResource(String moduleName, IUnitOfWork.Void<XtextResource> unit) {
		val uri = getFileURIFromModuleName(moduleName).toURI;
		val latch = new CountDownLatch(1);
		val failure = new AtomicReference<AssertionError>();
		resourceTaskManager.runInExistingContextVoid(uri, "#assertResource()", [ ctx, ci |
			try {
				unit.exec(ctx.resource);
			} catch(AssertionError e) {
				failure.set(e);
			}
			latch.countDown();
		]);
		latch.await();
		if (failure.get !== null) {
			throw new AssertionError("assertion failure in #assertResource()", failure.get);
		}
	}

	/**
	 * Assert that the resources denoted by their URIs are loaded from source in the resource set of 
	 * the given editor.
	 */
	def protected void assertFromSource(String moduleName, Set<FileURI> uris) {
		assertResource(moduleName, [ resource |
			val resourceSet = resource.resourceSet;
			val emfURIs = uris.map[toURI].toSet;
			resourceSet.resources.forEach [ other |
				if (other instanceof N4JSResource) {
					assertEquals(other.URI.toString + ' was loaded from source', emfURIs.contains(other.URI), !other.isLoadedFromDescription)	
				}
			]
		]);
	}

	/**
	 * Assert that all but the primary resource in the editors resource set are loaded from the index.
	 */
	def protected void assertAllFromIndex(String moduleName) {
		assertResource(moduleName, [ resource |
			val resourceSet = resource.resourceSet;
			resourceSet.resources.forEach [ other |
				if (other instanceof N4JSResource) {
					assertEquals(other.URI.toString + ' was loaded from source', other !== resource, other.isLoadedFromDescription)	
				}
			]
		]);
	}
}
