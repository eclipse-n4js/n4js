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
package org.eclipse.n4js.postprocessing;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.exists;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.PropertySpread;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper.Callable;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.inject.Inject;

/**
 * Base for all poly processors. Contains some utility and convenience methods.
 */
abstract class AbstractPolyProcessor extends AbstractProcessor {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;

	/**
	 * Convenience method for {@link #isPoly(Expression)} and {@link #isPoly(PropertyAssignment)}, accepting any type of
	 * EObject.
	 */
	boolean isPoly(EObject obj) {
		if (obj instanceof Expression) {
			return isPoly(obj);
		}
		if (obj instanceof PropertyAssignment) {
			return isPoly(obj);
		}
		return false;
	}

	/**
	 * Tells whether the given expression is a poly expression, i.e. requires constraint-based type inference.
	 */
	boolean isPoly(Expression obj) {
		if (obj instanceof ParameterizedCallExpression) {
			ParameterizedCallExpression pce = (ParameterizedCallExpression) obj;
			// NOTE: in next line, we do not propagate the cancel indicator; however, this is not required, because
			// all we do with the newly created rule environment is to type a backward(!) reference, so we can be
			// sure that no significant processing will be triggered by the type judgment invocation below
			RuleEnvironment G = newRuleEnvironment(pce);
			TypeRef targetTypeRef = ts.type(G, pce.getTarget()); // this is a backward reference (because we type obj's
																	// child)
			Callable callable = tsh.getCallableTypeRef(G, targetTypeRef);
			if (callable != null && callable.getSignatureTypeRef().isPresent()) {
				FunctionTypeExprOrRef signatureTypeRef = callable.getSignatureTypeRef().get();
				return N4JSLanguageUtils.isPoly(signatureTypeRef, pce);
			} else {
				return false;
			}
		}
		if (obj instanceof FunctionExpression) {
			FunctionExpression fe = (FunctionExpression) obj;
			return exists(fe.getFpars(), fpar -> fpar.getDeclaredTypeRefInAST() == null)
					// type of 1 or more fpars is undeclared
					|| fe.getDeclaredReturnTypeRefInAST() == null; // return type is undeclared
			// note: if the FunctionExpression is generic, this does *not* make it poly!
		}
		if (obj instanceof ArrayLiteral) {
			return true;
		}
		if (obj instanceof ObjectLiteral) {
			return exists(((ObjectLiteral) obj).getPropertyAssignments(), pa -> isPoly(pa));
		}
		if (obj instanceof ConditionalExpression) {
			ConditionalExpression ce = (ConditionalExpression) obj;
			boolean trueIsPoly = isPoly(ce.getTrueExpression());
			boolean trueAllowsPoly = allowsPoly(ce.getTrueExpression());
			boolean falseAllowsPoly = allowsPoly(ce.getFalseExpression());
			boolean falseIsPoly = isPoly(ce.getFalseExpression());

			return (trueIsPoly && falseAllowsPoly) || (falseIsPoly && trueAllowsPoly);
		}

		return false;

	}

	boolean allowsPoly(Expression obj) {
		if (obj == null) {
			return false;
		}
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(obj);
		return N4JSLanguageUtils.isUndefinedLiteral(G, obj) || N4JSLanguageUtils.isNullLiteral(G, obj);
	}

	/**
	 * True iff the given PropertyAssignment is a poly "expression", i.e. requires constraint-based type inference.
	 */
	private boolean isPoly(PropertyAssignment pa) {
		if (pa instanceof PropertyNameValuePair) {
			PropertyNameValuePair pnvp = (PropertyNameValuePair) pa;
			// FIXME requiring pa.expression!=null is inconsistent!
			return pnvp.getExpression() != null && pnvp.getDeclaredTypeRefInAST() == null;
		}
		if (pa instanceof PropertyGetterDeclaration) {
			PropertyGetterDeclaration pgd = (PropertyGetterDeclaration) pa;
			return pgd.getDeclaredTypeRefInAST() == null;
		}
		if (pa instanceof PropertySetterDeclaration) {
			PropertySetterDeclaration psd = (PropertySetterDeclaration) pa;
			return psd.getDeclaredTypeRefInAST() == null;
		}
		if (pa instanceof PropertyMethodDeclaration) {
			return false;
		}
		if (pa instanceof PropertySpread) {
			return false; // TODO GH-1337 add support for spread operator
		}
		if (pa instanceof PropertyAssignmentAnnotationList) {
			return false;
		}

		throw new IllegalArgumentException("unsupported subclass of PropertyAssignment: " + pa.eClass().getName());
	}

	/**
	 * Convenience method for {@link #isRootPoly(Expression)}, accepting any type of EObject.
	 */
	boolean isRootPoly(EObject obj) {
		return (obj instanceof Expression) ? isRootPoly(obj) : false;
	}

	/**
	 * Tells whether the given expression is a root poly expression, i.e. it
	 * <ol>
	 * <li>is a {@link #isPoly(Expression) poly expression}, <em>and</em>
	 * <li>represents the root of a tree of nested poly expressions which have to be inferred together within a single
	 * constraint system (this tree may have depth 0, i.e. consist only of the given expression).
	 * </ol>
	 */
	boolean isRootPoly(Expression obj) {
		if (isPoly(obj)) {
			EObject p = getParentPolyCandidate(obj);
			return p == null || !isPoly(p);
		}
		return false;
	}

	/**
	 * Given a poly expression, returns the parent expression that <em>might</em> be the parent poly expression. If the
	 * given expression is not poly, the return value is undefined.
	 */
	private EObject getParentPolyCandidate(Expression poly) {
		EObject directParent = poly == null ? null : poly.eContainer();
		EObject grandParent = directParent == null ? null : directParent.eContainer();

		if (directParent instanceof Argument) {
			if (grandParent instanceof ParameterizedCallExpression && toSet(map(
					((ParameterizedCallExpression) grandParent).getArguments(), a -> a.getExpression()))
							.contains(poly)) {
				// TODO what about the target expression? i.e.: || directParent.target==poly) {
				return grandParent;
			}
		} else if (directParent instanceof FunctionExpression) {
			return null; // function expressions never have nested poly expressions (expression in the body are
							// detached)
		} else if (directParent instanceof ArrayElement) {
			if (((Argument) directParent).getExpression() == poly) {
				return directParent.eContainer();// return the ArrayLiteral as parent (not the ArrayElement)
			}
		} else if (directParent instanceof PropertyNameValuePair) {
			if (((Argument) directParent).getExpression() == poly) {
				return directParent;// return the PropertyNameValuePair as parent (not the ObjectLiteral)
			}
		} else if (directParent instanceof ConditionalExpression) {
			return directParent;
		} else if (directParent instanceof PropertyGetterDeclaration) {
			return null;// getters never have nested poly expressions
		} else if (directParent instanceof PropertySetterDeclaration) {
			return null; // setters never have nested poly expressions
		} else if (directParent instanceof PropertySpread) {
			return null;// TODO GH-1337 add support for spread operator
		}

		return null;
	}

	// ------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Returns the type of a nested poly expression. The final type is returned, i.e. not the one created when preparing
	 * the constraint system that may contain inference variables.
	 * <p>
	 * Because final types are created and stored in the typing cache in the onSuccess/onFailure lambdas and those
	 * lambdas of nested poly expressions are registered before those of outer expression, we can here simply read the
	 * nested poly expression's type from the cache.
	 */
	protected TypeRef getFinalResultTypeOfNestedPolyExpression(Expression nestedPolyExpression) {
		return ASTMetaInfoUtils.getTypeFailSafe(nestedPolyExpression);
	}

	protected TypeRef subst(TypeRef typeRef, RuleEnvironment G,
			Map<TypeVariable, ? extends TypeVariable> substitutions) {

		return subst(typeRef, G, substitutions, false);
	}

	protected TypeRef subst(TypeRef typeRef, RuleEnvironment G,
			Map<TypeVariable, ? extends TypeVariable> substitutions, boolean reverse) {

		RuleEnvironment Gx = wrap(G);
		for (Entry<TypeVariable, ? extends TypeVariable> e : substitutions.entrySet()) {
			if (reverse) {
				Gx.put(e.getValue(), TypeUtils.createTypeRef(e.getKey()));
			} else {
				Gx.put(e.getKey(), TypeUtils.createTypeRef(e.getValue()));
			}
		}

		TypeRef typeRefSubst = ts.substTypeVariables(Gx, typeRef);
		if (typeRefSubst == null) {
			throw new IllegalArgumentException("substitution failed");
		}
		return typeRefSubst;
	}

	protected TypeRef applySolution(TypeRef typeRef, RuleEnvironment G, Map<InferenceVariable, TypeRef> solution) {
		if (typeRef == null || solution == null || solution.isEmpty()) {
			return typeRef; // note: returning 'null' if typeRef==null (broken AST, etc.)
		}
		RuleEnvironment Gx = wrap(G);
		for (Entry<InferenceVariable, TypeRef> e : solution.entrySet()) {
			Gx.put(e.getKey(), e.getValue());
		}
		TypeRef typeRefSubst = ts.substTypeVariables(Gx, typeRef);
		if (typeRefSubst == null) {
			throw new IllegalArgumentException("substitution failed");
		}
		return typeRefSubst;
	}

	protected Map<InferenceVariable, TypeRef> createPseudoSolution(InferenceContext infCtx,
			TypeRef defaultTypeRef) {

		Map<InferenceVariable, TypeRef> pseudoSolution = new HashMap<>();
		for (InferenceVariable iv : infCtx.getInferenceVariables()) {
			pseudoSolution.put(iv, defaultTypeRef); // map all inference variables to the default
		}
		return pseudoSolution;
	}

	// FIXME move to a better place
	protected boolean isReturningValue(FunctionDefinition fun) {
		return (fun.getBody() != null && exists(fun.getBody().getAllReturnStatements(), s -> s.getExpression() != null))
				|| ((fun instanceof ArrowFunction) ? ((ArrowFunction) fun).isSingleExprImplicitReturn() : false);
		// TODO except call to void function!!
	}

	protected TypeRef getTypeOfMember(TMember m) {
		if (m instanceof TField) {
			return ((TField) m).getTypeRef();
		} else if (m instanceof TGetter) {
			return ((TGetter) m).getTypeRef();
		} else if (m instanceof TSetter) {
			return ((TSetter) m).getFpar().getTypeRef();
		} else if (m instanceof TMethod) {
			throw new IllegalArgumentException("this method should not be used for TMethod");
		}
		String clsName = m == null ? null : m.eClass() == null ? null : m.eClass().getName();
		throw new IllegalArgumentException("unknown subtype of TMember: " + clsName);
	}

	protected void setTypeOfMember(TMember m, TypeRef type) {
		if (m instanceof TField) {
			((TField) m).setTypeRef(type);
		} else if (m instanceof TGetter) {
			((TGetter) m).setTypeRef(type);
		} else if (m instanceof TSetter) {
			TSetter tst = (TSetter) m;
			if (tst.getFpar() != null) {
				tst.getFpar().setTypeRef(type);
			}
		} else if (m instanceof TMethod) {
			throw new IllegalArgumentException("this method should not be used for TMethod");
		}
		String clsName = m == null ? null : m.eClass() == null ? null : m.eClass().getName();
		throw new IllegalArgumentException("unknown subtype of TMember: " + clsName);
	}
}
