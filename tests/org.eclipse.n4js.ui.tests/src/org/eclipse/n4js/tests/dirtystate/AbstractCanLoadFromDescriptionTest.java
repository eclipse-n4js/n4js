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
package org.eclipse.n4js.tests.dirtystate;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;

/**
 * Base class for testing logic in {@link CanLoadFromDescriptionHelper}.
 */
abstract public class AbstractCanLoadFromDescriptionTest extends ConvertedIdeTest {

	@Inject
	protected ResourceTaskManager resourceTaskManager;

	@Override
	protected Set<String> getIgnoredIssueCodes() {
		Set<String> ignoredIssueCodes = super.getIgnoredIssueCodes();
		ignoredIssueCodes.add(IssueCodes.LTD_ILLEGAL_LOADTIME_REFERENCE.name());
		return ignoredIssueCodes;
	}

	/**
	 * Execute the given unit of work with the resource of the given editor.
	 */
	protected void assertResource(String moduleName, IUnitOfWork.Void<XtextResource> unit) throws InterruptedException {
		URI uri = getFileURIFromModuleName(moduleName).toURI();
		CountDownLatch latch = new CountDownLatch(1);
		AtomicReference<AssertionError> failure = new AtomicReference<>();
		resourceTaskManager.runInExistingContextVoid(uri, "#assertResource()", (ctx, ci) -> {
			try {
				unit.exec(ctx.getResource());
			} catch (AssertionError e) {
				failure.set(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
			latch.countDown();
		});
		latch.await();
		if (failure.get() != null) {
			throw new AssertionError("assertion failure in #assertResource()", failure.get());
		}
	}

	/**
	 * Assert that the resources denoted by their URIs are loaded from source in the resource set of the given editor.
	 */
	protected void assertFromSource(String moduleName, Set<FileURI> uris) throws InterruptedException {
		IUnitOfWork.Void<XtextResource> unit = new IUnitOfWork.Void<>() {
			@Override
			public void process(XtextResource resource) throws Exception {
				ResourceSet resourceSet = resource.getResourceSet();
				Set<URI> emfURIs = toSet(map(uris, FileURI::toURI));
				for (Resource other : resourceSet.getResources()) {
					if (other instanceof N4JSResource) {
						assertEquals(other.getURI().toString() + " was loaded from source",
								emfURIs.contains(other.getURI()), !((N4JSResource) other).isLoadedFromDescription());
					}
				}
			}
		};

		assertResource(moduleName, unit);
	}

	/**
	 * Assert that all but the primary resource in the editors resource set are loaded from the index.
	 */
	protected void assertAllFromIndex(String moduleName) throws InterruptedException {
		IUnitOfWork.Void<XtextResource> unit = new IUnitOfWork.Void<>() {
			@Override
			public void process(XtextResource resource) throws Exception {
				ResourceSet resourceSet = resource.getResourceSet();
				for (Resource other : resourceSet.getResources()) {
					if (other instanceof N4JSResource) {
						assertEquals(other.getURI().toString() + " was loaded from source", other != resource,
								((N4JSResource) other).isLoadedFromDescription());
					}
				}
			}
		};

		assertResource(moduleName, unit);
	}
}
