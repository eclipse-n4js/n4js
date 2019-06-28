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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.json.JSON.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JSONFactoryImpl extends EFactoryImpl implements JSONFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JSONFactory init() {
		try {
			JSONFactory theJSONFactory = (JSONFactory)EPackage.Registry.INSTANCE.getEFactory(JSONPackage.eNS_URI);
			if (theJSONFactory != null) {
				return theJSONFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new JSONFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JSONFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case JSONPackage.JSON_DOCUMENT: return createJSONDocument();
			case JSONPackage.JSON_OBJECT: return createJSONObject();
			case JSONPackage.JSON_ARRAY: return createJSONArray();
			case JSONPackage.NAME_VALUE_PAIR: return createNameValuePair();
			case JSONPackage.JSON_STRING_LITERAL: return createJSONStringLiteral();
			case JSONPackage.JSON_NUMERIC_LITERAL: return createJSONNumericLiteral();
			case JSONPackage.JSON_BOOLEAN_LITERAL: return createJSONBooleanLiteral();
			case JSONPackage.JSON_NULL_LITERAL: return createJSONNullLiteral();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSONDocument createJSONDocument() {
		JSONDocumentImpl jsonDocument = new JSONDocumentImpl();
		return jsonDocument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSONObject createJSONObject() {
		JSONObjectImpl jsonObject = new JSONObjectImpl();
		return jsonObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSONArray createJSONArray() {
		JSONArrayImpl jsonArray = new JSONArrayImpl();
		return jsonArray;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NameValuePair createNameValuePair() {
		NameValuePairImpl nameValuePair = new NameValuePairImpl();
		return nameValuePair;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSONStringLiteral createJSONStringLiteral() {
		JSONStringLiteralImpl jsonStringLiteral = new JSONStringLiteralImpl();
		return jsonStringLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSONNumericLiteral createJSONNumericLiteral() {
		JSONNumericLiteralImpl jsonNumericLiteral = new JSONNumericLiteralImpl();
		return jsonNumericLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSONBooleanLiteral createJSONBooleanLiteral() {
		JSONBooleanLiteralImpl jsonBooleanLiteral = new JSONBooleanLiteralImpl();
		return jsonBooleanLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSONNullLiteral createJSONNullLiteral() {
		JSONNullLiteralImpl jsonNullLiteral = new JSONNullLiteralImpl();
		return jsonNullLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSONPackage getJSONPackage() {
		return (JSONPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static JSONPackage getPackage() {
		return JSONPackage.eINSTANCE;
	}

} //JSONFactoryImpl
