/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.hyperlink;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.EclipseUIUtils;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.tests.util.ShippedCodeInitializeTestHelper;
import org.eclipse.n4js.ui.editor.N4JSHyperlinkDetector;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.hyperlinking.XtextHyperlink;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * This test checks that hyperlinks to resources in the external libraries use file based {@link URI}s.
 */
public class HyperlinkPluginUITest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";
	private static final String SUBFOLDER = "Hyperlink";
	private static final String PROJECT_NAME = "Hyperlink";

	@Inject
	private ShippedCodeInitializeTestHelper shippedCodeInitializeTestHelper;

	@Inject
	private N4JSHyperlinkDetector hyperlinkDetector;

	@Inject
	private IURIEditorOpener uriEditorOpener;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		shippedCodeInitializeTestHelper.setupBuiltIns();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		shippedCodeInitializeTestHelper.tearDownBuiltIns();
		super.tearDown();
	}

	/**
	 * The test invokes the method
	 * {@link N4JSHyperlinkDetector#detectHyperlinks(org.eclipse.jface.text.ITextViewer, IRegion, boolean)} to gather
	 * all hyperlinks on the call to 'process' of "n4js-runtime-node" and checks that the returned hyperlink uri is a
	 * file based uri.
	 */
	@Test
	public void testHyperlinks() throws CoreException {
		File prjDir = new File(getResourceUri(PROBANDS, SUBFOLDER));
		IProject project = ProjectTestsUtils.importProject(prjDir, PROJECT_NAME);

		IResource resourceABC = project.findMember("src/ABC.n4js");
		IFile fileABC = ResourcesPlugin.getWorkspace().getRoot().getFile(resourceABC.getFullPath());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		syncExtAndBuild();

		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor editor = openAndGetXtextEditor(fileABC, page);
		ISourceViewer sourceViewer = editor.getInternalSourceViewer();
		IRegion region = new Region(367, 0);
		ProjectTestsUtils.waitForAllJobs();

		IHyperlink[] hlinksInABC = hyperlinkDetector.detectHyperlinks(sourceViewer, region, true);

		assertTrue("Hyperlink missing", hlinksInABC != null && hlinksInABC.length == 1);
		assertTrue("Hyperlink must be of type XtextHyperlink", hlinksInABC[0] instanceof XtextHyperlink);
		XtextHyperlink hyperlinkToProcess = (XtextHyperlink) hlinksInABC[0];
		URI uriProcess = hyperlinkToProcess.getURI();
		assertTrue("Hyperlink URI must be a file uri", uriProcess.isFile());
		File fileProcess = new File(uriProcess.toFileString());
		assertTrue("File 'process.n4jsd' must exist", fileProcess.isFile());

		editor = (XtextEditor) uriEditorOpener.open(uriProcess, true);
		page = EclipseUIUtils.getActivePage();
		TextSelection selectionProcess = (TextSelection) page.getSelection();
		assertTrue("Selection must be 'process'", selectionProcess.getText().equals("process"));
		sourceViewer = editor.getInternalSourceViewer();
		region = new Region(556, 12);
		IHyperlink[] hlinksInProcess = hyperlinkDetector.detectHyperlinks(sourceViewer, region, true);

		assertTrue("Hyperlink in external library missing", hlinksInProcess != null && hlinksInProcess.length == 1);
		assertTrue("Hyperlink must be of type XtextHyperlink", hlinksInProcess[0] instanceof XtextHyperlink);
		XtextHyperlink hyperlinkToEvent = (XtextHyperlink) hlinksInProcess[0];
		URI uriEvent = hyperlinkToEvent.getURI();
		assertTrue("Hyperlink URI must be a file uri", uriEvent.isFile());

		editor = (XtextEditor) uriEditorOpener.open(uriEvent, true);
		page = EclipseUIUtils.getActivePage();
		TextSelection selectionEvent = (TextSelection) page.getSelection();
		assertTrue("Selection must be 'EventEmitter'", selectionEvent.getText().equals("EventEmitter"));
	}

	/**
	 * GH-1199
	 * <p>
	 * Similar to {@link #testHyperlinks()}, but instead of opening external library file "process.n4jsd" by following a
	 * hyperlink inside an ordinary N4JS source file, we open "process.n4jsd" via the "External Dependencies" entry in
	 * the Project Explorer view.
	 */
	@Test
	public void testHyperlinksWhenOpenedFromExplorer() throws CoreException {
		File prjDir = new File(getResourceUri(PROBANDS, SUBFOLDER));
		ProjectTestsUtils.importProject(prjDir, PROJECT_NAME);
		waitForAutoBuild();

		syncExtAndBuild();
		UIUtils.waitForUiThread();

		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor editor = openAndGetXtextEditorViaProjectExplorer(page,
				PROJECT_NAME,
				"External Dependencies",
				"N4JS Runtime",
				"Runtime Libraries",
				"n4js-runtime-node",
				"src/n4js",
				"process.n4jsd");
		UIUtils.waitForUiThread();

		ISourceViewer sourceViewer = editor.getInternalSourceViewer();
		IRegion region = new Region(556, 12);
		IHyperlink[] hlinksInProcess = hyperlinkDetector.detectHyperlinks(sourceViewer, region, true);

		assertTrue("Hyperlink in external library missing", hlinksInProcess != null && hlinksInProcess.length == 1);
		assertTrue("Hyperlink must be of type XtextHyperlink", hlinksInProcess[0] instanceof XtextHyperlink);
		XtextHyperlink hyperlinkToEvent = (XtextHyperlink) hlinksInProcess[0];
		URI uriEvent = hyperlinkToEvent.getURI();
		assertTrue("Hyperlink URI must be a file uri", uriEvent.isFile());

		editor = (XtextEditor) uriEditorOpener.open(uriEvent, true);
		UIUtils.waitForUiThread();
		TextSelection selectionEvent = (TextSelection) page.getSelection();
		assertTrue("Selection must be 'EventEmitter'", selectionEvent.getText().equals("EventEmitter"));
	}
}
