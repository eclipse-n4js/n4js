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

import com.google.common.collect.Iterables;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;

import org.eclipse.n4js.ts.types.TModule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Script</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ScriptImpl#getHashbang <em>Hashbang</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ScriptImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ScriptImpl#getScriptElements <em>Script Elements</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ScriptImpl#getModule <em>Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ScriptImpl#isFlaggedUsageMarkingFinished <em>Flagged Usage Marking Finished</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ScriptImpl extends VariableEnvironmentElementImpl implements Script {
	/**
	 * The default value of the '{@link #getHashbang() <em>Hashbang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHashbang()
	 * @generated
	 * @ordered
	 */
	protected static final String HASHBANG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHashbang() <em>Hashbang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHashbang()
	 * @generated
	 * @ordered
	 */
	protected String hashbang = HASHBANG_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<Annotation> annotations;

	/**
	 * The cached value of the '{@link #getScriptElements() <em>Script Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScriptElements()
	 * @generated
	 * @ordered
	 */
	protected EList<ScriptElement> scriptElements;

	/**
	 * The cached value of the '{@link #getModule() <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModule()
	 * @generated
	 * @ordered
	 */
	protected TModule module;

	/**
	 * The default value of the '{@link #isFlaggedUsageMarkingFinished() <em>Flagged Usage Marking Finished</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFlaggedUsageMarkingFinished()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FLAGGED_USAGE_MARKING_FINISHED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFlaggedUsageMarkingFinished() <em>Flagged Usage Marking Finished</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFlaggedUsageMarkingFinished()
	 * @generated
	 * @ordered
	 */
	protected boolean flaggedUsageMarkingFinished = FLAGGED_USAGE_MARKING_FINISHED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScriptImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.SCRIPT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getHashbang() {
		return hashbang;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHashbang(String newHashbang) {
		String oldHashbang = hashbang;
		hashbang = newHashbang;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.SCRIPT__HASHBANG, oldHashbang, hashbang));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, N4JSPackage.SCRIPT__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ScriptElement> getScriptElements() {
		if (scriptElements == null) {
			scriptElements = new EObjectContainmentEList<ScriptElement>(ScriptElement.class, this, N4JSPackage.SCRIPT__SCRIPT_ELEMENTS);
		}
		return scriptElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TModule getModule() {
		if (module != null && module.eIsProxy()) {
			InternalEObject oldModule = (InternalEObject)module;
			module = (TModule)eResolveProxy(oldModule);
			if (module != oldModule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.SCRIPT__MODULE, oldModule, module));
			}
		}
		return module;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TModule basicGetModule() {
		return module;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModule(TModule newModule) {
		TModule oldModule = module;
		module = newModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.SCRIPT__MODULE, oldModule, module));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFlaggedUsageMarkingFinished() {
		return flaggedUsageMarkingFinished;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFlaggedUsageMarkingFinished(boolean newFlaggedUsageMarkingFinished) {
		boolean oldFlaggedUsageMarkingFinished = flaggedUsageMarkingFinished;
		flaggedUsageMarkingFinished = newFlaggedUsageMarkingFinished;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.SCRIPT__FLAGGED_USAGE_MARKING_FINISHED, oldFlaggedUsageMarkingFinished, flaggedUsageMarkingFinished));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Annotation> getAllAnnotations() {
		final BasicEList<Annotation> result = XcoreCollectionLiterals.<Annotation>newBasicEList();
		final EObject parent = this.eContainer();
		if ((parent instanceof ExportDeclaration)) {
			EList<Annotation> _annotations = ((ExportDeclaration)parent).getAnnotations();
			Iterables.<Annotation>addAll(result, _annotations);
		}
		EList<Annotation> _annotations_1 = this.getAnnotations();
		Iterables.<Annotation>addAll(result, _annotations_1);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.SCRIPT__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case N4JSPackage.SCRIPT__SCRIPT_ELEMENTS:
				return ((InternalEList<?>)getScriptElements()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.SCRIPT__HASHBANG:
				return getHashbang();
			case N4JSPackage.SCRIPT__ANNOTATIONS:
				return getAnnotations();
			case N4JSPackage.SCRIPT__SCRIPT_ELEMENTS:
				return getScriptElements();
			case N4JSPackage.SCRIPT__MODULE:
				if (resolve) return getModule();
				return basicGetModule();
			case N4JSPackage.SCRIPT__FLAGGED_USAGE_MARKING_FINISHED:
				return isFlaggedUsageMarkingFinished();
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
			case N4JSPackage.SCRIPT__HASHBANG:
				setHashbang((String)newValue);
				return;
			case N4JSPackage.SCRIPT__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case N4JSPackage.SCRIPT__SCRIPT_ELEMENTS:
				getScriptElements().clear();
				getScriptElements().addAll((Collection<? extends ScriptElement>)newValue);
				return;
			case N4JSPackage.SCRIPT__MODULE:
				setModule((TModule)newValue);
				return;
			case N4JSPackage.SCRIPT__FLAGGED_USAGE_MARKING_FINISHED:
				setFlaggedUsageMarkingFinished((Boolean)newValue);
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
			case N4JSPackage.SCRIPT__HASHBANG:
				setHashbang(HASHBANG_EDEFAULT);
				return;
			case N4JSPackage.SCRIPT__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case N4JSPackage.SCRIPT__SCRIPT_ELEMENTS:
				getScriptElements().clear();
				return;
			case N4JSPackage.SCRIPT__MODULE:
				setModule((TModule)null);
				return;
			case N4JSPackage.SCRIPT__FLAGGED_USAGE_MARKING_FINISHED:
				setFlaggedUsageMarkingFinished(FLAGGED_USAGE_MARKING_FINISHED_EDEFAULT);
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
			case N4JSPackage.SCRIPT__HASHBANG:
				return HASHBANG_EDEFAULT == null ? hashbang != null : !HASHBANG_EDEFAULT.equals(hashbang);
			case N4JSPackage.SCRIPT__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case N4JSPackage.SCRIPT__SCRIPT_ELEMENTS:
				return scriptElements != null && !scriptElements.isEmpty();
			case N4JSPackage.SCRIPT__MODULE:
				return module != null;
			case N4JSPackage.SCRIPT__FLAGGED_USAGE_MARKING_FINISHED:
				return flaggedUsageMarkingFinished != FLAGGED_USAGE_MARKING_FINISHED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == AnnotableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.ANNOTABLE_ELEMENT___GET_ANNOTATIONS: return N4JSPackage.SCRIPT___GET_ANNOTATIONS;
				case N4JSPackage.ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS: return N4JSPackage.SCRIPT___GET_ALL_ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == ControlFlowElement.class) {
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
			case N4JSPackage.SCRIPT___GET_ALL_ANNOTATIONS:
				return getAllAnnotations();
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
		result.append(" (hashbang: ");
		result.append(hashbang);
		result.append(", flaggedUsageMarkingFinished: ");
		result.append(flaggedUsageMarkingFinished);
		result.append(')');
		return result.toString();
	}

} //ScriptImpl
