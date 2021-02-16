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
package org.eclipse.n4js.tests.selection

import com.google.inject.Inject
import org.eclipse.n4js.N4JSUiInjectorProvider
import org.eclipse.n4js.annotation.TestWithScript
import org.eclipse.n4js.n4JS.AdditiveExpression
import org.eclipse.n4js.n4JS.BinaryLogicalExpression
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4TypeVariable
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ui.selection.AstSelectionProvider2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.ui.XtextProjectHelper
import org.eclipse.xtext.ui.testing.AbstractEditorTest
import org.eclipse.xtext.util.ITextRegion
import org.junit.Test
import org.junit.runner.RunWith

import static java.util.UUID.randomUUID
import static org.hamcrest.Matchers.*

import static extension org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.*

/**
 * Class for checking the functionality of the {@link AstSelectionProvider2} class.
 */
@SuppressWarnings("deprecation")
@RunWith(typeof(XtextRunner))
@InjectWith(typeof(N4JSUiInjectorProvider))
class AstSelectionProvider2PluginUITest extends AbstractEditorTest {

	val EDITOR_ID = 'org.eclipse.n4js.N4JS'
	val PROJECT_NAME = 'testProject'
	val MF_FILE = 'manifest.nfmf'
	val SRC = 'src';

	@Inject
	private extension AstSelectionProvider2

	override setUp() throws Exception {
		super.setUp()
		createN4JSProjectWithXtextNature
	}

	override tearDown() throws Exception {
		super.tearDown()
		cleanWorkspace
	}

	override protected getEditorId() {
		EDITOR_ID
	}

	@Test
	@TestWithScript(script='var i = 1 + 2 + 3;', selectedText=' ')
	public def void testZeroLengthSelectionOnAstNode() {
		script.astElement(selection).assertNull
	}

	@Test
	@TestWithScript(script='class  ABCD {}', occurrenceIndex = 6)
	public def void testEmptySelectionBetweenWhitespaceOnAstNode() {
		script.astElement(selection).assertNull
	}

	@Test
	@TestWithScript(script='class  ABCD {}', occurrenceIndex = 7)
	public def void testEmptySelectionBeforeClassNameOnAstNode() {
		script.astElement(selection).assertThat(instanceOf(N4ClassDeclaration))
	}

	@Test
	@TestWithScript(script='class  ABCD {}', occurrenceIndex = 8)
	public def void testEmptySelectionInClassNameOnAstNode() {
		script.astElement(selection).assertThat(instanceOf(N4ClassDeclaration))
	}

	@Test
	@TestWithScript(script='class  ABCD {}', occurrenceIndex = 11)
	public def void testEmptySelectionAfterClassNameOnAstNode() {
		script.astElement(selection).assertThat(instanceOf(N4ClassDeclaration))
	}

	@Test
	@TestWithScript(script='var i = 1 + 2 + 3;', selectedText=' 1 +')
	public def void testIncorrectSelectionOnAstNode() {
		script.astElement(selection).assertNull
	}

	@Test
	@TestWithScript(script='var i = 1 + 2 + 3;', selectedText='1')
	public def void testPreciseSelectionOnAstNode() {
		script.astElement(selection).assertThat(instanceOf(IntLiteral))
	}

	@Test
	@TestWithScript(script='var i = 1 + 2 + 3;', selectedText=' 1 ')
	public def void testSelectionWithWhitespaceOnAstNode() {
		script.astElement(selection).assertThat(instanceOf(IntLiteral))
	}

	@Test
	@TestWithScript(script='var i = 1 + 2 + 3;', selectedText='1 + 2')
	public def void testPreciseSelectionOnMultipleAstNodes() {
		script.astElement(selection).assertThat(instanceOf(AdditiveExpression))
	}

	@Test
	@TestWithScript(script='var i = 1 + 2 + 3;', selectedText=' 1 + 2 + 3')
	public def void testSelectionWithWhitespaceOnMultipleAstNodes() {
		script.astElement(selection).assertThat(instanceOf(AdditiveExpression))
	}

	@Test
	@TestWithScript(script='var alwaysFalse = function(): boolean {' +
							'return false;' +
							'}', selectedText = 'unction')
	public def void testPartialSelectionExpression() {
		script.astElement(selection).assertThat(instanceOf(FunctionExpression))
	}

	@Test
	@TestWithScript(script='var alwaysFalse = function(): boolean {' +
							'return false;' +
							'}',
					selectedText = 'function(): boolean {' +
							'return false;' +
							'}')
	public def void testFullSelectionExpression() {
		script.astElement(selection).assertThat(instanceOf(FunctionExpression))
	}

	@Test
	@TestWithScript(script='var bFalse = false;' +
							'if (true && false || !bFalse) { }', selectedText='true && false')
	public def void testSelectionLogicalExpression() {
		script.astElement(selection).assertThat(instanceOf(BinaryLogicalExpression))
	}

	@Test
	@TestWithScript(script='var bFalse = false;' +
							'if (true && false || !bFalse) { }', selectedText='true && false || !bFalse')
	public def void testSelectionComplexLogicalExpression() {
		script.astElement(selection).assertThat(instanceOf(BinaryLogicalExpression))
	}

	@Test
	@TestWithScript(script='var bFalse = false;' +
							'if (true && false || !bFalse) { }', selectedText='bFalse')
	public def void testSelectionVariableDecl() {
		script.astElement(selection).assertThat(instanceOf(VariableDeclaration))
	}

	@Test
	@TestWithScript(script='var bFalse = false;' +
							'if (true && false || !bFalse) { }', selectedText='bFalse', occurrenceIndex = 1)
	public def void testSelectionIdentifierRef() {
		script.astElement(selection).assertThat(instanceOf(IdentifierRef))
	}

	@Test
	@TestWithScript(script='class SomeClass<T> {' +
							'private t: T;' +
							'constructor(t: T) { }' +
							'}', selectedText='SomeClass')
	public def void testSelectionClassDeclaration() {
		script.astElement(selection).assertThat(instanceOf(N4ClassDeclaration))
	}

	@Test
	@TestWithScript(script='class SomeClass<T> {' +
							'private t: T;' +
							'constructor(t: T) { }' +
							'}', selectedText='T')
	public def void testSelectionTypeVariable() {
		script.astElement(selection).assertThat(instanceOf(N4TypeVariable))
	}

	@Test
	@TestWithScript(script='class SomeClass<T> {' +
							'private t: T;' +
							'constructor(t: T) { }' +
							'}', selectedText='T', occurrenceIndex = 1)
	public def void testSelectionParameterizedTypeRef() {
		script.astElement(selection).assertThat(instanceOf(TypeReferenceNode))
	}

	def private astElement(String script, ITextRegion selection) {
		createDocumentWithContent(script).getSelectedAstElement(selection);
	}

	def private createN4JSProjectWithXtextNature() {
		createProject(PROJECT_NAME) => [
			project.addNature(XtextProjectHelper.NATURE_ID)
			createFolder('''«PROJECT_NAME»/«SRC»''')
			createN4MFFile
		]
	}

	def private createDocumentWithContent(String content) {
		createFileWithContent(content).openEditor.document;
	}

	def private createFileWithContent(String content) {
		val file = createFile('''«PROJECT_NAME»/«SRC»/«randomUUID».n4js''', content)
		waitForBuild
		file
	}

	def private createN4MFFile() {
		createFile('''«PROJECT_NAME»/«MF_FILE»''', MFFileContent.toString);
	}

	def private getMFFileContent() '''
		ProjectId: «PROJECT_NAME»
		ProjectType: library
		ProjectVersion: 0.0.1-SNAPSHOT
		VendorId: org.eclipse.n4js
		VendorName: 'Eclipse N4JS Project'
		Output: 'src-gen'
		Sources {
			source {
				'«SRC»'
			}
		}
	'''

}
