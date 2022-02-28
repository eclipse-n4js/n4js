// Generated from /Users/mark-oliver.reiser/Home/Prog/Java/n4js-temp/git-repo/n4js/plugins/org.eclipse.n4js.dts/grammar/TypeScriptParser.g4 by ANTLR 4.7.2
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
		RULE_primaryTypeExpression = 14, RULE_literalType = 15, RULE_arrowFunctionTypeExpression = 16, 
		RULE_tupleTypeExpression = 17, RULE_tupleTypeArgument = 18, RULE_typeVariable = 19, 
		RULE_typeRefWithModifiers = 20, RULE_parameterizedTypeRef = 21, RULE_typeName = 22, 
		RULE_typeArguments = 23, RULE_typeArgumentList = 24, RULE_typeArgument = 25, 
		RULE_objectLiteralTypeRef = 26, RULE_thisTypeRef = 27, RULE_queryTypeRef = 28, 
		RULE_importTypeRef = 29, RULE_anonymousFormalParameterListWithDeclaredThisType = 30, 
		RULE_anonymousFormalParameter = 31, RULE_defaultFormalParameter = 32, 
		RULE_typePredicateWithOperatorTypeRef = 33, RULE_bindingIdentifier = 34, 
		RULE_propertyAccessExpressionInTypeRef = 35, RULE_inferTypeRef = 36, RULE_typeAliasDeclaration = 37, 
		RULE_typeParameters = 38, RULE_typeParameterList = 39, RULE_typeParameter = 40, 
		RULE_constraint = 41, RULE_defaultType = 42, RULE_moduleDeclaration = 43, 
		RULE_moduleName = 44, RULE_namespaceDeclaration = 45, RULE_namespaceName = 46, 
		RULE_decoratorList = 47, RULE_decorator = 48, RULE_decoratorMemberExpression = 49, 
		RULE_decoratorCallExpression = 50, RULE_interfaceDeclaration = 51, RULE_interfaceExtendsClause = 52, 
		RULE_classOrInterfaceTypeList = 53, RULE_interfaceBody = 54, RULE_interfaceMemberList = 55, 
		RULE_interfaceMember = 56, RULE_constructSignature = 57, RULE_callSignature = 58, 
		RULE_indexSignature = 59, RULE_indexSignatureElement = 60, RULE_methodSignature = 61, 
		RULE_propertySignature = 62, RULE_enumDeclaration = 63, RULE_enumBody = 64, 
		RULE_enumMemberList = 65, RULE_enumMember = 66, RULE_functionDeclaration = 67, 
		RULE_classDeclaration = 68, RULE_classHeritage = 69, RULE_classExtendsClause = 70, 
		RULE_classImplementsClause = 71, RULE_classBody = 72, RULE_classMemberList = 73, 
		RULE_classMember = 74, RULE_constructorDeclaration = 75, RULE_propertyMemberDeclaration = 76, 
		RULE_abstractDeclaration = 77, RULE_propertyMember = 78, RULE_propertyMemberBase = 79, 
		RULE_propertyOrMethod = 80, RULE_initializer = 81, RULE_parameterBlock = 82, 
		RULE_parameterListTrailingComma = 83, RULE_parameterList = 84, RULE_restParameter = 85, 
		RULE_parameter = 86, RULE_requiredParameter = 87, RULE_optionalParameter = 88, 
		RULE_accessibilityModifier = 89, RULE_identifierOrPattern = 90, RULE_bindingPattern = 91, 
		RULE_arrayLiteral = 92, RULE_elementList = 93, RULE_arrayElement = 94, 
		RULE_bindingElement = 95, RULE_objectLiteral = 96, RULE_propertyAssignment = 97, 
		RULE_propertyName = 98, RULE_getAccessor = 99, RULE_setAccessor = 100, 
		RULE_generatorMethod = 101, RULE_arguments = 102, RULE_argumentList = 103, 
		RULE_argument = 104, RULE_importStatement = 105, RULE_importFromBlock = 106, 
		RULE_multipleImportElements = 107, RULE_importedElement = 108, RULE_importAliasDeclaration = 109, 
		RULE_exportStatement = 110, RULE_exportStatementTail = 111, RULE_exportFromBlock = 112, 
		RULE_variableStatement = 113, RULE_varModifier = 114, RULE_bindingPatternBlock = 115, 
		RULE_variableDeclarationList = 116, RULE_variableDeclaration = 117, RULE_switchStatement = 118, 
		RULE_caseBlock = 119, RULE_caseClauses = 120, RULE_caseClause = 121, RULE_defaultClause = 122, 
		RULE_tryStatement = 123, RULE_catchProduction = 124, RULE_finallyProduction = 125, 
		RULE_emptyStatement = 126, RULE_ifStatement = 127, RULE_iterationStatement = 128, 
		RULE_continueStatement = 129, RULE_breakStatement = 130, RULE_returnStatement = 131, 
		RULE_withStatement = 132, RULE_labelledStatement = 133, RULE_throwStatement = 134, 
		RULE_debuggerStatement = 135, RULE_expressionStatement = 136, RULE_expressionSequence = 137, 
		RULE_singleExpression = 138, RULE_functionExpression = 139, RULE_arrowFunctionExpression = 140, 
		RULE_arrowFunctionBody = 141, RULE_classExpression = 142, RULE_assignmentOperator = 143, 
		RULE_relationalOperator = 144, RULE_unaryOperator = 145, RULE_newExpression = 146, 
		RULE_generatorBlock = 147, RULE_generatorDefinition = 148, RULE_iteratorBlock = 149, 
		RULE_iteratorDefinition = 150, RULE_literal = 151, RULE_templateStringLiteral = 152, 
		RULE_templateStringAtom = 153, RULE_numericLiteral = 154, RULE_identifierOrKeyWord = 155, 
		RULE_identifierName = 156, RULE_reservedWord = 157, RULE_typeReferenceName = 158, 
		RULE_keyword = 159, RULE_keywordAllowedInTypeReferences = 160, RULE_getter = 161, 
		RULE_setter = 162, RULE_eos = 163;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "declareStatement", "declarationStatement", "block", 
			"statementList", "colonSepTypeRef", "typeRef", "conditionalTypeRef", 
			"unionTypeExpression", "intersectionTypeExpression", "operatorTypeRef", 
			"typeOperator", "arrayTypeExpression", "primaryTypeExpression", "literalType", 
			"arrowFunctionTypeExpression", "tupleTypeExpression", "tupleTypeArgument", 
			"typeVariable", "typeRefWithModifiers", "parameterizedTypeRef", "typeName", 
			"typeArguments", "typeArgumentList", "typeArgument", "objectLiteralTypeRef", 
			"thisTypeRef", "queryTypeRef", "importTypeRef", "anonymousFormalParameterListWithDeclaredThisType", 
			"anonymousFormalParameter", "defaultFormalParameter", "typePredicateWithOperatorTypeRef", 
			"bindingIdentifier", "propertyAccessExpressionInTypeRef", "inferTypeRef", 
			"typeAliasDeclaration", "typeParameters", "typeParameterList", "typeParameter", 
			"constraint", "defaultType", "moduleDeclaration", "moduleName", "namespaceDeclaration", 
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
			"exportFromBlock", "variableStatement", "varModifier", "bindingPatternBlock", 
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
			setState(329);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Return - 71)) | (1L << (Continue - 71)) | (1L << (For - 71)) | (1L << (Switch - 71)) | (1L << (While - 71)) | (1L << (Debugger - 71)) | (1L << (Function - 71)) | (1L << (With - 71)) | (1L << (If - 71)) | (1L << (Throw - 71)) | (1L << (Try - 71)) | (1L << (ReadOnly - 71)) | (1L << (Class - 71)) | (1L << (Enum - 71)) | (1L << (Const - 71)) | (1L << (Export - 71)) | (1L << (Import - 71)) | (1L << (Let - 71)) | (1L << (Private - 71)) | (1L << (Public - 71)) | (1L << (Interface - 71)) | (1L << (Protected - 71)) | (1L << (TypeAlias - 71)) | (1L << (Namespace - 71)) | (1L << (Module - 71)) | (1L << (Declare - 71)) | (1L << (Abstract - 71)) | (1L << (At - 71)) | (1L << (Identifier - 71)))) != 0)) {
				{
				setState(328);
				statementList();
				}
			}

			setState(331);
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
			setState(350);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBrace:
				enterOuterAlt(_localctx, 1);
				{
				setState(333);
				block();
				}
				break;
			case Import:
				enterOuterAlt(_localctx, 2);
				{
				setState(334);
				importStatement();
				}
				break;
			case At:
				enterOuterAlt(_localctx, 3);
				{
				setState(335);
				decoratorList();
				}
				break;
			case Export:
				enterOuterAlt(_localctx, 4);
				{
				setState(336);
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
				setState(337);
				declareStatement();
				}
				break;
			case If:
				enterOuterAlt(_localctx, 6);
				{
				setState(338);
				ifStatement();
				}
				break;
			case Do:
			case For:
			case While:
				enterOuterAlt(_localctx, 7);
				{
				setState(339);
				iterationStatement();
				}
				break;
			case Continue:
				enterOuterAlt(_localctx, 8);
				{
				setState(340);
				continueStatement();
				}
				break;
			case Break:
				enterOuterAlt(_localctx, 9);
				{
				setState(341);
				breakStatement();
				}
				break;
			case Return:
				enterOuterAlt(_localctx, 10);
				{
				setState(342);
				returnStatement();
				}
				break;
			case With:
				enterOuterAlt(_localctx, 11);
				{
				setState(343);
				withStatement();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 12);
				{
				setState(344);
				labelledStatement();
				}
				break;
			case Switch:
				enterOuterAlt(_localctx, 13);
				{
				setState(345);
				switchStatement();
				}
				break;
			case Throw:
				enterOuterAlt(_localctx, 14);
				{
				setState(346);
				throwStatement();
				}
				break;
			case Try:
				enterOuterAlt(_localctx, 15);
				{
				setState(347);
				tryStatement();
				}
				break;
			case Debugger:
				enterOuterAlt(_localctx, 16);
				{
				setState(348);
				debuggerStatement();
				}
				break;
			case SemiColon:
				enterOuterAlt(_localctx, 17);
				{
				setState(349);
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
			setState(353);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Declare) {
				{
				setState(352);
				match(Declare);
				}
			}

			setState(355);
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
			setState(365);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(357);
				moduleDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(358);
				namespaceDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(359);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(360);
				typeAliasDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(361);
				functionDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(362);
				classDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(363);
				enumDeclaration();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(364);
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
			setState(367);
			match(OpenBrace);
			setState(369);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Return - 71)) | (1L << (Continue - 71)) | (1L << (For - 71)) | (1L << (Switch - 71)) | (1L << (While - 71)) | (1L << (Debugger - 71)) | (1L << (Function - 71)) | (1L << (With - 71)) | (1L << (If - 71)) | (1L << (Throw - 71)) | (1L << (Try - 71)) | (1L << (ReadOnly - 71)) | (1L << (Class - 71)) | (1L << (Enum - 71)) | (1L << (Const - 71)) | (1L << (Export - 71)) | (1L << (Import - 71)) | (1L << (Let - 71)) | (1L << (Private - 71)) | (1L << (Public - 71)) | (1L << (Interface - 71)) | (1L << (Protected - 71)) | (1L << (TypeAlias - 71)) | (1L << (Namespace - 71)) | (1L << (Module - 71)) | (1L << (Declare - 71)) | (1L << (Abstract - 71)) | (1L << (At - 71)) | (1L << (Identifier - 71)))) != 0)) {
				{
				setState(368);
				statementList();
				}
			}

			setState(371);
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
			setState(374); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(373);
				statement();
				}
				}
				setState(376); 
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
			setState(378);
			match(Colon);
			setState(379);
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
			setState(381);
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
			setState(383);
			unionTypeExpression();
			setState(391);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(384);
				match(Extends);
				setState(385);
				unionTypeExpression();
				setState(386);
				match(QuestionMark);
				setState(387);
				conditionalTypeRef();
				setState(388);
				match(Colon);
				setState(389);
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
			setState(394);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BitOr) {
				{
				setState(393);
				match(BitOr);
				}
			}

			setState(396);
			intersectionTypeExpression();
			setState(401);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(397);
					match(BitOr);
					setState(398);
					intersectionTypeExpression();
					}
					} 
				}
				setState(403);
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
			setState(405);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BitAnd) {
				{
				setState(404);
				match(BitAnd);
				}
			}

			setState(407);
			operatorTypeRef();
			setState(412);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(408);
					match(BitAnd);
					setState(409);
					operatorTypeRef();
					}
					} 
				}
				setState(414);
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
			setState(416);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(415);
				typeOperator();
				}
				break;
			}
			setState(418);
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
			setState(420);
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
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public List<TerminalNode> OpenBracket() { return getTokens(TypeScriptParser.OpenBracket); }
		public TerminalNode OpenBracket(int i) {
			return getToken(TypeScriptParser.OpenBracket, i);
		}
		public List<TerminalNode> CloseBracket() { return getTokens(TypeScriptParser.CloseBracket); }
		public TerminalNode CloseBracket(int i) {
			return getToken(TypeScriptParser.CloseBracket, i);
		}
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public PrimaryTypeExpressionContext primaryTypeExpression() {
			return getRuleContext(PrimaryTypeExpressionContext.class,0);
		}
		public List<TypeRefContext> typeRef() {
			return getRuleContexts(TypeRefContext.class);
		}
		public TypeRefContext typeRef(int i) {
			return getRuleContext(TypeRefContext.class,i);
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
			setState(456);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(422);
				match(QuestionMark);
				setState(423);
				match(OpenBracket);
				setState(424);
				match(CloseBracket);
				setState(429);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(425);
						match(OpenBracket);
						setState(426);
						match(CloseBracket);
						}
						} 
					}
					setState(431);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(432);
				match(OpenParen);
				setState(433);
				match(QuestionMark);
				setState(434);
				match(CloseParen);
				setState(435);
				match(OpenBracket);
				setState(436);
				match(CloseBracket);
				setState(441);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(437);
						match(OpenBracket);
						setState(438);
						match(CloseBracket);
						}
						} 
					}
					setState(443);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				}
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(444);
				primaryTypeExpression();
				setState(453);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						setState(451);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
						case 1:
							{
							{
							setState(445);
							match(OpenBracket);
							setState(446);
							match(CloseBracket);
							}
							}
							break;
						case 2:
							{
							{
							setState(447);
							match(OpenBracket);
							setState(448);
							typeRef();
							setState(449);
							match(CloseBracket);
							}
							}
							break;
						}
						} 
					}
					setState(455);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				}
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
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
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
		enterRule(_localctx, 28, RULE_primaryTypeExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(469);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(458);
				literalType();
				}
				break;
			case 2:
				{
				setState(459);
				arrowFunctionTypeExpression();
				}
				break;
			case 3:
				{
				setState(460);
				tupleTypeExpression();
				}
				break;
			case 4:
				{
				setState(461);
				queryTypeRef();
				}
				break;
			case 5:
				{
				setState(462);
				importTypeRef();
				}
				break;
			case 6:
				{
				setState(463);
				inferTypeRef();
				}
				break;
			case 7:
				{
				setState(464);
				typeRefWithModifiers();
				}
				break;
			case 8:
				{
				setState(465);
				match(OpenParen);
				setState(466);
				typeRef();
				setState(467);
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
		enterRule(_localctx, 30, RULE_literalType);
		try {
			setState(474);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BooleanLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(471);
				match(BooleanLiteral);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(472);
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
				setState(473);
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
		public TerminalNode OpenParen() { return getToken(TypeScriptParser.OpenParen, 0); }
		public AnonymousFormalParameterListWithDeclaredThisTypeContext anonymousFormalParameterListWithDeclaredThisType() {
			return getRuleContext(AnonymousFormalParameterListWithDeclaredThisTypeContext.class,0);
		}
		public TerminalNode CloseParen() { return getToken(TypeScriptParser.CloseParen, 0); }
		public TerminalNode ARROW() { return getToken(TypeScriptParser.ARROW, 0); }
		public TypePredicateWithOperatorTypeRefContext typePredicateWithOperatorTypeRef() {
			return getRuleContext(TypePredicateWithOperatorTypeRefContext.class,0);
		}
		public UnionTypeExpressionContext unionTypeExpression() {
			return getRuleContext(UnionTypeExpressionContext.class,0);
		}
		public TerminalNode New() { return getToken(TypeScriptParser.New, 0); }
		public TerminalNode LessThan() { return getToken(TypeScriptParser.LessThan, 0); }
		public List<TypeVariableContext> typeVariable() {
			return getRuleContexts(TypeVariableContext.class);
		}
		public TypeVariableContext typeVariable(int i) {
			return getRuleContext(TypeVariableContext.class,i);
		}
		public TerminalNode MoreThan() { return getToken(TypeScriptParser.MoreThan, 0); }
		public TerminalNode Abstract() { return getToken(TypeScriptParser.Abstract, 0); }
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
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
		enterRule(_localctx, 32, RULE_arrowFunctionTypeExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(480);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==New || _la==Abstract) {
				{
				setState(477);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Abstract) {
					{
					setState(476);
					match(Abstract);
					}
				}

				setState(479);
				match(New);
				}
			}

			setState(496);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(482);
				match(LessThan);
				setState(483);
				typeVariable();
				setState(488);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(484);
						match(Comma);
						setState(485);
						typeVariable();
						}
						} 
					}
					setState(490);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				}
				setState(492);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(491);
					match(Comma);
					}
				}

				setState(494);
				match(MoreThan);
				}
			}

			setState(498);
			match(OpenParen);
			setState(499);
			anonymousFormalParameterListWithDeclaredThisType();
			setState(500);
			match(CloseParen);
			setState(501);
			match(ARROW);
			}
			setState(505);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(503);
				typePredicateWithOperatorTypeRef();
				}
				break;
			case 2:
				{
				setState(504);
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
		enterRule(_localctx, 34, RULE_tupleTypeExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(507);
			match(OpenBracket);
			setState(522);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CloseBracket:
				{
				setState(508);
				match(CloseBracket);
				}
				break;
			case OpenBracket:
			case OpenParen:
			case OpenBrace:
			case QuestionMark:
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
				setState(509);
				tupleTypeArgument();
				setState(514);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(510);
						match(Comma);
						setState(511);
						tupleTypeArgument();
						}
						} 
					}
					setState(516);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				}
				setState(518);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(517);
					match(Comma);
					}
				}

				setState(520);
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
		enterRule(_localctx, 36, RULE_tupleTypeArgument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(525);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(524);
				match(Ellipsis);
				}
			}

			setState(528);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(527);
				match(Infer);
				}
				break;
			}
			setState(532);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(530);
				match(Identifier);
				setState(531);
				match(Colon);
				}
				break;
			}
			setState(534);
			typeRef();
			setState(536);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(535);
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
		enterRule(_localctx, 38, RULE_typeVariable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(538);
			match(Identifier);
			setState(541);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(539);
				match(Extends);
				setState(540);
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
		public ParameterizedTypeRefContext parameterizedTypeRef() {
			return getRuleContext(ParameterizedTypeRefContext.class,0);
		}
		public ObjectLiteralTypeRefContext objectLiteralTypeRef() {
			return getRuleContext(ObjectLiteralTypeRefContext.class,0);
		}
		public ThisTypeRefContext thisTypeRef() {
			return getRuleContext(ThisTypeRefContext.class,0);
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
		enterRule(_localctx, 40, RULE_typeRefWithModifiers);
		try {
			setState(546);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(543);
				parameterizedTypeRef();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(544);
				objectLiteralTypeRef();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(545);
				thisTypeRef();
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
		enterRule(_localctx, 42, RULE_parameterizedTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(548);
			typeName();
			setState(550);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				setState(549);
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
		enterRule(_localctx, 44, RULE_typeName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(552);
			typeReferenceName();
			setState(557);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(553);
					match(Dot);
					setState(554);
					typeReferenceName();
					}
					} 
				}
				setState(559);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
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
		enterRule(_localctx, 46, RULE_typeArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(560);
			match(LessThan);
			setState(565);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (OpenBrace - 5)) | (1L << (QuestionMark - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (BitAnd - 5)) | (1L << (BitOr - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Keyof - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(561);
				typeArgumentList();
				setState(563);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(562);
					match(Comma);
					}
				}

				}
			}

			setState(567);
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
		enterRule(_localctx, 48, RULE_typeArgumentList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(569);
			typeArgument();
			setState(574);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(570);
					match(Comma);
					setState(571);
					typeArgument();
					}
					} 
				}
				setState(576);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
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
		enterRule(_localctx, 50, RULE_typeArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(578);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				{
				setState(577);
				match(Infer);
				}
				break;
			}
			setState(580);
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
		enterRule(_localctx, 52, RULE_objectLiteralTypeRef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(582);
			match(OpenBrace);
			setState(584);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (Plus - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(583);
				interfaceBody();
				}
			}

			setState(586);
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
		enterRule(_localctx, 54, RULE_thisTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(588);
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
		enterRule(_localctx, 56, RULE_queryTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(590);
			match(Typeof);
			setState(591);
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
		enterRule(_localctx, 58, RULE_importTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(593);
			match(Import);
			setState(594);
			match(OpenParen);
			setState(595);
			match(StringLiteral);
			setState(596);
			match(CloseParen);
			setState(599);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				{
				setState(597);
				match(Dot);
				setState(598);
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

	public static class AnonymousFormalParameterListWithDeclaredThisTypeContext extends ParserRuleContext {
		public TerminalNode This() { return getToken(TypeScriptParser.This, 0); }
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public List<AnonymousFormalParameterContext> anonymousFormalParameter() {
			return getRuleContexts(AnonymousFormalParameterContext.class);
		}
		public AnonymousFormalParameterContext anonymousFormalParameter(int i) {
			return getRuleContext(AnonymousFormalParameterContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public AnonymousFormalParameterListWithDeclaredThisTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anonymousFormalParameterListWithDeclaredThisType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterAnonymousFormalParameterListWithDeclaredThisType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitAnonymousFormalParameterListWithDeclaredThisType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitAnonymousFormalParameterListWithDeclaredThisType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnonymousFormalParameterListWithDeclaredThisTypeContext anonymousFormalParameterListWithDeclaredThisType() throws RecognitionException {
		AnonymousFormalParameterListWithDeclaredThisTypeContext _localctx = new AnonymousFormalParameterListWithDeclaredThisTypeContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_anonymousFormalParameterListWithDeclaredThisType);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(616);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (OpenBrace - 5)) | (1L << (QuestionMark - 5)) | (1L << (Ellipsis - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (BitAnd - 5)) | (1L << (BitOr - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Keyof - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(604);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
				case 1:
					{
					setState(601);
					match(This);
					setState(602);
					colonSepTypeRef();
					}
					break;
				case 2:
					{
					setState(603);
					anonymousFormalParameter();
					}
					break;
				}
				setState(610);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(606);
						match(Comma);
						setState(607);
						anonymousFormalParameter();
						}
						} 
					}
					setState(612);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				}
				setState(614);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(613);
					match(Comma);
					}
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

	public static class AnonymousFormalParameterContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode Ellipsis() { return getToken(TypeScriptParser.Ellipsis, 0); }
		public DefaultFormalParameterContext defaultFormalParameter() {
			return getRuleContext(DefaultFormalParameterContext.class,0);
		}
		public BindingIdentifierContext bindingIdentifier() {
			return getRuleContext(BindingIdentifierContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public AnonymousFormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anonymousFormalParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterAnonymousFormalParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitAnonymousFormalParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitAnonymousFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnonymousFormalParameterContext anonymousFormalParameter() throws RecognitionException {
		AnonymousFormalParameterContext _localctx = new AnonymousFormalParameterContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_anonymousFormalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(619);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(618);
				match(Ellipsis);
				}
			}

			setState(628);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				{
				setState(621);
				bindingIdentifier();
				setState(623);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==QuestionMark) {
					{
					setState(622);
					match(QuestionMark);
					}
				}

				setState(625);
				colonSepTypeRef();
				}
				}
				break;
			case 2:
				{
				setState(627);
				typeRef();
				}
				break;
			}
			setState(631);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(630);
				defaultFormalParameter();
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

	public static class DefaultFormalParameterContext extends ParserRuleContext {
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public DefaultFormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultFormalParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDefaultFormalParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDefaultFormalParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDefaultFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultFormalParameterContext defaultFormalParameter() throws RecognitionException {
		DefaultFormalParameterContext _localctx = new DefaultFormalParameterContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_defaultFormalParameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(633);
			match(Assign);
			setState(634);
			match(Identifier);
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
		enterRule(_localctx, 66, RULE_typePredicateWithOperatorTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(637);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(636);
				match(Asserts);
				}
				break;
			}
			setState(641);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				{
				setState(639);
				match(This);
				}
				break;
			case 2:
				{
				setState(640);
				bindingIdentifier();
				}
				break;
			}
			setState(643);
			match(Is);
			setState(644);
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
		enterRule(_localctx, 68, RULE_bindingIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(646);
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
		enterRule(_localctx, 70, RULE_propertyAccessExpressionInTypeRef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(648);
			typeReferenceName();
			setState(653);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(649);
					match(Dot);
					setState(650);
					typeReferenceName();
					}
					} 
				}
				setState(655);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
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
		enterRule(_localctx, 72, RULE_inferTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(656);
			match(Infer);
			setState(657);
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
		enterRule(_localctx, 74, RULE_typeAliasDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(659);
			match(TypeAlias);
			setState(660);
			identifierName();
			setState(662);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(661);
				typeParameters();
				}
			}

			setState(664);
			match(Assign);
			setState(665);
			typeRef();
			setState(667);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				{
				setState(666);
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
		enterRule(_localctx, 76, RULE_typeParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(669);
			match(LessThan);
			setState(671);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (NullLiteral - 53)) | (1L << (UndefinedLiteral - 53)) | (1L << (BooleanLiteral - 53)) | (1L << (Break - 53)) | (1L << (Do - 53)) | (1L << (Instanceof - 53)) | (1L << (Typeof - 53)) | (1L << (Unique - 53)) | (1L << (Case - 53)) | (1L << (Else - 53)) | (1L << (New - 53)) | (1L << (Target - 53)) | (1L << (Var - 53)) | (1L << (Catch - 53)) | (1L << (Finally - 53)) | (1L << (Return - 53)) | (1L << (Void - 53)) | (1L << (Continue - 53)) | (1L << (For - 53)) | (1L << (Switch - 53)) | (1L << (While - 53)) | (1L << (Debugger - 53)) | (1L << (Function - 53)) | (1L << (This - 53)) | (1L << (With - 53)) | (1L << (Default - 53)) | (1L << (If - 53)) | (1L << (Throw - 53)) | (1L << (Delete - 53)) | (1L << (In - 53)) | (1L << (Try - 53)) | (1L << (As - 53)) | (1L << (From - 53)) | (1L << (ReadOnly - 53)) | (1L << (Async - 53)) | (1L << (Await - 53)) | (1L << (Class - 53)) | (1L << (Enum - 53)) | (1L << (Extends - 53)) | (1L << (Super - 53)) | (1L << (Const - 53)) | (1L << (Export - 53)) | (1L << (Import - 53)) | (1L << (Implements - 53)) | (1L << (Let - 53)) | (1L << (Private - 53)) | (1L << (Public - 53)) | (1L << (Interface - 53)) | (1L << (Package - 53)) | (1L << (Protected - 53)) | (1L << (Static - 53)) | (1L << (Yield - 53)) | (1L << (Any - 53)) | (1L << (Number - 53)) | (1L << (Boolean - 53)) | (1L << (String - 53)) | (1L << (Symbol - 53)) | (1L << (TypeAlias - 53)))) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & ((1L << (Get - 117)) | (1L << (Set - 117)) | (1L << (Constructor - 117)) | (1L << (Namespace - 117)) | (1L << (Require - 117)) | (1L << (Module - 117)) | (1L << (Declare - 117)) | (1L << (Abstract - 117)) | (1L << (Is - 117)) | (1L << (Infer - 117)) | (1L << (Never - 117)) | (1L << (Unknown - 117)) | (1L << (Asserts - 117)) | (1L << (Identifier - 117)))) != 0)) {
				{
				setState(670);
				typeParameterList();
				}
			}

			setState(673);
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
		enterRule(_localctx, 78, RULE_typeParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(675);
			typeParameter();
			setState(680);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(676);
				match(Comma);
				setState(677);
				typeParameter();
				}
				}
				setState(682);
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
		enterRule(_localctx, 80, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(683);
			identifierName();
			setState(685);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(684);
				constraint();
				}
			}

			setState(689);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(687);
				match(Assign);
				setState(688);
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
		enterRule(_localctx, 82, RULE_constraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(691);
			match(Extends);
			setState(692);
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
		enterRule(_localctx, 84, RULE_defaultType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(694);
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
		enterRule(_localctx, 86, RULE_moduleDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(696);
			match(Module);
			setState(697);
			moduleName();
			setState(698);
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
		enterRule(_localctx, 88, RULE_moduleName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(700);
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
		enterRule(_localctx, 90, RULE_namespaceDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(702);
			match(Namespace);
			setState(703);
			namespaceName();
			setState(704);
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
		enterRule(_localctx, 92, RULE_namespaceName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(706);
			typeReferenceName();
			setState(711);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(707);
					match(Dot);
					setState(708);
					typeReferenceName();
					}
					} 
				}
				setState(713);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
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
		enterRule(_localctx, 94, RULE_decoratorList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(715); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(714);
					decorator();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(717); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
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
		enterRule(_localctx, 96, RULE_decorator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(719);
			match(At);
			setState(722);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
			case 1:
				{
				setState(720);
				decoratorMemberExpression(0);
				}
				break;
			case 2:
				{
				setState(721);
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
		int _startState = 98;
		enterRecursionRule(_localctx, 98, RULE_decoratorMemberExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(730);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(725);
				match(Identifier);
				}
				break;
			case OpenParen:
				{
				setState(726);
				match(OpenParen);
				setState(727);
				singleExpression(0);
				setState(728);
				match(CloseParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(737);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new DecoratorMemberExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_decoratorMemberExpression);
					setState(732);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(733);
					match(Dot);
					setState(734);
					identifierName();
					}
					} 
				}
				setState(739);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
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
		enterRule(_localctx, 100, RULE_decoratorCallExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(740);
			decoratorMemberExpression(0);
			setState(741);
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
		enterRule(_localctx, 102, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(743);
			match(Interface);
			setState(744);
			identifierName();
			setState(746);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(745);
				typeParameters();
				}
			}

			setState(749);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(748);
				interfaceExtendsClause();
				}
			}

			setState(751);
			match(OpenBrace);
			setState(753);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (Plus - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(752);
				interfaceBody();
				}
			}

			setState(755);
			match(CloseBrace);
			setState(757);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
			case 1:
				{
				setState(756);
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
		enterRule(_localctx, 104, RULE_interfaceExtendsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(759);
			match(Extends);
			setState(760);
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
		enterRule(_localctx, 106, RULE_classOrInterfaceTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(762);
			parameterizedTypeRef();
			setState(767);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(763);
				match(Comma);
				setState(764);
				parameterizedTypeRef();
				}
				}
				setState(769);
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
		enterRule(_localctx, 108, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(770);
			interfaceMemberList();
			setState(772);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon || _la==Comma) {
				{
				setState(771);
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
		enterRule(_localctx, 110, RULE_interfaceMemberList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(774);
			interfaceMember();
			setState(783);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,71,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(778);
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
						setState(776);
						match(SemiColon);
						}
						break;
					case Comma:
						{
						setState(777);
						match(Comma);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(780);
					interfaceMember();
					}
					} 
				}
				setState(785);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,71,_ctx);
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
		enterRule(_localctx, 112, RULE_interfaceMember);
		try {
			setState(793);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,72,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(786);
				constructSignature();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(787);
				callSignature();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(788);
				indexSignature();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(789);
				getAccessor();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(790);
				setAccessor();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(791);
				methodSignature();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(792);
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
		enterRule(_localctx, 114, RULE_constructSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(795);
			match(New);
			setState(797);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(796);
				typeParameters();
				}
			}

			setState(799);
			parameterBlock();
			setState(801);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(800);
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
		enterRule(_localctx, 116, RULE_callSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(804);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(803);
				typeParameters();
				}
			}

			setState(806);
			parameterBlock();
			setState(812);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
			case 1:
				{
				setState(807);
				match(Colon);
				setState(810);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,76,_ctx) ) {
				case 1:
					{
					setState(808);
					typePredicateWithOperatorTypeRef();
					}
					break;
				case 2:
					{
					setState(809);
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
		enterRule(_localctx, 118, RULE_indexSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(815);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
			case 1:
				{
				setState(814);
				match(ReadOnly);
				}
				break;
			}
			setState(823);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Plus:
			case ReadOnly:
				{
				setState(818);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Plus) {
					{
					setState(817);
					match(Plus);
					}
				}

				setState(820);
				match(ReadOnly);
				}
				break;
			case Minus:
				{
				setState(821);
				match(Minus);
				setState(822);
				match(ReadOnly);
				}
				break;
			case OpenBracket:
				break;
			default:
				break;
			}
			setState(825);
			match(OpenBracket);
			setState(826);
			indexSignatureElement();
			setState(827);
			match(CloseBracket);
			setState(834);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QuestionMark:
			case Plus:
				{
				setState(829);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Plus) {
					{
					setState(828);
					match(Plus);
					}
				}

				setState(831);
				match(QuestionMark);
				}
				break;
			case Minus:
				{
				setState(832);
				match(Minus);
				setState(833);
				match(QuestionMark);
				}
				break;
			case Colon:
				break;
			default:
				break;
			}
			setState(836);
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
		enterRule(_localctx, 120, RULE_indexSignatureElement);
		int _la;
		try {
			setState(845);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,83,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(838);
				identifierName();
				setState(839);
				match(Colon);
				setState(840);
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
				setState(842);
				match(Identifier);
				setState(843);
				match(In);
				setState(844);
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
		enterRule(_localctx, 122, RULE_methodSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(847);
			propertyName();
			setState(849);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(848);
				match(QuestionMark);
				}
			}

			setState(851);
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
		enterRule(_localctx, 124, RULE_propertySignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(854);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				{
				setState(853);
				match(ReadOnly);
				}
				break;
			}
			setState(856);
			propertyName();
			setState(858);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(857);
				match(QuestionMark);
				}
			}

			setState(861);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(860);
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
		enterRule(_localctx, 126, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(864);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Const) {
				{
				setState(863);
				match(Const);
				}
			}

			setState(866);
			match(Enum);
			setState(867);
			identifierOrKeyWord();
			setState(868);
			match(OpenBrace);
			setState(870);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (Minus - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(869);
				enumBody();
				}
			}

			setState(872);
			match(CloseBrace);
			setState(874);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
			case 1:
				{
				setState(873);
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
		enterRule(_localctx, 128, RULE_enumBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(876);
			enumMemberList();
			setState(878);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(877);
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
		enterRule(_localctx, 130, RULE_enumMemberList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(880);
			enumMember();
			setState(885);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(881);
					match(Comma);
					setState(882);
					enumMember();
					}
					} 
				}
				setState(887);
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
		enterRule(_localctx, 132, RULE_enumMember);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(888);
			propertyName();
			setState(891);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(889);
				match(Assign);
				setState(890);
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
		enterRule(_localctx, 134, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(893);
			match(Function);
			setState(895);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(894);
				match(Multiply);
				}
			}

			setState(897);
			identifierName();
			setState(898);
			callSignature();
			setState(900);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,95,_ctx) ) {
			case 1:
				{
				setState(899);
				block();
				}
				break;
			}
			setState(903);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,96,_ctx) ) {
			case 1:
				{
				setState(902);
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
		enterRule(_localctx, 136, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(906);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Abstract) {
				{
				setState(905);
				match(Abstract);
				}
			}

			setState(908);
			match(Class);
			setState(909);
			identifierOrKeyWord();
			setState(911);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(910);
				typeParameters();
				}
			}

			setState(913);
			classHeritage();
			setState(914);
			classBody();
			setState(916);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,99,_ctx) ) {
			case 1:
				{
				setState(915);
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
		enterRule(_localctx, 138, RULE_classHeritage);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(919);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(918);
				classExtendsClause();
				}
			}

			setState(922);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Implements) {
				{
				setState(921);
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
		enterRule(_localctx, 140, RULE_classExtendsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(924);
			match(Extends);
			setState(925);
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
		enterRule(_localctx, 142, RULE_classImplementsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(927);
			match(Implements);
			setState(928);
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
		enterRule(_localctx, 144, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(930);
			match(OpenBrace);
			setState(932);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (Plus - 5)) | (1L << (Minus - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(931);
				classMemberList();
				}
			}

			setState(935);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon) {
				{
				setState(934);
				match(SemiColon);
				}
			}

			setState(937);
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
		enterRule(_localctx, 146, RULE_classMemberList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(939);
			classMember();
			setState(947);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(942);
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
						setState(941);
						match(SemiColon);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(944);
					classMember();
					}
					} 
				}
				setState(949);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
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
		enterRule(_localctx, 148, RULE_classMember);
		int _la;
		try {
			setState(956);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,107,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(950);
				constructorDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(951);
				indexSignature();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(953);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==At) {
					{
					setState(952);
					decoratorList();
					}
				}

				setState(955);
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
		enterRule(_localctx, 150, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(959);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 104)) & ~0x3f) == 0 && ((1L << (_la - 104)) & ((1L << (Private - 104)) | (1L << (Public - 104)) | (1L << (Protected - 104)))) != 0)) {
				{
				setState(958);
				accessibilityModifier();
				}
			}

			setState(961);
			match(Constructor);
			setState(962);
			parameterBlock();
			setState(964);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(963);
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
		enterRule(_localctx, 152, RULE_propertyMemberDeclaration);
		try {
			setState(968);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,110,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(966);
				abstractDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(967);
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
		enterRule(_localctx, 154, RULE_abstractDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(970);
			match(Abstract);
			setState(974);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(971);
				match(Identifier);
				setState(972);
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
				setState(973);
				variableStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(976);
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
		enterRule(_localctx, 156, RULE_propertyMember);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(978);
			propertyMemberBase();
			setState(982);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,112,_ctx) ) {
			case 1:
				{
				setState(979);
				getAccessor();
				}
				break;
			case 2:
				{
				setState(980);
				setAccessor();
				}
				break;
			case 3:
				{
				setState(981);
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
		enterRule(_localctx, 158, RULE_propertyMemberBase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(985);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,113,_ctx) ) {
			case 1:
				{
				setState(984);
				match(Async);
				}
				break;
			}
			setState(988);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,114,_ctx) ) {
			case 1:
				{
				setState(987);
				accessibilityModifier();
				}
				break;
			}
			setState(991);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,115,_ctx) ) {
			case 1:
				{
				setState(990);
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
		enterRule(_localctx, 160, RULE_propertyOrMethod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(994);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,116,_ctx) ) {
			case 1:
				{
				setState(993);
				match(ReadOnly);
				}
				break;
			}
			setState(996);
			propertyName();
			setState(998);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(997);
				match(QuestionMark);
				}
			}

			setState(1010);
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
				setState(1001);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(1000);
					colonSepTypeRef();
					}
				}

				setState(1004);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Assign) {
					{
					setState(1003);
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
				setState(1006);
				callSignature();
				setState(1008);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OpenBrace) {
					{
					setState(1007);
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
		enterRule(_localctx, 162, RULE_initializer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1012);
			match(Assign);
			setState(1013);
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
		public ParameterListTrailingCommaContext parameterListTrailingComma() {
			return getRuleContext(ParameterListTrailingCommaContext.class,0);
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
		enterRule(_localctx, 164, RULE_parameterBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1015);
			match(OpenParen);
			setState(1017);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenBrace - 5)) | (1L << (Ellipsis - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(1016);
				parameterListTrailingComma();
				}
			}

			setState(1019);
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
		enterRule(_localctx, 166, RULE_parameterListTrailingComma);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1021);
			parameterList();
			setState(1023);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1022);
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
		enterRule(_localctx, 168, RULE_parameterList);
		try {
			int _alt;
			setState(1038);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Ellipsis:
				enterOuterAlt(_localctx, 1);
				{
				setState(1025);
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
				setState(1026);
				parameter();
				setState(1031);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,124,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1027);
						match(Comma);
						setState(1028);
						parameter();
						}
						} 
					}
					setState(1033);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,124,_ctx);
				}
				setState(1036);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,125,_ctx) ) {
				case 1:
					{
					setState(1034);
					match(Comma);
					setState(1035);
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
		enterRule(_localctx, 170, RULE_restParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1040);
			match(Ellipsis);
			setState(1041);
			identifierOrPattern();
			setState(1043);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1042);
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
		enterRule(_localctx, 172, RULE_parameter);
		try {
			setState(1047);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,128,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1045);
				requiredParameter();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1046);
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
		enterRule(_localctx, 174, RULE_requiredParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1050);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==At) {
				{
				setState(1049);
				decoratorList();
				}
			}

			setState(1053);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,130,_ctx) ) {
			case 1:
				{
				setState(1052);
				accessibilityModifier();
				}
				break;
			}
			setState(1055);
			identifierOrPattern();
			setState(1057);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1056);
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
		enterRule(_localctx, 176, RULE_optionalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1060);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==At) {
				{
				setState(1059);
				decoratorList();
				}
			}

			setState(1063);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,133,_ctx) ) {
			case 1:
				{
				setState(1062);
				accessibilityModifier();
				}
				break;
			}
			setState(1065);
			identifierOrPattern();
			setState(1074);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QuestionMark:
				{
				setState(1066);
				match(QuestionMark);
				setState(1068);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(1067);
					colonSepTypeRef();
					}
				}

				}
				break;
			case Assign:
			case Colon:
				{
				setState(1071);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(1070);
					colonSepTypeRef();
					}
				}

				setState(1073);
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
		enterRule(_localctx, 178, RULE_accessibilityModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1076);
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
		enterRule(_localctx, 180, RULE_identifierOrPattern);
		try {
			setState(1080);
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
				setState(1078);
				identifierName();
				}
				break;
			case OpenBracket:
			case OpenBrace:
				enterOuterAlt(_localctx, 2);
				{
				setState(1079);
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
		enterRule(_localctx, 182, RULE_bindingPattern);
		try {
			setState(1084);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
				enterOuterAlt(_localctx, 1);
				{
				setState(1082);
				arrayLiteral();
				}
				break;
			case OpenBrace:
				enterOuterAlt(_localctx, 2);
				{
				setState(1083);
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
		enterRule(_localctx, 184, RULE_arrayLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1086);
			match(OpenBracket);
			setState(1088);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBracket) | (1L << OpenBrace) | (1L << Ellipsis))) != 0) || _la==Identifier) {
				{
				setState(1087);
				elementList();
				}
			}

			setState(1090);
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
		enterRule(_localctx, 186, RULE_elementList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1092);
			arrayElement();
			setState(1101);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,141,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1094); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(1093);
						match(Comma);
						}
						}
						setState(1096); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==Comma );
					setState(1098);
					arrayElement();
					}
					} 
				}
				setState(1103);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,141,_ctx);
			}
			setState(1105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1104);
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
		enterRule(_localctx, 188, RULE_arrayElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(1107);
				match(Ellipsis);
				}
			}

			setState(1110);
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
		enterRule(_localctx, 190, RULE_bindingElement);
		try {
			setState(1114);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
			case OpenBrace:
				enterOuterAlt(_localctx, 1);
				{
				setState(1112);
				bindingPattern();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1113);
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
		enterRule(_localctx, 192, RULE_objectLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1116);
			match(OpenBrace);
			setState(1128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (Ellipsis - 5)) | (1L << (Minus - 5)) | (1L << (Multiply - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Target - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Await - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(1117);
				propertyAssignment();
				setState(1122);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,145,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1118);
						match(Comma);
						setState(1119);
						propertyAssignment();
						}
						} 
					}
					setState(1124);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,145,_ctx);
				}
				setState(1126);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(1125);
					match(Comma);
					}
				}

				}
			}

			setState(1130);
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
		enterRule(_localctx, 194, RULE_propertyAssignment);
		int _la;
		try {
			setState(1146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,150,_ctx) ) {
			case 1:
				_localctx = new PropertyExpressionAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1132);
				propertyName();
				setState(1136);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Colon:
					{
					setState(1133);
					match(Colon);
					setState(1134);
					identifierOrKeyWord();
					}
					break;
				case OpenBracket:
				case OpenBrace:
					{
					setState(1135);
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
				setState(1140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Assign) {
					{
					setState(1138);
					match(Assign);
					setState(1139);
					singleExpression(0);
					}
				}

				}
				break;
			case 2:
				_localctx = new PropertyGetterContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1142);
				getAccessor();
				}
				break;
			case 3:
				_localctx = new PropertySetterContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1143);
				setAccessor();
				}
				break;
			case 4:
				_localctx = new MethodPropertyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1144);
				generatorMethod();
				}
				break;
			case 5:
				_localctx = new RestParameterInObjectContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1145);
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
		enterRule(_localctx, 196, RULE_propertyName);
		try {
			setState(1160);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case StringLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1148);
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
				setState(1149);
				numericLiteral();
				}
				break;
			case OpenBracket:
				enterOuterAlt(_localctx, 3);
				{
				setState(1150);
				match(OpenBracket);
				setState(1156);
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
					setState(1151);
					identifierName();
					setState(1152);
					match(Dot);
					setState(1153);
					identifierName();
					}
					break;
				case StringLiteral:
					{
					setState(1155);
					match(StringLiteral);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1158);
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
				setState(1159);
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
		enterRule(_localctx, 198, RULE_getAccessor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1162);
			getter();
			setState(1163);
			match(OpenParen);
			setState(1164);
			match(CloseParen);
			setState(1166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1165);
				colonSepTypeRef();
				}
			}

			setState(1169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(1168);
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
		enterRule(_localctx, 200, RULE_setAccessor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1171);
			setter();
			setState(1172);
			match(OpenParen);
			setState(1175);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(1173);
				match(Identifier);
				}
				break;
			case OpenBracket:
			case OpenBrace:
				{
				setState(1174);
				bindingPattern();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1178);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1177);
				colonSepTypeRef();
				}
			}

			setState(1180);
			match(CloseParen);
			setState(1182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(1181);
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
		enterRule(_localctx, 202, RULE_generatorMethod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1185);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(1184);
				match(Multiply);
				}
			}

			setState(1187);
			match(Identifier);
			setState(1188);
			parameterBlock();
			setState(1189);
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
		enterRule(_localctx, 204, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1191);
			match(OpenParen);
			setState(1196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << Ellipsis) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
				{
				setState(1192);
				argumentList();
				setState(1194);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(1193);
					match(Comma);
					}
				}

				}
			}

			setState(1198);
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
		enterRule(_localctx, 206, RULE_argumentList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1200);
			argument();
			setState(1205);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,161,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1201);
					match(Comma);
					setState(1202);
					argument();
					}
					} 
				}
				setState(1207);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,161,_ctx);
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
		enterRule(_localctx, 208, RULE_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1209);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(1208);
				match(Ellipsis);
				}
			}

			setState(1213);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,163,_ctx) ) {
			case 1:
				{
				setState(1211);
				singleExpression(0);
				}
				break;
			case 2:
				{
				setState(1212);
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
		enterRule(_localctx, 210, RULE_importStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1215);
			match(Import);
			setState(1219);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,164,_ctx) ) {
			case 1:
				{
				setState(1216);
				importFromBlock();
				}
				break;
			case 2:
				{
				setState(1217);
				importAliasDeclaration();
				}
				break;
			case 3:
				{
				setState(1218);
				match(StringLiteral);
				}
				break;
			}
			setState(1221);
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
		enterRule(_localctx, 212, RULE_importFromBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1224);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,165,_ctx) ) {
			case 1:
				{
				setState(1223);
				match(TypeAlias);
				}
				break;
			}
			setState(1231);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,166,_ctx) ) {
			case 1:
				{
				setState(1226);
				match(Multiply);
				setState(1227);
				match(As);
				setState(1228);
				identifierName();
				}
				break;
			case 2:
				{
				setState(1229);
				identifierName();
				}
				break;
			case 3:
				{
				setState(1230);
				multipleImportElements();
				}
				break;
			}
			setState(1233);
			match(From);
			setState(1234);
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
		enterRule(_localctx, 214, RULE_multipleImportElements);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (NullLiteral - 53)) | (1L << (UndefinedLiteral - 53)) | (1L << (BooleanLiteral - 53)) | (1L << (Break - 53)) | (1L << (Do - 53)) | (1L << (Instanceof - 53)) | (1L << (Typeof - 53)) | (1L << (Unique - 53)) | (1L << (Case - 53)) | (1L << (Else - 53)) | (1L << (New - 53)) | (1L << (Target - 53)) | (1L << (Var - 53)) | (1L << (Catch - 53)) | (1L << (Finally - 53)) | (1L << (Return - 53)) | (1L << (Void - 53)) | (1L << (Continue - 53)) | (1L << (For - 53)) | (1L << (Switch - 53)) | (1L << (While - 53)) | (1L << (Debugger - 53)) | (1L << (Function - 53)) | (1L << (This - 53)) | (1L << (With - 53)) | (1L << (Default - 53)) | (1L << (If - 53)) | (1L << (Throw - 53)) | (1L << (Delete - 53)) | (1L << (In - 53)) | (1L << (Try - 53)) | (1L << (As - 53)) | (1L << (From - 53)) | (1L << (ReadOnly - 53)) | (1L << (Async - 53)) | (1L << (Await - 53)) | (1L << (Class - 53)) | (1L << (Enum - 53)) | (1L << (Extends - 53)) | (1L << (Super - 53)) | (1L << (Const - 53)) | (1L << (Export - 53)) | (1L << (Import - 53)) | (1L << (Implements - 53)) | (1L << (Let - 53)) | (1L << (Private - 53)) | (1L << (Public - 53)) | (1L << (Interface - 53)) | (1L << (Package - 53)) | (1L << (Protected - 53)) | (1L << (Static - 53)) | (1L << (Yield - 53)) | (1L << (Any - 53)) | (1L << (Number - 53)) | (1L << (Boolean - 53)) | (1L << (String - 53)) | (1L << (Symbol - 53)) | (1L << (TypeAlias - 53)))) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & ((1L << (Get - 117)) | (1L << (Set - 117)) | (1L << (Constructor - 117)) | (1L << (Namespace - 117)) | (1L << (Require - 117)) | (1L << (Module - 117)) | (1L << (Declare - 117)) | (1L << (Abstract - 117)) | (1L << (Is - 117)) | (1L << (Infer - 117)) | (1L << (Never - 117)) | (1L << (Unknown - 117)) | (1L << (Asserts - 117)) | (1L << (Identifier - 117)))) != 0)) {
				{
				setState(1236);
				identifierName();
				setState(1237);
				match(Comma);
				}
			}

			setState(1241);
			match(OpenBrace);
			setState(1242);
			importedElement();
			setState(1247);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,168,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1243);
					match(Comma);
					setState(1244);
					importedElement();
					}
					} 
				}
				setState(1249);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,168,_ctx);
			}
			setState(1251);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1250);
				match(Comma);
				}
			}

			setState(1253);
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
		enterRule(_localctx, 216, RULE_importedElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1255);
			identifierName();
			setState(1258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==As) {
				{
				setState(1256);
				match(As);
				setState(1257);
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
		enterRule(_localctx, 218, RULE_importAliasDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1260);
			match(Identifier);
			setState(1261);
			match(Assign);
			setState(1267);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,171,_ctx) ) {
			case 1:
				{
				setState(1262);
				namespaceName();
				}
				break;
			case 2:
				{
				setState(1263);
				match(Require);
				setState(1264);
				match(OpenParen);
				setState(1265);
				match(StringLiteral);
				setState(1266);
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
		enterRule(_localctx, 220, RULE_exportStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1269);
			match(Export);
			setState(1270);
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
	public static class ExportElementContext extends ExportStatementTailContext {
		public ExportFromBlockContext exportFromBlock() {
			return getRuleContext(ExportFromBlockContext.class,0);
		}
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public TerminalNode Default() { return getToken(TypeScriptParser.Default, 0); }
		public ExportElementContext(ExportStatementTailContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportElement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExportDeclareStatementContext extends ExportStatementTailContext {
		public DeclareStatementContext declareStatement() {
			return getRuleContext(DeclareStatementContext.class,0);
		}
		public TerminalNode Default() { return getToken(TypeScriptParser.Default, 0); }
		public ExportDeclareStatementContext(ExportStatementTailContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportDeclareStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportDeclareStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportDeclareStatement(this);
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

	public final ExportStatementTailContext exportStatementTail() throws RecognitionException {
		ExportStatementTailContext _localctx = new ExportStatementTailContext(_ctx, getState());
		enterRule(_localctx, 222, RULE_exportStatementTail);
		int _la;
		try {
			setState(1297);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,174,_ctx) ) {
			case 1:
				_localctx = new ExportAsNamespaceContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1272);
				match(As);
				setState(1273);
				match(Namespace);
				setState(1274);
				identifierName();
				setState(1275);
				eos();
				}
				break;
			case 2:
				_localctx = new ExportEqualsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1277);
				match(Assign);
				setState(1278);
				namespaceName();
				setState(1279);
				eos();
				}
				break;
			case 3:
				_localctx = new ExportImportContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1281);
				match(Import);
				setState(1282);
				identifierName();
				setState(1283);
				match(Assign);
				setState(1284);
				namespaceName();
				setState(1285);
				eos();
				}
				break;
			case 4:
				_localctx = new ExportDeclareStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1288);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Default) {
					{
					setState(1287);
					match(Default);
					}
				}

				setState(1290);
				declareStatement();
				}
				break;
			case 5:
				_localctx = new ExportElementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1292);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,173,_ctx) ) {
				case 1:
					{
					setState(1291);
					match(Default);
					}
					break;
				}
				setState(1294);
				exportFromBlock();
				setState(1295);
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

	public static class ExportFromBlockContext extends ParserRuleContext {
		public TerminalNode Multiply() { return getToken(TypeScriptParser.Multiply, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public MultipleImportElementsContext multipleImportElements() {
			return getRuleContext(MultipleImportElementsContext.class,0);
		}
		public TerminalNode From() { return getToken(TypeScriptParser.From, 0); }
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public TerminalNode As() { return getToken(TypeScriptParser.As, 0); }
		public ExportFromBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exportFromBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterExportFromBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitExportFromBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitExportFromBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExportFromBlockContext exportFromBlock() throws RecognitionException {
		ExportFromBlockContext _localctx = new ExportFromBlockContext(_ctx, getState());
		enterRule(_localctx, 224, RULE_exportFromBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1306);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,176,_ctx) ) {
			case 1:
				{
				setState(1299);
				match(Multiply);
				setState(1302);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,175,_ctx) ) {
				case 1:
					{
					setState(1300);
					match(As);
					setState(1301);
					identifierName();
					}
					break;
				}
				}
				break;
			case 2:
				{
				setState(1304);
				identifierName();
				}
				break;
			case 3:
				{
				setState(1305);
				multipleImportElements();
				}
				break;
			}
			setState(1310);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,177,_ctx) ) {
			case 1:
				{
				setState(1308);
				match(From);
				setState(1309);
				match(StringLiteral);
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
		enterRule(_localctx, 226, RULE_variableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 104)) & ~0x3f) == 0 && ((1L << (_la - 104)) & ((1L << (Private - 104)) | (1L << (Public - 104)) | (1L << (Protected - 104)))) != 0)) {
				{
				setState(1312);
				accessibilityModifier();
				}
			}

			setState(1316);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ReadOnly) {
				{
				setState(1315);
				match(ReadOnly);
				}
			}

			setState(1318);
			varModifier();
			setState(1321);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
			case OpenBrace:
				{
				setState(1319);
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
				setState(1320);
				variableDeclarationList();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1324);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,181,_ctx) ) {
			case 1:
				{
				setState(1323);
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
		enterRule(_localctx, 228, RULE_varModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1326);
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
		enterRule(_localctx, 230, RULE_bindingPatternBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1328);
			bindingPattern();
			setState(1330);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,182,_ctx) ) {
			case 1:
				{
				setState(1329);
				colonSepTypeRef();
				}
				break;
			}
			setState(1333);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,183,_ctx) ) {
			case 1:
				{
				setState(1332);
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
		enterRule(_localctx, 232, RULE_variableDeclarationList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1335);
			variableDeclaration();
			setState(1340);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,184,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1336);
					match(Comma);
					setState(1337);
					variableDeclaration();
					}
					} 
				}
				setState(1342);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,184,_ctx);
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
		enterRule(_localctx, 234, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1343);
			identifierName();
			setState(1345);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,185,_ctx) ) {
			case 1:
				{
				setState(1344);
				colonSepTypeRef();
				}
				break;
			}
			setState(1352);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,187,_ctx) ) {
			case 1:
				{
				setState(1347);
				match(Assign);
				setState(1349);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LessThan) {
					{
					setState(1348);
					typeParameters();
					}
				}

				setState(1351);
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
		enterRule(_localctx, 236, RULE_switchStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1354);
			match(Switch);
			setState(1355);
			match(OpenParen);
			setState(1356);
			expressionSequence();
			setState(1357);
			match(CloseParen);
			setState(1358);
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
		enterRule(_localctx, 238, RULE_caseBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1360);
			match(OpenBrace);
			setState(1362);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Case) {
				{
				setState(1361);
				caseClauses();
				}
			}

			setState(1368);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Default) {
				{
				setState(1364);
				defaultClause();
				setState(1366);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Case) {
					{
					setState(1365);
					caseClauses();
					}
				}

				}
			}

			setState(1370);
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
		enterRule(_localctx, 240, RULE_caseClauses);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1373); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1372);
				caseClause();
				}
				}
				setState(1375); 
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
		enterRule(_localctx, 242, RULE_caseClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1377);
			match(Case);
			setState(1378);
			expressionSequence();
			setState(1379);
			match(Colon);
			setState(1381);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Return - 71)) | (1L << (Continue - 71)) | (1L << (For - 71)) | (1L << (Switch - 71)) | (1L << (While - 71)) | (1L << (Debugger - 71)) | (1L << (Function - 71)) | (1L << (With - 71)) | (1L << (If - 71)) | (1L << (Throw - 71)) | (1L << (Try - 71)) | (1L << (ReadOnly - 71)) | (1L << (Class - 71)) | (1L << (Enum - 71)) | (1L << (Const - 71)) | (1L << (Export - 71)) | (1L << (Import - 71)) | (1L << (Let - 71)) | (1L << (Private - 71)) | (1L << (Public - 71)) | (1L << (Interface - 71)) | (1L << (Protected - 71)) | (1L << (TypeAlias - 71)) | (1L << (Namespace - 71)) | (1L << (Module - 71)) | (1L << (Declare - 71)) | (1L << (Abstract - 71)) | (1L << (At - 71)) | (1L << (Identifier - 71)))) != 0)) {
				{
				setState(1380);
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
		enterRule(_localctx, 244, RULE_defaultClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1383);
			match(Default);
			setState(1384);
			match(Colon);
			setState(1386);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (Var - 71)) | (1L << (Return - 71)) | (1L << (Continue - 71)) | (1L << (For - 71)) | (1L << (Switch - 71)) | (1L << (While - 71)) | (1L << (Debugger - 71)) | (1L << (Function - 71)) | (1L << (With - 71)) | (1L << (If - 71)) | (1L << (Throw - 71)) | (1L << (Try - 71)) | (1L << (ReadOnly - 71)) | (1L << (Class - 71)) | (1L << (Enum - 71)) | (1L << (Const - 71)) | (1L << (Export - 71)) | (1L << (Import - 71)) | (1L << (Let - 71)) | (1L << (Private - 71)) | (1L << (Public - 71)) | (1L << (Interface - 71)) | (1L << (Protected - 71)) | (1L << (TypeAlias - 71)) | (1L << (Namespace - 71)) | (1L << (Module - 71)) | (1L << (Declare - 71)) | (1L << (Abstract - 71)) | (1L << (At - 71)) | (1L << (Identifier - 71)))) != 0)) {
				{
				setState(1385);
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
		enterRule(_localctx, 246, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1388);
			match(Try);
			setState(1389);
			block();
			setState(1395);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Catch:
				{
				setState(1390);
				catchProduction();
				setState(1392);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Finally) {
					{
					setState(1391);
					finallyProduction();
					}
				}

				}
				break;
			case Finally:
				{
				setState(1394);
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
		enterRule(_localctx, 248, RULE_catchProduction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1397);
			match(Catch);
			setState(1398);
			match(OpenParen);
			setState(1399);
			match(Identifier);
			setState(1400);
			match(CloseParen);
			setState(1401);
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
		enterRule(_localctx, 250, RULE_finallyProduction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1403);
			match(Finally);
			setState(1404);
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
		enterRule(_localctx, 252, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1406);
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
		enterRule(_localctx, 254, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1408);
			match(If);
			setState(1409);
			match(OpenParen);
			setState(1410);
			expressionSequence();
			setState(1411);
			match(CloseParen);
			setState(1412);
			statement();
			setState(1415);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,196,_ctx) ) {
			case 1:
				{
				setState(1413);
				match(Else);
				setState(1414);
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
		enterRule(_localctx, 256, RULE_iterationStatement);
		int _la;
		try {
			setState(1486);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,204,_ctx) ) {
			case 1:
				_localctx = new DoStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1417);
				match(Do);
				setState(1418);
				statement();
				setState(1419);
				match(While);
				setState(1420);
				match(OpenParen);
				setState(1421);
				expressionSequence();
				setState(1422);
				match(CloseParen);
				setState(1423);
				eos();
				}
				break;
			case 2:
				_localctx = new WhileStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1425);
				match(While);
				setState(1426);
				match(OpenParen);
				setState(1427);
				expressionSequence();
				setState(1428);
				match(CloseParen);
				setState(1429);
				statement();
				}
				break;
			case 3:
				_localctx = new ForStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1431);
				match(For);
				setState(1432);
				match(OpenParen);
				setState(1434);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1433);
					expressionSequence();
					}
				}

				setState(1436);
				match(SemiColon);
				setState(1438);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1437);
					expressionSequence();
					}
				}

				setState(1440);
				match(SemiColon);
				setState(1442);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1441);
					expressionSequence();
					}
				}

				setState(1444);
				match(CloseParen);
				setState(1445);
				statement();
				}
				break;
			case 4:
				_localctx = new ForVarStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1446);
				match(For);
				setState(1447);
				match(OpenParen);
				setState(1448);
				varModifier();
				setState(1449);
				variableDeclarationList();
				setState(1450);
				match(SemiColon);
				setState(1452);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1451);
					expressionSequence();
					}
				}

				setState(1454);
				match(SemiColon);
				setState(1456);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RegularExpressionLiteral) | (1L << OpenBracket) | (1L << OpenParen) | (1L << OpenBrace) | (1L << PlusPlus) | (1L << MinusMinus) | (1L << Plus) | (1L << Minus) | (1L << BitNot) | (1L << Not) | (1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << BooleanLiteral) | (1L << DecimalLiteral) | (1L << HexIntegerLiteral) | (1L << OctalIntegerLiteral) | (1L << OctalIntegerLiteral2) | (1L << BinaryIntegerLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Typeof - 64)) | (1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Target - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (ReadOnly - 64)) | (1L << (Async - 64)) | (1L << (Await - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (Unknown - 128)) | (1L << (Asserts - 128)) | (1L << (Identifier - 128)) | (1L << (StringLiteral - 128)) | (1L << (BackTick - 128)))) != 0)) {
					{
					setState(1455);
					expressionSequence();
					}
				}

				setState(1458);
				match(CloseParen);
				setState(1459);
				statement();
				}
				break;
			case 5:
				_localctx = new ForInStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1461);
				match(For);
				setState(1462);
				match(OpenParen);
				setState(1463);
				singleExpression(0);
				setState(1467);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case In:
					{
					setState(1464);
					match(In);
					}
					break;
				case Identifier:
					{
					setState(1465);
					match(Identifier);
					setState(1466);
					if (!(this.p("of"))) throw new FailedPredicateException(this, "this.p(\"of\")");
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1469);
				expressionSequence();
				setState(1470);
				match(CloseParen);
				setState(1471);
				statement();
				}
				break;
			case 6:
				_localctx = new ForVarInStatementContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1473);
				match(For);
				setState(1474);
				match(OpenParen);
				setState(1475);
				varModifier();
				setState(1476);
				variableDeclaration();
				setState(1480);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case In:
					{
					setState(1477);
					match(In);
					}
					break;
				case Identifier:
					{
					setState(1478);
					match(Identifier);
					setState(1479);
					if (!(this.p("of"))) throw new FailedPredicateException(this, "this.p(\"of\")");
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1482);
				expressionSequence();
				setState(1483);
				match(CloseParen);
				setState(1484);
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
		enterRule(_localctx, 258, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1488);
			match(Continue);
			setState(1491);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,205,_ctx) ) {
			case 1:
				{
				setState(1489);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1490);
				match(Identifier);
				}
				break;
			}
			setState(1493);
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
		enterRule(_localctx, 260, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1495);
			match(Break);
			setState(1498);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,206,_ctx) ) {
			case 1:
				{
				setState(1496);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1497);
				match(Identifier);
				}
				break;
			}
			setState(1500);
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
		enterRule(_localctx, 262, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1502);
			match(Return);
			setState(1505);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,207,_ctx) ) {
			case 1:
				{
				setState(1503);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1504);
				expressionSequence();
				}
				break;
			}
			setState(1507);
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
		enterRule(_localctx, 264, RULE_withStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1509);
			match(With);
			setState(1510);
			match(OpenParen);
			setState(1511);
			expressionSequence();
			setState(1512);
			match(CloseParen);
			setState(1513);
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
		enterRule(_localctx, 266, RULE_labelledStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1515);
			match(Identifier);
			setState(1516);
			match(Colon);
			setState(1517);
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
		enterRule(_localctx, 268, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1519);
			match(Throw);
			setState(1520);
			if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
			setState(1521);
			expressionSequence();
			setState(1522);
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
		enterRule(_localctx, 270, RULE_debuggerStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1524);
			match(Debugger);
			setState(1525);
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
		enterRule(_localctx, 272, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1527);
			if (!(this.notOpenBraceAndNotFunction())) throw new FailedPredicateException(this, "this.notOpenBraceAndNotFunction()");
			setState(1528);
			expressionSequence();
			setState(1529);
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
		enterRule(_localctx, 274, RULE_expressionSequence);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1531);
			singleExpression(0);
			setState(1536);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,208,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1532);
					match(Comma);
					setState(1533);
					singleExpression(0);
					}
					} 
				}
				setState(1538);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,208,_ctx);
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
		int _startState = 276;
		enterRecursionRule(_localctx, 276, RULE_singleExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1565);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,210,_ctx) ) {
			case 1:
				{
				_localctx = new ThisExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1540);
				match(This);
				}
				break;
			case 2:
				{
				_localctx = new SuperExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1541);
				match(Super);
				}
				break;
			case 3:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1542);
				literal();
				}
				break;
			case 4:
				{
				_localctx = new ArrayLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1543);
				arrayLiteral();
				}
				break;
			case 5:
				{
				_localctx = new ObjectLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1544);
				objectLiteral();
				}
				break;
			case 6:
				{
				_localctx = new TemplateStringExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1545);
				templateStringLiteral();
				}
				break;
			case 7:
				{
				_localctx = new YieldExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1546);
				match(Yield);
				setState(1549);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,209,_ctx) ) {
				case 1:
					{
					setState(1547);
					if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
					setState(1548);
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
				setState(1551);
				match(Await);
				setState(1552);
				singleExpression(23);
				}
				break;
			case 9:
				{
				_localctx = new UnaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1553);
				unaryOperator();
				setState(1554);
				singleExpression(12);
				}
				break;
			case 10:
				{
				_localctx = new NewExpressionLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1556);
				newExpression();
				}
				break;
			case 11:
				{
				_localctx = new FunctionExpressionLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1557);
				functionExpression();
				}
				break;
			case 12:
				{
				_localctx = new ArrowFunctionExpressionLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1558);
				arrowFunctionExpression();
				}
				break;
			case 13:
				{
				_localctx = new ClassExpressionLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1559);
				classExpression();
				}
				break;
			case 14:
				{
				_localctx = new IdentifierExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1560);
				identifierName();
				}
				break;
			case 15:
				{
				_localctx = new ParenthesizedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1561);
				match(OpenParen);
				setState(1562);
				expressionSequence();
				setState(1563);
				match(CloseParen);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1645);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,217,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1643);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,216,_ctx) ) {
					case 1:
						{
						_localctx = new AssignmentExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1567);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(1568);
						assignmentOperator();
						setState(1569);
						singleExpression(23);
						}
						break;
					case 2:
						{
						_localctx = new TernaryExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1571);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(1572);
						match(QuestionMark);
						setState(1573);
						singleExpression(0);
						setState(1574);
						match(Colon);
						setState(1575);
						singleExpression(22);
						}
						break;
					case 3:
						{
						_localctx = new CoalesceExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1577);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(1578);
						match(QuestionMark);
						setState(1579);
						match(QuestionMark);
						setState(1580);
						singleExpression(21);
						}
						break;
					case 4:
						{
						_localctx = new LogicalExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1581);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(1582);
						_la = _input.LA(1);
						if ( !(_la==And || _la==Or) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1583);
						singleExpression(20);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1584);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(1585);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BitAnd) | (1L << BitXOr) | (1L << BitOr))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1586);
						singleExpression(19);
						}
						break;
					case 6:
						{
						_localctx = new EqualityExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1587);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(1588);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Equals_) | (1L << NotEquals) | (1L << IdentityEquals) | (1L << IdentityNotEquals))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1589);
						singleExpression(18);
						}
						break;
					case 7:
						{
						_localctx = new RelationalExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1590);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(1591);
						relationalOperator();
						setState(1592);
						singleExpression(17);
						}
						break;
					case 8:
						{
						_localctx = new BitShiftExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1594);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(1601);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,211,_ctx) ) {
						case 1:
							{
							setState(1595);
							match(LeftShiftArithmetic);
							}
							break;
						case 2:
							{
							setState(1596);
							match(MoreThan);
							setState(1597);
							match(MoreThan);
							}
							break;
						case 3:
							{
							setState(1598);
							match(MoreThan);
							setState(1599);
							match(MoreThan);
							setState(1600);
							match(MoreThan);
							}
							break;
						}
						setState(1603);
						singleExpression(16);
						}
						break;
					case 9:
						{
						_localctx = new AdditiveExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1604);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(1605);
						_la = _input.LA(1);
						if ( !(_la==Plus || _la==Minus) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1606);
						singleExpression(15);
						}
						break;
					case 10:
						{
						_localctx = new MultiplicativeExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1607);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(1608);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Multiply) | (1L << Divide) | (1L << Modulus))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1609);
						singleExpression(14);
						}
						break;
					case 11:
						{
						_localctx = new CastAsExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1610);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(1611);
						match(As);
						setState(1612);
						typeRef();
						}
						break;
					case 12:
						{
						_localctx = new PostfixExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1613);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(1614);
						if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
						setState(1615);
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
						setState(1616);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(1617);
						match(QuestionMark);
						setState(1619);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==Dot) {
							{
							setState(1618);
							match(Dot);
							}
						}

						setState(1621);
						match(OpenBracket);
						setState(1622);
						expressionSequence();
						setState(1623);
						match(CloseBracket);
						}
						break;
					case 14:
						{
						_localctx = new CallExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1625);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(1626);
						match(QuestionMark);
						setState(1628);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==Dot) {
							{
							setState(1627);
							match(Dot);
							}
						}

						setState(1630);
						identifierName();
						setState(1632);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LessThan) {
							{
							setState(1631);
							typeArguments();
							}
						}

						setState(1634);
						arguments();
						}
						break;
					case 15:
						{
						_localctx = new PropertyAccessExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1636);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(1640);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
						case Dot:
							{
							setState(1637);
							match(Dot);
							}
							break;
						case QuestionMark:
							{
							setState(1638);
							match(QuestionMark);
							setState(1639);
							match(Dot);
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(1642);
						identifierName();
						}
						break;
					}
					} 
				}
				setState(1647);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,217,_ctx);
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
		enterRule(_localctx, 278, RULE_functionExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1648);
			match(Function);
			setState(1650);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(1649);
				match(Multiply);
				}
			}

			setState(1653);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1652);
				match(Identifier);
				}
			}

			setState(1655);
			parameterBlock();
			setState(1657);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1656);
				colonSepTypeRef();
				}
			}

			setState(1659);
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
		enterRule(_localctx, 280, RULE_arrowFunctionExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1662);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Async) {
				{
				setState(1661);
				match(Async);
				}
			}

			setState(1664);
			parameterBlock();
			setState(1666);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1665);
				colonSepTypeRef();
				}
			}

			setState(1668);
			match(ARROW);
			setState(1669);
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
		enterRule(_localctx, 282, RULE_arrowFunctionBody);
		try {
			setState(1673);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,223,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1671);
				singleExpression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1672);
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
		enterRule(_localctx, 284, RULE_classExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1675);
			match(Class);
			setState(1677);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1676);
				match(Identifier);
				}
			}

			setState(1679);
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
		enterRule(_localctx, 286, RULE_assignmentOperator);
		int _la;
		try {
			setState(1698);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Assign:
				enterOuterAlt(_localctx, 1);
				{
				setState(1681);
				match(Assign);
				}
				break;
			case MultiplyAssign:
				enterOuterAlt(_localctx, 2);
				{
				setState(1682);
				match(MultiplyAssign);
				}
				break;
			case DivideAssign:
				enterOuterAlt(_localctx, 3);
				{
				setState(1683);
				match(DivideAssign);
				}
				break;
			case ModulusAssign:
				enterOuterAlt(_localctx, 4);
				{
				setState(1684);
				match(ModulusAssign);
				}
				break;
			case PlusAssign:
				enterOuterAlt(_localctx, 5);
				{
				setState(1685);
				match(PlusAssign);
				}
				break;
			case Minus:
				enterOuterAlt(_localctx, 6);
				{
				setState(1686);
				match(Minus);
				setState(1687);
				match(Assign);
				}
				break;
			case LeftShiftArithmeticAssign:
				enterOuterAlt(_localctx, 7);
				{
				setState(1688);
				match(LeftShiftArithmeticAssign);
				}
				break;
			case MoreThan:
				enterOuterAlt(_localctx, 8);
				{
				setState(1689);
				match(MoreThan);
				setState(1690);
				match(MoreThan);
				setState(1692);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MoreThan) {
					{
					setState(1691);
					match(MoreThan);
					}
				}

				setState(1694);
				match(Assign);
				}
				break;
			case BitAndAssign:
				enterOuterAlt(_localctx, 9);
				{
				setState(1695);
				match(BitAndAssign);
				}
				break;
			case BitXorAssign:
				enterOuterAlt(_localctx, 10);
				{
				setState(1696);
				match(BitXorAssign);
				}
				break;
			case BitOrAssign:
				enterOuterAlt(_localctx, 11);
				{
				setState(1697);
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
		enterRule(_localctx, 288, RULE_relationalOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1700);
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
		enterRule(_localctx, 290, RULE_unaryOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1702);
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
		enterRule(_localctx, 292, RULE_newExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1704);
			match(New);
			setState(1714);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Dot:
				{
				setState(1705);
				match(Dot);
				setState(1706);
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
				setState(1707);
				singleExpression(0);
				setState(1709);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,227,_ctx) ) {
				case 1:
					{
					setState(1708);
					typeArguments();
					}
					break;
				}
				setState(1712);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,228,_ctx) ) {
				case 1:
					{
					setState(1711);
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
		enterRule(_localctx, 294, RULE_generatorBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1716);
			match(OpenBrace);
			setState(1717);
			generatorDefinition();
			setState(1722);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,230,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1718);
					match(Comma);
					setState(1719);
					generatorDefinition();
					}
					} 
				}
				setState(1724);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,230,_ctx);
			}
			setState(1726);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1725);
				match(Comma);
				}
			}

			setState(1728);
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
		enterRule(_localctx, 296, RULE_generatorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1730);
			match(Multiply);
			setState(1731);
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
		enterRule(_localctx, 298, RULE_iteratorBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1733);
			match(OpenBrace);
			setState(1734);
			iteratorDefinition();
			setState(1739);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,232,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1735);
					match(Comma);
					setState(1736);
					iteratorDefinition();
					}
					} 
				}
				setState(1741);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,232,_ctx);
			}
			setState(1743);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1742);
				match(Comma);
				}
			}

			setState(1745);
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
		enterRule(_localctx, 300, RULE_iteratorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1747);
			match(OpenBracket);
			setState(1748);
			singleExpression(0);
			setState(1749);
			match(CloseBracket);
			setState(1750);
			parameterBlock();
			setState(1751);
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
		enterRule(_localctx, 302, RULE_literal);
		try {
			setState(1759);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NullLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1753);
				match(NullLiteral);
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1754);
				match(BooleanLiteral);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(1755);
				match(StringLiteral);
				}
				break;
			case BackTick:
				enterOuterAlt(_localctx, 4);
				{
				setState(1756);
				templateStringLiteral();
				}
				break;
			case RegularExpressionLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(1757);
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
				setState(1758);
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
		enterRule(_localctx, 304, RULE_templateStringLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1761);
			match(BackTick);
			setState(1765);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TemplateStringStartExpression || _la==TemplateStringAtom) {
				{
				{
				setState(1762);
				templateStringAtom();
				}
				}
				setState(1767);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1768);
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
		enterRule(_localctx, 306, RULE_templateStringAtom);
		try {
			setState(1775);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TemplateStringAtom:
				enterOuterAlt(_localctx, 1);
				{
				setState(1770);
				match(TemplateStringAtom);
				}
				break;
			case TemplateStringStartExpression:
				enterOuterAlt(_localctx, 2);
				{
				setState(1771);
				match(TemplateStringStartExpression);
				setState(1772);
				singleExpression(0);
				setState(1773);
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
		enterRule(_localctx, 308, RULE_numericLiteral);
		int _la;
		try {
			setState(1785);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Minus:
			case DecimalLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1778);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Minus) {
					{
					setState(1777);
					match(Minus);
					}
				}

				setState(1780);
				match(DecimalLiteral);
				}
				break;
			case HexIntegerLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1781);
				match(HexIntegerLiteral);
				}
				break;
			case OctalIntegerLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(1782);
				match(OctalIntegerLiteral);
				}
				break;
			case OctalIntegerLiteral2:
				enterOuterAlt(_localctx, 4);
				{
				setState(1783);
				match(OctalIntegerLiteral2);
				}
				break;
			case BinaryIntegerLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(1784);
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
		enterRule(_localctx, 310, RULE_identifierOrKeyWord);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1787);
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
		enterRule(_localctx, 312, RULE_identifierName);
		try {
			setState(1791);
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
				setState(1789);
				reservedWord();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1790);
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
		enterRule(_localctx, 314, RULE_reservedWord);
		try {
			setState(1795);
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
				setState(1793);
				keyword();
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1794);
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
		enterRule(_localctx, 316, RULE_typeReferenceName);
		try {
			setState(1800);
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
				setState(1797);
				keywordAllowedInTypeReferences();
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1798);
				match(BooleanLiteral);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 3);
				{
				setState(1799);
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
		enterRule(_localctx, 318, RULE_keyword);
		try {
			setState(1805);
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
				setState(1802);
				keywordAllowedInTypeReferences();
				}
				break;
			case ReadOnly:
				enterOuterAlt(_localctx, 2);
				{
				setState(1803);
				match(ReadOnly);
				}
				break;
			case Typeof:
				enterOuterAlt(_localctx, 3);
				{
				setState(1804);
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
		enterRule(_localctx, 320, RULE_keywordAllowedInTypeReferences);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1807);
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
		enterRule(_localctx, 322, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1809);
			match(Get);
			setState(1810);
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
		enterRule(_localctx, 324, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1812);
			match(Set);
			setState(1813);
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
		enterRule(_localctx, 326, RULE_eos);
		try {
			setState(1819);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,243,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1815);
				match(SemiColon);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1816);
				match(EOF);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1817);
				if (!(this.lineTerminatorAhead())) throw new FailedPredicateException(this, "this.lineTerminatorAhead()");
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1818);
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
		case 49:
			return decoratorMemberExpression_sempred((DecoratorMemberExpressionContext)_localctx, predIndex);
		case 128:
			return iterationStatement_sempred((IterationStatementContext)_localctx, predIndex);
		case 129:
			return continueStatement_sempred((ContinueStatementContext)_localctx, predIndex);
		case 130:
			return breakStatement_sempred((BreakStatementContext)_localctx, predIndex);
		case 131:
			return returnStatement_sempred((ReturnStatementContext)_localctx, predIndex);
		case 134:
			return throwStatement_sempred((ThrowStatementContext)_localctx, predIndex);
		case 136:
			return expressionStatement_sempred((ExpressionStatementContext)_localctx, predIndex);
		case 138:
			return singleExpression_sempred((SingleExpressionContext)_localctx, predIndex);
		case 163:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u008e\u0720\4\2\t"+
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
		"\4\u00a5\t\u00a5\3\2\5\2\u014c\n\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u0161\n\3\3\4\5\4\u0164\n"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0170\n\5\3\6\3\6\5\6\u0174"+
		"\n\6\3\6\3\6\3\7\6\7\u0179\n\7\r\7\16\7\u017a\3\b\3\b\3\b\3\t\3\t\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u018a\n\n\3\13\5\13\u018d\n\13\3\13\3"+
		"\13\3\13\7\13\u0192\n\13\f\13\16\13\u0195\13\13\3\f\5\f\u0198\n\f\3\f"+
		"\3\f\3\f\7\f\u019d\n\f\f\f\16\f\u01a0\13\f\3\r\5\r\u01a3\n\r\3\r\3\r\3"+
		"\16\3\16\3\17\3\17\3\17\3\17\3\17\7\17\u01ae\n\17\f\17\16\17\u01b1\13"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u01ba\n\17\f\17\16\17\u01bd"+
		"\13\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u01c6\n\17\f\17\16\17\u01c9"+
		"\13\17\5\17\u01cb\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\5\20\u01d8\n\20\3\21\3\21\3\21\5\21\u01dd\n\21\3\22\5\22\u01e0"+
		"\n\22\3\22\5\22\u01e3\n\22\3\22\3\22\3\22\3\22\7\22\u01e9\n\22\f\22\16"+
		"\22\u01ec\13\22\3\22\5\22\u01ef\n\22\3\22\3\22\5\22\u01f3\n\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\5\22\u01fc\n\22\3\23\3\23\3\23\3\23\3\23"+
		"\7\23\u0203\n\23\f\23\16\23\u0206\13\23\3\23\5\23\u0209\n\23\3\23\3\23"+
		"\5\23\u020d\n\23\3\24\5\24\u0210\n\24\3\24\5\24\u0213\n\24\3\24\3\24\5"+
		"\24\u0217\n\24\3\24\3\24\5\24\u021b\n\24\3\25\3\25\3\25\5\25\u0220\n\25"+
		"\3\26\3\26\3\26\5\26\u0225\n\26\3\27\3\27\5\27\u0229\n\27\3\30\3\30\3"+
		"\30\7\30\u022e\n\30\f\30\16\30\u0231\13\30\3\31\3\31\3\31\5\31\u0236\n"+
		"\31\5\31\u0238\n\31\3\31\3\31\3\32\3\32\3\32\7\32\u023f\n\32\f\32\16\32"+
		"\u0242\13\32\3\33\5\33\u0245\n\33\3\33\3\33\3\34\3\34\5\34\u024b\n\34"+
		"\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\5\37"+
		"\u025a\n\37\3 \3 \3 \5 \u025f\n \3 \3 \7 \u0263\n \f \16 \u0266\13 \3"+
		" \5 \u0269\n \5 \u026b\n \3!\5!\u026e\n!\3!\3!\5!\u0272\n!\3!\3!\3!\5"+
		"!\u0277\n!\3!\5!\u027a\n!\3\"\3\"\3\"\3#\5#\u0280\n#\3#\3#\5#\u0284\n"+
		"#\3#\3#\3#\3$\3$\3%\3%\3%\7%\u028e\n%\f%\16%\u0291\13%\3&\3&\3&\3\'\3"+
		"\'\3\'\5\'\u0299\n\'\3\'\3\'\3\'\5\'\u029e\n\'\3(\3(\5(\u02a2\n(\3(\3"+
		"(\3)\3)\3)\7)\u02a9\n)\f)\16)\u02ac\13)\3*\3*\5*\u02b0\n*\3*\3*\5*\u02b4"+
		"\n*\3+\3+\3+\3,\3,\3-\3-\3-\3-\3.\3.\3/\3/\3/\3/\3\60\3\60\3\60\7\60\u02c8"+
		"\n\60\f\60\16\60\u02cb\13\60\3\61\6\61\u02ce\n\61\r\61\16\61\u02cf\3\62"+
		"\3\62\3\62\5\62\u02d5\n\62\3\63\3\63\3\63\3\63\3\63\3\63\5\63\u02dd\n"+
		"\63\3\63\3\63\3\63\7\63\u02e2\n\63\f\63\16\63\u02e5\13\63\3\64\3\64\3"+
		"\64\3\65\3\65\3\65\5\65\u02ed\n\65\3\65\5\65\u02f0\n\65\3\65\3\65\5\65"+
		"\u02f4\n\65\3\65\3\65\5\65\u02f8\n\65\3\66\3\66\3\66\3\67\3\67\3\67\7"+
		"\67\u0300\n\67\f\67\16\67\u0303\13\67\38\38\58\u0307\n8\39\39\39\39\5"+
		"9\u030d\n9\39\79\u0310\n9\f9\169\u0313\139\3:\3:\3:\3:\3:\3:\3:\5:\u031c"+
		"\n:\3;\3;\5;\u0320\n;\3;\3;\5;\u0324\n;\3<\5<\u0327\n<\3<\3<\3<\3<\5<"+
		"\u032d\n<\5<\u032f\n<\3=\5=\u0332\n=\3=\5=\u0335\n=\3=\3=\3=\5=\u033a"+
		"\n=\3=\3=\3=\3=\5=\u0340\n=\3=\3=\3=\5=\u0345\n=\3=\3=\3>\3>\3>\3>\3>"+
		"\3>\3>\5>\u0350\n>\3?\3?\5?\u0354\n?\3?\3?\3@\5@\u0359\n@\3@\3@\5@\u035d"+
		"\n@\3@\5@\u0360\n@\3A\5A\u0363\nA\3A\3A\3A\3A\5A\u0369\nA\3A\3A\5A\u036d"+
		"\nA\3B\3B\5B\u0371\nB\3C\3C\3C\7C\u0376\nC\fC\16C\u0379\13C\3D\3D\3D\5"+
		"D\u037e\nD\3E\3E\5E\u0382\nE\3E\3E\3E\5E\u0387\nE\3E\5E\u038a\nE\3F\5"+
		"F\u038d\nF\3F\3F\3F\5F\u0392\nF\3F\3F\3F\5F\u0397\nF\3G\5G\u039a\nG\3"+
		"G\5G\u039d\nG\3H\3H\3H\3I\3I\3I\3J\3J\5J\u03a7\nJ\3J\5J\u03aa\nJ\3J\3"+
		"J\3K\3K\3K\5K\u03b1\nK\3K\7K\u03b4\nK\fK\16K\u03b7\13K\3L\3L\3L\5L\u03bc"+
		"\nL\3L\5L\u03bf\nL\3M\5M\u03c2\nM\3M\3M\3M\5M\u03c7\nM\3N\3N\5N\u03cb"+
		"\nN\3O\3O\3O\3O\5O\u03d1\nO\3O\3O\3P\3P\3P\3P\5P\u03d9\nP\3Q\5Q\u03dc"+
		"\nQ\3Q\5Q\u03df\nQ\3Q\5Q\u03e2\nQ\3R\5R\u03e5\nR\3R\3R\5R\u03e9\nR\3R"+
		"\5R\u03ec\nR\3R\5R\u03ef\nR\3R\3R\5R\u03f3\nR\5R\u03f5\nR\3S\3S\3S\3T"+
		"\3T\5T\u03fc\nT\3T\3T\3U\3U\5U\u0402\nU\3V\3V\3V\3V\7V\u0408\nV\fV\16"+
		"V\u040b\13V\3V\3V\5V\u040f\nV\5V\u0411\nV\3W\3W\3W\5W\u0416\nW\3X\3X\5"+
		"X\u041a\nX\3Y\5Y\u041d\nY\3Y\5Y\u0420\nY\3Y\3Y\5Y\u0424\nY\3Z\5Z\u0427"+
		"\nZ\3Z\5Z\u042a\nZ\3Z\3Z\3Z\5Z\u042f\nZ\3Z\5Z\u0432\nZ\3Z\5Z\u0435\nZ"+
		"\3[\3[\3\\\3\\\5\\\u043b\n\\\3]\3]\5]\u043f\n]\3^\3^\5^\u0443\n^\3^\3"+
		"^\3_\3_\6_\u0449\n_\r_\16_\u044a\3_\7_\u044e\n_\f_\16_\u0451\13_\3_\5"+
		"_\u0454\n_\3`\5`\u0457\n`\3`\3`\3a\3a\5a\u045d\na\3b\3b\3b\3b\7b\u0463"+
		"\nb\fb\16b\u0466\13b\3b\5b\u0469\nb\5b\u046b\nb\3b\3b\3c\3c\3c\3c\5c\u0473"+
		"\nc\3c\3c\5c\u0477\nc\3c\3c\3c\3c\5c\u047d\nc\3d\3d\3d\3d\3d\3d\3d\3d"+
		"\5d\u0487\nd\3d\3d\5d\u048b\nd\3e\3e\3e\3e\5e\u0491\ne\3e\5e\u0494\ne"+
		"\3f\3f\3f\3f\5f\u049a\nf\3f\5f\u049d\nf\3f\3f\5f\u04a1\nf\3g\5g\u04a4"+
		"\ng\3g\3g\3g\3g\3h\3h\3h\5h\u04ad\nh\5h\u04af\nh\3h\3h\3i\3i\3i\7i\u04b6"+
		"\ni\fi\16i\u04b9\13i\3j\5j\u04bc\nj\3j\3j\5j\u04c0\nj\3k\3k\3k\3k\5k\u04c6"+
		"\nk\3k\3k\3l\5l\u04cb\nl\3l\3l\3l\3l\3l\5l\u04d2\nl\3l\3l\3l\3m\3m\3m"+
		"\5m\u04da\nm\3m\3m\3m\3m\7m\u04e0\nm\fm\16m\u04e3\13m\3m\5m\u04e6\nm\3"+
		"m\3m\3n\3n\3n\5n\u04ed\nn\3o\3o\3o\3o\3o\3o\3o\5o\u04f6\no\3p\3p\3p\3"+
		"q\3q\3q\3q\3q\3q\3q\3q\3q\3q\3q\3q\3q\3q\3q\3q\5q\u050b\nq\3q\3q\5q\u050f"+
		"\nq\3q\3q\3q\5q\u0514\nq\3r\3r\3r\5r\u0519\nr\3r\3r\5r\u051d\nr\3r\3r"+
		"\5r\u0521\nr\3s\5s\u0524\ns\3s\5s\u0527\ns\3s\3s\3s\5s\u052c\ns\3s\5s"+
		"\u052f\ns\3t\3t\3u\3u\5u\u0535\nu\3u\5u\u0538\nu\3v\3v\3v\7v\u053d\nv"+
		"\fv\16v\u0540\13v\3w\3w\5w\u0544\nw\3w\3w\5w\u0548\nw\3w\5w\u054b\nw\3"+
		"x\3x\3x\3x\3x\3x\3y\3y\5y\u0555\ny\3y\3y\5y\u0559\ny\5y\u055b\ny\3y\3"+
		"y\3z\6z\u0560\nz\rz\16z\u0561\3{\3{\3{\3{\5{\u0568\n{\3|\3|\3|\5|\u056d"+
		"\n|\3}\3}\3}\3}\5}\u0573\n}\3}\5}\u0576\n}\3~\3~\3~\3~\3~\3~\3\177\3\177"+
		"\3\177\3\u0080\3\u0080\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\5\u0081\u058a\n\u0081\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082"+
		"\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082"+
		"\3\u0082\3\u0082\3\u0082\5\u0082\u059d\n\u0082\3\u0082\3\u0082\5\u0082"+
		"\u05a1\n\u0082\3\u0082\3\u0082\5\u0082\u05a5\n\u0082\3\u0082\3\u0082\3"+
		"\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\5\u0082\u05af\n\u0082\3"+
		"\u0082\3\u0082\5\u0082\u05b3\n\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3"+
		"\u0082\3\u0082\3\u0082\3\u0082\3\u0082\5\u0082\u05be\n\u0082\3\u0082\3"+
		"\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082"+
		"\3\u0082\5\u0082\u05cb\n\u0082\3\u0082\3\u0082\3\u0082\3\u0082\5\u0082"+
		"\u05d1\n\u0082\3\u0083\3\u0083\3\u0083\5\u0083\u05d6\n\u0083\3\u0083\3"+
		"\u0083\3\u0084\3\u0084\3\u0084\5\u0084\u05dd\n\u0084\3\u0084\3\u0084\3"+
		"\u0085\3\u0085\3\u0085\5\u0085\u05e4\n\u0085\3\u0085\3\u0085\3\u0086\3"+
		"\u0086\3\u0086\3\u0086\3\u0086\3\u0086\3\u0087\3\u0087\3\u0087\3\u0087"+
		"\3\u0088\3\u0088\3\u0088\3\u0088\3\u0088\3\u0089\3\u0089\3\u0089\3\u008a"+
		"\3\u008a\3\u008a\3\u008a\3\u008b\3\u008b\3\u008b\7\u008b\u0601\n\u008b"+
		"\f\u008b\16\u008b\u0604\13\u008b\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\5\u008c\u0610\n\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\5\u008c\u0620\n\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\5\u008c\u0644\n\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\5\u008c\u0656\n\u008c"+
		"\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\5\u008c\u065f"+
		"\n\u008c\3\u008c\3\u008c\5\u008c\u0663\n\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\3\u008c\5\u008c\u066b\n\u008c\3\u008c\7\u008c\u066e\n"+
		"\u008c\f\u008c\16\u008c\u0671\13\u008c\3\u008d\3\u008d\5\u008d\u0675\n"+
		"\u008d\3\u008d\5\u008d\u0678\n\u008d\3\u008d\3\u008d\5\u008d\u067c\n\u008d"+
		"\3\u008d\3\u008d\3\u008e\5\u008e\u0681\n\u008e\3\u008e\3\u008e\5\u008e"+
		"\u0685\n\u008e\3\u008e\3\u008e\3\u008e\3\u008f\3\u008f\5\u008f\u068c\n"+
		"\u008f\3\u0090\3\u0090\5\u0090\u0690\n\u0090\3\u0090\3\u0090\3\u0091\3"+
		"\u0091\3\u0091\3\u0091\3\u0091\3\u0091\3\u0091\3\u0091\3\u0091\3\u0091"+
		"\3\u0091\5\u0091\u069f\n\u0091\3\u0091\3\u0091\3\u0091\3\u0091\5\u0091"+
		"\u06a5\n\u0091\3\u0092\3\u0092\3\u0093\3\u0093\3\u0094\3\u0094\3\u0094"+
		"\3\u0094\3\u0094\5\u0094\u06b0\n\u0094\3\u0094\5\u0094\u06b3\n\u0094\5"+
		"\u0094\u06b5\n\u0094\3\u0095\3\u0095\3\u0095\3\u0095\7\u0095\u06bb\n\u0095"+
		"\f\u0095\16\u0095\u06be\13\u0095\3\u0095\5\u0095\u06c1\n\u0095\3\u0095"+
		"\3\u0095\3\u0096\3\u0096\3\u0096\3\u0097\3\u0097\3\u0097\3\u0097\7\u0097"+
		"\u06cc\n\u0097\f\u0097\16\u0097\u06cf\13\u0097\3\u0097\5\u0097\u06d2\n"+
		"\u0097\3\u0097\3\u0097\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098"+
		"\3\u0099\3\u0099\3\u0099\3\u0099\3\u0099\3\u0099\5\u0099\u06e2\n\u0099"+
		"\3\u009a\3\u009a\7\u009a\u06e6\n\u009a\f\u009a\16\u009a\u06e9\13\u009a"+
		"\3\u009a\3\u009a\3\u009b\3\u009b\3\u009b\3\u009b\3\u009b\5\u009b\u06f2"+
		"\n\u009b\3\u009c\5\u009c\u06f5\n\u009c\3\u009c\3\u009c\3\u009c\3\u009c"+
		"\3\u009c\5\u009c\u06fc\n\u009c\3\u009d\3\u009d\3\u009e\3\u009e\5\u009e"+
		"\u0702\n\u009e\3\u009f\3\u009f\5\u009f\u0706\n\u009f\3\u00a0\3\u00a0\3"+
		"\u00a0\5\u00a0\u070b\n\u00a0\3\u00a1\3\u00a1\3\u00a1\5\u00a1\u0710\n\u00a1"+
		"\3\u00a2\3\u00a2\3\u00a3\3\u00a3\3\u00a3\3\u00a4\3\u00a4\3\u00a4\3\u00a5"+
		"\3\u00a5\3\u00a5\3\u00a5\5\u00a5\u071e\n\u00a5\3\u00a5\2\4d\u0116\u00a6"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH"+
		"JLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c"+
		"\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4"+
		"\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc"+
		"\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0\u00d2\u00d4"+
		"\u00d6\u00d8\u00da\u00dc\u00de\u00e0\u00e2\u00e4\u00e6\u00e8\u00ea\u00ec"+
		"\u00ee\u00f0\u00f2\u00f4\u00f6\u00f8\u00fa\u00fc\u00fe\u0100\u0102\u0104"+
		"\u0106\u0108\u010a\u010c\u010e\u0110\u0112\u0114\u0116\u0118\u011a\u011c"+
		"\u011e\u0120\u0122\u0124\u0126\u0128\u012a\u012c\u012e\u0130\u0132\u0134"+
		"\u0136\u0138\u013a\u013c\u013e\u0140\u0142\u0144\u0146\u0148\2\22\4\2"+
		"CD^^\3\2\u0085\u0086\3\2\r\16\4\2rrtt\4\2jknn\5\2IIeeii\3\2)*\3\2&(\3"+
		"\2\"%\3\2\26\27\3\2\32\34\3\2\24\25\5\2\36!AAZZ\6\2\24\31BBMMYY\5\2vv"+
		"{{\u0085\u0085\7\2\678?ACCE]_\u0083\2\u07d6\2\u014b\3\2\2\2\4\u0160\3"+
		"\2\2\2\6\u0163\3\2\2\2\b\u016f\3\2\2\2\n\u0171\3\2\2\2\f\u0178\3\2\2\2"+
		"\16\u017c\3\2\2\2\20\u017f\3\2\2\2\22\u0181\3\2\2\2\24\u018c\3\2\2\2\26"+
		"\u0197\3\2\2\2\30\u01a2\3\2\2\2\32\u01a6\3\2\2\2\34\u01ca\3\2\2\2\36\u01d7"+
		"\3\2\2\2 \u01dc\3\2\2\2\"\u01e2\3\2\2\2$\u01fd\3\2\2\2&\u020f\3\2\2\2"+
		"(\u021c\3\2\2\2*\u0224\3\2\2\2,\u0226\3\2\2\2.\u022a\3\2\2\2\60\u0232"+
		"\3\2\2\2\62\u023b\3\2\2\2\64\u0244\3\2\2\2\66\u0248\3\2\2\28\u024e\3\2"+
		"\2\2:\u0250\3\2\2\2<\u0253\3\2\2\2>\u026a\3\2\2\2@\u026d\3\2\2\2B\u027b"+
		"\3\2\2\2D\u027f\3\2\2\2F\u0288\3\2\2\2H\u028a\3\2\2\2J\u0292\3\2\2\2L"+
		"\u0295\3\2\2\2N\u029f\3\2\2\2P\u02a5\3\2\2\2R\u02ad\3\2\2\2T\u02b5\3\2"+
		"\2\2V\u02b8\3\2\2\2X\u02ba\3\2\2\2Z\u02be\3\2\2\2\\\u02c0\3\2\2\2^\u02c4"+
		"\3\2\2\2`\u02cd\3\2\2\2b\u02d1\3\2\2\2d\u02dc\3\2\2\2f\u02e6\3\2\2\2h"+
		"\u02e9\3\2\2\2j\u02f9\3\2\2\2l\u02fc\3\2\2\2n\u0304\3\2\2\2p\u0308\3\2"+
		"\2\2r\u031b\3\2\2\2t\u031d\3\2\2\2v\u0326\3\2\2\2x\u0331\3\2\2\2z\u034f"+
		"\3\2\2\2|\u0351\3\2\2\2~\u0358\3\2\2\2\u0080\u0362\3\2\2\2\u0082\u036e"+
		"\3\2\2\2\u0084\u0372\3\2\2\2\u0086\u037a\3\2\2\2\u0088\u037f\3\2\2\2\u008a"+
		"\u038c\3\2\2\2\u008c\u0399\3\2\2\2\u008e\u039e\3\2\2\2\u0090\u03a1\3\2"+
		"\2\2\u0092\u03a4\3\2\2\2\u0094\u03ad\3\2\2\2\u0096\u03be\3\2\2\2\u0098"+
		"\u03c1\3\2\2\2\u009a\u03ca\3\2\2\2\u009c\u03cc\3\2\2\2\u009e\u03d4\3\2"+
		"\2\2\u00a0\u03db\3\2\2\2\u00a2\u03e4\3\2\2\2\u00a4\u03f6\3\2\2\2\u00a6"+
		"\u03f9\3\2\2\2\u00a8\u03ff\3\2\2\2\u00aa\u0410\3\2\2\2\u00ac\u0412\3\2"+
		"\2\2\u00ae\u0419\3\2\2\2\u00b0\u041c\3\2\2\2\u00b2\u0426\3\2\2\2\u00b4"+
		"\u0436\3\2\2\2\u00b6\u043a\3\2\2\2\u00b8\u043e\3\2\2\2\u00ba\u0440\3\2"+
		"\2\2\u00bc\u0446\3\2\2\2\u00be\u0456\3\2\2\2\u00c0\u045c\3\2\2\2\u00c2"+
		"\u045e\3\2\2\2\u00c4\u047c\3\2\2\2\u00c6\u048a\3\2\2\2\u00c8\u048c\3\2"+
		"\2\2\u00ca\u0495\3\2\2\2\u00cc\u04a3\3\2\2\2\u00ce\u04a9\3\2\2\2\u00d0"+
		"\u04b2\3\2\2\2\u00d2\u04bb\3\2\2\2\u00d4\u04c1\3\2\2\2\u00d6\u04ca\3\2"+
		"\2\2\u00d8\u04d9\3\2\2\2\u00da\u04e9\3\2\2\2\u00dc\u04ee\3\2\2\2\u00de"+
		"\u04f7\3\2\2\2\u00e0\u0513\3\2\2\2\u00e2\u051c\3\2\2\2\u00e4\u0523\3\2"+
		"\2\2\u00e6\u0530\3\2\2\2\u00e8\u0532\3\2\2\2\u00ea\u0539\3\2\2\2\u00ec"+
		"\u0541\3\2\2\2\u00ee\u054c\3\2\2\2\u00f0\u0552\3\2\2\2\u00f2\u055f\3\2"+
		"\2\2\u00f4\u0563\3\2\2\2\u00f6\u0569\3\2\2\2\u00f8\u056e\3\2\2\2\u00fa"+
		"\u0577\3\2\2\2\u00fc\u057d\3\2\2\2\u00fe\u0580\3\2\2\2\u0100\u0582\3\2"+
		"\2\2\u0102\u05d0\3\2\2\2\u0104\u05d2\3\2\2\2\u0106\u05d9\3\2\2\2\u0108"+
		"\u05e0\3\2\2\2\u010a\u05e7\3\2\2\2\u010c\u05ed\3\2\2\2\u010e\u05f1\3\2"+
		"\2\2\u0110\u05f6\3\2\2\2\u0112\u05f9\3\2\2\2\u0114\u05fd\3\2\2\2\u0116"+
		"\u061f\3\2\2\2\u0118\u0672\3\2\2\2\u011a\u0680\3\2\2\2\u011c\u068b\3\2"+
		"\2\2\u011e\u068d\3\2\2\2\u0120\u06a4\3\2\2\2\u0122\u06a6\3\2\2\2\u0124"+
		"\u06a8\3\2\2\2\u0126\u06aa\3\2\2\2\u0128\u06b6\3\2\2\2\u012a\u06c4\3\2"+
		"\2\2\u012c\u06c7\3\2\2\2\u012e\u06d5\3\2\2\2\u0130\u06e1\3\2\2\2\u0132"+
		"\u06e3\3\2\2\2\u0134\u06f1\3\2\2\2\u0136\u06fb\3\2\2\2\u0138\u06fd\3\2"+
		"\2\2\u013a\u0701\3\2\2\2\u013c\u0705\3\2\2\2\u013e\u070a\3\2\2\2\u0140"+
		"\u070f\3\2\2\2\u0142\u0711\3\2\2\2\u0144\u0713\3\2\2\2\u0146\u0716\3\2"+
		"\2\2\u0148\u071d\3\2\2\2\u014a\u014c\5\f\7\2\u014b\u014a\3\2\2\2\u014b"+
		"\u014c\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014e\7\2\2\3\u014e\3\3\2\2\2"+
		"\u014f\u0161\5\n\6\2\u0150\u0161\5\u00d4k\2\u0151\u0161\5`\61\2\u0152"+
		"\u0161\5\u00dep\2\u0153\u0161\5\6\4\2\u0154\u0161\5\u0100\u0081\2\u0155"+
		"\u0161\5\u0102\u0082\2\u0156\u0161\5\u0104\u0083\2\u0157\u0161\5\u0106"+
		"\u0084\2\u0158\u0161\5\u0108\u0085\2\u0159\u0161\5\u010a\u0086\2\u015a"+
		"\u0161\5\u010c\u0087\2\u015b\u0161\5\u00eex\2\u015c\u0161\5\u010e\u0088"+
		"\2\u015d\u0161\5\u00f8}\2\u015e\u0161\5\u0110\u0089\2\u015f\u0161\5\u00fe"+
		"\u0080\2\u0160\u014f\3\2\2\2\u0160\u0150\3\2\2\2\u0160\u0151\3\2\2\2\u0160"+
		"\u0152\3\2\2\2\u0160\u0153\3\2\2\2\u0160\u0154\3\2\2\2\u0160\u0155\3\2"+
		"\2\2\u0160\u0156\3\2\2\2\u0160\u0157\3\2\2\2\u0160\u0158\3\2\2\2\u0160"+
		"\u0159\3\2\2\2\u0160\u015a\3\2\2\2\u0160\u015b\3\2\2\2\u0160\u015c\3\2"+
		"\2\2\u0160\u015d\3\2\2\2\u0160\u015e\3\2\2\2\u0160\u015f\3\2\2\2\u0161"+
		"\5\3\2\2\2\u0162\u0164\7}\2\2\u0163\u0162\3\2\2\2\u0163\u0164\3\2\2\2"+
		"\u0164\u0165\3\2\2\2\u0165\u0166\5\b\5\2\u0166\7\3\2\2\2\u0167\u0170\5"+
		"X-\2\u0168\u0170\5\\/\2\u0169\u0170\5h\65\2\u016a\u0170\5L\'\2\u016b\u0170"+
		"\5\u0088E\2\u016c\u0170\5\u008aF\2\u016d\u0170\5\u0080A\2\u016e\u0170"+
		"\5\u00e4s\2\u016f\u0167\3\2\2\2\u016f\u0168\3\2\2\2\u016f\u0169\3\2\2"+
		"\2\u016f\u016a\3\2\2\2\u016f\u016b\3\2\2\2\u016f\u016c\3\2\2\2\u016f\u016d"+
		"\3\2\2\2\u016f\u016e\3\2\2\2\u0170\t\3\2\2\2\u0171\u0173\7\13\2\2\u0172"+
		"\u0174\5\f\7\2\u0173\u0172\3\2\2\2\u0173\u0174\3\2\2\2\u0174\u0175\3\2"+
		"\2\2\u0175\u0176\7\f\2\2\u0176\13\3\2\2\2\u0177\u0179\5\4\3\2\u0178\u0177"+
		"\3\2\2\2\u0179\u017a\3\2\2\2\u017a\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017b"+
		"\r\3\2\2\2\u017c\u017d\7\21\2\2\u017d\u017e\5\20\t\2\u017e\17\3\2\2\2"+
		"\u017f\u0180\5\22\n\2\u0180\21\3\2\2\2\u0181\u0189\5\24\13\2\u0182\u0183"+
		"\7c\2\2\u0183\u0184\5\24\13\2\u0184\u0185\7\20\2\2\u0185\u0186\5\22\n"+
		"\2\u0186\u0187\7\21\2\2\u0187\u0188\5\22\n\2\u0188\u018a\3\2\2\2\u0189"+
		"\u0182\3\2\2\2\u0189\u018a\3\2\2\2\u018a\23\3\2\2\2\u018b\u018d\7(\2\2"+
		"\u018c\u018b\3\2\2\2\u018c\u018d\3\2\2\2\u018d\u018e\3\2\2\2\u018e\u0193"+
		"\5\26\f\2\u018f\u0190\7(\2\2\u0190\u0192\5\26\f\2\u0191\u018f\3\2\2\2"+
		"\u0192\u0195\3\2\2\2\u0193\u0191\3\2\2\2\u0193\u0194\3\2\2\2\u0194\25"+
		"\3\2\2\2\u0195\u0193\3\2\2\2\u0196\u0198\7&\2\2\u0197\u0196\3\2\2\2\u0197"+
		"\u0198\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u019e\5\30\r\2\u019a\u019b\7"+
		"&\2\2\u019b\u019d\5\30\r\2\u019c\u019a\3\2\2\2\u019d\u01a0\3\2\2\2\u019e"+
		"\u019c\3\2\2\2\u019e\u019f\3\2\2\2\u019f\27\3\2\2\2\u01a0\u019e\3\2\2"+
		"\2\u01a1\u01a3\5\32\16\2\u01a2\u01a1\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3"+
		"\u01a4\3\2\2\2\u01a4\u01a5\5\34\17\2\u01a5\31\3\2\2\2\u01a6\u01a7\t\2"+
		"\2\2\u01a7\33\3\2\2\2\u01a8\u01a9\7\20\2\2\u01a9\u01aa\7\7\2\2\u01aa\u01af"+
		"\7\b\2\2\u01ab\u01ac\7\7\2\2\u01ac\u01ae\7\b\2\2\u01ad\u01ab\3\2\2\2\u01ae"+
		"\u01b1\3\2\2\2\u01af\u01ad\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0\u01cb\3\2"+
		"\2\2\u01b1\u01af\3\2\2\2\u01b2\u01b3\7\t\2\2\u01b3\u01b4\7\20\2\2\u01b4"+
		"\u01b5\7\n\2\2\u01b5\u01b6\7\7\2\2\u01b6\u01bb\7\b\2\2\u01b7\u01b8\7\7"+
		"\2\2\u01b8\u01ba\7\b\2\2\u01b9\u01b7\3\2\2\2\u01ba\u01bd\3\2\2\2\u01bb"+
		"\u01b9\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01cb\3\2\2\2\u01bd\u01bb\3\2"+
		"\2\2\u01be\u01c7\5\36\20\2\u01bf\u01c0\7\7\2\2\u01c0\u01c6\7\b\2\2\u01c1"+
		"\u01c2\7\7\2\2\u01c2\u01c3\5\20\t\2\u01c3\u01c4\7\b\2\2\u01c4\u01c6\3"+
		"\2\2\2\u01c5\u01bf\3\2\2\2\u01c5\u01c1\3\2\2\2\u01c6\u01c9\3\2\2\2\u01c7"+
		"\u01c5\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01cb\3\2\2\2\u01c9\u01c7\3\2"+
		"\2\2\u01ca\u01a8\3\2\2\2\u01ca\u01b2\3\2\2\2\u01ca\u01be\3\2\2\2\u01cb"+
		"\35\3\2\2\2\u01cc\u01d8\5 \21\2\u01cd\u01d8\5\"\22\2\u01ce\u01d8\5$\23"+
		"\2\u01cf\u01d8\5:\36\2\u01d0\u01d8\5<\37\2\u01d1\u01d8\5J&\2\u01d2\u01d8"+
		"\5*\26\2\u01d3\u01d4\7\t\2\2\u01d4\u01d5\5\20\t\2\u01d5\u01d6\7\n\2\2"+
		"\u01d6\u01d8\3\2\2\2\u01d7\u01cc\3\2\2\2\u01d7\u01cd\3\2\2\2\u01d7\u01ce"+
		"\3\2\2\2\u01d7\u01cf\3\2\2\2\u01d7\u01d0\3\2\2\2\u01d7\u01d1\3\2\2\2\u01d7"+
		"\u01d2\3\2\2\2\u01d7\u01d3\3\2\2\2\u01d8\37\3\2\2\2\u01d9\u01dd\79\2\2"+
		"\u01da\u01dd\7\u0086\2\2\u01db\u01dd\5\u0136\u009c\2\u01dc\u01d9\3\2\2"+
		"\2\u01dc\u01da\3\2\2\2\u01dc\u01db\3\2\2\2\u01dd!\3\2\2\2\u01de\u01e0"+
		"\7~\2\2\u01df\u01de\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1"+
		"\u01e3\7G\2\2\u01e2\u01df\3\2\2\2\u01e2\u01e3\3\2\2\2\u01e3\u01f2\3\2"+
		"\2\2\u01e4\u01e5\7\36\2\2\u01e5\u01ea\5(\25\2\u01e6\u01e7\7\16\2\2\u01e7"+
		"\u01e9\5(\25\2\u01e8\u01e6\3\2\2\2\u01e9\u01ec\3\2\2\2\u01ea\u01e8\3\2"+
		"\2\2\u01ea\u01eb\3\2\2\2\u01eb\u01ee\3\2\2\2\u01ec\u01ea\3\2\2\2\u01ed"+
		"\u01ef\7\16\2\2\u01ee\u01ed\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef\u01f0\3"+
		"\2\2\2\u01f0\u01f1\7\37\2\2\u01f1\u01f3\3\2\2\2\u01f2\u01e4\3\2\2\2\u01f2"+
		"\u01f3\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4\u01f5\7\t\2\2\u01f5\u01f6\5>"+
		" \2\u01f6\u01f7\7\n\2\2\u01f7\u01f8\7\66\2\2\u01f8\u01fb\3\2\2\2\u01f9"+
		"\u01fc\5D#\2\u01fa\u01fc\5\24\13\2\u01fb\u01f9\3\2\2\2\u01fb\u01fa\3\2"+
		"\2\2\u01fc#\3\2\2\2\u01fd\u020c\7\7\2\2\u01fe\u020d\7\b\2\2\u01ff\u0204"+
		"\5&\24\2\u0200\u0201\7\16\2\2\u0201\u0203\5&\24\2\u0202\u0200\3\2\2\2"+
		"\u0203\u0206\3\2\2\2\u0204\u0202\3\2\2\2\u0204\u0205\3\2\2\2\u0205\u0208"+
		"\3\2\2\2\u0206\u0204\3\2\2\2\u0207\u0209\7\16\2\2\u0208\u0207\3\2\2\2"+
		"\u0208\u0209\3\2\2\2\u0209\u020a\3\2\2\2\u020a\u020b\7\b\2\2\u020b\u020d"+
		"\3\2\2\2\u020c\u01fe\3\2\2\2\u020c\u01ff\3\2\2\2\u020d%\3\2\2\2\u020e"+
		"\u0210\7\22\2\2\u020f\u020e\3\2\2\2\u020f\u0210\3\2\2\2\u0210\u0212\3"+
		"\2\2\2\u0211\u0213\7\u0080\2\2\u0212\u0211\3\2\2\2\u0212\u0213\3\2\2\2"+
		"\u0213\u0216\3\2\2\2\u0214\u0215\7\u0085\2\2\u0215\u0217\7\21\2\2\u0216"+
		"\u0214\3\2\2\2\u0216\u0217\3\2\2\2\u0217\u0218\3\2\2\2\u0218\u021a\5\20"+
		"\t\2\u0219\u021b\7\20\2\2\u021a\u0219\3\2\2\2\u021a\u021b\3\2\2\2\u021b"+
		"\'\3\2\2\2\u021c\u021f\7\u0085\2\2\u021d\u021e\7c\2\2\u021e\u0220\5\20"+
		"\t\2\u021f\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220)\3\2\2\2\u0221\u0225"+
		"\5,\27\2\u0222\u0225\5\66\34\2\u0223\u0225\58\35\2\u0224\u0221\3\2\2\2"+
		"\u0224\u0222\3\2\2\2\u0224\u0223\3\2\2\2\u0225+\3\2\2\2\u0226\u0228\5"+
		".\30\2\u0227\u0229\5\60\31\2\u0228\u0227\3\2\2\2\u0228\u0229\3\2\2\2\u0229"+
		"-\3\2\2\2\u022a\u022f\5\u013e\u00a0\2\u022b\u022c\7\23\2\2\u022c\u022e"+
		"\5\u013e\u00a0\2\u022d\u022b\3\2\2\2\u022e\u0231\3\2\2\2\u022f\u022d\3"+
		"\2\2\2\u022f\u0230\3\2\2\2\u0230/\3\2\2\2\u0231\u022f\3\2\2\2\u0232\u0237"+
		"\7\36\2\2\u0233\u0235\5\62\32\2\u0234\u0236\7\16\2\2\u0235\u0234\3\2\2"+
		"\2\u0235\u0236\3\2\2\2\u0236\u0238\3\2\2\2\u0237\u0233\3\2\2\2\u0237\u0238"+
		"\3\2\2\2\u0238\u0239\3\2\2\2\u0239\u023a\7\37\2\2\u023a\61\3\2\2\2\u023b"+
		"\u0240\5\64\33\2\u023c\u023d\7\16\2\2\u023d\u023f\5\64\33\2\u023e\u023c"+
		"\3\2\2\2\u023f\u0242\3\2\2\2\u0240\u023e\3\2\2\2\u0240\u0241\3\2\2\2\u0241"+
		"\63\3\2\2\2\u0242\u0240\3\2\2\2\u0243\u0245\7\u0080\2\2\u0244\u0243\3"+
		"\2\2\2\u0244\u0245\3\2\2\2\u0245\u0246\3\2\2\2\u0246\u0247\5\20\t\2\u0247"+
		"\65\3\2\2\2\u0248\u024a\7\13\2\2\u0249\u024b\5n8\2\u024a\u0249\3\2\2\2"+
		"\u024a\u024b\3\2\2\2\u024b\u024c\3\2\2\2\u024c\u024d\7\f\2\2\u024d\67"+
		"\3\2\2\2\u024e\u024f\7T\2\2\u024f9\3\2\2\2\u0250\u0251\7B\2\2\u0251\u0252"+
		"\5H%\2\u0252;\3\2\2\2\u0253\u0254\7g\2\2\u0254\u0255\7\t\2\2\u0255\u0256"+
		"\7\u0086\2\2\u0256\u0259\7\n\2\2\u0257\u0258\7\23\2\2\u0258\u025a\5,\27"+
		"\2\u0259\u0257\3\2\2\2\u0259\u025a\3\2\2\2\u025a=\3\2\2\2\u025b\u025c"+
		"\7T\2\2\u025c\u025f\5\16\b\2\u025d\u025f\5@!\2\u025e\u025b\3\2\2\2\u025e"+
		"\u025d\3\2\2\2\u025f\u0264\3\2\2\2\u0260\u0261\7\16\2\2\u0261\u0263\5"+
		"@!\2\u0262\u0260\3\2\2\2\u0263\u0266\3\2\2\2\u0264\u0262\3\2\2\2\u0264"+
		"\u0265\3\2\2\2\u0265\u0268\3\2\2\2\u0266\u0264\3\2\2\2\u0267\u0269\7\16"+
		"\2\2\u0268\u0267\3\2\2\2\u0268\u0269\3\2\2\2\u0269\u026b\3\2\2\2\u026a"+
		"\u025e\3\2\2\2\u026a\u026b\3\2\2\2\u026b?\3\2\2\2\u026c\u026e\7\22\2\2"+
		"\u026d\u026c\3\2\2\2\u026d\u026e\3\2\2\2\u026e\u0276\3\2\2\2\u026f\u0271"+
		"\5F$\2\u0270\u0272\7\20\2\2\u0271\u0270\3\2\2\2\u0271\u0272\3\2\2\2\u0272"+
		"\u0273\3\2\2\2\u0273\u0274\5\16\b\2\u0274\u0277\3\2\2\2\u0275\u0277\5"+
		"\20\t\2\u0276\u026f\3\2\2\2\u0276\u0275\3\2\2\2\u0277\u0279\3\2\2\2\u0278"+
		"\u027a\5B\"\2\u0279\u0278\3\2\2\2\u0279\u027a\3\2\2\2\u027aA\3\2\2\2\u027b"+
		"\u027c\7\17\2\2\u027c\u027d\7\u0085\2\2\u027dC\3\2\2\2\u027e\u0280\7\u0083"+
		"\2\2\u027f\u027e\3\2\2\2\u027f\u0280\3\2\2\2\u0280\u0283\3\2\2\2\u0281"+
		"\u0284\7T\2\2\u0282\u0284\5F$\2\u0283\u0281\3\2\2\2\u0283\u0282\3\2\2"+
		"\2\u0284\u0285\3\2\2\2\u0285\u0286\7\177\2\2\u0286\u0287\5\24\13\2\u0287"+
		"E\3\2\2\2\u0288\u0289\5\u013a\u009e\2\u0289G\3\2\2\2\u028a\u028f\5\u013e"+
		"\u00a0\2\u028b\u028c\7\23\2\2\u028c\u028e\5\u013e\u00a0\2\u028d\u028b"+
		"\3\2\2\2\u028e\u0291\3\2\2\2\u028f\u028d\3\2\2\2\u028f\u0290\3\2\2\2\u0290"+
		"I\3\2\2\2\u0291\u028f\3\2\2\2\u0292\u0293\7\u0080\2\2\u0293\u0294\5\u013e"+
		"\u00a0\2\u0294K\3\2\2\2\u0295\u0296\7v\2\2\u0296\u0298\5\u013a\u009e\2"+
		"\u0297\u0299\5N(\2\u0298\u0297\3\2\2\2\u0298\u0299\3\2\2\2\u0299\u029a"+
		"\3\2\2\2\u029a\u029b\7\17\2\2\u029b\u029d\5\20\t\2\u029c\u029e\7\r\2\2"+
		"\u029d\u029c\3\2\2\2\u029d\u029e\3\2\2\2\u029eM\3\2\2\2\u029f\u02a1\7"+
		"\36\2\2\u02a0\u02a2\5P)\2\u02a1\u02a0\3\2\2\2\u02a1\u02a2\3\2\2\2\u02a2"+
		"\u02a3\3\2\2\2\u02a3\u02a4\7\37\2\2\u02a4O\3\2\2\2\u02a5\u02aa\5R*\2\u02a6"+
		"\u02a7\7\16\2\2\u02a7\u02a9\5R*\2\u02a8\u02a6\3\2\2\2\u02a9\u02ac\3\2"+
		"\2\2\u02aa\u02a8\3\2\2\2\u02aa\u02ab\3\2\2\2\u02abQ\3\2\2\2\u02ac\u02aa"+
		"\3\2\2\2\u02ad\u02af\5\u013a\u009e\2\u02ae\u02b0\5T+\2\u02af\u02ae\3\2"+
		"\2\2\u02af\u02b0\3\2\2\2\u02b0\u02b3\3\2\2\2\u02b1\u02b2\7\17\2\2\u02b2"+
		"\u02b4\5V,\2\u02b3\u02b1\3\2\2\2\u02b3\u02b4\3\2\2\2\u02b4S\3\2\2\2\u02b5"+
		"\u02b6\7c\2\2\u02b6\u02b7\5\20\t\2\u02b7U\3\2\2\2\u02b8\u02b9\5\20\t\2"+
		"\u02b9W\3\2\2\2\u02ba\u02bb\7|\2\2\u02bb\u02bc\5Z.\2\u02bc\u02bd\5\n\6"+
		"\2\u02bdY\3\2\2\2\u02be\u02bf\t\3\2\2\u02bf[\3\2\2\2\u02c0\u02c1\7z\2"+
		"\2\u02c1\u02c2\5^\60\2\u02c2\u02c3\5\n\6\2\u02c3]\3\2\2\2\u02c4\u02c9"+
		"\5\u013e\u00a0\2\u02c5\u02c6\7\23\2\2\u02c6\u02c8\5\u013e\u00a0\2\u02c7"+
		"\u02c5\3\2\2\2\u02c8\u02cb\3\2\2\2\u02c9\u02c7\3\2\2\2\u02c9\u02ca\3\2"+
		"\2\2\u02ca_\3\2\2\2\u02cb\u02c9\3\2\2\2\u02cc\u02ce\5b\62\2\u02cd\u02cc"+
		"\3\2\2\2\u02ce\u02cf\3\2\2\2\u02cf\u02cd\3\2\2\2\u02cf\u02d0\3\2\2\2\u02d0"+
		"a\3\2\2\2\u02d1\u02d4\7\u0084\2\2\u02d2\u02d5\5d\63\2\u02d3\u02d5\5f\64"+
		"\2\u02d4\u02d2\3\2\2\2\u02d4\u02d3\3\2\2\2\u02d5c\3\2\2\2\u02d6\u02d7"+
		"\b\63\1\2\u02d7\u02dd\7\u0085\2\2\u02d8\u02d9\7\t\2\2\u02d9\u02da\5\u0116"+
		"\u008c\2\u02da\u02db\7\n\2\2\u02db\u02dd\3\2\2\2\u02dc\u02d6\3\2\2\2\u02dc"+
		"\u02d8\3\2\2\2\u02dd\u02e3\3\2\2\2\u02de\u02df\f\4\2\2\u02df\u02e0\7\23"+
		"\2\2\u02e0\u02e2\5\u013a\u009e\2\u02e1\u02de\3\2\2\2\u02e2\u02e5\3\2\2"+
		"\2\u02e3\u02e1\3\2\2\2\u02e3\u02e4\3\2\2\2\u02e4e\3\2\2\2\u02e5\u02e3"+
		"\3\2\2\2\u02e6\u02e7\5d\63\2\u02e7\u02e8\5\u00ceh\2\u02e8g\3\2\2\2\u02e9"+
		"\u02ea\7l\2\2\u02ea\u02ec\5\u013a\u009e\2\u02eb\u02ed\5N(\2\u02ec\u02eb"+
		"\3\2\2\2\u02ec\u02ed\3\2\2\2\u02ed\u02ef\3\2\2\2\u02ee\u02f0\5j\66\2\u02ef"+
		"\u02ee\3\2\2\2\u02ef\u02f0\3\2\2\2\u02f0\u02f1\3\2\2\2\u02f1\u02f3\7\13"+
		"\2\2\u02f2\u02f4\5n8\2\u02f3\u02f2\3\2\2\2\u02f3\u02f4\3\2\2\2\u02f4\u02f5"+
		"\3\2\2\2\u02f5\u02f7\7\f\2\2\u02f6\u02f8\7\r\2\2\u02f7\u02f6\3\2\2\2\u02f7"+
		"\u02f8\3\2\2\2\u02f8i\3\2\2\2\u02f9\u02fa\7c\2\2\u02fa\u02fb\5l\67\2\u02fb"+
		"k\3\2\2\2\u02fc\u0301\5,\27\2\u02fd\u02fe\7\16\2\2\u02fe\u0300\5,\27\2"+
		"\u02ff\u02fd\3\2\2\2\u0300\u0303\3\2\2\2\u0301\u02ff\3\2\2\2\u0301\u0302"+
		"\3\2\2\2\u0302m\3\2\2\2\u0303\u0301\3\2\2\2\u0304\u0306\5p9\2\u0305\u0307"+
		"\t\4\2\2\u0306\u0305\3\2\2\2\u0306\u0307\3\2\2\2\u0307o\3\2\2\2\u0308"+
		"\u0311\5r:\2\u0309\u030d\b9\1\2\u030a\u030d\7\r\2\2\u030b\u030d\7\16\2"+
		"\2\u030c\u0309\3\2\2\2\u030c\u030a\3\2\2\2\u030c\u030b\3\2\2\2\u030d\u030e"+
		"\3\2\2\2\u030e\u0310\5r:\2\u030f\u030c\3\2\2\2\u0310\u0313\3\2\2\2\u0311"+
		"\u030f\3\2\2\2\u0311\u0312\3\2\2\2\u0312q\3\2\2\2\u0313\u0311\3\2\2\2"+
		"\u0314\u031c\5t;\2\u0315\u031c\5v<\2\u0316\u031c\5x=\2\u0317\u031c\5\u00c8"+
		"e\2\u0318\u031c\5\u00caf\2\u0319\u031c\5|?\2\u031a\u031c\5~@\2\u031b\u0314"+
		"\3\2\2\2\u031b\u0315\3\2\2\2\u031b\u0316\3\2\2\2\u031b\u0317\3\2\2\2\u031b"+
		"\u0318\3\2\2\2\u031b\u0319\3\2\2\2\u031b\u031a\3\2\2\2\u031cs\3\2\2\2"+
		"\u031d\u031f\7G\2\2\u031e\u0320\5N(\2\u031f\u031e\3\2\2\2\u031f\u0320"+
		"\3\2\2\2\u0320\u0321\3\2\2\2\u0321\u0323\5\u00a6T\2\u0322\u0324\5\16\b"+
		"\2\u0323\u0322\3\2\2\2\u0323\u0324\3\2\2\2\u0324u\3\2\2\2\u0325\u0327"+
		"\5N(\2\u0326\u0325\3\2\2\2\u0326\u0327\3\2\2\2\u0327\u0328\3\2\2\2\u0328"+
		"\u032e\5\u00a6T\2\u0329\u032c\7\21\2\2\u032a\u032d\5D#\2\u032b\u032d\5"+
		"\20\t\2\u032c\u032a\3\2\2\2\u032c\u032b\3\2\2\2\u032d\u032f\3\2\2\2\u032e"+
		"\u0329\3\2\2\2\u032e\u032f\3\2\2\2\u032fw\3\2\2\2\u0330\u0332\7^\2\2\u0331"+
		"\u0330\3\2\2\2\u0331\u0332\3\2\2\2\u0332\u0339\3\2\2\2\u0333\u0335\7\26"+
		"\2\2\u0334\u0333\3\2\2\2\u0334\u0335\3\2\2\2\u0335\u0336\3\2\2\2\u0336"+
		"\u033a\7^\2\2\u0337\u0338\7\27\2\2\u0338\u033a\7^\2\2\u0339\u0334\3\2"+
		"\2\2\u0339\u0337\3\2\2\2\u0339\u033a\3\2\2\2\u033a\u033b\3\2\2\2\u033b"+
		"\u033c\7\7\2\2\u033c\u033d\5z>\2\u033d\u0344\7\b\2\2\u033e\u0340\7\26"+
		"\2\2\u033f\u033e\3\2\2\2\u033f\u0340\3\2\2\2\u0340\u0341\3\2\2\2\u0341"+
		"\u0345\7\20\2\2\u0342\u0343\7\27\2\2\u0343\u0345\7\20\2\2\u0344\u033f"+
		"\3\2\2\2\u0344\u0342\3\2\2\2\u0344\u0345\3\2\2\2\u0345\u0346\3\2\2\2\u0346"+
		"\u0347\5\16\b\2\u0347y\3\2\2\2\u0348\u0349\5\u013a\u009e\2\u0349\u034a"+
		"\7\21\2\2\u034a\u034b\t\5\2\2\u034b\u0350\3\2\2\2\u034c\u034d\7\u0085"+
		"\2\2\u034d\u034e\7Z\2\2\u034e\u0350\5\20\t\2\u034f\u0348\3\2\2\2\u034f"+
		"\u034c\3\2\2\2\u0350{\3\2\2\2\u0351\u0353\5\u00c6d\2\u0352\u0354\7\20"+
		"\2\2\u0353\u0352\3\2\2\2\u0353\u0354\3\2\2\2\u0354\u0355\3\2\2\2\u0355"+
		"\u0356\5v<\2\u0356}\3\2\2\2\u0357\u0359\7^\2\2\u0358\u0357\3\2\2\2\u0358"+
		"\u0359\3\2\2\2\u0359\u035a\3\2\2\2\u035a\u035c\5\u00c6d\2\u035b\u035d"+
		"\7\20\2\2\u035c\u035b\3\2\2\2\u035c\u035d\3\2\2\2\u035d\u035f\3\2\2\2"+
		"\u035e\u0360\5\16\b\2\u035f\u035e\3\2\2\2\u035f\u0360\3\2\2\2\u0360\177"+
		"\3\2\2\2\u0361\u0363\7e\2\2\u0362\u0361\3\2\2\2\u0362\u0363\3\2\2\2\u0363"+
		"\u0364\3\2\2\2\u0364\u0365\7b\2\2\u0365\u0366\5\u0138\u009d\2\u0366\u0368"+
		"\7\13\2\2\u0367\u0369\5\u0082B\2\u0368\u0367\3\2\2\2\u0368\u0369\3\2\2"+
		"\2\u0369\u036a\3\2\2\2\u036a\u036c\7\f\2\2\u036b\u036d\7\r\2\2\u036c\u036b"+
		"\3\2\2\2\u036c\u036d\3\2\2\2\u036d\u0081\3\2\2\2\u036e\u0370\5\u0084C"+
		"\2\u036f\u0371\7\16\2\2\u0370\u036f\3\2\2\2\u0370\u0371\3\2\2\2\u0371"+
		"\u0083\3\2\2\2\u0372\u0377\5\u0086D\2\u0373\u0374\7\16\2\2\u0374\u0376"+
		"\5\u0086D\2\u0375\u0373\3\2\2\2\u0376\u0379\3\2\2\2\u0377\u0375\3\2\2"+
		"\2\u0377\u0378\3\2\2\2\u0378\u0085\3\2\2\2\u0379\u0377\3\2\2\2\u037a\u037d"+
		"\5\u00c6d\2\u037b\u037c\7\17\2\2\u037c\u037e\5\u0116\u008c\2\u037d\u037b"+
		"\3\2\2\2\u037d\u037e\3\2\2\2\u037e\u0087\3\2\2\2\u037f\u0381\7S\2\2\u0380"+
		"\u0382\7\32\2\2\u0381\u0380\3\2\2\2\u0381\u0382\3\2\2\2\u0382\u0383\3"+
		"\2\2\2\u0383\u0384\5\u013a\u009e\2\u0384\u0386\5v<\2\u0385\u0387\5\n\6"+
		"\2\u0386\u0385\3\2\2\2\u0386\u0387\3\2\2\2\u0387\u0389\3\2\2\2\u0388\u038a"+
		"\7\r\2\2\u0389\u0388\3\2\2\2\u0389\u038a\3\2\2\2\u038a\u0089\3\2\2\2\u038b"+
		"\u038d\7~\2\2\u038c\u038b\3\2\2\2\u038c\u038d\3\2\2\2\u038d\u038e\3\2"+
		"\2\2\u038e\u038f\7a\2\2\u038f\u0391\5\u0138\u009d\2\u0390\u0392\5N(\2"+
		"\u0391\u0390\3\2\2\2\u0391\u0392\3\2\2\2\u0392\u0393\3\2\2\2\u0393\u0394"+
		"\5\u008cG\2\u0394\u0396\5\u0092J\2\u0395\u0397\7\r\2\2\u0396\u0395\3\2"+
		"\2\2\u0396\u0397\3\2\2\2\u0397\u008b\3\2\2\2\u0398\u039a\5\u008eH\2\u0399"+
		"\u0398\3\2\2\2\u0399\u039a\3\2\2\2\u039a\u039c\3\2\2\2\u039b\u039d\5\u0090"+
		"I\2\u039c\u039b\3\2\2\2\u039c\u039d\3\2\2\2\u039d\u008d\3\2\2\2\u039e"+
		"\u039f\7c\2\2\u039f\u03a0\5,\27\2\u03a0\u008f\3\2\2\2\u03a1\u03a2\7h\2"+
		"\2\u03a2\u03a3\5l\67\2\u03a3\u0091\3\2\2\2\u03a4\u03a6\7\13\2\2\u03a5"+
		"\u03a7\5\u0094K\2\u03a6\u03a5\3\2\2\2\u03a6\u03a7\3\2\2\2\u03a7\u03a9"+
		"\3\2\2\2\u03a8\u03aa\7\r\2\2\u03a9\u03a8\3\2\2\2\u03a9\u03aa\3\2\2\2\u03aa"+
		"\u03ab\3\2\2\2\u03ab\u03ac\7\f\2\2\u03ac\u0093\3\2\2\2\u03ad\u03b5\5\u0096"+
		"L\2\u03ae\u03b1\bK\1\2\u03af\u03b1\7\r\2\2\u03b0\u03ae\3\2\2\2\u03b0\u03af"+
		"\3\2\2\2\u03b1\u03b2\3\2\2\2\u03b2\u03b4\5\u0096L\2\u03b3\u03b0\3\2\2"+
		"\2\u03b4\u03b7\3\2\2\2\u03b5\u03b3\3\2\2\2\u03b5\u03b6\3\2\2\2\u03b6\u0095"+
		"\3\2\2\2\u03b7\u03b5\3\2\2\2\u03b8\u03bf\5\u0098M\2\u03b9\u03bf\5x=\2"+
		"\u03ba\u03bc\5`\61\2\u03bb\u03ba\3\2\2\2\u03bb\u03bc\3\2\2\2\u03bc\u03bd"+
		"\3\2\2\2\u03bd\u03bf\5\u009aN\2\u03be\u03b8\3\2\2\2\u03be\u03b9\3\2\2"+
		"\2\u03be\u03bb\3\2\2\2\u03bf\u0097\3\2\2\2\u03c0\u03c2\5\u00b4[\2\u03c1"+
		"\u03c0\3\2\2\2\u03c1\u03c2\3\2\2\2\u03c2\u03c3\3\2\2\2\u03c3\u03c4\7y"+
		"\2\2\u03c4\u03c6\5\u00a6T\2\u03c5\u03c7\5\n\6\2\u03c6\u03c5\3\2\2\2\u03c6"+
		"\u03c7\3\2\2\2\u03c7\u0099\3\2\2\2\u03c8\u03cb\5\u009cO\2\u03c9\u03cb"+
		"\5\u009eP\2\u03ca\u03c8\3\2\2\2\u03ca\u03c9\3\2\2\2\u03cb\u009b\3\2\2"+
		"\2\u03cc\u03d0\7~\2\2\u03cd\u03ce\7\u0085\2\2\u03ce\u03d1\5v<\2\u03cf"+
		"\u03d1\5\u00e4s\2\u03d0\u03cd\3\2\2\2\u03d0\u03cf\3\2\2\2\u03d1\u03d2"+
		"\3\2\2\2\u03d2\u03d3\5\u0148\u00a5\2\u03d3\u009d\3\2\2\2\u03d4\u03d8\5"+
		"\u00a0Q\2\u03d5\u03d9\5\u00c8e\2\u03d6\u03d9\5\u00caf\2\u03d7\u03d9\5"+
		"\u00a2R\2\u03d8\u03d5\3\2\2\2\u03d8\u03d6\3\2\2\2\u03d8\u03d7\3\2\2\2"+
		"\u03d9\u009f\3\2\2\2\u03da\u03dc\7_\2\2\u03db\u03da\3\2\2\2\u03db\u03dc"+
		"\3\2\2\2\u03dc\u03de\3\2\2\2\u03dd\u03df\5\u00b4[\2\u03de\u03dd\3\2\2"+
		"\2\u03de\u03df\3\2\2\2\u03df\u03e1\3\2\2\2\u03e0\u03e2\7o\2\2\u03e1\u03e0"+
		"\3\2\2\2\u03e1\u03e2\3\2\2\2\u03e2\u00a1\3\2\2\2\u03e3\u03e5\7^\2\2\u03e4"+
		"\u03e3\3\2\2\2\u03e4\u03e5\3\2\2\2\u03e5\u03e6\3\2\2\2\u03e6\u03e8\5\u00c6"+
		"d\2\u03e7\u03e9\7\20\2\2\u03e8\u03e7\3\2\2\2\u03e8\u03e9\3\2\2\2\u03e9"+
		"\u03f4\3\2\2\2\u03ea\u03ec\5\16\b\2\u03eb\u03ea\3\2\2\2\u03eb\u03ec\3"+
		"\2\2\2\u03ec\u03ee\3\2\2\2\u03ed\u03ef\5\u00a4S\2\u03ee\u03ed\3\2\2\2"+
		"\u03ee\u03ef\3\2\2\2\u03ef\u03f5\3\2\2\2\u03f0\u03f2\5v<\2\u03f1\u03f3"+
		"\5\n\6\2\u03f2\u03f1\3\2\2\2\u03f2\u03f3\3\2\2\2\u03f3\u03f5\3\2\2\2\u03f4"+
		"\u03eb\3\2\2\2\u03f4\u03f0\3\2\2\2\u03f5\u00a3\3\2\2\2\u03f6\u03f7\7\17"+
		"\2\2\u03f7\u03f8\5\u0116\u008c\2\u03f8\u00a5\3\2\2\2\u03f9\u03fb\7\t\2"+
		"\2\u03fa\u03fc\5\u00a8U\2\u03fb\u03fa\3\2\2\2\u03fb\u03fc\3\2\2\2\u03fc"+
		"\u03fd\3\2\2\2\u03fd\u03fe\7\n\2\2\u03fe\u00a7\3\2\2\2\u03ff\u0401\5\u00aa"+
		"V\2\u0400\u0402\7\16\2\2\u0401\u0400\3\2\2\2\u0401\u0402\3\2\2\2\u0402"+
		"\u00a9\3\2\2\2\u0403\u0411\5\u00acW\2\u0404\u0409\5\u00aeX\2\u0405\u0406"+
		"\7\16\2\2\u0406\u0408\5\u00aeX\2\u0407\u0405\3\2\2\2\u0408\u040b\3\2\2"+
		"\2\u0409\u0407\3\2\2\2\u0409\u040a\3\2\2\2\u040a\u040e\3\2\2\2\u040b\u0409"+
		"\3\2\2\2\u040c\u040d\7\16\2\2\u040d\u040f\5\u00acW\2\u040e\u040c\3\2\2"+
		"\2\u040e\u040f\3\2\2\2\u040f\u0411\3\2\2\2\u0410\u0403\3\2\2\2\u0410\u0404"+
		"\3\2\2\2\u0411\u00ab\3\2\2\2\u0412\u0413\7\22\2\2\u0413\u0415\5\u00b6"+
		"\\\2\u0414\u0416\5\16\b\2\u0415\u0414\3\2\2\2\u0415\u0416\3\2\2\2\u0416"+
		"\u00ad\3\2\2\2\u0417\u041a\5\u00b0Y\2\u0418\u041a\5\u00b2Z\2\u0419\u0417"+
		"\3\2\2\2\u0419\u0418\3\2\2\2\u041a\u00af\3\2\2\2\u041b\u041d\5`\61\2\u041c"+
		"\u041b\3\2\2\2\u041c\u041d\3\2\2\2\u041d\u041f\3\2\2\2\u041e\u0420\5\u00b4"+
		"[\2\u041f\u041e\3\2\2\2\u041f\u0420\3\2\2\2\u0420\u0421\3\2\2\2\u0421"+
		"\u0423\5\u00b6\\\2\u0422\u0424\5\16\b\2\u0423\u0422\3\2\2\2\u0423\u0424"+
		"\3\2\2\2\u0424\u00b1\3\2\2\2\u0425\u0427\5`\61\2\u0426\u0425\3\2\2\2\u0426"+
		"\u0427\3\2\2\2\u0427\u0429\3\2\2\2\u0428\u042a\5\u00b4[\2\u0429\u0428"+
		"\3\2\2\2\u0429\u042a\3\2\2\2\u042a\u042b\3\2\2\2\u042b\u0434\5\u00b6\\"+
		"\2\u042c\u042e\7\20\2\2\u042d\u042f\5\16\b\2\u042e\u042d\3\2\2\2\u042e"+
		"\u042f\3\2\2\2\u042f\u0435\3\2\2\2\u0430\u0432\5\16\b\2\u0431\u0430\3"+
		"\2\2\2\u0431\u0432\3\2\2\2\u0432\u0433\3\2\2\2\u0433\u0435\5\u00a4S\2"+
		"\u0434\u042c\3\2\2\2\u0434\u0431\3\2\2\2\u0435\u00b3\3\2\2\2\u0436\u0437"+
		"\t\6\2\2\u0437\u00b5\3\2\2\2\u0438\u043b\5\u013a\u009e\2\u0439\u043b\5"+
		"\u00b8]\2\u043a\u0438\3\2\2\2\u043a\u0439\3\2\2\2\u043b\u00b7\3\2\2\2"+
		"\u043c\u043f\5\u00ba^\2\u043d\u043f\5\u00c2b\2\u043e\u043c\3\2\2\2\u043e"+
		"\u043d\3\2\2\2\u043f\u00b9\3\2\2\2\u0440\u0442\7\7\2\2\u0441\u0443\5\u00bc"+
		"_\2\u0442\u0441\3\2\2\2\u0442\u0443\3\2\2\2\u0443\u0444\3\2\2\2\u0444"+
		"\u0445\7\b\2\2\u0445\u00bb\3\2\2\2\u0446\u044f\5\u00be`\2\u0447\u0449"+
		"\7\16\2\2\u0448\u0447\3\2\2\2\u0449\u044a\3\2\2\2\u044a\u0448\3\2\2\2"+
		"\u044a\u044b\3\2\2\2\u044b\u044c\3\2\2\2\u044c\u044e\5\u00be`\2\u044d"+
		"\u0448\3\2\2\2\u044e\u0451\3\2\2\2\u044f\u044d\3\2\2\2\u044f\u0450\3\2"+
		"\2\2\u0450\u0453\3\2\2\2\u0451\u044f\3\2\2\2\u0452\u0454\7\16\2\2\u0453"+
		"\u0452\3\2\2\2\u0453\u0454\3\2\2\2\u0454\u00bd\3\2\2\2\u0455\u0457\7\22"+
		"\2\2\u0456\u0455\3\2\2\2\u0456\u0457\3\2\2\2\u0457\u0458\3\2\2\2\u0458"+
		"\u0459\5\u00c0a\2\u0459\u00bf\3\2\2\2\u045a\u045d\5\u00b8]\2\u045b\u045d"+
		"\7\u0085\2\2\u045c\u045a\3\2\2\2\u045c\u045b\3\2\2\2\u045d\u00c1\3\2\2"+
		"\2\u045e\u046a\7\13\2\2\u045f\u0464\5\u00c4c\2\u0460\u0461\7\16\2\2\u0461"+
		"\u0463\5\u00c4c\2\u0462\u0460\3\2\2\2\u0463\u0466\3\2\2\2\u0464\u0462"+
		"\3\2\2\2\u0464\u0465\3\2\2\2\u0465\u0468\3\2\2\2\u0466\u0464\3\2\2\2\u0467"+
		"\u0469\7\16\2\2\u0468\u0467\3\2\2\2\u0468\u0469\3\2\2\2\u0469\u046b\3"+
		"\2\2\2\u046a\u045f\3\2\2\2\u046a\u046b\3\2\2\2\u046b\u046c\3\2\2\2\u046c"+
		"\u046d\7\f\2\2\u046d\u00c3\3\2\2\2\u046e\u0472\5\u00c6d\2\u046f\u0470"+
		"\7\21\2\2\u0470\u0473\5\u0138\u009d\2\u0471\u0473\5\u00b8]\2\u0472\u046f"+
		"\3\2\2\2\u0472\u0471\3\2\2\2\u0472\u0473\3\2\2\2\u0473\u0476\3\2\2\2\u0474"+
		"\u0475\7\17\2\2\u0475\u0477\5\u0116\u008c\2\u0476\u0474\3\2\2\2\u0476"+
		"\u0477\3\2\2\2\u0477\u047d\3\2\2\2\u0478\u047d\5\u00c8e\2\u0479\u047d"+
		"\5\u00caf\2\u047a\u047d\5\u00ccg\2\u047b\u047d\5\u00acW\2\u047c\u046e"+
		"\3\2\2\2\u047c\u0478\3\2\2\2\u047c\u0479\3\2\2\2\u047c\u047a\3\2\2\2\u047c"+
		"\u047b\3\2\2\2\u047d\u00c5\3\2\2\2\u047e\u048b\7\u0086\2\2\u047f\u048b"+
		"\5\u0136\u009c\2\u0480\u0486\7\7\2\2\u0481\u0482\5\u013a\u009e\2\u0482"+
		"\u0483\7\23\2\2\u0483\u0484\5\u013a\u009e\2\u0484\u0487\3\2\2\2\u0485"+
		"\u0487\7\u0086\2\2\u0486\u0481\3\2\2\2\u0486\u0485\3\2\2\2\u0487\u0488"+
		"\3\2\2\2\u0488\u048b\7\b\2\2\u0489\u048b\5\u013a\u009e\2\u048a\u047e\3"+
		"\2\2\2\u048a\u047f\3\2\2\2\u048a\u0480\3\2\2\2\u048a\u0489\3\2\2\2\u048b"+
		"\u00c7\3\2\2\2\u048c\u048d\5\u0144\u00a3\2\u048d\u048e\7\t\2\2\u048e\u0490"+
		"\7\n\2\2\u048f\u0491\5\16\b\2\u0490\u048f\3\2\2\2\u0490\u0491\3\2\2\2"+
		"\u0491\u0493\3\2\2\2\u0492\u0494\5\n\6\2\u0493\u0492\3\2\2\2\u0493\u0494"+
		"\3\2\2\2\u0494\u00c9\3\2\2\2\u0495\u0496\5\u0146\u00a4\2\u0496\u0499\7"+
		"\t\2\2\u0497\u049a\7\u0085\2\2\u0498\u049a\5\u00b8]\2\u0499\u0497\3\2"+
		"\2\2\u0499\u0498\3\2\2\2\u049a\u049c\3\2\2\2\u049b\u049d\5\16\b\2\u049c"+
		"\u049b\3\2\2\2\u049c\u049d\3\2\2\2\u049d\u049e\3\2\2\2\u049e\u04a0\7\n"+
		"\2\2\u049f\u04a1\5\n\6\2\u04a0\u049f\3\2\2\2\u04a0\u04a1\3\2\2\2\u04a1"+
		"\u00cb\3\2\2\2\u04a2\u04a4\7\32\2\2\u04a3\u04a2\3\2\2\2\u04a3\u04a4\3"+
		"\2\2\2\u04a4\u04a5\3\2\2\2\u04a5\u04a6\7\u0085\2\2\u04a6\u04a7\5\u00a6"+
		"T\2\u04a7\u04a8\5\n\6\2\u04a8\u00cd\3\2\2\2\u04a9\u04ae\7\t\2\2\u04aa"+
		"\u04ac\5\u00d0i\2\u04ab\u04ad\7\16\2\2\u04ac\u04ab\3\2\2\2\u04ac\u04ad"+
		"\3\2\2\2\u04ad\u04af\3\2\2\2\u04ae\u04aa\3\2\2\2\u04ae\u04af\3\2\2\2\u04af"+
		"\u04b0\3\2\2\2\u04b0\u04b1\7\n\2\2\u04b1\u00cf\3\2\2\2\u04b2\u04b7\5\u00d2"+
		"j\2\u04b3\u04b4\7\16\2\2\u04b4\u04b6\5\u00d2j\2\u04b5\u04b3\3\2\2\2\u04b6"+
		"\u04b9\3\2\2\2\u04b7\u04b5\3\2\2\2\u04b7\u04b8\3\2\2\2\u04b8\u00d1\3\2"+
		"\2\2\u04b9\u04b7\3\2\2\2\u04ba\u04bc\7\22\2\2\u04bb\u04ba\3\2\2\2\u04bb"+
		"\u04bc\3\2\2\2\u04bc\u04bf\3\2\2\2\u04bd\u04c0\5\u0116\u008c\2\u04be\u04c0"+
		"\7\u0085\2\2\u04bf\u04bd\3\2\2\2\u04bf\u04be\3\2\2\2\u04c0\u00d3\3\2\2"+
		"\2\u04c1\u04c5\7g\2\2\u04c2\u04c6\5\u00d6l\2\u04c3\u04c6\5\u00dco\2\u04c4"+
		"\u04c6\7\u0086\2\2\u04c5\u04c2\3\2\2\2\u04c5\u04c3\3\2\2\2\u04c5\u04c4"+
		"\3\2\2\2\u04c6\u04c7\3\2\2\2\u04c7\u04c8\5\u0148\u00a5\2\u04c8\u00d5\3"+
		"\2\2\2\u04c9\u04cb\7v\2\2\u04ca\u04c9\3\2\2\2\u04ca\u04cb\3\2\2\2\u04cb"+
		"\u04d1\3\2\2\2\u04cc\u04cd\7\32\2\2\u04cd\u04ce\7\\\2\2\u04ce\u04d2\5"+
		"\u013a\u009e\2\u04cf\u04d2\5\u013a\u009e\2\u04d0\u04d2\5\u00d8m\2\u04d1"+
		"\u04cc\3\2\2\2\u04d1\u04cf\3\2\2\2\u04d1\u04d0\3\2\2\2\u04d2\u04d3\3\2"+
		"\2\2\u04d3\u04d4\7]\2\2\u04d4\u04d5\7\u0086\2\2\u04d5\u00d7\3\2\2\2\u04d6"+
		"\u04d7\5\u013a\u009e\2\u04d7\u04d8\7\16\2\2\u04d8\u04da\3\2\2\2\u04d9"+
		"\u04d6\3\2\2\2\u04d9\u04da\3\2\2\2\u04da\u04db\3\2\2\2\u04db\u04dc\7\13"+
		"\2\2\u04dc\u04e1\5\u00dan\2\u04dd\u04de\7\16\2\2\u04de\u04e0\5\u00dan"+
		"\2\u04df\u04dd\3\2\2\2\u04e0\u04e3\3\2\2\2\u04e1\u04df\3\2\2\2\u04e1\u04e2"+
		"\3\2\2\2\u04e2\u04e5\3\2\2\2\u04e3\u04e1\3\2\2\2\u04e4\u04e6\7\16\2\2"+
		"\u04e5\u04e4\3\2\2\2\u04e5\u04e6\3\2\2\2\u04e6\u04e7\3\2\2\2\u04e7\u04e8"+
		"\7\f\2\2\u04e8\u00d9\3\2\2\2\u04e9\u04ec\5\u013a\u009e\2\u04ea\u04eb\7"+
		"\\\2\2\u04eb\u04ed\5\u013a\u009e\2\u04ec\u04ea\3\2\2\2\u04ec\u04ed\3\2"+
		"\2\2\u04ed\u00db\3\2\2\2\u04ee\u04ef\7\u0085\2\2\u04ef\u04f5\7\17\2\2"+
		"\u04f0\u04f6\5^\60\2\u04f1\u04f2\7{\2\2\u04f2\u04f3\7\t\2\2\u04f3\u04f4"+
		"\7\u0086\2\2\u04f4\u04f6\7\n\2\2\u04f5\u04f0\3\2\2\2\u04f5\u04f1\3\2\2"+
		"\2\u04f6\u00dd\3\2\2\2\u04f7\u04f8\7f\2\2\u04f8\u04f9\5\u00e0q\2\u04f9"+
		"\u00df\3\2\2\2\u04fa\u04fb\7\\\2\2\u04fb\u04fc\7z\2\2\u04fc\u04fd\5\u013a"+
		"\u009e\2\u04fd\u04fe\5\u0148\u00a5\2\u04fe\u0514\3\2\2\2\u04ff\u0500\7"+
		"\17\2\2\u0500\u0501\5^\60\2\u0501\u0502\5\u0148\u00a5\2\u0502\u0514\3"+
		"\2\2\2\u0503\u0504\7g\2\2\u0504\u0505\5\u013a\u009e\2\u0505\u0506\7\17"+
		"\2\2\u0506\u0507\5^\60\2\u0507\u0508\5\u0148\u00a5\2\u0508\u0514\3\2\2"+
		"\2\u0509\u050b\7V\2\2\u050a\u0509\3\2\2\2\u050a\u050b\3\2\2\2\u050b\u050c"+
		"\3\2\2\2\u050c\u0514\5\6\4\2\u050d\u050f\7V\2\2\u050e\u050d\3\2\2\2\u050e"+
		"\u050f\3\2\2\2\u050f\u0510\3\2\2\2\u0510\u0511\5\u00e2r\2\u0511\u0512"+
		"\5\u0148\u00a5\2\u0512\u0514\3\2\2\2\u0513\u04fa\3\2\2\2\u0513\u04ff\3"+
		"\2\2\2\u0513\u0503\3\2\2\2\u0513\u050a\3\2\2\2\u0513\u050e\3\2\2\2\u0514"+
		"\u00e1\3\2\2\2\u0515\u0518\7\32\2\2\u0516\u0517\7\\\2\2\u0517\u0519\5"+
		"\u013a\u009e\2\u0518\u0516\3\2\2\2\u0518\u0519\3\2\2\2\u0519\u051d\3\2"+
		"\2\2\u051a\u051d\5\u013a\u009e\2\u051b\u051d\5\u00d8m\2\u051c\u0515\3"+
		"\2\2\2\u051c\u051a\3\2\2\2\u051c\u051b\3\2\2\2\u051d\u0520\3\2\2\2\u051e"+
		"\u051f\7]\2\2\u051f\u0521\7\u0086\2\2\u0520\u051e\3\2\2\2\u0520\u0521"+
		"\3\2\2\2\u0521\u00e3\3\2\2\2\u0522\u0524\5\u00b4[\2\u0523\u0522\3\2\2"+
		"\2\u0523\u0524\3\2\2\2\u0524\u0526\3\2\2\2\u0525\u0527\7^\2\2\u0526\u0525"+
		"\3\2\2\2\u0526\u0527\3\2\2\2\u0527\u0528\3\2\2\2\u0528\u052b\5\u00e6t"+
		"\2\u0529\u052c\5\u00e8u\2\u052a\u052c\5\u00eav\2\u052b\u0529\3\2\2\2\u052b"+
		"\u052a\3\2\2\2\u052c\u052e\3\2\2\2\u052d\u052f\7\r\2\2\u052e\u052d\3\2"+
		"\2\2\u052e\u052f\3\2\2\2\u052f\u00e5\3\2\2\2\u0530\u0531\t\7\2\2\u0531"+
		"\u00e7\3\2\2\2\u0532\u0534\5\u00b8]\2\u0533\u0535\5\16\b\2\u0534\u0533"+
		"\3\2\2\2\u0534\u0535\3\2\2\2\u0535\u0537\3\2\2\2\u0536\u0538\5\u00a4S"+
		"\2\u0537\u0536\3\2\2\2\u0537\u0538\3\2\2\2\u0538\u00e9\3\2\2\2\u0539\u053e"+
		"\5\u00ecw\2\u053a\u053b\7\16\2\2\u053b\u053d\5\u00ecw\2\u053c\u053a\3"+
		"\2\2\2\u053d\u0540\3\2\2\2\u053e\u053c\3\2\2\2\u053e\u053f\3\2\2\2\u053f"+
		"\u00eb\3\2\2\2\u0540\u053e\3\2\2\2\u0541\u0543\5\u013a\u009e\2\u0542\u0544"+
		"\5\16\b\2\u0543\u0542\3\2\2\2\u0543\u0544\3\2\2\2\u0544\u054a\3\2\2\2"+
		"\u0545\u0547\7\17\2\2\u0546\u0548\5N(\2\u0547\u0546\3\2\2\2\u0547\u0548"+
		"\3\2\2\2\u0548\u0549\3\2\2\2\u0549\u054b\5\u0116\u008c\2\u054a\u0545\3"+
		"\2\2\2\u054a\u054b\3\2\2\2\u054b\u00ed\3\2\2\2\u054c\u054d\7P\2\2\u054d"+
		"\u054e\7\t\2\2\u054e\u054f\5\u0114\u008b\2\u054f\u0550\7\n\2\2\u0550\u0551"+
		"\5\u00f0y\2\u0551\u00ef\3\2\2\2\u0552\u0554\7\13\2\2\u0553\u0555\5\u00f2"+
		"z\2\u0554\u0553\3\2\2\2\u0554\u0555\3\2\2\2\u0555\u055a\3\2\2\2\u0556"+
		"\u0558\5\u00f6|\2\u0557\u0559\5\u00f2z\2\u0558\u0557\3\2\2\2\u0558\u0559"+
		"\3\2\2\2\u0559\u055b\3\2\2\2\u055a\u0556\3\2\2\2\u055a\u055b\3\2\2\2\u055b"+
		"\u055c\3\2\2\2\u055c\u055d\7\f\2\2\u055d\u00f1\3\2\2\2\u055e\u0560\5\u00f4"+
		"{\2\u055f\u055e\3\2\2\2\u0560\u0561\3\2\2\2\u0561\u055f\3\2\2\2\u0561"+
		"\u0562\3\2\2\2\u0562\u00f3\3\2\2\2\u0563\u0564\7E\2\2\u0564\u0565\5\u0114"+
		"\u008b\2\u0565\u0567\7\21\2\2\u0566\u0568\5\f\7\2\u0567\u0566\3\2\2\2"+
		"\u0567\u0568\3\2\2\2\u0568\u00f5\3\2\2\2\u0569\u056a\7V\2\2\u056a\u056c"+
		"\7\21\2\2\u056b\u056d\5\f\7\2\u056c\u056b\3\2\2\2\u056c\u056d\3\2\2\2"+
		"\u056d\u00f7\3\2\2\2\u056e\u056f\7[\2\2\u056f\u0575\5\n\6\2\u0570\u0572"+
		"\5\u00fa~\2\u0571\u0573\5\u00fc\177\2\u0572\u0571\3\2\2\2\u0572\u0573"+
		"\3\2\2\2\u0573\u0576\3\2\2\2\u0574\u0576\5\u00fc\177\2\u0575\u0570\3\2"+
		"\2\2\u0575\u0574\3\2\2\2\u0576\u00f9\3\2\2\2\u0577\u0578\7J\2\2\u0578"+
		"\u0579\7\t\2\2\u0579\u057a\7\u0085\2\2\u057a\u057b\7\n\2\2\u057b\u057c"+
		"\5\n\6\2\u057c\u00fb\3\2\2\2\u057d\u057e\7K\2\2\u057e\u057f\5\n\6\2\u057f"+
		"\u00fd\3\2\2\2\u0580\u0581\7\r\2\2\u0581\u00ff\3\2\2\2\u0582\u0583\7W"+
		"\2\2\u0583\u0584\7\t\2\2\u0584\u0585\5\u0114\u008b\2\u0585\u0586\7\n\2"+
		"\2\u0586\u0589\5\4\3\2\u0587\u0588\7F\2\2\u0588\u058a\5\4\3\2\u0589\u0587"+
		"\3\2\2\2\u0589\u058a\3\2\2\2\u058a\u0101\3\2\2\2\u058b\u058c\7@\2\2\u058c"+
		"\u058d\5\4\3\2\u058d\u058e\7Q\2\2\u058e\u058f\7\t\2\2\u058f\u0590\5\u0114"+
		"\u008b\2\u0590\u0591\7\n\2\2\u0591\u0592\5\u0148\u00a5\2\u0592\u05d1\3"+
		"\2\2\2\u0593\u0594\7Q\2\2\u0594\u0595\7\t\2\2\u0595\u0596\5\u0114\u008b"+
		"\2\u0596\u0597\7\n\2\2\u0597\u0598\5\4\3\2\u0598\u05d1\3\2\2\2\u0599\u059a"+
		"\7O\2\2\u059a\u059c\7\t\2\2\u059b\u059d\5\u0114\u008b\2\u059c\u059b\3"+
		"\2\2\2\u059c\u059d\3\2\2\2\u059d\u059e\3\2\2\2\u059e\u05a0\7\r\2\2\u059f"+
		"\u05a1\5\u0114\u008b\2\u05a0\u059f\3\2\2\2\u05a0\u05a1\3\2\2\2\u05a1\u05a2"+
		"\3\2\2\2\u05a2\u05a4\7\r\2\2\u05a3\u05a5\5\u0114\u008b\2\u05a4\u05a3\3"+
		"\2\2\2\u05a4\u05a5\3\2\2\2\u05a5\u05a6\3\2\2\2\u05a6\u05a7\7\n\2\2\u05a7"+
		"\u05d1\5\4\3\2\u05a8\u05a9\7O\2\2\u05a9\u05aa\7\t\2\2\u05aa\u05ab\5\u00e6"+
		"t\2\u05ab\u05ac\5\u00eav\2\u05ac\u05ae\7\r\2\2\u05ad\u05af\5\u0114\u008b"+
		"\2\u05ae\u05ad\3\2\2\2\u05ae\u05af\3\2\2\2\u05af\u05b0\3\2\2\2\u05b0\u05b2"+
		"\7\r\2\2\u05b1\u05b3\5\u0114\u008b\2\u05b2\u05b1\3\2\2\2\u05b2\u05b3\3"+
		"\2\2\2\u05b3\u05b4\3\2\2\2\u05b4\u05b5\7\n\2\2\u05b5\u05b6\5\4\3\2\u05b6"+
		"\u05d1\3\2\2\2\u05b7\u05b8\7O\2\2\u05b8\u05b9\7\t\2\2\u05b9\u05bd\5\u0116"+
		"\u008c\2\u05ba\u05be\7Z\2\2\u05bb\u05bc\7\u0085\2\2\u05bc\u05be\6\u0082"+
		"\3\2\u05bd\u05ba\3\2\2\2\u05bd\u05bb\3\2\2\2\u05be\u05bf\3\2\2\2\u05bf"+
		"\u05c0\5\u0114\u008b\2\u05c0\u05c1\7\n\2\2\u05c1\u05c2\5\4\3\2\u05c2\u05d1"+
		"\3\2\2\2\u05c3\u05c4\7O\2\2\u05c4\u05c5\7\t\2\2\u05c5\u05c6\5\u00e6t\2"+
		"\u05c6\u05ca\5\u00ecw\2\u05c7\u05cb\7Z\2\2\u05c8\u05c9\7\u0085\2\2\u05c9"+
		"\u05cb\6\u0082\4\2\u05ca\u05c7\3\2\2\2\u05ca\u05c8\3\2\2\2\u05cb\u05cc"+
		"\3\2\2\2\u05cc\u05cd\5\u0114\u008b\2\u05cd\u05ce\7\n\2\2\u05ce\u05cf\5"+
		"\4\3\2\u05cf\u05d1\3\2\2\2\u05d0\u058b\3\2\2\2\u05d0\u0593\3\2\2\2\u05d0"+
		"\u0599\3\2\2\2\u05d0\u05a8\3\2\2\2\u05d0\u05b7\3\2\2\2\u05d0\u05c3\3\2"+
		"\2\2\u05d1\u0103\3\2\2\2\u05d2\u05d5\7N\2\2\u05d3\u05d4\6\u0083\5\2\u05d4"+
		"\u05d6\7\u0085\2\2\u05d5\u05d3\3\2\2\2\u05d5\u05d6\3\2\2\2\u05d6\u05d7"+
		"\3\2\2\2\u05d7\u05d8\5\u0148\u00a5\2\u05d8\u0105\3\2\2\2\u05d9\u05dc\7"+
		"?\2\2\u05da\u05db\6\u0084\6\2\u05db\u05dd\7\u0085\2\2\u05dc\u05da\3\2"+
		"\2\2\u05dc\u05dd\3\2\2\2\u05dd\u05de\3\2\2\2\u05de\u05df\5\u0148\u00a5"+
		"\2\u05df\u0107\3\2\2\2\u05e0\u05e3\7L\2\2\u05e1\u05e2\6\u0085\7\2\u05e2"+
		"\u05e4\5\u0114\u008b\2\u05e3\u05e1\3\2\2\2\u05e3\u05e4\3\2\2\2\u05e4\u05e5"+
		"\3\2\2\2\u05e5\u05e6\5\u0148\u00a5\2\u05e6\u0109\3\2\2\2\u05e7\u05e8\7"+
		"U\2\2\u05e8\u05e9\7\t\2\2\u05e9\u05ea\5\u0114\u008b\2\u05ea\u05eb\7\n"+
		"\2\2\u05eb\u05ec\5\4\3\2\u05ec\u010b\3\2\2\2\u05ed\u05ee\7\u0085\2\2\u05ee"+
		"\u05ef\7\21\2\2\u05ef\u05f0\5\4\3\2\u05f0\u010d\3\2\2\2\u05f1\u05f2\7"+
		"X\2\2\u05f2\u05f3\6\u0088\b\2\u05f3\u05f4\5\u0114\u008b\2\u05f4\u05f5"+
		"\5\u0148\u00a5\2\u05f5\u010f\3\2\2\2\u05f6\u05f7\7R\2\2\u05f7\u05f8\5"+
		"\u0148\u00a5\2\u05f8\u0111\3\2\2\2\u05f9\u05fa\6\u008a\t\2\u05fa\u05fb"+
		"\5\u0114\u008b\2\u05fb\u05fc\5\u0148\u00a5\2\u05fc\u0113\3\2\2\2\u05fd"+
		"\u0602\5\u0116\u008c\2\u05fe\u05ff\7\16\2\2\u05ff\u0601\5\u0116\u008c"+
		"\2\u0600\u05fe\3\2\2\2\u0601\u0604\3\2\2\2\u0602\u0600\3\2\2\2\u0602\u0603"+
		"\3\2\2\2\u0603\u0115\3\2\2\2\u0604\u0602\3\2\2\2\u0605\u0606\b\u008c\1"+
		"\2\u0606\u0620\7T\2\2\u0607\u0620\7d\2\2\u0608\u0620\5\u0130\u0099\2\u0609"+
		"\u0620\5\u00ba^\2\u060a\u0620\5\u00c2b\2\u060b\u0620\5\u0132\u009a\2\u060c"+
		"\u060f\7p\2\2\u060d\u060e\6\u008c\n\2\u060e\u0610\5\u0114\u008b\2\u060f"+
		"\u060d\3\2\2\2\u060f\u0610\3\2\2\2\u0610\u0620\3\2\2\2\u0611\u0612\7`"+
		"\2\2\u0612\u0620\5\u0116\u008c\31\u0613\u0614\5\u0124\u0093\2\u0614\u0615"+
		"\5\u0116\u008c\16\u0615\u0620\3\2\2\2\u0616\u0620\5\u0126\u0094\2\u0617"+
		"\u0620\5\u0118\u008d\2\u0618\u0620\5\u011a\u008e\2\u0619\u0620\5\u011e"+
		"\u0090\2\u061a\u0620\5\u013a\u009e\2\u061b\u061c\7\t\2\2\u061c\u061d\5"+
		"\u0114\u008b\2\u061d\u061e\7\n\2\2\u061e\u0620\3\2\2\2\u061f\u0605\3\2"+
		"\2\2\u061f\u0607\3\2\2\2\u061f\u0608\3\2\2\2\u061f\u0609\3\2\2\2\u061f"+
		"\u060a\3\2\2\2\u061f\u060b\3\2\2\2\u061f\u060c\3\2\2\2\u061f\u0611\3\2"+
		"\2\2\u061f\u0613\3\2\2\2\u061f\u0616\3\2\2\2\u061f\u0617\3\2\2\2\u061f"+
		"\u0618\3\2\2\2\u061f\u0619\3\2\2\2\u061f\u061a\3\2\2\2\u061f\u061b\3\2"+
		"\2\2\u0620\u066f\3\2\2\2\u0621\u0622\f\30\2\2\u0622\u0623\5\u0120\u0091"+
		"\2\u0623\u0624\5\u0116\u008c\31\u0624\u066e\3\2\2\2\u0625\u0626\f\27\2"+
		"\2\u0626\u0627\7\20\2\2\u0627\u0628\5\u0116\u008c\2\u0628\u0629\7\21\2"+
		"\2\u0629\u062a\5\u0116\u008c\30\u062a\u066e\3\2\2\2\u062b\u062c\f\26\2"+
		"\2\u062c\u062d\7\20\2\2\u062d\u062e\7\20\2\2\u062e\u066e\5\u0116\u008c"+
		"\27\u062f\u0630\f\25\2\2\u0630\u0631\t\b\2\2\u0631\u066e\5\u0116\u008c"+
		"\26\u0632\u0633\f\24\2\2\u0633\u0634\t\t\2\2\u0634\u066e\5\u0116\u008c"+
		"\25\u0635\u0636\f\23\2\2\u0636\u0637\t\n\2\2\u0637\u066e\5\u0116\u008c"+
		"\24\u0638\u0639\f\22\2\2\u0639\u063a\5\u0122\u0092\2\u063a\u063b\5\u0116"+
		"\u008c\23\u063b\u066e\3\2\2\2\u063c\u0643\f\21\2\2\u063d\u0644\7\35\2"+
		"\2\u063e\u063f\7\37\2\2\u063f\u0644\7\37\2\2\u0640\u0641\7\37\2\2\u0641"+
		"\u0642\7\37\2\2\u0642\u0644\7\37\2\2\u0643\u063d\3\2\2\2\u0643\u063e\3"+
		"\2\2\2\u0643\u0640\3\2\2\2\u0644\u0645\3\2\2\2\u0645\u066e\5\u0116\u008c"+
		"\22\u0646\u0647\f\20\2\2\u0647\u0648\t\13\2\2\u0648\u066e\5\u0116\u008c"+
		"\21\u0649\u064a\f\17\2\2\u064a\u064b\t\f\2\2\u064b\u066e\5\u0116\u008c"+
		"\20\u064c\u064d\f\r\2\2\u064d\u064e\7\\\2\2\u064e\u066e\5\20\t\2\u064f"+
		"\u0650\f\f\2\2\u0650\u0651\6\u008c\27\2\u0651\u066e\t\r\2\2\u0652\u0653"+
		"\f\13\2\2\u0653\u0655\7\20\2\2\u0654\u0656\7\23\2\2\u0655\u0654\3\2\2"+
		"\2\u0655\u0656\3\2\2\2\u0656\u0657\3\2\2\2\u0657\u0658\7\7\2\2\u0658\u0659"+
		"\5\u0114\u008b\2\u0659\u065a\7\b\2\2\u065a\u066e\3\2\2\2\u065b\u065c\f"+
		"\n\2\2\u065c\u065e\7\20\2\2\u065d\u065f\7\23\2\2\u065e\u065d\3\2\2\2\u065e"+
		"\u065f\3\2\2\2\u065f\u0660\3\2\2\2\u0660\u0662\5\u013a\u009e\2\u0661\u0663"+
		"\5\60\31\2\u0662\u0661\3\2\2\2\u0662\u0663\3\2\2\2\u0663\u0664\3\2\2\2"+
		"\u0664\u0665\5\u00ceh\2\u0665\u066e\3\2\2\2\u0666\u066a\f\t\2\2\u0667"+
		"\u066b\7\23\2\2\u0668\u0669\7\20\2\2\u0669\u066b\7\23\2\2\u066a\u0667"+
		"\3\2\2\2\u066a\u0668\3\2\2\2\u066b\u066c\3\2\2\2\u066c\u066e\5\u013a\u009e"+
		"\2\u066d\u0621\3\2\2\2\u066d\u0625\3\2\2\2\u066d\u062b\3\2\2\2\u066d\u062f"+
		"\3\2\2\2\u066d\u0632\3\2\2\2\u066d\u0635\3\2\2\2\u066d\u0638\3\2\2\2\u066d"+
		"\u063c\3\2\2\2\u066d\u0646\3\2\2\2\u066d\u0649\3\2\2\2\u066d\u064c\3\2"+
		"\2\2\u066d\u064f\3\2\2\2\u066d\u0652\3\2\2\2\u066d\u065b\3\2\2\2\u066d"+
		"\u0666\3\2\2\2\u066e\u0671\3\2\2\2\u066f\u066d\3\2\2\2\u066f\u0670\3\2"+
		"\2\2\u0670\u0117\3\2\2\2\u0671\u066f\3\2\2\2\u0672\u0674\7S\2\2\u0673"+
		"\u0675\7\32\2\2\u0674\u0673\3\2\2\2\u0674\u0675\3\2\2\2\u0675\u0677\3"+
		"\2\2\2\u0676\u0678\7\u0085\2\2\u0677\u0676\3\2\2\2\u0677\u0678\3\2\2\2"+
		"\u0678\u0679\3\2\2\2\u0679\u067b\5\u00a6T\2\u067a\u067c\5\16\b\2\u067b"+
		"\u067a\3\2\2\2\u067b\u067c\3\2\2\2\u067c\u067d\3\2\2\2\u067d\u067e\5\n"+
		"\6\2\u067e\u0119\3\2\2\2\u067f\u0681\7_\2\2\u0680\u067f\3\2\2\2\u0680"+
		"\u0681\3\2\2\2\u0681\u0682\3\2\2\2\u0682\u0684\5\u00a6T\2\u0683\u0685"+
		"\5\16\b\2\u0684\u0683\3\2\2\2\u0684\u0685\3\2\2\2\u0685\u0686\3\2\2\2"+
		"\u0686\u0687\7\66\2\2\u0687\u0688\5\u011c\u008f\2\u0688\u011b\3\2\2\2"+
		"\u0689\u068c\5\u0116\u008c\2\u068a\u068c\5\n\6\2\u068b\u0689\3\2\2\2\u068b"+
		"\u068a\3\2\2\2\u068c\u011d\3\2\2\2\u068d\u068f\7a\2\2\u068e\u0690\7\u0085"+
		"\2\2\u068f\u068e\3\2\2\2\u068f\u0690\3\2\2\2\u0690\u0691\3\2\2\2\u0691"+
		"\u0692\5\u0092J\2\u0692\u011f\3\2\2\2\u0693\u06a5\7\17\2\2\u0694\u06a5"+
		"\7+\2\2\u0695\u06a5\7,\2\2\u0696\u06a5\7-\2\2\u0697\u06a5\7.\2\2\u0698"+
		"\u0699\7\27\2\2\u0699\u06a5\7\17\2\2\u069a\u06a5\7\60\2\2\u069b\u069c"+
		"\7\37\2\2\u069c\u069e\7\37\2\2\u069d\u069f\7\37\2\2\u069e\u069d\3\2\2"+
		"\2\u069e\u069f\3\2\2\2\u069f\u06a0\3\2\2\2\u06a0\u06a5\7\17\2\2\u06a1"+
		"\u06a5\7\63\2\2\u06a2\u06a5\7\64\2\2\u06a3\u06a5\7\65\2\2\u06a4\u0693"+
		"\3\2\2\2\u06a4\u0694\3\2\2\2\u06a4\u0695\3\2\2\2\u06a4\u0696\3\2\2\2\u06a4"+
		"\u0697\3\2\2\2\u06a4\u0698\3\2\2\2\u06a4\u069a\3\2\2\2\u06a4\u069b\3\2"+
		"\2\2\u06a4\u06a1\3\2\2\2\u06a4\u06a2\3\2\2\2\u06a4\u06a3\3\2\2\2\u06a5"+
		"\u0121\3\2\2\2\u06a6\u06a7\t\16\2\2\u06a7\u0123\3\2\2\2\u06a8\u06a9\t"+
		"\17\2\2\u06a9\u0125\3\2\2\2\u06aa\u06b4\7G\2\2\u06ab\u06ac\7\23\2\2\u06ac"+
		"\u06b5\7H\2\2\u06ad\u06af\5\u0116\u008c\2\u06ae\u06b0\5\60\31\2\u06af"+
		"\u06ae\3\2\2\2\u06af\u06b0\3\2\2\2\u06b0\u06b2\3\2\2\2\u06b1\u06b3\5\u00ce"+
		"h\2\u06b2\u06b1\3\2\2\2\u06b2\u06b3\3\2\2\2\u06b3\u06b5\3\2\2\2\u06b4"+
		"\u06ab\3\2\2\2\u06b4\u06ad\3\2\2\2\u06b5\u0127\3\2\2\2\u06b6\u06b7\7\13"+
		"\2\2\u06b7\u06bc\5\u012a\u0096\2\u06b8\u06b9\7\16\2\2\u06b9\u06bb\5\u012a"+
		"\u0096\2\u06ba\u06b8\3\2\2\2\u06bb\u06be\3\2\2\2\u06bc\u06ba\3\2\2\2\u06bc"+
		"\u06bd\3\2\2\2\u06bd\u06c0\3\2\2\2\u06be\u06bc\3\2\2\2\u06bf\u06c1\7\16"+
		"\2\2\u06c0\u06bf\3\2\2\2\u06c0\u06c1\3\2\2\2\u06c1\u06c2\3\2\2\2\u06c2"+
		"\u06c3\7\f\2\2\u06c3\u0129\3\2\2\2\u06c4\u06c5\7\32\2\2\u06c5\u06c6\5"+
		"\u012e\u0098\2\u06c6\u012b\3\2\2\2\u06c7\u06c8\7\13\2\2\u06c8\u06cd\5"+
		"\u012e\u0098\2\u06c9\u06ca\7\16\2\2\u06ca\u06cc\5\u012e\u0098\2\u06cb"+
		"\u06c9\3\2\2\2\u06cc\u06cf\3\2\2\2\u06cd\u06cb\3\2\2\2\u06cd\u06ce\3\2"+
		"\2\2\u06ce\u06d1\3\2\2\2\u06cf\u06cd\3\2\2\2\u06d0\u06d2\7\16\2\2\u06d1"+
		"\u06d0\3\2\2\2\u06d1\u06d2\3\2\2\2\u06d2\u06d3\3\2\2\2\u06d3\u06d4\7\f"+
		"\2\2\u06d4\u012d\3\2\2\2\u06d5\u06d6\7\7\2\2\u06d6\u06d7\5\u0116\u008c"+
		"\2\u06d7\u06d8\7\b\2\2\u06d8\u06d9\5\u00a6T\2\u06d9\u06da\5\n\6\2\u06da"+
		"\u012f\3\2\2\2\u06db\u06e2\7\67\2\2\u06dc\u06e2\79\2\2\u06dd\u06e2\7\u0086"+
		"\2\2\u06de\u06e2\5\u0132\u009a\2\u06df\u06e2\7\6\2\2\u06e0\u06e2\5\u0136"+
		"\u009c\2\u06e1\u06db\3\2\2\2\u06e1\u06dc\3\2\2\2\u06e1\u06dd\3\2\2\2\u06e1"+
		"\u06de\3\2\2\2\u06e1\u06df\3\2\2\2\u06e1\u06e0\3\2\2\2\u06e2\u0131\3\2"+
		"\2\2\u06e3\u06e7\7\u0087\2\2\u06e4\u06e6\5\u0134\u009b\2\u06e5\u06e4\3"+
		"\2\2\2\u06e6\u06e9\3\2\2\2\u06e7\u06e5\3\2\2\2\u06e7\u06e8\3\2\2\2\u06e8"+
		"\u06ea\3\2\2\2\u06e9\u06e7\3\2\2\2\u06ea\u06eb\7\u0087\2\2\u06eb\u0133"+
		"\3\2\2\2\u06ec\u06f2\7\u008e\2\2\u06ed\u06ee\7\u008d\2\2\u06ee\u06ef\5"+
		"\u0116\u008c\2\u06ef\u06f0\7\f\2\2\u06f0\u06f2\3\2\2\2\u06f1\u06ec\3\2"+
		"\2\2\u06f1\u06ed\3\2\2\2\u06f2\u0135\3\2\2\2\u06f3\u06f5\7\27\2\2\u06f4"+
		"\u06f3\3\2\2\2\u06f4\u06f5\3\2\2\2\u06f5\u06f6\3\2\2\2\u06f6\u06fc\7:"+
		"\2\2\u06f7\u06fc\7;\2\2\u06f8\u06fc\7<\2\2\u06f9\u06fc\7=\2\2\u06fa\u06fc"+
		"\7>\2\2\u06fb\u06f4\3\2\2\2\u06fb\u06f7\3\2\2\2\u06fb\u06f8\3\2\2\2\u06fb"+
		"\u06f9\3\2\2\2\u06fb\u06fa\3\2\2\2\u06fc\u0137\3\2\2\2\u06fd\u06fe\t\20"+
		"\2\2\u06fe\u0139\3\2\2\2\u06ff\u0702\5\u013c\u009f\2\u0700\u0702\7\u0085"+
		"\2\2\u0701\u06ff\3\2\2\2\u0701\u0700\3\2\2\2\u0702\u013b\3\2\2\2\u0703"+
		"\u0706\5\u0140\u00a1\2\u0704\u0706\79\2\2\u0705\u0703\3\2\2\2\u0705\u0704"+
		"\3\2\2\2\u0706\u013d\3\2\2\2\u0707\u070b\5\u0142\u00a2\2\u0708\u070b\7"+
		"9\2\2\u0709\u070b\7\u0085\2\2\u070a\u0707\3\2\2\2\u070a\u0708\3\2\2\2"+
		"\u070a\u0709\3\2\2\2\u070b\u013f\3\2\2\2\u070c\u0710\5\u0142\u00a2\2\u070d"+
		"\u0710\7^\2\2\u070e\u0710\7B\2\2\u070f\u070c\3\2\2\2\u070f\u070d\3\2\2"+
		"\2\u070f\u070e\3\2\2\2\u0710\u0141\3\2\2\2\u0711\u0712\t\21\2\2\u0712"+
		"\u0143\3\2\2\2\u0713\u0714\7w\2\2\u0714\u0715\5\u00c6d\2\u0715\u0145\3"+
		"\2\2\2\u0716\u0717\7x\2\2\u0717\u0718\5\u00c6d\2\u0718\u0147\3\2\2\2\u0719"+
		"\u071e\7\r\2\2\u071a\u071e\7\2\2\3\u071b\u071e\6\u00a5\33\2\u071c\u071e"+
		"\6\u00a5\34\2\u071d\u0719\3\2\2\2\u071d\u071a\3\2\2\2\u071d\u071b\3\2"+
		"\2\2\u071d\u071c\3\2\2\2\u071e\u0149\3\2\2\2\u00f6\u014b\u0160\u0163\u016f"+
		"\u0173\u017a\u0189\u018c\u0193\u0197\u019e\u01a2\u01af\u01bb\u01c5\u01c7"+
		"\u01ca\u01d7\u01dc\u01df\u01e2\u01ea\u01ee\u01f2\u01fb\u0204\u0208\u020c"+
		"\u020f\u0212\u0216\u021a\u021f\u0224\u0228\u022f\u0235\u0237\u0240\u0244"+
		"\u024a\u0259\u025e\u0264\u0268\u026a\u026d\u0271\u0276\u0279\u027f\u0283"+
		"\u028f\u0298\u029d\u02a1\u02aa\u02af\u02b3\u02c9\u02cf\u02d4\u02dc\u02e3"+
		"\u02ec\u02ef\u02f3\u02f7\u0301\u0306\u030c\u0311\u031b\u031f\u0323\u0326"+
		"\u032c\u032e\u0331\u0334\u0339\u033f\u0344\u034f\u0353\u0358\u035c\u035f"+
		"\u0362\u0368\u036c\u0370\u0377\u037d\u0381\u0386\u0389\u038c\u0391\u0396"+
		"\u0399\u039c\u03a6\u03a9\u03b0\u03b5\u03bb\u03be\u03c1\u03c6\u03ca\u03d0"+
		"\u03d8\u03db\u03de\u03e1\u03e4\u03e8\u03eb\u03ee\u03f2\u03f4\u03fb\u0401"+
		"\u0409\u040e\u0410\u0415\u0419\u041c\u041f\u0423\u0426\u0429\u042e\u0431"+
		"\u0434\u043a\u043e\u0442\u044a\u044f\u0453\u0456\u045c\u0464\u0468\u046a"+
		"\u0472\u0476\u047c\u0486\u048a\u0490\u0493\u0499\u049c\u04a0\u04a3\u04ac"+
		"\u04ae\u04b7\u04bb\u04bf\u04c5\u04ca\u04d1\u04d9\u04e1\u04e5\u04ec\u04f5"+
		"\u050a\u050e\u0513\u0518\u051c\u0520\u0523\u0526\u052b\u052e\u0534\u0537"+
		"\u053e\u0543\u0547\u054a\u0554\u0558\u055a\u0561\u0567\u056c\u0572\u0575"+
		"\u0589\u059c\u05a0\u05a4\u05ae\u05b2\u05bd\u05ca\u05d0\u05d5\u05dc\u05e3"+
		"\u0602\u060f\u061f\u0643\u0655\u065e\u0662\u066a\u066d\u066f\u0674\u0677"+
		"\u067b\u0680\u0684\u068b\u068f\u069e\u06a4\u06af\u06b2\u06b4\u06bc\u06c0"+
		"\u06cd\u06d1\u06e1\u06e7\u06f1\u06f4\u06fb\u0701\u0705\u070a\u070f\u071d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}