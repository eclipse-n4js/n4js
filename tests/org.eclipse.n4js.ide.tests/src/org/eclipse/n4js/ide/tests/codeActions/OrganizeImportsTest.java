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
package org.eclipse.n4js.ide.tests.codeActions;

import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.AbstractOrganizeImportsTest;
import org.junit.Test;

/**
 * Tests for source action "organize imports". Covers all cases except addition of missing imports, which is covered in
 * {@link OrganizeImportsAddMissingTest}.
 */

public class OrganizeImportsTest extends AbstractOrganizeImportsTest {

	@Test
	public void testMinimal01() {
		test("", "");
	}

	@Test
	public void testMinimal02() {
		test("import   \"A\"", "import \"A\";");
	}

	@Test
	public void testRemoveUnused_named01() {
		test("""
				import {A03, A01} from "A";
				import {A02} from "A";
				A01,A03;
				""", """
				import {A01} from "A";
				import {A03} from "A";
				A01,A03;
				""");
	}

	@Test
	public void testRemoveUnused_named02() {
		test("""
				import {A03, A01} from "A";
				import {A02} from "A";
				A02,A03;
				""", """
				import {A02} from "A";
				import {A03} from "A";
				A02,A03;
				""");
	}

	@Test
	public void testRemoveUnused_namespace() {
		test("""
				import * as NA from "A";
				import * as NB from "B";
				NB.B01;
				""", """
				import * as NB from "B";
				NB.B01;
				""");
	}

	@Test
	public void testRemoveUnused_dynamic() {
		test("""
				import * as NP+ from "PlainJS";
				import * as NB from "B";
				NB.B01;
				""", """
				import * as NB from "B";
				NB.B01;
				""");
	}

	@Test
	public void testRemoveUnused_allGone() {
		test("""
				import {A03, A01} from "A";
				import * as N from "B";
				import {A02} from "A";
				// nothing used
				""", """
				// nothing used
				""");
	}

	@Test
	public void testRemoveUnresolved() {
		test("""
				import "OOPS";
				import {C01} from "C";
				import {X1} from "BAD1";
				import {X2} from "B";
				import {A01} from "A";
				import * as N from "BAD2"
				import * as N2+ from "BAD3"
				A01,C01;
				""", """
				import {A01} from "A";
				import {C01} from "C";
				A01,C01;
				""");
	}

	@Test
	public void testRemoveBroken() {
		test("""
				import {C01} from "C";
				import {X3} from "";
				import {A01} from "A";
				A01,C01;
				""", List.of(
				"(Error, [1:17 - 1:19], Couldn't resolve reference to TModule ''.)"), """
						import {A01} from "A";
						import {C01} from "C";
						A01,C01;
						""");
	}

	@Test
	public void testRemoveDuplicate_bare() {
		test("""
				import "C";
				import "B";
				import "A";
				import "B";
				""", """
				import "C";
				import "B";
				import "A";
				""");
	}

	@Test
	public void testRemoveDuplicate_named01() {
		test("""
				import {A01} from "A";
				import {A01} from "A";
				A01;
				""", """
				import {A01} from "A";
				A01;
				""");
	}

	@Test
	public void testRemoveDuplicate_named02() {
		test("""
				import {A01} from "A";
				import {A01 as Alias} from "A";
				A01;
				""", """
				import {A01} from "A";
				A01;
				""");
	}

	@Test
	public void testRemoveDuplicate_named03() {
		test("""
				import {A01} from "A";
				import {A01 as Alias} from "A";
				Alias;
				""", """
				import {A01 as Alias} from "A";
				Alias;
				""");
	}

	@Test
	public void testRemoveDuplicate_named_exception01() {
		// don't remove duplicate in case it is being used
		test("""
				import {A01} from "A";
				import {A01 as Alias} from "A";
				A01;Alias;
				""", """
				import {A01} from "A";
				import {A01 as Alias} from "A";
				A01;Alias;
				""");
	}

	@Test
	public void testRemoveDuplicate_named_exception02() {
		// don't remove duplicate in case it is being used
		test("""
				import {A01 as Alias1} from "A";
				import {A01 as Alias2} from "A";
				Alias1;Alias2;
				""", """
				import {A01 as Alias1} from "A";
				import {A01 as Alias2} from "A";
				Alias1;Alias2;
				""");
	}

	@Test
	public void testRemoveDuplicate_namespace01() {
		test("""
				import * as N from "A";
				import * as N from "A";
				N.A01;
				""", """
				import * as N from "A";
				N.A01;
				""");
	}

	@Test
	public void testRemoveDuplicate_namespace02() {
		test("""
				import * as N1 from "A";
				import * as N2 from "A";
				N1.A01;
				""", """
				import * as N1 from "A";
				N1.A01;
				""");
	}

	@Test
	public void testRemoveDuplicate_namespace_exception() {
		// don't remove duplicate in case it is being used
		test("""
				import * as N1 from "A";
				import * as N2 from "A";
				N1.A01;N2.A02;
				""", """
				import * as N1 from "A";
				import * as N2 from "A";
				N1.A01;N2.A02;
				""");
	}

	@Test
	public void testRemoveDuplicate_default01() {
		test("""
				import D from "Def";
				import D from "Def";
				D;
				""", """
				import D from "Def";
				D;
				""");
	}

	@Test
	public void testRemoveDuplicate_default02() {
		test("""
				import D1 from "Def";
				import D2 from "Def";
				D1;
				""", """
				import D1 from "Def";
				D1;
				""");
	}

	@Test
	public void testRemoveDuplicate_default_exception() {
		// don't remove duplicate in case it is being used
		test("""
				import D1 from "Def";
				import D2 from "Def";
				D1;D2;
				""", """
				import D1 from "Def";
				import D2 from "Def";
				D1;D2;
				""");
	}

	@Test
	public void testRemoveDuplicate_dynamic() {
		test("""
				import * as N+ from "PlainJS";
				import * as N+ from "PlainJS";
				N.XYZ;
				""", """
				import * as N+ from "PlainJS";
				N.XYZ;
				""");
	}

	@Test
	public void testRemoveDuplicate_dynamic_exception() {
		// don't remove duplicate in case it is being used
		test("""
				import * as N1+ from "PlainJS";
				import * as N2+ from "PlainJS";
				N1.XYZ;N2.XYZ;
				""", """
				import * as N1+ from "PlainJS";
				import * as N2+ from "PlainJS";
				N1.XYZ;N2.XYZ;
				""");
	}

	@Test
	public void testSorting() {
		test("""
				import * as NB from "B";
				import {A03, A01} from "A";
				import * as NP+ from "PlainJS";
				import {Def03} from "Def";
				import {A02} from "A";
				import * as NA from "A";
				import "C";
				import MyDef, {Def02} from "Def";
				import "A";
				import {Def01} from "Def";
				A01;A02;A03;NA.A04;NB.B01;Def01;Def02;Def03;MyDef;NP.XYZ;
				""", """
				import "C";
				import "A";
				import * as NA from "A";
				import {A01} from "A";
				import {A02} from "A";
				import {A03} from "A";
				import * as NB from "B";
				import MyDef from "Def";
				import {Def01} from "Def";
				import {Def02} from "Def";
				import {Def03} from "Def";
				import * as NP+ from "PlainJS";
				A01;A02;A03;NA.A04;NB.B01;Def01;Def02;Def03;MyDef;NP.XYZ;
				""");
	}

	@Test
	public void testSorting_moduleSpecifierFormDoesNotAffectSorting() {
		// pointing to the same module A with different module specifiers "A" and
		// "test-project/A" does not affect the sorting:
		test("""
				import {B01} from "B";
				import {A03} from "A";
				import {A01} from "A";
				import {A02} from "test-project/A";
				A01;A02;A03;B01;
				""", """
				import {A01} from "A";
				import {A02} from "test-project/A";
				import {A03} from "A";
				import {B01} from "B";
				A01;A02;A03;B01;
				""");
	}

	@Test
	public void testNormalizeBareImport() {
		test("""
				import {} from "A"
				""", """
				import "A";
				""");
	}

	@Test
	public void testKeepModuleSpecifier() {
		test("""
				import {A01} from "A";
				import {A02} from "test-project/A";
				A01;A02;
				""", """
				import {A01} from "A";
				import {A02} from "test-project/A";
				A01;A02;
				""");
	}

	@Test
	public void testEnvironment_comments01() {
		test("""
				// before
				import {A01} from "A";
				// in between
				import {A02} from "A";
				// after
				A01;A02;
				""", """
				// before
				import {A01} from "A";
				import {A02} from "A";
				// in between
				// after
				A01;A02;
				""");
	}

	@Test
	public void testEnvironment_comments02() {
		test("""
				// before
				import {A01} from "A";   // post first
				import {A02} from "A";   // post second
				import {A03} from "A";   // post last
				// after
				A01;A02;A03;
				""", """
				// before
				import {A01} from "A";
				import {A02} from "A";
				import {A03} from "A";
				   // post first
				   // post second
				   // post last
				// after
				A01;A02;A03;
				""");
	}

	@Test
	public void testEnvironment_comments03() {
		test("""
				// before
				/* pre first */   import {A01} from "A";   // post first
				/* pre second */   import {A02} from "A";   // post second
				/* pre last */   import {A03} from "A";   // post last
				// after
				A01;A02;A03;
				""", """
				// before
				import {A01} from "A";
				import {A02} from "A";
				import {A03} from "A";
				/* pre first */      // post first
				/* pre second */      // post second
				/* pre last */      // post last
				// after
				A01;A02;A03;
				""");
	}

	@Test
	public void testEnvironment_whitespace01() {
		test("""
				// before

				import {A01} from "A";


				import {A02} from "A";

				// after
				A01;A02;
				""", """
				// before

				import {A01} from "A";
				import {A02} from "A";

				// after
				A01;A02;
				""");
	}

	@Test
	public void testEnvironment_whitespace02() {
		test("""
				// before

				    import {A01} from "A";

				// in between

				    import {A02} from "A";

				// after
				A01;A02;
				""", """
				// before

				import {A01} from "A";
				import {A02} from "A";

				// in between


				// after
				A01;A02;
				""");
	}

	@Test
	public void testPullUpImportsToCorrectLocation() {
		test("""
				/* copyright header */

				@@StaticPolyfillAware

				console.log('this should be below the import');

				import {A01} from "A";

				A01;
				""", List.of(
				"(Warning, [6:0 - 6:22], The import statement should be placed on top of other statements.)"),
				/* NOTE: not supported yet (correct location would be between the annotation and console.log()) */
				"""
						/* copyright header */

						@@StaticPolyfillAware

						console.log('this should be below the import');

						import {A01} from "A";

						A01;
						""");
	}

	@Test
	public void testCombined() {
		test("""
				// some comment

				      import {B01} from "B";
				import {
				    A04} from "A"

				import "C";
				import {B02} from "B"
				import {  A03, A01 }    from   "A"
				// comment
				import *     as   N      from"C";

				A01;
				A03;
				A04;
				B01;
				// B02 is unused
				N.C01;
				""", """
				// some comment

				import "C";
				import {A01} from "A";
				import {A03} from "A";
				import {A04} from "A";
				import {B01} from "B";
				import * as N from "C";
				// comment

				A01;
				[...]
				""");
	}
}
