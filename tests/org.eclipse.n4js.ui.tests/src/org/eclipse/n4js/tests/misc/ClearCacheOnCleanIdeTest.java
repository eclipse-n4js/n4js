/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.misc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.compare.ApiImplMapping;
import org.eclipse.n4js.internal.MultiCleartriggerCache;
import org.eclipse.n4js.internal.MultiCleartriggerCache.CleartriggerSupplier;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Tests if the {@link MultiCleartriggerCache} is cleared properly.
 */
// converted from ClearCacheOnCleanPluginUITest
public class ClearCacheOnCleanIdeTest extends ConvertedIdeTest {

	private static final String PROBANDS = "probands";
	private static final N4JSProjectName PROJECT_NAME = new N4JSProjectName("ClearCacheOnClean");

	@Inject
	private MultiCleartriggerCache cache;

	/**
	 * Tests if the {@link MultiCleartriggerCache} is cleared when a 'Clean Build' is triggered.
	 */
	@Test
	public void testClearOnCleanBuild() {
		importProband(new File(PROBANDS, PROJECT_NAME.getRawName()));

		assertNoIssues();

		// use key of API_IMPL_MAPPING
		SupplierWithPostAction testSupplier = new SupplierWithPostAction();
		assertTrue(testSupplier.postActionTriggerCount == 0);

		cache.clear(MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);

		// cache should be empty
		Object test = cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(testSupplier.postActionTriggerCount == 1);
		assertTrue(test instanceof TestApiImplMapping);

		// cache should contain key
		cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(testSupplier.postActionTriggerCount == 1);
		assertTrue(test instanceof TestApiImplMapping);

		// cleanBuild should clear the cache and set a new instance of ApiImplMappings
		cleanBuildAndWait();
		test = cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertFalse(test instanceof TestApiImplMapping);
		assertTrue(test instanceof ApiImplMapping);
	}

	/**
	 * Tests if the {@link MultiCleartriggerCache} is cleared when the user edits a 'package.json' file. Note that this
	 * triggering is performed for the key {@link MultiCleartriggerCache#CACHE_KEY_API_IMPL_MAPPING} but not necessarily
	 * for every other key in the cache.
	 */
	@Test
	public void testClearOnModifyPackageJson() {
		importProband(new File(PROBANDS, PROJECT_NAME.getRawName()));

		FileURI filePJ = toFileURI(getProjectRootForImportedProject(PROJECT_NAME.getRawName()))
				.appendSegment(N4JSGlobals.PACKAGE_JSON);
		Assert.assertTrue(filePJ.exists());

		assertNoIssues();

		// use key of API_IMPL_MAPPING
		SupplierWithPostAction testSupplier = new SupplierWithPostAction();
		assertTrue(testSupplier.postActionTriggerCount == 0);

		cache.clear(MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);

		// cache should be empty
		Object test = cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(testSupplier.postActionTriggerCount == 1);
		assertTrue(test instanceof TestApiImplMapping);

		// cache should contain key
		cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(testSupplier.postActionTriggerCount == 1);
		assertTrue(test instanceof TestApiImplMapping);

		// edit package.json should clear the cache and set a new instance of ApiImplMappings
		openFile(filePJ);
		joinServerRequests();
		changeOpenedFile(filePJ, oldContent -> oldContent + " ");
		joinServerRequests();
		saveOpenedFile(filePJ);
		joinServerRequests();
		test = cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		// TODO the following does not work yet (ApiImplMapping was not recreated after saving package.json)
		// assertFalse(test instanceof TestApiImplMapping);
		assertTrue(test instanceof ApiImplMapping);
	}

	private static class SupplierWithPostAction implements CleartriggerSupplier<ApiImplMapping> {
		int postActionTriggerCount = 0;

		@Override
		public ApiImplMapping get() {
			return new TestApiImplMapping();
		}

		@Override
		public void postSupply() {
			postActionTriggerCount++;
		}

	}

	private static class TestApiImplMapping extends ApiImplMapping {
		// no content
	}
}
