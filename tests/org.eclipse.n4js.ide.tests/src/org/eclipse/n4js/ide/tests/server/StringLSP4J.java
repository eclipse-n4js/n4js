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
package org.eclipse.n4js.ide.tests.server;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticRelatedInformation;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureInformation;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.utils.Strings;
import org.junit.Assert;

/**
 * Utility to serialize LSP4J JSON results to be used in test expectations
 */
public class StringLSP4J {
	final File root;

	/** Constructor */
	public StringLSP4J(File root) {
		this.root = root;
	}

	/** @return string for given element */
	public String toString(Hover hover) {
		String str = toString(hover.getRange()) + " " + toString1(hover.getContents());
		return str;
	}

	/** @return string for given element */
	public String toString1(Either<List<Either<String, MarkedString>>, MarkupContent> contents) {
		if (contents.isLeft()) {
			List<Either<String, MarkedString>> markedStrings = contents.getLeft();
			String str = Strings.toString(this::toString2, markedStrings);

			return str;

		} else {
			MarkupContent markupContent = contents.getRight();
			return "[" + markupContent.getKind() + "] " + markupContent.getValue();
		}
	}

	/** @return string for given element */
	public String toString2(Either<String, MarkedString> ms) {
		if (ms.isLeft()) {
			return ms.getLeft();
		} else {
			MarkedString markedStr = ms.getRight();
			return "[" + markedStr.getLanguage() + "] " + markedStr.getValue();
		}
	}

	/** @return string for given element */
	public String toString(SignatureHelp signatureHelp) {
		Integer activeSignature = signatureHelp.getActiveSignature();
		List<SignatureInformation> signatures = signatureHelp.getSignatures();

		if (signatures.size() == 0) {
			Assert.assertNull(
					"Signature index is expected to be null when no signatures are available. Was: " + activeSignature,
					activeSignature);
			return "<empty>";
		}
		Assert.assertNotNull("Active signature index must not be null when signatures are available.", activeSignature);

		Integer activeParameter = signatureHelp.getActiveParameter();
		String param = (activeParameter == null) ? "<empty>"
				: signatures.get(activeSignature).getParameters().get(activeParameter).getLabel().getLeft();

		String allSignatureStr = signatures.stream().map(s -> s.getLabel()).reduce("", (a, b) -> a + " | " + b);
		return allSignatureStr + param;
	}

	/** @return string for given element */
	public String toString3(Either<Command, CodeAction> content) {
		if (content.isLeft()) {
			Command command = content.getLeft();
			return toString(command);

		} else {
			CodeAction codeAction = content.getRight();
			return toString(codeAction);
		}
	}

	/** @return string for given element */
	public String toString(Command command) {
		String str = "CMD:";
		str += Strings.join(", ",
				command.getTitle(),
				command.getCommand(),
				Strings.toString(command.getArguments()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(CodeAction codeAction) {
		String str = "CA:";

		str += Strings.join(", ",
				codeAction.getTitle(),
				codeAction.getKind(),
				Strings.toString(this::toString, codeAction.getDiagnostics()),
				toString(codeAction.getEdit()),
				toString(codeAction.getCommand()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(Diagnostic diagnostic) {
		String str = "CODE:";

		str += Strings.join(", ",
				diagnostic.getCode(),
				diagnostic.getSeverity(),
				diagnostic.getSource(),
				toString(diagnostic.getRange()),
				diagnostic.getMessage(),
				Strings.toString(this::toString, diagnostic.getRelatedInformation()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(DiagnosticRelatedInformation dri) {
		String str = Strings.join(", ",
				dri.getMessage(),
				toString(dri.getLocation()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(WorkspaceEdit edit) {
		String str = "" + edit.getDocumentChanges();

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(Range range) {
		if (range == null) {
			return "[null]";
		}
		Position start = range.getStart();
		Position end = range.getEnd();
		String stringPosStr = start.getLine() + ":" + start.getCharacter();
		String endPosStr = end.getLine() + ":" + end.getCharacter();
		return "[" + stringPosStr + " - " + endPosStr + "]";
	}

	/** @return string for given element */
	public String toString(Either<List<? extends Location>, List<? extends LocationLink>> definitions) {
		if (definitions.isLeft()) {
			return Strings.join("\n", this::toString, definitions.getLeft());
		} else {
			return Strings.join("\n", this::toString, definitions.getRight());
		}
	}

	/** @return string for given element */
	public String toString(Location location) {
		String uri = location.getUri().startsWith(N4Scheme.SCHEME)
				? location.getUri()
				: relativize(location.getUri());

		String str = Strings.join(", ",
				uri,
				toString(location.getRange()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(LocationLink locationLink) {
		String str = Strings.join(", ",
				locationLink.getTargetUri(),
				toString(locationLink.getTargetRange()));
		return "(" + str + ")";
	}

	private String relativize(String uri) {
		try {
			URI uriuri = new URI(uri);
			return root.toPath().relativize(Path.of(uriuri)).toString();
		} catch (URISyntaxException e) {
			return "ERROR: " + e.getMessage();
		}
	}
}
