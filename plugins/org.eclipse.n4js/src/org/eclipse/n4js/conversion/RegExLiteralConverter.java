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
package org.eclipse.n4js.conversion;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.conversion.ValueConverterUtils.StringConverterResult;
import org.eclipse.n4js.regex.regularExpression.CharacterClassEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.CharacterClassRange;
import org.eclipse.n4js.regex.regularExpression.ControlLetterEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.DecimalEscapeSequence;
import org.eclipse.n4js.regex.regularExpression.Group;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.conversion.ValueConverterWithValueException;
import org.eclipse.xtext.conversion.impl.AbstractValueConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A value converter that validates regular expression literals and converts them to strings and vice versa.
 */
@Singleton
public class RegExLiteralConverter extends AbstractValueConverter<String> {

	/**
	 * The issue code for bogus regular expression literals.
	 */
	public static final String ISSUE_CODE = "RegExLiteralConverter.bogus.regex";

	/**
	 * A {@link ValueConverterWithValueException} that indicates an erroneous regular expression literal.
	 */
	public static class BogusRegExLiteralException extends N4JSValueConverterWithValueException {

		private static final long serialVersionUID = 1L;

		/**
		 * Create a new exception for the given node with the recovered value.
		 */
		public BogusRegExLiteralException(String message, INode node, String value, int offset, int length) {
			super(message, ISSUE_CODE, node, offset, length, value, null);
		}

	}

	private IParser regexParser;
	private final IResourceServiceProvider.Registry serviceProviders;

	/**
	 * Creates a new literal converter that obtains an {@link IParser} for regular expressions from the given registry.
	 */
	@Inject
	public RegExLiteralConverter(IResourceServiceProvider.Registry serviceProviders) {
		this.serviceProviders = serviceProviders;
	}

	@Override
	public String toString(String value) {
		return value;
	}

	private IParser getRegexParser() {
		if (regexParser == null) {
			// no need for sync since we can also use a new regexParser if concurrent access happens by accident
			IResourceServiceProvider serviceProvider = serviceProviders.getResourceServiceProvider(URI
					.createURI("a.regex"));
			regexParser = serviceProvider.get(IParser.class);
		}
		return regexParser;
	}

	@Override
	public String toValue(String string, INode node) {
		String escaped = convertFromJS(string, node);
		IParseResult parseResult = getRegexParser().parse(new StringReader(escaped));
		Iterable<INode> syntaxErrors = parseResult.getSyntaxErrors();
		Iterator<INode> iterator = syntaxErrors.iterator();
		INode issueNode = null;
		boolean hasErrors = false;
		while (iterator.hasNext()) {
			INode syntaxError = iterator.next();
			String message = syntaxError.getSyntaxErrorMessage().getMessage();
			if (message.contains("'<EOF>' expecting '/'")) {
				hasErrors = true;
				break;
			}
			if (issueNode == null)
				issueNode = syntaxError;
		}
		if (hasErrors) {
			throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
					IssueCodes.VCO_REGEX_INVALID, node, string, null);
		}
		if (issueNode != null) {
			throw new BogusRegExLiteralException(issueNode.getSyntaxErrorMessage().getMessage(), node, string,
					issueNode.getOffset(), issueNode.getLength());
		}
		RegularExpressionLiteral literal = (RegularExpressionLiteral) parseResult.getRootASTElement();
		boolean hasUnicodeFlag = literal.getFlags().getFlags().contains("u");
		TreeIterator<EObject> regExIterator = literal.eAllContents();
		while (regExIterator.hasNext()) {
			EObject element = regExIterator.next();
			if (hasUnicodeFlag) {
				if (element instanceof DecimalEscapeSequence) {
					throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
							IssueCodes.VCO_REGEX_INVALID, node, string, null);
				} else if (element instanceof ControlLetterEscapeSequence) {
					if (((ControlLetterEscapeSequence) element).getSequence().length() <= 2) {
						throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
								IssueCodes.VCO_REGEX_INVALID, node, string, null);
					}
				} else if (element instanceof CharacterClassRange) {
					CharacterClassRange casted = (CharacterClassRange) element;
					if (casted.getLeft() instanceof CharacterClassEscapeSequence
							|| casted.getRight() instanceof CharacterClassEscapeSequence) {
						throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
								IssueCodes.VCO_REGEX_INVALID, node, string, null);
					}
				}
			}
			if (element instanceof Group) {
				Group group = (Group) element;
				if (group.getName() != null) {
					List<INode> nodesForFeature = NodeModelUtils.findNodesForFeature(group,
							RegularExpressionPackage.Literals.GROUP__NAME);
					INode nameNode = nodesForFeature.get(0);
					throw new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_NAMED_GROUP(),
							IssueCodes.VCO_REGEX_NAMED_GROUP, node, nameNode.getOffset(), nameNode.getLength(),
							string, null);
				}
			}
		}
		return string;
	}

	private static String convertFromJS(String jsString, INode node) {
		StringConverterResult result = ValueConverterUtils.convertFromEscapedString(jsString, false, true, false, null);
		if (result.hasError()) {
			throw new N4JSValueConverterWithValueException(
					IssueCodes.getMessageForVCO_REGEX_ILLEGAL_ESCAPE(jsString),
					IssueCodes.VCO_REGEX_ILLEGAL_ESCAPE, node, result.getValue(), null);
		}
		return result.getValue();
	}
}
