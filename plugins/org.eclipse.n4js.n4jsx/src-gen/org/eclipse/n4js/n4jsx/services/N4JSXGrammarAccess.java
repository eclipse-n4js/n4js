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
package org.eclipse.n4js.n4jsx.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.n4js.common.unicode.services.UnicodeGrammarAccess;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class N4JSXGrammarAccess extends AbstractGrammarElementFinder {
	
	public class IDLScriptElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.IDLScript");
		private final RuleCall cScriptParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//IDLScript n4js::Script:
		//	super::Script;
		@Override public ParserRule getRule() { return rule; }
		
		//super::Script
		public RuleCall getScriptParserRuleCall() { return cScriptParserRuleCall; }
	}
	public class PrimaryExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.PrimaryExpression");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cThisLiteralParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSuperLiteralParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cIdentifierRefParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cJSXElementParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cParameterizedCallExpressionParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cLiteralParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cArrayLiteralParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		private final RuleCall cObjectLiteralParserRuleCall_7 = (RuleCall)cAlternatives.eContents().get(7);
		private final RuleCall cParenExpressionParserRuleCall_8 = (RuleCall)cAlternatives.eContents().get(8);
		private final RuleCall cAnnotatedExpressionParserRuleCall_9 = (RuleCall)cAlternatives.eContents().get(9);
		private final RuleCall cFunctionExpressionParserRuleCall_10 = (RuleCall)cAlternatives.eContents().get(10);
		private final RuleCall cAsyncFunctionExpressionParserRuleCall_11 = (RuleCall)cAlternatives.eContents().get(11);
		private final RuleCall cN4ClassExpressionParserRuleCall_12 = (RuleCall)cAlternatives.eContents().get(12);
		private final RuleCall cTemplateLiteralParserRuleCall_13 = (RuleCall)cAlternatives.eContents().get(13);
		
		//@ Override PrimaryExpression <Yield n4js::Expression:
		//	ThisLiteral
		//	| SuperLiteral
		//	| IdentifierRef<Yield> | JSXElement
		//	| ParameterizedCallExpression<Yield> | Literal
		//	| ArrayLiteral<Yield> | ObjectLiteral<Yield> | ParenExpression<Yield> | AnnotatedExpression<Yield> |
		//	FunctionExpression
		//	| AsyncFunctionExpression
		//	| N4ClassExpression<Yield> | TemplateLiteral<Yield>;
		@Override public ParserRule getRule() { return rule; }
		
		//ThisLiteral | SuperLiteral | IdentifierRef<Yield> | JSXElement | ParameterizedCallExpression<Yield> | Literal |
		//ArrayLiteral<Yield> | ObjectLiteral<Yield> | ParenExpression<Yield> | AnnotatedExpression<Yield> | FunctionExpression |
		//AsyncFunctionExpression | N4ClassExpression<Yield> | TemplateLiteral<Yield>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ThisLiteral
		public RuleCall getThisLiteralParserRuleCall_0() { return cThisLiteralParserRuleCall_0; }
		
		//SuperLiteral
		public RuleCall getSuperLiteralParserRuleCall_1() { return cSuperLiteralParserRuleCall_1; }
		
		//IdentifierRef<Yield>
		public RuleCall getIdentifierRefParserRuleCall_2() { return cIdentifierRefParserRuleCall_2; }
		
		//JSXElement
		public RuleCall getJSXElementParserRuleCall_3() { return cJSXElementParserRuleCall_3; }
		
		//ParameterizedCallExpression<Yield>
		public RuleCall getParameterizedCallExpressionParserRuleCall_4() { return cParameterizedCallExpressionParserRuleCall_4; }
		
		//Literal
		public RuleCall getLiteralParserRuleCall_5() { return cLiteralParserRuleCall_5; }
		
		//ArrayLiteral<Yield>
		public RuleCall getArrayLiteralParserRuleCall_6() { return cArrayLiteralParserRuleCall_6; }
		
		//ObjectLiteral<Yield>
		public RuleCall getObjectLiteralParserRuleCall_7() { return cObjectLiteralParserRuleCall_7; }
		
		//ParenExpression<Yield>
		public RuleCall getParenExpressionParserRuleCall_8() { return cParenExpressionParserRuleCall_8; }
		
		//AnnotatedExpression<Yield>
		public RuleCall getAnnotatedExpressionParserRuleCall_9() { return cAnnotatedExpressionParserRuleCall_9; }
		
		//FunctionExpression
		public RuleCall getFunctionExpressionParserRuleCall_10() { return cFunctionExpressionParserRuleCall_10; }
		
		//AsyncFunctionExpression
		public RuleCall getAsyncFunctionExpressionParserRuleCall_11() { return cAsyncFunctionExpressionParserRuleCall_11; }
		
		//N4ClassExpression<Yield>
		public RuleCall getN4ClassExpressionParserRuleCall_12() { return cN4ClassExpressionParserRuleCall_12; }
		
		//TemplateLiteral<Yield>
		public RuleCall getTemplateLiteralParserRuleCall_13() { return cTemplateLiteralParserRuleCall_13; }
	}
	public class JSXElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXElement");
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
		private final RuleCall cJSXClosingElementParserRuleCall_3_0_2 = (RuleCall)cGroup_3_0.eContents().get(2);
		private final Group cGroup_3_1 = (Group)cAlternatives_3.eContents().get(1);
		private final Keyword cSolidusKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_3_1_1 = (Keyword)cGroup_3_1.eContents().get(1);
		
		//JSXElement:
		//	'<' jsxElementName=JSXElementName JSXAttributes ('>' jsxChildren+=JSXChild* JSXClosingElement | '/' '>');
		@Override public ParserRule getRule() { return rule; }
		
		//'<' jsxElementName=JSXElementName JSXAttributes ('>' jsxChildren+=JSXChild* JSXClosingElement | '/' '>')
		public Group getGroup() { return cGroup; }
		
		//'<'
		public Keyword getLessThanSignKeyword_0() { return cLessThanSignKeyword_0; }
		
		//jsxElementName=JSXElementName
		public Assignment getJsxElementNameAssignment_1() { return cJsxElementNameAssignment_1; }
		
		//JSXElementName
		public RuleCall getJsxElementNameJSXElementNameParserRuleCall_1_0() { return cJsxElementNameJSXElementNameParserRuleCall_1_0; }
		
		//JSXAttributes
		public RuleCall getJSXAttributesParserRuleCall_2() { return cJSXAttributesParserRuleCall_2; }
		
		//'>' jsxChildren+=JSXChild* JSXClosingElement | '/' '>'
		public Alternatives getAlternatives_3() { return cAlternatives_3; }
		
		//'>' jsxChildren+=JSXChild* JSXClosingElement
		public Group getGroup_3_0() { return cGroup_3_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3_0_0() { return cGreaterThanSignKeyword_3_0_0; }
		
		//jsxChildren+=JSXChild*
		public Assignment getJsxChildrenAssignment_3_0_1() { return cJsxChildrenAssignment_3_0_1; }
		
		//JSXChild
		public RuleCall getJsxChildrenJSXChildParserRuleCall_3_0_1_0() { return cJsxChildrenJSXChildParserRuleCall_3_0_1_0; }
		
		//JSXClosingElement
		public RuleCall getJSXClosingElementParserRuleCall_3_0_2() { return cJSXClosingElementParserRuleCall_3_0_2; }
		
		//'/' '>'
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//'/'
		public Keyword getSolidusKeyword_3_1_0() { return cSolidusKeyword_3_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3_1_1() { return cGreaterThanSignKeyword_3_1_1; }
	}
	public class JSXClosingElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXClosingElement");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLessThanSignKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cSolidusKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cJsxClosingNameAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cJsxClosingNameJSXElementNameParserRuleCall_2_0 = (RuleCall)cJsxClosingNameAssignment_2.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//fragment JSXClosingElement *:
		//	'<' '/' jsxClosingName=JSXElementName '>';
		@Override public ParserRule getRule() { return rule; }
		
		//'<' '/' jsxClosingName=JSXElementName '>'
		public Group getGroup() { return cGroup; }
		
		//'<'
		public Keyword getLessThanSignKeyword_0() { return cLessThanSignKeyword_0; }
		
		//'/'
		public Keyword getSolidusKeyword_1() { return cSolidusKeyword_1; }
		
		//jsxClosingName=JSXElementName
		public Assignment getJsxClosingNameAssignment_2() { return cJsxClosingNameAssignment_2; }
		
		//JSXElementName
		public RuleCall getJsxClosingNameJSXElementNameParserRuleCall_2_0() { return cJsxClosingNameJSXElementNameParserRuleCall_2_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_3() { return cGreaterThanSignKeyword_3; }
	}
	public class JSXChildElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXChild");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cJSXElementParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cJSXExpressionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//JSXChild:
		//	JSXElement | JSXExpression
		//	//	| JSXText -- not supported yet, cf. IDE-2414
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//JSXElement | JSXExpression
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//JSXElement
		public RuleCall getJSXElementParserRuleCall_0() { return cJSXElementParserRuleCall_0; }
		
		//JSXExpression
		public RuleCall getJSXExpressionParserRuleCall_1() { return cJSXExpressionParserRuleCall_1; }
	}
	public class JSXExpressionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXExpression");
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXElementName");
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXElementNameExpression");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cIdentifierRefParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cParameterizedPropertyAccessExpressionTargetAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final RuleCall cParameterizedPropertyAccessExpressionTailParserRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//JSXElementNameExpression n4js::Expression:
		//	IdentifierRef<false> ({n4js::ParameterizedPropertyAccessExpression.target=current}
		//	ParameterizedPropertyAccessExpressionTail<false>)*
		//	//	| JSXNamedspacedName not supported
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//IdentifierRef<false> ({n4js::ParameterizedPropertyAccessExpression.target=current}
		//ParameterizedPropertyAccessExpressionTail<false>)*
		public Group getGroup() { return cGroup; }
		
		//IdentifierRef<false>
		public RuleCall getIdentifierRefParserRuleCall_0() { return cIdentifierRefParserRuleCall_0; }
		
		//({n4js::ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<false>)*
		public Group getGroup_1() { return cGroup_1; }
		
		//{n4js::ParameterizedPropertyAccessExpression.target=current}
		public Action getParameterizedPropertyAccessExpressionTargetAction_1_0() { return cParameterizedPropertyAccessExpressionTargetAction_1_0; }
		
		//ParameterizedPropertyAccessExpressionTail<false>
		public RuleCall getParameterizedPropertyAccessExpressionTailParserRuleCall_1_1() { return cParameterizedPropertyAccessExpressionTailParserRuleCall_1_1; }
	}
	public class JSXAttributesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXAttributes");
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXAttribute");
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXSpreadAttribute");
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
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4jsx.N4JSX.JSXPropertyAttribute");
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
	
	
	private final IDLScriptElements pIDLScript;
	private final PrimaryExpressionElements pPrimaryExpression;
	private final JSXElementElements pJSXElement;
	private final JSXClosingElementElements pJSXClosingElement;
	private final JSXChildElements pJSXChild;
	private final JSXExpressionElements pJSXExpression;
	private final JSXElementNameElements pJSXElementName;
	private final JSXElementNameExpressionElements pJSXElementNameExpression;
	private final JSXAttributesElements pJSXAttributes;
	private final JSXAttributeElements pJSXAttribute;
	private final JSXSpreadAttributeElements pJSXSpreadAttribute;
	private final JSXPropertyAttributeElements pJSXPropertyAttribute;
	
	private final Grammar grammar;
	
	private final N4JSGrammarAccess gaN4JS;
	
	private final TypeExpressionsGrammarAccess gaTypeExpressions;
	
	private final UnicodeGrammarAccess gaUnicode;

	@Inject
	public N4JSXGrammarAccess(GrammarProvider grammarProvider,
			N4JSGrammarAccess gaN4JS,
			TypeExpressionsGrammarAccess gaTypeExpressions,
			UnicodeGrammarAccess gaUnicode) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaN4JS = gaN4JS;
		this.gaTypeExpressions = gaTypeExpressions;
		this.gaUnicode = gaUnicode;
		this.pIDLScript = new IDLScriptElements();
		this.pPrimaryExpression = new PrimaryExpressionElements();
		this.pJSXElement = new JSXElementElements();
		this.pJSXClosingElement = new JSXClosingElementElements();
		this.pJSXChild = new JSXChildElements();
		this.pJSXExpression = new JSXExpressionElements();
		this.pJSXElementName = new JSXElementNameElements();
		this.pJSXElementNameExpression = new JSXElementNameExpressionElements();
		this.pJSXAttributes = new JSXAttributesElements();
		this.pJSXAttribute = new JSXAttributeElements();
		this.pJSXSpreadAttribute = new JSXSpreadAttributeElements();
		this.pJSXPropertyAttribute = new JSXPropertyAttributeElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.n4jsx.N4JSX".equals(grammar.getName())) {
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
	
	
	public N4JSGrammarAccess getN4JSGrammarAccess() {
		return gaN4JS;
	}
	
	public TypeExpressionsGrammarAccess getTypeExpressionsGrammarAccess() {
		return gaTypeExpressions;
	}
	
	public UnicodeGrammarAccess getUnicodeGrammarAccess() {
		return gaUnicode;
	}

	
	//IDLScript n4js::Script:
	//	super::Script;
	public IDLScriptElements getIDLScriptAccess() {
		return pIDLScript;
	}
	
	public ParserRule getIDLScriptRule() {
		return getIDLScriptAccess().getRule();
	}
	
	//@ Override PrimaryExpression <Yield n4js::Expression:
	//	ThisLiteral
	//	| SuperLiteral
	//	| IdentifierRef<Yield> | JSXElement
	//	| ParameterizedCallExpression<Yield> | Literal
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
	
	//JSXElement:
	//	'<' jsxElementName=JSXElementName JSXAttributes ('>' jsxChildren+=JSXChild* JSXClosingElement | '/' '>');
	public JSXElementElements getJSXElementAccess() {
		return pJSXElement;
	}
	
	public ParserRule getJSXElementRule() {
		return getJSXElementAccess().getRule();
	}
	
	//fragment JSXClosingElement *:
	//	'<' '/' jsxClosingName=JSXElementName '>';
	public JSXClosingElementElements getJSXClosingElementAccess() {
		return pJSXClosingElement;
	}
	
	public ParserRule getJSXClosingElementRule() {
		return getJSXClosingElementAccess().getRule();
	}
	
	//JSXChild:
	//	JSXElement | JSXExpression
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
	
	//JSXElementNameExpression n4js::Expression:
	//	IdentifierRef<false> ({n4js::ParameterizedPropertyAccessExpression.target=current}
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
	
	//// ****************************************************************************************************
	//// [ECM11] A.5 Functions and Programs (p. 224)
	//// [ECM15]
	//// [ECMWiki] http://wiki.ecmascript.org/doku.php?id=harmony:modules
	//// ****************************************************************************************************
	//Script:
	//	{Script} annotations+=ScriptAnnotation*
	//	scriptElements+=ScriptElement*;
	public N4JSGrammarAccess.ScriptElements getScriptAccess() {
		return gaN4JS.getScriptAccess();
	}
	
	public ParserRule getScriptRule() {
		return getScriptAccess().getRule();
	}
	
	///*
	// * The top level elements in a script are type declarations, exports, imports or statements
	// */ ScriptElement:
	//	AnnotatedScriptElement
	//	| N4ClassDeclaration<Yield=false> | N4InterfaceDeclaration<Yield=false> | N4EnumDeclaration<Yield=false> |
	//	ImportDeclaration
	//	| ExportDeclaration
	//	| RootStatement<Yield=false>;
	public N4JSGrammarAccess.ScriptElementElements getScriptElementAccess() {
		return gaN4JS.getScriptElementAccess();
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
	//	name=BindingIdentifier<Yield=false> TypeVariables?
	//	ClassExtendsClause<Yield=false>?
	//	| {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
	//	'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield=false> TypeVariables?
	//	InterfaceImplementsList?) Members<Yield=false> | {N4EnumDeclaration.annotationList=current}
	//	declaredModifiers+=N4Modifier*
	//	'enum' name=BindingIdentifier<Yield=false>
	//	'{'
	//	literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*
	//	'}');
	public N4JSGrammarAccess.AnnotatedScriptElementElements getAnnotatedScriptElementAccess() {
		return gaN4JS.getAnnotatedScriptElementAccess();
	}
	
	public ParserRule getAnnotatedScriptElementRule() {
		return getAnnotatedScriptElementAccess().getRule();
	}
	
	//ExportDeclaration:
	//	{ExportDeclaration} ExportDeclarationImpl;
	public N4JSGrammarAccess.ExportDeclarationElements getExportDeclarationAccess() {
		return gaN4JS.getExportDeclarationAccess();
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
	public N4JSGrammarAccess.ExportDeclarationImplElements getExportDeclarationImplAccess() {
		return gaN4JS.getExportDeclarationImplAccess();
	}
	
	public ParserRule getExportDeclarationImplRule() {
		return getExportDeclarationImplAccess().getRule();
	}
	
	//fragment ExportFromClause *:
	//	'from' reexportedFrom=[types::TModule|ModuleSpecifier];
	public N4JSGrammarAccess.ExportFromClauseElements getExportFromClauseAccess() {
		return gaN4JS.getExportFromClauseAccess();
	}
	
	public ParserRule getExportFromClauseRule() {
		return getExportFromClauseAccess().getRule();
	}
	
	//fragment ExportClause *:
	//	'{' (namedExports+=ExportSpecifier (',' namedExports+=ExportSpecifier)* ','?)?
	//	'}';
	public N4JSGrammarAccess.ExportClauseElements getExportClauseAccess() {
		return gaN4JS.getExportClauseAccess();
	}
	
	public ParserRule getExportClauseRule() {
		return getExportClauseAccess().getRule();
	}
	
	//ExportSpecifier:
	//	element=IdentifierRef<Yield=false> ('as' alias=IdentifierName)?;
	public N4JSGrammarAccess.ExportSpecifierElements getExportSpecifierAccess() {
		return gaN4JS.getExportSpecifierAccess();
	}
	
	public ParserRule getExportSpecifierRule() {
		return getExportSpecifierAccess().getRule();
	}
	
	//ExportableElement:
	//	AnnotatedExportableElement<Yield=false> | N4ClassDeclaration<Yield=false> | N4InterfaceDeclaration<Yield=false> |
	//	N4EnumDeclaration<Yield=false> | FunctionDeclaration<Yield=false> | ExportedVariableStatement;
	public N4JSGrammarAccess.ExportableElementElements getExportableElementAccess() {
		return gaN4JS.getExportableElementAccess();
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
	//	ClassExtendsClause<Yield>?
	//	| {N4InterfaceDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
	//	'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield> TypeVariables?
	//	InterfaceImplementsList?) Members<Yield> | {N4EnumDeclaration.annotationList=current} declaredModifiers+=N4Modifier*
	//	'enum' name=BindingIdentifier<Yield>
	//	'{'
	//	literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*
	//	'}');
	public N4JSGrammarAccess.AnnotatedExportableElementElements getAnnotatedExportableElementAccess() {
		return gaN4JS.getAnnotatedExportableElementAccess();
	}
	
	public ParserRule getAnnotatedExportableElementRule() {
		return getAnnotatedExportableElementAccess().getRule();
	}
	
	//ImportDeclaration:
	//	{ImportDeclaration} ImportDeclarationImpl;
	public N4JSGrammarAccess.ImportDeclarationElements getImportDeclarationAccess() {
		return gaN4JS.getImportDeclarationAccess();
	}
	
	public ParserRule getImportDeclarationRule() {
		return getImportDeclarationAccess().getRule();
	}
	
	//fragment ImportDeclarationImpl *:
	//	'import' (ImportClause importFrom?='from')? module=[types::TModule|ModuleSpecifier] Semi;
	public N4JSGrammarAccess.ImportDeclarationImplElements getImportDeclarationImplAccess() {
		return gaN4JS.getImportDeclarationImplAccess();
	}
	
	public ParserRule getImportDeclarationImplRule() {
		return getImportDeclarationImplAccess().getRule();
	}
	
	//fragment ImportClause returns ImportDeclaration:
	//	importSpecifiers+=DefaultImportSpecifier (',' ImportSpecifiersExceptDefault)?
	//	| ImportSpecifiersExceptDefault;
	public N4JSGrammarAccess.ImportClauseElements getImportClauseAccess() {
		return gaN4JS.getImportClauseAccess();
	}
	
	public ParserRule getImportClauseRule() {
		return getImportClauseAccess().getRule();
	}
	
	//fragment ImportSpecifiersExceptDefault returns ImportDeclaration:
	//	importSpecifiers+=NamespaceImportSpecifier
	//	| '{' (importSpecifiers+=NamedImportSpecifier (',' importSpecifiers+=NamedImportSpecifier)* ','?)? '}';
	public N4JSGrammarAccess.ImportSpecifiersExceptDefaultElements getImportSpecifiersExceptDefaultAccess() {
		return gaN4JS.getImportSpecifiersExceptDefaultAccess();
	}
	
	public ParserRule getImportSpecifiersExceptDefaultRule() {
		return getImportSpecifiersExceptDefaultAccess().getRule();
	}
	
	//NamedImportSpecifier:
	//	importedElement=[types::TExportableElement|BindingIdentifier<Yield=false>] |
	//	importedElement=[types::TExportableElement|IdentifierName] 'as' alias=BindingIdentifier<Yield=false>;
	public N4JSGrammarAccess.NamedImportSpecifierElements getNamedImportSpecifierAccess() {
		return gaN4JS.getNamedImportSpecifierAccess();
	}
	
	public ParserRule getNamedImportSpecifierRule() {
		return getNamedImportSpecifierAccess().getRule();
	}
	
	//DefaultImportSpecifier:
	//	importedElement=[types::TExportableElement|BindingIdentifier<Yield=false>];
	public N4JSGrammarAccess.DefaultImportSpecifierElements getDefaultImportSpecifierAccess() {
		return gaN4JS.getDefaultImportSpecifierAccess();
	}
	
	public ParserRule getDefaultImportSpecifierRule() {
		return getDefaultImportSpecifierAccess().getRule();
	}
	
	//NamespaceImportSpecifier:
	//	{NamespaceImportSpecifier} '*' 'as' alias=BindingIdentifier<false> declaredDynamic?='+'?;
	public N4JSGrammarAccess.NamespaceImportSpecifierElements getNamespaceImportSpecifierAccess() {
		return gaN4JS.getNamespaceImportSpecifierAccess();
	}
	
	public ParserRule getNamespaceImportSpecifierRule() {
		return getNamespaceImportSpecifierAccess().getRule();
	}
	
	//ModuleSpecifier:
	//	STRING;
	public N4JSGrammarAccess.ModuleSpecifierElements getModuleSpecifierAccess() {
		return gaN4JS.getModuleSpecifierAccess();
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
	public N4JSGrammarAccess.FunctionDeclarationElements getFunctionDeclarationAccess() {
		return gaN4JS.getFunctionDeclarationAccess();
	}
	
	public ParserRule getFunctionDeclarationRule() {
		return getFunctionDeclarationAccess().getRule();
	}
	
	//fragment AsyncNoTrailingLineBreak *:
	//	(declaredAsync?='async' NoLineTerminator)?;
	public N4JSGrammarAccess.AsyncNoTrailingLineBreakElements getAsyncNoTrailingLineBreakAccess() {
		return gaN4JS.getAsyncNoTrailingLineBreakAccess();
	}
	
	public ParserRule getAsyncNoTrailingLineBreakRule() {
		return getAsyncNoTrailingLineBreakAccess().getRule();
	}
	
	//fragment FunctionImpl <Yield, YieldIfGenerator, Expression> *:
	//	'function' (generator?='*' FunctionHeader<YieldIfGenerator,Generator=true> FunctionBody<Yield=true,Expression> |
	//	FunctionHeader<Yield,Generator=false> FunctionBody<Yield=false,Expression>);
	public N4JSGrammarAccess.FunctionImplElements getFunctionImplAccess() {
		return gaN4JS.getFunctionImplAccess();
	}
	
	public ParserRule getFunctionImplRule() {
		return getFunctionImplAccess().getRule();
	}
	
	//fragment FunctionHeader <Yield, Generator> *:
	//	TypeVariables?
	//	name=BindingIdentifier<Yield>?
	//	StrictFormalParameters<Yield=Generator> -> ColonSepReturnTypeRef?;
	public N4JSGrammarAccess.FunctionHeaderElements getFunctionHeaderAccess() {
		return gaN4JS.getFunctionHeaderAccess();
	}
	
	public ParserRule getFunctionHeaderRule() {
		return getFunctionHeaderAccess().getRule();
	}
	
	//fragment FunctionBody <Yield, Expression> *:
	//	<Expression> body=Block<Yield> | <!Expression> body=Block<Yield>?;
	public N4JSGrammarAccess.FunctionBodyElements getFunctionBodyAccess() {
		return gaN4JS.getFunctionBodyAccess();
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
	public N4JSGrammarAccess.AnnotatedFunctionDeclarationElements getAnnotatedFunctionDeclarationAccess() {
		return gaN4JS.getAnnotatedFunctionDeclarationAccess();
	}
	
	public ParserRule getAnnotatedFunctionDeclarationRule() {
		return getAnnotatedFunctionDeclarationAccess().getRule();
	}
	
	//FunctionExpression:
	//	{FunctionExpression} FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>;
	public N4JSGrammarAccess.FunctionExpressionElements getFunctionExpressionAccess() {
		return gaN4JS.getFunctionExpressionAccess();
	}
	
	public ParserRule getFunctionExpressionRule() {
		return getFunctionExpressionAccess().getRule();
	}
	
	///**
	// * We cannot use fragments here since we have to combine the terminals into a syntactic predicate.
	// */ AsyncFunctionExpression FunctionExpression:
	//	=> (declaredAsync?='async' NoLineTerminator 'function') FunctionHeader<Yield=false,Generator=false>
	//	FunctionBody<Yield=false,Expression=true>;
	public N4JSGrammarAccess.AsyncFunctionExpressionElements getAsyncFunctionExpressionAccess() {
		return gaN4JS.getAsyncFunctionExpressionAccess();
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
	public N4JSGrammarAccess.ArrowExpressionElements getArrowExpressionAccess() {
		return gaN4JS.getArrowExpressionAccess();
	}
	
	public ParserRule getArrowExpressionRule() {
		return getArrowExpressionAccess().getRule();
	}
	
	//fragment StrictFormalParameters <Yield> *:
	//	'(' (fpars+=FormalParameter<Yield> (',' fpars+=FormalParameter<Yield>)*)? ')';
	public N4JSGrammarAccess.StrictFormalParametersElements getStrictFormalParametersAccess() {
		return gaN4JS.getStrictFormalParametersAccess();
	}
	
	public ParserRule getStrictFormalParametersRule() {
		return getStrictFormalParametersAccess().getRule();
	}
	
	//BindingIdentifierAsFormalParameter <Yield FormalParameter:
	//	name=BindingIdentifier<Yield>;
	public N4JSGrammarAccess.BindingIdentifierAsFormalParameterElements getBindingIdentifierAsFormalParameterAccess() {
		return gaN4JS.getBindingIdentifierAsFormalParameterAccess();
	}
	
	public ParserRule getBindingIdentifierAsFormalParameterRule() {
		return getBindingIdentifierAsFormalParameterAccess().getRule();
	}
	
	//BlockMinusBraces <Yield Block:
	//	{Block} statements+=Statement<Yield>*;
	public N4JSGrammarAccess.BlockMinusBracesElements getBlockMinusBracesAccess() {
		return gaN4JS.getBlockMinusBracesAccess();
	}
	
	public ParserRule getBlockMinusBracesRule() {
		return getBlockMinusBracesAccess().getRule();
	}
	
	//ExpressionDisguisedAsBlock <In Block:
	//	{Block} statements+=AssignmentExpressionStatement<In>;
	public N4JSGrammarAccess.ExpressionDisguisedAsBlockElements getExpressionDisguisedAsBlockAccess() {
		return gaN4JS.getExpressionDisguisedAsBlockAccess();
	}
	
	public ParserRule getExpressionDisguisedAsBlockRule() {
		return getExpressionDisguisedAsBlockAccess().getRule();
	}
	
	//AssignmentExpressionStatement <In ExpressionStatement:
	//	expression=AssignmentExpression<In,Yield=false>;
	public N4JSGrammarAccess.AssignmentExpressionStatementElements getAssignmentExpressionStatementAccess() {
		return gaN4JS.getAssignmentExpressionStatementAccess();
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
	//	ClassExtendsClause<Yield>?
	//	Members<Yield> | {FunctionExpression.annotationList=current} AsyncNoTrailingLineBreak
	//	FunctionImpl<Yield=false,YieldIfGenerator=true,Expression=true>);
	public N4JSGrammarAccess.AnnotatedExpressionElements getAnnotatedExpressionAccess() {
		return gaN4JS.getAnnotatedExpressionAccess();
	}
	
	public ParserRule getAnnotatedExpressionRule() {
		return getAnnotatedExpressionAccess().getRule();
	}
	
	//@ Override TypeVariable types::TypeVariable:
	//	(declaredCovariant?='out' | declaredContravariant?='in')?
	//	name=IdentifierOrThis ('extends' declaredUpperBound=TypeRef)?;
	public N4JSGrammarAccess.TypeVariableElements getTypeVariableAccess() {
		return gaN4JS.getTypeVariableAccess();
	}
	
	public ParserRule getTypeVariableRule() {
		return getTypeVariableAccess().getRule();
	}
	
	//FormalParameter <Yield>:
	//	{FormalParameter} BindingElementFragment<Yield>;
	public N4JSGrammarAccess.FormalParameterElements getFormalParameterAccess() {
		return gaN4JS.getFormalParameterAccess();
	}
	
	public ParserRule getFormalParameterRule() {
		return getFormalParameterAccess().getRule();
	}
	
	//fragment BindingElementFragment <Yield> *:
	//	(=> bindingPattern=BindingPattern<Yield> | annotations+=Annotation* BogusTypeRefFragment? variadic?='...'?
	//	name=BindingIdentifier<Yield> ColonSepDeclaredTypeRef?) (hasInitializerAssignment?='='
	//	initializer=AssignmentExpression<In=true,Yield>?)?;
	public N4JSGrammarAccess.BindingElementFragmentElements getBindingElementFragmentAccess() {
		return gaN4JS.getBindingElementFragmentAccess();
	}
	
	public ParserRule getBindingElementFragmentRule() {
		return getBindingElementFragmentAccess().getRule();
	}
	
	//fragment BogusTypeRefFragment *:
	//	bogusTypeRef=TypeRefWithModifiers;
	public N4JSGrammarAccess.BogusTypeRefFragmentElements getBogusTypeRefFragmentAccess() {
		return gaN4JS.getBogusTypeRefFragmentAccess();
	}
	
	public ParserRule getBogusTypeRefFragmentRule() {
		return getBogusTypeRefFragmentAccess().getRule();
	}
	
	//Block <Yield>:
	//	=> ({Block} '{') statements+=Statement<Yield>* '}';
	public N4JSGrammarAccess.BlockElements getBlockAccess() {
		return gaN4JS.getBlockAccess();
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
	public N4JSGrammarAccess.RootStatementElements getRootStatementAccess() {
		return gaN4JS.getRootStatementAccess();
	}
	
	public ParserRule getRootStatementRule() {
		return getRootStatementAccess().getRule();
	}
	
	//Statement <Yield>:
	//	AnnotatedFunctionDeclaration<Yield,Default=false> | RootStatement<Yield>;
	public N4JSGrammarAccess.StatementElements getStatementAccess() {
		return gaN4JS.getStatementAccess();
	}
	
	public ParserRule getStatementRule() {
		return getStatementAccess().getRule();
	}
	
	//enum VariableStatementKeyword:
	//	var | const | let;
	public N4JSGrammarAccess.VariableStatementKeywordElements getVariableStatementKeywordAccess() {
		return gaN4JS.getVariableStatementKeywordAccess();
	}
	
	public EnumRule getVariableStatementKeywordRule() {
		return getVariableStatementKeywordAccess().getRule();
	}
	
	//VariableStatement <In, Yield>:
	//	=> ({VariableStatement} varStmtKeyword=VariableStatementKeyword)
	//	varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false> (','
	//	varDeclsOrBindings+=VariableDeclarationOrBinding<In,Yield,false>)* Semi;
	public N4JSGrammarAccess.VariableStatementElements getVariableStatementAccess() {
		return gaN4JS.getVariableStatementAccess();
	}
	
	public ParserRule getVariableStatementRule() {
		return getVariableStatementAccess().getRule();
	}
	
	//ExportedVariableStatement:
	//	{ExportedVariableStatement} declaredModifiers+=N4Modifier*
	//	varStmtKeyword=VariableStatementKeyword
	//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false> (','
	//	varDeclsOrBindings+=ExportedVariableDeclarationOrBinding<Yield=false>)* Semi;
	public N4JSGrammarAccess.ExportedVariableStatementElements getExportedVariableStatementAccess() {
		return gaN4JS.getExportedVariableStatementAccess();
	}
	
	public ParserRule getExportedVariableStatementRule() {
		return getExportedVariableStatementAccess().getRule();
	}
	
	//VariableDeclarationOrBinding <In, Yield, OptionalInit>:
	//	VariableBinding<In,Yield,OptionalInit> | VariableDeclaration<In,Yield,true>;
	public N4JSGrammarAccess.VariableDeclarationOrBindingElements getVariableDeclarationOrBindingAccess() {
		return gaN4JS.getVariableDeclarationOrBindingAccess();
	}
	
	public ParserRule getVariableDeclarationOrBindingRule() {
		return getVariableDeclarationOrBindingAccess().getRule();
	}
	
	//VariableBinding <In, Yield, OptionalInit>:
	//	=> pattern=BindingPattern<Yield> (<OptionalInit> ('=' expression=AssignmentExpression<In,Yield>)?
	//	| <!OptionalInit> '=' expression=AssignmentExpression<In,Yield>);
	public N4JSGrammarAccess.VariableBindingElements getVariableBindingAccess() {
		return gaN4JS.getVariableBindingAccess();
	}
	
	public ParserRule getVariableBindingRule() {
		return getVariableBindingAccess().getRule();
	}
	
	//VariableDeclaration <In, Yield, AllowType>:
	//	{VariableDeclaration} VariableDeclarationImpl<In,Yield,AllowType>;
	public N4JSGrammarAccess.VariableDeclarationElements getVariableDeclarationAccess() {
		return gaN4JS.getVariableDeclarationAccess();
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
	public N4JSGrammarAccess.VariableDeclarationImplElements getVariableDeclarationImplAccess() {
		return gaN4JS.getVariableDeclarationImplAccess();
	}
	
	public ParserRule getVariableDeclarationImplRule() {
		return getVariableDeclarationImplAccess().getRule();
	}
	
	//ExportedVariableDeclarationOrBinding <Yield VariableDeclarationOrBinding:
	//	ExportedVariableBinding<Yield> | ExportedVariableDeclaration<Yield>;
	public N4JSGrammarAccess.ExportedVariableDeclarationOrBindingElements getExportedVariableDeclarationOrBindingAccess() {
		return gaN4JS.getExportedVariableDeclarationOrBindingAccess();
	}
	
	public ParserRule getExportedVariableDeclarationOrBindingRule() {
		return getExportedVariableDeclarationOrBindingAccess().getRule();
	}
	
	//ExportedVariableBinding <Yield>:
	//	=> pattern=BindingPattern<Yield> '=' expression=AssignmentExpression<In=true,Yield>;
	public N4JSGrammarAccess.ExportedVariableBindingElements getExportedVariableBindingAccess() {
		return gaN4JS.getExportedVariableBindingAccess();
	}
	
	public ParserRule getExportedVariableBindingRule() {
		return getExportedVariableBindingAccess().getRule();
	}
	
	///**
	// * The created AST element has an additional reference to the inferred TVariable
	// */ ExportedVariableDeclaration <Yield>:
	//	{ExportedVariableDeclaration} VariableDeclarationImpl<In=true,Yield,AllowType=true>;
	public N4JSGrammarAccess.ExportedVariableDeclarationElements getExportedVariableDeclarationAccess() {
		return gaN4JS.getExportedVariableDeclarationAccess();
	}
	
	public ParserRule getExportedVariableDeclarationRule() {
		return getExportedVariableDeclarationAccess().getRule();
	}
	
	//// Defined with Action in statement: Block: {Block}  '{' (statements+=Statement)* '}';
	//EmptyStatement:
	//	{EmptyStatement} ';';
	public N4JSGrammarAccess.EmptyStatementElements getEmptyStatementAccess() {
		return gaN4JS.getEmptyStatementAccess();
	}
	
	public ParserRule getEmptyStatementRule() {
		return getEmptyStatementAccess().getRule();
	}
	
	//// Lookahead (function, {) done elsewhere: see Statement and SourceElement definitions
	//ExpressionStatement <Yield>:
	//	expression=Expression<In=true,Yield> Semi;
	public N4JSGrammarAccess.ExpressionStatementElements getExpressionStatementAccess() {
		return gaN4JS.getExpressionStatementAccess();
	}
	
	public ParserRule getExpressionStatementRule() {
		return getExpressionStatementAccess().getRule();
	}
	
	//IfStatement <Yield>:
	//	'if' '(' expression=Expression<In=true,Yield> ')' ifStmt=Statement<Yield> (=> 'else' elseStmt=Statement<Yield>)?;
	public N4JSGrammarAccess.IfStatementElements getIfStatementAccess() {
		return gaN4JS.getIfStatementAccess();
	}
	
	public ParserRule getIfStatementRule() {
		return getIfStatementAccess().getRule();
	}
	
	//IterationStatement <Yield>:
	//	DoStatement<Yield> | WhileStatement<Yield> | ForStatement<Yield>;
	public N4JSGrammarAccess.IterationStatementElements getIterationStatementAccess() {
		return gaN4JS.getIterationStatementAccess();
	}
	
	public ParserRule getIterationStatementRule() {
		return getIterationStatementAccess().getRule();
	}
	
	//DoStatement <Yield>:
	//	'do' statement=Statement<Yield> 'while' '(' expression=Expression<In=true,Yield> ')' => Semi?;
	public N4JSGrammarAccess.DoStatementElements getDoStatementAccess() {
		return gaN4JS.getDoStatementAccess();
	}
	
	public ParserRule getDoStatementRule() {
		return getDoStatementAccess().getRule();
	}
	
	//WhileStatement <Yield>:
	//	'while' '(' expression=Expression<In=true,Yield> ')' statement=Statement<Yield>;
	public N4JSGrammarAccess.WhileStatementElements getWhileStatementAccess() {
		return gaN4JS.getWhileStatementAccess();
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
	public N4JSGrammarAccess.ForStatementElements getForStatementAccess() {
		return gaN4JS.getForStatementAccess();
	}
	
	public ParserRule getForStatementRule() {
		return getForStatementAccess().getRule();
	}
	
	//LetIdentifierRef IdentifierRef:
	//	id=[types::IdentifiableElement|LetAsIdentifier];
	public N4JSGrammarAccess.LetIdentifierRefElements getLetIdentifierRefAccess() {
		return gaN4JS.getLetIdentifierRefAccess();
	}
	
	public ParserRule getLetIdentifierRefRule() {
		return getLetIdentifierRefAccess().getRule();
	}
	
	//LetAsIdentifier:
	//	'let';
	public N4JSGrammarAccess.LetAsIdentifierElements getLetAsIdentifierAccess() {
		return gaN4JS.getLetAsIdentifierAccess();
	}
	
	public ParserRule getLetAsIdentifierRule() {
		return getLetAsIdentifierAccess().getRule();
	}
	
	//BindingIdentifierAsVariableDeclaration <In, Yield VariableDeclaration:
	//	name=BindingIdentifier<Yield>;
	public N4JSGrammarAccess.BindingIdentifierAsVariableDeclarationElements getBindingIdentifierAsVariableDeclarationAccess() {
		return gaN4JS.getBindingIdentifierAsVariableDeclarationAccess();
	}
	
	public ParserRule getBindingIdentifierAsVariableDeclarationRule() {
		return getBindingIdentifierAsVariableDeclarationAccess().getRule();
	}
	
	///**
	// * The AutomaticSemicolonInjector rewrites the antlr grammar for this rule to inject the promotion of EOL to a statement delimiter.
	// */ ContinueStatement <Yield>:
	//	{ContinueStatement} 'continue' label=[LabelledStatement|BindingIdentifier<Yield>]? Semi;
	public N4JSGrammarAccess.ContinueStatementElements getContinueStatementAccess() {
		return gaN4JS.getContinueStatementAccess();
	}
	
	public ParserRule getContinueStatementRule() {
		return getContinueStatementAccess().getRule();
	}
	
	///**
	// * The AutomaticSemicolonInjector rewrites the antlr grammar for this rule to inject the promotion of EOL to a statement delimiter.
	// */ BreakStatement <Yield>:
	//	{BreakStatement} 'break' label=[LabelledStatement|BindingIdentifier<Yield>]? Semi;
	public N4JSGrammarAccess.BreakStatementElements getBreakStatementAccess() {
		return gaN4JS.getBreakStatementAccess();
	}
	
	public ParserRule getBreakStatementRule() {
		return getBreakStatementAccess().getRule();
	}
	
	///**
	// * The AutomaticSemicolonInjector rewrites the antlr grammar for this rule to inject the promotion of EOL to a statement delimiter.
	// */ ReturnStatement <Yield>:
	//	{ReturnStatement} 'return' expression=Expression<In=true,Yield>? Semi;
	public N4JSGrammarAccess.ReturnStatementElements getReturnStatementAccess() {
		return gaN4JS.getReturnStatementAccess();
	}
	
	public ParserRule getReturnStatementRule() {
		return getReturnStatementAccess().getRule();
	}
	
	//WithStatement <Yield>:
	//	'with' '(' expression=Expression<In=true,Yield> ')' statement=Statement<Yield>;
	public N4JSGrammarAccess.WithStatementElements getWithStatementAccess() {
		return gaN4JS.getWithStatementAccess();
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
	public N4JSGrammarAccess.SwitchStatementElements getSwitchStatementAccess() {
		return gaN4JS.getSwitchStatementAccess();
	}
	
	public ParserRule getSwitchStatementRule() {
		return getSwitchStatementAccess().getRule();
	}
	
	//CaseClause <Yield>:
	//	'case' expression=Expression<In=true,Yield> ':' statements+=Statement<Yield>*;
	public N4JSGrammarAccess.CaseClauseElements getCaseClauseAccess() {
		return gaN4JS.getCaseClauseAccess();
	}
	
	public ParserRule getCaseClauseRule() {
		return getCaseClauseAccess().getRule();
	}
	
	//DefaultClause <Yield>:
	//	{DefaultClause} 'default' ':' statements+=Statement<Yield>*;
	public N4JSGrammarAccess.DefaultClauseElements getDefaultClauseAccess() {
		return gaN4JS.getDefaultClauseAccess();
	}
	
	public ParserRule getDefaultClauseRule() {
		return getDefaultClauseAccess().getRule();
	}
	
	///**
	// * Simplified: [ECM15] distinguishes between BindingIdentifier and LabelIdentifier which are effectively the same
	// */ LabelledStatement <Yield>:
	//	=> (name=BindingIdentifier<Yield> ':') statement=Statement<Yield>;
	public N4JSGrammarAccess.LabelledStatementElements getLabelledStatementAccess() {
		return gaN4JS.getLabelledStatementAccess();
	}
	
	public ParserRule getLabelledStatementRule() {
		return getLabelledStatementAccess().getRule();
	}
	
	//// This is rewritten by the AutomaticSemicolonInjector (see above)
	//ThrowStatement <Yield>:
	//	'throw' expression=Expression<In=true,Yield> Semi;
	public N4JSGrammarAccess.ThrowStatementElements getThrowStatementAccess() {
		return gaN4JS.getThrowStatementAccess();
	}
	
	public ParserRule getThrowStatementRule() {
		return getThrowStatementAccess().getRule();
	}
	
	//TryStatement <Yield>:
	//	'try' block=Block<Yield> (catch=CatchBlock<Yield> finally=FinallyBlock<Yield>? | finally=FinallyBlock<Yield>);
	public N4JSGrammarAccess.TryStatementElements getTryStatementAccess() {
		return gaN4JS.getTryStatementAccess();
	}
	
	public ParserRule getTryStatementRule() {
		return getTryStatementAccess().getRule();
	}
	
	//CatchBlock <Yield>:
	//	{CatchBlock} 'catch' '(' catchVariable=CatchVariable<Yield> ')' block=Block<Yield>;
	public N4JSGrammarAccess.CatchBlockElements getCatchBlockAccess() {
		return gaN4JS.getCatchBlockAccess();
	}
	
	public ParserRule getCatchBlockRule() {
		return getCatchBlockAccess().getRule();
	}
	
	///**
	// * CatchVariable must not have a type reference, this is tested during validation (to enable better error messages).
	// */ CatchVariable <Yield>:
	//	=> bindingPattern=BindingPattern<Yield> | => (name=BindingIdentifier<Yield> -> ColonSepDeclaredTypeRef) |
	//	BogusTypeRefFragment? name=BindingIdentifier<Yield>;
	public N4JSGrammarAccess.CatchVariableElements getCatchVariableAccess() {
		return gaN4JS.getCatchVariableAccess();
	}
	
	public ParserRule getCatchVariableRule() {
		return getCatchVariableAccess().getRule();
	}
	
	//FinallyBlock <Yield>:
	//	{FinallyBlock} 'finally' block=Block<Yield>;
	public N4JSGrammarAccess.FinallyBlockElements getFinallyBlockAccess() {
		return gaN4JS.getFinallyBlockAccess();
	}
	
	public ParserRule getFinallyBlockRule() {
		return getFinallyBlockAccess().getRule();
	}
	
	///**
	// * This is rewritten by the AutomaticSemicolonInjector (see above)
	// */ DebuggerStatement:
	//	{DebuggerStatement} 'debugger' Semi;
	public N4JSGrammarAccess.DebuggerStatementElements getDebuggerStatementAccess() {
		return gaN4JS.getDebuggerStatementAccess();
	}
	
	public ParserRule getDebuggerStatementRule() {
		return getDebuggerStatementAccess().getRule();
	}
	
	//ParenExpression <Yield>:
	//	'(' expression=Expression<In=true,Yield> ')';
	public N4JSGrammarAccess.ParenExpressionElements getParenExpressionAccess() {
		return gaN4JS.getParenExpressionAccess();
	}
	
	public ParserRule getParenExpressionRule() {
		return getParenExpressionAccess().getRule();
	}
	
	//IdentifierRef <Yield>:
	//	id=[types::IdentifiableElement|BindingIdentifier<Yield>];
	public N4JSGrammarAccess.IdentifierRefElements getIdentifierRefAccess() {
		return gaN4JS.getIdentifierRefAccess();
	}
	
	public ParserRule getIdentifierRefRule() {
		return getIdentifierRefAccess().getRule();
	}
	
	//SuperLiteral:
	//	{SuperLiteral} 'super';
	public N4JSGrammarAccess.SuperLiteralElements getSuperLiteralAccess() {
		return gaN4JS.getSuperLiteralAccess();
	}
	
	public ParserRule getSuperLiteralRule() {
		return getSuperLiteralAccess().getRule();
	}
	
	//ThisLiteral:
	//	{ThisLiteral} 'this';
	public N4JSGrammarAccess.ThisLiteralElements getThisLiteralAccess() {
		return gaN4JS.getThisLiteralAccess();
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
	public N4JSGrammarAccess.ArrayLiteralElements getArrayLiteralAccess() {
		return gaN4JS.getArrayLiteralAccess();
	}
	
	public ParserRule getArrayLiteralRule() {
		return getArrayLiteralAccess().getRule();
	}
	
	///**
	// * This array element is used to pad the remaining elements, e.g. to get the
	// * length and index right
	// */ ArrayPadding ArrayElement:
	//	{ArrayPadding} ',';
	public N4JSGrammarAccess.ArrayPaddingElements getArrayPaddingAccess() {
		return gaN4JS.getArrayPaddingAccess();
	}
	
	public ParserRule getArrayPaddingRule() {
		return getArrayPaddingAccess().getRule();
	}
	
	//ArrayElement <Yield>:
	//	{ArrayElement} spread?='...'? expression=AssignmentExpression<In=true,Yield>;
	public N4JSGrammarAccess.ArrayElementElements getArrayElementAccess() {
		return gaN4JS.getArrayElementAccess();
	}
	
	public ParserRule getArrayElementRule() {
		return getArrayElementAccess().getRule();
	}
	
	//ObjectLiteral <Yield>:
	//	{ObjectLiteral}
	//	'{' (propertyAssignments+=PropertyAssignment<Yield> (',' propertyAssignments+=PropertyAssignment<Yield>)* ','?)?
	//	'}';
	public N4JSGrammarAccess.ObjectLiteralElements getObjectLiteralAccess() {
		return gaN4JS.getObjectLiteralAccess();
	}
	
	public ParserRule getObjectLiteralRule() {
		return getObjectLiteralAccess().getRule();
	}
	
	//PropertyAssignment <Yield>:
	//	AnnotatedPropertyAssignment<Yield> | PropertyNameValuePair<Yield> | PropertyGetterDeclaration<Yield> |
	//	PropertySetterDeclaration<Yield> | PropertyMethodDeclaration<Yield> | PropertyNameValuePairSingleName<Yield>;
	public N4JSGrammarAccess.PropertyAssignmentElements getPropertyAssignmentAccess() {
		return gaN4JS.getPropertyAssignmentAccess();
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
	public N4JSGrammarAccess.AnnotatedPropertyAssignmentElements getAnnotatedPropertyAssignmentAccess() {
		return gaN4JS.getAnnotatedPropertyAssignmentAccess();
	}
	
	public ParserRule getAnnotatedPropertyAssignmentRule() {
		return getAnnotatedPropertyAssignmentAccess().getRule();
	}
	
	//PropertyMethodDeclaration <Yield>:
	//	=> ({PropertyMethodDeclaration} TypeVariables? returnTypeRef=TypeRefWithModifiers? (generator?='*'
	//	declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=true> |
	//	declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsAndBody <Generator=false>))
	//	';'?;
	public N4JSGrammarAccess.PropertyMethodDeclarationElements getPropertyMethodDeclarationAccess() {
		return gaN4JS.getPropertyMethodDeclarationAccess();
	}
	
	public ParserRule getPropertyMethodDeclarationRule() {
		return getPropertyMethodDeclarationAccess().getRule();
	}
	
	//PropertyNameValuePair <Yield>:
	//	=> ({PropertyNameValuePair} declaredTypeRef=TypeRefWithModifiers?
	//	declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'?
	//	':') expression=AssignmentExpression<In=true,Yield>;
	public N4JSGrammarAccess.PropertyNameValuePairElements getPropertyNameValuePairAccess() {
		return gaN4JS.getPropertyNameValuePairAccess();
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
	public N4JSGrammarAccess.PropertyNameValuePairSingleNameElements getPropertyNameValuePairSingleNameAccess() {
		return gaN4JS.getPropertyNameValuePairSingleNameAccess();
	}
	
	public ParserRule getPropertyNameValuePairSingleNameRule() {
		return getPropertyNameValuePairSingleNameAccess().getRule();
	}
	
	//PropertyGetterDeclaration <Yield>:
	//	=> ({PropertyGetterDeclaration} GetterHeader<Yield>) body=Block<Yield=false>;
	public N4JSGrammarAccess.PropertyGetterDeclarationElements getPropertyGetterDeclarationAccess() {
		return gaN4JS.getPropertyGetterDeclarationAccess();
	}
	
	public ParserRule getPropertyGetterDeclarationRule() {
		return getPropertyGetterDeclarationAccess().getRule();
	}
	
	//PropertySetterDeclaration <Yield>:
	//	=> ({PropertySetterDeclaration}
	//	'set'
	//	-> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'?
	//	'(' fpar=FormalParameter<Yield> ')' body=Block<Yield=false>;
	public N4JSGrammarAccess.PropertySetterDeclarationElements getPropertySetterDeclarationAccess() {
		return gaN4JS.getPropertySetterDeclarationAccess();
	}
	
	public ParserRule getPropertySetterDeclarationRule() {
		return getPropertySetterDeclarationAccess().getRule();
	}
	
	///* Left-hand-side expressions (11.2) [ECM11]
	// * Heavily refactored to make them LL(*) compliant.
	// */ ParameterizedCallExpression <Yield>:
	//	ConcreteTypeArguments
	//	target=IdentifierRef<Yield> ArgumentsWithParentheses<Yield>;
	public N4JSGrammarAccess.ParameterizedCallExpressionElements getParameterizedCallExpressionAccess() {
		return gaN4JS.getParameterizedCallExpressionAccess();
	}
	
	public ParserRule getParameterizedCallExpressionRule() {
		return getParameterizedCallExpressionAccess().getRule();
	}
	
	//fragment ConcreteTypeArguments *:
	//	'<' typeArgs+=TypeRef (',' typeArgs+=TypeRef)* '>';
	public N4JSGrammarAccess.ConcreteTypeArgumentsElements getConcreteTypeArgumentsAccess() {
		return gaN4JS.getConcreteTypeArgumentsAccess();
	}
	
	public ParserRule getConcreteTypeArgumentsRule() {
		return getConcreteTypeArgumentsAccess().getRule();
	}
	
	//LeftHandSideExpression <Yield Expression:
	//	MemberExpression<Yield> ({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield>
	//	({ParameterizedCallExpression.target=current} ArgumentsWithParentheses<Yield> |
	//	{IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
	//	{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> | ->
	//	({TaggedTemplateString.target=current} template=TemplateLiteral<Yield>))*)?;
	public N4JSGrammarAccess.LeftHandSideExpressionElements getLeftHandSideExpressionAccess() {
		return gaN4JS.getLeftHandSideExpressionAccess();
	}
	
	public ParserRule getLeftHandSideExpressionRule() {
		return getLeftHandSideExpressionAccess().getRule();
	}
	
	//fragment ArgumentsWithParentheses <Yield> *:
	//	'(' Arguments<Yield>? ')';
	public N4JSGrammarAccess.ArgumentsWithParenthesesElements getArgumentsWithParenthesesAccess() {
		return gaN4JS.getArgumentsWithParenthesesAccess();
	}
	
	public ParserRule getArgumentsWithParenthesesRule() {
		return getArgumentsWithParenthesesAccess().getRule();
	}
	
	//fragment Arguments <Yield> *:
	//	arguments+=Argument<Yield> (',' arguments+=Argument<Yield>)*;
	public N4JSGrammarAccess.ArgumentsElements getArgumentsAccess() {
		return gaN4JS.getArgumentsAccess();
	}
	
	public ParserRule getArgumentsRule() {
		return getArgumentsAccess().getRule();
	}
	
	//Argument <Yield>:
	//	spread?='...'? expression=AssignmentExpression<In=true,Yield>;
	public N4JSGrammarAccess.ArgumentElements getArgumentAccess() {
		return gaN4JS.getArgumentAccess();
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
	//	| super::PrimaryExpression<Yield> ({IndexedAccessExpression.target=current} IndexedAccessExpressionTail<Yield> |
	//	{ParameterizedPropertyAccessExpression.target=current} ParameterizedPropertyAccessExpressionTail<Yield> |
	//	{TaggedTemplateString.target=current} template=TemplateLiteral<Yield>)*;
	public N4JSGrammarAccess.MemberExpressionElements getMemberExpressionAccess() {
		return gaN4JS.getMemberExpressionAccess();
	}
	
	public ParserRule getMemberExpressionRule() {
		return getMemberExpressionAccess().getRule();
	}
	
	//fragment IndexedAccessExpressionTail <Yield> *:
	//	'[' index=Expression<In=true,Yield> ']';
	public N4JSGrammarAccess.IndexedAccessExpressionTailElements getIndexedAccessExpressionTailAccess() {
		return gaN4JS.getIndexedAccessExpressionTailAccess();
	}
	
	public ParserRule getIndexedAccessExpressionTailRule() {
		return getIndexedAccessExpressionTailAccess().getRule();
	}
	
	//fragment ParameterizedPropertyAccessExpressionTail <Yield> *:
	//	'.' ConcreteTypeArguments? property=[types::IdentifiableElement|IdentifierName];
	public N4JSGrammarAccess.ParameterizedPropertyAccessExpressionTailElements getParameterizedPropertyAccessExpressionTailAccess() {
		return gaN4JS.getParameterizedPropertyAccessExpressionTailAccess();
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
	public N4JSGrammarAccess.PostfixExpressionElements getPostfixExpressionAccess() {
		return gaN4JS.getPostfixExpressionAccess();
	}
	
	public ParserRule getPostfixExpressionRule() {
		return getPostfixExpressionAccess().getRule();
	}
	
	//enum PostfixOperator:
	//	inc='++' | dec='--';
	public N4JSGrammarAccess.PostfixOperatorElements getPostfixOperatorAccess() {
		return gaN4JS.getPostfixOperatorAccess();
	}
	
	public EnumRule getPostfixOperatorRule() {
		return getPostfixOperatorAccess().getRule();
	}
	
	///* Cast expression (N4JS 6.2.3) */ CastExpression <Yield Expression:
	//	PostfixExpression<Yield> (=> ({CastExpression.expression=current} 'as') targetTypeRef=TypeRefForCast)?;
	public N4JSGrammarAccess.CastExpressionElements getCastExpressionAccess() {
		return gaN4JS.getCastExpressionAccess();
	}
	
	public ParserRule getCastExpressionRule() {
		return getCastExpressionAccess().getRule();
	}
	
	///* Unary operators ([ECM11] 11.4) */ UnaryExpression <Yield Expression:
	//	CastExpression<Yield> | {UnaryExpression} op=UnaryOperator expression=UnaryExpression<Yield>;
	public N4JSGrammarAccess.UnaryExpressionElements getUnaryExpressionAccess() {
		return gaN4JS.getUnaryExpressionAccess();
	}
	
	public ParserRule getUnaryExpressionRule() {
		return getUnaryExpressionAccess().getRule();
	}
	
	//enum UnaryOperator:
	//	delete | void | typeof | inc='++' | dec='--' | pos='+' | neg='-' | inv='~' | not='!';
	public N4JSGrammarAccess.UnaryOperatorElements getUnaryOperatorAccess() {
		return gaN4JS.getUnaryOperatorAccess();
	}
	
	public EnumRule getUnaryOperatorRule() {
		return getUnaryOperatorAccess().getRule();
	}
	
	///* Multiplicative operators ([ECM11] 11.5) */ MultiplicativeExpression <Yield Expression:
	//	UnaryExpression<Yield> (=> ({MultiplicativeExpression.lhs=current} op=MultiplicativeOperator)
	//	rhs=UnaryExpression<Yield>)*;
	public N4JSGrammarAccess.MultiplicativeExpressionElements getMultiplicativeExpressionAccess() {
		return gaN4JS.getMultiplicativeExpressionAccess();
	}
	
	public ParserRule getMultiplicativeExpressionRule() {
		return getMultiplicativeExpressionAccess().getRule();
	}
	
	//enum MultiplicativeOperator:
	//	times='*' | div='/' | mod='%';
	public N4JSGrammarAccess.MultiplicativeOperatorElements getMultiplicativeOperatorAccess() {
		return gaN4JS.getMultiplicativeOperatorAccess();
	}
	
	public EnumRule getMultiplicativeOperatorRule() {
		return getMultiplicativeOperatorAccess().getRule();
	}
	
	///* Additive operators ([ECM11] 11.6) */ AdditiveExpression <Yield Expression:
	//	MultiplicativeExpression<Yield> (=> ({AdditiveExpression.lhs=current} op=AdditiveOperator)
	//	rhs=MultiplicativeExpression<Yield>)*;
	public N4JSGrammarAccess.AdditiveExpressionElements getAdditiveExpressionAccess() {
		return gaN4JS.getAdditiveExpressionAccess();
	}
	
	public ParserRule getAdditiveExpressionRule() {
		return getAdditiveExpressionAccess().getRule();
	}
	
	//enum AdditiveOperator:
	//	add='+' | sub='-';
	public N4JSGrammarAccess.AdditiveOperatorElements getAdditiveOperatorAccess() {
		return gaN4JS.getAdditiveOperatorAccess();
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
	public N4JSGrammarAccess.ShiftExpressionElements getShiftExpressionAccess() {
		return gaN4JS.getShiftExpressionAccess();
	}
	
	public ParserRule getShiftExpressionRule() {
		return getShiftExpressionAccess().getRule();
	}
	
	///**  solve conflict with generics, e.g., List<List<C>> */ ShiftOperator ShiftOperator:
	//	'>' '>' '>'?
	//	| '<<';
	public N4JSGrammarAccess.ShiftOperatorElements getShiftOperatorAccess() {
		return gaN4JS.getShiftOperatorAccess();
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
	public N4JSGrammarAccess.RelationalExpressionElements getRelationalExpressionAccess() {
		return gaN4JS.getRelationalExpressionAccess();
	}
	
	public ParserRule getRelationalExpressionRule() {
		return getRelationalExpressionAccess().getRule();
	}
	
	//RelationalOperator <In RelationalOperator:
	//	'<' | '>' | '<=' | '>=' | 'instanceof' | <In> 'in';
	public N4JSGrammarAccess.RelationalOperatorElements getRelationalOperatorAccess() {
		return gaN4JS.getRelationalOperatorAccess();
	}
	
	public ParserRule getRelationalOperatorRule() {
		return getRelationalOperatorAccess().getRule();
	}
	
	//// Equality operators (11.9)
	//EqualityExpression <In, Yield Expression:
	//	RelationalExpression<In,Yield> (=> ({EqualityExpression.lhs=current} op=EqualityOperator)
	//	rhs=RelationalExpression<In,Yield>)*;
	public N4JSGrammarAccess.EqualityExpressionElements getEqualityExpressionAccess() {
		return gaN4JS.getEqualityExpressionAccess();
	}
	
	public ParserRule getEqualityExpressionRule() {
		return getEqualityExpressionAccess().getRule();
	}
	
	//enum EqualityOperator:
	//	same='===' | nsame='!==' | eq='==' | neq='!=';
	public N4JSGrammarAccess.EqualityOperatorElements getEqualityOperatorAccess() {
		return gaN4JS.getEqualityOperatorAccess();
	}
	
	public EnumRule getEqualityOperatorRule() {
		return getEqualityOperatorAccess().getRule();
	}
	
	//// Binary bitwise operators (11.10, N4JS Spec 6.1.17)
	//BitwiseANDExpression <In, Yield Expression:
	//	EqualityExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseANDOperator)
	//	rhs=EqualityExpression<In,Yield>)*;
	public N4JSGrammarAccess.BitwiseANDExpressionElements getBitwiseANDExpressionAccess() {
		return gaN4JS.getBitwiseANDExpressionAccess();
	}
	
	public ParserRule getBitwiseANDExpressionRule() {
		return getBitwiseANDExpressionAccess().getRule();
	}
	
	//BitwiseANDOperator BinaryBitwiseOperator:
	//	'&';
	public N4JSGrammarAccess.BitwiseANDOperatorElements getBitwiseANDOperatorAccess() {
		return gaN4JS.getBitwiseANDOperatorAccess();
	}
	
	public ParserRule getBitwiseANDOperatorRule() {
		return getBitwiseANDOperatorAccess().getRule();
	}
	
	//BitwiseXORExpression <In, Yield Expression:
	//	BitwiseANDExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseXOROperator)
	//	rhs=BitwiseANDExpression<In,Yield>)*;
	public N4JSGrammarAccess.BitwiseXORExpressionElements getBitwiseXORExpressionAccess() {
		return gaN4JS.getBitwiseXORExpressionAccess();
	}
	
	public ParserRule getBitwiseXORExpressionRule() {
		return getBitwiseXORExpressionAccess().getRule();
	}
	
	//BitwiseXOROperator BinaryBitwiseOperator:
	//	'^';
	public N4JSGrammarAccess.BitwiseXOROperatorElements getBitwiseXOROperatorAccess() {
		return gaN4JS.getBitwiseXOROperatorAccess();
	}
	
	public ParserRule getBitwiseXOROperatorRule() {
		return getBitwiseXOROperatorAccess().getRule();
	}
	
	//BitwiseORExpression <In, Yield Expression:
	//	BitwiseXORExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator)
	//	rhs=BitwiseXORExpression<In,Yield>)*;
	public N4JSGrammarAccess.BitwiseORExpressionElements getBitwiseORExpressionAccess() {
		return gaN4JS.getBitwiseORExpressionAccess();
	}
	
	public ParserRule getBitwiseORExpressionRule() {
		return getBitwiseORExpressionAccess().getRule();
	}
	
	//BitwiseOROperator BinaryBitwiseOperator:
	//	'|';
	public N4JSGrammarAccess.BitwiseOROperatorElements getBitwiseOROperatorAccess() {
		return gaN4JS.getBitwiseOROperatorAccess();
	}
	
	public ParserRule getBitwiseOROperatorRule() {
		return getBitwiseOROperatorAccess().getRule();
	}
	
	//// $<Binary logical operators ([ECM11] 11.11)
	//LogicalANDExpression <In, Yield Expression:
	//	BitwiseORExpression<In,Yield> (=> ({BinaryLogicalExpression.lhs=current} op=LogicalANDOperator)
	//	rhs=BitwiseORExpression<In,Yield>)*;
	public N4JSGrammarAccess.LogicalANDExpressionElements getLogicalANDExpressionAccess() {
		return gaN4JS.getLogicalANDExpressionAccess();
	}
	
	public ParserRule getLogicalANDExpressionRule() {
		return getLogicalANDExpressionAccess().getRule();
	}
	
	//LogicalANDOperator BinaryLogicalOperator:
	//	'&&';
	public N4JSGrammarAccess.LogicalANDOperatorElements getLogicalANDOperatorAccess() {
		return gaN4JS.getLogicalANDOperatorAccess();
	}
	
	public ParserRule getLogicalANDOperatorRule() {
		return getLogicalANDOperatorAccess().getRule();
	}
	
	//LogicalORExpression <In, Yield Expression:
	//	LogicalANDExpression<In,Yield> (=> ({BinaryLogicalExpression.lhs=current} op=LogicalOROperator)
	//	rhs=LogicalANDExpression<In,Yield>)*;
	public N4JSGrammarAccess.LogicalORExpressionElements getLogicalORExpressionAccess() {
		return gaN4JS.getLogicalORExpressionAccess();
	}
	
	public ParserRule getLogicalORExpressionRule() {
		return getLogicalORExpressionAccess().getRule();
	}
	
	//LogicalOROperator BinaryLogicalOperator:
	//	'||';
	public N4JSGrammarAccess.LogicalOROperatorElements getLogicalOROperatorAccess() {
		return gaN4JS.getLogicalOROperatorAccess();
	}
	
	public ParserRule getLogicalOROperatorRule() {
		return getLogicalOROperatorAccess().getRule();
	}
	
	///**
	// * Conditional operator ([ECM11] 11.12)
	// */ ConditionalExpression <In, Yield Expression:
	//	LogicalORExpression<In,Yield> (=> ({ConditionalExpression.expression=current} '?')
	//	trueExpression=AssignmentExpression<In=true,Yield> ':' falseExpression=AssignmentExpression<In,Yield>)?;
	public N4JSGrammarAccess.ConditionalExpressionElements getConditionalExpressionAccess() {
		return gaN4JS.getConditionalExpressionAccess();
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
	public N4JSGrammarAccess.AssignmentExpressionElements getAssignmentExpressionAccess() {
		return gaN4JS.getAssignmentExpressionAccess();
	}
	
	public ParserRule getAssignmentExpressionRule() {
		return getAssignmentExpressionAccess().getRule();
	}
	
	//YieldExpression <In Expression:
	//	{YieldExpression} 'yield' => many?='*'? -> expression=AssignmentExpression<In,Yield=true>?;
	public N4JSGrammarAccess.YieldExpressionElements getYieldExpressionAccess() {
		return gaN4JS.getYieldExpressionAccess();
	}
	
	public ParserRule getYieldExpressionRule() {
		return getYieldExpressionAccess().getRule();
	}
	
	//AssignmentOperator AssignmentOperator:
	//	'=' | '*=' | '/=' | '%=' | '+=' | '-='
	//	| '<<='
	//	| '>' '>'? '>='
	//	| '&=' | '^=' | '|=';
	public N4JSGrammarAccess.AssignmentOperatorElements getAssignmentOperatorAccess() {
		return gaN4JS.getAssignmentOperatorAccess();
	}
	
	public ParserRule getAssignmentOperatorRule() {
		return getAssignmentOperatorAccess().getRule();
	}
	
	///*
	// * await should mimic precedence of 'yield' in [ECM15] (because it will be transpiled into a 'yield')
	// */ AwaitExpression <In, Yield Expression:
	//	=> ({AwaitExpression} 'await') expression=AssignmentExpression<In,Yield>;
	public N4JSGrammarAccess.AwaitExpressionElements getAwaitExpressionAccess() {
		return gaN4JS.getAwaitExpressionAccess();
	}
	
	public ParserRule getAwaitExpressionRule() {
		return getAwaitExpressionAccess().getRule();
	}
	
	//PromisifyExpression <In, Yield Expression:
	//	=> ({PromisifyExpression} '@' 'Promisify') expression=AssignmentExpression<In,Yield>;
	public N4JSGrammarAccess.PromisifyExpressionElements getPromisifyExpressionAccess() {
		return gaN4JS.getPromisifyExpressionAccess();
	}
	
	public ParserRule getPromisifyExpressionRule() {
		return getPromisifyExpressionAccess().getRule();
	}
	
	//// $<Comma operator (11.14)
	//Expression <In, Yield>:
	//	AssignmentExpression<In,Yield> ({CommaExpression.exprs+=current} ',' exprs+=AssignmentExpression<In,Yield> (','
	//	exprs+=AssignmentExpression<In,Yield>)*)?;
	public N4JSGrammarAccess.ExpressionElements getExpressionAccess() {
		return gaN4JS.getExpressionAccess();
	}
	
	public ParserRule getExpressionRule() {
		return getExpressionAccess().getRule();
	}
	
	//TemplateLiteral <Yield>:
	//	{TemplateLiteral} (segments+=NoSubstitutionTemplate
	//	| segments+=TemplateHead segments+=Expression<In=true,Yield>? TemplateExpressionEnd (segments+=TemplateMiddle
	//	segments+=Expression<In=true,Yield>? TemplateExpressionEnd)*
	//	segments+=TemplateTail);
	public N4JSGrammarAccess.TemplateLiteralElements getTemplateLiteralAccess() {
		return gaN4JS.getTemplateLiteralAccess();
	}
	
	public ParserRule getTemplateLiteralRule() {
		return getTemplateLiteralAccess().getRule();
	}
	
	//TemplateExpressionEnd:
	//	'}';
	public N4JSGrammarAccess.TemplateExpressionEndElements getTemplateExpressionEndAccess() {
		return gaN4JS.getTemplateExpressionEndAccess();
	}
	
	public ParserRule getTemplateExpressionEndRule() {
		return getTemplateExpressionEndAccess().getRule();
	}
	
	//NoSubstitutionTemplate TemplateSegment:
	//	{TemplateSegment} rawValue=NO_SUBSTITUTION_TEMPLATE_LITERAL;
	public N4JSGrammarAccess.NoSubstitutionTemplateElements getNoSubstitutionTemplateAccess() {
		return gaN4JS.getNoSubstitutionTemplateAccess();
	}
	
	public ParserRule getNoSubstitutionTemplateRule() {
		return getNoSubstitutionTemplateAccess().getRule();
	}
	
	//TemplateHead TemplateSegment:
	//	{TemplateSegment} rawValue=TEMPLATE_HEAD;
	public N4JSGrammarAccess.TemplateHeadElements getTemplateHeadAccess() {
		return gaN4JS.getTemplateHeadAccess();
	}
	
	public ParserRule getTemplateHeadRule() {
		return getTemplateHeadAccess().getRule();
	}
	
	//TemplateTail TemplateSegment:
	//	{TemplateSegment} rawValue=TemplateTailLiteral;
	public N4JSGrammarAccess.TemplateTailElements getTemplateTailAccess() {
		return gaN4JS.getTemplateTailAccess();
	}
	
	public ParserRule getTemplateTailRule() {
		return getTemplateTailAccess().getRule();
	}
	
	//TemplateMiddle TemplateSegment:
	//	{TemplateSegment} rawValue=TemplateMiddleLiteral;
	public N4JSGrammarAccess.TemplateMiddleElements getTemplateMiddleAccess() {
		return gaN4JS.getTemplateMiddleAccess();
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
	public N4JSGrammarAccess.LiteralElements getLiteralAccess() {
		return gaN4JS.getLiteralAccess();
	}
	
	public ParserRule getLiteralRule() {
		return getLiteralAccess().getRule();
	}
	
	//NullLiteral:
	//	{NullLiteral} 'null';
	public N4JSGrammarAccess.NullLiteralElements getNullLiteralAccess() {
		return gaN4JS.getNullLiteralAccess();
	}
	
	public ParserRule getNullLiteralRule() {
		return getNullLiteralAccess().getRule();
	}
	
	//BooleanLiteral:
	//	{BooleanLiteral} (true?='true' | 'false');
	public N4JSGrammarAccess.BooleanLiteralElements getBooleanLiteralAccess() {
		return gaN4JS.getBooleanLiteralAccess();
	}
	
	public ParserRule getBooleanLiteralRule() {
		return getBooleanLiteralAccess().getRule();
	}
	
	//StringLiteral:
	//	value=STRING;
	public N4JSGrammarAccess.StringLiteralElements getStringLiteralAccess() {
		return gaN4JS.getStringLiteralAccess();
	}
	
	public ParserRule getStringLiteralRule() {
		return getStringLiteralAccess().getRule();
	}
	
	//NumericLiteral:
	//	DoubleLiteral | IntLiteral | BinaryIntLiteral | OctalIntLiteral | LegacyOctalIntLiteral | HexIntLiteral |
	//	ScientificIntLiteral;
	public N4JSGrammarAccess.NumericLiteralElements getNumericLiteralAccess() {
		return gaN4JS.getNumericLiteralAccess();
	}
	
	public ParserRule getNumericLiteralRule() {
		return getNumericLiteralAccess().getRule();
	}
	
	//DoubleLiteral:
	//	value=DOUBLE;
	public N4JSGrammarAccess.DoubleLiteralElements getDoubleLiteralAccess() {
		return gaN4JS.getDoubleLiteralAccess();
	}
	
	public ParserRule getDoubleLiteralRule() {
		return getDoubleLiteralAccess().getRule();
	}
	
	//IntLiteral:
	//	value=INT;
	public N4JSGrammarAccess.IntLiteralElements getIntLiteralAccess() {
		return gaN4JS.getIntLiteralAccess();
	}
	
	public ParserRule getIntLiteralRule() {
		return getIntLiteralAccess().getRule();
	}
	
	//OctalIntLiteral:
	//	value=OCTAL_INT;
	public N4JSGrammarAccess.OctalIntLiteralElements getOctalIntLiteralAccess() {
		return gaN4JS.getOctalIntLiteralAccess();
	}
	
	public ParserRule getOctalIntLiteralRule() {
		return getOctalIntLiteralAccess().getRule();
	}
	
	//LegacyOctalIntLiteral:
	//	value=LEGACY_OCTAL_INT;
	public N4JSGrammarAccess.LegacyOctalIntLiteralElements getLegacyOctalIntLiteralAccess() {
		return gaN4JS.getLegacyOctalIntLiteralAccess();
	}
	
	public ParserRule getLegacyOctalIntLiteralRule() {
		return getLegacyOctalIntLiteralAccess().getRule();
	}
	
	//HexIntLiteral:
	//	value=HEX_INT;
	public N4JSGrammarAccess.HexIntLiteralElements getHexIntLiteralAccess() {
		return gaN4JS.getHexIntLiteralAccess();
	}
	
	public ParserRule getHexIntLiteralRule() {
		return getHexIntLiteralAccess().getRule();
	}
	
	//BinaryIntLiteral:
	//	value=BINARY_INT;
	public N4JSGrammarAccess.BinaryIntLiteralElements getBinaryIntLiteralAccess() {
		return gaN4JS.getBinaryIntLiteralAccess();
	}
	
	public ParserRule getBinaryIntLiteralRule() {
		return getBinaryIntLiteralAccess().getRule();
	}
	
	//ScientificIntLiteral:
	//	value=SCIENTIFIC_INT;
	public N4JSGrammarAccess.ScientificIntLiteralElements getScientificIntLiteralAccess() {
		return gaN4JS.getScientificIntLiteralAccess();
	}
	
	public ParserRule getScientificIntLiteralRule() {
		return getScientificIntLiteralAccess().getRule();
	}
	
	//RegularExpressionLiteral:
	//	value=REGEX_LITERAL;
	public N4JSGrammarAccess.RegularExpressionLiteralElements getRegularExpressionLiteralAccess() {
		return gaN4JS.getRegularExpressionLiteralAccess();
	}
	
	public ParserRule getRegularExpressionLiteralRule() {
		return getRegularExpressionLiteralAccess().getRule();
	}
	
	//NumericLiteralAsString:
	//	DOUBLE | INT | OCTAL_INT | HEX_INT | SCIENTIFIC_INT;
	public N4JSGrammarAccess.NumericLiteralAsStringElements getNumericLiteralAsStringAccess() {
		return gaN4JS.getNumericLiteralAsStringAccess();
	}
	
	public ParserRule getNumericLiteralAsStringRule() {
		return getNumericLiteralAsStringAccess().getRule();
	}
	
	//IdentifierOrThis:
	//	IDENTIFIER
	//	| 'This'
	//	| 'Promisify'
	//	| 'target';
	public N4JSGrammarAccess.IdentifierOrThisElements getIdentifierOrThisAccess() {
		return gaN4JS.getIdentifierOrThisAccess();
	}
	
	public ParserRule getIdentifierOrThisRule() {
		return getIdentifierOrThisAccess().getRule();
	}
	
	//AnnotationName:
	//	IDENTIFIER
	//	| 'This'
	//	| 'target';
	public N4JSGrammarAccess.AnnotationNameElements getAnnotationNameAccess() {
		return gaN4JS.getAnnotationNameAccess();
	}
	
	public ParserRule getAnnotationNameRule() {
		return getAnnotationNameAccess().getRule();
	}
	
	//terminal DOUBLE returns ecore::EBigDecimal:
	//	'.' DECIMAL_DIGIT_FRAGMENT+ EXPONENT_PART?
	//	| DECIMAL_INTEGER_LITERAL_FRAGMENT '.' DECIMAL_DIGIT_FRAGMENT* EXPONENT_PART?;
	public TerminalRule getDOUBLERule() {
		return gaN4JS.getDOUBLERule();
	}
	
	//terminal HEX_INT returns ecore::EBigDecimal:
	//	'0' ('x' | 'X') INT_SUFFIX;
	public TerminalRule getHEX_INTRule() {
		return gaN4JS.getHEX_INTRule();
	}
	
	//terminal BINARY_INT returns ecore::EBigDecimal:
	//	'0' ('b' | 'B') INT_SUFFIX;
	public TerminalRule getBINARY_INTRule() {
		return gaN4JS.getBINARY_INTRule();
	}
	
	//terminal OCTAL_INT returns ecore::EBigDecimal:
	//	'0' ('o' | 'O') INT_SUFFIX;
	public TerminalRule getOCTAL_INTRule() {
		return gaN4JS.getOCTAL_INTRule();
	}
	
	//terminal LEGACY_OCTAL_INT returns ecore::EBigDecimal:
	//	'0' DECIMAL_DIGIT_FRAGMENT INT_SUFFIX;
	public TerminalRule getLEGACY_OCTAL_INTRule() {
		return gaN4JS.getLEGACY_OCTAL_INTRule();
	}
	
	//terminal fragment INT_SUFFIX:
	//	IDENTIFIER_PART*;
	public TerminalRule getINT_SUFFIXRule() {
		return gaN4JS.getINT_SUFFIXRule();
	}
	
	//terminal SCIENTIFIC_INT returns ecore::EBigDecimal:
	//	DECIMAL_INTEGER_LITERAL_FRAGMENT EXPONENT_PART;
	public TerminalRule getSCIENTIFIC_INTRule() {
		return gaN4JS.getSCIENTIFIC_INTRule();
	}
	
	//terminal fragment EXPONENT_PART:
	//	('e' | 'E') SIGNED_INT
	//	| IDENTIFIER;
	public TerminalRule getEXPONENT_PARTRule() {
		return gaN4JS.getEXPONENT_PARTRule();
	}
	
	//terminal fragment SIGNED_INT:
	//	('+' | '-') DECIMAL_DIGIT_FRAGMENT+ IDENTIFIER?;
	public TerminalRule getSIGNED_INTRule() {
		return gaN4JS.getSIGNED_INTRule();
	}
	
	//terminal STRING:
	//	'"' DOUBLE_STRING_CHAR* '"'?
	//	| "'" SINGLE_STRING_CHAR* "'"?;
	public TerminalRule getSTRINGRule() {
		return gaN4JS.getSTRINGRule();
	}
	
	//terminal fragment DOUBLE_STRING_CHAR:
	//	!(LINE_TERMINATOR_FRAGMENT | '"' | '\\') | '\\' (LINE_TERMINATOR_SEQUENCE_FRAGMENT | !LINE_TERMINATOR_FRAGMENT)?;
	public TerminalRule getDOUBLE_STRING_CHARRule() {
		return gaN4JS.getDOUBLE_STRING_CHARRule();
	}
	
	//terminal fragment SINGLE_STRING_CHAR:
	//	!(LINE_TERMINATOR_FRAGMENT | "'" | '\\') | '\\' (LINE_TERMINATOR_SEQUENCE_FRAGMENT | !LINE_TERMINATOR_FRAGMENT)?;
	public TerminalRule getSINGLE_STRING_CHARRule() {
		return gaN4JS.getSINGLE_STRING_CHARRule();
	}
	
	//terminal fragment BACKSLASH_SEQUENCE:
	//	'\\' !LINE_TERMINATOR_FRAGMENT?;
	public TerminalRule getBACKSLASH_SEQUENCERule() {
		return gaN4JS.getBACKSLASH_SEQUENCERule();
	}
	
	//terminal fragment REGEX_CHAR:
	//	!(LINE_TERMINATOR_FRAGMENT | '\\' | '/' | '[') | BACKSLASH_SEQUENCE
	//	| '[' REGEX_CHAR_OR_BRACKET* ']'?;
	public TerminalRule getREGEX_CHARRule() {
		return gaN4JS.getREGEX_CHARRule();
	}
	
	//terminal fragment REGEX_CHAR_OR_BRACKET:
	//	!(LINE_TERMINATOR_FRAGMENT | '\\' | ']') | BACKSLASH_SEQUENCE;
	public TerminalRule getREGEX_CHAR_OR_BRACKETRule() {
		return gaN4JS.getREGEX_CHAR_OR_BRACKETRule();
	}
	
	///**
	// * The regex literal is not very strict in the sense that the trailing parts are optional.
	// * This is to improve the error recovery in the generated lexer and parser. If the trailing slash
	// * was mandatory, the lexer would brick and the parser would not sync properly. Therefore
	// * we rely on value converters and validation to check the regex literals.
	// */ REGEX_LITERAL:
	//	('/' | '/=') REGEX_TAIL?;
	public N4JSGrammarAccess.REGEX_LITERALElements getREGEX_LITERALAccess() {
		return gaN4JS.getREGEX_LITERALAccess();
	}
	
	public ParserRule getREGEX_LITERALRule() {
		return getREGEX_LITERALAccess().getRule();
	}
	
	//terminal fragment ACTUAL_REGEX_TAIL:
	//	REGEX_CHAR+ ('/' IDENTIFIER_PART*)?
	//	| '/' IDENTIFIER_PART* // matches regular expression literals like /=/ or /=/g
	//;
	public TerminalRule getACTUAL_REGEX_TAILRule() {
		return gaN4JS.getACTUAL_REGEX_TAILRule();
	}
	
	//terminal fragment REGEX_START:
	//	'/' | '/=';
	public TerminalRule getREGEX_STARTRule() {
		return gaN4JS.getREGEX_STARTRule();
	}
	
	//terminal REGEX_TAIL:
	//	'//1' // never matched by lexer but required to have a terminal token
	//;
	public TerminalRule getREGEX_TAILRule() {
		return gaN4JS.getREGEX_TAILRule();
	}
	
	//terminal TEMPLATE_HEAD:
	//	"`" TEMPLATE_LITERAL_CHAR* '$'+ '{';
	public TerminalRule getTEMPLATE_HEADRule() {
		return gaN4JS.getTEMPLATE_HEADRule();
	}
	
	//terminal NO_SUBSTITUTION_TEMPLATE_LITERAL:
	//	'`' TEMPLATE_LITERAL_CHAR* '$'* "`"?;
	public TerminalRule getNO_SUBSTITUTION_TEMPLATE_LITERALRule() {
		return gaN4JS.getNO_SUBSTITUTION_TEMPLATE_LITERALRule();
	}
	
	//terminal fragment ACTUAL_TEMPLATE_END:
	//	TEMPLATE_LITERAL_CHAR* ('$'+ ('{' | '`'?) | '`'?);
	public TerminalRule getACTUAL_TEMPLATE_ENDRule() {
		return gaN4JS.getACTUAL_TEMPLATE_ENDRule();
	}
	
	//terminal fragment TEMPLATE_LITERAL_CHAR:
	//	!(LINE_TERMINATOR_FRAGMENT | '`' | '\\' | '$') | '$'+ !('{' | '`' | '$') | LINE_TERMINATOR_SEQUENCE_FRAGMENT
	//	| '\\' (LINE_TERMINATOR_SEQUENCE_FRAGMENT | !LINE_TERMINATOR_FRAGMENT)?;
	public TerminalRule getTEMPLATE_LITERAL_CHARRule() {
		return gaN4JS.getTEMPLATE_LITERAL_CHARRule();
	}
	
	//TemplateTailLiteral:
	//	TEMPLATE_END?;
	public N4JSGrammarAccess.TemplateTailLiteralElements getTemplateTailLiteralAccess() {
		return gaN4JS.getTemplateTailLiteralAccess();
	}
	
	public ParserRule getTemplateTailLiteralRule() {
		return getTemplateTailLiteralAccess().getRule();
	}
	
	//TemplateMiddleLiteral:
	//	TEMPLATE_MIDDLE;
	public N4JSGrammarAccess.TemplateMiddleLiteralElements getTemplateMiddleLiteralAccess() {
		return gaN4JS.getTemplateMiddleLiteralAccess();
	}
	
	public ParserRule getTemplateMiddleLiteralRule() {
		return getTemplateMiddleLiteralAccess().getRule();
	}
	
	//terminal TEMPLATE_MIDDLE:
	//	'//2' // will never be lexed
	//;
	public TerminalRule getTEMPLATE_MIDDLERule() {
		return gaN4JS.getTEMPLATE_MIDDLERule();
	}
	
	//terminal TEMPLATE_END:
	//	'//3' // will never be lexed
	//;
	public TerminalRule getTEMPLATE_ENDRule() {
		return gaN4JS.getTEMPLATE_ENDRule();
	}
	
	//terminal fragment TEMPLATE_CONTINUATION:
	//	'//4' // actually '}'
	//;
	public TerminalRule getTEMPLATE_CONTINUATIONRule() {
		return gaN4JS.getTEMPLATE_CONTINUATIONRule();
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
	public N4JSGrammarAccess.SemiElements getSemiAccess() {
		return gaN4JS.getSemiAccess();
	}
	
	public ParserRule getSemiRule() {
		return getSemiAccess().getRule();
	}
	
	///**
	// * Will be completely replaced during post processing, need some dummy token to be able to define rule.
	// */ fragment NoLineTerminator *:
	//	NO_LINE_TERMINATOR?;
	public N4JSGrammarAccess.NoLineTerminatorElements getNoLineTerminatorAccess() {
		return gaN4JS.getNoLineTerminatorAccess();
	}
	
	public ParserRule getNoLineTerminatorRule() {
		return getNoLineTerminatorAccess().getRule();
	}
	
	//terminal NO_LINE_TERMINATOR:
	//	'//5' // will never be lexed
	//;
	public TerminalRule getNO_LINE_TERMINATORRule() {
		return gaN4JS.getNO_LINE_TERMINATORRule();
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
	public N4JSGrammarAccess.AnnotationElements getAnnotationAccess() {
		return gaN4JS.getAnnotationAccess();
	}
	
	public ParserRule getAnnotationRule() {
		return getAnnotationAccess().getRule();
	}
	
	//ScriptAnnotation Annotation:
	//	'@@' AnnotationNoAtSign;
	public N4JSGrammarAccess.ScriptAnnotationElements getScriptAnnotationAccess() {
		return gaN4JS.getScriptAnnotationAccess();
	}
	
	public ParserRule getScriptAnnotationRule() {
		return getScriptAnnotationAccess().getRule();
	}
	
	//AnnotationNoAtSign Annotation:
	//	name=AnnotationName (=> '(' (args+=AnnotationArgument (',' args+=AnnotationArgument)*)? ')')?;
	public N4JSGrammarAccess.AnnotationNoAtSignElements getAnnotationNoAtSignAccess() {
		return gaN4JS.getAnnotationNoAtSignAccess();
	}
	
	public ParserRule getAnnotationNoAtSignRule() {
		return getAnnotationNoAtSignAccess().getRule();
	}
	
	//AnnotationArgument:
	//	LiteralAnnotationArgument | TypeRefAnnotationArgument;
	public N4JSGrammarAccess.AnnotationArgumentElements getAnnotationArgumentAccess() {
		return gaN4JS.getAnnotationArgumentAccess();
	}
	
	public ParserRule getAnnotationArgumentRule() {
		return getAnnotationArgumentAccess().getRule();
	}
	
	//LiteralAnnotationArgument:
	//	literal=Literal;
	public N4JSGrammarAccess.LiteralAnnotationArgumentElements getLiteralAnnotationArgumentAccess() {
		return gaN4JS.getLiteralAnnotationArgumentAccess();
	}
	
	public ParserRule getLiteralAnnotationArgumentRule() {
		return getLiteralAnnotationArgumentAccess().getRule();
	}
	
	//TypeRefAnnotationArgument:
	//	typeRef=TypeRef;
	public N4JSGrammarAccess.TypeRefAnnotationArgumentElements getTypeRefAnnotationArgumentAccess() {
		return gaN4JS.getTypeRefAnnotationArgumentAccess();
	}
	
	public ParserRule getTypeRefAnnotationArgumentRule() {
		return getTypeRefAnnotationArgumentAccess().getRule();
	}
	
	//TypeRefForCast types::StaticBaseTypeRef:
	//	ParameterizedTypeRef
	//	| ArrayTypeRef
	//	| ThisTypeRef
	//	| TypeTypeRef
	//	| ArrowFunctionTypeExpression
	//	| FunctionTypeExpressionOLD
	//	| UnionTypeExpressionOLD
	//	| IntersectionTypeExpressionOLD;
	public N4JSGrammarAccess.TypeRefForCastElements getTypeRefForCastAccess() {
		return gaN4JS.getTypeRefForCastAccess();
	}
	
	public ParserRule getTypeRefForCastRule() {
		return getTypeRefForCastAccess().getRule();
	}
	
	//AnnotationList:
	//	=> ({AnnotationList} '@' -> annotations+=AnnotationNoAtSign) annotations+=Annotation*;
	public N4JSGrammarAccess.AnnotationListElements getAnnotationListAccess() {
		return gaN4JS.getAnnotationListAccess();
	}
	
	public ParserRule getAnnotationListRule() {
		return getAnnotationListAccess().getRule();
	}
	
	//ExpressionAnnotationList:
	//	{ExpressionAnnotationList} annotations+=Annotation+;
	public N4JSGrammarAccess.ExpressionAnnotationListElements getExpressionAnnotationListAccess() {
		return gaN4JS.getExpressionAnnotationListAccess();
	}
	
	public ParserRule getExpressionAnnotationListRule() {
		return getExpressionAnnotationListAccess().getRule();
	}
	
	//PropertyAssignmentAnnotationList:
	//	{PropertyAssignmentAnnotationList} annotations+=Annotation+;
	public N4JSGrammarAccess.PropertyAssignmentAnnotationListElements getPropertyAssignmentAnnotationListAccess() {
		return gaN4JS.getPropertyAssignmentAnnotationListAccess();
	}
	
	public ParserRule getPropertyAssignmentAnnotationListRule() {
		return getPropertyAssignmentAnnotationListAccess().getRule();
	}
	
	//N4MemberAnnotationList:
	//	{N4MemberAnnotationList} annotations+=Annotation+;
	public N4JSGrammarAccess.N4MemberAnnotationListElements getN4MemberAnnotationListAccess() {
		return gaN4JS.getN4MemberAnnotationListAccess();
	}
	
	public ParserRule getN4MemberAnnotationListRule() {
		return getN4MemberAnnotationListAccess().getRule();
	}
	
	//@ Override TypeReferenceName:
	//	'void' | 'This' | 'await' | 'Promisify' | 'target' | QualifiedTypeReferenceName;
	public N4JSGrammarAccess.TypeReferenceNameElements getTypeReferenceNameAccess() {
		return gaN4JS.getTypeReferenceNameAccess();
	}
	
	public ParserRule getTypeReferenceNameRule() {
		return getTypeReferenceNameAccess().getRule();
	}
	
	//QualifiedTypeReferenceName:
	//	IDENTIFIER ('.' IDENTIFIER)?;
	public N4JSGrammarAccess.QualifiedTypeReferenceNameElements getQualifiedTypeReferenceNameAccess() {
		return gaN4JS.getQualifiedTypeReferenceNameAccess();
	}
	
	public ParserRule getQualifiedTypeReferenceNameRule() {
		return getQualifiedTypeReferenceNameAccess().getRule();
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
	//	=> ({N4ClassDeclaration} declaredModifiers+=N4Modifier*
	//	'class' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>?) TypeVariables?
	//	ClassExtendsClause<Yield>?
	//	Members<Yield>;
	public N4JSGrammarAccess.N4ClassDeclarationElements getN4ClassDeclarationAccess() {
		return gaN4JS.getN4ClassDeclarationAccess();
	}
	
	public ParserRule getN4ClassDeclarationRule() {
		return getN4ClassDeclarationAccess().getRule();
	}
	
	//fragment Members <Yield> *:
	//	'{'
	//	ownedMembersRaw+=N4MemberDeclaration<Yield>*
	//	'}';
	public N4JSGrammarAccess.MembersElements getMembersAccess() {
		return gaN4JS.getMembersAccess();
	}
	
	public ParserRule getMembersRule() {
		return getMembersAccess().getRule();
	}
	
	///**
	// * Second 'extends' is not allowed and later validated to produce nicer error messages.
	// */ fragment ClassExtendsClause <Yield> *:
	//	'extends' (=> superClassRef=ParameterizedTypeRefNominal (('implements' | 'extends') ClassImplementsList)?
	//	| superClassExpression=LeftHandSideExpression<Yield>) | 'implements' ClassImplementsList;
	public N4JSGrammarAccess.ClassExtendsClauseElements getClassExtendsClauseAccess() {
		return gaN4JS.getClassExtendsClauseAccess();
	}
	
	public ParserRule getClassExtendsClauseRule() {
		return getClassExtendsClauseAccess().getRule();
	}
	
	///**
	// * In the list, only ',' is allowed as separator, this is validated later to procude nicer error messages.
	// */ fragment ClassImplementsList *:
	//	implementedInterfaceRefs+=ParameterizedTypeRefNominal ((',' | 'implements' | 'extends')
	//	implementedInterfaceRefs+=ParameterizedTypeRefNominal)*;
	public N4JSGrammarAccess.ClassImplementsListElements getClassImplementsListAccess() {
		return gaN4JS.getClassImplementsListAccess();
	}
	
	public ParserRule getClassImplementsListRule() {
		return getClassImplementsListAccess().getRule();
	}
	
	//N4ClassExpression <Yield>:
	//	{N4ClassExpression}
	//	'class' name=BindingIdentifier<Yield>?
	//	ClassExtendsClause<Yield>?
	//	Members<Yield>;
	public N4JSGrammarAccess.N4ClassExpressionElements getN4ClassExpressionAccess() {
		return gaN4JS.getN4ClassExpressionAccess();
	}
	
	public ParserRule getN4ClassExpressionRule() {
		return getN4ClassExpressionAccess().getRule();
	}
	
	//// cf. N4JSSpec §16
	//N4InterfaceDeclaration <Yield>:
	//	=> ({N4InterfaceDeclaration} declaredModifiers+=N4Modifier*
	//	'interface' typingStrategy=TypingStrategyDefSiteOperator? name=BindingIdentifier<Yield>?) TypeVariables?
	//	InterfaceImplementsList?
	//	Members<Yield>;
	public N4JSGrammarAccess.N4InterfaceDeclarationElements getN4InterfaceDeclarationAccess() {
		return gaN4JS.getN4InterfaceDeclarationAccess();
	}
	
	public ParserRule getN4InterfaceDeclarationRule() {
		return getN4InterfaceDeclarationAccess().getRule();
	}
	
	///**
	// * Actually only 'implements' is allowed in front and ',' are allowed as list separator,
	// * this is validated later to produce nicer error messages.
	// */ fragment InterfaceImplementsList *:
	//	('extends' | 'implements') superInterfaceRefs+=ParameterizedTypeRefNominal ((',' | 'implements' | 'extends')
	//	superInterfaceRefs+=ParameterizedTypeRefNominal)*;
	public N4JSGrammarAccess.InterfaceImplementsListElements getInterfaceImplementsListAccess() {
		return gaN4JS.getInterfaceImplementsListAccess();
	}
	
	public ParserRule getInterfaceImplementsListRule() {
		return getInterfaceImplementsListAccess().getRule();
	}
	
	//// cf. N4JSSpec §13
	//N4EnumDeclaration <Yield>:
	//	=> ({N4EnumDeclaration} declaredModifiers+=N4Modifier*
	//	'enum' name=BindingIdentifier<Yield>?)
	//	'{' (literals+=N4EnumLiteral (',' literals+=N4EnumLiteral)*)?
	//	'}';
	public N4JSGrammarAccess.N4EnumDeclarationElements getN4EnumDeclarationAccess() {
		return gaN4JS.getN4EnumDeclarationAccess();
	}
	
	public ParserRule getN4EnumDeclarationRule() {
		return getN4EnumDeclarationAccess().getRule();
	}
	
	///*
	// * Only upper case literals are allows, this is to be checked by the validator
	// */ N4EnumLiteral:
	//	name=IdentifierOrThis (':' value=STRING)?;
	public N4JSGrammarAccess.N4EnumLiteralElements getN4EnumLiteralAccess() {
		return gaN4JS.getN4EnumLiteralAccess();
	}
	
	public ParserRule getN4EnumLiteralRule() {
		return getN4EnumLiteralAccess().getRule();
	}
	
	//enum N4Modifier:
	//	private | project | protected | public | external | abstract | static | const;
	public N4JSGrammarAccess.N4ModifierElements getN4ModifierAccess() {
		return gaN4JS.getN4ModifierAccess();
	}
	
	public EnumRule getN4ModifierRule() {
		return getN4ModifierAccess().getRule();
	}
	
	//// TODO jvp: order matters, seems odd. What about object literal getters and setter?
	//// TODO sz: what about it?
	//N4MemberDeclaration <Yield>:
	//	AnnotatedN4MemberDeclaration<Yield> | N4GetterDeclaration<Yield> | N4SetterDeclaration<Yield> |
	//	N4MethodDeclaration<Yield> | N4FieldDeclaration<Yield> | N4CallableConstructorDeclaration<Yield>;
	public N4JSGrammarAccess.N4MemberDeclarationElements getN4MemberDeclarationAccess() {
		return gaN4JS.getN4MemberDeclarationAccess();
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
	public N4JSGrammarAccess.AnnotatedN4MemberDeclarationElements getAnnotatedN4MemberDeclarationAccess() {
		return gaN4JS.getAnnotatedN4MemberDeclarationAccess();
	}
	
	public ParserRule getAnnotatedN4MemberDeclarationRule() {
		return getAnnotatedN4MemberDeclarationAccess().getRule();
	}
	
	//fragment FieldDeclarationImpl <Yield> *:
	//	declaredModifiers+=N4Modifier* BogusTypeRefFragment?
	//	declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'?
	//	ColonSepDeclaredTypeRef? ('=' expression=Expression<In=true,Yield>)?
	//	Semi;
	public N4JSGrammarAccess.FieldDeclarationImplElements getFieldDeclarationImplAccess() {
		return gaN4JS.getFieldDeclarationImplAccess();
	}
	
	public ParserRule getFieldDeclarationImplRule() {
		return getFieldDeclarationImplAccess().getRule();
	}
	
	//N4FieldDeclaration <Yield>:
	//	{N4FieldDeclaration} FieldDeclarationImpl<Yield>;
	public N4JSGrammarAccess.N4FieldDeclarationElements getN4FieldDeclarationAccess() {
		return gaN4JS.getN4FieldDeclarationAccess();
	}
	
	public ParserRule getN4FieldDeclarationRule() {
		return getN4FieldDeclarationAccess().getRule();
	}
	
	//N4MethodDeclaration <Yield>:
	//	=> ({N4MethodDeclaration} declaredModifiers+=N4Modifier* TypeVariables? BogusTypeRefFragment? (generator?='*'
	//	declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody <Generator=true> |
	//	AsyncNoTrailingLineBreak declaredName=LiteralOrComputedPropertyName<Yield> -> MethodParamsReturnAndBody
	//	<Generator=false>)) ';'?;
	public N4JSGrammarAccess.N4MethodDeclarationElements getN4MethodDeclarationAccess() {
		return gaN4JS.getN4MethodDeclarationAccess();
	}
	
	public ParserRule getN4MethodDeclarationRule() {
		return getN4MethodDeclarationAccess().getRule();
	}
	
	//N4CallableConstructorDeclaration <Yield N4MethodDeclaration:
	//	MethodParamsReturnAndBody<Generator=false> ';'?;
	public N4JSGrammarAccess.N4CallableConstructorDeclarationElements getN4CallableConstructorDeclarationAccess() {
		return gaN4JS.getN4CallableConstructorDeclarationAccess();
	}
	
	public ParserRule getN4CallableConstructorDeclarationRule() {
		return getN4CallableConstructorDeclarationAccess().getRule();
	}
	
	//fragment MethodParamsAndBody <Generator> *:
	//	StrictFormalParameters<Yield=Generator> body=Block<Yield=Generator>?;
	public N4JSGrammarAccess.MethodParamsAndBodyElements getMethodParamsAndBodyAccess() {
		return gaN4JS.getMethodParamsAndBodyAccess();
	}
	
	public ParserRule getMethodParamsAndBodyRule() {
		return getMethodParamsAndBodyAccess().getRule();
	}
	
	//fragment MethodParamsReturnAndBody <Generator> *:
	//	StrictFormalParameters<Yield=Generator> ColonSepReturnTypeRef?
	//	body=Block<Yield=Generator>?;
	public N4JSGrammarAccess.MethodParamsReturnAndBodyElements getMethodParamsReturnAndBodyAccess() {
		return gaN4JS.getMethodParamsReturnAndBodyAccess();
	}
	
	public ParserRule getMethodParamsReturnAndBodyRule() {
		return getMethodParamsReturnAndBodyAccess().getRule();
	}
	
	///*
	// * 'get' and 'set' are no reserved words, see BindingIdentifier.
	// */ N4GetterDeclaration <Yield>:
	//	=> ({N4GetterDeclaration} declaredModifiers+=N4Modifier*
	//	GetterHeader<Yield>) body=Block<Yield>? ';'?;
	public N4JSGrammarAccess.N4GetterDeclarationElements getN4GetterDeclarationAccess() {
		return gaN4JS.getN4GetterDeclarationAccess();
	}
	
	public ParserRule getN4GetterDeclarationRule() {
		return getN4GetterDeclarationAccess().getRule();
	}
	
	//fragment GetterHeader <Yield> *:
	//	BogusTypeRefFragment? 'get' -> declaredName=LiteralOrComputedPropertyName<Yield> declaredOptional?='?'? '(' ')'
	//	ColonSepDeclaredTypeRef?;
	public N4JSGrammarAccess.GetterHeaderElements getGetterHeaderAccess() {
		return gaN4JS.getGetterHeaderAccess();
	}
	
	public ParserRule getGetterHeaderRule() {
		return getGetterHeaderAccess().getRule();
	}
	
	//N4SetterDeclaration <Yield>:
	//	=> ({N4SetterDeclaration} declaredModifiers+=N4Modifier*
	//	'set'
	//	-> declaredName=LiteralOrComputedPropertyName<Yield>) declaredOptional?='?'?
	//	'(' fpar=FormalParameter<Yield> ')' body=Block<Yield>? ';'?;
	public N4JSGrammarAccess.N4SetterDeclarationElements getN4SetterDeclarationAccess() {
		return gaN4JS.getN4SetterDeclarationAccess();
	}
	
	public ParserRule getN4SetterDeclarationRule() {
		return getN4SetterDeclarationAccess().getRule();
	}
	
	//BindingPattern <Yield>:
	//	ObjectBindingPattern<Yield> | ArrayBindingPattern<Yield>;
	public N4JSGrammarAccess.BindingPatternElements getBindingPatternAccess() {
		return gaN4JS.getBindingPatternAccess();
	}
	
	public ParserRule getBindingPatternRule() {
		return getBindingPatternAccess().getRule();
	}
	
	//ObjectBindingPattern <Yield BindingPattern:
	//	{BindingPattern}
	//	'{' (properties+=BindingProperty<Yield,AllowType=false> (',' properties+=BindingProperty<Yield,AllowType=false>)*)?
	//	'}';
	public N4JSGrammarAccess.ObjectBindingPatternElements getObjectBindingPatternAccess() {
		return gaN4JS.getObjectBindingPatternAccess();
	}
	
	public ParserRule getObjectBindingPatternRule() {
		return getObjectBindingPatternAccess().getRule();
	}
	
	//ArrayBindingPattern <Yield BindingPattern:
	//	{BindingPattern}
	//	'['
	//	elements+=Elision* (elements+=BindingRestElement<Yield> (',' elements+=Elision* elements+=BindingRestElement<Yield>)*
	//	(',' elements+=Elision*)?)?
	//	']';
	public N4JSGrammarAccess.ArrayBindingPatternElements getArrayBindingPatternAccess() {
		return gaN4JS.getArrayBindingPatternAccess();
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
	public N4JSGrammarAccess.BindingPropertyElements getBindingPropertyAccess() {
		return gaN4JS.getBindingPropertyAccess();
	}
	
	public ParserRule getBindingPropertyRule() {
		return getBindingPropertyAccess().getRule();
	}
	
	//SingleNameBinding <Yield, AllowType BindingElement:
	//	varDecl=VariableDeclaration<In=true,Yield,AllowType>;
	public N4JSGrammarAccess.SingleNameBindingElements getSingleNameBindingAccess() {
		return gaN4JS.getSingleNameBindingAccess();
	}
	
	public ParserRule getSingleNameBindingRule() {
		return getSingleNameBindingAccess().getRule();
	}
	
	//BindingElement <Yield>:
	//	BindingElementImpl<Yield>;
	public N4JSGrammarAccess.BindingElementElements getBindingElementAccess() {
		return gaN4JS.getBindingElementAccess();
	}
	
	public ParserRule getBindingElementRule() {
		return getBindingElementAccess().getRule();
	}
	
	//BindingRestElement <Yield BindingElement:
	//	rest?='...'?
	//	BindingElementImpl<Yield>;
	public N4JSGrammarAccess.BindingRestElementElements getBindingRestElementAccess() {
		return gaN4JS.getBindingRestElementAccess();
	}
	
	public ParserRule getBindingRestElementRule() {
		return getBindingRestElementAccess().getRule();
	}
	
	//fragment BindingElementImpl <Yield> returns BindingElement:
	//	=> (nestedPattern=BindingPattern<Yield>) ('=' expression=AssignmentExpression<In=true,Yield>)?
	//	| varDecl=VariableDeclaration<In=true,Yield,AllowType=true>;
	public N4JSGrammarAccess.BindingElementImplElements getBindingElementImplAccess() {
		return gaN4JS.getBindingElementImplAccess();
	}
	
	public ParserRule getBindingElementImplRule() {
		return getBindingElementImplAccess().getRule();
	}
	
	//Elision BindingElement:
	//	{BindingElement} ',';
	public N4JSGrammarAccess.ElisionElements getElisionAccess() {
		return gaN4JS.getElisionAccess();
	}
	
	public ParserRule getElisionRule() {
		return getElisionAccess().getRule();
	}
	
	//LiteralOrComputedPropertyName <Yield>:
	//	literalName=IdentifierName
	//	| literalName=STRING
	//	| literalName=NumericLiteralAsString
	//	| '[' expression=AssignmentExpression<In=true,Yield> ']';
	public N4JSGrammarAccess.LiteralOrComputedPropertyNameElements getLiteralOrComputedPropertyNameAccess() {
		return gaN4JS.getLiteralOrComputedPropertyNameAccess();
	}
	
	public ParserRule getLiteralOrComputedPropertyNameRule() {
		return getLiteralOrComputedPropertyNameAccess().getRule();
	}
	
	//terminal INCOMPLETE_ASYNC_ARROW:
	//	'@=';
	public TerminalRule getINCOMPLETE_ASYNC_ARROWRule() {
		return gaN4JS.getINCOMPLETE_ASYNC_ARROWRule();
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
	//	PrimaryTypeExpression ({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=PrimaryTypeExpression)+)?;
	public TypeExpressionsGrammarAccess.IntersectionTypeExpressionElements getIntersectionTypeExpressionAccess() {
		return gaTypeExpressions.getIntersectionTypeExpressionAccess();
	}
	
	public ParserRule getIntersectionTypeExpressionRule() {
		return getIntersectionTypeExpressionAccess().getRule();
	}
	
	//PrimaryTypeExpression TypeRef:
	//	ArrowFunctionTypeExpression
	//	| ArrayTypeRef
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
	//	| ArrayTypeRef
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
	//	| Wildcard;
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
	//	'union' '{' typeRefs+=TypeRefWithoutModifiers (',' typeRefs+=TypeRefWithoutModifiers)* '}';
	public TypeExpressionsGrammarAccess.UnionTypeExpressionOLDElements getUnionTypeExpressionOLDAccess() {
		return gaTypeExpressions.getUnionTypeExpressionOLDAccess();
	}
	
	public ParserRule getUnionTypeExpressionOLDRule() {
		return getUnionTypeExpressionOLDAccess().getRule();
	}
	
	//IntersectionTypeExpressionOLD IntersectionTypeExpression:
	//	{IntersectionTypeExpression}
	//	'intersection' '{' typeRefs+=TypeRefWithoutModifiers (',' typeRefs+=TypeRefWithoutModifiers)* '}';
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
	//	TypeAndTypeArguments;
	public TypeExpressionsGrammarAccess.ParameterizedTypeRefNominalElements getParameterizedTypeRefNominalAccess() {
		return gaTypeExpressions.getParameterizedTypeRefNominalAccess();
	}
	
	public ParserRule getParameterizedTypeRefNominalRule() {
		return getParameterizedTypeRefNominalAccess().getRule();
	}
	
	//ArrayTypeRef ParameterizedTypeRef:
	//	arrayTypeLiteral?="[" typeArgs+=TypeArgument "]";
	public TypeExpressionsGrammarAccess.ArrayTypeRefElements getArrayTypeRefAccess() {
		return gaTypeExpressions.getArrayTypeRefAccess();
	}
	
	public ParserRule getArrayTypeRefRule() {
		return getArrayTypeRefAccess().getRule();
	}
	
	//ParameterizedTypeRefStructural:
	//	definedTypingStrategy=TypingStrategyUseSiteOperator
	//	TypeAndTypeArguments ('with' TStructMemberList)?;
	public TypeExpressionsGrammarAccess.ParameterizedTypeRefStructuralElements getParameterizedTypeRefStructuralAccess() {
		return gaTypeExpressions.getParameterizedTypeRefStructuralAccess();
	}
	
	public ParserRule getParameterizedTypeRefStructuralRule() {
		return getParameterizedTypeRefStructuralAccess().getRule();
	}
	
	//fragment TypeAndTypeArguments returns ParameterizedTypeRef:
	//	declaredType=[Type|super::TypeReferenceName] -> TypeArguments?;
	public TypeExpressionsGrammarAccess.TypeAndTypeArgumentsElements getTypeAndTypeArgumentsAccess() {
		return gaTypeExpressions.getTypeAndTypeArgumentsAccess();
	}
	
	public ParserRule getTypeAndTypeArgumentsRule() {
		return getTypeAndTypeArgumentsAccess().getRule();
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
	//	('r' | 'i' | 'w') '~';
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
	//	Wildcard | WildcardNewNotation | TypeRef;
	public TypeExpressionsGrammarAccess.TypeArgumentElements getTypeArgumentAccess() {
		return gaTypeExpressions.getTypeArgumentAccess();
	}
	
	public ParserRule getTypeArgumentRule() {
		return getTypeArgumentAccess().getRule();
	}
	
	//Wildcard:
	//	=> ({Wildcard} '?') ('extends' declaredUpperBound=TypeRef | 'super'
	//	declaredLowerBound=TypeRef)?;
	public TypeExpressionsGrammarAccess.WildcardElements getWildcardAccess() {
		return gaTypeExpressions.getWildcardAccess();
	}
	
	public ParserRule getWildcardRule() {
		return getWildcardAccess().getRule();
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
