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

import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Optional;

import org.eclipse.n4js.ArchiveURIUtil;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.n4mf.SourceContainerType;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSArchiveSourceContainer extends AbstractSourceContainer implements IN4JSSourceContainer {

	private final N4JSArchive archive;

	protected N4JSArchiveSourceContainer(N4JSArchive archive, SourceContainerType type, String relativeLocation) {
		super(type, relativeLocation);
		this.archive = archive;
	}

	@Override
	public IN4JSProject getProject() {
		return archive.getProject();
	}

	@Override
	public IN4JSArchive getLibrary() {
		return archive;
	}

	@Override
	public boolean isLibrary() {
		return true;
	}

	@Override
	public Iterator<URI> iterator() {
		return archive.getModel().iterator(this);
	}

	@Override
	public URI findArtifact(QualifiedName name, Optional<String> fileExtension) {
		return archive.getModel().findArtifact(this, name, fileExtension);
	}

	@Override
	public URI getLocation() {
		return ArchiveURIUtil.createURI(archive.getLocation(), getRelativeLocation());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((archive == null) ? 0 : archive.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof N4JSArchiveSourceContainer))
			return false;
		N4JSArchiveSourceContainer other = (N4JSArchiveSourceContainer) obj;
		if (archive == null) {
			if (other.archive != null)
				return false;
		} else if (!archive.equals(other.archive))
			return false;
		return true;
	}

}
