/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.ui.editor.doubleClicking.AbstractPartitionDoubleClickSelector 
 * 	in bundle  org.eclipse.xtext.ui
 * 	available under the terms of the Eclipse Public License 2.0
 * 	Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * 
 * and org.eclipse.jdt.internal.ui.text.java.JavadocDoubleClickStrategy
 *	in bundle org.eclipse.jdt.ui
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2000, 2011 IBM Corporation and others.
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.editor

import org.eclipse.n4js.ui.editor.N4JSDoubleClickStrategyProvider.MyBreakIterator
import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeToPartitionMapper
import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.ITextDoubleClickStrategy
import org.eclipse.jface.text.Region
import org.eclipse.jface.text.source.ISourceViewer
import org.eclipse.xtext.ui.editor.doubleClicking.AbstractPartitionDoubleClickSelector
import org.eclipse.xtext.ui.editor.doubleClicking.DoubleClickStrategyProvider
import org.eclipse.xtext.ui.editor.model.CommonBreakIterator

import static extension java.lang.Character.*
import static extension org.eclipse.jface.text.TextUtilities.*

/**
 * Double click strategy that is aware of template and regex partitions.
 */
class N4JSDoubleClickStrategyProvider extends DoubleClickStrategyProvider {

	override ITextDoubleClickStrategy getStrategy(ISourceViewer sourceViewer, String contentType,
		String documentPartitioning) {
		if (TokenTypeToPartitionMapper.REG_EX_PARTITION == contentType) {
			return new AbstractPartitionDoubleClickSelector(documentPartitioning) {

				override protected findExtendedDoubleClickSelection(extension IDocument document, int offset) {
					try {
						val region = document.getPartition(documentPartitioning, offset, true)
						var doSelect = false
						var int trimRight = 0
						if (offset === region.offset + 1 || offset >= region.offset + region.length - 2) {
							doSelect = offset === region.offset + 1
							var textInRegion = get(region.offset, region.length)
							var lastSlash = textInRegion.last('/')
							if (lastSlash !== 0) {
								doSelect = doSelect || offset === region.offset + lastSlash - 1
								trimRight = region.length - lastSlash
							}
						}
						if (doSelect) {
							val int trimLeft = 1
							return new Region(region.offset + trimLeft, region.length - trimLeft - trimRight)
						}
					} catch (BadLocationException e) {
						return null
					}
					return null
				}

			}
		}
		if (TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION == contentType) {
			return new AbstractPartitionDoubleClickSelector(documentPartitioning) {

				override protected findExtendedDoubleClickSelection(extension IDocument document, int offset) {
					try {
						val region = document.getPartition(documentPartitioning, offset, true)
						var doSelect = offset === region.offset + 1
						var trimRight = 0
						val endOffset = region.offset + region.length - 1
						val lastChar = getChar(endOffset)
						if (lastChar.is('`')) {
							doSelect = doSelect || offset === endOffset
							trimRight = 1
						} else if (lastChar.is('{') && getChar(endOffset - 1).is('$')) {
							doSelect = doSelect || offset === endOffset - 1
							trimRight = 2
						}
						if (doSelect) {
							var trimLeft = 1 // ` or }
							return new Region(region.offset + trimLeft, region.length - trimLeft - trimRight)
						}

					} catch (BadLocationException e) {
						return null
					}
					return null
				}

			}
		}
		if (TokenTypeToPartitionMapper.JS_DOC_PARTITION == contentType) {
			return new AbstractPartitionDoubleClickSelector(documentPartitioning) {

				/**
				 * Copied from Xtend's Javadoc comment handling.
				 * Allows to select the complete <code>@param</code> instead of just the literal <code>param</code>.
				 * Copied from org.eclipse.jdt.internal.ui.text.java.JavadocDoubleClickStrategy.
				 */
				override protected findExtendedDoubleClickSelection(extension IDocument document, int position) {
					try {
						var match = super.findExtendedDoubleClickSelection(document, position)
						if (match !== null)
							return match
						val word = document.findWord(position)
						val line = getLineInformationOfOffset(position)
						if (position === line.offset + line.length)
							return null
						var start = word.offset
						val end = start + word.length
						if (start > 0 && getChar(start - 1).is('@') && getChar(start).isJavaIdentifierPart && (
									start === 1 || getChar(start - 2).isWhitespace || getChar(start - 2).is('{'))) {
							// double click after @ident
							start--
						} else if (end === position && end === start + 1 && end < line.offset + line.length &&
							getChar(end).is('@')) {
							// double click before " @ident"
							return document.findExtendedDoubleClickSelection(position + 1)
						}
						if (start === end)
							return null
						return new Region(start, end - start)
					} catch (BadLocationException x) {
						return null
					}

				}

				override protected CommonBreakIterator createBreakIterator() {
					return new MyBreakIterator()
				}
			}
		}
		return super.getStrategy(sourceViewer, contentType, documentPartitioning)
	}

	static class MyBreakIterator extends CommonBreakIterator {
		static class Braces extends Other {
			override protected isValid(char ch) {
				return ch.is('{') || ch.is('}')
			}
		}

		static class Parentheses extends Other {
			override protected isValid(char ch) {
				return ch.is('(') || ch.is(')')
			}
		}

		val braces = new Braces
		val parentheses = new Parentheses

		new() {
			super(false)
		}

		override protected Run getRun(char ch) {
			if (braces.isValid(ch)) {
				return braces
			}
			if (parentheses.isValid(ch)) {
				return parentheses
			}
			return super.getRun(ch)
		}
	}

	def static boolean is(char a, char b) {
		a === b
	}

	def int last(String s, char b) {
		s.lastIndexOf(b)
	}
}
