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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TMigratable;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.ts.versions.MigratableUtils;

import org.eclipse.n4js.utils.EcoreUtilN4;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

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
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMigrationImpl#isHasDeclaredSourceAndTargetVersion <em>Has Declared Source And Target Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMigrationImpl#getSourceTypeRefs <em>Source Type Refs</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMigrationImpl#getTargetTypeRefs <em>Target Type Refs</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMigrationImpl#get_principalArgumentType <em>principal Argument Type</em>}</li>
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
	 * The default value of the '{@link #isHasDeclaredSourceAndTargetVersion() <em>Has Declared Source And Target Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasDeclaredSourceAndTargetVersion()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_DECLARED_SOURCE_AND_TARGET_VERSION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasDeclaredSourceAndTargetVersion() <em>Has Declared Source And Target Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasDeclaredSourceAndTargetVersion()
	 * @generated
	 * @ordered
	 */
	protected boolean hasDeclaredSourceAndTargetVersion = HAS_DECLARED_SOURCE_AND_TARGET_VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSourceTypeRefs() <em>Source Type Refs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceTypeRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeRef> sourceTypeRefs;

	/**
	 * The cached value of the '{@link #getTargetTypeRefs() <em>Target Type Refs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetTypeRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeRef> targetTypeRefs;

	/**
	 * The cached value of the '{@link #get_principalArgumentType() <em>principal Argument Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_principalArgumentType()
	 * @generated
	 * @ordered
	 */
	protected TMigratable _principalArgumentType;

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
	public boolean isHasDeclaredSourceAndTargetVersion() {
		return hasDeclaredSourceAndTargetVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasDeclaredSourceAndTargetVersion(boolean newHasDeclaredSourceAndTargetVersion) {
		boolean oldHasDeclaredSourceAndTargetVersion = hasDeclaredSourceAndTargetVersion;
		hasDeclaredSourceAndTargetVersion = newHasDeclaredSourceAndTargetVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMIGRATION__HAS_DECLARED_SOURCE_AND_TARGET_VERSION, oldHasDeclaredSourceAndTargetVersion, hasDeclaredSourceAndTargetVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeRef> getSourceTypeRefs() {
		if (sourceTypeRefs == null) {
			sourceTypeRefs = new EObjectResolvingEList<TypeRef>(TypeRef.class, this, TypesPackage.TMIGRATION__SOURCE_TYPE_REFS);
		}
		return sourceTypeRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeRef> getTargetTypeRefs() {
		if (targetTypeRefs == null) {
			targetTypeRefs = new EObjectResolvingEList<TypeRef>(TypeRef.class, this, TypesPackage.TMIGRATION__TARGET_TYPE_REFS);
		}
		return targetTypeRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TMigratable get_principalArgumentType() {
		if (_principalArgumentType != null && _principalArgumentType.eIsProxy()) {
			InternalEObject old_principalArgumentType = (InternalEObject)_principalArgumentType;
			_principalArgumentType = (TMigratable)eResolveProxy(old_principalArgumentType);
			if (_principalArgumentType != old_principalArgumentType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.TMIGRATION__PRINCIPAL_ARGUMENT_TYPE, old_principalArgumentType, _principalArgumentType));
			}
		}
		return _principalArgumentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TMigratable basicGet_principalArgumentType() {
		return _principalArgumentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void set_principalArgumentType(TMigratable new_principalArgumentType) {
		TMigratable old_principalArgumentType = _principalArgumentType;
		_principalArgumentType = new_principalArgumentType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMIGRATION__PRINCIPAL_ARGUMENT_TYPE, old_principalArgumentType, _principalArgumentType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TMigratable getPrincipalArgumentType() {
		TMigratable __principalArgumentType = this.get_principalArgumentType();
		boolean _tripleEquals = (__principalArgumentType == null);
		if (_tripleEquals) {
			final Procedure0 _function = new Procedure0() {
				public void apply() {
					TMigrationImpl.this.set_principalArgumentType(MigratableUtils.findPrincipalMigrationArgument(TMigrationImpl.this.getSourceTypeRefs()).orElse(null));
				}
			};
			EcoreUtilN4.doWithDeliver(false, _function, this);
		}
		return this.get_principalArgumentType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMigrationAsString() {
		final Function1<TypeRef, String> _function = new Function1<TypeRef, String>() {
			public String apply(final TypeRef t) {
				return t.getTypeRefAsString();
			}
		};
		final Function1<TypeRef, String> _function_1 = new Function1<TypeRef, String>() {
			public String apply(final TypeRef t) {
				return t.getTypeRefAsString();
			}
		};
		return String.format("%s (%s) => (%s)", 
			this.getName(), 
			IterableExtensions.join(XcoreEListExtensions.<TypeRef, String>map(this.getSourceTypeRefs(), _function), ", "), 
			IterableExtensions.join(XcoreEListExtensions.<TypeRef, String>map(this.getTargetTypeRefs(), _function_1), ", "));
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
			case TypesPackage.TMIGRATION__HAS_DECLARED_SOURCE_AND_TARGET_VERSION:
				return isHasDeclaredSourceAndTargetVersion();
			case TypesPackage.TMIGRATION__SOURCE_TYPE_REFS:
				return getSourceTypeRefs();
			case TypesPackage.TMIGRATION__TARGET_TYPE_REFS:
				return getTargetTypeRefs();
			case TypesPackage.TMIGRATION__PRINCIPAL_ARGUMENT_TYPE:
				if (resolve) return get_principalArgumentType();
				return basicGet_principalArgumentType();
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
			case TypesPackage.TMIGRATION__SOURCE_VERSION:
				setSourceVersion((Integer)newValue);
				return;
			case TypesPackage.TMIGRATION__TARGET_VERSION:
				setTargetVersion((Integer)newValue);
				return;
			case TypesPackage.TMIGRATION__HAS_DECLARED_SOURCE_AND_TARGET_VERSION:
				setHasDeclaredSourceAndTargetVersion((Boolean)newValue);
				return;
			case TypesPackage.TMIGRATION__SOURCE_TYPE_REFS:
				getSourceTypeRefs().clear();
				getSourceTypeRefs().addAll((Collection<? extends TypeRef>)newValue);
				return;
			case TypesPackage.TMIGRATION__TARGET_TYPE_REFS:
				getTargetTypeRefs().clear();
				getTargetTypeRefs().addAll((Collection<? extends TypeRef>)newValue);
				return;
			case TypesPackage.TMIGRATION__PRINCIPAL_ARGUMENT_TYPE:
				set_principalArgumentType((TMigratable)newValue);
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
			case TypesPackage.TMIGRATION__HAS_DECLARED_SOURCE_AND_TARGET_VERSION:
				setHasDeclaredSourceAndTargetVersion(HAS_DECLARED_SOURCE_AND_TARGET_VERSION_EDEFAULT);
				return;
			case TypesPackage.TMIGRATION__SOURCE_TYPE_REFS:
				getSourceTypeRefs().clear();
				return;
			case TypesPackage.TMIGRATION__TARGET_TYPE_REFS:
				getTargetTypeRefs().clear();
				return;
			case TypesPackage.TMIGRATION__PRINCIPAL_ARGUMENT_TYPE:
				set_principalArgumentType((TMigratable)null);
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
			case TypesPackage.TMIGRATION__HAS_DECLARED_SOURCE_AND_TARGET_VERSION:
				return hasDeclaredSourceAndTargetVersion != HAS_DECLARED_SOURCE_AND_TARGET_VERSION_EDEFAULT;
			case TypesPackage.TMIGRATION__SOURCE_TYPE_REFS:
				return sourceTypeRefs != null && !sourceTypeRefs.isEmpty();
			case TypesPackage.TMIGRATION__TARGET_TYPE_REFS:
				return targetTypeRefs != null && !targetTypeRefs.isEmpty();
			case TypesPackage.TMIGRATION__PRINCIPAL_ARGUMENT_TYPE:
				return _principalArgumentType != null;
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
			case TypesPackage.TMIGRATION___GET_PRINCIPAL_ARGUMENT_TYPE:
				return getPrincipalArgumentType();
			case TypesPackage.TMIGRATION___GET_MIGRATION_AS_STRING:
				return getMigrationAsString();
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
		result.append(" (sourceVersion: ");
		result.append(sourceVersion);
		result.append(", targetVersion: ");
		result.append(targetVersion);
		result.append(", hasDeclaredSourceAndTargetVersion: ");
		result.append(hasDeclaredSourceAndTargetVersion);
		result.append(')');
		return result.toString();
	}

} //TMigrationImpl
