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
package org.eclipse.n4js.xpect.ui.methods.quickfix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.tests.util.EditorsUtil;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.ResourceTweaker;
import org.eclipse.n4js.xpect.common.XpectCommentRemovalUtil;
import org.eclipse.n4js.xpect.config.Config;
import org.eclipse.n4js.xpect.config.VarDef;
import org.eclipse.n4js.xpect.config.XpEnvironmentData;
import org.eclipse.n4js.xpect.ui.common.QuickFixTestHelper;
import org.eclipse.n4js.xpect.ui.common.XpectN4JSES5TranspilerHelper;
import org.eclipse.n4js.xpect.ui.methods.contentassist.RegionWithCursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.CommaSeparatedValuesExpectation;
import org.eclipse.xpect.expectation.ICommaSeparatedValuesExpectation;
import org.eclipse.xpect.expectation.IStringDiffExpectation;
import org.eclipse.xpect.expectation.IStringDiffExpectation.ITokenAdapter;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringDiffExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.setup.ISetupInitializer;
import org.eclipse.xpect.xtext.lib.setup.FileSetupContext;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.ConsumedIssues;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.IssuesByLine;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.TestingResourceValidator;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * Provides XPEXT test methods for quick fixes
 */
@XpectImport({ N4JSOffsetAdapter.class, XpEnvironmentData.class, VarDef.class, Config.class,
		ValidationTestModuleSetup.class })
public class QuickFixXpectMethod {

	static {
		EclipseGracefulUIShutdownEnabler.enableOnce();
	}

	private static Logger logger = Logger.getLogger(QuickFixXpectMethod.class);

	private static class ExecutionResult {
		public String result;
	}

	/*-
	contentAssist kind 'smart' at 'a.<|>methodA'       display   'methodA2'            --> 'methodA2(): any - A'
	quickFix                   at 'a.<|>method'        apply     'methodA2'  fileValid --> a.<|>methodA2();
	                    kind        offset              checkType  selected    mode
	                                arg2                           arg3       arg4        arg5
	 */
	/**
	 * Choose quick fix and apply
	 *
	 * @param expectation
	 *            from right hand side - expected changes to code, especially cursor position.
	 * @param resource
	 *            injected resource under test
	 * @param offset
	 *            parsed arg2 offset cursor position
	 * @param selected
	 *            parsed arg3 - chosen quick fix to apply
	 * @param mode
	 *            parsed arg4 if 'fileValid' additional validation check after application
	 *
	 * @param specifiedResourcePath
	 *            Specifies the relative path of the resource in which the quickfix applies
	 * @throws Exception
	 *             in test failure.
	 */
	@ParameterParser(syntax = "('at' (arg2=STRING ('apply' arg3=STRING)? ('resource=' arg5=STRING)?  (arg4=ID)? )? )? )?")
	@Xpect
	@ConsumedIssues({ Severity.INFO, Severity.ERROR, Severity.WARNING })
	public void quickFix(
			// @StringDiffExpectation(tokenizer = LineBasedTokenizer) IStringDiffExpectation expectation, // arg0
			@StringDiffExpectation(whitespaceSensitive = false) IStringDiffExpectation expectation, // arg0
			@ThisResource XtextResource resource, // arg1
			RegionWithCursor offset, // arg2
			// String checkType, // arg3
			String selected, // arg3
			String mode, // arg4
			String specifiedResourcePath, // arg5
			@IssuesByLine Multimap<Integer, Issue> offset2issue) throws Exception {
		quickFix(expectation, resource, offset, selected, mode, specifiedResourcePath, offset2issue, true);
	}

	/**
	 * Choose quick fix and apply
	 *
	 * @param expectation
	 *            from right hand side - expected changes to code, especially cursor position.
	 * @param resource
	 *            injected resource under test
	 * @param offset
	 *            parsed arg2 offset cursor position
	 * @param selected
	 *            parsed arg3 - chosen quick fix to apply
	 * @param mode
	 *            parsed arg4 if 'fileValid' additional validation check after application
	 *
	 * @param specifiedResourcePath
	 *            Specifies the relative path of the resource in which the quickfix applies
	 *
	 * @param reparseResource
	 *            Specifies if the resource is reset after application of the quick fix or not.
	 *
	 *            Note: In case of passing false the caller has to reset the resource on its own as it is essential for
	 *            the following quick fix xpect method calls.
	 *
	 * @throws Exception
	 *             in test failure.
	 */
	private void quickFix(IStringDiffExpectation expectation,
			XtextResource resource,
			RegionWithCursor offset,
			String selected,
			String mode,
			String specifiedResourcePath,
			Multimap<Integer, Issue> offset2issue,
			boolean reparseResource) throws Exception {
		Optional<XtextEditor> editor = Optional.empty();
		// Optional<XtextEditor> specifiedResourceEditor = Optional.empty();
		try {

			List<IssueResolution> resolutions = collectAllResolutions(resource, offset, offset2issue);
			IssueResolution res = QuickFixTestHelper.selectSingleOrFail(resolutions, selected);

			String beforeApplication;

			URI targetResourceUri = resource.getURI();

			// For changes not applied in the file the issue occurs in
			// Get the content of the specified resource
			if (specifiedResourcePath != null && !specifiedResourcePath.isEmpty()) {

				URI specifiedURI = resource.getURI().trimSegments(1).appendSegments(specifiedResourcePath.split("/"));

				targetResourceUri = specifiedURI;
				beforeApplication = getContentForResourceUri(specifiedURI);

			} else {
				// capture text for comparison from disk:
				beforeApplication = getContentForResourceUri(resource.getURI());//
				// beforeApplication = resource.getParseResult().getRootNode().getText();
			}
			Display.getDefault().syncExec(() -> res.apply());

			// obtain new text from editor and reparse it into the resource, otherwise the resource is outdated.
			// necessary if the resource is further used in compilation after this QF-Application.
			editor = EditorsUtil.openXtextEditor(targetResourceUri, N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
			assertTrue("No editor for provided resource " + targetResourceUri.path(), editor.isPresent());

			String textAfterApplication = editor.get().getDocument().get();

			// Disable validation check in case of a seperately specified resource
			// as it would replace the test code with the specified resource
			if (specifiedResourcePath == null || specifiedResourcePath.isEmpty()) {

				// reparse the text into the resource.
				resource.reparse(textAfterApplication);

				// in case of @IssuesByLine our Validator is of type:
				TestingResourceValidator trVal = (TestingResourceValidator) resource.getResourceServiceProvider()
						.getResourceValidator();

				// Check for no other Issues
				if ("fileValid".equals(mode)) {
					List<Issue> remainingIssues = trVal.validateDelegate(resource,
							CheckMode.ALL,
							CancelIndicator.NullImpl,
							null);
					assertEquals("Expecting all issues resolved, but got still left: " + remainingIssues, 0,
							remainingIssues.size());
				}
			}

			if (reparseResource) {
				// Reset resource after quick fix application
				resource.reparse(getContentForResourceUri(resource.getURI()));
			}

			if (expectation != null) {

				// TODO assert cursor position after application.
				String before = XpectCommentRemovalUtil.removeAllXpectComments(beforeApplication);
				String after = XpectCommentRemovalUtil.removeAllXpectComments(textAfterApplication);

				if ("whitespaceSensitive".equals(mode)) {
					LineBasedTokenizer tokenizer = new LineBasedTokenizer();
					expectation.assertDiffEquals(tokenizer.apply(before), tokenizer.apply(after),
							new WSAwareTokenAdapter());
				} else {
					expectation.assertDiffEquals(before, after);
				}

			}
		} finally {
			// TODO initial state aware context
			/*
			 * If editor was created it should be closed, but if it was opened before running tests, we should only undo
			 * changes (and get rid of dirty state), but we should not close editor
			 */
			editor.ifPresent(e -> EditorsUtil.forceCloseAllEditors());
			// editor.ifPresent(e -> EditorsUtil.forceCloseEditor(e));

		}
	}

	/**
	 * Whitespace sensitive token adapter.
	 *
	 * This token adapter prevents hiding of whitespace and other control characters. It is used to realize whitespace
	 * sensitive difference computation.
	 *
	 */
	final private class WSAwareTokenAdapter implements ITokenAdapter<String> {

		@Override
		public boolean isHidden(String token, String segment) {
			return false;
		}

		@Override
		public Iterable<String> splitIntoSegments(String token) {
			return Collections.singleton(token);
		}

		@Override
		public float similarity(String token1, String segment1, String token2, String segment2) {
			return token1.equals(token2) ? 0.0f : 1.0f;
		}

	}

	/*-
	contentAssistList kind 'smart' at 'a.<|>methodA'       display   'methodA2'            --> 'methodA2(): any - A'
	quickFix                       at 'a.<|>method'        apply     'methodA2'  fileValid --> a.<|>methodA2();
	quickFixAndRun                 at 'a.<|>method'        apply     'methodHelloWorld' --> Hello World
	                        kind        offset             checkType  selected    mode
	                                    arg2                          arg3
	 */
	/**
	 * Apply quick fix, compile and run the result. Compares the generated stdout-result to the expectation on the right
	 * hand side.
	 *
	 * @param expectation
	 *            - expected output of running script, just stdout no error-stream. Expecting the error-stream to be
	 *            empty.
	 * @param resource
	 *            - injected resource
	 * @param offset
	 *            - parsed arg2 - cursor position
	 * @param selected
	 *            - parsed arg3 - selection from list of expectations
	 * @param offset2issue
	 *            - injected Map of issues
	 * @param init
	 *            - injected xpect-initizalizer
	 * @param fileSetupContext
	 *            - injected xpect meta-info about file under test.
	 * @throws Exception
	 *             in failure case
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg2=STRING)? ('apply'  arg3=STRING )?")
	@ConsumedIssues({ Severity.INFO, Severity.ERROR, Severity.WARNING })
	public void quickFixAndRun(
			@StringExpectation(caseSensitive = true) IStringExpectation expectation, // arg0
			@ThisResource XtextResource resource, // arg1
			RegionWithCursor offset, // arg2
			String selected, // arg3
			@IssuesByLine Multimap<Integer, Issue> offset2issue,
			ISetupInitializer<Object> init,
			FileSetupContext fileSetupContext) throws Exception {
		try {
			long timeStart = System.currentTimeMillis();
			logger.info("Execution started: " + new Date(timeStart));

			// System.out.println(
			// "##-Qr-## we got it selected='" + selected + "' at " + offset + " in " + resource.toString() + "");
			String executionResult;
			ExecutionResult exRes = new ExecutionResult();
			ResourceTweaker resourceTweaker = resourceToTweak -> {
				try {
					quickFix(null, resourceToTweak, offset, selected, "fileValid", "", offset2issue, false);
				} catch (Exception e) {
					Exceptions.sneakyThrow(e);
				}
			};

			Display.getDefault().syncExec(
					() -> exRes.result = compileAndExecute(resource, init, fileSetupContext, resourceTweaker));

			executionResult = exRes.result;
			long timeEnd = System.currentTimeMillis();
			logger.info("Execution finished: " + new Date(timeEnd));
			logger.info("Execution took " + (timeEnd - timeStart + 0.0) / 1000.0 + " seconds.");

			expectation.assertEquals(executionResult);

			// Reset resource after quick fix application and code execution
			resource.reparse(getContentForResourceUri(resource.getURI()));

		} finally {
			logger.info("Closing all editors");
			EditorsUtil.forceCloseAllEditors();
		}

		logger.info("Successful End of Execution");

	}

	private String compileAndExecute(XtextResource resource, ISetupInitializer<Object> init,
			FileSetupContext fileSetupContext, ResourceTweaker resourceTweaker) {

		try {
			// Asking the injector is necessary, since the Xpect methods get also called from N4MF context.
			XpectN4JSES5TranspilerHelper transpilerHelper = resource.getResourceServiceProvider()
					.get(XpectN4JSES5TranspilerHelper.class);
			return transpilerHelper.doCompileAndExecute(resource, init,
					fileSetupContext,
					false,
					resourceTweaker, GeneratorOption.DEFAULT_OPTIONS);
		} catch (IOException e) {
			throw new RuntimeException("Error while compiling script.", e);
		}
	}

	/*-
	contentAssistList kind 'smart' at 'a.<|>methodA'       display   'methodA2'            --> 'methodA2(): any - A'
	quickFix                       at 'a.<|>method'        apply     'methodA2'  fileValid --> a.<|>methodA2();
	                    kind        offset              checkType  selected    mode
	                                arg2                arg3       arg4        arg5
	 */
	/**
	 * Example: {@code // XPECT quickFixList  at 'a.<|>method' --> 'import A','do other things' }
	 *
	 * @param expectation
	 *            comma separated strings, which are proposed as quick fix
	 * @param resource
	 *            injected xtext-file
	 * @param offset
	 *            cursor position at '<|>'
	 * @param checkType
	 *            'display': verify list of provided proposals comparing their user-displayed strings.
	 * @param selected
	 *            which proposal to pick
	 * @param mode
	 *            modus of operation
	 * @param offset2issue
	 *            mapping of offset(!) to issues.
	 * @throws Exception
	 *             if failing
	 */
	@Xpect
	@ParameterParser(syntax = "('at' (arg2=STRING (arg3=ID  (arg4=STRING)?  (arg5=ID)? )? )? )?")
	@ConsumedIssues({ Severity.INFO, Severity.ERROR, Severity.WARNING })
	public void quickFixList(
			@CommaSeparatedValuesExpectation(quoted = true, ordered = true) ICommaSeparatedValuesExpectation expectation, // arg0
			@ThisResource XtextResource resource, // arg1
			RegionWithCursor offset, // arg2
			String checkType, // arg3
			String selected, // arg4
			String mode, // arg5
			@IssuesByLine Multimap<Integer, Issue> offset2issue) throws Exception {

		List<IssueResolution> resolutions = collectAllResolutions(resource, offset, offset2issue);

		List<String> resolutionNames = Lists.newArrayList();
		for (IssueResolution resolution : resolutions) {
			resolutionNames.add(resolution.getLabel());
		}

		expectation.assertEquals(resolutionNames);
	}

	/**
	 * CollectAll resolutions under the cursor at offset.
	 *
	 */
	List<IssueResolution> collectAllResolutions(XtextResource resource, RegionWithCursor offset,
			Multimap<Integer, Issue> offset2issue) {

		EObject script = resource.getContents().get(0);
		ICompositeNode scriptNode = NodeModelUtils.getNode(script);
		ILeafNode offsetNode = NodeModelUtils.findLeafNodeAtOffset(scriptNode, offset.getGlobalCursorOffset());
		int offStartLine = offsetNode.getTotalStartLine();
		List<Issue> allIssues = QuickFixTestHelper.extractAllIssuesInLine(offStartLine, offset2issue);

		List<IssueResolution> resolutions = Lists.newArrayList();

		for (Issue issue : allIssues) {
			if (issue.getLineNumber() == offsetNode.getStartLine()
					&& issue.getLineNumber() <= offsetNode.getEndLine()) {

				IssueResolutionProvider quickfixProvider = resource.getResourceServiceProvider()
						.get(IssueResolutionProvider.class);
				Display.getDefault().syncExec(() -> resolutions.addAll(quickfixProvider.getResolutions(issue)));
			}
		}
		return resolutions;
	}

	/**
	 * Helper method to get the content of an resource at uri. Takes care of the encoding.
	 *
	 * @param uri
	 *            URI to resource
	 * @return content as string
	 * @throws Exception
	 *             in case of io or uri issues
	 */
	private String getContentForResourceUri(URI uri)
			throws Exception {

		String platformStr = uri.toString().replace("platform:/resource/", "");
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformStr));

		java.io.InputStream fileStream = file.getContents();

		java.util.Scanner s = new java.util.Scanner(fileStream, file.getCharset());
		s.useDelimiter("\\A");

		String content = s.hasNext() ? s.next() : "";

		fileStream.close();
		s.close();

		return content;
	}
}
