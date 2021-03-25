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

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.locations.SafeURI;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper for finding artifacts in the projects.
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
	 * Convenience method for {@link #findArtifact(N4JSSourceFolderSnapshot, QualifiedName, Optional)}, searching all
	 * source folders of the given project.
	 */
	public URI findArtifact(N4JSProjectConfigSnapshot project, QualifiedName fqn, Optional<String> fileExtension) {
		for (N4JSSourceFolderSnapshot srcFolder : project.getSourceFolders()) {
			final SafeURI<?> uri = findArtifact(srcFolder, fqn, fileExtension);
			if (uri != null)
				return uri.toURI();
		}
		return null;
	}

	/**
	 * If the receiving source folder actually contains a file for the given fully qualified name and file extension on
	 * disk, this method will return a URI for this file of the same format as the URIs returned by method
	 * {@link N4JSSourceFolderSnapshot#getContents()}. Otherwise, this method returns <code>null</code>.
	 * <p>
	 * The file extension may but need not contain a leading '.'.
	 * <p>
	 * Implementations are expected to be optimized for fast look-up (in particular, they should avoid iterating over
	 * all URIs returned by method {@link N4JSSourceFolderSnapshot#getContents()}).
	 */
	public FileURI findArtifact(N4JSSourceFolderSnapshot sourceFolder, QualifiedName name,
			Optional<String> fileExtension) {

		final List<String> nameSegments = name.getSegments();
		if (nameSegments.isEmpty()) {
			return null;
		}
		final String[] nameSegmentsCpy = nameSegments.toArray(new String[nameSegments.size()]);
		final String ext = fileExtension.or("").trim();
		final String extWithDot = !ext.isEmpty() && !ext.startsWith(".") ? "." + ext : ext;
		final int idxLast = nameSegmentsCpy.length - 1;
		nameSegmentsCpy[idxLast] = nameSegmentsCpy[idxLast] + extWithDot;

		FileURI sourceFolderPath = sourceFolder.getPathAsFileURI();
		FileURI result = sourceFolderPath.appendSegments(nameSegmentsCpy);
		if (result.exists()) {
			return result;
		}
		return null;
	}
}
