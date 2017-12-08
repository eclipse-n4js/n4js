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
import org.eclipse.n4js.generator.ExceptionHandler
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.projectModel.ResourceNameComputer

/**
 */
 @Singleton
public class CompilerHelper {
	@Inject ResourceNameComputer projectUtils

	@Inject extension ExceptionHandler

	/**
	 * Convenience method, delegates to {@link #getTargetFileName(URI , String)} with the URI of the given resource.
	 */
	def String getTargetFileName(Resource n4jsSourceFile, String compiledFileExtension) {
		getTargetFileName(n4jsSourceFile.URI, compiledFileExtension);
	}

	/**
	 * Returns the name of the target file (without path) to which the source is to be compiled to.
	 * Default implementation returns a configured project Name with version + file name + extension.
	 * E.g., "proj/p/A.js" for a file A in proj and a compiledFileExtension of "js".
	 * <p>
	 * The compiledFileExtension should not include the separator dot; it may be <code>null</code>
	 * and then no extension is appended.
	 */
	def String getTargetFileName(URI n4jsSourceURI, String compiledFileExtension) {

		val extStr = if(compiledFileExtension!==null && compiledFileExtension.length>0) "." + compiledFileExtension else "";

		val String targetFilePath = try {
			projectUtils.generateFileDescriptor(n4jsSourceURI, extStr)
		} catch (Throwable t) {

			//TODO a bit generic error handling
			handleError(t.message, t)
			null
		}

		return targetFilePath;
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

		val extStr = if(compiledFileExtension!==null && compiledFileExtension.length>0) "." + compiledFileExtension else "";

		val String targetFilePath = try {
			projectUtils.generateFileDescriptor(project,n4jsSourceURI, extStr)
		} catch (Throwable t) {

			//TODO a bit generic error handling
			handleError(t.message, t)
			null
		}

		return targetFilePath;
	}

	/**
	 * Return workspace relative platform url to a given module.
	 */
	def String getModuleName(String sourcePathName, String fileExt) {
		val URI sourceURI = URI.createPlatformResourceURI(sourcePathName, true);
		val String simpleTargetFileName = getTargetFileName(sourceURI, fileExt);
		val String targetModuleName = simpleTargetFileName.substring(0,
			simpleTargetFileName.length() - fileExt.length() - 1);
		return targetModuleName;
	}

	/** delegates to {@code N4JSresource::getModule} */
	def TModule retrieveModule(N4JSResource res) {
		N4JSResource::getModule(res)
	}
}
