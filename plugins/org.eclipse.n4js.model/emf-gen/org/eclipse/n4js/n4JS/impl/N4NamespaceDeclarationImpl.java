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

import com.google.common.collect.Iterables;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4AbstractNamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDefinition;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.TypeDefiningElement;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Namespace Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4NamespaceDeclarationImpl#getDefinedType <em>Defined Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4NamespaceDeclarationImpl#getDeclaredModifiers <em>Declared Modifiers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4NamespaceDeclarationImpl extends N4AbstractNamespaceDeclarationImpl implements N4NamespaceDeclaration {
	/**
	 * The cached value of the '{@link #getDefinedType() <em>Defined Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedType()
	 * @generated
	 * @ordered
	 */
	protected Type definedType;

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
	public Type getDefinedType() {
		if (definedType != null && definedType.eIsProxy()) {
			InternalEObject oldDefinedType = (InternalEObject)definedType;
			definedType = (Type)eResolveProxy(oldDefinedType);
			if (definedType != oldDefinedType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.N4_NAMESPACE_DECLARATION__DEFINED_TYPE, oldDefinedType, definedType));
			}
		}
		return definedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetDefinedType() {
		return definedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedType(Type newDefinedType) {
		Type oldDefinedType = definedType;
		definedType = newDefinedType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_NAMESPACE_DECLARATION__DEFINED_TYPE, oldDefinedType, definedType));
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
	public boolean isExternal() {
		return (this.isDeclaredExternal() || this.isDefaultExternal());
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
	public boolean isHollow() {
		final Function1<NamespaceElement, Boolean> _function = new Function1<NamespaceElement, Boolean>() {
			public Boolean apply(final NamespaceElement it) {
				return Boolean.valueOf(it.isHollow());
			}
		};
		final boolean hollow = IterableExtensions.<NamespaceElement>forall(this.getOwnedElementsRaw(), _function);
		return hollow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TNamespace getDefinedNamespace() {
		Type _definedType = this.getDefinedType();
		return ((TNamespace) _definedType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExported() {
		return (this.isDeclaredExported() || this.isExportedByNamespace());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredExported() {
		EObject _eContainer = this.eContainer();
		return (_eContainer instanceof ExportDeclaration);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExportedByNamespace() {
		N4NamespaceDeclaration ns = this.getNamespace();
		if ((ns != null)) {
			return ns.isExported();
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExportedAsDefault() {
		return (this.isDeclaredExported() && ((ExportDeclaration) this.eContainer()).isDefaultExport());
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
			boolean _isDeclaredExported = this.isDeclaredExported();
			if (_isDeclaredExported) {
				EObject _eContainer = this.eContainer();
				final ExportDeclaration exportDecl = ((ExportDeclaration) _eContainer);
				boolean _isDefaultExport = exportDecl.isDefaultExport();
				if (_isDefaultExport) {
					return "default";
				}
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
	public N4NamespaceDeclaration getNamespace() {
		EObject parent = this.eContainer();
		if ((parent instanceof ExportDeclaration)) {
			parent = ((ExportDeclaration)parent).eContainer();
		}
		if ((parent instanceof N4NamespaceDeclaration)) {
			return ((N4NamespaceDeclaration)parent);
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInNamespace() {
		N4NamespaceDeclaration _namespace = this.getNamespace();
		return (_namespace != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredExternal() {
		return this.getDeclaredModifiers().contains(N4Modifier.EXTERNAL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDefaultExternal() {
		EObject parent = this.eContainer();
		if ((parent instanceof ExportDeclaration)) {
			parent = ((ExportDeclaration)parent).eContainer();
		}
		if ((parent instanceof N4NamespaceDeclaration)) {
			return ((N4NamespaceDeclaration)parent).isExternal();
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Annotation> getAllAnnotations() {
		final BasicEList<Annotation> result = XcoreCollectionLiterals.<Annotation>newBasicEList();
		final EObject parent = this.eContainer();
		if ((parent instanceof ExportDeclaration)) {
			EList<Annotation> _annotations = ((ExportDeclaration)parent).getAnnotations();
			Iterables.<Annotation>addAll(result, _annotations);
		}
		EList<Annotation> _annotations_1 = this.getAnnotations();
		Iterables.<Annotation>addAll(result, _annotations_1);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DEFINED_TYPE:
				if (resolve) return getDefinedType();
				return basicGetDefinedType();
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS:
				return getDeclaredModifiers();
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DEFINED_TYPE:
				setDefinedType((Type)newValue);
				return;
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS:
				getDeclaredModifiers().clear();
				getDeclaredModifiers().addAll((Collection<? extends N4Modifier>)newValue);
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DEFINED_TYPE:
				setDefinedType((Type)null);
				return;
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS:
				getDeclaredModifiers().clear();
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DEFINED_TYPE:
				return definedType != null;
			case N4JSPackage.N4_NAMESPACE_DECLARATION__DECLARED_MODIFIERS:
				return declaredModifiers != null && !declaredModifiers.isEmpty();
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
		if (baseClass == AnnotableElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypableElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypeDefiningElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_NAMESPACE_DECLARATION__DEFINED_TYPE: return N4JSPackage.TYPE_DEFINING_ELEMENT__DEFINED_TYPE;
				default: return -1;
			}
		}
		if (baseClass == N4TypeDefinition.class) {
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
		if (baseClass == ScriptElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == NamespaceElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ExportableElement.class) {
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
		if (baseClass == AnnotableElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypableElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypeDefiningElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.TYPE_DEFINING_ELEMENT__DEFINED_TYPE: return N4JSPackage.N4_NAMESPACE_DECLARATION__DEFINED_TYPE;
				default: return -1;
			}
		}
		if (baseClass == N4TypeDefinition.class) {
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
		if (baseClass == ScriptElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == NamespaceElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ExportableElement.class) {
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
		if (baseClass == N4AbstractNamespaceDeclaration.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_ABSTRACT_NAMESPACE_DECLARATION___GET_DEFINED_NAMESPACE: return N4JSPackage.N4_NAMESPACE_DECLARATION___GET_DEFINED_NAMESPACE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == AnnotableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.ANNOTABLE_ELEMENT___GET_ANNOTATIONS: return N4JSPackage.N4_NAMESPACE_DECLARATION___GET_ANNOTATIONS;
				case N4JSPackage.ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS: return N4JSPackage.N4_NAMESPACE_DECLARATION___GET_ALL_ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == TypableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == TypeDefiningElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == N4TypeDefinition.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_TYPE_DEFINITION___IS_EXTERNAL: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXTERNAL;
				default: return -1;
			}
		}
		if (baseClass == ModifiableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.MODIFIABLE_ELEMENT___IS_DECLARED_EXTERNAL: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_DECLARED_EXTERNAL;
				case N4JSPackage.MODIFIABLE_ELEMENT___IS_DEFAULT_EXTERNAL: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_DEFAULT_EXTERNAL;
				default: return -1;
			}
		}
		if (baseClass == ScriptElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == NamespaceElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMESPACE_ELEMENT___GET_NAMESPACE: return N4JSPackage.N4_NAMESPACE_DECLARATION___GET_NAMESPACE;
				case N4JSPackage.NAMESPACE_ELEMENT___IS_IN_NAMESPACE: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_IN_NAMESPACE;
				case N4JSPackage.NAMESPACE_ELEMENT___IS_HOLLOW: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_HOLLOW;
				default: return -1;
			}
		}
		if (baseClass == ExportableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_DECLARED_EXPORTED: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_DECLARED_EXPORTED;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_BY_NAMESPACE: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED_BY_NAMESPACE;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED_AS_DEFAULT;
				case N4JSPackage.EXPORTABLE_ELEMENT___GET_EXPORTED_NAME: return N4JSPackage.N4_NAMESPACE_DECLARATION___GET_EXPORTED_NAME;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_TOPLEVEL: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_TOPLEVEL;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_HOLLOW: return N4JSPackage.N4_NAMESPACE_DECLARATION___IS_HOLLOW;
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
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_HOLLOW:
				return isHollow();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___GET_DEFINED_NAMESPACE:
				return getDefinedNamespace();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED:
				return isExported();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_DECLARED_EXPORTED:
				return isDeclaredExported();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED_BY_NAMESPACE:
				return isExportedByNamespace();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_EXPORTED_AS_DEFAULT:
				return isExportedAsDefault();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___GET_EXPORTED_NAME:
				return getExportedName();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_TOPLEVEL:
				return isToplevel();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___GET_NAMESPACE:
				return getNamespace();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_IN_NAMESPACE:
				return isInNamespace();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_DECLARED_EXTERNAL:
				return isDeclaredExternal();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___IS_DEFAULT_EXTERNAL:
				return isDefaultExternal();
			case N4JSPackage.N4_NAMESPACE_DECLARATION___GET_ALL_ANNOTATIONS:
				return getAllAnnotations();
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
		result.append(')');
		return result.toString();
	}

} //N4NamespaceDeclarationImpl
