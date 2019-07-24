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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.packagejson.PackageJsonBuilder;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.util.EclipseUIUtils;
import org.eclipse.n4js.tests.util.PackageJSONTestHelper;
import org.eclipse.n4js.tests.util.ProjectTestsHelper;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.utils.process.ProcessResult;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.IDirtyStateManager;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.util.IssueUtil;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 */
public abstract class AbstractBuilderParticipantTest extends AbstractBuilderTest {
	@Inject
	private Provider<IDirtyStateManager> dirtyStateManager;

	@Inject
	private ProjectTestsHelper projectTestsHelper;

	@Inject
	private IssueUtil issueUtil;

	/** Helper to create and modify package.json project description files */
	@Inject
	protected PackageJSONTestHelper projectDescriptionTestHelper;

	Predicate<IMarker> ignoreSomeWarnings = (IMarker marker) -> {
		String code = issueUtil.getCode(marker);
		if (code != null) {
			switch (code) {
			case IssueCodes.CFG_LOCAL_VAR_UNUSED:
			case IssueCodes.DFG_NULL_DEREFERENCE:
				return false;
			}
		}
		return true;
	};

	/***/
	protected IProject createJSProject(String projectName) throws CoreException {
		return ProjectTestsUtils.createJSProject(projectName);
	}

	/***/
	protected IProject createJSProject(String projectName, String sourceFolder, String outputFolder,
			Consumer<PackageJsonBuilder> manifestAdjustments) throws CoreException {
		return ProjectTestsUtils.createJSProject(projectName, sourceFolder, outputFolder, manifestAdjustments);
	}

	/**
	 * Creates a new N4JS project with the given name and project type. The source and output folders will be named as
	 * {@code src} and {@code src-gen}. The Xtext project nature will be already configured on the N4JS project. Blocks
	 * until the auto-build job is terminated.
	 * <p>
	 * If the given project type {@link N4JSGlobals#PROJECT_TYPES_REQUIRING_N4JS_RUNTIME requires the N4JS runtime},
	 * then a dependency to "n4js-runtime" will be added to the <code>package.json</code>.
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
		final IProject project = createJSProject(projectName, "src", "src-gen",
				b -> {
					b.withType(type);
					if (N4JSGlobals.PROJECT_TYPES_REQUIRING_N4JS_RUNTIME.contains(type)) {
						b.withDependency(N4JSGlobals.N4JS_RUNTIME);
					}
				});
		configureProjectWithXtext(project);
		waitForAutoBuild();
		return project;
	}

	/**
	 * Same as {@link ProjectTestsUtils#createDummyN4JSRuntime(IProject)}, but will
	 * {@link LibraryManager#registerAllExternalProjects(org.eclipse.core.runtime.IProgressMonitor) refresh all external
	 * libraries} in the library manager.
	 */
	protected IFolder createAndRegisterDummyN4JSRuntime(IProject project) throws CoreException {
		IFolder runtimeProjectFolder = createDummyN4JSRuntime(project);
		waitForAutoBuild();
		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
		return runtimeProjectFolder;
	}

	/** Same as {@link ProjectTestsUtils#createDummyN4JSRuntime(IProject)}. */
	protected IFolder createDummyN4JSRuntime(IProject project) throws CoreException {
		return ProjectTestsUtils.createDummyN4JSRuntime(project);
	}

	/***/
	protected XtextEditor openAndGetXtextEditor(final IFile file1, final IWorkbenchPage page) {
		return openAndGetXtextEditorWithID(file1, page, N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
	}

	/***/
	protected XtextEditor openAndGetXtextEditorWithID(final IFile file1, final IWorkbenchPage page,
			final String editorID) {

		IEditorPart fileEditor = EclipseUIUtils.openFileEditor(file1, page, editorID);
		EclipseUIUtils.waitForEditorToBeActive(page, fileEditor);
		assertTrue(fileEditor instanceof XtextEditor);
		XtextEditor fileXtextEditor = (XtextEditor) fileEditor;
		return fileXtextEditor;
	}

	/**
	 * Opens a file in an Xtext editor by selecting its {@link TreeItem} in the Project Explorer view and then firing
	 * the "widget default selected" event (e.g. like pressing ENTER).
	 * <p>
	 * This method is primarily intended to test that certain UI functionality is available in the N4JS editor also for
	 * files from external libraries in case the file has been opened from the "External Dependencies" node shown in the
	 * Project Explorer view.
	 *
	 * @param page
	 *            the workbench page in which to open the editor. Use {@link EclipseUIUtils#getActivePage()} if unsure.
	 * @param textOfItemsInExplorer
	 *            path of tree item texts in the Project Explorer view pointing to the file to be opened. This path
	 *            should start with the project name, continue with all intermediate nodes (e.g. source folder) and end
	 *            with the name of the file to open.
	 * @return the editor that was opened, never <code>null</code>.
	 * @throws IllegalStateException
	 *             in case of error.
	 */
	protected XtextEditor openAndGetXtextEditorViaProjectExplorer(IWorkbenchPage page,
			String... textOfItemsInExplorer) {
		Objects.requireNonNull(page);
		Objects.requireNonNull(textOfItemsInExplorer);

		ProjectExplorer explorer;
		try {
			explorer = (ProjectExplorer) page.showView(ProjectExplorer.VIEW_ID);
		} catch (PartInitException e) {
			throw new IllegalStateException("cannot find Project Explorer view", e);
		}

		CommonViewer viewer = explorer.getCommonViewer();
		viewer.expandAll();
		UIUtils.waitForUiThread();

		Tree tree = viewer.getTree();
		TreeItem item = UIUtils.waitForTreeItem(tree, textOfItemsInExplorer);

		viewer.setSelection(new StructuredSelection(item.getData()));
		UIUtils.waitForUiThread();

		Event event = new Event();
		event.type = SWT.DefaultSelection;
		event.display = tree.getDisplay();
		event.widget = tree;
		event.item = item;
		item.getParent().notifyListeners(event.type, event);
		UIUtils.waitForUiThread();

		String lastText = textOfItemsInExplorer[textOfItemsInExplorer.length - 1];
		IEditorReference editorRef = UIUtils.waitForValueFromUI(
				() -> Stream.of(page.getEditorReferences())
						.filter(editor -> {
							try {
								return lastText.equals(editor.getEditorInput().getName());
							} catch (PartInitException e1) {
								return false;
							}
						})
						.findFirst(),
				() -> "editor with an editorInput with name==\"" + lastText + "\"");
		IEditorPart editor = editorRef.getEditor(false);
		if (editor == null) {
			throw new IllegalStateException("editor not found");
		} else if (!(editor instanceof XtextEditor)) {
			throw new IllegalStateException("not an Xtext editor");
		}
		return (XtextEditor) editor;
	}

	/**
	 * Adds a dependency to project 'projectName' in the package.json of project 'toChange'.
	 */
	protected void addProjectToDependencies(IProject toChange, N4JSProjectName projectName, String versionConstraint)
			throws IOException {
		ProjectTestsUtils.addProjectToDependencies(toChange, projectName, versionConstraint);
	}

	/**
	 * @return the source folder of the project
	 */
	protected IFolder configureProjectWithXtext(final IProject project) throws CoreException {
		return ProjectTestsUtils.configureProjectWithXtext(project, "src");
	}

	/***/
	protected IFolder configureProjectWithXtext(final IProject project, String sourceFolder) throws CoreException {
		return ProjectTestsUtils.configureProjectWithXtext(project, sourceFolder);
	}

	/**
	 * Creates a new file in the given folder. {@link N4JSGlobals#N4JS_FILE_EXTENSION} is appended to the name.
	 */
	protected IFile createTestFile(IFolder folder, String name, CharSequence content) throws CoreException {
		String fullName = name + "." + N4JSGlobals.N4JS_FILE_EXTENSION;
		return doCreateTestFile(folder, fullName, content);
	}

	/**
	 * Creates a new JavaScript file in the given folder. {@link N4JSGlobals#N4JSD_FILE_EXTENSION} is appended to the
	 * name.
	 */
	protected IFile createTestN4JSDFile(IFolder folder, String name, CharSequence content) throws CoreException {
		String fullName = name + "." + N4JSGlobals.N4JSD_FILE_EXTENSION;
		return doCreateTestFile(folder, fullName, content);
	}

	/**
	 * Creates a new JavaScript file in the given folder. {@link N4JSGlobals#JS_FILE_EXTENSION} is appended to the name.
	 */
	protected IFile createTestJSFile(IFolder folder, String name, CharSequence content) throws CoreException {
		String fullName = name + "." + N4JSGlobals.JS_FILE_EXTENSION;
		return doCreateTestFile(folder, fullName, content);
	}

	/***/
	@SuppressWarnings("resource")
	protected IFile doCreateTestFile(IFolder folder, String fullName, CharSequence content) throws CoreException {
		IFile file = folder.getFile(fullName);
		createFolder(folder);
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
	protected void createFolder(IFolder folder) throws CoreException {
		if (!folder.exists()) {
			createParentFolder(folder);
			folder.create(true, true, null);
		}
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
	protected void replaceFileContentAndWaitForRefresh(IFolder folder, IFile file, String newContent, long newTimestamp)
			throws CoreException, IOException {
		File fileInFilesystem = file.getLocation().toFile();
		FileWriter fileWriter = new FileWriter(fileInFilesystem);
		fileWriter.write(newContent);
		fileWriter.close();
		// We need to update the time of the file since out-of-sync is detected by timestamp on (most) OS
		fileInFilesystem.setLastModified(newTimestamp * 1000);
		folder.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IProject project, int count) throws CoreException {
		return ProjectTestsUtils.assertMarkers(assertMessage, project, count, ignoreSomeWarnings);
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IResource resource, int count) throws CoreException {
		return ProjectTestsUtils.assertMarkers(assertMessage, resource, count, ignoreSomeWarnings);
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IResource resource, int count,
			final Predicate<IMarker> markerPredicate) throws CoreException {

		return ProjectTestsUtils.assertMarkers(assertMessage, resource, count, markerPredicate, ignoreSomeWarnings);
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IProject project, String markerType, int count)
			throws CoreException {

		return ProjectTestsUtils.assertMarkers(assertMessage, project, markerType, count, ignoreSomeWarnings);
	}

	/***/
	protected IMarker[] assertMarkers(String assertMessage, final IResource resource, String markerType, int count)
			throws CoreException {

		return ProjectTestsUtils.assertMarkers(assertMessage, resource, markerType, count, ignoreSomeWarnings);
	}

	/** See {@link ProjectTestsUtils#assertNoErrors()}. */
	protected void assertNoErrors() throws CoreException {
		ProjectTestsUtils.assertNoErrors();
	}

	/** See {@link ProjectTestsUtils#assertNoIssues()}. */
	protected void assertNoIssues() throws CoreException {
		ProjectTestsUtils.assertNoIssues();
	}

	/** See {@link ProjectTestsUtils#assertIssues(String...)}. */
	protected void assertIssues(String... expectedMessages) throws CoreException {
		ProjectTestsUtils.assertIssues(expectedMessages);
	}

	/** See {@link ProjectTestsUtils#assertIssues(IResource, String...)}. */
	protected void assertIssues(final IResource resource, String... expectedMessages) throws CoreException {
		ProjectTestsUtils.assertIssues(resource, expectedMessages);
	}

	/** See {@link ProjectTestsUtils#assertIssues(String, IResource, String...)}. */
	protected void assertIssues(String message, final IResource resource, String... expectedMessages)
			throws CoreException {
		ProjectTestsUtils.assertIssues(message, resource, expectedMessages);
	}

	/** See {@link ProjectTestsHelper#runWithNodeRunnerUI(URI)}. */
	protected ProcessResult runWithNodeRunnerUI(URI moduleToRun) throws ExecutionException {
		return projectTestsHelper.runWithNodeRunnerUI(moduleToRun);
	}

	/** See {@link ProjectTestsHelper#runWithRunnerUI(String, N4JSProjectName, URI)}. */
	protected ProcessResult runWithRunnerUI(String runnerId, N4JSProjectName implementationId, URI moduleToRun)
			throws ExecutionException {
		return projectTestsHelper.runWithRunnerUI(runnerId, implementationId, moduleToRun);
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
		@SuppressWarnings("hiding")
		IDirtyStateManager dirtyStateManager = this.dirtyStateManager.get();

		TestEventListener eventListener = new TestEventListener(context, file);
		dirtyStateManager.addListener(eventListener);

		setDocumentContent(fileEditor, newContent);

		eventListener.waitForFiredEvent();
		dirtyStateManager.removeListener(eventListener);
		waitForUpdateEditorJob();
	}

	/***/
	protected void setDocumentContent(final XtextEditor xtextEditor, final String content) {
		Display.getCurrent().syncExec(() -> xtextEditor.getDocument().set(content));
	}

	/***/
	protected static void waitForUpdateEditorJob() {
		ProjectTestsUtils.waitForUpdateEditorJob();
		ProjectTestsUtils.waitForAllJobs();
	}

	/** Returns with the absolute URI of the resource loaded from the current plug-in. */
	protected java.net.URI getResourceUri(final String segment, final String... restSegments) {
		final String resourceName = getResourceName(segment, restSegments);
		final java.net.URL url = this.getClass().getClassLoader().getResource(resourceName);
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
