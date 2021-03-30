/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.packagejson.projectDescription;

/**
 * Types of dependencies between projects.
 */
public enum DependencyType {
	/** Dependencies of this type must always be present. */
	RUNTIME,
	/** Dependencies of this type must be present at development-time. */
	DEVELOPMENT,
	/** Dependencies of this type are added by n4jsc during project discovery. */
	IMPLICIT
}
