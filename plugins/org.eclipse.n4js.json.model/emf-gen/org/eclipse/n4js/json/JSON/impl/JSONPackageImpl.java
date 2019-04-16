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
package org.eclipse.n4js.json.JSON.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONNullLiteral;
import org.eclipse.n4js.json.JSON.JSONNumericLiteral;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JSONPackageImpl extends EPackageImpl implements JSONPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsonDocumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsonValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsonObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsonArrayEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameValuePairEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsonStringLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsonNumericLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsonBooleanLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsonNullLiteralEClass = null;

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
	 * @see org.eclipse.n4js.json.JSON.JSONPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private JSONPackageImpl() {
		super(eNS_URI, JSONFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link JSONPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static JSONPackage init() {
		if (isInited) return (JSONPackage)EPackage.Registry.INSTANCE.getEPackage(JSONPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredJSONPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		JSONPackageImpl theJSONPackage = registeredJSONPackage instanceof JSONPackageImpl ? (JSONPackageImpl)registeredJSONPackage : new JSONPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theJSONPackage.createPackageContents();

		// Initialize created meta-data
		theJSONPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theJSONPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(JSONPackage.eNS_URI, theJSONPackage);
		return theJSONPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSONDocument() {
		return jsonDocumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSONDocument_Content() {
		return (EReference)jsonDocumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getJSONDocument__ToString() {
		return jsonDocumentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSONValue() {
		return jsonValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSONObject() {
		return jsonObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSONObject_NameValuePairs() {
		return (EReference)jsonObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getJSONObject__ToString() {
		return jsonObjectEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSONArray() {
		return jsonArrayEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJSONArray_Elements() {
		return (EReference)jsonArrayEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getJSONArray__ToString() {
		return jsonArrayEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNameValuePair() {
		return nameValuePairEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNameValuePair_Name() {
		return (EAttribute)nameValuePairEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNameValuePair_Value() {
		return (EReference)nameValuePairEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getNameValuePair__ToString() {
		return nameValuePairEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSONStringLiteral() {
		return jsonStringLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getJSONStringLiteral_Value() {
		return (EAttribute)jsonStringLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getJSONStringLiteral__ToString() {
		return jsonStringLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSONNumericLiteral() {
		return jsonNumericLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getJSONNumericLiteral_Value() {
		return (EAttribute)jsonNumericLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getJSONNumericLiteral__ToString() {
		return jsonNumericLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSONBooleanLiteral() {
		return jsonBooleanLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getJSONBooleanLiteral_BooleanValue() {
		return (EAttribute)jsonBooleanLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getJSONBooleanLiteral__ToString() {
		return jsonBooleanLiteralEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJSONNullLiteral() {
		return jsonNullLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSONFactory getJSONFactory() {
		return (JSONFactory)getEFactoryInstance();
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
		jsonDocumentEClass = createEClass(JSON_DOCUMENT);
		createEReference(jsonDocumentEClass, JSON_DOCUMENT__CONTENT);
		createEOperation(jsonDocumentEClass, JSON_DOCUMENT___TO_STRING);

		jsonValueEClass = createEClass(JSON_VALUE);

		jsonObjectEClass = createEClass(JSON_OBJECT);
		createEReference(jsonObjectEClass, JSON_OBJECT__NAME_VALUE_PAIRS);
		createEOperation(jsonObjectEClass, JSON_OBJECT___TO_STRING);

		jsonArrayEClass = createEClass(JSON_ARRAY);
		createEReference(jsonArrayEClass, JSON_ARRAY__ELEMENTS);
		createEOperation(jsonArrayEClass, JSON_ARRAY___TO_STRING);

		nameValuePairEClass = createEClass(NAME_VALUE_PAIR);
		createEAttribute(nameValuePairEClass, NAME_VALUE_PAIR__NAME);
		createEReference(nameValuePairEClass, NAME_VALUE_PAIR__VALUE);
		createEOperation(nameValuePairEClass, NAME_VALUE_PAIR___TO_STRING);

		jsonStringLiteralEClass = createEClass(JSON_STRING_LITERAL);
		createEAttribute(jsonStringLiteralEClass, JSON_STRING_LITERAL__VALUE);
		createEOperation(jsonStringLiteralEClass, JSON_STRING_LITERAL___TO_STRING);

		jsonNumericLiteralEClass = createEClass(JSON_NUMERIC_LITERAL);
		createEAttribute(jsonNumericLiteralEClass, JSON_NUMERIC_LITERAL__VALUE);
		createEOperation(jsonNumericLiteralEClass, JSON_NUMERIC_LITERAL___TO_STRING);

		jsonBooleanLiteralEClass = createEClass(JSON_BOOLEAN_LITERAL);
		createEAttribute(jsonBooleanLiteralEClass, JSON_BOOLEAN_LITERAL__BOOLEAN_VALUE);
		createEOperation(jsonBooleanLiteralEClass, JSON_BOOLEAN_LITERAL___TO_STRING);

		jsonNullLiteralEClass = createEClass(JSON_NULL_LITERAL);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		jsonObjectEClass.getESuperTypes().add(this.getJSONValue());
		jsonArrayEClass.getESuperTypes().add(this.getJSONValue());
		jsonStringLiteralEClass.getESuperTypes().add(this.getJSONValue());
		jsonNumericLiteralEClass.getESuperTypes().add(this.getJSONValue());
		jsonBooleanLiteralEClass.getESuperTypes().add(this.getJSONValue());
		jsonNullLiteralEClass.getESuperTypes().add(this.getJSONValue());

		// Initialize classes, features, and operations; add parameters
		initEClass(jsonDocumentEClass, JSONDocument.class, "JSONDocument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSONDocument_Content(), this.getJSONValue(), null, "content", null, 0, 1, JSONDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getJSONDocument__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jsonValueEClass, JSONValue.class, "JSONValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jsonObjectEClass, JSONObject.class, "JSONObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSONObject_NameValuePairs(), this.getNameValuePair(), null, "nameValuePairs", null, 0, -1, JSONObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getJSONObject__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jsonArrayEClass, JSONArray.class, "JSONArray", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSONArray_Elements(), this.getJSONValue(), null, "elements", null, 0, -1, JSONArray.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getJSONArray__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(nameValuePairEClass, NameValuePair.class, "NameValuePair", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNameValuePair_Name(), theEcorePackage.getEString(), "name", null, 0, 1, NameValuePair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNameValuePair_Value(), this.getJSONValue(), null, "value", null, 0, 1, NameValuePair.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getNameValuePair__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jsonStringLiteralEClass, JSONStringLiteral.class, "JSONStringLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJSONStringLiteral_Value(), theEcorePackage.getEString(), "value", null, 0, 1, JSONStringLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getJSONStringLiteral__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jsonNumericLiteralEClass, JSONNumericLiteral.class, "JSONNumericLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJSONNumericLiteral_Value(), theEcorePackage.getEBigDecimal(), "value", null, 0, 1, JSONNumericLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getJSONNumericLiteral__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jsonBooleanLiteralEClass, JSONBooleanLiteral.class, "JSONBooleanLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJSONBooleanLiteral_BooleanValue(), theEcorePackage.getEBoolean(), "booleanValue", null, 0, 1, JSONBooleanLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getJSONBooleanLiteral__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(jsonNullLiteralEClass, JSONNullLiteral.class, "JSONNullLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //JSONPackageImpl
