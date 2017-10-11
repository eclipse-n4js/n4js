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
package org.eclipse.n4js.ui.building;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;
import org.eclipse.xtext.generator.OutputConfiguration;

/**
 * A file system access implementation that completely disables trace file support to avoid all the unnecessary overhead
 * related to refresh operations on the Eclipse resource tree.
 */
public class FileSystemAccessWithoutTraceFileSupport extends EclipseResourceFileSystemAccess2 {

	@Override
	protected IFile getTraceFile(IFile file) {
		return null;
	}

	@Override
	protected IFile getFile(String fileName, String outputName, IProgressMonitor progressMonitor) {
		OutputConfiguration configuration = getOutputConfig(outputName);
		IContainer container = getContainer(configuration);
		if (container != null) {
			// no need to refresh again - it was already done in
			// org.eclipse.n4js.ui.building.N4JSBuilderParticipant#refreshOutputFolders
			// not a life changer, but shaves off approx 5% from the build time and progress monitor flickering is
			// reduced
			return container.getFile(new Path(fileName));
		}
		return null;
	}

}
