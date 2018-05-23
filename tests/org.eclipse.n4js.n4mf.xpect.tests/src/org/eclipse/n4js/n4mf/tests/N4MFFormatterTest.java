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
package org.eclipse.n4js.n4mf.tests;

import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.n4mf.tests.N4MFFormatterTest.NullValidatorSetup;
import org.eclipse.xtext.formatting2.FormatterRequest;
import org.eclipse.xtext.formatting2.IFormatter2;
import org.eclipse.xtext.formatting2.regionaccess.ITextReplacement;
import org.eclipse.xtext.formatting2.regionaccess.TextRegionAccessBuilder;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.TextRegion;
import org.junit.runner.RunWith;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.runner.XpectTestFiles.FileRoot;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.setup.XtextStandaloneSetup;
import org.eclipse.xpect.xtext.lib.setup.XtextValidatingSetup;

import com.google.common.base.StandardSystemProperty;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 */
@RunWith(XpectRunner.class)
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "model/formatter", fileExtensions = "n4mf")
@XpectImport({ XtextStandaloneSetup.class, NullValidatorSetup.class })
@SuppressWarnings("restriction")
public class N4MFFormatterTest {

	/**
	 * Disable validation for formatter test so most combinations can be tested during formatting without the need to
	 * handle validation issues.
	 */
	@XpectSetupFactory
	public static class NullValidatorSetup extends XtextValidatingSetup {
		/***/
		public NullValidatorSetup(@ThisResource XtextResource resource) {
			super(resource);
		}

		@Override
		public void validate() {
			// do nothing
		}
	}

	@Inject
	private IFormatter2 formatter;

	@Inject
	private Provider<FormatterRequest> requestProvider;

	@Inject
	private Provider<TextRegionAccessBuilder> regionAccessBuilderProvider;

	/**
	 * @param expectation
	 *            the expected formatted code
	 * @param resource
	 *            the Xtext resource to be formatted
	 * @param offset
	 *            the optional start offset from where formatting should be applied
	 * @param to
	 *            the optional to offset to which formatting should be applied
	 */
	@ParameterParser(syntax = "('from' arg2=OFFSET 'to' arg3=OFFSET)?")
	@Xpect
	public void formatted(@StringExpectation(whitespaceSensitive = true) IStringExpectation expectation,
			@ThisResource XtextResource resource, int offset, int to) {
		ICompositeNode rootNode = resource.getParseResult().getRootNode();

		FormatterRequest request = requestProvider.get().setTextRegionAccess(
				regionAccessBuilderProvider.get().forNodeModel(resource).create());

		int totalLength = rootNode.getTotalLength();

		if (offset >= 0 && to > offset) {
			request.setRegions(Collections.singleton(new TextRegion(offset, to - offset)));
		} else {
			request.setRegions(Collections.singleton(new TextRegion(offset, totalLength - offset)));
		}

		List<ITextReplacement> replacements = formatter.format(request);
		String formatted = request.getTextRegionAccess().getRewriter().renderToString(replacements);

		if (offset >= 0 && to > offset) {
			formatted = formatted.substring(offset, formatted.length() - (totalLength - to));
		} else {
			formatted = formatted.substring(offset);
		}

		if (isUnixEnding()) {
			formatted = formatted.replaceAll("\r\n", "\n");
		} else if (isWindowsEnding()) {
			if (!rootNode.getText().contains("\r\n")) {
				formatted = formatted.replaceAll("\r\n", "\n");
			} else {
				formatted = formatted.replaceAll("(!\r)\n", "\r\n");
			}
		}
		expectation.assertEquals(formatted);
	}

	private static boolean isWindowsEnding() {
		String ls = StandardSystemProperty.LINE_SEPARATOR.value();
		return "\r\n".equals(ls);
	}

	private static boolean isUnixEnding() {
		String ls = StandardSystemProperty.LINE_SEPARATOR.value();
		return "\n".equals(ls);
	}
}
