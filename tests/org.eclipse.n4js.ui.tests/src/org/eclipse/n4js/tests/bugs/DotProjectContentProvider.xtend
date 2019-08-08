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
package org.eclipse.n4js.tests.bugs

import org.eclipse.n4js.projectModel.names.N4JSProjectName

/**
 * Utility class for providing {@code .project} file content for those projects that have
 * not got such file but are intended to import for test.
 */
abstract class DotProjectContentProvider {

	/**
	 * Returns with the content of the {@code .project} file for a project in case of its absence.
	 *
	 * @param projectName
	 * 			The name of the project to get the {@code .project} file content.
	 *
	 * @return the {@code .project} file content for a project as a string.
	 */
	static def getDotProjectContentForProject(N4JSProjectName projectName)'''
	<?xml version="1.0" encoding="UTF-8"?>
	<projectDescription>
		<name>«projectName.toEclipseProjectName.rawName»</name>
		<comment></comment>
		<projects>
		</projects>
		<buildSpec>
			<buildCommand>
				<name>org.eclipse.xtext.ui.shared.xtextBuilder</name>
				<arguments>
				</arguments>
			</buildCommand>
		</buildSpec>
		<natures>
			<nature>org.eclipse.xtext.ui.shared.xtextNature</nature>
		</natures>
	</projectDescription>
	'''

}
