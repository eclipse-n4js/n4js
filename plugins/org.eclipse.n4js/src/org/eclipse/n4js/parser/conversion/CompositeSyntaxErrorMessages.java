/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.parser.conversion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.util.Strings;

/**
 * Utility to attach multiple syntax error messages to the same node.
 *
 * The node model does only support exactly one syntax error information per node. This is a limitation that bites us
 * where we want to add more than one issue from a value converter for the given node.
 *
 * The problem is solved by packing multiple error messages into the user data of a single composite error message. The
 * conversion rules for this features are implemented here by means of
 * {@link #toSyntaxErrorMessage(N4JSValueConverterWithValueException, Function)} and
 * {@link #toDiagnostic(SyntaxErrorMessage, Consumer)}.
 */
public class CompositeSyntaxErrorMessages {

	private static final String CODE = CompositeSyntaxErrorMessages.class.getName();

	/**
	 * Convert the given N4JSValueConverterWithValueException to a SyntaxErrorMessage.
	 */
	public static SyntaxErrorMessage toSyntaxErrorMessage(
			N4JSValueConverterWithValueException vce,
			Function<? super N4JSValueConverterWithValueException, ? extends SyntaxErrorMessage> delegate) {
		if (vce.getSuppressed().length == 0) {
			return delegate.apply(vce);
		}
		List<String> encoded = new ArrayList<>();
		SyntaxErrorMessage first = delegate.apply(vce);
		encode(first, encoded);
		for (Throwable t : vce.getSuppressed()) {
			if (t instanceof N4JSValueConverterWithValueException) {
				SyntaxErrorMessage next = delegate.apply((N4JSValueConverterWithValueException) t);
				encode(next, encoded);
			}
		}
		return new SyntaxErrorMessage(first.getMessage(), CODE, encoded.toArray(String[]::new));
	}

	private static void encode(SyntaxErrorMessage message, List<String> result) {
		result.add(message.getMessage());
		result.add(message.getIssueCode());
		String[] issueData = message.getIssueData();
		if (issueData != null && issueData.length > 0) {
			result.add(Strings.pack(issueData));
		} else {
			result.add("");
		}
	}

	/**
	 * Unpack the encoded nested messages or process with the default logic.
	 */
	public static void toDiagnostic(SyntaxErrorMessage syntaxErrorMessage,
			Consumer<? super SyntaxErrorMessage> delegate) {
		if (CODE.equals(syntaxErrorMessage.getIssueCode())) {
			String[] encodedMessages = syntaxErrorMessage.getIssueData();
			for (int messageIndex = 0; messageIndex < encodedMessages.length; messageIndex += 3) {
				String message = encodedMessages[messageIndex];
				String issueCode = encodedMessages[messageIndex + 1];
				String encodedIssueData = encodedMessages[messageIndex + 2];
				String[] issueData = "".equals(encodedIssueData) ? null : Strings.unpack(encodedIssueData);
				delegate.accept(new SyntaxErrorMessage(message, issueCode, issueData));
			}
		} else {
			delegate.accept(syntaxErrorMessage);
		}
	}

}
