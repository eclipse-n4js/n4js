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
package org.eclipse.n4js.xpect.methods;

import java.util.List;

import org.eclipse.n4js.formatting2.N4JSFormatterPreferenceKeys;
import org.eclipse.n4js.xpect.config.Preference;
import org.eclipse.n4js.xpect.config.Preferences;
import org.eclipse.n4js.xpect.methods.FormatterXpectMethod.TextRegionAccessFactory;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.XpectInvocation;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.expectation.impl.TargetSyntaxSupport;
import org.eclipse.xpect.parameter.IStatementRelatedRegion;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.setup.ISetupInitializer;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.state.Creates;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.util.XtextTargetSyntaxSupport;
import org.eclipse.xtext.formatting2.FormatterPreferenceKeys;
import org.eclipse.xtext.formatting2.FormatterRequest;
import org.eclipse.xtext.formatting2.IFormatter2;
import org.eclipse.xtext.formatting2.regionaccess.ILineRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess;
import org.eclipse.xtext.formatting2.regionaccess.ITextReplacement;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;
import org.eclipse.xtext.formatting2.regionaccess.TextRegionAccessBuilder;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.ExceptionAcceptor;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 *
 */
@SuppressWarnings("restriction")
@XpectImport({ TextRegionAccessFactory.class, Preferences.class, Preference.class })
public class FormatterXpectMethod {

	/**
	 * Allow all tests per file to reuse the ITextRegionAccess-Object for performance reasons.
	 */
	@XpectSetupFactory
	public static class TextRegionAccessFactory {

		private final ITextRegionAccess regionAccess;

		/***/
		public TextRegionAccessFactory(@ThisResource XtextResource resource) {
			TextRegionAccessBuilder builder = resource.getResourceServiceProvider().get(TextRegionAccessBuilder.class);
			regionAccess = builder.forNodeModel(resource).create();
		}

		/***/
		@Creates
		public ITextRegionAccess getTextRegionAccess() {
			return regionAccess;
		}
	}

	@Inject
	private Provider<IFormatter2> formatterProvider;

	@Inject
	private Provider<FormatterRequest> formatterRequestProvider;

	/***/
	@Xpect
	@ParameterParser(syntax = "arg1=INT")
	public void formattedLines(
			@StringExpectation(whitespaceSensitive = true) IStringExpectation exp,
			int lines, // arg1
			XpectInvocation inv,
			TargetSyntaxSupport syntax,
			ITextRegionAccess reg,
			ISetupInitializer<Preferences> prefInit) {

		XtextResource resource = ((XtextTargetSyntaxSupport) syntax).getResource();

		IStatementRelatedRegion region2 = inv.getExtendedRegion();
		int end = region2.getOffset() + region2.getLength();
		ILeafNode node = NodeModelUtils.findLeafNodeAtOffset(resource.getParseResult().getRootNode(), end);

		int offset = node.getTotalEndOffset();
		ITextSegment region = getRegionForLines(reg, offset, lines);

		Preferences prefs = new Preferences();
		// First put some defaults
		prefs.put(N4JSFormatterPreferenceKeys.FORMAT_PARENTHESIS, true);
		prefs.put(FormatterPreferenceKeys.lineSeparator, "\n");
		// Second init from concrete tests - will override defaults.
		prefInit.initialize(prefs);

		IFormatter2 formatter = formatterProvider.get();

		FormatterRequest request = formatterRequestProvider.get();
		request.setTextRegionAccess(reg);
		request.setExceptionHandler(ExceptionAcceptor.THROWING);
		// needed in case a check like this will be implemented:
		// org.eclipse.xtext.testing.formatter.FormatterTester.assertAllWhitespaceIsFormatted()
		request.setAllowIdentityEdits(true);
		request.setFormatUndefinedHiddenRegionsOnly(false);
		request.addRegion(region);
		request.setPreferences(prefs);

		List<ITextReplacement> replacements = formatter.format(request);
		String fmt = reg.getRewriter().renderToString(replacements);
		ITextSegment doc = reg.regionForDocument();
		int endIndex = region.getEndOffset() + (fmt.length() - doc.getLength()) - 1;
		String selection = fmt.substring(region.getOffset(), endIndex);
		exp.assertEquals(selection);
	}

	private ITextSegment getRegionForLines(ITextRegionAccess regions, int offset, int lines) {
		ILineRegion firstLine = regions.regionForLineAtOffset(offset);
		ILineRegion lastLine = firstLine;
		for (int i = 1; i < lines; i++) {
			ILineRegion next = lastLine.getNextLine();
			if (next != null) {
				lastLine = next;
			} else {
				break;
			}
		}
		int firstLineOffset = firstLine.getOffset();
		ITextSegment region = regions.regionForOffset(firstLineOffset, (lastLine.getEndOffset() - firstLineOffset) + 1);
		return region;
	}

}
