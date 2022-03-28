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

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.TConstableElement;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TNamespaceElement;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TVariable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#getDeclaredTypeAccessModifier <em>Declared Type Access Modifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isDeclaredProvidedByRuntime <em>Declared Provided By Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isDirectlyExported <em>Directly Exported</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isConst <em>Const</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#getCompileTimeValue <em>Compile Time Value</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isExternal <em>External</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isObjectLiteral <em>Object Literal</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isNewExpression <em>New Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TVariableImpl extends TAbstractVariableImpl implements TVariable {
	/**
	 * The default value of the '{@link #getDeclaredTypeAccessModifier() <em>Declared Type Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected static final TypeAccessModifier DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT = TypeAccessModifier.UNDEFINED;

	/**
	 * The cached value of the '{@link #getDeclaredTypeAccessModifier() <em>Declared Type Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected TypeAccessModifier declaredTypeAccessModifier = DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredProvidedByRuntime() <em>Declared Provided By Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredProvidedByRuntime()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredProvidedByRuntime() <em>Declared Provided By Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredProvidedByRuntime()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredProvidedByRuntime = DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT;

	/**
	 * The default value of the '{@link #isDirectlyExported() <em>Directly Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirectlyExported()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DIRECTLY_EXPORTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDirectlyExported() <em>Directly Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirectlyExported()
	 * @generated
	 * @ordered
	 */
	protected boolean directlyExported = DIRECTLY_EXPORTED_EDEFAULT;

	/**
	 * The default value of the '{@link #isConst() <em>Const</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConst()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConst() <em>Const</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConst()
	 * @generated
	 * @ordered
	 */
	protected boolean const_ = CONST_EDEFAULT;

	/**
	 * The default value of the '{@link #getCompileTimeValue() <em>Compile Time Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompileTimeValue()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPILE_TIME_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCompileTimeValue() <em>Compile Time Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompileTimeValue()
	 * @generated
	 * @ordered
	 */
	protected String compileTimeValue = COMPILE_TIME_VALUE_EDEFAULT;

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
	 * The default value of the '{@link #isObjectLiteral() <em>Object Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isObjectLiteral()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OBJECT_LITERAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isObjectLiteral() <em>Object Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isObjectLiteral()
	 * @generated
	 * @ordered
	 */
	protected boolean objectLiteral = OBJECT_LITERAL_EDEFAULT;

	/**
	 * The default value of the '{@link #isNewExpression() <em>New Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNewExpression()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEW_EXPRESSION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNewExpression() <em>New Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNewExpression()
	 * @generated
	 * @ordered
	 */
	protected boolean newExpression = NEW_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TVARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeAccessModifier getDeclaredTypeAccessModifier() {
		return declaredTypeAccessModifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredTypeAccessModifier(TypeAccessModifier newDeclaredTypeAccessModifier) {
		TypeAccessModifier oldDeclaredTypeAccessModifier = declaredTypeAccessModifier;
		declaredTypeAccessModifier = newDeclaredTypeAccessModifier == null ? DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT : newDeclaredTypeAccessModifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER, oldDeclaredTypeAccessModifier, declaredTypeAccessModifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredProvidedByRuntime() {
		return declaredProvidedByRuntime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredProvidedByRuntime(boolean newDeclaredProvidedByRuntime) {
		boolean oldDeclaredProvidedByRuntime = declaredProvidedByRuntime;
		declaredProvidedByRuntime = newDeclaredProvidedByRuntime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME, oldDeclaredProvidedByRuntime, declaredProvidedByRuntime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDirectlyExported() {
		return directlyExported;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDirectlyExported(boolean newDirectlyExported) {
		boolean oldDirectlyExported = directlyExported;
		directlyExported = newDirectlyExported;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__DIRECTLY_EXPORTED, oldDirectlyExported, directlyExported));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConst() {
		return const_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConst(boolean newConst) {
		boolean oldConst = const_;
		const_ = newConst;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__CONST, oldConst, const_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCompileTimeValue() {
		return compileTimeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompileTimeValue(String newCompileTimeValue) {
		String oldCompileTimeValue = compileTimeValue;
		compileTimeValue = newCompileTimeValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__COMPILE_TIME_VALUE, oldCompileTimeValue, compileTimeValue));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__EXTERNAL, oldExternal, external));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isObjectLiteral() {
		return objectLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObjectLiteral(boolean newObjectLiteral) {
		boolean oldObjectLiteral = objectLiteral;
		objectLiteral = newObjectLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__OBJECT_LITERAL, oldObjectLiteral, objectLiteral));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNewExpression() {
		return newExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNewExpression(boolean newNewExpression) {
		boolean oldNewExpression = newExpression;
		newExpression = newNewExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__NEW_EXPRESSION, oldNewExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getVariableAsString() {
		final StringBuilder strb = new StringBuilder();
		boolean _isConst = this.isConst();
		if (_isConst) {
			strb.append("const ");
		}
		else {
			strb.append("var ");
		}
		strb.append(this.getName());
		TypeRef _typeRef = this.getTypeRef();
		boolean _tripleNotEquals = (_typeRef != null);
		if (_tripleNotEquals) {
			strb.append(": ").append(this.getTypeRef().getTypeRefAsString());
		}
		return strb.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHollow() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isProvidedByRuntime() {
		return this.isDeclaredProvidedByRuntime();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeAccessModifier getTypeAccessModifier() {
		TypeAccessModifier _declaredTypeAccessModifier = this.getDeclaredTypeAccessModifier();
		boolean _equals = Objects.equal(_declaredTypeAccessModifier, TypeAccessModifier.UNDEFINED);
		if (_equals) {
			return this.getDefaultTypeAccessModifier();
		}
		return this.getDeclaredTypeAccessModifier();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeAccessModifier getDefaultTypeAccessModifier() {
		EObject _eContainer = this.eContainer();
		if ((_eContainer instanceof TNamespace)) {
			EObject _eContainer_1 = this.eContainer();
			return ((TNamespace) _eContainer_1).getTypeAccessModifier();
		}
		else {
			boolean _isDirectlyExported = this.isDirectlyExported();
			if (_isDirectlyExported) {
				return TypeAccessModifier.PROJECT;
			}
			else {
				return TypeAccessModifier.PRIVATE;
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER:
				return getDeclaredTypeAccessModifier();
			case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME:
				return isDeclaredProvidedByRuntime();
			case TypesPackage.TVARIABLE__DIRECTLY_EXPORTED:
				return isDirectlyExported();
			case TypesPackage.TVARIABLE__CONST:
				return isConst();
			case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE:
				return getCompileTimeValue();
			case TypesPackage.TVARIABLE__EXTERNAL:
				return isExternal();
			case TypesPackage.TVARIABLE__OBJECT_LITERAL:
				return isObjectLiteral();
			case TypesPackage.TVARIABLE__NEW_EXPRESSION:
				return isNewExpression();
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
			case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier((TypeAccessModifier)newValue);
				return;
			case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime((Boolean)newValue);
				return;
			case TypesPackage.TVARIABLE__DIRECTLY_EXPORTED:
				setDirectlyExported((Boolean)newValue);
				return;
			case TypesPackage.TVARIABLE__CONST:
				setConst((Boolean)newValue);
				return;
			case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE:
				setCompileTimeValue((String)newValue);
				return;
			case TypesPackage.TVARIABLE__EXTERNAL:
				setExternal((Boolean)newValue);
				return;
			case TypesPackage.TVARIABLE__OBJECT_LITERAL:
				setObjectLiteral((Boolean)newValue);
				return;
			case TypesPackage.TVARIABLE__NEW_EXPRESSION:
				setNewExpression((Boolean)newValue);
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
			case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier(DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime(DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__DIRECTLY_EXPORTED:
				setDirectlyExported(DIRECTLY_EXPORTED_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__CONST:
				setConst(CONST_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE:
				setCompileTimeValue(COMPILE_TIME_VALUE_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__EXTERNAL:
				setExternal(EXTERNAL_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__OBJECT_LITERAL:
				setObjectLiteral(OBJECT_LITERAL_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__NEW_EXPRESSION:
				setNewExpression(NEW_EXPRESSION_EDEFAULT);
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
			case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER:
				return declaredTypeAccessModifier != DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT;
			case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME:
				return declaredProvidedByRuntime != DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT;
			case TypesPackage.TVARIABLE__DIRECTLY_EXPORTED:
				return directlyExported != DIRECTLY_EXPORTED_EDEFAULT;
			case TypesPackage.TVARIABLE__CONST:
				return const_ != CONST_EDEFAULT;
			case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE:
				return COMPILE_TIME_VALUE_EDEFAULT == null ? compileTimeValue != null : !COMPILE_TIME_VALUE_EDEFAULT.equals(compileTimeValue);
			case TypesPackage.TVARIABLE__EXTERNAL:
				return external != EXTERNAL_EDEFAULT;
			case TypesPackage.TVARIABLE__OBJECT_LITERAL:
				return objectLiteral != OBJECT_LITERAL_EDEFAULT;
			case TypesPackage.TVARIABLE__NEW_EXPRESSION:
				return newExpression != NEW_EXPRESSION_EDEFAULT;
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
		if (baseClass == AccessibleTypeElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == TExportableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TVARIABLE__DIRECTLY_EXPORTED: return TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED;
				default: return -1;
			}
		}
		if (baseClass == TConstableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TVARIABLE__CONST: return TypesPackage.TCONSTABLE_ELEMENT__CONST;
				case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE: return TypesPackage.TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE;
				default: return -1;
			}
		}
		if (baseClass == TNamespaceElement.class) {
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
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == TExportableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED: return TypesPackage.TVARIABLE__DIRECTLY_EXPORTED;
				default: return -1;
			}
		}
		if (baseClass == TConstableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TCONSTABLE_ELEMENT__CONST: return TypesPackage.TVARIABLE__CONST;
				case TypesPackage.TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE: return TypesPackage.TVARIABLE__COMPILE_TIME_VALUE;
				default: return -1;
			}
		}
		if (baseClass == TNamespaceElement.class) {
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
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseOperationID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TVARIABLE___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TVARIABLE___GET_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___GET_DEFAULT_TYPE_ACCESS_MODIFIER: return TypesPackage.TVARIABLE___GET_DEFAULT_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_DIRECTLY_EXPORTED: return TypesPackage.TVARIABLE___IS_DIRECTLY_EXPORTED;
				default: return -1;
			}
		}
		if (baseClass == TExportableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == TConstableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == TNamespaceElement.class) {
			switch (baseOperationID) {
				case TypesPackage.TNAMESPACE_ELEMENT___IS_HOLLOW: return TypesPackage.TVARIABLE___IS_HOLLOW;
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
			case TypesPackage.TVARIABLE___GET_VARIABLE_AS_STRING:
				return getVariableAsString();
			case TypesPackage.TVARIABLE___IS_HOLLOW:
				return isHollow();
			case TypesPackage.TVARIABLE___IS_PROVIDED_BY_RUNTIME:
				return isProvidedByRuntime();
			case TypesPackage.TVARIABLE___GET_TYPE_ACCESS_MODIFIER:
				return getTypeAccessModifier();
			case TypesPackage.TVARIABLE___GET_DEFAULT_TYPE_ACCESS_MODIFIER:
				return getDefaultTypeAccessModifier();
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
		result.append(" (declaredTypeAccessModifier: ");
		result.append(declaredTypeAccessModifier);
		result.append(", declaredProvidedByRuntime: ");
		result.append(declaredProvidedByRuntime);
		result.append(", directlyExported: ");
		result.append(directlyExported);
		result.append(", const: ");
		result.append(const_);
		result.append(", compileTimeValue: ");
		result.append(compileTimeValue);
		result.append(", external: ");
		result.append(external);
		result.append(", objectLiteral: ");
		result.append(objectLiteral);
		result.append(", newExpression: ");
		result.append(newExpression);
		result.append(')');
		return result.toString();
	}

} //TVariableImpl
