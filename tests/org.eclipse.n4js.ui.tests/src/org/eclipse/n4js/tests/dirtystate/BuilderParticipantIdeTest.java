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
package org.eclipse.n4js.tests.dirtystate;

import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.tests.dirtystate.testdata.CaseSensitiveTestFiles;
import org.eclipse.n4js.tests.dirtystate.testdata.EnumTestFiles;
import org.eclipse.n4js.tests.dirtystate.testdata.InheritanceTestFiles;
import org.eclipse.n4js.tests.dirtystate.testdata.InterfaceTestFiles;
import org.eclipse.n4js.tests.dirtystate.testdata.MemberTestFiles;
import org.eclipse.n4js.tests.dirtystate.testdata.RoleTestFiles;
import org.eclipse.n4js.tests.dirtystate.testdata.StaticTestFiles;
import org.eclipse.n4js.tests.dirtystate.testdata.TestFiles;
import org.eclipse.n4js.tests.dirtystate.testdata.TransitiveInheritMemberTestFiles;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * tests if the Xtext builder creates and removes error markers at affected resources when another resource breaks
 * references in them after saving
 */
// converted from BuilderParticipantPluginTest
public class BuilderParticipantIdeTest extends AbstractIdeTest {

	// @formatter:off
	/**
	 * <ol>
	 * <li>create Class1 file with having Class1.field0 -> should have no errors</li>
	 * <li>create Class0 file with referencing Class1.field0 -> should have no errors as Class1 already created</li>
	 * </ol>
	 */
	//@formatter:on
	@Test
	public void testNoErrorMarkersWhenReferencedFileIsCreatedBeforeReferencingFile() throws Exception {
		// create project and test files
		testWorkspaceManager.createTestProjectOnDisk();
		startAndWaitForLspServer();
		assertNoIssues();

		createFile(TestFiles.moduleFolder() + "/Class1", TestFiles.class1());
		joinServerRequests();
		assertNoIssues();

		createFile(TestFiles.moduleFolder() + "/Class0", TestFiles.class0());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. Create file with linking errors
	 * 02. Fix linking errors by creating the missing file
	 * 03. Assert not errors in workspace
	 */
	//@formatter:on
	@Test
	public void testNewFileFixesLinkingIssue() throws Exception {
		// create project and test files
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("pr0_0pa0/Class0", TestFiles.class0()));
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("Class0", List.of(
						"(Error, [3:23 - 3:40], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
						"(Error, [6:26 - 6:32], Couldn't resolve reference to Type 'Class1'.)")));

		createFile("pr0_0pa0/Class1", TestFiles.class1());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. B requires A, B calls method of A
	 * 02. method of A is renamed
	 * 03. B should get error marker at variable statement
	 * 04. method of A is renamed back
	 * 05. B should have no error markers
	 */
	//@formatter:on
	@Test
	public void testMethodInRequiredClassRenamed() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(InheritanceTestFiles.module1() + "/A", InheritanceTestFiles.A()),
				Pair.of(InheritanceTestFiles.module2() + "/B", InheritanceTestFiles.B()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", InheritanceTestFiles.AOtherMethodName());
		joinServerRequests();
		// One marker for using the old method name
		assertIssues2(Pair.of("B",
				List.of("(Error, [5:2 - 5:5], Couldn't resolve reference to IdentifiableElement 'foo'.)")));

		changeNonOpenedFile("A", InheritanceTestFiles.A());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. Class0 uses Class1 in require statement and in isa function
	 * 02. Class2 calls Class0.field0 in variable statement, field0 is defined in Class1
	 * 03. Class1.field0 is renamed in Class1.field23
	 * 04. Class2 should get error marker at field usage
	 * 05. Class1.field23 is renamed in Class1.field0
	 * 06. Class2 should have no error markers
	 */
	//@formatter:on
	@Test
	public void testMethodRenamedInSuperClassOfClassThatIsUsedToCallTheMethod() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(InheritanceTestFiles.module1() + "/A", InheritanceTestFiles.A()),
				Pair.of(InheritanceTestFiles.module2() + "/B", InheritanceTestFiles.B()),
				Pair.of(InheritanceTestFiles.module2() + "/C", InheritanceTestFiles.C()),
				Pair.of(InheritanceTestFiles.module1() + "/D", InheritanceTestFiles.D()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", InheritanceTestFiles.AOtherMethodName());
		joinServerRequests();
		// First marker for using old method name
		assertIssues2(
				Pair.of("B",
						List.of("(Error, [5:2 - 5:5], Couldn't resolve reference to IdentifiableElement 'foo'.)")),
				Pair.of("C",
						List.of("(Error, [5:2 - 5:6], Couldn't resolve reference to IdentifiableElement 'getB'.)")),
				Pair.of("D",
						List.of("(Error, [7:14 - 7:18], Couldn't resolve reference to IdentifiableElement 'getB'.)")));

		changeNonOpenedFile("A", InheritanceTestFiles.A());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. Brother uses Sister in require statement and as return type of a method, creates a Sister object and invokes method of Sister
	 * 02. Sister uses Brother in require statement and as return type of a method, creates a Brother object and invokes method of Brother
	 * 03. Sister method is renamed
	 * 04. Brother should get error marker at the usage of the old sister method
	 * 05. Sister should have no error markers (as Brother is not saved yet)
	 * 06. Sister method is renamed back
	 * 07. Brother should have no error markers
	 */
	//@formatter:on
	@SuppressWarnings("resource")
	@Test
	// TODO: while running there is a java.lang.IndexOutOfBoundsException: Index: 2, Size: 0 at at
	// org.eclipse.n4js.resource.N4JSResource.getEncodedURI(N4JSResource.java:446)
	public void testMutualDependency() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(TestFiles.mutualModuleFolder() + "/Brother", TestFiles.classBrother()),
				Pair.of(TestFiles.mutualModuleFolder() + "/Sister", TestFiles.classSister()),
				Pair.of(TestFiles.mutualModuleFolder() + "/Child", TestFiles.classChild()));
		startAndWaitForLspServer();
		// expected markers
		// Variable brother is used before it is declared
		assertIssues2(
				Pair.of("Sister", List.of("(Warning, [7:0 - 7:7], Variable brother is used before it is declared)")));

		changeNonOpenedFile("Sister", TestFiles.classSisterNew());
		joinServerRequests();
		// expected markers
		// Couldn't resolve reference to TMember 'getBrother'. at brother.getSister().getBrother
		// Couldn't resolve reference to TMember 'getBrother'. at brother = sister.getBrother;
		// expected markers
		// Variable brother is used before it is declared
		// Couldn't resolve reference to TMember 'getBrother'. at brother.getSister().getBrother
		// Couldn't resolve reference to TMember 'getBrother'. at var brotherChildAge =
		// sister.getBrother().getChild().age;
		// Consequential error, not reported anymore:
		// --> Couldn't resolve reference to TMember 'getChild'. at var brotherChildAge =
		// ... sister.getBrother().getChild().age;
		// Consequential error, not reported anymore:
		// --> Couldn't resolve reference to TMember 'age'. at var brotherChildAge = sister.getBrother().getChild().age;
		assertIssues2(
				Pair.of("Brother", List.of(
						"(Error, [7:20 - 7:30], Couldn't resolve reference to IdentifiableElement 'getBrother'.)",
						"(Error, [9:17 - 9:27], Couldn't resolve reference to IdentifiableElement 'getBrother'.)")),
				Pair.of("Sister", List.of(
						"(Warning, [7:0 - 7:7], Variable brother is used before it is declared)",
						"(Error, [7:20 - 7:30], Couldn't resolve reference to IdentifiableElement 'getBrother'.)",
						"(Error, [10:29 - 10:39], Couldn't resolve reference to IdentifiableElement 'getBrother'.)")));

		changeNonOpenedFile("Sister", TestFiles.classSister());
		joinServerRequests();

		// expected markers
		// Variable brother is used before it is declared
		assertIssues2(
				Pair.of("Sister", List.of("(Warning, [7:0 - 7:7], Variable brother is used before it is declared)")));
	}

	// @formatter:off
	/**
	 *
	 * 01. CRole consumes BRole, BRole consumes ARole
	 * 02. BRole calls method of ARole, BRole calls method of BRole and a method of ARole
	 * 03. method of ARole is renamed
	 * 04. BRole should get error marker at method call
	 * 05. CRole should get error marker at call of A's method, call to B's method should be ok
	 * 06. method of ARole is renamed back
	 * 07. B should have no error markers
	 * 08. C should have no error markers
	 */
	//@formatter:on
	@Test
	public void testMethodInConsumedRoleRenamed() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(RoleTestFiles.moduleFolder() + "/ARole", RoleTestFiles.roleA()),
				Pair.of(RoleTestFiles.moduleFolder() + "/BRole", RoleTestFiles.roleB()),
				Pair.of(RoleTestFiles.moduleFolder() + "/CRole", RoleTestFiles.roleC()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("ARole", RoleTestFiles.roleAChanged());
		joinServerRequests();
		assertIssues2(
				Pair.of("BRole", List
						.of("(Error, [4:7 - 4:16], Couldn't resolve reference to IdentifiableElement 'myMethodA'.)")),
				Pair.of("CRole", List
						.of("(Error, [5:7 - 5:16], Couldn't resolve reference to IdentifiableElement 'myMethodA'.)")));

		changeNonOpenedFile("ARole", RoleTestFiles.roleA());
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
	public void testMethodInConsumedRoleInBetweenRenamed() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(RoleTestFiles.moduleFolder() + "/ARole", RoleTestFiles.roleA()),
				Pair.of(RoleTestFiles.moduleFolder() + "/BRole", RoleTestFiles.roleB()),
				Pair.of(RoleTestFiles.moduleFolder() + "/CRole", RoleTestFiles.roleC()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("BRole", RoleTestFiles.roleBChanged());
		joinServerRequests();
		assertIssues2(Pair.of("CRole",
				List.of("(Error, [6:7 - 6:16], Couldn't resolve reference to IdentifiableElement 'myMethodB'.)")));

		changeNonOpenedFile("BRole", RoleTestFiles.roleB());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. CRole consumes BRole, BRole consumes ARole
	 * 02. BRole calls method of ARole, BRole calls method of BRole and a method of ARole
	 * 03. ARole as consumed Role is removed at BRole
	 * 04. CRole should get error marker at call of A's method, call to B's method should be ok
	 * 05. methodA is added to BRole
	 * 06. C should have no error markers
	 *
	 */
	//@formatter:on
	@Test
	public void testConsumedRoleInConsumedRoleInBetweenRemoved() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(RoleTestFiles.moduleFolder() + "/ARole", RoleTestFiles.roleA()),
				Pair.of(RoleTestFiles.moduleFolder() + "/BRole", RoleTestFiles.roleB()),
				Pair.of(RoleTestFiles.moduleFolder() + "/CRole", RoleTestFiles.roleC()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("BRole", RoleTestFiles.roleBChanged2());
		joinServerRequests();
		assertIssues2(
				Pair.of("BRole", List.of("(Warning, [0:9 - 0:14], The import of ARole is unused.)")),
				Pair.of("CRole", List
						.of("(Error, [6:7 - 6:16], Couldn't resolve reference to IdentifiableElement 'myMethodB'.)")));

		changeNonOpenedFile("BRole", RoleTestFiles.roleBChanged3());
		joinServerRequests();
		assertIssues2(
				Pair.of("BRole", List.of("(Warning, [0:9 - 0:14], The import of ARole is unused.)")));
	}

	// @formatter:off
	/**
	 *
	 * 01. class E extends class D and consumes role BRole
	 * 02. class E class methodB that is contained both in class D and role B
	 * 03. BRole as consumed Role is removed at E
	 * 04. E should get no error marker as now methodB of class D is consumed
	 * 05. methodB in D is renamed
	 * 06. E should get error marker
	 * 07. BRole as consumed Role is added at E again
	 * 08. E should have no error markers
	 *
	 */
	//@formatter:on
	@Test
	public void testSwitchFromRoleToClassMethod() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(RoleTestFiles.moduleFolder() + "/ARole", RoleTestFiles.roleA()),
				Pair.of(RoleTestFiles.moduleFolder() + "/BRole", RoleTestFiles.roleB()),
				Pair.of(RoleTestFiles.moduleFolder() + "/CRole", RoleTestFiles.roleC()),
				Pair.of(RoleTestFiles.moduleFolder() + "/D", RoleTestFiles.classD()),
				Pair.of(RoleTestFiles.moduleFolder() + "/E", RoleTestFiles.classE()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("E", RoleTestFiles.classEChanged());
		joinServerRequests();
		// File E should have no errors as now using method of D
		assertIssues2(Pair.of("E", List.of("(Warning, [1:9 - 1:14], The import of BRole is unused.)")));

		changeNonOpenedFile("D", RoleTestFiles.classDChanged());
		joinServerRequests();
		// File E should have errors after method not available anymore
		assertIssues2(
				Pair.of("E", List.of(
						"(Warning, [1:9 - 1:14], The import of BRole is unused.)",
						"(Error, [5:7 - 5:16], Couldn't resolve reference to IdentifiableElement 'myMethodB'.)")));

		changeNonOpenedFile("E", RoleTestFiles.classE());
		joinServerRequests();
		// File E should have no errors after method is available via role
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. ClassWithInterfaces implements InterfaceB, InterfaceB extends InterfaceA
	 * 02. ClassWithInterfaces calls method of InterfaceA and a method of InterfaceB
	 * 03. method of InterfaceA is renamed
	 * 04. ClassWithInterfaces should get error marker at method call
	 * 05. method of InterfaceA is renamed back
	 * 06. ClassWithInterfaces should have no error markers
	 *
	 */
	//@formatter:on
	@Test
	public void testMethodInImplementedInterfaceRenamed() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(InterfaceTestFiles.moduleFolder() + "/InterfaceA", InterfaceTestFiles.interfaceA()),
				Pair.of(InterfaceTestFiles.moduleFolder() + "/InterfaceB", InterfaceTestFiles.interfaceB()),
				Pair.of(InterfaceTestFiles.moduleFolder() + "/ClassWithInterfaces",
						InterfaceTestFiles.classWithInterfaces()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("InterfaceA", InterfaceTestFiles.interfaceAChanged());
		joinServerRequests();
		assertIssues2(Pair.of("ClassWithInterfaces",
				List.of("(Error, [4:7 - 4:15], Couldn't resolve reference to IdentifiableElement 'methodIA'.)")));

		changeNonOpenedFile("InterfaceA", InterfaceTestFiles.interfaceA());
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
	 */
	//@formatter:on
	@Test
	public void testMethodChainingWithRenamingLastOne() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(MemberTestFiles.moduleFolder() + "/MyInterfaceFour", MemberTestFiles.myInterfaceFour()),
				Pair.of(MemberTestFiles.moduleFolder() + "/MyRoleThree", MemberTestFiles.myRoleThree()),
				Pair.of(MemberTestFiles.moduleFolder() + "/MyClassTwo", MemberTestFiles.myClassTwo()),
				Pair.of(MemberTestFiles.moduleFolder() + "/MyVariableTwo", MemberTestFiles.myVariableTwo()),
				Pair.of(MemberTestFiles.moduleFolder() + "/MyClassOne", MemberTestFiles.myClassOne()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("MyInterfaceFour", MemberTestFiles.myInterfaceFourChanged());
		joinServerRequests();
		// File MyClassOne with other missing method name in chain should have errors
		assertIssues2(Pair.of("MyClassOne",
				List.of("(Error, [5:35 - 5:47], Couldn't resolve reference to IdentifiableElement 'myMethodFour'.)")));

		changeNonOpenedFile("MyInterfaceFour", MemberTestFiles.myInterfaceFour());
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
	public void testMethodChainingWithCreatingFilesInReverseOrder() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk();
		startAndWaitForLspServer();
		assertNoIssues();

		createFile(MemberTestFiles.moduleFolder() + "/MyClassOne", MemberTestFiles.myClassOne());
		joinServerRequests();
		createFile(MemberTestFiles.moduleFolder() + "/MyVariableTwo", MemberTestFiles.myVariableTwo());
		joinServerRequests();
		createFile(MemberTestFiles.moduleFolder() + "/MyClassTwo", MemberTestFiles.myClassTwo());
		joinServerRequests();
		createFile(MemberTestFiles.moduleFolder() + "/MyRoleThree", MemberTestFiles.myRoleThree());
		joinServerRequests();
		createFile(MemberTestFiles.moduleFolder() + "/MyInterfaceFour", MemberTestFiles.myInterfaceFour());
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
	 */
	//@formatter:on
	@Test
	public void testRenamingMethodAccessedViaSubclass() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(TransitiveInheritMemberTestFiles.moduleFolder() + "/C", TransitiveInheritMemberTestFiles.C()),
				Pair.of(TransitiveInheritMemberTestFiles.moduleFolder() + "/B", TransitiveInheritMemberTestFiles.B()),
				Pair.of(TransitiveInheritMemberTestFiles.moduleFolder() + "/A", TransitiveInheritMemberTestFiles.A()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("C", TransitiveInheritMemberTestFiles.CChanged());
		joinServerRequests();
		// File A with other missing method name in chain should have errors
		assertIssues2(Pair.of("A",
				List.of("(Error, [5:19 - 5:28], Couldn't resolve reference to IdentifiableElement 'myMethodC'.)")));

		changeNonOpenedFile("C", TransitiveInheritMemberTestFiles.C());
		joinServerRequests();
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
	public void testMethodCallWithCaseSensitiveMethodNames() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(CaseSensitiveTestFiles.moduleFolder() + "/CaseSensitiveCallee",
						CaseSensitiveTestFiles.callee()),
				Pair.of(CaseSensitiveTestFiles.moduleFolder() + "/CaseSensitiveCaller",
						CaseSensitiveTestFiles.caller()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("CaseSensitiveCallee", CaseSensitiveTestFiles.calleeChanged());
		joinServerRequests();
		// File Caller with other missing method name in chain should have errors
		assertIssues2(Pair.of("CaseSensitiveCaller",
				List.of("(Error, [5:14 - 5:22], Couldn't resolve reference to IdentifiableElement 'mymethod'.)")));

		changeNonOpenedFile("CaseSensitiveCallee", CaseSensitiveTestFiles.callee());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. static called member is changed to non-static -> errors at callers and errors gone, when changed back
	 * 02. static added to getter -> errors at callers and errors gone, when changed back
	 * 03a. static method overrides static method in super class, sub class is called in caller
	 * 03b. method in sub class renamed -> no errors expected, as now linking to method in super class
	 *
	 */
	//@formatter:on
	@Test
	public void testStaticMethodCalls() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(StaticTestFiles.moduleFolder() + "/Callee", StaticTestFiles.callee()),
				Pair.of(StaticTestFiles.moduleFolder() + "/SubCallee", StaticTestFiles.subCallee()),
				Pair.of(StaticTestFiles.moduleFolder() + "/Caller", StaticTestFiles.caller()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("Callee", StaticTestFiles.callee_changedStaticMember());
		joinServerRequests();
		// File SubCallee should have one error
		// File Caller with field not static anymore should have errors
		assertIssues2(
				Pair.of("SubCallee", List.of(
						"(Error, [9:7 - 9:20], The non-static member myStaticField cannot be accessed from a static context.)")),
				Pair.of("Caller", List.of(
						"(Error, [6:17 - 6:30], The non-static member myStaticField cannot be accessed from a static context.)",
						"(Error, [22:15 - 22:28], The non-static member myStaticField cannot be accessed from a static context.)")));

		changeNonOpenedFile("Callee", StaticTestFiles.callee());
		joinServerRequests();
		assertNoIssues();

		changeNonOpenedFile("Callee", StaticTestFiles.callee_changedNonStaticAccessors());
		joinServerRequests();
		// File Caller with getter static now should have errors (1 for static access in non-static context + 1 any is
		// not sub type of string)
		// File Callee should have one error, because of wrong this access
		assertIssues2(
				Pair.of("Caller", List.of(
						"(Error, [14:13 - 14:39], The accessor myPrivateNonStaticAccessor is write-only.)")),
				Pair.of("Callee", List.of(
						"(Error, [26:15 - 26:38], The non-static member myPrivateNonStaticField cannot be accessed from a static context.)")));

		changeNonOpenedFile("Callee", StaticTestFiles.callee());
		joinServerRequests();
		assertNoIssues();

		changeNonOpenedFile("SubCallee", StaticTestFiles.subCallee_changed());
		joinServerRequests();
		// File Caller should have no errors as now linking to super class method
		assertNoIssues();

		changeNonOpenedFile("SubCallee", StaticTestFiles.subCallee());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 * 01. class accesses literal from enumeration
	 * 02. literal is renamed
	 * 03. accessing class should have error at literal access
	 * 04. literal is renamed back
	 * 05. error in accessing class should have been gone
	 *
	 */
	//@formatter:on
	@Test
	public void testEnumLiterals() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(EnumTestFiles.moduleFolder() + "/MyEnum", EnumTestFiles.myEnum()),
				Pair.of(EnumTestFiles.moduleFolder() + "/MyEnumUser", EnumTestFiles.myEnumUser()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("MyEnum", EnumTestFiles.myEnum_changed());
		joinServerRequests();
		// File MyEnumUser with old literal should have errors
		assertIssues2(Pair.of("MyEnumUser",
				List.of("(Error, [5:22 - 5:25], Couldn't resolve reference to IdentifiableElement 'ONE'.)")));

		changeNonOpenedFile("MyEnum", EnumTestFiles.myEnum());
		joinServerRequests();
		assertNoIssues();
	}

	// @formatter:off
	/**
	 *
	 * 01. B requires A, B calls method of A
	 * 02. method of A is renamed but outside the workspace
	 * 03. Perform workspace refresh
	 * 04. B should get error marker at variable statement
	 * 05. method of A is renamed back but outside the workspace
	 * 06. Perform workspace refresh
	 * 07. B should have no error markers
	 *
	 */
	//@formatter:on
	@Test
	public void testChangeOutsideWorkspaceAndRefreshInWorkspace() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of(InheritanceTestFiles.module1() + "/A", InheritanceTestFiles.A()),
				Pair.of(InheritanceTestFiles.module2() + "/B", InheritanceTestFiles.B()));
		startAndWaitForLspServer();
		assertNoIssues();

		changeNonOpenedFile("A", InheritanceTestFiles.AOtherMethodName());
		joinServerRequests();
		// File B should have errors as using old method name
		assertIssues2(Pair.of("B",
				List.of("(Error, [5:2 - 5:5], Couldn't resolve reference to IdentifiableElement 'foo'.)")));

		changeNonOpenedFile("A", InheritanceTestFiles.A());
		joinServerRequests();
		assertNoIssues();
	}
}
