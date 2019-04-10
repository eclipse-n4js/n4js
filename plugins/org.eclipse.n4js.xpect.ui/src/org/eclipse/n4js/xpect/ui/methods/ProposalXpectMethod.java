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

import static org.eclipse.n4js.xpect.ui.common.QuickFixTestHelper.asString2;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.XpectCommentRemovalUtil;
import org.eclipse.n4js.xpect.config.Config;
import org.eclipse.n4js.xpect.config.VarDef;
import org.eclipse.n4js.xpect.config.XpEnvironmentData;
import org.eclipse.n4js.xpect.ui.methods.contentassist.ContentAssistXpectMethod;
import org.eclipse.n4js.xpect.ui.methods.contentassist.N4ContentAssistProcessorTestBuilder;
import org.eclipse.n4js.xpect.ui.methods.contentassist.N4ContentAssistProcessorTestBuilderHelper;
import org.eclipse.n4js.xpect.ui.methods.contentassist.RegionWithCursor;
import org.eclipse.n4js.xpect.ui.methods.quickfix.QuickFixXpectMethod;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.CommaSeparatedValuesExpectation;
import org.eclipse.xpect.expectation.ICommaSeparatedValuesExpectation;
import org.eclipse.xpect.expectation.IStringDiffExpectation;
import org.eclipse.xpect.expectation.StringDiffExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

import junit.framework.AssertionFailedError;

/**
 * Provides XPECT test methods for proposals
 *
 * deprecated this class is not used or documented in design document, use content assist xpect methods instead
 * {@link ContentAssistXpectMethod} or {@link QuickFixXpectMethod}
 */
@XpectImport({ N4JSOffsetAdapter.class, XpEnvironmentData.class, VarDef.class, Config.class })
public class ProposalXpectMethod {

	static {
		EclipseGracefulUIShutdownEnabler.enableOnce();
	}

	private static Logger logger = Logger.getLogger(ProposalXpectMethod.class);

	@Inject
	private IParser parser;

	@Inject
	private Provider<ResourceSet> resourceSetProvider;

	@Inject
	private Provider<XtextResource> resourceProvider;

	@Inject
	private N4ContentAssistProcessorTestBuilderHelper n4ContentAssistProcessorTestBuilderHelper;

	/**
	 * Iterates over all proposed completion entries at the given offset and checks whether its application would cause
	 * validation issues in the resource.
	 *
	 * @param expectation
	 *            the expected proposals causing validation errors
	 * @param resource
	 *            the resource under test
	 * @param offset
	 *            the offset of where to invoke content assist (note: it must be named arg2 as Xpect injects the
	 *            parameter values by position)
	 * @throws Exception
	 *             some exception
	 */
	@ParameterParser(syntax = "'at' arg2=STRING")
	@Xpect
	public void checkProposals(@CommaSeparatedValuesExpectation ICommaSeparatedValuesExpectation expectation,
			@ThisResource XtextResource resource, RegionWithCursor offset) throws Exception {

		N4ContentAssistProcessorTestBuilder fixture = n4ContentAssistProcessorTestBuilderHelper
				.createTestBuilderForResource(resource);

		ICompletionProposal[] computeCompletionProposals = allProposalsAt(offset, fixture);

		List<String> proposalsWithError = Lists.newArrayList();
		for (int proposal = 0; proposal < computeCompletionProposals.length; proposal++) {
			fixture = fixture.reset();
			String content = resource.getParseResult().getRootNode().getText();
			fixture = fixture.append(content);
			IXtextDocument document = fixture.getDocument(resource, content);
			int index = proposal;
			String newContent = applyProposal(computeCompletionProposals[index], document);
			IXtextDocument newXtextDocument = fixture.getDocument(getNewResource(newContent, resource.getURI()),
					content);
			newXtextDocument.readOnly(
					new IUnitOfWork<Object, XtextResource>() {

						@Override
						public Object exec(XtextResource state) throws Exception {
							EcoreUtil.resolveAll(state);
							if (!state.getErrors().isEmpty()) {
								proposalsWithError.add(computeCompletionProposals[index].getDisplayString());
							} else if (!state.validateConcreteSyntax().isEmpty()) {
								proposalsWithError.add(computeCompletionProposals[index].getDisplayString());
							}
							return null;
						}
					});
		}
		expectation.assertEquals(proposalsWithError);
	}

	// @formatter:off
	/**
	 * Test the application of the given content assist entry. After applying the content assist there should be the
	 * expected diff produced when comparing the document before and after applying the content assist.
	 *
	 * The diff has the following semantics:
	 *
	 * "  ": (whitespace) nothing changed
	 * "+ ": line was added
	 * "- ": line was deleted
	 * "| ": line was modified, displayed as [oldText|newText]
	 *
	 * If the given expected content assist entry cannot be found, an error message is inserted at the offset resulting
	 * in expectation mismatch and so also to a failing test.
	 *
	 * Also clf.
	 * https://github.com/meysholdt/Xpect/tree/master/org.eclipse.xtext.example.domainmodel.xpect.tests/src/org
	 * /eclipse/xtext/example/domainmodel/xpect/tests/modify for some more examples.
	 *
	 * /\* XPECT proposalChange 'methodA2' at 'methodA' ---
	 *   (...)
	 *  }
	 * }
	 * var A a = new A();
	 * |a.[methodA|methodA2methodA]
	 * --- *\/
	 *
	 * Please note, that the comparison always contains also some lines around the line that contains the diff.
	 * Xpect currently doesn't allow to configure the number of those lines.
	 *
	 * @param expectation
	 *            the expected diff comparing the document before and after applying the content assist
	 * @param resource
	 *            the resource under test
	 * @param arg2
	 *            the offset of where to invoke content assist (note: it must be named arg2 as Xpect injects the
	 *            parameter values by position)
	 * @param arg3
	 *            the content assist entry to select
	 * @throws Exception
	 *             some exception
	 */
	// @formatter:on
	@ParameterParser(syntax = "(arg3=STRING 'at' arg2=STRING)?")
	@Xpect
	public void proposalChange(@StringDiffExpectation IStringDiffExpectation expectation,
			@ThisResource XtextResource resource, /* @ThisOffset int */RegionWithCursor arg2, String arg3)
			throws Exception {

		// val int offset = arg2;
		RegionWithCursor offset = arg2;
		String text = arg3;

		// println("proposal change with offset="+arg2+" selection="+arg3)
		N4ContentAssistProcessorTestBuilder fixture = n4ContentAssistProcessorTestBuilderHelper
				.createTestBuilderForResource(resource);
		ICompletionProposal proposal = exactlyMatchingProposal(offset, fixture, text);

		String before = resource.getParseResult().getRootNode().getText();
		if (proposal != null) {
			IXtextDocument document = fixture.getDocument(resource, before);
			String after = applyProposal(proposal, document);
			before = XpectCommentRemovalUtil.removeAllXpectComments(before);
			after = XpectCommentRemovalUtil.removeAllXpectComments(after);

			expectation.assertDiffEquals(before, after);
		}
	}

	/**
	 * Searches for the proposal matching to selected.
	 *
	 * Throws exception if there are more then one or no proposals matching 'selected' found.
	 */
	private ICompletionProposal exactlyMatchingProposal(RegionWithCursor offset,
			N4ContentAssistProcessorTestBuilder fixture,
			String selected) {
		ICompletionProposal[] computeCompletionProposals = allProposalsAt(offset, fixture);
		List<ICompletionProposal> candidates = Arrays.stream(computeCompletionProposals)
				.filter(proposal -> proposal.getDisplayString().contains(selected)).collect(Collectors.toList());

		if (candidates.size() > 1) {
			throw new AssertionFailedError(
					"The selection of contentassist is not precise enough more then one assist matched the selection '"
							+
							selected + "': " + asString2(candidates) + " Please be more precise.");
		} else if (candidates.size() < 1) {
			throw new AssertionFailedError(
					"No content assist matching the selection '" + selected + "' found. Available are " +
							asString2(Arrays.asList(computeCompletionProposals)));
		}

		ICompletionProposal proposal = candidates.get(0);
		return proposal;
	}

	private ICompletionProposal[] allProposalsAt(RegionWithCursor offset, N4ContentAssistProcessorTestBuilder fixture) {
		AtomicReference<ICompletionProposal[]> w = new AtomicReference<>();

		Display.getDefault().syncExec(() -> {

			try {
				w.set(fixture.computeCompletionProposals(offset
						.getGlobalCursorOffset()));
			} catch (Exception e) {
				logger.warn("Cannot compute Completion Proposals", e);
			}
		});

		return w.get();
	}

	private String applyProposal(ICompletionProposal proposal, IXtextDocument document) {
		return document.modify(
				new IUnitOfWork<String, XtextResource>() {
					@Override
					public String exec(XtextResource state) throws Exception {
						state.setValidationDisabled(false);
						if (!(proposal instanceof TemplateProposal)) {
							proposal.apply(document);
						}
						return document.get();
					}
				});
	}

	private XtextResource getNewResource(String content, URI uri) {
		IParseResult parseResult = parser.parse(new StringReader(content));
		ResourceSet resourceSet2 = resourceSetProvider.get();
		XtextResource newXtextResource = resourceProvider.get();
		newXtextResource.setURI(uri);
		resourceSet2.getResources().add(newXtextResource);
		newXtextResource.setParseResult(parseResult);
		return newXtextResource;
	}

}
