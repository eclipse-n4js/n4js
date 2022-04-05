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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.ModuleSpecifierForm;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.n4JS.N4JSPackage.Literals;

import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.NamedExportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.NamespaceExportSpecifier;

import org.eclipse.n4js.ts.types.TModule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Export Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getModule <em>Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getModuleSpecifierAsText <em>Module Specifier As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getModuleSpecifierForm <em>Module Specifier Form</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getExportedElement <em>Exported Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getDefaultExportedExpression <em>Default Exported Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getNamespaceExport <em>Namespace Export</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#getNamedExports <em>Named Exports</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl#isDefaultExport <em>Default Export</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExportDeclarationImpl extends AnnotableScriptElementImpl implements ExportDeclaration {
	/**
	 * The cached value of the '{@link #getModule() <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModule()
	 * @generated
	 * @ordered
	 */
	protected TModule module;

	/**
	 * The default value of the '{@link #getModuleSpecifierAsText() <em>Module Specifier As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierAsText()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_SPECIFIER_AS_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModuleSpecifierAsText() <em>Module Specifier As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierAsText()
	 * @generated
	 * @ordered
	 */
	protected String moduleSpecifierAsText = MODULE_SPECIFIER_AS_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getModuleSpecifierForm() <em>Module Specifier Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierForm()
	 * @generated
	 * @ordered
	 */
	protected static final ModuleSpecifierForm MODULE_SPECIFIER_FORM_EDEFAULT = ModuleSpecifierForm.UNKNOWN;

	/**
	 * The cached value of the '{@link #getModuleSpecifierForm() <em>Module Specifier Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierForm()
	 * @generated
	 * @ordered
	 */
	protected ModuleSpecifierForm moduleSpecifierForm = MODULE_SPECIFIER_FORM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExportedElement() <em>Exported Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedElement()
	 * @generated
	 * @ordered
	 */
	protected ExportableElement exportedElement;

	/**
	 * The cached value of the '{@link #getDefaultExportedExpression() <em>Default Exported Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultExportedExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression defaultExportedExpression;

	/**
	 * The cached value of the '{@link #getNamespaceExport() <em>Namespace Export</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaceExport()
	 * @generated
	 * @ordered
	 */
	protected NamespaceExportSpecifier namespaceExport;

	/**
	 * The cached value of the '{@link #getNamedExports() <em>Named Exports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedExports()
	 * @generated
	 * @ordered
	 */
	protected EList<NamedExportSpecifier> namedExports;

	/**
	 * The default value of the '{@link #isDefaultExport() <em>Default Export</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefaultExport()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEFAULT_EXPORT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDefaultExport() <em>Default Export</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefaultExport()
	 * @generated
	 * @ordered
	 */
	protected boolean defaultExport = DEFAULT_EXPORT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExportDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.EXPORT_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TModule getModule() {
		if (module != null && module.eIsProxy()) {
			InternalEObject oldModule = (InternalEObject)module;
			module = (TModule)eResolveProxy(oldModule);
			if (module != oldModule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.EXPORT_DECLARATION__MODULE, oldModule, module));
			}
		}
		return module;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TModule basicGetModule() {
		return module;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModule(TModule newModule) {
		TModule oldModule = module;
		module = newModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__MODULE, oldModule, module));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModuleSpecifierAsText() {
		return moduleSpecifierAsText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModuleSpecifierAsText(String newModuleSpecifierAsText) {
		String oldModuleSpecifierAsText = moduleSpecifierAsText;
		moduleSpecifierAsText = newModuleSpecifierAsText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT, oldModuleSpecifierAsText, moduleSpecifierAsText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModuleSpecifierForm getModuleSpecifierForm() {
		return moduleSpecifierForm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModuleSpecifierForm(ModuleSpecifierForm newModuleSpecifierForm) {
		ModuleSpecifierForm oldModuleSpecifierForm = moduleSpecifierForm;
		moduleSpecifierForm = newModuleSpecifierForm == null ? MODULE_SPECIFIER_FORM_EDEFAULT : newModuleSpecifierForm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_FORM, oldModuleSpecifierForm, moduleSpecifierForm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExportableElement getExportedElement() {
		return exportedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExportedElement(ExportableElement newExportedElement, NotificationChain msgs) {
		ExportableElement oldExportedElement = exportedElement;
		exportedElement = newExportedElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT, oldExportedElement, newExportedElement);
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
	public void setExportedElement(ExportableElement newExportedElement) {
		if (newExportedElement != exportedElement) {
			NotificationChain msgs = null;
			if (exportedElement != null)
				msgs = ((InternalEObject)exportedElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT, null, msgs);
			if (newExportedElement != null)
				msgs = ((InternalEObject)newExportedElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT, null, msgs);
			msgs = basicSetExportedElement(newExportedElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT, newExportedElement, newExportedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getDefaultExportedExpression() {
		return defaultExportedExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefaultExportedExpression(Expression newDefaultExportedExpression, NotificationChain msgs) {
		Expression oldDefaultExportedExpression = defaultExportedExpression;
		defaultExportedExpression = newDefaultExportedExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION, oldDefaultExportedExpression, newDefaultExportedExpression);
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
	public void setDefaultExportedExpression(Expression newDefaultExportedExpression) {
		if (newDefaultExportedExpression != defaultExportedExpression) {
			NotificationChain msgs = null;
			if (defaultExportedExpression != null)
				msgs = ((InternalEObject)defaultExportedExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION, null, msgs);
			if (newDefaultExportedExpression != null)
				msgs = ((InternalEObject)newDefaultExportedExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION, null, msgs);
			msgs = basicSetDefaultExportedExpression(newDefaultExportedExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION, newDefaultExportedExpression, newDefaultExportedExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NamespaceExportSpecifier getNamespaceExport() {
		return namespaceExport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNamespaceExport(NamespaceExportSpecifier newNamespaceExport, NotificationChain msgs) {
		NamespaceExportSpecifier oldNamespaceExport = namespaceExport;
		namespaceExport = newNamespaceExport;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__NAMESPACE_EXPORT, oldNamespaceExport, newNamespaceExport);
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
	public void setNamespaceExport(NamespaceExportSpecifier newNamespaceExport) {
		if (newNamespaceExport != namespaceExport) {
			NotificationChain msgs = null;
			if (namespaceExport != null)
				msgs = ((InternalEObject)namespaceExport).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__NAMESPACE_EXPORT, null, msgs);
			if (newNamespaceExport != null)
				msgs = ((InternalEObject)newNamespaceExport).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPORT_DECLARATION__NAMESPACE_EXPORT, null, msgs);
			msgs = basicSetNamespaceExport(newNamespaceExport, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__NAMESPACE_EXPORT, newNamespaceExport, newNamespaceExport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NamedExportSpecifier> getNamedExports() {
		if (namedExports == null) {
			namedExports = new EObjectContainmentEList<NamedExportSpecifier>(NamedExportSpecifier.class, this, N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS);
		}
		return namedExports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDefaultExport() {
		return defaultExport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefaultExport(boolean newDefaultExport) {
		boolean oldDefaultExport = defaultExport;
		defaultExport = newDefaultExport;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT, oldDefaultExport, defaultExport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHollow() {
		return this.getExportedElement().isHollow();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReexport() {
		return this.isReferringToOtherModule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isRetainedAtRuntime() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReferringToOtherModule() {
		Object _eGet = this.eGet(Literals.MODULE_REF__MODULE, false);
		return (_eGet != null);
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				return basicSetExportedElement(null, msgs);
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				return basicSetDefaultExportedExpression(null, msgs);
			case N4JSPackage.EXPORT_DECLARATION__NAMESPACE_EXPORT:
				return basicSetNamespaceExport(null, msgs);
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				return ((InternalEList<?>)getNamedExports()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.EXPORT_DECLARATION__MODULE:
				if (resolve) return getModule();
				return basicGetModule();
			case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT:
				return getModuleSpecifierAsText();
			case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_FORM:
				return getModuleSpecifierForm();
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				return getExportedElement();
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				return getDefaultExportedExpression();
			case N4JSPackage.EXPORT_DECLARATION__NAMESPACE_EXPORT:
				return getNamespaceExport();
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				return getNamedExports();
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT:
				return isDefaultExport();
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
			case N4JSPackage.EXPORT_DECLARATION__MODULE:
				setModule((TModule)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT:
				setModuleSpecifierAsText((String)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_FORM:
				setModuleSpecifierForm((ModuleSpecifierForm)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				setExportedElement((ExportableElement)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				setDefaultExportedExpression((Expression)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__NAMESPACE_EXPORT:
				setNamespaceExport((NamespaceExportSpecifier)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				getNamedExports().clear();
				getNamedExports().addAll((Collection<? extends NamedExportSpecifier>)newValue);
				return;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT:
				setDefaultExport((Boolean)newValue);
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
			case N4JSPackage.EXPORT_DECLARATION__MODULE:
				setModule((TModule)null);
				return;
			case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT:
				setModuleSpecifierAsText(MODULE_SPECIFIER_AS_TEXT_EDEFAULT);
				return;
			case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_FORM:
				setModuleSpecifierForm(MODULE_SPECIFIER_FORM_EDEFAULT);
				return;
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				setExportedElement((ExportableElement)null);
				return;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				setDefaultExportedExpression((Expression)null);
				return;
			case N4JSPackage.EXPORT_DECLARATION__NAMESPACE_EXPORT:
				setNamespaceExport((NamespaceExportSpecifier)null);
				return;
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				getNamedExports().clear();
				return;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT:
				setDefaultExport(DEFAULT_EXPORT_EDEFAULT);
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
			case N4JSPackage.EXPORT_DECLARATION__MODULE:
				return module != null;
			case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT:
				return MODULE_SPECIFIER_AS_TEXT_EDEFAULT == null ? moduleSpecifierAsText != null : !MODULE_SPECIFIER_AS_TEXT_EDEFAULT.equals(moduleSpecifierAsText);
			case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_FORM:
				return moduleSpecifierForm != MODULE_SPECIFIER_FORM_EDEFAULT;
			case N4JSPackage.EXPORT_DECLARATION__EXPORTED_ELEMENT:
				return exportedElement != null;
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION:
				return defaultExportedExpression != null;
			case N4JSPackage.EXPORT_DECLARATION__NAMESPACE_EXPORT:
				return namespaceExport != null;
			case N4JSPackage.EXPORT_DECLARATION__NAMED_EXPORTS:
				return namedExports != null && !namedExports.isEmpty();
			case N4JSPackage.EXPORT_DECLARATION__DEFAULT_EXPORT:
				return defaultExport != DEFAULT_EXPORT_EDEFAULT;
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
		if (baseClass == NamespaceElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ModuleRef.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.EXPORT_DECLARATION__MODULE: return N4JSPackage.MODULE_REF__MODULE;
				case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT: return N4JSPackage.MODULE_REF__MODULE_SPECIFIER_AS_TEXT;
				case N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_FORM: return N4JSPackage.MODULE_REF__MODULE_SPECIFIER_FORM;
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
		if (baseClass == NamespaceElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ModuleRef.class) {
			switch (baseFeatureID) {
				case N4JSPackage.MODULE_REF__MODULE: return N4JSPackage.EXPORT_DECLARATION__MODULE;
				case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_AS_TEXT: return N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT;
				case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_FORM: return N4JSPackage.EXPORT_DECLARATION__MODULE_SPECIFIER_FORM;
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
		if (baseClass == NamespaceElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMESPACE_ELEMENT___GET_NAMESPACE: return N4JSPackage.EXPORT_DECLARATION___GET_NAMESPACE;
				case N4JSPackage.NAMESPACE_ELEMENT___IS_IN_NAMESPACE: return N4JSPackage.EXPORT_DECLARATION___IS_IN_NAMESPACE;
				case N4JSPackage.NAMESPACE_ELEMENT___IS_HOLLOW: return N4JSPackage.EXPORT_DECLARATION___IS_HOLLOW;
				default: return -1;
			}
		}
		if (baseClass == ModuleRef.class) {
			switch (baseOperationID) {
				case N4JSPackage.MODULE_REF___IS_REFERRING_TO_OTHER_MODULE: return N4JSPackage.EXPORT_DECLARATION___IS_REFERRING_TO_OTHER_MODULE;
				case N4JSPackage.MODULE_REF___IS_RETAINED_AT_RUNTIME: return N4JSPackage.EXPORT_DECLARATION___IS_RETAINED_AT_RUNTIME;
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
			case N4JSPackage.EXPORT_DECLARATION___IS_HOLLOW:
				return isHollow();
			case N4JSPackage.EXPORT_DECLARATION___IS_REEXPORT:
				return isReexport();
			case N4JSPackage.EXPORT_DECLARATION___IS_RETAINED_AT_RUNTIME:
				return isRetainedAtRuntime();
			case N4JSPackage.EXPORT_DECLARATION___IS_REFERRING_TO_OTHER_MODULE:
				return isReferringToOtherModule();
			case N4JSPackage.EXPORT_DECLARATION___GET_NAMESPACE:
				return getNamespace();
			case N4JSPackage.EXPORT_DECLARATION___IS_IN_NAMESPACE:
				return isInNamespace();
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
		result.append(" (moduleSpecifierAsText: ");
		result.append(moduleSpecifierAsText);
		result.append(", moduleSpecifierForm: ");
		result.append(moduleSpecifierForm);
		result.append(", defaultExport: ");
		result.append(defaultExport);
		result.append(')');
		return result.toString();
	}

} //ExportDeclarationImpl
