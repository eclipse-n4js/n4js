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
package org.eclipse.n4js.tests.bugs;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.difference;
import static org.eclipse.n4js.resource.UserdataMapper.USERDATA_KEY_SERIALIZED_SCRIPT;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ui.building.ResourceDescriptionWithoutModuleUserData;
import org.eclipse.n4js.ui.external.ExternalProjectMappings;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.builderState.impl.ResourceDescriptionImpl;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Test for checking that no {@link ResourceDescriptionWithoutModuleUserData customized resource description} instances
 * are leaked into the {@link IBuilderState builder state Xtext index} because those cannot be persisted.
 *
 * Way to manually reproduce the issue:
 * <ol>
 * <li>Start up IDE with empty workspace but available N4JS built-ins.</li>
 * <li>Create a test project with the 'greeter' test.</li>
 * <li>Run test verify everything is fine, then gracefully shutdown the application.</li>
 * <li>Start application, then crash.</li>
 * <li>Start application, due to the crash the index content is gone, so externals will be re-initialized automatically.
 * </li>
 * <li>After the re-initialization, everything should be fine, run 'greeter' test to verify.</li>
 * <li>Gracefully stop application. &rarr; <b>That point some index entries will not be persisted.</b></li>
 * <li>Start application, no built-in re-initialization will run (there was a graceful shutdown), but due to some
 * missing index entries there will be a compiler error.</li>
 * </ol>
 *
 * <p>
 * Since a plug-in UI test cannot test multiple application startup and VM crash we have to test is deep down at the
 * builder state and EMF based persister level.
 *
 * <p>
 * To make the test fail comment out
 * {@code org.eclipse.n4js.ui.building.N4JSGenerateImmediatelyBuilderState.setResourceDescriptionsData(ResourceDescriptionsData)}
 * method.
 *
 */
@SuppressWarnings({ "restriction" })
public class GHOLD_120_XtextIndexPersistence_PluginUITest extends AbstractIDEBUG_Test {

	private static final String PROJECT_NAME = "GHOLD-120";

	@Inject
	private IBuilderState builderState;

	@Inject
	private ContributingResourceDescriptionPersister persister;

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	/**
	 * Initializes the N4JS built-in libraries. Does not matter before or after the test project import.
	 */
	@Before
	public void loadBuiltIns() {
		ExternalProjectMappings.REDUCE_REGISTERED_NPMS = false;
		shippedCodeInitializeTestHelper.setupBuiltIns();
		waitForAutoBuild();
	}

	/**
	 * Cleans the external library content.
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		unLoadBuiltIns();
		super.tearDown();
	}

	private void unLoadBuiltIns() {
		shippedCodeInitializeTestHelper.tearDownBuiltIns();
		waitForAutoBuild();
		ExternalProjectMappings.REDUCE_REGISTERED_NPMS = true;
	}

	@Override
	protected ProjectImporter getProjectImporter() {
		return new ProjectImporter(new File(new File("probands/" + PROJECT_NAME + "/").getAbsolutePath()));
	}

	/***/
	@Test
	public void checkNoCustomResourceDescriptionsLeaksToBuilderState() throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		assertTrue("Test project is not accessible.", project.isAccessible());

		syncExtAndBuild();
		// Since we do not know whether the built-in initialization or the test project import happened earlier...
		// Make sure both test module and project description file get into the index.
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		// List of expected external library warnings originating from shipped code projects:
		//
		// line 0: External library warning: public is reserved identifier.
		// line 0: External library warning: The use of the any type in a union type is discouraged.
		// line 0: External library warning: Neither constructor{? extends N4Object} is a subtype of type{T} nor type{T}
		// is a subtype of constructor{? extends N4Object}. The expression will always evaluate to false.
		// line 0: External library warning: Neither constructor{? extends N4Object} is a subtype of type{T} nor type{T}
		// is a subtype of constructor{? extends N4Object}. The expression will always evaluate to false.
		// line 0: External library warning: Neither constructor{? extends N4Object} is a subtype of type{T} nor type{T}
		// is a subtype of constructor{? extends N4Object}. The expression will always evaluate to false.
		// line 0: External library warning: Unnecessary cast from ~~ResultGroup to Object
		// line 0: External library warning: Unnecessary cast from Array<ResultGroups>
		assertMarkers("Expected exactly 7 issues.", project, 7); // issues are in external libraries

		final Resource resource = persister.createResource();
		assertNotNull("Test resource was null.", resource);

		final Set<org.eclipse.emf.common.util.URI> beforeCrashBuilderState = from(
				builderState.getAllResourceDescriptions())
						.transform(desc -> desc.getURI()).toSet();
		final int builderStateBeforeReloadSize = Iterables.size(beforeCrashBuilderState);
		final FluentIterable<IEObjectDescription> beforeTModulesInBuilderState = from(
				builderState.getAllResourceDescriptions()).transformAndConcat(desc -> desc.getExportedObjects())
						.filter(desc -> desc.getEClass() == TypesPackage.eINSTANCE.getTModule());
		int beforeTModulesInBuilderStateSize = size(beforeTModulesInBuilderState);
		int beforeTModulesInBuilderStateWithUserDataSize = size(
				beforeTModulesInBuilderState.filter(desc -> null != desc.getUserData(USERDATA_KEY_SERIALIZED_SCRIPT)));

		persister.saveToResource(resource, builderState.getAllResourceDescriptions());
		final Iterable<EObject> beforeCrashResource = newArrayList(resource.getContents());
		final int persistedBeforeReloadSize = resource.getContents().size();

		// Imitate VM crash with force built-in unload and reload.
		unLoadBuiltIns();
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		// Test module issues:
		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'Assert'.
		// Couldn't resolve reference to TExportableElement 'Assert'.
		// Import of Assert cannot be resolved.

		// package.json issues:
		// Project does not exist with project ID: org.eclipse.n4js.mangelhaft.
		// Project does not exist with project ID: org.eclipse.n4js.mangelhaft.assert.
		// Project with test fragment should depend on org.eclipse.n4js.mangelhaft.
		assertMarkers("Expected exactly 7 issues.", project, 7);

		loadBuiltIns();
		syncExtAndBuild();

		resource.getContents().clear();
		// see above for list of expected issues
		assertMarkers("Expected exactly 7 issues.", project, 7); // issues are in external libraries

		final Set<org.eclipse.emf.common.util.URI> afterCrashBuilderState = from(
				builderState.getAllResourceDescriptions())
						.transform(desc -> desc.getURI()).toSet();
		final int builderStateAfterReloadSize = Iterables.size(afterCrashBuilderState);

		final FluentIterable<IEObjectDescription> afterTModulesInBuilderState = from(
				builderState.getAllResourceDescriptions()).transformAndConcat(desc -> desc.getExportedObjects())
						.filter(desc -> desc.getEClass() == TypesPackage.eINSTANCE.getTModule());
		int afterTModulesInBuilderStateSize = size(afterTModulesInBuilderState);
		int afterTModulesInBuilderStateWithUserDataSize = size(
				afterTModulesInBuilderState.filter(desc -> null != desc.getUserData(USERDATA_KEY_SERIALIZED_SCRIPT)));

		persister.saveToResource(resource, builderState.getAllResourceDescriptions());
		final Iterable<EObject> afterCrashResource = newArrayList(resource.getContents());
		final int persistedAfterReloadSize = resource.getContents().size();

		assertTrue("Expected same number of persisted and available resource description before and after crash. Was:"
				+ "\nBuilder state before reload size: " + builderStateBeforeReloadSize
				+ "\nBuilder state after reload size: " + builderStateAfterReloadSize
				+ "\nPersisted before reload size: " + persistedBeforeReloadSize
				+ "\nPersisted after reload size: " + persistedAfterReloadSize
				+ "\nDifferences: "
				+ printDiff(beforeCrashBuilderState, afterCrashBuilderState, beforeCrashResource, afterCrashResource),
				builderStateBeforeReloadSize == builderStateAfterReloadSize
						&& persistedBeforeReloadSize == persistedAfterReloadSize
						&& builderStateBeforeReloadSize == persistedBeforeReloadSize);

		assertTrue(
				"Expected same number for EObject descriptions for TModules before and after crash. Before was: "
						+ beforeTModulesInBuilderStateSize + " after was: " + afterTModulesInBuilderStateSize,
				beforeTModulesInBuilderStateSize == afterTModulesInBuilderStateSize);

		assertTrue(
				"Expected same number for EObject descriptions for TModules with serialized user data before and after crash. Before was: "
						+ beforeTModulesInBuilderStateWithUserDataSize + " after was: "
						+ afterTModulesInBuilderStateWithUserDataSize,
				beforeTModulesInBuilderStateWithUserDataSize == afterTModulesInBuilderStateWithUserDataSize);

	}

	private String printDiff(Set<org.eclipse.emf.common.util.URI> beforeCrashBuilderState,
			Set<org.eclipse.emf.common.util.URI> afterCrashBuilderState, Iterable<EObject> beforeCrashResource,
			Iterable<EObject> afterCrashResource) {

		return "\n--------------------------------------------------------------------------------"
				+ "\n\t### Before crash difference in builder state and resource: "
				+ Joiner.on("\n\t\t- ").join(difference(beforeCrashBuilderState, getContent(beforeCrashResource)))
				+ "\n\t### After crash difference in builder state and resource: "
				+ Joiner.on("\n\t\t- ").join(difference(afterCrashBuilderState, getContent(afterCrashResource)))
				+ "\n--------------------------------------------------------------------------------";

	}

	private Set<org.eclipse.emf.common.util.URI> getContent(Iterable<? extends EObject> contents) {
		return from(contents).filter(ResourceDescriptionImpl.class).transform(desc -> desc.getURI()).toSet();
	}

}
