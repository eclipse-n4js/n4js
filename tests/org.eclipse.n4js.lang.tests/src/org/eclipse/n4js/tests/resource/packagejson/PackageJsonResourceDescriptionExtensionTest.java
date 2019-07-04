/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.resource.packagejson;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.resource.packagejson.PackageJsonResourceDescriptionExtension;
import org.eclipse.n4js.tests.projectModel.AbstractProjectModelSetup;
import org.eclipse.n4js.tests.projectModel.AbstractProjectModelTest;
import org.eclipse.n4js.tests.projectModel.FileBasedProjectModelSetup;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * A test class.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class PackageJsonResourceDescriptionExtensionTest extends AbstractProjectModelTest {

	@Inject
	private PackageJsonResourceDescriptionExtension packageJsonExtension;
	@Inject
	private FileBasedWorkspace workspace;

	/**
	 * Clear the workspace after testing.
	 */
	@Override
	@After
	public void tearDown() {
		workspace.clear();
	}

	/** A test */
	@Test
	public void testGetDepthOfLocation() {
		// new PackageJsonResourceDescriptionExtension()
		String[] emptySegments = { "", "", "" };
		URI uri0 = myProjectURI;
		URI uri1 = myProjectURI.appendSegment("someFile.txt");
		URI uri2 = myProjectURI.appendSegment("someFolder").appendSegment("someFile.txt");
		URI uriBad = myProjectURI.trimSegments(1).appendSegment("DoesNotExist").appendSegment("someFile.txt");
		assertEquals(0, packageJsonExtension.getDepthOfLocation(uri0));
		assertEquals(0, packageJsonExtension.getDepthOfLocation(uri0.appendSegments(emptySegments)));
		assertEquals(1, packageJsonExtension.getDepthOfLocation(uri1));
		assertEquals(1, packageJsonExtension.getDepthOfLocation(uri1.appendSegments(emptySegments)));
		assertEquals(2, packageJsonExtension.getDepthOfLocation(uri2));
		assertEquals(2, packageJsonExtension.getDepthOfLocation(uri2.appendSegments(emptySegments)));
		assertEquals(-1, packageJsonExtension.getDepthOfLocation(uriBad));
		assertEquals(-1, packageJsonExtension.getDepthOfLocation(uriBad.appendSegments(emptySegments)));
	}

	@Override
	protected AbstractProjectModelSetup createSetup() {
		return new FileBasedProjectModelSetup(this, workspace);
	}

	@Override
	protected String[] getExpectedIssuesInInitialSetup(String projectName) {
		return new String[0];
	}
}
