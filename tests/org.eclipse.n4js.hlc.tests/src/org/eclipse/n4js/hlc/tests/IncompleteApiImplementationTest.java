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
package org.eclipse.n4js.hlc.tests;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 */
@Ignore("GH-1291") // when removing this @Ignore: check and maybe remove older @Ignore annotations below too!!
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncompleteApiImplementationTest extends AbstractCliCompileTest {

	File workspace;

	/**
	 * Prepare workspace.
	 */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace(DIR_TEST_DATA_SET__API_IMPL, true);

		compileAPI_And_API_Impl();
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	private static final String DIR_SRC_GEN = "src-gen";
	private static final String DIR_TEST_DATA_SET__API_IMPL = "api-impl--stubs-if-incomplete";
	private String proot;
	private String project_one_api_execution;
	private String pathTo_one_api_execution;

	/**
	 * Output-log-file of external Process. The file will be assigned given on the current test-method by calling
	 * {@link #logFile()} as first statement in each test-method.
	 */
	protected File outputLogFile = null;

	/**
	 * Setup workspace with api & api-impl & compile
	 *
	 */

	/** Upfront compilation when loading this test-class. */
	public void compileAPI_And_API_Impl() {
		proot = new File(workspace, PACKAGES).getAbsolutePath().toString();
		project_one_api_execution = "one.api.execution/";
		pathTo_one_api_execution = proot + "/" + project_one_api_execution;

		N4jscOptions options = COMPILE(workspace);
		CliCompileResult cliResult = n4jsc(options);
		assertEquals(cliResult.toString(), 69, cliResult.getTranspiledFilesCount());
	}

	String fileToExecute_direct(String filename) {
		return pathTo_one_api_execution + DIR_SRC_GEN + "/direct/" + filename;
	}

	String fileToExecute_fields(String filename) {
		return pathTo_one_api_execution + DIR_SRC_GEN + "/fields/" + filename;
	}

	String fileToExecute_if(String filename) {
		return pathTo_one_api_execution + DIR_SRC_GEN + "/if/" + filename;
	}

	String fileToExecute_routing(String filename) {
		return pathTo_one_api_execution + DIR_SRC_GEN + "/routing/" + filename;
	}

	String fileToExecute_var_and_fun(String filename) {
		return pathTo_one_api_execution + DIR_SRC_GEN + "/var_and_fun/" + filename;
	}

	String[] runArgs(String execPath) {
		return new String[] { "--projectlocations", proot,
				"--buildType", "dontcompile",
				"--runWith", "nodejs",
				"--run", execPath
		};
	}

	/**
	 * Should be called as first line in test methods.
	 *
	 * Creates an log-file in the {@link #workspace}-folder based on the callers Class/Methodname. something like
	 * "target/org.eclipse.n4js.hlc.test.N4jscSingleFileCompileIT.testHelp.log"
	 */
	protected void logFile() {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		String name = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ".log";
		outputLogFile = new File(workspace, name);
	}

	/** testing correct behavior of untouched elements. */
	@Test
	public void testImplemented_members() {
		String fileToRunName = fileToExecute_direct("Exec_implemented_members.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js\n"
				+ "OK: holds not undefined\n"
				+ "OK: holds not undefined";
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testMissing_field_in_class() {
		String fileToRunName = fileToExecute_direct("Exec_missing_field_in_class.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testMissing_getter_in_class() {
		String fileToRunName = fileToExecute_direct("Exec_missing_getter_in_class.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testMissing_method_in_class() {
		String fileToRunName = fileToExecute_direct("Exec_missing_field_in_class.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";
		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testMissing_method2_in_class() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_missing_method2_in_class.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testMissing_setter_in_class() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_missing_setter_in_class.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	// Enums
	// ++ ++ ++++++++ + + ++++ +++ + ++ +

	/** */
	@Test
	public void testEnums_Literal_in_existing_Enum() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js" + "\n" +
				"OK: holds not undefined" + "\n" +
				"OK: holds not undefined" + "\n" +
				"OK: holds not undefined";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE_1510_Enums_Literal_in_existing_Enum.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	@Ignore("IDE-1574 decide on Enum-strategy.")
	public void testEnums_literal_in_missing_Enum() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE_1510_Enums_literal_in_missing_Enum.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	@Ignore("IDE-1574 decide on Enum-strategy.")
	public void testEnums_literal_in_missing_EnumSB() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE_1510_Enums_literal_in_missing_EnumSB.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	@Ignore("IDE-1574 decide on Enum-strategy.")
	public void testEnums_missing_Literal_in_existing_Enum() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE_1510_Enums_missing_Literal_in_existing_Enum.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	@Ignore("IDE-1574 decide on Enum-strategy.")
	public void testEnums_missing_Literal_in_existing_EnumSB() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE_1510_Enums_missing_Literal_in_existing_EnumSB.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testEnums_normal_existing_EnumSB() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js" + "\n" +
				"OK: holds not undefined" + "\n" +
				"OK: holds not undefined" + "\n" +
				"OK: holds not undefined";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE_1510_Enums_normal_existing_EnumSB.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	// Interfaces
	// ++ ++ ++++++++ + + ++++ +++ + ++ +

	/** */
	@Test
	@Ignore("IDE-1576 requires mix-in of stub")
	public void testInterfaces_provided_get_missing() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_provided_get_missing.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInterfaces_provided_get() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js" + "\n" +
				"OK: holds not undefined";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_provided_get.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	@Ignore("IDE-1576 requires mix-in of stub")
	public void testInterfaces_provided_method_missing() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_provided_method_missing.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInterfaces_provided_method() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js" + "\n" +
				"OK: holds not undefined";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_provided_method.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInterfaces_provided_set_missing() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_provided_set_missing.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInterfaces_provided_set() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_provided_set.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInterfaces_static_method_missing() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_static_method_missing.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInterfaces_static_method() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js" + "\n" +
				"OK: holds not undefined";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_static_method.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInterfaces_static_getter_missing() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_static_getter_missing.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInterfaces_static_setter_missing() {
		String expectedString = "Loaded Implementation one.x.impl::p.A.n4js";

		String fileToRunName = fileToExecute_direct("Exec_AT_IDE-1510_Interfaces_static_setter_missing.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	// Interfaces - mixed in member stubs
	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	/** accumulative testing of combinations. */
	@Test
	public void testConsumed_Members_of_Missing_Inteface() {
		String expectedString = "Loaded Implementation one.x.impl::p.IF.n4js";

		String fileToRunName = fileToExecute_if("Exec_AT_IDE-1510_Consumed_Members_of_Missing_Inteface.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/**
	 * Testing the most simple case where client-code extends an API-interface with an default-initialized field (IA.f)
	 * which is actually missing from the concrete implementation. Unfortunately fields behave very different form
	 * getter/setter/methods, hence, in client code the generated get/set stub cannot be known and a raw-property access
	 * on the JS-Object will be obtained. This results in access to 'undefined'
	 */
	@Ignore("Client side access to missing mixed-in-fields cannot be detected.")
	@Test
	public void testConsumed_Members_of_Missing_Inteface_single_case() {
		String expectedString = "Loaded Implementation one.x.impl::p.IF.n4js";

		String fileToRunName = fileToExecute_if(
				"Exec_AT_IDE-1510_Consumed_Members_of_Missing_Inteface_single_case.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	// global function
	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	/** */
	@Test
	public void testFunction_test_function() {
		String expectedString = "Loaded Implementation one.x.impl::p.VarFun.n4js";

		String fileToRunName = fileToExecute_var_and_fun("Exec_AT_IDE-1510_Variable_And_Function_test_function.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	// global variable
	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	/** */
	@Ignore("Variables not yet supported by project comparison. Results in 'module cannot be loaded.'")
	@Test
	public void testVariable_global_variable() {
		String expectedString = "Loaded Implementation one.x.impl::p.VarFun.n4js";

		String fileToRunName = fileToExecute_var_and_fun(
				"Exec_AT_IDE-1510_Variable_And_Function_test_global_variable.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	// routing
	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	/** */
	@Test
	public void testSubclass_missing_inherited() {
		String expectedString = "Loaded Implementation one.x.impl::p.StubRoute.n4js";

		String fileToRunName = fileToExecute_routing("Exec_AT_IDE-1510_Subclass_missing_inherited.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testSubclass_normal_inherited() {
		String expectedString = "Loaded Implementation one.x.impl::p.StubRoute.n4js";

		String fileToRunName = fileToExecute_routing("Exec_AT_IDE-1510_Subclass_normal_inherited.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInternal_impl_Subclass_UsingDirectImplementation() {
		String expectedString = "Loaded Implementation one.x.impl::p.IF.n4js" + "\n"
				+ "Loaded Implementation one.x.impl::p.IFuser.n4js" + "\n"
				+ "OK: holds not undefined";

		String fileToRunName = fileToExecute_routing(
				"Exec_AT_IDE-1510_Internal_impl_Subclass_UsingDirectImplementation.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testInternal_impl_Subclass_UsingIndirectImplementation() {
		String expectedString = "Loaded Implementation one.x.impl::p.IF.n4js" + "\n"
				+ "Loaded Implementation one.x.impl::p.IFuser.n4js" + "\n"
				+ "OK: holds not undefined";

		String fileToRunName = fileToExecute_routing(
				"Exec_AT_IDE-1510_Internal_impl_Subclass_UsingIndirectImplementation.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	// field versus get/set from supertype.
	// ++ ++ ++++++++ + + ++++ +++ + ++ +
	/** */
	@Test
	public void testfield_vs_getset_1() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_2.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_2() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_3.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_3() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_4.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_4() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_5.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_5() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_1.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_6() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_6.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_7() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_7.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_8() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_8.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_9() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_9.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_A() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_A.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_B() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_B.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_C() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_C.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

	/** */
	@Test
	public void testfield_vs_getset_D() {
		String expectedString = "Loaded Implementation one.x.impl::fields.F.n4js";

		String fileToRunName = fileToExecute_fields("Exec_AT_IDEBUG-505_field_vs_getset_D.js");
		ProcessResult nodejsResult = runNodejs(workspace.toPath(), Path.of(fileToRunName));

		assertEquals(nodejsResult.toString(), expectedString, nodejsResult.getStdOut());
	}

}
