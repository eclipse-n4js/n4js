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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.AnnotableExpression;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionAnnotationList;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedElement;

import org.eclipse.n4js.ts.types.TypeVariable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionExpressionImpl#getAnnotationList <em>Annotation List</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionExpressionImpl#getTypeVars <em>Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionExpressionImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionExpressionImpl extends FunctionDefinitionImpl implements FunctionExpression {
	/**
	 * The cached value of the '{@link #getAnnotationList() <em>Annotation List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotationList()
	 * @generated
	 * @ordered
	 */
	protected ExpressionAnnotationList annotationList;

	/**
	 * The cached value of the '{@link #getTypeVars() <em>Type Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeVars()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeVariable> typeVars;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.FUNCTION_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionAnnotationList getAnnotationList() {
		return annotationList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAnnotationList(ExpressionAnnotationList newAnnotationList, NotificationChain msgs) {
		ExpressionAnnotationList oldAnnotationList = annotationList;
		annotationList = newAnnotationList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST, oldAnnotationList, newAnnotationList);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnnotationList(ExpressionAnnotationList newAnnotationList) {
		if (newAnnotationList != annotationList) {
			NotificationChain msgs = null;
			if (annotationList != null)
				msgs = ((InternalEObject)annotationList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST, null, msgs);
			if (newAnnotationList != null)
				msgs = ((InternalEObject)newAnnotationList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST, null, msgs);
			msgs = basicSetAnnotationList(newAnnotationList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST, newAnnotationList, newAnnotationList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeVariable> getTypeVars() {
		if (typeVars == null) {
			typeVars = new EObjectContainmentEList<TypeVariable>(TypeVariable.class, this, N4JSPackage.FUNCTION_EXPRESSION__TYPE_VARS);
		}
		return typeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_EXPRESSION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArrowFunction() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		EList<Annotation> _elvis = null;
		ExpressionAnnotationList _annotationList = this.getAnnotationList();
		EList<Annotation> _annotations = null;
		if (_annotationList!=null) {
			_annotations=_annotationList.getAnnotations();
		}
		if (_annotations != null) {
			_elvis = _annotations;
		} else {
			EList<Annotation> _emptyEList = XcoreCollectionLiterals.<Annotation>emptyEList();
			_elvis = _emptyEList;
		}
		return _elvis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isValidSimpleAssignmentTarget() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST:
				return basicSetAnnotationList(null, msgs);
			case N4JSPackage.FUNCTION_EXPRESSION__TYPE_VARS:
				return ((InternalEList<?>)getTypeVars()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST:
				return getAnnotationList();
			case N4JSPackage.FUNCTION_EXPRESSION__TYPE_VARS:
				return getTypeVars();
			case N4JSPackage.FUNCTION_EXPRESSION__NAME:
				return getName();
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
			case N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST:
				setAnnotationList((ExpressionAnnotationList)newValue);
				return;
			case N4JSPackage.FUNCTION_EXPRESSION__TYPE_VARS:
				getTypeVars().clear();
				getTypeVars().addAll((Collection<? extends TypeVariable>)newValue);
				return;
			case N4JSPackage.FUNCTION_EXPRESSION__NAME:
				setName((String)newValue);
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
			case N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST:
				setAnnotationList((ExpressionAnnotationList)null);
				return;
			case N4JSPackage.FUNCTION_EXPRESSION__TYPE_VARS:
				getTypeVars().clear();
				return;
			case N4JSPackage.FUNCTION_EXPRESSION__NAME:
				setName(NAME_EDEFAULT);
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
			case N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST:
				return annotationList != null;
			case N4JSPackage.FUNCTION_EXPRESSION__TYPE_VARS:
				return typeVars != null && !typeVars.isEmpty();
			case N4JSPackage.FUNCTION_EXPRESSION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		if (baseClass == ControlFlowElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Expression.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AnnotableExpression.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST: return N4JSPackage.ANNOTABLE_EXPRESSION__ANNOTATION_LIST;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FUNCTION_EXPRESSION__TYPE_VARS: return N4JSPackage.GENERIC_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == ControlFlowElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Expression.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AnnotableExpression.class) {
			switch (baseFeatureID) {
				case N4JSPackage.ANNOTABLE_EXPRESSION__ANNOTATION_LIST: return N4JSPackage.FUNCTION_EXPRESSION__ANNOTATION_LIST;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseFeatureID) {
				case N4JSPackage.GENERIC_DECLARATION__TYPE_VARS: return N4JSPackage.FUNCTION_EXPRESSION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
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
		if (baseClass == AnnotableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.ANNOTABLE_ELEMENT___GET_ANNOTATIONS: return N4JSPackage.FUNCTION_EXPRESSION___GET_ANNOTATIONS;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ControlFlowElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == Expression.class) {
			switch (baseOperationID) {
				case N4JSPackage.EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET: return N4JSPackage.FUNCTION_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;
				default: return -1;
			}
		}
		if (baseClass == AnnotableExpression.class) {
			switch (baseOperationID) {
				case N4JSPackage.ANNOTABLE_EXPRESSION___GET_ANNOTATIONS: return N4JSPackage.FUNCTION_EXPRESSION___GET_ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.FUNCTION_EXPRESSION___GET_NAME;
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
			case N4JSPackage.FUNCTION_EXPRESSION___IS_ARROW_FUNCTION:
				return isArrowFunction();
			case N4JSPackage.FUNCTION_EXPRESSION___GET_ANNOTATIONS:
				return getAnnotations();
			case N4JSPackage.FUNCTION_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET:
				return isValidSimpleAssignmentTarget();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //FunctionExpressionImpl
