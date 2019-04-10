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

import org.eclipse.n4js.regex.regularExpression.RegularExpressionBody;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionFlags;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionLiteralImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.RegularExpressionLiteralImpl#getFlags <em>Flags</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RegularExpressionLiteralImpl extends MinimalEObjectImpl.Container implements RegularExpressionLiteral
{
  /**
   * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBody()
   * @generated
   * @ordered
   */
  protected RegularExpressionBody body;

  /**
   * The cached value of the '{@link #getFlags() <em>Flags</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFlags()
   * @generated
   * @ordered
   */
  protected RegularExpressionFlags flags;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RegularExpressionLiteralImpl()
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
    return RegularExpressionPackage.Literals.REGULAR_EXPRESSION_LITERAL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public RegularExpressionBody getBody()
  {
    return body;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBody(RegularExpressionBody newBody, NotificationChain msgs)
  {
    RegularExpressionBody oldBody = body;
    body = newBody;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__BODY, oldBody, newBody);
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
  public void setBody(RegularExpressionBody newBody)
  {
    if (newBody != body)
    {
      NotificationChain msgs = null;
      if (body != null)
        msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__BODY, null, msgs);
      if (newBody != null)
        msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__BODY, null, msgs);
      msgs = basicSetBody(newBody, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__BODY, newBody, newBody));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public RegularExpressionFlags getFlags()
  {
    return flags;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFlags(RegularExpressionFlags newFlags, NotificationChain msgs)
  {
    RegularExpressionFlags oldFlags = flags;
    flags = newFlags;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__FLAGS, oldFlags, newFlags);
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
  public void setFlags(RegularExpressionFlags newFlags)
  {
    if (newFlags != flags)
    {
      NotificationChain msgs = null;
      if (flags != null)
        msgs = ((InternalEObject)flags).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__FLAGS, null, msgs);
      if (newFlags != null)
        msgs = ((InternalEObject)newFlags).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__FLAGS, null, msgs);
      msgs = basicSetFlags(newFlags, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__FLAGS, newFlags, newFlags));
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
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__BODY:
        return basicSetBody(null, msgs);
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__FLAGS:
        return basicSetFlags(null, msgs);
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
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__BODY:
        return getBody();
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__FLAGS:
        return getFlags();
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
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__BODY:
        setBody((RegularExpressionBody)newValue);
        return;
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__FLAGS:
        setFlags((RegularExpressionFlags)newValue);
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
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__BODY:
        setBody((RegularExpressionBody)null);
        return;
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__FLAGS:
        setFlags((RegularExpressionFlags)null);
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
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__BODY:
        return body != null;
      case RegularExpressionPackage.REGULAR_EXPRESSION_LITERAL__FLAGS:
        return flags != null;
    }
    return super.eIsSet(featureID);
  }

} //RegularExpressionLiteralImpl
