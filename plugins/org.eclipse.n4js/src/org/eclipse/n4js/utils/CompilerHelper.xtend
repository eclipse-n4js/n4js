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
package org.eclipse.n4js.utils

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.generator.GeneratorExceptionHandler
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.projectModel.ResourceNameComputer

/**
 */
 @Singleton
public class CompilerHelper {
	@Inject ResourceNameComputer resourceNameComputer

	@Inject GeneratorExceptionHandler generatorExceptionHandler

	/**
	 * Convenience method, delegates to {@link #getTargetFileName(URI , String)} with the URI of the given resource.
	 */
	def String getTargetFileName(Resource n4jsSourceFile, String compiledFileExtension) {
		return safeGetTargetFileName(null, n4jsSourceFile.URI, compiledFileExtension)
	}

	/**
	 * Convenience method, delegates to {@link #getTargetFileName(IN4JSProject, URI , String)} with the {@code null} project.
	 */
	def String getTargetFileName(URI n4jsSourceURI, String compiledFileExtension) {
		return safeGetTargetFileName(null, n4jsSourceURI, compiledFileExtension)
	}
	
	/**
	 * Returns the name of the target file (without path) to which the source is to be compiled to.
	 * Default implementation returns a configured project Name with version + file name + extension.
	 * E.g., "proj/p/A.js" for a file A in proj and a compiledFileExtension of "js".
	 * <p>
	 * The compiledFileExtension should not include the separator dot; it may be <code>null</code>
	 * and then no extension is appended.
	 */
	def String getTargetFileName(IN4JSProject project, URI n4jsSourceURI, String compiledFileExtension) {
		return safeGetTargetFileName(project, n4jsSourceURI, compiledFileExtension)
	}
	
	/** Delegates to {@link ResourceNameComputer#generateFileDescriptor} but takes care to prepare data. */
	private def String safeGetTargetFileName(IN4JSProject project, URI n4jsSourceURI, String compiledFileExtension) {

		val extStr = if(compiledFileExtension!==null && compiledFileExtension.length>0) "." + compiledFileExtension else "";

		var String targetFilePath = null
		try {
			if(project === null)
				targetFilePath = resourceNameComputer.generateFileDescriptor(n4jsSourceURI, extStr)
			else
				targetFilePath = resourceNameComputer.generateFileDescriptor(project,n4jsSourceURI, extStr)
		} catch (Throwable t) {
			generatorExceptionHandler.handleError(t.message, t)
			targetFilePath = null
		}

		return targetFilePath;
	}

}
