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
package org.eclipse.n4js.tooling.organizeImports;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.core.resources.IFile;

import com.google.inject.Inject;

import org.eclipse.n4js.fileextensions.FileExtensionType;
import org.eclipse.n4js.fileextensions.FileExtensionsRegistry;
import org.eclipse.n4js.utils.Lazy;

/**
 * Helper to used to filter files that should / should not be handled by organize import. For example we expect imports
 * to be handled in {@code N4JS} files but not in the {@code JS} files.
 *
 * This helper is using {@link FileExtensionsRegistry}, it will work as expected only for languages registering their
 * file types to the registry (e.g. via exposed extension point).
 */
public class FileExtensionFilter implements Predicate<IFile> {

	/** Lazily initialized collection of file extensions to be used when checking file */
	final private Lazy<Collection<String>> n4FileExtensions;

	/**
	 * This class is expected to be injected, but caller can call constructor by providing all required parameters.
	 *
	 * @param fileExtensionsRegistry
	 *            used to obtain supported languages
	 */
	@Inject
	public FileExtensionFilter(FileExtensionsRegistry fileExtensionsRegistry) {
		n4FileExtensions = Lazy.create(() -> getN4FileExtensions(fileExtensionsRegistry));
	}

	/**
	 * Checking the file type by getting the known extensions from the FileExtensionProvider
	 *
	 * @param iFile
	 *            file to judge
	 * @return true if the file is a valid file for organize import
	 */
	@Override
	public boolean test(IFile iFile) {
		String fileExtension = iFile.getFileExtension();
		return fileExtension != null && n4FileExtensions.get().contains(fileExtension);
	}

	/**
	 * Read extension point to read desired file extensions to organize.
	 *
	 * @return Set of extensions for files on which organization should be applied
	 */
	private static Collection<String> getN4FileExtensions(FileExtensionsRegistry fileExtensionsRegistry) {
		Set<String> n4FileExtensions = new HashSet<>(
				fileExtensionsRegistry.getFileExtensions(FileExtensionType.TYPABLE_FILE_EXTENSION));
		n4FileExtensions.removeAll(fileExtensionsRegistry.getFileExtensions(FileExtensionType.RAW_FILE_EXTENSION));
		return n4FileExtensions;
	}

}
