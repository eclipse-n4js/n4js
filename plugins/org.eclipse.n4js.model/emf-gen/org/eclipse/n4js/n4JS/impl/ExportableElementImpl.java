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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.Script;

import org.eclipse.n4js.ts.types.IdentifiableElement;

import org.eclipse.n4js.utils.UtilN4;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Exportable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ExportableElementImpl extends NamespaceElementImpl implements ExportableElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExportableElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.EXPORTABLE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDirectlyExported() {
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
			return ns.isDirectlyExported();
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
	public String getDirectlyExportedName() {
		boolean _isDirectlyExported = this.isDirectlyExported();
		if (_isDirectlyExported) {
			boolean _isDeclaredExported = this.isDeclaredExported();
			if (_isDeclaredExported) {
				EObject _eContainer = this.eContainer();
				final ExportDeclaration exportDecl = ((ExportDeclaration) _eContainer);
				boolean _isDefaultExport = exportDecl.isDefaultExport();
				if (_isDefaultExport) {
					return UtilN4.EXPORT_DEFAULT_NAME;
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
	public boolean isHollow() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == NamespaceElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMESPACE_ELEMENT___IS_HOLLOW: return N4JSPackage.EXPORTABLE_ELEMENT___IS_HOLLOW;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
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
			case N4JSPackage.EXPORTABLE_ELEMENT___IS_DIRECTLY_EXPORTED:
				return isDirectlyExported();
			case N4JSPackage.EXPORTABLE_ELEMENT___IS_DECLARED_EXPORTED:
				return isDeclaredExported();
			case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_BY_NAMESPACE:
				return isExportedByNamespace();
			case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT:
				return isExportedAsDefault();
			case N4JSPackage.EXPORTABLE_ELEMENT___GET_DIRECTLY_EXPORTED_NAME:
				return getDirectlyExportedName();
			case N4JSPackage.EXPORTABLE_ELEMENT___IS_TOPLEVEL:
				return isToplevel();
			case N4JSPackage.EXPORTABLE_ELEMENT___IS_HOLLOW:
				return isHollow();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ExportableElementImpl
