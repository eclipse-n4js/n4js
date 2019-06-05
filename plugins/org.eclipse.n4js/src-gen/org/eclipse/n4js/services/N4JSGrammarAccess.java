/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.n4js.common.unicode.services.UnicodeGrammarAccess;
import org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess;
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
import org.eclipse.xtext.service.AbstractElementFinder.AbstractEnumRuleElementFinder;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class N4JSGrammarAccess extends AbstractGrammarElementFinder {
	
	public class ScriptElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Script");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cScriptAction_0 = (Action)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cAnnotationsAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final RuleCall cAnnotationsScriptAnnotationParserRuleCall_1_0_0 = (RuleCall)cAnnotationsAssignment_1_0.eContents().get(0);
		private final Assignment cScriptElementsAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final RuleCall cScriptElementsScriptElementParserRuleCall_1_1_0 = (RuleCall)cScriptElementsAssignment_1_1.eContents().get(0);
		
		//// ****************************************************************************************************
		//// [ECM11] A.5 Functions and Programs (p. 224)
		//// [ECM15]
		//// [ECMWiki] http://wiki.ecmascript.org/doku.php?id=harmony:modules
		//// ****************************************************************************************************
		//Script:
		//	{Script} (annotations+=ScriptAnnotation
		//	| scriptElements+=ScriptElement)*;
		@Override public ParserRule getRule() { return rule; }
		
		//{Script} (annotations+=ScriptAnnotation | scriptElements+=ScriptElement)*
		public Group getGroup() { return cGroup; }
		
		//{Script}
		public Action getScriptAction_0() { return cScriptAction_0; }
		
		//(annotations+=ScriptAnnotation | scriptElements+=ScriptElement)*
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//annotations+=ScriptAnnotation
		public Assignment getAnnotationsAssignment_1_0() { return cAnnotationsAssignment_1_0; }
		
		//ScriptAnnotation
		public RuleCall getAnnotationsScriptAnnotationParserRuleCall_1_0_0() { return cAnnotationsScriptAnnotationParserRuleCall_1_0_0; }
		
		//scriptElements+=ScriptElement
		public Assignment getScriptElementsAssignment_1_1() { return cScriptElementsAssignment_1_1; }
		
		//ScriptElement
		public RuleCall getScriptElementsScriptElementParserRuleCall_1_1_0() { return cScriptElementsScriptElementParserRuleCall_1_1_0; }
	}
	public class ScriptElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ScriptElement");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cAnnotatedScriptElementParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cN4ClassDeclarationParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cN4InterfaceDeclarationParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cN4EnumDeclarationParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cImportDeclarationParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cExportDeclarationParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cRootStatementParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		
		///*
		// * The top level elements in a script are type declarations, exports, imports or statements
		// */ ScriptElement:
		//	AnnotatedScriptElement
		//	| N4ClassDeclaration<Yield=false> | N4InterfaceDeclaration<Yield=false> | N4EnumDeclaration<Yield=false> | =>
		//	ImportDeclaration // syntactic predicate required due to RootStatement > ExpressionStatement > ... > ImportCallExpression
		//	| ExportDeclaration
		//	| RootStatement<Yield=false>;
		@Override public ParserRule getRule() { return rule; }
		
		//AnnotatedScriptElement | N4ClassDeclaration<Yield=false> | N4InterfaceDeclaration<Yield=false> |
		//N4EnumDeclaration<Yield=false> | => ImportDeclaration // syntactic predicate required due to RootStatement > ExpressionStatement > ... > ImportCallExpression
		//| ExportDeclaration | RootStatement<Yield=false>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//AnnotatedScriptElement
		public RuleCall getAnnotatedScriptElementParserRuleCall_0() { return cAnnotatedScriptElementParserRuleCall_0; }
		
		//N4ClassDeclaration<Yield=false>
		public RuleCall getN4ClassDeclarationParserRuleCall_1() { return cN4ClassDeclarationParserRuleCall_1; }
		
		//N4InterfaceDeclaration<Yield=false>
		public RuleCall getN4InterfaceDeclarationParserRuleCall_2() { return cN4InterfaceDeclarationParserRuleCall_2; }
		
		//N4EnumDeclaration<Yield=false>
		public RuleCall getN4EnumDeclarationParserRuleCall_3() { return cN4EnumDeclarationParserRuleCall_3; }
		
		//=> ImportDeclaration
		public RuleCall getImportDeclarationParserRuleCall_4() { return cImportDeclarationParserRuleCall_4; }
		
		//ExportDeclaration
		public RuleCall getExportDeclarationParserRuleCall_5() { return cExportDeclarationParserRuleCall_5; }
		
		//RootStatement<Yield=false>
		public RuleCall getRootStatementParserRuleCall_6() { return cRootStatementParserRuleCall_6; }
	}
	public class AnnotatedScriptElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotatedScriptElement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cAnnotationListParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Action cExportDeclarationAnnotationListAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final RuleCall cExportDeclarationImplParserRuleCall_1_0_1 = (RuleCall)cGroup_1_0.eContents().get(1);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Action cImportDeclarationAnnotationListAction_1_1_0 = (Action)cGroup_1_1.eContents().get(0);
		private final RuleCall cImportDeclarationImplParserRuleCall_1_1_1 = (RuleCall)cGroup_1_1.eContents().get(1);
		private final Group cGroup_1_2 = (Group)cAlternatives_1.eContents().get(2);
		private final Action cFunctionDeclarationAnnotationListAction_1_2_0 = (Action)cGroup_1_2.eContents().get(0);
		private final Group cGroup_1_2_1 = (Group)cGroup_1_2.eContents().get(1);
		private final Group cGroup_1_2_1_0 = (Group)cGroup_1_2_1.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_2_1_0_0 = (Assignment)cGroup_1_2_1_0.eContents().get(0);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_2_1_0_0_0 = (RuleCall)cDeclaredModifiersAssignment_1_2_1_0_0.eContents().get(0);
		private final RuleCall cAsyncNoTrailingLineBreakParserRuleCall_1_2_1_0_1 = (RuleCall)cGroup_1_2_1_0.eContents().get(1);
		private final RuleCall cFunctionImplParserRuleCall_1_2_1_0_2 = (RuleCall)cGroup_1_2_1_0.eContents().get(2);
		private final Group cGroup_1_3 = (Group)cAlternatives_1.eContents().get(3);
		private final Alternatives cAlternatives_1_3_0 = (Alternatives)cGroup_1_3.eContents().get(0);
		private final Group cGroup_1_3_0_0 = (Group)cAlternatives_1_3_0.eContents().get(0);
		private final Action cN4ClassDeclarationAnnotationListAction_1_3_0_0_0 = (Action)cGroup_1_3_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_3_0_0_1 = (Assignment)cGroup_1_3_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_3_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_3_0_0_1.eContents().get(0);
		private final Keyword cClassKeyword_1_3_0_0_2 = (Keyword)cGroup_1_3_0_0.eContents().get(2);
		private final Assignment cTypingStrategyAssignment_1_3_0_0_3 = (Assignment)cGroup_1_3_0_0.eContents().get(3);
		private final RuleCall cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_0_3_0 = (RuleCall)cTypingStrategyAssignment_1_3_0_0_3.eContents().get(0);
		private final Assignment cNameAssignment_1_3_0_0_4 = (Assignment)cGroup_1_3_0_0.eContents().get(4);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_3_0_0_4_0 = (RuleCall)cNameAssignment_1_3_0_0_4.eContents().get(0);
		private final RuleCall cVersionDeclarationParserRuleCall_1_3_0_0_5 = (RuleCall)cGroup_1_3_0_0.eContents().get(5);
		private final RuleCall cTypeVariablesParserRuleCall_1_3_0_0_6 = (RuleCall)cGroup_1_3_0_0.eContents().get(6);
		private final RuleCall cClassExtendsImplementsParserRuleCall_1_3_0_0_7 = (RuleCall)cGroup_1_3_0_0.eContents().get(7);
		private final Group cGroup_1_3_0_1 = (Group)cAlternatives_1_3_0.eContents().get(1);
		private final Action cN4InterfaceDeclarationAnnotationListAction_1_3_0_1_0 = (Action)cGroup_1_3_0_1.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_3_0_1_1 = (Assignment)cGroup_1_3_0_1.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_3_0_1_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_3_0_1_1.eContents().get(0);
		private final Keyword cInterfaceKeyword_1_3_0_1_2 = (Keyword)cGroup_1_3_0_1.eContents().get(2);
		private final Assignment cTypingStrategyAssignment_1_3_0_1_3 = (Assignment)cGroup_1_3_0_1.eContents().get(3);
		private final RuleCall cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_1_3_0 = (RuleCall)cTypingStrategyAssignment_1_3_0_1_3.eContents().get(0);
		private final Assignment cNameAssignment_1_3_0_1_4 = (Assignment)cGroup_1_3_0_1.eContents().get(4);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_3_0_1_4_0 = (RuleCall)cNameAssignment_1_3_0_1_4.eContents().get(0);
		private final RuleCall cVersionDeclarationParserRuleCall_1_3_0_1_5 = (RuleCall)cGroup_1_3_0_1.eContents().get(5);
		private final RuleCall cTypeVariablesParserRuleCall_1_3_0_1_6 = (RuleCall)cGroup_1_3_0_1.eContents().get(6);
		private final RuleCall cInterfaceExtendsListParserRuleCall_1_3_0_1_7 = (RuleCall)cGroup_1_3_0_1.eContents().get(7);
		private final RuleCall cMembersParserRuleCall_1_3_1 = (RuleCall)cGroup_1_3.eContents().get(1);
		private final Group cGroup_1_4 = (Group)cAlternatives_1.eContents().get(4);
		private final Action cN4EnumDeclarationAnnotationListAction_1_4_0 = (Action)cGroup_1_4.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_4_1 = (Assignment)cGroup_1_4.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_4_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_4_1.eContents().get(0);
		private final Keyword cEnumKeyword_1_4_2 = (Keyword)cGroup_1_4.eContents().get(2);
		private final Assignment cNameAssignment_1_4_3 = (Assignment)cGroup_1_4.eContents().get(3);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_4_3_0 = (RuleCall)cNameAssignment_1_4_3.eContents().get(0);
		private final RuleCall cVersionDeclarationParserRuleCall_1_4_4 = (RuleCall)cGroup_1_4.eContents().get(4);
		private final Keyword cLeftCurlyBracketKeyword_1_4_5 = (Keyword)cGroup_1_4.eContents().get(5);
		private final Assignment cLiteralsAssignment_1_4_6 = (Assignment)cGroup_1_4.eContents().get(6);
		private final RuleCall cLiteralsN4EnumLiteralParserRuleCall_1_4_6_0 = (RuleCall)cLiteralsAssignment_1_4_6.eContents().get(0);
		private final Group cGroup_1_4_7 = (Group)cGroup_1_4.eContents().get(7);
		private final Keyword cCommaKeyword_1_4_7_0 = (Keyword)cGroup_1_4_7.eContents().get(0);
		private final Assignment cLiteralsAssignment_1_4_7_1 = (Assignment)cGroup_1_4_7.eContents().get(1);
		private final RuleCall cLiteralsN4EnumLiteralParserRuleCall_1_4_7_1_0 = (RuleCall)cLiteralsAssignment_1_4_7_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_1_4_8 = (Keyword)cGroup_1_4.eContents().get(8);
		
		///**
		// * Left factored, annotated script elements.
		// *
		// * Pretty much inlined versions of type declarations, imports, exports and function declarations.
		// *
		// * The GrammarLinter ensures that the inlined content mirrors the content of the real declarations.
		// */ AnnotatedScriptElement ScriptElement:
		//	AnnotationList ({ExportDeclaration.annotationList=current} ExportDeclarationImpl
		//	| {ImportDeclaration.annotationList=current} ImportDeclarationImpl
		//	| {FunctionDeclaration.annotationList=current}
		//	=> (declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
		//	-> FunctionImpl <Yield=false,YieldIfGenerator=false,Expression=false>) | ({N4ClassDeclaration.annotationList=current}
		//	declaredModifiers+=N4Modifier*
		//	'class' typingStrategy=TypingStrategyDefSiteOperator?
		//	name=BindingIdentifier<Yield=false> VersionDeclaration?
		//	TypeVariables?
		//	ClassExtendsImplements<Yield=false>?
		//	| {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//	'interface' typingStrategy=TypingStrategyDefSiteOperator?
		//	name=BindingIdentifier<Yield=false> VersionDeclaration?
		//	TypeVariables?
		//	InterfaceExtendsList?) Members<Yield=false> | {N4EnumDeclaration.annotationList=current}
		//	declaredModifiers+=N4Modifier*
		//	'enum' name=BindingIdentifier<Yield=false> VersionDeclaration?
		//	'{'
		//	literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*
		//	'}');
		@Override public ParserRule getRule() { return rule; }
		
		//AnnotationList ({ExportDeclaration.annotationList=current} ExportDeclarationImpl |
		//{ImportDeclaration.annotationList=current} ImportDeclarationImpl | {FunctionDeclaration.annotationList=current} =>
		//(declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak -> FunctionImpl
		//<Yield=false,YieldIfGenerator=false,Expression=false>) | ({N4ClassDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* 'class' typingStrategy=TypingStrategyDefSiteOperator?
		//name=BindingIdentifier<Yield=false> VersionDeclaration? TypeVariables? ClassExtendsImplements<Yield=false>? |
		//{N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'interface'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> VersionDeclaration? TypeVariables?
		//InterfaceExtendsList?) Members<Yield=false> | {N4EnumDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//'enum' name=BindingIdentifier<Yield=false> VersionDeclaration? '{' literals+=N4EnumLiteral (','
		//literals+=N4EnumLiteral)* '}')
		public Group getGroup() { return cGroup; }
		
		//AnnotationList
		public RuleCall getAnnotationListParserRuleCall_0() { return cAnnotationListParserRuleCall_0; }
		
		//{ExportDeclaration.annotationList=current} ExportDeclarationImpl | {ImportDeclaration.annotationList=current}
		//ImportDeclarationImpl | {FunctionDeclaration.annotationList=current} => (declaredModifiers+=N4Modifier*
		//AsyncNoTrailingLineBreak -> FunctionImpl <Yield=false,YieldIfGenerator=false,Expression=false>) |
		//({N4ClassDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'class'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> VersionDeclaration? TypeVariables?
		//ClassExtendsImplements<Yield=false>? | {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> VersionDeclaration?
		//TypeVariables? InterfaceExtendsList?) Members<Yield=false> | {N4EnumDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* 'enum' name=BindingIdentifier<Yield=false> VersionDeclaration? '{'
		//literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)* '}'
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//{ExportDeclaration.annotationList=current} ExportDeclarationImpl
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{ExportDeclaration.annotationList=current}
		public Action getExportDeclarationAnnotationListAction_1_0_0() { return cExportDeclarationAnnotationListAction_1_0_0; }
		
		//ExportDeclarationImpl
		public RuleCall getExportDeclarationImplParserRuleCall_1_0_1() { return cExportDeclarationImplParserRuleCall_1_0_1; }
		
		//{ImportDeclaration.annotationList=current} ImportDeclarationImpl
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//{ImportDeclaration.annotationList=current}
		public Action getImportDeclarationAnnotationListAction_1_1_0() { return cImportDeclarationAnnotationListAction_1_1_0; }
		
		//ImportDeclarationImpl
		public RuleCall getImportDeclarationImplParserRuleCall_1_1_1() { return cImportDeclarationImplParserRuleCall_1_1_1; }
		
		//{FunctionDeclaration.annotationList=current} => (declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak -> FunctionImpl
		//<Yield=false,YieldIfGenerator=false,Expression=false>)
		public Group getGroup_1_2() { return cGroup_1_2; }
		
		//{FunctionDeclaration.annotationList=current}
		public Action getFunctionDeclarationAnnotationListAction_1_2_0() { return cFunctionDeclarationAnnotationListAction_1_2_0; }
		
		//=> (declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak -> FunctionImpl
		//<Yield=false,YieldIfGenerator=false,Expression=false>)
		public Group getGroup_1_2_1() { return cGroup_1_2_1; }
		
		//declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak -> FunctionImpl
		//<Yield=false,YieldIfGenerator=false,Expression=false>
		public Group getGroup_1_2_1_0() { return cGroup_1_2_1_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_2_1_0_0() { return cDeclaredModifiersAssignment_1_2_1_0_0; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_2_1_0_0_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_2_1_0_0_0; }
		
		//AsyncNoTrailingLineBreak
		public RuleCall getAsyncNoTrailingLineBreakParserRuleCall_1_2_1_0_1() { return cAsyncNoTrailingLineBreakParserRuleCall_1_2_1_0_1; }
		
		//-> FunctionImpl <Yield=false,YieldIfGenerator=false,Expression=false>
		public RuleCall getFunctionImplParserRuleCall_1_2_1_0_2() { return cFunctionImplParserRuleCall_1_2_1_0_2; }
		
		//({N4ClassDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'class'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> VersionDeclaration? TypeVariables?
		//ClassExtendsImplements<Yield=false>? | {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> VersionDeclaration?
		//TypeVariables? InterfaceExtendsList?) Members<Yield=false>
		public Group getGroup_1_3() { return cGroup_1_3; }
		
		//{N4ClassDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'class'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> VersionDeclaration? TypeVariables?
		//ClassExtendsImplements<Yield=false>? | {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> VersionDeclaration?
		//TypeVariables? InterfaceExtendsList?
		public Alternatives getAlternatives_1_3_0() { return cAlternatives_1_3_0; }
		
		//{N4ClassDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'class'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> VersionDeclaration? TypeVariables?
		//ClassExtendsImplements<Yield=false>?
		public Group getGroup_1_3_0_0() { return cGroup_1_3_0_0; }
		
		//{N4ClassDeclaration.annotationList=current}
		public Action getN4ClassDeclarationAnnotationListAction_1_3_0_0_0() { return cN4ClassDeclarationAnnotationListAction_1_3_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_3_0_0_1() { return cDeclaredModifiersAssignment_1_3_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_3_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_3_0_0_1_0; }
		
		//'class'
		public Keyword getClassKeyword_1_3_0_0_2() { return cClassKeyword_1_3_0_0_2; }
		
		//typingStrategy=TypingStrategyDefSiteOperator?
		public Assignment getTypingStrategyAssignment_1_3_0_0_3() { return cTypingStrategyAssignment_1_3_0_0_3; }
		
		//TypingStrategyDefSiteOperator
		public RuleCall getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_0_3_0() { return cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_0_3_0; }
		
		//name=BindingIdentifier<Yield=false>
		public Assignment getNameAssignment_1_3_0_0_4() { return cNameAssignment_1_3_0_0_4; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_3_0_0_4_0() { return cNameBindingIdentifierParserRuleCall_1_3_0_0_4_0; }
		
		//VersionDeclaration?
		public RuleCall getVersionDeclarationParserRuleCall_1_3_0_0_5() { return cVersionDeclarationParserRuleCall_1_3_0_0_5; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_1_3_0_0_6() { return cTypeVariablesParserRuleCall_1_3_0_0_6; }
		
		//ClassExtendsImplements<Yield=false>?
		public RuleCall getClassExtendsImplementsParserRuleCall_1_3_0_0_7() { return cClassExtendsImplementsParserRuleCall_1_3_0_0_7; }
		
		//{N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'interface'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> VersionDeclaration? TypeVariables?
		//InterfaceExtendsList?
		public Group getGroup_1_3_0_1() { return cGroup_1_3_0_1; }
		
		//{N4InterfaceDeclaration.annotationList=current}
		public Action getN4InterfaceDeclarationAnnotationListAction_1_3_0_1_0() { return cN4InterfaceDeclarationAnnotationListAction_1_3_0_1_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_3_0_1_1() { return cDeclaredModifiersAssignment_1_3_0_1_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_3_0_1_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_3_0_1_1_0; }
		
		//'interface'
		public Keyword getInterfaceKeyword_1_3_0_1_2() { return cInterfaceKeyword_1_3_0_1_2; }
		
		//typingStrategy=TypingStrategyDefSiteOperator?
		public Assignment getTypingStrategyAssignment_1_3_0_1_3() { return cTypingStrategyAssignment_1_3_0_1_3; }
		
		//TypingStrategyDefSiteOperator
		public RuleCall getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_1_3_0() { return cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_1_3_0; }
		
		//name=BindingIdentifier<Yield=false>
		public Assignment getNameAssignment_1_3_0_1_4() { return cNameAssignment_1_3_0_1_4; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_3_0_1_4_0() { return cNameBindingIdentifierParserRuleCall_1_3_0_1_4_0; }
		
		//VersionDeclaration?
		public RuleCall getVersionDeclarationParserRuleCall_1_3_0_1_5() { return cVersionDeclarationParserRuleCall_1_3_0_1_5; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_1_3_0_1_6() { return cTypeVariablesParserRuleCall_1_3_0_1_6; }
		
		//InterfaceExtendsList?
		public RuleCall getInterfaceExtendsListParserRuleCall_1_3_0_1_7() { return cInterfaceExtendsListParserRuleCall_1_3_0_1_7; }
		
		//Members<Yield=false>
		public RuleCall getMembersParserRuleCall_1_3_1() { return cMembersParserRuleCall_1_3_1; }
		
		//{N4EnumDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'enum' name=BindingIdentifier<Yield=false>
		//VersionDeclaration? '{' literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)* '}'
		public Group getGroup_1_4() { return cGroup_1_4; }
		
		//{N4EnumDeclaration.annotationList=current}
		public Action getN4EnumDeclarationAnnotationListAction_1_4_0() { return cN4EnumDeclarationAnnotationListAction_1_4_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_4_1() { return cDeclaredModifiersAssignment_1_4_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_4_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_4_1_0; }
		
		//'enum'
		public Keyword getEnumKeyword_1_4_2() { return cEnumKeyword_1_4_2; }
		
		//name=BindingIdentifier<Yield=false>
		public Assignment getNameAssignment_1_4_3() { return cNameAssignment_1_4_3; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_4_3_0() { return cNameBindingIdentifierParserRuleCall_1_4_3_0; }
		
		//VersionDeclaration?
		public RuleCall getVersionDeclarationParserRuleCall_1_4_4() { return cVersionDeclarationParserRuleCall_1_4_4; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1_4_5() { return cLeftCurlyBracketKeyword_1_4_5; }
		
		//literals+=N4EnumLiteral
		public Assignment getLiteralsAssignment_1_4_6() { return cLiteralsAssignment_1_4_6; }
		
		//N4EnumLiteral
		public RuleCall getLiteralsN4EnumLiteralParserRuleCall_1_4_6_0() { return cLiteralsN4EnumLiteralParserRuleCall_1_4_6_0; }
		
		//(',' literals+=N4EnumLiteral)*
		public Group getGroup_1_4_7() { return cGroup_1_4_7; }
		
		//','
		public Keyword getCommaKeyword_1_4_7_0() { return cCommaKeyword_1_4_7_0; }
		
		//literals+=N4EnumLiteral
		public Assignment getLiteralsAssignment_1_4_7_1() { return cLiteralsAssignment_1_4_7_1; }
		
		//N4EnumLiteral
		public RuleCall getLiteralsN4EnumLiteralParserRuleCall_1_4_7_1_0() { return cLiteralsN4EnumLiteralParserRuleCall_1_4_7_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_1_4_8() { return cRightCurlyBracketKeyword_1_4_8; }
	}
	public class ExportDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cExportDeclarationAction_0 = (Action)cGroup.eContents().get(0);
		private final RuleCall cExportDeclarationImplParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//ExportDeclaration:
		//	{ExportDeclaration} ExportDeclarationImpl;
		@Override public ParserRule getRule() { return rule; }
		
		//{ExportDeclaration} ExportDeclarationImpl
		public Group getGroup() { return cGroup; }
		
		//{ExportDeclaration}
		public Action getExportDeclarationAction_0() { return cExportDeclarationAction_0; }
		
		//ExportDeclarationImpl
		public RuleCall getExportDeclarationImplParserRuleCall_1() { return cExportDeclarationImplParserRuleCall_1; }
	}
	public class ExportDeclarationImplElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportDeclarationImpl");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cExportKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Assignment cWildcardExportAssignment_1_0_0 = (Assignment)cGroup_1_0.eContents().get(0);
		private final Keyword cWildcardExportAsteriskKeyword_1_0_0_0 = (Keyword)cWildcardExportAssignment_1_0_0.eContents().get(0);
		private final RuleCall cExportFromClauseParserRuleCall_1_0_1 = (RuleCall)cGroup_1_0.eContents().get(1);
		private final RuleCall cSemiParserRuleCall_1_0_2 = (RuleCall)cGroup_1_0.eContents().get(2);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final RuleCall cExportClauseParserRuleCall_1_1_0 = (RuleCall)cGroup_1_1.eContents().get(0);
		private final RuleCall cExportFromClauseParserRuleCall_1_1_1 = (RuleCall)cGroup_1_1.eContents().get(1);
		private final RuleCall cSemiParserRuleCall_1_1_2 = (RuleCall)cGroup_1_1.eContents().get(2);
		private final Assignment cExportedElementAssignment_1_2 = (Assignment)cAlternatives_1.eContents().get(2);
		private final RuleCall cExportedElementExportableElementParserRuleCall_1_2_0 = (RuleCall)cExportedElementAssignment_1_2.eContents().get(0);
		private final Group cGroup_1_3 = (Group)cAlternatives_1.eContents().get(3);
		private final Assignment cDefaultExportAssignment_1_3_0 = (Assignment)cGroup_1_3.eContents().get(0);
		private final Keyword cDefaultExportDefaultKeyword_1_3_0_0 = (Keyword)cDefaultExportAssignment_1_3_0.eContents().get(0);
		private final Alternatives cAlternatives_1_3_1 = (Alternatives)cGroup_1_3.eContents().get(1);
		private final Assignment cExportedElementAssignment_1_3_1_0 = (Assignment)cAlternatives_1_3_1.eContents().get(0);
		private final RuleCall cExportedElementExportableElementParserRuleCall_1_3_1_0_0 = (RuleCall)cExportedElementAssignment_1_3_1_0.eContents().get(0);
		private final Group cGroup_1_3_1_1 = (Group)cAlternatives_1_3_1.eContents().get(1);
		private final Assignment cDefaultExportedExpressionAssignment_1_3_1_1_0 = (Assignment)cGroup_1_3_1_1.eContents().get(0);
		private final RuleCall cDefaultExportedExpressionAssignmentExpressionParserRuleCall_1_3_1_1_0_0 = (RuleCall)cDefaultExportedExpressionAssignment_1_3_1_1_0.eContents().get(0);
		private final RuleCall cSemiParserRuleCall_1_3_1_1_1 = (RuleCall)cGroup_1_3_1_1.eContents().get(1);
		
		//fragment ExportDeclarationImpl *:
		//	'export' (wildcardExport?='*' ExportFromClause Semi
		//	| ExportClause -> ExportFromClause? Semi
		//	| exportedElement=ExportableElement
		//	| defaultExport?='default' (-> exportedElement=ExportableElement |
		//	defaultExportedExpression=AssignmentExpression<In=true,Yield=false> Semi));
		@Override public ParserRule getRule() { return rule; }
		
		//'export' (wildcardExport?='*' ExportFromClause Semi | ExportClause -> ExportFromClause? Semi |
		//exportedElement=ExportableElement | defaultExport?='default' (-> exportedElement=ExportableElement |
		//defaultExportedExpression=AssignmentExpression<In=true,Yield=false> Semi))
		public Group getGroup() { return cGroup; }
		
		//'export'
		public Keyword getExportKeyword_0() { return cExportKeyword_0; }
		
		//wildcardExport?='*' ExportFromClause Semi | ExportClause -> ExportFromClause? Semi | exportedElement=ExportableElement |
		//defaultExport?='default' (-> exportedElement=ExportableElement |
		//defaultExportedExpression=AssignmentExpression<In=true,Yield=false> Semi)
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//wildcardExport?='*' ExportFromClause Semi
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//wildcardExport?='*'
		public Assignment getWildcardExportAssignment_1_0_0() { return cWildcardExportAssignment_1_0_0; }
		
		//'*'
		public Keyword getWildcardExportAsteriskKeyword_1_0_0_0() { return cWildcardExportAsteriskKeyword_1_0_0_0; }
		
		//ExportFromClause
		public RuleCall getExportFromClauseParserRuleCall_1_0_1() { return cExportFromClauseParserRuleCall_1_0_1; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_1_0_2() { return cSemiParserRuleCall_1_0_2; }
		
		//ExportClause -> ExportFromClause? Semi
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//ExportClause
		public RuleCall getExportClauseParserRuleCall_1_1_0() { return cExportClauseParserRuleCall_1_1_0; }
		
		//-> ExportFromClause?
		public RuleCall getExportFromClauseParserRuleCall_1_1_1() { return cExportFromClauseParserRuleCall_1_1_1; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_1_1_2() { return cSemiParserRuleCall_1_1_2; }
		
		//exportedElement=ExportableElement
		public Assignment getExportedElementAssignment_1_2() { return cExportedElementAssignment_1_2; }
		
		//ExportableElement
		public RuleCall getExportedElementExportableElementParserRuleCall_1_2_0() { return cExportedElementExportableElementParserRuleCall_1_2_0; }
		
		//defaultExport?='default' (-> exportedElement=ExportableElement |
		//defaultExportedExpression=AssignmentExpression<In=true,Yield=false> Semi)
		public Group getGroup_1_3() { return cGroup_1_3; }
		
		//defaultExport?='default'
		public Assignment getDefaultExportAssignment_1_3_0() { return cDefaultExportAssignment_1_3_0; }
		
		//'default'
		public Keyword getDefaultExportDefaultKeyword_1_3_0_0() { return cDefaultExportDefaultKeyword_1_3_0_0; }
		
		//-> exportedElement=ExportableElement | defaultExportedExpression=AssignmentExpression<In=true,Yield=false> Semi
		public Alternatives getAlternatives_1_3_1() { return cAlternatives_1_3_1; }
		
		//-> exportedElement=ExportableElement
		public Assignment getExportedElementAssignment_1_3_1_0() { return cExportedElementAssignment_1_3_1_0; }
		
		//ExportableElement
		public RuleCall getExportedElementExportableElementParserRuleCall_1_3_1_0_0() { return cExportedElementExportableElementParserRuleCall_1_3_1_0_0; }
		
		//defaultExportedExpression=AssignmentExpression<In=true,Yield=false> Semi
		public Group getGroup_1_3_1_1() { return cGroup_1_3_1_1; }
		
		//defaultExportedExpression=AssignmentExpression<In=true,Yield=false>
		public Assignment getDefaultExportedExpressionAssignment_1_3_1_1_0() { return cDefaultExportedExpressionAssignment_1_3_1_1_0; }
		
		//AssignmentExpression<In=true,Yield=false>
		public RuleCall getDefaultExportedExpressionAssignmentExpressionParserRuleCall_1_3_1_1_0_0() { return cDefaultExportedExpressionAssignmentExpressionParserRuleCall_1_3_1_1_0_0; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_1_3_1_1_1() { return cSemiParserRuleCall_1_3_1_1_1; }
	}
	public class ExportFromClauseElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportFromClause");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cFromKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cReexportedFromAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final CrossReference cReexportedFromTModuleCrossReference_1_0 = (CrossReference)cReexportedFromAssignment_1.eContents().get(0);
		private final RuleCall cReexportedFromTModuleModuleSpecifierParserRuleCall_1_0_1 = (RuleCall)cReexportedFromTModuleCrossReference_1_0.eContents().get(1);
		
		//fragment ExportFromClause *:
		//	'from' reexportedFrom=[types::TModule|ModuleSpecifier];
		@Override public ParserRule getRule() { return rule; }
		
		//'from' reexportedFrom=[types::TModule|ModuleSpecifier]
		public Group getGroup() { return cGroup; }
		
		//'from'
		public Keyword getFromKeyword_0() { return cFromKeyword_0; }
		
		//reexportedFrom=[types::TModule|ModuleSpecifier]
		public Assignment getReexportedFromAssignment_1() { return cReexportedFromAssignment_1; }
		
		//[types::TModule|ModuleSpecifier]
		public CrossReference getReexportedFromTModuleCrossReference_1_0() { return cReexportedFromTModuleCrossReference_1_0; }
		
		//ModuleSpecifier
		public RuleCall getReexportedFromTModuleModuleSpecifierParserRuleCall_1_0_1() { return cReexportedFromTModuleModuleSpecifierParserRuleCall_1_0_1; }
	}
	public class ExportClauseElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportClause");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Assignment cNamedExportsAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cNamedExportsExportSpecifierParserRuleCall_1_0_0 = (RuleCall)cNamedExportsAssignment_1_0.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Keyword cCommaKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cNamedExportsAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cNamedExportsExportSpecifierParserRuleCall_1_1_1_0 = (RuleCall)cNamedExportsAssignment_1_1_1.eContents().get(0);
		private final Keyword cCommaKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		private final Keyword cRightCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//fragment ExportClause *:
		//	'{' (namedExports+=ExportSpecifier (',' namedExports+=ExportSpecifier)* ','?)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//'{' (namedExports+=ExportSpecifier (',' namedExports+=ExportSpecifier)* ','?)? '}'
		public Group getGroup() { return cGroup; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_0() { return cLeftCurlyBracketKeyword_0; }
		
		//(namedExports+=ExportSpecifier (',' namedExports+=ExportSpecifier)* ','?)?
		public Group getGroup_1() { return cGroup_1; }
		
		//namedExports+=ExportSpecifier
		public Assignment getNamedExportsAssignment_1_0() { return cNamedExportsAssignment_1_0; }
		
		//ExportSpecifier
		public RuleCall getNamedExportsExportSpecifierParserRuleCall_1_0_0() { return cNamedExportsExportSpecifierParserRuleCall_1_0_0; }
		
		//(',' namedExports+=ExportSpecifier)*
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//','
		public Keyword getCommaKeyword_1_1_0() { return cCommaKeyword_1_1_0; }
		
		//namedExports+=ExportSpecifier
		public Assignment getNamedExportsAssignment_1_1_1() { return cNamedExportsAssignment_1_1_1; }
		
		//ExportSpecifier
		public RuleCall getNamedExportsExportSpecifierParserRuleCall_1_1_1_0() { return cNamedExportsExportSpecifierParserRuleCall_1_1_1_0; }
		
		//','?
		public Keyword getCommaKeyword_1_2() { return cCommaKeyword_1_2; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_2() { return cRightCurlyBracketKeyword_2; }
	}
	public class ExportSpecifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportSpecifier");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cElementAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cElementIdentifierRefParserRuleCall_0_0 = (RuleCall)cElementAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cAsKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cAliasAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cAliasIdentifierNameParserRuleCall_1_1_0 = (RuleCall)cAliasAssignment_1_1.eContents().get(0);
		
		//ExportSpecifier:
		//	element=IdentifierRef<Yield=false> ('as' alias=IdentifierName)?;
		@Override public ParserRule getRule() { return rule; }
		
		//element=IdentifierRef<Yield=false> ('as' alias=IdentifierName)?
		public Group getGroup() { return cGroup; }
		
		//element=IdentifierRef<Yield=false>
		public Assignment getElementAssignment_0() { return cElementAssignment_0; }
		
		//IdentifierRef<Yield=false>
		public RuleCall getElementIdentifierRefParserRuleCall_0_0() { return cElementIdentifierRefParserRuleCall_0_0; }
		
		//('as' alias=IdentifierName)?
		public Group getGroup_1() { return cGroup_1; }
		
		//'as'
		public Keyword getAsKeyword_1_0() { return cAsKeyword_1_0; }
		
		//alias=IdentifierName
		public Assignment getAliasAssignment_1_1() { return cAliasAssignment_1_1; }
		
		//IdentifierName
		public RuleCall getAliasIdentifierNameParserRuleCall_1_1_0() { return cAliasIdentifierNameParserRuleCall_1_1_0; }
	}
	public class ExportableElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportableElement");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cAnnotatedExportableElementParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cN4ClassDeclarationParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cN4InterfaceDeclarationParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cN4EnumDeclarationParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cFunctionDeclarationParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cExportedVariableStatementParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		
		//ExportableElement:
		//	AnnotatedExportableElement<Yield=false> | N4ClassDeclaration<Yield=false> | N4InterfaceDeclaration<Yield=false> |
		//	N4EnumDeclaration<Yield=false> | FunctionDeclaration<Yield=false> | ExportedVariableStatement;
		@Override public ParserRule getRule() { return rule; }
		
		//AnnotatedExportableElement<Yield=false> | N4ClassDeclaration<Yield=false> | N4InterfaceDeclaration<Yield=false> |
		//N4EnumDeclaration<Yield=false> | FunctionDeclaration<Yield=false> | ExportedVariableStatement
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//AnnotatedExportableElement<Yield=false>
		public RuleCall getAnnotatedExportableElementParserRuleCall_0() { return cAnnotatedExportableElementParserRuleCall_0; }
		
		//N4ClassDeclaration<Yield=false>
		public RuleCall getN4ClassDeclarationParserRuleCall_1() { return cN4ClassDeclarationParserRuleCall_1; }
		
		//N4InterfaceDeclaration<Yield=false>
		public RuleCall getN4InterfaceDeclarationParserRuleCall_2() { return cN4InterfaceDeclarationParserRuleCall_2; }
		
		//N4EnumDeclaration<Yield=false>
		public RuleCall getN4EnumDeclarationParserRuleCall_3() { return cN4EnumDeclarationParserRuleCall_3; }
		
		//FunctionDeclaration<Yield=false>
		public RuleCall getFunctionDeclarationParserRuleCall_4() { return cFunctionDeclarationParserRuleCall_4; }
		
		//ExportedVariableStatement
		public RuleCall getExportedVariableStatementParserRuleCall_5() { return cExportedVariableStatementParserRuleCall_5; }
	}
	public class AnnotatedExportableElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotatedExportableElement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cAnnotationListParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Action cFunctionDeclarationAnnotationListAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_0_1.eContents().get(0);
		private final RuleCall cAsyncNoTrailingLineBreakParserRuleCall_1_0_2 = (RuleCall)cGroup_1_0.eContents().get(2);
		private final RuleCall cFunctionImplParserRuleCall_1_0_3 = (RuleCall)cGroup_1_0.eContents().get(3);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Action cExportedVariableStatementAnnotationListAction_1_1_0 = (Action)cGroup_1_1.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_1_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_1_1.eContents().get(0);
		private final Assignment cVarStmtKeywordAssignment_1_1_2 = (Assignment)cGroup_1_1.eContents().get(2);
		private final RuleCall cVarStmtKeywordVariableStatementKeywordEnumRuleCall_1_1_2_0 = (RuleCall)cVarStmtKeywordAssignment_1_1_2.eContents().get(0);
		private final Assignment cVarDeclsOrBindingsAssignment_1_1_3 = (Assignment)cGroup_1_1.eContents().get(3);
		private final RuleCall cVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_3_0 = (RuleCall)cVarDeclsOrBindingsAssignment_1_1_3.eContents().get(0);
		private final Group cGroup_1_1_4 = (Group)cGroup_1_1.eContents().get(4);
		private final Keyword cCommaKeyword_1_1_4_0 = (Keyword)cGroup_1_1_4.eContents().get(0);
		private final Assignment cVarDeclsOrBindingsAssignment_1_1_4_1 = (Assignment)cGroup_1_1_4.eContents().get(1);
		private final RuleCall cVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_4_1_0 = (RuleCall)cVarDeclsOrBindingsAssignment_1_1_4_1.eContents().get(0);
		private final RuleCall cSemiParserRuleCall_1_1_5 = (RuleCall)cGroup_1_1.eContents().get(5);
		private final Group cGroup_1_2 = (Group)cAlternatives_1.eContents().get(2);
		private final Alternatives cAlternatives_1_2_0 = (Alternatives)cGroup_1_2.eContents().get(0);
		private final Group cGroup_1_2_0_0 = (Group)cAlternatives_1_2_0.eContents().get(0);
		private final Action cN4ClassDeclarationAnnotationListAction_1_2_0_0_0 = (Action)cGroup_1_2_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_2_0_0_1 = (Assignment)cGroup_1_2_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_2_0_0_1.eContents().get(0);
		private final Keyword cClassKeyword_1_2_0_0_2 = (Keyword)cGroup_1_2_0_0.eContents().get(2);
		private final Assignment cTypingStrategyAssignment_1_2_0_0_3 = (Assignment)cGroup_1_2_0_0.eContents().get(3);
		private final RuleCall cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_0_3_0 = (RuleCall)cTypingStrategyAssignment_1_2_0_0_3.eContents().get(0);
		private final Assignment cNameAssignment_1_2_0_0_4 = (Assignment)cGroup_1_2_0_0.eContents().get(4);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_2_0_0_4_0 = (RuleCall)cNameAssignment_1_2_0_0_4.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_1_2_0_0_5 = (RuleCall)cGroup_1_2_0_0.eContents().get(5);
		private final RuleCall cClassExtendsImplementsParserRuleCall_1_2_0_0_6 = (RuleCall)cGroup_1_2_0_0.eContents().get(6);
		private final Group cGroup_1_2_0_1 = (Group)cAlternatives_1_2_0.eContents().get(1);
		private final Action cN4InterfaceDeclarationAnnotationListAction_1_2_0_1_0 = (Action)cGroup_1_2_0_1.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_2_0_1_1 = (Assignment)cGroup_1_2_0_1.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_1_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_2_0_1_1.eContents().get(0);
		private final Keyword cInterfaceKeyword_1_2_0_1_2 = (Keyword)cGroup_1_2_0_1.eContents().get(2);
		private final Assignment cTypingStrategyAssignment_1_2_0_1_3 = (Assignment)cGroup_1_2_0_1.eContents().get(3);
		private final RuleCall cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_1_3_0 = (RuleCall)cTypingStrategyAssignment_1_2_0_1_3.eContents().get(0);
		private final Assignment cNameAssignment_1_2_0_1_4 = (Assignment)cGroup_1_2_0_1.eContents().get(4);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_2_0_1_4_0 = (RuleCall)cNameAssignment_1_2_0_1_4.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_1_2_0_1_5 = (RuleCall)cGroup_1_2_0_1.eContents().get(5);
		private final RuleCall cInterfaceExtendsListParserRuleCall_1_2_0_1_6 = (RuleCall)cGroup_1_2_0_1.eContents().get(6);
		private final RuleCall cMembersParserRuleCall_1_2_1 = (RuleCall)cGroup_1_2.eContents().get(1);
		private final Group cGroup_1_3 = (Group)cAlternatives_1.eContents().get(3);
		private final Action cN4EnumDeclarationAnnotationListAction_1_3_0 = (Action)cGroup_1_3.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_3_1 = (Assignment)cGroup_1_3.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_3_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_3_1.eContents().get(0);
		private final Keyword cEnumKeyword_1_3_2 = (Keyword)cGroup_1_3.eContents().get(2);
		private final Assignment cNameAssignment_1_3_3 = (Assignment)cGroup_1_3.eContents().get(3);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_3_3_0 = (RuleCall)cNameAssignment_1_3_3.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1_3_4 = (Keyword)cGroup_1_3.eContents().get(4);
		private final Assignment cLiteralsAssignment_1_3_5 = (Assignment)cGroup_1_3.eContents().get(5);
		private final RuleCall cLiteralsN4EnumLiteralParserRuleCall_1_3_5_0 = (RuleCall)cLiteralsAssignment_1_3_5.eContents().get(0);
		private final Group cGroup_1_3_6 = (Group)cGroup_1_3.eContents().get(6);
		private final Keyword cCommaKeyword_1_3_6_0 = (Keyword)cGroup_1_3_6.eContents().get(0);
		private final Assignment cLiteralsAssignment_1_3_6_1 = (Assignment)cGroup_1_3_6.eContents().get(1);
		private final RuleCall cLiteralsN4EnumLiteralParserRuleCall_1_3_6_1_0 = (RuleCall)cLiteralsAssignment_1_3_6_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_1_3_7 = (Keyword)cGroup_1_3.eContents().get(7);
		
		///**
		// * Left factored, annotated exportable elements.
		// *
		// * Pretty much inlined versions of type and function declarations.
		// *
		// * The GrammarLinter ensures that the inlined content mirrors the content of the real declarations.
		// */ AnnotatedExportableElement <Yield ExportableElement:
		//	AnnotationList ({FunctionDeclaration.annotationList=current} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
		//	FunctionImpl<Yield,Yield,Expression=false> | {ExportedVariableStatement.annotationList=current}
		//	declaredModifiers+=N4Modifier*
		//	varStmtKeyword=VariableStatementKeyword
		//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield> (','
		//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield>)* Semi
		//	| ({N4ClassDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//	'class' typingStrategy=TypingStrategyDefSiteOperator?
		//	name=BindingIdentifier<Yield> TypeVariables?
		//	ClassExtendsImplements<Yield>?
		//	| {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//	'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield> TypeVariables?
		//	InterfaceExtendsList?) Members<Yield> | {N4EnumDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//	'enum' name=BindingIdentifier<Yield>
		//	'{'
		//	literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*
		//	'}');
		@Override public ParserRule getRule() { return rule; }
		
		//AnnotationList ({FunctionDeclaration.annotationList=current} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
		//FunctionImpl<Yield,Yield,Expression=false> | {ExportedVariableStatement.annotationList=current}
		//declaredModifiers+=N4Modifier* varStmtKeyword=VariableStatementKeyword
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield> (','
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield>)* Semi | ({N4ClassDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* 'class' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>
		//TypeVariables? ClassExtendsImplements<Yield>? | {N4InterfaceDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* 'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>
		//TypeVariables? InterfaceExtendsList?) Members<Yield> | {N4EnumDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* 'enum' name=BindingIdentifier<Yield> '{' literals+=N4EnumLiteral (','
		//literals+=N4EnumLiteral)* '}')
		public Group getGroup() { return cGroup; }
		
		//AnnotationList
		public RuleCall getAnnotationListParserRuleCall_0() { return cAnnotationListParserRuleCall_0; }
		
		//{FunctionDeclaration.annotationList=current} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
		//FunctionImpl<Yield,Yield,Expression=false> | {ExportedVariableStatement.annotationList=current}
		//declaredModifiers+=N4Modifier* varStmtKeyword=VariableStatementKeyword
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield> (','
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield>)* Semi | ({N4ClassDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* 'class' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>
		//TypeVariables? ClassExtendsImplements<Yield>? | {N4InterfaceDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* 'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>
		//TypeVariables? InterfaceExtendsList?) Members<Yield> | {N4EnumDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* 'enum' name=BindingIdentifier<Yield> '{' literals+=N4EnumLiteral (','
		//literals+=N4EnumLiteral)* '}'
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//{FunctionDeclaration.annotationList=current} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
		//FunctionImpl<Yield,Yield,Expression=false>
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{FunctionDeclaration.annotationList=current}
		public Action getFunctionDeclarationAnnotationListAction_1_0_0() { return cFunctionDeclarationAnnotationListAction_1_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_0_1() { return cDeclaredModifiersAssignment_1_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_0_1_0; }
		
		//AsyncNoTrailingLineBreak
		public RuleCall getAsyncNoTrailingLineBreakParserRuleCall_1_0_2() { return cAsyncNoTrailingLineBreakParserRuleCall_1_0_2; }
		
		//FunctionImpl<Yield,Yield,Expression=false>
		public RuleCall getFunctionImplParserRuleCall_1_0_3() { return cFunctionImplParserRuleCall_1_0_3; }
		
		//{ExportedVariableStatement.annotationList=current} declaredModifiers+=N4Modifier*
		//varStmtKeyword=VariableStatementKeyword varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield> (','
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield>)* Semi
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//{ExportedVariableStatement.annotationList=current}
		public Action getExportedVariableStatementAnnotationListAction_1_1_0() { return cExportedVariableStatementAnnotationListAction_1_1_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_1_1() { return cDeclaredModifiersAssignment_1_1_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_1_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_1_1_0; }
		
		//varStmtKeyword=VariableStatementKeyword
		public Assignment getVarStmtKeywordAssignment_1_1_2() { return cVarStmtKeywordAssignment_1_1_2; }
		
		//VariableStatementKeyword
		public RuleCall getVarStmtKeywordVariableStatementKeywordEnumRuleCall_1_1_2_0() { return cVarStmtKeywordVariableStatementKeywordEnumRuleCall_1_1_2_0; }
		
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield>
		public Assignment getVarDeclsOrBindingsAssignment_1_1_3() { return cVarDeclsOrBindingsAssignment_1_1_3; }
		
		//ExportedVariableDeclarationOrBinding<Yield>
		public RuleCall getVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_3_0() { return cVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_3_0; }
		
		//(',' varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield>)*
		public Group getGroup_1_1_4() { return cGroup_1_1_4; }
		
		//','
		public Keyword getCommaKeyword_1_1_4_0() { return cCommaKeyword_1_1_4_0; }
		
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield>
		public Assignment getVarDeclsOrBindingsAssignment_1_1_4_1() { return cVarDeclsOrBindingsAssignment_1_1_4_1; }
		
		//ExportedVariableDeclarationOrBinding<Yield>
		public RuleCall getVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_4_1_0() { return cVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_4_1_0; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_1_1_5() { return cSemiParserRuleCall_1_1_5; }
		
		//({N4ClassDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'class'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield> TypeVariables?
		//ClassExtendsImplements<Yield>? | {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield> TypeVariables?
		//InterfaceExtendsList?) Members<Yield>
		public Group getGroup_1_2() { return cGroup_1_2; }
		
		//{N4ClassDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'class'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield> TypeVariables?
		//ClassExtendsImplements<Yield>? | {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield> TypeVariables?
		//InterfaceExtendsList?
		public Alternatives getAlternatives_1_2_0() { return cAlternatives_1_2_0; }
		
		//{N4ClassDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'class'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield> TypeVariables?
		//ClassExtendsImplements<Yield>?
		public Group getGroup_1_2_0_0() { return cGroup_1_2_0_0; }
		
		//{N4ClassDeclaration.annotationList=current}
		public Action getN4ClassDeclarationAnnotationListAction_1_2_0_0_0() { return cN4ClassDeclarationAnnotationListAction_1_2_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_2_0_0_1() { return cDeclaredModifiersAssignment_1_2_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_0_1_0; }
		
		//'class'
		public Keyword getClassKeyword_1_2_0_0_2() { return cClassKeyword_1_2_0_0_2; }
		
		//typingStrategy=TypingStrategyDefSiteOperator?
		public Assignment getTypingStrategyAssignment_1_2_0_0_3() { return cTypingStrategyAssignment_1_2_0_0_3; }
		
		//TypingStrategyDefSiteOperator
		public RuleCall getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_0_3_0() { return cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_0_3_0; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment_1_2_0_0_4() { return cNameAssignment_1_2_0_0_4; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_2_0_0_4_0() { return cNameBindingIdentifierParserRuleCall_1_2_0_0_4_0; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_1_2_0_0_5() { return cTypeVariablesParserRuleCall_1_2_0_0_5; }
		
		//ClassExtendsImplements<Yield>?
		public RuleCall getClassExtendsImplementsParserRuleCall_1_2_0_0_6() { return cClassExtendsImplementsParserRuleCall_1_2_0_0_6; }
		
		//{N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'interface'
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield> TypeVariables? InterfaceExtendsList?
		public Group getGroup_1_2_0_1() { return cGroup_1_2_0_1; }
		
		//{N4InterfaceDeclaration.annotationList=current}
		public Action getN4InterfaceDeclarationAnnotationListAction_1_2_0_1_0() { return cN4InterfaceDeclarationAnnotationListAction_1_2_0_1_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_2_0_1_1() { return cDeclaredModifiersAssignment_1_2_0_1_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_1_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_1_1_0; }
		
		//'interface'
		public Keyword getInterfaceKeyword_1_2_0_1_2() { return cInterfaceKeyword_1_2_0_1_2; }
		
		//typingStrategy=TypingStrategyDefSiteOperator?
		public Assignment getTypingStrategyAssignment_1_2_0_1_3() { return cTypingStrategyAssignment_1_2_0_1_3; }
		
		//TypingStrategyDefSiteOperator
		public RuleCall getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_1_3_0() { return cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_1_3_0; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment_1_2_0_1_4() { return cNameAssignment_1_2_0_1_4; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_2_0_1_4_0() { return cNameBindingIdentifierParserRuleCall_1_2_0_1_4_0; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_1_2_0_1_5() { return cTypeVariablesParserRuleCall_1_2_0_1_5; }
		
		//InterfaceExtendsList?
		public RuleCall getInterfaceExtendsListParserRuleCall_1_2_0_1_6() { return cInterfaceExtendsListParserRuleCall_1_2_0_1_6; }
		
		//Members<Yield>
		public RuleCall getMembersParserRuleCall_1_2_1() { return cMembersParserRuleCall_1_2_1; }
		
		//{N4EnumDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'enum' name=BindingIdentifier<Yield> '{'
		//literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)* '}'
		public Group getGroup_1_3() { return cGroup_1_3; }
		
		//{N4EnumDeclaration.annotationList=current}
		public Action getN4EnumDeclarationAnnotationListAction_1_3_0() { return cN4EnumDeclarationAnnotationListAction_1_3_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_3_1() { return cDeclaredModifiersAssignment_1_3_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_3_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_3_1_0; }
		
		//'enum'
		public Keyword getEnumKeyword_1_3_2() { return cEnumKeyword_1_3_2; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment_1_3_3() { return cNameAssignment_1_3_3; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_3_3_0() { return cNameBindingIdentifierParserRuleCall_1_3_3_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1_3_4() { return cLeftCurlyBracketKeyword_1_3_4; }
		
		//literals+=N4EnumLiteral
		public Assignment getLiteralsAssignment_1_3_5() { return cLiteralsAssignment_1_3_5; }
		
		//N4EnumLiteral
		public RuleCall getLiteralsN4EnumLiteralParserRuleCall_1_3_5_0() { return cLiteralsN4EnumLiteralParserRuleCall_1_3_5_0; }
		
		//(',' literals+=N4EnumLiteral)*
		public Group getGroup_1_3_6() { return cGroup_1_3_6; }
		
		//','
		public Keyword getCommaKeyword_1_3_6_0() { return cCommaKeyword_1_3_6_0; }
		
		//literals+=N4EnumLiteral
		public Assignment getLiteralsAssignment_1_3_6_1() { return cLiteralsAssignment_1_3_6_1; }
		
		//N4EnumLiteral
		public RuleCall getLiteralsN4EnumLiteralParserRuleCall_1_3_6_1_0() { return cLiteralsN4EnumLiteralParserRuleCall_1_3_6_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_1_3_7() { return cRightCurlyBracketKeyword_1_3_7; }
	}
	public class ImportDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ImportDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cImportDeclarationAction_0 = (Action)cGroup.eContents().get(0);
		private final RuleCall cImportDeclarationImplParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//ImportDeclaration:
		//	{ImportDeclaration} ImportDeclarationImpl;
		@Override public ParserRule getRule() { return rule; }
		
		//{ImportDeclaration} ImportDeclarationImpl
		public Group getGroup() { return cGroup; }
		
		//{ImportDeclaration}
		public Action getImportDeclarationAction_0() { return cImportDeclarationAction_0; }
		
		//ImportDeclarationImpl
		public RuleCall getImportDeclarationImplParserRuleCall_1() { return cImportDeclarationImplParserRuleCall_1; }
	}
	public class ImportDeclarationImplElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ImportDeclarationImpl");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cImportKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final RuleCall cImportClauseParserRuleCall_1_0 = (RuleCall)cGroup_1.eContents().get(0);
		private final Assignment cImportFromAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final Keyword cImportFromFromKeyword_1_1_0 = (Keyword)cImportFromAssignment_1_1.eContents().get(0);
		private final Assignment cModuleAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final CrossReference cModuleTModuleCrossReference_2_0 = (CrossReference)cModuleAssignment_2.eContents().get(0);
		private final RuleCall cModuleTModuleModuleSpecifierParserRuleCall_2_0_1 = (RuleCall)cModuleTModuleCrossReference_2_0.eContents().get(1);
		private final RuleCall cSemiParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		//fragment ImportDeclarationImpl *:
		//	'import' (ImportClause importFrom?='from')? module=[types::TModule|ModuleSpecifier] Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//'import' (ImportClause importFrom?='from')? module=[types::TModule|ModuleSpecifier] Semi
		public Group getGroup() { return cGroup; }
		
		//'import'
		public Keyword getImportKeyword_0() { return cImportKeyword_0; }
		
		//(ImportClause importFrom?='from')?
		public Group getGroup_1() { return cGroup_1; }
		
		//ImportClause
		public RuleCall getImportClauseParserRuleCall_1_0() { return cImportClauseParserRuleCall_1_0; }
		
		//importFrom?='from'
		public Assignment getImportFromAssignment_1_1() { return cImportFromAssignment_1_1; }
		
		//'from'
		public Keyword getImportFromFromKeyword_1_1_0() { return cImportFromFromKeyword_1_1_0; }
		
		//module=[types::TModule|ModuleSpecifier]
		public Assignment getModuleAssignment_2() { return cModuleAssignment_2; }
		
		//[types::TModule|ModuleSpecifier]
		public CrossReference getModuleTModuleCrossReference_2_0() { return cModuleTModuleCrossReference_2_0; }
		
		//ModuleSpecifier
		public RuleCall getModuleTModuleModuleSpecifierParserRuleCall_2_0_1() { return cModuleTModuleModuleSpecifierParserRuleCall_2_0_1; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_3() { return cSemiParserRuleCall_3; }
	}
	public class ImportClauseElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ImportClause");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Assignment cImportSpecifiersAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final RuleCall cImportSpecifiersDefaultImportSpecifierParserRuleCall_0_0_0 = (RuleCall)cImportSpecifiersAssignment_0_0.eContents().get(0);
		private final Group cGroup_0_1 = (Group)cGroup_0.eContents().get(1);
		private final Keyword cCommaKeyword_0_1_0 = (Keyword)cGroup_0_1.eContents().get(0);
		private final RuleCall cImportSpecifiersExceptDefaultParserRuleCall_0_1_1 = (RuleCall)cGroup_0_1.eContents().get(1);
		private final RuleCall cImportSpecifiersExceptDefaultParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//fragment ImportClause returns ImportDeclaration:
		//	importSpecifiers+=DefaultImportSpecifier (',' ImportSpecifiersExceptDefault)?
		//	| ImportSpecifiersExceptDefault;
		@Override public ParserRule getRule() { return rule; }
		
		//importSpecifiers+=DefaultImportSpecifier (',' ImportSpecifiersExceptDefault)? | ImportSpecifiersExceptDefault
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//importSpecifiers+=DefaultImportSpecifier (',' ImportSpecifiersExceptDefault)?
		public Group getGroup_0() { return cGroup_0; }
		
		//importSpecifiers+=DefaultImportSpecifier
		public Assignment getImportSpecifiersAssignment_0_0() { return cImportSpecifiersAssignment_0_0; }
		
		//DefaultImportSpecifier
		public RuleCall getImportSpecifiersDefaultImportSpecifierParserRuleCall_0_0_0() { return cImportSpecifiersDefaultImportSpecifierParserRuleCall_0_0_0; }
		
		//(',' ImportSpecifiersExceptDefault)?
		public Group getGroup_0_1() { return cGroup_0_1; }
		
		//','
		public Keyword getCommaKeyword_0_1_0() { return cCommaKeyword_0_1_0; }
		
		//ImportSpecifiersExceptDefault
		public RuleCall getImportSpecifiersExceptDefaultParserRuleCall_0_1_1() { return cImportSpecifiersExceptDefaultParserRuleCall_0_1_1; }
		
		//ImportSpecifiersExceptDefault
		public RuleCall getImportSpecifiersExceptDefaultParserRuleCall_1() { return cImportSpecifiersExceptDefaultParserRuleCall_1; }
	}
	public class ImportSpecifiersExceptDefaultElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ImportSpecifiersExceptDefault");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cImportSpecifiersAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final RuleCall cImportSpecifiersNamespaceImportSpecifierParserRuleCall_0_0 = (RuleCall)cImportSpecifiersAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Assignment cImportSpecifiersAssignment_1_1_0 = (Assignment)cGroup_1_1.eContents().get(0);
		private final RuleCall cImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_0_0 = (RuleCall)cImportSpecifiersAssignment_1_1_0.eContents().get(0);
		private final Group cGroup_1_1_1 = (Group)cGroup_1_1.eContents().get(1);
		private final Keyword cCommaKeyword_1_1_1_0 = (Keyword)cGroup_1_1_1.eContents().get(0);
		private final Assignment cImportSpecifiersAssignment_1_1_1_1 = (Assignment)cGroup_1_1_1.eContents().get(1);
		private final RuleCall cImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_1_1_0 = (RuleCall)cImportSpecifiersAssignment_1_1_1_1.eContents().get(0);
		private final Keyword cCommaKeyword_1_1_2 = (Keyword)cGroup_1_1.eContents().get(2);
		private final Keyword cRightCurlyBracketKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		
		//fragment ImportSpecifiersExceptDefault returns ImportDeclaration:
		//	importSpecifiers+=NamespaceImportSpecifier
		//	| '{' (importSpecifiers+=NamedImportSpecifier (',' importSpecifiers+=NamedImportSpecifier)* ','?)? '}';
		@Override public ParserRule getRule() { return rule; }
		
		//importSpecifiers+=NamespaceImportSpecifier | '{' (importSpecifiers+=NamedImportSpecifier (','
		//importSpecifiers+=NamedImportSpecifier)* ','?)? '}'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//importSpecifiers+=NamespaceImportSpecifier
		public Assignment getImportSpecifiersAssignment_0() { return cImportSpecifiersAssignment_0; }
		
		//NamespaceImportSpecifier
		public RuleCall getImportSpecifiersNamespaceImportSpecifierParserRuleCall_0_0() { return cImportSpecifiersNamespaceImportSpecifierParserRuleCall_0_0; }
		
		//'{' (importSpecifiers+=NamedImportSpecifier (',' importSpecifiers+=NamedImportSpecifier)* ','?)? '}'
		public Group getGroup_1() { return cGroup_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1_0() { return cLeftCurlyBracketKeyword_1_0; }
		
		//(importSpecifiers+=NamedImportSpecifier (',' importSpecifiers+=NamedImportSpecifier)* ','?)?
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//importSpecifiers+=NamedImportSpecifier
		public Assignment getImportSpecifiersAssignment_1_1_0() { return cImportSpecifiersAssignment_1_1_0; }
		
		//NamedImportSpecifier
		public RuleCall getImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_0_0() { return cImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_0_0; }
		
		//(',' importSpecifiers+=NamedImportSpecifier)*
		public Group getGroup_1_1_1() { return cGroup_1_1_1; }
		
		//','
		public Keyword getCommaKeyword_1_1_1_0() { return cCommaKeyword_1_1_1_0; }
		
		//importSpecifiers+=NamedImportSpecifier
		public Assignment getImportSpecifiersAssignment_1_1_1_1() { return cImportSpecifiersAssignment_1_1_1_1; }
		
		//NamedImportSpecifier
		public RuleCall getImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_1_1_0() { return cImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_1_1_0; }
		
		//','?
		public Keyword getCommaKeyword_1_1_2() { return cCommaKeyword_1_1_2; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_1_2() { return cRightCurlyBracketKeyword_1_2; }
	}
	public class NamedImportSpecifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.NamedImportSpecifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cImportedElementAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final CrossReference cImportedElementTExportableElementCrossReference_0_0 = (CrossReference)cImportedElementAssignment_0.eContents().get(0);
		private final RuleCall cImportedElementTExportableElementBindingIdentifierParserRuleCall_0_0_1 = (RuleCall)cImportedElementTExportableElementCrossReference_0_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Assignment cImportedElementAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final CrossReference cImportedElementTExportableElementCrossReference_1_0_0 = (CrossReference)cImportedElementAssignment_1_0.eContents().get(0);
		private final RuleCall cImportedElementTExportableElementIdentifierNameParserRuleCall_1_0_0_1 = (RuleCall)cImportedElementTExportableElementCrossReference_1_0_0.eContents().get(1);
		private final Keyword cAsKeyword_1_1 = (Keyword)cGroup_1.eContents().get(1);
		private final Assignment cAliasAssignment_1_2 = (Assignment)cGroup_1.eContents().get(2);
		private final RuleCall cAliasBindingIdentifierParserRuleCall_1_2_0 = (RuleCall)cAliasAssignment_1_2.eContents().get(0);
		
		//NamedImportSpecifier:
		//	importedElement=[types::TExportableElement|BindingIdentifier<Yield=false>] |
		//	importedElement=[types::TExportableElement|IdentifierName] 'as' alias=BindingIdentifier<Yield=false>;
		@Override public ParserRule getRule() { return rule; }
		
		//importedElement=[types::TExportableElement|BindingIdentifier<Yield=false>] |
		//importedElement=[types::TExportableElement|IdentifierName] 'as' alias=BindingIdentifier<Yield=false>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//importedElement=[types::TExportableElement|BindingIdentifier<Yield=false>]
		public Assignment getImportedElementAssignment_0() { return cImportedElementAssignment_0; }
		
		//[types::TExportableElement|BindingIdentifier<Yield=false>]
		public CrossReference getImportedElementTExportableElementCrossReference_0_0() { return cImportedElementTExportableElementCrossReference_0_0; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getImportedElementTExportableElementBindingIdentifierParserRuleCall_0_0_1() { return cImportedElementTExportableElementBindingIdentifierParserRuleCall_0_0_1; }
		
		//importedElement=[types::TExportableElement|IdentifierName] 'as' alias=BindingIdentifier<Yield=false>
		public Group getGroup_1() { return cGroup_1; }
		
		//importedElement=[types::TExportableElement|IdentifierName]
		public Assignment getImportedElementAssignment_1_0() { return cImportedElementAssignment_1_0; }
		
		//[types::TExportableElement|IdentifierName]
		public CrossReference getImportedElementTExportableElementCrossReference_1_0_0() { return cImportedElementTExportableElementCrossReference_1_0_0; }
		
		//IdentifierName
		public RuleCall getImportedElementTExportableElementIdentifierNameParserRuleCall_1_0_0_1() { return cImportedElementTExportableElementIdentifierNameParserRuleCall_1_0_0_1; }
		
		//'as'
		public Keyword getAsKeyword_1_1() { return cAsKeyword_1_1; }
		
		//alias=BindingIdentifier<Yield=false>
		public Assignment getAliasAssignment_1_2() { return cAliasAssignment_1_2; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getAliasBindingIdentifierParserRuleCall_1_2_0() { return cAliasBindingIdentifierParserRuleCall_1_2_0; }
	}
	public class DefaultImportSpecifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.DefaultImportSpecifier");
		private final Assignment cImportedElementAssignment = (Assignment)rule.eContents().get(1);
		private final CrossReference cImportedElementTExportableElementCrossReference_0 = (CrossReference)cImportedElementAssignment.eContents().get(0);
		private final RuleCall cImportedElementTExportableElementBindingIdentifierParserRuleCall_0_1 = (RuleCall)cImportedElementTExportableElementCrossReference_0.eContents().get(1);
		
		//DefaultImportSpecifier:
		//	importedElement=[types::TExportableElement|BindingIdentifier<Yield=false>];
		@Override public ParserRule getRule() { return rule; }
		
		//importedElement=[types::TExportableElement|BindingIdentifier<Yield=false>]
		public Assignment getImportedElementAssignment() { return cImportedElementAssignment; }
		
		//[types::TExportableElement|BindingIdentifier<Yield=false>]
		public CrossReference getImportedElementTExportableElementCrossReference_0() { return cImportedElementTExportableElementCrossReference_0; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getImportedElementTExportableElementBindingIdentifierParserRuleCall_0_1() { return cImportedElementTExportableElementBindingIdentifierParserRuleCall_0_1; }
	}
	public class NamespaceImportSpecifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.NamespaceImportSpecifier");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cNamespaceImportSpecifierAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cAsteriskKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cAsKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cAliasAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cAliasBindingIdentifierParserRuleCall_3_0 = (RuleCall)cAliasAssignment_3.eContents().get(0);
		private final Assignment cDeclaredDynamicAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final Keyword cDeclaredDynamicPlusSignKeyword_4_0 = (Keyword)cDeclaredDynamicAssignment_4.eContents().get(0);
		
		//NamespaceImportSpecifier:
		//	{NamespaceImportSpecifier} '*' 'as' alias=BindingIdentifier<false> declaredDynamic?='+'?;
		@Override public ParserRule getRule() { return rule; }
		
		//{NamespaceImportSpecifier} '*' 'as' alias=BindingIdentifier<false> declaredDynamic?='+'?
		public Group getGroup() { return cGroup; }
		
		//{NamespaceImportSpecifier}
		public Action getNamespaceImportSpecifierAction_0() { return cNamespaceImportSpecifierAction_0; }
		
		//'*'
		public Keyword getAsteriskKeyword_1() { return cAsteriskKeyword_1; }
		
		//'as'
		public Keyword getAsKeyword_2() { return cAsKeyword_2; }
		
		//alias=BindingIdentifier<false>
		public Assignment getAliasAssignment_3() { return cAliasAssignment_3; }
		
		//BindingIdentifier<false>
		public RuleCall getAliasBindingIdentifierParserRuleCall_3_0() { return cAliasBindingIdentifierParserRuleCall_3_0; }
		
		//declaredDynamic?='+'?
		public Assignment getDeclaredDynamicAssignment_4() { return cDeclaredDynamicAssignment_4; }
		
		//'+'
		public Keyword getDeclaredDynamicPlusSignKeyword_4_0() { return cDeclaredDynamicPlusSignKeyword_4_0; }
	}
	public class ModuleSpecifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ModuleSpecifier");
		private final RuleCall cSTRINGTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		//ModuleSpecifier:
		//	STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//STRING
		public RuleCall getSTRINGTerminalRuleCall() { return cSTRINGTerminalRuleCall; }
	}
	public class FunctionDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.FunctionDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cFunctionDeclarationAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_0_0_1 = (Assignment)cGroup_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_0_0_1.eContents().get(0);
		private final RuleCall cAsyncNoTrailingLineBreakParserRuleCall_0_0_2 = (RuleCall)cGroup_0_0.eContents().get(2);
		private final RuleCall cFunctionImplParserRuleCall_0_0_3 = (RuleCall)cGroup_0_0.eContents().get(3);
		private final RuleCall cSemiParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		///*
		// * A function declaration without annotations. The annotated variant is factored into
		// * an own production AnnotatedFunctionDeclaration to avoid the infinite lookahead
		// * of the annotation list
		// */ FunctionDeclaration <Yield>:
		//	=> ({FunctionDeclaration} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
		//	-> FunctionImpl <Yield,Yield,Expression=false>) => Semi?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({FunctionDeclaration} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak -> FunctionImpl
		//<Yield,Yield,Expression=false>) => Semi?
		public Group getGroup() { return cGroup; }
		
		//=> ({FunctionDeclaration} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak -> FunctionImpl
		//<Yield,Yield,Expression=false>)
		public Group getGroup_0() { return cGroup_0; }
		
		//{FunctionDeclaration} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak -> FunctionImpl
		//<Yield,Yield,Expression=false>
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{FunctionDeclaration}
		public Action getFunctionDeclarationAction_0_0_0() { return cFunctionDeclarationAction_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_0_0_1() { return cDeclaredModifiersAssignment_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0; }
		
		//AsyncNoTrailingLineBreak
		public RuleCall getAsyncNoTrailingLineBreakParserRuleCall_0_0_2() { return cAsyncNoTrailingLineBreakParserRuleCall_0_0_2; }
		
		//-> FunctionImpl <Yield,Yield,Expression=false>
		public RuleCall getFunctionImplParserRuleCall_0_0_3() { return cFunctionImplParserRuleCall_0_0_3; }
		
		//=> Semi?
		public RuleCall getSemiParserRuleCall_1() { return cSemiParserRuleCall_1; }
	}
	public class AsyncNoTrailingLineBreakElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AsyncNoTrailingLineBreak");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Assignment cDeclaredAsyncAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cDeclaredAsyncAsyncKeyword_0_0 = (Keyword)cDeclaredAsyncAssignment_0.eContents().get(0);
		private final RuleCall cNoLineTerminatorParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//fragment AsyncNoTrailingLineBreak *:
		//	(declaredAsync?='async' NoLineTerminator)?;
		@Override public ParserRule getRule() { return rule; }
		
		//(declaredAsync?='async' NoLineTerminator)?
		public Group getGroup() { return cGroup; }
		
		//declaredAsync?='async'
		public Assignment getDeclaredAsyncAssignment_0() { return cDeclaredAsyncAssignment_0; }
		
		//'async'
		public Keyword getDeclaredAsyncAsyncKeyword_0_0() { return cDeclaredAsyncAsyncKeyword_0_0; }
		
		//NoLineTerminator
		public RuleCall getNoLineTerminatorParserRuleCall_1() { return cNoLineTerminatorParserRuleCall_1; }
	}
	public class FunctionImplElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.FunctionImpl");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cFunctionKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Assignment cGeneratorAssignment_1_0_0 = (Assignment)cGroup_1_0.eContents().get(0);
		private final Keyword cGeneratorAsteriskKeyword_1_0_0_0 = (Keyword)cGeneratorAssignment_1_0_0.eContents().get(0);
		private final RuleCall cFunctionHeaderParserRuleCall_1_0_1 = (RuleCall)cGroup_1_0.eContents().get(1);
		private final RuleCall cFunctionBodyParserRuleCall_1_0_2 = (RuleCall)cGroup_1_0.eContents().get(2);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final RuleCall cFunctionHeaderParserRuleCall_1_1_0 = (RuleCall)cGroup_1_1.eContents().get(0);
		private final RuleCall cFunctionBodyParserRuleCall_1_1_1 = (RuleCall)cGroup_1_1.eContents().get(1);
		
		//fragment FunctionImpl <Yield, YieldIfGenerator, Expression> *:
		//	'function' (generator?='*' FunctionHeader<YieldIfGenerator,Generator=true> FunctionBody<Yield=true,Expression> |
		//	FunctionHeader<Yield,Generator=false> FunctionBody<Yield=false,Expression>);
		@Override public ParserRule getRule() { return rule; }
		
		//'function' (generator?='*' FunctionHeader<YieldIfGenerator,Generator=true> FunctionBody<Yield=true,Expression> |
		//FunctionHeader<Yield,Generator=false> FunctionBody<Yield=false,Expression>)
		public Group getGroup() { return cGroup; }
		
		//'function'
		public Keyword getFunctionKeyword_0() { return cFunctionKeyword_0; }
		
		//generator?='*' FunctionHeader<YieldIfGenerator,Generator=true> FunctionBody<Yield=true,Expression> |
		//FunctionHeader<Yield,Generator=false> FunctionBody<Yield=false,Expression>
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//generator?='*' FunctionHeader<YieldIfGenerator,Generator=true> FunctionBody<Yield=true,Expression>
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//generator?='*'
		public Assignment getGeneratorAssignment_1_0_0() { return cGeneratorAssignment_1_0_0; }
		
		//'*'
		public Keyword getGeneratorAsteriskKeyword_1_0_0_0() { return cGeneratorAsteriskKeyword_1_0_0_0; }
		
		//FunctionHeader<YieldIfGenerator,Generator=true>
		public RuleCall getFunctionHeaderParserRuleCall_1_0_1() { return cFunctionHeaderParserRuleCall_1_0_1; }
		
		//FunctionBody<Yield=true,Expression>
		public RuleCall getFunctionBodyParserRuleCall_1_0_2() { return cFunctionBodyParserRuleCall_1_0_2; }
		
		//FunctionHeader<Yield,Generator=false> FunctionBody<Yield=false,Expression>
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//FunctionHeader<Yield,Generator=false>
		public RuleCall getFunctionHeaderParserRuleCall_1_1_0() { return cFunctionHeaderParserRuleCall_1_1_0; }
		
		//FunctionBody<Yield=false,Expression>
		public RuleCall getFunctionBodyParserRuleCall_1_1_1() { return cFunctionBodyParserRuleCall_1_1_1; }
	}
	public class FunctionHeaderElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.FunctionHeader");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final RuleCall cVersionDeclarationParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final RuleCall cStrictFormalParametersParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		
		//fragment FunctionHeader <Yield, Generator> *:
		//	TypeVariables?
		//	name=BindingIdentifier<Yield>?
		//	VersionDeclaration?
		//	StrictFormalParameters<Yield=Generator> -> ColonSepReturnTypeRef?;
		@Override public ParserRule getRule() { return rule; }
		
		//TypeVariables? name=BindingIdentifier<Yield>? VersionDeclaration? StrictFormalParameters<Yield=Generator> ->
		//ColonSepReturnTypeRef?
		public Group getGroup() { return cGroup; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_0() { return cTypeVariablesParserRuleCall_0; }
		
		//name=BindingIdentifier<Yield>?
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_0() { return cNameBindingIdentifierParserRuleCall_1_0; }
		
		//VersionDeclaration?
		public RuleCall getVersionDeclarationParserRuleCall_2() { return cVersionDeclarationParserRuleCall_2; }
		
		//StrictFormalParameters<Yield=Generator>
		public RuleCall getStrictFormalParametersParserRuleCall_3() { return cStrictFormalParametersParserRuleCall_3; }
		
		//-> ColonSepReturnTypeRef?
		public RuleCall getColonSepReturnTypeRefParserRuleCall_4() { return cColonSepReturnTypeRefParserRuleCall_4; }
	}
	public class FunctionBodyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.FunctionBody");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(0);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Assignment cBodyAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final RuleCall cBodyBlockParserRuleCall_0_0_0 = (RuleCall)cBodyAssignment_0_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Assignment cBodyAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cBodyBlockParserRuleCall_1_0_0 = (RuleCall)cBodyAssignment_1_0.eContents().get(0);
		
		//fragment FunctionBody <Yield, Expression> *:
		//	<Expression> body=Block<Yield> | <!Expression> body=Block<Yield>?;
		@Override public ParserRule getRule() { return rule; }
		
		//<Expression> body=Block<Yield> | <!Expression> body=Block<Yield>?
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//<Expression> body=Block<Yield>
		public Group getGroup_0() { return cGroup_0; }
		
		//body=Block<Yield>
		public Assignment getBodyAssignment_0_0() { return cBodyAssignment_0_0; }
		
		//Block<Yield>
		public RuleCall getBodyBlockParserRuleCall_0_0_0() { return cBodyBlockParserRuleCall_0_0_0; }
		
		//<!Expression> body=Block<Yield>?
		public Group getGroup_1() { return cGroup_1; }
		
		//body=Block<Yield>?
		public Assignment getBodyAssignment_1_0() { return cBodyAssignment_1_0; }
		
		//Block<Yield>
		public RuleCall getBodyBlockParserRuleCall_1_0_0() { return cBodyBlockParserRuleCall_1_0_0; }
	}
	public class AnnotatedFunctionDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotatedFunctionDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cAnnotationListAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cAnnotationListAnnotationListParserRuleCall_0_0 = (RuleCall)cAnnotationListAssignment_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_0 = (RuleCall)cDeclaredModifiersAssignment_1.eContents().get(0);
		private final RuleCall cAsyncNoTrailingLineBreakParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final RuleCall cFunctionImplParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		///*
		// * Used only within statement blocks, the annotated functions on the root level
		// * are handled by the rule AnnotatedScriptElement and its inlined content of FunctionDeclaration
		// */ AnnotatedFunctionDeclaration <Yield, Default FunctionDeclaration:
		//	annotationList=AnnotationList
		//	declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
		//	FunctionImpl<Yield,Yield,Expression=false>;
		@Override public ParserRule getRule() { return rule; }
		
		//annotationList=AnnotationList declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
		//FunctionImpl<Yield,Yield,Expression=false>
		public Group getGroup() { return cGroup; }
		
		//annotationList=AnnotationList
		public Assignment getAnnotationListAssignment_0() { return cAnnotationListAssignment_0; }
		
		//AnnotationList
		public RuleCall getAnnotationListAnnotationListParserRuleCall_0_0() { return cAnnotationListAnnotationListParserRuleCall_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1() { return cDeclaredModifiersAssignment_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_0; }
		
		//AsyncNoTrailingLineBreak
		public RuleCall getAsyncNoTrailingLineBreakParserRuleCall_2() { return cAsyncNoTrailingLineBreakParserRuleCall_2; }
		
		//FunctionImpl<Yield,Yield,Expression=false>
		public RuleCall getFunctionImplParserRuleCall_3() { return cFunctionImplParserRuleCall_3; }
	}
	public class FunctionExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.FunctionExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cFunctionExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final RuleCall cFunctionImplParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//FunctionExpression:
		//	{FunctionExpression} FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>;
		@Override public ParserRule getRule() { return rule; }
		
		//{FunctionExpression} FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>
		public Group getGroup() { return cGroup; }
		
		//{FunctionExpression}
		public Action getFunctionExpressionAction_0() { return cFunctionExpressionAction_0; }
		
		//FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>
		public RuleCall getFunctionImplParserRuleCall_1() { return cFunctionImplParserRuleCall_1; }
	}
	public class AsyncFunctionExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AsyncFunctionExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Assignment cDeclaredAsyncAssignment_0_0_0 = (Assignment)cGroup_0_0.eContents().get(0);
		private final Keyword cDeclaredAsyncAsyncKeyword_0_0_0_0 = (Keyword)cDeclaredAsyncAssignment_0_0_0.eContents().get(0);
		private final RuleCall cNoLineTerminatorParserRuleCall_0_0_1 = (RuleCall)cGroup_0_0.eContents().get(1);
		private final Keyword cFunctionKeyword_0_0_2 = (Keyword)cGroup_0_0.eContents().get(2);
		private final RuleCall cFunctionHeaderParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final RuleCall cFunctionBodyParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		///**
		// * We cannot use fragments here since we have to combine the terminals into a syntactic predicate.
		// */ AsyncFunctionExpression FunctionExpression:
		//	=> (declaredAsync?='async' NoLineTerminator 'function') FunctionHeader<Yield=false,Generator=false>
		//	FunctionBody<Yield=false,Expression=true>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> (declaredAsync?='async' NoLineTerminator 'function') FunctionHeader<Yield=false,Generator=false>
		//FunctionBody<Yield=false,Expression=true>
		public Group getGroup() { return cGroup; }
		
		//=> (declaredAsync?='async' NoLineTerminator 'function')
		public Group getGroup_0() { return cGroup_0; }
		
		//declaredAsync?='async' NoLineTerminator 'function'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//declaredAsync?='async'
		public Assignment getDeclaredAsyncAssignment_0_0_0() { return cDeclaredAsyncAssignment_0_0_0; }
		
		//'async'
		public Keyword getDeclaredAsyncAsyncKeyword_0_0_0_0() { return cDeclaredAsyncAsyncKeyword_0_0_0_0; }
		
		//NoLineTerminator
		public RuleCall getNoLineTerminatorParserRuleCall_0_0_1() { return cNoLineTerminatorParserRuleCall_0_0_1; }
		
		//'function'
		public Keyword getFunctionKeyword_0_0_2() { return cFunctionKeyword_0_0_2; }
		
		//FunctionHeader<Yield=false,Generator=false>
		public RuleCall getFunctionHeaderParserRuleCall_1() { return cFunctionHeaderParserRuleCall_1; }
		
		//FunctionBody<Yield=false,Expression=true>
		public RuleCall getFunctionBodyParserRuleCall_2() { return cFunctionBodyParserRuleCall_2; }
	}
	public class ArrowExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ArrowExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Alternatives cAlternatives_0_0_0 = (Alternatives)cGroup_0_0.eContents().get(0);
		private final Group cGroup_0_0_0_0 = (Group)cAlternatives_0_0_0.eContents().get(0);
		private final RuleCall cStrictFormalParametersParserRuleCall_0_0_0_0_0 = (RuleCall)cGroup_0_0_0_0.eContents().get(0);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_0_0_0_0_1 = (RuleCall)cGroup_0_0_0_0.eContents().get(1);
		private final Group cGroup_0_0_0_1 = (Group)cAlternatives_0_0_0.eContents().get(1);
		private final Group cGroup_0_0_0_1_0 = (Group)cGroup_0_0_0_1.eContents().get(0);
		private final Group cGroup_0_0_0_1_0_0 = (Group)cGroup_0_0_0_1_0.eContents().get(0);
		private final Assignment cDeclaredAsyncAssignment_0_0_0_1_0_0_0 = (Assignment)cGroup_0_0_0_1_0_0.eContents().get(0);
		private final Keyword cDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0 = (Keyword)cDeclaredAsyncAssignment_0_0_0_1_0_0_0.eContents().get(0);
		private final RuleCall cNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1 = (RuleCall)cGroup_0_0_0_1_0_0.eContents().get(1);
		private final RuleCall cStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2 = (RuleCall)cGroup_0_0_0_1_0_0.eContents().get(2);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_0_0_0_1_1 = (RuleCall)cGroup_0_0_0_1.eContents().get(1);
		private final Assignment cFparsAssignment_0_0_0_2 = (Assignment)cAlternatives_0_0_0.eContents().get(2);
		private final RuleCall cFparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0 = (RuleCall)cFparsAssignment_0_0_0_2.eContents().get(0);
		private final Keyword cEqualsSignGreaterThanSignKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Assignment cHasBracesAroundBodyAssignment_1_0_0 = (Assignment)cGroup_1_0.eContents().get(0);
		private final Keyword cHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0 = (Keyword)cHasBracesAroundBodyAssignment_1_0_0.eContents().get(0);
		private final Assignment cBodyAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cBodyBlockMinusBracesParserRuleCall_1_0_1_0 = (RuleCall)cBodyAssignment_1_0_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_1_0_2 = (Keyword)cGroup_1_0.eContents().get(2);
		private final Assignment cBodyAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final RuleCall cBodyExpressionDisguisedAsBlockParserRuleCall_1_1_0 = (RuleCall)cBodyAssignment_1_1.eContents().get(0);
		
		//ArrowExpression <In, Yield ArrowFunction:
		//	=> ((StrictFormalParameters<Yield> ColonSepReturnTypeRef?
		//	| => (declaredAsync?='async' NoLineTerminator -> StrictFormalParameters <Yield>) ColonSepReturnTypeRef?
		//	| fpars+=BindingIdentifierAsFormalParameter<Yield>)
		//	/* no line terminator here, guaranteed implicitly */ '=>') (-> hasBracesAroundBody?='{' body=BlockMinusBraces<Yield>
		//	'}' | body=ExpressionDisguisedAsBlock<In>);
		@Override public ParserRule getRule() { return rule; }
		
		//=> ((StrictFormalParameters<Yield> ColonSepReturnTypeRef? | => (declaredAsync?='async' NoLineTerminator ->
		//StrictFormalParameters <Yield>) ColonSepReturnTypeRef? | fpars+=BindingIdentifierAsFormalParameter<Yield>)
		///* no line terminator here, guaranteed implicitly */ '=>') (-> hasBracesAroundBody?='{' body=BlockMinusBraces<Yield>
		//'}' | body=ExpressionDisguisedAsBlock<In>)
		public Group getGroup() { return cGroup; }
		
		//=> ((StrictFormalParameters<Yield> ColonSepReturnTypeRef? | => (declaredAsync?='async' NoLineTerminator ->
		//StrictFormalParameters <Yield>) ColonSepReturnTypeRef? | fpars+=BindingIdentifierAsFormalParameter<Yield>)
		///* no line terminator here, guaranteed implicitly */ '=>')
		public Group getGroup_0() { return cGroup_0; }
		
		//(StrictFormalParameters<Yield> ColonSepReturnTypeRef? | => (declaredAsync?='async' NoLineTerminator ->
		//StrictFormalParameters <Yield>) ColonSepReturnTypeRef? | fpars+=BindingIdentifierAsFormalParameter<Yield>)
		///* no line terminator here, guaranteed implicitly */ '=>'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//StrictFormalParameters<Yield> ColonSepReturnTypeRef? | => (declaredAsync?='async' NoLineTerminator ->
		//StrictFormalParameters <Yield>) ColonSepReturnTypeRef? | fpars+=BindingIdentifierAsFormalParameter<Yield>
		public Alternatives getAlternatives_0_0_0() { return cAlternatives_0_0_0; }
		
		//// we cannot use fragments here since we have to combine the terminals into a syntactic predicate
		//// also, we have to use explicit alternatives instead of making async optional due to a generation bug
		//StrictFormalParameters<Yield> ColonSepReturnTypeRef?
		public Group getGroup_0_0_0_0() { return cGroup_0_0_0_0; }
		
		//// we cannot use fragments here since we have to combine the terminals into a syntactic predicate
		//// also, we have to use explicit alternatives instead of making async optional due to a generation bug
		//StrictFormalParameters<Yield>
		public RuleCall getStrictFormalParametersParserRuleCall_0_0_0_0_0() { return cStrictFormalParametersParserRuleCall_0_0_0_0_0; }
		
		//ColonSepReturnTypeRef?
		public RuleCall getColonSepReturnTypeRefParserRuleCall_0_0_0_0_1() { return cColonSepReturnTypeRefParserRuleCall_0_0_0_0_1; }
		
		//=> (declaredAsync?='async' NoLineTerminator -> StrictFormalParameters <Yield>) ColonSepReturnTypeRef?
		public Group getGroup_0_0_0_1() { return cGroup_0_0_0_1; }
		
		//=> (declaredAsync?='async' NoLineTerminator -> StrictFormalParameters <Yield>)
		public Group getGroup_0_0_0_1_0() { return cGroup_0_0_0_1_0; }
		
		//declaredAsync?='async' NoLineTerminator -> StrictFormalParameters <Yield>
		public Group getGroup_0_0_0_1_0_0() { return cGroup_0_0_0_1_0_0; }
		
		//declaredAsync?='async'
		public Assignment getDeclaredAsyncAssignment_0_0_0_1_0_0_0() { return cDeclaredAsyncAssignment_0_0_0_1_0_0_0; }
		
		//'async'
		public Keyword getDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0() { return cDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0; }
		
		//NoLineTerminator
		public RuleCall getNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1() { return cNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1; }
		
		//-> StrictFormalParameters <Yield>
		public RuleCall getStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2() { return cStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2; }
		
		//ColonSepReturnTypeRef?
		public RuleCall getColonSepReturnTypeRefParserRuleCall_0_0_0_1_1() { return cColonSepReturnTypeRefParserRuleCall_0_0_0_1_1; }
		
		//fpars+=BindingIdentifierAsFormalParameter<Yield>
		public Assignment getFparsAssignment_0_0_0_2() { return cFparsAssignment_0_0_0_2; }
		
		//BindingIdentifierAsFormalParameter<Yield>
		public RuleCall getFparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0() { return cFparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0; }
		
		///* no line terminator here, guaranteed implicitly */ '=>'
		public Keyword getEqualsSignGreaterThanSignKeyword_0_0_1() { return cEqualsSignGreaterThanSignKeyword_0_0_1; }
		
		//-> hasBracesAroundBody?='{' body=BlockMinusBraces<Yield> '}' | body=ExpressionDisguisedAsBlock<In>
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//-> hasBracesAroundBody?='{' body=BlockMinusBraces<Yield> '}'
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//-> hasBracesAroundBody?='{'
		public Assignment getHasBracesAroundBodyAssignment_1_0_0() { return cHasBracesAroundBodyAssignment_1_0_0; }
		
		//'{'
		public Keyword getHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0() { return cHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0; }
		
		//body=BlockMinusBraces<Yield>
		public Assignment getBodyAssignment_1_0_1() { return cBodyAssignment_1_0_1; }
		
		//BlockMinusBraces<Yield>
		public RuleCall getBodyBlockMinusBracesParserRuleCall_1_0_1_0() { return cBodyBlockMinusBracesParserRuleCall_1_0_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_1_0_2() { return cRightCurlyBracketKeyword_1_0_2; }
		
		//body=ExpressionDisguisedAsBlock<In>
		public Assignment getBodyAssignment_1_1() { return cBodyAssignment_1_1; }
		
		//ExpressionDisguisedAsBlock<In>
		public RuleCall getBodyExpressionDisguisedAsBlockParserRuleCall_1_1_0() { return cBodyExpressionDisguisedAsBlockParserRuleCall_1_1_0; }
	}
	public class StrictFormalParametersElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.StrictFormalParameters");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Assignment cFparsAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cFparsFormalParameterParserRuleCall_1_0_0 = (RuleCall)cFparsAssignment_1_0.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Keyword cCommaKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cFparsAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cFparsFormalParameterParserRuleCall_1_1_1_0 = (RuleCall)cFparsAssignment_1_1_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//fragment StrictFormalParameters <Yield> *:
		//	'(' (fpars+=FormalParameter<Yield> (',' fpars+=FormalParameter<Yield>)*)? ')';
		@Override public ParserRule getRule() { return rule; }
		
		//'(' (fpars+=FormalParameter<Yield> (',' fpars+=FormalParameter<Yield>)*)? ')'
		public Group getGroup() { return cGroup; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_0() { return cLeftParenthesisKeyword_0; }
		
		//(fpars+=FormalParameter<Yield> (',' fpars+=FormalParameter<Yield>)*)?
		public Group getGroup_1() { return cGroup_1; }
		
		//fpars+=FormalParameter<Yield>
		public Assignment getFparsAssignment_1_0() { return cFparsAssignment_1_0; }
		
		//FormalParameter<Yield>
		public RuleCall getFparsFormalParameterParserRuleCall_1_0_0() { return cFparsFormalParameterParserRuleCall_1_0_0; }
		
		//(',' fpars+=FormalParameter<Yield>)*
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//','
		public Keyword getCommaKeyword_1_1_0() { return cCommaKeyword_1_1_0; }
		
		//fpars+=FormalParameter<Yield>
		public Assignment getFparsAssignment_1_1_1() { return cFparsAssignment_1_1_1; }
		
		//FormalParameter<Yield>
		public RuleCall getFparsFormalParameterParserRuleCall_1_1_1_0() { return cFparsFormalParameterParserRuleCall_1_1_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2() { return cRightParenthesisKeyword_2; }
	}
	public class BindingIdentifierAsFormalParameterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BindingIdentifierAsFormalParameter");
		private final Assignment cNameAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cNameBindingIdentifierParserRuleCall_0 = (RuleCall)cNameAssignment.eContents().get(0);
		
		//BindingIdentifierAsFormalParameter <Yield FormalParameter:
		//	name=BindingIdentifier<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment() { return cNameAssignment; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_0() { return cNameBindingIdentifierParserRuleCall_0; }
	}
	public class BlockMinusBracesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BlockMinusBraces");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cBlockAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cStatementsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cStatementsStatementParserRuleCall_1_0 = (RuleCall)cStatementsAssignment_1.eContents().get(0);
		
		//BlockMinusBraces <Yield Block:
		//	{Block} statements+=Statement<Yield>*;
		@Override public ParserRule getRule() { return rule; }
		
		//{Block} statements+=Statement<Yield>*
		public Group getGroup() { return cGroup; }
		
		//{Block}
		public Action getBlockAction_0() { return cBlockAction_0; }
		
		//statements+=Statement<Yield>*
		public Assignment getStatementsAssignment_1() { return cStatementsAssignment_1; }
		
		//Statement<Yield>
		public RuleCall getStatementsStatementParserRuleCall_1_0() { return cStatementsStatementParserRuleCall_1_0; }
	}
	public class ExpressionDisguisedAsBlockElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExpressionDisguisedAsBlock");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cBlockAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cStatementsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cStatementsAssignmentExpressionStatementParserRuleCall_1_0 = (RuleCall)cStatementsAssignment_1.eContents().get(0);
		
		//ExpressionDisguisedAsBlock <In Block:
		//	{Block} statements+=AssignmentExpressionStatement<In>;
		@Override public ParserRule getRule() { return rule; }
		
		//{Block} statements+=AssignmentExpressionStatement<In>
		public Group getGroup() { return cGroup; }
		
		//{Block}
		public Action getBlockAction_0() { return cBlockAction_0; }
		
		//statements+=AssignmentExpressionStatement<In>
		public Assignment getStatementsAssignment_1() { return cStatementsAssignment_1; }
		
		//AssignmentExpressionStatement<In>
		public RuleCall getStatementsAssignmentExpressionStatementParserRuleCall_1_0() { return cStatementsAssignmentExpressionStatementParserRuleCall_1_0; }
	}
	public class AssignmentExpressionStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AssignmentExpressionStatement");
		private final Assignment cExpressionAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_0 = (RuleCall)cExpressionAssignment.eContents().get(0);
		
		//AssignmentExpressionStatement <In ExpressionStatement:
		//	expression=AssignmentExpression<In,Yield=false>;
		@Override public ParserRule getRule() { return rule; }
		
		//expression=AssignmentExpression<In,Yield=false>
		public Assignment getExpressionAssignment() { return cExpressionAssignment; }
		
		//AssignmentExpression<In,Yield=false>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_0() { return cExpressionAssignmentExpressionParserRuleCall_0; }
	}
	public class AnnotatedExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotatedExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cExpressionAnnotationListParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Action cN4ClassExpressionAnnotationListAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Keyword cClassKeyword_1_0_1 = (Keyword)cGroup_1_0.eContents().get(1);
		private final Assignment cNameAssignment_1_0_2 = (Assignment)cGroup_1_0.eContents().get(2);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_0_2_0 = (RuleCall)cNameAssignment_1_0_2.eContents().get(0);
		private final RuleCall cClassExtendsImplementsParserRuleCall_1_0_3 = (RuleCall)cGroup_1_0.eContents().get(3);
		private final RuleCall cMembersParserRuleCall_1_0_4 = (RuleCall)cGroup_1_0.eContents().get(4);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Action cFunctionExpressionAnnotationListAction_1_1_0 = (Action)cGroup_1_1.eContents().get(0);
		private final RuleCall cAsyncNoTrailingLineBreakParserRuleCall_1_1_1 = (RuleCall)cGroup_1_1.eContents().get(1);
		private final RuleCall cFunctionImplParserRuleCall_1_1_2 = (RuleCall)cGroup_1_1.eContents().get(2);
		
		///**
		// * Left factored, annotated expression.
		// *
		// * Pretty much inlined versions of function expression and class expression.
		// *
		// * The GrammarLinter ensures that the inlined content mirrors the content of the real declarations.
		// */ AnnotatedExpression <Yield Expression:
		//	ExpressionAnnotationList ({N4ClassExpression.annotationList=current}
		//	'class' name=BindingIdentifier<Yield>?
		//	ClassExtendsImplements<Yield>?
		//	Members<Yield> | {FunctionExpression.annotationList=current} AsyncNoTrailingLineBreak
		//	FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>);
		@Override public ParserRule getRule() { return rule; }
		
		//ExpressionAnnotationList ({N4ClassExpression.annotationList=current} 'class' name=BindingIdentifier<Yield>?
		//ClassExtendsImplements<Yield>? Members<Yield> | {FunctionExpression.annotationList=current} AsyncNoTrailingLineBreak
		//FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>)
		public Group getGroup() { return cGroup; }
		
		//ExpressionAnnotationList
		public RuleCall getExpressionAnnotationListParserRuleCall_0() { return cExpressionAnnotationListParserRuleCall_0; }
		
		//{N4ClassExpression.annotationList=current} 'class' name=BindingIdentifier<Yield>? ClassExtendsImplements<Yield>?
		//Members<Yield> | {FunctionExpression.annotationList=current} AsyncNoTrailingLineBreak
		//FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//{N4ClassExpression.annotationList=current} 'class' name=BindingIdentifier<Yield>? ClassExtendsImplements<Yield>?
		//Members<Yield>
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{N4ClassExpression.annotationList=current}
		public Action getN4ClassExpressionAnnotationListAction_1_0_0() { return cN4ClassExpressionAnnotationListAction_1_0_0; }
		
		//'class'
		public Keyword getClassKeyword_1_0_1() { return cClassKeyword_1_0_1; }
		
		//name=BindingIdentifier<Yield>?
		public Assignment getNameAssignment_1_0_2() { return cNameAssignment_1_0_2; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_0_2_0() { return cNameBindingIdentifierParserRuleCall_1_0_2_0; }
		
		//ClassExtendsImplements<Yield>?
		public RuleCall getClassExtendsImplementsParserRuleCall_1_0_3() { return cClassExtendsImplementsParserRuleCall_1_0_3; }
		
		//Members<Yield>
		public RuleCall getMembersParserRuleCall_1_0_4() { return cMembersParserRuleCall_1_0_4; }
		
		//{FunctionExpression.annotationList=current} AsyncNoTrailingLineBreak
		//FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//{FunctionExpression.annotationList=current}
		public Action getFunctionExpressionAnnotationListAction_1_1_0() { return cFunctionExpressionAnnotationListAction_1_1_0; }
		
		//AsyncNoTrailingLineBreak
		public RuleCall getAsyncNoTrailingLineBreakParserRuleCall_1_1_1() { return cAsyncNoTrailingLineBreakParserRuleCall_1_1_1; }
		
		//FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>
		public RuleCall getFunctionImplParserRuleCall_1_1_2() { return cFunctionImplParserRuleCall_1_1_2; }
	}
	public class TypeVariableElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TypeVariable");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Assignment cDeclaredCovariantAssignment_0_0 = (Assignment)cAlternatives_0.eContents().get(0);
		private final Keyword cDeclaredCovariantOutKeyword_0_0_0 = (Keyword)cDeclaredCovariantAssignment_0_0.eContents().get(0);
		private final Assignment cDeclaredContravariantAssignment_0_1 = (Assignment)cAlternatives_0.eContents().get(1);
		private final Keyword cDeclaredContravariantInKeyword_0_1_0 = (Keyword)cDeclaredContravariantAssignment_0_1.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIdentifierOrThisParserRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cExtendsKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cDeclaredUpperBoundAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cDeclaredUpperBoundTypeRefParserRuleCall_2_1_0 = (RuleCall)cDeclaredUpperBoundAssignment_2_1.eContents().get(0);
		
		//@Override
		//TypeVariable types::TypeVariable:
		//	(declaredCovariant?='out' | declaredContravariant?='in')?
		//	name=IdentifierOrThis ('extends' declaredUpperBound=TypeRef)?;
		@Override public ParserRule getRule() { return rule; }
		
		//(declaredCovariant?='out' | declaredContravariant?='in')? name=IdentifierOrThis ('extends' declaredUpperBound=TypeRef)?
		public Group getGroup() { return cGroup; }
		
		//(declaredCovariant?='out' | declaredContravariant?='in')?
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//declaredCovariant?='out'
		public Assignment getDeclaredCovariantAssignment_0_0() { return cDeclaredCovariantAssignment_0_0; }
		
		//'out'
		public Keyword getDeclaredCovariantOutKeyword_0_0_0() { return cDeclaredCovariantOutKeyword_0_0_0; }
		
		//declaredContravariant?='in'
		public Assignment getDeclaredContravariantAssignment_0_1() { return cDeclaredContravariantAssignment_0_1; }
		
		//'in'
		public Keyword getDeclaredContravariantInKeyword_0_1_0() { return cDeclaredContravariantInKeyword_0_1_0; }
		
		//name=IdentifierOrThis
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//IdentifierOrThis
		public RuleCall getNameIdentifierOrThisParserRuleCall_1_0() { return cNameIdentifierOrThisParserRuleCall_1_0; }
		
		//('extends' declaredUpperBound=TypeRef)?
		public Group getGroup_2() { return cGroup_2; }
		
		//'extends'
		public Keyword getExtendsKeyword_2_0() { return cExtendsKeyword_2_0; }
		
		//declaredUpperBound=TypeRef
		public Assignment getDeclaredUpperBoundAssignment_2_1() { return cDeclaredUpperBoundAssignment_2_1; }
		
		//TypeRef
		public RuleCall getDeclaredUpperBoundTypeRefParserRuleCall_2_1_0() { return cDeclaredUpperBoundTypeRefParserRuleCall_2_1_0; }
	}
	public class FormalParameterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.FormalParameter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cFormalParameterAction_0 = (Action)cGroup.eContents().get(0);
		private final RuleCall cBindingElementFragmentParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//FormalParameter <Yield>:
		//	{FormalParameter} BindingElementFragment<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//{FormalParameter} BindingElementFragment<Yield>
		public Group getGroup() { return cGroup; }
		
		//{FormalParameter}
		public Action getFormalParameterAction_0() { return cFormalParameterAction_0; }
		
		//BindingElementFragment<Yield>
		public RuleCall getBindingElementFragmentParserRuleCall_1() { return cBindingElementFragmentParserRuleCall_1; }
	}
	public class BindingElementFragmentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BindingElementFragment");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Assignment cBindingPatternAssignment_0_0 = (Assignment)cAlternatives_0.eContents().get(0);
		private final RuleCall cBindingPatternBindingPatternParserRuleCall_0_0_0 = (RuleCall)cBindingPatternAssignment_0_0.eContents().get(0);
		private final Group cGroup_0_1 = (Group)cAlternatives_0.eContents().get(1);
		private final Assignment cAnnotationsAssignment_0_1_0 = (Assignment)cGroup_0_1.eContents().get(0);
		private final RuleCall cAnnotationsAnnotationParserRuleCall_0_1_0_0 = (RuleCall)cAnnotationsAssignment_0_1_0.eContents().get(0);
		private final RuleCall cBogusTypeRefFragmentParserRuleCall_0_1_1 = (RuleCall)cGroup_0_1.eContents().get(1);
		private final Assignment cVariadicAssignment_0_1_2 = (Assignment)cGroup_0_1.eContents().get(2);
		private final Keyword cVariadicFullStopFullStopFullStopKeyword_0_1_2_0 = (Keyword)cVariadicAssignment_0_1_2.eContents().get(0);
		private final Assignment cNameAssignment_0_1_3 = (Assignment)cGroup_0_1.eContents().get(3);
		private final RuleCall cNameBindingIdentifierParserRuleCall_0_1_3_0 = (RuleCall)cNameAssignment_0_1_3.eContents().get(0);
		private final RuleCall cColonSepDeclaredTypeRefParserRuleCall_0_1_4 = (RuleCall)cGroup_0_1.eContents().get(4);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Assignment cHasInitializerAssignmentAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final Keyword cHasInitializerAssignmentEqualsSignKeyword_1_0_0 = (Keyword)cHasInitializerAssignmentAssignment_1_0.eContents().get(0);
		private final Assignment cInitializerAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cInitializerAssignmentExpressionParserRuleCall_1_1_0 = (RuleCall)cInitializerAssignment_1_1.eContents().get(0);
		
		//fragment BindingElementFragment <Yield> *:
		//	(=> bindingPattern=BindingPattern<Yield> | annotations+=Annotation* BogusTypeRefFragment? variadic?='...'?
		//	name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?) (hasInitializerAssignment?='='
		//	initializer=AssignmentExpression<In=true,Yield>?)?;
		@Override public ParserRule getRule() { return rule; }
		
		//(=> bindingPattern=BindingPattern<Yield> | annotations+=Annotation* BogusTypeRefFragment? variadic?='...'?
		//name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?) (hasInitializerAssignment?='='
		//initializer=AssignmentExpression<In=true,Yield>?)?
		public Group getGroup() { return cGroup; }
		
		//=> bindingPattern=BindingPattern<Yield> | annotations+=Annotation* BogusTypeRefFragment? variadic?='...'?
		//name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//=> bindingPattern=BindingPattern<Yield>
		public Assignment getBindingPatternAssignment_0_0() { return cBindingPatternAssignment_0_0; }
		
		//BindingPattern<Yield>
		public RuleCall getBindingPatternBindingPatternParserRuleCall_0_0_0() { return cBindingPatternBindingPatternParserRuleCall_0_0_0; }
		
		//annotations+=Annotation* BogusTypeRefFragment? variadic?='...'? name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?
		public Group getGroup_0_1() { return cGroup_0_1; }
		
		//annotations+=Annotation*
		public Assignment getAnnotationsAssignment_0_1_0() { return cAnnotationsAssignment_0_1_0; }
		
		//Annotation
		public RuleCall getAnnotationsAnnotationParserRuleCall_0_1_0_0() { return cAnnotationsAnnotationParserRuleCall_0_1_0_0; }
		
		//BogusTypeRefFragment?
		public RuleCall getBogusTypeRefFragmentParserRuleCall_0_1_1() { return cBogusTypeRefFragmentParserRuleCall_0_1_1; }
		
		//variadic?='...'?
		public Assignment getVariadicAssignment_0_1_2() { return cVariadicAssignment_0_1_2; }
		
		//'...'
		public Keyword getVariadicFullStopFullStopFullStopKeyword_0_1_2_0() { return cVariadicFullStopFullStopFullStopKeyword_0_1_2_0; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment_0_1_3() { return cNameAssignment_0_1_3; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_0_1_3_0() { return cNameBindingIdentifierParserRuleCall_0_1_3_0; }
		
		//ColonSepDeclaredTypeRef?
		public RuleCall getColonSepDeclaredTypeRefParserRuleCall_0_1_4() { return cColonSepDeclaredTypeRefParserRuleCall_0_1_4; }
		
		//(hasInitializerAssignment?='=' initializer=AssignmentExpression<In=true,Yield>?)?
		public Group getGroup_1() { return cGroup_1; }
		
		//hasInitializerAssignment?='='
		public Assignment getHasInitializerAssignmentAssignment_1_0() { return cHasInitializerAssignmentAssignment_1_0; }
		
		//'='
		public Keyword getHasInitializerAssignmentEqualsSignKeyword_1_0_0() { return cHasInitializerAssignmentEqualsSignKeyword_1_0_0; }
		
		//initializer=AssignmentExpression<In=true,Yield>?
		public Assignment getInitializerAssignment_1_1() { return cInitializerAssignment_1_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getInitializerAssignmentExpressionParserRuleCall_1_1_0() { return cInitializerAssignmentExpressionParserRuleCall_1_1_0; }
	}
	public class BogusTypeRefFragmentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BogusTypeRefFragment");
		private final Assignment cBogusTypeRefAssignment = (Assignment)rule.eContents().get(0);
		private final RuleCall cBogusTypeRefTypeRefWithModifiersParserRuleCall_0 = (RuleCall)cBogusTypeRefAssignment.eContents().get(0);
		
		//fragment BogusTypeRefFragment *:
		//	bogusTypeRef=TypeRefWithModifiers;
		@Override public ParserRule getRule() { return rule; }
		
		//bogusTypeRef=TypeRefWithModifiers
		public Assignment getBogusTypeRefAssignment() { return cBogusTypeRefAssignment; }
		
		//TypeRefWithModifiers
		public RuleCall getBogusTypeRefTypeRefWithModifiersParserRuleCall_0() { return cBogusTypeRefTypeRefWithModifiersParserRuleCall_0; }
	}
	public class BlockElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Block");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cBlockAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cStatementsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cStatementsStatementParserRuleCall_1_0 = (RuleCall)cStatementsAssignment_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//Block <Yield>:
		//	=> ({Block} '{') statements+=Statement<Yield>* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({Block} '{') statements+=Statement<Yield>* '}'
		public Group getGroup() { return cGroup; }
		
		//=> ({Block} '{')
		public Group getGroup_0() { return cGroup_0; }
		
		//{Block} '{'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{Block}
		public Action getBlockAction_0_0_0() { return cBlockAction_0_0_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_0_0_1() { return cLeftCurlyBracketKeyword_0_0_1; }
		
		//statements+=Statement<Yield>*
		public Assignment getStatementsAssignment_1() { return cStatementsAssignment_1; }
		
		//Statement<Yield>
		public RuleCall getStatementsStatementParserRuleCall_1_0() { return cStatementsStatementParserRuleCall_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_2() { return cRightCurlyBracketKeyword_2; }
	}
	public class RootStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.RootStatement");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cBlockParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cFunctionDeclarationParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cVariableStatementParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cEmptyStatementParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cLabelledStatementParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cExpressionStatementParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cIfStatementParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		private final RuleCall cIterationStatementParserRuleCall_7 = (RuleCall)cAlternatives.eContents().get(7);
		private final RuleCall cContinueStatementParserRuleCall_8 = (RuleCall)cAlternatives.eContents().get(8);
		private final RuleCall cBreakStatementParserRuleCall_9 = (RuleCall)cAlternatives.eContents().get(9);
		private final RuleCall cReturnStatementParserRuleCall_10 = (RuleCall)cAlternatives.eContents().get(10);
		private final RuleCall cWithStatementParserRuleCall_11 = (RuleCall)cAlternatives.eContents().get(11);
		private final RuleCall cSwitchStatementParserRuleCall_12 = (RuleCall)cAlternatives.eContents().get(12);
		private final RuleCall cThrowStatementParserRuleCall_13 = (RuleCall)cAlternatives.eContents().get(13);
		private final RuleCall cTryStatementParserRuleCall_14 = (RuleCall)cAlternatives.eContents().get(14);
		private final RuleCall cDebuggerStatementParserRuleCall_15 = (RuleCall)cAlternatives.eContents().get(15);
		
		//// ****************************************************************************************************
		//// [ECM11] A.4 Statements (p. 222)
		//// ****************************************************************************************************
		//RootStatement <Yield Statement:
		//	Block<Yield> | FunctionDeclaration<Yield> | VariableStatement<In=true,Yield> | EmptyStatement
		//	| LabelledStatement<Yield> | ExpressionStatement<Yield> | IfStatement<Yield> | IterationStatement<Yield> |
		//	ContinueStatement<Yield> | BreakStatement<Yield> | ReturnStatement<Yield> | WithStatement<Yield> |
		//	SwitchStatement<Yield> | ThrowStatement<Yield> | TryStatement<Yield> | DebuggerStatement;
		@Override public ParserRule getRule() { return rule; }
		
		//Block<Yield> | FunctionDeclaration<Yield> | VariableStatement<In=true,Yield> | EmptyStatement | LabelledStatement<Yield>
		//| ExpressionStatement<Yield> | IfStatement<Yield> | IterationStatement<Yield> | ContinueStatement<Yield> |
		//BreakStatement<Yield> | ReturnStatement<Yield> | WithStatement<Yield> | SwitchStatement<Yield> | ThrowStatement<Yield>
		//| TryStatement<Yield> | DebuggerStatement
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Block<Yield>
		public RuleCall getBlockParserRuleCall_0() { return cBlockParserRuleCall_0; }
		
		//FunctionDeclaration<Yield>
		public RuleCall getFunctionDeclarationParserRuleCall_1() { return cFunctionDeclarationParserRuleCall_1; }
		
		//VariableStatement<In=true,Yield>
		public RuleCall getVariableStatementParserRuleCall_2() { return cVariableStatementParserRuleCall_2; }
		
		//EmptyStatement
		public RuleCall getEmptyStatementParserRuleCall_3() { return cEmptyStatementParserRuleCall_3; }
		
		//LabelledStatement<Yield>
		public RuleCall getLabelledStatementParserRuleCall_4() { return cLabelledStatementParserRuleCall_4; }
		
		//ExpressionStatement<Yield>
		public RuleCall getExpressionStatementParserRuleCall_5() { return cExpressionStatementParserRuleCall_5; }
		
		//IfStatement<Yield>
		public RuleCall getIfStatementParserRuleCall_6() { return cIfStatementParserRuleCall_6; }
		
		//IterationStatement<Yield>
		public RuleCall getIterationStatementParserRuleCall_7() { return cIterationStatementParserRuleCall_7; }
		
		//ContinueStatement<Yield>
		public RuleCall getContinueStatementParserRuleCall_8() { return cContinueStatementParserRuleCall_8; }
		
		//BreakStatement<Yield>
		public RuleCall getBreakStatementParserRuleCall_9() { return cBreakStatementParserRuleCall_9; }
		
		//ReturnStatement<Yield>
		public RuleCall getReturnStatementParserRuleCall_10() { return cReturnStatementParserRuleCall_10; }
		
		//WithStatement<Yield>
		public RuleCall getWithStatementParserRuleCall_11() { return cWithStatementParserRuleCall_11; }
		
		//SwitchStatement<Yield>
		public RuleCall getSwitchStatementParserRuleCall_12() { return cSwitchStatementParserRuleCall_12; }
		
		//ThrowStatement<Yield>
		public RuleCall getThrowStatementParserRuleCall_13() { return cThrowStatementParserRuleCall_13; }
		
		//TryStatement<Yield>
		public RuleCall getTryStatementParserRuleCall_14() { return cTryStatementParserRuleCall_14; }
		
		//DebuggerStatement
		public RuleCall getDebuggerStatementParserRuleCall_15() { return cDebuggerStatementParserRuleCall_15; }
	}
	public class StatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Statement");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cAnnotatedFunctionDeclarationParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cRootStatementParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//Statement <Yield>:
		//	AnnotatedFunctionDeclaration<Yield,Default=false> | RootStatement<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//AnnotatedFunctionDeclaration<Yield,Default=false> | RootStatement<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//AnnotatedFunctionDeclaration<Yield,Default=false>
		public RuleCall getAnnotatedFunctionDeclarationParserRuleCall_0() { return cAnnotatedFunctionDeclarationParserRuleCall_0; }
		
		//RootStatement<Yield>
		public RuleCall getRootStatementParserRuleCall_1() { return cRootStatementParserRuleCall_1; }
	}
	public class VariableStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.VariableStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cVariableStatementAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Assignment cVarStmtKeywordAssignment_0_0_1 = (Assignment)cGroup_0_0.eContents().get(1);
		private final RuleCall cVarStmtKeywordVariableStatementKeywordEnumRuleCall_0_0_1_0 = (RuleCall)cVarStmtKeywordAssignment_0_0_1.eContents().get(0);
		private final Assignment cVarDeclsOrBindingsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0 = (RuleCall)cVarDeclsOrBindingsAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cCommaKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cVarDeclsOrBindingsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0 = (RuleCall)cVarDeclsOrBindingsAssignment_2_1.eContents().get(0);
		private final RuleCall cSemiParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		//VariableStatement <In, Yield>:
		//	=> ({VariableStatement} varStmtKeyword=VariableStatementKeyword)
		//	varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false> (','
		//	varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false>)* Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({VariableStatement} varStmtKeyword=VariableStatementKeyword)
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false> (','
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false>)* Semi
		public Group getGroup() { return cGroup; }
		
		//=> ({VariableStatement} varStmtKeyword=VariableStatementKeyword)
		public Group getGroup_0() { return cGroup_0; }
		
		//{VariableStatement} varStmtKeyword=VariableStatementKeyword
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{VariableStatement}
		public Action getVariableStatementAction_0_0_0() { return cVariableStatementAction_0_0_0; }
		
		//varStmtKeyword=VariableStatementKeyword
		public Assignment getVarStmtKeywordAssignment_0_0_1() { return cVarStmtKeywordAssignment_0_0_1; }
		
		//VariableStatementKeyword
		public RuleCall getVarStmtKeywordVariableStatementKeywordEnumRuleCall_0_0_1_0() { return cVarStmtKeywordVariableStatementKeywordEnumRuleCall_0_0_1_0; }
		
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false>
		public Assignment getVarDeclsOrBindingsAssignment_1() { return cVarDeclsOrBindingsAssignment_1; }
		
		//VariableDeclarationOrBinding<In,Yield,false>
		public RuleCall getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0() { return cVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0; }
		
		//(',' varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false>)*
		public Group getGroup_2() { return cGroup_2; }
		
		//','
		public Keyword getCommaKeyword_2_0() { return cCommaKeyword_2_0; }
		
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false>
		public Assignment getVarDeclsOrBindingsAssignment_2_1() { return cVarDeclsOrBindingsAssignment_2_1; }
		
		//VariableDeclarationOrBinding<In,Yield,false>
		public RuleCall getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0() { return cVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_3() { return cSemiParserRuleCall_3; }
	}
	public class ExportedVariableStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportedVariableStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cExportedVariableStatementAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_0 = (RuleCall)cDeclaredModifiersAssignment_1.eContents().get(0);
		private final Assignment cVarStmtKeywordAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cVarStmtKeywordVariableStatementKeywordEnumRuleCall_2_0 = (RuleCall)cVarStmtKeywordAssignment_2.eContents().get(0);
		private final Assignment cVarDeclsOrBindingsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_3_0 = (RuleCall)cVarDeclsOrBindingsAssignment_3.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cCommaKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cVarDeclsOrBindingsAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_4_1_0 = (RuleCall)cVarDeclsOrBindingsAssignment_4_1.eContents().get(0);
		private final RuleCall cSemiParserRuleCall_5 = (RuleCall)cGroup.eContents().get(5);
		
		//ExportedVariableStatement:
		//	{ExportedVariableStatement} declaredModifiers+=N4Modifier*
		//	varStmtKeyword=VariableStatementKeyword
		//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false> (','
		//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false>)* Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//{ExportedVariableStatement} declaredModifiers+=N4Modifier* varStmtKeyword=VariableStatementKeyword
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false> (','
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false>)* Semi
		public Group getGroup() { return cGroup; }
		
		//{ExportedVariableStatement}
		public Action getExportedVariableStatementAction_0() { return cExportedVariableStatementAction_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1() { return cDeclaredModifiersAssignment_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_0; }
		
		//varStmtKeyword=VariableStatementKeyword
		public Assignment getVarStmtKeywordAssignment_2() { return cVarStmtKeywordAssignment_2; }
		
		//VariableStatementKeyword
		public RuleCall getVarStmtKeywordVariableStatementKeywordEnumRuleCall_2_0() { return cVarStmtKeywordVariableStatementKeywordEnumRuleCall_2_0; }
		
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false>
		public Assignment getVarDeclsOrBindingsAssignment_3() { return cVarDeclsOrBindingsAssignment_3; }
		
		//ExportedVariableDeclarationOrBinding<Yield=false>
		public RuleCall getVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_3_0() { return cVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_3_0; }
		
		//(',' varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false>)*
		public Group getGroup_4() { return cGroup_4; }
		
		//','
		public Keyword getCommaKeyword_4_0() { return cCommaKeyword_4_0; }
		
		//varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false>
		public Assignment getVarDeclsOrBindingsAssignment_4_1() { return cVarDeclsOrBindingsAssignment_4_1; }
		
		//ExportedVariableDeclarationOrBinding<Yield=false>
		public RuleCall getVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_4_1_0() { return cVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_4_1_0; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_5() { return cSemiParserRuleCall_5; }
	}
	public class VariableDeclarationOrBindingElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.VariableDeclarationOrBinding");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cVariableBindingParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cVariableDeclarationParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//VariableDeclarationOrBinding <In, Yield, OptionalInit>:
		//	VariableBinding<In,Yield,OptionalInit> | VariableDeclaration<In,Yield,true>;
		@Override public ParserRule getRule() { return rule; }
		
		//VariableBinding<In,Yield,OptionalInit> | VariableDeclaration<In,Yield,true>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//VariableBinding<In,Yield,OptionalInit>
		public RuleCall getVariableBindingParserRuleCall_0() { return cVariableBindingParserRuleCall_0; }
		
		//VariableDeclaration<In,Yield,true>
		public RuleCall getVariableDeclarationParserRuleCall_1() { return cVariableDeclarationParserRuleCall_1; }
	}
	public class VariableBindingElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.VariableBinding");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cPatternAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cPatternBindingPatternParserRuleCall_0_0 = (RuleCall)cPatternAssignment_0.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Keyword cEqualsSignKeyword_1_0_0_0 = (Keyword)cGroup_1_0_0.eContents().get(0);
		private final Assignment cExpressionAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0 = (RuleCall)cExpressionAssignment_1_0_0_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Keyword cEqualsSignKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cExpressionAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_1_1_0 = (RuleCall)cExpressionAssignment_1_1_1.eContents().get(0);
		
		//VariableBinding <In, Yield, OptionalInit>:
		//	=> pattern=BindingPattern<Yield> (<OptionalInit> ('=' expression=AssignmentExpression<In,Yield>)?
		//	| <!OptionalInit> '=' expression=AssignmentExpression<In,Yield>);
		@Override public ParserRule getRule() { return rule; }
		
		//=> pattern=BindingPattern<Yield> (<OptionalInit> ('=' expression=AssignmentExpression<In,Yield>)? | <!OptionalInit> '='
		//expression=AssignmentExpression<In,Yield>)
		public Group getGroup() { return cGroup; }
		
		//=> pattern=BindingPattern<Yield>
		public Assignment getPatternAssignment_0() { return cPatternAssignment_0; }
		
		//BindingPattern<Yield>
		public RuleCall getPatternBindingPatternParserRuleCall_0_0() { return cPatternBindingPatternParserRuleCall_0_0; }
		
		//<OptionalInit> ('=' expression=AssignmentExpression<In,Yield>)? | <!OptionalInit> '='
		//expression=AssignmentExpression<In,Yield>
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//<OptionalInit> ('=' expression=AssignmentExpression<In,Yield>)?
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//('=' expression=AssignmentExpression<In,Yield>)?
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//'='
		public Keyword getEqualsSignKeyword_1_0_0_0() { return cEqualsSignKeyword_1_0_0_0; }
		
		//expression=AssignmentExpression<In,Yield>
		public Assignment getExpressionAssignment_1_0_0_1() { return cExpressionAssignment_1_0_0_1; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0; }
		
		//<!OptionalInit> '=' expression=AssignmentExpression<In,Yield>
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//'='
		public Keyword getEqualsSignKeyword_1_1_0() { return cEqualsSignKeyword_1_1_0; }
		
		//expression=AssignmentExpression<In,Yield>
		public Assignment getExpressionAssignment_1_1_1() { return cExpressionAssignment_1_1_1; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_1_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_1_1_0; }
	}
	public class VariableDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.VariableDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cVariableDeclarationAction_0 = (Action)cGroup.eContents().get(0);
		private final RuleCall cVariableDeclarationImplParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//VariableDeclaration <In, Yield, AllowType>:
		//	{VariableDeclaration} VariableDeclarationImpl<In,Yield,AllowType>;
		@Override public ParserRule getRule() { return rule; }
		
		//{VariableDeclaration} VariableDeclarationImpl<In,Yield,AllowType>
		public Group getGroup() { return cGroup; }
		
		//{VariableDeclaration}
		public Action getVariableDeclarationAction_0() { return cVariableDeclarationAction_0; }
		
		//VariableDeclarationImpl<In,Yield,AllowType>
		public RuleCall getVariableDeclarationImplParserRuleCall_1() { return cVariableDeclarationImplParserRuleCall_1; }
	}
	public class VariableDeclarationImplElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.VariableDeclarationImpl");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Assignment cAnnotationsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cAnnotationsAnnotationParserRuleCall_0_0 = (RuleCall)cAnnotationsAssignment_0.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Group cGroup_1_0_0_0 = (Group)cGroup_1_0_0.eContents().get(0);
		private final Assignment cNameAssignment_1_0_0_0_0 = (Assignment)cGroup_1_0_0_0.eContents().get(0);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_0_0_0_0_0 = (RuleCall)cNameAssignment_1_0_0_0_0.eContents().get(0);
		private final RuleCall cColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1 = (RuleCall)cGroup_1_0_0_0.eContents().get(1);
		private final Group cGroup_1_0_1 = (Group)cGroup_1_0.eContents().get(1);
		private final Keyword cEqualsSignKeyword_1_0_1_0 = (Keyword)cGroup_1_0_1.eContents().get(0);
		private final Assignment cExpressionAssignment_1_0_1_1 = (Assignment)cGroup_1_0_1.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0 = (RuleCall)cExpressionAssignment_1_0_1_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Group cGroup_1_1_0 = (Group)cGroup_1_1.eContents().get(0);
		private final Assignment cNameAssignment_1_1_0_0 = (Assignment)cGroup_1_1_0.eContents().get(0);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_1_0_0_0 = (RuleCall)cNameAssignment_1_1_0_0.eContents().get(0);
		private final Group cGroup_1_1_1 = (Group)cGroup_1_1.eContents().get(1);
		private final Keyword cEqualsSignKeyword_1_1_1_0 = (Keyword)cGroup_1_1_1.eContents().get(0);
		private final Assignment cExpressionAssignment_1_1_1_1 = (Assignment)cGroup_1_1_1.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0 = (RuleCall)cExpressionAssignment_1_1_1_1.eContents().get(0);
		
		///**
		// * This rule was very complicated for Java like type annotations. It is much simpler with the ES4 colon style type annotations. However,
		// * just in case odd things will happen, may look at the previous version in the git history.
		// *
		// * The colon type annotation syntax clashes with object literals and object destruction. While we still support java type annotation in the
		// * former case, we do not allow types in the latter. This may be changed in the future.
		// */ fragment VariableDeclarationImpl <In, Yield, AllowType> *:
		//	annotations+=Annotation* (<AllowType> => (name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?) ('='
		//	expression=AssignmentExpression<In,Yield>)?
		//	| <!AllowType> => (name=BindingIdentifier<Yield>) ('=' expression=AssignmentExpression<In,Yield>)?);
		@Override public ParserRule getRule() { return rule; }
		
		//annotations+=Annotation* (<AllowType> => (name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?) ('='
		//expression=AssignmentExpression<In,Yield>)? | <!AllowType> => (name=BindingIdentifier<Yield>) ('='
		//expression=AssignmentExpression<In,Yield>)?)
		public Group getGroup() { return cGroup; }
		
		//annotations+=Annotation*
		public Assignment getAnnotationsAssignment_0() { return cAnnotationsAssignment_0; }
		
		//Annotation
		public RuleCall getAnnotationsAnnotationParserRuleCall_0_0() { return cAnnotationsAnnotationParserRuleCall_0_0; }
		
		//<AllowType> => (name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?) ('=' expression=AssignmentExpression<In,Yield>)?
		//| <!AllowType> => (name=BindingIdentifier<Yield>) ('=' expression=AssignmentExpression<In,Yield>)?
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//<AllowType> => (name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?) ('=' expression=AssignmentExpression<In,Yield>)?
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//=> (name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?)
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?
		public Group getGroup_1_0_0_0() { return cGroup_1_0_0_0; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment_1_0_0_0_0() { return cNameAssignment_1_0_0_0_0; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_0_0_0_0_0() { return cNameBindingIdentifierParserRuleCall_1_0_0_0_0_0; }
		
		//ColonSepDeclaredTypeRef?
		public RuleCall getColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1() { return cColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1; }
		
		//('=' expression=AssignmentExpression<In,Yield>)?
		public Group getGroup_1_0_1() { return cGroup_1_0_1; }
		
		//'='
		public Keyword getEqualsSignKeyword_1_0_1_0() { return cEqualsSignKeyword_1_0_1_0; }
		
		//expression=AssignmentExpression<In,Yield>
		public Assignment getExpressionAssignment_1_0_1_1() { return cExpressionAssignment_1_0_1_1; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0; }
		
		//<!AllowType> => (name=BindingIdentifier<Yield>) ('=' expression=AssignmentExpression<In,Yield>)?
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//=> (name=BindingIdentifier<Yield>)
		public Group getGroup_1_1_0() { return cGroup_1_1_0; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment_1_1_0_0() { return cNameAssignment_1_1_0_0; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_1_0_0_0() { return cNameBindingIdentifierParserRuleCall_1_1_0_0_0; }
		
		//('=' expression=AssignmentExpression<In,Yield>)?
		public Group getGroup_1_1_1() { return cGroup_1_1_1; }
		
		//'='
		public Keyword getEqualsSignKeyword_1_1_1_0() { return cEqualsSignKeyword_1_1_1_0; }
		
		//expression=AssignmentExpression<In,Yield>
		public Assignment getExpressionAssignment_1_1_1_1() { return cExpressionAssignment_1_1_1_1; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0; }
	}
	public class ExportedVariableDeclarationOrBindingElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportedVariableDeclarationOrBinding");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cExportedVariableBindingParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cExportedVariableDeclarationParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//ExportedVariableDeclarationOrBinding <Yield VariableDeclarationOrBinding:
		//	ExportedVariableBinding<Yield> | ExportedVariableDeclaration<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//ExportedVariableBinding<Yield> | ExportedVariableDeclaration<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ExportedVariableBinding<Yield>
		public RuleCall getExportedVariableBindingParserRuleCall_0() { return cExportedVariableBindingParserRuleCall_0; }
		
		//ExportedVariableDeclaration<Yield>
		public RuleCall getExportedVariableDeclarationParserRuleCall_1() { return cExportedVariableDeclarationParserRuleCall_1; }
	}
	public class ExportedVariableBindingElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportedVariableBinding");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cPatternAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cPatternBindingPatternParserRuleCall_0_0 = (RuleCall)cPatternAssignment_0.eContents().get(0);
		private final Keyword cEqualsSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cExpressionAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_2_0 = (RuleCall)cExpressionAssignment_2.eContents().get(0);
		
		//ExportedVariableBinding <Yield>:
		//	=> pattern=BindingPattern<Yield> '=' expression=AssignmentExpression<In=true,Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> pattern=BindingPattern<Yield> '=' expression=AssignmentExpression<In=true,Yield>
		public Group getGroup() { return cGroup; }
		
		//=> pattern=BindingPattern<Yield>
		public Assignment getPatternAssignment_0() { return cPatternAssignment_0; }
		
		//BindingPattern<Yield>
		public RuleCall getPatternBindingPatternParserRuleCall_0_0() { return cPatternBindingPatternParserRuleCall_0_0; }
		
		//'='
		public Keyword getEqualsSignKeyword_1() { return cEqualsSignKeyword_1; }
		
		//expression=AssignmentExpression<In=true,Yield>
		public Assignment getExpressionAssignment_2() { return cExpressionAssignment_2; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_2_0() { return cExpressionAssignmentExpressionParserRuleCall_2_0; }
	}
	public class ExportedVariableDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExportedVariableDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cExportedVariableDeclarationAction_0 = (Action)cGroup.eContents().get(0);
		private final RuleCall cVariableDeclarationImplParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		///**
		// * The created AST element has an additional reference to the inferred TVariable
		// */ ExportedVariableDeclaration <Yield>:
		//	{ExportedVariableDeclaration} VariableDeclarationImpl<In=true,Yield,AllowType=true>;
		@Override public ParserRule getRule() { return rule; }
		
		//{ExportedVariableDeclaration} VariableDeclarationImpl<In=true,Yield,AllowType=true>
		public Group getGroup() { return cGroup; }
		
		//{ExportedVariableDeclaration}
		public Action getExportedVariableDeclarationAction_0() { return cExportedVariableDeclarationAction_0; }
		
		//VariableDeclarationImpl<In=true,Yield,AllowType=true>
		public RuleCall getVariableDeclarationImplParserRuleCall_1() { return cVariableDeclarationImplParserRuleCall_1; }
	}
	public class EmptyStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.EmptyStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cEmptyStatementAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cSemicolonKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//// Defined with Action in statement: Block: {Block}  '{' (statements+=Statement)* '}';
		//EmptyStatement:
		//	{EmptyStatement} ';';
		@Override public ParserRule getRule() { return rule; }
		
		//{EmptyStatement} ';'
		public Group getGroup() { return cGroup; }
		
		//{EmptyStatement}
		public Action getEmptyStatementAction_0() { return cEmptyStatementAction_0; }
		
		//';'
		public Keyword getSemicolonKeyword_1() { return cSemicolonKeyword_1; }
	}
	public class ExpressionStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExpressionStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cExpressionAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cExpressionExpressionParserRuleCall_0_0 = (RuleCall)cExpressionAssignment_0.eContents().get(0);
		private final RuleCall cSemiParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//// Lookahead (function, {) done elsewhere: see Statement and SourceElement definitions
		//ExpressionStatement <Yield>:
		//	expression=Expression<In=true,Yield> Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//expression=Expression<In=true,Yield> Semi
		public Group getGroup() { return cGroup; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_0() { return cExpressionAssignment_0; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_0_0() { return cExpressionExpressionParserRuleCall_0_0; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_1() { return cSemiParserRuleCall_1; }
	}
	public class IfStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.IfStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cIfKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cExpressionAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cExpressionExpressionParserRuleCall_2_0 = (RuleCall)cExpressionAssignment_2.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cIfStmtAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cIfStmtStatementParserRuleCall_4_0 = (RuleCall)cIfStmtAssignment_4.eContents().get(0);
		private final Group cGroup_5 = (Group)cGroup.eContents().get(5);
		private final Keyword cElseKeyword_5_0 = (Keyword)cGroup_5.eContents().get(0);
		private final Assignment cElseStmtAssignment_5_1 = (Assignment)cGroup_5.eContents().get(1);
		private final RuleCall cElseStmtStatementParserRuleCall_5_1_0 = (RuleCall)cElseStmtAssignment_5_1.eContents().get(0);
		
		//IfStatement <Yield>:
		//	'if' '(' expression=Expression<In=true,Yield> ')' ifStmt=Statement<Yield> (=> 'else' elseStmt=Statement<Yield>)?;
		@Override public ParserRule getRule() { return rule; }
		
		//'if' '(' expression=Expression<In=true,Yield> ')' ifStmt=Statement<Yield> (=> 'else' elseStmt=Statement<Yield>)?
		public Group getGroup() { return cGroup; }
		
		//'if'
		public Keyword getIfKeyword_0() { return cIfKeyword_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_2() { return cExpressionAssignment_2; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_2_0() { return cExpressionExpressionParserRuleCall_2_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }
		
		//ifStmt=Statement<Yield>
		public Assignment getIfStmtAssignment_4() { return cIfStmtAssignment_4; }
		
		//Statement<Yield>
		public RuleCall getIfStmtStatementParserRuleCall_4_0() { return cIfStmtStatementParserRuleCall_4_0; }
		
		//(=> 'else' elseStmt=Statement<Yield>)?
		public Group getGroup_5() { return cGroup_5; }
		
		//=> 'else'
		public Keyword getElseKeyword_5_0() { return cElseKeyword_5_0; }
		
		//elseStmt=Statement<Yield>
		public Assignment getElseStmtAssignment_5_1() { return cElseStmtAssignment_5_1; }
		
		//Statement<Yield>
		public RuleCall getElseStmtStatementParserRuleCall_5_1_0() { return cElseStmtStatementParserRuleCall_5_1_0; }
	}
	public class IterationStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.IterationStatement");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cDoStatementParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cWhileStatementParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cForStatementParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//IterationStatement <Yield>:
		//	DoStatement<Yield> | WhileStatement<Yield> | ForStatement<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//DoStatement<Yield> | WhileStatement<Yield> | ForStatement<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//DoStatement<Yield>
		public RuleCall getDoStatementParserRuleCall_0() { return cDoStatementParserRuleCall_0; }
		
		//WhileStatement<Yield>
		public RuleCall getWhileStatementParserRuleCall_1() { return cWhileStatementParserRuleCall_1; }
		
		//ForStatement<Yield>
		public RuleCall getForStatementParserRuleCall_2() { return cForStatementParserRuleCall_2; }
	}
	public class DoStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.DoStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cDoKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cStatementAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cStatementStatementParserRuleCall_1_0 = (RuleCall)cStatementAssignment_1.eContents().get(0);
		private final Keyword cWhileKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cLeftParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cExpressionAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cExpressionExpressionParserRuleCall_4_0 = (RuleCall)cExpressionAssignment_4.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final RuleCall cSemiParserRuleCall_6 = (RuleCall)cGroup.eContents().get(6);
		
		//DoStatement <Yield>:
		//	'do' statement=Statement<Yield> 'while' '(' expression=Expression<In=true,Yield> ')' => Semi?;
		@Override public ParserRule getRule() { return rule; }
		
		//'do' statement=Statement<Yield> 'while' '(' expression=Expression<In=true,Yield> ')' => Semi?
		public Group getGroup() { return cGroup; }
		
		//'do'
		public Keyword getDoKeyword_0() { return cDoKeyword_0; }
		
		//statement=Statement<Yield>
		public Assignment getStatementAssignment_1() { return cStatementAssignment_1; }
		
		//Statement<Yield>
		public RuleCall getStatementStatementParserRuleCall_1_0() { return cStatementStatementParserRuleCall_1_0; }
		
		//'while'
		public Keyword getWhileKeyword_2() { return cWhileKeyword_2; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_3() { return cLeftParenthesisKeyword_3; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_4() { return cExpressionAssignment_4; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_4_0() { return cExpressionExpressionParserRuleCall_4_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_5() { return cRightParenthesisKeyword_5; }
		
		//=> Semi?
		public RuleCall getSemiParserRuleCall_6() { return cSemiParserRuleCall_6; }
	}
	public class WhileStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.WhileStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cWhileKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cExpressionAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cExpressionExpressionParserRuleCall_2_0 = (RuleCall)cExpressionAssignment_2.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cStatementAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cStatementStatementParserRuleCall_4_0 = (RuleCall)cStatementAssignment_4.eContents().get(0);
		
		//WhileStatement <Yield>:
		//	'while' '(' expression=Expression<In=true,Yield> ')' statement=Statement<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//'while' '(' expression=Expression<In=true,Yield> ')' statement=Statement<Yield>
		public Group getGroup() { return cGroup; }
		
		//'while'
		public Keyword getWhileKeyword_0() { return cWhileKeyword_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_2() { return cExpressionAssignment_2; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_2_0() { return cExpressionExpressionParserRuleCall_2_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }
		
		//statement=Statement<Yield>
		public Assignment getStatementAssignment_4() { return cStatementAssignment_4; }
		
		//Statement<Yield>
		public RuleCall getStatementStatementParserRuleCall_4_0() { return cStatementStatementParserRuleCall_4_0; }
	}
	public class ForStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ForStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cForStatementAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cForKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Alternatives cAlternatives_3 = (Alternatives)cGroup.eContents().get(3);
		private final Group cGroup_3_0 = (Group)cAlternatives_3.eContents().get(0);
		private final Group cGroup_3_0_0 = (Group)cGroup_3_0.eContents().get(0);
		private final Assignment cInitExprAssignment_3_0_0_0 = (Assignment)cGroup_3_0_0.eContents().get(0);
		private final RuleCall cInitExprLetIdentifierRefParserRuleCall_3_0_0_0_0 = (RuleCall)cInitExprAssignment_3_0_0_0.eContents().get(0);
		private final Assignment cForInAssignment_3_0_0_1 = (Assignment)cGroup_3_0_0.eContents().get(1);
		private final Keyword cForInInKeyword_3_0_0_1_0 = (Keyword)cForInAssignment_3_0_0_1.eContents().get(0);
		private final Assignment cExpressionAssignment_3_0_0_2 = (Assignment)cGroup_3_0_0.eContents().get(2);
		private final RuleCall cExpressionExpressionParserRuleCall_3_0_0_2_0 = (RuleCall)cExpressionAssignment_3_0_0_2.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_3_0_0_3 = (Keyword)cGroup_3_0_0.eContents().get(3);
		private final Group cGroup_3_1 = (Group)cAlternatives_3.eContents().get(1);
		private final Alternatives cAlternatives_3_1_0 = (Alternatives)cGroup_3_1.eContents().get(0);
		private final Group cGroup_3_1_0_0 = (Group)cAlternatives_3_1_0.eContents().get(0);
		private final Assignment cVarStmtKeywordAssignment_3_1_0_0_0 = (Assignment)cGroup_3_1_0_0.eContents().get(0);
		private final RuleCall cVarStmtKeywordVariableStatementKeywordEnumRuleCall_3_1_0_0_0_0 = (RuleCall)cVarStmtKeywordAssignment_3_1_0_0_0.eContents().get(0);
		private final Alternatives cAlternatives_3_1_0_0_1 = (Alternatives)cGroup_3_1_0_0.eContents().get(1);
		private final Group cGroup_3_1_0_0_1_0 = (Group)cAlternatives_3_1_0_0_1.eContents().get(0);
		private final Group cGroup_3_1_0_0_1_0_0 = (Group)cGroup_3_1_0_0_1_0.eContents().get(0);
		private final Assignment cVarDeclsOrBindingsAssignment_3_1_0_0_1_0_0_0 = (Assignment)cGroup_3_1_0_0_1_0_0.eContents().get(0);
		private final RuleCall cVarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0 = (RuleCall)cVarDeclsOrBindingsAssignment_3_1_0_0_1_0_0_0.eContents().get(0);
		private final Alternatives cAlternatives_3_1_0_0_1_0_0_1 = (Alternatives)cGroup_3_1_0_0_1_0_0.eContents().get(1);
		private final Assignment cForInAssignment_3_1_0_0_1_0_0_1_0 = (Assignment)cAlternatives_3_1_0_0_1_0_0_1.eContents().get(0);
		private final Keyword cForInInKeyword_3_1_0_0_1_0_0_1_0_0 = (Keyword)cForInAssignment_3_1_0_0_1_0_0_1_0.eContents().get(0);
		private final Assignment cForOfAssignment_3_1_0_0_1_0_0_1_1 = (Assignment)cAlternatives_3_1_0_0_1_0_0_1.eContents().get(1);
		private final Keyword cForOfOfKeyword_3_1_0_0_1_0_0_1_1_0 = (Keyword)cForOfAssignment_3_1_0_0_1_0_0_1_1.eContents().get(0);
		private final Assignment cExpressionAssignment_3_1_0_0_1_0_0_2 = (Assignment)cGroup_3_1_0_0_1_0_0.eContents().get(2);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0 = (RuleCall)cExpressionAssignment_3_1_0_0_1_0_0_2.eContents().get(0);
		private final Group cGroup_3_1_0_0_1_1 = (Group)cAlternatives_3_1_0_0_1.eContents().get(1);
		private final Assignment cVarDeclsOrBindingsAssignment_3_1_0_0_1_1_0 = (Assignment)cGroup_3_1_0_0_1_1.eContents().get(0);
		private final RuleCall cVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0 = (RuleCall)cVarDeclsOrBindingsAssignment_3_1_0_0_1_1_0.eContents().get(0);
		private final Alternatives cAlternatives_3_1_0_0_1_1_1 = (Alternatives)cGroup_3_1_0_0_1_1.eContents().get(1);
		private final Group cGroup_3_1_0_0_1_1_1_0 = (Group)cAlternatives_3_1_0_0_1_1_1.eContents().get(0);
		private final Group cGroup_3_1_0_0_1_1_1_0_0 = (Group)cGroup_3_1_0_0_1_1_1_0.eContents().get(0);
		private final Keyword cCommaKeyword_3_1_0_0_1_1_1_0_0_0 = (Keyword)cGroup_3_1_0_0_1_1_1_0_0.eContents().get(0);
		private final Assignment cVarDeclsOrBindingsAssignment_3_1_0_0_1_1_1_0_0_1 = (Assignment)cGroup_3_1_0_0_1_1_1_0_0.eContents().get(1);
		private final RuleCall cVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0 = (RuleCall)cVarDeclsOrBindingsAssignment_3_1_0_0_1_1_1_0_0_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_3_1_0_0_1_1_1_0_1 = (Keyword)cGroup_3_1_0_0_1_1_1_0.eContents().get(1);
		private final Assignment cExpressionAssignment_3_1_0_0_1_1_1_0_2 = (Assignment)cGroup_3_1_0_0_1_1_1_0.eContents().get(2);
		private final RuleCall cExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0 = (RuleCall)cExpressionAssignment_3_1_0_0_1_1_1_0_2.eContents().get(0);
		private final Keyword cSemicolonKeyword_3_1_0_0_1_1_1_0_3 = (Keyword)cGroup_3_1_0_0_1_1_1_0.eContents().get(3);
		private final Assignment cUpdateExprAssignment_3_1_0_0_1_1_1_0_4 = (Assignment)cGroup_3_1_0_0_1_1_1_0.eContents().get(4);
		private final RuleCall cUpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0 = (RuleCall)cUpdateExprAssignment_3_1_0_0_1_1_1_0_4.eContents().get(0);
		private final Group cGroup_3_1_0_0_1_1_1_1 = (Group)cAlternatives_3_1_0_0_1_1_1.eContents().get(1);
		private final Assignment cForInAssignment_3_1_0_0_1_1_1_1_0 = (Assignment)cGroup_3_1_0_0_1_1_1_1.eContents().get(0);
		private final Keyword cForInInKeyword_3_1_0_0_1_1_1_1_0_0 = (Keyword)cForInAssignment_3_1_0_0_1_1_1_1_0.eContents().get(0);
		private final Assignment cExpressionAssignment_3_1_0_0_1_1_1_1_1 = (Assignment)cGroup_3_1_0_0_1_1_1_1.eContents().get(1);
		private final RuleCall cExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0 = (RuleCall)cExpressionAssignment_3_1_0_0_1_1_1_1_1.eContents().get(0);
		private final Group cGroup_3_1_0_0_1_1_1_2 = (Group)cAlternatives_3_1_0_0_1_1_1.eContents().get(2);
		private final Assignment cForOfAssignment_3_1_0_0_1_1_1_2_0 = (Assignment)cGroup_3_1_0_0_1_1_1_2.eContents().get(0);
		private final Keyword cForOfOfKeyword_3_1_0_0_1_1_1_2_0_0 = (Keyword)cForOfAssignment_3_1_0_0_1_1_1_2_0.eContents().get(0);
		private final Assignment cExpressionAssignment_3_1_0_0_1_1_1_2_1 = (Assignment)cGroup_3_1_0_0_1_1_1_2.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0 = (RuleCall)cExpressionAssignment_3_1_0_0_1_1_1_2_1.eContents().get(0);
		private final Group cGroup_3_1_0_1 = (Group)cAlternatives_3_1_0.eContents().get(1);
		private final Assignment cInitExprAssignment_3_1_0_1_0 = (Assignment)cGroup_3_1_0_1.eContents().get(0);
		private final RuleCall cInitExprExpressionParserRuleCall_3_1_0_1_0_0 = (RuleCall)cInitExprAssignment_3_1_0_1_0.eContents().get(0);
		private final Alternatives cAlternatives_3_1_0_1_1 = (Alternatives)cGroup_3_1_0_1.eContents().get(1);
		private final Group cGroup_3_1_0_1_1_0 = (Group)cAlternatives_3_1_0_1_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_3_1_0_1_1_0_0 = (Keyword)cGroup_3_1_0_1_1_0.eContents().get(0);
		private final Assignment cExpressionAssignment_3_1_0_1_1_0_1 = (Assignment)cGroup_3_1_0_1_1_0.eContents().get(1);
		private final RuleCall cExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0 = (RuleCall)cExpressionAssignment_3_1_0_1_1_0_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_3_1_0_1_1_0_2 = (Keyword)cGroup_3_1_0_1_1_0.eContents().get(2);
		private final Assignment cUpdateExprAssignment_3_1_0_1_1_0_3 = (Assignment)cGroup_3_1_0_1_1_0.eContents().get(3);
		private final RuleCall cUpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0 = (RuleCall)cUpdateExprAssignment_3_1_0_1_1_0_3.eContents().get(0);
		private final Group cGroup_3_1_0_1_1_1 = (Group)cAlternatives_3_1_0_1_1.eContents().get(1);
		private final Assignment cForInAssignment_3_1_0_1_1_1_0 = (Assignment)cGroup_3_1_0_1_1_1.eContents().get(0);
		private final Keyword cForInInKeyword_3_1_0_1_1_1_0_0 = (Keyword)cForInAssignment_3_1_0_1_1_1_0.eContents().get(0);
		private final Assignment cExpressionAssignment_3_1_0_1_1_1_1 = (Assignment)cGroup_3_1_0_1_1_1.eContents().get(1);
		private final RuleCall cExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0 = (RuleCall)cExpressionAssignment_3_1_0_1_1_1_1.eContents().get(0);
		private final Group cGroup_3_1_0_1_1_2 = (Group)cAlternatives_3_1_0_1_1.eContents().get(2);
		private final Assignment cForOfAssignment_3_1_0_1_1_2_0 = (Assignment)cGroup_3_1_0_1_1_2.eContents().get(0);
		private final Keyword cForOfOfKeyword_3_1_0_1_1_2_0_0 = (Keyword)cForOfAssignment_3_1_0_1_1_2_0.eContents().get(0);
		private final Assignment cExpressionAssignment_3_1_0_1_1_2_1 = (Assignment)cGroup_3_1_0_1_1_2.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0 = (RuleCall)cExpressionAssignment_3_1_0_1_1_2_1.eContents().get(0);
		private final Group cGroup_3_1_0_2 = (Group)cAlternatives_3_1_0.eContents().get(2);
		private final Keyword cSemicolonKeyword_3_1_0_2_0 = (Keyword)cGroup_3_1_0_2.eContents().get(0);
		private final Assignment cExpressionAssignment_3_1_0_2_1 = (Assignment)cGroup_3_1_0_2.eContents().get(1);
		private final RuleCall cExpressionExpressionParserRuleCall_3_1_0_2_1_0 = (RuleCall)cExpressionAssignment_3_1_0_2_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_3_1_0_2_2 = (Keyword)cGroup_3_1_0_2.eContents().get(2);
		private final Assignment cUpdateExprAssignment_3_1_0_2_3 = (Assignment)cGroup_3_1_0_2.eContents().get(3);
		private final RuleCall cUpdateExprExpressionParserRuleCall_3_1_0_2_3_0 = (RuleCall)cUpdateExprAssignment_3_1_0_2_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_3_1_1 = (Keyword)cGroup_3_1.eContents().get(1);
		private final Assignment cStatementAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cStatementStatementParserRuleCall_4_0 = (RuleCall)cStatementAssignment_4.eContents().get(0);
		
		//ForStatement <Yield>:
		//	{ForStatement} 'for' '(' (
		//	// this is not in the spec as far as I can tell, but there are tests that rely on this to be valid JS
		//	=> (initExpr=LetIdentifierRef forIn?='in' expression=Expression<In=true,Yield> ')') | (->
		//	varStmtKeyword=VariableStatementKeyword (=>
		//	(varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield> (forIn?='in' | forOf?='of') ->
		//	expression=AssignmentExpression<In=true,Yield>?) |
		//	varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true> ((','
		//	varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>? ';'
		//	updateExpr=Expression<In=true,Yield>?
		//	| forIn?='in' expression=Expression<In=true,Yield>?
		//	| forOf?='of' expression=AssignmentExpression<In=true,Yield>?)) | initExpr=Expression<In=false,Yield> (';'
		//	expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>?
		//	| forIn?='in' expression=Expression<In=true,Yield>?
		//	| forOf?='of' expression=AssignmentExpression<In=true,Yield>?) | ';' expression=Expression<In=true,Yield>? ';'
		//	updateExpr=Expression<In=true,Yield>?)
		//	')') statement=Statement<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//{ForStatement} 'for' '(' ( // this is not in the spec as far as I can tell, but there are tests that rely on this to be valid JS
		//=> (initExpr=LetIdentifierRef forIn?='in' expression=Expression<In=true,Yield> ')') | (->
		//varStmtKeyword=VariableStatementKeyword (=> (varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield>
		//(forIn?='in' | forOf?='of') -> expression=AssignmentExpression<In=true,Yield>?) |
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true> ((','
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>? ';'
		//updateExpr=Expression<In=true,Yield>? | forIn?='in' expression=Expression<In=true,Yield>? | forOf?='of'
		//expression=AssignmentExpression<In=true,Yield>?)) | initExpr=Expression<In=false,Yield> (';'
		//expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>? | forIn?='in'
		//expression=Expression<In=true,Yield>? | forOf?='of' expression=AssignmentExpression<In=true,Yield>?) | ';'
		//expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>?) ')') statement=Statement<Yield>
		public Group getGroup() { return cGroup; }
		
		//{ForStatement}
		public Action getForStatementAction_0() { return cForStatementAction_0; }
		
		//'for'
		public Keyword getForKeyword_1() { return cForKeyword_1; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }
		
		//=> (initExpr=LetIdentifierRef forIn?='in' expression=Expression<In=true,Yield> ')') | (->
		//varStmtKeyword=VariableStatementKeyword (=> (varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield>
		//(forIn?='in' | forOf?='of') -> expression=AssignmentExpression<In=true,Yield>?) |
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true> ((','
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>? ';'
		//updateExpr=Expression<In=true,Yield>? | forIn?='in' expression=Expression<In=true,Yield>? | forOf?='of'
		//expression=AssignmentExpression<In=true,Yield>?)) | initExpr=Expression<In=false,Yield> (';'
		//expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>? | forIn?='in'
		//expression=Expression<In=true,Yield>? | forOf?='of' expression=AssignmentExpression<In=true,Yield>?) | ';'
		//expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>?) ')'
		public Alternatives getAlternatives_3() { return cAlternatives_3; }
		
		//// this is not in the spec as far as I can tell, but there are tests that rely on this to be valid JS
		//=> (initExpr=LetIdentifierRef forIn?='in' expression=Expression<In=true,Yield> ')')
		public Group getGroup_3_0() { return cGroup_3_0; }
		
		//initExpr=LetIdentifierRef forIn?='in' expression=Expression<In=true,Yield> ')'
		public Group getGroup_3_0_0() { return cGroup_3_0_0; }
		
		//initExpr=LetIdentifierRef
		public Assignment getInitExprAssignment_3_0_0_0() { return cInitExprAssignment_3_0_0_0; }
		
		//LetIdentifierRef
		public RuleCall getInitExprLetIdentifierRefParserRuleCall_3_0_0_0_0() { return cInitExprLetIdentifierRefParserRuleCall_3_0_0_0_0; }
		
		//forIn?='in'
		public Assignment getForInAssignment_3_0_0_1() { return cForInAssignment_3_0_0_1; }
		
		//'in'
		public Keyword getForInInKeyword_3_0_0_1_0() { return cForInInKeyword_3_0_0_1_0; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_3_0_0_2() { return cExpressionAssignment_3_0_0_2; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_3_0_0_2_0() { return cExpressionExpressionParserRuleCall_3_0_0_2_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3_0_0_3() { return cRightParenthesisKeyword_3_0_0_3; }
		
		//(-> varStmtKeyword=VariableStatementKeyword (=>
		//(varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield> (forIn?='in' | forOf?='of') ->
		//expression=AssignmentExpression<In=true,Yield>?) |
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true> ((','
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>? ';'
		//updateExpr=Expression<In=true,Yield>? | forIn?='in' expression=Expression<In=true,Yield>? | forOf?='of'
		//expression=AssignmentExpression<In=true,Yield>?)) | initExpr=Expression<In=false,Yield> (';'
		//expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>? | forIn?='in'
		//expression=Expression<In=true,Yield>? | forOf?='of' expression=AssignmentExpression<In=true,Yield>?) | ';'
		//expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>?) ')'
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//-> varStmtKeyword=VariableStatementKeyword (=>
		//(varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield> (forIn?='in' | forOf?='of') ->
		//expression=AssignmentExpression<In=true,Yield>?) |
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true> ((','
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>? ';'
		//updateExpr=Expression<In=true,Yield>? | forIn?='in' expression=Expression<In=true,Yield>? | forOf?='of'
		//expression=AssignmentExpression<In=true,Yield>?)) | initExpr=Expression<In=false,Yield> (';'
		//expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>? | forIn?='in'
		//expression=Expression<In=true,Yield>? | forOf?='of' expression=AssignmentExpression<In=true,Yield>?) | ';'
		//expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>?
		public Alternatives getAlternatives_3_1_0() { return cAlternatives_3_1_0; }
		
		//-> varStmtKeyword=VariableStatementKeyword (=>
		//(varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield> (forIn?='in' | forOf?='of') ->
		//expression=AssignmentExpression<In=true,Yield>?) |
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true> ((','
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>? ';'
		//updateExpr=Expression<In=true,Yield>? | forIn?='in' expression=Expression<In=true,Yield>? | forOf?='of'
		//expression=AssignmentExpression<In=true,Yield>?))
		public Group getGroup_3_1_0_0() { return cGroup_3_1_0_0; }
		
		//-> varStmtKeyword=VariableStatementKeyword
		public Assignment getVarStmtKeywordAssignment_3_1_0_0_0() { return cVarStmtKeywordAssignment_3_1_0_0_0; }
		
		//VariableStatementKeyword
		public RuleCall getVarStmtKeywordVariableStatementKeywordEnumRuleCall_3_1_0_0_0_0() { return cVarStmtKeywordVariableStatementKeywordEnumRuleCall_3_1_0_0_0_0; }
		
		//=> (varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield> (forIn?='in' | forOf?='of') ->
		//expression=AssignmentExpression<In=true,Yield>?) |
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true> ((','
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>? ';'
		//updateExpr=Expression<In=true,Yield>? | forIn?='in' expression=Expression<In=true,Yield>? | forOf?='of'
		//expression=AssignmentExpression<In=true,Yield>?)
		public Alternatives getAlternatives_3_1_0_0_1() { return cAlternatives_3_1_0_0_1; }
		
		//=> (varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield> (forIn?='in' | forOf?='of') ->
		//expression=AssignmentExpression<In=true,Yield>?)
		public Group getGroup_3_1_0_0_1_0() { return cGroup_3_1_0_0_1_0; }
		
		//varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield> (forIn?='in' | forOf?='of') ->
		//expression=AssignmentExpression<In=true,Yield>?
		public Group getGroup_3_1_0_0_1_0_0() { return cGroup_3_1_0_0_1_0_0; }
		
		//varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield>
		public Assignment getVarDeclsOrBindingsAssignment_3_1_0_0_1_0_0_0() { return cVarDeclsOrBindingsAssignment_3_1_0_0_1_0_0_0; }
		
		//BindingIdentifierAsVariableDeclaration<In=false,Yield>
		public RuleCall getVarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0() { return cVarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0; }
		
		//forIn?='in' | forOf?='of'
		public Alternatives getAlternatives_3_1_0_0_1_0_0_1() { return cAlternatives_3_1_0_0_1_0_0_1; }
		
		//forIn?='in'
		public Assignment getForInAssignment_3_1_0_0_1_0_0_1_0() { return cForInAssignment_3_1_0_0_1_0_0_1_0; }
		
		//'in'
		public Keyword getForInInKeyword_3_1_0_0_1_0_0_1_0_0() { return cForInInKeyword_3_1_0_0_1_0_0_1_0_0; }
		
		//forOf?='of'
		public Assignment getForOfAssignment_3_1_0_0_1_0_0_1_1() { return cForOfAssignment_3_1_0_0_1_0_0_1_1; }
		
		//'of'
		public Keyword getForOfOfKeyword_3_1_0_0_1_0_0_1_1_0() { return cForOfOfKeyword_3_1_0_0_1_0_0_1_1_0; }
		
		//-> expression=AssignmentExpression<In=true,Yield>?
		public Assignment getExpressionAssignment_3_1_0_0_1_0_0_2() { return cExpressionAssignment_3_1_0_0_1_0_0_2; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0() { return cExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0; }
		
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true> ((','
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>? ';'
		//updateExpr=Expression<In=true,Yield>? | forIn?='in' expression=Expression<In=true,Yield>? | forOf?='of'
		//expression=AssignmentExpression<In=true,Yield>?)
		public Group getGroup_3_1_0_0_1_1() { return cGroup_3_1_0_0_1_1; }
		
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true>
		public Assignment getVarDeclsOrBindingsAssignment_3_1_0_0_1_1_0() { return cVarDeclsOrBindingsAssignment_3_1_0_0_1_1_0; }
		
		//VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true>
		public RuleCall getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0() { return cVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0; }
		
		//(',' varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>?
		//';' updateExpr=Expression<In=true,Yield>? | forIn?='in' expression=Expression<In=true,Yield>? | forOf?='of'
		//expression=AssignmentExpression<In=true,Yield>?
		public Alternatives getAlternatives_3_1_0_0_1_1_1() { return cAlternatives_3_1_0_0_1_1_1; }
		
		//(',' varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>?
		//';' updateExpr=Expression<In=true,Yield>?
		public Group getGroup_3_1_0_0_1_1_1_0() { return cGroup_3_1_0_0_1_1_1_0; }
		
		//(',' varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)*
		public Group getGroup_3_1_0_0_1_1_1_0_0() { return cGroup_3_1_0_0_1_1_1_0_0; }
		
		//','
		public Keyword getCommaKeyword_3_1_0_0_1_1_1_0_0_0() { return cCommaKeyword_3_1_0_0_1_1_1_0_0_0; }
		
		//varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>
		public Assignment getVarDeclsOrBindingsAssignment_3_1_0_0_1_1_1_0_0_1() { return cVarDeclsOrBindingsAssignment_3_1_0_0_1_1_1_0_0_1; }
		
		//VariableDeclarationOrBinding<In=false,Yield,false>
		public RuleCall getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0() { return cVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0; }
		
		//';'
		public Keyword getSemicolonKeyword_3_1_0_0_1_1_1_0_1() { return cSemicolonKeyword_3_1_0_0_1_1_1_0_1; }
		
		//expression=Expression<In=true,Yield>?
		public Assignment getExpressionAssignment_3_1_0_0_1_1_1_0_2() { return cExpressionAssignment_3_1_0_0_1_1_1_0_2; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0() { return cExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0; }
		
		//';'
		public Keyword getSemicolonKeyword_3_1_0_0_1_1_1_0_3() { return cSemicolonKeyword_3_1_0_0_1_1_1_0_3; }
		
		//updateExpr=Expression<In=true,Yield>?
		public Assignment getUpdateExprAssignment_3_1_0_0_1_1_1_0_4() { return cUpdateExprAssignment_3_1_0_0_1_1_1_0_4; }
		
		//Expression<In=true,Yield>
		public RuleCall getUpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0() { return cUpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0; }
		
		//forIn?='in' expression=Expression<In=true,Yield>?
		public Group getGroup_3_1_0_0_1_1_1_1() { return cGroup_3_1_0_0_1_1_1_1; }
		
		//forIn?='in'
		public Assignment getForInAssignment_3_1_0_0_1_1_1_1_0() { return cForInAssignment_3_1_0_0_1_1_1_1_0; }
		
		//'in'
		public Keyword getForInInKeyword_3_1_0_0_1_1_1_1_0_0() { return cForInInKeyword_3_1_0_0_1_1_1_1_0_0; }
		
		//expression=Expression<In=true,Yield>?
		public Assignment getExpressionAssignment_3_1_0_0_1_1_1_1_1() { return cExpressionAssignment_3_1_0_0_1_1_1_1_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0() { return cExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0; }
		
		//forOf?='of' expression=AssignmentExpression<In=true,Yield>?
		public Group getGroup_3_1_0_0_1_1_1_2() { return cGroup_3_1_0_0_1_1_1_2; }
		
		//forOf?='of'
		public Assignment getForOfAssignment_3_1_0_0_1_1_1_2_0() { return cForOfAssignment_3_1_0_0_1_1_1_2_0; }
		
		//'of'
		public Keyword getForOfOfKeyword_3_1_0_0_1_1_1_2_0_0() { return cForOfOfKeyword_3_1_0_0_1_1_1_2_0_0; }
		
		//expression=AssignmentExpression<In=true,Yield>?
		public Assignment getExpressionAssignment_3_1_0_0_1_1_1_2_1() { return cExpressionAssignment_3_1_0_0_1_1_1_2_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0() { return cExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0; }
		
		//initExpr=Expression<In=false,Yield> (';' expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>?
		//| forIn?='in' expression=Expression<In=true,Yield>? | forOf?='of' expression=AssignmentExpression<In=true,Yield>?)
		public Group getGroup_3_1_0_1() { return cGroup_3_1_0_1; }
		
		//initExpr=Expression<In=false,Yield>
		public Assignment getInitExprAssignment_3_1_0_1_0() { return cInitExprAssignment_3_1_0_1_0; }
		
		//Expression<In=false,Yield>
		public RuleCall getInitExprExpressionParserRuleCall_3_1_0_1_0_0() { return cInitExprExpressionParserRuleCall_3_1_0_1_0_0; }
		
		//';' expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>? | forIn?='in'
		//expression=Expression<In=true,Yield>? | forOf?='of' expression=AssignmentExpression<In=true,Yield>?
		public Alternatives getAlternatives_3_1_0_1_1() { return cAlternatives_3_1_0_1_1; }
		
		//';' expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>?
		public Group getGroup_3_1_0_1_1_0() { return cGroup_3_1_0_1_1_0; }
		
		//';'
		public Keyword getSemicolonKeyword_3_1_0_1_1_0_0() { return cSemicolonKeyword_3_1_0_1_1_0_0; }
		
		//expression=Expression<In=true,Yield>?
		public Assignment getExpressionAssignment_3_1_0_1_1_0_1() { return cExpressionAssignment_3_1_0_1_1_0_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0() { return cExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0; }
		
		//';'
		public Keyword getSemicolonKeyword_3_1_0_1_1_0_2() { return cSemicolonKeyword_3_1_0_1_1_0_2; }
		
		//updateExpr=Expression<In=true,Yield>?
		public Assignment getUpdateExprAssignment_3_1_0_1_1_0_3() { return cUpdateExprAssignment_3_1_0_1_1_0_3; }
		
		//Expression<In=true,Yield>
		public RuleCall getUpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0() { return cUpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0; }
		
		//forIn?='in' expression=Expression<In=true,Yield>?
		public Group getGroup_3_1_0_1_1_1() { return cGroup_3_1_0_1_1_1; }
		
		//forIn?='in'
		public Assignment getForInAssignment_3_1_0_1_1_1_0() { return cForInAssignment_3_1_0_1_1_1_0; }
		
		//'in'
		public Keyword getForInInKeyword_3_1_0_1_1_1_0_0() { return cForInInKeyword_3_1_0_1_1_1_0_0; }
		
		//expression=Expression<In=true,Yield>?
		public Assignment getExpressionAssignment_3_1_0_1_1_1_1() { return cExpressionAssignment_3_1_0_1_1_1_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0() { return cExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0; }
		
		//forOf?='of' expression=AssignmentExpression<In=true,Yield>?
		public Group getGroup_3_1_0_1_1_2() { return cGroup_3_1_0_1_1_2; }
		
		//forOf?='of'
		public Assignment getForOfAssignment_3_1_0_1_1_2_0() { return cForOfAssignment_3_1_0_1_1_2_0; }
		
		//'of'
		public Keyword getForOfOfKeyword_3_1_0_1_1_2_0_0() { return cForOfOfKeyword_3_1_0_1_1_2_0_0; }
		
		//expression=AssignmentExpression<In=true,Yield>?
		public Assignment getExpressionAssignment_3_1_0_1_1_2_1() { return cExpressionAssignment_3_1_0_1_1_2_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0() { return cExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0; }
		
		//';' expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>?
		public Group getGroup_3_1_0_2() { return cGroup_3_1_0_2; }
		
		//';'
		public Keyword getSemicolonKeyword_3_1_0_2_0() { return cSemicolonKeyword_3_1_0_2_0; }
		
		//expression=Expression<In=true,Yield>?
		public Assignment getExpressionAssignment_3_1_0_2_1() { return cExpressionAssignment_3_1_0_2_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_3_1_0_2_1_0() { return cExpressionExpressionParserRuleCall_3_1_0_2_1_0; }
		
		//';'
		public Keyword getSemicolonKeyword_3_1_0_2_2() { return cSemicolonKeyword_3_1_0_2_2; }
		
		//updateExpr=Expression<In=true,Yield>?
		public Assignment getUpdateExprAssignment_3_1_0_2_3() { return cUpdateExprAssignment_3_1_0_2_3; }
		
		//Expression<In=true,Yield>
		public RuleCall getUpdateExprExpressionParserRuleCall_3_1_0_2_3_0() { return cUpdateExprExpressionParserRuleCall_3_1_0_2_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3_1_1() { return cRightParenthesisKeyword_3_1_1; }
		
		//statement=Statement<Yield>
		public Assignment getStatementAssignment_4() { return cStatementAssignment_4; }
		
		//Statement<Yield>
		public RuleCall getStatementStatementParserRuleCall_4_0() { return cStatementStatementParserRuleCall_4_0; }
	}
	public class LetIdentifierRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LetIdentifierRef");
		private final Assignment cIdAssignment = (Assignment)rule.eContents().get(1);
		private final CrossReference cIdIdentifiableElementCrossReference_0 = (CrossReference)cIdAssignment.eContents().get(0);
		private final RuleCall cIdIdentifiableElementLetAsIdentifierParserRuleCall_0_1 = (RuleCall)cIdIdentifiableElementCrossReference_0.eContents().get(1);
		
		//LetIdentifierRef IdentifierRef:
		//	id=[types::IdentifiableElement|LetAsIdentifier];
		@Override public ParserRule getRule() { return rule; }
		
		//id=[types::IdentifiableElement|LetAsIdentifier]
		public Assignment getIdAssignment() { return cIdAssignment; }
		
		//[types::IdentifiableElement|LetAsIdentifier]
		public CrossReference getIdIdentifiableElementCrossReference_0() { return cIdIdentifiableElementCrossReference_0; }
		
		//LetAsIdentifier
		public RuleCall getIdIdentifiableElementLetAsIdentifierParserRuleCall_0_1() { return cIdIdentifiableElementLetAsIdentifierParserRuleCall_0_1; }
	}
	public class LetAsIdentifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LetAsIdentifier");
		private final Keyword cLetKeyword = (Keyword)rule.eContents().get(1);
		
		//LetAsIdentifier:
		//	'let';
		@Override public ParserRule getRule() { return rule; }
		
		//'let'
		public Keyword getLetKeyword() { return cLetKeyword; }
	}
	public class BindingIdentifierAsVariableDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BindingIdentifierAsVariableDeclaration");
		private final Assignment cNameAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cNameBindingIdentifierParserRuleCall_0 = (RuleCall)cNameAssignment.eContents().get(0);
		
		//BindingIdentifierAsVariableDeclaration <In, Yield VariableDeclaration:
		//	name=BindingIdentifier<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		////	annotations+=Annotation*
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment() { return cNameAssignment; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_0() { return cNameBindingIdentifierParserRuleCall_0; }
	}
	public class ContinueStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ContinueStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cContinueStatementAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cContinueKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cLabelAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final CrossReference cLabelLabelledStatementCrossReference_2_0 = (CrossReference)cLabelAssignment_2.eContents().get(0);
		private final RuleCall cLabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1 = (RuleCall)cLabelLabelledStatementCrossReference_2_0.eContents().get(1);
		private final RuleCall cSemiParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		///**
		// * The AutomaticSemicolonInjector rewrites the antlr grammar for this rule to inject the promotion of EOL to a statement delimiter.
		// */ ContinueStatement <Yield>:
		//	{ContinueStatement} 'continue' label=[LabelledStatement|BindingIdentifier<Yield>]? Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//{ContinueStatement} 'continue' label=[LabelledStatement|BindingIdentifier<Yield>]? Semi
		public Group getGroup() { return cGroup; }
		
		//{ContinueStatement}
		public Action getContinueStatementAction_0() { return cContinueStatementAction_0; }
		
		//'continue'
		public Keyword getContinueKeyword_1() { return cContinueKeyword_1; }
		
		//label=[LabelledStatement|BindingIdentifier<Yield>]?
		public Assignment getLabelAssignment_2() { return cLabelAssignment_2; }
		
		//[LabelledStatement|BindingIdentifier<Yield>]
		public CrossReference getLabelLabelledStatementCrossReference_2_0() { return cLabelLabelledStatementCrossReference_2_0; }
		
		//BindingIdentifier<Yield>
		public RuleCall getLabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1() { return cLabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_3() { return cSemiParserRuleCall_3; }
	}
	public class BreakStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BreakStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cBreakStatementAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cBreakKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cLabelAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final CrossReference cLabelLabelledStatementCrossReference_2_0 = (CrossReference)cLabelAssignment_2.eContents().get(0);
		private final RuleCall cLabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1 = (RuleCall)cLabelLabelledStatementCrossReference_2_0.eContents().get(1);
		private final RuleCall cSemiParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		///**
		// * The AutomaticSemicolonInjector rewrites the antlr grammar for this rule to inject the promotion of EOL to a statement delimiter.
		// */ BreakStatement <Yield>:
		//	{BreakStatement} 'break' label=[LabelledStatement|BindingIdentifier<Yield>]? Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//{BreakStatement} 'break' label=[LabelledStatement|BindingIdentifier<Yield>]? Semi
		public Group getGroup() { return cGroup; }
		
		//{BreakStatement}
		public Action getBreakStatementAction_0() { return cBreakStatementAction_0; }
		
		//'break'
		public Keyword getBreakKeyword_1() { return cBreakKeyword_1; }
		
		//label=[LabelledStatement|BindingIdentifier<Yield>]?
		public Assignment getLabelAssignment_2() { return cLabelAssignment_2; }
		
		//[LabelledStatement|BindingIdentifier<Yield>]
		public CrossReference getLabelLabelledStatementCrossReference_2_0() { return cLabelLabelledStatementCrossReference_2_0; }
		
		//BindingIdentifier<Yield>
		public RuleCall getLabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1() { return cLabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_3() { return cSemiParserRuleCall_3; }
	}
	public class ReturnStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ReturnStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cReturnStatementAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cReturnKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cExpressionAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cExpressionExpressionParserRuleCall_2_0 = (RuleCall)cExpressionAssignment_2.eContents().get(0);
		private final RuleCall cSemiParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		///**
		// * The AutomaticSemicolonInjector rewrites the antlr grammar for this rule to inject the promotion of EOL to a statement delimiter.
		// */ ReturnStatement <Yield>:
		//	{ReturnStatement} 'return' expression=Expression<In=true,Yield>? Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//{ReturnStatement} 'return' expression=Expression<In=true,Yield>? Semi
		public Group getGroup() { return cGroup; }
		
		//{ReturnStatement}
		public Action getReturnStatementAction_0() { return cReturnStatementAction_0; }
		
		//'return'
		public Keyword getReturnKeyword_1() { return cReturnKeyword_1; }
		
		//expression=Expression<In=true,Yield>?
		public Assignment getExpressionAssignment_2() { return cExpressionAssignment_2; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_2_0() { return cExpressionExpressionParserRuleCall_2_0; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_3() { return cSemiParserRuleCall_3; }
	}
	public class WithStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.WithStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cWithKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cExpressionAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cExpressionExpressionParserRuleCall_2_0 = (RuleCall)cExpressionAssignment_2.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cStatementAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cStatementStatementParserRuleCall_4_0 = (RuleCall)cStatementAssignment_4.eContents().get(0);
		
		//WithStatement <Yield>:
		//	'with' '(' expression=Expression<In=true,Yield> ')' statement=Statement<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//'with' '(' expression=Expression<In=true,Yield> ')' statement=Statement<Yield>
		public Group getGroup() { return cGroup; }
		
		//'with'
		public Keyword getWithKeyword_0() { return cWithKeyword_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_2() { return cExpressionAssignment_2; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_2_0() { return cExpressionExpressionParserRuleCall_2_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }
		
		//statement=Statement<Yield>
		public Assignment getStatementAssignment_4() { return cStatementAssignment_4; }
		
		//Statement<Yield>
		public RuleCall getStatementStatementParserRuleCall_4_0() { return cStatementStatementParserRuleCall_4_0; }
	}
	public class SwitchStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.SwitchStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cSwitchKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cExpressionAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cExpressionExpressionParserRuleCall_2_0 = (RuleCall)cExpressionAssignment_2.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Keyword cLeftCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cCasesAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cCasesCaseClauseParserRuleCall_5_0 = (RuleCall)cCasesAssignment_5.eContents().get(0);
		private final Group cGroup_6 = (Group)cGroup.eContents().get(6);
		private final Assignment cCasesAssignment_6_0 = (Assignment)cGroup_6.eContents().get(0);
		private final RuleCall cCasesDefaultClauseParserRuleCall_6_0_0 = (RuleCall)cCasesAssignment_6_0.eContents().get(0);
		private final Assignment cCasesAssignment_6_1 = (Assignment)cGroup_6.eContents().get(1);
		private final RuleCall cCasesCaseClauseParserRuleCall_6_1_0 = (RuleCall)cCasesAssignment_6_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_7 = (Keyword)cGroup.eContents().get(7);
		
		///*
		// * All clauses are added to a single list, in order to retain order of the clauses. In particular,
		// * the position of the default clause is
		// */ SwitchStatement <Yield>:
		//	'switch' '(' expression=Expression<In=true,Yield> ')' '{'
		//	cases+=CaseClause<Yield>* (cases+=DefaultClause<Yield> cases+=CaseClause<Yield>*)? '}';
		@Override public ParserRule getRule() { return rule; }
		
		//'switch' '(' expression=Expression<In=true,Yield> ')' '{' cases+=CaseClause<Yield>* (cases+=DefaultClause<Yield>
		//cases+=CaseClause<Yield>*)? '}'
		public Group getGroup() { return cGroup; }
		
		//'switch'
		public Keyword getSwitchKeyword_0() { return cSwitchKeyword_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_2() { return cExpressionAssignment_2; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_2_0() { return cExpressionExpressionParserRuleCall_2_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_4() { return cLeftCurlyBracketKeyword_4; }
		
		//cases+=CaseClause<Yield>*
		public Assignment getCasesAssignment_5() { return cCasesAssignment_5; }
		
		//CaseClause<Yield>
		public RuleCall getCasesCaseClauseParserRuleCall_5_0() { return cCasesCaseClauseParserRuleCall_5_0; }
		
		//(cases+=DefaultClause<Yield> cases+=CaseClause<Yield>*)?
		public Group getGroup_6() { return cGroup_6; }
		
		//cases+=DefaultClause<Yield>
		public Assignment getCasesAssignment_6_0() { return cCasesAssignment_6_0; }
		
		//DefaultClause<Yield>
		public RuleCall getCasesDefaultClauseParserRuleCall_6_0_0() { return cCasesDefaultClauseParserRuleCall_6_0_0; }
		
		//cases+=CaseClause<Yield>*
		public Assignment getCasesAssignment_6_1() { return cCasesAssignment_6_1; }
		
		//CaseClause<Yield>
		public RuleCall getCasesCaseClauseParserRuleCall_6_1_0() { return cCasesCaseClauseParserRuleCall_6_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_7() { return cRightCurlyBracketKeyword_7; }
	}
	public class CaseClauseElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.CaseClause");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cCaseKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExpressionExpressionParserRuleCall_1_0 = (RuleCall)cExpressionAssignment_1.eContents().get(0);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cStatementsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cStatementsStatementParserRuleCall_3_0 = (RuleCall)cStatementsAssignment_3.eContents().get(0);
		
		//CaseClause <Yield>:
		//	'case' expression=Expression<In=true,Yield> ':' statements+=Statement<Yield>*;
		@Override public ParserRule getRule() { return rule; }
		
		//'case' expression=Expression<In=true,Yield> ':' statements+=Statement<Yield>*
		public Group getGroup() { return cGroup; }
		
		//'case'
		public Keyword getCaseKeyword_0() { return cCaseKeyword_0; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_1() { return cExpressionAssignment_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_1_0() { return cExpressionExpressionParserRuleCall_1_0; }
		
		//':'
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//statements+=Statement<Yield>*
		public Assignment getStatementsAssignment_3() { return cStatementsAssignment_3; }
		
		//Statement<Yield>
		public RuleCall getStatementsStatementParserRuleCall_3_0() { return cStatementsStatementParserRuleCall_3_0; }
	}
	public class DefaultClauseElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.DefaultClause");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cDefaultClauseAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cDefaultKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cStatementsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cStatementsStatementParserRuleCall_3_0 = (RuleCall)cStatementsAssignment_3.eContents().get(0);
		
		//DefaultClause <Yield>:
		//	{DefaultClause} 'default' ':' statements+=Statement<Yield>*;
		@Override public ParserRule getRule() { return rule; }
		
		//{DefaultClause} 'default' ':' statements+=Statement<Yield>*
		public Group getGroup() { return cGroup; }
		
		//{DefaultClause}
		public Action getDefaultClauseAction_0() { return cDefaultClauseAction_0; }
		
		//'default'
		public Keyword getDefaultKeyword_1() { return cDefaultKeyword_1; }
		
		//':'
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//statements+=Statement<Yield>*
		public Assignment getStatementsAssignment_3() { return cStatementsAssignment_3; }
		
		//Statement<Yield>
		public RuleCall getStatementsStatementParserRuleCall_3_0() { return cStatementsStatementParserRuleCall_3_0; }
	}
	public class LabelledStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LabelledStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Assignment cNameAssignment_0_0_0 = (Assignment)cGroup_0_0.eContents().get(0);
		private final RuleCall cNameBindingIdentifierParserRuleCall_0_0_0_0 = (RuleCall)cNameAssignment_0_0_0.eContents().get(0);
		private final Keyword cColonKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cStatementAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cStatementStatementParserRuleCall_1_0 = (RuleCall)cStatementAssignment_1.eContents().get(0);
		
		///**
		// * Simplified: [ECM15] distinguishes between BindingIdentifier and LabelIdentifier which are effectively the same
		// */ LabelledStatement <Yield>:
		//	=> (name=BindingIdentifier<Yield> ':') statement=Statement<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> (name=BindingIdentifier<Yield> ':') statement=Statement<Yield>
		public Group getGroup() { return cGroup; }
		
		//=> (name=BindingIdentifier<Yield> ':')
		public Group getGroup_0() { return cGroup_0; }
		
		//name=BindingIdentifier<Yield> ':'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment_0_0_0() { return cNameAssignment_0_0_0; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_0_0_0_0() { return cNameBindingIdentifierParserRuleCall_0_0_0_0; }
		
		//':'
		public Keyword getColonKeyword_0_0_1() { return cColonKeyword_0_0_1; }
		
		//statement=Statement<Yield>
		public Assignment getStatementAssignment_1() { return cStatementAssignment_1; }
		
		//Statement<Yield>
		public RuleCall getStatementStatementParserRuleCall_1_0() { return cStatementStatementParserRuleCall_1_0; }
	}
	public class ThrowStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ThrowStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cThrowKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExpressionExpressionParserRuleCall_1_0 = (RuleCall)cExpressionAssignment_1.eContents().get(0);
		private final RuleCall cSemiParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		//// This is rewritten by the AutomaticSemicolonInjector (see above)
		//ThrowStatement <Yield>:
		//	'throw' expression=Expression<In=true,Yield> Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//'throw' expression=Expression<In=true,Yield> Semi
		public Group getGroup() { return cGroup; }
		
		//'throw'
		public Keyword getThrowKeyword_0() { return cThrowKeyword_0; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_1() { return cExpressionAssignment_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_1_0() { return cExpressionExpressionParserRuleCall_1_0; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_2() { return cSemiParserRuleCall_2; }
	}
	public class TryStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TryStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cTryKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cBlockAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cBlockBlockParserRuleCall_1_0 = (RuleCall)cBlockAssignment_1.eContents().get(0);
		private final Alternatives cAlternatives_2 = (Alternatives)cGroup.eContents().get(2);
		private final Group cGroup_2_0 = (Group)cAlternatives_2.eContents().get(0);
		private final Assignment cCatchAssignment_2_0_0 = (Assignment)cGroup_2_0.eContents().get(0);
		private final RuleCall cCatchCatchBlockParserRuleCall_2_0_0_0 = (RuleCall)cCatchAssignment_2_0_0.eContents().get(0);
		private final Assignment cFinallyAssignment_2_0_1 = (Assignment)cGroup_2_0.eContents().get(1);
		private final RuleCall cFinallyFinallyBlockParserRuleCall_2_0_1_0 = (RuleCall)cFinallyAssignment_2_0_1.eContents().get(0);
		private final Assignment cFinallyAssignment_2_1 = (Assignment)cAlternatives_2.eContents().get(1);
		private final RuleCall cFinallyFinallyBlockParserRuleCall_2_1_0 = (RuleCall)cFinallyAssignment_2_1.eContents().get(0);
		
		//TryStatement <Yield>:
		//	'try' block=Block<Yield> (catch=CatchBlock<Yield> finally=FinallyBlock<Yield>? | finally=FinallyBlock<Yield>);
		@Override public ParserRule getRule() { return rule; }
		
		//'try' block=Block<Yield> (catch=CatchBlock<Yield> finally=FinallyBlock<Yield>? | finally=FinallyBlock<Yield>)
		public Group getGroup() { return cGroup; }
		
		//'try'
		public Keyword getTryKeyword_0() { return cTryKeyword_0; }
		
		//block=Block<Yield>
		public Assignment getBlockAssignment_1() { return cBlockAssignment_1; }
		
		//Block<Yield>
		public RuleCall getBlockBlockParserRuleCall_1_0() { return cBlockBlockParserRuleCall_1_0; }
		
		//catch=CatchBlock<Yield> finally=FinallyBlock<Yield>? | finally=FinallyBlock<Yield>
		public Alternatives getAlternatives_2() { return cAlternatives_2; }
		
		//catch=CatchBlock<Yield> finally=FinallyBlock<Yield>?
		public Group getGroup_2_0() { return cGroup_2_0; }
		
		//catch=CatchBlock<Yield>
		public Assignment getCatchAssignment_2_0_0() { return cCatchAssignment_2_0_0; }
		
		//CatchBlock<Yield>
		public RuleCall getCatchCatchBlockParserRuleCall_2_0_0_0() { return cCatchCatchBlockParserRuleCall_2_0_0_0; }
		
		//finally=FinallyBlock<Yield>?
		public Assignment getFinallyAssignment_2_0_1() { return cFinallyAssignment_2_0_1; }
		
		//FinallyBlock<Yield>
		public RuleCall getFinallyFinallyBlockParserRuleCall_2_0_1_0() { return cFinallyFinallyBlockParserRuleCall_2_0_1_0; }
		
		//finally=FinallyBlock<Yield>
		public Assignment getFinallyAssignment_2_1() { return cFinallyAssignment_2_1; }
		
		//FinallyBlock<Yield>
		public RuleCall getFinallyFinallyBlockParserRuleCall_2_1_0() { return cFinallyFinallyBlockParserRuleCall_2_1_0; }
	}
	public class CatchBlockElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.CatchBlock");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cCatchBlockAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cCatchKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cCatchVariableAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cCatchVariableCatchVariableParserRuleCall_3_0 = (RuleCall)cCatchVariableAssignment_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cBlockAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cBlockBlockParserRuleCall_5_0 = (RuleCall)cBlockAssignment_5.eContents().get(0);
		
		//CatchBlock <Yield>:
		//	{CatchBlock} 'catch' '(' catchVariable=CatchVariable<Yield> ')' block=Block<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//{CatchBlock} 'catch' '(' catchVariable=CatchVariable<Yield> ')' block=Block<Yield>
		public Group getGroup() { return cGroup; }
		
		//{CatchBlock}
		public Action getCatchBlockAction_0() { return cCatchBlockAction_0; }
		
		//'catch'
		public Keyword getCatchKeyword_1() { return cCatchKeyword_1; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }
		
		//catchVariable=CatchVariable<Yield>
		public Assignment getCatchVariableAssignment_3() { return cCatchVariableAssignment_3; }
		
		//CatchVariable<Yield>
		public RuleCall getCatchVariableCatchVariableParserRuleCall_3_0() { return cCatchVariableCatchVariableParserRuleCall_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_4() { return cRightParenthesisKeyword_4; }
		
		//block=Block<Yield>
		public Assignment getBlockAssignment_5() { return cBlockAssignment_5; }
		
		//Block<Yield>
		public RuleCall getBlockBlockParserRuleCall_5_0() { return cBlockBlockParserRuleCall_5_0; }
	}
	public class CatchVariableElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.CatchVariable");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cBindingPatternAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final RuleCall cBindingPatternBindingPatternParserRuleCall_0_0 = (RuleCall)cBindingPatternAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Assignment cNameAssignment_1_0_0 = (Assignment)cGroup_1_0.eContents().get(0);
		private final RuleCall cNameBindingIdentifierParserRuleCall_1_0_0_0 = (RuleCall)cNameAssignment_1_0_0.eContents().get(0);
		private final RuleCall cColonSepDeclaredTypeRefParserRuleCall_1_0_1 = (RuleCall)cGroup_1_0.eContents().get(1);
		private final Group cGroup_2 = (Group)cAlternatives.eContents().get(2);
		private final RuleCall cBogusTypeRefFragmentParserRuleCall_2_0 = (RuleCall)cGroup_2.eContents().get(0);
		private final Assignment cNameAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cNameBindingIdentifierParserRuleCall_2_1_0 = (RuleCall)cNameAssignment_2_1.eContents().get(0);
		
		///**
		// * CatchVariable must not have a type reference, this is tested during validation (to enable better error messages).
		// */ CatchVariable <Yield>:
		//	=> bindingPattern=BindingPattern<Yield> | => (name=BindingIdentifier<Yield> -> ColonSepDeclaredTypeRef) |
		//	BogusTypeRefFragment? name=BindingIdentifier<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> bindingPattern=BindingPattern<Yield> | => (name=BindingIdentifier<Yield> -> ColonSepDeclaredTypeRef) |
		//BogusTypeRefFragment? name=BindingIdentifier<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//=> bindingPattern=BindingPattern<Yield>
		public Assignment getBindingPatternAssignment_0() { return cBindingPatternAssignment_0; }
		
		//BindingPattern<Yield>
		public RuleCall getBindingPatternBindingPatternParserRuleCall_0_0() { return cBindingPatternBindingPatternParserRuleCall_0_0; }
		
		//=> (name=BindingIdentifier<Yield> -> ColonSepDeclaredTypeRef)
		public Group getGroup_1() { return cGroup_1; }
		
		//name=BindingIdentifier<Yield> -> ColonSepDeclaredTypeRef
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment_1_0_0() { return cNameAssignment_1_0_0; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_1_0_0_0() { return cNameBindingIdentifierParserRuleCall_1_0_0_0; }
		
		//-> ColonSepDeclaredTypeRef
		public RuleCall getColonSepDeclaredTypeRefParserRuleCall_1_0_1() { return cColonSepDeclaredTypeRefParserRuleCall_1_0_1; }
		
		//BogusTypeRefFragment? name=BindingIdentifier<Yield>
		public Group getGroup_2() { return cGroup_2; }
		
		//BogusTypeRefFragment?
		public RuleCall getBogusTypeRefFragmentParserRuleCall_2_0() { return cBogusTypeRefFragmentParserRuleCall_2_0; }
		
		//name=BindingIdentifier<Yield>
		public Assignment getNameAssignment_2_1() { return cNameAssignment_2_1; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_2_1_0() { return cNameBindingIdentifierParserRuleCall_2_1_0; }
	}
	public class FinallyBlockElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.FinallyBlock");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cFinallyBlockAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cFinallyKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cBlockAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cBlockBlockParserRuleCall_2_0 = (RuleCall)cBlockAssignment_2.eContents().get(0);
		
		//FinallyBlock <Yield>:
		//	{FinallyBlock} 'finally' block=Block<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//{FinallyBlock} 'finally' block=Block<Yield>
		public Group getGroup() { return cGroup; }
		
		//{FinallyBlock}
		public Action getFinallyBlockAction_0() { return cFinallyBlockAction_0; }
		
		//'finally'
		public Keyword getFinallyKeyword_1() { return cFinallyKeyword_1; }
		
		//block=Block<Yield>
		public Assignment getBlockAssignment_2() { return cBlockAssignment_2; }
		
		//Block<Yield>
		public RuleCall getBlockBlockParserRuleCall_2_0() { return cBlockBlockParserRuleCall_2_0; }
	}
	public class DebuggerStatementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.DebuggerStatement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cDebuggerStatementAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cDebuggerKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final RuleCall cSemiParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		///**
		// * This is rewritten by the AutomaticSemicolonInjector (see above)
		// */ DebuggerStatement:
		//	{DebuggerStatement} 'debugger' Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//{DebuggerStatement} 'debugger' Semi
		public Group getGroup() { return cGroup; }
		
		//{DebuggerStatement}
		public Action getDebuggerStatementAction_0() { return cDebuggerStatementAction_0; }
		
		//'debugger'
		public Keyword getDebuggerKeyword_1() { return cDebuggerKeyword_1; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_2() { return cSemiParserRuleCall_2; }
	}
	public class PrimaryExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PrimaryExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cThisLiteralParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSuperLiteralParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cIdentifierRefParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cJSXFragmentParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cJSXElementParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cImportCallExpressionParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cParameterizedCallExpressionParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		private final RuleCall cLiteralParserRuleCall_7 = (RuleCall)cAlternatives.eContents().get(7);
		private final RuleCall cArrayLiteralParserRuleCall_8 = (RuleCall)cAlternatives.eContents().get(8);
		private final RuleCall cObjectLiteralParserRuleCall_9 = (RuleCall)cAlternatives.eContents().get(9);
		private final RuleCall cParenExpressionParserRuleCall_10 = (RuleCall)cAlternatives.eContents().get(10);
		private final RuleCall cAnnotatedExpressionParserRuleCall_11 = (RuleCall)cAlternatives.eContents().get(11);
		private final RuleCall cFunctionExpressionParserRuleCall_12 = (RuleCall)cAlternatives.eContents().get(12);
		private final RuleCall cAsyncFunctionExpressionParserRuleCall_13 = (RuleCall)cAlternatives.eContents().get(13);
		private final RuleCall cN4ClassExpressionParserRuleCall_14 = (RuleCall)cAlternatives.eContents().get(14);
		private final RuleCall cTemplateLiteralParserRuleCall_15 = (RuleCall)cAlternatives.eContents().get(15);
		
		//// ****************************************************************************************************
		//// [ECM11] A.3 Expressions (p. 218)
		//// ****************************************************************************************************
		//// Primary expressions ([ECM11] 11.1)
		//PrimaryExpression <Yield Expression:
		//	ThisLiteral
		//	| SuperLiteral
		//	| IdentifierRef<Yield> | JSXFragment /* see JSX  */
		//	| JSXElement /* see JSX  */
		//	| ImportCallExpression<Yield> | ParameterizedCallExpression<Yield> | Literal
		//	| ArrayLiteral<Yield> | ObjectLiteral<Yield> | ParenExpression<Yield> | AnnotatedExpression<Yield> |
		//	FunctionExpression
		//	| AsyncFunctionExpression
		//	| N4ClassExpression<Yield> | TemplateLiteral<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//ThisLiteral | SuperLiteral | IdentifierRef<Yield> | JSXFragment /* see JSX  */ | JSXElement /* see JSX  */ |
		//ImportCallExpression<Yield> | ParameterizedCallExpression<Yield> | Literal | ArrayLiteral<Yield> | ObjectLiteral<Yield>
		//| ParenExpression<Yield> | AnnotatedExpression<Yield> | FunctionExpression | AsyncFunctionExpression |
		//N4ClassExpression<Yield> | TemplateLiteral<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ThisLiteral
		public RuleCall getThisLiteralParserRuleCall_0() { return cThisLiteralParserRuleCall_0; }
		
		//SuperLiteral
		public RuleCall getSuperLiteralParserRuleCall_1() { return cSuperLiteralParserRuleCall_1; }
		
		//IdentifierRef<Yield>
		public RuleCall getIdentifierRefParserRuleCall_2() { return cIdentifierRefParserRuleCall_2; }
		
		//JSXFragment
		public RuleCall getJSXFragmentParserRuleCall_3() { return cJSXFragmentParserRuleCall_3; }
		
		//JSXElement
		public RuleCall getJSXElementParserRuleCall_4() { return cJSXElementParserRuleCall_4; }
		
		//ImportCallExpression<Yield>
		public RuleCall getImportCallExpressionParserRuleCall_5() { return cImportCallExpressionParserRuleCall_5; }
		
		//ParameterizedCallExpression<Yield>
		public RuleCall getParameterizedCallExpressionParserRuleCall_6() { return cParameterizedCallExpressionParserRuleCall_6; }
		
		//Literal
		public RuleCall getLiteralParserRuleCall_7() { return cLiteralParserRuleCall_7; }
		
		//ArrayLiteral<Yield>
		public RuleCall getArrayLiteralParserRuleCall_8() { return cArrayLiteralParserRuleCall_8; }
		
		//ObjectLiteral<Yield>
		public RuleCall getObjectLiteralParserRuleCall_9() { return cObjectLiteralParserRuleCall_9; }
		
		//ParenExpression<Yield>
		public RuleCall getParenExpressionParserRuleCall_10() { return cParenExpressionParserRuleCall_10; }
		
		//AnnotatedExpression<Yield>
		public RuleCall getAnnotatedExpressionParserRuleCall_11() { return cAnnotatedExpressionParserRuleCall_11; }
		
		//FunctionExpression
		public RuleCall getFunctionExpressionParserRuleCall_12() { return cFunctionExpressionParserRuleCall_12; }
		
		//AsyncFunctionExpression
		public RuleCall getAsyncFunctionExpressionParserRuleCall_13() { return cAsyncFunctionExpressionParserRuleCall_13; }
		
		//N4ClassExpression<Yield>
		public RuleCall getN4ClassExpressionParserRuleCall_14() { return cN4ClassExpressionParserRuleCall_14; }
		
		//TemplateLiteral<Yield>
		public RuleCall getTemplateLiteralParserRuleCall_15() { return cTemplateLiteralParserRuleCall_15; }
	}
	public class ParenExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ParenExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExpressionExpressionParserRuleCall_1_0 = (RuleCall)cExpressionAssignment_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//ParenExpression <Yield>:
		//	'(' expression=Expression<In=true,Yield> ')';
		@Override public ParserRule getRule() { return rule; }
		
		//'(' expression=Expression<In=true,Yield> ')'
		public Group getGroup() { return cGroup; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_0() { return cLeftParenthesisKeyword_0; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_1() { return cExpressionAssignment_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_1_0() { return cExpressionExpressionParserRuleCall_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2() { return cRightParenthesisKeyword_2; }
	}
	public class IdentifierRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.IdentifierRef");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cIdAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final CrossReference cIdIdentifiableElementCrossReference_0_0 = (CrossReference)cIdAssignment_0.eContents().get(0);
		private final RuleCall cIdIdentifiableElementBindingIdentifierParserRuleCall_0_0_1 = (RuleCall)cIdIdentifiableElementCrossReference_0_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Action cVersionedIdentifierRefAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Assignment cIdAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final CrossReference cIdIdentifiableElementCrossReference_1_1_0 = (CrossReference)cIdAssignment_1_1.eContents().get(0);
		private final RuleCall cIdIdentifiableElementBindingIdentifierParserRuleCall_1_1_0_1 = (RuleCall)cIdIdentifiableElementCrossReference_1_1_0.eContents().get(1);
		private final RuleCall cVersionRequestParserRuleCall_1_2 = (RuleCall)cGroup_1.eContents().get(2);
		
		//IdentifierRef <Yield>:
		//	id=[types::IdentifiableElement|BindingIdentifier<Yield>] | {VersionedIdentifierRef}
		//	id=[types::IdentifiableElement|BindingIdentifier<Yield>] VersionRequest;
		@Override public ParserRule getRule() { return rule; }
		
		//id=[types::IdentifiableElement|BindingIdentifier<Yield>] | {VersionedIdentifierRef}
		//id=[types::IdentifiableElement|BindingIdentifier<Yield>] VersionRequest
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//id=[types::IdentifiableElement|BindingIdentifier<Yield>]
		public Assignment getIdAssignment_0() { return cIdAssignment_0; }
		
		//[types::IdentifiableElement|BindingIdentifier<Yield>]
		public CrossReference getIdIdentifiableElementCrossReference_0_0() { return cIdIdentifiableElementCrossReference_0_0; }
		
		//BindingIdentifier<Yield>
		public RuleCall getIdIdentifiableElementBindingIdentifierParserRuleCall_0_0_1() { return cIdIdentifiableElementBindingIdentifierParserRuleCall_0_0_1; }
		
		//{VersionedIdentifierRef} id=[types::IdentifiableElement|BindingIdentifier<Yield>] VersionRequest
		public Group getGroup_1() { return cGroup_1; }
		
		//{VersionedIdentifierRef}
		public Action getVersionedIdentifierRefAction_1_0() { return cVersionedIdentifierRefAction_1_0; }
		
		//id=[types::IdentifiableElement|BindingIdentifier<Yield>]
		public Assignment getIdAssignment_1_1() { return cIdAssignment_1_1; }
		
		//[types::IdentifiableElement|BindingIdentifier<Yield>]
		public CrossReference getIdIdentifiableElementCrossReference_1_1_0() { return cIdIdentifiableElementCrossReference_1_1_0; }
		
		//BindingIdentifier<Yield>
		public RuleCall getIdIdentifiableElementBindingIdentifierParserRuleCall_1_1_0_1() { return cIdIdentifiableElementBindingIdentifierParserRuleCall_1_1_0_1; }
		
		//VersionRequest
		public RuleCall getVersionRequestParserRuleCall_1_2() { return cVersionRequestParserRuleCall_1_2; }
	}
	public class SuperLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.SuperLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSuperLiteralAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cSuperKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//SuperLiteral:
		//	{SuperLiteral} 'super';
		@Override public ParserRule getRule() { return rule; }
		
		//{SuperLiteral} 'super'
		public Group getGroup() { return cGroup; }
		
		//{SuperLiteral}
		public Action getSuperLiteralAction_0() { return cSuperLiteralAction_0; }
		
		//'super'
		public Keyword getSuperKeyword_1() { return cSuperKeyword_1; }
	}
	public class ThisLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ThisLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cThisLiteralAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cThisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//ThisLiteral:
		//	{ThisLiteral} 'this';
		@Override public ParserRule getRule() { return rule; }
		
		//{ThisLiteral} 'this'
		public Group getGroup() { return cGroup; }
		
		//{ThisLiteral}
		public Action getThisLiteralAction_0() { return cThisLiteralAction_0; }
		
		//'this'
		public Keyword getThisKeyword_1() { return cThisKeyword_1; }
	}
	public class ArrayLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ArrayLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cArrayLiteralAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cElementsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cElementsArrayPaddingParserRuleCall_2_0 = (RuleCall)cElementsAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Assignment cElementsAssignment_3_0 = (Assignment)cGroup_3.eContents().get(0);
		private final RuleCall cElementsArrayElementParserRuleCall_3_0_0 = (RuleCall)cElementsAssignment_3_0.eContents().get(0);
		private final Group cGroup_3_1 = (Group)cGroup_3.eContents().get(1);
		private final Keyword cCommaKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Assignment cElementsAssignment_3_1_1 = (Assignment)cGroup_3_1.eContents().get(1);
		private final RuleCall cElementsArrayPaddingParserRuleCall_3_1_1_0 = (RuleCall)cElementsAssignment_3_1_1.eContents().get(0);
		private final Assignment cElementsAssignment_3_1_2 = (Assignment)cGroup_3_1.eContents().get(2);
		private final RuleCall cElementsArrayElementParserRuleCall_3_1_2_0 = (RuleCall)cElementsAssignment_3_1_2.eContents().get(0);
		private final Group cGroup_3_2 = (Group)cGroup_3.eContents().get(2);
		private final Assignment cTrailingCommaAssignment_3_2_0 = (Assignment)cGroup_3_2.eContents().get(0);
		private final Keyword cTrailingCommaCommaKeyword_3_2_0_0 = (Keyword)cTrailingCommaAssignment_3_2_0.eContents().get(0);
		private final Assignment cElementsAssignment_3_2_1 = (Assignment)cGroup_3_2.eContents().get(1);
		private final RuleCall cElementsArrayPaddingParserRuleCall_3_2_1_0 = (RuleCall)cElementsAssignment_3_2_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		///**
		// * As described in the spec, array literals may use elisions to influence the
		// * index of expressions in the array.
		// * This is achieved by special ArrayElements, called ArrayPadding, which are
		// * represented by a ',' in the concrete syntax.
		// *
		// * 	ArrayLiteral :
		// * 		[ Elision/opt ]
		// * 		[ ElementList ]
		// * 		[ ElementList , Elision/opt ]
		// * 	ElementList :
		// * 		Elision/opt AssignmentExpression
		// * 		ElementList , Elision/opt AssignmentExpression
		// * 	Elision :
		// * 		,
		// * 		Elision ,
		// *
		// */ ArrayLiteral <Yield>:
		//	{ArrayLiteral} '['
		//	elements+=ArrayPadding* (elements+=ArrayElement<Yield> (',' elements+=ArrayPadding* elements+=ArrayElement<Yield>)*
		//	(trailingComma?=',' elements+=ArrayPadding*)?)?
		//	']';
		@Override public ParserRule getRule() { return rule; }
		
		//{ArrayLiteral} '[' elements+=ArrayPadding* (elements+=ArrayElement<Yield> (',' elements+=ArrayPadding*
		//elements+=ArrayElement<Yield>)* (trailingComma?=',' elements+=ArrayPadding*)?)? ']'
		public Group getGroup() { return cGroup; }
		
		//{ArrayLiteral}
		public Action getArrayLiteralAction_0() { return cArrayLiteralAction_0; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_1() { return cLeftSquareBracketKeyword_1; }
		
		//elements+=ArrayPadding*
		public Assignment getElementsAssignment_2() { return cElementsAssignment_2; }
		
		//ArrayPadding
		public RuleCall getElementsArrayPaddingParserRuleCall_2_0() { return cElementsArrayPaddingParserRuleCall_2_0; }
		
		//(elements+=ArrayElement<Yield> (',' elements+=ArrayPadding* elements+=ArrayElement<Yield>)* (trailingComma?=','
		//elements+=ArrayPadding*)?)?
		public Group getGroup_3() { return cGroup_3; }
		
		//elements+=ArrayElement<Yield>
		public Assignment getElementsAssignment_3_0() { return cElementsAssignment_3_0; }
		
		//ArrayElement<Yield>
		public RuleCall getElementsArrayElementParserRuleCall_3_0_0() { return cElementsArrayElementParserRuleCall_3_0_0; }
		
		//(',' elements+=ArrayPadding* elements+=ArrayElement<Yield>)*
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//','
		public Keyword getCommaKeyword_3_1_0() { return cCommaKeyword_3_1_0; }
		
		//elements+=ArrayPadding*
		public Assignment getElementsAssignment_3_1_1() { return cElementsAssignment_3_1_1; }
		
		//ArrayPadding
		public RuleCall getElementsArrayPaddingParserRuleCall_3_1_1_0() { return cElementsArrayPaddingParserRuleCall_3_1_1_0; }
		
		//elements+=ArrayElement<Yield>
		public Assignment getElementsAssignment_3_1_2() { return cElementsAssignment_3_1_2; }
		
		//ArrayElement<Yield>
		public RuleCall getElementsArrayElementParserRuleCall_3_1_2_0() { return cElementsArrayElementParserRuleCall_3_1_2_0; }
		
		//(trailingComma?=',' elements+=ArrayPadding*)?
		public Group getGroup_3_2() { return cGroup_3_2; }
		
		//trailingComma?=','
		public Assignment getTrailingCommaAssignment_3_2_0() { return cTrailingCommaAssignment_3_2_0; }
		
		//','
		public Keyword getTrailingCommaCommaKeyword_3_2_0_0() { return cTrailingCommaCommaKeyword_3_2_0_0; }
		
		//elements+=ArrayPadding*
		public Assignment getElementsAssignment_3_2_1() { return cElementsAssignment_3_2_1; }
		
		//ArrayPadding
		public RuleCall getElementsArrayPaddingParserRuleCall_3_2_1_0() { return cElementsArrayPaddingParserRuleCall_3_2_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_4() { return cRightSquareBracketKeyword_4; }
	}
	public class ArrayPaddingElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ArrayPadding");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cArrayPaddingAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cCommaKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		///**
		// * This array element is used to pad the remaining elements, e.g. to get the
		// * length and index right
		// */ ArrayPadding ArrayElement:
		//	{ArrayPadding} ',';
		@Override public ParserRule getRule() { return rule; }
		
		//{ArrayPadding} ','
		public Group getGroup() { return cGroup; }
		
		//{ArrayPadding}
		public Action getArrayPaddingAction_0() { return cArrayPaddingAction_0; }
		
		//','
		public Keyword getCommaKeyword_1() { return cCommaKeyword_1; }
	}
	public class ArrayElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ArrayElement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cArrayElementAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cSpreadAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cSpreadFullStopFullStopFullStopKeyword_1_0 = (Keyword)cSpreadAssignment_1.eContents().get(0);
		private final Assignment cExpressionAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_2_0 = (RuleCall)cExpressionAssignment_2.eContents().get(0);
		
		//ArrayElement <Yield>:
		//	{ArrayElement} spread?='...'? expression=AssignmentExpression<In=true,Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//{ArrayElement} spread?='...'? expression=AssignmentExpression<In=true,Yield>
		public Group getGroup() { return cGroup; }
		
		//{ArrayElement}
		public Action getArrayElementAction_0() { return cArrayElementAction_0; }
		
		//spread?='...'?
		public Assignment getSpreadAssignment_1() { return cSpreadAssignment_1; }
		
		//'...'
		public Keyword getSpreadFullStopFullStopFullStopKeyword_1_0() { return cSpreadFullStopFullStopFullStopKeyword_1_0; }
		
		//expression=AssignmentExpression<In=true,Yield>
		public Assignment getExpressionAssignment_2() { return cExpressionAssignment_2; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_2_0() { return cExpressionAssignmentExpressionParserRuleCall_2_0; }
	}
	public class ObjectLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ObjectLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cObjectLiteralAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Assignment cPropertyAssignmentsAssignment_2_0 = (Assignment)cGroup_2.eContents().get(0);
		private final RuleCall cPropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0 = (RuleCall)cPropertyAssignmentsAssignment_2_0.eContents().get(0);
		private final Group cGroup_2_1 = (Group)cGroup_2.eContents().get(1);
		private final Keyword cCommaKeyword_2_1_0 = (Keyword)cGroup_2_1.eContents().get(0);
		private final Assignment cPropertyAssignmentsAssignment_2_1_1 = (Assignment)cGroup_2_1.eContents().get(1);
		private final RuleCall cPropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0 = (RuleCall)cPropertyAssignmentsAssignment_2_1_1.eContents().get(0);
		private final Keyword cCommaKeyword_2_2 = (Keyword)cGroup_2.eContents().get(2);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//ObjectLiteral <Yield>:
		//	{ObjectLiteral}
		//	'{' (propertyAssignments+=PropertyAssignment<Yield> (',' propertyAssignments+=PropertyAssignment<Yield>)* ','?)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{ObjectLiteral} '{' (propertyAssignments+=PropertyAssignment<Yield> (','
		//propertyAssignments+=PropertyAssignment<Yield>)* ','?)? '}'
		public Group getGroup() { return cGroup; }
		
		//{ObjectLiteral}
		public Action getObjectLiteralAction_0() { return cObjectLiteralAction_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//(propertyAssignments+=PropertyAssignment<Yield> (',' propertyAssignments+=PropertyAssignment<Yield>)* ','?)?
		public Group getGroup_2() { return cGroup_2; }
		
		//propertyAssignments+=PropertyAssignment<Yield>
		public Assignment getPropertyAssignmentsAssignment_2_0() { return cPropertyAssignmentsAssignment_2_0; }
		
		//PropertyAssignment<Yield>
		public RuleCall getPropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0() { return cPropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0; }
		
		//(',' propertyAssignments+=PropertyAssignment<Yield>)*
		public Group getGroup_2_1() { return cGroup_2_1; }
		
		//','
		public Keyword getCommaKeyword_2_1_0() { return cCommaKeyword_2_1_0; }
		
		//propertyAssignments+=PropertyAssignment<Yield>
		public Assignment getPropertyAssignmentsAssignment_2_1_1() { return cPropertyAssignmentsAssignment_2_1_1; }
		
		//PropertyAssignment<Yield>
		public RuleCall getPropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0() { return cPropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0; }
		
		//','?
		public Keyword getCommaKeyword_2_2() { return cCommaKeyword_2_2; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class PropertyAssignmentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PropertyAssignment");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cAnnotatedPropertyAssignmentParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cPropertyNameValuePairParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cPropertyGetterDeclarationParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cPropertySetterDeclarationParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cPropertyMethodDeclarationParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cPropertyNameValuePairSingleNameParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		
		//PropertyAssignment <Yield>:
		//	AnnotatedPropertyAssignment<Yield> | PropertyNameValuePair<Yield> | PropertyGetterDeclaration<Yield> |
		//	PropertySetterDeclaration<Yield> | PropertyMethodDeclaration<Yield> | PropertyNameValuePairSingleName<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//AnnotatedPropertyAssignment<Yield> | PropertyNameValuePair<Yield> | PropertyGetterDeclaration<Yield> |
		//PropertySetterDeclaration<Yield> | PropertyMethodDeclaration<Yield> | PropertyNameValuePairSingleName<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//AnnotatedPropertyAssignment<Yield>
		public RuleCall getAnnotatedPropertyAssignmentParserRuleCall_0() { return cAnnotatedPropertyAssignmentParserRuleCall_0; }
		
		//PropertyNameValuePair<Yield>
		public RuleCall getPropertyNameValuePairParserRuleCall_1() { return cPropertyNameValuePairParserRuleCall_1; }
		
		//PropertyGetterDeclaration<Yield>
		public RuleCall getPropertyGetterDeclarationParserRuleCall_2() { return cPropertyGetterDeclarationParserRuleCall_2; }
		
		//PropertySetterDeclaration<Yield>
		public RuleCall getPropertySetterDeclarationParserRuleCall_3() { return cPropertySetterDeclarationParserRuleCall_3; }
		
		//PropertyMethodDeclaration<Yield>
		public RuleCall getPropertyMethodDeclarationParserRuleCall_4() { return cPropertyMethodDeclarationParserRuleCall_4; }
		
		//PropertyNameValuePairSingleName<Yield>
		public RuleCall getPropertyNameValuePairSingleNameParserRuleCall_5() { return cPropertyNameValuePairSingleNameParserRuleCall_5; }
	}
	public class AnnotatedPropertyAssignmentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotatedPropertyAssignment");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cPropertyAssignmentAnnotationListParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Group cGroup_1_0_0_0 = (Group)cGroup_1_0_0.eContents().get(0);
		private final Action cPropertyNameValuePairAnnotationListAction_1_0_0_0_0 = (Action)cGroup_1_0_0_0.eContents().get(0);
		private final Assignment cDeclaredTypeRefAssignment_1_0_0_0_1 = (Assignment)cGroup_1_0_0_0.eContents().get(1);
		private final RuleCall cDeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0 = (RuleCall)cDeclaredTypeRefAssignment_1_0_0_0_1.eContents().get(0);
		private final Assignment cDeclaredNameAssignment_1_0_0_0_2 = (Assignment)cGroup_1_0_0_0.eContents().get(2);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0 = (RuleCall)cDeclaredNameAssignment_1_0_0_0_2.eContents().get(0);
		private final Keyword cColonKeyword_1_0_0_0_3 = (Keyword)cGroup_1_0_0_0.eContents().get(3);
		private final Assignment cExpressionAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_0_1_0 = (RuleCall)cExpressionAssignment_1_0_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Group cGroup_1_1_0 = (Group)cGroup_1_1.eContents().get(0);
		private final Group cGroup_1_1_0_0 = (Group)cGroup_1_1_0.eContents().get(0);
		private final Action cPropertyGetterDeclarationAnnotationListAction_1_1_0_0_0 = (Action)cGroup_1_1_0_0.eContents().get(0);
		private final RuleCall cGetterHeaderParserRuleCall_1_1_0_0_1 = (RuleCall)cGroup_1_1_0_0.eContents().get(1);
		private final Assignment cBodyAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cBodyBlockParserRuleCall_1_1_1_0 = (RuleCall)cBodyAssignment_1_1_1.eContents().get(0);
		private final Group cGroup_1_2 = (Group)cAlternatives_1.eContents().get(2);
		private final Group cGroup_1_2_0 = (Group)cGroup_1_2.eContents().get(0);
		private final Group cGroup_1_2_0_0 = (Group)cGroup_1_2_0.eContents().get(0);
		private final Action cPropertySetterDeclarationAnnotationListAction_1_2_0_0_0 = (Action)cGroup_1_2_0_0.eContents().get(0);
		private final Keyword cSetKeyword_1_2_0_0_1 = (Keyword)cGroup_1_2_0_0.eContents().get(1);
		private final Assignment cDeclaredNameAssignment_1_2_0_0_2 = (Assignment)cGroup_1_2_0_0.eContents().get(2);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0 = (RuleCall)cDeclaredNameAssignment_1_2_0_0_2.eContents().get(0);
		private final Assignment cDeclaredOptionalAssignment_1_2_1 = (Assignment)cGroup_1_2.eContents().get(1);
		private final Keyword cDeclaredOptionalQuestionMarkKeyword_1_2_1_0 = (Keyword)cDeclaredOptionalAssignment_1_2_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1_2_2 = (Keyword)cGroup_1_2.eContents().get(2);
		private final Assignment cFparAssignment_1_2_3 = (Assignment)cGroup_1_2.eContents().get(3);
		private final RuleCall cFparFormalParameterParserRuleCall_1_2_3_0 = (RuleCall)cFparAssignment_1_2_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_1_2_4 = (Keyword)cGroup_1_2.eContents().get(4);
		private final Assignment cBodyAssignment_1_2_5 = (Assignment)cGroup_1_2.eContents().get(5);
		private final RuleCall cBodyBlockParserRuleCall_1_2_5_0 = (RuleCall)cBodyAssignment_1_2_5.eContents().get(0);
		private final Group cGroup_1_3 = (Group)cAlternatives_1.eContents().get(3);
		private final Group cGroup_1_3_0 = (Group)cGroup_1_3.eContents().get(0);
		private final Group cGroup_1_3_0_0 = (Group)cGroup_1_3_0.eContents().get(0);
		private final Action cPropertyMethodDeclarationAnnotationListAction_1_3_0_0_0 = (Action)cGroup_1_3_0_0.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_1_3_0_0_1 = (RuleCall)cGroup_1_3_0_0.eContents().get(1);
		private final Assignment cReturnTypeRefAssignment_1_3_0_0_2 = (Assignment)cGroup_1_3_0_0.eContents().get(2);
		private final RuleCall cReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0 = (RuleCall)cReturnTypeRefAssignment_1_3_0_0_2.eContents().get(0);
		private final Alternatives cAlternatives_1_3_0_0_3 = (Alternatives)cGroup_1_3_0_0.eContents().get(3);
		private final Group cGroup_1_3_0_0_3_0 = (Group)cAlternatives_1_3_0_0_3.eContents().get(0);
		private final Assignment cGeneratorAssignment_1_3_0_0_3_0_0 = (Assignment)cGroup_1_3_0_0_3_0.eContents().get(0);
		private final Keyword cGeneratorAsteriskKeyword_1_3_0_0_3_0_0_0 = (Keyword)cGeneratorAssignment_1_3_0_0_3_0_0.eContents().get(0);
		private final Assignment cDeclaredNameAssignment_1_3_0_0_3_0_1 = (Assignment)cGroup_1_3_0_0_3_0.eContents().get(1);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0 = (RuleCall)cDeclaredNameAssignment_1_3_0_0_3_0_1.eContents().get(0);
		private final RuleCall cMethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2 = (RuleCall)cGroup_1_3_0_0_3_0.eContents().get(2);
		private final Group cGroup_1_3_0_0_3_1 = (Group)cAlternatives_1_3_0_0_3.eContents().get(1);
		private final Assignment cDeclaredNameAssignment_1_3_0_0_3_1_0 = (Assignment)cGroup_1_3_0_0_3_1.eContents().get(0);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0 = (RuleCall)cDeclaredNameAssignment_1_3_0_0_3_1_0.eContents().get(0);
		private final RuleCall cMethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1 = (RuleCall)cGroup_1_3_0_0_3_1.eContents().get(1);
		private final Keyword cSemicolonKeyword_1_3_1 = (Keyword)cGroup_1_3.eContents().get(1);
		private final Group cGroup_1_4 = (Group)cAlternatives_1.eContents().get(4);
		private final Action cPropertyNameValuePairSingleNameAnnotationListAction_1_4_0 = (Action)cGroup_1_4.eContents().get(0);
		private final Assignment cDeclaredTypeRefAssignment_1_4_1 = (Assignment)cGroup_1_4.eContents().get(1);
		private final RuleCall cDeclaredTypeRefTypeRefParserRuleCall_1_4_1_0 = (RuleCall)cDeclaredTypeRefAssignment_1_4_1.eContents().get(0);
		private final Assignment cIdentifierRefAssignment_1_4_2 = (Assignment)cGroup_1_4.eContents().get(2);
		private final RuleCall cIdentifierRefIdentifierRefParserRuleCall_1_4_2_0 = (RuleCall)cIdentifierRefAssignment_1_4_2.eContents().get(0);
		private final Group cGroup_1_4_3 = (Group)cGroup_1_4.eContents().get(3);
		private final Keyword cEqualsSignKeyword_1_4_3_0 = (Keyword)cGroup_1_4_3.eContents().get(0);
		private final Assignment cExpressionAssignment_1_4_3_1 = (Assignment)cGroup_1_4_3.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0 = (RuleCall)cExpressionAssignment_1_4_3_1.eContents().get(0);
		
		//AnnotatedPropertyAssignment <Yield PropertyAssignment:
		//	PropertyAssignmentAnnotationList (
		//	// TODO extract property header into an own instance to defer the object instantiation
		//	=> ({PropertyNameValuePair.annotationList=current} declaredTypeRef=TypeRefWithModifiers?
		//	declaredName=LiteralOrComputedPropertyName<Yield> ':') expression=AssignmentExpression<In=true,Yield> | =>
		//	({PropertyGetterDeclaration.annotationList=current} GetterHeader<Yield>) body=Block<Yield=false> | =>
		//	({PropertySetterDeclaration.annotationList=current}
		//	'set' -> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')'
		//	body=Block<Yield=false> | => ({PropertyMethodDeclaration.annotationList=current} TypeVariables?
		//	returnTypeRef=TypeRefWithModifiers? (generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> ->
		//	MethodParamsAndBody <Generator=true> | declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody
		//	<Generator=false>)) ';'?
		//	| {PropertyNameValuePairSingleName.annotationList=current} declaredTypeRef=TypeRef? identifierRef=IdentifierRef<Yield>
		//	('=' expression=AssignmentExpression<In=true,Yield>)?);
		@Override public ParserRule getRule() { return rule; }
		
		//PropertyAssignmentAnnotationList ( // TODO extract property header into an own instance to defer the object instantiation
		//=> ({PropertyNameValuePair.annotationList=current} declaredTypeRef=TypeRefWithModifiers?
		//declaredName=LiteralOrComputedPropertyName<Yield> ':') expression=AssignmentExpression<In=true,Yield> | =>
		//({PropertyGetterDeclaration.annotationList=current} GetterHeader<Yield>) body=Block<Yield=false> | =>
		//({PropertySetterDeclaration.annotationList=current} 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>)
		//declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')' body=Block<Yield=false> | =>
		//({PropertyMethodDeclaration.annotationList=current} TypeVariables? returnTypeRef=TypeRefWithModifiers? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>)) ';'? |
		//{PropertyNameValuePairSingleName.annotationList=current} declaredTypeRef=TypeRef? identifierRef=IdentifierRef<Yield>
		//('=' expression=AssignmentExpression<In=true,Yield>)?)
		public Group getGroup() { return cGroup; }
		
		//PropertyAssignmentAnnotationList
		public RuleCall getPropertyAssignmentAnnotationListParserRuleCall_0() { return cPropertyAssignmentAnnotationListParserRuleCall_0; }
		
		//=> ({PropertyNameValuePair.annotationList=current} declaredTypeRef=TypeRefWithModifiers?
		//declaredName=LiteralOrComputedPropertyName<Yield> ':') expression=AssignmentExpression<In=true,Yield> | =>
		//({PropertyGetterDeclaration.annotationList=current} GetterHeader<Yield>) body=Block<Yield=false> | =>
		//({PropertySetterDeclaration.annotationList=current} 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>)
		//declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')' body=Block<Yield=false> | =>
		//({PropertyMethodDeclaration.annotationList=current} TypeVariables? returnTypeRef=TypeRefWithModifiers? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>)) ';'? |
		//{PropertyNameValuePairSingleName.annotationList=current} declaredTypeRef=TypeRef? identifierRef=IdentifierRef<Yield>
		//('=' expression=AssignmentExpression<In=true,Yield>)?
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//// TODO extract property header into an own instance to defer the object instantiation
		//=> ({PropertyNameValuePair.annotationList=current} declaredTypeRef=TypeRefWithModifiers?
		//declaredName=LiteralOrComputedPropertyName<Yield> ':') expression=AssignmentExpression<In=true,Yield>
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//// TODO extract property header into an own instance to defer the object instantiation
		//=> ({PropertyNameValuePair.annotationList=current} declaredTypeRef=TypeRefWithModifiers?
		//declaredName=LiteralOrComputedPropertyName<Yield> ':')
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{PropertyNameValuePair.annotationList=current} declaredTypeRef=TypeRefWithModifiers?
		//declaredName=LiteralOrComputedPropertyName<Yield> ':'
		public Group getGroup_1_0_0_0() { return cGroup_1_0_0_0; }
		
		//{PropertyNameValuePair.annotationList=current}
		public Action getPropertyNameValuePairAnnotationListAction_1_0_0_0_0() { return cPropertyNameValuePairAnnotationListAction_1_0_0_0_0; }
		
		//declaredTypeRef=TypeRefWithModifiers?
		public Assignment getDeclaredTypeRefAssignment_1_0_0_0_1() { return cDeclaredTypeRefAssignment_1_0_0_0_1; }
		
		//TypeRefWithModifiers
		public RuleCall getDeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0() { return cDeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_1_0_0_0_2() { return cDeclaredNameAssignment_1_0_0_0_2; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0; }
		
		//':'
		public Keyword getColonKeyword_1_0_0_0_3() { return cColonKeyword_1_0_0_0_3; }
		
		//expression=AssignmentExpression<In=true,Yield>
		public Assignment getExpressionAssignment_1_0_1() { return cExpressionAssignment_1_0_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_0_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_0_1_0; }
		
		//=> ({PropertyGetterDeclaration.annotationList=current} GetterHeader<Yield>) body=Block<Yield=false>
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//=> ({PropertyGetterDeclaration.annotationList=current} GetterHeader<Yield>)
		public Group getGroup_1_1_0() { return cGroup_1_1_0; }
		
		//{PropertyGetterDeclaration.annotationList=current} GetterHeader<Yield>
		public Group getGroup_1_1_0_0() { return cGroup_1_1_0_0; }
		
		//{PropertyGetterDeclaration.annotationList=current}
		public Action getPropertyGetterDeclarationAnnotationListAction_1_1_0_0_0() { return cPropertyGetterDeclarationAnnotationListAction_1_1_0_0_0; }
		
		//GetterHeader<Yield>
		public RuleCall getGetterHeaderParserRuleCall_1_1_0_0_1() { return cGetterHeaderParserRuleCall_1_1_0_0_1; }
		
		//body=Block<Yield=false>
		public Assignment getBodyAssignment_1_1_1() { return cBodyAssignment_1_1_1; }
		
		//Block<Yield=false>
		public RuleCall getBodyBlockParserRuleCall_1_1_1_0() { return cBodyBlockParserRuleCall_1_1_1_0; }
		
		//=> ({PropertySetterDeclaration.annotationList=current} 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>)
		//declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')' body=Block<Yield=false>
		public Group getGroup_1_2() { return cGroup_1_2; }
		
		//=> ({PropertySetterDeclaration.annotationList=current} 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>)
		public Group getGroup_1_2_0() { return cGroup_1_2_0; }
		
		//{PropertySetterDeclaration.annotationList=current} 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>
		public Group getGroup_1_2_0_0() { return cGroup_1_2_0_0; }
		
		//{PropertySetterDeclaration.annotationList=current}
		public Action getPropertySetterDeclarationAnnotationListAction_1_2_0_0_0() { return cPropertySetterDeclarationAnnotationListAction_1_2_0_0_0; }
		
		//'set'
		public Keyword getSetKeyword_1_2_0_0_1() { return cSetKeyword_1_2_0_0_1; }
		
		//-> declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_1_2_0_0_2() { return cDeclaredNameAssignment_1_2_0_0_2; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0; }
		
		//declaredOptional?='?'?
		public Assignment getDeclaredOptionalAssignment_1_2_1() { return cDeclaredOptionalAssignment_1_2_1; }
		
		//'?'
		public Keyword getDeclaredOptionalQuestionMarkKeyword_1_2_1_0() { return cDeclaredOptionalQuestionMarkKeyword_1_2_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1_2_2() { return cLeftParenthesisKeyword_1_2_2; }
		
		//fpar=FormalParameter<Yield>
		public Assignment getFparAssignment_1_2_3() { return cFparAssignment_1_2_3; }
		
		//FormalParameter<Yield>
		public RuleCall getFparFormalParameterParserRuleCall_1_2_3_0() { return cFparFormalParameterParserRuleCall_1_2_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_1_2_4() { return cRightParenthesisKeyword_1_2_4; }
		
		//body=Block<Yield=false>
		public Assignment getBodyAssignment_1_2_5() { return cBodyAssignment_1_2_5; }
		
		//Block<Yield=false>
		public RuleCall getBodyBlockParserRuleCall_1_2_5_0() { return cBodyBlockParserRuleCall_1_2_5_0; }
		
		//=> ({PropertyMethodDeclaration.annotationList=current} TypeVariables? returnTypeRef=TypeRefWithModifiers?
		//(generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>)) ';'?
		public Group getGroup_1_3() { return cGroup_1_3; }
		
		//=> ({PropertyMethodDeclaration.annotationList=current} TypeVariables? returnTypeRef=TypeRefWithModifiers?
		//(generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>))
		public Group getGroup_1_3_0() { return cGroup_1_3_0; }
		
		//{PropertyMethodDeclaration.annotationList=current} TypeVariables? returnTypeRef=TypeRefWithModifiers? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>)
		public Group getGroup_1_3_0_0() { return cGroup_1_3_0_0; }
		
		//{PropertyMethodDeclaration.annotationList=current}
		public Action getPropertyMethodDeclarationAnnotationListAction_1_3_0_0_0() { return cPropertyMethodDeclarationAnnotationListAction_1_3_0_0_0; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_1_3_0_0_1() { return cTypeVariablesParserRuleCall_1_3_0_0_1; }
		
		//returnTypeRef=TypeRefWithModifiers?
		public Assignment getReturnTypeRefAssignment_1_3_0_0_2() { return cReturnTypeRefAssignment_1_3_0_0_2; }
		
		//TypeRefWithModifiers
		public RuleCall getReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0() { return cReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0; }
		
		//generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>
		public Alternatives getAlternatives_1_3_0_0_3() { return cAlternatives_1_3_0_0_3; }
		
		//generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true>
		public Group getGroup_1_3_0_0_3_0() { return cGroup_1_3_0_0_3_0; }
		
		//generator?='*'
		public Assignment getGeneratorAssignment_1_3_0_0_3_0_0() { return cGeneratorAssignment_1_3_0_0_3_0_0; }
		
		//'*'
		public Keyword getGeneratorAsteriskKeyword_1_3_0_0_3_0_0_0() { return cGeneratorAsteriskKeyword_1_3_0_0_3_0_0_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_1_3_0_0_3_0_1() { return cDeclaredNameAssignment_1_3_0_0_3_0_1; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0; }
		
		//-> MethodParamsAndBody <Generator=true>
		public RuleCall getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2() { return cMethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>
		public Group getGroup_1_3_0_0_3_1() { return cGroup_1_3_0_0_3_1; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_1_3_0_0_3_1_0() { return cDeclaredNameAssignment_1_3_0_0_3_1_0; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0; }
		
		//-> MethodParamsAndBody <Generator=false>
		public RuleCall getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1() { return cMethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1; }
		
		//';'?
		public Keyword getSemicolonKeyword_1_3_1() { return cSemicolonKeyword_1_3_1; }
		
		//{PropertyNameValuePairSingleName.annotationList=current} declaredTypeRef=TypeRef? identifierRef=IdentifierRef<Yield>
		//('=' expression=AssignmentExpression<In=true,Yield>)?
		public Group getGroup_1_4() { return cGroup_1_4; }
		
		//{PropertyNameValuePairSingleName.annotationList=current}
		public Action getPropertyNameValuePairSingleNameAnnotationListAction_1_4_0() { return cPropertyNameValuePairSingleNameAnnotationListAction_1_4_0; }
		
		//declaredTypeRef=TypeRef?
		public Assignment getDeclaredTypeRefAssignment_1_4_1() { return cDeclaredTypeRefAssignment_1_4_1; }
		
		//TypeRef
		public RuleCall getDeclaredTypeRefTypeRefParserRuleCall_1_4_1_0() { return cDeclaredTypeRefTypeRefParserRuleCall_1_4_1_0; }
		
		//identifierRef=IdentifierRef<Yield>
		public Assignment getIdentifierRefAssignment_1_4_2() { return cIdentifierRefAssignment_1_4_2; }
		
		//IdentifierRef<Yield>
		public RuleCall getIdentifierRefIdentifierRefParserRuleCall_1_4_2_0() { return cIdentifierRefIdentifierRefParserRuleCall_1_4_2_0; }
		
		//('=' expression=AssignmentExpression<In=true,Yield>)?
		public Group getGroup_1_4_3() { return cGroup_1_4_3; }
		
		//'='
		public Keyword getEqualsSignKeyword_1_4_3_0() { return cEqualsSignKeyword_1_4_3_0; }
		
		//expression=AssignmentExpression<In=true,Yield>
		public Assignment getExpressionAssignment_1_4_3_1() { return cExpressionAssignment_1_4_3_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0; }
	}
	public class PropertyMethodDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PropertyMethodDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cPropertyMethodDeclarationAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_0_0_1 = (RuleCall)cGroup_0_0.eContents().get(1);
		private final Assignment cReturnTypeRefAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0 = (RuleCall)cReturnTypeRefAssignment_0_0_2.eContents().get(0);
		private final Alternatives cAlternatives_0_0_3 = (Alternatives)cGroup_0_0.eContents().get(3);
		private final Group cGroup_0_0_3_0 = (Group)cAlternatives_0_0_3.eContents().get(0);
		private final Assignment cGeneratorAssignment_0_0_3_0_0 = (Assignment)cGroup_0_0_3_0.eContents().get(0);
		private final Keyword cGeneratorAsteriskKeyword_0_0_3_0_0_0 = (Keyword)cGeneratorAssignment_0_0_3_0_0.eContents().get(0);
		private final Assignment cDeclaredNameAssignment_0_0_3_0_1 = (Assignment)cGroup_0_0_3_0.eContents().get(1);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0 = (RuleCall)cDeclaredNameAssignment_0_0_3_0_1.eContents().get(0);
		private final RuleCall cMethodParamsAndBodyParserRuleCall_0_0_3_0_2 = (RuleCall)cGroup_0_0_3_0.eContents().get(2);
		private final Group cGroup_0_0_3_1 = (Group)cAlternatives_0_0_3.eContents().get(1);
		private final Assignment cDeclaredNameAssignment_0_0_3_1_0 = (Assignment)cGroup_0_0_3_1.eContents().get(0);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0 = (RuleCall)cDeclaredNameAssignment_0_0_3_1_0.eContents().get(0);
		private final RuleCall cMethodParamsAndBodyParserRuleCall_0_0_3_1_1 = (RuleCall)cGroup_0_0_3_1.eContents().get(1);
		private final Keyword cSemicolonKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//PropertyMethodDeclaration <Yield>:
		//	=> ({PropertyMethodDeclaration} TypeVariables? returnTypeRef=TypeRefWithModifiers? (generator?='*'
		//	declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//	declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>))
		//	';'?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({PropertyMethodDeclaration} TypeVariables? returnTypeRef=TypeRefWithModifiers? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>)) ';'?
		public Group getGroup() { return cGroup; }
		
		//=> ({PropertyMethodDeclaration} TypeVariables? returnTypeRef=TypeRefWithModifiers? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>))
		public Group getGroup_0() { return cGroup_0; }
		
		//{PropertyMethodDeclaration} TypeVariables? returnTypeRef=TypeRefWithModifiers? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>)
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{PropertyMethodDeclaration}
		public Action getPropertyMethodDeclarationAction_0_0_0() { return cPropertyMethodDeclarationAction_0_0_0; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_0_0_1() { return cTypeVariablesParserRuleCall_0_0_1; }
		
		//returnTypeRef=TypeRefWithModifiers?
		public Assignment getReturnTypeRefAssignment_0_0_2() { return cReturnTypeRefAssignment_0_0_2; }
		
		//TypeRefWithModifiers
		public RuleCall getReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0() { return cReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0; }
		
		//generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>
		public Alternatives getAlternatives_0_0_3() { return cAlternatives_0_0_3; }
		
		//generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true>
		public Group getGroup_0_0_3_0() { return cGroup_0_0_3_0; }
		
		//generator?='*'
		public Assignment getGeneratorAssignment_0_0_3_0_0() { return cGeneratorAssignment_0_0_3_0_0; }
		
		//'*'
		public Keyword getGeneratorAsteriskKeyword_0_0_3_0_0_0() { return cGeneratorAsteriskKeyword_0_0_3_0_0_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_0_0_3_0_1() { return cDeclaredNameAssignment_0_0_3_0_1; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0; }
		
		//-> MethodParamsAndBody <Generator=true>
		public RuleCall getMethodParamsAndBodyParserRuleCall_0_0_3_0_2() { return cMethodParamsAndBodyParserRuleCall_0_0_3_0_2; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>
		public Group getGroup_0_0_3_1() { return cGroup_0_0_3_1; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_0_0_3_1_0() { return cDeclaredNameAssignment_0_0_3_1_0; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0; }
		
		//-> MethodParamsAndBody <Generator=false>
		public RuleCall getMethodParamsAndBodyParserRuleCall_0_0_3_1_1() { return cMethodParamsAndBodyParserRuleCall_0_0_3_1_1; }
		
		//';'?
		public Keyword getSemicolonKeyword_1() { return cSemicolonKeyword_1; }
	}
	public class PropertyNameValuePairElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PropertyNameValuePair");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cPropertyNameValuePairAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Assignment cDeclaredTypeRefAssignment_0_0_1 = (Assignment)cGroup_0_0.eContents().get(1);
		private final RuleCall cDeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0 = (RuleCall)cDeclaredTypeRefAssignment_0_0_1.eContents().get(0);
		private final Assignment cDeclaredNameAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0 = (RuleCall)cDeclaredNameAssignment_0_0_2.eContents().get(0);
		private final Assignment cDeclaredOptionalAssignment_0_0_3 = (Assignment)cGroup_0_0.eContents().get(3);
		private final Keyword cDeclaredOptionalQuestionMarkKeyword_0_0_3_0 = (Keyword)cDeclaredOptionalAssignment_0_0_3.eContents().get(0);
		private final Keyword cColonKeyword_0_0_4 = (Keyword)cGroup_0_0.eContents().get(4);
		private final Assignment cExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_0 = (RuleCall)cExpressionAssignment_1.eContents().get(0);
		
		//PropertyNameValuePair <Yield>:
		//	=> ({PropertyNameValuePair} declaredTypeRef=TypeRefWithModifiers?
		//	declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'?
		//	':') expression=AssignmentExpression<In=true,Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({PropertyNameValuePair} declaredTypeRef=TypeRefWithModifiers? declaredName=LiteralOrComputedPropertyName<Yield>
		//declaredOptional?='?'? ':') expression=AssignmentExpression<In=true,Yield>
		public Group getGroup() { return cGroup; }
		
		//=> ({PropertyNameValuePair} declaredTypeRef=TypeRefWithModifiers? declaredName=LiteralOrComputedPropertyName<Yield>
		//declaredOptional?='?'? ':')
		public Group getGroup_0() { return cGroup_0; }
		
		//{PropertyNameValuePair} declaredTypeRef=TypeRefWithModifiers? declaredName=LiteralOrComputedPropertyName<Yield>
		//declaredOptional?='?'? ':'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{PropertyNameValuePair}
		public Action getPropertyNameValuePairAction_0_0_0() { return cPropertyNameValuePairAction_0_0_0; }
		
		//declaredTypeRef=TypeRefWithModifiers?
		public Assignment getDeclaredTypeRefAssignment_0_0_1() { return cDeclaredTypeRefAssignment_0_0_1; }
		
		//TypeRefWithModifiers
		public RuleCall getDeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0() { return cDeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_0_0_2() { return cDeclaredNameAssignment_0_0_2; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0; }
		
		//declaredOptional?='?'?
		public Assignment getDeclaredOptionalAssignment_0_0_3() { return cDeclaredOptionalAssignment_0_0_3; }
		
		//'?'
		public Keyword getDeclaredOptionalQuestionMarkKeyword_0_0_3_0() { return cDeclaredOptionalQuestionMarkKeyword_0_0_3_0; }
		
		//':'
		public Keyword getColonKeyword_0_0_4() { return cColonKeyword_0_0_4; }
		
		//expression=AssignmentExpression<In=true,Yield>
		public Assignment getExpressionAssignment_1() { return cExpressionAssignment_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_0; }
	}
	public class PropertyNameValuePairSingleNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PropertyNameValuePairSingleName");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDeclaredTypeRefAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDeclaredTypeRefTypeRefParserRuleCall_0_0 = (RuleCall)cDeclaredTypeRefAssignment_0.eContents().get(0);
		private final Assignment cIdentifierRefAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cIdentifierRefIdentifierRefParserRuleCall_1_0 = (RuleCall)cIdentifierRefAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cEqualsSignKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cExpressionAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_2_1_0 = (RuleCall)cExpressionAssignment_2_1.eContents().get(0);
		
		///*
		// * Support for single name syntax in ObjectLiteral (but disallowed in actual object literals by ASTStructureValidator
		// * except in assignment destructuring patterns)
		// */ PropertyNameValuePairSingleName <Yield>:
		//	declaredTypeRef=TypeRef?
		//	identifierRef=IdentifierRef<Yield> ('=' expression=AssignmentExpression<In=true,Yield>)?;
		@Override public ParserRule getRule() { return rule; }
		
		//declaredTypeRef=TypeRef? identifierRef=IdentifierRef<Yield> ('=' expression=AssignmentExpression<In=true,Yield>)?
		public Group getGroup() { return cGroup; }
		
		//declaredTypeRef=TypeRef?
		public Assignment getDeclaredTypeRefAssignment_0() { return cDeclaredTypeRefAssignment_0; }
		
		//TypeRef
		public RuleCall getDeclaredTypeRefTypeRefParserRuleCall_0_0() { return cDeclaredTypeRefTypeRefParserRuleCall_0_0; }
		
		//identifierRef=IdentifierRef<Yield>
		public Assignment getIdentifierRefAssignment_1() { return cIdentifierRefAssignment_1; }
		
		//IdentifierRef<Yield>
		public RuleCall getIdentifierRefIdentifierRefParserRuleCall_1_0() { return cIdentifierRefIdentifierRefParserRuleCall_1_0; }
		
		//('=' expression=AssignmentExpression<In=true,Yield>)?
		public Group getGroup_2() { return cGroup_2; }
		
		//'='
		public Keyword getEqualsSignKeyword_2_0() { return cEqualsSignKeyword_2_0; }
		
		//expression=AssignmentExpression<In=true,Yield>
		public Assignment getExpressionAssignment_2_1() { return cExpressionAssignment_2_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_2_1_0() { return cExpressionAssignmentExpressionParserRuleCall_2_1_0; }
	}
	public class PropertyGetterDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PropertyGetterDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cPropertyGetterDeclarationAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final RuleCall cGetterHeaderParserRuleCall_0_0_1 = (RuleCall)cGroup_0_0.eContents().get(1);
		private final Assignment cBodyAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cBodyBlockParserRuleCall_1_0 = (RuleCall)cBodyAssignment_1.eContents().get(0);
		
		//PropertyGetterDeclaration <Yield>:
		//	=> ({PropertyGetterDeclaration} GetterHeader<Yield>) body=Block<Yield=false>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({PropertyGetterDeclaration} GetterHeader<Yield>) body=Block<Yield=false>
		public Group getGroup() { return cGroup; }
		
		//=> ({PropertyGetterDeclaration} GetterHeader<Yield>)
		public Group getGroup_0() { return cGroup_0; }
		
		//{PropertyGetterDeclaration} GetterHeader<Yield>
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{PropertyGetterDeclaration}
		public Action getPropertyGetterDeclarationAction_0_0_0() { return cPropertyGetterDeclarationAction_0_0_0; }
		
		//GetterHeader<Yield>
		public RuleCall getGetterHeaderParserRuleCall_0_0_1() { return cGetterHeaderParserRuleCall_0_0_1; }
		
		//body=Block<Yield=false>
		public Assignment getBodyAssignment_1() { return cBodyAssignment_1; }
		
		//Block<Yield=false>
		public RuleCall getBodyBlockParserRuleCall_1_0() { return cBodyBlockParserRuleCall_1_0; }
	}
	public class PropertySetterDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PropertySetterDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cPropertySetterDeclarationAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cSetKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cDeclaredNameAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0 = (RuleCall)cDeclaredNameAssignment_0_0_2.eContents().get(0);
		private final Assignment cDeclaredOptionalAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cDeclaredOptionalQuestionMarkKeyword_1_0 = (Keyword)cDeclaredOptionalAssignment_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cFparAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cFparFormalParameterParserRuleCall_3_0 = (RuleCall)cFparAssignment_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cBodyAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cBodyBlockParserRuleCall_5_0 = (RuleCall)cBodyAssignment_5.eContents().get(0);
		
		//PropertySetterDeclaration <Yield>:
		//	=> ({PropertySetterDeclaration}
		//	'set'
		//	-> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'?
		//	'(' fpar=FormalParameter<Yield> ')' body=Block<Yield=false>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({PropertySetterDeclaration} 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'? '('
		//fpar=FormalParameter<Yield> ')' body=Block<Yield=false>
		public Group getGroup() { return cGroup; }
		
		//=> ({PropertySetterDeclaration} 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>)
		public Group getGroup_0() { return cGroup_0; }
		
		//{PropertySetterDeclaration} 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{PropertySetterDeclaration}
		public Action getPropertySetterDeclarationAction_0_0_0() { return cPropertySetterDeclarationAction_0_0_0; }
		
		//'set'
		public Keyword getSetKeyword_0_0_1() { return cSetKeyword_0_0_1; }
		
		//-> declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_0_0_2() { return cDeclaredNameAssignment_0_0_2; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0; }
		
		//declaredOptional?='?'?
		public Assignment getDeclaredOptionalAssignment_1() { return cDeclaredOptionalAssignment_1; }
		
		//'?'
		public Keyword getDeclaredOptionalQuestionMarkKeyword_1_0() { return cDeclaredOptionalQuestionMarkKeyword_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }
		
		//fpar=FormalParameter<Yield>
		public Assignment getFparAssignment_3() { return cFparAssignment_3; }
		
		//FormalParameter<Yield>
		public RuleCall getFparFormalParameterParserRuleCall_3_0() { return cFparFormalParameterParserRuleCall_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_4() { return cRightParenthesisKeyword_4; }
		
		//body=Block<Yield=false>
		public Assignment getBodyAssignment_5() { return cBodyAssignment_5; }
		
		//Block<Yield=false>
		public RuleCall getBodyBlockParserRuleCall_5_0() { return cBodyBlockParserRuleCall_5_0; }
	}
	public class ParameterizedCallExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ParameterizedCallExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cConcreteTypeArgumentsParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Assignment cTargetAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTargetIdentifierRefParserRuleCall_1_0 = (RuleCall)cTargetAssignment_1.eContents().get(0);
		private final RuleCall cArgumentsWithParenthesesParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		///* Left-hand-side expressions (11.2) [ECM11]
		// * Heavily refactored to make them LL(*) compliant.
		// */ ParameterizedCallExpression <Yield>:
		//	ConcreteTypeArguments
		//	target=IdentifierRef<Yield> ArgumentsWithParentheses<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//ConcreteTypeArguments target=IdentifierRef<Yield> ArgumentsWithParentheses<Yield>
		public Group getGroup() { return cGroup; }
		
		//ConcreteTypeArguments
		public RuleCall getConcreteTypeArgumentsParserRuleCall_0() { return cConcreteTypeArgumentsParserRuleCall_0; }
		
		//target=IdentifierRef<Yield>
		public Assignment getTargetAssignment_1() { return cTargetAssignment_1; }
		
		//IdentifierRef<Yield>
		public RuleCall getTargetIdentifierRefParserRuleCall_1_0() { return cTargetIdentifierRefParserRuleCall_1_0; }
		
		//ArgumentsWithParentheses<Yield>
		public RuleCall getArgumentsWithParenthesesParserRuleCall_2() { return cArgumentsWithParenthesesParserRuleCall_2; }
	}
	public class ConcreteTypeArgumentsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ConcreteTypeArguments");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLessThanSignKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cTypeArgsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTypeArgsTypeRefParserRuleCall_1_0 = (RuleCall)cTypeArgsAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cCommaKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cTypeArgsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cTypeArgsTypeRefParserRuleCall_2_1_0 = (RuleCall)cTypeArgsAssignment_2_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//fragment ConcreteTypeArguments *:
		//	'<' typeArgs+=TypeRef (',' typeArgs+=TypeRef)* '>';
		@Override public ParserRule getRule() { return rule; }
		
		//'<' typeArgs+=TypeRef (',' typeArgs+=TypeRef)* '>'
		public Group getGroup() { return cGroup; }
		
		//'<'
		public Keyword getLessThanSignKeyword_0() { return cLessThanSignKeyword_0; }
		
		//typeArgs+=TypeRef
		public Assignment getTypeArgsAssignment_1() { return cTypeArgsAssignment_1; }
		
		//TypeRef
		public RuleCall getTypeArgsTypeRefParserRuleCall_1_0() { return cTypeArgsTypeRefParserRuleCall_1_0; }
		
		//(',' typeArgs+=TypeRef)*
		public Group getGroup_2() { return cGroup_2; }
		
		//','
		public Keyword getCommaKeyword_2_0() { return cCommaKeyword_2_0; }
		
		//typeArgs+=TypeRef
		public Assignment getTypeArgsAssignment_2_1() { return cTypeArgsAssignment_2_1; }
		
		//TypeRef
		public RuleCall getTypeArgsTypeRefParserRuleCall_2_1_0() { return cTypeArgsTypeRefParserRuleCall_2_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3() { return cGreaterThanSignKeyword_3; }
	}
	public class ImportCallExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ImportCallExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cImportKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final RuleCall cArgumentsWithParenthesesParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//ImportCallExpression <Yield>:
		//	'import' ArgumentsWithParentheses<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//'import' ArgumentsWithParentheses<Yield>
		public Group getGroup() { return cGroup; }
		
		//'import'
		public Keyword getImportKeyword_0() { return cImportKeyword_0; }
		
		//ArgumentsWithParentheses<Yield>
		public RuleCall getArgumentsWithParenthesesParserRuleCall_1() { return cArgumentsWithParenthesesParserRuleCall_1; }
	}
	public class LeftHandSideExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LeftHandSideExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cMemberExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cParameterizedCallExpressionTargetAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final RuleCall cArgumentsWithParenthesesParserRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		private final Alternatives cAlternatives_1_2 = (Alternatives)cGroup_1.eContents().get(2);
		private final Group cGroup_1_2_0 = (Group)cAlternatives_1_2.eContents().get(0);
		private final Action cParameterizedCallExpressionTargetAction_1_2_0_0 = (Action)cGroup_1_2_0.eContents().get(0);
		private final RuleCall cArgumentsWithParenthesesParserRuleCall_1_2_0_1 = (RuleCall)cGroup_1_2_0.eContents().get(1);
		private final Group cGroup_1_2_1 = (Group)cAlternatives_1_2.eContents().get(1);
		private final Action cIndexedAccessExpressionTargetAction_1_2_1_0 = (Action)cGroup_1_2_1.eContents().get(0);
		private final RuleCall cIndexedAccessExpressionTailParserRuleCall_1_2_1_1 = (RuleCall)cGroup_1_2_1.eContents().get(1);
		private final Group cGroup_1_2_2 = (Group)cAlternatives_1_2.eContents().get(2);
		private final Action cParameterizedPropertyAccessExpressionTargetAction_1_2_2_0 = (Action)cGroup_1_2_2.eContents().get(0);
		private final RuleCall cParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1 = (RuleCall)cGroup_1_2_2.eContents().get(1);
		private final Group cGroup_1_2_3 = (Group)cAlternatives_1_2.eContents().get(3);
		private final Group cGroup_1_2_3_0 = (Group)cGroup_1_2_3.eContents().get(0);
		private final Action cTaggedTemplateStringTargetAction_1_2_3_0_0 = (Action)cGroup_1_2_3_0.eContents().get(0);
		private final Assignment cTemplateAssignment_1_2_3_0_1 = (Assignment)cGroup_1_2_3_0.eContents().get(1);
		private final RuleCall cTemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0 = (RuleCall)cTemplateAssignment_1_2_3_0_1.eContents().get(0);
		
		//LeftHandSideExpression <Yield Expression:
		//	MemberExpression<Yield> ({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield>
		//	({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield> |
		//	{IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//	{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> | ->
		//	({TaggedTemplateString.target=current} template=TemplateLiteral<Yield>))*)?;
		@Override public ParserRule getRule() { return rule; }
		
		//MemberExpression<Yield> ({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield>
		//({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield> |
		//{IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> | ->
		//({TaggedTemplateString.target=current} template=TemplateLiteral<Yield>))*)?
		public Group getGroup() { return cGroup; }
		
		//MemberExpression<Yield>
		public RuleCall getMemberExpressionParserRuleCall_0() { return cMemberExpressionParserRuleCall_0; }
		
		//({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield>
		//({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield> |
		//{IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> | ->
		//({TaggedTemplateString.target=current} template=TemplateLiteral<Yield>))*)?
		public Group getGroup_1() { return cGroup_1; }
		
		//{ParameterizedCallExpression.target=current}
		public Action getParameterizedCallExpressionTargetAction_1_0() { return cParameterizedCallExpressionTargetAction_1_0; }
		
		//ArgumentsWithParentheses<Yield>
		public RuleCall getArgumentsWithParenthesesParserRuleCall_1_1() { return cArgumentsWithParenthesesParserRuleCall_1_1; }
		
		//({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield> | {IndexedAccessExpression.target=current}
		//IndexedAccessExpressionTail<Yield> | {ParameterizedPropertyAccessExpression.target=current}
		//ParameterizedPropertyAccessExpressionTail<Yield> | -> ({TaggedTemplateString.target=current}
		//template=TemplateLiteral<Yield>))*
		public Alternatives getAlternatives_1_2() { return cAlternatives_1_2; }
		
		//{ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield>
		public Group getGroup_1_2_0() { return cGroup_1_2_0; }
		
		//{ParameterizedCallExpression.target=current}
		public Action getParameterizedCallExpressionTargetAction_1_2_0_0() { return cParameterizedCallExpressionTargetAction_1_2_0_0; }
		
		//ArgumentsWithParentheses<Yield>
		public RuleCall getArgumentsWithParenthesesParserRuleCall_1_2_0_1() { return cArgumentsWithParenthesesParserRuleCall_1_2_0_1; }
		
		//{IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield>
		public Group getGroup_1_2_1() { return cGroup_1_2_1; }
		
		//{IndexedAccessExpression.target=current}
		public Action getIndexedAccessExpressionTargetAction_1_2_1_0() { return cIndexedAccessExpressionTargetAction_1_2_1_0; }
		
		//IndexedAccessExpressionTail<Yield>
		public RuleCall getIndexedAccessExpressionTailParserRuleCall_1_2_1_1() { return cIndexedAccessExpressionTailParserRuleCall_1_2_1_1; }
		
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield>
		public Group getGroup_1_2_2() { return cGroup_1_2_2; }
		
		//{ParameterizedPropertyAccessExpression.target=current}
		public Action getParameterizedPropertyAccessExpressionTargetAction_1_2_2_0() { return cParameterizedPropertyAccessExpressionTargetAction_1_2_2_0; }
		
		//ParameterizedPropertyAccessExpressionTail<Yield>
		public RuleCall getParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1() { return cParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1; }
		
		//-> ({TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)
		public Group getGroup_1_2_3() { return cGroup_1_2_3; }
		
		//{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>
		public Group getGroup_1_2_3_0() { return cGroup_1_2_3_0; }
		
		//{TaggedTemplateString.target=current}
		public Action getTaggedTemplateStringTargetAction_1_2_3_0_0() { return cTaggedTemplateStringTargetAction_1_2_3_0_0; }
		
		//template=TemplateLiteral<Yield>
		public Assignment getTemplateAssignment_1_2_3_0_1() { return cTemplateAssignment_1_2_3_0_1; }
		
		//TemplateLiteral<Yield>
		public RuleCall getTemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0() { return cTemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0; }
	}
	public class ArgumentsWithParenthesesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ArgumentsWithParentheses");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final RuleCall cArgumentsParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//fragment ArgumentsWithParentheses <Yield> *:
		//	'(' Arguments<Yield>? ')';
		@Override public ParserRule getRule() { return rule; }
		
		//'(' Arguments<Yield>? ')'
		public Group getGroup() { return cGroup; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_0() { return cLeftParenthesisKeyword_0; }
		
		//Arguments<Yield>?
		public RuleCall getArgumentsParserRuleCall_1() { return cArgumentsParserRuleCall_1; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2() { return cRightParenthesisKeyword_2; }
	}
	public class ArgumentsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Arguments");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Assignment cArgumentsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cArgumentsArgumentParserRuleCall_0_0 = (RuleCall)cArgumentsAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cCommaKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cArgumentsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cArgumentsArgumentParserRuleCall_1_1_0 = (RuleCall)cArgumentsAssignment_1_1.eContents().get(0);
		
		//fragment Arguments <Yield> *:
		//	arguments+=Argument<Yield> (',' arguments+=Argument<Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//arguments+=Argument<Yield> (',' arguments+=Argument<Yield>)*
		public Group getGroup() { return cGroup; }
		
		//arguments+=Argument<Yield>
		public Assignment getArgumentsAssignment_0() { return cArgumentsAssignment_0; }
		
		//Argument<Yield>
		public RuleCall getArgumentsArgumentParserRuleCall_0_0() { return cArgumentsArgumentParserRuleCall_0_0; }
		
		//(',' arguments+=Argument<Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//','
		public Keyword getCommaKeyword_1_0() { return cCommaKeyword_1_0; }
		
		//arguments+=Argument<Yield>
		public Assignment getArgumentsAssignment_1_1() { return cArgumentsAssignment_1_1; }
		
		//Argument<Yield>
		public RuleCall getArgumentsArgumentParserRuleCall_1_1_0() { return cArgumentsArgumentParserRuleCall_1_1_0; }
	}
	public class ArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Argument");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cSpreadAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cSpreadFullStopFullStopFullStopKeyword_0_0 = (Keyword)cSpreadAssignment_0.eContents().get(0);
		private final Assignment cExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_0 = (RuleCall)cExpressionAssignment_1.eContents().get(0);
		
		//Argument <Yield>:
		//	spread?='...'? expression=AssignmentExpression<In=true,Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//spread?='...'? expression=AssignmentExpression<In=true,Yield>
		public Group getGroup() { return cGroup; }
		
		//spread?='...'?
		public Assignment getSpreadAssignment_0() { return cSpreadAssignment_0; }
		
		//'...'
		public Keyword getSpreadFullStopFullStopFullStopKeyword_0_0() { return cSpreadFullStopFullStopFullStopKeyword_0_0; }
		
		//expression=AssignmentExpression<In=true,Yield>
		public Assignment getExpressionAssignment_1() { return cExpressionAssignment_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_0; }
	}
	public class MemberExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.MemberExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Group cGroup_0_0_0 = (Group)cGroup_0_0.eContents().get(0);
		private final Action cNewTargetAction_0_0_0_0 = (Action)cGroup_0_0_0.eContents().get(0);
		private final Keyword cNewKeyword_0_0_0_1 = (Keyword)cGroup_0_0_0.eContents().get(1);
		private final Keyword cFullStopKeyword_0_0_0_2 = (Keyword)cGroup_0_0_0.eContents().get(2);
		private final Keyword cTargetKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cNewExpressionAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Keyword cNewKeyword_1_0_0_1 = (Keyword)cGroup_1_0_0.eContents().get(1);
		private final Assignment cCalleeAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cCalleeMemberExpressionParserRuleCall_1_1_0 = (RuleCall)cCalleeAssignment_1_1.eContents().get(0);
		private final RuleCall cConcreteTypeArgumentsParserRuleCall_1_2 = (RuleCall)cGroup_1.eContents().get(2);
		private final Group cGroup_1_3 = (Group)cGroup_1.eContents().get(3);
		private final Assignment cWithArgsAssignment_1_3_0 = (Assignment)cGroup_1_3.eContents().get(0);
		private final Keyword cWithArgsLeftParenthesisKeyword_1_3_0_0 = (Keyword)cWithArgsAssignment_1_3_0.eContents().get(0);
		private final RuleCall cArgumentsParserRuleCall_1_3_1 = (RuleCall)cGroup_1_3.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_1_3_2 = (Keyword)cGroup_1_3.eContents().get(2);
		private final Alternatives cAlternatives_1_3_3 = (Alternatives)cGroup_1_3.eContents().get(3);
		private final Group cGroup_1_3_3_0 = (Group)cAlternatives_1_3_3.eContents().get(0);
		private final Action cIndexedAccessExpressionTargetAction_1_3_3_0_0 = (Action)cGroup_1_3_3_0.eContents().get(0);
		private final RuleCall cIndexedAccessExpressionTailParserRuleCall_1_3_3_0_1 = (RuleCall)cGroup_1_3_3_0.eContents().get(1);
		private final Group cGroup_1_3_3_1 = (Group)cAlternatives_1_3_3.eContents().get(1);
		private final Action cParameterizedPropertyAccessExpressionTargetAction_1_3_3_1_0 = (Action)cGroup_1_3_3_1.eContents().get(0);
		private final RuleCall cParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1 = (RuleCall)cGroup_1_3_3_1.eContents().get(1);
		private final Group cGroup_1_3_3_2 = (Group)cAlternatives_1_3_3.eContents().get(2);
		private final Action cTaggedTemplateStringTargetAction_1_3_3_2_0 = (Action)cGroup_1_3_3_2.eContents().get(0);
		private final Assignment cTemplateAssignment_1_3_3_2_1 = (Assignment)cGroup_1_3_3_2.eContents().get(1);
		private final RuleCall cTemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0 = (RuleCall)cTemplateAssignment_1_3_3_2_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cAlternatives.eContents().get(2);
		private final RuleCall cPrimaryExpressionParserRuleCall_2_0 = (RuleCall)cGroup_2.eContents().get(0);
		private final Alternatives cAlternatives_2_1 = (Alternatives)cGroup_2.eContents().get(1);
		private final Group cGroup_2_1_0 = (Group)cAlternatives_2_1.eContents().get(0);
		private final Action cIndexedAccessExpressionTargetAction_2_1_0_0 = (Action)cGroup_2_1_0.eContents().get(0);
		private final RuleCall cIndexedAccessExpressionTailParserRuleCall_2_1_0_1 = (RuleCall)cGroup_2_1_0.eContents().get(1);
		private final Group cGroup_2_1_1 = (Group)cAlternatives_2_1.eContents().get(1);
		private final Action cParameterizedPropertyAccessExpressionTargetAction_2_1_1_0 = (Action)cGroup_2_1_1.eContents().get(0);
		private final RuleCall cParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1 = (RuleCall)cGroup_2_1_1.eContents().get(1);
		private final Group cGroup_2_1_2 = (Group)cAlternatives_2_1.eContents().get(2);
		private final Action cTaggedTemplateStringTargetAction_2_1_2_0 = (Action)cGroup_2_1_2.eContents().get(0);
		private final Assignment cTemplateAssignment_2_1_2_1 = (Assignment)cGroup_2_1_2.eContents().get(1);
		private final RuleCall cTemplateTemplateLiteralParserRuleCall_2_1_2_1_0 = (RuleCall)cTemplateAssignment_2_1_2_1.eContents().get(0);
		
		//MemberExpression <Yield Expression:
		//	=> ({NewTarget} 'new' '.') 'target'
		//	| => ({NewExpression} 'new') callee=MemberExpression<Yield> -> ConcreteTypeArguments? (=> withArgs?='('
		//	Arguments<Yield>? ')' ({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//	{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
		//	{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*)?
		//	| PrimaryExpression<Yield> ({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//	{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
		//	{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({NewTarget} 'new' '.') 'target' | => ({NewExpression} 'new') callee=MemberExpression<Yield> ->
		//ConcreteTypeArguments? (=> withArgs?='(' Arguments<Yield>? ')' ({IndexedAccessExpression.target=current}
		//IndexedAccessExpressionTail<Yield> | {ParameterizedPropertyAccessExpression.target=current}
		//ParameterizedPropertyAccessExpressionTail<Yield> | {TaggedTemplateString.target=current}
		//template=TemplateLiteral<Yield>)*)? | PrimaryExpression<Yield> ({IndexedAccessExpression.target=current}
		//IndexedAccessExpressionTail<Yield> | {ParameterizedPropertyAccessExpression.target=current}
		//ParameterizedPropertyAccessExpressionTail<Yield> | {TaggedTemplateString.target=current}
		//template=TemplateLiteral<Yield>)*
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//=> ({NewTarget} 'new' '.') 'target'
		public Group getGroup_0() { return cGroup_0; }
		
		//=> ({NewTarget} 'new' '.')
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{NewTarget} 'new' '.'
		public Group getGroup_0_0_0() { return cGroup_0_0_0; }
		
		//{NewTarget}
		public Action getNewTargetAction_0_0_0_0() { return cNewTargetAction_0_0_0_0; }
		
		//'new'
		public Keyword getNewKeyword_0_0_0_1() { return cNewKeyword_0_0_0_1; }
		
		//'.'
		public Keyword getFullStopKeyword_0_0_0_2() { return cFullStopKeyword_0_0_0_2; }
		
		//'target'
		public Keyword getTargetKeyword_0_1() { return cTargetKeyword_0_1; }
		
		//=> ({NewExpression} 'new') callee=MemberExpression<Yield> -> ConcreteTypeArguments? (=> withArgs?='(' Arguments<Yield>?
		//')' ({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
		//{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*)?
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({NewExpression} 'new')
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{NewExpression} 'new'
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{NewExpression}
		public Action getNewExpressionAction_1_0_0_0() { return cNewExpressionAction_1_0_0_0; }
		
		//'new'
		public Keyword getNewKeyword_1_0_0_1() { return cNewKeyword_1_0_0_1; }
		
		//callee=MemberExpression<Yield>
		public Assignment getCalleeAssignment_1_1() { return cCalleeAssignment_1_1; }
		
		//MemberExpression<Yield>
		public RuleCall getCalleeMemberExpressionParserRuleCall_1_1_0() { return cCalleeMemberExpressionParserRuleCall_1_1_0; }
		
		//-> ConcreteTypeArguments?
		public RuleCall getConcreteTypeArgumentsParserRuleCall_1_2() { return cConcreteTypeArgumentsParserRuleCall_1_2; }
		
		//(=> withArgs?='(' Arguments<Yield>? ')' ({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
		//{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*)?
		public Group getGroup_1_3() { return cGroup_1_3; }
		
		//=> withArgs?='('
		public Assignment getWithArgsAssignment_1_3_0() { return cWithArgsAssignment_1_3_0; }
		
		//'('
		public Keyword getWithArgsLeftParenthesisKeyword_1_3_0_0() { return cWithArgsLeftParenthesisKeyword_1_3_0_0; }
		
		//Arguments<Yield>?
		public RuleCall getArgumentsParserRuleCall_1_3_1() { return cArgumentsParserRuleCall_1_3_1; }
		
		//')'
		public Keyword getRightParenthesisKeyword_1_3_2() { return cRightParenthesisKeyword_1_3_2; }
		
		//({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
		//{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*
		public Alternatives getAlternatives_1_3_3() { return cAlternatives_1_3_3; }
		
		//{IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield>
		public Group getGroup_1_3_3_0() { return cGroup_1_3_3_0; }
		
		//{IndexedAccessExpression.target=current}
		public Action getIndexedAccessExpressionTargetAction_1_3_3_0_0() { return cIndexedAccessExpressionTargetAction_1_3_3_0_0; }
		
		//IndexedAccessExpressionTail<Yield>
		public RuleCall getIndexedAccessExpressionTailParserRuleCall_1_3_3_0_1() { return cIndexedAccessExpressionTailParserRuleCall_1_3_3_0_1; }
		
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield>
		public Group getGroup_1_3_3_1() { return cGroup_1_3_3_1; }
		
		//{ParameterizedPropertyAccessExpression.target=current}
		public Action getParameterizedPropertyAccessExpressionTargetAction_1_3_3_1_0() { return cParameterizedPropertyAccessExpressionTargetAction_1_3_3_1_0; }
		
		//ParameterizedPropertyAccessExpressionTail<Yield>
		public RuleCall getParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1() { return cParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1; }
		
		//{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>
		public Group getGroup_1_3_3_2() { return cGroup_1_3_3_2; }
		
		//{TaggedTemplateString.target=current}
		public Action getTaggedTemplateStringTargetAction_1_3_3_2_0() { return cTaggedTemplateStringTargetAction_1_3_3_2_0; }
		
		//template=TemplateLiteral<Yield>
		public Assignment getTemplateAssignment_1_3_3_2_1() { return cTemplateAssignment_1_3_3_2_1; }
		
		//TemplateLiteral<Yield>
		public RuleCall getTemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0() { return cTemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0; }
		
		//PrimaryExpression<Yield> ({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
		//{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*
		public Group getGroup_2() { return cGroup_2; }
		
		//PrimaryExpression<Yield>
		public RuleCall getPrimaryExpressionParserRuleCall_2_0() { return cPrimaryExpressionParserRuleCall_2_0; }
		
		//({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
		//{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*
		public Alternatives getAlternatives_2_1() { return cAlternatives_2_1; }
		
		//{IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield>
		public Group getGroup_2_1_0() { return cGroup_2_1_0; }
		
		//{IndexedAccessExpression.target=current}
		public Action getIndexedAccessExpressionTargetAction_2_1_0_0() { return cIndexedAccessExpressionTargetAction_2_1_0_0; }
		
		//IndexedAccessExpressionTail<Yield>
		public RuleCall getIndexedAccessExpressionTailParserRuleCall_2_1_0_1() { return cIndexedAccessExpressionTailParserRuleCall_2_1_0_1; }
		
		//{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield>
		public Group getGroup_2_1_1() { return cGroup_2_1_1; }
		
		//{ParameterizedPropertyAccessExpression.target=current}
		public Action getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0() { return cParameterizedPropertyAccessExpressionTargetAction_2_1_1_0; }
		
		//ParameterizedPropertyAccessExpressionTail<Yield>
		public RuleCall getParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1() { return cParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1; }
		
		//{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>
		public Group getGroup_2_1_2() { return cGroup_2_1_2; }
		
		//{TaggedTemplateString.target=current}
		public Action getTaggedTemplateStringTargetAction_2_1_2_0() { return cTaggedTemplateStringTargetAction_2_1_2_0; }
		
		//template=TemplateLiteral<Yield>
		public Assignment getTemplateAssignment_2_1_2_1() { return cTemplateAssignment_2_1_2_1; }
		
		//TemplateLiteral<Yield>
		public RuleCall getTemplateTemplateLiteralParserRuleCall_2_1_2_1_0() { return cTemplateTemplateLiteralParserRuleCall_2_1_2_1_0; }
	}
	public class IndexedAccessExpressionTailElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.IndexedAccessExpressionTail");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cIndexAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cIndexExpressionParserRuleCall_1_0 = (RuleCall)cIndexAssignment_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//fragment IndexedAccessExpressionTail <Yield> *:
		//	'[' index=Expression<In=true,Yield> ']';
		@Override public ParserRule getRule() { return rule; }
		
		//'[' index=Expression<In=true,Yield> ']'
		public Group getGroup() { return cGroup; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_0() { return cLeftSquareBracketKeyword_0; }
		
		//index=Expression<In=true,Yield>
		public Assignment getIndexAssignment_1() { return cIndexAssignment_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getIndexExpressionParserRuleCall_1_0() { return cIndexExpressionParserRuleCall_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_2() { return cRightSquareBracketKeyword_2; }
	}
	public class ParameterizedPropertyAccessExpressionTailElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ParameterizedPropertyAccessExpressionTail");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cFullStopKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final RuleCall cConcreteTypeArgumentsParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final Assignment cPropertyAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final CrossReference cPropertyIdentifiableElementCrossReference_2_0 = (CrossReference)cPropertyAssignment_2.eContents().get(0);
		private final RuleCall cPropertyIdentifiableElementIdentifierNameParserRuleCall_2_0_1 = (RuleCall)cPropertyIdentifiableElementCrossReference_2_0.eContents().get(1);
		
		//fragment ParameterizedPropertyAccessExpressionTail <Yield> *:
		//	'.' ConcreteTypeArguments? property=[types::IdentifiableElement|IdentifierName];
		@Override public ParserRule getRule() { return rule; }
		
		//'.' ConcreteTypeArguments? property=[types::IdentifiableElement|IdentifierName]
		public Group getGroup() { return cGroup; }
		
		//'.'
		public Keyword getFullStopKeyword_0() { return cFullStopKeyword_0; }
		
		//ConcreteTypeArguments?
		public RuleCall getConcreteTypeArgumentsParserRuleCall_1() { return cConcreteTypeArgumentsParserRuleCall_1; }
		
		//property=[types::IdentifiableElement|IdentifierName]
		public Assignment getPropertyAssignment_2() { return cPropertyAssignment_2; }
		
		//[types::IdentifiableElement|IdentifierName]
		public CrossReference getPropertyIdentifiableElementCrossReference_2_0() { return cPropertyIdentifiableElementCrossReference_2_0; }
		
		//IdentifierName
		public RuleCall getPropertyIdentifiableElementIdentifierNameParserRuleCall_2_0_1() { return cPropertyIdentifiableElementIdentifierNameParserRuleCall_2_0_1; }
	}
	public class PostfixExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PostfixExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cLeftHandSideExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cPostfixExpressionExpressionAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpPostfixOperatorEnumRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		
		///**
		// * Postfix expressions ([ECM11] 11.3).
		// * The specification states that there are no line terminators allowed before the postfix operators.
		// * This is enforced by the call to promoteEOL in the action before ( '++' | '--' ),
		// * added during grammar post-processing.
		// * We only must promote EOLs when the la is '++' or '--' because this production is chained as all expression rules.
		// * In other words: only promote EOL when we are really in a postfix expression. A check on the la will ensure this.
		// */ PostfixExpression <Yield Expression:
		//	LeftHandSideExpression<Yield>
		//	=> ({PostfixExpression.expression=current} op=PostfixOperator)?;
		@Override public ParserRule getRule() { return rule; }
		
		//LeftHandSideExpression<Yield> => ({PostfixExpression.expression=current} op=PostfixOperator)?
		public Group getGroup() { return cGroup; }
		
		//LeftHandSideExpression<Yield>
		public RuleCall getLeftHandSideExpressionParserRuleCall_0() { return cLeftHandSideExpressionParserRuleCall_0; }
		
		//=> ({PostfixExpression.expression=current} op=PostfixOperator)?
		public Group getGroup_1() { return cGroup_1; }
		
		//{PostfixExpression.expression=current} op=PostfixOperator
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{PostfixExpression.expression=current}
		public Action getPostfixExpressionExpressionAction_1_0_0() { return cPostfixExpressionExpressionAction_1_0_0; }
		
		///* no line terminator here */ op=PostfixOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//PostfixOperator
		public RuleCall getOpPostfixOperatorEnumRuleCall_1_0_1_0() { return cOpPostfixOperatorEnumRuleCall_1_0_1_0; }
	}
	public class CastExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.CastExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cPostfixExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cCastExpressionExpressionAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Keyword cAsKeyword_1_0_0_1 = (Keyword)cGroup_1_0_0.eContents().get(1);
		private final Assignment cTargetTypeRefAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cTargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0 = (RuleCall)cTargetTypeRefAssignment_1_1.eContents().get(0);
		
		///* Cast expression (N4JS 6.2.3) */ CastExpression <Yield Expression:
		//	PostfixExpression<Yield> (=> ({CastExpression.expression=current} 'as') targetTypeRef=ArrayTypeExpression)?;
		@Override public ParserRule getRule() { return rule; }
		
		//PostfixExpression<Yield> (=> ({CastExpression.expression=current} 'as') targetTypeRef=ArrayTypeExpression)?
		public Group getGroup() { return cGroup; }
		
		//PostfixExpression<Yield>
		public RuleCall getPostfixExpressionParserRuleCall_0() { return cPostfixExpressionParserRuleCall_0; }
		
		//(=> ({CastExpression.expression=current} 'as') targetTypeRef=ArrayTypeExpression)?
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({CastExpression.expression=current} 'as')
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{CastExpression.expression=current} 'as'
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{CastExpression.expression=current}
		public Action getCastExpressionExpressionAction_1_0_0_0() { return cCastExpressionExpressionAction_1_0_0_0; }
		
		//'as'
		public Keyword getAsKeyword_1_0_0_1() { return cAsKeyword_1_0_0_1; }
		
		//targetTypeRef=ArrayTypeExpression
		public Assignment getTargetTypeRefAssignment_1_1() { return cTargetTypeRefAssignment_1_1; }
		
		//ArrayTypeExpression
		public RuleCall getTargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0() { return cTargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0; }
	}
	public class UnaryExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.UnaryExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cCastExpressionParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Action cUnaryExpressionAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Assignment cOpAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cOpUnaryOperatorEnumRuleCall_1_1_0 = (RuleCall)cOpAssignment_1_1.eContents().get(0);
		private final Assignment cExpressionAssignment_1_2 = (Assignment)cGroup_1.eContents().get(2);
		private final RuleCall cExpressionUnaryExpressionParserRuleCall_1_2_0 = (RuleCall)cExpressionAssignment_1_2.eContents().get(0);
		
		///* Unary operators ([ECM11] 11.4) */ UnaryExpression <Yield Expression:
		//	CastExpression<Yield> | {UnaryExpression} op=UnaryOperator expression=UnaryExpression<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//CastExpression<Yield> | {UnaryExpression} op=UnaryOperator expression=UnaryExpression<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//CastExpression<Yield>
		public RuleCall getCastExpressionParserRuleCall_0() { return cCastExpressionParserRuleCall_0; }
		
		//{UnaryExpression} op=UnaryOperator expression=UnaryExpression<Yield>
		public Group getGroup_1() { return cGroup_1; }
		
		//{UnaryExpression}
		public Action getUnaryExpressionAction_1_0() { return cUnaryExpressionAction_1_0; }
		
		//op=UnaryOperator
		public Assignment getOpAssignment_1_1() { return cOpAssignment_1_1; }
		
		//UnaryOperator
		public RuleCall getOpUnaryOperatorEnumRuleCall_1_1_0() { return cOpUnaryOperatorEnumRuleCall_1_1_0; }
		
		//expression=UnaryExpression<Yield>
		public Assignment getExpressionAssignment_1_2() { return cExpressionAssignment_1_2; }
		
		//UnaryExpression<Yield>
		public RuleCall getExpressionUnaryExpressionParserRuleCall_1_2_0() { return cExpressionUnaryExpressionParserRuleCall_1_2_0; }
	}
	public class MultiplicativeExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.MultiplicativeExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cUnaryExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cMultiplicativeExpressionLhsAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cOpMultiplicativeOperatorEnumRuleCall_1_0_0_1_0 = (RuleCall)cOpAssignment_1_0_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRhsUnaryExpressionParserRuleCall_1_1_0 = (RuleCall)cRhsAssignment_1_1.eContents().get(0);
		
		///* Multiplicative operators ([ECM11] 11.5) */ MultiplicativeExpression <Yield Expression:
		//	UnaryExpression<Yield> (=> ({MultiplicativeExpression.lhs=current} op=MultiplicativeOperator)
		//	rhs=UnaryExpression<Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//UnaryExpression<Yield> (=> ({MultiplicativeExpression.lhs=current} op=MultiplicativeOperator)
		//rhs=UnaryExpression<Yield>)*
		public Group getGroup() { return cGroup; }
		
		//UnaryExpression<Yield>
		public RuleCall getUnaryExpressionParserRuleCall_0() { return cUnaryExpressionParserRuleCall_0; }
		
		//(=> ({MultiplicativeExpression.lhs=current} op=MultiplicativeOperator) rhs=UnaryExpression<Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({MultiplicativeExpression.lhs=current} op=MultiplicativeOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{MultiplicativeExpression.lhs=current} op=MultiplicativeOperator
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{MultiplicativeExpression.lhs=current}
		public Action getMultiplicativeExpressionLhsAction_1_0_0_0() { return cMultiplicativeExpressionLhsAction_1_0_0_0; }
		
		//op=MultiplicativeOperator
		public Assignment getOpAssignment_1_0_0_1() { return cOpAssignment_1_0_0_1; }
		
		//MultiplicativeOperator
		public RuleCall getOpMultiplicativeOperatorEnumRuleCall_1_0_0_1_0() { return cOpMultiplicativeOperatorEnumRuleCall_1_0_0_1_0; }
		
		//rhs=UnaryExpression<Yield>
		public Assignment getRhsAssignment_1_1() { return cRhsAssignment_1_1; }
		
		//UnaryExpression<Yield>
		public RuleCall getRhsUnaryExpressionParserRuleCall_1_1_0() { return cRhsUnaryExpressionParserRuleCall_1_1_0; }
	}
	public class AdditiveExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AdditiveExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cMultiplicativeExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cAdditiveExpressionLhsAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cOpAdditiveOperatorEnumRuleCall_1_0_0_1_0 = (RuleCall)cOpAssignment_1_0_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRhsMultiplicativeExpressionParserRuleCall_1_1_0 = (RuleCall)cRhsAssignment_1_1.eContents().get(0);
		
		///* Additive operators ([ECM11] 11.6) */ AdditiveExpression <Yield Expression:
		//	MultiplicativeExpression<Yield> (=> ({AdditiveExpression.lhs=current} op=AdditiveOperator)
		//	rhs=MultiplicativeExpression<Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//MultiplicativeExpression<Yield> (=> ({AdditiveExpression.lhs=current} op=AdditiveOperator)
		//rhs=MultiplicativeExpression<Yield>)*
		public Group getGroup() { return cGroup; }
		
		//MultiplicativeExpression<Yield>
		public RuleCall getMultiplicativeExpressionParserRuleCall_0() { return cMultiplicativeExpressionParserRuleCall_0; }
		
		//(=> ({AdditiveExpression.lhs=current} op=AdditiveOperator) rhs=MultiplicativeExpression<Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({AdditiveExpression.lhs=current} op=AdditiveOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{AdditiveExpression.lhs=current} op=AdditiveOperator
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{AdditiveExpression.lhs=current}
		public Action getAdditiveExpressionLhsAction_1_0_0_0() { return cAdditiveExpressionLhsAction_1_0_0_0; }
		
		//op=AdditiveOperator
		public Assignment getOpAssignment_1_0_0_1() { return cOpAssignment_1_0_0_1; }
		
		//AdditiveOperator
		public RuleCall getOpAdditiveOperatorEnumRuleCall_1_0_0_1_0() { return cOpAdditiveOperatorEnumRuleCall_1_0_0_1_0; }
		
		//rhs=MultiplicativeExpression<Yield>
		public Assignment getRhsAssignment_1_1() { return cRhsAssignment_1_1; }
		
		//MultiplicativeExpression<Yield>
		public RuleCall getRhsMultiplicativeExpressionParserRuleCall_1_1_0() { return cRhsMultiplicativeExpressionParserRuleCall_1_1_0; }
	}
	public class ShiftExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ShiftExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cAdditiveExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cShiftExpressionLhsAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpShiftOperatorParserRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_0_2 = (Assignment)cGroup_1_0.eContents().get(2);
		private final RuleCall cRhsAdditiveExpressionParserRuleCall_1_0_2_0 = (RuleCall)cRhsAssignment_1_0_2.eContents().get(0);
		
		//// Bitwise shift operators ([ECM11] 11.7)
		///**
		// * Note that the whole expression, including the rhs, must be in the syntactic
		// * predicate in order to avoid problems stemming from the parameterized function call
		// * and from the assignment operator >>>=
		// */ ShiftExpression <Yield Expression:
		//	AdditiveExpression<Yield> => ({ShiftExpression.lhs=current} op=ShiftOperator rhs=AdditiveExpression<Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//AdditiveExpression<Yield> => ({ShiftExpression.lhs=current} op=ShiftOperator rhs=AdditiveExpression<Yield>)*
		public Group getGroup() { return cGroup; }
		
		//AdditiveExpression<Yield>
		public RuleCall getAdditiveExpressionParserRuleCall_0() { return cAdditiveExpressionParserRuleCall_0; }
		
		//=> ({ShiftExpression.lhs=current} op=ShiftOperator rhs=AdditiveExpression<Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//{ShiftExpression.lhs=current} op=ShiftOperator rhs=AdditiveExpression<Yield>
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{ShiftExpression.lhs=current}
		public Action getShiftExpressionLhsAction_1_0_0() { return cShiftExpressionLhsAction_1_0_0; }
		
		//op=ShiftOperator
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//ShiftOperator
		public RuleCall getOpShiftOperatorParserRuleCall_1_0_1_0() { return cOpShiftOperatorParserRuleCall_1_0_1_0; }
		
		//rhs=AdditiveExpression<Yield>
		public Assignment getRhsAssignment_1_0_2() { return cRhsAssignment_1_0_2; }
		
		//AdditiveExpression<Yield>
		public RuleCall getRhsAdditiveExpressionParserRuleCall_1_0_2_0() { return cRhsAdditiveExpressionParserRuleCall_1_0_2_0; }
	}
	public class ShiftOperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ShiftOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_0_0 = (Keyword)cGroup_0.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Keyword cGreaterThanSignKeyword_0_2 = (Keyword)cGroup_0.eContents().get(2);
		private final Keyword cLessThanSignLessThanSignKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		
		///**  solve conflict with generics, e.g., List<List<C>> */ ShiftOperator ShiftOperator:
		//	'>' '>' '>'?
		//	| '<<';
		@Override public ParserRule getRule() { return rule; }
		
		//'>' '>' '>'? | '<<'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'>' '>' '>'?
		public Group getGroup_0() { return cGroup_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_0_0() { return cGreaterThanSignKeyword_0_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_0_1() { return cGreaterThanSignKeyword_0_1; }
		
		//'>'?
		public Keyword getGreaterThanSignKeyword_0_2() { return cGreaterThanSignKeyword_0_2; }
		
		//'<<'
		public Keyword getLessThanSignLessThanSignKeyword_1() { return cLessThanSignLessThanSignKeyword_1; }
	}
	public class RelationalExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.RelationalExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cShiftExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cRelationalExpressionLhsAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cOpRelationalOperatorParserRuleCall_1_0_1_0 = (RuleCall)cOpAssignment_1_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_0_2 = (Assignment)cGroup_1_0.eContents().get(2);
		private final RuleCall cRhsShiftExpressionParserRuleCall_1_0_2_0 = (RuleCall)cRhsAssignment_1_0_2.eContents().get(0);
		
		///*
		// * Note that the whole expression, including the rhs, must be in the syntactic
		// * predicate in order to avoid problems stemming from the parameterized function call
		// * and from the assignment operator >>>=
		// */ // Relational operators (11.8)
		//RelationalExpression <In, Yield Expression:
		//	ShiftExpression<Yield>
		//	=> ({RelationalExpression.lhs=current} op=RelationalOperator<In> -> rhs=ShiftExpression<Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//ShiftExpression<Yield> => ({RelationalExpression.lhs=current} op=RelationalOperator<In> -> rhs=ShiftExpression<Yield>)*
		public Group getGroup() { return cGroup; }
		
		//ShiftExpression<Yield>
		public RuleCall getShiftExpressionParserRuleCall_0() { return cShiftExpressionParserRuleCall_0; }
		
		//=> ({RelationalExpression.lhs=current} op=RelationalOperator<In> -> rhs=ShiftExpression<Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//{RelationalExpression.lhs=current} op=RelationalOperator<In> -> rhs=ShiftExpression<Yield>
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{RelationalExpression.lhs=current}
		public Action getRelationalExpressionLhsAction_1_0_0() { return cRelationalExpressionLhsAction_1_0_0; }
		
		//op=RelationalOperator<In>
		public Assignment getOpAssignment_1_0_1() { return cOpAssignment_1_0_1; }
		
		//RelationalOperator<In>
		public RuleCall getOpRelationalOperatorParserRuleCall_1_0_1_0() { return cOpRelationalOperatorParserRuleCall_1_0_1_0; }
		
		//-> rhs=ShiftExpression<Yield>
		public Assignment getRhsAssignment_1_0_2() { return cRhsAssignment_1_0_2; }
		
		//ShiftExpression<Yield>
		public RuleCall getRhsShiftExpressionParserRuleCall_1_0_2_0() { return cRhsShiftExpressionParserRuleCall_1_0_2_0; }
	}
	public class RelationalOperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.RelationalOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cLessThanSignKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Keyword cGreaterThanSignKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Keyword cEqualsSignKeyword_1_1 = (Keyword)cGroup_1.eContents().get(1);
		private final Keyword cLessThanSignEqualsSignKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cInstanceofKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Group cGroup_4 = (Group)cAlternatives.eContents().get(4);
		private final Keyword cInKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		
		//RelationalOperator <In RelationalOperator:
		//	'<' | '>' '='? | '<=' | 'instanceof' | <In> 'in';
		@Override public ParserRule getRule() { return rule; }
		
		//'<' | '>' '='? | '<=' | 'instanceof' | <In> 'in'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'<'
		public Keyword getLessThanSignKeyword_0() { return cLessThanSignKeyword_0; }
		
		//'>' '='?
		public Group getGroup_1() { return cGroup_1; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_1_0() { return cGreaterThanSignKeyword_1_0; }
		
		//'='?
		public Keyword getEqualsSignKeyword_1_1() { return cEqualsSignKeyword_1_1; }
		
		//'<='
		public Keyword getLessThanSignEqualsSignKeyword_2() { return cLessThanSignEqualsSignKeyword_2; }
		
		//'instanceof'
		public Keyword getInstanceofKeyword_3() { return cInstanceofKeyword_3; }
		
		//<In> 'in'
		public Group getGroup_4() { return cGroup_4; }
		
		//'in'
		public Keyword getInKeyword_4_0() { return cInKeyword_4_0; }
	}
	public class EqualityExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.EqualityExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cRelationalExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cEqualityExpressionLhsAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cOpEqualityOperatorEnumRuleCall_1_0_0_1_0 = (RuleCall)cOpAssignment_1_0_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRhsRelationalExpressionParserRuleCall_1_1_0 = (RuleCall)cRhsAssignment_1_1.eContents().get(0);
		
		//// Equality operators (11.9)
		//EqualityExpression <In, Yield Expression:
		//	RelationalExpression<In,Yield> (=> ({EqualityExpression.lhs=current} op=EqualityOperator)
		//	rhs=RelationalExpression<In,Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//RelationalExpression<In,Yield> (=> ({EqualityExpression.lhs=current} op=EqualityOperator)
		//rhs=RelationalExpression<In,Yield>)*
		public Group getGroup() { return cGroup; }
		
		//RelationalExpression<In,Yield>
		public RuleCall getRelationalExpressionParserRuleCall_0() { return cRelationalExpressionParserRuleCall_0; }
		
		//(=> ({EqualityExpression.lhs=current} op=EqualityOperator) rhs=RelationalExpression<In,Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({EqualityExpression.lhs=current} op=EqualityOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{EqualityExpression.lhs=current} op=EqualityOperator
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{EqualityExpression.lhs=current}
		public Action getEqualityExpressionLhsAction_1_0_0_0() { return cEqualityExpressionLhsAction_1_0_0_0; }
		
		//op=EqualityOperator
		public Assignment getOpAssignment_1_0_0_1() { return cOpAssignment_1_0_0_1; }
		
		//EqualityOperator
		public RuleCall getOpEqualityOperatorEnumRuleCall_1_0_0_1_0() { return cOpEqualityOperatorEnumRuleCall_1_0_0_1_0; }
		
		//rhs=RelationalExpression<In,Yield>
		public Assignment getRhsAssignment_1_1() { return cRhsAssignment_1_1; }
		
		//RelationalExpression<In,Yield>
		public RuleCall getRhsRelationalExpressionParserRuleCall_1_1_0() { return cRhsRelationalExpressionParserRuleCall_1_1_0; }
	}
	public class BitwiseANDExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BitwiseANDExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cEqualityExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cBinaryBitwiseExpressionLhsAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cOpBitwiseANDOperatorParserRuleCall_1_0_0_1_0 = (RuleCall)cOpAssignment_1_0_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRhsEqualityExpressionParserRuleCall_1_1_0 = (RuleCall)cRhsAssignment_1_1.eContents().get(0);
		
		//// Binary bitwise operators (11.10, N4JS Spec 6.1.17)
		//BitwiseANDExpression <In, Yield Expression:
		//	EqualityExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseANDOperator)
		//	rhs=EqualityExpression<In,Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//EqualityExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseANDOperator)
		//rhs=EqualityExpression<In,Yield>)*
		public Group getGroup() { return cGroup; }
		
		//EqualityExpression<In,Yield>
		public RuleCall getEqualityExpressionParserRuleCall_0() { return cEqualityExpressionParserRuleCall_0; }
		
		//(=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseANDOperator) rhs=EqualityExpression<In,Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseANDOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{BinaryBitwiseExpression.lhs=current} op=BitwiseANDOperator
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{BinaryBitwiseExpression.lhs=current}
		public Action getBinaryBitwiseExpressionLhsAction_1_0_0_0() { return cBinaryBitwiseExpressionLhsAction_1_0_0_0; }
		
		//op=BitwiseANDOperator
		public Assignment getOpAssignment_1_0_0_1() { return cOpAssignment_1_0_0_1; }
		
		//BitwiseANDOperator
		public RuleCall getOpBitwiseANDOperatorParserRuleCall_1_0_0_1_0() { return cOpBitwiseANDOperatorParserRuleCall_1_0_0_1_0; }
		
		//rhs=EqualityExpression<In,Yield>
		public Assignment getRhsAssignment_1_1() { return cRhsAssignment_1_1; }
		
		//EqualityExpression<In,Yield>
		public RuleCall getRhsEqualityExpressionParserRuleCall_1_1_0() { return cRhsEqualityExpressionParserRuleCall_1_1_0; }
	}
	public class BitwiseANDOperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BitwiseANDOperator");
		private final Keyword cAmpersandKeyword = (Keyword)rule.eContents().get(1);
		
		//BitwiseANDOperator BinaryBitwiseOperator:
		//	'&';
		@Override public ParserRule getRule() { return rule; }
		
		//'&'
		public Keyword getAmpersandKeyword() { return cAmpersandKeyword; }
	}
	public class BitwiseXORExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BitwiseXORExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cBitwiseANDExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cBinaryBitwiseExpressionLhsAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cOpBitwiseXOROperatorParserRuleCall_1_0_0_1_0 = (RuleCall)cOpAssignment_1_0_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRhsBitwiseANDExpressionParserRuleCall_1_1_0 = (RuleCall)cRhsAssignment_1_1.eContents().get(0);
		
		//BitwiseXORExpression <In, Yield Expression:
		//	BitwiseANDExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseXOROperator)
		//	rhs=BitwiseANDExpression<In,Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//BitwiseANDExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseXOROperator)
		//rhs=BitwiseANDExpression<In,Yield>)*
		public Group getGroup() { return cGroup; }
		
		//BitwiseANDExpression<In,Yield>
		public RuleCall getBitwiseANDExpressionParserRuleCall_0() { return cBitwiseANDExpressionParserRuleCall_0; }
		
		//(=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseXOROperator) rhs=BitwiseANDExpression<In,Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseXOROperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{BinaryBitwiseExpression.lhs=current} op=BitwiseXOROperator
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{BinaryBitwiseExpression.lhs=current}
		public Action getBinaryBitwiseExpressionLhsAction_1_0_0_0() { return cBinaryBitwiseExpressionLhsAction_1_0_0_0; }
		
		//op=BitwiseXOROperator
		public Assignment getOpAssignment_1_0_0_1() { return cOpAssignment_1_0_0_1; }
		
		//BitwiseXOROperator
		public RuleCall getOpBitwiseXOROperatorParserRuleCall_1_0_0_1_0() { return cOpBitwiseXOROperatorParserRuleCall_1_0_0_1_0; }
		
		//rhs=BitwiseANDExpression<In,Yield>
		public Assignment getRhsAssignment_1_1() { return cRhsAssignment_1_1; }
		
		//BitwiseANDExpression<In,Yield>
		public RuleCall getRhsBitwiseANDExpressionParserRuleCall_1_1_0() { return cRhsBitwiseANDExpressionParserRuleCall_1_1_0; }
	}
	public class BitwiseXOROperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BitwiseXOROperator");
		private final Keyword cCircumflexAccentKeyword = (Keyword)rule.eContents().get(1);
		
		//BitwiseXOROperator BinaryBitwiseOperator:
		//	'^';
		@Override public ParserRule getRule() { return rule; }
		
		//'^'
		public Keyword getCircumflexAccentKeyword() { return cCircumflexAccentKeyword; }
	}
	public class BitwiseORExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BitwiseORExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cBitwiseXORExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cBinaryBitwiseExpressionLhsAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cOpBitwiseOROperatorParserRuleCall_1_0_0_1_0 = (RuleCall)cOpAssignment_1_0_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRhsBitwiseXORExpressionParserRuleCall_1_1_0 = (RuleCall)cRhsAssignment_1_1.eContents().get(0);
		
		//BitwiseORExpression <In, Yield Expression:
		//	BitwiseXORExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator)
		//	rhs=BitwiseXORExpression<In,Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//BitwiseXORExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator)
		//rhs=BitwiseXORExpression<In,Yield>)*
		public Group getGroup() { return cGroup; }
		
		//BitwiseXORExpression<In,Yield>
		public RuleCall getBitwiseXORExpressionParserRuleCall_0() { return cBitwiseXORExpressionParserRuleCall_0; }
		
		//(=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator) rhs=BitwiseXORExpression<In,Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{BinaryBitwiseExpression.lhs=current}
		public Action getBinaryBitwiseExpressionLhsAction_1_0_0_0() { return cBinaryBitwiseExpressionLhsAction_1_0_0_0; }
		
		//op=BitwiseOROperator
		public Assignment getOpAssignment_1_0_0_1() { return cOpAssignment_1_0_0_1; }
		
		//BitwiseOROperator
		public RuleCall getOpBitwiseOROperatorParserRuleCall_1_0_0_1_0() { return cOpBitwiseOROperatorParserRuleCall_1_0_0_1_0; }
		
		//rhs=BitwiseXORExpression<In,Yield>
		public Assignment getRhsAssignment_1_1() { return cRhsAssignment_1_1; }
		
		//BitwiseXORExpression<In,Yield>
		public RuleCall getRhsBitwiseXORExpressionParserRuleCall_1_1_0() { return cRhsBitwiseXORExpressionParserRuleCall_1_1_0; }
	}
	public class BitwiseOROperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BitwiseOROperator");
		private final Keyword cVerticalLineKeyword = (Keyword)rule.eContents().get(1);
		
		//BitwiseOROperator BinaryBitwiseOperator:
		//	'|';
		@Override public ParserRule getRule() { return rule; }
		
		//'|'
		public Keyword getVerticalLineKeyword() { return cVerticalLineKeyword; }
	}
	public class LogicalANDExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LogicalANDExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cBitwiseORExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cBinaryLogicalExpressionLhsAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cOpLogicalANDOperatorParserRuleCall_1_0_0_1_0 = (RuleCall)cOpAssignment_1_0_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRhsBitwiseORExpressionParserRuleCall_1_1_0 = (RuleCall)cRhsAssignment_1_1.eContents().get(0);
		
		//// $<Binary logical operators ([ECM11] 11.11)
		//LogicalANDExpression <In, Yield Expression:
		//	BitwiseORExpression<In,Yield> (=> ({BinaryLogicalExpression.lhs=current} op=LogicalANDOperator)
		//	rhs=BitwiseORExpression<In,Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//BitwiseORExpression<In,Yield> (=> ({BinaryLogicalExpression.lhs=current} op=LogicalANDOperator)
		//rhs=BitwiseORExpression<In,Yield>)*
		public Group getGroup() { return cGroup; }
		
		//BitwiseORExpression<In,Yield>
		public RuleCall getBitwiseORExpressionParserRuleCall_0() { return cBitwiseORExpressionParserRuleCall_0; }
		
		//(=> ({BinaryLogicalExpression.lhs=current} op=LogicalANDOperator) rhs=BitwiseORExpression<In,Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({BinaryLogicalExpression.lhs=current} op=LogicalANDOperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{BinaryLogicalExpression.lhs=current} op=LogicalANDOperator
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{BinaryLogicalExpression.lhs=current}
		public Action getBinaryLogicalExpressionLhsAction_1_0_0_0() { return cBinaryLogicalExpressionLhsAction_1_0_0_0; }
		
		//op=LogicalANDOperator
		public Assignment getOpAssignment_1_0_0_1() { return cOpAssignment_1_0_0_1; }
		
		//LogicalANDOperator
		public RuleCall getOpLogicalANDOperatorParserRuleCall_1_0_0_1_0() { return cOpLogicalANDOperatorParserRuleCall_1_0_0_1_0; }
		
		//rhs=BitwiseORExpression<In,Yield>
		public Assignment getRhsAssignment_1_1() { return cRhsAssignment_1_1; }
		
		//BitwiseORExpression<In,Yield>
		public RuleCall getRhsBitwiseORExpressionParserRuleCall_1_1_0() { return cRhsBitwiseORExpressionParserRuleCall_1_1_0; }
	}
	public class LogicalANDOperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LogicalANDOperator");
		private final Keyword cAmpersandAmpersandKeyword = (Keyword)rule.eContents().get(1);
		
		//LogicalANDOperator BinaryLogicalOperator:
		//	'&&';
		@Override public ParserRule getRule() { return rule; }
		
		//'&&'
		public Keyword getAmpersandAmpersandKeyword() { return cAmpersandAmpersandKeyword; }
	}
	public class LogicalORExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LogicalORExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cLogicalANDExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cBinaryLogicalExpressionLhsAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Assignment cOpAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
		private final RuleCall cOpLogicalOROperatorParserRuleCall_1_0_0_1_0 = (RuleCall)cOpAssignment_1_0_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRhsLogicalANDExpressionParserRuleCall_1_1_0 = (RuleCall)cRhsAssignment_1_1.eContents().get(0);
		
		//LogicalORExpression <In, Yield Expression:
		//	LogicalANDExpression<In,Yield> (=> ({BinaryLogicalExpression.lhs=current} op=LogicalOROperator)
		//	rhs=LogicalANDExpression<In,Yield>)*;
		@Override public ParserRule getRule() { return rule; }
		
		//LogicalANDExpression<In,Yield> (=> ({BinaryLogicalExpression.lhs=current} op=LogicalOROperator)
		//rhs=LogicalANDExpression<In,Yield>)*
		public Group getGroup() { return cGroup; }
		
		//LogicalANDExpression<In,Yield>
		public RuleCall getLogicalANDExpressionParserRuleCall_0() { return cLogicalANDExpressionParserRuleCall_0; }
		
		//(=> ({BinaryLogicalExpression.lhs=current} op=LogicalOROperator) rhs=LogicalANDExpression<In,Yield>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({BinaryLogicalExpression.lhs=current} op=LogicalOROperator)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{BinaryLogicalExpression.lhs=current} op=LogicalOROperator
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{BinaryLogicalExpression.lhs=current}
		public Action getBinaryLogicalExpressionLhsAction_1_0_0_0() { return cBinaryLogicalExpressionLhsAction_1_0_0_0; }
		
		//op=LogicalOROperator
		public Assignment getOpAssignment_1_0_0_1() { return cOpAssignment_1_0_0_1; }
		
		//LogicalOROperator
		public RuleCall getOpLogicalOROperatorParserRuleCall_1_0_0_1_0() { return cOpLogicalOROperatorParserRuleCall_1_0_0_1_0; }
		
		//rhs=LogicalANDExpression<In,Yield>
		public Assignment getRhsAssignment_1_1() { return cRhsAssignment_1_1; }
		
		//LogicalANDExpression<In,Yield>
		public RuleCall getRhsLogicalANDExpressionParserRuleCall_1_1_0() { return cRhsLogicalANDExpressionParserRuleCall_1_1_0; }
	}
	public class LogicalOROperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LogicalOROperator");
		private final Keyword cVerticalLineVerticalLineKeyword = (Keyword)rule.eContents().get(1);
		
		//LogicalOROperator BinaryLogicalOperator:
		//	'||';
		@Override public ParserRule getRule() { return rule; }
		
		//'||'
		public Keyword getVerticalLineVerticalLineKeyword() { return cVerticalLineVerticalLineKeyword; }
	}
	public class ConditionalExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ConditionalExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cLogicalORExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Action cConditionalExpressionExpressionAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
		private final Keyword cQuestionMarkKeyword_1_0_0_1 = (Keyword)cGroup_1_0_0.eContents().get(1);
		private final Assignment cTrueExpressionAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cTrueExpressionAssignmentExpressionParserRuleCall_1_1_0 = (RuleCall)cTrueExpressionAssignment_1_1.eContents().get(0);
		private final Keyword cColonKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		private final Assignment cFalseExpressionAssignment_1_3 = (Assignment)cGroup_1.eContents().get(3);
		private final RuleCall cFalseExpressionAssignmentExpressionParserRuleCall_1_3_0 = (RuleCall)cFalseExpressionAssignment_1_3.eContents().get(0);
		
		///**
		// * Conditional operator ([ECM11] 11.12)
		// */ ConditionalExpression <In, Yield Expression:
		//	LogicalORExpression<In,Yield> (=> ({ConditionalExpression.expression=current} '?')
		//	trueExpression=AssignmentExpression<In=true,Yield> ':' falseExpression=AssignmentExpression<In,Yield>)?;
		@Override public ParserRule getRule() { return rule; }
		
		//LogicalORExpression<In,Yield> (=> ({ConditionalExpression.expression=current} '?')
		//trueExpression=AssignmentExpression<In=true,Yield> ':' falseExpression=AssignmentExpression<In,Yield>)?
		public Group getGroup() { return cGroup; }
		
		//LogicalORExpression<In,Yield>
		public RuleCall getLogicalORExpressionParserRuleCall_0() { return cLogicalORExpressionParserRuleCall_0; }
		
		//(=> ({ConditionalExpression.expression=current} '?') trueExpression=AssignmentExpression<In=true,Yield> ':'
		//falseExpression=AssignmentExpression<In,Yield>)?
		public Group getGroup_1() { return cGroup_1; }
		
		//=> ({ConditionalExpression.expression=current} '?')
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{ConditionalExpression.expression=current} '?'
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{ConditionalExpression.expression=current}
		public Action getConditionalExpressionExpressionAction_1_0_0_0() { return cConditionalExpressionExpressionAction_1_0_0_0; }
		
		//'?'
		public Keyword getQuestionMarkKeyword_1_0_0_1() { return cQuestionMarkKeyword_1_0_0_1; }
		
		//trueExpression=AssignmentExpression<In=true,Yield>
		public Assignment getTrueExpressionAssignment_1_1() { return cTrueExpressionAssignment_1_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getTrueExpressionAssignmentExpressionParserRuleCall_1_1_0() { return cTrueExpressionAssignmentExpressionParserRuleCall_1_1_0; }
		
		//':'
		public Keyword getColonKeyword_1_2() { return cColonKeyword_1_2; }
		
		//falseExpression=AssignmentExpression<In,Yield>
		public Assignment getFalseExpressionAssignment_1_3() { return cFalseExpressionAssignment_1_3; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getFalseExpressionAssignmentExpressionParserRuleCall_1_3_0() { return cFalseExpressionAssignmentExpressionParserRuleCall_1_3_0; }
	}
	public class AssignmentExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AssignmentExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cAwaitExpressionParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cPromisifyExpressionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cArrowExpressionParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final Group cGroup_3 = (Group)cAlternatives.eContents().get(3);
		private final RuleCall cYieldExpressionParserRuleCall_3_0 = (RuleCall)cGroup_3.eContents().get(0);
		private final Group cGroup_4 = (Group)cAlternatives.eContents().get(4);
		private final RuleCall cConditionalExpressionParserRuleCall_4_0 = (RuleCall)cGroup_4.eContents().get(0);
		private final Group cGroup_4_1 = (Group)cGroup_4.eContents().get(1);
		private final Group cGroup_4_1_0 = (Group)cGroup_4_1.eContents().get(0);
		private final Group cGroup_4_1_0_0 = (Group)cGroup_4_1_0.eContents().get(0);
		private final Action cAssignmentExpressionLhsAction_4_1_0_0_0 = (Action)cGroup_4_1_0_0.eContents().get(0);
		private final Assignment cOpAssignment_4_1_0_0_1 = (Assignment)cGroup_4_1_0_0.eContents().get(1);
		private final RuleCall cOpAssignmentOperatorParserRuleCall_4_1_0_0_1_0 = (RuleCall)cOpAssignment_4_1_0_0_1.eContents().get(0);
		private final Assignment cRhsAssignment_4_1_1 = (Assignment)cGroup_4_1.eContents().get(1);
		private final RuleCall cRhsAssignmentExpressionParserRuleCall_4_1_1_0 = (RuleCall)cRhsAssignment_4_1_1.eContents().get(0);
		
		///*
		// * Assignment operators ([ECM11] 11.13)
		// */ AssignmentExpression <In, Yield Expression:
		//	AwaitExpression<In,Yield> | PromisifyExpression<In,Yield> | ArrowExpression<In,Yield> | <Yield> YieldExpression<In> |
		//	ConditionalExpression<In,Yield> (=> ({AssignmentExpression.lhs=current} op=AssignmentOperator)
		//	rhs=AssignmentExpression<In,Yield>)?;
		@Override public ParserRule getRule() { return rule; }
		
		//AwaitExpression<In,Yield> | PromisifyExpression<In,Yield> | ArrowExpression<In,Yield> | <Yield> YieldExpression<In> |
		//ConditionalExpression<In,Yield> (=> ({AssignmentExpression.lhs=current} op=AssignmentOperator)
		//rhs=AssignmentExpression<In,Yield>)?
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//AwaitExpression<In,Yield>
		public RuleCall getAwaitExpressionParserRuleCall_0() { return cAwaitExpressionParserRuleCall_0; }
		
		//PromisifyExpression<In,Yield>
		public RuleCall getPromisifyExpressionParserRuleCall_1() { return cPromisifyExpressionParserRuleCall_1; }
		
		//ArrowExpression<In,Yield>
		public RuleCall getArrowExpressionParserRuleCall_2() { return cArrowExpressionParserRuleCall_2; }
		
		//<Yield> YieldExpression<In>
		public Group getGroup_3() { return cGroup_3; }
		
		//YieldExpression<In>
		public RuleCall getYieldExpressionParserRuleCall_3_0() { return cYieldExpressionParserRuleCall_3_0; }
		
		//ConditionalExpression<In,Yield> (=> ({AssignmentExpression.lhs=current} op=AssignmentOperator)
		//rhs=AssignmentExpression<In,Yield>)?
		public Group getGroup_4() { return cGroup_4; }
		
		//ConditionalExpression<In,Yield>
		public RuleCall getConditionalExpressionParserRuleCall_4_0() { return cConditionalExpressionParserRuleCall_4_0; }
		
		//(=> ({AssignmentExpression.lhs=current} op=AssignmentOperator) rhs=AssignmentExpression<In,Yield>)?
		public Group getGroup_4_1() { return cGroup_4_1; }
		
		//=> ({AssignmentExpression.lhs=current} op=AssignmentOperator)
		public Group getGroup_4_1_0() { return cGroup_4_1_0; }
		
		//{AssignmentExpression.lhs=current} op=AssignmentOperator
		public Group getGroup_4_1_0_0() { return cGroup_4_1_0_0; }
		
		//{AssignmentExpression.lhs=current}
		public Action getAssignmentExpressionLhsAction_4_1_0_0_0() { return cAssignmentExpressionLhsAction_4_1_0_0_0; }
		
		//op=AssignmentOperator
		public Assignment getOpAssignment_4_1_0_0_1() { return cOpAssignment_4_1_0_0_1; }
		
		//AssignmentOperator
		public RuleCall getOpAssignmentOperatorParserRuleCall_4_1_0_0_1_0() { return cOpAssignmentOperatorParserRuleCall_4_1_0_0_1_0; }
		
		//rhs=AssignmentExpression<In,Yield>
		public Assignment getRhsAssignment_4_1_1() { return cRhsAssignment_4_1_1; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getRhsAssignmentExpressionParserRuleCall_4_1_1_0() { return cRhsAssignmentExpressionParserRuleCall_4_1_1_0; }
	}
	public class YieldExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.YieldExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cYieldExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cYieldKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cManyAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cManyAsteriskKeyword_2_0 = (Keyword)cManyAssignment_2.eContents().get(0);
		private final Assignment cExpressionAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_3_0 = (RuleCall)cExpressionAssignment_3.eContents().get(0);
		
		//YieldExpression <In Expression:
		//	{YieldExpression} 'yield' => many?='*'? -> expression=AssignmentExpression<In,Yield=true>?;
		@Override public ParserRule getRule() { return rule; }
		
		//{YieldExpression} 'yield' => many?='*'? -> expression=AssignmentExpression<In,Yield=true>?
		public Group getGroup() { return cGroup; }
		
		//{YieldExpression}
		public Action getYieldExpressionAction_0() { return cYieldExpressionAction_0; }
		
		//'yield'
		public Keyword getYieldKeyword_1() { return cYieldKeyword_1; }
		
		//=> many?='*'?
		public Assignment getManyAssignment_2() { return cManyAssignment_2; }
		
		//'*'
		public Keyword getManyAsteriskKeyword_2_0() { return cManyAsteriskKeyword_2_0; }
		
		//-> expression=AssignmentExpression<In,Yield=true>?
		public Assignment getExpressionAssignment_3() { return cExpressionAssignment_3; }
		
		//AssignmentExpression<In,Yield=true>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_3_0() { return cExpressionAssignmentExpressionParserRuleCall_3_0; }
	}
	public class AssignmentOperatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AssignmentOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cEqualsSignKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cAsteriskEqualsSignKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cSolidusEqualsSignKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cPercentSignEqualsSignKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cPlusSignEqualsSignKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cHyphenMinusEqualsSignKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final Keyword cLessThanSignLessThanSignEqualsSignKeyword_6 = (Keyword)cAlternatives.eContents().get(6);
		private final Group cGroup_7 = (Group)cAlternatives.eContents().get(7);
		private final Keyword cGreaterThanSignKeyword_7_0 = (Keyword)cGroup_7.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_7_1 = (Keyword)cGroup_7.eContents().get(1);
		private final Keyword cGreaterThanSignKeyword_7_2 = (Keyword)cGroup_7.eContents().get(2);
		private final Keyword cEqualsSignKeyword_7_3 = (Keyword)cGroup_7.eContents().get(3);
		private final Keyword cAmpersandEqualsSignKeyword_8 = (Keyword)cAlternatives.eContents().get(8);
		private final Keyword cCircumflexAccentEqualsSignKeyword_9 = (Keyword)cAlternatives.eContents().get(9);
		private final Keyword cVerticalLineEqualsSignKeyword_10 = (Keyword)cAlternatives.eContents().get(10);
		
		//AssignmentOperator AssignmentOperator:
		//	'=' | '*=' | '/=' | '%=' | '+=' | '-='
		//	| '<<='
		//	| '>' '>' '>'? '='
		//	| '&=' | '^=' | '|=';
		@Override public ParserRule getRule() { return rule; }
		
		//'=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>' '>' '>'? '=' | '&=' | '^=' | '|='
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'='
		public Keyword getEqualsSignKeyword_0() { return cEqualsSignKeyword_0; }
		
		//'*='
		public Keyword getAsteriskEqualsSignKeyword_1() { return cAsteriskEqualsSignKeyword_1; }
		
		//'/='
		public Keyword getSolidusEqualsSignKeyword_2() { return cSolidusEqualsSignKeyword_2; }
		
		//'%='
		public Keyword getPercentSignEqualsSignKeyword_3() { return cPercentSignEqualsSignKeyword_3; }
		
		//'+='
		public Keyword getPlusSignEqualsSignKeyword_4() { return cPlusSignEqualsSignKeyword_4; }
		
		//'-='
		public Keyword getHyphenMinusEqualsSignKeyword_5() { return cHyphenMinusEqualsSignKeyword_5; }
		
		//'<<='
		public Keyword getLessThanSignLessThanSignEqualsSignKeyword_6() { return cLessThanSignLessThanSignEqualsSignKeyword_6; }
		
		//'>' '>' '>'? '='
		public Group getGroup_7() { return cGroup_7; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_7_0() { return cGreaterThanSignKeyword_7_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_7_1() { return cGreaterThanSignKeyword_7_1; }
		
		//'>'?
		public Keyword getGreaterThanSignKeyword_7_2() { return cGreaterThanSignKeyword_7_2; }
		
		//'='
		public Keyword getEqualsSignKeyword_7_3() { return cEqualsSignKeyword_7_3; }
		
		//'&='
		public Keyword getAmpersandEqualsSignKeyword_8() { return cAmpersandEqualsSignKeyword_8; }
		
		//'^='
		public Keyword getCircumflexAccentEqualsSignKeyword_9() { return cCircumflexAccentEqualsSignKeyword_9; }
		
		//'|='
		public Keyword getVerticalLineEqualsSignKeyword_10() { return cVerticalLineEqualsSignKeyword_10; }
	}
	public class AwaitExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AwaitExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cAwaitExpressionAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cAwaitKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_0 = (RuleCall)cExpressionAssignment_1.eContents().get(0);
		
		///*
		// * await should mimic precedence of 'yield' in [ECM15] (because it will be transpiled into a 'yield')
		// */ AwaitExpression <In, Yield Expression:
		//	=> ({AwaitExpression} 'await') expression=AssignmentExpression<In,Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({AwaitExpression} 'await') expression=AssignmentExpression<In,Yield>
		public Group getGroup() { return cGroup; }
		
		//=> ({AwaitExpression} 'await')
		public Group getGroup_0() { return cGroup_0; }
		
		//{AwaitExpression} 'await'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{AwaitExpression}
		public Action getAwaitExpressionAction_0_0_0() { return cAwaitExpressionAction_0_0_0; }
		
		//'await'
		public Keyword getAwaitKeyword_0_0_1() { return cAwaitKeyword_0_0_1; }
		
		//expression=AssignmentExpression<In,Yield>
		public Assignment getExpressionAssignment_1() { return cExpressionAssignment_1; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_0; }
	}
	public class PromisifyExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PromisifyExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cPromisifyExpressionAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cCommercialAtKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Keyword cPromisifyKeyword_0_0_2 = (Keyword)cGroup_0_0.eContents().get(2);
		private final Assignment cExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_0 = (RuleCall)cExpressionAssignment_1.eContents().get(0);
		
		//PromisifyExpression <In, Yield Expression:
		//	=> ({PromisifyExpression} '@' 'Promisify') expression=AssignmentExpression<In,Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({PromisifyExpression} '@' 'Promisify') expression=AssignmentExpression<In,Yield>
		public Group getGroup() { return cGroup; }
		
		//=> ({PromisifyExpression} '@' 'Promisify')
		public Group getGroup_0() { return cGroup_0; }
		
		//{PromisifyExpression} '@' 'Promisify'
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{PromisifyExpression}
		public Action getPromisifyExpressionAction_0_0_0() { return cPromisifyExpressionAction_0_0_0; }
		
		//'@'
		public Keyword getCommercialAtKeyword_0_0_1() { return cCommercialAtKeyword_0_0_1; }
		
		//'Promisify'
		public Keyword getPromisifyKeyword_0_0_2() { return cPromisifyKeyword_0_0_2; }
		
		//expression=AssignmentExpression<In,Yield>
		public Assignment getExpressionAssignment_1() { return cExpressionAssignment_1; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_0; }
	}
	public class ExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Expression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cAssignmentExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cCommaExpressionExprsAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Keyword cCommaKeyword_1_1 = (Keyword)cGroup_1.eContents().get(1);
		private final Assignment cExprsAssignment_1_2 = (Assignment)cGroup_1.eContents().get(2);
		private final RuleCall cExprsAssignmentExpressionParserRuleCall_1_2_0 = (RuleCall)cExprsAssignment_1_2.eContents().get(0);
		private final Group cGroup_1_3 = (Group)cGroup_1.eContents().get(3);
		private final Keyword cCommaKeyword_1_3_0 = (Keyword)cGroup_1_3.eContents().get(0);
		private final Assignment cExprsAssignment_1_3_1 = (Assignment)cGroup_1_3.eContents().get(1);
		private final RuleCall cExprsAssignmentExpressionParserRuleCall_1_3_1_0 = (RuleCall)cExprsAssignment_1_3_1.eContents().get(0);
		
		//// $<Comma operator (11.14)
		//Expression <In, Yield>:
		//	AssignmentExpression<In,Yield> ({CommaExpression.exprs+=current} ',' exprs+=AssignmentExpression<In,Yield> (','
		//	exprs+=AssignmentExpression<In,Yield>)*)?;
		@Override public ParserRule getRule() { return rule; }
		
		//AssignmentExpression<In,Yield> ({CommaExpression.exprs+=current} ',' exprs+=AssignmentExpression<In,Yield> (','
		//exprs+=AssignmentExpression<In,Yield>)*)?
		public Group getGroup() { return cGroup; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getAssignmentExpressionParserRuleCall_0() { return cAssignmentExpressionParserRuleCall_0; }
		
		//({CommaExpression.exprs+=current} ',' exprs+=AssignmentExpression<In,Yield> (','
		//exprs+=AssignmentExpression<In,Yield>)*)?
		public Group getGroup_1() { return cGroup_1; }
		
		//{CommaExpression.exprs+=current}
		public Action getCommaExpressionExprsAction_1_0() { return cCommaExpressionExprsAction_1_0; }
		
		//','
		public Keyword getCommaKeyword_1_1() { return cCommaKeyword_1_1; }
		
		//exprs+=AssignmentExpression<In,Yield>
		public Assignment getExprsAssignment_1_2() { return cExprsAssignment_1_2; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getExprsAssignmentExpressionParserRuleCall_1_2_0() { return cExprsAssignmentExpressionParserRuleCall_1_2_0; }
		
		//(',' exprs+=AssignmentExpression<In,Yield>)*
		public Group getGroup_1_3() { return cGroup_1_3; }
		
		//','
		public Keyword getCommaKeyword_1_3_0() { return cCommaKeyword_1_3_0; }
		
		//exprs+=AssignmentExpression<In,Yield>
		public Assignment getExprsAssignment_1_3_1() { return cExprsAssignment_1_3_1; }
		
		//AssignmentExpression<In,Yield>
		public RuleCall getExprsAssignmentExpressionParserRuleCall_1_3_1_0() { return cExprsAssignmentExpressionParserRuleCall_1_3_1_0; }
	}
	public class TemplateLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TemplateLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cTemplateLiteralAction_0 = (Action)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cSegmentsAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final RuleCall cSegmentsNoSubstitutionTemplateParserRuleCall_1_0_0 = (RuleCall)cSegmentsAssignment_1_0.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Assignment cSegmentsAssignment_1_1_0 = (Assignment)cGroup_1_1.eContents().get(0);
		private final RuleCall cSegmentsTemplateHeadParserRuleCall_1_1_0_0 = (RuleCall)cSegmentsAssignment_1_1_0.eContents().get(0);
		private final Assignment cSegmentsAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cSegmentsExpressionParserRuleCall_1_1_1_0 = (RuleCall)cSegmentsAssignment_1_1_1.eContents().get(0);
		private final RuleCall cTemplateExpressionEndParserRuleCall_1_1_2 = (RuleCall)cGroup_1_1.eContents().get(2);
		private final Group cGroup_1_1_3 = (Group)cGroup_1_1.eContents().get(3);
		private final Assignment cSegmentsAssignment_1_1_3_0 = (Assignment)cGroup_1_1_3.eContents().get(0);
		private final RuleCall cSegmentsTemplateMiddleParserRuleCall_1_1_3_0_0 = (RuleCall)cSegmentsAssignment_1_1_3_0.eContents().get(0);
		private final Assignment cSegmentsAssignment_1_1_3_1 = (Assignment)cGroup_1_1_3.eContents().get(1);
		private final RuleCall cSegmentsExpressionParserRuleCall_1_1_3_1_0 = (RuleCall)cSegmentsAssignment_1_1_3_1.eContents().get(0);
		private final RuleCall cTemplateExpressionEndParserRuleCall_1_1_3_2 = (RuleCall)cGroup_1_1_3.eContents().get(2);
		private final Assignment cSegmentsAssignment_1_1_4 = (Assignment)cGroup_1_1.eContents().get(4);
		private final RuleCall cSegmentsTemplateTailParserRuleCall_1_1_4_0 = (RuleCall)cSegmentsAssignment_1_1_4.eContents().get(0);
		
		//TemplateLiteral <Yield>:
		//	{TemplateLiteral} (segments+=NoSubstitutionTemplate
		//	| segments+=TemplateHead segments+=Expression<In=true,Yield>? TemplateExpressionEnd (segments+=TemplateMiddle
		//	segments+=Expression<In=true,Yield>? TemplateExpressionEnd)*
		//	segments+=TemplateTail);
		@Override public ParserRule getRule() { return rule; }
		
		//{TemplateLiteral} (segments+=NoSubstitutionTemplate | segments+=TemplateHead segments+=Expression<In=true,Yield>?
		//TemplateExpressionEnd (segments+=TemplateMiddle segments+=Expression<In=true,Yield>? TemplateExpressionEnd)*
		//segments+=TemplateTail)
		public Group getGroup() { return cGroup; }
		
		//{TemplateLiteral}
		public Action getTemplateLiteralAction_0() { return cTemplateLiteralAction_0; }
		
		//segments+=NoSubstitutionTemplate | segments+=TemplateHead segments+=Expression<In=true,Yield>? TemplateExpressionEnd
		//(segments+=TemplateMiddle segments+=Expression<In=true,Yield>? TemplateExpressionEnd)* segments+=TemplateTail
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//segments+=NoSubstitutionTemplate
		public Assignment getSegmentsAssignment_1_0() { return cSegmentsAssignment_1_0; }
		
		//NoSubstitutionTemplate
		public RuleCall getSegmentsNoSubstitutionTemplateParserRuleCall_1_0_0() { return cSegmentsNoSubstitutionTemplateParserRuleCall_1_0_0; }
		
		//segments+=TemplateHead segments+=Expression<In=true,Yield>? TemplateExpressionEnd (segments+=TemplateMiddle
		//segments+=Expression<In=true,Yield>? TemplateExpressionEnd)* segments+=TemplateTail
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//segments+=TemplateHead
		public Assignment getSegmentsAssignment_1_1_0() { return cSegmentsAssignment_1_1_0; }
		
		//TemplateHead
		public RuleCall getSegmentsTemplateHeadParserRuleCall_1_1_0_0() { return cSegmentsTemplateHeadParserRuleCall_1_1_0_0; }
		
		//segments+=Expression<In=true,Yield>?
		public Assignment getSegmentsAssignment_1_1_1() { return cSegmentsAssignment_1_1_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getSegmentsExpressionParserRuleCall_1_1_1_0() { return cSegmentsExpressionParserRuleCall_1_1_1_0; }
		
		//TemplateExpressionEnd
		public RuleCall getTemplateExpressionEndParserRuleCall_1_1_2() { return cTemplateExpressionEndParserRuleCall_1_1_2; }
		
		//(segments+=TemplateMiddle segments+=Expression<In=true,Yield>? TemplateExpressionEnd)*
		public Group getGroup_1_1_3() { return cGroup_1_1_3; }
		
		//segments+=TemplateMiddle
		public Assignment getSegmentsAssignment_1_1_3_0() { return cSegmentsAssignment_1_1_3_0; }
		
		//TemplateMiddle
		public RuleCall getSegmentsTemplateMiddleParserRuleCall_1_1_3_0_0() { return cSegmentsTemplateMiddleParserRuleCall_1_1_3_0_0; }
		
		//segments+=Expression<In=true,Yield>?
		public Assignment getSegmentsAssignment_1_1_3_1() { return cSegmentsAssignment_1_1_3_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getSegmentsExpressionParserRuleCall_1_1_3_1_0() { return cSegmentsExpressionParserRuleCall_1_1_3_1_0; }
		
		//TemplateExpressionEnd
		public RuleCall getTemplateExpressionEndParserRuleCall_1_1_3_2() { return cTemplateExpressionEndParserRuleCall_1_1_3_2; }
		
		//segments+=TemplateTail
		public Assignment getSegmentsAssignment_1_1_4() { return cSegmentsAssignment_1_1_4; }
		
		//TemplateTail
		public RuleCall getSegmentsTemplateTailParserRuleCall_1_1_4_0() { return cSegmentsTemplateTailParserRuleCall_1_1_4_0; }
	}
	public class TemplateExpressionEndElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TemplateExpressionEnd");
		private final Keyword cRightCurlyBracketKeyword = (Keyword)rule.eContents().get(1);
		
		//TemplateExpressionEnd:
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword() { return cRightCurlyBracketKeyword; }
	}
	public class NoSubstitutionTemplateElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.NoSubstitutionTemplate");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cTemplateSegmentAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cValueAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cValueNO_SUBSTITUTION_TEMPLATE_LITERALTerminalRuleCall_1_0 = (RuleCall)cValueAssignment_1.eContents().get(0);
		
		//NoSubstitutionTemplate TemplateSegment:
		//	{TemplateSegment} value=NO_SUBSTITUTION_TEMPLATE_LITERAL;
		@Override public ParserRule getRule() { return rule; }
		
		//{TemplateSegment} value=NO_SUBSTITUTION_TEMPLATE_LITERAL
		public Group getGroup() { return cGroup; }
		
		//{TemplateSegment}
		public Action getTemplateSegmentAction_0() { return cTemplateSegmentAction_0; }
		
		//value=NO_SUBSTITUTION_TEMPLATE_LITERAL
		public Assignment getValueAssignment_1() { return cValueAssignment_1; }
		
		//NO_SUBSTITUTION_TEMPLATE_LITERAL
		public RuleCall getValueNO_SUBSTITUTION_TEMPLATE_LITERALTerminalRuleCall_1_0() { return cValueNO_SUBSTITUTION_TEMPLATE_LITERALTerminalRuleCall_1_0; }
	}
	public class TemplateHeadElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TemplateHead");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cTemplateSegmentAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cValueAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cValueTEMPLATE_HEADTerminalRuleCall_1_0 = (RuleCall)cValueAssignment_1.eContents().get(0);
		
		//TemplateHead TemplateSegment:
		//	{TemplateSegment} value=TEMPLATE_HEAD;
		@Override public ParserRule getRule() { return rule; }
		
		//{TemplateSegment} value=TEMPLATE_HEAD
		public Group getGroup() { return cGroup; }
		
		//{TemplateSegment}
		public Action getTemplateSegmentAction_0() { return cTemplateSegmentAction_0; }
		
		//value=TEMPLATE_HEAD
		public Assignment getValueAssignment_1() { return cValueAssignment_1; }
		
		//TEMPLATE_HEAD
		public RuleCall getValueTEMPLATE_HEADTerminalRuleCall_1_0() { return cValueTEMPLATE_HEADTerminalRuleCall_1_0; }
	}
	public class TemplateTailElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TemplateTail");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cTemplateSegmentAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cValueAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cValueTemplateTailLiteralParserRuleCall_1_0 = (RuleCall)cValueAssignment_1.eContents().get(0);
		
		//TemplateTail TemplateSegment:
		//	{TemplateSegment} value=TemplateTailLiteral;
		@Override public ParserRule getRule() { return rule; }
		
		//{TemplateSegment} value=TemplateTailLiteral
		public Group getGroup() { return cGroup; }
		
		//{TemplateSegment}
		public Action getTemplateSegmentAction_0() { return cTemplateSegmentAction_0; }
		
		//value=TemplateTailLiteral
		public Assignment getValueAssignment_1() { return cValueAssignment_1; }
		
		//TemplateTailLiteral
		public RuleCall getValueTemplateTailLiteralParserRuleCall_1_0() { return cValueTemplateTailLiteralParserRuleCall_1_0; }
	}
	public class TemplateMiddleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TemplateMiddle");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cTemplateSegmentAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cValueAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cValueTemplateMiddleLiteralParserRuleCall_1_0 = (RuleCall)cValueAssignment_1.eContents().get(0);
		
		//TemplateMiddle TemplateSegment:
		//	{TemplateSegment} value=TemplateMiddleLiteral;
		@Override public ParserRule getRule() { return rule; }
		
		//{TemplateSegment} value=TemplateMiddleLiteral
		public Group getGroup() { return cGroup; }
		
		//{TemplateSegment}
		public Action getTemplateSegmentAction_0() { return cTemplateSegmentAction_0; }
		
		//value=TemplateMiddleLiteral
		public Assignment getValueAssignment_1() { return cValueAssignment_1; }
		
		//TemplateMiddleLiteral
		public RuleCall getValueTemplateMiddleLiteralParserRuleCall_1_0() { return cValueTemplateMiddleLiteralParserRuleCall_1_0; }
	}
	public class LiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Literal");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cNumericLiteralParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cBooleanLiteralParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cStringLiteralParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cNullLiteralParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cRegularExpressionLiteralParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		
		//// ****************************************************************************************************
		//// [ECM11] A.1 Lexical Grammar (p. 211)
		//// note: 'undefined' is not a literal, but a property of the built-in global object
		//// ****************************************************************************************************
		//Literal:
		//	NumericLiteral | BooleanLiteral | StringLiteral | NullLiteral | RegularExpressionLiteral;
		@Override public ParserRule getRule() { return rule; }
		
		//NumericLiteral | BooleanLiteral | StringLiteral | NullLiteral | RegularExpressionLiteral
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//NumericLiteral
		public RuleCall getNumericLiteralParserRuleCall_0() { return cNumericLiteralParserRuleCall_0; }
		
		//BooleanLiteral
		public RuleCall getBooleanLiteralParserRuleCall_1() { return cBooleanLiteralParserRuleCall_1; }
		
		//StringLiteral
		public RuleCall getStringLiteralParserRuleCall_2() { return cStringLiteralParserRuleCall_2; }
		
		//NullLiteral
		public RuleCall getNullLiteralParserRuleCall_3() { return cNullLiteralParserRuleCall_3; }
		
		//RegularExpressionLiteral
		public RuleCall getRegularExpressionLiteralParserRuleCall_4() { return cRegularExpressionLiteralParserRuleCall_4; }
	}
	public class NullLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.NullLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cNullLiteralAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cNullKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//NullLiteral:
		//	{NullLiteral} 'null';
		@Override public ParserRule getRule() { return rule; }
		
		//{NullLiteral} 'null'
		public Group getGroup() { return cGroup; }
		
		//{NullLiteral}
		public Action getNullLiteralAction_0() { return cNullLiteralAction_0; }
		
		//'null'
		public Keyword getNullKeyword_1() { return cNullKeyword_1; }
	}
	public class BooleanLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BooleanLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cBooleanLiteralAction_0 = (Action)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cTrueAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final Keyword cTrueTrueKeyword_1_0_0 = (Keyword)cTrueAssignment_1_0.eContents().get(0);
		private final Keyword cFalseKeyword_1_1 = (Keyword)cAlternatives_1.eContents().get(1);
		
		//BooleanLiteral:
		//	{BooleanLiteral} (true?='true' | 'false');
		@Override public ParserRule getRule() { return rule; }
		
		//{BooleanLiteral} (true?='true' | 'false')
		public Group getGroup() { return cGroup; }
		
		//{BooleanLiteral}
		public Action getBooleanLiteralAction_0() { return cBooleanLiteralAction_0; }
		
		//true?='true' | 'false'
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//true?='true'
		public Assignment getTrueAssignment_1_0() { return cTrueAssignment_1_0; }
		
		//'true'
		public Keyword getTrueTrueKeyword_1_0_0() { return cTrueTrueKeyword_1_0_0; }
		
		//'false'
		public Keyword getFalseKeyword_1_1() { return cFalseKeyword_1_1; }
	}
	public class StringLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.StringLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueSTRINGTerminalRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//StringLiteral:
		//	value=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//value=STRING
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//STRING
		public RuleCall getValueSTRINGTerminalRuleCall_0() { return cValueSTRINGTerminalRuleCall_0; }
	}
	public class NumericLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.NumericLiteral");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cDoubleLiteralParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cIntLiteralParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cBinaryIntLiteralParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cOctalIntLiteralParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cLegacyOctalIntLiteralParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cHexIntLiteralParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cScientificIntLiteralParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		
		//NumericLiteral:
		//	DoubleLiteral | IntLiteral | BinaryIntLiteral | OctalIntLiteral | LegacyOctalIntLiteral | HexIntLiteral |
		//	ScientificIntLiteral;
		@Override public ParserRule getRule() { return rule; }
		
		//DoubleLiteral | IntLiteral | BinaryIntLiteral | OctalIntLiteral | LegacyOctalIntLiteral | HexIntLiteral |
		//ScientificIntLiteral
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//DoubleLiteral
		public RuleCall getDoubleLiteralParserRuleCall_0() { return cDoubleLiteralParserRuleCall_0; }
		
		//IntLiteral
		public RuleCall getIntLiteralParserRuleCall_1() { return cIntLiteralParserRuleCall_1; }
		
		//BinaryIntLiteral
		public RuleCall getBinaryIntLiteralParserRuleCall_2() { return cBinaryIntLiteralParserRuleCall_2; }
		
		//OctalIntLiteral
		public RuleCall getOctalIntLiteralParserRuleCall_3() { return cOctalIntLiteralParserRuleCall_3; }
		
		//LegacyOctalIntLiteral
		public RuleCall getLegacyOctalIntLiteralParserRuleCall_4() { return cLegacyOctalIntLiteralParserRuleCall_4; }
		
		//HexIntLiteral
		public RuleCall getHexIntLiteralParserRuleCall_5() { return cHexIntLiteralParserRuleCall_5; }
		
		//ScientificIntLiteral
		public RuleCall getScientificIntLiteralParserRuleCall_6() { return cScientificIntLiteralParserRuleCall_6; }
	}
	public class DoubleLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.DoubleLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueDOUBLETerminalRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//DoubleLiteral:
		//	value=DOUBLE;
		@Override public ParserRule getRule() { return rule; }
		
		//value=DOUBLE
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//DOUBLE
		public RuleCall getValueDOUBLETerminalRuleCall_0() { return cValueDOUBLETerminalRuleCall_0; }
	}
	public class IntLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.IntLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueINTTerminalRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//IntLiteral:
		//	value=INT;
		@Override public ParserRule getRule() { return rule; }
		
		//value=INT
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//INT
		public RuleCall getValueINTTerminalRuleCall_0() { return cValueINTTerminalRuleCall_0; }
	}
	public class OctalIntLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.OctalIntLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueOCTAL_INTTerminalRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//OctalIntLiteral:
		//	value=OCTAL_INT;
		@Override public ParserRule getRule() { return rule; }
		
		//value=OCTAL_INT
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//OCTAL_INT
		public RuleCall getValueOCTAL_INTTerminalRuleCall_0() { return cValueOCTAL_INTTerminalRuleCall_0; }
	}
	public class LegacyOctalIntLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LegacyOctalIntLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueLEGACY_OCTAL_INTTerminalRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//LegacyOctalIntLiteral:
		//	value=LEGACY_OCTAL_INT;
		@Override public ParserRule getRule() { return rule; }
		
		//value=LEGACY_OCTAL_INT
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//LEGACY_OCTAL_INT
		public RuleCall getValueLEGACY_OCTAL_INTTerminalRuleCall_0() { return cValueLEGACY_OCTAL_INTTerminalRuleCall_0; }
	}
	public class HexIntLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.HexIntLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueHEX_INTTerminalRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//HexIntLiteral:
		//	value=HEX_INT;
		@Override public ParserRule getRule() { return rule; }
		
		//value=HEX_INT
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//HEX_INT
		public RuleCall getValueHEX_INTTerminalRuleCall_0() { return cValueHEX_INTTerminalRuleCall_0; }
	}
	public class BinaryIntLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BinaryIntLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueBINARY_INTTerminalRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//BinaryIntLiteral:
		//	value=BINARY_INT;
		@Override public ParserRule getRule() { return rule; }
		
		//value=BINARY_INT
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//BINARY_INT
		public RuleCall getValueBINARY_INTTerminalRuleCall_0() { return cValueBINARY_INTTerminalRuleCall_0; }
	}
	public class ScientificIntLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ScientificIntLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueSCIENTIFIC_INTTerminalRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//ScientificIntLiteral:
		//	value=SCIENTIFIC_INT;
		@Override public ParserRule getRule() { return rule; }
		
		//value=SCIENTIFIC_INT
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//SCIENTIFIC_INT
		public RuleCall getValueSCIENTIFIC_INTTerminalRuleCall_0() { return cValueSCIENTIFIC_INTTerminalRuleCall_0; }
	}
	public class RegularExpressionLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.RegularExpressionLiteral");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueREGEX_LITERALParserRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//RegularExpressionLiteral:
		//	value=REGEX_LITERAL;
		@Override public ParserRule getRule() { return rule; }
		
		//value=REGEX_LITERAL
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//REGEX_LITERAL
		public RuleCall getValueREGEX_LITERALParserRuleCall_0() { return cValueREGEX_LITERALParserRuleCall_0; }
	}
	public class NumericLiteralAsStringElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.NumericLiteralAsString");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cDOUBLETerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cINTTerminalRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cOCTAL_INTTerminalRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cHEX_INTTerminalRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cSCIENTIFIC_INTTerminalRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		
		//NumericLiteralAsString:
		//	DOUBLE | INT | OCTAL_INT | HEX_INT | SCIENTIFIC_INT;
		@Override public ParserRule getRule() { return rule; }
		
		//DOUBLE | INT | OCTAL_INT | HEX_INT | SCIENTIFIC_INT
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//DOUBLE
		public RuleCall getDOUBLETerminalRuleCall_0() { return cDOUBLETerminalRuleCall_0; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_1() { return cINTTerminalRuleCall_1; }
		
		//OCTAL_INT
		public RuleCall getOCTAL_INTTerminalRuleCall_2() { return cOCTAL_INTTerminalRuleCall_2; }
		
		//HEX_INT
		public RuleCall getHEX_INTTerminalRuleCall_3() { return cHEX_INTTerminalRuleCall_3; }
		
		//SCIENTIFIC_INT
		public RuleCall getSCIENTIFIC_INTTerminalRuleCall_4() { return cSCIENTIFIC_INTTerminalRuleCall_4; }
	}
	public class IdentifierOrThisElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.IdentifierOrThis");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cIDENTIFIERTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Keyword cThisKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cPromisifyKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cTargetKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		
		//IdentifierOrThis:
		//	IDENTIFIER
		//	| 'This'
		//	| 'Promisify'
		//	| 'target';
		@Override public ParserRule getRule() { return rule; }
		
		//IDENTIFIER | 'This' | 'Promisify' | 'target'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_0() { return cIDENTIFIERTerminalRuleCall_0; }
		
		//'This'
		public Keyword getThisKeyword_1() { return cThisKeyword_1; }
		
		//'Promisify'
		public Keyword getPromisifyKeyword_2() { return cPromisifyKeyword_2; }
		
		//'target'
		public Keyword getTargetKeyword_3() { return cTargetKeyword_3; }
	}
	public class AnnotationNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotationName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cIDENTIFIERTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Keyword cThisKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cTargetKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		
		//AnnotationName:
		//	IDENTIFIER
		//	| 'This'
		//	| 'target';
		@Override public ParserRule getRule() { return rule; }
		
		//IDENTIFIER | 'This' | 'target'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_0() { return cIDENTIFIERTerminalRuleCall_0; }
		
		//'This'
		public Keyword getThisKeyword_1() { return cThisKeyword_1; }
		
		//'target'
		public Keyword getTargetKeyword_2() { return cTargetKeyword_2; }
	}
	public class REGEX_LITERALElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.REGEX_LITERAL");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Keyword cSolidusKeyword_0_0 = (Keyword)cAlternatives_0.eContents().get(0);
		private final Keyword cSolidusEqualsSignKeyword_0_1 = (Keyword)cAlternatives_0.eContents().get(1);
		private final RuleCall cREGEX_TAILTerminalRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		///**
		// * The regex literal is not very strict in the sense that the trailing parts are optional.
		// * This is to improve the error recovery in the generated lexer and parser. If the trailing slash
		// * was mandatory, the lexer would brick and the parser would not sync properly. Therefore
		// * we rely on value converters and validation to check the regex literals.
		// */ REGEX_LITERAL:
		//	('/' | '/=') REGEX_TAIL?;
		@Override public ParserRule getRule() { return rule; }
		
		//('/' | '/=') REGEX_TAIL?
		public Group getGroup() { return cGroup; }
		
		//'/' | '/='
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//'/'
		public Keyword getSolidusKeyword_0_0() { return cSolidusKeyword_0_0; }
		
		//'/='
		public Keyword getSolidusEqualsSignKeyword_0_1() { return cSolidusEqualsSignKeyword_0_1; }
		
		//REGEX_TAIL?
		public RuleCall getREGEX_TAILTerminalRuleCall_1() { return cREGEX_TAILTerminalRuleCall_1; }
	}
	public class TemplateTailLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TemplateTailLiteral");
		private final RuleCall cTEMPLATE_ENDTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		//TemplateTailLiteral:
		//	TEMPLATE_END?;
		@Override public ParserRule getRule() { return rule; }
		
		//TEMPLATE_END?
		public RuleCall getTEMPLATE_ENDTerminalRuleCall() { return cTEMPLATE_ENDTerminalRuleCall; }
	}
	public class TemplateMiddleLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TemplateMiddleLiteral");
		private final RuleCall cTEMPLATE_MIDDLETerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		//TemplateMiddleLiteral:
		//	TEMPLATE_MIDDLE;
		@Override public ParserRule getRule() { return rule; }
		
		//TEMPLATE_MIDDLE
		public RuleCall getTEMPLATE_MIDDLETerminalRuleCall() { return cTEMPLATE_MIDDLETerminalRuleCall; }
	}
	public class SemiElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Semi");
		private final Keyword cSemicolonKeyword = (Keyword)rule.eContents().get(1);
		
		//// ****************************************************************************************************
		//// Helpers
		//// ****************************************************************************************************
		///**
		// * Placeholder, will be replaced by manually written ANTLR rule.
		// * This rule handles semicolons reported by the lexer and situations where the ECMA 3 specification states there should be semicolons automatically inserted.
		// * The auto semicolons are not actually inserted but this rule behaves as if they were.
		// */ Semi:
		//	';';
		@Override public ParserRule getRule() { return rule; }
		
		//';'
		public Keyword getSemicolonKeyword() { return cSemicolonKeyword; }
	}
	public class NoLineTerminatorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.NoLineTerminator");
		private final RuleCall cNO_LINE_TERMINATORTerminalRuleCall = (RuleCall)rule.eContents().get(0);
		
		///**
		// * Will be completely replaced during post processing, need some dummy token to be able to define rule.
		// */ fragment NoLineTerminator *:
		//	NO_LINE_TERMINATOR?;
		@Override public ParserRule getRule() { return rule; }
		
		//NO_LINE_TERMINATOR?
		public RuleCall getNO_LINE_TERMINATORTerminalRuleCall() { return cNO_LINE_TERMINATORTerminalRuleCall; }
	}
	public class AnnotationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Annotation");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cCommercialAtKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final RuleCall cAnnotationNoAtSignParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//// ****************************************************************************************************
		//// N4JS Specific
		//// ****************************************************************************************************
		//// ****************************************************************************************************
		//// Annotations
		//// ****************************************************************************************************
		//// cf. N4JSSpec §9
		//Annotation:
		//	'@' AnnotationNoAtSign;
		@Override public ParserRule getRule() { return rule; }
		
		//'@' AnnotationNoAtSign
		public Group getGroup() { return cGroup; }
		
		//'@'
		public Keyword getCommercialAtKeyword_0() { return cCommercialAtKeyword_0; }
		
		//AnnotationNoAtSign
		public RuleCall getAnnotationNoAtSignParserRuleCall_1() { return cAnnotationNoAtSignParserRuleCall_1; }
	}
	public class ScriptAnnotationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ScriptAnnotation");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cCommercialAtCommercialAtKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final RuleCall cAnnotationNoAtSignParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//ScriptAnnotation Annotation:
		//	'@@' AnnotationNoAtSign;
		@Override public ParserRule getRule() { return rule; }
		
		//'@@' AnnotationNoAtSign
		public Group getGroup() { return cGroup; }
		
		//'@@'
		public Keyword getCommercialAtCommercialAtKeyword_0() { return cCommercialAtCommercialAtKeyword_0; }
		
		//AnnotationNoAtSign
		public RuleCall getAnnotationNoAtSignParserRuleCall_1() { return cAnnotationNoAtSignParserRuleCall_1; }
	}
	public class AnnotationNoAtSignElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotationNoAtSign");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cNameAnnotationNameParserRuleCall_0_0 = (RuleCall)cNameAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Assignment cArgsAssignment_1_1_0 = (Assignment)cGroup_1_1.eContents().get(0);
		private final RuleCall cArgsAnnotationArgumentParserRuleCall_1_1_0_0 = (RuleCall)cArgsAssignment_1_1_0.eContents().get(0);
		private final Group cGroup_1_1_1 = (Group)cGroup_1_1.eContents().get(1);
		private final Keyword cCommaKeyword_1_1_1_0 = (Keyword)cGroup_1_1_1.eContents().get(0);
		private final Assignment cArgsAssignment_1_1_1_1 = (Assignment)cGroup_1_1_1.eContents().get(1);
		private final RuleCall cArgsAnnotationArgumentParserRuleCall_1_1_1_1_0 = (RuleCall)cArgsAssignment_1_1_1_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		
		//AnnotationNoAtSign Annotation:
		//	name=AnnotationName (=> '(' (args+=AnnotationArgument (',' args+=AnnotationArgument)*)? ')')?;
		@Override public ParserRule getRule() { return rule; }
		
		//name=AnnotationName (=> '(' (args+=AnnotationArgument (',' args+=AnnotationArgument)*)? ')')?
		public Group getGroup() { return cGroup; }
		
		//name=AnnotationName
		public Assignment getNameAssignment_0() { return cNameAssignment_0; }
		
		//AnnotationName
		public RuleCall getNameAnnotationNameParserRuleCall_0_0() { return cNameAnnotationNameParserRuleCall_0_0; }
		
		//(=> '(' (args+=AnnotationArgument (',' args+=AnnotationArgument)*)? ')')?
		public Group getGroup_1() { return cGroup_1; }
		
		//=> '('
		public Keyword getLeftParenthesisKeyword_1_0() { return cLeftParenthesisKeyword_1_0; }
		
		//(args+=AnnotationArgument (',' args+=AnnotationArgument)*)?
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//args+=AnnotationArgument
		public Assignment getArgsAssignment_1_1_0() { return cArgsAssignment_1_1_0; }
		
		//AnnotationArgument
		public RuleCall getArgsAnnotationArgumentParserRuleCall_1_1_0_0() { return cArgsAnnotationArgumentParserRuleCall_1_1_0_0; }
		
		//(',' args+=AnnotationArgument)*
		public Group getGroup_1_1_1() { return cGroup_1_1_1; }
		
		//','
		public Keyword getCommaKeyword_1_1_1_0() { return cCommaKeyword_1_1_1_0; }
		
		//args+=AnnotationArgument
		public Assignment getArgsAssignment_1_1_1_1() { return cArgsAssignment_1_1_1_1; }
		
		//AnnotationArgument
		public RuleCall getArgsAnnotationArgumentParserRuleCall_1_1_1_1_0() { return cArgsAnnotationArgumentParserRuleCall_1_1_1_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_1_2() { return cRightParenthesisKeyword_1_2; }
	}
	public class AnnotationArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotationArgument");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cLiteralAnnotationArgumentParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cTypeRefAnnotationArgumentParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//AnnotationArgument:
		//	LiteralAnnotationArgument | TypeRefAnnotationArgument;
		@Override public ParserRule getRule() { return rule; }
		
		//LiteralAnnotationArgument | TypeRefAnnotationArgument
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//LiteralAnnotationArgument
		public RuleCall getLiteralAnnotationArgumentParserRuleCall_0() { return cLiteralAnnotationArgumentParserRuleCall_0; }
		
		//TypeRefAnnotationArgument
		public RuleCall getTypeRefAnnotationArgumentParserRuleCall_1() { return cTypeRefAnnotationArgumentParserRuleCall_1; }
	}
	public class LiteralAnnotationArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LiteralAnnotationArgument");
		private final Assignment cLiteralAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cLiteralLiteralParserRuleCall_0 = (RuleCall)cLiteralAssignment.eContents().get(0);
		
		//LiteralAnnotationArgument:
		//	literal=Literal;
		@Override public ParserRule getRule() { return rule; }
		
		//literal=Literal
		public Assignment getLiteralAssignment() { return cLiteralAssignment; }
		
		//Literal
		public RuleCall getLiteralLiteralParserRuleCall_0() { return cLiteralLiteralParserRuleCall_0; }
	}
	public class TypeRefAnnotationArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TypeRefAnnotationArgument");
		private final Assignment cTypeRefAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cTypeRefTypeRefParserRuleCall_0 = (RuleCall)cTypeRefAssignment.eContents().get(0);
		
		//TypeRefAnnotationArgument:
		//	typeRef=TypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//typeRef=TypeRef
		public Assignment getTypeRefAssignment() { return cTypeRefAssignment; }
		
		//TypeRef
		public RuleCall getTypeRefTypeRefParserRuleCall_0() { return cTypeRefTypeRefParserRuleCall_0; }
	}
	public class AnnotationListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotationList");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cAnnotationListAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Keyword cCommercialAtKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cAnnotationsAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cAnnotationsAnnotationNoAtSignParserRuleCall_0_0_2_0 = (RuleCall)cAnnotationsAssignment_0_0_2.eContents().get(0);
		private final Assignment cAnnotationsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cAnnotationsAnnotationParserRuleCall_1_0 = (RuleCall)cAnnotationsAssignment_1.eContents().get(0);
		
		//AnnotationList:
		//	=> ({AnnotationList} '@' -> annotations+=AnnotationNoAtSign) annotations+=Annotation*;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({AnnotationList} '@' -> annotations+=AnnotationNoAtSign) annotations+=Annotation*
		public Group getGroup() { return cGroup; }
		
		//=> ({AnnotationList} '@' -> annotations+=AnnotationNoAtSign)
		public Group getGroup_0() { return cGroup_0; }
		
		//{AnnotationList} '@' -> annotations+=AnnotationNoAtSign
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{AnnotationList}
		public Action getAnnotationListAction_0_0_0() { return cAnnotationListAction_0_0_0; }
		
		//'@'
		public Keyword getCommercialAtKeyword_0_0_1() { return cCommercialAtKeyword_0_0_1; }
		
		//-> annotations+=AnnotationNoAtSign
		public Assignment getAnnotationsAssignment_0_0_2() { return cAnnotationsAssignment_0_0_2; }
		
		//AnnotationNoAtSign
		public RuleCall getAnnotationsAnnotationNoAtSignParserRuleCall_0_0_2_0() { return cAnnotationsAnnotationNoAtSignParserRuleCall_0_0_2_0; }
		
		//annotations+=Annotation*
		public Assignment getAnnotationsAssignment_1() { return cAnnotationsAssignment_1; }
		
		//Annotation
		public RuleCall getAnnotationsAnnotationParserRuleCall_1_0() { return cAnnotationsAnnotationParserRuleCall_1_0; }
	}
	public class ExpressionAnnotationListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ExpressionAnnotationList");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cExpressionAnnotationListAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cAnnotationsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cAnnotationsAnnotationParserRuleCall_1_0 = (RuleCall)cAnnotationsAssignment_1.eContents().get(0);
		
		//ExpressionAnnotationList:
		//	{ExpressionAnnotationList} annotations+=Annotation+;
		@Override public ParserRule getRule() { return rule; }
		
		//{ExpressionAnnotationList} annotations+=Annotation+
		public Group getGroup() { return cGroup; }
		
		//{ExpressionAnnotationList}
		public Action getExpressionAnnotationListAction_0() { return cExpressionAnnotationListAction_0; }
		
		//annotations+=Annotation+
		public Assignment getAnnotationsAssignment_1() { return cAnnotationsAssignment_1; }
		
		//Annotation
		public RuleCall getAnnotationsAnnotationParserRuleCall_1_0() { return cAnnotationsAnnotationParserRuleCall_1_0; }
	}
	public class PropertyAssignmentAnnotationListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PropertyAssignmentAnnotationList");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cPropertyAssignmentAnnotationListAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cAnnotationsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cAnnotationsAnnotationParserRuleCall_1_0 = (RuleCall)cAnnotationsAssignment_1.eContents().get(0);
		
		//PropertyAssignmentAnnotationList:
		//	{PropertyAssignmentAnnotationList} annotations+=Annotation+;
		@Override public ParserRule getRule() { return rule; }
		
		//{PropertyAssignmentAnnotationList} annotations+=Annotation+
		public Group getGroup() { return cGroup; }
		
		//{PropertyAssignmentAnnotationList}
		public Action getPropertyAssignmentAnnotationListAction_0() { return cPropertyAssignmentAnnotationListAction_0; }
		
		//annotations+=Annotation+
		public Assignment getAnnotationsAssignment_1() { return cAnnotationsAssignment_1; }
		
		//Annotation
		public RuleCall getAnnotationsAnnotationParserRuleCall_1_0() { return cAnnotationsAnnotationParserRuleCall_1_0; }
	}
	public class N4MemberAnnotationListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4MemberAnnotationList");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cN4MemberAnnotationListAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cAnnotationsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cAnnotationsAnnotationParserRuleCall_1_0 = (RuleCall)cAnnotationsAssignment_1.eContents().get(0);
		
		//N4MemberAnnotationList:
		//	{N4MemberAnnotationList} annotations+=Annotation+;
		@Override public ParserRule getRule() { return rule; }
		
		//{N4MemberAnnotationList} annotations+=Annotation+
		public Group getGroup() { return cGroup; }
		
		//{N4MemberAnnotationList}
		public Action getN4MemberAnnotationListAction_0() { return cN4MemberAnnotationListAction_0; }
		
		//annotations+=Annotation+
		public Assignment getAnnotationsAssignment_1() { return cAnnotationsAssignment_1; }
		
		//Annotation
		public RuleCall getAnnotationsAnnotationParserRuleCall_1_0() { return cAnnotationsAnnotationParserRuleCall_1_0; }
	}
	public class TypeReferenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TypeReference");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Assignment cAstNamespaceAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final CrossReference cAstNamespaceModuleNamespaceVirtualTypeCrossReference_0_0_0 = (CrossReference)cAstNamespaceAssignment_0_0.eContents().get(0);
		private final RuleCall cAstNamespaceModuleNamespaceVirtualTypeTypeReferenceNameParserRuleCall_0_0_0_1 = (RuleCall)cAstNamespaceModuleNamespaceVirtualTypeCrossReference_0_0_0.eContents().get(1);
		private final Keyword cFullStopKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Assignment cDeclaredTypeAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final CrossReference cDeclaredTypeTypeCrossReference_1_0 = (CrossReference)cDeclaredTypeAssignment_1.eContents().get(0);
		private final RuleCall cDeclaredTypeTypeTypeReferenceNameParserRuleCall_1_0_1 = (RuleCall)cDeclaredTypeTypeCrossReference_1_0.eContents().get(1);
		
		//@Override
		//fragment TypeReference *:
		//	(astNamespace=[types::ModuleNamespaceVirtualType|TypeReferenceName] '.')?
		//	declaredType=[types::Type|TypeReferenceName];
		@Override public ParserRule getRule() { return rule; }
		
		//(astNamespace=[types::ModuleNamespaceVirtualType|TypeReferenceName] '.')? declaredType=[types::Type|TypeReferenceName]
		public Group getGroup() { return cGroup; }
		
		//(astNamespace=[types::ModuleNamespaceVirtualType|TypeReferenceName] '.')?
		public Group getGroup_0() { return cGroup_0; }
		
		//astNamespace=[types::ModuleNamespaceVirtualType|TypeReferenceName]
		public Assignment getAstNamespaceAssignment_0_0() { return cAstNamespaceAssignment_0_0; }
		
		//[types::ModuleNamespaceVirtualType|TypeReferenceName]
		public CrossReference getAstNamespaceModuleNamespaceVirtualTypeCrossReference_0_0_0() { return cAstNamespaceModuleNamespaceVirtualTypeCrossReference_0_0_0; }
		
		//TypeReferenceName
		public RuleCall getAstNamespaceModuleNamespaceVirtualTypeTypeReferenceNameParserRuleCall_0_0_0_1() { return cAstNamespaceModuleNamespaceVirtualTypeTypeReferenceNameParserRuleCall_0_0_0_1; }
		
		//'.'
		public Keyword getFullStopKeyword_0_1() { return cFullStopKeyword_0_1; }
		
		//declaredType=[types::Type|TypeReferenceName]
		public Assignment getDeclaredTypeAssignment_1() { return cDeclaredTypeAssignment_1; }
		
		//[types::Type|TypeReferenceName]
		public CrossReference getDeclaredTypeTypeCrossReference_1_0() { return cDeclaredTypeTypeCrossReference_1_0; }
		
		//TypeReferenceName
		public RuleCall getDeclaredTypeTypeTypeReferenceNameParserRuleCall_1_0_1() { return cDeclaredTypeTypeTypeReferenceNameParserRuleCall_1_0_1; }
	}
	public class TypeReferenceNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TypeReferenceName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cVoidKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cThisKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cAwaitKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cPromisifyKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cTargetKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cDefaultKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final RuleCall cIDENTIFIERTerminalRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		
		//@Override
		//TypeReferenceName:
		//	'void' | 'This' | 'await' | 'Promisify' | 'target' | 'default' | IDENTIFIER;
		@Override public ParserRule getRule() { return rule; }
		
		//'void' | 'This' | 'await' | 'Promisify' | 'target' | 'default' | IDENTIFIER
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'void'
		public Keyword getVoidKeyword_0() { return cVoidKeyword_0; }
		
		//'This'
		public Keyword getThisKeyword_1() { return cThisKeyword_1; }
		
		//'await'
		public Keyword getAwaitKeyword_2() { return cAwaitKeyword_2; }
		
		//'Promisify'
		public Keyword getPromisifyKeyword_3() { return cPromisifyKeyword_3; }
		
		//'target'
		public Keyword getTargetKeyword_4() { return cTargetKeyword_4; }
		
		//'default'
		public Keyword getDefaultKeyword_5() { return cDefaultKeyword_5; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_6() { return cIDENTIFIERTerminalRuleCall_6; }
	}
	public class N4ClassDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4ClassDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_0_0_0 = (Assignment)cGroup_0_0.eContents().get(0);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_0_0_0_0 = (RuleCall)cDeclaredModifiersAssignment_0_0_0.eContents().get(0);
		private final Keyword cClassKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cTypingStrategyAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0 = (RuleCall)cTypingStrategyAssignment_0_0_2.eContents().get(0);
		private final Assignment cNameAssignment_0_0_3 = (Assignment)cGroup_0_0.eContents().get(3);
		private final RuleCall cNameBindingIdentifierParserRuleCall_0_0_3_0 = (RuleCall)cNameAssignment_0_0_3.eContents().get(0);
		private final RuleCall cVersionDeclarationParserRuleCall_0_0_4 = (RuleCall)cGroup_0_0.eContents().get(4);
		private final RuleCall cTypeVariablesParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final RuleCall cClassExtendsImplementsParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final RuleCall cMembersParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		//// ****************************************************************************************************
		//// New Expressions, Statements, and other Features
		//// ****************************************************************************************************
		//// cf. N4JSSpec §2.2.1 -- const statements are handled by means of variable statement modifiers
		//// ****************************************************************************************************
		//// New Meta Types
		//// ****************************************************************************************************
		//// cf. N4JSSpec §14
		//N4ClassDeclaration <Yield>:
		//	=> (declaredModifiers+=N4Modifier*
		//	'class' typingStrategy=TypingStrategyDefSiteOperator?
		//	name=BindingIdentifier<Yield>?
		//	VersionDeclaration?) TypeVariables?
		//	ClassExtendsImplements<Yield>?
		//	Members<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> (declaredModifiers+=N4Modifier* 'class' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>?
		//VersionDeclaration?) TypeVariables? ClassExtendsImplements<Yield>? Members<Yield>
		public Group getGroup() { return cGroup; }
		
		//=> (declaredModifiers+=N4Modifier* 'class' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>?
		//VersionDeclaration?)
		public Group getGroup_0() { return cGroup_0; }
		
		//declaredModifiers+=N4Modifier* 'class' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>?
		//VersionDeclaration?
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_0_0_0() { return cDeclaredModifiersAssignment_0_0_0; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_0_0_0_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_0_0_0_0; }
		
		//'class'
		public Keyword getClassKeyword_0_0_1() { return cClassKeyword_0_0_1; }
		
		//typingStrategy=TypingStrategyDefSiteOperator?
		public Assignment getTypingStrategyAssignment_0_0_2() { return cTypingStrategyAssignment_0_0_2; }
		
		//TypingStrategyDefSiteOperator
		public RuleCall getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0() { return cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0; }
		
		//name=BindingIdentifier<Yield>?
		public Assignment getNameAssignment_0_0_3() { return cNameAssignment_0_0_3; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_0_0_3_0() { return cNameBindingIdentifierParserRuleCall_0_0_3_0; }
		
		//VersionDeclaration?
		public RuleCall getVersionDeclarationParserRuleCall_0_0_4() { return cVersionDeclarationParserRuleCall_0_0_4; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_1() { return cTypeVariablesParserRuleCall_1; }
		
		//ClassExtendsImplements<Yield>?
		public RuleCall getClassExtendsImplementsParserRuleCall_2() { return cClassExtendsImplementsParserRuleCall_2; }
		
		//Members<Yield>
		public RuleCall getMembersParserRuleCall_3() { return cMembersParserRuleCall_3; }
	}
	public class MembersElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Members");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cOwnedMembersRawAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cOwnedMembersRawN4MemberDeclarationParserRuleCall_1_0 = (RuleCall)cOwnedMembersRawAssignment_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//fragment Members <Yield> *:
		//	'{'
		//	ownedMembersRaw+=N4MemberDeclaration<Yield>*
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//'{' ownedMembersRaw+=N4MemberDeclaration<Yield>* '}'
		public Group getGroup() { return cGroup; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_0() { return cLeftCurlyBracketKeyword_0; }
		
		//ownedMembersRaw+=N4MemberDeclaration<Yield>*
		public Assignment getOwnedMembersRawAssignment_1() { return cOwnedMembersRawAssignment_1; }
		
		//N4MemberDeclaration<Yield>
		public RuleCall getOwnedMembersRawN4MemberDeclarationParserRuleCall_1_0() { return cOwnedMembersRawN4MemberDeclarationParserRuleCall_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_2() { return cRightCurlyBracketKeyword_2; }
	}
	public class ClassExtendsImplementsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ClassExtendsImplements");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(0);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final RuleCall cClassExtendsClauseParserRuleCall_0_0 = (RuleCall)cGroup_0.eContents().get(0);
		private final RuleCall cClassImplementsListParserRuleCall_0_1 = (RuleCall)cGroup_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final RuleCall cClassImplementsListParserRuleCall_1_0 = (RuleCall)cGroup_1.eContents().get(0);
		private final RuleCall cClassExtendsClauseParserRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//// we allow incorrect order of 'extends' and 'implements'; a validation will ensure the correct order
		//fragment ClassExtendsImplements <Yield> *:
		//	ClassExtendsClause<Yield> ClassImplementsList? | ClassImplementsList ClassExtendsClause<Yield>?;
		@Override public ParserRule getRule() { return rule; }
		
		//ClassExtendsClause<Yield> ClassImplementsList? | ClassImplementsList ClassExtendsClause<Yield>?
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ClassExtendsClause<Yield> ClassImplementsList?
		public Group getGroup_0() { return cGroup_0; }
		
		//ClassExtendsClause<Yield>
		public RuleCall getClassExtendsClauseParserRuleCall_0_0() { return cClassExtendsClauseParserRuleCall_0_0; }
		
		//ClassImplementsList?
		public RuleCall getClassImplementsListParserRuleCall_0_1() { return cClassImplementsListParserRuleCall_0_1; }
		
		//ClassImplementsList ClassExtendsClause<Yield>?
		public Group getGroup_1() { return cGroup_1; }
		
		//ClassImplementsList
		public RuleCall getClassImplementsListParserRuleCall_1_0() { return cClassImplementsListParserRuleCall_1_0; }
		
		//ClassExtendsClause<Yield>?
		public RuleCall getClassExtendsClauseParserRuleCall_1_1() { return cClassExtendsClauseParserRuleCall_1_1; }
	}
	public class ClassExtendsClauseElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ClassExtendsClause");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cExtendsKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cSuperClassRefAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final RuleCall cSuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0 = (RuleCall)cSuperClassRefAssignment_1_0.eContents().get(0);
		private final Assignment cSuperClassExpressionAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final RuleCall cSuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0 = (RuleCall)cSuperClassExpressionAssignment_1_1.eContents().get(0);
		
		//fragment ClassExtendsClause <Yield> *:
		//	'extends' (=> superClassRef=ParameterizedTypeRefNominal
		//	| superClassExpression=LeftHandSideExpression<Yield>);
		@Override public ParserRule getRule() { return rule; }
		
		//'extends' (=> superClassRef=ParameterizedTypeRefNominal | superClassExpression=LeftHandSideExpression<Yield>)
		public Group getGroup() { return cGroup; }
		
		//'extends'
		public Keyword getExtendsKeyword_0() { return cExtendsKeyword_0; }
		
		//=> superClassRef=ParameterizedTypeRefNominal | superClassExpression=LeftHandSideExpression<Yield>
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//=> superClassRef=ParameterizedTypeRefNominal
		public Assignment getSuperClassRefAssignment_1_0() { return cSuperClassRefAssignment_1_0; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getSuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0() { return cSuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0; }
		
		//superClassExpression=LeftHandSideExpression<Yield>
		public Assignment getSuperClassExpressionAssignment_1_1() { return cSuperClassExpressionAssignment_1_1; }
		
		//LeftHandSideExpression<Yield>
		public RuleCall getSuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0() { return cSuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0; }
	}
	public class ClassImplementsListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ClassImplementsList");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cImplementsKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cImplementedInterfaceRefsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0 = (RuleCall)cImplementedInterfaceRefsAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cCommaKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cImplementedInterfaceRefsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0 = (RuleCall)cImplementedInterfaceRefsAssignment_2_1.eContents().get(0);
		
		//fragment ClassImplementsList *:
		//	'implements' implementedInterfaceRefs+=ParameterizedTypeRefNominal (','
		//	implementedInterfaceRefs+=ParameterizedTypeRefNominal)*;
		@Override public ParserRule getRule() { return rule; }
		
		//'implements' implementedInterfaceRefs+=ParameterizedTypeRefNominal (','
		//implementedInterfaceRefs+=ParameterizedTypeRefNominal)*
		public Group getGroup() { return cGroup; }
		
		//'implements'
		public Keyword getImplementsKeyword_0() { return cImplementsKeyword_0; }
		
		//implementedInterfaceRefs+=ParameterizedTypeRefNominal
		public Assignment getImplementedInterfaceRefsAssignment_1() { return cImplementedInterfaceRefsAssignment_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0() { return cImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0; }
		
		//(',' implementedInterfaceRefs+=ParameterizedTypeRefNominal)*
		public Group getGroup_2() { return cGroup_2; }
		
		//','
		public Keyword getCommaKeyword_2_0() { return cCommaKeyword_2_0; }
		
		//implementedInterfaceRefs+=ParameterizedTypeRefNominal
		public Assignment getImplementedInterfaceRefsAssignment_2_1() { return cImplementedInterfaceRefsAssignment_2_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0() { return cImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0; }
	}
	public class N4ClassExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4ClassExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cN4ClassExpressionAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cClassKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cNameAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cNameBindingIdentifierParserRuleCall_2_0 = (RuleCall)cNameAssignment_2.eContents().get(0);
		private final RuleCall cClassExtendsImplementsParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		private final RuleCall cMembersParserRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		
		//N4ClassExpression <Yield>:
		//	{N4ClassExpression}
		//	'class' name=BindingIdentifier<Yield>?
		//	ClassExtendsImplements<Yield>?
		//	Members<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//{N4ClassExpression} 'class' name=BindingIdentifier<Yield>? ClassExtendsImplements<Yield>? Members<Yield>
		public Group getGroup() { return cGroup; }
		
		//{N4ClassExpression}
		public Action getN4ClassExpressionAction_0() { return cN4ClassExpressionAction_0; }
		
		//'class'
		public Keyword getClassKeyword_1() { return cClassKeyword_1; }
		
		//name=BindingIdentifier<Yield>?
		public Assignment getNameAssignment_2() { return cNameAssignment_2; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_2_0() { return cNameBindingIdentifierParserRuleCall_2_0; }
		
		//ClassExtendsImplements<Yield>?
		public RuleCall getClassExtendsImplementsParserRuleCall_3() { return cClassExtendsImplementsParserRuleCall_3; }
		
		//Members<Yield>
		public RuleCall getMembersParserRuleCall_4() { return cMembersParserRuleCall_4; }
	}
	public class N4InterfaceDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4InterfaceDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_0_0_0 = (Assignment)cGroup_0_0.eContents().get(0);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_0_0_0_0 = (RuleCall)cDeclaredModifiersAssignment_0_0_0.eContents().get(0);
		private final Keyword cInterfaceKeyword_0_0_1 = (Keyword)cGroup_0_0.eContents().get(1);
		private final Assignment cTypingStrategyAssignment_0_0_2 = (Assignment)cGroup_0_0.eContents().get(2);
		private final RuleCall cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0 = (RuleCall)cTypingStrategyAssignment_0_0_2.eContents().get(0);
		private final Assignment cNameAssignment_0_0_3 = (Assignment)cGroup_0_0.eContents().get(3);
		private final RuleCall cNameBindingIdentifierParserRuleCall_0_0_3_0 = (RuleCall)cNameAssignment_0_0_3.eContents().get(0);
		private final RuleCall cVersionDeclarationParserRuleCall_0_0_4 = (RuleCall)cGroup_0_0.eContents().get(4);
		private final RuleCall cTypeVariablesParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final RuleCall cInterfaceExtendsListParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final RuleCall cMembersParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		
		//// cf. N4JSSpec §16
		//N4InterfaceDeclaration <Yield>:
		//	=> (declaredModifiers+=N4Modifier*
		//	'interface' typingStrategy=TypingStrategyDefSiteOperator?
		//	name=BindingIdentifier<Yield>?
		//	VersionDeclaration?) TypeVariables?
		//	InterfaceExtendsList?
		//	Members<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> (declaredModifiers+=N4Modifier* 'interface' typingStrategy=TypingStrategyDefSiteOperator?
		//name=BindingIdentifier<Yield>? VersionDeclaration?) TypeVariables? InterfaceExtendsList? Members<Yield>
		public Group getGroup() { return cGroup; }
		
		//=> (declaredModifiers+=N4Modifier* 'interface' typingStrategy=TypingStrategyDefSiteOperator?
		//name=BindingIdentifier<Yield>? VersionDeclaration?)
		public Group getGroup_0() { return cGroup_0; }
		
		//declaredModifiers+=N4Modifier* 'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>?
		//VersionDeclaration?
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_0_0_0() { return cDeclaredModifiersAssignment_0_0_0; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_0_0_0_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_0_0_0_0; }
		
		//'interface'
		public Keyword getInterfaceKeyword_0_0_1() { return cInterfaceKeyword_0_0_1; }
		
		//typingStrategy=TypingStrategyDefSiteOperator?
		public Assignment getTypingStrategyAssignment_0_0_2() { return cTypingStrategyAssignment_0_0_2; }
		
		//TypingStrategyDefSiteOperator
		public RuleCall getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0() { return cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0; }
		
		//name=BindingIdentifier<Yield>?
		public Assignment getNameAssignment_0_0_3() { return cNameAssignment_0_0_3; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_0_0_3_0() { return cNameBindingIdentifierParserRuleCall_0_0_3_0; }
		
		//VersionDeclaration?
		public RuleCall getVersionDeclarationParserRuleCall_0_0_4() { return cVersionDeclarationParserRuleCall_0_0_4; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_1() { return cTypeVariablesParserRuleCall_1; }
		
		//InterfaceExtendsList?
		public RuleCall getInterfaceExtendsListParserRuleCall_2() { return cInterfaceExtendsListParserRuleCall_2; }
		
		//Members<Yield>
		public RuleCall getMembersParserRuleCall_3() { return cMembersParserRuleCall_3; }
	}
	public class InterfaceExtendsListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.InterfaceExtendsList");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Keyword cExtendsKeyword_0_0 = (Keyword)cAlternatives_0.eContents().get(0);
		private final Keyword cImplementsKeyword_0_1 = (Keyword)cAlternatives_0.eContents().get(1);
		private final Assignment cSuperInterfaceRefsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0 = (RuleCall)cSuperInterfaceRefsAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cCommaKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cSuperInterfaceRefsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0 = (RuleCall)cSuperInterfaceRefsAssignment_2_1.eContents().get(0);
		
		//// we allow both 'extends' and 'implements' here, a validation will ensure 'extends' is used
		//fragment InterfaceExtendsList *:
		//	('extends' | 'implements') superInterfaceRefs+=ParameterizedTypeRefNominal (','
		//	superInterfaceRefs+=ParameterizedTypeRefNominal)*;
		@Override public ParserRule getRule() { return rule; }
		
		//('extends' | 'implements') superInterfaceRefs+=ParameterizedTypeRefNominal (','
		//superInterfaceRefs+=ParameterizedTypeRefNominal)*
		public Group getGroup() { return cGroup; }
		
		//'extends' | 'implements'
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//'extends'
		public Keyword getExtendsKeyword_0_0() { return cExtendsKeyword_0_0; }
		
		//'implements'
		public Keyword getImplementsKeyword_0_1() { return cImplementsKeyword_0_1; }
		
		//superInterfaceRefs+=ParameterizedTypeRefNominal
		public Assignment getSuperInterfaceRefsAssignment_1() { return cSuperInterfaceRefsAssignment_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0() { return cSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0; }
		
		//(',' superInterfaceRefs+=ParameterizedTypeRefNominal)*
		public Group getGroup_2() { return cGroup_2; }
		
		//','
		public Keyword getCommaKeyword_2_0() { return cCommaKeyword_2_0; }
		
		//superInterfaceRefs+=ParameterizedTypeRefNominal
		public Assignment getSuperInterfaceRefsAssignment_2_1() { return cSuperInterfaceRefsAssignment_2_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0() { return cSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0; }
	}
	public class N4EnumDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4EnumDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cN4EnumDeclarationAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_0_0_1 = (Assignment)cGroup_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_0_0_1.eContents().get(0);
		private final Keyword cEnumKeyword_0_0_2 = (Keyword)cGroup_0_0.eContents().get(2);
		private final Assignment cNameAssignment_0_0_3 = (Assignment)cGroup_0_0.eContents().get(3);
		private final RuleCall cNameBindingIdentifierParserRuleCall_0_0_3_0 = (RuleCall)cNameAssignment_0_0_3.eContents().get(0);
		private final RuleCall cVersionDeclarationParserRuleCall_0_0_4 = (RuleCall)cGroup_0_0.eContents().get(4);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Assignment cLiteralsAssignment_2_0 = (Assignment)cGroup_2.eContents().get(0);
		private final RuleCall cLiteralsN4EnumLiteralParserRuleCall_2_0_0 = (RuleCall)cLiteralsAssignment_2_0.eContents().get(0);
		private final Group cGroup_2_1 = (Group)cGroup_2.eContents().get(1);
		private final Keyword cCommaKeyword_2_1_0 = (Keyword)cGroup_2_1.eContents().get(0);
		private final Assignment cLiteralsAssignment_2_1_1 = (Assignment)cGroup_2_1.eContents().get(1);
		private final RuleCall cLiteralsN4EnumLiteralParserRuleCall_2_1_1_0 = (RuleCall)cLiteralsAssignment_2_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//// cf. N4JSSpec §13
		//N4EnumDeclaration <Yield>:
		//	=> ({N4EnumDeclaration} declaredModifiers+=N4Modifier*
		//	'enum' name=BindingIdentifier<Yield>?
		//	VersionDeclaration?)
		//	'{' (literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({N4EnumDeclaration} declaredModifiers+=N4Modifier* 'enum' name=BindingIdentifier<Yield>? VersionDeclaration?) '{'
		//(literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*)? '}'
		public Group getGroup() { return cGroup; }
		
		//=> ({N4EnumDeclaration} declaredModifiers+=N4Modifier* 'enum' name=BindingIdentifier<Yield>? VersionDeclaration?)
		public Group getGroup_0() { return cGroup_0; }
		
		//{N4EnumDeclaration} declaredModifiers+=N4Modifier* 'enum' name=BindingIdentifier<Yield>? VersionDeclaration?
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{N4EnumDeclaration}
		public Action getN4EnumDeclarationAction_0_0_0() { return cN4EnumDeclarationAction_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_0_0_1() { return cDeclaredModifiersAssignment_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0; }
		
		//'enum'
		public Keyword getEnumKeyword_0_0_2() { return cEnumKeyword_0_0_2; }
		
		//name=BindingIdentifier<Yield>?
		public Assignment getNameAssignment_0_0_3() { return cNameAssignment_0_0_3; }
		
		//BindingIdentifier<Yield>
		public RuleCall getNameBindingIdentifierParserRuleCall_0_0_3_0() { return cNameBindingIdentifierParserRuleCall_0_0_3_0; }
		
		//VersionDeclaration?
		public RuleCall getVersionDeclarationParserRuleCall_0_0_4() { return cVersionDeclarationParserRuleCall_0_0_4; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//(literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*)?
		public Group getGroup_2() { return cGroup_2; }
		
		//literals+=N4EnumLiteral
		public Assignment getLiteralsAssignment_2_0() { return cLiteralsAssignment_2_0; }
		
		//N4EnumLiteral
		public RuleCall getLiteralsN4EnumLiteralParserRuleCall_2_0_0() { return cLiteralsN4EnumLiteralParserRuleCall_2_0_0; }
		
		//(',' literals+=N4EnumLiteral)*
		public Group getGroup_2_1() { return cGroup_2_1; }
		
		//','
		public Keyword getCommaKeyword_2_1_0() { return cCommaKeyword_2_1_0; }
		
		//literals+=N4EnumLiteral
		public Assignment getLiteralsAssignment_2_1_1() { return cLiteralsAssignment_2_1_1; }
		
		//N4EnumLiteral
		public RuleCall getLiteralsN4EnumLiteralParserRuleCall_2_1_1_0() { return cLiteralsN4EnumLiteralParserRuleCall_2_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class N4EnumLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4EnumLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cNameIdentifierNameParserRuleCall_0_0 = (RuleCall)cNameAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cColonKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cValueAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cValueSTRINGTerminalRuleCall_1_1_0 = (RuleCall)cValueAssignment_1_1.eContents().get(0);
		
		///*
		// * Only upper case literals are allows, this is to be checked by the validator
		// */ N4EnumLiteral:
		//	name=IdentifierName (':' value=STRING)?;
		@Override public ParserRule getRule() { return rule; }
		
		//name=IdentifierName (':' value=STRING)?
		public Group getGroup() { return cGroup; }
		
		//name=IdentifierName
		public Assignment getNameAssignment_0() { return cNameAssignment_0; }
		
		//IdentifierName
		public RuleCall getNameIdentifierNameParserRuleCall_0_0() { return cNameIdentifierNameParserRuleCall_0_0; }
		
		//(':' value=STRING)?
		public Group getGroup_1() { return cGroup_1; }
		
		//':'
		public Keyword getColonKeyword_1_0() { return cColonKeyword_1_0; }
		
		//value=STRING
		public Assignment getValueAssignment_1_1() { return cValueAssignment_1_1; }
		
		//STRING
		public RuleCall getValueSTRINGTerminalRuleCall_1_1_0() { return cValueSTRINGTerminalRuleCall_1_1_0; }
	}
	public class N4MemberDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4MemberDeclaration");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cAnnotatedN4MemberDeclarationParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cN4GetterDeclarationParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cN4SetterDeclarationParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cN4MethodDeclarationParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cN4FieldDeclarationParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cN4CallableConstructorDeclarationParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		
		//// TODO jvp: order matters, seems odd. What about object literal getters and setter?
		//// TODO sz: what about it?
		//N4MemberDeclaration <Yield>:
		//	AnnotatedN4MemberDeclaration<Yield> | N4GetterDeclaration<Yield> | N4SetterDeclaration<Yield> |
		//	N4MethodDeclaration<Yield> | N4FieldDeclaration<Yield> | N4CallableConstructorDeclaration<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//AnnotatedN4MemberDeclaration<Yield> | N4GetterDeclaration<Yield> | N4SetterDeclaration<Yield> |
		//N4MethodDeclaration<Yield> | N4FieldDeclaration<Yield> | N4CallableConstructorDeclaration<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//AnnotatedN4MemberDeclaration<Yield>
		public RuleCall getAnnotatedN4MemberDeclarationParserRuleCall_0() { return cAnnotatedN4MemberDeclarationParserRuleCall_0; }
		
		//N4GetterDeclaration<Yield>
		public RuleCall getN4GetterDeclarationParserRuleCall_1() { return cN4GetterDeclarationParserRuleCall_1; }
		
		//N4SetterDeclaration<Yield>
		public RuleCall getN4SetterDeclarationParserRuleCall_2() { return cN4SetterDeclarationParserRuleCall_2; }
		
		//N4MethodDeclaration<Yield>
		public RuleCall getN4MethodDeclarationParserRuleCall_3() { return cN4MethodDeclarationParserRuleCall_3; }
		
		//N4FieldDeclaration<Yield>
		public RuleCall getN4FieldDeclarationParserRuleCall_4() { return cN4FieldDeclarationParserRuleCall_4; }
		
		//N4CallableConstructorDeclaration<Yield>
		public RuleCall getN4CallableConstructorDeclarationParserRuleCall_5() { return cN4CallableConstructorDeclarationParserRuleCall_5; }
	}
	public class AnnotatedN4MemberDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AnnotatedN4MemberDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cN4MemberAnnotationListParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cAlternatives_1.eContents().get(0);
		private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
		private final Group cGroup_1_0_0_0 = (Group)cGroup_1_0_0.eContents().get(0);
		private final Action cN4GetterDeclarationAnnotationListAction_1_0_0_0_0 = (Action)cGroup_1_0_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_0_0_0_1 = (Assignment)cGroup_1_0_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_0_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_0_0_0_1.eContents().get(0);
		private final RuleCall cGetterHeaderParserRuleCall_1_0_0_0_2 = (RuleCall)cGroup_1_0_0_0.eContents().get(2);
		private final Assignment cBodyAssignment_1_0_1 = (Assignment)cGroup_1_0.eContents().get(1);
		private final RuleCall cBodyBlockParserRuleCall_1_0_1_0 = (RuleCall)cBodyAssignment_1_0_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_1_0_2 = (Keyword)cGroup_1_0.eContents().get(2);
		private final Group cGroup_1_1 = (Group)cAlternatives_1.eContents().get(1);
		private final Group cGroup_1_1_0 = (Group)cGroup_1_1.eContents().get(0);
		private final Group cGroup_1_1_0_0 = (Group)cGroup_1_1_0.eContents().get(0);
		private final Action cN4SetterDeclarationAnnotationListAction_1_1_0_0_0 = (Action)cGroup_1_1_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_1_0_0_1 = (Assignment)cGroup_1_1_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_1_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_1_0_0_1.eContents().get(0);
		private final Keyword cSetKeyword_1_1_0_0_2 = (Keyword)cGroup_1_1_0_0.eContents().get(2);
		private final Assignment cDeclaredNameAssignment_1_1_0_0_3 = (Assignment)cGroup_1_1_0_0.eContents().get(3);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0 = (RuleCall)cDeclaredNameAssignment_1_1_0_0_3.eContents().get(0);
		private final Assignment cDeclaredOptionalAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final Keyword cDeclaredOptionalQuestionMarkKeyword_1_1_1_0 = (Keyword)cDeclaredOptionalAssignment_1_1_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1_1_2 = (Keyword)cGroup_1_1.eContents().get(2);
		private final Assignment cFparAssignment_1_1_3 = (Assignment)cGroup_1_1.eContents().get(3);
		private final RuleCall cFparFormalParameterParserRuleCall_1_1_3_0 = (RuleCall)cFparAssignment_1_1_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_1_1_4 = (Keyword)cGroup_1_1.eContents().get(4);
		private final Assignment cBodyAssignment_1_1_5 = (Assignment)cGroup_1_1.eContents().get(5);
		private final RuleCall cBodyBlockParserRuleCall_1_1_5_0 = (RuleCall)cBodyAssignment_1_1_5.eContents().get(0);
		private final Keyword cSemicolonKeyword_1_1_6 = (Keyword)cGroup_1_1.eContents().get(6);
		private final Group cGroup_1_2 = (Group)cAlternatives_1.eContents().get(2);
		private final Group cGroup_1_2_0 = (Group)cGroup_1_2.eContents().get(0);
		private final Group cGroup_1_2_0_0 = (Group)cGroup_1_2_0.eContents().get(0);
		private final Action cN4MethodDeclarationAnnotationListAction_1_2_0_0_0 = (Action)cGroup_1_2_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_1_2_0_0_1 = (Assignment)cGroup_1_2_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_1_2_0_0_1.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_1_2_0_0_2 = (RuleCall)cGroup_1_2_0_0.eContents().get(2);
		private final RuleCall cBogusTypeRefFragmentParserRuleCall_1_2_0_0_3 = (RuleCall)cGroup_1_2_0_0.eContents().get(3);
		private final Alternatives cAlternatives_1_2_0_0_4 = (Alternatives)cGroup_1_2_0_0.eContents().get(4);
		private final Group cGroup_1_2_0_0_4_0 = (Group)cAlternatives_1_2_0_0_4.eContents().get(0);
		private final Assignment cGeneratorAssignment_1_2_0_0_4_0_0 = (Assignment)cGroup_1_2_0_0_4_0.eContents().get(0);
		private final Keyword cGeneratorAsteriskKeyword_1_2_0_0_4_0_0_0 = (Keyword)cGeneratorAssignment_1_2_0_0_4_0_0.eContents().get(0);
		private final Assignment cDeclaredNameAssignment_1_2_0_0_4_0_1 = (Assignment)cGroup_1_2_0_0_4_0.eContents().get(1);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0 = (RuleCall)cDeclaredNameAssignment_1_2_0_0_4_0_1.eContents().get(0);
		private final RuleCall cMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2 = (RuleCall)cGroup_1_2_0_0_4_0.eContents().get(2);
		private final Group cGroup_1_2_0_0_4_1 = (Group)cAlternatives_1_2_0_0_4.eContents().get(1);
		private final RuleCall cAsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0 = (RuleCall)cGroup_1_2_0_0_4_1.eContents().get(0);
		private final Assignment cDeclaredNameAssignment_1_2_0_0_4_1_1 = (Assignment)cGroup_1_2_0_0_4_1.eContents().get(1);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0 = (RuleCall)cDeclaredNameAssignment_1_2_0_0_4_1_1.eContents().get(0);
		private final RuleCall cMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2 = (RuleCall)cGroup_1_2_0_0_4_1.eContents().get(2);
		private final Keyword cSemicolonKeyword_1_2_1 = (Keyword)cGroup_1_2.eContents().get(1);
		private final Group cGroup_1_3 = (Group)cAlternatives_1.eContents().get(3);
		private final Action cN4FieldDeclarationAnnotationListAction_1_3_0 = (Action)cGroup_1_3.eContents().get(0);
		private final RuleCall cFieldDeclarationImplParserRuleCall_1_3_1 = (RuleCall)cGroup_1_3.eContents().get(1);
		
		///**
		// * Left factored, annotated member declarations.
		// *
		// * Pretty much inlined versions of getter, setter, method and field declarations with leading annotations.
		// *
		// * The GrammarLinter ensures that the inlined content mirrors the content of the real declarations.
		// */ AnnotatedN4MemberDeclaration <Yield N4MemberDeclaration:
		//	N4MemberAnnotationList (=> ({N4GetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//	GetterHeader<Yield>) body=Block<Yield>? ';'?
		//	| => ({N4SetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'set' ->
		//	declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')'
		//	body=Block<Yield>? ';'?
		//	| => ({N4MethodDeclaration.annotationList=current} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment?
		//	(generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//	AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//	<Generator=false>)) ';'?
		//	| {N4FieldDeclaration.annotationList=current} FieldDeclarationImpl<Yield>);
		@Override public ParserRule getRule() { return rule; }
		
		//N4MemberAnnotationList (=> ({N4GetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//GetterHeader<Yield>) body=Block<Yield>? ';'? | => ({N4SetterDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'? '('
		//fpar=FormalParameter<Yield> ')' body=Block<Yield>? ';'? | => ({N4MethodDeclaration.annotationList=current}
		//declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>)) ';'? | {N4FieldDeclaration.annotationList=current} FieldDeclarationImpl<Yield>)
		public Group getGroup() { return cGroup; }
		
		//N4MemberAnnotationList
		public RuleCall getN4MemberAnnotationListParserRuleCall_0() { return cN4MemberAnnotationListParserRuleCall_0; }
		
		//=> ({N4GetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* GetterHeader<Yield>) body=Block<Yield>?
		//';'? | => ({N4SetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'set' ->
		//declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')'
		//body=Block<Yield>? ';'? | => ({N4MethodDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
		//TypeVariables? BogusTypeRefFragment? (generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> ->
		//MethodParamsReturnAndBody <Generator=true> | AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield>
		//-> MethodParamsReturnAndBody <Generator=false>)) ';'? | {N4FieldDeclaration.annotationList=current}
		//FieldDeclarationImpl<Yield>
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//=> ({N4GetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* GetterHeader<Yield>) body=Block<Yield>?
		//';'?
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//=> ({N4GetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* GetterHeader<Yield>)
		public Group getGroup_1_0_0() { return cGroup_1_0_0; }
		
		//{N4GetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* GetterHeader<Yield>
		public Group getGroup_1_0_0_0() { return cGroup_1_0_0_0; }
		
		//{N4GetterDeclaration.annotationList=current}
		public Action getN4GetterDeclarationAnnotationListAction_1_0_0_0_0() { return cN4GetterDeclarationAnnotationListAction_1_0_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_0_0_0_1() { return cDeclaredModifiersAssignment_1_0_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_0_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_0_0_0_1_0; }
		
		//GetterHeader<Yield>
		public RuleCall getGetterHeaderParserRuleCall_1_0_0_0_2() { return cGetterHeaderParserRuleCall_1_0_0_0_2; }
		
		//body=Block<Yield>?
		public Assignment getBodyAssignment_1_0_1() { return cBodyAssignment_1_0_1; }
		
		//Block<Yield>
		public RuleCall getBodyBlockParserRuleCall_1_0_1_0() { return cBodyBlockParserRuleCall_1_0_1_0; }
		
		//';'?
		public Keyword getSemicolonKeyword_1_0_2() { return cSemicolonKeyword_1_0_2; }
		
		//=> ({N4SetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'set' ->
		//declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')'
		//body=Block<Yield>? ';'?
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//=> ({N4SetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'set' ->
		//declaredName=LiteralOrComputedPropertyName<Yield>)
		public Group getGroup_1_1_0() { return cGroup_1_1_0; }
		
		//{N4SetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'set' ->
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Group getGroup_1_1_0_0() { return cGroup_1_1_0_0; }
		
		//{N4SetterDeclaration.annotationList=current}
		public Action getN4SetterDeclarationAnnotationListAction_1_1_0_0_0() { return cN4SetterDeclarationAnnotationListAction_1_1_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_1_0_0_1() { return cDeclaredModifiersAssignment_1_1_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_1_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_1_0_0_1_0; }
		
		//'set'
		public Keyword getSetKeyword_1_1_0_0_2() { return cSetKeyword_1_1_0_0_2; }
		
		//-> declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_1_1_0_0_3() { return cDeclaredNameAssignment_1_1_0_0_3; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0; }
		
		//declaredOptional?='?'?
		public Assignment getDeclaredOptionalAssignment_1_1_1() { return cDeclaredOptionalAssignment_1_1_1; }
		
		//'?'
		public Keyword getDeclaredOptionalQuestionMarkKeyword_1_1_1_0() { return cDeclaredOptionalQuestionMarkKeyword_1_1_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1_1_2() { return cLeftParenthesisKeyword_1_1_2; }
		
		//fpar=FormalParameter<Yield>
		public Assignment getFparAssignment_1_1_3() { return cFparAssignment_1_1_3; }
		
		//FormalParameter<Yield>
		public RuleCall getFparFormalParameterParserRuleCall_1_1_3_0() { return cFparFormalParameterParserRuleCall_1_1_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_1_1_4() { return cRightParenthesisKeyword_1_1_4; }
		
		//body=Block<Yield>?
		public Assignment getBodyAssignment_1_1_5() { return cBodyAssignment_1_1_5; }
		
		//Block<Yield>
		public RuleCall getBodyBlockParserRuleCall_1_1_5_0() { return cBodyBlockParserRuleCall_1_1_5_0; }
		
		//';'?
		public Keyword getSemicolonKeyword_1_1_6() { return cSemicolonKeyword_1_1_6; }
		
		//=> ({N4MethodDeclaration.annotationList=current} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment?
		//(generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>)) ';'?
		public Group getGroup_1_2() { return cGroup_1_2; }
		
		//=> ({N4MethodDeclaration.annotationList=current} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment?
		//(generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>))
		public Group getGroup_1_2_0() { return cGroup_1_2_0; }
		
		//{N4MethodDeclaration.annotationList=current} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment?
		//(generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>)
		public Group getGroup_1_2_0_0() { return cGroup_1_2_0_0; }
		
		//{N4MethodDeclaration.annotationList=current}
		public Action getN4MethodDeclarationAnnotationListAction_1_2_0_0_0() { return cN4MethodDeclarationAnnotationListAction_1_2_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_1_2_0_0_1() { return cDeclaredModifiersAssignment_1_2_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_0_1_0; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_1_2_0_0_2() { return cTypeVariablesParserRuleCall_1_2_0_0_2; }
		
		//BogusTypeRefFragment?
		public RuleCall getBogusTypeRefFragmentParserRuleCall_1_2_0_0_3() { return cBogusTypeRefFragmentParserRuleCall_1_2_0_0_3; }
		
		//generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>
		public Alternatives getAlternatives_1_2_0_0_4() { return cAlternatives_1_2_0_0_4; }
		
		//generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true>
		public Group getGroup_1_2_0_0_4_0() { return cGroup_1_2_0_0_4_0; }
		
		//generator?='*'
		public Assignment getGeneratorAssignment_1_2_0_0_4_0_0() { return cGeneratorAssignment_1_2_0_0_4_0_0; }
		
		//'*'
		public Keyword getGeneratorAsteriskKeyword_1_2_0_0_4_0_0_0() { return cGeneratorAsteriskKeyword_1_2_0_0_4_0_0_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_1_2_0_0_4_0_1() { return cDeclaredNameAssignment_1_2_0_0_4_0_1; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0; }
		
		//-> MethodParamsReturnAndBody <Generator=true>
		public RuleCall getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2() { return cMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2; }
		
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>
		public Group getGroup_1_2_0_0_4_1() { return cGroup_1_2_0_0_4_1; }
		
		//AsyncNoTrailingLineBreak
		public RuleCall getAsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0() { return cAsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_1_2_0_0_4_1_1() { return cDeclaredNameAssignment_1_2_0_0_4_1_1; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0; }
		
		//-> MethodParamsReturnAndBody <Generator=false>
		public RuleCall getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2() { return cMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2; }
		
		//';'?
		public Keyword getSemicolonKeyword_1_2_1() { return cSemicolonKeyword_1_2_1; }
		
		//{N4FieldDeclaration.annotationList=current} FieldDeclarationImpl<Yield>
		public Group getGroup_1_3() { return cGroup_1_3; }
		
		//{N4FieldDeclaration.annotationList=current}
		public Action getN4FieldDeclarationAnnotationListAction_1_3_0() { return cN4FieldDeclarationAnnotationListAction_1_3_0; }
		
		//FieldDeclarationImpl<Yield>
		public RuleCall getFieldDeclarationImplParserRuleCall_1_3_1() { return cFieldDeclarationImplParserRuleCall_1_3_1; }
	}
	public class FieldDeclarationImplElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.FieldDeclarationImpl");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_0_0 = (RuleCall)cDeclaredModifiersAssignment_0.eContents().get(0);
		private final RuleCall cBogusTypeRefFragmentParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final Assignment cDeclaredNameAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0 = (RuleCall)cDeclaredNameAssignment_2.eContents().get(0);
		private final Assignment cDeclaredOptionalAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final Keyword cDeclaredOptionalQuestionMarkKeyword_3_0 = (Keyword)cDeclaredOptionalAssignment_3.eContents().get(0);
		private final RuleCall cColonSepDeclaredTypeRefParserRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		private final Group cGroup_5 = (Group)cGroup.eContents().get(5);
		private final Keyword cEqualsSignKeyword_5_0 = (Keyword)cGroup_5.eContents().get(0);
		private final Assignment cExpressionAssignment_5_1 = (Assignment)cGroup_5.eContents().get(1);
		private final RuleCall cExpressionExpressionParserRuleCall_5_1_0 = (RuleCall)cExpressionAssignment_5_1.eContents().get(0);
		private final RuleCall cSemiParserRuleCall_6 = (RuleCall)cGroup.eContents().get(6);
		
		//fragment FieldDeclarationImpl <Yield> *:
		//	declaredModifiers+=N4Modifier* BogusTypeRefFragment?
		//	declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'?
		//	ColonSepDeclaredTypeRef? ('=' expression=Expression<In=true,Yield>)?
		//	Semi;
		@Override public ParserRule getRule() { return rule; }
		
		//declaredModifiers+=N4Modifier* BogusTypeRefFragment? declaredName=LiteralOrComputedPropertyName<Yield>
		//declaredOptional?='?'? ColonSepDeclaredTypeRef? ('=' expression=Expression<In=true,Yield>)? Semi
		public Group getGroup() { return cGroup; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_0() { return cDeclaredModifiersAssignment_0; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_0_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_0_0; }
		
		//BogusTypeRefFragment?
		public RuleCall getBogusTypeRefFragmentParserRuleCall_1() { return cBogusTypeRefFragmentParserRuleCall_1; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_2() { return cDeclaredNameAssignment_2; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0; }
		
		//declaredOptional?='?'?
		public Assignment getDeclaredOptionalAssignment_3() { return cDeclaredOptionalAssignment_3; }
		
		//'?'
		public Keyword getDeclaredOptionalQuestionMarkKeyword_3_0() { return cDeclaredOptionalQuestionMarkKeyword_3_0; }
		
		//ColonSepDeclaredTypeRef?
		public RuleCall getColonSepDeclaredTypeRefParserRuleCall_4() { return cColonSepDeclaredTypeRefParserRuleCall_4; }
		
		//('=' expression=Expression<In=true,Yield>)?
		public Group getGroup_5() { return cGroup_5; }
		
		//'='
		public Keyword getEqualsSignKeyword_5_0() { return cEqualsSignKeyword_5_0; }
		
		//expression=Expression<In=true,Yield>
		public Assignment getExpressionAssignment_5_1() { return cExpressionAssignment_5_1; }
		
		//Expression<In=true,Yield>
		public RuleCall getExpressionExpressionParserRuleCall_5_1_0() { return cExpressionExpressionParserRuleCall_5_1_0; }
		
		//Semi
		public RuleCall getSemiParserRuleCall_6() { return cSemiParserRuleCall_6; }
	}
	public class N4FieldDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4FieldDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cN4FieldDeclarationAction_0 = (Action)cGroup.eContents().get(0);
		private final RuleCall cFieldDeclarationImplParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//N4FieldDeclaration <Yield>:
		//	{N4FieldDeclaration} FieldDeclarationImpl<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//{N4FieldDeclaration} FieldDeclarationImpl<Yield>
		public Group getGroup() { return cGroup; }
		
		//{N4FieldDeclaration}
		public Action getN4FieldDeclarationAction_0() { return cN4FieldDeclarationAction_0; }
		
		//FieldDeclarationImpl<Yield>
		public RuleCall getFieldDeclarationImplParserRuleCall_1() { return cFieldDeclarationImplParserRuleCall_1; }
	}
	public class N4MethodDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4MethodDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cN4MethodDeclarationAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_0_0_1 = (Assignment)cGroup_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_0_0_1.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_0_0_2 = (RuleCall)cGroup_0_0.eContents().get(2);
		private final RuleCall cBogusTypeRefFragmentParserRuleCall_0_0_3 = (RuleCall)cGroup_0_0.eContents().get(3);
		private final Alternatives cAlternatives_0_0_4 = (Alternatives)cGroup_0_0.eContents().get(4);
		private final Group cGroup_0_0_4_0 = (Group)cAlternatives_0_0_4.eContents().get(0);
		private final Assignment cGeneratorAssignment_0_0_4_0_0 = (Assignment)cGroup_0_0_4_0.eContents().get(0);
		private final Keyword cGeneratorAsteriskKeyword_0_0_4_0_0_0 = (Keyword)cGeneratorAssignment_0_0_4_0_0.eContents().get(0);
		private final Assignment cDeclaredNameAssignment_0_0_4_0_1 = (Assignment)cGroup_0_0_4_0.eContents().get(1);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0 = (RuleCall)cDeclaredNameAssignment_0_0_4_0_1.eContents().get(0);
		private final RuleCall cMethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2 = (RuleCall)cGroup_0_0_4_0.eContents().get(2);
		private final Group cGroup_0_0_4_1 = (Group)cAlternatives_0_0_4.eContents().get(1);
		private final RuleCall cAsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0 = (RuleCall)cGroup_0_0_4_1.eContents().get(0);
		private final Assignment cDeclaredNameAssignment_0_0_4_1_1 = (Assignment)cGroup_0_0_4_1.eContents().get(1);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0 = (RuleCall)cDeclaredNameAssignment_0_0_4_1_1.eContents().get(0);
		private final RuleCall cMethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2 = (RuleCall)cGroup_0_0_4_1.eContents().get(2);
		private final Keyword cSemicolonKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//N4MethodDeclaration <Yield>:
		//	=> ({N4MethodDeclaration} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment? (generator?='*'
		//	declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//	AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//	<Generator=false>)) ';'?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({N4MethodDeclaration} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>)) ';'?
		public Group getGroup() { return cGroup; }
		
		//=> ({N4MethodDeclaration} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>))
		public Group getGroup_0() { return cGroup_0; }
		
		//{N4MethodDeclaration} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment? (generator?='*'
		//declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>)
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{N4MethodDeclaration}
		public Action getN4MethodDeclarationAction_0_0_0() { return cN4MethodDeclarationAction_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_0_0_1() { return cDeclaredModifiersAssignment_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_0_0_2() { return cTypeVariablesParserRuleCall_0_0_2; }
		
		//BogusTypeRefFragment?
		public RuleCall getBogusTypeRefFragmentParserRuleCall_0_0_3() { return cBogusTypeRefFragmentParserRuleCall_0_0_3; }
		
		//generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>
		public Alternatives getAlternatives_0_0_4() { return cAlternatives_0_0_4; }
		
		//generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true>
		public Group getGroup_0_0_4_0() { return cGroup_0_0_4_0; }
		
		//generator?='*'
		public Assignment getGeneratorAssignment_0_0_4_0_0() { return cGeneratorAssignment_0_0_4_0_0; }
		
		//'*'
		public Keyword getGeneratorAsteriskKeyword_0_0_4_0_0_0() { return cGeneratorAsteriskKeyword_0_0_4_0_0_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_0_0_4_0_1() { return cDeclaredNameAssignment_0_0_4_0_1; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0; }
		
		//-> MethodParamsReturnAndBody <Generator=true>
		public RuleCall getMethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2() { return cMethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2; }
		
		//AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
		//<Generator=false>
		public Group getGroup_0_0_4_1() { return cGroup_0_0_4_1; }
		
		//AsyncNoTrailingLineBreak
		public RuleCall getAsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0() { return cAsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_0_0_4_1_1() { return cDeclaredNameAssignment_0_0_4_1_1; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0; }
		
		//-> MethodParamsReturnAndBody <Generator=false>
		public RuleCall getMethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2() { return cMethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2; }
		
		//';'?
		public Keyword getSemicolonKeyword_1() { return cSemicolonKeyword_1; }
	}
	public class N4CallableConstructorDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4CallableConstructorDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cMethodParamsReturnAndBodyParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Keyword cSemicolonKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//N4CallableConstructorDeclaration <Yield N4MethodDeclaration:
		//	MethodParamsReturnAndBody<Generator=false> ';'?;
		@Override public ParserRule getRule() { return rule; }
		
		//MethodParamsReturnAndBody<Generator=false> ';'?
		public Group getGroup() { return cGroup; }
		
		//MethodParamsReturnAndBody<Generator=false>
		public RuleCall getMethodParamsReturnAndBodyParserRuleCall_0() { return cMethodParamsReturnAndBodyParserRuleCall_0; }
		
		//';'?
		public Keyword getSemicolonKeyword_1() { return cSemicolonKeyword_1; }
	}
	public class MethodParamsAndBodyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.MethodParamsAndBody");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final RuleCall cStrictFormalParametersParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Assignment cBodyAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cBodyBlockParserRuleCall_1_0 = (RuleCall)cBodyAssignment_1.eContents().get(0);
		
		//fragment MethodParamsAndBody <Generator> *:
		//	StrictFormalParameters<Yield=Generator> body=Block<Yield=Generator>?;
		@Override public ParserRule getRule() { return rule; }
		
		//StrictFormalParameters<Yield=Generator> body=Block<Yield=Generator>?
		public Group getGroup() { return cGroup; }
		
		//StrictFormalParameters<Yield=Generator>
		public RuleCall getStrictFormalParametersParserRuleCall_0() { return cStrictFormalParametersParserRuleCall_0; }
		
		//body=Block<Yield=Generator>?
		public Assignment getBodyAssignment_1() { return cBodyAssignment_1; }
		
		//Block<Yield=Generator>
		public RuleCall getBodyBlockParserRuleCall_1_0() { return cBodyBlockParserRuleCall_1_0; }
	}
	public class MethodParamsReturnAndBodyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.MethodParamsReturnAndBody");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final RuleCall cStrictFormalParametersParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final Assignment cBodyAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cBodyBlockParserRuleCall_2_0 = (RuleCall)cBodyAssignment_2.eContents().get(0);
		
		//fragment MethodParamsReturnAndBody <Generator> *:
		//	StrictFormalParameters<Yield=Generator> ColonSepReturnTypeRef?
		//	body=Block<Yield=Generator>?;
		@Override public ParserRule getRule() { return rule; }
		
		//StrictFormalParameters<Yield=Generator> ColonSepReturnTypeRef? body=Block<Yield=Generator>?
		public Group getGroup() { return cGroup; }
		
		//StrictFormalParameters<Yield=Generator>
		public RuleCall getStrictFormalParametersParserRuleCall_0() { return cStrictFormalParametersParserRuleCall_0; }
		
		//ColonSepReturnTypeRef?
		public RuleCall getColonSepReturnTypeRefParserRuleCall_1() { return cColonSepReturnTypeRefParserRuleCall_1; }
		
		//body=Block<Yield=Generator>?
		public Assignment getBodyAssignment_2() { return cBodyAssignment_2; }
		
		//Block<Yield=Generator>
		public RuleCall getBodyBlockParserRuleCall_2_0() { return cBodyBlockParserRuleCall_2_0; }
	}
	public class N4GetterDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4GetterDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cN4GetterDeclarationAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_0_0_1 = (Assignment)cGroup_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_0_0_1.eContents().get(0);
		private final RuleCall cGetterHeaderParserRuleCall_0_0_2 = (RuleCall)cGroup_0_0.eContents().get(2);
		private final Assignment cBodyAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cBodyBlockParserRuleCall_1_0 = (RuleCall)cBodyAssignment_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		///*
		// * 'get' and 'set' are no reserved words, see BindingIdentifier.
		// */ N4GetterDeclaration <Yield>:
		//	=> ({N4GetterDeclaration} declaredModifiers+=N4Modifier*
		//	GetterHeader<Yield>) body=Block<Yield>? ';'?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({N4GetterDeclaration} declaredModifiers+=N4Modifier* GetterHeader<Yield>) body=Block<Yield>? ';'?
		public Group getGroup() { return cGroup; }
		
		//=> ({N4GetterDeclaration} declaredModifiers+=N4Modifier* GetterHeader<Yield>)
		public Group getGroup_0() { return cGroup_0; }
		
		//{N4GetterDeclaration} declaredModifiers+=N4Modifier* GetterHeader<Yield>
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{N4GetterDeclaration}
		public Action getN4GetterDeclarationAction_0_0_0() { return cN4GetterDeclarationAction_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_0_0_1() { return cDeclaredModifiersAssignment_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0; }
		
		//GetterHeader<Yield>
		public RuleCall getGetterHeaderParserRuleCall_0_0_2() { return cGetterHeaderParserRuleCall_0_0_2; }
		
		//body=Block<Yield>?
		public Assignment getBodyAssignment_1() { return cBodyAssignment_1; }
		
		//Block<Yield>
		public RuleCall getBodyBlockParserRuleCall_1_0() { return cBodyBlockParserRuleCall_1_0; }
		
		//';'?
		public Keyword getSemicolonKeyword_2() { return cSemicolonKeyword_2; }
	}
	public class GetterHeaderElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.GetterHeader");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final RuleCall cBogusTypeRefFragmentParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Keyword cGetKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cDeclaredNameAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0 = (RuleCall)cDeclaredNameAssignment_2.eContents().get(0);
		private final Assignment cDeclaredOptionalAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final Keyword cDeclaredOptionalQuestionMarkKeyword_3_0 = (Keyword)cDeclaredOptionalAssignment_3.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Keyword cRightParenthesisKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final RuleCall cColonSepDeclaredTypeRefParserRuleCall_6 = (RuleCall)cGroup.eContents().get(6);
		
		//fragment GetterHeader <Yield> *:
		//	BogusTypeRefFragment? 'get' -> declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'? '(' ')'
		//	ColonSepDeclaredTypeRef?;
		@Override public ParserRule getRule() { return rule; }
		
		//BogusTypeRefFragment? 'get' -> declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'? '(' ')'
		//ColonSepDeclaredTypeRef?
		public Group getGroup() { return cGroup; }
		
		//BogusTypeRefFragment?
		public RuleCall getBogusTypeRefFragmentParserRuleCall_0() { return cBogusTypeRefFragmentParserRuleCall_0; }
		
		//'get'
		public Keyword getGetKeyword_1() { return cGetKeyword_1; }
		
		//-> declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_2() { return cDeclaredNameAssignment_2; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0; }
		
		//declaredOptional?='?'?
		public Assignment getDeclaredOptionalAssignment_3() { return cDeclaredOptionalAssignment_3; }
		
		//'?'
		public Keyword getDeclaredOptionalQuestionMarkKeyword_3_0() { return cDeclaredOptionalQuestionMarkKeyword_3_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_4() { return cLeftParenthesisKeyword_4; }
		
		//')'
		public Keyword getRightParenthesisKeyword_5() { return cRightParenthesisKeyword_5; }
		
		//ColonSepDeclaredTypeRef?
		public RuleCall getColonSepDeclaredTypeRefParserRuleCall_6() { return cColonSepDeclaredTypeRefParserRuleCall_6; }
	}
	public class N4SetterDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4SetterDeclaration");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Action cN4SetterDeclarationAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Assignment cDeclaredModifiersAssignment_0_0_1 = (Assignment)cGroup_0_0.eContents().get(1);
		private final RuleCall cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0 = (RuleCall)cDeclaredModifiersAssignment_0_0_1.eContents().get(0);
		private final Keyword cSetKeyword_0_0_2 = (Keyword)cGroup_0_0.eContents().get(2);
		private final Assignment cDeclaredNameAssignment_0_0_3 = (Assignment)cGroup_0_0.eContents().get(3);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0 = (RuleCall)cDeclaredNameAssignment_0_0_3.eContents().get(0);
		private final Assignment cDeclaredOptionalAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cDeclaredOptionalQuestionMarkKeyword_1_0 = (Keyword)cDeclaredOptionalAssignment_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cFparAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cFparFormalParameterParserRuleCall_3_0 = (RuleCall)cFparAssignment_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cBodyAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cBodyBlockParserRuleCall_5_0 = (RuleCall)cBodyAssignment_5.eContents().get(0);
		private final Keyword cSemicolonKeyword_6 = (Keyword)cGroup.eContents().get(6);
		
		//N4SetterDeclaration <Yield>:
		//	=> ({N4SetterDeclaration} declaredModifiers+=N4Modifier*
		//	'set'
		//	-> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'?
		//	'(' fpar=FormalParameter<Yield> ')' body=Block<Yield>? ';'?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ({N4SetterDeclaration} declaredModifiers+=N4Modifier* 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>)
		//declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')' body=Block<Yield>? ';'?
		public Group getGroup() { return cGroup; }
		
		//=> ({N4SetterDeclaration} declaredModifiers+=N4Modifier* 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>)
		public Group getGroup_0() { return cGroup_0; }
		
		//{N4SetterDeclaration} declaredModifiers+=N4Modifier* 'set' -> declaredName=LiteralOrComputedPropertyName<Yield>
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{N4SetterDeclaration}
		public Action getN4SetterDeclarationAction_0_0_0() { return cN4SetterDeclarationAction_0_0_0; }
		
		//declaredModifiers+=N4Modifier*
		public Assignment getDeclaredModifiersAssignment_0_0_1() { return cDeclaredModifiersAssignment_0_0_1; }
		
		//N4Modifier
		public RuleCall getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0() { return cDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0; }
		
		//'set'
		public Keyword getSetKeyword_0_0_2() { return cSetKeyword_0_0_2; }
		
		//-> declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_0_0_3() { return cDeclaredNameAssignment_0_0_3; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0; }
		
		//declaredOptional?='?'?
		public Assignment getDeclaredOptionalAssignment_1() { return cDeclaredOptionalAssignment_1; }
		
		//'?'
		public Keyword getDeclaredOptionalQuestionMarkKeyword_1_0() { return cDeclaredOptionalQuestionMarkKeyword_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }
		
		//fpar=FormalParameter<Yield>
		public Assignment getFparAssignment_3() { return cFparAssignment_3; }
		
		//FormalParameter<Yield>
		public RuleCall getFparFormalParameterParserRuleCall_3_0() { return cFparFormalParameterParserRuleCall_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_4() { return cRightParenthesisKeyword_4; }
		
		//body=Block<Yield>?
		public Assignment getBodyAssignment_5() { return cBodyAssignment_5; }
		
		//Block<Yield>
		public RuleCall getBodyBlockParserRuleCall_5_0() { return cBodyBlockParserRuleCall_5_0; }
		
		//';'?
		public Keyword getSemicolonKeyword_6() { return cSemicolonKeyword_6; }
	}
	public class BindingPatternElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BindingPattern");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cObjectBindingPatternParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cArrayBindingPatternParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//BindingPattern <Yield>:
		//	ObjectBindingPattern<Yield> | ArrayBindingPattern<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//ObjectBindingPattern<Yield> | ArrayBindingPattern<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ObjectBindingPattern<Yield>
		public RuleCall getObjectBindingPatternParserRuleCall_0() { return cObjectBindingPatternParserRuleCall_0; }
		
		//ArrayBindingPattern<Yield>
		public RuleCall getArrayBindingPatternParserRuleCall_1() { return cArrayBindingPatternParserRuleCall_1; }
	}
	public class ObjectBindingPatternElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ObjectBindingPattern");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cObjectBindingPatternAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Assignment cPropertiesAssignment_2_0 = (Assignment)cGroup_2.eContents().get(0);
		private final RuleCall cPropertiesBindingPropertyParserRuleCall_2_0_0 = (RuleCall)cPropertiesAssignment_2_0.eContents().get(0);
		private final Group cGroup_2_1 = (Group)cGroup_2.eContents().get(1);
		private final Keyword cCommaKeyword_2_1_0 = (Keyword)cGroup_2_1.eContents().get(0);
		private final Assignment cPropertiesAssignment_2_1_1 = (Assignment)cGroup_2_1.eContents().get(1);
		private final RuleCall cPropertiesBindingPropertyParserRuleCall_2_1_1_0 = (RuleCall)cPropertiesAssignment_2_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//ObjectBindingPattern <Yield>:
		//	{ObjectBindingPattern}
		//	'{' (properties+=BindingProperty<Yield,AllowType=false> (',' properties+=BindingProperty<Yield,AllowType=false>)*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{ObjectBindingPattern} '{' (properties+=BindingProperty<Yield,AllowType=false> (','
		//properties+=BindingProperty<Yield,AllowType=false>)*)? '}'
		public Group getGroup() { return cGroup; }
		
		//{ObjectBindingPattern}
		public Action getObjectBindingPatternAction_0() { return cObjectBindingPatternAction_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//(properties+=BindingProperty<Yield,AllowType=false> (',' properties+=BindingProperty<Yield,AllowType=false>)*)?
		public Group getGroup_2() { return cGroup_2; }
		
		//properties+=BindingProperty<Yield,AllowType=false>
		public Assignment getPropertiesAssignment_2_0() { return cPropertiesAssignment_2_0; }
		
		//BindingProperty<Yield,AllowType=false>
		public RuleCall getPropertiesBindingPropertyParserRuleCall_2_0_0() { return cPropertiesBindingPropertyParserRuleCall_2_0_0; }
		
		//(',' properties+=BindingProperty<Yield,AllowType=false>)*
		public Group getGroup_2_1() { return cGroup_2_1; }
		
		//','
		public Keyword getCommaKeyword_2_1_0() { return cCommaKeyword_2_1_0; }
		
		//properties+=BindingProperty<Yield,AllowType=false>
		public Assignment getPropertiesAssignment_2_1_1() { return cPropertiesAssignment_2_1_1; }
		
		//BindingProperty<Yield,AllowType=false>
		public RuleCall getPropertiesBindingPropertyParserRuleCall_2_1_1_0() { return cPropertiesBindingPropertyParserRuleCall_2_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class ArrayBindingPatternElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ArrayBindingPattern");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cArrayBindingPatternAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cElementsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cElementsElisionParserRuleCall_2_0 = (RuleCall)cElementsAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Assignment cElementsAssignment_3_0 = (Assignment)cGroup_3.eContents().get(0);
		private final RuleCall cElementsBindingRestElementParserRuleCall_3_0_0 = (RuleCall)cElementsAssignment_3_0.eContents().get(0);
		private final Group cGroup_3_1 = (Group)cGroup_3.eContents().get(1);
		private final Keyword cCommaKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Assignment cElementsAssignment_3_1_1 = (Assignment)cGroup_3_1.eContents().get(1);
		private final RuleCall cElementsElisionParserRuleCall_3_1_1_0 = (RuleCall)cElementsAssignment_3_1_1.eContents().get(0);
		private final Assignment cElementsAssignment_3_1_2 = (Assignment)cGroup_3_1.eContents().get(2);
		private final RuleCall cElementsBindingRestElementParserRuleCall_3_1_2_0 = (RuleCall)cElementsAssignment_3_1_2.eContents().get(0);
		private final Group cGroup_3_2 = (Group)cGroup_3.eContents().get(2);
		private final Keyword cCommaKeyword_3_2_0 = (Keyword)cGroup_3_2.eContents().get(0);
		private final Assignment cElementsAssignment_3_2_1 = (Assignment)cGroup_3_2.eContents().get(1);
		private final RuleCall cElementsElisionParserRuleCall_3_2_1_0 = (RuleCall)cElementsAssignment_3_2_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//ArrayBindingPattern <Yield>:
		//	{ArrayBindingPattern}
		//	'['
		//	elements+=Elision* (elements+=BindingRestElement<Yield> (',' elements+=Elision* elements+=BindingRestElement<Yield>)*
		//	(',' elements+=Elision*)?)?
		//	']';
		@Override public ParserRule getRule() { return rule; }
		
		//{ArrayBindingPattern} '[' elements+=Elision* (elements+=BindingRestElement<Yield> (',' elements+=Elision*
		//elements+=BindingRestElement<Yield>)* (',' elements+=Elision*)?)? ']'
		public Group getGroup() { return cGroup; }
		
		//{ArrayBindingPattern}
		public Action getArrayBindingPatternAction_0() { return cArrayBindingPatternAction_0; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_1() { return cLeftSquareBracketKeyword_1; }
		
		//elements+=Elision*
		public Assignment getElementsAssignment_2() { return cElementsAssignment_2; }
		
		//Elision
		public RuleCall getElementsElisionParserRuleCall_2_0() { return cElementsElisionParserRuleCall_2_0; }
		
		//(elements+=BindingRestElement<Yield> (',' elements+=Elision* elements+=BindingRestElement<Yield>)* (','
		//elements+=Elision*)?)?
		public Group getGroup_3() { return cGroup_3; }
		
		//elements+=BindingRestElement<Yield>
		public Assignment getElementsAssignment_3_0() { return cElementsAssignment_3_0; }
		
		//BindingRestElement<Yield>
		public RuleCall getElementsBindingRestElementParserRuleCall_3_0_0() { return cElementsBindingRestElementParserRuleCall_3_0_0; }
		
		//(',' elements+=Elision* elements+=BindingRestElement<Yield>)*
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//','
		public Keyword getCommaKeyword_3_1_0() { return cCommaKeyword_3_1_0; }
		
		//elements+=Elision*
		public Assignment getElementsAssignment_3_1_1() { return cElementsAssignment_3_1_1; }
		
		//Elision
		public RuleCall getElementsElisionParserRuleCall_3_1_1_0() { return cElementsElisionParserRuleCall_3_1_1_0; }
		
		//elements+=BindingRestElement<Yield>
		public Assignment getElementsAssignment_3_1_2() { return cElementsAssignment_3_1_2; }
		
		//BindingRestElement<Yield>
		public RuleCall getElementsBindingRestElementParserRuleCall_3_1_2_0() { return cElementsBindingRestElementParserRuleCall_3_1_2_0; }
		
		//(',' elements+=Elision*)?
		public Group getGroup_3_2() { return cGroup_3_2; }
		
		//','
		public Keyword getCommaKeyword_3_2_0() { return cCommaKeyword_3_2_0; }
		
		//elements+=Elision*
		public Assignment getElementsAssignment_3_2_1() { return cElementsAssignment_3_2_1; }
		
		//Elision
		public RuleCall getElementsElisionParserRuleCall_3_2_1_0() { return cElementsElisionParserRuleCall_3_2_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_4() { return cRightSquareBracketKeyword_4; }
	}
	public class BindingPropertyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BindingProperty");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Group cGroup_0_0_0 = (Group)cGroup_0_0.eContents().get(0);
		private final Assignment cDeclaredNameAssignment_0_0_0_0 = (Assignment)cGroup_0_0_0.eContents().get(0);
		private final RuleCall cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0 = (RuleCall)cDeclaredNameAssignment_0_0_0_0.eContents().get(0);
		private final Keyword cColonKeyword_0_0_0_1 = (Keyword)cGroup_0_0_0.eContents().get(1);
		private final Assignment cValueAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
		private final RuleCall cValueBindingElementParserRuleCall_0_1_0 = (RuleCall)cValueAssignment_0_1.eContents().get(0);
		private final Assignment cValueAssignment_1 = (Assignment)cAlternatives.eContents().get(1);
		private final RuleCall cValueSingleNameBindingParserRuleCall_1_0 = (RuleCall)cValueAssignment_1.eContents().get(0);
		
		///*
		// * In case of object destruction, no colon separated type can be declared in case of single name binding since this would
		// * be ambiguous (e.g., {prop: newVar} vs.  {propAndVarName: TypeForVar}.
		// * However it is possible with a preceding LiteralBindingPropertyName, as in this case we simply have three
		// * segment, e.g. { prop: newVar: TypeOfNewVar }.
		// */ BindingProperty <Yield, AllowType>:
		//	=> (declaredName=LiteralOrComputedPropertyName<Yield> ':') value=BindingElement<Yield> |
		//	value=SingleNameBinding<Yield,AllowType>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> (declaredName=LiteralOrComputedPropertyName<Yield> ':') value=BindingElement<Yield> |
		//value=SingleNameBinding<Yield,AllowType>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//=> (declaredName=LiteralOrComputedPropertyName<Yield> ':') value=BindingElement<Yield>
		public Group getGroup_0() { return cGroup_0; }
		
		//=> (declaredName=LiteralOrComputedPropertyName<Yield> ':')
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield> ':'
		public Group getGroup_0_0_0() { return cGroup_0_0_0; }
		
		//declaredName=LiteralOrComputedPropertyName<Yield>
		public Assignment getDeclaredNameAssignment_0_0_0_0() { return cDeclaredNameAssignment_0_0_0_0; }
		
		//LiteralOrComputedPropertyName<Yield>
		public RuleCall getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0() { return cDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0; }
		
		//':'
		public Keyword getColonKeyword_0_0_0_1() { return cColonKeyword_0_0_0_1; }
		
		//value=BindingElement<Yield>
		public Assignment getValueAssignment_0_1() { return cValueAssignment_0_1; }
		
		//BindingElement<Yield>
		public RuleCall getValueBindingElementParserRuleCall_0_1_0() { return cValueBindingElementParserRuleCall_0_1_0; }
		
		//value=SingleNameBinding<Yield,AllowType>
		public Assignment getValueAssignment_1() { return cValueAssignment_1; }
		
		//SingleNameBinding<Yield,AllowType>
		public RuleCall getValueSingleNameBindingParserRuleCall_1_0() { return cValueSingleNameBindingParserRuleCall_1_0; }
	}
	public class SingleNameBindingElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.SingleNameBinding");
		private final Assignment cVarDeclAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cVarDeclVariableDeclarationParserRuleCall_0 = (RuleCall)cVarDeclAssignment.eContents().get(0);
		
		//SingleNameBinding <Yield, AllowType BindingElement:
		//	varDecl=VariableDeclaration<In=true,Yield,AllowType>;
		@Override public ParserRule getRule() { return rule; }
		
		//varDecl=VariableDeclaration<In=true,Yield,AllowType>
		public Assignment getVarDeclAssignment() { return cVarDeclAssignment; }
		
		//VariableDeclaration<In=true,Yield,AllowType>
		public RuleCall getVarDeclVariableDeclarationParserRuleCall_0() { return cVarDeclVariableDeclarationParserRuleCall_0; }
	}
	public class BindingElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BindingElement");
		private final RuleCall cBindingElementImplParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//BindingElement <Yield>:
		//	BindingElementImpl<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//BindingElementImpl<Yield>
		public RuleCall getBindingElementImplParserRuleCall() { return cBindingElementImplParserRuleCall; }
	}
	public class BindingRestElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BindingRestElement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cRestAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cRestFullStopFullStopFullStopKeyword_0_0 = (Keyword)cRestAssignment_0.eContents().get(0);
		private final RuleCall cBindingElementImplParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//BindingRestElement <Yield BindingElement:
		//	rest?='...'?
		//	BindingElementImpl<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//rest?='...'? BindingElementImpl<Yield>
		public Group getGroup() { return cGroup; }
		
		//rest?='...'?
		public Assignment getRestAssignment_0() { return cRestAssignment_0; }
		
		//'...'
		public Keyword getRestFullStopFullStopFullStopKeyword_0_0() { return cRestFullStopFullStopFullStopKeyword_0_0; }
		
		//BindingElementImpl<Yield>
		public RuleCall getBindingElementImplParserRuleCall_1() { return cBindingElementImplParserRuleCall_1; }
	}
	public class BindingElementImplElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BindingElementImpl");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Assignment cNestedPatternAssignment_0_0_0 = (Assignment)cGroup_0_0.eContents().get(0);
		private final RuleCall cNestedPatternBindingPatternParserRuleCall_0_0_0_0 = (RuleCall)cNestedPatternAssignment_0_0_0.eContents().get(0);
		private final Group cGroup_0_1 = (Group)cGroup_0.eContents().get(1);
		private final Keyword cEqualsSignKeyword_0_1_0 = (Keyword)cGroup_0_1.eContents().get(0);
		private final Assignment cExpressionAssignment_0_1_1 = (Assignment)cGroup_0_1.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_0_1_1_0 = (RuleCall)cExpressionAssignment_0_1_1.eContents().get(0);
		private final Assignment cVarDeclAssignment_1 = (Assignment)cAlternatives.eContents().get(1);
		private final RuleCall cVarDeclVariableDeclarationParserRuleCall_1_0 = (RuleCall)cVarDeclAssignment_1.eContents().get(0);
		
		//fragment BindingElementImpl <Yield> returns BindingElement:
		//	=> (nestedPattern=BindingPattern<Yield>) ('=' expression=AssignmentExpression<In=true,Yield>)?
		//	| varDecl=VariableDeclaration<In=true,Yield,AllowType=true>;
		@Override public ParserRule getRule() { return rule; }
		
		//=> (nestedPattern=BindingPattern<Yield>) ('=' expression=AssignmentExpression<In=true,Yield>)? |
		//varDecl=VariableDeclaration<In=true,Yield,AllowType=true>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//=> (nestedPattern=BindingPattern<Yield>) ('=' expression=AssignmentExpression<In=true,Yield>)?
		public Group getGroup_0() { return cGroup_0; }
		
		//=> (nestedPattern=BindingPattern<Yield>)
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//nestedPattern=BindingPattern<Yield>
		public Assignment getNestedPatternAssignment_0_0_0() { return cNestedPatternAssignment_0_0_0; }
		
		//BindingPattern<Yield>
		public RuleCall getNestedPatternBindingPatternParserRuleCall_0_0_0_0() { return cNestedPatternBindingPatternParserRuleCall_0_0_0_0; }
		
		//('=' expression=AssignmentExpression<In=true,Yield>)?
		public Group getGroup_0_1() { return cGroup_0_1; }
		
		//'='
		public Keyword getEqualsSignKeyword_0_1_0() { return cEqualsSignKeyword_0_1_0; }
		
		//expression=AssignmentExpression<In=true,Yield>
		public Assignment getExpressionAssignment_0_1_1() { return cExpressionAssignment_0_1_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_0_1_1_0() { return cExpressionAssignmentExpressionParserRuleCall_0_1_1_0; }
		
		//varDecl=VariableDeclaration<In=true,Yield,AllowType=true>
		public Assignment getVarDeclAssignment_1() { return cVarDeclAssignment_1; }
		
		//VariableDeclaration<In=true,Yield,AllowType=true>
		public RuleCall getVarDeclVariableDeclarationParserRuleCall_1_0() { return cVarDeclVariableDeclarationParserRuleCall_1_0; }
	}
	public class ElisionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.Elision");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cBindingElementAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cCommaKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//Elision BindingElement:
		//	{BindingElement} ',';
		@Override public ParserRule getRule() { return rule; }
		
		//{BindingElement} ','
		public Group getGroup() { return cGroup; }
		
		//{BindingElement}
		public Action getBindingElementAction_0() { return cBindingElementAction_0; }
		
		//','
		public Keyword getCommaKeyword_1() { return cCommaKeyword_1; }
	}
	public class LiteralOrComputedPropertyNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cLiteralNameAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final RuleCall cLiteralNameIdentifierNameParserRuleCall_0_0 = (RuleCall)cLiteralNameAssignment_0.eContents().get(0);
		private final Assignment cLiteralNameAssignment_1 = (Assignment)cAlternatives.eContents().get(1);
		private final RuleCall cLiteralNameSTRINGTerminalRuleCall_1_0 = (RuleCall)cLiteralNameAssignment_1.eContents().get(0);
		private final Assignment cLiteralNameAssignment_2 = (Assignment)cAlternatives.eContents().get(2);
		private final RuleCall cLiteralNameNumericLiteralAsStringParserRuleCall_2_0 = (RuleCall)cLiteralNameAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cAlternatives.eContents().get(3);
		private final Keyword cLeftSquareBracketKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cExpressionAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_3_1_0 = (RuleCall)cExpressionAssignment_3_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_3_2 = (Keyword)cGroup_3.eContents().get(2);
		
		//LiteralOrComputedPropertyName <Yield>:
		//	literalName=IdentifierName
		//	| literalName=STRING
		//	| literalName=NumericLiteralAsString
		//	| '[' expression=AssignmentExpression<In=true,Yield> ']';
		@Override public ParserRule getRule() { return rule; }
		
		//literalName=IdentifierName | literalName=STRING | literalName=NumericLiteralAsString | '['
		//expression=AssignmentExpression<In=true,Yield> ']'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//literalName=IdentifierName
		public Assignment getLiteralNameAssignment_0() { return cLiteralNameAssignment_0; }
		
		//IdentifierName
		public RuleCall getLiteralNameIdentifierNameParserRuleCall_0_0() { return cLiteralNameIdentifierNameParserRuleCall_0_0; }
		
		//literalName=STRING
		public Assignment getLiteralNameAssignment_1() { return cLiteralNameAssignment_1; }
		
		//STRING
		public RuleCall getLiteralNameSTRINGTerminalRuleCall_1_0() { return cLiteralNameSTRINGTerminalRuleCall_1_0; }
		
		//literalName=NumericLiteralAsString
		public Assignment getLiteralNameAssignment_2() { return cLiteralNameAssignment_2; }
		
		//NumericLiteralAsString
		public RuleCall getLiteralNameNumericLiteralAsStringParserRuleCall_2_0() { return cLiteralNameNumericLiteralAsStringParserRuleCall_2_0; }
		
		//'[' expression=AssignmentExpression<In=true,Yield> ']'
		public Group getGroup_3() { return cGroup_3; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_3_0() { return cLeftSquareBracketKeyword_3_0; }
		
		//expression=AssignmentExpression<In=true,Yield>
		public Assignment getExpressionAssignment_3_1() { return cExpressionAssignment_3_1; }
		
		//AssignmentExpression<In=true,Yield>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_3_1_0() { return cExpressionAssignmentExpressionParserRuleCall_3_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_3_2() { return cRightSquareBracketKeyword_3_2; }
	}
	public class JSXElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXElement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLessThanSignKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cJsxElementNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cJsxElementNameJSXElementNameParserRuleCall_1_0 = (RuleCall)cJsxElementNameAssignment_1.eContents().get(0);
		private final RuleCall cJSXAttributesParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final Alternatives cAlternatives_3 = (Alternatives)cGroup.eContents().get(3);
		private final Group cGroup_3_0 = (Group)cAlternatives_3.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_3_0_0 = (Keyword)cGroup_3_0.eContents().get(0);
		private final Assignment cJsxChildrenAssignment_3_0_1 = (Assignment)cGroup_3_0.eContents().get(1);
		private final RuleCall cJsxChildrenJSXChildParserRuleCall_3_0_1_0 = (RuleCall)cJsxChildrenAssignment_3_0_1.eContents().get(0);
		private final Keyword cLessThanSignKeyword_3_0_2 = (Keyword)cGroup_3_0.eContents().get(2);
		private final Keyword cSolidusKeyword_3_0_3 = (Keyword)cGroup_3_0.eContents().get(3);
		private final Assignment cJsxClosingNameAssignment_3_0_4 = (Assignment)cGroup_3_0.eContents().get(4);
		private final RuleCall cJsxClosingNameJSXElementNameParserRuleCall_3_0_4_0 = (RuleCall)cJsxClosingNameAssignment_3_0_4.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_3_0_5 = (Keyword)cGroup_3_0.eContents().get(5);
		private final Group cGroup_3_1 = (Group)cAlternatives_3.eContents().get(1);
		private final Keyword cSolidusKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_3_1_1 = (Keyword)cGroup_3_1.eContents().get(1);
		
		//// ****************************************************************************************************
		//// see https://facebook.github.io/jsx/
		//// ****************************************************************************************************
		//JSXElement:
		//	'<' jsxElementName=JSXElementName JSXAttributes ('>' jsxChildren+=JSXChild* '<' '/' jsxClosingName=JSXElementName '>'
		//	| '/' '>');
		@Override public ParserRule getRule() { return rule; }
		
		//'<' jsxElementName=JSXElementName JSXAttributes ('>' jsxChildren+=JSXChild* '<' '/' jsxClosingName=JSXElementName '>' |
		//'/' '>')
		public Group getGroup() { return cGroup; }
		
		//'<'
		public Keyword getLessThanSignKeyword_0() { return cLessThanSignKeyword_0; }
		
		//jsxElementName=JSXElementName
		public Assignment getJsxElementNameAssignment_1() { return cJsxElementNameAssignment_1; }
		
		//JSXElementName
		public RuleCall getJsxElementNameJSXElementNameParserRuleCall_1_0() { return cJsxElementNameJSXElementNameParserRuleCall_1_0; }
		
		//JSXAttributes
		public RuleCall getJSXAttributesParserRuleCall_2() { return cJSXAttributesParserRuleCall_2; }
		
		//'>' jsxChildren+=JSXChild* '<' '/' jsxClosingName=JSXElementName '>' | '/' '>'
		public Alternatives getAlternatives_3() { return cAlternatives_3; }
		
		//'>' jsxChildren+=JSXChild* '<' '/' jsxClosingName=JSXElementName '>'
		public Group getGroup_3_0() { return cGroup_3_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3_0_0() { return cGreaterThanSignKeyword_3_0_0; }
		
		//jsxChildren+=JSXChild*
		public Assignment getJsxChildrenAssignment_3_0_1() { return cJsxChildrenAssignment_3_0_1; }
		
		//JSXChild
		public RuleCall getJsxChildrenJSXChildParserRuleCall_3_0_1_0() { return cJsxChildrenJSXChildParserRuleCall_3_0_1_0; }
		
		//'<'
		public Keyword getLessThanSignKeyword_3_0_2() { return cLessThanSignKeyword_3_0_2; }
		
		//'/'
		public Keyword getSolidusKeyword_3_0_3() { return cSolidusKeyword_3_0_3; }
		
		//jsxClosingName=JSXElementName
		public Assignment getJsxClosingNameAssignment_3_0_4() { return cJsxClosingNameAssignment_3_0_4; }
		
		//JSXElementName
		public RuleCall getJsxClosingNameJSXElementNameParserRuleCall_3_0_4_0() { return cJsxClosingNameJSXElementNameParserRuleCall_3_0_4_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3_0_5() { return cGreaterThanSignKeyword_3_0_5; }
		
		//'/' '>'
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//'/'
		public Keyword getSolidusKeyword_3_1_0() { return cSolidusKeyword_3_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3_1_1() { return cGreaterThanSignKeyword_3_1_1; }
	}
	public class JSXFragmentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXFragment");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cJSXFragmentAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cLessThanSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cGreaterThanSignKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cJsxChildrenAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cJsxChildrenJSXChildParserRuleCall_3_0 = (RuleCall)cJsxChildrenAssignment_3.eContents().get(0);
		private final Keyword cLessThanSignKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Keyword cSolidusKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Keyword cGreaterThanSignKeyword_6 = (Keyword)cGroup.eContents().get(6);
		
		//JSXFragment:
		//	{JSXFragment} '<' '>' jsxChildren+=JSXChild* '<' '/' '>';
		@Override public ParserRule getRule() { return rule; }
		
		//{JSXFragment} '<' '>' jsxChildren+=JSXChild* '<' '/' '>'
		public Group getGroup() { return cGroup; }
		
		//{JSXFragment}
		public Action getJSXFragmentAction_0() { return cJSXFragmentAction_0; }
		
		//'<'
		public Keyword getLessThanSignKeyword_1() { return cLessThanSignKeyword_1; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_2() { return cGreaterThanSignKeyword_2; }
		
		//jsxChildren+=JSXChild*
		public Assignment getJsxChildrenAssignment_3() { return cJsxChildrenAssignment_3; }
		
		//JSXChild
		public RuleCall getJsxChildrenJSXChildParserRuleCall_3_0() { return cJsxChildrenJSXChildParserRuleCall_3_0; }
		
		//'<'
		public Keyword getLessThanSignKeyword_4() { return cLessThanSignKeyword_4; }
		
		//'/'
		public Keyword getSolidusKeyword_5() { return cSolidusKeyword_5; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_6() { return cGreaterThanSignKeyword_6; }
	}
	public class JSXChildElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXChild");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cJSXElementParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cJSXFragmentParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cJSXExpressionParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//JSXChild:
		//	JSXElement
		//	| JSXFragment
		//	| JSXExpression
		//	//	| JSXText -- not supported yet, cf. IDE-2414
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//JSXElement | JSXFragment | JSXExpression
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//JSXElement
		public RuleCall getJSXElementParserRuleCall_0() { return cJSXElementParserRuleCall_0; }
		
		//JSXFragment
		public RuleCall getJSXFragmentParserRuleCall_1() { return cJSXFragmentParserRuleCall_1; }
		
		//JSXExpression
		public RuleCall getJSXExpressionParserRuleCall_2() { return cJSXExpressionParserRuleCall_2; }
	}
	public class JSXExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cExpressionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_1_0 = (RuleCall)cExpressionAssignment_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//// terminal JSX_TEXT: !('{'|'<'|'>'|'}'); needs to be refactored similar to template text, cf. IDE-2414
		//JSXExpression:
		//	'{' expression=AssignmentExpression<false,false> '}';
		@Override public ParserRule getRule() { return rule; }
		
		//'{' expression=AssignmentExpression<false,false> '}'
		public Group getGroup() { return cGroup; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_0() { return cLeftCurlyBracketKeyword_0; }
		
		//expression=AssignmentExpression<false,false>
		public Assignment getExpressionAssignment_1() { return cExpressionAssignment_1; }
		
		//AssignmentExpression<false,false>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_1_0() { return cExpressionAssignmentExpressionParserRuleCall_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_2() { return cRightCurlyBracketKeyword_2; }
	}
	public class JSXElementNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXElementName");
		private final Assignment cExpressionAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cExpressionJSXElementNameExpressionParserRuleCall_0 = (RuleCall)cExpressionAssignment.eContents().get(0);
		
		//JSXElementName:
		//	expression=JSXElementNameExpression;
		@Override public ParserRule getRule() { return rule; }
		
		//expression=JSXElementNameExpression
		public Assignment getExpressionAssignment() { return cExpressionAssignment; }
		
		//JSXElementNameExpression
		public RuleCall getExpressionJSXElementNameExpressionParserRuleCall_0() { return cExpressionJSXElementNameExpressionParserRuleCall_0; }
	}
	public class JSXElementNameExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXElementNameExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cIdentifierRefParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cParameterizedPropertyAccessExpressionTargetAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final RuleCall cParameterizedPropertyAccessExpressionTailParserRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//JSXElementNameExpression Expression:
		//	IdentifierRef<false> ({ParameterizedPropertyAccessExpression.target=current}
		//	ParameterizedPropertyAccessExpressionTail<false>)*
		//	//	| JSXNamedspacedName not supported
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//IdentifierRef<false> ({ParameterizedPropertyAccessExpression.target=current}
		//ParameterizedPropertyAccessExpressionTail<false>)*
		public Group getGroup() { return cGroup; }
		
		//IdentifierRef<false>
		public RuleCall getIdentifierRefParserRuleCall_0() { return cIdentifierRefParserRuleCall_0; }
		
		//({ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<false>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//{ParameterizedPropertyAccessExpression.target=current}
		public Action getParameterizedPropertyAccessExpressionTargetAction_1_0() { return cParameterizedPropertyAccessExpressionTargetAction_1_0; }
		
		//ParameterizedPropertyAccessExpressionTail<false>
		public RuleCall getParameterizedPropertyAccessExpressionTailParserRuleCall_1_1() { return cParameterizedPropertyAccessExpressionTailParserRuleCall_1_1; }
	}
	public class JSXAttributesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXAttributes");
		private final Assignment cJsxAttributesAssignment = (Assignment)rule.eContents().get(0);
		private final RuleCall cJsxAttributesJSXAttributeParserRuleCall_0 = (RuleCall)cJsxAttributesAssignment.eContents().get(0);
		
		////JSXNamedspacedName: JSXIdentifier ':' JSXIdentifier -- not supported in N4JSX
		////JSXMemberExpression: JSXIdentifier '.' JSXIdentifier -- defined by means of ParameterizedPropertyAccessExpression
		//fragment JSXAttributes *:
		//	jsxAttributes+=JSXAttribute*;
		@Override public ParserRule getRule() { return rule; }
		
		//jsxAttributes+=JSXAttribute*
		public Assignment getJsxAttributesAssignment() { return cJsxAttributesAssignment; }
		
		//JSXAttribute
		public RuleCall getJsxAttributesJSXAttributeParserRuleCall_0() { return cJsxAttributesJSXAttributeParserRuleCall_0; }
	}
	public class JSXAttributeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXAttribute");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cJSXSpreadAttributeParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cJSXPropertyAttributeParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//JSXAttribute:
		//	JSXSpreadAttribute
		//	| JSXPropertyAttribute;
		@Override public ParserRule getRule() { return rule; }
		
		//JSXSpreadAttribute | JSXPropertyAttribute
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//JSXSpreadAttribute
		public RuleCall getJSXSpreadAttributeParserRuleCall_0() { return cJSXSpreadAttributeParserRuleCall_0; }
		
		//JSXPropertyAttribute
		public RuleCall getJSXPropertyAttributeParserRuleCall_1() { return cJSXPropertyAttributeParserRuleCall_1; }
	}
	public class JSXSpreadAttributeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXSpreadAttribute");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cFullStopFullStopFullStopKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cExpressionAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cExpressionAssignmentExpressionParserRuleCall_2_0 = (RuleCall)cExpressionAssignment_2.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//JSXSpreadAttribute:
		//	'{' '...' expression=AssignmentExpression<In=false,Yield=false> '}';
		@Override public ParserRule getRule() { return rule; }
		
		//'{' '...' expression=AssignmentExpression<In=false,Yield=false> '}'
		public Group getGroup() { return cGroup; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_0() { return cLeftCurlyBracketKeyword_0; }
		
		//'...'
		public Keyword getFullStopFullStopFullStopKeyword_1() { return cFullStopFullStopFullStopKeyword_1; }
		
		//expression=AssignmentExpression<In=false,Yield=false>
		public Assignment getExpressionAssignment_2() { return cExpressionAssignment_2; }
		
		//AssignmentExpression<In=false,Yield=false>
		public RuleCall getExpressionAssignmentExpressionParserRuleCall_2_0() { return cExpressionAssignmentExpressionParserRuleCall_2_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class JSXPropertyAttributeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.JSXPropertyAttribute");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cPropertyAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final CrossReference cPropertyIdentifiableElementCrossReference_0_0 = (CrossReference)cPropertyAssignment_0.eContents().get(0);
		private final RuleCall cPropertyIdentifiableElementIdentifierNameParserRuleCall_0_0_1 = (RuleCall)cPropertyIdentifiableElementCrossReference_0_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cEqualsSignKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Alternatives cAlternatives_1_1 = (Alternatives)cGroup_1.eContents().get(1);
		private final Assignment cJsxAttributeValueAssignment_1_1_0 = (Assignment)cAlternatives_1_1.eContents().get(0);
		private final RuleCall cJsxAttributeValueStringLiteralParserRuleCall_1_1_0_0 = (RuleCall)cJsxAttributeValueAssignment_1_1_0.eContents().get(0);
		private final Group cGroup_1_1_1 = (Group)cAlternatives_1_1.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_1_1_1_0 = (Keyword)cGroup_1_1_1.eContents().get(0);
		private final Assignment cJsxAttributeValueAssignment_1_1_1_1 = (Assignment)cGroup_1_1_1.eContents().get(1);
		private final RuleCall cJsxAttributeValueAssignmentExpressionParserRuleCall_1_1_1_1_0 = (RuleCall)cJsxAttributeValueAssignment_1_1_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_1_1_1_2 = (Keyword)cGroup_1_1_1.eContents().get(2);
		
		//JSXPropertyAttribute:
		//	property=[types::IdentifiableElement|IdentifierName] ('=' (jsxAttributeValue=StringLiteral | '{'
		//	jsxAttributeValue=AssignmentExpression<In=false,Yield=false> '}'))?;
		@Override public ParserRule getRule() { return rule; }
		
		//property=[types::IdentifiableElement|IdentifierName] ('=' (jsxAttributeValue=StringLiteral | '{'
		//jsxAttributeValue=AssignmentExpression<In=false,Yield=false> '}'))?
		public Group getGroup() { return cGroup; }
		
		//property=[types::IdentifiableElement|IdentifierName]
		public Assignment getPropertyAssignment_0() { return cPropertyAssignment_0; }
		
		//[types::IdentifiableElement|IdentifierName]
		public CrossReference getPropertyIdentifiableElementCrossReference_0_0() { return cPropertyIdentifiableElementCrossReference_0_0; }
		
		//IdentifierName
		public RuleCall getPropertyIdentifiableElementIdentifierNameParserRuleCall_0_0_1() { return cPropertyIdentifiableElementIdentifierNameParserRuleCall_0_0_1; }
		
		//('=' (jsxAttributeValue=StringLiteral | '{' jsxAttributeValue=AssignmentExpression<In=false,Yield=false> '}'))?
		public Group getGroup_1() { return cGroup_1; }
		
		//'='
		public Keyword getEqualsSignKeyword_1_0() { return cEqualsSignKeyword_1_0; }
		
		//jsxAttributeValue=StringLiteral | '{' jsxAttributeValue=AssignmentExpression<In=false,Yield=false> '}'
		public Alternatives getAlternatives_1_1() { return cAlternatives_1_1; }
		
		//jsxAttributeValue=StringLiteral
		public Assignment getJsxAttributeValueAssignment_1_1_0() { return cJsxAttributeValueAssignment_1_1_0; }
		
		//StringLiteral
		public RuleCall getJsxAttributeValueStringLiteralParserRuleCall_1_1_0_0() { return cJsxAttributeValueStringLiteralParserRuleCall_1_1_0_0; }
		
		//'{' jsxAttributeValue=AssignmentExpression<In=false,Yield=false> '}'
		public Group getGroup_1_1_1() { return cGroup_1_1_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1_1_1_0() { return cLeftCurlyBracketKeyword_1_1_1_0; }
		
		//jsxAttributeValue=AssignmentExpression<In=false,Yield=false>
		public Assignment getJsxAttributeValueAssignment_1_1_1_1() { return cJsxAttributeValueAssignment_1_1_1_1; }
		
		//AssignmentExpression<In=false,Yield=false>
		public RuleCall getJsxAttributeValueAssignmentExpressionParserRuleCall_1_1_1_1_0() { return cJsxAttributeValueAssignmentExpressionParserRuleCall_1_1_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_1_1_1_2() { return cRightCurlyBracketKeyword_1_1_1_2; }
	}
	public class VersionDeclarationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.VersionDeclaration");
		private final Assignment cDeclaredVersionAssignment = (Assignment)rule.eContents().get(0);
		private final RuleCall cDeclaredVersionVERSIONTerminalRuleCall_0 = (RuleCall)cDeclaredVersionAssignment.eContents().get(0);
		
		///* Version (N4IDL) related rules */ fragment VersionDeclaration *:
		//	declaredVersion=VERSION;
		@Override public ParserRule getRule() { return rule; }
		
		//declaredVersion=VERSION
		public Assignment getDeclaredVersionAssignment() { return cDeclaredVersionAssignment; }
		
		//VERSION
		public RuleCall getDeclaredVersionVERSIONTerminalRuleCall_0() { return cDeclaredVersionVERSIONTerminalRuleCall_0; }
	}
	
	public class VariableStatementKeywordElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.VariableStatementKeyword");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cVarEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cVarVarKeyword_0_0 = (Keyword)cVarEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cConstEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cConstConstKeyword_1_0 = (Keyword)cConstEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cLetEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cLetLetKeyword_2_0 = (Keyword)cLetEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum VariableStatementKeyword:
		//	var | const | let;
		public EnumRule getRule() { return rule; }
		
		//var | const | let
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//var
		public EnumLiteralDeclaration getVarEnumLiteralDeclaration_0() { return cVarEnumLiteralDeclaration_0; }
		
		//'var'
		public Keyword getVarVarKeyword_0_0() { return cVarVarKeyword_0_0; }
		
		//const
		public EnumLiteralDeclaration getConstEnumLiteralDeclaration_1() { return cConstEnumLiteralDeclaration_1; }
		
		//'const'
		public Keyword getConstConstKeyword_1_0() { return cConstConstKeyword_1_0; }
		
		//let
		public EnumLiteralDeclaration getLetEnumLiteralDeclaration_2() { return cLetEnumLiteralDeclaration_2; }
		
		//'let'
		public Keyword getLetLetKeyword_2_0() { return cLetLetKeyword_2_0; }
	}
	public class PostfixOperatorElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.PostfixOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cIncEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cIncPlusSignPlusSignKeyword_0_0 = (Keyword)cIncEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cDecEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cDecHyphenMinusHyphenMinusKeyword_1_0 = (Keyword)cDecEnumLiteralDeclaration_1.eContents().get(0);
		
		//enum PostfixOperator:
		//	inc='++' | dec='--';
		public EnumRule getRule() { return rule; }
		
		//inc='++' | dec='--'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//inc='++'
		public EnumLiteralDeclaration getIncEnumLiteralDeclaration_0() { return cIncEnumLiteralDeclaration_0; }
		
		//'++'
		public Keyword getIncPlusSignPlusSignKeyword_0_0() { return cIncPlusSignPlusSignKeyword_0_0; }
		
		//dec='--'
		public EnumLiteralDeclaration getDecEnumLiteralDeclaration_1() { return cDecEnumLiteralDeclaration_1; }
		
		//'--'
		public Keyword getDecHyphenMinusHyphenMinusKeyword_1_0() { return cDecHyphenMinusHyphenMinusKeyword_1_0; }
	}
	public class UnaryOperatorElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.UnaryOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cDeleteEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cDeleteDeleteKeyword_0_0 = (Keyword)cDeleteEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cVoidEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cVoidVoidKeyword_1_0 = (Keyword)cVoidEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cTypeofEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cTypeofTypeofKeyword_2_0 = (Keyword)cTypeofEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cIncEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cIncPlusSignPlusSignKeyword_3_0 = (Keyword)cIncEnumLiteralDeclaration_3.eContents().get(0);
		private final EnumLiteralDeclaration cDecEnumLiteralDeclaration_4 = (EnumLiteralDeclaration)cAlternatives.eContents().get(4);
		private final Keyword cDecHyphenMinusHyphenMinusKeyword_4_0 = (Keyword)cDecEnumLiteralDeclaration_4.eContents().get(0);
		private final EnumLiteralDeclaration cPosEnumLiteralDeclaration_5 = (EnumLiteralDeclaration)cAlternatives.eContents().get(5);
		private final Keyword cPosPlusSignKeyword_5_0 = (Keyword)cPosEnumLiteralDeclaration_5.eContents().get(0);
		private final EnumLiteralDeclaration cNegEnumLiteralDeclaration_6 = (EnumLiteralDeclaration)cAlternatives.eContents().get(6);
		private final Keyword cNegHyphenMinusKeyword_6_0 = (Keyword)cNegEnumLiteralDeclaration_6.eContents().get(0);
		private final EnumLiteralDeclaration cInvEnumLiteralDeclaration_7 = (EnumLiteralDeclaration)cAlternatives.eContents().get(7);
		private final Keyword cInvTildeKeyword_7_0 = (Keyword)cInvEnumLiteralDeclaration_7.eContents().get(0);
		private final EnumLiteralDeclaration cNotEnumLiteralDeclaration_8 = (EnumLiteralDeclaration)cAlternatives.eContents().get(8);
		private final Keyword cNotExclamationMarkKeyword_8_0 = (Keyword)cNotEnumLiteralDeclaration_8.eContents().get(0);
		
		//enum UnaryOperator:
		//	delete | void | typeof | inc='++' | dec='--' | pos='+' | neg='-' | inv='~' | not='!';
		public EnumRule getRule() { return rule; }
		
		//delete | void | typeof | inc='++' | dec='--' | pos='+' | neg='-' | inv='~' | not='!'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//delete
		public EnumLiteralDeclaration getDeleteEnumLiteralDeclaration_0() { return cDeleteEnumLiteralDeclaration_0; }
		
		//"delete"
		public Keyword getDeleteDeleteKeyword_0_0() { return cDeleteDeleteKeyword_0_0; }
		
		//void
		public EnumLiteralDeclaration getVoidEnumLiteralDeclaration_1() { return cVoidEnumLiteralDeclaration_1; }
		
		//"void"
		public Keyword getVoidVoidKeyword_1_0() { return cVoidVoidKeyword_1_0; }
		
		//typeof
		public EnumLiteralDeclaration getTypeofEnumLiteralDeclaration_2() { return cTypeofEnumLiteralDeclaration_2; }
		
		//"typeof"
		public Keyword getTypeofTypeofKeyword_2_0() { return cTypeofTypeofKeyword_2_0; }
		
		//inc='++'
		public EnumLiteralDeclaration getIncEnumLiteralDeclaration_3() { return cIncEnumLiteralDeclaration_3; }
		
		//'++'
		public Keyword getIncPlusSignPlusSignKeyword_3_0() { return cIncPlusSignPlusSignKeyword_3_0; }
		
		//dec='--'
		public EnumLiteralDeclaration getDecEnumLiteralDeclaration_4() { return cDecEnumLiteralDeclaration_4; }
		
		//'--'
		public Keyword getDecHyphenMinusHyphenMinusKeyword_4_0() { return cDecHyphenMinusHyphenMinusKeyword_4_0; }
		
		//pos='+'
		public EnumLiteralDeclaration getPosEnumLiteralDeclaration_5() { return cPosEnumLiteralDeclaration_5; }
		
		//'+'
		public Keyword getPosPlusSignKeyword_5_0() { return cPosPlusSignKeyword_5_0; }
		
		//neg='-'
		public EnumLiteralDeclaration getNegEnumLiteralDeclaration_6() { return cNegEnumLiteralDeclaration_6; }
		
		//'-'
		public Keyword getNegHyphenMinusKeyword_6_0() { return cNegHyphenMinusKeyword_6_0; }
		
		//inv='~'
		public EnumLiteralDeclaration getInvEnumLiteralDeclaration_7() { return cInvEnumLiteralDeclaration_7; }
		
		//'~'
		public Keyword getInvTildeKeyword_7_0() { return cInvTildeKeyword_7_0; }
		
		//not='!'
		public EnumLiteralDeclaration getNotEnumLiteralDeclaration_8() { return cNotEnumLiteralDeclaration_8; }
		
		//'!'
		public Keyword getNotExclamationMarkKeyword_8_0() { return cNotExclamationMarkKeyword_8_0; }
	}
	public class MultiplicativeOperatorElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.MultiplicativeOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cTimesEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cTimesAsteriskKeyword_0_0 = (Keyword)cTimesEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cDivEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cDivSolidusKeyword_1_0 = (Keyword)cDivEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cModEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cModPercentSignKeyword_2_0 = (Keyword)cModEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum MultiplicativeOperator:
		//	times='*' | div='/' | mod='%';
		public EnumRule getRule() { return rule; }
		
		//times='*' | div='/' | mod='%'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//times='*'
		public EnumLiteralDeclaration getTimesEnumLiteralDeclaration_0() { return cTimesEnumLiteralDeclaration_0; }
		
		//'*'
		public Keyword getTimesAsteriskKeyword_0_0() { return cTimesAsteriskKeyword_0_0; }
		
		//div='/'
		public EnumLiteralDeclaration getDivEnumLiteralDeclaration_1() { return cDivEnumLiteralDeclaration_1; }
		
		//'/'
		public Keyword getDivSolidusKeyword_1_0() { return cDivSolidusKeyword_1_0; }
		
		//mod='%'
		public EnumLiteralDeclaration getModEnumLiteralDeclaration_2() { return cModEnumLiteralDeclaration_2; }
		
		//'%'
		public Keyword getModPercentSignKeyword_2_0() { return cModPercentSignKeyword_2_0; }
	}
	public class AdditiveOperatorElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.AdditiveOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cAddEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cAddPlusSignKeyword_0_0 = (Keyword)cAddEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cSubEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cSubHyphenMinusKeyword_1_0 = (Keyword)cSubEnumLiteralDeclaration_1.eContents().get(0);
		
		//enum AdditiveOperator:
		//	add='+' | sub='-';
		public EnumRule getRule() { return rule; }
		
		//add='+' | sub='-'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//add='+'
		public EnumLiteralDeclaration getAddEnumLiteralDeclaration_0() { return cAddEnumLiteralDeclaration_0; }
		
		//'+'
		public Keyword getAddPlusSignKeyword_0_0() { return cAddPlusSignKeyword_0_0; }
		
		//sub='-'
		public EnumLiteralDeclaration getSubEnumLiteralDeclaration_1() { return cSubEnumLiteralDeclaration_1; }
		
		//'-'
		public Keyword getSubHyphenMinusKeyword_1_0() { return cSubHyphenMinusKeyword_1_0; }
	}
	public class EqualityOperatorElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.EqualityOperator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cSameEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cSameEqualsSignEqualsSignEqualsSignKeyword_0_0 = (Keyword)cSameEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cNsameEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cNsameExclamationMarkEqualsSignEqualsSignKeyword_1_0 = (Keyword)cNsameEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cEqEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cEqEqualsSignEqualsSignKeyword_2_0 = (Keyword)cEqEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cNeqEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cNeqExclamationMarkEqualsSignKeyword_3_0 = (Keyword)cNeqEnumLiteralDeclaration_3.eContents().get(0);
		
		//enum EqualityOperator:
		//	same='===' | nsame='!==' | eq='==' | neq='!=';
		public EnumRule getRule() { return rule; }
		
		//same='===' | nsame='!==' | eq='==' | neq='!='
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//same='==='
		public EnumLiteralDeclaration getSameEnumLiteralDeclaration_0() { return cSameEnumLiteralDeclaration_0; }
		
		//'==='
		public Keyword getSameEqualsSignEqualsSignEqualsSignKeyword_0_0() { return cSameEqualsSignEqualsSignEqualsSignKeyword_0_0; }
		
		//nsame='!=='
		public EnumLiteralDeclaration getNsameEnumLiteralDeclaration_1() { return cNsameEnumLiteralDeclaration_1; }
		
		//'!=='
		public Keyword getNsameExclamationMarkEqualsSignEqualsSignKeyword_1_0() { return cNsameExclamationMarkEqualsSignEqualsSignKeyword_1_0; }
		
		//eq='=='
		public EnumLiteralDeclaration getEqEnumLiteralDeclaration_2() { return cEqEnumLiteralDeclaration_2; }
		
		//'=='
		public Keyword getEqEqualsSignEqualsSignKeyword_2_0() { return cEqEqualsSignEqualsSignKeyword_2_0; }
		
		//neq='!='
		public EnumLiteralDeclaration getNeqEnumLiteralDeclaration_3() { return cNeqEnumLiteralDeclaration_3; }
		
		//'!='
		public Keyword getNeqExclamationMarkEqualsSignKeyword_3_0() { return cNeqExclamationMarkEqualsSignKeyword_3_0; }
	}
	public class N4ModifierElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.N4Modifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cPrivateEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cPrivatePrivateKeyword_0_0 = (Keyword)cPrivateEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cProjectEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cProjectProjectKeyword_1_0 = (Keyword)cProjectEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cProtectedEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cProtectedProtectedKeyword_2_0 = (Keyword)cProtectedEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cPublicEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cPublicPublicKeyword_3_0 = (Keyword)cPublicEnumLiteralDeclaration_3.eContents().get(0);
		private final EnumLiteralDeclaration cExternalEnumLiteralDeclaration_4 = (EnumLiteralDeclaration)cAlternatives.eContents().get(4);
		private final Keyword cExternalExternalKeyword_4_0 = (Keyword)cExternalEnumLiteralDeclaration_4.eContents().get(0);
		private final EnumLiteralDeclaration cAbstractEnumLiteralDeclaration_5 = (EnumLiteralDeclaration)cAlternatives.eContents().get(5);
		private final Keyword cAbstractAbstractKeyword_5_0 = (Keyword)cAbstractEnumLiteralDeclaration_5.eContents().get(0);
		private final EnumLiteralDeclaration cStaticEnumLiteralDeclaration_6 = (EnumLiteralDeclaration)cAlternatives.eContents().get(6);
		private final Keyword cStaticStaticKeyword_6_0 = (Keyword)cStaticEnumLiteralDeclaration_6.eContents().get(0);
		private final EnumLiteralDeclaration cConstEnumLiteralDeclaration_7 = (EnumLiteralDeclaration)cAlternatives.eContents().get(7);
		private final Keyword cConstConstKeyword_7_0 = (Keyword)cConstEnumLiteralDeclaration_7.eContents().get(0);
		
		//enum N4Modifier:
		//	private | project | protected | public | external | abstract | static | const;
		public EnumRule getRule() { return rule; }
		
		//private | project | protected | public | external | abstract | static | const
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//private
		public EnumLiteralDeclaration getPrivateEnumLiteralDeclaration_0() { return cPrivateEnumLiteralDeclaration_0; }
		
		//"private"
		public Keyword getPrivatePrivateKeyword_0_0() { return cPrivatePrivateKeyword_0_0; }
		
		//project
		public EnumLiteralDeclaration getProjectEnumLiteralDeclaration_1() { return cProjectEnumLiteralDeclaration_1; }
		
		//"project"
		public Keyword getProjectProjectKeyword_1_0() { return cProjectProjectKeyword_1_0; }
		
		//protected
		public EnumLiteralDeclaration getProtectedEnumLiteralDeclaration_2() { return cProtectedEnumLiteralDeclaration_2; }
		
		//"protected"
		public Keyword getProtectedProtectedKeyword_2_0() { return cProtectedProtectedKeyword_2_0; }
		
		//public
		public EnumLiteralDeclaration getPublicEnumLiteralDeclaration_3() { return cPublicEnumLiteralDeclaration_3; }
		
		//"public"
		public Keyword getPublicPublicKeyword_3_0() { return cPublicPublicKeyword_3_0; }
		
		//external
		public EnumLiteralDeclaration getExternalEnumLiteralDeclaration_4() { return cExternalEnumLiteralDeclaration_4; }
		
		//"external"
		public Keyword getExternalExternalKeyword_4_0() { return cExternalExternalKeyword_4_0; }
		
		//abstract
		public EnumLiteralDeclaration getAbstractEnumLiteralDeclaration_5() { return cAbstractEnumLiteralDeclaration_5; }
		
		//"abstract"
		public Keyword getAbstractAbstractKeyword_5_0() { return cAbstractAbstractKeyword_5_0; }
		
		//static
		public EnumLiteralDeclaration getStaticEnumLiteralDeclaration_6() { return cStaticEnumLiteralDeclaration_6; }
		
		//"static"
		public Keyword getStaticStaticKeyword_6_0() { return cStaticStaticKeyword_6_0; }
		
		//const
		public EnumLiteralDeclaration getConstEnumLiteralDeclaration_7() { return cConstEnumLiteralDeclaration_7; }
		
		//"const"
		public Keyword getConstConstKeyword_7_0() { return cConstConstKeyword_7_0; }
	}
	
	private final ScriptElements pScript;
	private final ScriptElementElements pScriptElement;
	private final AnnotatedScriptElementElements pAnnotatedScriptElement;
	private final ExportDeclarationElements pExportDeclaration;
	private final ExportDeclarationImplElements pExportDeclarationImpl;
	private final ExportFromClauseElements pExportFromClause;
	private final ExportClauseElements pExportClause;
	private final ExportSpecifierElements pExportSpecifier;
	private final ExportableElementElements pExportableElement;
	private final AnnotatedExportableElementElements pAnnotatedExportableElement;
	private final ImportDeclarationElements pImportDeclaration;
	private final ImportDeclarationImplElements pImportDeclarationImpl;
	private final ImportClauseElements pImportClause;
	private final ImportSpecifiersExceptDefaultElements pImportSpecifiersExceptDefault;
	private final NamedImportSpecifierElements pNamedImportSpecifier;
	private final DefaultImportSpecifierElements pDefaultImportSpecifier;
	private final NamespaceImportSpecifierElements pNamespaceImportSpecifier;
	private final ModuleSpecifierElements pModuleSpecifier;
	private final FunctionDeclarationElements pFunctionDeclaration;
	private final AsyncNoTrailingLineBreakElements pAsyncNoTrailingLineBreak;
	private final FunctionImplElements pFunctionImpl;
	private final FunctionHeaderElements pFunctionHeader;
	private final FunctionBodyElements pFunctionBody;
	private final AnnotatedFunctionDeclarationElements pAnnotatedFunctionDeclaration;
	private final FunctionExpressionElements pFunctionExpression;
	private final AsyncFunctionExpressionElements pAsyncFunctionExpression;
	private final ArrowExpressionElements pArrowExpression;
	private final StrictFormalParametersElements pStrictFormalParameters;
	private final BindingIdentifierAsFormalParameterElements pBindingIdentifierAsFormalParameter;
	private final BlockMinusBracesElements pBlockMinusBraces;
	private final ExpressionDisguisedAsBlockElements pExpressionDisguisedAsBlock;
	private final AssignmentExpressionStatementElements pAssignmentExpressionStatement;
	private final AnnotatedExpressionElements pAnnotatedExpression;
	private final TypeVariableElements pTypeVariable;
	private final FormalParameterElements pFormalParameter;
	private final BindingElementFragmentElements pBindingElementFragment;
	private final BogusTypeRefFragmentElements pBogusTypeRefFragment;
	private final BlockElements pBlock;
	private final RootStatementElements pRootStatement;
	private final StatementElements pStatement;
	private final VariableStatementKeywordElements eVariableStatementKeyword;
	private final VariableStatementElements pVariableStatement;
	private final ExportedVariableStatementElements pExportedVariableStatement;
	private final VariableDeclarationOrBindingElements pVariableDeclarationOrBinding;
	private final VariableBindingElements pVariableBinding;
	private final VariableDeclarationElements pVariableDeclaration;
	private final VariableDeclarationImplElements pVariableDeclarationImpl;
	private final ExportedVariableDeclarationOrBindingElements pExportedVariableDeclarationOrBinding;
	private final ExportedVariableBindingElements pExportedVariableBinding;
	private final ExportedVariableDeclarationElements pExportedVariableDeclaration;
	private final EmptyStatementElements pEmptyStatement;
	private final ExpressionStatementElements pExpressionStatement;
	private final IfStatementElements pIfStatement;
	private final IterationStatementElements pIterationStatement;
	private final DoStatementElements pDoStatement;
	private final WhileStatementElements pWhileStatement;
	private final ForStatementElements pForStatement;
	private final LetIdentifierRefElements pLetIdentifierRef;
	private final LetAsIdentifierElements pLetAsIdentifier;
	private final BindingIdentifierAsVariableDeclarationElements pBindingIdentifierAsVariableDeclaration;
	private final ContinueStatementElements pContinueStatement;
	private final BreakStatementElements pBreakStatement;
	private final ReturnStatementElements pReturnStatement;
	private final WithStatementElements pWithStatement;
	private final SwitchStatementElements pSwitchStatement;
	private final CaseClauseElements pCaseClause;
	private final DefaultClauseElements pDefaultClause;
	private final LabelledStatementElements pLabelledStatement;
	private final ThrowStatementElements pThrowStatement;
	private final TryStatementElements pTryStatement;
	private final CatchBlockElements pCatchBlock;
	private final CatchVariableElements pCatchVariable;
	private final FinallyBlockElements pFinallyBlock;
	private final DebuggerStatementElements pDebuggerStatement;
	private final PrimaryExpressionElements pPrimaryExpression;
	private final ParenExpressionElements pParenExpression;
	private final IdentifierRefElements pIdentifierRef;
	private final SuperLiteralElements pSuperLiteral;
	private final ThisLiteralElements pThisLiteral;
	private final ArrayLiteralElements pArrayLiteral;
	private final ArrayPaddingElements pArrayPadding;
	private final ArrayElementElements pArrayElement;
	private final ObjectLiteralElements pObjectLiteral;
	private final PropertyAssignmentElements pPropertyAssignment;
	private final AnnotatedPropertyAssignmentElements pAnnotatedPropertyAssignment;
	private final PropertyMethodDeclarationElements pPropertyMethodDeclaration;
	private final PropertyNameValuePairElements pPropertyNameValuePair;
	private final PropertyNameValuePairSingleNameElements pPropertyNameValuePairSingleName;
	private final PropertyGetterDeclarationElements pPropertyGetterDeclaration;
	private final PropertySetterDeclarationElements pPropertySetterDeclaration;
	private final ParameterizedCallExpressionElements pParameterizedCallExpression;
	private final ConcreteTypeArgumentsElements pConcreteTypeArguments;
	private final ImportCallExpressionElements pImportCallExpression;
	private final LeftHandSideExpressionElements pLeftHandSideExpression;
	private final ArgumentsWithParenthesesElements pArgumentsWithParentheses;
	private final ArgumentsElements pArguments;
	private final ArgumentElements pArgument;
	private final MemberExpressionElements pMemberExpression;
	private final IndexedAccessExpressionTailElements pIndexedAccessExpressionTail;
	private final ParameterizedPropertyAccessExpressionTailElements pParameterizedPropertyAccessExpressionTail;
	private final PostfixExpressionElements pPostfixExpression;
	private final PostfixOperatorElements ePostfixOperator;
	private final CastExpressionElements pCastExpression;
	private final UnaryExpressionElements pUnaryExpression;
	private final UnaryOperatorElements eUnaryOperator;
	private final MultiplicativeExpressionElements pMultiplicativeExpression;
	private final MultiplicativeOperatorElements eMultiplicativeOperator;
	private final AdditiveExpressionElements pAdditiveExpression;
	private final AdditiveOperatorElements eAdditiveOperator;
	private final ShiftExpressionElements pShiftExpression;
	private final ShiftOperatorElements pShiftOperator;
	private final RelationalExpressionElements pRelationalExpression;
	private final RelationalOperatorElements pRelationalOperator;
	private final EqualityExpressionElements pEqualityExpression;
	private final EqualityOperatorElements eEqualityOperator;
	private final BitwiseANDExpressionElements pBitwiseANDExpression;
	private final BitwiseANDOperatorElements pBitwiseANDOperator;
	private final BitwiseXORExpressionElements pBitwiseXORExpression;
	private final BitwiseXOROperatorElements pBitwiseXOROperator;
	private final BitwiseORExpressionElements pBitwiseORExpression;
	private final BitwiseOROperatorElements pBitwiseOROperator;
	private final LogicalANDExpressionElements pLogicalANDExpression;
	private final LogicalANDOperatorElements pLogicalANDOperator;
	private final LogicalORExpressionElements pLogicalORExpression;
	private final LogicalOROperatorElements pLogicalOROperator;
	private final ConditionalExpressionElements pConditionalExpression;
	private final AssignmentExpressionElements pAssignmentExpression;
	private final YieldExpressionElements pYieldExpression;
	private final AssignmentOperatorElements pAssignmentOperator;
	private final AwaitExpressionElements pAwaitExpression;
	private final PromisifyExpressionElements pPromisifyExpression;
	private final ExpressionElements pExpression;
	private final TemplateLiteralElements pTemplateLiteral;
	private final TemplateExpressionEndElements pTemplateExpressionEnd;
	private final NoSubstitutionTemplateElements pNoSubstitutionTemplate;
	private final TemplateHeadElements pTemplateHead;
	private final TemplateTailElements pTemplateTail;
	private final TemplateMiddleElements pTemplateMiddle;
	private final LiteralElements pLiteral;
	private final NullLiteralElements pNullLiteral;
	private final BooleanLiteralElements pBooleanLiteral;
	private final StringLiteralElements pStringLiteral;
	private final NumericLiteralElements pNumericLiteral;
	private final DoubleLiteralElements pDoubleLiteral;
	private final IntLiteralElements pIntLiteral;
	private final OctalIntLiteralElements pOctalIntLiteral;
	private final LegacyOctalIntLiteralElements pLegacyOctalIntLiteral;
	private final HexIntLiteralElements pHexIntLiteral;
	private final BinaryIntLiteralElements pBinaryIntLiteral;
	private final ScientificIntLiteralElements pScientificIntLiteral;
	private final RegularExpressionLiteralElements pRegularExpressionLiteral;
	private final NumericLiteralAsStringElements pNumericLiteralAsString;
	private final IdentifierOrThisElements pIdentifierOrThis;
	private final AnnotationNameElements pAnnotationName;
	private final TerminalRule tDOUBLE;
	private final TerminalRule tHEX_INT;
	private final TerminalRule tBINARY_INT;
	private final TerminalRule tOCTAL_INT;
	private final TerminalRule tLEGACY_OCTAL_INT;
	private final TerminalRule tINT_SUFFIX;
	private final TerminalRule tSCIENTIFIC_INT;
	private final TerminalRule tEXPONENT_PART;
	private final TerminalRule tSIGNED_INT;
	private final TerminalRule tSTRING;
	private final TerminalRule tDOUBLE_STRING_CHAR;
	private final TerminalRule tSINGLE_STRING_CHAR;
	private final TerminalRule tBACKSLASH_SEQUENCE;
	private final TerminalRule tREGEX_CHAR;
	private final TerminalRule tREGEX_CHAR_OR_BRACKET;
	private final REGEX_LITERALElements pREGEX_LITERAL;
	private final TerminalRule tACTUAL_REGEX_TAIL;
	private final TerminalRule tREGEX_START;
	private final TerminalRule tREGEX_TAIL;
	private final TerminalRule tTEMPLATE_HEAD;
	private final TerminalRule tNO_SUBSTITUTION_TEMPLATE_LITERAL;
	private final TerminalRule tACTUAL_TEMPLATE_END;
	private final TerminalRule tTEMPLATE_LITERAL_CHAR;
	private final TemplateTailLiteralElements pTemplateTailLiteral;
	private final TemplateMiddleLiteralElements pTemplateMiddleLiteral;
	private final TerminalRule tTEMPLATE_MIDDLE;
	private final TerminalRule tTEMPLATE_END;
	private final TerminalRule tTEMPLATE_CONTINUATION;
	private final SemiElements pSemi;
	private final NoLineTerminatorElements pNoLineTerminator;
	private final TerminalRule tNO_LINE_TERMINATOR;
	private final AnnotationElements pAnnotation;
	private final ScriptAnnotationElements pScriptAnnotation;
	private final AnnotationNoAtSignElements pAnnotationNoAtSign;
	private final AnnotationArgumentElements pAnnotationArgument;
	private final LiteralAnnotationArgumentElements pLiteralAnnotationArgument;
	private final TypeRefAnnotationArgumentElements pTypeRefAnnotationArgument;
	private final AnnotationListElements pAnnotationList;
	private final ExpressionAnnotationListElements pExpressionAnnotationList;
	private final PropertyAssignmentAnnotationListElements pPropertyAssignmentAnnotationList;
	private final N4MemberAnnotationListElements pN4MemberAnnotationList;
	private final TypeReferenceElements pTypeReference;
	private final TypeReferenceNameElements pTypeReferenceName;
	private final N4ClassDeclarationElements pN4ClassDeclaration;
	private final MembersElements pMembers;
	private final ClassExtendsImplementsElements pClassExtendsImplements;
	private final ClassExtendsClauseElements pClassExtendsClause;
	private final ClassImplementsListElements pClassImplementsList;
	private final N4ClassExpressionElements pN4ClassExpression;
	private final N4InterfaceDeclarationElements pN4InterfaceDeclaration;
	private final InterfaceExtendsListElements pInterfaceExtendsList;
	private final N4EnumDeclarationElements pN4EnumDeclaration;
	private final N4EnumLiteralElements pN4EnumLiteral;
	private final N4ModifierElements eN4Modifier;
	private final N4MemberDeclarationElements pN4MemberDeclaration;
	private final AnnotatedN4MemberDeclarationElements pAnnotatedN4MemberDeclaration;
	private final FieldDeclarationImplElements pFieldDeclarationImpl;
	private final N4FieldDeclarationElements pN4FieldDeclaration;
	private final N4MethodDeclarationElements pN4MethodDeclaration;
	private final N4CallableConstructorDeclarationElements pN4CallableConstructorDeclaration;
	private final MethodParamsAndBodyElements pMethodParamsAndBody;
	private final MethodParamsReturnAndBodyElements pMethodParamsReturnAndBody;
	private final N4GetterDeclarationElements pN4GetterDeclaration;
	private final GetterHeaderElements pGetterHeader;
	private final N4SetterDeclarationElements pN4SetterDeclaration;
	private final BindingPatternElements pBindingPattern;
	private final ObjectBindingPatternElements pObjectBindingPattern;
	private final ArrayBindingPatternElements pArrayBindingPattern;
	private final BindingPropertyElements pBindingProperty;
	private final SingleNameBindingElements pSingleNameBinding;
	private final BindingElementElements pBindingElement;
	private final BindingRestElementElements pBindingRestElement;
	private final BindingElementImplElements pBindingElementImpl;
	private final ElisionElements pElision;
	private final LiteralOrComputedPropertyNameElements pLiteralOrComputedPropertyName;
	private final TerminalRule tINCOMPLETE_ASYNC_ARROW;
	private final JSXElementElements pJSXElement;
	private final JSXFragmentElements pJSXFragment;
	private final JSXChildElements pJSXChild;
	private final JSXExpressionElements pJSXExpression;
	private final JSXElementNameElements pJSXElementName;
	private final JSXElementNameExpressionElements pJSXElementNameExpression;
	private final JSXAttributesElements pJSXAttributes;
	private final JSXAttributeElements pJSXAttribute;
	private final JSXSpreadAttributeElements pJSXSpreadAttribute;
	private final JSXPropertyAttributeElements pJSXPropertyAttribute;
	private final VersionDeclarationElements pVersionDeclaration;
	
	private final Grammar grammar;
	
	private final TypeExpressionsGrammarAccess gaTypeExpressions;
	
	private final UnicodeGrammarAccess gaUnicode;

	@Inject
	public N4JSGrammarAccess(GrammarProvider grammarProvider,
			TypeExpressionsGrammarAccess gaTypeExpressions,
			UnicodeGrammarAccess gaUnicode) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTypeExpressions = gaTypeExpressions;
		this.gaUnicode = gaUnicode;
		this.pScript = new ScriptElements();
		this.pScriptElement = new ScriptElementElements();
		this.pAnnotatedScriptElement = new AnnotatedScriptElementElements();
		this.pExportDeclaration = new ExportDeclarationElements();
		this.pExportDeclarationImpl = new ExportDeclarationImplElements();
		this.pExportFromClause = new ExportFromClauseElements();
		this.pExportClause = new ExportClauseElements();
		this.pExportSpecifier = new ExportSpecifierElements();
		this.pExportableElement = new ExportableElementElements();
		this.pAnnotatedExportableElement = new AnnotatedExportableElementElements();
		this.pImportDeclaration = new ImportDeclarationElements();
		this.pImportDeclarationImpl = new ImportDeclarationImplElements();
		this.pImportClause = new ImportClauseElements();
		this.pImportSpecifiersExceptDefault = new ImportSpecifiersExceptDefaultElements();
		this.pNamedImportSpecifier = new NamedImportSpecifierElements();
		this.pDefaultImportSpecifier = new DefaultImportSpecifierElements();
		this.pNamespaceImportSpecifier = new NamespaceImportSpecifierElements();
		this.pModuleSpecifier = new ModuleSpecifierElements();
		this.pFunctionDeclaration = new FunctionDeclarationElements();
		this.pAsyncNoTrailingLineBreak = new AsyncNoTrailingLineBreakElements();
		this.pFunctionImpl = new FunctionImplElements();
		this.pFunctionHeader = new FunctionHeaderElements();
		this.pFunctionBody = new FunctionBodyElements();
		this.pAnnotatedFunctionDeclaration = new AnnotatedFunctionDeclarationElements();
		this.pFunctionExpression = new FunctionExpressionElements();
		this.pAsyncFunctionExpression = new AsyncFunctionExpressionElements();
		this.pArrowExpression = new ArrowExpressionElements();
		this.pStrictFormalParameters = new StrictFormalParametersElements();
		this.pBindingIdentifierAsFormalParameter = new BindingIdentifierAsFormalParameterElements();
		this.pBlockMinusBraces = new BlockMinusBracesElements();
		this.pExpressionDisguisedAsBlock = new ExpressionDisguisedAsBlockElements();
		this.pAssignmentExpressionStatement = new AssignmentExpressionStatementElements();
		this.pAnnotatedExpression = new AnnotatedExpressionElements();
		this.pTypeVariable = new TypeVariableElements();
		this.pFormalParameter = new FormalParameterElements();
		this.pBindingElementFragment = new BindingElementFragmentElements();
		this.pBogusTypeRefFragment = new BogusTypeRefFragmentElements();
		this.pBlock = new BlockElements();
		this.pRootStatement = new RootStatementElements();
		this.pStatement = new StatementElements();
		this.eVariableStatementKeyword = new VariableStatementKeywordElements();
		this.pVariableStatement = new VariableStatementElements();
		this.pExportedVariableStatement = new ExportedVariableStatementElements();
		this.pVariableDeclarationOrBinding = new VariableDeclarationOrBindingElements();
		this.pVariableBinding = new VariableBindingElements();
		this.pVariableDeclaration = new VariableDeclarationElements();
		this.pVariableDeclarationImpl = new VariableDeclarationImplElements();
		this.pExportedVariableDeclarationOrBinding = new ExportedVariableDeclarationOrBindingElements();
		this.pExportedVariableBinding = new ExportedVariableBindingElements();
		this.pExportedVariableDeclaration = new ExportedVariableDeclarationElements();
		this.pEmptyStatement = new EmptyStatementElements();
		this.pExpressionStatement = new ExpressionStatementElements();
		this.pIfStatement = new IfStatementElements();
		this.pIterationStatement = new IterationStatementElements();
		this.pDoStatement = new DoStatementElements();
		this.pWhileStatement = new WhileStatementElements();
		this.pForStatement = new ForStatementElements();
		this.pLetIdentifierRef = new LetIdentifierRefElements();
		this.pLetAsIdentifier = new LetAsIdentifierElements();
		this.pBindingIdentifierAsVariableDeclaration = new BindingIdentifierAsVariableDeclarationElements();
		this.pContinueStatement = new ContinueStatementElements();
		this.pBreakStatement = new BreakStatementElements();
		this.pReturnStatement = new ReturnStatementElements();
		this.pWithStatement = new WithStatementElements();
		this.pSwitchStatement = new SwitchStatementElements();
		this.pCaseClause = new CaseClauseElements();
		this.pDefaultClause = new DefaultClauseElements();
		this.pLabelledStatement = new LabelledStatementElements();
		this.pThrowStatement = new ThrowStatementElements();
		this.pTryStatement = new TryStatementElements();
		this.pCatchBlock = new CatchBlockElements();
		this.pCatchVariable = new CatchVariableElements();
		this.pFinallyBlock = new FinallyBlockElements();
		this.pDebuggerStatement = new DebuggerStatementElements();
		this.pPrimaryExpression = new PrimaryExpressionElements();
		this.pParenExpression = new ParenExpressionElements();
		this.pIdentifierRef = new IdentifierRefElements();
		this.pSuperLiteral = new SuperLiteralElements();
		this.pThisLiteral = new ThisLiteralElements();
		this.pArrayLiteral = new ArrayLiteralElements();
		this.pArrayPadding = new ArrayPaddingElements();
		this.pArrayElement = new ArrayElementElements();
		this.pObjectLiteral = new ObjectLiteralElements();
		this.pPropertyAssignment = new PropertyAssignmentElements();
		this.pAnnotatedPropertyAssignment = new AnnotatedPropertyAssignmentElements();
		this.pPropertyMethodDeclaration = new PropertyMethodDeclarationElements();
		this.pPropertyNameValuePair = new PropertyNameValuePairElements();
		this.pPropertyNameValuePairSingleName = new PropertyNameValuePairSingleNameElements();
		this.pPropertyGetterDeclaration = new PropertyGetterDeclarationElements();
		this.pPropertySetterDeclaration = new PropertySetterDeclarationElements();
		this.pParameterizedCallExpression = new ParameterizedCallExpressionElements();
		this.pConcreteTypeArguments = new ConcreteTypeArgumentsElements();
		this.pImportCallExpression = new ImportCallExpressionElements();
		this.pLeftHandSideExpression = new LeftHandSideExpressionElements();
		this.pArgumentsWithParentheses = new ArgumentsWithParenthesesElements();
		this.pArguments = new ArgumentsElements();
		this.pArgument = new ArgumentElements();
		this.pMemberExpression = new MemberExpressionElements();
		this.pIndexedAccessExpressionTail = new IndexedAccessExpressionTailElements();
		this.pParameterizedPropertyAccessExpressionTail = new ParameterizedPropertyAccessExpressionTailElements();
		this.pPostfixExpression = new PostfixExpressionElements();
		this.ePostfixOperator = new PostfixOperatorElements();
		this.pCastExpression = new CastExpressionElements();
		this.pUnaryExpression = new UnaryExpressionElements();
		this.eUnaryOperator = new UnaryOperatorElements();
		this.pMultiplicativeExpression = new MultiplicativeExpressionElements();
		this.eMultiplicativeOperator = new MultiplicativeOperatorElements();
		this.pAdditiveExpression = new AdditiveExpressionElements();
		this.eAdditiveOperator = new AdditiveOperatorElements();
		this.pShiftExpression = new ShiftExpressionElements();
		this.pShiftOperator = new ShiftOperatorElements();
		this.pRelationalExpression = new RelationalExpressionElements();
		this.pRelationalOperator = new RelationalOperatorElements();
		this.pEqualityExpression = new EqualityExpressionElements();
		this.eEqualityOperator = new EqualityOperatorElements();
		this.pBitwiseANDExpression = new BitwiseANDExpressionElements();
		this.pBitwiseANDOperator = new BitwiseANDOperatorElements();
		this.pBitwiseXORExpression = new BitwiseXORExpressionElements();
		this.pBitwiseXOROperator = new BitwiseXOROperatorElements();
		this.pBitwiseORExpression = new BitwiseORExpressionElements();
		this.pBitwiseOROperator = new BitwiseOROperatorElements();
		this.pLogicalANDExpression = new LogicalANDExpressionElements();
		this.pLogicalANDOperator = new LogicalANDOperatorElements();
		this.pLogicalORExpression = new LogicalORExpressionElements();
		this.pLogicalOROperator = new LogicalOROperatorElements();
		this.pConditionalExpression = new ConditionalExpressionElements();
		this.pAssignmentExpression = new AssignmentExpressionElements();
		this.pYieldExpression = new YieldExpressionElements();
		this.pAssignmentOperator = new AssignmentOperatorElements();
		this.pAwaitExpression = new AwaitExpressionElements();
		this.pPromisifyExpression = new PromisifyExpressionElements();
		this.pExpression = new ExpressionElements();
		this.pTemplateLiteral = new TemplateLiteralElements();
		this.pTemplateExpressionEnd = new TemplateExpressionEndElements();
		this.pNoSubstitutionTemplate = new NoSubstitutionTemplateElements();
		this.pTemplateHead = new TemplateHeadElements();
		this.pTemplateTail = new TemplateTailElements();
		this.pTemplateMiddle = new TemplateMiddleElements();
		this.pLiteral = new LiteralElements();
		this.pNullLiteral = new NullLiteralElements();
		this.pBooleanLiteral = new BooleanLiteralElements();
		this.pStringLiteral = new StringLiteralElements();
		this.pNumericLiteral = new NumericLiteralElements();
		this.pDoubleLiteral = new DoubleLiteralElements();
		this.pIntLiteral = new IntLiteralElements();
		this.pOctalIntLiteral = new OctalIntLiteralElements();
		this.pLegacyOctalIntLiteral = new LegacyOctalIntLiteralElements();
		this.pHexIntLiteral = new HexIntLiteralElements();
		this.pBinaryIntLiteral = new BinaryIntLiteralElements();
		this.pScientificIntLiteral = new ScientificIntLiteralElements();
		this.pRegularExpressionLiteral = new RegularExpressionLiteralElements();
		this.pNumericLiteralAsString = new NumericLiteralAsStringElements();
		this.pIdentifierOrThis = new IdentifierOrThisElements();
		this.pAnnotationName = new AnnotationNameElements();
		this.tDOUBLE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.DOUBLE");
		this.tHEX_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.HEX_INT");
		this.tBINARY_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BINARY_INT");
		this.tOCTAL_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.OCTAL_INT");
		this.tLEGACY_OCTAL_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.LEGACY_OCTAL_INT");
		this.tINT_SUFFIX = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.INT_SUFFIX");
		this.tSCIENTIFIC_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.SCIENTIFIC_INT");
		this.tEXPONENT_PART = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.EXPONENT_PART");
		this.tSIGNED_INT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.SIGNED_INT");
		this.tSTRING = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.STRING");
		this.tDOUBLE_STRING_CHAR = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.DOUBLE_STRING_CHAR");
		this.tSINGLE_STRING_CHAR = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.SINGLE_STRING_CHAR");
		this.tBACKSLASH_SEQUENCE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BACKSLASH_SEQUENCE");
		this.tREGEX_CHAR = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.REGEX_CHAR");
		this.tREGEX_CHAR_OR_BRACKET = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.REGEX_CHAR_OR_BRACKET");
		this.pREGEX_LITERAL = new REGEX_LITERALElements();
		this.tACTUAL_REGEX_TAIL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ACTUAL_REGEX_TAIL");
		this.tREGEX_START = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.REGEX_START");
		this.tREGEX_TAIL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.REGEX_TAIL");
		this.tTEMPLATE_HEAD = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TEMPLATE_HEAD");
		this.tNO_SUBSTITUTION_TEMPLATE_LITERAL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.NO_SUBSTITUTION_TEMPLATE_LITERAL");
		this.tACTUAL_TEMPLATE_END = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.ACTUAL_TEMPLATE_END");
		this.tTEMPLATE_LITERAL_CHAR = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TEMPLATE_LITERAL_CHAR");
		this.pTemplateTailLiteral = new TemplateTailLiteralElements();
		this.pTemplateMiddleLiteral = new TemplateMiddleLiteralElements();
		this.tTEMPLATE_MIDDLE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TEMPLATE_MIDDLE");
		this.tTEMPLATE_END = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TEMPLATE_END");
		this.tTEMPLATE_CONTINUATION = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.TEMPLATE_CONTINUATION");
		this.pSemi = new SemiElements();
		this.pNoLineTerminator = new NoLineTerminatorElements();
		this.tNO_LINE_TERMINATOR = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.NO_LINE_TERMINATOR");
		this.pAnnotation = new AnnotationElements();
		this.pScriptAnnotation = new ScriptAnnotationElements();
		this.pAnnotationNoAtSign = new AnnotationNoAtSignElements();
		this.pAnnotationArgument = new AnnotationArgumentElements();
		this.pLiteralAnnotationArgument = new LiteralAnnotationArgumentElements();
		this.pTypeRefAnnotationArgument = new TypeRefAnnotationArgumentElements();
		this.pAnnotationList = new AnnotationListElements();
		this.pExpressionAnnotationList = new ExpressionAnnotationListElements();
		this.pPropertyAssignmentAnnotationList = new PropertyAssignmentAnnotationListElements();
		this.pN4MemberAnnotationList = new N4MemberAnnotationListElements();
		this.pTypeReference = new TypeReferenceElements();
		this.pTypeReferenceName = new TypeReferenceNameElements();
		this.pN4ClassDeclaration = new N4ClassDeclarationElements();
		this.pMembers = new MembersElements();
		this.pClassExtendsImplements = new ClassExtendsImplementsElements();
		this.pClassExtendsClause = new ClassExtendsClauseElements();
		this.pClassImplementsList = new ClassImplementsListElements();
		this.pN4ClassExpression = new N4ClassExpressionElements();
		this.pN4InterfaceDeclaration = new N4InterfaceDeclarationElements();
		this.pInterfaceExtendsList = new InterfaceExtendsListElements();
		this.pN4EnumDeclaration = new N4EnumDeclarationElements();
		this.pN4EnumLiteral = new N4EnumLiteralElements();
		this.eN4Modifier = new N4ModifierElements();
		this.pN4MemberDeclaration = new N4MemberDeclarationElements();
		this.pAnnotatedN4MemberDeclaration = new AnnotatedN4MemberDeclarationElements();
		this.pFieldDeclarationImpl = new FieldDeclarationImplElements();
		this.pN4FieldDeclaration = new N4FieldDeclarationElements();
		this.pN4MethodDeclaration = new N4MethodDeclarationElements();
		this.pN4CallableConstructorDeclaration = new N4CallableConstructorDeclarationElements();
		this.pMethodParamsAndBody = new MethodParamsAndBodyElements();
		this.pMethodParamsReturnAndBody = new MethodParamsReturnAndBodyElements();
		this.pN4GetterDeclaration = new N4GetterDeclarationElements();
		this.pGetterHeader = new GetterHeaderElements();
		this.pN4SetterDeclaration = new N4SetterDeclarationElements();
		this.pBindingPattern = new BindingPatternElements();
		this.pObjectBindingPattern = new ObjectBindingPatternElements();
		this.pArrayBindingPattern = new ArrayBindingPatternElements();
		this.pBindingProperty = new BindingPropertyElements();
		this.pSingleNameBinding = new SingleNameBindingElements();
		this.pBindingElement = new BindingElementElements();
		this.pBindingRestElement = new BindingRestElementElements();
		this.pBindingElementImpl = new BindingElementImplElements();
		this.pElision = new ElisionElements();
		this.pLiteralOrComputedPropertyName = new LiteralOrComputedPropertyNameElements();
		this.tINCOMPLETE_ASYNC_ARROW = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.INCOMPLETE_ASYNC_ARROW");
		this.pJSXElement = new JSXElementElements();
		this.pJSXFragment = new JSXFragmentElements();
		this.pJSXChild = new JSXChildElements();
		this.pJSXExpression = new JSXExpressionElements();
		this.pJSXElementName = new JSXElementNameElements();
		this.pJSXElementNameExpression = new JSXElementNameExpressionElements();
		this.pJSXAttributes = new JSXAttributesElements();
		this.pJSXAttribute = new JSXAttributeElements();
		this.pJSXSpreadAttribute = new JSXSpreadAttributeElements();
		this.pJSXPropertyAttribute = new JSXPropertyAttributeElements();
		this.pVersionDeclaration = new VersionDeclarationElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.N4JS".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public TypeExpressionsGrammarAccess getTypeExpressionsGrammarAccess() {
		return gaTypeExpressions;
	}
	
	public UnicodeGrammarAccess getUnicodeGrammarAccess() {
		return gaUnicode;
	}

	
	//// ****************************************************************************************************
	//// [ECM11] A.5 Functions and Programs (p. 224)
	//// [ECM15]
	//// [ECMWiki] http://wiki.ecmascript.org/doku.php?id=harmony:modules
	//// ****************************************************************************************************
	//Script:
	//	{Script} (annotations+=ScriptAnnotation
	//	| scriptElements+=ScriptElement)*;
	public ScriptElements getScriptAccess() {
		return pScript;
	}
	
	public ParserRule getScriptRule() {
		return getScriptAccess().getRule();
	}
	
	///*
	// * The top level elements in a script are type declarations, exports, imports or statements
	// */ ScriptElement:
	//	AnnotatedScriptElement
	//	| N4ClassDeclaration<Yield=false> | N4InterfaceDeclaration<Yield=false> | N4EnumDeclaration<Yield=false> | =>
	//	ImportDeclaration // syntactic predicate required due to RootStatement > ExpressionStatement > ... > ImportCallExpression
	//	| ExportDeclaration
	//	| RootStatement<Yield=false>;
	public ScriptElementElements getScriptElementAccess() {
		return pScriptElement;
	}
	
	public ParserRule getScriptElementRule() {
		return getScriptElementAccess().getRule();
	}
	
	///**
	// * Left factored, annotated script elements.
	// *
	// * Pretty much inlined versions of type declarations, imports, exports and function declarations.
	// *
	// * The GrammarLinter ensures that the inlined content mirrors the content of the real declarations.
	// */ AnnotatedScriptElement ScriptElement:
	//	AnnotationList ({ExportDeclaration.annotationList=current} ExportDeclarationImpl
	//	| {ImportDeclaration.annotationList=current} ImportDeclarationImpl
	//	| {FunctionDeclaration.annotationList=current}
	//	=> (declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
	//	-> FunctionImpl <Yield=false,YieldIfGenerator=false,Expression=false>) | ({N4ClassDeclaration.annotationList=current}
	//	declaredModifiers+=N4Modifier*
	//	'class' typingStrategy=TypingStrategyDefSiteOperator?
	//	name=BindingIdentifier<Yield=false> VersionDeclaration?
	//	TypeVariables?
	//	ClassExtendsImplements<Yield=false>?
	//	| {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
	//	'interface' typingStrategy=TypingStrategyDefSiteOperator?
	//	name=BindingIdentifier<Yield=false> VersionDeclaration?
	//	TypeVariables?
	//	InterfaceExtendsList?) Members<Yield=false> | {N4EnumDeclaration.annotationList=current}
	//	declaredModifiers+=N4Modifier*
	//	'enum' name=BindingIdentifier<Yield=false> VersionDeclaration?
	//	'{'
	//	literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*
	//	'}');
	public AnnotatedScriptElementElements getAnnotatedScriptElementAccess() {
		return pAnnotatedScriptElement;
	}
	
	public ParserRule getAnnotatedScriptElementRule() {
		return getAnnotatedScriptElementAccess().getRule();
	}
	
	//ExportDeclaration:
	//	{ExportDeclaration} ExportDeclarationImpl;
	public ExportDeclarationElements getExportDeclarationAccess() {
		return pExportDeclaration;
	}
	
	public ParserRule getExportDeclarationRule() {
		return getExportDeclarationAccess().getRule();
	}
	
	//fragment ExportDeclarationImpl *:
	//	'export' (wildcardExport?='*' ExportFromClause Semi
	//	| ExportClause -> ExportFromClause? Semi
	//	| exportedElement=ExportableElement
	//	| defaultExport?='default' (-> exportedElement=ExportableElement |
	//	defaultExportedExpression=AssignmentExpression<In=true,Yield=false> Semi));
	public ExportDeclarationImplElements getExportDeclarationImplAccess() {
		return pExportDeclarationImpl;
	}
	
	public ParserRule getExportDeclarationImplRule() {
		return getExportDeclarationImplAccess().getRule();
	}
	
	//fragment ExportFromClause *:
	//	'from' reexportedFrom=[types::TModule|ModuleSpecifier];
	public ExportFromClauseElements getExportFromClauseAccess() {
		return pExportFromClause;
	}
	
	public ParserRule getExportFromClauseRule() {
		return getExportFromClauseAccess().getRule();
	}
	
	//fragment ExportClause *:
	//	'{' (namedExports+=ExportSpecifier (',' namedExports+=ExportSpecifier)* ','?)?
	//	'}';
	public ExportClauseElements getExportClauseAccess() {
		return pExportClause;
	}
	
	public ParserRule getExportClauseRule() {
		return getExportClauseAccess().getRule();
	}
	
	//ExportSpecifier:
	//	element=IdentifierRef<Yield=false> ('as' alias=IdentifierName)?;
	public ExportSpecifierElements getExportSpecifierAccess() {
		return pExportSpecifier;
	}
	
	public ParserRule getExportSpecifierRule() {
		return getExportSpecifierAccess().getRule();
	}
	
	//ExportableElement:
	//	AnnotatedExportableElement<Yield=false> | N4ClassDeclaration<Yield=false> | N4InterfaceDeclaration<Yield=false> |
	//	N4EnumDeclaration<Yield=false> | FunctionDeclaration<Yield=false> | ExportedVariableStatement;
	public ExportableElementElements getExportableElementAccess() {
		return pExportableElement;
	}
	
	public ParserRule getExportableElementRule() {
		return getExportableElementAccess().getRule();
	}
	
	///**
	// * Left factored, annotated exportable elements.
	// *
	// * Pretty much inlined versions of type and function declarations.
	// *
	// * The GrammarLinter ensures that the inlined content mirrors the content of the real declarations.
	// */ AnnotatedExportableElement <Yield ExportableElement:
	//	AnnotationList ({FunctionDeclaration.annotationList=current} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
	//	FunctionImpl<Yield,Yield,Expression=false> | {ExportedVariableStatement.annotationList=current}
	//	declaredModifiers+=N4Modifier*
	//	varStmtKeyword=VariableStatementKeyword
	//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield> (','
	//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield>)* Semi
	//	| ({N4ClassDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
	//	'class' typingStrategy=TypingStrategyDefSiteOperator?
	//	name=BindingIdentifier<Yield> TypeVariables?
	//	ClassExtendsImplements<Yield>?
	//	| {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
	//	'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield> TypeVariables?
	//	InterfaceExtendsList?) Members<Yield> | {N4EnumDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
	//	'enum' name=BindingIdentifier<Yield>
	//	'{'
	//	literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*
	//	'}');
	public AnnotatedExportableElementElements getAnnotatedExportableElementAccess() {
		return pAnnotatedExportableElement;
	}
	
	public ParserRule getAnnotatedExportableElementRule() {
		return getAnnotatedExportableElementAccess().getRule();
	}
	
	//ImportDeclaration:
	//	{ImportDeclaration} ImportDeclarationImpl;
	public ImportDeclarationElements getImportDeclarationAccess() {
		return pImportDeclaration;
	}
	
	public ParserRule getImportDeclarationRule() {
		return getImportDeclarationAccess().getRule();
	}
	
	//fragment ImportDeclarationImpl *:
	//	'import' (ImportClause importFrom?='from')? module=[types::TModule|ModuleSpecifier] Semi;
	public ImportDeclarationImplElements getImportDeclarationImplAccess() {
		return pImportDeclarationImpl;
	}
	
	public ParserRule getImportDeclarationImplRule() {
		return getImportDeclarationImplAccess().getRule();
	}
	
	//fragment ImportClause returns ImportDeclaration:
	//	importSpecifiers+=DefaultImportSpecifier (',' ImportSpecifiersExceptDefault)?
	//	| ImportSpecifiersExceptDefault;
	public ImportClauseElements getImportClauseAccess() {
		return pImportClause;
	}
	
	public ParserRule getImportClauseRule() {
		return getImportClauseAccess().getRule();
	}
	
	//fragment ImportSpecifiersExceptDefault returns ImportDeclaration:
	//	importSpecifiers+=NamespaceImportSpecifier
	//	| '{' (importSpecifiers+=NamedImportSpecifier (',' importSpecifiers+=NamedImportSpecifier)* ','?)? '}';
	public ImportSpecifiersExceptDefaultElements getImportSpecifiersExceptDefaultAccess() {
		return pImportSpecifiersExceptDefault;
	}
	
	public ParserRule getImportSpecifiersExceptDefaultRule() {
		return getImportSpecifiersExceptDefaultAccess().getRule();
	}
	
	//NamedImportSpecifier:
	//	importedElement=[types::TExportableElement|BindingIdentifier<Yield=false>] |
	//	importedElement=[types::TExportableElement|IdentifierName] 'as' alias=BindingIdentifier<Yield=false>;
	public NamedImportSpecifierElements getNamedImportSpecifierAccess() {
		return pNamedImportSpecifier;
	}
	
	public ParserRule getNamedImportSpecifierRule() {
		return getNamedImportSpecifierAccess().getRule();
	}
	
	//DefaultImportSpecifier:
	//	importedElement=[types::TExportableElement|BindingIdentifier<Yield=false>];
	public DefaultImportSpecifierElements getDefaultImportSpecifierAccess() {
		return pDefaultImportSpecifier;
	}
	
	public ParserRule getDefaultImportSpecifierRule() {
		return getDefaultImportSpecifierAccess().getRule();
	}
	
	//NamespaceImportSpecifier:
	//	{NamespaceImportSpecifier} '*' 'as' alias=BindingIdentifier<false> declaredDynamic?='+'?;
	public NamespaceImportSpecifierElements getNamespaceImportSpecifierAccess() {
		return pNamespaceImportSpecifier;
	}
	
	public ParserRule getNamespaceImportSpecifierRule() {
		return getNamespaceImportSpecifierAccess().getRule();
	}
	
	//ModuleSpecifier:
	//	STRING;
	public ModuleSpecifierElements getModuleSpecifierAccess() {
		return pModuleSpecifier;
	}
	
	public ParserRule getModuleSpecifierRule() {
		return getModuleSpecifierAccess().getRule();
	}
	
	///*
	// * A function declaration without annotations. The annotated variant is factored into
	// * an own production AnnotatedFunctionDeclaration to avoid the infinite lookahead
	// * of the annotation list
	// */ FunctionDeclaration <Yield>:
	//	=> ({FunctionDeclaration} declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
	//	-> FunctionImpl <Yield,Yield,Expression=false>) => Semi?;
	public FunctionDeclarationElements getFunctionDeclarationAccess() {
		return pFunctionDeclaration;
	}
	
	public ParserRule getFunctionDeclarationRule() {
		return getFunctionDeclarationAccess().getRule();
	}
	
	//fragment AsyncNoTrailingLineBreak *:
	//	(declaredAsync?='async' NoLineTerminator)?;
	public AsyncNoTrailingLineBreakElements getAsyncNoTrailingLineBreakAccess() {
		return pAsyncNoTrailingLineBreak;
	}
	
	public ParserRule getAsyncNoTrailingLineBreakRule() {
		return getAsyncNoTrailingLineBreakAccess().getRule();
	}
	
	//fragment FunctionImpl <Yield, YieldIfGenerator, Expression> *:
	//	'function' (generator?='*' FunctionHeader<YieldIfGenerator,Generator=true> FunctionBody<Yield=true,Expression> |
	//	FunctionHeader<Yield,Generator=false> FunctionBody<Yield=false,Expression>);
	public FunctionImplElements getFunctionImplAccess() {
		return pFunctionImpl;
	}
	
	public ParserRule getFunctionImplRule() {
		return getFunctionImplAccess().getRule();
	}
	
	//fragment FunctionHeader <Yield, Generator> *:
	//	TypeVariables?
	//	name=BindingIdentifier<Yield>?
	//	VersionDeclaration?
	//	StrictFormalParameters<Yield=Generator> -> ColonSepReturnTypeRef?;
	public FunctionHeaderElements getFunctionHeaderAccess() {
		return pFunctionHeader;
	}
	
	public ParserRule getFunctionHeaderRule() {
		return getFunctionHeaderAccess().getRule();
	}
	
	//fragment FunctionBody <Yield, Expression> *:
	//	<Expression> body=Block<Yield> | <!Expression> body=Block<Yield>?;
	public FunctionBodyElements getFunctionBodyAccess() {
		return pFunctionBody;
	}
	
	public ParserRule getFunctionBodyRule() {
		return getFunctionBodyAccess().getRule();
	}
	
	///*
	// * Used only within statement blocks, the annotated functions on the root level
	// * are handled by the rule AnnotatedScriptElement and its inlined content of FunctionDeclaration
	// */ AnnotatedFunctionDeclaration <Yield, Default FunctionDeclaration:
	//	annotationList=AnnotationList
	//	declaredModifiers+=N4Modifier* AsyncNoTrailingLineBreak
	//	FunctionImpl<Yield,Yield,Expression=false>;
	public AnnotatedFunctionDeclarationElements getAnnotatedFunctionDeclarationAccess() {
		return pAnnotatedFunctionDeclaration;
	}
	
	public ParserRule getAnnotatedFunctionDeclarationRule() {
		return getAnnotatedFunctionDeclarationAccess().getRule();
	}
	
	//FunctionExpression:
	//	{FunctionExpression} FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>;
	public FunctionExpressionElements getFunctionExpressionAccess() {
		return pFunctionExpression;
	}
	
	public ParserRule getFunctionExpressionRule() {
		return getFunctionExpressionAccess().getRule();
	}
	
	///**
	// * We cannot use fragments here since we have to combine the terminals into a syntactic predicate.
	// */ AsyncFunctionExpression FunctionExpression:
	//	=> (declaredAsync?='async' NoLineTerminator 'function') FunctionHeader<Yield=false,Generator=false>
	//	FunctionBody<Yield=false,Expression=true>;
	public AsyncFunctionExpressionElements getAsyncFunctionExpressionAccess() {
		return pAsyncFunctionExpression;
	}
	
	public ParserRule getAsyncFunctionExpressionRule() {
		return getAsyncFunctionExpressionAccess().getRule();
	}
	
	//ArrowExpression <In, Yield ArrowFunction:
	//	=> ((StrictFormalParameters<Yield> ColonSepReturnTypeRef?
	//	| => (declaredAsync?='async' NoLineTerminator -> StrictFormalParameters <Yield>) ColonSepReturnTypeRef?
	//	| fpars+=BindingIdentifierAsFormalParameter<Yield>)
	//	/* no line terminator here, guaranteed implicitly */ '=>') (-> hasBracesAroundBody?='{' body=BlockMinusBraces<Yield>
	//	'}' | body=ExpressionDisguisedAsBlock<In>);
	public ArrowExpressionElements getArrowExpressionAccess() {
		return pArrowExpression;
	}
	
	public ParserRule getArrowExpressionRule() {
		return getArrowExpressionAccess().getRule();
	}
	
	//fragment StrictFormalParameters <Yield> *:
	//	'(' (fpars+=FormalParameter<Yield> (',' fpars+=FormalParameter<Yield>)*)? ')';
	public StrictFormalParametersElements getStrictFormalParametersAccess() {
		return pStrictFormalParameters;
	}
	
	public ParserRule getStrictFormalParametersRule() {
		return getStrictFormalParametersAccess().getRule();
	}
	
	//BindingIdentifierAsFormalParameter <Yield FormalParameter:
	//	name=BindingIdentifier<Yield>;
	public BindingIdentifierAsFormalParameterElements getBindingIdentifierAsFormalParameterAccess() {
		return pBindingIdentifierAsFormalParameter;
	}
	
	public ParserRule getBindingIdentifierAsFormalParameterRule() {
		return getBindingIdentifierAsFormalParameterAccess().getRule();
	}
	
	//BlockMinusBraces <Yield Block:
	//	{Block} statements+=Statement<Yield>*;
	public BlockMinusBracesElements getBlockMinusBracesAccess() {
		return pBlockMinusBraces;
	}
	
	public ParserRule getBlockMinusBracesRule() {
		return getBlockMinusBracesAccess().getRule();
	}
	
	//ExpressionDisguisedAsBlock <In Block:
	//	{Block} statements+=AssignmentExpressionStatement<In>;
	public ExpressionDisguisedAsBlockElements getExpressionDisguisedAsBlockAccess() {
		return pExpressionDisguisedAsBlock;
	}
	
	public ParserRule getExpressionDisguisedAsBlockRule() {
		return getExpressionDisguisedAsBlockAccess().getRule();
	}
	
	//AssignmentExpressionStatement <In ExpressionStatement:
	//	expression=AssignmentExpression<In,Yield=false>;
	public AssignmentExpressionStatementElements getAssignmentExpressionStatementAccess() {
		return pAssignmentExpressionStatement;
	}
	
	public ParserRule getAssignmentExpressionStatementRule() {
		return getAssignmentExpressionStatementAccess().getRule();
	}
	
	///**
	// * Left factored, annotated expression.
	// *
	// * Pretty much inlined versions of function expression and class expression.
	// *
	// * The GrammarLinter ensures that the inlined content mirrors the content of the real declarations.
	// */ AnnotatedExpression <Yield Expression:
	//	ExpressionAnnotationList ({N4ClassExpression.annotationList=current}
	//	'class' name=BindingIdentifier<Yield>?
	//	ClassExtendsImplements<Yield>?
	//	Members<Yield> | {FunctionExpression.annotationList=current} AsyncNoTrailingLineBreak
	//	FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>);
	public AnnotatedExpressionElements getAnnotatedExpressionAccess() {
		return pAnnotatedExpression;
	}
	
	public ParserRule getAnnotatedExpressionRule() {
		return getAnnotatedExpressionAccess().getRule();
	}
	
	//@Override
	//TypeVariable types::TypeVariable:
	//	(declaredCovariant?='out' | declaredContravariant?='in')?
	//	name=IdentifierOrThis ('extends' declaredUpperBound=TypeRef)?;
	public TypeVariableElements getTypeVariableAccess() {
		return pTypeVariable;
	}
	
	public ParserRule getTypeVariableRule() {
		return getTypeVariableAccess().getRule();
	}
	
	//FormalParameter <Yield>:
	//	{FormalParameter} BindingElementFragment<Yield>;
	public FormalParameterElements getFormalParameterAccess() {
		return pFormalParameter;
	}
	
	public ParserRule getFormalParameterRule() {
		return getFormalParameterAccess().getRule();
	}
	
	//fragment BindingElementFragment <Yield> *:
	//	(=> bindingPattern=BindingPattern<Yield> | annotations+=Annotation* BogusTypeRefFragment? variadic?='...'?
	//	name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?) (hasInitializerAssignment?='='
	//	initializer=AssignmentExpression<In=true,Yield>?)?;
	public BindingElementFragmentElements getBindingElementFragmentAccess() {
		return pBindingElementFragment;
	}
	
	public ParserRule getBindingElementFragmentRule() {
		return getBindingElementFragmentAccess().getRule();
	}
	
	//fragment BogusTypeRefFragment *:
	//	bogusTypeRef=TypeRefWithModifiers;
	public BogusTypeRefFragmentElements getBogusTypeRefFragmentAccess() {
		return pBogusTypeRefFragment;
	}
	
	public ParserRule getBogusTypeRefFragmentRule() {
		return getBogusTypeRefFragmentAccess().getRule();
	}
	
	//Block <Yield>:
	//	=> ({Block} '{') statements+=Statement<Yield>* '}';
	public BlockElements getBlockAccess() {
		return pBlock;
	}
	
	public ParserRule getBlockRule() {
		return getBlockAccess().getRule();
	}
	
	//// ****************************************************************************************************
	//// [ECM11] A.4 Statements (p. 222)
	//// ****************************************************************************************************
	//RootStatement <Yield Statement:
	//	Block<Yield> | FunctionDeclaration<Yield> | VariableStatement<In=true,Yield> | EmptyStatement
	//	| LabelledStatement<Yield> | ExpressionStatement<Yield> | IfStatement<Yield> | IterationStatement<Yield> |
	//	ContinueStatement<Yield> | BreakStatement<Yield> | ReturnStatement<Yield> | WithStatement<Yield> |
	//	SwitchStatement<Yield> | ThrowStatement<Yield> | TryStatement<Yield> | DebuggerStatement;
	public RootStatementElements getRootStatementAccess() {
		return pRootStatement;
	}
	
	public ParserRule getRootStatementRule() {
		return getRootStatementAccess().getRule();
	}
	
	//Statement <Yield>:
	//	AnnotatedFunctionDeclaration<Yield,Default=false> | RootStatement<Yield>;
	public StatementElements getStatementAccess() {
		return pStatement;
	}
	
	public ParserRule getStatementRule() {
		return getStatementAccess().getRule();
	}
	
	//enum VariableStatementKeyword:
	//	var | const | let;
	public VariableStatementKeywordElements getVariableStatementKeywordAccess() {
		return eVariableStatementKeyword;
	}
	
	public EnumRule getVariableStatementKeywordRule() {
		return getVariableStatementKeywordAccess().getRule();
	}
	
	//VariableStatement <In, Yield>:
	//	=> ({VariableStatement} varStmtKeyword=VariableStatementKeyword)
	//	varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false> (','
	//	varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false>)* Semi;
	public VariableStatementElements getVariableStatementAccess() {
		return pVariableStatement;
	}
	
	public ParserRule getVariableStatementRule() {
		return getVariableStatementAccess().getRule();
	}
	
	//ExportedVariableStatement:
	//	{ExportedVariableStatement} declaredModifiers+=N4Modifier*
	//	varStmtKeyword=VariableStatementKeyword
	//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false> (','
	//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false>)* Semi;
	public ExportedVariableStatementElements getExportedVariableStatementAccess() {
		return pExportedVariableStatement;
	}
	
	public ParserRule getExportedVariableStatementRule() {
		return getExportedVariableStatementAccess().getRule();
	}
	
	//VariableDeclarationOrBinding <In, Yield, OptionalInit>:
	//	VariableBinding<In,Yield,OptionalInit> | VariableDeclaration<In,Yield,true>;
	public VariableDeclarationOrBindingElements getVariableDeclarationOrBindingAccess() {
		return pVariableDeclarationOrBinding;
	}
	
	public ParserRule getVariableDeclarationOrBindingRule() {
		return getVariableDeclarationOrBindingAccess().getRule();
	}
	
	//VariableBinding <In, Yield, OptionalInit>:
	//	=> pattern=BindingPattern<Yield> (<OptionalInit> ('=' expression=AssignmentExpression<In,Yield>)?
	//	| <!OptionalInit> '=' expression=AssignmentExpression<In,Yield>);
	public VariableBindingElements getVariableBindingAccess() {
		return pVariableBinding;
	}
	
	public ParserRule getVariableBindingRule() {
		return getVariableBindingAccess().getRule();
	}
	
	//VariableDeclaration <In, Yield, AllowType>:
	//	{VariableDeclaration} VariableDeclarationImpl<In,Yield,AllowType>;
	public VariableDeclarationElements getVariableDeclarationAccess() {
		return pVariableDeclaration;
	}
	
	public ParserRule getVariableDeclarationRule() {
		return getVariableDeclarationAccess().getRule();
	}
	
	///**
	// * This rule was very complicated for Java like type annotations. It is much simpler with the ES4 colon style type annotations. However,
	// * just in case odd things will happen, may look at the previous version in the git history.
	// *
	// * The colon type annotation syntax clashes with object literals and object destruction. While we still support java type annotation in the
	// * former case, we do not allow types in the latter. This may be changed in the future.
	// */ fragment VariableDeclarationImpl <In, Yield, AllowType> *:
	//	annotations+=Annotation* (<AllowType> => (name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?) ('='
	//	expression=AssignmentExpression<In,Yield>)?
	//	| <!AllowType> => (name=BindingIdentifier<Yield>) ('=' expression=AssignmentExpression<In,Yield>)?);
	public VariableDeclarationImplElements getVariableDeclarationImplAccess() {
		return pVariableDeclarationImpl;
	}
	
	public ParserRule getVariableDeclarationImplRule() {
		return getVariableDeclarationImplAccess().getRule();
	}
	
	//ExportedVariableDeclarationOrBinding <Yield VariableDeclarationOrBinding:
	//	ExportedVariableBinding<Yield> | ExportedVariableDeclaration<Yield>;
	public ExportedVariableDeclarationOrBindingElements getExportedVariableDeclarationOrBindingAccess() {
		return pExportedVariableDeclarationOrBinding;
	}
	
	public ParserRule getExportedVariableDeclarationOrBindingRule() {
		return getExportedVariableDeclarationOrBindingAccess().getRule();
	}
	
	//ExportedVariableBinding <Yield>:
	//	=> pattern=BindingPattern<Yield> '=' expression=AssignmentExpression<In=true,Yield>;
	public ExportedVariableBindingElements getExportedVariableBindingAccess() {
		return pExportedVariableBinding;
	}
	
	public ParserRule getExportedVariableBindingRule() {
		return getExportedVariableBindingAccess().getRule();
	}
	
	///**
	// * The created AST element has an additional reference to the inferred TVariable
	// */ ExportedVariableDeclaration <Yield>:
	//	{ExportedVariableDeclaration} VariableDeclarationImpl<In=true,Yield,AllowType=true>;
	public ExportedVariableDeclarationElements getExportedVariableDeclarationAccess() {
		return pExportedVariableDeclaration;
	}
	
	public ParserRule getExportedVariableDeclarationRule() {
		return getExportedVariableDeclarationAccess().getRule();
	}
	
	//// Defined with Action in statement: Block: {Block}  '{' (statements+=Statement)* '}';
	//EmptyStatement:
	//	{EmptyStatement} ';';
	public EmptyStatementElements getEmptyStatementAccess() {
		return pEmptyStatement;
	}
	
	public ParserRule getEmptyStatementRule() {
		return getEmptyStatementAccess().getRule();
	}
	
	//// Lookahead (function, {) done elsewhere: see Statement and SourceElement definitions
	//ExpressionStatement <Yield>:
	//	expression=Expression<In=true,Yield> Semi;
	public ExpressionStatementElements getExpressionStatementAccess() {
		return pExpressionStatement;
	}
	
	public ParserRule getExpressionStatementRule() {
		return getExpressionStatementAccess().getRule();
	}
	
	//IfStatement <Yield>:
	//	'if' '(' expression=Expression<In=true,Yield> ')' ifStmt=Statement<Yield> (=> 'else' elseStmt=Statement<Yield>)?;
	public IfStatementElements getIfStatementAccess() {
		return pIfStatement;
	}
	
	public ParserRule getIfStatementRule() {
		return getIfStatementAccess().getRule();
	}
	
	//IterationStatement <Yield>:
	//	DoStatement<Yield> | WhileStatement<Yield> | ForStatement<Yield>;
	public IterationStatementElements getIterationStatementAccess() {
		return pIterationStatement;
	}
	
	public ParserRule getIterationStatementRule() {
		return getIterationStatementAccess().getRule();
	}
	
	//DoStatement <Yield>:
	//	'do' statement=Statement<Yield> 'while' '(' expression=Expression<In=true,Yield> ')' => Semi?;
	public DoStatementElements getDoStatementAccess() {
		return pDoStatement;
	}
	
	public ParserRule getDoStatementRule() {
		return getDoStatementAccess().getRule();
	}
	
	//WhileStatement <Yield>:
	//	'while' '(' expression=Expression<In=true,Yield> ')' statement=Statement<Yield>;
	public WhileStatementElements getWhileStatementAccess() {
		return pWhileStatement;
	}
	
	public ParserRule getWhileStatementRule() {
		return getWhileStatementAccess().getRule();
	}
	
	//ForStatement <Yield>:
	//	{ForStatement} 'for' '(' (
	//	// this is not in the spec as far as I can tell, but there are tests that rely on this to be valid JS
	//	=> (initExpr=LetIdentifierRef forIn?='in' expression=Expression<In=true,Yield> ')') | (->
	//	varStmtKeyword=VariableStatementKeyword (=>
	//	(varDeclsOrBindings+=BindingIdentifierAsVariableDeclaration<In=false,Yield> (forIn?='in' | forOf?='of') ->
	//	expression=AssignmentExpression<In=true,Yield>?) |
	//	varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,OptionalInit=true> ((','
	//	varDeclsOrBindings+=VariableDeclarationOrBinding<In=false,Yield,false>)* ';' expression=Expression<In=true,Yield>? ';'
	//	updateExpr=Expression<In=true,Yield>?
	//	| forIn?='in' expression=Expression<In=true,Yield>?
	//	| forOf?='of' expression=AssignmentExpression<In=true,Yield>?)) | initExpr=Expression<In=false,Yield> (';'
	//	expression=Expression<In=true,Yield>? ';' updateExpr=Expression<In=true,Yield>?
	//	| forIn?='in' expression=Expression<In=true,Yield>?
	//	| forOf?='of' expression=AssignmentExpression<In=true,Yield>?) | ';' expression=Expression<In=true,Yield>? ';'
	//	updateExpr=Expression<In=true,Yield>?)
	//	')') statement=Statement<Yield>;
	public ForStatementElements getForStatementAccess() {
		return pForStatement;
	}
	
	public ParserRule getForStatementRule() {
		return getForStatementAccess().getRule();
	}
	
	//LetIdentifierRef IdentifierRef:
	//	id=[types::IdentifiableElement|LetAsIdentifier];
	public LetIdentifierRefElements getLetIdentifierRefAccess() {
		return pLetIdentifierRef;
	}
	
	public ParserRule getLetIdentifierRefRule() {
		return getLetIdentifierRefAccess().getRule();
	}
	
	//LetAsIdentifier:
	//	'let';
	public LetAsIdentifierElements getLetAsIdentifierAccess() {
		return pLetAsIdentifier;
	}
	
	public ParserRule getLetAsIdentifierRule() {
		return getLetAsIdentifierAccess().getRule();
	}
	
	//BindingIdentifierAsVariableDeclaration <In, Yield VariableDeclaration:
	//	name=BindingIdentifier<Yield>;
	public BindingIdentifierAsVariableDeclarationElements getBindingIdentifierAsVariableDeclarationAccess() {
		return pBindingIdentifierAsVariableDeclaration;
	}
	
	public ParserRule getBindingIdentifierAsVariableDeclarationRule() {
		return getBindingIdentifierAsVariableDeclarationAccess().getRule();
	}
	
	///**
	// * The AutomaticSemicolonInjector rewrites the antlr grammar for this rule to inject the promotion of EOL to a statement delimiter.
	// */ ContinueStatement <Yield>:
	//	{ContinueStatement} 'continue' label=[LabelledStatement|BindingIdentifier<Yield>]? Semi;
	public ContinueStatementElements getContinueStatementAccess() {
		return pContinueStatement;
	}
	
	public ParserRule getContinueStatementRule() {
		return getContinueStatementAccess().getRule();
	}
	
	///**
	// * The AutomaticSemicolonInjector rewrites the antlr grammar for this rule to inject the promotion of EOL to a statement delimiter.
	// */ BreakStatement <Yield>:
	//	{BreakStatement} 'break' label=[LabelledStatement|BindingIdentifier<Yield>]? Semi;
	public BreakStatementElements getBreakStatementAccess() {
		return pBreakStatement;
	}
	
	public ParserRule getBreakStatementRule() {
		return getBreakStatementAccess().getRule();
	}
	
	///**
	// * The AutomaticSemicolonInjector rewrites the antlr grammar for this rule to inject the promotion of EOL to a statement delimiter.
	// */ ReturnStatement <Yield>:
	//	{ReturnStatement} 'return' expression=Expression<In=true,Yield>? Semi;
	public ReturnStatementElements getReturnStatementAccess() {
		return pReturnStatement;
	}
	
	public ParserRule getReturnStatementRule() {
		return getReturnStatementAccess().getRule();
	}
	
	//WithStatement <Yield>:
	//	'with' '(' expression=Expression<In=true,Yield> ')' statement=Statement<Yield>;
	public WithStatementElements getWithStatementAccess() {
		return pWithStatement;
	}
	
	public ParserRule getWithStatementRule() {
		return getWithStatementAccess().getRule();
	}
	
	///*
	// * All clauses are added to a single list, in order to retain order of the clauses. In particular,
	// * the position of the default clause is
	// */ SwitchStatement <Yield>:
	//	'switch' '(' expression=Expression<In=true,Yield> ')' '{'
	//	cases+=CaseClause<Yield>* (cases+=DefaultClause<Yield> cases+=CaseClause<Yield>*)? '}';
	public SwitchStatementElements getSwitchStatementAccess() {
		return pSwitchStatement;
	}
	
	public ParserRule getSwitchStatementRule() {
		return getSwitchStatementAccess().getRule();
	}
	
	//CaseClause <Yield>:
	//	'case' expression=Expression<In=true,Yield> ':' statements+=Statement<Yield>*;
	public CaseClauseElements getCaseClauseAccess() {
		return pCaseClause;
	}
	
	public ParserRule getCaseClauseRule() {
		return getCaseClauseAccess().getRule();
	}
	
	//DefaultClause <Yield>:
	//	{DefaultClause} 'default' ':' statements+=Statement<Yield>*;
	public DefaultClauseElements getDefaultClauseAccess() {
		return pDefaultClause;
	}
	
	public ParserRule getDefaultClauseRule() {
		return getDefaultClauseAccess().getRule();
	}
	
	///**
	// * Simplified: [ECM15] distinguishes between BindingIdentifier and LabelIdentifier which are effectively the same
	// */ LabelledStatement <Yield>:
	//	=> (name=BindingIdentifier<Yield> ':') statement=Statement<Yield>;
	public LabelledStatementElements getLabelledStatementAccess() {
		return pLabelledStatement;
	}
	
	public ParserRule getLabelledStatementRule() {
		return getLabelledStatementAccess().getRule();
	}
	
	//// This is rewritten by the AutomaticSemicolonInjector (see above)
	//ThrowStatement <Yield>:
	//	'throw' expression=Expression<In=true,Yield> Semi;
	public ThrowStatementElements getThrowStatementAccess() {
		return pThrowStatement;
	}
	
	public ParserRule getThrowStatementRule() {
		return getThrowStatementAccess().getRule();
	}
	
	//TryStatement <Yield>:
	//	'try' block=Block<Yield> (catch=CatchBlock<Yield> finally=FinallyBlock<Yield>? | finally=FinallyBlock<Yield>);
	public TryStatementElements getTryStatementAccess() {
		return pTryStatement;
	}
	
	public ParserRule getTryStatementRule() {
		return getTryStatementAccess().getRule();
	}
	
	//CatchBlock <Yield>:
	//	{CatchBlock} 'catch' '(' catchVariable=CatchVariable<Yield> ')' block=Block<Yield>;
	public CatchBlockElements getCatchBlockAccess() {
		return pCatchBlock;
	}
	
	public ParserRule getCatchBlockRule() {
		return getCatchBlockAccess().getRule();
	}
	
	///**
	// * CatchVariable must not have a type reference, this is tested during validation (to enable better error messages).
	// */ CatchVariable <Yield>:
	//	=> bindingPattern=BindingPattern<Yield> | => (name=BindingIdentifier<Yield> -> ColonSepDeclaredTypeRef) |
	//	BogusTypeRefFragment? name=BindingIdentifier<Yield>;
	public CatchVariableElements getCatchVariableAccess() {
		return pCatchVariable;
	}
	
	public ParserRule getCatchVariableRule() {
		return getCatchVariableAccess().getRule();
	}
	
	//FinallyBlock <Yield>:
	//	{FinallyBlock} 'finally' block=Block<Yield>;
	public FinallyBlockElements getFinallyBlockAccess() {
		return pFinallyBlock;
	}
	
	public ParserRule getFinallyBlockRule() {
		return getFinallyBlockAccess().getRule();
	}
	
	///**
	// * This is rewritten by the AutomaticSemicolonInjector (see above)
	// */ DebuggerStatement:
	//	{DebuggerStatement} 'debugger' Semi;
	public DebuggerStatementElements getDebuggerStatementAccess() {
		return pDebuggerStatement;
	}
	
	public ParserRule getDebuggerStatementRule() {
		return getDebuggerStatementAccess().getRule();
	}
	
	//// ****************************************************************************************************
	//// [ECM11] A.3 Expressions (p. 218)
	//// ****************************************************************************************************
	//// Primary expressions ([ECM11] 11.1)
	//PrimaryExpression <Yield Expression:
	//	ThisLiteral
	//	| SuperLiteral
	//	| IdentifierRef<Yield> | JSXFragment /* see JSX  */
	//	| JSXElement /* see JSX  */
	//	| ImportCallExpression<Yield> | ParameterizedCallExpression<Yield> | Literal
	//	| ArrayLiteral<Yield> | ObjectLiteral<Yield> | ParenExpression<Yield> | AnnotatedExpression<Yield> |
	//	FunctionExpression
	//	| AsyncFunctionExpression
	//	| N4ClassExpression<Yield> | TemplateLiteral<Yield>;
	public PrimaryExpressionElements getPrimaryExpressionAccess() {
		return pPrimaryExpression;
	}
	
	public ParserRule getPrimaryExpressionRule() {
		return getPrimaryExpressionAccess().getRule();
	}
	
	//ParenExpression <Yield>:
	//	'(' expression=Expression<In=true,Yield> ')';
	public ParenExpressionElements getParenExpressionAccess() {
		return pParenExpression;
	}
	
	public ParserRule getParenExpressionRule() {
		return getParenExpressionAccess().getRule();
	}
	
	//IdentifierRef <Yield>:
	//	id=[types::IdentifiableElement|BindingIdentifier<Yield>] | {VersionedIdentifierRef}
	//	id=[types::IdentifiableElement|BindingIdentifier<Yield>] VersionRequest;
	public IdentifierRefElements getIdentifierRefAccess() {
		return pIdentifierRef;
	}
	
	public ParserRule getIdentifierRefRule() {
		return getIdentifierRefAccess().getRule();
	}
	
	//SuperLiteral:
	//	{SuperLiteral} 'super';
	public SuperLiteralElements getSuperLiteralAccess() {
		return pSuperLiteral;
	}
	
	public ParserRule getSuperLiteralRule() {
		return getSuperLiteralAccess().getRule();
	}
	
	//ThisLiteral:
	//	{ThisLiteral} 'this';
	public ThisLiteralElements getThisLiteralAccess() {
		return pThisLiteral;
	}
	
	public ParserRule getThisLiteralRule() {
		return getThisLiteralAccess().getRule();
	}
	
	///**
	// * As described in the spec, array literals may use elisions to influence the
	// * index of expressions in the array.
	// * This is achieved by special ArrayElements, called ArrayPadding, which are
	// * represented by a ',' in the concrete syntax.
	// *
	// * 	ArrayLiteral :
	// * 		[ Elision/opt ]
	// * 		[ ElementList ]
	// * 		[ ElementList , Elision/opt ]
	// * 	ElementList :
	// * 		Elision/opt AssignmentExpression
	// * 		ElementList , Elision/opt AssignmentExpression
	// * 	Elision :
	// * 		,
	// * 		Elision ,
	// *
	// */ ArrayLiteral <Yield>:
	//	{ArrayLiteral} '['
	//	elements+=ArrayPadding* (elements+=ArrayElement<Yield> (',' elements+=ArrayPadding* elements+=ArrayElement<Yield>)*
	//	(trailingComma?=',' elements+=ArrayPadding*)?)?
	//	']';
	public ArrayLiteralElements getArrayLiteralAccess() {
		return pArrayLiteral;
	}
	
	public ParserRule getArrayLiteralRule() {
		return getArrayLiteralAccess().getRule();
	}
	
	///**
	// * This array element is used to pad the remaining elements, e.g. to get the
	// * length and index right
	// */ ArrayPadding ArrayElement:
	//	{ArrayPadding} ',';
	public ArrayPaddingElements getArrayPaddingAccess() {
		return pArrayPadding;
	}
	
	public ParserRule getArrayPaddingRule() {
		return getArrayPaddingAccess().getRule();
	}
	
	//ArrayElement <Yield>:
	//	{ArrayElement} spread?='...'? expression=AssignmentExpression<In=true,Yield>;
	public ArrayElementElements getArrayElementAccess() {
		return pArrayElement;
	}
	
	public ParserRule getArrayElementRule() {
		return getArrayElementAccess().getRule();
	}
	
	//ObjectLiteral <Yield>:
	//	{ObjectLiteral}
	//	'{' (propertyAssignments+=PropertyAssignment<Yield> (',' propertyAssignments+=PropertyAssignment<Yield>)* ','?)?
	//	'}';
	public ObjectLiteralElements getObjectLiteralAccess() {
		return pObjectLiteral;
	}
	
	public ParserRule getObjectLiteralRule() {
		return getObjectLiteralAccess().getRule();
	}
	
	//PropertyAssignment <Yield>:
	//	AnnotatedPropertyAssignment<Yield> | PropertyNameValuePair<Yield> | PropertyGetterDeclaration<Yield> |
	//	PropertySetterDeclaration<Yield> | PropertyMethodDeclaration<Yield> | PropertyNameValuePairSingleName<Yield>;
	public PropertyAssignmentElements getPropertyAssignmentAccess() {
		return pPropertyAssignment;
	}
	
	public ParserRule getPropertyAssignmentRule() {
		return getPropertyAssignmentAccess().getRule();
	}
	
	//AnnotatedPropertyAssignment <Yield PropertyAssignment:
	//	PropertyAssignmentAnnotationList (
	//	// TODO extract property header into an own instance to defer the object instantiation
	//	=> ({PropertyNameValuePair.annotationList=current} declaredTypeRef=TypeRefWithModifiers?
	//	declaredName=LiteralOrComputedPropertyName<Yield> ':') expression=AssignmentExpression<In=true,Yield> | =>
	//	({PropertyGetterDeclaration.annotationList=current} GetterHeader<Yield>) body=Block<Yield=false> | =>
	//	({PropertySetterDeclaration.annotationList=current}
	//	'set' -> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')'
	//	body=Block<Yield=false> | => ({PropertyMethodDeclaration.annotationList=current} TypeVariables?
	//	returnTypeRef=TypeRefWithModifiers? (generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> ->
	//	MethodParamsAndBody <Generator=true> | declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody
	//	<Generator=false>)) ';'?
	//	| {PropertyNameValuePairSingleName.annotationList=current} declaredTypeRef=TypeRef? identifierRef=IdentifierRef<Yield>
	//	('=' expression=AssignmentExpression<In=true,Yield>)?);
	public AnnotatedPropertyAssignmentElements getAnnotatedPropertyAssignmentAccess() {
		return pAnnotatedPropertyAssignment;
	}
	
	public ParserRule getAnnotatedPropertyAssignmentRule() {
		return getAnnotatedPropertyAssignmentAccess().getRule();
	}
	
	//PropertyMethodDeclaration <Yield>:
	//	=> ({PropertyMethodDeclaration} TypeVariables? returnTypeRef=TypeRefWithModifiers? (generator?='*'
	//	declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
	//	declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>))
	//	';'?;
	public PropertyMethodDeclarationElements getPropertyMethodDeclarationAccess() {
		return pPropertyMethodDeclaration;
	}
	
	public ParserRule getPropertyMethodDeclarationRule() {
		return getPropertyMethodDeclarationAccess().getRule();
	}
	
	//PropertyNameValuePair <Yield>:
	//	=> ({PropertyNameValuePair} declaredTypeRef=TypeRefWithModifiers?
	//	declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'?
	//	':') expression=AssignmentExpression<In=true,Yield>;
	public PropertyNameValuePairElements getPropertyNameValuePairAccess() {
		return pPropertyNameValuePair;
	}
	
	public ParserRule getPropertyNameValuePairRule() {
		return getPropertyNameValuePairAccess().getRule();
	}
	
	///*
	// * Support for single name syntax in ObjectLiteral (but disallowed in actual object literals by ASTStructureValidator
	// * except in assignment destructuring patterns)
	// */ PropertyNameValuePairSingleName <Yield>:
	//	declaredTypeRef=TypeRef?
	//	identifierRef=IdentifierRef<Yield> ('=' expression=AssignmentExpression<In=true,Yield>)?;
	public PropertyNameValuePairSingleNameElements getPropertyNameValuePairSingleNameAccess() {
		return pPropertyNameValuePairSingleName;
	}
	
	public ParserRule getPropertyNameValuePairSingleNameRule() {
		return getPropertyNameValuePairSingleNameAccess().getRule();
	}
	
	//PropertyGetterDeclaration <Yield>:
	//	=> ({PropertyGetterDeclaration} GetterHeader<Yield>) body=Block<Yield=false>;
	public PropertyGetterDeclarationElements getPropertyGetterDeclarationAccess() {
		return pPropertyGetterDeclaration;
	}
	
	public ParserRule getPropertyGetterDeclarationRule() {
		return getPropertyGetterDeclarationAccess().getRule();
	}
	
	//PropertySetterDeclaration <Yield>:
	//	=> ({PropertySetterDeclaration}
	//	'set'
	//	-> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'?
	//	'(' fpar=FormalParameter<Yield> ')' body=Block<Yield=false>;
	public PropertySetterDeclarationElements getPropertySetterDeclarationAccess() {
		return pPropertySetterDeclaration;
	}
	
	public ParserRule getPropertySetterDeclarationRule() {
		return getPropertySetterDeclarationAccess().getRule();
	}
	
	///* Left-hand-side expressions (11.2) [ECM11]
	// * Heavily refactored to make them LL(*) compliant.
	// */ ParameterizedCallExpression <Yield>:
	//	ConcreteTypeArguments
	//	target=IdentifierRef<Yield> ArgumentsWithParentheses<Yield>;
	public ParameterizedCallExpressionElements getParameterizedCallExpressionAccess() {
		return pParameterizedCallExpression;
	}
	
	public ParserRule getParameterizedCallExpressionRule() {
		return getParameterizedCallExpressionAccess().getRule();
	}
	
	//fragment ConcreteTypeArguments *:
	//	'<' typeArgs+=TypeRef (',' typeArgs+=TypeRef)* '>';
	public ConcreteTypeArgumentsElements getConcreteTypeArgumentsAccess() {
		return pConcreteTypeArguments;
	}
	
	public ParserRule getConcreteTypeArgumentsRule() {
		return getConcreteTypeArgumentsAccess().getRule();
	}
	
	//ImportCallExpression <Yield>:
	//	'import' ArgumentsWithParentheses<Yield>;
	public ImportCallExpressionElements getImportCallExpressionAccess() {
		return pImportCallExpression;
	}
	
	public ParserRule getImportCallExpressionRule() {
		return getImportCallExpressionAccess().getRule();
	}
	
	//LeftHandSideExpression <Yield Expression:
	//	MemberExpression<Yield> ({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield>
	//	({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield> |
	//	{IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
	//	{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> | ->
	//	({TaggedTemplateString.target=current} template=TemplateLiteral<Yield>))*)?;
	public LeftHandSideExpressionElements getLeftHandSideExpressionAccess() {
		return pLeftHandSideExpression;
	}
	
	public ParserRule getLeftHandSideExpressionRule() {
		return getLeftHandSideExpressionAccess().getRule();
	}
	
	//fragment ArgumentsWithParentheses <Yield> *:
	//	'(' Arguments<Yield>? ')';
	public ArgumentsWithParenthesesElements getArgumentsWithParenthesesAccess() {
		return pArgumentsWithParentheses;
	}
	
	public ParserRule getArgumentsWithParenthesesRule() {
		return getArgumentsWithParenthesesAccess().getRule();
	}
	
	//fragment Arguments <Yield> *:
	//	arguments+=Argument<Yield> (',' arguments+=Argument<Yield>)*;
	public ArgumentsElements getArgumentsAccess() {
		return pArguments;
	}
	
	public ParserRule getArgumentsRule() {
		return getArgumentsAccess().getRule();
	}
	
	//Argument <Yield>:
	//	spread?='...'? expression=AssignmentExpression<In=true,Yield>;
	public ArgumentElements getArgumentAccess() {
		return pArgument;
	}
	
	public ParserRule getArgumentRule() {
		return getArgumentAccess().getRule();
	}
	
	//MemberExpression <Yield Expression:
	//	=> ({NewTarget} 'new' '.') 'target'
	//	| => ({NewExpression} 'new') callee=MemberExpression<Yield> -> ConcreteTypeArguments? (=> withArgs?='('
	//	Arguments<Yield>? ')' ({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
	//	{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
	//	{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*)?
	//	| PrimaryExpression<Yield> ({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
	//	{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
	//	{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*;
	public MemberExpressionElements getMemberExpressionAccess() {
		return pMemberExpression;
	}
	
	public ParserRule getMemberExpressionRule() {
		return getMemberExpressionAccess().getRule();
	}
	
	//fragment IndexedAccessExpressionTail <Yield> *:
	//	'[' index=Expression<In=true,Yield> ']';
	public IndexedAccessExpressionTailElements getIndexedAccessExpressionTailAccess() {
		return pIndexedAccessExpressionTail;
	}
	
	public ParserRule getIndexedAccessExpressionTailRule() {
		return getIndexedAccessExpressionTailAccess().getRule();
	}
	
	//fragment ParameterizedPropertyAccessExpressionTail <Yield> *:
	//	'.' ConcreteTypeArguments? property=[types::IdentifiableElement|IdentifierName];
	public ParameterizedPropertyAccessExpressionTailElements getParameterizedPropertyAccessExpressionTailAccess() {
		return pParameterizedPropertyAccessExpressionTail;
	}
	
	public ParserRule getParameterizedPropertyAccessExpressionTailRule() {
		return getParameterizedPropertyAccessExpressionTailAccess().getRule();
	}
	
	///**
	// * Postfix expressions ([ECM11] 11.3).
	// * The specification states that there are no line terminators allowed before the postfix operators.
	// * This is enforced by the call to promoteEOL in the action before ( '++' | '--' ),
	// * added during grammar post-processing.
	// * We only must promote EOLs when the la is '++' or '--' because this production is chained as all expression rules.
	// * In other words: only promote EOL when we are really in a postfix expression. A check on the la will ensure this.
	// */ PostfixExpression <Yield Expression:
	//	LeftHandSideExpression<Yield>
	//	=> ({PostfixExpression.expression=current} op=PostfixOperator)?;
	public PostfixExpressionElements getPostfixExpressionAccess() {
		return pPostfixExpression;
	}
	
	public ParserRule getPostfixExpressionRule() {
		return getPostfixExpressionAccess().getRule();
	}
	
	//enum PostfixOperator:
	//	inc='++' | dec='--';
	public PostfixOperatorElements getPostfixOperatorAccess() {
		return ePostfixOperator;
	}
	
	public EnumRule getPostfixOperatorRule() {
		return getPostfixOperatorAccess().getRule();
	}
	
	///* Cast expression (N4JS 6.2.3) */ CastExpression <Yield Expression:
	//	PostfixExpression<Yield> (=> ({CastExpression.expression=current} 'as') targetTypeRef=ArrayTypeExpression)?;
	public CastExpressionElements getCastExpressionAccess() {
		return pCastExpression;
	}
	
	public ParserRule getCastExpressionRule() {
		return getCastExpressionAccess().getRule();
	}
	
	///* Unary operators ([ECM11] 11.4) */ UnaryExpression <Yield Expression:
	//	CastExpression<Yield> | {UnaryExpression} op=UnaryOperator expression=UnaryExpression<Yield>;
	public UnaryExpressionElements getUnaryExpressionAccess() {
		return pUnaryExpression;
	}
	
	public ParserRule getUnaryExpressionRule() {
		return getUnaryExpressionAccess().getRule();
	}
	
	//enum UnaryOperator:
	//	delete | void | typeof | inc='++' | dec='--' | pos='+' | neg='-' | inv='~' | not='!';
	public UnaryOperatorElements getUnaryOperatorAccess() {
		return eUnaryOperator;
	}
	
	public EnumRule getUnaryOperatorRule() {
		return getUnaryOperatorAccess().getRule();
	}
	
	///* Multiplicative operators ([ECM11] 11.5) */ MultiplicativeExpression <Yield Expression:
	//	UnaryExpression<Yield> (=> ({MultiplicativeExpression.lhs=current} op=MultiplicativeOperator)
	//	rhs=UnaryExpression<Yield>)*;
	public MultiplicativeExpressionElements getMultiplicativeExpressionAccess() {
		return pMultiplicativeExpression;
	}
	
	public ParserRule getMultiplicativeExpressionRule() {
		return getMultiplicativeExpressionAccess().getRule();
	}
	
	//enum MultiplicativeOperator:
	//	times='*' | div='/' | mod='%';
	public MultiplicativeOperatorElements getMultiplicativeOperatorAccess() {
		return eMultiplicativeOperator;
	}
	
	public EnumRule getMultiplicativeOperatorRule() {
		return getMultiplicativeOperatorAccess().getRule();
	}
	
	///* Additive operators ([ECM11] 11.6) */ AdditiveExpression <Yield Expression:
	//	MultiplicativeExpression<Yield> (=> ({AdditiveExpression.lhs=current} op=AdditiveOperator)
	//	rhs=MultiplicativeExpression<Yield>)*;
	public AdditiveExpressionElements getAdditiveExpressionAccess() {
		return pAdditiveExpression;
	}
	
	public ParserRule getAdditiveExpressionRule() {
		return getAdditiveExpressionAccess().getRule();
	}
	
	//enum AdditiveOperator:
	//	add='+' | sub='-';
	public AdditiveOperatorElements getAdditiveOperatorAccess() {
		return eAdditiveOperator;
	}
	
	public EnumRule getAdditiveOperatorRule() {
		return getAdditiveOperatorAccess().getRule();
	}
	
	//// Bitwise shift operators ([ECM11] 11.7)
	///**
	// * Note that the whole expression, including the rhs, must be in the syntactic
	// * predicate in order to avoid problems stemming from the parameterized function call
	// * and from the assignment operator >>>=
	// */ ShiftExpression <Yield Expression:
	//	AdditiveExpression<Yield> => ({ShiftExpression.lhs=current} op=ShiftOperator rhs=AdditiveExpression<Yield>)*;
	public ShiftExpressionElements getShiftExpressionAccess() {
		return pShiftExpression;
	}
	
	public ParserRule getShiftExpressionRule() {
		return getShiftExpressionAccess().getRule();
	}
	
	///**  solve conflict with generics, e.g., List<List<C>> */ ShiftOperator ShiftOperator:
	//	'>' '>' '>'?
	//	| '<<';
	public ShiftOperatorElements getShiftOperatorAccess() {
		return pShiftOperator;
	}
	
	public ParserRule getShiftOperatorRule() {
		return getShiftOperatorAccess().getRule();
	}
	
	///*
	// * Note that the whole expression, including the rhs, must be in the syntactic
	// * predicate in order to avoid problems stemming from the parameterized function call
	// * and from the assignment operator >>>=
	// */ // Relational operators (11.8)
	//RelationalExpression <In, Yield Expression:
	//	ShiftExpression<Yield>
	//	=> ({RelationalExpression.lhs=current} op=RelationalOperator<In> -> rhs=ShiftExpression<Yield>)*;
	public RelationalExpressionElements getRelationalExpressionAccess() {
		return pRelationalExpression;
	}
	
	public ParserRule getRelationalExpressionRule() {
		return getRelationalExpressionAccess().getRule();
	}
	
	//RelationalOperator <In RelationalOperator:
	//	'<' | '>' '='? | '<=' | 'instanceof' | <In> 'in';
	public RelationalOperatorElements getRelationalOperatorAccess() {
		return pRelationalOperator;
	}
	
	public ParserRule getRelationalOperatorRule() {
		return getRelationalOperatorAccess().getRule();
	}
	
	//// Equality operators (11.9)
	//EqualityExpression <In, Yield Expression:
	//	RelationalExpression<In,Yield> (=> ({EqualityExpression.lhs=current} op=EqualityOperator)
	//	rhs=RelationalExpression<In,Yield>)*;
	public EqualityExpressionElements getEqualityExpressionAccess() {
		return pEqualityExpression;
	}
	
	public ParserRule getEqualityExpressionRule() {
		return getEqualityExpressionAccess().getRule();
	}
	
	//enum EqualityOperator:
	//	same='===' | nsame='!==' | eq='==' | neq='!=';
	public EqualityOperatorElements getEqualityOperatorAccess() {
		return eEqualityOperator;
	}
	
	public EnumRule getEqualityOperatorRule() {
		return getEqualityOperatorAccess().getRule();
	}
	
	//// Binary bitwise operators (11.10, N4JS Spec 6.1.17)
	//BitwiseANDExpression <In, Yield Expression:
	//	EqualityExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseANDOperator)
	//	rhs=EqualityExpression<In,Yield>)*;
	public BitwiseANDExpressionElements getBitwiseANDExpressionAccess() {
		return pBitwiseANDExpression;
	}
	
	public ParserRule getBitwiseANDExpressionRule() {
		return getBitwiseANDExpressionAccess().getRule();
	}
	
	//BitwiseANDOperator BinaryBitwiseOperator:
	//	'&';
	public BitwiseANDOperatorElements getBitwiseANDOperatorAccess() {
		return pBitwiseANDOperator;
	}
	
	public ParserRule getBitwiseANDOperatorRule() {
		return getBitwiseANDOperatorAccess().getRule();
	}
	
	//BitwiseXORExpression <In, Yield Expression:
	//	BitwiseANDExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseXOROperator)
	//	rhs=BitwiseANDExpression<In,Yield>)*;
	public BitwiseXORExpressionElements getBitwiseXORExpressionAccess() {
		return pBitwiseXORExpression;
	}
	
	public ParserRule getBitwiseXORExpressionRule() {
		return getBitwiseXORExpressionAccess().getRule();
	}
	
	//BitwiseXOROperator BinaryBitwiseOperator:
	//	'^';
	public BitwiseXOROperatorElements getBitwiseXOROperatorAccess() {
		return pBitwiseXOROperator;
	}
	
	public ParserRule getBitwiseXOROperatorRule() {
		return getBitwiseXOROperatorAccess().getRule();
	}
	
	//BitwiseORExpression <In, Yield Expression:
	//	BitwiseXORExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator)
	//	rhs=BitwiseXORExpression<In,Yield>)*;
	public BitwiseORExpressionElements getBitwiseORExpressionAccess() {
		return pBitwiseORExpression;
	}
	
	public ParserRule getBitwiseORExpressionRule() {
		return getBitwiseORExpressionAccess().getRule();
	}
	
	//BitwiseOROperator BinaryBitwiseOperator:
	//	'|';
	public BitwiseOROperatorElements getBitwiseOROperatorAccess() {
		return pBitwiseOROperator;
	}
	
	public ParserRule getBitwiseOROperatorRule() {
		return getBitwiseOROperatorAccess().getRule();
	}
	
	//// $<Binary logical operators ([ECM11] 11.11)
	//LogicalANDExpression <In, Yield Expression:
	//	BitwiseORExpression<In,Yield> (=> ({BinaryLogicalExpression.lhs=current} op=LogicalANDOperator)
	//	rhs=BitwiseORExpression<In,Yield>)*;
	public LogicalANDExpressionElements getLogicalANDExpressionAccess() {
		return pLogicalANDExpression;
	}
	
	public ParserRule getLogicalANDExpressionRule() {
		return getLogicalANDExpressionAccess().getRule();
	}
	
	//LogicalANDOperator BinaryLogicalOperator:
	//	'&&';
	public LogicalANDOperatorElements getLogicalANDOperatorAccess() {
		return pLogicalANDOperator;
	}
	
	public ParserRule getLogicalANDOperatorRule() {
		return getLogicalANDOperatorAccess().getRule();
	}
	
	//LogicalORExpression <In, Yield Expression:
	//	LogicalANDExpression<In,Yield> (=> ({BinaryLogicalExpression.lhs=current} op=LogicalOROperator)
	//	rhs=LogicalANDExpression<In,Yield>)*;
	public LogicalORExpressionElements getLogicalORExpressionAccess() {
		return pLogicalORExpression;
	}
	
	public ParserRule getLogicalORExpressionRule() {
		return getLogicalORExpressionAccess().getRule();
	}
	
	//LogicalOROperator BinaryLogicalOperator:
	//	'||';
	public LogicalOROperatorElements getLogicalOROperatorAccess() {
		return pLogicalOROperator;
	}
	
	public ParserRule getLogicalOROperatorRule() {
		return getLogicalOROperatorAccess().getRule();
	}
	
	///**
	// * Conditional operator ([ECM11] 11.12)
	// */ ConditionalExpression <In, Yield Expression:
	//	LogicalORExpression<In,Yield> (=> ({ConditionalExpression.expression=current} '?')
	//	trueExpression=AssignmentExpression<In=true,Yield> ':' falseExpression=AssignmentExpression<In,Yield>)?;
	public ConditionalExpressionElements getConditionalExpressionAccess() {
		return pConditionalExpression;
	}
	
	public ParserRule getConditionalExpressionRule() {
		return getConditionalExpressionAccess().getRule();
	}
	
	///*
	// * Assignment operators ([ECM11] 11.13)
	// */ AssignmentExpression <In, Yield Expression:
	//	AwaitExpression<In,Yield> | PromisifyExpression<In,Yield> | ArrowExpression<In,Yield> | <Yield> YieldExpression<In> |
	//	ConditionalExpression<In,Yield> (=> ({AssignmentExpression.lhs=current} op=AssignmentOperator)
	//	rhs=AssignmentExpression<In,Yield>)?;
	public AssignmentExpressionElements getAssignmentExpressionAccess() {
		return pAssignmentExpression;
	}
	
	public ParserRule getAssignmentExpressionRule() {
		return getAssignmentExpressionAccess().getRule();
	}
	
	//YieldExpression <In Expression:
	//	{YieldExpression} 'yield' => many?='*'? -> expression=AssignmentExpression<In,Yield=true>?;
	public YieldExpressionElements getYieldExpressionAccess() {
		return pYieldExpression;
	}
	
	public ParserRule getYieldExpressionRule() {
		return getYieldExpressionAccess().getRule();
	}
	
	//AssignmentOperator AssignmentOperator:
	//	'=' | '*=' | '/=' | '%=' | '+=' | '-='
	//	| '<<='
	//	| '>' '>' '>'? '='
	//	| '&=' | '^=' | '|=';
	public AssignmentOperatorElements getAssignmentOperatorAccess() {
		return pAssignmentOperator;
	}
	
	public ParserRule getAssignmentOperatorRule() {
		return getAssignmentOperatorAccess().getRule();
	}
	
	///*
	// * await should mimic precedence of 'yield' in [ECM15] (because it will be transpiled into a 'yield')
	// */ AwaitExpression <In, Yield Expression:
	//	=> ({AwaitExpression} 'await') expression=AssignmentExpression<In,Yield>;
	public AwaitExpressionElements getAwaitExpressionAccess() {
		return pAwaitExpression;
	}
	
	public ParserRule getAwaitExpressionRule() {
		return getAwaitExpressionAccess().getRule();
	}
	
	//PromisifyExpression <In, Yield Expression:
	//	=> ({PromisifyExpression} '@' 'Promisify') expression=AssignmentExpression<In,Yield>;
	public PromisifyExpressionElements getPromisifyExpressionAccess() {
		return pPromisifyExpression;
	}
	
	public ParserRule getPromisifyExpressionRule() {
		return getPromisifyExpressionAccess().getRule();
	}
	
	//// $<Comma operator (11.14)
	//Expression <In, Yield>:
	//	AssignmentExpression<In,Yield> ({CommaExpression.exprs+=current} ',' exprs+=AssignmentExpression<In,Yield> (','
	//	exprs+=AssignmentExpression<In,Yield>)*)?;
	public ExpressionElements getExpressionAccess() {
		return pExpression;
	}
	
	public ParserRule getExpressionRule() {
		return getExpressionAccess().getRule();
	}
	
	//TemplateLiteral <Yield>:
	//	{TemplateLiteral} (segments+=NoSubstitutionTemplate
	//	| segments+=TemplateHead segments+=Expression<In=true,Yield>? TemplateExpressionEnd (segments+=TemplateMiddle
	//	segments+=Expression<In=true,Yield>? TemplateExpressionEnd)*
	//	segments+=TemplateTail);
	public TemplateLiteralElements getTemplateLiteralAccess() {
		return pTemplateLiteral;
	}
	
	public ParserRule getTemplateLiteralRule() {
		return getTemplateLiteralAccess().getRule();
	}
	
	//TemplateExpressionEnd:
	//	'}';
	public TemplateExpressionEndElements getTemplateExpressionEndAccess() {
		return pTemplateExpressionEnd;
	}
	
	public ParserRule getTemplateExpressionEndRule() {
		return getTemplateExpressionEndAccess().getRule();
	}
	
	//NoSubstitutionTemplate TemplateSegment:
	//	{TemplateSegment} value=NO_SUBSTITUTION_TEMPLATE_LITERAL;
	public NoSubstitutionTemplateElements getNoSubstitutionTemplateAccess() {
		return pNoSubstitutionTemplate;
	}
	
	public ParserRule getNoSubstitutionTemplateRule() {
		return getNoSubstitutionTemplateAccess().getRule();
	}
	
	//TemplateHead TemplateSegment:
	//	{TemplateSegment} value=TEMPLATE_HEAD;
	public TemplateHeadElements getTemplateHeadAccess() {
		return pTemplateHead;
	}
	
	public ParserRule getTemplateHeadRule() {
		return getTemplateHeadAccess().getRule();
	}
	
	//TemplateTail TemplateSegment:
	//	{TemplateSegment} value=TemplateTailLiteral;
	public TemplateTailElements getTemplateTailAccess() {
		return pTemplateTail;
	}
	
	public ParserRule getTemplateTailRule() {
		return getTemplateTailAccess().getRule();
	}
	
	//TemplateMiddle TemplateSegment:
	//	{TemplateSegment} value=TemplateMiddleLiteral;
	public TemplateMiddleElements getTemplateMiddleAccess() {
		return pTemplateMiddle;
	}
	
	public ParserRule getTemplateMiddleRule() {
		return getTemplateMiddleAccess().getRule();
	}
	
	//// ****************************************************************************************************
	//// [ECM11] A.1 Lexical Grammar (p. 211)
	//// note: 'undefined' is not a literal, but a property of the built-in global object
	//// ****************************************************************************************************
	//Literal:
	//	NumericLiteral | BooleanLiteral | StringLiteral | NullLiteral | RegularExpressionLiteral;
	public LiteralElements getLiteralAccess() {
		return pLiteral;
	}
	
	public ParserRule getLiteralRule() {
		return getLiteralAccess().getRule();
	}
	
	//NullLiteral:
	//	{NullLiteral} 'null';
	public NullLiteralElements getNullLiteralAccess() {
		return pNullLiteral;
	}
	
	public ParserRule getNullLiteralRule() {
		return getNullLiteralAccess().getRule();
	}
	
	//BooleanLiteral:
	//	{BooleanLiteral} (true?='true' | 'false');
	public BooleanLiteralElements getBooleanLiteralAccess() {
		return pBooleanLiteral;
	}
	
	public ParserRule getBooleanLiteralRule() {
		return getBooleanLiteralAccess().getRule();
	}
	
	//StringLiteral:
	//	value=STRING;
	public StringLiteralElements getStringLiteralAccess() {
		return pStringLiteral;
	}
	
	public ParserRule getStringLiteralRule() {
		return getStringLiteralAccess().getRule();
	}
	
	//NumericLiteral:
	//	DoubleLiteral | IntLiteral | BinaryIntLiteral | OctalIntLiteral | LegacyOctalIntLiteral | HexIntLiteral |
	//	ScientificIntLiteral;
	public NumericLiteralElements getNumericLiteralAccess() {
		return pNumericLiteral;
	}
	
	public ParserRule getNumericLiteralRule() {
		return getNumericLiteralAccess().getRule();
	}
	
	//DoubleLiteral:
	//	value=DOUBLE;
	public DoubleLiteralElements getDoubleLiteralAccess() {
		return pDoubleLiteral;
	}
	
	public ParserRule getDoubleLiteralRule() {
		return getDoubleLiteralAccess().getRule();
	}
	
	//IntLiteral:
	//	value=INT;
	public IntLiteralElements getIntLiteralAccess() {
		return pIntLiteral;
	}
	
	public ParserRule getIntLiteralRule() {
		return getIntLiteralAccess().getRule();
	}
	
	//OctalIntLiteral:
	//	value=OCTAL_INT;
	public OctalIntLiteralElements getOctalIntLiteralAccess() {
		return pOctalIntLiteral;
	}
	
	public ParserRule getOctalIntLiteralRule() {
		return getOctalIntLiteralAccess().getRule();
	}
	
	//LegacyOctalIntLiteral:
	//	value=LEGACY_OCTAL_INT;
	public LegacyOctalIntLiteralElements getLegacyOctalIntLiteralAccess() {
		return pLegacyOctalIntLiteral;
	}
	
	public ParserRule getLegacyOctalIntLiteralRule() {
		return getLegacyOctalIntLiteralAccess().getRule();
	}
	
	//HexIntLiteral:
	//	value=HEX_INT;
	public HexIntLiteralElements getHexIntLiteralAccess() {
		return pHexIntLiteral;
	}
	
	public ParserRule getHexIntLiteralRule() {
		return getHexIntLiteralAccess().getRule();
	}
	
	//BinaryIntLiteral:
	//	value=BINARY_INT;
	public BinaryIntLiteralElements getBinaryIntLiteralAccess() {
		return pBinaryIntLiteral;
	}
	
	public ParserRule getBinaryIntLiteralRule() {
		return getBinaryIntLiteralAccess().getRule();
	}
	
	//ScientificIntLiteral:
	//	value=SCIENTIFIC_INT;
	public ScientificIntLiteralElements getScientificIntLiteralAccess() {
		return pScientificIntLiteral;
	}
	
	public ParserRule getScientificIntLiteralRule() {
		return getScientificIntLiteralAccess().getRule();
	}
	
	//RegularExpressionLiteral:
	//	value=REGEX_LITERAL;
	public RegularExpressionLiteralElements getRegularExpressionLiteralAccess() {
		return pRegularExpressionLiteral;
	}
	
	public ParserRule getRegularExpressionLiteralRule() {
		return getRegularExpressionLiteralAccess().getRule();
	}
	
	//NumericLiteralAsString:
	//	DOUBLE | INT | OCTAL_INT | HEX_INT | SCIENTIFIC_INT;
	public NumericLiteralAsStringElements getNumericLiteralAsStringAccess() {
		return pNumericLiteralAsString;
	}
	
	public ParserRule getNumericLiteralAsStringRule() {
		return getNumericLiteralAsStringAccess().getRule();
	}
	
	//IdentifierOrThis:
	//	IDENTIFIER
	//	| 'This'
	//	| 'Promisify'
	//	| 'target';
	public IdentifierOrThisElements getIdentifierOrThisAccess() {
		return pIdentifierOrThis;
	}
	
	public ParserRule getIdentifierOrThisRule() {
		return getIdentifierOrThisAccess().getRule();
	}
	
	//AnnotationName:
	//	IDENTIFIER
	//	| 'This'
	//	| 'target';
	public AnnotationNameElements getAnnotationNameAccess() {
		return pAnnotationName;
	}
	
	public ParserRule getAnnotationNameRule() {
		return getAnnotationNameAccess().getRule();
	}
	
	//terminal DOUBLE returns ecore::EBigDecimal:
	//	'.' DECIMAL_DIGIT_FRAGMENT+ EXPONENT_PART?
	//	| DECIMAL_INTEGER_LITERAL_FRAGMENT '.' DECIMAL_DIGIT_FRAGMENT* EXPONENT_PART?;
	public TerminalRule getDOUBLERule() {
		return tDOUBLE;
	}
	
	//terminal HEX_INT returns ecore::EBigDecimal:
	//	'0' ('x' | 'X') INT_SUFFIX;
	public TerminalRule getHEX_INTRule() {
		return tHEX_INT;
	}
	
	//terminal BINARY_INT returns ecore::EBigDecimal:
	//	'0' ('b' | 'B') INT_SUFFIX;
	public TerminalRule getBINARY_INTRule() {
		return tBINARY_INT;
	}
	
	//terminal OCTAL_INT returns ecore::EBigDecimal:
	//	'0' ('o' | 'O') INT_SUFFIX;
	public TerminalRule getOCTAL_INTRule() {
		return tOCTAL_INT;
	}
	
	//terminal LEGACY_OCTAL_INT returns ecore::EBigDecimal:
	//	'0' DECIMAL_DIGIT_FRAGMENT INT_SUFFIX;
	public TerminalRule getLEGACY_OCTAL_INTRule() {
		return tLEGACY_OCTAL_INT;
	}
	
	//terminal fragment INT_SUFFIX:
	//	IDENTIFIER_PART*;
	public TerminalRule getINT_SUFFIXRule() {
		return tINT_SUFFIX;
	}
	
	//terminal SCIENTIFIC_INT returns ecore::EBigDecimal:
	//	DECIMAL_INTEGER_LITERAL_FRAGMENT EXPONENT_PART;
	public TerminalRule getSCIENTIFIC_INTRule() {
		return tSCIENTIFIC_INT;
	}
	
	//terminal fragment EXPONENT_PART:
	//	('e' | 'E') SIGNED_INT
	//	| IDENTIFIER;
	public TerminalRule getEXPONENT_PARTRule() {
		return tEXPONENT_PART;
	}
	
	//terminal fragment SIGNED_INT:
	//	('+' | '-') DECIMAL_DIGIT_FRAGMENT+ IDENTIFIER?;
	public TerminalRule getSIGNED_INTRule() {
		return tSIGNED_INT;
	}
	
	//terminal STRING:
	//	'"' DOUBLE_STRING_CHAR* '"'?
	//	| "'" SINGLE_STRING_CHAR* "'"?;
	public TerminalRule getSTRINGRule() {
		return tSTRING;
	}
	
	//terminal fragment DOUBLE_STRING_CHAR:
	//	!(LINE_TERMINATOR_FRAGMENT | '"' | '\\') | '\\' (LINE_TERMINATOR_SEQUENCE_FRAGMENT | !LINE_TERMINATOR_FRAGMENT)?;
	public TerminalRule getDOUBLE_STRING_CHARRule() {
		return tDOUBLE_STRING_CHAR;
	}
	
	//terminal fragment SINGLE_STRING_CHAR:
	//	!(LINE_TERMINATOR_FRAGMENT | "'" | '\\') | '\\' (LINE_TERMINATOR_SEQUENCE_FRAGMENT | !LINE_TERMINATOR_FRAGMENT)?;
	public TerminalRule getSINGLE_STRING_CHARRule() {
		return tSINGLE_STRING_CHAR;
	}
	
	//terminal fragment BACKSLASH_SEQUENCE:
	//	'\\' !LINE_TERMINATOR_FRAGMENT?;
	public TerminalRule getBACKSLASH_SEQUENCERule() {
		return tBACKSLASH_SEQUENCE;
	}
	
	//terminal fragment REGEX_CHAR:
	//	!(LINE_TERMINATOR_FRAGMENT | '\\' | '/' | '[') | BACKSLASH_SEQUENCE
	//	| '[' REGEX_CHAR_OR_BRACKET* ']'?;
	public TerminalRule getREGEX_CHARRule() {
		return tREGEX_CHAR;
	}
	
	//terminal fragment REGEX_CHAR_OR_BRACKET:
	//	!(LINE_TERMINATOR_FRAGMENT | '\\' | ']') | BACKSLASH_SEQUENCE;
	public TerminalRule getREGEX_CHAR_OR_BRACKETRule() {
		return tREGEX_CHAR_OR_BRACKET;
	}
	
	///**
	// * The regex literal is not very strict in the sense that the trailing parts are optional.
	// * This is to improve the error recovery in the generated lexer and parser. If the trailing slash
	// * was mandatory, the lexer would brick and the parser would not sync properly. Therefore
	// * we rely on value converters and validation to check the regex literals.
	// */ REGEX_LITERAL:
	//	('/' | '/=') REGEX_TAIL?;
	public REGEX_LITERALElements getREGEX_LITERALAccess() {
		return pREGEX_LITERAL;
	}
	
	public ParserRule getREGEX_LITERALRule() {
		return getREGEX_LITERALAccess().getRule();
	}
	
	//terminal fragment ACTUAL_REGEX_TAIL:
	//	REGEX_CHAR+ ('/' IDENTIFIER_PART*)?
	//	| '/' IDENTIFIER_PART* // matches regular expression literals like /=/ or /=/g
	//;
	public TerminalRule getACTUAL_REGEX_TAILRule() {
		return tACTUAL_REGEX_TAIL;
	}
	
	//terminal fragment REGEX_START:
	//	'/' | '/=';
	public TerminalRule getREGEX_STARTRule() {
		return tREGEX_START;
	}
	
	//terminal REGEX_TAIL:
	//	'//1' // never matched by lexer but required to have a terminal token
	//;
	public TerminalRule getREGEX_TAILRule() {
		return tREGEX_TAIL;
	}
	
	//terminal TEMPLATE_HEAD:
	//	"`" TEMPLATE_LITERAL_CHAR* '$'+ '{';
	public TerminalRule getTEMPLATE_HEADRule() {
		return tTEMPLATE_HEAD;
	}
	
	//terminal NO_SUBSTITUTION_TEMPLATE_LITERAL:
	//	'`' TEMPLATE_LITERAL_CHAR* '$'* "`"?;
	public TerminalRule getNO_SUBSTITUTION_TEMPLATE_LITERALRule() {
		return tNO_SUBSTITUTION_TEMPLATE_LITERAL;
	}
	
	//terminal fragment ACTUAL_TEMPLATE_END:
	//	TEMPLATE_LITERAL_CHAR* ('$'+ ('{' | '`'?) | '`'?);
	public TerminalRule getACTUAL_TEMPLATE_ENDRule() {
		return tACTUAL_TEMPLATE_END;
	}
	
	//terminal fragment TEMPLATE_LITERAL_CHAR:
	//	!(LINE_TERMINATOR_FRAGMENT | '`' | '\\' | '$') | '$'+ !('{' | '`' | '$') | LINE_TERMINATOR_SEQUENCE_FRAGMENT
	//	| '\\' (LINE_TERMINATOR_SEQUENCE_FRAGMENT | !LINE_TERMINATOR_FRAGMENT)?;
	public TerminalRule getTEMPLATE_LITERAL_CHARRule() {
		return tTEMPLATE_LITERAL_CHAR;
	}
	
	//TemplateTailLiteral:
	//	TEMPLATE_END?;
	public TemplateTailLiteralElements getTemplateTailLiteralAccess() {
		return pTemplateTailLiteral;
	}
	
	public ParserRule getTemplateTailLiteralRule() {
		return getTemplateTailLiteralAccess().getRule();
	}
	
	//TemplateMiddleLiteral:
	//	TEMPLATE_MIDDLE;
	public TemplateMiddleLiteralElements getTemplateMiddleLiteralAccess() {
		return pTemplateMiddleLiteral;
	}
	
	public ParserRule getTemplateMiddleLiteralRule() {
		return getTemplateMiddleLiteralAccess().getRule();
	}
	
	//terminal TEMPLATE_MIDDLE:
	//	'//2' // will never be lexed
	//;
	public TerminalRule getTEMPLATE_MIDDLERule() {
		return tTEMPLATE_MIDDLE;
	}
	
	//terminal TEMPLATE_END:
	//	'//3' // will never be lexed
	//;
	public TerminalRule getTEMPLATE_ENDRule() {
		return tTEMPLATE_END;
	}
	
	//terminal fragment TEMPLATE_CONTINUATION:
	//	'//4' // actually '}'
	//;
	public TerminalRule getTEMPLATE_CONTINUATIONRule() {
		return tTEMPLATE_CONTINUATION;
	}
	
	//// ****************************************************************************************************
	//// Helpers
	//// ****************************************************************************************************
	///**
	// * Placeholder, will be replaced by manually written ANTLR rule.
	// * This rule handles semicolons reported by the lexer and situations where the ECMA 3 specification states there should be semicolons automatically inserted.
	// * The auto semicolons are not actually inserted but this rule behaves as if they were.
	// */ Semi:
	//	';';
	public SemiElements getSemiAccess() {
		return pSemi;
	}
	
	public ParserRule getSemiRule() {
		return getSemiAccess().getRule();
	}
	
	///**
	// * Will be completely replaced during post processing, need some dummy token to be able to define rule.
	// */ fragment NoLineTerminator *:
	//	NO_LINE_TERMINATOR?;
	public NoLineTerminatorElements getNoLineTerminatorAccess() {
		return pNoLineTerminator;
	}
	
	public ParserRule getNoLineTerminatorRule() {
		return getNoLineTerminatorAccess().getRule();
	}
	
	//terminal NO_LINE_TERMINATOR:
	//	'//5' // will never be lexed
	//;
	public TerminalRule getNO_LINE_TERMINATORRule() {
		return tNO_LINE_TERMINATOR;
	}
	
	//// ****************************************************************************************************
	//// N4JS Specific
	//// ****************************************************************************************************
	//// ****************************************************************************************************
	//// Annotations
	//// ****************************************************************************************************
	//// cf. N4JSSpec §9
	//Annotation:
	//	'@' AnnotationNoAtSign;
	public AnnotationElements getAnnotationAccess() {
		return pAnnotation;
	}
	
	public ParserRule getAnnotationRule() {
		return getAnnotationAccess().getRule();
	}
	
	//ScriptAnnotation Annotation:
	//	'@@' AnnotationNoAtSign;
	public ScriptAnnotationElements getScriptAnnotationAccess() {
		return pScriptAnnotation;
	}
	
	public ParserRule getScriptAnnotationRule() {
		return getScriptAnnotationAccess().getRule();
	}
	
	//AnnotationNoAtSign Annotation:
	//	name=AnnotationName (=> '(' (args+=AnnotationArgument (',' args+=AnnotationArgument)*)? ')')?;
	public AnnotationNoAtSignElements getAnnotationNoAtSignAccess() {
		return pAnnotationNoAtSign;
	}
	
	public ParserRule getAnnotationNoAtSignRule() {
		return getAnnotationNoAtSignAccess().getRule();
	}
	
	//AnnotationArgument:
	//	LiteralAnnotationArgument | TypeRefAnnotationArgument;
	public AnnotationArgumentElements getAnnotationArgumentAccess() {
		return pAnnotationArgument;
	}
	
	public ParserRule getAnnotationArgumentRule() {
		return getAnnotationArgumentAccess().getRule();
	}
	
	//LiteralAnnotationArgument:
	//	literal=Literal;
	public LiteralAnnotationArgumentElements getLiteralAnnotationArgumentAccess() {
		return pLiteralAnnotationArgument;
	}
	
	public ParserRule getLiteralAnnotationArgumentRule() {
		return getLiteralAnnotationArgumentAccess().getRule();
	}
	
	//TypeRefAnnotationArgument:
	//	typeRef=TypeRef;
	public TypeRefAnnotationArgumentElements getTypeRefAnnotationArgumentAccess() {
		return pTypeRefAnnotationArgument;
	}
	
	public ParserRule getTypeRefAnnotationArgumentRule() {
		return getTypeRefAnnotationArgumentAccess().getRule();
	}
	
	//AnnotationList:
	//	=> ({AnnotationList} '@' -> annotations+=AnnotationNoAtSign) annotations+=Annotation*;
	public AnnotationListElements getAnnotationListAccess() {
		return pAnnotationList;
	}
	
	public ParserRule getAnnotationListRule() {
		return getAnnotationListAccess().getRule();
	}
	
	//ExpressionAnnotationList:
	//	{ExpressionAnnotationList} annotations+=Annotation+;
	public ExpressionAnnotationListElements getExpressionAnnotationListAccess() {
		return pExpressionAnnotationList;
	}
	
	public ParserRule getExpressionAnnotationListRule() {
		return getExpressionAnnotationListAccess().getRule();
	}
	
	//PropertyAssignmentAnnotationList:
	//	{PropertyAssignmentAnnotationList} annotations+=Annotation+;
	public PropertyAssignmentAnnotationListElements getPropertyAssignmentAnnotationListAccess() {
		return pPropertyAssignmentAnnotationList;
	}
	
	public ParserRule getPropertyAssignmentAnnotationListRule() {
		return getPropertyAssignmentAnnotationListAccess().getRule();
	}
	
	//N4MemberAnnotationList:
	//	{N4MemberAnnotationList} annotations+=Annotation+;
	public N4MemberAnnotationListElements getN4MemberAnnotationListAccess() {
		return pN4MemberAnnotationList;
	}
	
	public ParserRule getN4MemberAnnotationListRule() {
		return getN4MemberAnnotationListAccess().getRule();
	}
	
	//@Override
	//fragment TypeReference *:
	//	(astNamespace=[types::ModuleNamespaceVirtualType|TypeReferenceName] '.')?
	//	declaredType=[types::Type|TypeReferenceName];
	public TypeReferenceElements getTypeReferenceAccess() {
		return pTypeReference;
	}
	
	public ParserRule getTypeReferenceRule() {
		return getTypeReferenceAccess().getRule();
	}
	
	//@Override
	//TypeReferenceName:
	//	'void' | 'This' | 'await' | 'Promisify' | 'target' | 'default' | IDENTIFIER;
	public TypeReferenceNameElements getTypeReferenceNameAccess() {
		return pTypeReferenceName;
	}
	
	public ParserRule getTypeReferenceNameRule() {
		return getTypeReferenceNameAccess().getRule();
	}
	
	//// ****************************************************************************************************
	//// New Expressions, Statements, and other Features
	//// ****************************************************************************************************
	//// cf. N4JSSpec §2.2.1 -- const statements are handled by means of variable statement modifiers
	//// ****************************************************************************************************
	//// New Meta Types
	//// ****************************************************************************************************
	//// cf. N4JSSpec §14
	//N4ClassDeclaration <Yield>:
	//	=> (declaredModifiers+=N4Modifier*
	//	'class' typingStrategy=TypingStrategyDefSiteOperator?
	//	name=BindingIdentifier<Yield>?
	//	VersionDeclaration?) TypeVariables?
	//	ClassExtendsImplements<Yield>?
	//	Members<Yield>;
	public N4ClassDeclarationElements getN4ClassDeclarationAccess() {
		return pN4ClassDeclaration;
	}
	
	public ParserRule getN4ClassDeclarationRule() {
		return getN4ClassDeclarationAccess().getRule();
	}
	
	//fragment Members <Yield> *:
	//	'{'
	//	ownedMembersRaw+=N4MemberDeclaration<Yield>*
	//	'}';
	public MembersElements getMembersAccess() {
		return pMembers;
	}
	
	public ParserRule getMembersRule() {
		return getMembersAccess().getRule();
	}
	
	//// we allow incorrect order of 'extends' and 'implements'; a validation will ensure the correct order
	//fragment ClassExtendsImplements <Yield> *:
	//	ClassExtendsClause<Yield> ClassImplementsList? | ClassImplementsList ClassExtendsClause<Yield>?;
	public ClassExtendsImplementsElements getClassExtendsImplementsAccess() {
		return pClassExtendsImplements;
	}
	
	public ParserRule getClassExtendsImplementsRule() {
		return getClassExtendsImplementsAccess().getRule();
	}
	
	//fragment ClassExtendsClause <Yield> *:
	//	'extends' (=> superClassRef=ParameterizedTypeRefNominal
	//	| superClassExpression=LeftHandSideExpression<Yield>);
	public ClassExtendsClauseElements getClassExtendsClauseAccess() {
		return pClassExtendsClause;
	}
	
	public ParserRule getClassExtendsClauseRule() {
		return getClassExtendsClauseAccess().getRule();
	}
	
	//fragment ClassImplementsList *:
	//	'implements' implementedInterfaceRefs+=ParameterizedTypeRefNominal (','
	//	implementedInterfaceRefs+=ParameterizedTypeRefNominal)*;
	public ClassImplementsListElements getClassImplementsListAccess() {
		return pClassImplementsList;
	}
	
	public ParserRule getClassImplementsListRule() {
		return getClassImplementsListAccess().getRule();
	}
	
	//N4ClassExpression <Yield>:
	//	{N4ClassExpression}
	//	'class' name=BindingIdentifier<Yield>?
	//	ClassExtendsImplements<Yield>?
	//	Members<Yield>;
	public N4ClassExpressionElements getN4ClassExpressionAccess() {
		return pN4ClassExpression;
	}
	
	public ParserRule getN4ClassExpressionRule() {
		return getN4ClassExpressionAccess().getRule();
	}
	
	//// cf. N4JSSpec §16
	//N4InterfaceDeclaration <Yield>:
	//	=> (declaredModifiers+=N4Modifier*
	//	'interface' typingStrategy=TypingStrategyDefSiteOperator?
	//	name=BindingIdentifier<Yield>?
	//	VersionDeclaration?) TypeVariables?
	//	InterfaceExtendsList?
	//	Members<Yield>;
	public N4InterfaceDeclarationElements getN4InterfaceDeclarationAccess() {
		return pN4InterfaceDeclaration;
	}
	
	public ParserRule getN4InterfaceDeclarationRule() {
		return getN4InterfaceDeclarationAccess().getRule();
	}
	
	//// we allow both 'extends' and 'implements' here, a validation will ensure 'extends' is used
	//fragment InterfaceExtendsList *:
	//	('extends' | 'implements') superInterfaceRefs+=ParameterizedTypeRefNominal (','
	//	superInterfaceRefs+=ParameterizedTypeRefNominal)*;
	public InterfaceExtendsListElements getInterfaceExtendsListAccess() {
		return pInterfaceExtendsList;
	}
	
	public ParserRule getInterfaceExtendsListRule() {
		return getInterfaceExtendsListAccess().getRule();
	}
	
	//// cf. N4JSSpec §13
	//N4EnumDeclaration <Yield>:
	//	=> ({N4EnumDeclaration} declaredModifiers+=N4Modifier*
	//	'enum' name=BindingIdentifier<Yield>?
	//	VersionDeclaration?)
	//	'{' (literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*)?
	//	'}';
	public N4EnumDeclarationElements getN4EnumDeclarationAccess() {
		return pN4EnumDeclaration;
	}
	
	public ParserRule getN4EnumDeclarationRule() {
		return getN4EnumDeclarationAccess().getRule();
	}
	
	///*
	// * Only upper case literals are allows, this is to be checked by the validator
	// */ N4EnumLiteral:
	//	name=IdentifierName (':' value=STRING)?;
	public N4EnumLiteralElements getN4EnumLiteralAccess() {
		return pN4EnumLiteral;
	}
	
	public ParserRule getN4EnumLiteralRule() {
		return getN4EnumLiteralAccess().getRule();
	}
	
	//enum N4Modifier:
	//	private | project | protected | public | external | abstract | static | const;
	public N4ModifierElements getN4ModifierAccess() {
		return eN4Modifier;
	}
	
	public EnumRule getN4ModifierRule() {
		return getN4ModifierAccess().getRule();
	}
	
	//// TODO jvp: order matters, seems odd. What about object literal getters and setter?
	//// TODO sz: what about it?
	//N4MemberDeclaration <Yield>:
	//	AnnotatedN4MemberDeclaration<Yield> | N4GetterDeclaration<Yield> | N4SetterDeclaration<Yield> |
	//	N4MethodDeclaration<Yield> | N4FieldDeclaration<Yield> | N4CallableConstructorDeclaration<Yield>;
	public N4MemberDeclarationElements getN4MemberDeclarationAccess() {
		return pN4MemberDeclaration;
	}
	
	public ParserRule getN4MemberDeclarationRule() {
		return getN4MemberDeclarationAccess().getRule();
	}
	
	///**
	// * Left factored, annotated member declarations.
	// *
	// * Pretty much inlined versions of getter, setter, method and field declarations with leading annotations.
	// *
	// * The GrammarLinter ensures that the inlined content mirrors the content of the real declarations.
	// */ AnnotatedN4MemberDeclaration <Yield N4MemberDeclaration:
	//	N4MemberAnnotationList (=> ({N4GetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
	//	GetterHeader<Yield>) body=Block<Yield>? ';'?
	//	| => ({N4SetterDeclaration.annotationList=current} declaredModifiers+=N4Modifier* 'set' ->
	//	declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'? '(' fpar=FormalParameter<Yield> ')'
	//	body=Block<Yield>? ';'?
	//	| => ({N4MethodDeclaration.annotationList=current} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment?
	//	(generator?='*' declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
	//	AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
	//	<Generator=false>)) ';'?
	//	| {N4FieldDeclaration.annotationList=current} FieldDeclarationImpl<Yield>);
	public AnnotatedN4MemberDeclarationElements getAnnotatedN4MemberDeclarationAccess() {
		return pAnnotatedN4MemberDeclaration;
	}
	
	public ParserRule getAnnotatedN4MemberDeclarationRule() {
		return getAnnotatedN4MemberDeclarationAccess().getRule();
	}
	
	//fragment FieldDeclarationImpl <Yield> *:
	//	declaredModifiers+=N4Modifier* BogusTypeRefFragment?
	//	declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'?
	//	ColonSepDeclaredTypeRef? ('=' expression=Expression<In=true,Yield>)?
	//	Semi;
	public FieldDeclarationImplElements getFieldDeclarationImplAccess() {
		return pFieldDeclarationImpl;
	}
	
	public ParserRule getFieldDeclarationImplRule() {
		return getFieldDeclarationImplAccess().getRule();
	}
	
	//N4FieldDeclaration <Yield>:
	//	{N4FieldDeclaration} FieldDeclarationImpl<Yield>;
	public N4FieldDeclarationElements getN4FieldDeclarationAccess() {
		return pN4FieldDeclaration;
	}
	
	public ParserRule getN4FieldDeclarationRule() {
		return getN4FieldDeclarationAccess().getRule();
	}
	
	//N4MethodDeclaration <Yield>:
	//	=> ({N4MethodDeclaration} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment? (generator?='*'
	//	declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
	//	AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
	//	<Generator=false>)) ';'?;
	public N4MethodDeclarationElements getN4MethodDeclarationAccess() {
		return pN4MethodDeclaration;
	}
	
	public ParserRule getN4MethodDeclarationRule() {
		return getN4MethodDeclarationAccess().getRule();
	}
	
	//N4CallableConstructorDeclaration <Yield N4MethodDeclaration:
	//	MethodParamsReturnAndBody<Generator=false> ';'?;
	public N4CallableConstructorDeclarationElements getN4CallableConstructorDeclarationAccess() {
		return pN4CallableConstructorDeclaration;
	}
	
	public ParserRule getN4CallableConstructorDeclarationRule() {
		return getN4CallableConstructorDeclarationAccess().getRule();
	}
	
	//fragment MethodParamsAndBody <Generator> *:
	//	StrictFormalParameters<Yield=Generator> body=Block<Yield=Generator>?;
	public MethodParamsAndBodyElements getMethodParamsAndBodyAccess() {
		return pMethodParamsAndBody;
	}
	
	public ParserRule getMethodParamsAndBodyRule() {
		return getMethodParamsAndBodyAccess().getRule();
	}
	
	//fragment MethodParamsReturnAndBody <Generator> *:
	//	StrictFormalParameters<Yield=Generator> ColonSepReturnTypeRef?
	//	body=Block<Yield=Generator>?;
	public MethodParamsReturnAndBodyElements getMethodParamsReturnAndBodyAccess() {
		return pMethodParamsReturnAndBody;
	}
	
	public ParserRule getMethodParamsReturnAndBodyRule() {
		return getMethodParamsReturnAndBodyAccess().getRule();
	}
	
	///*
	// * 'get' and 'set' are no reserved words, see BindingIdentifier.
	// */ N4GetterDeclaration <Yield>:
	//	=> ({N4GetterDeclaration} declaredModifiers+=N4Modifier*
	//	GetterHeader<Yield>) body=Block<Yield>? ';'?;
	public N4GetterDeclarationElements getN4GetterDeclarationAccess() {
		return pN4GetterDeclaration;
	}
	
	public ParserRule getN4GetterDeclarationRule() {
		return getN4GetterDeclarationAccess().getRule();
	}
	
	//fragment GetterHeader <Yield> *:
	//	BogusTypeRefFragment? 'get' -> declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'? '(' ')'
	//	ColonSepDeclaredTypeRef?;
	public GetterHeaderElements getGetterHeaderAccess() {
		return pGetterHeader;
	}
	
	public ParserRule getGetterHeaderRule() {
		return getGetterHeaderAccess().getRule();
	}
	
	//N4SetterDeclaration <Yield>:
	//	=> ({N4SetterDeclaration} declaredModifiers+=N4Modifier*
	//	'set'
	//	-> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'?
	//	'(' fpar=FormalParameter<Yield> ')' body=Block<Yield>? ';'?;
	public N4SetterDeclarationElements getN4SetterDeclarationAccess() {
		return pN4SetterDeclaration;
	}
	
	public ParserRule getN4SetterDeclarationRule() {
		return getN4SetterDeclarationAccess().getRule();
	}
	
	//BindingPattern <Yield>:
	//	ObjectBindingPattern<Yield> | ArrayBindingPattern<Yield>;
	public BindingPatternElements getBindingPatternAccess() {
		return pBindingPattern;
	}
	
	public ParserRule getBindingPatternRule() {
		return getBindingPatternAccess().getRule();
	}
	
	//ObjectBindingPattern <Yield>:
	//	{ObjectBindingPattern}
	//	'{' (properties+=BindingProperty<Yield,AllowType=false> (',' properties+=BindingProperty<Yield,AllowType=false>)*)?
	//	'}';
	public ObjectBindingPatternElements getObjectBindingPatternAccess() {
		return pObjectBindingPattern;
	}
	
	public ParserRule getObjectBindingPatternRule() {
		return getObjectBindingPatternAccess().getRule();
	}
	
	//ArrayBindingPattern <Yield>:
	//	{ArrayBindingPattern}
	//	'['
	//	elements+=Elision* (elements+=BindingRestElement<Yield> (',' elements+=Elision* elements+=BindingRestElement<Yield>)*
	//	(',' elements+=Elision*)?)?
	//	']';
	public ArrayBindingPatternElements getArrayBindingPatternAccess() {
		return pArrayBindingPattern;
	}
	
	public ParserRule getArrayBindingPatternRule() {
		return getArrayBindingPatternAccess().getRule();
	}
	
	///*
	// * In case of object destruction, no colon separated type can be declared in case of single name binding since this would
	// * be ambiguous (e.g., {prop: newVar} vs.  {propAndVarName: TypeForVar}.
	// * However it is possible with a preceding LiteralBindingPropertyName, as in this case we simply have three
	// * segment, e.g. { prop: newVar: TypeOfNewVar }.
	// */ BindingProperty <Yield, AllowType>:
	//	=> (declaredName=LiteralOrComputedPropertyName<Yield> ':') value=BindingElement<Yield> |
	//	value=SingleNameBinding<Yield,AllowType>;
	public BindingPropertyElements getBindingPropertyAccess() {
		return pBindingProperty;
	}
	
	public ParserRule getBindingPropertyRule() {
		return getBindingPropertyAccess().getRule();
	}
	
	//SingleNameBinding <Yield, AllowType BindingElement:
	//	varDecl=VariableDeclaration<In=true,Yield,AllowType>;
	public SingleNameBindingElements getSingleNameBindingAccess() {
		return pSingleNameBinding;
	}
	
	public ParserRule getSingleNameBindingRule() {
		return getSingleNameBindingAccess().getRule();
	}
	
	//BindingElement <Yield>:
	//	BindingElementImpl<Yield>;
	public BindingElementElements getBindingElementAccess() {
		return pBindingElement;
	}
	
	public ParserRule getBindingElementRule() {
		return getBindingElementAccess().getRule();
	}
	
	//BindingRestElement <Yield BindingElement:
	//	rest?='...'?
	//	BindingElementImpl<Yield>;
	public BindingRestElementElements getBindingRestElementAccess() {
		return pBindingRestElement;
	}
	
	public ParserRule getBindingRestElementRule() {
		return getBindingRestElementAccess().getRule();
	}
	
	//fragment BindingElementImpl <Yield> returns BindingElement:
	//	=> (nestedPattern=BindingPattern<Yield>) ('=' expression=AssignmentExpression<In=true,Yield>)?
	//	| varDecl=VariableDeclaration<In=true,Yield,AllowType=true>;
	public BindingElementImplElements getBindingElementImplAccess() {
		return pBindingElementImpl;
	}
	
	public ParserRule getBindingElementImplRule() {
		return getBindingElementImplAccess().getRule();
	}
	
	//Elision BindingElement:
	//	{BindingElement} ',';
	public ElisionElements getElisionAccess() {
		return pElision;
	}
	
	public ParserRule getElisionRule() {
		return getElisionAccess().getRule();
	}
	
	//LiteralOrComputedPropertyName <Yield>:
	//	literalName=IdentifierName
	//	| literalName=STRING
	//	| literalName=NumericLiteralAsString
	//	| '[' expression=AssignmentExpression<In=true,Yield> ']';
	public LiteralOrComputedPropertyNameElements getLiteralOrComputedPropertyNameAccess() {
		return pLiteralOrComputedPropertyName;
	}
	
	public ParserRule getLiteralOrComputedPropertyNameRule() {
		return getLiteralOrComputedPropertyNameAccess().getRule();
	}
	
	//terminal INCOMPLETE_ASYNC_ARROW:
	//	'@=';
	public TerminalRule getINCOMPLETE_ASYNC_ARROWRule() {
		return tINCOMPLETE_ASYNC_ARROW;
	}
	
	//// ****************************************************************************************************
	//// see https://facebook.github.io/jsx/
	//// ****************************************************************************************************
	//JSXElement:
	//	'<' jsxElementName=JSXElementName JSXAttributes ('>' jsxChildren+=JSXChild* '<' '/' jsxClosingName=JSXElementName '>'
	//	| '/' '>');
	public JSXElementElements getJSXElementAccess() {
		return pJSXElement;
	}
	
	public ParserRule getJSXElementRule() {
		return getJSXElementAccess().getRule();
	}
	
	//JSXFragment:
	//	{JSXFragment} '<' '>' jsxChildren+=JSXChild* '<' '/' '>';
	public JSXFragmentElements getJSXFragmentAccess() {
		return pJSXFragment;
	}
	
	public ParserRule getJSXFragmentRule() {
		return getJSXFragmentAccess().getRule();
	}
	
	//JSXChild:
	//	JSXElement
	//	| JSXFragment
	//	| JSXExpression
	//	//	| JSXText -- not supported yet, cf. IDE-2414
	//;
	public JSXChildElements getJSXChildAccess() {
		return pJSXChild;
	}
	
	public ParserRule getJSXChildRule() {
		return getJSXChildAccess().getRule();
	}
	
	//// terminal JSX_TEXT: !('{'|'<'|'>'|'}'); needs to be refactored similar to template text, cf. IDE-2414
	//JSXExpression:
	//	'{' expression=AssignmentExpression<false,false> '}';
	public JSXExpressionElements getJSXExpressionAccess() {
		return pJSXExpression;
	}
	
	public ParserRule getJSXExpressionRule() {
		return getJSXExpressionAccess().getRule();
	}
	
	//JSXElementName:
	//	expression=JSXElementNameExpression;
	public JSXElementNameElements getJSXElementNameAccess() {
		return pJSXElementName;
	}
	
	public ParserRule getJSXElementNameRule() {
		return getJSXElementNameAccess().getRule();
	}
	
	//JSXElementNameExpression Expression:
	//	IdentifierRef<false> ({ParameterizedPropertyAccessExpression.target=current}
	//	ParameterizedPropertyAccessExpressionTail<false>)*
	//	//	| JSXNamedspacedName not supported
	//;
	public JSXElementNameExpressionElements getJSXElementNameExpressionAccess() {
		return pJSXElementNameExpression;
	}
	
	public ParserRule getJSXElementNameExpressionRule() {
		return getJSXElementNameExpressionAccess().getRule();
	}
	
	////JSXNamedspacedName: JSXIdentifier ':' JSXIdentifier -- not supported in N4JSX
	////JSXMemberExpression: JSXIdentifier '.' JSXIdentifier -- defined by means of ParameterizedPropertyAccessExpression
	//fragment JSXAttributes *:
	//	jsxAttributes+=JSXAttribute*;
	public JSXAttributesElements getJSXAttributesAccess() {
		return pJSXAttributes;
	}
	
	public ParserRule getJSXAttributesRule() {
		return getJSXAttributesAccess().getRule();
	}
	
	//JSXAttribute:
	//	JSXSpreadAttribute
	//	| JSXPropertyAttribute;
	public JSXAttributeElements getJSXAttributeAccess() {
		return pJSXAttribute;
	}
	
	public ParserRule getJSXAttributeRule() {
		return getJSXAttributeAccess().getRule();
	}
	
	//JSXSpreadAttribute:
	//	'{' '...' expression=AssignmentExpression<In=false,Yield=false> '}';
	public JSXSpreadAttributeElements getJSXSpreadAttributeAccess() {
		return pJSXSpreadAttribute;
	}
	
	public ParserRule getJSXSpreadAttributeRule() {
		return getJSXSpreadAttributeAccess().getRule();
	}
	
	//JSXPropertyAttribute:
	//	property=[types::IdentifiableElement|IdentifierName] ('=' (jsxAttributeValue=StringLiteral | '{'
	//	jsxAttributeValue=AssignmentExpression<In=false,Yield=false> '}'))?;
	public JSXPropertyAttributeElements getJSXPropertyAttributeAccess() {
		return pJSXPropertyAttribute;
	}
	
	public ParserRule getJSXPropertyAttributeRule() {
		return getJSXPropertyAttributeAccess().getRule();
	}
	
	///* Version (N4IDL) related rules */ fragment VersionDeclaration *:
	//	declaredVersion=VERSION;
	public VersionDeclarationElements getVersionDeclarationAccess() {
		return pVersionDeclaration;
	}
	
	public ParserRule getVersionDeclarationRule() {
		return getVersionDeclarationAccess().getRule();
	}
	
	//// ****************************************************************************************************
	//// N4JS versions of type references and expressions, also used by Types.xtext
	////
	//// References:
	////
	//// [ECM15]	ECMAScript 2015 Language Specification / ISO/IEC (ECMA-262, 6th Edition).
	////			International Standard.
	////			http://www.ecma-international.org/publications/ files/ECMA-ST/Ecma-262.pdf
	////
	//// ****************************************************************************************************
	///*
	// * cf. N4JSSec §4
	// * Depending on where the type references is used, not all possible variants are allowed.
	// * This is however checked by the validator in order to
	// * 1) provide better error messages
	// * 2) simplify grammar
	// *
	// * Constraints:
	// * UnionElementTypeRef: no AnyType, no Void, union itself must not be dynamic
	// * FParTypeRef: no Void
	// * ReturnTypeRef: everything, but no dynamic
	// * // in N4JS:
	// * VarTypeRef: no Void, i.e.
	// * AttributeTypeRef: no Void
	// */ TypeRef:
	//	IntersectionTypeExpression ({UnionTypeExpression.typeRefs+=current} ("|" typeRefs+=IntersectionTypeExpression)+)?;
	public TypeExpressionsGrammarAccess.TypeRefElements getTypeRefAccess() {
		return gaTypeExpressions.getTypeRefAccess();
	}
	
	public ParserRule getTypeRefRule() {
		return getTypeRefAccess().getRule();
	}
	
	//IntersectionTypeExpression TypeRef:
	//	ArrayTypeExpression ({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=ArrayTypeExpression)+)?;
	public TypeExpressionsGrammarAccess.IntersectionTypeExpressionElements getIntersectionTypeExpressionAccess() {
		return gaTypeExpressions.getIntersectionTypeExpressionAccess();
	}
	
	public ParserRule getIntersectionTypeExpressionRule() {
		return getIntersectionTypeExpressionAccess().getRule();
	}
	
	//ArrayTypeExpression TypeRef:
	//	{ParameterizedTypeRef} typeArgs+=WildcardOldNotationWithoutBound arrayTypeExpression?='[' ']' =>
	//	({ParameterizedTypeRef.typeArgs+=current} arrayTypeExpression?='[' ']')* | {ParameterizedTypeRef} '('
	//	typeArgs+=Wildcard ')' arrayTypeExpression?='[' ']' => ({ParameterizedTypeRef.typeArgs+=current}
	//	arrayTypeExpression?='[' ']')* | PrimaryTypeExpression => ({ParameterizedTypeRef.typeArgs+=current}
	//	arrayTypeExpression?='[' ']')*;
	public TypeExpressionsGrammarAccess.ArrayTypeExpressionElements getArrayTypeExpressionAccess() {
		return gaTypeExpressions.getArrayTypeExpressionAccess();
	}
	
	public ParserRule getArrayTypeExpressionRule() {
		return getArrayTypeExpressionAccess().getRule();
	}
	
	//PrimaryTypeExpression TypeRef:
	//	ArrowFunctionTypeExpression
	//	| IterableTypeExpression
	//	| TypeRefWithModifiers
	//	| "(" TypeRef ")";
	public TypeExpressionsGrammarAccess.PrimaryTypeExpressionElements getPrimaryTypeExpressionAccess() {
		return gaTypeExpressions.getPrimaryTypeExpressionAccess();
	}
	
	public ParserRule getPrimaryTypeExpressionRule() {
		return getPrimaryTypeExpressionAccess().getRule();
	}
	
	//TypeRefWithModifiers StaticBaseTypeRef:
	//	TypeRefWithoutModifiers => followedByQuestionMark?='?'?;
	public TypeExpressionsGrammarAccess.TypeRefWithModifiersElements getTypeRefWithModifiersAccess() {
		return gaTypeExpressions.getTypeRefWithModifiersAccess();
	}
	
	public ParserRule getTypeRefWithModifiersRule() {
		return getTypeRefWithModifiersAccess().getRule();
	}
	
	//TypeRefWithoutModifiers StaticBaseTypeRef:
	//	(ParameterizedTypeRef | ThisTypeRef) => dynamic?='+'? | TypeTypeRef
	//	| FunctionTypeExpressionOLD
	//	| UnionTypeExpressionOLD
	//	| IntersectionTypeExpressionOLD;
	public TypeExpressionsGrammarAccess.TypeRefWithoutModifiersElements getTypeRefWithoutModifiersAccess() {
		return gaTypeExpressions.getTypeRefWithoutModifiersAccess();
	}
	
	public ParserRule getTypeRefWithoutModifiersRule() {
		return getTypeRefWithoutModifiersAccess().getRule();
	}
	
	//TypeRefFunctionTypeExpression StaticBaseTypeRef:
	//	ParameterizedTypeRef
	//	| IterableTypeExpression
	//	| TypeTypeRef
	//	| UnionTypeExpressionOLD
	//	| IntersectionTypeExpressionOLD;
	public TypeExpressionsGrammarAccess.TypeRefFunctionTypeExpressionElements getTypeRefFunctionTypeExpressionAccess() {
		return gaTypeExpressions.getTypeRefFunctionTypeExpressionAccess();
	}
	
	public ParserRule getTypeRefFunctionTypeExpressionRule() {
		return getTypeRefFunctionTypeExpressionAccess().getRule();
	}
	
	//TypeArgInTypeTypeRef TypeArgument:
	//	ParameterizedTypeRefNominal
	//	| ThisTypeRefNominal
	//	| WildcardOldNotation;
	public TypeExpressionsGrammarAccess.TypeArgInTypeTypeRefElements getTypeArgInTypeTypeRefAccess() {
		return gaTypeExpressions.getTypeArgInTypeTypeRefAccess();
	}
	
	public ParserRule getTypeArgInTypeTypeRefRule() {
		return getTypeArgInTypeTypeRefAccess().getRule();
	}
	
	//ThisTypeRef:
	//	ThisTypeRefNominal | ThisTypeRefStructural;
	public TypeExpressionsGrammarAccess.ThisTypeRefElements getThisTypeRefAccess() {
		return gaTypeExpressions.getThisTypeRefAccess();
	}
	
	public ParserRule getThisTypeRefRule() {
		return getThisTypeRefAccess().getRule();
	}
	
	//ThisTypeRefNominal:
	//	{ThisTypeRefNominal} 'this';
	public TypeExpressionsGrammarAccess.ThisTypeRefNominalElements getThisTypeRefNominalAccess() {
		return gaTypeExpressions.getThisTypeRefNominalAccess();
	}
	
	public ParserRule getThisTypeRefNominalRule() {
		return getThisTypeRefNominalAccess().getRule();
	}
	
	//ThisTypeRefStructural:
	//	definedTypingStrategy=TypingStrategyUseSiteOperator
	//	'this' ('with' TStructMemberList)?;
	public TypeExpressionsGrammarAccess.ThisTypeRefStructuralElements getThisTypeRefStructuralAccess() {
		return gaTypeExpressions.getThisTypeRefStructuralAccess();
	}
	
	public ParserRule getThisTypeRefStructuralRule() {
		return getThisTypeRefStructuralAccess().getRule();
	}
	
	//FunctionTypeExpressionOLD FunctionTypeExpression:
	//	{FunctionTypeExpression}
	//	'{' ('@' 'This' '(' declaredThisType=TypeRefFunctionTypeExpression ')')?
	//	'function' ('<' ownedTypeVars+=super::TypeVariable (',' ownedTypeVars+=super::TypeVariable)* '>')?
	//	'(' TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?
	//	'}';
	public TypeExpressionsGrammarAccess.FunctionTypeExpressionOLDElements getFunctionTypeExpressionOLDAccess() {
		return gaTypeExpressions.getFunctionTypeExpressionOLDAccess();
	}
	
	public ParserRule getFunctionTypeExpressionOLDRule() {
		return getFunctionTypeExpressionOLDAccess().getRule();
	}
	
	//ArrowFunctionTypeExpression FunctionTypeExpression:
	//	=> ({FunctionTypeExpression} '(' TAnonymousFormalParameterList ')' '=>') returnTypeRef=PrimaryTypeExpression;
	public TypeExpressionsGrammarAccess.ArrowFunctionTypeExpressionElements getArrowFunctionTypeExpressionAccess() {
		return gaTypeExpressions.getArrowFunctionTypeExpressionAccess();
	}
	
	public ParserRule getArrowFunctionTypeExpressionRule() {
		return getArrowFunctionTypeExpressionAccess().getRule();
	}
	
	//// TODO extract FormalParameterContainer and use returns FormalParameterContainer instead of wildcard
	//fragment TAnonymousFormalParameterList *:
	//	(fpars+=TAnonymousFormalParameter (',' fpars+=TAnonymousFormalParameter)*)?;
	public TypeExpressionsGrammarAccess.TAnonymousFormalParameterListElements getTAnonymousFormalParameterListAccess() {
		return gaTypeExpressions.getTAnonymousFormalParameterListAccess();
	}
	
	public ParserRule getTAnonymousFormalParameterListRule() {
		return getTAnonymousFormalParameterListAccess().getRule();
	}
	
	///**
	// * Used in type expressions, name is optional.
	// */ TAnonymousFormalParameter:
	//	variadic?='...'? (=> (name=BindingIdentifier<Yield=false> -> ColonSepTypeRef) | typeRef=TypeRef)
	//	DefaultFormalParameter;
	public TypeExpressionsGrammarAccess.TAnonymousFormalParameterElements getTAnonymousFormalParameterAccess() {
		return gaTypeExpressions.getTAnonymousFormalParameterAccess();
	}
	
	public ParserRule getTAnonymousFormalParameterRule() {
		return getTAnonymousFormalParameterAccess().getRule();
	}
	
	///**
	// * Used in Types language only.
	// */ TFormalParameter:
	//	variadic?='...'? name=BindingIdentifier<Yield=false> ColonSepTypeRef
	//	DefaultFormalParameter;
	public TypeExpressionsGrammarAccess.TFormalParameterElements getTFormalParameterAccess() {
		return gaTypeExpressions.getTFormalParameterAccess();
	}
	
	public ParserRule getTFormalParameterRule() {
		return getTFormalParameterAccess().getRule();
	}
	
	///**
	// * Default initializers in FunctionTypeExpressions or TFunctions
	// * are necessary to specify optional formal parameters. Hence, their
	// * initializer expression is rather uninteresting and limited by validations
	// * to 'undefined'. The shorthand form, that is omitting the initializer, is supported.
	// */ fragment DefaultFormalParameter *:
	//	(hasInitializerAssignment?='=' astInitializer=super::TypeReferenceName?)?;
	public TypeExpressionsGrammarAccess.DefaultFormalParameterElements getDefaultFormalParameterAccess() {
		return gaTypeExpressions.getDefaultFormalParameterAccess();
	}
	
	public ParserRule getDefaultFormalParameterRule() {
		return getDefaultFormalParameterAccess().getRule();
	}
	
	//UnionTypeExpressionOLD UnionTypeExpression:
	//	{UnionTypeExpression}
	//	'union' '{' typeRefs+=TypeRef (',' typeRefs+=TypeRef)* '}';
	public TypeExpressionsGrammarAccess.UnionTypeExpressionOLDElements getUnionTypeExpressionOLDAccess() {
		return gaTypeExpressions.getUnionTypeExpressionOLDAccess();
	}
	
	public ParserRule getUnionTypeExpressionOLDRule() {
		return getUnionTypeExpressionOLDAccess().getRule();
	}
	
	//IntersectionTypeExpressionOLD IntersectionTypeExpression:
	//	{IntersectionTypeExpression}
	//	'intersection' '{' typeRefs+=TypeRef (',' typeRefs+=TypeRef)* '}';
	public TypeExpressionsGrammarAccess.IntersectionTypeExpressionOLDElements getIntersectionTypeExpressionOLDAccess() {
		return gaTypeExpressions.getIntersectionTypeExpressionOLDAccess();
	}
	
	public ParserRule getIntersectionTypeExpressionOLDRule() {
		return getIntersectionTypeExpressionOLDAccess().getRule();
	}
	
	//ParameterizedTypeRef:
	//	ParameterizedTypeRefNominal | ParameterizedTypeRefStructural;
	public TypeExpressionsGrammarAccess.ParameterizedTypeRefElements getParameterizedTypeRefAccess() {
		return gaTypeExpressions.getParameterizedTypeRefAccess();
	}
	
	public ParserRule getParameterizedTypeRefRule() {
		return getParameterizedTypeRefAccess().getRule();
	}
	
	//ParameterizedTypeRefNominal ParameterizedTypeRef:
	//	(super::TypeReference
	//	| {VersionedParameterizedTypeRef} super::TypeReference VersionRequest) -> TypeArguments?;
	public TypeExpressionsGrammarAccess.ParameterizedTypeRefNominalElements getParameterizedTypeRefNominalAccess() {
		return gaTypeExpressions.getParameterizedTypeRefNominalAccess();
	}
	
	public ParserRule getParameterizedTypeRefNominalRule() {
		return getParameterizedTypeRefNominalAccess().getRule();
	}
	
	//ParameterizedTypeRefStructural:
	//	(definedTypingStrategy=TypingStrategyUseSiteOperator super::TypeReference
	//	| {VersionedParameterizedTypeRefStructural} definedTypingStrategy=TypingStrategyUseSiteOperator super::TypeReference
	//	VersionRequest) -> TypeArguments? ('with' TStructMemberList)?;
	public TypeExpressionsGrammarAccess.ParameterizedTypeRefStructuralElements getParameterizedTypeRefStructuralAccess() {
		return gaTypeExpressions.getParameterizedTypeRefStructuralAccess();
	}
	
	public ParserRule getParameterizedTypeRefStructuralRule() {
		return getParameterizedTypeRefStructuralAccess().getRule();
	}
	
	//IterableTypeExpression ParameterizedTypeRef:
	//	iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail
	//	| typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* ']');
	public TypeExpressionsGrammarAccess.IterableTypeExpressionElements getIterableTypeExpressionAccess() {
		return gaTypeExpressions.getIterableTypeExpressionAccess();
	}
	
	public ParserRule getIterableTypeExpressionRule() {
		return getIterableTypeExpressionAccess().getRule();
	}
	
	//EmptyIterableTypeExpressionTail Wildcard:
	//	{Wildcard} ']';
	public TypeExpressionsGrammarAccess.EmptyIterableTypeExpressionTailElements getEmptyIterableTypeExpressionTailAccess() {
		return gaTypeExpressions.getEmptyIterableTypeExpressionTailAccess();
	}
	
	public ParserRule getEmptyIterableTypeExpressionTailRule() {
		return getEmptyIterableTypeExpressionTailAccess().getRule();
	}
	
	//fragment VersionRequest *:
	//	requestedVersion=VERSION;
	public TypeExpressionsGrammarAccess.VersionRequestElements getVersionRequestAccess() {
		return gaTypeExpressions.getVersionRequestAccess();
	}
	
	public ParserRule getVersionRequestRule() {
		return getVersionRequestAccess().getRule();
	}
	
	//fragment TypeArguments *:
	//	'<' typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* '>';
	public TypeExpressionsGrammarAccess.TypeArgumentsElements getTypeArgumentsAccess() {
		return gaTypeExpressions.getTypeArgumentsAccess();
	}
	
	public ParserRule getTypeArgumentsRule() {
		return getTypeArgumentsAccess().getRule();
	}
	
	//fragment TStructMemberList *:
	//	'{' (astStructuralMembers+=TStructMember (';' | ',')?)* '}';
	public TypeExpressionsGrammarAccess.TStructMemberListElements getTStructMemberListAccess() {
		return gaTypeExpressions.getTStructMemberListAccess();
	}
	
	public ParserRule getTStructMemberListRule() {
		return getTStructMemberListAccess().getRule();
	}
	
	///**
	// * All TMembers here are only used in ParameterizedTypeRefStructural references
	// * Most type references are optional. However, in the types language (n4ts), these
	// * references are NOT optional.
	// */ TStructMember:
	//	TStructGetter
	//	| TStructSetter
	//	| TStructMethod
	//	| TStructField;
	public TypeExpressionsGrammarAccess.TStructMemberElements getTStructMemberAccess() {
		return gaTypeExpressions.getTStructMemberAccess();
	}
	
	public ParserRule getTStructMemberRule() {
		return getTStructMemberAccess().getRule();
	}
	
	//TStructMethod:
	//	=>
	//	({TStructMethod} TypeVariables?
	//	name=IdentifierName '(') TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?;
	public TypeExpressionsGrammarAccess.TStructMethodElements getTStructMethodAccess() {
		return gaTypeExpressions.getTStructMethodAccess();
	}
	
	public ParserRule getTStructMethodRule() {
		return getTStructMethodAccess().getRule();
	}
	
	//// TODO extract TypeVariableContainer to be used here
	//fragment TypeVariables *:
	//	'<' typeVars+=super::TypeVariable (',' typeVars+=super::TypeVariable)* '>';
	public TypeExpressionsGrammarAccess.TypeVariablesElements getTypeVariablesAccess() {
		return gaTypeExpressions.getTypeVariablesAccess();
	}
	
	public ParserRule getTypeVariablesRule() {
		return getTypeVariablesAccess().getRule();
	}
	
	//fragment ColonSepDeclaredTypeRef *:
	//	':' declaredTypeRef=TypeRef;
	public TypeExpressionsGrammarAccess.ColonSepDeclaredTypeRefElements getColonSepDeclaredTypeRefAccess() {
		return gaTypeExpressions.getColonSepDeclaredTypeRefAccess();
	}
	
	public ParserRule getColonSepDeclaredTypeRefRule() {
		return getColonSepDeclaredTypeRefAccess().getRule();
	}
	
	//fragment ColonSepTypeRef *:
	//	':' typeRef=TypeRef;
	public TypeExpressionsGrammarAccess.ColonSepTypeRefElements getColonSepTypeRefAccess() {
		return gaTypeExpressions.getColonSepTypeRefAccess();
	}
	
	public ParserRule getColonSepTypeRefRule() {
		return getColonSepTypeRefAccess().getRule();
	}
	
	//fragment ColonSepReturnTypeRef *:
	//	':' returnTypeRef=TypeRef;
	public TypeExpressionsGrammarAccess.ColonSepReturnTypeRefElements getColonSepReturnTypeRefAccess() {
		return gaTypeExpressions.getColonSepReturnTypeRefAccess();
	}
	
	public ParserRule getColonSepReturnTypeRefRule() {
		return getColonSepReturnTypeRefAccess().getRule();
	}
	
	//TStructField:
	//	name=IdentifierName optional?='?'? ColonSepTypeRef?;
	public TypeExpressionsGrammarAccess.TStructFieldElements getTStructFieldAccess() {
		return gaTypeExpressions.getTStructFieldAccess();
	}
	
	public ParserRule getTStructFieldRule() {
		return getTStructFieldAccess().getRule();
	}
	
	//TStructGetter:
	//	=> ({TStructGetter}
	//	'get'
	//	name=IdentifierName) optional?='?'?
	//	'(' ')' ColonSepDeclaredTypeRef?;
	public TypeExpressionsGrammarAccess.TStructGetterElements getTStructGetterAccess() {
		return gaTypeExpressions.getTStructGetterAccess();
	}
	
	public ParserRule getTStructGetterRule() {
		return getTStructGetterAccess().getRule();
	}
	
	//TStructSetter:
	//	=> ({TStructSetter}
	//	'set'
	//	name=IdentifierName) optional?='?'?
	//	'(' fpar=TAnonymousFormalParameter ')';
	public TypeExpressionsGrammarAccess.TStructSetterElements getTStructSetterAccess() {
		return gaTypeExpressions.getTStructSetterAccess();
	}
	
	public ParserRule getTStructSetterRule() {
		return getTStructSetterAccess().getRule();
	}
	
	//TypingStrategyUseSiteOperator TypingStrategy:
	//	'~' ('~' | STRUCTMODSUFFIX)?;
	public TypeExpressionsGrammarAccess.TypingStrategyUseSiteOperatorElements getTypingStrategyUseSiteOperatorAccess() {
		return gaTypeExpressions.getTypingStrategyUseSiteOperatorAccess();
	}
	
	public ParserRule getTypingStrategyUseSiteOperatorRule() {
		return getTypingStrategyUseSiteOperatorAccess().getRule();
	}
	
	//TypingStrategyDefSiteOperator TypingStrategy:
	//	'~';
	public TypeExpressionsGrammarAccess.TypingStrategyDefSiteOperatorElements getTypingStrategyDefSiteOperatorAccess() {
		return gaTypeExpressions.getTypingStrategyDefSiteOperatorAccess();
	}
	
	public ParserRule getTypingStrategyDefSiteOperatorRule() {
		return getTypingStrategyDefSiteOperatorAccess().getRule();
	}
	
	//terminal STRUCTMODSUFFIX:
	//	('r' | 'i' | 'w' | '\\u2205') '~';
	public TerminalRule getSTRUCTMODSUFFIXRule() {
		return gaTypeExpressions.getSTRUCTMODSUFFIXRule();
	}
	
	//TypeTypeRef:
	//	{TypeTypeRef} ('type' | constructorRef?='constructor')
	//	'{' typeArg=TypeArgInTypeTypeRef '}';
	public TypeExpressionsGrammarAccess.TypeTypeRefElements getTypeTypeRefAccess() {
		return gaTypeExpressions.getTypeTypeRefAccess();
	}
	
	public ParserRule getTypeTypeRefRule() {
		return getTypeTypeRefAccess().getRule();
	}
	
	//TypeArgument:
	//	Wildcard | TypeRef;
	public TypeExpressionsGrammarAccess.TypeArgumentElements getTypeArgumentAccess() {
		return gaTypeExpressions.getTypeArgumentAccess();
	}
	
	public ParserRule getTypeArgumentRule() {
		return getTypeArgumentAccess().getRule();
	}
	
	//Wildcard:
	//	WildcardOldNotation
	//	| WildcardNewNotation;
	public TypeExpressionsGrammarAccess.WildcardElements getWildcardAccess() {
		return gaTypeExpressions.getWildcardAccess();
	}
	
	public ParserRule getWildcardRule() {
		return getWildcardAccess().getRule();
	}
	
	//WildcardOldNotation Wildcard:
	//	=> ({Wildcard} '?') ('extends' declaredUpperBound=TypeRef | 'super'
	//	declaredLowerBound=TypeRef)?;
	public TypeExpressionsGrammarAccess.WildcardOldNotationElements getWildcardOldNotationAccess() {
		return gaTypeExpressions.getWildcardOldNotationAccess();
	}
	
	public ParserRule getWildcardOldNotationRule() {
		return getWildcardOldNotationAccess().getRule();
	}
	
	//WildcardOldNotationWithoutBound Wildcard:
	//	{Wildcard} '?';
	public TypeExpressionsGrammarAccess.WildcardOldNotationWithoutBoundElements getWildcardOldNotationWithoutBoundAccess() {
		return gaTypeExpressions.getWildcardOldNotationWithoutBoundAccess();
	}
	
	public ParserRule getWildcardOldNotationWithoutBoundRule() {
		return getWildcardOldNotationWithoutBoundAccess().getRule();
	}
	
	//WildcardNewNotation Wildcard:
	//	usingInOutNotation?='out' declaredUpperBound=TypeRef | usingInOutNotation?='in' declaredLowerBound=TypeRef;
	public TypeExpressionsGrammarAccess.WildcardNewNotationElements getWildcardNewNotationAccess() {
		return gaTypeExpressions.getWildcardNewNotationAccess();
	}
	
	public ParserRule getWildcardNewNotationRule() {
		return getWildcardNewNotationAccess().getRule();
	}
	
	///*
	// * [ECM11] (7.6, pp. 17)
	// * Identifier :: IdentifierName but not ReservedWord
	// * ReservedWord :: Keyword | FutureReservedWord | NullLiteral | BooleanLiteral
	// */ BindingIdentifier <Yield>:
	//	IDENTIFIER
	//	// yield as identifier as of [ECM15] (11.6.2, pp. 165)
	//	| <!Yield> 'yield'
	//	| N4Keyword;
	public TypeExpressionsGrammarAccess.BindingIdentifierElements getBindingIdentifierAccess() {
		return gaTypeExpressions.getBindingIdentifierAccess();
	}
	
	public ParserRule getBindingIdentifierRule() {
		return getBindingIdentifierAccess().getRule();
	}
	
	//IdentifierName:
	//	IDENTIFIER | ReservedWord | N4Keyword;
	public TypeExpressionsGrammarAccess.IdentifierNameElements getIdentifierNameAccess() {
		return gaTypeExpressions.getIdentifierNameAccess();
	}
	
	public ParserRule getIdentifierNameRule() {
		return getIdentifierNameAccess().getRule();
	}
	
	//ReservedWord: // Keywords as of [ECM15] (11.6.2, pp. 165)
	//	'break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' |
	//	'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' |
	//	'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' // null literal
	//	| 'null' // boolean literal
	//	| 'true' | 'false' // Future Reserved Word as of [ECM15] (11.6.2.2, pp. 166)
	//	// | 'await' /* reserved word only if parse goal is module - compromise: allow as identifier and validate */
	//	| 'enum';
	public TypeExpressionsGrammarAccess.ReservedWordElements getReservedWordAccess() {
		return gaTypeExpressions.getReservedWordAccess();
	}
	
	public ParserRule getReservedWordRule() {
		return getReservedWordAccess().getRule();
	}
	
	//N4Keyword:
	//	'get' | 'set'
	//	| 'let'
	//	| 'project'
	//	| 'external' | 'abstract' | 'static'
	//	| 'as' | 'from' | 'constructor' | 'of' | 'target'
	//	| 'type' | 'union' | 'intersection'
	//	| 'This' | 'Promisify'
	//	// future reserved keyword in [ECM15] only in modules, we add additional validation
	//	| 'await'
	//	// async is not a reserved keyword, i.e. it can be used as a variable name
	//	| 'async'
	//	// future reserved keywords in [ECM15], restricted via static semantic in [ECM15]
	//	| 'implements' | 'interface'
	//	| 'private' | 'protected' | 'public' // package not used in N4JS
	//	// definition-site variance
	//	| 'out';
	public TypeExpressionsGrammarAccess.N4KeywordElements getN4KeywordAccess() {
		return gaTypeExpressions.getN4KeywordAccess();
	}
	
	public ParserRule getN4KeywordRule() {
		return getN4KeywordAccess().getRule();
	}
	
	//terminal IDENTIFIER:
	//	IDENTIFIER_START IDENTIFIER_PART*;
	public TerminalRule getIDENTIFIERRule() {
		return gaTypeExpressions.getIDENTIFIERRule();
	}
	
	//terminal INT returns ecore::EBigDecimal:
	//	DECIMAL_INTEGER_LITERAL_FRAGMENT;
	public TerminalRule getINTRule() {
		return gaTypeExpressions.getINTRule();
	}
	
	//terminal ML_COMMENT:
	//	ML_COMMENT_FRAGMENT;
	public TerminalRule getML_COMMENTRule() {
		return gaTypeExpressions.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !LINE_TERMINATOR_FRAGMENT*;
	public TerminalRule getSL_COMMENTRule() {
		return gaTypeExpressions.getSL_COMMENTRule();
	}
	
	//terminal EOL:
	//	LINE_TERMINATOR_SEQUENCE_FRAGMENT;
	public TerminalRule getEOLRule() {
		return gaTypeExpressions.getEOLRule();
	}
	
	//terminal WS:
	//	WHITESPACE_FRAGMENT+;
	public TerminalRule getWSRule() {
		return gaTypeExpressions.getWSRule();
	}
	
	//terminal fragment UNICODE_ESCAPE_FRAGMENT:
	//	'\\' ('u' (HEX_DIGIT (HEX_DIGIT (HEX_DIGIT HEX_DIGIT?)?)?
	//	| '{' HEX_DIGIT* '}'?)?)?;
	public TerminalRule getUNICODE_ESCAPE_FRAGMENTRule() {
		return gaTypeExpressions.getUNICODE_ESCAPE_FRAGMENTRule();
	}
	
	//terminal fragment IDENTIFIER_START:
	//	UNICODE_LETTER_FRAGMENT
	//	| '$'
	//	| '_'
	//	| UNICODE_ESCAPE_FRAGMENT;
	public TerminalRule getIDENTIFIER_STARTRule() {
		return gaTypeExpressions.getIDENTIFIER_STARTRule();
	}
	
	//terminal fragment IDENTIFIER_PART:
	//	UNICODE_LETTER_FRAGMENT
	//	| UNICODE_ESCAPE_FRAGMENT
	//	| '$'
	//	| UNICODE_COMBINING_MARK_FRAGMENT
	//	| UNICODE_DIGIT_FRAGMENT
	//	| UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT
	//	| ZWNJ
	//	| ZWJ;
	public TerminalRule getIDENTIFIER_PARTRule() {
		return gaTypeExpressions.getIDENTIFIER_PARTRule();
	}
	
	//terminal DOT_DOT:
	//	'..';
	public TerminalRule getDOT_DOTRule() {
		return gaTypeExpressions.getDOT_DOTRule();
	}
	
	//terminal VERSION returns ecore::EBigDecimal:
	//	'#' WS* INT;
	public TerminalRule getVERSIONRule() {
		return gaTypeExpressions.getVERSIONRule();
	}
	
	//terminal fragment HEX_DIGIT:
	//	DECIMAL_DIGIT_FRAGMENT | 'a'..'f' | 'A'..'F';
	public TerminalRule getHEX_DIGITRule() {
		return gaUnicode.getHEX_DIGITRule();
	}
	
	//terminal fragment DECIMAL_INTEGER_LITERAL_FRAGMENT:
	//	'0'
	//	| '1'..'9' DECIMAL_DIGIT_FRAGMENT*;
	public TerminalRule getDECIMAL_INTEGER_LITERAL_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_INTEGER_LITERAL_FRAGMENTRule();
	}
	
	//terminal fragment DECIMAL_DIGIT_FRAGMENT:
	//	'0'..'9';
	public TerminalRule getDECIMAL_DIGIT_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment ZWJ:
	//	'\\u200D';
	public TerminalRule getZWJRule() {
		return gaUnicode.getZWJRule();
	}
	
	//terminal fragment ZWNJ:
	//	'\\u200C';
	public TerminalRule getZWNJRule() {
		return gaUnicode.getZWNJRule();
	}
	
	//terminal fragment BOM:
	//	'\\uFEFF';
	public TerminalRule getBOMRule() {
		return gaUnicode.getBOMRule();
	}
	
	//terminal fragment WHITESPACE_FRAGMENT:
	//	'\\u0009' | '\\u000B' | '\\u000C' | '\\u0020' | '\\u00A0' | BOM | UNICODE_SPACE_SEPARATOR_FRAGMENT;
	public TerminalRule getWHITESPACE_FRAGMENTRule() {
		return gaUnicode.getWHITESPACE_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_FRAGMENT:
	//	'\\u000A' | '\\u000D' | '\\u2028' | '\\u2029';
	public TerminalRule getLINE_TERMINATOR_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_SEQUENCE_FRAGMENT:
	//	'\\u000A' | '\\u000D' '\\u000A'? | '\\u2028' | '\\u2029';
	public TerminalRule getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule();
	}
	
	//terminal fragment SL_COMMENT_FRAGMENT:
	//	'//' !LINE_TERMINATOR_FRAGMENT*;
	public TerminalRule getSL_COMMENT_FRAGMENTRule() {
		return gaUnicode.getSL_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment ML_COMMENT_FRAGMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENT_FRAGMENTRule() {
		return gaUnicode.getML_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_COMBINING_MARK_FRAGMENT: // any character in the Unicode categories
	//// ―Non-spacing mark (Mn)
	//// ―Combining spacing mark (Mc)
	//	'\\u0300'..'\\u036F' | '\\u0483'..'\\u0487' | '\\u0591'..'\\u05BD' | '\\u05BF' | '\\u05C1'..'\\u05C2' | '\\u05C4'..'\\u05C5' |
	//	'\\u05C7' | '\\u0610'..'\\u061A' | '\\u064B'..'\\u065F' | '\\u0670' | '\\u06D6'..'\\u06DC' | '\\u06DF'..'\\u06E4' |
	//	'\\u06E7'..'\\u06E8' | '\\u06EA'..'\\u06ED' | '\\u0711' | '\\u0730'..'\\u074A' | '\\u07A6'..'\\u07B0' | '\\u07EB'..'\\u07F3' |
	//	'\\u0816'..'\\u0819' | '\\u081B'..'\\u0823' | '\\u0825'..'\\u0827' | '\\u0829'..'\\u082D' | '\\u0859'..'\\u085B' |
	//	'\\u08E3'..'\\u0903' | '\\u093A'..'\\u093C' | '\\u093E'..'\\u094F' | '\\u0951'..'\\u0957' | '\\u0962'..'\\u0963' |
	//	'\\u0981'..'\\u0983' | '\\u09BC' | '\\u09BE'..'\\u09C4' | '\\u09C7'..'\\u09C8' | '\\u09CB'..'\\u09CD' | '\\u09D7' |
	//	'\\u09E2'..'\\u09E3' | '\\u0A01'..'\\u0A03' | '\\u0A3C' | '\\u0A3E'..'\\u0A42' | '\\u0A47'..'\\u0A48' | '\\u0A4B'..'\\u0A4D' |
	//	'\\u0A51' | '\\u0A70'..'\\u0A71' | '\\u0A75' | '\\u0A81'..'\\u0A83' | '\\u0ABC' | '\\u0ABE'..'\\u0AC5' | '\\u0AC7'..'\\u0AC9' |
	//	'\\u0ACB'..'\\u0ACD' | '\\u0AE2'..'\\u0AE3' | '\\u0B01'..'\\u0B03' | '\\u0B3C' | '\\u0B3E'..'\\u0B44' | '\\u0B47'..'\\u0B48' |
	//	'\\u0B4B'..'\\u0B4D' | '\\u0B56'..'\\u0B57' | '\\u0B62'..'\\u0B63' | '\\u0B82' | '\\u0BBE'..'\\u0BC2' | '\\u0BC6'..'\\u0BC8' |
	//	'\\u0BCA'..'\\u0BCD' | '\\u0BD7' | '\\u0C00'..'\\u0C03' | '\\u0C3E'..'\\u0C44' | '\\u0C46'..'\\u0C48' | '\\u0C4A'..'\\u0C4D' |
	//	'\\u0C55'..'\\u0C56' | '\\u0C62'..'\\u0C63' | '\\u0C81'..'\\u0C83' | '\\u0CBC' | '\\u0CBE'..'\\u0CC4' | '\\u0CC6'..'\\u0CC8' |
	//	'\\u0CCA'..'\\u0CCD' | '\\u0CD5'..'\\u0CD6' | '\\u0CE2'..'\\u0CE3' | '\\u0D01'..'\\u0D03' | '\\u0D3E'..'\\u0D44' |
	//	'\\u0D46'..'\\u0D48' | '\\u0D4A'..'\\u0D4D' | '\\u0D57' | '\\u0D62'..'\\u0D63' | '\\u0D82'..'\\u0D83' | '\\u0DCA' |
	//	'\\u0DCF'..'\\u0DD4' | '\\u0DD6' | '\\u0DD8'..'\\u0DDF' | '\\u0DF2'..'\\u0DF3' | '\\u0E31' | '\\u0E34'..'\\u0E3A' |
	//	'\\u0E47'..'\\u0E4E' | '\\u0EB1' | '\\u0EB4'..'\\u0EB9' | '\\u0EBB'..'\\u0EBC' | '\\u0EC8'..'\\u0ECD' | '\\u0F18'..'\\u0F19' |
	//	'\\u0F35' | '\\u0F37' | '\\u0F39' | '\\u0F3E'..'\\u0F3F' | '\\u0F71'..'\\u0F84' | '\\u0F86'..'\\u0F87' | '\\u0F8D'..'\\u0F97' |
	//	'\\u0F99'..'\\u0FBC' | '\\u0FC6' | '\\u102B'..'\\u103E' | '\\u1056'..'\\u1059' | '\\u105E'..'\\u1060' | '\\u1062'..'\\u1064' |
	//	'\\u1067'..'\\u106D' | '\\u1071'..'\\u1074' | '\\u1082'..'\\u108D' | '\\u108F' | '\\u109A'..'\\u109D' | '\\u135D'..'\\u135F' |
	//	'\\u1712'..'\\u1714' | '\\u1732'..'\\u1734' | '\\u1752'..'\\u1753' | '\\u1772'..'\\u1773' | '\\u17B4'..'\\u17D3' | '\\u17DD' |
	//	'\\u180B'..'\\u180D' | '\\u18A9' | '\\u1920'..'\\u192B' | '\\u1930'..'\\u193B' | '\\u1A17'..'\\u1A1B' | '\\u1A55'..'\\u1A5E' |
	//	'\\u1A60'..'\\u1A7C' | '\\u1A7F' | '\\u1AB0'..'\\u1ABD' | '\\u1B00'..'\\u1B04' | '\\u1B34'..'\\u1B44' | '\\u1B6B'..'\\u1B73' |
	//	'\\u1B80'..'\\u1B82' | '\\u1BA1'..'\\u1BAD' | '\\u1BE6'..'\\u1BF3' | '\\u1C24'..'\\u1C37' | '\\u1CD0'..'\\u1CD2' |
	//	'\\u1CD4'..'\\u1CE8' | '\\u1CED' | '\\u1CF2'..'\\u1CF4' | '\\u1CF8'..'\\u1CF9' | '\\u1DC0'..'\\u1DF5' | '\\u1DFC'..'\\u1DFF' |
	//	'\\u20D0'..'\\u20DC' | '\\u20E1' | '\\u20E5'..'\\u20F0' | '\\u2CEF'..'\\u2CF1' | '\\u2D7F' | '\\u2DE0'..'\\u2DFF' |
	//	'\\u302A'..'\\u302F' | '\\u3099'..'\\u309A' | '\\uA66F' | '\\uA674'..'\\uA67D' | '\\uA69E'..'\\uA69F' | '\\uA6F0'..'\\uA6F1' |
	//	'\\uA802' | '\\uA806' | '\\uA80B' | '\\uA823'..'\\uA827' | '\\uA880'..'\\uA881' | '\\uA8B4'..'\\uA8C4' | '\\uA8E0'..'\\uA8F1' |
	//	'\\uA926'..'\\uA92D' | '\\uA947'..'\\uA953' | '\\uA980'..'\\uA983' | '\\uA9B3'..'\\uA9C0' | '\\uA9E5' | '\\uAA29'..'\\uAA36' |
	//	'\\uAA43' | '\\uAA4C'..'\\uAA4D' | '\\uAA7B'..'\\uAA7D' | '\\uAAB0' | '\\uAAB2'..'\\uAAB4' | '\\uAAB7'..'\\uAAB8' |
	//	'\\uAABE'..'\\uAABF' | '\\uAAC1' | '\\uAAEB'..'\\uAAEF' | '\\uAAF5'..'\\uAAF6' | '\\uABE3'..'\\uABEA' | '\\uABEC'..'\\uABED' |
	//	'\\uFB1E' | '\\uFE00'..'\\uFE0F' | '\\uFE20'..'\\uFE2F';
	public TerminalRule getUNICODE_COMBINING_MARK_FRAGMENTRule() {
		return gaUnicode.getUNICODE_COMBINING_MARK_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_DIGIT_FRAGMENT: // any character in the Unicode categories
	//// ―Decimal number (Nd)
	//	'\\u0030'..'\\u0039' | '\\u0660'..'\\u0669' | '\\u06F0'..'\\u06F9' | '\\u07C0'..'\\u07C9' | '\\u0966'..'\\u096F' |
	//	'\\u09E6'..'\\u09EF' | '\\u0A66'..'\\u0A6F' | '\\u0AE6'..'\\u0AEF' | '\\u0B66'..'\\u0B6F' | '\\u0BE6'..'\\u0BEF' |
	//	'\\u0C66'..'\\u0C6F' | '\\u0CE6'..'\\u0CEF' | '\\u0D66'..'\\u0D6F' | '\\u0DE6'..'\\u0DEF' | '\\u0E50'..'\\u0E59' |
	//	'\\u0ED0'..'\\u0ED9' | '\\u0F20'..'\\u0F29' | '\\u1040'..'\\u1049' | '\\u1090'..'\\u1099' | '\\u17E0'..'\\u17E9' |
	//	'\\u1810'..'\\u1819' | '\\u1946'..'\\u194F' | '\\u19D0'..'\\u19D9' | '\\u1A80'..'\\u1A89' | '\\u1A90'..'\\u1A99' |
	//	'\\u1B50'..'\\u1B59' | '\\u1BB0'..'\\u1BB9' | '\\u1C40'..'\\u1C49' | '\\u1C50'..'\\u1C59' | '\\uA620'..'\\uA629' |
	//	'\\uA8D0'..'\\uA8D9' | '\\uA900'..'\\uA909' | '\\uA9D0'..'\\uA9D9' | '\\uA9F0'..'\\uA9F9' | '\\uAA50'..'\\uAA59' |
	//	'\\uABF0'..'\\uABF9' | '\\uFF10'..'\\uFF19';
	public TerminalRule getUNICODE_DIGIT_FRAGMENTRule() {
		return gaUnicode.getUNICODE_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT: // any character in the Unicode categories
	//// ―Connector punctuation (Pc)
	//	'\\u005F' | '\\u203F'..'\\u2040' | '\\u2054' | '\\uFE33'..'\\uFE34' | '\\uFE4D'..'\\uFE4F' | '\\uFF3F';
	public TerminalRule getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule() {
		return gaUnicode.getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_LETTER_FRAGMENT: // any character in the Unicode categories
	//// ―Uppercase letter (Lu)
	//// ―Lowercase letter (Ll)
	//// ―Titlecase letter (Lt)
	//// ―Modifier letter (Lm)
	//// ―Other letter (Lo)
	//// ―Letter number (Nl)
	//	'\\u0041'..'\\u005A' | '\\u0061'..'\\u007A' | '\\u00AA' | '\\u00B5' | '\\u00BA' | '\\u00C0'..'\\u00D6' | '\\u00D8'..'\\u00F6' |
	//	'\\u00F8'..'\\u02C1' | '\\u02C6'..'\\u02D1' | '\\u02E0'..'\\u02E4' | '\\u02EC' | '\\u02EE' | '\\u0370'..'\\u0374' |
	//	'\\u0376'..'\\u0377' | '\\u037A'..'\\u037D' | '\\u037F' | '\\u0386' | '\\u0388'..'\\u038A' | '\\u038C' | '\\u038E'..'\\u03A1' |
	//	'\\u03A3'..'\\u03F5' | '\\u03F7'..'\\u0481' | '\\u048A'..'\\u052F' | '\\u0531'..'\\u0556' | '\\u0559' | '\\u0561'..'\\u0587' |
	//	'\\u05D0'..'\\u05EA' | '\\u05F0'..'\\u05F2' | '\\u0620'..'\\u064A' | '\\u066E'..'\\u066F' | '\\u0671'..'\\u06D3' | '\\u06D5' |
	//	'\\u06E5'..'\\u06E6' | '\\u06EE'..'\\u06EF' | '\\u06FA'..'\\u06FC' | '\\u06FF' | '\\u0710' | '\\u0712'..'\\u072F' |
	//	'\\u074D'..'\\u07A5' | '\\u07B1' | '\\u07CA'..'\\u07EA' | '\\u07F4'..'\\u07F5' | '\\u07FA' | '\\u0800'..'\\u0815' | '\\u081A' |
	//	'\\u0824' | '\\u0828' | '\\u0840'..'\\u0858' | '\\u08A0'..'\\u08B4' | '\\u0904'..'\\u0939' | '\\u093D' | '\\u0950' |
	//	'\\u0958'..'\\u0961' | '\\u0971'..'\\u0980' | '\\u0985'..'\\u098C' | '\\u098F'..'\\u0990' | '\\u0993'..'\\u09A8' |
	//	'\\u09AA'..'\\u09B0' | '\\u09B2' | '\\u09B6'..'\\u09B9' | '\\u09BD' | '\\u09CE' | '\\u09DC'..'\\u09DD' | '\\u09DF'..'\\u09E1' |
	//	'\\u09F0'..'\\u09F1' | '\\u0A05'..'\\u0A0A' | '\\u0A0F'..'\\u0A10' | '\\u0A13'..'\\u0A28' | '\\u0A2A'..'\\u0A30' |
	//	'\\u0A32'..'\\u0A33' | '\\u0A35'..'\\u0A36' | '\\u0A38'..'\\u0A39' | '\\u0A59'..'\\u0A5C' | '\\u0A5E' | '\\u0A72'..'\\u0A74' |
	//	'\\u0A85'..'\\u0A8D' | '\\u0A8F'..'\\u0A91' | '\\u0A93'..'\\u0AA8' | '\\u0AAA'..'\\u0AB0' | '\\u0AB2'..'\\u0AB3' |
	//	'\\u0AB5'..'\\u0AB9' | '\\u0ABD' | '\\u0AD0' | '\\u0AE0'..'\\u0AE1' | '\\u0AF9' | '\\u0B05'..'\\u0B0C' | '\\u0B0F'..'\\u0B10' |
	//	'\\u0B13'..'\\u0B28' | '\\u0B2A'..'\\u0B30' | '\\u0B32'..'\\u0B33' | '\\u0B35'..'\\u0B39' | '\\u0B3D' | '\\u0B5C'..'\\u0B5D' |
	//	'\\u0B5F'..'\\u0B61' | '\\u0B71' | '\\u0B83' | '\\u0B85'..'\\u0B8A' | '\\u0B8E'..'\\u0B90' | '\\u0B92'..'\\u0B95' |
	//	'\\u0B99'..'\\u0B9A' | '\\u0B9C' | '\\u0B9E'..'\\u0B9F' | '\\u0BA3'..'\\u0BA4' | '\\u0BA8'..'\\u0BAA' | '\\u0BAE'..'\\u0BB9' |
	//	'\\u0BD0' | '\\u0C05'..'\\u0C0C' | '\\u0C0E'..'\\u0C10' | '\\u0C12'..'\\u0C28' | '\\u0C2A'..'\\u0C39' | '\\u0C3D' |
	//	'\\u0C58'..'\\u0C5A' | '\\u0C60'..'\\u0C61' | '\\u0C85'..'\\u0C8C' | '\\u0C8E'..'\\u0C90' | '\\u0C92'..'\\u0CA8' |
	//	'\\u0CAA'..'\\u0CB3' | '\\u0CB5'..'\\u0CB9' | '\\u0CBD' | '\\u0CDE' | '\\u0CE0'..'\\u0CE1' | '\\u0CF1'..'\\u0CF2' |
	//	'\\u0D05'..'\\u0D0C' | '\\u0D0E'..'\\u0D10' | '\\u0D12'..'\\u0D3A' | '\\u0D3D' | '\\u0D4E' | '\\u0D5F'..'\\u0D61' |
	//	'\\u0D7A'..'\\u0D7F' | '\\u0D85'..'\\u0D96' | '\\u0D9A'..'\\u0DB1' | '\\u0DB3'..'\\u0DBB' | '\\u0DBD' | '\\u0DC0'..'\\u0DC6' |
	//	'\\u0E01'..'\\u0E30' | '\\u0E32'..'\\u0E33' | '\\u0E40'..'\\u0E46' | '\\u0E81'..'\\u0E82' | '\\u0E84' | '\\u0E87'..'\\u0E88' |
	//	'\\u0E8A' | '\\u0E8D' | '\\u0E94'..'\\u0E97' | '\\u0E99'..'\\u0E9F' | '\\u0EA1'..'\\u0EA3' | '\\u0EA5' | '\\u0EA7' |
	//	'\\u0EAA'..'\\u0EAB' | '\\u0EAD'..'\\u0EB0' | '\\u0EB2'..'\\u0EB3' | '\\u0EBD' | '\\u0EC0'..'\\u0EC4' | '\\u0EC6' |
	//	'\\u0EDC'..'\\u0EDF' | '\\u0F00' | '\\u0F40'..'\\u0F47' | '\\u0F49'..'\\u0F6C' | '\\u0F88'..'\\u0F8C' | '\\u1000'..'\\u102A' |
	//	'\\u103F' | '\\u1050'..'\\u1055' | '\\u105A'..'\\u105D' | '\\u1061' | '\\u1065'..'\\u1066' | '\\u106E'..'\\u1070' |
	//	'\\u1075'..'\\u1081' | '\\u108E' | '\\u10A0'..'\\u10C5' | '\\u10C7' | '\\u10CD' | '\\u10D0'..'\\u10FA' | '\\u10FC'..'\\u1248' |
	//	'\\u124A'..'\\u124D' | '\\u1250'..'\\u1256' | '\\u1258' | '\\u125A'..'\\u125D' | '\\u1260'..'\\u1288' | '\\u128A'..'\\u128D' |
	//	'\\u1290'..'\\u12B0' | '\\u12B2'..'\\u12B5' | '\\u12B8'..'\\u12BE' | '\\u12C0' | '\\u12C2'..'\\u12C5' | '\\u12C8'..'\\u12D6' |
	//	'\\u12D8'..'\\u1310' | '\\u1312'..'\\u1315' | '\\u1318'..'\\u135A' | '\\u1380'..'\\u138F' | '\\u13A0'..'\\u13F5' |
	//	'\\u13F8'..'\\u13FD' | '\\u1401'..'\\u166C' | '\\u166F'..'\\u167F' | '\\u1681'..'\\u169A' | '\\u16A0'..'\\u16EA' |
	//	'\\u16EE'..'\\u16F8' | '\\u1700'..'\\u170C' | '\\u170E'..'\\u1711' | '\\u1720'..'\\u1731' | '\\u1740'..'\\u1751' |
	//	'\\u1760'..'\\u176C' | '\\u176E'..'\\u1770' | '\\u1780'..'\\u17B3' | '\\u17D7' | '\\u17DC' | '\\u1820'..'\\u1877' |
	//	'\\u1880'..'\\u18A8' | '\\u18AA' | '\\u18B0'..'\\u18F5' | '\\u1900'..'\\u191E' | '\\u1950'..'\\u196D' | '\\u1970'..'\\u1974' |
	//	'\\u1980'..'\\u19AB' | '\\u19B0'..'\\u19C9' | '\\u1A00'..'\\u1A16' | '\\u1A20'..'\\u1A54' | '\\u1AA7' | '\\u1B05'..'\\u1B33' |
	//	'\\u1B45'..'\\u1B4B' | '\\u1B83'..'\\u1BA0' | '\\u1BAE'..'\\u1BAF' | '\\u1BBA'..'\\u1BE5' | '\\u1C00'..'\\u1C23' |
	//	'\\u1C4D'..'\\u1C4F' | '\\u1C5A'..'\\u1C7D' | '\\u1CE9'..'\\u1CEC' | '\\u1CEE'..'\\u1CF1' | '\\u1CF5'..'\\u1CF6' |
	//	'\\u1D00'..'\\u1DBF' | '\\u1E00'..'\\u1F15' | '\\u1F18'..'\\u1F1D' | '\\u1F20'..'\\u1F45' | '\\u1F48'..'\\u1F4D' |
	//	'\\u1F50'..'\\u1F57' | '\\u1F59' | '\\u1F5B' | '\\u1F5D' | '\\u1F5F'..'\\u1F7D' | '\\u1F80'..'\\u1FB4' | '\\u1FB6'..'\\u1FBC' |
	//	'\\u1FBE' | '\\u1FC2'..'\\u1FC4' | '\\u1FC6'..'\\u1FCC' | '\\u1FD0'..'\\u1FD3' | '\\u1FD6'..'\\u1FDB' | '\\u1FE0'..'\\u1FEC' |
	//	'\\u1FF2'..'\\u1FF4' | '\\u1FF6'..'\\u1FFC' | '\\u2071' | '\\u207F' | '\\u2090'..'\\u209C' | '\\u2102' | '\\u2107' |
	//	'\\u210A'..'\\u2113' | '\\u2115' | '\\u2119'..'\\u211D' | '\\u2124' | '\\u2126' | '\\u2128' | '\\u212A'..'\\u212D' |
	//	'\\u212F'..'\\u2139' | '\\u213C'..'\\u213F' | '\\u2145'..'\\u2149' | '\\u214E' | '\\u2160'..'\\u2188' | '\\u2C00'..'\\u2C2E' |
	//	'\\u2C30'..'\\u2C5E' | '\\u2C60'..'\\u2CE4' | '\\u2CEB'..'\\u2CEE' | '\\u2CF2'..'\\u2CF3' | '\\u2D00'..'\\u2D25' | '\\u2D27' |
	//	'\\u2D2D' | '\\u2D30'..'\\u2D67' | '\\u2D6F' | '\\u2D80'..'\\u2D96' | '\\u2DA0'..'\\u2DA6' | '\\u2DA8'..'\\u2DAE' |
	//	'\\u2DB0'..'\\u2DB6' | '\\u2DB8'..'\\u2DBE' | '\\u2DC0'..'\\u2DC6' | '\\u2DC8'..'\\u2DCE' | '\\u2DD0'..'\\u2DD6' |
	//	'\\u2DD8'..'\\u2DDE' | '\\u2E2F' | '\\u3005'..'\\u3007' | '\\u3021'..'\\u3029' | '\\u3031'..'\\u3035' | '\\u3038'..'\\u303C' |
	//	'\\u3041'..'\\u3096' | '\\u309D'..'\\u309F' | '\\u30A1'..'\\u30FA' | '\\u30FC'..'\\u30FF' | '\\u3105'..'\\u312D' |
	//	'\\u3131'..'\\u318E' | '\\u31A0'..'\\u31BA' | '\\u31F0'..'\\u31FF' | '\\u3400'..'\\u4DB5' | '\\u4E00'..'\\u9FD5' |
	//	'\\uA000'..'\\uA48C' | '\\uA4D0'..'\\uA4FD' | '\\uA500'..'\\uA60C' | '\\uA610'..'\\uA61F' | '\\uA62A'..'\\uA62B' |
	//	'\\uA640'..'\\uA66E' | '\\uA67F'..'\\uA69D' | '\\uA6A0'..'\\uA6EF' | '\\uA717'..'\\uA71F' | '\\uA722'..'\\uA788' |
	//	'\\uA78B'..'\\uA7AD' | '\\uA7B0'..'\\uA7B7' | '\\uA7F7'..'\\uA801' | '\\uA803'..'\\uA805' | '\\uA807'..'\\uA80A' |
	//	'\\uA80C'..'\\uA822' | '\\uA840'..'\\uA873' | '\\uA882'..'\\uA8B3' | '\\uA8F2'..'\\uA8F7' | '\\uA8FB' | '\\uA8FD' |
	//	'\\uA90A'..'\\uA925' | '\\uA930'..'\\uA946' | '\\uA960'..'\\uA97C' | '\\uA984'..'\\uA9B2' | '\\uA9CF' | '\\uA9E0'..'\\uA9E4' |
	//	'\\uA9E6'..'\\uA9EF' | '\\uA9FA'..'\\uA9FE' | '\\uAA00'..'\\uAA28' | '\\uAA40'..'\\uAA42' | '\\uAA44'..'\\uAA4B' |
	//	'\\uAA60'..'\\uAA76' | '\\uAA7A' | '\\uAA7E'..'\\uAAAF' | '\\uAAB1' | '\\uAAB5'..'\\uAAB6' | '\\uAAB9'..'\\uAABD' | '\\uAAC0' |
	//	'\\uAAC2' | '\\uAADB'..'\\uAADD' | '\\uAAE0'..'\\uAAEA' | '\\uAAF2'..'\\uAAF4' | '\\uAB01'..'\\uAB06' | '\\uAB09'..'\\uAB0E' |
	//	'\\uAB11'..'\\uAB16' | '\\uAB20'..'\\uAB26' | '\\uAB28'..'\\uAB2E' | '\\uAB30'..'\\uAB5A' | '\\uAB5C'..'\\uAB65' |
	//	'\\uAB70'..'\\uABE2' | '\\uAC00'..'\\uD7A3' | '\\uD7B0'..'\\uD7C6' | '\\uD7CB'..'\\uD7FB' | '\\uF900'..'\\uFA6D' |
	//	'\\uFA70'..'\\uFAD9' | '\\uFB00'..'\\uFB06' | '\\uFB13'..'\\uFB17' | '\\uFB1D' | '\\uFB1F'..'\\uFB28' | '\\uFB2A'..'\\uFB36' |
	//	'\\uFB38'..'\\uFB3C' | '\\uFB3E' | '\\uFB40'..'\\uFB41' | '\\uFB43'..'\\uFB44' | '\\uFB46'..'\\uFBB1' | '\\uFBD3'..'\\uFD3D' |
	//	'\\uFD50'..'\\uFD8F' | '\\uFD92'..'\\uFDC7' | '\\uFDF0'..'\\uFDFB' | '\\uFE70'..'\\uFE74' | '\\uFE76'..'\\uFEFC' |
	//	'\\uFF21'..'\\uFF3A' | '\\uFF41'..'\\uFF5A' | '\\uFF66'..'\\uFFBE' | '\\uFFC2'..'\\uFFC7' | '\\uFFCA'..'\\uFFCF' |
	//	'\\uFFD2'..'\\uFFD7' | '\\uFFDA'..'\\uFFDC';
	public TerminalRule getUNICODE_LETTER_FRAGMENTRule() {
		return gaUnicode.getUNICODE_LETTER_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_SPACE_SEPARATOR_FRAGMENT: // any character in the Unicode categories
	//// ―space separator (Zs)
	//	'\\u0020' | '\\u00A0' | '\\u1680' | '\\u2000'..'\\u200A' | '\\u202F' | '\\u205F' | '\\u3000';
	public TerminalRule getUNICODE_SPACE_SEPARATOR_FRAGMENTRule() {
		return gaUnicode.getUNICODE_SPACE_SEPARATOR_FRAGMENTRule();
	}
	
	//terminal fragment ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaUnicode.getANY_OTHERRule();
	}
}
