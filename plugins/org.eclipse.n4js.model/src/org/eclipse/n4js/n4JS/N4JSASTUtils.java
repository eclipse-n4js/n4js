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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * General helpers for navigating in the AST. Some of these are used by convenience methods in N4JS model classes.
 *
 * Hint: use <code>import static extension org.eclipse.n4js.n4JS.N4JSASTUtils.*</code> in Xtend
 */
public abstract class N4JSASTUtils {

	/** The reserved {@value} keyword. */
	public static final String CONSTRUCTOR = "constructor";

	/**
	 * Returns the containing variable environment scope for the given identifiable element, depending on whether the
	 * element is block scoped (i.e. variables declared with let, const) or not.
	 *
	 * @param elemInAST
	 *            an AST node of a subtype of {@link IdentifiableElement} that may appear in the AST, e.g.
	 *            {@link Variable}, {@link TypeVariable}, {@link TStructMember}.
	 */
	public static VariableEnvironmentElement getScope(IdentifiableElement elemInAST) {
		return getScope(elemInAST, isBlockScoped(elemInAST));
	}

	/**
	 * Same as {@link #getScope(IdentifiableElement)}, but takes any kind of AST node. Flag <code>isBlockScoped</code>
	 * can be used to determine whether the scope for "block scoped" elements should be returned (i.e. let, const) or
	 * the scope for ordinarily scoped elements (e.g. var).
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
	 * Tells if given identifiable element is block scoped, i.e. if it is a variable declared with <code>let</code> or
	 * <code>const</code>. Delegates to {@link VariableDeclarationContainer#isBlockScoped()}.
	 *
	 * @param elemInAST
	 *            an AST node of a subtype of {@link IdentifiableElement} that may appear in the AST, e.g.
	 *            {@link Variable}, {@link TypeVariable}, {@link TStructMember}.
	 */
	public static boolean isBlockScoped(IdentifiableElement elemInAST) {
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
	 * To get from a variable declaration to its "containing" {@link VariableDeclarationContainer} use this method.
	 * Details on why this is required are given {@link VariableDeclarationContainer here}.
	 */
	public static VariableDeclarationContainer getVariableDeclarationContainer(VariableDeclaration varDecl) {
		EObject parent = varDecl.eContainer();
		if (parent instanceof BindingProperty || parent instanceof BindingElement) {
			final EObject destructRoot = getRootOfDestructuringPattern(varDecl);
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
	 * Tells if the given AST node contains a variable declaration in form of a destructuring pattern.
	 */
	public static boolean containsDestructuringPattern(VariableDeclarationContainer vdeclContainer) {
		for (VariableDeclarationOrBinding vdeclOrBinding : vdeclContainer.getVarDeclsOrBindings())
			if (vdeclOrBinding instanceof VariableBinding)
				return true;
		return false;
	}

	/**
	 * Tells if the given expression may form a destructuring pattern if it appears on the LHS of an assignment
	 * expression. This method does <b>not</b> check if the expression actually appears on the LHS of an assignment.
	 * <p>
	 * NOTE: does <b>not</b> check the entire supplementary syntax for destructuring assignments from Section 12.14.5
	 * "Destructuring Assignment" of the ECMA Script 6 specification. This is done by dedicated validations in
	 * ASTStructureValidator.
	 */
	public static boolean isLeftHandSideDestructuringPattern(Expression expr) {
		return expr instanceof ObjectLiteral || expr instanceof ArrayLiteral;
	}

	/**
	 * Tells if the given assignment expression is a destructuring assignment, i.e. has an array or object destructuring
	 * pattern on its LHS.
	 * <p>
	 * NOTE: does <b>not</b> check the entire supplementary syntax for destructuring assignments from Section 12.14.5
	 * "Destructuring Assignment" of the ECMA Script 6 specification. This is done by dedicated validations in
	 * ASTStructureValidator.
	 */
	public static boolean isDestructuringAssignment(AssignmentExpression expr) {
		return isLeftHandSideDestructuringPattern(expr.getLhs());
	}

	/**
	 * Tells if the given for statement is a destructuring for statement, i.e. for cases like:
	 *
	 * <pre>
	 * for(var [a,b] of arr) {}
	 * for([a,b] of arr) {}
	 * </pre>
	 *
	 * Note that this method returns <code>false</code> for cases like ...
	 *
	 * <pre>
	 * for(var [a,b] = [2,4]; a<10; a++) {}
	 * </pre>
	 *
	 * because in such cases the destructuring is seen as part of the variable declaration, not the for statement.
	 */
	public static boolean isDestructuringForStatement(ForStatement stmnt) {
		return (stmnt.isForIn() || stmnt.isForOf())
				&& (containsDestructuringPattern(stmnt) // case: for(var [a,b] of arr) {}
						|| isLeftHandSideDestructuringPattern(stmnt.getInitExpr())); // case: for([a,b] of arr) {}
	}

	/**
	 * Returns true iff given object is an array/object literal used as a destructuring pattern (i.e. on lhs of an
	 * assignment or in a for..in/of loop or it is a nested literal within such an array/object literal).
	 */
	public static boolean isArrayOrObjectLiteralUsedAsDestructuringPattern(EObject obj) {
		if (!(obj instanceof ArrayLiteral || obj instanceof ObjectLiteral))
			return false;
		final EObject root = getRootOfDestructuringPattern(obj);
		final EObject parent = root.eContainer();
		if (parent instanceof AssignmentExpression)
			return ((AssignmentExpression) parent).getLhs() == root;
		if (parent instanceof ForStatement)
			return !((ForStatement) parent).isForPlain() && ((ForStatement) parent).getInitExpr() == root;
		return false;
	}

	/**
	 * Returns true iff given object is an array/object literal used <b>as input to</b> a destructuring pattern (i.e. as
	 * expression of a VariableBinding, on rhs of an assignment or in a for..in/of loop or it is a nested literal within
	 * such an array/object literal).
	 * <p>
	 * TODO this method does not properly support nesting yet, i.e. in code like
	 *
	 * <pre>
	 * var [string a, Array<?> arr, number b] = ["hello", [1,2,3], 42];
	 * </pre>
	 *
	 * it will return <code>true</code> for the nested array <code>[1,2,3]</code>, which is wrong.
	 */
	public static boolean isArrayOrObjectLiteralBeingDestructured(EObject obj) {
		if (!(obj instanceof ArrayLiteral || obj instanceof ObjectLiteral))
			return false;
		final EObject root = getRootOfDestructuringPattern(obj);
		final EObject parent = root.eContainer();
		if (parent instanceof VariableBinding)
			return ((VariableBinding) parent).getExpression() == root;
		if (parent instanceof AssignmentExpression && isDestructuringAssignment((AssignmentExpression) parent))
			return ((AssignmentExpression) parent).getRhs() == root;
		if (parent instanceof ForStatement && isDestructuringForStatement((ForStatement) parent)) {
			// reason why we require obj!=root below:
			// in code like "for([a,b] of [ ["hello",42], ["world",43] ]) {}" the top-level array literal after
			// the "of" is *NOT* an array being destructured; instead, it's the array being iterated over.
			return !((ForStatement) parent).isForPlain() && ((ForStatement) parent).getExpression() == root
					&& obj != root;
		}
		return false;
	}

	/**
	 * When given an AST node in a destructuring pattern, tells if the given node is the root of the containing
	 * destructuring pattern.
	 * <p>
	 * NOTE: this method assumes that the argument is actually part of a destructuring pattern; otherwise the return
	 * value is undefined.
	 */
	public static boolean isRootOfDestructuringPattern(EObject nodeWithinDestructuringPattern) {
		return !isParentPartOfSameDestructuringPattern(nodeWithinDestructuringPattern);
	}

	/**
	 * When given an AST node in a destructuring pattern, returns the root ArrayLiteral, ObjectLiteral, or
	 * BindingPattern of the containing tree of nested array/object literals. Will return <code>obj</code> if it already
	 * is such a root.
	 * <p>
	 * Can also be used in array and object literals that are <b>not</b> actually used as destructuring patterns, i.e.
	 * that aren't located on the LHS of an AssignmentExpression.
	 * <p>
	 * NOTE: this method assumes that the argument is actually part of a destructuring pattern (or at least array/object
	 * literal); otherwise the return value is undefined.
	 */
	public static EObject getRootOfDestructuringPattern(EObject nodeWithinDestructuringPattern) {
		EObject curr = nodeWithinDestructuringPattern;
		while (isParentPartOfSameDestructuringPattern(curr)) {
			curr = curr.eContainer();
		}
		if (curr instanceof ObjectLiteral || curr instanceof ArrayLiteral || curr instanceof BindingPattern)
			return curr;
		return null;
	}

	private static boolean isParentPartOfSameDestructuringPattern(EObject obj) {
		final EObject parent = obj != null ? obj.eContainer() : null;
		return parent instanceof ArrayLiteral || parent instanceof ArrayElement
				|| parent instanceof ObjectLiteral || parent instanceof PropertyAssignment
				|| (parent instanceof AssignmentExpression
						&& obj == ((AssignmentExpression) parent).getLhs()
						&& (parent.eContainer() instanceof ArrayElement
								|| parent.eContainer() instanceof PropertyAssignment))
				|| parent instanceof BindingPattern || parent instanceof BindingElement
				|| parent instanceof BindingProperty;
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
		final Type containingTFunction = containingFunction.getDefinedType();
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
			return obj;
		}
		// is obj an AST node that defines a type model element?
		if (obj instanceof TypeDefiningElement) {
			return ((TypeDefiningElement) obj).getDefinedType();
		} else if (obj instanceof N4MemberDeclaration) {
			return ((N4MemberDeclaration) obj).getDefinedTypeElement();
		} else if (obj instanceof PropertyAssignment) {
			return ((PropertyAssignment) obj).getDefinedMember();
		} else if (obj instanceof FormalParameter) {
			return ((FormalParameter) obj).getDefinedTypeElement();
		} else if (obj instanceof ExportedVariableDeclaration) {
			return ((ExportedVariableDeclaration) obj).getDefinedVariable();
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
		if (obj != null && obj.eClass().getEPackage() == N4JSPackage.eINSTANCE)
			return obj;
		// is obj a type model element related to an AST node?
		if (obj instanceof SyntaxRelatedTElement)
			return ((SyntaxRelatedTElement) obj).getAstElement();
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
	 * If the given AST node is a {@link ParenExpression}, this method will return its first nested expression which is
	 * not a {@code ParenExpression}; otherwise returns the given AST node.
	 * <p>
	 * In case of a {@code ParenExpression} without a nested expression (i.e. broken AST) this method will return this
	 * {@code ParenExpression}. Hence, this method will return <code>null</code> only if passed in a value of
	 * <code>null</code>.
	 */
	public static TypableElement ignoreParentheses(TypableElement astNode) {
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
		return Hashing.md5().hashString(source, Charsets.UTF_8).toString();
	}
}
