/**
 * *
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.regex.regularExpression.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.regex.regularExpression.ExactQuantifier;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Exact Quantifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.ExactQuantifierImpl#getMin <em>Min</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.ExactQuantifierImpl#getMax <em>Max</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.ExactQuantifierImpl#isUnboundedMax <em>Unbounded Max</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExactQuantifierImpl extends QuantifierImpl implements ExactQuantifier
{
  /**
   * The default value of the '{@link #getMin() <em>Min</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMin()
   * @generated
   * @ordered
   */
  protected static final int MIN_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getMin() <em>Min</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMin()
   * @generated
   * @ordered
   */
  protected int min = MIN_EDEFAULT;

  /**
   * The default value of the '{@link #getMax() <em>Max</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMax()
   * @generated
   * @ordered
   */
  protected static final int MAX_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getMax() <em>Max</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMax()
   * @generated
   * @ordered
   */
  protected int max = MAX_EDEFAULT;

  /**
   * The default value of the '{@link #isUnboundedMax() <em>Unbounded Max</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isUnboundedMax()
   * @generated
   * @ordered
   */
  protected static final boolean UNBOUNDED_MAX_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isUnboundedMax() <em>Unbounded Max</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isUnboundedMax()
   * @generated
   * @ordered
   */
  protected boolean unboundedMax = UNBOUNDED_MAX_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExactQuantifierImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return RegularExpressionPackage.Literals.EXACT_QUANTIFIER;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getMin()
  {
    return min;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMin(int newMin)
  {
    int oldMin = min;
    min = newMin;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.EXACT_QUANTIFIER__MIN, oldMin, min));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getMax()
  {
    return max;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMax(int newMax)
  {
    int oldMax = max;
    max = newMax;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.EXACT_QUANTIFIER__MAX, oldMax, max));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isUnboundedMax()
  {
    return unboundedMax;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUnboundedMax(boolean newUnboundedMax)
  {
    boolean oldUnboundedMax = unboundedMax;
    unboundedMax = newUnboundedMax;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.EXACT_QUANTIFIER__UNBOUNDED_MAX, oldUnboundedMax, unboundedMax));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.EXACT_QUANTIFIER__MIN:
        return getMin();
      case RegularExpressionPackage.EXACT_QUANTIFIER__MAX:
        return getMax();
      case RegularExpressionPackage.EXACT_QUANTIFIER__UNBOUNDED_MAX:
        return isUnboundedMax();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.EXACT_QUANTIFIER__MIN:
        setMin((Integer)newValue);
        return;
      case RegularExpressionPackage.EXACT_QUANTIFIER__MAX:
        setMax((Integer)newValue);
        return;
      case RegularExpressionPackage.EXACT_QUANTIFIER__UNBOUNDED_MAX:
        setUnboundedMax((Boolean)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.EXACT_QUANTIFIER__MIN:
        setMin(MIN_EDEFAULT);
        return;
      case RegularExpressionPackage.EXACT_QUANTIFIER__MAX:
        setMax(MAX_EDEFAULT);
        return;
      case RegularExpressionPackage.EXACT_QUANTIFIER__UNBOUNDED_MAX:
        setUnboundedMax(UNBOUNDED_MAX_EDEFAULT);
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.EXACT_QUANTIFIER__MIN:
        return min != MIN_EDEFAULT;
      case RegularExpressionPackage.EXACT_QUANTIFIER__MAX:
        return max != MAX_EDEFAULT;
      case RegularExpressionPackage.EXACT_QUANTIFIER__UNBOUNDED_MAX:
        return unboundedMax != UNBOUNDED_MAX_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (min: ");
    result.append(min);
    result.append(", max: ");
    result.append(max);
    result.append(", unboundedMax: ");
    result.append(unboundedMax);
    result.append(')');
    return result.toString();
  }

} //ExactQuantifierImpl
