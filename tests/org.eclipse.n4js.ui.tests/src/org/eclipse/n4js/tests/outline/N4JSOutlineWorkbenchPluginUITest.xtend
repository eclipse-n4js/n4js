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
package org.eclipse.n4js.tests.outline

import java.util.LinkedHashMap
import java.util.List
import java.util.Map.Entry
import org.eclipse.n4js.dirtystate.testdata.TestFiles
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider
import org.junit.Test

/**
 * Ensure to configure the right product when running this plugin-ui tests:
 * Main / Program to Run: Run a product: com.enfore.n4js.product.product
 */
class N4JSOutlineWorkbenchPluginUITest extends AbstractOutlineWorkbenchTest {

	override protected shouldCreateProjectStructure() {
		true
	}

	override protected getFileName() {
		"Child"
	}

	override protected getModelAsText() {
		String.valueOf(TestFiles.classChild)
	}

	override protected getModuleFolder() {
		TestFiles.moduleFolder
	}

	override protected getProjectName() {
		"SimpleOutlineTest"
	}

	@Test
	def void testOutlineContribution() {
		editor.editorSite.page.activate(editor)
		assertEquals("First opened editor doesn't became active", editor, editor.editorSite.page.activeEditor)
		treeViewer.expandAll
		assertEquals("Initially the tree view should contain one item.", 1, treeViewer.tree.getItem(0).itemCount)
		treeViewer.expandAll
		assertEquals("Filter action does not work.", 1, treeViewer.tree.getItem(0).itemCount)
	}

	@Test
	def void testNoNPEs() throws Exception {
		assertNoException("xyz")
		assertNoException("a = ;")
	}

	@Test
	def void testFileWithNonTrivialTypeExpressions() throws Exception {
		val model = '''
			interface I1<T1,T2,T3> {}
			interface I2 {}
			
			class OutlineTest {
				field: intersection{I1<int, int, int>, I2}
				fieldWithLongTypeDescription: union{int, intersection{int, union{int, string}}}
				a(param: union{int, string}) {}
				b(param: intersection{I1<int, int, int>, I2}) {}
				c(param: type{I1}) {}
				d(param: constructor{I1}) {}
				e(param: {function(union{int, string}): int}): void {}
				f(param: (intersection{I1<string, string, string>, I2})=>int) {}
			}
			
			function a(param: union{int, string}) {}
			function <B extends I2> b(param0: int, param: intersection{I1<B, union{string, int}, string>, I2}, param2: number) {}
			function c(param: type{I1}) {}
			function d(param: constructor{I1}) {}
			function e(param: {function(union{int, string}): int}) {}
			function f(param: (union{int, string, intersection{number,int, string}, int})=>union{int, string, intersection{number,int, string}, int}): union{int, string, intersection{number,int, string}, int}{return 1;}
			function g(param: union{int, string, intersection{number,int, string}, int}): union{int, string, intersection{int, string, number}, int} { return 3; }
			function g1(param: union{int, string, intersection{number,int, string},number}): union{int, string, intersection{int, string, number}, string} { return 3; }
			function none(fct: ()=>void) {}
			export public var fct = 3;
			export var c: constructor{? super OutlineTest}
		'''
		val rootNode = assertNoException(model);
		assertEquals("There is only one top level node", rootNode.children.length, 1);

		val documentNode = rootNode.children.get(0);

		// test document level nodes
		assertNodeChildrenText(documentNode, #[
			"I1<T1, T2, T3>",
			"I2",
			"OutlineTest",
			"a(int | string): void",
			"b(int, I1<B,string | int,string> & I2, number)<B>: void",
			"c(type{I1}): void",
			"d(constructor{I1}): void",
			"e((int | string)=>int): void",
			"f((int | string | (number & int & string) | …)=>int | string | (number & int & string) | …): int | string | (number & int & string) | …",
			"g(int | string | (number & int & string) | …): int | string | (int & string & number) | …",
			"g1(int | string | (number & int & string) | …): int | string | (int & string & number) | …",
			"none(()=>void): void",
			"fct: int",
			"c: constructor{? super OutlineTest}"
		]);

		// test class level nodes
		assertNodeChildrenText(documentNode.children.get(2), #[
			"field: I1<int,int,int> & I2",
			"fieldWithLongTypeDescription: int | (int & (int | string))",
			"a(int | string): void",
			"b(I1<int,int,int> & I2): void",
			"c(type{I1}): void",
			"d(constructor{I1}): void",
			"e((int | string)=>int): void",
			"f((I1<string,string,string> & I2)=>int): void"
		])
	}

	def void assertNodeChildrenText(IOutlineNode parentNode, List<String> textExpectations) {
		val nodeChildren = parentNode.children;

		assertEquals("The number of children wasn't correct.", textExpectations.length, nodeChildren.length);

		textExpectations.forEach [ expectation, index |
			assertEquals(expectation, nodeChildren.get(index).text.toString);
		]
	}

	def void assertNodeChildrenTextDeep(IOutlineNode parentNode, LinkedHashMap<String, List<String>> textExpectations) {
		val nodeChildren = parentNode.children;

		assertEquals("The number of children wasn't correct.", textExpectations.keySet.length, nodeChildren.length);

		var int typeIndex = 0;
		for (Entry<String, List<String>> typeExpectation: textExpectations.entrySet) {
			val String typeName = typeExpectation.key;
			val List<String> memberExpectations = typeExpectation.value;
			val IOutlineNode typeNode = nodeChildren.get(typeIndex);
			assertEquals(typeName, typeNode.text.toString);
			assertEquals("The number of children for " + typeName + " wasn't correct", memberExpectations.length,
				typeNode.children.length);
			assertNodeChildrenText(typeNode, memberExpectations);
			typeIndex++;
		}

	}

	@Test
	def void testClassWithTypedField() throws Exception {
		val model = '''
			class A {
				field1: string;
			}
		'''
		val rootNode = assertNoException(model)
		assertEquals(1, rootNode.children.size)
		val IOutlineNode moduleNode = rootNode.children.get(0)
		assertNode(moduleNode, "pr0_0pa0/Child", 1)
		val classNode = moduleNode.children.get(0)
		assertNode(classNode, "A", 1)
		val fieldNode = classNode.children.get(0)
		assertNode(fieldNode, "field1: string", 0)
		assertEquals(model.lastIndexOf("field1: string"), fieldNode.getFullTextRegion.getOffset)
		assertEquals("field1: string;".length, fieldNode.getFullTextRegion.getLength)
		assertEquals(model.lastIndexOf("field1"), fieldNode.getSignificantTextRegion.getOffset)
		assertEquals("field1".length, fieldNode.getSignificantTextRegion.getLength)
	}

	protected def void assertNode(IOutlineNode node, String text, int numChildren) {
		assertEquals(numChildren, node.getChildren.size);
		assertEquals(text, node.getText.toString);
	}

	protected def IOutlineNode assertNoException(String model) throws Exception {
		assertNoException(model, null);
	}

	protected def IOutlineNode assertNoException(String model, String modeID) throws Exception {
		return try {
			val document = editor.document => [set(model)]
			val IOutlineTreeProvider treeProvider = getInstance(IOutlineTreeProvider)
			assertTrue(
				"treeProvider is " + treeProvider.class + ", should be instanceof ModeAware",
				treeProvider instanceof IOutlineTreeProvider.ModeAware
			);
			val modeAwareTreeProvider = treeProvider as IOutlineTreeProvider.ModeAware;
			val firstMode = modeAwareTreeProvider.currentMode;
			while (modeID !== null && modeAwareTreeProvider.currentMode.id != modeID) {
				assertNotEquals("Mode " + modeID + " not found", firstMode, modeAwareTreeProvider.nextMode);
				modeAwareTreeProvider.currentMode = modeAwareTreeProvider.nextMode
			}

			treeProvider.createRoot(document) => [traverseChildren]
		} catch (Exception exc) {
			exc.printStackTrace
			fail("Exception in outline tree construction")
			return null // unreachable
		}
	}

	private def void traverseChildren(IOutlineNode node) {
		node.children.forEach[traverseChildren]
	}

	/**
	 * Tests different modes (with and without - inherited members). Since the tests directly tests
	 * the IOutlineTreeProvider (and not the displayed test), the modes used for toggling the quick out line 
	 * view are used to test the result of this provider. In the "normal" outline view, these items are filtered
	 * via a toggle button after creation -- this filter is not tested here.
	 */
	@Test
	def void testModes() throws Exception {
		val model = '''
			interface I 			{ fooI() {} }
			interface J extends I 	{ fooJ() {} }
			class A implements J 	{ fooA() {} }
			class B extends A 		{ fooB() {} @Override fooI() {}}
			class C extends B 		{ fooC() {} }
		'''
		var rootNode = assertNoException(model, "owned");
		assertEquals("There is only one top level node", rootNode.children.length, 1);

		var documentNode = rootNode.children.get(0);

		// test document level nodes
		assertNodeChildrenTextDeep(documentNode, newLinkedHashMap(
			"I" -> #["fooI(): void"],
			"J" -> #["fooJ(): void"],
			"A" -> #["fooA(): void"],
			"B" -> #["fooB(): void", "fooI(): void"],
			"C" -> #["fooC(): void"]
		));

		rootNode = assertNoException(model, "inherited");
		assertEquals("There is only one top level node", rootNode.children.length, 1);

		documentNode = rootNode.children.get(0);

		// test document level nodes
		assertNodeChildrenTextDeep(documentNode, newLinkedHashMap(
			"I" -> #["fooI(): void"],
			"J" -> #["fooJ(): void", "fooI(): void - inherited from I"],
			"A" -> #["fooA(): void", "fooJ(): void - consumed from J", "fooI(): void - consumed from I"],
			"B" ->
				#["fooB(): void", "fooI(): void", "fooA(): void - inherited from A", "fooJ(): void - consumed from J"],
			"C" ->
				#["fooC(): void", "fooB(): void - inherited from B", "fooI(): void - inherited from B",
					"fooA(): void - inherited from A", "fooJ(): void - consumed from J"]
		));
	}

	/**
	 * Special case for - inherited case, basically testing N4JSOutlineTree#isLeaf.
	 */
	@Test
	def void testModesWithMemberlessSubclass() throws Exception {
		val model = '''
			class A { foo; }
			class B extends A {}
		'''
		var rootNode = assertNoException(model, "owned");
		assertEquals("There is only one top level node", rootNode.children.length, 1);

		var documentNode = rootNode.children.get(0);

		// test document level nodes
		assertNodeChildrenTextDeep(documentNode, newLinkedHashMap(
			"A" -> #["foo: any"],
			"B" -> #[]
		));

		rootNode = assertNoException(model, "inherited");
		assertEquals("There is only one top level node", rootNode.children.length, 1);

		documentNode = rootNode.children.get(0);

		// test document level nodes
		assertNodeChildrenTextDeep(documentNode, newLinkedHashMap(
			"A" -> #["foo: any"],
			"B" -> #["foo: any - inherited from A"]
		));
	}

}
