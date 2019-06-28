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
package org.eclipse.n4js.tests.staticpolyfill

import java.util.regex.Pattern
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.PackageJSONTestUtils
import org.junit.Before
import org.eclipse.xtext.resource.SaveOptions

/**
 */
public abstract class AbstractStaticPolyfillBuilderTest extends AbstractBuilderParticipantTest {

	protected IProject projectUnderTest
	protected IFolder src
	protected IFolder src2
	protected IFile projectDescriptionFile

	@Before
	def void setUp2() {
		projectUnderTest = createJSProject("singleProjectTest")
		src = configureProjectWithXtext(projectUnderTest)
		projectDescriptionFile = projectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON)
		src2 = projectUnderTest.project.getFolder("src2");
		src2.create(false, true, null)
		waitForAutoBuild
	}

	/** 
	 * Updates project description file to declare another source container 'src2' 
	 * in addition to the existing container 'src'.
	 */
	def void addSrc2ToSources() {
		val uri = URI.createPlatformResourceURI(projectDescriptionFile.fullPath.toString, true);
		val rs = getResourceSet(projectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val JSONDocument projectDescDocument = resource.contents.head as JSONDocument
		val packageJSONRoot = projectDescDocument.content as JSONObject;

		PackageJSONTestUtils.setSourceContainerSpecifiers(
			packageJSONRoot,
			SourceContainerType.SOURCE,
			#["src", "src2"]
		)
		// save formatted modified package.json
		resource.save(SaveOptions.newBuilder.format.options.toOptionsMap);
		waitForAutoBuild();
	}

	static def int matchCount(Pattern pattern, CharSequence s) {
		val matcher = pattern.matcher(s)
		var matchCount = 0;
		while (matcher.find) {
			matchCount++;
		}
		return matchCount
	}

}
