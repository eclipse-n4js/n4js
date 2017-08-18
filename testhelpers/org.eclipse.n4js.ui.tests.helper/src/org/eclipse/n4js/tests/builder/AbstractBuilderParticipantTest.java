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
package org.eclipse.n4js.tests.builder;

import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.monitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.tests.util.ProjectUtils;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.IDirtyStateManager;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.inject.Injector;

/**
 */
public abstract class AbstractBuilderParticipantTest extends AbstractBuilderTest {
	private ResourceSet resourceSet = null;

	/***/
	protected Injector getInjector() {
		final Injector injector = N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
		return injector;
	}

	/***/
	protected IProject createJSProject(String projectName) throws CoreException {
		return ProjectUtils.createJSProject(projectName);
	}

	/***/
	protected IProject createJSProject(String projectName, String sourceFolder, String outputFolder,
			Consumer<ProjectDescription> manifestAdjustments) throws CoreException {
		return ProjectUtils.createJSProject(projectName, sourceFolder, outputFolder, manifestAdjustments);
	}

	/**
	 * Creates a new N4JS project with the given name and project type. The source and output folders will be named as
	 * {@code src} and {@code src-gen}. The Xtext project nature will be already configured on the N4JS project. Blocks
	 * until the auto-build job is terminated.
	 *
	 * @param projectName
	 *            the name of the project.
	 * @param type
	 *            the desired project type of the new project.
	 * @return the new N4JS project with the desired type.
	 * @throws CoreException
	 *             if the project creation failed.
	 */
	protected IProject createN4JSProject(String projectName, ProjectType type) throws CoreException {
		final IProject project = createJSProject(projectName, "src", "src-gen", t -> t.setProjectType(type));
		configureProjectWithXtext(project);
		waitForAutoBuild();
		return project;
	}

	/***/
	protected void createManifestN4MFFile(IProject javaProject) throws CoreException {
		ProjectUtils.createManifestN4MFFile(javaProject.getProject());
	}

	/***/
	protected void createManifestN4MFFile(IProject javaProject, String sourceFolder, String outputFolder,
			Consumer<ProjectDescription> manifestAdjustments) throws CoreException {
		ProjectUtils.createManifestN4MFFile(javaProject.getProject(), sourceFolder, outputFolder, manifestAdjustments);
	}

	/***/
	protected ResourceSet getResourceSet(IProject project) {
		if (resourceSet == null) {
			resourceSet = getInjector().getInstance(IResourceSetProvider.class).get(project);
		}
		return resourceSet;
	}

	/***/
	protected XtextEditor openAndGetXtextEditor(final IFile file1, final IWorkbenchPage page) {
		IEditorPart fileEditor = getFileEditor(file1, page);
		assertTrue(fileEditor instanceof XtextEditor);
		XtextEditor fileXtextEditor = (XtextEditor) fileEditor;
		return fileXtextEditor;
	}

	private IEditorPart internalFileEditor = null;

	private IEditorPart getFileEditor(final IFile file1, final IWorkbenchPage page) {
		internalFileEditor = null;
		Display.getCurrent().syncExec(new Runnable() {

			@Override
			public void run() {
				try {
					internalFileEditor = IDE.openEditor(page, file1, getEditorId(), true);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
		long start = System.currentTimeMillis();
		long end = start;
		do {
			end = System.currentTimeMillis();
		} while (page.getActiveEditor() != internalFileEditor && (end - start) < 5000);
		return internalFileEditor;
	}

	/**
	 * @return the source folder of the project
	 */
	protected IFolder configureProjectWithXtext(final IProject project) throws CoreException {
		return ProjectUtils.configureProjectWithXtext(project, "src");
	}

	/***/
	protected IFolder configureProjectWithXtext(final IProject project, String sourceFolder) throws CoreException {
		return ProjectUtils.configureProjectWithXtext(project, sourceFolder);
	}

	/**
	 * Creates a new file in the given folder. {@link #F_EXT} is appended to the name.
	 */
	protected IFile createTestFile(IFolder folder, String name, CharSequence content) throws CoreException {
		String fullName = name + F_EXT;
		return doCreateTestFile(folder, fullName, content);
	}

	/***/
	@SuppressWarnings("resource")
	protected IFile doCreateTestFile(IFolder folder, String fullName, CharSequence content) throws CoreException {
		IFile file = folder.getFile(fullName);
		file.create(new StringInputStream(content.toString()), true, monitor());
		waitForAutoBuild();
		return file;
	}

	/**
	 * Changes content of an existing file to the given {@link CharSequence}.
	 */
	@SuppressWarnings("resource")
	protected IFile changeTestFile(IFile file, CharSequence newContent) throws CoreException {
		assertTrue("test file should exist", file.exists());
		file.setContents(new StringInputStream(newContent.toString()), true, true, monitor());
		return file;
	}

	/***/
	protected IFolder createFolder(IFolder superFolder, String path) throws CoreException {
		IFolder folder = superFolder.getFolder(path);
		if (!folder.exists()) {
			createParentFolder(folder);
			folder.create(true, true, null);
		}
		return folder;
	}

	/***/
	protected IFolder createFolder(IProject project, String path) throws CoreException {
		IFolder folder = project.getFolder(path);
		if (!folder.exists()) {
			createParentFolder(folder);
			folder.create(true, true, null);
		}
		return folder;
	}

	/***/
	protected void createParentFolder(IFolder folder) throws CoreException {
		IContainer parent = folder.getParent();
		if (parent instanceof IFolder) {
			IFolder parentFolder = (IFolder) parent;
			if (!parentFolder.exists()) {
				createParentFolder(parentFolder);
				parentFolder.create(true, true, null);
			}
		}
	}

	/***/
	protected void replaceFileContentAndWaitForRefresh(IFolder folder, IFile file, String newContent)
			throws IOException, CoreException {
		File fileInFilesystem = file.getLocation().toFile();
		FileWriter fileWriter = new FileWriter(fileInFilesystem);
		fileWriter.write(newContent);
		fileWriter.close();
		folder.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IProject project, int count) throws CoreException {
		return ProjectUtils.assertMarkers(assertMessage, project, count);
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IResource resource, int count) throws CoreException {
		return ProjectUtils.assertMarkers(assertMessage, resource, count);
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IResource resource, int count,
			final Predicate<IMarker> markerPredicate) throws CoreException {

		return ProjectUtils.assertMarkers(assertMessage, resource, count, markerPredicate);
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IProject project, String markerType, int count)
			throws CoreException {
		return ProjectUtils.assertMarkers(assertMessage, project, markerType, count);
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IResource resource, String markerType, int count)
			throws CoreException {
		return ProjectUtils.assertMarkers(assertMessage, resource, markerType, count);
	}

	/***/
	protected void assertIssues(final IResource resource, String... expectedMessages) throws CoreException {
		ProjectUtils.assertIssues(resource, expectedMessages);
	}

	/**
	 * Will only return parse errors, not validation errors!
	 */
	protected List<Resource.Diagnostic> getEditorErrors(XtextEditor fileXtextEditor) {
		return fileXtextEditor.getDocument().readOnly(new IUnitOfWork<List<Diagnostic>, XtextResource>() {

			@Override
			public List<Resource.Diagnostic> exec(XtextResource state) throws Exception {
				EcoreUtil.resolveAll(state);
				return state.getErrors();
			}
		});
	}

	/**
	 * Returns validation errors in given Xtext editor.
	 */
	protected List<Issue> getEditorValidationErrors(XtextEditor editor) {
		return editor.getDocument().readOnly(new IUnitOfWork<List<Issue>, XtextResource>() {
			@Override
			public List<Issue> exec(XtextResource state) throws Exception {
				final IResourceValidator validator = state.getResourceServiceProvider().getResourceValidator();
				return validator.validate(state, CheckMode.ALL, CancelIndicator.NullImpl);
			}
		});
	}

	/***/
	protected void setDocumentContent(String context, IFile file, XtextEditor fileEditor, String newContent) {
		IDirtyStateManager dirtyStateManager = getInjector().getInstance(IDirtyStateManager.class);

		TestEventListener eventListener = new TestEventListener(context, file);
		dirtyStateManager.addListener(eventListener);

		setDocumentContent(fileEditor, newContent);

		eventListener.waitForFiredEvent();
		dirtyStateManager.removeListener(eventListener);
		waitForUpdateEditorJob();
	}

	/***/
	protected void setDocumentContent(final XtextEditor xtextEditor, final String content) {
		Display.getCurrent().syncExec(new Runnable() {

			@Override
			public void run() {
				xtextEditor.getDocument().set(content);
			}
		});
	}

	/***/
	protected static void waitForUpdateEditorJob() {
		ProjectUtils.waitForUpdateEditorJob();
	}

	/***/
	protected String getEditorId() {
		return N4JSActivator.ORG_ECLIPSE_N4JS_N4JS;
	}

	/** Returns with the absolute URI of the resource loaded from the current plug-in. */
	protected URI getResourceUri(final String segment, final String... restSegments) {
		final String resourceName = getResourceName(segment, restSegments);
		final URL url = this.getClass().getClassLoader().getResource(resourceName);
		try {
			return new File(FileLocator.resolve(url).toURI()).getCanonicalFile().toURI();
		} catch (final URISyntaxException | IOException e) {
			throw new RuntimeException("Error while trying to locate resource at: " + resourceName + ".", e);
		}
	}

	/** Returns with the resource name by simply joining them together like {@link IPath#append(String)} does. */
	protected String getResourceName(final String segment, final String... restSegments) {
		return Joiner.on("/").join(Lists.asList(segment, restSegments));
	}
}
