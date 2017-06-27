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
package org.eclipse.n4js.projectModel;

import java.io.File;

/**
 * Enhances the {@link IN4JSCore core service} with runtime specific stuff.
 */
public interface IN4JSRuntimeCore extends IN4JSCore {

	/**
	 * Registers the given directory as an N4JS project.
	 */
	void registerProject(File directory);

}
