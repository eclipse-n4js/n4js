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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Versionable;

import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TMigratable;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.n4js.ts.types.TVersionable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TEnum</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TEnumImpl#getAstElement <em>Ast Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TEnumImpl#getDeclaredVersion <em>Declared Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TEnumImpl#getMigrations <em>Migrations</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TEnumImpl#isExternal <em>External</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TEnumImpl#getLiterals <em>Literals</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TEnumImpl extends DeclaredTypeWithAccessModifierImpl implements TEnum {
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
	 * The default value of the '{@link #getDeclaredVersion() <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVersion()
	 * @generated
	 * @ordered
	 */
	protected static final int DECLARED_VERSION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDeclaredVersion() <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVersion()
	 * @generated
	 * @ordered
	 */
	protected int declaredVersion = DECLARED_VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMigrations() <em>Migrations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMigrations()
	 * @generated
	 * @ordered
	 */
	protected EList<TMigration> migrations;

	/**
	 * The default value of the '{@link #isExternal() <em>External</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExternal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXTERNAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExternal() <em>External</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExternal()
	 * @generated
	 * @ordered
	 */
	protected boolean external = EXTERNAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLiterals() <em>Literals</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiterals()
	 * @generated
	 * @ordered
	 */
	protected EList<TEnumLiteral> literals;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TEnumImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TENUM;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.TENUM__AST_ELEMENT, oldAstElement, astElement));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TENUM__AST_ELEMENT, oldAstElement, astElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDeclaredVersion() {
		return declaredVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredVersion(int newDeclaredVersion) {
		int oldDeclaredVersion = declaredVersion;
		declaredVersion = newDeclaredVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TENUM__DECLARED_VERSION, oldDeclaredVersion, declaredVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TMigration> getMigrations() {
		if (migrations == null) {
			migrations = new EObjectResolvingEList<TMigration>(TMigration.class, this, TypesPackage.TENUM__MIGRATIONS);
		}
		return migrations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExternal() {
		return external;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExternal(boolean newExternal) {
		boolean oldExternal = external;
		external = newExternal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TENUM__EXTERNAL, oldExternal, external));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TEnumLiteral> getLiterals() {
		if (literals == null) {
			literals = new EObjectContainmentEList<TEnumLiteral>(TEnumLiteral.class, this, TypesPackage.TENUM__LITERALS);
		}
		return literals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getTypeVars() {
		return XcoreCollectionLiterals.<TypeVariable>emptyEList();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVersion() {
		return this.getDeclaredVersion();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TENUM__LITERALS:
				return ((InternalEList<?>)getLiterals()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TENUM__AST_ELEMENT:
				if (resolve) return getAstElement();
				return basicGetAstElement();
			case TypesPackage.TENUM__DECLARED_VERSION:
				return getDeclaredVersion();
			case TypesPackage.TENUM__MIGRATIONS:
				return getMigrations();
			case TypesPackage.TENUM__EXTERNAL:
				return isExternal();
			case TypesPackage.TENUM__LITERALS:
				return getLiterals();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TypesPackage.TENUM__AST_ELEMENT:
				setAstElement((EObject)newValue);
				return;
			case TypesPackage.TENUM__DECLARED_VERSION:
				setDeclaredVersion((Integer)newValue);
				return;
			case TypesPackage.TENUM__MIGRATIONS:
				getMigrations().clear();
				getMigrations().addAll((Collection<? extends TMigration>)newValue);
				return;
			case TypesPackage.TENUM__EXTERNAL:
				setExternal((Boolean)newValue);
				return;
			case TypesPackage.TENUM__LITERALS:
				getLiterals().clear();
				getLiterals().addAll((Collection<? extends TEnumLiteral>)newValue);
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
			case TypesPackage.TENUM__AST_ELEMENT:
				setAstElement((EObject)null);
				return;
			case TypesPackage.TENUM__DECLARED_VERSION:
				setDeclaredVersion(DECLARED_VERSION_EDEFAULT);
				return;
			case TypesPackage.TENUM__MIGRATIONS:
				getMigrations().clear();
				return;
			case TypesPackage.TENUM__EXTERNAL:
				setExternal(EXTERNAL_EDEFAULT);
				return;
			case TypesPackage.TENUM__LITERALS:
				getLiterals().clear();
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
			case TypesPackage.TENUM__AST_ELEMENT:
				return astElement != null;
			case TypesPackage.TENUM__DECLARED_VERSION:
				return declaredVersion != DECLARED_VERSION_EDEFAULT;
			case TypesPackage.TENUM__MIGRATIONS:
				return migrations != null && !migrations.isEmpty();
			case TypesPackage.TENUM__EXTERNAL:
				return external != EXTERNAL_EDEFAULT;
			case TypesPackage.TENUM__LITERALS:
				return literals != null && !literals.isEmpty();
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
				case TypesPackage.TENUM__AST_ELEMENT: return TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == TVersionable.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TENUM__DECLARED_VERSION: return TypesPackage.TVERSIONABLE__DECLARED_VERSION;
				default: return -1;
			}
		}
		if (baseClass == TMigratable.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TENUM__MIGRATIONS: return TypesPackage.TMIGRATABLE__MIGRATIONS;
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
				case TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT: return TypesPackage.TENUM__AST_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == TVersionable.class) {
			switch (baseFeatureID) {
				case TypesPackage.TVERSIONABLE__DECLARED_VERSION: return TypesPackage.TENUM__DECLARED_VERSION;
				default: return -1;
			}
		}
		if (baseClass == TMigratable.class) {
			switch (baseFeatureID) {
				case TypesPackage.TMIGRATABLE__MIGRATIONS: return TypesPackage.TENUM__MIGRATIONS;
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
		if (baseClass == Versionable.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONABLE___GET_VERSION: return TypesPackage.TENUM___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___GET_TYPE_VARS: return TypesPackage.TENUM___GET_TYPE_VARS;
				case TypesPackage.TYPE___GET_VERSION: return TypesPackage.TENUM___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == TVersionable.class) {
			switch (baseOperationID) {
				case TypesPackage.TVERSIONABLE___GET_VERSION: return TypesPackage.TENUM___GET_VERSION;
				default: return -1;
			}
		}
		if (baseClass == TMigratable.class) {
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
			case TypesPackage.TENUM___GET_TYPE_VARS:
				return getTypeVars();
			case TypesPackage.TENUM___GET_VERSION:
				return getVersion();
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (declaredVersion: ");
		result.append(declaredVersion);
		result.append(", external: ");
		result.append(external);
		result.append(')');
		return result.toString();
	}

} //TEnumImpl
