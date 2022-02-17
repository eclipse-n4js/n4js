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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.types.AbstractModule;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractModuleImpl#getAstElement <em>Ast Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractModuleImpl#getSimpleName <em>Simple Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractModuleImpl#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractModuleImpl extends AbstractNamespaceImpl implements AbstractModule {
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
	 * The default value of the '{@link #getSimpleName() <em>Simple Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleName()
	 * @generated
	 * @ordered
	 */
	protected static final String SIMPLE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSimpleName() <em>Simple Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleName()
	 * @generated
	 * @ordered
	 */
	protected String simpleName = SIMPLE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String qualifiedName = QUALIFIED_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.ABSTRACT_MODULE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.ABSTRACT_MODULE__AST_ELEMENT, oldAstElement, astElement));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ABSTRACT_MODULE__AST_ELEMENT, oldAstElement, astElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSimpleName(String newSimpleName) {
		String oldSimpleName = simpleName;
		simpleName = newSimpleName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ABSTRACT_MODULE__SIMPLE_NAME, oldSimpleName, simpleName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedName() {
		return qualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setQualifiedName(String newQualifiedName) {
		String oldQualifiedName = qualifiedName;
		qualifiedName = newQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ABSTRACT_MODULE__QUALIFIED_NAME, oldQualifiedName, qualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.ABSTRACT_MODULE__AST_ELEMENT:
				if (resolve) return getAstElement();
				return basicGetAstElement();
			case TypesPackage.ABSTRACT_MODULE__SIMPLE_NAME:
				return getSimpleName();
			case TypesPackage.ABSTRACT_MODULE__QUALIFIED_NAME:
				return getQualifiedName();
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
			case TypesPackage.ABSTRACT_MODULE__AST_ELEMENT:
				setAstElement((EObject)newValue);
				return;
			case TypesPackage.ABSTRACT_MODULE__SIMPLE_NAME:
				setSimpleName((String)newValue);
				return;
			case TypesPackage.ABSTRACT_MODULE__QUALIFIED_NAME:
				setQualifiedName((String)newValue);
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
			case TypesPackage.ABSTRACT_MODULE__AST_ELEMENT:
				setAstElement((EObject)null);
				return;
			case TypesPackage.ABSTRACT_MODULE__SIMPLE_NAME:
				setSimpleName(SIMPLE_NAME_EDEFAULT);
				return;
			case TypesPackage.ABSTRACT_MODULE__QUALIFIED_NAME:
				setQualifiedName(QUALIFIED_NAME_EDEFAULT);
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
			case TypesPackage.ABSTRACT_MODULE__AST_ELEMENT:
				return astElement != null;
			case TypesPackage.ABSTRACT_MODULE__SIMPLE_NAME:
				return SIMPLE_NAME_EDEFAULT == null ? simpleName != null : !SIMPLE_NAME_EDEFAULT.equals(simpleName);
			case TypesPackage.ABSTRACT_MODULE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? qualifiedName != null : !QUALIFIED_NAME_EDEFAULT.equals(qualifiedName);
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
				case TypesPackage.ABSTRACT_MODULE__AST_ELEMENT: return TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT;
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
				case TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT: return TypesPackage.ABSTRACT_MODULE__AST_ELEMENT;
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
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (simpleName: ");
		result.append(simpleName);
		result.append(", qualifiedName: ");
		result.append(qualifiedName);
		result.append(')');
		return result.toString();
	}

} //AbstractModuleImpl
