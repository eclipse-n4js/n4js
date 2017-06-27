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
package org.eclipse.n4js.npmexporter;

import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 * Static helpers.
 */
public class NpmExporterUtil {

	/**
	 * Converts a {@link DeclaredVersion} from an {@link IN4JSProject} into the form "0.0.0" - leaves out the
	 * {@link DeclaredVersion#getQualifier()}.
	 */
	public static final String versionAsSemverString(IN4JSProject project) {
		final char sep = '.';
		final char hyphon = '-';
		final DeclaredVersion ver = project.getVersion();
		final String verStr = "" + ver.getMajor() + sep + ver.getMinor() + sep + ver.getMicro();
		if (ver.getQualifier() != null)
			return verStr + hyphon + ver.getQualifier();
		return verStr;
	}

	/** Access to the qualifier of the project's version */
	public static final String versionQualifier(IN4JSProject project) {
		DeclaredVersion ver = project.getVersion();
		return ver.getQualifier();
	}

}
