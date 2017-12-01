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
package org.eclipse.n4js.ui.handler;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static org.eclipse.n4js.N4JSGlobals.JS_FILE_EXTENSION;
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import org.eclipse.n4js.fileextensions.FileExtensionType;
import org.eclipse.n4js.fileextensions.FileExtensionsRegistry;
import org.eclipse.n4js.generator.CompilerUtils;
import org.eclipse.n4js.generator.GeneratorException;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants;

/**
 * Service class for locating generated JS files for N4JS files.
 */
public class GeneratedJsFileLocator {
	@Inject
	private IN4JSCore core;

	@Inject
	private CompilerUtils compilerUtils;

	@Inject
	private FileExtensionBasedPropertTester tester;

	@Inject
	private FileExtensionsRegistry fileExtensionRegistry;
	// private TranspilableFileExtensionsProvider allowedFileExtensionProvider;

	/**
	 * Tries to locates the generated {@link IFile file} of an N4JS or pure JS file give with the argument and returns
	 * with it.
	 *
	 * @param file
	 *            the N4JS or JS file. Optional, can be {@code null}.
	 * @return the generated JS file. Optional, may absent if the generated file cannot be located. Never null.
	 */
	public Optional<IFile> tryGetGeneratedSourceForN4jsFile(final IFile file) {

		if (null == file || !file.exists()) {
			return absent();
		}

		if (tester.test(file, null, null,
				fileExtensionRegistry.getFileExtensions(FileExtensionType.TRANSPILABLE_FILE_EXTENSION))) {
			final IFile generatedFile = tryLocateGeneratedFile(file,
					N4JSLanguageConstants.TRANSPILER_SUBFOLDER_FOR_TESTS);
			if (null != generatedFile && generatedFile.exists()) {
				return fromNullable(generatedFile);
			}
		}

		return absent();
	}

	private IFile tryLocateGeneratedFile(final IFile file, final String genID) {
		final URI fileUri = createPlatformResourceURI(file.getFullPath().toOSString(), true);
		if (fileUri.isPlatform()) {
			final Optional<? extends IN4JSProject> project = core.findProject(fileUri);
			if (project.isPresent()) {
				try {
					final String targetFileName = compilerUtils.getTargetFileName(fileUri, JS_FILE_EXTENSION);
					final String targetFileRelativeLocation = project.get().getOutputPath() + "/"
					// TODO replace hard coded ES5 sub-generator ID once it is clear how to use various
					// sub-generators for runners (IDE-1487)
							+ genID + "/" + targetFileName;
					final IFile targetFile = file.getProject().getFile(targetFileRelativeLocation);
					if (targetFile.exists()) {
						return targetFile;
					}
				} catch (final GeneratorException e) {
					// file is not contained in a source container.
					return null;
				}
			}
		}
		return null;
	}

}
