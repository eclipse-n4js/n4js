// Generated from /Users/mark-oliver.reiser/Home/Prog/Java/n4js-sec/git-repo/n4js/plugins/org.eclipse.n4js.dts/grammar/TypeScriptParser.g4 by ANTLR 4.7.2
package org.eclipse.n4js.dts;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TypeScriptParser extends TypeScriptParserBase {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		JSDocComment=1, MultiLineComment=2, SingleLineComment=3, RegularExpressionLiteral=4, 
		OpenBracket=5, CloseBracket=6, OpenParen=7, CloseParen=8, OpenBrace=9, 
		CloseBrace=10, SemiColon=11, Comma=12, Assign=13, QuestionMark=14, Colon=15, 
		Ellipsis=16, Dot=17, PlusPlus=18, MinusMinus=19, Plus=20, Minus=21, BitNot=22, 
		Not=23, Multiply=24, Divide=25, Modulus=26, LeftShiftArithmetic=27, LessThan=28, 
		MoreThan=29, LessThanEquals=30, GreaterThanEquals=31, Equals_=32, NotEquals=33, 
		IdentityEquals=34, IdentityNotEquals=35, BitAnd=36, BitXOr=37, BitOr=38, 
		And=39, Or=40, MultiplyAssign=41, DivideAssign=42, ModulusAssign=43, PlusAssign=44, 
		MinusAssign=45, LeftShiftArithmeticAssign=46, RightShiftArithmeticAssign=47, 
		RightShiftLogicalAssign=48, BitAndAssign=49, BitXorAssign=50, BitOrAssign=51, 
		ARROW=52, NullLiteral=53, UndefinedLiteral=54, BooleanLiteral=55, DecimalLiteral=56, 
		HexIntegerLiteral=57, OctalIntegerLiteral=58, OctalIntegerLiteral2=59, 
		BinaryIntegerLiteral=60, Break=61, Do=62, Instanceof=63, Typeof=64, Unique=65, 
		Keyof=66, Case=67, Else=68, New=69, Target=70, Var=71, Catch=72, Finally=73, 
		Return=74, Void=75, Continue=76, For=77, Switch=78, While=79, Debugger=80, 
		Function=81, This=82, With=83, Default=84, If=85, Throw=86, Delete=87, 
		In=88, Try=89, As=90, From=91, ReadOnly=92, Async=93, Await=94, Class=95, 
		Enum=96, Extends=97, Super=98, Const=99, Export=100, Import=101, Implements=102, 
		Let=103, Private=104, Public=105, Interface=106, Package=107, Protected=108, 
		Static=109, Yield=110, Any=111, Number=112, Boolean=113, String=114, Symbol=115, 
		TypeAlias=116, Get=117, Set=118, Constructor=119, Namespace=120, Require=121, 
		Module=122, Declare=123, Abstract=124, Is=125, Infer=126, Never=127, Unknown=128, 
		Asserts=129, At=130, Identifier=131, StringLiteral=132, BackTick=133, 
		WhiteSpaces=134, LineTerminator=135, HtmlComment=136, CDataComment=137, 
		UnexpectedCharacter=138, TemplateStringStartExpression=139, TemplateStringAtom=140;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_declareStatement = 2, RULE_declarationStatement = 3, 
		RULE_block = 4, RULE_statementList = 5, RULE_colonSepTypeRef = 6, RULE_typeRef = 7, 
		RULE_conditionalTypeRef = 8, RULE_unionTypeExpression = 9, RULE_intersectionTypeExpression = 10, 
		RULE_operatorTypeRef = 11, RULE_typeOperator = 12, RULE_arrayTypeExpression = 13, 
		RULE_arrayTypeExpressionSuffix = 14, RULE_primaryTypeExpression = 15, 
		RULE_literalType = 16, RULE_arrowFunctionTypeExpression = 17, RULE_tupleTypeExpression = 18, 
		RULE_tupleTypeArgument = 19, RULE_typeVariable = 20, RULE_typeRefWithModifiers = 21, 
		RULE_parenthesizedTypeRef = 22, RULE_parameterizedTypeRef = 23, RULE_typeName = 24, 
		RULE_typeArguments = 25, RULE_typeArgumentList = 26, RULE_typeArgument = 27, 
		RULE_objectLiteralTypeRef = 28, RULE_thisTypeRef = 29, RULE_queryTypeRef = 30, 
		RULE_importTypeRef = 31, RULE_typePredicateWithOperatorTypeRef = 32, RULE_bindingIdentifier = 33, 
		RULE_propertyAccessExpressionInTypeRef = 34, RULE_inferTypeRef = 35, RULE_typeAliasDeclaration = 36, 
		RULE_typeParameters = 37, RULE_typeParameterList = 38, RULE_typeParameter = 39, 
		RULE_constraint = 40, RULE_defaultType = 41, RULE_moduleDeclaration = 42, 
		RULE_moduleName = 43, RULE_namespaceDeclaration = 44, RULE_namespaceName = 45, 
		RULE_decoratorList = 46, RULE_decorator = 47, RULE_decoratorMemberExpression = 48, 
		RULE_decoratorCallExpression = 49, RULE_interfaceDeclaration = 50, RULE_interfaceExtendsClause = 51, 
		RULE_classOrInterfaceTypeList = 52, RULE_interfaceBody = 53, RULE_interfaceMemberList = 54, 
		RULE_interfaceMember = 55, RULE_constructSignature = 56, RULE_callSignature = 57, 
		RULE_indexSignature = 58, RULE_indexSignatureElement = 59, RULE_methodSignature = 60, 
		RULE_propertySignature = 61, RULE_enumDeclaration = 62, RULE_enumBody = 63, 
		RULE_enumMemberList = 64, RULE_enumMember = 65, RULE_functionDeclaration = 66, 
		RULE_classDeclaration = 67, RULE_classHeritage = 68, RULE_classExtendsClause = 69, 
		RULE_classImplementsClause = 70, RULE_classBody = 71, RULE_classMemberList = 72, 
		RULE_classMember = 73, RULE_constructorDeclaration = 74, RULE_propertyMemberDeclaration = 75, 
		RULE_abstractDeclaration = 76, RULE_propertyMember = 77, RULE_propertyMemberBase = 78, 
		RULE_propertyOrMethod = 79, RULE_initializer = 80, RULE_parameterBlock = 81, 
		RULE_parameterListTrailingComma = 82, RULE_parameterList = 83, RULE_restParameter = 84, 
		RULE_parameter = 85, RULE_requiredParameter = 86, RULE_optionalParameter = 87, 
		RULE_accessibilityModifier = 88, RULE_identifierOrPattern = 89, RULE_bindingPattern = 90, 
		RULE_arrayLiteral = 91, RULE_elementList = 92, RULE_arrayElement = 93, 
		RULE_bindingElement = 94, RULE_objectLiteral = 95, RULE_propertyAssignment = 96, 
		RULE_propertyName = 97, RULE_getAccessor = 98, RULE_setAccessor = 99, 
		RULE_generatorMethod = 100, RULE_arguments = 101, RULE_argumentList = 102, 
		RULE_argument = 103, RULE_importStatement = 104, RULE_importFromBlock = 105, 
		RULE_multipleImportElements = 106, RULE_importedElement = 107, RULE_importAliasDeclaration = 108, 
		RULE_exportStatement = 109, RULE_exportStatementTail = 110, RULE_multipleExportElements = 111, 
		RULE_variableStatement = 112, RULE_varModifier = 113, RULE_bindingPatternBlock = 114, 
		RULE_variableDeclarationList = 115, RULE_variableDeclaration = 116, RULE_switchStatement = 117, 
		RULE_caseBlock = 118, RULE_caseClauses = 119, RULE_caseClause = 120, RULE_defaultClause = 121, 
		RULE_tryStatement = 122, RULE_catchProduction = 123, RULE_finallyProduction = 124, 
		RULE_emptyStatement = 125, RULE_ifStatement = 126, RULE_iterationStatement = 127, 
		RULE_continueStatement = 128, RULE_breakStatement = 129, RULE_returnStatement = 130, 
		RULE_withStatement = 131, RULE_labelledStatement = 132, RULE_throwStatement = 133, 
		RULE_debuggerStatement = 134, RULE_expressionStatement = 135, RULE_expressionSequence = 136, 
		RULE_singleExpression = 137, RULE_functionExpression = 138, RULE_arrowFunctionExpression = 139, 
		RULE_arrowFunctionBody = 140, RULE_classExpression = 141, RULE_assignmentOperator = 142, 
		RULE_relationalOperator = 143, RULE_unaryOperator = 144, RULE_newExpression = 145, 
		RULE_generatorBlock = 146, RULE_generatorDefinition = 147, RULE_iteratorBlock = 148, 
		RULE_iteratorDefinition = 149, RULE_literal = 150, RULE_templateStringLiteral = 151, 
		RULE_templateStringAtom = 152, RULE_numericLiteral = 153, RULE_identifierOrKeyWord = 154, 
		RULE_identifierName = 155, RULE_reservedWord = 156, RULE_typeReferenceName = 157, 
		RULE_keyword = 158, RULE_keywordAllowedInTypeReferences = 159, RULE_getter = 160, 
		RULE_setter = 161, RULE_eos = 162;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "declareStatement", "declarationStatement", "block", 
			"statementList", "colonSepTypeRef", "typeRef", "conditionalTypeRef", 
			"unionTypeExpression", "intersectionTypeExpression", "operatorTypeRef", 
			"typeOperator", "arrayTypeExpression", "arrayTypeExpressionSuffix", "primaryTypeExpression", 
			"literalType", "arrowFunctionTypeExpression", "tupleTypeExpression", 
			"tupleTypeArgument", "typeVariable", "typeRefWithModifiers", "parenthesizedTypeRef", 
			"parameterizedTypeRef", "typeName", "typeArguments", "typeArgumentList", 
			"typeArgument", "objectLiteralTypeRef", "thisTypeRef", "queryTypeRef", 
			"importTypeRef", "typePredicateWithOperatorTypeRef", "bindingIdentifier", 
			"propertyAccessExpressionInTypeRef", "inferTypeRef", "typeAliasDeclaration", 
			"typeParameters", "typeParameterList", "typeParameter", "constraint", 
			"defaultType", "moduleDeclaration", "moduleName", "namespaceDeclaration", 
			"namespaceName", "decoratorList", "decorator", "decoratorMemberExpression", 
			"decoratorCallExpression", "interfaceDeclaration", "interfaceExtendsClause", 
			"classOrInterfaceTypeList", "interfaceBody", "interfaceMemberList", "interfaceMember", 
			"constructSignature", "callSignature", "indexSignature", "indexSignatureElement", 
			"methodSignature", "propertySignature", "enumDeclaration", "enumBody", 
			"enumMemberList", "enumMember", "functionDeclaration", "classDeclaration", 
			"classHeritage", "classExtendsClause", "classImplementsClause", "classBody", 
			"classMemberList", "classMember", "constructorDeclaration", "propertyMemberDeclaration", 
			"abstractDeclaration", "propertyMember", "propertyMemberBase", "propertyOrMethod", 
			"initializer", "parameterBlock", "parameterListTrailingComma", "parameterList", 
			"restParameter", "parameter", "requiredParameter", "optionalParameter", 
			"accessibilityModifier", "identifierOrPattern", "bindingPattern", "arrayLiteral", 
			"elementList", "arrayElement", "bindingElement", "objectLiteral", "propertyAssignment", 
			"propertyName", "getAccessor", "setAccessor", "generatorMethod", "arguments", 
			"argumentList", "argument", "importStatement", "importFromBlock", "multipleImportElements", 
			"importedElement", "importAliasDeclaration", "exportStatement", "exportStatementTail", 
			"multipleExportElements", "variableStatement", "varModifier", "bindingPatternBlock", 
			"variableDeclarationList", "variableDeclaration", "switchStatement", 
			"caseBlock", "caseClauses", "caseClause", "defaultClause", "tryStatement", 
			"catchProduction", "finallyProduction", "emptyStatement", "ifStatement", 
			"iterationStatement", "continueStatement", "breakStatement", "returnStatement", 
			"withStatement", "labelledStatement", "throwStatement", "debuggerStatement", 
			"expressionStatement", "expressionSequence", "singleExpression", "functionExpression", 
			"arrowFunctionExpression", "arrowFunctionBody", "classExpression", "assignmentOperator", 
			"relationalOperator", "unaryOperator", "newExpression", "generatorBlock", 
			"generatorDefinition", "iteratorBlock", "iteratorDefinition", "literal", 
			"templateStringLiteral", "templateStringAtom", "numericLiteral", "identifierOrKeyWord", 
			"identifierName", "reservedWord", "typeReferenceName", "keyword", "keywordAllowedInTypeReferences", 
			"getter", "setter", "eos"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'['", "']'", "'('", "')'", "'{'", "'}'", 
			"';'", "','", "'='", "'?'", "':'", "'...'", "'.'", "'++'", "'--'", "'+'", 
			"'-'", "'~'", "'!'", "'*'", "'/'", "'%'", "'<<'", "'<'", "'>'", "'<='", 
			"'>='", "'=='", "'!='", "'==='", "'!=='", "'&'", "'^'", "'|'", "'&&'", 
			"'||'", "'*='", "'/='", "'%='", "'+='", "'-='", "'<<='", "'>>='", "'>>>='", 
			"'&='", "'^='", "'|='", "'=>'", "'null'", "'undefined'", null, null, 
			null, null, null, null, "'break'", "'do'", "'instanceof'", "'typeof'", 
			"'unique'", "'keyof'", "'case'", "'else'", "'new'", "'target'", "'var'", 
			"'catch'", "'finally'", "'return'", "'void'", "'continue'", "'for'", 
			"'switch'", "'while'", "'debugger'", "'function'", "'this'", "'with'", 
			"'default'", "'if'", "'throw'", "'delete'", "'in'", "'try'", "'as'", 
			"'from'", "'readonly'", "'async'", "'await'", "'class'", "'enum'", "'extends'", 
			"'super'", "'const'", "'export'", "'import'", "'implements'", "'let'", 
			"'private'", "'public'", "'interface'", "'package'", "'protected'", "'static'", 
			"'yield'", "'any'", "'number'", "'boolean'", "'string'", "'symbol'", 
			"'type'", "'get'", "'set'", "'constructor'", "'namespace'", "'require'", 
			"'module'", "'declare'", "'abstract'", "'is'", "'infer'", "'never'", 
			"'unknown'", "'asserts'", "'@'", null, null, null, null, null, null, 
			null, null, "'${'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "JSDocComment", "MultiLineComment", "SingleLineComment", "RegularExpressionLiteral", 
			"OpenBracket", "CloseBracket", "OpenParen", "CloseParen", "OpenBrace", 
			"CloseBrace", "SemiColon", "Comma", "Assign", "QuestionMark", "Colon", 
			"Ellipsis", "Dot", "PlusPlus", "MinusMinus", "Plus", "Minus", "BitNot", 
			"Not", "Multiply", "Divide", "Modulus", "LeftShiftArithmetic", "LessThan", 
			"MoreThan", "LessThanEquals", "GreaterThanEquals", "Equals_", "NotEquals", 
			"IdentityEquals", "IdentityNotEquals", "BitAnd", "BitXOr", "BitOr", "And", 
			"Or", "MultiplyAssign", "DivideAssign", "ModulusAssign", "PlusAssign", 
			"MinusAssign", "LeftShiftArithmeticAssign", "RightShiftArithmeticAssign", 
			"RightShiftLogicalAssign", "BitAndAssign", "BitXorAssign", "BitOrAssign", 
			"ARROW", "NullLiteral", "UndefinedLiteral", "BooleanLiteral", "DecimalLiteral", 
			"HexIntegerLiteral", "OctalIntegerLiteral", "OctalIntegerLiteral2", "BinaryIntegerLiteral", 
			"Break", "Do", "Instanceof", "Typeof", "Unique", "Keyof", "Case", "Else", 
			"New", "Target", "Var", "Catch", "Finally", "Return", "Void", "Continue", 
			"For", "Switch", "While", "Debugger", "Function", "This", "With", "Default", 
			"If", "Throw", "Delete", "In", "Try", "As", "From", "ReadOnly", "Async", 
			"Await", "Class", "Enum", "Extends", "Super", "Const", "Export", "Import", 
			"Implements", "Let", "Private", "Public", "Interface", "Package", "Protected", 
			"Static", "Yield", "Any", "Number", "Boolean", "String", "Symbol", "TypeAlias", 
			"Get", "Set", "Constructor", "Namespace", "Require", "Module", "Declare", 
			"Abstract", "Is", "Infer", "Never", "Unknown", "Asserts", "At", "Identifier", 
			"StringLiteral", "BackTick", "WhiteSpaces", "LineTerminator", "HtmlComment", 
			"CDataComment", "UnexpectedCharacter", "TemplateStringStartExpression", 
			"TemplateStringAtom"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "TypeScriptParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TypeScriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(TypeScriptParser.EOF, 0); }
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Return - 71)) | (1L << (Continue - 71)) | (1L << (For - 71)) | (1L << (Switch - 71)) | (1L << (While - 71)) | (1L << (Debugger - 71)) | (1L << (Function - 71)) | (1L << (With - 71)) | (1L << (If - 71)) | (1L << (Throw - 71)) | (1L << (Try - 71)) | (1L << (ReadOnly - 71)) | (1L << (Class - 71)) | (1L << (Enum - 71)) | (1L << (Const - 71)) | (1L << (Export - 71)) | (1L << (Import - 71)) | (1L << (Let - 71)) | (1L << (Private - 71)) | (1L << (Public - 71)) | (1L << (Interface - 71)) | (1L << (Protected - 71)) | (1L << (TypeAlias - 71)) | (1L << (Namespace - 71)) | (1L << (Module - 71)) | (1L << (Declare - 71)) | (1L << (Abstract - 71)) | (1L << (At - 71)) | (1L << (Identifier - 71)))) != 0)) {
				{
				setState(326);
				statementList();
				}
			}

			setState(329);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ImportStatementContext importStatement() {
			return getRuleContext(ImportStatementContext.class,0);
		}
		public DecoratorListContext decoratorList() {
			return getRuleContext(DecoratorListContext.class,0);
		}
		public ExportStatementContext exportStatement() {
			return getRuleContext(ExportStatementContext.class,0);
		}
		public DeclareStatementContext declareStatement() {
			return getRuleContext(DeclareStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public IterationStatementContext iterationStatement() {
			return getRuleContext(IterationStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public WithStatementContext withStatement() {
			return getRuleContext(WithStatementContext.class,0);
		}
		public LabelledStatementContext labelledStatement() {
			return getRuleContext(LabelledStatementContext.class,0);
		}
		public SwitchStatementContext switchStatement() {
			return getRuleContext(SwitchStatementContext.class,0);
		}
		public ThrowStatementContext throwStatement() {
			return getRuleContext(ThrowStatementContext.class,0);
		}
		public TryStatementContext tryStatement() {
			return getRuleContext(TryStatementContext.class,0);
		}
		public DebuggerStatementContext debuggerStatement() {
			return getRuleContext(DebuggerStatementContext.class,0);
		}
		public EmptyStatementContext emptyStatement() {
			return getRuleContext(EmptyStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(348);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBrace:
				enterOuterAlt(_localctx, 1);
				{
				setState(331);
				block();
				}
				break;
			case Import:
				enterOuterAlt(_localctx, 2);
				{
				setState(332);
				importStatement();
				}
				break;
			case At:
				enterOuterAlt(_localctx, 3);
				{
				setState(333);
				decoratorList();
				}
				break;
			case Export:
				enterOuterAlt(_localctx, 4);
				{
				setState(334);
				exportStatement();
				}
				break;
			case Var:
			case Function:
			case ReadOnly:
			case Class:
			case Enum:
			case Const:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Protected:
			case TypeAlias:
			case Namespace:
			case Module:
			case Declare:
			case Abstract:
				enterOuterAlt(_localctx, 5);
				{
				setState(335);
				declareStatement();
				}
				break;
			case If:
				enterOuterAlt(_localctx, 6);
				{
				setState(336);
				ifStatement();
				}
				break;
			case Do:
			case For:
			case While:
				enterOuterAlt(_localctx, 7);
				{
				setState(337);
				iterationStatement();
				}
				break;
			case Continue:
				enterOuterAlt(_localctx, 8);
				{
				setState(338);
				continueStatement();
				}
				break;
			case Break:
				enterOuterAlt(_localctx, 9);
				{
				setState(339);
				breakStatement();
				}
				break;
			case Return:
				enterOuterAlt(_localctx, 10);
				{
				setState(340);
				returnStatement();
				}
				break;
			case With:
				enterOuterAlt(_localctx, 11);
				{
				setState(341);
				withStatement();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 12);
				{
				setState(342);
				labelledStatement();
				}
				break;
			case Switch:
				enterOuterAlt(_localctx, 13);
				{
				setState(343);
				switchStatement();
				}
				break;
			case Throw:
				enterOuterAlt(_localctx, 14);
				{
				setState(344);
				throwStatement();
				}
				break;
			case Try:
				enterOuterAlt(_localctx, 15);
				{
				setState(345);
				tryStatement();
				}
				break;
			case Debugger:
				enterOuterAlt(_localctx, 16);
				{
				setState(346);
				debuggerStatement();
				}
				break;
			case SemiColon:
				enterOuterAlt(_localctx, 17);
				{
				setState(347);
				emptyStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclareStatementContext extends ParserRuleContext {
		public DeclarationStatementContext declarationStatement() {
			return getRuleContext(DeclarationStatementContext.class,0);
		}
		public TerminalNode Declare() { return getToken(TypeScriptParser.Declare, 0); }
		public DeclareStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declareStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDeclareStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDeclareStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDeclareStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclareStatementContext declareStatement() throws RecognitionException {
		DeclareStatementContext _localctx = new DeclareStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declareStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(351);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Declare) {
				{
				setState(350);
				match(Declare);
				}
			}

			setState(353);
			declarationStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationStatementContext extends ParserRuleContext {
		public ModuleDeclarationContext moduleDeclaration() {
			return getRuleContext(ModuleDeclarationContext.class,0);
		}
		public NamespaceDeclarationContext namespaceDeclaration() {
			return getRuleContext(NamespaceDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public TypeAliasDeclarationContext typeAliasDeclaration() {
			return getRuleContext(TypeAliasDeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public VariableStatementContext variableStatement() {
			return getRuleContext(VariableStatementContext.class,0);
		}
		public DeclarationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDeclarationStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDeclarationStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDeclarationStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationStatementContext declarationStatement() throws RecognitionException {
		DeclarationStatementContext _localctx = new DeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_declarationStatement);
		try {
			setState(363);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(355);
				moduleDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(356);
				namespaceDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(357);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(358);
				typeAliasDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(359);
				functionDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(360);
				classDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(361);
				enumDeclaration();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(362);
				variableStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			match(OpenBrace);
			setState(367);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Return - 71)) | (1L << (Continue - 71)) | (1L << (For - 71)) | (1L << (Switch - 71)) | (1L << (While - 71)) | (1L << (Debugger - 71)) | (1L << (Function - 71)) | (1L << (With - 71)) | (1L << (If - 71)) | (1L << (Throw - 71)) | (1L << (Try - 71)) | (1L << (ReadOnly - 71)) | (1L << (Class - 71)) | (1L << (Enum - 71)) | (1L << (Const - 71)) | (1L << (Export - 71)) | (1L << (Import - 71)) | (1L << (Let - 71)) | (1L << (Private - 71)) | (1L << (Public - 71)) | (1L << (Interface - 71)) | (1L << (Protected - 71)) | (1L << (TypeAlias - 71)) | (1L << (Namespace - 71)) | (1L << (Module - 71)) | (1L << (Declare - 71)) | (1L << (Abstract - 71)) | (1L << (At - 71)) | (1L << (Identifier - 71)))) != 0)) {
				{
				setState(366);
				statementList();
				}
			}

			setState(369);
			match(CloseBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementListContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitStatementList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitStatementList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementListContext statementList() throws RecognitionException {
		StatementListContext _localctx = new StatementListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_statementList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(372); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(371);
				statement();
				}
				}
				setState(374); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Return - 71)) | (1L << (Continue - 71)) | (1L << (For - 71)) | (1L << (Switch - 71)) | (1L << (While - 71)) | (1L << (Debugger - 71)) | (1L << (Function - 71)) | (1L << (With - 71)) | (1L << (If - 71)) | (1L << (Throw - 71)) | (1L << (Try - 71)) | (1L << (ReadOnly - 71)) | (1L << (Class - 71)) | (1L << (Enum - 71)) | (1L << (Const - 71)) | (1L << (Export - 71)) | (1L << (Import - 71)) | (1L << (Let - 71)) | (1L << (Private - 71)) | (1L << (Public - 71)) | (1L << (Interface - 71)) | (1L << (Protected - 71)) | (1L << (TypeAlias - 71)) | (1L << (Namespace - 71)) | (1L << (Module - 71)) | (1L << (Declare - 71)) | (1L << (Abstract - 71)) | (1L << (At - 71)) | (1L << (Identifier - 71)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ColonSepTypeRefContext extends ParserRuleContext {
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public ColonSepTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colonSepTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterColonSepTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitColonSepTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitColonSepTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColonSepTypeRefContext colonSepTypeRef() throws RecognitionException {
		ColonSepTypeRefContext _localctx = new ColonSepTypeRefContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_colonSepTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(376);
			match(Colon);
			setState(377);
			typeRef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeRefContext extends ParserRuleContext {
		public ConditionalTypeRefContext conditionalTypeRef() {
			return getRuleContext(ConditionalTypeRefContext.class,0);
		}
		public TypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeRefContext typeRef() throws RecognitionException {
		TypeRefContext _localctx = new TypeRefContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_typeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(379);
			conditionalTypeRef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalTypeRefContext extends ParserRuleContext {
		public List<UnionTypeExpressionContext> unionTypeExpression() {
			return getRuleContexts(UnionTypeExpressionContext.class);
		}
		public UnionTypeExpressionContext unionTypeExpression(int i) {
			return getRuleContext(UnionTypeExpressionContext.class,i);
		}
		public TerminalNode Extends() { return getToken(TypeScriptParser.Extends, 0); }
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public List<ConditionalTypeRefContext> conditionalTypeRef() {
			return getRuleContexts(ConditionalTypeRefContext.class);
		}
		public ConditionalTypeRefContext conditionalTypeRef(int i) {
			return getRuleContext(ConditionalTypeRefContext.class,i);
		}
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public ConditionalTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterConditionalTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitConditionalTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitConditionalTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalTypeRefContext conditionalTypeRef() throws RecognitionException {
		ConditionalTypeRefContext _localctx = new ConditionalTypeRefContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_conditionalTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			unionTypeExpression();
			setState(389);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(382);
				match(Extends);
				setState(383);
				unionTypeExpression();
				setState(384);
				match(QuestionMark);
				setState(385);
				conditionalTypeRef();
				setState(386);
				match(Colon);
				setState(387);
				conditionalTypeRef();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnionTypeExpressionContext extends ParserRuleContext {
		public List<IntersectionTypeExpressionContext> intersectionTypeExpression() {
			return getRuleContexts(IntersectionTypeExpressionContext.class);
		}
		public IntersectionTypeExpressionContext intersectionTypeExpression(int i) {
			return getRuleContext(IntersectionTypeExpressionContext.class,i);
		}
		public List<TerminalNode> BitOr() { return getTokens(TypeScriptParser.BitOr); }
		public TerminalNode BitOr(int i) {
			return getToken(TypeScriptParser.BitOr, i);
		}
		public UnionTypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unionTypeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterUnionTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitUnionTypeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitUnionTypeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnionTypeExpressionContext unionTypeExpression() throws RecognitionException {
		UnionTypeExpressionContext _localctx = new UnionTypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_unionTypeExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(392);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BitOr) {
				{
				setState(391);
				match(BitOr);
				}
			}

			setState(394);
			intersectionTypeExpression();
			setState(399);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(395);
					match(BitOr);
					setState(396);
					intersectionTypeExpression();
					}
					} 
				}
				setState(401);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntersectionTypeExpressionContext extends ParserRuleContext {
		public List<OperatorTypeRefContext> operatorTypeRef() {
			return getRuleContexts(OperatorTypeRefContext.class);
		}
		public OperatorTypeRefContext operatorTypeRef(int i) {
			return getRuleContext(OperatorTypeRefContext.class,i);
		}
		public List<TerminalNode> BitAnd() { return getTokens(TypeScriptParser.BitAnd); }
		public TerminalNode BitAnd(int i) {
			return getToken(TypeScriptParser.BitAnd, i);
		}
		public IntersectionTypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intersectionTypeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIntersectionTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIntersectionTypeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIntersectionTypeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntersectionTypeExpressionContext intersectionTypeExpression() throws RecognitionException {
		IntersectionTypeExpressionContext _localctx = new IntersectionTypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_intersectionTypeExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(403);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BitAnd) {
				{
				setState(402);
				match(BitAnd);
				}
			}

			setState(405);
			operatorTypeRef();
			setState(410);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(406);
					match(BitAnd);
					setState(407);
					operatorTypeRef();
					}
					} 
				}
				setState(412);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorTypeRefContext extends ParserRuleContext {
		public ArrayTypeExpressionContext arrayTypeExpression() {
			return getRuleContext(ArrayTypeExpressionContext.class,0);
		}
		public TypeOperatorContext typeOperator() {
			return getRuleContext(TypeOperatorContext.class,0);
		}
		public OperatorTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterOperatorTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitOperatorTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitOperatorTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorTypeRefContext operatorTypeRef() throws RecognitionException {
		OperatorTypeRefContext _localctx = new OperatorTypeRefContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_operatorTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(414);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(413);
				typeOperator();
				}
				break;
			}
			setState(416);
			arrayTypeExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeOperatorContext extends ParserRuleContext {
		public TerminalNode Keyof() { return getToken(TypeScriptParser.Keyof, 0); }
		public TerminalNode Unique() { return getToken(TypeScriptParser.Unique, 0); }
		public TerminalNode ReadOnly() { return getToken(TypeScriptParser.ReadOnly, 0); }
		public TypeOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeOperatorContext typeOperator() throws RecognitionException {
		TypeOperatorContext _localctx = new TypeOperatorContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_typeOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418);
			_la = _input.LA(1);
			if ( !(((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (Unique - 65)) | (1L << (Keyof - 65)) | (1L << (ReadOnly - 65)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeExpressionContext extends ParserRuleContext {
		public PrimaryTypeExpressionContext primaryTypeExpression() {
			return getRuleContext(PrimaryTypeExpressionContext.class,0);
		}
		public List<ArrayTypeExpressionSuffixContext> arrayTypeExpressionSuffix() {
			return getRuleContexts(ArrayTypeExpressionSuffixContext.class);
		}
		public ArrayTypeExpressionSuffixContext arrayTypeExpressionSuffix(int i) {
			return getRuleContext(ArrayTypeExpressionSuffixContext.class,i);
		}
		public ArrayTypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayTypeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrayTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrayTypeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrayTypeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeExpressionContext arrayTypeExpression() throws RecognitionException {
		ArrayTypeExpressionContext _localctx = new ArrayTypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_arrayTypeExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(420);
			primaryTypeExpression();
			setState(424);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(421);
					arrayTypeExpressionSuffix();
					}
					} 
				}
				setState(426);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeExpressionSuffixContext extends ParserRuleContext {
		public TerminalNode OpenBracket() { return getToken(TypeScriptParser.OpenBracket, 0); }
		public TerminalNode CloseBracket() { return getToken(TypeScriptParser.CloseBracket, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public ArrayTypeExpressionSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayTypeExpressionSuffix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrayTypeExpressionSuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrayTypeExpressionSuffix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrayTypeExpressionSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeExpressionSuffixContext arrayTypeExpressionSuffix() throws RecognitionException {
		ArrayTypeExpressionSuffixContext _localctx = new ArrayTypeExpressionSuffixContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_arrayTypeExpressionSuffix);
		try {
			setState(433);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(427);
				match(OpenBracket);
				setState(428);
				match(CloseBracket);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(429);
				match(OpenBracket);
				setState(430);
				typeRef();
				setState(431);
				match(CloseBracket);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryTypeExpressionContext extends ParserRuleContext {
		public LiteralTypeContext literalType() {
			return getRuleContext(LiteralTypeContext.class,0);
		}
		public ArrowFunctionTypeExpressionContext arrowFunctionTypeExpression() {
			return getRuleContext(ArrowFunctionTypeExpressionContext.class,0);
		}
		public TupleTypeExpressionContext tupleTypeExpression() {
			return getRuleContext(TupleTypeExpressionContext.class,0);
		}
		public QueryTypeRefContext queryTypeRef() {
			return getRuleContext(QueryTypeRefContext.class,0);
		}
		public ImportTypeRefContext importTypeRef() {
			return getRuleContext(ImportTypeRefContext.class,0);
		}
		public InferTypeRefContext inferTypeRef() {
			return getRuleContext(InferTypeRefContext.class,0);
		}
		public TypeRefWithModifiersContext typeRefWithModifiers() {
			return getRuleContext(TypeRefWithModifiersContext.class,0);
		}
		public ParenthesizedTypeRefContext parenthesizedTypeRef() {
			return getRuleContext(ParenthesizedTypeRefContext.class,0);
		}
		public PrimaryTypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryTypeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPrimaryTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPrimaryTypeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPrimaryTypeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryTypeExpressionContext primaryTypeExpression() throws RecognitionException {
		PrimaryTypeExpressionContext _localctx = new PrimaryTypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_primaryTypeExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(443);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(435);
				literalType();
				}
				break;
			case 2:
				{
				setState(436);
				arrowFunctionTypeExpression();
				}
				break;
			case 3:
				{
				setState(437);
				tupleTypeExpression();
				}
				break;
			case 4:
				{
				setState(438);
				queryTypeRef();
				}
				break;
			case 5:
				{
				setState(439);
				importTypeRef();
				}
				break;
			case 6:
				{
				setState(440);
				inferTypeRef();
				}
				break;
			case 7:
				{
				setState(441);
				typeRefWithModifiers();
				}
				break;
			case 8:
				{
				setState(442);
				parenthesizedTypeRef();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralTypeContext extends ParserRuleContext {
		public TerminalNode BooleanLiteral() { return getToken(TypeScriptParser.BooleanLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public LiteralTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterLiteralType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitLiteralType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitLiteralType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralTypeContext literalType() throws RecognitionException {
		LiteralTypeContext _localctx = new LiteralTypeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_literalType);
		try {
			setState(448);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BooleanLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(445);
				match(BooleanLiteral);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(446);
				match(StringLiteral);
				}
				break;
			case Minus:
			case DecimalLiteral:
			case HexIntegerLiteral:
			case OctalIntegerLiteral:
			case OctalIntegerLiteral2:
			case BinaryIntegerLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(447);
				numericLiteral();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrowFunctionTypeExpressionContext extends ParserRuleContext {
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(TypeScriptParser.ARROW, 0); }
		public TypePredicateWithOperatorTypeRefContext typePredicateWithOperatorTypeRef() {
			return getRuleContext(TypePredicateWithOperatorTypeRefContext.class,0);
		}
		public UnionTypeExpressionContext unionTypeExpression() {
			return getRuleContext(UnionTypeExpressionContext.class,0);
		}
		public TerminalNode New() { return getToken(TypeScriptParser.New, 0); }
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public TerminalNode Abstract() { return getToken(TypeScriptParser.Abstract, 0); }
		public ArrowFunctionTypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrowFunctionTypeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrowFunctionTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrowFunctionTypeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrowFunctionTypeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrowFunctionTypeExpressionContext arrowFunctionTypeExpression() throws RecognitionException {
		ArrowFunctionTypeExpressionContext _localctx = new ArrowFunctionTypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_arrowFunctionTypeExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(454);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==New || _la==Abstract) {
				{
				setState(451);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Abstract) {
					{
					setState(450);
					match(Abstract);
					}
				}

				setState(453);
				match(New);
				}
			}

			setState(457);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(456);
				typeParameters();
				}
			}

			setState(459);
			parameterBlock();
			setState(460);
			match(ARROW);
			}
			setState(464);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(462);
				typePredicateWithOperatorTypeRef();
				}
				break;
			case 2:
				{
				setState(463);
				unionTypeExpression();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TupleTypeExpressionContext extends ParserRuleContext {
		public TerminalNode OpenBracket() { return getToken(TypeScriptParser.OpenBracket, 0); }
		public TerminalNode CloseBracket() { return getToken(TypeScriptParser.CloseBracket, 0); }
		public List<TupleTypeArgumentContext> tupleTypeArgument() {
			return getRuleContexts(TupleTypeArgumentContext.class);
		}
		public TupleTypeArgumentContext tupleTypeArgument(int i) {
			return getRuleContext(TupleTypeArgumentContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public TupleTypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleTypeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTupleTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTupleTypeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTupleTypeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TupleTypeExpressionContext tupleTypeExpression() throws RecognitionException {
		TupleTypeExpressionContext _localctx = new TupleTypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_tupleTypeExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(466);
			match(OpenBracket);
			setState(481);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CloseBracket:
				{
				setState(467);
				match(CloseBracket);
				}
				break;
			case OpenBracket:
			case OpenParen:
			case OpenBrace:
			case Ellipsis:
			case Minus:
			case LessThan:
			case BitAnd:
			case BitOr:
			case NullLiteral:
			case UndefinedLiteral:
			case BooleanLiteral:
			case DecimalLiteral:
			case HexIntegerLiteral:
			case OctalIntegerLiteral:
			case OctalIntegerLiteral2:
			case BinaryIntegerLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Typeof:
			case Unique:
			case Keyof:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case ReadOnly:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
			case Identifier:
			case StringLiteral:
				{
				setState(468);
				tupleTypeArgument();
				setState(473);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(469);
						match(Comma);
						setState(470);
						tupleTypeArgument();
						}
						} 
					}
					setState(475);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
				setState(477);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(476);
					match(Comma);
					}
				}

				setState(479);
				match(CloseBracket);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TupleTypeArgumentContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode Ellipsis() { return getToken(TypeScriptParser.Ellipsis, 0); }
		public TerminalNode Infer() { return getToken(TypeScriptParser.Infer, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public TupleTypeArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleTypeArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTupleTypeArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTupleTypeArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTupleTypeArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TupleTypeArgumentContext tupleTypeArgument() throws RecognitionException {
		TupleTypeArgumentContext _localctx = new TupleTypeArgumentContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_tupleTypeArgument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(484);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(483);
				match(Ellipsis);
				}
			}

			setState(487);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(486);
				match(Infer);
				}
				break;
			}
			setState(491);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(489);
				match(Identifier);
				setState(490);
				match(Colon);
				}
				break;
			}
			setState(493);
			typeRef();
			setState(495);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(494);
				match(QuestionMark);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeVariableContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TerminalNode Extends() { return getToken(TypeScriptParser.Extends, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TypeVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeVariableContext typeVariable() throws RecognitionException {
		TypeVariableContext _localctx = new TypeVariableContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_typeVariable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(497);
			match(Identifier);
			setState(500);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(498);
				match(Extends);
				setState(499);
				typeRef();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeRefWithModifiersContext extends ParserRuleContext {
		public ThisTypeRefContext thisTypeRef() {
			return getRuleContext(ThisTypeRefContext.class,0);
		}
		public ParameterizedTypeRefContext parameterizedTypeRef() {
			return getRuleContext(ParameterizedTypeRefContext.class,0);
		}
		public ObjectLiteralTypeRefContext objectLiteralTypeRef() {
			return getRuleContext(ObjectLiteralTypeRefContext.class,0);
		}
		public TypeRefWithModifiersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeRefWithModifiers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeRefWithModifiers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeRefWithModifiers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeRefWithModifiers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeRefWithModifiersContext typeRefWithModifiers() throws RecognitionException {
		TypeRefWithModifiersContext _localctx = new TypeRefWithModifiersContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_typeRefWithModifiers);
		try {
			setState(505);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(502);
				thisTypeRef();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(503);
				parameterizedTypeRef();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(504);
				objectLiteralTypeRef();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParenthesizedTypeRefContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public ParenthesizedTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenthesizedTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterParenthesizedTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitParenthesizedTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitParenthesizedTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParenthesizedTypeRefContext parenthesizedTypeRef() throws RecognitionException {
		ParenthesizedTypeRefContext _localctx = new ParenthesizedTypeRefContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_parenthesizedTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(507);
			match(OpenParen);
			setState(508);
			typeRef();
			setState(509);
			match(CloseParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterizedTypeRefContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ParameterizedTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterizedTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterParameterizedTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitParameterizedTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitParameterizedTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterizedTypeRefContext parameterizedTypeRef() throws RecognitionException {
		ParameterizedTypeRefContext _localctx = new ParameterizedTypeRefContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_parameterizedTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(511);
			typeName();
			setState(513);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(512);
				typeArguments();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeNameContext extends ParserRuleContext {
		public List<TypeReferenceNameContext> typeReferenceName() {
			return getRuleContexts(TypeReferenceNameContext.class);
		}
		public TypeReferenceNameContext typeReferenceName(int i) {
			return getRuleContext(TypeReferenceNameContext.class,i);
		}
		public List<TerminalNode> Dot() { return getTokens(TypeScriptParser.Dot); }
		public TerminalNode Dot(int i) {
			return getToken(TypeScriptParser.Dot, i);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_typeName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(515);
			typeReferenceName();
			setState(520);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(516);
					match(Dot);
					setState(517);
					typeReferenceName();
					}
					} 
				}
				setState(522);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentsContext extends ParserRuleContext {
		public TerminalNode LessThan() { return getToken(TypeScriptParser.LessThan, 0); }
		public TerminalNode MoreThan() { return getToken(TypeScriptParser.MoreThan, 0); }
		public TypeArgumentListContext typeArgumentList() {
			return getRuleContext(TypeArgumentListContext.class,0);
		}
		public TerminalNode Comma() { return getToken(TypeScriptParser.Comma, 0); }
		public TypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsContext typeArguments() throws RecognitionException {
		TypeArgumentsContext _localctx = new TypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_typeArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(523);
			match(LessThan);
			setState(528);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (OpenBrace - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (BitAnd - 5)) | (1L << (BitOr - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Keyof - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(524);
				typeArgumentList();
				setState(526);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(525);
					match(Comma);
					}
				}

				}
			}

			setState(530);
			match(MoreThan);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentListContext extends ParserRuleContext {
		public List<TypeArgumentContext> typeArgument() {
			return getRuleContexts(TypeArgumentContext.class);
		}
		public TypeArgumentContext typeArgument(int i) {
			return getRuleContext(TypeArgumentContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public TypeArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentListContext typeArgumentList() throws RecognitionException {
		TypeArgumentListContext _localctx = new TypeArgumentListContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_typeArgumentList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(532);
			typeArgument();
			setState(537);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(533);
					match(Comma);
					setState(534);
					typeArgument();
					}
					} 
				}
				setState(539);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode Infer() { return getToken(TypeScriptParser.Infer, 0); }
		public TypeArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentContext typeArgument() throws RecognitionException {
		TypeArgumentContext _localctx = new TypeArgumentContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_typeArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(541);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				setState(540);
				match(Infer);
				}
				break;
			}
			setState(543);
			typeRef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectLiteralTypeRefContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public InterfaceBodyContext interfaceBody() {
			return getRuleContext(InterfaceBodyContext.class,0);
		}
		public ObjectLiteralTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectLiteralTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterObjectLiteralTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitObjectLiteralTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitObjectLiteralTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectLiteralTypeRefContext objectLiteralTypeRef() throws RecognitionException {
		ObjectLiteralTypeRefContext _localctx = new ObjectLiteralTypeRefContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_objectLiteralTypeRef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			match(OpenBrace);
			setState(547);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (Plus - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(546);
				interfaceBody();
				}
			}

			setState(549);
			match(CloseBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ThisTypeRefContext extends ParserRuleContext {
		public TerminalNode This() { return getToken(TypeScriptParser.This, 0); }
		public ThisTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thisTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterThisTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitThisTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitThisTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThisTypeRefContext thisTypeRef() throws RecognitionException {
		ThisTypeRefContext _localctx = new ThisTypeRefContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_thisTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(551);
			match(This);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryTypeRefContext extends ParserRuleContext {
		public TerminalNode Typeof() { return getToken(TypeScriptParser.Typeof, 0); }
		public PropertyAccessExpressionInTypeRefContext propertyAccessExpressionInTypeRef() {
			return getRuleContext(PropertyAccessExpressionInTypeRefContext.class,0);
		}
		public QueryTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterQueryTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitQueryTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitQueryTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryTypeRefContext queryTypeRef() throws RecognitionException {
		QueryTypeRefContext _localctx = new QueryTypeRefContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_queryTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(553);
			match(Typeof);
			setState(554);
			propertyAccessExpressionInTypeRef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportTypeRefContext extends ParserRuleContext {
		public TerminalNode Import() { return getToken(TypeScriptParser.Import, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public TerminalNode Dot() { return getToken(TypeScriptParser.Dot, 0); }
		public ParameterizedTypeRefContext parameterizedTypeRef() {
			return getRuleContext(ParameterizedTypeRefContext.class,0);
		}
		public ImportTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterImportTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitImportTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitImportTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportTypeRefContext importTypeRef() throws RecognitionException {
		ImportTypeRefContext _localctx = new ImportTypeRefContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_importTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(556);
			match(Import);
			setState(557);
			match(OpenParen);
			setState(558);
			match(StringLiteral);
			setState(559);
			match(CloseParen);
			setState(562);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(560);
				match(Dot);
				setState(561);
				parameterizedTypeRef();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypePredicateWithOperatorTypeRefContext extends ParserRuleContext {
		public TerminalNode Is() { return getToken(TypeScriptParser.Is, 0); }
		public UnionTypeExpressionContext unionTypeExpression() {
			return getRuleContext(UnionTypeExpressionContext.class,0);
		}
		public TerminalNode This() { return getToken(TypeScriptParser.This, 0); }
		public BindingIdentifierContext bindingIdentifier() {
			return getRuleContext(BindingIdentifierContext.class,0);
		}
		public TerminalNode Asserts() { return getToken(TypeScriptParser.Asserts, 0); }
		public TypePredicateWithOperatorTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typePredicateWithOperatorTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypePredicateWithOperatorTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypePredicateWithOperatorTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypePredicateWithOperatorTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypePredicateWithOperatorTypeRefContext typePredicateWithOperatorTypeRef() throws RecognitionException {
		TypePredicateWithOperatorTypeRefContext _localctx = new TypePredicateWithOperatorTypeRefContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_typePredicateWithOperatorTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(565);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(564);
				match(Asserts);
				}
				break;
			}
			setState(569);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(567);
				match(This);
				}
				break;
			case 2:
				{
				setState(568);
				bindingIdentifier();
				}
				break;
			}
			setState(571);
			match(Is);
			setState(572);
			unionTypeExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BindingIdentifierContext extends ParserRuleContext {
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public BindingIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bindingIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterBindingIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitBindingIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitBindingIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BindingIdentifierContext bindingIdentifier() throws RecognitionException {
		BindingIdentifierContext _localctx = new BindingIdentifierContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_bindingIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(574);
			identifierName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyAccessExpressionInTypeRefContext extends ParserRuleContext {
		public List<TypeReferenceNameContext> typeReferenceName() {
			return getRuleContexts(TypeReferenceNameContext.class);
		}
		public TypeReferenceNameContext typeReferenceName(int i) {
			return getRuleContext(TypeReferenceNameContext.class,i);
		}
		public List<TerminalNode> Dot() { return getTokens(TypeScriptParser.Dot); }
		public TerminalNode Dot(int i) {
			return getToken(TypeScriptParser.Dot, i);
		}
		public PropertyAccessExpressionInTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyAccessExpressionInTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertyAccessExpressionInTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertyAccessExpressionInTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertyAccessExpressionInTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyAccessExpressionInTypeRefContext propertyAccessExpressionInTypeRef() throws RecognitionException {
		PropertyAccessExpressionInTypeRefContext _localctx = new PropertyAccessExpressionInTypeRefContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_propertyAccessExpressionInTypeRef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(576);
			typeReferenceName();
			setState(581);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(577);
					match(Dot);
					setState(578);
					typeReferenceName();
					}
					} 
				}
				setState(583);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InferTypeRefContext extends ParserRuleContext {
		public TerminalNode Infer() { return getToken(TypeScriptParser.Infer, 0); }
		public TypeReferenceNameContext typeReferenceName() {
			return getRuleContext(TypeReferenceNameContext.class,0);
		}
		public InferTypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inferTypeRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterInferTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitInferTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitInferTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InferTypeRefContext inferTypeRef() throws RecognitionException {
		InferTypeRefContext _localctx = new InferTypeRefContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_inferTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(584);
			match(Infer);
			setState(585);
			typeReferenceName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeAliasDeclarationContext extends ParserRuleContext {
		public TerminalNode TypeAlias() { return getToken(TypeScriptParser.TypeAlias, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public TypeAliasDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeAliasDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeAliasDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeAliasDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeAliasDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeAliasDeclarationContext typeAliasDeclaration() throws RecognitionException {
		TypeAliasDeclarationContext _localctx = new TypeAliasDeclarationContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_typeAliasDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(587);
			match(TypeAlias);
			setState(588);
			identifierName();
			setState(590);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(589);
				typeParameters();
				}
			}

			setState(592);
			match(Assign);
			setState(593);
			typeRef();
			setState(595);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				{
				setState(594);
				match(SemiColon);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeParametersContext extends ParserRuleContext {
		public TerminalNode LessThan() { return getToken(TypeScriptParser.LessThan, 0); }
		public TerminalNode MoreThan() { return getToken(TypeScriptParser.MoreThan, 0); }
		public TypeParameterListContext typeParameterList() {
			return getRuleContext(TypeParameterListContext.class,0);
		}
		public TypeParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParametersContext typeParameters() throws RecognitionException {
		TypeParametersContext _localctx = new TypeParametersContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_typeParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(597);
			match(LessThan);
			setState(599);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (NullLiteral - 53)) | (1L << (UndefinedLiteral - 53)) | (1L << (BooleanLiteral - 53)) | (1L << (Break - 53)) | (1L << (Do - 53)) | (1L << (Instanceof - 53)) | (1L << (Typeof - 53)) | (1L << (Unique - 53)) | (1L << (Case - 53)) | (1L << (Else - 53)) | (1L << (New - 53)) | (1L << (Target - 53)) | (1L << (Var - 53)) | (1L << (Catch - 53)) | (1L << (Finally - 53)) | (1L << (Return - 53)) | (1L << (Void - 53)) | (1L << (Continue - 53)) | (1L << (For - 53)) | (1L << (Switch - 53)) | (1L << (While - 53)) | (1L << (Debugger - 53)) | (1L << (Function - 53)) | (1L << (This - 53)) | (1L << (With - 53)) | (1L << (Default - 53)) | (1L << (If - 53)) | (1L << (Throw - 53)) | (1L << (Delete - 53)) | (1L << (In - 53)) | (1L << (Try - 53)) | (1L << (As - 53)) | (1L << (From - 53)) | (1L << (ReadOnly - 53)) | (1L << (Async - 53)) | (1L << (Await - 53)) | (1L << (Class - 53)) | (1L << (Enum - 53)) | (1L << (Extends - 53)) | (1L << (Super - 53)) | (1L << (Const - 53)) | (1L << (Export - 53)) | (1L << (Import - 53)) | (1L << (Implements - 53)) | (1L << (Let - 53)) | (1L << (Private - 53)) | (1L << (Public - 53)) | (1L << (Interface - 53)) | (1L << (Package - 53)) | (1L << (Protected - 53)) | (1L << (Static - 53)) | (1L << (Yield - 53)) | (1L << (Any - 53)) | (1L << (Number - 53)) | (1L << (Boolean - 53)) | (1L << (String - 53)) | (1L << (Symbol - 53)) | (1L << (TypeAlias - 53)))) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & ((1L << (Get - 117)) | (1L << (Set - 117)) | (1L << (Constructor - 117)) | (1L << (Namespace - 117)) | (1L << (Require - 117)) | (1L << (Module - 117)) | (1L << (Declare - 117)) | (1L << (Abstract - 117)) | (1L << (Is - 117)) | (1L << (Infer - 117)) | (1L << (Never - 117)) | (1L << (Unknown - 117)) | (1L << (Asserts - 117)) | (1L << (Identifier - 117)))) != 0)) {
				{
				setState(598);
				typeParameterList();
				}
			}

			setState(601);
			match(MoreThan);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeParameterListContext extends ParserRuleContext {
		public List<TypeParameterContext> typeParameter() {
			return getRuleContexts(TypeParameterContext.class);
		}
		public TypeParameterContext typeParameter(int i) {
			return getRuleContext(TypeParameterContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public TypeParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParameterListContext typeParameterList() throws RecognitionException {
		TypeParameterListContext _localctx = new TypeParameterListContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_typeParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(603);
			typeParameter();
			setState(608);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(604);
				match(Comma);
				setState(605);
				typeParameter();
				}
				}
				setState(610);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeParameterContext extends ParserRuleContext {
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public DefaultTypeContext defaultType() {
			return getRuleContext(DefaultTypeContext.class,0);
		}
		public TypeParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParameterContext typeParameter() throws RecognitionException {
		TypeParameterContext _localctx = new TypeParameterContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(611);
			identifierName();
			setState(613);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(612);
				constraint();
				}
			}

			setState(617);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(615);
				match(Assign);
				setState(616);
				defaultType();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintContext extends ParserRuleContext {
		public TerminalNode Extends() { return getToken(TypeScriptParser.Extends, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_constraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(619);
			match(Extends);
			setState(620);
			typeRef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefaultTypeContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public DefaultTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDefaultType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDefaultType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDefaultType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultTypeContext defaultType() throws RecognitionException {
		DefaultTypeContext _localctx = new DefaultTypeContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_defaultType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(622);
			typeRef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModuleDeclarationContext extends ParserRuleContext {
		public TerminalNode Module() { return getToken(TypeScriptParser.Module, 0); }
		public ModuleNameContext moduleName() {
			return getRuleContext(ModuleNameContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ModuleDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_moduleDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterModuleDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitModuleDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitModuleDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModuleDeclarationContext moduleDeclaration() throws RecognitionException {
		ModuleDeclarationContext _localctx = new ModuleDeclarationContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_moduleDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(624);
			match(Module);
			setState(625);
			moduleName();
			setState(626);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModuleNameContext extends ParserRuleContext {
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public ModuleNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_moduleName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterModuleName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitModuleName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitModuleName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModuleNameContext moduleName() throws RecognitionException {
		ModuleNameContext _localctx = new ModuleNameContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_moduleName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(628);
			_la = _input.LA(1);
			if ( !(_la==Identifier || _la==StringLiteral) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamespaceDeclarationContext extends ParserRuleContext {
		public TerminalNode Namespace() { return getToken(TypeScriptParser.Namespace, 0); }
		public NamespaceNameContext namespaceName() {
			return getRuleContext(NamespaceNameContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public NamespaceDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespaceDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterNamespaceDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitNamespaceDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitNamespaceDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamespaceDeclarationContext namespaceDeclaration() throws RecognitionException {
		NamespaceDeclarationContext _localctx = new NamespaceDeclarationContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_namespaceDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(630);
			match(Namespace);
			setState(631);
			namespaceName();
			setState(632);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamespaceNameContext extends ParserRuleContext {
		public List<TypeReferenceNameContext> typeReferenceName() {
			return getRuleContexts(TypeReferenceNameContext.class);
		}
		public TypeReferenceNameContext typeReferenceName(int i) {
			return getRuleContext(TypeReferenceNameContext.class,i);
		}
		public List<TerminalNode> Dot() { return getTokens(TypeScriptParser.Dot); }
		public TerminalNode Dot(int i) {
			return getToken(TypeScriptParser.Dot, i);
		}
		public NamespaceNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespaceName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterNamespaceName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitNamespaceName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitNamespaceName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamespaceNameContext namespaceName() throws RecognitionException {
		NamespaceNameContext _localctx = new NamespaceNameContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_namespaceName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(634);
			typeReferenceName();
			setState(639);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(635);
					match(Dot);
					setState(636);
					typeReferenceName();
					}
					} 
				}
				setState(641);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DecoratorListContext extends ParserRuleContext {
		public List<DecoratorContext> decorator() {
			return getRuleContexts(DecoratorContext.class);
		}
		public DecoratorContext decorator(int i) {
			return getRuleContext(DecoratorContext.class,i);
		}
		public DecoratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decoratorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDecoratorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDecoratorList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDecoratorList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecoratorListContext decoratorList() throws RecognitionException {
		DecoratorListContext _localctx = new DecoratorListContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_decoratorList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(643); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(642);
					decorator();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(645); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DecoratorContext extends ParserRuleContext {
		public TerminalNode At() { return getToken(TypeScriptParser.At, 0); }
		public DecoratorMemberExpressionContext decoratorMemberExpression() {
			return getRuleContext(DecoratorMemberExpressionContext.class,0);
		}
		public DecoratorCallExpressionContext decoratorCallExpression() {
			return getRuleContext(DecoratorCallExpressionContext.class,0);
		}
		public DecoratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decorator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDecorator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDecorator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDecorator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecoratorContext decorator() throws RecognitionException {
		DecoratorContext _localctx = new DecoratorContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_decorator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(647);
			match(At);
			setState(650);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				setState(648);
				decoratorMemberExpression(0);
				}
				break;
			case 2:
				{
				setState(649);
				decoratorCallExpression();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DecoratorMemberExpressionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public DecoratorMemberExpressionContext decoratorMemberExpression() {
			return getRuleContext(DecoratorMemberExpressionContext.class,0);
		}
		public TerminalNode Dot() { return getToken(TypeScriptParser.Dot, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public DecoratorMemberExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decoratorMemberExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDecoratorMemberExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDecoratorMemberExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDecoratorMemberExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecoratorMemberExpressionContext decoratorMemberExpression() throws RecognitionException {
		return decoratorMemberExpression(0);
	}

	private DecoratorMemberExpressionContext decoratorMemberExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		DecoratorMemberExpressionContext _localctx = new DecoratorMemberExpressionContext(_ctx, _parentState);
		DecoratorMemberExpressionContext _prevctx = _localctx;
		int _startState = 96;
		enterRecursionRule(_localctx, 96, RULE_decoratorMemberExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(658);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(653);
				match(Identifier);
				}
				break;
			case OpenParen:
				{
				setState(654);
				match(OpenParen);
				setState(655);
				singleExpression(0);
				setState(656);
				match(CloseParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(665);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new DecoratorMemberExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_decoratorMemberExpression);
					setState(660);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(661);
					match(Dot);
					setState(662);
					identifierName();
					}
					} 
				}
				setState(667);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class DecoratorCallExpressionContext extends ParserRuleContext {
		public DecoratorMemberExpressionContext decoratorMemberExpression() {
			return getRuleContext(DecoratorMemberExpressionContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public DecoratorCallExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decoratorCallExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDecoratorCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDecoratorCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDecoratorCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecoratorCallExpressionContext decoratorCallExpression() throws RecognitionException {
		DecoratorCallExpressionContext _localctx = new DecoratorCallExpressionContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_decoratorCallExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(668);
			decoratorMemberExpression(0);
			setState(669);
			arguments();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceDeclarationContext extends ParserRuleContext {
		public TerminalNode Interface() { return getToken(TypeScriptParser.Interface, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public InterfaceExtendsClauseContext interfaceExtendsClause() {
			return getRuleContext(InterfaceExtendsClauseContext.class,0);
		}
		public InterfaceBodyContext interfaceBody() {
			return getRuleContext(InterfaceBodyContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public InterfaceDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterInterfaceDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitInterfaceDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitInterfaceDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceDeclarationContext interfaceDeclaration() throws RecognitionException {
		InterfaceDeclarationContext _localctx = new InterfaceDeclarationContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(671);
			match(Interface);
			setState(672);
			identifierName();
			setState(674);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(673);
				typeParameters();
				}
			}

			setState(677);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(676);
				interfaceExtendsClause();
				}
			}

			setState(679);
			match(OpenBrace);
			setState(681);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (Plus - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(680);
				interfaceBody();
				}
			}

			setState(683);
			match(CloseBrace);
			setState(685);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				{
				setState(684);
				match(SemiColon);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceExtendsClauseContext extends ParserRuleContext {
		public TerminalNode Extends() { return getToken(TypeScriptParser.Extends, 0); }
		public ClassOrInterfaceTypeListContext classOrInterfaceTypeList() {
			return getRuleContext(ClassOrInterfaceTypeListContext.class,0);
		}
		public InterfaceExtendsClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceExtendsClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterInterfaceExtendsClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitInterfaceExtendsClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitInterfaceExtendsClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceExtendsClauseContext interfaceExtendsClause() throws RecognitionException {
		InterfaceExtendsClauseContext _localctx = new InterfaceExtendsClauseContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_interfaceExtendsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(687);
			match(Extends);
			setState(688);
			classOrInterfaceTypeList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassOrInterfaceTypeListContext extends ParserRuleContext {
		public List<ParameterizedTypeRefContext> parameterizedTypeRef() {
			return getRuleContexts(ParameterizedTypeRefContext.class);
		}
		public ParameterizedTypeRefContext parameterizedTypeRef(int i) {
			return getRuleContext(ParameterizedTypeRefContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public ClassOrInterfaceTypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classOrInterfaceTypeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassOrInterfaceTypeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassOrInterfaceTypeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassOrInterfaceTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassOrInterfaceTypeListContext classOrInterfaceTypeList() throws RecognitionException {
		ClassOrInterfaceTypeListContext _localctx = new ClassOrInterfaceTypeListContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_classOrInterfaceTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(690);
			parameterizedTypeRef();
			setState(695);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(691);
				match(Comma);
				setState(692);
				parameterizedTypeRef();
				}
				}
				setState(697);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceBodyContext extends ParserRuleContext {
		public InterfaceMemberListContext interfaceMemberList() {
			return getRuleContext(InterfaceMemberListContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public TerminalNode Comma() { return getToken(TypeScriptParser.Comma, 0); }
		public InterfaceBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterInterfaceBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitInterfaceBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitInterfaceBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceBodyContext interfaceBody() throws RecognitionException {
		InterfaceBodyContext _localctx = new InterfaceBodyContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(698);
			interfaceMemberList();
			setState(700);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon || _la==Comma) {
				{
				setState(699);
				_la = _input.LA(1);
				if ( !(_la==SemiColon || _la==Comma) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceMemberListContext extends ParserRuleContext {
		public List<InterfaceMemberContext> interfaceMember() {
			return getRuleContexts(InterfaceMemberContext.class);
		}
		public InterfaceMemberContext interfaceMember(int i) {
			return getRuleContext(InterfaceMemberContext.class,i);
		}
		public List<TerminalNode> SemiColon() { return getTokens(TypeScriptParser.SemiColon); }
		public TerminalNode SemiColon(int i) {
			return getToken(TypeScriptParser.SemiColon, i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public InterfaceMemberListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceMemberList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterInterfaceMemberList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitInterfaceMemberList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitInterfaceMemberList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceMemberListContext interfaceMemberList() throws RecognitionException {
		InterfaceMemberListContext _localctx = new InterfaceMemberListContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_interfaceMemberList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(702);
			interfaceMember();
			setState(711);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(706);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case OpenBracket:
					case OpenParen:
					case Plus:
					case Minus:
					case LessThan:
					case NullLiteral:
					case UndefinedLiteral:
					case BooleanLiteral:
					case DecimalLiteral:
					case HexIntegerLiteral:
					case OctalIntegerLiteral:
					case OctalIntegerLiteral2:
					case BinaryIntegerLiteral:
					case Break:
					case Do:
					case Instanceof:
					case Typeof:
					case Unique:
					case Case:
					case Else:
					case New:
					case Target:
					case Var:
					case Catch:
					case Finally:
					case Return:
					case Void:
					case Continue:
					case For:
					case Switch:
					case While:
					case Debugger:
					case Function:
					case This:
					case With:
					case Default:
					case If:
					case Throw:
					case Delete:
					case In:
					case Try:
					case As:
					case From:
					case ReadOnly:
					case Async:
					case Await:
					case Class:
					case Enum:
					case Extends:
					case Super:
					case Const:
					case Export:
					case Import:
					case Implements:
					case Let:
					case Private:
					case Public:
					case Interface:
					case Package:
					case Protected:
					case Static:
					case Yield:
					case Any:
					case Number:
					case Boolean:
					case String:
					case Symbol:
					case TypeAlias:
					case Get:
					case Set:
					case Constructor:
					case Namespace:
					case Require:
					case Module:
					case Declare:
					case Abstract:
					case Is:
					case Infer:
					case Never:
					case Unknown:
					case Asserts:
					case Identifier:
					case StringLiteral:
						{
						lineTerminatorAhead();
						}
						break;
					case SemiColon:
						{
						setState(704);
						match(SemiColon);
						}
						break;
					case Comma:
						{
						setState(705);
						match(Comma);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(708);
					interfaceMember();
					}
					} 
				}
				setState(713);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceMemberContext extends ParserRuleContext {
		public ConstructSignatureContext constructSignature() {
			return getRuleContext(ConstructSignatureContext.class,0);
		}
		public CallSignatureContext callSignature() {
			return getRuleContext(CallSignatureContext.class,0);
		}
		public IndexSignatureContext indexSignature() {
			return getRuleContext(IndexSignatureContext.class,0);
		}
		public GetAccessorContext getAccessor() {
			return getRuleContext(GetAccessorContext.class,0);
		}
		public SetAccessorContext setAccessor() {
			return getRuleContext(SetAccessorContext.class,0);
		}
		public MethodSignatureContext methodSignature() {
			return getRuleContext(MethodSignatureContext.class,0);
		}
		public PropertySignatureContext propertySignature() {
			return getRuleContext(PropertySignatureContext.class,0);
		}
		public InterfaceMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceMember; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterInterfaceMember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitInterfaceMember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitInterfaceMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceMemberContext interfaceMember() throws RecognitionException {
		InterfaceMemberContext _localctx = new InterfaceMemberContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_interfaceMember);
		try {
			setState(721);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(714);
				constructSignature();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(715);
				callSignature();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(716);
				indexSignature();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(717);
				getAccessor();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(718);
				setAccessor();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(719);
				methodSignature();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(720);
				propertySignature();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructSignatureContext extends ParserRuleContext {
		public TerminalNode New() { return getToken(TypeScriptParser.New, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public ConstructSignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructSignature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterConstructSignature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitConstructSignature(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitConstructSignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructSignatureContext constructSignature() throws RecognitionException {
		ConstructSignatureContext _localctx = new ConstructSignatureContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_constructSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(723);
			match(New);
			setState(725);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(724);
				typeParameters();
				}
			}

			setState(727);
			parameterBlock();
			setState(729);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(728);
				colonSepTypeRef();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallSignatureContext extends ParserRuleContext {
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public TypePredicateWithOperatorTypeRefContext typePredicateWithOperatorTypeRef() {
			return getRuleContext(TypePredicateWithOperatorTypeRefContext.class,0);
		}
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public CallSignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callSignature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterCallSignature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitCallSignature(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitCallSignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallSignatureContext callSignature() throws RecognitionException {
		CallSignatureContext _localctx = new CallSignatureContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_callSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(732);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(731);
				typeParameters();
				}
			}

			setState(734);
			parameterBlock();
			setState(740);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				{
				setState(735);
				match(Colon);
				setState(738);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
				case 1:
					{
					setState(736);
					typePredicateWithOperatorTypeRef();
					}
					break;
				case 2:
					{
					setState(737);
					typeRef();
					}
					break;
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexSignatureContext extends ParserRuleContext {
		public TerminalNode OpenBracket() { return getToken(TypeScriptParser.OpenBracket, 0); }
		public IndexSignatureElementContext indexSignatureElement() {
			return getRuleContext(IndexSignatureElementContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(TypeScriptParser.CloseBracket, 0); }
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public List<TerminalNode> ReadOnly() { return getTokens(TypeScriptParser.ReadOnly); }
		public TerminalNode ReadOnly(int i) {
			return getToken(TypeScriptParser.ReadOnly, i);
		}
		public List<TerminalNode> Minus() { return getTokens(TypeScriptParser.Minus); }
		public TerminalNode Minus(int i) {
			return getToken(TypeScriptParser.Minus, i);
		}
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public List<TerminalNode> Plus() { return getTokens(TypeScriptParser.Plus); }
		public TerminalNode Plus(int i) {
			return getToken(TypeScriptParser.Plus, i);
		}
		public IndexSignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexSignature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIndexSignature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIndexSignature(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIndexSignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexSignatureContext indexSignature() throws RecognitionException {
		IndexSignatureContext _localctx = new IndexSignatureContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_indexSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(743);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				{
				setState(742);
				match(ReadOnly);
				}
				break;
			}
			setState(751);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Plus:
			case ReadOnly:
				{
				setState(746);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Plus) {
					{
					setState(745);
					match(Plus);
					}
				}

				setState(748);
				match(ReadOnly);
				}
				break;
			case Minus:
				{
				setState(749);
				match(Minus);
				setState(750);
				match(ReadOnly);
				}
				break;
			case OpenBracket:
				break;
			default:
				break;
			}
			setState(753);
			match(OpenBracket);
			setState(754);
			indexSignatureElement();
			setState(755);
			match(CloseBracket);
			setState(762);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QuestionMark:
			case Plus:
				{
				setState(757);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Plus) {
					{
					setState(756);
					match(Plus);
					}
				}

				setState(759);
				match(QuestionMark);
				}
				break;
			case Minus:
				{
				setState(760);
				match(Minus);
				setState(761);
				match(QuestionMark);
				}
				break;
			case Colon:
				break;
			default:
				break;
			}
			setState(764);
			colonSepTypeRef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexSignatureElementContext extends ParserRuleContext {
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public TerminalNode Number() { return getToken(TypeScriptParser.Number, 0); }
		public TerminalNode String() { return getToken(TypeScriptParser.String, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TerminalNode In() { return getToken(TypeScriptParser.In, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public IndexSignatureElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexSignatureElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIndexSignatureElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIndexSignatureElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIndexSignatureElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexSignatureElementContext indexSignatureElement() throws RecognitionException {
		IndexSignatureElementContext _localctx = new IndexSignatureElementContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_indexSignatureElement);
		int _la;
		try {
			setState(773);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(766);
				identifierName();
				setState(767);
				match(Colon);
				setState(768);
				_la = _input.LA(1);
				if ( !(_la==Number || _la==String) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(770);
				match(Identifier);
				setState(771);
				match(In);
				setState(772);
				typeRef();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodSignatureContext extends ParserRuleContext {
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public CallSignatureContext callSignature() {
			return getRuleContext(CallSignatureContext.class,0);
		}
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public MethodSignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodSignature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterMethodSignature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitMethodSignature(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitMethodSignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodSignatureContext methodSignature() throws RecognitionException {
		MethodSignatureContext _localctx = new MethodSignatureContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_methodSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(775);
			propertyName();
			setState(777);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(776);
				match(QuestionMark);
				}
			}

			setState(779);
			callSignature();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertySignatureContext extends ParserRuleContext {
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public TerminalNode ReadOnly() { return getToken(TypeScriptParser.ReadOnly, 0); }
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public PropertySignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertySignature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertySignature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertySignature(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertySignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertySignatureContext propertySignature() throws RecognitionException {
		PropertySignatureContext _localctx = new PropertySignatureContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_propertySignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(782);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,72,_ctx) ) {
			case 1:
				{
				setState(781);
				match(ReadOnly);
				}
				break;
			}
			setState(784);
			propertyName();
			setState(786);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(785);
				match(QuestionMark);
				}
			}

			setState(789);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(788);
				colonSepTypeRef();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumDeclarationContext extends ParserRuleContext {
		public TerminalNode Enum() { return getToken(TypeScriptParser.Enum, 0); }
		public IdentifierOrKeyWordContext identifierOrKeyWord() {
			return getRuleContext(IdentifierOrKeyWordContext.class,0);
		}
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public TerminalNode Const() { return getToken(TypeScriptParser.Const, 0); }
		public EnumBodyContext enumBody() {
			return getRuleContext(EnumBodyContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public EnumDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterEnumDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitEnumDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitEnumDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumDeclarationContext enumDeclaration() throws RecognitionException {
		EnumDeclarationContext _localctx = new EnumDeclarationContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(792);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Const) {
				{
				setState(791);
				match(Const);
				}
			}

			setState(794);
			match(Enum);
			setState(795);
			identifierOrKeyWord();
			setState(796);
			match(OpenBrace);
			setState(798);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (Minus - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(797);
				enumBody();
				}
			}

			setState(800);
			match(CloseBrace);
			setState(802);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
			case 1:
				{
				setState(801);
				match(SemiColon);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumBodyContext extends ParserRuleContext {
		public EnumMemberListContext enumMemberList() {
			return getRuleContext(EnumMemberListContext.class,0);
		}
		public TerminalNode Comma() { return getToken(TypeScriptParser.Comma, 0); }
		public EnumBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterEnumBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitEnumBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitEnumBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumBodyContext enumBody() throws RecognitionException {
		EnumBodyContext _localctx = new EnumBodyContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_enumBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(804);
			enumMemberList();
			setState(806);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(805);
				match(Comma);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumMemberListContext extends ParserRuleContext {
		public List<EnumMemberContext> enumMember() {
			return getRuleContexts(EnumMemberContext.class);
		}
		public EnumMemberContext enumMember(int i) {
			return getRuleContext(EnumMemberContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public EnumMemberListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumMemberList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterEnumMemberList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitEnumMemberList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitEnumMemberList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumMemberListContext enumMemberList() throws RecognitionException {
		EnumMemberListContext _localctx = new EnumMemberListContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_enumMemberList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(808);
			enumMember();
			setState(813);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,79,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(809);
					match(Comma);
					setState(810);
					enumMember();
					}
					} 
				}
				setState(815);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,79,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumMemberContext extends ParserRuleContext {
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public EnumMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumMember; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterEnumMember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitEnumMember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitEnumMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumMemberContext enumMember() throws RecognitionException {
		EnumMemberContext _localctx = new EnumMemberContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_enumMember);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(816);
			propertyName();
			setState(819);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(817);
				match(Assign);
				setState(818);
				singleExpression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TerminalNode Function() { return getToken(TypeScriptParser.Function, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public CallSignatureContext callSignature() {
			return getRuleContext(CallSignatureContext.class,0);
		}
		public TerminalNode Multiply() { return getToken(TypeScriptParser.Multiply, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(821);
			match(Function);
			setState(823);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(822);
				match(Multiply);
				}
			}

			setState(825);
			identifierName();
			setState(826);
			callSignature();
			setState(828);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,82,_ctx) ) {
			case 1:
				{
				setState(827);
				block();
				}
				break;
			}
			setState(831);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,83,_ctx) ) {
			case 1:
				{
				setState(830);
				match(SemiColon);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public TerminalNode Class() { return getToken(TypeScriptParser.Class, 0); }
		public IdentifierOrKeyWordContext identifierOrKeyWord() {
			return getRuleContext(IdentifierOrKeyWordContext.class,0);
		}
		public ClassHeritageContext classHeritage() {
			return getRuleContext(ClassHeritageContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public TerminalNode Abstract() { return getToken(TypeScriptParser.Abstract, 0); }
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(834);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Abstract) {
				{
				setState(833);
				match(Abstract);
				}
			}

			setState(836);
			match(Class);
			setState(837);
			identifierOrKeyWord();
			setState(839);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(838);
				typeParameters();
				}
			}

			setState(841);
			classHeritage();
			setState(842);
			classBody();
			setState(844);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
			case 1:
				{
				setState(843);
				match(SemiColon);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassHeritageContext extends ParserRuleContext {
		public ClassExtendsClauseContext classExtendsClause() {
			return getRuleContext(ClassExtendsClauseContext.class,0);
		}
		public ClassImplementsClauseContext classImplementsClause() {
			return getRuleContext(ClassImplementsClauseContext.class,0);
		}
		public ClassHeritageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classHeritage; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassHeritage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassHeritage(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassHeritage(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassHeritageContext classHeritage() throws RecognitionException {
		ClassHeritageContext _localctx = new ClassHeritageContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_classHeritage);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(847);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(846);
				classExtendsClause();
				}
			}

			setState(850);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Implements) {
				{
				setState(849);
				classImplementsClause();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassExtendsClauseContext extends ParserRuleContext {
		public TerminalNode Extends() { return getToken(TypeScriptParser.Extends, 0); }
		public ParameterizedTypeRefContext parameterizedTypeRef() {
			return getRuleContext(ParameterizedTypeRefContext.class,0);
		}
		public ClassExtendsClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classExtendsClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassExtendsClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassExtendsClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassExtendsClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassExtendsClauseContext classExtendsClause() throws RecognitionException {
		ClassExtendsClauseContext _localctx = new ClassExtendsClauseContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_classExtendsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(852);
			match(Extends);
			setState(853);
			parameterizedTypeRef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassImplementsClauseContext extends ParserRuleContext {
		public TerminalNode Implements() { return getToken(TypeScriptParser.Implements, 0); }
		public ClassOrInterfaceTypeListContext classOrInterfaceTypeList() {
			return getRuleContext(ClassOrInterfaceTypeListContext.class,0);
		}
		public ClassImplementsClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classImplementsClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassImplementsClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassImplementsClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassImplementsClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassImplementsClauseContext classImplementsClause() throws RecognitionException {
		ClassImplementsClauseContext _localctx = new ClassImplementsClauseContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_classImplementsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(855);
			match(Implements);
			setState(856);
			classOrInterfaceTypeList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public ClassMemberListContext classMemberList() {
			return getRuleContext(ClassMemberListContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(858);
			match(OpenBrace);
			setState(860);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (Plus - 5)) | (1L << (Minus - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(859);
				classMemberList();
				}
			}

			setState(863);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon) {
				{
				setState(862);
				match(SemiColon);
				}
			}

			setState(865);
			match(CloseBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassMemberListContext extends ParserRuleContext {
		public List<ClassMemberContext> classMember() {
			return getRuleContexts(ClassMemberContext.class);
		}
		public ClassMemberContext classMember(int i) {
			return getRuleContext(ClassMemberContext.class,i);
		}
		public List<TerminalNode> SemiColon() { return getTokens(TypeScriptParser.SemiColon); }
		public TerminalNode SemiColon(int i) {
			return getToken(TypeScriptParser.SemiColon, i);
		}
		public ClassMemberListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classMemberList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassMemberList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassMemberList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassMemberList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassMemberListContext classMemberList() throws RecognitionException {
		ClassMemberListContext _localctx = new ClassMemberListContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_classMemberList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(867);
			classMember();
			setState(875);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(870);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case OpenBracket:
					case Plus:
					case Minus:
					case NullLiteral:
					case UndefinedLiteral:
					case BooleanLiteral:
					case DecimalLiteral:
					case HexIntegerLiteral:
					case OctalIntegerLiteral:
					case OctalIntegerLiteral2:
					case BinaryIntegerLiteral:
					case Break:
					case Do:
					case Instanceof:
					case Typeof:
					case Unique:
					case Case:
					case Else:
					case New:
					case Target:
					case Var:
					case Catch:
					case Finally:
					case Return:
					case Void:
					case Continue:
					case For:
					case Switch:
					case While:
					case Debugger:
					case Function:
					case This:
					case With:
					case Default:
					case If:
					case Throw:
					case Delete:
					case In:
					case Try:
					case As:
					case From:
					case ReadOnly:
					case Async:
					case Await:
					case Class:
					case Enum:
					case Extends:
					case Super:
					case Const:
					case Export:
					case Import:
					case Implements:
					case Let:
					case Private:
					case Public:
					case Interface:
					case Package:
					case Protected:
					case Static:
					case Yield:
					case Any:
					case Number:
					case Boolean:
					case String:
					case Symbol:
					case TypeAlias:
					case Get:
					case Set:
					case Constructor:
					case Namespace:
					case Require:
					case Module:
					case Declare:
					case Abstract:
					case Is:
					case Infer:
					case Never:
					case Unknown:
					case Asserts:
					case At:
					case Identifier:
					case StringLiteral:
						{
						lineTerminatorAhead();
						}
						break;
					case SemiColon:
						{
						setState(869);
						match(SemiColon);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(872);
					classMember();
					}
					} 
				}
				setState(877);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassMemberContext extends ParserRuleContext {
		public ConstructorDeclarationContext constructorDeclaration() {
			return getRuleContext(ConstructorDeclarationContext.class,0);
		}
		public IndexSignatureContext indexSignature() {
			return getRuleContext(IndexSignatureContext.class,0);
		}
		public PropertyMemberDeclarationContext propertyMemberDeclaration() {
			return getRuleContext(PropertyMemberDeclarationContext.class,0);
		}
		public DecoratorListContext decoratorList() {
			return getRuleContext(DecoratorListContext.class,0);
		}
		public ClassMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classMember; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassMember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassMember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassMemberContext classMember() throws RecognitionException {
		ClassMemberContext _localctx = new ClassMemberContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_classMember);
		int _la;
		try {
			setState(884);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,94,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(878);
				constructorDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(879);
				indexSignature();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(881);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==At) {
					{
					setState(880);
					decoratorList();
					}
				}

				setState(883);
				propertyMemberDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorDeclarationContext extends ParserRuleContext {
		public TerminalNode Constructor() { return getToken(TypeScriptParser.Constructor, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public AccessibilityModifierContext accessibilityModifier() {
			return getRuleContext(AccessibilityModifierContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterConstructorDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitConstructorDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclarationContext constructorDeclaration() throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(887);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 104)) & ~0x3f) == 0 && ((1L << (_la - 104)) & ((1L << (Private - 104)) | (1L << (Public - 104)) | (1L << (Protected - 104)))) != 0)) {
				{
				setState(886);
				accessibilityModifier();
				}
			}

			setState(889);
			match(Constructor);
			setState(890);
			parameterBlock();
			setState(892);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(891);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyMemberDeclarationContext extends ParserRuleContext {
		public AbstractDeclarationContext abstractDeclaration() {
			return getRuleContext(AbstractDeclarationContext.class,0);
		}
		public PropertyMemberContext propertyMember() {
			return getRuleContext(PropertyMemberContext.class,0);
		}
		public PropertyMemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyMemberDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertyMemberDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertyMemberDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertyMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyMemberDeclarationContext propertyMemberDeclaration() throws RecognitionException {
		PropertyMemberDeclarationContext _localctx = new PropertyMemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_propertyMemberDeclaration);
		try {
			setState(896);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(894);
				abstractDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(895);
				propertyMember();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AbstractDeclarationContext extends ParserRuleContext {
		public TerminalNode Abstract() { return getToken(TypeScriptParser.Abstract, 0); }
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public CallSignatureContext callSignature() {
			return getRuleContext(CallSignatureContext.class,0);
		}
		public VariableStatementContext variableStatement() {
			return getRuleContext(VariableStatementContext.class,0);
		}
		public AbstractDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abstractDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterAbstractDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitAbstractDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitAbstractDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AbstractDeclarationContext abstractDeclaration() throws RecognitionException {
		AbstractDeclarationContext _localctx = new AbstractDeclarationContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_abstractDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(898);
			match(Abstract);
			setState(902);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(899);
				match(Identifier);
				setState(900);
				callSignature();
				}
				break;
			case Var:
			case ReadOnly:
			case Const:
			case Let:
			case Private:
			case Public:
			case Protected:
				{
				setState(901);
				variableStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(904);
			eos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyMemberContext extends ParserRuleContext {
		public PropertyMemberBaseContext propertyMemberBase() {
			return getRuleContext(PropertyMemberBaseContext.class,0);
		}
		public GetAccessorContext getAccessor() {
			return getRuleContext(GetAccessorContext.class,0);
		}
		public SetAccessorContext setAccessor() {
			return getRuleContext(SetAccessorContext.class,0);
		}
		public PropertyOrMethodContext propertyOrMethod() {
			return getRuleContext(PropertyOrMethodContext.class,0);
		}
		public PropertyMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyMember; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertyMember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertyMember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertyMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyMemberContext propertyMember() throws RecognitionException {
		PropertyMemberContext _localctx = new PropertyMemberContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_propertyMember);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(906);
			propertyMemberBase();
			setState(910);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,99,_ctx) ) {
			case 1:
				{
				setState(907);
				getAccessor();
				}
				break;
			case 2:
				{
				setState(908);
				setAccessor();
				}
				break;
			case 3:
				{
				setState(909);
				propertyOrMethod();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyMemberBaseContext extends ParserRuleContext {
		public TerminalNode Async() { return getToken(TypeScriptParser.Async, 0); }
		public AccessibilityModifierContext accessibilityModifier() {
			return getRuleContext(AccessibilityModifierContext.class,0);
		}
		public TerminalNode Static() { return getToken(TypeScriptParser.Static, 0); }
		public PropertyMemberBaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyMemberBase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertyMemberBase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertyMemberBase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertyMemberBase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyMemberBaseContext propertyMemberBase() throws RecognitionException {
		PropertyMemberBaseContext _localctx = new PropertyMemberBaseContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_propertyMemberBase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(913);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
			case 1:
				{
				setState(912);
				match(Async);
				}
				break;
			}
			setState(916);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,101,_ctx) ) {
			case 1:
				{
				setState(915);
				accessibilityModifier();
				}
				break;
			}
			setState(919);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,102,_ctx) ) {
			case 1:
				{
				setState(918);
				match(Static);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyOrMethodContext extends ParserRuleContext {
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public TerminalNode ReadOnly() { return getToken(TypeScriptParser.ReadOnly, 0); }
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public CallSignatureContext callSignature() {
			return getRuleContext(CallSignatureContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public PropertyOrMethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyOrMethod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertyOrMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertyOrMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertyOrMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyOrMethodContext propertyOrMethod() throws RecognitionException {
		PropertyOrMethodContext _localctx = new PropertyOrMethodContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_propertyOrMethod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(922);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,103,_ctx) ) {
			case 1:
				{
				setState(921);
				match(ReadOnly);
				}
				break;
			}
			setState(924);
			propertyName();
			setState(926);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(925);
				match(QuestionMark);
				}
			}

			setState(938);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
			case CloseBrace:
			case SemiColon:
			case Assign:
			case Colon:
			case Plus:
			case Minus:
			case NullLiteral:
			case UndefinedLiteral:
			case BooleanLiteral:
			case DecimalLiteral:
			case HexIntegerLiteral:
			case OctalIntegerLiteral:
			case OctalIntegerLiteral2:
			case BinaryIntegerLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Typeof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case ReadOnly:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
			case At:
			case Identifier:
			case StringLiteral:
				{
				{
				setState(929);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(928);
					colonSepTypeRef();
					}
				}

				setState(932);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Assign) {
					{
					setState(931);
					initializer();
					}
				}

				}
				}
				break;
			case OpenParen:
			case LessThan:
				{
				{
				setState(934);
				callSignature();
				setState(936);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OpenBrace) {
					{
					setState(935);
					block();
					}
				}

				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitializerContext extends ParserRuleContext {
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public InitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitializerContext initializer() throws RecognitionException {
		InitializerContext _localctx = new InitializerContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_initializer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(940);
			match(Assign);
			setState(941);
			singleExpression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterBlockContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public TerminalNode This() { return getToken(TypeScriptParser.This, 0); }
		public TerminalNode Comma() { return getToken(TypeScriptParser.Comma, 0); }
		public ParameterListTrailingCommaContext parameterListTrailingComma() {
			return getRuleContext(ParameterListTrailingCommaContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public ParameterBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterParameterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitParameterBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitParameterBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterBlockContext parameterBlock() throws RecognitionException {
		ParameterBlockContext _localctx = new ParameterBlockContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_parameterBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(943);
			match(OpenParen);
			setState(949);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,110,_ctx) ) {
			case 1:
				{
				setState(944);
				match(This);
				setState(946);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(945);
					colonSepTypeRef();
					}
				}

				setState(948);
				match(Comma);
				}
				break;
			}
			setState(952);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenBrace - 5)) | (1L << (Ellipsis - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(951);
				parameterListTrailingComma();
				}
			}

			setState(954);
			match(CloseParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListTrailingCommaContext extends ParserRuleContext {
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public TerminalNode Comma() { return getToken(TypeScriptParser.Comma, 0); }
		public ParameterListTrailingCommaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterListTrailingComma; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterParameterListTrailingComma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitParameterListTrailingComma(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitParameterListTrailingComma(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListTrailingCommaContext parameterListTrailingComma() throws RecognitionException {
		ParameterListTrailingCommaContext _localctx = new ParameterListTrailingCommaContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_parameterListTrailingComma);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(956);
			parameterList();
			setState(958);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(957);
				match(Comma);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public RestParameterContext restParameter() {
			return getRuleContext(RestParameterContext.class,0);
		}
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_parameterList);
		try {
			int _alt;
			setState(973);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Ellipsis:
				enterOuterAlt(_localctx, 1);
				{
				setState(960);
				restParameter();
				}
				break;
			case OpenBracket:
			case OpenBrace:
			case NullLiteral:
			case UndefinedLiteral:
			case BooleanLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Typeof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case ReadOnly:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
			case At:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(961);
				parameter();
				setState(966);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,113,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(962);
						match(Comma);
						setState(963);
						parameter();
						}
						} 
					}
					setState(968);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,113,_ctx);
				}
				setState(971);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,114,_ctx) ) {
				case 1:
					{
					setState(969);
					match(Comma);
					setState(970);
					restParameter();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RestParameterContext extends ParserRuleContext {
		public TerminalNode Ellipsis() { return getToken(TypeScriptParser.Ellipsis, 0); }
		public IdentifierOrPatternContext identifierOrPattern() {
			return getRuleContext(IdentifierOrPatternContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public RestParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_restParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterRestParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitRestParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitRestParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RestParameterContext restParameter() throws RecognitionException {
		RestParameterContext _localctx = new RestParameterContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_restParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(975);
			match(Ellipsis);
			setState(976);
			identifierOrPattern();
			setState(978);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(977);
				colonSepTypeRef();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public RequiredParameterContext requiredParameter() {
			return getRuleContext(RequiredParameterContext.class,0);
		}
		public OptionalParameterContext optionalParameter() {
			return getRuleContext(OptionalParameterContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_parameter);
		try {
			setState(982);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,117,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(980);
				requiredParameter();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(981);
				optionalParameter();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RequiredParameterContext extends ParserRuleContext {
		public IdentifierOrPatternContext identifierOrPattern() {
			return getRuleContext(IdentifierOrPatternContext.class,0);
		}
		public DecoratorListContext decoratorList() {
			return getRuleContext(DecoratorListContext.class,0);
		}
		public AccessibilityModifierContext accessibilityModifier() {
			return getRuleContext(AccessibilityModifierContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public RequiredParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_requiredParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterRequiredParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitRequiredParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitRequiredParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RequiredParameterContext requiredParameter() throws RecognitionException {
		RequiredParameterContext _localctx = new RequiredParameterContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_requiredParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(985);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==At) {
				{
				setState(984);
				decoratorList();
				}
			}

			setState(988);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,119,_ctx) ) {
			case 1:
				{
				setState(987);
				accessibilityModifier();
				}
				break;
			}
			setState(990);
			identifierOrPattern();
			setState(992);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(991);
				colonSepTypeRef();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionalParameterContext extends ParserRuleContext {
		public IdentifierOrPatternContext identifierOrPattern() {
			return getRuleContext(IdentifierOrPatternContext.class,0);
		}
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public DecoratorListContext decoratorList() {
			return getRuleContext(DecoratorListContext.class,0);
		}
		public AccessibilityModifierContext accessibilityModifier() {
			return getRuleContext(AccessibilityModifierContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public OptionalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optionalParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterOptionalParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitOptionalParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitOptionalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionalParameterContext optionalParameter() throws RecognitionException {
		OptionalParameterContext _localctx = new OptionalParameterContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_optionalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(995);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==At) {
				{
				setState(994);
				decoratorList();
				}
			}

			setState(998);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,122,_ctx) ) {
			case 1:
				{
				setState(997);
				accessibilityModifier();
				}
				break;
			}
			setState(1000);
			identifierOrPattern();
			setState(1009);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QuestionMark:
				{
				setState(1001);
				match(QuestionMark);
				setState(1003);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(1002);
					colonSepTypeRef();
					}
				}

				}
				break;
			case Assign:
			case Colon:
				{
				setState(1006);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(1005);
					colonSepTypeRef();
					}
				}

				setState(1008);
				initializer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AccessibilityModifierContext extends ParserRuleContext {
		public TerminalNode Public() { return getToken(TypeScriptParser.Public, 0); }
		public TerminalNode Private() { return getToken(TypeScriptParser.Private, 0); }
		public TerminalNode Protected() { return getToken(TypeScriptParser.Protected, 0); }
		public AccessibilityModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_accessibilityModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterAccessibilityModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitAccessibilityModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitAccessibilityModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AccessibilityModifierContext accessibilityModifier() throws RecognitionException {
		AccessibilityModifierContext _localctx = new AccessibilityModifierContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_accessibilityModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1011);
			_la = _input.LA(1);
			if ( !(((((_la - 104)) & ~0x3f) == 0 && ((1L << (_la - 104)) & ((1L << (Private - 104)) | (1L << (Public - 104)) | (1L << (Protected - 104)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierOrPatternContext extends ParserRuleContext {
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public BindingPatternContext bindingPattern() {
			return getRuleContext(BindingPatternContext.class,0);
		}
		public IdentifierOrPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierOrPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIdentifierOrPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIdentifierOrPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIdentifierOrPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierOrPatternContext identifierOrPattern() throws RecognitionException {
		IdentifierOrPatternContext _localctx = new IdentifierOrPatternContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_identifierOrPattern);
		try {
			setState(1015);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NullLiteral:
			case UndefinedLiteral:
			case BooleanLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Typeof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case ReadOnly:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(1013);
				identifierName();
				}
				break;
			case OpenBracket:
			case OpenBrace:
				enterOuterAlt(_localctx, 2);
				{
				setState(1014);
				bindingPattern();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BindingPatternContext extends ParserRuleContext {
		public ArrayLiteralContext arrayLiteral() {
			return getRuleContext(ArrayLiteralContext.class,0);
		}
		public ObjectLiteralContext objectLiteral() {
			return getRuleContext(ObjectLiteralContext.class,0);
		}
		public BindingPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bindingPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterBindingPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitBindingPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitBindingPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BindingPatternContext bindingPattern() throws RecognitionException {
		BindingPatternContext _localctx = new BindingPatternContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_bindingPattern);
		try {
			setState(1019);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
				enterOuterAlt(_localctx, 1);
				{
				setState(1017);
				arrayLiteral();
				}
				break;
			case OpenBrace:
				enterOuterAlt(_localctx, 2);
				{
				setState(1018);
				objectLiteral();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayLiteralContext extends ParserRuleContext {
		public TerminalNode OpenBracket() { return getToken(TypeScriptParser.OpenBracket, 0); }
		public TerminalNode CloseBracket() { return getToken(TypeScriptParser.CloseBracket, 0); }
		public ElementListContext elementList() {
			return getRuleContext(ElementListContext.class,0);
		}
		public ArrayLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrayLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrayLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrayLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayLiteralContext arrayLiteral() throws RecognitionException {
		ArrayLiteralContext _localctx = new ArrayLiteralContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_arrayLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1021);
			match(OpenBracket);
			setState(1023);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBracket) | (1L << OpenBrace) | (1L << Ellipsis))) != 0) || _la==Identifier) {
				{
				setState(1022);
				elementList();
				}
			}

			setState(1025);
			match(CloseBracket);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementListContext extends ParserRuleContext {
		public List<ArrayElementContext> arrayElement() {
			return getRuleContexts(ArrayElementContext.class);
		}
		public ArrayElementContext arrayElement(int i) {
			return getRuleContext(ArrayElementContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public ElementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterElementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitElementList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitElementList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementListContext elementList() throws RecognitionException {
		ElementListContext _localctx = new ElementListContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_elementList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1027);
			arrayElement();
			setState(1036);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,130,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1029); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(1028);
						match(Comma);
						}
						}
						setState(1031); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==Comma );
					setState(1033);
					arrayElement();
					}
					} 
				}
				setState(1038);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,130,_ctx);
			}
			setState(1040);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1039);
				match(Comma);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayElementContext extends ParserRuleContext {
		public BindingElementContext bindingElement() {
			return getRuleContext(BindingElementContext.class,0);
		}
		public TerminalNode Ellipsis() { return getToken(TypeScriptParser.Ellipsis, 0); }
		public ArrayElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrayElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrayElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrayElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayElementContext arrayElement() throws RecognitionException {
		ArrayElementContext _localctx = new ArrayElementContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_arrayElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1043);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(1042);
				match(Ellipsis);
				}
			}

			setState(1045);
			bindingElement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BindingElementContext extends ParserRuleContext {
		public BindingPatternContext bindingPattern() {
			return getRuleContext(BindingPatternContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public BindingElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bindingElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterBindingElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitBindingElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitBindingElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BindingElementContext bindingElement() throws RecognitionException {
		BindingElementContext _localctx = new BindingElementContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_bindingElement);
		try {
			setState(1049);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
			case OpenBrace:
				enterOuterAlt(_localctx, 1);
				{
				setState(1047);
				bindingPattern();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1048);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectLiteralContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public List<PropertyAssignmentContext> propertyAssignment() {
			return getRuleContexts(PropertyAssignmentContext.class);
		}
		public PropertyAssignmentContext propertyAssignment(int i) {
			return getRuleContext(PropertyAssignmentContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public ObjectLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterObjectLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitObjectLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitObjectLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectLiteralContext objectLiteral() throws RecognitionException {
		ObjectLiteralContext _localctx = new ObjectLiteralContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_objectLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1051);
			match(OpenBrace);
			setState(1063);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (Ellipsis - 5)) | (1L << (Minus - 5)) | (1L << (Multiply - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(1052);
				propertyAssignment();
				setState(1057);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,134,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1053);
						match(Comma);
						setState(1054);
						propertyAssignment();
						}
						} 
					}
					setState(1059);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,134,_ctx);
				}
				setState(1061);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(1060);
					match(Comma);
					}
				}

				}
			}

			setState(1065);
			match(CloseBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyAssignmentContext extends ParserRuleContext {
		public PropertyAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyAssignment; }
	 
		public PropertyAssignmentContext() { }
		public void copyFrom(PropertyAssignmentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PropertyExpressionAssignmentContext extends PropertyAssignmentContext {
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public IdentifierOrKeyWordContext identifierOrKeyWord() {
			return getRuleContext(IdentifierOrKeyWordContext.class,0);
		}
		public BindingPatternContext bindingPattern() {
			return getRuleContext(BindingPatternContext.class,0);
		}
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public PropertyExpressionAssignmentContext(PropertyAssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertyExpressionAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertyExpressionAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertyExpressionAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PropertySetterContext extends PropertyAssignmentContext {
		public SetAccessorContext setAccessor() {
			return getRuleContext(SetAccessorContext.class,0);
		}
		public PropertySetterContext(PropertyAssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertySetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertySetter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertySetter(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PropertyGetterContext extends PropertyAssignmentContext {
		public GetAccessorContext getAccessor() {
			return getRuleContext(GetAccessorContext.class,0);
		}
		public PropertyGetterContext(PropertyAssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertyGetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertyGetter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertyGetter(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RestParameterInObjectContext extends PropertyAssignmentContext {
		public RestParameterContext restParameter() {
			return getRuleContext(RestParameterContext.class,0);
		}
		public RestParameterInObjectContext(PropertyAssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterRestParameterInObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitRestParameterInObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitRestParameterInObject(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MethodPropertyContext extends PropertyAssignmentContext {
		public GeneratorMethodContext generatorMethod() {
			return getRuleContext(GeneratorMethodContext.class,0);
		}
		public MethodPropertyContext(PropertyAssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterMethodProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitMethodProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitMethodProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyAssignmentContext propertyAssignment() throws RecognitionException {
		PropertyAssignmentContext _localctx = new PropertyAssignmentContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_propertyAssignment);
		int _la;
		try {
			setState(1081);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,139,_ctx) ) {
			case 1:
				_localctx = new PropertyExpressionAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1067);
				propertyName();
				setState(1071);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Colon:
					{
					setState(1068);
					match(Colon);
					setState(1069);
					identifierOrKeyWord();
					}
					break;
				case OpenBracket:
				case OpenBrace:
					{
					setState(1070);
					bindingPattern();
					}
					break;
				case CloseBrace:
				case Comma:
				case Assign:
					break;
				default:
					break;
				}
				setState(1075);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Assign) {
					{
					setState(1073);
					match(Assign);
					setState(1074);
					singleExpression(0);
					}
				}

				}
				break;
			case 2:
				_localctx = new PropertyGetterContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1077);
				getAccessor();
				}
				break;
			case 3:
				_localctx = new PropertySetterContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1078);
				setAccessor();
				}
				break;
			case 4:
				_localctx = new MethodPropertyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1079);
				generatorMethod();
				}
				break;
			case 5:
				_localctx = new RestParameterInObjectContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1080);
				restParameter();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyNameContext extends ParserRuleContext {
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public TerminalNode OpenBracket() { return getToken(TypeScriptParser.OpenBracket, 0); }
		public TerminalNode CloseBracket() { return getToken(TypeScriptParser.CloseBracket, 0); }
		public List<IdentifierNameContext> identifierName() {
			return getRuleContexts(IdentifierNameContext.class);
		}
		public IdentifierNameContext identifierName(int i) {
			return getRuleContext(IdentifierNameContext.class,i);
		}
		public TerminalNode Dot() { return getToken(TypeScriptParser.Dot, 0); }
		public PropertyNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertyName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertyName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertyName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyNameContext propertyName() throws RecognitionException {
		PropertyNameContext _localctx = new PropertyNameContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_propertyName);
		try {
			setState(1095);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case StringLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1083);
				match(StringLiteral);
				}
				break;
			case Minus:
			case DecimalLiteral:
			case HexIntegerLiteral:
			case OctalIntegerLiteral:
			case OctalIntegerLiteral2:
			case BinaryIntegerLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1084);
				numericLiteral();
				}
				break;
			case OpenBracket:
				enterOuterAlt(_localctx, 3);
				{
				setState(1085);
				match(OpenBracket);
				setState(1091);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NullLiteral:
				case UndefinedLiteral:
				case BooleanLiteral:
				case Break:
				case Do:
				case Instanceof:
				case Typeof:
				case Unique:
				case Case:
				case Else:
				case New:
				case Target:
				case Var:
				case Catch:
				case Finally:
				case Return:
				case Void:
				case Continue:
				case For:
				case Switch:
				case While:
				case Debugger:
				case Function:
				case This:
				case With:
				case Default:
				case If:
				case Throw:
				case Delete:
				case In:
				case Try:
				case As:
				case From:
				case ReadOnly:
				case Async:
				case Await:
				case Class:
				case Enum:
				case Extends:
				case Super:
				case Const:
				case Export:
				case Import:
				case Implements:
				case Let:
				case Private:
				case Public:
				case Interface:
				case Package:
				case Protected:
				case Static:
				case Yield:
				case Any:
				case Number:
				case Boolean:
				case String:
				case Symbol:
				case TypeAlias:
				case Get:
				case Set:
				case Constructor:
				case Namespace:
				case Require:
				case Module:
				case Declare:
				case Abstract:
				case Is:
				case Infer:
				case Never:
				case Unknown:
				case Asserts:
				case Identifier:
					{
					setState(1086);
					identifierName();
					setState(1087);
					match(Dot);
					setState(1088);
					identifierName();
					}
					break;
				case StringLiteral:
					{
					setState(1090);
					match(StringLiteral);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1093);
				match(CloseBracket);
				}
				break;
			case NullLiteral:
			case UndefinedLiteral:
			case BooleanLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Typeof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case ReadOnly:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(1094);
				identifierName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GetAccessorContext extends ParserRuleContext {
		public GetterContext getter() {
			return getRuleContext(GetterContext.class,0);
		}
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public GetAccessorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_getAccessor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterGetAccessor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitGetAccessor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitGetAccessor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GetAccessorContext getAccessor() throws RecognitionException {
		GetAccessorContext _localctx = new GetAccessorContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_getAccessor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1097);
			getter();
			setState(1098);
			match(OpenParen);
			setState(1099);
			match(CloseParen);
			setState(1101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1100);
				colonSepTypeRef();
				}
			}

			setState(1104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(1103);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetAccessorContext extends ParserRuleContext {
		public SetterContext setter() {
			return getRuleContext(SetterContext.class,0);
		}
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public BindingPatternContext bindingPattern() {
			return getRuleContext(BindingPatternContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SetAccessorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setAccessor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterSetAccessor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitSetAccessor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitSetAccessor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetAccessorContext setAccessor() throws RecognitionException {
		SetAccessorContext _localctx = new SetAccessorContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_setAccessor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1106);
			setter();
			setState(1107);
			match(OpenParen);
			setState(1110);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(1108);
				match(Identifier);
				}
				break;
			case OpenBracket:
			case OpenBrace:
				{
				setState(1109);
				bindingPattern();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1112);
				colonSepTypeRef();
				}
			}

			setState(1115);
			match(CloseParen);
			setState(1117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(1116);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GeneratorMethodContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode Multiply() { return getToken(TypeScriptParser.Multiply, 0); }
		public GeneratorMethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_generatorMethod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterGeneratorMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitGeneratorMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitGeneratorMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GeneratorMethodContext generatorMethod() throws RecognitionException {
		GeneratorMethodContext _localctx = new GeneratorMethodContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_generatorMethod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(1119);
				match(Multiply);
				}
			}

			setState(1122);
			match(Identifier);
			setState(1123);
			parameterBlock();
			setState(1124);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public TerminalNode Comma() { return getToken(TypeScriptParser.Comma, 0); }
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1126);
			match(OpenParen);
			setState(1131);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << Ellipsis) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
				{
				setState(1127);
				argumentList();
				setState(1129);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(1128);
					match(Comma);
					}
				}

				}
			}

			setState(1133);
			match(CloseParen);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentListContext extends ParserRuleContext {
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_argumentList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1135);
			argument();
			setState(1140);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,150,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1136);
					match(Comma);
					setState(1137);
					argument();
					}
					} 
				}
				setState(1142);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,150,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentContext extends ParserRuleContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TerminalNode Ellipsis() { return getToken(TypeScriptParser.Ellipsis, 0); }
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1144);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(1143);
				match(Ellipsis);
				}
			}

			setState(1148);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,152,_ctx) ) {
			case 1:
				{
				setState(1146);
				singleExpression(0);
				}
				break;
			case 2:
				{
				setState(1147);
				match(Identifier);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportStatementContext extends ParserRuleContext {
		public TerminalNode Import() { return getToken(TypeScriptParser.Import, 0); }
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public ImportFromBlockContext importFromBlock() {
			return getRuleContext(ImportFromBlockContext.class,0);
		}
		public ImportAliasDeclarationContext importAliasDeclaration() {
			return getRuleContext(ImportAliasDeclarationContext.class,0);
		}
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public ImportStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterImportStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitImportStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitImportStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportStatementContext importStatement() throws RecognitionException {
		ImportStatementContext _localctx = new ImportStatementContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_importStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1150);
			match(Import);
			setState(1154);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,153,_ctx) ) {
			case 1:
				{
				setState(1151);
				importFromBlock();
				}
				break;
			case 2:
				{
				setState(1152);
				importAliasDeclaration();
				}
				break;
			case 3:
				{
				setState(1153);
				match(StringLiteral);
				}
				break;
			}
			setState(1156);
			eos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportFromBlockContext extends ParserRuleContext {
		public TerminalNode From() { return getToken(TypeScriptParser.From, 0); }
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public TerminalNode Multiply() { return getToken(TypeScriptParser.Multiply, 0); }
		public TerminalNode As() { return getToken(TypeScriptParser.As, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public MultipleImportElementsContext multipleImportElements() {
			return getRuleContext(MultipleImportElementsContext.class,0);
		}
		public TerminalNode TypeAlias() { return getToken(TypeScriptParser.TypeAlias, 0); }
		public ImportFromBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importFromBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterImportFromBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitImportFromBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitImportFromBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportFromBlockContext importFromBlock() throws RecognitionException {
		ImportFromBlockContext _localctx = new ImportFromBlockContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_importFromBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1159);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,154,_ctx) ) {
			case 1:
				{
				setState(1158);
				match(TypeAlias);
				}
				break;
			}
			setState(1166);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,155,_ctx) ) {
			case 1:
				{
				setState(1161);
				match(Multiply);
				setState(1162);
				match(As);
				setState(1163);
				identifierName();
				}
				break;
			case 2:
				{
				setState(1164);
				identifierName();
				}
				break;
			case 3:
				{
				setState(1165);
				multipleImportElements();
				}
				break;
			}
			setState(1168);
			match(From);
			setState(1169);
			match(StringLiteral);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleImportElementsContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public List<ImportedElementContext> importedElement() {
			return getRuleContexts(ImportedElementContext.class);
		}
		public ImportedElementContext importedElement(int i) {
			return getRuleContext(ImportedElementContext.class,i);
		}
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public MultipleImportElementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multipleImportElements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterMultipleImportElements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitMultipleImportElements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitMultipleImportElements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultipleImportElementsContext multipleImportElements() throws RecognitionException {
		MultipleImportElementsContext _localctx = new MultipleImportElementsContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_multipleImportElements);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (NullLiteral - 53)) | (1L << (UndefinedLiteral - 53)) | (1L << (BooleanLiteral - 53)) | (1L << (Break - 53)) | (1L << (Do - 53)) | (1L << (Instanceof - 53)) | (1L << (Typeof - 53)) | (1L << (Unique - 53)) | (1L << (Case - 53)) | (1L << (Else - 53)) | (1L << (New - 53)) | (1L << (Target - 53)) | (1L << (Var - 53)) | (1L << (Catch - 53)) | (1L << (Finally - 53)) | (1L << (Return - 53)) | (1L << (Void - 53)) | (1L << (Continue - 53)) | (1L << (For - 53)) | (1L << (Switch - 53)) | (1L << (While - 53)) | (1L << (Debugger - 53)) | (1L << (Function - 53)) | (1L << (This - 53)) | (1L << (With - 53)) | (1L << (Default - 53)) | (1L << (If - 53)) | (1L << (Throw - 53)) | (1L << (Delete - 53)) | (1L << (In - 53)) | (1L << (Try - 53)) | (1L << (As - 53)) | (1L << (From - 53)) | (1L << (ReadOnly - 53)) | (1L << (Async - 53)) | (1L << (Await - 53)) | (1L << (Class - 53)) | (1L << (Enum - 53)) | (1L << (Extends - 53)) | (1L << (Super - 53)) | (1L << (Const - 53)) | (1L << (Export - 53)) | (1L << (Import - 53)) | (1L << (Implements - 53)) | (1L << (Let - 53)) | (1L << (Private - 53)) | (1L << (Public - 53)) | (1L << (Interface - 53)) | (1L << (Package - 53)) | (1L << (Protected - 53)) | (1L << (Static - 53)) | (1L << (Yield - 53)) | (1L << (Any - 53)) | (1L << (Number - 53)) | (1L << (Boolean - 53)) | (1L << (String - 53)) | (1L << (Symbol - 53)) | (1L << (TypeAlias - 53)))) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & ((1L << (Get - 117)) | (1L << (Set - 117)) | (1L << (Constructor - 117)) | (1L << (Namespace - 117)) | (1L << (Require - 117)) | (1L << (Module - 117)) | (1L << (Declare - 117)) | (1L << (Abstract - 117)) | (1L << (Is - 117)) | (1L << (Infer - 117)) | (1L << (Never - 117)) | (1L << (Unknown - 117)) | (1L << (Asserts - 117)) | (1L << (Identifier - 117)))) != 0)) {
				{
				setState(1171);
				identifierName();
				setState(1172);
				match(Comma);
				}
			}

			setState(1176);
			match(OpenBrace);
			setState(1177);
			importedElement();
			setState(1182);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,157,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1178);
					match(Comma);
					setState(1179);
					importedElement();
					}
					} 
				}
				setState(1184);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,157,_ctx);
			}
			setState(1186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1185);
				match(Comma);
				}
			}

			setState(1188);
			match(CloseBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportedElementContext extends ParserRuleContext {
		public List<IdentifierNameContext> identifierName() {
			return getRuleContexts(IdentifierNameContext.class);
		}
		public IdentifierNameContext identifierName(int i) {
			return getRuleContext(IdentifierNameContext.class,i);
		}
		public TerminalNode As() { return getToken(TypeScriptParser.As, 0); }
		public ImportedElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importedElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterImportedElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitImportedElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitImportedElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportedElementContext importedElement() throws RecognitionException {
		ImportedElementContext _localctx = new ImportedElementContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_importedElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1190);
			identifierName();
			setState(1193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==As) {
				{
				setState(1191);
				match(As);
				setState(1192);
				identifierName();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportAliasDeclarationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public NamespaceNameContext namespaceName() {
			return getRuleContext(NamespaceNameContext.class,0);
		}
		public TerminalNode Require() { return getToken(TypeScriptParser.Require, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public ImportAliasDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importAliasDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterImportAliasDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitImportAliasDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitImportAliasDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportAliasDeclarationContext importAliasDeclaration() throws RecognitionException {
		ImportAliasDeclarationContext _localctx = new ImportAliasDeclarationContext(_ctx, getState());
		enterRule(_localctx, 216, RULE_importAliasDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1195);
			match(Identifier);
			setState(1196);
			match(Assign);
			setState(1202);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,160,_ctx) ) {
			case 1:
				{
				setState(1197);
				namespaceName();
				}
				break;
			case 2:
				{
				setState(1198);
				match(Require);
				setState(1199);
				match(OpenParen);
				setState(1200);
				match(StringLiteral);
				setState(1201);
				match(CloseParen);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExportStatementContext extends ParserRuleContext {
		public TerminalNode Export() { return getToken(TypeScriptParser.Export, 0); }
		public ExportStatementTailContext exportStatementTail() {
			return getRuleContext(ExportStatementTailContext.class,0);
		}
		public ExportStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exportStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExportStatementContext exportStatement() throws RecognitionException {
		ExportStatementContext _localctx = new ExportStatementContext(_ctx, getState());
		enterRule(_localctx, 218, RULE_exportStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1204);
			match(Export);
			setState(1205);
			exportStatementTail();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExportStatementTailContext extends ParserRuleContext {
		public ExportStatementTailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exportStatementTail; }
	 
		public ExportStatementTailContext() { }
		public void copyFrom(ExportStatementTailContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExportElementDirectlyContext extends ExportStatementTailContext {
		public DeclareStatementContext declareStatement() {
			return getRuleContext(DeclareStatementContext.class,0);
		}
		public TerminalNode Default() { return getToken(TypeScriptParser.Default, 0); }
		public ExportElementDirectlyContext(ExportStatementTailContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportElementDirectly(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportElementDirectly(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportElementDirectly(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExportElementAsDefaultContext extends ExportStatementTailContext {
		public TerminalNode Default() { return getToken(TypeScriptParser.Default, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public ExportElementAsDefaultContext(ExportStatementTailContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportElementAsDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportElementAsDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportElementAsDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExportEqualsContext extends ExportStatementTailContext {
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public NamespaceNameContext namespaceName() {
			return getRuleContext(NamespaceNameContext.class,0);
		}
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public ExportEqualsContext(ExportStatementTailContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportEquals(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportEquals(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportEquals(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExportAsNamespaceContext extends ExportStatementTailContext {
		public TerminalNode As() { return getToken(TypeScriptParser.As, 0); }
		public TerminalNode Namespace() { return getToken(TypeScriptParser.Namespace, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public ExportAsNamespaceContext(ExportStatementTailContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportAsNamespace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportAsNamespace(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportAsNamespace(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExportImportContext extends ExportStatementTailContext {
		public TerminalNode Import() { return getToken(TypeScriptParser.Import, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public NamespaceNameContext namespaceName() {
			return getRuleContext(NamespaceNameContext.class,0);
		}
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public ExportImportContext(ExportStatementTailContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportImport(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportImport(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportImport(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExportModuleContext extends ExportStatementTailContext {
		public TerminalNode Multiply() { return getToken(TypeScriptParser.Multiply, 0); }
		public TerminalNode From() { return getToken(TypeScriptParser.From, 0); }
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public TerminalNode As() { return getToken(TypeScriptParser.As, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public ExportModuleContext(ExportStatementTailContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportModule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportModule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportModule(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExportElementsContext extends ExportStatementTailContext {
		public MultipleExportElementsContext multipleExportElements() {
			return getRuleContext(MultipleExportElementsContext.class,0);
		}
		public TerminalNode From() { return getToken(TypeScriptParser.From, 0); }
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public ExportElementsContext(ExportStatementTailContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportElements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportElements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportElements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExportStatementTailContext exportStatementTail() throws RecognitionException {
		ExportStatementTailContext _localctx = new ExportStatementTailContext(_ctx, getState());
		enterRule(_localctx, 220, RULE_exportStatementTail);
		int _la;
		try {
			setState(1240);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,164,_ctx) ) {
			case 1:
				_localctx = new ExportElementDirectlyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1208);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Default) {
					{
					setState(1207);
					match(Default);
					}
				}

				setState(1210);
				declareStatement();
				}
				break;
			case 2:
				_localctx = new ExportElementAsDefaultContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1211);
				match(Default);
				setState(1212);
				identifierName();
				}
				break;
			case 3:
				_localctx = new ExportElementsContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1213);
				multipleExportElements();
				setState(1216);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==From) {
					{
					setState(1214);
					match(From);
					setState(1215);
					match(StringLiteral);
					}
				}

				}
				break;
			case 4:
				_localctx = new ExportModuleContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1218);
				match(Multiply);
				setState(1221);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==As) {
					{
					setState(1219);
					match(As);
					setState(1220);
					identifierName();
					}
				}

				setState(1223);
				match(From);
				setState(1224);
				match(StringLiteral);
				}
				break;
			case 5:
				_localctx = new ExportAsNamespaceContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1225);
				match(As);
				setState(1226);
				match(Namespace);
				setState(1227);
				identifierName();
				setState(1228);
				eos();
				}
				break;
			case 6:
				_localctx = new ExportEqualsContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1230);
				match(Assign);
				setState(1231);
				namespaceName();
				setState(1232);
				eos();
				}
				break;
			case 7:
				_localctx = new ExportImportContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(1234);
				match(Import);
				setState(1235);
				identifierName();
				setState(1236);
				match(Assign);
				setState(1237);
				namespaceName();
				setState(1238);
				eos();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleExportElementsContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public List<ImportedElementContext> importedElement() {
			return getRuleContexts(ImportedElementContext.class);
		}
		public ImportedElementContext importedElement(int i) {
			return getRuleContext(ImportedElementContext.class,i);
		}
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public MultipleExportElementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multipleExportElements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterMultipleExportElements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitMultipleExportElements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitMultipleExportElements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultipleExportElementsContext multipleExportElements() throws RecognitionException {
		MultipleExportElementsContext _localctx = new MultipleExportElementsContext(_ctx, getState());
		enterRule(_localctx, 222, RULE_multipleExportElements);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1242);
			match(OpenBrace);
			setState(1243);
			importedElement();
			setState(1248);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,165,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1244);
					match(Comma);
					setState(1245);
					importedElement();
					}
					} 
				}
				setState(1250);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,165,_ctx);
			}
			setState(1252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1251);
				match(Comma);
				}
			}

			setState(1254);
			match(CloseBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableStatementContext extends ParserRuleContext {
		public VarModifierContext varModifier() {
			return getRuleContext(VarModifierContext.class,0);
		}
		public BindingPatternBlockContext bindingPatternBlock() {
			return getRuleContext(BindingPatternBlockContext.class,0);
		}
		public VariableDeclarationListContext variableDeclarationList() {
			return getRuleContext(VariableDeclarationListContext.class,0);
		}
		public AccessibilityModifierContext accessibilityModifier() {
			return getRuleContext(AccessibilityModifierContext.class,0);
		}
		public TerminalNode ReadOnly() { return getToken(TypeScriptParser.ReadOnly, 0); }
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public VariableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterVariableStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitVariableStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitVariableStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableStatementContext variableStatement() throws RecognitionException {
		VariableStatementContext _localctx = new VariableStatementContext(_ctx, getState());
		enterRule(_localctx, 224, RULE_variableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 104)) & ~0x3f) == 0 && ((1L << (_la - 104)) & ((1L << (Private - 104)) | (1L << (Public - 104)) | (1L << (Protected - 104)))) != 0)) {
				{
				setState(1256);
				accessibilityModifier();
				}
			}

			setState(1260);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ReadOnly) {
				{
				setState(1259);
				match(ReadOnly);
				}
			}

			setState(1262);
			varModifier();
			setState(1265);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
			case OpenBrace:
				{
				setState(1263);
				bindingPatternBlock();
				}
				break;
			case NullLiteral:
			case UndefinedLiteral:
			case BooleanLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Typeof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case ReadOnly:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
			case Identifier:
				{
				setState(1264);
				variableDeclarationList();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1268);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,170,_ctx) ) {
			case 1:
				{
				setState(1267);
				match(SemiColon);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarModifierContext extends ParserRuleContext {
		public TerminalNode Var() { return getToken(TypeScriptParser.Var, 0); }
		public TerminalNode Let() { return getToken(TypeScriptParser.Let, 0); }
		public TerminalNode Const() { return getToken(TypeScriptParser.Const, 0); }
		public VarModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterVarModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitVarModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitVarModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarModifierContext varModifier() throws RecognitionException {
		VarModifierContext _localctx = new VarModifierContext(_ctx, getState());
		enterRule(_localctx, 226, RULE_varModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1270);
			_la = _input.LA(1);
			if ( !(((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Const - 71)) | (1L << (Let - 71)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BindingPatternBlockContext extends ParserRuleContext {
		public BindingPatternContext bindingPattern() {
			return getRuleContext(BindingPatternContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public BindingPatternBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bindingPatternBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterBindingPatternBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitBindingPatternBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitBindingPatternBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BindingPatternBlockContext bindingPatternBlock() throws RecognitionException {
		BindingPatternBlockContext _localctx = new BindingPatternBlockContext(_ctx, getState());
		enterRule(_localctx, 228, RULE_bindingPatternBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1272);
			bindingPattern();
			setState(1274);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,171,_ctx) ) {
			case 1:
				{
				setState(1273);
				colonSepTypeRef();
				}
				break;
			}
			setState(1277);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,172,_ctx) ) {
			case 1:
				{
				setState(1276);
				initializer();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationListContext extends ParserRuleContext {
		public List<VariableDeclarationContext> variableDeclaration() {
			return getRuleContexts(VariableDeclarationContext.class);
		}
		public VariableDeclarationContext variableDeclaration(int i) {
			return getRuleContext(VariableDeclarationContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public VariableDeclarationListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarationList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterVariableDeclarationList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitVariableDeclarationList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitVariableDeclarationList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationListContext variableDeclarationList() throws RecognitionException {
		VariableDeclarationListContext _localctx = new VariableDeclarationListContext(_ctx, getState());
		enterRule(_localctx, 230, RULE_variableDeclarationList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1279);
			variableDeclaration();
			setState(1284);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,173,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1280);
					match(Comma);
					setState(1281);
					variableDeclaration();
					}
					} 
				}
				setState(1286);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,173,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 232, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1287);
			identifierName();
			setState(1289);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,174,_ctx) ) {
			case 1:
				{
				setState(1288);
				colonSepTypeRef();
				}
				break;
			}
			setState(1296);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,176,_ctx) ) {
			case 1:
				{
				setState(1291);
				match(Assign);
				setState(1293);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LessThan) {
					{
					setState(1292);
					typeParameters();
					}
				}

				setState(1295);
				singleExpression(0);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwitchStatementContext extends ParserRuleContext {
		public TerminalNode Switch() { return getToken(TypeScriptParser.Switch, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public CaseBlockContext caseBlock() {
			return getRuleContext(CaseBlockContext.class,0);
		}
		public SwitchStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterSwitchStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitSwitchStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitSwitchStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchStatementContext switchStatement() throws RecognitionException {
		SwitchStatementContext _localctx = new SwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 234, RULE_switchStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1298);
			match(Switch);
			setState(1299);
			match(OpenParen);
			setState(1300);
			expressionSequence();
			setState(1301);
			match(CloseParen);
			setState(1302);
			caseBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseBlockContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public List<CaseClausesContext> caseClauses() {
			return getRuleContexts(CaseClausesContext.class);
		}
		public CaseClausesContext caseClauses(int i) {
			return getRuleContext(CaseClausesContext.class,i);
		}
		public DefaultClauseContext defaultClause() {
			return getRuleContext(DefaultClauseContext.class,0);
		}
		public CaseBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterCaseBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitCaseBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitCaseBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseBlockContext caseBlock() throws RecognitionException {
		CaseBlockContext _localctx = new CaseBlockContext(_ctx, getState());
		enterRule(_localctx, 236, RULE_caseBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1304);
			match(OpenBrace);
			setState(1306);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Case) {
				{
				setState(1305);
				caseClauses();
				}
			}

			setState(1312);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Default) {
				{
				setState(1308);
				defaultClause();
				setState(1310);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Case) {
					{
					setState(1309);
					caseClauses();
					}
				}

				}
			}

			setState(1314);
			match(CloseBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseClausesContext extends ParserRuleContext {
		public List<CaseClauseContext> caseClause() {
			return getRuleContexts(CaseClauseContext.class);
		}
		public CaseClauseContext caseClause(int i) {
			return getRuleContext(CaseClauseContext.class,i);
		}
		public CaseClausesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseClauses; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterCaseClauses(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitCaseClauses(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitCaseClauses(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseClausesContext caseClauses() throws RecognitionException {
		CaseClausesContext _localctx = new CaseClausesContext(_ctx, getState());
		enterRule(_localctx, 238, RULE_caseClauses);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1317); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1316);
				caseClause();
				}
				}
				setState(1319); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Case );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseClauseContext extends ParserRuleContext {
		public TerminalNode Case() { return getToken(TypeScriptParser.Case, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public CaseClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterCaseClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitCaseClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitCaseClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseClauseContext caseClause() throws RecognitionException {
		CaseClauseContext _localctx = new CaseClauseContext(_ctx, getState());
		enterRule(_localctx, 240, RULE_caseClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1321);
			match(Case);
			setState(1322);
			expressionSequence();
			setState(1323);
			match(Colon);
			setState(1325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Return - 71)) | (1L << (Continue - 71)) | (1L << (For - 71)) | (1L << (Switch - 71)) | (1L << (While - 71)) | (1L << (Debugger - 71)) | (1L << (Function - 71)) | (1L << (With - 71)) | (1L << (If - 71)) | (1L << (Throw - 71)) | (1L << (Try - 71)) | (1L << (ReadOnly - 71)) | (1L << (Class - 71)) | (1L << (Enum - 71)) | (1L << (Const - 71)) | (1L << (Export - 71)) | (1L << (Import - 71)) | (1L << (Let - 71)) | (1L << (Private - 71)) | (1L << (Public - 71)) | (1L << (Interface - 71)) | (1L << (Protected - 71)) | (1L << (TypeAlias - 71)) | (1L << (Namespace - 71)) | (1L << (Module - 71)) | (1L << (Declare - 71)) | (1L << (Abstract - 71)) | (1L << (At - 71)) | (1L << (Identifier - 71)))) != 0)) {
				{
				setState(1324);
				statementList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefaultClauseContext extends ParserRuleContext {
		public TerminalNode Default() { return getToken(TypeScriptParser.Default, 0); }
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public DefaultClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDefaultClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDefaultClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDefaultClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultClauseContext defaultClause() throws RecognitionException {
		DefaultClauseContext _localctx = new DefaultClauseContext(_ctx, getState());
		enterRule(_localctx, 242, RULE_defaultClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1327);
			match(Default);
			setState(1328);
			match(Colon);
			setState(1330);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Return - 71)) | (1L << (Continue - 71)) | (1L << (For - 71)) | (1L << (Switch - 71)) | (1L << (While - 71)) | (1L << (Debugger - 71)) | (1L << (Function - 71)) | (1L << (With - 71)) | (1L << (If - 71)) | (1L << (Throw - 71)) | (1L << (Try - 71)) | (1L << (ReadOnly - 71)) | (1L << (Class - 71)) | (1L << (Enum - 71)) | (1L << (Const - 71)) | (1L << (Export - 71)) | (1L << (Import - 71)) | (1L << (Let - 71)) | (1L << (Private - 71)) | (1L << (Public - 71)) | (1L << (Interface - 71)) | (1L << (Protected - 71)) | (1L << (TypeAlias - 71)) | (1L << (Namespace - 71)) | (1L << (Module - 71)) | (1L << (Declare - 71)) | (1L << (Abstract - 71)) | (1L << (At - 71)) | (1L << (Identifier - 71)))) != 0)) {
				{
				setState(1329);
				statementList();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TryStatementContext extends ParserRuleContext {
		public TerminalNode Try() { return getToken(TypeScriptParser.Try, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public CatchProductionContext catchProduction() {
			return getRuleContext(CatchProductionContext.class,0);
		}
		public FinallyProductionContext finallyProduction() {
			return getRuleContext(FinallyProductionContext.class,0);
		}
		public TryStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tryStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTryStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTryStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTryStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TryStatementContext tryStatement() throws RecognitionException {
		TryStatementContext _localctx = new TryStatementContext(_ctx, getState());
		enterRule(_localctx, 244, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1332);
			match(Try);
			setState(1333);
			block();
			setState(1339);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Catch:
				{
				setState(1334);
				catchProduction();
				setState(1336);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Finally) {
					{
					setState(1335);
					finallyProduction();
					}
				}

				}
				break;
			case Finally:
				{
				setState(1338);
				finallyProduction();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CatchProductionContext extends ParserRuleContext {
		public TerminalNode Catch() { return getToken(TypeScriptParser.Catch, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public CatchProductionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchProduction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterCatchProduction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitCatchProduction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitCatchProduction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchProductionContext catchProduction() throws RecognitionException {
		CatchProductionContext _localctx = new CatchProductionContext(_ctx, getState());
		enterRule(_localctx, 246, RULE_catchProduction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1341);
			match(Catch);
			setState(1342);
			match(OpenParen);
			setState(1343);
			match(Identifier);
			setState(1344);
			match(CloseParen);
			setState(1345);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FinallyProductionContext extends ParserRuleContext {
		public TerminalNode Finally() { return getToken(TypeScriptParser.Finally, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FinallyProductionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_finallyProduction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterFinallyProduction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitFinallyProduction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitFinallyProduction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FinallyProductionContext finallyProduction() throws RecognitionException {
		FinallyProductionContext _localctx = new FinallyProductionContext(_ctx, getState());
		enterRule(_localctx, 248, RULE_finallyProduction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1347);
			match(Finally);
			setState(1348);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EmptyStatementContext extends ParserRuleContext {
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public EmptyStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterEmptyStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitEmptyStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitEmptyStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyStatementContext emptyStatement() throws RecognitionException {
		EmptyStatementContext _localctx = new EmptyStatementContext(_ctx, getState());
		enterRule(_localctx, 250, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1350);
			match(SemiColon);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode If() { return getToken(TypeScriptParser.If, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode Else() { return getToken(TypeScriptParser.Else, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 252, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1352);
			match(If);
			setState(1353);
			match(OpenParen);
			setState(1354);
			expressionSequence();
			setState(1355);
			match(CloseParen);
			setState(1356);
			statement();
			setState(1359);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,185,_ctx) ) {
			case 1:
				{
				setState(1357);
				match(Else);
				setState(1358);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IterationStatementContext extends ParserRuleContext {
		public IterationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iterationStatement; }
	 
		public IterationStatementContext() { }
		public void copyFrom(IterationStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DoStatementContext extends IterationStatementContext {
		public TerminalNode Do() { return getToken(TypeScriptParser.Do, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode While() { return getToken(TypeScriptParser.While, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public DoStatementContext(IterationStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDoStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDoStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDoStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForVarStatementContext extends IterationStatementContext {
		public TerminalNode For() { return getToken(TypeScriptParser.For, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public VarModifierContext varModifier() {
			return getRuleContext(VarModifierContext.class,0);
		}
		public VariableDeclarationListContext variableDeclarationList() {
			return getRuleContext(VariableDeclarationListContext.class,0);
		}
		public List<TerminalNode> SemiColon() { return getTokens(TypeScriptParser.SemiColon); }
		public TerminalNode SemiColon(int i) {
			return getToken(TypeScriptParser.SemiColon, i);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionSequenceContext> expressionSequence() {
			return getRuleContexts(ExpressionSequenceContext.class);
		}
		public ExpressionSequenceContext expressionSequence(int i) {
			return getRuleContext(ExpressionSequenceContext.class,i);
		}
		public ForVarStatementContext(IterationStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterForVarStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitForVarStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitForVarStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForVarInStatementContext extends IterationStatementContext {
		public TerminalNode For() { return getToken(TypeScriptParser.For, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public VarModifierContext varModifier() {
			return getRuleContext(VarModifierContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode In() { return getToken(TypeScriptParser.In, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public ForVarInStatementContext(IterationStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterForVarInStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitForVarInStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitForVarInStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileStatementContext extends IterationStatementContext {
		public TerminalNode While() { return getToken(TypeScriptParser.While, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(IterationStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForStatementContext extends IterationStatementContext {
		public TerminalNode For() { return getToken(TypeScriptParser.For, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public List<TerminalNode> SemiColon() { return getTokens(TypeScriptParser.SemiColon); }
		public TerminalNode SemiColon(int i) {
			return getToken(TypeScriptParser.SemiColon, i);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionSequenceContext> expressionSequence() {
			return getRuleContexts(ExpressionSequenceContext.class);
		}
		public ExpressionSequenceContext expressionSequence(int i) {
			return getRuleContext(ExpressionSequenceContext.class,i);
		}
		public ForStatementContext(IterationStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForInStatementContext extends IterationStatementContext {
		public TerminalNode For() { return getToken(TypeScriptParser.For, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode In() { return getToken(TypeScriptParser.In, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public ForInStatementContext(IterationStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterForInStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitForInStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitForInStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IterationStatementContext iterationStatement() throws RecognitionException {
		IterationStatementContext _localctx = new IterationStatementContext(_ctx, getState());
		enterRule(_localctx, 254, RULE_iterationStatement);
		int _la;
		try {
			setState(1430);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,193,_ctx) ) {
			case 1:
				_localctx = new DoStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1361);
				match(Do);
				setState(1362);
				statement();
				setState(1363);
				match(While);
				setState(1364);
				match(OpenParen);
				setState(1365);
				expressionSequence();
				setState(1366);
				match(CloseParen);
				setState(1367);
				eos();
				}
				break;
			case 2:
				_localctx = new WhileStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1369);
				match(While);
				setState(1370);
				match(OpenParen);
				setState(1371);
				expressionSequence();
				setState(1372);
				match(CloseParen);
				setState(1373);
				statement();
				}
				break;
			case 3:
				_localctx = new ForStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1375);
				match(For);
				setState(1376);
				match(OpenParen);
				setState(1378);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1377);
					expressionSequence();
					}
				}

				setState(1380);
				match(SemiColon);
				setState(1382);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1381);
					expressionSequence();
					}
				}

				setState(1384);
				match(SemiColon);
				setState(1386);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1385);
					expressionSequence();
					}
				}

				setState(1388);
				match(CloseParen);
				setState(1389);
				statement();
				}
				break;
			case 4:
				_localctx = new ForVarStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1390);
				match(For);
				setState(1391);
				match(OpenParen);
				setState(1392);
				varModifier();
				setState(1393);
				variableDeclarationList();
				setState(1394);
				match(SemiColon);
				setState(1396);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1395);
					expressionSequence();
					}
				}

				setState(1398);
				match(SemiColon);
				setState(1400);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1399);
					expressionSequence();
					}
				}

				setState(1402);
				match(CloseParen);
				setState(1403);
				statement();
				}
				break;
			case 5:
				_localctx = new ForInStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1405);
				match(For);
				setState(1406);
				match(OpenParen);
				setState(1407);
				singleExpression(0);
				setState(1411);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case In:
					{
					setState(1408);
					match(In);
					}
					break;
				case Identifier:
					{
					setState(1409);
					match(Identifier);
					setState(1410);
					if (!(this.p("of"))) throw new FailedPredicateException(this, "this.p(\"of\")");
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1413);
				expressionSequence();
				setState(1414);
				match(CloseParen);
				setState(1415);
				statement();
				}
				break;
			case 6:
				_localctx = new ForVarInStatementContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1417);
				match(For);
				setState(1418);
				match(OpenParen);
				setState(1419);
				varModifier();
				setState(1420);
				variableDeclaration();
				setState(1424);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case In:
					{
					setState(1421);
					match(In);
					}
					break;
				case Identifier:
					{
					setState(1422);
					match(Identifier);
					setState(1423);
					if (!(this.p("of"))) throw new FailedPredicateException(this, "this.p(\"of\")");
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1426);
				expressionSequence();
				setState(1427);
				match(CloseParen);
				setState(1428);
				statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode Continue() { return getToken(TypeScriptParser.Continue, 0); }
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 256, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1432);
			match(Continue);
			setState(1435);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,194,_ctx) ) {
			case 1:
				{
				setState(1433);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1434);
				match(Identifier);
				}
				break;
			}
			setState(1437);
			eos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode Break() { return getToken(TypeScriptParser.Break, 0); }
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 258, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1439);
			match(Break);
			setState(1442);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,195,_ctx) ) {
			case 1:
				{
				setState(1440);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1441);
				match(Identifier);
				}
				break;
			}
			setState(1444);
			eos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode Return() { return getToken(TypeScriptParser.Return, 0); }
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 260, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1446);
			match(Return);
			setState(1449);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,196,_ctx) ) {
			case 1:
				{
				setState(1447);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1448);
				expressionSequence();
				}
				break;
			}
			setState(1451);
			eos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WithStatementContext extends ParserRuleContext {
		public TerminalNode With() { return getToken(TypeScriptParser.With, 0); }
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WithStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterWithStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitWithStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitWithStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithStatementContext withStatement() throws RecognitionException {
		WithStatementContext _localctx = new WithStatementContext(_ctx, getState());
		enterRule(_localctx, 262, RULE_withStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1453);
			match(With);
			setState(1454);
			match(OpenParen);
			setState(1455);
			expressionSequence();
			setState(1456);
			match(CloseParen);
			setState(1457);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelledStatementContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public LabelledStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelledStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterLabelledStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitLabelledStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitLabelledStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelledStatementContext labelledStatement() throws RecognitionException {
		LabelledStatementContext _localctx = new LabelledStatementContext(_ctx, getState());
		enterRule(_localctx, 264, RULE_labelledStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1459);
			match(Identifier);
			setState(1460);
			match(Colon);
			setState(1461);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ThrowStatementContext extends ParserRuleContext {
		public TerminalNode Throw() { return getToken(TypeScriptParser.Throw, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public ThrowStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throwStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterThrowStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitThrowStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitThrowStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThrowStatementContext throwStatement() throws RecognitionException {
		ThrowStatementContext _localctx = new ThrowStatementContext(_ctx, getState());
		enterRule(_localctx, 266, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1463);
			match(Throw);
			setState(1464);
			if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
			setState(1465);
			expressionSequence();
			setState(1466);
			eos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DebuggerStatementContext extends ParserRuleContext {
		public TerminalNode Debugger() { return getToken(TypeScriptParser.Debugger, 0); }
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public DebuggerStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_debuggerStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDebuggerStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDebuggerStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDebuggerStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DebuggerStatementContext debuggerStatement() throws RecognitionException {
		DebuggerStatementContext _localctx = new DebuggerStatementContext(_ctx, getState());
		enterRule(_localctx, 268, RULE_debuggerStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1468);
			match(Debugger);
			setState(1469);
			eos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 270, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1471);
			if (!(this.notOpenBraceAndNotFunction())) throw new FailedPredicateException(this, "this.notOpenBraceAndNotFunction()");
			setState(1472);
			expressionSequence();
			setState(1473);
			eos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionSequenceContext extends ParserRuleContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public ExpressionSequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionSequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExpressionSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExpressionSequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExpressionSequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionSequenceContext expressionSequence() throws RecognitionException {
		ExpressionSequenceContext _localctx = new ExpressionSequenceContext(_ctx, getState());
		enterRule(_localctx, 272, RULE_expressionSequence);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1475);
			singleExpression(0);
			setState(1480);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,197,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1476);
					match(Comma);
					setState(1477);
					singleExpression(0);
					}
					} 
				}
				setState(1482);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,197,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleExpressionContext extends ParserRuleContext {
		public SingleExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleExpression; }
	 
		public SingleExpressionContext() { }
		public void copyFrom(SingleExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TemplateStringExpressionContext extends SingleExpressionContext {
		public TemplateStringLiteralContext templateStringLiteral() {
			return getRuleContext(TemplateStringLiteralContext.class,0);
		}
		public TemplateStringExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTemplateStringExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTemplateStringExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTemplateStringExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TernaryExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public TerminalNode Colon() { return getToken(TypeScriptParser.Colon, 0); }
		public TernaryExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTernaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTernaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTernaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ObjectLiteralExpressionContext extends SingleExpressionContext {
		public ObjectLiteralContext objectLiteral() {
			return getRuleContext(ObjectLiteralContext.class,0);
		}
		public ObjectLiteralExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterObjectLiteralExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitObjectLiteralExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitObjectLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryExpressionContext extends SingleExpressionContext {
		public UnaryOperatorContext unaryOperator() {
			return getRuleContext(UnaryOperatorContext.class,0);
		}
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public UnaryExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionExpressionLContext extends SingleExpressionContext {
		public FunctionExpressionContext functionExpression() {
			return getRuleContext(FunctionExpressionContext.class,0);
		}
		public FunctionExpressionLContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterFunctionExpressionL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitFunctionExpressionL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitFunctionExpressionL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ThisExpressionContext extends SingleExpressionContext {
		public TerminalNode This() { return getToken(TypeScriptParser.This, 0); }
		public ThisExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterThisExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitThisExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitThisExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AwaitExpressionContext extends SingleExpressionContext {
		public TerminalNode Await() { return getToken(TypeScriptParser.Await, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public AwaitExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterAwaitExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitAwaitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitAwaitExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode Or() { return getToken(TypeScriptParser.Or, 0); }
		public TerminalNode And() { return getToken(TypeScriptParser.And, 0); }
		public LogicalExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterLogicalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitLogicalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitLogicalExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignmentExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public AssignmentOperatorContext assignmentOperator() {
			return getRuleContext(AssignmentOperatorContext.class,0);
		}
		public AssignmentExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterAssignmentExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitAssignmentExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitAssignmentExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode BitAnd() { return getToken(TypeScriptParser.BitAnd, 0); }
		public TerminalNode BitXOr() { return getToken(TypeScriptParser.BitXOr, 0); }
		public TerminalNode BitOr() { return getToken(TypeScriptParser.BitOr, 0); }
		public BinaryExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterBinaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitBinaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitBinaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualityExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode Equals_() { return getToken(TypeScriptParser.Equals_, 0); }
		public TerminalNode NotEquals() { return getToken(TypeScriptParser.NotEquals, 0); }
		public TerminalNode IdentityEquals() { return getToken(TypeScriptParser.IdentityEquals, 0); }
		public TerminalNode IdentityNotEquals() { return getToken(TypeScriptParser.IdentityNotEquals, 0); }
		public EqualityExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitEqualityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CastAsExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode As() { return getToken(TypeScriptParser.As, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public CastAsExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterCastAsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitCastAsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitCastAsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SuperExpressionContext extends SingleExpressionContext {
		public TerminalNode Super() { return getToken(TypeScriptParser.Super, 0); }
		public SuperExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterSuperExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitSuperExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitSuperExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiplicativeExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode Multiply() { return getToken(TypeScriptParser.Multiply, 0); }
		public TerminalNode Divide() { return getToken(TypeScriptParser.Divide, 0); }
		public TerminalNode Modulus() { return getToken(TypeScriptParser.Modulus, 0); }
		public MultiplicativeExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterMultiplicativeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitMultiplicativeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitMultiplicativeExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CallExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode Dot() { return getToken(TypeScriptParser.Dot, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public CallExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BitShiftExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode LeftShiftArithmetic() { return getToken(TypeScriptParser.LeftShiftArithmetic, 0); }
		public List<TerminalNode> MoreThan() { return getTokens(TypeScriptParser.MoreThan); }
		public TerminalNode MoreThan(int i) {
			return getToken(TypeScriptParser.MoreThan, i);
		}
		public BitShiftExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterBitShiftExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitBitShiftExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitBitShiftExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenthesizedExpressionContext extends SingleExpressionContext {
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public ParenthesizedExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterParenthesizedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitParenthesizedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitParenthesizedExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CoalesceExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public List<TerminalNode> QuestionMark() { return getTokens(TypeScriptParser.QuestionMark); }
		public TerminalNode QuestionMark(int i) {
			return getToken(TypeScriptParser.QuestionMark, i);
		}
		public CoalesceExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterCoalesceExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitCoalesceExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitCoalesceExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RelationalExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public RelationalOperatorContext relationalOperator() {
			return getRuleContext(RelationalOperatorContext.class,0);
		}
		public RelationalExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterRelationalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitRelationalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitRelationalExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AdditiveExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode Plus() { return getToken(TypeScriptParser.Plus, 0); }
		public TerminalNode Minus() { return getToken(TypeScriptParser.Minus, 0); }
		public AdditiveExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitAdditiveExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitAdditiveExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IndexedAccessExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public TerminalNode OpenBracket() { return getToken(TypeScriptParser.OpenBracket, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(TypeScriptParser.CloseBracket, 0); }
		public TerminalNode Dot() { return getToken(TypeScriptParser.Dot, 0); }
		public IndexedAccessExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIndexedAccessExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIndexedAccessExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIndexedAccessExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class YieldExpressionContext extends SingleExpressionContext {
		public TerminalNode Yield() { return getToken(TypeScriptParser.Yield, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public YieldExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterYieldExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitYieldExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitYieldExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralExpressionContext extends SingleExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterLiteralExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitLiteralExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayLiteralExpressionContext extends SingleExpressionContext {
		public ArrayLiteralContext arrayLiteral() {
			return getRuleContext(ArrayLiteralContext.class,0);
		}
		public ArrayLiteralExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrayLiteralExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrayLiteralExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrayLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ClassExpressionLContext extends SingleExpressionContext {
		public ClassExpressionContext classExpression() {
			return getRuleContext(ClassExpressionContext.class,0);
		}
		public ClassExpressionLContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassExpressionL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassExpressionL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassExpressionL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NewExpressionLContext extends SingleExpressionContext {
		public NewExpressionContext newExpression() {
			return getRuleContext(NewExpressionContext.class,0);
		}
		public NewExpressionLContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterNewExpressionL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitNewExpressionL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitNewExpressionL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentifierExpressionContext extends SingleExpressionContext {
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public IdentifierExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIdentifierExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIdentifierExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIdentifierExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PropertyAccessExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public TerminalNode Dot() { return getToken(TypeScriptParser.Dot, 0); }
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public PropertyAccessExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertyAccessExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertyAccessExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertyAccessExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PostfixExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode PlusPlus() { return getToken(TypeScriptParser.PlusPlus, 0); }
		public TerminalNode MinusMinus() { return getToken(TypeScriptParser.MinusMinus, 0); }
		public PostfixExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPostfixExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPostfixExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPostfixExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrowFunctionExpressionLContext extends SingleExpressionContext {
		public ArrowFunctionExpressionContext arrowFunctionExpression() {
			return getRuleContext(ArrowFunctionExpressionContext.class,0);
		}
		public ArrowFunctionExpressionLContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrowFunctionExpressionL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrowFunctionExpressionL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrowFunctionExpressionL(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleExpressionContext singleExpression() throws RecognitionException {
		return singleExpression(0);
	}

	private SingleExpressionContext singleExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SingleExpressionContext _localctx = new SingleExpressionContext(_ctx, _parentState);
		SingleExpressionContext _prevctx = _localctx;
		int _startState = 274;
		enterRecursionRule(_localctx, 274, RULE_singleExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1509);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,199,_ctx) ) {
			case 1:
				{
				_localctx = new ThisExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1484);
				match(This);
				}
				break;
			case 2:
				{
				_localctx = new SuperExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1485);
				match(Super);
				}
				break;
			case 3:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1486);
				literal();
				}
				break;
			case 4:
				{
				_localctx = new ArrayLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1487);
				arrayLiteral();
				}
				break;
			case 5:
				{
				_localctx = new ObjectLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1488);
				objectLiteral();
				}
				break;
			case 6:
				{
				_localctx = new TemplateStringExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1489);
				templateStringLiteral();
				}
				break;
			case 7:
				{
				_localctx = new YieldExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1490);
				match(Yield);
				setState(1493);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,198,_ctx) ) {
				case 1:
					{
					setState(1491);
					if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
					setState(1492);
					expressionSequence();
					}
					break;
				}
				}
				break;
			case 8:
				{
				_localctx = new AwaitExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1495);
				match(Await);
				setState(1496);
				singleExpression(23);
				}
				break;
			case 9:
				{
				_localctx = new UnaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1497);
				unaryOperator();
				setState(1498);
				singleExpression(12);
				}
				break;
			case 10:
				{
				_localctx = new NewExpressionLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1500);
				newExpression();
				}
				break;
			case 11:
				{
				_localctx = new FunctionExpressionLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1501);
				functionExpression();
				}
				break;
			case 12:
				{
				_localctx = new ArrowFunctionExpressionLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1502);
				arrowFunctionExpression();
				}
				break;
			case 13:
				{
				_localctx = new ClassExpressionLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1503);
				classExpression();
				}
				break;
			case 14:
				{
				_localctx = new IdentifierExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1504);
				identifierName();
				}
				break;
			case 15:
				{
				_localctx = new ParenthesizedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1505);
				match(OpenParen);
				setState(1506);
				expressionSequence();
				setState(1507);
				match(CloseParen);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1589);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,206,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1587);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,205,_ctx) ) {
					case 1:
						{
						_localctx = new AssignmentExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1511);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(1512);
						assignmentOperator();
						setState(1513);
						singleExpression(23);
						}
						break;
					case 2:
						{
						_localctx = new TernaryExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1515);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(1516);
						match(QuestionMark);
						setState(1517);
						singleExpression(0);
						setState(1518);
						match(Colon);
						setState(1519);
						singleExpression(22);
						}
						break;
					case 3:
						{
						_localctx = new CoalesceExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1521);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(1522);
						match(QuestionMark);
						setState(1523);
						match(QuestionMark);
						setState(1524);
						singleExpression(21);
						}
						break;
					case 4:
						{
						_localctx = new LogicalExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1525);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(1526);
						_la = _input.LA(1);
						if ( !(_la==And || _la==Or) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1527);
						singleExpression(20);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1528);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(1529);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BitAnd) | (1L << BitXOr) | (1L << BitOr))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1530);
						singleExpression(19);
						}
						break;
					case 6:
						{
						_localctx = new EqualityExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1531);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(1532);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Equals_) | (1L << NotEquals) | (1L << IdentityEquals) | (1L << IdentityNotEquals))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1533);
						singleExpression(18);
						}
						break;
					case 7:
						{
						_localctx = new RelationalExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1534);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(1535);
						relationalOperator();
						setState(1536);
						singleExpression(17);
						}
						break;
					case 8:
						{
						_localctx = new BitShiftExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1538);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(1545);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,200,_ctx) ) {
						case 1:
							{
							setState(1539);
							match(LeftShiftArithmetic);
							}
							break;
						case 2:
							{
							setState(1540);
							match(MoreThan);
							setState(1541);
							match(MoreThan);
							}
							break;
						case 3:
							{
							setState(1542);
							match(MoreThan);
							setState(1543);
							match(MoreThan);
							setState(1544);
							match(MoreThan);
							}
							break;
						}
						setState(1547);
						singleExpression(16);
						}
						break;
					case 9:
						{
						_localctx = new AdditiveExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1548);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(1549);
						_la = _input.LA(1);
						if ( !(_la==Plus || _la==Minus) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1550);
						singleExpression(15);
						}
						break;
					case 10:
						{
						_localctx = new MultiplicativeExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1551);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(1552);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Multiply) | (1L << Divide) | (1L << Modulus))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1553);
						singleExpression(14);
						}
						break;
					case 11:
						{
						_localctx = new CastAsExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1554);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(1555);
						match(As);
						setState(1556);
						typeRef();
						}
						break;
					case 12:
						{
						_localctx = new PostfixExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1557);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(1558);
						if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
						setState(1559);
						_la = _input.LA(1);
						if ( !(_la==PlusPlus || _la==MinusMinus) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					case 13:
						{
						_localctx = new IndexedAccessExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1560);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(1561);
						match(QuestionMark);
						setState(1563);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==Dot) {
							{
							setState(1562);
							match(Dot);
							}
						}

						setState(1565);
						match(OpenBracket);
						setState(1566);
						expressionSequence();
						setState(1567);
						match(CloseBracket);
						}
						break;
					case 14:
						{
						_localctx = new CallExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1569);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(1570);
						match(QuestionMark);
						setState(1572);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==Dot) {
							{
							setState(1571);
							match(Dot);
							}
						}

						setState(1574);
						identifierName();
						setState(1576);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LessThan) {
							{
							setState(1575);
							typeArguments();
							}
						}

						setState(1578);
						arguments();
						}
						break;
					case 15:
						{
						_localctx = new PropertyAccessExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1580);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(1584);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
						case Dot:
							{
							setState(1581);
							match(Dot);
							}
							break;
						case QuestionMark:
							{
							setState(1582);
							match(QuestionMark);
							setState(1583);
							match(Dot);
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(1586);
						identifierName();
						}
						break;
					}
					} 
				}
				setState(1591);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,206,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FunctionExpressionContext extends ParserRuleContext {
		public TerminalNode Function() { return getToken(TypeScriptParser.Function, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode Multiply() { return getToken(TypeScriptParser.Multiply, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public FunctionExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterFunctionExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitFunctionExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitFunctionExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionExpressionContext functionExpression() throws RecognitionException {
		FunctionExpressionContext _localctx = new FunctionExpressionContext(_ctx, getState());
		enterRule(_localctx, 276, RULE_functionExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1592);
			match(Function);
			setState(1594);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(1593);
				match(Multiply);
				}
			}

			setState(1597);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1596);
				match(Identifier);
				}
			}

			setState(1599);
			parameterBlock();
			setState(1601);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1600);
				colonSepTypeRef();
				}
			}

			setState(1603);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrowFunctionExpressionContext extends ParserRuleContext {
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(TypeScriptParser.ARROW, 0); }
		public ArrowFunctionBodyContext arrowFunctionBody() {
			return getRuleContext(ArrowFunctionBodyContext.class,0);
		}
		public TerminalNode Async() { return getToken(TypeScriptParser.Async, 0); }
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public ArrowFunctionExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrowFunctionExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrowFunctionExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrowFunctionExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrowFunctionExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrowFunctionExpressionContext arrowFunctionExpression() throws RecognitionException {
		ArrowFunctionExpressionContext _localctx = new ArrowFunctionExpressionContext(_ctx, getState());
		enterRule(_localctx, 278, RULE_arrowFunctionExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1606);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Async) {
				{
				setState(1605);
				match(Async);
				}
			}

			setState(1608);
			parameterBlock();
			setState(1610);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1609);
				colonSepTypeRef();
				}
			}

			setState(1612);
			match(ARROW);
			setState(1613);
			arrowFunctionBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrowFunctionBodyContext extends ParserRuleContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ArrowFunctionBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrowFunctionBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrowFunctionBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrowFunctionBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrowFunctionBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrowFunctionBodyContext arrowFunctionBody() throws RecognitionException {
		ArrowFunctionBodyContext _localctx = new ArrowFunctionBodyContext(_ctx, getState());
		enterRule(_localctx, 280, RULE_arrowFunctionBody);
		try {
			setState(1617);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,212,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1615);
				singleExpression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1616);
				block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassExpressionContext extends ParserRuleContext {
		public TerminalNode Class() { return getToken(TypeScriptParser.Class, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public ClassExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassExpressionContext classExpression() throws RecognitionException {
		ClassExpressionContext _localctx = new ClassExpressionContext(_ctx, getState());
		enterRule(_localctx, 282, RULE_classExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1619);
			match(Class);
			setState(1621);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1620);
				match(Identifier);
				}
			}

			setState(1623);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentOperatorContext extends ParserRuleContext {
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public TerminalNode MultiplyAssign() { return getToken(TypeScriptParser.MultiplyAssign, 0); }
		public TerminalNode DivideAssign() { return getToken(TypeScriptParser.DivideAssign, 0); }
		public TerminalNode ModulusAssign() { return getToken(TypeScriptParser.ModulusAssign, 0); }
		public TerminalNode PlusAssign() { return getToken(TypeScriptParser.PlusAssign, 0); }
		public TerminalNode Minus() { return getToken(TypeScriptParser.Minus, 0); }
		public TerminalNode LeftShiftArithmeticAssign() { return getToken(TypeScriptParser.LeftShiftArithmeticAssign, 0); }
		public List<TerminalNode> MoreThan() { return getTokens(TypeScriptParser.MoreThan); }
		public TerminalNode MoreThan(int i) {
			return getToken(TypeScriptParser.MoreThan, i);
		}
		public TerminalNode BitAndAssign() { return getToken(TypeScriptParser.BitAndAssign, 0); }
		public TerminalNode BitXorAssign() { return getToken(TypeScriptParser.BitXorAssign, 0); }
		public TerminalNode BitOrAssign() { return getToken(TypeScriptParser.BitOrAssign, 0); }
		public AssignmentOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterAssignmentOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitAssignmentOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitAssignmentOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentOperatorContext assignmentOperator() throws RecognitionException {
		AssignmentOperatorContext _localctx = new AssignmentOperatorContext(_ctx, getState());
		enterRule(_localctx, 284, RULE_assignmentOperator);
		int _la;
		try {
			setState(1642);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Assign:
				enterOuterAlt(_localctx, 1);
				{
				setState(1625);
				match(Assign);
				}
				break;
			case MultiplyAssign:
				enterOuterAlt(_localctx, 2);
				{
				setState(1626);
				match(MultiplyAssign);
				}
				break;
			case DivideAssign:
				enterOuterAlt(_localctx, 3);
				{
				setState(1627);
				match(DivideAssign);
				}
				break;
			case ModulusAssign:
				enterOuterAlt(_localctx, 4);
				{
				setState(1628);
				match(ModulusAssign);
				}
				break;
			case PlusAssign:
				enterOuterAlt(_localctx, 5);
				{
				setState(1629);
				match(PlusAssign);
				}
				break;
			case Minus:
				enterOuterAlt(_localctx, 6);
				{
				setState(1630);
				match(Minus);
				setState(1631);
				match(Assign);
				}
				break;
			case LeftShiftArithmeticAssign:
				enterOuterAlt(_localctx, 7);
				{
				setState(1632);
				match(LeftShiftArithmeticAssign);
				}
				break;
			case MoreThan:
				enterOuterAlt(_localctx, 8);
				{
				setState(1633);
				match(MoreThan);
				setState(1634);
				match(MoreThan);
				setState(1636);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MoreThan) {
					{
					setState(1635);
					match(MoreThan);
					}
				}

				setState(1638);
				match(Assign);
				}
				break;
			case BitAndAssign:
				enterOuterAlt(_localctx, 9);
				{
				setState(1639);
				match(BitAndAssign);
				}
				break;
			case BitXorAssign:
				enterOuterAlt(_localctx, 10);
				{
				setState(1640);
				match(BitXorAssign);
				}
				break;
			case BitOrAssign:
				enterOuterAlt(_localctx, 11);
				{
				setState(1641);
				match(BitOrAssign);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationalOperatorContext extends ParserRuleContext {
		public TerminalNode Instanceof() { return getToken(TypeScriptParser.Instanceof, 0); }
		public TerminalNode In() { return getToken(TypeScriptParser.In, 0); }
		public TerminalNode LessThan() { return getToken(TypeScriptParser.LessThan, 0); }
		public TerminalNode MoreThan() { return getToken(TypeScriptParser.MoreThan, 0); }
		public TerminalNode LessThanEquals() { return getToken(TypeScriptParser.LessThanEquals, 0); }
		public TerminalNode GreaterThanEquals() { return getToken(TypeScriptParser.GreaterThanEquals, 0); }
		public RelationalOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterRelationalOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitRelationalOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitRelationalOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalOperatorContext relationalOperator() throws RecognitionException {
		RelationalOperatorContext _localctx = new RelationalOperatorContext(_ctx, getState());
		enterRule(_localctx, 286, RULE_relationalOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1644);
			_la = _input.LA(1);
			if ( !(((((_la - 28)) & ~0x3f) == 0 && ((1L << (_la - 28)) & ((1L << (LessThan - 28)) | (1L << (MoreThan - 28)) | (1L << (LessThanEquals - 28)) | (1L << (GreaterThanEquals - 28)) | (1L << (Instanceof - 28)) | (1L << (In - 28)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryOperatorContext extends ParserRuleContext {
		public TerminalNode Delete() { return getToken(TypeScriptParser.Delete, 0); }
		public TerminalNode Void() { return getToken(TypeScriptParser.Void, 0); }
		public TerminalNode Typeof() { return getToken(TypeScriptParser.Typeof, 0); }
		public TerminalNode PlusPlus() { return getToken(TypeScriptParser.PlusPlus, 0); }
		public TerminalNode MinusMinus() { return getToken(TypeScriptParser.MinusMinus, 0); }
		public TerminalNode Plus() { return getToken(TypeScriptParser.Plus, 0); }
		public TerminalNode Minus() { return getToken(TypeScriptParser.Minus, 0); }
		public TerminalNode BitNot() { return getToken(TypeScriptParser.BitNot, 0); }
		public TerminalNode Not() { return getToken(TypeScriptParser.Not, 0); }
		public UnaryOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterUnaryOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitUnaryOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitUnaryOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperatorContext unaryOperator() throws RecognitionException {
		UnaryOperatorContext _localctx = new UnaryOperatorContext(_ctx, getState());
		enterRule(_localctx, 288, RULE_unaryOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1646);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Void - 64)) | (1L << (Delete - 64)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewExpressionContext extends ParserRuleContext {
		public TerminalNode New() { return getToken(TypeScriptParser.New, 0); }
		public TerminalNode Dot() { return getToken(TypeScriptParser.Dot, 0); }
		public TerminalNode Target() { return getToken(TypeScriptParser.Target, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public NewExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterNewExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitNewExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitNewExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewExpressionContext newExpression() throws RecognitionException {
		NewExpressionContext _localctx = new NewExpressionContext(_ctx, getState());
		enterRule(_localctx, 290, RULE_newExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1648);
			match(New);
			setState(1658);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Dot:
				{
				setState(1649);
				match(Dot);
				setState(1650);
				match(Target);
				}
				break;
			case RegularExpressionLiteral:
			case OpenBracket:
			case OpenParen:
			case OpenBrace:
			case PlusPlus:
			case MinusMinus:
			case Plus:
			case Minus:
			case BitNot:
			case Not:
			case NullLiteral:
			case UndefinedLiteral:
			case BooleanLiteral:
			case DecimalLiteral:
			case HexIntegerLiteral:
			case OctalIntegerLiteral:
			case OctalIntegerLiteral2:
			case BinaryIntegerLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Typeof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case ReadOnly:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
			case Identifier:
			case StringLiteral:
			case BackTick:
				{
				setState(1651);
				singleExpression(0);
				setState(1653);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,216,_ctx) ) {
				case 1:
					{
					setState(1652);
					typeArguments();
					}
					break;
				}
				setState(1656);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,217,_ctx) ) {
				case 1:
					{
					setState(1655);
					arguments();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GeneratorBlockContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public List<GeneratorDefinitionContext> generatorDefinition() {
			return getRuleContexts(GeneratorDefinitionContext.class);
		}
		public GeneratorDefinitionContext generatorDefinition(int i) {
			return getRuleContext(GeneratorDefinitionContext.class,i);
		}
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public GeneratorBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_generatorBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterGeneratorBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitGeneratorBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitGeneratorBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GeneratorBlockContext generatorBlock() throws RecognitionException {
		GeneratorBlockContext _localctx = new GeneratorBlockContext(_ctx, getState());
		enterRule(_localctx, 292, RULE_generatorBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1660);
			match(OpenBrace);
			setState(1661);
			generatorDefinition();
			setState(1666);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,219,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1662);
					match(Comma);
					setState(1663);
					generatorDefinition();
					}
					} 
				}
				setState(1668);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,219,_ctx);
			}
			setState(1670);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1669);
				match(Comma);
				}
			}

			setState(1672);
			match(CloseBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GeneratorDefinitionContext extends ParserRuleContext {
		public TerminalNode Multiply() { return getToken(TypeScriptParser.Multiply, 0); }
		public IteratorDefinitionContext iteratorDefinition() {
			return getRuleContext(IteratorDefinitionContext.class,0);
		}
		public GeneratorDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_generatorDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterGeneratorDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitGeneratorDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitGeneratorDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GeneratorDefinitionContext generatorDefinition() throws RecognitionException {
		GeneratorDefinitionContext _localctx = new GeneratorDefinitionContext(_ctx, getState());
		enterRule(_localctx, 294, RULE_generatorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1674);
			match(Multiply);
			setState(1675);
			iteratorDefinition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IteratorBlockContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public List<IteratorDefinitionContext> iteratorDefinition() {
			return getRuleContexts(IteratorDefinitionContext.class);
		}
		public IteratorDefinitionContext iteratorDefinition(int i) {
			return getRuleContext(IteratorDefinitionContext.class,i);
		}
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public IteratorBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iteratorBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIteratorBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIteratorBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIteratorBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IteratorBlockContext iteratorBlock() throws RecognitionException {
		IteratorBlockContext _localctx = new IteratorBlockContext(_ctx, getState());
		enterRule(_localctx, 296, RULE_iteratorBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1677);
			match(OpenBrace);
			setState(1678);
			iteratorDefinition();
			setState(1683);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,221,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1679);
					match(Comma);
					setState(1680);
					iteratorDefinition();
					}
					} 
				}
				setState(1685);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,221,_ctx);
			}
			setState(1687);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1686);
				match(Comma);
				}
			}

			setState(1689);
			match(CloseBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IteratorDefinitionContext extends ParserRuleContext {
		public TerminalNode OpenBracket() { return getToken(TypeScriptParser.OpenBracket, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(TypeScriptParser.CloseBracket, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IteratorDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iteratorDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIteratorDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIteratorDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIteratorDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IteratorDefinitionContext iteratorDefinition() throws RecognitionException {
		IteratorDefinitionContext _localctx = new IteratorDefinitionContext(_ctx, getState());
		enterRule(_localctx, 298, RULE_iteratorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1691);
			match(OpenBracket);
			setState(1692);
			singleExpression(0);
			setState(1693);
			match(CloseBracket);
			setState(1694);
			parameterBlock();
			setState(1695);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode NullLiteral() { return getToken(TypeScriptParser.NullLiteral, 0); }
		public TerminalNode BooleanLiteral() { return getToken(TypeScriptParser.BooleanLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public TemplateStringLiteralContext templateStringLiteral() {
			return getRuleContext(TemplateStringLiteralContext.class,0);
		}
		public TerminalNode RegularExpressionLiteral() { return getToken(TypeScriptParser.RegularExpressionLiteral, 0); }
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 300, RULE_literal);
		try {
			setState(1703);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NullLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1697);
				match(NullLiteral);
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1698);
				match(BooleanLiteral);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(1699);
				match(StringLiteral);
				}
				break;
			case BackTick:
				enterOuterAlt(_localctx, 4);
				{
				setState(1700);
				templateStringLiteral();
				}
				break;
			case RegularExpressionLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(1701);
				match(RegularExpressionLiteral);
				}
				break;
			case Minus:
			case DecimalLiteral:
			case HexIntegerLiteral:
			case OctalIntegerLiteral:
			case OctalIntegerLiteral2:
			case BinaryIntegerLiteral:
				enterOuterAlt(_localctx, 6);
				{
				setState(1702);
				numericLiteral();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateStringLiteralContext extends ParserRuleContext {
		public List<TerminalNode> BackTick() { return getTokens(TypeScriptParser.BackTick); }
		public TerminalNode BackTick(int i) {
			return getToken(TypeScriptParser.BackTick, i);
		}
		public List<TemplateStringAtomContext> templateStringAtom() {
			return getRuleContexts(TemplateStringAtomContext.class);
		}
		public TemplateStringAtomContext templateStringAtom(int i) {
			return getRuleContext(TemplateStringAtomContext.class,i);
		}
		public TemplateStringLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateStringLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTemplateStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTemplateStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTemplateStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TemplateStringLiteralContext templateStringLiteral() throws RecognitionException {
		TemplateStringLiteralContext _localctx = new TemplateStringLiteralContext(_ctx, getState());
		enterRule(_localctx, 302, RULE_templateStringLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1705);
			match(BackTick);
			setState(1709);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TemplateStringStartExpression || _la==TemplateStringAtom) {
				{
				{
				setState(1706);
				templateStringAtom();
				}
				}
				setState(1711);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1712);
			match(BackTick);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateStringAtomContext extends ParserRuleContext {
		public TerminalNode TemplateStringAtom() { return getToken(TypeScriptParser.TemplateStringAtom, 0); }
		public TerminalNode TemplateStringStartExpression() { return getToken(TypeScriptParser.TemplateStringStartExpression, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public TemplateStringAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateStringAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTemplateStringAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTemplateStringAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTemplateStringAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TemplateStringAtomContext templateStringAtom() throws RecognitionException {
		TemplateStringAtomContext _localctx = new TemplateStringAtomContext(_ctx, getState());
		enterRule(_localctx, 304, RULE_templateStringAtom);
		try {
			setState(1719);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TemplateStringAtom:
				enterOuterAlt(_localctx, 1);
				{
				setState(1714);
				match(TemplateStringAtom);
				}
				break;
			case TemplateStringStartExpression:
				enterOuterAlt(_localctx, 2);
				{
				setState(1715);
				match(TemplateStringStartExpression);
				setState(1716);
				singleExpression(0);
				setState(1717);
				match(CloseBrace);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericLiteralContext extends ParserRuleContext {
		public TerminalNode DecimalLiteral() { return getToken(TypeScriptParser.DecimalLiteral, 0); }
		public TerminalNode Minus() { return getToken(TypeScriptParser.Minus, 0); }
		public TerminalNode HexIntegerLiteral() { return getToken(TypeScriptParser.HexIntegerLiteral, 0); }
		public TerminalNode OctalIntegerLiteral() { return getToken(TypeScriptParser.OctalIntegerLiteral, 0); }
		public TerminalNode OctalIntegerLiteral2() { return getToken(TypeScriptParser.OctalIntegerLiteral2, 0); }
		public TerminalNode BinaryIntegerLiteral() { return getToken(TypeScriptParser.BinaryIntegerLiteral, 0); }
		public NumericLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterNumericLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitNumericLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralContext numericLiteral() throws RecognitionException {
		NumericLiteralContext _localctx = new NumericLiteralContext(_ctx, getState());
		enterRule(_localctx, 306, RULE_numericLiteral);
		int _la;
		try {
			setState(1729);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Minus:
			case DecimalLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1722);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Minus) {
					{
					setState(1721);
					match(Minus);
					}
				}

				setState(1724);
				match(DecimalLiteral);
				}
				break;
			case HexIntegerLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1725);
				match(HexIntegerLiteral);
				}
				break;
			case OctalIntegerLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(1726);
				match(OctalIntegerLiteral);
				}
				break;
			case OctalIntegerLiteral2:
				enterOuterAlt(_localctx, 4);
				{
				setState(1727);
				match(OctalIntegerLiteral2);
				}
				break;
			case BinaryIntegerLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(1728);
				match(BinaryIntegerLiteral);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierOrKeyWordContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TerminalNode Require() { return getToken(TypeScriptParser.Require, 0); }
		public TerminalNode TypeAlias() { return getToken(TypeScriptParser.TypeAlias, 0); }
		public IdentifierOrKeyWordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierOrKeyWord; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIdentifierOrKeyWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIdentifierOrKeyWord(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIdentifierOrKeyWord(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierOrKeyWordContext identifierOrKeyWord() throws RecognitionException {
		IdentifierOrKeyWordContext _localctx = new IdentifierOrKeyWordContext(_ctx, getState());
		enterRule(_localctx, 308, RULE_identifierOrKeyWord);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1731);
			_la = _input.LA(1);
			if ( !(((((_la - 116)) & ~0x3f) == 0 && ((1L << (_la - 116)) & ((1L << (TypeAlias - 116)) | (1L << (Require - 116)) | (1L << (Identifier - 116)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierNameContext extends ParserRuleContext {
		public ReservedWordContext reservedWord() {
			return getRuleContext(ReservedWordContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public IdentifierNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIdentifierName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIdentifierName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIdentifierName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierNameContext identifierName() throws RecognitionException {
		IdentifierNameContext _localctx = new IdentifierNameContext(_ctx, getState());
		enterRule(_localctx, 310, RULE_identifierName);
		try {
			setState(1735);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NullLiteral:
			case UndefinedLiteral:
			case BooleanLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Typeof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case ReadOnly:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
				enterOuterAlt(_localctx, 1);
				{
				setState(1733);
				reservedWord();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1734);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReservedWordContext extends ParserRuleContext {
		public KeywordContext keyword() {
			return getRuleContext(KeywordContext.class,0);
		}
		public TerminalNode BooleanLiteral() { return getToken(TypeScriptParser.BooleanLiteral, 0); }
		public ReservedWordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reservedWord; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterReservedWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitReservedWord(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitReservedWord(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReservedWordContext reservedWord() throws RecognitionException {
		ReservedWordContext _localctx = new ReservedWordContext(_ctx, getState());
		enterRule(_localctx, 312, RULE_reservedWord);
		try {
			setState(1739);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NullLiteral:
			case UndefinedLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Typeof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case ReadOnly:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
				enterOuterAlt(_localctx, 1);
				{
				setState(1737);
				keyword();
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1738);
				match(BooleanLiteral);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeReferenceNameContext extends ParserRuleContext {
		public KeywordAllowedInTypeReferencesContext keywordAllowedInTypeReferences() {
			return getRuleContext(KeywordAllowedInTypeReferencesContext.class,0);
		}
		public TerminalNode BooleanLiteral() { return getToken(TypeScriptParser.BooleanLiteral, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public TypeReferenceNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeReferenceName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeReferenceName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeReferenceName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeReferenceName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeReferenceNameContext typeReferenceName() throws RecognitionException {
		TypeReferenceNameContext _localctx = new TypeReferenceNameContext(_ctx, getState());
		enterRule(_localctx, 314, RULE_typeReferenceName);
		try {
			setState(1744);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NullLiteral:
			case UndefinedLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
				enterOuterAlt(_localctx, 1);
				{
				setState(1741);
				keywordAllowedInTypeReferences();
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1742);
				match(BooleanLiteral);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 3);
				{
				setState(1743);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeywordContext extends ParserRuleContext {
		public KeywordAllowedInTypeReferencesContext keywordAllowedInTypeReferences() {
			return getRuleContext(KeywordAllowedInTypeReferencesContext.class,0);
		}
		public TerminalNode ReadOnly() { return getToken(TypeScriptParser.ReadOnly, 0); }
		public TerminalNode Typeof() { return getToken(TypeScriptParser.Typeof, 0); }
		public KeywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyword; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterKeyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitKeyword(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitKeyword(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeywordContext keyword() throws RecognitionException {
		KeywordContext _localctx = new KeywordContext(_ctx, getState());
		enterRule(_localctx, 316, RULE_keyword);
		try {
			setState(1749);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NullLiteral:
			case UndefinedLiteral:
			case Break:
			case Do:
			case Instanceof:
			case Unique:
			case Case:
			case Else:
			case New:
			case Target:
			case Var:
			case Catch:
			case Finally:
			case Return:
			case Void:
			case Continue:
			case For:
			case Switch:
			case While:
			case Debugger:
			case Function:
			case This:
			case With:
			case Default:
			case If:
			case Throw:
			case Delete:
			case In:
			case Try:
			case As:
			case From:
			case Async:
			case Await:
			case Class:
			case Enum:
			case Extends:
			case Super:
			case Const:
			case Export:
			case Import:
			case Implements:
			case Let:
			case Private:
			case Public:
			case Interface:
			case Package:
			case Protected:
			case Static:
			case Yield:
			case Any:
			case Number:
			case Boolean:
			case String:
			case Symbol:
			case TypeAlias:
			case Get:
			case Set:
			case Constructor:
			case Namespace:
			case Require:
			case Module:
			case Declare:
			case Abstract:
			case Is:
			case Infer:
			case Never:
			case Unknown:
			case Asserts:
				enterOuterAlt(_localctx, 1);
				{
				setState(1746);
				keywordAllowedInTypeReferences();
				}
				break;
			case ReadOnly:
				enterOuterAlt(_localctx, 2);
				{
				setState(1747);
				match(ReadOnly);
				}
				break;
			case Typeof:
				enterOuterAlt(_localctx, 3);
				{
				setState(1748);
				match(Typeof);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeywordAllowedInTypeReferencesContext extends ParserRuleContext {
		public TerminalNode Abstract() { return getToken(TypeScriptParser.Abstract, 0); }
		public TerminalNode Any() { return getToken(TypeScriptParser.Any, 0); }
		public TerminalNode As() { return getToken(TypeScriptParser.As, 0); }
		public TerminalNode Asserts() { return getToken(TypeScriptParser.Asserts, 0); }
		public TerminalNode Async() { return getToken(TypeScriptParser.Async, 0); }
		public TerminalNode Await() { return getToken(TypeScriptParser.Await, 0); }
		public TerminalNode Break() { return getToken(TypeScriptParser.Break, 0); }
		public TerminalNode Boolean() { return getToken(TypeScriptParser.Boolean, 0); }
		public TerminalNode Case() { return getToken(TypeScriptParser.Case, 0); }
		public TerminalNode Catch() { return getToken(TypeScriptParser.Catch, 0); }
		public TerminalNode Class() { return getToken(TypeScriptParser.Class, 0); }
		public TerminalNode Const() { return getToken(TypeScriptParser.Const, 0); }
		public TerminalNode Constructor() { return getToken(TypeScriptParser.Constructor, 0); }
		public TerminalNode Continue() { return getToken(TypeScriptParser.Continue, 0); }
		public TerminalNode Debugger() { return getToken(TypeScriptParser.Debugger, 0); }
		public TerminalNode Declare() { return getToken(TypeScriptParser.Declare, 0); }
		public TerminalNode Default() { return getToken(TypeScriptParser.Default, 0); }
		public TerminalNode Delete() { return getToken(TypeScriptParser.Delete, 0); }
		public TerminalNode Do() { return getToken(TypeScriptParser.Do, 0); }
		public TerminalNode Else() { return getToken(TypeScriptParser.Else, 0); }
		public TerminalNode Enum() { return getToken(TypeScriptParser.Enum, 0); }
		public TerminalNode Export() { return getToken(TypeScriptParser.Export, 0); }
		public TerminalNode Extends() { return getToken(TypeScriptParser.Extends, 0); }
		public TerminalNode Finally() { return getToken(TypeScriptParser.Finally, 0); }
		public TerminalNode For() { return getToken(TypeScriptParser.For, 0); }
		public TerminalNode From() { return getToken(TypeScriptParser.From, 0); }
		public TerminalNode Function() { return getToken(TypeScriptParser.Function, 0); }
		public TerminalNode Get() { return getToken(TypeScriptParser.Get, 0); }
		public TerminalNode If() { return getToken(TypeScriptParser.If, 0); }
		public TerminalNode Implements() { return getToken(TypeScriptParser.Implements, 0); }
		public TerminalNode Import() { return getToken(TypeScriptParser.Import, 0); }
		public TerminalNode In() { return getToken(TypeScriptParser.In, 0); }
		public TerminalNode Infer() { return getToken(TypeScriptParser.Infer, 0); }
		public TerminalNode Instanceof() { return getToken(TypeScriptParser.Instanceof, 0); }
		public TerminalNode Interface() { return getToken(TypeScriptParser.Interface, 0); }
		public TerminalNode Is() { return getToken(TypeScriptParser.Is, 0); }
		public TerminalNode Let() { return getToken(TypeScriptParser.Let, 0); }
		public TerminalNode Module() { return getToken(TypeScriptParser.Module, 0); }
		public TerminalNode Namespace() { return getToken(TypeScriptParser.Namespace, 0); }
		public TerminalNode Never() { return getToken(TypeScriptParser.Never, 0); }
		public TerminalNode New() { return getToken(TypeScriptParser.New, 0); }
		public TerminalNode NullLiteral() { return getToken(TypeScriptParser.NullLiteral, 0); }
		public TerminalNode Number() { return getToken(TypeScriptParser.Number, 0); }
		public TerminalNode Package() { return getToken(TypeScriptParser.Package, 0); }
		public TerminalNode Private() { return getToken(TypeScriptParser.Private, 0); }
		public TerminalNode Protected() { return getToken(TypeScriptParser.Protected, 0); }
		public TerminalNode Public() { return getToken(TypeScriptParser.Public, 0); }
		public TerminalNode Require() { return getToken(TypeScriptParser.Require, 0); }
		public TerminalNode Return() { return getToken(TypeScriptParser.Return, 0); }
		public TerminalNode Set() { return getToken(TypeScriptParser.Set, 0); }
		public TerminalNode Static() { return getToken(TypeScriptParser.Static, 0); }
		public TerminalNode String() { return getToken(TypeScriptParser.String, 0); }
		public TerminalNode Super() { return getToken(TypeScriptParser.Super, 0); }
		public TerminalNode Switch() { return getToken(TypeScriptParser.Switch, 0); }
		public TerminalNode Symbol() { return getToken(TypeScriptParser.Symbol, 0); }
		public TerminalNode Target() { return getToken(TypeScriptParser.Target, 0); }
		public TerminalNode This() { return getToken(TypeScriptParser.This, 0); }
		public TerminalNode Throw() { return getToken(TypeScriptParser.Throw, 0); }
		public TerminalNode Try() { return getToken(TypeScriptParser.Try, 0); }
		public TerminalNode TypeAlias() { return getToken(TypeScriptParser.TypeAlias, 0); }
		public TerminalNode UndefinedLiteral() { return getToken(TypeScriptParser.UndefinedLiteral, 0); }
		public TerminalNode Unknown() { return getToken(TypeScriptParser.Unknown, 0); }
		public TerminalNode Unique() { return getToken(TypeScriptParser.Unique, 0); }
		public TerminalNode Var() { return getToken(TypeScriptParser.Var, 0); }
		public TerminalNode Void() { return getToken(TypeScriptParser.Void, 0); }
		public TerminalNode While() { return getToken(TypeScriptParser.While, 0); }
		public TerminalNode With() { return getToken(TypeScriptParser.With, 0); }
		public TerminalNode Yield() { return getToken(TypeScriptParser.Yield, 0); }
		public KeywordAllowedInTypeReferencesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keywordAllowedInTypeReferences; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterKeywordAllowedInTypeReferences(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitKeywordAllowedInTypeReferences(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitKeywordAllowedInTypeReferences(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeywordAllowedInTypeReferencesContext keywordAllowedInTypeReferences() throws RecognitionException {
		KeywordAllowedInTypeReferencesContext _localctx = new KeywordAllowedInTypeReferencesContext(_ctx, getState());
		enterRule(_localctx, 318, RULE_keywordAllowedInTypeReferences);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1751);
			_la = _input.LA(1);
			if ( !(((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (NullLiteral - 53)) | (1L << (UndefinedLiteral - 53)) | (1L << (Break - 53)) | (1L << (Do - 53)) | (1L << (Instanceof - 53)) | (1L << (Unique - 53)) | (1L << (Case - 53)) | (1L << (Else - 53)) | (1L << (New - 53)) | (1L << (Target - 53)) | (1L << (Var - 53)) | (1L << (Catch - 53)) | (1L << (Finally - 53)) | (1L << (Return - 53)) | (1L << (Void - 53)) | (1L << (Continue - 53)) | (1L << (For - 53)) | (1L << (Switch - 53)) | (1L << (While - 53)) | (1L << (Debugger - 53)) | (1L << (Function - 53)) | (1L << (This - 53)) | (1L << (With - 53)) | (1L << (Default - 53)) | (1L << (If - 53)) | (1L << (Throw - 53)) | (1L << (Delete - 53)) | (1L << (In - 53)) | (1L << (Try - 53)) | (1L << (As - 53)) | (1L << (From - 53)) | (1L << (Async - 53)) | (1L << (Await - 53)) | (1L << (Class - 53)) | (1L << (Enum - 53)) | (1L << (Extends - 53)) | (1L << (Super - 53)) | (1L << (Const - 53)) | (1L << (Export - 53)) | (1L << (Import - 53)) | (1L << (Implements - 53)) | (1L << (Let - 53)) | (1L << (Private - 53)) | (1L << (Public - 53)) | (1L << (Interface - 53)) | (1L << (Package - 53)) | (1L << (Protected - 53)) | (1L << (Static - 53)) | (1L << (Yield - 53)) | (1L << (Any - 53)) | (1L << (Number - 53)) | (1L << (Boolean - 53)) | (1L << (String - 53)) | (1L << (Symbol - 53)) | (1L << (TypeAlias - 53)))) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & ((1L << (Get - 117)) | (1L << (Set - 117)) | (1L << (Constructor - 117)) | (1L << (Namespace - 117)) | (1L << (Require - 117)) | (1L << (Module - 117)) | (1L << (Declare - 117)) | (1L << (Abstract - 117)) | (1L << (Is - 117)) | (1L << (Infer - 117)) | (1L << (Never - 117)) | (1L << (Unknown - 117)) | (1L << (Asserts - 117)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GetterContext extends ParserRuleContext {
		public TerminalNode Get() { return getToken(TypeScriptParser.Get, 0); }
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public GetterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_getter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterGetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitGetter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitGetter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GetterContext getter() throws RecognitionException {
		GetterContext _localctx = new GetterContext(_ctx, getState());
		enterRule(_localctx, 320, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1753);
			match(Get);
			setState(1754);
			propertyName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetterContext extends ParserRuleContext {
		public TerminalNode Set() { return getToken(TypeScriptParser.Set, 0); }
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public SetterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterSetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitSetter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitSetter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetterContext setter() throws RecognitionException {
		SetterContext _localctx = new SetterContext(_ctx, getState());
		enterRule(_localctx, 322, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1756);
			match(Set);
			setState(1757);
			propertyName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EosContext extends ParserRuleContext {
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public TerminalNode EOF() { return getToken(TypeScriptParser.EOF, 0); }
		public EosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eos; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterEos(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitEos(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitEos(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EosContext eos() throws RecognitionException {
		EosContext _localctx = new EosContext(_ctx, getState());
		enterRule(_localctx, 324, RULE_eos);
		try {
			setState(1763);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,232,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1759);
				match(SemiColon);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1760);
				match(EOF);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1761);
				if (!(this.lineTerminatorAhead())) throw new FailedPredicateException(this, "this.lineTerminatorAhead()");
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1762);
				if (!(this.closeBrace())) throw new FailedPredicateException(this, "this.closeBrace()");
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 48:
			return decoratorMemberExpression_sempred((DecoratorMemberExpressionContext)_localctx, predIndex);
		case 127:
			return iterationStatement_sempred((IterationStatementContext)_localctx, predIndex);
		case 128:
			return continueStatement_sempred((ContinueStatementContext)_localctx, predIndex);
		case 129:
			return breakStatement_sempred((BreakStatementContext)_localctx, predIndex);
		case 130:
			return returnStatement_sempred((ReturnStatementContext)_localctx, predIndex);
		case 133:
			return throwStatement_sempred((ThrowStatementContext)_localctx, predIndex);
		case 135:
			return expressionStatement_sempred((ExpressionStatementContext)_localctx, predIndex);
		case 137:
			return singleExpression_sempred((SingleExpressionContext)_localctx, predIndex);
		case 162:
			return eos_sempred((EosContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean decoratorMemberExpression_sempred(DecoratorMemberExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean iterationStatement_sempred(IterationStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return this.p("of");
		case 2:
			return this.p("of");
		}
		return true;
	}
	private boolean continueStatement_sempred(ContinueStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean breakStatement_sempred(BreakStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean returnStatement_sempred(ReturnStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean throwStatement_sempred(ThrowStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean expressionStatement_sempred(ExpressionStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return this.notOpenBraceAndNotFunction();
		}
		return true;
	}
	private boolean singleExpression_sempred(SingleExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return this.notLineTerminator();
		case 9:
			return precpred(_ctx, 22);
		case 10:
			return precpred(_ctx, 21);
		case 11:
			return precpred(_ctx, 20);
		case 12:
			return precpred(_ctx, 19);
		case 13:
			return precpred(_ctx, 18);
		case 14:
			return precpred(_ctx, 17);
		case 15:
			return precpred(_ctx, 16);
		case 16:
			return precpred(_ctx, 15);
		case 17:
			return precpred(_ctx, 14);
		case 18:
			return precpred(_ctx, 13);
		case 19:
			return precpred(_ctx, 11);
		case 20:
			return precpred(_ctx, 10);
		case 21:
			return this.notLineTerminator();
		case 22:
			return precpred(_ctx, 9);
		case 23:
			return precpred(_ctx, 8);
		case 24:
			return precpred(_ctx, 7);
		}
		return true;
	}
	private boolean eos_sempred(EosContext _localctx, int predIndex) {
		switch (predIndex) {
		case 25:
			return this.lineTerminatorAhead();
		case 26:
			return this.closeBrace();
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u008e\u06e8\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\t"+
		"k\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv\4"+
		"w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t\u0080"+
		"\4\u0081\t\u0081\4\u0082\t\u0082\4\u0083\t\u0083\4\u0084\t\u0084\4\u0085"+
		"\t\u0085\4\u0086\t\u0086\4\u0087\t\u0087\4\u0088\t\u0088\4\u0089\t\u0089"+
		"\4\u008a\t\u008a\4\u008b\t\u008b\4\u008c\t\u008c\4\u008d\t\u008d\4\u008e"+
		"\t\u008e\4\u008f\t\u008f\4\u0090\t\u0090\4\u0091\t\u0091\4\u0092\t\u0092"+
		"\4\u0093\t\u0093\4\u0094\t\u0094\4\u0095\t\u0095\4\u0096\t\u0096\4\u0097"+
		"\t\u0097\4\u0098\t\u0098\4\u0099\t\u0099\4\u009a\t\u009a\4\u009b\t\u009b"+
		"\4\u009c\t\u009c\4\u009d\t\u009d\4\u009e\t\u009e\4\u009f\t\u009f\4\u00a0"+
		"\t\u00a0\4\u00a1\t\u00a1\4\u00a2\t\u00a2\4\u00a3\t\u00a3\4\u00a4\t\u00a4"+
		"\3\2\5\2\u014a\n\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u015f\n\3\3\4\5\4\u0162\n\4\3\4\3\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u016e\n\5\3\6\3\6\5\6\u0172\n\6\3\6\3\6"+
		"\3\7\6\7\u0177\n\7\r\7\16\7\u0178\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\5\n\u0188\n\n\3\13\5\13\u018b\n\13\3\13\3\13\3\13\7\13"+
		"\u0190\n\13\f\13\16\13\u0193\13\13\3\f\5\f\u0196\n\f\3\f\3\f\3\f\7\f\u019b"+
		"\n\f\f\f\16\f\u019e\13\f\3\r\5\r\u01a1\n\r\3\r\3\r\3\16\3\16\3\17\3\17"+
		"\7\17\u01a9\n\17\f\17\16\17\u01ac\13\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\5\20\u01b4\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u01be\n"+
		"\21\3\22\3\22\3\22\5\22\u01c3\n\22\3\23\5\23\u01c6\n\23\3\23\5\23\u01c9"+
		"\n\23\3\23\5\23\u01cc\n\23\3\23\3\23\3\23\3\23\3\23\5\23\u01d3\n\23\3"+
		"\24\3\24\3\24\3\24\3\24\7\24\u01da\n\24\f\24\16\24\u01dd\13\24\3\24\5"+
		"\24\u01e0\n\24\3\24\3\24\5\24\u01e4\n\24\3\25\5\25\u01e7\n\25\3\25\5\25"+
		"\u01ea\n\25\3\25\3\25\5\25\u01ee\n\25\3\25\3\25\5\25\u01f2\n\25\3\26\3"+
		"\26\3\26\5\26\u01f7\n\26\3\27\3\27\3\27\5\27\u01fc\n\27\3\30\3\30\3\30"+
		"\3\30\3\31\3\31\5\31\u0204\n\31\3\32\3\32\3\32\7\32\u0209\n\32\f\32\16"+
		"\32\u020c\13\32\3\33\3\33\3\33\5\33\u0211\n\33\5\33\u0213\n\33\3\33\3"+
		"\33\3\34\3\34\3\34\7\34\u021a\n\34\f\34\16\34\u021d\13\34\3\35\5\35\u0220"+
		"\n\35\3\35\3\35\3\36\3\36\5\36\u0226\n\36\3\36\3\36\3\37\3\37\3 \3 \3"+
		" \3!\3!\3!\3!\3!\3!\5!\u0235\n!\3\"\5\"\u0238\n\"\3\"\3\"\5\"\u023c\n"+
		"\"\3\"\3\"\3\"\3#\3#\3$\3$\3$\7$\u0246\n$\f$\16$\u0249\13$\3%\3%\3%\3"+
		"&\3&\3&\5&\u0251\n&\3&\3&\3&\5&\u0256\n&\3\'\3\'\5\'\u025a\n\'\3\'\3\'"+
		"\3(\3(\3(\7(\u0261\n(\f(\16(\u0264\13(\3)\3)\5)\u0268\n)\3)\3)\5)\u026c"+
		"\n)\3*\3*\3*\3+\3+\3,\3,\3,\3,\3-\3-\3.\3.\3.\3.\3/\3/\3/\7/\u0280\n/"+
		"\f/\16/\u0283\13/\3\60\6\60\u0286\n\60\r\60\16\60\u0287\3\61\3\61\3\61"+
		"\5\61\u028d\n\61\3\62\3\62\3\62\3\62\3\62\3\62\5\62\u0295\n\62\3\62\3"+
		"\62\3\62\7\62\u029a\n\62\f\62\16\62\u029d\13\62\3\63\3\63\3\63\3\64\3"+
		"\64\3\64\5\64\u02a5\n\64\3\64\5\64\u02a8\n\64\3\64\3\64\5\64\u02ac\n\64"+
		"\3\64\3\64\5\64\u02b0\n\64\3\65\3\65\3\65\3\66\3\66\3\66\7\66\u02b8\n"+
		"\66\f\66\16\66\u02bb\13\66\3\67\3\67\5\67\u02bf\n\67\38\38\38\38\58\u02c5"+
		"\n8\38\78\u02c8\n8\f8\168\u02cb\138\39\39\39\39\39\39\39\59\u02d4\n9\3"+
		":\3:\5:\u02d8\n:\3:\3:\5:\u02dc\n:\3;\5;\u02df\n;\3;\3;\3;\3;\5;\u02e5"+
		"\n;\5;\u02e7\n;\3<\5<\u02ea\n<\3<\5<\u02ed\n<\3<\3<\3<\5<\u02f2\n<\3<"+
		"\3<\3<\3<\5<\u02f8\n<\3<\3<\3<\5<\u02fd\n<\3<\3<\3=\3=\3=\3=\3=\3=\3="+
		"\5=\u0308\n=\3>\3>\5>\u030c\n>\3>\3>\3?\5?\u0311\n?\3?\3?\5?\u0315\n?"+
		"\3?\5?\u0318\n?\3@\5@\u031b\n@\3@\3@\3@\3@\5@\u0321\n@\3@\3@\5@\u0325"+
		"\n@\3A\3A\5A\u0329\nA\3B\3B\3B\7B\u032e\nB\fB\16B\u0331\13B\3C\3C\3C\5"+
		"C\u0336\nC\3D\3D\5D\u033a\nD\3D\3D\3D\5D\u033f\nD\3D\5D\u0342\nD\3E\5"+
		"E\u0345\nE\3E\3E\3E\5E\u034a\nE\3E\3E\3E\5E\u034f\nE\3F\5F\u0352\nF\3"+
		"F\5F\u0355\nF\3G\3G\3G\3H\3H\3H\3I\3I\5I\u035f\nI\3I\5I\u0362\nI\3I\3"+
		"I\3J\3J\3J\5J\u0369\nJ\3J\7J\u036c\nJ\fJ\16J\u036f\13J\3K\3K\3K\5K\u0374"+
		"\nK\3K\5K\u0377\nK\3L\5L\u037a\nL\3L\3L\3L\5L\u037f\nL\3M\3M\5M\u0383"+
		"\nM\3N\3N\3N\3N\5N\u0389\nN\3N\3N\3O\3O\3O\3O\5O\u0391\nO\3P\5P\u0394"+
		"\nP\3P\5P\u0397\nP\3P\5P\u039a\nP\3Q\5Q\u039d\nQ\3Q\3Q\5Q\u03a1\nQ\3Q"+
		"\5Q\u03a4\nQ\3Q\5Q\u03a7\nQ\3Q\3Q\5Q\u03ab\nQ\5Q\u03ad\nQ\3R\3R\3R\3S"+
		"\3S\3S\5S\u03b5\nS\3S\5S\u03b8\nS\3S\5S\u03bb\nS\3S\3S\3T\3T\5T\u03c1"+
		"\nT\3U\3U\3U\3U\7U\u03c7\nU\fU\16U\u03ca\13U\3U\3U\5U\u03ce\nU\5U\u03d0"+
		"\nU\3V\3V\3V\5V\u03d5\nV\3W\3W\5W\u03d9\nW\3X\5X\u03dc\nX\3X\5X\u03df"+
		"\nX\3X\3X\5X\u03e3\nX\3Y\5Y\u03e6\nY\3Y\5Y\u03e9\nY\3Y\3Y\3Y\5Y\u03ee"+
		"\nY\3Y\5Y\u03f1\nY\3Y\5Y\u03f4\nY\3Z\3Z\3[\3[\5[\u03fa\n[\3\\\3\\\5\\"+
		"\u03fe\n\\\3]\3]\5]\u0402\n]\3]\3]\3^\3^\6^\u0408\n^\r^\16^\u0409\3^\7"+
		"^\u040d\n^\f^\16^\u0410\13^\3^\5^\u0413\n^\3_\5_\u0416\n_\3_\3_\3`\3`"+
		"\5`\u041c\n`\3a\3a\3a\3a\7a\u0422\na\fa\16a\u0425\13a\3a\5a\u0428\na\5"+
		"a\u042a\na\3a\3a\3b\3b\3b\3b\5b\u0432\nb\3b\3b\5b\u0436\nb\3b\3b\3b\3"+
		"b\5b\u043c\nb\3c\3c\3c\3c\3c\3c\3c\3c\5c\u0446\nc\3c\3c\5c\u044a\nc\3"+
		"d\3d\3d\3d\5d\u0450\nd\3d\5d\u0453\nd\3e\3e\3e\3e\5e\u0459\ne\3e\5e\u045c"+
		"\ne\3e\3e\5e\u0460\ne\3f\5f\u0463\nf\3f\3f\3f\3f\3g\3g\3g\5g\u046c\ng"+
		"\5g\u046e\ng\3g\3g\3h\3h\3h\7h\u0475\nh\fh\16h\u0478\13h\3i\5i\u047b\n"+
		"i\3i\3i\5i\u047f\ni\3j\3j\3j\3j\5j\u0485\nj\3j\3j\3k\5k\u048a\nk\3k\3"+
		"k\3k\3k\3k\5k\u0491\nk\3k\3k\3k\3l\3l\3l\5l\u0499\nl\3l\3l\3l\3l\7l\u049f"+
		"\nl\fl\16l\u04a2\13l\3l\5l\u04a5\nl\3l\3l\3m\3m\3m\5m\u04ac\nm\3n\3n\3"+
		"n\3n\3n\3n\3n\5n\u04b5\nn\3o\3o\3o\3p\5p\u04bb\np\3p\3p\3p\3p\3p\3p\5"+
		"p\u04c3\np\3p\3p\3p\5p\u04c8\np\3p\3p\3p\3p\3p\3p\3p\3p\3p\3p\3p\3p\3"+
		"p\3p\3p\3p\3p\5p\u04db\np\3q\3q\3q\3q\7q\u04e1\nq\fq\16q\u04e4\13q\3q"+
		"\5q\u04e7\nq\3q\3q\3r\5r\u04ec\nr\3r\5r\u04ef\nr\3r\3r\3r\5r\u04f4\nr"+
		"\3r\5r\u04f7\nr\3s\3s\3t\3t\5t\u04fd\nt\3t\5t\u0500\nt\3u\3u\3u\7u\u0505"+
		"\nu\fu\16u\u0508\13u\3v\3v\5v\u050c\nv\3v\3v\5v\u0510\nv\3v\5v\u0513\n"+
		"v\3w\3w\3w\3w\3w\3w\3x\3x\5x\u051d\nx\3x\3x\5x\u0521\nx\5x\u0523\nx\3"+
		"x\3x\3y\6y\u0528\ny\ry\16y\u0529\3z\3z\3z\3z\5z\u0530\nz\3{\3{\3{\5{\u0535"+
		"\n{\3|\3|\3|\3|\5|\u053b\n|\3|\5|\u053e\n|\3}\3}\3}\3}\3}\3}\3~\3~\3~"+
		"\3\177\3\177\3\u0080\3\u0080\3\u0080\3\u0080\3\u0080\3\u0080\3\u0080\5"+
		"\u0080\u0552\n\u0080\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3"+
		"\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\5\u0081\u0565\n\u0081\3\u0081\3\u0081\5\u0081\u0569\n"+
		"\u0081\3\u0081\3\u0081\5\u0081\u056d\n\u0081\3\u0081\3\u0081\3\u0081\3"+
		"\u0081\3\u0081\3\u0081\3\u0081\3\u0081\5\u0081\u0577\n\u0081\3\u0081\3"+
		"\u0081\5\u0081\u057b\n\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3"+
		"\u0081\3\u0081\3\u0081\3\u0081\5\u0081\u0586\n\u0081\3\u0081\3\u0081\3"+
		"\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\5\u0081\u0593\n\u0081\3\u0081\3\u0081\3\u0081\3\u0081\5\u0081\u0599\n"+
		"\u0081\3\u0082\3\u0082\3\u0082\5\u0082\u059e\n\u0082\3\u0082\3\u0082\3"+
		"\u0083\3\u0083\3\u0083\5\u0083\u05a5\n\u0083\3\u0083\3\u0083\3\u0084\3"+
		"\u0084\3\u0084\5\u0084\u05ac\n\u0084\3\u0084\3\u0084\3\u0085\3\u0085\3"+
		"\u0085\3\u0085\3\u0085\3\u0085\3\u0086\3\u0086\3\u0086\3\u0086\3\u0087"+
		"\3\u0087\3\u0087\3\u0087\3\u0087\3\u0088\3\u0088\3\u0088\3\u0089\3\u0089"+
		"\3\u0089\3\u0089\3\u008a\3\u008a\3\u008a\7\u008a\u05c9\n\u008a\f\u008a"+
		"\16\u008a\u05cc\13\u008a\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\5\u008b\u05d8\n\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\5\u008b\u05e8\n\u008b\3\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\5\u008b\u060c\n\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\5\u008b\u061e\n\u008b\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\3\u008b\5\u008b\u0627\n\u008b"+
		"\3\u008b\3\u008b\5\u008b\u062b\n\u008b\3\u008b\3\u008b\3\u008b\3\u008b"+
		"\3\u008b\3\u008b\5\u008b\u0633\n\u008b\3\u008b\7\u008b\u0636\n\u008b\f"+
		"\u008b\16\u008b\u0639\13\u008b\3\u008c\3\u008c\5\u008c\u063d\n\u008c\3"+
		"\u008c\5\u008c\u0640\n\u008c\3\u008c\3\u008c\5\u008c\u0644\n\u008c\3\u008c"+
		"\3\u008c\3\u008d\5\u008d\u0649\n\u008d\3\u008d\3\u008d\5\u008d\u064d\n"+
		"\u008d\3\u008d\3\u008d\3\u008d\3\u008e\3\u008e\5\u008e\u0654\n\u008e\3"+
		"\u008f\3\u008f\5\u008f\u0658\n\u008f\3\u008f\3\u008f\3\u0090\3\u0090\3"+
		"\u0090\3\u0090\3\u0090\3\u0090\3\u0090\3\u0090\3\u0090\3\u0090\3\u0090"+
		"\5\u0090\u0667\n\u0090\3\u0090\3\u0090\3\u0090\3\u0090\5\u0090\u066d\n"+
		"\u0090\3\u0091\3\u0091\3\u0092\3\u0092\3\u0093\3\u0093\3\u0093\3\u0093"+
		"\3\u0093\5\u0093\u0678\n\u0093\3\u0093\5\u0093\u067b\n\u0093\5\u0093\u067d"+
		"\n\u0093\3\u0094\3\u0094\3\u0094\3\u0094\7\u0094\u0683\n\u0094\f\u0094"+
		"\16\u0094\u0686\13\u0094\3\u0094\5\u0094\u0689\n\u0094\3\u0094\3\u0094"+
		"\3\u0095\3\u0095\3\u0095\3\u0096\3\u0096\3\u0096\3\u0096\7\u0096\u0694"+
		"\n\u0096\f\u0096\16\u0096\u0697\13\u0096\3\u0096\5\u0096\u069a\n\u0096"+
		"\3\u0096\3\u0096\3\u0097\3\u0097\3\u0097\3\u0097\3\u0097\3\u0097\3\u0098"+
		"\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098\5\u0098\u06aa\n\u0098\3\u0099"+
		"\3\u0099\7\u0099\u06ae\n\u0099\f\u0099\16\u0099\u06b1\13\u0099\3\u0099"+
		"\3\u0099\3\u009a\3\u009a\3\u009a\3\u009a\3\u009a\5\u009a\u06ba\n\u009a"+
		"\3\u009b\5\u009b\u06bd\n\u009b\3\u009b\3\u009b\3\u009b\3\u009b\3\u009b"+
		"\5\u009b\u06c4\n\u009b\3\u009c\3\u009c\3\u009d\3\u009d\5\u009d\u06ca\n"+
		"\u009d\3\u009e\3\u009e\5\u009e\u06ce\n\u009e\3\u009f\3\u009f\3\u009f\5"+
		"\u009f\u06d3\n\u009f\3\u00a0\3\u00a0\3\u00a0\5\u00a0\u06d8\n\u00a0\3\u00a1"+
		"\3\u00a1\3\u00a2\3\u00a2\3\u00a2\3\u00a3\3\u00a3\3\u00a3\3\u00a4\3\u00a4"+
		"\3\u00a4\3\u00a4\5\u00a4\u06e6\n\u00a4\3\u00a4\2\4b\u0114\u00a5\2\4\6"+
		"\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRT"+
		"VXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e"+
		"\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4\u00a6"+
		"\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc\u00be"+
		"\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0\u00d2\u00d4\u00d6"+
		"\u00d8\u00da\u00dc\u00de\u00e0\u00e2\u00e4\u00e6\u00e8\u00ea\u00ec\u00ee"+
		"\u00f0\u00f2\u00f4\u00f6\u00f8\u00fa\u00fc\u00fe\u0100\u0102\u0104\u0106"+
		"\u0108\u010a\u010c\u010e\u0110\u0112\u0114\u0116\u0118\u011a\u011c\u011e"+
		"\u0120\u0122\u0124\u0126\u0128\u012a\u012c\u012e\u0130\u0132\u0134\u0136"+
		"\u0138\u013a\u013c\u013e\u0140\u0142\u0144\u0146\2\22\4\2CD^^\3\2\u0085"+
		"\u0086\3\2\r\16\4\2rrtt\4\2jknn\5\2IIeeii\3\2)*\3\2&(\3\2\"%\3\2\26\27"+
		"\3\2\32\34\3\2\24\25\5\2\36!AAZZ\6\2\24\31BBMMYY\5\2vv{{\u0085\u0085\7"+
		"\2\678?ACCE]_\u0083\2\u0794\2\u0149\3\2\2\2\4\u015e\3\2\2\2\6\u0161\3"+
		"\2\2\2\b\u016d\3\2\2\2\n\u016f\3\2\2\2\f\u0176\3\2\2\2\16\u017a\3\2\2"+
		"\2\20\u017d\3\2\2\2\22\u017f\3\2\2\2\24\u018a\3\2\2\2\26\u0195\3\2\2\2"+
		"\30\u01a0\3\2\2\2\32\u01a4\3\2\2\2\34\u01a6\3\2\2\2\36\u01b3\3\2\2\2 "+
		"\u01bd\3\2\2\2\"\u01c2\3\2\2\2$\u01c8\3\2\2\2&\u01d4\3\2\2\2(\u01e6\3"+
		"\2\2\2*\u01f3\3\2\2\2,\u01fb\3\2\2\2.\u01fd\3\2\2\2\60\u0201\3\2\2\2\62"+
		"\u0205\3\2\2\2\64\u020d\3\2\2\2\66\u0216\3\2\2\28\u021f\3\2\2\2:\u0223"+
		"\3\2\2\2<\u0229\3\2\2\2>\u022b\3\2\2\2@\u022e\3\2\2\2B\u0237\3\2\2\2D"+
		"\u0240\3\2\2\2F\u0242\3\2\2\2H\u024a\3\2\2\2J\u024d\3\2\2\2L\u0257\3\2"+
		"\2\2N\u025d\3\2\2\2P\u0265\3\2\2\2R\u026d\3\2\2\2T\u0270\3\2\2\2V\u0272"+
		"\3\2\2\2X\u0276\3\2\2\2Z\u0278\3\2\2\2\\\u027c\3\2\2\2^\u0285\3\2\2\2"+
		"`\u0289\3\2\2\2b\u0294\3\2\2\2d\u029e\3\2\2\2f\u02a1\3\2\2\2h\u02b1\3"+
		"\2\2\2j\u02b4\3\2\2\2l\u02bc\3\2\2\2n\u02c0\3\2\2\2p\u02d3\3\2\2\2r\u02d5"+
		"\3\2\2\2t\u02de\3\2\2\2v\u02e9\3\2\2\2x\u0307\3\2\2\2z\u0309\3\2\2\2|"+
		"\u0310\3\2\2\2~\u031a\3\2\2\2\u0080\u0326\3\2\2\2\u0082\u032a\3\2\2\2"+
		"\u0084\u0332\3\2\2\2\u0086\u0337\3\2\2\2\u0088\u0344\3\2\2\2\u008a\u0351"+
		"\3\2\2\2\u008c\u0356\3\2\2\2\u008e\u0359\3\2\2\2\u0090\u035c\3\2\2\2\u0092"+
		"\u0365\3\2\2\2\u0094\u0376\3\2\2\2\u0096\u0379\3\2\2\2\u0098\u0382\3\2"+
		"\2\2\u009a\u0384\3\2\2\2\u009c\u038c\3\2\2\2\u009e\u0393\3\2\2\2\u00a0"+
		"\u039c\3\2\2\2\u00a2\u03ae\3\2\2\2\u00a4\u03b1\3\2\2\2\u00a6\u03be\3\2"+
		"\2\2\u00a8\u03cf\3\2\2\2\u00aa\u03d1\3\2\2\2\u00ac\u03d8\3\2\2\2\u00ae"+
		"\u03db\3\2\2\2\u00b0\u03e5\3\2\2\2\u00b2\u03f5\3\2\2\2\u00b4\u03f9\3\2"+
		"\2\2\u00b6\u03fd\3\2\2\2\u00b8\u03ff\3\2\2\2\u00ba\u0405\3\2\2\2\u00bc"+
		"\u0415\3\2\2\2\u00be\u041b\3\2\2\2\u00c0\u041d\3\2\2\2\u00c2\u043b\3\2"+
		"\2\2\u00c4\u0449\3\2\2\2\u00c6\u044b\3\2\2\2\u00c8\u0454\3\2\2\2\u00ca"+
		"\u0462\3\2\2\2\u00cc\u0468\3\2\2\2\u00ce\u0471\3\2\2\2\u00d0\u047a\3\2"+
		"\2\2\u00d2\u0480\3\2\2\2\u00d4\u0489\3\2\2\2\u00d6\u0498\3\2\2\2\u00d8"+
		"\u04a8\3\2\2\2\u00da\u04ad\3\2\2\2\u00dc\u04b6\3\2\2\2\u00de\u04da\3\2"+
		"\2\2\u00e0\u04dc\3\2\2\2\u00e2\u04eb\3\2\2\2\u00e4\u04f8\3\2\2\2\u00e6"+
		"\u04fa\3\2\2\2\u00e8\u0501\3\2\2\2\u00ea\u0509\3\2\2\2\u00ec\u0514\3\2"+
		"\2\2\u00ee\u051a\3\2\2\2\u00f0\u0527\3\2\2\2\u00f2\u052b\3\2\2\2\u00f4"+
		"\u0531\3\2\2\2\u00f6\u0536\3\2\2\2\u00f8\u053f\3\2\2\2\u00fa\u0545\3\2"+
		"\2\2\u00fc\u0548\3\2\2\2\u00fe\u054a\3\2\2\2\u0100\u0598\3\2\2\2\u0102"+
		"\u059a\3\2\2\2\u0104\u05a1\3\2\2\2\u0106\u05a8\3\2\2\2\u0108\u05af\3\2"+
		"\2\2\u010a\u05b5\3\2\2\2\u010c\u05b9\3\2\2\2\u010e\u05be\3\2\2\2\u0110"+
		"\u05c1\3\2\2\2\u0112\u05c5\3\2\2\2\u0114\u05e7\3\2\2\2\u0116\u063a\3\2"+
		"\2\2\u0118\u0648\3\2\2\2\u011a\u0653\3\2\2\2\u011c\u0655\3\2\2\2\u011e"+
		"\u066c\3\2\2\2\u0120\u066e\3\2\2\2\u0122\u0670\3\2\2\2\u0124\u0672\3\2"+
		"\2\2\u0126\u067e\3\2\2\2\u0128\u068c\3\2\2\2\u012a\u068f\3\2\2\2\u012c"+
		"\u069d\3\2\2\2\u012e\u06a9\3\2\2\2\u0130\u06ab\3\2\2\2\u0132\u06b9\3\2"+
		"\2\2\u0134\u06c3\3\2\2\2\u0136\u06c5\3\2\2\2\u0138\u06c9\3\2\2\2\u013a"+
		"\u06cd\3\2\2\2\u013c\u06d2\3\2\2\2\u013e\u06d7\3\2\2\2\u0140\u06d9\3\2"+
		"\2\2\u0142\u06db\3\2\2\2\u0144\u06de\3\2\2\2\u0146\u06e5\3\2\2\2\u0148"+
		"\u014a\5\f\7\2\u0149\u0148\3\2\2\2\u0149\u014a\3\2\2\2\u014a\u014b\3\2"+
		"\2\2\u014b\u014c\7\2\2\3\u014c\3\3\2\2\2\u014d\u015f\5\n\6\2\u014e\u015f"+
		"\5\u00d2j\2\u014f\u015f\5^\60\2\u0150\u015f\5\u00dco\2\u0151\u015f\5\6"+
		"\4\2\u0152\u015f\5\u00fe\u0080\2\u0153\u015f\5\u0100\u0081\2\u0154\u015f"+
		"\5\u0102\u0082\2\u0155\u015f\5\u0104\u0083\2\u0156\u015f\5\u0106\u0084"+
		"\2\u0157\u015f\5\u0108\u0085\2\u0158\u015f\5\u010a\u0086\2\u0159\u015f"+
		"\5\u00ecw\2\u015a\u015f\5\u010c\u0087\2\u015b\u015f\5\u00f6|\2\u015c\u015f"+
		"\5\u010e\u0088\2\u015d\u015f\5\u00fc\177\2\u015e\u014d\3\2\2\2\u015e\u014e"+
		"\3\2\2\2\u015e\u014f\3\2\2\2\u015e\u0150\3\2\2\2\u015e\u0151\3\2\2\2\u015e"+
		"\u0152\3\2\2\2\u015e\u0153\3\2\2\2\u015e\u0154\3\2\2\2\u015e\u0155\3\2"+
		"\2\2\u015e\u0156\3\2\2\2\u015e\u0157\3\2\2\2\u015e\u0158\3\2\2\2\u015e"+
		"\u0159\3\2\2\2\u015e\u015a\3\2\2\2\u015e\u015b\3\2\2\2\u015e\u015c\3\2"+
		"\2\2\u015e\u015d\3\2\2\2\u015f\5\3\2\2\2\u0160\u0162\7}\2\2\u0161\u0160"+
		"\3\2\2\2\u0161\u0162\3\2\2\2\u0162\u0163\3\2\2\2\u0163\u0164\5\b\5\2\u0164"+
		"\7\3\2\2\2\u0165\u016e\5V,\2\u0166\u016e\5Z.\2\u0167\u016e\5f\64\2\u0168"+
		"\u016e\5J&\2\u0169\u016e\5\u0086D\2\u016a\u016e\5\u0088E\2\u016b\u016e"+
		"\5~@\2\u016c\u016e\5\u00e2r\2\u016d\u0165\3\2\2\2\u016d\u0166\3\2\2\2"+
		"\u016d\u0167\3\2\2\2\u016d\u0168\3\2\2\2\u016d\u0169\3\2\2\2\u016d\u016a"+
		"\3\2\2\2\u016d\u016b\3\2\2\2\u016d\u016c\3\2\2\2\u016e\t\3\2\2\2\u016f"+
		"\u0171\7\13\2\2\u0170\u0172\5\f\7\2\u0171\u0170\3\2\2\2\u0171\u0172\3"+
		"\2\2\2\u0172\u0173\3\2\2\2\u0173\u0174\7\f\2\2\u0174\13\3\2\2\2\u0175"+
		"\u0177\5\4\3\2\u0176\u0175\3\2\2\2\u0177\u0178\3\2\2\2\u0178\u0176\3\2"+
		"\2\2\u0178\u0179\3\2\2\2\u0179\r\3\2\2\2\u017a\u017b\7\21\2\2\u017b\u017c"+
		"\5\20\t\2\u017c\17\3\2\2\2\u017d\u017e\5\22\n\2\u017e\21\3\2\2\2\u017f"+
		"\u0187\5\24\13\2\u0180\u0181\7c\2\2\u0181\u0182\5\24\13\2\u0182\u0183"+
		"\7\20\2\2\u0183\u0184\5\22\n\2\u0184\u0185\7\21\2\2\u0185\u0186\5\22\n"+
		"\2\u0186\u0188\3\2\2\2\u0187\u0180\3\2\2\2\u0187\u0188\3\2\2\2\u0188\23"+
		"\3\2\2\2\u0189\u018b\7(\2\2\u018a\u0189\3\2\2\2\u018a\u018b\3\2\2\2\u018b"+
		"\u018c\3\2\2\2\u018c\u0191\5\26\f\2\u018d\u018e\7(\2\2\u018e\u0190\5\26"+
		"\f\2\u018f\u018d\3\2\2\2\u0190\u0193\3\2\2\2\u0191\u018f\3\2\2\2\u0191"+
		"\u0192\3\2\2\2\u0192\25\3\2\2\2\u0193\u0191\3\2\2\2\u0194\u0196\7&\2\2"+
		"\u0195\u0194\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u0197\3\2\2\2\u0197\u019c"+
		"\5\30\r\2\u0198\u0199\7&\2\2\u0199\u019b\5\30\r\2\u019a\u0198\3\2\2\2"+
		"\u019b\u019e\3\2\2\2\u019c\u019a\3\2\2\2\u019c\u019d\3\2\2\2\u019d\27"+
		"\3\2\2\2\u019e\u019c\3\2\2\2\u019f\u01a1\5\32\16\2\u01a0\u019f\3\2\2\2"+
		"\u01a0\u01a1\3\2\2\2\u01a1\u01a2\3\2\2\2\u01a2\u01a3\5\34\17\2\u01a3\31"+
		"\3\2\2\2\u01a4\u01a5\t\2\2\2\u01a5\33\3\2\2\2\u01a6\u01aa\5 \21\2\u01a7"+
		"\u01a9\5\36\20\2\u01a8\u01a7\3\2\2\2\u01a9\u01ac\3\2\2\2\u01aa\u01a8\3"+
		"\2\2\2\u01aa\u01ab\3\2\2\2\u01ab\35\3\2\2\2\u01ac\u01aa\3\2\2\2\u01ad"+
		"\u01ae\7\7\2\2\u01ae\u01b4\7\b\2\2\u01af\u01b0\7\7\2\2\u01b0\u01b1\5\20"+
		"\t\2\u01b1\u01b2\7\b\2\2\u01b2\u01b4\3\2\2\2\u01b3\u01ad\3\2\2\2\u01b3"+
		"\u01af\3\2\2\2\u01b4\37\3\2\2\2\u01b5\u01be\5\"\22\2\u01b6\u01be\5$\23"+
		"\2\u01b7\u01be\5&\24\2\u01b8\u01be\5> \2\u01b9\u01be\5@!\2\u01ba\u01be"+
		"\5H%\2\u01bb\u01be\5,\27\2\u01bc\u01be\5.\30\2\u01bd\u01b5\3\2\2\2\u01bd"+
		"\u01b6\3\2\2\2\u01bd\u01b7\3\2\2\2\u01bd\u01b8\3\2\2\2\u01bd\u01b9\3\2"+
		"\2\2\u01bd\u01ba\3\2\2\2\u01bd\u01bb\3\2\2\2\u01bd\u01bc\3\2\2\2\u01be"+
		"!\3\2\2\2\u01bf\u01c3\79\2\2\u01c0\u01c3\7\u0086\2\2\u01c1\u01c3\5\u0134"+
		"\u009b\2\u01c2\u01bf\3\2\2\2\u01c2\u01c0\3\2\2\2\u01c2\u01c1\3\2\2\2\u01c3"+
		"#\3\2\2\2\u01c4\u01c6\7~\2\2\u01c5\u01c4\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c6"+
		"\u01c7\3\2\2\2\u01c7\u01c9\7G\2\2\u01c8\u01c5\3\2\2\2\u01c8\u01c9\3\2"+
		"\2\2\u01c9\u01cb\3\2\2\2\u01ca\u01cc\5L\'\2\u01cb\u01ca\3\2\2\2\u01cb"+
		"\u01cc\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01ce\5\u00a4S\2\u01ce\u01cf"+
		"\7\66\2\2\u01cf\u01d2\3\2\2\2\u01d0\u01d3\5B\"\2\u01d1\u01d3\5\24\13\2"+
		"\u01d2\u01d0\3\2\2\2\u01d2\u01d1\3\2\2\2\u01d3%\3\2\2\2\u01d4\u01e3\7"+
		"\7\2\2\u01d5\u01e4\7\b\2\2\u01d6\u01db\5(\25\2\u01d7\u01d8\7\16\2\2\u01d8"+
		"\u01da\5(\25\2\u01d9\u01d7\3\2\2\2\u01da\u01dd\3\2\2\2\u01db\u01d9\3\2"+
		"\2\2\u01db\u01dc\3\2\2\2\u01dc\u01df\3\2\2\2\u01dd\u01db\3\2\2\2\u01de"+
		"\u01e0\7\16\2\2\u01df\u01de\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e1\3"+
		"\2\2\2\u01e1\u01e2\7\b\2\2\u01e2\u01e4\3\2\2\2\u01e3\u01d5\3\2\2\2\u01e3"+
		"\u01d6\3\2\2\2\u01e4\'\3\2\2\2\u01e5\u01e7\7\22\2\2\u01e6\u01e5\3\2\2"+
		"\2\u01e6\u01e7\3\2\2\2\u01e7\u01e9\3\2\2\2\u01e8\u01ea\7\u0080\2\2\u01e9"+
		"\u01e8\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01ed\3\2\2\2\u01eb\u01ec\7\u0085"+
		"\2\2\u01ec\u01ee\7\21\2\2\u01ed\u01eb\3\2\2\2\u01ed\u01ee\3\2\2\2\u01ee"+
		"\u01ef\3\2\2\2\u01ef\u01f1\5\20\t\2\u01f0\u01f2\7\20\2\2\u01f1\u01f0\3"+
		"\2\2\2\u01f1\u01f2\3\2\2\2\u01f2)\3\2\2\2\u01f3\u01f6\7\u0085\2\2\u01f4"+
		"\u01f5\7c\2\2\u01f5\u01f7\5\20\t\2\u01f6\u01f4\3\2\2\2\u01f6\u01f7\3\2"+
		"\2\2\u01f7+\3\2\2\2\u01f8\u01fc\5<\37\2\u01f9\u01fc\5\60\31\2\u01fa\u01fc"+
		"\5:\36\2\u01fb\u01f8\3\2\2\2\u01fb\u01f9\3\2\2\2\u01fb\u01fa\3\2\2\2\u01fc"+
		"-\3\2\2\2\u01fd\u01fe\7\t\2\2\u01fe\u01ff\5\20\t\2\u01ff\u0200\7\n\2\2"+
		"\u0200/\3\2\2\2\u0201\u0203\5\62\32\2\u0202\u0204\5\64\33\2\u0203\u0202"+
		"\3\2\2\2\u0203\u0204\3\2\2\2\u0204\61\3\2\2\2\u0205\u020a\5\u013c\u009f"+
		"\2\u0206\u0207\7\23\2\2\u0207\u0209\5\u013c\u009f\2\u0208\u0206\3\2\2"+
		"\2\u0209\u020c\3\2\2\2\u020a\u0208\3\2\2\2\u020a\u020b\3\2\2\2\u020b\63"+
		"\3\2\2\2\u020c\u020a\3\2\2\2\u020d\u0212\7\36\2\2\u020e\u0210\5\66\34"+
		"\2\u020f\u0211\7\16\2\2\u0210\u020f\3\2\2\2\u0210\u0211\3\2\2\2\u0211"+
		"\u0213\3\2\2\2\u0212\u020e\3\2\2\2\u0212\u0213\3\2\2\2\u0213\u0214\3\2"+
		"\2\2\u0214\u0215\7\37\2\2\u0215\65\3\2\2\2\u0216\u021b\58\35\2\u0217\u0218"+
		"\7\16\2\2\u0218\u021a\58\35\2\u0219\u0217\3\2\2\2\u021a\u021d\3\2\2\2"+
		"\u021b\u0219\3\2\2\2\u021b\u021c\3\2\2\2\u021c\67\3\2\2\2\u021d\u021b"+
		"\3\2\2\2\u021e\u0220\7\u0080\2\2\u021f\u021e\3\2\2\2\u021f\u0220\3\2\2"+
		"\2\u0220\u0221\3\2\2\2\u0221\u0222\5\20\t\2\u02229\3\2\2\2\u0223\u0225"+
		"\7\13\2\2\u0224\u0226\5l\67\2\u0225\u0224\3\2\2\2\u0225\u0226\3\2\2\2"+
		"\u0226\u0227\3\2\2\2\u0227\u0228\7\f\2\2\u0228;\3\2\2\2\u0229\u022a\7"+
		"T\2\2\u022a=\3\2\2\2\u022b\u022c\7B\2\2\u022c\u022d\5F$\2\u022d?\3\2\2"+
		"\2\u022e\u022f\7g\2\2\u022f\u0230\7\t\2\2\u0230\u0231\7\u0086\2\2\u0231"+
		"\u0234\7\n\2\2\u0232\u0233\7\23\2\2\u0233\u0235\5\60\31\2\u0234\u0232"+
		"\3\2\2\2\u0234\u0235\3\2\2\2\u0235A\3\2\2\2\u0236\u0238\7\u0083\2\2\u0237"+
		"\u0236\3\2\2\2\u0237\u0238\3\2\2\2\u0238\u023b\3\2\2\2\u0239\u023c\7T"+
		"\2\2\u023a\u023c\5D#\2\u023b\u0239\3\2\2\2\u023b\u023a\3\2\2\2\u023c\u023d"+
		"\3\2\2\2\u023d\u023e\7\177\2\2\u023e\u023f\5\24\13\2\u023fC\3\2\2\2\u0240"+
		"\u0241\5\u0138\u009d\2\u0241E\3\2\2\2\u0242\u0247\5\u013c\u009f\2\u0243"+
		"\u0244\7\23\2\2\u0244\u0246\5\u013c\u009f\2\u0245\u0243\3\2\2\2\u0246"+
		"\u0249\3\2\2\2\u0247\u0245\3\2\2\2\u0247\u0248\3\2\2\2\u0248G\3\2\2\2"+
		"\u0249\u0247\3\2\2\2\u024a\u024b\7\u0080\2\2\u024b\u024c\5\u013c\u009f"+
		"\2\u024cI\3\2\2\2\u024d\u024e\7v\2\2\u024e\u0250\5\u0138\u009d\2\u024f"+
		"\u0251\5L\'\2\u0250\u024f\3\2\2\2\u0250\u0251\3\2\2\2\u0251\u0252\3\2"+
		"\2\2\u0252\u0253\7\17\2\2\u0253\u0255\5\20\t\2\u0254\u0256\7\r\2\2\u0255"+
		"\u0254\3\2\2\2\u0255\u0256\3\2\2\2\u0256K\3\2\2\2\u0257\u0259\7\36\2\2"+
		"\u0258\u025a\5N(\2\u0259\u0258\3\2\2\2\u0259\u025a\3\2\2\2\u025a\u025b"+
		"\3\2\2\2\u025b\u025c\7\37\2\2\u025cM\3\2\2\2\u025d\u0262\5P)\2\u025e\u025f"+
		"\7\16\2\2\u025f\u0261\5P)\2\u0260\u025e\3\2\2\2\u0261\u0264\3\2\2\2\u0262"+
		"\u0260\3\2\2\2\u0262\u0263\3\2\2\2\u0263O\3\2\2\2\u0264\u0262\3\2\2\2"+
		"\u0265\u0267\5\u0138\u009d\2\u0266\u0268\5R*\2\u0267\u0266\3\2\2\2\u0267"+
		"\u0268\3\2\2\2\u0268\u026b\3\2\2\2\u0269\u026a\7\17\2\2\u026a\u026c\5"+
		"T+\2\u026b\u0269\3\2\2\2\u026b\u026c\3\2\2\2\u026cQ\3\2\2\2\u026d\u026e"+
		"\7c\2\2\u026e\u026f\5\20\t\2\u026fS\3\2\2\2\u0270\u0271\5\20\t\2\u0271"+
		"U\3\2\2\2\u0272\u0273\7|\2\2\u0273\u0274\5X-\2\u0274\u0275\5\n\6\2\u0275"+
		"W\3\2\2\2\u0276\u0277\t\3\2\2\u0277Y\3\2\2\2\u0278\u0279\7z\2\2\u0279"+
		"\u027a\5\\/\2\u027a\u027b\5\n\6\2\u027b[\3\2\2\2\u027c\u0281\5\u013c\u009f"+
		"\2\u027d\u027e\7\23\2\2\u027e\u0280\5\u013c\u009f\2\u027f\u027d\3\2\2"+
		"\2\u0280\u0283\3\2\2\2\u0281\u027f\3\2\2\2\u0281\u0282\3\2\2\2\u0282]"+
		"\3\2\2\2\u0283\u0281\3\2\2\2\u0284\u0286\5`\61\2\u0285\u0284\3\2\2\2\u0286"+
		"\u0287\3\2\2\2\u0287\u0285\3\2\2\2\u0287\u0288\3\2\2\2\u0288_\3\2\2\2"+
		"\u0289\u028c\7\u0084\2\2\u028a\u028d\5b\62\2\u028b\u028d\5d\63\2\u028c"+
		"\u028a\3\2\2\2\u028c\u028b\3\2\2\2\u028da\3\2\2\2\u028e\u028f\b\62\1\2"+
		"\u028f\u0295\7\u0085\2\2\u0290\u0291\7\t\2\2\u0291\u0292\5\u0114\u008b"+
		"\2\u0292\u0293\7\n\2\2\u0293\u0295\3\2\2\2\u0294\u028e\3\2\2\2\u0294\u0290"+
		"\3\2\2\2\u0295\u029b\3\2\2\2\u0296\u0297\f\4\2\2\u0297\u0298\7\23\2\2"+
		"\u0298\u029a\5\u0138\u009d\2\u0299\u0296\3\2\2\2\u029a\u029d\3\2\2\2\u029b"+
		"\u0299\3\2\2\2\u029b\u029c\3\2\2\2\u029cc\3\2\2\2\u029d\u029b\3\2\2\2"+
		"\u029e\u029f\5b\62\2\u029f\u02a0\5\u00ccg\2\u02a0e\3\2\2\2\u02a1\u02a2"+
		"\7l\2\2\u02a2\u02a4\5\u0138\u009d\2\u02a3\u02a5\5L\'\2\u02a4\u02a3\3\2"+
		"\2\2\u02a4\u02a5\3\2\2\2\u02a5\u02a7\3\2\2\2\u02a6\u02a8\5h\65\2\u02a7"+
		"\u02a6\3\2\2\2\u02a7\u02a8\3\2\2\2\u02a8\u02a9\3\2\2\2\u02a9\u02ab\7\13"+
		"\2\2\u02aa\u02ac\5l\67\2\u02ab\u02aa\3\2\2\2\u02ab\u02ac\3\2\2\2\u02ac"+
		"\u02ad\3\2\2\2\u02ad\u02af\7\f\2\2\u02ae\u02b0\7\r\2\2\u02af\u02ae\3\2"+
		"\2\2\u02af\u02b0\3\2\2\2\u02b0g\3\2\2\2\u02b1\u02b2\7c\2\2\u02b2\u02b3"+
		"\5j\66\2\u02b3i\3\2\2\2\u02b4\u02b9\5\60\31\2\u02b5\u02b6\7\16\2\2\u02b6"+
		"\u02b8\5\60\31\2\u02b7\u02b5\3\2\2\2\u02b8\u02bb\3\2\2\2\u02b9\u02b7\3"+
		"\2\2\2\u02b9\u02ba\3\2\2\2\u02bak\3\2\2\2\u02bb\u02b9\3\2\2\2\u02bc\u02be"+
		"\5n8\2\u02bd\u02bf\t\4\2\2\u02be\u02bd\3\2\2\2\u02be\u02bf\3\2\2\2\u02bf"+
		"m\3\2\2\2\u02c0\u02c9\5p9\2\u02c1\u02c5\b8\1\2\u02c2\u02c5\7\r\2\2\u02c3"+
		"\u02c5\7\16\2\2\u02c4\u02c1\3\2\2\2\u02c4\u02c2\3\2\2\2\u02c4\u02c3\3"+
		"\2\2\2\u02c5\u02c6\3\2\2\2\u02c6\u02c8\5p9\2\u02c7\u02c4\3\2\2\2\u02c8"+
		"\u02cb\3\2\2\2\u02c9\u02c7\3\2\2\2\u02c9\u02ca\3\2\2\2\u02cao\3\2\2\2"+
		"\u02cb\u02c9\3\2\2\2\u02cc\u02d4\5r:\2\u02cd\u02d4\5t;\2\u02ce\u02d4\5"+
		"v<\2\u02cf\u02d4\5\u00c6d\2\u02d0\u02d4\5\u00c8e\2\u02d1\u02d4\5z>\2\u02d2"+
		"\u02d4\5|?\2\u02d3\u02cc\3\2\2\2\u02d3\u02cd\3\2\2\2\u02d3\u02ce\3\2\2"+
		"\2\u02d3\u02cf\3\2\2\2\u02d3\u02d0\3\2\2\2\u02d3\u02d1\3\2\2\2\u02d3\u02d2"+
		"\3\2\2\2\u02d4q\3\2\2\2\u02d5\u02d7\7G\2\2\u02d6\u02d8\5L\'\2\u02d7\u02d6"+
		"\3\2\2\2\u02d7\u02d8\3\2\2\2\u02d8\u02d9\3\2\2\2\u02d9\u02db\5\u00a4S"+
		"\2\u02da\u02dc\5\16\b\2\u02db\u02da\3\2\2\2\u02db\u02dc\3\2\2\2\u02dc"+
		"s\3\2\2\2\u02dd\u02df\5L\'\2\u02de\u02dd\3\2\2\2\u02de\u02df\3\2\2\2\u02df"+
		"\u02e0\3\2\2\2\u02e0\u02e6\5\u00a4S\2\u02e1\u02e4\7\21\2\2\u02e2\u02e5"+
		"\5B\"\2\u02e3\u02e5\5\20\t\2\u02e4\u02e2\3\2\2\2\u02e4\u02e3\3\2\2\2\u02e5"+
		"\u02e7\3\2\2\2\u02e6\u02e1\3\2\2\2\u02e6\u02e7\3\2\2\2\u02e7u\3\2\2\2"+
		"\u02e8\u02ea\7^\2\2\u02e9\u02e8\3\2\2\2\u02e9\u02ea\3\2\2\2\u02ea\u02f1"+
		"\3\2\2\2\u02eb\u02ed\7\26\2\2\u02ec\u02eb\3\2\2\2\u02ec\u02ed\3\2\2\2"+
		"\u02ed\u02ee\3\2\2\2\u02ee\u02f2\7^\2\2\u02ef\u02f0\7\27\2\2\u02f0\u02f2"+
		"\7^\2\2\u02f1\u02ec\3\2\2\2\u02f1\u02ef\3\2\2\2\u02f1\u02f2\3\2\2\2\u02f2"+
		"\u02f3\3\2\2\2\u02f3\u02f4\7\7\2\2\u02f4\u02f5\5x=\2\u02f5\u02fc\7\b\2"+
		"\2\u02f6\u02f8\7\26\2\2\u02f7\u02f6\3\2\2\2\u02f7\u02f8\3\2\2\2\u02f8"+
		"\u02f9\3\2\2\2\u02f9\u02fd\7\20\2\2\u02fa\u02fb\7\27\2\2\u02fb\u02fd\7"+
		"\20\2\2\u02fc\u02f7\3\2\2\2\u02fc\u02fa\3\2\2\2\u02fc\u02fd\3\2\2\2\u02fd"+
		"\u02fe\3\2\2\2\u02fe\u02ff\5\16\b\2\u02ffw\3\2\2\2\u0300\u0301\5\u0138"+
		"\u009d\2\u0301\u0302\7\21\2\2\u0302\u0303\t\5\2\2\u0303\u0308\3\2\2\2"+
		"\u0304\u0305\7\u0085\2\2\u0305\u0306\7Z\2\2\u0306\u0308\5\20\t\2\u0307"+
		"\u0300\3\2\2\2\u0307\u0304\3\2\2\2\u0308y\3\2\2\2\u0309\u030b\5\u00c4"+
		"c\2\u030a\u030c\7\20\2\2\u030b\u030a\3\2\2\2\u030b\u030c\3\2\2\2\u030c"+
		"\u030d\3\2\2\2\u030d\u030e\5t;\2\u030e{\3\2\2\2\u030f\u0311\7^\2\2\u0310"+
		"\u030f\3\2\2\2\u0310\u0311\3\2\2\2\u0311\u0312\3\2\2\2\u0312\u0314\5\u00c4"+
		"c\2\u0313\u0315\7\20\2\2\u0314\u0313\3\2\2\2\u0314\u0315\3\2\2\2\u0315"+
		"\u0317\3\2\2\2\u0316\u0318\5\16\b\2\u0317\u0316\3\2\2\2\u0317\u0318\3"+
		"\2\2\2\u0318}\3\2\2\2\u0319\u031b\7e\2\2\u031a\u0319\3\2\2\2\u031a\u031b"+
		"\3\2\2\2\u031b\u031c\3\2\2\2\u031c\u031d\7b\2\2\u031d\u031e\5\u0136\u009c"+
		"\2\u031e\u0320\7\13\2\2\u031f\u0321\5\u0080A\2\u0320\u031f\3\2\2\2\u0320"+
		"\u0321\3\2\2\2\u0321\u0322\3\2\2\2\u0322\u0324\7\f\2\2\u0323\u0325\7\r"+
		"\2\2\u0324\u0323\3\2\2\2\u0324\u0325\3\2\2\2\u0325\177\3\2\2\2\u0326\u0328"+
		"\5\u0082B\2\u0327\u0329\7\16\2\2\u0328\u0327\3\2\2\2\u0328\u0329\3\2\2"+
		"\2\u0329\u0081\3\2\2\2\u032a\u032f\5\u0084C\2\u032b\u032c\7\16\2\2\u032c"+
		"\u032e\5\u0084C\2\u032d\u032b\3\2\2\2\u032e\u0331\3\2\2\2\u032f\u032d"+
		"\3\2\2\2\u032f\u0330\3\2\2\2\u0330\u0083\3\2\2\2\u0331\u032f\3\2\2\2\u0332"+
		"\u0335\5\u00c4c\2\u0333\u0334\7\17\2\2\u0334\u0336\5\u0114\u008b\2\u0335"+
		"\u0333\3\2\2\2\u0335\u0336\3\2\2\2\u0336\u0085\3\2\2\2\u0337\u0339\7S"+
		"\2\2\u0338\u033a\7\32\2\2\u0339\u0338\3\2\2\2\u0339\u033a\3\2\2\2\u033a"+
		"\u033b\3\2\2\2\u033b\u033c\5\u0138\u009d\2\u033c\u033e\5t;\2\u033d\u033f"+
		"\5\n\6\2\u033e\u033d\3\2\2\2\u033e\u033f\3\2\2\2\u033f\u0341\3\2\2\2\u0340"+
		"\u0342\7\r\2\2\u0341\u0340\3\2\2\2\u0341\u0342\3\2\2\2\u0342\u0087\3\2"+
		"\2\2\u0343\u0345\7~\2\2\u0344\u0343\3\2\2\2\u0344\u0345\3\2\2\2\u0345"+
		"\u0346\3\2\2\2\u0346\u0347\7a\2\2\u0347\u0349\5\u0136\u009c\2\u0348\u034a"+
		"\5L\'\2\u0349\u0348\3\2\2\2\u0349\u034a\3\2\2\2\u034a\u034b\3\2\2\2\u034b"+
		"\u034c\5\u008aF\2\u034c\u034e\5\u0090I\2\u034d\u034f\7\r\2\2\u034e\u034d"+
		"\3\2\2\2\u034e\u034f\3\2\2\2\u034f\u0089\3\2\2\2\u0350\u0352\5\u008cG"+
		"\2\u0351\u0350\3\2\2\2\u0351\u0352\3\2\2\2\u0352\u0354\3\2\2\2\u0353\u0355"+
		"\5\u008eH\2\u0354\u0353\3\2\2\2\u0354\u0355\3\2\2\2\u0355\u008b\3\2\2"+
		"\2\u0356\u0357\7c\2\2\u0357\u0358\5\60\31\2\u0358\u008d\3\2\2\2\u0359"+
		"\u035a\7h\2\2\u035a\u035b\5j\66\2\u035b\u008f\3\2\2\2\u035c\u035e\7\13"+
		"\2\2\u035d\u035f\5\u0092J\2\u035e\u035d\3\2\2\2\u035e\u035f\3\2\2\2\u035f"+
		"\u0361\3\2\2\2\u0360\u0362\7\r\2\2\u0361\u0360\3\2\2\2\u0361\u0362\3\2"+
		"\2\2\u0362\u0363\3\2\2\2\u0363\u0364\7\f\2\2\u0364\u0091\3\2\2\2\u0365"+
		"\u036d\5\u0094K\2\u0366\u0369\bJ\1\2\u0367\u0369\7\r\2\2\u0368\u0366\3"+
		"\2\2\2\u0368\u0367\3\2\2\2\u0369\u036a\3\2\2\2\u036a\u036c\5\u0094K\2"+
		"\u036b\u0368\3\2\2\2\u036c\u036f\3\2\2\2\u036d\u036b\3\2\2\2\u036d\u036e"+
		"\3\2\2\2\u036e\u0093\3\2\2\2\u036f\u036d\3\2\2\2\u0370\u0377\5\u0096L"+
		"\2\u0371\u0377\5v<\2\u0372\u0374\5^\60\2\u0373\u0372\3\2\2\2\u0373\u0374"+
		"\3\2\2\2\u0374\u0375\3\2\2\2\u0375\u0377\5\u0098M\2\u0376\u0370\3\2\2"+
		"\2\u0376\u0371\3\2\2\2\u0376\u0373\3\2\2\2\u0377\u0095\3\2\2\2\u0378\u037a"+
		"\5\u00b2Z\2\u0379\u0378\3\2\2\2\u0379\u037a\3\2\2\2\u037a\u037b\3\2\2"+
		"\2\u037b\u037c\7y\2\2\u037c\u037e\5\u00a4S\2\u037d\u037f\5\n\6\2\u037e"+
		"\u037d\3\2\2\2\u037e\u037f\3\2\2\2\u037f\u0097\3\2\2\2\u0380\u0383\5\u009a"+
		"N\2\u0381\u0383\5\u009cO\2\u0382\u0380\3\2\2\2\u0382\u0381\3\2\2\2\u0383"+
		"\u0099\3\2\2\2\u0384\u0388\7~\2\2\u0385\u0386\7\u0085\2\2\u0386\u0389"+
		"\5t;\2\u0387\u0389\5\u00e2r\2\u0388\u0385\3\2\2\2\u0388\u0387\3\2\2\2"+
		"\u0389\u038a\3\2\2\2\u038a\u038b\5\u0146\u00a4\2\u038b\u009b\3\2\2\2\u038c"+
		"\u0390\5\u009eP\2\u038d\u0391\5\u00c6d\2\u038e\u0391\5\u00c8e\2\u038f"+
		"\u0391\5\u00a0Q\2\u0390\u038d\3\2\2\2\u0390\u038e\3\2\2\2\u0390\u038f"+
		"\3\2\2\2\u0391\u009d\3\2\2\2\u0392\u0394\7_\2\2\u0393\u0392\3\2\2\2\u0393"+
		"\u0394\3\2\2\2\u0394\u0396\3\2\2\2\u0395\u0397\5\u00b2Z\2\u0396\u0395"+
		"\3\2\2\2\u0396\u0397\3\2\2\2\u0397\u0399\3\2\2\2\u0398\u039a\7o\2\2\u0399"+
		"\u0398\3\2\2\2\u0399\u039a\3\2\2\2\u039a\u009f\3\2\2\2\u039b\u039d\7^"+
		"\2\2\u039c\u039b\3\2\2\2\u039c\u039d\3\2\2\2\u039d\u039e\3\2\2\2\u039e"+
		"\u03a0\5\u00c4c\2\u039f\u03a1\7\20\2\2\u03a0\u039f\3\2\2\2\u03a0\u03a1"+
		"\3\2\2\2\u03a1\u03ac\3\2\2\2\u03a2\u03a4\5\16\b\2\u03a3\u03a2\3\2\2\2"+
		"\u03a3\u03a4\3\2\2\2\u03a4\u03a6\3\2\2\2\u03a5\u03a7\5\u00a2R\2\u03a6"+
		"\u03a5\3\2\2\2\u03a6\u03a7\3\2\2\2\u03a7\u03ad\3\2\2\2\u03a8\u03aa\5t"+
		";\2\u03a9\u03ab\5\n\6\2\u03aa\u03a9\3\2\2\2\u03aa\u03ab\3\2\2\2\u03ab"+
		"\u03ad\3\2\2\2\u03ac\u03a3\3\2\2\2\u03ac\u03a8\3\2\2\2\u03ad\u00a1\3\2"+
		"\2\2\u03ae\u03af\7\17\2\2\u03af\u03b0\5\u0114\u008b\2\u03b0\u00a3\3\2"+
		"\2\2\u03b1\u03b7\7\t\2\2\u03b2\u03b4\7T\2\2\u03b3\u03b5\5\16\b\2\u03b4"+
		"\u03b3\3\2\2\2\u03b4\u03b5\3\2\2\2\u03b5\u03b6\3\2\2\2\u03b6\u03b8\7\16"+
		"\2\2\u03b7\u03b2\3\2\2\2\u03b7\u03b8\3\2\2\2\u03b8\u03ba\3\2\2\2\u03b9"+
		"\u03bb\5\u00a6T\2\u03ba\u03b9\3\2\2\2\u03ba\u03bb\3\2\2\2\u03bb\u03bc"+
		"\3\2\2\2\u03bc\u03bd\7\n\2\2\u03bd\u00a5\3\2\2\2\u03be\u03c0\5\u00a8U"+
		"\2\u03bf\u03c1\7\16\2\2\u03c0\u03bf\3\2\2\2\u03c0\u03c1\3\2\2\2\u03c1"+
		"\u00a7\3\2\2\2\u03c2\u03d0\5\u00aaV\2\u03c3\u03c8\5\u00acW\2\u03c4\u03c5"+
		"\7\16\2\2\u03c5\u03c7\5\u00acW\2\u03c6\u03c4\3\2\2\2\u03c7\u03ca\3\2\2"+
		"\2\u03c8\u03c6\3\2\2\2\u03c8\u03c9\3\2\2\2\u03c9\u03cd\3\2\2\2\u03ca\u03c8"+
		"\3\2\2\2\u03cb\u03cc\7\16\2\2\u03cc\u03ce\5\u00aaV\2\u03cd\u03cb\3\2\2"+
		"\2\u03cd\u03ce\3\2\2\2\u03ce\u03d0\3\2\2\2\u03cf\u03c2\3\2\2\2\u03cf\u03c3"+
		"\3\2\2\2\u03d0\u00a9\3\2\2\2\u03d1\u03d2\7\22\2\2\u03d2\u03d4\5\u00b4"+
		"[\2\u03d3\u03d5\5\16\b\2\u03d4\u03d3\3\2\2\2\u03d4\u03d5\3\2\2\2\u03d5"+
		"\u00ab\3\2\2\2\u03d6\u03d9\5\u00aeX\2\u03d7\u03d9\5\u00b0Y\2\u03d8\u03d6"+
		"\3\2\2\2\u03d8\u03d7\3\2\2\2\u03d9\u00ad\3\2\2\2\u03da\u03dc\5^\60\2\u03db"+
		"\u03da\3\2\2\2\u03db\u03dc\3\2\2\2\u03dc\u03de\3\2\2\2\u03dd\u03df\5\u00b2"+
		"Z\2\u03de\u03dd\3\2\2\2\u03de\u03df\3\2\2\2\u03df\u03e0\3\2\2\2\u03e0"+
		"\u03e2\5\u00b4[\2\u03e1\u03e3\5\16\b\2\u03e2\u03e1\3\2\2\2\u03e2\u03e3"+
		"\3\2\2\2\u03e3\u00af\3\2\2\2\u03e4\u03e6\5^\60\2\u03e5\u03e4\3\2\2\2\u03e5"+
		"\u03e6\3\2\2\2\u03e6\u03e8\3\2\2\2\u03e7\u03e9\5\u00b2Z\2\u03e8\u03e7"+
		"\3\2\2\2\u03e8\u03e9\3\2\2\2\u03e9\u03ea\3\2\2\2\u03ea\u03f3\5\u00b4["+
		"\2\u03eb\u03ed\7\20\2\2\u03ec\u03ee\5\16\b\2\u03ed\u03ec\3\2\2\2\u03ed"+
		"\u03ee\3\2\2\2\u03ee\u03f4\3\2\2\2\u03ef\u03f1\5\16\b\2\u03f0\u03ef\3"+
		"\2\2\2\u03f0\u03f1\3\2\2\2\u03f1\u03f2\3\2\2\2\u03f2\u03f4\5\u00a2R\2"+
		"\u03f3\u03eb\3\2\2\2\u03f3\u03f0\3\2\2\2\u03f4\u00b1\3\2\2\2\u03f5\u03f6"+
		"\t\6\2\2\u03f6\u00b3\3\2\2\2\u03f7\u03fa\5\u0138\u009d\2\u03f8\u03fa\5"+
		"\u00b6\\\2\u03f9\u03f7\3\2\2\2\u03f9\u03f8\3\2\2\2\u03fa\u00b5\3\2\2\2"+
		"\u03fb\u03fe\5\u00b8]\2\u03fc\u03fe\5\u00c0a\2\u03fd\u03fb\3\2\2\2\u03fd"+
		"\u03fc\3\2\2\2\u03fe\u00b7\3\2\2\2\u03ff\u0401\7\7\2\2\u0400\u0402\5\u00ba"+
		"^\2\u0401\u0400\3\2\2\2\u0401\u0402\3\2\2\2\u0402\u0403\3\2\2\2\u0403"+
		"\u0404\7\b\2\2\u0404\u00b9\3\2\2\2\u0405\u040e\5\u00bc_\2\u0406\u0408"+
		"\7\16\2\2\u0407\u0406\3\2\2\2\u0408\u0409\3\2\2\2\u0409\u0407\3\2\2\2"+
		"\u0409\u040a\3\2\2\2\u040a\u040b\3\2\2\2\u040b\u040d\5\u00bc_\2\u040c"+
		"\u0407\3\2\2\2\u040d\u0410\3\2\2\2\u040e\u040c\3\2\2\2\u040e\u040f\3\2"+
		"\2\2\u040f\u0412\3\2\2\2\u0410\u040e\3\2\2\2\u0411\u0413\7\16\2\2\u0412"+
		"\u0411\3\2\2\2\u0412\u0413\3\2\2\2\u0413\u00bb\3\2\2\2\u0414\u0416\7\22"+
		"\2\2\u0415\u0414\3\2\2\2\u0415\u0416\3\2\2\2\u0416\u0417\3\2\2\2\u0417"+
		"\u0418\5\u00be`\2\u0418\u00bd\3\2\2\2\u0419\u041c\5\u00b6\\\2\u041a\u041c"+
		"\7\u0085\2\2\u041b\u0419\3\2\2\2\u041b\u041a\3\2\2\2\u041c\u00bf\3\2\2"+
		"\2\u041d\u0429\7\13\2\2\u041e\u0423\5\u00c2b\2\u041f\u0420\7\16\2\2\u0420"+
		"\u0422\5\u00c2b\2\u0421\u041f\3\2\2\2\u0422\u0425\3\2\2\2\u0423\u0421"+
		"\3\2\2\2\u0423\u0424\3\2\2\2\u0424\u0427\3\2\2\2\u0425\u0423\3\2\2\2\u0426"+
		"\u0428\7\16\2\2\u0427\u0426\3\2\2\2\u0427\u0428\3\2\2\2\u0428\u042a\3"+
		"\2\2\2\u0429\u041e\3\2\2\2\u0429\u042a\3\2\2\2\u042a\u042b\3\2\2\2\u042b"+
		"\u042c\7\f\2\2\u042c\u00c1\3\2\2\2\u042d\u0431\5\u00c4c\2\u042e\u042f"+
		"\7\21\2\2\u042f\u0432\5\u0136\u009c\2\u0430\u0432\5\u00b6\\\2\u0431\u042e"+
		"\3\2\2\2\u0431\u0430\3\2\2\2\u0431\u0432\3\2\2\2\u0432\u0435\3\2\2\2\u0433"+
		"\u0434\7\17\2\2\u0434\u0436\5\u0114\u008b\2\u0435\u0433\3\2\2\2\u0435"+
		"\u0436\3\2\2\2\u0436\u043c\3\2\2\2\u0437\u043c\5\u00c6d\2\u0438\u043c"+
		"\5\u00c8e\2\u0439\u043c\5\u00caf\2\u043a\u043c\5\u00aaV\2\u043b\u042d"+
		"\3\2\2\2\u043b\u0437\3\2\2\2\u043b\u0438\3\2\2\2\u043b\u0439\3\2\2\2\u043b"+
		"\u043a\3\2\2\2\u043c\u00c3\3\2\2\2\u043d\u044a\7\u0086\2\2\u043e\u044a"+
		"\5\u0134\u009b\2\u043f\u0445\7\7\2\2\u0440\u0441\5\u0138\u009d\2\u0441"+
		"\u0442\7\23\2\2\u0442\u0443\5\u0138\u009d\2\u0443\u0446\3\2\2\2\u0444"+
		"\u0446\7\u0086\2\2\u0445\u0440\3\2\2\2\u0445\u0444\3\2\2\2\u0446\u0447"+
		"\3\2\2\2\u0447\u044a\7\b\2\2\u0448\u044a\5\u0138\u009d\2\u0449\u043d\3"+
		"\2\2\2\u0449\u043e\3\2\2\2\u0449\u043f\3\2\2\2\u0449\u0448\3\2\2\2\u044a"+
		"\u00c5\3\2\2\2\u044b\u044c\5\u0142\u00a2\2\u044c\u044d\7\t\2\2\u044d\u044f"+
		"\7\n\2\2\u044e\u0450\5\16\b\2\u044f\u044e\3\2\2\2\u044f\u0450\3\2\2\2"+
		"\u0450\u0452\3\2\2\2\u0451\u0453\5\n\6\2\u0452\u0451\3\2\2\2\u0452\u0453"+
		"\3\2\2\2\u0453\u00c7\3\2\2\2\u0454\u0455\5\u0144\u00a3\2\u0455\u0458\7"+
		"\t\2\2\u0456\u0459\7\u0085\2\2\u0457\u0459\5\u00b6\\\2\u0458\u0456\3\2"+
		"\2\2\u0458\u0457\3\2\2\2\u0459\u045b\3\2\2\2\u045a\u045c\5\16\b\2\u045b"+
		"\u045a\3\2\2\2\u045b\u045c\3\2\2\2\u045c\u045d\3\2\2\2\u045d\u045f\7\n"+
		"\2\2\u045e\u0460\5\n\6\2\u045f\u045e\3\2\2\2\u045f\u0460\3\2\2\2\u0460"+
		"\u00c9\3\2\2\2\u0461\u0463\7\32\2\2\u0462\u0461\3\2\2\2\u0462\u0463\3"+
		"\2\2\2\u0463\u0464\3\2\2\2\u0464\u0465\7\u0085\2\2\u0465\u0466\5\u00a4"+
		"S\2\u0466\u0467\5\n\6\2\u0467\u00cb\3\2\2\2\u0468\u046d\7\t\2\2\u0469"+
		"\u046b\5\u00ceh\2\u046a\u046c\7\16\2\2\u046b\u046a\3\2\2\2\u046b\u046c"+
		"\3\2\2\2\u046c\u046e\3\2\2\2\u046d\u0469\3\2\2\2\u046d\u046e\3\2\2\2\u046e"+
		"\u046f\3\2\2\2\u046f\u0470\7\n\2\2\u0470\u00cd\3\2\2\2\u0471\u0476\5\u00d0"+
		"i\2\u0472\u0473\7\16\2\2\u0473\u0475\5\u00d0i\2\u0474\u0472\3\2\2\2\u0475"+
		"\u0478\3\2\2\2\u0476\u0474\3\2\2\2\u0476\u0477\3\2\2\2\u0477\u00cf\3\2"+
		"\2\2\u0478\u0476\3\2\2\2\u0479\u047b\7\22\2\2\u047a\u0479\3\2\2\2\u047a"+
		"\u047b\3\2\2\2\u047b\u047e\3\2\2\2\u047c\u047f\5\u0114\u008b\2\u047d\u047f"+
		"\7\u0085\2\2\u047e\u047c\3\2\2\2\u047e\u047d\3\2\2\2\u047f\u00d1\3\2\2"+
		"\2\u0480\u0484\7g\2\2\u0481\u0485\5\u00d4k\2\u0482\u0485\5\u00dan\2\u0483"+
		"\u0485\7\u0086\2\2\u0484\u0481\3\2\2\2\u0484\u0482\3\2\2\2\u0484\u0483"+
		"\3\2\2\2\u0485\u0486\3\2\2\2\u0486\u0487\5\u0146\u00a4\2\u0487\u00d3\3"+
		"\2\2\2\u0488\u048a\7v\2\2\u0489\u0488\3\2\2\2\u0489\u048a\3\2\2\2\u048a"+
		"\u0490\3\2\2\2\u048b\u048c\7\32\2\2\u048c\u048d\7\\\2\2\u048d\u0491\5"+
		"\u0138\u009d\2\u048e\u0491\5\u0138\u009d\2\u048f\u0491\5\u00d6l\2\u0490"+
		"\u048b\3\2\2\2\u0490\u048e\3\2\2\2\u0490\u048f\3\2\2\2\u0491\u0492\3\2"+
		"\2\2\u0492\u0493\7]\2\2\u0493\u0494\7\u0086\2\2\u0494\u00d5\3\2\2\2\u0495"+
		"\u0496\5\u0138\u009d\2\u0496\u0497\7\16\2\2\u0497\u0499\3\2\2\2\u0498"+
		"\u0495\3\2\2\2\u0498\u0499\3\2\2\2\u0499\u049a\3\2\2\2\u049a\u049b\7\13"+
		"\2\2\u049b\u04a0\5\u00d8m\2\u049c\u049d\7\16\2\2\u049d\u049f\5\u00d8m"+
		"\2\u049e\u049c\3\2\2\2\u049f\u04a2\3\2\2\2\u04a0\u049e\3\2\2\2\u04a0\u04a1"+
		"\3\2\2\2\u04a1\u04a4\3\2\2\2\u04a2\u04a0\3\2\2\2\u04a3\u04a5\7\16\2\2"+
		"\u04a4\u04a3\3\2\2\2\u04a4\u04a5\3\2\2\2\u04a5\u04a6\3\2\2\2\u04a6\u04a7"+
		"\7\f\2\2\u04a7\u00d7\3\2\2\2\u04a8\u04ab\5\u0138\u009d\2\u04a9\u04aa\7"+
		"\\\2\2\u04aa\u04ac\5\u0138\u009d\2\u04ab\u04a9\3\2\2\2\u04ab\u04ac\3\2"+
		"\2\2\u04ac\u00d9\3\2\2\2\u04ad\u04ae\7\u0085\2\2\u04ae\u04b4\7\17\2\2"+
		"\u04af\u04b5\5\\/\2\u04b0\u04b1\7{\2\2\u04b1\u04b2\7\t\2\2\u04b2\u04b3"+
		"\7\u0086\2\2\u04b3\u04b5\7\n\2\2\u04b4\u04af\3\2\2\2\u04b4\u04b0\3\2\2"+
		"\2\u04b5\u00db\3\2\2\2\u04b6\u04b7\7f\2\2\u04b7\u04b8\5\u00dep\2\u04b8"+
		"\u00dd\3\2\2\2\u04b9\u04bb\7V\2\2\u04ba\u04b9\3\2\2\2\u04ba\u04bb\3\2"+
		"\2\2\u04bb\u04bc\3\2\2\2\u04bc\u04db\5\6\4\2\u04bd\u04be\7V\2\2\u04be"+
		"\u04db\5\u0138\u009d\2\u04bf\u04c2\5\u00e0q\2\u04c0\u04c1\7]\2\2\u04c1"+
		"\u04c3\7\u0086\2\2\u04c2\u04c0\3\2\2\2\u04c2\u04c3\3\2\2\2\u04c3\u04db"+
		"\3\2\2\2\u04c4\u04c7\7\32\2\2\u04c5\u04c6\7\\\2\2\u04c6\u04c8\5\u0138"+
		"\u009d\2\u04c7\u04c5\3\2\2\2\u04c7\u04c8\3\2\2\2\u04c8\u04c9\3\2\2\2\u04c9"+
		"\u04ca\7]\2\2\u04ca\u04db\7\u0086\2\2\u04cb\u04cc\7\\\2\2\u04cc\u04cd"+
		"\7z\2\2\u04cd\u04ce\5\u0138\u009d\2\u04ce\u04cf\5\u0146\u00a4\2\u04cf"+
		"\u04db\3\2\2\2\u04d0\u04d1\7\17\2\2\u04d1\u04d2\5\\/\2\u04d2\u04d3\5\u0146"+
		"\u00a4\2\u04d3\u04db\3\2\2\2\u04d4\u04d5\7g\2\2\u04d5\u04d6\5\u0138\u009d"+
		"\2\u04d6\u04d7\7\17\2\2\u04d7\u04d8\5\\/\2\u04d8\u04d9\5\u0146\u00a4\2"+
		"\u04d9\u04db\3\2\2\2\u04da\u04ba\3\2\2\2\u04da\u04bd\3\2\2\2\u04da\u04bf"+
		"\3\2\2\2\u04da\u04c4\3\2\2\2\u04da\u04cb\3\2\2\2\u04da\u04d0\3\2\2\2\u04da"+
		"\u04d4\3\2\2\2\u04db\u00df\3\2\2\2\u04dc\u04dd\7\13\2\2\u04dd\u04e2\5"+
		"\u00d8m\2\u04de\u04df\7\16\2\2\u04df\u04e1\5\u00d8m\2\u04e0\u04de\3\2"+
		"\2\2\u04e1\u04e4\3\2\2\2\u04e2\u04e0\3\2\2\2\u04e2\u04e3\3\2\2\2\u04e3"+
		"\u04e6\3\2\2\2\u04e4\u04e2\3\2\2\2\u04e5\u04e7\7\16\2\2\u04e6\u04e5\3"+
		"\2\2\2\u04e6\u04e7\3\2\2\2\u04e7\u04e8\3\2\2\2\u04e8\u04e9\7\f\2\2\u04e9"+
		"\u00e1\3\2\2\2\u04ea\u04ec\5\u00b2Z\2\u04eb\u04ea\3\2\2\2\u04eb\u04ec"+
		"\3\2\2\2\u04ec\u04ee\3\2\2\2\u04ed\u04ef\7^\2\2\u04ee\u04ed\3\2\2\2\u04ee"+
		"\u04ef\3\2\2\2\u04ef\u04f0\3\2\2\2\u04f0\u04f3\5\u00e4s\2\u04f1\u04f4"+
		"\5\u00e6t\2\u04f2\u04f4\5\u00e8u\2\u04f3\u04f1\3\2\2\2\u04f3\u04f2\3\2"+
		"\2\2\u04f4\u04f6\3\2\2\2\u04f5\u04f7\7\r\2\2\u04f6\u04f5\3\2\2\2\u04f6"+
		"\u04f7\3\2\2\2\u04f7\u00e3\3\2\2\2\u04f8\u04f9\t\7\2\2\u04f9\u00e5\3\2"+
		"\2\2\u04fa\u04fc\5\u00b6\\\2\u04fb\u04fd\5\16\b\2\u04fc\u04fb\3\2\2\2"+
		"\u04fc\u04fd\3\2\2\2\u04fd\u04ff\3\2\2\2\u04fe\u0500\5\u00a2R\2\u04ff"+
		"\u04fe\3\2\2\2\u04ff\u0500\3\2\2\2\u0500\u00e7\3\2\2\2\u0501\u0506\5\u00ea"+
		"v\2\u0502\u0503\7\16\2\2\u0503\u0505\5\u00eav\2\u0504\u0502\3\2\2\2\u0505"+
		"\u0508\3\2\2\2\u0506\u0504\3\2\2\2\u0506\u0507\3\2\2\2\u0507\u00e9\3\2"+
		"\2\2\u0508\u0506\3\2\2\2\u0509\u050b\5\u0138\u009d\2\u050a\u050c\5\16"+
		"\b\2\u050b\u050a\3\2\2\2\u050b\u050c\3\2\2\2\u050c\u0512\3\2\2\2\u050d"+
		"\u050f\7\17\2\2\u050e\u0510\5L\'\2\u050f\u050e\3\2\2\2\u050f\u0510\3\2"+
		"\2\2\u0510\u0511\3\2\2\2\u0511\u0513\5\u0114\u008b\2\u0512\u050d\3\2\2"+
		"\2\u0512\u0513\3\2\2\2\u0513\u00eb\3\2\2\2\u0514\u0515\7P\2\2\u0515\u0516"+
		"\7\t\2\2\u0516\u0517\5\u0112\u008a\2\u0517\u0518\7\n\2\2\u0518\u0519\5"+
		"\u00eex\2\u0519\u00ed\3\2\2\2\u051a\u051c\7\13\2\2\u051b\u051d\5\u00f0"+
		"y\2\u051c\u051b\3\2\2\2\u051c\u051d\3\2\2\2\u051d\u0522\3\2\2\2\u051e"+
		"\u0520\5\u00f4{\2\u051f\u0521\5\u00f0y\2\u0520\u051f\3\2\2\2\u0520\u0521"+
		"\3\2\2\2\u0521\u0523\3\2\2\2\u0522\u051e\3\2\2\2\u0522\u0523\3\2\2\2\u0523"+
		"\u0524\3\2\2\2\u0524\u0525\7\f\2\2\u0525\u00ef\3\2\2\2\u0526\u0528\5\u00f2"+
		"z\2\u0527\u0526\3\2\2\2\u0528\u0529\3\2\2\2\u0529\u0527\3\2\2\2\u0529"+
		"\u052a\3\2\2\2\u052a\u00f1\3\2\2\2\u052b\u052c\7E\2\2\u052c\u052d\5\u0112"+
		"\u008a\2\u052d\u052f\7\21\2\2\u052e\u0530\5\f\7\2\u052f\u052e\3\2\2\2"+
		"\u052f\u0530\3\2\2\2\u0530\u00f3\3\2\2\2\u0531\u0532\7V\2\2\u0532\u0534"+
		"\7\21\2\2\u0533\u0535\5\f\7\2\u0534\u0533\3\2\2\2\u0534\u0535\3\2\2\2"+
		"\u0535\u00f5\3\2\2\2\u0536\u0537\7[\2\2\u0537\u053d\5\n\6\2\u0538\u053a"+
		"\5\u00f8}\2\u0539\u053b\5\u00fa~\2\u053a\u0539\3\2\2\2\u053a\u053b\3\2"+
		"\2\2\u053b\u053e\3\2\2\2\u053c\u053e\5\u00fa~\2\u053d\u0538\3\2\2\2\u053d"+
		"\u053c\3\2\2\2\u053e\u00f7\3\2\2\2\u053f\u0540\7J\2\2\u0540\u0541\7\t"+
		"\2\2\u0541\u0542\7\u0085\2\2\u0542\u0543\7\n\2\2\u0543\u0544\5\n\6\2\u0544"+
		"\u00f9\3\2\2\2\u0545\u0546\7K\2\2\u0546\u0547\5\n\6\2\u0547\u00fb\3\2"+
		"\2\2\u0548\u0549\7\r\2\2\u0549\u00fd\3\2\2\2\u054a\u054b\7W\2\2\u054b"+
		"\u054c\7\t\2\2\u054c\u054d\5\u0112\u008a\2\u054d\u054e\7\n\2\2\u054e\u0551"+
		"\5\4\3\2\u054f\u0550\7F\2\2\u0550\u0552\5\4\3\2\u0551\u054f\3\2\2\2\u0551"+
		"\u0552\3\2\2\2\u0552\u00ff\3\2\2\2\u0553\u0554\7@\2\2\u0554\u0555\5\4"+
		"\3\2\u0555\u0556\7Q\2\2\u0556\u0557\7\t\2\2\u0557\u0558\5\u0112\u008a"+
		"\2\u0558\u0559\7\n\2\2\u0559\u055a\5\u0146\u00a4\2\u055a\u0599\3\2\2\2"+
		"\u055b\u055c\7Q\2\2\u055c\u055d\7\t\2\2\u055d\u055e\5\u0112\u008a\2\u055e"+
		"\u055f\7\n\2\2\u055f\u0560\5\4\3\2\u0560\u0599\3\2\2\2\u0561\u0562\7O"+
		"\2\2\u0562\u0564\7\t\2\2\u0563\u0565\5\u0112\u008a\2\u0564\u0563\3\2\2"+
		"\2\u0564\u0565\3\2\2\2\u0565\u0566\3\2\2\2\u0566\u0568\7\r\2\2\u0567\u0569"+
		"\5\u0112\u008a\2\u0568\u0567\3\2\2\2\u0568\u0569\3\2\2\2\u0569\u056a\3"+
		"\2\2\2\u056a\u056c\7\r\2\2\u056b\u056d\5\u0112\u008a\2\u056c\u056b\3\2"+
		"\2\2\u056c\u056d\3\2\2\2\u056d\u056e\3\2\2\2\u056e\u056f\7\n\2\2\u056f"+
		"\u0599\5\4\3\2\u0570\u0571\7O\2\2\u0571\u0572\7\t\2\2\u0572\u0573\5\u00e4"+
		"s\2\u0573\u0574\5\u00e8u\2\u0574\u0576\7\r\2\2\u0575\u0577\5\u0112\u008a"+
		"\2\u0576\u0575\3\2\2\2\u0576\u0577\3\2\2\2\u0577\u0578\3\2\2\2\u0578\u057a"+
		"\7\r\2\2\u0579\u057b\5\u0112\u008a\2\u057a\u0579\3\2\2\2\u057a\u057b\3"+
		"\2\2\2\u057b\u057c\3\2\2\2\u057c\u057d\7\n\2\2\u057d\u057e\5\4\3\2\u057e"+
		"\u0599\3\2\2\2\u057f\u0580\7O\2\2\u0580\u0581\7\t\2\2\u0581\u0585\5\u0114"+
		"\u008b\2\u0582\u0586\7Z\2\2\u0583\u0584\7\u0085\2\2\u0584\u0586\6\u0081"+
		"\3\2\u0585\u0582\3\2\2\2\u0585\u0583\3\2\2\2\u0586\u0587\3\2\2\2\u0587"+
		"\u0588\5\u0112\u008a\2\u0588\u0589\7\n\2\2\u0589\u058a\5\4\3\2\u058a\u0599"+
		"\3\2\2\2\u058b\u058c\7O\2\2\u058c\u058d\7\t\2\2\u058d\u058e\5\u00e4s\2"+
		"\u058e\u0592\5\u00eav\2\u058f\u0593\7Z\2\2\u0590\u0591\7\u0085\2\2\u0591"+
		"\u0593\6\u0081\4\2\u0592\u058f\3\2\2\2\u0592\u0590\3\2\2\2\u0593\u0594"+
		"\3\2\2\2\u0594\u0595\5\u0112\u008a\2\u0595\u0596\7\n\2\2\u0596\u0597\5"+
		"\4\3\2\u0597\u0599\3\2\2\2\u0598\u0553\3\2\2\2\u0598\u055b\3\2\2\2\u0598"+
		"\u0561\3\2\2\2\u0598\u0570\3\2\2\2\u0598\u057f\3\2\2\2\u0598\u058b\3\2"+
		"\2\2\u0599\u0101\3\2\2\2\u059a\u059d\7N\2\2\u059b\u059c\6\u0082\5\2\u059c"+
		"\u059e\7\u0085\2\2\u059d\u059b\3\2\2\2\u059d\u059e\3\2\2\2\u059e\u059f"+
		"\3\2\2\2\u059f\u05a0\5\u0146\u00a4\2\u05a0\u0103\3\2\2\2\u05a1\u05a4\7"+
		"?\2\2\u05a2\u05a3\6\u0083\6\2\u05a3\u05a5\7\u0085\2\2\u05a4\u05a2\3\2"+
		"\2\2\u05a4\u05a5\3\2\2\2\u05a5\u05a6\3\2\2\2\u05a6\u05a7\5\u0146\u00a4"+
		"\2\u05a7\u0105\3\2\2\2\u05a8\u05ab\7L\2\2\u05a9\u05aa\6\u0084\7\2\u05aa"+
		"\u05ac\5\u0112\u008a\2\u05ab\u05a9\3\2\2\2\u05ab\u05ac\3\2\2\2\u05ac\u05ad"+
		"\3\2\2\2\u05ad\u05ae\5\u0146\u00a4\2\u05ae\u0107\3\2\2\2\u05af\u05b0\7"+
		"U\2\2\u05b0\u05b1\7\t\2\2\u05b1\u05b2\5\u0112\u008a\2\u05b2\u05b3\7\n"+
		"\2\2\u05b3\u05b4\5\4\3\2\u05b4\u0109\3\2\2\2\u05b5\u05b6\7\u0085\2\2\u05b6"+
		"\u05b7\7\21\2\2\u05b7\u05b8\5\4\3\2\u05b8\u010b\3\2\2\2\u05b9\u05ba\7"+
		"X\2\2\u05ba\u05bb\6\u0087\b\2\u05bb\u05bc\5\u0112\u008a\2\u05bc\u05bd"+
		"\5\u0146\u00a4\2\u05bd\u010d\3\2\2\2\u05be\u05bf\7R\2\2\u05bf\u05c0\5"+
		"\u0146\u00a4\2\u05c0\u010f\3\2\2\2\u05c1\u05c2\6\u0089\t\2\u05c2\u05c3"+
		"\5\u0112\u008a\2\u05c3\u05c4\5\u0146\u00a4\2\u05c4\u0111\3\2\2\2\u05c5"+
		"\u05ca\5\u0114\u008b\2\u05c6\u05c7\7\16\2\2\u05c7\u05c9\5\u0114\u008b"+
		"\2\u05c8\u05c6\3\2\2\2\u05c9\u05cc\3\2\2\2\u05ca\u05c8\3\2\2\2\u05ca\u05cb"+
		"\3\2\2\2\u05cb\u0113\3\2\2\2\u05cc\u05ca\3\2\2\2\u05cd\u05ce\b\u008b\1"+
		"\2\u05ce\u05e8\7T\2\2\u05cf\u05e8\7d\2\2\u05d0\u05e8\5\u012e\u0098\2\u05d1"+
		"\u05e8\5\u00b8]\2\u05d2\u05e8\5\u00c0a\2\u05d3\u05e8\5\u0130\u0099\2\u05d4"+
		"\u05d7\7p\2\2\u05d5\u05d6\6\u008b\n\2\u05d6\u05d8\5\u0112\u008a\2\u05d7"+
		"\u05d5\3\2\2\2\u05d7\u05d8\3\2\2\2\u05d8\u05e8\3\2\2\2\u05d9\u05da\7`"+
		"\2\2\u05da\u05e8\5\u0114\u008b\31\u05db\u05dc\5\u0122\u0092\2\u05dc\u05dd"+
		"\5\u0114\u008b\16\u05dd\u05e8\3\2\2\2\u05de\u05e8\5\u0124\u0093\2\u05df"+
		"\u05e8\5\u0116\u008c\2\u05e0\u05e8\5\u0118\u008d\2\u05e1\u05e8\5\u011c"+
		"\u008f\2\u05e2\u05e8\5\u0138\u009d\2\u05e3\u05e4\7\t\2\2\u05e4\u05e5\5"+
		"\u0112\u008a\2\u05e5\u05e6\7\n\2\2\u05e6\u05e8\3\2\2\2\u05e7\u05cd\3\2"+
		"\2\2\u05e7\u05cf\3\2\2\2\u05e7\u05d0\3\2\2\2\u05e7\u05d1\3\2\2\2\u05e7"+
		"\u05d2\3\2\2\2\u05e7\u05d3\3\2\2\2\u05e7\u05d4\3\2\2\2\u05e7\u05d9\3\2"+
		"\2\2\u05e7\u05db\3\2\2\2\u05e7\u05de\3\2\2\2\u05e7\u05df\3\2\2\2\u05e7"+
		"\u05e0\3\2\2\2\u05e7\u05e1\3\2\2\2\u05e7\u05e2\3\2\2\2\u05e7\u05e3\3\2"+
		"\2\2\u05e8\u0637\3\2\2\2\u05e9\u05ea\f\30\2\2\u05ea\u05eb\5\u011e\u0090"+
		"\2\u05eb\u05ec\5\u0114\u008b\31\u05ec\u0636\3\2\2\2\u05ed\u05ee\f\27\2"+
		"\2\u05ee\u05ef\7\20\2\2\u05ef\u05f0\5\u0114\u008b\2\u05f0\u05f1\7\21\2"+
		"\2\u05f1\u05f2\5\u0114\u008b\30\u05f2\u0636\3\2\2\2\u05f3\u05f4\f\26\2"+
		"\2\u05f4\u05f5\7\20\2\2\u05f5\u05f6\7\20\2\2\u05f6\u0636\5\u0114\u008b"+
		"\27\u05f7\u05f8\f\25\2\2\u05f8\u05f9\t\b\2\2\u05f9\u0636\5\u0114\u008b"+
		"\26\u05fa\u05fb\f\24\2\2\u05fb\u05fc\t\t\2\2\u05fc\u0636\5\u0114\u008b"+
		"\25\u05fd\u05fe\f\23\2\2\u05fe\u05ff\t\n\2\2\u05ff\u0636\5\u0114\u008b"+
		"\24\u0600\u0601\f\22\2\2\u0601\u0602\5\u0120\u0091\2\u0602\u0603\5\u0114"+
		"\u008b\23\u0603\u0636\3\2\2\2\u0604\u060b\f\21\2\2\u0605\u060c\7\35\2"+
		"\2\u0606\u0607\7\37\2\2\u0607\u060c\7\37\2\2\u0608\u0609\7\37\2\2\u0609"+
		"\u060a\7\37\2\2\u060a\u060c\7\37\2\2\u060b\u0605\3\2\2\2\u060b\u0606\3"+
		"\2\2\2\u060b\u0608\3\2\2\2\u060c\u060d\3\2\2\2\u060d\u0636\5\u0114\u008b"+
		"\22\u060e\u060f\f\20\2\2\u060f\u0610\t\13\2\2\u0610\u0636\5\u0114\u008b"+
		"\21\u0611\u0612\f\17\2\2\u0612\u0613\t\f\2\2\u0613\u0636\5\u0114\u008b"+
		"\20\u0614\u0615\f\r\2\2\u0615\u0616\7\\\2\2\u0616\u0636\5\20\t\2\u0617"+
		"\u0618\f\f\2\2\u0618\u0619\6\u008b\27\2\u0619\u0636\t\r\2\2\u061a\u061b"+
		"\f\13\2\2\u061b\u061d\7\20\2\2\u061c\u061e\7\23\2\2\u061d\u061c\3\2\2"+
		"\2\u061d\u061e\3\2\2\2\u061e\u061f\3\2\2\2\u061f\u0620\7\7\2\2\u0620\u0621"+
		"\5\u0112\u008a\2\u0621\u0622\7\b\2\2\u0622\u0636\3\2\2\2\u0623\u0624\f"+
		"\n\2\2\u0624\u0626\7\20\2\2\u0625\u0627\7\23\2\2\u0626\u0625\3\2\2\2\u0626"+
		"\u0627\3\2\2\2\u0627\u0628\3\2\2\2\u0628\u062a\5\u0138\u009d\2\u0629\u062b"+
		"\5\64\33\2\u062a\u0629\3\2\2\2\u062a\u062b\3\2\2\2\u062b\u062c\3\2\2\2"+
		"\u062c\u062d\5\u00ccg\2\u062d\u0636\3\2\2\2\u062e\u0632\f\t\2\2\u062f"+
		"\u0633\7\23\2\2\u0630\u0631\7\20\2\2\u0631\u0633\7\23\2\2\u0632\u062f"+
		"\3\2\2\2\u0632\u0630\3\2\2\2\u0633\u0634\3\2\2\2\u0634\u0636\5\u0138\u009d"+
		"\2\u0635\u05e9\3\2\2\2\u0635\u05ed\3\2\2\2\u0635\u05f3\3\2\2\2\u0635\u05f7"+
		"\3\2\2\2\u0635\u05fa\3\2\2\2\u0635\u05fd\3\2\2\2\u0635\u0600\3\2\2\2\u0635"+
		"\u0604\3\2\2\2\u0635\u060e\3\2\2\2\u0635\u0611\3\2\2\2\u0635\u0614\3\2"+
		"\2\2\u0635\u0617\3\2\2\2\u0635\u061a\3\2\2\2\u0635\u0623\3\2\2\2\u0635"+
		"\u062e\3\2\2\2\u0636\u0639\3\2\2\2\u0637\u0635\3\2\2\2\u0637\u0638\3\2"+
		"\2\2\u0638\u0115\3\2\2\2\u0639\u0637\3\2\2\2\u063a\u063c\7S\2\2\u063b"+
		"\u063d\7\32\2\2\u063c\u063b\3\2\2\2\u063c\u063d\3\2\2\2\u063d\u063f\3"+
		"\2\2\2\u063e\u0640\7\u0085\2\2\u063f\u063e\3\2\2\2\u063f\u0640\3\2\2\2"+
		"\u0640\u0641\3\2\2\2\u0641\u0643\5\u00a4S\2\u0642\u0644\5\16\b\2\u0643"+
		"\u0642\3\2\2\2\u0643\u0644\3\2\2\2\u0644\u0645\3\2\2\2\u0645\u0646\5\n"+
		"\6\2\u0646\u0117\3\2\2\2\u0647\u0649\7_\2\2\u0648\u0647\3\2\2\2\u0648"+
		"\u0649\3\2\2\2\u0649\u064a\3\2\2\2\u064a\u064c\5\u00a4S\2\u064b\u064d"+
		"\5\16\b\2\u064c\u064b\3\2\2\2\u064c\u064d\3\2\2\2\u064d\u064e\3\2\2\2"+
		"\u064e\u064f\7\66\2\2\u064f\u0650\5\u011a\u008e\2\u0650\u0119\3\2\2\2"+
		"\u0651\u0654\5\u0114\u008b\2\u0652\u0654\5\n\6\2\u0653\u0651\3\2\2\2\u0653"+
		"\u0652\3\2\2\2\u0654\u011b\3\2\2\2\u0655\u0657\7a\2\2\u0656\u0658\7\u0085"+
		"\2\2\u0657\u0656\3\2\2\2\u0657\u0658\3\2\2\2\u0658\u0659\3\2\2\2\u0659"+
		"\u065a\5\u0090I\2\u065a\u011d\3\2\2\2\u065b\u066d\7\17\2\2\u065c\u066d"+
		"\7+\2\2\u065d\u066d\7,\2\2\u065e\u066d\7-\2\2\u065f\u066d\7.\2\2\u0660"+
		"\u0661\7\27\2\2\u0661\u066d\7\17\2\2\u0662\u066d\7\60\2\2\u0663\u0664"+
		"\7\37\2\2\u0664\u0666\7\37\2\2\u0665\u0667\7\37\2\2\u0666\u0665\3\2\2"+
		"\2\u0666\u0667\3\2\2\2\u0667\u0668\3\2\2\2\u0668\u066d\7\17\2\2\u0669"+
		"\u066d\7\63\2\2\u066a\u066d\7\64\2\2\u066b\u066d\7\65\2\2\u066c\u065b"+
		"\3\2\2\2\u066c\u065c\3\2\2\2\u066c\u065d\3\2\2\2\u066c\u065e\3\2\2\2\u066c"+
		"\u065f\3\2\2\2\u066c\u0660\3\2\2\2\u066c\u0662\3\2\2\2\u066c\u0663\3\2"+
		"\2\2\u066c\u0669\3\2\2\2\u066c\u066a\3\2\2\2\u066c\u066b\3\2\2\2\u066d"+
		"\u011f\3\2\2\2\u066e\u066f\t\16\2\2\u066f\u0121\3\2\2\2\u0670\u0671\t"+
		"\17\2\2\u0671\u0123\3\2\2\2\u0672\u067c\7G\2\2\u0673\u0674\7\23\2\2\u0674"+
		"\u067d\7H\2\2\u0675\u0677\5\u0114\u008b\2\u0676\u0678\5\64\33\2\u0677"+
		"\u0676\3\2\2\2\u0677\u0678\3\2\2\2\u0678\u067a\3\2\2\2\u0679\u067b\5\u00cc"+
		"g\2\u067a\u0679\3\2\2\2\u067a\u067b\3\2\2\2\u067b\u067d\3\2\2\2\u067c"+
		"\u0673\3\2\2\2\u067c\u0675\3\2\2\2\u067d\u0125\3\2\2\2\u067e\u067f\7\13"+
		"\2\2\u067f\u0684\5\u0128\u0095\2\u0680\u0681\7\16\2\2\u0681\u0683\5\u0128"+
		"\u0095\2\u0682\u0680\3\2\2\2\u0683\u0686\3\2\2\2\u0684\u0682\3\2\2\2\u0684"+
		"\u0685\3\2\2\2\u0685\u0688\3\2\2\2\u0686\u0684\3\2\2\2\u0687\u0689\7\16"+
		"\2\2\u0688\u0687\3\2\2\2\u0688\u0689\3\2\2\2\u0689\u068a\3\2\2\2\u068a"+
		"\u068b\7\f\2\2\u068b\u0127\3\2\2\2\u068c\u068d\7\32\2\2\u068d\u068e\5"+
		"\u012c\u0097\2\u068e\u0129\3\2\2\2\u068f\u0690\7\13\2\2\u0690\u0695\5"+
		"\u012c\u0097\2\u0691\u0692\7\16\2\2\u0692\u0694\5\u012c\u0097\2\u0693"+
		"\u0691\3\2\2\2\u0694\u0697\3\2\2\2\u0695\u0693\3\2\2\2\u0695\u0696\3\2"+
		"\2\2\u0696\u0699\3\2\2\2\u0697\u0695\3\2\2\2\u0698\u069a\7\16\2\2\u0699"+
		"\u0698\3\2\2\2\u0699\u069a\3\2\2\2\u069a\u069b\3\2\2\2\u069b\u069c\7\f"+
		"\2\2\u069c\u012b\3\2\2\2\u069d\u069e\7\7\2\2\u069e\u069f\5\u0114\u008b"+
		"\2\u069f\u06a0\7\b\2\2\u06a0\u06a1\5\u00a4S\2\u06a1\u06a2\5\n\6\2\u06a2"+
		"\u012d\3\2\2\2\u06a3\u06aa\7\67\2\2\u06a4\u06aa\79\2\2\u06a5\u06aa\7\u0086"+
		"\2\2\u06a6\u06aa\5\u0130\u0099\2\u06a7\u06aa\7\6\2\2\u06a8\u06aa\5\u0134"+
		"\u009b\2\u06a9\u06a3\3\2\2\2\u06a9\u06a4\3\2\2\2\u06a9\u06a5\3\2\2\2\u06a9"+
		"\u06a6\3\2\2\2\u06a9\u06a7\3\2\2\2\u06a9\u06a8\3\2\2\2\u06aa\u012f\3\2"+
		"\2\2\u06ab\u06af\7\u0087\2\2\u06ac\u06ae\5\u0132\u009a\2\u06ad\u06ac\3"+
		"\2\2\2\u06ae\u06b1\3\2\2\2\u06af\u06ad\3\2\2\2\u06af\u06b0\3\2\2\2\u06b0"+
		"\u06b2\3\2\2\2\u06b1\u06af\3\2\2\2\u06b2\u06b3\7\u0087\2\2\u06b3\u0131"+
		"\3\2\2\2\u06b4\u06ba\7\u008e\2\2\u06b5\u06b6\7\u008d\2\2\u06b6\u06b7\5"+
		"\u0114\u008b\2\u06b7\u06b8\7\f\2\2\u06b8\u06ba\3\2\2\2\u06b9\u06b4\3\2"+
		"\2\2\u06b9\u06b5\3\2\2\2\u06ba\u0133\3\2\2\2\u06bb\u06bd\7\27\2\2\u06bc"+
		"\u06bb\3\2\2\2\u06bc\u06bd\3\2\2\2\u06bd\u06be\3\2\2\2\u06be\u06c4\7:"+
		"\2\2\u06bf\u06c4\7;\2\2\u06c0\u06c4\7<\2\2\u06c1\u06c4\7=\2\2\u06c2\u06c4"+
		"\7>\2\2\u06c3\u06bc\3\2\2\2\u06c3\u06bf\3\2\2\2\u06c3\u06c0\3\2\2\2\u06c3"+
		"\u06c1\3\2\2\2\u06c3\u06c2\3\2\2\2\u06c4\u0135\3\2\2\2\u06c5\u06c6\t\20"+
		"\2\2\u06c6\u0137\3\2\2\2\u06c7\u06ca\5\u013a\u009e\2\u06c8\u06ca\7\u0085"+
		"\2\2\u06c9\u06c7\3\2\2\2\u06c9\u06c8\3\2\2\2\u06ca\u0139\3\2\2\2\u06cb"+
		"\u06ce\5\u013e\u00a0\2\u06cc\u06ce\79\2\2\u06cd\u06cb\3\2\2\2\u06cd\u06cc"+
		"\3\2\2\2\u06ce\u013b\3\2\2\2\u06cf\u06d3\5\u0140\u00a1\2\u06d0\u06d3\7"+
		"9\2\2\u06d1\u06d3\7\u0085\2\2\u06d2\u06cf\3\2\2\2\u06d2\u06d0\3\2\2\2"+
		"\u06d2\u06d1\3\2\2\2\u06d3\u013d\3\2\2\2\u06d4\u06d8\5\u0140\u00a1\2\u06d5"+
		"\u06d8\7^\2\2\u06d6\u06d8\7B\2\2\u06d7\u06d4\3\2\2\2\u06d7\u06d5\3\2\2"+
		"\2\u06d7\u06d6\3\2\2\2\u06d8\u013f\3\2\2\2\u06d9\u06da\t\21\2\2\u06da"+
		"\u0141\3\2\2\2\u06db\u06dc\7w\2\2\u06dc\u06dd\5\u00c4c\2\u06dd\u0143\3"+
		"\2\2\2\u06de\u06df\7x\2\2\u06df\u06e0\5\u00c4c\2\u06e0\u0145\3\2\2\2\u06e1"+
		"\u06e6\7\r\2\2\u06e2\u06e6\7\2\2\3\u06e3\u06e6\6\u00a4\33\2\u06e4\u06e6"+
		"\6\u00a4\34\2\u06e5\u06e1\3\2\2\2\u06e5\u06e2\3\2\2\2\u06e5\u06e3\3\2"+
		"\2\2\u06e5\u06e4\3\2\2\2\u06e6\u0147\3\2\2\2\u00eb\u0149\u015e\u0161\u016d"+
		"\u0171\u0178\u0187\u018a\u0191\u0195\u019c\u01a0\u01aa\u01b3\u01bd\u01c2"+
		"\u01c5\u01c8\u01cb\u01d2\u01db\u01df\u01e3\u01e6\u01e9\u01ed\u01f1\u01f6"+
		"\u01fb\u0203\u020a\u0210\u0212\u021b\u021f\u0225\u0234\u0237\u023b\u0247"+
		"\u0250\u0255\u0259\u0262\u0267\u026b\u0281\u0287\u028c\u0294\u029b\u02a4"+
		"\u02a7\u02ab\u02af\u02b9\u02be\u02c4\u02c9\u02d3\u02d7\u02db\u02de\u02e4"+
		"\u02e6\u02e9\u02ec\u02f1\u02f7\u02fc\u0307\u030b\u0310\u0314\u0317\u031a"+
		"\u0320\u0324\u0328\u032f\u0335\u0339\u033e\u0341\u0344\u0349\u034e\u0351"+
		"\u0354\u035e\u0361\u0368\u036d\u0373\u0376\u0379\u037e\u0382\u0388\u0390"+
		"\u0393\u0396\u0399\u039c\u03a0\u03a3\u03a6\u03aa\u03ac\u03b4\u03b7\u03ba"+
		"\u03c0\u03c8\u03cd\u03cf\u03d4\u03d8\u03db\u03de\u03e2\u03e5\u03e8\u03ed"+
		"\u03f0\u03f3\u03f9\u03fd\u0401\u0409\u040e\u0412\u0415\u041b\u0423\u0427"+
		"\u0429\u0431\u0435\u043b\u0445\u0449\u044f\u0452\u0458\u045b\u045f\u0462"+
		"\u046b\u046d\u0476\u047a\u047e\u0484\u0489\u0490\u0498\u04a0\u04a4\u04ab"+
		"\u04b4\u04ba\u04c2\u04c7\u04da\u04e2\u04e6\u04eb\u04ee\u04f3\u04f6\u04fc"+
		"\u04ff\u0506\u050b\u050f\u0512\u051c\u0520\u0522\u0529\u052f\u0534\u053a"+
		"\u053d\u0551\u0564\u0568\u056c\u0576\u057a\u0585\u0592\u0598\u059d\u05a4"+
		"\u05ab\u05ca\u05d7\u05e7\u060b\u061d\u0626\u062a\u0632\u0635\u0637\u063c"+
		"\u063f\u0643\u0648\u064c\u0653\u0657\u0666\u066c\u0677\u067a\u067c\u0684"+
		"\u0688\u0695\u0699\u06a9\u06af\u06b9\u06bc\u06c3\u06c9\u06cd\u06d2\u06d7"+
		"\u06e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}