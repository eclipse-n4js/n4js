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
package org.eclipse.n4js.workspace;

import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;

/**
 * N4JS specific extension to {@link ISourceFolder} to ensure that the containing project is a {@link IProjectConfig}.
 */
@SuppressWarnings("restriction")
public interface IN4JSSourceFolder extends ISourceFolder {

	/** @return the parent project */
	IProjectConfig getProject();

	/** @return the {@link SourceContainerType}. */
	SourceContainerType getType();

	/** @return the relative path of this source folder. */
	String getRelativePath();
}
