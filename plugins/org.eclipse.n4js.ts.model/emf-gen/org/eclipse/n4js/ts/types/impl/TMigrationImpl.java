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

import com.google.common.collect.Iterables;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;
import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.VoidType;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TMigration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMigrationImpl#getSourceVersion <em>Source Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMigrationImpl#getTargetVersion <em>Target Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TMigrationImpl extends TFunctionImpl implements TMigration {
	/**
	 * The default value of the '{@link #getSourceVersion() <em>Source Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceVersion()
	 * @generated
	 * @ordered
	 */
	protected static final int SOURCE_VERSION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSourceVersion() <em>Source Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceVersion()
	 * @generated
	 * @ordered
	 */
	protected int sourceVersion = SOURCE_VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetVersion() <em>Target Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetVersion()
	 * @generated
	 * @ordered
	 */
	protected static final int TARGET_VERSION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTargetVersion() <em>Target Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetVersion()
	 * @generated
	 * @ordered
	 */
	protected int targetVersion = TARGET_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TMigrationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TMIGRATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSourceVersion() {
		return sourceVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceVersion(int newSourceVersion) {
		int oldSourceVersion = sourceVersion;
		sourceVersion = newSourceVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMIGRATION__SOURCE_VERSION, oldSourceVersion, sourceVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTargetVersion() {
		return targetVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetVersion(int newTargetVersion) {
		int oldTargetVersion = targetVersion;
		targetVersion = newTargetVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMIGRATION__TARGET_VERSION, oldTargetVersion, targetVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeRef> getSourceTypeRefs() {
		final Function1<TFormalParameter, TypeRef> _function = new Function1<TFormalParameter, TypeRef>() {
			public TypeRef apply(final TFormalParameter p) {
				return p.getTypeRef();
			}
		};
		return ECollections.<TypeRef>toEList(XcoreEListExtensions.<TFormalParameter, TypeRef>map(this.getFpars(), _function));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeRef> getTargetTypeRefs() {
		final TypeRef returnTypeRef = this.getReturnTypeRef();
		if ((returnTypeRef == null)) {
			return XcoreCollectionLiterals.<TypeRef>emptyEList();
		}
		if ((returnTypeRef instanceof StructuralTypeRef)) {
			final Function1<TStructField, TypeRef> _function = new Function1<TStructField, TypeRef>() {
				public TypeRef apply(final TStructField f) {
					return f.getTypeRef();
				}
			};
			return ECollections.<TypeRef>toEList(IterableExtensions.<TStructField, TypeRef>map(Iterables.<TStructField>filter(this.getReturnTypeRef().getStructuralMembers(), TStructField.class), _function));
		}
		else {
			Type _declaredType = this.getReturnTypeRef().getDeclaredType();
			if ((_declaredType instanceof VoidType)) {
				return XcoreCollectionLiterals.<TypeRef>emptyEList();
			}
			else {
				return ECollections.<TypeRef>asEList(this.getReturnTypeRef());
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TMIGRATION__SOURCE_VERSION:
				return getSourceVersion();
			case TypesPackage.TMIGRATION__TARGET_VERSION:
				return getTargetVersion();
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
			case TypesPackage.TMIGRATION__SOURCE_VERSION:
				setSourceVersion((Integer)newValue);
				return;
			case TypesPackage.TMIGRATION__TARGET_VERSION:
				setTargetVersion((Integer)newValue);
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
			case TypesPackage.TMIGRATION__SOURCE_VERSION:
				setSourceVersion(SOURCE_VERSION_EDEFAULT);
				return;
			case TypesPackage.TMIGRATION__TARGET_VERSION:
				setTargetVersion(TARGET_VERSION_EDEFAULT);
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
			case TypesPackage.TMIGRATION__SOURCE_VERSION:
				return sourceVersion != SOURCE_VERSION_EDEFAULT;
			case TypesPackage.TMIGRATION__TARGET_VERSION:
				return targetVersion != TARGET_VERSION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.TMIGRATION___GET_SOURCE_TYPE_REFS:
				return getSourceTypeRefs();
			case TypesPackage.TMIGRATION___GET_TARGET_TYPE_REFS:
				return getTargetTypeRefs();
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (sourceVersion: ");
		result.append(sourceVersion);
		result.append(", targetVersion: ");
		result.append(targetVersion);
		result.append(')');
		return result.toString();
	}

} //TMigrationImpl
