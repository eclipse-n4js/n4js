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
package org.eclipse.n4js.ui.building.instructions;

import static org.eclipse.xtext.ui.util.ResourceUtil.getContainer;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.ui.generator.IDerivedResourceMarkers;

/**
 * A {@link IBuildParticipantInstruction instruction} for a clean operation.
 */
@SuppressWarnings("restriction")
public class CleanInstruction extends AbstractBuildParticipantInstruction {

	/**
	 * Creates a new clean operation for the given project.
	 */
	public CleanInstruction(IProject project, Map<String, OutputConfiguration> outputConfigurations,
			IDerivedResourceMarkers derivedResourceMarkers) {
		super(project, outputConfigurations, derivedResourceMarkers);
		this.project = project;
		this.outputConfigurations = outputConfigurations;
		this.derivedResourceMarkers = derivedResourceMarkers;
	}

	@Override
	public void finish(List<Delta> deltas, IProgressMonitor progressMonitor) throws CoreException {
		SubMonitor cleanMonitor = SubMonitor.convert(progressMonitor, outputConfigurations.size());
		for (OutputConfiguration config : outputConfigurations.values()) {
			cleanOutput(project, config, cleanMonitor.newChild(1));
		}
	}

	private void cleanOutput(IProject aProject, OutputConfiguration config, IProgressMonitor monitor)
			throws CoreException {
		IContainer container = getContainer(aProject, config.getOutputDirectory());
		if (!container.exists()) {
			return;
		}
		if (config.isCanClearOutputDirectory()) {
			for (IResource resource : container.members()) {
				resource.delete(IResource.KEEP_HISTORY, monitor);
			}
		} else if (config.isCleanUpDerivedResources()) {
			List<IFile> resources = derivedResourceMarkers.findDerivedResources(container, null);
			for (IFile iFile : resources) {
				iFile.delete(IResource.KEEP_HISTORY, monitor);
			}
		}
	}

	@Override
	public void process(Delta delta, ResourceSet resourceSet, IProgressMonitor progressMonitor)
			throws CoreException {
		// do nothing
	}
}
