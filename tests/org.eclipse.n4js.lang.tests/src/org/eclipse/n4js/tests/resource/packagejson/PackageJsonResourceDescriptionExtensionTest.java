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

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
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
public class PackageJsonResourceDescriptionExtensionTest extends AbstractProjectModelTest<FileURI> {

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
		SafeURI<?> uri0 = myProjectURI;
		SafeURI<?> uri1 = myProjectURI.appendSegment("someFile.txt");
		SafeURI<?> uri2 = myProjectURI.appendSegment("someFolder").appendSegment("someFile.txt");
		SafeURI<?> uriBad = myProjectURI.getParent().appendSegment("DoesNotExist").appendSegment("someFile.txt");
		assertEquals(0, packageJsonExtension.getDepthOfLocation(uri0.toURI()));
		assertEquals(0, packageJsonExtension.getDepthOfLocation(uri0.toURI().appendSegments(emptySegments)));
		assertEquals(1, packageJsonExtension.getDepthOfLocation(uri1.toURI()));
		assertEquals(1, packageJsonExtension.getDepthOfLocation(uri1.toURI().appendSegments(emptySegments)));
		assertEquals(2, packageJsonExtension.getDepthOfLocation(uri2.toURI()));
		assertEquals(2, packageJsonExtension.getDepthOfLocation(uri2.toURI().appendSegments(emptySegments)));
		assertEquals(-1, packageJsonExtension.getDepthOfLocation(uriBad.toURI()));
		assertEquals(-1, packageJsonExtension.getDepthOfLocation(uriBad.toURI().appendSegments(emptySegments)));
	}

	@Override
	protected AbstractProjectModelSetup<FileURI> createSetup() {
		return new FileBasedProjectModelSetup(this, workspace);
	}

	@Override
	protected String[] getExpectedIssuesInInitialSetup(N4JSProjectName projectName) {
		return new String[0];
	}
}
