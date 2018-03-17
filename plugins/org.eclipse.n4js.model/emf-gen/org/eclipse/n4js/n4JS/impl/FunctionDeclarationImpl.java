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

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.LocalArgumentsVariable;
import org.eclipse.n4js.n4JS.MigrationContextVariable;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.ThisArgProvider;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.n4JS.VersionedElement;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;

import org.eclipse.n4js.utils.EcoreUtilN4;

import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#getDeclaredModifiers <em>Declared Modifiers</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#get_lok <em>lok</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#getDefinedType <em>Defined Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#getDeclaredVersion <em>Declared Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#getFpars <em>Fpars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#getReturnTypeRef <em>Return Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#isGenerator <em>Generator</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#isDeclaredAsync <em>Declared Async</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#getTypeVars <em>Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl#get_migrationContext <em>migration Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionDeclarationImpl extends AnnotableScriptElementImpl implements FunctionDeclaration {
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
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Block body;

	/**
	 * The cached value of the '{@link #get_lok() <em>lok</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_lok()
	 * @generated
	 * @ordered
	 */
	protected LocalArgumentsVariable _lok;

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
	 * The cached value of the '{@link #getFpars() <em>Fpars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFpars()
	 * @generated
	 * @ordered
	 */
	protected EList<FormalParameter> fpars;

	/**
	 * The cached value of the '{@link #getReturnTypeRef() <em>Return Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef returnTypeRef;

	/**
	 * The default value of the '{@link #isGenerator() <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerator()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerator() <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerator()
	 * @generated
	 * @ordered
	 */
	protected boolean generator = GENERATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredAsync() <em>Declared Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredAsync()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_ASYNC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredAsync() <em>Declared Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredAsync()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredAsync = DECLARED_ASYNC_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTypeVars() <em>Type Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeVars()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeVariable> typeVars;

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
	 * The cached value of the '{@link #get_migrationContext() <em>migration Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #get_migrationContext()
	 * @generated
	 * @ordered
	 */
	protected MigrationContextVariable _migrationContext;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.FUNCTION_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<N4Modifier> getDeclaredModifiers() {
		if (declaredModifiers == null) {
			declaredModifiers = new EDataTypeEList<N4Modifier>(N4Modifier.class, this, N4JSPackage.FUNCTION_DECLARATION__DECLARED_MODIFIERS);
		}
		return declaredModifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Block getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(Block newBody, NotificationChain msgs) {
		Block oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__BODY, oldBody, newBody);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(Block newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DECLARATION__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DECLARATION__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalArgumentsVariable get_lok() {
		return _lok;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSet_lok(LocalArgumentsVariable new_lok, NotificationChain msgs) {
		LocalArgumentsVariable old_lok = _lok;
		_lok = new_lok;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__LOK, old_lok, new_lok);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void set_lok(LocalArgumentsVariable new_lok) {
		if (new_lok != _lok) {
			NotificationChain msgs = null;
			if (_lok != null)
				msgs = ((InternalEObject)_lok).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DECLARATION__LOK, null, msgs);
			if (new_lok != null)
				msgs = ((InternalEObject)new_lok).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DECLARATION__LOK, null, msgs);
			msgs = basicSet_lok(new_lok, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__LOK, new_lok, new_lok));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getDefinedType() {
		if (definedType != null && definedType.eIsProxy()) {
			InternalEObject oldDefinedType = (InternalEObject)definedType;
			definedType = (Type)eResolveProxy(oldDefinedType);
			if (definedType != oldDefinedType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.FUNCTION_DECLARATION__DEFINED_TYPE, oldDefinedType, definedType));
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
	public void setDefinedType(Type newDefinedType) {
		Type oldDefinedType = definedType;
		definedType = newDefinedType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__DEFINED_TYPE, oldDefinedType, definedType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigDecimal getDeclaredVersion() {
		return declaredVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredVersion(BigDecimal newDeclaredVersion) {
		BigDecimal oldDeclaredVersion = declaredVersion;
		declaredVersion = newDeclaredVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__DECLARED_VERSION, oldDeclaredVersion, declaredVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FormalParameter> getFpars() {
		if (fpars == null) {
			fpars = new EObjectContainmentEList<FormalParameter>(FormalParameter.class, this, N4JSPackage.FUNCTION_DECLARATION__FPARS);
		}
		return fpars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef getReturnTypeRef() {
		return returnTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReturnTypeRef(TypeRef newReturnTypeRef, NotificationChain msgs) {
		TypeRef oldReturnTypeRef = returnTypeRef;
		returnTypeRef = newReturnTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF, oldReturnTypeRef, newReturnTypeRef);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnTypeRef(TypeRef newReturnTypeRef) {
		if (newReturnTypeRef != returnTypeRef) {
			NotificationChain msgs = null;
			if (returnTypeRef != null)
				msgs = ((InternalEObject)returnTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF, null, msgs);
			if (newReturnTypeRef != null)
				msgs = ((InternalEObject)newReturnTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF, null, msgs);
			msgs = basicSetReturnTypeRef(newReturnTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF, newReturnTypeRef, newReturnTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerator() {
		return generator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerator(boolean newGenerator) {
		boolean oldGenerator = generator;
		generator = newGenerator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__GENERATOR, oldGenerator, generator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeclaredAsync() {
		return declaredAsync;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredAsync(boolean newDeclaredAsync) {
		boolean oldDeclaredAsync = declaredAsync;
		declaredAsync = newDeclaredAsync;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__DECLARED_ASYNC, oldDeclaredAsync, declaredAsync));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeVariable> getTypeVars() {
		if (typeVars == null) {
			typeVars = new EObjectContainmentEList<TypeVariable>(TypeVariable.class, this, N4JSPackage.FUNCTION_DECLARATION__TYPE_VARS);
		}
		return typeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MigrationContextVariable get_migrationContext() {
		return _migrationContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSet_migrationContext(MigrationContextVariable new_migrationContext, NotificationChain msgs) {
		MigrationContextVariable old_migrationContext = _migrationContext;
		_migrationContext = new_migrationContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__MIGRATION_CONTEXT, old_migrationContext, new_migrationContext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void set_migrationContext(MigrationContextVariable new_migrationContext) {
		if (new_migrationContext != _migrationContext) {
			NotificationChain msgs = null;
			if (_migrationContext != null)
				msgs = ((InternalEObject)_migrationContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DECLARATION__MIGRATION_CONTEXT, null, msgs);
			if (new_migrationContext != null)
				msgs = ((InternalEObject)new_migrationContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DECLARATION__MIGRATION_CONTEXT, null, msgs);
			msgs = basicSet_migrationContext(new_migrationContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DECLARATION__MIGRATION_CONTEXT, new_migrationContext, new_migrationContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExternal() {
		return this.getDeclaredModifiers().contains(N4Modifier.EXTERNAL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MigrationContextVariable getMigrationContextVariable() {
		MigrationContextVariable __migrationContext = this.get_migrationContext();
		boolean _tripleEquals = (__migrationContext == null);
		if (_tripleEquals) {
			final MigrationContextVariable newMigrationContext = N4JSFactory.eINSTANCE.createMigrationContextVariable();
			newMigrationContext.setName("context");
			final Procedure0 _function = new Procedure0() {
				public void apply() {
					FunctionDeclarationImpl.this.set_migrationContext(newMigrationContext);
				}
			};
			EcoreUtilN4.doWithDeliver(false, _function, this);
		}
		return this.get_migrationContext();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExported() {
		EObject _eContainer = this.eContainer();
		return (_eContainer instanceof ExportDeclaration);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExportedAsDefault() {
		return (this.isExported() && ((ExportDeclaration) this.eContainer()).isDefaultExport());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	public boolean isReturnValueOptional() {
		return (((this.getDefinedFunction() != null) && this.getDefinedFunction().isReturnValueOptional()) || ((this.getReturnTypeRef() != null) && this.getReturnTypeRef().isFollowedByQuestionMark()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAsync() {
		return this.isDeclaredAsync();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFunction getDefinedFunction() {
		final Type defType = this.getDefinedType();
		TFunction _xifexpression = null;
		if ((defType instanceof TFunction)) {
			_xifexpression = ((TFunction)defType);
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean hasDeclaredVersion() {
		BigDecimal _declaredVersion = this.getDeclaredVersion();
		return (_declaredVersion != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	public LocalArgumentsVariable getLocalArgumentsVariable() {
		LocalArgumentsVariable __lok = this.get_lok();
		boolean _tripleEquals = (__lok == null);
		if (_tripleEquals) {
			final LocalArgumentsVariable newLok = N4JSFactory.eINSTANCE.createLocalArgumentsVariable();
			newLok.setName("arguments");
			final Procedure0 _function = new Procedure0() {
				public void apply() {
					FunctionDeclarationImpl.this.set_lok(newLok);
				}
			};
			EcoreUtilN4.doWithDeliver(false, _function, this);
		}
		return this.get_lok();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdentifiableElement getDefinedFunctionOrAccessor() {
		final FunctionOrFieldAccessor _this = this;
		EObject _switchResult = null;
		boolean _matched = false;
		if (_this instanceof FunctionDefinition) {
			_matched=true;
			_switchResult = ((FunctionDefinition)_this).getDefinedType();
		}
		if (!_matched) {
			if (_this instanceof FieldAccessor) {
				_matched=true;
				_switchResult = ((FieldAccessor)_this).getDefinedAccessor();
			}
		}
		return ((IdentifiableElement)_switchResult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
			case N4JSPackage.FUNCTION_DECLARATION__BODY:
				return basicSetBody(null, msgs);
			case N4JSPackage.FUNCTION_DECLARATION__LOK:
				return basicSet_lok(null, msgs);
			case N4JSPackage.FUNCTION_DECLARATION__FPARS:
				return ((InternalEList<?>)getFpars()).basicRemove(otherEnd, msgs);
			case N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF:
				return basicSetReturnTypeRef(null, msgs);
			case N4JSPackage.FUNCTION_DECLARATION__TYPE_VARS:
				return ((InternalEList<?>)getTypeVars()).basicRemove(otherEnd, msgs);
			case N4JSPackage.FUNCTION_DECLARATION__MIGRATION_CONTEXT:
				return basicSet_migrationContext(null, msgs);
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
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_MODIFIERS:
				return getDeclaredModifiers();
			case N4JSPackage.FUNCTION_DECLARATION__BODY:
				return getBody();
			case N4JSPackage.FUNCTION_DECLARATION__LOK:
				return get_lok();
			case N4JSPackage.FUNCTION_DECLARATION__DEFINED_TYPE:
				if (resolve) return getDefinedType();
				return basicGetDefinedType();
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_VERSION:
				return getDeclaredVersion();
			case N4JSPackage.FUNCTION_DECLARATION__FPARS:
				return getFpars();
			case N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF:
				return getReturnTypeRef();
			case N4JSPackage.FUNCTION_DECLARATION__GENERATOR:
				return isGenerator();
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_ASYNC:
				return isDeclaredAsync();
			case N4JSPackage.FUNCTION_DECLARATION__TYPE_VARS:
				return getTypeVars();
			case N4JSPackage.FUNCTION_DECLARATION__NAME:
				return getName();
			case N4JSPackage.FUNCTION_DECLARATION__MIGRATION_CONTEXT:
				return get_migrationContext();
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
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_MODIFIERS:
				getDeclaredModifiers().clear();
				getDeclaredModifiers().addAll((Collection<? extends N4Modifier>)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__BODY:
				setBody((Block)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__LOK:
				set_lok((LocalArgumentsVariable)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__DEFINED_TYPE:
				setDefinedType((Type)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_VERSION:
				setDeclaredVersion((BigDecimal)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__FPARS:
				getFpars().clear();
				getFpars().addAll((Collection<? extends FormalParameter>)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF:
				setReturnTypeRef((TypeRef)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__GENERATOR:
				setGenerator((Boolean)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_ASYNC:
				setDeclaredAsync((Boolean)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				getTypeVars().addAll((Collection<? extends TypeVariable>)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__NAME:
				setName((String)newValue);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__MIGRATION_CONTEXT:
				set_migrationContext((MigrationContextVariable)newValue);
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
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_MODIFIERS:
				getDeclaredModifiers().clear();
				return;
			case N4JSPackage.FUNCTION_DECLARATION__BODY:
				setBody((Block)null);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__LOK:
				set_lok((LocalArgumentsVariable)null);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__DEFINED_TYPE:
				setDefinedType((Type)null);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_VERSION:
				setDeclaredVersion(DECLARED_VERSION_EDEFAULT);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__FPARS:
				getFpars().clear();
				return;
			case N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF:
				setReturnTypeRef((TypeRef)null);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__GENERATOR:
				setGenerator(GENERATOR_EDEFAULT);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_ASYNC:
				setDeclaredAsync(DECLARED_ASYNC_EDEFAULT);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				return;
			case N4JSPackage.FUNCTION_DECLARATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case N4JSPackage.FUNCTION_DECLARATION__MIGRATION_CONTEXT:
				set_migrationContext((MigrationContextVariable)null);
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
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_MODIFIERS:
				return declaredModifiers != null && !declaredModifiers.isEmpty();
			case N4JSPackage.FUNCTION_DECLARATION__BODY:
				return body != null;
			case N4JSPackage.FUNCTION_DECLARATION__LOK:
				return _lok != null;
			case N4JSPackage.FUNCTION_DECLARATION__DEFINED_TYPE:
				return definedType != null;
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_VERSION:
				return DECLARED_VERSION_EDEFAULT == null ? declaredVersion != null : !DECLARED_VERSION_EDEFAULT.equals(declaredVersion);
			case N4JSPackage.FUNCTION_DECLARATION__FPARS:
				return fpars != null && !fpars.isEmpty();
			case N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF:
				return returnTypeRef != null;
			case N4JSPackage.FUNCTION_DECLARATION__GENERATOR:
				return generator != GENERATOR_EDEFAULT;
			case N4JSPackage.FUNCTION_DECLARATION__DECLARED_ASYNC:
				return declaredAsync != DECLARED_ASYNC_EDEFAULT;
			case N4JSPackage.FUNCTION_DECLARATION__TYPE_VARS:
				return typeVars != null && !typeVars.isEmpty();
			case N4JSPackage.FUNCTION_DECLARATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case N4JSPackage.FUNCTION_DECLARATION__MIGRATION_CONTEXT:
				return _migrationContext != null;
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
		if (baseClass == ModifiableElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FUNCTION_DECLARATION__DECLARED_MODIFIERS: return N4JSPackage.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS;
				default: return -1;
			}
		}
		if (baseClass == ControlFlowElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == VariableEnvironmentElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ThisArgProvider.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypableElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == FunctionOrFieldAccessor.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FUNCTION_DECLARATION__BODY: return N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR__BODY;
				case N4JSPackage.FUNCTION_DECLARATION__LOK: return N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR__LOK;
				default: return -1;
			}
		}
		if (baseClass == TypeDefiningElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FUNCTION_DECLARATION__DEFINED_TYPE: return N4JSPackage.TYPE_DEFINING_ELEMENT__DEFINED_TYPE;
				default: return -1;
			}
		}
		if (baseClass == VersionedElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FUNCTION_DECLARATION__DECLARED_VERSION: return N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION;
				default: return -1;
			}
		}
		if (baseClass == FunctionDefinition.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FUNCTION_DECLARATION__FPARS: return N4JSPackage.FUNCTION_DEFINITION__FPARS;
				case N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF: return N4JSPackage.FUNCTION_DEFINITION__RETURN_TYPE_REF;
				case N4JSPackage.FUNCTION_DECLARATION__GENERATOR: return N4JSPackage.FUNCTION_DEFINITION__GENERATOR;
				case N4JSPackage.FUNCTION_DECLARATION__DECLARED_ASYNC: return N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FUNCTION_DECLARATION__TYPE_VARS: return N4JSPackage.GENERIC_DECLARATION__TYPE_VARS;
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
		if (baseClass == ModifiableElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS: return N4JSPackage.FUNCTION_DECLARATION__DECLARED_MODIFIERS;
				default: return -1;
			}
		}
		if (baseClass == ControlFlowElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == VariableEnvironmentElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ThisArgProvider.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypableElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == FunctionOrFieldAccessor.class) {
			switch (baseFeatureID) {
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR__BODY: return N4JSPackage.FUNCTION_DECLARATION__BODY;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR__LOK: return N4JSPackage.FUNCTION_DECLARATION__LOK;
				default: return -1;
			}
		}
		if (baseClass == TypeDefiningElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.TYPE_DEFINING_ELEMENT__DEFINED_TYPE: return N4JSPackage.FUNCTION_DECLARATION__DEFINED_TYPE;
				default: return -1;
			}
		}
		if (baseClass == VersionedElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION: return N4JSPackage.FUNCTION_DECLARATION__DECLARED_VERSION;
				default: return -1;
			}
		}
		if (baseClass == FunctionDefinition.class) {
			switch (baseFeatureID) {
				case N4JSPackage.FUNCTION_DEFINITION__FPARS: return N4JSPackage.FUNCTION_DECLARATION__FPARS;
				case N4JSPackage.FUNCTION_DEFINITION__RETURN_TYPE_REF: return N4JSPackage.FUNCTION_DECLARATION__RETURN_TYPE_REF;
				case N4JSPackage.FUNCTION_DEFINITION__GENERATOR: return N4JSPackage.FUNCTION_DECLARATION__GENERATOR;
				case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC: return N4JSPackage.FUNCTION_DECLARATION__DECLARED_ASYNC;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseFeatureID) {
				case N4JSPackage.GENERIC_DECLARATION__TYPE_VARS: return N4JSPackage.FUNCTION_DECLARATION__TYPE_VARS;
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
		if (baseClass == ModifiableElement.class) {
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
		if (baseClass == VariableEnvironmentElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS: return N4JSPackage.FUNCTION_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;
				default: return -1;
			}
		}
		if (baseClass == ThisArgProvider.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == TypableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == FunctionOrFieldAccessor.class) {
			switch (baseOperationID) {
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___GET_NAME: return N4JSPackage.FUNCTION_DECLARATION___GET_NAME;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE: return N4JSPackage.FUNCTION_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL: return N4JSPackage.FUNCTION_DECLARATION___IS_RETURN_VALUE_OPTIONAL;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___IS_ASYNC: return N4JSPackage.FUNCTION_DECLARATION___IS_ASYNC;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR: return N4JSPackage.FUNCTION_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR;
				default: return -1;
			}
		}
		if (baseClass == TypeDefiningElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == VersionedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.VERSIONED_ELEMENT___HAS_DECLARED_VERSION: return N4JSPackage.FUNCTION_DECLARATION___HAS_DECLARED_VERSION;
				case N4JSPackage.VERSIONED_ELEMENT___GET_DECLARED_VERSION_OR_ZERO: return N4JSPackage.FUNCTION_DECLARATION___GET_DECLARED_VERSION_OR_ZERO;
				default: return -1;
			}
		}
		if (baseClass == FunctionDefinition.class) {
			switch (baseOperationID) {
				case N4JSPackage.FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL: return N4JSPackage.FUNCTION_DECLARATION___IS_RETURN_VALUE_OPTIONAL;
				case N4JSPackage.FUNCTION_DEFINITION___IS_ASYNC: return N4JSPackage.FUNCTION_DECLARATION___IS_ASYNC;
				case N4JSPackage.FUNCTION_DEFINITION___GET_DEFINED_FUNCTION: return N4JSPackage.FUNCTION_DECLARATION___GET_DEFINED_FUNCTION;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == ExportableElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED: return N4JSPackage.FUNCTION_DECLARATION___IS_EXPORTED;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT: return N4JSPackage.FUNCTION_DECLARATION___IS_EXPORTED_AS_DEFAULT;
				case N4JSPackage.EXPORTABLE_ELEMENT___GET_EXPORTED_NAME: return N4JSPackage.FUNCTION_DECLARATION___GET_EXPORTED_NAME;
				case N4JSPackage.EXPORTABLE_ELEMENT___IS_TOPLEVEL: return N4JSPackage.FUNCTION_DECLARATION___IS_TOPLEVEL;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.FUNCTION_DECLARATION___GET_NAME;
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
			case N4JSPackage.FUNCTION_DECLARATION___IS_EXTERNAL:
				return isExternal();
			case N4JSPackage.FUNCTION_DECLARATION___GET_MIGRATION_CONTEXT_VARIABLE:
				return getMigrationContextVariable();
			case N4JSPackage.FUNCTION_DECLARATION___IS_EXPORTED:
				return isExported();
			case N4JSPackage.FUNCTION_DECLARATION___IS_EXPORTED_AS_DEFAULT:
				return isExportedAsDefault();
			case N4JSPackage.FUNCTION_DECLARATION___GET_EXPORTED_NAME:
				return getExportedName();
			case N4JSPackage.FUNCTION_DECLARATION___IS_TOPLEVEL:
				return isToplevel();
			case N4JSPackage.FUNCTION_DECLARATION___IS_RETURN_VALUE_OPTIONAL:
				return isReturnValueOptional();
			case N4JSPackage.FUNCTION_DECLARATION___IS_ASYNC:
				return isAsync();
			case N4JSPackage.FUNCTION_DECLARATION___GET_DEFINED_FUNCTION:
				return getDefinedFunction();
			case N4JSPackage.FUNCTION_DECLARATION___HAS_DECLARED_VERSION:
				return hasDeclaredVersion();
			case N4JSPackage.FUNCTION_DECLARATION___GET_DECLARED_VERSION_OR_ZERO:
				return getDeclaredVersionOrZero();
			case N4JSPackage.FUNCTION_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE:
				return getLocalArgumentsVariable();
			case N4JSPackage.FUNCTION_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR:
				return getDefinedFunctionOrAccessor();
			case N4JSPackage.FUNCTION_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS:
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (declaredModifiers: ");
		result.append(declaredModifiers);
		result.append(", declaredVersion: ");
		result.append(declaredVersion);
		result.append(", generator: ");
		result.append(generator);
		result.append(", declaredAsync: ");
		result.append(declaredAsync);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //FunctionDeclarationImpl
