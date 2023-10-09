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
package org.eclipse.n4js.antlr.syntaxcoloring;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.UnorderedGroup;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xtext.generator.CodeConfig;
import org.eclipse.xtext.xtext.generator.grammarAccess.GrammarAccessExtensions;
import org.eclipse.xtext.xtext.generator.model.TypeReference;
import org.eclipse.xtext.xtext.generator.parser.antlr.AbstractAntlrGrammarWithActionsGenerator;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammarGenUtil;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrOptions;
import org.eclipse.xtext.xtext.generator.parser.antlr.GrammarNaming;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;

/**
 * Custom highlighting parser grammar generator derived from
 * src/org/eclipse/n4js/antlr/syntaxcoloring/ParserGrammar.xpt.
 */
@SuppressWarnings("all")
public class N4JSAntlrHighlightingGrammarGenerator extends AbstractAntlrGrammarWithActionsGenerator {
	@Inject
	private CodeConfig codeConfig;

	@Inject
	@Extension
	private N4JSHighlightingGrammarNaming naming;

	@Inject
	@Extension
	private GrammarAccessExtensions grammarUtil;

	@Override
	protected GrammarNaming getGrammarNaming() {
		return this.naming;
	}

	@Override
	protected CharSequence compileParser(final Grammar it, final AntlrOptions options) {
		StringConcatenation _builder = new StringConcatenation();
		String _fileHeader = this.codeConfig.getFileHeader();
		_builder.append(_fileHeader);
		_builder.newLineIfNotEmpty();
		_builder.append("parser grammar ");
		String _simpleName = this.getGrammarNaming().getParserGrammar(it).getSimpleName();
		_builder.append(_simpleName);
		_builder.append(";");
		_builder.newLineIfNotEmpty();
		CharSequence _compileParserOptions = this.compileParserOptions(it, options);
		_builder.append(_compileParserOptions);
		_builder.newLineIfNotEmpty();
		CharSequence _compileParserHeader = this.compileParserHeader(it, options);
		_builder.append(_compileParserHeader);
		_builder.newLineIfNotEmpty();
		String _compileParserMembers = this.compileParserMembers(it, options);
		_builder.append(_compileParserMembers);
		_builder.newLineIfNotEmpty();
		String _compileRuleCatch = this.compileRuleCatch(it, options);
		_builder.append(_compileRuleCatch);
		_builder.newLineIfNotEmpty();
		CharSequence _compileRules = this.compileRules(it, options);
		_builder.append(_compileRules);
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	/**
	 * just returns null since no lexer need to be generated, although non-combined grammar configuration is active. can
	 * be removed once https://github.com/eclipse/xtext-core/issues/158 is resolved and the solution is leveraged here.
	 */
	@Override
	protected CharSequence compileLexer(final Grammar it, final AntlrOptions options) {
		return null;
	}

	/**
	 * called via 'compileParserHeader(...)'
	 */
	@Override
	protected String compileParserImports(final Grammar it, final AntlrOptions options) {
		StringConcatenation _builder = new StringConcatenation();
		_builder.append("""
				import org.eclipse.xtext.*;
				import org.eclipse.xtext.parser.*;
				import org.eclipse.xtext.parser.impl.*;
				import org.eclipse.xtext.parser.antlr.XtextTokenStream;
				import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
				""");
		{
			if (((!isEmpty(Iterables.<UnorderedGroup> concat(
					ListExtensions.<ParserRule, Iterable<UnorderedGroup>> map(GrammarUtil.allParserRules(it),
							((Function1<ParserRule, Iterable<UnorderedGroup>>) (ParserRule it_1) -> {
								return IteratorExtensions.<UnorderedGroup> toIterable(
										Iterators.<UnorderedGroup> filter(it_1.eAllContents(), UnorderedGroup.class));
							})))))
					&& options.isBacktrack())) {
				_builder.append("import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper.UnorderedGroupState;");
				_builder.newLine();
			}
		}
		_builder.append("import ");
		TypeReference _grammarAccess = this.grammarUtil.getGrammarAccess(it);
		_builder.append(_grammarAccess);
		_builder.append(";");
		_builder.newLineIfNotEmpty();
		_builder.append("import org.eclipse.n4js.ui.editor.syntaxcoloring.AbstractInternalHighlightingAntlrParser;");
		_builder.newLine();
		return _builder.toString();
	}

	@Override
	protected String compileParserMembers(final Grammar it, final AntlrOptions options) {
		StringConcatenation _builder = new StringConcatenation();
		_builder.newLine();
		_builder.append("@members {");
		_builder.newLine();
		_builder.newLine();
		{
			boolean _isBacktrack = options.isBacktrack();
			if (_isBacktrack) {
				_builder.append("""
						/*
						  This grammar contains a lot of empty actions to work around a bug in ANTLR.
						  Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
						*/
						""");
			}
		}
		_builder.append(" \t");
		_builder.append("private ");
		String _simpleName = this.grammarUtil.getGrammarAccess(it).getSimpleName();
		_builder.append(_simpleName, " \t");
		_builder.append(" grammarAccess;");
		_builder.newLineIfNotEmpty();
		_builder.newLine();
		_builder.append("    ");
		_builder.append("public ");
		String _simpleName_1 = this.naming.getInternalParserClass(it).getSimpleName();
		_builder.append(_simpleName_1, "    ");
		_builder.append("(TokenStream input, ");
		String _simpleName_2 = this.grammarUtil.getGrammarAccess(it).getSimpleName();
		_builder.append(_simpleName_2, "    ");
		_builder.append(" grammarAccess) {");
		_builder.newLineIfNotEmpty();
		_builder.append("""
				       this(input);
				       this.grammarAccess = grammarAccess;
				   }
				""");

		_builder.append(
				"""
							protected boolean forcedRewind(int position) { return true; } // overridden in subtype
							protected void promoteEOL() {} // overridden in subtype
							protected boolean hasDisallowedEOL() { return false; } // overridden in subtype
							protected boolean hasDisallowedWhiteSpace() { return false; } // overridden in subtype
							protected boolean isTypeRefNoTrailingLineBreak() { return true; } // overridden in subtype
							protected void setInRegularExpression() {} // overridden in subtype
							protected void setInTemplateSegment() {} // overridden in subtype
							protected void announce(Token token, AbstractElement element) {} // overridden in subtype
							protected void announce(Token start, Token stop, AbstractElement element) {} // overridden in subtype
						}
						""");
		return _builder.toString();
	}

	@Override
	protected String compileRuleCatch(final Grammar it, final AntlrOptions options) {
		return """
				@rulecatch {
				    catch (RecognitionException re) {
				        recover(input,re);
				    }
				}
				""";
	}

	@Override
	protected CharSequence compileRules(final Grammar it, final AntlrOptions options) {
		StringConcatenation _builder = new StringConcatenation();
		{
			final Function1<ParserRule, Boolean> _function = (ParserRule rule) -> {
				return Boolean.valueOf(this.grammarUtil.isCalled(rule, it));
			};
			Iterable<ParserRule> _filter = IterableExtensions.<ParserRule> filter(GrammarUtil.allParserRules(it),
					_function);
			for (final ParserRule rule : _filter) {
				_builder.newLine();
				CharSequence _compileRule = this.compileRule(rule, it, options);
				_builder.append(_compileRule);
				_builder.newLineIfNotEmpty();
			}
		}
		{
			final Function1<EnumRule, Boolean> _function_1 = (EnumRule rule_1) -> {
				return Boolean.valueOf(this.grammarUtil.isCalled(rule_1, it));
			};
			Iterable<EnumRule> _filter_1 = IterableExtensions.<EnumRule> filter(GrammarUtil.allEnumRules(it),
					_function_1);
			for (final EnumRule rule_1 : _filter_1) {
				_builder.newLine();
				CharSequence _compileRule_1 = this.compileRule(rule_1, it, options);
				_builder.append(_compileRule_1);
				_builder.newLineIfNotEmpty();
			}
		}
		return _builder;
	}

	@Override
	protected CharSequence _compileRule(final ParserRule it, final Grammar grammar, final AntlrOptions options) {
		StringConcatenation _builder = new StringConcatenation();
		{
			boolean _isValidEntryRule = AntlrGrammarGenUtil.isValidEntryRule(it);
			if (_isValidEntryRule) {
				String _compileEntryRule = this.compileEntryRule(it, grammar, options);
				_builder.append(_compileEntryRule);
				_builder.newLineIfNotEmpty();
			}
		}
		_builder.newLine();
		String _compileEBNF = this.compileEBNF(it, options);
		_builder.append(_compileEBNF);
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	protected String compileEntryRule(final ParserRule it, final Grammar grammar, final AntlrOptions options) {
		StringConcatenation _builder = new StringConcatenation();
		_builder.append("// Entry rule ");
		String _entryRuleName = this.grammarUtil.entryRuleName(AntlrGrammarGenUtil.<ParserRule> getOriginalElement(it));
		_builder.append(_entryRuleName);
		_builder.newLineIfNotEmpty();
		String _entryRuleName_1 = this.grammarUtil
				.entryRuleName(AntlrGrammarGenUtil.<ParserRule> getOriginalElement(it));
		_builder.append(_entryRuleName_1);
		_builder.newLineIfNotEmpty();
		CharSequence _compileEntryInit = this.compileEntryInit(it, options);
		_builder.append(_compileEntryInit);
		_builder.newLineIfNotEmpty();
		_builder.append("\t");
		_builder.append(":");
		_builder.newLine();
		_builder.append("\t");
		String _ruleName = this.grammarUtil.ruleName(it);
		_builder.append(_ruleName, "\t");
		_builder.newLineIfNotEmpty();
		_builder.append("\t");
		_builder.append("EOF;");
		_builder.newLine();
		CharSequence _compileEntryFinally = this.compileEntryFinally(it, options);
		_builder.append(_compileEntryFinally);
		_builder.newLineIfNotEmpty();
		return _builder.toString();
	}

	@Override
	protected String compileEBNF(final AbstractRule it, final AntlrOptions options) {
		StringConcatenation _builder = new StringConcatenation();
		_builder.append("// Rule ");
		String _name = AntlrGrammarGenUtil.<AbstractRule> getOriginalElement(it).getName();
		_builder.append(_name);
		_builder.newLineIfNotEmpty();
		String _ruleName = this.grammarUtil.ruleName(it);
		_builder.append(_ruleName);
		_builder.newLineIfNotEmpty();
		{
			if (((it instanceof ParserRule)
					&& GrammarUtil.isDatatypeRule(AntlrGrammarGenUtil.<AbstractRule> getOriginalElement(it)))) {
				CharSequence _compileDataTypeInit = this.compileDataTypeInit(((ParserRule) it), options);
				_builder.append(_compileDataTypeInit);
				_builder.append(":");
				_builder.newLineIfNotEmpty();
				String _dataTypeEbnf = this.dataTypeEbnf(it.getAlternatives(), true);
				_builder.append(_dataTypeEbnf);
				_builder.newLineIfNotEmpty();
			} else {
				String _compileInit = this.compileInit(it, options);
				_builder.append(_compileInit);
				_builder.append(":");
				_builder.newLineIfNotEmpty();
				String _ebnf = this.ebnf(it.getAlternatives(), options, true);
				_builder.append(_ebnf);
				_builder.newLineIfNotEmpty();
			}
		}
		_builder.append(";");
		_builder.newLine();
		String _compileFinally = this.compileFinally(it, options);
		_builder.append(_compileFinally);
		_builder.newLineIfNotEmpty();
		return _builder.toString();
	}

	@Override
	protected String compileInit(final AbstractRule it, final AntlrOptions options) {
		String _switchResult = null;
		boolean _matched = false;
		if (it instanceof ParserRule) {
			_matched = true;
			StringConcatenation _builder = new StringConcatenation();
			_builder.append("@init {");
			_builder.newLine();
			{
				if ((((ParserRule) it).isDefinesHiddenTokens()
						|| this.grammarUtil.definesUnorderedGroups(((ParserRule) it), options))) {
					_builder.append("\t");
					CharSequence _compileInitHiddenTokens = this.compileInitHiddenTokens(it, options);
					_builder.append(_compileInitHiddenTokens, "\t");
					_builder.newLineIfNotEmpty();
					_builder.append("\t");
					CharSequence _compileInitUnorderedGroups = this.compileInitUnorderedGroups(it, options);
					_builder.append(_compileInitUnorderedGroups, "\t");
					_builder.newLineIfNotEmpty();
				}
			}
			_builder.append("}");
			_switchResult = _builder.toString();
		}
		if (!_matched) {
			_switchResult = "";
		}
		return _switchResult;
	}

	protected CharSequence compileDataTypeInit(final ParserRule it, final AntlrOptions options) {
		StringConcatenation _builder = new StringConcatenation();
		_builder.append("@init {");
		_builder.newLine();
		{
			String _name = AntlrGrammarGenUtil.<ParserRule> getOriginalElement(it).getName();
			boolean _equals = Objects.equal("REGEX_LITERAL", _name);
			if (_equals) {
				_builder.append("\t");
				_builder.append("setInRegularExpression();");
				_builder.newLine();
			}
		}
		{
			String _name_1 = AntlrGrammarGenUtil.<ParserRule> getOriginalElement(it).getName();
			boolean _equals_1 = Objects.equal("TemplateExpressionEnd", _name_1);
			if (_equals_1) {
				_builder.append("\t");
				_builder.append("setInTemplateSegment();");
				_builder.newLine();
			}
		}
		{
			String _name_2 = AntlrGrammarGenUtil.<ParserRule> getOriginalElement(it).getName();
			boolean _equals_2 = Objects.equal("Semi", _name_2);
			if (_equals_2) {
				_builder.append("\t");
				_builder.append("int position = input.index();");
				_builder.newLine();
				_builder.append("\t");
				_builder.append("// Promote EOL if appropriate");
				_builder.newLine();
				_builder.append("\t");
				_builder.append("promoteEOL();");
				_builder.newLine();
			}
		}
		{
			if ((it.isDefinesHiddenTokens() || this.grammarUtil.definesUnorderedGroups(it, options))) {
				_builder.append("\t");
				CharSequence _compileInitHiddenTokens = this.compileInitHiddenTokens(it, options);
				_builder.append(_compileInitHiddenTokens, "\t");
				_builder.newLineIfNotEmpty();
				_builder.append("\t");
				CharSequence _compileInitUnorderedGroups = this.compileInitUnorderedGroups(it, options);
				_builder.append(_compileInitUnorderedGroups, "\t");
				_builder.newLineIfNotEmpty();
			}
		}
		_builder.append("}");
		_builder.newLine();
		return _builder;
	}

	@Override
	protected String ebnf(final AbstractElement it, final AntlrOptions options, final boolean supportActions) {
		StringConcatenation _builder = new StringConcatenation();
		{
			boolean _mustBeParenthesized = this.mustBeParenthesized(it);
			if (_mustBeParenthesized) {
				_builder.append("(");
				_builder.newLineIfNotEmpty();
				_builder.append("\t");
				String _ebnfPredicate = this.ebnfPredicate(it, options);
				_builder.append(_ebnfPredicate, "\t");
				String _ebnf2 = this.ebnf2(it, options, supportActions);
				_builder.append(_ebnf2, "\t");
				_builder.newLineIfNotEmpty();
				_builder.append(")");
			} else {
				String _ebnf2_1 = this.ebnf2(it, options, supportActions);
				_builder.append(_ebnf2_1);
			}
		}
		String _cardinality = it.getCardinality();
		_builder.append(_cardinality);
		_builder.newLineIfNotEmpty();
		return _builder.toString();
	}

	protected Iterable<AbstractElement> withoutActions(final Iterable<AbstractElement> elements) {
		final Function1<AbstractElement, Boolean> _function = (AbstractElement it) -> {
			return Boolean.valueOf((!(it instanceof Action)));
		};
		return IterableExtensions.<AbstractElement> filter(elements, _function);
	}

	@Override
	protected String _ebnf2(final Alternatives it, final AntlrOptions options, final boolean supportActions) {
		StringConcatenation _builder = new StringConcatenation();
		{
			Iterable<AbstractElement> _withoutActions = this.withoutActions(it.getElements());
			boolean _hasElements = false;
			for (final AbstractElement element : _withoutActions) {
				if (!_hasElements) {
					_hasElements = true;
				} else {
					_builder.appendImmediate("\n    |", "");
				}
				String _ebnf = this.ebnf(element, options, supportActions);
				_builder.append(_ebnf);
			}
		}
		_builder.newLineIfNotEmpty();
		return _builder.toString();
	}

	@Override
	protected String _ebnf2(final Group it, final AntlrOptions options, final boolean supportActions) {
		StringConcatenation _builder = new StringConcatenation();
		{
			Iterable<AbstractElement> _withoutActions = this.withoutActions(it.getElements());
			for (final AbstractElement element : _withoutActions) {
				String _ebnf = this.ebnf(element, options, supportActions);
				_builder.append(_ebnf);
			}
		}
		_builder.newLineIfNotEmpty();
		return _builder.toString();
	}

	@Override
	protected String _ebnf2(final EnumLiteralDeclaration it, final AntlrOptions options, final boolean supportActions) {
		StringConcatenation _builder = new StringConcatenation();
		{
			if (supportActions) {
				String _gaElementIdentifier = this.grammarUtil
						.gaElementIdentifier(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it.getLiteral()));
				_builder.append(_gaElementIdentifier);
				_builder.append("=");
			}
		}
		String _ruleName = this.keywordHelper.getRuleName(it.getLiteral().getValue());
		_builder.append(_ruleName);
		{
			if (supportActions) {
				_builder.append(" {");
				_builder.newLineIfNotEmpty();
				_builder.append("\t");
				_builder.append("announce($");
				String _gaElementIdentifier_1 = this.grammarUtil
						.gaElementIdentifier(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it.getLiteral()));
				_builder.append(_gaElementIdentifier_1, "\t");
				_builder.append(", grammarAccess.");
				String _gaRuleElementAccessor = this.grammarUtil
						.gaRuleElementAccessor(AntlrGrammarGenUtil.<EnumLiteralDeclaration> getOriginalElement(it));
				_builder.append(_gaRuleElementAccessor, "\t");
				_builder.append(");");
				_builder.newLineIfNotEmpty();
				_builder.append("}");
			}
		}
		_builder.newLineIfNotEmpty();
		return _builder.toString();
	}

	@Override
	protected String _dataTypeEbnf2(final Alternatives it, final boolean supportActions) {
		StringConcatenation _builder = new StringConcatenation();
		{
			Iterable<AbstractElement> _withoutActions = this.withoutActions(it.getElements());
			boolean _hasElements = false;
			for (final AbstractElement e : _withoutActions) {
				if (!_hasElements) {
					_hasElements = true;
				} else {
					_builder.appendImmediate("\n    |", "");
				}
				String _dataTypeEbnf = this.dataTypeEbnf(e, supportActions);
				_builder.append(_dataTypeEbnf);
			}
		}
		_builder.newLineIfNotEmpty();
		return _builder.toString();
	}

	@Override
	protected String _dataTypeEbnf2(final Group it, final boolean supportActions) {
		StringConcatenation _builder = new StringConcatenation();
		{
			Iterable<AbstractElement> _withoutActions = this.withoutActions(it.getElements());
			for (final AbstractElement e : _withoutActions) {
				String _dataTypeEbnf = this.dataTypeEbnf(e, supportActions);
				_builder.append(_dataTypeEbnf);
			}
		}
		_builder.newLineIfNotEmpty();
		return _builder.toString();
	}

	@Override
	protected String _dataTypeEbnf2(final Keyword it, final boolean supportActions) {
		StringConcatenation _builder = new StringConcatenation();
		{
			String _value = it.getValue();
			boolean _equals = Objects.equal(";", _value);
			if (_equals) {
				{
					if (supportActions) {
						String _gaElementIdentifier = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaElementIdentifier);
						_builder.append("=");
					}
				}
				String _ruleName = this.keywordHelper.getRuleName(it.getValue());
				_builder.append(_ruleName);
				{
					if (supportActions) {
						_builder.append(" {");
						_builder.newLineIfNotEmpty();
						_builder.append("\t");
						_builder.append("announce($");
						String _gaElementIdentifier_1 = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaElementIdentifier_1, "\t");
						_builder.append(", grammarAccess.");
						String _gaRuleElementAccessor = this.grammarUtil
								.gaRuleElementAccessor(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaRuleElementAccessor, "\t");
						_builder.append(");");
						_builder.newLineIfNotEmpty();
						_builder.append("}");
					}
				}
				_builder.newLineIfNotEmpty();
				_builder.append("| EOF");
				_builder.newLine();
				_builder.append("| RULE_EOL");
				_builder.newLine();
				_builder.append("| RULE_ML_COMMENT");
				_builder.newLine();
				_builder.append("| RightCurlyBracket { forcedRewind(position) }?");
				_builder.newLine();
			} else {
				{
					if (supportActions) {
						String _gaElementIdentifier_2 = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaElementIdentifier_2);
						_builder.append("=");
					}
				}
				String _ruleName_1 = this.keywordHelper.getRuleName(it.getValue());
				_builder.append(_ruleName_1);
				{
					if (supportActions) {
						_builder.append(" {");
						_builder.newLineIfNotEmpty();
						_builder.append("\t");
						_builder.append("announce($");
						String _gaElementIdentifier_3 = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaElementIdentifier_3, "\t");
						_builder.append(", grammarAccess.");
						String _gaRuleElementAccessor_1 = this.grammarUtil
								.gaRuleElementAccessor(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaRuleElementAccessor_1, "\t");
						_builder.append(");");
						_builder.newLineIfNotEmpty();
						_builder.append("}");
					}
				}
				_builder.newLineIfNotEmpty();
			}
		}
		return _builder.toString();
	}

	@Override
	protected String _dataTypeEbnf2(final RuleCall it, final boolean supportActions) {
		return this.grammarUtil.ruleName(it.getRule());
	}

	@Override
	protected String crossrefEbnf(final AbstractRule it, final RuleCall call, final CrossReference ref,
			final boolean supportActions) {
		String _switchResult = null;
		boolean _matched = false;
		if (it instanceof EnumRule) {
			_matched = true;
			_switchResult = this.grammarUtil.ruleName(it);
		}
		if (!_matched) {
			if (it instanceof ParserRule) {
				_matched = true;
				StringConcatenation _builder = new StringConcatenation();
				{
					if (supportActions) {
						String _gaElementIdentifier = this.grammarUtil.gaElementIdentifier(
								AntlrGrammarGenUtil.<AbstractElement> getOriginalElement(ref.getTerminal()));
						_builder.append(_gaElementIdentifier);
						_builder.append("=");
					}
				}
				String _ruleName = this.grammarUtil.ruleName(it);
				_builder.append(_ruleName);
				{
					if (supportActions) {
						_builder.append("{");
						_builder.newLineIfNotEmpty();
						_builder.append("\t");
						_builder.append("announce($");
						String _gaElementIdentifier_1 = this.grammarUtil.gaElementIdentifier(
								AntlrGrammarGenUtil.<AbstractElement> getOriginalElement(ref.getTerminal()));
						_builder.append(_gaElementIdentifier_1, "\t");
						_builder.append(".start, $");
						String _gaElementIdentifier_2 = this.grammarUtil.gaElementIdentifier(
								AntlrGrammarGenUtil.<AbstractElement> getOriginalElement(ref.getTerminal()));
						_builder.append(_gaElementIdentifier_2, "\t");
						_builder.append(".stop, grammarAccess.");
						String _gaRuleElementAccessor = this.grammarUtil.gaRuleElementAccessor(AntlrGrammarGenUtil
								.<Assignment> getOriginalElement(GrammarUtil.containingAssignment(ref)));
						_builder.append(_gaRuleElementAccessor, "\t");
						_builder.append(");");
						_builder.newLineIfNotEmpty();
						_builder.append("}");
						_builder.newLine();
					}
				}
				_switchResult = _builder.toString();
			}
		}
		if (!_matched) {
			if (it instanceof TerminalRule) {
				_matched = true;
				StringConcatenation _builder = new StringConcatenation();
				{
					if (supportActions) {
						String _gaElementIdentifier = this.grammarUtil.gaElementIdentifier(
								AntlrGrammarGenUtil.<AbstractElement> getOriginalElement(ref.getTerminal()));
						_builder.append(_gaElementIdentifier);
						_builder.append("=");
					}
				}
				String _ruleName = this.grammarUtil.ruleName(it);
				_builder.append(_ruleName);
				{
					if (supportActions) {
						_builder.append("{");
						_builder.newLineIfNotEmpty();
						_builder.append("\t");
						_builder.append("announce($");
						String _gaElementIdentifier_1 = this.grammarUtil.gaElementIdentifier(
								AntlrGrammarGenUtil.<AbstractElement> getOriginalElement(ref.getTerminal()));
						_builder.append(_gaElementIdentifier_1, "\t");
						_builder.append(", grammarAccess.");
						String _gaRuleElementAccessor = this.grammarUtil.gaRuleElementAccessor(AntlrGrammarGenUtil
								.<Assignment> getOriginalElement(GrammarUtil.containingAssignment(ref)));
						_builder.append(_gaRuleElementAccessor, "\t");
						_builder.append(");");
						_builder.newLineIfNotEmpty();
						_builder.append("}");
						_builder.newLine();
					}
				}
				_switchResult = _builder.toString();
			}
		}
		return _switchResult;
	}

	@Override
	protected String _assignmentEbnf(final AbstractElement it, final Assignment assignment, final AntlrOptions options,
			final boolean supportActions) {
		return this.ebnf(it, options, supportActions);
	}

	@Override
	protected String _assignmentEbnf(final RuleCall it, final Assignment assignment, final AntlrOptions options,
			final boolean supportActions) {
		String _switchResult = null;
		AbstractRule _rule = it.getRule();
		boolean _matched = false;
		if (_rule instanceof EnumRule) {
			_matched = true;
			_switchResult = this.grammarUtil.ruleName(it.getRule());
		}
		if (!_matched) {
			if (_rule instanceof ParserRule) {
				_matched = true;
				StringConcatenation _builder = new StringConcatenation();
				{
					if (((!supportActions) && Objects.equal("TypeRefNoTrailingLineBreak",
							AntlrGrammarGenUtil.<AbstractRule> getOriginalElement(it.getRule()).getName()))) {
						_builder.append("{isTypeRefNoTrailingLineBreak()}?");
						_builder.newLine();
					} else {
						{
							if (supportActions) {
								String _gaElementIdentifier = this.grammarUtil
										.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
								_builder.append(_gaElementIdentifier);
								_builder.append("=");
							}
						}
						String _ruleName = this.grammarUtil.ruleName(it.getRule());
						_builder.append(_ruleName);
						{
							if (supportActions) {
								_builder.append("{");
								_builder.newLineIfNotEmpty();
								_builder.append("\t");
								_builder.append("announce($");
								String _gaElementIdentifier_1 = this.grammarUtil
										.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
								_builder.append(_gaElementIdentifier_1, "\t");
								_builder.append(".start, $");
								String _gaElementIdentifier_2 = this.grammarUtil
										.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
								_builder.append(_gaElementIdentifier_2, "\t");
								_builder.append(".stop, grammarAccess.");
								String _gaRuleElementAccessor = this.grammarUtil.gaRuleElementAccessor(
										AntlrGrammarGenUtil.<Assignment> getOriginalElement(assignment));
								_builder.append(_gaRuleElementAccessor, "\t");
								_builder.append(");");
								_builder.newLineIfNotEmpty();
								_builder.append("}");
								_builder.newLine();
							}
						}
					}
				}
				_switchResult = _builder.toString();
			}
		}
		if (!_matched) {
			if (_rule instanceof TerminalRule) {
				_matched = true;
				StringConcatenation _builder = new StringConcatenation();
				{
					if (supportActions) {
						String _gaElementIdentifier = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
						_builder.append(_gaElementIdentifier);
						_builder.append("=");
					}
				}
				String _ruleName = this.grammarUtil.ruleName(it.getRule());
				_builder.append(_ruleName);
				{
					if (supportActions) {
						_builder.append("{");
						_builder.newLineIfNotEmpty();
						_builder.append("\t");
						_builder.append("announce($");
						String _gaElementIdentifier_1 = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
						_builder.append(_gaElementIdentifier_1, "\t");
						_builder.append(", grammarAccess.");
						String _gaRuleElementAccessor = this.grammarUtil
								.gaRuleElementAccessor(AntlrGrammarGenUtil.<Assignment> getOriginalElement(assignment));
						_builder.append(_gaRuleElementAccessor, "\t");
						_builder.append(");");
						_builder.newLineIfNotEmpty();
						_builder.append("}");
						_builder.newLine();
					}
				}
				_switchResult = _builder.toString();
			}
		}
		return _switchResult;
	}

	@Override
	protected String _assignmentEbnf(final CrossReference it, final Assignment assignment, final AntlrOptions options,
			final boolean supportActions) {
		return this.crossrefEbnf(it.getTerminal(), it, supportActions);
	}

	@Override
	protected String _ebnf2(final Action it, final AntlrOptions options, final boolean supportActions) {
		return "";
	}

	@Override
	protected String _ebnf2(final Keyword it, final AntlrOptions options, final boolean supportActions) {
		StringConcatenation _builder = new StringConcatenation();
		{
			if (supportActions) {
				String _gaElementIdentifier = this.grammarUtil
						.gaElementIdentifier(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
				_builder.append(_gaElementIdentifier);
				_builder.append("=");
			}
		}
		String _ruleName = this.keywordHelper.getRuleName(it.getValue());
		_builder.append(_ruleName);
		_builder.newLineIfNotEmpty();
		{
			if ((((Objects.equal("return", it.getValue()) || Objects.equal("throw", it.getValue()))
					|| Objects.equal("break", it.getValue())) || Objects.equal("continue", it.getValue()))) {
				{
					if (supportActions) {
						_builder.append(" {");
						_builder.newLineIfNotEmpty();
						_builder.append("\t");
						_builder.append("promoteEOL();");
						_builder.newLine();
						_builder.append("\t");
						_builder.append("announce($");
						String _gaElementIdentifier_1 = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaElementIdentifier_1, "\t");
						_builder.append(", grammarAccess.");
						String _gaRuleElementAccessor = this.grammarUtil
								.gaRuleElementAccessor(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaRuleElementAccessor, "\t");
						_builder.append(");");
						_builder.newLineIfNotEmpty();
						_builder.append("}");
						_builder.newLine();
					}
				}
			} else {
				{
					if (supportActions) {
						_builder.append(" {");
						_builder.newLineIfNotEmpty();
						_builder.append("\t");
						_builder.append("announce($");
						String _gaElementIdentifier_2 = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaElementIdentifier_2, "\t");
						_builder.append(", grammarAccess.");
						String _gaRuleElementAccessor_1 = this.grammarUtil
								.gaRuleElementAccessor(AntlrGrammarGenUtil.<Keyword> getOriginalElement(it));
						_builder.append(_gaRuleElementAccessor_1, "\t");
						_builder.append(");");
						_builder.newLineIfNotEmpty();
						_builder.append("}");
						_builder.newLine();
					}
				}
			}
		}
		return _builder.toString();
	}

	@Override
	protected String _ebnf2(final RuleCall it, final AntlrOptions options, final boolean supportActions) {
		StringConcatenation _builder = new StringConcatenation();
		{
			if (((Objects.equal("TypeRef",
					AntlrGrammarGenUtil.<AbstractRule> getOriginalElement(it.getRule()).getName())
					&& AbstractRule.class.isInstance(AntlrGrammarGenUtil.<EObject> getOriginalElement(it.eContainer())))
					&& Objects.equal("TypeRefNoTrailingLineBreak",
							((AbstractRule) AntlrGrammarGenUtil.<EObject> getOriginalElement(it.eContainer()))
									.getName()))) {
				{
					if (supportActions) {
						String _gaElementIdentifier = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
						_builder.append(_gaElementIdentifier);
						_builder.append("=");
					}
				}
				String _ruleName = this.grammarUtil.ruleName(it.getRule());
				_builder.append(_ruleName);
				_builder.append(" {");
				_builder.newLineIfNotEmpty();
				_builder.append("\t");
				_builder.append("if (hasDisallowedEOL()) {");
				_builder.newLine();
				_builder.append("\t\t");
				_builder.append("if (state.backtracking>0) {");
				_builder.newLine();
				_builder.append("\t\t\t");
				_builder.append("state.failed=true;");
				_builder.newLine();
				_builder.append("\t\t");
				_builder.append("}");
				_builder.newLine();
				_builder.append("\t");
				_builder.append("}");
				_builder.newLine();
				{
					if (supportActions) {
						_builder.append("\t");
						_builder.append("announce($");
						String _gaElementIdentifier_1 = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
						_builder.append(_gaElementIdentifier_1, "\t");
						_builder.append(".start, $");
						String _gaElementIdentifier_2 = this.grammarUtil
								.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
						_builder.append(_gaElementIdentifier_2, "\t");
						_builder.append(".stop, grammarAccess.");
						String _gaRuleElementAccessor = this.grammarUtil
								.gaRuleElementAccessor(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
						_builder.append(_gaRuleElementAccessor, "\t");
						_builder.append(");");
						_builder.newLineIfNotEmpty();
					}
				}
				_builder.append("}");
				_builder.newLine();
			} else {
				String _name = AntlrGrammarGenUtil.<AbstractRule> getOriginalElement(it.getRule()).getName();
				boolean _equals = Objects.equal("LeftHandSideExpression", _name);
				if (_equals) {
					{
						if (supportActions) {
							String _gaElementIdentifier_3 = this.grammarUtil
									.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
							_builder.append(_gaElementIdentifier_3);
							_builder.append("=");
						}
					}
					String _ruleName_1 = this.grammarUtil.ruleName(it.getRule());
					_builder.append(_ruleName_1);
					_builder.newLineIfNotEmpty();
					{
						if (supportActions) {
							_builder.append("{");
							_builder.newLine();
							_builder.append("\t");
							_builder.append(
									"if (input.LA(1) == PlusSignPlusSign || input.LA(1) == HyphenMinusHyphenMinus) {");
							_builder.newLine();
							_builder.append("\t\t");
							_builder.append("promoteEOL();");
							_builder.newLine();
							_builder.append("\t");
							_builder.append("}");
							_builder.newLine();
							_builder.append("\t");
							_builder.append("announce($");
							String _gaElementIdentifier_4 = this.grammarUtil
									.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
							_builder.append(_gaElementIdentifier_4, "\t");
							_builder.append(".start, $");
							String _gaElementIdentifier_5 = this.grammarUtil
									.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
							_builder.append(_gaElementIdentifier_5, "\t");
							_builder.append(".stop, grammarAccess.");
							String _gaRuleElementAccessor_1 = this.grammarUtil
									.gaRuleElementAccessor(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
							_builder.append(_gaRuleElementAccessor_1, "\t");
							_builder.append(");");
							_builder.newLineIfNotEmpty();
							_builder.append("}");
							_builder.newLine();
						}
					}
				} else {
					{
						if (supportActions) {
							String _gaElementIdentifier_6 = this.grammarUtil
									.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
							_builder.append(_gaElementIdentifier_6);
							_builder.append("=");
						}
					}
					String _ruleName_2 = this.grammarUtil.ruleName(it.getRule());
					_builder.append(_ruleName_2);
					{
						if ((supportActions && TerminalRule.class.isInstance(it.getRule()))) {
							_builder.newLineIfNotEmpty();
							_builder.append("{ announce($");
							String _gaElementIdentifier_7 = this.grammarUtil
									.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
							_builder.append(_gaElementIdentifier_7);
							_builder.append(", grammarAccess.");
							String _gaRuleElementAccessor_2 = this.grammarUtil
									.gaRuleElementAccessor(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
							_builder.append(_gaRuleElementAccessor_2);
							_builder.append("); }");
							_builder.newLineIfNotEmpty();
						} else {
							if ((supportActions && ParserRule.class.isInstance(it.getRule()))) {
								_builder.append("{ announce($");
								String _gaElementIdentifier_8 = this.grammarUtil
										.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
								_builder.append(_gaElementIdentifier_8);
								_builder.append(".start, $");
								String _gaElementIdentifier_9 = this.grammarUtil
										.gaElementIdentifier(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
								_builder.append(_gaElementIdentifier_9);
								_builder.append(".stop, grammarAccess.");
								String _gaRuleElementAccessor_3 = this.grammarUtil
										.gaRuleElementAccessor(AntlrGrammarGenUtil.<RuleCall> getOriginalElement(it));
								_builder.append(_gaRuleElementAccessor_3);
								_builder.append("); }");
								_builder.newLineIfNotEmpty();
							}
						}
					}
				}
			}
		}
		return _builder.toString();
	}
}
