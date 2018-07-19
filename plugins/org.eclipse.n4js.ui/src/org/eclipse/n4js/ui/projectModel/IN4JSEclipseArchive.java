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
package org.eclipse.n4js.ui.projectModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.n4js.projectModel.IN4JSArchive;

/**
 */
@SuppressWarnings("javadoc")
public interface IN4JSEclipseArchive extends IN4JSArchive {

	IFile getArchiveFile(); // maybe IStorage if the archive is provided by a library manager?

	@Override
	IN4JSEclipseProject getProject();

	boolean exists();

}
