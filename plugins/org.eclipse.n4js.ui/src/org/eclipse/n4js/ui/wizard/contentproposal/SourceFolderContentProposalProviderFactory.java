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
package org.eclipse.n4js.ui.wizard.contentproposal;

import java.util.stream.StreamSupport;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;

import com.google.inject.Inject;

/**
 * A content proposal provider to propose source folders in a given project.
 */
public class SourceFolderContentProposalProviderFactory {

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Returns a source folder proposal provider for the given project.
	 *
	 * @param contextProject
	 *            The project to look for source folders
	 * @return The provider
	 */
	public IContentProposalProvider createProviderForProject(IProject contextProject) {
		if (null == contextProject) {
			return null;
		}

		IN4JSProject n4Project = StreamSupport.stream(n4jsCore.findAllProjects().spliterator(), false)
				// Filter for the context project
				.filter(p -> p.getProjectName().getRawName().equals(contextProject.getName())) 
				.findAny().orElse(null);

		if (n4Project == null) {
			return null;
		}
		SimpleContentProposalProvider provider = new SimpleContentProposalProvider(
				n4Project.getSourceContainers().stream()
						.map(src -> src.getRelativeLocation())
						.toArray(String[]::new));
		provider.setFiltering(true);

		return provider;
	}
}
