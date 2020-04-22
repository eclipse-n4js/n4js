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
package org.eclipse.n4js.antlr.syntaxcoloring

import com.google.inject.Inject
import org.eclipse.xtext.AbstractElement
import org.eclipse.xtext.AbstractRule
import org.eclipse.xtext.Action
import org.eclipse.xtext.Alternatives
import org.eclipse.xtext.Assignment
import org.eclipse.xtext.CrossReference
import org.eclipse.xtext.EnumLiteralDeclaration
import org.eclipse.xtext.EnumRule
import org.eclipse.xtext.Grammar
import org.eclipse.xtext.Group
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.TerminalRule
import org.eclipse.xtext.UnorderedGroup
import org.eclipse.xtext.xtext.generator.CodeConfig
import org.eclipse.xtext.xtext.generator.grammarAccess.GrammarAccessExtensions
import org.eclipse.xtext.xtext.generator.parser.antlr.AbstractAntlrGrammarWithActionsGenerator
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrOptions

import static extension org.eclipse.xtext.GrammarUtil.*
import static extension org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammarGenUtil.*

/**
 * Custom highlighting parser grammar generator derived from src/org/eclipse/n4js/antlr/syntaxcoloring/ParserGrammar.xpt.
 */
class N4JSAntlrHighlightingGrammarGenerator extends AbstractAntlrGrammarWithActionsGenerator {

	@Inject CodeConfig codeConfig
	@Inject extension N4JSHighlightingGrammarNaming naming
	@Inject extension GrammarAccessExtensions grammarUtil

	protected override getGrammarNaming() {
		naming
	}

	override protected compileParser(Grammar it, AntlrOptions options) '''
		«codeConfig.fileHeader»
		parser grammar «grammarNaming.getParserGrammar(it).simpleName»;
		«compileParserOptions(options)»
		«compileParserHeader(options)»
		«compileParserMembers(options)»
		«compileRuleCatch(options)»
		«compileRules(options)»
	'''

	/**
	 * just returns null since no lexer need to be generated,
	 *  although non-combined grammar configuration is active.
	 * can be removed once https://github.com/eclipse/xtext-core/issues/158
	 * is resolved and the solution is leveraged here.
	 */
	override protected compileLexer(Grammar it, AntlrOptions options) {
		null
	}

	/** called via 'compileParserHeader(...)' */
	override protected compileParserImports(Grammar it, AntlrOptions options) '''
		import org.eclipse.xtext.*;
		import org.eclipse.xtext.parser.*;
		import org.eclipse.xtext.parser.impl.*;
		import org.eclipse.xtext.parser.antlr.XtextTokenStream;
		import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
		«IF !allParserRules().map[ eAllContents().filter(UnorderedGroup).toIterable ].flatten.isEmpty && options.backtrack»
		import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper.UnorderedGroupState;
		«ENDIF»
		import «grammarAccess»;
		import org.eclipse.n4js.ui.editor.syntaxcoloring.AbstractInternalHighlightingAntlrParser;
	'''

	protected override compileParserMembers(Grammar it, AntlrOptions options) '''

		@members {

		«IF options.backtrack»
		/*
		  This grammar contains a lot of empty actions to work around a bug in ANTLR.
		  Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
		*/

		«ENDIF»
		 	private «grammarAccess.simpleName» grammarAccess;

		    public «internalParserClass.simpleName»(TokenStream input, «grammarAccess.simpleName» grammarAccess) {
		        this(input);
		        this.grammarAccess = grammarAccess;
		    }

			protected boolean forcedRewind(int marker, boolean advance) { return true; } // overridden in subtype
			protected void promoteEOL() {} // overridden in subtype
			protected boolean hasDisallowedEOL() { return false; } // overridden in subtype
			protected boolean isTypeRefNoTrailingLineBreak() { return true; } // overridden in subtype
			protected void setInRegularExpression() {} // overridden in subtype
			protected void setInTemplateSegment() {} // overridden in subtype
			protected void announce(Token token, AbstractElement element) {} // overridden in subtype
			protected void announce(Token start, Token stop, AbstractElement element) {} // overridden in subtype
		}
	'''

	protected override compileRuleCatch(Grammar it, AntlrOptions options) '''

		@rulecatch {
		    catch (RecognitionException re) {
		        recover(input,re);
		    }
		}
	'''

	override protected compileRules(Grammar it, AntlrOptions options) '''
		«FOR rule: (allParserRules).filter[rule | rule.isCalled(it)]»

			«rule.compileRule(it, options)»
		«ENDFOR»
		«FOR rule: (allEnumRules).filter[rule | rule.isCalled(it)]»

			«rule.compileRule(it, options)»
		«ENDFOR»
	'''

	protected override _compileRule(ParserRule it, Grammar grammar, AntlrOptions options) '''
		«IF isValidEntryRule()»
			«compileEntryRule(grammar, options)»
		«ENDIF»

		«compileEBNF(options)»
	'''

	protected def String compileEntryRule(ParserRule it, Grammar grammar, AntlrOptions options) '''
		// Entry rule «originalElement.entryRuleName»
		«originalElement.entryRuleName»
		«compileEntryInit(options)»
			:
			«ruleName»
			EOF;
		«compileEntryFinally(options)»
	'''

	protected override String compileEBNF(AbstractRule it, AntlrOptions options) '''
		// Rule «originalElement.name»
		«ruleName»
		«IF it instanceof ParserRule && originalElement.datatypeRule»
			«(it as ParserRule).compileDataTypeInit(options)»:
			«dataTypeEbnf(alternatives, true)»
		«ELSE»
			«compileInit(options)»:
			«ebnf(alternatives, options, true)»
		«ENDIF»
		;
		«compileFinally(options)»
	'''

	protected override compileInit(AbstractRule it, AntlrOptions options) {
		switch it {
			ParserRule: '''
					@init {
						«IF definesHiddenTokens || definesUnorderedGroups(options)»
							«compileInitHiddenTokens(options)»
							«compileInitUnorderedGroups(options)»
						«ENDIF»
					}'''
			default: ''
		}
	}

	protected def compileDataTypeInit(ParserRule it, AntlrOptions options) '''
		@init {
			«IF "REGEX_LITERAL" == originalElement.name»
				setInRegularExpression();
			«ENDIF»
			«IF "TemplateExpressionEnd" == originalElement.name»
				setInTemplateSegment();
			«ENDIF»
			«IF "Semi" == originalElement.name»
				int marker = input.mark();
				// Promote EOL if appropriate
				promoteEOL();
			«ENDIF»
			«IF definesHiddenTokens || definesUnorderedGroups(options)»
				«compileInitHiddenTokens(options)»
				«compileInitUnorderedGroups(options)»
			«ENDIF»
		}
	'''

	protected override String ebnf(AbstractElement it, AntlrOptions options, boolean supportActions) '''
		«IF mustBeParenthesized»(
			«ebnfPredicate(options)»«ebnf2(options, supportActions)»
		)«ELSE»«ebnf2(options, supportActions)»«ENDIF»«cardinality»
	'''

	protected def withoutActions(Iterable<AbstractElement> elements) {
		elements.filter[
			!(it instanceof Action)
		]
	}

	protected override String _ebnf2(Alternatives it, AntlrOptions options, boolean supportActions) '''
		«FOR element:elements.withoutActions SEPARATOR '\n    |'»«element.ebnf(options, supportActions)»«ENDFOR»
	'''

	protected override String _ebnf2(Group it, AntlrOptions options, boolean supportActions) '''
		«FOR element:elements.withoutActions»«element.ebnf(options, supportActions)»«ENDFOR»
	'''

	protected override String _ebnf2(EnumLiteralDeclaration it, AntlrOptions options, boolean supportActions) '''
		«IF supportActions»«literal.originalElement.gaElementIdentifier»=«ENDIF»«keywordHelper.getRuleName(literal.value)»«
		 IF supportActions» {
			announce($«literal.originalElement.gaElementIdentifier», grammarAccess.«originalElement.gaRuleElementAccessor()»);
		}«ENDIF»
	'''

	protected override String _dataTypeEbnf2(Alternatives it, boolean supportActions) '''
		«FOR e:elements.withoutActions SEPARATOR '\n    |'»«e.dataTypeEbnf(supportActions)»«ENDFOR»
	'''

	protected override String _dataTypeEbnf2(Group it, boolean supportActions) '''
		«FOR e:elements.withoutActions»«e.dataTypeEbnf(supportActions)»«ENDFOR»
	'''

	protected override String _dataTypeEbnf2(Keyword it, boolean supportActions) {
		'''
		«IF ";" == value»
			«IF supportActions»«originalElement.gaElementIdentifier»=«ENDIF»«
			 keywordHelper.getRuleName(value)»«
			 IF supportActions» {
				announce($«originalElement.gaElementIdentifier», grammarAccess.«originalElement.gaRuleElementAccessor()»);
			}«ENDIF»
			| EOF { forcedRewind(marker, true) }?
			| RULE_EOL { forcedRewind(marker, true) }?
			| RULE_ML_COMMENT { forcedRewind(marker, true) }?
			| RightCurlyBracket { forcedRewind(marker, false) }?
		«ELSE»
			«IF supportActions»«originalElement.gaElementIdentifier»=«ENDIF»«
			 keywordHelper.getRuleName(value)»«
			 IF supportActions» {
				announce($«originalElement.gaElementIdentifier», grammarAccess.«originalElement.gaRuleElementAccessor()»);
			}«ENDIF»
		«ENDIF»
		'''
	}

	protected override String _dataTypeEbnf2(RuleCall it, boolean supportActions) {
		rule.ruleName()
	}

	override protected crossrefEbnf(AbstractRule it, RuleCall call, CrossReference ref, boolean supportActions) {
		switch it {
			EnumRule:
				ruleName()
			ParserRule: '''
					«IF supportActions»«
						ref.terminal.originalElement.gaElementIdentifier»=«ENDIF»«
					ruleName»«
					IF supportActions»{
						announce($«ref.terminal.originalElement.gaElementIdentifier».start, $«ref.terminal.originalElement.gaElementIdentifier».stop, grammarAccess.«ref.containingAssignment.originalElement.gaRuleElementAccessor()»);
					}
					«ENDIF»
				'''
			TerminalRule: '''
					«IF supportActions»«
						ref.terminal.originalElement.gaElementIdentifier»=«ENDIF»«
					 ruleName()»«
					 IF supportActions»{
						announce($«ref.terminal.originalElement.gaElementIdentifier», grammarAccess.«ref.containingAssignment.originalElement.gaRuleElementAccessor()»);
					}
					«ENDIF»
				'''
		}
	}

	override protected _assignmentEbnf(AbstractElement it, Assignment assignment, AntlrOptions options, boolean supportActions) {
		ebnf(options, supportActions)
	}

	override protected _assignmentEbnf(RuleCall it, Assignment assignment, AntlrOptions options, boolean supportActions) {
		switch rule {
			EnumRule:
				rule.ruleName()
			ParserRule: '''
					«IF !supportActions && "TypeRefNoTrailingLineBreak" == rule.originalElement.name»
						{isTypeRefNoTrailingLineBreak()}?
					«ELSE»
						«IF supportActions»«originalElement.gaElementIdentifier»=«ENDIF»«
						 rule.ruleName()»«
						 IF supportActions»{
							announce($«originalElement.gaElementIdentifier».start, $«originalElement.gaElementIdentifier».stop, grammarAccess.«assignment.originalElement.gaRuleElementAccessor()»);
						}
						«ENDIF»
					«ENDIF»
				'''
			TerminalRule: '''
					«IF supportActions»«originalElement.gaElementIdentifier»=«ENDIF»«
					 rule.ruleName»«
					 IF supportActions»{
						announce($«originalElement.gaElementIdentifier», grammarAccess.«assignment.originalElement.gaRuleElementAccessor()»);
					}
					«ENDIF»
				'''
		}
	}

	override protected _assignmentEbnf(CrossReference it, Assignment assignment, AntlrOptions options, boolean supportActions) {
		terminal.crossrefEbnf(it, supportActions)
	}

	protected override String _ebnf2(Action it, AntlrOptions options, boolean supportActions) {
		''
	}

	protected override String _ebnf2(Keyword it, AntlrOptions options, boolean supportActions) '''
		«IF supportActions»«originalElement.gaElementIdentifier()»=«ENDIF»«
		 keywordHelper.getRuleName(value)»
		«IF "return" == value || "throw" == value || "break" == value || "continue" == value»«
		 IF supportActions» {
			promoteEOL();
			announce($«originalElement.gaElementIdentifier», grammarAccess.«originalElement.gaRuleElementAccessor()»);
		}
		«ENDIF»«
		 ELSE»«
		 IF supportActions» {
			announce($«originalElement.gaElementIdentifier()», grammarAccess.«originalElement.gaRuleElementAccessor()»);
		}
		«ENDIF»«
		 ENDIF»
	'''

	override protected _ebnf2(RuleCall it, AntlrOptions options, boolean supportActions) '''
		«IF "TypeRef" == rule.originalElement.name && AbstractRule.isInstance(eContainer().originalElement) && "TypeRefNoTrailingLineBreak" == (eContainer().originalElement as AbstractRule).name»
			«IF supportActions»«originalElement.gaElementIdentifier»=«ENDIF»«rule.ruleName()» {
				if (hasDisallowedEOL()) {
					if (state.backtracking>0) {
						state.failed=true;
					}
				}
				«IF supportActions»
					announce($«originalElement.gaElementIdentifier».start, $«originalElement.gaElementIdentifier».stop, grammarAccess.«originalElement.gaRuleElementAccessor()»);
				«ENDIF»
			}
		«ELSEIF "LeftHandSideExpression" == rule.originalElement.name»
			«IF supportActions»«originalElement.gaElementIdentifier»=«ENDIF»«rule.ruleName()»
			«IF supportActions»
			{
				if (input.LA(1) == PlusSignPlusSign || input.LA(1) == HyphenMinusHyphenMinus) {
					promoteEOL();
				}
				announce($«originalElement.gaElementIdentifier».start, $«originalElement.gaElementIdentifier».stop, grammarAccess.«originalElement.gaRuleElementAccessor()»);
			}
			«ENDIF»
		«ELSE»
			«IF supportActions»«originalElement.gaElementIdentifier»=«ENDIF»«
			 rule.ruleName()»«
			 IF supportActions && TerminalRule.isInstance(rule)»
				{ announce($«originalElement.gaElementIdentifier», grammarAccess.«originalElement.gaRuleElementAccessor()»); }
			«ELSEIF supportActions && ParserRule.isInstance(rule)»
				{ announce($«originalElement.gaElementIdentifier».start, $«originalElement.gaElementIdentifier».stop, grammarAccess.«originalElement.gaRuleElementAccessor()»); }
			«ENDIF»
		«ENDIF»
	'''
}
