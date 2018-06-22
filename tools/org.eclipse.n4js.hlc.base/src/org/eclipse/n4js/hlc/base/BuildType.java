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
package org.eclipse.n4js.hlc.base;

/**
 * Denoting the build-type.
 */
public enum BuildType {
	/** Single file compile */
	singlefile("Build only single given file"),
	/** Compile given projects */
	projects("Compile given projects"),
	/** Compile all projects in project-locations folder */
	allprojects("Compile all projects in project-locations folder"),
	/** Default case */
	dontcompile("Do not compile");

	BuildType(String description) {
		this.description = description;
	}

	String description = "";
}
