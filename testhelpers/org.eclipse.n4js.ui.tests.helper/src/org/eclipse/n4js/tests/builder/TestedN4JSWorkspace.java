/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.builder;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.n4js.ui.external.ExternalLibraryBuildScheduler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.xtext.builder.impl.ProjectOpenedOrClosedListener;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.eclipse.xtext.ui.testing.util.TestedWorkspace;
import org.eclipse.xtext.util.Exceptions;
import org.junit.Assert;
import org.junit.runner.Description;

import com.google.inject.Inject;

/**
 *
 */
@SuppressWarnings("restriction")
public class TestedN4JSWorkspace extends TestedWorkspace {

	@Inject
	private ExternalLibraryBuildScheduler externalLibraryBuildJobProvider;
	private String name;

	/***/
	@Inject
	public TestedN4JSWorkspace(ISharedStateContributionRegistry contributions) {
		this(contributions.getSingleContributedInstance(ProjectOpenedOrClosedListener.class));
	}

	/***/
	public TestedN4JSWorkspace(ProjectOpenedOrClosedListener closedProjectTaskProcessor) {
		super(closedProjectTaskProcessor);
	}

	@Override
	public void joinJobsBeforeBuild() {
		super.joinJobsBeforeBuild();
		externalLibraryBuildJobProvider.joinBuildJob();
	}

	@Override
	protected void starting(Description d) {
		name = d.getMethodName();

		assertEmptyIndex();

		IProject[] projects = IResourcesSetupUtil.root().getProjects();
		if (projects.length != 0) {
			Assert.assertEquals(1, projects.length);
			Assert.assertEquals("RemoteSystemsTempFiles", projects[0].getName());
		}

		if (PlatformUI.isWorkbenchRunning()) {
			final IIntroManager introManager = PlatformUI.getWorkbench().getIntroManager();
			if (introManager.getIntro() != null) {
				Display.getDefault().asyncExec(() -> introManager.closeIntro(introManager.getIntro()));
			}
		}
	}

	@Override
	public void deleteAllProjects() {
		try {
			new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor monitor)
						throws CoreException, InvocationTargetException,
						InterruptedException {
					IProject[] visibleProjects = root().getProjects();
					deleteProjects(visibleProjects);
					IProject[] hiddenProjects = root().getProjects(IContainer.INCLUDE_HIDDEN);
					deleteProjects(hiddenProjects);
				}

				private void deleteProjects(IProject[] projects) {
					for (IProject project : projects) {
						if (!"RemoteSystemsTempFiles".equals(project.getName()) && project.exists()) {
							try {
								project.delete(true, true, monitor());
							} catch (CoreException e) {
								throw new RuntimeException(e);
							}
						}
					}
				}
			}.run(monitor());
		} catch (InvocationTargetException e) {
			Exceptions.throwUncheckedException(e.getCause());
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public String getTestName() {
		return name;
	}

}
