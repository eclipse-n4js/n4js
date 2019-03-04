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
import static org.eclipse.n4js.resource.UserdataMapper.USERDATA_KEY_SERIALIZED_SCRIPT;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ui.building.ResourceDescriptionWithoutModuleUserData;
import org.eclipse.n4js.ui.external.ExternalProjectMappings;
import org.eclipse.n4js.ui.internal.ContributingResourceDescriptionPersister;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.builderState.impl.ResourceDescriptionImpl;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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

	/** Disable reduction of external libraries */
	@BeforeClass
	static public void disableMappingsFlag() {
		ExternalProjectMappings.REDUCE_REGISTERED_NPMS = false;
	}

	/** */
	@AfterClass
	static public void enableMappingsFlag() {
		ExternalProjectMappings.REDUCE_REGISTERED_NPMS = true;
	}

	@Override
	protected boolean provideShippedCode() {
		return true;
	}

	private void loadBuiltIns() {
		shippedCodeInitializeTestHelper.setupBuiltIns();
		ProjectTestsUtils.waitForAllJobs();
		waitForAutoBuild();
	}

	private void unLoadBuiltIns() {
		shippedCodeInitializeTestHelper.tearDownBuiltIns();
		ProjectTestsUtils.waitForAllJobs();
		waitForAutoBuild();
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

		libraryManager.runNpmYarnInstallOnAllProjects(new NullProgressMonitor());
		syncExtAndBuild();
		// Since we do not know whether the built-in initialization or the test project import happened earlier...
		// Make sure both test module and project description file get into the index.
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();
		assertMarkers("Expected exactly 0 issues.", project, 0); // issues are in external libraries

		final Resource resource = persister.createResource();
		assertNotNull("Test resource was null.", resource);

		final Set<URI> beforeCrashBuilderState = from(builderState.getAllResourceDescriptions())
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
		syncExtAndBuild();

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

		libraryManager.runNpmYarnInstallOnAllProjects(new NullProgressMonitor());
		syncExtAndBuild();

		resource.getContents().clear();
		assertMarkers("Expected exactly 0 issues.", project, 0); // issues are in external libraries

		final Set<URI> afterCrashBuilderState = from(builderState.getAllResourceDescriptions())
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

		boolean validBuilderState = builderStateBeforeReloadSize == builderStateAfterReloadSize
				&& persistedBeforeReloadSize == persistedAfterReloadSize
				&& builderStateBeforeReloadSize == persistedBeforeReloadSize;

		assertTrue("Expected same number of persisted and available resource description before and after crash. Was:"
				+ "\nBuilder state before reload size: " + builderStateBeforeReloadSize
				+ "\nBuilder state after reload size: " + builderStateAfterReloadSize
				+ "\nPersisted before reload size: " + persistedBeforeReloadSize
				+ "\nPersisted after reload size: " + persistedAfterReloadSize
				+ "\nDifferences: "
				+ printDiff(beforeCrashBuilderState, afterCrashBuilderState, beforeCrashResource, afterCrashResource),
				validBuilderState);

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

	private String printDiff(Set<URI> beforeCrashBuilderState,
			Set<URI> afterCrashBuilderState, Iterable<EObject> beforeCrashResource,
			Iterable<EObject> afterCrashResource) {

		Set<URI> beforeCrashResourceUris = getContent(beforeCrashResource);
		Set<URI> afterCrashResourceUris = getContent(afterCrashResource);

		new HashSet<>(beforeCrashBuilderState).retainAll(afterCrashBuilderState);

		Set<URI> stateBeforeAfterIntersection = new HashSet<>(beforeCrashBuilderState);
		stateBeforeAfterIntersection.retainAll(afterCrashBuilderState);
		Set<URI> stateBeforeAfterDisjoint = new HashSet<>(beforeCrashBuilderState);
		stateBeforeAfterDisjoint.addAll(afterCrashBuilderState);
		stateBeforeAfterDisjoint.removeAll(stateBeforeAfterIntersection);
		String builderStateBeforeAfterDifferenceJoined = Joiner.on("\n\t\t- ").join(stateBeforeAfterDisjoint);

		Set<URI> beforeIntersection = new HashSet<>(beforeCrashBuilderState);
		beforeIntersection.retainAll(beforeCrashResourceUris);
		Set<URI> beforeDisjoint = new HashSet<>(beforeCrashBuilderState);
		beforeDisjoint.addAll(beforeCrashResourceUris);
		beforeDisjoint.removeAll(beforeIntersection);
		String beforeDifferenceJoined = Joiner.on("\n\t\t- ").join(beforeDisjoint);

		Set<URI> afterIntersection = new HashSet<>(afterCrashBuilderState);
		afterIntersection.retainAll(afterCrashResourceUris);
		Set<URI> afterDisjoint = new HashSet<>(afterCrashBuilderState);
		afterDisjoint.addAll(afterCrashResourceUris);
		afterDisjoint.removeAll(afterIntersection);
		String afterDifferenceJoined = Joiner.on("\n\t\t- ").join(afterDisjoint);
		return "\n--------------------------------------------------------------------------------"
				+ "\n\t### Builder state difference before and after crash: "
				+ builderStateBeforeAfterDifferenceJoined
				+ "\n\t### Before crash difference in builder state and resource: "
				+ beforeDifferenceJoined
				+ "\n\t### After crash difference in builder state and resource: "
				+ afterDifferenceJoined
				+ "\n--------------------------------------------------------------------------------";

	}

	private Set<URI> getContent(Iterable<? extends EObject> contents) {
		return from(contents).filter(ResourceDescriptionImpl.class).transform(desc -> desc.getURI()).toSet();
	}

}
