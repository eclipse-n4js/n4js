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

import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.n4mf.ProjectDescription
import java.util.regex.Pattern
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.junit.Before

/**
 */
public abstract class AbstractStaticPolyfillBuilderTest extends AbstractBuilderParticipantTest {


	protected IProject projectUnderTest
	protected IFolder src
	protected IFolder src2
	protected IFile manifest

	@Before
	override void setUp() {
		super.setUp
		projectUnderTest = createJSProject("singleProjectTest")
		src = configureProjectWithXtext(projectUnderTest)
		manifest = projectUnderTest.project.getFile("manifest.n4mf")
		src2 = projectUnderTest.project.getFolder("src2");
		src2.create(false, true, null)
		waitForAutoBuild
	}

	def void addSrc2ToSources() {
		addSrc2ToSources("src2")
	}

	def void addSrc2ToSources(String folderName) {
		val uri = URI.createPlatformResourceURI(manifest.fullPath.toString, true);
		val rs = getResourceSet(projectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val ProjectDescription pd = resource.contents.head as ProjectDescription
		pd.sourceFragment.head.pathsRaw.add(folderName)
		resource.save(null)
		waitForAutoBuild();
	}

	def void removeSrc2FromSource() {
		val uri = URI.createPlatformResourceURI(manifest.fullPath.toString, true);
		val rs = getResourceSet(projectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val ProjectDescription pd = resource.contents.head as ProjectDescription
		pd.sourceFragment.head.pathsRaw.remove(1)
		resource.save(null)
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
