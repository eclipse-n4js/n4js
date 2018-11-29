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
package org.eclipse.n4js.tests.contentAssist

import org.junit.Ignore
import org.junit.Test

/**
 */
@SuppressWarnings("deprecation")
class TypeReferenceContentAssistPluginUITest extends AbstractN4JSContentAssistPluginUITest {

	@Test def void testTypeProposals_ignoreCase() {
		newBuilder().append("var varName: my").assertText("MyFirstClass", "MySecondClass");
	}

	@Test def void testTypeProposals_importAdded() {
		newBuilder().append("var x: My").assertProposal("MyFirstClass").withDisplayString('MyFirstClass - path/Libs').apply.expectContent('''
			import {MyFirstClass} from "path/Libs";
			var x: MyFirstClass''')
	}

	@Test def void testTypeProposals_namespaceImport() {
		newBuilder().append('''
			import * as L from 'path/Libs'

			var varName: L.MyF<|>
		''').assertProposalAtCursor("MyFirstClass").withDisplayString('MyFirstClass - path/Libs').apply.expectContent('''
			import * as L from 'path/Libs'

			var varName: L.MyFirstClass''');
	}

	@Test def void testTypeProposals_wildcardImport2() {
		newBuilder().append('''
			import * as L from 'path/Libs'

			var varName: MyF<|>
		''').assertProposalAtCursor("MyFirstClass").withDisplayString('MyFirstClass - path/Libs').apply.expectContent('''
			import * as L from 'path/Libs'

			var varName: L.MyFirstClass''');
	}

	@Test def void testTypeProposals_importAdjusted() {
		newBuilder().append('''
			import {MySecondClass} from 'path/Libs'

			var varName: MyF<|>
		''').assertProposalAtCursor("MyFirstClass").withDisplayString('MyFirstClass - path/Libs').apply.expectContent(
// original expectation:
//		'''
//			import { MySecondClass , MyFirstClass } from 'path/Libs'
//
//			var varName: MyFirstClass<|>
//		'''
// new expectation after disabling ImportRewriter#enhanceExistingImportDeclaration(ImportDeclaration,QualifiedName,String,MultiTextEdit):
		'''
			import {MySecondClass} from 'path/Libs'
			import {MyFirstClass} from "path/Libs";

			var varName: MyFirstClass'''
		);
	}

	@Ignore("One comment is lost by the serializer: in front of 'from'")
	@Test def void testTypeProposals_commentsPreserved() {
		newBuilder().append('''
			import /* a */ { /* b */ MySecondClass /* c */ } /* d */ from 'path/Libs'

			var varName: MyF<|>
		''').assertProposalAtCursor("MyFirstClass").withDisplayString('MyFirstClass - path.Libs').apply.expectContent('''
			import /* a */ { /* b */ MySecondClass /* c */ , MyFirstClass } /* d */ from 'path/Libs'

			var varName: MyFirstClass<|>
		''');
	}

	@Test def void testTypeProposals_camelCase() {
		newBuilder().append('''
			/* some comment */
			var varName: MLiFi''').applyProposal("path.MoreLibs.MoreLibFirstClass").expectContent('''
			/* some comment */
			import {MoreLibFirstClass} from "path/MoreLibs";
			var varName: MoreLibFirstClass''')
	}

	@Test def void testTypeProposals_secondImportAdded() {
		newBuilder().append('''
			import {MoreLibFirstClass} from "path/MoreLibs"

			var varName: MyF''').applyProposal("path.Libs.MyFirstClass").expectContent('''
			import {MoreLibFirstClass} from "path/MoreLibs"
			import {MyFirstClass} from "path/Libs";

			var varName: MyFirstClass''')
	}

	@Test def void testTypeProposals_secondImportAddedAfterHiddenTokens() {
		newBuilder().append('''
			import {MoreLibFirstClass} from "path/MoreLibs" /* some comment */

			var varName: MyF''').applyProposal("path.Libs.MyFirstClass").expectContent('''
			import {MoreLibFirstClass} from "path/MoreLibs" /* some comment */
			import {MyFirstClass} from "path/Libs";

			var varName: MyFirstClass''')
	}

	@Test def void testTypeProposals_secondImportAddedWithMultipleEmptyLines() {
		newBuilder().append('''
			import {MoreLibFirstClass} from "path/MoreLibs"


			var varName: MyF''').applyProposal("path.Libs.MyFirstClass").expectContent('''
			import {MoreLibFirstClass} from "path/MoreLibs"
			import {MyFirstClass} from "path/Libs";


			var varName: MyFirstClass''')
	}

	@Test def void testTypeProposals_secondImportAddedBetweenImportDeclarations() {
		val prefix = "import {MoreLibFirstClass} from \"path/MoreLibs\" /* trailing comment */" + "\n" +
			"\n" +
		 	"var varName: MyF";

		val model = prefix + "\n" +
			"\n" +
			"/* leading comment */import {MoreLibSecondClass} from \"path/MoreLibs\" /* trailing comment */"

		val expected = "import {MoreLibFirstClass} from \"path/MoreLibs\" /* trailing comment */" + "\n" +
			"import {MyFirstClass} from \"path/Libs\"" + ";" +"\n" +
			"\n" +
			"var varName: MyFirstClass" + "\n" +
			"\n" +
			"/* leading comment */import {MoreLibSecondClass} from \"path/MoreLibs\" /* trailing comment */"

		newBuilder().append(model)
			.applyProposal(prefix.length, "path.Libs.MyFirstClass")
			.expectContent(expected)
	}

	@Test def void testTypeProposals_aliasProposed() {
		newBuilder().append("import {MyFirstClass as MyRenamedClass} from 'path/Libs' var varName: my")
			.assertText("MyRenamedClass", "MyFirstClass", "MySecondClass");
	}

	@Test def void testTypeProposals_aliasExplained() {
		newBuilder().append("import {MyFirstClass as MyRenamedClass} from 'path/Libs' var varName: my")
			.assertProposal("MyRenamedClass")
			.withDisplayString('MyRenamedClass - path/Libs alias for MyFirstClass')
	}
}
