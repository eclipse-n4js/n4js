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
package org.eclipse.n4js.external;

import static java.util.Collections.emptyIterator;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSSourceContainerType;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.xtext.builder.impl.ToBeBuilt;

/**
 * NOOP implementation of the external library workspace.
 */
@SuppressWarnings("restriction")
public class NoopExternalLibraryWorkspace extends ExternalLibraryWorkspace {

	@Override
	public RegisterResult registerProjects(ToBeBuilt result, IProgressMonitor monitor, boolean triggerCleanbuild) {
		// NOOP
		return new RegisterResult();
	}

	@Override
	public Collection<N4JSExternalProject> getProjects() {
		return emptyList();
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(final java.net.URI rootLocation) {
		return emptyList();
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(final Collection<java.net.URI> rootLocations) {
		return emptyList();
	}

	@Override
	public Collection<ProjectDescription> getProjectsDescriptions(java.net.URI rootLocation) {
		return emptyList();
	}

	@Override
	public ExternalProject getProject(final String projectName) {
		return null;
	}

	@Override
	public IResource getResource(final URI location) {
		return null;
	}

	@Override
	public void updateState() {
		// NOOP
	}

	@Override
	public ProjectDescription getProjectDescription(final URI location) {
		return null;
	}

	@Override
	public URI getLocation(final URI projectURI, final ProjectReference reference,
			final N4JSSourceContainerType expectedN4JSSourceContainerType) {
		return null;
	}

	@Override
	public Iterator<URI> getArchiveIterator(final URI archiveLocation, final String archiveRelativeLocation) {
		return emptyIterator();
	}

	@Override
	public Iterator<URI> getFolderIterator(final URI folderLocation) {
		return emptyIterator();
	}

	@Override
	public URI findArtifactInFolder(final URI folderLocation, final String folderRelativePath) {
		return null;
	}

	@Override
	public URI findProjectWith(final URI nestedLocation) {
		return null;
	}

}
