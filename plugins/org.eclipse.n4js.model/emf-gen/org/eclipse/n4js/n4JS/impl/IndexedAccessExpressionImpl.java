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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.ts.types.ComposedMemberCache;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Indexed Access Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.IndexedAccessExpressionImpl#getComposedMemberCache <em>Composed Member Cache</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.IndexedAccessExpressionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.IndexedAccessExpressionImpl#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IndexedAccessExpressionImpl extends ExpressionImpl implements IndexedAccessExpression {
	/**
	 * The cached value of the '{@link #getComposedMemberCache() <em>Composed Member Cache</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComposedMemberCache()
	 * @generated
	 * @ordered
	 */
	protected ComposedMemberCache composedMemberCache;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Expression target;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected Expression index;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IndexedAccessExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.INDEXED_ACCESS_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComposedMemberCache getComposedMemberCache() {
		if (composedMemberCache != null && composedMemberCache.eIsProxy()) {
			InternalEObject oldComposedMemberCache = (InternalEObject)composedMemberCache;
			composedMemberCache = (ComposedMemberCache)eResolveProxy(oldComposedMemberCache);
			if (composedMemberCache != oldComposedMemberCache) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.INDEXED_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
			}
		}
		return composedMemberCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposedMemberCache basicGetComposedMemberCache() {
		return composedMemberCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComposedMemberCache(ComposedMemberCache newComposedMemberCache) {
		ComposedMemberCache oldComposedMemberCache = composedMemberCache;
		composedMemberCache = newComposedMemberCache;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.INDEXED_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Expression newTarget, NotificationChain msgs) {
		Expression oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.INDEXED_ACCESS_EXPRESSION__TARGET, oldTarget, newTarget);
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
	public void setTarget(Expression newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.INDEXED_ACCESS_EXPRESSION__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.INDEXED_ACCESS_EXPRESSION__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.INDEXED_ACCESS_EXPRESSION__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndex(Expression newIndex, NotificationChain msgs) {
		Expression oldIndex = index;
		index = newIndex;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.INDEXED_ACCESS_EXPRESSION__INDEX, oldIndex, newIndex);
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
	public void setIndex(Expression newIndex) {
		if (newIndex != index) {
			NotificationChain msgs = null;
			if (index != null)
				msgs = ((InternalEObject)index).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.INDEXED_ACCESS_EXPRESSION__INDEX, null, msgs);
			if (newIndex != null)
				msgs = ((InternalEObject)newIndex).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.INDEXED_ACCESS_EXPRESSION__INDEX, null, msgs);
			msgs = basicSetIndex(newIndex, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.INDEXED_ACCESS_EXPRESSION__INDEX, newIndex, newIndex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isValidSimpleAssignmentTarget() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__TARGET:
				return basicSetTarget(null, msgs);
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__INDEX:
				return basicSetIndex(null, msgs);
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
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE:
				if (resolve) return getComposedMemberCache();
				return basicGetComposedMemberCache();
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__TARGET:
				return getTarget();
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__INDEX:
				return getIndex();
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
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)newValue);
				return;
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__TARGET:
				setTarget((Expression)newValue);
				return;
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__INDEX:
				setIndex((Expression)newValue);
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
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)null);
				return;
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__TARGET:
				setTarget((Expression)null);
				return;
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__INDEX:
				setIndex((Expression)null);
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
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE:
				return composedMemberCache != null;
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__TARGET:
				return target != null;
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION__INDEX:
				return index != null;
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
		if (baseClass == MemberAccess.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.INDEXED_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE: return N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE;
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
		if (baseClass == MemberAccess.class) {
			switch (baseFeatureID) {
				case N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE: return N4JSPackage.INDEXED_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE;
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
		if (baseClass == Expression.class) {
			switch (baseOperationID) {
				case N4JSPackage.EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET: return N4JSPackage.INDEXED_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == MemberAccess.class) {
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
			case N4JSPackage.INDEXED_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET:
				return isValidSimpleAssignmentTarget();
		}
		return super.eInvoke(operationID, arguments);
	}

} //IndexedAccessExpressionImpl
