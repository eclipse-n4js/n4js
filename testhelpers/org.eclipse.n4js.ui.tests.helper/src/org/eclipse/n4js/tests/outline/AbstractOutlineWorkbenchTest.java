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
package org.eclipse.n4js.tests.outline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.EclipseUIUtils;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.IOutlineNodeComparer;
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;
import org.junit.After;
import org.junit.Before;

/**
 * Copied and adapted from http://git.eclipse.org/c/tmf/org.eclipse.xtext.git/plain/tests/
 * org.eclipse.xtext.ui.tests/tests/org/eclipse/xtext/ui/tests/editor/outline/ AbstractOutlineWorkbenchTest.java
 *
 * Uses infrastructure of builder test.
 */
@SuppressWarnings("javadoc")
public abstract class AbstractOutlineWorkbenchTest extends AbstractBuilderParticipantTest {
	protected static final int ERROR_TIMEOUT = 10000;
	protected static final int EXPECTED_TIMEOUT = 1000;
	protected IOutlineNodeComparer nodeComparer = new IOutlineNodeComparer.Default();
	protected IFile file;
	protected XtextEditor editor;
	protected IXtextDocument document;
	protected IViewPart outlineView;
	protected SyncableOutlinePage outlinePage;
	protected TreeViewer treeViewer;
	protected String modelAsText;
	protected IOutlineNode modelNode;
	protected IPreferenceStore preferenceStore;
	protected IOutlineNodeComparer comparer;

	@Before
	public void setUp2() throws Exception {
		preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
		comparer = new IOutlineNodeComparer.Default();

		// when using in XPECT, XPECT already creates the project structure
		if (shouldCreateProjectStructure()) {
			createProjectStructure();
		}
		//
		openXtextDocument();
		openOutlineView();
	}

	// creates project structure with N4JS file and uses for that the values given by:
	// - getProjectName()
	// - getModuleFolder()
	// - getFileName()
	// - getModelAsText()
	// and checks the N4JS file has no errors
	private void createProjectStructure() throws CoreException {
		final IProject project = createJSProject(getProjectName());
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, getModuleFolder());
		modelAsText = getModelAsText();
		file = createTestFile(moduleFolder, getFileName(), modelAsText);
		assertMarkers("File should have no errors", file, 0);
	}

	// opens the Xtext editor for the N4JS file as defined by getProjectName() / getModuleFolder() / getFileName()
	// retrieves the XtextDocument from that editor
	private void openXtextDocument() {
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		editor = openAndGetXtextEditor(file, page);
		List<?> errors = getEditorErrors(editor);
		assertEquals("Editor of " + getFileName() + " should have no errors", 0, errors.size());

		document = editor.getDocument();
	}

	// opens the outline view for the currently open Xtext editor
	// the outline page is adapted so that we can wait for the Job that refreshes the outline view
	// it is asserted that the outline has exactly one direct child node
	protected void openOutlineView() throws PartInitException, InterruptedException {
		outlineView = editor.getEditorSite().getPage().showView("org.eclipse.ui.views.ContentOutline");
		executeAsyncDisplayJobs();
		Object adapter = editor.getAdapter(IContentOutlinePage.class);
		assertTrue(adapter instanceof OutlinePage);
		outlinePage = new SyncableOutlinePage((OutlinePage) adapter);
		outlinePage.resetSyncer();
		try {
			outlinePage.waitForUpdate(EXPECTED_TIMEOUT);
		} catch (TimeoutException e) {
			System.out.println("Expected timeout exceeded: " + EXPECTED_TIMEOUT);// timeout is OK here
		}
		treeViewer = outlinePage.getTreeViewer();
		assertSelected(treeViewer);
		assertExpanded(treeViewer);
		assertTrue(treeViewer.getInput() instanceof IOutlineNode);
		IOutlineNode rootNode = (IOutlineNode) treeViewer.getInput();
		List<IOutlineNode> children = rootNode.getChildren();
		assertEquals(1, children.size());
		modelNode = children.get(0);
	}

	// when return false, the creation of the project structure is suppress
	protected abstract boolean shouldCreateProjectStructure();

	// the name for N4JS resource inside the module folder, that is to create
	// (resp. that is expected to be there)
	protected abstract String getFileName();

	// the name for the folder inside the source folder of project, that is to create
	// (resp. that is expected to be there)
	protected abstract String getModuleFolder();

	// the name for the project to create (resp. that is expected to be there)
	protected abstract String getProjectName();

	// the content to be written in N4JS file (resp. that should be in the file)
	protected abstract String getModelAsText();

	@After
	public void tearDown2() throws Exception {
		if (null != editor) {
			editor.close(false);
		}
		if (null != outlineView) {
			outlineView.getSite().getPage().hideView(outlineView);
		}
		executeAsyncDisplayJobs();
	}

	protected void assertExpanded(TreeViewer aTreeViewer, IOutlineNode... expectedExpansion) {
		Object[] expandedElements = aTreeViewer.getExpandedElements();
		assertEquals(expectedExpansion.length, expandedElements.length);
		OUTER: for (Object expandedObject : expandedElements) {
			assertTrue(expandedObject instanceof IOutlineNode);
			for (IOutlineNode expectedExpanded : expectedExpansion) {
				if (nodeComparer.equals((IOutlineNode) expandedObject, expectedExpanded))
					continue OUTER;
			}
			fail("Unexpected expansion" + expandedObject.toString());
		}
	}

	protected void assertSelected(TreeViewer aTreeViewer, IOutlineNode... expectedSelection) {
		ISelection selection = aTreeViewer.getSelection();
		assertTrue(selection instanceof IStructuredSelection);
		assertEquals(expectedSelection.length, ((IStructuredSelection) selection).size());
		OUTER: for (Iterator<?> i = ((IStructuredSelection) selection).iterator(); i.hasNext();) {
			Object selectedObject = i.next();
			assertTrue(selectedObject instanceof IOutlineNode);
			for (IOutlineNode expectedSelected : expectedSelection) {
				if (nodeComparer.equals((IOutlineNode) selectedObject, expectedSelected))
					continue OUTER;
			}
			fail("Unexpected selection " + selectedObject.toString());
		}
	}

	protected void assertSame(IOutlineNode node, TreeItem treeItem) {
		assertTrue(treeItem.getData() instanceof IOutlineNode);
		assertTrue(comparer.equals(node, (IOutlineNode) treeItem.getData()));
	}

	protected void activate(IWorkbenchPart part) {
		editor.getSite().getPage().activate(part);
	}

	protected void executeAsyncDisplayJobs() {
		while (Display.getCurrent().readAndDispatch()) {
			// blocking
		}
	}
}
