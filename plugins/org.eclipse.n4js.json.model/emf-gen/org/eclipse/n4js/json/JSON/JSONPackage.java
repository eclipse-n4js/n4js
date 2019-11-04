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
package org.eclipse.n4js.json.JSON;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.n4js.json.JSON.JSONFactory
 * @model kind="package"
 * @generated
 */
public interface JSONPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "JSON";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/JSON";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "JSON";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	JSONPackage eINSTANCE = org.eclipse.n4js.json.JSON.impl.JSONPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.json.JSON.impl.JSONDocumentImpl <em>Document</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.json.JSON.impl.JSONDocumentImpl
	 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONDocument()
	 * @generated
	 */
	int JSON_DOCUMENT = 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_DOCUMENT__CONTENT = 0;

	/**
	 * The number of structural features of the '<em>Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_DOCUMENT_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_DOCUMENT___TO_STRING = 0;

	/**
	 * The number of operations of the '<em>Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_DOCUMENT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.json.JSON.impl.JSONValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.json.JSON.impl.JSONValueImpl
	 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONValue()
	 * @generated
	 */
	int JSON_VALUE = 1;

	/**
	 * The number of structural features of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_VALUE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.json.JSON.impl.JSONObjectImpl <em>Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.json.JSON.impl.JSONObjectImpl
	 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONObject()
	 * @generated
	 */
	int JSON_OBJECT = 2;

	/**
	 * The feature id for the '<em><b>Name Value Pairs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_OBJECT__NAME_VALUE_PAIRS = JSON_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_OBJECT_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_OBJECT___TO_STRING = JSON_VALUE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_OBJECT_OPERATION_COUNT = JSON_VALUE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.json.JSON.impl.JSONArrayImpl <em>Array</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.json.JSON.impl.JSONArrayImpl
	 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONArray()
	 * @generated
	 */
	int JSON_ARRAY = 3;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_ARRAY__ELEMENTS = JSON_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Array</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_ARRAY_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_ARRAY___TO_STRING = JSON_VALUE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Array</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_ARRAY_OPERATION_COUNT = JSON_VALUE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.json.JSON.impl.NameValuePairImpl <em>Name Value Pair</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.json.JSON.impl.NameValuePairImpl
	 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getNameValuePair()
	 * @generated
	 */
	int NAME_VALUE_PAIR = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE_PAIR__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE_PAIR__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Name Value Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE_PAIR_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE_PAIR___TO_STRING = 0;

	/**
	 * The number of operations of the '<em>Name Value Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE_PAIR_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.json.JSON.impl.JSONStringLiteralImpl <em>String Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.json.JSON.impl.JSONStringLiteralImpl
	 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONStringLiteral()
	 * @generated
	 */
	int JSON_STRING_LITERAL = 5;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_STRING_LITERAL__VALUE = JSON_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_STRING_LITERAL_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_STRING_LITERAL___TO_STRING = JSON_VALUE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>String Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_STRING_LITERAL_OPERATION_COUNT = JSON_VALUE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.json.JSON.impl.JSONNumericLiteralImpl <em>Numeric Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.json.JSON.impl.JSONNumericLiteralImpl
	 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONNumericLiteral()
	 * @generated
	 */
	int JSON_NUMERIC_LITERAL = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_NUMERIC_LITERAL__VALUE = JSON_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Numeric Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_NUMERIC_LITERAL_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_NUMERIC_LITERAL___TO_STRING = JSON_VALUE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Numeric Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_NUMERIC_LITERAL_OPERATION_COUNT = JSON_VALUE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.json.JSON.impl.JSONBooleanLiteralImpl <em>Boolean Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.json.JSON.impl.JSONBooleanLiteralImpl
	 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONBooleanLiteral()
	 * @generated
	 */
	int JSON_BOOLEAN_LITERAL = 7;

	/**
	 * The feature id for the '<em><b>Boolean Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_BOOLEAN_LITERAL__BOOLEAN_VALUE = JSON_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_BOOLEAN_LITERAL_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_BOOLEAN_LITERAL___TO_STRING = JSON_VALUE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_BOOLEAN_LITERAL_OPERATION_COUNT = JSON_VALUE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.json.JSON.impl.JSONNullLiteralImpl <em>Null Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.json.JSON.impl.JSONNullLiteralImpl
	 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONNullLiteral()
	 * @generated
	 */
	int JSON_NULL_LITERAL = 8;

	/**
	 * The number of structural features of the '<em>Null Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_NULL_LITERAL_FEATURE_COUNT = JSON_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Null Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSON_NULL_LITERAL_OPERATION_COUNT = JSON_VALUE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.json.JSON.JSONDocument <em>Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONDocument
	 * @generated
	 */
	EClass getJSONDocument();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.json.JSON.JSONDocument#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Content</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONDocument#getContent()
	 * @see #getJSONDocument()
	 * @generated
	 */
	EReference getJSONDocument_Content();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.json.JSON.JSONDocument#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.json.JSON.JSONDocument#toString()
	 * @generated
	 */
	EOperation getJSONDocument__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.json.JSON.JSONValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONValue
	 * @generated
	 */
	EClass getJSONValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.json.JSON.JSONObject <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONObject
	 * @generated
	 */
	EClass getJSONObject();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.json.JSON.JSONObject#getNameValuePairs <em>Name Value Pairs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Name Value Pairs</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONObject#getNameValuePairs()
	 * @see #getJSONObject()
	 * @generated
	 */
	EReference getJSONObject_NameValuePairs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.json.JSON.JSONObject#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.json.JSON.JSONObject#toString()
	 * @generated
	 */
	EOperation getJSONObject__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.json.JSON.JSONArray <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONArray
	 * @generated
	 */
	EClass getJSONArray();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.json.JSON.JSONArray#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONArray#getElements()
	 * @see #getJSONArray()
	 * @generated
	 */
	EReference getJSONArray_Elements();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.json.JSON.JSONArray#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.json.JSON.JSONArray#toString()
	 * @generated
	 */
	EOperation getJSONArray__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.json.JSON.NameValuePair <em>Name Value Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Value Pair</em>'.
	 * @see org.eclipse.n4js.json.JSON.NameValuePair
	 * @generated
	 */
	EClass getNameValuePair();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.json.JSON.NameValuePair#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.json.JSON.NameValuePair#getName()
	 * @see #getNameValuePair()
	 * @generated
	 */
	EAttribute getNameValuePair_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.json.JSON.NameValuePair#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.n4js.json.JSON.NameValuePair#getValue()
	 * @see #getNameValuePair()
	 * @generated
	 */
	EReference getNameValuePair_Value();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.json.JSON.NameValuePair#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.json.JSON.NameValuePair#toString()
	 * @generated
	 */
	EOperation getNameValuePair__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.json.JSON.JSONStringLiteral <em>String Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Literal</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONStringLiteral
	 * @generated
	 */
	EClass getJSONStringLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.json.JSON.JSONStringLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONStringLiteral#getValue()
	 * @see #getJSONStringLiteral()
	 * @generated
	 */
	EAttribute getJSONStringLiteral_Value();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.json.JSON.JSONStringLiteral#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.json.JSON.JSONStringLiteral#toString()
	 * @generated
	 */
	EOperation getJSONStringLiteral__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.json.JSON.JSONNumericLiteral <em>Numeric Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Literal</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONNumericLiteral
	 * @generated
	 */
	EClass getJSONNumericLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.json.JSON.JSONNumericLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONNumericLiteral#getValue()
	 * @see #getJSONNumericLiteral()
	 * @generated
	 */
	EAttribute getJSONNumericLiteral_Value();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.json.JSON.JSONNumericLiteral#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.json.JSON.JSONNumericLiteral#toString()
	 * @generated
	 */
	EOperation getJSONNumericLiteral__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.json.JSON.JSONBooleanLiteral <em>Boolean Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Literal</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONBooleanLiteral
	 * @generated
	 */
	EClass getJSONBooleanLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.json.JSON.JSONBooleanLiteral#isBooleanValue <em>Boolean Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Boolean Value</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONBooleanLiteral#isBooleanValue()
	 * @see #getJSONBooleanLiteral()
	 * @generated
	 */
	EAttribute getJSONBooleanLiteral_BooleanValue();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.json.JSON.JSONBooleanLiteral#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.json.JSON.JSONBooleanLiteral#toString()
	 * @generated
	 */
	EOperation getJSONBooleanLiteral__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.json.JSON.JSONNullLiteral <em>Null Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Null Literal</em>'.
	 * @see org.eclipse.n4js.json.JSON.JSONNullLiteral
	 * @generated
	 */
	EClass getJSONNullLiteral();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	JSONFactory getJSONFactory();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.json.JSON.impl.JSONDocumentImpl <em>Document</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.json.JSON.impl.JSONDocumentImpl
		 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONDocument()
		 * @generated
		 */
		EClass JSON_DOCUMENT = eINSTANCE.getJSONDocument();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSON_DOCUMENT__CONTENT = eINSTANCE.getJSONDocument_Content();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JSON_DOCUMENT___TO_STRING = eINSTANCE.getJSONDocument__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.json.JSON.impl.JSONValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.json.JSON.impl.JSONValueImpl
		 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONValue()
		 * @generated
		 */
		EClass JSON_VALUE = eINSTANCE.getJSONValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.json.JSON.impl.JSONObjectImpl <em>Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.json.JSON.impl.JSONObjectImpl
		 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONObject()
		 * @generated
		 */
		EClass JSON_OBJECT = eINSTANCE.getJSONObject();

		/**
		 * The meta object literal for the '<em><b>Name Value Pairs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSON_OBJECT__NAME_VALUE_PAIRS = eINSTANCE.getJSONObject_NameValuePairs();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JSON_OBJECT___TO_STRING = eINSTANCE.getJSONObject__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.json.JSON.impl.JSONArrayImpl <em>Array</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.json.JSON.impl.JSONArrayImpl
		 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONArray()
		 * @generated
		 */
		EClass JSON_ARRAY = eINSTANCE.getJSONArray();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSON_ARRAY__ELEMENTS = eINSTANCE.getJSONArray_Elements();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JSON_ARRAY___TO_STRING = eINSTANCE.getJSONArray__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.json.JSON.impl.NameValuePairImpl <em>Name Value Pair</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.json.JSON.impl.NameValuePairImpl
		 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getNameValuePair()
		 * @generated
		 */
		EClass NAME_VALUE_PAIR = eINSTANCE.getNameValuePair();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_VALUE_PAIR__NAME = eINSTANCE.getNameValuePair_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAME_VALUE_PAIR__VALUE = eINSTANCE.getNameValuePair_Value();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAME_VALUE_PAIR___TO_STRING = eINSTANCE.getNameValuePair__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.json.JSON.impl.JSONStringLiteralImpl <em>String Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.json.JSON.impl.JSONStringLiteralImpl
		 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONStringLiteral()
		 * @generated
		 */
		EClass JSON_STRING_LITERAL = eINSTANCE.getJSONStringLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JSON_STRING_LITERAL__VALUE = eINSTANCE.getJSONStringLiteral_Value();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JSON_STRING_LITERAL___TO_STRING = eINSTANCE.getJSONStringLiteral__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.json.JSON.impl.JSONNumericLiteralImpl <em>Numeric Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.json.JSON.impl.JSONNumericLiteralImpl
		 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONNumericLiteral()
		 * @generated
		 */
		EClass JSON_NUMERIC_LITERAL = eINSTANCE.getJSONNumericLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JSON_NUMERIC_LITERAL__VALUE = eINSTANCE.getJSONNumericLiteral_Value();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JSON_NUMERIC_LITERAL___TO_STRING = eINSTANCE.getJSONNumericLiteral__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.json.JSON.impl.JSONBooleanLiteralImpl <em>Boolean Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.json.JSON.impl.JSONBooleanLiteralImpl
		 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONBooleanLiteral()
		 * @generated
		 */
		EClass JSON_BOOLEAN_LITERAL = eINSTANCE.getJSONBooleanLiteral();

		/**
		 * The meta object literal for the '<em><b>Boolean Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JSON_BOOLEAN_LITERAL__BOOLEAN_VALUE = eINSTANCE.getJSONBooleanLiteral_BooleanValue();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JSON_BOOLEAN_LITERAL___TO_STRING = eINSTANCE.getJSONBooleanLiteral__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.json.JSON.impl.JSONNullLiteralImpl <em>Null Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.json.JSON.impl.JSONNullLiteralImpl
		 * @see org.eclipse.n4js.json.JSON.impl.JSONPackageImpl#getJSONNullLiteral()
		 * @generated
		 */
		EClass JSON_NULL_LITERAL = eINSTANCE.getJSONNullLiteral();

	}

} //JSONPackage
