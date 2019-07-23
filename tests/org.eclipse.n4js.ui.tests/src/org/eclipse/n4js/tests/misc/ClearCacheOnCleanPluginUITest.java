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

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.compare.ApiImplMapping;
import org.eclipse.n4js.internal.MultiCleartriggerCache;
import org.eclipse.n4js.internal.MultiCleartriggerCache.CleartriggerSupplier;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.EclipseUIUtils;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Tests if the {@link MultiCleartriggerCache} is cleared properly.
 */
public class ClearCacheOnCleanPluginUITest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";
	private static final N4JSProjectName PROJECT_NAME = new N4JSProjectName("ClearCacheOnClean");

	@Inject
	private MultiCleartriggerCache cache;

	/**
	 * Tests if the {@link MultiCleartriggerCache} is cleared when a 'Clean Build' is triggered.
	 */
	@Test
	public void testClearOnCleanBuild() throws CoreException {
		File prjDir = new File(PROBANDS);
		IProject project = ProjectTestsUtils.importProject(prjDir, PROJECT_NAME);
		IResource resourceABC = project.findMember("src/ABC.n4js");
		IFile fileABC = ResourcesPlugin.getWorkspace().getRoot().getFile(resourceABC.getFullPath());
		assertTrue(fileABC.exists());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		syncExtAndBuild();
		assertNoIssues();

		// use key of API_IMPL_MAPPING
		SupplierWithPostAction testSupplier = new SupplierWithPostAction();
		assertTrue(testSupplier.postActionTriggerCount == 0);

		cache.clear(MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);

		// cache should be empty
		Object test = cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(testSupplier.postActionTriggerCount == 1);
		assertTrue("test".equals(test));

		// cache should contain key
		cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(testSupplier.postActionTriggerCount == 1);
		assertTrue("test".equals(test));

		// cleanBuild should clear the cache and set a new instance of ApiImplMappings
		IResourcesSetupUtil.cleanBuild();
		waitForAutoBuild();
		test = cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(test instanceof ApiImplMapping);
	}

	/**
	 * Tests if the {@link MultiCleartriggerCache} is cleared when the user edits a 'package.json' file. Note that this
	 * triggering is performed for the key {@link MultiCleartriggerCache#CACHE_KEY_API_IMPL_MAPPING} but not necessarily
	 * for every other key in the cache.
	 */
	@Test
	public void testClearOnModifyPackageJson() throws CoreException {
		File prjDir = new File(PROBANDS);
		IProject project = ProjectTestsUtils.importProject(prjDir, PROJECT_NAME);
		IResource packagejson = project.findMember("package.json");
		IFile filePJ = ResourcesPlugin.getWorkspace().getRoot().getFile(packagejson.getFullPath());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		syncExtAndBuild();
		assertNoIssues();

		// use key of API_IMPL_MAPPING
		SupplierWithPostAction testSupplier = new SupplierWithPostAction();
		assertTrue(testSupplier.postActionTriggerCount == 0);

		cache.clear(MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);

		// cache should be empty
		Object test = cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(testSupplier.postActionTriggerCount == 1);
		assertTrue("test".equals(test));

		// cache should contain key
		cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(testSupplier.postActionTriggerCount == 1);
		assertTrue("test".equals(test));

		// edit package.json should clear the cache and set a new instance of ApiImplMappings
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor editor = openAndGetXtextEditor(filePJ, page);
		editor.doSave(new NullProgressMonitor());
		waitForAutoBuild();
		test = cache.get(testSupplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);
		assertTrue(test instanceof ApiImplMapping);
	}

	static class SupplierWithPostAction implements CleartriggerSupplier<Object> {
		int postActionTriggerCount = 0;

		@Override
		public Object get() {
			return "test";
		}

		@Override
		public void postSupply() {
			postActionTriggerCount++;
		}

	}
}
