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

import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.internal.ClasspathPackageManager;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;

/**
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class FileBasedInternalWorkspaceTest extends AbstractInternalWorkspaceTest {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private ClasspathPackageManager classpathPackageManager;

	private FileBasedWorkspace testMe;

	@Override
	protected AbstractProjectModelSetup createSetup() {
		return new FileBasedProjectModelSetup(this, testMe);
	}

	@Override
	public void setUp() {
		testMe = new FileBasedWorkspace(resourceSetProvider, classpathPackageManager);
		super.setUp();
	}

	@Override
	public void tearDown() {
		super.tearDown();
		testMe = null;
	}

	@Override
	protected InternalN4JSWorkspace getWorkspace() {
		return testMe;
	}

}
