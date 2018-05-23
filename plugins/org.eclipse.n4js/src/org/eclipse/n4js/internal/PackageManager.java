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
package org.eclipse.n4js.internal;

import org.eclipse.emf.common.util.URI;

/**
 * Simple abstraction that allows find libraries somewhere by name. 'Somewhere' is explicitly vague since a concrete
 * impl may use a nexus repository, hard coded paths on disk or bundle resources.
 */
public interface PackageManager {

	/**
	 * Returns the location where the given project can be found now or in the future. This call may trigger async
	 * actions, e.g. downloads etc.
	 *
	 * @param projectId
	 *            the name of the project to be located
	 * @return the location or null.
	 */
	URI getLocation(String projectId);

}
