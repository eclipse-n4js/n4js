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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TClass</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TClassImpl#isExternal <em>External</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TClassImpl#isDeclaredAbstract <em>Declared Abstract</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TClassImpl#isDeclaredN4JS <em>Declared N4JS</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TClassImpl#isDeclaredFinal <em>Declared Final</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TClassImpl#isDeclaredPolyfill <em>Declared Polyfill</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TClassImpl#isDeclaredStaticPolyfill <em>Declared Static Polyfill</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TClassImpl#isObservable <em>Observable</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TClassImpl#getSuperClassRef <em>Super Class Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TClassImpl#getImplementedInterfaceRefs <em>Implemented Interface Refs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TClassImpl extends TN4ClassifierImpl implements TClass {
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
	 * The default value of the '{@link #isDeclaredAbstract() <em>Declared Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredAbstract() <em>Declared Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredAbstract = DECLARED_ABSTRACT_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredN4JS() <em>Declared N4JS</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredN4JS()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_N4JS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredN4JS() <em>Declared N4JS</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredN4JS()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredN4JS = DECLARED_N4JS_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredFinal() <em>Declared Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredFinal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_FINAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredFinal() <em>Declared Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredFinal()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredFinal = DECLARED_FINAL_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredPolyfill() <em>Declared Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredPolyfill()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_POLYFILL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredPolyfill() <em>Declared Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredPolyfill()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredPolyfill = DECLARED_POLYFILL_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredStaticPolyfill() <em>Declared Static Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredStaticPolyfill()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_STATIC_POLYFILL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredStaticPolyfill() <em>Declared Static Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredStaticPolyfill()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredStaticPolyfill = DECLARED_STATIC_POLYFILL_EDEFAULT;

	/**
	 * The default value of the '{@link #isObservable() <em>Observable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isObservable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OBSERVABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isObservable() <em>Observable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isObservable()
	 * @generated
	 * @ordered
	 */
	protected boolean observable = OBSERVABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSuperClassRef() <em>Super Class Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperClassRef()
	 * @generated
	 * @ordered
	 */
	protected ParameterizedTypeRef superClassRef;

	/**
	 * The cached value of the '{@link #getImplementedInterfaceRefs() <em>Implemented Interface Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementedInterfaceRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterizedTypeRef> implementedInterfaceRefs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TCLASS;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCLASS__EXTERNAL, oldExternal, external));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredAbstract() {
		return declaredAbstract;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredAbstract(boolean newDeclaredAbstract) {
		boolean oldDeclaredAbstract = declaredAbstract;
		declaredAbstract = newDeclaredAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCLASS__DECLARED_ABSTRACT, oldDeclaredAbstract, declaredAbstract));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredN4JS() {
		return declaredN4JS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredN4JS(boolean newDeclaredN4JS) {
		boolean oldDeclaredN4JS = declaredN4JS;
		declaredN4JS = newDeclaredN4JS;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCLASS__DECLARED_N4JS, oldDeclaredN4JS, declaredN4JS));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredFinal() {
		return declaredFinal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredFinal(boolean newDeclaredFinal) {
		boolean oldDeclaredFinal = declaredFinal;
		declaredFinal = newDeclaredFinal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCLASS__DECLARED_FINAL, oldDeclaredFinal, declaredFinal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredPolyfill() {
		return declaredPolyfill;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredPolyfill(boolean newDeclaredPolyfill) {
		boolean oldDeclaredPolyfill = declaredPolyfill;
		declaredPolyfill = newDeclaredPolyfill;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCLASS__DECLARED_POLYFILL, oldDeclaredPolyfill, declaredPolyfill));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredStaticPolyfill() {
		return declaredStaticPolyfill;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredStaticPolyfill(boolean newDeclaredStaticPolyfill) {
		boolean oldDeclaredStaticPolyfill = declaredStaticPolyfill;
		declaredStaticPolyfill = newDeclaredStaticPolyfill;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCLASS__DECLARED_STATIC_POLYFILL, oldDeclaredStaticPolyfill, declaredStaticPolyfill));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isObservable() {
		return observable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObservable(boolean newObservable) {
		boolean oldObservable = observable;
		observable = newObservable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCLASS__OBSERVABLE, oldObservable, observable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRef getSuperClassRef() {
		return superClassRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSuperClassRef(ParameterizedTypeRef newSuperClassRef, NotificationChain msgs) {
		ParameterizedTypeRef oldSuperClassRef = superClassRef;
		superClassRef = newSuperClassRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TCLASS__SUPER_CLASS_REF, oldSuperClassRef, newSuperClassRef);
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
	public void setSuperClassRef(ParameterizedTypeRef newSuperClassRef) {
		if (newSuperClassRef != superClassRef) {
			NotificationChain msgs = null;
			if (superClassRef != null)
				msgs = ((InternalEObject)superClassRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TCLASS__SUPER_CLASS_REF, null, msgs);
			if (newSuperClassRef != null)
				msgs = ((InternalEObject)newSuperClassRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TCLASS__SUPER_CLASS_REF, null, msgs);
			msgs = basicSetSuperClassRef(newSuperClassRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCLASS__SUPER_CLASS_REF, newSuperClassRef, newSuperClassRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ParameterizedTypeRef> getImplementedInterfaceRefs() {
		if (implementedInterfaceRefs == null) {
			implementedInterfaceRefs = new EObjectContainmentEList<ParameterizedTypeRef>(ParameterizedTypeRef.class, this, TypesPackage.TCLASS__IMPLEMENTED_INTERFACE_REFS);
		}
		return implementedInterfaceRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbstract() {
		return this.isDeclaredAbstract();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TClass getSuperClass() {
		ParameterizedTypeRef _superClassRef = this.getSuperClassRef();
		Type _declaredType = null;
		if (_superClassRef!=null) {
			_declaredType=_superClassRef.getDeclaredType();
		}
		final Type superType = _declaredType;
		TClass _xifexpression = null;
		if ((superType instanceof TClass)) {
			_xifexpression = ((TClass)superType);
		}
		else {
			_xifexpression = null;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<ParameterizedTypeRef> getSuperClassifierRefs() {
		ParameterizedTypeRef _superClassRef = this.getSuperClassRef();
		boolean _tripleNotEquals = (_superClassRef != null);
		if (_tripleNotEquals) {
			Set<ParameterizedTypeRef> _singleton = Collections.<ParameterizedTypeRef>singleton(this.getSuperClassRef());
			EList<ParameterizedTypeRef> _implementedInterfaceRefs = this.getImplementedInterfaceRefs();
			return Iterables.<ParameterizedTypeRef>concat(_singleton, _implementedInterfaceRefs);
		}
		return this.getImplementedInterfaceRefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<ParameterizedTypeRef> getImplementedOrExtendedInterfaceRefs() {
		return this.getImplementedInterfaceRefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPolyfill() {
		return this.isDeclaredPolyfill();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStaticPolyfill() {
		return this.isDeclaredStaticPolyfill();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFinal() {
		return this.isDeclaredFinal();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TCLASS__SUPER_CLASS_REF:
				return basicSetSuperClassRef(null, msgs);
			case TypesPackage.TCLASS__IMPLEMENTED_INTERFACE_REFS:
				return ((InternalEList<?>)getImplementedInterfaceRefs()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.TCLASS__EXTERNAL:
				return isExternal();
			case TypesPackage.TCLASS__DECLARED_ABSTRACT:
				return isDeclaredAbstract();
			case TypesPackage.TCLASS__DECLARED_N4JS:
				return isDeclaredN4JS();
			case TypesPackage.TCLASS__DECLARED_FINAL:
				return isDeclaredFinal();
			case TypesPackage.TCLASS__DECLARED_POLYFILL:
				return isDeclaredPolyfill();
			case TypesPackage.TCLASS__DECLARED_STATIC_POLYFILL:
				return isDeclaredStaticPolyfill();
			case TypesPackage.TCLASS__OBSERVABLE:
				return isObservable();
			case TypesPackage.TCLASS__SUPER_CLASS_REF:
				return getSuperClassRef();
			case TypesPackage.TCLASS__IMPLEMENTED_INTERFACE_REFS:
				return getImplementedInterfaceRefs();
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
			case TypesPackage.TCLASS__EXTERNAL:
				setExternal((Boolean)newValue);
				return;
			case TypesPackage.TCLASS__DECLARED_ABSTRACT:
				setDeclaredAbstract((Boolean)newValue);
				return;
			case TypesPackage.TCLASS__DECLARED_N4JS:
				setDeclaredN4JS((Boolean)newValue);
				return;
			case TypesPackage.TCLASS__DECLARED_FINAL:
				setDeclaredFinal((Boolean)newValue);
				return;
			case TypesPackage.TCLASS__DECLARED_POLYFILL:
				setDeclaredPolyfill((Boolean)newValue);
				return;
			case TypesPackage.TCLASS__DECLARED_STATIC_POLYFILL:
				setDeclaredStaticPolyfill((Boolean)newValue);
				return;
			case TypesPackage.TCLASS__OBSERVABLE:
				setObservable((Boolean)newValue);
				return;
			case TypesPackage.TCLASS__SUPER_CLASS_REF:
				setSuperClassRef((ParameterizedTypeRef)newValue);
				return;
			case TypesPackage.TCLASS__IMPLEMENTED_INTERFACE_REFS:
				getImplementedInterfaceRefs().clear();
				getImplementedInterfaceRefs().addAll((Collection<? extends ParameterizedTypeRef>)newValue);
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
			case TypesPackage.TCLASS__EXTERNAL:
				setExternal(EXTERNAL_EDEFAULT);
				return;
			case TypesPackage.TCLASS__DECLARED_ABSTRACT:
				setDeclaredAbstract(DECLARED_ABSTRACT_EDEFAULT);
				return;
			case TypesPackage.TCLASS__DECLARED_N4JS:
				setDeclaredN4JS(DECLARED_N4JS_EDEFAULT);
				return;
			case TypesPackage.TCLASS__DECLARED_FINAL:
				setDeclaredFinal(DECLARED_FINAL_EDEFAULT);
				return;
			case TypesPackage.TCLASS__DECLARED_POLYFILL:
				setDeclaredPolyfill(DECLARED_POLYFILL_EDEFAULT);
				return;
			case TypesPackage.TCLASS__DECLARED_STATIC_POLYFILL:
				setDeclaredStaticPolyfill(DECLARED_STATIC_POLYFILL_EDEFAULT);
				return;
			case TypesPackage.TCLASS__OBSERVABLE:
				setObservable(OBSERVABLE_EDEFAULT);
				return;
			case TypesPackage.TCLASS__SUPER_CLASS_REF:
				setSuperClassRef((ParameterizedTypeRef)null);
				return;
			case TypesPackage.TCLASS__IMPLEMENTED_INTERFACE_REFS:
				getImplementedInterfaceRefs().clear();
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
			case TypesPackage.TCLASS__EXTERNAL:
				return external != EXTERNAL_EDEFAULT;
			case TypesPackage.TCLASS__DECLARED_ABSTRACT:
				return declaredAbstract != DECLARED_ABSTRACT_EDEFAULT;
			case TypesPackage.TCLASS__DECLARED_N4JS:
				return declaredN4JS != DECLARED_N4JS_EDEFAULT;
			case TypesPackage.TCLASS__DECLARED_FINAL:
				return declaredFinal != DECLARED_FINAL_EDEFAULT;
			case TypesPackage.TCLASS__DECLARED_POLYFILL:
				return declaredPolyfill != DECLARED_POLYFILL_EDEFAULT;
			case TypesPackage.TCLASS__DECLARED_STATIC_POLYFILL:
				return declaredStaticPolyfill != DECLARED_STATIC_POLYFILL_EDEFAULT;
			case TypesPackage.TCLASS__OBSERVABLE:
				return observable != OBSERVABLE_EDEFAULT;
			case TypesPackage.TCLASS__SUPER_CLASS_REF:
				return superClassRef != null;
			case TypesPackage.TCLASS__IMPLEMENTED_INTERFACE_REFS:
				return implementedInterfaceRefs != null && !implementedInterfaceRefs.isEmpty();
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
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___IS_POLYFILL: return TypesPackage.TCLASS___IS_POLYFILL;
				case TypesPackage.TYPE___IS_STATIC_POLYFILL: return TypesPackage.TCLASS___IS_STATIC_POLYFILL;
				case TypesPackage.TYPE___IS_FINAL: return TypesPackage.TCLASS___IS_FINAL;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TClassifier.class) {
			switch (baseOperationID) {
				case TypesPackage.TCLASSIFIER___IS_ABSTRACT: return TypesPackage.TCLASS___IS_ABSTRACT;
				case TypesPackage.TCLASSIFIER___GET_SUPER_CLASSIFIER_REFS: return TypesPackage.TCLASS___GET_SUPER_CLASSIFIER_REFS;
				case TypesPackage.TCLASSIFIER___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS: return TypesPackage.TCLASS___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS;
				case TypesPackage.TCLASSIFIER___IS_FINAL: return TypesPackage.TCLASS___IS_FINAL;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
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
			case TypesPackage.TCLASS___IS_ABSTRACT:
				return isAbstract();
			case TypesPackage.TCLASS___GET_SUPER_CLASS:
				return getSuperClass();
			case TypesPackage.TCLASS___GET_SUPER_CLASSIFIER_REFS:
				return getSuperClassifierRefs();
			case TypesPackage.TCLASS___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS:
				return getImplementedOrExtendedInterfaceRefs();
			case TypesPackage.TCLASS___IS_POLYFILL:
				return isPolyfill();
			case TypesPackage.TCLASS___IS_STATIC_POLYFILL:
				return isStaticPolyfill();
			case TypesPackage.TCLASS___IS_FINAL:
				return isFinal();
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
		result.append(" (external: ");
		result.append(external);
		result.append(", declaredAbstract: ");
		result.append(declaredAbstract);
		result.append(", declaredN4JS: ");
		result.append(declaredN4JS);
		result.append(", declaredFinal: ");
		result.append(declaredFinal);
		result.append(", declaredPolyfill: ");
		result.append(declaredPolyfill);
		result.append(", declaredStaticPolyfill: ");
		result.append(declaredStaticPolyfill);
		result.append(", observable: ");
		result.append(observable);
		result.append(')');
		return result.toString();
	}

} //TClassImpl
