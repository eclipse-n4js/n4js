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
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper for finding artifacts in the projects. Delegates to
 * {@link N4JSSourceFolderSnapshot#findArtifact(QualifiedName, Optional)} but exposes a more convenient API.
 */
@Singleton
public final class FindArtifactHelper {

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	/**
	 * Same as {@link #findArtifact(N4JSProjectConfigSnapshot, QualifiedName, Optional)}, but the qualified name can be
	 * provided as a String.
	 */
	public URI findArtifact(N4JSProjectConfigSnapshot project, String fqn, Optional<String> fileExtension) {
		return findArtifact(project, qualifiedNameConverter.toQualifiedName(fqn), fileExtension);
	}

	/**
	 * Convenience method for {@link N4JSSourceFolderSnapshot#findArtifact(QualifiedName, Optional)}, searching all
	 * source folders of the given project.
	 */
	public URI findArtifact(N4JSProjectConfigSnapshot project, QualifiedName fqn, Optional<String> fileExtension) {
		for (N4JSSourceFolderSnapshot srcFolder : project.getSourceFolders()) {
			final SafeURI<?> uri = srcFolder.findArtifact(fqn, fileExtension);
			if (uri != null)
				return uri.toURI();
		}
		return null;
	}

}
