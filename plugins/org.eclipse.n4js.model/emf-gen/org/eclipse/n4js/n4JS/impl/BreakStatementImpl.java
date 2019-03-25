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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.LabelRef;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.N4JSPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Break Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BreakStatementImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BreakStatementImpl#getLabelAsText <em>Label As Text</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BreakStatementImpl extends StatementImpl implements BreakStatement {
	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected LabelledStatement label;

	/**
	 * The default value of the '{@link #getLabelAsText() <em>Label As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelAsText()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_AS_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabelAsText() <em>Label As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelAsText()
	 * @generated
	 * @ordered
	 */
	protected String labelAsText = LABEL_AS_TEXT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BreakStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.BREAK_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LabelledStatement getLabel() {
		if (label != null && label.eIsProxy()) {
			InternalEObject oldLabel = (InternalEObject)label;
			label = (LabelledStatement)eResolveProxy(oldLabel);
			if (label != oldLabel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.BREAK_STATEMENT__LABEL, oldLabel, label));
			}
		}
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LabelledStatement basicGetLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(LabelledStatement newLabel) {
		LabelledStatement oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BREAK_STATEMENT__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabelAsText() {
		return labelAsText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabelAsText(String newLabelAsText) {
		String oldLabelAsText = labelAsText;
		labelAsText = newLabelAsText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BREAK_STATEMENT__LABEL_AS_TEXT, oldLabelAsText, labelAsText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.BREAK_STATEMENT__LABEL:
				if (resolve) return getLabel();
				return basicGetLabel();
			case N4JSPackage.BREAK_STATEMENT__LABEL_AS_TEXT:
				return getLabelAsText();
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
			case N4JSPackage.BREAK_STATEMENT__LABEL:
				setLabel((LabelledStatement)newValue);
				return;
			case N4JSPackage.BREAK_STATEMENT__LABEL_AS_TEXT:
				setLabelAsText((String)newValue);
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
			case N4JSPackage.BREAK_STATEMENT__LABEL:
				setLabel((LabelledStatement)null);
				return;
			case N4JSPackage.BREAK_STATEMENT__LABEL_AS_TEXT:
				setLabelAsText(LABEL_AS_TEXT_EDEFAULT);
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
			case N4JSPackage.BREAK_STATEMENT__LABEL:
				return label != null;
			case N4JSPackage.BREAK_STATEMENT__LABEL_AS_TEXT:
				return LABEL_AS_TEXT_EDEFAULT == null ? labelAsText != null : !LABEL_AS_TEXT_EDEFAULT.equals(labelAsText);
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
		if (baseClass == LabelRef.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.BREAK_STATEMENT__LABEL: return N4JSPackage.LABEL_REF__LABEL;
				case N4JSPackage.BREAK_STATEMENT__LABEL_AS_TEXT: return N4JSPackage.LABEL_REF__LABEL_AS_TEXT;
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
		if (baseClass == LabelRef.class) {
			switch (baseFeatureID) {
				case N4JSPackage.LABEL_REF__LABEL: return N4JSPackage.BREAK_STATEMENT__LABEL;
				case N4JSPackage.LABEL_REF__LABEL_AS_TEXT: return N4JSPackage.BREAK_STATEMENT__LABEL_AS_TEXT;
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
		result.append(" (labelAsText: ");
		result.append(labelAsText);
		result.append(')');
		return result.toString();
	}

} //BreakStatementImpl
