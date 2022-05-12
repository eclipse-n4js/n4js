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
package org.eclipse.n4js.n4JS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;

/**
 * General helpers for navigating in the AST. Some of these are used by convenience methods in N4JS model classes.
 *
 * Hint: use <code>import static extension org.eclipse.n4js.n4JS.N4JSASTUtils.*</code> in Xtend
 */
public abstract class N4JSASTUtils {
	/** Seed for hashing algorithm */
	private static final int SEED = 9415;

	/** The reserved {@value} keyword. */
	public static final String CONSTRUCTOR = "constructor";

	/** All {@link N4Modifier}s denoting a certain level of accessibility, not taking into account {@code @Internal}. */
	public static final Set<N4Modifier> ACCESSIBILITY_MODIFIERS = Set.of(
			N4Modifier.PUBLIC,
			N4Modifier.PROTECTED,
			N4Modifier.PROJECT,
			N4Modifier.PRIVATE);

	/**
	 * Tells if the given {@link EObject} represents a write access, e.g. left-hand side of an assignment.
	 */
	public static boolean isWriteAccess(EObject reference) {
		EObject parent = reference.eContainer();
		while (parent instanceof ParameterizedPropertyAccessExpression
				&& ((ParameterizedPropertyAccessExpression) parent).getTarget() == reference) {
			reference = parent;
			parent = parent.eContainer();
		}
		if (parent == null) {
			return false;
		}

		if (parent instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) parent;
			return ae.getLhs() == reference;
		}
		if (parent instanceof ForStatement) {
			ForStatement fs = (ForStatement) parent;
			return fs.getInitExpr() == reference;
		}

		DestructNode dNode = DestructureUtils.getCorrespondingDestructNode(reference);
		if (dNode != null) {
			dNode = dNode.findNodeForElement(parent);
			return dNode != null;
		}

		return false;
	}

	/**
	 * Returns the containing variable environment scope for the given variable, depending on whether it is block scoped
	 * (i.e. variables declared with let, const) or not.
	 *
	 * @param variableInAST
	 *            an AST node of a subtype of {@link IdentifiableElement} that may appear in the AST, e.g.
	 *            {@link AbstractVariable}, {@link TypeVariable}, {@link TStructMember}.
	 */
	public static VariableEnvironmentElement getScope(AbstractVariable<?> variableInAST) {
		return getScope(variableInAST, isBlockScoped(variableInAST));
	}

	/**
	 * Same as {@link #getScope(AbstractVariable)}, but takes any kind of AST node. Flag <code>isBlockScoped</code> can
	 * be used to determine whether the scope for "block scoped" elements should be returned (i.e. let, const) or the
	 * scope for ordinarily scoped elements (e.g. var).
	 */
	public static VariableEnvironmentElement getScope(EObject astNode, boolean isBlockScoped) {
		VariableEnvironmentElement scope = EcoreUtil2.getContainerOfType(astNode, VariableEnvironmentElement.class);
		if (!isBlockScoped) {
			while (scope != null && scope.appliesOnlyToBlockScopedElements()) {
				scope = EcoreUtil2.getContainerOfType(scope.eContainer(), VariableEnvironmentElement.class);
			}
		}
		return scope;
	}

	/**
	 * Tells if given object is an <em>AST node</em>, i.e. contained below a {@link Script} element.
	 * <p>
	 * Note that it is not possible to tell AST nodes from type model elements only based on the object's type, because
	 * there exist type model entities that may appear as a node in the AST (e.g. some TypeRefs, TStructField).
	 */
	public static boolean isASTNode(EObject obj) {
		// note: despite its name, #getContainerOfType() returns 'obj' if instance of Script
		return EcoreUtil2.getContainerOfType(obj, Script.class) != null;
	}

	/**
	 * Returns <code>true</code> iff the given AST node belongs to top-level code, i.e. is not wrapped in a function or
	 * field accessor.
	 */
	public static boolean isTopLevelCode(EObject astNode) {
		// need ".eContainer()" in next line, because #getContainerOfType() also returns the passed-in astNode itself if
		// it is of the requested type (despite the method's name)
		return EcoreUtil2.getContainerOfType(astNode.eContainer(), FunctionOrFieldAccessor.class) == null;
	}

	/**
	 * Tells if given identifiable element is block scoped, i.e. if it is a variable declared with <code>let</code> or
	 * <code>const</code>. Delegates to {@link VariableDeclarationContainer#isBlockScoped()}.
	 *
	 * @param elemInAST
	 *            an AST node of a subtype of {@link IdentifiableElement} that may appear in the AST, e.g.
	 *            {@link AbstractVariable}, {@link TypeVariable}, {@link TStructMember}.
	 */
	public static boolean isBlockScoped(AbstractVariable<?> elemInAST) {
		if (elemInAST instanceof VariableDeclaration) {
			final VariableDeclarationContainer parent = getVariableDeclarationContainer(
					(VariableDeclaration) elemInAST);
			if (parent != null) {
				return parent.isBlockScoped();
			}
		}
		return false;
	}

	/**
	 * Tells if given AST node is the body of a {@link FunctionOrFieldAccessor}. Returns <code>false</code> if
	 * <code>null</code> is passed in.
	 */
	public static boolean isBodyOfFunctionOrFieldAccessor(EObject astNode) {
		if (astNode != null) {
			final EObject parent = astNode.eContainer();
			if (parent instanceof FunctionOrFieldAccessor) {
				return astNode == ((FunctionOrFieldAccessor) parent).getBody();
			}
		}
		return false;
	}

	/**
	 * Returns all {@link TypeReferenceNode}s contained in the given AST node. The returned nodes might belong to
	 * several {@link EReference}s (for example: when given a class declaration, nodes for the super class reference and
	 * all implemented interface references will be returned at the same time). Only direct contents will be returned.
	 * For AST nodes that cannot contain {@code TypeReferenceNode}s an empty iterable will be returned.
	 */
	public static List<TypeReferenceNode<?>> getContainedTypeReferenceNodes(EObject astNode) {
		List<TypeReferenceNode<?>> result = new ArrayList<>();
		for (EReference eRef : N4JSMetaModelUtils.containersOfTypeReferenceNodes.get(astNode.eClass())) {
			// we know eRef is a containment reference, so no need to worry about proxy resolution in next line
			Object value = astNode.eGet(eRef);
			if (value != null) {
				if (eRef.isMany()) {
					@SuppressWarnings("unchecked")
					Collection<TypeReferenceNode<?>> valueCasted = (Collection<TypeReferenceNode<?>>) value;
					result.addAll(valueCasted);
				} else {
					result.add((TypeReferenceNode<?>) value);
				}
			}
		}
		return result;
	}

	/**
	 * Returns <code>true</code> iff the given AST node is a variable, field, or property that is immutable (e.g. const,
	 * final).
	 */
	public static boolean isImmutable(EObject astNode) {
		if (astNode instanceof PropertyNameValuePair) {
			return isImmutable((PropertyNameValuePair) astNode);
		} else if (astNode instanceof N4FieldDeclaration) {
			return isImmutable((N4FieldDeclaration) astNode);
		} else if (astNode instanceof VariableDeclaration) {
			return isImmutable((VariableDeclaration) astNode);
		}
		return false;
	}

	/** Same as {@link #isImmutable(EObject)}, but only for properties. */
	public static boolean isImmutable(@SuppressWarnings("unused") PropertyNameValuePair nameValuePair) {
		return false;
	}

	/** Same as {@link #isImmutable(EObject)}, but only for fields. */
	public static boolean isImmutable(N4FieldDeclaration fieldDecl) {
		// note regarding @Final:
		// it is tempting to treat an @Final field as immutable iff it has an initializer expression;
		// however, even in case an initializer expression is provided for a @Final field, its value
		// may be changed via a @Spec-constructor (not in ordinary constructor code) and actually this
		// is a common case because it is an important reason for choosing a @Final field over a const
		// field; thus, we have to treat @Final fields as mutable for the purpose of this method!
		return fieldDecl.isConst();
	}

	/** Same as {@link #isImmutable(EObject)}, but only for variables. */
	public static boolean isImmutable(VariableDeclaration vdecl) {
		return getVariableStatementKeyword(vdecl) == VariableStatementKeyword.CONST;
	}

	/**
	 * Returns the {@link VariableStatementKeyword} of the given variable, i.e. {@code var}, {@code let}, or
	 * {@code const}.
	 */
	public static VariableStatementKeyword getVariableStatementKeyword(VariableDeclaration vdecl) {
		VariableDeclarationContainer container = getVariableDeclarationContainer(vdecl);
		return container != null ? container.getVarStmtKeyword() : null;
	}

	/**
	 * To get from a variable declaration to its "containing" {@link VariableDeclarationContainer} use this method.
	 * Details on why this is required are given {@link VariableDeclarationContainer here}.
	 */
	public static VariableDeclarationContainer getVariableDeclarationContainer(VariableDeclaration varDecl) {
		EObject parent = varDecl.eContainer();
		if (parent instanceof BindingProperty || parent instanceof BindingElement) {
			final EObject destructRoot = DestructureUtils.getRoot(varDecl);
			if (destructRoot instanceof BindingPattern && destructRoot.eContainer() instanceof VariableBinding) {
				parent = destructRoot.eContainer().eContainer();
			}
		}
		if (parent instanceof VariableDeclarationContainer) {
			return (VariableDeclarationContainer) parent;
		}
		return null;
	}

	/**
	 * The heuristically computed this target, but not the directly containing function, in which the expression (or any
	 * other object) is (indirectly) contained, may be null. This typically is an {@link ObjectLiteral}, an
	 * {@link N4ClassDeclaration}, or another outer {@link FunctionDefinition}. Note that for expressions contained in
	 * property name value pairs, it is <b>not</b> the object literal.
	 * <p>
	 * cf. ECMAScript spec 10.4.3 Entering Function Code
	 * </p>
	 */
	public static ThisTarget getProbableThisTarget(EObject location) {
		if (location == null || location.eContainer() == null) {
			return null;
		}

		final ThisArgProvider thisArgProvider = location instanceof N4MethodDeclaration ? (N4MethodDeclaration) location
				: EcoreUtil2.getContainerOfType(location.eContainer(), ThisArgProvider.class);
		if (thisArgProvider == null) {
			return null;
		}

		final ThisTarget thisTarget = EcoreUtil2.getContainerOfType(thisArgProvider.eContainer(), ThisTarget.class);
		if (thisTarget != null) {
			ThisArgProvider indirectThisArgProvider = EcoreUtil2.getContainerOfType(thisArgProvider.eContainer(),
					ThisArgProvider.class);
			if (indirectThisArgProvider != null && EcoreUtil.isAncestor(thisTarget, indirectThisArgProvider)) {
				return null; // nested function
			}
		}
		return thisTarget;
	}

	/**
	 * The parent expression, in which an expression is contained, may be null
	 */
	public static Expression getContainingExpression(EObject eobj) {
		if (eobj == null || eobj.eContainer() == null) {
			return null;
		}
		return EcoreUtil2.getContainerOfType(eobj.eContainer(), Expression.class);
	}

	/**
	 * The statement in which the expression is (indirectly) contained
	 */
	public static Statement getContainingStatement(EObject eobj) {
		if (eobj == null || eobj.eContainer() == null) {
			return null;
		}
		return EcoreUtil2.getContainerOfType(eobj.eContainer(), Statement.class);
	}

	/**
	 * The function or method in which the expression is (indirectly) contained, may be null In most cases it is
	 * recommended to call {@link #getContainingFunctionOrAccessor(EObject)} instead.
	 */
	public static FunctionDefinition getContainingFunction(EObject eobj) {
		if (eobj == null || eobj.eContainer() == null) {
			return null;
		}
		return EcoreUtil2.getContainerOfType(eobj.eContainer(), FunctionDefinition.class);
	}

	/**
	 * The function or method in which the given location is (indirectly) contained, may be null. If a function or
	 * method is passed in, it won't be returned itself but the next outer function or method will be returned (so, this
	 * method behaves differently than {@link EcoreUtil2#getContainerOfType(EObject, Class)}).
	 */
	public static FunctionOrFieldAccessor getContainingFunctionOrAccessor(EObject eobj) {
		if (eobj == null || eobj.eContainer() == null) {
			return null;
		}
		return EcoreUtil2.getContainerOfType(eobj.eContainer(), FunctionOrFieldAccessor.class);
	}

	/**
	 * If the given expression is the expression of a single-expression arrow function, then this method returns that
	 * arrow function; otherwise <code>null</code> is returned.
	 */
	public static ArrowFunction getContainingSingleExpressionArrowFunction(Expression expression) {
		final EObject parent = expression.eContainer();
		final EObject grandparent = parent != null ? parent.eContainer() : null;
		final EObject grandgrandparent = grandparent != null ? grandparent.eContainer() : null;
		if (grandgrandparent instanceof ArrowFunction) {
			final ArrowFunction arrFun = (ArrowFunction) grandgrandparent;
			if (arrFun.isSingleExprImplicitReturn() && arrFun.implicitReturnExpr() == expression) {
				return arrFun;
			}
		}
		return null;
	}

	/**
	 * Returns true iff <code>expr</code> is a semi-legal assignment expression within a constructor to a final field of
	 * the same class. Here, "semi"-legal means that one requirement for a fully legal such assignment is <b>not</b>
	 * checked by this method: the requirement that the declaration of the assigned final field must not have an
	 * initializer expression. For a fully legal assignment to a final field this has to be checked by client code.
	 */
	public static boolean isSemiLegalAssignmentToFinalFieldInCtor(EObject expr, TMember writtenMember) {
		if (!(expr instanceof AssignmentExpression))
			return false;
		final AssignmentExpression assExpr = (AssignmentExpression) expr;
		// left-hand side must be a property access to 'this'
		final Expression lhs = assExpr.getLhs();
		if (!(lhs instanceof ParameterizedPropertyAccessExpression))
			return false;
		final ParameterizedPropertyAccessExpression paExpr = (ParameterizedPropertyAccessExpression) lhs;
		if (!(paExpr.getTarget() instanceof ThisLiteral))
			return false;
		// left-hand side must be a property access to this.writtenMember
		// BUT: check this only if we have a resolved property in paExpr (important if this method used from scoping)
		if (paExpr.getProperty() != null && !paExpr.getProperty().eIsProxy()) {
			if (writtenMember != null) {
				// case 1: writtenMember provided as argument -> must be identical
				if (paExpr.getProperty() != writtenMember)
					return false;
			} else {
				// case 2: writtenMember not provided -> take from paExpr
				if (paExpr.getProperty() instanceof TMember)
					writtenMember = (TMember) paExpr.getProperty();
			}
		}
		// written member must be a final field
		if (!(writtenMember instanceof TField))
			return false;
		final TField field = (TField) writtenMember;
		if (!field.isFinal())
			return false;
		// (IMPORTANT: we do not assert !field.isHasExpression() here, which would be required for a fully-legal write
		// access)
		// assExpr must be located in the constructor of the owner of 'field'
		// (a) find type model element for the function containing the assignment expression
		final FunctionDefinition containingFunction = getContainingFunction(assExpr);
		if (containingFunction == null)
			return false;
		final TFunction containingTFunction = containingFunction.getDefinedFunction();
		if (containingTFunction == null)
			return false;
		// (b) find constructor of the owner of the field
		final ContainerType<?> ownerOfField = field.getContainingType();
		if (ownerOfField == null)
			return false;
		final TMember ctorOfOwner = getOwnOrSuperCtor(ownerOfField);
		if (ctorOfOwner == null)
			return false;
		// (c) compare
		boolean simpleCompare = containingTFunction == ctorOfOwner;
		if (simpleCompare) {
			return true;
		}
		// edge-case (IDEBUG-656): static polyfill, the ctorOf Owner is actually replacing the version inherited from
		// filled type:
		if (containingTFunction.eContainer() instanceof TClass) {
			TClass containingTClass = (TClass) containingTFunction.eContainer();
			if (containingTClass.isStaticPolyfill()
					&& containingTClass.getSuperClassRef() != null
					&& containingTClass.getSuperClassRef().getDeclaredType() instanceof TClass) {
				// get replaced ctor:
				TClass filledClass = (TClass) containingTClass.getSuperClassRef().getDeclaredType();
				TMember replacedCtorOfFilledClass = getOwnOrSuperCtor(filledClass);
				// compare (incl. null)
				return replacedCtorOfFilledClass == ctorOfOwner;
			}
		}

		return false;
	}

	private static TMember getOwnOrSuperCtor(final ContainerType<?> ownerOfField) {
		TClass klass = (TClass) (ownerOfField instanceof TClass ? ownerOfField : null);
		while (null != klass) {
			final TMember ctor = klass.findOwnedMember(CONSTRUCTOR);
			if (null != ctor) {
				return ctor;
			}
			final ParameterizedTypeRef superClassRef = klass.getSuperClassRef();
			if (null == superClassRef) {
				klass = null;
			} else {
				final Type declaredType = superClassRef.getDeclaredType();
				klass = (TClass) (declaredType instanceof TClass ? declaredType : null);
			}
		}
		return null;
	}

	/**
	 * Returns the type model element corresponding to 'obj' or 'obj' itself if it is a type model element already or
	 * <code>null</code>.
	 */
	public static EObject getCorrespondingTypeModelElement(EObject obj) {
		// is obj already a type model element?
		if (obj != null && obj.eClass().getEPackage() == TypesPackage.eINSTANCE) {
			// special case: is obj a TStructMember used in the AST?
			if (obj instanceof TStructMember && ((TStructMember) obj).getDefinedMember() != null)
				return ((TStructMember) obj).getDefinedMember();
			// yes, obj is already a type model element -> simply return it
			return obj;
		}
		// is obj an AST node that defines a type model element?
		if (obj instanceof TypeDefiningElement) {
			return ((TypeDefiningElement) obj).getDefinedType();
		} else if (obj instanceof N4MemberDeclaration) {
			return ((N4MemberDeclaration) obj).getDefinedTypeElement();
		} else if (obj instanceof N4EnumLiteral) {
			return ((N4EnumLiteral) obj).getDefinedLiteral();
		} else if (obj instanceof N4TypeVariable) {
			return ((N4TypeVariable) obj).getDefinedTypeVariable();
		} else if (obj instanceof PropertyAssignment) {
			return ((PropertyAssignment) obj).getDefinedMember();
		} else if (obj instanceof AbstractVariable) {
			return ((AbstractVariable<?>) obj).getDefinedVariable();
		}
		// no type model element found
		return null;
	}

	/**
	 * Returns the AST node corresponding to type model element 'obj' or 'obj' itself if it is an AST node already or
	 * <code>null</code>.
	 */
	public static EObject getCorrespondingASTNode(EObject obj) {
		// is obj already an AST node?
		if (obj != null && obj.eClass().getEPackage() == N4JSPackage.eINSTANCE) {
			return obj;
		}
		// is obj a type model element related to an AST node?
		if (obj instanceof SyntaxRelatedTElement) {
			return ((SyntaxRelatedTElement) obj).getAstElement();
		} else if (obj instanceof TypeVariable) {
			return getCorrespondingTypeVariableInAST((TypeVariable) obj);
		}
		return null;
	}

	/**
	 * Returns the name of the given {@link N4TypeVariable} or {@link TypeVariable}; throws exception if other type is
	 * passed in.
	 */
	public static String getNameOfTypeVarInAST(EObject typeVarInAST) {
		if (typeVarInAST instanceof N4TypeVariable) {
			return ((N4TypeVariable) typeVarInAST).getName();
		} else if (typeVarInAST instanceof TypeVariable) {
			return ((TypeVariable) typeVarInAST).getName();
		}
		throw new IllegalArgumentException("type variable in AST must be a N4TypeVariable or TypeVariable");
	}

	/**
	 * Returns the {@link EAttribute name feature} of the given {@link N4TypeVariable} or {@link TypeVariable}; throws
	 * exception if other type is passed in.
	 */
	public static EAttribute getNameFeatureOfTypeVarInAST(EObject typeVarInAST) {
		if (typeVarInAST instanceof N4TypeVariable) {
			return N4JSPackage.Literals.N4_TYPE_VARIABLE__NAME;
		} else if (typeVarInAST instanceof TypeVariable) {
			return TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME;
		}
		throw new IllegalArgumentException("type variable in AST must be a N4TypeVariable or TypeVariable");
	}

	/**
	 * If the given type variable is located in the TModule, then this method returns its corresponding type variable in
	 * the AST or <code>null</code> if it is not available or not found. Usually returns a {@link N4TypeVariable}, but
	 * in some cases also a {@link TypeVariable} may be returned.
	 */
	public static EObject getCorrespondingTypeVariableInAST(TypeVariable tv) {
		EObject containerInTModule = tv.eContainer();
		if (containerInTModule instanceof Type) {
			List<TypeVariable> typeVarsInTModule = ((Type) containerInTModule).getTypeVars();
			if (!typeVarsInTModule.isEmpty()) {
				EObject containerInAST = getCorrespondingASTNode(containerInTModule);
				List<? extends EObject> typeVarsInAST = null;
				if (containerInAST instanceof GenericDeclaration) {
					typeVarsInAST = ((GenericDeclaration) containerInAST).getTypeVars();
				} else if (containerInAST instanceof TStructMethod) {
					typeVarsInAST = ((TStructMethod) containerInAST).getTypeVars();
				} else if (containerInAST instanceof FunctionTypeExpression) {
					typeVarsInAST = ((FunctionTypeExpression) containerInAST).getTypeVars();
				}
				if (typeVarsInAST != null) {
					int idx = typeVarsInTModule.indexOf(tv);
					if (idx >= 0 && idx < typeVarsInAST.size()) {
						return typeVarsInAST.get(idx);
					}
				}
			}
		}
		return null;
	}

	/**
	 * @return {@code true} for expression statements containing a single string literal. (e.g. a JS directive like '
	 *         "use strict"' )
	 */
	public static boolean isStringLiteralExpression(ScriptElement element) {
		if (element instanceof ExpressionStatement) {
			Expression expression = ((ExpressionStatement) element).getExpression();
			if (expression instanceof StringLiteral) {
				return true;
			}
		}
		return false;
	}

	/**
	 * If the given AST node is a {@link ParenExpression}, this method will return its first ancestor which is not a
	 * {@code ParenExpression}; otherwise returns the given AST node. Will return <code>null</code> if a value of
	 * <code>null</code> is passed in or if a non-contained ParenExpression is reached while moving upwards.
	 */
	public static EObject skipParenExpressionUpward(EObject astNode) {
		while (astNode instanceof ParenExpression) {
			astNode = astNode.eContainer();
		}
		return astNode;
	}

	/**
	 * If the given AST node is a {@link ParenExpression}, this method will return its first nested expression which is
	 * not a {@code ParenExpression}; otherwise returns the given AST node.
	 * <p>
	 * In case of a {@code ParenExpression} without a nested expression (i.e. broken AST) this method will return this
	 * {@code ParenExpression}. Hence, this method will return <code>null</code> only if passed in a value of
	 * <code>null</code>.
	 */
	public static TypableElement skipParenExpressionDownward(TypableElement astNode) {
		while (astNode instanceof ParenExpression) {
			final Expression expr = ((ParenExpression) astNode).getExpression();
			if (expr == null) {
				break; // avoid returning null in case of broken AST (we return the broken ParenExpression)
			}
			astNode = expr;
		}
		return astNode;
	}

	/**
	 * Returns the name of the given AST or types model element or <code>null</code> if it does not have a name.
	 *
	 * @see N4JSMetaModelUtils#getElementNameFeature(EObject)
	 */
	public static String getElementName(EObject element) {
		if (element == null) {
			return null;
		} else if (element instanceof LiteralOrComputedPropertyName) {
			return ((LiteralOrComputedPropertyName) element).getName();
		}
		EStructuralFeature nameFeature = N4JSMetaModelUtils.getElementNameFeature(element);
		if (nameFeature != null) {
			Object name = element.eGet(nameFeature);
			if (name instanceof LiteralOrComputedPropertyName) {
				return ((LiteralOrComputedPropertyName) name).getName();
			} else if (name instanceof String) {
				return (String) name;
			}
		}
		return null;

	}

	/**
	 * Returns the formal parameters of the given declared function, function expression, method or accessor. Always
	 * returns an empty list when given a getter.
	 */
	public static List<FormalParameter> getFormalParameters(FunctionOrFieldAccessor functionOrFieldAccessor) {
		if (functionOrFieldAccessor == null) {
			return Collections.emptyList();
		} else if (functionOrFieldAccessor instanceof GetterDeclaration) {
			return Collections.emptyList();
		} else if (functionOrFieldAccessor instanceof SetterDeclaration) {
			return Collections.singletonList(((SetterDeclaration) functionOrFieldAccessor).getFpar());
		} else if (functionOrFieldAccessor instanceof FunctionDefinition) {
			return ImmutableList.copyOf(((FunctionDefinition) functionOrFieldAccessor).getFpars());
		} else {
			throw new IllegalStateException("unhandled subclass of FunctionOrFieldAccessor: "
					+ functionOrFieldAccessor.getClass().getSimpleName());
		}
	}

	/**
	 * Computes an MD5 hash from the given resource's source code (the actual source text), as stored in
	 * {@link TModule#getAstMD5()}. Will fail with an exception if the given resource does not have a valid
	 * {@link XtextResource#getParseResult() parse result}, as created by Xtext during parsing.
	 */
	public static String md5Hex(XtextResource resource) {
		final IParseResult parseResult = resource.getParseResult();
		final INode rootNode = parseResult != null ? parseResult.getRootNode() : null;
		final String source = rootNode != null ? rootNode.getText() : null;
		if (source == null) {
			throw new IllegalStateException("resource does not have a valid parse result: " + resource.getURI());
		}
		return Hashing.murmur3_128(SEED).hashString(source, Charsets.UTF_8).toString();
	}

	/** Adds the given annotation to the given element. */
	public static void addAnnotation(AnnotableElement elem, Annotation ann) {
		if (elem instanceof AnnotableScriptElement) {
			AnnotableScriptElement elemCasted = (AnnotableScriptElement) elem;
			if (elemCasted.getAnnotationList() == null) {
				elemCasted.setAnnotationList(N4JSFactory.eINSTANCE.createAnnotationList());
			}
			elemCasted.getAnnotationList().getAnnotations().add(ann);
		} else if (elem instanceof AnnotablePropertyAssignment) {
			AnnotablePropertyAssignment elemCasted = (AnnotablePropertyAssignment) elem;
			if (elemCasted.getAnnotationList() == null) {
				elemCasted.setAnnotationList(N4JSFactory.eINSTANCE.createPropertyAssignmentAnnotationList());
			}
			elemCasted.getAnnotationList().getAnnotations().add(ann);
		} else if (elem instanceof AnnotableN4MemberDeclaration) {
			AnnotableN4MemberDeclaration elemCasted = (AnnotableN4MemberDeclaration) elem;
			if (elemCasted.getAnnotationList() == null) {
				elemCasted.setAnnotationList(N4JSFactory.eINSTANCE.createN4MemberAnnotationList());
			}
			elemCasted.getAnnotationList().getAnnotations().add(ann);
		} else {
			elem.getAnnotations().add(ann);
		}
	}
}
