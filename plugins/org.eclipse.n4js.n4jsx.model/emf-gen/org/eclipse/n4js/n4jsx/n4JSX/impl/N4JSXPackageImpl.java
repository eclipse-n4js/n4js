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
package org.eclipse.n4js.n4jsx.n4JSX.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.n4jsx.n4JSX.JSXAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.JSXChild;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElementName;
import org.eclipse.n4js.n4jsx.n4JSX.JSXExpression;
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.JSXText;
import org.eclipse.n4js.n4jsx.n4JSX.N4JSXFactory;
import org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage;

import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class N4JSXPackageImpl extends EPackageImpl implements N4JSXPackage {
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
	private EClass jsxChildEClass = null;

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
	private EClass jsxElementNameEClass = null;

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
	 * @see org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private N4JSXPackageImpl() {
		super(eNS_URI, N4JSXFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link N4JSXPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static N4JSXPackage init() {
		if (isInited) return (N4JSXPackage)EPackage.Registry.INSTANCE.getEPackage(N4JSXPackage.eNS_URI);

		// Obtain or create and register package
		N4JSXPackageImpl theN4JSXPackage = (N4JSXPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof N4JSXPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new N4JSXPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		N4JSPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theN4JSXPackage.createPackageContents();

		// Initialize created meta-data
		theN4JSXPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theN4JSXPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(N4JSXPackage.eNS_URI, theN4JSXPackage);
		return theN4JSXPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJSXElement() {
		return jsxElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSXElement_JsxElementName() {
		return (EReference)jsxElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSXElement_JsxClosingName() {
		return (EReference)jsxElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSXElement_JsxAttributes() {
		return (EReference)jsxElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSXElement_JsxChildren() {
		return (EReference)jsxElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJSXChild() {
		return jsxChildEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJSXText() {
		return jsxTextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJSXExpression() {
		return jsxExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSXExpression_Expression() {
		return (EReference)jsxExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJSXElementName() {
		return jsxElementNameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSXElementName_Expression() {
		return (EReference)jsxElementNameEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJSXAttribute() {
		return jsxAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJSXPropertyAttribute() {
		return jsxPropertyAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSXPropertyAttribute_Property() {
		return (EReference)jsxPropertyAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJSXPropertyAttribute_PropertyAsText() {
		return (EAttribute)jsxPropertyAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSXPropertyAttribute_JsxAttributeValue() {
		return (EReference)jsxPropertyAttributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJSXSpreadAttribute() {
		return jsxSpreadAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSXSpreadAttribute_Expression() {
		return (EReference)jsxSpreadAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4JSXFactory getN4JSXFactory() {
		return (N4JSXFactory)getEFactoryInstance();
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
		jsxElementEClass = createEClass(JSX_ELEMENT);
		createEReference(jsxElementEClass, JSX_ELEMENT__JSX_ELEMENT_NAME);
		createEReference(jsxElementEClass, JSX_ELEMENT__JSX_CLOSING_NAME);
		createEReference(jsxElementEClass, JSX_ELEMENT__JSX_ATTRIBUTES);
		createEReference(jsxElementEClass, JSX_ELEMENT__JSX_CHILDREN);

		jsxChildEClass = createEClass(JSX_CHILD);

		jsxTextEClass = createEClass(JSX_TEXT);

		jsxExpressionEClass = createEClass(JSX_EXPRESSION);
		createEReference(jsxExpressionEClass, JSX_EXPRESSION__EXPRESSION);

		jsxElementNameEClass = createEClass(JSX_ELEMENT_NAME);
		createEReference(jsxElementNameEClass, JSX_ELEMENT_NAME__EXPRESSION);

		jsxAttributeEClass = createEClass(JSX_ATTRIBUTE);

		jsxPropertyAttributeEClass = createEClass(JSX_PROPERTY_ATTRIBUTE);
		createEReference(jsxPropertyAttributeEClass, JSX_PROPERTY_ATTRIBUTE__PROPERTY);
		createEAttribute(jsxPropertyAttributeEClass, JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT);
		createEReference(jsxPropertyAttributeEClass, JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE);

		jsxSpreadAttributeEClass = createEClass(JSX_SPREAD_ATTRIBUTE);
		createEReference(jsxSpreadAttributeEClass, JSX_SPREAD_ATTRIBUTE__EXPRESSION);
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
		N4JSPackage theN4JSPackage = (N4JSPackage)EPackage.Registry.INSTANCE.getEPackage(N4JSPackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		jsxElementEClass.getESuperTypes().add(theN4JSPackage.getExpression());
		jsxElementEClass.getESuperTypes().add(this.getJSXChild());
		jsxTextEClass.getESuperTypes().add(this.getJSXChild());
		jsxExpressionEClass.getESuperTypes().add(this.getJSXChild());
		jsxPropertyAttributeEClass.getESuperTypes().add(this.getJSXAttribute());
		jsxPropertyAttributeEClass.getESuperTypes().add(theN4JSPackage.getMemberAccess());
		jsxSpreadAttributeEClass.getESuperTypes().add(this.getJSXAttribute());

		// Initialize classes, features, and operations; add parameters
		initEClass(jsxElementEClass, JSXElement.class, "JSXElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXElement_JsxElementName(), this.getJSXElementName(), null, "jsxElementName", null, 0, 1, JSXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJSXElement_JsxClosingName(), this.getJSXElementName(), null, "jsxClosingName", null, 0, 1, JSXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJSXElement_JsxAttributes(), this.getJSXAttribute(), null, "jsxAttributes", null, 0, -1, JSXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJSXElement_JsxChildren(), this.getJSXChild(), null, "jsxChildren", null, 0, -1, JSXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxChildEClass, JSXChild.class, "JSXChild", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jsxTextEClass, JSXText.class, "JSXText", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jsxExpressionEClass, JSXExpression.class, "JSXExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXExpression_Expression(), theN4JSPackage.getExpression(), null, "expression", null, 0, 1, JSXExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxElementNameEClass, JSXElementName.class, "JSXElementName", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXElementName_Expression(), theN4JSPackage.getExpression(), null, "expression", null, 0, 1, JSXElementName.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxAttributeEClass, JSXAttribute.class, "JSXAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jsxPropertyAttributeEClass, JSXPropertyAttribute.class, "JSXPropertyAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXPropertyAttribute_Property(), theTypesPackage.getIdentifiableElement(), null, "property", null, 0, 1, JSXPropertyAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJSXPropertyAttribute_PropertyAsText(), theEcorePackage.getEString(), "propertyAsText", null, 0, 1, JSXPropertyAttribute.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJSXPropertyAttribute_JsxAttributeValue(), theN4JSPackage.getExpression(), null, "jsxAttributeValue", null, 0, 1, JSXPropertyAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsxSpreadAttributeEClass, JSXSpreadAttribute.class, "JSXSpreadAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSXSpreadAttribute_Expression(), theN4JSPackage.getExpression(), null, "expression", null, 0, 1, JSXSpreadAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //N4JSXPackageImpl
