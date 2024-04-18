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
package org.eclipse.n4js.formatting2;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.groupBy;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sortBy;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.formatting2.AbstractFormatter2;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatting;
import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.HiddenRegionReplacer;
import org.eclipse.xtext.formatting2.internal.TextReplacerMerger;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionExtensions;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 *
 */
@SuppressWarnings("restriction")
@FinalFieldsConstructor
class N4JSGenericFormatter {

	N4JSGrammarAccess grammarAccess;
	ITextRegionExtensions trExtensions;

	public static int PRIO_1 = -10;
	public static int PRIO_2 = -9;
	public static int PRIO_3 = -8;

	public N4JSGenericFormatter(N4JSGrammarAccess grammarAccess, ITextRegionExtensions trExtensions) {
		this.grammarAccess = grammarAccess;
		this.trExtensions = trExtensions;
	}

	public void formatColon(EObject semanticElement, IFormattableDocument document) {
		for (ISemanticRegion colon : trExtensions.allRegionsFor(semanticElement).keywords(":")) {
			ISemanticRegion sr = document.prepend(colon, hrf -> {
				hrf.noSpace();
				hrf.setNewLines(0);
				hrf.setPriority(PRIO_3);
			});
			document.append(sr, hrf -> {
				hrf.oneSpace();
				hrf.setPriority(PRIO_2);
			});
		}
	}

	/**
	 * Formats whitespace around already present semicolons (;) and inserts new semicolons where the parser expects
	 * them.
	 */
	public void formatSemicolons(EObject script, IFormattableDocument document) {
		for (ISemanticRegion region : trExtensions.allRegionsFor(script).ruleCallsTo(grammarAccess.getSemiRule())) {
			String text = region.getText();
			IHiddenRegion previous = region.getPreviousHiddenRegion();
			if (text == ";") {

				// there is already a ";" so let's format it
				document.prepend(region, hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
					hrf.highPriority();
				});
			} else if (region.getNextSemanticRegion() != null && "}".equals(region.getNextSemanticRegion().getText())
					&& !region.isMultiline()) {
				// do nothing
			} else if (previous.containsComment()) {

				// we're behind a comment - insert semicolon before the comment
				ITextSegment insertAt = region.getTextRegionAccess().regionForOffset(previous.getOffset(), 0);
				document.addReplacer(new InsertSemi(insertAt, ";"));
			} else if (text.trim().isEmpty()) {
				// Don't eat up white space here.
				// Look for first line break and replace with ";\n":
				int lbIdx = text.indexOf("\n");
				if (lbIdx >= 0) {
					ITextSegment replaceRegion = region.getTextRegionAccess().regionForOffset(region.getOffset(),
							lbIdx + 1);
					document.addReplacer(new InsertSemi(replaceRegion, ";\n"));
				} else {
					// the text region only contains whitespace, e.g. so let's insert a ; and a "\n"
					document.addReplacer(new InsertSemi(region, ";"));
				}
			} else {

				// we probably are the comment, so let's prefix it with ;
				ITextSegment insertAt = region.getTextRegionAccess().regionForOffset(region.getOffset(), 0);
				document.addReplacer(new InsertSemi(insertAt, ";"));
			}
		}
	}

	/**
	 * Format whitespace around (), [], and {}
	 *
	 * When multiple pairs of (), [], or {} open in the same line, indentation is only applied for the innermost pair.
	 */
	public void formatParenthesisBracketsAndBraces(EObject script, IFormattableDocument document) {
		List<Pair<ISemanticRegion, ISemanticRegion>> all = new ArrayList<>();
		all.addAll(trExtensions.allRegionsFor(script).keywordPairs("(", ")"));
		all.addAll(trExtensions.allRegionsFor(script).keywordPairs("{", "}"));
		all.addAll(trExtensions.allRegionsFor(script).keywordPairs("[", "]"));

		Map<Integer, List<Pair<ISemanticRegion, ISemanticRegion>>> byLine = groupBy(all,
				p -> p.getKey().getLineRegions().get(0).getOffset());

		for (Entry<Integer, List<Pair<ISemanticRegion, ISemanticRegion>>> e : byLine.entrySet()) {
			List<Pair<ISemanticRegion, ISemanticRegion>> bracePairsInSameLine = sortBy(e.getValue(),
					p -> p.getKey().getOffset());
			Pair<ISemanticRegion, ISemanticRegion> outermost = bracePairsInSameLine.get(0);
			Pair<ISemanticRegion, ISemanticRegion> innermost = last(bracePairsInSameLine);

			// keep track of lastOpen/lastClose to avoid formatting the HiddenRegion twice if that hidden region
			// is located directly between two brackets. Then, first.append[] would collide with second.prepend[].
			IHiddenRegion lastOpen = null;
			IHiddenRegion lastClose = null;
			for (Pair<ISemanticRegion, ISemanticRegion> pair : bracePairsInSameLine) {
				ISemanticRegion open = pair.getKey();
				ISemanticRegion close = pair.getValue();
				if (open.getPreviousHiddenRegion() != lastOpen && lastOpen != null) {
					// something between last opening and this one; null-check for first opening-> don't force noSpace
					// here
					document.prepend(open, hrf -> {
						hrf.noSpace();
						hrf.setPriority(PRIO_1);
					});
				}
				if (pair != innermost) {
					document.append(open, hrf -> {
						hrf.noSpace();
						hrf.setPriority(PRIO_1);
					});
					document.prepend(close, hrf -> {
						hrf.noSpace();
						hrf.setPriority(PRIO_1);
					});
				}
				if (close.getNextHiddenRegion() != lastClose) {
					if (pair == outermost) {
						// close.appendNewLine(document)
					} else {
						document.append(close, hrf -> {
							hrf.noSpace();
							hrf.setPriority(PRIO_1);
						});
					}
				}
				lastOpen = open.getNextHiddenRegion();
				lastClose = close.getPreviousHiddenRegion();
			}

			ISemanticRegion open = innermost.getKey();
			ISemanticRegion close = innermost.getValue();
			if (open.getNextSemanticRegion() == close && !open.getNextHiddenRegion().isMultiline()) { // empty
																										// brace-pair
				document.append(open, hrf -> {
					hrf.noSpace();
					hrf.setPriority(PRIO_1);
				});
			} // otherwise, if there is a newline before the innermost closing bracket, we want to format the surrounded
				// tokens multiline style.
			else if (close.getPreviousHiddenRegion().isMultiline()) {
				appendNewLine(document.prepend(close, hrf -> {
					hrf.newLine();
					hrf.setPriority(PRIO_1);
				}), document);
				document.append(open, hrf -> {
					hrf.newLine();
					hrf.setPriority(PRIO_1);
				});
				document.interior(innermost, hrf -> hrf.indent());
				for (ISemanticRegion comma : trExtensions.regionFor(open.getSemanticElement()).keywords(",")) {
					// FIXME: bug in toString: println(comma.toString);
					ISemanticRegion sr = document.prepend(comma, hrf -> {
						hrf.noSpace();
						hrf.setPriority(PRIO_1);
					});
					document.append(sr, hfr -> {
						// If dangling comma, then a conflict arises with new line of close.
						// Avoid here by using Prio_2
						hfr.setNewLines(1, 1, 2);
						hfr.setPriority(PRIO_2);
					});
				}
			} // otherwise, format the tokens into a single line.
			else {
				if (document.getRequest().getPreferences()
						.getPreference(N4JSFormatterPreferenceKeys.FORMAT_SURROUND_PAREN_CONTENT_WITH_SPACE)) {
					// configured way to have single space in parenthesised expression.
					document.prepend(close, hrf -> {
						hrf.oneSpace();
						hrf.setPriority(PRIO_1);
					});
					document.append(open, hrf -> {
						hrf.oneSpace();
						hrf.setPriority(PRIO_1);
					});
				}
				for (ISemanticRegion comma : trExtensions.regionFor(open.getSemanticElement()).keywords(",")) {
					ISemanticRegion sr = document.prepend(comma, hrf -> {
						hrf.noSpace();
						hrf.setPriority(PRIO_1);
					});
					document.append(sr, hrf -> {
						hrf.oneSpace();
						hrf.setPriority(PRIO_1);
					});
				}
			}
		}
	}

	public ISemanticRegion appendNewLine(ISemanticRegion appendAfter, IFormattableDocument doc) {
		EObject semi = appendAfter.getNextSemanticRegion() == null ? null
				: appendAfter.getNextSemanticRegion().getGrammarElement();
		if (semi instanceof RuleCall && ((RuleCall) semi).getRule() == grammarAccess.getSemiRule()) {
			// noop, handled by org.eclipse.n4js.formatting2.N4JSGenericFormatter.formatSemicolons(EObject,
			// IFormattableDocument)
		} else {
			doc.append(appendAfter, hrf -> {
				hrf.newLine();
				hrf.setPriority(PRIO_1);
			});
		}
		return appendAfter;
	}

	static interface InsertSemiBase extends ITextReplacer {
		// marker interface
	}

	@Accessors
	static class InsertSemi implements InsertSemiBase {
		ITextSegment region;
		String text;

		public InsertSemi(ITextSegment region, String text) {
			this.region = region;
			this.text = text;
		}

		@Override
		public ITextReplacerContext createReplacements(ITextReplacerContext context) {
			context.addReplacement(region.replaceWith(text));
			return context;
		}

		@Override
		public ITextSegment getRegion() {
			return this.region;
		}

		public String getText() {
			return this.text;
		}
	}

	static class InsertSemiFollowedByTextReplacer implements InsertSemiBase {
		InsertSemiBase insertSemi;
		ITextReplacer textReplacer;
		ITextSegment region;

		InsertSemiFollowedByTextReplacer(InsertSemiBase insertSemi, ITextReplacer textReplacer) {
			this.insertSemi = insertSemi;
			this.textReplacer = textReplacer;
			this.region = insertSemi.getRegion().merge(textReplacer.getRegion());
		}

		@Override
		public ITextReplacerContext createReplacements(ITextReplacerContext context) {
			// First insert semicolon
			ITextReplacerContext replContext = insertSemi.createReplacements(context).withReplacer(textReplacer);
			// Then apply the text replacer
			return textReplacer.createReplacements(replContext);
		}

		@Override
		public ITextSegment getRegion() {
			return this.region;
		}
	}

	static class IndentHandlingTextReplaceMerger extends TextReplacerMerger {
		private final static Logger LOGGER = Logger.getLogger(IndentHandlingTextReplaceMerger.class);

		AbstractFormatter2 fmt;

		IndentHandlingTextReplaceMerger(AbstractFormatter2 formatter) {
			super(formatter);
			fmt = formatter;
		}

		/**
		 * Overridden for special case of {@link InsertSemi} & {@link HiddenRegionReplacer} merging. Calls super
		 * implementation if no InsertSemi object is involved
		 */
		@Override
		public ITextReplacer merge(List<? extends ITextReplacer> conflicting) {
			if (findFirst(conflicting, tr -> tr instanceof InsertSemiBase) == null) {
				// standard case, but not handled as we want by super because due to ASI there can be
				// HiddenRegionReplacer that have equal offsets and length but are not identical.
				List<HiddenRegionReplacer> hrf = toList(filter(conflicting, HiddenRegionReplacer.class));
				if (hrf.size() == conflicting.size()) {
					IHiddenRegionFormatting merged = fmt.createHiddenRegionFormattingMerger()
							.merge(toList(map(hrf, hrr -> hrr.getFormatting())));
					if (merged != null) {
						return fmt.createHiddenRegionReplacer(hrf.get(0).getRegion(), merged);
					}
				}
				return super.merge(conflicting);
			}

			// there is an insertSemi.
			List<? extends ITextReplacer> semiReplacements = toList(
					filter(conflicting, tr -> tr instanceof InsertSemiBase));
			List<? extends ITextReplacer> otherReplacements = toList(
					filter(conflicting, tr -> !(tr instanceof InsertSemiBase)));

			if (semiReplacements.size() != 1 || otherReplacements.size() != 1) {
				LOGGER.warn("""
						Unhandled merge-case: "
							"Semis replacer («semiReplacements.size») :«semiReplacements»
							"Non-Semi replacer ( «otherReplacements.size»  «otherReplacements»
						""");
				return null; // null creates merge Exception
			}
			// exactly one:
			InsertSemiBase semiRepl = (InsertSemiBase) semiReplacements.get(0);
			ITextReplacer otherRepl = otherReplacements.get(0);

			if (otherRepl instanceof HiddenRegionReplacer) {
				return new InsertSemiFollowedByTextReplacer(semiRepl, otherRepl);
			}

			return null;
		}
	}
}
