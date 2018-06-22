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
package org.eclipse.n4js.utils.ui.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;

/**
 * This class solves the problem that a file is refreshed when opened. GH-270.
 */
public class AvoidRefreshDocumentProvider extends XtextDocumentProvider {

	@Override
	protected void refreshFile(IFile file, IProgressMonitor monitor) throws CoreException {
		// Note that file.isSynchronized does not require a scheduling rule and thus helps to identify a no-op attempt
		// to refresh the file. The no-op will otherwise be blocked by a running build or cancel a running build
		if (!file.isSynchronized(IResource.DEPTH_ZERO)) {
			super.refreshFile(file, monitor);
		}
	}
}
