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
package org.eclipse.n4js.dirtystate

import org.eclipse.core.resources.IFile
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants

/**
 * Base utility for 
 */
class AbstractResourceLoadingTest extends AbstractBuilderParticipantTest {

	def protected IFile getOutputFileForTestFile(IFile file) {
		val name = file.name;
		val idx = name.lastIndexOf('.');
		val baseName = if(idx>=0) name.substring(0, idx) else name;
		val project = file.project;
		return project.getFile(
			"src-gen/"
			+ N4JSLanguageConstants.TRANSPILER_SUBFOLDER_FOR_TESTS
			+ "/"
			+ project.name
			+ "/m/"
			+ baseName + ".js"
		);
	}	
}