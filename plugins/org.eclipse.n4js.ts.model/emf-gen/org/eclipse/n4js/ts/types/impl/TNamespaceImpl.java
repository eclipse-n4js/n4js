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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;
import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.ArrayLikes;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.ts.types.util.Variance;

import org.eclipse.xtext.EcoreUtil2;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TNamespace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getExportedName <em>Exported Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getDeclaredTypeAccessModifier <em>Declared Type Access Modifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#isDeclaredProvidedByRuntime <em>Declared Provided By Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getAstElement <em>Ast Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#isExternal <em>External</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TNamespaceImpl extends AbstractNamespaceImpl implements TNamespace {
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
	 * The default value of the '{@link #getExportedName() <em>Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedName()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPORTED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExportedName() <em>Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedName()
	 * @generated
	 * @ordered
	 */
	protected String exportedName = EXPORTED_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<TAnnotation> annotations;

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
	 * The cached value of the '{@link #getAstElement() <em>Ast Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstElement()
	 * @generated
	 * @ordered
	 */
	protected EObject astElement;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TNamespaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TNAMESPACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TNAMESPACE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExportedName() {
		return exportedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExportedName(String newExportedName) {
		String oldExportedName = exportedName;
		exportedName = newExportedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TNAMESPACE__EXPORTED_NAME, oldExportedName, exportedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TAnnotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<TAnnotation>(TAnnotation.class, this, TypesPackage.TNAMESPACE__ANNOTATIONS);
		}
		return annotations;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TNAMESPACE__DECLARED_TYPE_ACCESS_MODIFIER, oldDeclaredTypeAccessModifier, declaredTypeAccessModifier));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TNAMESPACE__DECLARED_PROVIDED_BY_RUNTIME, oldDeclaredProvidedByRuntime, declaredProvidedByRuntime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject getAstElement() {
		if (astElement != null && astElement.eIsProxy()) {
			InternalEObject oldAstElement = (InternalEObject)astElement;
			astElement = eResolveProxy(oldAstElement);
			if (astElement != oldAstElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.TNAMESPACE__AST_ELEMENT, oldAstElement, astElement));
			}
		}
		return astElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetAstElement() {
		return astElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAstElement(EObject newAstElement) {
		EObject oldAstElement = astElement;
		astElement = newAstElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TNAMESPACE__AST_ELEMENT, oldAstElement, astElement));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TNAMESPACE__EXTERNAL, oldExternal, external));
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
			boolean _isExported = this.isExported();
			if (_isExported) {
				return TypeAccessModifier.PROJECT;
			}
			else {
				return TypeAccessModifier.PRIVATE;
			}
		}
		return this.getDeclaredTypeAccessModifier();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExported() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAlias() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPolyfill() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStaticPolyfill() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFinal() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDynamizable() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isArrayLike() {
		TypeRef _elementType = this.getElementType();
		return (_elementType != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getElementType() {
		Object _elementType = ArrayLikes.getElementType(this);
		return ((TypeRef) _elementType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGeneric() {
		boolean _isEmpty = this.getTypeVars().isEmpty();
		return (!_isEmpty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getTypeVars() {
		return XcoreCollectionLiterals.<TypeVariable>emptyEList();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Variance getVarianceOfTypeVar(final int idx) {
		Variance _xifexpression = null;
		if (((idx >= 0) && (idx < this.getTypeVars().size()))) {
			_xifexpression = this.getTypeVars().get(idx).getVariance();
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
	public String getRawTypeAsString() {
		return this.getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeAsString() {
		String _xifexpression = null;
		boolean _isGeneric = this.isGeneric();
		if (_isGeneric) {
			String _name = this.getName();
			String _plus = (_name + "<");
			final Function1<TypeVariable, String> _function = new Function1<TypeVariable, String>() {
				public String apply(final TypeVariable it) {
					return it.getTypeAsString();
				}
			};
			String _join = IterableExtensions.join(XcoreEListExtensions.<TypeVariable, String>map(this.getTypeVars(), _function), ",");
			String _plus_1 = (_plus + _join);
			_xifexpression = (_plus_1 + ">");
		}
		else {
			_xifexpression = this.getName();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TModule getContainingModule() {
		return EcoreUtil2.<TModule>getContainerOfType(this, TModule.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TNAMESPACE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.TNAMESPACE__NAME:
				return getName();
			case TypesPackage.TNAMESPACE__EXPORTED_NAME:
				return getExportedName();
			case TypesPackage.TNAMESPACE__ANNOTATIONS:
				return getAnnotations();
			case TypesPackage.TNAMESPACE__DECLARED_TYPE_ACCESS_MODIFIER:
				return getDeclaredTypeAccessModifier();
			case TypesPackage.TNAMESPACE__DECLARED_PROVIDED_BY_RUNTIME:
				return isDeclaredProvidedByRuntime();
			case TypesPackage.TNAMESPACE__AST_ELEMENT:
				if (resolve) return getAstElement();
				return basicGetAstElement();
			case TypesPackage.TNAMESPACE__EXTERNAL:
				return isExternal();
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
			case TypesPackage.TNAMESPACE__NAME:
				setName((String)newValue);
				return;
			case TypesPackage.TNAMESPACE__EXPORTED_NAME:
				setExportedName((String)newValue);
				return;
			case TypesPackage.TNAMESPACE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends TAnnotation>)newValue);
				return;
			case TypesPackage.TNAMESPACE__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier((TypeAccessModifier)newValue);
				return;
			case TypesPackage.TNAMESPACE__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime((Boolean)newValue);
				return;
			case TypesPackage.TNAMESPACE__AST_ELEMENT:
				setAstElement((EObject)newValue);
				return;
			case TypesPackage.TNAMESPACE__EXTERNAL:
				setExternal((Boolean)newValue);
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
			case TypesPackage.TNAMESPACE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TypesPackage.TNAMESPACE__EXPORTED_NAME:
				setExportedName(EXPORTED_NAME_EDEFAULT);
				return;
			case TypesPackage.TNAMESPACE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case TypesPackage.TNAMESPACE__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier(DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT);
				return;
			case TypesPackage.TNAMESPACE__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime(DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT);
				return;
			case TypesPackage.TNAMESPACE__AST_ELEMENT:
				setAstElement((EObject)null);
				return;
			case TypesPackage.TNAMESPACE__EXTERNAL:
				setExternal(EXTERNAL_EDEFAULT);
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
			case TypesPackage.TNAMESPACE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TypesPackage.TNAMESPACE__EXPORTED_NAME:
				return EXPORTED_NAME_EDEFAULT == null ? exportedName != null : !EXPORTED_NAME_EDEFAULT.equals(exportedName);
			case TypesPackage.TNAMESPACE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case TypesPackage.TNAMESPACE__DECLARED_TYPE_ACCESS_MODIFIER:
				return declaredTypeAccessModifier != DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT;
			case TypesPackage.TNAMESPACE__DECLARED_PROVIDED_BY_RUNTIME:
				return declaredProvidedByRuntime != DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT;
			case TypesPackage.TNAMESPACE__AST_ELEMENT:
				return astElement != null;
			case TypesPackage.TNAMESPACE__EXTERNAL:
				return external != EXTERNAL_EDEFAULT;
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
		if (baseClass == TypableElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TNAMESPACE__NAME: return TypesPackage.IDENTIFIABLE_ELEMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == TExportableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TNAMESPACE__EXPORTED_NAME: return TypesPackage.TEXPORTABLE_ELEMENT__EXPORTED_NAME;
				default: return -1;
			}
		}
		if (baseClass == TAnnotableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TNAMESPACE__ANNOTATIONS: return TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == Type.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AccessibleTypeElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TNAMESPACE__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.TNAMESPACE__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TNAMESPACE__AST_ELEMENT: return TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT;
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
		if (baseClass == TypableElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.IDENTIFIABLE_ELEMENT__NAME: return TypesPackage.TNAMESPACE__NAME;
				default: return -1;
			}
		}
		if (baseClass == TExportableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TEXPORTABLE_ELEMENT__EXPORTED_NAME: return TypesPackage.TNAMESPACE__EXPORTED_NAME;
				default: return -1;
			}
		}
		if (baseClass == TAnnotableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS: return TypesPackage.TNAMESPACE__ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == Type.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.TNAMESPACE__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.TNAMESPACE__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT: return TypesPackage.TNAMESPACE__AST_ELEMENT;
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
		if (baseClass == AbstractNamespace.class) {
			switch (baseOperationID) {
				case TypesPackage.ABSTRACT_NAMESPACE___GET_CONTAINING_MODULE: return TypesPackage.TNAMESPACE___GET_CONTAINING_MODULE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseOperationID) {
				case TypesPackage.IDENTIFIABLE_ELEMENT___GET_CONTAINING_MODULE: return TypesPackage.TNAMESPACE___GET_CONTAINING_MODULE;
				default: return -1;
			}
		}
		if (baseClass == TExportableElement.class) {
			switch (baseOperationID) {
				case TypesPackage.TEXPORTABLE_ELEMENT___IS_EXPORTED: return TypesPackage.TNAMESPACE___IS_EXPORTED;
				default: return -1;
			}
		}
		if (baseClass == TAnnotableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___IS_ALIAS: return TypesPackage.TNAMESPACE___IS_ALIAS;
				case TypesPackage.TYPE___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TNAMESPACE___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.TYPE___IS_POLYFILL: return TypesPackage.TNAMESPACE___IS_POLYFILL;
				case TypesPackage.TYPE___IS_STATIC_POLYFILL: return TypesPackage.TNAMESPACE___IS_STATIC_POLYFILL;
				case TypesPackage.TYPE___IS_FINAL: return TypesPackage.TNAMESPACE___IS_FINAL;
				case TypesPackage.TYPE___IS_DYNAMIZABLE: return TypesPackage.TNAMESPACE___IS_DYNAMIZABLE;
				case TypesPackage.TYPE___IS_ARRAY_LIKE: return TypesPackage.TNAMESPACE___IS_ARRAY_LIKE;
				case TypesPackage.TYPE___GET_ELEMENT_TYPE: return TypesPackage.TNAMESPACE___GET_ELEMENT_TYPE;
				case TypesPackage.TYPE___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TNAMESPACE___GET_TYPE_ACCESS_MODIFIER;
				case TypesPackage.TYPE___IS_GENERIC: return TypesPackage.TNAMESPACE___IS_GENERIC;
				case TypesPackage.TYPE___GET_TYPE_VARS: return TypesPackage.TNAMESPACE___GET_TYPE_VARS;
				case TypesPackage.TYPE___GET_VARIANCE_OF_TYPE_VAR__INT: return TypesPackage.TNAMESPACE___GET_VARIANCE_OF_TYPE_VAR__INT;
				case TypesPackage.TYPE___GET_RAW_TYPE_AS_STRING: return TypesPackage.TNAMESPACE___GET_RAW_TYPE_AS_STRING;
				case TypesPackage.TYPE___GET_TYPE_AS_STRING: return TypesPackage.TNAMESPACE___GET_TYPE_AS_STRING;
				default: return -1;
			}
		}
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseOperationID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TNAMESPACE___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TNAMESPACE___GET_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_EXPORTED: return TypesPackage.TNAMESPACE___IS_EXPORTED;
				default: return -1;
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
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
			case TypesPackage.TNAMESPACE___IS_PROVIDED_BY_RUNTIME:
				return isProvidedByRuntime();
			case TypesPackage.TNAMESPACE___GET_TYPE_ACCESS_MODIFIER:
				return getTypeAccessModifier();
			case TypesPackage.TNAMESPACE___IS_EXPORTED:
				return isExported();
			case TypesPackage.TNAMESPACE___IS_ALIAS:
				return isAlias();
			case TypesPackage.TNAMESPACE___IS_POLYFILL:
				return isPolyfill();
			case TypesPackage.TNAMESPACE___IS_STATIC_POLYFILL:
				return isStaticPolyfill();
			case TypesPackage.TNAMESPACE___IS_FINAL:
				return isFinal();
			case TypesPackage.TNAMESPACE___IS_DYNAMIZABLE:
				return isDynamizable();
			case TypesPackage.TNAMESPACE___IS_ARRAY_LIKE:
				return isArrayLike();
			case TypesPackage.TNAMESPACE___GET_ELEMENT_TYPE:
				return getElementType();
			case TypesPackage.TNAMESPACE___IS_GENERIC:
				return isGeneric();
			case TypesPackage.TNAMESPACE___GET_TYPE_VARS:
				return getTypeVars();
			case TypesPackage.TNAMESPACE___GET_VARIANCE_OF_TYPE_VAR__INT:
				return getVarianceOfTypeVar((Integer)arguments.get(0));
			case TypesPackage.TNAMESPACE___GET_RAW_TYPE_AS_STRING:
				return getRawTypeAsString();
			case TypesPackage.TNAMESPACE___GET_TYPE_AS_STRING:
				return getTypeAsString();
			case TypesPackage.TNAMESPACE___GET_CONTAINING_MODULE:
				return getContainingModule();
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
		result.append(", exportedName: ");
		result.append(exportedName);
		result.append(", declaredTypeAccessModifier: ");
		result.append(declaredTypeAccessModifier);
		result.append(", declaredProvidedByRuntime: ");
		result.append(declaredProvidedByRuntime);
		result.append(", external: ");
		result.append(external);
		result.append(')');
		return result.toString();
	}

} //TNamespaceImpl
