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

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.n4js.ui.internal.N4JSEclipseCore;
import org.eclipse.n4js.ui.internal.N4JSEclipseModel;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
public class N4JSEclipseCorePluginTest extends AbstractN4JSCoreTest {

	@Inject
	private IWorkspaceRoot workspace;

	@Inject
	private ProjectDescriptionHelper projectDescriptionHelper;

	@Inject
	private Injector injector;

	private EclipseBasedN4JSWorkspace internalWorkspace;

	private N4JSEclipseCore testMe;

	@Override
	protected AbstractProjectModelSetup createSetup() {
		return new EclipseBasedProjectModelSetup(this, workspace);
	}

	@Override
	public void setUp() {
		internalWorkspace = new EclipseBasedN4JSWorkspace(workspace, projectDescriptionHelper);
		N4JSEclipseModel model = new N4JSEclipseModel(internalWorkspace);
		injector.injectMembers(model);
		testMe = new N4JSEclipseCore(model);
		super.setUp();
	}

	@Override
	public void tearDown() {
		super.tearDown();
		testMe = null;
	}

	@Override
	protected IN4JSCore getN4JSCore() {
		return testMe;
	}

}
