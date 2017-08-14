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
package org.eclipse.n4js.formatting2

import org.eclipse.n4js.services.N4JSGrammarAccess
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.formatting2.IFormattableDocument
import org.eclipse.xtext.formatting2.ITextReplacer
import org.eclipse.xtext.formatting2.ITextReplacerContext
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionExtensions
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment
import org.eclipse.xtext.formatting2.internal.TextReplacerMerger
import org.eclipse.xtext.formatting2.AbstractFormatter2
import java.util.List
import org.eclipse.n4js.utils.Log
import org.eclipse.xtext.formatting2.internal.HiddenRegionReplacer

/**
 *
 */
@FinalFieldsConstructor class N4JSGenericFormatter {

	val extension N4JSGrammarAccess
	val extension ITextRegionExtensions

	public static val PRIO_1 = -10
	public static val PRIO_2 = -9
	public static val PRIO_3 = -8

	def void formatColon(EObject semanticElement, extension IFormattableDocument document) {
		for (colon : semanticElement.allRegionsFor.keywords(":")) {
			colon.prepend[noSpace; newLines = 0; priority = PRIO_3]
				 .append[oneSpace; priority = PRIO_2];
		}
	}

	/**
	 * Formats whitespace around already present semicolons (;) and inserts new semicolons where the parser expects them.
	 */
	def void formatSemicolons(EObject script, extension IFormattableDocument document) {
		for (region : script.allRegionsFor.ruleCallsTo(semiRule)) {
			val text = region.text;
			val previous = region.previousHiddenRegion;
			if (text == ";") {

				// there is already a ";" so let's format it
				region.prepend[noSpace; newLines = 0; highPriority];
			} else if(region.nextSemanticRegion?.text == "}" && !region.isMultiline){
				// do nothing
			}
			else if (previous.containsComment) {

				// we're behind a comment - insert semicolon before the comment
				val insertAt = region.textRegionAccess.regionForOffset(previous.offset, 0)
				document.addReplacer(new InsertSemi(insertAt, ";"));
			} else if (text.trim.isEmpty) {
				// Don't eat up white space here.
				// Look for first line break and replace with ";\n":
				val lbIdx = text.indexOf("\n");
				if( lbIdx >= 0 ) {
					val replaceRegion = region.textRegionAccess.regionForOffset(region.offset, lbIdx+1 );
					document.addReplacer(new InsertSemi(replaceRegion, ";\n"));
				} else {
					// the text region only contains whitespace, e.g. so let's insert a ; and a "\n"
					document.addReplacer(new InsertSemi(region, ";"));
				}
			} else {

				// we probably are the comment, so let's prefix it with ;
				val insertAt = region.textRegionAccess.regionForOffset(region.offset, 0);
				document.addReplacer(new InsertSemi(insertAt, ";"));
			}
		}
	}

	/**
	 * Format whitespace around (), [], and {}
	 *
	 * When multiple pairs of (), [], or {} open in the same line, indentation is only applied for the innermost pair.
	 */
	def void formatParenthesisBracketsAndBraces(EObject script, extension IFormattableDocument document) {
		val all = newArrayList()
		all += script.allRegionsFor.keywordPairs("(", ")")
		all += script.allRegionsFor.keywordPairs("{", "}")
		all += script.allRegionsFor.keywordPairs("[", "]")

		val byLine = all.groupBy[key.lineRegions.head.offset]

		for (e : byLine.entrySet) {
			val bracePairsInSameLine = e.value.sortBy[key.offset]
			val outermost = bracePairsInSameLine.head
			val innermost = bracePairsInSameLine.last

			// keep track of lastOpen/lastClose to avoid formatting the HiddenRegion twice if that hidden region
			// is located directly between two brackets. Then, first.append[] would collide with second.prepend[].
			var IHiddenRegion lastOpen = null
			var IHiddenRegion lastClose = null
			for (pair : bracePairsInSameLine) {
				val open = pair.key
				val close = pair.value
				if (open.previousHiddenRegion != lastOpen && lastOpen !== null) { // something between last opening and this one; null-check for first opening-> don't force noSpace here
					open.prepend[noSpace; priority = PRIO_1]
				}
				if (pair !== innermost) {
					open.append[noSpace; priority = PRIO_1]
					close.prepend[noSpace; priority = PRIO_1]
				}
				if (close.nextHiddenRegion != lastClose) {
					if (pair === outermost) {
//						close.appendNewLine(document)
					} else {
						close.append[noSpace; priority = PRIO_1]
					}
				}
				lastOpen = open.nextHiddenRegion
				lastClose = close.previousHiddenRegion
			}

			val ISemanticRegion open = innermost.key;
			val ISemanticRegion close = innermost.value;
			if (open.nextSemanticRegion == close && !open.nextHiddenRegion.isMultiline) { // empty brace-pair
				open.append[noSpace; priority = PRIO_1];
			} // otherwise, if there is a newline before the innermost closing bracket, we want to format the surrounded tokens multiline style.
			else if (close.previousHiddenRegion.isMultiline) {
				close.prepend[newLine; priority = PRIO_1].appendNewLine(document);
				open.append[newLine; priority = PRIO_1];
				innermost.interior[indent];
				for (comma : open.semanticElement.regionFor.keywords(",")) {
					// FIXME: bug in toString: println(comma.toString);
					comma.prepend[noSpace; priority = PRIO_1]
					.append[
						// If dangling comma, then a conflict arises with new line of close.
						// Avoid here by using Prio_2
						setNewLines(1,1,2);
						priority = PRIO_2
					];
				}
			} // otherwise, format the tokens into a single line.
			else {
				if( document.request.preferences.getPreference( N4JSFormatterPreferenceKeys.FORMAT_SURROUND_PAREN_CONTENT_WITH_SPACE ) ) { // configured way to have single space in parenthesised expression.
					close.prepend[oneSpace; priority = PRIO_1]
					open.append[oneSpace; priority = PRIO_1]
				}
				for (comma : open.semanticElement.regionFor.keywords(",")) {
					comma.prepend[noSpace; priority = PRIO_1].append[oneSpace; priority = PRIO_1]
				}
			}
		}
	}

	def ISemanticRegion appendNewLine(ISemanticRegion appendAfter, extension IFormattableDocument doc) {
		val semi = appendAfter.nextSemanticRegion?.grammarElement
		if (semi instanceof RuleCall && (semi as RuleCall)?.rule == semiRule) {
			// noop, handled by org.eclipse.n4js.formatting2.N4JSGenericFormatter.formatSemicolons(EObject, IFormattableDocument)
		} else {
			appendAfter.append[newLine; priority = PRIO_1]
		}
		return appendAfter
	}
}

interface InsertSemiBase extends ITextReplacer {}

@Accessors class InsertSemi implements InsertSemiBase {
	val ITextSegment region
	val String text

	override createReplacements(ITextReplacerContext context) {
		context.addReplacement(region.replaceWith(text))
		return context;
	}
}

class InsertSemiFollowedByTextReplacer implements InsertSemiBase {
	val InsertSemiBase insertSemi;
	val ITextReplacer textReplacer;
	val ITextSegment region;
	
	new(InsertSemiBase insertSemi, ITextReplacer textReplacer) {
		this.insertSemi = insertSemi;
		this.textReplacer = textReplacer;
		this.region = insertSemi.region.merge(textReplacer.region)
	}

	override createReplacements(ITextReplacerContext context) {
		// First insert semicolon
		var replContext = insertSemi.createReplacements(context).withReplacer(textReplacer);
		// Then apply the text replacer
		return textReplacer.createReplacements(replContext);
	}
	
	override getRegion() {
		return this.region;
	}
}

@Log
class IndentHandlingTextReplaceMerger extends TextReplacerMerger {
	val AbstractFormatter2 fmt

	new(AbstractFormatter2 formatter) {
		super(formatter)
		fmt = formatter
	}

	/** Overridden for special case of {@link InsertSemi} & {@link HiddenRegionReplacer} merging.
	 * Calls super implementation if no InsertSemi object is involved */
	override merge(List<? extends ITextReplacer> conflicting) {
		if(conflicting.findFirst[it instanceof InsertSemiBase] === null ) {
 			// standard case, but not handled as we want by super because due to ASI there can be
 			// HiddenRegionReplacer that have equal offsets and length but are not identical.
 			val hrf = conflicting.filter(HiddenRegionReplacer).toList
 			if(hrf.size === conflicting.size) {
 				val merged = fmt.createHiddenRegionFormattingMerger.merge(hrf.map[formatting])
 				if(merged !== null) {
 					return fmt.createHiddenRegionReplacer(hrf.head.region, merged)
 				}
 			}
			return super.merge(conflicting);
		}

		// there is an insertSemi.
		val semiReplacements = conflicting.filter[it instanceof InsertSemiBase].toList;
		val otherReplacements = conflicting.filter[!(it instanceof InsertSemiBase)].toList;

		if( semiReplacements.size !== 1  || otherReplacements.size !== 1  ) {
			logger.warn( '''
			Unhandled merge-case: "
				"Semis replacer («semiReplacements.size») :«semiReplacements»
				"Non-Semi replacer ( «otherReplacements.size»  «otherReplacements»
			 ''');
			return null; // null creates merge Exception
		}
		// exactly one:
		val semiRepl = semiReplacements.get(0) as InsertSemiBase;
		val otherRepl = otherReplacements.get(0);
		
		if( otherRepl instanceof HiddenRegionReplacer ) {
			return new InsertSemiFollowedByTextReplacer(semiRepl, otherRepl);
		}

		return null;
	}
}
