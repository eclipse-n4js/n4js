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
package org.eclipse.n4js.tests.contentAssist;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.addNature;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.monitor;
import static org.eclipse.xtext.ui.testing.util.JavaProjectSetupUtil.deleteProject;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.testing.ContentAssistProcessorTestBuilder;
import org.eclipse.xtext.ui.testing.util.ResourceLoadHelper;
import org.eclipse.xtext.util.StringInputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.inject.Injector;

/**
 * Abstract base class for CA tests. Uses a shared project for the entire test class.
 */
public abstract class AbstractN4JSContentAssistPluginUITest extends Assert implements ResourceLoadHelper {

	private static final String PROJECT_NAME = "ContentAssistTestProject";

	private IProject demandCreateProject;

	private static IProject staticProject;

	/**
	 * @since 2.3
	 */
	@Before
	public void setUp() throws Exception {
		getInjector().injectMembers(this);
	}

	/**
	 * @since 2.3
	 */
	@After
	public void tearDown() throws Exception {
		if (demandCreateProject != null)
			deleteProject(demandCreateProject);
	}

	private <T> T get(Class<T> clazz) {
		return getInjector().getInstance(clazz);
	}

	private XtextResourceSet getResourceSet() {
		IResourceSetProvider resourceSetProvider = get(IResourceSetProvider.class);
		ResourceSet result = resourceSetProvider.get(getJSProject());
		return (XtextResourceSet) result;
	}

	@Override
	public final XtextResource getResourceFor(InputStream stream) {
		try {
			XtextResource result = (XtextResource) getResourceSet().createResource(
					URI.createURI("platform:/resource/" + PROJECT_NAME + "/src/path/Test.n4js"));
			result.load(stream, null);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a new tester for ContentAssist.
	 */
	protected final ContentAssistProcessorTestBuilder newBuilder() throws Exception {
		ContentAssistProcessorTestBuilder builder = new ContentAssistProcessorTestBuilder(getInjector(), this);
		return builder;
	}

	/**
	 * Creates a project with two files.
	 */
	@BeforeClass
	public static void createTestProject() throws Exception {
		staticProject = ProjectTestsUtils.createJSProject(PROJECT_NAME);
		IFolder path = staticProject.getFolder("src").getFolder("path");
		path.create(true, true, null);
		IFile libFile = path.getFile("Libs.n4js");
		libFile.create(new StringInputStream(
				"export public class MyFirstClass {} export public class MySecondClass {} class MyHiddenClass {}",
				libFile.getCharset()), true, monitor());
		IFile moreLibFile = path.getFile("MoreLibs.n4js");
		moreLibFile.create(new StringInputStream(
				"export public class MoreLibFirstClass {} export public class MoreLibSecondClass {}",
				moreLibFile.getCharset()), true, monitor());
		IFile testFile = path.getFile("Test.n4js");
		testFile.create(new StringInputStream("", testFile.getCharset()), true, monitor());
		addNature(staticProject, XtextProjectHelper.NATURE_ID);
		ProjectTestsUtils.waitForAutoBuild();
	}

	/***/
	@AfterClass
	public static void deleteTestProject() throws Exception {
		deleteProject(staticProject);
	}

	private Injector getInjector() {
		return N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
	}

	private IProject getJSProject() {
		IProject javaProject = findJSProject(PROJECT_NAME);
		if (javaProject == null || !javaProject.exists()) {
			try {
				demandCreateProject = ProjectTestsUtils.createJSProject(PROJECT_NAME);
				javaProject = findJSProject(PROJECT_NAME);
			} catch (CoreException e) {
				fail("Cannot create project due to: " + e.getMessage() + " / " + e);
			}
		}
		return javaProject;
	}

	/** Delegates into {@link IWorkspaceRoot#getProject(String)}. */
	private IProject findJSProject(final String projectName) {
		checkNotNull(projectName, "projectName");
		checkState(Platform.isRunning(), "Expected running platform for plug-in UI test.");
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}

}
