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
import java.util.ArrayList;
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
import org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags;
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

	private static final String UNICODE_REGEX = "u";
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
		List<N4JSValueConverterWithValueException> errors = new ArrayList<>();
		while (iterator.hasNext()) {
			INode syntaxError = iterator.next();
			String message = syntaxError.getSyntaxErrorMessage().getMessage();
			if (message.contains("'<EOF>' expecting '/'")) {
				N4JSValueConverterWithValueException mainError = new N4JSValueConverterWithValueException(
						IssueCodes.getMessageForVCO_REGEX_INVALID(),
						IssueCodes.VCO_REGEX_INVALID, node, syntaxError.getOffset(), syntaxError.getLength(), string,
						null);
				errors.add(0, mainError);
			} else {
				errors.add(new BogusRegExLiteralException(message, node, string,
						syntaxError.getOffset(), syntaxError.getLength()));
			}
		}

		RegularExpressionLiteral literal = (RegularExpressionLiteral) parseResult.getRootASTElement();
		RegularExpressionFlags flags = literal.getFlags();
		boolean hasUnicodeFlag = flags != null && flags.getFlags().contains(UNICODE_REGEX);
		TreeIterator<EObject> regExIterator = literal.eAllContents();
		while (regExIterator.hasNext()) {
			EObject element = regExIterator.next();
			if (hasUnicodeFlag) {
				if (element instanceof DecimalEscapeSequence) {
					INode nodeForElement = NodeModelUtils.findActualNodeFor(element);
					errors.add(new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
							IssueCodes.VCO_REGEX_INVALID, node, nodeForElement.getOffset(), nodeForElement.getLength(),
							string, null));
				} else if (element instanceof ControlLetterEscapeSequence) {
					List<INode> nodesForFeature = NodeModelUtils.findNodesForFeature(element,
							RegularExpressionPackage.Literals.CONTROL_LETTER_ESCAPE_SEQUENCE__SEQUENCE);
					INode featureNode = nodesForFeature.get(0);
					if (((ControlLetterEscapeSequence) element).getSequence().length() <= 2) {
						errors.add(new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
								IssueCodes.VCO_REGEX_INVALID, node, featureNode.getOffset(), featureNode.getLength(),
								string, null));
					}
				} else if (element instanceof CharacterClassRange) {
					CharacterClassRange casted = (CharacterClassRange) element;
					if (casted.getLeft() instanceof CharacterClassEscapeSequence) {
						INode nodeForElement = NodeModelUtils.findActualNodeFor(casted.getLeft());
						errors.add(new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
								IssueCodes.VCO_REGEX_INVALID, node, nodeForElement.getOffset(),
								nodeForElement.getLength(), string, null));
					}
					if (casted.getRight() instanceof CharacterClassEscapeSequence) {
						INode nodeForElement = NodeModelUtils.findActualNodeFor(casted.getRight());
						errors.add(new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_INVALID(),
								IssueCodes.VCO_REGEX_INVALID, node, nodeForElement.getOffset(),
								nodeForElement.getLength(), string, null));
					}
				}
			}
			if (element instanceof Group) {
				Group group = (Group) element;
				if (group.getName() != null) {
					List<INode> nodesForFeature = NodeModelUtils.findNodesForFeature(group,
							RegularExpressionPackage.Literals.GROUP__NAME);
					INode nameNode = nodesForFeature.get(0);
					errors.add(new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_REGEX_NAMED_GROUP(),
							IssueCodes.VCO_REGEX_NAMED_GROUP, node, nameNode.getOffset(), nameNode.getLength(),
							string, null));
				}
			}
		}
		if (!errors.isEmpty()) {
			N4JSValueConverterWithValueException mainError = errors.remove(0);
			for (N4JSValueConverterWithValueException other : errors) {
				mainError.addSuppressed(other);
			}
			throw mainError;
		}
		return string;
	}

	private static String convertFromJS(String jsString, INode node) {
		StringConverterResult result = ValueConverterUtils.convertFromEscapedString(jsString, false, true, false, null);
		if (result.hasError()) {
			throw new N4JSValueConverterWithValueException(
					IssueCodes.getMessageForVCO_REGEX_ILLEGAL_ESCAPE(jsString),
					IssueCodes.VCO_REGEX_ILLEGAL_ESCAPE, node, result.getErrorOffset(), 1, result.getValue(), null);
		}
		return result.getValue();
	}
}
