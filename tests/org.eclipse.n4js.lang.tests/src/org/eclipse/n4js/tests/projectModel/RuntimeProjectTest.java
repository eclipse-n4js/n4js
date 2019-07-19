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
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class RuntimeProjectTest extends AbstractN4JSProjectTest<FileURI> {

	@Inject
	private Injector injector;

	private FileBasedWorkspace workspace;

	private N4JSRuntimeCore testMe;

	@Override
	protected AbstractProjectModelSetup<FileURI> createSetup() {
		return new FileBasedProjectModelSetup(this, workspace);
	}

	@Override
	protected String[] getExpectedIssuesInInitialSetup(N4JSProjectName projectName) {
		return new String[0];
	}

	@Override
	public void setUp() {
		workspace = injector.getInstance(FileBasedWorkspace.class);
		testMe = injector.getInstance(N4JSRuntimeCore.class);
		super.setUp();
	}

	@Override
	protected IN4JSCore getN4JSCore() {
		return testMe;
	}

	@Override
	public void tearDown() {
		super.tearDown();
		workspace = null;
		testMe = null;
	}

}
