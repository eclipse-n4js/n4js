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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;
import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.AnnotableScriptElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationContainer;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;

import org.eclipse.n4js.ts.types.IdentifiableElement;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.VariableStatementImpl#getVarDeclsOrBindings <em>Var Decls Or Bindings</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.VariableStatementImpl#getVarStmtKeyword <em>Var Stmt Keyword</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.VariableStatementImpl#getAnnotationList <em>Annotation List</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.VariableStatementImpl#getDeclaredModifiers <em>Declared Modifiers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariableStatementImpl extends StatementImpl implements VariableStatement {
	/**
	 * The cached value of the '{@link #getVarDeclsOrBindings() <em>Var Decls Or Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarDeclsOrBindings()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableDeclarationOrBinding> varDeclsOrBindings;

	/**
	 * The default value of the '{@link #getVarStmtKeyword() <em>Var Stmt Keyword</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarStmtKeyword()
	 * @generated
	 * @ordered
	 */
	protected static final VariableStatementKeyword VAR_STMT_KEYWORD_EDEFAULT = VariableStatementKeyword.VAR;

	/**
	 * The cached value of the '{@link #getVarStmtKeyword() <em>Var Stmt Keyword</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarStmtKeyword()
	 * @generated
	 * @ordered
	 */
	protected VariableStatementKeyword varStmtKeyword = VAR_STMT_KEYWORD_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.VARIABLE_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableDeclarationOrBinding> getVarDeclsOrBindings() {
		if (varDeclsOrBindings == null) {
			varDeclsOrBindings = new EObjectContainmentEList<VariableDeclarationOrBinding>(VariableDeclarationOrBinding.class, this, N4JSPackage.VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS);
		}
		return varDeclsOrBindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VariableStatementKeyword getVarStmtKeyword() {
		return varStmtKeyword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVarStmtKeyword(VariableStatementKeyword newVarStmtKeyword) {
		VariableStatementKeyword oldVarStmtKeyword = varStmtKeyword;
		varStmtKeyword = newVarStmtKeyword == null ? VAR_STMT_KEYWORD_EDEFAULT : newVarStmtKeyword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.VARIABLE_STATEMENT__VAR_STMT_KEYWORD, oldVarStmtKeyword, varStmtKeyword));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST, oldAnnotationList, newAnnotationList);
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
				msgs = ((InternalEObject)annotationList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST, null, msgs);
			if (newAnnotationList != null)
				msgs = ((InternalEObject)newAnnotationList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST, null, msgs);
			msgs = basicSetAnnotationList(newAnnotationList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST, newAnnotationList, newAnnotationList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4Modifier> getDeclaredModifiers() {
		if (declaredModifiers == null) {
			declaredModifiers = new EDataTypeEList<N4Modifier>(N4Modifier.class, this, N4JSPackage.VARIABLE_STATEMENT__DECLARED_MODIFIERS);
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
	public boolean isHollow() {
		return false;
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
	public EList<VariableDeclaration> getVarDecl() {
		final Function1<VariableDeclarationOrBinding, EList<VariableDeclaration>> _function = new Function1<VariableDeclarationOrBinding, EList<VariableDeclaration>>() {
			public EList<VariableDeclaration> apply(final VariableDeclarationOrBinding it) {
				return it.getAllVariableDeclarations();
			}
		};
		return ECollections.<VariableDeclaration>toEList(Iterables.<VariableDeclaration>concat(XcoreEListExtensions.<VariableDeclarationOrBinding, EList<VariableDeclaration>>map(this.getVarDeclsOrBindings(), _function)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBlockScoped() {
		boolean _switchResult = false;
		VariableStatementKeyword _varStmtKeyword = this.getVarStmtKeyword();
		if (_varStmtKeyword != null) {
			switch (_varStmtKeyword) {
				case LET:
					_switchResult = true;
					break;
				case CONST:
					_switchResult = true;
					break;
				case VAR:
					_switchResult = false;
					break;
				default:
					VariableStatementKeyword _varStmtKeyword_1 = this.getVarStmtKeyword();
					String _plus = ("unsupported enum literal: " + _varStmtKeyword_1);
					throw new UnsupportedOperationException(_plus);
			}
		}
		else {
			VariableStatementKeyword _varStmtKeyword_1 = this.getVarStmtKeyword();
			String _plus = ("unsupported enum literal: " + _varStmtKeyword_1);
			throw new UnsupportedOperationException(_plus);
		}
		return _switchResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS:
				return ((InternalEList<?>)getVarDeclsOrBindings()).basicRemove(otherEnd, msgs);
			case N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST:
				return basicSetAnnotationList(null, msgs);
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
			case N4JSPackage.VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS:
				return getVarDeclsOrBindings();
			case N4JSPackage.VARIABLE_STATEMENT__VAR_STMT_KEYWORD:
				return getVarStmtKeyword();
			case N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST:
				return getAnnotationList();
			case N4JSPackage.VARIABLE_STATEMENT__DECLARED_MODIFIERS:
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
			case N4JSPackage.VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS:
				getVarDeclsOrBindings().clear();
				getVarDeclsOrBindings().addAll((Collection<? extends VariableDeclarationOrBinding>)newValue);
				return;
			case N4JSPackage.VARIABLE_STATEMENT__VAR_STMT_KEYWORD:
				setVarStmtKeyword((VariableStatementKeyword)newValue);
				return;
			case N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST:
				setAnnotationList((AnnotationList)newValue);
				return;
			case N4JSPackage.VARIABLE_STATEMENT__DECLARED_MODIFIERS:
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
			case N4JSPackage.VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS:
				getVarDeclsOrBindings().clear();
				return;
			case N4JSPackage.VARIABLE_STATEMENT__VAR_STMT_KEYWORD:
				setVarStmtKeyword(VAR_STMT_KEYWORD_EDEFAULT);
				return;
			case N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST:
				setAnnotationList((AnnotationList)null);
				return;
			case N4JSPackage.VARIABLE_STATEMENT__DECLARED_MODIFIERS:
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
			case N4JSPackage.VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS:
				return varDeclsOrBindings != null && !varDeclsOrBindings.isEmpty();
			case N4JSPackage.VARIABLE_STATEMENT__VAR_STMT_KEYWORD:
				return varStmtKeyword != VAR_STMT_KEYWORD_EDEFAULT;
			case N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST:
				return annotationList != null;
			case N4JSPackage.VARIABLE_STATEMENT__DECLARED_MODIFIERS:
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
		if (baseClass == VariableDeclarationContainer.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS: return N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS;
				case N4JSPackage.VARIABLE_STATEMENT__VAR_STMT_KEYWORD: return N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD;
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
		if (baseClass == AnnotableElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AnnotableScriptElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST: return N4JSPackage.ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST;
				default: return -1;
			}
		}
		if (baseClass == ModifiableElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.VARIABLE_STATEMENT__DECLARED_MODIFIERS: return N4JSPackage.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS;
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
		if (baseClass == VariableDeclarationContainer.class) {
			switch (baseFeatureID) {
				case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS: return N4JSPackage.VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS;
				case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD: return N4JSPackage.VARIABLE_STATEMENT__VAR_STMT_KEYWORD;
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
		if (baseClass == AnnotableElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == AnnotableScriptElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST: return N4JSPackage.VARIABLE_STATEMENT__ANNOTATION_LIST;
				default: return -1;
			}
		}
		if (baseClass == ModifiableElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS: return N4JSPackage.VARIABLE_STATEMENT__DECLARED_MODIFIERS;
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
		if (baseClass == VariableDeclarationContainer.class) {
			switch (baseOperationID) {
				case N4JSPackage.VARIABLE_DECLARATION_CONTAINER___GET_VAR_DECL: return N4JSPackage.VARIABLE_STATEMENT___GET_VAR_DECL;
				case N4JSPackage.VARIABLE_DECLARATION_CONTAINER___IS_BLOCK_SCOPED: return N4JSPackage.VARIABLE_STATEMENT___IS_BLOCK_SCOPED;
				default: return -1;
			}
		}
		if (baseClass == NamespaceElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMESPACE_ELEMENT___GET_NAMESPACE: return N4JSPackage.VARIABLE_STATEMENT___GET_NAMESPACE;
				case N4JSPackage.NAMESPACE_ELEMENT___IS_IN_NAMESPACE: return N4JSPackage.VARIABLE_STATEMENT___IS_IN_NAMESPACE;
				case N4JSPackage.NAMESPACE_ELEMENT___IS_HOLLOW: return N4JSPackage.VARIABLE_STATEMENT___IS_HOLLOW;
				default: return -1;
			}
		}
		if (baseClass == ExportableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED: return N4JSPackage.VARIABLE_STATEMENT___IS_EXPORTED;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_DECLARED_EXPORTED: return N4JSPackage.VARIABLE_STATEMENT___IS_DECLARED_EXPORTED;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_BY_NAMESPACE: return N4JSPackage.VARIABLE_STATEMENT___IS_EXPORTED_BY_NAMESPACE;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT: return N4JSPackage.VARIABLE_STATEMENT___IS_EXPORTED_AS_DEFAULT;
				case N4JSPackage.EXPORTABLE_ELEMENT___GET_EXPORTED_NAME: return N4JSPackage.VARIABLE_STATEMENT___GET_EXPORTED_NAME;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_TOPLEVEL: return N4JSPackage.VARIABLE_STATEMENT___IS_TOPLEVEL;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_HOLLOW: return N4JSPackage.VARIABLE_STATEMENT___IS_HOLLOW;
				default: return -1;
			}
		}
		if (baseClass == AnnotableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.ANNOTABLE_ELEMENT___GET_ANNOTATIONS: return N4JSPackage.VARIABLE_STATEMENT___GET_ANNOTATIONS;
				case N4JSPackage.ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS: return N4JSPackage.VARIABLE_STATEMENT___GET_ALL_ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == AnnotableScriptElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.ANNOTABLE_SCRIPT_ELEMENT___GET_ANNOTATIONS: return N4JSPackage.VARIABLE_STATEMENT___GET_ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == ModifiableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.MODIFIABLE_ELEMENT___IS_EXTERNAL: return N4JSPackage.VARIABLE_STATEMENT___IS_EXTERNAL;
				case N4JSPackage.MODIFIABLE_ELEMENT___IS_DECLARED_EXTERNAL: return N4JSPackage.VARIABLE_STATEMENT___IS_DECLARED_EXTERNAL;
				case N4JSPackage.MODIFIABLE_ELEMENT___IS_DEFAULT_EXTERNAL: return N4JSPackage.VARIABLE_STATEMENT___IS_DEFAULT_EXTERNAL;
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
			case N4JSPackage.VARIABLE_STATEMENT___IS_EXTERNAL:
				return isExternal();
			case N4JSPackage.VARIABLE_STATEMENT___IS_DECLARED_EXTERNAL:
				return isDeclaredExternal();
			case N4JSPackage.VARIABLE_STATEMENT___IS_DEFAULT_EXTERNAL:
				return isDefaultExternal();
			case N4JSPackage.VARIABLE_STATEMENT___GET_ANNOTATIONS:
				return getAnnotations();
			case N4JSPackage.VARIABLE_STATEMENT___GET_ALL_ANNOTATIONS:
				return getAllAnnotations();
			case N4JSPackage.VARIABLE_STATEMENT___IS_EXPORTED:
				return isExported();
			case N4JSPackage.VARIABLE_STATEMENT___IS_DECLARED_EXPORTED:
				return isDeclaredExported();
			case N4JSPackage.VARIABLE_STATEMENT___IS_EXPORTED_BY_NAMESPACE:
				return isExportedByNamespace();
			case N4JSPackage.VARIABLE_STATEMENT___IS_EXPORTED_AS_DEFAULT:
				return isExportedAsDefault();
			case N4JSPackage.VARIABLE_STATEMENT___GET_EXPORTED_NAME:
				return getExportedName();
			case N4JSPackage.VARIABLE_STATEMENT___IS_TOPLEVEL:
				return isToplevel();
			case N4JSPackage.VARIABLE_STATEMENT___IS_HOLLOW:
				return isHollow();
			case N4JSPackage.VARIABLE_STATEMENT___GET_NAMESPACE:
				return getNamespace();
			case N4JSPackage.VARIABLE_STATEMENT___IS_IN_NAMESPACE:
				return isInNamespace();
			case N4JSPackage.VARIABLE_STATEMENT___GET_VAR_DECL:
				return getVarDecl();
			case N4JSPackage.VARIABLE_STATEMENT___IS_BLOCK_SCOPED:
				return isBlockScoped();
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
		result.append(" (varStmtKeyword: ");
		result.append(varStmtKeyword);
		result.append(", declaredModifiers: ");
		result.append(declaredModifiers);
		result.append(')');
		return result.toString();
	}

} //VariableStatementImpl
