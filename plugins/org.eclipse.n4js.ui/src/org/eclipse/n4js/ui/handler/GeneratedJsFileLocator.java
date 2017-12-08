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
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;
import static org.eclipse.n4js.N4JSGlobals.JS_FILE_EXTENSION;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.fileextensions.FileExtensionType;
import org.eclipse.n4js.fileextensions.FileExtensionsRegistry;
import org.eclipse.n4js.generator.AbstractSubGenerator;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.CompilerHelper;
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Service class for locating generated JS files for N4JS files.
 */
public class GeneratedJsFileLocator {
	@Inject
	private IN4JSCore core;

	@Inject
	private CompilerHelper compilerHelper;

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
				final String targetFileName = compilerHelper.getTargetFileName(fileUri, JS_FILE_EXTENSION);
				// TODO replace hard coded ES5 sub-generator ID once it is clear how to use various
				// sub-generators for runners (IDE-1487)
				final String targetFileRelativeLocation = AbstractSubGenerator
						.calculateOutputDirectory(project.get().getOutputPath(), genID)
						+ "/" + targetFileName;
				final IFile targetFile = file.getProject().getFile(targetFileRelativeLocation);
				if (targetFile.exists()) {
					return targetFile;
				}
			}
		}
		return null;
	}

}
