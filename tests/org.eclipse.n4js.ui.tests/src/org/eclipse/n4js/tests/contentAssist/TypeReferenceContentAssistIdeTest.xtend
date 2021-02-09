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

import java.util.List
import org.eclipse.n4js.tests.utils.ConvertedCompletionIdeTest
import org.junit.Ignore
import org.junit.Test

/**
 */
@SuppressWarnings("deprecation")
// converted from TypeReferenceContentAssistPluginUITest
class TypeReferenceContentAssistIdeTest extends ConvertedCompletionIdeTest {

	/** Some default modules that export a number of classes for all tests. */
	override final List<Pair<String, String>> getDefaultTestProject() {
		return #[
			"path/Libs.n4js"  -> '''
				export public class MyFirstClass {}
				export public class MySecondClass {}
				class MyHiddenClass {}
			''',
			"path/MoreLibs.n4js"  -> '''
				export public class MoreLibFirstClass {}
				export public class MoreLibSecondClass {}
			'''
		];
	}

	@Test def void testTypeProposals_ignoreCase() {
		testAtCursor("var varName: my<|>", '''
			(MyFirstClass, Class, path/Libs, , , 00000, , , , ([0:13 - 0:15], MyFirstClass), [([0:0 - 0:0], import {MyFirstClass} from "path/Libs";
			)], [], , )
			(MySecondClass, Class, path/Libs, , , 00001, , , , ([0:13 - 0:15], MySecondClass), [([0:0 - 0:0], import {MySecondClass} from "path/Libs";
			)], [], , )
		''');
	}

	@Test def void testTypeProposals_importAdded() {
		testAtCursorWithApply('''
			var x: My<|>
		''', '''
			==>(MyFirstClass, Class, path/Libs, , , 00000, , , , ([0:7 - 0:9], MyFirstClass), [([0:0 - 0:0], import {MyFirstClass} from "path/Libs";
			)], [], , )
			(MySecondClass, Class, path/Libs, , , 00001, , , , ([0:7 - 0:9], MySecondClass), [([0:0 - 0:0], import {MySecondClass} from "path/Libs";
			)], [], , )
		''', '''
			import {MyFirstClass} from "path/Libs";
			var x: MyFirstClass
		''')
	}

	@Test def void testTypeProposals_namespaceImport() {
		testAtCursorWithApply('''
			import * as L from 'path/Libs'

			var varName: L.MyF<|>
		''', '''
			==>(MyFirstClass, Class, path/Libs, , , 00000, , , , ([2:15 - 2:18], MyFirstClass), [], [], , )
		''', '''
			import * as L from 'path/Libs'

			var varName: L.MyFirstClass
		''');
	}

	@Test def void testTypeProposals_wildcardImport2() {
		testAtCursorWithApply('''
			import * as L from 'path/Libs'

			var varName: MyF<|>
		''', '''
			==>(L.MyFirstClass, Class, path/Libs/MyFirstClass, , , 00000, , , , ([2:13 - 2:16], L.MyFirstClass), [], [], , )
		''', '''
			import * as L from 'path/Libs'

			var varName: L.MyFirstClass
		''');
	}

	@Test def void testTypeProposals_importAdjusted() {
		testAtCursorWithApply('''
			import {MySecondClass} from 'path/Libs'

			var varName: MyF<|>
		''', '''
			==>(MyFirstClass, Class, path/Libs, , , 00000, , , , ([2:13 - 2:16], MyFirstClass), [([0:39 - 0:39], 
			import {MyFirstClass} from "path/Libs";)], [], , )
		''',
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
		testAtCursorWithApply('''
			import /* a */ { /* b */ MySecondClass /* c */ } /* d */ from 'path/Libs'

			var varName: MyF<|>
		''', '''
			==>(MyFirstClass, Class, path/Libs, , , 00000, , , , ([2:13 - 2:16], MyFirstClass), [([0:73 - 0:73], 
			import {MyFirstClass} from "path/Libs";)], [], , )
		''', '''
			import /* a */ { /* b */ MySecondClass /* c */ , MyFirstClass } /* d */ from 'path/Libs'

			var varName: MyFirstClass
		''');
	}

	@Test def void testTypeProposals_camelCase() {
		testAtCursorWithApply('''
			/* some comment */
			var varName: MLiFi<|>
		''', '''
			==>(MoreLibFirstClass, Class, path/MoreLibs, , , 00000, , , , ([1:13 - 1:18], MoreLibFirstClass), [([0:18 - 0:18], 
			import {MoreLibFirstClass} from "path/MoreLibs";)], [], , )
		''', '''
			/* some comment */
			import {MoreLibFirstClass} from "path/MoreLibs";
			var varName: MoreLibFirstClass
		''')
	}

	@Test def void testTypeProposals_secondImportAdded() {
		testAtCursorWithApply('''
			import {MoreLibFirstClass} from "path/MoreLibs"

			var varName: MyF<|>
		''', '''
			==>(MyFirstClass, Class, path/Libs, , , 00000, , , , ([2:13 - 2:16], MyFirstClass), [([0:47 - 0:47], 
			import {MyFirstClass} from "path/Libs";)], [], , )
		''', '''
			import {MoreLibFirstClass} from "path/MoreLibs"
			import {MyFirstClass} from "path/Libs";

			var varName: MyFirstClass
		''')
	}

	@Test def void testTypeProposals_secondImportAddedAfterHiddenTokens() {
		testAtCursorWithApply('''
			import {MoreLibFirstClass} from "path/MoreLibs" /* some comment */

			var varName: MyF<|>
		''', '''
			==>(MyFirstClass, Class, path/Libs, , , 00000, , , , ([2:13 - 2:16], MyFirstClass), [([0:66 - 0:66], 
			import {MyFirstClass} from "path/Libs";)], [], , )
		''', '''
			import {MoreLibFirstClass} from "path/MoreLibs" /* some comment */
			import {MyFirstClass} from "path/Libs";

			var varName: MyFirstClass
		''')
	}

	@Test def void testTypeProposals_secondImportAddedWithMultipleEmptyLines() {
		testAtCursorWithApply('''
			import {MoreLibFirstClass} from "path/MoreLibs"



			var varName: MyF<|>
		''', '''
			==>(MyFirstClass, Class, path/Libs, , , 00000, , , , ([4:13 - 4:16], MyFirstClass), [([0:47 - 0:47], 
			import {MyFirstClass} from "path/Libs";)], [], , )
		''', '''
			import {MoreLibFirstClass} from "path/MoreLibs"
			import {MyFirstClass} from "path/Libs";



			var varName: MyFirstClass
		''')
	}

	@Test def void testTypeProposals_secondImportAddedBetweenImportDeclarations() {
		val prefix = "import {MoreLibFirstClass} from \"path/MoreLibs\" /* trailing comment */" + "\n" +
			"\n" +
		 	"var varName: MyF";

		val model = prefix + "<|>" + "\n" +
			"\n" +
			"/* leading comment */import {MoreLibSecondClass} from \"path/MoreLibs\" /* trailing comment */"

		val expected = "import {MoreLibFirstClass} from \"path/MoreLibs\" /* trailing comment */" + "\n" +
			"import {MyFirstClass} from \"path/Libs\"" + ";" +"\n" +
			"\n" +
			"var varName: MyFirstClass" + "\n" +
			"\n" +
			"/* leading comment */import {MoreLibSecondClass} from \"path/MoreLibs\" /* trailing comment */"

		testAtCursorWithApply(model, '''
			==>(MyFirstClass, Class, path/Libs, , , 00000, , , , ([2:13 - 2:16], MyFirstClass), [([0:70 - 0:70], 
			import {MyFirstClass} from "path/Libs";)], [], , )
		''' , expected)
	}

	@Test def void testTypeProposals_aliasProposed() {
		testAtCursor('''
			import {MyFirstClass as MyRenamedClass} from 'path/Libs'; var varName: my<|>
		''', '''
			(MyRenamedClass, Class, alias for path/Libs/MyFirstClass, , , 00000, , , , ([0:71 - 0:73], MyRenamedClass), [], [], , )
			(MySecondClass, Class, path/Libs, , , 00001, , , , ([0:71 - 0:73], MySecondClass), [([0:57 - 0:57], 
			import {MySecondClass} from "path/Libs";)], [], , )
		''');
	}
}
