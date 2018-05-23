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
package org.eclipse.n4js.ui.organize.imports;

/**
 * Mode how to cope with necessary decisions. Default is queryUser
 */
public enum Interaction {
	/** Ask user in doubt. */
	queryUser,
	/** Always resolve with highest priority solution. */
	takeFirst,
	/** Terminate with {@link BreakException} in ambiguous cases. */
	breakBuild
}
