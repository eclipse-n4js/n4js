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
package org.eclipse.n4js.n4jsx.n4JSX.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.n4js.n4JS.Expression;

import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4jsx.n4JSX.*;

import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage
 * @generated
 */
public class N4JSXSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static N4JSXPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4JSXSwitch() {
		if (modelPackage == null) {
			modelPackage = N4JSXPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case N4JSXPackage.JSX_ELEMENT: {
				JSXElement jsxElement = (JSXElement)theEObject;
				T result = caseJSXElement(jsxElement);
				if (result == null) result = caseExpression(jsxElement);
				if (result == null) result = caseJSXChild(jsxElement);
				if (result == null) result = caseTypableElement(jsxElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4JSXPackage.JSX_CHILD: {
				JSXChild jsxChild = (JSXChild)theEObject;
				T result = caseJSXChild(jsxChild);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4JSXPackage.JSX_TEXT: {
				JSXText jsxText = (JSXText)theEObject;
				T result = caseJSXText(jsxText);
				if (result == null) result = caseJSXChild(jsxText);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4JSXPackage.JSX_EXPRESSION: {
				JSXExpression jsxExpression = (JSXExpression)theEObject;
				T result = caseJSXExpression(jsxExpression);
				if (result == null) result = caseJSXChild(jsxExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4JSXPackage.JSX_ELEMENT_NAME: {
				JSXElementName jsxElementName = (JSXElementName)theEObject;
				T result = caseJSXElementName(jsxElementName);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4JSXPackage.JSX_ATTRIBUTE: {
				JSXAttribute jsxAttribute = (JSXAttribute)theEObject;
				T result = caseJSXAttribute(jsxAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4JSXPackage.JSX_PROPERTY_ATTRIBUTE: {
				JSXPropertyAttribute jsxPropertyAttribute = (JSXPropertyAttribute)theEObject;
				T result = caseJSXPropertyAttribute(jsxPropertyAttribute);
				if (result == null) result = caseJSXAttribute(jsxPropertyAttribute);
				if (result == null) result = caseMemberAccess(jsxPropertyAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case N4JSXPackage.JSX_SPREAD_ATTRIBUTE: {
				JSXSpreadAttribute jsxSpreadAttribute = (JSXSpreadAttribute)theEObject;
				T result = caseJSXSpreadAttribute(jsxSpreadAttribute);
				if (result == null) result = caseJSXAttribute(jsxSpreadAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>JSX Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JSX Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJSXElement(JSXElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>JSX Child</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JSX Child</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJSXChild(JSXChild object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>JSX Text</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JSX Text</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJSXText(JSXText object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>JSX Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JSX Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJSXExpression(JSXExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>JSX Element Name</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JSX Element Name</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJSXElementName(JSXElementName object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>JSX Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JSX Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJSXAttribute(JSXAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>JSX Property Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JSX Property Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJSXPropertyAttribute(JSXPropertyAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>JSX Spread Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JSX Spread Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJSXSpreadAttribute(JSXSpreadAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypableElement(TypableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Member Access</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Member Access</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMemberAccess(MemberAccess object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //N4JSXSwitch
