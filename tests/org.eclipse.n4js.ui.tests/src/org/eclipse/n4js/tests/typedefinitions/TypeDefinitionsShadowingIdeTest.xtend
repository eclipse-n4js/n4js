package org.eclipse.n4js.tests.typedefinitions;

import java.io.File
import java.util.List
import org.eclipse.core.runtime.CoreException
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.junit.Test

/**
 * Simple plugin test that imports client, definition and implementation projects into the workspace and checks that the
 * type definition shadowing works as intended.
 */
// converted from TypeDefinitionsShadowingPluginTest
public class TypeDefinitionsShadowingIdeTest extends ConvertedIdeTest {

	private static final String PROBANDS = "probands";
	private static final String PROBANDS_SUBFOLDER = "type-definitions";

	private static final String POSITIVE_FIXTURE_FOLDER = "simple-positive";
	private static final String NEGATIVE_FIXTURE_FOLDER = "simple-negative";

	/**
	 * Positive test case.
	 *
	 * Imports client, definition and implementation project and expects none of the projects to raise any compilation
	 * issues.
	 *
	 * The client project imports both module 'A' and 'B'. For 'A', the definition project provides type information,
	 * for 'B' it uses a dynamic namespace import, since the definition project does not provide any type information on
	 * it.
	 */
	@Test
	def void testValidTypeDefinitionsShadowing() throws CoreException {
		importProband(new File(new File(PROBANDS, PROBANDS_SUBFOLDER), POSITIVE_FIXTURE_FOLDER),
			List.of(N4JSGlobals.N4JS_RUNTIME));
		cleanBuildAndWait();
		assertNoIssues();
	}

	/**
	 * Negative test case.
	 *
	 * Imports client, definition and implementation project that declare an invalid type definitions configuration.
	 *
	 * More specifically, the "definesPackage" property of the definition project does not point to the intended
	 * implementation project. As a consequence, the client project cannot make use of any type information on the
	 * implementation project.
	 */
	@Test
	def void testInvalidTypeDefinitionsShadowing() throws CoreException {
		importProband(new File(new File(PROBANDS, PROBANDS_SUBFOLDER), NEGATIVE_FIXTURE_FOLDER),
			List.of(N4JSGlobals.N4JS_RUNTIME));
		cleanBuildAndWait();
		assertIssues(
			"Client" -> #[
				"(Error, [1:8 - 1:9], Import of A cannot be resolved.)",
				"(Error, [5:14 - 5:15], Couldn't resolve reference to IdentifiableElement 'A'.)"
			]
		);
	}
}
