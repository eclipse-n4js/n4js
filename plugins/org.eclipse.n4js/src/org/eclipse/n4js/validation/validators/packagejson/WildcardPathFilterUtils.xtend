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
package org.eclipse.n4js.validation.validators.packagejson

import org.eclipse.n4js.utils.io.FileMatcher
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.util.List
import java.util.regex.Pattern

/**
 * Utility methods for the resolution of module filter wildcards as used in N4JS {@code package.json} files.
 */
// methods have to be static as this class is used in N4JSModel that is used in the Xtext SharedStateModule
class WildcardPathFilterUtils {
	private static final String MATCHING_FILE_EXT = ".*js*"
	private static final List<String> supportedFileExtension = #[".js", ".n4js", ".n4jsd", ".n4js.xt", ".n4jsd.xt", ".js.xt"]

	def static List<String> collectPathsByWildcardPath(String absoluteProjectPath, String projectRelativeWildcardPath) {
		val absoluteFolderAndFilePaths = collectAllFoldersAndFilesByWildcardPath(absoluteProjectPath, projectRelativeWildcardPath, true)
		if(!absoluteFolderAndFilePaths.value.empty) absoluteFolderAndFilePaths.value else absoluteFolderAndFilePaths.key
	}

	// (only used in tests)
	def static collectAllFilesByWildcardPath(String absoluteProjectPath, String projectRelativeWildcardPath) {
		val absoluteFolderAndFilePaths = collectAllFoldersAndFilesByWildcardPath(absoluteProjectPath, projectRelativeWildcardPath, true)
		val files = absoluteFolderAndFilePaths.key.map(path | matchFiles(path, "*.*").map[path + File.separator + it].toList).flatten.toList
		files += absoluteFolderAndFilePaths.value
		files
	}

	private def static filterByFileExtensions(List<String> paths) {
		paths.filter[supportedFileExtension.exists(fe|endsWith(fe))].toList
	}

	// (only used in tests)
	def static collectAllFoldersByWildcardPath(String absoluteProjectPath, String projectRelativeWildcardPath) {
		val absoluteFolderAndFilePaths = collectAllFoldersAndFilesByWildcardPath(absoluteProjectPath, projectRelativeWildcardPath, false)
		absoluteFolderAndFilePaths.key
	}

	private def static Pair<List<String>, List<String>> collectAllFoldersAndFilesByWildcardPath(String absoluteProjectPath, String matchString, boolean addFileExtensionPattern) {
		if(!(new File(absoluteProjectPath)).exists)
			return #[]->#[]
		
		val newMatchString = matchString.replace("\\", File.separator).replace("/", File.separator).adaptWildcard(addFileExtensionPattern)
		val separator = File.separator + ".."
		val splittedMatchStringParts = newMatchString.split(Pattern.quote(separator)).iterator
		val absoluteFolderPaths = newArrayList(absoluteProjectPath)
		val absoluteFilePaths = <String>newArrayList
		while(splittedMatchStringParts.hasNext) {
			val splittedMatchStringPart = splittedMatchStringParts.next
			val folders = absoluteFolderPaths.map[it -> matchFolders(it, splittedMatchStringPart).toList]
			val newAbsoluteFolderPaths = <String>newArrayList
			for(folderEntry : folders) {
				newAbsoluteFolderPaths += folderEntry.value.map[
					try {
						new File(folderEntry.key + File.separator + it + (
												if(!splittedMatchStringParts.hasNext && newMatchString.endsWith(separator)) separator else ""
											)).canonicalPath
					} catch (IOException exc) {
						throw new RuntimeException("Canonical path cannot be calculated", exc)
					}
				]
			}
			if(!splittedMatchStringParts.hasNext && !newMatchString.endsWith(separator)) {
				val files = absoluteFolderPaths.map[it -> matchFiles(it, splittedMatchStringPart).toList]
				for(fileEntry : files) {
					val filesPaths = fileEntry.value.map[try {
						// IDEBUG-369: removed use of File#getCanonicalPath() in following line because it breaks if
						// the path to the containing N4JS project contains a symbolic link (i.e. the canonical path
						// will be different and cannot be matched anymore to paths for which #getCanonicalPath() has
						// not been called; either #getCanonicalPath() has to be consistently used by IN4JSCore and
						// IN4JSProject (and maybe other code) or we have to avoid it entirely)
						// (note: this is just a quick fix; created follow-up task IDEBUG-377 for this)
						//new File(fileEntry.key + File.separator + it).canonicalPath
						fileEntry.key + File.separator + it
					} catch (IOException exc) {
						throw new RuntimeException("Canonical path cannot be calculated", exc)
					}]
					absoluteFilePaths += filesPaths
				}
			}
			absoluteFolderPaths.clear
			absoluteFolderPaths += newAbsoluteFolderPaths
		}
		val Pair<List<String>, List<String>> result = (absoluteFolderPaths -> absoluteFilePaths.filterByFileExtensions)
		return result
	}

	private def static adaptWildcard(String projectRelativeWildcardPath, boolean addFileExtensionPattern) {
		if(!projectRelativeWildcardPath.endsWith("**") && addFileExtensionPattern) projectRelativeWildcardPath + MATCHING_FILE_EXT else projectRelativeWildcardPath
	}

	private def static matchFolders(String absoluteProjectPath, String matchString) {
		FileMatcher.scanDirectories(Paths.get(absoluteProjectPath), matchString).map[toString];
	}

	private def static matchFiles(String absoluteProjectPath, String matchString) {
		FileMatcher.scanFiles(Paths.get(absoluteProjectPath), matchString).map[toString];
	}

}
