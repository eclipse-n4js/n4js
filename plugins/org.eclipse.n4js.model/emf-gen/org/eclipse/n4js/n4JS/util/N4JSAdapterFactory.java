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
package org.eclipse.n4js.n4JS.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.n4JS.*;

import org.eclipse.n4js.ts.typeRefs.Versionable;
import org.eclipse.n4js.ts.typeRefs.VersionedReference;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4JS.N4JSPackage
 * @generated
 */
public class N4JSAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static N4JSPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4JSAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = N4JSPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4JSSwitch<Adapter> modelSwitch =
		new N4JSSwitch<Adapter>() {
			@Override
			public Adapter caseNamedElement(NamedElement object) {
				return createNamedElementAdapter();
			}
			@Override
			public Adapter caseControlFlowElement(ControlFlowElement object) {
				return createControlFlowElementAdapter();
			}
			@Override
			public Adapter caseScript(Script object) {
				return createScriptAdapter();
			}
			@Override
			public Adapter caseScriptElement(ScriptElement object) {
				return createScriptElementAdapter();
			}
			@Override
			public Adapter caseExportDeclaration(ExportDeclaration object) {
				return createExportDeclarationAdapter();
			}
			@Override
			public Adapter caseExportSpecifier(ExportSpecifier object) {
				return createExportSpecifierAdapter();
			}
			@Override
			public Adapter caseExportableElement(ExportableElement object) {
				return createExportableElementAdapter();
			}
			@Override
			public Adapter caseImportDeclaration(ImportDeclaration object) {
				return createImportDeclarationAdapter();
			}
			@Override
			public Adapter caseImportSpecifier(ImportSpecifier object) {
				return createImportSpecifierAdapter();
			}
			@Override
			public Adapter caseNamedImportSpecifier(NamedImportSpecifier object) {
				return createNamedImportSpecifierAdapter();
			}
			@Override
			public Adapter caseDefaultImportSpecifier(DefaultImportSpecifier object) {
				return createDefaultImportSpecifierAdapter();
			}
			@Override
			public Adapter caseNamespaceImportSpecifier(NamespaceImportSpecifier object) {
				return createNamespaceImportSpecifierAdapter();
			}
			@Override
			public Adapter caseTypeProvidingElement(TypeProvidingElement object) {
				return createTypeProvidingElementAdapter();
			}
			@Override
			public Adapter caseTypedElement(TypedElement object) {
				return createTypedElementAdapter();
			}
			@Override
			public Adapter caseVariableEnvironmentElement(VariableEnvironmentElement object) {
				return createVariableEnvironmentElementAdapter();
			}
			@Override
			public Adapter caseThisTarget(ThisTarget object) {
				return createThisTargetAdapter();
			}
			@Override
			public Adapter caseThisArgProvider(ThisArgProvider object) {
				return createThisArgProviderAdapter();
			}
			@Override
			public Adapter caseVariable(Variable object) {
				return createVariableAdapter();
			}
			@Override
			public Adapter caseAnnotableElement(AnnotableElement object) {
				return createAnnotableElementAdapter();
			}
			@Override
			public Adapter caseAnnotableScriptElement(AnnotableScriptElement object) {
				return createAnnotableScriptElementAdapter();
			}
			@Override
			public Adapter caseAnnotableExpression(AnnotableExpression object) {
				return createAnnotableExpressionAdapter();
			}
			@Override
			public Adapter caseAbstractAnnotationList(AbstractAnnotationList object) {
				return createAbstractAnnotationListAdapter();
			}
			@Override
			public Adapter caseAnnotationList(AnnotationList object) {
				return createAnnotationListAdapter();
			}
			@Override
			public Adapter caseExpressionAnnotationList(ExpressionAnnotationList object) {
				return createExpressionAnnotationListAdapter();
			}
			@Override
			public Adapter caseAnnotation(Annotation object) {
				return createAnnotationAdapter();
			}
			@Override
			public Adapter caseAnnotationArgument(AnnotationArgument object) {
				return createAnnotationArgumentAdapter();
			}
			@Override
			public Adapter caseLiteralAnnotationArgument(LiteralAnnotationArgument object) {
				return createLiteralAnnotationArgumentAdapter();
			}
			@Override
			public Adapter caseTypeRefAnnotationArgument(TypeRefAnnotationArgument object) {
				return createTypeRefAnnotationArgumentAdapter();
			}
			@Override
			public Adapter caseFunctionOrFieldAccessor(FunctionOrFieldAccessor object) {
				return createFunctionOrFieldAccessorAdapter();
			}
			@Override
			public Adapter caseFunctionDefinition(FunctionDefinition object) {
				return createFunctionDefinitionAdapter();
			}
			@Override
			public Adapter caseFieldAccessor(FieldAccessor object) {
				return createFieldAccessorAdapter();
			}
			@Override
			public Adapter caseFunctionDeclaration(FunctionDeclaration object) {
				return createFunctionDeclarationAdapter();
			}
			@Override
			public Adapter caseFunctionExpression(FunctionExpression object) {
				return createFunctionExpressionAdapter();
			}
			@Override
			public Adapter caseArrowFunction(ArrowFunction object) {
				return createArrowFunctionAdapter();
			}
			@Override
			public Adapter caseLocalArgumentsVariable(LocalArgumentsVariable object) {
				return createLocalArgumentsVariableAdapter();
			}
			@Override
			public Adapter caseFormalParameter(FormalParameter object) {
				return createFormalParameterAdapter();
			}
			@Override
			public Adapter caseBlock(Block object) {
				return createBlockAdapter();
			}
			@Override
			public Adapter caseStatement(Statement object) {
				return createStatementAdapter();
			}
			@Override
			public Adapter caseVariableDeclarationContainer(VariableDeclarationContainer object) {
				return createVariableDeclarationContainerAdapter();
			}
			@Override
			public Adapter caseVariableStatement(VariableStatement object) {
				return createVariableStatementAdapter();
			}
			@Override
			public Adapter caseExportedVariableStatement(ExportedVariableStatement object) {
				return createExportedVariableStatementAdapter();
			}
			@Override
			public Adapter caseVariableDeclarationOrBinding(VariableDeclarationOrBinding object) {
				return createVariableDeclarationOrBindingAdapter();
			}
			@Override
			public Adapter caseVariableBinding(VariableBinding object) {
				return createVariableBindingAdapter();
			}
			@Override
			public Adapter caseExportedVariableBinding(ExportedVariableBinding object) {
				return createExportedVariableBindingAdapter();
			}
			@Override
			public Adapter caseVariableDeclaration(VariableDeclaration object) {
				return createVariableDeclarationAdapter();
			}
			@Override
			public Adapter caseExportedVariableDeclaration(ExportedVariableDeclaration object) {
				return createExportedVariableDeclarationAdapter();
			}
			@Override
			public Adapter caseEmptyStatement(EmptyStatement object) {
				return createEmptyStatementAdapter();
			}
			@Override
			public Adapter caseExpressionStatement(ExpressionStatement object) {
				return createExpressionStatementAdapter();
			}
			@Override
			public Adapter caseIfStatement(IfStatement object) {
				return createIfStatementAdapter();
			}
			@Override
			public Adapter caseIterationStatement(IterationStatement object) {
				return createIterationStatementAdapter();
			}
			@Override
			public Adapter caseDoStatement(DoStatement object) {
				return createDoStatementAdapter();
			}
			@Override
			public Adapter caseWhileStatement(WhileStatement object) {
				return createWhileStatementAdapter();
			}
			@Override
			public Adapter caseForStatement(ForStatement object) {
				return createForStatementAdapter();
			}
			@Override
			public Adapter caseLabelRef(LabelRef object) {
				return createLabelRefAdapter();
			}
			@Override
			public Adapter caseContinueStatement(ContinueStatement object) {
				return createContinueStatementAdapter();
			}
			@Override
			public Adapter caseBreakStatement(BreakStatement object) {
				return createBreakStatementAdapter();
			}
			@Override
			public Adapter caseReturnStatement(ReturnStatement object) {
				return createReturnStatementAdapter();
			}
			@Override
			public Adapter caseWithStatement(WithStatement object) {
				return createWithStatementAdapter();
			}
			@Override
			public Adapter caseSwitchStatement(SwitchStatement object) {
				return createSwitchStatementAdapter();
			}
			@Override
			public Adapter caseAbstractCaseClause(AbstractCaseClause object) {
				return createAbstractCaseClauseAdapter();
			}
			@Override
			public Adapter caseCaseClause(CaseClause object) {
				return createCaseClauseAdapter();
			}
			@Override
			public Adapter caseDefaultClause(DefaultClause object) {
				return createDefaultClauseAdapter();
			}
			@Override
			public Adapter caseLabelledStatement(LabelledStatement object) {
				return createLabelledStatementAdapter();
			}
			@Override
			public Adapter caseThrowStatement(ThrowStatement object) {
				return createThrowStatementAdapter();
			}
			@Override
			public Adapter caseTryStatement(TryStatement object) {
				return createTryStatementAdapter();
			}
			@Override
			public Adapter caseAbstractCatchBlock(AbstractCatchBlock object) {
				return createAbstractCatchBlockAdapter();
			}
			@Override
			public Adapter caseCatchBlock(CatchBlock object) {
				return createCatchBlockAdapter();
			}
			@Override
			public Adapter caseCatchVariable(CatchVariable object) {
				return createCatchVariableAdapter();
			}
			@Override
			public Adapter caseFinallyBlock(FinallyBlock object) {
				return createFinallyBlockAdapter();
			}
			@Override
			public Adapter caseDebuggerStatement(DebuggerStatement object) {
				return createDebuggerStatementAdapter();
			}
			@Override
			public Adapter casePrimaryExpression(PrimaryExpression object) {
				return createPrimaryExpressionAdapter();
			}
			@Override
			public Adapter caseParenExpression(ParenExpression object) {
				return createParenExpressionAdapter();
			}
			@Override
			public Adapter caseIdentifierRef(IdentifierRef object) {
				return createIdentifierRefAdapter();
			}
			@Override
			public Adapter caseStrictModeRelevant(StrictModeRelevant object) {
				return createStrictModeRelevantAdapter();
			}
			@Override
			public Adapter caseSuperLiteral(SuperLiteral object) {
				return createSuperLiteralAdapter();
			}
			@Override
			public Adapter caseThisLiteral(ThisLiteral object) {
				return createThisLiteralAdapter();
			}
			@Override
			public Adapter caseArrayLiteral(ArrayLiteral object) {
				return createArrayLiteralAdapter();
			}
			@Override
			public Adapter caseArrayElement(ArrayElement object) {
				return createArrayElementAdapter();
			}
			@Override
			public Adapter caseArrayPadding(ArrayPadding object) {
				return createArrayPaddingAdapter();
			}
			@Override
			public Adapter caseObjectLiteral(ObjectLiteral object) {
				return createObjectLiteralAdapter();
			}
			@Override
			public Adapter casePropertyAssignment(PropertyAssignment object) {
				return createPropertyAssignmentAdapter();
			}
			@Override
			public Adapter casePropertyNameOwner(PropertyNameOwner object) {
				return createPropertyNameOwnerAdapter();
			}
			@Override
			public Adapter caseLiteralOrComputedPropertyName(LiteralOrComputedPropertyName object) {
				return createLiteralOrComputedPropertyNameAdapter();
			}
			@Override
			public Adapter caseAnnotablePropertyAssignment(AnnotablePropertyAssignment object) {
				return createAnnotablePropertyAssignmentAdapter();
			}
			@Override
			public Adapter casePropertyAssignmentAnnotationList(PropertyAssignmentAnnotationList object) {
				return createPropertyAssignmentAnnotationListAdapter();
			}
			@Override
			public Adapter casePropertyNameValuePair(PropertyNameValuePair object) {
				return createPropertyNameValuePairAdapter();
			}
			@Override
			public Adapter casePropertyNameValuePairSingleName(PropertyNameValuePairSingleName object) {
				return createPropertyNameValuePairSingleNameAdapter();
			}
			@Override
			public Adapter casePropertyMethodDeclaration(PropertyMethodDeclaration object) {
				return createPropertyMethodDeclarationAdapter();
			}
			@Override
			public Adapter caseGetterDeclaration(GetterDeclaration object) {
				return createGetterDeclarationAdapter();
			}
			@Override
			public Adapter caseSetterDeclaration(SetterDeclaration object) {
				return createSetterDeclarationAdapter();
			}
			@Override
			public Adapter casePropertyGetterDeclaration(PropertyGetterDeclaration object) {
				return createPropertyGetterDeclarationAdapter();
			}
			@Override
			public Adapter casePropertySetterDeclaration(PropertySetterDeclaration object) {
				return createPropertySetterDeclarationAdapter();
			}
			@Override
			public Adapter caseExpression(Expression object) {
				return createExpressionAdapter();
			}
			@Override
			public Adapter caseNewTarget(NewTarget object) {
				return createNewTargetAdapter();
			}
			@Override
			public Adapter caseNewExpression(NewExpression object) {
				return createNewExpressionAdapter();
			}
			@Override
			public Adapter caseParameterizedAccess(ParameterizedAccess object) {
				return createParameterizedAccessAdapter();
			}
			@Override
			public Adapter caseParameterizedCallExpression(ParameterizedCallExpression object) {
				return createParameterizedCallExpressionAdapter();
			}
			@Override
			public Adapter caseArgument(Argument object) {
				return createArgumentAdapter();
			}
			@Override
			public Adapter caseIndexedAccessExpression(IndexedAccessExpression object) {
				return createIndexedAccessExpressionAdapter();
			}
			@Override
			public Adapter caseTaggedTemplateString(TaggedTemplateString object) {
				return createTaggedTemplateStringAdapter();
			}
			@Override
			public Adapter caseMemberAccess(MemberAccess object) {
				return createMemberAccessAdapter();
			}
			@Override
			public Adapter caseParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression object) {
				return createParameterizedPropertyAccessExpressionAdapter();
			}
			@Override
			public Adapter caseAwaitExpression(AwaitExpression object) {
				return createAwaitExpressionAdapter();
			}
			@Override
			public Adapter casePromisifyExpression(PromisifyExpression object) {
				return createPromisifyExpressionAdapter();
			}
			@Override
			public Adapter caseYieldExpression(YieldExpression object) {
				return createYieldExpressionAdapter();
			}
			@Override
			public Adapter caseLiteral(Literal object) {
				return createLiteralAdapter();
			}
			@Override
			public Adapter caseNullLiteral(NullLiteral object) {
				return createNullLiteralAdapter();
			}
			@Override
			public Adapter caseBooleanLiteral(BooleanLiteral object) {
				return createBooleanLiteralAdapter();
			}
			@Override
			public Adapter caseStringLiteral(StringLiteral object) {
				return createStringLiteralAdapter();
			}
			@Override
			public Adapter caseTemplateLiteral(TemplateLiteral object) {
				return createTemplateLiteralAdapter();
			}
			@Override
			public Adapter caseTemplateSegment(TemplateSegment object) {
				return createTemplateSegmentAdapter();
			}
			@Override
			public Adapter caseNumericLiteral(NumericLiteral object) {
				return createNumericLiteralAdapter();
			}
			@Override
			public Adapter caseDoubleLiteral(DoubleLiteral object) {
				return createDoubleLiteralAdapter();
			}
			@Override
			public Adapter caseAbstractIntLiteral(AbstractIntLiteral object) {
				return createAbstractIntLiteralAdapter();
			}
			@Override
			public Adapter caseIntLiteral(IntLiteral object) {
				return createIntLiteralAdapter();
			}
			@Override
			public Adapter caseBinaryIntLiteral(BinaryIntLiteral object) {
				return createBinaryIntLiteralAdapter();
			}
			@Override
			public Adapter caseOctalIntLiteral(OctalIntLiteral object) {
				return createOctalIntLiteralAdapter();
			}
			@Override
			public Adapter caseLegacyOctalIntLiteral(LegacyOctalIntLiteral object) {
				return createLegacyOctalIntLiteralAdapter();
			}
			@Override
			public Adapter caseHexIntLiteral(HexIntLiteral object) {
				return createHexIntLiteralAdapter();
			}
			@Override
			public Adapter caseScientificIntLiteral(ScientificIntLiteral object) {
				return createScientificIntLiteralAdapter();
			}
			@Override
			public Adapter caseRegularExpressionLiteral(RegularExpressionLiteral object) {
				return createRegularExpressionLiteralAdapter();
			}
			@Override
			public Adapter casePostfixExpression(PostfixExpression object) {
				return createPostfixExpressionAdapter();
			}
			@Override
			public Adapter caseUnaryExpression(UnaryExpression object) {
				return createUnaryExpressionAdapter();
			}
			@Override
			public Adapter caseCastExpression(CastExpression object) {
				return createCastExpressionAdapter();
			}
			@Override
			public Adapter caseMultiplicativeExpression(MultiplicativeExpression object) {
				return createMultiplicativeExpressionAdapter();
			}
			@Override
			public Adapter caseAdditiveExpression(AdditiveExpression object) {
				return createAdditiveExpressionAdapter();
			}
			@Override
			public Adapter caseShiftExpression(ShiftExpression object) {
				return createShiftExpressionAdapter();
			}
			@Override
			public Adapter caseRelationalExpression(RelationalExpression object) {
				return createRelationalExpressionAdapter();
			}
			@Override
			public Adapter caseEqualityExpression(EqualityExpression object) {
				return createEqualityExpressionAdapter();
			}
			@Override
			public Adapter caseBinaryBitwiseExpression(BinaryBitwiseExpression object) {
				return createBinaryBitwiseExpressionAdapter();
			}
			@Override
			public Adapter caseBinaryLogicalExpression(BinaryLogicalExpression object) {
				return createBinaryLogicalExpressionAdapter();
			}
			@Override
			public Adapter caseConditionalExpression(ConditionalExpression object) {
				return createConditionalExpressionAdapter();
			}
			@Override
			public Adapter caseAssignmentExpression(AssignmentExpression object) {
				return createAssignmentExpressionAdapter();
			}
			@Override
			public Adapter caseCommaExpression(CommaExpression object) {
				return createCommaExpressionAdapter();
			}
			@Override
			public Adapter caseTypeDefiningElement(TypeDefiningElement object) {
				return createTypeDefiningElementAdapter();
			}
			@Override
			public Adapter caseGenericDeclaration(GenericDeclaration object) {
				return createGenericDeclarationAdapter();
			}
			@Override
			public Adapter caseN4TypeDefinition(N4TypeDefinition object) {
				return createN4TypeDefinitionAdapter();
			}
			@Override
			public Adapter caseN4TypeDeclaration(N4TypeDeclaration object) {
				return createN4TypeDeclarationAdapter();
			}
			@Override
			public Adapter caseN4ClassifierDeclaration(N4ClassifierDeclaration object) {
				return createN4ClassifierDeclarationAdapter();
			}
			@Override
			public Adapter caseN4ClassifierDefinition(N4ClassifierDefinition object) {
				return createN4ClassifierDefinitionAdapter();
			}
			@Override
			public Adapter caseN4ClassDefinition(N4ClassDefinition object) {
				return createN4ClassDefinitionAdapter();
			}
			@Override
			public Adapter caseN4ClassDeclaration(N4ClassDeclaration object) {
				return createN4ClassDeclarationAdapter();
			}
			@Override
			public Adapter caseN4ClassExpression(N4ClassExpression object) {
				return createN4ClassExpressionAdapter();
			}
			@Override
			public Adapter caseN4InterfaceDeclaration(N4InterfaceDeclaration object) {
				return createN4InterfaceDeclarationAdapter();
			}
			@Override
			public Adapter caseN4EnumDeclaration(N4EnumDeclaration object) {
				return createN4EnumDeclarationAdapter();
			}
			@Override
			public Adapter caseN4EnumLiteral(N4EnumLiteral object) {
				return createN4EnumLiteralAdapter();
			}
			@Override
			public Adapter caseModifiableElement(ModifiableElement object) {
				return createModifiableElementAdapter();
			}
			@Override
			public Adapter caseN4MemberDeclaration(N4MemberDeclaration object) {
				return createN4MemberDeclarationAdapter();
			}
			@Override
			public Adapter caseAnnotableN4MemberDeclaration(AnnotableN4MemberDeclaration object) {
				return createAnnotableN4MemberDeclarationAdapter();
			}
			@Override
			public Adapter caseN4MemberAnnotationList(N4MemberAnnotationList object) {
				return createN4MemberAnnotationListAdapter();
			}
			@Override
			public Adapter caseN4FieldDeclaration(N4FieldDeclaration object) {
				return createN4FieldDeclarationAdapter();
			}
			@Override
			public Adapter caseMethodDeclaration(MethodDeclaration object) {
				return createMethodDeclarationAdapter();
			}
			@Override
			public Adapter caseN4MethodDeclaration(N4MethodDeclaration object) {
				return createN4MethodDeclarationAdapter();
			}
			@Override
			public Adapter caseN4FieldAccessor(N4FieldAccessor object) {
				return createN4FieldAccessorAdapter();
			}
			@Override
			public Adapter caseN4GetterDeclaration(N4GetterDeclaration object) {
				return createN4GetterDeclarationAdapter();
			}
			@Override
			public Adapter caseN4SetterDeclaration(N4SetterDeclaration object) {
				return createN4SetterDeclarationAdapter();
			}
			@Override
			public Adapter caseBindingPattern(BindingPattern object) {
				return createBindingPatternAdapter();
			}
			@Override
			public Adapter caseObjectBindingPattern(ObjectBindingPattern object) {
				return createObjectBindingPatternAdapter();
			}
			@Override
			public Adapter caseArrayBindingPattern(ArrayBindingPattern object) {
				return createArrayBindingPatternAdapter();
			}
			@Override
			public Adapter caseBindingProperty(BindingProperty object) {
				return createBindingPropertyAdapter();
			}
			@Override
			public Adapter caseBindingElement(BindingElement object) {
				return createBindingElementAdapter();
			}
			@Override
			public Adapter caseJSXChild(JSXChild object) {
				return createJSXChildAdapter();
			}
			@Override
			public Adapter caseJSXElementName(JSXElementName object) {
				return createJSXElementNameAdapter();
			}
			@Override
			public Adapter caseJSXText(JSXText object) {
				return createJSXTextAdapter();
			}
			@Override
			public Adapter caseJSXExpression(JSXExpression object) {
				return createJSXExpressionAdapter();
			}
			@Override
			public Adapter caseJSXAttribute(JSXAttribute object) {
				return createJSXAttributeAdapter();
			}
			@Override
			public Adapter caseJSXPropertyAttribute(JSXPropertyAttribute object) {
				return createJSXPropertyAttributeAdapter();
			}
			@Override
			public Adapter caseJSXSpreadAttribute(JSXSpreadAttribute object) {
				return createJSXSpreadAttributeAdapter();
			}
			@Override
			public Adapter caseJSXElement(JSXElement object) {
				return createJSXElementAdapter();
			}
			@Override
			public Adapter caseJSXFragment(JSXFragment object) {
				return createJSXFragmentAdapter();
			}
			@Override
			public Adapter caseVersionedElement(VersionedElement object) {
				return createVersionedElementAdapter();
			}
			@Override
			public Adapter caseVersionedIdentifierRef(VersionedIdentifierRef object) {
				return createVersionedIdentifierRefAdapter();
			}
			@Override
			public Adapter caseMigrationContextVariable(MigrationContextVariable object) {
				return createMigrationContextVariableAdapter();
			}
			@Override
			public Adapter caseTypableElement(TypableElement object) {
				return createTypableElementAdapter();
			}
			@Override
			public Adapter caseIdentifiableElement(IdentifiableElement object) {
				return createIdentifiableElementAdapter();
			}
			@Override
			public Adapter caseVersionable(Versionable object) {
				return createVersionableAdapter();
			}
			@Override
			public Adapter caseVersionedReference(VersionedReference object) {
				return createVersionedReferenceAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ControlFlowElement <em>Control Flow Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ControlFlowElement
	 * @generated
	 */
	public Adapter createControlFlowElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.Script <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.Script
	 * @generated
	 */
	public Adapter createScriptAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ScriptElement <em>Script Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ScriptElement
	 * @generated
	 */
	public Adapter createScriptElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ExportDeclaration <em>Export Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ExportDeclaration
	 * @generated
	 */
	public Adapter createExportDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ExportSpecifier <em>Export Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ExportSpecifier
	 * @generated
	 */
	public Adapter createExportSpecifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ExportableElement <em>Exportable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ExportableElement
	 * @generated
	 */
	public Adapter createExportableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ImportDeclaration <em>Import Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ImportDeclaration
	 * @generated
	 */
	public Adapter createImportDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ImportSpecifier <em>Import Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ImportSpecifier
	 * @generated
	 */
	public Adapter createImportSpecifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.NamedImportSpecifier <em>Named Import Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.NamedImportSpecifier
	 * @generated
	 */
	public Adapter createNamedImportSpecifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.DefaultImportSpecifier <em>Default Import Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.DefaultImportSpecifier
	 * @generated
	 */
	public Adapter createDefaultImportSpecifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.NamespaceImportSpecifier <em>Namespace Import Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.NamespaceImportSpecifier
	 * @generated
	 */
	public Adapter createNamespaceImportSpecifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.TypeProvidingElement <em>Type Providing Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.TypeProvidingElement
	 * @generated
	 */
	public Adapter createTypeProvidingElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.TypedElement
	 * @generated
	 */
	public Adapter createTypedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.VariableEnvironmentElement <em>Variable Environment Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.VariableEnvironmentElement
	 * @generated
	 */
	public Adapter createVariableEnvironmentElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ThisTarget <em>This Target</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ThisTarget
	 * @generated
	 */
	public Adapter createThisTargetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ThisArgProvider <em>This Arg Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ThisArgProvider
	 * @generated
	 */
	public Adapter createThisArgProviderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.Variable
	 * @generated
	 */
	public Adapter createVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AnnotableElement <em>Annotable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AnnotableElement
	 * @generated
	 */
	public Adapter createAnnotableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AnnotableScriptElement <em>Annotable Script Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AnnotableScriptElement
	 * @generated
	 */
	public Adapter createAnnotableScriptElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AnnotableExpression <em>Annotable Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AnnotableExpression
	 * @generated
	 */
	public Adapter createAnnotableExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AbstractAnnotationList <em>Abstract Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AbstractAnnotationList
	 * @generated
	 */
	public Adapter createAbstractAnnotationListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AnnotationList <em>Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AnnotationList
	 * @generated
	 */
	public Adapter createAnnotationListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ExpressionAnnotationList <em>Expression Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ExpressionAnnotationList
	 * @generated
	 */
	public Adapter createExpressionAnnotationListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.Annotation
	 * @generated
	 */
	public Adapter createAnnotationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AnnotationArgument <em>Annotation Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AnnotationArgument
	 * @generated
	 */
	public Adapter createAnnotationArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.LiteralAnnotationArgument <em>Literal Annotation Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.LiteralAnnotationArgument
	 * @generated
	 */
	public Adapter createLiteralAnnotationArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.TypeRefAnnotationArgument <em>Type Ref Annotation Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.TypeRefAnnotationArgument
	 * @generated
	 */
	public Adapter createTypeRefAnnotationArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor <em>Function Or Field Accessor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
	 * @generated
	 */
	public Adapter createFunctionOrFieldAccessorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.FunctionDefinition <em>Function Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.FunctionDefinition
	 * @generated
	 */
	public Adapter createFunctionDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.FieldAccessor <em>Field Accessor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.FieldAccessor
	 * @generated
	 */
	public Adapter createFieldAccessorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.FunctionDeclaration <em>Function Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.FunctionDeclaration
	 * @generated
	 */
	public Adapter createFunctionDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.FunctionExpression <em>Function Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.FunctionExpression
	 * @generated
	 */
	public Adapter createFunctionExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ArrowFunction <em>Arrow Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ArrowFunction
	 * @generated
	 */
	public Adapter createArrowFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.LocalArgumentsVariable <em>Local Arguments Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.LocalArgumentsVariable
	 * @generated
	 */
	public Adapter createLocalArgumentsVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.FormalParameter <em>Formal Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.FormalParameter
	 * @generated
	 */
	public Adapter createFormalParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.Block <em>Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.Block
	 * @generated
	 */
	public Adapter createBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.Statement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.Statement
	 * @generated
	 */
	public Adapter createStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer <em>Variable Declaration Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationContainer
	 * @generated
	 */
	public Adapter createVariableDeclarationContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.VariableStatement <em>Variable Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.VariableStatement
	 * @generated
	 */
	public Adapter createVariableStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ExportedVariableStatement <em>Exported Variable Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ExportedVariableStatement
	 * @generated
	 */
	public Adapter createExportedVariableStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.VariableDeclarationOrBinding <em>Variable Declaration Or Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationOrBinding
	 * @generated
	 */
	public Adapter createVariableDeclarationOrBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.VariableBinding <em>Variable Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.VariableBinding
	 * @generated
	 */
	public Adapter createVariableBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ExportedVariableBinding <em>Exported Variable Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ExportedVariableBinding
	 * @generated
	 */
	public Adapter createExportedVariableBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.VariableDeclaration <em>Variable Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.VariableDeclaration
	 * @generated
	 */
	public Adapter createVariableDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ExportedVariableDeclaration <em>Exported Variable Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ExportedVariableDeclaration
	 * @generated
	 */
	public Adapter createExportedVariableDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.EmptyStatement <em>Empty Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.EmptyStatement
	 * @generated
	 */
	public Adapter createEmptyStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ExpressionStatement <em>Expression Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ExpressionStatement
	 * @generated
	 */
	public Adapter createExpressionStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.IfStatement <em>If Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.IfStatement
	 * @generated
	 */
	public Adapter createIfStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.IterationStatement <em>Iteration Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.IterationStatement
	 * @generated
	 */
	public Adapter createIterationStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.DoStatement <em>Do Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.DoStatement
	 * @generated
	 */
	public Adapter createDoStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.WhileStatement <em>While Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.WhileStatement
	 * @generated
	 */
	public Adapter createWhileStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ForStatement <em>For Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ForStatement
	 * @generated
	 */
	public Adapter createForStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.LabelRef <em>Label Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.LabelRef
	 * @generated
	 */
	public Adapter createLabelRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ContinueStatement <em>Continue Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ContinueStatement
	 * @generated
	 */
	public Adapter createContinueStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.BreakStatement <em>Break Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.BreakStatement
	 * @generated
	 */
	public Adapter createBreakStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ReturnStatement <em>Return Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ReturnStatement
	 * @generated
	 */
	public Adapter createReturnStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.WithStatement <em>With Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.WithStatement
	 * @generated
	 */
	public Adapter createWithStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.SwitchStatement <em>Switch Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.SwitchStatement
	 * @generated
	 */
	public Adapter createSwitchStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AbstractCaseClause <em>Abstract Case Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AbstractCaseClause
	 * @generated
	 */
	public Adapter createAbstractCaseClauseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.CaseClause <em>Case Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.CaseClause
	 * @generated
	 */
	public Adapter createCaseClauseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.DefaultClause <em>Default Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.DefaultClause
	 * @generated
	 */
	public Adapter createDefaultClauseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.LabelledStatement <em>Labelled Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.LabelledStatement
	 * @generated
	 */
	public Adapter createLabelledStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ThrowStatement <em>Throw Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ThrowStatement
	 * @generated
	 */
	public Adapter createThrowStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.TryStatement <em>Try Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.TryStatement
	 * @generated
	 */
	public Adapter createTryStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AbstractCatchBlock <em>Abstract Catch Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AbstractCatchBlock
	 * @generated
	 */
	public Adapter createAbstractCatchBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.CatchBlock <em>Catch Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.CatchBlock
	 * @generated
	 */
	public Adapter createCatchBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.CatchVariable <em>Catch Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.CatchVariable
	 * @generated
	 */
	public Adapter createCatchVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.FinallyBlock <em>Finally Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.FinallyBlock
	 * @generated
	 */
	public Adapter createFinallyBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.DebuggerStatement <em>Debugger Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.DebuggerStatement
	 * @generated
	 */
	public Adapter createDebuggerStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PrimaryExpression <em>Primary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PrimaryExpression
	 * @generated
	 */
	public Adapter createPrimaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ParenExpression <em>Paren Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ParenExpression
	 * @generated
	 */
	public Adapter createParenExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.IdentifierRef <em>Identifier Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.IdentifierRef
	 * @generated
	 */
	public Adapter createIdentifierRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.StrictModeRelevant <em>Strict Mode Relevant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.StrictModeRelevant
	 * @generated
	 */
	public Adapter createStrictModeRelevantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.SuperLiteral <em>Super Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.SuperLiteral
	 * @generated
	 */
	public Adapter createSuperLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ThisLiteral <em>This Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ThisLiteral
	 * @generated
	 */
	public Adapter createThisLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ArrayLiteral <em>Array Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ArrayLiteral
	 * @generated
	 */
	public Adapter createArrayLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ArrayElement <em>Array Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ArrayElement
	 * @generated
	 */
	public Adapter createArrayElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ArrayPadding <em>Array Padding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ArrayPadding
	 * @generated
	 */
	public Adapter createArrayPaddingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ObjectLiteral <em>Object Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ObjectLiteral
	 * @generated
	 */
	public Adapter createObjectLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PropertyAssignment <em>Property Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PropertyAssignment
	 * @generated
	 */
	public Adapter createPropertyAssignmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PropertyNameOwner <em>Property Name Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PropertyNameOwner
	 * @generated
	 */
	public Adapter createPropertyNameOwnerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName <em>Literal Or Computed Property Name</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
	 * @generated
	 */
	public Adapter createLiteralOrComputedPropertyNameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AnnotablePropertyAssignment <em>Annotable Property Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AnnotablePropertyAssignment
	 * @generated
	 */
	public Adapter createAnnotablePropertyAssignmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList <em>Property Assignment Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList
	 * @generated
	 */
	public Adapter createPropertyAssignmentAnnotationListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair <em>Property Name Value Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePair
	 * @generated
	 */
	public Adapter createPropertyNameValuePairAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName <em>Property Name Value Pair Single Name</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName
	 * @generated
	 */
	public Adapter createPropertyNameValuePairSingleNameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PropertyMethodDeclaration <em>Property Method Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PropertyMethodDeclaration
	 * @generated
	 */
	public Adapter createPropertyMethodDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.GetterDeclaration <em>Getter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.GetterDeclaration
	 * @generated
	 */
	public Adapter createGetterDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.SetterDeclaration <em>Setter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.SetterDeclaration
	 * @generated
	 */
	public Adapter createSetterDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PropertyGetterDeclaration <em>Property Getter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PropertyGetterDeclaration
	 * @generated
	 */
	public Adapter createPropertyGetterDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PropertySetterDeclaration <em>Property Setter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PropertySetterDeclaration
	 * @generated
	 */
	public Adapter createPropertySetterDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.NewTarget <em>New Target</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.NewTarget
	 * @generated
	 */
	public Adapter createNewTargetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.NewExpression <em>New Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.NewExpression
	 * @generated
	 */
	public Adapter createNewExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ParameterizedAccess <em>Parameterized Access</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ParameterizedAccess
	 * @generated
	 */
	public Adapter createParameterizedAccessAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ParameterizedCallExpression <em>Parameterized Call Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ParameterizedCallExpression
	 * @generated
	 */
	public Adapter createParameterizedCallExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.Argument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.Argument
	 * @generated
	 */
	public Adapter createArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.IndexedAccessExpression <em>Indexed Access Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.IndexedAccessExpression
	 * @generated
	 */
	public Adapter createIndexedAccessExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.TaggedTemplateString <em>Tagged Template String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.TaggedTemplateString
	 * @generated
	 */
	public Adapter createTaggedTemplateStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.MemberAccess <em>Member Access</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.MemberAccess
	 * @generated
	 */
	public Adapter createMemberAccessAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression <em>Parameterized Property Access Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
	 * @generated
	 */
	public Adapter createParameterizedPropertyAccessExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AwaitExpression <em>Await Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AwaitExpression
	 * @generated
	 */
	public Adapter createAwaitExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PromisifyExpression <em>Promisify Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PromisifyExpression
	 * @generated
	 */
	public Adapter createPromisifyExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.YieldExpression <em>Yield Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.YieldExpression
	 * @generated
	 */
	public Adapter createYieldExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.Literal <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.Literal
	 * @generated
	 */
	public Adapter createLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.NullLiteral <em>Null Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.NullLiteral
	 * @generated
	 */
	public Adapter createNullLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.BooleanLiteral <em>Boolean Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.BooleanLiteral
	 * @generated
	 */
	public Adapter createBooleanLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.StringLiteral <em>String Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.StringLiteral
	 * @generated
	 */
	public Adapter createStringLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.TemplateLiteral <em>Template Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.TemplateLiteral
	 * @generated
	 */
	public Adapter createTemplateLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.TemplateSegment <em>Template Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.TemplateSegment
	 * @generated
	 */
	public Adapter createTemplateSegmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.NumericLiteral <em>Numeric Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.NumericLiteral
	 * @generated
	 */
	public Adapter createNumericLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.DoubleLiteral <em>Double Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.DoubleLiteral
	 * @generated
	 */
	public Adapter createDoubleLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AbstractIntLiteral <em>Abstract Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AbstractIntLiteral
	 * @generated
	 */
	public Adapter createAbstractIntLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.IntLiteral <em>Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.IntLiteral
	 * @generated
	 */
	public Adapter createIntLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.BinaryIntLiteral <em>Binary Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.BinaryIntLiteral
	 * @generated
	 */
	public Adapter createBinaryIntLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.OctalIntLiteral <em>Octal Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.OctalIntLiteral
	 * @generated
	 */
	public Adapter createOctalIntLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.LegacyOctalIntLiteral <em>Legacy Octal Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.LegacyOctalIntLiteral
	 * @generated
	 */
	public Adapter createLegacyOctalIntLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.HexIntLiteral <em>Hex Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.HexIntLiteral
	 * @generated
	 */
	public Adapter createHexIntLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ScientificIntLiteral <em>Scientific Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ScientificIntLiteral
	 * @generated
	 */
	public Adapter createScientificIntLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.RegularExpressionLiteral <em>Regular Expression Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.RegularExpressionLiteral
	 * @generated
	 */
	public Adapter createRegularExpressionLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.PostfixExpression <em>Postfix Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.PostfixExpression
	 * @generated
	 */
	public Adapter createPostfixExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.UnaryExpression
	 * @generated
	 */
	public Adapter createUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.CastExpression <em>Cast Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.CastExpression
	 * @generated
	 */
	public Adapter createCastExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.MultiplicativeExpression <em>Multiplicative Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.MultiplicativeExpression
	 * @generated
	 */
	public Adapter createMultiplicativeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AdditiveExpression <em>Additive Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AdditiveExpression
	 * @generated
	 */
	public Adapter createAdditiveExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ShiftExpression <em>Shift Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ShiftExpression
	 * @generated
	 */
	public Adapter createShiftExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.RelationalExpression <em>Relational Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.RelationalExpression
	 * @generated
	 */
	public Adapter createRelationalExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.EqualityExpression <em>Equality Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.EqualityExpression
	 * @generated
	 */
	public Adapter createEqualityExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.BinaryBitwiseExpression <em>Binary Bitwise Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.BinaryBitwiseExpression
	 * @generated
	 */
	public Adapter createBinaryBitwiseExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression <em>Binary Logical Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.BinaryLogicalExpression
	 * @generated
	 */
	public Adapter createBinaryLogicalExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ConditionalExpression <em>Conditional Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ConditionalExpression
	 * @generated
	 */
	public Adapter createConditionalExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AssignmentExpression <em>Assignment Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AssignmentExpression
	 * @generated
	 */
	public Adapter createAssignmentExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.CommaExpression <em>Comma Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.CommaExpression
	 * @generated
	 */
	public Adapter createCommaExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.TypeDefiningElement <em>Type Defining Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.TypeDefiningElement
	 * @generated
	 */
	public Adapter createTypeDefiningElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.GenericDeclaration <em>Generic Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.GenericDeclaration
	 * @generated
	 */
	public Adapter createGenericDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4TypeDefinition <em>N4 Type Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4TypeDefinition
	 * @generated
	 */
	public Adapter createN4TypeDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4TypeDeclaration <em>N4 Type Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4TypeDeclaration
	 * @generated
	 */
	public Adapter createN4TypeDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4ClassifierDeclaration <em>N4 Classifier Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDeclaration
	 * @generated
	 */
	public Adapter createN4ClassifierDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition <em>N4 Classifier Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition
	 * @generated
	 */
	public Adapter createN4ClassifierDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4ClassDefinition <em>N4 Class Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4ClassDefinition
	 * @generated
	 */
	public Adapter createN4ClassDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4ClassDeclaration <em>N4 Class Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4ClassDeclaration
	 * @generated
	 */
	public Adapter createN4ClassDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4ClassExpression <em>N4 Class Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4ClassExpression
	 * @generated
	 */
	public Adapter createN4ClassExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4InterfaceDeclaration <em>N4 Interface Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4InterfaceDeclaration
	 * @generated
	 */
	public Adapter createN4InterfaceDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4EnumDeclaration <em>N4 Enum Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4EnumDeclaration
	 * @generated
	 */
	public Adapter createN4EnumDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4EnumLiteral <em>N4 Enum Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4EnumLiteral
	 * @generated
	 */
	public Adapter createN4EnumLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ModifiableElement <em>Modifiable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ModifiableElement
	 * @generated
	 */
	public Adapter createModifiableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration <em>N4 Member Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration
	 * @generated
	 */
	public Adapter createN4MemberDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration <em>Annotable N4 Member Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration
	 * @generated
	 */
	public Adapter createAnnotableN4MemberDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4MemberAnnotationList <em>N4 Member Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4MemberAnnotationList
	 * @generated
	 */
	public Adapter createN4MemberAnnotationListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration <em>N4 Field Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration
	 * @generated
	 */
	public Adapter createN4FieldDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.MethodDeclaration <em>Method Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.MethodDeclaration
	 * @generated
	 */
	public Adapter createMethodDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4MethodDeclaration <em>N4 Method Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4MethodDeclaration
	 * @generated
	 */
	public Adapter createN4MethodDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4FieldAccessor <em>N4 Field Accessor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4FieldAccessor
	 * @generated
	 */
	public Adapter createN4FieldAccessorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4GetterDeclaration <em>N4 Getter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4GetterDeclaration
	 * @generated
	 */
	public Adapter createN4GetterDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.N4SetterDeclaration <em>N4 Setter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.N4SetterDeclaration
	 * @generated
	 */
	public Adapter createN4SetterDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.BindingPattern <em>Binding Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.BindingPattern
	 * @generated
	 */
	public Adapter createBindingPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ObjectBindingPattern <em>Object Binding Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ObjectBindingPattern
	 * @generated
	 */
	public Adapter createObjectBindingPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.ArrayBindingPattern <em>Array Binding Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.ArrayBindingPattern
	 * @generated
	 */
	public Adapter createArrayBindingPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.BindingProperty <em>Binding Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.BindingProperty
	 * @generated
	 */
	public Adapter createBindingPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.BindingElement <em>Binding Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.BindingElement
	 * @generated
	 */
	public Adapter createBindingElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.JSXChild <em>JSX Child</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.JSXChild
	 * @generated
	 */
	public Adapter createJSXChildAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.JSXElementName <em>JSX Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.JSXElementName
	 * @generated
	 */
	public Adapter createJSXElementNameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.JSXText <em>JSX Text</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.JSXText
	 * @generated
	 */
	public Adapter createJSXTextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.JSXExpression <em>JSX Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.JSXExpression
	 * @generated
	 */
	public Adapter createJSXExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.JSXAttribute <em>JSX Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.JSXAttribute
	 * @generated
	 */
	public Adapter createJSXAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute <em>JSX Property Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.JSXPropertyAttribute
	 * @generated
	 */
	public Adapter createJSXPropertyAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.JSXSpreadAttribute <em>JSX Spread Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.JSXSpreadAttribute
	 * @generated
	 */
	public Adapter createJSXSpreadAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.JSXElement <em>JSX Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.JSXElement
	 * @generated
	 */
	public Adapter createJSXElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.JSXFragment <em>JSX Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.JSXFragment
	 * @generated
	 */
	public Adapter createJSXFragmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.VersionedElement <em>Versioned Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.VersionedElement
	 * @generated
	 */
	public Adapter createVersionedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.VersionedIdentifierRef <em>Versioned Identifier Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.VersionedIdentifierRef
	 * @generated
	 */
	public Adapter createVersionedIdentifierRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4JS.MigrationContextVariable <em>Migration Context Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4JS.MigrationContextVariable
	 * @generated
	 */
	public Adapter createMigrationContextVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.types.TypableElement <em>Typable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.types.TypableElement
	 * @generated
	 */
	public Adapter createTypableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.types.IdentifiableElement <em>Identifiable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.types.IdentifiableElement
	 * @generated
	 */
	public Adapter createIdentifiableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.Versionable <em>Versionable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.Versionable
	 * @generated
	 */
	public Adapter createVersionableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.VersionedReference <em>Versioned Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedReference
	 * @generated
	 */
	public Adapter createVersionedReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //N4JSAdapterFactory
