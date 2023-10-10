/**
 * Copyright (c) 2023 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TypeHierarchyItem;
import org.eclipse.lsp4j.TypeHierarchyPrepareParams;
import org.eclipse.lsp4j.TypeHierarchySubtypesParams;
import org.eclipse.lsp4j.TypeHierarchySupertypesParams;
import org.eclipse.n4js.ide.tests.helper.server.AbstractTypeHierarchyTest.TypeHierarchyConfig;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.utils.Strings;

/**
 * Abstract test class for type hierarchy protocol tests.
 */
abstract public class AbstractTypeHierarchyTest extends AbstractStructuredIdeTest<TypeHierarchyConfig> {

	
	public static class TypeHierarchyConfig {
		final String expectation;
		final Position position;
		final boolean testSubtype; // iff false: test super type

		TypeHierarchyConfig(String expectation, Position position, boolean testSubtype) {
			this.expectation = expectation;
			this.position = position;
			this.testSubtype = testSubtype;
		}

		TypeHierarchyPrepareParams getParams(AbstractIdeTest ideTest) {
			String uriStr = ideTest.getFileURIFromModuleName(DEFAULT_MODULE_NAME).toString();
			TypeHierarchyPrepareParams thpp = new TypeHierarchyPrepareParams();
			thpp.setPosition(position);
			thpp.setTextDocument(new TextDocumentIdentifier(uriStr));
			return thpp;
		}
	}

	/** Call this method in a test */
	protected void testSubtypesAtCursor(String content, String expectation) throws Exception {
		ContentAndPosition contentAndPosition = getContentAndPosition(content);
		Position position = new Position(contentAndPosition.line, contentAndPosition.column);
		test(contentAndPosition.content, new TypeHierarchyConfig(expectation, position, true));
	}

	/** Call this method in a test */
	protected void testSupertypesAtCursor(String content, String expectation) throws Exception {
		ContentAndPosition contentAndPosition = getContentAndPosition(content);
		Position position = new Position(contentAndPosition.line, contentAndPosition.column);
		test(contentAndPosition.content, new TypeHierarchyConfig(expectation, position, false));
	}

	@Override
	protected void performTest(Project project, String moduleName, TypeHierarchyConfig thc)
			throws InterruptedException, ExecutionException, URISyntaxException {

		TypeHierarchyPrepareParams thpp = thc.getParams(this);
		CompletableFuture<List<TypeHierarchyItem>> result1 = languageServer.prepareTypeHierarchy(thpp);
		List<TypeHierarchyItem> items = result1.get();
		assertEquals(1, items.size());

		List<TypeHierarchyItem> subtypes;
		if (thc.testSubtype) {
			TypeHierarchySubtypesParams thsp = new TypeHierarchySubtypesParams(items.get(0));
			CompletableFuture<List<TypeHierarchyItem>> result2 = languageServer.typeHierarchySubtypes(thsp);
			subtypes = result2.get();
		} else {
			TypeHierarchySupertypesParams thsp = new TypeHierarchySupertypesParams(items.get(0));
			CompletableFuture<List<TypeHierarchyItem>> result2 = languageServer.typeHierarchySupertypes(thsp);
			subtypes = result2.get();
		}

		String actualSymbols = Strings.join("\n", s -> getStringLSP4J().toString(s), subtypes);
		assertEquals(thc.expectation, actualSymbols);
	}

}