/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper for finding artifacts in the projects. Delegates to the
 * {@link IN4JSSourceContainer#findArtifact(QualifiedName, Optional)} but exposes more convenient API.
 */
@Singleton
public final class FindArtifactHelper {

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	/**
	 * Same as {@link #findArtifact(IN4JSProject, QualifiedName, Optional)}, but the qualified name can be provided as a
	 * String.
	 */
	public URI findArtifact(IN4JSProject project, String fqn, Optional<String> fileExtension) {
		return findArtifact(project, qualifiedNameConverter.toQualifiedName(fqn), fileExtension);
	}

	/**
	 * Convenience method for {@link IN4JSSourceContainer#findArtifact(QualifiedName, Optional)}, searching all source
	 * containers of the given project.
	 */
	public URI findArtifact(IN4JSProject project, QualifiedName fqn, Optional<String> fileExtension) {
		for (IN4JSSourceContainer srcConti : project.getSourceContainers()) {
			final SafeURI<?> uri = srcConti.findArtifact(fqn, fileExtension);
			if (uri != null)
				return uri.toURI();
		}
		return null;
	}

}
