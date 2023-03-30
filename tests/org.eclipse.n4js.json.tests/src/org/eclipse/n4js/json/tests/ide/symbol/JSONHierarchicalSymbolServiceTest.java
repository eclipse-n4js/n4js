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
package org.eclipse.n4js.json.tests.ide.symbol;

import static org.junit.Assert.assertTrue;

import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.util.Ranges;
import org.eclipse.xtext.testing.AbstractLanguageServerTest;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Test;

import com.google.common.base.Strings;

/**
 * Tests for the outline tree
 */
@SuppressWarnings("javadoc")
public class JSONHierarchicalSymbolServiceTest extends AbstractLanguageServerTest {

	public JSONHierarchicalSymbolServiceTest() {
		super("json");
	}

	static Procedure1<InitializeParams> INITIALIZER = new Procedure1<>() {
		public void apply(InitializeParams ip) {
			ClientCapabilities cc = new ClientCapabilities();
			TextDocumentClientCapabilities tdcc = new TextDocumentClientCapabilities();
			DocumentSymbolCapabilities dsc = new DocumentSymbolCapabilities();
			dsc.setHierarchicalDocumentSymbolSupport(true);
			tdcc.setDocumentSymbol(dsc);
			cc.setTextDocument(tdcc);

			ip.setCapabilities(cc);
		}
	};

	@Test
	public void testDocumentSymbol_01() {

		testDocumentSymbol((config) -> {
			config.setInitializer(INITIALIZER);
			config.setModel("""
						{
						"name1" : "string",
						"name2" : true,
						"name3" : 123,
						"name4" : {
							"child" : null
						},
						"name5" : [ "value1", "value2" ]
					}
					""");
			config.setExpectedSymbols("""
					symbol "name1 : "string"" {
					    kind: Field
					    range: [[1, 1] .. [1, 19]]
					    selectionRange: [[1, 1] .. [1, 8]]
					    details:
					    deprecated: false
					}
					symbol "name2 : true" {
					    kind: Boolean
					    range: [[2, 1] .. [2, 15]]
					    selectionRange: [[2, 1] .. [2, 8]]
					    details:
					    deprecated: false
					}
					symbol "name3 : 123" {
					    kind: Number
					    range: [[3, 1] .. [3, 14]]
					    selectionRange: [[3, 1] .. [3, 8]]
					    details:
					    deprecated: false
					}
					symbol "name4" {
					    kind: Object
					    range: [[4, 1] .. [6, 2]]
					    selectionRange: [[4, 1] .. [4, 8]]
					    details:
					    deprecated: false
					    children: [
					        symbol "child : null" {
					            kind: Null
					            range: [[5, 2] .. [5, 16]]
					            selectionRange: [[5, 2] .. [5, 9]]
					            details:
					            deprecated: false
					        }
					    ]
					}
					symbol "name5" {
					    kind: Array
					    range: [[7, 1] .. [7, 33]]
					    selectionRange: [[7, 1] .. [7, 8]]
					    details:
					    deprecated: false
					    children: [
					        symbol ""value1"" {
					            kind: String
					            range: [[7, 13] .. [7, 21]]
					            selectionRange: [[7, 13] .. [7, 21]]
					            details:
					            deprecated: false
					        }
					        symbol ""value2"" {
					            kind: String
					            range: [[7, 23] .. [7, 31]]
					            selectionRange: [[7, 23] .. [7, 31]]
					            details:
					            deprecated: false
					        }
					    ]
					}
					""");
		});
	}

	@Override
	protected String _toExpectation(DocumentSymbol ds) {
		assertTrue("selectionRange must be contained in the range: %s".formatted(ds.toString()),
				Ranges.containsRange(ds.getRange(), ds.getSelectionRange()));

		// Use the symbolKind.name instead of the int value in the assertion
		String children = "";
		if (ds.getChildren() != null && !ds.getChildren().isEmpty()) {
			children += "\n    children: [";
			for (DocumentSymbol child : ds.getChildren()) {
				String childExpct = toExpectation(child).replace("\n", "\n        ");
				children += "\n        " + childExpct + "";
			}
			children += "\n    ]";
		}

		String detail = Strings.isNullOrEmpty(ds.getDetail()) ? "" : " " + ds.getDetail();
		return """
				symbol "%s" {
				    kind: %s
				    range: %s
				    selectionRange: %s
				    details:%s
				    deprecated: %s%s
				}""".formatted(
				ds.getName(),
				ds.getKind().name(),
				toExpectation(ds.getRange()),
				toExpectation(ds.getSelectionRange()),
				detail,
				DocumentSymbolSuppressDeprecationUtil.getDeprecated(ds),
				children);
	}

}