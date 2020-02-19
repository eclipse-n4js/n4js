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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.xtext.testing.DefinitionTestConfiguration;

/**
 * Abstract test class for defintion protocol tests
 */
@SuppressWarnings("javadoc")
abstract public class AbstractDefinitionTest extends AbstractIdeTest<DefinitionTestConfiguration> {

	/** Call this method in a test */
	protected void test(DefinitionTestConfiguration dtc) throws Exception {
		test(dtc.getFilePath(), dtc.getModel(), dtc);
	}

	@Override
	protected void performTest(File root, Project project, DefinitionTestConfiguration dtc)
			throws InterruptedException, ExecutionException, URISyntaxException {
		TextDocumentPositionParams textDocumentPositionParams = new TextDocumentPositionParams();
		String completeFileUri = getFileUriFromModuleName(root, dtc.getFilePath()).toString();
		textDocumentPositionParams.setTextDocument(new TextDocumentIdentifier(completeFileUri));
		textDocumentPositionParams.setPosition(new Position(dtc.getLine(), dtc.getColumn()));
		CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definitionsFuture = languageServer
				.definition(textDocumentPositionParams);

		Either<List<? extends Location>, List<? extends LocationLink>> definitions = definitionsFuture.get();
		if (dtc.getAssertDefinitions() != null) {
			dtc.getAssertDefinitions().apply(definitions.getLeft());
		} else {
			String actualSignatureHelp = toString(definitions);
			assertEquals(dtc.getExpectedDefinitions().trim(), actualSignatureHelp.trim());
		}
	}

	protected String toString(Range range) {
		if (range == null) {
			return "[null]";
		}
		Position start = range.getStart();
		Position end = range.getEnd();
		String stringPosStr = start.getLine() + ":" + start.getCharacter();
		String endPosStr = end.getLine() + ":" + end.getCharacter();
		return "[" + stringPosStr + " - " + endPosStr + "]";
	}

	protected String toString(Either<List<? extends Location>, List<? extends LocationLink>> definitions)
			throws URISyntaxException {
		StringBuilder result = new StringBuilder();
		if (definitions.isLeft()) {
			List<? extends Location> locations = definitions.getLeft();
			for (Location location : locations) {
				if (location.getUri().startsWith(N4Scheme.SCHEME)) {
					result.append(location.getUri()).append(" ").append(toString(location.getRange())).append('\n');
				} else {
					String uri = getRoot().toPath().relativize(Path.of(new URI(location.getUri()))).toString();
					result.append(uri).append(" ").append(toString(location.getRange())).append('\n');
				}
			}
		} else {
			List<? extends LocationLink> locations = definitions.getRight();
			for (LocationLink location : locations) {
				result.append(location.getTargetUri()).append(" ").append(toString(location.getTargetRange()))
						.append('\n');
			}
		}
		return result.toString();
	}

}