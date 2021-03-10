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

import java.math.BigDecimal;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.AnnotableScriptElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDefinition;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VersionedElement;

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Versionable;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Class Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl#getAnnotationList <em>Annotation List</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl#getDeclaredModifiers <em>Declared Modifiers</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl#getTypeVars <em>Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl#getTypingStrategy <em>Typing Strategy</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl#getDeclaredVersion <em>Declared Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4ClassDeclarationImpl extends N4ClassDefinitionImpl implements N4ClassDeclaration {
	/**
	 * The cached value of the '{@link #getAnnotationList() <em>Annotation List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotationList()
	 * @generated
	 * @ordered
	 */
	protected AnnotationList annotationList;

	/**
	 * The cached value of the '{@link #getDeclaredModifiers() <em>Declared Modifiers</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredModifiers()
	 * @generated
	 * @ordered
	 */
	protected EList<N4Modifier> declaredModifiers;

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
	 * The cached value of the '{@link #getTypeVars() <em>Type Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeVars()
	 * @generated
	 * @ordered
	 */
	protected EList<N4TypeVariable> typeVars;

	/**
	 * The default value of the '{@link #getTypingStrategy() <em>Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypingStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final TypingStrategy TYPING_STRATEGY_EDEFAULT = TypingStrategy.DEFAULT;

	/**
	 * The cached value of the '{@link #getTypingStrategy() <em>Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypingStrategy()
	 * @generated
	 * @ordered
	 */
	protected TypingStrategy typingStrategy = TYPING_STRATEGY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeclaredVersion() <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVersion()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal DECLARED_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeclaredVersion() <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVersion()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal declaredVersion = DECLARED_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4ClassDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_CLASS_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnnotationList getAnnotationList() {
		return annotationList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAnnotationList(AnnotationList newAnnotationList, NotificationChain msgs) {
		AnnotationList oldAnnotationList = annotationList;
		annotationList = newAnnotationList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST, oldAnnotationList, newAnnotationList);
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
	public void setAnnotationList(AnnotationList newAnnotationList) {
		if (newAnnotationList != annotationList) {
			NotificationChain msgs = null;
			if (annotationList != null)
				msgs = ((InternalEObject)annotationList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST, null, msgs);
			if (newAnnotationList != null)
				msgs = ((InternalEObject)newAnnotationList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST, null, msgs);
			msgs = basicSetAnnotationList(newAnnotationList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST, newAnnotationList, newAnnotationList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4Modifier> getDeclaredModifiers() {
		if (declaredModifiers == null) {
			declaredModifiers = new EDataTypeEList<N4Modifier>(N4Modifier.class, this, N4JSPackage.N4_CLASS_DECLARATION__DECLARED_MODIFIERS);
		}
		return declaredModifiers;
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASS_DECLARATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4TypeVariable> getTypeVars() {
		if (typeVars == null) {
			typeVars = new EObjectContainmentEList<N4TypeVariable>(N4TypeVariable.class, this, N4JSPackage.N4_CLASS_DECLARATION__TYPE_VARS);
		}
		return typeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypingStrategy getTypingStrategy() {
		return typingStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypingStrategy(TypingStrategy newTypingStrategy) {
		TypingStrategy oldTypingStrategy = typingStrategy;
		typingStrategy = newTypingStrategy == null ? TYPING_STRATEGY_EDEFAULT : newTypingStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASS_DECLARATION__TYPING_STRATEGY, oldTypingStrategy, typingStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigDecimal getDeclaredVersion() {
		return declaredVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredVersion(BigDecimal newDeclaredVersion) {
		BigDecimal oldDeclaredVersion = declaredVersion;
		declaredVersion = newDeclaredVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASS_DECLARATION__DECLARED_VERSION, oldDeclaredVersion, declaredVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbstract() {
		return this.getDeclaredModifiers().contains(N4Modifier.ABSTRACT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVersion() {
		return this.getDeclaredVersionOrZero();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasDeclaredVersion() {
		BigDecimal _declaredVersion = this.getDeclaredVersion();
		return (_declaredVersion != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDeclaredVersionOrZero() {
		int _xifexpression = (int) 0;
		boolean _hasDeclaredVersion = this.hasDeclaredVersion();
		if (_hasDeclaredVersion) {
			_xifexpression = this.getDeclaredVersion().intValue();
		}
		else {
			_xifexpression = 0;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExternal() {
		return this.getDeclaredModifiers().contains(N4Modifier.EXTERNAL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExported() {
		EObject _eContainer = this.eContainer();
		return (_eContainer instanceof ExportDeclaration);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExportedAsDefault() {
		return (this.isExported() && ((ExportDeclaration) this.eContainer()).isDefaultExport());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExportedName() {
		boolean _isExported = this.isExported();
		if (_isExported) {
			EObject _eContainer = this.eContainer();
			final ExportDeclaration exportDecl = ((ExportDeclaration) _eContainer);
			boolean _isDefaultExport = exportDecl.isDefaultExport();
			if (_isDefaultExport) {
				return "default";
			}
			final ExportableElement me = this;
			String _switchResult = null;
			boolean _matched = false;
			if (me instanceof NamedElement) {
				_matched=true;
				_switchResult = ((NamedElement)me).getName();
			}
			if (!_matched) {
				if (me instanceof IdentifiableElement) {
					_matched=true;
					_switchResult = ((IdentifiableElement)me).getName();
				}
			}
			return _switchResult;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isToplevel() {
		EObject _eContainer = this.eContainer();
		if ((_eContainer instanceof ExportDeclaration)) {
			EObject _eContainer_1 = this.eContainer().eContainer();
			return (_eContainer_1 instanceof Script);
		}
		EObject _eContainer_2 = this.eContainer();
		return (_eContainer_2 instanceof Script);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Annotation> getAnnotations() {
		EList<Annotation> _elvis = null;
		AnnotationList _annotationList = this.getAnnotationList();
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
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST:
				return basicSetAnnotationList(null, msgs);
			case N4JSPackage.N4_CLASS_DECLARATION__TYPE_VARS:
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
			case N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST:
				return getAnnotationList();
			case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_MODIFIERS:
				return getDeclaredModifiers();
			case N4JSPackage.N4_CLASS_DECLARATION__NAME:
				return getName();
			case N4JSPackage.N4_CLASS_DECLARATION__TYPE_VARS:
				return getTypeVars();
			case N4JSPackage.N4_CLASS_DECLARATION__TYPING_STRATEGY:
				return getTypingStrategy();
			case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_VERSION:
				return getDeclaredVersion();
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
			case N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST:
				setAnnotationList((AnnotationList)newValue);
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_MODIFIERS:
				getDeclaredModifiers().clear();
				getDeclaredModifiers().addAll((Collection<? extends N4Modifier>)newValue);
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__NAME:
				setName((String)newValue);
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				getTypeVars().addAll((Collection<? extends N4TypeVariable>)newValue);
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__TYPING_STRATEGY:
				setTypingStrategy((TypingStrategy)newValue);
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_VERSION:
				setDeclaredVersion((BigDecimal)newValue);
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
			case N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST:
				setAnnotationList((AnnotationList)null);
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_MODIFIERS:
				getDeclaredModifiers().clear();
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__TYPING_STRATEGY:
				setTypingStrategy(TYPING_STRATEGY_EDEFAULT);
				return;
			case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_VERSION:
				setDeclaredVersion(DECLARED_VERSION_EDEFAULT);
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
			case N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST:
				return annotationList != null;
			case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_MODIFIERS:
				return declaredModifiers != null && !declaredModifiers.isEmpty();
			case N4JSPackage.N4_CLASS_DECLARATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case N4JSPackage.N4_CLASS_DECLARATION__TYPE_VARS:
				return typeVars != null && !typeVars.isEmpty();
			case N4JSPackage.N4_CLASS_DECLARATION__TYPING_STRATEGY:
				return typingStrategy != TYPING_STRATEGY_EDEFAULT;
			case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_VERSION:
				return DECLARED_VERSION_EDEFAULT == null ? declaredVersion != null : !DECLARED_VERSION_EDEFAULT.equals(declaredVersion);
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
		if (baseClass == ScriptElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AnnotableScriptElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST: return N4JSPackage.ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST;
				default: return -1;
			}
		}
		if (baseClass == ModifiableElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_MODIFIERS: return N4JSPackage.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS;
				default: return -1;
			}
		}
		if (baseClass == ExportableElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == N4TypeDeclaration.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_CLASS_DECLARATION__NAME: return N4JSPackage.N4_TYPE_DECLARATION__NAME;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_CLASS_DECLARATION__TYPE_VARS: return N4JSPackage.GENERIC_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == N4ClassifierDeclaration.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_CLASS_DECLARATION__TYPING_STRATEGY: return N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY;
				default: return -1;
			}
		}
		if (baseClass == Versionable.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == VersionedElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_CLASS_DECLARATION__DECLARED_VERSION: return N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION;
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
		if (baseClass == ScriptElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AnnotableScriptElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST: return N4JSPackage.N4_CLASS_DECLARATION__ANNOTATION_LIST;
				default: return -1;
			}
		}
		if (baseClass == ModifiableElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS: return N4JSPackage.N4_CLASS_DECLARATION__DECLARED_MODIFIERS;
				default: return -1;
			}
		}
		if (baseClass == ExportableElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == N4TypeDeclaration.class) {
			switch (baseFeatureID) {
				case N4JSPackage.N4_TYPE_DECLARATION__NAME: return N4JSPackage.N4_CLASS_DECLARATION__NAME;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseFeatureID) {
				case N4JSPackage.GENERIC_DECLARATION__TYPE_VARS: return N4JSPackage.N4_CLASS_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == N4ClassifierDeclaration.class) {
			switch (baseFeatureID) {
				case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY: return N4JSPackage.N4_CLASS_DECLARATION__TYPING_STRATEGY;
				default: return -1;
			}
		}
		if (baseClass == Versionable.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == VersionedElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION: return N4JSPackage.N4_CLASS_DECLARATION__DECLARED_VERSION;
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
				case N4JSPackage.ANNOTABLE_ELEMENT___GET_ANNOTATIONS: return N4JSPackage.N4_CLASS_DECLARATION___GET_ANNOTATIONS;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == N4TypeDefinition.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_TYPE_DEFINITION___IS_EXTERNAL: return N4JSPackage.N4_CLASS_DECLARATION___IS_EXTERNAL;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ScriptElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == AnnotableScriptElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.ANNOTABLE_SCRIPT_ELEMENT___GET_ANNOTATIONS: return N4JSPackage.N4_CLASS_DECLARATION___GET_ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == ModifiableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == ExportableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED: return N4JSPackage.N4_CLASS_DECLARATION___IS_EXPORTED;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT: return N4JSPackage.N4_CLASS_DECLARATION___IS_EXPORTED_AS_DEFAULT;
				case N4JSPackage.EXPORTABLE_ELEMENT___GET_EXPORTED_NAME: return N4JSPackage.N4_CLASS_DECLARATION___GET_EXPORTED_NAME;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_TOPLEVEL: return N4JSPackage.N4_CLASS_DECLARATION___IS_TOPLEVEL;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.N4_CLASS_DECLARATION___GET_NAME;
				default: return -1;
			}
		}
		if (baseClass == N4TypeDeclaration.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_TYPE_DECLARATION___IS_EXTERNAL: return N4JSPackage.N4_CLASS_DECLARATION___IS_EXTERNAL;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == N4ClassifierDeclaration.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == Versionable.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONABLE___GET_VERSION: return N4JSPackage.N4_CLASS_DECLARATION___GET_VERSION;
				default: return -1;
			}
		}
		if (baseClass == VersionedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.VERSIONED_ELEMENT___HAS_DECLARED_VERSION: return N4JSPackage.N4_CLASS_DECLARATION___HAS_DECLARED_VERSION;
				case N4JSPackage.VERSIONED_ELEMENT___GET_DECLARED_VERSION_OR_ZERO: return N4JSPackage.N4_CLASS_DECLARATION___GET_DECLARED_VERSION_OR_ZERO;
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
			case N4JSPackage.N4_CLASS_DECLARATION___IS_ABSTRACT:
				return isAbstract();
			case N4JSPackage.N4_CLASS_DECLARATION___GET_VERSION:
				return getVersion();
			case N4JSPackage.N4_CLASS_DECLARATION___HAS_DECLARED_VERSION:
				return hasDeclaredVersion();
			case N4JSPackage.N4_CLASS_DECLARATION___GET_DECLARED_VERSION_OR_ZERO:
				return getDeclaredVersionOrZero();
			case N4JSPackage.N4_CLASS_DECLARATION___IS_EXTERNAL:
				return isExternal();
			case N4JSPackage.N4_CLASS_DECLARATION___IS_EXPORTED:
				return isExported();
			case N4JSPackage.N4_CLASS_DECLARATION___IS_EXPORTED_AS_DEFAULT:
				return isExportedAsDefault();
			case N4JSPackage.N4_CLASS_DECLARATION___GET_EXPORTED_NAME:
				return getExportedName();
			case N4JSPackage.N4_CLASS_DECLARATION___IS_TOPLEVEL:
				return isToplevel();
			case N4JSPackage.N4_CLASS_DECLARATION___GET_ANNOTATIONS:
				return getAnnotations();
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
		result.append(" (declaredModifiers: ");
		result.append(declaredModifiers);
		result.append(", name: ");
		result.append(name);
		result.append(", typingStrategy: ");
		result.append(typingStrategy);
		result.append(", declaredVersion: ");
		result.append(declaredVersion);
		result.append(')');
		return result.toString();
	}

} //N4ClassDeclarationImpl
