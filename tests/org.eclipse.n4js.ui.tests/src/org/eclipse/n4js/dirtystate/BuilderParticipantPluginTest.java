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
package org.eclipse.n4js.dirtystate;

import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.monitor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.n4js.dirtystate.testdata.CaseSensitiveTestFiles;
import org.eclipse.n4js.dirtystate.testdata.EnumTestFiles;
import org.eclipse.n4js.dirtystate.testdata.InheritanceTestFiles;
import org.eclipse.n4js.dirtystate.testdata.InterfaceTestFiles;
import org.eclipse.n4js.dirtystate.testdata.MemberTestFiles;
import org.eclipse.n4js.dirtystate.testdata.RoleTestFiles;
import org.eclipse.n4js.dirtystate.testdata.StaticTestFiles;
import org.eclipse.n4js.dirtystate.testdata.TestFiles;
import org.eclipse.n4js.dirtystate.testdata.TransitiveInheritMemberTestFiles;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.xtext.util.StringInputStream;
import org.junit.Test;

/**
 * tests if the Xtext builder creates and removes error markers at affected resources when another resource breaks
 * references in them after saving
 */
public class BuilderParticipantPluginTest extends AbstractBuilderParticipantTest {

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
		final IProject project = createJSProject("testNoErrorMarkersWhenReferencedFileIsCreatedBeforeReferencingFile");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, TestFiles.moduleFolder());

		IFile file2 = createTestFile(moduleFolder, "Class1", TestFiles.class1());
		assertMarkers("File2 should have no errors", file2, 0);

		IFile file1 = createTestFile(moduleFolder, "Class0", TestFiles.class0());

		assertMarkers("File1 should have no errors", file1, 0);
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
		final IProject project = createJSProject("testNewFileFixesLinkingIssue");
		IFolder folder = configureProjectWithXtext(project);
		IFolder pr0_0pa0 = createFolder(folder, "pr0_0pa0");
		IFile file1 = createTestFile(pr0_0pa0, "Class0", TestFiles.class0());
		waitForAutoBuild();
		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to Type 'Class1'.
		assertMarkers("File1 should have 2 markers", file1, 2);

		IFile file2 = createTestFile(pr0_0pa0, "Class1", TestFiles.class1());
		waitForAutoBuild();
		assertMarkers("File1 should have no errors", file1, 0);
		assertMarkers("File2 should have no errors", file2, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
	/**
	 *
	 * 01. B requires A, B calls method of A
	 * 02. method of A is renamed
	 * 03. B should get error marker at variable statement
	 * 04. method of A is renamed back
	 * 05. B should have no error markers
	 *
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testMethodInRequiredClassRenamed() throws Exception {
		final IProject project = createJSProject("testMethodInSuperClassRenamed");
		IFolder folder = configureProjectWithXtext(project);
		IFolder module1Folder = createFolder(folder, InheritanceTestFiles.module1());
		IFolder module2Folder = createFolder(folder, InheritanceTestFiles.module2());

		IFile fileA = createTestFile(module1Folder, "A", InheritanceTestFiles.A());
		IFile fileB = createTestFile(module2Folder, "B", InheritanceTestFiles.B());

		assertMarkers("File A should only have no errors and no warnings", fileA, 0);
		assertMarkers("File B should only have no errors and no warnings", fileB, 0);

		fileA.setContents(new StringInputStream(InheritanceTestFiles.AOtherMethodName().toString()), true, true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File A with other method name should have no errors and no warnings", fileA, 0);

		// One marker for using the old method name
		assertMarkers("File B should have errors as using old method name", fileB, 1);

		fileA.setContents(new StringInputStream(InheritanceTestFiles.A().toString()), true, true, monitor());
		waitForAutoBuild();

		assertMarkers("File A with old method name should have no errors and no warnings", fileA, 0);
		assertMarkers("File B should have no errors and no warnings after changing back to old A", fileB, 0);
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
		final IProject project = createJSProject("testMethodInSuperClassRenamed");
		IFolder folder = configureProjectWithXtext(project);
		IFolder module1Folder = createFolder(folder, InheritanceTestFiles.module1());
		IFolder module2Folder = createFolder(folder, InheritanceTestFiles.module2());

		IFile fileA = createTestFile(module1Folder, "A", InheritanceTestFiles.A());
		fileA.setLocalTimeStamp(0L);
		IFile fileB = createTestFile(module2Folder, "B", InheritanceTestFiles.B());
		IFile fileC = createTestFile(module2Folder, "C", InheritanceTestFiles.C());
		IFile fileD = createTestFile(module1Folder, "D", InheritanceTestFiles.D());

		assertMarkers("File A should only have no errors and no warnings", fileA, 0);

		assertMarkers("File B should only have no errors no warnings", fileB, 0);
		assertMarkers("File C should have no markers", fileC, 0);
		assertMarkers("File D should no errors and only no warnings", fileD, 0);

		replaceFileContentAndWaitForRefresh(folder, fileA, InheritanceTestFiles.AOtherMethodName().toString(), 1L);

		assertMarkers("File A with other method name should have no errors and no warnings", fileA, 0);

		// First marker for using old method name
		assertMarkers("File D should have errors as using old method name.", fileD, 1);

		replaceFileContentAndWaitForRefresh(folder, fileA, InheritanceTestFiles.A().toString(), 2L);

		assertMarkers("File A with old method name should have no errors and no warnings", fileA, 0);
		assertMarkers("File D should have no errors and no warnings after changing back to old A", fileD, 0);
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
		final IProject project = createJSProject("testMutualDependency");
		IFolder folder = configureProjectWithXtext(project);

		IFolder moduleFolder = createFolder(folder, TestFiles.mutualModuleFolder());

		IFile brotherFile = createTestFile(moduleFolder, "Brother", TestFiles.classBrother());
		IFile sisterFile = createTestFile(moduleFolder, "Sister", TestFiles.classSister());
		IFile childFile = createTestFile(moduleFolder, "Child", TestFiles.classChild());

		assertMarkers("brother file should have no errors", brotherFile, 0);
		// expected markers
		// Variable brother is used before it is declared
		assertMarkers("sister file should have one errors", sisterFile, 1);
		assertMarkers("child file should have no errors", childFile, 0);

		sisterFile.setContents(new StringInputStream(TestFiles.classSisterNew().toString()), true, true, monitor());
		waitForAutoBuild();

		// expected markers
		// Couldn't resolve reference to TMember 'getBrother'. at brother.getSister().getBrother
		// Couldn't resolve reference to TMember 'getBrother'. at brother = sister.getBrother;
		assertMarkers("brother file should have errors as using old method of sister", brotherFile, 2);
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
		assertMarkers("new sister file should have errors as calling oldMethod via brother file", sisterFile, 3);

		sisterFile.setContents(new StringInputStream(TestFiles.classSister().toString()), true, true, monitor());
		waitForAutoBuild();

		// expected markers
		// Variable brother is used before it is declared
		assertMarkers("sister file should have still one error", sisterFile, 1);
		assertMarkers("brother file should have no errors anymore", brotherFile, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
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
	 *
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testMethodInConsumedRoleRenamed() throws Exception {
		final IProject project = createJSProject("testMethodInConsumedRoleRenamed");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, RoleTestFiles.moduleFolder());

		IFile fileARole = createTestFile(moduleFolder, "ARole", RoleTestFiles.roleA());
		IFile fileBRole = createTestFile(moduleFolder, "BRole", RoleTestFiles.roleB());
		IFile fileCRole = createTestFile(moduleFolder, "CRole", RoleTestFiles.roleC());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileARole, 0);
		assertMarkers("File B should have no errors", fileBRole, 0);
		assertMarkers("File C should have no errors", fileCRole, 0);

		fileARole.setContents(new StringInputStream(RoleTestFiles.roleAChanged().toString()), true, true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File A with other method name should have no errors", fileARole, 0);

		assertMarkers("File B should have errors as using old method name", fileBRole, 1);

		assertMarkers("File C should have errors as using old method name", fileCRole, 1);

		fileARole.setContents(new StringInputStream(RoleTestFiles.roleA().toString()), true, true, monitor());
		waitForAutoBuild();

		assertMarkers("File A with old method name should have no errors", fileARole, 0);

		assertMarkers("File B should have exactly one marker after changing back to old A", fileBRole, 0);

		assertMarkers("File C should have exactly one marker after changing back to old A", fileCRole, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
	/**
	 *
	 * 01. CRole consumes BRole, BRole consumes ARole
	 * 02. BRole calls method of ARole, BRole calls method of BRole and a method of ARole
	 * 03. method of BRole is renamed
	 * 04. CRole should get error marker at call of B's method, call to A's method should be ok
	 * 05. method of BRole is renamed back
	 * 06. C should have no error markers
	 *
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testMethodInConsumedRoleInBetweenRenamed() throws Exception {
		final IProject project = createJSProject("testMethodInConsumedRoleInBetweenRenamed");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, RoleTestFiles.moduleFolder());

		IFile fileARole = createTestFile(moduleFolder, "ARole", RoleTestFiles.roleA());
		IFile fileBRole = createTestFile(moduleFolder, "BRole", RoleTestFiles.roleB());
		IFile fileCRole = createTestFile(moduleFolder, "CRole", RoleTestFiles.roleC());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileARole, 0);
		assertMarkers("File B should have no errors", fileBRole, 0);
		assertMarkers("File C should have no errors", fileCRole, 0);

		fileBRole.setContents(new StringInputStream(RoleTestFiles.roleBChanged().toString()), true, true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileARole, 0);

		assertMarkers("File B with other method name should have no errors", fileBRole, 0);

		assertMarkers("File C should have errors as using old method name", fileCRole, 1);

		fileBRole.setContents(new StringInputStream(RoleTestFiles.roleB().toString()), true, true, monitor());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileARole, 0);

		assertMarkers("File B should have no errors", fileBRole, 0);

		assertMarkers("File C should have no errors after changing back to old B", fileCRole, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
	/**
	 *
	 * 01. CRole consumes BRole, BRole consumes ARole
	 * 02. BRole calls method of ARole, BRole calls method of BRole and a method of ARole
	 * 03. ARole as consumed Role is removed at BRole
	 * 04. CRole should get error marker at call of A's method, call to B's method should be ok
	 * 05. methodA is added to BRole
	 * 06. C should have no error markers
	 *
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testConsumedRoleInConsumedRoleInBetweenRemoved() throws Exception {
		final IProject project = createJSProject("testConsumedRoleInConsumedRoleInBetweenRemoved");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, RoleTestFiles.moduleFolder());

		IFile fileARole = createTestFile(moduleFolder, "ARole", RoleTestFiles.roleA());
		IFile fileBRole = createTestFile(moduleFolder, "BRole", RoleTestFiles.roleB());
		IFile fileCRole = createTestFile(moduleFolder, "CRole", RoleTestFiles.roleC());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileARole, 0);
		assertMarkers("File B should have no errors", fileBRole, 0);
		assertMarkers("File C should have no errors", fileCRole, 0);

		fileBRole.setContents(new StringInputStream(RoleTestFiles.roleBChanged2().toString()), true, true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileARole, 0);

		assertMarkers("File B with no A Role consumed should have no errors, one unused import warning.", fileBRole, 1);

		assertMarkers("File C should have errors as using non existing method", fileCRole, 1);

		fileBRole.setContents(new StringInputStream(RoleTestFiles.roleBChanged3().toString()), true, true, monitor());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileARole, 0);

		assertMarkers("File B should have no errors, one unused imports warning", fileBRole, 1);

		assertMarkers("File C should have no errors after changing back to B with additional method", fileCRole, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
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
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testSwitchFromRoleToClassMethod() throws Exception {
		final IProject project = createJSProject("testConsumedRoleInConsumedRoleInBetweenRemoved");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, RoleTestFiles.moduleFolder());

		IFile fileARole = createTestFile(moduleFolder, "ARole", RoleTestFiles.roleA());
		IFile fileBRole = createTestFile(moduleFolder, "BRole", RoleTestFiles.roleB());
		IFile fileCRole = createTestFile(moduleFolder, "CRole", RoleTestFiles.roleC());
		IFile fileD = createTestFile(moduleFolder, "D", RoleTestFiles.classD());
		IFile fileE = createTestFile(moduleFolder, "E", RoleTestFiles.classE());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileARole, 0);
		assertMarkers("File B should have no errors", fileBRole, 0);
		assertMarkers("File C should have no errors", fileCRole, 0);
		assertMarkers("File D should have no errors", fileD, 0);
		assertMarkers("File E should have no errors", fileE, 0);

		fileE.setContents(new StringInputStream(RoleTestFiles.classEChanged().toString()), true, true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File C should have no errors as now using method of D", fileD, 0);

		fileD.setContents(new StringInputStream(RoleTestFiles.classDChanged().toString()), true, true, monitor());
		waitForAutoBuild();

		assertMarkers("File E should have errors after method not available anymore", fileE, 2);

		fileE.setContents(new StringInputStream(RoleTestFiles.classE().toString()), true, true, monitor());
		waitForAutoBuild();

		assertMarkers("File E should have no errors after method is available via role", fileE, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
	/**
	 *
	 * 01. ClassWithInterfaces implements InterfaceB, InterfaceB extends InterfaceA
	 * 02. ClassWithInterfaces calls method of InterfaceA and a method of InterfaceB
	 * 03. method of InterfaceA is renamed
	 * 04. ClassWithInterfaces should get error marker at method call
	 * 05. method of InterfaceA is renamed back
	 * 06. ClassWithInterfaces should have no error markers
	 *
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testMethodInImplementedInterfaceRenamed() throws Exception {
		final IProject project = createJSProject("testMethodInImplementedInterfaceRenamed");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, InterfaceTestFiles.moduleFolder());

		IFile fileInterfaceA = createTestFile(moduleFolder, "InterfaceA", InterfaceTestFiles.interfaceA());
		IFile fileInterfaceB = createTestFile(moduleFolder, "InterfaceB", InterfaceTestFiles.interfaceB());
		IFile fileClassWithInterface = createTestFile(moduleFolder, "ClassWithInterfaces",
				InterfaceTestFiles.classWithInterfaces());
		waitForAutoBuild();

		assertMarkers("File InterfaceA should have no errors", fileInterfaceA, 0);
		assertMarkers("File InterfaceB should have no errors", fileInterfaceB, 0);
		assertMarkers("File ClassWithInterface should have no errors", fileClassWithInterface, 0);

		fileInterfaceA.setContents(new StringInputStream(InterfaceTestFiles.interfaceAChanged().toString()), true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File InterfaceA with other method name should have no errors", fileInterfaceA, 0);

		assertMarkers("File InterfaceB should have errors", fileInterfaceB, 0);

		assertMarkers("File ClassWithInterface should have errors as using old method name", fileClassWithInterface, 1);

		fileInterfaceA.setContents(new StringInputStream(InterfaceTestFiles.interfaceA().toString()), true, true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File InterfaceA with old method name should have no errors", fileInterfaceA, 0);

		assertMarkers("FileInterfaceBB should have no errors", fileInterfaceB, 0);

		assertMarkers("File ClassWithInterface should have no errors after changing back to old InterfaceA",
				fileClassWithInterface, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
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
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testMethodChainingWithRenamingLastOne() throws Exception {
		final IProject project = createJSProject("testMethodChainingWithRenamingLastOne");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, MemberTestFiles.moduleFolder());

		IFile fileMyInterfaceFour = createTestFile(moduleFolder, "MyInterfaceFour", MemberTestFiles.myInterfaceFour());
		IFile fileRoleThree = createTestFile(moduleFolder, "MyRoleThree", MemberTestFiles.myRoleThree());
		IFile fileMyClassTwo = createTestFile(moduleFolder, "MyClassTwo", MemberTestFiles.myClassTwo());
		IFile fileMyVariableTwo = createTestFile(moduleFolder, "MyVariableTwo", MemberTestFiles.myVariableTwo());
		IFile fileMyClassOne = createTestFile(moduleFolder, "MyClassOne", MemberTestFiles.myClassOne());
		waitForAutoBuild();

		assertMarkers("File MyInterfaceFour should have no errors", fileMyInterfaceFour, 0);
		assertMarkers("File MyRoleThree should have no errors", fileRoleThree, 0);
		assertMarkers("File MyClassTwo should have no errors", fileMyClassTwo, 0);
		assertMarkers("File MyVariableTwo should have no errors", fileMyVariableTwo, 0);
		assertMarkers("File MyClassOne should have no errors", fileMyClassOne, 0);

		fileMyInterfaceFour.setContents(new StringInputStream(MemberTestFiles.myInterfaceFourChanged().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File MyClassOne with other missing method name in chain should have errors", fileMyClassOne,
				1);
		assertMarkers("File MyVariableTwo should have no errors", fileMyVariableTwo, 0);
		assertMarkers("File MyClassTwo should have no errors", fileMyClassTwo, 0);
		assertMarkers("File MyRoleThree should have no errors", fileRoleThree, 0);
		assertMarkers("File MyInterfaceFour should have no errors", fileMyInterfaceFour, 0);

		fileMyInterfaceFour.setContents(new StringInputStream(MemberTestFiles.myInterfaceFour().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File MyClassOne with old method name in chain should have no errors", fileMyClassOne, 0);
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
		final IProject project = createJSProject("testMethodChainingWithCreatingFilesInReverseOrder");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, MemberTestFiles.moduleFolder());

		IFile fileMyClassOne = createTestFile(moduleFolder, "MyClassOne", MemberTestFiles.myClassOne());
		IFile fileMyVariableTwo = createTestFile(moduleFolder, "MyVariableTwo", MemberTestFiles.myVariableTwo());
		IFile fileMyClassTwo = createTestFile(moduleFolder, "MyClassTwo", MemberTestFiles.myClassTwo());
		IFile fileRoleThree = createTestFile(moduleFolder, "MyRoleThree", MemberTestFiles.myRoleThree());
		IFile fileMyInterfaceFour = createTestFile(moduleFolder, "MyInterfaceFour", MemberTestFiles.myInterfaceFour());
		waitForAutoBuild();

		assertMarkers("File MyInterfaceFour should have no errors", fileMyInterfaceFour, 0);
		assertMarkers("File MyRoleThree should have no errors", fileRoleThree, 0);
		assertMarkers("File MyClassTwo should have no errors", fileMyClassTwo, 0);
		assertMarkers("File MyVariableTwo should have no errors", fileMyVariableTwo, 0);
		assertMarkers("File MyClassOne should have no errors", fileMyClassOne, 0);
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
	@SuppressWarnings("resource")
	//@formatter:on
	@Test
	public void testRenamingMethodAccessedViaSubclass() throws Exception {
		final IProject project = createJSProject("testRenamingMethodAccessedViaSubclass");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, TransitiveInheritMemberTestFiles.moduleFolder());

		IFile fileC = createTestFile(moduleFolder, "C", TransitiveInheritMemberTestFiles.C());
		IFile fileB = createTestFile(moduleFolder, "B", TransitiveInheritMemberTestFiles.B());
		IFile fileA = createTestFile(moduleFolder, "A", TransitiveInheritMemberTestFiles.A());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileA, 0);
		assertMarkers("File B should have no errors", fileB, 0);
		assertMarkers("File C should have no errors", fileC, 0);

		fileC.setContents(new StringInputStream(TransitiveInheritMemberTestFiles.CChanged().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File A with other missing method name in chain should have errors", fileA,
				1);
		assertMarkers("File B should have no errors", fileB, 0);
		assertMarkers("File C should have no errors", fileC, 0);

		fileC.setContents(new StringInputStream(TransitiveInheritMemberTestFiles.C().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File A with old method name in chain should have no errors", fileA, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
	/**
	 *
	 * 01. Caller calls callee methods, who just differs in lower/upper case
	 * 02. method of callee is renamed
	 * 04. Caller should get error marker at method call
	 * 05. method of Callee is renamed back
	 * 06. Caller should have no error markers
	 *
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testMethodCallWithCaseSensitiveMethodNames() throws Exception {
		final IProject project = createJSProject("testMethodCallWithCaseSensitiveMethodNames");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, CaseSensitiveTestFiles.moduleFolder());

		IFile fileCallee = createTestFile(moduleFolder, "CaseSensitiveCallee", CaseSensitiveTestFiles.callee());
		IFile fileCaller = createTestFile(moduleFolder, "CaseSensitiveCaller", CaseSensitiveTestFiles.caller());
		waitForAutoBuild();

		assertMarkers("File Caller should have no errors", fileCaller, 0);
		assertMarkers("File Callee should have no errors", fileCallee, 0);

		fileCallee.setContents(new StringInputStream(CaseSensitiveTestFiles.calleeChanged().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File Caller with other missing method name in chain should have errors", fileCaller,
				1);
		assertMarkers("File Callee should have no errors", fileCallee, 0);

		fileCallee.setContents(new StringInputStream(CaseSensitiveTestFiles.callee().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File Caller with old method name in chain should have no errors", fileCaller, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
	/**
	 *
	 * 01. static called member is changed to non-static -> errors at callers and errors gone, when changed back
	 * 02. static added to getter -> errors at callers and errors gone, when changed back
	 * 03a. static method overrides static method in super class, sub class is called in caller
	 * 03b. method in sub class renamed -> no errors expected, as now linking to method in super class
	 *
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testStaticMethodCalls() throws Exception {
		final IProject project = createJSProject("testStaticMethodCalls");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, StaticTestFiles.moduleFolder());

		IFile fileCallee = createTestFile(moduleFolder, "Callee", StaticTestFiles.callee());
		IFile fileSubCallee = createTestFile(moduleFolder, "SubCallee", StaticTestFiles.subCallee());
		IFile fileCaller = createTestFile(moduleFolder, "Caller", StaticTestFiles.caller());
		waitForAutoBuild();

		assertMarkers("File Callee should have no errors", fileCallee, 0);
		assertMarkers("File SubCallee should have no errors", fileSubCallee, 0);
		assertMarkers("File Caller should have no errors", fileCaller, 0);

		fileCallee.setContents(new StringInputStream(StaticTestFiles.callee_changedStaticMember().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File Caller with field not static anymore should have errors", fileCaller,
				2);
		assertMarkers("File Callee should have no errors", fileCallee, 0);
		assertMarkers("File SubCallee should have one error", fileSubCallee, 1);

		fileCallee.setContents(new StringInputStream(StaticTestFiles.callee().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File Caller with old field access should have no errors", fileCaller, 0);
		assertMarkers("File SubCallee with old field access should have no errors", fileSubCallee, 0);

		fileCallee.setContents(new StringInputStream(StaticTestFiles.callee_changedNonStaticAccessors().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File Caller with getter static now should have errors", fileCaller,
				1); // 1 for static access in non-static context + 1 any is not sub type of string
		assertMarkers("File Callee should have one error, because of wrong this access", fileCallee, 1);
		assertMarkers("File SubCallee should have no errors", fileSubCallee, 0);

		fileCallee.setContents(new StringInputStream(StaticTestFiles.callee().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File Caller with old getter access should have no errors", fileCaller, 0);
		assertMarkers("File Callee with old this access should have no errors", fileCallee, 0);

		fileSubCallee.setContents(new StringInputStream(StaticTestFiles.subCallee_changed().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File SubCallee should have no errors", fileSubCallee, 0);
		assertMarkers("File Callee should have no errors", fileCallee, 0);
		assertMarkers("File Caller should have no errors as now linking to super class method", fileCaller, 0);

		fileSubCallee.setContents(new StringInputStream(StaticTestFiles.subCallee().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File Caller with old method should have no errors", fileCaller, 0);
	}

	// @formatter:off
	@SuppressWarnings({ "resource", "javadoc" })
	/**
	 * 01. class accesses literal from enumeration
	 * 02. literal is renamed
	 * 03. accessing class should have error at literal access
	 * 04. literal is renamed back
	 * 05. error in accessing class should have been gone
	 *
	 * @throws Exception
	 */
	//@formatter:on
	@Test
	public void testEnumLiterals() throws Exception {
		final IProject project = createJSProject("testEnumLiterals");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, EnumTestFiles.moduleFolder());

		IFile fileMyEnum = createTestFile(moduleFolder, "MyEnum", EnumTestFiles.myEnum());
		IFile fileMyEnumUser = createTestFile(moduleFolder, "MyEnumUser", EnumTestFiles.myEnumUser());
		waitForAutoBuild();

		assertMarkers("File MyEnum should have no errors", fileMyEnum, 0);
		assertMarkers("File MyEnumUser should have no errors", fileMyEnumUser, 0);

		fileMyEnum.setContents(new StringInputStream(EnumTestFiles.myEnum_changed().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File MyEnum should have no errors", fileMyEnum, 0);

		// Still uses old enum
		assertMarkers("File MyEnumUser with old literal should have errors", fileMyEnumUser, 1);

		fileMyEnum.setContents(new StringInputStream(EnumTestFiles.myEnum().toString()),
				true,
				true,
				monitor());
		waitForAutoBuild();

		assertMarkers("File MyEnumUser with old literal access have no errors and no warnings", fileMyEnumUser, 0);
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
		final IProject project = createJSProject("testMethodInSuperClassRenamed");
		IFolder folder = configureProjectWithXtext(project);
		IFolder module1Folder = createFolder(folder, InheritanceTestFiles.module1());
		IFolder module2Folder = createFolder(folder, InheritanceTestFiles.module2());

		IFile fileA = createTestFile(module1Folder, "A", InheritanceTestFiles.A());
		fileA.setLocalTimeStamp(0L);
		IFile fileB = createTestFile(module2Folder, "B", InheritanceTestFiles.B());

		assertMarkers("File A should only have no warnings", fileA, 0);
		assertMarkers("File B should only have no warnings", fileB, 0);

		replaceFileContentAndWaitForRefresh(folder, fileA, InheritanceTestFiles.AOtherMethodName().toString(), 1L);
		assertMarkers("File A with other method name should no markers", fileA, 0);

		// Uses the old method name
		assertMarkers("File B should have errors as using old method name", fileB, 1);

		replaceFileContentAndWaitForRefresh(folder, fileA, InheritanceTestFiles.A().toString(), 2L);

		assertMarkers("File A with old method name should only have no warnings", fileA, 0);
		assertMarkers("File B should have no errors after changing back to old A", fileB, 0);
	}

}
