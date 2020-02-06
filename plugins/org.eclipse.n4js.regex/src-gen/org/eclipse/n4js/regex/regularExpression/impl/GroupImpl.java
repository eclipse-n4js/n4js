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

import org.eclipse.n4js.regex.regularExpression.Group;
import org.eclipse.n4js.regex.regularExpression.Pattern;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.GroupImpl#isNamed <em>Named</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.GroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.GroupImpl#isNonCapturing <em>Non Capturing</em>}</li>
 *   <li>{@link org.eclipse.n4js.regex.regularExpression.impl.GroupImpl#getPattern <em>Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GroupImpl extends PatternImpl implements Group
{
  /**
   * The default value of the '{@link #isNamed() <em>Named</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isNamed()
   * @generated
   * @ordered
   */
  protected static final boolean NAMED_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isNamed() <em>Named</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isNamed()
   * @generated
   * @ordered
   */
  protected boolean named = NAMED_EDEFAULT;

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
   * The default value of the '{@link #isNonCapturing() <em>Non Capturing</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isNonCapturing()
   * @generated
   * @ordered
   */
  protected static final boolean NON_CAPTURING_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isNonCapturing() <em>Non Capturing</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isNonCapturing()
   * @generated
   * @ordered
   */
  protected boolean nonCapturing = NON_CAPTURING_EDEFAULT;

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
  protected GroupImpl()
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
    return RegularExpressionPackage.Literals.GROUP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isNamed()
  {
    return named;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNamed(boolean newNamed)
  {
    boolean oldNamed = named;
    named = newNamed;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.GROUP__NAMED, oldNamed, named));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.GROUP__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isNonCapturing()
  {
    return nonCapturing;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNonCapturing(boolean newNonCapturing)
  {
    boolean oldNonCapturing = nonCapturing;
    nonCapturing = newNonCapturing;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.GROUP__NON_CAPTURING, oldNonCapturing, nonCapturing));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.GROUP__PATTERN, oldPattern, newPattern);
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
        msgs = ((InternalEObject)pattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RegularExpressionPackage.GROUP__PATTERN, null, msgs);
      if (newPattern != null)
        msgs = ((InternalEObject)newPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RegularExpressionPackage.GROUP__PATTERN, null, msgs);
      msgs = basicSetPattern(newPattern, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RegularExpressionPackage.GROUP__PATTERN, newPattern, newPattern));
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
      case RegularExpressionPackage.GROUP__PATTERN:
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
      case RegularExpressionPackage.GROUP__NAMED:
        return isNamed();
      case RegularExpressionPackage.GROUP__NAME:
        return getName();
      case RegularExpressionPackage.GROUP__NON_CAPTURING:
        return isNonCapturing();
      case RegularExpressionPackage.GROUP__PATTERN:
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
      case RegularExpressionPackage.GROUP__NAMED:
        setNamed((Boolean)newValue);
        return;
      case RegularExpressionPackage.GROUP__NAME:
        setName((String)newValue);
        return;
      case RegularExpressionPackage.GROUP__NON_CAPTURING:
        setNonCapturing((Boolean)newValue);
        return;
      case RegularExpressionPackage.GROUP__PATTERN:
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
      case RegularExpressionPackage.GROUP__NAMED:
        setNamed(NAMED_EDEFAULT);
        return;
      case RegularExpressionPackage.GROUP__NAME:
        setName(NAME_EDEFAULT);
        return;
      case RegularExpressionPackage.GROUP__NON_CAPTURING:
        setNonCapturing(NON_CAPTURING_EDEFAULT);
        return;
      case RegularExpressionPackage.GROUP__PATTERN:
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
      case RegularExpressionPackage.GROUP__NAMED:
        return named != NAMED_EDEFAULT;
      case RegularExpressionPackage.GROUP__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case RegularExpressionPackage.GROUP__NON_CAPTURING:
        return nonCapturing != NON_CAPTURING_EDEFAULT;
      case RegularExpressionPackage.GROUP__PATTERN:
        return pattern != null;
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
    result.append(" (named: ");
    result.append(named);
    result.append(", name: ");
    result.append(name);
    result.append(", nonCapturing: ");
    result.append(nonCapturing);
    result.append(')');
    return result.toString();
  }

} //GroupImpl
