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
package org.eclipse.n4js.ui.example;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.wizard.ExampleInstallerWizard;
import org.eclipse.n4js.ui.wizard.utils.WizardHelper;

import com.google.inject.Inject;

/**
 * Wizard for the {@code N4JS Tasks Example} projects.
 */
public class N4JSTasksExampleWizard extends ExampleInstallerWizard {

	/**
	 * Unique ID of the {@code N4JS Tasks Example} wizard.
	 */
	public static final String ID = N4JSTasksExampleWizard.class.getName();

	@Inject
	private WizardHelper wizardHelper;

	@Override
	public boolean performFinish() {
		if (super.performFinish()) {
			List<IProject> projects = getProjectDescriptors().stream()
					.map(ProjectDescriptor::getProject)
					.collect(Collectors.toList());
			wizardHelper.runNpmInstallInWizard(getContainer(), projects);
			return true;
		}
		return false;
	}

	@Override
	protected void installExample(IProgressMonitor progressMonitor) throws Exception {
		// import project(s) registered via the extension point
		super.installExample(progressMonitor);

		// we assume that the project(s) registered via the extension point are root projects containing a yarn
		// workspace with the actual projects contained in a top-level folder "packages"; so we need to import
		// those projects as well:
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File workspaceFolder = workspace.getRoot().getLocation().toFile().getAbsoluteFile();
		for (ProjectDescriptor pd : getProjectDescriptors()) {
			File projectFolder = new File(workspaceFolder, pd.getName());
			File packagesFolder = new File(projectFolder, "packages");
			for (File memberProject : packagesFolder.listFiles(File::isDirectory)) {
				importProject(workspace, memberProject, progressMonitor);
			}
		}
	}

	private static void importProject(IWorkspace workspace, File rootFolder, IProgressMonitor progressMonitor)
			throws CoreException {
		IPath path = new org.eclipse.core.runtime.Path(new File(rootFolder, ".project").getAbsolutePath());
		IProjectDescription desc = workspace.loadProjectDescription(path);
		IProject project = workspace.getRoot().getProject(desc.getName());
		project.create(desc, progressMonitor);
		project.open(progressMonitor);
	}
}
