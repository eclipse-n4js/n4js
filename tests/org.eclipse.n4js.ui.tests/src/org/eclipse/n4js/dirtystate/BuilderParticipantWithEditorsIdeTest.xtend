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

import org.eclipse.n4js.dirtystate.testdata.CaseSensitiveTestFiles
import org.eclipse.n4js.dirtystate.testdata.EnumTestFiles
import org.eclipse.n4js.dirtystate.testdata.InheritanceTestFiles
import org.eclipse.n4js.dirtystate.testdata.MemberTestFiles
import org.eclipse.n4js.dirtystate.testdata.RoleTestFiles
import org.eclipse.n4js.dirtystate.testdata.StaticTestFiles
import org.eclipse.n4js.dirtystate.testdata.TestFiles
import org.eclipse.n4js.dirtystate.testdata.TransitiveInheritMemberTestFiles
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.junit.Test

/**
 * tests if the dirty state manager creates and removes error markers at affected resources when another resource breaks
 * references while editing
 */
// converted from BuilderParticipantPluginUITest
public class BuilderParticipantWithEditorsIdeTest extends AbstractIdeTest {

	// @formatter:off
	/**
	 *
	 * 01. create Class1 file with having Class1.field0 -> should have no errors
	 * 02. create Class0 file with referencing Class1.field0 -> should have no errors as Class1 already created
	 * 03. rename Class1.field0 in Class1.field23 without save
	 * 04. Class0 -> Class1.field0 should get error marker
	 * 05. rename Class1.field23 in Class1.field0 without save
	 * 06. Class0 -> Class1.field0 should have no error marker
	 */
	//@formatter:on
	@Test
	def void testReferenceBrokenToOtherClassesField() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			TestFiles.moduleFolder() + "/Class0" -> TestFiles.class0(),
			TestFiles.moduleFolder() + "/Class1" -> TestFiles.class1()
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// open editors of test files
		openFile("Class0");
		openFile("Class1");
		joinServerRequests();
		assertNoIssues();

		// change input of second editor so that reference is broken for content
		// in editor 1
		changeOpenedFile("Class1", TestFiles.class1After());
		joinServerRequests();
		// New content of editor 2 should be valid
		assertIssuesInModules("Class1" -> #[]);
		// Content of editor 1 should be broken, because now linking to a missing field 'field0' in Class1
		assertIssuesInModules("Class0" -> #["(Error, [7:32 - 7:38], Couldn't resolve reference to IdentifiableElement 'field0'.)"]);

		// resetting old content in editor 2
		changeOpenedFile("Class1", TestFiles.class1());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. Class0
	 * 02. Class1.field0
	 * 03. Class0 -> Class1.field0
	 * 04. delete Class1
	 * 05. Class0 -> Class1 import and Class1.field0 should get error marker
	 * 06. recreate Class1 file
	 * 07. Class0 should have no error markers
	 */
	//@formatter:on
	@Test
	def void testReferenceDeleted() throws Exception {
		// create project and test files
		testWorkspaceManager.createTestProjectOnDisk(
			TestFiles.moduleFolder() + "/Class0" -> TestFiles.class0(),
			TestFiles.moduleFolder() + "/Class1" -> TestFiles.class1()
		);
		startAndWaitForLspServer();

		// open editors of test files
		openFile("Class0");
		openFile("Class1");
		joinServerRequests();
		assertNoIssues();

		closeFile("Class1");
		deleteNonOpenedFile("Class1");
		joinServerRequests();
		// Content of editor for Class0 should be broken, because now linking to missing resource
		// Consequential errors are omitted, so there is no error reported for unknown field, as the receiver is of
		// unknown type
		assertIssuesInModules("Class0" -> #[
			"(Error, [3:23 - 3:40], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
			"(Error, [6:26 - 6:32], Couldn't resolve reference to Type 'Class1'.)"
		]);

		createFile(TestFiles.moduleFolder() + "/Class1", TestFiles.class1());
		joinServerRequests();
		// Content of editor for Class0 should be valid again, as Class1 has got the field with name 'field0' again
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. Class0 uses Class1 in require statement and in isa function
	 * 02. Class1 is deleted
	 * 05. Class0 should get error marker at require statement and at isa function
	 * 06. recreate Class1 file
	 * 07. Class0 should have no error markers
	 *
	 */
	//@formatter:on
	@Test
	def void testSuperClassDeleted() throws Exception {
		// create project and test files
		testWorkspaceManager.createTestProjectOnDisk(
			InheritanceTestFiles.inheritanceModule() + "/Parent" -> InheritanceTestFiles.Parent(),
			InheritanceTestFiles.inheritanceModule() + "/Child" -> InheritanceTestFiles.Child()
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// open editors of test files
		openFile("Parent");
		openFile("Child");
		joinServerRequests();
		assertNoIssues();

		closeFile("Parent");

		deleteNonOpenedFile("Parent");
		joinServerRequests();
		// Editor of child should have error markers
		assertIssues(
			"Child" -> #[
				"(Error, [0:46 - 0:66], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:34 - 1:53], Couldn't resolve reference to Type 'ParentObjectLiteral'.)",
				"(Error, [3:18 - 3:35], The method printlnToOverride must override or implement a method from a super class or interface.)"
			]
		);

		createFile(InheritanceTestFiles.inheritanceModule() + "/Parent", InheritanceTestFiles.Parent());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. CRole consumes BRole, BRole consumes ARole
	 * 02. BRole calls method of ARole, BRole calls method of BRole and a method of ARole
	 * 03. method of BRole is renamed
	 * 04. CRole should get error marker at call of B's method, call to A's method should be ok
	 * 05. method of BRole is renamed back
	 * 06. C should have no error markers
	 */
	//@formatter:on
	@Test
	def void testMethodInConsumedRoleInBetweenRenamed() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			RoleTestFiles.moduleFolder() + "/ARole" -> RoleTestFiles.roleA(),
			RoleTestFiles.moduleFolder() + "/BRole" -> RoleTestFiles.roleB(),
			RoleTestFiles.moduleFolder() + "/CRole" -> RoleTestFiles.roleC()
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// open editors of test files
		openFile("ARole");
		openFile("BRole");
		openFile("CRole");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("BRole", RoleTestFiles.roleBChanged());
		assertIssues(
			"CRole" -> #["(Error, [6:7 - 6:16], Couldn't resolve reference to IdentifiableElement 'myMethodB'.)"]
		);

		changeOpenedFile("BRole", RoleTestFiles.roleB());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. MyClassOne import MyVariableTwo, calls in chain
	 * 01a. method of variable type (MyClassTwo),
	 * 01b. method of type of this variable type method (MyRoleThree),
	 * 01c. MyRoleThree method (typed with MyInterfaceFour)
	 * 01d. finally a MyInterfaceFour's method
	 * 02. method of MyInterfaceFour is renamed
	 * 04. MyClassOne should get error marker at method call
	 * 05. method of MyInterfaceFour is renamed back
	 * 06. MyClassOne should have no error markers
	 *
	 * @throws Exception if creation of resources fails
	 */
	//@formatter:on
	@Test
	def void testMethodChainingWithRenamingLastOne() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			MemberTestFiles.moduleFolder() + "/MyInterfaceFour" -> MemberTestFiles.myInterfaceFour(),
			MemberTestFiles.moduleFolder() + "/MyRoleThree" -> MemberTestFiles.myRoleThree(),
			MemberTestFiles.moduleFolder() + "/MyClassTwo" -> MemberTestFiles.myClassTwo(),
			MemberTestFiles.moduleFolder() + "/MyVariableTwo" -> MemberTestFiles.myVariableTwo(),
			MemberTestFiles.moduleFolder() + "/MyClassOne" -> MemberTestFiles.myClassOne()
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// open editors of test files
		openFile("MyInterfaceFour");
		openFile("MyClassOne");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("MyInterfaceFour", MemberTestFiles.myInterfaceFourChanged());
		joinServerRequests();
		assertIssues("MyClassOne" -> #["(Error, [5:35 - 5:47], Couldn't resolve reference to IdentifiableElement 'myMethodFour'.)"]);

		changeOpenedFile("MyInterfaceFour", MemberTestFiles.myInterfaceFour());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. MyClassOne import MyVariableTwo, calls in chain
	 * 01a. method of variable type (MyClassTwo),
	 * 01b. method of type of this variable type method (MyRoleThree),
	 * 01c. MyRoleThree method (typed with MyInterfaceFour)
	 * 01d. finally a MyInterfaceFour's method
	 * 02. Creating files in an order that there are initial error markers as required files are not created yet
	 * 03. When all files have been created no file should have error markers
	 *
	 * @throws Exception when creating resources fails
	 */
	//@formatter:on
	@Test
	def void testRenamingMethodAccessedViaSubclass() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			TransitiveInheritMemberTestFiles.moduleFolder() + "/A" -> TransitiveInheritMemberTestFiles.A(),
			TransitiveInheritMemberTestFiles.moduleFolder() + "/B" -> TransitiveInheritMemberTestFiles.B(),
			TransitiveInheritMemberTestFiles.moduleFolder() + "/C" -> TransitiveInheritMemberTestFiles.C()
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// open editors of test files
		openFile("A");
		openFile("B");
		openFile("C");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("C", TransitiveInheritMemberTestFiles.CChanged());
		joinServerRequests();
		// Editor of A should have error markers because of missing method
		assertIssues("A" -> #["(Error, [5:19 - 5:28], Couldn't resolve reference to IdentifiableElement 'myMethodC'.)"]);

		changeOpenedFile("C", TransitiveInheritMemberTestFiles.C());
		joinServerRequests();
		// Editor of A should have no error markers because of method is again available
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. Caller calls callee methods, who just differs in lower/upper case
	 * 02. method of callee is renamed
	 * 04. Caller should get error marker at method call
	 * 05. method of Callee is renamed back
	 * 06. Caller should have no error markers
	 *
	 */
	//@formatter:on
	@Test
	def void testMethodCallWithCaseSensitiveMethodNames() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			CaseSensitiveTestFiles.moduleFolder() + "/CaseSensitiveCallee" -> CaseSensitiveTestFiles.callee(),
			CaseSensitiveTestFiles.moduleFolder() + "/CaseSensitiveCaller" -> CaseSensitiveTestFiles.caller()
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// open editors of test files
		openFile("CaseSensitiveCallee");
		openFile("CaseSensitiveCaller");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("CaseSensitiveCallee", CaseSensitiveTestFiles.calleeChanged());
		joinServerRequests();
		// Editor of Caller should have error markers because of missing method
		assertIssues(
			"CaseSensitiveCaller" -> #["(Error, [5:14 - 5:22], Couldn't resolve reference to IdentifiableElement 'mymethod'.)"]
		);

		changeOpenedFile("CaseSensitiveCallee", CaseSensitiveTestFiles.callee());
		joinServerRequests();
		// Editor of Caller should have no error markers because of method is again available
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. transitive chain of dependencies
	 * 02. static getter in 3rd file is renamed
	 * 03. first file should get error marker although it is not directly dependent on 3rd file
	 * 04. after renaming back, the error marker should be gone
	 *
	 */
	//@formatter:on
	@Test
	def void testStaticMethodCalls() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			StaticTestFiles.moduleFolder() + "/A" -> StaticTestFiles.A(),
			StaticTestFiles.moduleFolder() + "/B" -> StaticTestFiles.B(),
			StaticTestFiles.moduleFolder() + "/C" -> StaticTestFiles.C(),
			StaticTestFiles.moduleFolder() + "/D" -> StaticTestFiles.D()
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// open editors of test files
		openFile("A");
		openFile("C");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("C", StaticTestFiles.C_changed());
		joinServerRequests();
		// Editor of A should have error markers because of missing method
		assertIssues(
			"A" -> #["(Error, [6:6 - 6:7], The non-static member d cannot be accessed from a static context.)"]
		);

		changeOpenedFile("C", StaticTestFiles.C());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 * 01. class accesses literal from enumeration
	 * 02. literal is renamed without save with accessing class editor is closed
	 * 03. accessing class file should have no errors
	 * 04. enumeration editor is saved
	 * 05. accessing class file should have error
	 * 06. literal is renamed back
	 * 07. error at accessing class file should have been gone
	 * 08. literal is renamed with accessing class editor is open
	 * 09. accessing class should have error at literal access
	 * 10. literal is renamed back
	 * 11. error at accessing class should have been gone
	 */
	//@formatter:on
	@Test
	def void testEnumLiterals() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			EnumTestFiles.moduleFolder() + "/MyEnum" -> EnumTestFiles.myEnum(),
			EnumTestFiles.moduleFolder() + "/MyEnumUser" -> EnumTestFiles.myEnumUser()
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// open editors of test files
		openFile("MyEnum");
		openFile("MyEnumUser");
		joinServerRequests();
		assertNoIssues();

		closeFile("MyEnumUser");

		changeOpenedFile("MyEnum", EnumTestFiles.myEnum_changed());
		joinServerRequests();
		// File MyEnumUser with old literal should have no errors yet
		assertIssuesInModules("MyEnumUser" -> #[]);

		saveOpenedFile("MyEnum");
		joinServerRequests();
		// File MyEnumUser with old literal should have errors as editor now saved
		assertIssuesInModules("MyEnumUser" -> #["(Error, [5:22 - 5:25], Couldn't resolve reference to IdentifiableElement 'ONE'.)"]);

		changeOpenedFile("MyEnum", EnumTestFiles.myEnum());
		saveOpenedFile("MyEnum");
		joinServerRequests();
		// Error in MyEnumUser now gone
		assertIssuesInModules("MyEnumUser" -> #[]);

		// now again with MyEnumUser being open:
		openFile("MyEnumUser");
		joinServerRequests();
		assertNoIssues();

		changeOpenedFile("MyEnum", EnumTestFiles.myEnum_changed());
		joinServerRequests();
		// Editor of MyEnumUser should have error markers because of missing literal (even without saving)
		assertIssuesInModules("MyEnumUser" -> #["(Error, [5:22 - 5:25], Couldn't resolve reference to IdentifiableElement 'ONE'.)"]);
		
		changeOpenedFile("MyEnum", EnumTestFiles.myEnum());
		joinServerRequests();
		assertNoIssues();
	}
}
