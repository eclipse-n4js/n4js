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

import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.resource.ModuleToModuleProxyPluginTest
import org.eclipse.n4js.utils.emf.ProxyResolvingResource
import org.junit.Test
import org.eclipse.n4js.N4JSGlobals

import static org.junit.Assert.*
import org.eclipse.n4js.tests.util.EclipseUIUtils

/**
 * Tests a corner case of dependencies between resources. See also {@link ModuleToModuleProxyPluginTest}.
 */
class ResourceLoadingCornerCasesPluginUITest extends AbstractBuilderParticipantTest {

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
		val project = createJSProject("NastyBug")
		val srcFolder = configureProjectWithXtext(project)
		val projectDescriptionFile = project.getFile(N4JSGlobals.PACKAGE_JSON)
		assertMarkers("project description file (package.json) should have no errors", projectDescriptionFile, 0)

		val fileC = createTestFile(srcFolder, "C", '''
			export public class C {
				constructor() {}
			}
		''')
		val fileB = createTestFile(srcFolder, "B", '''
			import { C } from "C"

			export public class B extends C {
			}
		''')
		val fileA = createTestFile(srcFolder, "A", '''
			import { B } from "B"

			var ctor: constructor{B};
			ctor = B; // <-- before bug fix, error appeared here: "constructor{B} is not a subtype of constructor{B}." at "B"
		''')

		cleanBuild
		waitForAutoBuild

		assertMarkers("file A should have no errors", fileA, 0)
		assertMarkers("file B should have no errors", fileB, 0)
		assertMarkers("file C should have no errors", fileC, 0)

		val page = EclipseUIUtils.getActivePage()
		val editorA = openAndGetXtextEditor(fileA, page)
		val errorsA = getEditorValidationErrors(editorA)
		// bug fix, part 1:
		// when commenting out the creation of m2m URIs in UserDataMapper#getDeserializedModuleFromDescription(),
		// next line should fail with "constructor{B} is not a subtype of constructor{B}."
		assertEquals("editor for file A should not have any errors", #[], errorsA)

		// we now open file C.n4js in a new, separate editor and change its contents (without saving!);
		// this will cause the resource of C.n4js to be unloaded in the ResourceSet of editorA (via dirty-state index)
		val editorC = openAndGetXtextEditor(fileC, page)
		setDocumentContent("file C", fileC, editorC, '''
			export public class C {
				constructor() {}
			}
			export public class X {} // <-- adding a class in editor to cause a change in dirty-state index
		''');
		// note: we do *not* save editorC

		assertMarkers("file A should have no errors", fileA, 0) // no errors on disk (also before bug fix)

		val errorsA_part2 = getEditorValidationErrors(editorA)
		// bug fix, part 2:
		// when commenting out the creation of m2m URIs in N4JSResource#doUnload(),
		// next line should fail with "constructor{B} is not a subtype of constructor{B}."
		assertEquals("editor for file A should *still* not have any errors", #[], errorsA_part2)
	}
}
