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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDefinition;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;

import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Namespace Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4NamespaceDeclarationImpl#getDeclaredModifiers <em>Declared Modifiers</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4NamespaceDeclarationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4NamespaceDeclarationImpl#getOwnedElementsRaw <em>Owned Elements Raw</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4NamespaceDeclarationImpl extends N4TypeDefinitionImpl implements N4NamespaceDeclaration {
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
	 * The cached value of the '{@link #getOwnedElementsRaw() <em>Owned Elements Raw</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedElementsRaw()
	 * @generated
	 * @ordered
	 */
	protected EList<ScriptElement> ownedElementsRaw;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4NamespaceDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_NAMESPACE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4Modifier> getDeclaredModifiers() {
		if (declaredModifiers == null) {
			declaredModifiers = new EDataTypeEList<N4Modifier>(N4Modifier.class, this, N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS);
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_NAMESPACE_DECLARATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ScriptElement> getOwnedElementsRaw() {
		if (ownedElementsRaw == null) {
			ownedElementsRaw = new EObjectContainmentEList<ScriptElement>(ScriptElement.class, this, N4JSPackage.N4_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW);
		}
		return ownedElementsRaw;
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
	public EList<Annotation> getAnnotations() {
		return XcoreCollectionLiterals.<Annotation>emptyEList();
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
	public boolean appliesOnlyToBlockScopedElements() {
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW:
				return ((InternalEList<?>)getOwnedElementsRaw()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS:
				return getDeclaredModifiers();
			case N4JSPackage.N4_NAMESPACE_DECLARATION__NAME:
				return getName();
			case N4JSPackage.N4_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW:
				return getOwnedElementsRaw();
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS:
				getDeclaredModifiers().clear();
				getDeclaredModifiers().addAll((Collection<? extends N4Modifier>)newValue);
				return;
			case N4JSPackage.N4_NAMESPACE_DECLARATION__NAME:
				setName((String)newValue);
				return;
			case N4JSPackage.N4_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW:
				getOwnedElementsRaw().clear();
				getOwnedElementsRaw().addAll((Collection<? extends ScriptElement>)newValue);
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS:
				getDeclaredModifiers().clear();
				return;
			case N4JSPackage.N4_NAMESPACE_DECLARATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case N4JSPackage.N4_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW:
				getOwnedElementsRaw().clear();
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS:
				return declaredModifiers != null && !declaredModifiers.isEmpty();
			case N4JSPackage.N4_NAMESPACE_DECLARATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case N4JSPackage.N4_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW:
				return ownedElementsRaw != null && !ownedElementsRaw.isEmpty();
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
		if (baseClass == VariableEnvironmentElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ScriptElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ModifiableElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS: return N4JSPackage.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS;
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
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == VariableEnvironmentElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ScriptElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ModifiableElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS: return N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS;
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
				case N4JSPackage.ANNOTABLE_ELEMENT___GET_ANNOTATIONS: return N4JSPackage.N4_NAMESPACE_DECLARATION___GET_ANNOTATIONS;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == N4TypeDefinition.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_TYPE_DEFINITION___IS_EXTERNAL: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXTERNAL;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == VariableEnvironmentElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS: return N4JSPackage.N4_NAMESPACE_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;
				default: return -1;
			}
		}
		if (baseClass == ScriptElement.class) {
			switch (baseOperationID) {
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
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED_AS_DEFAULT;
				case N4JSPackage.EXPORTABLE_ELEMENT___GET_EXPORTED_NAME: return N4JSPackage.N4_NAMESPACE_DECLARATION___GET_EXPORTED_NAME;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_TOPLEVEL: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_TOPLEVEL;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.N4_NAMESPACE_DECLARATION___GET_NAME;
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXTERNAL:
				return isExternal();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___GET_ANNOTATIONS:
				return getAnnotations();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED:
				return isExported();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED_AS_DEFAULT:
				return isExportedAsDefault();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___GET_EXPORTED_NAME:
				return getExportedName();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_TOPLEVEL:
				return isToplevel();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS:
				return appliesOnlyToBlockScopedElements();
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
		result.append(')');
		return result.toString();
	}

} //N4NamespaceDeclarationImpl
