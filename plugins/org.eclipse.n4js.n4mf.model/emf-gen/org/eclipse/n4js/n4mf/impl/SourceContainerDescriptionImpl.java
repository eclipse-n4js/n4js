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
package org.eclipse.n4js.n4mf.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;

import org.eclipse.n4js.utils.io.FileUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source Container Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.SourceContainerDescriptionImpl#getSourceContainerType <em>Source Container Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.SourceContainerDescriptionImpl#getPathsRaw <em>Paths Raw</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SourceContainerDescriptionImpl extends MinimalEObjectImpl.Container implements SourceContainerDescription {
	/**
	 * The default value of the '{@link #getSourceContainerType() <em>Source Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceContainerType()
	 * @generated
	 * @ordered
	 */
	protected static final SourceContainerType SOURCE_CONTAINER_TYPE_EDEFAULT = SourceContainerType.SOURCE;

	/**
	 * The cached value of the '{@link #getSourceContainerType() <em>Source Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceContainerType()
	 * @generated
	 * @ordered
	 */
	protected SourceContainerType sourceContainerType = SOURCE_CONTAINER_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPathsRaw() <em>Paths Raw</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPathsRaw()
	 * @generated
	 * @ordered
	 */
	protected EList<String> pathsRaw;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SourceContainerDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.SOURCE_CONTAINER_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceContainerType getSourceContainerType() {
		return sourceContainerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceContainerType(SourceContainerType newSourceContainerType) {
		SourceContainerType oldSourceContainerType = sourceContainerType;
		sourceContainerType = newSourceContainerType == null ? SOURCE_CONTAINER_TYPE_EDEFAULT : newSourceContainerType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE, oldSourceContainerType, sourceContainerType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPathsRaw() {
		if (pathsRaw == null) {
			pathsRaw = new EDataTypeEList<String>(String.class, this, N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW);
		}
		return pathsRaw;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int compareByFragmentType(final SourceContainerDescription other) {
		if ((null == other)) {
			return (-1);
		}
		SourceContainerType _sourceContainerType = this.getSourceContainerType();
		boolean _tripleEquals = (null == _sourceContainerType);
		if (_tripleEquals) {
			int _xifexpression = (int) 0;
			SourceContainerType _sourceContainerType_1 = other.getSourceContainerType();
			boolean _tripleEquals_1 = (null == _sourceContainerType_1);
			if (_tripleEquals_1) {
				_xifexpression = 0;
			}
			else {
				_xifexpression = 1;
			}
			return _xifexpression;
		}
		return this.getSourceContainerType().compareTo(other.getSourceContainerType());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPaths() {
		int _length = ((Object[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(this.getPathsRaw(), Object.class)).length;
		final BasicEList<String> paths = new BasicEList<String>(_length);
		EList<String> _pathsRaw = this.getPathsRaw();
		for (final String pathRaw : _pathsRaw) {
			{
				final String normalizedPath = FileUtils.normalizeDotWhenEmpty(pathRaw);
				paths.add(normalizedPath);
			}
		}
		return paths;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE:
				return getSourceContainerType();
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW:
				return getPathsRaw();
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
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE:
				setSourceContainerType((SourceContainerType)newValue);
				return;
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW:
				getPathsRaw().clear();
				getPathsRaw().addAll((Collection<? extends String>)newValue);
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
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE:
				setSourceContainerType(SOURCE_CONTAINER_TYPE_EDEFAULT);
				return;
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW:
				getPathsRaw().clear();
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
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE:
				return sourceContainerType != SOURCE_CONTAINER_TYPE_EDEFAULT;
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW:
				return pathsRaw != null && !pathsRaw.isEmpty();
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
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION___COMPARE_BY_FRAGMENT_TYPE__SOURCECONTAINERDESCRIPTION:
				return compareByFragmentType((SourceContainerDescription)arguments.get(0));
			case N4mfPackage.SOURCE_CONTAINER_DESCRIPTION___GET_PATHS:
				return getPaths();
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
		result.append(" (sourceContainerType: ");
		result.append(sourceContainerType);
		result.append(", pathsRaw: ");
		result.append(pathsRaw);
		result.append(')');
		return result.toString();
	}

} //SourceContainerDescriptionImpl
