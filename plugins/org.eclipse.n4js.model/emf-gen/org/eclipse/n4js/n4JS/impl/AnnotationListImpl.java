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

import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.Statement;

import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation List</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AnnotationListImpl extends AbstractAnnotationListImpl implements AnnotationList {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotationListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.ANNOTATION_LIST;
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
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == ScriptElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == ControlFlowElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == ExportableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED: return N4JSPackage.ANNOTATION_LIST___IS_EXPORTED;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT: return N4JSPackage.ANNOTATION_LIST___IS_EXPORTED_AS_DEFAULT;
				case N4JSPackage.EXPORTABLE_ELEMENT___GET_EXPORTED_NAME: return N4JSPackage.ANNOTATION_LIST___GET_EXPORTED_NAME;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_TOPLEVEL: return N4JSPackage.ANNOTATION_LIST___IS_TOPLEVEL;
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
			case N4JSPackage.ANNOTATION_LIST___IS_EXPORTED:
				return isExported();
			case N4JSPackage.ANNOTATION_LIST___IS_EXPORTED_AS_DEFAULT:
				return isExportedAsDefault();
			case N4JSPackage.ANNOTATION_LIST___GET_EXPORTED_NAME:
				return getExportedName();
			case N4JSPackage.ANNOTATION_LIST___IS_TOPLEVEL:
				return isToplevel();
		}
		return super.eInvoke(operationID, arguments);
	}

} //AnnotationListImpl
