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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4JS.N4JSPackage
 * @generated
 */
public interface N4JSFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	N4JSFactory eINSTANCE = org.eclipse.n4js.n4JS.impl.N4JSFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Script</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Script</em>'.
	 * @generated
	 */
	Script createScript();

	/**
	 * Returns a new object of class '<em>Export Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Export Declaration</em>'.
	 * @generated
	 */
	ExportDeclaration createExportDeclaration();

	/**
	 * Returns a new object of class '<em>Export Specifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Export Specifier</em>'.
	 * @generated
	 */
	ExportSpecifier createExportSpecifier();

	/**
	 * Returns a new object of class '<em>Import Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Import Declaration</em>'.
	 * @generated
	 */
	ImportDeclaration createImportDeclaration();

	/**
	 * Returns a new object of class '<em>Named Import Specifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Named Import Specifier</em>'.
	 * @generated
	 */
	NamedImportSpecifier createNamedImportSpecifier();

	/**
	 * Returns a new object of class '<em>Default Import Specifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Default Import Specifier</em>'.
	 * @generated
	 */
	DefaultImportSpecifier createDefaultImportSpecifier();

	/**
	 * Returns a new object of class '<em>Namespace Import Specifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Namespace Import Specifier</em>'.
	 * @generated
	 */
	NamespaceImportSpecifier createNamespaceImportSpecifier();

	/**
	 * Returns a new object of class '<em>Annotation List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Annotation List</em>'.
	 * @generated
	 */
	AnnotationList createAnnotationList();

	/**
	 * Returns a new object of class '<em>Expression Annotation List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expression Annotation List</em>'.
	 * @generated
	 */
	ExpressionAnnotationList createExpressionAnnotationList();

	/**
	 * Returns a new object of class '<em>Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Annotation</em>'.
	 * @generated
	 */
	Annotation createAnnotation();

	/**
	 * Returns a new object of class '<em>Literal Annotation Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal Annotation Argument</em>'.
	 * @generated
	 */
	LiteralAnnotationArgument createLiteralAnnotationArgument();

	/**
	 * Returns a new object of class '<em>Type Ref Annotation Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Ref Annotation Argument</em>'.
	 * @generated
	 */
	TypeRefAnnotationArgument createTypeRefAnnotationArgument();

	/**
	 * Returns a new object of class '<em>Function Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Declaration</em>'.
	 * @generated
	 */
	FunctionDeclaration createFunctionDeclaration();

	/**
	 * Returns a new object of class '<em>Function Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Expression</em>'.
	 * @generated
	 */
	FunctionExpression createFunctionExpression();

	/**
	 * Returns a new object of class '<em>Arrow Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Arrow Function</em>'.
	 * @generated
	 */
	ArrowFunction createArrowFunction();

	/**
	 * Returns a new object of class '<em>Local Arguments Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Arguments Variable</em>'.
	 * @generated
	 */
	LocalArgumentsVariable createLocalArgumentsVariable();

	/**
	 * Returns a new object of class '<em>Formal Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Formal Parameter</em>'.
	 * @generated
	 */
	FormalParameter createFormalParameter();

	/**
	 * Returns a new object of class '<em>Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Block</em>'.
	 * @generated
	 */
	Block createBlock();

	/**
	 * Returns a new object of class '<em>Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Statement</em>'.
	 * @generated
	 */
	Statement createStatement();

	/**
	 * Returns a new object of class '<em>Variable Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Statement</em>'.
	 * @generated
	 */
	VariableStatement createVariableStatement();

	/**
	 * Returns a new object of class '<em>Exported Variable Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exported Variable Statement</em>'.
	 * @generated
	 */
	ExportedVariableStatement createExportedVariableStatement();

	/**
	 * Returns a new object of class '<em>Variable Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Binding</em>'.
	 * @generated
	 */
	VariableBinding createVariableBinding();

	/**
	 * Returns a new object of class '<em>Exported Variable Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exported Variable Binding</em>'.
	 * @generated
	 */
	ExportedVariableBinding createExportedVariableBinding();

	/**
	 * Returns a new object of class '<em>Variable Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Declaration</em>'.
	 * @generated
	 */
	VariableDeclaration createVariableDeclaration();

	/**
	 * Returns a new object of class '<em>Exported Variable Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exported Variable Declaration</em>'.
	 * @generated
	 */
	ExportedVariableDeclaration createExportedVariableDeclaration();

	/**
	 * Returns a new object of class '<em>Empty Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Empty Statement</em>'.
	 * @generated
	 */
	EmptyStatement createEmptyStatement();

	/**
	 * Returns a new object of class '<em>Expression Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expression Statement</em>'.
	 * @generated
	 */
	ExpressionStatement createExpressionStatement();

	/**
	 * Returns a new object of class '<em>If Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>If Statement</em>'.
	 * @generated
	 */
	IfStatement createIfStatement();

	/**
	 * Returns a new object of class '<em>Iteration Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Iteration Statement</em>'.
	 * @generated
	 */
	IterationStatement createIterationStatement();

	/**
	 * Returns a new object of class '<em>Do Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Do Statement</em>'.
	 * @generated
	 */
	DoStatement createDoStatement();

	/**
	 * Returns a new object of class '<em>While Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>While Statement</em>'.
	 * @generated
	 */
	WhileStatement createWhileStatement();

	/**
	 * Returns a new object of class '<em>For Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>For Statement</em>'.
	 * @generated
	 */
	ForStatement createForStatement();

	/**
	 * Returns a new object of class '<em>Continue Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Continue Statement</em>'.
	 * @generated
	 */
	ContinueStatement createContinueStatement();

	/**
	 * Returns a new object of class '<em>Break Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Break Statement</em>'.
	 * @generated
	 */
	BreakStatement createBreakStatement();

	/**
	 * Returns a new object of class '<em>Return Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Return Statement</em>'.
	 * @generated
	 */
	ReturnStatement createReturnStatement();

	/**
	 * Returns a new object of class '<em>With Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>With Statement</em>'.
	 * @generated
	 */
	WithStatement createWithStatement();

	/**
	 * Returns a new object of class '<em>Switch Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Switch Statement</em>'.
	 * @generated
	 */
	SwitchStatement createSwitchStatement();

	/**
	 * Returns a new object of class '<em>Case Clause</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Case Clause</em>'.
	 * @generated
	 */
	CaseClause createCaseClause();

	/**
	 * Returns a new object of class '<em>Default Clause</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Default Clause</em>'.
	 * @generated
	 */
	DefaultClause createDefaultClause();

	/**
	 * Returns a new object of class '<em>Labelled Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Labelled Statement</em>'.
	 * @generated
	 */
	LabelledStatement createLabelledStatement();

	/**
	 * Returns a new object of class '<em>Throw Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Throw Statement</em>'.
	 * @generated
	 */
	ThrowStatement createThrowStatement();

	/**
	 * Returns a new object of class '<em>Try Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Try Statement</em>'.
	 * @generated
	 */
	TryStatement createTryStatement();

	/**
	 * Returns a new object of class '<em>Catch Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Catch Block</em>'.
	 * @generated
	 */
	CatchBlock createCatchBlock();

	/**
	 * Returns a new object of class '<em>Catch Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Catch Variable</em>'.
	 * @generated
	 */
	CatchVariable createCatchVariable();

	/**
	 * Returns a new object of class '<em>Finally Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Finally Block</em>'.
	 * @generated
	 */
	FinallyBlock createFinallyBlock();

	/**
	 * Returns a new object of class '<em>Debugger Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Debugger Statement</em>'.
	 * @generated
	 */
	DebuggerStatement createDebuggerStatement();

	/**
	 * Returns a new object of class '<em>Primary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Primary Expression</em>'.
	 * @generated
	 */
	PrimaryExpression createPrimaryExpression();

	/**
	 * Returns a new object of class '<em>Paren Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Paren Expression</em>'.
	 * @generated
	 */
	ParenExpression createParenExpression();

	/**
	 * Returns a new object of class '<em>Identifier Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Identifier Ref</em>'.
	 * @generated
	 */
	IdentifierRef createIdentifierRef();

	/**
	 * Returns a new object of class '<em>Super Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Super Literal</em>'.
	 * @generated
	 */
	SuperLiteral createSuperLiteral();

	/**
	 * Returns a new object of class '<em>This Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>This Literal</em>'.
	 * @generated
	 */
	ThisLiteral createThisLiteral();

	/**
	 * Returns a new object of class '<em>Array Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Literal</em>'.
	 * @generated
	 */
	ArrayLiteral createArrayLiteral();

	/**
	 * Returns a new object of class '<em>Array Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Element</em>'.
	 * @generated
	 */
	ArrayElement createArrayElement();

	/**
	 * Returns a new object of class '<em>Array Padding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Padding</em>'.
	 * @generated
	 */
	ArrayPadding createArrayPadding();

	/**
	 * Returns a new object of class '<em>Object Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Literal</em>'.
	 * @generated
	 */
	ObjectLiteral createObjectLiteral();

	/**
	 * Returns a new object of class '<em>Literal Or Computed Property Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal Or Computed Property Name</em>'.
	 * @generated
	 */
	LiteralOrComputedPropertyName createLiteralOrComputedPropertyName();

	/**
	 * Returns a new object of class '<em>Property Assignment Annotation List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Assignment Annotation List</em>'.
	 * @generated
	 */
	PropertyAssignmentAnnotationList createPropertyAssignmentAnnotationList();

	/**
	 * Returns a new object of class '<em>Property Name Value Pair</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Name Value Pair</em>'.
	 * @generated
	 */
	PropertyNameValuePair createPropertyNameValuePair();

	/**
	 * Returns a new object of class '<em>Property Name Value Pair Single Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Name Value Pair Single Name</em>'.
	 * @generated
	 */
	PropertyNameValuePairSingleName createPropertyNameValuePairSingleName();

	/**
	 * Returns a new object of class '<em>Property Method Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Method Declaration</em>'.
	 * @generated
	 */
	PropertyMethodDeclaration createPropertyMethodDeclaration();

	/**
	 * Returns a new object of class '<em>Property Getter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Getter Declaration</em>'.
	 * @generated
	 */
	PropertyGetterDeclaration createPropertyGetterDeclaration();

	/**
	 * Returns a new object of class '<em>Property Setter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Setter Declaration</em>'.
	 * @generated
	 */
	PropertySetterDeclaration createPropertySetterDeclaration();

	/**
	 * Returns a new object of class '<em>Property Spread</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Spread</em>'.
	 * @generated
	 */
	PropertySpread createPropertySpread();

	/**
	 * Returns a new object of class '<em>New Target</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>New Target</em>'.
	 * @generated
	 */
	NewTarget createNewTarget();

	/**
	 * Returns a new object of class '<em>New Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>New Expression</em>'.
	 * @generated
	 */
	NewExpression createNewExpression();

	/**
	 * Returns a new object of class '<em>Parameterized Call Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameterized Call Expression</em>'.
	 * @generated
	 */
	ParameterizedCallExpression createParameterizedCallExpression();

	/**
	 * Returns a new object of class '<em>Import Call Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Import Call Expression</em>'.
	 * @generated
	 */
	ImportCallExpression createImportCallExpression();

	/**
	 * Returns a new object of class '<em>Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Argument</em>'.
	 * @generated
	 */
	Argument createArgument();

	/**
	 * Returns a new object of class '<em>Indexed Access Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Indexed Access Expression</em>'.
	 * @generated
	 */
	IndexedAccessExpression createIndexedAccessExpression();

	/**
	 * Returns a new object of class '<em>Tagged Template String</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tagged Template String</em>'.
	 * @generated
	 */
	TaggedTemplateString createTaggedTemplateString();

	/**
	 * Returns a new object of class '<em>Parameterized Property Access Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameterized Property Access Expression</em>'.
	 * @generated
	 */
	ParameterizedPropertyAccessExpression createParameterizedPropertyAccessExpression();

	/**
	 * Returns a new object of class '<em>Await Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Await Expression</em>'.
	 * @generated
	 */
	AwaitExpression createAwaitExpression();

	/**
	 * Returns a new object of class '<em>Promisify Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Promisify Expression</em>'.
	 * @generated
	 */
	PromisifyExpression createPromisifyExpression();

	/**
	 * Returns a new object of class '<em>Yield Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Yield Expression</em>'.
	 * @generated
	 */
	YieldExpression createYieldExpression();

	/**
	 * Returns a new object of class '<em>Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal</em>'.
	 * @generated
	 */
	Literal createLiteral();

	/**
	 * Returns a new object of class '<em>Null Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Null Literal</em>'.
	 * @generated
	 */
	NullLiteral createNullLiteral();

	/**
	 * Returns a new object of class '<em>Boolean Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Literal</em>'.
	 * @generated
	 */
	BooleanLiteral createBooleanLiteral();

	/**
	 * Returns a new object of class '<em>String Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Literal</em>'.
	 * @generated
	 */
	StringLiteral createStringLiteral();

	/**
	 * Returns a new object of class '<em>Template Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template Literal</em>'.
	 * @generated
	 */
	TemplateLiteral createTemplateLiteral();

	/**
	 * Returns a new object of class '<em>Template Segment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template Segment</em>'.
	 * @generated
	 */
	TemplateSegment createTemplateSegment();

	/**
	 * Returns a new object of class '<em>Numeric Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Numeric Literal</em>'.
	 * @generated
	 */
	NumericLiteral createNumericLiteral();

	/**
	 * Returns a new object of class '<em>Double Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Double Literal</em>'.
	 * @generated
	 */
	DoubleLiteral createDoubleLiteral();

	/**
	 * Returns a new object of class '<em>Abstract Int Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abstract Int Literal</em>'.
	 * @generated
	 */
	AbstractIntLiteral createAbstractIntLiteral();

	/**
	 * Returns a new object of class '<em>Int Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int Literal</em>'.
	 * @generated
	 */
	IntLiteral createIntLiteral();

	/**
	 * Returns a new object of class '<em>Binary Int Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Int Literal</em>'.
	 * @generated
	 */
	BinaryIntLiteral createBinaryIntLiteral();

	/**
	 * Returns a new object of class '<em>Octal Int Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Octal Int Literal</em>'.
	 * @generated
	 */
	OctalIntLiteral createOctalIntLiteral();

	/**
	 * Returns a new object of class '<em>Legacy Octal Int Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Legacy Octal Int Literal</em>'.
	 * @generated
	 */
	LegacyOctalIntLiteral createLegacyOctalIntLiteral();

	/**
	 * Returns a new object of class '<em>Hex Int Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hex Int Literal</em>'.
	 * @generated
	 */
	HexIntLiteral createHexIntLiteral();

	/**
	 * Returns a new object of class '<em>Scientific Int Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Scientific Int Literal</em>'.
	 * @generated
	 */
	ScientificIntLiteral createScientificIntLiteral();

	/**
	 * Returns a new object of class '<em>Regular Expression Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Regular Expression Literal</em>'.
	 * @generated
	 */
	RegularExpressionLiteral createRegularExpressionLiteral();

	/**
	 * Returns a new object of class '<em>Postfix Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Postfix Expression</em>'.
	 * @generated
	 */
	PostfixExpression createPostfixExpression();

	/**
	 * Returns a new object of class '<em>Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unary Expression</em>'.
	 * @generated
	 */
	UnaryExpression createUnaryExpression();

	/**
	 * Returns a new object of class '<em>Cast Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cast Expression</em>'.
	 * @generated
	 */
	CastExpression createCastExpression();

	/**
	 * Returns a new object of class '<em>Multiplicative Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Multiplicative Expression</em>'.
	 * @generated
	 */
	MultiplicativeExpression createMultiplicativeExpression();

	/**
	 * Returns a new object of class '<em>Additive Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Additive Expression</em>'.
	 * @generated
	 */
	AdditiveExpression createAdditiveExpression();

	/**
	 * Returns a new object of class '<em>Shift Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shift Expression</em>'.
	 * @generated
	 */
	ShiftExpression createShiftExpression();

	/**
	 * Returns a new object of class '<em>Relational Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Relational Expression</em>'.
	 * @generated
	 */
	RelationalExpression createRelationalExpression();

	/**
	 * Returns a new object of class '<em>Equality Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Equality Expression</em>'.
	 * @generated
	 */
	EqualityExpression createEqualityExpression();

	/**
	 * Returns a new object of class '<em>Binary Bitwise Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Bitwise Expression</em>'.
	 * @generated
	 */
	BinaryBitwiseExpression createBinaryBitwiseExpression();

	/**
	 * Returns a new object of class '<em>Binary Logical Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Logical Expression</em>'.
	 * @generated
	 */
	BinaryLogicalExpression createBinaryLogicalExpression();

	/**
	 * Returns a new object of class '<em>Conditional Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conditional Expression</em>'.
	 * @generated
	 */
	ConditionalExpression createConditionalExpression();

	/**
	 * Returns a new object of class '<em>Assignment Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Assignment Expression</em>'.
	 * @generated
	 */
	AssignmentExpression createAssignmentExpression();

	/**
	 * Returns a new object of class '<em>Comma Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comma Expression</em>'.
	 * @generated
	 */
	CommaExpression createCommaExpression();

	/**
	 * Returns a new object of class '<em>N4 Class Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Class Declaration</em>'.
	 * @generated
	 */
	N4ClassDeclaration createN4ClassDeclaration();

	/**
	 * Returns a new object of class '<em>N4 Class Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Class Expression</em>'.
	 * @generated
	 */
	N4ClassExpression createN4ClassExpression();

	/**
	 * Returns a new object of class '<em>N4 Interface Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Interface Declaration</em>'.
	 * @generated
	 */
	N4InterfaceDeclaration createN4InterfaceDeclaration();

	/**
	 * Returns a new object of class '<em>N4 Enum Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Enum Declaration</em>'.
	 * @generated
	 */
	N4EnumDeclaration createN4EnumDeclaration();

	/**
	 * Returns a new object of class '<em>N4 Enum Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Enum Literal</em>'.
	 * @generated
	 */
	N4EnumLiteral createN4EnumLiteral();

	/**
	 * Returns a new object of class '<em>N4 Member Annotation List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Member Annotation List</em>'.
	 * @generated
	 */
	N4MemberAnnotationList createN4MemberAnnotationList();

	/**
	 * Returns a new object of class '<em>N4 Field Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Field Declaration</em>'.
	 * @generated
	 */
	N4FieldDeclaration createN4FieldDeclaration();

	/**
	 * Returns a new object of class '<em>N4 Method Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Method Declaration</em>'.
	 * @generated
	 */
	N4MethodDeclaration createN4MethodDeclaration();

	/**
	 * Returns a new object of class '<em>N4 Getter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Getter Declaration</em>'.
	 * @generated
	 */
	N4GetterDeclaration createN4GetterDeclaration();

	/**
	 * Returns a new object of class '<em>N4 Setter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>N4 Setter Declaration</em>'.
	 * @generated
	 */
	N4SetterDeclaration createN4SetterDeclaration();

	/**
	 * Returns a new object of class '<em>Object Binding Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Binding Pattern</em>'.
	 * @generated
	 */
	ObjectBindingPattern createObjectBindingPattern();

	/**
	 * Returns a new object of class '<em>Array Binding Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Binding Pattern</em>'.
	 * @generated
	 */
	ArrayBindingPattern createArrayBindingPattern();

	/**
	 * Returns a new object of class '<em>Binding Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binding Property</em>'.
	 * @generated
	 */
	BindingProperty createBindingProperty();

	/**
	 * Returns a new object of class '<em>Binding Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binding Element</em>'.
	 * @generated
	 */
	BindingElement createBindingElement();

	/**
	 * Returns a new object of class '<em>JSX Element Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Element Name</em>'.
	 * @generated
	 */
	JSXElementName createJSXElementName();

	/**
	 * Returns a new object of class '<em>JSX Text</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Text</em>'.
	 * @generated
	 */
	JSXText createJSXText();

	/**
	 * Returns a new object of class '<em>JSX Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Expression</em>'.
	 * @generated
	 */
	JSXExpression createJSXExpression();

	/**
	 * Returns a new object of class '<em>JSX Property Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Property Attribute</em>'.
	 * @generated
	 */
	JSXPropertyAttribute createJSXPropertyAttribute();

	/**
	 * Returns a new object of class '<em>JSX Spread Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Spread Attribute</em>'.
	 * @generated
	 */
	JSXSpreadAttribute createJSXSpreadAttribute();

	/**
	 * Returns a new object of class '<em>JSX Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Element</em>'.
	 * @generated
	 */
	JSXElement createJSXElement();

	/**
	 * Returns a new object of class '<em>JSX Fragment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Fragment</em>'.
	 * @generated
	 */
	JSXFragment createJSXFragment();

	/**
	 * Returns a new object of class '<em>Versioned Identifier Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Versioned Identifier Ref</em>'.
	 * @generated
	 */
	VersionedIdentifierRef createVersionedIdentifierRef();

	/**
	 * Returns a new object of class '<em>Migration Context Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Migration Context Variable</em>'.
	 * @generated
	 */
	MigrationContextVariable createMigrationContextVariable();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	N4JSPackage getN4JSPackage();

} //N4JSFactory
