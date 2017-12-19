/*******************************************************************************
 * Copyright (c) 2009 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.tests.builder;

import static java.lang.Boolean.TRUE;
import static org.apache.log4j.Logger.getLogger;
import static org.eclipse.core.resources.IContainer.INCLUDE_HIDDEN;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.n4js.tests.builder.BuilderUtil.countResourcesInIndex;
import static org.eclipse.n4js.tests.builder.BuilderUtil.getAllResourceDescriptionsAsString;
import static org.eclipse.n4js.tests.builder.BuilderUtil.getBuilderState;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;
import static org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.cleanWorkspace;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.root;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.tests.util.ProjectUtils;
import org.eclipse.n4js.ui.building.ResourceDescriptionWithoutModuleUserData;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Event;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author Sven Efftinge - Initial contribution and API
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
public abstract class AbstractBuilderTest extends Assert implements IResourceDescription.Event.Listener {

	private static final Logger LOGGER = getLogger(AbstractBuilderTest.class);

	static {
		EclipseGracefulUIShutdownEnabler.enableOnce();
	}

	/***/
	public static final String F_EXT = ".n4js";
	private volatile List<Event> events = Lists.newArrayList();

	@Inject
	private IResourceSetProvider resourceSetProvider;
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	/***/
	@Before
	public void setUp() throws Exception {
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

	private void toggleAutobuild(final boolean enable) {
		if (isWorkbenchRunning()) {
			final IWorkspaceDescription workspaceDescription = getWorkspace().getDescription();
			if (null != workspaceDescription) {
				if (workspaceDescription.isAutoBuilding() != enable) {
					workspaceDescription.setAutoBuilding(enable);
					try {
						getWorkspace().setDescription(workspaceDescription);
					} catch (final CoreException e) {
						throw new IllegalStateException("Error while trying to turn workspace autobuild "
								+ (enable ? "on" : "off") + ".", e);
					}
				}
			}

		}
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

	/***/
	@After
	public void tearDown() throws Exception {
		// save the files as otherwise the projects cannot be deleted
		closeAllEditorsForTearDown();
		cleanWorkspace();
		waitForAutoBuild();
		events.clear();
		getBuilderState().removeListener(this);
		assertEquals("Resources in index:\n" + getAllResourceDescriptionsAsString() + "\n", 0, countResourcesInIndex());
		assertEquals(0, root().getProjects().length);
	}

	/***/
	public void waitForAutoBuild() {
		waitForAutoBuild(true);
	}

	/***/
	public void waitForAutoBuild(boolean assertValidityOfXtextIndex) {
		ProjectUtils.waitForAutoBuild();
		if (assertValidityOfXtextIndex)
			assertXtextIndexIsValid();
	}

	/***/
	public void cleanBuild() throws CoreException {
		IResourcesSetupUtil.cleanBuild();
	}

	/***/
	@SuppressWarnings("restriction")
	protected IWorkbenchPage getActivePage() {
		IWorkbenchPage page = null;
		if (org.eclipse.ui.internal.Workbench.getInstance() != null) {
			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
			// Could be null if using Timeout test rule and does not run on main thread.
			if (null != window) {
				page = window.getActivePage();
			}
		}
		return page;
	}

	private void closeAllEditorsForTearDown() {
		IWorkbenchPage page = getActivePage();
		if (page != null)
			page.closeAllEditors(false);
	}

	@Override
	public void descriptionsChanged(Event event) {
		this.events.add(event);
	}

	/***/
	public List<Event> getEvents() {
		return events;
	}

	/***/
	public <T> T getInstance(Class<T> type) {
		Injector injector = N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
		return injector.getInstance(type);
	}

	/**
	 * Returns the Xtext index, i.e. a new instance of {@link IResourceDescriptions}.
	 */
	protected IResourceDescriptions getXtextIndex() {
		final ResourceSet resourceSet = resourceSetProvider.get(null);
		resourceSet.getLoadOptions().put(PERSISTED_DESCRIPTIONS, TRUE);
		return resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
	}

	/**
	 * Performs some general validity checks on the Xtext index.
	 */
	protected void assertXtextIndexIsValid() {
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
