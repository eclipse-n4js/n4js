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
package org.eclipse.n4js.n4JS.impl;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.n4js.n4JS.AbstractAnnotationList;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.AbstractCatchBlock;
import org.eclipse.n4js.n4JS.AbstractIntLiteral;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.AdditiveOperator;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.AnnotableExpression;
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration;
import org.eclipse.n4js.n4JS.AnnotablePropertyAssignment;
import org.eclipse.n4js.n4JS.AnnotableScriptElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AnnotationArgument;
import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrayPadding;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AssignmentOperator;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseOperator;
import org.eclipse.n4js.n4JS.BinaryIntLiteral;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalOperator;
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
import org.eclipse.n4js.n4JS.CoalesceExpression;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DebuggerStatement;
import org.eclipse.n4js.n4JS.DefaultClause;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.DoubleLiteral;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportSpecifier;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ExportedVariableBinding;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionAnnotationList;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.HexIntLiteral;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.ImportCallExpression;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.IterationStatement;
import org.eclipse.n4js.n4JS.JSXAbstractElement;
import org.eclipse.n4js.n4JS.JSXAttribute;
import org.eclipse.n4js.n4JS.JSXChild;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXElementName;
import org.eclipse.n4js.n4JS.JSXExpression;
import org.eclipse.n4js.n4JS.JSXFragment;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.JSXSpreadAttribute;
import org.eclipse.n4js.n4JS.JSXText;
import org.eclipse.n4js.n4JS.LabelRef;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.LegacyOctalIntLiteral;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.LiteralAnnotationArgument;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.LocalArgumentsVariable;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.MethodDeclaration;
import org.eclipse.n4js.n4JS.MigrationContextVariable;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.ModuleSpecifierForm;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.MultiplicativeOperator;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldAccessor;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberAnnotationList;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDefinition;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NewTarget;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ObjectBindingPattern;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.OctalIntLiteral;
import org.eclipse.n4js.n4JS.ParameterizedAccess;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PostfixOperator;
import org.eclipse.n4js.n4JS.PrimaryExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.PropertySpread;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.RelationalOperator;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.ScientificIntLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.ShiftOperator;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.StrictModeRelevant;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.n4JS.ThisArgProvider;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.ThisTarget;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.TypeProvidingElement;
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument;
import org.eclipse.n4js.n4JS.TypedElement;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.Variable;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationContainer;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.n4JS.VersionedElement;
import org.eclipse.n4js.n4JS.VersionedIdentifierRef;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.n4js.n4JS.WithStatement;
import org.eclipse.n4js.n4JS.YieldExpression;

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class N4JSPackageImpl extends EPackageImpl implements N4JSPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass controlFlowElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scriptEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scriptElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exportDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exportSpecifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exportableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importSpecifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedImportSpecifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass defaultImportSpecifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namespaceImportSpecifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeProvidingElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableEnvironmentElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass thisTargetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass thisArgProviderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotableScriptElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotableExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractAnnotationListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotationListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionAnnotationListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotationArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalAnnotationArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeRefAnnotationArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionOrFieldAccessorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldAccessorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrowFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localArgumentsVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass formalParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableDeclarationContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exportedVariableStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableDeclarationOrBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exportedVariableBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exportedVariableDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass emptyStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ifStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iterationStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass doStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass whileStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass forStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass labelRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass continueStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass breakStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass returnStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass withStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass switchStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractCaseClauseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass caseClauseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass defaultClauseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass labelledStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass throwStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tryStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractCatchBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass catchBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass catchVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass finallyBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass debuggerStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass primaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parenExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identifierRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass strictModeRelevantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass superLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass thisLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayPaddingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyAssignmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyNameOwnerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalOrComputedPropertyNameEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotablePropertyAssignmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyAssignmentAnnotationListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyNameValuePairEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyNameValuePairSingleNameEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyMethodDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass getterDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass setterDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyGetterDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertySetterDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertySpreadEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass newTargetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass newExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterizedAccessEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionWithTargetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterizedCallExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importCallExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass indexedAccessExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass taggedTemplateStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass memberAccessEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterizedPropertyAccessExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass awaitExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass promisifyExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass yieldExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nullLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateSegmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numericLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass doubleLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractIntLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryIntLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass octalIntLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass legacyOctalIntLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hexIntLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scientificIntLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass regularExpressionLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass postfixExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass castExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiplicativeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass additiveExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass shiftExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relationalExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass equalityExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryBitwiseExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryLogicalExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass coalesceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionalExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass assignmentExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commaExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeDefiningElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4TypeDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4TypeDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4ClassifierDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4ClassifierDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4ClassDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4ClassDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4ClassExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4InterfaceDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4EnumDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4EnumLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4TypeAliasDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modifiableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4MemberDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotableN4MemberDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4MemberAnnotationListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4FieldDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass methodDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4MethodDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4FieldAccessorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4GetterDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass n4SetterDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bindingPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectBindingPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayBindingPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bindingPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bindingElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxChildEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxElementNameEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxTextEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxPropertyAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxSpreadAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxAbstractElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsxFragmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionedIdentifierRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass migrationContextVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum moduleSpecifierFormEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum variableStatementKeywordEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum propertyNameKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum postfixOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum unaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum multiplicativeOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum additiveOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum relationalOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum equalityOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum binaryBitwiseOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum binaryLogicalOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum shiftOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum assignmentOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum n4ModifierEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iteratorOfExpressionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iteratorOfYieldExpressionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iteratorOfStatementEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iteratorOfReturnStatementEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private N4JSPackageImpl() {
		super(eNS_URI, N4JSFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link N4JSPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static N4JSPackage init() {
		if (isInited) return (N4JSPackage)EPackage.Registry.INSTANCE.getEPackage(N4JSPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredN4JSPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		N4JSPackageImpl theN4JSPackage = registeredN4JSPackage instanceof N4JSPackageImpl ? (N4JSPackageImpl)registeredN4JSPackage : new N4JSPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();
		TypeRefsPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theN4JSPackage.createPackageContents();

		// Initialize created meta-data
		theN4JSPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theN4JSPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(N4JSPackage.eNS_URI, theN4JSPackage);
		return theN4JSPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNamedElement() {
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getNamedElement__GetName() {
		return namedElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getControlFlowElement() {
		return controlFlowElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getScript() {
		return scriptEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getScript_Hashbang() {
		return (EAttribute)scriptEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getScript_Annotations() {
		return (EReference)scriptEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getScript_ScriptElements() {
		return (EReference)scriptEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getScript_Module() {
		return (EReference)scriptEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getScript_FlaggedUsageMarkingFinished() {
		return (EAttribute)scriptEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getScriptElement() {
		return scriptElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExportDeclaration() {
		return exportDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportDeclaration_ExportedElement() {
		return (EReference)exportDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportDeclaration_DefaultExportedExpression() {
		return (EReference)exportDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportDeclaration_NamedExports() {
		return (EReference)exportDeclarationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExportDeclaration_WildcardExport() {
		return (EAttribute)exportDeclarationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExportDeclaration_DefaultExport() {
		return (EAttribute)exportDeclarationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportDeclaration_ReexportedFrom() {
		return (EReference)exportDeclarationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExportSpecifier() {
		return exportSpecifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportSpecifier_Element() {
		return (EReference)exportSpecifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExportSpecifier_Alias() {
		return (EAttribute)exportSpecifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExportableElement() {
		return exportableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getExportableElement__IsExported() {
		return exportableElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getExportableElement__IsExportedAsDefault() {
		return exportableElementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getExportableElement__GetExportedName() {
		return exportableElementEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getExportableElement__IsToplevel() {
		return exportableElementEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getImportDeclaration() {
		return importDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getImportDeclaration_ImportSpecifiers() {
		return (EReference)importDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getImportDeclaration_ImportFrom() {
		return (EAttribute)importDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getImportDeclaration_Module() {
		return (EReference)importDeclarationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getImportDeclaration_ModuleSpecifierAsText() {
		return (EAttribute)importDeclarationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getImportDeclaration_ModuleSpecifierForm() {
		return (EAttribute)importDeclarationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getImportDeclaration__IsBare() {
		return importDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getImportDeclaration__IsRetainedAtRuntime() {
		return importDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getImportSpecifier() {
		return importSpecifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getImportSpecifier_FlaggedUsedInCode() {
		return (EAttribute)importSpecifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getImportSpecifier_RetainedAtRuntime() {
		return (EAttribute)importSpecifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNamedImportSpecifier() {
		return namedImportSpecifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNamedImportSpecifier_ImportedElement() {
		return (EReference)namedImportSpecifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNamedImportSpecifier_ImportedElementAsText() {
		return (EAttribute)namedImportSpecifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNamedImportSpecifier_Alias() {
		return (EAttribute)namedImportSpecifierEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getNamedImportSpecifier__IsDefaultImport() {
		return namedImportSpecifierEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDefaultImportSpecifier() {
		return defaultImportSpecifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getDefaultImportSpecifier__GetAlias() {
		return defaultImportSpecifierEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getDefaultImportSpecifier__IsDefaultImport() {
		return defaultImportSpecifierEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNamespaceImportSpecifier() {
		return namespaceImportSpecifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNamespaceImportSpecifier_DeclaredDynamic() {
		return (EAttribute)namespaceImportSpecifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNamespaceImportSpecifier_Alias() {
		return (EAttribute)namespaceImportSpecifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeProvidingElement() {
		return typeProvidingElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTypeProvidingElement__GetDeclaredTypeRef() {
		return typeProvidingElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypedElement() {
		return typedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypedElement_DeclaredTypeRef() {
		return (EReference)typedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariableEnvironmentElement() {
		return variableEnvironmentElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVariableEnvironmentElement__AppliesOnlyToBlockScopedElements() {
		return variableEnvironmentElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getThisTarget() {
		return thisTargetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getThisArgProvider() {
		return thisArgProviderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVariable__IsConst() {
		return variableEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnotableElement() {
		return annotableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnnotableElement__GetAnnotations() {
		return annotableElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnnotableElement__GetAllAnnotations() {
		return annotableElementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnotableScriptElement() {
		return annotableScriptElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnnotableScriptElement_AnnotationList() {
		return (EReference)annotableScriptElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnnotableScriptElement__GetAnnotations() {
		return annotableScriptElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnotableExpression() {
		return annotableExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnnotableExpression_AnnotationList() {
		return (EReference)annotableExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnnotableExpression__GetAnnotations() {
		return annotableExpressionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractAnnotationList() {
		return abstractAnnotationListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractAnnotationList_Annotations() {
		return (EReference)abstractAnnotationListEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnotationList() {
		return annotationListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExpressionAnnotationList() {
		return expressionAnnotationListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnotation() {
		return annotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAnnotation_Name() {
		return (EAttribute)annotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnnotation_Args() {
		return (EReference)annotationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnnotation__GetAnnotatedElement() {
		return annotationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnotationArgument() {
		return annotationArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnnotationArgument__Value() {
		return annotationArgumentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnnotationArgument__GetValueAsString() {
		return annotationArgumentEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLiteralAnnotationArgument() {
		return literalAnnotationArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLiteralAnnotationArgument_Literal() {
		return (EReference)literalAnnotationArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getLiteralAnnotationArgument__Value() {
		return literalAnnotationArgumentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeRefAnnotationArgument() {
		return typeRefAnnotationArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeRefAnnotationArgument_TypeRef() {
		return (EReference)typeRefAnnotationArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTypeRefAnnotationArgument__Value() {
		return typeRefAnnotationArgumentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunctionOrFieldAccessor() {
		return functionOrFieldAccessorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionOrFieldAccessor_Body() {
		return (EReference)functionOrFieldAccessorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionOrFieldAccessor__lok() {
		return (EReference)functionOrFieldAccessorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionOrFieldAccessor__GetName() {
		return functionOrFieldAccessorEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionOrFieldAccessor__GetLocalArgumentsVariable() {
		return functionOrFieldAccessorEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionOrFieldAccessor__IsReturnValueOptional() {
		return functionOrFieldAccessorEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionOrFieldAccessor__IsAsync() {
		return functionOrFieldAccessorEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionOrFieldAccessor__GetDefinedFunctionOrAccessor() {
		return functionOrFieldAccessorEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunctionDefinition() {
		return functionDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionDefinition_Fpars() {
		return (EReference)functionDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionDefinition_DeclaredReturnTypeRef() {
		return (EReference)functionDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFunctionDefinition_Generator() {
		return (EAttribute)functionDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFunctionDefinition_DeclaredAsync() {
		return (EAttribute)functionDefinitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionDefinition__IsReturnValueOptional() {
		return functionDefinitionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionDefinition__IsAsync() {
		return functionDefinitionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionDefinition__GetDefinedFunction() {
		return functionDefinitionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFieldAccessor() {
		return fieldAccessorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFieldAccessor_DeclaredOptional() {
		return (EAttribute)fieldAccessorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFieldAccessor__GetDeclaredTypeRef() {
		return fieldAccessorEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFieldAccessor__GetDefinedAccessor() {
		return fieldAccessorEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFieldAccessor__IsOptional() {
		return fieldAccessorEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunctionDeclaration() {
		return functionDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFunctionDeclaration_Name() {
		return (EAttribute)functionDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionDeclaration__migrationContext() {
		return (EReference)functionDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionDeclaration__IsExternal() {
		return functionDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionDeclaration__GetMigrationContextVariable() {
		return functionDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunctionExpression() {
		return functionExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFunctionExpression_Name() {
		return (EAttribute)functionExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getFunctionExpression__IsArrowFunction() {
		return functionExpressionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArrowFunction() {
		return arrowFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getArrowFunction_HasBracesAroundBody() {
		return (EAttribute)arrowFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getArrowFunction__IsArrowFunction() {
		return arrowFunctionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getArrowFunction__IsSingleExprImplicitReturn() {
		return arrowFunctionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getArrowFunction__GetSingleExpression() {
		return arrowFunctionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getArrowFunction__ImplicitReturnExpr() {
		return arrowFunctionEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLocalArgumentsVariable() {
		return localArgumentsVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getLocalArgumentsVariable__GetName() {
		return localArgumentsVariableEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFormalParameter() {
		return formalParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFormalParameter_Annotations() {
		return (EReference)formalParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFormalParameter_Variadic() {
		return (EAttribute)formalParameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFormalParameter_DefinedTypeElement() {
		return (EReference)formalParameterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFormalParameter_HasInitializerAssignment() {
		return (EAttribute)formalParameterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFormalParameter_Initializer() {
		return (EReference)formalParameterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFormalParameter_BindingPattern() {
		return (EReference)formalParameterEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlock() {
		return blockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlock_Statements() {
		return (EReference)blockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__AppliesOnlyToBlockScopedElements() {
		return blockEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__GetAllExpressions() {
		return blockEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__GetAllYieldExpressions() {
		return blockEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__GetAllVoidYieldExpressions() {
		return blockEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__GetAllNonVoidYieldExpressions() {
		return blockEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__HasNonVoidYield() {
		return blockEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__GetAllStatements() {
		return blockEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__GetAllReturnStatements() {
		return blockEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__GetAllNonVoidReturnStatements() {
		return blockEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__GetAllVoidReturnStatements() {
		return blockEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBlock__HasNonVoidReturn() {
		return blockEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStatement() {
		return statementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariableDeclarationContainer() {
		return variableDeclarationContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariableDeclarationContainer_VarDeclsOrBindings() {
		return (EReference)variableDeclarationContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariableDeclarationContainer_VarStmtKeyword() {
		return (EAttribute)variableDeclarationContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVariableDeclarationContainer__GetVarDecl() {
		return variableDeclarationContainerEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVariableDeclarationContainer__IsBlockScoped() {
		return variableDeclarationContainerEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariableStatement() {
		return variableStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExportedVariableStatement() {
		return exportedVariableStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getExportedVariableStatement__IsExternal() {
		return exportedVariableStatementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariableDeclarationOrBinding() {
		return variableDeclarationOrBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVariableDeclarationOrBinding__GetVariableDeclarations() {
		return variableDeclarationOrBindingEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVariableDeclarationOrBinding__GetExpression() {
		return variableDeclarationOrBindingEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariableBinding() {
		return variableBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariableBinding_Pattern() {
		return (EReference)variableBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariableBinding_Expression() {
		return (EReference)variableBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExportedVariableBinding() {
		return exportedVariableBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportedVariableBinding_DefinedVariable() {
		return (EReference)exportedVariableBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariableDeclaration() {
		return variableDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariableDeclaration_Annotations() {
		return (EReference)variableDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariableDeclaration_Expression() {
		return (EReference)variableDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVariableDeclaration__IsConst() {
		return variableDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExportedVariableDeclaration() {
		return exportedVariableDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportedVariableDeclaration_DefinedVariable() {
		return (EReference)exportedVariableDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEmptyStatement() {
		return emptyStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExpressionStatement() {
		return expressionStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExpressionStatement_Expression() {
		return (EReference)expressionStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIfStatement() {
		return ifStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIfStatement_Expression() {
		return (EReference)ifStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIfStatement_IfStmt() {
		return (EReference)ifStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIfStatement_ElseStmt() {
		return (EReference)ifStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIterationStatement() {
		return iterationStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIterationStatement_Statement() {
		return (EReference)iterationStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIterationStatement_Expression() {
		return (EReference)iterationStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDoStatement() {
		return doStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWhileStatement() {
		return whileStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getForStatement() {
		return forStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getForStatement_InitExpr() {
		return (EReference)forStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getForStatement_UpdateExpr() {
		return (EReference)forStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getForStatement_Await() {
		return (EAttribute)forStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getForStatement_ForIn() {
		return (EAttribute)forStatementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getForStatement_ForOf() {
		return (EAttribute)forStatementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getForStatement__IsForPlain() {
		return forStatementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getForStatement__AppliesOnlyToBlockScopedElements() {
		return forStatementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLabelRef() {
		return labelRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLabelRef_Label() {
		return (EReference)labelRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLabelRef_LabelAsText() {
		return (EAttribute)labelRefEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContinueStatement() {
		return continueStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBreakStatement() {
		return breakStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReturnStatement() {
		return returnStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReturnStatement_Expression() {
		return (EReference)returnStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWithStatement() {
		return withStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getWithStatement_Expression() {
		return (EReference)withStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getWithStatement_Statement() {
		return (EReference)withStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSwitchStatement() {
		return switchStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSwitchStatement_Expression() {
		return (EReference)switchStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSwitchStatement_Cases() {
		return (EReference)switchStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSwitchStatement__AppliesOnlyToBlockScopedElements() {
		return switchStatementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSwitchStatement__GetDefaultClause() {
		return switchStatementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSwitchStatement__GetCaseClauses() {
		return switchStatementEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractCaseClause() {
		return abstractCaseClauseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractCaseClause_Statements() {
		return (EReference)abstractCaseClauseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCaseClause() {
		return caseClauseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCaseClause_Expression() {
		return (EReference)caseClauseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDefaultClause() {
		return defaultClauseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLabelledStatement() {
		return labelledStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLabelledStatement_Name() {
		return (EAttribute)labelledStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLabelledStatement_Statement() {
		return (EReference)labelledStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getThrowStatement() {
		return throwStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getThrowStatement_Expression() {
		return (EReference)throwStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTryStatement() {
		return tryStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTryStatement_Block() {
		return (EReference)tryStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTryStatement_Catch() {
		return (EReference)tryStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTryStatement_Finally() {
		return (EReference)tryStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractCatchBlock() {
		return abstractCatchBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractCatchBlock_Block() {
		return (EReference)abstractCatchBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCatchBlock() {
		return catchBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCatchBlock_CatchVariable() {
		return (EReference)catchBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCatchVariable() {
		return catchVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCatchVariable_BindingPattern() {
		return (EReference)catchVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFinallyBlock() {
		return finallyBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDebuggerStatement() {
		return debuggerStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPrimaryExpression() {
		return primaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getParenExpression() {
		return parenExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getParenExpression_Expression() {
		return (EReference)parenExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getParenExpression__IsValidSimpleAssignmentTarget() {
		return parenExpressionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIdentifierRef() {
		return identifierRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIdentifierRef_Id() {
		return (EReference)identifierRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIdentifierRef_IdAsText() {
		return (EAttribute)identifierRefEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIdentifierRef_OriginImport() {
		return (EReference)identifierRefEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getIdentifierRef__GetTargetElement() {
		return identifierRefEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getIdentifierRef__IsValidSimpleAssignmentTarget() {
		return identifierRefEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStrictModeRelevant() {
		return strictModeRelevantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStrictModeRelevant_StrictMode() {
		return (EAttribute)strictModeRelevantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSuperLiteral() {
		return superLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSuperLiteral__IsSuperConstructorAccess() {
		return superLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSuperLiteral__IsSuperMemberAccess() {
		return superLiteralEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getThisLiteral() {
		return thisLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArrayLiteral() {
		return arrayLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArrayLiteral_Elements() {
		return (EReference)arrayLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getArrayLiteral_TrailingComma() {
		return (EAttribute)arrayLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArrayElement() {
		return arrayElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getArrayElement_Spread() {
		return (EAttribute)arrayElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArrayElement_Expression() {
		return (EReference)arrayElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArrayPadding() {
		return arrayPaddingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getObjectLiteral() {
		return objectLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getObjectLiteral_PropertyAssignments() {
		return (EReference)objectLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPropertyAssignment() {
		return propertyAssignmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyAssignment__GetDefinedMember() {
		return propertyAssignmentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyAssignment__IsValidName() {
		return propertyAssignmentEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPropertyNameOwner() {
		return propertyNameOwnerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPropertyNameOwner_DeclaredName() {
		return (EReference)propertyNameOwnerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyNameOwner__GetName() {
		return propertyNameOwnerEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyNameOwner__HasComputedPropertyName() {
		return propertyNameOwnerEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyNameOwner__IsValidName() {
		return propertyNameOwnerEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLiteralOrComputedPropertyName() {
		return literalOrComputedPropertyNameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLiteralOrComputedPropertyName_Kind() {
		return (EAttribute)literalOrComputedPropertyNameEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLiteralOrComputedPropertyName_LiteralName() {
		return (EAttribute)literalOrComputedPropertyNameEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLiteralOrComputedPropertyName_ComputedName() {
		return (EAttribute)literalOrComputedPropertyNameEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLiteralOrComputedPropertyName_Expression() {
		return (EReference)literalOrComputedPropertyNameEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getLiteralOrComputedPropertyName__HasComputedPropertyName() {
		return literalOrComputedPropertyNameEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getLiteralOrComputedPropertyName__GetName() {
		return literalOrComputedPropertyNameEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnotablePropertyAssignment() {
		return annotablePropertyAssignmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnnotablePropertyAssignment_AnnotationList() {
		return (EReference)annotablePropertyAssignmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnnotablePropertyAssignment__GetAnnotations() {
		return annotablePropertyAssignmentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPropertyAssignmentAnnotationList() {
		return propertyAssignmentAnnotationListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyAssignmentAnnotationList__GetDefinedMember() {
		return propertyAssignmentAnnotationListEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPropertyNameValuePair() {
		return propertyNameValuePairEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPropertyNameValuePair_DefinedField() {
		return (EReference)propertyNameValuePairEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPropertyNameValuePair_DeclaredOptional() {
		return (EAttribute)propertyNameValuePairEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPropertyNameValuePair_Expression() {
		return (EReference)propertyNameValuePairEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyNameValuePair__GetDefinedMember() {
		return propertyNameValuePairEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyNameValuePair__IsValidName() {
		return propertyNameValuePairEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPropertyNameValuePairSingleName() {
		return propertyNameValuePairSingleNameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyNameValuePairSingleName__GetIdentifierRef() {
		return propertyNameValuePairSingleNameEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyNameValuePairSingleName__GetName() {
		return propertyNameValuePairSingleNameEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPropertyMethodDeclaration() {
		return propertyMethodDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyMethodDeclaration__GetDefinedMember() {
		return propertyMethodDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGetterDeclaration() {
		return getterDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGetterDeclaration_DefinedGetter() {
		return (EReference)getterDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getGetterDeclaration__GetDefinedAccessor() {
		return getterDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSetterDeclaration() {
		return setterDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSetterDeclaration_DefinedSetter() {
		return (EReference)setterDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSetterDeclaration_Fpar() {
		return (EReference)setterDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSetterDeclaration__GetDefinedAccessor() {
		return setterDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSetterDeclaration__GetDeclaredTypeRef() {
		return setterDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPropertyGetterDeclaration() {
		return propertyGetterDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyGetterDeclaration__GetDefinedGetter() {
		return propertyGetterDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyGetterDeclaration__GetDefinedMember() {
		return propertyGetterDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertyGetterDeclaration__IsValidName() {
		return propertyGetterDeclarationEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPropertySetterDeclaration() {
		return propertySetterDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertySetterDeclaration__GetDefinedSetter() {
		return propertySetterDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertySetterDeclaration__GetDefinedMember() {
		return propertySetterDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertySetterDeclaration__IsValidName() {
		return propertySetterDeclarationEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPropertySpread() {
		return propertySpreadEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPropertySpread_Expression() {
		return (EReference)propertySpreadEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPropertySpread__GetDefinedMember() {
		return propertySpreadEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getExpression__IsValidSimpleAssignmentTarget() {
		return expressionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNewTarget() {
		return newTargetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNewExpression() {
		return newExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNewExpression_Callee() {
		return (EReference)newExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNewExpression_Arguments() {
		return (EReference)newExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNewExpression_WithArgs() {
		return (EAttribute)newExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getParameterizedAccess() {
		return parameterizedAccessEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getParameterizedAccess_TypeArgs() {
		return (EReference)parameterizedAccessEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getParameterizedAccess__IsParameterized() {
		return parameterizedAccessEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExpressionWithTarget() {
		return expressionWithTargetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExpressionWithTarget_Target() {
		return (EReference)expressionWithTargetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExpressionWithTarget_OptionalChaining() {
		return (EAttribute)expressionWithTargetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getExpressionWithTarget__IsOrHasTargetWithOptionalChaining() {
		return expressionWithTargetEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getParameterizedCallExpression() {
		return parameterizedCallExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getParameterizedCallExpression_Arguments() {
		return (EReference)parameterizedCallExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getParameterizedCallExpression__GetReceiver() {
		return parameterizedCallExpressionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getImportCallExpression() {
		return importCallExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getImportCallExpression_Arguments() {
		return (EReference)importCallExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getImportCallExpression__GetArgument() {
		return importCallExpressionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArgument() {
		return argumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getArgument_Spread() {
		return (EAttribute)argumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArgument_Expression() {
		return (EReference)argumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIndexedAccessExpression() {
		return indexedAccessExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIndexedAccessExpression_Index() {
		return (EReference)indexedAccessExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getIndexedAccessExpression__IsValidSimpleAssignmentTarget() {
		return indexedAccessExpressionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTaggedTemplateString() {
		return taggedTemplateStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTaggedTemplateString_Template() {
		return (EReference)taggedTemplateStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMemberAccess() {
		return memberAccessEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMemberAccess_ComposedMemberCache() {
		return (EReference)memberAccessEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getParameterizedPropertyAccessExpression() {
		return parameterizedPropertyAccessExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getParameterizedPropertyAccessExpression_Property() {
		return (EReference)parameterizedPropertyAccessExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getParameterizedPropertyAccessExpression_PropertyAsText() {
		return (EAttribute)parameterizedPropertyAccessExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getParameterizedPropertyAccessExpression__IsValidSimpleAssignmentTarget() {
		return parameterizedPropertyAccessExpressionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAwaitExpression() {
		return awaitExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAwaitExpression_Expression() {
		return (EReference)awaitExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPromisifyExpression() {
		return promisifyExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPromisifyExpression_Expression() {
		return (EReference)promisifyExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getYieldExpression() {
		return yieldExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getYieldExpression_Expression() {
		return (EReference)yieldExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getYieldExpression_Many() {
		return (EAttribute)yieldExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLiteral() {
		return literalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getLiteral__GetValueAsString() {
		return literalEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNullLiteral() {
		return nullLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getNullLiteral__GetValueAsString() {
		return nullLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBooleanLiteral() {
		return booleanLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBooleanLiteral_True() {
		return (EAttribute)booleanLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBooleanLiteral__GetValueAsString() {
		return booleanLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringLiteral() {
		return stringLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringLiteral_Value() {
		return (EAttribute)stringLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringLiteral_RawValue() {
		return (EAttribute)stringLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getStringLiteral__GetValueAsString() {
		return stringLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTemplateLiteral() {
		return templateLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTemplateLiteral_Segments() {
		return (EReference)templateLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTemplateLiteral__GetValueAsString() {
		return templateLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTemplateSegment() {
		return templateSegmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTemplateSegment_Value() {
		return (EAttribute)templateSegmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTemplateSegment_RawValue() {
		return (EAttribute)templateSegmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getTemplateSegment__GetValueAsString() {
		return templateSegmentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNumericLiteral() {
		return numericLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNumericLiteral_Value() {
		return (EAttribute)numericLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getNumericLiteral__GetValueAsString() {
		return numericLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDoubleLiteral() {
		return doubleLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getDoubleLiteral__ToDouble() {
		return doubleLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getDoubleLiteral__GetValueAsString() {
		return doubleLiteralEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractIntLiteral() {
		return abstractIntLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAbstractIntLiteral__ToInt() {
		return abstractIntLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAbstractIntLiteral__ToLong() {
		return abstractIntLiteralEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAbstractIntLiteral__ToBigInteger() {
		return abstractIntLiteralEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIntLiteral() {
		return intLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBinaryIntLiteral() {
		return binaryIntLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOctalIntLiteral() {
		return octalIntLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLegacyOctalIntLiteral() {
		return legacyOctalIntLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHexIntLiteral() {
		return hexIntLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getScientificIntLiteral() {
		return scientificIntLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRegularExpressionLiteral() {
		return regularExpressionLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRegularExpressionLiteral_Value() {
		return (EAttribute)regularExpressionLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getRegularExpressionLiteral__GetValueAsString() {
		return regularExpressionLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPostfixExpression() {
		return postfixExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPostfixExpression_Expression() {
		return (EReference)postfixExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPostfixExpression_Op() {
		return (EAttribute)postfixExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUnaryExpression() {
		return unaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUnaryExpression_Op() {
		return (EAttribute)unaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUnaryExpression_Expression() {
		return (EReference)unaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCastExpression() {
		return castExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCastExpression_Expression() {
		return (EReference)castExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCastExpression_TargetTypeRef() {
		return (EReference)castExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMultiplicativeExpression() {
		return multiplicativeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMultiplicativeExpression_Lhs() {
		return (EReference)multiplicativeExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMultiplicativeExpression_Op() {
		return (EAttribute)multiplicativeExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMultiplicativeExpression_Rhs() {
		return (EReference)multiplicativeExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAdditiveExpression() {
		return additiveExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAdditiveExpression_Lhs() {
		return (EReference)additiveExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAdditiveExpression_Op() {
		return (EAttribute)additiveExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAdditiveExpression_Rhs() {
		return (EReference)additiveExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getShiftExpression() {
		return shiftExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getShiftExpression_Lhs() {
		return (EReference)shiftExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getShiftExpression_Op() {
		return (EAttribute)shiftExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getShiftExpression_Rhs() {
		return (EReference)shiftExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRelationalExpression() {
		return relationalExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRelationalExpression_Lhs() {
		return (EReference)relationalExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelationalExpression_Op() {
		return (EAttribute)relationalExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRelationalExpression_Rhs() {
		return (EReference)relationalExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEqualityExpression() {
		return equalityExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEqualityExpression_Lhs() {
		return (EReference)equalityExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEqualityExpression_Op() {
		return (EAttribute)equalityExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEqualityExpression_Rhs() {
		return (EReference)equalityExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBinaryBitwiseExpression() {
		return binaryBitwiseExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBinaryBitwiseExpression_Lhs() {
		return (EReference)binaryBitwiseExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBinaryBitwiseExpression_Op() {
		return (EAttribute)binaryBitwiseExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBinaryBitwiseExpression_Rhs() {
		return (EReference)binaryBitwiseExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBinaryLogicalExpression() {
		return binaryLogicalExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBinaryLogicalExpression_Lhs() {
		return (EReference)binaryLogicalExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBinaryLogicalExpression_Op() {
		return (EAttribute)binaryLogicalExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBinaryLogicalExpression_Rhs() {
		return (EReference)binaryLogicalExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCoalesceExpression() {
		return coalesceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCoalesceExpression_Expression() {
		return (EReference)coalesceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCoalesceExpression_DefaultExpression() {
		return (EReference)coalesceExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConditionalExpression() {
		return conditionalExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConditionalExpression_Expression() {
		return (EReference)conditionalExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConditionalExpression_TrueExpression() {
		return (EReference)conditionalExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConditionalExpression_FalseExpression() {
		return (EReference)conditionalExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAssignmentExpression() {
		return assignmentExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAssignmentExpression_Lhs() {
		return (EReference)assignmentExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAssignmentExpression_Op() {
		return (EAttribute)assignmentExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAssignmentExpression_Rhs() {
		return (EReference)assignmentExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCommaExpression() {
		return commaExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCommaExpression_Exprs() {
		return (EReference)commaExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeDefiningElement() {
		return typeDefiningElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeDefiningElement_DefinedType() {
		return (EReference)typeDefiningElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGenericDeclaration() {
		return genericDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGenericDeclaration_TypeVars() {
		return (EReference)genericDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4TypeDefinition() {
		return n4TypeDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4TypeDefinition__IsExternal() {
		return n4TypeDefinitionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4TypeDeclaration() {
		return n4TypeDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getN4TypeDeclaration_Name() {
		return (EAttribute)n4TypeDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4TypeDeclaration__IsExternal() {
		return n4TypeDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4ClassifierDeclaration() {
		return n4ClassifierDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getN4ClassifierDeclaration_TypingStrategy() {
		return (EAttribute)n4ClassifierDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4ClassifierDefinition() {
		return n4ClassifierDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4ClassifierDefinition_OwnedMembersRaw() {
		return (EReference)n4ClassifierDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassifierDefinition__GetOwnedMembers() {
		return n4ClassifierDefinitionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassifierDefinition__GetOwnedCtor() {
		return n4ClassifierDefinitionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassifierDefinition__GetOwnedCallableCtor() {
		return n4ClassifierDefinitionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassifierDefinition__GetOwnedMethods() {
		return n4ClassifierDefinitionEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassifierDefinition__GetOwnedFields() {
		return n4ClassifierDefinitionEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassifierDefinition__GetOwnedGetters() {
		return n4ClassifierDefinitionEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassifierDefinition__GetOwnedSetters() {
		return n4ClassifierDefinitionEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassifierDefinition__GetSuperClassifierRefs() {
		return n4ClassifierDefinitionEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassifierDefinition__GetImplementedOrExtendedInterfaceRefs() {
		return n4ClassifierDefinitionEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4ClassDefinition() {
		return n4ClassDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4ClassDefinition_SuperClassRef() {
		return (EReference)n4ClassDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4ClassDefinition_SuperClassExpression() {
		return (EReference)n4ClassDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4ClassDefinition_ImplementedInterfaceRefs() {
		return (EReference)n4ClassDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassDefinition__GetDefinedTypeAsClass() {
		return n4ClassDefinitionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassDefinition__GetSuperClassifierRefs() {
		return n4ClassDefinitionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassDefinition__GetImplementedOrExtendedInterfaceRefs() {
		return n4ClassDefinitionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4ClassDeclaration() {
		return n4ClassDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassDeclaration__IsAbstract() {
		return n4ClassDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4ClassDeclaration__GetVersion() {
		return n4ClassDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4ClassExpression() {
		return n4ClassExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getN4ClassExpression_Name() {
		return (EAttribute)n4ClassExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4InterfaceDeclaration() {
		return n4InterfaceDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4InterfaceDeclaration_SuperInterfaceRefs() {
		return (EReference)n4InterfaceDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4InterfaceDeclaration__GetDefinedTypeAsInterface() {
		return n4InterfaceDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4InterfaceDeclaration__GetSuperClassifierRefs() {
		return n4InterfaceDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4InterfaceDeclaration__GetImplementedOrExtendedInterfaceRefs() {
		return n4InterfaceDeclarationEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4InterfaceDeclaration__GetVersion() {
		return n4InterfaceDeclarationEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4EnumDeclaration() {
		return n4EnumDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4EnumDeclaration_Literals() {
		return (EReference)n4EnumDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4EnumDeclaration__GetDefinedTypeAsEnum() {
		return n4EnumDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4EnumDeclaration__GetVersion() {
		return n4EnumDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4EnumLiteral() {
		return n4EnumLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getN4EnumLiteral_Name() {
		return (EAttribute)n4EnumLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4EnumLiteral_ValueExpression() {
		return (EReference)n4EnumLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4EnumLiteral_DefinedLiteral() {
		return (EReference)n4EnumLiteralEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4TypeAliasDeclaration() {
		return n4TypeAliasDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4TypeAliasDeclaration__GetDefinedTypeAsTypeAlias() {
		return n4TypeAliasDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getModifiableElement() {
		return modifiableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModifiableElement_DeclaredModifiers() {
		return (EAttribute)modifiableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4MemberDeclaration() {
		return n4MemberDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4MemberDeclaration_Owner() {
		return (EReference)n4MemberDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberDeclaration__GetDefinedTypeElement() {
		return n4MemberDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberDeclaration__IsDeclaredAbstract() {
		return n4MemberDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberDeclaration__IsAbstract() {
		return n4MemberDeclarationEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberDeclaration__IsDeclaredStatic() {
		return n4MemberDeclarationEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberDeclaration__IsStatic() {
		return n4MemberDeclarationEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberDeclaration__IsDeclaredFinal() {
		return n4MemberDeclarationEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberDeclaration__IsFinal() {
		return n4MemberDeclarationEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberDeclaration__IsConstructor() {
		return n4MemberDeclarationEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberDeclaration__IsCallableConstructor() {
		return n4MemberDeclarationEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnnotableN4MemberDeclaration() {
		return annotableN4MemberDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnnotableN4MemberDeclaration_AnnotationList() {
		return (EReference)annotableN4MemberDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getAnnotableN4MemberDeclaration__GetAnnotations() {
		return annotableN4MemberDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4MemberAnnotationList() {
		return n4MemberAnnotationListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberAnnotationList__GetDefinedTypeElement() {
		return n4MemberAnnotationListEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberAnnotationList__GetDeclaredTypeRef() {
		return n4MemberAnnotationListEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MemberAnnotationList__GetName() {
		return n4MemberAnnotationListEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4FieldDeclaration() {
		return n4FieldDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4FieldDeclaration_DefinedField() {
		return (EReference)n4FieldDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getN4FieldDeclaration_DeclaredOptional() {
		return (EAttribute)n4FieldDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getN4FieldDeclaration_Expression() {
		return (EReference)n4FieldDeclarationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4FieldDeclaration__GetDefinedTypeElement() {
		return n4FieldDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4FieldDeclaration__IsConst() {
		return n4FieldDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4FieldDeclaration__IsStatic() {
		return n4FieldDeclarationEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4FieldDeclaration__IsValid() {
		return n4FieldDeclarationEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4FieldDeclaration__IsValidName() {
		return n4FieldDeclarationEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMethodDeclaration() {
		return methodDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMethodDeclaration__ExistsExplicitSuperCall() {
		return methodDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMethodDeclaration__GetDefinedTypeElement() {
		return methodDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMethodDeclaration__IsStatic() {
		return methodDeclarationEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4MethodDeclaration() {
		return n4MethodDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MethodDeclaration__IsAbstract() {
		return n4MethodDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MethodDeclaration__IsConstructor() {
		return n4MethodDeclarationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MethodDeclaration__IsCallableConstructor() {
		return n4MethodDeclarationEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MethodDeclaration__IsStatic() {
		return n4MethodDeclarationEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4MethodDeclaration__IsValidName() {
		return n4MethodDeclarationEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4FieldAccessor() {
		return n4FieldAccessorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4FieldAccessor__IsAbstract() {
		return n4FieldAccessorEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4FieldAccessor__IsValidName() {
		return n4FieldAccessorEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4GetterDeclaration() {
		return n4GetterDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4GetterDeclaration__GetDefinedTypeElement() {
		return n4GetterDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getN4SetterDeclaration() {
		return n4SetterDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getN4SetterDeclaration__GetDefinedTypeElement() {
		return n4SetterDeclarationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBindingPattern() {
		return bindingPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getObjectBindingPattern() {
		return objectBindingPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getObjectBindingPattern_Properties() {
		return (EReference)objectBindingPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArrayBindingPattern() {
		return arrayBindingPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArrayBindingPattern_Elements() {
		return (EReference)arrayBindingPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBindingProperty() {
		return bindingPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBindingProperty_Value() {
		return (EReference)bindingPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBindingProperty__GetName() {
		return bindingPropertyEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBindingProperty__IsValidName() {
		return bindingPropertyEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBindingElement() {
		return bindingElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBindingElement_Rest() {
		return (EAttribute)bindingElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBindingElement_VarDecl() {
		return (EReference)bindingElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBindingElement_NestedPattern() {
		return (EReference)bindingElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBindingElement_Expression() {
		return (EReference)bindingElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getBindingElement__IsElision() {
		return bindingElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXChild() {
		return jsxChildEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXElementName() {
		return jsxElementNameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSXElementName_Expression() {
		return (EReference)jsxElementNameEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXText() {
		return jsxTextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXExpression() {
		return jsxExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSXExpression_Expression() {
		return (EReference)jsxExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXAttribute() {
		return jsxAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXPropertyAttribute() {
		return jsxPropertyAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSXPropertyAttribute_Property() {
		return (EReference)jsxPropertyAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getJSXPropertyAttribute_PropertyAsText() {
		return (EAttribute)jsxPropertyAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSXPropertyAttribute_JsxAttributeValue() {
		return (EReference)jsxPropertyAttributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXSpreadAttribute() {
		return jsxSpreadAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSXSpreadAttribute_Expression() {
		return (EReference)jsxSpreadAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXAbstractElement() {
		return jsxAbstractElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSXAbstractElement_JsxChildren() {
		return (EReference)jsxAbstractElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXElement() {
		return jsxElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSXElement_JsxElementName() {
		return (EReference)jsxElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSXElement_JsxAttributes() {
		return (EReference)jsxElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSXElement_JsxClosingName() {
		return (EReference)jsxElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSXFragment() {
		return jsxFragmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVersionedElement() {
		return versionedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionedElement_DeclaredVersion() {
		return (EAttribute)versionedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVersionedElement__HasDeclaredVersion() {
		return versionedElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVersionedElement__GetDeclaredVersionOrZero() {
		return versionedElementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVersionedIdentifierRef() {
		return versionedIdentifierRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getVersionedIdentifierRef__GetVersion() {
		return versionedIdentifierRefEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMigrationContextVariable() {
		return migrationContextVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMigrationContextVariable__GetName() {
		return migrationContextVariableEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getModuleSpecifierForm() {
		return moduleSpecifierFormEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getVariableStatementKeyword() {
		return variableStatementKeywordEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getPropertyNameKind() {
		return propertyNameKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getPostfixOperator() {
		return postfixOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getUnaryOperator() {
		return unaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getMultiplicativeOperator() {
		return multiplicativeOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAdditiveOperator() {
		return additiveOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getRelationalOperator() {
		return relationalOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getEqualityOperator() {
		return equalityOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getBinaryBitwiseOperator() {
		return binaryBitwiseOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getBinaryLogicalOperator() {
		return binaryLogicalOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getShiftOperator() {
		return shiftOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAssignmentOperator() {
		return assignmentOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getN4Modifier() {
		return n4ModifierEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIteratorOfExpression() {
		return iteratorOfExpressionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIteratorOfYieldExpression() {
		return iteratorOfYieldExpressionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIteratorOfStatement() {
		return iteratorOfStatementEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIteratorOfReturnStatement() {
		return iteratorOfReturnStatementEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public N4JSFactory getN4JSFactory() {
		return (N4JSFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEOperation(namedElementEClass, NAMED_ELEMENT___GET_NAME);

		controlFlowElementEClass = createEClass(CONTROL_FLOW_ELEMENT);

		scriptEClass = createEClass(SCRIPT);
		createEAttribute(scriptEClass, SCRIPT__HASHBANG);
		createEReference(scriptEClass, SCRIPT__ANNOTATIONS);
		createEReference(scriptEClass, SCRIPT__SCRIPT_ELEMENTS);
		createEReference(scriptEClass, SCRIPT__MODULE);
		createEAttribute(scriptEClass, SCRIPT__FLAGGED_USAGE_MARKING_FINISHED);

		scriptElementEClass = createEClass(SCRIPT_ELEMENT);

		exportDeclarationEClass = createEClass(EXPORT_DECLARATION);
		createEReference(exportDeclarationEClass, EXPORT_DECLARATION__EXPORTED_ELEMENT);
		createEReference(exportDeclarationEClass, EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION);
		createEReference(exportDeclarationEClass, EXPORT_DECLARATION__NAMED_EXPORTS);
		createEAttribute(exportDeclarationEClass, EXPORT_DECLARATION__WILDCARD_EXPORT);
		createEAttribute(exportDeclarationEClass, EXPORT_DECLARATION__DEFAULT_EXPORT);
		createEReference(exportDeclarationEClass, EXPORT_DECLARATION__REEXPORTED_FROM);

		exportSpecifierEClass = createEClass(EXPORT_SPECIFIER);
		createEReference(exportSpecifierEClass, EXPORT_SPECIFIER__ELEMENT);
		createEAttribute(exportSpecifierEClass, EXPORT_SPECIFIER__ALIAS);

		exportableElementEClass = createEClass(EXPORTABLE_ELEMENT);
		createEOperation(exportableElementEClass, EXPORTABLE_ELEMENT___IS_EXPORTED);
		createEOperation(exportableElementEClass, EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT);
		createEOperation(exportableElementEClass, EXPORTABLE_ELEMENT___GET_EXPORTED_NAME);
		createEOperation(exportableElementEClass, EXPORTABLE_ELEMENT___IS_TOPLEVEL);

		importDeclarationEClass = createEClass(IMPORT_DECLARATION);
		createEReference(importDeclarationEClass, IMPORT_DECLARATION__IMPORT_SPECIFIERS);
		createEAttribute(importDeclarationEClass, IMPORT_DECLARATION__IMPORT_FROM);
		createEReference(importDeclarationEClass, IMPORT_DECLARATION__MODULE);
		createEAttribute(importDeclarationEClass, IMPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT);
		createEAttribute(importDeclarationEClass, IMPORT_DECLARATION__MODULE_SPECIFIER_FORM);
		createEOperation(importDeclarationEClass, IMPORT_DECLARATION___IS_BARE);
		createEOperation(importDeclarationEClass, IMPORT_DECLARATION___IS_RETAINED_AT_RUNTIME);

		importSpecifierEClass = createEClass(IMPORT_SPECIFIER);
		createEAttribute(importSpecifierEClass, IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE);
		createEAttribute(importSpecifierEClass, IMPORT_SPECIFIER__RETAINED_AT_RUNTIME);

		namedImportSpecifierEClass = createEClass(NAMED_IMPORT_SPECIFIER);
		createEReference(namedImportSpecifierEClass, NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT);
		createEAttribute(namedImportSpecifierEClass, NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT);
		createEAttribute(namedImportSpecifierEClass, NAMED_IMPORT_SPECIFIER__ALIAS);
		createEOperation(namedImportSpecifierEClass, NAMED_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT);

		defaultImportSpecifierEClass = createEClass(DEFAULT_IMPORT_SPECIFIER);
		createEOperation(defaultImportSpecifierEClass, DEFAULT_IMPORT_SPECIFIER___GET_ALIAS);
		createEOperation(defaultImportSpecifierEClass, DEFAULT_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT);

		namespaceImportSpecifierEClass = createEClass(NAMESPACE_IMPORT_SPECIFIER);
		createEAttribute(namespaceImportSpecifierEClass, NAMESPACE_IMPORT_SPECIFIER__DECLARED_DYNAMIC);
		createEAttribute(namespaceImportSpecifierEClass, NAMESPACE_IMPORT_SPECIFIER__ALIAS);

		typeProvidingElementEClass = createEClass(TYPE_PROVIDING_ELEMENT);
		createEOperation(typeProvidingElementEClass, TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF);

		typedElementEClass = createEClass(TYPED_ELEMENT);
		createEReference(typedElementEClass, TYPED_ELEMENT__DECLARED_TYPE_REF);

		variableEnvironmentElementEClass = createEClass(VARIABLE_ENVIRONMENT_ELEMENT);
		createEOperation(variableEnvironmentElementEClass, VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS);

		thisTargetEClass = createEClass(THIS_TARGET);

		thisArgProviderEClass = createEClass(THIS_ARG_PROVIDER);

		variableEClass = createEClass(VARIABLE);
		createEOperation(variableEClass, VARIABLE___IS_CONST);

		annotableElementEClass = createEClass(ANNOTABLE_ELEMENT);
		createEOperation(annotableElementEClass, ANNOTABLE_ELEMENT___GET_ANNOTATIONS);
		createEOperation(annotableElementEClass, ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS);

		annotableScriptElementEClass = createEClass(ANNOTABLE_SCRIPT_ELEMENT);
		createEReference(annotableScriptElementEClass, ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST);
		createEOperation(annotableScriptElementEClass, ANNOTABLE_SCRIPT_ELEMENT___GET_ANNOTATIONS);

		annotableExpressionEClass = createEClass(ANNOTABLE_EXPRESSION);
		createEReference(annotableExpressionEClass, ANNOTABLE_EXPRESSION__ANNOTATION_LIST);
		createEOperation(annotableExpressionEClass, ANNOTABLE_EXPRESSION___GET_ANNOTATIONS);

		abstractAnnotationListEClass = createEClass(ABSTRACT_ANNOTATION_LIST);
		createEReference(abstractAnnotationListEClass, ABSTRACT_ANNOTATION_LIST__ANNOTATIONS);

		annotationListEClass = createEClass(ANNOTATION_LIST);

		expressionAnnotationListEClass = createEClass(EXPRESSION_ANNOTATION_LIST);

		annotationEClass = createEClass(ANNOTATION);
		createEAttribute(annotationEClass, ANNOTATION__NAME);
		createEReference(annotationEClass, ANNOTATION__ARGS);
		createEOperation(annotationEClass, ANNOTATION___GET_ANNOTATED_ELEMENT);

		annotationArgumentEClass = createEClass(ANNOTATION_ARGUMENT);
		createEOperation(annotationArgumentEClass, ANNOTATION_ARGUMENT___VALUE);
		createEOperation(annotationArgumentEClass, ANNOTATION_ARGUMENT___GET_VALUE_AS_STRING);

		literalAnnotationArgumentEClass = createEClass(LITERAL_ANNOTATION_ARGUMENT);
		createEReference(literalAnnotationArgumentEClass, LITERAL_ANNOTATION_ARGUMENT__LITERAL);
		createEOperation(literalAnnotationArgumentEClass, LITERAL_ANNOTATION_ARGUMENT___VALUE);

		typeRefAnnotationArgumentEClass = createEClass(TYPE_REF_ANNOTATION_ARGUMENT);
		createEReference(typeRefAnnotationArgumentEClass, TYPE_REF_ANNOTATION_ARGUMENT__TYPE_REF);
		createEOperation(typeRefAnnotationArgumentEClass, TYPE_REF_ANNOTATION_ARGUMENT___VALUE);

		functionOrFieldAccessorEClass = createEClass(FUNCTION_OR_FIELD_ACCESSOR);
		createEReference(functionOrFieldAccessorEClass, FUNCTION_OR_FIELD_ACCESSOR__BODY);
		createEReference(functionOrFieldAccessorEClass, FUNCTION_OR_FIELD_ACCESSOR__LOK);
		createEOperation(functionOrFieldAccessorEClass, FUNCTION_OR_FIELD_ACCESSOR___GET_NAME);
		createEOperation(functionOrFieldAccessorEClass, FUNCTION_OR_FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE);
		createEOperation(functionOrFieldAccessorEClass, FUNCTION_OR_FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL);
		createEOperation(functionOrFieldAccessorEClass, FUNCTION_OR_FIELD_ACCESSOR___IS_ASYNC);
		createEOperation(functionOrFieldAccessorEClass, FUNCTION_OR_FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR);

		functionDefinitionEClass = createEClass(FUNCTION_DEFINITION);
		createEReference(functionDefinitionEClass, FUNCTION_DEFINITION__FPARS);
		createEReference(functionDefinitionEClass, FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF);
		createEAttribute(functionDefinitionEClass, FUNCTION_DEFINITION__GENERATOR);
		createEAttribute(functionDefinitionEClass, FUNCTION_DEFINITION__DECLARED_ASYNC);
		createEOperation(functionDefinitionEClass, FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL);
		createEOperation(functionDefinitionEClass, FUNCTION_DEFINITION___IS_ASYNC);
		createEOperation(functionDefinitionEClass, FUNCTION_DEFINITION___GET_DEFINED_FUNCTION);

		fieldAccessorEClass = createEClass(FIELD_ACCESSOR);
		createEAttribute(fieldAccessorEClass, FIELD_ACCESSOR__DECLARED_OPTIONAL);
		createEOperation(fieldAccessorEClass, FIELD_ACCESSOR___GET_DECLARED_TYPE_REF);
		createEOperation(fieldAccessorEClass, FIELD_ACCESSOR___GET_DEFINED_ACCESSOR);
		createEOperation(fieldAccessorEClass, FIELD_ACCESSOR___IS_OPTIONAL);

		functionDeclarationEClass = createEClass(FUNCTION_DECLARATION);
		createEAttribute(functionDeclarationEClass, FUNCTION_DECLARATION__NAME);
		createEReference(functionDeclarationEClass, FUNCTION_DECLARATION__MIGRATION_CONTEXT);
		createEOperation(functionDeclarationEClass, FUNCTION_DECLARATION___IS_EXTERNAL);
		createEOperation(functionDeclarationEClass, FUNCTION_DECLARATION___GET_MIGRATION_CONTEXT_VARIABLE);

		functionExpressionEClass = createEClass(FUNCTION_EXPRESSION);
		createEAttribute(functionExpressionEClass, FUNCTION_EXPRESSION__NAME);
		createEOperation(functionExpressionEClass, FUNCTION_EXPRESSION___IS_ARROW_FUNCTION);

		arrowFunctionEClass = createEClass(ARROW_FUNCTION);
		createEAttribute(arrowFunctionEClass, ARROW_FUNCTION__HAS_BRACES_AROUND_BODY);
		createEOperation(arrowFunctionEClass, ARROW_FUNCTION___IS_ARROW_FUNCTION);
		createEOperation(arrowFunctionEClass, ARROW_FUNCTION___IS_SINGLE_EXPR_IMPLICIT_RETURN);
		createEOperation(arrowFunctionEClass, ARROW_FUNCTION___GET_SINGLE_EXPRESSION);
		createEOperation(arrowFunctionEClass, ARROW_FUNCTION___IMPLICIT_RETURN_EXPR);

		localArgumentsVariableEClass = createEClass(LOCAL_ARGUMENTS_VARIABLE);
		createEOperation(localArgumentsVariableEClass, LOCAL_ARGUMENTS_VARIABLE___GET_NAME);

		formalParameterEClass = createEClass(FORMAL_PARAMETER);
		createEReference(formalParameterEClass, FORMAL_PARAMETER__ANNOTATIONS);
		createEAttribute(formalParameterEClass, FORMAL_PARAMETER__VARIADIC);
		createEReference(formalParameterEClass, FORMAL_PARAMETER__DEFINED_TYPE_ELEMENT);
		createEAttribute(formalParameterEClass, FORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT);
		createEReference(formalParameterEClass, FORMAL_PARAMETER__INITIALIZER);
		createEReference(formalParameterEClass, FORMAL_PARAMETER__BINDING_PATTERN);

		blockEClass = createEClass(BLOCK);
		createEReference(blockEClass, BLOCK__STATEMENTS);
		createEOperation(blockEClass, BLOCK___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS);
		createEOperation(blockEClass, BLOCK___GET_ALL_EXPRESSIONS);
		createEOperation(blockEClass, BLOCK___GET_ALL_YIELD_EXPRESSIONS);
		createEOperation(blockEClass, BLOCK___GET_ALL_VOID_YIELD_EXPRESSIONS);
		createEOperation(blockEClass, BLOCK___GET_ALL_NON_VOID_YIELD_EXPRESSIONS);
		createEOperation(blockEClass, BLOCK___HAS_NON_VOID_YIELD);
		createEOperation(blockEClass, BLOCK___GET_ALL_STATEMENTS);
		createEOperation(blockEClass, BLOCK___GET_ALL_RETURN_STATEMENTS);
		createEOperation(blockEClass, BLOCK___GET_ALL_NON_VOID_RETURN_STATEMENTS);
		createEOperation(blockEClass, BLOCK___GET_ALL_VOID_RETURN_STATEMENTS);
		createEOperation(blockEClass, BLOCK___HAS_NON_VOID_RETURN);

		statementEClass = createEClass(STATEMENT);

		variableDeclarationContainerEClass = createEClass(VARIABLE_DECLARATION_CONTAINER);
		createEReference(variableDeclarationContainerEClass, VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS);
		createEAttribute(variableDeclarationContainerEClass, VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD);
		createEOperation(variableDeclarationContainerEClass, VARIABLE_DECLARATION_CONTAINER___GET_VAR_DECL);
		createEOperation(variableDeclarationContainerEClass, VARIABLE_DECLARATION_CONTAINER___IS_BLOCK_SCOPED);

		variableStatementEClass = createEClass(VARIABLE_STATEMENT);

		exportedVariableStatementEClass = createEClass(EXPORTED_VARIABLE_STATEMENT);
		createEOperation(exportedVariableStatementEClass, EXPORTED_VARIABLE_STATEMENT___IS_EXTERNAL);

		variableDeclarationOrBindingEClass = createEClass(VARIABLE_DECLARATION_OR_BINDING);
		createEOperation(variableDeclarationOrBindingEClass, VARIABLE_DECLARATION_OR_BINDING___GET_VARIABLE_DECLARATIONS);
		createEOperation(variableDeclarationOrBindingEClass, VARIABLE_DECLARATION_OR_BINDING___GET_EXPRESSION);

		variableBindingEClass = createEClass(VARIABLE_BINDING);
		createEReference(variableBindingEClass, VARIABLE_BINDING__PATTERN);
		createEReference(variableBindingEClass, VARIABLE_BINDING__EXPRESSION);

		exportedVariableBindingEClass = createEClass(EXPORTED_VARIABLE_BINDING);
		createEReference(exportedVariableBindingEClass, EXPORTED_VARIABLE_BINDING__DEFINED_VARIABLE);

		variableDeclarationEClass = createEClass(VARIABLE_DECLARATION);
		createEReference(variableDeclarationEClass, VARIABLE_DECLARATION__ANNOTATIONS);
		createEReference(variableDeclarationEClass, VARIABLE_DECLARATION__EXPRESSION);
		createEOperation(variableDeclarationEClass, VARIABLE_DECLARATION___IS_CONST);

		exportedVariableDeclarationEClass = createEClass(EXPORTED_VARIABLE_DECLARATION);
		createEReference(exportedVariableDeclarationEClass, EXPORTED_VARIABLE_DECLARATION__DEFINED_VARIABLE);

		emptyStatementEClass = createEClass(EMPTY_STATEMENT);

		expressionStatementEClass = createEClass(EXPRESSION_STATEMENT);
		createEReference(expressionStatementEClass, EXPRESSION_STATEMENT__EXPRESSION);

		ifStatementEClass = createEClass(IF_STATEMENT);
		createEReference(ifStatementEClass, IF_STATEMENT__EXPRESSION);
		createEReference(ifStatementEClass, IF_STATEMENT__IF_STMT);
		createEReference(ifStatementEClass, IF_STATEMENT__ELSE_STMT);

		iterationStatementEClass = createEClass(ITERATION_STATEMENT);
		createEReference(iterationStatementEClass, ITERATION_STATEMENT__STATEMENT);
		createEReference(iterationStatementEClass, ITERATION_STATEMENT__EXPRESSION);

		doStatementEClass = createEClass(DO_STATEMENT);

		whileStatementEClass = createEClass(WHILE_STATEMENT);

		forStatementEClass = createEClass(FOR_STATEMENT);
		createEReference(forStatementEClass, FOR_STATEMENT__INIT_EXPR);
		createEReference(forStatementEClass, FOR_STATEMENT__UPDATE_EXPR);
		createEAttribute(forStatementEClass, FOR_STATEMENT__AWAIT);
		createEAttribute(forStatementEClass, FOR_STATEMENT__FOR_IN);
		createEAttribute(forStatementEClass, FOR_STATEMENT__FOR_OF);
		createEOperation(forStatementEClass, FOR_STATEMENT___IS_FOR_PLAIN);
		createEOperation(forStatementEClass, FOR_STATEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS);

		labelRefEClass = createEClass(LABEL_REF);
		createEReference(labelRefEClass, LABEL_REF__LABEL);
		createEAttribute(labelRefEClass, LABEL_REF__LABEL_AS_TEXT);

		continueStatementEClass = createEClass(CONTINUE_STATEMENT);

		breakStatementEClass = createEClass(BREAK_STATEMENT);

		returnStatementEClass = createEClass(RETURN_STATEMENT);
		createEReference(returnStatementEClass, RETURN_STATEMENT__EXPRESSION);

		withStatementEClass = createEClass(WITH_STATEMENT);
		createEReference(withStatementEClass, WITH_STATEMENT__EXPRESSION);
		createEReference(withStatementEClass, WITH_STATEMENT__STATEMENT);

		switchStatementEClass = createEClass(SWITCH_STATEMENT);
		createEReference(switchStatementEClass, SWITCH_STATEMENT__EXPRESSION);
		createEReference(switchStatementEClass, SWITCH_STATEMENT__CASES);
		createEOperation(switchStatementEClass, SWITCH_STATEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS);
		createEOperation(switchStatementEClass, SWITCH_STATEMENT___GET_DEFAULT_CLAUSE);
		createEOperation(switchStatementEClass, SWITCH_STATEMENT___GET_CASE_CLAUSES);

		abstractCaseClauseEClass = createEClass(ABSTRACT_CASE_CLAUSE);
		createEReference(abstractCaseClauseEClass, ABSTRACT_CASE_CLAUSE__STATEMENTS);

		caseClauseEClass = createEClass(CASE_CLAUSE);
		createEReference(caseClauseEClass, CASE_CLAUSE__EXPRESSION);

		defaultClauseEClass = createEClass(DEFAULT_CLAUSE);

		labelledStatementEClass = createEClass(LABELLED_STATEMENT);
		createEAttribute(labelledStatementEClass, LABELLED_STATEMENT__NAME);
		createEReference(labelledStatementEClass, LABELLED_STATEMENT__STATEMENT);

		throwStatementEClass = createEClass(THROW_STATEMENT);
		createEReference(throwStatementEClass, THROW_STATEMENT__EXPRESSION);

		tryStatementEClass = createEClass(TRY_STATEMENT);
		createEReference(tryStatementEClass, TRY_STATEMENT__BLOCK);
		createEReference(tryStatementEClass, TRY_STATEMENT__CATCH);
		createEReference(tryStatementEClass, TRY_STATEMENT__FINALLY);

		abstractCatchBlockEClass = createEClass(ABSTRACT_CATCH_BLOCK);
		createEReference(abstractCatchBlockEClass, ABSTRACT_CATCH_BLOCK__BLOCK);

		catchBlockEClass = createEClass(CATCH_BLOCK);
		createEReference(catchBlockEClass, CATCH_BLOCK__CATCH_VARIABLE);

		catchVariableEClass = createEClass(CATCH_VARIABLE);
		createEReference(catchVariableEClass, CATCH_VARIABLE__BINDING_PATTERN);

		finallyBlockEClass = createEClass(FINALLY_BLOCK);

		debuggerStatementEClass = createEClass(DEBUGGER_STATEMENT);

		primaryExpressionEClass = createEClass(PRIMARY_EXPRESSION);

		parenExpressionEClass = createEClass(PAREN_EXPRESSION);
		createEReference(parenExpressionEClass, PAREN_EXPRESSION__EXPRESSION);
		createEOperation(parenExpressionEClass, PAREN_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET);

		identifierRefEClass = createEClass(IDENTIFIER_REF);
		createEReference(identifierRefEClass, IDENTIFIER_REF__ID);
		createEAttribute(identifierRefEClass, IDENTIFIER_REF__ID_AS_TEXT);
		createEReference(identifierRefEClass, IDENTIFIER_REF__ORIGIN_IMPORT);
		createEOperation(identifierRefEClass, IDENTIFIER_REF___GET_TARGET_ELEMENT);
		createEOperation(identifierRefEClass, IDENTIFIER_REF___IS_VALID_SIMPLE_ASSIGNMENT_TARGET);

		strictModeRelevantEClass = createEClass(STRICT_MODE_RELEVANT);
		createEAttribute(strictModeRelevantEClass, STRICT_MODE_RELEVANT__STRICT_MODE);

		superLiteralEClass = createEClass(SUPER_LITERAL);
		createEOperation(superLiteralEClass, SUPER_LITERAL___IS_SUPER_CONSTRUCTOR_ACCESS);
		createEOperation(superLiteralEClass, SUPER_LITERAL___IS_SUPER_MEMBER_ACCESS);

		thisLiteralEClass = createEClass(THIS_LITERAL);

		arrayLiteralEClass = createEClass(ARRAY_LITERAL);
		createEReference(arrayLiteralEClass, ARRAY_LITERAL__ELEMENTS);
		createEAttribute(arrayLiteralEClass, ARRAY_LITERAL__TRAILING_COMMA);

		arrayElementEClass = createEClass(ARRAY_ELEMENT);
		createEAttribute(arrayElementEClass, ARRAY_ELEMENT__SPREAD);
		createEReference(arrayElementEClass, ARRAY_ELEMENT__EXPRESSION);

		arrayPaddingEClass = createEClass(ARRAY_PADDING);

		objectLiteralEClass = createEClass(OBJECT_LITERAL);
		createEReference(objectLiteralEClass, OBJECT_LITERAL__PROPERTY_ASSIGNMENTS);

		propertyAssignmentEClass = createEClass(PROPERTY_ASSIGNMENT);
		createEOperation(propertyAssignmentEClass, PROPERTY_ASSIGNMENT___GET_DEFINED_MEMBER);
		createEOperation(propertyAssignmentEClass, PROPERTY_ASSIGNMENT___IS_VALID_NAME);

		propertyNameOwnerEClass = createEClass(PROPERTY_NAME_OWNER);
		createEReference(propertyNameOwnerEClass, PROPERTY_NAME_OWNER__DECLARED_NAME);
		createEOperation(propertyNameOwnerEClass, PROPERTY_NAME_OWNER___GET_NAME);
		createEOperation(propertyNameOwnerEClass, PROPERTY_NAME_OWNER___HAS_COMPUTED_PROPERTY_NAME);
		createEOperation(propertyNameOwnerEClass, PROPERTY_NAME_OWNER___IS_VALID_NAME);

		literalOrComputedPropertyNameEClass = createEClass(LITERAL_OR_COMPUTED_PROPERTY_NAME);
		createEAttribute(literalOrComputedPropertyNameEClass, LITERAL_OR_COMPUTED_PROPERTY_NAME__KIND);
		createEAttribute(literalOrComputedPropertyNameEClass, LITERAL_OR_COMPUTED_PROPERTY_NAME__LITERAL_NAME);
		createEAttribute(literalOrComputedPropertyNameEClass, LITERAL_OR_COMPUTED_PROPERTY_NAME__COMPUTED_NAME);
		createEReference(literalOrComputedPropertyNameEClass, LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION);
		createEOperation(literalOrComputedPropertyNameEClass, LITERAL_OR_COMPUTED_PROPERTY_NAME___HAS_COMPUTED_PROPERTY_NAME);
		createEOperation(literalOrComputedPropertyNameEClass, LITERAL_OR_COMPUTED_PROPERTY_NAME___GET_NAME);

		annotablePropertyAssignmentEClass = createEClass(ANNOTABLE_PROPERTY_ASSIGNMENT);
		createEReference(annotablePropertyAssignmentEClass, ANNOTABLE_PROPERTY_ASSIGNMENT__ANNOTATION_LIST);
		createEOperation(annotablePropertyAssignmentEClass, ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ANNOTATIONS);

		propertyAssignmentAnnotationListEClass = createEClass(PROPERTY_ASSIGNMENT_ANNOTATION_LIST);
		createEOperation(propertyAssignmentAnnotationListEClass, PROPERTY_ASSIGNMENT_ANNOTATION_LIST___GET_DEFINED_MEMBER);

		propertyNameValuePairEClass = createEClass(PROPERTY_NAME_VALUE_PAIR);
		createEReference(propertyNameValuePairEClass, PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD);
		createEAttribute(propertyNameValuePairEClass, PROPERTY_NAME_VALUE_PAIR__DECLARED_OPTIONAL);
		createEReference(propertyNameValuePairEClass, PROPERTY_NAME_VALUE_PAIR__EXPRESSION);
		createEOperation(propertyNameValuePairEClass, PROPERTY_NAME_VALUE_PAIR___GET_DEFINED_MEMBER);
		createEOperation(propertyNameValuePairEClass, PROPERTY_NAME_VALUE_PAIR___IS_VALID_NAME);

		propertyNameValuePairSingleNameEClass = createEClass(PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME);
		createEOperation(propertyNameValuePairSingleNameEClass, PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_IDENTIFIER_REF);
		createEOperation(propertyNameValuePairSingleNameEClass, PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_NAME);

		propertyMethodDeclarationEClass = createEClass(PROPERTY_METHOD_DECLARATION);
		createEOperation(propertyMethodDeclarationEClass, PROPERTY_METHOD_DECLARATION___GET_DEFINED_MEMBER);

		getterDeclarationEClass = createEClass(GETTER_DECLARATION);
		createEReference(getterDeclarationEClass, GETTER_DECLARATION__DEFINED_GETTER);
		createEOperation(getterDeclarationEClass, GETTER_DECLARATION___GET_DEFINED_ACCESSOR);

		setterDeclarationEClass = createEClass(SETTER_DECLARATION);
		createEReference(setterDeclarationEClass, SETTER_DECLARATION__DEFINED_SETTER);
		createEReference(setterDeclarationEClass, SETTER_DECLARATION__FPAR);
		createEOperation(setterDeclarationEClass, SETTER_DECLARATION___GET_DEFINED_ACCESSOR);
		createEOperation(setterDeclarationEClass, SETTER_DECLARATION___GET_DECLARED_TYPE_REF);

		propertyGetterDeclarationEClass = createEClass(PROPERTY_GETTER_DECLARATION);
		createEOperation(propertyGetterDeclarationEClass, PROPERTY_GETTER_DECLARATION___GET_DEFINED_GETTER);
		createEOperation(propertyGetterDeclarationEClass, PROPERTY_GETTER_DECLARATION___GET_DEFINED_MEMBER);
		createEOperation(propertyGetterDeclarationEClass, PROPERTY_GETTER_DECLARATION___IS_VALID_NAME);

		propertySetterDeclarationEClass = createEClass(PROPERTY_SETTER_DECLARATION);
		createEOperation(propertySetterDeclarationEClass, PROPERTY_SETTER_DECLARATION___GET_DEFINED_SETTER);
		createEOperation(propertySetterDeclarationEClass, PROPERTY_SETTER_DECLARATION___GET_DEFINED_MEMBER);
		createEOperation(propertySetterDeclarationEClass, PROPERTY_SETTER_DECLARATION___IS_VALID_NAME);

		propertySpreadEClass = createEClass(PROPERTY_SPREAD);
		createEReference(propertySpreadEClass, PROPERTY_SPREAD__EXPRESSION);
		createEOperation(propertySpreadEClass, PROPERTY_SPREAD___GET_DEFINED_MEMBER);

		expressionEClass = createEClass(EXPRESSION);
		createEOperation(expressionEClass, EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET);

		newTargetEClass = createEClass(NEW_TARGET);

		newExpressionEClass = createEClass(NEW_EXPRESSION);
		createEReference(newExpressionEClass, NEW_EXPRESSION__CALLEE);
		createEReference(newExpressionEClass, NEW_EXPRESSION__ARGUMENTS);
		createEAttribute(newExpressionEClass, NEW_EXPRESSION__WITH_ARGS);

		parameterizedAccessEClass = createEClass(PARAMETERIZED_ACCESS);
		createEReference(parameterizedAccessEClass, PARAMETERIZED_ACCESS__TYPE_ARGS);
		createEOperation(parameterizedAccessEClass, PARAMETERIZED_ACCESS___IS_PARAMETERIZED);

		expressionWithTargetEClass = createEClass(EXPRESSION_WITH_TARGET);
		createEReference(expressionWithTargetEClass, EXPRESSION_WITH_TARGET__TARGET);
		createEAttribute(expressionWithTargetEClass, EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING);
		createEOperation(expressionWithTargetEClass, EXPRESSION_WITH_TARGET___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING);

		parameterizedCallExpressionEClass = createEClass(PARAMETERIZED_CALL_EXPRESSION);
		createEReference(parameterizedCallExpressionEClass, PARAMETERIZED_CALL_EXPRESSION__ARGUMENTS);
		createEOperation(parameterizedCallExpressionEClass, PARAMETERIZED_CALL_EXPRESSION___GET_RECEIVER);

		importCallExpressionEClass = createEClass(IMPORT_CALL_EXPRESSION);
		createEReference(importCallExpressionEClass, IMPORT_CALL_EXPRESSION__ARGUMENTS);
		createEOperation(importCallExpressionEClass, IMPORT_CALL_EXPRESSION___GET_ARGUMENT);

		argumentEClass = createEClass(ARGUMENT);
		createEAttribute(argumentEClass, ARGUMENT__SPREAD);
		createEReference(argumentEClass, ARGUMENT__EXPRESSION);

		indexedAccessExpressionEClass = createEClass(INDEXED_ACCESS_EXPRESSION);
		createEReference(indexedAccessExpressionEClass, INDEXED_ACCESS_EXPRESSION__INDEX);
		createEOperation(indexedAccessExpressionEClass, INDEXED_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET);

		taggedTemplateStringEClass = createEClass(TAGGED_TEMPLATE_STRING);
		createEReference(taggedTemplateStringEClass, TAGGED_TEMPLATE_STRING__TEMPLATE);

		memberAccessEClass = createEClass(MEMBER_ACCESS);
		createEReference(memberAccessEClass, MEMBER_ACCESS__COMPOSED_MEMBER_CACHE);

		parameterizedPropertyAccessExpressionEClass = createEClass(PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION);
		createEReference(parameterizedPropertyAccessExpressionEClass, PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY);
		createEAttribute(parameterizedPropertyAccessExpressionEClass, PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY_AS_TEXT);
		createEOperation(parameterizedPropertyAccessExpressionEClass, PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET);

		awaitExpressionEClass = createEClass(AWAIT_EXPRESSION);
		createEReference(awaitExpressionEClass, AWAIT_EXPRESSION__EXPRESSION);

		promisifyExpressionEClass = createEClass(PROMISIFY_EXPRESSION);
		createEReference(promisifyExpressionEClass, PROMISIFY_EXPRESSION__EXPRESSION);

		yieldExpressionEClass = createEClass(YIELD_EXPRESSION);
		createEReference(yieldExpressionEClass, YIELD_EXPRESSION__EXPRESSION);
		createEAttribute(yieldExpressionEClass, YIELD_EXPRESSION__MANY);

		literalEClass = createEClass(LITERAL);
		createEOperation(literalEClass, LITERAL___GET_VALUE_AS_STRING);

		nullLiteralEClass = createEClass(NULL_LITERAL);
		createEOperation(nullLiteralEClass, NULL_LITERAL___GET_VALUE_AS_STRING);

		booleanLiteralEClass = createEClass(BOOLEAN_LITERAL);
		createEAttribute(booleanLiteralEClass, BOOLEAN_LITERAL__TRUE);
		createEOperation(booleanLiteralEClass, BOOLEAN_LITERAL___GET_VALUE_AS_STRING);

		stringLiteralEClass = createEClass(STRING_LITERAL);
		createEAttribute(stringLiteralEClass, STRING_LITERAL__VALUE);
		createEAttribute(stringLiteralEClass, STRING_LITERAL__RAW_VALUE);
		createEOperation(stringLiteralEClass, STRING_LITERAL___GET_VALUE_AS_STRING);

		templateLiteralEClass = createEClass(TEMPLATE_LITERAL);
		createEReference(templateLiteralEClass, TEMPLATE_LITERAL__SEGMENTS);
		createEOperation(templateLiteralEClass, TEMPLATE_LITERAL___GET_VALUE_AS_STRING);

		templateSegmentEClass = createEClass(TEMPLATE_SEGMENT);
		createEAttribute(templateSegmentEClass, TEMPLATE_SEGMENT__VALUE);
		createEAttribute(templateSegmentEClass, TEMPLATE_SEGMENT__RAW_VALUE);
		createEOperation(templateSegmentEClass, TEMPLATE_SEGMENT___GET_VALUE_AS_STRING);

		numericLiteralEClass = createEClass(NUMERIC_LITERAL);
		createEAttribute(numericLiteralEClass, NUMERIC_LITERAL__VALUE);
		createEOperation(numericLiteralEClass, NUMERIC_LITERAL___GET_VALUE_AS_STRING);

		doubleLiteralEClass = createEClass(DOUBLE_LITERAL);
		createEOperation(doubleLiteralEClass, DOUBLE_LITERAL___TO_DOUBLE);
		createEOperation(doubleLiteralEClass, DOUBLE_LITERAL___GET_VALUE_AS_STRING);

		abstractIntLiteralEClass = createEClass(ABSTRACT_INT_LITERAL);
		createEOperation(abstractIntLiteralEClass, ABSTRACT_INT_LITERAL___TO_INT);
		createEOperation(abstractIntLiteralEClass, ABSTRACT_INT_LITERAL___TO_LONG);
		createEOperation(abstractIntLiteralEClass, ABSTRACT_INT_LITERAL___TO_BIG_INTEGER);

		intLiteralEClass = createEClass(INT_LITERAL);

		binaryIntLiteralEClass = createEClass(BINARY_INT_LITERAL);

		octalIntLiteralEClass = createEClass(OCTAL_INT_LITERAL);

		legacyOctalIntLiteralEClass = createEClass(LEGACY_OCTAL_INT_LITERAL);

		hexIntLiteralEClass = createEClass(HEX_INT_LITERAL);

		scientificIntLiteralEClass = createEClass(SCIENTIFIC_INT_LITERAL);

		regularExpressionLiteralEClass = createEClass(REGULAR_EXPRESSION_LITERAL);
		createEAttribute(regularExpressionLiteralEClass, REGULAR_EXPRESSION_LITERAL__VALUE);
		createEOperation(regularExpressionLiteralEClass, REGULAR_EXPRESSION_LITERAL___GET_VALUE_AS_STRING);

		postfixExpressionEClass = createEClass(POSTFIX_EXPRESSION);
		createEReference(postfixExpressionEClass, POSTFIX_EXPRESSION__EXPRESSION);
		createEAttribute(postfixExpressionEClass, POSTFIX_EXPRESSION__OP);

		unaryExpressionEClass = createEClass(UNARY_EXPRESSION);
		createEAttribute(unaryExpressionEClass, UNARY_EXPRESSION__OP);
		createEReference(unaryExpressionEClass, UNARY_EXPRESSION__EXPRESSION);

		castExpressionEClass = createEClass(CAST_EXPRESSION);
		createEReference(castExpressionEClass, CAST_EXPRESSION__EXPRESSION);
		createEReference(castExpressionEClass, CAST_EXPRESSION__TARGET_TYPE_REF);

		multiplicativeExpressionEClass = createEClass(MULTIPLICATIVE_EXPRESSION);
		createEReference(multiplicativeExpressionEClass, MULTIPLICATIVE_EXPRESSION__LHS);
		createEAttribute(multiplicativeExpressionEClass, MULTIPLICATIVE_EXPRESSION__OP);
		createEReference(multiplicativeExpressionEClass, MULTIPLICATIVE_EXPRESSION__RHS);

		additiveExpressionEClass = createEClass(ADDITIVE_EXPRESSION);
		createEReference(additiveExpressionEClass, ADDITIVE_EXPRESSION__LHS);
		createEAttribute(additiveExpressionEClass, ADDITIVE_EXPRESSION__OP);
		createEReference(additiveExpressionEClass, ADDITIVE_EXPRESSION__RHS);

		shiftExpressionEClass = createEClass(SHIFT_EXPRESSION);
		createEReference(shiftExpressionEClass, SHIFT_EXPRESSION__LHS);
		createEAttribute(shiftExpressionEClass, SHIFT_EXPRESSION__OP);
		createEReference(shiftExpressionEClass, SHIFT_EXPRESSION__RHS);

		relationalExpressionEClass = createEClass(RELATIONAL_EXPRESSION);
		createEReference(relationalExpressionEClass, RELATIONAL_EXPRESSION__LHS);
		createEAttribute(relationalExpressionEClass, RELATIONAL_EXPRESSION__OP);
		createEReference(relationalExpressionEClass, RELATIONAL_EXPRESSION__RHS);

		equalityExpressionEClass = createEClass(EQUALITY_EXPRESSION);
		createEReference(equalityExpressionEClass, EQUALITY_EXPRESSION__LHS);
		createEAttribute(equalityExpressionEClass, EQUALITY_EXPRESSION__OP);
		createEReference(equalityExpressionEClass, EQUALITY_EXPRESSION__RHS);

		binaryBitwiseExpressionEClass = createEClass(BINARY_BITWISE_EXPRESSION);
		createEReference(binaryBitwiseExpressionEClass, BINARY_BITWISE_EXPRESSION__LHS);
		createEAttribute(binaryBitwiseExpressionEClass, BINARY_BITWISE_EXPRESSION__OP);
		createEReference(binaryBitwiseExpressionEClass, BINARY_BITWISE_EXPRESSION__RHS);

		binaryLogicalExpressionEClass = createEClass(BINARY_LOGICAL_EXPRESSION);
		createEReference(binaryLogicalExpressionEClass, BINARY_LOGICAL_EXPRESSION__LHS);
		createEAttribute(binaryLogicalExpressionEClass, BINARY_LOGICAL_EXPRESSION__OP);
		createEReference(binaryLogicalExpressionEClass, BINARY_LOGICAL_EXPRESSION__RHS);

		coalesceExpressionEClass = createEClass(COALESCE_EXPRESSION);
		createEReference(coalesceExpressionEClass, COALESCE_EXPRESSION__EXPRESSION);
		createEReference(coalesceExpressionEClass, COALESCE_EXPRESSION__DEFAULT_EXPRESSION);

		conditionalExpressionEClass = createEClass(CONDITIONAL_EXPRESSION);
		createEReference(conditionalExpressionEClass, CONDITIONAL_EXPRESSION__EXPRESSION);
		createEReference(conditionalExpressionEClass, CONDITIONAL_EXPRESSION__TRUE_EXPRESSION);
		createEReference(conditionalExpressionEClass, CONDITIONAL_EXPRESSION__FALSE_EXPRESSION);

		assignmentExpressionEClass = createEClass(ASSIGNMENT_EXPRESSION);
		createEReference(assignmentExpressionEClass, ASSIGNMENT_EXPRESSION__LHS);
		createEAttribute(assignmentExpressionEClass, ASSIGNMENT_EXPRESSION__OP);
		createEReference(assignmentExpressionEClass, ASSIGNMENT_EXPRESSION__RHS);

		commaExpressionEClass = createEClass(COMMA_EXPRESSION);
		createEReference(commaExpressionEClass, COMMA_EXPRESSION__EXPRS);

		typeDefiningElementEClass = createEClass(TYPE_DEFINING_ELEMENT);
		createEReference(typeDefiningElementEClass, TYPE_DEFINING_ELEMENT__DEFINED_TYPE);

		genericDeclarationEClass = createEClass(GENERIC_DECLARATION);
		createEReference(genericDeclarationEClass, GENERIC_DECLARATION__TYPE_VARS);

		n4TypeDefinitionEClass = createEClass(N4_TYPE_DEFINITION);
		createEOperation(n4TypeDefinitionEClass, N4_TYPE_DEFINITION___IS_EXTERNAL);

		n4TypeDeclarationEClass = createEClass(N4_TYPE_DECLARATION);
		createEAttribute(n4TypeDeclarationEClass, N4_TYPE_DECLARATION__NAME);
		createEOperation(n4TypeDeclarationEClass, N4_TYPE_DECLARATION___IS_EXTERNAL);

		n4ClassifierDeclarationEClass = createEClass(N4_CLASSIFIER_DECLARATION);
		createEAttribute(n4ClassifierDeclarationEClass, N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY);

		n4ClassifierDefinitionEClass = createEClass(N4_CLASSIFIER_DEFINITION);
		createEReference(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION__OWNED_MEMBERS_RAW);
		createEOperation(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION___GET_OWNED_MEMBERS);
		createEOperation(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION___GET_OWNED_CTOR);
		createEOperation(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION___GET_OWNED_CALLABLE_CTOR);
		createEOperation(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION___GET_OWNED_METHODS);
		createEOperation(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION___GET_OWNED_FIELDS);
		createEOperation(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION___GET_OWNED_GETTERS);
		createEOperation(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION___GET_OWNED_SETTERS);
		createEOperation(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION___GET_SUPER_CLASSIFIER_REFS);
		createEOperation(n4ClassifierDefinitionEClass, N4_CLASSIFIER_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS);

		n4ClassDefinitionEClass = createEClass(N4_CLASS_DEFINITION);
		createEReference(n4ClassDefinitionEClass, N4_CLASS_DEFINITION__SUPER_CLASS_REF);
		createEReference(n4ClassDefinitionEClass, N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION);
		createEReference(n4ClassDefinitionEClass, N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS);
		createEOperation(n4ClassDefinitionEClass, N4_CLASS_DEFINITION___GET_DEFINED_TYPE_AS_CLASS);
		createEOperation(n4ClassDefinitionEClass, N4_CLASS_DEFINITION___GET_SUPER_CLASSIFIER_REFS);
		createEOperation(n4ClassDefinitionEClass, N4_CLASS_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS);

		n4ClassDeclarationEClass = createEClass(N4_CLASS_DECLARATION);
		createEOperation(n4ClassDeclarationEClass, N4_CLASS_DECLARATION___IS_ABSTRACT);
		createEOperation(n4ClassDeclarationEClass, N4_CLASS_DECLARATION___GET_VERSION);

		n4ClassExpressionEClass = createEClass(N4_CLASS_EXPRESSION);
		createEAttribute(n4ClassExpressionEClass, N4_CLASS_EXPRESSION__NAME);

		n4InterfaceDeclarationEClass = createEClass(N4_INTERFACE_DECLARATION);
		createEReference(n4InterfaceDeclarationEClass, N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS);
		createEOperation(n4InterfaceDeclarationEClass, N4_INTERFACE_DECLARATION___GET_DEFINED_TYPE_AS_INTERFACE);
		createEOperation(n4InterfaceDeclarationEClass, N4_INTERFACE_DECLARATION___GET_SUPER_CLASSIFIER_REFS);
		createEOperation(n4InterfaceDeclarationEClass, N4_INTERFACE_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS);
		createEOperation(n4InterfaceDeclarationEClass, N4_INTERFACE_DECLARATION___GET_VERSION);

		n4EnumDeclarationEClass = createEClass(N4_ENUM_DECLARATION);
		createEReference(n4EnumDeclarationEClass, N4_ENUM_DECLARATION__LITERALS);
		createEOperation(n4EnumDeclarationEClass, N4_ENUM_DECLARATION___GET_DEFINED_TYPE_AS_ENUM);
		createEOperation(n4EnumDeclarationEClass, N4_ENUM_DECLARATION___GET_VERSION);

		n4EnumLiteralEClass = createEClass(N4_ENUM_LITERAL);
		createEAttribute(n4EnumLiteralEClass, N4_ENUM_LITERAL__NAME);
		createEReference(n4EnumLiteralEClass, N4_ENUM_LITERAL__VALUE_EXPRESSION);
		createEReference(n4EnumLiteralEClass, N4_ENUM_LITERAL__DEFINED_LITERAL);

		n4TypeAliasDeclarationEClass = createEClass(N4_TYPE_ALIAS_DECLARATION);
		createEOperation(n4TypeAliasDeclarationEClass, N4_TYPE_ALIAS_DECLARATION___GET_DEFINED_TYPE_AS_TYPE_ALIAS);

		modifiableElementEClass = createEClass(MODIFIABLE_ELEMENT);
		createEAttribute(modifiableElementEClass, MODIFIABLE_ELEMENT__DECLARED_MODIFIERS);

		n4MemberDeclarationEClass = createEClass(N4_MEMBER_DECLARATION);
		createEReference(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION__OWNER);
		createEOperation(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION___GET_DEFINED_TYPE_ELEMENT);
		createEOperation(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION___IS_DECLARED_ABSTRACT);
		createEOperation(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION___IS_ABSTRACT);
		createEOperation(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION___IS_DECLARED_STATIC);
		createEOperation(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION___IS_STATIC);
		createEOperation(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION___IS_DECLARED_FINAL);
		createEOperation(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION___IS_FINAL);
		createEOperation(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION___IS_CONSTRUCTOR);
		createEOperation(n4MemberDeclarationEClass, N4_MEMBER_DECLARATION___IS_CALLABLE_CONSTRUCTOR);

		annotableN4MemberDeclarationEClass = createEClass(ANNOTABLE_N4_MEMBER_DECLARATION);
		createEReference(annotableN4MemberDeclarationEClass, ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST);
		createEOperation(annotableN4MemberDeclarationEClass, ANNOTABLE_N4_MEMBER_DECLARATION___GET_ANNOTATIONS);

		n4MemberAnnotationListEClass = createEClass(N4_MEMBER_ANNOTATION_LIST);
		createEOperation(n4MemberAnnotationListEClass, N4_MEMBER_ANNOTATION_LIST___GET_DEFINED_TYPE_ELEMENT);
		createEOperation(n4MemberAnnotationListEClass, N4_MEMBER_ANNOTATION_LIST___GET_DECLARED_TYPE_REF);
		createEOperation(n4MemberAnnotationListEClass, N4_MEMBER_ANNOTATION_LIST___GET_NAME);

		n4FieldDeclarationEClass = createEClass(N4_FIELD_DECLARATION);
		createEReference(n4FieldDeclarationEClass, N4_FIELD_DECLARATION__DEFINED_FIELD);
		createEAttribute(n4FieldDeclarationEClass, N4_FIELD_DECLARATION__DECLARED_OPTIONAL);
		createEReference(n4FieldDeclarationEClass, N4_FIELD_DECLARATION__EXPRESSION);
		createEOperation(n4FieldDeclarationEClass, N4_FIELD_DECLARATION___GET_DEFINED_TYPE_ELEMENT);
		createEOperation(n4FieldDeclarationEClass, N4_FIELD_DECLARATION___IS_CONST);
		createEOperation(n4FieldDeclarationEClass, N4_FIELD_DECLARATION___IS_STATIC);
		createEOperation(n4FieldDeclarationEClass, N4_FIELD_DECLARATION___IS_VALID);
		createEOperation(n4FieldDeclarationEClass, N4_FIELD_DECLARATION___IS_VALID_NAME);

		methodDeclarationEClass = createEClass(METHOD_DECLARATION);
		createEOperation(methodDeclarationEClass, METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL);
		createEOperation(methodDeclarationEClass, METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT);
		createEOperation(methodDeclarationEClass, METHOD_DECLARATION___IS_STATIC);

		n4MethodDeclarationEClass = createEClass(N4_METHOD_DECLARATION);
		createEOperation(n4MethodDeclarationEClass, N4_METHOD_DECLARATION___IS_ABSTRACT);
		createEOperation(n4MethodDeclarationEClass, N4_METHOD_DECLARATION___IS_CONSTRUCTOR);
		createEOperation(n4MethodDeclarationEClass, N4_METHOD_DECLARATION___IS_CALLABLE_CONSTRUCTOR);
		createEOperation(n4MethodDeclarationEClass, N4_METHOD_DECLARATION___IS_STATIC);
		createEOperation(n4MethodDeclarationEClass, N4_METHOD_DECLARATION___IS_VALID_NAME);

		n4FieldAccessorEClass = createEClass(N4_FIELD_ACCESSOR);
		createEOperation(n4FieldAccessorEClass, N4_FIELD_ACCESSOR___IS_ABSTRACT);
		createEOperation(n4FieldAccessorEClass, N4_FIELD_ACCESSOR___IS_VALID_NAME);

		n4GetterDeclarationEClass = createEClass(N4_GETTER_DECLARATION);
		createEOperation(n4GetterDeclarationEClass, N4_GETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT);

		n4SetterDeclarationEClass = createEClass(N4_SETTER_DECLARATION);
		createEOperation(n4SetterDeclarationEClass, N4_SETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT);

		bindingPatternEClass = createEClass(BINDING_PATTERN);

		objectBindingPatternEClass = createEClass(OBJECT_BINDING_PATTERN);
		createEReference(objectBindingPatternEClass, OBJECT_BINDING_PATTERN__PROPERTIES);

		arrayBindingPatternEClass = createEClass(ARRAY_BINDING_PATTERN);
		createEReference(arrayBindingPatternEClass, ARRAY_BINDING_PATTERN__ELEMENTS);

		bindingPropertyEClass = createEClass(BINDING_PROPERTY);
		createEReference(bindingPropertyEClass, BINDING_PROPERTY__VALUE);
		createEOperation(bindingPropertyEClass, BINDING_PROPERTY___GET_NAME);
		createEOperation(bindingPropertyEClass, BINDING_PROPERTY___IS_VALID_NAME);

		bindingElementEClass = createEClass(BINDING_ELEMENT);
		createEAttribute(bindingElementEClass, BINDING_ELEMENT__REST);
		createEReference(bindingElementEClass, BINDING_ELEMENT__VAR_DECL);
		createEReference(bindingElementEClass, BINDING_ELEMENT__NESTED_PATTERN);
		createEReference(bindingElementEClass, BINDING_ELEMENT__EXPRESSION);
		createEOperation(bindingElementEClass, BINDING_ELEMENT___IS_ELISION);

		jsxChildEClass = createEClass(JSX_CHILD);

		jsxElementNameEClass = createEClass(JSX_ELEMENT_NAME);
		createEReference(jsxElementNameEClass, JSX_ELEMENT_NAME__EXPRESSION);

		jsxTextEClass = createEClass(JSX_TEXT);

		jsxExpressionEClass = createEClass(JSX_EXPRESSION);
		createEReference(jsxExpressionEClass, JSX_EXPRESSION__EXPRESSION);

		jsxAttributeEClass = createEClass(JSX_ATTRIBUTE);

		jsxPropertyAttributeEClass = createEClass(JSX_PROPERTY_ATTRIBUTE);
		createEReference(jsxPropertyAttributeEClass, JSX_PROPERTY_ATTRIBUTE__PROPERTY);
		createEAttribute(jsxPropertyAttributeEClass, JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT);
		createEReference(jsxPropertyAttributeEClass, JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE);

		jsxSpreadAttributeEClass = createEClass(JSX_SPREAD_ATTRIBUTE);
		createEReference(jsxSpreadAttributeEClass, JSX_SPREAD_ATTRIBUTE__EXPRESSION);

		jsxAbstractElementEClass = createEClass(JSX_ABSTRACT_ELEMENT);
		createEReference(jsxAbstractElementEClass, JSX_ABSTRACT_ELEMENT__JSX_CHILDREN);

		jsxElementEClass = createEClass(JSX_ELEMENT);
		createEReference(jsxElementEClass, JSX_ELEMENT__JSX_ELEMENT_NAME);
		createEReference(jsxElementEClass, JSX_ELEMENT__JSX_ATTRIBUTES);
		createEReference(jsxElementEClass, JSX_ELEMENT__JSX_CLOSING_NAME);

		jsxFragmentEClass = createEClass(JSX_FRAGMENT);

		versionedElementEClass = createEClass(VERSIONED_ELEMENT);
		createEAttribute(versionedElementEClass, VERSIONED_ELEMENT__DECLARED_VERSION);
		createEOperation(versionedElementEClass, VERSIONED_ELEMENT___HAS_DECLARED_VERSION);
		createEOperation(versionedElementEClass, VERSIONED_ELEMENT___GET_DECLARED_VERSION_OR_ZERO);

		versionedIdentifierRefEClass = createEClass(VERSIONED_IDENTIFIER_REF);
		createEOperation(versionedIdentifierRefEClass, VERSIONED_IDENTIFIER_REF___GET_VERSION);

		migrationContextVariableEClass = createEClass(MIGRATION_CONTEXT_VARIABLE);
		createEOperation(migrationContextVariableEClass, MIGRATION_CONTEXT_VARIABLE___GET_NAME);

		// Create enums
		moduleSpecifierFormEEnum = createEEnum(MODULE_SPECIFIER_FORM);
		variableStatementKeywordEEnum = createEEnum(VARIABLE_STATEMENT_KEYWORD);
		propertyNameKindEEnum = createEEnum(PROPERTY_NAME_KIND);
		postfixOperatorEEnum = createEEnum(POSTFIX_OPERATOR);
		unaryOperatorEEnum = createEEnum(UNARY_OPERATOR);
		multiplicativeOperatorEEnum = createEEnum(MULTIPLICATIVE_OPERATOR);
		additiveOperatorEEnum = createEEnum(ADDITIVE_OPERATOR);
		relationalOperatorEEnum = createEEnum(RELATIONAL_OPERATOR);
		equalityOperatorEEnum = createEEnum(EQUALITY_OPERATOR);
		binaryBitwiseOperatorEEnum = createEEnum(BINARY_BITWISE_OPERATOR);
		binaryLogicalOperatorEEnum = createEEnum(BINARY_LOGICAL_OPERATOR);
		shiftOperatorEEnum = createEEnum(SHIFT_OPERATOR);
		assignmentOperatorEEnum = createEEnum(ASSIGNMENT_OPERATOR);
		n4ModifierEEnum = createEEnum(N4_MODIFIER);

		// Create data types
		iteratorOfExpressionEDataType = createEDataType(ITERATOR_OF_EXPRESSION);
		iteratorOfYieldExpressionEDataType = createEDataType(ITERATOR_OF_YIELD_EXPRESSION);
		iteratorOfStatementEDataType = createEDataType(ITERATOR_OF_STATEMENT);
		iteratorOfReturnStatementEDataType = createEDataType(ITERATOR_OF_RETURN_STATEMENT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		TypeRefsPackage theTypeRefsPackage = (TypeRefsPackage)EPackage.Registry.INSTANCE.getEPackage(TypeRefsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		scriptEClass.getESuperTypes().add(this.getVariableEnvironmentElement());
		scriptEClass.getESuperTypes().add(this.getAnnotableElement());
		scriptEClass.getESuperTypes().add(this.getControlFlowElement());
		exportDeclarationEClass.getESuperTypes().add(this.getAnnotableScriptElement());
		importDeclarationEClass.getESuperTypes().add(this.getAnnotableScriptElement());
		namedImportSpecifierEClass.getESuperTypes().add(this.getImportSpecifier());
		defaultImportSpecifierEClass.getESuperTypes().add(this.getNamedImportSpecifier());
		namespaceImportSpecifierEClass.getESuperTypes().add(this.getImportSpecifier());
		namespaceImportSpecifierEClass.getESuperTypes().add(this.getTypeDefiningElement());
		typedElementEClass.getESuperTypes().add(this.getTypeProvidingElement());
		variableEClass.getESuperTypes().add(this.getTypedElement());
		variableEClass.getESuperTypes().add(theTypesPackage.getIdentifiableElement());
		variableEClass.getESuperTypes().add(this.getNamedElement());
		annotableScriptElementEClass.getESuperTypes().add(this.getAnnotableElement());
		annotableScriptElementEClass.getESuperTypes().add(this.getScriptElement());
		annotableExpressionEClass.getESuperTypes().add(this.getAnnotableElement());
		annotableExpressionEClass.getESuperTypes().add(this.getExpression());
		annotationListEClass.getESuperTypes().add(this.getAbstractAnnotationList());
		annotationListEClass.getESuperTypes().add(this.getScriptElement());
		annotationListEClass.getESuperTypes().add(this.getStatement());
		annotationListEClass.getESuperTypes().add(this.getExportableElement());
		expressionAnnotationListEClass.getESuperTypes().add(this.getAbstractAnnotationList());
		expressionAnnotationListEClass.getESuperTypes().add(this.getExpression());
		annotationEClass.getESuperTypes().add(this.getNamedElement());
		literalAnnotationArgumentEClass.getESuperTypes().add(this.getAnnotationArgument());
		typeRefAnnotationArgumentEClass.getESuperTypes().add(this.getAnnotationArgument());
		functionOrFieldAccessorEClass.getESuperTypes().add(this.getAnnotableElement());
		functionOrFieldAccessorEClass.getESuperTypes().add(this.getVariableEnvironmentElement());
		functionOrFieldAccessorEClass.getESuperTypes().add(this.getThisArgProvider());
		functionOrFieldAccessorEClass.getESuperTypes().add(theTypesPackage.getTypableElement());
		functionDefinitionEClass.getESuperTypes().add(this.getFunctionOrFieldAccessor());
		functionDefinitionEClass.getESuperTypes().add(this.getTypeDefiningElement());
		functionDefinitionEClass.getESuperTypes().add(this.getVersionedElement());
		fieldAccessorEClass.getESuperTypes().add(this.getFunctionOrFieldAccessor());
		fieldAccessorEClass.getESuperTypes().add(this.getTypeProvidingElement());
		fieldAccessorEClass.getESuperTypes().add(this.getPropertyNameOwner());
		functionDeclarationEClass.getESuperTypes().add(this.getAnnotableScriptElement());
		functionDeclarationEClass.getESuperTypes().add(this.getModifiableElement());
		functionDeclarationEClass.getESuperTypes().add(this.getStatement());
		functionDeclarationEClass.getESuperTypes().add(this.getFunctionDefinition());
		functionDeclarationEClass.getESuperTypes().add(this.getGenericDeclaration());
		functionDeclarationEClass.getESuperTypes().add(this.getExportableElement());
		functionDeclarationEClass.getESuperTypes().add(this.getNamedElement());
		functionExpressionEClass.getESuperTypes().add(this.getFunctionDefinition());
		functionExpressionEClass.getESuperTypes().add(this.getAnnotableExpression());
		functionExpressionEClass.getESuperTypes().add(this.getGenericDeclaration());
		functionExpressionEClass.getESuperTypes().add(this.getNamedElement());
		arrowFunctionEClass.getESuperTypes().add(this.getFunctionExpression());
		localArgumentsVariableEClass.getESuperTypes().add(this.getVariable());
		formalParameterEClass.getESuperTypes().add(this.getAnnotableElement());
		formalParameterEClass.getESuperTypes().add(this.getVariable());
		blockEClass.getESuperTypes().add(this.getStatement());
		blockEClass.getESuperTypes().add(this.getVariableEnvironmentElement());
		statementEClass.getESuperTypes().add(this.getScriptElement());
		statementEClass.getESuperTypes().add(this.getControlFlowElement());
		variableStatementEClass.getESuperTypes().add(this.getStatement());
		variableStatementEClass.getESuperTypes().add(this.getVariableDeclarationContainer());
		exportedVariableStatementEClass.getESuperTypes().add(this.getVariableStatement());
		exportedVariableStatementEClass.getESuperTypes().add(this.getExportableElement());
		exportedVariableStatementEClass.getESuperTypes().add(this.getAnnotableScriptElement());
		exportedVariableStatementEClass.getESuperTypes().add(this.getModifiableElement());
		variableDeclarationOrBindingEClass.getESuperTypes().add(this.getControlFlowElement());
		variableBindingEClass.getESuperTypes().add(this.getVariableDeclarationOrBinding());
		exportedVariableBindingEClass.getESuperTypes().add(this.getVariableBinding());
		variableDeclarationEClass.getESuperTypes().add(this.getVariableDeclarationOrBinding());
		variableDeclarationEClass.getESuperTypes().add(this.getAnnotableElement());
		variableDeclarationEClass.getESuperTypes().add(this.getVariable());
		exportedVariableDeclarationEClass.getESuperTypes().add(this.getVariableDeclaration());
		emptyStatementEClass.getESuperTypes().add(this.getStatement());
		expressionStatementEClass.getESuperTypes().add(this.getStatement());
		ifStatementEClass.getESuperTypes().add(this.getStatement());
		iterationStatementEClass.getESuperTypes().add(this.getStatement());
		doStatementEClass.getESuperTypes().add(this.getIterationStatement());
		whileStatementEClass.getESuperTypes().add(this.getIterationStatement());
		forStatementEClass.getESuperTypes().add(this.getVariableDeclarationContainer());
		forStatementEClass.getESuperTypes().add(this.getIterationStatement());
		forStatementEClass.getESuperTypes().add(this.getVariableEnvironmentElement());
		continueStatementEClass.getESuperTypes().add(this.getStatement());
		continueStatementEClass.getESuperTypes().add(this.getLabelRef());
		breakStatementEClass.getESuperTypes().add(this.getStatement());
		breakStatementEClass.getESuperTypes().add(this.getLabelRef());
		returnStatementEClass.getESuperTypes().add(this.getStatement());
		withStatementEClass.getESuperTypes().add(this.getStatement());
		withStatementEClass.getESuperTypes().add(this.getVariableEnvironmentElement());
		switchStatementEClass.getESuperTypes().add(this.getStatement());
		switchStatementEClass.getESuperTypes().add(this.getVariableEnvironmentElement());
		abstractCaseClauseEClass.getESuperTypes().add(this.getControlFlowElement());
		caseClauseEClass.getESuperTypes().add(this.getAbstractCaseClause());
		defaultClauseEClass.getESuperTypes().add(this.getAbstractCaseClause());
		labelledStatementEClass.getESuperTypes().add(this.getStatement());
		labelledStatementEClass.getESuperTypes().add(this.getNamedElement());
		throwStatementEClass.getESuperTypes().add(this.getStatement());
		tryStatementEClass.getESuperTypes().add(this.getStatement());
		catchBlockEClass.getESuperTypes().add(this.getAbstractCatchBlock());
		catchBlockEClass.getESuperTypes().add(this.getVariableEnvironmentElement());
		catchVariableEClass.getESuperTypes().add(this.getVariable());
		finallyBlockEClass.getESuperTypes().add(this.getAbstractCatchBlock());
		debuggerStatementEClass.getESuperTypes().add(this.getStatement());
		primaryExpressionEClass.getESuperTypes().add(this.getExpression());
		parenExpressionEClass.getESuperTypes().add(this.getPrimaryExpression());
		identifierRefEClass.getESuperTypes().add(this.getPrimaryExpression());
		identifierRefEClass.getESuperTypes().add(this.getStrictModeRelevant());
		identifierRefEClass.getESuperTypes().add(theTypeRefsPackage.getVersionable());
		superLiteralEClass.getESuperTypes().add(this.getPrimaryExpression());
		thisLiteralEClass.getESuperTypes().add(this.getPrimaryExpression());
		thisLiteralEClass.getESuperTypes().add(this.getStrictModeRelevant());
		arrayLiteralEClass.getESuperTypes().add(this.getPrimaryExpression());
		arrayElementEClass.getESuperTypes().add(theTypesPackage.getTypableElement());
		arrayPaddingEClass.getESuperTypes().add(this.getArrayElement());
		objectLiteralEClass.getESuperTypes().add(this.getPrimaryExpression());
		objectLiteralEClass.getESuperTypes().add(this.getThisTarget());
		objectLiteralEClass.getESuperTypes().add(this.getTypeDefiningElement());
		propertyAssignmentEClass.getESuperTypes().add(this.getAnnotableElement());
		propertyAssignmentEClass.getESuperTypes().add(this.getVariableEnvironmentElement());
		propertyAssignmentEClass.getESuperTypes().add(this.getPropertyNameOwner());
		propertyAssignmentEClass.getESuperTypes().add(theTypesPackage.getTypableElement());
		propertyNameOwnerEClass.getESuperTypes().add(this.getNamedElement());
		annotablePropertyAssignmentEClass.getESuperTypes().add(this.getPropertyAssignment());
		propertyAssignmentAnnotationListEClass.getESuperTypes().add(this.getAbstractAnnotationList());
		propertyAssignmentAnnotationListEClass.getESuperTypes().add(this.getPropertyAssignment());
		propertyNameValuePairEClass.getESuperTypes().add(this.getAnnotablePropertyAssignment());
		propertyNameValuePairEClass.getESuperTypes().add(this.getTypedElement());
		propertyNameValuePairEClass.getESuperTypes().add(theTypesPackage.getTypableElement());
		propertyNameValuePairSingleNameEClass.getESuperTypes().add(this.getPropertyNameValuePair());
		propertyMethodDeclarationEClass.getESuperTypes().add(this.getAnnotablePropertyAssignment());
		propertyMethodDeclarationEClass.getESuperTypes().add(this.getMethodDeclaration());
		propertyMethodDeclarationEClass.getESuperTypes().add(this.getTypeProvidingElement());
		getterDeclarationEClass.getESuperTypes().add(this.getFieldAccessor());
		getterDeclarationEClass.getESuperTypes().add(this.getTypedElement());
		setterDeclarationEClass.getESuperTypes().add(this.getFieldAccessor());
		propertyGetterDeclarationEClass.getESuperTypes().add(this.getGetterDeclaration());
		propertyGetterDeclarationEClass.getESuperTypes().add(this.getAnnotablePropertyAssignment());
		propertySetterDeclarationEClass.getESuperTypes().add(this.getSetterDeclaration());
		propertySetterDeclarationEClass.getESuperTypes().add(this.getAnnotablePropertyAssignment());
		propertySpreadEClass.getESuperTypes().add(this.getAnnotablePropertyAssignment());
		expressionEClass.getESuperTypes().add(theTypesPackage.getTypableElement());
		expressionEClass.getESuperTypes().add(this.getControlFlowElement());
		newTargetEClass.getESuperTypes().add(this.getExpression());
		newExpressionEClass.getESuperTypes().add(this.getExpression());
		newExpressionEClass.getESuperTypes().add(this.getParameterizedAccess());
		expressionWithTargetEClass.getESuperTypes().add(this.getExpression());
		parameterizedCallExpressionEClass.getESuperTypes().add(this.getExpressionWithTarget());
		parameterizedCallExpressionEClass.getESuperTypes().add(this.getParameterizedAccess());
		importCallExpressionEClass.getESuperTypes().add(this.getExpression());
		argumentEClass.getESuperTypes().add(theTypesPackage.getTypableElement());
		indexedAccessExpressionEClass.getESuperTypes().add(this.getExpressionWithTarget());
		indexedAccessExpressionEClass.getESuperTypes().add(this.getMemberAccess());
		taggedTemplateStringEClass.getESuperTypes().add(this.getExpressionWithTarget());
		parameterizedPropertyAccessExpressionEClass.getESuperTypes().add(this.getExpressionWithTarget());
		parameterizedPropertyAccessExpressionEClass.getESuperTypes().add(this.getMemberAccess());
		parameterizedPropertyAccessExpressionEClass.getESuperTypes().add(this.getParameterizedAccess());
		awaitExpressionEClass.getESuperTypes().add(this.getExpression());
		promisifyExpressionEClass.getESuperTypes().add(this.getExpression());
		yieldExpressionEClass.getESuperTypes().add(this.getExpression());
		literalEClass.getESuperTypes().add(this.getPrimaryExpression());
		nullLiteralEClass.getESuperTypes().add(this.getLiteral());
		booleanLiteralEClass.getESuperTypes().add(this.getLiteral());
		stringLiteralEClass.getESuperTypes().add(this.getLiteral());
		templateLiteralEClass.getESuperTypes().add(this.getPrimaryExpression());
		templateSegmentEClass.getESuperTypes().add(this.getLiteral());
		numericLiteralEClass.getESuperTypes().add(this.getLiteral());
		doubleLiteralEClass.getESuperTypes().add(this.getNumericLiteral());
		abstractIntLiteralEClass.getESuperTypes().add(this.getNumericLiteral());
		intLiteralEClass.getESuperTypes().add(this.getAbstractIntLiteral());
		binaryIntLiteralEClass.getESuperTypes().add(this.getAbstractIntLiteral());
		octalIntLiteralEClass.getESuperTypes().add(this.getAbstractIntLiteral());
		legacyOctalIntLiteralEClass.getESuperTypes().add(this.getAbstractIntLiteral());
		hexIntLiteralEClass.getESuperTypes().add(this.getAbstractIntLiteral());
		scientificIntLiteralEClass.getESuperTypes().add(this.getAbstractIntLiteral());
		regularExpressionLiteralEClass.getESuperTypes().add(this.getLiteral());
		postfixExpressionEClass.getESuperTypes().add(this.getExpression());
		unaryExpressionEClass.getESuperTypes().add(this.getExpression());
		castExpressionEClass.getESuperTypes().add(this.getExpression());
		multiplicativeExpressionEClass.getESuperTypes().add(this.getExpression());
		additiveExpressionEClass.getESuperTypes().add(this.getExpression());
		shiftExpressionEClass.getESuperTypes().add(this.getExpression());
		relationalExpressionEClass.getESuperTypes().add(this.getExpression());
		equalityExpressionEClass.getESuperTypes().add(this.getExpression());
		binaryBitwiseExpressionEClass.getESuperTypes().add(this.getExpression());
		binaryLogicalExpressionEClass.getESuperTypes().add(this.getExpression());
		coalesceExpressionEClass.getESuperTypes().add(this.getExpression());
		conditionalExpressionEClass.getESuperTypes().add(this.getExpression());
		assignmentExpressionEClass.getESuperTypes().add(this.getExpression());
		commaExpressionEClass.getESuperTypes().add(this.getExpression());
		typeDefiningElementEClass.getESuperTypes().add(theTypesPackage.getTypableElement());
		genericDeclarationEClass.getESuperTypes().add(this.getTypeDefiningElement());
		n4TypeDefinitionEClass.getESuperTypes().add(this.getAnnotableElement());
		n4TypeDefinitionEClass.getESuperTypes().add(this.getTypeDefiningElement());
		n4TypeDeclarationEClass.getESuperTypes().add(this.getN4TypeDefinition());
		n4TypeDeclarationEClass.getESuperTypes().add(this.getAnnotableScriptElement());
		n4TypeDeclarationEClass.getESuperTypes().add(this.getModifiableElement());
		n4TypeDeclarationEClass.getESuperTypes().add(this.getExportableElement());
		n4TypeDeclarationEClass.getESuperTypes().add(this.getNamedElement());
		n4ClassifierDeclarationEClass.getESuperTypes().add(this.getN4TypeDeclaration());
		n4ClassifierDeclarationEClass.getESuperTypes().add(this.getN4ClassifierDefinition());
		n4ClassifierDeclarationEClass.getESuperTypes().add(this.getGenericDeclaration());
		n4ClassifierDeclarationEClass.getESuperTypes().add(this.getThisTarget());
		n4ClassifierDefinitionEClass.getESuperTypes().add(this.getN4TypeDefinition());
		n4ClassDefinitionEClass.getESuperTypes().add(this.getN4ClassifierDefinition());
		n4ClassDefinitionEClass.getESuperTypes().add(this.getThisTarget());
		n4ClassDeclarationEClass.getESuperTypes().add(this.getN4ClassDefinition());
		n4ClassDeclarationEClass.getESuperTypes().add(this.getN4ClassifierDeclaration());
		n4ClassDeclarationEClass.getESuperTypes().add(theTypeRefsPackage.getVersionable());
		n4ClassDeclarationEClass.getESuperTypes().add(this.getVersionedElement());
		n4ClassExpressionEClass.getESuperTypes().add(this.getN4ClassDefinition());
		n4ClassExpressionEClass.getESuperTypes().add(this.getPrimaryExpression());
		n4ClassExpressionEClass.getESuperTypes().add(this.getAnnotableExpression());
		n4ClassExpressionEClass.getESuperTypes().add(this.getNamedElement());
		n4InterfaceDeclarationEClass.getESuperTypes().add(this.getN4ClassifierDeclaration());
		n4InterfaceDeclarationEClass.getESuperTypes().add(theTypeRefsPackage.getVersionable());
		n4InterfaceDeclarationEClass.getESuperTypes().add(this.getVersionedElement());
		n4EnumDeclarationEClass.getESuperTypes().add(this.getN4TypeDeclaration());
		n4EnumDeclarationEClass.getESuperTypes().add(theTypeRefsPackage.getVersionable());
		n4EnumDeclarationEClass.getESuperTypes().add(this.getVersionedElement());
		n4EnumLiteralEClass.getESuperTypes().add(this.getNamedElement());
		n4EnumLiteralEClass.getESuperTypes().add(theTypesPackage.getTypableElement());
		n4TypeAliasDeclarationEClass.getESuperTypes().add(this.getN4TypeDeclaration());
		n4TypeAliasDeclarationEClass.getESuperTypes().add(this.getGenericDeclaration());
		n4TypeAliasDeclarationEClass.getESuperTypes().add(this.getTypedElement());
		n4MemberDeclarationEClass.getESuperTypes().add(this.getAnnotableElement());
		n4MemberDeclarationEClass.getESuperTypes().add(this.getModifiableElement());
		n4MemberDeclarationEClass.getESuperTypes().add(this.getTypeProvidingElement());
		n4MemberDeclarationEClass.getESuperTypes().add(theTypesPackage.getTypableElement());
		n4MemberDeclarationEClass.getESuperTypes().add(this.getNamedElement());
		annotableN4MemberDeclarationEClass.getESuperTypes().add(this.getN4MemberDeclaration());
		n4MemberAnnotationListEClass.getESuperTypes().add(this.getAbstractAnnotationList());
		n4MemberAnnotationListEClass.getESuperTypes().add(this.getN4MemberDeclaration());
		n4FieldDeclarationEClass.getESuperTypes().add(this.getAnnotableN4MemberDeclaration());
		n4FieldDeclarationEClass.getESuperTypes().add(this.getTypedElement());
		n4FieldDeclarationEClass.getESuperTypes().add(this.getThisArgProvider());
		n4FieldDeclarationEClass.getESuperTypes().add(this.getPropertyNameOwner());
		methodDeclarationEClass.getESuperTypes().add(this.getFunctionDefinition());
		methodDeclarationEClass.getESuperTypes().add(this.getGenericDeclaration());
		methodDeclarationEClass.getESuperTypes().add(this.getTypedElement());
		methodDeclarationEClass.getESuperTypes().add(this.getPropertyNameOwner());
		n4MethodDeclarationEClass.getESuperTypes().add(this.getAnnotableN4MemberDeclaration());
		n4MethodDeclarationEClass.getESuperTypes().add(this.getMethodDeclaration());
		n4FieldAccessorEClass.getESuperTypes().add(this.getAnnotableN4MemberDeclaration());
		n4FieldAccessorEClass.getESuperTypes().add(this.getFieldAccessor());
		n4GetterDeclarationEClass.getESuperTypes().add(this.getGetterDeclaration());
		n4GetterDeclarationEClass.getESuperTypes().add(this.getN4FieldAccessor());
		n4SetterDeclarationEClass.getESuperTypes().add(this.getSetterDeclaration());
		n4SetterDeclarationEClass.getESuperTypes().add(this.getN4FieldAccessor());
		bindingPatternEClass.getESuperTypes().add(this.getControlFlowElement());
		objectBindingPatternEClass.getESuperTypes().add(this.getBindingPattern());
		arrayBindingPatternEClass.getESuperTypes().add(this.getBindingPattern());
		bindingPropertyEClass.getESuperTypes().add(this.getPropertyNameOwner());
		bindingElementEClass.getESuperTypes().add(this.getControlFlowElement());
		jsxTextEClass.getESuperTypes().add(this.getJSXChild());
		jsxExpressionEClass.getESuperTypes().add(this.getJSXChild());
		jsxAttributeEClass.getESuperTypes().add(this.getControlFlowElement());
		jsxPropertyAttributeEClass.getESuperTypes().add(this.getJSXAttribute());
		jsxPropertyAttributeEClass.getESuperTypes().add(this.getMemberAccess());
		jsxSpreadAttributeEClass.getESuperTypes().add(this.getJSXAttribute());
		jsxAbstractElementEClass.getESuperTypes().add(this.getExpression());
		jsxElementEClass.getESuperTypes().add(this.getExpression());
		jsxElementEClass.getESuperTypes().add(this.getJSXChild());
		jsxElementEClass.getESuperTypes().add(this.getJSXAbstractElement());
		jsxFragmentEClass.getESuperTypes().add(this.getJSXChild());
		jsxFragmentEClass.getESuperTypes().add(this.getJSXAbstractElement());
		versionedIdentifierRefEClass.getESuperTypes().add(this.getIdentifierRef());
		versionedIdentifierRefEClass.getESuperTypes().add(theTypeRefsPackage.getVersionedReference());
		migrationContextVariableEClass.getESuperTypes().add(this.getVariable());

		// Initialize classes, features, and operations; add parameters
		initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getNamedElement__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(controlFlowElementEClass, ControlFlowElement.class, "ControlFlowElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(scriptEClass, Script.class, "Script", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getScript_Hashbang(), theEcorePackage.getEString(), "hashbang", null, 0, 1, Script.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScript_Annotations(), this.getAnnotation(), null, "annotations", null, 0, -1, Script.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScript_ScriptElements(), this.getScriptElement(), null, "scriptElements", null, 0, -1, Script.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScript_Module(), theTypesPackage.getTModule(), null, "module", null, 0, 1, Script.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScript_FlaggedUsageMarkingFinished(), theEcorePackage.getEBoolean(), "flaggedUsageMarkingFinished", null, 0, 1, Script.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(scriptElementEClass, ScriptElement.class, "ScriptElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(exportDeclarationEClass, ExportDeclaration.class, "ExportDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExportDeclaration_ExportedElement(), this.getExportableElement(), null, "exportedElement", null, 0, 1, ExportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExportDeclaration_DefaultExportedExpression(), this.getExpression(), null, "defaultExportedExpression", null, 0, 1, ExportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExportDeclaration_NamedExports(), this.getExportSpecifier(), null, "namedExports", null, 0, -1, ExportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExportDeclaration_WildcardExport(), theEcorePackage.getEBoolean(), "wildcardExport", null, 0, 1, ExportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExportDeclaration_DefaultExport(), theEcorePackage.getEBoolean(), "defaultExport", null, 0, 1, ExportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExportDeclaration_ReexportedFrom(), theTypesPackage.getTModule(), null, "reexportedFrom", null, 0, 1, ExportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exportSpecifierEClass, ExportSpecifier.class, "ExportSpecifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExportSpecifier_Element(), this.getIdentifierRef(), null, "element", null, 0, 1, ExportSpecifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExportSpecifier_Alias(), theEcorePackage.getEString(), "alias", null, 0, 1, ExportSpecifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exportableElementEClass, ExportableElement.class, "ExportableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getExportableElement__IsExported(), theEcorePackage.getEBoolean(), "isExported", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getExportableElement__IsExportedAsDefault(), theEcorePackage.getEBoolean(), "isExportedAsDefault", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getExportableElement__GetExportedName(), theEcorePackage.getEString(), "getExportedName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getExportableElement__IsToplevel(), theEcorePackage.getEBoolean(), "isToplevel", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(importDeclarationEClass, ImportDeclaration.class, "ImportDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getImportDeclaration_ImportSpecifiers(), this.getImportSpecifier(), null, "importSpecifiers", null, 0, -1, ImportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportDeclaration_ImportFrom(), theEcorePackage.getEBoolean(), "importFrom", null, 0, 1, ImportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getImportDeclaration_Module(), theTypesPackage.getTModule(), null, "module", null, 0, 1, ImportDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportDeclaration_ModuleSpecifierAsText(), theEcorePackage.getEString(), "moduleSpecifierAsText", null, 0, 1, ImportDeclaration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportDeclaration_ModuleSpecifierForm(), this.getModuleSpecifierForm(), "moduleSpecifierForm", null, 0, 1, ImportDeclaration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getImportDeclaration__IsBare(), theEcorePackage.getEBoolean(), "isBare", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getImportDeclaration__IsRetainedAtRuntime(), theEcorePackage.getEBoolean(), "isRetainedAtRuntime", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(importSpecifierEClass, ImportSpecifier.class, "ImportSpecifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImportSpecifier_FlaggedUsedInCode(), theEcorePackage.getEBoolean(), "flaggedUsedInCode", null, 0, 1, ImportSpecifier.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportSpecifier_RetainedAtRuntime(), theEcorePackage.getEBoolean(), "retainedAtRuntime", null, 0, 1, ImportSpecifier.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedImportSpecifierEClass, NamedImportSpecifier.class, "NamedImportSpecifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNamedImportSpecifier_ImportedElement(), theTypesPackage.getTExportableElement(), null, "importedElement", null, 0, 1, NamedImportSpecifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedImportSpecifier_ImportedElementAsText(), theEcorePackage.getEString(), "importedElementAsText", null, 0, 1, NamedImportSpecifier.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedImportSpecifier_Alias(), theEcorePackage.getEString(), "alias", null, 0, 1, NamedImportSpecifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getNamedImportSpecifier__IsDefaultImport(), theEcorePackage.getEBoolean(), "isDefaultImport", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(defaultImportSpecifierEClass, DefaultImportSpecifier.class, "DefaultImportSpecifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getDefaultImportSpecifier__GetAlias(), theEcorePackage.getEString(), "getAlias", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getDefaultImportSpecifier__IsDefaultImport(), theEcorePackage.getEBoolean(), "isDefaultImport", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(namespaceImportSpecifierEClass, NamespaceImportSpecifier.class, "NamespaceImportSpecifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamespaceImportSpecifier_DeclaredDynamic(), theEcorePackage.getEBoolean(), "declaredDynamic", null, 0, 1, NamespaceImportSpecifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamespaceImportSpecifier_Alias(), theEcorePackage.getEString(), "alias", null, 0, 1, NamespaceImportSpecifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeProvidingElementEClass, TypeProvidingElement.class, "TypeProvidingElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getTypeProvidingElement__GetDeclaredTypeRef(), theTypeRefsPackage.getTypeRef(), "getDeclaredTypeRef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(typedElementEClass, TypedElement.class, "TypedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypedElement_DeclaredTypeRef(), theTypeRefsPackage.getTypeRef(), null, "declaredTypeRef", null, 0, 1, TypedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableEnvironmentElementEClass, VariableEnvironmentElement.class, "VariableEnvironmentElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getVariableEnvironmentElement__AppliesOnlyToBlockScopedElements(), theEcorePackage.getEBoolean(), "appliesOnlyToBlockScopedElements", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(thisTargetEClass, ThisTarget.class, "ThisTarget", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(thisArgProviderEClass, ThisArgProvider.class, "ThisArgProvider", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(variableEClass, Variable.class, "Variable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getVariable__IsConst(), theEcorePackage.getEBoolean(), "isConst", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(annotableElementEClass, AnnotableElement.class, "AnnotableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getAnnotableElement__GetAnnotations(), this.getAnnotation(), "getAnnotations", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getAnnotableElement__GetAllAnnotations(), this.getAnnotation(), "getAllAnnotations", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(annotableScriptElementEClass, AnnotableScriptElement.class, "AnnotableScriptElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAnnotableScriptElement_AnnotationList(), this.getAnnotationList(), null, "annotationList", null, 0, 1, AnnotableScriptElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getAnnotableScriptElement__GetAnnotations(), this.getAnnotation(), "getAnnotations", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(annotableExpressionEClass, AnnotableExpression.class, "AnnotableExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAnnotableExpression_AnnotationList(), this.getExpressionAnnotationList(), null, "annotationList", null, 0, 1, AnnotableExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getAnnotableExpression__GetAnnotations(), this.getAnnotation(), "getAnnotations", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(abstractAnnotationListEClass, AbstractAnnotationList.class, "AbstractAnnotationList", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractAnnotationList_Annotations(), this.getAnnotation(), null, "annotations", null, 0, -1, AbstractAnnotationList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(annotationListEClass, AnnotationList.class, "AnnotationList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(expressionAnnotationListEClass, ExpressionAnnotationList.class, "ExpressionAnnotationList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(annotationEClass, Annotation.class, "Annotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAnnotation_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAnnotation_Args(), this.getAnnotationArgument(), null, "args", null, 0, -1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getAnnotation__GetAnnotatedElement(), theEcorePackage.getEObject(), "getAnnotatedElement", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(annotationArgumentEClass, AnnotationArgument.class, "AnnotationArgument", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getAnnotationArgument__Value(), theEcorePackage.getEObject(), "value", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getAnnotationArgument__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(literalAnnotationArgumentEClass, LiteralAnnotationArgument.class, "LiteralAnnotationArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLiteralAnnotationArgument_Literal(), this.getLiteral(), null, "literal", null, 0, 1, LiteralAnnotationArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getLiteralAnnotationArgument__Value(), this.getLiteral(), "value", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(typeRefAnnotationArgumentEClass, TypeRefAnnotationArgument.class, "TypeRefAnnotationArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeRefAnnotationArgument_TypeRef(), theTypeRefsPackage.getTypeRef(), null, "typeRef", null, 0, 1, TypeRefAnnotationArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTypeRefAnnotationArgument__Value(), theTypeRefsPackage.getTypeRef(), "value", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(functionOrFieldAccessorEClass, FunctionOrFieldAccessor.class, "FunctionOrFieldAccessor", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunctionOrFieldAccessor_Body(), this.getBlock(), null, "body", null, 0, 1, FunctionOrFieldAccessor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunctionOrFieldAccessor__lok(), this.getLocalArgumentsVariable(), null, "_lok", null, 0, 1, FunctionOrFieldAccessor.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFunctionOrFieldAccessor__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFunctionOrFieldAccessor__GetLocalArgumentsVariable(), this.getLocalArgumentsVariable(), "getLocalArgumentsVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFunctionOrFieldAccessor__IsReturnValueOptional(), theEcorePackage.getEBoolean(), "isReturnValueOptional", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFunctionOrFieldAccessor__IsAsync(), theEcorePackage.getEBoolean(), "isAsync", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFunctionOrFieldAccessor__GetDefinedFunctionOrAccessor(), theTypesPackage.getIdentifiableElement(), "getDefinedFunctionOrAccessor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(functionDefinitionEClass, FunctionDefinition.class, "FunctionDefinition", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunctionDefinition_Fpars(), this.getFormalParameter(), null, "fpars", null, 0, -1, FunctionDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunctionDefinition_DeclaredReturnTypeRef(), theTypeRefsPackage.getTypeRef(), null, "declaredReturnTypeRef", null, 0, 1, FunctionDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFunctionDefinition_Generator(), theEcorePackage.getEBoolean(), "generator", null, 0, 1, FunctionDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFunctionDefinition_DeclaredAsync(), theEcorePackage.getEBoolean(), "declaredAsync", null, 0, 1, FunctionDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFunctionDefinition__IsReturnValueOptional(), theEcorePackage.getEBoolean(), "isReturnValueOptional", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFunctionDefinition__IsAsync(), theEcorePackage.getEBoolean(), "isAsync", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFunctionDefinition__GetDefinedFunction(), theTypesPackage.getTFunction(), "getDefinedFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fieldAccessorEClass, FieldAccessor.class, "FieldAccessor", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFieldAccessor_DeclaredOptional(), theEcorePackage.getEBoolean(), "declaredOptional", null, 0, 1, FieldAccessor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFieldAccessor__GetDeclaredTypeRef(), theTypeRefsPackage.getTypeRef(), "getDeclaredTypeRef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFieldAccessor__GetDefinedAccessor(), theTypesPackage.getFieldAccessor(), "getDefinedAccessor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFieldAccessor__IsOptional(), theEcorePackage.getEBoolean(), "isOptional", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(functionDeclarationEClass, FunctionDeclaration.class, "FunctionDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFunctionDeclaration_Name(), theEcorePackage.getEString(), "name", null, 0, 1, FunctionDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunctionDeclaration__migrationContext(), this.getMigrationContextVariable(), null, "_migrationContext", null, 0, 1, FunctionDeclaration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFunctionDeclaration__IsExternal(), theEcorePackage.getEBoolean(), "isExternal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFunctionDeclaration__GetMigrationContextVariable(), this.getMigrationContextVariable(), "getMigrationContextVariable", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(functionExpressionEClass, FunctionExpression.class, "FunctionExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFunctionExpression_Name(), theEcorePackage.getEString(), "name", null, 0, 1, FunctionExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFunctionExpression__IsArrowFunction(), theEcorePackage.getEBoolean(), "isArrowFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(arrowFunctionEClass, ArrowFunction.class, "ArrowFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArrowFunction_HasBracesAroundBody(), theEcorePackage.getEBoolean(), "hasBracesAroundBody", null, 0, 1, ArrowFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getArrowFunction__IsArrowFunction(), theEcorePackage.getEBoolean(), "isArrowFunction", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getArrowFunction__IsSingleExprImplicitReturn(), theEcorePackage.getEBoolean(), "isSingleExprImplicitReturn", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getArrowFunction__GetSingleExpression(), this.getExpression(), "getSingleExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getArrowFunction__ImplicitReturnExpr(), this.getExpression(), "implicitReturnExpr", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(localArgumentsVariableEClass, LocalArgumentsVariable.class, "LocalArgumentsVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getLocalArgumentsVariable__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(formalParameterEClass, FormalParameter.class, "FormalParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFormalParameter_Annotations(), this.getAnnotation(), null, "annotations", null, 0, -1, FormalParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFormalParameter_Variadic(), theEcorePackage.getEBoolean(), "variadic", null, 0, 1, FormalParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFormalParameter_DefinedTypeElement(), theTypesPackage.getTFormalParameter(), null, "definedTypeElement", null, 0, 1, FormalParameter.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFormalParameter_HasInitializerAssignment(), theEcorePackage.getEBoolean(), "hasInitializerAssignment", null, 0, 1, FormalParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFormalParameter_Initializer(), this.getExpression(), null, "initializer", null, 0, 1, FormalParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFormalParameter_BindingPattern(), this.getBindingPattern(), null, "bindingPattern", null, 0, 1, FormalParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(blockEClass, Block.class, "Block", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlock_Statements(), this.getStatement(), null, "statements", null, 0, -1, Block.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getBlock__AppliesOnlyToBlockScopedElements(), theEcorePackage.getEBoolean(), "appliesOnlyToBlockScopedElements", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__GetAllExpressions(), this.getIteratorOfExpression(), "getAllExpressions", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__GetAllYieldExpressions(), this.getIteratorOfYieldExpression(), "getAllYieldExpressions", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__GetAllVoidYieldExpressions(), this.getIteratorOfYieldExpression(), "getAllVoidYieldExpressions", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__GetAllNonVoidYieldExpressions(), this.getIteratorOfYieldExpression(), "getAllNonVoidYieldExpressions", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__HasNonVoidYield(), theEcorePackage.getEBoolean(), "hasNonVoidYield", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__GetAllStatements(), this.getIteratorOfStatement(), "getAllStatements", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__GetAllReturnStatements(), this.getIteratorOfReturnStatement(), "getAllReturnStatements", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__GetAllNonVoidReturnStatements(), this.getIteratorOfReturnStatement(), "getAllNonVoidReturnStatements", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__GetAllVoidReturnStatements(), this.getIteratorOfReturnStatement(), "getAllVoidReturnStatements", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBlock__HasNonVoidReturn(), theEcorePackage.getEBoolean(), "hasNonVoidReturn", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(statementEClass, Statement.class, "Statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(variableDeclarationContainerEClass, VariableDeclarationContainer.class, "VariableDeclarationContainer", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariableDeclarationContainer_VarDeclsOrBindings(), this.getVariableDeclarationOrBinding(), null, "varDeclsOrBindings", null, 0, -1, VariableDeclarationContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariableDeclarationContainer_VarStmtKeyword(), this.getVariableStatementKeyword(), "varStmtKeyword", null, 0, 1, VariableDeclarationContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getVariableDeclarationContainer__GetVarDecl(), this.getVariableDeclaration(), "getVarDecl", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getVariableDeclarationContainer__IsBlockScoped(), theEcorePackage.getEBoolean(), "isBlockScoped", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(variableStatementEClass, VariableStatement.class, "VariableStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(exportedVariableStatementEClass, ExportedVariableStatement.class, "ExportedVariableStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getExportedVariableStatement__IsExternal(), theEcorePackage.getEBoolean(), "isExternal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(variableDeclarationOrBindingEClass, VariableDeclarationOrBinding.class, "VariableDeclarationOrBinding", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getVariableDeclarationOrBinding__GetVariableDeclarations(), this.getVariableDeclaration(), "getVariableDeclarations", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getVariableDeclarationOrBinding__GetExpression(), this.getExpression(), "getExpression", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(variableBindingEClass, VariableBinding.class, "VariableBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariableBinding_Pattern(), this.getBindingPattern(), null, "pattern", null, 0, 1, VariableBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariableBinding_Expression(), this.getExpression(), null, "expression", null, 0, 1, VariableBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exportedVariableBindingEClass, ExportedVariableBinding.class, "ExportedVariableBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExportedVariableBinding_DefinedVariable(), theTypesPackage.getTVariable(), null, "definedVariable", null, 0, 1, ExportedVariableBinding.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableDeclarationEClass, VariableDeclaration.class, "VariableDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariableDeclaration_Annotations(), this.getAnnotation(), null, "annotations", null, 0, -1, VariableDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariableDeclaration_Expression(), this.getExpression(), null, "expression", null, 0, 1, VariableDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getVariableDeclaration__IsConst(), theEcorePackage.getEBoolean(), "isConst", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(exportedVariableDeclarationEClass, ExportedVariableDeclaration.class, "ExportedVariableDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExportedVariableDeclaration_DefinedVariable(), theTypesPackage.getTVariable(), null, "definedVariable", null, 0, 1, ExportedVariableDeclaration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(emptyStatementEClass, EmptyStatement.class, "EmptyStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(expressionStatementEClass, ExpressionStatement.class, "ExpressionStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExpressionStatement_Expression(), this.getExpression(), null, "expression", null, 0, 1, ExpressionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ifStatementEClass, IfStatement.class, "IfStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIfStatement_Expression(), this.getExpression(), null, "expression", null, 0, 1, IfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIfStatement_IfStmt(), this.getStatement(), null, "ifStmt", null, 0, 1, IfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIfStatement_ElseStmt(), this.getStatement(), null, "elseStmt", null, 0, 1, IfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iterationStatementEClass, IterationStatement.class, "IterationStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIterationStatement_Statement(), this.getStatement(), null, "statement", null, 0, 1, IterationStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIterationStatement_Expression(), this.getExpression(), null, "expression", null, 0, 1, IterationStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(doStatementEClass, DoStatement.class, "DoStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(whileStatementEClass, WhileStatement.class, "WhileStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(forStatementEClass, ForStatement.class, "ForStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getForStatement_InitExpr(), this.getExpression(), null, "initExpr", null, 0, 1, ForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getForStatement_UpdateExpr(), this.getExpression(), null, "updateExpr", null, 0, 1, ForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getForStatement_Await(), theEcorePackage.getEBoolean(), "await", null, 0, 1, ForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getForStatement_ForIn(), theEcorePackage.getEBoolean(), "forIn", null, 0, 1, ForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getForStatement_ForOf(), theEcorePackage.getEBoolean(), "forOf", null, 0, 1, ForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getForStatement__IsForPlain(), theEcorePackage.getEBoolean(), "isForPlain", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getForStatement__AppliesOnlyToBlockScopedElements(), theEcorePackage.getEBoolean(), "appliesOnlyToBlockScopedElements", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(labelRefEClass, LabelRef.class, "LabelRef", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLabelRef_Label(), this.getLabelledStatement(), null, "label", null, 0, 1, LabelRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLabelRef_LabelAsText(), theEcorePackage.getEString(), "labelAsText", null, 0, 1, LabelRef.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(continueStatementEClass, ContinueStatement.class, "ContinueStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(breakStatementEClass, BreakStatement.class, "BreakStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(returnStatementEClass, ReturnStatement.class, "ReturnStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReturnStatement_Expression(), this.getExpression(), null, "expression", null, 0, 1, ReturnStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(withStatementEClass, WithStatement.class, "WithStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWithStatement_Expression(), this.getExpression(), null, "expression", null, 0, 1, WithStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWithStatement_Statement(), this.getStatement(), null, "statement", null, 0, 1, WithStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(switchStatementEClass, SwitchStatement.class, "SwitchStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSwitchStatement_Expression(), this.getExpression(), null, "expression", null, 0, 1, SwitchStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSwitchStatement_Cases(), this.getAbstractCaseClause(), null, "cases", null, 0, -1, SwitchStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getSwitchStatement__AppliesOnlyToBlockScopedElements(), theEcorePackage.getEBoolean(), "appliesOnlyToBlockScopedElements", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSwitchStatement__GetDefaultClause(), this.getDefaultClause(), "getDefaultClause", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSwitchStatement__GetCaseClauses(), this.getCaseClause(), "getCaseClauses", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(abstractCaseClauseEClass, AbstractCaseClause.class, "AbstractCaseClause", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractCaseClause_Statements(), this.getStatement(), null, "statements", null, 0, -1, AbstractCaseClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(caseClauseEClass, CaseClause.class, "CaseClause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCaseClause_Expression(), this.getExpression(), null, "expression", null, 0, 1, CaseClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(defaultClauseEClass, DefaultClause.class, "DefaultClause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(labelledStatementEClass, LabelledStatement.class, "LabelledStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLabelledStatement_Name(), theEcorePackage.getEString(), "name", null, 0, 1, LabelledStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLabelledStatement_Statement(), this.getStatement(), null, "statement", null, 0, 1, LabelledStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(throwStatementEClass, ThrowStatement.class, "ThrowStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getThrowStatement_Expression(), this.getExpression(), null, "expression", null, 0, 1, ThrowStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tryStatementEClass, TryStatement.class, "TryStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTryStatement_Block(), this.getBlock(), null, "block", null, 0, 1, TryStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTryStatement_Catch(), this.getCatchBlock(), null, "catch", null, 0, 1, TryStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTryStatement_Finally(), this.getFinallyBlock(), null, "finally", null, 0, 1, TryStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractCatchBlockEClass, AbstractCatchBlock.class, "AbstractCatchBlock", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractCatchBlock_Block(), this.getBlock(), null, "block", null, 0, 1, AbstractCatchBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(catchBlockEClass, CatchBlock.class, "CatchBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCatchBlock_CatchVariable(), this.getCatchVariable(), null, "catchVariable", null, 0, 1, CatchBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(catchVariableEClass, CatchVariable.class, "CatchVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCatchVariable_BindingPattern(), this.getBindingPattern(), null, "bindingPattern", null, 0, 1, CatchVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(finallyBlockEClass, FinallyBlock.class, "FinallyBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(debuggerStatementEClass, DebuggerStatement.class, "DebuggerStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(primaryExpressionEClass, PrimaryExpression.class, "PrimaryExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(parenExpressionEClass, ParenExpression.class, "ParenExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParenExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, ParenExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getParenExpression__IsValidSimpleAssignmentTarget(), theEcorePackage.getEBoolean(), "isValidSimpleAssignmentTarget", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(identifierRefEClass, IdentifierRef.class, "IdentifierRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIdentifierRef_Id(), theTypesPackage.getIdentifiableElement(), null, "id", null, 0, 1, IdentifierRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentifierRef_IdAsText(), theEcorePackage.getEString(), "idAsText", null, 0, 1, IdentifierRef.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIdentifierRef_OriginImport(), this.getImportSpecifier(), null, "originImport", null, 0, 1, IdentifierRef.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getIdentifierRef__GetTargetElement(), theTypesPackage.getIdentifiableElement(), "getTargetElement", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getIdentifierRef__IsValidSimpleAssignmentTarget(), theEcorePackage.getEBoolean(), "isValidSimpleAssignmentTarget", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(strictModeRelevantEClass, StrictModeRelevant.class, "StrictModeRelevant", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStrictModeRelevant_StrictMode(), theEcorePackage.getEBoolean(), "strictMode", null, 0, 1, StrictModeRelevant.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(superLiteralEClass, SuperLiteral.class, "SuperLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getSuperLiteral__IsSuperConstructorAccess(), theEcorePackage.getEBoolean(), "isSuperConstructorAccess", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSuperLiteral__IsSuperMemberAccess(), theEcorePackage.getEBoolean(), "isSuperMemberAccess", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(thisLiteralEClass, ThisLiteral.class, "ThisLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(arrayLiteralEClass, ArrayLiteral.class, "ArrayLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArrayLiteral_Elements(), this.getArrayElement(), null, "elements", null, 0, -1, ArrayLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArrayLiteral_TrailingComma(), theEcorePackage.getEBoolean(), "trailingComma", null, 0, 1, ArrayLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arrayElementEClass, ArrayElement.class, "ArrayElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArrayElement_Spread(), theEcorePackage.getEBoolean(), "spread", null, 0, 1, ArrayElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArrayElement_Expression(), this.getExpression(), null, "expression", null, 0, 1, ArrayElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arrayPaddingEClass, ArrayPadding.class, "ArrayPadding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(objectLiteralEClass, ObjectLiteral.class, "ObjectLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getObjectLiteral_PropertyAssignments(), this.getPropertyAssignment(), null, "propertyAssignments", null, 0, -1, ObjectLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyAssignmentEClass, PropertyAssignment.class, "PropertyAssignment", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getPropertyAssignment__GetDefinedMember(), theTypesPackage.getTStructMember(), "getDefinedMember", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getPropertyAssignment__IsValidName(), theEcorePackage.getEBoolean(), "isValidName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(propertyNameOwnerEClass, PropertyNameOwner.class, "PropertyNameOwner", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertyNameOwner_DeclaredName(), this.getLiteralOrComputedPropertyName(), null, "declaredName", null, 0, 1, PropertyNameOwner.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getPropertyNameOwner__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getPropertyNameOwner__HasComputedPropertyName(), theEcorePackage.getEBoolean(), "hasComputedPropertyName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getPropertyNameOwner__IsValidName(), theEcorePackage.getEBoolean(), "isValidName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(literalOrComputedPropertyNameEClass, LiteralOrComputedPropertyName.class, "LiteralOrComputedPropertyName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLiteralOrComputedPropertyName_Kind(), this.getPropertyNameKind(), "kind", null, 0, 1, LiteralOrComputedPropertyName.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLiteralOrComputedPropertyName_LiteralName(), theEcorePackage.getEString(), "literalName", null, 0, 1, LiteralOrComputedPropertyName.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLiteralOrComputedPropertyName_ComputedName(), theEcorePackage.getEString(), "computedName", null, 0, 1, LiteralOrComputedPropertyName.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLiteralOrComputedPropertyName_Expression(), this.getExpression(), null, "expression", null, 0, 1, LiteralOrComputedPropertyName.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getLiteralOrComputedPropertyName__HasComputedPropertyName(), theEcorePackage.getEBoolean(), "hasComputedPropertyName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getLiteralOrComputedPropertyName__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(annotablePropertyAssignmentEClass, AnnotablePropertyAssignment.class, "AnnotablePropertyAssignment", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAnnotablePropertyAssignment_AnnotationList(), this.getPropertyAssignmentAnnotationList(), null, "annotationList", null, 0, 1, AnnotablePropertyAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getAnnotablePropertyAssignment__GetAnnotations(), this.getAnnotation(), "getAnnotations", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(propertyAssignmentAnnotationListEClass, PropertyAssignmentAnnotationList.class, "PropertyAssignmentAnnotationList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getPropertyAssignmentAnnotationList__GetDefinedMember(), theTypesPackage.getTStructMember(), "getDefinedMember", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(propertyNameValuePairEClass, PropertyNameValuePair.class, "PropertyNameValuePair", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertyNameValuePair_DefinedField(), theTypesPackage.getTStructField(), null, "definedField", null, 0, 1, PropertyNameValuePair.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyNameValuePair_DeclaredOptional(), theEcorePackage.getEBoolean(), "declaredOptional", null, 0, 1, PropertyNameValuePair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyNameValuePair_Expression(), this.getExpression(), null, "expression", null, 0, 1, PropertyNameValuePair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getPropertyNameValuePair__GetDefinedMember(), theTypesPackage.getTStructField(), "getDefinedMember", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getPropertyNameValuePair__IsValidName(), theEcorePackage.getEBoolean(), "isValidName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(propertyNameValuePairSingleNameEClass, PropertyNameValuePairSingleName.class, "PropertyNameValuePairSingleName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getPropertyNameValuePairSingleName__GetIdentifierRef(), this.getIdentifierRef(), "getIdentifierRef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getPropertyNameValuePairSingleName__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(propertyMethodDeclarationEClass, PropertyMethodDeclaration.class, "PropertyMethodDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getPropertyMethodDeclaration__GetDefinedMember(), theTypesPackage.getTStructMethod(), "getDefinedMember", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(getterDeclarationEClass, GetterDeclaration.class, "GetterDeclaration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGetterDeclaration_DefinedGetter(), theTypesPackage.getTGetter(), null, "definedGetter", null, 0, 1, GetterDeclaration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getGetterDeclaration__GetDefinedAccessor(), theTypesPackage.getTGetter(), "getDefinedAccessor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(setterDeclarationEClass, SetterDeclaration.class, "SetterDeclaration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSetterDeclaration_DefinedSetter(), theTypesPackage.getTSetter(), null, "definedSetter", null, 0, 1, SetterDeclaration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSetterDeclaration_Fpar(), this.getFormalParameter(), null, "fpar", null, 0, 1, SetterDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getSetterDeclaration__GetDefinedAccessor(), theTypesPackage.getTSetter(), "getDefinedAccessor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSetterDeclaration__GetDeclaredTypeRef(), theTypeRefsPackage.getTypeRef(), "getDeclaredTypeRef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(propertyGetterDeclarationEClass, PropertyGetterDeclaration.class, "PropertyGetterDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getPropertyGetterDeclaration__GetDefinedGetter(), theTypesPackage.getTStructGetter(), "getDefinedGetter", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getPropertyGetterDeclaration__GetDefinedMember(), theTypesPackage.getTStructGetter(), "getDefinedMember", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getPropertyGetterDeclaration__IsValidName(), theEcorePackage.getEBoolean(), "isValidName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(propertySetterDeclarationEClass, PropertySetterDeclaration.class, "PropertySetterDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getPropertySetterDeclaration__GetDefinedSetter(), theTypesPackage.getTStructSetter(), "getDefinedSetter", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getPropertySetterDeclaration__GetDefinedMember(), theTypesPackage.getTStructSetter(), "getDefinedMember", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getPropertySetterDeclaration__IsValidName(), theEcorePackage.getEBoolean(), "isValidName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(propertySpreadEClass, PropertySpread.class, "PropertySpread", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertySpread_Expression(), this.getExpression(), null, "expression", null, 0, 1, PropertySpread.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getPropertySpread__GetDefinedMember(), theTypesPackage.getTStructField(), "getDefinedMember", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(expressionEClass, Expression.class, "Expression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getExpression__IsValidSimpleAssignmentTarget(), theEcorePackage.getEBoolean(), "isValidSimpleAssignmentTarget", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(newTargetEClass, NewTarget.class, "NewTarget", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(newExpressionEClass, NewExpression.class, "NewExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNewExpression_Callee(), this.getExpression(), null, "callee", null, 0, 1, NewExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNewExpression_Arguments(), this.getArgument(), null, "arguments", null, 0, -1, NewExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNewExpression_WithArgs(), theEcorePackage.getEBoolean(), "withArgs", null, 0, 1, NewExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterizedAccessEClass, ParameterizedAccess.class, "ParameterizedAccess", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterizedAccess_TypeArgs(), theTypeRefsPackage.getTypeRef(), null, "typeArgs", null, 0, -1, ParameterizedAccess.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getParameterizedAccess__IsParameterized(), theEcorePackage.getEBoolean(), "isParameterized", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(expressionWithTargetEClass, ExpressionWithTarget.class, "ExpressionWithTarget", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExpressionWithTarget_Target(), this.getExpression(), null, "target", null, 0, 1, ExpressionWithTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExpressionWithTarget_OptionalChaining(), theEcorePackage.getEBoolean(), "optionalChaining", null, 0, 1, ExpressionWithTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getExpressionWithTarget__IsOrHasTargetWithOptionalChaining(), theEcorePackage.getEBoolean(), "isOrHasTargetWithOptionalChaining", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(parameterizedCallExpressionEClass, ParameterizedCallExpression.class, "ParameterizedCallExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterizedCallExpression_Arguments(), this.getArgument(), null, "arguments", null, 0, -1, ParameterizedCallExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getParameterizedCallExpression__GetReceiver(), this.getExpression(), "getReceiver", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(importCallExpressionEClass, ImportCallExpression.class, "ImportCallExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getImportCallExpression_Arguments(), this.getArgument(), null, "arguments", null, 0, -1, ImportCallExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getImportCallExpression__GetArgument(), this.getArgument(), "getArgument", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(argumentEClass, Argument.class, "Argument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArgument_Spread(), theEcorePackage.getEBoolean(), "spread", null, 0, 1, Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArgument_Expression(), this.getExpression(), null, "expression", null, 0, 1, Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(indexedAccessExpressionEClass, IndexedAccessExpression.class, "IndexedAccessExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIndexedAccessExpression_Index(), this.getExpression(), null, "index", null, 0, 1, IndexedAccessExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getIndexedAccessExpression__IsValidSimpleAssignmentTarget(), theEcorePackage.getEBoolean(), "isValidSimpleAssignmentTarget", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(taggedTemplateStringEClass, TaggedTemplateString.class, "TaggedTemplateString", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTaggedTemplateString_Template(), this.getTemplateLiteral(), null, "template", null, 0, 1, TaggedTemplateString.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(memberAccessEClass, MemberAccess.class, "MemberAccess", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMemberAccess_ComposedMemberCache(), theTypesPackage.getComposedMemberCache(), null, "composedMemberCache", null, 0, 1, MemberAccess.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterizedPropertyAccessExpressionEClass, ParameterizedPropertyAccessExpression.class, "ParameterizedPropertyAccessExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterizedPropertyAccessExpression_Property(), theTypesPackage.getIdentifiableElement(), null, "property", null, 0, 1, ParameterizedPropertyAccessExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameterizedPropertyAccessExpression_PropertyAsText(), theEcorePackage.getEString(), "propertyAsText", null, 0, 1, ParameterizedPropertyAccessExpression.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getParameterizedPropertyAccessExpression__IsValidSimpleAssignmentTarget(), theEcorePackage.getEBoolean(), "isValidSimpleAssignmentTarget", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(awaitExpressionEClass, AwaitExpression.class, "AwaitExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAwaitExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, AwaitExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(promisifyExpressionEClass, PromisifyExpression.class, "PromisifyExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPromisifyExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, PromisifyExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(yieldExpressionEClass, YieldExpression.class, "YieldExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getYieldExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, YieldExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getYieldExpression_Many(), theEcorePackage.getEBoolean(), "many", null, 0, 1, YieldExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(literalEClass, Literal.class, "Literal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getLiteral__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(nullLiteralEClass, NullLiteral.class, "NullLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getNullLiteral__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(booleanLiteralEClass, BooleanLiteral.class, "BooleanLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanLiteral_True(), theEcorePackage.getEBoolean(), "true", null, 0, 1, BooleanLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getBooleanLiteral__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(stringLiteralEClass, StringLiteral.class, "StringLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringLiteral_Value(), theEcorePackage.getEString(), "value", null, 0, 1, StringLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringLiteral_RawValue(), theEcorePackage.getEString(), "rawValue", null, 0, 1, StringLiteral.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getStringLiteral__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(templateLiteralEClass, TemplateLiteral.class, "TemplateLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemplateLiteral_Segments(), this.getExpression(), null, "segments", null, 0, -1, TemplateLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTemplateLiteral__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(templateSegmentEClass, TemplateSegment.class, "TemplateSegment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTemplateSegment_Value(), theEcorePackage.getEString(), "value", null, 0, 1, TemplateSegment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplateSegment_RawValue(), theEcorePackage.getEString(), "rawValue", null, 0, 1, TemplateSegment.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getTemplateSegment__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(numericLiteralEClass, NumericLiteral.class, "NumericLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNumericLiteral_Value(), theEcorePackage.getEBigDecimal(), "value", null, 0, 1, NumericLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getNumericLiteral__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(doubleLiteralEClass, DoubleLiteral.class, "DoubleLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getDoubleLiteral__ToDouble(), theEcorePackage.getEDouble(), "toDouble", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getDoubleLiteral__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(abstractIntLiteralEClass, AbstractIntLiteral.class, "AbstractIntLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getAbstractIntLiteral__ToInt(), theEcorePackage.getEInt(), "toInt", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getAbstractIntLiteral__ToLong(), theEcorePackage.getELong(), "toLong", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getAbstractIntLiteral__ToBigInteger(), theEcorePackage.getEBigInteger(), "toBigInteger", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(intLiteralEClass, IntLiteral.class, "IntLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(binaryIntLiteralEClass, BinaryIntLiteral.class, "BinaryIntLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(octalIntLiteralEClass, OctalIntLiteral.class, "OctalIntLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(legacyOctalIntLiteralEClass, LegacyOctalIntLiteral.class, "LegacyOctalIntLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(hexIntLiteralEClass, HexIntLiteral.class, "HexIntLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(scientificIntLiteralEClass, ScientificIntLiteral.class, "ScientificIntLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(regularExpressionLiteralEClass, RegularExpressionLiteral.class, "RegularExpressionLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRegularExpressionLiteral_Value(), theEcorePackage.getEString(), "value", null, 0, 1, RegularExpressionLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getRegularExpressionLiteral__GetValueAsString(), theEcorePackage.getEString(), "getValueAsString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(postfixExpressionEClass, PostfixExpression.class, "PostfixExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPostfixExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, PostfixExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPostfixExpression_Op(), this.getPostfixOperator(), "op", null, 0, 1, PostfixExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unaryExpressionEClass, UnaryExpression.class, "UnaryExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUnaryExpression_Op(), this.getUnaryOperator(), "op", null, 0, 1, UnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUnaryExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, UnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(castExpressionEClass, CastExpression.class, "CastExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCastExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, CastExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCastExpression_TargetTypeRef(), theTypeRefsPackage.getTypeRef(), null, "targetTypeRef", null, 0, 1, CastExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multiplicativeExpressionEClass, MultiplicativeExpression.class, "MultiplicativeExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMultiplicativeExpression_Lhs(), this.getExpression(), null, "lhs", null, 0, 1, MultiplicativeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMultiplicativeExpression_Op(), this.getMultiplicativeOperator(), "op", null, 0, 1, MultiplicativeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMultiplicativeExpression_Rhs(), this.getExpression(), null, "rhs", null, 0, 1, MultiplicativeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(additiveExpressionEClass, AdditiveExpression.class, "AdditiveExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAdditiveExpression_Lhs(), this.getExpression(), null, "lhs", null, 0, 1, AdditiveExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdditiveExpression_Op(), this.getAdditiveOperator(), "op", null, 0, 1, AdditiveExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAdditiveExpression_Rhs(), this.getExpression(), null, "rhs", null, 0, 1, AdditiveExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(shiftExpressionEClass, ShiftExpression.class, "ShiftExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getShiftExpression_Lhs(), this.getExpression(), null, "lhs", null, 0, 1, ShiftExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getShiftExpression_Op(), this.getShiftOperator(), "op", null, 0, 1, ShiftExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getShiftExpression_Rhs(), this.getExpression(), null, "rhs", null, 0, 1, ShiftExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(relationalExpressionEClass, RelationalExpression.class, "RelationalExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRelationalExpression_Lhs(), this.getExpression(), null, "lhs", null, 0, 1, RelationalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelationalExpression_Op(), this.getRelationalOperator(), "op", null, 0, 1, RelationalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationalExpression_Rhs(), this.getExpression(), null, "rhs", null, 0, 1, RelationalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(equalityExpressionEClass, EqualityExpression.class, "EqualityExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEqualityExpression_Lhs(), this.getExpression(), null, "lhs", null, 0, 1, EqualityExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEqualityExpression_Op(), this.getEqualityOperator(), "op", null, 0, 1, EqualityExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEqualityExpression_Rhs(), this.getExpression(), null, "rhs", null, 0, 1, EqualityExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(binaryBitwiseExpressionEClass, BinaryBitwiseExpression.class, "BinaryBitwiseExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBinaryBitwiseExpression_Lhs(), this.getExpression(), null, "lhs", null, 0, 1, BinaryBitwiseExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryBitwiseExpression_Op(), this.getBinaryBitwiseOperator(), "op", null, 0, 1, BinaryBitwiseExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryBitwiseExpression_Rhs(), this.getExpression(), null, "rhs", null, 0, 1, BinaryBitwiseExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(binaryLogicalExpressionEClass, BinaryLogicalExpression.class, "BinaryLogicalExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBinaryLogicalExpression_Lhs(), this.getExpression(), null, "lhs", null, 0, 1, BinaryLogicalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryLogicalExpression_Op(), this.getBinaryLogicalOperator(), "op", null, 0, 1, BinaryLogicalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryLogicalExpression_Rhs(), this.getExpression(), null, "rhs", null, 0, 1, BinaryLogicalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(coalesceExpressionEClass, CoalesceExpression.class, "CoalesceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCoalesceExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, CoalesceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCoalesceExpression_DefaultExpression(), this.getExpression(), null, "defaultExpression", null, 0, 1, CoalesceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalExpressionEClass, ConditionalExpression.class, "ConditionalExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConditionalExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, ConditionalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConditionalExpression_TrueExpression(), this.getExpression(), null, "trueExpression", null, 0, 1, ConditionalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConditionalExpression_FalseExpression(), this.getExpression(), null, "falseExpression", null, 0, 1, ConditionalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(assignmentExpressionEClass, AssignmentExpression.class, "AssignmentExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssignmentExpression_Lhs(), this.getExpression(), null, "lhs", null, 0, 1, AssignmentExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssignmentExpression_Op(), this.getAssignmentOperator(), "op", null, 0, 1, AssignmentExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAssignmentExpression_Rhs(), this.getExpression(), null, "rhs", null, 0, 1, AssignmentExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commaExpressionEClass, CommaExpression.class, "CommaExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCommaExpression_Exprs(), this.getExpression(), null, "exprs", null, 0, -1, CommaExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeDefiningElementEClass, TypeDefiningElement.class, "TypeDefiningElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeDefiningElement_DefinedType(), theTypesPackage.getType(), null, "definedType", null, 0, 1, TypeDefiningElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericDeclarationEClass, GenericDeclaration.class, "GenericDeclaration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGenericDeclaration_TypeVars(), theTypesPackage.getTypeVariable(), null, "typeVars", null, 0, -1, GenericDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(n4TypeDefinitionEClass, N4TypeDefinition.class, "N4TypeDefinition", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getN4TypeDefinition__IsExternal(), theEcorePackage.getEBoolean(), "isExternal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4TypeDeclarationEClass, N4TypeDeclaration.class, "N4TypeDeclaration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getN4TypeDeclaration_Name(), theEcorePackage.getEString(), "name", null, 0, 1, N4TypeDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getN4TypeDeclaration__IsExternal(), theEcorePackage.getEBoolean(), "isExternal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4ClassifierDeclarationEClass, N4ClassifierDeclaration.class, "N4ClassifierDeclaration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getN4ClassifierDeclaration_TypingStrategy(), theTypesPackage.getTypingStrategy(), "typingStrategy", null, 0, 1, N4ClassifierDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(n4ClassifierDefinitionEClass, N4ClassifierDefinition.class, "N4ClassifierDefinition", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getN4ClassifierDefinition_OwnedMembersRaw(), this.getN4MemberDeclaration(), this.getN4MemberDeclaration_Owner(), "ownedMembersRaw", null, 0, -1, N4ClassifierDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getN4ClassifierDefinition__GetOwnedMembers(), this.getN4MemberDeclaration(), "getOwnedMembers", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassifierDefinition__GetOwnedCtor(), this.getN4MethodDeclaration(), "getOwnedCtor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassifierDefinition__GetOwnedCallableCtor(), this.getN4MethodDeclaration(), "getOwnedCallableCtor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassifierDefinition__GetOwnedMethods(), this.getN4MethodDeclaration(), "getOwnedMethods", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassifierDefinition__GetOwnedFields(), this.getN4FieldDeclaration(), "getOwnedFields", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassifierDefinition__GetOwnedGetters(), this.getN4GetterDeclaration(), "getOwnedGetters", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassifierDefinition__GetOwnedSetters(), this.getN4SetterDeclaration(), "getOwnedSetters", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassifierDefinition__GetSuperClassifierRefs(), theTypesPackage.getParameterizedTypeRefIterable(), "getSuperClassifierRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassifierDefinition__GetImplementedOrExtendedInterfaceRefs(), theTypesPackage.getParameterizedTypeRefIterable(), "getImplementedOrExtendedInterfaceRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4ClassDefinitionEClass, N4ClassDefinition.class, "N4ClassDefinition", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getN4ClassDefinition_SuperClassRef(), theTypeRefsPackage.getParameterizedTypeRef(), null, "superClassRef", null, 0, 1, N4ClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getN4ClassDefinition_SuperClassExpression(), this.getExpression(), null, "superClassExpression", null, 0, 1, N4ClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getN4ClassDefinition_ImplementedInterfaceRefs(), theTypeRefsPackage.getParameterizedTypeRef(), null, "implementedInterfaceRefs", null, 0, -1, N4ClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getN4ClassDefinition__GetDefinedTypeAsClass(), theTypesPackage.getTClass(), "getDefinedTypeAsClass", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassDefinition__GetSuperClassifierRefs(), theTypesPackage.getParameterizedTypeRefIterable(), "getSuperClassifierRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassDefinition__GetImplementedOrExtendedInterfaceRefs(), theTypesPackage.getParameterizedTypeRefIterable(), "getImplementedOrExtendedInterfaceRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4ClassDeclarationEClass, N4ClassDeclaration.class, "N4ClassDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getN4ClassDeclaration__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4ClassDeclaration__GetVersion(), theEcorePackage.getEInt(), "getVersion", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4ClassExpressionEClass, N4ClassExpression.class, "N4ClassExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getN4ClassExpression_Name(), theEcorePackage.getEString(), "name", null, 0, 1, N4ClassExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(n4InterfaceDeclarationEClass, N4InterfaceDeclaration.class, "N4InterfaceDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getN4InterfaceDeclaration_SuperInterfaceRefs(), theTypeRefsPackage.getParameterizedTypeRef(), null, "superInterfaceRefs", null, 0, -1, N4InterfaceDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getN4InterfaceDeclaration__GetDefinedTypeAsInterface(), theTypesPackage.getTInterface(), "getDefinedTypeAsInterface", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4InterfaceDeclaration__GetSuperClassifierRefs(), theTypesPackage.getParameterizedTypeRefIterable(), "getSuperClassifierRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4InterfaceDeclaration__GetImplementedOrExtendedInterfaceRefs(), theTypesPackage.getParameterizedTypeRefIterable(), "getImplementedOrExtendedInterfaceRefs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4InterfaceDeclaration__GetVersion(), theEcorePackage.getEInt(), "getVersion", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4EnumDeclarationEClass, N4EnumDeclaration.class, "N4EnumDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getN4EnumDeclaration_Literals(), this.getN4EnumLiteral(), null, "literals", null, 0, -1, N4EnumDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getN4EnumDeclaration__GetDefinedTypeAsEnum(), theTypesPackage.getTEnum(), "getDefinedTypeAsEnum", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4EnumDeclaration__GetVersion(), theEcorePackage.getEInt(), "getVersion", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4EnumLiteralEClass, N4EnumLiteral.class, "N4EnumLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getN4EnumLiteral_Name(), theEcorePackage.getEString(), "name", null, 0, 1, N4EnumLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getN4EnumLiteral_ValueExpression(), this.getExpression(), null, "valueExpression", null, 0, 1, N4EnumLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getN4EnumLiteral_DefinedLiteral(), theTypesPackage.getTEnumLiteral(), null, "definedLiteral", null, 0, 1, N4EnumLiteral.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(n4TypeAliasDeclarationEClass, N4TypeAliasDeclaration.class, "N4TypeAliasDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getN4TypeAliasDeclaration__GetDefinedTypeAsTypeAlias(), theTypesPackage.getTypeAlias(), "getDefinedTypeAsTypeAlias", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(modifiableElementEClass, ModifiableElement.class, "ModifiableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModifiableElement_DeclaredModifiers(), this.getN4Modifier(), "declaredModifiers", null, 0, -1, ModifiableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(n4MemberDeclarationEClass, N4MemberDeclaration.class, "N4MemberDeclaration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getN4MemberDeclaration_Owner(), this.getN4ClassifierDefinition(), this.getN4ClassifierDefinition_OwnedMembersRaw(), "owner", null, 0, 1, N4MemberDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getN4MemberDeclaration__GetDefinedTypeElement(), theTypesPackage.getTMember(), "getDefinedTypeElement", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberDeclaration__IsDeclaredAbstract(), theEcorePackage.getEBoolean(), "isDeclaredAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberDeclaration__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberDeclaration__IsDeclaredStatic(), theEcorePackage.getEBoolean(), "isDeclaredStatic", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberDeclaration__IsStatic(), theEcorePackage.getEBoolean(), "isStatic", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberDeclaration__IsDeclaredFinal(), theEcorePackage.getEBoolean(), "isDeclaredFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberDeclaration__IsFinal(), theEcorePackage.getEBoolean(), "isFinal", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberDeclaration__IsConstructor(), theEcorePackage.getEBoolean(), "isConstructor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberDeclaration__IsCallableConstructor(), theEcorePackage.getEBoolean(), "isCallableConstructor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(annotableN4MemberDeclarationEClass, AnnotableN4MemberDeclaration.class, "AnnotableN4MemberDeclaration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAnnotableN4MemberDeclaration_AnnotationList(), this.getN4MemberAnnotationList(), null, "annotationList", null, 0, 1, AnnotableN4MemberDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getAnnotableN4MemberDeclaration__GetAnnotations(), this.getAnnotation(), "getAnnotations", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4MemberAnnotationListEClass, N4MemberAnnotationList.class, "N4MemberAnnotationList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getN4MemberAnnotationList__GetDefinedTypeElement(), theTypesPackage.getTMember(), "getDefinedTypeElement", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberAnnotationList__GetDeclaredTypeRef(), theTypeRefsPackage.getTypeRef(), "getDeclaredTypeRef", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MemberAnnotationList__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4FieldDeclarationEClass, N4FieldDeclaration.class, "N4FieldDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getN4FieldDeclaration_DefinedField(), theTypesPackage.getTField(), null, "definedField", null, 0, 1, N4FieldDeclaration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getN4FieldDeclaration_DeclaredOptional(), theEcorePackage.getEBoolean(), "declaredOptional", null, 0, 1, N4FieldDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getN4FieldDeclaration_Expression(), this.getExpression(), null, "expression", null, 0, 1, N4FieldDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getN4FieldDeclaration__GetDefinedTypeElement(), theTypesPackage.getTMember(), "getDefinedTypeElement", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4FieldDeclaration__IsConst(), theEcorePackage.getEBoolean(), "isConst", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4FieldDeclaration__IsStatic(), theEcorePackage.getEBoolean(), "isStatic", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4FieldDeclaration__IsValid(), theEcorePackage.getEBoolean(), "isValid", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4FieldDeclaration__IsValidName(), theEcorePackage.getEBoolean(), "isValidName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(methodDeclarationEClass, MethodDeclaration.class, "MethodDeclaration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getMethodDeclaration__ExistsExplicitSuperCall(), theEcorePackage.getEBoolean(), "existsExplicitSuperCall", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getMethodDeclaration__GetDefinedTypeElement(), theTypesPackage.getTMember(), "getDefinedTypeElement", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getMethodDeclaration__IsStatic(), theEcorePackage.getEBoolean(), "isStatic", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4MethodDeclarationEClass, N4MethodDeclaration.class, "N4MethodDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getN4MethodDeclaration__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MethodDeclaration__IsConstructor(), theEcorePackage.getEBoolean(), "isConstructor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MethodDeclaration__IsCallableConstructor(), theEcorePackage.getEBoolean(), "isCallableConstructor", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MethodDeclaration__IsStatic(), theEcorePackage.getEBoolean(), "isStatic", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4MethodDeclaration__IsValidName(), theEcorePackage.getEBoolean(), "isValidName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4FieldAccessorEClass, N4FieldAccessor.class, "N4FieldAccessor", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getN4FieldAccessor__IsAbstract(), theEcorePackage.getEBoolean(), "isAbstract", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getN4FieldAccessor__IsValidName(), theEcorePackage.getEBoolean(), "isValidName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4GetterDeclarationEClass, N4GetterDeclaration.class, "N4GetterDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getN4GetterDeclaration__GetDefinedTypeElement(), theTypesPackage.getTMember(), "getDefinedTypeElement", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(n4SetterDeclarationEClass, N4SetterDeclaration.class, "N4SetterDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getN4SetterDeclaration__GetDefinedTypeElement(), theTypesPackage.getTMember(), "getDefinedTypeElement", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(bindingPatternEClass, BindingPattern.class, "BindingPattern", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(objectBindingPatternEClass, ObjectBindingPattern.class, "ObjectBindingPattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getObjectBindingPattern_Properties(), this.getBindingProperty(), null, "properties", null, 0, -1, ObjectBindingPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arrayBindingPatternEClass, ArrayBindingPattern.class, "ArrayBindingPattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArrayBindingPattern_Elements(), this.getBindingElement(), null, "elements", null, 0, -1, ArrayBindingPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bindingPropertyEClass, BindingProperty.class, "BindingProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBindingProperty_Value(), this.getBindingElement(), null, "value", null, 0, 1, BindingProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getBindingProperty__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getBindingProperty__IsValidName(), theEcorePackage.getEBoolean(), "isValidName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(bindingElementEClass, BindingElement.class, "BindingElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBindingElement_Rest(), theEcorePackage.getEBoolean(), "rest", null, 0, 1, BindingElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBindingElement_VarDecl(), this.getVariableDeclaration(), null, "varDecl", null, 0, 1, BindingElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBindingElement_NestedPattern(), this.getBindingPattern(), null, "nestedPattern", null, 0, 1, BindingElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBindingElement_Expression(), this.getExpression(), null, "expression", null, 0, 1, BindingElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getBindingElement__IsElision(), theEcorePackage.getEBoolean(), "isElision", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jsxChildEClass, JSXChild.class, "JSXChild", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jsxElementNameEClass, JSXElementName.class, "JSXElementName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXElementName_Expression(), this.getExpression(), null, "expression", null, 0, 1, JSXElementName.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxTextEClass, JSXText.class, "JSXText", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jsxExpressionEClass, JSXExpression.class, "JSXExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXExpression_Expression(), this.getExpression(), null, "expression", null, 0, 1, JSXExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxAttributeEClass, JSXAttribute.class, "JSXAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jsxPropertyAttributeEClass, JSXPropertyAttribute.class, "JSXPropertyAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXPropertyAttribute_Property(), theTypesPackage.getIdentifiableElement(), null, "property", null, 0, 1, JSXPropertyAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJSXPropertyAttribute_PropertyAsText(), theEcorePackage.getEString(), "propertyAsText", null, 0, 1, JSXPropertyAttribute.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJSXPropertyAttribute_JsxAttributeValue(), this.getExpression(), null, "jsxAttributeValue", null, 0, 1, JSXPropertyAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxSpreadAttributeEClass, JSXSpreadAttribute.class, "JSXSpreadAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXSpreadAttribute_Expression(), this.getExpression(), null, "expression", null, 0, 1, JSXSpreadAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxAbstractElementEClass, JSXAbstractElement.class, "JSXAbstractElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXAbstractElement_JsxChildren(), this.getJSXChild(), null, "jsxChildren", null, 0, -1, JSXAbstractElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxElementEClass, JSXElement.class, "JSXElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXElement_JsxElementName(), this.getJSXElementName(), null, "jsxElementName", null, 0, 1, JSXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJSXElement_JsxAttributes(), this.getJSXAttribute(), null, "jsxAttributes", null, 0, -1, JSXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJSXElement_JsxClosingName(), this.getJSXElementName(), null, "jsxClosingName", null, 0, 1, JSXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxFragmentEClass, JSXFragment.class, "JSXFragment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(versionedElementEClass, VersionedElement.class, "VersionedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersionedElement_DeclaredVersion(), theEcorePackage.getEBigDecimal(), "declaredVersion", null, 0, 1, VersionedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getVersionedElement__HasDeclaredVersion(), theEcorePackage.getEBoolean(), "hasDeclaredVersion", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getVersionedElement__GetDeclaredVersionOrZero(), theEcorePackage.getEInt(), "getDeclaredVersionOrZero", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(versionedIdentifierRefEClass, VersionedIdentifierRef.class, "VersionedIdentifierRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getVersionedIdentifierRef__GetVersion(), theEcorePackage.getEInt(), "getVersion", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(migrationContextVariableEClass, MigrationContextVariable.class, "MigrationContextVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getMigrationContextVariable__GetName(), theEcorePackage.getEString(), "getName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(moduleSpecifierFormEEnum, ModuleSpecifierForm.class, "ModuleSpecifierForm");
		addEEnumLiteral(moduleSpecifierFormEEnum, ModuleSpecifierForm.UNKNOWN);
		addEEnumLiteral(moduleSpecifierFormEEnum, ModuleSpecifierForm.PLAIN);
		addEEnumLiteral(moduleSpecifierFormEEnum, ModuleSpecifierForm.COMPLETE);
		addEEnumLiteral(moduleSpecifierFormEEnum, ModuleSpecifierForm.PROJECT);
		addEEnumLiteral(moduleSpecifierFormEEnum, ModuleSpecifierForm.PROJECT_NO_MAIN);

		initEEnum(variableStatementKeywordEEnum, VariableStatementKeyword.class, "VariableStatementKeyword");
		addEEnumLiteral(variableStatementKeywordEEnum, VariableStatementKeyword.VAR);
		addEEnumLiteral(variableStatementKeywordEEnum, VariableStatementKeyword.CONST);
		addEEnumLiteral(variableStatementKeywordEEnum, VariableStatementKeyword.LET);

		initEEnum(propertyNameKindEEnum, PropertyNameKind.class, "PropertyNameKind");
		addEEnumLiteral(propertyNameKindEEnum, PropertyNameKind.IDENTIFIER);
		addEEnumLiteral(propertyNameKindEEnum, PropertyNameKind.STRING);
		addEEnumLiteral(propertyNameKindEEnum, PropertyNameKind.NUMBER);
		addEEnumLiteral(propertyNameKindEEnum, PropertyNameKind.COMPUTED);

		initEEnum(postfixOperatorEEnum, PostfixOperator.class, "PostfixOperator");
		addEEnumLiteral(postfixOperatorEEnum, PostfixOperator.INC);
		addEEnumLiteral(postfixOperatorEEnum, PostfixOperator.DEC);

		initEEnum(unaryOperatorEEnum, UnaryOperator.class, "UnaryOperator");
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.DELETE);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.VOID);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.TYPEOF);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.INC);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.DEC);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.POS);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.NEG);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.INV);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.NOT);

		initEEnum(multiplicativeOperatorEEnum, MultiplicativeOperator.class, "MultiplicativeOperator");
		addEEnumLiteral(multiplicativeOperatorEEnum, MultiplicativeOperator.TIMES);
		addEEnumLiteral(multiplicativeOperatorEEnum, MultiplicativeOperator.DIV);
		addEEnumLiteral(multiplicativeOperatorEEnum, MultiplicativeOperator.MOD);

		initEEnum(additiveOperatorEEnum, AdditiveOperator.class, "AdditiveOperator");
		addEEnumLiteral(additiveOperatorEEnum, AdditiveOperator.ADD);
		addEEnumLiteral(additiveOperatorEEnum, AdditiveOperator.SUB);

		initEEnum(relationalOperatorEEnum, RelationalOperator.class, "RelationalOperator");
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.LT);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.GT);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.LTE);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.GTE);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.INSTANCEOF);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.IN);

		initEEnum(equalityOperatorEEnum, EqualityOperator.class, "EqualityOperator");
		addEEnumLiteral(equalityOperatorEEnum, EqualityOperator.SAME);
		addEEnumLiteral(equalityOperatorEEnum, EqualityOperator.NSAME);
		addEEnumLiteral(equalityOperatorEEnum, EqualityOperator.EQ);
		addEEnumLiteral(equalityOperatorEEnum, EqualityOperator.NEQ);

		initEEnum(binaryBitwiseOperatorEEnum, BinaryBitwiseOperator.class, "BinaryBitwiseOperator");
		addEEnumLiteral(binaryBitwiseOperatorEEnum, BinaryBitwiseOperator.AND);
		addEEnumLiteral(binaryBitwiseOperatorEEnum, BinaryBitwiseOperator.OR);
		addEEnumLiteral(binaryBitwiseOperatorEEnum, BinaryBitwiseOperator.XOR);

		initEEnum(binaryLogicalOperatorEEnum, BinaryLogicalOperator.class, "BinaryLogicalOperator");
		addEEnumLiteral(binaryLogicalOperatorEEnum, BinaryLogicalOperator.AND);
		addEEnumLiteral(binaryLogicalOperatorEEnum, BinaryLogicalOperator.OR);

		initEEnum(shiftOperatorEEnum, ShiftOperator.class, "ShiftOperator");
		addEEnumLiteral(shiftOperatorEEnum, ShiftOperator.SHL);
		addEEnumLiteral(shiftOperatorEEnum, ShiftOperator.SHR);
		addEEnumLiteral(shiftOperatorEEnum, ShiftOperator.USHR);

		initEEnum(assignmentOperatorEEnum, AssignmentOperator.class, "AssignmentOperator");
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.MUL_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.DIV_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.MOD_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.ADD_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.SUB_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.SHL_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.SHR_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.USHR_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.AND_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.XOR_ASSIGN);
		addEEnumLiteral(assignmentOperatorEEnum, AssignmentOperator.OR_ASSIGN);

		initEEnum(n4ModifierEEnum, N4Modifier.class, "N4Modifier");
		addEEnumLiteral(n4ModifierEEnum, N4Modifier.UNDEFINED);
		addEEnumLiteral(n4ModifierEEnum, N4Modifier.EXTERNAL);
		addEEnumLiteral(n4ModifierEEnum, N4Modifier.PRIVATE);
		addEEnumLiteral(n4ModifierEEnum, N4Modifier.PROJECT);
		addEEnumLiteral(n4ModifierEEnum, N4Modifier.PROTECTED);
		addEEnumLiteral(n4ModifierEEnum, N4Modifier.PUBLIC);
		addEEnumLiteral(n4ModifierEEnum, N4Modifier.ABSTRACT);
		addEEnumLiteral(n4ModifierEEnum, N4Modifier.STATIC);
		addEEnumLiteral(n4ModifierEEnum, N4Modifier.CONST);

		// Initialize data types
		initEDataType(iteratorOfExpressionEDataType, Iterator.class, "IteratorOfExpression", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.Iterator<org.eclipse.n4js.n4JS.Expression>");
		initEDataType(iteratorOfYieldExpressionEDataType, Iterator.class, "IteratorOfYieldExpression", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.Iterator<org.eclipse.n4js.n4JS.YieldExpression>");
		initEDataType(iteratorOfStatementEDataType, Iterator.class, "IteratorOfStatement", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.Iterator<org.eclipse.n4js.n4JS.Statement>");
		initEDataType(iteratorOfReturnStatementEDataType, Iterator.class, "IteratorOfReturnStatement", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.Iterator<org.eclipse.n4js.n4JS.ReturnStatement>");

		// Create resource
		createResource(eNS_URI);
	}

} //N4JSPackageImpl
