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
package org.eclipse.n4js.tests.document

import org.eclipse.n4js.N4JSUiInjectorProvider
import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeToPartitionMapper
import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.IDocumentPartitioner
import org.eclipse.jface.text.ITypedRegion
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.junit.Test
import org.junit.runner.RunWith
import com.google.inject.Inject
import org.junit.Assert
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper
import java.util.List

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSUiInjectorProvider)
class PartitioningPluginTest extends Assert {
	XtextDocument document

	@Inject
	def setUpDocument(XtextDocument document, IDocumentPartitioner partitioner) {
		partitioner.connect(document)
		document.setDocumentPartitioner(partitioner)
		this.document = document
	}

	@Test def void testInitialPartitioning_01() {
		val input = 'var x = /r/; var y = `ab${""}cd`'
		val expectation = #[
			'var x = ' -> IDocument.DEFAULT_CONTENT_TYPE,
			'/r/' -> TokenTypeToPartitionMapper.REG_EX_PARTITION,
			'; var y = ' -> IDocument.DEFAULT_CONTENT_TYPE,
			'`ab${' -> TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION,
			'""' -> TerminalsTokenTypeToPartitionMapper.STRING_LITERAL_PARTITION,
			'}cd`' -> TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION
		]

		input.assertPartitions(expectation)
	}

	@Test def void testInitialPartitioning_02() {
		val input = 'var x = /=r/; var y = `ab${}cd`'
		val expectation = #[
			'var x = ' -> IDocument.DEFAULT_CONTENT_TYPE,
			'/=r/' -> TokenTypeToPartitionMapper.REG_EX_PARTITION,
			'; var y = ' -> IDocument.DEFAULT_CONTENT_TYPE,
			'`ab${' -> TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION,
			'}cd`' -> TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION
		]

		input.assertPartitions(expectation)
	}

	@Test def void testInitialPartitioning_03() {
		val input = '// /'
		val expectation = #[
			'// /' -> TokenTypeToPartitionMapper.SL_COMMENT_PARTITION
		]

		input.assertPartitions(expectation)
	}

	@Test def void testInitialPartitioning_04() {
		val input = ' // /\n'
		val expectation = #[
			' ' -> IDocument.DEFAULT_CONTENT_TYPE,
			'// /' -> TokenTypeToPartitionMapper.SL_COMMENT_PARTITION,
			'\n' -> IDocument.DEFAULT_CONTENT_TYPE
		]

		input.assertPartitions(expectation)
	}

	@Test def void testInitialPartitioning_05() {
		val input = '`a${b}c${d}e`'
		val expectation = #[
			'`a${' -> TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION,
			'b' -> IDocument.DEFAULT_CONTENT_TYPE,
			'}c${' -> TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION,
			'd' -> IDocument.DEFAULT_CONTENT_TYPE,
			'}e`' -> TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION
		]

		input.assertPartitions(expectation)
	}

	@Test def void testPartitioningAfterModify_01() throws BadLocationException {
		var input = 'var x = /r/; var y = `ab${""}cd`'
		document.set(input)
		document.replace(input.indexOf("$") + 1, 0, " ")

		val expectation = #[
			'var x = ' -> IDocument.DEFAULT_CONTENT_TYPE,
			'/r/' -> TokenTypeToPartitionMapper.REG_EX_PARTITION,
			'; var y = ' -> IDocument.DEFAULT_CONTENT_TYPE,
			'`ab$ {""}cd`' -> TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION
		]
		expectation.assertPartitions
	}

	@Test def void testPartitioningAfterModify_02() throws BadLocationException {
		var input = 'var x = /r/;'
		document.set(input)
		document.replace(input.indexOf("r/"), 0, "=")

		val expectation = #[
			'var x = ' -> IDocument.DEFAULT_CONTENT_TYPE,
			'/=r/' -> TokenTypeToPartitionMapper.REG_EX_PARTITION,
			';' -> IDocument.DEFAULT_CONTENT_TYPE
		]
		expectation.assertPartitions
	}

	@Test def void testPartitioningAfterModify_03() throws BadLocationException {
		var input = 'var x = /r/g;'
		document.set(input)
		document.replace(input.indexOf("/"), 1, "")

		val expectation = #[
			'var x = r/g;' -> IDocument.DEFAULT_CONTENT_TYPE
		]
		expectation.assertPartitions
	}

	@Test def void testPartitioningAfterModify_04() throws BadLocationException {
		var input = 'var x = /r/;'
		document.set(input)
		document.replace(input.indexOf("r/"), 1, "")

		val expectation = #[
			'var x = ' -> IDocument.DEFAULT_CONTENT_TYPE,
			'//;' -> TokenTypeToPartitionMapper.SL_COMMENT_PARTITION
		]
		expectation.assertPartitions
	}

	@Test def void testPartitioningAfterModify_05() throws BadLocationException {
		var input = '// '
		document.set(input)
		document.replace(input.indexOf(" ") + 1, 0, "/")

		val expectation = #[
			'// /' -> TokenTypeToPartitionMapper.SL_COMMENT_PARTITION
		]
		expectation.assertPartitions
	}

	@Test def void testPartitioningAfterModify_06() throws BadLocationException {
		var input = '// /'
		document.set(input)
		document.replace(input.indexOf(" /") + 2, 0, "*")

		val expectation = #[
			'// /*' -> TokenTypeToPartitionMapper.SL_COMMENT_PARTITION
		]
		expectation.assertPartitions
	}

	@Test def void testMLComment() throws BadLocationException {
		val input = '/* some comment */ var x = 1'
		val expectation = #[
			'/* some comment */' -> TokenTypeToPartitionMapper.COMMENT_PARTITION,
			' var x = 1' -> IDocument.DEFAULT_CONTENT_TYPE
		]

		input.assertPartitions(expectation)
	}

	@Test def void testJSDocComment() throws BadLocationException {
		val input = '/** js doc comment */ var x = 1'
		val expectation = #[
			'/** js doc comment */' -> TokenTypeToPartitionMapper.JS_DOC_PARTITION,
			' var x = 1' -> IDocument.DEFAULT_CONTENT_TYPE
		]

		input.assertPartitions(expectation)
	}

	@Test def void testMLCommentToJSDoc() throws BadLocationException {
		val input = '/* some comment */ var x = 1'

		document.set(input)
		document.replace(input.indexOf("*"), 0, "*")
		val expectation = #[
			'/** some comment */' -> TokenTypeToPartitionMapper.JS_DOC_PARTITION,
			' var x = 1' -> IDocument.DEFAULT_CONTENT_TYPE
		]

		expectation.assertPartitions
	}

	@Test def void testJsDocCommentToMLComment() throws BadLocationException {
		val input = '/** some comment */ var x = 1'

		document.set(input)
		document.replace(input.indexOf("*"), 1, "")
		val expectation = #[
			'/* some comment */' -> TokenTypeToPartitionMapper.COMMENT_PARTITION,
			' var x = 1' -> IDocument.DEFAULT_CONTENT_TYPE
		]

		expectation.assertPartitions
	}

	private def void assertPartitions(String input, List<Pair<String, String>> expectation) {
		document.set(input)
		expectation.assertPartitions
	}

	private def void assertPartitions(List<Pair<String, String>> expectation) {
		var List<ITypedRegion> partitions = document.getDocumentPartitioner().computePartitioning(0, document.length)
		assertEquals(partitions.toString, expectation.size, partitions.size)
		partitions.forEach [ region, idx |
			val exp = expectation.get(idx)
			assertEquals(exp.key, exp.key.length, region.length)
			assertEquals(exp.key, exp.value, region.type)
		]
	}
}
