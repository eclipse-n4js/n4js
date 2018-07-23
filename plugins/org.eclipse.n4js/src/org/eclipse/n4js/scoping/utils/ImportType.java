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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.n4js.projectDescription.ProjectDescription;

/**
 * Internal Distinction of different import types.
 *
 * Although in the AST we don't make distinction, internally we need to handle different types of import.
 */
public enum ImportType {
	/** import specifies only target project name, we expect to import from main module */
	PROJECT_IMPORT,
	/** import specifies target project name and concrete module, we expect to import from that particular module */
	COMPLETE_IMPORT,
	/** import specifies no target project name, we expect to import some module */
	SIMPLE_IMPORT,
	/**
	 * Error case : it looked like {@link ImportType#PROJECT_IMPORT} but target project has no
	 * {@link ProjectDescription#getMainModule()}
	 */
	PROJECT_IMPORT_NO_MAIN;
}
