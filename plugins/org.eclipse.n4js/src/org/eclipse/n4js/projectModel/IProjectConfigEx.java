/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.projectModel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IProjectConfig;

/**
 *
 */
public interface IProjectConfigEx extends IProjectConfig {

	@Override
	Set<? extends ISourceFolderEx> getSourceFolders();

	@Override
	default ISourceFolderEx findSourceFolderContaining(URI member) {
		for (ISourceFolderEx srcFolder : getSourceFolders()) {
			if (srcFolder.contains(member)) {
				return srcFolder;
			}
		}
		return null;
	}

	URI getOutputFolder();

	default boolean isInSourceFolder(URI member) {
		return findSourceFolderContaining(member) != null;
	}

	default boolean isInOutputFolder(URI member) {
		Path memberPath = Paths.get(member.path());
		Path outputPath = Paths.get(getOutputFolder().path());

		return memberPath.startsWith(outputPath);
	}

}
