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
package org.eclipse.n4js.dirtystate

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.tests.resource.ModuleToModuleProxyPluginTest
import org.eclipse.n4js.utils.emf.ProxyResolvingResource
import org.junit.Test

/**
 * Tests a corner case of dependencies between resources. See also {@link ModuleToModuleProxyPluginTest}.
 */
// converted from ResourceLoadingCornerCasesPluginUITest
class ResourceLoadingCornerCasesIdeTest extends AbstractIdeTest {

	/**
	 * This tests the bug fix of IDE-2243 / IDE-2299.
	 * <p>
	 * To reproduce the bug and to make the following test fail, comment out the special handling in
	 * {@link N4JSResource#doResolveProxy(InternalEObject, EObject)}.
	 * <p>
	 * NOTE: as of GHOLD-141, in order to make this test fail, you also have to undo a bug fix in
	 * Xsemantics rule 'subtypeTypeTypeRef'. Replace this code:
	 * <pre>
	 * // we need the type of the two constructors (i.e. their signature)
	 * // DO NOT USE "var leftCtorRef = TypeUtils.createTypeRef(leftCtor)", because this would by-pass the handling
	 * // of forward references during AST traversal! Instead, obtain the type via the type judgment:
	 * G |- leftCtor : var TypeRef leftCtorRef;
	 * G |- rightCtor : var TypeRef rightCtorRef;
	 * </pre>
	 * with the following (old) code:
	 * <pre>
	 * val leftCtorRef = TypeUtils.createTypeRef(leftCtor);
	 * val rightCtorRef = TypeUtils.createTypeRef(rightCtor);
	 * </pre>
	 * <p>
	 * For further details, see {@link ProxyResolvingResource} and {@link ModuleToModuleProxyPluginTest}.
	 */
	@Test
	def void testModule2ModuleReferencesBug() {
		testWorkspaceManager.createTestProjectOnDisk(
			"C" -> '''
				export public class C {
					constructor() {}
				}
			''',
			"B" -> '''
				import { C } from "C"

				export public class B extends C {
				}
			''',
			"A" -> '''
				import { B } from "B"

				var ctor: constructor{B};
				ctor = B; // <-- before bug fix, error appeared here: "constructor{B} is not a subtype of constructor{B}." at "B"
			'''
		);
		startAndWaitForLspServer();
		assertNoErrors();

		openFile("A");
		// bug fix, part 1:
		// when commenting out the creation of m2m URIs in UserDataMapper#getDeserializedModuleFromDescription(),
		// next line should fail with an error in "A.n4js": "constructor{B} is not a subtype of constructor{B}."
		assertNoIssues();

		// we now open file C.n4js in a new, separate editor and change its contents (without saving!);
		// this will cause the resource of C.n4js to be unloaded in the ResourceSet of editorA (via dirty-state index)
		openFile("C");
		changeOpenedFile("C", '''
			export public class C {
				constructor() {}
			}
			export public class X {} // <-- adding a class in editor to cause a change in dirty-state index
		''');
		// note: we do *not* save editorC

		/* NOTE: after transition to LSP, can no longer check issues on disk for an opened file! */ // no errors on disk (also before bug fix)

		// bug fix, part 2:
		// when commenting out the creation of m2m URIs in N4JSResource#doUnload(),
		// next line should fail with "constructor{B} is not a subtype of constructor{B}."
		assertIssuesInModules("A" -> #[]); // editor for file A should *still* not have any errors
	}
}
