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

import com.google.common.collect.Iterables;

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

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TExportingElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.ts.types.TypesPackage.Literals;

import org.eclipse.xtext.EcoreUtil2;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TNamespace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getExportDefinitions <em>Export Definitions</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getExportedVariables <em>Exported Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getLocalVariables <em>Local Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getExposedLocalVariables <em>Exposed Local Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getNamespaces <em>Namespaces</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getDeclaredTypeAccessModifier <em>Declared Type Access Modifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#isDeclaredProvidedByRuntime <em>Declared Provided By Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#getAstElement <em>Ast Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TNamespaceImpl#isExternal <em>External</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TNamespaceImpl extends TypeImpl implements TNamespace {
	/**
	 * The cached value of the '{@link #getExportDefinitions() <em>Export Definitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportDefinitions()
	 * @generated
	 * @ordered
	 */
	protected EList<ExportDefinition> exportDefinitions;

	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> types;

	/**
	 * The cached value of the '{@link #getExportedVariables() <em>Exported Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<TVariable> exportedVariables;

	/**
	 * The cached value of the '{@link #getLocalVariables() <em>Local Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<TVariable> localVariables;

	/**
	 * The cached value of the '{@link #getExposedLocalVariables() <em>Exposed Local Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExposedLocalVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<TVariable> exposedLocalVariables;

	/**
	 * The cached value of the '{@link #getNamespaces() <em>Namespaces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaces()
	 * @generated
	 * @ordered
	 */
	protected EList<TNamespace> namespaces;

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
	public EList<ExportDefinition> getExportDefinitions() {
		if (exportDefinitions == null) {
			exportDefinitions = new EObjectContainmentEList<ExportDefinition>(ExportDefinition.class, this, TypesPackage.TNAMESPACE__EXPORT_DEFINITIONS);
		}
		return exportDefinitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Type> getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.TNAMESPACE__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TVariable> getExportedVariables() {
		if (exportedVariables == null) {
			exportedVariables = new EObjectContainmentEList<TVariable>(TVariable.class, this, TypesPackage.TNAMESPACE__EXPORTED_VARIABLES);
		}
		return exportedVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TVariable> getLocalVariables() {
		if (localVariables == null) {
			localVariables = new EObjectContainmentEList<TVariable>(TVariable.class, this, TypesPackage.TNAMESPACE__LOCAL_VARIABLES);
		}
		return localVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TVariable> getExposedLocalVariables() {
		if (exposedLocalVariables == null) {
			exposedLocalVariables = new EObjectContainmentEList<TVariable>(TVariable.class, this, TypesPackage.TNAMESPACE__EXPOSED_LOCAL_VARIABLES);
		}
		return exposedLocalVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TNamespace> getNamespaces() {
		if (namespaces == null) {
			namespaces = new EObjectContainmentEList<TNamespace>(TNamespace.class, this, TypesPackage.TNAMESPACE__NAMESPACES);
		}
		return namespaces;
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
	public EObject getAstElementNoResolve() {
		Object _eGet = this.eGet(Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT, false);
		final EObject astElem = ((EObject) _eGet);
		if (((astElem != null) && (!astElem.eIsProxy()))) {
			return astElem;
		}
		return null;
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
	public Iterable<? extends TExportableElement> getExportableElements() {
		return Iterables.<TExportableElement>concat(this.getTypes(), this.getExportedVariables(), this.getLocalVariables(), this.getExposedLocalVariables(), this.getNamespaces());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<? extends AbstractNamespace> getAllNamespaces() {
		boolean _isEmpty = this.getNamespaces().isEmpty();
		if (_isEmpty) {
			return java.util.Collections.<AbstractNamespace>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<AbstractNamespace>newArrayList(this));
		}
		final Function1<TNamespace, Iterable<? extends AbstractNamespace>> _function = new Function1<TNamespace, Iterable<? extends AbstractNamespace>>() {
			public Iterable<? extends AbstractNamespace> apply(final TNamespace it) {
				return it.getAllNamespaces();
			}
		};
		Iterable<AbstractNamespace> _flatten = Iterables.<AbstractNamespace>concat(XcoreEListExtensions.<TNamespace, Iterable<? extends AbstractNamespace>>map(this.getNamespaces(), _function));
		return Iterables.<AbstractNamespace>concat(java.util.Collections.<AbstractNamespace>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<AbstractNamespace>newArrayList(this)), _flatten);
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
	public void clearTransientElements() {
		this.getLocalVariables().clear();
		EList<TNamespace> _namespaces = this.getNamespaces();
		for (final TNamespace child : _namespaces) {
			child.clearTransientElements();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TNAMESPACE__EXPORT_DEFINITIONS:
				return ((InternalEList<?>)getExportDefinitions()).basicRemove(otherEnd, msgs);
			case TypesPackage.TNAMESPACE__TYPES:
				return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.TNAMESPACE__EXPORTED_VARIABLES:
				return ((InternalEList<?>)getExportedVariables()).basicRemove(otherEnd, msgs);
			case TypesPackage.TNAMESPACE__LOCAL_VARIABLES:
				return ((InternalEList<?>)getLocalVariables()).basicRemove(otherEnd, msgs);
			case TypesPackage.TNAMESPACE__EXPOSED_LOCAL_VARIABLES:
				return ((InternalEList<?>)getExposedLocalVariables()).basicRemove(otherEnd, msgs);
			case TypesPackage.TNAMESPACE__NAMESPACES:
				return ((InternalEList<?>)getNamespaces()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.TNAMESPACE__EXPORT_DEFINITIONS:
				return getExportDefinitions();
			case TypesPackage.TNAMESPACE__TYPES:
				return getTypes();
			case TypesPackage.TNAMESPACE__EXPORTED_VARIABLES:
				return getExportedVariables();
			case TypesPackage.TNAMESPACE__LOCAL_VARIABLES:
				return getLocalVariables();
			case TypesPackage.TNAMESPACE__EXPOSED_LOCAL_VARIABLES:
				return getExposedLocalVariables();
			case TypesPackage.TNAMESPACE__NAMESPACES:
				return getNamespaces();
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
			case TypesPackage.TNAMESPACE__EXPORT_DEFINITIONS:
				getExportDefinitions().clear();
				getExportDefinitions().addAll((Collection<? extends ExportDefinition>)newValue);
				return;
			case TypesPackage.TNAMESPACE__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case TypesPackage.TNAMESPACE__EXPORTED_VARIABLES:
				getExportedVariables().clear();
				getExportedVariables().addAll((Collection<? extends TVariable>)newValue);
				return;
			case TypesPackage.TNAMESPACE__LOCAL_VARIABLES:
				getLocalVariables().clear();
				getLocalVariables().addAll((Collection<? extends TVariable>)newValue);
				return;
			case TypesPackage.TNAMESPACE__EXPOSED_LOCAL_VARIABLES:
				getExposedLocalVariables().clear();
				getExposedLocalVariables().addAll((Collection<? extends TVariable>)newValue);
				return;
			case TypesPackage.TNAMESPACE__NAMESPACES:
				getNamespaces().clear();
				getNamespaces().addAll((Collection<? extends TNamespace>)newValue);
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
			case TypesPackage.TNAMESPACE__EXPORT_DEFINITIONS:
				getExportDefinitions().clear();
				return;
			case TypesPackage.TNAMESPACE__TYPES:
				getTypes().clear();
				return;
			case TypesPackage.TNAMESPACE__EXPORTED_VARIABLES:
				getExportedVariables().clear();
				return;
			case TypesPackage.TNAMESPACE__LOCAL_VARIABLES:
				getLocalVariables().clear();
				return;
			case TypesPackage.TNAMESPACE__EXPOSED_LOCAL_VARIABLES:
				getExposedLocalVariables().clear();
				return;
			case TypesPackage.TNAMESPACE__NAMESPACES:
				getNamespaces().clear();
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
			case TypesPackage.TNAMESPACE__EXPORT_DEFINITIONS:
				return exportDefinitions != null && !exportDefinitions.isEmpty();
			case TypesPackage.TNAMESPACE__TYPES:
				return types != null && !types.isEmpty();
			case TypesPackage.TNAMESPACE__EXPORTED_VARIABLES:
				return exportedVariables != null && !exportedVariables.isEmpty();
			case TypesPackage.TNAMESPACE__LOCAL_VARIABLES:
				return localVariables != null && !localVariables.isEmpty();
			case TypesPackage.TNAMESPACE__EXPOSED_LOCAL_VARIABLES:
				return exposedLocalVariables != null && !exposedLocalVariables.isEmpty();
			case TypesPackage.TNAMESPACE__NAMESPACES:
				return namespaces != null && !namespaces.isEmpty();
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
		if (baseClass == TExportingElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TNAMESPACE__EXPORT_DEFINITIONS: return TypesPackage.TEXPORTING_ELEMENT__EXPORT_DEFINITIONS;
				default: return -1;
			}
		}
		if (baseClass == AbstractNamespace.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TNAMESPACE__TYPES: return TypesPackage.ABSTRACT_NAMESPACE__TYPES;
				case TypesPackage.TNAMESPACE__EXPORTED_VARIABLES: return TypesPackage.ABSTRACT_NAMESPACE__EXPORTED_VARIABLES;
				case TypesPackage.TNAMESPACE__LOCAL_VARIABLES: return TypesPackage.ABSTRACT_NAMESPACE__LOCAL_VARIABLES;
				case TypesPackage.TNAMESPACE__EXPOSED_LOCAL_VARIABLES: return TypesPackage.ABSTRACT_NAMESPACE__EXPOSED_LOCAL_VARIABLES;
				case TypesPackage.TNAMESPACE__NAMESPACES: return TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES;
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
		if (baseClass == TExportingElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TEXPORTING_ELEMENT__EXPORT_DEFINITIONS: return TypesPackage.TNAMESPACE__EXPORT_DEFINITIONS;
				default: return -1;
			}
		}
		if (baseClass == AbstractNamespace.class) {
			switch (baseFeatureID) {
				case TypesPackage.ABSTRACT_NAMESPACE__TYPES: return TypesPackage.TNAMESPACE__TYPES;
				case TypesPackage.ABSTRACT_NAMESPACE__EXPORTED_VARIABLES: return TypesPackage.TNAMESPACE__EXPORTED_VARIABLES;
				case TypesPackage.ABSTRACT_NAMESPACE__LOCAL_VARIABLES: return TypesPackage.TNAMESPACE__LOCAL_VARIABLES;
				case TypesPackage.ABSTRACT_NAMESPACE__EXPOSED_LOCAL_VARIABLES: return TypesPackage.TNAMESPACE__EXPOSED_LOCAL_VARIABLES;
				case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES: return TypesPackage.TNAMESPACE__NAMESPACES;
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
		if (baseClass == IdentifiableElement.class) {
			switch (baseOperationID) {
				case TypesPackage.IDENTIFIABLE_ELEMENT___GET_CONTAINING_MODULE: return TypesPackage.TNAMESPACE___GET_CONTAINING_MODULE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TNAMESPACE___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.TYPE___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TNAMESPACE___GET_TYPE_ACCESS_MODIFIER;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TExportingElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == AbstractNamespace.class) {
			switch (baseOperationID) {
				case TypesPackage.ABSTRACT_NAMESPACE___GET_EXPORTABLE_ELEMENTS: return TypesPackage.TNAMESPACE___GET_EXPORTABLE_ELEMENTS;
				case TypesPackage.ABSTRACT_NAMESPACE___GET_ALL_NAMESPACES: return TypesPackage.TNAMESPACE___GET_ALL_NAMESPACES;
				case TypesPackage.ABSTRACT_NAMESPACE___GET_CONTAINING_MODULE: return TypesPackage.TNAMESPACE___GET_CONTAINING_MODULE;
				case TypesPackage.ABSTRACT_NAMESPACE___CLEAR_TRANSIENT_ELEMENTS: return TypesPackage.TNAMESPACE___CLEAR_TRANSIENT_ELEMENTS;
				default: return -1;
			}
		}
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseOperationID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TNAMESPACE___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TNAMESPACE___GET_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___GET_DEFAULT_TYPE_ACCESS_MODIFIER: return TypesPackage.TNAMESPACE___GET_DEFAULT_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_DIRECTLY_EXPORTED: return TypesPackage.TNAMESPACE___IS_DIRECTLY_EXPORTED;
				default: return -1;
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseOperationID) {
				case TypesPackage.SYNTAX_RELATED_TELEMENT___GET_AST_ELEMENT_NO_RESOLVE: return TypesPackage.TNAMESPACE___GET_AST_ELEMENT_NO_RESOLVE;
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
			case TypesPackage.TNAMESPACE___GET_AST_ELEMENT_NO_RESOLVE:
				return getAstElementNoResolve();
			case TypesPackage.TNAMESPACE___IS_PROVIDED_BY_RUNTIME:
				return isProvidedByRuntime();
			case TypesPackage.TNAMESPACE___GET_TYPE_ACCESS_MODIFIER:
				return getTypeAccessModifier();
			case TypesPackage.TNAMESPACE___GET_DEFAULT_TYPE_ACCESS_MODIFIER:
				return getDefaultTypeAccessModifier();
			case TypesPackage.TNAMESPACE___GET_EXPORTABLE_ELEMENTS:
				return getExportableElements();
			case TypesPackage.TNAMESPACE___GET_ALL_NAMESPACES:
				return getAllNamespaces();
			case TypesPackage.TNAMESPACE___GET_CONTAINING_MODULE:
				return getContainingModule();
			case TypesPackage.TNAMESPACE___CLEAR_TRANSIENT_ELEMENTS:
				clearTransientElements();
				return null;
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
		result.append(", external: ");
		result.append(external);
		result.append(')');
		return result.toString();
	}

} //TNamespaceImpl
