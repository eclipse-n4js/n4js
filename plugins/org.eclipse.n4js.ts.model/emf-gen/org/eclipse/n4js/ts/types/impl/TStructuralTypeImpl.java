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
package org.eclipse.n4js.ts.types.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;

import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TStructural Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TStructuralTypeImpl#getAstElement <em>Ast Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TStructuralTypeImpl extends ContainerTypeImpl<TStructMember> implements TStructuralType {
	/**
	 * The cached value of the '{@link #getAstElement() <em>Ast Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstElement()
	 * @generated
	 * @ordered
	 */
	protected EObject astElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TStructuralTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TSTRUCTURAL_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<TStructMember> getOwnedMembers() {
		if (ownedMembers == null) {
			ownedMembers = new EObjectContainmentEList<TStructMember>(TStructMember.class, this, TypesPackage.TSTRUCTURAL_TYPE__OWNED_MEMBERS);
		}
		return ownedMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject getAstElement() {
		if (astElement != null && astElement.eIsProxy()) {
			InternalEObject oldAstElement = (InternalEObject)astElement;
			astElement = eResolveProxy(oldAstElement);
			if (astElement != oldAstElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.TSTRUCTURAL_TYPE__AST_ELEMENT, oldAstElement, astElement));
			}
		}
		return astElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetAstElement() {
		return astElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAstElement(EObject newAstElement) {
		EObject oldAstElement = astElement;
		astElement = newAstElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TSTRUCTURAL_TYPE__AST_ELEMENT, oldAstElement, astElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFinal() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TSTRUCTURAL_TYPE__AST_ELEMENT:
				if (resolve) return getAstElement();
				return basicGetAstElement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TypesPackage.TSTRUCTURAL_TYPE__AST_ELEMENT:
				setAstElement((EObject)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TypesPackage.TSTRUCTURAL_TYPE__AST_ELEMENT:
				setAstElement((EObject)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TypesPackage.TSTRUCTURAL_TYPE__AST_ELEMENT:
				return astElement != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TSTRUCTURAL_TYPE__AST_ELEMENT: return TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT: return TypesPackage.TSTRUCTURAL_TYPE__AST_ELEMENT;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___IS_FINAL: return TypesPackage.TSTRUCTURAL_TYPE___IS_FINAL;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.TSTRUCTURAL_TYPE___IS_FINAL:
				return isFinal();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TStructuralTypeImpl
