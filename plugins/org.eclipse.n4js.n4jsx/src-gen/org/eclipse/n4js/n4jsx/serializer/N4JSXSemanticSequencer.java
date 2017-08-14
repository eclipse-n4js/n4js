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
package org.eclipse.n4js.n4jsx.serializer;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrayPadding;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryIntLiteral;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.CaseClause;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.CatchVariable;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.DebuggerStatement;
import org.eclipse.n4js.n4JS.DefaultClause;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.DoubleLiteral;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportSpecifier;
import org.eclipse.n4js.n4JS.ExportedVariableBinding;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.ExpressionAnnotationList;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.HexIntLiteral;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.LegacyOctalIntLiteral;
import org.eclipse.n4js.n4JS.LiteralAnnotationArgument;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberAnnotationList;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NewTarget;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.OctalIntLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.ScientificIntLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.n4js.n4JS.WithStatement;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElementName;
import org.eclipse.n4js.n4jsx.n4JSX.JSXExpression;
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage;
import org.eclipse.n4js.n4jsx.services.N4JSXGrammarAccess;
import org.eclipse.n4js.serializer.N4JSSemanticSequencer;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefNominal;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TAnonymousFormalParameter;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class N4JSXSemanticSequencer extends N4JSSemanticSequencer {

	@Inject
	private N4JSXGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == N4JSPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case N4JSPackage.ADDITIVE_EXPRESSION:
				sequence_AdditiveExpression(context, (AdditiveExpression) semanticObject); 
				return; 
			case N4JSPackage.ANNOTATION:
				sequence_AnnotationNoAtSign(context, (Annotation) semanticObject); 
				return; 
			case N4JSPackage.ANNOTATION_LIST:
				sequence_AnnotationList(context, (AnnotationList) semanticObject); 
				return; 
			case N4JSPackage.ARGUMENT:
				sequence_Argument(context, (Argument) semanticObject); 
				return; 
			case N4JSPackage.ARRAY_ELEMENT:
				sequence_ArrayElement(context, (ArrayElement) semanticObject); 
				return; 
			case N4JSPackage.ARRAY_LITERAL:
				sequence_ArrayLiteral(context, (ArrayLiteral) semanticObject); 
				return; 
			case N4JSPackage.ARRAY_PADDING:
				sequence_ArrayPadding(context, (ArrayPadding) semanticObject); 
				return; 
			case N4JSPackage.ARROW_FUNCTION:
				sequence_ArrowExpression_ColonSepReturnTypeRef_StrictFormalParameters(context, (ArrowFunction) semanticObject); 
				return; 
			case N4JSPackage.ASSIGNMENT_EXPRESSION:
				sequence_AssignmentExpression(context, (AssignmentExpression) semanticObject); 
				return; 
			case N4JSPackage.AWAIT_EXPRESSION:
				sequence_AwaitExpression(context, (AwaitExpression) semanticObject); 
				return; 
			case N4JSPackage.BINARY_BITWISE_EXPRESSION:
				if (rule == grammarAccess.getBitwiseANDExpressionRule()
						|| action == grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()) {
					sequence_BitwiseANDExpression(context, (BinaryBitwiseExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getBitwiseORExpressionRule()
						|| action == grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_BitwiseANDExpression_BitwiseORExpression_BitwiseXORExpression(context, (BinaryBitwiseExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getBitwiseXORExpressionRule()
						|| action == grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()) {
					sequence_BitwiseANDExpression_BitwiseXORExpression(context, (BinaryBitwiseExpression) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.BINARY_INT_LITERAL:
				sequence_BinaryIntLiteral(context, (BinaryIntLiteral) semanticObject); 
				return; 
			case N4JSPackage.BINARY_LOGICAL_EXPRESSION:
				if (rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()) {
					sequence_LogicalANDExpression(context, (BinaryLogicalExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_LogicalANDExpression_LogicalORExpression(context, (BinaryLogicalExpression) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.BINDING_ELEMENT:
				if (rule == grammarAccess.getBindingElementRule()) {
					sequence_BindingElementImpl(context, (BindingElement) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getBindingRestElementRule()) {
					sequence_BindingElementImpl_BindingRestElement(context, (BindingElement) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getElisionRule()) {
					sequence_Elision(context, (BindingElement) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getSingleNameBindingRule()) {
					sequence_SingleNameBinding(context, (BindingElement) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.BINDING_PATTERN:
				if (rule == grammarAccess.getArrayBindingPatternRule()) {
					sequence_ArrayBindingPattern(context, (BindingPattern) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getBindingPatternRule()) {
					sequence_ArrayBindingPattern_ObjectBindingPattern(context, (BindingPattern) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getObjectBindingPatternRule()) {
					sequence_ObjectBindingPattern(context, (BindingPattern) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.BINDING_PROPERTY:
				sequence_BindingProperty(context, (BindingProperty) semanticObject); 
				return; 
			case N4JSPackage.BLOCK:
				if (rule == grammarAccess.getBlockMinusBracesRule()) {
					sequence_BlockMinusBraces(context, (Block) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getScriptElementRule()
						|| rule == grammarAccess.getBlockRule()
						|| rule == grammarAccess.getRootStatementRule()
						|| rule == grammarAccess.getStatementRule()) {
					sequence_Block(context, (Block) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getExpressionDisguisedAsBlockRule()) {
					sequence_ExpressionDisguisedAsBlock(context, (Block) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.BOOLEAN_LITERAL:
				sequence_BooleanLiteral(context, (BooleanLiteral) semanticObject); 
				return; 
			case N4JSPackage.BREAK_STATEMENT:
				sequence_BreakStatement(context, (BreakStatement) semanticObject); 
				return; 
			case N4JSPackage.CASE_CLAUSE:
				sequence_CaseClause(context, (CaseClause) semanticObject); 
				return; 
			case N4JSPackage.CAST_EXPRESSION:
				sequence_CastExpression(context, (CastExpression) semanticObject); 
				return; 
			case N4JSPackage.CATCH_BLOCK:
				sequence_CatchBlock(context, (CatchBlock) semanticObject); 
				return; 
			case N4JSPackage.CATCH_VARIABLE:
				sequence_BogusTypeRefFragment_CatchVariable_ColonSepDeclaredTypeRef(context, (CatchVariable) semanticObject); 
				return; 
			case N4JSPackage.COMMA_EXPRESSION:
				sequence_Expression(context, (CommaExpression) semanticObject); 
				return; 
			case N4JSPackage.CONDITIONAL_EXPRESSION:
				sequence_ConditionalExpression(context, (ConditionalExpression) semanticObject); 
				return; 
			case N4JSPackage.CONTINUE_STATEMENT:
				sequence_ContinueStatement(context, (ContinueStatement) semanticObject); 
				return; 
			case N4JSPackage.DEBUGGER_STATEMENT:
				sequence_DebuggerStatement(context, (DebuggerStatement) semanticObject); 
				return; 
			case N4JSPackage.DEFAULT_CLAUSE:
				sequence_DefaultClause(context, (DefaultClause) semanticObject); 
				return; 
			case N4JSPackage.DEFAULT_IMPORT_SPECIFIER:
				sequence_DefaultImportSpecifier(context, (DefaultImportSpecifier) semanticObject); 
				return; 
			case N4JSPackage.DO_STATEMENT:
				sequence_DoStatement(context, (DoStatement) semanticObject); 
				return; 
			case N4JSPackage.DOUBLE_LITERAL:
				sequence_DoubleLiteral(context, (DoubleLiteral) semanticObject); 
				return; 
			case N4JSPackage.EMPTY_STATEMENT:
				sequence_EmptyStatement(context, (EmptyStatement) semanticObject); 
				return; 
			case N4JSPackage.EQUALITY_EXPRESSION:
				sequence_EqualityExpression(context, (EqualityExpression) semanticObject); 
				return; 
			case N4JSPackage.EXPORT_DECLARATION:
				if (rule == grammarAccess.getAnnotatedScriptElementRule()) {
					sequence_AnnotatedScriptElement_ExportClause_ExportDeclarationImpl_ExportFromClause(context, (ExportDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getScriptElementRule()) {
					sequence_AnnotatedScriptElement_ExportClause_ExportDeclaration_ExportDeclarationImpl_ExportFromClause(context, (ExportDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getExportDeclarationRule()) {
					sequence_ExportClause_ExportDeclaration_ExportDeclarationImpl_ExportFromClause(context, (ExportDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.EXPORT_SPECIFIER:
				sequence_ExportSpecifier(context, (ExportSpecifier) semanticObject); 
				return; 
			case N4JSPackage.EXPORTED_VARIABLE_BINDING:
				sequence_ExportedVariableBinding(context, (ExportedVariableBinding) semanticObject); 
				return; 
			case N4JSPackage.EXPORTED_VARIABLE_DECLARATION:
				sequence_ColonSepDeclaredTypeRef_ExportedVariableDeclaration_VariableDeclarationImpl(context, (ExportedVariableDeclaration) semanticObject); 
				return; 
			case N4JSPackage.EXPORTED_VARIABLE_STATEMENT:
				if (rule == grammarAccess.getAnnotatedExportableElementRule()) {
					sequence_AnnotatedExportableElement(context, (ExportedVariableStatement) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getExportableElementRule()) {
					sequence_AnnotatedExportableElement_ExportedVariableStatement(context, (ExportedVariableStatement) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getExportedVariableStatementRule()) {
					sequence_ExportedVariableStatement(context, (ExportedVariableStatement) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.EXPRESSION_ANNOTATION_LIST:
				sequence_ExpressionAnnotationList(context, (ExpressionAnnotationList) semanticObject); 
				return; 
			case N4JSPackage.EXPRESSION_STATEMENT:
				if (rule == grammarAccess.getAssignmentExpressionStatementRule()) {
					sequence_AssignmentExpressionStatement(context, (ExpressionStatement) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getScriptElementRule()
						|| rule == grammarAccess.getRootStatementRule()
						|| rule == grammarAccess.getStatementRule()
						|| rule == grammarAccess.getExpressionStatementRule()) {
					sequence_ExpressionStatement(context, (ExpressionStatement) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.FINALLY_BLOCK:
				sequence_FinallyBlock(context, (FinallyBlock) semanticObject); 
				return; 
			case N4JSPackage.FOR_STATEMENT:
				sequence_ForStatement(context, (ForStatement) semanticObject); 
				return; 
			case N4JSPackage.FORMAL_PARAMETER:
				if (rule == grammarAccess.getFormalParameterRule()) {
					sequence_BindingElementFragment_BogusTypeRefFragment_ColonSepDeclaredTypeRef_FormalParameter(context, (FormalParameter) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getBindingIdentifierAsFormalParameterRule()) {
					sequence_BindingIdentifierAsFormalParameter(context, (FormalParameter) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.FUNCTION_DECLARATION:
				if (rule == grammarAccess.getExportableElementRule()) {
					sequence_AnnotatedExportableElement_AsyncNoTrailingLineBreak_ColonSepReturnTypeRef_FunctionBody_FunctionDeclaration_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedExportableElementRule()) {
					sequence_AnnotatedExportableElement_AsyncNoTrailingLineBreak_ColonSepReturnTypeRef_FunctionBody_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getStatementRule()) {
					sequence_AnnotatedFunctionDeclaration_AsyncNoTrailingLineBreak_ColonSepReturnTypeRef_FunctionBody_FunctionDeclaration_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedFunctionDeclarationRule()) {
					sequence_AnnotatedFunctionDeclaration_AsyncNoTrailingLineBreak_ColonSepReturnTypeRef_FunctionBody_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getScriptElementRule()) {
					sequence_AnnotatedScriptElement_AsyncNoTrailingLineBreak_ColonSepReturnTypeRef_FunctionBody_FunctionDeclaration_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedScriptElementRule()) {
					sequence_AnnotatedScriptElement_AsyncNoTrailingLineBreak_ColonSepReturnTypeRef_FunctionBody_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getFunctionDeclarationRule()
						|| rule == grammarAccess.getRootStatementRule()) {
					sequence_AsyncNoTrailingLineBreak_ColonSepReturnTypeRef_FunctionBody_FunctionDeclaration_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.FUNCTION_EXPRESSION:
				if (rule == grammarAccess.getPrimaryExpressionRule()
						|| rule == grammarAccess.getLeftHandSideExpressionRule()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0()
						|| rule == grammarAccess.getMemberExpressionRule()
						|| action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_2_1_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_2_1_2_0()
						|| rule == grammarAccess.getPostfixExpressionRule()
						|| action == grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0()
						|| rule == grammarAccess.getCastExpressionRule()
						|| action == grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getUnaryExpressionRule()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getShiftExpressionRule()
						|| action == grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getEqualityExpressionRule()
						|| action == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseANDExpressionRule()
						|| action == grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseXORExpressionRule()
						|| action == grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseORExpressionRule()
						|| action == grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_AnnotatedExpression_AsyncFunctionExpression_AsyncNoTrailingLineBreak_ColonSepReturnTypeRef_FunctionBody_FunctionExpression_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedExpressionRule()) {
					sequence_AnnotatedExpression_AsyncNoTrailingLineBreak_ColonSepReturnTypeRef_FunctionBody_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAsyncFunctionExpressionRule()) {
					sequence_AsyncFunctionExpression_ColonSepReturnTypeRef_FunctionBody_FunctionHeader_StrictFormalParameters_TypeVariables(context, (FunctionExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getFunctionExpressionRule()) {
					sequence_ColonSepReturnTypeRef_FunctionBody_FunctionExpression_FunctionHeader_FunctionImpl_StrictFormalParameters_TypeVariables(context, (FunctionExpression) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.HEX_INT_LITERAL:
				sequence_HexIntLiteral(context, (HexIntLiteral) semanticObject); 
				return; 
			case N4JSPackage.IDENTIFIER_REF:
				if (rule == grammarAccess.getPrimaryExpressionRule()
						|| rule == grammarAccess.getJSXElementNameExpressionRule()
						|| action == grammarAccess.getJSXElementNameExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_0()
						|| rule == grammarAccess.getIdentifierRefRule()
						|| rule == grammarAccess.getLeftHandSideExpressionRule()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0()
						|| rule == grammarAccess.getMemberExpressionRule()
						|| action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_2_1_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_2_1_2_0()
						|| rule == grammarAccess.getPostfixExpressionRule()
						|| action == grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0()
						|| rule == grammarAccess.getCastExpressionRule()
						|| action == grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getUnaryExpressionRule()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getShiftExpressionRule()
						|| action == grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getEqualityExpressionRule()
						|| action == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseANDExpressionRule()
						|| action == grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseXORExpressionRule()
						|| action == grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseORExpressionRule()
						|| action == grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_IdentifierRef(context, (IdentifierRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getLetIdentifierRefRule()) {
					sequence_LetIdentifierRef(context, (IdentifierRef) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.IF_STATEMENT:
				sequence_IfStatement(context, (IfStatement) semanticObject); 
				return; 
			case N4JSPackage.IMPORT_DECLARATION:
				if (rule == grammarAccess.getAnnotatedScriptElementRule()) {
					sequence_AnnotatedScriptElement_ImportClause_ImportDeclarationImpl_ImportSpecifiersExceptDefault(context, (ImportDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getScriptElementRule()) {
					sequence_AnnotatedScriptElement_ImportClause_ImportDeclaration_ImportDeclarationImpl_ImportSpecifiersExceptDefault(context, (ImportDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getImportDeclarationRule()) {
					sequence_ImportClause_ImportDeclaration_ImportDeclarationImpl_ImportSpecifiersExceptDefault(context, (ImportDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION:
				if (action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_2_0_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTargetAction_1_2_1_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_2_2_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getTaggedTemplateStringTargetAction_1_2_3_0_0()) {
					sequence_IndexedAccessExpressionTail_LeftHandSideExpression_IndexedAccessExpression_1_2_1_0_ParameterizedCallExpression_1_2_0_0_ParameterizedPropertyAccessExpression_1_2_2_0_TaggedTemplateString_1_2_3_0_0(context, (IndexedAccessExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getLeftHandSideExpressionRule()
						|| rule == grammarAccess.getPostfixExpressionRule()
						|| action == grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0()
						|| rule == grammarAccess.getCastExpressionRule()
						|| action == grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getUnaryExpressionRule()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getShiftExpressionRule()
						|| action == grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getEqualityExpressionRule()
						|| action == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseANDExpressionRule()
						|| action == grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseXORExpressionRule()
						|| action == grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseORExpressionRule()
						|| action == grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_IndexedAccessExpressionTail_LeftHandSideExpression_MemberExpression(context, (IndexedAccessExpression) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0()
						|| rule == grammarAccess.getMemberExpressionRule()) {
					sequence_IndexedAccessExpressionTail_MemberExpression(context, (IndexedAccessExpression) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_1_3_3_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_3_3_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_1_3_3_2_0()) {
					sequence_IndexedAccessExpressionTail_MemberExpression_IndexedAccessExpression_1_3_3_0_0_ParameterizedPropertyAccessExpression_1_3_3_1_0_TaggedTemplateString_1_3_3_2_0(context, (IndexedAccessExpression) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_2_1_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_2_1_2_0()) {
					sequence_IndexedAccessExpressionTail_MemberExpression_IndexedAccessExpression_2_1_0_0_ParameterizedPropertyAccessExpression_2_1_1_0_TaggedTemplateString_2_1_2_0(context, (IndexedAccessExpression) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.INT_LITERAL:
				sequence_IntLiteral(context, (IntLiteral) semanticObject); 
				return; 
			case N4JSPackage.LABELLED_STATEMENT:
				sequence_LabelledStatement(context, (LabelledStatement) semanticObject); 
				return; 
			case N4JSPackage.LEGACY_OCTAL_INT_LITERAL:
				sequence_LegacyOctalIntLiteral(context, (LegacyOctalIntLiteral) semanticObject); 
				return; 
			case N4JSPackage.LITERAL_ANNOTATION_ARGUMENT:
				sequence_LiteralAnnotationArgument(context, (LiteralAnnotationArgument) semanticObject); 
				return; 
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME:
				sequence_LiteralOrComputedPropertyName(context, (LiteralOrComputedPropertyName) semanticObject); 
				return; 
			case N4JSPackage.MULTIPLICATIVE_EXPRESSION:
				sequence_MultiplicativeExpression(context, (MultiplicativeExpression) semanticObject); 
				return; 
			case N4JSPackage.N4_CLASS_DECLARATION:
				if (rule == grammarAccess.getExportableElementRule()) {
					sequence_AnnotatedExportableElement_ClassExtendsClause_ClassImplementsList_Members_N4ClassDeclaration_TypeVariables(context, (N4ClassDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedExportableElementRule()) {
					sequence_AnnotatedExportableElement_ClassExtendsClause_ClassImplementsList_Members_TypeVariables(context, (N4ClassDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getScriptElementRule()) {
					sequence_AnnotatedScriptElement_ClassExtendsClause_ClassImplementsList_Members_N4ClassDeclaration_TypeVariables(context, (N4ClassDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedScriptElementRule()) {
					sequence_AnnotatedScriptElement_ClassExtendsClause_ClassImplementsList_Members_TypeVariables(context, (N4ClassDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4ClassDeclarationRule()) {
					sequence_ClassExtendsClause_ClassImplementsList_Members_N4ClassDeclaration_TypeVariables(context, (N4ClassDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.N4_CLASS_EXPRESSION:
				if (rule == grammarAccess.getAnnotatedExpressionRule()) {
					sequence_AnnotatedExpression_ClassExtendsClause_ClassImplementsList_Members(context, (N4ClassExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPrimaryExpressionRule()
						|| rule == grammarAccess.getLeftHandSideExpressionRule()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0()
						|| rule == grammarAccess.getMemberExpressionRule()
						|| action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_2_1_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_2_1_2_0()
						|| rule == grammarAccess.getPostfixExpressionRule()
						|| action == grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0()
						|| rule == grammarAccess.getCastExpressionRule()
						|| action == grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getUnaryExpressionRule()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getShiftExpressionRule()
						|| action == grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getEqualityExpressionRule()
						|| action == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseANDExpressionRule()
						|| action == grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseXORExpressionRule()
						|| action == grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseORExpressionRule()
						|| action == grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_AnnotatedExpression_ClassExtendsClause_ClassImplementsList_Members_N4ClassExpression(context, (N4ClassExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4ClassExpressionRule()) {
					sequence_ClassExtendsClause_ClassImplementsList_Members_N4ClassExpression(context, (N4ClassExpression) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.N4_ENUM_DECLARATION:
				if (rule == grammarAccess.getAnnotatedExportableElementRule()) {
					sequence_AnnotatedExportableElement(context, (N4EnumDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getExportableElementRule()) {
					sequence_AnnotatedExportableElement_N4EnumDeclaration(context, (N4EnumDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedScriptElementRule()) {
					sequence_AnnotatedScriptElement(context, (N4EnumDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getScriptElementRule()) {
					sequence_AnnotatedScriptElement_N4EnumDeclaration(context, (N4EnumDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4EnumDeclarationRule()) {
					sequence_N4EnumDeclaration(context, (N4EnumDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.N4_ENUM_LITERAL:
				sequence_N4EnumLiteral(context, (N4EnumLiteral) semanticObject); 
				return; 
			case N4JSPackage.N4_FIELD_DECLARATION:
				if (rule == grammarAccess.getAnnotatedN4MemberDeclarationRule()) {
					sequence_AnnotatedN4MemberDeclaration_BogusTypeRefFragment_ColonSepDeclaredTypeRef_FieldDeclarationImpl(context, (N4FieldDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4MemberDeclarationRule()) {
					sequence_AnnotatedN4MemberDeclaration_BogusTypeRefFragment_ColonSepDeclaredTypeRef_FieldDeclarationImpl_N4FieldDeclaration(context, (N4FieldDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4FieldDeclarationRule()) {
					sequence_BogusTypeRefFragment_ColonSepDeclaredTypeRef_FieldDeclarationImpl_N4FieldDeclaration(context, (N4FieldDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.N4_GETTER_DECLARATION:
				if (rule == grammarAccess.getAnnotatedN4MemberDeclarationRule()) {
					sequence_AnnotatedN4MemberDeclaration_BogusTypeRefFragment_ColonSepDeclaredTypeRef_GetterHeader(context, (N4GetterDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4MemberDeclarationRule()) {
					sequence_AnnotatedN4MemberDeclaration_BogusTypeRefFragment_ColonSepDeclaredTypeRef_GetterHeader_N4GetterDeclaration(context, (N4GetterDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4GetterDeclarationRule()) {
					sequence_BogusTypeRefFragment_ColonSepDeclaredTypeRef_GetterHeader_N4GetterDeclaration(context, (N4GetterDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.N4_INTERFACE_DECLARATION:
				if (rule == grammarAccess.getExportableElementRule()) {
					sequence_AnnotatedExportableElement_InterfaceImplementsList_Members_N4InterfaceDeclaration_TypeVariables(context, (N4InterfaceDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedExportableElementRule()) {
					sequence_AnnotatedExportableElement_InterfaceImplementsList_Members_TypeVariables(context, (N4InterfaceDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getScriptElementRule()) {
					sequence_AnnotatedScriptElement_InterfaceImplementsList_Members_N4InterfaceDeclaration_TypeVariables(context, (N4InterfaceDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedScriptElementRule()) {
					sequence_AnnotatedScriptElement_InterfaceImplementsList_Members_TypeVariables(context, (N4InterfaceDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4InterfaceDeclarationRule()) {
					sequence_InterfaceImplementsList_Members_N4InterfaceDeclaration_TypeVariables(context, (N4InterfaceDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.N4_MEMBER_ANNOTATION_LIST:
				sequence_N4MemberAnnotationList(context, (N4MemberAnnotationList) semanticObject); 
				return; 
			case N4JSPackage.N4_METHOD_DECLARATION:
				if (rule == grammarAccess.getN4MemberDeclarationRule()) {
					sequence_AnnotatedN4MemberDeclaration_AsyncNoTrailingLineBreak_BogusTypeRefFragment_ColonSepReturnTypeRef_MethodParamsReturnAndBody_N4MethodDeclaration_StrictFormalParameters_TypeVariables(context, (N4MethodDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedN4MemberDeclarationRule()) {
					sequence_AnnotatedN4MemberDeclaration_AsyncNoTrailingLineBreak_BogusTypeRefFragment_ColonSepReturnTypeRef_MethodParamsReturnAndBody_StrictFormalParameters_TypeVariables(context, (N4MethodDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4MethodDeclarationRule()) {
					sequence_AsyncNoTrailingLineBreak_BogusTypeRefFragment_ColonSepReturnTypeRef_MethodParamsReturnAndBody_N4MethodDeclaration_StrictFormalParameters_TypeVariables(context, (N4MethodDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4CallableConstructorDeclarationRule()) {
					sequence_ColonSepReturnTypeRef_MethodParamsReturnAndBody_StrictFormalParameters(context, (N4MethodDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.N4_SETTER_DECLARATION:
				if (rule == grammarAccess.getAnnotatedN4MemberDeclarationRule()) {
					sequence_AnnotatedN4MemberDeclaration(context, (N4SetterDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4MemberDeclarationRule()) {
					sequence_AnnotatedN4MemberDeclaration_N4SetterDeclaration(context, (N4SetterDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getN4SetterDeclarationRule()) {
					sequence_N4SetterDeclaration(context, (N4SetterDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.NAMED_IMPORT_SPECIFIER:
				sequence_NamedImportSpecifier(context, (NamedImportSpecifier) semanticObject); 
				return; 
			case N4JSPackage.NAMESPACE_IMPORT_SPECIFIER:
				sequence_NamespaceImportSpecifier(context, (NamespaceImportSpecifier) semanticObject); 
				return; 
			case N4JSPackage.NEW_EXPRESSION:
				if (action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_1_3_3_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_3_3_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_1_3_3_2_0()) {
					sequence_Arguments_ConcreteTypeArguments_MemberExpression_IndexedAccessExpression_1_3_3_0_0_ParameterizedPropertyAccessExpression_1_3_3_1_0_TaggedTemplateString_1_3_3_2_0(context, (NewExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getLeftHandSideExpressionRule()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0()
						|| rule == grammarAccess.getMemberExpressionRule()
						|| rule == grammarAccess.getPostfixExpressionRule()
						|| action == grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0()
						|| rule == grammarAccess.getCastExpressionRule()
						|| action == grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getUnaryExpressionRule()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getShiftExpressionRule()
						|| action == grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getEqualityExpressionRule()
						|| action == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseANDExpressionRule()
						|| action == grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseXORExpressionRule()
						|| action == grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseORExpressionRule()
						|| action == grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_Arguments_ConcreteTypeArguments_MemberExpression(context, (NewExpression) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.NEW_TARGET:
				sequence_MemberExpression(context, (NewTarget) semanticObject); 
				return; 
			case N4JSPackage.NULL_LITERAL:
				sequence_NullLiteral(context, (NullLiteral) semanticObject); 
				return; 
			case N4JSPackage.OBJECT_LITERAL:
				sequence_ObjectLiteral(context, (ObjectLiteral) semanticObject); 
				return; 
			case N4JSPackage.OCTAL_INT_LITERAL:
				sequence_OctalIntLiteral(context, (OctalIntLiteral) semanticObject); 
				return; 
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION:
				if (rule == grammarAccess.getLeftHandSideExpressionRule()
						|| rule == grammarAccess.getPostfixExpressionRule()
						|| action == grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0()
						|| rule == grammarAccess.getCastExpressionRule()
						|| action == grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getUnaryExpressionRule()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getShiftExpressionRule()
						|| action == grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getEqualityExpressionRule()
						|| action == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseANDExpressionRule()
						|| action == grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseXORExpressionRule()
						|| action == grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseORExpressionRule()
						|| action == grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_Arguments_ConcreteTypeArguments_LeftHandSideExpression_ParameterizedCallExpression(context, (ParameterizedCallExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPrimaryExpressionRule()
						|| rule == grammarAccess.getParameterizedCallExpressionRule()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0()
						|| rule == grammarAccess.getMemberExpressionRule()
						|| action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_2_1_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_2_1_2_0()) {
					sequence_Arguments_ConcreteTypeArguments_ParameterizedCallExpression(context, (ParameterizedCallExpression) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_2_0_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTargetAction_1_2_1_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_2_2_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getTaggedTemplateStringTargetAction_1_2_3_0_0()) {
					sequence_Arguments_LeftHandSideExpression_IndexedAccessExpression_1_2_1_0_ParameterizedCallExpression_1_2_0_0_ParameterizedPropertyAccessExpression_1_2_2_0_TaggedTemplateString_1_2_3_0_0(context, (ParameterizedCallExpression) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION:
				if (rule == grammarAccess.getJSXElementNameExpressionRule()
						|| action == grammarAccess.getJSXElementNameExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_0()) {
					sequence_ConcreteTypeArguments_JSXElementNameExpression_ParameterizedPropertyAccessExpressionTail(context, (ParameterizedPropertyAccessExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getLeftHandSideExpressionRule()
						|| rule == grammarAccess.getPostfixExpressionRule()
						|| action == grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0()
						|| rule == grammarAccess.getCastExpressionRule()
						|| action == grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getUnaryExpressionRule()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getShiftExpressionRule()
						|| action == grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getEqualityExpressionRule()
						|| action == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseANDExpressionRule()
						|| action == grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseXORExpressionRule()
						|| action == grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseORExpressionRule()
						|| action == grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_ConcreteTypeArguments_LeftHandSideExpression_MemberExpression_ParameterizedPropertyAccessExpressionTail(context, (ParameterizedPropertyAccessExpression) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_2_0_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTargetAction_1_2_1_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_2_2_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getTaggedTemplateStringTargetAction_1_2_3_0_0()) {
					sequence_ConcreteTypeArguments_LeftHandSideExpression_ParameterizedPropertyAccessExpressionTail_IndexedAccessExpression_1_2_1_0_ParameterizedCallExpression_1_2_0_0_ParameterizedPropertyAccessExpression_1_2_2_0_TaggedTemplateString_1_2_3_0_0(context, (ParameterizedPropertyAccessExpression) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_1_3_3_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_3_3_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_1_3_3_2_0()) {
					sequence_ConcreteTypeArguments_MemberExpression_ParameterizedPropertyAccessExpressionTail_IndexedAccessExpression_1_3_3_0_0_ParameterizedPropertyAccessExpression_1_3_3_1_0_TaggedTemplateString_1_3_3_2_0(context, (ParameterizedPropertyAccessExpression) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_2_1_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_2_1_2_0()) {
					sequence_ConcreteTypeArguments_MemberExpression_ParameterizedPropertyAccessExpressionTail_IndexedAccessExpression_2_1_0_0_ParameterizedPropertyAccessExpression_2_1_1_0_TaggedTemplateString_2_1_2_0(context, (ParameterizedPropertyAccessExpression) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0()
						|| rule == grammarAccess.getMemberExpressionRule()) {
					sequence_ConcreteTypeArguments_MemberExpression_ParameterizedPropertyAccessExpressionTail(context, (ParameterizedPropertyAccessExpression) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.PAREN_EXPRESSION:
				sequence_ParenExpression(context, (ParenExpression) semanticObject); 
				return; 
			case N4JSPackage.POSTFIX_EXPRESSION:
				sequence_PostfixExpression(context, (PostfixExpression) semanticObject); 
				return; 
			case N4JSPackage.PROMISIFY_EXPRESSION:
				sequence_PromisifyExpression(context, (PromisifyExpression) semanticObject); 
				return; 
			case N4JSPackage.PROPERTY_ASSIGNMENT_ANNOTATION_LIST:
				sequence_PropertyAssignmentAnnotationList(context, (PropertyAssignmentAnnotationList) semanticObject); 
				return; 
			case N4JSPackage.PROPERTY_GETTER_DECLARATION:
				if (rule == grammarAccess.getAnnotatedPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment_BogusTypeRefFragment_ColonSepDeclaredTypeRef_GetterHeader(context, (PropertyGetterDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment_BogusTypeRefFragment_ColonSepDeclaredTypeRef_GetterHeader_PropertyGetterDeclaration(context, (PropertyGetterDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPropertyGetterDeclarationRule()) {
					sequence_BogusTypeRefFragment_ColonSepDeclaredTypeRef_GetterHeader_PropertyGetterDeclaration(context, (PropertyGetterDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.PROPERTY_METHOD_DECLARATION:
				if (rule == grammarAccess.getPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment_MethodParamsAndBody_PropertyMethodDeclaration_StrictFormalParameters_TypeVariables(context, (PropertyMethodDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotatedPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment_MethodParamsAndBody_StrictFormalParameters_TypeVariables(context, (PropertyMethodDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPropertyMethodDeclarationRule()) {
					sequence_MethodParamsAndBody_PropertyMethodDeclaration_StrictFormalParameters_TypeVariables(context, (PropertyMethodDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR:
				if (rule == grammarAccess.getAnnotatedPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment(context, (PropertyNameValuePair) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment_PropertyNameValuePair(context, (PropertyNameValuePair) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPropertyNameValuePairRule()) {
					sequence_PropertyNameValuePair(context, (PropertyNameValuePair) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME:
				if (rule == grammarAccess.getAnnotatedPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment(context, (PropertyNameValuePairSingleName) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment_PropertyNameValuePairSingleName(context, (PropertyNameValuePairSingleName) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPropertyNameValuePairSingleNameRule()) {
					sequence_PropertyNameValuePairSingleName(context, (PropertyNameValuePairSingleName) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.PROPERTY_SETTER_DECLARATION:
				if (rule == grammarAccess.getAnnotatedPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment(context, (PropertySetterDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPropertyAssignmentRule()) {
					sequence_AnnotatedPropertyAssignment_PropertySetterDeclaration(context, (PropertySetterDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPropertySetterDeclarationRule()) {
					sequence_PropertySetterDeclaration(context, (PropertySetterDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.REGULAR_EXPRESSION_LITERAL:
				sequence_RegularExpressionLiteral(context, (RegularExpressionLiteral) semanticObject); 
				return; 
			case N4JSPackage.RELATIONAL_EXPRESSION:
				sequence_RelationalExpression(context, (RelationalExpression) semanticObject); 
				return; 
			case N4JSPackage.RETURN_STATEMENT:
				sequence_ReturnStatement(context, (ReturnStatement) semanticObject); 
				return; 
			case N4JSPackage.SCIENTIFIC_INT_LITERAL:
				sequence_ScientificIntLiteral(context, (ScientificIntLiteral) semanticObject); 
				return; 
			case N4JSPackage.SCRIPT:
				sequence_Script(context, (Script) semanticObject); 
				return; 
			case N4JSPackage.SHIFT_EXPRESSION:
				sequence_ShiftExpression(context, (ShiftExpression) semanticObject); 
				return; 
			case N4JSPackage.STRING_LITERAL:
				sequence_StringLiteral(context, (StringLiteral) semanticObject); 
				return; 
			case N4JSPackage.SUPER_LITERAL:
				sequence_SuperLiteral(context, (SuperLiteral) semanticObject); 
				return; 
			case N4JSPackage.SWITCH_STATEMENT:
				sequence_SwitchStatement(context, (SwitchStatement) semanticObject); 
				return; 
			case N4JSPackage.TAGGED_TEMPLATE_STRING:
				if (action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_2_0_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTargetAction_1_2_1_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_2_2_0()
						|| action == grammarAccess.getLeftHandSideExpressionAccess().getTaggedTemplateStringTargetAction_1_2_3_0_0()) {
					sequence_LeftHandSideExpression_IndexedAccessExpression_1_2_1_0_ParameterizedCallExpression_1_2_0_0_ParameterizedPropertyAccessExpression_1_2_2_0_TaggedTemplateString_1_2_3_0_0(context, (TaggedTemplateString) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getLeftHandSideExpressionRule()
						|| rule == grammarAccess.getPostfixExpressionRule()
						|| action == grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0()
						|| rule == grammarAccess.getCastExpressionRule()
						|| action == grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getUnaryExpressionRule()
						|| rule == grammarAccess.getMultiplicativeExpressionRule()
						|| action == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getAdditiveExpressionRule()
						|| action == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getShiftExpressionRule()
						|| action == grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getRelationalExpressionRule()
						|| action == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0()
						|| rule == grammarAccess.getEqualityExpressionRule()
						|| action == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseANDExpressionRule()
						|| action == grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseXORExpressionRule()
						|| action == grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getBitwiseORExpressionRule()
						|| action == grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalANDExpressionRule()
						|| action == grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getLogicalORExpressionRule()
						|| action == grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0()
						|| rule == grammarAccess.getConditionalExpressionRule()
						|| action == grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0()
						|| rule == grammarAccess.getAssignmentExpressionRule()
						|| action == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0()
						|| rule == grammarAccess.getExpressionRule()
						|| action == grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0()) {
					sequence_LeftHandSideExpression_MemberExpression(context, (TaggedTemplateString) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_1_3_3_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_3_3_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_1_3_3_2_0()) {
					sequence_MemberExpression_IndexedAccessExpression_1_3_3_0_0_ParameterizedPropertyAccessExpression_1_3_3_1_0_TaggedTemplateString_1_3_3_2_0(context, (TaggedTemplateString) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_2_1_0_0()
						|| action == grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0()
						|| action == grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_2_1_2_0()) {
					sequence_MemberExpression_IndexedAccessExpression_2_1_0_0_ParameterizedPropertyAccessExpression_2_1_1_0_TaggedTemplateString_2_1_2_0(context, (TaggedTemplateString) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0()
						|| rule == grammarAccess.getMemberExpressionRule()) {
					sequence_MemberExpression(context, (TaggedTemplateString) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.TEMPLATE_LITERAL:
				sequence_TemplateLiteral(context, (TemplateLiteral) semanticObject); 
				return; 
			case N4JSPackage.TEMPLATE_SEGMENT:
				if (rule == grammarAccess.getNoSubstitutionTemplateRule()) {
					sequence_NoSubstitutionTemplate(context, (TemplateSegment) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTemplateHeadRule()) {
					sequence_TemplateHead(context, (TemplateSegment) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTemplateMiddleRule()) {
					sequence_TemplateMiddle(context, (TemplateSegment) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTemplateTailRule()) {
					sequence_TemplateTail(context, (TemplateSegment) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.THIS_LITERAL:
				sequence_ThisLiteral(context, (ThisLiteral) semanticObject); 
				return; 
			case N4JSPackage.THROW_STATEMENT:
				sequence_ThrowStatement(context, (ThrowStatement) semanticObject); 
				return; 
			case N4JSPackage.TRY_STATEMENT:
				sequence_TryStatement(context, (TryStatement) semanticObject); 
				return; 
			case N4JSPackage.TYPE_REF_ANNOTATION_ARGUMENT:
				sequence_TypeRefAnnotationArgument(context, (TypeRefAnnotationArgument) semanticObject); 
				return; 
			case N4JSPackage.UNARY_EXPRESSION:
				sequence_UnaryExpression(context, (UnaryExpression) semanticObject); 
				return; 
			case N4JSPackage.VARIABLE_BINDING:
				if (rule == grammarAccess.getVariableDeclarationOrBindingRule() && (ImmutableSet.of(grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(0/*In*/), grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(1/*Yield*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(0/*In*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(1/*Yield*/)).equals(parameters)
				 			|| parameters.isEmpty())
						|| rule == grammarAccess.getVariableBindingRule() && (ImmutableSet.of(grammarAccess.getVariableBindingRule().getParameters().get(0/*In*/), grammarAccess.getVariableBindingRule().getParameters().get(1/*Yield*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableBindingRule().getParameters().get(0/*In*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableBindingRule().getParameters().get(1/*Yield*/)).equals(parameters)
				 			|| parameters.isEmpty())) {
					sequence_VariableBinding$OptionalInit$false$(context, (VariableBinding) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getVariableDeclarationOrBindingRule() && (ImmutableSet.of(grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(0/*In*/), grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(1/*Yield*/), grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(2/*OptionalInit*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(0/*In*/), grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(2/*OptionalInit*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(1/*Yield*/), grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(2/*OptionalInit*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationOrBindingRule().getParameters().get(2/*OptionalInit*/)).equals(parameters))
						|| rule == grammarAccess.getVariableBindingRule() && (ImmutableSet.of(grammarAccess.getVariableBindingRule().getParameters().get(0/*In*/), grammarAccess.getVariableBindingRule().getParameters().get(1/*Yield*/), grammarAccess.getVariableBindingRule().getParameters().get(2/*OptionalInit*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableBindingRule().getParameters().get(0/*In*/), grammarAccess.getVariableBindingRule().getParameters().get(2/*OptionalInit*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableBindingRule().getParameters().get(1/*Yield*/), grammarAccess.getVariableBindingRule().getParameters().get(2/*OptionalInit*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableBindingRule().getParameters().get(2/*OptionalInit*/)).equals(parameters))) {
					sequence_VariableBinding(context, (VariableBinding) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.VARIABLE_DECLARATION:
				if (rule == grammarAccess.getBindingIdentifierAsVariableDeclarationRule()) {
					sequence_BindingIdentifierAsVariableDeclaration(context, (VariableDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getVariableDeclarationOrBindingRule()
						|| rule == grammarAccess.getVariableDeclarationRule() && (ImmutableSet.of(grammarAccess.getVariableDeclarationRule().getParameters().get(0/*In*/), grammarAccess.getVariableDeclarationRule().getParameters().get(1/*Yield*/), grammarAccess.getVariableDeclarationRule().getParameters().get(2/*AllowType*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationRule().getParameters().get(0/*In*/), grammarAccess.getVariableDeclarationRule().getParameters().get(2/*AllowType*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationRule().getParameters().get(1/*Yield*/), grammarAccess.getVariableDeclarationRule().getParameters().get(2/*AllowType*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationRule().getParameters().get(2/*AllowType*/)).equals(parameters))) {
					sequence_ColonSepDeclaredTypeRef_VariableDeclaration_VariableDeclarationImpl(context, (VariableDeclaration) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getVariableDeclarationRule() && (ImmutableSet.of(grammarAccess.getVariableDeclarationRule().getParameters().get(0/*In*/), grammarAccess.getVariableDeclarationRule().getParameters().get(1/*Yield*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationRule().getParameters().get(0/*In*/)).equals(parameters)
				 			|| ImmutableSet.of(grammarAccess.getVariableDeclarationRule().getParameters().get(1/*Yield*/)).equals(parameters)
				 			|| parameters.isEmpty())) {
					sequence_VariableDeclaration$AllowType$false$_VariableDeclarationImpl(context, (VariableDeclaration) semanticObject); 
					return; 
				}
				else break;
			case N4JSPackage.VARIABLE_STATEMENT:
				sequence_VariableStatement(context, (VariableStatement) semanticObject); 
				return; 
			case N4JSPackage.WHILE_STATEMENT:
				sequence_WhileStatement(context, (WhileStatement) semanticObject); 
				return; 
			case N4JSPackage.WITH_STATEMENT:
				sequence_WithStatement(context, (WithStatement) semanticObject); 
				return; 
			case N4JSPackage.YIELD_EXPRESSION:
				sequence_YieldExpression(context, (YieldExpression) semanticObject); 
				return; 
			}
		else if (epackage == N4JSXPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case N4JSXPackage.JSX_ELEMENT:
				sequence_JSXAttributes_JSXClosingElement_JSXElement(context, (JSXElement) semanticObject); 
				return; 
			case N4JSXPackage.JSX_ELEMENT_NAME:
				sequence_JSXElementName(context, (JSXElementName) semanticObject); 
				return; 
			case N4JSXPackage.JSX_EXPRESSION:
				sequence_JSXExpression(context, (JSXExpression) semanticObject); 
				return; 
			case N4JSXPackage.JSX_PROPERTY_ATTRIBUTE:
				sequence_JSXPropertyAttribute(context, (JSXPropertyAttribute) semanticObject); 
				return; 
			case N4JSXPackage.JSX_SPREAD_ATTRIBUTE:
				sequence_JSXSpreadAttribute(context, (JSXSpreadAttribute) semanticObject); 
				return; 
			}
		else if (epackage == TypeRefsPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION:
				if (rule == grammarAccess.getTypeRefForCastRule()) {
					sequence_ArrowFunctionTypeExpression_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ArrowFunctionTypeExpression_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRefWithModifiers(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getArrowFunctionTypeExpressionRule()) {
					sequence_ArrowFunctionTypeExpression_TAnonymousFormalParameterList(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()
						|| rule == grammarAccess.getFunctionTypeExpressionOLDRule()) {
					sequence_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRefWithModifiers(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION:
				if (rule == grammarAccess.getTypeRefForCastRule()
						|| rule == grammarAccess.getTypeRefWithoutModifiersRule()
						|| rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getIntersectionTypeExpressionOLDRule()) {
					sequence_IntersectionTypeExpressionOLD(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_IntersectionTypeExpressionOLD_TypeRefWithModifiers(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_IntersectionTypeExpression_IntersectionTypeExpressionOLD_TypeRefWithModifiers(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF:
				if (rule == grammarAccess.getArrayTypeRefRule()) {
					sequence_ArrayTypeRef(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefForCastRule()
						|| rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()) {
					sequence_ArrayTypeRef_TypeAndTypeArguments_TypeArguments(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ArrayTypeRef_TypeAndTypeArguments_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefNominalRule()) {
					sequence_TypeAndTypeArguments_TypeArguments(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_TypeAndTypeArguments_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_TypeAndTypeArguments_TypeArguments_TypeRefWithoutModifiers(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL:
				if (rule == grammarAccess.getTypeRefForCastRule()
						|| rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getParameterizedTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefStructuralRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeAndTypeArguments_TypeArguments(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeAndTypeArguments_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeAndTypeArguments_TypeArguments_TypeRefWithoutModifiers(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.THIS_TYPE_REF_NOMINAL:
				if (rule == grammarAccess.getTypeRefForCastRule()
						|| rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getThisTypeRefRule()
						|| rule == grammarAccess.getThisTypeRefNominalRule()) {
					sequence_ThisTypeRefNominal(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ThisTypeRefNominal_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ThisTypeRefNominal_TypeRefWithoutModifiers(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL:
				if (rule == grammarAccess.getTypeRefForCastRule()
						|| rule == grammarAccess.getThisTypeRefRule()
						|| rule == grammarAccess.getThisTypeRefStructuralRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithoutModifiers(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.TYPE_TYPE_REF:
				if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TypeRefWithModifiers_TypeTypeRef(context, (TypeTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefForCastRule()
						|| rule == grammarAccess.getTypeRefWithoutModifiersRule()
						|| rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getTypeTypeRefRule()) {
					sequence_TypeTypeRef(context, (TypeTypeRef) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.UNION_TYPE_EXPRESSION:
				if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_TypeRefWithModifiers_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TypeRef_TypeRefWithModifiers_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefForCastRule()
						|| rule == grammarAccess.getTypeRefWithoutModifiersRule()
						|| rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getUnionTypeExpressionOLDRule()) {
					sequence_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.WILDCARD:
				if (rule == grammarAccess.getWildcardNewNotationRule()) {
					sequence_WildcardNewNotation(context, (Wildcard) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getWildcardRule()) {
					sequence_Wildcard(context, (Wildcard) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeArgumentRule()) {
					sequence_Wildcard_WildcardNewNotation(context, (Wildcard) semanticObject); 
					return; 
				}
				else break;
			}
		else if (epackage == TypesPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case TypesPackage.TANONYMOUS_FORMAL_PARAMETER:
				sequence_ColonSepTypeRef_DefaultFormalParameter_TAnonymousFormalParameter(context, (TAnonymousFormalParameter) semanticObject); 
				return; 
			case TypesPackage.TFORMAL_PARAMETER:
				sequence_ColonSepTypeRef_DefaultFormalParameter_TFormalParameter(context, (TFormalParameter) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_FIELD:
				sequence_ColonSepTypeRef_TStructField(context, (TStructField) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_GETTER:
				sequence_ColonSepDeclaredTypeRef_TStructGetter(context, (TStructGetter) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_METHOD:
				sequence_ColonSepReturnTypeRef_TAnonymousFormalParameterList_TStructMethod_TypeVariables(context, (TStructMethod) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_SETTER:
				sequence_TStructSetter(context, (TStructSetter) semanticObject); 
				return; 
			case TypesPackage.TYPE_VARIABLE:
				sequence_TypeVariable(context, (TypeVariable) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     JSXElementNameExpression returns ParameterizedPropertyAccessExpression
	 *     JSXElementNameExpression.ParameterizedPropertyAccessExpression_1_0 returns ParameterizedPropertyAccessExpression
	 *
	 * Constraint:
	 *     (
	 *         target=JSXElementNameExpression_ParameterizedPropertyAccessExpression_1_0 
	 *         (typeArgs+=TypeRef typeArgs+=TypeRef*)? 
	 *         property=[IdentifiableElement|IdentifierName]
	 *     )
	 */
	protected void sequence_ConcreteTypeArguments_JSXElementNameExpression_ParameterizedPropertyAccessExpressionTail(ISerializationContext context, ParameterizedPropertyAccessExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     PrimaryExpression<Yield> returns JSXElement
	 *     PrimaryExpression returns JSXElement
	 *     JSXElement returns JSXElement
	 *     JSXChild returns JSXElement
	 *     LeftHandSideExpression<Yield> returns JSXElement
	 *     LeftHandSideExpression returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<PostfixExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<CastExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<UnaryExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<MultiplicativeExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<AdditiveExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<ShiftExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<RelationalExpression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<RelationalExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<RelationalExpression.In,RelationalExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<EqualityExpression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<EqualityExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<BitwiseANDExpression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<BitwiseXORExpression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<BitwiseORExpression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<BitwiseORExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<LogicalANDExpression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<LogicalANDExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<LogicalORExpression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<LogicalORExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<ConditionalExpression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<ConditionalExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<AssignmentExpression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<AssignmentExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<Expression.In> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<Expression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0<Expression.In,Expression.Yield> returns JSXElement
	 *     LeftHandSideExpression.ParameterizedCallExpression_1_0 returns JSXElement
	 *     MemberExpression<Yield> returns JSXElement
	 *     MemberExpression returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<LeftHandSideExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<PostfixExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<CastExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<UnaryExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<MultiplicativeExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<AdditiveExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<ShiftExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<RelationalExpression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<RelationalExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<RelationalExpression.In,RelationalExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<EqualityExpression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<EqualityExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<BitwiseANDExpression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<BitwiseXORExpression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<BitwiseORExpression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<LogicalANDExpression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<LogicalORExpression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<ConditionalExpression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<AssignmentExpression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<Expression.In> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<Expression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     MemberExpression.IndexedAccessExpression_2_1_0_0 returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<LeftHandSideExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<PostfixExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<CastExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<UnaryExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<MultiplicativeExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<AdditiveExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<ShiftExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<RelationalExpression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<RelationalExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<RelationalExpression.In,RelationalExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<EqualityExpression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<EqualityExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<BitwiseANDExpression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<BitwiseXORExpression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<BitwiseORExpression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<BitwiseORExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<LogicalANDExpression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<LogicalANDExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<LogicalORExpression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<LogicalORExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<ConditionalExpression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<ConditionalExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<AssignmentExpression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<AssignmentExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<Expression.In> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<Expression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0<Expression.In,Expression.Yield> returns JSXElement
	 *     MemberExpression.ParameterizedPropertyAccessExpression_2_1_1_0 returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<LeftHandSideExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<PostfixExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<CastExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<UnaryExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<MultiplicativeExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<AdditiveExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<ShiftExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<RelationalExpression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<RelationalExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<RelationalExpression.In,RelationalExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<EqualityExpression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<EqualityExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<BitwiseANDExpression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<BitwiseXORExpression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<BitwiseORExpression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<BitwiseORExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<LogicalANDExpression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<LogicalANDExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<LogicalORExpression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<LogicalORExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<ConditionalExpression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<ConditionalExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<AssignmentExpression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<AssignmentExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<Expression.In> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<Expression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0<Expression.In,Expression.Yield> returns JSXElement
	 *     MemberExpression.TaggedTemplateString_2_1_2_0 returns JSXElement
	 *     PostfixExpression<Yield> returns JSXElement
	 *     PostfixExpression returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<CastExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<UnaryExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<MultiplicativeExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<AdditiveExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<ShiftExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<RelationalExpression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<RelationalExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<RelationalExpression.In,RelationalExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<EqualityExpression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<EqualityExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<BitwiseANDExpression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<BitwiseXORExpression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<BitwiseORExpression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<LogicalANDExpression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<LogicalORExpression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<ConditionalExpression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<AssignmentExpression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<Expression.In> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<Expression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     PostfixExpression.PostfixExpression_1_0_0 returns JSXElement
	 *     CastExpression<Yield> returns JSXElement
	 *     CastExpression returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<UnaryExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<MultiplicativeExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<AdditiveExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<ShiftExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<RelationalExpression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<RelationalExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<RelationalExpression.In,RelationalExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<EqualityExpression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<EqualityExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<BitwiseANDExpression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<BitwiseXORExpression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<BitwiseORExpression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<LogicalANDExpression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<LogicalORExpression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<ConditionalExpression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     CastExpression.CastExpression_1_0_0_0 returns JSXElement
	 *     UnaryExpression<Yield> returns JSXElement
	 *     UnaryExpression returns JSXElement
	 *     MultiplicativeExpression<Yield> returns JSXElement
	 *     MultiplicativeExpression returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<AdditiveExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<ShiftExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<RelationalExpression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<RelationalExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<RelationalExpression.In,RelationalExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<EqualityExpression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<EqualityExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<BitwiseANDExpression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<BitwiseXORExpression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<BitwiseORExpression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<LogicalANDExpression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<LogicalORExpression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<ConditionalExpression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     MultiplicativeExpression.MultiplicativeExpression_1_0_0_0 returns JSXElement
	 *     AdditiveExpression<Yield> returns JSXElement
	 *     AdditiveExpression returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<ShiftExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<RelationalExpression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<RelationalExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<RelationalExpression.In,RelationalExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<EqualityExpression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<EqualityExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<BitwiseANDExpression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<BitwiseXORExpression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<BitwiseORExpression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<LogicalANDExpression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<LogicalORExpression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<ConditionalExpression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     AdditiveExpression.AdditiveExpression_1_0_0_0 returns JSXElement
	 *     ShiftExpression<Yield> returns JSXElement
	 *     ShiftExpression returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<RelationalExpression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<RelationalExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<RelationalExpression.In,RelationalExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<EqualityExpression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<EqualityExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<BitwiseANDExpression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<BitwiseXORExpression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<BitwiseORExpression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<LogicalANDExpression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<LogicalORExpression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<ConditionalExpression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<AssignmentExpression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<Expression.In> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<Expression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     ShiftExpression.ShiftExpression_1_0_0 returns JSXElement
	 *     RelationalExpression<In,Yield> returns JSXElement
	 *     RelationalExpression<In> returns JSXElement
	 *     RelationalExpression<Yield> returns JSXElement
	 *     RelationalExpression returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<In,Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<EqualityExpression.In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<EqualityExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<EqualityExpression.In,EqualityExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<BitwiseANDExpression.In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<BitwiseXORExpression.In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<BitwiseORExpression.In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<LogicalANDExpression.In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<LogicalORExpression.In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<ConditionalExpression.In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<AssignmentExpression.In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<Expression.In> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<Expression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     RelationalExpression.RelationalExpression_1_0_0 returns JSXElement
	 *     EqualityExpression<In,Yield> returns JSXElement
	 *     EqualityExpression<In> returns JSXElement
	 *     EqualityExpression<Yield> returns JSXElement
	 *     EqualityExpression returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<In,Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<In> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<BitwiseANDExpression.In> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<BitwiseANDExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<BitwiseANDExpression.In,BitwiseANDExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<BitwiseXORExpression.In> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<BitwiseORExpression.In> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<LogicalANDExpression.In> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<LogicalORExpression.In> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<ConditionalExpression.In> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     EqualityExpression.EqualityExpression_1_0_0_0 returns JSXElement
	 *     BitwiseANDExpression<In,Yield> returns JSXElement
	 *     BitwiseANDExpression<In> returns JSXElement
	 *     BitwiseANDExpression<Yield> returns JSXElement
	 *     BitwiseANDExpression returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<In,Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<In> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<BitwiseXORExpression.In> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<BitwiseXORExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<BitwiseXORExpression.In,BitwiseXORExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<BitwiseORExpression.In> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<LogicalANDExpression.In> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<LogicalORExpression.In> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<ConditionalExpression.In> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     BitwiseANDExpression.BinaryBitwiseExpression_1_0_0_0 returns JSXElement
	 *     BitwiseXORExpression<In,Yield> returns JSXElement
	 *     BitwiseXORExpression<In> returns JSXElement
	 *     BitwiseXORExpression<Yield> returns JSXElement
	 *     BitwiseXORExpression returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<In,Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<In> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<BitwiseORExpression.In> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<BitwiseORExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<BitwiseORExpression.In,BitwiseORExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalANDExpression.In> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalORExpression.In> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<ConditionalExpression.In> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     BitwiseXORExpression.BinaryBitwiseExpression_1_0_0_0 returns JSXElement
	 *     BitwiseORExpression<In,Yield> returns JSXElement
	 *     BitwiseORExpression<In> returns JSXElement
	 *     BitwiseORExpression<Yield> returns JSXElement
	 *     BitwiseORExpression returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<In,Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<In> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalANDExpression.In> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalANDExpression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalANDExpression.In,LogicalANDExpression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalORExpression.In> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<ConditionalExpression.In> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     BitwiseORExpression.BinaryBitwiseExpression_1_0_0_0 returns JSXElement
	 *     LogicalANDExpression<In,Yield> returns JSXElement
	 *     LogicalANDExpression<In> returns JSXElement
	 *     LogicalANDExpression<Yield> returns JSXElement
	 *     LogicalANDExpression returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<In,Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<In> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<LogicalORExpression.In> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<LogicalORExpression.Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<LogicalORExpression.In,LogicalORExpression.Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<ConditionalExpression.In> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     LogicalANDExpression.BinaryLogicalExpression_1_0_0_0 returns JSXElement
	 *     LogicalORExpression<In,Yield> returns JSXElement
	 *     LogicalORExpression<In> returns JSXElement
	 *     LogicalORExpression<Yield> returns JSXElement
	 *     LogicalORExpression returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<In,Yield> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<In> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<Yield> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<ConditionalExpression.In> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<ConditionalExpression.Yield> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<ConditionalExpression.In,ConditionalExpression.Yield> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     LogicalORExpression.BinaryLogicalExpression_1_0_0_0 returns JSXElement
	 *     ConditionalExpression<In,Yield> returns JSXElement
	 *     ConditionalExpression<In> returns JSXElement
	 *     ConditionalExpression<Yield> returns JSXElement
	 *     ConditionalExpression returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0<In,Yield> returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0<In> returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0<Yield> returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0<AssignmentExpression.In> returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0<AssignmentExpression.Yield> returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0<AssignmentExpression.In,AssignmentExpression.Yield> returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0<Expression.In> returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0<Expression.Yield> returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     ConditionalExpression.ConditionalExpression_1_0_0_0 returns JSXElement
	 *     AssignmentExpression<In,Yield> returns JSXElement
	 *     AssignmentExpression<In> returns JSXElement
	 *     AssignmentExpression<Yield> returns JSXElement
	 *     AssignmentExpression returns JSXElement
	 *     AssignmentExpression.AssignmentExpression_4_1_0_0_0<In,Yield> returns JSXElement
	 *     AssignmentExpression.AssignmentExpression_4_1_0_0_0<In> returns JSXElement
	 *     AssignmentExpression.AssignmentExpression_4_1_0_0_0<Yield> returns JSXElement
	 *     AssignmentExpression.AssignmentExpression_4_1_0_0_0<Expression.In> returns JSXElement
	 *     AssignmentExpression.AssignmentExpression_4_1_0_0_0<Expression.Yield> returns JSXElement
	 *     AssignmentExpression.AssignmentExpression_4_1_0_0_0<Expression.In,Expression.Yield> returns JSXElement
	 *     AssignmentExpression.AssignmentExpression_4_1_0_0_0 returns JSXElement
	 *     Expression<In,Yield> returns JSXElement
	 *     Expression<In> returns JSXElement
	 *     Expression<Yield> returns JSXElement
	 *     Expression returns JSXElement
	 *     Expression.CommaExpression_1_0<In,Yield> returns JSXElement
	 *     Expression.CommaExpression_1_0<In> returns JSXElement
	 *     Expression.CommaExpression_1_0<Yield> returns JSXElement
	 *     Expression.CommaExpression_1_0 returns JSXElement
	 *
	 * Constraint:
	 *     (jsxElementName=JSXElementName jsxAttributes+=JSXAttribute* (jsxChildren+=JSXChild* jsxClosingName=JSXElementName)?)
	 */
	protected void sequence_JSXAttributes_JSXClosingElement_JSXElement(ISerializationContext context, JSXElement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     JSXElementName returns JSXElementName
	 *
	 * Constraint:
	 *     expression=JSXElementNameExpression
	 */
	protected void sequence_JSXElementName(ISerializationContext context, JSXElementName semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, N4JSXPackage.Literals.JSX_ELEMENT_NAME__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, N4JSXPackage.Literals.JSX_ELEMENT_NAME__EXPRESSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getJSXElementNameAccess().getExpressionJSXElementNameExpressionParserRuleCall_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     JSXChild returns JSXExpression
	 *     JSXExpression returns JSXExpression
	 *
	 * Constraint:
	 *     expression=AssignmentExpression
	 */
	protected void sequence_JSXExpression(ISerializationContext context, JSXExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, N4JSXPackage.Literals.JSX_EXPRESSION__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, N4JSXPackage.Literals.JSX_EXPRESSION__EXPRESSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getJSXExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     JSXAttribute returns JSXPropertyAttribute
	 *     JSXPropertyAttribute returns JSXPropertyAttribute
	 *
	 * Constraint:
	 *     (property=[IdentifiableElement|IdentifierName] (jsxAttributeValue=StringLiteral | jsxAttributeValue=AssignmentExpression)?)
	 */
	protected void sequence_JSXPropertyAttribute(ISerializationContext context, JSXPropertyAttribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     JSXAttribute returns JSXSpreadAttribute
	 *     JSXSpreadAttribute returns JSXSpreadAttribute
	 *
	 * Constraint:
	 *     expression=AssignmentExpression
	 */
	protected void sequence_JSXSpreadAttribute(ISerializationContext context, JSXSpreadAttribute semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, N4JSXPackage.Literals.JSX_SPREAD_ATTRIBUTE__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, N4JSXPackage.Literals.JSX_SPREAD_ATTRIBUTE__EXPRESSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getJSXSpreadAttributeAccess().getExpressionAssignmentExpressionParserRuleCall_2_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
}
