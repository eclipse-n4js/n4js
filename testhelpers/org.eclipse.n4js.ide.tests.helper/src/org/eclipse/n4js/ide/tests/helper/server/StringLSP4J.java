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
package org.eclipse.n4js.ide.tests.helper.server;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticRelatedInformation;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.ParameterInformation;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ResourceOperation;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureInformation;
import org.eclipse.lsp4j.TextDocumentEdit;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Tuple.Two;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.utils.Strings;

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
	public String toString1(Either<List<Either<String, MarkedString>>, MarkupContent> contents) {
		if (contents == null) {
			return "";
		}
		if (contents.isLeft()) {
			List<Either<String, MarkedString>> markedStrings = contents.getLeft();
			return Strings.toString(this::toString2, markedStrings);

		} else {
			return toString(contents.getRight());
		}
	}

	/** @return string for given element */
	public String toString2(Either<String, MarkedString> ms) {
		if (ms == null) {
			return "";
		}
		if (ms.isLeft()) {
			return ms.getLeft();
		} else {
			return toString(ms.getRight());
		}
	}

	/** @return string for given element */
	public String toString3(Either<Command, CodeAction> content) {
		if (content == null) {
			return "";
		}
		if (content.isLeft()) {
			Command command = content.getLeft();
			return toString(command);

		} else {
			CodeAction codeAction = content.getRight();
			return toString(codeAction);
		}
	}

	/** @return string for given element */
	public String toString4(Either<List<? extends Location>, List<? extends LocationLink>> definitions) {
		if (definitions == null) {
			return "";
		}
		if (definitions.isLeft()) {
			return Strings.join("\n", this::toString, definitions.getLeft());
		} else {
			return Strings.join("\n", this::toString, definitions.getRight());
		}
	}

	/** @return string for given element */
	public String toString5(Either<String, MarkupContent> strOrMarkupContent) {
		if (strOrMarkupContent == null) {
			return "";
		}
		if (strOrMarkupContent.isLeft()) {
			return strOrMarkupContent.getLeft();
		} else {
			return toString(strOrMarkupContent.getRight());
		}
	}

	/** @return string for given element */
	public String toString6(Either<TextDocumentEdit, ResourceOperation> documentChanges) {
		if (documentChanges == null) {
			return "";
		}
		if (documentChanges.isLeft()) {
			return toString(documentChanges.getLeft());
		} else {
			return toString(documentChanges.getRight());
		}
	}

	/** @return string for given element */
	public String toString7(Either<String, MarkupContent> documentation) {
		if (documentation == null) {
			return "";
		}
		if (documentation.isLeft()) {
			return documentation.getLeft();
		} else {
			return toString(documentation.getRight());
		}
	}

	/** @return string for given element */
	public String toString8(Either<String, Two<Integer, Integer>> documentation) {
		if (documentation == null) {
			return "";
		}
		if (documentation.isLeft()) {
			return documentation.getLeft();
		} else {
			Two<Integer, Integer> right = documentation.getRight();
			return "(" + right.getFirst() + "," + right.getSecond() + ")";
		}
	}

	/** @return string for given element */
	public String toString(ResourceOperation operation) {
		if (operation == null) {
			return "";
		}

		return operation.getKind();
	}

	/** @return string for given element */
	public String toString(TextDocumentEdit edit) {
		if (edit == null) {
			return "";
		}
		String str = Strings.join(", ",
				toString(edit.getTextDocument()),
				Strings.toString(this::toString, edit.getEdits()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(VersionedTextDocumentIdentifier textDocument) {
		if (textDocument == null) {
			return "";
		}
		String str = Strings.join(", ",
				textDocument.getUri(),
				textDocument.getVersion());

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(Hover hover) {
		if (hover == null) {
			return "";
		}
		String str = toString(hover.getRange()) + " " + toString1(hover.getContents());
		return str;
	}

	/** @return string for given element */
	public String toString(SignatureHelp signatureHelp) {
		if (signatureHelp == null) {
			return "";
		}

		String str = Strings.join(", ",
				signatureHelp.getActiveSignature(),
				signatureHelp.getActiveParameter(),
				Strings.toString(this::toString, signatureHelp.getSignatures()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(SignatureInformation sigInfo) {
		if (sigInfo == null) {
			return "";
		}
		String str = Strings.join(", ",
				sigInfo.getLabel(),
				Strings.toString(this::toString, sigInfo.getParameters()),
				toString7(sigInfo.getDocumentation()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(ParameterInformation parmInfo) {
		if (parmInfo == null) {
			return "";
		}
		String str = Strings.join(", ",
				toString8(parmInfo.getLabel()),
				toString7(parmInfo.getDocumentation()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(Command command) {
		if (command == null) {
			return "";
		}
		List<Object> argumentsRelativized = command.getArguments().stream()
				.map(this::relativizeIfURIString)
				.collect(Collectors.toList());
		String str = Strings.join(", ",
				command.getTitle(),
				command.getCommand(),
				Strings.toString(argumentsRelativized));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(CodeAction codeAction) {
		if (codeAction == null) {
			return "";
		}
		String str = Strings.join("\n",
				"title      : " + codeAction.getTitle(),
				"kind       : " + codeAction.getKind(),
				"command    : " + toString(codeAction.getCommand()),
				"diagnostics: " + Strings.join("\n", this::toString, codeAction.getDiagnostics()),
				"edit       : " + toString(codeAction.getEdit()));

		return str;
	}

	/** @return string for given element */
	public String toString(Diagnostic diagnostic) {
		if (diagnostic == null) {
			return "";
		}
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

	/** Like {@link #toString(Diagnostic)}, but shorter, ignoring code, source, and related information. */
	public String toStringShort(Diagnostic diagnostic) {
		if (diagnostic == null) {
			return "";
		}

		String str = Strings.join(", ",
				diagnostic.getSeverity(),
				toString(diagnostic.getRange()),
				diagnostic.getMessage());

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(DiagnosticRelatedInformation dri) {
		if (dri == null) {
			return "";
		}
		String str = Strings.join(", ",
				dri.getMessage(),
				toString(dri.getLocation()));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(WorkspaceEdit edit) {
		if (edit == null) {
			return "";
		}
		String str = Strings.join(", ",
				Strings.toString(this::toString6, edit.getDocumentChanges()),
				"\n   " + Strings.toString("\n   ", edit.getChanges(), this::relativize,
						(l) -> Strings.toString(this::toString, l)));

		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(Range range) {
		if (range == null) {
			return "";
		}
		Position start = range.getStart();
		Position end = range.getEnd();
		String stringPosStr = start.getLine() + ":" + start.getCharacter();
		String endPosStr = end.getLine() + ":" + end.getCharacter();
		return "[" + stringPosStr + " - " + endPosStr + "]";
	}

	/** @return string for given element */
	public String toString(Location location) {
		if (location == null) {
			return "";
		}
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
		if (locationLink == null) {
			return "";
		}
		String str = Strings.join(", ",
				locationLink.getTargetUri(),
				toString(locationLink.getTargetRange()));
		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(TextEdit edit) {
		if (edit == null) {
			return "";
		}
		String str = Strings.join(", ",
				toString(edit.getRange()),
				edit.getNewText());
		return "(" + str + ")";
	}

	/** @return string for given element */
	public String toString(MarkupContent markupContent) {
		if (markupContent == null) {
			return "";
		}
		return "[" + markupContent.getKind() + "] " + markupContent.getValue();
	}

	/** @return string for given element */
	public String toString(MarkedString markedStr) {
		if (markedStr == null) {
			return "";
		}
		return "[" + markedStr.getLanguage() + "] " + markedStr.getValue();
	}

	/** @return string for given element */
	public String toString(CompletionItem item) {
		if (item == null) {
			return "";
		}
		String str = Strings.join(", ",
				item.getLabel(),
				item.getKind(),
				item.getDetail(),
				toString5(item.getDocumentation()),
				item.getPreselect(),
				item.getSortText(),
				item.getFilterText(),
				item.getInsertText(),
				item.getInsertTextFormat(),
				toString(item.getTextEdit()),
				Strings.toString(this::toString, item.getAdditionalTextEdits()),
				Strings.toString(item.getCommitCharacters()),
				toString(item.getCommand()),
				Objects.toString(item.getData(), ""));
		return "(" + str + ")";
	}

	private String relativize(String uri) {
		return relativize(uri, true);
	}

	private Object relativizeIfURIString(Object obj) {
		return obj instanceof String ? relativize((String) obj, false) : obj;
	}

	private String relativize(String uri, boolean failFast) {
		try {
			URI uriuri = new URI(uri);
			return root.toPath().relativize(Path.of(uriuri)).toString();
		} catch (URISyntaxException e) {
			if (failFast) {
				throw new IllegalStateException("cannot relativize a string that is not an URI: " + uri, e);
			}
			return uri;
		}
	}
}
