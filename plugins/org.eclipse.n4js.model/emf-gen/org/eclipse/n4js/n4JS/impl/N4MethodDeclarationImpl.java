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

import com.google.common.base.Objects;

import com.google.common.collect.Iterators;

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

import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.MethodDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.ThisArgProvider;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TVariable;

import org.eclipse.n4js.utils.EcoreUtilN4;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Method Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl#getImplicitArgumentsVariable <em>Implicit Arguments Variable</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl#getFpars <em>Fpars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl#getDeclaredReturnTypeRefNode <em>Declared Return Type Ref Node</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl#isGenerator <em>Generator</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl#isDeclaredAsync <em>Declared Async</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl#getDefinedFunction <em>Defined Function</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl#getTypeVars <em>Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl#getDeclaredName <em>Declared Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4MethodDeclarationImpl extends AnnotableN4MemberDeclarationImpl implements N4MethodDeclaration {
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
	 * The cached value of the '{@link #getImplicitArgumentsVariable() <em>Implicit Arguments Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplicitArgumentsVariable()
	 * @generated
	 * @ordered
	 */
	protected TVariable implicitArgumentsVariable;

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
	 * The cached value of the '{@link #getDeclaredReturnTypeRefNode() <em>Declared Return Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredReturnTypeRefNode()
	 * @generated
	 * @ordered
	 */
	protected TypeReferenceNode<TypeRef> declaredReturnTypeRefNode;

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
	 * The cached value of the '{@link #getDefinedFunction() <em>Defined Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedFunction()
	 * @generated
	 * @ordered
	 */
	protected TFunction definedFunction;

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
	 * The cached value of the '{@link #getDeclaredName() <em>Declared Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredName()
	 * @generated
	 * @ordered
	 */
	protected LiteralOrComputedPropertyName declaredName;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4MethodDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_METHOD_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__BODY, oldBody, newBody);
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
	public void setBody(Block newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_METHOD_DECLARATION__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_METHOD_DECLARATION__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TVariable getImplicitArgumentsVariable() {
		if (implicitArgumentsVariable != null && implicitArgumentsVariable.eIsProxy()) {
			InternalEObject oldImplicitArgumentsVariable = (InternalEObject)implicitArgumentsVariable;
			implicitArgumentsVariable = (TVariable)eResolveProxy(oldImplicitArgumentsVariable);
			if (implicitArgumentsVariable != oldImplicitArgumentsVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.N4_METHOD_DECLARATION__IMPLICIT_ARGUMENTS_VARIABLE, oldImplicitArgumentsVariable, implicitArgumentsVariable));
			}
		}
		return implicitArgumentsVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TVariable basicGetImplicitArgumentsVariable() {
		return implicitArgumentsVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImplicitArgumentsVariable(TVariable newImplicitArgumentsVariable) {
		TVariable oldImplicitArgumentsVariable = implicitArgumentsVariable;
		implicitArgumentsVariable = newImplicitArgumentsVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__IMPLICIT_ARGUMENTS_VARIABLE, oldImplicitArgumentsVariable, implicitArgumentsVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FormalParameter> getFpars() {
		if (fpars == null) {
			fpars = new EObjectContainmentEList<FormalParameter>(FormalParameter.class, this, N4JSPackage.N4_METHOD_DECLARATION__FPARS);
		}
		return fpars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeReferenceNode<TypeRef> getDeclaredReturnTypeRefNode() {
		return declaredReturnTypeRefNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredReturnTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredReturnTypeRefNode, NotificationChain msgs) {
		TypeReferenceNode<TypeRef> oldDeclaredReturnTypeRefNode = declaredReturnTypeRefNode;
		declaredReturnTypeRefNode = newDeclaredReturnTypeRefNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE, oldDeclaredReturnTypeRefNode, newDeclaredReturnTypeRefNode);
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
	public void setDeclaredReturnTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredReturnTypeRefNode) {
		if (newDeclaredReturnTypeRefNode != declaredReturnTypeRefNode) {
			NotificationChain msgs = null;
			if (declaredReturnTypeRefNode != null)
				msgs = ((InternalEObject)declaredReturnTypeRefNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE, null, msgs);
			if (newDeclaredReturnTypeRefNode != null)
				msgs = ((InternalEObject)newDeclaredReturnTypeRefNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE, null, msgs);
			msgs = basicSetDeclaredReturnTypeRefNode(newDeclaredReturnTypeRefNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE, newDeclaredReturnTypeRefNode, newDeclaredReturnTypeRefNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGenerator() {
		return generator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGenerator(boolean newGenerator) {
		boolean oldGenerator = generator;
		generator = newGenerator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__GENERATOR, oldGenerator, generator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredAsync() {
		return declaredAsync;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredAsync(boolean newDeclaredAsync) {
		boolean oldDeclaredAsync = declaredAsync;
		declaredAsync = newDeclaredAsync;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__DECLARED_ASYNC, oldDeclaredAsync, declaredAsync));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TFunction getDefinedFunction() {
		if (definedFunction != null && definedFunction.eIsProxy()) {
			InternalEObject oldDefinedFunction = (InternalEObject)definedFunction;
			definedFunction = (TFunction)eResolveProxy(oldDefinedFunction);
			if (definedFunction != oldDefinedFunction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.N4_METHOD_DECLARATION__DEFINED_FUNCTION, oldDefinedFunction, definedFunction));
			}
		}
		return definedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFunction basicGetDefinedFunction() {
		return definedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedFunction(TFunction newDefinedFunction) {
		TFunction oldDefinedFunction = definedFunction;
		definedFunction = newDefinedFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__DEFINED_FUNCTION, oldDefinedFunction, definedFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4TypeVariable> getTypeVars() {
		if (typeVars == null) {
			typeVars = new EObjectContainmentEList<N4TypeVariable>(N4TypeVariable.class, this, N4JSPackage.N4_METHOD_DECLARATION__TYPE_VARS);
		}
		return typeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LiteralOrComputedPropertyName getDeclaredName() {
		return declaredName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredName(LiteralOrComputedPropertyName newDeclaredName, NotificationChain msgs) {
		LiteralOrComputedPropertyName oldDeclaredName = declaredName;
		declaredName = newDeclaredName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME, oldDeclaredName, newDeclaredName);
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
	public void setDeclaredName(LiteralOrComputedPropertyName newDeclaredName) {
		if (newDeclaredName != declaredName) {
			NotificationChain msgs = null;
			if (declaredName != null)
				msgs = ((InternalEObject)declaredName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME, null, msgs);
			if (newDeclaredName != null)
				msgs = ((InternalEObject)newDeclaredName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME, null, msgs);
			msgs = basicSetDeclaredName(newDeclaredName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME, newDeclaredName, newDeclaredName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbstract() {
		return ((((this.eContainer() instanceof N4InterfaceDeclaration) && (this.getBody() == null)) && 
			(!IterableExtensions.<Annotation>exists(this.getAnnotations(), new Function1<Annotation, Boolean>() {
				public Boolean apply(final Annotation it) {
					String _name = it.getName();
					return Boolean.valueOf(Objects.equal(_name, "ProvidesDefaultImplementation"));
				}
			}))) || this.isDeclaredAbstract());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConstructor() {
		return (Objects.equal(this.getName(), "constructor") && (!this.isStatic()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isCallSignature() {
		LiteralOrComputedPropertyName _declaredName = this.getDeclaredName();
		return (_declaredName == null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConstructSignature() {
		return ((Objects.equal(this.getName(), "new") && (!this.isStatic())) && (this.eContainer() instanceof N4InterfaceDeclaration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStatic() {
		return this.getDeclaredModifiers().contains(N4Modifier.STATIC);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isValidName() {
		String _name = this.getName();
		boolean _equals = Objects.equal("prototype", _name);
		if (_equals) {
			return false;
		}
		boolean _and = false;
		if (!(Objects.equal("constructor", this.getName()) && this.isGenerator())) {
			_and = false;
		} else {
			LiteralOrComputedPropertyName _declaredName = this.getDeclaredName();
			PropertyNameKind _kind = null;
			if (_declaredName!=null) {
				_kind=_declaredName.getKind();
			}
			boolean _tripleNotEquals = (_kind != PropertyNameKind.COMPUTED);
			_and = _tripleNotEquals;
		}
		if (_and) {
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean existsExplicitSuperCall() {
		final Function1<ExpressionStatement, Expression> _function = new Function1<ExpressionStatement, Expression>() {
			public Expression apply(final ExpressionStatement it) {
				return it.getExpression();
			}
		};
		final Function1<ParameterizedCallExpression, Boolean> _function_1 = new Function1<ParameterizedCallExpression, Boolean>() {
			public Boolean apply(final ParameterizedCallExpression it) {
				Expression _target = it.getTarget();
				return Boolean.valueOf((_target instanceof SuperLiteral));
			}
		};
		final boolean existsSuperCall = IteratorExtensions.<ParameterizedCallExpression>exists(Iterators.<ParameterizedCallExpression>filter(IteratorExtensions.<ExpressionStatement, Expression>map(Iterators.<ExpressionStatement>filter(EcoreUtilN4.<Statement>getAllDirectlyFoundContentsOfType(this.getBody(), Statement.class), ExpressionStatement.class), _function), ParameterizedCallExpression.class), _function_1);
		return existsSuperCall;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TMember getDefinedTypeElement() {
		TMember _xifexpression = null;
		TFunction _definedFunction = this.getDefinedFunction();
		boolean _tripleEquals = (_definedFunction == null);
		if (_tripleEquals) {
			_xifexpression = null;
		}
		else {
			TMember _xifexpression_1 = null;
			TFunction _definedFunction_1 = this.getDefinedFunction();
			if ((_definedFunction_1 instanceof TMember)) {
				TFunction _definedFunction_2 = this.getDefinedFunction();
				_xifexpression_1 = ((TMember) _definedFunction_2);
			}
			else {
				throw new IllegalArgumentException(
					"");
			}
			_xifexpression = _xifexpression_1;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		LiteralOrComputedPropertyName _declaredName = this.getDeclaredName();
		String _name = null;
		if (_declaredName!=null) {
			_name=_declaredName.getName();
		}
		return _name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasComputedPropertyName() {
		final LiteralOrComputedPropertyName declName = this.getDeclaredName();
		return ((declName != null) && declName.hasComputedPropertyName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReturnValueOptional() {
		boolean _or = false;
		if (((this.getDefinedFunction() != null) && this.getDefinedFunction().isReturnValueOptional())) {
			_or = true;
		} else {
			boolean _and = false;
			TypeReferenceNode<TypeRef> _declaredReturnTypeRefNode = this.getDeclaredReturnTypeRefNode();
			TypeRef _typeRefInAST = null;
			if (_declaredReturnTypeRefNode!=null) {
				_typeRefInAST=_declaredReturnTypeRefNode.getTypeRefInAST();
			}
			boolean _tripleNotEquals = (_typeRefInAST != null);
			if (!_tripleNotEquals) {
				_and = false;
			} else {
				boolean _isFollowedByQuestionMark = this.getDeclaredReturnTypeRefNode().getTypeRefInAST().isFollowedByQuestionMark();
				_and = _isFollowedByQuestionMark;
			}
			_or = _and;
		}
		return _or;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredReturnTypeRef() {
		TypeReferenceNode<TypeRef> _declaredReturnTypeRefNode = this.getDeclaredReturnTypeRefNode();
		TypeRef _typeRef = null;
		if (_declaredReturnTypeRefNode!=null) {
			_typeRef=_declaredReturnTypeRefNode.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredReturnTypeRefInAST() {
		TypeReferenceNode<TypeRef> _declaredReturnTypeRefNode = this.getDeclaredReturnTypeRefNode();
		TypeRef _typeRefInAST = null;
		if (_declaredReturnTypeRefNode!=null) {
			_typeRefInAST=_declaredReturnTypeRefNode.getTypeRefInAST();
		}
		return _typeRefInAST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAsync() {
		return this.isDeclaredAsync();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifiableElement getDefinedFunctionOrAccessor() {
		final FunctionOrFieldAccessor _this = this;
		EObject _switchResult = null;
		boolean _matched = false;
		if (_this instanceof FunctionDefinition) {
			_matched=true;
			_switchResult = ((FunctionDefinition)_this).getDefinedFunction();
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
			case N4JSPackage.N4_METHOD_DECLARATION__BODY:
				return basicSetBody(null, msgs);
			case N4JSPackage.N4_METHOD_DECLARATION__FPARS:
				return ((InternalEList<?>)getFpars()).basicRemove(otherEnd, msgs);
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE:
				return basicSetDeclaredReturnTypeRefNode(null, msgs);
			case N4JSPackage.N4_METHOD_DECLARATION__TYPE_VARS:
				return ((InternalEList<?>)getTypeVars()).basicRemove(otherEnd, msgs);
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME:
				return basicSetDeclaredName(null, msgs);
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
			case N4JSPackage.N4_METHOD_DECLARATION__BODY:
				return getBody();
			case N4JSPackage.N4_METHOD_DECLARATION__IMPLICIT_ARGUMENTS_VARIABLE:
				if (resolve) return getImplicitArgumentsVariable();
				return basicGetImplicitArgumentsVariable();
			case N4JSPackage.N4_METHOD_DECLARATION__FPARS:
				return getFpars();
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE:
				return getDeclaredReturnTypeRefNode();
			case N4JSPackage.N4_METHOD_DECLARATION__GENERATOR:
				return isGenerator();
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_ASYNC:
				return isDeclaredAsync();
			case N4JSPackage.N4_METHOD_DECLARATION__DEFINED_FUNCTION:
				if (resolve) return getDefinedFunction();
				return basicGetDefinedFunction();
			case N4JSPackage.N4_METHOD_DECLARATION__TYPE_VARS:
				return getTypeVars();
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME:
				return getDeclaredName();
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
			case N4JSPackage.N4_METHOD_DECLARATION__BODY:
				setBody((Block)newValue);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__IMPLICIT_ARGUMENTS_VARIABLE:
				setImplicitArgumentsVariable((TVariable)newValue);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__FPARS:
				getFpars().clear();
				getFpars().addAll((Collection<? extends FormalParameter>)newValue);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE:
				setDeclaredReturnTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__GENERATOR:
				setGenerator((Boolean)newValue);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_ASYNC:
				setDeclaredAsync((Boolean)newValue);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__DEFINED_FUNCTION:
				setDefinedFunction((TFunction)newValue);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				getTypeVars().addAll((Collection<? extends N4TypeVariable>)newValue);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME:
				setDeclaredName((LiteralOrComputedPropertyName)newValue);
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
			case N4JSPackage.N4_METHOD_DECLARATION__BODY:
				setBody((Block)null);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__IMPLICIT_ARGUMENTS_VARIABLE:
				setImplicitArgumentsVariable((TVariable)null);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__FPARS:
				getFpars().clear();
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE:
				setDeclaredReturnTypeRefNode((TypeReferenceNode<TypeRef>)null);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__GENERATOR:
				setGenerator(GENERATOR_EDEFAULT);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_ASYNC:
				setDeclaredAsync(DECLARED_ASYNC_EDEFAULT);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__DEFINED_FUNCTION:
				setDefinedFunction((TFunction)null);
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				return;
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME:
				setDeclaredName((LiteralOrComputedPropertyName)null);
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
			case N4JSPackage.N4_METHOD_DECLARATION__BODY:
				return body != null;
			case N4JSPackage.N4_METHOD_DECLARATION__IMPLICIT_ARGUMENTS_VARIABLE:
				return implicitArgumentsVariable != null;
			case N4JSPackage.N4_METHOD_DECLARATION__FPARS:
				return fpars != null && !fpars.isEmpty();
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE:
				return declaredReturnTypeRefNode != null;
			case N4JSPackage.N4_METHOD_DECLARATION__GENERATOR:
				return generator != GENERATOR_EDEFAULT;
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_ASYNC:
				return declaredAsync != DECLARED_ASYNC_EDEFAULT;
			case N4JSPackage.N4_METHOD_DECLARATION__DEFINED_FUNCTION:
				return definedFunction != null;
			case N4JSPackage.N4_METHOD_DECLARATION__TYPE_VARS:
				return typeVars != null && !typeVars.isEmpty();
			case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME:
				return declaredName != null;
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
		if (baseClass == ThisArgProvider.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == FunctionOrFieldAccessor.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_METHOD_DECLARATION__BODY: return N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR__BODY;
				case N4JSPackage.N4_METHOD_DECLARATION__IMPLICIT_ARGUMENTS_VARIABLE: return N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR__IMPLICIT_ARGUMENTS_VARIABLE;
				default: return -1;
			}
		}
		if (baseClass == FunctionDefinition.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_METHOD_DECLARATION__FPARS: return N4JSPackage.FUNCTION_DEFINITION__FPARS;
				case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE: return N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE;
				case N4JSPackage.N4_METHOD_DECLARATION__GENERATOR: return N4JSPackage.FUNCTION_DEFINITION__GENERATOR;
				case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_ASYNC: return N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC;
				case N4JSPackage.N4_METHOD_DECLARATION__DEFINED_FUNCTION: return N4JSPackage.FUNCTION_DEFINITION__DEFINED_FUNCTION;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_METHOD_DECLARATION__TYPE_VARS: return N4JSPackage.GENERIC_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME: return N4JSPackage.PROPERTY_NAME_OWNER__DECLARED_NAME;
				default: return -1;
			}
		}
		if (baseClass == MethodDeclaration.class) {
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
		if (baseClass == ThisArgProvider.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == FunctionOrFieldAccessor.class) {
			switch (baseFeatureID) {
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR__BODY: return N4JSPackage.N4_METHOD_DECLARATION__BODY;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR__IMPLICIT_ARGUMENTS_VARIABLE: return N4JSPackage.N4_METHOD_DECLARATION__IMPLICIT_ARGUMENTS_VARIABLE;
				default: return -1;
			}
		}
		if (baseClass == FunctionDefinition.class) {
			switch (baseFeatureID) {
				case N4JSPackage.FUNCTION_DEFINITION__FPARS: return N4JSPackage.N4_METHOD_DECLARATION__FPARS;
				case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE: return N4JSPackage.N4_METHOD_DECLARATION__DECLARED_RETURN_TYPE_REF_NODE;
				case N4JSPackage.FUNCTION_DEFINITION__GENERATOR: return N4JSPackage.N4_METHOD_DECLARATION__GENERATOR;
				case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC: return N4JSPackage.N4_METHOD_DECLARATION__DECLARED_ASYNC;
				case N4JSPackage.FUNCTION_DEFINITION__DEFINED_FUNCTION: return N4JSPackage.N4_METHOD_DECLARATION__DEFINED_FUNCTION;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseFeatureID) {
				case N4JSPackage.GENERIC_DECLARATION__TYPE_VARS: return N4JSPackage.N4_METHOD_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (baseFeatureID) {
				case N4JSPackage.PROPERTY_NAME_OWNER__DECLARED_NAME: return N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME;
				default: return -1;
			}
		}
		if (baseClass == MethodDeclaration.class) {
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
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.N4_METHOD_DECLARATION___GET_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == N4MemberDeclaration.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_MEMBER_DECLARATION___GET_DEFINED_TYPE_ELEMENT: return N4JSPackage.N4_METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT;
				case N4JSPackage.N4_MEMBER_DECLARATION___IS_ABSTRACT: return N4JSPackage.N4_METHOD_DECLARATION___IS_ABSTRACT;
				case N4JSPackage.N4_MEMBER_DECLARATION___IS_STATIC: return N4JSPackage.N4_METHOD_DECLARATION___IS_STATIC;
				case N4JSPackage.N4_MEMBER_DECLARATION___IS_CONSTRUCTOR: return N4JSPackage.N4_METHOD_DECLARATION___IS_CONSTRUCTOR;
				case N4JSPackage.N4_MEMBER_DECLARATION___IS_CALL_SIGNATURE: return N4JSPackage.N4_METHOD_DECLARATION___IS_CALL_SIGNATURE;
				case N4JSPackage.N4_MEMBER_DECLARATION___IS_CONSTRUCT_SIGNATURE: return N4JSPackage.N4_METHOD_DECLARATION___IS_CONSTRUCT_SIGNATURE;
				case N4JSPackage.N4_MEMBER_DECLARATION___GET_NAME: return N4JSPackage.N4_METHOD_DECLARATION___GET_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == VariableEnvironmentElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS: return N4JSPackage.N4_METHOD_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;
				default: return -1;
			}
		}
		if (baseClass == ThisArgProvider.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == FunctionOrFieldAccessor.class) {
			switch (baseOperationID) {
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___GET_NAME: return N4JSPackage.N4_METHOD_DECLARATION___GET_NAME;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL: return N4JSPackage.N4_METHOD_DECLARATION___IS_RETURN_VALUE_OPTIONAL;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___IS_ASYNC: return N4JSPackage.N4_METHOD_DECLARATION___IS_ASYNC;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR: return N4JSPackage.N4_METHOD_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR;
				default: return -1;
			}
		}
		if (baseClass == FunctionDefinition.class) {
			switch (baseOperationID) {
				case N4JSPackage.FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL: return N4JSPackage.N4_METHOD_DECLARATION___IS_RETURN_VALUE_OPTIONAL;
				case N4JSPackage.FUNCTION_DEFINITION___GET_DECLARED_RETURN_TYPE_REF: return N4JSPackage.N4_METHOD_DECLARATION___GET_DECLARED_RETURN_TYPE_REF;
				case N4JSPackage.FUNCTION_DEFINITION___GET_DECLARED_RETURN_TYPE_REF_IN_AST: return N4JSPackage.N4_METHOD_DECLARATION___GET_DECLARED_RETURN_TYPE_REF_IN_AST;
				case N4JSPackage.FUNCTION_DEFINITION___IS_ASYNC: return N4JSPackage.N4_METHOD_DECLARATION___IS_ASYNC;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (baseOperationID) {
				case N4JSPackage.PROPERTY_NAME_OWNER___GET_NAME: return N4JSPackage.N4_METHOD_DECLARATION___GET_NAME;
				case N4JSPackage.PROPERTY_NAME_OWNER___HAS_COMPUTED_PROPERTY_NAME: return N4JSPackage.N4_METHOD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME;
				case N4JSPackage.PROPERTY_NAME_OWNER___IS_VALID_NAME: return N4JSPackage.N4_METHOD_DECLARATION___IS_VALID_NAME;
				default: return -1;
			}
		}
		if (baseClass == MethodDeclaration.class) {
			switch (baseOperationID) {
				case N4JSPackage.METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL: return N4JSPackage.N4_METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL;
				case N4JSPackage.METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT: return N4JSPackage.N4_METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT;
				case N4JSPackage.METHOD_DECLARATION___IS_STATIC: return N4JSPackage.N4_METHOD_DECLARATION___IS_STATIC;
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
			case N4JSPackage.N4_METHOD_DECLARATION___IS_ABSTRACT:
				return isAbstract();
			case N4JSPackage.N4_METHOD_DECLARATION___IS_CONSTRUCTOR:
				return isConstructor();
			case N4JSPackage.N4_METHOD_DECLARATION___IS_CALL_SIGNATURE:
				return isCallSignature();
			case N4JSPackage.N4_METHOD_DECLARATION___IS_CONSTRUCT_SIGNATURE:
				return isConstructSignature();
			case N4JSPackage.N4_METHOD_DECLARATION___IS_STATIC:
				return isStatic();
			case N4JSPackage.N4_METHOD_DECLARATION___IS_VALID_NAME:
				return isValidName();
			case N4JSPackage.N4_METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL:
				return existsExplicitSuperCall();
			case N4JSPackage.N4_METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT:
				return getDefinedTypeElement();
			case N4JSPackage.N4_METHOD_DECLARATION___GET_NAME:
				return getName();
			case N4JSPackage.N4_METHOD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME:
				return hasComputedPropertyName();
			case N4JSPackage.N4_METHOD_DECLARATION___IS_RETURN_VALUE_OPTIONAL:
				return isReturnValueOptional();
			case N4JSPackage.N4_METHOD_DECLARATION___GET_DECLARED_RETURN_TYPE_REF:
				return getDeclaredReturnTypeRef();
			case N4JSPackage.N4_METHOD_DECLARATION___GET_DECLARED_RETURN_TYPE_REF_IN_AST:
				return getDeclaredReturnTypeRefInAST();
			case N4JSPackage.N4_METHOD_DECLARATION___IS_ASYNC:
				return isAsync();
			case N4JSPackage.N4_METHOD_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR:
				return getDefinedFunctionOrAccessor();
			case N4JSPackage.N4_METHOD_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS:
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
		result.append(" (generator: ");
		result.append(generator);
		result.append(", declaredAsync: ");
		result.append(declaredAsync);
		result.append(')');
		return result.toString();
	}

} //N4MethodDeclarationImpl
