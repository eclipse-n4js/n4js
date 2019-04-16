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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.regex.regularExpression.Pattern;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionBody;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionBodyImpl#getPattern <em>Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RegularExpressionBodyImpl extends MinimalEObjectImpl.Container implements RegularExpressionBody
{
  /**
   * The cached value of the '{@link #getPattern() <em>Pattern</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPattern()
   * @generated
   * @ordered
   */
  protected Pattern pattern;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RegularExpressionBodyImpl()
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
    return RegularExpressionPackage.Literals.REGULAR_EXPRESSION_BODY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Pattern getPattern()
  {
    return pattern;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPattern(Pattern newPattern, NotificationChain msgs)
  {
    Pattern oldPattern = pattern;
    pattern = newPattern;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.REGULAR_EXPRESSION_BODY__PATTERN, oldPattern, newPattern);
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
  public void setPattern(Pattern newPattern)
  {
    if (newPattern != pattern)
    {
      NotificationChain msgs = null;
      if (pattern != null)
        msgs = ((InternalEObject)pattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RegularExpressionPackage.REGULAR_EXPRESSION_BODY__PATTERN, null, msgs);
      if (newPattern != null)
        msgs = ((InternalEObject)newPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RegularExpressionPackage.REGULAR_EXPRESSION_BODY__PATTERN, null, msgs);
      msgs = basicSetPattern(newPattern, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.REGULAR_EXPRESSION_BODY__PATTERN, newPattern, newPattern));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case RegularExpressionPackage.REGULAR_EXPRESSION_BODY__PATTERN:
        return basicSetPattern(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case RegularExpressionPackage.REGULAR_EXPRESSION_BODY__PATTERN:
        return getPattern();
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
      case RegularExpressionPackage.REGULAR_EXPRESSION_BODY__PATTERN:
        setPattern((Pattern)newValue);
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
      case RegularExpressionPackage.REGULAR_EXPRESSION_BODY__PATTERN:
        setPattern((Pattern)null);
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
      case RegularExpressionPackage.REGULAR_EXPRESSION_BODY__PATTERN:
        return pattern != null;
    }
    return super.eIsSet(featureID);
  }

} //RegularExpressionBodyImpl
