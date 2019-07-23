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
package org.eclipse.n4js.tests.projectModel;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.UriExtensions;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class FileBasedInternalWorkspaceTest extends AbstractInternalWorkspaceTest<FileURI> {

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	private FileBasedWorkspace testMe;

	@Override
	protected AbstractProjectModelSetup<FileURI> createSetup() {
		return new FileBasedProjectModelSetup(this, testMe);
	}

	@Override
	protected String[] getExpectedIssuesInInitialSetup(N4JSProjectName projectName) {
		return new String[0];
	}

	@Override
	public void setUp() {
		testMe = new FileBasedWorkspace(projectDescriptionLoader, new UriExtensions());
		super.setUp();
	}

	@Override
	public void tearDown() {
		super.tearDown();
		testMe = null;
	}

	@Override
	protected InternalN4JSWorkspace<FileURI> getWorkspace() {
		return testMe;
	}

}
