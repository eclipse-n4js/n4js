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
package org.eclipse.n4js.xpect.ui.methods;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.ui.organize.imports.BreakException;
import org.eclipse.n4js.ui.organize.imports.Interaction;
import org.eclipse.n4js.ui.organize.imports.UnsafeImortsOrganizer;
import org.eclipse.n4js.xpect.common.XpectCommentRemovalUtil;
import org.eclipse.n4js.xpect.ui.methods.contentassist.N4ContentAssistProcessorTestBuilder;
import org.eclipse.n4js.xpect.ui.methods.contentassist.N4ContentAssistProcessorTestBuilderHelper;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.IStringDiffExpectation;
import org.eclipse.xpect.expectation.StringDiffExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.ConsumedIssues;

import com.google.inject.Inject;

import junit.framework.AssertionFailedError;

/**
 */
@XpectImport(ValidationTestModuleSetup.class)
public class OrganizeImportXpectMethod {

	static {
		EclipseGracefulUIShutdownEnabler.enableOnce();
	}

	private final static Logger logger = Logger.getLogger(OrganizeImportXpectMethod.class);

	// TODO IDE-2539 switch to OrganizeImportsService
	@Inject
	UnsafeImortsOrganizer imortsOrganizer;

	@Inject
	private N4ContentAssistProcessorTestBuilderHelper n4ContentAssistProcessorTestBuilderHelper;

	@Inject
	XtextDocumentProvider xdocProvider;

	/**
	 * Give the result as a multiline diff. If cancellation due to multiple possible resolution is expected, provide the
	 * expected Exception with 'XPECT organizeImports ambiguous "Exception-Message" -->'.
	 *
	 * If the parameter is not provided, always the first computed solution in the list will be taken
	 *
	 * @param ambiguous
	 *            - String Expectation in {@link BreakException}
	 */
	@ParameterParser(syntax = "('ambiguous' arg0=STRING)?")
	@Xpect
	@ConsumedIssues({ Severity.INFO, Severity.ERROR, Severity.WARNING })
	public void organizeImports(
			String ambiguous, // arg0
			@StringDiffExpectation(whitespaceSensitive = false, allowSingleSegmentDiff = false, allowSingleLineDiff = false) IStringDiffExpectation expectation,
			@ThisResource XtextResource resource) throws Exception {

		logger.info("organize imports ...");
		boolean bAmbiguityCheck = ambiguous != null && ambiguous.trim().length() > 0;
		Interaction iaMode = bAmbiguityCheck ? Interaction.breakBuild : Interaction.takeFirst;

		try {

			if (expectation == null /* || expectation.isEmpty() */) {
				// TODO throw exception if empty.
				// Hey, I want to ask the expectation if it's empty.
				// Cannot access the region which could be asked for it's length.
				throw new AssertionFailedError(
						"The test is missing a diff: // XPECT organizeImports --> [old string replaced|new string expected] ");
			}

			// capture text for comparison:
			String beforeApplication = resource.getParseResult().getRootNode().getText();

			N4ContentAssistProcessorTestBuilder fixture = n4ContentAssistProcessorTestBuilderHelper
					.createTestBuilderForResource(resource);

			IXtextDocument xtextDoc = fixture.getDocument(resource, beforeApplication);

			// in case of cross-file hyperlinks, we have to make sure the target resources are fully resolved
			final ResourceSet resSet = resource.getResourceSet();
			for (Resource currRes : new ArrayList<>(resSet.getResources())) {
				N4JSResource.postProcess(currRes);
			}

			// Calling organize imports
			Display.getDefault().syncExec(
					() -> imortsOrganizer.unsafeOrganizeDocument(xtextDoc, iaMode));

			if (bAmbiguityCheck) {
				// should fail if here
				assertEquals("Expected ambiguous resolution to break the organize import command.", ambiguous, "");
			}

			// checking that no errors are left.
			String textAfterApplication = xtextDoc.get();

			// compare with expectation, it's a multiline-diff expectation.
			String before = XpectCommentRemovalUtil.removeAllXpectComments(beforeApplication);
			String after = XpectCommentRemovalUtil.removeAllXpectComments(textAfterApplication);

			expectation.assertDiffEquals(before, after);
		} catch (Exception exc) {
			if (exc instanceof RuntimeException && exc.getCause() instanceof BreakException) {
				String breakMessage = exc.getCause().getMessage();
				assertEquals(ambiguous, breakMessage);
			} else {
				throw exc;
			}
		}
	}
}
