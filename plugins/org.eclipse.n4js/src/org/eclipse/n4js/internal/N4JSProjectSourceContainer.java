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
package org.eclipse.n4js.internal;

import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.Strings;

import com.google.common.base.Optional;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSProjectSourceContainer extends AbstractSourceContainer implements IN4JSSourceContainer {

	private final N4JSProject project;

	protected N4JSProjectSourceContainer(N4JSProject project, SourceContainerType type, String relativeLocation) {
		super(type, relativeLocation);
		this.project = project;
	}

	@Override
	public IN4JSProject getProject() {
		return project;
	}

	@Override
	public SafeURI<?> findArtifact(QualifiedName name, Optional<String> fileExtension) {
		return project.getModel().findArtifact(this, name, fileExtension);
	}

	@Override
	public SafeURI<?> getLocation() {
		String location = getRelativeLocation();
		if (!Strings.isEmpty(location)) {
			return project.getLocation().appendPath(getRelativeLocation());
		}
		return project.getLocation();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof N4JSProjectSourceContainer))
			return false;
		N4JSProjectSourceContainer other = (N4JSProjectSourceContainer) obj;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}
}
