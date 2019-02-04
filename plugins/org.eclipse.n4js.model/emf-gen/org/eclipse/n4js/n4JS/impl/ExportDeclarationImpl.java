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
package org.eclipse.n4js.n4JS.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportSpecifier;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.ts.types.TModule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Export Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getExportedElement <em>Exported Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getDefaultExportedExpression <em>Default Exported Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getNamedExports <em>Named Exports</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#isWildcardExport <em>Wildcard Export</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#isDefaultExport <em>Default Export</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getReexportedFrom <em>Reexported From</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExportDeclarationImpl extends AnnotableScriptElementImpl implements ExportDeclaration {
	/**
	 * The cached value of the '{@link #getExportedElement() <em>Exported Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedElement()
	 * @generated
	 * @ordered
	 */
	protected ExportableElement exportedElement;

	/**
	 * The cached value of the '{@link #getDefaultExportedExpression() <em>Default Exported Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultExportedExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression defaultExportedExpression;

	/**
	 * The cached value of the '{@link #getNamedExports() <em>Named Exports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedExports()
	 * @generated
	 * @ordered
	 */
	protected EList<ExportSpecifier> namedExports;

	/**
	 * The default value of the '{@link #isWildcardExport() <em>Wildcard Export</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWildcardExport()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WILDCARD_EXPORT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWildcardExport() <em>Wildcard Export</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWildcardExport()
	 * @generated
	 * @ordered
	 */
	protected boolean wildcardExport = WILDCARD_EXPORT_EDEFAULT;

	/**
	 * The default value of the '{@link #isDefaultExport() <em>Default Export</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefaultExport()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEFAULT_EXPORT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDefaultExport() <em>Default Export</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefaultExport()
	 * @generated
	 * @ordered
	 */
	protected boolean defaultExport = DEFAULT_EXPORT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReexportedFrom() <em>Reexported From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReexportedFrom()
	 * @generated
	 * @ordered
	 */
	protected TModule reexportedFrom;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExportDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.EXPORT_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExportableElement getExportedElement() {
		return exportedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExportedElement(ExportableElement newExportedElement, NotificationChain msgs) {
		ExportableElement oldExportedElement = exportedElement;
		exportedElement = newExportedElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT, oldExportedElement, newExportedElement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExportedElement(ExportableElement newExportedElement) {
		if (newExportedElement != exportedElement) {
			NotificationChain msgs = null;
			if (exportedElement != null)
				msgs = ((InternalEObject)exportedElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT, null, msgs);
			if (newExportedElement != null)
				msgs = ((InternalEObject)newExportedElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT, null, msgs);
			msgs = basicSetExportedElement(newExportedElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT, newExportedElement, newExportedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getDefaultExportedExpression() {
		return defaultExportedExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefaultExportedExpression(Expression newDefaultExportedExpression, NotificationChain msgs) {
		Expression oldDefaultExportedExpression = defaultExportedExpression;
		defaultExportedExpression = newDefaultExportedExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION, oldDefaultExportedExpression, newDefaultExportedExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefaultExportedExpression(Expression newDefaultExportedExpression) {
		if (newDefaultExportedExpression != defaultExportedExpression) {
			NotificationChain msgs = null;
			if (defaultExportedExpression != null)
				msgs = ((InternalEObject)defaultExportedExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION, null, msgs);
			if (newDefaultExportedExpression != null)
				msgs = ((InternalEObject)newDefaultExportedExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION, null, msgs);
			msgs = basicSetDefaultExportedExpression(newDefaultExportedExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION, newDefaultExportedExpression, newDefaultExportedExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ExportSpecifier> getNamedExports() {
		if (namedExports == null) {
			namedExports = new EObjectContainmentEList<ExportSpecifier>(ExportSpecifier.class, this, N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS);
		}
		return namedExports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWildcardExport() {
		return wildcardExport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWildcardExport(boolean newWildcardExport) {
		boolean oldWildcardExport = wildcardExport;
		wildcardExport = newWildcardExport;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__WILDCARD_EXPORT, oldWildcardExport, wildcardExport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDefaultExport() {
		return defaultExport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefaultExport(boolean newDefaultExport) {
		boolean oldDefaultExport = defaultExport;
		defaultExport = newDefaultExport;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT, oldDefaultExport, defaultExport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TModule getReexportedFrom() {
		if (reexportedFrom != null && reexportedFrom.eIsProxy()) {
			InternalEObject oldReexportedFrom = (InternalEObject)reexportedFrom;
			reexportedFrom = (TModule)eResolveProxy(oldReexportedFrom);
			if (reexportedFrom != oldReexportedFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.EXPORT_DECLARATION__REEXPORTED_FROM, oldReexportedFrom, reexportedFrom));
			}
		}
		return reexportedFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TModule basicGetReexportedFrom() {
		return reexportedFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReexportedFrom(TModule newReexportedFrom) {
		TModule oldReexportedFrom = reexportedFrom;
		reexportedFrom = newReexportedFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__REEXPORTED_FROM, oldReexportedFrom, reexportedFrom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				return basicSetExportedElement(null, msgs);
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				return basicSetDefaultExportedExpression(null, msgs);
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				return ((InternalEList<?>)getNamedExports()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				return getExportedElement();
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				return getDefaultExportedExpression();
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				return getNamedExports();
			case N4JSPackage.EXPORT_DECLARATION__WILDCARD_EXPORT:
				return isWildcardExport();
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT:
				return isDefaultExport();
			case N4JSPackage.EXPORT_DECLARATION__REEXPORTED_FROM:
				if (resolve) return getReexportedFrom();
				return basicGetReexportedFrom();
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
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				setExportedElement((ExportableElement)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				setDefaultExportedExpression((Expression)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				getNamedExports().clear();
				getNamedExports().addAll((Collection<? extends ExportSpecifier>)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__WILDCARD_EXPORT:
				setWildcardExport((Boolean)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT:
				setDefaultExport((Boolean)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__REEXPORTED_FROM:
				setReexportedFrom((TModule)newValue);
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
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				setExportedElement((ExportableElement)null);
				return;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				setDefaultExportedExpression((Expression)null);
				return;
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				getNamedExports().clear();
				return;
			case N4JSPackage.EXPORT_DECLARATION__WILDCARD_EXPORT:
				setWildcardExport(WILDCARD_EXPORT_EDEFAULT);
				return;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT:
				setDefaultExport(DEFAULT_EXPORT_EDEFAULT);
				return;
			case N4JSPackage.EXPORT_DECLARATION__REEXPORTED_FROM:
				setReexportedFrom((TModule)null);
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
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				return exportedElement != null;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				return defaultExportedExpression != null;
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				return namedExports != null && !namedExports.isEmpty();
			case N4JSPackage.EXPORT_DECLARATION__WILDCARD_EXPORT:
				return wildcardExport != WILDCARD_EXPORT_EDEFAULT;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT:
				return defaultExport != DEFAULT_EXPORT_EDEFAULT;
			case N4JSPackage.EXPORT_DECLARATION__REEXPORTED_FROM:
				return reexportedFrom != null;
		}
		return super.eIsSet(featureID);
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
		result.append(" (wildcardExport: ");
		result.append(wildcardExport);
		result.append(", defaultExport: ");
		result.append(defaultExport);
		result.append(')');
		return result.toString();
	}

} //ExportDeclarationImpl
