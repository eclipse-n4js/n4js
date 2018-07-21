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
import org.eclipse.n4js.internal.ClasspathPackageManager;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N4JSRuntimeCoreTest extends AbstractN4JSCoreTest {

	@Inject
	private ClasspathPackageManager classpathPackageManager;

	@Inject
	private ProjectDescriptionHelper projectDescriptionHelper;

	@Inject
	private Injector injector;

	private FileBasedWorkspace workspace;

	private N4JSRuntimeCore testMe;

	@Override
	protected AbstractProjectModelSetup createSetup() {
		return new FileBasedProjectModelSetup(this, workspace);
	}

	@Override
	public void setUp() {
		workspace = new FileBasedWorkspace(classpathPackageManager, projectDescriptionHelper);
		N4JSModel model = new N4JSModel(workspace);
		injector.injectMembers(model);
		testMe = new N4JSRuntimeCore(workspace, model);
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
