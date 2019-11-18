/*******************************************************************************
 * Copyright (c) 2009 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.tests.builder;

import static org.apache.log4j.Logger.getLogger;
import static org.eclipse.core.resources.IContainer.INCLUDE_HIDDEN;
import static org.eclipse.n4js.tests.builder.BuilderUtil.countResourcesInIndex;
import static org.eclipse.n4js.tests.builder.BuilderUtil.getAllResourceDescriptionsAsString;
import static org.eclipse.n4js.tests.builder.BuilderUtil.getBuilderState;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.root;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.tests.util.EclipseUIUtils;
import org.eclipse.n4js.tests.util.ProjectTestsHelper;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.building.ResourceDescriptionWithoutModuleUserData;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.AutobuildUtils;
import org.eclipse.n4js.utils.process.ProcessResult;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.xtext.builder.impl.QueuedBuildData;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author Sven Efftinge - Initial contribution and API
 */
@SuppressWarnings("restriction")
@RunWith(XtextRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
public abstract class AbstractBuilderTest {

	private static final Logger LOGGER = getLogger(AbstractBuilderTest.class);

	static {
		EclipseGracefulUIShutdownEnabler.enableOnce();
	}

	/***/
	@Inject
	protected LibraryManager libraryManager;
	@Inject
	private IResourceSetProvider resourceSetProvider;
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;
	@Inject
	private ProjectTestsHelper projectTestsHelper;

	/**
	 * Allows to trigger operations on the workspace that are considered to prevent race conditions, e.g. allows to
	 * build synchronously.
	 */
	@Inject
	@Rule
	public TestedN4JSWorkspace testedWorkspace;

	@Inject
	private QueuedBuildData queuedBuildData;

	/** Setups workspace by cleaning and waiting for auto builds, asserting index is clean. */
	@Before
	final public void setUp() throws Exception {
		IResourcesSetupUtil.cleanWorkspace();
		IResourcesSetupUtil.cleanBuild();
		waitForAutoBuild();
		if (checkForCleanWorkspace()) {
			if (root().getProjects().length != 0 && 0 != root().getProjects(INCLUDE_HIDDEN).length) {
				StringBuilder error = new StringBuilder();
				tryCleanWorkspace(error);
				for (IProject project : root().getProjects()) {
					error.append(project.getName());
					error.append("\n");
				}
				assertEquals("", error.toString());
			}
			if (countResourcesInIndex() != 0) {
				StringBuilder error = new StringBuilder();
				tryCleanXtextIndex(error);
				for (IResourceDescription desc : getBuilderState().getAllResourceDescriptions()) {
					error.append(desc.getURI());
					error.append("\n");
				}
				assertEquals("", error.toString());
			}
		}
		if (PlatformUI.isWorkbenchRunning()) {
			final IIntroManager introManager = PlatformUI.getWorkbench().getIntroManager();
			if (introManager.getIntro() != null) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						introManager.closeIntro(introManager.getIntro());
					}
				});
			}
		}
	}

	private void tryCleanXtextIndex(final StringBuilder error) {
		try {
			LOGGER.info("Xtext index was not empty. Trying to clean up index...");
			turnOffAutobuild();
			tryCleanWorkspace(error);
			cleanBuild();
			LOGGER.info("Index clean up finished.");
		} catch (CoreException e) {
			throw new IllegalStateException("Error while performing clean build.", e);
		} finally {
			turnOnAutobuild();
			waitForAutoBuild();
		}
	}

	private void tryCleanWorkspace(final StringBuilder error) {
		deleteProjects(root().getProjects(), error);
		deleteProjects(root().getProjects(INCLUDE_HIDDEN), error);
	}

	private void turnOffAutobuild() {
		toggleAutobuild(false);
	}

	private void turnOnAutobuild() {
		toggleAutobuild(true);
	}

	/**
	 * Turns auto build on/off. Delegating to {@link AutobuildUtils#set(boolean)}.
	 *
	 * Consider using {@link AutobuildUtils#suppressAutobuild()} if you want to disable auto build only temporarily.
	 */
	private void toggleAutobuild(final boolean enable) {
		AutobuildUtils.set(enable);
	}

	private static void deleteProjects(final IProject[] projects, StringBuilder error) {
		for (final IProject project : projects) {
			if (project.exists()) {
				try {
					project.delete(true, true, new NullProgressMonitor());
				} catch (CoreException e) {
					error.append(e.getMessage());
				}
			}
		}
	}

	/**
	 * @return by default workspace should be clean, but when executed as XPECT test, XPECT already set up the workspace
	 */
	protected boolean checkForCleanWorkspace() {
		return true;
	}

	/**
	 * Clean up by saving and closing editors, cleaning the workspace, waiting for auto builds and asserting clean
	 * index.
	 */
	@After
	final public void tearDown() throws Exception {

		// save the files as otherwise the projects cannot be deleted
		libraryManager.deleteAllNodeModulesFolders(new NullProgressMonitor());
		closeAllEditorsForTearDown();
		ProjectTestsUtils.closeAllProjectsInWorkspace();
		testedWorkspace.cleanBuild();
		IResourcesSetupUtil.cleanWorkspace();
		testedWorkspace.cleanBuild();
		assertEquals("Resources in index:\n" + getAllResourceDescriptionsAsString() + "\n", 0,
				countResourcesInIndex());

		final IProject[] projects = root().getProjects();
		assertTrue("Expected empty workspace. Projects were in workspace: " + Arrays.toString(projects),
				0 == projects.length);

		queuedBuildData.reset();
	}

	/***/
	public void waitForAutoBuild() {
		waitForAutoBuild(true);
	}

	/***/
	public void waitForAutoBuild(boolean assertValidityOfXtextIndex) {
		testedWorkspace.build();

		ProjectTestsUtils.waitForAllJobs();
		if (assertValidityOfXtextIndex)
			assertXtextIndexIsValid();
	}

	/**
	 * Trigger a blocking, incremental build. This is usually to be favored over {@link #waitForAutoBuild()} since it is
	 * a mere method call and does not involve job joining or similar troublesome stuff.
	 */
	public void waitForIncrementalBuild() {
		waitForIncrementalBuild(true);
	}

	/**
	 * Wait for the incremental build and optionally assert the Xtext index to be valid.
	 *
	 * @see #waitForIncrementalBuild()
	 */
	public void waitForIncrementalBuild(boolean assertValidityOfXtextIndex) {
		testedWorkspace.build();
		if (assertValidityOfXtextIndex)
			assertXtextIndexIsValid();
	}

	/**
	 * Waits for the jobs that do the housekeeping after project close or removal.
	 */
	protected void waitForNotReallyBuildButHousekeepingJobs() {
		testedWorkspace.joinJobsBeforeBuild();
	}

	/***/
	public void cleanBuild() throws CoreException {
		IResourcesSetupUtil.cleanBuild();
	}

	/** Synchronizes the index, rebuilds externals and workspace */
	protected void syncExtAndBuild() {
		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
		waitForAutoBuild();
	}

	private void closeAllEditorsForTearDown() {
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		if (page != null)
			page.closeAllEditors(false);
	}

	/***/
	public <T> T getInstance(Class<T> type) {
		Injector injector = N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
		return injector.getInstance(type);
	}

	/**
	 * Returns the Xtext index, i.e. a new instance of {@link IResourceDescriptions}. Note similarity to
	 * {@link IN4JSCore#getXtextIndex(ResourceSet)}
	 */
	protected IResourceDescriptions getXtextIndex() {
		final ResourceSet resourceSet = getResourceSet(null);
		resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS, Boolean.TRUE);
		return resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
	}

	/**
	 * Return resource set for given project. Note similarity to
	 * {@link IN4JSCore#createResourceSet(com.google.common.base.Optional)}
	 */
	protected ResourceSet getResourceSet(IProject project) {
		return resourceSetProvider.get(project);
	}

	/**
	 * Runs the given file with the node.js runner and asserts the actual output to match the given expected output.
	 */
	protected void assertCorrectOutput(IFile module, CharSequence expectedOutput) throws ExecutionException {
		URI moduleURI = URI.createPlatformResourceURI(module.getFullPath().toString(), true);
		ProcessResult result = projectTestsHelper.runWithNodeRunnerUI(moduleURI);
		String actualOutput = result.getStdOut().trim();
		String expectedOutputTrimmed = expectedOutput.toString().trim();
		assertEquals("incorrect output when running " + module.getName(), expectedOutputTrimmed, actualOutput);
	}

	/**
	 * Performs some general validity checks on the Xtext index. Only intended to be used in tests (e.g. no support for
	 * cancellation).
	 */
	protected void assertXtextIndexIsValid() {
		// ensure no build is running while we examine the Xtext index
		final ISchedulingRule rule = ResourcesPlugin.getWorkspace().getRoot();
		try {
			Job.getJobManager().beginRule(rule, null);
			assertXtextIndexIsValidInternal();
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	private void assertXtextIndexIsValidInternal() {
		final IResourceDescriptions index = getXtextIndex();
		final StringBuilder sb = new StringBuilder();
		for (IResourceDescription desc : index.getAllResourceDescriptions()) {
			if (desc instanceof ResourceDescriptionWithoutModuleUserData) {
				sb.append("\n");
				sb.append(IResourceDescription.class.getSimpleName());
				sb.append(" in index must not be an instance of ");
				sb.append(ResourceDescriptionWithoutModuleUserData.class.getSimpleName());
				sb.append(" but it was. URI: ");
				sb.append(desc.getURI());
			}
		}
		assertTrue(sb.toString(), 0 == sb.length());
	}
}
