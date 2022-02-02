// Generated from /Users/marcusmews/Eclipses/ECL3/git/n4js/plugins/org.eclipse.n4js.dts/grammar/TypeScriptParser.g4 by ANTLR 4.7.2
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
		Keyof=66, Case=67, Else=68, New=69, Var=70, Catch=71, Finally=72, Return=73, 
		Void=74, Continue=75, For=76, Switch=77, While=78, Debugger=79, Function=80, 
		This=81, With=82, Default=83, If=84, Throw=85, Delete=86, In=87, Try=88, 
		As=89, From=90, ReadOnly=91, Async=92, Class=93, Enum=94, Extends=95, 
		Super=96, Const=97, Export=98, Import=99, Implements=100, Let=101, Private=102, 
		Public=103, Interface=104, Package=105, Protected=106, Static=107, Yield=108, 
		Any=109, Number=110, Boolean=111, String=112, Symbol=113, TypeAlias=114, 
		Get=115, Set=116, Constructor=117, Namespace=118, Require=119, Module=120, 
		Declare=121, Abstract=122, Is=123, Infer=124, Never=125, Unknown=126, 
		Asserts=127, At=128, Identifier=129, StringLiteral=130, BackTick=131, 
		WhiteSpaces=132, LineTerminator=133, HtmlComment=134, CDataComment=135, 
		UnexpectedCharacter=136, TemplateStringStartExpression=137, TemplateStringAtom=138;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_declareStatement = 2, RULE_declarationStatement = 3, 
		RULE_block = 4, RULE_statementList = 5, RULE_colonSepTypeRef = 6, RULE_typeRef = 7, 
		RULE_conditionalTypeRef = 8, RULE_unionTypeExpression = 9, RULE_intersectionTypeExpression = 10, 
		RULE_operatorTypeRef = 11, RULE_typeOperator = 12, RULE_arrayTypeExpression = 13, 
		RULE_primaryTypeExpression = 14, RULE_literalType = 15, RULE_arrowFunctionTypeExpression = 16, 
		RULE_tupleTypeExpression = 17, RULE_tupleTypeArgument = 18, RULE_typeVariable = 19, 
		RULE_typeRefWithModifiers = 20, RULE_parameterizedTypeRef = 21, RULE_typeName = 22, 
		RULE_typeGeneric = 23, RULE_typeArgumentList = 24, RULE_typeArgument = 25, 
		RULE_typeArguments = 26, RULE_objectLiteralTypeRef = 27, RULE_thisTypeRef = 28, 
		RULE_queryTypeRef = 29, RULE_importTypeRef = 30, RULE_anonymousFormalParameterListWithDeclaredThisType = 31, 
		RULE_anonymousFormalParameter = 32, RULE_defaultFormalParameter = 33, 
		RULE_typePredicateWithOperatorTypeRef = 34, RULE_bindingIdentifier = 35, 
		RULE_propertyAccessExpressionInTypeRef = 36, RULE_inferTypeRef = 37, RULE_propertySignature = 38, 
		RULE_constructSignature = 39, RULE_typeParameters = 40, RULE_typeParameterList = 41, 
		RULE_typeParameter = 42, RULE_constraint = 43, RULE_defaultType = 44, 
		RULE_indexSignature = 45, RULE_indexSignatureElement = 46, RULE_methodSignature = 47, 
		RULE_typeAliasDeclaration = 48, RULE_moduleDeclaration = 49, RULE_moduleName = 50, 
		RULE_namespaceDeclaration = 51, RULE_namespaceName = 52, RULE_decoratorList = 53, 
		RULE_decorator = 54, RULE_decoratorMemberExpression = 55, RULE_decoratorCallExpression = 56, 
		RULE_interfaceDeclaration = 57, RULE_interfaceExtendsClause = 58, RULE_classOrInterfaceTypeList = 59, 
		RULE_interfaceBody = 60, RULE_interfaceMemberList = 61, RULE_interfaceMember = 62, 
		RULE_enumDeclaration = 63, RULE_enumBody = 64, RULE_enumMemberList = 65, 
		RULE_enumMember = 66, RULE_functionDeclaration = 67, RULE_classDeclaration = 68, 
		RULE_classHeritage = 69, RULE_classExtendsClause = 70, RULE_classImplementsClause = 71, 
		RULE_classBody = 72, RULE_classMemberList = 73, RULE_classMember = 74, 
		RULE_constructorDeclaration = 75, RULE_propertyMemberDeclaration = 76, 
		RULE_abstractDeclaration = 77, RULE_propertyMember = 78, RULE_propertyMemberBase = 79, 
		RULE_propertyOrMethod = 80, RULE_initializer = 81, RULE_callSignature = 82, 
		RULE_parameterBlock = 83, RULE_parameterListTrailingComma = 84, RULE_parameterList = 85, 
		RULE_restParameter = 86, RULE_parameter = 87, RULE_requiredParameter = 88, 
		RULE_optionalParameter = 89, RULE_accessibilityModifier = 90, RULE_identifierOrPattern = 91, 
		RULE_bindingPattern = 92, RULE_arrayLiteral = 93, RULE_elementList = 94, 
		RULE_arrayElement = 95, RULE_bindingElement = 96, RULE_objectLiteral = 97, 
		RULE_propertyAssignment = 98, RULE_propertyName = 99, RULE_getAccessor = 100, 
		RULE_setAccessor = 101, RULE_generatorMethod = 102, RULE_arguments = 103, 
		RULE_argumentList = 104, RULE_argument = 105, RULE_importStatement = 106, 
		RULE_importFromBlock = 107, RULE_multipleImportElements = 108, RULE_importedElement = 109, 
		RULE_importAliasDeclaration = 110, RULE_exportStatement = 111, RULE_exportStatementTail = 112, 
		RULE_exportFromBlock = 113, RULE_variableStatement = 114, RULE_varModifier = 115, 
		RULE_bindingPatternBlock = 116, RULE_variableDeclarationList = 117, RULE_variableDeclaration = 118, 
		RULE_switchStatement = 119, RULE_caseBlock = 120, RULE_caseClauses = 121, 
		RULE_caseClause = 122, RULE_defaultClause = 123, RULE_tryStatement = 124, 
		RULE_catchProduction = 125, RULE_finallyProduction = 126, RULE_emptyStatement = 127, 
		RULE_ifStatement = 128, RULE_iterationStatement = 129, RULE_continueStatement = 130, 
		RULE_breakStatement = 131, RULE_returnStatement = 132, RULE_yieldStatement = 133, 
		RULE_withStatement = 134, RULE_labelledStatement = 135, RULE_throwStatement = 136, 
		RULE_debuggerStatement = 137, RULE_expressionStatement = 138, RULE_singleExpression = 139, 
		RULE_expressionSequence = 140, RULE_functionExpressionDeclaration = 141, 
		RULE_generatorBlock = 142, RULE_generatorDefinition = 143, RULE_iteratorBlock = 144, 
		RULE_iteratorDefinition = 145, RULE_arrowFunctionDeclaration = 146, RULE_arrowFunctionParameters = 147, 
		RULE_arrowFunctionBody = 148, RULE_assignmentOperator = 149, RULE_literal = 150, 
		RULE_templateStringLiteral = 151, RULE_templateStringAtom = 152, RULE_numericLiteral = 153, 
		RULE_identifierOrKeyWord = 154, RULE_identifierName = 155, RULE_reservedWord = 156, 
		RULE_typeReferenceName = 157, RULE_keyword = 158, RULE_keywordAllowedInTypeReferences = 159, 
		RULE_getter = 160, RULE_setter = 161, RULE_eos = 162;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "declareStatement", "declarationStatement", "block", 
			"statementList", "colonSepTypeRef", "typeRef", "conditionalTypeRef", 
			"unionTypeExpression", "intersectionTypeExpression", "operatorTypeRef", 
			"typeOperator", "arrayTypeExpression", "primaryTypeExpression", "literalType", 
			"arrowFunctionTypeExpression", "tupleTypeExpression", "tupleTypeArgument", 
			"typeVariable", "typeRefWithModifiers", "parameterizedTypeRef", "typeName", 
			"typeGeneric", "typeArgumentList", "typeArgument", "typeArguments", "objectLiteralTypeRef", 
			"thisTypeRef", "queryTypeRef", "importTypeRef", "anonymousFormalParameterListWithDeclaredThisType", 
			"anonymousFormalParameter", "defaultFormalParameter", "typePredicateWithOperatorTypeRef", 
			"bindingIdentifier", "propertyAccessExpressionInTypeRef", "inferTypeRef", 
			"propertySignature", "constructSignature", "typeParameters", "typeParameterList", 
			"typeParameter", "constraint", "defaultType", "indexSignature", "indexSignatureElement", 
			"methodSignature", "typeAliasDeclaration", "moduleDeclaration", "moduleName", 
			"namespaceDeclaration", "namespaceName", "decoratorList", "decorator", 
			"decoratorMemberExpression", "decoratorCallExpression", "interfaceDeclaration", 
			"interfaceExtendsClause", "classOrInterfaceTypeList", "interfaceBody", 
			"interfaceMemberList", "interfaceMember", "enumDeclaration", "enumBody", 
			"enumMemberList", "enumMember", "functionDeclaration", "classDeclaration", 
			"classHeritage", "classExtendsClause", "classImplementsClause", "classBody", 
			"classMemberList", "classMember", "constructorDeclaration", "propertyMemberDeclaration", 
			"abstractDeclaration", "propertyMember", "propertyMemberBase", "propertyOrMethod", 
			"initializer", "callSignature", "parameterBlock", "parameterListTrailingComma", 
			"parameterList", "restParameter", "parameter", "requiredParameter", "optionalParameter", 
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
			"yieldStatement", "withStatement", "labelledStatement", "throwStatement", 
			"debuggerStatement", "expressionStatement", "singleExpression", "expressionSequence", 
			"functionExpressionDeclaration", "generatorBlock", "generatorDefinition", 
			"iteratorBlock", "iteratorDefinition", "arrowFunctionDeclaration", "arrowFunctionParameters", 
			"arrowFunctionBody", "assignmentOperator", "literal", "templateStringLiteral", 
			"templateStringAtom", "numericLiteral", "identifierOrKeyWord", "identifierName", 
			"reservedWord", "typeReferenceName", "keyword", "keywordAllowedInTypeReferences", 
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
			"'unique'", "'keyof'", "'case'", "'else'", "'new'", "'var'", "'catch'", 
			"'finally'", "'return'", "'void'", "'continue'", "'for'", "'switch'", 
			"'while'", "'debugger'", "'function'", "'this'", "'with'", "'default'", 
			"'if'", "'throw'", "'delete'", "'in'", "'try'", "'as'", "'from'", "'readonly'", 
			"'async'", "'class'", "'enum'", "'extends'", "'super'", "'const'", "'export'", 
			"'import'", "'implements'", "'let'", "'private'", "'public'", "'interface'", 
			"'package'", "'protected'", "'static'", "'yield'", "'any'", "'number'", 
			"'boolean'", "'string'", "'symbol'", "'type'", "'get'", "'set'", "'constructor'", 
			"'namespace'", "'require'", "'module'", "'declare'", "'abstract'", "'is'", 
			"'infer'", "'never'", "'unknown'", "'asserts'", "'@'", null, null, null, 
			null, null, null, null, null, "'${'"
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
			"New", "Var", "Catch", "Finally", "Return", "Void", "Continue", "For", 
			"Switch", "While", "Debugger", "Function", "This", "With", "Default", 
			"If", "Throw", "Delete", "In", "Try", "As", "From", "ReadOnly", "Async", 
			"Class", "Enum", "Extends", "Super", "Const", "Export", "Import", "Implements", 
			"Let", "Private", "Public", "Interface", "Package", "Protected", "Static", 
			"Yield", "Any", "Number", "Boolean", "String", "Symbol", "TypeAlias", 
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
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Var - 70)) | (1L << (Return - 70)) | (1L << (Continue - 70)) | (1L << (For - 70)) | (1L << (Switch - 70)) | (1L << (While - 70)) | (1L << (Debugger - 70)) | (1L << (Function - 70)) | (1L << (With - 70)) | (1L << (If - 70)) | (1L << (Throw - 70)) | (1L << (Try - 70)) | (1L << (ReadOnly - 70)) | (1L << (Async - 70)) | (1L << (Class - 70)) | (1L << (Enum - 70)) | (1L << (Const - 70)) | (1L << (Export - 70)) | (1L << (Import - 70)) | (1L << (Let - 70)) | (1L << (Private - 70)) | (1L << (Public - 70)) | (1L << (Interface - 70)) | (1L << (Protected - 70)) | (1L << (Yield - 70)) | (1L << (TypeAlias - 70)) | (1L << (Namespace - 70)) | (1L << (Module - 70)) | (1L << (Declare - 70)) | (1L << (Abstract - 70)) | (1L << (At - 70)) | (1L << (Identifier - 70)))) != 0)) {
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
		public ArrowFunctionDeclarationContext arrowFunctionDeclaration() {
			return getRuleContext(ArrowFunctionDeclarationContext.class,0);
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
		public YieldStatementContext yieldStatement() {
			return getRuleContext(YieldStatementContext.class,0);
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
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(331);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(332);
				importStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(333);
				decoratorList();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(334);
				exportStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(335);
				declareStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(336);
				arrowFunctionDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(337);
				ifStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(338);
				iterationStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(339);
				continueStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(340);
				breakStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(341);
				returnStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(342);
				yieldStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(343);
				withStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(344);
				labelledStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(345);
				switchStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(346);
				throwStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(347);
				tryStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(348);
				debuggerStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(349);
				emptyStatement();
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
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Var - 70)) | (1L << (Return - 70)) | (1L << (Continue - 70)) | (1L << (For - 70)) | (1L << (Switch - 70)) | (1L << (While - 70)) | (1L << (Debugger - 70)) | (1L << (Function - 70)) | (1L << (With - 70)) | (1L << (If - 70)) | (1L << (Throw - 70)) | (1L << (Try - 70)) | (1L << (ReadOnly - 70)) | (1L << (Async - 70)) | (1L << (Class - 70)) | (1L << (Enum - 70)) | (1L << (Const - 70)) | (1L << (Export - 70)) | (1L << (Import - 70)) | (1L << (Let - 70)) | (1L << (Private - 70)) | (1L << (Public - 70)) | (1L << (Interface - 70)) | (1L << (Protected - 70)) | (1L << (Yield - 70)) | (1L << (TypeAlias - 70)) | (1L << (Namespace - 70)) | (1L << (Module - 70)) | (1L << (Declare - 70)) | (1L << (Abstract - 70)) | (1L << (At - 70)) | (1L << (Identifier - 70)))) != 0)) {
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
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Var - 70)) | (1L << (Return - 70)) | (1L << (Continue - 70)) | (1L << (For - 70)) | (1L << (Switch - 70)) | (1L << (While - 70)) | (1L << (Debugger - 70)) | (1L << (Function - 70)) | (1L << (With - 70)) | (1L << (If - 70)) | (1L << (Throw - 70)) | (1L << (Try - 70)) | (1L << (ReadOnly - 70)) | (1L << (Async - 70)) | (1L << (Class - 70)) | (1L << (Enum - 70)) | (1L << (Const - 70)) | (1L << (Export - 70)) | (1L << (Import - 70)) | (1L << (Let - 70)) | (1L << (Private - 70)) | (1L << (Public - 70)) | (1L << (Interface - 70)) | (1L << (Protected - 70)) | (1L << (Yield - 70)) | (1L << (TypeAlias - 70)) | (1L << (Namespace - 70)) | (1L << (Module - 70)) | (1L << (Declare - 70)) | (1L << (Abstract - 70)) | (1L << (At - 70)) | (1L << (Identifier - 70)))) != 0) );
			}
		}
		catch (RecognitionException re) {
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
		public TypeGenericContext typeGeneric() {
			return getRuleContext(TypeGenericContext.class,0);
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
				typeGeneric();
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

	public static class TypeGenericContext extends ParserRuleContext {
		public TerminalNode LessThan() { return getToken(TypeScriptParser.LessThan, 0); }
		public TypeArgumentListContext typeArgumentList() {
			return getRuleContext(TypeArgumentListContext.class,0);
		}
		public TerminalNode MoreThan() { return getToken(TypeScriptParser.MoreThan, 0); }
		public TerminalNode Comma() { return getToken(TypeScriptParser.Comma, 0); }
		public TypeGenericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeGeneric; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeGeneric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeGeneric(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeGeneric(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeGenericContext typeGeneric() throws RecognitionException {
		TypeGenericContext _localctx = new TypeGenericContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_typeGeneric);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(560);
			match(LessThan);
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

			setState(565);
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
			setState(567);
			typeArgument();
			setState(572);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(568);
					match(Comma);
					setState(569);
					typeArgument();
					}
					} 
				}
				setState(574);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
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
			setState(576);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(575);
				match(Infer);
				}
				break;
			}
			setState(578);
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

	public static class TypeArgumentsContext extends ParserRuleContext {
		public TerminalNode LessThan() { return getToken(TypeScriptParser.LessThan, 0); }
		public TerminalNode MoreThan() { return getToken(TypeScriptParser.MoreThan, 0); }
		public TypeArgumentListContext typeArgumentList() {
			return getRuleContext(TypeArgumentListContext.class,0);
		}
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
		enterRule(_localctx, 52, RULE_typeArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(580);
			match(LessThan);
			setState(582);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (OpenBrace - 5)) | (1L << (QuestionMark - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (BitAnd - 5)) | (1L << (BitOr - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Keyof - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(581);
				typeArgumentList();
				}
			}

			setState(584);
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
		enterRule(_localctx, 54, RULE_objectLiteralTypeRef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(586);
			match(OpenBrace);
			setState(588);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (Plus - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(587);
				interfaceBody();
				}
			}

			setState(590);
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
		enterRule(_localctx, 56, RULE_thisTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(592);
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
		enterRule(_localctx, 58, RULE_queryTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(594);
			match(Typeof);
			setState(595);
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
		enterRule(_localctx, 60, RULE_importTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(597);
			match(Import);
			setState(598);
			match(OpenParen);
			setState(599);
			match(StringLiteral);
			setState(600);
			match(CloseParen);
			setState(603);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				{
				setState(601);
				match(Dot);
				setState(602);
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
		enterRule(_localctx, 62, RULE_anonymousFormalParameterListWithDeclaredThisType);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(620);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (OpenBrace - 5)) | (1L << (QuestionMark - 5)) | (1L << (Ellipsis - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (BitAnd - 5)) | (1L << (BitOr - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Keyof - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(608);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
				case 1:
					{
					setState(605);
					match(This);
					setState(606);
					colonSepTypeRef();
					}
					break;
				case 2:
					{
					setState(607);
					anonymousFormalParameter();
					}
					break;
				}
				setState(614);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(610);
						match(Comma);
						setState(611);
						anonymousFormalParameter();
						}
						} 
					}
					setState(616);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				}
				setState(618);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(617);
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
		enterRule(_localctx, 64, RULE_anonymousFormalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(623);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(622);
				match(Ellipsis);
				}
			}

			setState(632);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				{
				setState(625);
				bindingIdentifier();
				setState(627);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==QuestionMark) {
					{
					setState(626);
					match(QuestionMark);
					}
				}

				setState(629);
				colonSepTypeRef();
				}
				}
				break;
			case 2:
				{
				setState(631);
				typeRef();
				}
				break;
			}
			setState(635);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(634);
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
		enterRule(_localctx, 66, RULE_defaultFormalParameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(637);
			match(Assign);
			setState(638);
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
		enterRule(_localctx, 68, RULE_typePredicateWithOperatorTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(641);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(640);
				match(Asserts);
				}
				break;
			}
			setState(645);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				{
				setState(643);
				match(This);
				}
				break;
			case 2:
				{
				setState(644);
				bindingIdentifier();
				}
				break;
			}
			setState(647);
			match(Is);
			setState(648);
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
		enterRule(_localctx, 70, RULE_bindingIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(650);
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
		enterRule(_localctx, 72, RULE_propertyAccessExpressionInTypeRef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(652);
			typeReferenceName();
			setState(657);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(653);
					match(Dot);
					setState(654);
					typeReferenceName();
					}
					} 
				}
				setState(659);
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
		enterRule(_localctx, 74, RULE_inferTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(660);
			match(Infer);
			setState(661);
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

	public static class PropertySignatureContext extends ParserRuleContext {
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public TerminalNode ReadOnly() { return getToken(TypeScriptParser.ReadOnly, 0); }
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(TypeScriptParser.ARROW, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
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
		enterRule(_localctx, 76, RULE_propertySignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(664);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				{
				setState(663);
				match(ReadOnly);
				}
				break;
			}
			setState(666);
			propertyName();
			setState(668);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(667);
				match(QuestionMark);
				}
			}

			setState(671);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(670);
				colonSepTypeRef();
				}
			}

			setState(675);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ARROW) {
				{
				setState(673);
				match(ARROW);
				setState(674);
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
		enterRule(_localctx, 78, RULE_constructSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(677);
			match(New);
			setState(679);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(678);
				typeParameters();
				}
			}

			setState(681);
			parameterBlock();
			setState(683);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(682);
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
		enterRule(_localctx, 80, RULE_typeParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(685);
			match(LessThan);
			setState(687);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (NullLiteral - 53)) | (1L << (UndefinedLiteral - 53)) | (1L << (BooleanLiteral - 53)) | (1L << (Break - 53)) | (1L << (Do - 53)) | (1L << (Instanceof - 53)) | (1L << (Typeof - 53)) | (1L << (Unique - 53)) | (1L << (Case - 53)) | (1L << (Else - 53)) | (1L << (New - 53)) | (1L << (Var - 53)) | (1L << (Catch - 53)) | (1L << (Finally - 53)) | (1L << (Return - 53)) | (1L << (Void - 53)) | (1L << (Continue - 53)) | (1L << (For - 53)) | (1L << (Switch - 53)) | (1L << (While - 53)) | (1L << (Debugger - 53)) | (1L << (Function - 53)) | (1L << (This - 53)) | (1L << (With - 53)) | (1L << (Default - 53)) | (1L << (If - 53)) | (1L << (Throw - 53)) | (1L << (Delete - 53)) | (1L << (In - 53)) | (1L << (Try - 53)) | (1L << (As - 53)) | (1L << (From - 53)) | (1L << (ReadOnly - 53)) | (1L << (Async - 53)) | (1L << (Class - 53)) | (1L << (Enum - 53)) | (1L << (Extends - 53)) | (1L << (Super - 53)) | (1L << (Const - 53)) | (1L << (Export - 53)) | (1L << (Import - 53)) | (1L << (Implements - 53)) | (1L << (Let - 53)) | (1L << (Private - 53)) | (1L << (Public - 53)) | (1L << (Interface - 53)) | (1L << (Package - 53)) | (1L << (Protected - 53)) | (1L << (Static - 53)) | (1L << (Yield - 53)) | (1L << (Any - 53)) | (1L << (Number - 53)) | (1L << (Boolean - 53)) | (1L << (String - 53)) | (1L << (Symbol - 53)) | (1L << (TypeAlias - 53)) | (1L << (Get - 53)) | (1L << (Set - 53)))) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & ((1L << (Constructor - 117)) | (1L << (Namespace - 117)) | (1L << (Require - 117)) | (1L << (Module - 117)) | (1L << (Declare - 117)) | (1L << (Abstract - 117)) | (1L << (Is - 117)) | (1L << (Infer - 117)) | (1L << (Never - 117)) | (1L << (Unknown - 117)) | (1L << (Asserts - 117)) | (1L << (Identifier - 117)))) != 0)) {
				{
				setState(686);
				typeParameterList();
				}
			}

			setState(689);
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
		enterRule(_localctx, 82, RULE_typeParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(691);
			typeParameter();
			setState(696);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(692);
				match(Comma);
				setState(693);
				typeParameter();
				}
				}
				setState(698);
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
		enterRule(_localctx, 84, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(699);
			identifierName();
			setState(701);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(700);
				constraint();
				}
			}

			setState(705);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(703);
				match(Assign);
				setState(704);
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
		enterRule(_localctx, 86, RULE_constraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(707);
			match(Extends);
			setState(708);
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
		enterRule(_localctx, 88, RULE_defaultType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(710);
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
		enterRule(_localctx, 90, RULE_indexSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(713);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
			case 1:
				{
				setState(712);
				match(ReadOnly);
				}
				break;
			}
			setState(721);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Plus:
			case ReadOnly:
				{
				setState(716);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Plus) {
					{
					setState(715);
					match(Plus);
					}
				}

				setState(718);
				match(ReadOnly);
				}
				break;
			case Minus:
				{
				setState(719);
				match(Minus);
				setState(720);
				match(ReadOnly);
				}
				break;
			case OpenBracket:
				break;
			default:
				break;
			}
			setState(723);
			match(OpenBracket);
			setState(724);
			indexSignatureElement();
			setState(725);
			match(CloseBracket);
			setState(732);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QuestionMark:
			case Plus:
				{
				setState(727);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Plus) {
					{
					setState(726);
					match(Plus);
					}
				}

				setState(729);
				match(QuestionMark);
				}
				break;
			case Minus:
				{
				setState(730);
				match(Minus);
				setState(731);
				match(QuestionMark);
				}
				break;
			case Colon:
				break;
			default:
				break;
			}
			setState(734);
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
		enterRule(_localctx, 92, RULE_indexSignatureElement);
		int _la;
		try {
			setState(743);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(736);
				identifierName();
				setState(737);
				match(Colon);
				setState(738);
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
				setState(740);
				match(Identifier);
				setState(741);
				match(In);
				setState(742);
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
		enterRule(_localctx, 94, RULE_methodSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(745);
			propertyName();
			setState(747);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(746);
				match(QuestionMark);
				}
			}

			setState(749);
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
		enterRule(_localctx, 96, RULE_typeAliasDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(751);
			match(TypeAlias);
			setState(752);
			identifierName();
			setState(754);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(753);
				typeParameters();
				}
			}

			setState(756);
			match(Assign);
			setState(757);
			typeRef();
			setState(759);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				{
				setState(758);
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
		enterRule(_localctx, 98, RULE_moduleDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(761);
			match(Module);
			setState(762);
			moduleName();
			setState(763);
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
		enterRule(_localctx, 100, RULE_moduleName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(765);
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
		enterRule(_localctx, 102, RULE_namespaceDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(767);
			match(Namespace);
			setState(768);
			namespaceName();
			setState(769);
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
		enterRule(_localctx, 104, RULE_namespaceName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(771);
			typeReferenceName();
			setState(776);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,72,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(772);
					match(Dot);
					setState(773);
					typeReferenceName();
					}
					} 
				}
				setState(778);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,72,_ctx);
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
		enterRule(_localctx, 106, RULE_decoratorList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(780); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(779);
					decorator();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(782); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,73,_ctx);
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
		enterRule(_localctx, 108, RULE_decorator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(784);
			match(At);
			setState(787);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(785);
				decoratorMemberExpression(0);
				}
				break;
			case 2:
				{
				setState(786);
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
		int _startState = 110;
		enterRecursionRule(_localctx, 110, RULE_decoratorMemberExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(795);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(790);
				match(Identifier);
				}
				break;
			case OpenParen:
				{
				setState(791);
				match(OpenParen);
				setState(792);
				singleExpression(0);
				setState(793);
				match(CloseParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(802);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new DecoratorMemberExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_decoratorMemberExpression);
					setState(797);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(798);
					match(Dot);
					setState(799);
					identifierName();
					}
					} 
				}
				setState(804);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
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
		enterRule(_localctx, 112, RULE_decoratorCallExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(805);
			decoratorMemberExpression(0);
			setState(806);
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
		enterRule(_localctx, 114, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(808);
			match(Interface);
			setState(809);
			identifierName();
			setState(811);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(810);
				typeParameters();
				}
			}

			setState(814);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(813);
				interfaceExtendsClause();
				}
			}

			setState(816);
			match(OpenBrace);
			setState(818);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenParen - 5)) | (1L << (Plus - 5)) | (1L << (Minus - 5)) | (1L << (LessThan - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(817);
				interfaceBody();
				}
			}

			setState(820);
			match(CloseBrace);
			setState(822);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,80,_ctx) ) {
			case 1:
				{
				setState(821);
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
		enterRule(_localctx, 116, RULE_interfaceExtendsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(824);
			match(Extends);
			setState(825);
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
		enterRule(_localctx, 118, RULE_classOrInterfaceTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(827);
			parameterizedTypeRef();
			setState(832);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(828);
				match(Comma);
				setState(829);
				parameterizedTypeRef();
				}
				}
				setState(834);
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
		enterRule(_localctx, 120, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(835);
			interfaceMemberList();
			setState(837);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon || _la==Comma) {
				{
				setState(836);
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
		enterRule(_localctx, 122, RULE_interfaceMemberList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(839);
			interfaceMember();
			setState(848);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,84,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(843);
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
						setState(841);
						match(SemiColon);
						}
						break;
					case Comma:
						{
						setState(842);
						match(Comma);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(845);
					interfaceMember();
					}
					} 
				}
				setState(850);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,84,_ctx);
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
		enterRule(_localctx, 124, RULE_interfaceMember);
		try {
			setState(856);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(851);
				constructSignature();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(852);
				callSignature();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(853);
				indexSignature();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(854);
				methodSignature();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(855);
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

	public static class EnumDeclarationContext extends ParserRuleContext {
		public TerminalNode Enum() { return getToken(TypeScriptParser.Enum, 0); }
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
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
			setState(859);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Const) {
				{
				setState(858);
				match(Const);
				}
			}

			setState(861);
			match(Enum);
			setState(862);
			match(Identifier);
			setState(863);
			match(OpenBrace);
			setState(865);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (Minus - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(864);
				enumBody();
				}
			}

			setState(867);
			match(CloseBrace);
			setState(869);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
			case 1:
				{
				setState(868);
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
			setState(871);
			enumMemberList();
			setState(873);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(872);
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
			setState(875);
			enumMember();
			setState(880);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,90,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(876);
					match(Comma);
					setState(877);
					enumMember();
					}
					} 
				}
				setState(882);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,90,_ctx);
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
			setState(883);
			propertyName();
			setState(886);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(884);
				match(Assign);
				setState(885);
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
			setState(888);
			match(Function);
			setState(890);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(889);
				match(Multiply);
				}
			}

			setState(892);
			identifierName();
			setState(893);
			callSignature();
			setState(895);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,93,_ctx) ) {
			case 1:
				{
				setState(894);
				block();
				}
				break;
			}
			setState(898);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,94,_ctx) ) {
			case 1:
				{
				setState(897);
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
			setState(901);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Abstract) {
				{
				setState(900);
				match(Abstract);
				}
			}

			setState(903);
			match(Class);
			setState(904);
			identifierOrKeyWord();
			setState(906);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(905);
				typeParameters();
				}
			}

			setState(908);
			classHeritage();
			setState(909);
			classBody();
			setState(911);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
			case 1:
				{
				setState(910);
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
			setState(914);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(913);
				classExtendsClause();
				}
			}

			setState(917);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Implements) {
				{
				setState(916);
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
			setState(919);
			match(Extends);
			setState(920);
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
			setState(922);
			match(Implements);
			setState(923);
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
			setState(925);
			match(OpenBrace);
			setState(927);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (Plus - 5)) | (1L << (Minus - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(926);
				classMemberList();
				}
			}

			setState(930);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon) {
				{
				setState(929);
				match(SemiColon);
				}
			}

			setState(932);
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
			setState(934);
			classMember();
			setState(942);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(937);
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
						setState(936);
						match(SemiColon);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(939);
					classMember();
					}
					} 
				}
				setState(944);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
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
			setState(951);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,105,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(945);
				constructorDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(946);
				indexSignature();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(948);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==At) {
					{
					setState(947);
					decoratorList();
					}
				}

				setState(950);
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
			setState(954);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Private - 102)) | (1L << (Public - 102)) | (1L << (Protected - 102)))) != 0)) {
				{
				setState(953);
				accessibilityModifier();
				}
			}

			setState(956);
			match(Constructor);
			setState(957);
			parameterBlock();
			setState(959);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(958);
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
			setState(963);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(961);
				abstractDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(962);
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
			setState(965);
			match(Abstract);
			setState(969);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(966);
				match(Identifier);
				setState(967);
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
				setState(968);
				variableStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(971);
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
			setState(973);
			propertyMemberBase();
			setState(977);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,110,_ctx) ) {
			case 1:
				{
				setState(974);
				getAccessor();
				}
				break;
			case 2:
				{
				setState(975);
				setAccessor();
				}
				break;
			case 3:
				{
				setState(976);
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
		public TerminalNode ReadOnly() { return getToken(TypeScriptParser.ReadOnly, 0); }
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
			setState(980);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,111,_ctx) ) {
			case 1:
				{
				setState(979);
				match(Async);
				}
				break;
			}
			setState(983);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,112,_ctx) ) {
			case 1:
				{
				setState(982);
				accessibilityModifier();
				}
				break;
			}
			setState(986);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,113,_ctx) ) {
			case 1:
				{
				setState(985);
				match(Static);
				}
				break;
			}
			setState(989);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,114,_ctx) ) {
			case 1:
				{
				setState(988);
				match(ReadOnly);
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
			setState(991);
			propertyName();
			setState(993);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(992);
				match(QuestionMark);
				}
			}

			setState(1005);
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
				setState(996);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(995);
					colonSepTypeRef();
					}
				}

				setState(999);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Assign) {
					{
					setState(998);
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
				setState(1001);
				callSignature();
				setState(1003);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OpenBrace) {
					{
					setState(1002);
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
			setState(1007);
			match(Assign);
			setState(1008);
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
		enterRule(_localctx, 164, RULE_callSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1011);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(1010);
				typeParameters();
				}
			}

			setState(1013);
			parameterBlock();
			setState(1019);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,122,_ctx) ) {
			case 1:
				{
				setState(1014);
				match(Colon);
				setState(1017);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,121,_ctx) ) {
				case 1:
					{
					setState(1015);
					typePredicateWithOperatorTypeRef();
					}
					break;
				case 2:
					{
					setState(1016);
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
		enterRule(_localctx, 166, RULE_parameterBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1021);
			match(OpenParen);
			setState(1023);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (OpenBrace - 5)) | (1L << (Ellipsis - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(1022);
				parameterListTrailingComma();
				}
			}

			setState(1025);
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
		enterRule(_localctx, 168, RULE_parameterListTrailingComma);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1027);
			parameterList();
			setState(1029);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1028);
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
		enterRule(_localctx, 170, RULE_parameterList);
		try {
			int _alt;
			setState(1044);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Ellipsis:
				enterOuterAlt(_localctx, 1);
				{
				setState(1031);
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
				setState(1032);
				parameter();
				setState(1037);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,125,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1033);
						match(Comma);
						setState(1034);
						parameter();
						}
						} 
					}
					setState(1039);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,125,_ctx);
				}
				setState(1042);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,126,_ctx) ) {
				case 1:
					{
					setState(1040);
					match(Comma);
					setState(1041);
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
		enterRule(_localctx, 172, RULE_restParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1046);
			match(Ellipsis);
			setState(1047);
			identifierOrPattern();
			setState(1049);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1048);
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
		enterRule(_localctx, 174, RULE_parameter);
		try {
			setState(1053);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,129,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1051);
				requiredParameter();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1052);
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
		enterRule(_localctx, 176, RULE_requiredParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1056);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==At) {
				{
				setState(1055);
				decoratorList();
				}
			}

			setState(1059);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,131,_ctx) ) {
			case 1:
				{
				setState(1058);
				accessibilityModifier();
				}
				break;
			}
			setState(1061);
			identifierOrPattern();
			setState(1063);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1062);
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
		enterRule(_localctx, 178, RULE_optionalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1066);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==At) {
				{
				setState(1065);
				decoratorList();
				}
			}

			setState(1069);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,134,_ctx) ) {
			case 1:
				{
				setState(1068);
				accessibilityModifier();
				}
				break;
			}
			setState(1071);
			identifierOrPattern();
			setState(1080);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QuestionMark:
				{
				setState(1072);
				match(QuestionMark);
				setState(1074);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(1073);
					colonSepTypeRef();
					}
				}

				}
				break;
			case Assign:
			case Colon:
				{
				setState(1077);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(1076);
					colonSepTypeRef();
					}
				}

				setState(1079);
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
		enterRule(_localctx, 180, RULE_accessibilityModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1082);
			_la = _input.LA(1);
			if ( !(((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Private - 102)) | (1L << (Public - 102)) | (1L << (Protected - 102)))) != 0)) ) {
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
		enterRule(_localctx, 182, RULE_identifierOrPattern);
		try {
			setState(1086);
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
				setState(1084);
				identifierName();
				}
				break;
			case OpenBracket:
			case OpenBrace:
				enterOuterAlt(_localctx, 2);
				{
				setState(1085);
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
		enterRule(_localctx, 184, RULE_bindingPattern);
		try {
			setState(1090);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
				enterOuterAlt(_localctx, 1);
				{
				setState(1088);
				arrayLiteral();
				}
				break;
			case OpenBrace:
				enterOuterAlt(_localctx, 2);
				{
				setState(1089);
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
		enterRule(_localctx, 186, RULE_arrayLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1092);
			match(OpenBracket);
			setState(1094);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBracket) | (1L << OpenBrace) | (1L << Ellipsis))) != 0) || _la==Identifier) {
				{
				setState(1093);
				elementList();
				}
			}

			setState(1096);
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
		enterRule(_localctx, 188, RULE_elementList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1098);
			arrayElement();
			setState(1107);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,142,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1100); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(1099);
						match(Comma);
						}
						}
						setState(1102); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==Comma );
					setState(1104);
					arrayElement();
					}
					} 
				}
				setState(1109);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,142,_ctx);
			}
			setState(1111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1110);
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
		enterRule(_localctx, 190, RULE_arrayElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(1113);
				match(Ellipsis);
				}
			}

			setState(1116);
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
		enterRule(_localctx, 192, RULE_bindingElement);
		try {
			setState(1120);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
			case OpenBrace:
				enterOuterAlt(_localctx, 1);
				{
				setState(1118);
				bindingPattern();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1119);
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
		enterRule(_localctx, 194, RULE_objectLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1122);
			match(OpenBrace);
			setState(1134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 5)) & ~0x3f) == 0 && ((1L << (_la - 5)) & ((1L << (OpenBracket - 5)) | (1L << (Ellipsis - 5)) | (1L << (Minus - 5)) | (1L << (Multiply - 5)) | (1L << (NullLiteral - 5)) | (1L << (UndefinedLiteral - 5)) | (1L << (BooleanLiteral - 5)) | (1L << (DecimalLiteral - 5)) | (1L << (HexIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral - 5)) | (1L << (OctalIntegerLiteral2 - 5)) | (1L << (BinaryIntegerLiteral - 5)) | (1L << (Break - 5)) | (1L << (Do - 5)) | (1L << (Instanceof - 5)) | (1L << (Typeof - 5)) | (1L << (Unique - 5)) | (1L << (Case - 5)) | (1L << (Else - 5)))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (New - 69)) | (1L << (Var - 69)) | (1L << (Catch - 69)) | (1L << (Finally - 69)) | (1L << (Return - 69)) | (1L << (Void - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (This - 69)) | (1L << (With - 69)) | (1L << (Default - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Delete - 69)) | (1L << (In - 69)) | (1L << (Try - 69)) | (1L << (As - 69)) | (1L << (From - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Extends - 69)) | (1L << (Super - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Implements - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Package - 69)) | (1L << (Protected - 69)) | (1L << (Static - 69)) | (1L << (Yield - 69)) | (1L << (Any - 69)) | (1L << (Number - 69)) | (1L << (Boolean - 69)) | (1L << (String - 69)) | (1L << (Symbol - 69)) | (1L << (TypeAlias - 69)) | (1L << (Get - 69)) | (1L << (Set - 69)) | (1L << (Constructor - 69)) | (1L << (Namespace - 69)) | (1L << (Require - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (Is - 69)) | (1L << (Infer - 69)) | (1L << (Never - 69)) | (1L << (Unknown - 69)) | (1L << (Asserts - 69)) | (1L << (Identifier - 69)) | (1L << (StringLiteral - 69)))) != 0)) {
				{
				setState(1123);
				propertyAssignment();
				setState(1128);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,146,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1124);
						match(Comma);
						setState(1125);
						propertyAssignment();
						}
						} 
					}
					setState(1130);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,146,_ctx);
				}
				setState(1132);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(1131);
					match(Comma);
					}
				}

				}
			}

			setState(1136);
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
		enterRule(_localctx, 196, RULE_propertyAssignment);
		int _la;
		try {
			setState(1152);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,151,_ctx) ) {
			case 1:
				_localctx = new PropertyExpressionAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1138);
				propertyName();
				setState(1142);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Colon:
					{
					setState(1139);
					match(Colon);
					setState(1140);
					identifierOrKeyWord();
					}
					break;
				case OpenBracket:
				case OpenBrace:
					{
					setState(1141);
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
				setState(1146);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Assign) {
					{
					setState(1144);
					match(Assign);
					setState(1145);
					singleExpression(0);
					}
				}

				}
				break;
			case 2:
				_localctx = new PropertyGetterContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1148);
				getAccessor();
				}
				break;
			case 3:
				_localctx = new PropertySetterContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1149);
				setAccessor();
				}
				break;
			case 4:
				_localctx = new MethodPropertyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1150);
				generatorMethod();
				}
				break;
			case 5:
				_localctx = new RestParameterInObjectContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1151);
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
		enterRule(_localctx, 198, RULE_propertyName);
		try {
			setState(1166);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case StringLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1154);
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
				setState(1155);
				numericLiteral();
				}
				break;
			case OpenBracket:
				enterOuterAlt(_localctx, 3);
				{
				setState(1156);
				match(OpenBracket);
				setState(1162);
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
					setState(1157);
					identifierName();
					setState(1158);
					match(Dot);
					setState(1159);
					identifierName();
					}
					break;
				case StringLiteral:
					{
					setState(1161);
					match(StringLiteral);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1164);
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
				setState(1165);
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
		enterRule(_localctx, 200, RULE_getAccessor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1168);
			getter();
			setState(1169);
			match(OpenParen);
			setState(1170);
			match(CloseParen);
			setState(1172);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1171);
				colonSepTypeRef();
				}
			}

			setState(1175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(1174);
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
		enterRule(_localctx, 202, RULE_setAccessor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1177);
			setter();
			setState(1178);
			match(OpenParen);
			setState(1181);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(1179);
				match(Identifier);
				}
				break;
			case OpenBracket:
			case OpenBrace:
				{
				setState(1180);
				bindingPattern();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1184);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1183);
				colonSepTypeRef();
				}
			}

			setState(1186);
			match(CloseParen);
			setState(1188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(1187);
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
		enterRule(_localctx, 204, RULE_generatorMethod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(1190);
				match(Multiply);
				}
			}

			setState(1193);
			match(Identifier);
			setState(1194);
			parameterBlock();
			setState(1195);
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
		enterRule(_localctx, 206, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1197);
			match(OpenParen);
			setState(1202);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (RegularExpressionLiteral - 4)) | (1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (OpenBrace - 4)) | (1L << (Ellipsis - 4)) | (1L << (PlusPlus - 4)) | (1L << (MinusMinus - 4)) | (1L << (Plus - 4)) | (1L << (Minus - 4)) | (1L << (BitNot - 4)) | (1L << (Not - 4)) | (1L << (LessThan - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (Else - 68)) | (1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)) | (1L << (BackTick - 68)))) != 0)) {
				{
				setState(1198);
				argumentList();
				setState(1200);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(1199);
					match(Comma);
					}
				}

				}
			}

			setState(1204);
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
		enterRule(_localctx, 208, RULE_argumentList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1206);
			argument();
			setState(1211);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,162,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1207);
					match(Comma);
					setState(1208);
					argument();
					}
					} 
				}
				setState(1213);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,162,_ctx);
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
		enterRule(_localctx, 210, RULE_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(1214);
				match(Ellipsis);
				}
			}

			setState(1219);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,164,_ctx) ) {
			case 1:
				{
				setState(1217);
				singleExpression(0);
				}
				break;
			case 2:
				{
				setState(1218);
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
		enterRule(_localctx, 212, RULE_importStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1221);
			match(Import);
			setState(1225);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,165,_ctx) ) {
			case 1:
				{
				setState(1222);
				importFromBlock();
				}
				break;
			case 2:
				{
				setState(1223);
				importAliasDeclaration();
				}
				break;
			case 3:
				{
				setState(1224);
				match(StringLiteral);
				}
				break;
			}
			setState(1227);
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
		enterRule(_localctx, 214, RULE_importFromBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1230);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,166,_ctx) ) {
			case 1:
				{
				setState(1229);
				match(TypeAlias);
				}
				break;
			}
			setState(1237);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,167,_ctx) ) {
			case 1:
				{
				setState(1232);
				match(Multiply);
				setState(1233);
				match(As);
				setState(1234);
				identifierName();
				}
				break;
			case 2:
				{
				setState(1235);
				identifierName();
				}
				break;
			case 3:
				{
				setState(1236);
				multipleImportElements();
				}
				break;
			}
			setState(1239);
			match(From);
			setState(1240);
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
		enterRule(_localctx, 216, RULE_multipleImportElements);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (NullLiteral - 53)) | (1L << (UndefinedLiteral - 53)) | (1L << (BooleanLiteral - 53)) | (1L << (Break - 53)) | (1L << (Do - 53)) | (1L << (Instanceof - 53)) | (1L << (Typeof - 53)) | (1L << (Unique - 53)) | (1L << (Case - 53)) | (1L << (Else - 53)) | (1L << (New - 53)) | (1L << (Var - 53)) | (1L << (Catch - 53)) | (1L << (Finally - 53)) | (1L << (Return - 53)) | (1L << (Void - 53)) | (1L << (Continue - 53)) | (1L << (For - 53)) | (1L << (Switch - 53)) | (1L << (While - 53)) | (1L << (Debugger - 53)) | (1L << (Function - 53)) | (1L << (This - 53)) | (1L << (With - 53)) | (1L << (Default - 53)) | (1L << (If - 53)) | (1L << (Throw - 53)) | (1L << (Delete - 53)) | (1L << (In - 53)) | (1L << (Try - 53)) | (1L << (As - 53)) | (1L << (From - 53)) | (1L << (ReadOnly - 53)) | (1L << (Async - 53)) | (1L << (Class - 53)) | (1L << (Enum - 53)) | (1L << (Extends - 53)) | (1L << (Super - 53)) | (1L << (Const - 53)) | (1L << (Export - 53)) | (1L << (Import - 53)) | (1L << (Implements - 53)) | (1L << (Let - 53)) | (1L << (Private - 53)) | (1L << (Public - 53)) | (1L << (Interface - 53)) | (1L << (Package - 53)) | (1L << (Protected - 53)) | (1L << (Static - 53)) | (1L << (Yield - 53)) | (1L << (Any - 53)) | (1L << (Number - 53)) | (1L << (Boolean - 53)) | (1L << (String - 53)) | (1L << (Symbol - 53)) | (1L << (TypeAlias - 53)) | (1L << (Get - 53)) | (1L << (Set - 53)))) != 0) || ((((_la - 117)) & ~0x3f) == 0 && ((1L << (_la - 117)) & ((1L << (Constructor - 117)) | (1L << (Namespace - 117)) | (1L << (Require - 117)) | (1L << (Module - 117)) | (1L << (Declare - 117)) | (1L << (Abstract - 117)) | (1L << (Is - 117)) | (1L << (Infer - 117)) | (1L << (Never - 117)) | (1L << (Unknown - 117)) | (1L << (Asserts - 117)) | (1L << (Identifier - 117)))) != 0)) {
				{
				setState(1242);
				identifierName();
				setState(1243);
				match(Comma);
				}
			}

			setState(1247);
			match(OpenBrace);
			setState(1248);
			importedElement();
			setState(1253);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,169,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1249);
					match(Comma);
					setState(1250);
					importedElement();
					}
					} 
				}
				setState(1255);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,169,_ctx);
			}
			setState(1257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1256);
				match(Comma);
				}
			}

			setState(1259);
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
		enterRule(_localctx, 218, RULE_importedElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1261);
			identifierName();
			setState(1264);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==As) {
				{
				setState(1262);
				match(As);
				setState(1263);
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
		enterRule(_localctx, 220, RULE_importAliasDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1266);
			match(Identifier);
			setState(1267);
			match(Assign);
			setState(1273);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,172,_ctx) ) {
			case 1:
				{
				setState(1268);
				namespaceName();
				}
				break;
			case 2:
				{
				setState(1269);
				match(Require);
				setState(1270);
				match(OpenParen);
				setState(1271);
				match(StringLiteral);
				setState(1272);
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
		enterRule(_localctx, 222, RULE_exportStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1275);
			match(Export);
			setState(1276);
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
		enterRule(_localctx, 224, RULE_exportStatementTail);
		int _la;
		try {
			setState(1303);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,175,_ctx) ) {
			case 1:
				_localctx = new ExportAsNamespaceContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1278);
				match(As);
				setState(1279);
				match(Namespace);
				setState(1280);
				identifierName();
				setState(1281);
				eos();
				}
				break;
			case 2:
				_localctx = new ExportEqualsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1283);
				match(Assign);
				setState(1284);
				namespaceName();
				setState(1285);
				eos();
				}
				break;
			case 3:
				_localctx = new ExportImportContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1287);
				match(Import);
				setState(1288);
				identifierName();
				setState(1289);
				match(Assign);
				setState(1290);
				namespaceName();
				setState(1291);
				eos();
				}
				break;
			case 4:
				_localctx = new ExportDeclareStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1294);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Default) {
					{
					setState(1293);
					match(Default);
					}
				}

				setState(1296);
				declareStatement();
				}
				break;
			case 5:
				_localctx = new ExportElementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1298);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,174,_ctx) ) {
				case 1:
					{
					setState(1297);
					match(Default);
					}
					break;
				}
				setState(1300);
				exportFromBlock();
				setState(1301);
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
		enterRule(_localctx, 226, RULE_exportFromBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1312);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,177,_ctx) ) {
			case 1:
				{
				setState(1305);
				match(Multiply);
				setState(1308);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,176,_ctx) ) {
				case 1:
					{
					setState(1306);
					match(As);
					setState(1307);
					identifierName();
					}
					break;
				}
				}
				break;
			case 2:
				{
				setState(1310);
				identifierName();
				}
				break;
			case 3:
				{
				setState(1311);
				multipleImportElements();
				}
				break;
			}
			setState(1316);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,178,_ctx) ) {
			case 1:
				{
				setState(1314);
				match(From);
				setState(1315);
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
		enterRule(_localctx, 228, RULE_variableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1319);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 102)) & ~0x3f) == 0 && ((1L << (_la - 102)) & ((1L << (Private - 102)) | (1L << (Public - 102)) | (1L << (Protected - 102)))) != 0)) {
				{
				setState(1318);
				accessibilityModifier();
				}
			}

			setState(1322);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ReadOnly) {
				{
				setState(1321);
				match(ReadOnly);
				}
			}

			setState(1324);
			varModifier();
			setState(1327);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
			case OpenBrace:
				{
				setState(1325);
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
				setState(1326);
				variableDeclarationList();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1330);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,182,_ctx) ) {
			case 1:
				{
				setState(1329);
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
		enterRule(_localctx, 230, RULE_varModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1332);
			_la = _input.LA(1);
			if ( !(((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Var - 70)) | (1L << (Const - 70)) | (1L << (Let - 70)))) != 0)) ) {
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
		enterRule(_localctx, 232, RULE_bindingPatternBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1334);
			bindingPattern();
			setState(1336);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,183,_ctx) ) {
			case 1:
				{
				setState(1335);
				colonSepTypeRef();
				}
				break;
			}
			setState(1339);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,184,_ctx) ) {
			case 1:
				{
				setState(1338);
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
		enterRule(_localctx, 234, RULE_variableDeclarationList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1341);
			variableDeclaration();
			setState(1346);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,185,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1342);
					match(Comma);
					setState(1343);
					variableDeclaration();
					}
					} 
				}
				setState(1348);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,185,_ctx);
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
		enterRule(_localctx, 236, RULE_variableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1349);
			identifierName();
			setState(1351);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,186,_ctx) ) {
			case 1:
				{
				setState(1350);
				colonSepTypeRef();
				}
				break;
			}
			setState(1358);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,188,_ctx) ) {
			case 1:
				{
				setState(1353);
				match(Assign);
				setState(1355);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,187,_ctx) ) {
				case 1:
					{
					setState(1354);
					typeParameters();
					}
					break;
				}
				setState(1357);
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
		enterRule(_localctx, 238, RULE_switchStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1360);
			match(Switch);
			setState(1361);
			match(OpenParen);
			setState(1362);
			expressionSequence();
			setState(1363);
			match(CloseParen);
			setState(1364);
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
		enterRule(_localctx, 240, RULE_caseBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1366);
			match(OpenBrace);
			setState(1368);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Case) {
				{
				setState(1367);
				caseClauses();
				}
			}

			setState(1374);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Default) {
				{
				setState(1370);
				defaultClause();
				setState(1372);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Case) {
					{
					setState(1371);
					caseClauses();
					}
				}

				}
			}

			setState(1376);
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
		enterRule(_localctx, 242, RULE_caseClauses);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1379); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1378);
				caseClause();
				}
				}
				setState(1381); 
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
		enterRule(_localctx, 244, RULE_caseClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1383);
			match(Case);
			setState(1384);
			expressionSequence();
			setState(1385);
			match(Colon);
			setState(1387);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Var - 70)) | (1L << (Return - 70)) | (1L << (Continue - 70)) | (1L << (For - 70)) | (1L << (Switch - 70)) | (1L << (While - 70)) | (1L << (Debugger - 70)) | (1L << (Function - 70)) | (1L << (With - 70)) | (1L << (If - 70)) | (1L << (Throw - 70)) | (1L << (Try - 70)) | (1L << (ReadOnly - 70)) | (1L << (Async - 70)) | (1L << (Class - 70)) | (1L << (Enum - 70)) | (1L << (Const - 70)) | (1L << (Export - 70)) | (1L << (Import - 70)) | (1L << (Let - 70)) | (1L << (Private - 70)) | (1L << (Public - 70)) | (1L << (Interface - 70)) | (1L << (Protected - 70)) | (1L << (Yield - 70)) | (1L << (TypeAlias - 70)) | (1L << (Namespace - 70)) | (1L << (Module - 70)) | (1L << (Declare - 70)) | (1L << (Abstract - 70)) | (1L << (At - 70)) | (1L << (Identifier - 70)))) != 0)) {
				{
				setState(1386);
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
		enterRule(_localctx, 246, RULE_defaultClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1389);
			match(Default);
			setState(1390);
			match(Colon);
			setState(1392);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (Var - 70)) | (1L << (Return - 70)) | (1L << (Continue - 70)) | (1L << (For - 70)) | (1L << (Switch - 70)) | (1L << (While - 70)) | (1L << (Debugger - 70)) | (1L << (Function - 70)) | (1L << (With - 70)) | (1L << (If - 70)) | (1L << (Throw - 70)) | (1L << (Try - 70)) | (1L << (ReadOnly - 70)) | (1L << (Async - 70)) | (1L << (Class - 70)) | (1L << (Enum - 70)) | (1L << (Const - 70)) | (1L << (Export - 70)) | (1L << (Import - 70)) | (1L << (Let - 70)) | (1L << (Private - 70)) | (1L << (Public - 70)) | (1L << (Interface - 70)) | (1L << (Protected - 70)) | (1L << (Yield - 70)) | (1L << (TypeAlias - 70)) | (1L << (Namespace - 70)) | (1L << (Module - 70)) | (1L << (Declare - 70)) | (1L << (Abstract - 70)) | (1L << (At - 70)) | (1L << (Identifier - 70)))) != 0)) {
				{
				setState(1391);
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
		enterRule(_localctx, 248, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1394);
			match(Try);
			setState(1395);
			block();
			setState(1401);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Catch:
				{
				setState(1396);
				catchProduction();
				setState(1398);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Finally) {
					{
					setState(1397);
					finallyProduction();
					}
				}

				}
				break;
			case Finally:
				{
				setState(1400);
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
		enterRule(_localctx, 250, RULE_catchProduction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1403);
			match(Catch);
			setState(1404);
			match(OpenParen);
			setState(1405);
			match(Identifier);
			setState(1406);
			match(CloseParen);
			setState(1407);
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
		enterRule(_localctx, 252, RULE_finallyProduction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1409);
			match(Finally);
			setState(1410);
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
		enterRule(_localctx, 254, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1412);
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
		enterRule(_localctx, 256, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1414);
			match(If);
			setState(1415);
			match(OpenParen);
			setState(1416);
			expressionSequence();
			setState(1417);
			match(CloseParen);
			setState(1418);
			statement();
			setState(1421);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,197,_ctx) ) {
			case 1:
				{
				setState(1419);
				match(Else);
				setState(1420);
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
		enterRule(_localctx, 258, RULE_iterationStatement);
		int _la;
		try {
			setState(1492);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,205,_ctx) ) {
			case 1:
				_localctx = new DoStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1423);
				match(Do);
				setState(1424);
				statement();
				setState(1425);
				match(While);
				setState(1426);
				match(OpenParen);
				setState(1427);
				expressionSequence();
				setState(1428);
				match(CloseParen);
				setState(1429);
				eos();
				}
				break;
			case 2:
				_localctx = new WhileStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1431);
				match(While);
				setState(1432);
				match(OpenParen);
				setState(1433);
				expressionSequence();
				setState(1434);
				match(CloseParen);
				setState(1435);
				statement();
				}
				break;
			case 3:
				_localctx = new ForStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1437);
				match(For);
				setState(1438);
				match(OpenParen);
				setState(1440);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (RegularExpressionLiteral - 4)) | (1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (OpenBrace - 4)) | (1L << (PlusPlus - 4)) | (1L << (MinusMinus - 4)) | (1L << (Plus - 4)) | (1L << (Minus - 4)) | (1L << (BitNot - 4)) | (1L << (Not - 4)) | (1L << (LessThan - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (Else - 68)) | (1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)) | (1L << (BackTick - 68)))) != 0)) {
					{
					setState(1439);
					expressionSequence();
					}
				}

				setState(1442);
				match(SemiColon);
				setState(1444);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (RegularExpressionLiteral - 4)) | (1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (OpenBrace - 4)) | (1L << (PlusPlus - 4)) | (1L << (MinusMinus - 4)) | (1L << (Plus - 4)) | (1L << (Minus - 4)) | (1L << (BitNot - 4)) | (1L << (Not - 4)) | (1L << (LessThan - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (Else - 68)) | (1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)) | (1L << (BackTick - 68)))) != 0)) {
					{
					setState(1443);
					expressionSequence();
					}
				}

				setState(1446);
				match(SemiColon);
				setState(1448);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (RegularExpressionLiteral - 4)) | (1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (OpenBrace - 4)) | (1L << (PlusPlus - 4)) | (1L << (MinusMinus - 4)) | (1L << (Plus - 4)) | (1L << (Minus - 4)) | (1L << (BitNot - 4)) | (1L << (Not - 4)) | (1L << (LessThan - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (Else - 68)) | (1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)) | (1L << (BackTick - 68)))) != 0)) {
					{
					setState(1447);
					expressionSequence();
					}
				}

				setState(1450);
				match(CloseParen);
				setState(1451);
				statement();
				}
				break;
			case 4:
				_localctx = new ForVarStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1452);
				match(For);
				setState(1453);
				match(OpenParen);
				setState(1454);
				varModifier();
				setState(1455);
				variableDeclarationList();
				setState(1456);
				match(SemiColon);
				setState(1458);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (RegularExpressionLiteral - 4)) | (1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (OpenBrace - 4)) | (1L << (PlusPlus - 4)) | (1L << (MinusMinus - 4)) | (1L << (Plus - 4)) | (1L << (Minus - 4)) | (1L << (BitNot - 4)) | (1L << (Not - 4)) | (1L << (LessThan - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (Else - 68)) | (1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)) | (1L << (BackTick - 68)))) != 0)) {
					{
					setState(1457);
					expressionSequence();
					}
				}

				setState(1460);
				match(SemiColon);
				setState(1462);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (RegularExpressionLiteral - 4)) | (1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (OpenBrace - 4)) | (1L << (PlusPlus - 4)) | (1L << (MinusMinus - 4)) | (1L << (Plus - 4)) | (1L << (Minus - 4)) | (1L << (BitNot - 4)) | (1L << (Not - 4)) | (1L << (LessThan - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (Else - 68)) | (1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)) | (1L << (BackTick - 68)))) != 0)) {
					{
					setState(1461);
					expressionSequence();
					}
				}

				setState(1464);
				match(CloseParen);
				setState(1465);
				statement();
				}
				break;
			case 5:
				_localctx = new ForInStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1467);
				match(For);
				setState(1468);
				match(OpenParen);
				setState(1469);
				singleExpression(0);
				setState(1473);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case In:
					{
					setState(1470);
					match(In);
					}
					break;
				case Identifier:
					{
					setState(1471);
					match(Identifier);
					setState(1472);
					if (!(this.p("of"))) throw new FailedPredicateException(this, "this.p(\"of\")");
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1475);
				expressionSequence();
				setState(1476);
				match(CloseParen);
				setState(1477);
				statement();
				}
				break;
			case 6:
				_localctx = new ForVarInStatementContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1479);
				match(For);
				setState(1480);
				match(OpenParen);
				setState(1481);
				varModifier();
				setState(1482);
				variableDeclaration();
				setState(1486);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case In:
					{
					setState(1483);
					match(In);
					}
					break;
				case Identifier:
					{
					setState(1484);
					match(Identifier);
					setState(1485);
					if (!(this.p("of"))) throw new FailedPredicateException(this, "this.p(\"of\")");
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1488);
				expressionSequence();
				setState(1489);
				match(CloseParen);
				setState(1490);
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
		enterRule(_localctx, 260, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1494);
			match(Continue);
			setState(1497);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,206,_ctx) ) {
			case 1:
				{
				setState(1495);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1496);
				match(Identifier);
				}
				break;
			}
			setState(1499);
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
		enterRule(_localctx, 262, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1501);
			match(Break);
			setState(1504);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,207,_ctx) ) {
			case 1:
				{
				setState(1502);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1503);
				match(Identifier);
				}
				break;
			}
			setState(1506);
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
		enterRule(_localctx, 264, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1508);
			match(Return);
			setState(1511);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,208,_ctx) ) {
			case 1:
				{
				setState(1509);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1510);
				expressionSequence();
				}
				break;
			}
			setState(1513);
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

	public static class YieldStatementContext extends ParserRuleContext {
		public TerminalNode Yield() { return getToken(TypeScriptParser.Yield, 0); }
		public EosContext eos() {
			return getRuleContext(EosContext.class,0);
		}
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public YieldStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yieldStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterYieldStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitYieldStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitYieldStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final YieldStatementContext yieldStatement() throws RecognitionException {
		YieldStatementContext _localctx = new YieldStatementContext(_ctx, getState());
		enterRule(_localctx, 266, RULE_yieldStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1515);
			match(Yield);
			setState(1518);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,209,_ctx) ) {
			case 1:
				{
				setState(1516);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1517);
				expressionSequence();
				}
				break;
			}
			setState(1520);
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
		enterRule(_localctx, 268, RULE_withStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1522);
			match(With);
			setState(1523);
			match(OpenParen);
			setState(1524);
			expressionSequence();
			setState(1525);
			match(CloseParen);
			setState(1526);
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
		enterRule(_localctx, 270, RULE_labelledStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1528);
			match(Identifier);
			setState(1529);
			match(Colon);
			setState(1530);
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
		enterRule(_localctx, 272, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1532);
			match(Throw);
			setState(1533);
			if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
			setState(1534);
			expressionSequence();
			setState(1535);
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
		enterRule(_localctx, 274, RULE_debuggerStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1537);
			match(Debugger);
			setState(1538);
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
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
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
		enterRule(_localctx, 276, RULE_expressionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1540);
			if (!(this.notOpenBraceAndNotFunction())) throw new FailedPredicateException(this, "this.notOpenBraceAndNotFunction()");
			setState(1541);
			expressionSequence();
			setState(1543);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon) {
				{
				setState(1542);
				match(SemiColon);
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
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
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
	public static class GeneratorsExpressionContext extends SingleExpressionContext {
		public GeneratorBlockContext generatorBlock() {
			return getRuleContext(GeneratorBlockContext.class,0);
		}
		public GeneratorsExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterGeneratorsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitGeneratorsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitGeneratorsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PreIncrementExpressionContext extends SingleExpressionContext {
		public TerminalNode PlusPlus() { return getToken(TypeScriptParser.PlusPlus, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public PreIncrementExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPreIncrementExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPreIncrementExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPreIncrementExpression(this);
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
	public static class InExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode In() { return getToken(TypeScriptParser.In, 0); }
		public InExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterInExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitInExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitInExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GenericTypesContext extends SingleExpressionContext {
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public GenericTypesContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterGenericTypes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitGenericTypes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitGenericTypes(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotExpressionContext extends SingleExpressionContext {
		public TerminalNode Not() { return getToken(TypeScriptParser.Not, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public NotExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterNotExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitNotExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitNotExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PreDecreaseExpressionContext extends SingleExpressionContext {
		public TerminalNode MinusMinus() { return getToken(TypeScriptParser.MinusMinus, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public PreDecreaseExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPreDecreaseExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPreDecreaseExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPreDecreaseExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArgumentsExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ArgumentsExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArgumentsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArgumentsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArgumentsExpression(this);
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
	public static class LogicalExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode And() { return getToken(TypeScriptParser.And, 0); }
		public TerminalNode Or() { return getToken(TypeScriptParser.Or, 0); }
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
	public static class FunctionExpressionContext extends SingleExpressionContext {
		public FunctionExpressionDeclarationContext functionExpressionDeclaration() {
			return getRuleContext(FunctionExpressionDeclarationContext.class,0);
		}
		public FunctionExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
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
	public static class UnaryMinusExpressionContext extends SingleExpressionContext {
		public TerminalNode Minus() { return getToken(TypeScriptParser.Minus, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public UnaryMinusExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterUnaryMinusExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitUnaryMinusExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitUnaryMinusExpression(this);
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
	public static class AssignmentExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode Assign() { return getToken(TypeScriptParser.Assign, 0); }
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
	public static class PostDecreaseExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode MinusMinus() { return getToken(TypeScriptParser.MinusMinus, 0); }
		public PostDecreaseExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPostDecreaseExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPostDecreaseExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPostDecreaseExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TypeofExpressionContext extends SingleExpressionContext {
		public TerminalNode Typeof() { return getToken(TypeScriptParser.Typeof, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TypeofExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeofExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeofExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeofExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InstanceofExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode Instanceof() { return getToken(TypeScriptParser.Instanceof, 0); }
		public InstanceofExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterInstanceofExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitInstanceofExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitInstanceofExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryPlusExpressionContext extends SingleExpressionContext {
		public TerminalNode Plus() { return getToken(TypeScriptParser.Plus, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public UnaryPlusExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterUnaryPlusExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitUnaryPlusExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitUnaryPlusExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeleteExpressionContext extends SingleExpressionContext {
		public TerminalNode Delete() { return getToken(TypeScriptParser.Delete, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public DeleteExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterDeleteExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitDeleteExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitDeleteExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrowFunctionExpressionContext extends SingleExpressionContext {
		public ArrowFunctionDeclarationContext arrowFunctionDeclaration() {
			return getRuleContext(ArrowFunctionDeclarationContext.class,0);
		}
		public ArrowFunctionExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
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
	public static class IteratorsExpressionContext extends SingleExpressionContext {
		public IteratorBlockContext iteratorBlock() {
			return getRuleContext(IteratorBlockContext.class,0);
		}
		public IteratorsExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterIteratorsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitIteratorsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitIteratorsExpression(this);
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
	public static class RelationalExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public TerminalNode LessThan() { return getToken(TypeScriptParser.LessThan, 0); }
		public TerminalNode MoreThan() { return getToken(TypeScriptParser.MoreThan, 0); }
		public TerminalNode LessThanEquals() { return getToken(TypeScriptParser.LessThanEquals, 0); }
		public TerminalNode GreaterThanEquals() { return getToken(TypeScriptParser.GreaterThanEquals, 0); }
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
	public static class PostIncrementExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode PlusPlus() { return getToken(TypeScriptParser.PlusPlus, 0); }
		public PostIncrementExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPostIncrementExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPostIncrementExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPostIncrementExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class YieldExpressionContext extends SingleExpressionContext {
		public YieldStatementContext yieldStatement() {
			return getRuleContext(YieldStatementContext.class,0);
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
	public static class BitNotExpressionContext extends SingleExpressionContext {
		public TerminalNode BitNot() { return getToken(TypeScriptParser.BitNot, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public BitNotExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterBitNotExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitBitNotExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitBitNotExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NewExpressionContext extends SingleExpressionContext {
		public TerminalNode New() { return getToken(TypeScriptParser.New, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public NewExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
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
	public static class MemberDotExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode Dot() { return getToken(TypeScriptParser.Dot, 0); }
		public IdentifierNameContext identifierName() {
			return getRuleContext(IdentifierNameContext.class,0);
		}
		public TypeGenericContext typeGeneric() {
			return getRuleContext(TypeGenericContext.class,0);
		}
		public MemberDotExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterMemberDotExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitMemberDotExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitMemberDotExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ClassExpressionContext extends SingleExpressionContext {
		public TerminalNode Class() { return getToken(TypeScriptParser.Class, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public ClassExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
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
	public static class MemberIndexExpressionContext extends SingleExpressionContext {
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public TerminalNode OpenBracket() { return getToken(TypeScriptParser.OpenBracket, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public TerminalNode CloseBracket() { return getToken(TypeScriptParser.CloseBracket, 0); }
		public MemberIndexExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterMemberIndexExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitMemberIndexExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitMemberIndexExpression(this);
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
	public static class AssignmentOperatorExpressionContext extends SingleExpressionContext {
		public List<SingleExpressionContext> singleExpression() {
			return getRuleContexts(SingleExpressionContext.class);
		}
		public SingleExpressionContext singleExpression(int i) {
			return getRuleContext(SingleExpressionContext.class,i);
		}
		public AssignmentOperatorContext assignmentOperator() {
			return getRuleContext(AssignmentOperatorContext.class,0);
		}
		public AssignmentOperatorExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterAssignmentOperatorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitAssignmentOperatorExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitAssignmentOperatorExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VoidExpressionContext extends SingleExpressionContext {
		public TerminalNode Void() { return getToken(TypeScriptParser.Void, 0); }
		public SingleExpressionContext singleExpression() {
			return getRuleContext(SingleExpressionContext.class,0);
		}
		public VoidExpressionContext(SingleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterVoidExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitVoidExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitVoidExpression(this);
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
		int _startState = 278;
		enterRecursionRule(_localctx, 278, RULE_singleExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1596);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,215,_ctx) ) {
			case 1:
				{
				_localctx = new FunctionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1546);
				functionExpressionDeclaration();
				}
				break;
			case 2:
				{
				_localctx = new ArrowFunctionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1547);
				arrowFunctionDeclaration();
				}
				break;
			case 3:
				{
				_localctx = new ClassExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1548);
				match(Class);
				setState(1550);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Identifier) {
					{
					setState(1549);
					match(Identifier);
					}
				}

				setState(1552);
				classBody();
				}
				break;
			case 4:
				{
				_localctx = new NewExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1553);
				match(New);
				setState(1554);
				singleExpression(0);
				setState(1556);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,212,_ctx) ) {
				case 1:
					{
					setState(1555);
					typeArguments();
					}
					break;
				}
				setState(1559);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,213,_ctx) ) {
				case 1:
					{
					setState(1558);
					arguments();
					}
					break;
				}
				}
				break;
			case 5:
				{
				_localctx = new DeleteExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1561);
				match(Delete);
				setState(1562);
				singleExpression(34);
				}
				break;
			case 6:
				{
				_localctx = new VoidExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1563);
				match(Void);
				setState(1564);
				singleExpression(33);
				}
				break;
			case 7:
				{
				_localctx = new TypeofExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1565);
				match(Typeof);
				setState(1566);
				singleExpression(32);
				}
				break;
			case 8:
				{
				_localctx = new PreIncrementExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1567);
				match(PlusPlus);
				setState(1568);
				singleExpression(31);
				}
				break;
			case 9:
				{
				_localctx = new PreDecreaseExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1569);
				match(MinusMinus);
				setState(1570);
				singleExpression(30);
				}
				break;
			case 10:
				{
				_localctx = new UnaryPlusExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1571);
				match(Plus);
				setState(1572);
				singleExpression(29);
				}
				break;
			case 11:
				{
				_localctx = new UnaryMinusExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1573);
				match(Minus);
				setState(1574);
				singleExpression(28);
				}
				break;
			case 12:
				{
				_localctx = new BitNotExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1575);
				match(BitNot);
				setState(1576);
				singleExpression(27);
				}
				break;
			case 13:
				{
				_localctx = new NotExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1577);
				match(Not);
				setState(1578);
				singleExpression(26);
				}
				break;
			case 14:
				{
				_localctx = new IteratorsExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1579);
				iteratorBlock();
				}
				break;
			case 15:
				{
				_localctx = new GeneratorsExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1580);
				generatorBlock();
				}
				break;
			case 16:
				{
				_localctx = new YieldExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1581);
				yieldStatement();
				}
				break;
			case 17:
				{
				_localctx = new ThisExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1582);
				match(This);
				}
				break;
			case 18:
				{
				_localctx = new IdentifierExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1583);
				identifierName();
				}
				break;
			case 19:
				{
				_localctx = new SuperExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1584);
				match(Super);
				}
				break;
			case 20:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1585);
				literal();
				}
				break;
			case 21:
				{
				_localctx = new ArrayLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1586);
				arrayLiteral();
				}
				break;
			case 22:
				{
				_localctx = new ObjectLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1587);
				objectLiteral();
				}
				break;
			case 23:
				{
				_localctx = new ParenthesizedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1588);
				match(OpenParen);
				setState(1589);
				expressionSequence();
				setState(1590);
				match(CloseParen);
				}
				break;
			case 24:
				{
				_localctx = new GenericTypesContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1592);
				typeArguments();
				setState(1594);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,214,_ctx) ) {
				case 1:
					{
					setState(1593);
					expressionSequence();
					}
					break;
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1671);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,219,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1669);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,218,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicativeExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1598);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(1599);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Multiply) | (1L << Divide) | (1L << Modulus))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1600);
						singleExpression(26);
						}
						break;
					case 2:
						{
						_localctx = new AdditiveExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1601);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(1602);
						_la = _input.LA(1);
						if ( !(_la==Plus || _la==Minus) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1603);
						singleExpression(25);
						}
						break;
					case 3:
						{
						_localctx = new BitShiftExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1604);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(1611);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,216,_ctx) ) {
						case 1:
							{
							setState(1605);
							match(LeftShiftArithmetic);
							}
							break;
						case 2:
							{
							setState(1606);
							match(MoreThan);
							setState(1607);
							match(MoreThan);
							}
							break;
						case 3:
							{
							setState(1608);
							match(MoreThan);
							setState(1609);
							match(MoreThan);
							setState(1610);
							match(MoreThan);
							}
							break;
						}
						setState(1613);
						singleExpression(24);
						}
						break;
					case 4:
						{
						_localctx = new RelationalExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1614);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(1615);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LessThan) | (1L << MoreThan) | (1L << LessThanEquals) | (1L << GreaterThanEquals))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1616);
						singleExpression(23);
						}
						break;
					case 5:
						{
						_localctx = new InstanceofExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1617);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(1618);
						match(Instanceof);
						setState(1619);
						singleExpression(22);
						}
						break;
					case 6:
						{
						_localctx = new InExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1620);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(1621);
						match(In);
						setState(1622);
						singleExpression(21);
						}
						break;
					case 7:
						{
						_localctx = new EqualityExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1623);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(1624);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Equals_) | (1L << NotEquals) | (1L << IdentityEquals) | (1L << IdentityNotEquals))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1625);
						singleExpression(20);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1626);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(1627);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BitAnd) | (1L << BitXOr) | (1L << BitOr))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1628);
						singleExpression(19);
						}
						break;
					case 9:
						{
						_localctx = new LogicalExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1629);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(1630);
						_la = _input.LA(1);
						if ( !(_la==And || _la==Or) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1631);
						singleExpression(18);
						}
						break;
					case 10:
						{
						_localctx = new TernaryExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1632);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(1633);
						match(QuestionMark);
						setState(1634);
						singleExpression(0);
						setState(1635);
						match(Colon);
						setState(1636);
						singleExpression(17);
						}
						break;
					case 11:
						{
						_localctx = new AssignmentExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1638);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(1639);
						match(Assign);
						setState(1640);
						singleExpression(16);
						}
						break;
					case 12:
						{
						_localctx = new AssignmentOperatorExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1641);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(1642);
						assignmentOperator();
						setState(1643);
						singleExpression(15);
						}
						break;
					case 13:
						{
						_localctx = new MemberIndexExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1645);
						if (!(precpred(_ctx, 40))) throw new FailedPredicateException(this, "precpred(_ctx, 40)");
						setState(1646);
						match(OpenBracket);
						setState(1647);
						expressionSequence();
						setState(1648);
						match(CloseBracket);
						}
						break;
					case 14:
						{
						_localctx = new MemberDotExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1650);
						if (!(precpred(_ctx, 39))) throw new FailedPredicateException(this, "precpred(_ctx, 39)");
						setState(1651);
						match(Dot);
						setState(1652);
						identifierName();
						setState(1654);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,217,_ctx) ) {
						case 1:
							{
							setState(1653);
							typeGeneric();
							}
							break;
						}
						}
						break;
					case 15:
						{
						_localctx = new ArgumentsExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1656);
						if (!(precpred(_ctx, 37))) throw new FailedPredicateException(this, "precpred(_ctx, 37)");
						setState(1657);
						arguments();
						}
						break;
					case 16:
						{
						_localctx = new PostIncrementExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1658);
						if (!(precpred(_ctx, 36))) throw new FailedPredicateException(this, "precpred(_ctx, 36)");
						setState(1659);
						if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
						setState(1660);
						match(PlusPlus);
						}
						break;
					case 17:
						{
						_localctx = new PostDecreaseExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1661);
						if (!(precpred(_ctx, 35))) throw new FailedPredicateException(this, "precpred(_ctx, 35)");
						setState(1662);
						if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
						setState(1663);
						match(MinusMinus);
						}
						break;
					case 18:
						{
						_localctx = new TemplateStringExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1664);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(1665);
						templateStringLiteral();
						}
						break;
					case 19:
						{
						_localctx = new CastAsExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1666);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(1667);
						match(As);
						setState(1668);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(1673);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,219,_ctx);
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
		enterRule(_localctx, 280, RULE_expressionSequence);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1674);
			singleExpression(0);
			setState(1679);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,220,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1675);
					match(Comma);
					setState(1676);
					singleExpression(0);
					}
					} 
				}
				setState(1681);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,220,_ctx);
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

	public static class FunctionExpressionDeclarationContext extends ParserRuleContext {
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
		public FunctionExpressionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionExpressionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterFunctionExpressionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitFunctionExpressionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitFunctionExpressionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionExpressionDeclarationContext functionExpressionDeclaration() throws RecognitionException {
		FunctionExpressionDeclarationContext _localctx = new FunctionExpressionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 282, RULE_functionExpressionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1682);
			match(Function);
			setState(1684);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(1683);
				match(Multiply);
				}
			}

			setState(1687);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1686);
				match(Identifier);
				}
			}

			setState(1689);
			parameterBlock();
			setState(1691);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1690);
				colonSepTypeRef();
				}
			}

			setState(1693);
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
		enterRule(_localctx, 284, RULE_generatorBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1695);
			match(OpenBrace);
			setState(1696);
			generatorDefinition();
			setState(1701);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,224,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1697);
					match(Comma);
					setState(1698);
					generatorDefinition();
					}
					} 
				}
				setState(1703);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,224,_ctx);
			}
			setState(1705);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1704);
				match(Comma);
				}
			}

			setState(1707);
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
		enterRule(_localctx, 286, RULE_generatorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1709);
			match(Multiply);
			setState(1710);
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
		enterRule(_localctx, 288, RULE_iteratorBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1712);
			match(OpenBrace);
			setState(1713);
			iteratorDefinition();
			setState(1718);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,226,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1714);
					match(Comma);
					setState(1715);
					iteratorDefinition();
					}
					} 
				}
				setState(1720);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,226,_ctx);
			}
			setState(1722);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1721);
				match(Comma);
				}
			}

			setState(1724);
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
		enterRule(_localctx, 290, RULE_iteratorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1726);
			match(OpenBracket);
			setState(1727);
			singleExpression(0);
			setState(1728);
			match(CloseBracket);
			setState(1729);
			parameterBlock();
			setState(1730);
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

	public static class ArrowFunctionDeclarationContext extends ParserRuleContext {
		public ArrowFunctionParametersContext arrowFunctionParameters() {
			return getRuleContext(ArrowFunctionParametersContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(TypeScriptParser.ARROW, 0); }
		public ArrowFunctionBodyContext arrowFunctionBody() {
			return getRuleContext(ArrowFunctionBodyContext.class,0);
		}
		public TerminalNode Async() { return getToken(TypeScriptParser.Async, 0); }
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public ArrowFunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrowFunctionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrowFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrowFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrowFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrowFunctionDeclarationContext arrowFunctionDeclaration() throws RecognitionException {
		ArrowFunctionDeclarationContext _localctx = new ArrowFunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 292, RULE_arrowFunctionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1733);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Async) {
				{
				setState(1732);
				match(Async);
				}
			}

			setState(1735);
			arrowFunctionParameters();
			setState(1737);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1736);
				colonSepTypeRef();
				}
			}

			setState(1739);
			match(ARROW);
			setState(1740);
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

	public static class ArrowFunctionParametersContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public ArrowFunctionParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrowFunctionParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterArrowFunctionParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitArrowFunctionParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitArrowFunctionParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrowFunctionParametersContext arrowFunctionParameters() throws RecognitionException {
		ArrowFunctionParametersContext _localctx = new ArrowFunctionParametersContext(_ctx, getState());
		enterRule(_localctx, 294, RULE_arrowFunctionParameters);
		try {
			setState(1744);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(1742);
				match(Identifier);
				}
				break;
			case OpenParen:
				enterOuterAlt(_localctx, 2);
				{
				setState(1743);
				parameterBlock();
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
		enterRule(_localctx, 296, RULE_arrowFunctionBody);
		try {
			setState(1748);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,231,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1746);
				singleExpression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1747);
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

	public static class AssignmentOperatorContext extends ParserRuleContext {
		public TerminalNode MultiplyAssign() { return getToken(TypeScriptParser.MultiplyAssign, 0); }
		public TerminalNode DivideAssign() { return getToken(TypeScriptParser.DivideAssign, 0); }
		public TerminalNode ModulusAssign() { return getToken(TypeScriptParser.ModulusAssign, 0); }
		public TerminalNode PlusAssign() { return getToken(TypeScriptParser.PlusAssign, 0); }
		public TerminalNode MinusAssign() { return getToken(TypeScriptParser.MinusAssign, 0); }
		public TerminalNode LeftShiftArithmeticAssign() { return getToken(TypeScriptParser.LeftShiftArithmeticAssign, 0); }
		public TerminalNode RightShiftArithmeticAssign() { return getToken(TypeScriptParser.RightShiftArithmeticAssign, 0); }
		public TerminalNode RightShiftLogicalAssign() { return getToken(TypeScriptParser.RightShiftLogicalAssign, 0); }
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
		enterRule(_localctx, 298, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1750);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MultiplyAssign) | (1L << DivideAssign) | (1L << ModulusAssign) | (1L << PlusAssign) | (1L << MinusAssign) | (1L << LeftShiftArithmeticAssign) | (1L << RightShiftArithmeticAssign) | (1L << RightShiftLogicalAssign) | (1L << BitAndAssign) | (1L << BitXorAssign) | (1L << BitOrAssign))) != 0)) ) {
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
			setState(1758);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NullLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1752);
				match(NullLiteral);
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1753);
				match(BooleanLiteral);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(1754);
				match(StringLiteral);
				}
				break;
			case BackTick:
				enterOuterAlt(_localctx, 4);
				{
				setState(1755);
				templateStringLiteral();
				}
				break;
			case RegularExpressionLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(1756);
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
				setState(1757);
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
			setState(1760);
			match(BackTick);
			setState(1764);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TemplateStringStartExpression || _la==TemplateStringAtom) {
				{
				{
				setState(1761);
				templateStringAtom();
				}
				}
				setState(1766);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1767);
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
			setState(1774);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TemplateStringAtom:
				enterOuterAlt(_localctx, 1);
				{
				setState(1769);
				match(TemplateStringAtom);
				}
				break;
			case TemplateStringStartExpression:
				enterOuterAlt(_localctx, 2);
				{
				setState(1770);
				match(TemplateStringStartExpression);
				setState(1771);
				singleExpression(0);
				setState(1772);
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
			setState(1784);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Minus:
			case DecimalLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1777);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Minus) {
					{
					setState(1776);
					match(Minus);
					}
				}

				setState(1779);
				match(DecimalLiteral);
				}
				break;
			case HexIntegerLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1780);
				match(HexIntegerLiteral);
				}
				break;
			case OctalIntegerLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(1781);
				match(OctalIntegerLiteral);
				}
				break;
			case OctalIntegerLiteral2:
				enterOuterAlt(_localctx, 4);
				{
				setState(1782);
				match(OctalIntegerLiteral2);
				}
				break;
			case BinaryIntegerLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(1783);
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
			setState(1786);
			_la = _input.LA(1);
			if ( !(((((_la - 114)) & ~0x3f) == 0 && ((1L << (_la - 114)) & ((1L << (TypeAlias - 114)) | (1L << (Require - 114)) | (1L << (Identifier - 114)))) != 0)) ) {
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
			setState(1790);
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
				setState(1788);
				reservedWord();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1789);
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
			setState(1794);
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
				setState(1792);
				keyword();
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1793);
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
			setState(1799);
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
				setState(1796);
				keywordAllowedInTypeReferences();
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1797);
				match(BooleanLiteral);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 3);
				{
				setState(1798);
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
			setState(1804);
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
				setState(1801);
				keywordAllowedInTypeReferences();
				}
				break;
			case ReadOnly:
				enterOuterAlt(_localctx, 2);
				{
				setState(1802);
				match(ReadOnly);
				}
				break;
			case Typeof:
				enterOuterAlt(_localctx, 3);
				{
				setState(1803);
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
			setState(1806);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (Unique - 65)) | (1L << (Case - 65)) | (1L << (Else - 65)) | (1L << (New - 65)) | (1L << (Var - 65)) | (1L << (Catch - 65)) | (1L << (Finally - 65)) | (1L << (Return - 65)) | (1L << (Void - 65)) | (1L << (Continue - 65)) | (1L << (For - 65)) | (1L << (Switch - 65)) | (1L << (While - 65)) | (1L << (Debugger - 65)) | (1L << (Function - 65)) | (1L << (This - 65)) | (1L << (With - 65)) | (1L << (Default - 65)) | (1L << (If - 65)) | (1L << (Throw - 65)) | (1L << (Delete - 65)) | (1L << (In - 65)) | (1L << (Try - 65)) | (1L << (As - 65)) | (1L << (From - 65)) | (1L << (Async - 65)) | (1L << (Class - 65)) | (1L << (Enum - 65)) | (1L << (Extends - 65)) | (1L << (Super - 65)) | (1L << (Const - 65)) | (1L << (Export - 65)) | (1L << (Import - 65)) | (1L << (Implements - 65)) | (1L << (Let - 65)) | (1L << (Private - 65)) | (1L << (Public - 65)) | (1L << (Interface - 65)) | (1L << (Package - 65)) | (1L << (Protected - 65)) | (1L << (Static - 65)) | (1L << (Yield - 65)) | (1L << (Any - 65)) | (1L << (Number - 65)) | (1L << (Boolean - 65)) | (1L << (String - 65)) | (1L << (Symbol - 65)) | (1L << (TypeAlias - 65)) | (1L << (Get - 65)) | (1L << (Set - 65)) | (1L << (Constructor - 65)) | (1L << (Namespace - 65)) | (1L << (Require - 65)) | (1L << (Module - 65)) | (1L << (Declare - 65)) | (1L << (Abstract - 65)) | (1L << (Is - 65)) | (1L << (Infer - 65)) | (1L << (Never - 65)) | (1L << (Unknown - 65)) | (1L << (Asserts - 65)))) != 0)) ) {
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
			setState(1808);
			match(Get);
			setState(1809);
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
			setState(1811);
			match(Set);
			setState(1812);
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
			setState(1818);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,241,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1814);
				match(SemiColon);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1815);
				match(EOF);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1816);
				if (!(this.lineTerminatorAhead())) throw new FailedPredicateException(this, "this.lineTerminatorAhead()");
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1817);
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
		case 55:
			return decoratorMemberExpression_sempred((DecoratorMemberExpressionContext)_localctx, predIndex);
		case 129:
			return iterationStatement_sempred((IterationStatementContext)_localctx, predIndex);
		case 130:
			return continueStatement_sempred((ContinueStatementContext)_localctx, predIndex);
		case 131:
			return breakStatement_sempred((BreakStatementContext)_localctx, predIndex);
		case 132:
			return returnStatement_sempred((ReturnStatementContext)_localctx, predIndex);
		case 133:
			return yieldStatement_sempred((YieldStatementContext)_localctx, predIndex);
		case 136:
			return throwStatement_sempred((ThrowStatementContext)_localctx, predIndex);
		case 138:
			return expressionStatement_sempred((ExpressionStatementContext)_localctx, predIndex);
		case 139:
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
	private boolean yieldStatement_sempred(YieldStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean throwStatement_sempred(ThrowStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean expressionStatement_sempred(ExpressionStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return this.notOpenBraceAndNotFunction();
		}
		return true;
	}
	private boolean singleExpression_sempred(SingleExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 25);
		case 10:
			return precpred(_ctx, 24);
		case 11:
			return precpred(_ctx, 23);
		case 12:
			return precpred(_ctx, 22);
		case 13:
			return precpred(_ctx, 21);
		case 14:
			return precpred(_ctx, 20);
		case 15:
			return precpred(_ctx, 19);
		case 16:
			return precpred(_ctx, 18);
		case 17:
			return precpred(_ctx, 17);
		case 18:
			return precpred(_ctx, 16);
		case 19:
			return precpred(_ctx, 15);
		case 20:
			return precpred(_ctx, 14);
		case 21:
			return precpred(_ctx, 40);
		case 22:
			return precpred(_ctx, 39);
		case 23:
			return precpred(_ctx, 37);
		case 24:
			return precpred(_ctx, 36);
		case 25:
			return this.notLineTerminator();
		case 26:
			return precpred(_ctx, 35);
		case 27:
			return this.notLineTerminator();
		case 28:
			return precpred(_ctx, 13);
		case 29:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean eos_sempred(EosContext _localctx, int predIndex) {
		switch (predIndex) {
		case 30:
			return this.lineTerminatorAhead();
		case 31:
			return this.closeBrace();
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u008c\u071f\4\2\t"+
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
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u0161\n\3\3\4\5\4\u0164\n\4\3\4\3"+
		"\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0170\n\5\3\6\3\6\5\6\u0174\n\6"+
		"\3\6\3\6\3\7\6\7\u0179\n\7\r\7\16\7\u017a\3\b\3\b\3\b\3\t\3\t\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u018a\n\n\3\13\5\13\u018d\n\13\3\13\3\13"+
		"\3\13\7\13\u0192\n\13\f\13\16\13\u0195\13\13\3\f\5\f\u0198\n\f\3\f\3\f"+
		"\3\f\7\f\u019d\n\f\f\f\16\f\u01a0\13\f\3\r\5\r\u01a3\n\r\3\r\3\r\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\17\7\17\u01ae\n\17\f\17\16\17\u01b1\13\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u01ba\n\17\f\17\16\17\u01bd\13"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u01c6\n\17\f\17\16\17\u01c9"+
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
		"\31\3\31\3\31\3\32\3\32\3\32\7\32\u023d\n\32\f\32\16\32\u0240\13\32\3"+
		"\33\5\33\u0243\n\33\3\33\3\33\3\34\3\34\5\34\u0249\n\34\3\34\3\34\3\35"+
		"\3\35\5\35\u024f\n\35\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3 \3"+
		" \3 \5 \u025e\n \3!\3!\3!\5!\u0263\n!\3!\3!\7!\u0267\n!\f!\16!\u026a\13"+
		"!\3!\5!\u026d\n!\5!\u026f\n!\3\"\5\"\u0272\n\"\3\"\3\"\5\"\u0276\n\"\3"+
		"\"\3\"\3\"\5\"\u027b\n\"\3\"\5\"\u027e\n\"\3#\3#\3#\3$\5$\u0284\n$\3$"+
		"\3$\5$\u0288\n$\3$\3$\3$\3%\3%\3&\3&\3&\7&\u0292\n&\f&\16&\u0295\13&\3"+
		"\'\3\'\3\'\3(\5(\u029b\n(\3(\3(\5(\u029f\n(\3(\5(\u02a2\n(\3(\3(\5(\u02a6"+
		"\n(\3)\3)\5)\u02aa\n)\3)\3)\5)\u02ae\n)\3*\3*\5*\u02b2\n*\3*\3*\3+\3+"+
		"\3+\7+\u02b9\n+\f+\16+\u02bc\13+\3,\3,\5,\u02c0\n,\3,\3,\5,\u02c4\n,\3"+
		"-\3-\3-\3.\3.\3/\5/\u02cc\n/\3/\5/\u02cf\n/\3/\3/\3/\5/\u02d4\n/\3/\3"+
		"/\3/\3/\5/\u02da\n/\3/\3/\3/\5/\u02df\n/\3/\3/\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\60\5\60\u02ea\n\60\3\61\3\61\5\61\u02ee\n\61\3\61\3\61\3\62\3"+
		"\62\3\62\5\62\u02f5\n\62\3\62\3\62\3\62\5\62\u02fa\n\62\3\63\3\63\3\63"+
		"\3\63\3\64\3\64\3\65\3\65\3\65\3\65\3\66\3\66\3\66\7\66\u0309\n\66\f\66"+
		"\16\66\u030c\13\66\3\67\6\67\u030f\n\67\r\67\16\67\u0310\38\38\38\58\u0316"+
		"\n8\39\39\39\39\39\39\59\u031e\n9\39\39\39\79\u0323\n9\f9\169\u0326\13"+
		"9\3:\3:\3:\3;\3;\3;\5;\u032e\n;\3;\5;\u0331\n;\3;\3;\5;\u0335\n;\3;\3"+
		";\5;\u0339\n;\3<\3<\3<\3=\3=\3=\7=\u0341\n=\f=\16=\u0344\13=\3>\3>\5>"+
		"\u0348\n>\3?\3?\3?\3?\5?\u034e\n?\3?\7?\u0351\n?\f?\16?\u0354\13?\3@\3"+
		"@\3@\3@\3@\5@\u035b\n@\3A\5A\u035e\nA\3A\3A\3A\3A\5A\u0364\nA\3A\3A\5"+
		"A\u0368\nA\3B\3B\5B\u036c\nB\3C\3C\3C\7C\u0371\nC\fC\16C\u0374\13C\3D"+
		"\3D\3D\5D\u0379\nD\3E\3E\5E\u037d\nE\3E\3E\3E\5E\u0382\nE\3E\5E\u0385"+
		"\nE\3F\5F\u0388\nF\3F\3F\3F\5F\u038d\nF\3F\3F\3F\5F\u0392\nF\3G\5G\u0395"+
		"\nG\3G\5G\u0398\nG\3H\3H\3H\3I\3I\3I\3J\3J\5J\u03a2\nJ\3J\5J\u03a5\nJ"+
		"\3J\3J\3K\3K\3K\5K\u03ac\nK\3K\7K\u03af\nK\fK\16K\u03b2\13K\3L\3L\3L\5"+
		"L\u03b7\nL\3L\5L\u03ba\nL\3M\5M\u03bd\nM\3M\3M\3M\5M\u03c2\nM\3N\3N\5"+
		"N\u03c6\nN\3O\3O\3O\3O\5O\u03cc\nO\3O\3O\3P\3P\3P\3P\5P\u03d4\nP\3Q\5"+
		"Q\u03d7\nQ\3Q\5Q\u03da\nQ\3Q\5Q\u03dd\nQ\3Q\5Q\u03e0\nQ\3R\3R\5R\u03e4"+
		"\nR\3R\5R\u03e7\nR\3R\5R\u03ea\nR\3R\3R\5R\u03ee\nR\5R\u03f0\nR\3S\3S"+
		"\3S\3T\5T\u03f6\nT\3T\3T\3T\3T\5T\u03fc\nT\5T\u03fe\nT\3U\3U\5U\u0402"+
		"\nU\3U\3U\3V\3V\5V\u0408\nV\3W\3W\3W\3W\7W\u040e\nW\fW\16W\u0411\13W\3"+
		"W\3W\5W\u0415\nW\5W\u0417\nW\3X\3X\3X\5X\u041c\nX\3Y\3Y\5Y\u0420\nY\3"+
		"Z\5Z\u0423\nZ\3Z\5Z\u0426\nZ\3Z\3Z\5Z\u042a\nZ\3[\5[\u042d\n[\3[\5[\u0430"+
		"\n[\3[\3[\3[\5[\u0435\n[\3[\5[\u0438\n[\3[\5[\u043b\n[\3\\\3\\\3]\3]\5"+
		"]\u0441\n]\3^\3^\5^\u0445\n^\3_\3_\5_\u0449\n_\3_\3_\3`\3`\6`\u044f\n"+
		"`\r`\16`\u0450\3`\7`\u0454\n`\f`\16`\u0457\13`\3`\5`\u045a\n`\3a\5a\u045d"+
		"\na\3a\3a\3b\3b\5b\u0463\nb\3c\3c\3c\3c\7c\u0469\nc\fc\16c\u046c\13c\3"+
		"c\5c\u046f\nc\5c\u0471\nc\3c\3c\3d\3d\3d\3d\5d\u0479\nd\3d\3d\5d\u047d"+
		"\nd\3d\3d\3d\3d\5d\u0483\nd\3e\3e\3e\3e\3e\3e\3e\3e\5e\u048d\ne\3e\3e"+
		"\5e\u0491\ne\3f\3f\3f\3f\5f\u0497\nf\3f\5f\u049a\nf\3g\3g\3g\3g\5g\u04a0"+
		"\ng\3g\5g\u04a3\ng\3g\3g\5g\u04a7\ng\3h\5h\u04aa\nh\3h\3h\3h\3h\3i\3i"+
		"\3i\5i\u04b3\ni\5i\u04b5\ni\3i\3i\3j\3j\3j\7j\u04bc\nj\fj\16j\u04bf\13"+
		"j\3k\5k\u04c2\nk\3k\3k\5k\u04c6\nk\3l\3l\3l\3l\5l\u04cc\nl\3l\3l\3m\5"+
		"m\u04d1\nm\3m\3m\3m\3m\3m\5m\u04d8\nm\3m\3m\3m\3n\3n\3n\5n\u04e0\nn\3"+
		"n\3n\3n\3n\7n\u04e6\nn\fn\16n\u04e9\13n\3n\5n\u04ec\nn\3n\3n\3o\3o\3o"+
		"\5o\u04f3\no\3p\3p\3p\3p\3p\3p\3p\5p\u04fc\np\3q\3q\3q\3r\3r\3r\3r\3r"+
		"\3r\3r\3r\3r\3r\3r\3r\3r\3r\3r\3r\5r\u0511\nr\3r\3r\5r\u0515\nr\3r\3r"+
		"\3r\5r\u051a\nr\3s\3s\3s\5s\u051f\ns\3s\3s\5s\u0523\ns\3s\3s\5s\u0527"+
		"\ns\3t\5t\u052a\nt\3t\5t\u052d\nt\3t\3t\3t\5t\u0532\nt\3t\5t\u0535\nt"+
		"\3u\3u\3v\3v\5v\u053b\nv\3v\5v\u053e\nv\3w\3w\3w\7w\u0543\nw\fw\16w\u0546"+
		"\13w\3x\3x\5x\u054a\nx\3x\3x\5x\u054e\nx\3x\5x\u0551\nx\3y\3y\3y\3y\3"+
		"y\3y\3z\3z\5z\u055b\nz\3z\3z\5z\u055f\nz\5z\u0561\nz\3z\3z\3{\6{\u0566"+
		"\n{\r{\16{\u0567\3|\3|\3|\3|\5|\u056e\n|\3}\3}\3}\5}\u0573\n}\3~\3~\3"+
		"~\3~\5~\u0579\n~\3~\5~\u057c\n~\3\177\3\177\3\177\3\177\3\177\3\177\3"+
		"\u0080\3\u0080\3\u0080\3\u0081\3\u0081\3\u0082\3\u0082\3\u0082\3\u0082"+
		"\3\u0082\3\u0082\3\u0082\5\u0082\u0590\n\u0082\3\u0083\3\u0083\3\u0083"+
		"\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083"+
		"\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\5\u0083\u05a3\n\u0083\3\u0083"+
		"\3\u0083\5\u0083\u05a7\n\u0083\3\u0083\3\u0083\5\u0083\u05ab\n\u0083\3"+
		"\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\5\u0083"+
		"\u05b5\n\u0083\3\u0083\3\u0083\5\u0083\u05b9\n\u0083\3\u0083\3\u0083\3"+
		"\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\5\u0083\u05c4\n"+
		"\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083"+
		"\3\u0083\3\u0083\3\u0083\5\u0083\u05d1\n\u0083\3\u0083\3\u0083\3\u0083"+
		"\3\u0083\5\u0083\u05d7\n\u0083\3\u0084\3\u0084\3\u0084\5\u0084\u05dc\n"+
		"\u0084\3\u0084\3\u0084\3\u0085\3\u0085\3\u0085\5\u0085\u05e3\n\u0085\3"+
		"\u0085\3\u0085\3\u0086\3\u0086\3\u0086\5\u0086\u05ea\n\u0086\3\u0086\3"+
		"\u0086\3\u0087\3\u0087\3\u0087\5\u0087\u05f1\n\u0087\3\u0087\3\u0087\3"+
		"\u0088\3\u0088\3\u0088\3\u0088\3\u0088\3\u0088\3\u0089\3\u0089\3\u0089"+
		"\3\u0089\3\u008a\3\u008a\3\u008a\3\u008a\3\u008a\3\u008b\3\u008b\3\u008b"+
		"\3\u008c\3\u008c\3\u008c\5\u008c\u060a\n\u008c\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\5\u008d\u0611\n\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\5\u008d\u0617\n\u008d\3\u008d\5\u008d\u061a\n\u008d\3\u008d\3\u008d\3"+
		"\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\5\u008d\u063d\n\u008d\5\u008d\u063f\n"+
		"\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\5\u008d\u064e\n\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\5\u008d\u0679\n\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d\3\u008d"+
		"\3\u008d\3\u008d\7\u008d\u0688\n\u008d\f\u008d\16\u008d\u068b\13\u008d"+
		"\3\u008e\3\u008e\3\u008e\7\u008e\u0690\n\u008e\f\u008e\16\u008e\u0693"+
		"\13\u008e\3\u008f\3\u008f\5\u008f\u0697\n\u008f\3\u008f\5\u008f\u069a"+
		"\n\u008f\3\u008f\3\u008f\5\u008f\u069e\n\u008f\3\u008f\3\u008f\3\u0090"+
		"\3\u0090\3\u0090\3\u0090\7\u0090\u06a6\n\u0090\f\u0090\16\u0090\u06a9"+
		"\13\u0090\3\u0090\5\u0090\u06ac\n\u0090\3\u0090\3\u0090\3\u0091\3\u0091"+
		"\3\u0091\3\u0092\3\u0092\3\u0092\3\u0092\7\u0092\u06b7\n\u0092\f\u0092"+
		"\16\u0092\u06ba\13\u0092\3\u0092\5\u0092\u06bd\n\u0092\3\u0092\3\u0092"+
		"\3\u0093\3\u0093\3\u0093\3\u0093\3\u0093\3\u0093\3\u0094\5\u0094\u06c8"+
		"\n\u0094\3\u0094\3\u0094\5\u0094\u06cc\n\u0094\3\u0094\3\u0094\3\u0094"+
		"\3\u0095\3\u0095\5\u0095\u06d3\n\u0095\3\u0096\3\u0096\5\u0096\u06d7\n"+
		"\u0096\3\u0097\3\u0097\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098"+
		"\5\u0098\u06e1\n\u0098\3\u0099\3\u0099\7\u0099\u06e5\n\u0099\f\u0099\16"+
		"\u0099\u06e8\13\u0099\3\u0099\3\u0099\3\u009a\3\u009a\3\u009a\3\u009a"+
		"\3\u009a\5\u009a\u06f1\n\u009a\3\u009b\5\u009b\u06f4\n\u009b\3\u009b\3"+
		"\u009b\3\u009b\3\u009b\3\u009b\5\u009b\u06fb\n\u009b\3\u009c\3\u009c\3"+
		"\u009d\3\u009d\5\u009d\u0701\n\u009d\3\u009e\3\u009e\5\u009e\u0705\n\u009e"+
		"\3\u009f\3\u009f\3\u009f\5\u009f\u070a\n\u009f\3\u00a0\3\u00a0\3\u00a0"+
		"\5\u00a0\u070f\n\u00a0\3\u00a1\3\u00a1\3\u00a2\3\u00a2\3\u00a2\3\u00a3"+
		"\3\u00a3\3\u00a3\3\u00a4\3\u00a4\3\u00a4\3\u00a4\5\u00a4\u071d\n\u00a4"+
		"\3\u00a4\2\4p\u0118\u00a5\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&"+
		"(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084"+
		"\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c"+
		"\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4"+
		"\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc"+
		"\u00ce\u00d0\u00d2\u00d4\u00d6\u00d8\u00da\u00dc\u00de\u00e0\u00e2\u00e4"+
		"\u00e6\u00e8\u00ea\u00ec\u00ee\u00f0\u00f2\u00f4\u00f6\u00f8\u00fa\u00fc"+
		"\u00fe\u0100\u0102\u0104\u0106\u0108\u010a\u010c\u010e\u0110\u0112\u0114"+
		"\u0116\u0118\u011a\u011c\u011e\u0120\u0122\u0124\u0126\u0128\u012a\u012c"+
		"\u012e\u0130\u0132\u0134\u0136\u0138\u013a\u013c\u013e\u0140\u0142\u0144"+
		"\u0146\2\21\4\2CD]]\4\2pprr\3\2\u0083\u0084\3\2\r\16\4\2hill\5\2HHccg"+
		"g\3\2\32\34\3\2\26\27\3\2\36!\3\2\"%\3\2&(\3\2)*\3\2+\65\5\2ttyy\u0083"+
		"\u0083\7\2\678?ACCE\\^\u0081\2\u07d8\2\u0149\3\2\2\2\4\u0160\3\2\2\2\6"+
		"\u0163\3\2\2\2\b\u016f\3\2\2\2\n\u0171\3\2\2\2\f\u0178\3\2\2\2\16\u017c"+
		"\3\2\2\2\20\u017f\3\2\2\2\22\u0181\3\2\2\2\24\u018c\3\2\2\2\26\u0197\3"+
		"\2\2\2\30\u01a2\3\2\2\2\32\u01a6\3\2\2\2\34\u01ca\3\2\2\2\36\u01d7\3\2"+
		"\2\2 \u01dc\3\2\2\2\"\u01e2\3\2\2\2$\u01fd\3\2\2\2&\u020f\3\2\2\2(\u021c"+
		"\3\2\2\2*\u0224\3\2\2\2,\u0226\3\2\2\2.\u022a\3\2\2\2\60\u0232\3\2\2\2"+
		"\62\u0239\3\2\2\2\64\u0242\3\2\2\2\66\u0246\3\2\2\28\u024c\3\2\2\2:\u0252"+
		"\3\2\2\2<\u0254\3\2\2\2>\u0257\3\2\2\2@\u026e\3\2\2\2B\u0271\3\2\2\2D"+
		"\u027f\3\2\2\2F\u0283\3\2\2\2H\u028c\3\2\2\2J\u028e\3\2\2\2L\u0296\3\2"+
		"\2\2N\u029a\3\2\2\2P\u02a7\3\2\2\2R\u02af\3\2\2\2T\u02b5\3\2\2\2V\u02bd"+
		"\3\2\2\2X\u02c5\3\2\2\2Z\u02c8\3\2\2\2\\\u02cb\3\2\2\2^\u02e9\3\2\2\2"+
		"`\u02eb\3\2\2\2b\u02f1\3\2\2\2d\u02fb\3\2\2\2f\u02ff\3\2\2\2h\u0301\3"+
		"\2\2\2j\u0305\3\2\2\2l\u030e\3\2\2\2n\u0312\3\2\2\2p\u031d\3\2\2\2r\u0327"+
		"\3\2\2\2t\u032a\3\2\2\2v\u033a\3\2\2\2x\u033d\3\2\2\2z\u0345\3\2\2\2|"+
		"\u0349\3\2\2\2~\u035a\3\2\2\2\u0080\u035d\3\2\2\2\u0082\u0369\3\2\2\2"+
		"\u0084\u036d\3\2\2\2\u0086\u0375\3\2\2\2\u0088\u037a\3\2\2\2\u008a\u0387"+
		"\3\2\2\2\u008c\u0394\3\2\2\2\u008e\u0399\3\2\2\2\u0090\u039c\3\2\2\2\u0092"+
		"\u039f\3\2\2\2\u0094\u03a8\3\2\2\2\u0096\u03b9\3\2\2\2\u0098\u03bc\3\2"+
		"\2\2\u009a\u03c5\3\2\2\2\u009c\u03c7\3\2\2\2\u009e\u03cf\3\2\2\2\u00a0"+
		"\u03d6\3\2\2\2\u00a2\u03e1\3\2\2\2\u00a4\u03f1\3\2\2\2\u00a6\u03f5\3\2"+
		"\2\2\u00a8\u03ff\3\2\2\2\u00aa\u0405\3\2\2\2\u00ac\u0416\3\2\2\2\u00ae"+
		"\u0418\3\2\2\2\u00b0\u041f\3\2\2\2\u00b2\u0422\3\2\2\2\u00b4\u042c\3\2"+
		"\2\2\u00b6\u043c\3\2\2\2\u00b8\u0440\3\2\2\2\u00ba\u0444\3\2\2\2\u00bc"+
		"\u0446\3\2\2\2\u00be\u044c\3\2\2\2\u00c0\u045c\3\2\2\2\u00c2\u0462\3\2"+
		"\2\2\u00c4\u0464\3\2\2\2\u00c6\u0482\3\2\2\2\u00c8\u0490\3\2\2\2\u00ca"+
		"\u0492\3\2\2\2\u00cc\u049b\3\2\2\2\u00ce\u04a9\3\2\2\2\u00d0\u04af\3\2"+
		"\2\2\u00d2\u04b8\3\2\2\2\u00d4\u04c1\3\2\2\2\u00d6\u04c7\3\2\2\2\u00d8"+
		"\u04d0\3\2\2\2\u00da\u04df\3\2\2\2\u00dc\u04ef\3\2\2\2\u00de\u04f4\3\2"+
		"\2\2\u00e0\u04fd\3\2\2\2\u00e2\u0519\3\2\2\2\u00e4\u0522\3\2\2\2\u00e6"+
		"\u0529\3\2\2\2\u00e8\u0536\3\2\2\2\u00ea\u0538\3\2\2\2\u00ec\u053f\3\2"+
		"\2\2\u00ee\u0547\3\2\2\2\u00f0\u0552\3\2\2\2\u00f2\u0558\3\2\2\2\u00f4"+
		"\u0565\3\2\2\2\u00f6\u0569\3\2\2\2\u00f8\u056f\3\2\2\2\u00fa\u0574\3\2"+
		"\2\2\u00fc\u057d\3\2\2\2\u00fe\u0583\3\2\2\2\u0100\u0586\3\2\2\2\u0102"+
		"\u0588\3\2\2\2\u0104\u05d6\3\2\2\2\u0106\u05d8\3\2\2\2\u0108\u05df\3\2"+
		"\2\2\u010a\u05e6\3\2\2\2\u010c\u05ed\3\2\2\2\u010e\u05f4\3\2\2\2\u0110"+
		"\u05fa\3\2\2\2\u0112\u05fe\3\2\2\2\u0114\u0603\3\2\2\2\u0116\u0606\3\2"+
		"\2\2\u0118\u063e\3\2\2\2\u011a\u068c\3\2\2\2\u011c\u0694\3\2\2\2\u011e"+
		"\u06a1\3\2\2\2\u0120\u06af\3\2\2\2\u0122\u06b2\3\2\2\2\u0124\u06c0\3\2"+
		"\2\2\u0126\u06c7\3\2\2\2\u0128\u06d2\3\2\2\2\u012a\u06d6\3\2\2\2\u012c"+
		"\u06d8\3\2\2\2\u012e\u06e0\3\2\2\2\u0130\u06e2\3\2\2\2\u0132\u06f0\3\2"+
		"\2\2\u0134\u06fa\3\2\2\2\u0136\u06fc\3\2\2\2\u0138\u0700\3\2\2\2\u013a"+
		"\u0704\3\2\2\2\u013c\u0709\3\2\2\2\u013e\u070e\3\2\2\2\u0140\u0710\3\2"+
		"\2\2\u0142\u0712\3\2\2\2\u0144\u0715\3\2\2\2\u0146\u071c\3\2\2\2\u0148"+
		"\u014a\5\f\7\2\u0149\u0148\3\2\2\2\u0149\u014a\3\2\2\2\u014a\u014b\3\2"+
		"\2\2\u014b\u014c\7\2\2\3\u014c\3\3\2\2\2\u014d\u0161\5\n\6\2\u014e\u0161"+
		"\5\u00d6l\2\u014f\u0161\5l\67\2\u0150\u0161\5\u00e0q\2\u0151\u0161\5\6"+
		"\4\2\u0152\u0161\5\u0126\u0094\2\u0153\u0161\5\u0102\u0082\2\u0154\u0161"+
		"\5\u0104\u0083\2\u0155\u0161\5\u0106\u0084\2\u0156\u0161\5\u0108\u0085"+
		"\2\u0157\u0161\5\u010a\u0086\2\u0158\u0161\5\u010c\u0087\2\u0159\u0161"+
		"\5\u010e\u0088\2\u015a\u0161\5\u0110\u0089\2\u015b\u0161\5\u00f0y\2\u015c"+
		"\u0161\5\u0112\u008a\2\u015d\u0161\5\u00fa~\2\u015e\u0161\5\u0114\u008b"+
		"\2\u015f\u0161\5\u0100\u0081\2\u0160\u014d\3\2\2\2\u0160\u014e\3\2\2\2"+
		"\u0160\u014f\3\2\2\2\u0160\u0150\3\2\2\2\u0160\u0151\3\2\2\2\u0160\u0152"+
		"\3\2\2\2\u0160\u0153\3\2\2\2\u0160\u0154\3\2\2\2\u0160\u0155\3\2\2\2\u0160"+
		"\u0156\3\2\2\2\u0160\u0157\3\2\2\2\u0160\u0158\3\2\2\2\u0160\u0159\3\2"+
		"\2\2\u0160\u015a\3\2\2\2\u0160\u015b\3\2\2\2\u0160\u015c\3\2\2\2\u0160"+
		"\u015d\3\2\2\2\u0160\u015e\3\2\2\2\u0160\u015f\3\2\2\2\u0161\5\3\2\2\2"+
		"\u0162\u0164\7{\2\2\u0163\u0162\3\2\2\2\u0163\u0164\3\2\2\2\u0164\u0165"+
		"\3\2\2\2\u0165\u0166\5\b\5\2\u0166\7\3\2\2\2\u0167\u0170\5d\63\2\u0168"+
		"\u0170\5h\65\2\u0169\u0170\5t;\2\u016a\u0170\5b\62\2\u016b\u0170\5\u0088"+
		"E\2\u016c\u0170\5\u008aF\2\u016d\u0170\5\u0080A\2\u016e\u0170\5\u00e6"+
		"t\2\u016f\u0167\3\2\2\2\u016f\u0168\3\2\2\2\u016f\u0169\3\2\2\2\u016f"+
		"\u016a\3\2\2\2\u016f\u016b\3\2\2\2\u016f\u016c\3\2\2\2\u016f\u016d\3\2"+
		"\2\2\u016f\u016e\3\2\2\2\u0170\t\3\2\2\2\u0171\u0173\7\13\2\2\u0172\u0174"+
		"\5\f\7\2\u0173\u0172\3\2\2\2\u0173\u0174\3\2\2\2\u0174\u0175\3\2\2\2\u0175"+
		"\u0176\7\f\2\2\u0176\13\3\2\2\2\u0177\u0179\5\4\3\2\u0178\u0177\3\2\2"+
		"\2\u0179\u017a\3\2\2\2\u017a\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017b\r"+
		"\3\2\2\2\u017c\u017d\7\21\2\2\u017d\u017e\5\20\t\2\u017e\17\3\2\2\2\u017f"+
		"\u0180\5\22\n\2\u0180\21\3\2\2\2\u0181\u0189\5\24\13\2\u0182\u0183\7a"+
		"\2\2\u0183\u0184\5\24\13\2\u0184\u0185\7\20\2\2\u0185\u0186\5\22\n\2\u0186"+
		"\u0187\7\21\2\2\u0187\u0188\5\22\n\2\u0188\u018a\3\2\2\2\u0189\u0182\3"+
		"\2\2\2\u0189\u018a\3\2\2\2\u018a\23\3\2\2\2\u018b\u018d\7(\2\2\u018c\u018b"+
		"\3\2\2\2\u018c\u018d\3\2\2\2\u018d\u018e\3\2\2\2\u018e\u0193\5\26\f\2"+
		"\u018f\u0190\7(\2\2\u0190\u0192\5\26\f\2\u0191\u018f\3\2\2\2\u0192\u0195"+
		"\3\2\2\2\u0193\u0191\3\2\2\2\u0193\u0194\3\2\2\2\u0194\25\3\2\2\2\u0195"+
		"\u0193\3\2\2\2\u0196\u0198\7&\2\2\u0197\u0196\3\2\2\2\u0197\u0198\3\2"+
		"\2\2\u0198\u0199\3\2\2\2\u0199\u019e\5\30\r\2\u019a\u019b\7&\2\2\u019b"+
		"\u019d\5\30\r\2\u019c\u019a\3\2\2\2\u019d\u01a0\3\2\2\2\u019e\u019c\3"+
		"\2\2\2\u019e\u019f\3\2\2\2\u019f\27\3\2\2\2\u01a0\u019e\3\2\2\2\u01a1"+
		"\u01a3\5\32\16\2\u01a2\u01a1\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3\u01a4\3"+
		"\2\2\2\u01a4\u01a5\5\34\17\2\u01a5\31\3\2\2\2\u01a6\u01a7\t\2\2\2\u01a7"+
		"\33\3\2\2\2\u01a8\u01a9\7\20\2\2\u01a9\u01aa\7\7\2\2\u01aa\u01af\7\b\2"+
		"\2\u01ab\u01ac\7\7\2\2\u01ac\u01ae\7\b\2\2\u01ad\u01ab\3\2\2\2\u01ae\u01b1"+
		"\3\2\2\2\u01af\u01ad\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0\u01cb\3\2\2\2\u01b1"+
		"\u01af\3\2\2\2\u01b2\u01b3\7\t\2\2\u01b3\u01b4\7\20\2\2\u01b4\u01b5\7"+
		"\n\2\2\u01b5\u01b6\7\7\2\2\u01b6\u01bb\7\b\2\2\u01b7\u01b8\7\7\2\2\u01b8"+
		"\u01ba\7\b\2\2\u01b9\u01b7\3\2\2\2\u01ba\u01bd\3\2\2\2\u01bb\u01b9\3\2"+
		"\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01cb\3\2\2\2\u01bd\u01bb\3\2\2\2\u01be"+
		"\u01c7\5\36\20\2\u01bf\u01c0\7\7\2\2\u01c0\u01c6\7\b\2\2\u01c1\u01c2\7"+
		"\7\2\2\u01c2\u01c3\5\20\t\2\u01c3\u01c4\7\b\2\2\u01c4\u01c6\3\2\2\2\u01c5"+
		"\u01bf\3\2\2\2\u01c5\u01c1\3\2\2\2\u01c6\u01c9\3\2\2\2\u01c7\u01c5\3\2"+
		"\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01cb\3\2\2\2\u01c9\u01c7\3\2\2\2\u01ca"+
		"\u01a8\3\2\2\2\u01ca\u01b2\3\2\2\2\u01ca\u01be\3\2\2\2\u01cb\35\3\2\2"+
		"\2\u01cc\u01d8\5 \21\2\u01cd\u01d8\5\"\22\2\u01ce\u01d8\5$\23\2\u01cf"+
		"\u01d8\5<\37\2\u01d0\u01d8\5> \2\u01d1\u01d8\5L\'\2\u01d2\u01d8\5*\26"+
		"\2\u01d3\u01d4\7\t\2\2\u01d4\u01d5\5\20\t\2\u01d5\u01d6\7\n\2\2\u01d6"+
		"\u01d8\3\2\2\2\u01d7\u01cc\3\2\2\2\u01d7\u01cd\3\2\2\2\u01d7\u01ce\3\2"+
		"\2\2\u01d7\u01cf\3\2\2\2\u01d7\u01d0\3\2\2\2\u01d7\u01d1\3\2\2\2\u01d7"+
		"\u01d2\3\2\2\2\u01d7\u01d3\3\2\2\2\u01d8\37\3\2\2\2\u01d9\u01dd\79\2\2"+
		"\u01da\u01dd\7\u0084\2\2\u01db\u01dd\5\u0134\u009b\2\u01dc\u01d9\3\2\2"+
		"\2\u01dc\u01da\3\2\2\2\u01dc\u01db\3\2\2\2\u01dd!\3\2\2\2\u01de\u01e0"+
		"\7|\2\2\u01df\u01de\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1"+
		"\u01e3\7G\2\2\u01e2\u01df\3\2\2\2\u01e2\u01e3\3\2\2\2\u01e3\u01f2\3\2"+
		"\2\2\u01e4\u01e5\7\36\2\2\u01e5\u01ea\5(\25\2\u01e6\u01e7\7\16\2\2\u01e7"+
		"\u01e9\5(\25\2\u01e8\u01e6\3\2\2\2\u01e9\u01ec\3\2\2\2\u01ea\u01e8\3\2"+
		"\2\2\u01ea\u01eb\3\2\2\2\u01eb\u01ee\3\2\2\2\u01ec\u01ea\3\2\2\2\u01ed"+
		"\u01ef\7\16\2\2\u01ee\u01ed\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef\u01f0\3"+
		"\2\2\2\u01f0\u01f1\7\37\2\2\u01f1\u01f3\3\2\2\2\u01f2\u01e4\3\2\2\2\u01f2"+
		"\u01f3\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4\u01f5\7\t\2\2\u01f5\u01f6\5@"+
		"!\2\u01f6\u01f7\7\n\2\2\u01f7\u01f8\7\66\2\2\u01f8\u01fb\3\2\2\2\u01f9"+
		"\u01fc\5F$\2\u01fa\u01fc\5\24\13\2\u01fb\u01f9\3\2\2\2\u01fb\u01fa\3\2"+
		"\2\2\u01fc#\3\2\2\2\u01fd\u020c\7\7\2\2\u01fe\u020d\7\b\2\2\u01ff\u0204"+
		"\5&\24\2\u0200\u0201\7\16\2\2\u0201\u0203\5&\24\2\u0202\u0200\3\2\2\2"+
		"\u0203\u0206\3\2\2\2\u0204\u0202\3\2\2\2\u0204\u0205\3\2\2\2\u0205\u0208"+
		"\3\2\2\2\u0206\u0204\3\2\2\2\u0207\u0209\7\16\2\2\u0208\u0207\3\2\2\2"+
		"\u0208\u0209\3\2\2\2\u0209\u020a\3\2\2\2\u020a\u020b\7\b\2\2\u020b\u020d"+
		"\3\2\2\2\u020c\u01fe\3\2\2\2\u020c\u01ff\3\2\2\2\u020d%\3\2\2\2\u020e"+
		"\u0210\7\22\2\2\u020f\u020e\3\2\2\2\u020f\u0210\3\2\2\2\u0210\u0212\3"+
		"\2\2\2\u0211\u0213\7~\2\2\u0212\u0211\3\2\2\2\u0212\u0213\3\2\2\2\u0213"+
		"\u0216\3\2\2\2\u0214\u0215\7\u0083\2\2\u0215\u0217\7\21\2\2\u0216\u0214"+
		"\3\2\2\2\u0216\u0217\3\2\2\2\u0217\u0218\3\2\2\2\u0218\u021a\5\20\t\2"+
		"\u0219\u021b\7\20\2\2\u021a\u0219\3\2\2\2\u021a\u021b\3\2\2\2\u021b\'"+
		"\3\2\2\2\u021c\u021f\7\u0083\2\2\u021d\u021e\7a\2\2\u021e\u0220\5\20\t"+
		"\2\u021f\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220)\3\2\2\2\u0221\u0225"+
		"\5,\27\2\u0222\u0225\58\35\2\u0223\u0225\5:\36\2\u0224\u0221\3\2\2\2\u0224"+
		"\u0222\3\2\2\2\u0224\u0223\3\2\2\2\u0225+\3\2\2\2\u0226\u0228\5.\30\2"+
		"\u0227\u0229\5\60\31\2\u0228\u0227\3\2\2\2\u0228\u0229\3\2\2\2\u0229-"+
		"\3\2\2\2\u022a\u022f\5\u013c\u009f\2\u022b\u022c\7\23\2\2\u022c\u022e"+
		"\5\u013c\u009f\2\u022d\u022b\3\2\2\2\u022e\u0231\3\2\2\2\u022f\u022d\3"+
		"\2\2\2\u022f\u0230\3\2\2\2\u0230/\3\2\2\2\u0231\u022f\3\2\2\2\u0232\u0233"+
		"\7\36\2\2\u0233\u0235\5\62\32\2\u0234\u0236\7\16\2\2\u0235\u0234\3\2\2"+
		"\2\u0235\u0236\3\2\2\2\u0236\u0237\3\2\2\2\u0237\u0238\7\37\2\2\u0238"+
		"\61\3\2\2\2\u0239\u023e\5\64\33\2\u023a\u023b\7\16\2\2\u023b\u023d\5\64"+
		"\33\2\u023c\u023a\3\2\2\2\u023d\u0240\3\2\2\2\u023e\u023c\3\2\2\2\u023e"+
		"\u023f\3\2\2\2\u023f\63\3\2\2\2\u0240\u023e\3\2\2\2\u0241\u0243\7~\2\2"+
		"\u0242\u0241\3\2\2\2\u0242\u0243\3\2\2\2\u0243\u0244\3\2\2\2\u0244\u0245"+
		"\5\20\t\2\u0245\65\3\2\2\2\u0246\u0248\7\36\2\2\u0247\u0249\5\62\32\2"+
		"\u0248\u0247\3\2\2\2\u0248\u0249\3\2\2\2\u0249\u024a\3\2\2\2\u024a\u024b"+
		"\7\37\2\2\u024b\67\3\2\2\2\u024c\u024e\7\13\2\2\u024d\u024f\5z>\2\u024e"+
		"\u024d\3\2\2\2\u024e\u024f\3\2\2\2\u024f\u0250\3\2\2\2\u0250\u0251\7\f"+
		"\2\2\u02519\3\2\2\2\u0252\u0253\7S\2\2\u0253;\3\2\2\2\u0254\u0255\7B\2"+
		"\2\u0255\u0256\5J&\2\u0256=\3\2\2\2\u0257\u0258\7e\2\2\u0258\u0259\7\t"+
		"\2\2\u0259\u025a\7\u0084\2\2\u025a\u025d\7\n\2\2\u025b\u025c\7\23\2\2"+
		"\u025c\u025e\5,\27\2\u025d\u025b\3\2\2\2\u025d\u025e\3\2\2\2\u025e?\3"+
		"\2\2\2\u025f\u0260\7S\2\2\u0260\u0263\5\16\b\2\u0261\u0263\5B\"\2\u0262"+
		"\u025f\3\2\2\2\u0262\u0261\3\2\2\2\u0263\u0268\3\2\2\2\u0264\u0265\7\16"+
		"\2\2\u0265\u0267\5B\"\2\u0266\u0264\3\2\2\2\u0267\u026a\3\2\2\2\u0268"+
		"\u0266\3\2\2\2\u0268\u0269\3\2\2\2\u0269\u026c\3\2\2\2\u026a\u0268\3\2"+
		"\2\2\u026b\u026d\7\16\2\2\u026c\u026b\3\2\2\2\u026c\u026d\3\2\2\2\u026d"+
		"\u026f\3\2\2\2\u026e\u0262\3\2\2\2\u026e\u026f\3\2\2\2\u026fA\3\2\2\2"+
		"\u0270\u0272\7\22\2\2\u0271\u0270\3\2\2\2\u0271\u0272\3\2\2\2\u0272\u027a"+
		"\3\2\2\2\u0273\u0275\5H%\2\u0274\u0276\7\20\2\2\u0275\u0274\3\2\2\2\u0275"+
		"\u0276\3\2\2\2\u0276\u0277\3\2\2\2\u0277\u0278\5\16\b\2\u0278\u027b\3"+
		"\2\2\2\u0279\u027b\5\20\t\2\u027a\u0273\3\2\2\2\u027a\u0279\3\2\2\2\u027b"+
		"\u027d\3\2\2\2\u027c\u027e\5D#\2\u027d\u027c\3\2\2\2\u027d\u027e\3\2\2"+
		"\2\u027eC\3\2\2\2\u027f\u0280\7\17\2\2\u0280\u0281\7\u0083\2\2\u0281E"+
		"\3\2\2\2\u0282\u0284\7\u0081\2\2\u0283\u0282\3\2\2\2\u0283\u0284\3\2\2"+
		"\2\u0284\u0287\3\2\2\2\u0285\u0288\7S\2\2\u0286\u0288\5H%\2\u0287\u0285"+
		"\3\2\2\2\u0287\u0286\3\2\2\2\u0288\u0289\3\2\2\2\u0289\u028a\7}\2\2\u028a"+
		"\u028b\5\24\13\2\u028bG\3\2\2\2\u028c\u028d\5\u0138\u009d\2\u028dI\3\2"+
		"\2\2\u028e\u0293\5\u013c\u009f\2\u028f\u0290\7\23\2\2\u0290\u0292\5\u013c"+
		"\u009f\2\u0291\u028f\3\2\2\2\u0292\u0295\3\2\2\2\u0293\u0291\3\2\2\2\u0293"+
		"\u0294\3\2\2\2\u0294K\3\2\2\2\u0295\u0293\3\2\2\2\u0296\u0297\7~\2\2\u0297"+
		"\u0298\5\u013c\u009f\2\u0298M\3\2\2\2\u0299\u029b\7]\2\2\u029a\u0299\3"+
		"\2\2\2\u029a\u029b\3\2\2\2\u029b\u029c\3\2\2\2\u029c\u029e\5\u00c8e\2"+
		"\u029d\u029f\7\20\2\2\u029e\u029d\3\2\2\2\u029e\u029f\3\2\2\2\u029f\u02a1"+
		"\3\2\2\2\u02a0\u02a2\5\16\b\2\u02a1\u02a0\3\2\2\2\u02a1\u02a2\3\2\2\2"+
		"\u02a2\u02a5\3\2\2\2\u02a3\u02a4\7\66\2\2\u02a4\u02a6\5\20\t\2\u02a5\u02a3"+
		"\3\2\2\2\u02a5\u02a6\3\2\2\2\u02a6O\3\2\2\2\u02a7\u02a9\7G\2\2\u02a8\u02aa"+
		"\5R*\2\u02a9\u02a8\3\2\2\2\u02a9\u02aa\3\2\2\2\u02aa\u02ab\3\2\2\2\u02ab"+
		"\u02ad\5\u00a8U\2\u02ac\u02ae\5\16\b\2\u02ad\u02ac\3\2\2\2\u02ad\u02ae"+
		"\3\2\2\2\u02aeQ\3\2\2\2\u02af\u02b1\7\36\2\2\u02b0\u02b2\5T+\2\u02b1\u02b0"+
		"\3\2\2\2\u02b1\u02b2\3\2\2\2\u02b2\u02b3\3\2\2\2\u02b3\u02b4\7\37\2\2"+
		"\u02b4S\3\2\2\2\u02b5\u02ba\5V,\2\u02b6\u02b7\7\16\2\2\u02b7\u02b9\5V"+
		",\2\u02b8\u02b6\3\2\2\2\u02b9\u02bc\3\2\2\2\u02ba\u02b8\3\2\2\2\u02ba"+
		"\u02bb\3\2\2\2\u02bbU\3\2\2\2\u02bc\u02ba\3\2\2\2\u02bd\u02bf\5\u0138"+
		"\u009d\2\u02be\u02c0\5X-\2\u02bf\u02be\3\2\2\2\u02bf\u02c0\3\2\2\2\u02c0"+
		"\u02c3\3\2\2\2\u02c1\u02c2\7\17\2\2\u02c2\u02c4\5Z.\2\u02c3\u02c1\3\2"+
		"\2\2\u02c3\u02c4\3\2\2\2\u02c4W\3\2\2\2\u02c5\u02c6\7a\2\2\u02c6\u02c7"+
		"\5\20\t\2\u02c7Y\3\2\2\2\u02c8\u02c9\5\20\t\2\u02c9[\3\2\2\2\u02ca\u02cc"+
		"\7]\2\2\u02cb\u02ca\3\2\2\2\u02cb\u02cc\3\2\2\2\u02cc\u02d3\3\2\2\2\u02cd"+
		"\u02cf\7\26\2\2\u02ce\u02cd\3\2\2\2\u02ce\u02cf\3\2\2\2\u02cf\u02d0\3"+
		"\2\2\2\u02d0\u02d4\7]\2\2\u02d1\u02d2\7\27\2\2\u02d2\u02d4\7]\2\2\u02d3"+
		"\u02ce\3\2\2\2\u02d3\u02d1\3\2\2\2\u02d3\u02d4\3\2\2\2\u02d4\u02d5\3\2"+
		"\2\2\u02d5\u02d6\7\7\2\2\u02d6\u02d7\5^\60\2\u02d7\u02de\7\b\2\2\u02d8"+
		"\u02da\7\26\2\2\u02d9\u02d8\3\2\2\2\u02d9\u02da\3\2\2\2\u02da\u02db\3"+
		"\2\2\2\u02db\u02df\7\20\2\2\u02dc\u02dd\7\27\2\2\u02dd\u02df\7\20\2\2"+
		"\u02de\u02d9\3\2\2\2\u02de\u02dc\3\2\2\2\u02de\u02df\3\2\2\2\u02df\u02e0"+
		"\3\2\2\2\u02e0\u02e1\5\16\b\2\u02e1]\3\2\2\2\u02e2\u02e3\5\u0138\u009d"+
		"\2\u02e3\u02e4\7\21\2\2\u02e4\u02e5\t\3\2\2\u02e5\u02ea\3\2\2\2\u02e6"+
		"\u02e7\7\u0083\2\2\u02e7\u02e8\7Y\2\2\u02e8\u02ea\5\20\t\2\u02e9\u02e2"+
		"\3\2\2\2\u02e9\u02e6\3\2\2\2\u02ea_\3\2\2\2\u02eb\u02ed\5\u00c8e\2\u02ec"+
		"\u02ee\7\20\2\2\u02ed\u02ec\3\2\2\2\u02ed\u02ee\3\2\2\2\u02ee\u02ef\3"+
		"\2\2\2\u02ef\u02f0\5\u00a6T\2\u02f0a\3\2\2\2\u02f1\u02f2\7t\2\2\u02f2"+
		"\u02f4\5\u0138\u009d\2\u02f3\u02f5\5R*\2\u02f4\u02f3\3\2\2\2\u02f4\u02f5"+
		"\3\2\2\2\u02f5\u02f6\3\2\2\2\u02f6\u02f7\7\17\2\2\u02f7\u02f9\5\20\t\2"+
		"\u02f8\u02fa\7\r\2\2\u02f9\u02f8\3\2\2\2\u02f9\u02fa\3\2\2\2\u02fac\3"+
		"\2\2\2\u02fb\u02fc\7z\2\2\u02fc\u02fd\5f\64\2\u02fd\u02fe\5\n\6\2\u02fe"+
		"e\3\2\2\2\u02ff\u0300\t\4\2\2\u0300g\3\2\2\2\u0301\u0302\7x\2\2\u0302"+
		"\u0303\5j\66\2\u0303\u0304\5\n\6\2\u0304i\3\2\2\2\u0305\u030a\5\u013c"+
		"\u009f\2\u0306\u0307\7\23\2\2\u0307\u0309\5\u013c\u009f\2\u0308\u0306"+
		"\3\2\2\2\u0309\u030c\3\2\2\2\u030a\u0308\3\2\2\2\u030a\u030b\3\2\2\2\u030b"+
		"k\3\2\2\2\u030c\u030a\3\2\2\2\u030d\u030f\5n8\2\u030e\u030d\3\2\2\2\u030f"+
		"\u0310\3\2\2\2\u0310\u030e\3\2\2\2\u0310\u0311\3\2\2\2\u0311m\3\2\2\2"+
		"\u0312\u0315\7\u0082\2\2\u0313\u0316\5p9\2\u0314\u0316\5r:\2\u0315\u0313"+
		"\3\2\2\2\u0315\u0314\3\2\2\2\u0316o\3\2\2\2\u0317\u0318\b9\1\2\u0318\u031e"+
		"\7\u0083\2\2\u0319\u031a\7\t\2\2\u031a\u031b\5\u0118\u008d\2\u031b\u031c"+
		"\7\n\2\2\u031c\u031e\3\2\2\2\u031d\u0317\3\2\2\2\u031d\u0319\3\2\2\2\u031e"+
		"\u0324\3\2\2\2\u031f\u0320\f\4\2\2\u0320\u0321\7\23\2\2\u0321\u0323\5"+
		"\u0138\u009d\2\u0322\u031f\3\2\2\2\u0323\u0326\3\2\2\2\u0324\u0322\3\2"+
		"\2\2\u0324\u0325\3\2\2\2\u0325q\3\2\2\2\u0326\u0324\3\2\2\2\u0327\u0328"+
		"\5p9\2\u0328\u0329\5\u00d0i\2\u0329s\3\2\2\2\u032a\u032b\7j\2\2\u032b"+
		"\u032d\5\u0138\u009d\2\u032c\u032e\5R*\2\u032d\u032c\3\2\2\2\u032d\u032e"+
		"\3\2\2\2\u032e\u0330\3\2\2\2\u032f\u0331\5v<\2\u0330\u032f\3\2\2\2\u0330"+
		"\u0331\3\2\2\2\u0331\u0332\3\2\2\2\u0332\u0334\7\13\2\2\u0333\u0335\5"+
		"z>\2\u0334\u0333\3\2\2\2\u0334\u0335\3\2\2\2\u0335\u0336\3\2\2\2\u0336"+
		"\u0338\7\f\2\2\u0337\u0339\7\r\2\2\u0338\u0337\3\2\2\2\u0338\u0339\3\2"+
		"\2\2\u0339u\3\2\2\2\u033a\u033b\7a\2\2\u033b\u033c\5x=\2\u033cw\3\2\2"+
		"\2\u033d\u0342\5,\27\2\u033e\u033f\7\16\2\2\u033f\u0341\5,\27\2\u0340"+
		"\u033e\3\2\2\2\u0341\u0344\3\2\2\2\u0342\u0340\3\2\2\2\u0342\u0343\3\2"+
		"\2\2\u0343y\3\2\2\2\u0344\u0342\3\2\2\2\u0345\u0347\5|?\2\u0346\u0348"+
		"\t\5\2\2\u0347\u0346\3\2\2\2\u0347\u0348\3\2\2\2\u0348{\3\2\2\2\u0349"+
		"\u0352\5~@\2\u034a\u034e\b?\1\2\u034b\u034e\7\r\2\2\u034c\u034e\7\16\2"+
		"\2\u034d\u034a\3\2\2\2\u034d\u034b\3\2\2\2\u034d\u034c\3\2\2\2\u034e\u034f"+
		"\3\2\2\2\u034f\u0351\5~@\2\u0350\u034d\3\2\2\2\u0351\u0354\3\2\2\2\u0352"+
		"\u0350\3\2\2\2\u0352\u0353\3\2\2\2\u0353}\3\2\2\2\u0354\u0352\3\2\2\2"+
		"\u0355\u035b\5P)\2\u0356\u035b\5\u00a6T\2\u0357\u035b\5\\/\2\u0358\u035b"+
		"\5`\61\2\u0359\u035b\5N(\2\u035a\u0355\3\2\2\2\u035a\u0356\3\2\2\2\u035a"+
		"\u0357\3\2\2\2\u035a\u0358\3\2\2\2\u035a\u0359\3\2\2\2\u035b\177\3\2\2"+
		"\2\u035c\u035e\7c\2\2\u035d\u035c\3\2\2\2\u035d\u035e\3\2\2\2\u035e\u035f"+
		"\3\2\2\2\u035f\u0360\7`\2\2\u0360\u0361\7\u0083\2\2\u0361\u0363\7\13\2"+
		"\2\u0362\u0364\5\u0082B\2\u0363\u0362\3\2\2\2\u0363\u0364\3\2\2\2\u0364"+
		"\u0365\3\2\2\2\u0365\u0367\7\f\2\2\u0366\u0368\7\r\2\2\u0367\u0366\3\2"+
		"\2\2\u0367\u0368\3\2\2\2\u0368\u0081\3\2\2\2\u0369\u036b\5\u0084C\2\u036a"+
		"\u036c\7\16\2\2\u036b\u036a\3\2\2\2\u036b\u036c\3\2\2\2\u036c\u0083\3"+
		"\2\2\2\u036d\u0372\5\u0086D\2\u036e\u036f\7\16\2\2\u036f\u0371\5\u0086"+
		"D\2\u0370\u036e\3\2\2\2\u0371\u0374\3\2\2\2\u0372\u0370\3\2\2\2\u0372"+
		"\u0373\3\2\2\2\u0373\u0085\3\2\2\2\u0374\u0372\3\2\2\2\u0375\u0378\5\u00c8"+
		"e\2\u0376\u0377\7\17\2\2\u0377\u0379\5\u0118\u008d\2\u0378\u0376\3\2\2"+
		"\2\u0378\u0379\3\2\2\2\u0379\u0087\3\2\2\2\u037a\u037c\7R\2\2\u037b\u037d"+
		"\7\32\2\2\u037c\u037b\3\2\2\2\u037c\u037d\3\2\2\2\u037d\u037e\3\2\2\2"+
		"\u037e\u037f\5\u0138\u009d\2\u037f\u0381\5\u00a6T\2\u0380\u0382\5\n\6"+
		"\2\u0381\u0380\3\2\2\2\u0381\u0382\3\2\2\2\u0382\u0384\3\2\2\2\u0383\u0385"+
		"\7\r\2\2\u0384\u0383\3\2\2\2\u0384\u0385\3\2\2\2\u0385\u0089\3\2\2\2\u0386"+
		"\u0388\7|\2\2\u0387\u0386\3\2\2\2\u0387\u0388\3\2\2\2\u0388\u0389\3\2"+
		"\2\2\u0389\u038a\7_\2\2\u038a\u038c\5\u0136\u009c\2\u038b\u038d\5R*\2"+
		"\u038c\u038b\3\2\2\2\u038c\u038d\3\2\2\2\u038d\u038e\3\2\2\2\u038e\u038f"+
		"\5\u008cG\2\u038f\u0391\5\u0092J\2\u0390\u0392\7\r\2\2\u0391\u0390\3\2"+
		"\2\2\u0391\u0392\3\2\2\2\u0392\u008b\3\2\2\2\u0393\u0395\5\u008eH\2\u0394"+
		"\u0393\3\2\2\2\u0394\u0395\3\2\2\2\u0395\u0397\3\2\2\2\u0396\u0398\5\u0090"+
		"I\2\u0397\u0396\3\2\2\2\u0397\u0398\3\2\2\2\u0398\u008d\3\2\2\2\u0399"+
		"\u039a\7a\2\2\u039a\u039b\5,\27\2\u039b\u008f\3\2\2\2\u039c\u039d\7f\2"+
		"\2\u039d\u039e\5x=\2\u039e\u0091\3\2\2\2\u039f\u03a1\7\13\2\2\u03a0\u03a2"+
		"\5\u0094K\2\u03a1\u03a0\3\2\2\2\u03a1\u03a2\3\2\2\2\u03a2\u03a4\3\2\2"+
		"\2\u03a3\u03a5\7\r\2\2\u03a4\u03a3\3\2\2\2\u03a4\u03a5\3\2\2\2\u03a5\u03a6"+
		"\3\2\2\2\u03a6\u03a7\7\f\2\2\u03a7\u0093\3\2\2\2\u03a8\u03b0\5\u0096L"+
		"\2\u03a9\u03ac\bK\1\2\u03aa\u03ac\7\r\2\2\u03ab\u03a9\3\2\2\2\u03ab\u03aa"+
		"\3\2\2\2\u03ac\u03ad\3\2\2\2\u03ad\u03af\5\u0096L\2\u03ae\u03ab\3\2\2"+
		"\2\u03af\u03b2\3\2\2\2\u03b0\u03ae\3\2\2\2\u03b0\u03b1\3\2\2\2\u03b1\u0095"+
		"\3\2\2\2\u03b2\u03b0\3\2\2\2\u03b3\u03ba\5\u0098M\2\u03b4\u03ba\5\\/\2"+
		"\u03b5\u03b7\5l\67\2\u03b6\u03b5\3\2\2\2\u03b6\u03b7\3\2\2\2\u03b7\u03b8"+
		"\3\2\2\2\u03b8\u03ba\5\u009aN\2\u03b9\u03b3\3\2\2\2\u03b9\u03b4\3\2\2"+
		"\2\u03b9\u03b6\3\2\2\2\u03ba\u0097\3\2\2\2\u03bb\u03bd\5\u00b6\\\2\u03bc"+
		"\u03bb\3\2\2\2\u03bc\u03bd\3\2\2\2\u03bd\u03be\3\2\2\2\u03be\u03bf\7w"+
		"\2\2\u03bf\u03c1\5\u00a8U\2\u03c0\u03c2\5\n\6\2\u03c1\u03c0\3\2\2\2\u03c1"+
		"\u03c2\3\2\2\2\u03c2\u0099\3\2\2\2\u03c3\u03c6\5\u009cO\2\u03c4\u03c6"+
		"\5\u009eP\2\u03c5\u03c3\3\2\2\2\u03c5\u03c4\3\2\2\2\u03c6\u009b\3\2\2"+
		"\2\u03c7\u03cb\7|\2\2\u03c8\u03c9\7\u0083\2\2\u03c9\u03cc\5\u00a6T\2\u03ca"+
		"\u03cc\5\u00e6t\2\u03cb\u03c8\3\2\2\2\u03cb\u03ca\3\2\2\2\u03cc\u03cd"+
		"\3\2\2\2\u03cd\u03ce\5\u0146\u00a4\2\u03ce\u009d\3\2\2\2\u03cf\u03d3\5"+
		"\u00a0Q\2\u03d0\u03d4\5\u00caf\2\u03d1\u03d4\5\u00ccg\2\u03d2\u03d4\5"+
		"\u00a2R\2\u03d3\u03d0\3\2\2\2\u03d3\u03d1\3\2\2\2\u03d3\u03d2\3\2\2\2"+
		"\u03d4\u009f\3\2\2\2\u03d5\u03d7\7^\2\2\u03d6\u03d5\3\2\2\2\u03d6\u03d7"+
		"\3\2\2\2\u03d7\u03d9\3\2\2\2\u03d8\u03da\5\u00b6\\\2\u03d9\u03d8\3\2\2"+
		"\2\u03d9\u03da\3\2\2\2\u03da\u03dc\3\2\2\2\u03db\u03dd\7m\2\2\u03dc\u03db"+
		"\3\2\2\2\u03dc\u03dd\3\2\2\2\u03dd\u03df\3\2\2\2\u03de\u03e0\7]\2\2\u03df"+
		"\u03de\3\2\2\2\u03df\u03e0\3\2\2\2\u03e0\u00a1\3\2\2\2\u03e1\u03e3\5\u00c8"+
		"e\2\u03e2\u03e4\7\20\2\2\u03e3\u03e2\3\2\2\2\u03e3\u03e4\3\2\2\2\u03e4"+
		"\u03ef\3\2\2\2\u03e5\u03e7\5\16\b\2\u03e6\u03e5\3\2\2\2\u03e6\u03e7\3"+
		"\2\2\2\u03e7\u03e9\3\2\2\2\u03e8\u03ea\5\u00a4S\2\u03e9\u03e8\3\2\2\2"+
		"\u03e9\u03ea\3\2\2\2\u03ea\u03f0\3\2\2\2\u03eb\u03ed\5\u00a6T\2\u03ec"+
		"\u03ee\5\n\6\2\u03ed\u03ec\3\2\2\2\u03ed\u03ee\3\2\2\2\u03ee\u03f0\3\2"+
		"\2\2\u03ef\u03e6\3\2\2\2\u03ef\u03eb\3\2\2\2\u03f0\u00a3\3\2\2\2\u03f1"+
		"\u03f2\7\17\2\2\u03f2\u03f3\5\u0118\u008d\2\u03f3\u00a5\3\2\2\2\u03f4"+
		"\u03f6\5R*\2\u03f5\u03f4\3\2\2\2\u03f5\u03f6\3\2\2\2\u03f6\u03f7\3\2\2"+
		"\2\u03f7\u03fd\5\u00a8U\2\u03f8\u03fb\7\21\2\2\u03f9\u03fc\5F$\2\u03fa"+
		"\u03fc\5\20\t\2\u03fb\u03f9\3\2\2\2\u03fb\u03fa\3\2\2\2\u03fc\u03fe\3"+
		"\2\2\2\u03fd\u03f8\3\2\2\2\u03fd\u03fe\3\2\2\2\u03fe\u00a7\3\2\2\2\u03ff"+
		"\u0401\7\t\2\2\u0400\u0402\5\u00aaV\2\u0401\u0400\3\2\2\2\u0401\u0402"+
		"\3\2\2\2\u0402\u0403\3\2\2\2\u0403\u0404\7\n\2\2\u0404\u00a9\3\2\2\2\u0405"+
		"\u0407\5\u00acW\2\u0406\u0408\7\16\2\2\u0407\u0406\3\2\2\2\u0407\u0408"+
		"\3\2\2\2\u0408\u00ab\3\2\2\2\u0409\u0417\5\u00aeX\2\u040a\u040f\5\u00b0"+
		"Y\2\u040b\u040c\7\16\2\2\u040c\u040e\5\u00b0Y\2\u040d\u040b\3\2\2\2\u040e"+
		"\u0411\3\2\2\2\u040f\u040d\3\2\2\2\u040f\u0410\3\2\2\2\u0410\u0414\3\2"+
		"\2\2\u0411\u040f\3\2\2\2\u0412\u0413\7\16\2\2\u0413\u0415\5\u00aeX\2\u0414"+
		"\u0412\3\2\2\2\u0414\u0415\3\2\2\2\u0415\u0417\3\2\2\2\u0416\u0409\3\2"+
		"\2\2\u0416\u040a\3\2\2\2\u0417\u00ad\3\2\2\2\u0418\u0419\7\22\2\2\u0419"+
		"\u041b\5\u00b8]\2\u041a\u041c\5\16\b\2\u041b\u041a\3\2\2\2\u041b\u041c"+
		"\3\2\2\2\u041c\u00af\3\2\2\2\u041d\u0420\5\u00b2Z\2\u041e\u0420\5\u00b4"+
		"[\2\u041f\u041d\3\2\2\2\u041f\u041e\3\2\2\2\u0420\u00b1\3\2\2\2\u0421"+
		"\u0423\5l\67\2\u0422\u0421\3\2\2\2\u0422\u0423\3\2\2\2\u0423\u0425\3\2"+
		"\2\2\u0424\u0426\5\u00b6\\\2\u0425\u0424\3\2\2\2\u0425\u0426\3\2\2\2\u0426"+
		"\u0427\3\2\2\2\u0427\u0429\5\u00b8]\2\u0428\u042a\5\16\b\2\u0429\u0428"+
		"\3\2\2\2\u0429\u042a\3\2\2\2\u042a\u00b3\3\2\2\2\u042b\u042d\5l\67\2\u042c"+
		"\u042b\3\2\2\2\u042c\u042d\3\2\2\2\u042d\u042f\3\2\2\2\u042e\u0430\5\u00b6"+
		"\\\2\u042f\u042e\3\2\2\2\u042f\u0430\3\2\2\2\u0430\u0431\3\2\2\2\u0431"+
		"\u043a\5\u00b8]\2\u0432\u0434\7\20\2\2\u0433\u0435\5\16\b\2\u0434\u0433"+
		"\3\2\2\2\u0434\u0435\3\2\2\2\u0435\u043b\3\2\2\2\u0436\u0438\5\16\b\2"+
		"\u0437\u0436\3\2\2\2\u0437\u0438\3\2\2\2\u0438\u0439\3\2\2\2\u0439\u043b"+
		"\5\u00a4S\2\u043a\u0432\3\2\2\2\u043a\u0437\3\2\2\2\u043b\u00b5\3\2\2"+
		"\2\u043c\u043d\t\6\2\2\u043d\u00b7\3\2\2\2\u043e\u0441\5\u0138\u009d\2"+
		"\u043f\u0441\5\u00ba^\2\u0440\u043e\3\2\2\2\u0440\u043f\3\2\2\2\u0441"+
		"\u00b9\3\2\2\2\u0442\u0445\5\u00bc_\2\u0443\u0445\5\u00c4c\2\u0444\u0442"+
		"\3\2\2\2\u0444\u0443\3\2\2\2\u0445\u00bb\3\2\2\2\u0446\u0448\7\7\2\2\u0447"+
		"\u0449\5\u00be`\2\u0448\u0447\3\2\2\2\u0448\u0449\3\2\2\2\u0449\u044a"+
		"\3\2\2\2\u044a\u044b\7\b\2\2\u044b\u00bd\3\2\2\2\u044c\u0455\5\u00c0a"+
		"\2\u044d\u044f\7\16\2\2\u044e\u044d\3\2\2\2\u044f\u0450\3\2\2\2\u0450"+
		"\u044e\3\2\2\2\u0450\u0451\3\2\2\2\u0451\u0452\3\2\2\2\u0452\u0454\5\u00c0"+
		"a\2\u0453\u044e\3\2\2\2\u0454\u0457\3\2\2\2\u0455\u0453\3\2\2\2\u0455"+
		"\u0456\3\2\2\2\u0456\u0459\3\2\2\2\u0457\u0455\3\2\2\2\u0458\u045a\7\16"+
		"\2\2\u0459\u0458\3\2\2\2\u0459\u045a\3\2\2\2\u045a\u00bf\3\2\2\2\u045b"+
		"\u045d\7\22\2\2\u045c\u045b\3\2\2\2\u045c\u045d\3\2\2\2\u045d\u045e\3"+
		"\2\2\2\u045e\u045f\5\u00c2b\2\u045f\u00c1\3\2\2\2\u0460\u0463\5\u00ba"+
		"^\2\u0461\u0463\7\u0083\2\2\u0462\u0460\3\2\2\2\u0462\u0461\3\2\2\2\u0463"+
		"\u00c3\3\2\2\2\u0464\u0470\7\13\2\2\u0465\u046a\5\u00c6d\2\u0466\u0467"+
		"\7\16\2\2\u0467\u0469\5\u00c6d\2\u0468\u0466\3\2\2\2\u0469\u046c\3\2\2"+
		"\2\u046a\u0468\3\2\2\2\u046a\u046b\3\2\2\2\u046b\u046e\3\2\2\2\u046c\u046a"+
		"\3\2\2\2\u046d\u046f\7\16\2\2\u046e\u046d\3\2\2\2\u046e\u046f\3\2\2\2"+
		"\u046f\u0471\3\2\2\2\u0470\u0465\3\2\2\2\u0470\u0471\3\2\2\2\u0471\u0472"+
		"\3\2\2\2\u0472\u0473\7\f\2\2\u0473\u00c5\3\2\2\2\u0474\u0478\5\u00c8e"+
		"\2\u0475\u0476\7\21\2\2\u0476\u0479\5\u0136\u009c\2\u0477\u0479\5\u00ba"+
		"^\2\u0478\u0475\3\2\2\2\u0478\u0477\3\2\2\2\u0478\u0479\3\2\2\2\u0479"+
		"\u047c\3\2\2\2\u047a\u047b\7\17\2\2\u047b\u047d\5\u0118\u008d\2\u047c"+
		"\u047a\3\2\2\2\u047c\u047d\3\2\2\2\u047d\u0483\3\2\2\2\u047e\u0483\5\u00ca"+
		"f\2\u047f\u0483\5\u00ccg\2\u0480\u0483\5\u00ceh\2\u0481\u0483\5\u00ae"+
		"X\2\u0482\u0474\3\2\2\2\u0482\u047e\3\2\2\2\u0482\u047f\3\2\2\2\u0482"+
		"\u0480\3\2\2\2\u0482\u0481\3\2\2\2\u0483\u00c7\3\2\2\2\u0484\u0491\7\u0084"+
		"\2\2\u0485\u0491\5\u0134\u009b\2\u0486\u048c\7\7\2\2\u0487\u0488\5\u0138"+
		"\u009d\2\u0488\u0489\7\23\2\2\u0489\u048a\5\u0138\u009d\2\u048a\u048d"+
		"\3\2\2\2\u048b\u048d\7\u0084\2\2\u048c\u0487\3\2\2\2\u048c\u048b\3\2\2"+
		"\2\u048d\u048e\3\2\2\2\u048e\u0491\7\b\2\2\u048f\u0491\5\u0138\u009d\2"+
		"\u0490\u0484\3\2\2\2\u0490\u0485\3\2\2\2\u0490\u0486\3\2\2\2\u0490\u048f"+
		"\3\2\2\2\u0491\u00c9\3\2\2\2\u0492\u0493\5\u0142\u00a2\2\u0493\u0494\7"+
		"\t\2\2\u0494\u0496\7\n\2\2\u0495\u0497\5\16\b\2\u0496\u0495\3\2\2\2\u0496"+
		"\u0497\3\2\2\2\u0497\u0499\3\2\2\2\u0498\u049a\5\n\6\2\u0499\u0498\3\2"+
		"\2\2\u0499\u049a\3\2\2\2\u049a\u00cb\3\2\2\2\u049b\u049c\5\u0144\u00a3"+
		"\2\u049c\u049f\7\t\2\2\u049d\u04a0\7\u0083\2\2\u049e\u04a0\5\u00ba^\2"+
		"\u049f\u049d\3\2\2\2\u049f\u049e\3\2\2\2\u04a0\u04a2\3\2\2\2\u04a1\u04a3"+
		"\5\16\b\2\u04a2\u04a1\3\2\2\2\u04a2\u04a3\3\2\2\2\u04a3\u04a4\3\2\2\2"+
		"\u04a4\u04a6\7\n\2\2\u04a5\u04a7\5\n\6\2\u04a6\u04a5\3\2\2\2\u04a6\u04a7"+
		"\3\2\2\2\u04a7\u00cd\3\2\2\2\u04a8\u04aa\7\32\2\2\u04a9\u04a8\3\2\2\2"+
		"\u04a9\u04aa\3\2\2\2\u04aa\u04ab\3\2\2\2\u04ab\u04ac\7\u0083\2\2\u04ac"+
		"\u04ad\5\u00a8U\2\u04ad\u04ae\5\n\6\2\u04ae\u00cf\3\2\2\2\u04af\u04b4"+
		"\7\t\2\2\u04b0\u04b2\5\u00d2j\2\u04b1\u04b3\7\16\2\2\u04b2\u04b1\3\2\2"+
		"\2\u04b2\u04b3\3\2\2\2\u04b3\u04b5\3\2\2\2\u04b4\u04b0\3\2\2\2\u04b4\u04b5"+
		"\3\2\2\2\u04b5\u04b6\3\2\2\2\u04b6\u04b7\7\n\2\2\u04b7\u00d1\3\2\2\2\u04b8"+
		"\u04bd\5\u00d4k\2\u04b9\u04ba\7\16\2\2\u04ba\u04bc\5\u00d4k\2\u04bb\u04b9"+
		"\3\2\2\2\u04bc\u04bf\3\2\2\2\u04bd\u04bb\3\2\2\2\u04bd\u04be\3\2\2\2\u04be"+
		"\u00d3\3\2\2\2\u04bf\u04bd\3\2\2\2\u04c0\u04c2\7\22\2\2\u04c1\u04c0\3"+
		"\2\2\2\u04c1\u04c2\3\2\2\2\u04c2\u04c5\3\2\2\2\u04c3\u04c6\5\u0118\u008d"+
		"\2\u04c4\u04c6\7\u0083\2\2\u04c5\u04c3\3\2\2\2\u04c5\u04c4\3\2\2\2\u04c6"+
		"\u00d5\3\2\2\2\u04c7\u04cb\7e\2\2\u04c8\u04cc\5\u00d8m\2\u04c9\u04cc\5"+
		"\u00dep\2\u04ca\u04cc\7\u0084\2\2\u04cb\u04c8\3\2\2\2\u04cb\u04c9\3\2"+
		"\2\2\u04cb\u04ca\3\2\2\2\u04cc\u04cd\3\2\2\2\u04cd\u04ce\5\u0146\u00a4"+
		"\2\u04ce\u00d7\3\2\2\2\u04cf\u04d1\7t\2\2\u04d0\u04cf\3\2\2\2\u04d0\u04d1"+
		"\3\2\2\2\u04d1\u04d7\3\2\2\2\u04d2\u04d3\7\32\2\2\u04d3\u04d4\7[\2\2\u04d4"+
		"\u04d8\5\u0138\u009d\2\u04d5\u04d8\5\u0138\u009d\2\u04d6\u04d8\5\u00da"+
		"n\2\u04d7\u04d2\3\2\2\2\u04d7\u04d5\3\2\2\2\u04d7\u04d6\3\2\2\2\u04d8"+
		"\u04d9\3\2\2\2\u04d9\u04da\7\\\2\2\u04da\u04db\7\u0084\2\2\u04db\u00d9"+
		"\3\2\2\2\u04dc\u04dd\5\u0138\u009d\2\u04dd\u04de\7\16\2\2\u04de\u04e0"+
		"\3\2\2\2\u04df\u04dc\3\2\2\2\u04df\u04e0\3\2\2\2\u04e0\u04e1\3\2\2\2\u04e1"+
		"\u04e2\7\13\2\2\u04e2\u04e7\5\u00dco\2\u04e3\u04e4\7\16\2\2\u04e4\u04e6"+
		"\5\u00dco\2\u04e5\u04e3\3\2\2\2\u04e6\u04e9\3\2\2\2\u04e7\u04e5\3\2\2"+
		"\2\u04e7\u04e8\3\2\2\2\u04e8\u04eb\3\2\2\2\u04e9\u04e7\3\2\2\2\u04ea\u04ec"+
		"\7\16\2\2\u04eb\u04ea\3\2\2\2\u04eb\u04ec\3\2\2\2\u04ec\u04ed\3\2\2\2"+
		"\u04ed\u04ee\7\f\2\2\u04ee\u00db\3\2\2\2\u04ef\u04f2\5\u0138\u009d\2\u04f0"+
		"\u04f1\7[\2\2\u04f1\u04f3\5\u0138\u009d\2\u04f2\u04f0\3\2\2\2\u04f2\u04f3"+
		"\3\2\2\2\u04f3\u00dd\3\2\2\2\u04f4\u04f5\7\u0083\2\2\u04f5\u04fb\7\17"+
		"\2\2\u04f6\u04fc\5j\66\2\u04f7\u04f8\7y\2\2\u04f8\u04f9\7\t\2\2\u04f9"+
		"\u04fa\7\u0084\2\2\u04fa\u04fc\7\n\2\2\u04fb\u04f6\3\2\2\2\u04fb\u04f7"+
		"\3\2\2\2\u04fc\u00df\3\2\2\2\u04fd\u04fe\7d\2\2\u04fe\u04ff\5\u00e2r\2"+
		"\u04ff\u00e1\3\2\2\2\u0500\u0501\7[\2\2\u0501\u0502\7x\2\2\u0502\u0503"+
		"\5\u0138\u009d\2\u0503\u0504\5\u0146\u00a4\2\u0504\u051a\3\2\2\2\u0505"+
		"\u0506\7\17\2\2\u0506\u0507\5j\66\2\u0507\u0508\5\u0146\u00a4\2\u0508"+
		"\u051a\3\2\2\2\u0509\u050a\7e\2\2\u050a\u050b\5\u0138\u009d\2\u050b\u050c"+
		"\7\17\2\2\u050c\u050d\5j\66\2\u050d\u050e\5\u0146\u00a4\2\u050e\u051a"+
		"\3\2\2\2\u050f\u0511\7U\2\2\u0510\u050f\3\2\2\2\u0510\u0511\3\2\2\2\u0511"+
		"\u0512\3\2\2\2\u0512\u051a\5\6\4\2\u0513\u0515\7U\2\2\u0514\u0513\3\2"+
		"\2\2\u0514\u0515\3\2\2\2\u0515\u0516\3\2\2\2\u0516\u0517\5\u00e4s\2\u0517"+
		"\u0518\5\u0146\u00a4\2\u0518\u051a\3\2\2\2\u0519\u0500\3\2\2\2\u0519\u0505"+
		"\3\2\2\2\u0519\u0509\3\2\2\2\u0519\u0510\3\2\2\2\u0519\u0514\3\2\2\2\u051a"+
		"\u00e3\3\2\2\2\u051b\u051e\7\32\2\2\u051c\u051d\7[\2\2\u051d\u051f\5\u0138"+
		"\u009d\2\u051e\u051c\3\2\2\2\u051e\u051f\3\2\2\2\u051f\u0523\3\2\2\2\u0520"+
		"\u0523\5\u0138\u009d\2\u0521\u0523\5\u00dan\2\u0522\u051b\3\2\2\2\u0522"+
		"\u0520\3\2\2\2\u0522\u0521\3\2\2\2\u0523\u0526\3\2\2\2\u0524\u0525\7\\"+
		"\2\2\u0525\u0527\7\u0084\2\2\u0526\u0524\3\2\2\2\u0526\u0527\3\2\2\2\u0527"+
		"\u00e5\3\2\2\2\u0528\u052a\5\u00b6\\\2\u0529\u0528\3\2\2\2\u0529\u052a"+
		"\3\2\2\2\u052a\u052c\3\2\2\2\u052b\u052d\7]\2\2\u052c\u052b\3\2\2\2\u052c"+
		"\u052d\3\2\2\2\u052d\u052e\3\2\2\2\u052e\u0531\5\u00e8u\2\u052f\u0532"+
		"\5\u00eav\2\u0530\u0532\5\u00ecw\2\u0531\u052f\3\2\2\2\u0531\u0530\3\2"+
		"\2\2\u0532\u0534\3\2\2\2\u0533\u0535\7\r\2\2\u0534\u0533\3\2\2\2\u0534"+
		"\u0535\3\2\2\2\u0535\u00e7\3\2\2\2\u0536\u0537\t\7\2\2\u0537\u00e9\3\2"+
		"\2\2\u0538\u053a\5\u00ba^\2\u0539\u053b\5\16\b\2\u053a\u0539\3\2\2\2\u053a"+
		"\u053b\3\2\2\2\u053b\u053d\3\2\2\2\u053c\u053e\5\u00a4S\2\u053d\u053c"+
		"\3\2\2\2\u053d\u053e\3\2\2\2\u053e\u00eb\3\2\2\2\u053f\u0544\5\u00eex"+
		"\2\u0540\u0541\7\16\2\2\u0541\u0543\5\u00eex\2\u0542\u0540\3\2\2\2\u0543"+
		"\u0546\3\2\2\2\u0544\u0542\3\2\2\2\u0544\u0545\3\2\2\2\u0545\u00ed\3\2"+
		"\2\2\u0546\u0544\3\2\2\2\u0547\u0549\5\u0138\u009d\2\u0548\u054a\5\16"+
		"\b\2\u0549\u0548\3\2\2\2\u0549\u054a\3\2\2\2\u054a\u0550\3\2\2\2\u054b"+
		"\u054d\7\17\2\2\u054c\u054e\5R*\2\u054d\u054c\3\2\2\2\u054d\u054e\3\2"+
		"\2\2\u054e\u054f\3\2\2\2\u054f\u0551\5\u0118\u008d\2\u0550\u054b\3\2\2"+
		"\2\u0550\u0551\3\2\2\2\u0551\u00ef\3\2\2\2\u0552\u0553\7O\2\2\u0553\u0554"+
		"\7\t\2\2\u0554\u0555\5\u011a\u008e\2\u0555\u0556\7\n\2\2\u0556\u0557\5"+
		"\u00f2z\2\u0557\u00f1\3\2\2\2\u0558\u055a\7\13\2\2\u0559\u055b\5\u00f4"+
		"{\2\u055a\u0559\3\2\2\2\u055a\u055b\3\2\2\2\u055b\u0560\3\2\2\2\u055c"+
		"\u055e\5\u00f8}\2\u055d\u055f\5\u00f4{\2\u055e\u055d\3\2\2\2\u055e\u055f"+
		"\3\2\2\2\u055f\u0561\3\2\2\2\u0560\u055c\3\2\2\2\u0560\u0561\3\2\2\2\u0561"+
		"\u0562\3\2\2\2\u0562\u0563\7\f\2\2\u0563\u00f3\3\2\2\2\u0564\u0566\5\u00f6"+
		"|\2\u0565\u0564\3\2\2\2\u0566\u0567\3\2\2\2\u0567\u0565\3\2\2\2\u0567"+
		"\u0568\3\2\2\2\u0568\u00f5\3\2\2\2\u0569\u056a\7E\2\2\u056a\u056b\5\u011a"+
		"\u008e\2\u056b\u056d\7\21\2\2\u056c\u056e\5\f\7\2\u056d\u056c\3\2\2\2"+
		"\u056d\u056e\3\2\2\2\u056e\u00f7\3\2\2\2\u056f\u0570\7U\2\2\u0570\u0572"+
		"\7\21\2\2\u0571\u0573\5\f\7\2\u0572\u0571\3\2\2\2\u0572\u0573\3\2\2\2"+
		"\u0573\u00f9\3\2\2\2\u0574\u0575\7Z\2\2\u0575\u057b\5\n\6\2\u0576\u0578"+
		"\5\u00fc\177\2\u0577\u0579\5\u00fe\u0080\2\u0578\u0577\3\2\2\2\u0578\u0579"+
		"\3\2\2\2\u0579\u057c\3\2\2\2\u057a\u057c\5\u00fe\u0080\2\u057b\u0576\3"+
		"\2\2\2\u057b\u057a\3\2\2\2\u057c\u00fb\3\2\2\2\u057d\u057e\7I\2\2\u057e"+
		"\u057f\7\t\2\2\u057f\u0580\7\u0083\2\2\u0580\u0581\7\n\2\2\u0581\u0582"+
		"\5\n\6\2\u0582\u00fd\3\2\2\2\u0583\u0584\7J\2\2\u0584\u0585\5\n\6\2\u0585"+
		"\u00ff\3\2\2\2\u0586\u0587\7\r\2\2\u0587\u0101\3\2\2\2\u0588\u0589\7V"+
		"\2\2\u0589\u058a\7\t\2\2\u058a\u058b\5\u011a\u008e\2\u058b\u058c\7\n\2"+
		"\2\u058c\u058f\5\4\3\2\u058d\u058e\7F\2\2\u058e\u0590\5\4\3\2\u058f\u058d"+
		"\3\2\2\2\u058f\u0590\3\2\2\2\u0590\u0103\3\2\2\2\u0591\u0592\7@\2\2\u0592"+
		"\u0593\5\4\3\2\u0593\u0594\7P\2\2\u0594\u0595\7\t\2\2\u0595\u0596\5\u011a"+
		"\u008e\2\u0596\u0597\7\n\2\2\u0597\u0598\5\u0146\u00a4\2\u0598\u05d7\3"+
		"\2\2\2\u0599\u059a\7P\2\2\u059a\u059b\7\t\2\2\u059b\u059c\5\u011a\u008e"+
		"\2\u059c\u059d\7\n\2\2\u059d\u059e\5\4\3\2\u059e\u05d7\3\2\2\2\u059f\u05a0"+
		"\7N\2\2\u05a0\u05a2\7\t\2\2\u05a1\u05a3\5\u011a\u008e\2\u05a2\u05a1\3"+
		"\2\2\2\u05a2\u05a3\3\2\2\2\u05a3\u05a4\3\2\2\2\u05a4\u05a6\7\r\2\2\u05a5"+
		"\u05a7\5\u011a\u008e\2\u05a6\u05a5\3\2\2\2\u05a6\u05a7\3\2\2\2\u05a7\u05a8"+
		"\3\2\2\2\u05a8\u05aa\7\r\2\2\u05a9\u05ab\5\u011a\u008e\2\u05aa\u05a9\3"+
		"\2\2\2\u05aa\u05ab\3\2\2\2\u05ab\u05ac\3\2\2\2\u05ac\u05ad\7\n\2\2\u05ad"+
		"\u05d7\5\4\3\2\u05ae\u05af\7N\2\2\u05af\u05b0\7\t\2\2\u05b0\u05b1\5\u00e8"+
		"u\2\u05b1\u05b2\5\u00ecw\2\u05b2\u05b4\7\r\2\2\u05b3\u05b5\5\u011a\u008e"+
		"\2\u05b4\u05b3\3\2\2\2\u05b4\u05b5\3\2\2\2\u05b5\u05b6\3\2\2\2\u05b6\u05b8"+
		"\7\r\2\2\u05b7\u05b9\5\u011a\u008e\2\u05b8\u05b7\3\2\2\2\u05b8\u05b9\3"+
		"\2\2\2\u05b9\u05ba\3\2\2\2\u05ba\u05bb\7\n\2\2\u05bb\u05bc\5\4\3\2\u05bc"+
		"\u05d7\3\2\2\2\u05bd\u05be\7N\2\2\u05be\u05bf\7\t\2\2\u05bf\u05c3\5\u0118"+
		"\u008d\2\u05c0\u05c4\7Y\2\2\u05c1\u05c2\7\u0083\2\2\u05c2\u05c4\6\u0083"+
		"\3\2\u05c3\u05c0\3\2\2\2\u05c3\u05c1\3\2\2\2\u05c4\u05c5\3\2\2\2\u05c5"+
		"\u05c6\5\u011a\u008e\2\u05c6\u05c7\7\n\2\2\u05c7\u05c8\5\4\3\2\u05c8\u05d7"+
		"\3\2\2\2\u05c9\u05ca\7N\2\2\u05ca\u05cb\7\t\2\2\u05cb\u05cc\5\u00e8u\2"+
		"\u05cc\u05d0\5\u00eex\2\u05cd\u05d1\7Y\2\2\u05ce\u05cf\7\u0083\2\2\u05cf"+
		"\u05d1\6\u0083\4\2\u05d0\u05cd\3\2\2\2\u05d0\u05ce\3\2\2\2\u05d1\u05d2"+
		"\3\2\2\2\u05d2\u05d3\5\u011a\u008e\2\u05d3\u05d4\7\n\2\2\u05d4\u05d5\5"+
		"\4\3\2\u05d5\u05d7\3\2\2\2\u05d6\u0591\3\2\2\2\u05d6\u0599\3\2\2\2\u05d6"+
		"\u059f\3\2\2\2\u05d6\u05ae\3\2\2\2\u05d6\u05bd\3\2\2\2\u05d6\u05c9\3\2"+
		"\2\2\u05d7\u0105\3\2\2\2\u05d8\u05db\7M\2\2\u05d9\u05da\6\u0084\5\2\u05da"+
		"\u05dc\7\u0083\2\2\u05db\u05d9\3\2\2\2\u05db\u05dc\3\2\2\2\u05dc\u05dd"+
		"\3\2\2\2\u05dd\u05de\5\u0146\u00a4\2\u05de\u0107\3\2\2\2\u05df\u05e2\7"+
		"?\2\2\u05e0\u05e1\6\u0085\6\2\u05e1\u05e3\7\u0083\2\2\u05e2\u05e0\3\2"+
		"\2\2\u05e2\u05e3\3\2\2\2\u05e3\u05e4\3\2\2\2\u05e4\u05e5\5\u0146\u00a4"+
		"\2\u05e5\u0109\3\2\2\2\u05e6\u05e9\7K\2\2\u05e7\u05e8\6\u0086\7\2\u05e8"+
		"\u05ea\5\u011a\u008e\2\u05e9\u05e7\3\2\2\2\u05e9\u05ea\3\2\2\2\u05ea\u05eb"+
		"\3\2\2\2\u05eb\u05ec\5\u0146\u00a4\2\u05ec\u010b\3\2\2\2\u05ed\u05f0\7"+
		"n\2\2\u05ee\u05ef\6\u0087\b\2\u05ef\u05f1\5\u011a\u008e\2\u05f0\u05ee"+
		"\3\2\2\2\u05f0\u05f1\3\2\2\2\u05f1\u05f2\3\2\2\2\u05f2\u05f3\5\u0146\u00a4"+
		"\2\u05f3\u010d\3\2\2\2\u05f4\u05f5\7T\2\2\u05f5\u05f6\7\t\2\2\u05f6\u05f7"+
		"\5\u011a\u008e\2\u05f7\u05f8\7\n\2\2\u05f8\u05f9\5\4\3\2\u05f9\u010f\3"+
		"\2\2\2\u05fa\u05fb\7\u0083\2\2\u05fb\u05fc\7\21\2\2\u05fc\u05fd\5\4\3"+
		"\2\u05fd\u0111\3\2\2\2\u05fe\u05ff\7W\2\2\u05ff\u0600\6\u008a\t\2\u0600"+
		"\u0601\5\u011a\u008e\2\u0601\u0602\5\u0146\u00a4\2\u0602\u0113\3\2\2\2"+
		"\u0603\u0604\7Q\2\2\u0604\u0605\5\u0146\u00a4\2\u0605\u0115\3\2\2\2\u0606"+
		"\u0607\6\u008c\n\2\u0607\u0609\5\u011a\u008e\2\u0608\u060a\7\r\2\2\u0609"+
		"\u0608\3\2\2\2\u0609\u060a\3\2\2\2\u060a\u0117\3\2\2\2\u060b\u060c\b\u008d"+
		"\1\2\u060c\u063f\5\u011c\u008f\2\u060d\u063f\5\u0126\u0094\2\u060e\u0610"+
		"\7_\2\2\u060f\u0611\7\u0083\2\2\u0610\u060f\3\2\2\2\u0610\u0611\3\2\2"+
		"\2\u0611\u0612\3\2\2\2\u0612\u063f\5\u0092J\2\u0613\u0614\7G\2\2\u0614"+
		"\u0616\5\u0118\u008d\2\u0615\u0617\5\66\34\2\u0616\u0615\3\2\2\2\u0616"+
		"\u0617\3\2\2\2\u0617\u0619\3\2\2\2\u0618\u061a\5\u00d0i\2\u0619\u0618"+
		"\3\2\2\2\u0619\u061a\3\2\2\2\u061a\u063f\3\2\2\2\u061b\u061c\7X\2\2\u061c"+
		"\u063f\5\u0118\u008d$\u061d\u061e\7L\2\2\u061e\u063f\5\u0118\u008d#\u061f"+
		"\u0620\7B\2\2\u0620\u063f\5\u0118\u008d\"\u0621\u0622\7\24\2\2\u0622\u063f"+
		"\5\u0118\u008d!\u0623\u0624\7\25\2\2\u0624\u063f\5\u0118\u008d \u0625"+
		"\u0626\7\26\2\2\u0626\u063f\5\u0118\u008d\37\u0627\u0628\7\27\2\2\u0628"+
		"\u063f\5\u0118\u008d\36\u0629\u062a\7\30\2\2\u062a\u063f\5\u0118\u008d"+
		"\35\u062b\u062c\7\31\2\2\u062c\u063f\5\u0118\u008d\34\u062d\u063f\5\u0122"+
		"\u0092\2\u062e\u063f\5\u011e\u0090\2\u062f\u063f\5\u010c\u0087\2\u0630"+
		"\u063f\7S\2\2\u0631\u063f\5\u0138\u009d\2\u0632\u063f\7b\2\2\u0633\u063f"+
		"\5\u012e\u0098\2\u0634\u063f\5\u00bc_\2\u0635\u063f\5\u00c4c\2\u0636\u0637"+
		"\7\t\2\2\u0637\u0638\5\u011a\u008e\2\u0638\u0639\7\n\2\2\u0639\u063f\3"+
		"\2\2\2\u063a\u063c\5\66\34\2\u063b\u063d\5\u011a\u008e\2\u063c\u063b\3"+
		"\2\2\2\u063c\u063d\3\2\2\2\u063d\u063f\3\2\2\2\u063e\u060b\3\2\2\2\u063e"+
		"\u060d\3\2\2\2\u063e\u060e\3\2\2\2\u063e\u0613\3\2\2\2\u063e\u061b\3\2"+
		"\2\2\u063e\u061d\3\2\2\2\u063e\u061f\3\2\2\2\u063e\u0621\3\2\2\2\u063e"+
		"\u0623\3\2\2\2\u063e\u0625\3\2\2\2\u063e\u0627\3\2\2\2\u063e\u0629\3\2"+
		"\2\2\u063e\u062b\3\2\2\2\u063e\u062d\3\2\2\2\u063e\u062e\3\2\2\2\u063e"+
		"\u062f\3\2\2\2\u063e\u0630\3\2\2\2\u063e\u0631\3\2\2\2\u063e\u0632\3\2"+
		"\2\2\u063e\u0633\3\2\2\2\u063e\u0634\3\2\2\2\u063e\u0635\3\2\2\2\u063e"+
		"\u0636\3\2\2\2\u063e\u063a\3\2\2\2\u063f\u0689\3\2\2\2\u0640\u0641\f\33"+
		"\2\2\u0641\u0642\t\b\2\2\u0642\u0688\5\u0118\u008d\34\u0643\u0644\f\32"+
		"\2\2\u0644\u0645\t\t\2\2\u0645\u0688\5\u0118\u008d\33\u0646\u064d\f\31"+
		"\2\2\u0647\u064e\7\35\2\2\u0648\u0649\7\37\2\2\u0649\u064e\7\37\2\2\u064a"+
		"\u064b\7\37\2\2\u064b\u064c\7\37\2\2\u064c\u064e\7\37\2\2\u064d\u0647"+
		"\3\2\2\2\u064d\u0648\3\2\2\2\u064d\u064a\3\2\2\2\u064e\u064f\3\2\2\2\u064f"+
		"\u0688\5\u0118\u008d\32\u0650\u0651\f\30\2\2\u0651\u0652\t\n\2\2\u0652"+
		"\u0688\5\u0118\u008d\31\u0653\u0654\f\27\2\2\u0654\u0655\7A\2\2\u0655"+
		"\u0688\5\u0118\u008d\30\u0656\u0657\f\26\2\2\u0657\u0658\7Y\2\2\u0658"+
		"\u0688\5\u0118\u008d\27\u0659\u065a\f\25\2\2\u065a\u065b\t\13\2\2\u065b"+
		"\u0688\5\u0118\u008d\26\u065c\u065d\f\24\2\2\u065d\u065e\t\f\2\2\u065e"+
		"\u0688\5\u0118\u008d\25\u065f\u0660\f\23\2\2\u0660\u0661\t\r\2\2\u0661"+
		"\u0688\5\u0118\u008d\24\u0662\u0663\f\22\2\2\u0663\u0664\7\20\2\2\u0664"+
		"\u0665\5\u0118\u008d\2\u0665\u0666\7\21\2\2\u0666\u0667\5\u0118\u008d"+
		"\23\u0667\u0688\3\2\2\2\u0668\u0669\f\21\2\2\u0669\u066a\7\17\2\2\u066a"+
		"\u0688\5\u0118\u008d\22\u066b\u066c\f\20\2\2\u066c\u066d\5\u012c\u0097"+
		"\2\u066d\u066e\5\u0118\u008d\21\u066e\u0688\3\2\2\2\u066f\u0670\f*\2\2"+
		"\u0670\u0671\7\7\2\2\u0671\u0672\5\u011a\u008e\2\u0672\u0673\7\b\2\2\u0673"+
		"\u0688\3\2\2\2\u0674\u0675\f)\2\2\u0675\u0676\7\23\2\2\u0676\u0678\5\u0138"+
		"\u009d\2\u0677\u0679\5\60\31\2\u0678\u0677\3\2\2\2\u0678\u0679\3\2\2\2"+
		"\u0679\u0688\3\2\2\2\u067a\u067b\f\'\2\2\u067b\u0688\5\u00d0i\2\u067c"+
		"\u067d\f&\2\2\u067d\u067e\6\u008d\33\2\u067e\u0688\7\24\2\2\u067f\u0680"+
		"\f%\2\2\u0680\u0681\6\u008d\35\2\u0681\u0688\7\25\2\2\u0682\u0683\f\17"+
		"\2\2\u0683\u0688\5\u0130\u0099\2\u0684\u0685\f\3\2\2\u0685\u0686\7[\2"+
		"\2\u0686\u0688\5\20\t\2\u0687\u0640\3\2\2\2\u0687\u0643\3\2\2\2\u0687"+
		"\u0646\3\2\2\2\u0687\u0650\3\2\2\2\u0687\u0653\3\2\2\2\u0687\u0656\3\2"+
		"\2\2\u0687\u0659\3\2\2\2\u0687\u065c\3\2\2\2\u0687\u065f\3\2\2\2\u0687"+
		"\u0662\3\2\2\2\u0687\u0668\3\2\2\2\u0687\u066b\3\2\2\2\u0687\u066f\3\2"+
		"\2\2\u0687\u0674\3\2\2\2\u0687\u067a\3\2\2\2\u0687\u067c\3\2\2\2\u0687"+
		"\u067f\3\2\2\2\u0687\u0682\3\2\2\2\u0687\u0684\3\2\2\2\u0688\u068b\3\2"+
		"\2\2\u0689\u0687\3\2\2\2\u0689\u068a\3\2\2\2\u068a\u0119\3\2\2\2\u068b"+
		"\u0689\3\2\2\2\u068c\u0691\5\u0118\u008d\2\u068d\u068e\7\16\2\2\u068e"+
		"\u0690\5\u0118\u008d\2\u068f\u068d\3\2\2\2\u0690\u0693\3\2\2\2\u0691\u068f"+
		"\3\2\2\2\u0691\u0692\3\2\2\2\u0692\u011b\3\2\2\2\u0693\u0691\3\2\2\2\u0694"+
		"\u0696\7R\2\2\u0695\u0697\7\32\2\2\u0696\u0695\3\2\2\2\u0696\u0697\3\2"+
		"\2\2\u0697\u0699\3\2\2\2\u0698\u069a\7\u0083\2\2\u0699\u0698\3\2\2\2\u0699"+
		"\u069a\3\2\2\2\u069a\u069b\3\2\2\2\u069b\u069d\5\u00a8U\2\u069c\u069e"+
		"\5\16\b\2\u069d\u069c\3\2\2\2\u069d\u069e\3\2\2\2\u069e\u069f\3\2\2\2"+
		"\u069f\u06a0\5\n\6\2\u06a0\u011d\3\2\2\2\u06a1\u06a2\7\13\2\2\u06a2\u06a7"+
		"\5\u0120\u0091\2\u06a3\u06a4\7\16\2\2\u06a4\u06a6\5\u0120\u0091\2\u06a5"+
		"\u06a3\3\2\2\2\u06a6\u06a9\3\2\2\2\u06a7\u06a5\3\2\2\2\u06a7\u06a8\3\2"+
		"\2\2\u06a8\u06ab\3\2\2\2\u06a9\u06a7\3\2\2\2\u06aa\u06ac\7\16\2\2\u06ab"+
		"\u06aa\3\2\2\2\u06ab\u06ac\3\2\2\2\u06ac\u06ad\3\2\2\2\u06ad\u06ae\7\f"+
		"\2\2\u06ae\u011f\3\2\2\2\u06af\u06b0\7\32\2\2\u06b0\u06b1\5\u0124\u0093"+
		"\2\u06b1\u0121\3\2\2\2\u06b2\u06b3\7\13\2\2\u06b3\u06b8\5\u0124\u0093"+
		"\2\u06b4\u06b5\7\16\2\2\u06b5\u06b7\5\u0124\u0093\2\u06b6\u06b4\3\2\2"+
		"\2\u06b7\u06ba\3\2\2\2\u06b8\u06b6\3\2\2\2\u06b8\u06b9\3\2\2\2\u06b9\u06bc"+
		"\3\2\2\2\u06ba\u06b8\3\2\2\2\u06bb\u06bd\7\16\2\2\u06bc\u06bb\3\2\2\2"+
		"\u06bc\u06bd\3\2\2\2\u06bd\u06be\3\2\2\2\u06be\u06bf\7\f\2\2\u06bf\u0123"+
		"\3\2\2\2\u06c0\u06c1\7\7\2\2\u06c1\u06c2\5\u0118\u008d\2\u06c2\u06c3\7"+
		"\b\2\2\u06c3\u06c4\5\u00a8U\2\u06c4\u06c5\5\n\6\2\u06c5\u0125\3\2\2\2"+
		"\u06c6\u06c8\7^\2\2\u06c7\u06c6\3\2\2\2\u06c7\u06c8\3\2\2\2\u06c8\u06c9"+
		"\3\2\2\2\u06c9\u06cb\5\u0128\u0095\2\u06ca\u06cc\5\16\b\2\u06cb\u06ca"+
		"\3\2\2\2\u06cb\u06cc\3\2\2\2\u06cc\u06cd\3\2\2\2\u06cd\u06ce\7\66\2\2"+
		"\u06ce\u06cf\5\u012a\u0096\2\u06cf\u0127\3\2\2\2\u06d0\u06d3\7\u0083\2"+
		"\2\u06d1\u06d3\5\u00a8U\2\u06d2\u06d0\3\2\2\2\u06d2\u06d1\3\2\2\2\u06d3"+
		"\u0129\3\2\2\2\u06d4\u06d7\5\u0118\u008d\2\u06d5\u06d7\5\n\6\2\u06d6\u06d4"+
		"\3\2\2\2\u06d6\u06d5\3\2\2\2\u06d7\u012b\3\2\2\2\u06d8\u06d9\t\16\2\2"+
		"\u06d9\u012d\3\2\2\2\u06da\u06e1\7\67\2\2\u06db\u06e1\79\2\2\u06dc\u06e1"+
		"\7\u0084\2\2\u06dd\u06e1\5\u0130\u0099\2\u06de\u06e1\7\6\2\2\u06df\u06e1"+
		"\5\u0134\u009b\2\u06e0\u06da\3\2\2\2\u06e0\u06db\3\2\2\2\u06e0\u06dc\3"+
		"\2\2\2\u06e0\u06dd\3\2\2\2\u06e0\u06de\3\2\2\2\u06e0\u06df\3\2\2\2\u06e1"+
		"\u012f\3\2\2\2\u06e2\u06e6\7\u0085\2\2\u06e3\u06e5\5\u0132\u009a\2\u06e4"+
		"\u06e3\3\2\2\2\u06e5\u06e8\3\2\2\2\u06e6\u06e4\3\2\2\2\u06e6\u06e7\3\2"+
		"\2\2\u06e7\u06e9\3\2\2\2\u06e8\u06e6\3\2\2\2\u06e9\u06ea\7\u0085\2\2\u06ea"+
		"\u0131\3\2\2\2\u06eb\u06f1\7\u008c\2\2\u06ec\u06ed\7\u008b\2\2\u06ed\u06ee"+
		"\5\u0118\u008d\2\u06ee\u06ef\7\f\2\2\u06ef\u06f1\3\2\2\2\u06f0\u06eb\3"+
		"\2\2\2\u06f0\u06ec\3\2\2\2\u06f1\u0133\3\2\2\2\u06f2\u06f4\7\27\2\2\u06f3"+
		"\u06f2\3\2\2\2\u06f3\u06f4\3\2\2\2\u06f4\u06f5\3\2\2\2\u06f5\u06fb\7:"+
		"\2\2\u06f6\u06fb\7;\2\2\u06f7\u06fb\7<\2\2\u06f8\u06fb\7=\2\2\u06f9\u06fb"+
		"\7>\2\2\u06fa\u06f3\3\2\2\2\u06fa\u06f6\3\2\2\2\u06fa\u06f7\3\2\2\2\u06fa"+
		"\u06f8\3\2\2\2\u06fa\u06f9\3\2\2\2\u06fb\u0135\3\2\2\2\u06fc\u06fd\t\17"+
		"\2\2\u06fd\u0137\3\2\2\2\u06fe\u0701\5\u013a\u009e\2\u06ff\u0701\7\u0083"+
		"\2\2\u0700\u06fe\3\2\2\2\u0700\u06ff\3\2\2\2\u0701\u0139\3\2\2\2\u0702"+
		"\u0705\5\u013e\u00a0\2\u0703\u0705\79\2\2\u0704\u0702\3\2\2\2\u0704\u0703"+
		"\3\2\2\2\u0705\u013b\3\2\2\2\u0706\u070a\5\u0140\u00a1\2\u0707\u070a\7"+
		"9\2\2\u0708\u070a\7\u0083\2\2\u0709\u0706\3\2\2\2\u0709\u0707\3\2\2\2"+
		"\u0709\u0708\3\2\2\2\u070a\u013d\3\2\2\2\u070b\u070f\5\u0140\u00a1\2\u070c"+
		"\u070f\7]\2\2\u070d\u070f\7B\2\2\u070e\u070b\3\2\2\2\u070e\u070c\3\2\2"+
		"\2\u070e\u070d\3\2\2\2\u070f\u013f\3\2\2\2\u0710\u0711\t\20\2\2\u0711"+
		"\u0141\3\2\2\2\u0712\u0713\7u\2\2\u0713\u0714\5\u00c8e\2\u0714\u0143\3"+
		"\2\2\2\u0715\u0716\7v\2\2\u0716\u0717\5\u00c8e\2\u0717\u0145\3\2\2\2\u0718"+
		"\u071d\7\r\2\2\u0719\u071d\7\2\2\3\u071a\u071d\6\u00a4 \2\u071b\u071d"+
		"\6\u00a4!\2\u071c\u0718\3\2\2\2\u071c\u0719\3\2\2\2\u071c\u071a\3\2\2"+
		"\2\u071c\u071b\3\2\2\2\u071d\u0147\3\2\2\2\u00f4\u0149\u0160\u0163\u016f"+
		"\u0173\u017a\u0189\u018c\u0193\u0197\u019e\u01a2\u01af\u01bb\u01c5\u01c7"+
		"\u01ca\u01d7\u01dc\u01df\u01e2\u01ea\u01ee\u01f2\u01fb\u0204\u0208\u020c"+
		"\u020f\u0212\u0216\u021a\u021f\u0224\u0228\u022f\u0235\u023e\u0242\u0248"+
		"\u024e\u025d\u0262\u0268\u026c\u026e\u0271\u0275\u027a\u027d\u0283\u0287"+
		"\u0293\u029a\u029e\u02a1\u02a5\u02a9\u02ad\u02b1\u02ba\u02bf\u02c3\u02cb"+
		"\u02ce\u02d3\u02d9\u02de\u02e9\u02ed\u02f4\u02f9\u030a\u0310\u0315\u031d"+
		"\u0324\u032d\u0330\u0334\u0338\u0342\u0347\u034d\u0352\u035a\u035d\u0363"+
		"\u0367\u036b\u0372\u0378\u037c\u0381\u0384\u0387\u038c\u0391\u0394\u0397"+
		"\u03a1\u03a4\u03ab\u03b0\u03b6\u03b9\u03bc\u03c1\u03c5\u03cb\u03d3\u03d6"+
		"\u03d9\u03dc\u03df\u03e3\u03e6\u03e9\u03ed\u03ef\u03f5\u03fb\u03fd\u0401"+
		"\u0407\u040f\u0414\u0416\u041b\u041f\u0422\u0425\u0429\u042c\u042f\u0434"+
		"\u0437\u043a\u0440\u0444\u0448\u0450\u0455\u0459\u045c\u0462\u046a\u046e"+
		"\u0470\u0478\u047c\u0482\u048c\u0490\u0496\u0499\u049f\u04a2\u04a6\u04a9"+
		"\u04b2\u04b4\u04bd\u04c1\u04c5\u04cb\u04d0\u04d7\u04df\u04e7\u04eb\u04f2"+
		"\u04fb\u0510\u0514\u0519\u051e\u0522\u0526\u0529\u052c\u0531\u0534\u053a"+
		"\u053d\u0544\u0549\u054d\u0550\u055a\u055e\u0560\u0567\u056d\u0572\u0578"+
		"\u057b\u058f\u05a2\u05a6\u05aa\u05b4\u05b8\u05c3\u05d0\u05d6\u05db\u05e2"+
		"\u05e9\u05f0\u0609\u0610\u0616\u0619\u063c\u063e\u064d\u0678\u0687\u0689"+
		"\u0691\u0696\u0699\u069d\u06a7\u06ab\u06b8\u06bc\u06c7\u06cb\u06d2\u06d6"+
		"\u06e0\u06e6\u06f0\u06f3\u06fa\u0700\u0704\u0709\u070e\u071c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}