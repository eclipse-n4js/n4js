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
package org.eclipse.n4js.postprocessing

import com.google.inject.Inject
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.Argument
import org.eclipse.n4js.n4JS.ArrayElement
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.PropertyAssignment
import org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.PropertySetterDeclaration
import org.eclipse.n4js.n4JS.PropertySpread
import org.eclipse.n4js.n4idl.versioning.MigrationUtils
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.InferenceVariable
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.constraints.InferenceContext
import org.eclipse.n4js.typesystem.utils.RuleEnvironment

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Base for all poly processors. Contains some utility and convenience methods.
 */
package abstract class AbstractPolyProcessor extends AbstractProcessor {

	@Inject
	private N4JSTypeSystem ts;

	/**
	 * Convenience method for {@link #isPoly(Expression)} and {@link #isPoly(PropertyAssignment)}, accepting any type of
	 * EObject.
	 */
	def boolean isPoly(EObject obj) {
		return switch (obj) {
			Expression: obj.isPoly
			PropertyAssignment: obj.isPoly
			default: false
		}
	}

	/**
	 * Tells whether the given expression is a poly expression, i.e. requires constraint-based type inference.
	 */
	def boolean isPoly(Expression obj) {
		return switch (obj) {
			ParameterizedCallExpression: {
				if (MigrationUtils.isMigrateCall(obj)) {
					// Constraint-based type inference is disabled for migrate calls,
					// since the type of the invoked migration is not known at this point.
					return false;
				}
				
				// NOTE: in next line, we do not propagate the cancel indicator; however, this is not required, because
				// all we do with the newly created rule environment is to type a backward(!) reference, so we can be
				// sure that no significant processing will be triggered by the type judgment invocation below
				val G = obj.newRuleEnvironment;
				val TypeRef targetTypeRef = ts.type(G, obj.target); // this is a backward reference (because we type obj's child)
				if (targetTypeRef instanceof FunctionTypeExprOrRef) {
					targetTypeRef.generic && obj.typeArgs.size < targetTypeRef.typeVars.size
				} else {
					false
				}
			}
			FunctionExpression:
				obj.fpars.exists[declaredTypeRef === null] // type of 1 or more fpars is undeclared
				|| obj.declaredReturnTypeRef === null // return type is undeclared
				// note: if the FunctionExpression is generic, this does *not* make it poly!
			ArrayLiteral:
				true
			ObjectLiteral:
				obj.propertyAssignments.exists[isPoly]
			default:
				false
		}
	}

	/**
	 * Tells whether the given PropertyAssignment is a poly "expression", i.e. requires constraint-based type inference.
	 */
	def private boolean isPoly(PropertyAssignment pa) {
		switch (pa) {
			PropertyNameValuePair:
				pa.expression !== null && pa.declaredTypeRef === null // FIXME requiring pa.expression!==null is inconsistent!
			PropertyGetterDeclaration:
				pa.declaredTypeRef === null
			PropertySetterDeclaration:
				pa.declaredTypeRef === null
			PropertyMethodDeclaration:
				false
			PropertySpread:
				false // TODO GH-1337 add support for spread operator
			PropertyAssignmentAnnotationList:
				false
			default:
				throw new IllegalArgumentException("unsupported subclass of PropertyAssignment: " + pa.eClass.name)
		}
	}

	/**
	 * Convenience method for {@link #isRootPoly(Expression)}, accepting any type of EObject.
	 */
	def boolean isRootPoly(EObject obj) {
		if (obj instanceof Expression) obj.isRootPoly else false
	}

	/**
	 * Tells whether the given expression is a root poly expression, i.e. it
	 * <ol>
	 * <li>is a {@link #isPoly(Expression) poly expression}, <em>and</em>
	 * <li>represents the root of a tree of nested poly expressions which have to be inferred together within a single
	 * constraint system (this tree may have depth 0, i.e. consist only of the given expression).
	 * </ol>
	 */
	def boolean isRootPoly(Expression obj) {
		if (isPoly(obj)) {
			val p = getParentPolyCandidate(obj);
			return p === null || !isPoly(p);
		}
		return false;
	}

	/**
	 * Given a poly expression, returns the parent expression that <em>might</em> be the parent poly expression.
	 * If the given expression is not poly, the return value is undefined.
	 */
	def private EObject getParentPolyCandidate(Expression poly) {
		val directParent = poly?.eContainer;
		val grandParent = directParent?.eContainer;
		return switch (directParent) {
			Argument case grandParent instanceof ParameterizedCallExpression &&
				(grandParent as ParameterizedCallExpression).arguments.map[expression].contains(poly): // TODO what about the target expression? i.e.: || directParent.target===poly
				grandParent
			FunctionExpression:
				null // function expressions never have nested poly expressions (expression in the body are detached)
			ArrayElement case directParent.expression === poly:
				directParent.eContainer as ArrayLiteral // return the ArrayLiteral as parent (not the ArrayElement)
			PropertyNameValuePair case directParent.expression === poly:
				directParent // return the PropertyNameValuePair as parent (not the ObjectLiteral)
			PropertyGetterDeclaration:
				null // getters never have nested poly expressions
			PropertySetterDeclaration:
				null // setters never have nested poly expressions
			PropertySpread:
				null // TODO GH-1337 add support for spread operator
		}
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
	def protected TypeRef getFinalResultTypeOfNestedPolyExpression(Expression nestedPolyExpression) {
		return ASTMetaInfoUtils.getTypeFailSafe(nestedPolyExpression);
	}

	def protected TypeRef subst(TypeRef typeRef, RuleEnvironment G,
		Map<TypeVariable, ? extends TypeVariable> substitutions) {

		subst(typeRef, G, substitutions, false)
	}

	def protected TypeRef subst(TypeRef typeRef, RuleEnvironment G,
		Map<TypeVariable, ? extends TypeVariable> substitutions, boolean reverse) {

		val Gx = G.wrap;
		substitutions.entrySet.forEach [ e |
			if (reverse)
				Gx.put(e.value, TypeUtils.createTypeRef(e.key))
			else
				Gx.put(e.key, TypeUtils.createTypeRef(e.value))
		];
		val typeRefSubst = ts.substTypeVariables(Gx, typeRef);
		if (typeRefSubst === null)
			throw new IllegalArgumentException("substitution failed");
		return typeRefSubst;
	}

	def protected TypeRef applySolution(TypeRef typeRef, RuleEnvironment G, Map<InferenceVariable, TypeRef> solution) {
		if (typeRef === null || solution === null || solution.empty) {
			return typeRef; // note: returning 'null' if typeRef==null (broken AST, etc.)
		}
		val Gx = G.wrap;
		solution.entrySet.forEach[e|Gx.put(e.key, e.value)];
		val typeRefSubst = ts.substTypeVariables(Gx, typeRef);
		if (typeRefSubst === null)
			throw new IllegalArgumentException("substitution failed");
		return typeRefSubst;
	}

	def protected Map<InferenceVariable, TypeRef> createPseudoSolution(InferenceContext infCtx,
		TypeRef defaultTypeRef) {

		val pseudoSolution = newHashMap;
		for (iv : infCtx.getInferenceVariables) {
			pseudoSolution.put(iv, defaultTypeRef); // map all inference variables to the default
		}
		return pseudoSolution;
	}

	// FIXME move to a better place
	def protected boolean isReturningValue(FunctionDefinition fun) {
		return (fun.body !== null && fun.body.allReturnStatements.exists[expression !== null]) ||
			(if (fun instanceof ArrowFunction) fun.singleExprImplicitReturn else false); // TODO except call to void function!!
	}

	def protected TypeRef getTypeOfMember(TMember m) {
		switch (m) {
			TField:
				m.typeRef
			TGetter:
				m.typeRef
			TSetter:
				m?.fpar.typeRef
			TMethod:
				throw new IllegalArgumentException("this method should not be used for TMethod")
			default:
				throw new IllegalArgumentException("unknown subtype of TMember: " + m?.eClass?.name)
		}
	}

	def protected void setTypeOfMember(TMember m, TypeRef type) {
		switch (m) {
			TField:
				m.typeRef = type
			TGetter:
				m.typeRef = type
			TSetter:
				if (m.fpar !== null) m.fpar.typeRef = type
			TMethod:
				throw new IllegalArgumentException("this method should not be used for TMethod")
			default:
				throw new IllegalArgumentException("unknown subtype of TMember: " + m?.eClass?.name)
		}
	}
}
