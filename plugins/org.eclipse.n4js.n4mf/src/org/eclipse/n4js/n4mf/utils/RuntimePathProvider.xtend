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
package org.eclipse.n4js.n4mf.utils

import java.io.File
import org.eclipse.core.resources.IWorkspaceRoot
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Platform
import org.eclipse.emf.common.CommonPlugin
import org.eclipse.emf.common.util.URI

/**
 */
class RuntimePathProvider implements IPathProvider {

	@Override
	override getAbsoluteProjectPath(URI manifestURI) {
        val root = getWorkspaceRoot
        if (null !== root) {
        	if (manifestURI.isPlatform) {
				val file = root.getFile(new Path(manifestURI.toPlatformString(true)))
				if (null !== file && file.exists) {
					return file.project.location.toFile.absolutePath;
				}
        	} else {
        		new File(manifestURI.toFileString).parentFile.absolutePath;
        	}
        } else {
			return CommonPlugin.asLocalURI(manifestURI).trimSegments(1).toFileString;
		}
	}

	private def IWorkspaceRoot getWorkspaceRoot() {
		if (Platform.isRunning()) {
	        val workspace = ResourcesPlugin.getWorkspace();
	        if (null !== workspace) {
	        	return workspace.root;
	        }
		}
		return null;
    }
}
