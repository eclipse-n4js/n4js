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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.dirtystate.testdata.CaseSensitiveTestFiles;
import org.eclipse.n4js.dirtystate.testdata.EnumTestFiles;
import org.eclipse.n4js.dirtystate.testdata.InheritanceTestFiles;
import org.eclipse.n4js.dirtystate.testdata.MemberTestFiles;
import org.eclipse.n4js.dirtystate.testdata.RoleTestFiles;
import org.eclipse.n4js.dirtystate.testdata.StaticTestFiles;
import org.eclipse.n4js.dirtystate.testdata.TestFiles;
import org.eclipse.n4js.dirtystate.testdata.TransitiveInheritMemberTestFiles;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.EclipseUIUtils;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * tests if the dirty state manager creates and removes error markers at affected resources when another resource breaks
 * references while editing
 */
public class BuilderParticipantPluginUITest extends AbstractBuilderParticipantTest {
	private static final Logger logger = Logger.getLogger(BuilderParticipantPluginUITest.class);

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
	public void testReferenceBrokenToOtherClassesField() throws Exception {
		logger.info("BuilderParticipantPluginUITest.testReferenceBrokenToOtherClassesField");
		// create project and test files
		final IProject project = createJSProject("testReferenceBrokenToOtherClassesField");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, TestFiles.moduleFolder());

		IFile file1 = createTestFile(moduleFolder, "Class0", TestFiles.class0());
		IFile file2 = createTestFile(moduleFolder, "Class1", TestFiles.class1());

		assertMarkers("File2 should have no errors", file2, 0);
		assertMarkers("File1 should have no errors", file1, 0);

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor file1XtextEditor = openAndGetXtextEditor(file1, page);
		List<?> errors = getEditorErrors(file1XtextEditor);
		assertEquals("Editor of Class0 should have no errors", 0, errors.size());
		XtextEditor file2XtextEditor = openAndGetXtextEditor(file2, page);
		errors = getEditorErrors(file2XtextEditor);
		assertEquals("Editor of Class1 should have no errors", 0, errors.size());

		// change input of second editor so that reference is broken for content
		// in editor 1
		setDocumentContent("changing Class1", file2, file2XtextEditor, TestFiles.class1After().toString());

		errors = getEditorErrors(file2XtextEditor);
		assertEquals("New content of editor 2 should be valid", 0, errors.size());

		// check if editor 1 contain error markers
		errors = getEditorErrors(file1XtextEditor);
		assertEquals("Content of editor 1 should be broken, because now linking to a missing field 'field0' in Class1",
				1, errors.size());

		// resetting old content in editor 2
		setDocumentContent("changing Class1 back", file2, file2XtextEditor, TestFiles.class1().toString());

		// IMarker[] markers = file1.findMarkers(MarkerTypes.ANY_VALIDATION,
		// true, IResource.DEPTH_ZERO);
		// assertEquals(0, markers.length);
		errors = getEditorErrors(file2XtextEditor);
		assertEquals("Old content of editor 2 should be valid", 0, errors.size());

		// editor 1 should not contain any error markers anymore
		errors = getEditorErrors(file1XtextEditor);
		assertEquals(
				"Content of editor 1 should be valid again, as Class1 in editor has got the field with name 'field0' again",
				0, errors.size());
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
	public void testReferenceDeleted() throws Exception {
		logger.info("BuilderParticipantPluginUITest.testReferenceDeleted");
		// create project and test files
		final IProject project = createJSProject("testReferenceBrokenToOtherClassesMethod");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, TestFiles.moduleFolder());

		IFile file2 = createTestFile(moduleFolder, "Class1", TestFiles.class1());
		assertMarkers("File2 should have no errors", file2, 0);
		IFile file1 = createTestFile(moduleFolder, "Class0", TestFiles.class0());
		assertMarkers("File1 should have no errors", file1, 0);

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor file1XtextEditor = openAndGetXtextEditor(file1, page);
		List<Resource.Diagnostic> errors = getEditorErrors(file1XtextEditor);
		assertEquals("Editor of Class0 should have no errors", 0, errors.size());
		XtextEditor file2XtextEditor = openAndGetXtextEditor(file2, page);
		errors = getEditorErrors(file2XtextEditor);
		assertEquals("Editor of Class1 should have no errors", 0, errors.size());

		file2.delete(true, monitor());
		moduleFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();
		waitForUpdateEditorJob();

		// check if editor 1 contain error markers
		errors = getEditorErrors(file1XtextEditor);
		// Consequential errors are omitted, so there is no error reported for unknown field, as the receiver is of
		// unknown type
		assertEquals("Content of editor 1 should be broken, because now linking to missing resource",
				Sets.newHashSet(
						"line 4: Cannot resolve import target :: resolving simple module import : found no matching modules",
						"line 7: Couldn't resolve reference to Type 'Class1'."),
				toSetOfStrings(errors));

		file2 = createTestFile(folder, "Class1", TestFiles.class1());
		assertMarkers("File2 should have no errors", file2, 0);

		// editor 1 should not contain any error markers anymore
		file2 = createTestFile(moduleFolder, "Class1", TestFiles.class1());
		waitForAutoBuild();
		waitForUpdateEditorJob();

		errors = getEditorErrors(file1XtextEditor);
		assertEquals(
				"Content of editor 1 should be valid again, as Class1 in editor has got the field with name 'field0' again",
				0, errors.size());
	}

	// @formatter:off
	/**
	 *
	 * 01. Class0 uses Class1 in require statement and in isa function
	 * 02. Class1 file is renamed
	 * 05. Class0 should get error marker at require statement and at isa function
	 * 06. Class1 is renamed back
	 * 07. Class0 should have no error markers
	 */
	//@formatter:on
	@Test
	public void testSuperClassRenamed() throws Exception {
		logger.info("BuilderParticipantPluginUITest.testSuperClassRenamed");
		// create project and test files
		final IProject project = createJSProject("testSuperClassRenamed");
		IFolder folder = configureProjectWithXtext(project);

		IFolder moduleFolder = createFolder(folder, InheritanceTestFiles.inheritanceModule());

		IFile parentFile = createTestFile(moduleFolder, "Parent", InheritanceTestFiles.Parent());
		assertMarkers("Parent file should have no errors", parentFile, 0);
		IFile childFile = createTestFile(moduleFolder, "Child", InheritanceTestFiles.Child());
		assertMarkers("Child file should have no errors", childFile, 0);

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor parentFileXtextEditor = openAndGetXtextEditor(parentFile, page);
		List<Resource.Diagnostic> errors = getEditorErrors(parentFileXtextEditor);
		assertEquals("Editor of parent should have no errors", 0, errors.size());
		XtextEditor childFileXtextEditor = openAndGetXtextEditor(childFile, page);
		errors = getEditorErrors(childFileXtextEditor);
		assertEquals("Editor of child should have no errors", 0, errors.size());

		parentFileXtextEditor.close(true);

		parentFile.move(new Path("Parent2" + "." + N4JSGlobals.N4JS_FILE_EXTENSION), true, true, monitor());
		moduleFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();
		waitForUpdateEditorJob();

		assertFalse("Parent.n4js doesn't exist anymore",
				moduleFolder.getFile(new Path("Parent" + "." + N4JSGlobals.N4JS_FILE_EXTENSION)).exists());
		IFile movedParentFile = moduleFolder.getFile("Parent2" + "." + N4JSGlobals.N4JS_FILE_EXTENSION);
		assertTrue("Parent2.n4js does exist", movedParentFile.exists());

		errors = getEditorErrors(childFileXtextEditor);
		assertEquals("Editor of child should have got error markers",
				Sets.newHashSet(
						"line 1: Cannot resolve import target :: resolving simple module import : found no matching modules",
						"line 2: Couldn't resolve reference to Type 'ParentObjectLiteral'."),
				toSetOfStrings(errors));

		movedParentFile.move(new Path("Parent" + "." + N4JSGlobals.N4JS_FILE_EXTENSION), true, true, monitor());
		moduleFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();
		waitForUpdateEditorJob();
		assertTrue("Parent.n4js does exist",
				moduleFolder.getFile(new Path("Parent" + "." + N4JSGlobals.N4JS_FILE_EXTENSION)).exists());

		errors = getEditorErrors(childFileXtextEditor);
		assertEquals("Editor of child should have no errors", 0, errors.size());
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
	public void testSuperClassDeleted() throws Exception {
		logger.info("BuilderParticipantPluginUITest.testSuperClassDeleted");
		// create project and test files
		final IProject project = createJSProject("testSuperClassDeleted");
		IFolder folder = configureProjectWithXtext(project);

		IFolder moduleFolder = createFolder(folder, InheritanceTestFiles.inheritanceModule());

		IFile parentFile = createTestFile(moduleFolder, "Parent", InheritanceTestFiles.Parent());
		assertMarkers("Parent file should have no errors", parentFile, 0);
		IFile childFile = createTestFile(moduleFolder, "Child", InheritanceTestFiles.Child());
		assertMarkers("Child file should have no errors", childFile, 0);

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor parentFileXtextEditor = openAndGetXtextEditor(parentFile, page);
		List<Resource.Diagnostic> errors = getEditorErrors(parentFileXtextEditor);
		assertEquals("Editor of parent should have no errors", 0, errors.size());
		XtextEditor childFileXtextEditor = openAndGetXtextEditor(childFile, page);
		errors = getEditorErrors(childFileXtextEditor);
		assertEquals("Editor of child should have no errors", 0, errors.size());

		parentFileXtextEditor.close(true);

		parentFile.delete(true, true, monitor());
		moduleFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();
		waitForUpdateEditorJob();
		assertFalse("Parent.n4js doesn't exist anymore",
				moduleFolder.getFile(new Path("Parent" + "." + N4JSGlobals.N4JS_FILE_EXTENSION)).exists());
		errors = getEditorErrors(childFileXtextEditor);
		assertEquals("Editor of child should have error markers",
				Sets.newHashSet(
						"line 1: Cannot resolve import target :: resolving simple module import : found no matching modules",
						"line 2: Couldn't resolve reference to Type 'ParentObjectLiteral'."),
				toSetOfStrings(errors));

		IFile recreatedParentFile = createTestFile(moduleFolder, "Parent", InheritanceTestFiles.Parent());
		assertMarkers("File1 should have no errors", recreatedParentFile, 0);
		waitForUpdateEditorJob();

		errors = getEditorErrors(childFileXtextEditor);
		assertEquals("Editor of child should have no errors", 0, errors.size());
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
		logger.info("BuilderParticipantPluginUITest.testMethodInConsumedRoleInBetweenRenamed");
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

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor roleAFileXtextEditor = openAndGetXtextEditor(fileARole, page);
		List<?> errors = getEditorErrors(roleAFileXtextEditor);
		assertEquals("Editor of role A should have no errors", 0, errors.size());
		XtextEditor roleBFileXtextEditor = openAndGetXtextEditor(fileBRole, page);
		errors = getEditorErrors(roleBFileXtextEditor);
		assertEquals("Editor of role B should have no errors", 0, errors.size());
		XtextEditor roleCFileXtextEditor = openAndGetXtextEditor(fileCRole, page);
		errors = getEditorErrors(roleCFileXtextEditor);
		assertEquals("Editor of role C should have no errors", 0, errors.size());

		setDocumentContent("changing BRole", fileBRole, roleBFileXtextEditor, RoleTestFiles.roleBChanged().toString());

		assertTrue(roleBFileXtextEditor.isDirty());
		assertEquals(RoleTestFiles.roleBChanged().toString(), roleBFileXtextEditor.getDocument().get());

		errors = getEditorErrors(roleBFileXtextEditor);
		assertEquals("Editor of role B should have no error markers", 0, errors.size());

		errors = getEditorErrors(roleCFileXtextEditor);
		assertEquals("Editor of role C should have error markers", 1, errors.size());

		setDocumentContent("changing BRole back", fileBRole, roleBFileXtextEditor, RoleTestFiles.roleB().toString());

		errors = getEditorErrors(roleBFileXtextEditor);
		assertEquals("Editor of role B should have no error markers", 0, errors.size());

		errors = getEditorErrors(roleCFileXtextEditor);
		assertEquals("Editor of role C should have no error markers", 0, errors.size());
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
	public void testMethodChainingWithRenamingLastOne() throws Exception {
		logger.info("BuilderParticipantPluginUITest.testMethodChainingWithRenamingLastOne");
		final IProject project = createJSProject("testMethodChainingWithRenamingLastOne");
		IFolder folder = configureProjectWithXtext(project);

		IFolder moduleFolder = createFolder(folder, MemberTestFiles.moduleFolder());

		final IFile fileMyInterfaceFour = createTestFile(moduleFolder, "MyInterfaceFour",
				MemberTestFiles.myInterfaceFour());
		IFile fileRoleThree = createTestFile(moduleFolder, "MyRoleThree", MemberTestFiles.myRoleThree());
		IFile fileMyClassTwo = createTestFile(moduleFolder, "MyClassTwo", MemberTestFiles.myClassTwo());
		IFile fileMyVariableTwo = createTestFile(moduleFolder, "MyVariableTwo", MemberTestFiles.myVariableTwo());
		final IFile fileMyClassOne = createTestFile(moduleFolder, "MyClassOne", MemberTestFiles.myClassOne());
		project.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor());
		waitForAutoBuild();

		assertMarkers("File MyInterfaceFour should have no errors", fileMyInterfaceFour, 0);
		assertMarkers("File MyRoleThree should have no errors", fileRoleThree, 0);
		assertMarkers("File MyClassTwo should have no errors", fileMyClassTwo, 0);
		assertMarkers("File MyVariableTwo should have no errors", fileMyVariableTwo, 0);
		assertMarkers("File MyClassOne should have no errors", fileMyClassOne, 0);

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		final XtextEditor fileMyInterfaceFourXtextEditor = openAndGetXtextEditor(fileMyInterfaceFour, page);
		List<?> errors = getEditorErrors(fileMyInterfaceFourXtextEditor);
		assertEquals("Editor of role A should have no errors", 0, errors.size());
		XtextEditor fileMyClassOneXtextEditor = openAndGetXtextEditor(fileMyClassOne, page);
		errors = getEditorErrors(fileMyClassOneXtextEditor);
		assertEquals("Editor of role C should have no errors", 0, errors.size());

		setDocumentContent("changing MyInterfaceFour", fileMyInterfaceFour, fileMyInterfaceFourXtextEditor,
				MemberTestFiles
						.myInterfaceFourChanged().toString());

		errors = getEditorErrors(fileMyInterfaceFourXtextEditor);
		assertEquals("Editor of MyInterfaceFour should have no error markers", 0, errors.size());

		errors = getEditorErrors(fileMyClassOneXtextEditor);
		assertEquals("Editor of MyClassOne should have error markers: " + errors, 1, errors.size());

		setDocumentContent("changing MyInterfaceFour back", fileMyInterfaceFour, fileMyInterfaceFourXtextEditor,
				MemberTestFiles
						.myInterfaceFour().toString());

		errors = getEditorErrors(fileMyClassOneXtextEditor);
		assertEquals("Editor of MyClassOne should have no error markers", 0, errors.size());
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

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor fileAXtextEditor = openAndGetXtextEditor(fileA, page);
		List<?> errors = getEditorErrors(fileAXtextEditor);
		assertEquals("Editor of role A should have no errors", 0, errors.size());
		XtextEditor fileBXtextEditor = openAndGetXtextEditor(fileB, page);
		errors = getEditorErrors(fileBXtextEditor);
		assertEquals("Editor of role B should have no errors", 0, errors.size());
		XtextEditor fileCXtextEditor = openAndGetXtextEditor(fileC, page);
		errors = getEditorErrors(fileCXtextEditor);
		assertEquals("Editor of role C should have no errors", 0, errors.size());

		setDocumentContent("changing C", fileC, fileCXtextEditor, TransitiveInheritMemberTestFiles.CChanged()
				.toString());

		assertTrue(fileCXtextEditor.isDirty());

		errors = getEditorErrors(fileCXtextEditor);
		assertEquals("Editor of C should have no error markers", 0, errors.size());

		errors = getEditorErrors(fileAXtextEditor);
		assertEquals("Editor of A should have error markers because of missing method", 1, errors.size());

		setDocumentContent("changing C back", fileC, fileCXtextEditor, TransitiveInheritMemberTestFiles.C().toString());

		errors = getEditorErrors(fileAXtextEditor);
		assertEquals("Editor of A should have no error markers because of method is again available", 0, errors.size());
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
		final IProject project = createJSProject("testMethodCallWithCaseSensitiveMethodNames");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, CaseSensitiveTestFiles.moduleFolder());

		IFile fileCallee = createTestFile(moduleFolder, "CaseSensitiveCallee", CaseSensitiveTestFiles.callee());
		IFile fileCaller = createTestFile(moduleFolder, "CaseSensitiveCaller", CaseSensitiveTestFiles.caller());
		waitForAutoBuild();

		assertMarkers("File Caller should have no errors", fileCaller, 0);
		assertMarkers("File Callee should have no errors", fileCallee, 0);

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor fileCalleeXtextEditor = openAndGetXtextEditor(fileCallee, page);
		List<?> errors = getEditorErrors(fileCalleeXtextEditor);
		assertEquals("Editor of Callee should have no errors", 0, errors.size());
		XtextEditor fileCallerXtextEditor = openAndGetXtextEditor(fileCaller, page);
		errors = getEditorErrors(fileCallerXtextEditor);
		assertEquals("Editor of Caller should have no errors", 0, errors.size());

		setDocumentContent("changing Callee", fileCallee, fileCalleeXtextEditor, CaseSensitiveTestFiles.calleeChanged()
				.toString());

		assertTrue(fileCalleeXtextEditor.isDirty());

		errors = getEditorErrors(fileCalleeXtextEditor);
		assertEquals("Editor of Callee should have no error markers", 0, errors.size());

		errors = getEditorErrors(fileCallerXtextEditor);
		assertEquals("Editor of Caller should have error markers because of missing method", 1, errors.size());

		setDocumentContent("changing Callee back", fileCallee, fileCalleeXtextEditor, CaseSensitiveTestFiles.callee()
				.toString());

		errors = getEditorErrors(fileCallerXtextEditor);
		assertEquals("Editor of Caller should have no error markers because of method is again available", 0,
				errors.size());
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
	public void testStaticMethodCalls() throws Exception {
		final IProject project = createJSProject("testStaticMethodCalls");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, StaticTestFiles.moduleFolder());

		IFile fileA = createTestFile(moduleFolder, "A", StaticTestFiles.A());
		IFile fileB = createTestFile(moduleFolder, "B", StaticTestFiles.B());
		IFile fileC = createTestFile(moduleFolder, "C", StaticTestFiles.C());
		IFile fileD = createTestFile(moduleFolder, "D", StaticTestFiles.D());
		waitForAutoBuild();

		assertMarkers("File A should have no errors", fileA, 0);
		assertMarkers("File B should have no errors", fileB, 0);
		assertMarkers("File C should have no errors", fileC, 0);
		assertMarkers("File D should have no errors", fileD, 0);

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor fileAXtextEditor = openAndGetXtextEditor(fileA, page);
		List<?> errors = getEditorErrors(fileAXtextEditor);
		assertEquals("Editor of A should have no errors", 0, errors.size());
		XtextEditor fileCXtextEditor = openAndGetXtextEditor(fileC, page);
		errors = getEditorErrors(fileCXtextEditor);
		assertEquals("Editor of C should have no errors", 0, errors.size());

		setDocumentContent("changing C", fileC, fileCXtextEditor, StaticTestFiles.C_changed()
				.toString());

		assertTrue(fileCXtextEditor.isDirty());

		errors = getEditorErrors(fileCXtextEditor);
		assertEquals("Editor of C should have no error markers", 0, errors.size());

		errors = getEditorErrors(fileAXtextEditor);
		assertEquals("Editor of A should have error markers because of missing method", 1, errors.size());

		setDocumentContent("changing C back", fileC, fileCXtextEditor, StaticTestFiles.C()
				.toString());

		errors = getEditorErrors(fileAXtextEditor);
		assertEquals("Editor of A should have no error markers because of method is again available", 0,
				errors.size());
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
	public void testEnumLiterals() throws Exception {
		final IProject project = createJSProject("testEnumLiterals");
		IFolder folder = configureProjectWithXtext(project);
		IFolder moduleFolder = createFolder(folder, EnumTestFiles.moduleFolder());

		IFile fileMyEnum = createTestFile(moduleFolder, "MyEnum", EnumTestFiles.myEnum());
		IFile fileMyEnumUser = createTestFile(moduleFolder, "MyEnumUser", EnumTestFiles.myEnumUser());
		waitForAutoBuild();

		assertMarkers("File MyEnum should have no errors", fileMyEnum, 0);
		assertMarkers("File MyEnumUser should have no errors", fileMyEnumUser, 0);

		// open editors of test files
		IWorkbenchPage page = EclipseUIUtils.getActivePage();
		XtextEditor fileMyEnumXtextEditor = openAndGetXtextEditor(fileMyEnum, page);
		List<?> errors = getEditorErrors(fileMyEnumXtextEditor);
		assertEquals("Editor of MyEnum should have no errors", 0, errors.size());
		XtextEditor fileMyEnumUserXtextEditor = openAndGetXtextEditor(fileMyEnumUser, page);
		errors = getEditorErrors(fileMyEnumUserXtextEditor);
		assertEquals("Editor of MyEnumUser should have no errors", 0, errors.size());

		// TODO: the combination of first modifying the file with closed editor and then with dirty state
		// seems to lead to a threading issue, so that the dirty state isn't recognized correctly

		// fileMyEnumUserXtextEditor.close(false);
		//
		// setDocumentContent("changing MyEnum", fileMyEnum, fileMyEnumXtextEditor, EnumTestFiles.myEnum_changed()
		// .toString());
		//
		// assertTrue(fileMyEnumXtextEditor.isDirty());
		//
		// assertMarkers("File MyEnumUser with old literal should have no errors yet", fileMyEnumUser,
		// 0);
		//
		// fileMyEnumXtextEditor.doSave(new NullProgressMonitor());
		//
		// assertFalse(fileMyEnumXtextEditor.isDirty());
		//
		// waitForAutoBuild();
		//
		// assertMarkers("File MyEnumUser with old literal should have errors as editor now saved", fileMyEnumUser,
		// 1);
		//
		// setDocumentContent("changing MyEnum back", fileMyEnum, fileMyEnumXtextEditor, EnumTestFiles.myEnum()
		// .toString());
		//
		// fileMyEnumXtextEditor.doSave(new NullProgressMonitor());
		//
		// waitForAutoBuild();
		//
		// assertMarkers("File MyEnumUser with old literal should have no errors", fileMyEnumUser,
		// 0);
		//
		// fileMyEnumUserXtextEditor = openAndGetXtextEditor(fileMyEnumUser, page);

		errors = getEditorErrors(fileMyEnumUserXtextEditor);
		assertEquals("Editor of MyEnumUser should have no error markers", 0, errors.size());

		setDocumentContent("changing MyEnum again", fileMyEnum, fileMyEnumXtextEditor, EnumTestFiles.myEnum_changed()
				.toString());

		waitForUpdateEditorJob();
		errors = getEditorErrors(fileMyEnumUserXtextEditor);
		assertEquals("Editor of MyEnumUser should have error markers because of missing literal", 1, errors.size());

		setDocumentContent("changing MyEnum back again", fileMyEnum, fileMyEnumXtextEditor, EnumTestFiles.myEnum()
				.toString());

		waitForUpdateEditorJob();
		errors = getEditorErrors(fileMyEnumUserXtextEditor);
		assertEquals("Editor of MyEnumUser should have no error markers because of literal is again available", 0,
				errors.size());
	}

	private Set<String> toSetOfStrings(Collection<? extends Resource.Diagnostic> diagnostics) {
		return diagnostics.stream()
				.map(d -> "line " + d.getLine() + ": " + d.getMessage())
				.collect(Collectors.toSet());
	}
}
