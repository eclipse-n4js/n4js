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
package org.eclipse.n4js.n4jsx.n4JSX;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.n4js.n4JS.N4JSPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * *
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  * Contributors:
 *   NumberFour AG - Initial API and implementation
 * <!-- end-model-doc -->
 * @see org.eclipse.n4js.n4jsx.n4JSX.N4JSXFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel fileExtensions='n4jsx' rootExtendsClass='org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl' modelDirectory='/org.eclipse.n4js.n4jsx.model/emf-gen' forceOverwrite='true' updateClasspath='false' literalsInterface='true' loadInitialization='false' complianceLevel='8.0' copyrightFields='false' copyrightText='Copyright (c) 2016 NumberFour AG.\nAll rights reserved. This program and the accompanying materials\nare made available under the terms of the Eclipse Public License v1.0\nwhich accompanies this distribution, and is available at\nhttp://www.eclipse.org/legal/epl-v10.html\n\nContributors:\n  NumberFour AG - Initial API and implementation' language='' basePackage='org.eclipse.n4js.n4jsx'"
 * @generated
 */
public interface N4JSXPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "n4JSX";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/n4jsx/N4JSX";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "n4JSX";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	N4JSXPackage eINSTANCE = org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXElementImpl <em>JSX Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXElementImpl
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXElement()
	 * @generated
	 */
	int JSX_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Jsx Element Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT__JSX_ELEMENT_NAME = N4JSPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Jsx Closing Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT__JSX_CLOSING_NAME = N4JSPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Jsx Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT__JSX_ATTRIBUTES = N4JSPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Jsx Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT__JSX_CHILDREN = N4JSPackage.EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>JSX Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_FEATURE_COUNT = N4JSPackage.EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = N4JSPackage.EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>JSX Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_OPERATION_COUNT = N4JSPackage.EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXChildImpl <em>JSX Child</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXChildImpl
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXChild()
	 * @generated
	 */
	int JSX_CHILD = 1;

	/**
	 * The number of structural features of the '<em>JSX Child</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_CHILD_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>JSX Child</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_CHILD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXTextImpl <em>JSX Text</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXTextImpl
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXText()
	 * @generated
	 */
	int JSX_TEXT = 2;

	/**
	 * The number of structural features of the '<em>JSX Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_TEXT_FEATURE_COUNT = JSX_CHILD_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>JSX Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_TEXT_OPERATION_COUNT = JSX_CHILD_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXExpressionImpl <em>JSX Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXExpressionImpl
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXExpression()
	 * @generated
	 */
	int JSX_EXPRESSION = 3;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_EXPRESSION__EXPRESSION = JSX_CHILD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JSX Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_EXPRESSION_FEATURE_COUNT = JSX_CHILD_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>JSX Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_EXPRESSION_OPERATION_COUNT = JSX_CHILD_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXElementNameImpl <em>JSX Element Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXElementNameImpl
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXElementName()
	 * @generated
	 */
	int JSX_ELEMENT_NAME = 4;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_NAME__EXPRESSION = 0;

	/**
	 * The number of structural features of the '<em>JSX Element Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_NAME_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>JSX Element Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_NAME_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXAttributeImpl <em>JSX Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXAttributeImpl
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXAttribute()
	 * @generated
	 */
	int JSX_ATTRIBUTE = 5;

	/**
	 * The number of structural features of the '<em>JSX Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ATTRIBUTE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>JSX Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ATTRIBUTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXPropertyAttributeImpl <em>JSX Property Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXPropertyAttributeImpl
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXPropertyAttribute()
	 * @generated
	 */
	int JSX_PROPERTY_ATTRIBUTE = 6;

	/**
	 * The feature id for the '<em><b>Composed Member Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE = JSX_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE__PROPERTY = JSX_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Property As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT = JSX_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Jsx Attribute Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE = JSX_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>JSX Property Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE_FEATURE_COUNT = JSX_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>JSX Property Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE_OPERATION_COUNT = JSX_ATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXSpreadAttributeImpl <em>JSX Spread Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXSpreadAttributeImpl
	 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXSpreadAttribute()
	 * @generated
	 */
	int JSX_SPREAD_ATTRIBUTE = 7;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_SPREAD_ATTRIBUTE__EXPRESSION = JSX_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JSX Spread Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_SPREAD_ATTRIBUTE_FEATURE_COUNT = JSX_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>JSX Spread Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_SPREAD_ATTRIBUTE_OPERATION_COUNT = JSX_ATTRIBUTE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement <em>JSX Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Element</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXElement
	 * @generated
	 */
	EClass getJSXElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxElementName <em>Jsx Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Jsx Element Name</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxElementName()
	 * @see #getJSXElement()
	 * @generated
	 */
	EReference getJSXElement_JsxElementName();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxClosingName <em>Jsx Closing Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Jsx Closing Name</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxClosingName()
	 * @see #getJSXElement()
	 * @generated
	 */
	EReference getJSXElement_JsxClosingName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxAttributes <em>Jsx Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Jsx Attributes</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxAttributes()
	 * @see #getJSXElement()
	 * @generated
	 */
	EReference getJSXElement_JsxAttributes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxChildren <em>Jsx Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Jsx Children</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxChildren()
	 * @see #getJSXElement()
	 * @generated
	 */
	EReference getJSXElement_JsxChildren();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXChild <em>JSX Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Child</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXChild
	 * @generated
	 */
	EClass getJSXChild();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXText <em>JSX Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Text</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXText
	 * @generated
	 */
	EClass getJSXText();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXExpression <em>JSX Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Expression</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXExpression
	 * @generated
	 */
	EClass getJSXExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXExpression#getExpression()
	 * @see #getJSXExpression()
	 * @generated
	 */
	EReference getJSXExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElementName <em>JSX Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Element Name</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXElementName
	 * @generated
	 */
	EClass getJSXElementName();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElementName#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXElementName#getExpression()
	 * @see #getJSXElementName()
	 * @generated
	 */
	EReference getJSXElementName_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXAttribute <em>JSX Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Attribute</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXAttribute
	 * @generated
	 */
	EClass getJSXAttribute();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute <em>JSX Property Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Property Attribute</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute
	 * @generated
	 */
	EClass getJSXPropertyAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute#getProperty()
	 * @see #getJSXPropertyAttribute()
	 * @generated
	 */
	EReference getJSXPropertyAttribute_Property();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute#getPropertyAsText <em>Property As Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property As Text</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute#getPropertyAsText()
	 * @see #getJSXPropertyAttribute()
	 * @generated
	 */
	EAttribute getJSXPropertyAttribute_PropertyAsText();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute#getJsxAttributeValue <em>Jsx Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Jsx Attribute Value</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute#getJsxAttributeValue()
	 * @see #getJSXPropertyAttribute()
	 * @generated
	 */
	EReference getJSXPropertyAttribute_JsxAttributeValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute <em>JSX Spread Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Spread Attribute</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute
	 * @generated
	 */
	EClass getJSXSpreadAttribute();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute#getExpression()
	 * @see #getJSXSpreadAttribute()
	 * @generated
	 */
	EReference getJSXSpreadAttribute_Expression();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	N4JSXFactory getN4JSXFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXElementImpl <em>JSX Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXElementImpl
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXElement()
		 * @generated
		 */
		EClass JSX_ELEMENT = eINSTANCE.getJSXElement();

		/**
		 * The meta object literal for the '<em><b>Jsx Element Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ELEMENT__JSX_ELEMENT_NAME = eINSTANCE.getJSXElement_JsxElementName();

		/**
		 * The meta object literal for the '<em><b>Jsx Closing Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ELEMENT__JSX_CLOSING_NAME = eINSTANCE.getJSXElement_JsxClosingName();

		/**
		 * The meta object literal for the '<em><b>Jsx Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ELEMENT__JSX_ATTRIBUTES = eINSTANCE.getJSXElement_JsxAttributes();

		/**
		 * The meta object literal for the '<em><b>Jsx Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ELEMENT__JSX_CHILDREN = eINSTANCE.getJSXElement_JsxChildren();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXChildImpl <em>JSX Child</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXChildImpl
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXChild()
		 * @generated
		 */
		EClass JSX_CHILD = eINSTANCE.getJSXChild();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXTextImpl <em>JSX Text</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXTextImpl
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXText()
		 * @generated
		 */
		EClass JSX_TEXT = eINSTANCE.getJSXText();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXExpressionImpl <em>JSX Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXExpressionImpl
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXExpression()
		 * @generated
		 */
		EClass JSX_EXPRESSION = eINSTANCE.getJSXExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_EXPRESSION__EXPRESSION = eINSTANCE.getJSXExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXElementNameImpl <em>JSX Element Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXElementNameImpl
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXElementName()
		 * @generated
		 */
		EClass JSX_ELEMENT_NAME = eINSTANCE.getJSXElementName();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ELEMENT_NAME__EXPRESSION = eINSTANCE.getJSXElementName_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXAttributeImpl <em>JSX Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXAttributeImpl
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXAttribute()
		 * @generated
		 */
		EClass JSX_ATTRIBUTE = eINSTANCE.getJSXAttribute();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXPropertyAttributeImpl <em>JSX Property Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXPropertyAttributeImpl
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXPropertyAttribute()
		 * @generated
		 */
		EClass JSX_PROPERTY_ATTRIBUTE = eINSTANCE.getJSXPropertyAttribute();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_PROPERTY_ATTRIBUTE__PROPERTY = eINSTANCE.getJSXPropertyAttribute_Property();

		/**
		 * The meta object literal for the '<em><b>Property As Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT = eINSTANCE.getJSXPropertyAttribute_PropertyAsText();

		/**
		 * The meta object literal for the '<em><b>Jsx Attribute Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE = eINSTANCE.getJSXPropertyAttribute_JsxAttributeValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4jsx.n4JSX.impl.JSXSpreadAttributeImpl <em>JSX Spread Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.JSXSpreadAttributeImpl
		 * @see org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXPackageImpl#getJSXSpreadAttribute()
		 * @generated
		 */
		EClass JSX_SPREAD_ATTRIBUTE = eINSTANCE.getJSXSpreadAttribute();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_SPREAD_ATTRIBUTE__EXPRESSION = eINSTANCE.getJSXSpreadAttribute_Expression();

	}

} //N4JSXPackage
