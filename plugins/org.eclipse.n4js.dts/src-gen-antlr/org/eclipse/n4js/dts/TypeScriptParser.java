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
		MultiLineComment=1, SingleLineComment=2, RegularExpressionLiteral=3, OpenBracket=4, 
		CloseBracket=5, OpenParen=6, CloseParen=7, OpenBrace=8, CloseBrace=9, 
		SemiColon=10, Comma=11, Assign=12, QuestionMark=13, Colon=14, Ellipsis=15, 
		Dot=16, PlusPlus=17, MinusMinus=18, Plus=19, Minus=20, BitNot=21, Not=22, 
		Multiply=23, Divide=24, Modulus=25, LeftShiftArithmetic=26, LessThan=27, 
		MoreThan=28, LessThanEquals=29, GreaterThanEquals=30, Equals_=31, NotEquals=32, 
		IdentityEquals=33, IdentityNotEquals=34, BitAnd=35, BitXOr=36, BitOr=37, 
		And=38, Or=39, MultiplyAssign=40, DivideAssign=41, ModulusAssign=42, PlusAssign=43, 
		MinusAssign=44, LeftShiftArithmeticAssign=45, RightShiftArithmeticAssign=46, 
		RightShiftLogicalAssign=47, BitAndAssign=48, BitXorAssign=49, BitOrAssign=50, 
		ARROW=51, NullLiteral=52, UndefinedLiteral=53, BooleanLiteral=54, DecimalLiteral=55, 
		HexIntegerLiteral=56, OctalIntegerLiteral=57, OctalIntegerLiteral2=58, 
		BinaryIntegerLiteral=59, Break=60, Do=61, Instanceof=62, Typeof=63, Unique=64, 
		Keyof=65, Case=66, Else=67, New=68, Var=69, Catch=70, Finally=71, Return=72, 
		Void=73, Continue=74, For=75, Switch=76, While=77, Debugger=78, Function=79, 
		This=80, With=81, Default=82, If=83, Throw=84, Delete=85, In=86, Try=87, 
		As=88, From=89, ReadOnly=90, Async=91, Class=92, Enum=93, Extends=94, 
		Super=95, Const=96, Export=97, Import=98, Implements=99, Let=100, Private=101, 
		Public=102, Interface=103, Package=104, Protected=105, Static=106, Yield=107, 
		Any=108, Number=109, Boolean=110, String=111, Symbol=112, TypeAlias=113, 
		Get=114, Set=115, Constructor=116, Namespace=117, Require=118, Module=119, 
		Declare=120, Abstract=121, Is=122, Infer=123, Never=124, Unknown=125, 
		Asserts=126, At=127, Identifier=128, StringLiteral=129, BackTick=130, 
		WhiteSpaces=131, LineTerminator=132, HtmlComment=133, CDataComment=134, 
		UnexpectedCharacter=135, TemplateStringStartExpression=136, TemplateStringAtom=137;
	public static final int
		RULE_initializer = 0, RULE_bindingPattern = 1, RULE_typeParameters = 2, 
		RULE_typeParameterList = 3, RULE_typeParameter = 4, RULE_constraint = 5, 
		RULE_defaultType = 6, RULE_colonSepTypeRef = 7, RULE_typeRef = 8, RULE_conditionalTypeRef = 9, 
		RULE_unionTypeExpression = 10, RULE_intersectionTypeExpression = 11, RULE_operatorTypeRef = 12, 
		RULE_typeOperator = 13, RULE_arrayTypeExpression = 14, RULE_primaryTypeExpression = 15, 
		RULE_literalType = 16, RULE_arrowFunctionTypeExpression = 17, RULE_tupleTypeExpression = 18, 
		RULE_tupleTypeArgument = 19, RULE_typeVariable = 20, RULE_typeRefWithModifiers = 21, 
		RULE_parameterizedTypeRef = 22, RULE_typeName = 23, RULE_typeGeneric = 24, 
		RULE_typeArgumentList = 25, RULE_typeArgument = 26, RULE_typeArguments = 27, 
		RULE_objectLiteralTypeRef = 28, RULE_thisTypeRef = 29, RULE_queryTypeRef = 30, 
		RULE_importTypeRef = 31, RULE_anonymousFormalParameterListWithDeclaredThisType = 32, 
		RULE_anonymousFormalParameter = 33, RULE_defaultFormalParameter = 34, 
		RULE_typePredicateWithOperatorTypeRef = 35, RULE_bindingIdentifier = 36, 
		RULE_propertyAccessExpressionInTypeRef = 37, RULE_inferTypeRef = 38, RULE_propertySignatur = 39, 
		RULE_constructSignature = 40, RULE_indexSignature = 41, RULE_indexSignatureElement = 42, 
		RULE_methodSignature = 43, RULE_typeAliasDeclaration = 44, RULE_interfaceDeclaration = 45, 
		RULE_interfaceExtendsClause = 46, RULE_classOrInterfaceTypeList = 47, 
		RULE_enumDeclaration = 48, RULE_enumBody = 49, RULE_enumMemberList = 50, 
		RULE_enumMember = 51, RULE_namespaceDeclaration = 52, RULE_namespaceName = 53, 
		RULE_moduleDeclaration = 54, RULE_moduleName = 55, RULE_decoratorList = 56, 
		RULE_decorator = 57, RULE_decoratorMemberExpression = 58, RULE_decoratorCallExpression = 59, 
		RULE_program = 60, RULE_statement = 61, RULE_declareStatement = 62, RULE_declarationStatement = 63, 
		RULE_block = 64, RULE_statementList = 65, RULE_importStatement = 66, RULE_importFromBlock = 67, 
		RULE_multipleImportElements = 68, RULE_importedElement = 69, RULE_importAliasDeclaration = 70, 
		RULE_exportStatement = 71, RULE_exportStatementTail = 72, RULE_exportFromBlock = 73, 
		RULE_variableStatement = 74, RULE_bindingPatternBlock = 75, RULE_variableDeclarationList = 76, 
		RULE_variableDeclaration = 77, RULE_emptyStatement = 78, RULE_expressionStatement = 79, 
		RULE_ifStatement = 80, RULE_iterationStatement = 81, RULE_varModifier = 82, 
		RULE_continueStatement = 83, RULE_breakStatement = 84, RULE_returnStatement = 85, 
		RULE_yieldStatement = 86, RULE_withStatement = 87, RULE_switchStatement = 88, 
		RULE_caseBlock = 89, RULE_caseClauses = 90, RULE_caseClause = 91, RULE_defaultClause = 92, 
		RULE_labelledStatement = 93, RULE_throwStatement = 94, RULE_tryStatement = 95, 
		RULE_catchProduction = 96, RULE_finallyProduction = 97, RULE_debuggerStatement = 98, 
		RULE_functionDeclaration = 99, RULE_classDeclaration = 100, RULE_classHeritage = 101, 
		RULE_classTail = 102, RULE_classElementList = 103, RULE_classExtendsClause = 104, 
		RULE_implementsClause = 105, RULE_classElement = 106, RULE_constructorDeclaration = 107, 
		RULE_propertyMemberDeclaration = 108, RULE_abstractDeclaration = 109, 
		RULE_propertyMemberBase = 110, RULE_generatorMethod = 111, RULE_generatorFunctionExpressionDeclaration = 112, 
		RULE_generatorBlock = 113, RULE_generatorDefinition = 114, RULE_iteratorBlock = 115, 
		RULE_iteratorDefinition = 116, RULE_callSignature = 117, RULE_parameterBlock = 118, 
		RULE_parameterListTrailingComma = 119, RULE_parameterList = 120, RULE_restParameter = 121, 
		RULE_parameter = 122, RULE_requiredParameter = 123, RULE_optionalParameter = 124, 
		RULE_accessibilityModifier = 125, RULE_identifierOrPattern = 126, RULE_arrayLiteral = 127, 
		RULE_elementList = 128, RULE_arrayElement = 129, RULE_bindingElement = 130, 
		RULE_typeBody = 131, RULE_typeMemberList = 132, RULE_typeMember = 133, 
		RULE_objectLiteral = 134, RULE_propertyAssignment = 135, RULE_getAccessor = 136, 
		RULE_setAccessor = 137, RULE_propertyName = 138, RULE_arguments = 139, 
		RULE_argumentList = 140, RULE_argument = 141, RULE_expressionSequence = 142, 
		RULE_functionExpressionDeclaration = 143, RULE_singleExpression = 144, 
		RULE_arrowFunctionDeclaration = 145, RULE_arrowFunctionParameters = 146, 
		RULE_arrowFunctionBody = 147, RULE_assignmentOperator = 148, RULE_literal = 149, 
		RULE_templateStringLiteral = 150, RULE_templateStringAtom = 151, RULE_numericLiteral = 152, 
		RULE_identifierOrKeyWord = 153, RULE_identifierName = 154, RULE_reservedWord = 155, 
		RULE_typeReferenceName = 156, RULE_keyword = 157, RULE_keywordAllowedInTypeReferences = 158, 
		RULE_getter = 159, RULE_setter = 160, RULE_eos = 161;
	private static String[] makeRuleNames() {
		return new String[] {
			"initializer", "bindingPattern", "typeParameters", "typeParameterList", 
			"typeParameter", "constraint", "defaultType", "colonSepTypeRef", "typeRef", 
			"conditionalTypeRef", "unionTypeExpression", "intersectionTypeExpression", 
			"operatorTypeRef", "typeOperator", "arrayTypeExpression", "primaryTypeExpression", 
			"literalType", "arrowFunctionTypeExpression", "tupleTypeExpression", 
			"tupleTypeArgument", "typeVariable", "typeRefWithModifiers", "parameterizedTypeRef", 
			"typeName", "typeGeneric", "typeArgumentList", "typeArgument", "typeArguments", 
			"objectLiteralTypeRef", "thisTypeRef", "queryTypeRef", "importTypeRef", 
			"anonymousFormalParameterListWithDeclaredThisType", "anonymousFormalParameter", 
			"defaultFormalParameter", "typePredicateWithOperatorTypeRef", "bindingIdentifier", 
			"propertyAccessExpressionInTypeRef", "inferTypeRef", "propertySignatur", 
			"constructSignature", "indexSignature", "indexSignatureElement", "methodSignature", 
			"typeAliasDeclaration", "interfaceDeclaration", "interfaceExtendsClause", 
			"classOrInterfaceTypeList", "enumDeclaration", "enumBody", "enumMemberList", 
			"enumMember", "namespaceDeclaration", "namespaceName", "moduleDeclaration", 
			"moduleName", "decoratorList", "decorator", "decoratorMemberExpression", 
			"decoratorCallExpression", "program", "statement", "declareStatement", 
			"declarationStatement", "block", "statementList", "importStatement", 
			"importFromBlock", "multipleImportElements", "importedElement", "importAliasDeclaration", 
			"exportStatement", "exportStatementTail", "exportFromBlock", "variableStatement", 
			"bindingPatternBlock", "variableDeclarationList", "variableDeclaration", 
			"emptyStatement", "expressionStatement", "ifStatement", "iterationStatement", 
			"varModifier", "continueStatement", "breakStatement", "returnStatement", 
			"yieldStatement", "withStatement", "switchStatement", "caseBlock", "caseClauses", 
			"caseClause", "defaultClause", "labelledStatement", "throwStatement", 
			"tryStatement", "catchProduction", "finallyProduction", "debuggerStatement", 
			"functionDeclaration", "classDeclaration", "classHeritage", "classTail", 
			"classElementList", "classExtendsClause", "implementsClause", "classElement", 
			"constructorDeclaration", "propertyMemberDeclaration", "abstractDeclaration", 
			"propertyMemberBase", "generatorMethod", "generatorFunctionExpressionDeclaration", 
			"generatorBlock", "generatorDefinition", "iteratorBlock", "iteratorDefinition", 
			"callSignature", "parameterBlock", "parameterListTrailingComma", "parameterList", 
			"restParameter", "parameter", "requiredParameter", "optionalParameter", 
			"accessibilityModifier", "identifierOrPattern", "arrayLiteral", "elementList", 
			"arrayElement", "bindingElement", "typeBody", "typeMemberList", "typeMember", 
			"objectLiteral", "propertyAssignment", "getAccessor", "setAccessor", 
			"propertyName", "arguments", "argumentList", "argument", "expressionSequence", 
			"functionExpressionDeclaration", "singleExpression", "arrowFunctionDeclaration", 
			"arrowFunctionParameters", "arrowFunctionBody", "assignmentOperator", 
			"literal", "templateStringLiteral", "templateStringAtom", "numericLiteral", 
			"identifierOrKeyWord", "identifierName", "reservedWord", "typeReferenceName", 
			"keyword", "keywordAllowedInTypeReferences", "getter", "setter", "eos"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'['", "']'", "'('", "')'", "'{'", "'}'", "';'", 
			"','", "'='", "'?'", "':'", "'...'", "'.'", "'++'", "'--'", "'+'", "'-'", 
			"'~'", "'!'", "'*'", "'/'", "'%'", "'<<'", "'<'", "'>'", "'<='", "'>='", 
			"'=='", "'!='", "'==='", "'!=='", "'&'", "'^'", "'|'", "'&&'", "'||'", 
			"'*='", "'/='", "'%='", "'+='", "'-='", "'<<='", "'>>='", "'>>>='", "'&='", 
			"'^='", "'|='", "'=>'", "'null'", "'undefined'", null, null, null, null, 
			null, null, "'break'", "'do'", "'instanceof'", "'typeof'", "'unique'", 
			"'keyof'", "'case'", "'else'", "'new'", "'var'", "'catch'", "'finally'", 
			"'return'", "'void'", "'continue'", "'for'", "'switch'", "'while'", "'debugger'", 
			"'function'", "'this'", "'with'", "'default'", "'if'", "'throw'", "'delete'", 
			"'in'", "'try'", "'as'", "'from'", "'readonly'", "'async'", "'class'", 
			"'enum'", "'extends'", "'super'", "'const'", "'export'", "'import'", 
			"'implements'", "'let'", "'private'", "'public'", "'interface'", "'package'", 
			"'protected'", "'static'", "'yield'", "'any'", "'number'", "'boolean'", 
			"'string'", "'symbol'", "'type'", "'get'", "'set'", "'constructor'", 
			"'namespace'", "'require'", "'module'", "'declare'", "'abstract'", "'is'", 
			"'infer'", "'never'", "'unknown'", "'asserts'", "'@'", null, null, null, 
			null, null, null, null, null, "'${'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "MultiLineComment", "SingleLineComment", "RegularExpressionLiteral", 
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
		enterRule(_localctx, 0, RULE_initializer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(Assign);
			setState(325);
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
		enterRule(_localctx, 2, RULE_bindingPattern);
		try {
			setState(329);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
				enterOuterAlt(_localctx, 1);
				{
				setState(327);
				arrayLiteral();
				}
				break;
			case OpenBrace:
				enterOuterAlt(_localctx, 2);
				{
				setState(328);
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
		enterRule(_localctx, 4, RULE_typeParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			match(LessThan);
			setState(333);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 52)) & ~0x3f) == 0 && ((1L << (_la - 52)) & ((1L << (NullLiteral - 52)) | (1L << (UndefinedLiteral - 52)) | (1L << (BooleanLiteral - 52)) | (1L << (Break - 52)) | (1L << (Do - 52)) | (1L << (Instanceof - 52)) | (1L << (Typeof - 52)) | (1L << (Unique - 52)) | (1L << (Case - 52)) | (1L << (Else - 52)) | (1L << (New - 52)) | (1L << (Var - 52)) | (1L << (Catch - 52)) | (1L << (Finally - 52)) | (1L << (Return - 52)) | (1L << (Void - 52)) | (1L << (Continue - 52)) | (1L << (For - 52)) | (1L << (Switch - 52)) | (1L << (While - 52)) | (1L << (Debugger - 52)) | (1L << (Function - 52)) | (1L << (This - 52)) | (1L << (With - 52)) | (1L << (Default - 52)) | (1L << (If - 52)) | (1L << (Throw - 52)) | (1L << (Delete - 52)) | (1L << (In - 52)) | (1L << (Try - 52)) | (1L << (As - 52)) | (1L << (From - 52)) | (1L << (ReadOnly - 52)) | (1L << (Async - 52)) | (1L << (Class - 52)) | (1L << (Enum - 52)) | (1L << (Extends - 52)) | (1L << (Super - 52)) | (1L << (Const - 52)) | (1L << (Export - 52)) | (1L << (Import - 52)) | (1L << (Implements - 52)) | (1L << (Let - 52)) | (1L << (Private - 52)) | (1L << (Public - 52)) | (1L << (Interface - 52)) | (1L << (Package - 52)) | (1L << (Protected - 52)) | (1L << (Static - 52)) | (1L << (Yield - 52)) | (1L << (Any - 52)) | (1L << (Number - 52)) | (1L << (Boolean - 52)) | (1L << (String - 52)) | (1L << (Symbol - 52)) | (1L << (TypeAlias - 52)) | (1L << (Get - 52)) | (1L << (Set - 52)))) != 0) || ((((_la - 116)) & ~0x3f) == 0 && ((1L << (_la - 116)) & ((1L << (Constructor - 116)) | (1L << (Namespace - 116)) | (1L << (Require - 116)) | (1L << (Module - 116)) | (1L << (Declare - 116)) | (1L << (Abstract - 116)) | (1L << (Is - 116)) | (1L << (Infer - 116)) | (1L << (Never - 116)) | (1L << (Unknown - 116)) | (1L << (Asserts - 116)) | (1L << (Identifier - 116)))) != 0)) {
				{
				setState(332);
				typeParameterList();
				}
			}

			setState(335);
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
		enterRule(_localctx, 6, RULE_typeParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			typeParameter();
			setState(342);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(338);
				match(Comma);
				setState(339);
				typeParameter();
				}
				}
				setState(344);
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
		enterRule(_localctx, 8, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(345);
			identifierName();
			setState(347);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(346);
				constraint();
				}
			}

			setState(351);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(349);
				match(Assign);
				setState(350);
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
		enterRule(_localctx, 10, RULE_constraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			match(Extends);
			setState(354);
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
		enterRule(_localctx, 12, RULE_defaultType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
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
		enterRule(_localctx, 14, RULE_colonSepTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(358);
			match(Colon);
			setState(359);
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
		enterRule(_localctx, 16, RULE_typeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
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
		enterRule(_localctx, 18, RULE_conditionalTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			unionTypeExpression();
			setState(371);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(364);
				match(Extends);
				setState(365);
				unionTypeExpression();
				setState(366);
				match(QuestionMark);
				setState(367);
				conditionalTypeRef();
				setState(368);
				match(Colon);
				setState(369);
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
		enterRule(_localctx, 20, RULE_unionTypeExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(374);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BitOr) {
				{
				setState(373);
				match(BitOr);
				}
			}

			setState(376);
			intersectionTypeExpression();
			setState(381);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(377);
					match(BitOr);
					setState(378);
					intersectionTypeExpression();
					}
					} 
				}
				setState(383);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
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
		enterRule(_localctx, 22, RULE_intersectionTypeExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BitAnd) {
				{
				setState(384);
				match(BitAnd);
				}
			}

			setState(387);
			operatorTypeRef();
			setState(392);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(388);
					match(BitAnd);
					setState(389);
					operatorTypeRef();
					}
					} 
				}
				setState(394);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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
		enterRule(_localctx, 24, RULE_operatorTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(395);
				typeOperator();
				}
				break;
			}
			setState(398);
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
		enterRule(_localctx, 26, RULE_typeOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(400);
			_la = _input.LA(1);
			if ( !(((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Unique - 64)) | (1L << (Keyof - 64)) | (1L << (ReadOnly - 64)))) != 0)) ) {
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
		enterRule(_localctx, 28, RULE_arrayTypeExpression);
		try {
			int _alt;
			setState(436);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(402);
				match(QuestionMark);
				setState(403);
				match(OpenBracket);
				setState(404);
				match(CloseBracket);
				setState(409);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(405);
						match(OpenBracket);
						setState(406);
						match(CloseBracket);
						}
						} 
					}
					setState(411);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(412);
				match(OpenParen);
				setState(413);
				match(QuestionMark);
				setState(414);
				match(CloseParen);
				setState(415);
				match(OpenBracket);
				setState(416);
				match(CloseBracket);
				setState(421);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(417);
						match(OpenBracket);
						setState(418);
						match(CloseBracket);
						}
						} 
					}
					setState(423);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(424);
				primaryTypeExpression();
				setState(433);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						setState(431);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
						case 1:
							{
							{
							setState(425);
							match(OpenBracket);
							setState(426);
							match(CloseBracket);
							}
							}
							break;
						case 2:
							{
							{
							setState(427);
							match(OpenBracket);
							setState(428);
							typeRef();
							setState(429);
							match(CloseBracket);
							}
							}
							break;
						}
						} 
					}
					setState(435);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
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
		enterRule(_localctx, 30, RULE_primaryTypeExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(449);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(438);
				literalType();
				}
				break;
			case 2:
				{
				setState(439);
				arrowFunctionTypeExpression();
				}
				break;
			case 3:
				{
				setState(440);
				tupleTypeExpression();
				}
				break;
			case 4:
				{
				setState(441);
				queryTypeRef();
				}
				break;
			case 5:
				{
				setState(442);
				importTypeRef();
				}
				break;
			case 6:
				{
				setState(443);
				inferTypeRef();
				}
				break;
			case 7:
				{
				setState(444);
				typeRefWithModifiers();
				}
				break;
			case 8:
				{
				setState(445);
				match(OpenParen);
				setState(446);
				typeRef();
				setState(447);
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
		enterRule(_localctx, 32, RULE_literalType);
		try {
			setState(454);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BooleanLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(451);
				match(BooleanLiteral);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(452);
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
				setState(453);
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
		enterRule(_localctx, 34, RULE_arrowFunctionTypeExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(460);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==New || _la==Abstract) {
				{
				setState(457);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Abstract) {
					{
					setState(456);
					match(Abstract);
					}
				}

				setState(459);
				match(New);
				}
			}

			setState(476);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(462);
				match(LessThan);
				setState(463);
				typeVariable();
				setState(468);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(464);
						match(Comma);
						setState(465);
						typeVariable();
						}
						} 
					}
					setState(470);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
				setState(472);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(471);
					match(Comma);
					}
				}

				setState(474);
				match(MoreThan);
				}
			}

			setState(478);
			match(OpenParen);
			setState(479);
			anonymousFormalParameterListWithDeclaredThisType();
			setState(480);
			match(CloseParen);
			setState(481);
			match(ARROW);
			}
			setState(485);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(483);
				typePredicateWithOperatorTypeRef();
				}
				break;
			case 2:
				{
				setState(484);
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
			setState(487);
			match(OpenBracket);
			setState(502);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CloseBracket:
				{
				setState(488);
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
				setState(489);
				tupleTypeArgument();
				setState(494);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(490);
						match(Comma);
						setState(491);
						tupleTypeArgument();
						}
						} 
					}
					setState(496);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				}
				setState(498);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(497);
					match(Comma);
					}
				}

				setState(500);
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
			setState(505);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(504);
				match(Ellipsis);
				}
			}

			setState(508);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(507);
				match(Infer);
				}
				break;
			}
			setState(512);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(510);
				match(Identifier);
				setState(511);
				match(Colon);
				}
				break;
			}
			setState(514);
			typeRef();
			setState(516);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(515);
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
			setState(518);
			match(Identifier);
			setState(521);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(519);
				match(Extends);
				setState(520);
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
		enterRule(_localctx, 42, RULE_typeRefWithModifiers);
		try {
			setState(526);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(523);
				parameterizedTypeRef();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(524);
				objectLiteralTypeRef();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(525);
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
		enterRule(_localctx, 44, RULE_parameterizedTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(528);
			typeName();
			setState(530);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(529);
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
		enterRule(_localctx, 46, RULE_typeName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(532);
			typeReferenceName();
			setState(537);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(533);
					match(Dot);
					setState(534);
					typeReferenceName();
					}
					} 
				}
				setState(539);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
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
		enterRule(_localctx, 48, RULE_typeGeneric);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(540);
			match(LessThan);
			setState(541);
			typeArgumentList();
			setState(543);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(542);
				match(Comma);
				}
			}

			setState(545);
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
		enterRule(_localctx, 50, RULE_typeArgumentList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(547);
			typeArgument();
			setState(552);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(548);
					match(Comma);
					setState(549);
					typeArgument();
					}
					} 
				}
				setState(554);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
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
		enterRule(_localctx, 52, RULE_typeArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(556);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(555);
				match(Infer);
				}
				break;
			}
			setState(558);
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
		enterRule(_localctx, 54, RULE_typeArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(560);
			match(LessThan);
			setState(562);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (OpenBrace - 4)) | (1L << (QuestionMark - 4)) | (1L << (Minus - 4)) | (1L << (LessThan - 4)) | (1L << (BitAnd - 4)) | (1L << (BitOr - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Keyof - 4)) | (1L << (Case - 4)) | (1L << (Else - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)))) != 0)) {
				{
				setState(561);
				typeArgumentList();
				}
			}

			setState(564);
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
		public TypeBodyContext typeBody() {
			return getRuleContext(TypeBodyContext.class,0);
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
			setState(566);
			match(OpenBrace);
			setState(568);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (Plus - 4)) | (1L << (Minus - 4)) | (1L << (LessThan - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)) | (1L << (Else - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)))) != 0)) {
				{
				setState(567);
				typeBody();
				}
			}

			setState(570);
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
			setState(572);
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
			setState(574);
			match(Typeof);
			setState(575);
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
			setState(577);
			match(Import);
			setState(578);
			match(OpenParen);
			setState(579);
			match(StringLiteral);
			setState(580);
			match(CloseParen);
			setState(583);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				{
				setState(581);
				match(Dot);
				setState(582);
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
		enterRule(_localctx, 64, RULE_anonymousFormalParameterListWithDeclaredThisType);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(600);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (OpenBrace - 4)) | (1L << (QuestionMark - 4)) | (1L << (Ellipsis - 4)) | (1L << (Minus - 4)) | (1L << (LessThan - 4)) | (1L << (BitAnd - 4)) | (1L << (BitOr - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Keyof - 4)) | (1L << (Case - 4)) | (1L << (Else - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)))) != 0)) {
				{
				setState(588);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
				case 1:
					{
					setState(585);
					match(This);
					setState(586);
					colonSepTypeRef();
					}
					break;
				case 2:
					{
					setState(587);
					anonymousFormalParameter();
					}
					break;
				}
				setState(594);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(590);
						match(Comma);
						setState(591);
						anonymousFormalParameter();
						}
						} 
					}
					setState(596);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
				}
				setState(598);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(597);
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
		enterRule(_localctx, 66, RULE_anonymousFormalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(603);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(602);
				match(Ellipsis);
				}
			}

			setState(612);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				{
				{
				setState(605);
				bindingIdentifier();
				setState(607);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==QuestionMark) {
					{
					setState(606);
					match(QuestionMark);
					}
				}

				setState(609);
				colonSepTypeRef();
				}
				}
				break;
			case 2:
				{
				setState(611);
				typeRef();
				}
				break;
			}
			setState(615);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(614);
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
		enterRule(_localctx, 68, RULE_defaultFormalParameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(617);
			match(Assign);
			setState(618);
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
		enterRule(_localctx, 70, RULE_typePredicateWithOperatorTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(621);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				{
				setState(620);
				match(Asserts);
				}
				break;
			}
			setState(625);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(623);
				match(This);
				}
				break;
			case 2:
				{
				setState(624);
				bindingIdentifier();
				}
				break;
			}
			setState(627);
			match(Is);
			setState(628);
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
		enterRule(_localctx, 72, RULE_bindingIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(630);
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
		enterRule(_localctx, 74, RULE_propertyAccessExpressionInTypeRef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(632);
			typeReferenceName();
			setState(637);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(633);
					match(Dot);
					setState(634);
					typeReferenceName();
					}
					} 
				}
				setState(639);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
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
		enterRule(_localctx, 76, RULE_inferTypeRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(640);
			match(Infer);
			setState(641);
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

	public static class PropertySignaturContext extends ParserRuleContext {
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
		public PropertySignaturContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertySignatur; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterPropertySignatur(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitPropertySignatur(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitPropertySignatur(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertySignaturContext propertySignatur() throws RecognitionException {
		PropertySignaturContext _localctx = new PropertySignaturContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_propertySignatur);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(644);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
			case 1:
				{
				setState(643);
				match(ReadOnly);
				}
				break;
			}
			setState(646);
			propertyName();
			setState(648);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(647);
				match(QuestionMark);
				}
			}

			setState(651);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(650);
				colonSepTypeRef();
				}
			}

			setState(655);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ARROW) {
				{
				setState(653);
				match(ARROW);
				setState(654);
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
		enterRule(_localctx, 80, RULE_constructSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(657);
			match(New);
			setState(659);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(658);
				typeParameters();
				}
			}

			setState(661);
			parameterBlock();
			setState(663);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(662);
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
		enterRule(_localctx, 82, RULE_indexSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(666);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
			case 1:
				{
				setState(665);
				match(ReadOnly);
				}
				break;
			}
			setState(674);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Plus:
			case ReadOnly:
				{
				setState(669);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Plus) {
					{
					setState(668);
					match(Plus);
					}
				}

				setState(671);
				match(ReadOnly);
				}
				break;
			case Minus:
				{
				setState(672);
				match(Minus);
				setState(673);
				match(ReadOnly);
				}
				break;
			case OpenBracket:
				break;
			default:
				break;
			}
			setState(676);
			match(OpenBracket);
			setState(677);
			indexSignatureElement();
			setState(678);
			match(CloseBracket);
			setState(685);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QuestionMark:
			case Plus:
				{
				setState(680);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Plus) {
					{
					setState(679);
					match(Plus);
					}
				}

				setState(682);
				match(QuestionMark);
				}
				break;
			case Minus:
				{
				setState(683);
				match(Minus);
				setState(684);
				match(QuestionMark);
				}
				break;
			case Colon:
				break;
			default:
				break;
			}
			setState(687);
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
		enterRule(_localctx, 84, RULE_indexSignatureElement);
		int _la;
		try {
			setState(696);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(689);
				identifierName();
				setState(690);
				match(Colon);
				setState(691);
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
				setState(693);
				match(Identifier);
				setState(694);
				match(In);
				setState(695);
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
		enterRule(_localctx, 86, RULE_methodSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(698);
			propertyName();
			setState(700);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QuestionMark) {
				{
				setState(699);
				match(QuestionMark);
				}
			}

			setState(702);
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
		enterRule(_localctx, 88, RULE_typeAliasDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(704);
			match(TypeAlias);
			setState(705);
			identifierName();
			setState(707);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(706);
				typeParameters();
				}
			}

			setState(709);
			match(Assign);
			setState(710);
			typeRef();
			setState(712);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				{
				setState(711);
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
		public TypeBodyContext typeBody() {
			return getRuleContext(TypeBodyContext.class,0);
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
		enterRule(_localctx, 90, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(714);
			match(Interface);
			setState(715);
			identifierName();
			setState(717);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(716);
				typeParameters();
				}
			}

			setState(720);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(719);
				interfaceExtendsClause();
				}
			}

			setState(722);
			match(OpenBrace);
			setState(724);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (OpenBracket - 4)) | (1L << (OpenParen - 4)) | (1L << (Plus - 4)) | (1L << (Minus - 4)) | (1L << (LessThan - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)) | (1L << (Else - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)))) != 0)) {
				{
				setState(723);
				typeBody();
				}
			}

			setState(726);
			match(CloseBrace);
			setState(728);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				{
				setState(727);
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
		enterRule(_localctx, 92, RULE_interfaceExtendsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(730);
			match(Extends);
			setState(731);
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
		enterRule(_localctx, 94, RULE_classOrInterfaceTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(733);
			parameterizedTypeRef();
			setState(738);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(734);
				match(Comma);
				setState(735);
				parameterizedTypeRef();
				}
				}
				setState(740);
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
		enterRule(_localctx, 96, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(742);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Const) {
				{
				setState(741);
				match(Const);
				}
			}

			setState(744);
			match(Enum);
			setState(745);
			match(Identifier);
			setState(746);
			match(OpenBrace);
			setState(748);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (OpenBracket - 4)) | (1L << (Minus - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)) | (1L << (Else - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)))) != 0)) {
				{
				setState(747);
				enumBody();
				}
			}

			setState(750);
			match(CloseBrace);
			setState(752);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(751);
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
		enterRule(_localctx, 98, RULE_enumBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(754);
			enumMemberList();
			setState(756);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(755);
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
		enterRule(_localctx, 100, RULE_enumMemberList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(758);
			enumMember();
			setState(763);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(759);
					match(Comma);
					setState(760);
					enumMember();
					}
					} 
				}
				setState(765);
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
		enterRule(_localctx, 102, RULE_enumMember);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(766);
			propertyName();
			setState(769);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(767);
				match(Assign);
				setState(768);
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
		enterRule(_localctx, 104, RULE_namespaceDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(771);
			match(Namespace);
			setState(772);
			namespaceName();
			setState(773);
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
		enterRule(_localctx, 106, RULE_namespaceName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(775);
			typeReferenceName();
			setState(780);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,78,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(776);
					match(Dot);
					setState(777);
					typeReferenceName();
					}
					} 
				}
				setState(782);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,78,_ctx);
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
		enterRule(_localctx, 108, RULE_moduleDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(783);
			match(Module);
			setState(784);
			moduleName();
			setState(785);
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
		enterRule(_localctx, 110, RULE_moduleName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(787);
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
		enterRule(_localctx, 112, RULE_decoratorList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(790); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(789);
					decorator();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(792); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,79,_ctx);
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
		enterRule(_localctx, 114, RULE_decorator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(794);
			match(At);
			setState(797);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,80,_ctx) ) {
			case 1:
				{
				setState(795);
				decoratorMemberExpression(0);
				}
				break;
			case 2:
				{
				setState(796);
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
		int _startState = 116;
		enterRecursionRule(_localctx, 116, RULE_decoratorMemberExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(805);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(800);
				match(Identifier);
				}
				break;
			case OpenParen:
				{
				setState(801);
				match(OpenParen);
				setState(802);
				singleExpression(0);
				setState(803);
				match(CloseParen);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(812);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,82,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new DecoratorMemberExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_decoratorMemberExpression);
					setState(807);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(808);
					match(Dot);
					setState(809);
					identifierName();
					}
					} 
				}
				setState(814);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,82,_ctx);
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
		enterRule(_localctx, 118, RULE_decoratorCallExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(815);
			decoratorMemberExpression(0);
			setState(816);
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
		enterRule(_localctx, 120, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(819);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (Var - 69)) | (1L << (Return - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (With - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Try - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Protected - 69)) | (1L << (Yield - 69)) | (1L << (TypeAlias - 69)) | (1L << (Namespace - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(818);
				statementList();
				}
			}

			setState(821);
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
		enterRule(_localctx, 122, RULE_statement);
		try {
			setState(842);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(823);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(824);
				importStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(825);
				decoratorList();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(826);
				exportStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(827);
				declareStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(828);
				arrowFunctionDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(829);
				ifStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(830);
				iterationStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(831);
				continueStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(832);
				breakStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(833);
				returnStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(834);
				yieldStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(835);
				withStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(836);
				labelledStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(837);
				switchStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(838);
				throwStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(839);
				tryStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(840);
				debuggerStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(841);
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
		enterRule(_localctx, 124, RULE_declareStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(845);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Declare) {
				{
				setState(844);
				match(Declare);
				}
			}

			setState(847);
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
		enterRule(_localctx, 126, RULE_declarationStatement);
		try {
			setState(857);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(849);
				moduleDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(850);
				namespaceDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(851);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(852);
				typeAliasDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(853);
				functionDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(854);
				classDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(855);
				enumDeclaration();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(856);
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
		enterRule(_localctx, 128, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(859);
			match(OpenBrace);
			setState(861);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (Var - 69)) | (1L << (Return - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (With - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Try - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Protected - 69)) | (1L << (Yield - 69)) | (1L << (TypeAlias - 69)) | (1L << (Namespace - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(860);
				statementList();
				}
			}

			setState(863);
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
		enterRule(_localctx, 130, RULE_statementList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(866); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(865);
				statement();
				}
				}
				setState(868); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (Var - 69)) | (1L << (Return - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (With - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Try - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Protected - 69)) | (1L << (Yield - 69)) | (1L << (TypeAlias - 69)) | (1L << (Namespace - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)))) != 0) );
			}
		}
		catch (RecognitionException re) {
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
		enterRule(_localctx, 132, RULE_importStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(870);
			match(Import);
			setState(874);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,89,_ctx) ) {
			case 1:
				{
				setState(871);
				importFromBlock();
				}
				break;
			case 2:
				{
				setState(872);
				importAliasDeclaration();
				}
				break;
			case 3:
				{
				setState(873);
				match(StringLiteral);
				}
				break;
			}
			setState(876);
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
		enterRule(_localctx, 134, RULE_importFromBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(879);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
			case 1:
				{
				setState(878);
				match(TypeAlias);
				}
				break;
			}
			setState(886);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,91,_ctx) ) {
			case 1:
				{
				setState(881);
				match(Multiply);
				setState(882);
				match(As);
				setState(883);
				identifierName();
				}
				break;
			case 2:
				{
				setState(884);
				identifierName();
				}
				break;
			case 3:
				{
				setState(885);
				multipleImportElements();
				}
				break;
			}
			setState(888);
			match(From);
			setState(889);
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
		enterRule(_localctx, 136, RULE_multipleImportElements);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(894);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 52)) & ~0x3f) == 0 && ((1L << (_la - 52)) & ((1L << (NullLiteral - 52)) | (1L << (UndefinedLiteral - 52)) | (1L << (BooleanLiteral - 52)) | (1L << (Break - 52)) | (1L << (Do - 52)) | (1L << (Instanceof - 52)) | (1L << (Typeof - 52)) | (1L << (Unique - 52)) | (1L << (Case - 52)) | (1L << (Else - 52)) | (1L << (New - 52)) | (1L << (Var - 52)) | (1L << (Catch - 52)) | (1L << (Finally - 52)) | (1L << (Return - 52)) | (1L << (Void - 52)) | (1L << (Continue - 52)) | (1L << (For - 52)) | (1L << (Switch - 52)) | (1L << (While - 52)) | (1L << (Debugger - 52)) | (1L << (Function - 52)) | (1L << (This - 52)) | (1L << (With - 52)) | (1L << (Default - 52)) | (1L << (If - 52)) | (1L << (Throw - 52)) | (1L << (Delete - 52)) | (1L << (In - 52)) | (1L << (Try - 52)) | (1L << (As - 52)) | (1L << (From - 52)) | (1L << (ReadOnly - 52)) | (1L << (Async - 52)) | (1L << (Class - 52)) | (1L << (Enum - 52)) | (1L << (Extends - 52)) | (1L << (Super - 52)) | (1L << (Const - 52)) | (1L << (Export - 52)) | (1L << (Import - 52)) | (1L << (Implements - 52)) | (1L << (Let - 52)) | (1L << (Private - 52)) | (1L << (Public - 52)) | (1L << (Interface - 52)) | (1L << (Package - 52)) | (1L << (Protected - 52)) | (1L << (Static - 52)) | (1L << (Yield - 52)) | (1L << (Any - 52)) | (1L << (Number - 52)) | (1L << (Boolean - 52)) | (1L << (String - 52)) | (1L << (Symbol - 52)) | (1L << (TypeAlias - 52)) | (1L << (Get - 52)) | (1L << (Set - 52)))) != 0) || ((((_la - 116)) & ~0x3f) == 0 && ((1L << (_la - 116)) & ((1L << (Constructor - 116)) | (1L << (Namespace - 116)) | (1L << (Require - 116)) | (1L << (Module - 116)) | (1L << (Declare - 116)) | (1L << (Abstract - 116)) | (1L << (Is - 116)) | (1L << (Infer - 116)) | (1L << (Never - 116)) | (1L << (Unknown - 116)) | (1L << (Asserts - 116)) | (1L << (Identifier - 116)))) != 0)) {
				{
				setState(891);
				identifierName();
				setState(892);
				match(Comma);
				}
			}

			setState(896);
			match(OpenBrace);
			setState(897);
			importedElement();
			setState(902);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(898);
					match(Comma);
					setState(899);
					importedElement();
					}
					} 
				}
				setState(904);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
			}
			setState(906);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(905);
				match(Comma);
				}
			}

			setState(908);
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
		enterRule(_localctx, 138, RULE_importedElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(910);
			identifierName();
			setState(913);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==As) {
				{
				setState(911);
				match(As);
				setState(912);
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
		enterRule(_localctx, 140, RULE_importAliasDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(915);
			match(Identifier);
			setState(916);
			match(Assign);
			setState(922);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,96,_ctx) ) {
			case 1:
				{
				setState(917);
				namespaceName();
				}
				break;
			case 2:
				{
				setState(918);
				match(Require);
				setState(919);
				match(OpenParen);
				setState(920);
				match(StringLiteral);
				setState(921);
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
		enterRule(_localctx, 142, RULE_exportStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(924);
			match(Export);
			setState(925);
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
		enterRule(_localctx, 144, RULE_exportStatementTail);
		int _la;
		try {
			setState(952);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,99,_ctx) ) {
			case 1:
				_localctx = new ExportAsNamespaceContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(927);
				match(As);
				setState(928);
				match(Namespace);
				setState(929);
				identifierName();
				setState(930);
				eos();
				}
				break;
			case 2:
				_localctx = new ExportEqualsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(932);
				match(Assign);
				setState(933);
				namespaceName();
				setState(934);
				eos();
				}
				break;
			case 3:
				_localctx = new ExportImportContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(936);
				match(Import);
				setState(937);
				identifierName();
				setState(938);
				match(Assign);
				setState(939);
				namespaceName();
				setState(940);
				eos();
				}
				break;
			case 4:
				_localctx = new ExportDeclareStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(943);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Default) {
					{
					setState(942);
					match(Default);
					}
				}

				setState(945);
				declareStatement();
				}
				break;
			case 5:
				_localctx = new ExportElementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(947);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,98,_ctx) ) {
				case 1:
					{
					setState(946);
					match(Default);
					}
					break;
				}
				setState(949);
				exportFromBlock();
				setState(950);
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
		enterRule(_localctx, 146, RULE_exportFromBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(961);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,101,_ctx) ) {
			case 1:
				{
				setState(954);
				match(Multiply);
				setState(957);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
				case 1:
					{
					setState(955);
					match(As);
					setState(956);
					identifierName();
					}
					break;
				}
				}
				break;
			case 2:
				{
				setState(959);
				identifierName();
				}
				break;
			case 3:
				{
				setState(960);
				multipleImportElements();
				}
				break;
			}
			setState(965);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,102,_ctx) ) {
			case 1:
				{
				setState(963);
				match(From);
				setState(964);
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
		enterRule(_localctx, 148, RULE_variableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(968);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 101)) & ~0x3f) == 0 && ((1L << (_la - 101)) & ((1L << (Private - 101)) | (1L << (Public - 101)) | (1L << (Protected - 101)))) != 0)) {
				{
				setState(967);
				accessibilityModifier();
				}
			}

			setState(971);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ReadOnly) {
				{
				setState(970);
				match(ReadOnly);
				}
			}

			setState(973);
			varModifier();
			setState(976);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
			case OpenBrace:
				{
				setState(974);
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
				setState(975);
				variableDeclarationList();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(979);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,106,_ctx) ) {
			case 1:
				{
				setState(978);
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
		enterRule(_localctx, 150, RULE_bindingPatternBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(981);
			bindingPattern();
			setState(983);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,107,_ctx) ) {
			case 1:
				{
				setState(982);
				colonSepTypeRef();
				}
				break;
			}
			setState(986);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
			case 1:
				{
				setState(985);
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
		enterRule(_localctx, 152, RULE_variableDeclarationList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(988);
			variableDeclaration();
			setState(993);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,109,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(989);
					match(Comma);
					setState(990);
					variableDeclaration();
					}
					} 
				}
				setState(995);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,109,_ctx);
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
		enterRule(_localctx, 154, RULE_variableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(996);
			identifierName();
			setState(998);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,110,_ctx) ) {
			case 1:
				{
				setState(997);
				colonSepTypeRef();
				}
				break;
			}
			setState(1005);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,112,_ctx) ) {
			case 1:
				{
				setState(1000);
				match(Assign);
				setState(1002);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,111,_ctx) ) {
				case 1:
					{
					setState(1001);
					typeParameters();
					}
					break;
				}
				setState(1004);
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
		enterRule(_localctx, 156, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1007);
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
		enterRule(_localctx, 158, RULE_expressionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1009);
			if (!(this.notOpenBraceAndNotFunction())) throw new FailedPredicateException(this, "this.notOpenBraceAndNotFunction()");
			setState(1010);
			expressionSequence();
			setState(1012);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon) {
				{
				setState(1011);
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
		enterRule(_localctx, 160, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1014);
			match(If);
			setState(1015);
			match(OpenParen);
			setState(1016);
			expressionSequence();
			setState(1017);
			match(CloseParen);
			setState(1018);
			statement();
			setState(1021);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,114,_ctx) ) {
			case 1:
				{
				setState(1019);
				match(Else);
				setState(1020);
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
		enterRule(_localctx, 162, RULE_iterationStatement);
		int _la;
		try {
			setState(1092);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,122,_ctx) ) {
			case 1:
				_localctx = new DoStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1023);
				match(Do);
				setState(1024);
				statement();
				setState(1025);
				match(While);
				setState(1026);
				match(OpenParen);
				setState(1027);
				expressionSequence();
				setState(1028);
				match(CloseParen);
				setState(1029);
				eos();
				}
				break;
			case 2:
				_localctx = new WhileStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1031);
				match(While);
				setState(1032);
				match(OpenParen);
				setState(1033);
				expressionSequence();
				setState(1034);
				match(CloseParen);
				setState(1035);
				statement();
				}
				break;
			case 3:
				_localctx = new ForStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1037);
				match(For);
				setState(1038);
				match(OpenParen);
				setState(1040);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (RegularExpressionLiteral - 3)) | (1L << (OpenBracket - 3)) | (1L << (OpenParen - 3)) | (1L << (OpenBrace - 3)) | (1L << (PlusPlus - 3)) | (1L << (MinusMinus - 3)) | (1L << (Plus - 3)) | (1L << (Minus - 3)) | (1L << (BitNot - 3)) | (1L << (Not - 3)) | (1L << (LessThan - 3)) | (1L << (NullLiteral - 3)) | (1L << (UndefinedLiteral - 3)) | (1L << (BooleanLiteral - 3)) | (1L << (DecimalLiteral - 3)) | (1L << (HexIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral2 - 3)) | (1L << (BinaryIntegerLiteral - 3)) | (1L << (Break - 3)) | (1L << (Do - 3)) | (1L << (Instanceof - 3)) | (1L << (Typeof - 3)) | (1L << (Unique - 3)) | (1L << (Case - 3)))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (Else - 67)) | (1L << (New - 67)) | (1L << (Var - 67)) | (1L << (Catch - 67)) | (1L << (Finally - 67)) | (1L << (Return - 67)) | (1L << (Void - 67)) | (1L << (Continue - 67)) | (1L << (For - 67)) | (1L << (Switch - 67)) | (1L << (While - 67)) | (1L << (Debugger - 67)) | (1L << (Function - 67)) | (1L << (This - 67)) | (1L << (With - 67)) | (1L << (Default - 67)) | (1L << (If - 67)) | (1L << (Throw - 67)) | (1L << (Delete - 67)) | (1L << (In - 67)) | (1L << (Try - 67)) | (1L << (As - 67)) | (1L << (From - 67)) | (1L << (ReadOnly - 67)) | (1L << (Async - 67)) | (1L << (Class - 67)) | (1L << (Enum - 67)) | (1L << (Extends - 67)) | (1L << (Super - 67)) | (1L << (Const - 67)) | (1L << (Export - 67)) | (1L << (Import - 67)) | (1L << (Implements - 67)) | (1L << (Let - 67)) | (1L << (Private - 67)) | (1L << (Public - 67)) | (1L << (Interface - 67)) | (1L << (Package - 67)) | (1L << (Protected - 67)) | (1L << (Static - 67)) | (1L << (Yield - 67)) | (1L << (Any - 67)) | (1L << (Number - 67)) | (1L << (Boolean - 67)) | (1L << (String - 67)) | (1L << (Symbol - 67)) | (1L << (TypeAlias - 67)) | (1L << (Get - 67)) | (1L << (Set - 67)) | (1L << (Constructor - 67)) | (1L << (Namespace - 67)) | (1L << (Require - 67)) | (1L << (Module - 67)) | (1L << (Declare - 67)) | (1L << (Abstract - 67)) | (1L << (Is - 67)) | (1L << (Infer - 67)) | (1L << (Never - 67)) | (1L << (Unknown - 67)) | (1L << (Asserts - 67)) | (1L << (Identifier - 67)) | (1L << (StringLiteral - 67)) | (1L << (BackTick - 67)))) != 0)) {
					{
					setState(1039);
					expressionSequence();
					}
				}

				setState(1042);
				match(SemiColon);
				setState(1044);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (RegularExpressionLiteral - 3)) | (1L << (OpenBracket - 3)) | (1L << (OpenParen - 3)) | (1L << (OpenBrace - 3)) | (1L << (PlusPlus - 3)) | (1L << (MinusMinus - 3)) | (1L << (Plus - 3)) | (1L << (Minus - 3)) | (1L << (BitNot - 3)) | (1L << (Not - 3)) | (1L << (LessThan - 3)) | (1L << (NullLiteral - 3)) | (1L << (UndefinedLiteral - 3)) | (1L << (BooleanLiteral - 3)) | (1L << (DecimalLiteral - 3)) | (1L << (HexIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral2 - 3)) | (1L << (BinaryIntegerLiteral - 3)) | (1L << (Break - 3)) | (1L << (Do - 3)) | (1L << (Instanceof - 3)) | (1L << (Typeof - 3)) | (1L << (Unique - 3)) | (1L << (Case - 3)))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (Else - 67)) | (1L << (New - 67)) | (1L << (Var - 67)) | (1L << (Catch - 67)) | (1L << (Finally - 67)) | (1L << (Return - 67)) | (1L << (Void - 67)) | (1L << (Continue - 67)) | (1L << (For - 67)) | (1L << (Switch - 67)) | (1L << (While - 67)) | (1L << (Debugger - 67)) | (1L << (Function - 67)) | (1L << (This - 67)) | (1L << (With - 67)) | (1L << (Default - 67)) | (1L << (If - 67)) | (1L << (Throw - 67)) | (1L << (Delete - 67)) | (1L << (In - 67)) | (1L << (Try - 67)) | (1L << (As - 67)) | (1L << (From - 67)) | (1L << (ReadOnly - 67)) | (1L << (Async - 67)) | (1L << (Class - 67)) | (1L << (Enum - 67)) | (1L << (Extends - 67)) | (1L << (Super - 67)) | (1L << (Const - 67)) | (1L << (Export - 67)) | (1L << (Import - 67)) | (1L << (Implements - 67)) | (1L << (Let - 67)) | (1L << (Private - 67)) | (1L << (Public - 67)) | (1L << (Interface - 67)) | (1L << (Package - 67)) | (1L << (Protected - 67)) | (1L << (Static - 67)) | (1L << (Yield - 67)) | (1L << (Any - 67)) | (1L << (Number - 67)) | (1L << (Boolean - 67)) | (1L << (String - 67)) | (1L << (Symbol - 67)) | (1L << (TypeAlias - 67)) | (1L << (Get - 67)) | (1L << (Set - 67)) | (1L << (Constructor - 67)) | (1L << (Namespace - 67)) | (1L << (Require - 67)) | (1L << (Module - 67)) | (1L << (Declare - 67)) | (1L << (Abstract - 67)) | (1L << (Is - 67)) | (1L << (Infer - 67)) | (1L << (Never - 67)) | (1L << (Unknown - 67)) | (1L << (Asserts - 67)) | (1L << (Identifier - 67)) | (1L << (StringLiteral - 67)) | (1L << (BackTick - 67)))) != 0)) {
					{
					setState(1043);
					expressionSequence();
					}
				}

				setState(1046);
				match(SemiColon);
				setState(1048);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (RegularExpressionLiteral - 3)) | (1L << (OpenBracket - 3)) | (1L << (OpenParen - 3)) | (1L << (OpenBrace - 3)) | (1L << (PlusPlus - 3)) | (1L << (MinusMinus - 3)) | (1L << (Plus - 3)) | (1L << (Minus - 3)) | (1L << (BitNot - 3)) | (1L << (Not - 3)) | (1L << (LessThan - 3)) | (1L << (NullLiteral - 3)) | (1L << (UndefinedLiteral - 3)) | (1L << (BooleanLiteral - 3)) | (1L << (DecimalLiteral - 3)) | (1L << (HexIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral2 - 3)) | (1L << (BinaryIntegerLiteral - 3)) | (1L << (Break - 3)) | (1L << (Do - 3)) | (1L << (Instanceof - 3)) | (1L << (Typeof - 3)) | (1L << (Unique - 3)) | (1L << (Case - 3)))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (Else - 67)) | (1L << (New - 67)) | (1L << (Var - 67)) | (1L << (Catch - 67)) | (1L << (Finally - 67)) | (1L << (Return - 67)) | (1L << (Void - 67)) | (1L << (Continue - 67)) | (1L << (For - 67)) | (1L << (Switch - 67)) | (1L << (While - 67)) | (1L << (Debugger - 67)) | (1L << (Function - 67)) | (1L << (This - 67)) | (1L << (With - 67)) | (1L << (Default - 67)) | (1L << (If - 67)) | (1L << (Throw - 67)) | (1L << (Delete - 67)) | (1L << (In - 67)) | (1L << (Try - 67)) | (1L << (As - 67)) | (1L << (From - 67)) | (1L << (ReadOnly - 67)) | (1L << (Async - 67)) | (1L << (Class - 67)) | (1L << (Enum - 67)) | (1L << (Extends - 67)) | (1L << (Super - 67)) | (1L << (Const - 67)) | (1L << (Export - 67)) | (1L << (Import - 67)) | (1L << (Implements - 67)) | (1L << (Let - 67)) | (1L << (Private - 67)) | (1L << (Public - 67)) | (1L << (Interface - 67)) | (1L << (Package - 67)) | (1L << (Protected - 67)) | (1L << (Static - 67)) | (1L << (Yield - 67)) | (1L << (Any - 67)) | (1L << (Number - 67)) | (1L << (Boolean - 67)) | (1L << (String - 67)) | (1L << (Symbol - 67)) | (1L << (TypeAlias - 67)) | (1L << (Get - 67)) | (1L << (Set - 67)) | (1L << (Constructor - 67)) | (1L << (Namespace - 67)) | (1L << (Require - 67)) | (1L << (Module - 67)) | (1L << (Declare - 67)) | (1L << (Abstract - 67)) | (1L << (Is - 67)) | (1L << (Infer - 67)) | (1L << (Never - 67)) | (1L << (Unknown - 67)) | (1L << (Asserts - 67)) | (1L << (Identifier - 67)) | (1L << (StringLiteral - 67)) | (1L << (BackTick - 67)))) != 0)) {
					{
					setState(1047);
					expressionSequence();
					}
				}

				setState(1050);
				match(CloseParen);
				setState(1051);
				statement();
				}
				break;
			case 4:
				_localctx = new ForVarStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1052);
				match(For);
				setState(1053);
				match(OpenParen);
				setState(1054);
				varModifier();
				setState(1055);
				variableDeclarationList();
				setState(1056);
				match(SemiColon);
				setState(1058);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (RegularExpressionLiteral - 3)) | (1L << (OpenBracket - 3)) | (1L << (OpenParen - 3)) | (1L << (OpenBrace - 3)) | (1L << (PlusPlus - 3)) | (1L << (MinusMinus - 3)) | (1L << (Plus - 3)) | (1L << (Minus - 3)) | (1L << (BitNot - 3)) | (1L << (Not - 3)) | (1L << (LessThan - 3)) | (1L << (NullLiteral - 3)) | (1L << (UndefinedLiteral - 3)) | (1L << (BooleanLiteral - 3)) | (1L << (DecimalLiteral - 3)) | (1L << (HexIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral2 - 3)) | (1L << (BinaryIntegerLiteral - 3)) | (1L << (Break - 3)) | (1L << (Do - 3)) | (1L << (Instanceof - 3)) | (1L << (Typeof - 3)) | (1L << (Unique - 3)) | (1L << (Case - 3)))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (Else - 67)) | (1L << (New - 67)) | (1L << (Var - 67)) | (1L << (Catch - 67)) | (1L << (Finally - 67)) | (1L << (Return - 67)) | (1L << (Void - 67)) | (1L << (Continue - 67)) | (1L << (For - 67)) | (1L << (Switch - 67)) | (1L << (While - 67)) | (1L << (Debugger - 67)) | (1L << (Function - 67)) | (1L << (This - 67)) | (1L << (With - 67)) | (1L << (Default - 67)) | (1L << (If - 67)) | (1L << (Throw - 67)) | (1L << (Delete - 67)) | (1L << (In - 67)) | (1L << (Try - 67)) | (1L << (As - 67)) | (1L << (From - 67)) | (1L << (ReadOnly - 67)) | (1L << (Async - 67)) | (1L << (Class - 67)) | (1L << (Enum - 67)) | (1L << (Extends - 67)) | (1L << (Super - 67)) | (1L << (Const - 67)) | (1L << (Export - 67)) | (1L << (Import - 67)) | (1L << (Implements - 67)) | (1L << (Let - 67)) | (1L << (Private - 67)) | (1L << (Public - 67)) | (1L << (Interface - 67)) | (1L << (Package - 67)) | (1L << (Protected - 67)) | (1L << (Static - 67)) | (1L << (Yield - 67)) | (1L << (Any - 67)) | (1L << (Number - 67)) | (1L << (Boolean - 67)) | (1L << (String - 67)) | (1L << (Symbol - 67)) | (1L << (TypeAlias - 67)) | (1L << (Get - 67)) | (1L << (Set - 67)) | (1L << (Constructor - 67)) | (1L << (Namespace - 67)) | (1L << (Require - 67)) | (1L << (Module - 67)) | (1L << (Declare - 67)) | (1L << (Abstract - 67)) | (1L << (Is - 67)) | (1L << (Infer - 67)) | (1L << (Never - 67)) | (1L << (Unknown - 67)) | (1L << (Asserts - 67)) | (1L << (Identifier - 67)) | (1L << (StringLiteral - 67)) | (1L << (BackTick - 67)))) != 0)) {
					{
					setState(1057);
					expressionSequence();
					}
				}

				setState(1060);
				match(SemiColon);
				setState(1062);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (RegularExpressionLiteral - 3)) | (1L << (OpenBracket - 3)) | (1L << (OpenParen - 3)) | (1L << (OpenBrace - 3)) | (1L << (PlusPlus - 3)) | (1L << (MinusMinus - 3)) | (1L << (Plus - 3)) | (1L << (Minus - 3)) | (1L << (BitNot - 3)) | (1L << (Not - 3)) | (1L << (LessThan - 3)) | (1L << (NullLiteral - 3)) | (1L << (UndefinedLiteral - 3)) | (1L << (BooleanLiteral - 3)) | (1L << (DecimalLiteral - 3)) | (1L << (HexIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral2 - 3)) | (1L << (BinaryIntegerLiteral - 3)) | (1L << (Break - 3)) | (1L << (Do - 3)) | (1L << (Instanceof - 3)) | (1L << (Typeof - 3)) | (1L << (Unique - 3)) | (1L << (Case - 3)))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (Else - 67)) | (1L << (New - 67)) | (1L << (Var - 67)) | (1L << (Catch - 67)) | (1L << (Finally - 67)) | (1L << (Return - 67)) | (1L << (Void - 67)) | (1L << (Continue - 67)) | (1L << (For - 67)) | (1L << (Switch - 67)) | (1L << (While - 67)) | (1L << (Debugger - 67)) | (1L << (Function - 67)) | (1L << (This - 67)) | (1L << (With - 67)) | (1L << (Default - 67)) | (1L << (If - 67)) | (1L << (Throw - 67)) | (1L << (Delete - 67)) | (1L << (In - 67)) | (1L << (Try - 67)) | (1L << (As - 67)) | (1L << (From - 67)) | (1L << (ReadOnly - 67)) | (1L << (Async - 67)) | (1L << (Class - 67)) | (1L << (Enum - 67)) | (1L << (Extends - 67)) | (1L << (Super - 67)) | (1L << (Const - 67)) | (1L << (Export - 67)) | (1L << (Import - 67)) | (1L << (Implements - 67)) | (1L << (Let - 67)) | (1L << (Private - 67)) | (1L << (Public - 67)) | (1L << (Interface - 67)) | (1L << (Package - 67)) | (1L << (Protected - 67)) | (1L << (Static - 67)) | (1L << (Yield - 67)) | (1L << (Any - 67)) | (1L << (Number - 67)) | (1L << (Boolean - 67)) | (1L << (String - 67)) | (1L << (Symbol - 67)) | (1L << (TypeAlias - 67)) | (1L << (Get - 67)) | (1L << (Set - 67)) | (1L << (Constructor - 67)) | (1L << (Namespace - 67)) | (1L << (Require - 67)) | (1L << (Module - 67)) | (1L << (Declare - 67)) | (1L << (Abstract - 67)) | (1L << (Is - 67)) | (1L << (Infer - 67)) | (1L << (Never - 67)) | (1L << (Unknown - 67)) | (1L << (Asserts - 67)) | (1L << (Identifier - 67)) | (1L << (StringLiteral - 67)) | (1L << (BackTick - 67)))) != 0)) {
					{
					setState(1061);
					expressionSequence();
					}
				}

				setState(1064);
				match(CloseParen);
				setState(1065);
				statement();
				}
				break;
			case 5:
				_localctx = new ForInStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1067);
				match(For);
				setState(1068);
				match(OpenParen);
				setState(1069);
				singleExpression(0);
				setState(1073);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case In:
					{
					setState(1070);
					match(In);
					}
					break;
				case Identifier:
					{
					setState(1071);
					match(Identifier);
					setState(1072);
					if (!(this.p("of"))) throw new FailedPredicateException(this, "this.p(\"of\")");
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1075);
				expressionSequence();
				setState(1076);
				match(CloseParen);
				setState(1077);
				statement();
				}
				break;
			case 6:
				_localctx = new ForVarInStatementContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1079);
				match(For);
				setState(1080);
				match(OpenParen);
				setState(1081);
				varModifier();
				setState(1082);
				variableDeclaration();
				setState(1086);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case In:
					{
					setState(1083);
					match(In);
					}
					break;
				case Identifier:
					{
					setState(1084);
					match(Identifier);
					setState(1085);
					if (!(this.p("of"))) throw new FailedPredicateException(this, "this.p(\"of\")");
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1088);
				expressionSequence();
				setState(1089);
				match(CloseParen);
				setState(1090);
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
		enterRule(_localctx, 164, RULE_varModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1094);
			_la = _input.LA(1);
			if ( !(((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (Var - 69)) | (1L << (Const - 69)) | (1L << (Let - 69)))) != 0)) ) {
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
		enterRule(_localctx, 166, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1096);
			match(Continue);
			setState(1099);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,123,_ctx) ) {
			case 1:
				{
				setState(1097);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1098);
				match(Identifier);
				}
				break;
			}
			setState(1101);
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
		enterRule(_localctx, 168, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1103);
			match(Break);
			setState(1106);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,124,_ctx) ) {
			case 1:
				{
				setState(1104);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1105);
				match(Identifier);
				}
				break;
			}
			setState(1108);
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
		enterRule(_localctx, 170, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1110);
			match(Return);
			setState(1113);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,125,_ctx) ) {
			case 1:
				{
				setState(1111);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1112);
				expressionSequence();
				}
				break;
			}
			setState(1115);
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
		enterRule(_localctx, 172, RULE_yieldStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1117);
			match(Yield);
			setState(1120);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,126,_ctx) ) {
			case 1:
				{
				setState(1118);
				if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
				setState(1119);
				expressionSequence();
				}
				break;
			}
			setState(1122);
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
		enterRule(_localctx, 174, RULE_withStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1124);
			match(With);
			setState(1125);
			match(OpenParen);
			setState(1126);
			expressionSequence();
			setState(1127);
			match(CloseParen);
			setState(1128);
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
		enterRule(_localctx, 176, RULE_switchStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1130);
			match(Switch);
			setState(1131);
			match(OpenParen);
			setState(1132);
			expressionSequence();
			setState(1133);
			match(CloseParen);
			setState(1134);
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
		enterRule(_localctx, 178, RULE_caseBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1136);
			match(OpenBrace);
			setState(1138);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Case) {
				{
				setState(1137);
				caseClauses();
				}
			}

			setState(1144);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Default) {
				{
				setState(1140);
				defaultClause();
				setState(1142);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Case) {
					{
					setState(1141);
					caseClauses();
					}
				}

				}
			}

			setState(1146);
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
		enterRule(_localctx, 180, RULE_caseClauses);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1149); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1148);
				caseClause();
				}
				}
				setState(1151); 
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
		enterRule(_localctx, 182, RULE_caseClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1153);
			match(Case);
			setState(1154);
			expressionSequence();
			setState(1155);
			match(Colon);
			setState(1157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (Var - 69)) | (1L << (Return - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (With - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Try - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Protected - 69)) | (1L << (Yield - 69)) | (1L << (TypeAlias - 69)) | (1L << (Namespace - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(1156);
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
		enterRule(_localctx, 184, RULE_defaultClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1159);
			match(Default);
			setState(1160);
			match(Colon);
			setState(1162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenParen) | (1L << OpenBrace) | (1L << SemiColon) | (1L << Break) | (1L << Do))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (Var - 69)) | (1L << (Return - 69)) | (1L << (Continue - 69)) | (1L << (For - 69)) | (1L << (Switch - 69)) | (1L << (While - 69)) | (1L << (Debugger - 69)) | (1L << (Function - 69)) | (1L << (With - 69)) | (1L << (If - 69)) | (1L << (Throw - 69)) | (1L << (Try - 69)) | (1L << (ReadOnly - 69)) | (1L << (Async - 69)) | (1L << (Class - 69)) | (1L << (Enum - 69)) | (1L << (Const - 69)) | (1L << (Export - 69)) | (1L << (Import - 69)) | (1L << (Let - 69)) | (1L << (Private - 69)) | (1L << (Public - 69)) | (1L << (Interface - 69)) | (1L << (Protected - 69)) | (1L << (Yield - 69)) | (1L << (TypeAlias - 69)) | (1L << (Namespace - 69)) | (1L << (Module - 69)) | (1L << (Declare - 69)) | (1L << (Abstract - 69)) | (1L << (At - 69)) | (1L << (Identifier - 69)))) != 0)) {
				{
				setState(1161);
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
		enterRule(_localctx, 186, RULE_labelledStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1164);
			match(Identifier);
			setState(1165);
			match(Colon);
			setState(1166);
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
		enterRule(_localctx, 188, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1168);
			match(Throw);
			setState(1169);
			if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
			setState(1170);
			expressionSequence();
			setState(1171);
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
		enterRule(_localctx, 190, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1173);
			match(Try);
			setState(1174);
			block();
			setState(1180);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Catch:
				{
				setState(1175);
				catchProduction();
				setState(1177);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Finally) {
					{
					setState(1176);
					finallyProduction();
					}
				}

				}
				break;
			case Finally:
				{
				setState(1179);
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
		enterRule(_localctx, 192, RULE_catchProduction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1182);
			match(Catch);
			setState(1183);
			match(OpenParen);
			setState(1184);
			match(Identifier);
			setState(1185);
			match(CloseParen);
			setState(1186);
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
		enterRule(_localctx, 194, RULE_finallyProduction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1188);
			match(Finally);
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
		enterRule(_localctx, 196, RULE_debuggerStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1191);
			match(Debugger);
			setState(1192);
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
		enterRule(_localctx, 198, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1194);
			match(Function);
			setState(1196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(1195);
				match(Multiply);
				}
			}

			setState(1198);
			identifierName();
			setState(1199);
			callSignature();
			setState(1201);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,136,_ctx) ) {
			case 1:
				{
				setState(1200);
				block();
				}
				break;
			}
			setState(1204);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,137,_ctx) ) {
			case 1:
				{
				setState(1203);
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
		public ClassTailContext classTail() {
			return getRuleContext(ClassTailContext.class,0);
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
		enterRule(_localctx, 200, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Abstract) {
				{
				setState(1206);
				match(Abstract);
				}
			}

			setState(1209);
			match(Class);
			setState(1210);
			identifierOrKeyWord();
			setState(1212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(1211);
				typeParameters();
				}
			}

			setState(1214);
			classHeritage();
			setState(1215);
			classTail();
			setState(1217);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,140,_ctx) ) {
			case 1:
				{
				setState(1216);
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
		public ImplementsClauseContext implementsClause() {
			return getRuleContext(ImplementsClauseContext.class,0);
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
		enterRule(_localctx, 202, RULE_classHeritage);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1220);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Extends) {
				{
				setState(1219);
				classExtendsClause();
				}
			}

			setState(1223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Implements) {
				{
				setState(1222);
				implementsClause();
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

	public static class ClassTailContext extends ParserRuleContext {
		public TerminalNode OpenBrace() { return getToken(TypeScriptParser.OpenBrace, 0); }
		public TerminalNode CloseBrace() { return getToken(TypeScriptParser.CloseBrace, 0); }
		public ClassElementListContext classElementList() {
			return getRuleContext(ClassElementListContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public ClassTailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classTail; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassTail(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassTail(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassTail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTailContext classTail() throws RecognitionException {
		ClassTailContext _localctx = new ClassTailContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_classTail);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1225);
			match(OpenBrace);
			setState(1227);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (OpenBracket - 4)) | (1L << (Plus - 4)) | (1L << (Minus - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)) | (1L << (Else - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (At - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)))) != 0)) {
				{
				setState(1226);
				classElementList();
				}
			}

			setState(1230);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon) {
				{
				setState(1229);
				match(SemiColon);
				}
			}

			setState(1232);
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

	public static class ClassElementListContext extends ParserRuleContext {
		public List<ClassElementContext> classElement() {
			return getRuleContexts(ClassElementContext.class);
		}
		public ClassElementContext classElement(int i) {
			return getRuleContext(ClassElementContext.class,i);
		}
		public List<TerminalNode> SemiColon() { return getTokens(TypeScriptParser.SemiColon); }
		public TerminalNode SemiColon(int i) {
			return getToken(TypeScriptParser.SemiColon, i);
		}
		public ClassElementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classElementList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassElementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassElementList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassElementList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassElementListContext classElementList() throws RecognitionException {
		ClassElementListContext _localctx = new ClassElementListContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_classElementList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1234);
			classElement();
			setState(1242);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,146,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1237);
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
						setState(1236);
						match(SemiColon);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(1239);
					classElement();
					}
					} 
				}
				setState(1244);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,146,_ctx);
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
		enterRule(_localctx, 208, RULE_classExtendsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1245);
			match(Extends);
			setState(1246);
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

	public static class ImplementsClauseContext extends ParserRuleContext {
		public TerminalNode Implements() { return getToken(TypeScriptParser.Implements, 0); }
		public ClassOrInterfaceTypeListContext classOrInterfaceTypeList() {
			return getRuleContext(ClassOrInterfaceTypeListContext.class,0);
		}
		public ImplementsClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_implementsClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterImplementsClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitImplementsClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitImplementsClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImplementsClauseContext implementsClause() throws RecognitionException {
		ImplementsClauseContext _localctx = new ImplementsClauseContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_implementsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1248);
			match(Implements);
			setState(1249);
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

	public static class ClassElementContext extends ParserRuleContext {
		public ConstructorDeclarationContext constructorDeclaration() {
			return getRuleContext(ConstructorDeclarationContext.class,0);
		}
		public PropertyMemberDeclarationContext propertyMemberDeclaration() {
			return getRuleContext(PropertyMemberDeclarationContext.class,0);
		}
		public DecoratorListContext decoratorList() {
			return getRuleContext(DecoratorListContext.class,0);
		}
		public IndexSignatureContext indexSignature() {
			return getRuleContext(IndexSignatureContext.class,0);
		}
		public ClassElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterClassElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitClassElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitClassElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassElementContext classElement() throws RecognitionException {
		ClassElementContext _localctx = new ClassElementContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_classElement);
		int _la;
		try {
			setState(1257);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,148,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1251);
				constructorDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1253);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==At) {
					{
					setState(1252);
					decoratorList();
					}
				}

				setState(1255);
				propertyMemberDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1256);
				indexSignature();
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
		enterRule(_localctx, 214, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1260);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 101)) & ~0x3f) == 0 && ((1L << (_la - 101)) & ((1L << (Private - 101)) | (1L << (Public - 101)) | (1L << (Protected - 101)))) != 0)) {
				{
				setState(1259);
				accessibilityModifier();
				}
			}

			setState(1262);
			match(Constructor);
			setState(1263);
			parameterBlock();
			setState(1265);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(1264);
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
		public PropertyMemberBaseContext propertyMemberBase() {
			return getRuleContext(PropertyMemberBaseContext.class,0);
		}
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public GetAccessorContext getAccessor() {
			return getRuleContext(GetAccessorContext.class,0);
		}
		public SetAccessorContext setAccessor() {
			return getRuleContext(SetAccessorContext.class,0);
		}
		public CallSignatureContext callSignature() {
			return getRuleContext(CallSignatureContext.class,0);
		}
		public TerminalNode QuestionMark() { return getToken(TypeScriptParser.QuestionMark, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ColonSepTypeRefContext colonSepTypeRef() {
			return getRuleContext(ColonSepTypeRefContext.class,0);
		}
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
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
		enterRule(_localctx, 216, RULE_propertyMemberDeclaration);
		int _la;
		try {
			setState(1289);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,157,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1267);
				abstractDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1268);
				propertyMemberBase();
				setState(1287);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,156,_ctx) ) {
				case 1:
					{
					setState(1269);
					propertyName();
					setState(1271);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==QuestionMark) {
						{
						setState(1270);
						match(QuestionMark);
						}
					}

					setState(1283);
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
						setState(1274);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==Colon) {
							{
							setState(1273);
							colonSepTypeRef();
							}
						}

						setState(1277);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==Assign) {
							{
							setState(1276);
							initializer();
							}
						}

						}
						}
						break;
					case OpenParen:
					case LessThan:
						{
						setState(1279);
						callSignature();
						setState(1281);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==OpenBrace) {
							{
							setState(1280);
							block();
							}
						}

						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				case 2:
					{
					setState(1285);
					getAccessor();
					}
					break;
				case 3:
					{
					setState(1286);
					setAccessor();
					}
					break;
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
		enterRule(_localctx, 218, RULE_abstractDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1291);
			match(Abstract);
			setState(1295);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(1292);
				match(Identifier);
				setState(1293);
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
				setState(1294);
				variableStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1297);
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
		enterRule(_localctx, 220, RULE_propertyMemberBase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1300);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,159,_ctx) ) {
			case 1:
				{
				setState(1299);
				match(Async);
				}
				break;
			}
			setState(1303);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,160,_ctx) ) {
			case 1:
				{
				setState(1302);
				accessibilityModifier();
				}
				break;
			}
			setState(1306);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,161,_ctx) ) {
			case 1:
				{
				setState(1305);
				match(Static);
				}
				break;
			}
			setState(1309);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,162,_ctx) ) {
			case 1:
				{
				setState(1308);
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
		enterRule(_localctx, 222, RULE_generatorMethod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1312);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(1311);
				match(Multiply);
				}
			}

			setState(1314);
			match(Identifier);
			setState(1315);
			parameterBlock();
			setState(1316);
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

	public static class GeneratorFunctionExpressionDeclarationContext extends ParserRuleContext {
		public TerminalNode Function() { return getToken(TypeScriptParser.Function, 0); }
		public TerminalNode Multiply() { return getToken(TypeScriptParser.Multiply, 0); }
		public ParameterBlockContext parameterBlock() {
			return getRuleContext(ParameterBlockContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(TypeScriptParser.Identifier, 0); }
		public GeneratorFunctionExpressionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_generatorFunctionExpressionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterGeneratorFunctionExpressionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitGeneratorFunctionExpressionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitGeneratorFunctionExpressionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GeneratorFunctionExpressionDeclarationContext generatorFunctionExpressionDeclaration() throws RecognitionException {
		GeneratorFunctionExpressionDeclarationContext _localctx = new GeneratorFunctionExpressionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 224, RULE_generatorFunctionExpressionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1318);
			match(Function);
			setState(1319);
			match(Multiply);
			setState(1321);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1320);
				match(Identifier);
				}
			}

			setState(1323);
			parameterBlock();
			setState(1324);
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
		enterRule(_localctx, 226, RULE_generatorBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1326);
			match(OpenBrace);
			setState(1327);
			generatorDefinition();
			setState(1332);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,165,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1328);
					match(Comma);
					setState(1329);
					generatorDefinition();
					}
					} 
				}
				setState(1334);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,165,_ctx);
			}
			setState(1336);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1335);
				match(Comma);
				}
			}

			setState(1338);
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
		enterRule(_localctx, 228, RULE_generatorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1340);
			match(Multiply);
			setState(1341);
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
		enterRule(_localctx, 230, RULE_iteratorBlock);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1343);
			match(OpenBrace);
			setState(1344);
			iteratorDefinition();
			setState(1349);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,167,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1345);
					match(Comma);
					setState(1346);
					iteratorDefinition();
					}
					} 
				}
				setState(1351);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,167,_ctx);
			}
			setState(1353);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1352);
				match(Comma);
				}
			}

			setState(1355);
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
		enterRule(_localctx, 232, RULE_iteratorDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1357);
			match(OpenBracket);
			setState(1358);
			singleExpression(0);
			setState(1359);
			match(CloseBracket);
			setState(1360);
			parameterBlock();
			setState(1361);
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
		enterRule(_localctx, 234, RULE_callSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1364);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LessThan) {
				{
				setState(1363);
				typeParameters();
				}
			}

			setState(1366);
			parameterBlock();
			setState(1372);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,171,_ctx) ) {
			case 1:
				{
				setState(1367);
				match(Colon);
				setState(1370);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,170,_ctx) ) {
				case 1:
					{
					setState(1368);
					typePredicateWithOperatorTypeRef();
					}
					break;
				case 2:
					{
					setState(1369);
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
		enterRule(_localctx, 236, RULE_parameterBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1374);
			match(OpenParen);
			setState(1376);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (OpenBracket - 4)) | (1L << (OpenBrace - 4)) | (1L << (Ellipsis - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)) | (1L << (Else - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (At - 68)) | (1L << (Identifier - 68)))) != 0)) {
				{
				setState(1375);
				parameterListTrailingComma();
				}
			}

			setState(1378);
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
		enterRule(_localctx, 238, RULE_parameterListTrailingComma);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1380);
			parameterList();
			setState(1382);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1381);
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
		enterRule(_localctx, 240, RULE_parameterList);
		try {
			int _alt;
			setState(1397);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Ellipsis:
				enterOuterAlt(_localctx, 1);
				{
				setState(1384);
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
				setState(1385);
				parameter();
				setState(1390);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,174,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1386);
						match(Comma);
						setState(1387);
						parameter();
						}
						} 
					}
					setState(1392);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,174,_ctx);
				}
				setState(1395);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,175,_ctx) ) {
				case 1:
					{
					setState(1393);
					match(Comma);
					setState(1394);
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
		enterRule(_localctx, 242, RULE_restParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1399);
			match(Ellipsis);
			setState(1400);
			identifierOrPattern();
			setState(1402);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1401);
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
		enterRule(_localctx, 244, RULE_parameter);
		try {
			setState(1406);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,178,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1404);
				requiredParameter();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1405);
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
		enterRule(_localctx, 246, RULE_requiredParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1409);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==At) {
				{
				setState(1408);
				decoratorList();
				}
			}

			setState(1412);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,180,_ctx) ) {
			case 1:
				{
				setState(1411);
				accessibilityModifier();
				}
				break;
			}
			setState(1414);
			identifierOrPattern();
			setState(1416);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1415);
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
		enterRule(_localctx, 248, RULE_optionalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1419);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==At) {
				{
				setState(1418);
				decoratorList();
				}
			}

			setState(1422);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,183,_ctx) ) {
			case 1:
				{
				setState(1421);
				accessibilityModifier();
				}
				break;
			}
			setState(1424);
			identifierOrPattern();
			setState(1433);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QuestionMark:
				{
				setState(1425);
				match(QuestionMark);
				setState(1427);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(1426);
					colonSepTypeRef();
					}
				}

				}
				break;
			case Assign:
			case Colon:
				{
				setState(1430);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(1429);
					colonSepTypeRef();
					}
				}

				setState(1432);
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
		enterRule(_localctx, 250, RULE_accessibilityModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1435);
			_la = _input.LA(1);
			if ( !(((((_la - 101)) & ~0x3f) == 0 && ((1L << (_la - 101)) & ((1L << (Private - 101)) | (1L << (Public - 101)) | (1L << (Protected - 101)))) != 0)) ) {
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
		enterRule(_localctx, 252, RULE_identifierOrPattern);
		try {
			setState(1439);
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
				setState(1437);
				identifierName();
				}
				break;
			case OpenBracket:
			case OpenBrace:
				enterOuterAlt(_localctx, 2);
				{
				setState(1438);
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
		enterRule(_localctx, 254, RULE_arrayLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(1441);
			match(OpenBracket);
			setState(1443);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OpenBracket) | (1L << OpenBrace) | (1L << Ellipsis))) != 0) || _la==Identifier) {
				{
				setState(1442);
				elementList();
				}
			}

			setState(1445);
			match(CloseBracket);
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
		enterRule(_localctx, 256, RULE_elementList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1447);
			arrayElement();
			setState(1456);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,190,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1449); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(1448);
						match(Comma);
						}
						}
						setState(1451); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==Comma );
					setState(1453);
					arrayElement();
					}
					} 
				}
				setState(1458);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,190,_ctx);
			}
			setState(1460);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(1459);
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
		enterRule(_localctx, 258, RULE_arrayElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1463);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(1462);
				match(Ellipsis);
				}
			}

			setState(1465);
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
		enterRule(_localctx, 260, RULE_bindingElement);
		try {
			setState(1469);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OpenBracket:
			case OpenBrace:
				enterOuterAlt(_localctx, 1);
				{
				setState(1467);
				bindingPattern();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1468);
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

	public static class TypeBodyContext extends ParserRuleContext {
		public TypeMemberListContext typeMemberList() {
			return getRuleContext(TypeMemberListContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(TypeScriptParser.SemiColon, 0); }
		public TerminalNode Comma() { return getToken(TypeScriptParser.Comma, 0); }
		public TypeBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeBodyContext typeBody() throws RecognitionException {
		TypeBodyContext _localctx = new TypeBodyContext(_ctx, getState());
		enterRule(_localctx, 262, RULE_typeBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1471);
			typeMemberList();
			setState(1473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SemiColon || _la==Comma) {
				{
				setState(1472);
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

	public static class TypeMemberListContext extends ParserRuleContext {
		public List<TypeMemberContext> typeMember() {
			return getRuleContexts(TypeMemberContext.class);
		}
		public TypeMemberContext typeMember(int i) {
			return getRuleContext(TypeMemberContext.class,i);
		}
		public List<TerminalNode> SemiColon() { return getTokens(TypeScriptParser.SemiColon); }
		public TerminalNode SemiColon(int i) {
			return getToken(TypeScriptParser.SemiColon, i);
		}
		public List<TerminalNode> Comma() { return getTokens(TypeScriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(TypeScriptParser.Comma, i);
		}
		public TypeMemberListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeMemberList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeMemberList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeMemberList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeMemberList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeMemberListContext typeMemberList() throws RecognitionException {
		TypeMemberListContext _localctx = new TypeMemberListContext(_ctx, getState());
		enterRule(_localctx, 264, RULE_typeMemberList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1475);
			typeMember();
			setState(1484);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,196,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1479);
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
						setState(1477);
						match(SemiColon);
						}
						break;
					case Comma:
						{
						setState(1478);
						match(Comma);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(1481);
					typeMember();
					}
					} 
				}
				setState(1486);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,196,_ctx);
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

	public static class TypeMemberContext extends ParserRuleContext {
		public PropertySignaturContext propertySignatur() {
			return getRuleContext(PropertySignaturContext.class,0);
		}
		public CallSignatureContext callSignature() {
			return getRuleContext(CallSignatureContext.class,0);
		}
		public ConstructSignatureContext constructSignature() {
			return getRuleContext(ConstructSignatureContext.class,0);
		}
		public IndexSignatureContext indexSignature() {
			return getRuleContext(IndexSignatureContext.class,0);
		}
		public MethodSignatureContext methodSignature() {
			return getRuleContext(MethodSignatureContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(TypeScriptParser.ARROW, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TypeMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeMember; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).enterTypeMember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeScriptParserListener ) ((TypeScriptParserListener)listener).exitTypeMember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TypeScriptParserVisitor ) return ((TypeScriptParserVisitor<? extends T>)visitor).visitTypeMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeMemberContext typeMember() throws RecognitionException {
		TypeMemberContext _localctx = new TypeMemberContext(_ctx, getState());
		enterRule(_localctx, 266, RULE_typeMember);
		int _la;
		try {
			setState(1496);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,198,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1487);
				propertySignatur();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1488);
				callSignature();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1489);
				constructSignature();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1490);
				indexSignature();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1491);
				methodSignature();
				setState(1494);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ARROW) {
					{
					setState(1492);
					match(ARROW);
					setState(1493);
					typeRef();
					}
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
		enterRule(_localctx, 268, RULE_objectLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1498);
			match(OpenBrace);
			setState(1510);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 4)) & ~0x3f) == 0 && ((1L << (_la - 4)) & ((1L << (OpenBracket - 4)) | (1L << (Ellipsis - 4)) | (1L << (Minus - 4)) | (1L << (Multiply - 4)) | (1L << (NullLiteral - 4)) | (1L << (UndefinedLiteral - 4)) | (1L << (BooleanLiteral - 4)) | (1L << (DecimalLiteral - 4)) | (1L << (HexIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral - 4)) | (1L << (OctalIntegerLiteral2 - 4)) | (1L << (BinaryIntegerLiteral - 4)) | (1L << (Break - 4)) | (1L << (Do - 4)) | (1L << (Instanceof - 4)) | (1L << (Typeof - 4)) | (1L << (Unique - 4)) | (1L << (Case - 4)) | (1L << (Else - 4)))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (New - 68)) | (1L << (Var - 68)) | (1L << (Catch - 68)) | (1L << (Finally - 68)) | (1L << (Return - 68)) | (1L << (Void - 68)) | (1L << (Continue - 68)) | (1L << (For - 68)) | (1L << (Switch - 68)) | (1L << (While - 68)) | (1L << (Debugger - 68)) | (1L << (Function - 68)) | (1L << (This - 68)) | (1L << (With - 68)) | (1L << (Default - 68)) | (1L << (If - 68)) | (1L << (Throw - 68)) | (1L << (Delete - 68)) | (1L << (In - 68)) | (1L << (Try - 68)) | (1L << (As - 68)) | (1L << (From - 68)) | (1L << (ReadOnly - 68)) | (1L << (Async - 68)) | (1L << (Class - 68)) | (1L << (Enum - 68)) | (1L << (Extends - 68)) | (1L << (Super - 68)) | (1L << (Const - 68)) | (1L << (Export - 68)) | (1L << (Import - 68)) | (1L << (Implements - 68)) | (1L << (Let - 68)) | (1L << (Private - 68)) | (1L << (Public - 68)) | (1L << (Interface - 68)) | (1L << (Package - 68)) | (1L << (Protected - 68)) | (1L << (Static - 68)) | (1L << (Yield - 68)) | (1L << (Any - 68)) | (1L << (Number - 68)) | (1L << (Boolean - 68)) | (1L << (String - 68)) | (1L << (Symbol - 68)) | (1L << (TypeAlias - 68)) | (1L << (Get - 68)) | (1L << (Set - 68)) | (1L << (Constructor - 68)) | (1L << (Namespace - 68)) | (1L << (Require - 68)) | (1L << (Module - 68)) | (1L << (Declare - 68)) | (1L << (Abstract - 68)) | (1L << (Is - 68)) | (1L << (Infer - 68)) | (1L << (Never - 68)) | (1L << (Unknown - 68)) | (1L << (Asserts - 68)) | (1L << (Identifier - 68)) | (1L << (StringLiteral - 68)))) != 0)) {
				{
				setState(1499);
				propertyAssignment();
				setState(1504);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,199,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1500);
						match(Comma);
						setState(1501);
						propertyAssignment();
						}
						} 
					}
					setState(1506);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,199,_ctx);
				}
				setState(1508);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(1507);
					match(Comma);
					}
				}

				}
			}

			setState(1512);
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
		enterRule(_localctx, 270, RULE_propertyAssignment);
		int _la;
		try {
			setState(1528);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,204,_ctx) ) {
			case 1:
				_localctx = new PropertyExpressionAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1514);
				propertyName();
				setState(1518);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Colon:
					{
					setState(1515);
					match(Colon);
					setState(1516);
					identifierOrKeyWord();
					}
					break;
				case OpenBracket:
				case OpenBrace:
					{
					setState(1517);
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
				setState(1522);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Assign) {
					{
					setState(1520);
					match(Assign);
					setState(1521);
					singleExpression(0);
					}
				}

				}
				break;
			case 2:
				_localctx = new PropertyGetterContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1524);
				getAccessor();
				}
				break;
			case 3:
				_localctx = new PropertySetterContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1525);
				setAccessor();
				}
				break;
			case 4:
				_localctx = new MethodPropertyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1526);
				generatorMethod();
				}
				break;
			case 5:
				_localctx = new RestParameterInObjectContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1527);
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
		enterRule(_localctx, 272, RULE_getAccessor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1530);
			getter();
			setState(1531);
			match(OpenParen);
			setState(1532);
			match(CloseParen);
			setState(1534);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1533);
				colonSepTypeRef();
				}
			}

			setState(1537);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(1536);
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
		enterRule(_localctx, 274, RULE_setAccessor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1539);
			setter();
			setState(1540);
			match(OpenParen);
			setState(1543);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(1541);
				match(Identifier);
				}
				break;
			case OpenBracket:
			case OpenBrace:
				{
				setState(1542);
				bindingPattern();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1546);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1545);
				colonSepTypeRef();
				}
			}

			setState(1548);
			match(CloseParen);
			setState(1550);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OpenBrace) {
				{
				setState(1549);
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

	public static class PropertyNameContext extends ParserRuleContext {
		public List<IdentifierNameContext> identifierName() {
			return getRuleContexts(IdentifierNameContext.class);
		}
		public IdentifierNameContext identifierName(int i) {
			return getRuleContext(IdentifierNameContext.class,i);
		}
		public TerminalNode StringLiteral() { return getToken(TypeScriptParser.StringLiteral, 0); }
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public TerminalNode OpenBracket() { return getToken(TypeScriptParser.OpenBracket, 0); }
		public TerminalNode CloseBracket() { return getToken(TypeScriptParser.CloseBracket, 0); }
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
		enterRule(_localctx, 276, RULE_propertyName);
		try {
			setState(1564);
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
				setState(1552);
				identifierName();
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1553);
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
				setState(1554);
				numericLiteral();
				}
				break;
			case OpenBracket:
				enterOuterAlt(_localctx, 4);
				{
				setState(1555);
				match(OpenBracket);
				setState(1561);
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
					setState(1556);
					identifierName();
					setState(1557);
					match(Dot);
					setState(1558);
					identifierName();
					}
					break;
				case StringLiteral:
					{
					setState(1560);
					match(StringLiteral);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1563);
				match(CloseBracket);
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
		enterRule(_localctx, 278, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1566);
			match(OpenParen);
			setState(1571);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (RegularExpressionLiteral - 3)) | (1L << (OpenBracket - 3)) | (1L << (OpenParen - 3)) | (1L << (OpenBrace - 3)) | (1L << (Ellipsis - 3)) | (1L << (PlusPlus - 3)) | (1L << (MinusMinus - 3)) | (1L << (Plus - 3)) | (1L << (Minus - 3)) | (1L << (BitNot - 3)) | (1L << (Not - 3)) | (1L << (LessThan - 3)) | (1L << (NullLiteral - 3)) | (1L << (UndefinedLiteral - 3)) | (1L << (BooleanLiteral - 3)) | (1L << (DecimalLiteral - 3)) | (1L << (HexIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral - 3)) | (1L << (OctalIntegerLiteral2 - 3)) | (1L << (BinaryIntegerLiteral - 3)) | (1L << (Break - 3)) | (1L << (Do - 3)) | (1L << (Instanceof - 3)) | (1L << (Typeof - 3)) | (1L << (Unique - 3)) | (1L << (Case - 3)))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (Else - 67)) | (1L << (New - 67)) | (1L << (Var - 67)) | (1L << (Catch - 67)) | (1L << (Finally - 67)) | (1L << (Return - 67)) | (1L << (Void - 67)) | (1L << (Continue - 67)) | (1L << (For - 67)) | (1L << (Switch - 67)) | (1L << (While - 67)) | (1L << (Debugger - 67)) | (1L << (Function - 67)) | (1L << (This - 67)) | (1L << (With - 67)) | (1L << (Default - 67)) | (1L << (If - 67)) | (1L << (Throw - 67)) | (1L << (Delete - 67)) | (1L << (In - 67)) | (1L << (Try - 67)) | (1L << (As - 67)) | (1L << (From - 67)) | (1L << (ReadOnly - 67)) | (1L << (Async - 67)) | (1L << (Class - 67)) | (1L << (Enum - 67)) | (1L << (Extends - 67)) | (1L << (Super - 67)) | (1L << (Const - 67)) | (1L << (Export - 67)) | (1L << (Import - 67)) | (1L << (Implements - 67)) | (1L << (Let - 67)) | (1L << (Private - 67)) | (1L << (Public - 67)) | (1L << (Interface - 67)) | (1L << (Package - 67)) | (1L << (Protected - 67)) | (1L << (Static - 67)) | (1L << (Yield - 67)) | (1L << (Any - 67)) | (1L << (Number - 67)) | (1L << (Boolean - 67)) | (1L << (String - 67)) | (1L << (Symbol - 67)) | (1L << (TypeAlias - 67)) | (1L << (Get - 67)) | (1L << (Set - 67)) | (1L << (Constructor - 67)) | (1L << (Namespace - 67)) | (1L << (Require - 67)) | (1L << (Module - 67)) | (1L << (Declare - 67)) | (1L << (Abstract - 67)) | (1L << (Is - 67)) | (1L << (Infer - 67)) | (1L << (Never - 67)) | (1L << (Unknown - 67)) | (1L << (Asserts - 67)) | (1L << (Identifier - 67)) | (1L << (StringLiteral - 67)) | (1L << (BackTick - 67)))) != 0)) {
				{
				setState(1567);
				argumentList();
				setState(1569);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Comma) {
					{
					setState(1568);
					match(Comma);
					}
				}

				}
			}

			setState(1573);
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
		enterRule(_localctx, 280, RULE_argumentList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1575);
			argument();
			setState(1580);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,214,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1576);
					match(Comma);
					setState(1577);
					argument();
					}
					} 
				}
				setState(1582);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,214,_ctx);
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
		enterRule(_localctx, 282, RULE_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1584);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Ellipsis) {
				{
				setState(1583);
				match(Ellipsis);
				}
			}

			setState(1588);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,216,_ctx) ) {
			case 1:
				{
				setState(1586);
				singleExpression(0);
				}
				break;
			case 2:
				{
				setState(1587);
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
		enterRule(_localctx, 284, RULE_expressionSequence);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1590);
			singleExpression(0);
			setState(1595);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,217,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1591);
					match(Comma);
					setState(1592);
					singleExpression(0);
					}
					} 
				}
				setState(1597);
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
		enterRule(_localctx, 286, RULE_functionExpressionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1598);
			match(Function);
			setState(1600);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Multiply) {
				{
				setState(1599);
				match(Multiply);
				}
			}

			setState(1603);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(1602);
				match(Identifier);
				}
			}

			setState(1605);
			parameterBlock();
			setState(1607);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1606);
				colonSepTypeRef();
				}
			}

			setState(1609);
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
		public ClassTailContext classTail() {
			return getRuleContext(ClassTailContext.class,0);
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
		int _startState = 288;
		enterRecursionRule(_localctx, 288, RULE_singleExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1662);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,225,_ctx) ) {
			case 1:
				{
				_localctx = new FunctionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1612);
				functionExpressionDeclaration();
				}
				break;
			case 2:
				{
				_localctx = new ArrowFunctionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1613);
				arrowFunctionDeclaration();
				}
				break;
			case 3:
				{
				_localctx = new ClassExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1614);
				match(Class);
				setState(1616);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Identifier) {
					{
					setState(1615);
					match(Identifier);
					}
				}

				setState(1618);
				classTail();
				}
				break;
			case 4:
				{
				_localctx = new NewExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1619);
				match(New);
				setState(1620);
				singleExpression(0);
				setState(1622);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,222,_ctx) ) {
				case 1:
					{
					setState(1621);
					typeArguments();
					}
					break;
				}
				setState(1625);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,223,_ctx) ) {
				case 1:
					{
					setState(1624);
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
				setState(1627);
				match(Delete);
				setState(1628);
				singleExpression(34);
				}
				break;
			case 6:
				{
				_localctx = new VoidExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1629);
				match(Void);
				setState(1630);
				singleExpression(33);
				}
				break;
			case 7:
				{
				_localctx = new TypeofExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1631);
				match(Typeof);
				setState(1632);
				singleExpression(32);
				}
				break;
			case 8:
				{
				_localctx = new PreIncrementExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1633);
				match(PlusPlus);
				setState(1634);
				singleExpression(31);
				}
				break;
			case 9:
				{
				_localctx = new PreDecreaseExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1635);
				match(MinusMinus);
				setState(1636);
				singleExpression(30);
				}
				break;
			case 10:
				{
				_localctx = new UnaryPlusExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1637);
				match(Plus);
				setState(1638);
				singleExpression(29);
				}
				break;
			case 11:
				{
				_localctx = new UnaryMinusExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1639);
				match(Minus);
				setState(1640);
				singleExpression(28);
				}
				break;
			case 12:
				{
				_localctx = new BitNotExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1641);
				match(BitNot);
				setState(1642);
				singleExpression(27);
				}
				break;
			case 13:
				{
				_localctx = new NotExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1643);
				match(Not);
				setState(1644);
				singleExpression(26);
				}
				break;
			case 14:
				{
				_localctx = new IteratorsExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1645);
				iteratorBlock();
				}
				break;
			case 15:
				{
				_localctx = new GeneratorsExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1646);
				generatorBlock();
				}
				break;
			case 16:
				{
				_localctx = new YieldExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1647);
				yieldStatement();
				}
				break;
			case 17:
				{
				_localctx = new ThisExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1648);
				match(This);
				}
				break;
			case 18:
				{
				_localctx = new IdentifierExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1649);
				identifierName();
				}
				break;
			case 19:
				{
				_localctx = new SuperExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1650);
				match(Super);
				}
				break;
			case 20:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1651);
				literal();
				}
				break;
			case 21:
				{
				_localctx = new ArrayLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1652);
				arrayLiteral();
				}
				break;
			case 22:
				{
				_localctx = new ObjectLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1653);
				objectLiteral();
				}
				break;
			case 23:
				{
				_localctx = new ParenthesizedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1654);
				match(OpenParen);
				setState(1655);
				expressionSequence();
				setState(1656);
				match(CloseParen);
				}
				break;
			case 24:
				{
				_localctx = new GenericTypesContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1658);
				typeArguments();
				setState(1660);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,224,_ctx) ) {
				case 1:
					{
					setState(1659);
					expressionSequence();
					}
					break;
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1737);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,229,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1735);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,228,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicativeExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1664);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(1665);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Multiply) | (1L << Divide) | (1L << Modulus))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1666);
						singleExpression(26);
						}
						break;
					case 2:
						{
						_localctx = new AdditiveExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1667);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(1668);
						_la = _input.LA(1);
						if ( !(_la==Plus || _la==Minus) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1669);
						singleExpression(25);
						}
						break;
					case 3:
						{
						_localctx = new BitShiftExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1670);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(1677);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,226,_ctx) ) {
						case 1:
							{
							setState(1671);
							match(LeftShiftArithmetic);
							}
							break;
						case 2:
							{
							setState(1672);
							match(MoreThan);
							setState(1673);
							match(MoreThan);
							}
							break;
						case 3:
							{
							setState(1674);
							match(MoreThan);
							setState(1675);
							match(MoreThan);
							setState(1676);
							match(MoreThan);
							}
							break;
						}
						setState(1679);
						singleExpression(24);
						}
						break;
					case 4:
						{
						_localctx = new RelationalExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1680);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(1681);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LessThan) | (1L << MoreThan) | (1L << LessThanEquals) | (1L << GreaterThanEquals))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1682);
						singleExpression(23);
						}
						break;
					case 5:
						{
						_localctx = new InstanceofExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1683);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(1684);
						match(Instanceof);
						setState(1685);
						singleExpression(22);
						}
						break;
					case 6:
						{
						_localctx = new InExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1686);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(1687);
						match(In);
						setState(1688);
						singleExpression(21);
						}
						break;
					case 7:
						{
						_localctx = new EqualityExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1689);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(1690);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Equals_) | (1L << NotEquals) | (1L << IdentityEquals) | (1L << IdentityNotEquals))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1691);
						singleExpression(20);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1692);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(1693);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BitAnd) | (1L << BitXOr) | (1L << BitOr))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1694);
						singleExpression(19);
						}
						break;
					case 9:
						{
						_localctx = new LogicalExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1695);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(1696);
						_la = _input.LA(1);
						if ( !(_la==And || _la==Or) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1697);
						singleExpression(18);
						}
						break;
					case 10:
						{
						_localctx = new TernaryExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1698);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(1699);
						match(QuestionMark);
						setState(1700);
						singleExpression(0);
						setState(1701);
						match(Colon);
						setState(1702);
						singleExpression(17);
						}
						break;
					case 11:
						{
						_localctx = new AssignmentExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1704);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(1705);
						match(Assign);
						setState(1706);
						singleExpression(16);
						}
						break;
					case 12:
						{
						_localctx = new AssignmentOperatorExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1707);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(1708);
						assignmentOperator();
						setState(1709);
						singleExpression(15);
						}
						break;
					case 13:
						{
						_localctx = new MemberIndexExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1711);
						if (!(precpred(_ctx, 40))) throw new FailedPredicateException(this, "precpred(_ctx, 40)");
						setState(1712);
						match(OpenBracket);
						setState(1713);
						expressionSequence();
						setState(1714);
						match(CloseBracket);
						}
						break;
					case 14:
						{
						_localctx = new MemberDotExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1716);
						if (!(precpred(_ctx, 39))) throw new FailedPredicateException(this, "precpred(_ctx, 39)");
						setState(1717);
						match(Dot);
						setState(1718);
						identifierName();
						setState(1720);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,227,_ctx) ) {
						case 1:
							{
							setState(1719);
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
						setState(1722);
						if (!(precpred(_ctx, 37))) throw new FailedPredicateException(this, "precpred(_ctx, 37)");
						setState(1723);
						arguments();
						}
						break;
					case 16:
						{
						_localctx = new PostIncrementExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1724);
						if (!(precpred(_ctx, 36))) throw new FailedPredicateException(this, "precpred(_ctx, 36)");
						setState(1725);
						if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
						setState(1726);
						match(PlusPlus);
						}
						break;
					case 17:
						{
						_localctx = new PostDecreaseExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1727);
						if (!(precpred(_ctx, 35))) throw new FailedPredicateException(this, "precpred(_ctx, 35)");
						setState(1728);
						if (!(this.notLineTerminator())) throw new FailedPredicateException(this, "this.notLineTerminator()");
						setState(1729);
						match(MinusMinus);
						}
						break;
					case 18:
						{
						_localctx = new TemplateStringExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1730);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(1731);
						templateStringLiteral();
						}
						break;
					case 19:
						{
						_localctx = new CastAsExpressionContext(new SingleExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_singleExpression);
						setState(1732);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(1733);
						match(As);
						setState(1734);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(1739);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,229,_ctx);
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
		enterRule(_localctx, 290, RULE_arrowFunctionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1741);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Async) {
				{
				setState(1740);
				match(Async);
				}
			}

			setState(1743);
			arrowFunctionParameters();
			setState(1745);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Colon) {
				{
				setState(1744);
				colonSepTypeRef();
				}
			}

			setState(1747);
			match(ARROW);
			setState(1748);
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
		enterRule(_localctx, 292, RULE_arrowFunctionParameters);
		try {
			setState(1752);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(1750);
				match(Identifier);
				}
				break;
			case OpenParen:
				enterOuterAlt(_localctx, 2);
				{
				setState(1751);
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
		enterRule(_localctx, 294, RULE_arrowFunctionBody);
		try {
			setState(1756);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,233,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1754);
				singleExpression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1755);
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
		enterRule(_localctx, 296, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1758);
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
		enterRule(_localctx, 298, RULE_literal);
		try {
			setState(1766);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NullLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1760);
				match(NullLiteral);
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1761);
				match(BooleanLiteral);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(1762);
				match(StringLiteral);
				}
				break;
			case BackTick:
				enterOuterAlt(_localctx, 4);
				{
				setState(1763);
				templateStringLiteral();
				}
				break;
			case RegularExpressionLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(1764);
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
				setState(1765);
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
		enterRule(_localctx, 300, RULE_templateStringLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1768);
			match(BackTick);
			setState(1772);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TemplateStringStartExpression || _la==TemplateStringAtom) {
				{
				{
				setState(1769);
				templateStringAtom();
				}
				}
				setState(1774);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1775);
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
		enterRule(_localctx, 302, RULE_templateStringAtom);
		try {
			setState(1782);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TemplateStringAtom:
				enterOuterAlt(_localctx, 1);
				{
				setState(1777);
				match(TemplateStringAtom);
				}
				break;
			case TemplateStringStartExpression:
				enterOuterAlt(_localctx, 2);
				{
				setState(1778);
				match(TemplateStringStartExpression);
				setState(1779);
				singleExpression(0);
				setState(1780);
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
		enterRule(_localctx, 304, RULE_numericLiteral);
		int _la;
		try {
			setState(1792);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Minus:
			case DecimalLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(1785);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Minus) {
					{
					setState(1784);
					match(Minus);
					}
				}

				setState(1787);
				match(DecimalLiteral);
				}
				break;
			case HexIntegerLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1788);
				match(HexIntegerLiteral);
				}
				break;
			case OctalIntegerLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(1789);
				match(OctalIntegerLiteral);
				}
				break;
			case OctalIntegerLiteral2:
				enterOuterAlt(_localctx, 4);
				{
				setState(1790);
				match(OctalIntegerLiteral2);
				}
				break;
			case BinaryIntegerLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(1791);
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
		enterRule(_localctx, 306, RULE_identifierOrKeyWord);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1794);
			_la = _input.LA(1);
			if ( !(((((_la - 113)) & ~0x3f) == 0 && ((1L << (_la - 113)) & ((1L << (TypeAlias - 113)) | (1L << (Require - 113)) | (1L << (Identifier - 113)))) != 0)) ) {
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
		enterRule(_localctx, 308, RULE_identifierName);
		try {
			setState(1798);
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
				setState(1796);
				reservedWord();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1797);
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
		enterRule(_localctx, 310, RULE_reservedWord);
		try {
			setState(1802);
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
				setState(1800);
				keyword();
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1801);
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
		enterRule(_localctx, 312, RULE_typeReferenceName);
		try {
			setState(1807);
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
				setState(1804);
				keywordAllowedInTypeReferences();
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(1805);
				match(BooleanLiteral);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 3);
				{
				setState(1806);
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
		enterRule(_localctx, 314, RULE_keyword);
		try {
			setState(1812);
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
				setState(1809);
				keywordAllowedInTypeReferences();
				}
				break;
			case ReadOnly:
				enterOuterAlt(_localctx, 2);
				{
				setState(1810);
				match(ReadOnly);
				}
				break;
			case Typeof:
				enterOuterAlt(_localctx, 3);
				{
				setState(1811);
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
		enterRule(_localctx, 316, RULE_keywordAllowedInTypeReferences);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1814);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NullLiteral) | (1L << UndefinedLiteral) | (1L << Break) | (1L << Do) | (1L << Instanceof))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (Unique - 64)) | (1L << (Case - 64)) | (1L << (Else - 64)) | (1L << (New - 64)) | (1L << (Var - 64)) | (1L << (Catch - 64)) | (1L << (Finally - 64)) | (1L << (Return - 64)) | (1L << (Void - 64)) | (1L << (Continue - 64)) | (1L << (For - 64)) | (1L << (Switch - 64)) | (1L << (While - 64)) | (1L << (Debugger - 64)) | (1L << (Function - 64)) | (1L << (This - 64)) | (1L << (With - 64)) | (1L << (Default - 64)) | (1L << (If - 64)) | (1L << (Throw - 64)) | (1L << (Delete - 64)) | (1L << (In - 64)) | (1L << (Try - 64)) | (1L << (As - 64)) | (1L << (From - 64)) | (1L << (Async - 64)) | (1L << (Class - 64)) | (1L << (Enum - 64)) | (1L << (Extends - 64)) | (1L << (Super - 64)) | (1L << (Const - 64)) | (1L << (Export - 64)) | (1L << (Import - 64)) | (1L << (Implements - 64)) | (1L << (Let - 64)) | (1L << (Private - 64)) | (1L << (Public - 64)) | (1L << (Interface - 64)) | (1L << (Package - 64)) | (1L << (Protected - 64)) | (1L << (Static - 64)) | (1L << (Yield - 64)) | (1L << (Any - 64)) | (1L << (Number - 64)) | (1L << (Boolean - 64)) | (1L << (String - 64)) | (1L << (Symbol - 64)) | (1L << (TypeAlias - 64)) | (1L << (Get - 64)) | (1L << (Set - 64)) | (1L << (Constructor - 64)) | (1L << (Namespace - 64)) | (1L << (Require - 64)) | (1L << (Module - 64)) | (1L << (Declare - 64)) | (1L << (Abstract - 64)) | (1L << (Is - 64)) | (1L << (Infer - 64)) | (1L << (Never - 64)) | (1L << (Unknown - 64)) | (1L << (Asserts - 64)))) != 0)) ) {
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
		enterRule(_localctx, 318, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1816);
			match(Get);
			setState(1817);
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
		enterRule(_localctx, 320, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1819);
			match(Set);
			setState(1820);
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
		enterRule(_localctx, 322, RULE_eos);
		try {
			setState(1826);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,243,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1822);
				match(SemiColon);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1823);
				match(EOF);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1824);
				if (!(this.lineTerminatorAhead())) throw new FailedPredicateException(this, "this.lineTerminatorAhead()");
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1825);
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
		case 58:
			return decoratorMemberExpression_sempred((DecoratorMemberExpressionContext)_localctx, predIndex);
		case 79:
			return expressionStatement_sempred((ExpressionStatementContext)_localctx, predIndex);
		case 81:
			return iterationStatement_sempred((IterationStatementContext)_localctx, predIndex);
		case 83:
			return continueStatement_sempred((ContinueStatementContext)_localctx, predIndex);
		case 84:
			return breakStatement_sempred((BreakStatementContext)_localctx, predIndex);
		case 85:
			return returnStatement_sempred((ReturnStatementContext)_localctx, predIndex);
		case 86:
			return yieldStatement_sempred((YieldStatementContext)_localctx, predIndex);
		case 94:
			return throwStatement_sempred((ThrowStatementContext)_localctx, predIndex);
		case 144:
			return singleExpression_sempred((SingleExpressionContext)_localctx, predIndex);
		case 161:
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
	private boolean expressionStatement_sempred(ExpressionStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return this.notOpenBraceAndNotFunction();
		}
		return true;
	}
	private boolean iterationStatement_sempred(IterationStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return this.p("of");
		case 3:
			return this.p("of");
		}
		return true;
	}
	private boolean continueStatement_sempred(ContinueStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean breakStatement_sempred(BreakStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean returnStatement_sempred(ReturnStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean yieldStatement_sempred(YieldStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return this.notLineTerminator();
		}
		return true;
	}
	private boolean throwStatement_sempred(ThrowStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return this.notLineTerminator();
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u008b\u0727\4\2\t"+
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
		"\t\u00a0\4\u00a1\t\u00a1\4\u00a2\t\u00a2\4\u00a3\t\u00a3\3\2\3\2\3\2\3"+
		"\3\3\3\5\3\u014c\n\3\3\4\3\4\5\4\u0150\n\4\3\4\3\4\3\5\3\5\3\5\7\5\u0157"+
		"\n\5\f\5\16\5\u015a\13\5\3\6\3\6\5\6\u015e\n\6\3\6\3\6\5\6\u0162\n\6\3"+
		"\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\5\13\u0176\n\13\3\f\5\f\u0179\n\f\3\f\3\f\3\f\7\f\u017e\n\f\f"+
		"\f\16\f\u0181\13\f\3\r\5\r\u0184\n\r\3\r\3\r\3\r\7\r\u0189\n\r\f\r\16"+
		"\r\u018c\13\r\3\16\5\16\u018f\n\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20"+
		"\3\20\3\20\7\20\u019a\n\20\f\20\16\20\u019d\13\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\7\20\u01a6\n\20\f\20\16\20\u01a9\13\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\7\20\u01b2\n\20\f\20\16\20\u01b5\13\20\5\20\u01b7"+
		"\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u01c4"+
		"\n\21\3\22\3\22\3\22\5\22\u01c9\n\22\3\23\5\23\u01cc\n\23\3\23\5\23\u01cf"+
		"\n\23\3\23\3\23\3\23\3\23\7\23\u01d5\n\23\f\23\16\23\u01d8\13\23\3\23"+
		"\5\23\u01db\n\23\3\23\3\23\5\23\u01df\n\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\5\23\u01e8\n\23\3\24\3\24\3\24\3\24\3\24\7\24\u01ef\n\24\f\24"+
		"\16\24\u01f2\13\24\3\24\5\24\u01f5\n\24\3\24\3\24\5\24\u01f9\n\24\3\25"+
		"\5\25\u01fc\n\25\3\25\5\25\u01ff\n\25\3\25\3\25\5\25\u0203\n\25\3\25\3"+
		"\25\5\25\u0207\n\25\3\26\3\26\3\26\5\26\u020c\n\26\3\27\3\27\3\27\5\27"+
		"\u0211\n\27\3\30\3\30\5\30\u0215\n\30\3\31\3\31\3\31\7\31\u021a\n\31\f"+
		"\31\16\31\u021d\13\31\3\32\3\32\3\32\5\32\u0222\n\32\3\32\3\32\3\33\3"+
		"\33\3\33\7\33\u0229\n\33\f\33\16\33\u022c\13\33\3\34\5\34\u022f\n\34\3"+
		"\34\3\34\3\35\3\35\5\35\u0235\n\35\3\35\3\35\3\36\3\36\5\36\u023b\n\36"+
		"\3\36\3\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3!\3!\3!\5!\u024a\n!\3\"\3\"\3"+
		"\"\5\"\u024f\n\"\3\"\3\"\7\"\u0253\n\"\f\"\16\"\u0256\13\"\3\"\5\"\u0259"+
		"\n\"\5\"\u025b\n\"\3#\5#\u025e\n#\3#\3#\5#\u0262\n#\3#\3#\3#\5#\u0267"+
		"\n#\3#\5#\u026a\n#\3$\3$\3$\3%\5%\u0270\n%\3%\3%\5%\u0274\n%\3%\3%\3%"+
		"\3&\3&\3\'\3\'\3\'\7\'\u027e\n\'\f\'\16\'\u0281\13\'\3(\3(\3(\3)\5)\u0287"+
		"\n)\3)\3)\5)\u028b\n)\3)\5)\u028e\n)\3)\3)\5)\u0292\n)\3*\3*\5*\u0296"+
		"\n*\3*\3*\5*\u029a\n*\3+\5+\u029d\n+\3+\5+\u02a0\n+\3+\3+\3+\5+\u02a5"+
		"\n+\3+\3+\3+\3+\5+\u02ab\n+\3+\3+\3+\5+\u02b0\n+\3+\3+\3,\3,\3,\3,\3,"+
		"\3,\3,\5,\u02bb\n,\3-\3-\5-\u02bf\n-\3-\3-\3.\3.\3.\5.\u02c6\n.\3.\3."+
		"\3.\5.\u02cb\n.\3/\3/\3/\5/\u02d0\n/\3/\5/\u02d3\n/\3/\3/\5/\u02d7\n/"+
		"\3/\3/\5/\u02db\n/\3\60\3\60\3\60\3\61\3\61\3\61\7\61\u02e3\n\61\f\61"+
		"\16\61\u02e6\13\61\3\62\5\62\u02e9\n\62\3\62\3\62\3\62\3\62\5\62\u02ef"+
		"\n\62\3\62\3\62\5\62\u02f3\n\62\3\63\3\63\5\63\u02f7\n\63\3\64\3\64\3"+
		"\64\7\64\u02fc\n\64\f\64\16\64\u02ff\13\64\3\65\3\65\3\65\5\65\u0304\n"+
		"\65\3\66\3\66\3\66\3\66\3\67\3\67\3\67\7\67\u030d\n\67\f\67\16\67\u0310"+
		"\13\67\38\38\38\38\39\39\3:\6:\u0319\n:\r:\16:\u031a\3;\3;\3;\5;\u0320"+
		"\n;\3<\3<\3<\3<\3<\3<\5<\u0328\n<\3<\3<\3<\7<\u032d\n<\f<\16<\u0330\13"+
		"<\3=\3=\3=\3>\5>\u0336\n>\3>\3>\3?\3?\3?\3?\3?\3?\3?\3?\3?\3?\3?\3?\3"+
		"?\3?\3?\3?\3?\3?\3?\5?\u034d\n?\3@\5@\u0350\n@\3@\3@\3A\3A\3A\3A\3A\3"+
		"A\3A\3A\5A\u035c\nA\3B\3B\5B\u0360\nB\3B\3B\3C\6C\u0365\nC\rC\16C\u0366"+
		"\3D\3D\3D\3D\5D\u036d\nD\3D\3D\3E\5E\u0372\nE\3E\3E\3E\3E\3E\5E\u0379"+
		"\nE\3E\3E\3E\3F\3F\3F\5F\u0381\nF\3F\3F\3F\3F\7F\u0387\nF\fF\16F\u038a"+
		"\13F\3F\5F\u038d\nF\3F\3F\3G\3G\3G\5G\u0394\nG\3H\3H\3H\3H\3H\3H\3H\5"+
		"H\u039d\nH\3I\3I\3I\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\5"+
		"J\u03b2\nJ\3J\3J\5J\u03b6\nJ\3J\3J\3J\5J\u03bb\nJ\3K\3K\3K\5K\u03c0\n"+
		"K\3K\3K\5K\u03c4\nK\3K\3K\5K\u03c8\nK\3L\5L\u03cb\nL\3L\5L\u03ce\nL\3"+
		"L\3L\3L\5L\u03d3\nL\3L\5L\u03d6\nL\3M\3M\5M\u03da\nM\3M\5M\u03dd\nM\3"+
		"N\3N\3N\7N\u03e2\nN\fN\16N\u03e5\13N\3O\3O\5O\u03e9\nO\3O\3O\5O\u03ed"+
		"\nO\3O\5O\u03f0\nO\3P\3P\3Q\3Q\3Q\5Q\u03f7\nQ\3R\3R\3R\3R\3R\3R\3R\5R"+
		"\u0400\nR\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\5S\u0413"+
		"\nS\3S\3S\5S\u0417\nS\3S\3S\5S\u041b\nS\3S\3S\3S\3S\3S\3S\3S\3S\5S\u0425"+
		"\nS\3S\3S\5S\u0429\nS\3S\3S\3S\3S\3S\3S\3S\3S\3S\5S\u0434\nS\3S\3S\3S"+
		"\3S\3S\3S\3S\3S\3S\3S\3S\5S\u0441\nS\3S\3S\3S\3S\5S\u0447\nS\3T\3T\3U"+
		"\3U\3U\5U\u044e\nU\3U\3U\3V\3V\3V\5V\u0455\nV\3V\3V\3W\3W\3W\5W\u045c"+
		"\nW\3W\3W\3X\3X\3X\5X\u0463\nX\3X\3X\3Y\3Y\3Y\3Y\3Y\3Y\3Z\3Z\3Z\3Z\3Z"+
		"\3Z\3[\3[\5[\u0475\n[\3[\3[\5[\u0479\n[\5[\u047b\n[\3[\3[\3\\\6\\\u0480"+
		"\n\\\r\\\16\\\u0481\3]\3]\3]\3]\5]\u0488\n]\3^\3^\3^\5^\u048d\n^\3_\3"+
		"_\3_\3_\3`\3`\3`\3`\3`\3a\3a\3a\3a\5a\u049c\na\3a\5a\u049f\na\3b\3b\3"+
		"b\3b\3b\3b\3c\3c\3c\3d\3d\3d\3e\3e\5e\u04af\ne\3e\3e\3e\5e\u04b4\ne\3"+
		"e\5e\u04b7\ne\3f\5f\u04ba\nf\3f\3f\3f\5f\u04bf\nf\3f\3f\3f\5f\u04c4\n"+
		"f\3g\5g\u04c7\ng\3g\5g\u04ca\ng\3h\3h\5h\u04ce\nh\3h\5h\u04d1\nh\3h\3"+
		"h\3i\3i\3i\5i\u04d8\ni\3i\7i\u04db\ni\fi\16i\u04de\13i\3j\3j\3j\3k\3k"+
		"\3k\3l\3l\5l\u04e8\nl\3l\3l\5l\u04ec\nl\3m\5m\u04ef\nm\3m\3m\3m\5m\u04f4"+
		"\nm\3n\3n\3n\3n\5n\u04fa\nn\3n\5n\u04fd\nn\3n\5n\u0500\nn\3n\3n\5n\u0504"+
		"\nn\5n\u0506\nn\3n\3n\5n\u050a\nn\5n\u050c\nn\3o\3o\3o\3o\5o\u0512\no"+
		"\3o\3o\3p\5p\u0517\np\3p\5p\u051a\np\3p\5p\u051d\np\3p\5p\u0520\np\3q"+
		"\5q\u0523\nq\3q\3q\3q\3q\3r\3r\3r\5r\u052c\nr\3r\3r\3r\3s\3s\3s\3s\7s"+
		"\u0535\ns\fs\16s\u0538\13s\3s\5s\u053b\ns\3s\3s\3t\3t\3t\3u\3u\3u\3u\7"+
		"u\u0546\nu\fu\16u\u0549\13u\3u\5u\u054c\nu\3u\3u\3v\3v\3v\3v\3v\3v\3w"+
		"\5w\u0557\nw\3w\3w\3w\3w\5w\u055d\nw\5w\u055f\nw\3x\3x\5x\u0563\nx\3x"+
		"\3x\3y\3y\5y\u0569\ny\3z\3z\3z\3z\7z\u056f\nz\fz\16z\u0572\13z\3z\3z\5"+
		"z\u0576\nz\5z\u0578\nz\3{\3{\3{\5{\u057d\n{\3|\3|\5|\u0581\n|\3}\5}\u0584"+
		"\n}\3}\5}\u0587\n}\3}\3}\5}\u058b\n}\3~\5~\u058e\n~\3~\5~\u0591\n~\3~"+
		"\3~\3~\5~\u0596\n~\3~\5~\u0599\n~\3~\5~\u059c\n~\3\177\3\177\3\u0080\3"+
		"\u0080\5\u0080\u05a2\n\u0080\3\u0081\3\u0081\5\u0081\u05a6\n\u0081\3\u0081"+
		"\3\u0081\3\u0082\3\u0082\6\u0082\u05ac\n\u0082\r\u0082\16\u0082\u05ad"+
		"\3\u0082\7\u0082\u05b1\n\u0082\f\u0082\16\u0082\u05b4\13\u0082\3\u0082"+
		"\5\u0082\u05b7\n\u0082\3\u0083\5\u0083\u05ba\n\u0083\3\u0083\3\u0083\3"+
		"\u0084\3\u0084\5\u0084\u05c0\n\u0084\3\u0085\3\u0085\5\u0085\u05c4\n\u0085"+
		"\3\u0086\3\u0086\3\u0086\3\u0086\5\u0086\u05ca\n\u0086\3\u0086\7\u0086"+
		"\u05cd\n\u0086\f\u0086\16\u0086\u05d0\13\u0086\3\u0087\3\u0087\3\u0087"+
		"\3\u0087\3\u0087\3\u0087\3\u0087\5\u0087\u05d9\n\u0087\5\u0087\u05db\n"+
		"\u0087\3\u0088\3\u0088\3\u0088\3\u0088\7\u0088\u05e1\n\u0088\f\u0088\16"+
		"\u0088\u05e4\13\u0088\3\u0088\5\u0088\u05e7\n\u0088\5\u0088\u05e9\n\u0088"+
		"\3\u0088\3\u0088\3\u0089\3\u0089\3\u0089\3\u0089\5\u0089\u05f1\n\u0089"+
		"\3\u0089\3\u0089\5\u0089\u05f5\n\u0089\3\u0089\3\u0089\3\u0089\3\u0089"+
		"\5\u0089\u05fb\n\u0089\3\u008a\3\u008a\3\u008a\3\u008a\5\u008a\u0601\n"+
		"\u008a\3\u008a\5\u008a\u0604\n\u008a\3\u008b\3\u008b\3\u008b\3\u008b\5"+
		"\u008b\u060a\n\u008b\3\u008b\5\u008b\u060d\n\u008b\3\u008b\3\u008b\5\u008b"+
		"\u0611\n\u008b\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c\3\u008c"+
		"\3\u008c\3\u008c\5\u008c\u061c\n\u008c\3\u008c\5\u008c\u061f\n\u008c\3"+
		"\u008d\3\u008d\3\u008d\5\u008d\u0624\n\u008d\5\u008d\u0626\n\u008d\3\u008d"+
		"\3\u008d\3\u008e\3\u008e\3\u008e\7\u008e\u062d\n\u008e\f\u008e\16\u008e"+
		"\u0630\13\u008e\3\u008f\5\u008f\u0633\n\u008f\3\u008f\3\u008f\5\u008f"+
		"\u0637\n\u008f\3\u0090\3\u0090\3\u0090\7\u0090\u063c\n\u0090\f\u0090\16"+
		"\u0090\u063f\13\u0090\3\u0091\3\u0091\5\u0091\u0643\n\u0091\3\u0091\5"+
		"\u0091\u0646\n\u0091\3\u0091\3\u0091\5\u0091\u064a\n\u0091\3\u0091\3\u0091"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\5\u0092\u0653\n\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\5\u0092\u0659\n\u0092\3\u0092\5\u0092\u065c\n"+
		"\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\5\u0092\u067f"+
		"\n\u0092\5\u0092\u0681\n\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\5\u0092"+
		"\u0690\n\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\5\u0092\u06bb"+
		"\n\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092"+
		"\3\u0092\3\u0092\3\u0092\3\u0092\3\u0092\7\u0092\u06ca\n\u0092\f\u0092"+
		"\16\u0092\u06cd\13\u0092\3\u0093\5\u0093\u06d0\n\u0093\3\u0093\3\u0093"+
		"\5\u0093\u06d4\n\u0093\3\u0093\3\u0093\3\u0093\3\u0094\3\u0094\5\u0094"+
		"\u06db\n\u0094\3\u0095\3\u0095\5\u0095\u06df\n\u0095\3\u0096\3\u0096\3"+
		"\u0097\3\u0097\3\u0097\3\u0097\3\u0097\3\u0097\5\u0097\u06e9\n\u0097\3"+
		"\u0098\3\u0098\7\u0098\u06ed\n\u0098\f\u0098\16\u0098\u06f0\13\u0098\3"+
		"\u0098\3\u0098\3\u0099\3\u0099\3\u0099\3\u0099\3\u0099\5\u0099\u06f9\n"+
		"\u0099\3\u009a\5\u009a\u06fc\n\u009a\3\u009a\3\u009a\3\u009a\3\u009a\3"+
		"\u009a\5\u009a\u0703\n\u009a\3\u009b\3\u009b\3\u009c\3\u009c\5\u009c\u0709"+
		"\n\u009c\3\u009d\3\u009d\5\u009d\u070d\n\u009d\3\u009e\3\u009e\3\u009e"+
		"\5\u009e\u0712\n\u009e\3\u009f\3\u009f\3\u009f\5\u009f\u0717\n\u009f\3"+
		"\u00a0\3\u00a0\3\u00a1\3\u00a1\3\u00a1\3\u00a2\3\u00a2\3\u00a2\3\u00a3"+
		"\3\u00a3\3\u00a3\3\u00a3\5\u00a3\u0725\n\u00a3\3\u00a3\2\4v\u0122\u00a4"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH"+
		"JLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c"+
		"\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4"+
		"\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc"+
		"\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0\u00d2\u00d4"+
		"\u00d6\u00d8\u00da\u00dc\u00de\u00e0\u00e2\u00e4\u00e6\u00e8\u00ea\u00ec"+
		"\u00ee\u00f0\u00f2\u00f4\u00f6\u00f8\u00fa\u00fc\u00fe\u0100\u0102\u0104"+
		"\u0106\u0108\u010a\u010c\u010e\u0110\u0112\u0114\u0116\u0118\u011a\u011c"+
		"\u011e\u0120\u0122\u0124\u0126\u0128\u012a\u012c\u012e\u0130\u0132\u0134"+
		"\u0136\u0138\u013a\u013c\u013e\u0140\u0142\u0144\2\21\4\2BC\\\\\4\2oo"+
		"qq\3\2\u0082\u0083\5\2GGbbff\4\2ghkk\3\2\f\r\3\2\31\33\3\2\25\26\3\2\35"+
		" \3\2!$\3\2%\'\3\2()\3\2*\64\5\2ssxx\u0082\u0082\7\2\66\67>@BBD[]\u0080"+
		"\2\u07e3\2\u0146\3\2\2\2\4\u014b\3\2\2\2\6\u014d\3\2\2\2\b\u0153\3\2\2"+
		"\2\n\u015b\3\2\2\2\f\u0163\3\2\2\2\16\u0166\3\2\2\2\20\u0168\3\2\2\2\22"+
		"\u016b\3\2\2\2\24\u016d\3\2\2\2\26\u0178\3\2\2\2\30\u0183\3\2\2\2\32\u018e"+
		"\3\2\2\2\34\u0192\3\2\2\2\36\u01b6\3\2\2\2 \u01c3\3\2\2\2\"\u01c8\3\2"+
		"\2\2$\u01ce\3\2\2\2&\u01e9\3\2\2\2(\u01fb\3\2\2\2*\u0208\3\2\2\2,\u0210"+
		"\3\2\2\2.\u0212\3\2\2\2\60\u0216\3\2\2\2\62\u021e\3\2\2\2\64\u0225\3\2"+
		"\2\2\66\u022e\3\2\2\28\u0232\3\2\2\2:\u0238\3\2\2\2<\u023e\3\2\2\2>\u0240"+
		"\3\2\2\2@\u0243\3\2\2\2B\u025a\3\2\2\2D\u025d\3\2\2\2F\u026b\3\2\2\2H"+
		"\u026f\3\2\2\2J\u0278\3\2\2\2L\u027a\3\2\2\2N\u0282\3\2\2\2P\u0286\3\2"+
		"\2\2R\u0293\3\2\2\2T\u029c\3\2\2\2V\u02ba\3\2\2\2X\u02bc\3\2\2\2Z\u02c2"+
		"\3\2\2\2\\\u02cc\3\2\2\2^\u02dc\3\2\2\2`\u02df\3\2\2\2b\u02e8\3\2\2\2"+
		"d\u02f4\3\2\2\2f\u02f8\3\2\2\2h\u0300\3\2\2\2j\u0305\3\2\2\2l\u0309\3"+
		"\2\2\2n\u0311\3\2\2\2p\u0315\3\2\2\2r\u0318\3\2\2\2t\u031c\3\2\2\2v\u0327"+
		"\3\2\2\2x\u0331\3\2\2\2z\u0335\3\2\2\2|\u034c\3\2\2\2~\u034f\3\2\2\2\u0080"+
		"\u035b\3\2\2\2\u0082\u035d\3\2\2\2\u0084\u0364\3\2\2\2\u0086\u0368\3\2"+
		"\2\2\u0088\u0371\3\2\2\2\u008a\u0380\3\2\2\2\u008c\u0390\3\2\2\2\u008e"+
		"\u0395\3\2\2\2\u0090\u039e\3\2\2\2\u0092\u03ba\3\2\2\2\u0094\u03c3\3\2"+
		"\2\2\u0096\u03ca\3\2\2\2\u0098\u03d7\3\2\2\2\u009a\u03de\3\2\2\2\u009c"+
		"\u03e6\3\2\2\2\u009e\u03f1\3\2\2\2\u00a0\u03f3\3\2\2\2\u00a2\u03f8\3\2"+
		"\2\2\u00a4\u0446\3\2\2\2\u00a6\u0448\3\2\2\2\u00a8\u044a\3\2\2\2\u00aa"+
		"\u0451\3\2\2\2\u00ac\u0458\3\2\2\2\u00ae\u045f\3\2\2\2\u00b0\u0466\3\2"+
		"\2\2\u00b2\u046c\3\2\2\2\u00b4\u0472\3\2\2\2\u00b6\u047f\3\2\2\2\u00b8"+
		"\u0483\3\2\2\2\u00ba\u0489\3\2\2\2\u00bc\u048e\3\2\2\2\u00be\u0492\3\2"+
		"\2\2\u00c0\u0497\3\2\2\2\u00c2\u04a0\3\2\2\2\u00c4\u04a6\3\2\2\2\u00c6"+
		"\u04a9\3\2\2\2\u00c8\u04ac\3\2\2\2\u00ca\u04b9\3\2\2\2\u00cc\u04c6\3\2"+
		"\2\2\u00ce\u04cb\3\2\2\2\u00d0\u04d4\3\2\2\2\u00d2\u04df\3\2\2\2\u00d4"+
		"\u04e2\3\2\2\2\u00d6\u04eb\3\2\2\2\u00d8\u04ee\3\2\2\2\u00da\u050b\3\2"+
		"\2\2\u00dc\u050d\3\2\2\2\u00de\u0516\3\2\2\2\u00e0\u0522\3\2\2\2\u00e2"+
		"\u0528\3\2\2\2\u00e4\u0530\3\2\2\2\u00e6\u053e\3\2\2\2\u00e8\u0541\3\2"+
		"\2\2\u00ea\u054f\3\2\2\2\u00ec\u0556\3\2\2\2\u00ee\u0560\3\2\2\2\u00f0"+
		"\u0566\3\2\2\2\u00f2\u0577\3\2\2\2\u00f4\u0579\3\2\2\2\u00f6\u0580\3\2"+
		"\2\2\u00f8\u0583\3\2\2\2\u00fa\u058d\3\2\2\2\u00fc\u059d\3\2\2\2\u00fe"+
		"\u05a1\3\2\2\2\u0100\u05a3\3\2\2\2\u0102\u05a9\3\2\2\2\u0104\u05b9\3\2"+
		"\2\2\u0106\u05bf\3\2\2\2\u0108\u05c1\3\2\2\2\u010a\u05c5\3\2\2\2\u010c"+
		"\u05da\3\2\2\2\u010e\u05dc\3\2\2\2\u0110\u05fa\3\2\2\2\u0112\u05fc\3\2"+
		"\2\2\u0114\u0605\3\2\2\2\u0116\u061e\3\2\2\2\u0118\u0620\3\2\2\2\u011a"+
		"\u0629\3\2\2\2\u011c\u0632\3\2\2\2\u011e\u0638\3\2\2\2\u0120\u0640\3\2"+
		"\2\2\u0122\u0680\3\2\2\2\u0124\u06cf\3\2\2\2\u0126\u06da\3\2\2\2\u0128"+
		"\u06de\3\2\2\2\u012a\u06e0\3\2\2\2\u012c\u06e8\3\2\2\2\u012e\u06ea\3\2"+
		"\2\2\u0130\u06f8\3\2\2\2\u0132\u0702\3\2\2\2\u0134\u0704\3\2\2\2\u0136"+
		"\u0708\3\2\2\2\u0138\u070c\3\2\2\2\u013a\u0711\3\2\2\2\u013c\u0716\3\2"+
		"\2\2\u013e\u0718\3\2\2\2\u0140\u071a\3\2\2\2\u0142\u071d\3\2\2\2\u0144"+
		"\u0724\3\2\2\2\u0146\u0147\7\16\2\2\u0147\u0148\5\u0122\u0092\2\u0148"+
		"\3\3\2\2\2\u0149\u014c\5\u0100\u0081\2\u014a\u014c\5\u010e\u0088\2\u014b"+
		"\u0149\3\2\2\2\u014b\u014a\3\2\2\2\u014c\5\3\2\2\2\u014d\u014f\7\35\2"+
		"\2\u014e\u0150\5\b\5\2\u014f\u014e\3\2\2\2\u014f\u0150\3\2\2\2\u0150\u0151"+
		"\3\2\2\2\u0151\u0152\7\36\2\2\u0152\7\3\2\2\2\u0153\u0158\5\n\6\2\u0154"+
		"\u0155\7\r\2\2\u0155\u0157\5\n\6\2\u0156\u0154\3\2\2\2\u0157\u015a\3\2"+
		"\2\2\u0158\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159\t\3\2\2\2\u015a\u0158"+
		"\3\2\2\2\u015b\u015d\5\u0136\u009c\2\u015c\u015e\5\f\7\2\u015d\u015c\3"+
		"\2\2\2\u015d\u015e\3\2\2\2\u015e\u0161\3\2\2\2\u015f\u0160\7\16\2\2\u0160"+
		"\u0162\5\16\b\2\u0161\u015f\3\2\2\2\u0161\u0162\3\2\2\2\u0162\13\3\2\2"+
		"\2\u0163\u0164\7`\2\2\u0164\u0165\5\22\n\2\u0165\r\3\2\2\2\u0166\u0167"+
		"\5\22\n\2\u0167\17\3\2\2\2\u0168\u0169\7\20\2\2\u0169\u016a\5\22\n\2\u016a"+
		"\21\3\2\2\2\u016b\u016c\5\24\13\2\u016c\23\3\2\2\2\u016d\u0175\5\26\f"+
		"\2\u016e\u016f\7`\2\2\u016f\u0170\5\26\f\2\u0170\u0171\7\17\2\2\u0171"+
		"\u0172\5\24\13\2\u0172\u0173\7\20\2\2\u0173\u0174\5\24\13\2\u0174\u0176"+
		"\3\2\2\2\u0175\u016e\3\2\2\2\u0175\u0176\3\2\2\2\u0176\25\3\2\2\2\u0177"+
		"\u0179\7\'\2\2\u0178\u0177\3\2\2\2\u0178\u0179\3\2\2\2\u0179\u017a\3\2"+
		"\2\2\u017a\u017f\5\30\r\2\u017b\u017c\7\'\2\2\u017c\u017e\5\30\r\2\u017d"+
		"\u017b\3\2\2\2\u017e\u0181\3\2\2\2\u017f\u017d\3\2\2\2\u017f\u0180\3\2"+
		"\2\2\u0180\27\3\2\2\2\u0181\u017f\3\2\2\2\u0182\u0184\7%\2\2\u0183\u0182"+
		"\3\2\2\2\u0183\u0184\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u018a\5\32\16\2"+
		"\u0186\u0187\7%\2\2\u0187\u0189\5\32\16\2\u0188\u0186\3\2\2\2\u0189\u018c"+
		"\3\2\2\2\u018a\u0188\3\2\2\2\u018a\u018b\3\2\2\2\u018b\31\3\2\2\2\u018c"+
		"\u018a\3\2\2\2\u018d\u018f\5\34\17\2\u018e\u018d\3\2\2\2\u018e\u018f\3"+
		"\2\2\2\u018f\u0190\3\2\2\2\u0190\u0191\5\36\20\2\u0191\33\3\2\2\2\u0192"+
		"\u0193\t\2\2\2\u0193\35\3\2\2\2\u0194\u0195\7\17\2\2\u0195\u0196\7\6\2"+
		"\2\u0196\u019b\7\7\2\2\u0197\u0198\7\6\2\2\u0198\u019a\7\7\2\2\u0199\u0197"+
		"\3\2\2\2\u019a\u019d\3\2\2\2\u019b\u0199\3\2\2\2\u019b\u019c\3\2\2\2\u019c"+
		"\u01b7\3\2\2\2\u019d\u019b\3\2\2\2\u019e\u019f\7\b\2\2\u019f\u01a0\7\17"+
		"\2\2\u01a0\u01a1\7\t\2\2\u01a1\u01a2\7\6\2\2\u01a2\u01a7\7\7\2\2\u01a3"+
		"\u01a4\7\6\2\2\u01a4\u01a6\7\7\2\2\u01a5\u01a3\3\2\2\2\u01a6\u01a9\3\2"+
		"\2\2\u01a7\u01a5\3\2\2\2\u01a7\u01a8\3\2\2\2\u01a8\u01b7\3\2\2\2\u01a9"+
		"\u01a7\3\2\2\2\u01aa\u01b3\5 \21\2\u01ab\u01ac\7\6\2\2\u01ac\u01b2\7\7"+
		"\2\2\u01ad\u01ae\7\6\2\2\u01ae\u01af\5\22\n\2\u01af\u01b0\7\7\2\2\u01b0"+
		"\u01b2\3\2\2\2\u01b1\u01ab\3\2\2\2\u01b1\u01ad\3\2\2\2\u01b2\u01b5\3\2"+
		"\2\2\u01b3\u01b1\3\2\2\2\u01b3\u01b4\3\2\2\2\u01b4\u01b7\3\2\2\2\u01b5"+
		"\u01b3\3\2\2\2\u01b6\u0194\3\2\2\2\u01b6\u019e\3\2\2\2\u01b6\u01aa\3\2"+
		"\2\2\u01b7\37\3\2\2\2\u01b8\u01c4\5\"\22\2\u01b9\u01c4\5$\23\2\u01ba\u01c4"+
		"\5&\24\2\u01bb\u01c4\5> \2\u01bc\u01c4\5@!\2\u01bd\u01c4\5N(\2\u01be\u01c4"+
		"\5,\27\2\u01bf\u01c0\7\b\2\2\u01c0\u01c1\5\22\n\2\u01c1\u01c2\7\t\2\2"+
		"\u01c2\u01c4\3\2\2\2\u01c3\u01b8\3\2\2\2\u01c3\u01b9\3\2\2\2\u01c3\u01ba"+
		"\3\2\2\2\u01c3\u01bb\3\2\2\2\u01c3\u01bc\3\2\2\2\u01c3\u01bd\3\2\2\2\u01c3"+
		"\u01be\3\2\2\2\u01c3\u01bf\3\2\2\2\u01c4!\3\2\2\2\u01c5\u01c9\78\2\2\u01c6"+
		"\u01c9\7\u0083\2\2\u01c7\u01c9\5\u0132\u009a\2\u01c8\u01c5\3\2\2\2\u01c8"+
		"\u01c6\3\2\2\2\u01c8\u01c7\3\2\2\2\u01c9#\3\2\2\2\u01ca\u01cc\7{\2\2\u01cb"+
		"\u01ca\3\2\2\2\u01cb\u01cc\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01cf\7F"+
		"\2\2\u01ce\u01cb\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01de\3\2\2\2\u01d0"+
		"\u01d1\7\35\2\2\u01d1\u01d6\5*\26\2\u01d2\u01d3\7\r\2\2\u01d3\u01d5\5"+
		"*\26\2\u01d4\u01d2\3\2\2\2\u01d5\u01d8\3\2\2\2\u01d6\u01d4\3\2\2\2\u01d6"+
		"\u01d7\3\2\2\2\u01d7\u01da\3\2\2\2\u01d8\u01d6\3\2\2\2\u01d9\u01db\7\r"+
		"\2\2\u01da\u01d9\3\2\2\2\u01da\u01db\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc"+
		"\u01dd\7\36\2\2\u01dd\u01df\3\2\2\2\u01de\u01d0\3\2\2\2\u01de\u01df\3"+
		"\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e1\7\b\2\2\u01e1\u01e2\5B\"\2\u01e2"+
		"\u01e3\7\t\2\2\u01e3\u01e4\7\65\2\2\u01e4\u01e7\3\2\2\2\u01e5\u01e8\5"+
		"H%\2\u01e6\u01e8\5\26\f\2\u01e7\u01e5\3\2\2\2\u01e7\u01e6\3\2\2\2\u01e8"+
		"%\3\2\2\2\u01e9\u01f8\7\6\2\2\u01ea\u01f9\7\7\2\2\u01eb\u01f0\5(\25\2"+
		"\u01ec\u01ed\7\r\2\2\u01ed\u01ef\5(\25\2\u01ee\u01ec\3\2\2\2\u01ef\u01f2"+
		"\3\2\2\2\u01f0\u01ee\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\u01f4\3\2\2\2\u01f2"+
		"\u01f0\3\2\2\2\u01f3\u01f5\7\r\2\2\u01f4\u01f3\3\2\2\2\u01f4\u01f5\3\2"+
		"\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f7\7\7\2\2\u01f7\u01f9\3\2\2\2\u01f8"+
		"\u01ea\3\2\2\2\u01f8\u01eb\3\2\2\2\u01f9\'\3\2\2\2\u01fa\u01fc\7\21\2"+
		"\2\u01fb\u01fa\3\2\2\2\u01fb\u01fc\3\2\2\2\u01fc\u01fe\3\2\2\2\u01fd\u01ff"+
		"\7}\2\2\u01fe\u01fd\3\2\2\2\u01fe\u01ff\3\2\2\2\u01ff\u0202\3\2\2\2\u0200"+
		"\u0201\7\u0082\2\2\u0201\u0203\7\20\2\2\u0202\u0200\3\2\2\2\u0202\u0203"+
		"\3\2\2\2\u0203\u0204\3\2\2\2\u0204\u0206\5\22\n\2\u0205\u0207\7\17\2\2"+
		"\u0206\u0205\3\2\2\2\u0206\u0207\3\2\2\2\u0207)\3\2\2\2\u0208\u020b\7"+
		"\u0082\2\2\u0209\u020a\7`\2\2\u020a\u020c\5\22\n\2\u020b\u0209\3\2\2\2"+
		"\u020b\u020c\3\2\2\2\u020c+\3\2\2\2\u020d\u0211\5.\30\2\u020e\u0211\5"+
		":\36\2\u020f\u0211\5<\37\2\u0210\u020d\3\2\2\2\u0210\u020e\3\2\2\2\u0210"+
		"\u020f\3\2\2\2\u0211-\3\2\2\2\u0212\u0214\5\60\31\2\u0213\u0215\5\62\32"+
		"\2\u0214\u0213\3\2\2\2\u0214\u0215\3\2\2\2\u0215/\3\2\2\2\u0216\u021b"+
		"\5\u013a\u009e\2\u0217\u0218\7\22\2\2\u0218\u021a\5\u013a\u009e\2\u0219"+
		"\u0217\3\2\2\2\u021a\u021d\3\2\2\2\u021b\u0219\3\2\2\2\u021b\u021c\3\2"+
		"\2\2\u021c\61\3\2\2\2\u021d\u021b\3\2\2\2\u021e\u021f\7\35\2\2\u021f\u0221"+
		"\5\64\33\2\u0220\u0222\7\r\2\2\u0221\u0220\3\2\2\2\u0221\u0222\3\2\2\2"+
		"\u0222\u0223\3\2\2\2\u0223\u0224\7\36\2\2\u0224\63\3\2\2\2\u0225\u022a"+
		"\5\66\34\2\u0226\u0227\7\r\2\2\u0227\u0229\5\66\34\2\u0228\u0226\3\2\2"+
		"\2\u0229\u022c\3\2\2\2\u022a\u0228\3\2\2\2\u022a\u022b\3\2\2\2\u022b\65"+
		"\3\2\2\2\u022c\u022a\3\2\2\2\u022d\u022f\7}\2\2\u022e\u022d\3\2\2\2\u022e"+
		"\u022f\3\2\2\2\u022f\u0230\3\2\2\2\u0230\u0231\5\22\n\2\u0231\67\3\2\2"+
		"\2\u0232\u0234\7\35\2\2\u0233\u0235\5\64\33\2\u0234\u0233\3\2\2\2\u0234"+
		"\u0235\3\2\2\2\u0235\u0236\3\2\2\2\u0236\u0237\7\36\2\2\u02379\3\2\2\2"+
		"\u0238\u023a\7\n\2\2\u0239\u023b\5\u0108\u0085\2\u023a\u0239\3\2\2\2\u023a"+
		"\u023b\3\2\2\2\u023b\u023c\3\2\2\2\u023c\u023d\7\13\2\2\u023d;\3\2\2\2"+
		"\u023e\u023f\7R\2\2\u023f=\3\2\2\2\u0240\u0241\7A\2\2\u0241\u0242\5L\'"+
		"\2\u0242?\3\2\2\2\u0243\u0244\7d\2\2\u0244\u0245\7\b\2\2\u0245\u0246\7"+
		"\u0083\2\2\u0246\u0249\7\t\2\2\u0247\u0248\7\22\2\2\u0248\u024a\5.\30"+
		"\2\u0249\u0247\3\2\2\2\u0249\u024a\3\2\2\2\u024aA\3\2\2\2\u024b\u024c"+
		"\7R\2\2\u024c\u024f\5\20\t\2\u024d\u024f\5D#\2\u024e\u024b\3\2\2\2\u024e"+
		"\u024d\3\2\2\2\u024f\u0254\3\2\2\2\u0250\u0251\7\r\2\2\u0251\u0253\5D"+
		"#\2\u0252\u0250\3\2\2\2\u0253\u0256\3\2\2\2\u0254\u0252\3\2\2\2\u0254"+
		"\u0255\3\2\2\2\u0255\u0258\3\2\2\2\u0256\u0254\3\2\2\2\u0257\u0259\7\r"+
		"\2\2\u0258\u0257\3\2\2\2\u0258\u0259\3\2\2\2\u0259\u025b\3\2\2\2\u025a"+
		"\u024e\3\2\2\2\u025a\u025b\3\2\2\2\u025bC\3\2\2\2\u025c\u025e\7\21\2\2"+
		"\u025d\u025c\3\2\2\2\u025d\u025e\3\2\2\2\u025e\u0266\3\2\2\2\u025f\u0261"+
		"\5J&\2\u0260\u0262\7\17\2\2\u0261\u0260\3\2\2\2\u0261\u0262\3\2\2\2\u0262"+
		"\u0263\3\2\2\2\u0263\u0264\5\20\t\2\u0264\u0267\3\2\2\2\u0265\u0267\5"+
		"\22\n\2\u0266\u025f\3\2\2\2\u0266\u0265\3\2\2\2\u0267\u0269\3\2\2\2\u0268"+
		"\u026a\5F$\2\u0269\u0268\3\2\2\2\u0269\u026a\3\2\2\2\u026aE\3\2\2\2\u026b"+
		"\u026c\7\16\2\2\u026c\u026d\7\u0082\2\2\u026dG\3\2\2\2\u026e\u0270\7\u0080"+
		"\2\2\u026f\u026e\3\2\2\2\u026f\u0270\3\2\2\2\u0270\u0273\3\2\2\2\u0271"+
		"\u0274\7R\2\2\u0272\u0274\5J&\2\u0273\u0271\3\2\2\2\u0273\u0272\3\2\2"+
		"\2\u0274\u0275\3\2\2\2\u0275\u0276\7|\2\2\u0276\u0277\5\26\f\2\u0277I"+
		"\3\2\2\2\u0278\u0279\5\u0136\u009c\2\u0279K\3\2\2\2\u027a\u027f\5\u013a"+
		"\u009e\2\u027b\u027c\7\22\2\2\u027c\u027e\5\u013a\u009e\2\u027d\u027b"+
		"\3\2\2\2\u027e\u0281\3\2\2\2\u027f\u027d\3\2\2\2\u027f\u0280\3\2\2\2\u0280"+
		"M\3\2\2\2\u0281\u027f\3\2\2\2\u0282\u0283\7}\2\2\u0283\u0284\5\u013a\u009e"+
		"\2\u0284O\3\2\2\2\u0285\u0287\7\\\2\2\u0286\u0285\3\2\2\2\u0286\u0287"+
		"\3\2\2\2\u0287\u0288\3\2\2\2\u0288\u028a\5\u0116\u008c\2\u0289\u028b\7"+
		"\17\2\2\u028a\u0289\3\2\2\2\u028a\u028b\3\2\2\2\u028b\u028d\3\2\2\2\u028c"+
		"\u028e\5\20\t\2\u028d\u028c\3\2\2\2\u028d\u028e\3\2\2\2\u028e\u0291\3"+
		"\2\2\2\u028f\u0290\7\65\2\2\u0290\u0292\5\22\n\2\u0291\u028f\3\2\2\2\u0291"+
		"\u0292\3\2\2\2\u0292Q\3\2\2\2\u0293\u0295\7F\2\2\u0294\u0296\5\6\4\2\u0295"+
		"\u0294\3\2\2\2\u0295\u0296\3\2\2\2\u0296\u0297\3\2\2\2\u0297\u0299\5\u00ee"+
		"x\2\u0298\u029a\5\20\t\2\u0299\u0298\3\2\2\2\u0299\u029a\3\2\2\2\u029a"+
		"S\3\2\2\2\u029b\u029d\7\\\2\2\u029c\u029b\3\2\2\2\u029c\u029d\3\2\2\2"+
		"\u029d\u02a4\3\2\2\2\u029e\u02a0\7\25\2\2\u029f\u029e\3\2\2\2\u029f\u02a0"+
		"\3\2\2\2\u02a0\u02a1\3\2\2\2\u02a1\u02a5\7\\\2\2\u02a2\u02a3\7\26\2\2"+
		"\u02a3\u02a5\7\\\2\2\u02a4\u029f\3\2\2\2\u02a4\u02a2\3\2\2\2\u02a4\u02a5"+
		"\3\2\2\2\u02a5\u02a6\3\2\2\2\u02a6\u02a7\7\6\2\2\u02a7\u02a8\5V,\2\u02a8"+
		"\u02af\7\7\2\2\u02a9\u02ab\7\25\2\2\u02aa\u02a9\3\2\2\2\u02aa\u02ab\3"+
		"\2\2\2\u02ab\u02ac\3\2\2\2\u02ac\u02b0\7\17\2\2\u02ad\u02ae\7\26\2\2\u02ae"+
		"\u02b0\7\17\2\2\u02af\u02aa\3\2\2\2\u02af\u02ad\3\2\2\2\u02af\u02b0\3"+
		"\2\2\2\u02b0\u02b1\3\2\2\2\u02b1\u02b2\5\20\t\2\u02b2U\3\2\2\2\u02b3\u02b4"+
		"\5\u0136\u009c\2\u02b4\u02b5\7\20\2\2\u02b5\u02b6\t\3\2\2\u02b6\u02bb"+
		"\3\2\2\2\u02b7\u02b8\7\u0082\2\2\u02b8\u02b9\7X\2\2\u02b9\u02bb\5\22\n"+
		"\2\u02ba\u02b3\3\2\2\2\u02ba\u02b7\3\2\2\2\u02bbW\3\2\2\2\u02bc\u02be"+
		"\5\u0116\u008c\2\u02bd\u02bf\7\17\2\2\u02be\u02bd\3\2\2\2\u02be\u02bf"+
		"\3\2\2\2\u02bf\u02c0\3\2\2\2\u02c0\u02c1\5\u00ecw\2\u02c1Y\3\2\2\2\u02c2"+
		"\u02c3\7s\2\2\u02c3\u02c5\5\u0136\u009c\2\u02c4\u02c6\5\6\4\2\u02c5\u02c4"+
		"\3\2\2\2\u02c5\u02c6\3\2\2\2\u02c6\u02c7\3\2\2\2\u02c7\u02c8\7\16\2\2"+
		"\u02c8\u02ca\5\22\n\2\u02c9\u02cb\7\f\2\2\u02ca\u02c9\3\2\2\2\u02ca\u02cb"+
		"\3\2\2\2\u02cb[\3\2\2\2\u02cc\u02cd\7i\2\2\u02cd\u02cf\5\u0136\u009c\2"+
		"\u02ce\u02d0\5\6\4\2\u02cf\u02ce\3\2\2\2\u02cf\u02d0\3\2\2\2\u02d0\u02d2"+
		"\3\2\2\2\u02d1\u02d3\5^\60\2\u02d2\u02d1\3\2\2\2\u02d2\u02d3\3\2\2\2\u02d3"+
		"\u02d4\3\2\2\2\u02d4\u02d6\7\n\2\2\u02d5\u02d7\5\u0108\u0085\2\u02d6\u02d5"+
		"\3\2\2\2\u02d6\u02d7\3\2\2\2\u02d7\u02d8\3\2\2\2\u02d8\u02da\7\13\2\2"+
		"\u02d9\u02db\7\f\2\2\u02da\u02d9\3\2\2\2\u02da\u02db\3\2\2\2\u02db]\3"+
		"\2\2\2\u02dc\u02dd\7`\2\2\u02dd\u02de\5`\61\2\u02de_\3\2\2\2\u02df\u02e4"+
		"\5.\30\2\u02e0\u02e1\7\r\2\2\u02e1\u02e3\5.\30\2\u02e2\u02e0\3\2\2\2\u02e3"+
		"\u02e6\3\2\2\2\u02e4\u02e2\3\2\2\2\u02e4\u02e5\3\2\2\2\u02e5a\3\2\2\2"+
		"\u02e6\u02e4\3\2\2\2\u02e7\u02e9\7b\2\2\u02e8\u02e7\3\2\2\2\u02e8\u02e9"+
		"\3\2\2\2\u02e9\u02ea\3\2\2\2\u02ea\u02eb\7_\2\2\u02eb\u02ec\7\u0082\2"+
		"\2\u02ec\u02ee\7\n\2\2\u02ed\u02ef\5d\63\2\u02ee\u02ed\3\2\2\2\u02ee\u02ef"+
		"\3\2\2\2\u02ef\u02f0\3\2\2\2\u02f0\u02f2\7\13\2\2\u02f1\u02f3\7\f\2\2"+
		"\u02f2\u02f1\3\2\2\2\u02f2\u02f3\3\2\2\2\u02f3c\3\2\2\2\u02f4\u02f6\5"+
		"f\64\2\u02f5\u02f7\7\r\2\2\u02f6\u02f5\3\2\2\2\u02f6\u02f7\3\2\2\2\u02f7"+
		"e\3\2\2\2\u02f8\u02fd\5h\65\2\u02f9\u02fa\7\r\2\2\u02fa\u02fc\5h\65\2"+
		"\u02fb\u02f9\3\2\2\2\u02fc\u02ff\3\2\2\2\u02fd\u02fb\3\2\2\2\u02fd\u02fe"+
		"\3\2\2\2\u02feg\3\2\2\2\u02ff\u02fd\3\2\2\2\u0300\u0303\5\u0116\u008c"+
		"\2\u0301\u0302\7\16\2\2\u0302\u0304\5\u0122\u0092\2\u0303\u0301\3\2\2"+
		"\2\u0303\u0304\3\2\2\2\u0304i\3\2\2\2\u0305\u0306\7w\2\2\u0306\u0307\5"+
		"l\67\2\u0307\u0308\5\u0082B\2\u0308k\3\2\2\2\u0309\u030e\5\u013a\u009e"+
		"\2\u030a\u030b\7\22\2\2\u030b\u030d\5\u013a\u009e\2\u030c\u030a\3\2\2"+
		"\2\u030d\u0310\3\2\2\2\u030e\u030c\3\2\2\2\u030e\u030f\3\2\2\2\u030fm"+
		"\3\2\2\2\u0310\u030e\3\2\2\2\u0311\u0312\7y\2\2\u0312\u0313\5p9\2\u0313"+
		"\u0314\5\u0082B\2\u0314o\3\2\2\2\u0315\u0316\t\4\2\2\u0316q\3\2\2\2\u0317"+
		"\u0319\5t;\2\u0318\u0317\3\2\2\2\u0319\u031a\3\2\2\2\u031a\u0318\3\2\2"+
		"\2\u031a\u031b\3\2\2\2\u031bs\3\2\2\2\u031c\u031f\7\u0081\2\2\u031d\u0320"+
		"\5v<\2\u031e\u0320\5x=\2\u031f\u031d\3\2\2\2\u031f\u031e\3\2\2\2\u0320"+
		"u\3\2\2\2\u0321\u0322\b<\1\2\u0322\u0328\7\u0082\2\2\u0323\u0324\7\b\2"+
		"\2\u0324\u0325\5\u0122\u0092\2\u0325\u0326\7\t\2\2\u0326\u0328\3\2\2\2"+
		"\u0327\u0321\3\2\2\2\u0327\u0323\3\2\2\2\u0328\u032e\3\2\2\2\u0329\u032a"+
		"\f\4\2\2\u032a\u032b\7\22\2\2\u032b\u032d\5\u0136\u009c\2\u032c\u0329"+
		"\3\2\2\2\u032d\u0330\3\2\2\2\u032e\u032c\3\2\2\2\u032e\u032f\3\2\2\2\u032f"+
		"w\3\2\2\2\u0330\u032e\3\2\2\2\u0331\u0332\5v<\2\u0332\u0333\5\u0118\u008d"+
		"\2\u0333y\3\2\2\2\u0334\u0336\5\u0084C\2\u0335\u0334\3\2\2\2\u0335\u0336"+
		"\3\2\2\2\u0336\u0337\3\2\2\2\u0337\u0338\7\2\2\3\u0338{\3\2\2\2\u0339"+
		"\u034d\5\u0082B\2\u033a\u034d\5\u0086D\2\u033b\u034d\5r:\2\u033c\u034d"+
		"\5\u0090I\2\u033d\u034d\5~@\2\u033e\u034d\5\u0124\u0093\2\u033f\u034d"+
		"\5\u00a2R\2\u0340\u034d\5\u00a4S\2\u0341\u034d\5\u00a8U\2\u0342\u034d"+
		"\5\u00aaV\2\u0343\u034d\5\u00acW\2\u0344\u034d\5\u00aeX\2\u0345\u034d"+
		"\5\u00b0Y\2\u0346\u034d\5\u00bc_\2\u0347\u034d\5\u00b2Z\2\u0348\u034d"+
		"\5\u00be`\2\u0349\u034d\5\u00c0a\2\u034a\u034d\5\u00c6d\2\u034b\u034d"+
		"\5\u009eP\2\u034c\u0339\3\2\2\2\u034c\u033a\3\2\2\2\u034c\u033b\3\2\2"+
		"\2\u034c\u033c\3\2\2\2\u034c\u033d\3\2\2\2\u034c\u033e\3\2\2\2\u034c\u033f"+
		"\3\2\2\2\u034c\u0340\3\2\2\2\u034c\u0341\3\2\2\2\u034c\u0342\3\2\2\2\u034c"+
		"\u0343\3\2\2\2\u034c\u0344\3\2\2\2\u034c\u0345\3\2\2\2\u034c\u0346\3\2"+
		"\2\2\u034c\u0347\3\2\2\2\u034c\u0348\3\2\2\2\u034c\u0349\3\2\2\2\u034c"+
		"\u034a\3\2\2\2\u034c\u034b\3\2\2\2\u034d}\3\2\2\2\u034e\u0350\7z\2\2\u034f"+
		"\u034e\3\2\2\2\u034f\u0350\3\2\2\2\u0350\u0351\3\2\2\2\u0351\u0352\5\u0080"+
		"A\2\u0352\177\3\2\2\2\u0353\u035c\5n8\2\u0354\u035c\5j\66\2\u0355\u035c"+
		"\5\\/\2\u0356\u035c\5Z.\2\u0357\u035c\5\u00c8e\2\u0358\u035c\5\u00caf"+
		"\2\u0359\u035c\5b\62\2\u035a\u035c\5\u0096L\2\u035b\u0353\3\2\2\2\u035b"+
		"\u0354\3\2\2\2\u035b\u0355\3\2\2\2\u035b\u0356\3\2\2\2\u035b\u0357\3\2"+
		"\2\2\u035b\u0358\3\2\2\2\u035b\u0359\3\2\2\2\u035b\u035a\3\2\2\2\u035c"+
		"\u0081\3\2\2\2\u035d\u035f\7\n\2\2\u035e\u0360\5\u0084C\2\u035f\u035e"+
		"\3\2\2\2\u035f\u0360\3\2\2\2\u0360\u0361\3\2\2\2\u0361\u0362\7\13\2\2"+
		"\u0362\u0083\3\2\2\2\u0363\u0365\5|?\2\u0364\u0363\3\2\2\2\u0365\u0366"+
		"\3\2\2\2\u0366\u0364\3\2\2\2\u0366\u0367\3\2\2\2\u0367\u0085\3\2\2\2\u0368"+
		"\u036c\7d\2\2\u0369\u036d\5\u0088E\2\u036a\u036d\5\u008eH\2\u036b\u036d"+
		"\7\u0083\2\2\u036c\u0369\3\2\2\2\u036c\u036a\3\2\2\2\u036c\u036b\3\2\2"+
		"\2\u036d\u036e\3\2\2\2\u036e\u036f\5\u0144\u00a3\2\u036f\u0087\3\2\2\2"+
		"\u0370\u0372\7s\2\2\u0371\u0370\3\2\2\2\u0371\u0372\3\2\2\2\u0372\u0378"+
		"\3\2\2\2\u0373\u0374\7\31\2\2\u0374\u0375\7Z\2\2\u0375\u0379\5\u0136\u009c"+
		"\2\u0376\u0379\5\u0136\u009c\2\u0377\u0379\5\u008aF\2\u0378\u0373\3\2"+
		"\2\2\u0378\u0376\3\2\2\2\u0378\u0377\3\2\2\2\u0379\u037a\3\2\2\2\u037a"+
		"\u037b\7[\2\2\u037b\u037c\7\u0083\2\2\u037c\u0089\3\2\2\2\u037d\u037e"+
		"\5\u0136\u009c\2\u037e\u037f\7\r\2\2\u037f\u0381\3\2\2\2\u0380\u037d\3"+
		"\2\2\2\u0380\u0381\3\2\2\2\u0381\u0382\3\2\2\2\u0382\u0383\7\n\2\2\u0383"+
		"\u0388\5\u008cG\2\u0384\u0385\7\r\2\2\u0385\u0387\5\u008cG\2\u0386\u0384"+
		"\3\2\2\2\u0387\u038a\3\2\2\2\u0388\u0386\3\2\2\2\u0388\u0389\3\2\2\2\u0389"+
		"\u038c\3\2\2\2\u038a\u0388\3\2\2\2\u038b\u038d\7\r\2\2\u038c\u038b\3\2"+
		"\2\2\u038c\u038d\3\2\2\2\u038d\u038e\3\2\2\2\u038e\u038f\7\13\2\2\u038f"+
		"\u008b\3\2\2\2\u0390\u0393\5\u0136\u009c\2\u0391\u0392\7Z\2\2\u0392\u0394"+
		"\5\u0136\u009c\2\u0393\u0391\3\2\2\2\u0393\u0394\3\2\2\2\u0394\u008d\3"+
		"\2\2\2\u0395\u0396\7\u0082\2\2\u0396\u039c\7\16\2\2\u0397\u039d\5l\67"+
		"\2\u0398\u0399\7x\2\2\u0399\u039a\7\b\2\2\u039a\u039b\7\u0083\2\2\u039b"+
		"\u039d\7\t\2\2\u039c\u0397\3\2\2\2\u039c\u0398\3\2\2\2\u039d\u008f\3\2"+
		"\2\2\u039e\u039f\7c\2\2\u039f\u03a0\5\u0092J\2\u03a0\u0091\3\2\2\2\u03a1"+
		"\u03a2\7Z\2\2\u03a2\u03a3\7w\2\2\u03a3\u03a4\5\u0136\u009c\2\u03a4\u03a5"+
		"\5\u0144\u00a3\2\u03a5\u03bb\3\2\2\2\u03a6\u03a7\7\16\2\2\u03a7\u03a8"+
		"\5l\67\2\u03a8\u03a9\5\u0144\u00a3\2\u03a9\u03bb\3\2\2\2\u03aa\u03ab\7"+
		"d\2\2\u03ab\u03ac\5\u0136\u009c\2\u03ac\u03ad\7\16\2\2\u03ad\u03ae\5l"+
		"\67\2\u03ae\u03af\5\u0144\u00a3\2\u03af\u03bb\3\2\2\2\u03b0\u03b2\7T\2"+
		"\2\u03b1\u03b0\3\2\2\2\u03b1\u03b2\3\2\2\2\u03b2\u03b3\3\2\2\2\u03b3\u03bb"+
		"\5~@\2\u03b4\u03b6\7T\2\2\u03b5\u03b4\3\2\2\2\u03b5\u03b6\3\2\2\2\u03b6"+
		"\u03b7\3\2\2\2\u03b7\u03b8\5\u0094K\2\u03b8\u03b9\5\u0144\u00a3\2\u03b9"+
		"\u03bb\3\2\2\2\u03ba\u03a1\3\2\2\2\u03ba\u03a6\3\2\2\2\u03ba\u03aa\3\2"+
		"\2\2\u03ba\u03b1\3\2\2\2\u03ba\u03b5\3\2\2\2\u03bb\u0093\3\2\2\2\u03bc"+
		"\u03bf\7\31\2\2\u03bd\u03be\7Z\2\2\u03be\u03c0\5\u0136\u009c\2\u03bf\u03bd"+
		"\3\2\2\2\u03bf\u03c0\3\2\2\2\u03c0\u03c4\3\2\2\2\u03c1\u03c4\5\u0136\u009c"+
		"\2\u03c2\u03c4\5\u008aF\2\u03c3\u03bc\3\2\2\2\u03c3\u03c1\3\2\2\2\u03c3"+
		"\u03c2\3\2\2\2\u03c4\u03c7\3\2\2\2\u03c5\u03c6\7[\2\2\u03c6\u03c8\7\u0083"+
		"\2\2\u03c7\u03c5\3\2\2\2\u03c7\u03c8\3\2\2\2\u03c8\u0095\3\2\2\2\u03c9"+
		"\u03cb\5\u00fc\177\2\u03ca\u03c9\3\2\2\2\u03ca\u03cb\3\2\2\2\u03cb\u03cd"+
		"\3\2\2\2\u03cc\u03ce\7\\\2\2\u03cd\u03cc\3\2\2\2\u03cd\u03ce\3\2\2\2\u03ce"+
		"\u03cf\3\2\2\2\u03cf\u03d2\5\u00a6T\2\u03d0\u03d3\5\u0098M\2\u03d1\u03d3"+
		"\5\u009aN\2\u03d2\u03d0\3\2\2\2\u03d2\u03d1\3\2\2\2\u03d3\u03d5\3\2\2"+
		"\2\u03d4\u03d6\7\f\2\2\u03d5\u03d4\3\2\2\2\u03d5\u03d6\3\2\2\2\u03d6\u0097"+
		"\3\2\2\2\u03d7\u03d9\5\4\3\2\u03d8\u03da\5\20\t\2\u03d9\u03d8\3\2\2\2"+
		"\u03d9\u03da\3\2\2\2\u03da\u03dc\3\2\2\2\u03db\u03dd\5\2\2\2\u03dc\u03db"+
		"\3\2\2\2\u03dc\u03dd\3\2\2\2\u03dd\u0099\3\2\2\2\u03de\u03e3\5\u009cO"+
		"\2\u03df\u03e0\7\r\2\2\u03e0\u03e2\5\u009cO\2\u03e1\u03df\3\2\2\2\u03e2"+
		"\u03e5\3\2\2\2\u03e3\u03e1\3\2\2\2\u03e3\u03e4\3\2\2\2\u03e4\u009b\3\2"+
		"\2\2\u03e5\u03e3\3\2\2\2\u03e6\u03e8\5\u0136\u009c\2\u03e7\u03e9\5\20"+
		"\t\2\u03e8\u03e7\3\2\2\2\u03e8\u03e9\3\2\2\2\u03e9\u03ef\3\2\2\2\u03ea"+
		"\u03ec\7\16\2\2\u03eb\u03ed\5\6\4\2\u03ec\u03eb\3\2\2\2\u03ec\u03ed\3"+
		"\2\2\2\u03ed\u03ee\3\2\2\2\u03ee\u03f0\5\u0122\u0092\2\u03ef\u03ea\3\2"+
		"\2\2\u03ef\u03f0\3\2\2\2\u03f0\u009d\3\2\2\2\u03f1\u03f2\7\f\2\2\u03f2"+
		"\u009f\3\2\2\2\u03f3\u03f4\6Q\3\2\u03f4\u03f6\5\u011e\u0090\2\u03f5\u03f7"+
		"\7\f\2\2\u03f6\u03f5\3\2\2\2\u03f6\u03f7\3\2\2\2\u03f7\u00a1\3\2\2\2\u03f8"+
		"\u03f9\7U\2\2\u03f9\u03fa\7\b\2\2\u03fa\u03fb\5\u011e\u0090\2\u03fb\u03fc"+
		"\7\t\2\2\u03fc\u03ff\5|?\2\u03fd\u03fe\7E\2\2\u03fe\u0400\5|?\2\u03ff"+
		"\u03fd\3\2\2\2\u03ff\u0400\3\2\2\2\u0400\u00a3\3\2\2\2\u0401\u0402\7?"+
		"\2\2\u0402\u0403\5|?\2\u0403\u0404\7O\2\2\u0404\u0405\7\b\2\2\u0405\u0406"+
		"\5\u011e\u0090\2\u0406\u0407\7\t\2\2\u0407\u0408\5\u0144\u00a3\2\u0408"+
		"\u0447\3\2\2\2\u0409\u040a\7O\2\2\u040a\u040b\7\b\2\2\u040b\u040c\5\u011e"+
		"\u0090\2\u040c\u040d\7\t\2\2\u040d\u040e\5|?\2\u040e\u0447\3\2\2\2\u040f"+
		"\u0410\7M\2\2\u0410\u0412\7\b\2\2\u0411\u0413\5\u011e\u0090\2\u0412\u0411"+
		"\3\2\2\2\u0412\u0413\3\2\2\2\u0413\u0414\3\2\2\2\u0414\u0416\7\f\2\2\u0415"+
		"\u0417\5\u011e\u0090\2\u0416\u0415\3\2\2\2\u0416\u0417\3\2\2\2\u0417\u0418"+
		"\3\2\2\2\u0418\u041a\7\f\2\2\u0419\u041b\5\u011e\u0090\2\u041a\u0419\3"+
		"\2\2\2\u041a\u041b\3\2\2\2\u041b\u041c\3\2\2\2\u041c\u041d\7\t\2\2\u041d"+
		"\u0447\5|?\2\u041e\u041f\7M\2\2\u041f\u0420\7\b\2\2\u0420\u0421\5\u00a6"+
		"T\2\u0421\u0422\5\u009aN\2\u0422\u0424\7\f\2\2\u0423\u0425\5\u011e\u0090"+
		"\2\u0424\u0423\3\2\2\2\u0424\u0425\3\2\2\2\u0425\u0426\3\2\2\2\u0426\u0428"+
		"\7\f\2\2\u0427\u0429\5\u011e\u0090\2\u0428\u0427\3\2\2\2\u0428\u0429\3"+
		"\2\2\2\u0429\u042a\3\2\2\2\u042a\u042b\7\t\2\2\u042b\u042c\5|?\2\u042c"+
		"\u0447\3\2\2\2\u042d\u042e\7M\2\2\u042e\u042f\7\b\2\2\u042f\u0433\5\u0122"+
		"\u0092\2\u0430\u0434\7X\2\2\u0431\u0432\7\u0082\2\2\u0432\u0434\6S\4\2"+
		"\u0433\u0430\3\2\2\2\u0433\u0431\3\2\2\2\u0434\u0435\3\2\2\2\u0435\u0436"+
		"\5\u011e\u0090\2\u0436\u0437\7\t\2\2\u0437\u0438\5|?\2\u0438\u0447\3\2"+
		"\2\2\u0439\u043a\7M\2\2\u043a\u043b\7\b\2\2\u043b\u043c\5\u00a6T\2\u043c"+
		"\u0440\5\u009cO\2\u043d\u0441\7X\2\2\u043e\u043f\7\u0082\2\2\u043f\u0441"+
		"\6S\5\2\u0440\u043d\3\2\2\2\u0440\u043e\3\2\2\2\u0441\u0442\3\2\2\2\u0442"+
		"\u0443\5\u011e\u0090\2\u0443\u0444\7\t\2\2\u0444\u0445\5|?\2\u0445\u0447"+
		"\3\2\2\2\u0446\u0401\3\2\2\2\u0446\u0409\3\2\2\2\u0446\u040f\3\2\2\2\u0446"+
		"\u041e\3\2\2\2\u0446\u042d\3\2\2\2\u0446\u0439\3\2\2\2\u0447\u00a5\3\2"+
		"\2\2\u0448\u0449\t\5\2\2\u0449\u00a7\3\2\2\2\u044a\u044d\7L\2\2\u044b"+
		"\u044c\6U\6\2\u044c\u044e\7\u0082\2\2\u044d\u044b\3\2\2\2\u044d\u044e"+
		"\3\2\2\2\u044e\u044f\3\2\2\2\u044f\u0450\5\u0144\u00a3\2\u0450\u00a9\3"+
		"\2\2\2\u0451\u0454\7>\2\2\u0452\u0453\6V\7\2\u0453\u0455\7\u0082\2\2\u0454"+
		"\u0452\3\2\2\2\u0454\u0455\3\2\2\2\u0455\u0456\3\2\2\2\u0456\u0457\5\u0144"+
		"\u00a3\2\u0457\u00ab\3\2\2\2\u0458\u045b\7J\2\2\u0459\u045a\6W\b\2\u045a"+
		"\u045c\5\u011e\u0090\2\u045b\u0459\3\2\2\2\u045b\u045c\3\2\2\2\u045c\u045d"+
		"\3\2\2\2\u045d\u045e\5\u0144\u00a3\2\u045e\u00ad\3\2\2\2\u045f\u0462\7"+
		"m\2\2\u0460\u0461\6X\t\2\u0461\u0463\5\u011e\u0090\2\u0462\u0460\3\2\2"+
		"\2\u0462\u0463\3\2\2\2\u0463\u0464\3\2\2\2\u0464\u0465\5\u0144\u00a3\2"+
		"\u0465\u00af\3\2\2\2\u0466\u0467\7S\2\2\u0467\u0468\7\b\2\2\u0468\u0469"+
		"\5\u011e\u0090\2\u0469\u046a\7\t\2\2\u046a\u046b\5|?\2\u046b\u00b1\3\2"+
		"\2\2\u046c\u046d\7N\2\2\u046d\u046e\7\b\2\2\u046e\u046f\5\u011e\u0090"+
		"\2\u046f\u0470\7\t\2\2\u0470\u0471\5\u00b4[\2\u0471\u00b3\3\2\2\2\u0472"+
		"\u0474\7\n\2\2\u0473\u0475\5\u00b6\\\2\u0474\u0473\3\2\2\2\u0474\u0475"+
		"\3\2\2\2\u0475\u047a\3\2\2\2\u0476\u0478\5\u00ba^\2\u0477\u0479\5\u00b6"+
		"\\\2\u0478\u0477\3\2\2\2\u0478\u0479\3\2\2\2\u0479\u047b\3\2\2\2\u047a"+
		"\u0476\3\2\2\2\u047a\u047b\3\2\2\2\u047b\u047c\3\2\2\2\u047c\u047d\7\13"+
		"\2\2\u047d\u00b5\3\2\2\2\u047e\u0480\5\u00b8]\2\u047f\u047e\3\2\2\2\u0480"+
		"\u0481\3\2\2\2\u0481\u047f\3\2\2\2\u0481\u0482\3\2\2\2\u0482\u00b7\3\2"+
		"\2\2\u0483\u0484\7D\2\2\u0484\u0485\5\u011e\u0090\2\u0485\u0487\7\20\2"+
		"\2\u0486\u0488\5\u0084C\2\u0487\u0486\3\2\2\2\u0487\u0488\3\2\2\2\u0488"+
		"\u00b9\3\2\2\2\u0489\u048a\7T\2\2\u048a\u048c\7\20\2\2\u048b\u048d\5\u0084"+
		"C\2\u048c\u048b\3\2\2\2\u048c\u048d\3\2\2\2\u048d\u00bb\3\2\2\2\u048e"+
		"\u048f\7\u0082\2\2\u048f\u0490\7\20\2\2\u0490\u0491\5|?\2\u0491\u00bd"+
		"\3\2\2\2\u0492\u0493\7V\2\2\u0493\u0494\6`\n\2\u0494\u0495\5\u011e\u0090"+
		"\2\u0495\u0496\5\u0144\u00a3\2\u0496\u00bf\3\2\2\2\u0497\u0498\7Y\2\2"+
		"\u0498\u049e\5\u0082B\2\u0499\u049b\5\u00c2b\2\u049a\u049c\5\u00c4c\2"+
		"\u049b\u049a\3\2\2\2\u049b\u049c\3\2\2\2\u049c\u049f\3\2\2\2\u049d\u049f"+
		"\5\u00c4c\2\u049e\u0499\3\2\2\2\u049e\u049d\3\2\2\2\u049f\u00c1\3\2\2"+
		"\2\u04a0\u04a1\7H\2\2\u04a1\u04a2\7\b\2\2\u04a2\u04a3\7\u0082\2\2\u04a3"+
		"\u04a4\7\t\2\2\u04a4\u04a5\5\u0082B\2\u04a5\u00c3\3\2\2\2\u04a6\u04a7"+
		"\7I\2\2\u04a7\u04a8\5\u0082B\2\u04a8\u00c5\3\2\2\2\u04a9\u04aa\7P\2\2"+
		"\u04aa\u04ab\5\u0144\u00a3\2\u04ab\u00c7\3\2\2\2\u04ac\u04ae\7Q\2\2\u04ad"+
		"\u04af\7\31\2\2\u04ae\u04ad\3\2\2\2\u04ae\u04af\3\2\2\2\u04af\u04b0\3"+
		"\2\2\2\u04b0\u04b1\5\u0136\u009c\2\u04b1\u04b3\5\u00ecw\2\u04b2\u04b4"+
		"\5\u0082B\2\u04b3\u04b2\3\2\2\2\u04b3\u04b4\3\2\2\2\u04b4\u04b6\3\2\2"+
		"\2\u04b5\u04b7\7\f\2\2\u04b6\u04b5\3\2\2\2\u04b6\u04b7\3\2\2\2\u04b7\u00c9"+
		"\3\2\2\2\u04b8\u04ba\7{\2\2\u04b9\u04b8\3\2\2\2\u04b9\u04ba\3\2\2\2\u04ba"+
		"\u04bb\3\2\2\2\u04bb\u04bc\7^\2\2\u04bc\u04be\5\u0134\u009b\2\u04bd\u04bf"+
		"\5\6\4\2\u04be\u04bd\3\2\2\2\u04be\u04bf\3\2\2\2\u04bf\u04c0\3\2\2\2\u04c0"+
		"\u04c1\5\u00ccg\2\u04c1\u04c3\5\u00ceh\2\u04c2\u04c4\7\f\2\2\u04c3\u04c2"+
		"\3\2\2\2\u04c3\u04c4\3\2\2\2\u04c4\u00cb\3\2\2\2\u04c5\u04c7\5\u00d2j"+
		"\2\u04c6\u04c5\3\2\2\2\u04c6\u04c7\3\2\2\2\u04c7\u04c9\3\2\2\2\u04c8\u04ca"+
		"\5\u00d4k\2\u04c9\u04c8\3\2\2\2\u04c9\u04ca\3\2\2\2\u04ca\u00cd\3\2\2"+
		"\2\u04cb\u04cd\7\n\2\2\u04cc\u04ce\5\u00d0i\2\u04cd\u04cc\3\2\2\2\u04cd"+
		"\u04ce\3\2\2\2\u04ce\u04d0\3\2\2\2\u04cf\u04d1\7\f\2\2\u04d0\u04cf\3\2"+
		"\2\2\u04d0\u04d1\3\2\2\2\u04d1\u04d2\3\2\2\2\u04d2\u04d3\7\13\2\2\u04d3"+
		"\u00cf\3\2\2\2\u04d4\u04dc\5\u00d6l\2\u04d5\u04d8\bi\1\2\u04d6\u04d8\7"+
		"\f\2\2\u04d7\u04d5\3\2\2\2\u04d7\u04d6\3\2\2\2\u04d8\u04d9\3\2\2\2\u04d9"+
		"\u04db\5\u00d6l\2\u04da\u04d7\3\2\2\2\u04db\u04de\3\2\2\2\u04dc\u04da"+
		"\3\2\2\2\u04dc\u04dd\3\2\2\2\u04dd\u00d1\3\2\2\2\u04de\u04dc\3\2\2\2\u04df"+
		"\u04e0\7`\2\2\u04e0\u04e1\5.\30\2\u04e1\u00d3\3\2\2\2\u04e2\u04e3\7e\2"+
		"\2\u04e3\u04e4\5`\61\2\u04e4\u00d5\3\2\2\2\u04e5\u04ec\5\u00d8m\2\u04e6"+
		"\u04e8\5r:\2\u04e7\u04e6\3\2\2\2\u04e7\u04e8\3\2\2\2\u04e8\u04e9\3\2\2"+
		"\2\u04e9\u04ec\5\u00dan\2\u04ea\u04ec\5T+\2\u04eb\u04e5\3\2\2\2\u04eb"+
		"\u04e7\3\2\2\2\u04eb\u04ea\3\2\2\2\u04ec\u00d7\3\2\2\2\u04ed\u04ef\5\u00fc"+
		"\177\2\u04ee\u04ed\3\2\2\2\u04ee\u04ef\3\2\2\2\u04ef\u04f0\3\2\2\2\u04f0"+
		"\u04f1\7v\2\2\u04f1\u04f3\5\u00eex\2\u04f2\u04f4\5\u0082B\2\u04f3\u04f2"+
		"\3\2\2\2\u04f3\u04f4\3\2\2\2\u04f4\u00d9\3\2\2\2\u04f5\u050c\5\u00dco"+
		"\2\u04f6\u0509\5\u00dep\2\u04f7\u04f9\5\u0116\u008c\2\u04f8\u04fa\7\17"+
		"\2\2\u04f9\u04f8\3\2\2\2\u04f9\u04fa\3\2\2\2\u04fa\u0505\3\2\2\2\u04fb"+
		"\u04fd\5\20\t\2\u04fc\u04fb\3\2\2\2\u04fc\u04fd\3\2\2\2\u04fd\u04ff\3"+
		"\2\2\2\u04fe\u0500\5\2\2\2\u04ff\u04fe\3\2\2\2\u04ff\u0500\3\2\2\2\u0500"+
		"\u0506\3\2\2\2\u0501\u0503\5\u00ecw\2\u0502\u0504\5\u0082B\2\u0503\u0502"+
		"\3\2\2\2\u0503\u0504\3\2\2\2\u0504\u0506\3\2\2\2\u0505\u04fc\3\2\2\2\u0505"+
		"\u0501\3\2\2\2\u0506\u050a\3\2\2\2\u0507\u050a\5\u0112\u008a\2\u0508\u050a"+
		"\5\u0114\u008b\2\u0509\u04f7\3\2\2\2\u0509\u0507\3\2\2\2\u0509\u0508\3"+
		"\2\2\2\u050a\u050c\3\2\2\2\u050b\u04f5\3\2\2\2\u050b\u04f6\3\2\2\2\u050c"+
		"\u00db\3\2\2\2\u050d\u0511\7{\2\2\u050e\u050f\7\u0082\2\2\u050f\u0512"+
		"\5\u00ecw\2\u0510\u0512\5\u0096L\2\u0511\u050e\3\2\2\2\u0511\u0510\3\2"+
		"\2\2\u0512\u0513\3\2\2\2\u0513\u0514\5\u0144\u00a3\2\u0514\u00dd\3\2\2"+
		"\2\u0515\u0517\7]\2\2\u0516\u0515\3\2\2\2\u0516\u0517\3\2\2\2\u0517\u0519"+
		"\3\2\2\2\u0518\u051a\5\u00fc\177\2\u0519\u0518\3\2\2\2\u0519\u051a\3\2"+
		"\2\2\u051a\u051c\3\2\2\2\u051b\u051d\7l\2\2\u051c\u051b\3\2\2\2\u051c"+
		"\u051d\3\2\2\2\u051d\u051f\3\2\2\2\u051e\u0520\7\\\2\2\u051f\u051e\3\2"+
		"\2\2\u051f\u0520\3\2\2\2\u0520\u00df\3\2\2\2\u0521\u0523\7\31\2\2\u0522"+
		"\u0521\3\2\2\2\u0522\u0523\3\2\2\2\u0523\u0524\3\2\2\2\u0524\u0525\7\u0082"+
		"\2\2\u0525\u0526\5\u00eex\2\u0526\u0527\5\u0082B\2\u0527\u00e1\3\2\2\2"+
		"\u0528\u0529\7Q\2\2\u0529\u052b\7\31\2\2\u052a\u052c\7\u0082\2\2\u052b"+
		"\u052a\3\2\2\2\u052b\u052c\3\2\2\2\u052c\u052d\3\2\2\2\u052d\u052e\5\u00ee"+
		"x\2\u052e\u052f\5\u0082B\2\u052f\u00e3\3\2\2\2\u0530\u0531\7\n\2\2\u0531"+
		"\u0536\5\u00e6t\2\u0532\u0533\7\r\2\2\u0533\u0535\5\u00e6t\2\u0534\u0532"+
		"\3\2\2\2\u0535\u0538\3\2\2\2\u0536\u0534\3\2\2\2\u0536\u0537\3\2\2\2\u0537"+
		"\u053a\3\2\2\2\u0538\u0536\3\2\2\2\u0539\u053b\7\r\2\2\u053a\u0539\3\2"+
		"\2\2\u053a\u053b\3\2\2\2\u053b\u053c\3\2\2\2\u053c\u053d\7\13\2\2\u053d"+
		"\u00e5\3\2\2\2\u053e\u053f\7\31\2\2\u053f\u0540\5\u00eav\2\u0540\u00e7"+
		"\3\2\2\2\u0541\u0542\7\n\2\2\u0542\u0547\5\u00eav\2\u0543\u0544\7\r\2"+
		"\2\u0544\u0546\5\u00eav\2\u0545\u0543\3\2\2\2\u0546\u0549\3\2\2\2\u0547"+
		"\u0545\3\2\2\2\u0547\u0548\3\2\2\2\u0548\u054b\3\2\2\2\u0549\u0547\3\2"+
		"\2\2\u054a\u054c\7\r\2\2\u054b\u054a\3\2\2\2\u054b\u054c\3\2\2\2\u054c"+
		"\u054d\3\2\2\2\u054d\u054e\7\13\2\2\u054e\u00e9\3\2\2\2\u054f\u0550\7"+
		"\6\2\2\u0550\u0551\5\u0122\u0092\2\u0551\u0552\7\7\2\2\u0552\u0553\5\u00ee"+
		"x\2\u0553\u0554\5\u0082B\2\u0554\u00eb\3\2\2\2\u0555\u0557\5\6\4\2\u0556"+
		"\u0555\3\2\2\2\u0556\u0557\3\2\2\2\u0557\u0558\3\2\2\2\u0558\u055e\5\u00ee"+
		"x\2\u0559\u055c\7\20\2\2\u055a\u055d\5H%\2\u055b\u055d\5\22\n\2\u055c"+
		"\u055a\3\2\2\2\u055c\u055b\3\2\2\2\u055d\u055f\3\2\2\2\u055e\u0559\3\2"+
		"\2\2\u055e\u055f\3\2\2\2\u055f\u00ed\3\2\2\2\u0560\u0562\7\b\2\2\u0561"+
		"\u0563\5\u00f0y\2\u0562\u0561\3\2\2\2\u0562\u0563\3\2\2\2\u0563\u0564"+
		"\3\2\2\2\u0564\u0565\7\t\2\2\u0565\u00ef\3\2\2\2\u0566\u0568\5\u00f2z"+
		"\2\u0567\u0569\7\r\2\2\u0568\u0567\3\2\2\2\u0568\u0569\3\2\2\2\u0569\u00f1"+
		"\3\2\2\2\u056a\u0578\5\u00f4{\2\u056b\u0570\5\u00f6|\2\u056c\u056d\7\r"+
		"\2\2\u056d\u056f\5\u00f6|\2\u056e\u056c\3\2\2\2\u056f\u0572\3\2\2\2\u0570"+
		"\u056e\3\2\2\2\u0570\u0571\3\2\2\2\u0571\u0575\3\2\2\2\u0572\u0570\3\2"+
		"\2\2\u0573\u0574\7\r\2\2\u0574\u0576\5\u00f4{\2\u0575\u0573\3\2\2\2\u0575"+
		"\u0576\3\2\2\2\u0576\u0578\3\2\2\2\u0577\u056a\3\2\2\2\u0577\u056b\3\2"+
		"\2\2\u0578\u00f3\3\2\2\2\u0579\u057a\7\21\2\2\u057a\u057c\5\u00fe\u0080"+
		"\2\u057b\u057d\5\20\t\2\u057c\u057b\3\2\2\2\u057c\u057d\3\2\2\2\u057d"+
		"\u00f5\3\2\2\2\u057e\u0581\5\u00f8}\2\u057f\u0581\5\u00fa~\2\u0580\u057e"+
		"\3\2\2\2\u0580\u057f\3\2\2\2\u0581\u00f7\3\2\2\2\u0582\u0584\5r:\2\u0583"+
		"\u0582\3\2\2\2\u0583\u0584\3\2\2\2\u0584\u0586\3\2\2\2\u0585\u0587\5\u00fc"+
		"\177\2\u0586\u0585\3\2\2\2\u0586\u0587\3\2\2\2\u0587\u0588\3\2\2\2\u0588"+
		"\u058a\5\u00fe\u0080\2\u0589\u058b\5\20\t\2\u058a\u0589\3\2\2\2\u058a"+
		"\u058b\3\2\2\2\u058b\u00f9\3\2\2\2\u058c\u058e\5r:\2\u058d\u058c\3\2\2"+
		"\2\u058d\u058e\3\2\2\2\u058e\u0590\3\2\2\2\u058f\u0591\5\u00fc\177\2\u0590"+
		"\u058f\3\2\2\2\u0590\u0591\3\2\2\2\u0591\u0592\3\2\2\2\u0592\u059b\5\u00fe"+
		"\u0080\2\u0593\u0595\7\17\2\2\u0594\u0596\5\20\t\2\u0595\u0594\3\2\2\2"+
		"\u0595\u0596\3\2\2\2\u0596\u059c\3\2\2\2\u0597\u0599\5\20\t\2\u0598\u0597"+
		"\3\2\2\2\u0598\u0599\3\2\2\2\u0599\u059a\3\2\2\2\u059a\u059c\5\2\2\2\u059b"+
		"\u0593\3\2\2\2\u059b\u0598\3\2\2\2\u059c\u00fb\3\2\2\2\u059d\u059e\t\6"+
		"\2\2\u059e\u00fd\3\2\2\2\u059f\u05a2\5\u0136\u009c\2\u05a0\u05a2\5\4\3"+
		"\2\u05a1\u059f\3\2\2\2\u05a1\u05a0\3\2\2\2\u05a2\u00ff\3\2\2\2\u05a3\u05a5"+
		"\7\6\2\2\u05a4\u05a6\5\u0102\u0082\2\u05a5\u05a4\3\2\2\2\u05a5\u05a6\3"+
		"\2\2\2\u05a6\u05a7\3\2\2\2\u05a7\u05a8\7\7\2\2\u05a8\u0101\3\2\2\2\u05a9"+
		"\u05b2\5\u0104\u0083\2\u05aa\u05ac\7\r\2\2\u05ab\u05aa\3\2\2\2\u05ac\u05ad"+
		"\3\2\2\2\u05ad\u05ab\3\2\2\2\u05ad\u05ae\3\2\2\2\u05ae\u05af\3\2\2\2\u05af"+
		"\u05b1\5\u0104\u0083\2\u05b0\u05ab\3\2\2\2\u05b1\u05b4\3\2\2\2\u05b2\u05b0"+
		"\3\2\2\2\u05b2\u05b3\3\2\2\2\u05b3\u05b6\3\2\2\2\u05b4\u05b2\3\2\2\2\u05b5"+
		"\u05b7\7\r\2\2\u05b6\u05b5\3\2\2\2\u05b6\u05b7\3\2\2\2\u05b7\u0103\3\2"+
		"\2\2\u05b8\u05ba\7\21\2\2\u05b9\u05b8\3\2\2\2\u05b9\u05ba\3\2\2\2\u05ba"+
		"\u05bb\3\2\2\2\u05bb\u05bc\5\u0106\u0084\2\u05bc\u0105\3\2\2\2\u05bd\u05c0"+
		"\5\4\3\2\u05be\u05c0\7\u0082\2\2\u05bf\u05bd\3\2\2\2\u05bf\u05be\3\2\2"+
		"\2\u05c0\u0107\3\2\2\2\u05c1\u05c3\5\u010a\u0086\2\u05c2\u05c4\t\7\2\2"+
		"\u05c3\u05c2\3\2\2\2\u05c3\u05c4\3\2\2\2\u05c4\u0109\3\2\2\2\u05c5\u05ce"+
		"\5\u010c\u0087\2\u05c6\u05ca\b\u0086\1\2\u05c7\u05ca\7\f\2\2\u05c8\u05ca"+
		"\7\r\2\2\u05c9\u05c6\3\2\2\2\u05c9\u05c7\3\2\2\2\u05c9\u05c8\3\2\2\2\u05ca"+
		"\u05cb\3\2\2\2\u05cb\u05cd\5\u010c\u0087\2\u05cc\u05c9\3\2\2\2\u05cd\u05d0"+
		"\3\2\2\2\u05ce\u05cc\3\2\2\2\u05ce\u05cf\3\2\2\2\u05cf\u010b\3\2\2\2\u05d0"+
		"\u05ce\3\2\2\2\u05d1\u05db\5P)\2\u05d2\u05db\5\u00ecw\2\u05d3\u05db\5"+
		"R*\2\u05d4\u05db\5T+\2\u05d5\u05d8\5X-\2\u05d6\u05d7\7\65\2\2\u05d7\u05d9"+
		"\5\22\n\2\u05d8\u05d6\3\2\2\2\u05d8\u05d9\3\2\2\2\u05d9\u05db\3\2\2\2"+
		"\u05da\u05d1\3\2\2\2\u05da\u05d2\3\2\2\2\u05da\u05d3\3\2\2\2\u05da\u05d4"+
		"\3\2\2\2\u05da\u05d5\3\2\2\2\u05db\u010d\3\2\2\2\u05dc\u05e8\7\n\2\2\u05dd"+
		"\u05e2\5\u0110\u0089\2\u05de\u05df\7\r\2\2\u05df\u05e1\5\u0110\u0089\2"+
		"\u05e0\u05de\3\2\2\2\u05e1\u05e4\3\2\2\2\u05e2\u05e0\3\2\2\2\u05e2\u05e3"+
		"\3\2\2\2\u05e3\u05e6\3\2\2\2\u05e4\u05e2\3\2\2\2\u05e5\u05e7\7\r\2\2\u05e6"+
		"\u05e5\3\2\2\2\u05e6\u05e7\3\2\2\2\u05e7\u05e9\3\2\2\2\u05e8\u05dd\3\2"+
		"\2\2\u05e8\u05e9\3\2\2\2\u05e9\u05ea\3\2\2\2\u05ea\u05eb\7\13\2\2\u05eb"+
		"\u010f\3\2\2\2\u05ec\u05f0\5\u0116\u008c\2\u05ed\u05ee\7\20\2\2\u05ee"+
		"\u05f1\5\u0134\u009b\2\u05ef\u05f1\5\4\3\2\u05f0\u05ed\3\2\2\2\u05f0\u05ef"+
		"\3\2\2\2\u05f0\u05f1\3\2\2\2\u05f1\u05f4\3\2\2\2\u05f2\u05f3\7\16\2\2"+
		"\u05f3\u05f5\5\u0122\u0092\2\u05f4\u05f2\3\2\2\2\u05f4\u05f5\3\2\2\2\u05f5"+
		"\u05fb\3\2\2\2\u05f6\u05fb\5\u0112\u008a\2\u05f7\u05fb\5\u0114\u008b\2"+
		"\u05f8\u05fb\5\u00e0q\2\u05f9\u05fb\5\u00f4{\2\u05fa\u05ec\3\2\2\2\u05fa"+
		"\u05f6\3\2\2\2\u05fa\u05f7\3\2\2\2\u05fa\u05f8\3\2\2\2\u05fa\u05f9\3\2"+
		"\2\2\u05fb\u0111\3\2\2\2\u05fc\u05fd\5\u0140\u00a1\2\u05fd\u05fe\7\b\2"+
		"\2\u05fe\u0600\7\t\2\2\u05ff\u0601\5\20\t\2\u0600\u05ff\3\2\2\2\u0600"+
		"\u0601\3\2\2\2\u0601\u0603\3\2\2\2\u0602\u0604\5\u0082B\2\u0603\u0602"+
		"\3\2\2\2\u0603\u0604\3\2\2\2\u0604\u0113\3\2\2\2\u0605\u0606\5\u0142\u00a2"+
		"\2\u0606\u0609\7\b\2\2\u0607\u060a\7\u0082\2\2\u0608\u060a\5\4\3\2\u0609"+
		"\u0607\3\2\2\2\u0609\u0608\3\2\2\2\u060a\u060c\3\2\2\2\u060b\u060d\5\20"+
		"\t\2\u060c\u060b\3\2\2\2\u060c\u060d\3\2\2\2\u060d\u060e\3\2\2\2\u060e"+
		"\u0610\7\t\2\2\u060f\u0611\5\u0082B\2\u0610\u060f\3\2\2\2\u0610\u0611"+
		"\3\2\2\2\u0611\u0115\3\2\2\2\u0612\u061f\5\u0136\u009c\2\u0613\u061f\7"+
		"\u0083\2\2\u0614\u061f\5\u0132\u009a\2\u0615\u061b\7\6\2\2\u0616\u0617"+
		"\5\u0136\u009c\2\u0617\u0618\7\22\2\2\u0618\u0619\5\u0136\u009c\2\u0619"+
		"\u061c\3\2\2\2\u061a\u061c\7\u0083\2\2\u061b\u0616\3\2\2\2\u061b\u061a"+
		"\3\2\2\2\u061c\u061d\3\2\2\2\u061d\u061f\7\7\2\2\u061e\u0612\3\2\2\2\u061e"+
		"\u0613\3\2\2\2\u061e\u0614\3\2\2\2\u061e\u0615\3\2\2\2\u061f\u0117\3\2"+
		"\2\2\u0620\u0625\7\b\2\2\u0621\u0623\5\u011a\u008e\2\u0622\u0624\7\r\2"+
		"\2\u0623\u0622\3\2\2\2\u0623\u0624\3\2\2\2\u0624\u0626\3\2\2\2\u0625\u0621"+
		"\3\2\2\2\u0625\u0626\3\2\2\2\u0626\u0627\3\2\2\2\u0627\u0628\7\t\2\2\u0628"+
		"\u0119\3\2\2\2\u0629\u062e\5\u011c\u008f\2\u062a\u062b\7\r\2\2\u062b\u062d"+
		"\5\u011c\u008f\2\u062c\u062a\3\2\2\2\u062d\u0630\3\2\2\2\u062e\u062c\3"+
		"\2\2\2\u062e\u062f\3\2\2\2\u062f\u011b\3\2\2\2\u0630\u062e\3\2\2\2\u0631"+
		"\u0633\7\21\2\2\u0632\u0631\3\2\2\2\u0632\u0633\3\2\2\2\u0633\u0636\3"+
		"\2\2\2\u0634\u0637\5\u0122\u0092\2\u0635\u0637\7\u0082\2\2\u0636\u0634"+
		"\3\2\2\2\u0636\u0635\3\2\2\2\u0637\u011d\3\2\2\2\u0638\u063d\5\u0122\u0092"+
		"\2\u0639\u063a\7\r\2\2\u063a\u063c\5\u0122\u0092\2\u063b\u0639\3\2\2\2"+
		"\u063c\u063f\3\2\2\2\u063d\u063b\3\2\2\2\u063d\u063e\3\2\2\2\u063e\u011f"+
		"\3\2\2\2\u063f\u063d\3\2\2\2\u0640\u0642\7Q\2\2\u0641\u0643\7\31\2\2\u0642"+
		"\u0641\3\2\2\2\u0642\u0643\3\2\2\2\u0643\u0645\3\2\2\2\u0644\u0646\7\u0082"+
		"\2\2\u0645\u0644\3\2\2\2\u0645\u0646\3\2\2\2\u0646\u0647\3\2\2\2\u0647"+
		"\u0649\5\u00eex\2\u0648\u064a\5\20\t\2\u0649\u0648\3\2\2\2\u0649\u064a"+
		"\3\2\2\2\u064a\u064b\3\2\2\2\u064b\u064c\5\u0082B\2\u064c\u0121\3\2\2"+
		"\2\u064d\u064e\b\u0092\1\2\u064e\u0681\5\u0120\u0091\2\u064f\u0681\5\u0124"+
		"\u0093\2\u0650\u0652\7^\2\2\u0651\u0653\7\u0082\2\2\u0652\u0651\3\2\2"+
		"\2\u0652\u0653\3\2\2\2\u0653\u0654\3\2\2\2\u0654\u0681\5\u00ceh\2\u0655"+
		"\u0656\7F\2\2\u0656\u0658\5\u0122\u0092\2\u0657\u0659\58\35\2\u0658\u0657"+
		"\3\2\2\2\u0658\u0659\3\2\2\2\u0659\u065b\3\2\2\2\u065a\u065c\5\u0118\u008d"+
		"\2\u065b\u065a\3\2\2\2\u065b\u065c\3\2\2\2\u065c\u0681\3\2\2\2\u065d\u065e"+
		"\7W\2\2\u065e\u0681\5\u0122\u0092$\u065f\u0660\7K\2\2\u0660\u0681\5\u0122"+
		"\u0092#\u0661\u0662\7A\2\2\u0662\u0681\5\u0122\u0092\"\u0663\u0664\7\23"+
		"\2\2\u0664\u0681\5\u0122\u0092!\u0665\u0666\7\24\2\2\u0666\u0681\5\u0122"+
		"\u0092 \u0667\u0668\7\25\2\2\u0668\u0681\5\u0122\u0092\37\u0669\u066a"+
		"\7\26\2\2\u066a\u0681\5\u0122\u0092\36\u066b\u066c\7\27\2\2\u066c\u0681"+
		"\5\u0122\u0092\35\u066d\u066e\7\30\2\2\u066e\u0681\5\u0122\u0092\34\u066f"+
		"\u0681\5\u00e8u\2\u0670\u0681\5\u00e4s\2\u0671\u0681\5\u00aeX\2\u0672"+
		"\u0681\7R\2\2\u0673\u0681\5\u0136\u009c\2\u0674\u0681\7a\2\2\u0675\u0681"+
		"\5\u012c\u0097\2\u0676\u0681\5\u0100\u0081\2\u0677\u0681\5\u010e\u0088"+
		"\2\u0678\u0679\7\b\2\2\u0679\u067a\5\u011e\u0090\2\u067a\u067b\7\t\2\2"+
		"\u067b\u0681\3\2\2\2\u067c\u067e\58\35\2\u067d\u067f\5\u011e\u0090\2\u067e"+
		"\u067d\3\2\2\2\u067e\u067f\3\2\2\2\u067f\u0681\3\2\2\2\u0680\u064d\3\2"+
		"\2\2\u0680\u064f\3\2\2\2\u0680\u0650\3\2\2\2\u0680\u0655\3\2\2\2\u0680"+
		"\u065d\3\2\2\2\u0680\u065f\3\2\2\2\u0680\u0661\3\2\2\2\u0680\u0663\3\2"+
		"\2\2\u0680\u0665\3\2\2\2\u0680\u0667\3\2\2\2\u0680\u0669\3\2\2\2\u0680"+
		"\u066b\3\2\2\2\u0680\u066d\3\2\2\2\u0680\u066f\3\2\2\2\u0680\u0670\3\2"+
		"\2\2\u0680\u0671\3\2\2\2\u0680\u0672\3\2\2\2\u0680\u0673\3\2\2\2\u0680"+
		"\u0674\3\2\2\2\u0680\u0675\3\2\2\2\u0680\u0676\3\2\2\2\u0680\u0677\3\2"+
		"\2\2\u0680\u0678\3\2\2\2\u0680\u067c\3\2\2\2\u0681\u06cb\3\2\2\2\u0682"+
		"\u0683\f\33\2\2\u0683\u0684\t\b\2\2\u0684\u06ca\5\u0122\u0092\34\u0685"+
		"\u0686\f\32\2\2\u0686\u0687\t\t\2\2\u0687\u06ca\5\u0122\u0092\33\u0688"+
		"\u068f\f\31\2\2\u0689\u0690\7\34\2\2\u068a\u068b\7\36\2\2\u068b\u0690"+
		"\7\36\2\2\u068c\u068d\7\36\2\2\u068d\u068e\7\36\2\2\u068e\u0690\7\36\2"+
		"\2\u068f\u0689\3\2\2\2\u068f\u068a\3\2\2\2\u068f\u068c\3\2\2\2\u0690\u0691"+
		"\3\2\2\2\u0691\u06ca\5\u0122\u0092\32\u0692\u0693\f\30\2\2\u0693\u0694"+
		"\t\n\2\2\u0694\u06ca\5\u0122\u0092\31\u0695\u0696\f\27\2\2\u0696\u0697"+
		"\7@\2\2\u0697\u06ca\5\u0122\u0092\30\u0698\u0699\f\26\2\2\u0699\u069a"+
		"\7X\2\2\u069a\u06ca\5\u0122\u0092\27\u069b\u069c\f\25\2\2\u069c\u069d"+
		"\t\13\2\2\u069d\u06ca\5\u0122\u0092\26\u069e\u069f\f\24\2\2\u069f\u06a0"+
		"\t\f\2\2\u06a0\u06ca\5\u0122\u0092\25\u06a1\u06a2\f\23\2\2\u06a2\u06a3"+
		"\t\r\2\2\u06a3\u06ca\5\u0122\u0092\24\u06a4\u06a5\f\22\2\2\u06a5\u06a6"+
		"\7\17\2\2\u06a6\u06a7\5\u0122\u0092\2\u06a7\u06a8\7\20\2\2\u06a8\u06a9"+
		"\5\u0122\u0092\23\u06a9\u06ca\3\2\2\2\u06aa\u06ab\f\21\2\2\u06ab\u06ac"+
		"\7\16\2\2\u06ac\u06ca\5\u0122\u0092\22\u06ad\u06ae\f\20\2\2\u06ae\u06af"+
		"\5\u012a\u0096\2\u06af\u06b0\5\u0122\u0092\21\u06b0\u06ca\3\2\2\2\u06b1"+
		"\u06b2\f*\2\2\u06b2\u06b3\7\6\2\2\u06b3\u06b4\5\u011e\u0090\2\u06b4\u06b5"+
		"\7\7\2\2\u06b5\u06ca\3\2\2\2\u06b6\u06b7\f)\2\2\u06b7\u06b8\7\22\2\2\u06b8"+
		"\u06ba\5\u0136\u009c\2\u06b9\u06bb\5\62\32\2\u06ba\u06b9\3\2\2\2\u06ba"+
		"\u06bb\3\2\2\2\u06bb\u06ca\3\2\2\2\u06bc\u06bd\f\'\2\2\u06bd\u06ca\5\u0118"+
		"\u008d\2\u06be\u06bf\f&\2\2\u06bf\u06c0\6\u0092\33\2\u06c0\u06ca\7\23"+
		"\2\2\u06c1\u06c2\f%\2\2\u06c2\u06c3\6\u0092\35\2\u06c3\u06ca\7\24\2\2"+
		"\u06c4\u06c5\f\17\2\2\u06c5\u06ca\5\u012e\u0098\2\u06c6\u06c7\f\3\2\2"+
		"\u06c7\u06c8\7Z\2\2\u06c8\u06ca\5\22\n\2\u06c9\u0682\3\2\2\2\u06c9\u0685"+
		"\3\2\2\2\u06c9\u0688\3\2\2\2\u06c9\u0692\3\2\2\2\u06c9\u0695\3\2\2\2\u06c9"+
		"\u0698\3\2\2\2\u06c9\u069b\3\2\2\2\u06c9\u069e\3\2\2\2\u06c9\u06a1\3\2"+
		"\2\2\u06c9\u06a4\3\2\2\2\u06c9\u06aa\3\2\2\2\u06c9\u06ad\3\2\2\2\u06c9"+
		"\u06b1\3\2\2\2\u06c9\u06b6\3\2\2\2\u06c9\u06bc\3\2\2\2\u06c9\u06be\3\2"+
		"\2\2\u06c9\u06c1\3\2\2\2\u06c9\u06c4\3\2\2\2\u06c9\u06c6\3\2\2\2\u06ca"+
		"\u06cd\3\2\2\2\u06cb\u06c9\3\2\2\2\u06cb\u06cc\3\2\2\2\u06cc\u0123\3\2"+
		"\2\2\u06cd\u06cb\3\2\2\2\u06ce\u06d0\7]\2\2\u06cf\u06ce\3\2\2\2\u06cf"+
		"\u06d0\3\2\2\2\u06d0\u06d1\3\2\2\2\u06d1\u06d3\5\u0126\u0094\2\u06d2\u06d4"+
		"\5\20\t\2\u06d3\u06d2\3\2\2\2\u06d3\u06d4\3\2\2\2\u06d4\u06d5\3\2\2\2"+
		"\u06d5\u06d6\7\65\2\2\u06d6\u06d7\5\u0128\u0095\2\u06d7\u0125\3\2\2\2"+
		"\u06d8\u06db\7\u0082\2\2\u06d9\u06db\5\u00eex\2\u06da\u06d8\3\2\2\2\u06da"+
		"\u06d9\3\2\2\2\u06db\u0127\3\2\2\2\u06dc\u06df\5\u0122\u0092\2\u06dd\u06df"+
		"\5\u0082B\2\u06de\u06dc\3\2\2\2\u06de\u06dd\3\2\2\2\u06df\u0129\3\2\2"+
		"\2\u06e0\u06e1\t\16\2\2\u06e1\u012b\3\2\2\2\u06e2\u06e9\7\66\2\2\u06e3"+
		"\u06e9\78\2\2\u06e4\u06e9\7\u0083\2\2\u06e5\u06e9\5\u012e\u0098\2\u06e6"+
		"\u06e9\7\5\2\2\u06e7\u06e9\5\u0132\u009a\2\u06e8\u06e2\3\2\2\2\u06e8\u06e3"+
		"\3\2\2\2\u06e8\u06e4\3\2\2\2\u06e8\u06e5\3\2\2\2\u06e8\u06e6\3\2\2\2\u06e8"+
		"\u06e7\3\2\2\2\u06e9\u012d\3\2\2\2\u06ea\u06ee\7\u0084\2\2\u06eb\u06ed"+
		"\5\u0130\u0099\2\u06ec\u06eb\3\2\2\2\u06ed\u06f0\3\2\2\2\u06ee\u06ec\3"+
		"\2\2\2\u06ee\u06ef\3\2\2\2\u06ef\u06f1\3\2\2\2\u06f0\u06ee\3\2\2\2\u06f1"+
		"\u06f2\7\u0084\2\2\u06f2\u012f\3\2\2\2\u06f3\u06f9\7\u008b\2\2\u06f4\u06f5"+
		"\7\u008a\2\2\u06f5\u06f6\5\u0122\u0092\2\u06f6\u06f7\7\13\2\2\u06f7\u06f9"+
		"\3\2\2\2\u06f8\u06f3\3\2\2\2\u06f8\u06f4\3\2\2\2\u06f9\u0131\3\2\2\2\u06fa"+
		"\u06fc\7\26\2\2\u06fb\u06fa\3\2\2\2\u06fb\u06fc\3\2\2\2\u06fc\u06fd\3"+
		"\2\2\2\u06fd\u0703\79\2\2\u06fe\u0703\7:\2\2\u06ff\u0703\7;\2\2\u0700"+
		"\u0703\7<\2\2\u0701\u0703\7=\2\2\u0702\u06fb\3\2\2\2\u0702\u06fe\3\2\2"+
		"\2\u0702\u06ff\3\2\2\2\u0702\u0700\3\2\2\2\u0702\u0701\3\2\2\2\u0703\u0133"+
		"\3\2\2\2\u0704\u0705\t\17\2\2\u0705\u0135\3\2\2\2\u0706\u0709\5\u0138"+
		"\u009d\2\u0707\u0709\7\u0082\2\2\u0708\u0706\3\2\2\2\u0708\u0707\3\2\2"+
		"\2\u0709\u0137\3\2\2\2\u070a\u070d\5\u013c\u009f\2\u070b\u070d\78\2\2"+
		"\u070c\u070a\3\2\2\2\u070c\u070b\3\2\2\2\u070d\u0139\3\2\2\2\u070e\u0712"+
		"\5\u013e\u00a0\2\u070f\u0712\78\2\2\u0710\u0712\7\u0082\2\2\u0711\u070e"+
		"\3\2\2\2\u0711\u070f\3\2\2\2\u0711\u0710\3\2\2\2\u0712\u013b\3\2\2\2\u0713"+
		"\u0717\5\u013e\u00a0\2\u0714\u0717\7\\\2\2\u0715\u0717\7A\2\2\u0716\u0713"+
		"\3\2\2\2\u0716\u0714\3\2\2\2\u0716\u0715\3\2\2\2\u0717\u013d\3\2\2\2\u0718"+
		"\u0719\t\20\2\2\u0719\u013f\3\2\2\2\u071a\u071b\7t\2\2\u071b\u071c\5\u0116"+
		"\u008c\2\u071c\u0141\3\2\2\2\u071d\u071e\7u\2\2\u071e\u071f\5\u0116\u008c"+
		"\2\u071f\u0143\3\2\2\2\u0720\u0725\7\f\2\2\u0721\u0725\7\2\2\3\u0722\u0725"+
		"\6\u00a3 \2\u0723\u0725\6\u00a3!\2\u0724\u0720\3\2\2\2\u0724\u0721\3\2"+
		"\2\2\u0724\u0722\3\2\2\2\u0724\u0723\3\2\2\2\u0725\u0145\3\2\2\2\u00f6"+
		"\u014b\u014f\u0158\u015d\u0161\u0175\u0178\u017f\u0183\u018a\u018e\u019b"+
		"\u01a7\u01b1\u01b3\u01b6\u01c3\u01c8\u01cb\u01ce\u01d6\u01da\u01de\u01e7"+
		"\u01f0\u01f4\u01f8\u01fb\u01fe\u0202\u0206\u020b\u0210\u0214\u021b\u0221"+
		"\u022a\u022e\u0234\u023a\u0249\u024e\u0254\u0258\u025a\u025d\u0261\u0266"+
		"\u0269\u026f\u0273\u027f\u0286\u028a\u028d\u0291\u0295\u0299\u029c\u029f"+
		"\u02a4\u02aa\u02af\u02ba\u02be\u02c5\u02ca\u02cf\u02d2\u02d6\u02da\u02e4"+
		"\u02e8\u02ee\u02f2\u02f6\u02fd\u0303\u030e\u031a\u031f\u0327\u032e\u0335"+
		"\u034c\u034f\u035b\u035f\u0366\u036c\u0371\u0378\u0380\u0388\u038c\u0393"+
		"\u039c\u03b1\u03b5\u03ba\u03bf\u03c3\u03c7\u03ca\u03cd\u03d2\u03d5\u03d9"+
		"\u03dc\u03e3\u03e8\u03ec\u03ef\u03f6\u03ff\u0412\u0416\u041a\u0424\u0428"+
		"\u0433\u0440\u0446\u044d\u0454\u045b\u0462\u0474\u0478\u047a\u0481\u0487"+
		"\u048c\u049b\u049e\u04ae\u04b3\u04b6\u04b9\u04be\u04c3\u04c6\u04c9\u04cd"+
		"\u04d0\u04d7\u04dc\u04e7\u04eb\u04ee\u04f3\u04f9\u04fc\u04ff\u0503\u0505"+
		"\u0509\u050b\u0511\u0516\u0519\u051c\u051f\u0522\u052b\u0536\u053a\u0547"+
		"\u054b\u0556\u055c\u055e\u0562\u0568\u0570\u0575\u0577\u057c\u0580\u0583"+
		"\u0586\u058a\u058d\u0590\u0595\u0598\u059b\u05a1\u05a5\u05ad\u05b2\u05b6"+
		"\u05b9\u05bf\u05c3\u05c9\u05ce\u05d8\u05da\u05e2\u05e6\u05e8\u05f0\u05f4"+
		"\u05fa\u0600\u0603\u0609\u060c\u0610\u061b\u061e\u0623\u0625\u062e\u0632"+
		"\u0636\u063d\u0642\u0645\u0649\u0652\u0658\u065b\u067e\u0680\u068f\u06ba"+
		"\u06c9\u06cb\u06cf\u06d3\u06da\u06de\u06e8\u06ee\u06f8\u06fb\u0702\u0708"+
		"\u070c\u0711\u0716\u0724";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}