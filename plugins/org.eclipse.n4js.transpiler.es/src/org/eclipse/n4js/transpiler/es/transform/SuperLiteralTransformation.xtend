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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.SuperLiteral
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesBefore
import org.eclipse.n4js.transpiler.TransformationDependency.Optional
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.DelegationAssistant
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.xtext.EcoreUtil2

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Dependencies:
 * <ul>
 * <li>excludesBefore {@link ClassDeclarationTransformation}:
 *     this transformation relies on N4MemberDeclarations which are removed by ClassDeclarationTransformation.
 * </ul>
 */
@Optional(AsyncAwait) // FIXME improve this: move code for transpiling super-access to AsyncAwaitTransformation OR remove transpilation of async/await completely!
@ExcludesBefore(ClassDeclarationTransformation)
class SuperLiteralTransformation extends Transformation {

	@Inject TypeAssistant typeAssistant;
	@Inject DelegationAssistant delegationAssistant;


	override assertPreConditions() {
		// none
	}

	override assertPostConditions() {
		// none
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, N4MemberDeclaration, false).forEach[transformSuper];
	}

	def private void transformSuper(N4MemberDeclaration memDecl) {
		val owner = memDecl.owner;
		if(owner instanceof N4ClassDeclaration) { // only search classes (super not allowed in interfaces)
			if(memDecl instanceof FunctionOrFieldAccessor) { // only search members that have a body
				val body = memDecl.body;
				if(body!==null) // it is legal for members to have no body (in some cases)
					transformSuper(owner, body);
			}
			// note: super not allowed in field initializers
			// (if it were ever made legal, it would have to be handled here)
		}
	}

	def private void transformSuper(N4ClassDeclaration classDecl, Block block) {
		block.eAllContents.filter(SuperLiteral).forEach[sl|
			val p = sl.eContainer;
			if(p instanceof ParameterizedCallExpression) {
				if(p.target === sl) {
//					transformSuperCall(classDecl, p);
				}
			} else if(p instanceof ParameterizedPropertyAccessExpression_IM) {
				if(p.target === sl) {
					transformSuperAccess(classDecl, p);
				}
			}
		];
	}

	def private void transformSuperCall(N4ClassDeclaration classDecl, ParameterizedCallExpression callExpr) {
		// transform:
		//     super(arg)
		// -->
		//     S.prototype.constructor.call(this, arg)
		// (with S being the immediate super class)
		val superClassSTE = typeAssistant.getSuperClassSTE(classDecl);
		val prototypeSTE = getSymbolTableEntryForMember(state.G.objectType, "prototype", false, true, true);
		val constructorSTE = getSymbolTableEntryForMember(state.G.objectType, "constructor", false, false, true);
		val callSTE = getSymbolTableEntryForMember(state.G.functionType, "call", false, false, true);

		// (note: don't have to care about static / non-static here, because we are always in a constructor)

		setTarget(callExpr, __NSSafe_PropertyAccessExpr(superClassSTE, prototypeSTE, constructorSTE, callSTE));
		addArgument(callExpr, 0, _ThisLiteral());

		state.info.markAsExplicitSuperCall(callExpr);
	}

	/**
	 * This convenience method allows other transformations to extract the arguments from an explicit super call.
	 */
	def public static Expression[] getArgumentsFromExplicitSuperCall(Statement superCallStmnt) {
		if(superCallStmnt===null) {
			throw new IllegalArgumentException("superCallStmnt may not be null");
		}
		if(superCallStmnt instanceof ExpressionStatement) {
			val expr = superCallStmnt.expression;
			if(expr instanceof ParameterizedCallExpression) {
				if(expr.target instanceof SuperLiteral) {
					return expr.arguments.map[expression];
				}
			}
		}
		throw new IllegalArgumentException("explicit super call has an unexpected structure");
	}

	def private void transformSuperAccess(N4ClassDeclaration classDecl, ParameterizedPropertyAccessExpression_IM accExpr) {
	    // note: field access is not possible with super-literal but using getter/setter is still possible.
	    // this leads to two cases:
	    // A) super in conjunction with a method call: super.m()
	    // B) a simple PropertyAccess involving getter/setter : super.s=5;  or super.g;

		// NOTE about cast in next line: we do not allow class declarations in the IM that have a super class
		// without a TModule element (i.e. a super class created programmatically in the IM), so we can be sure that
		// in "super.m()" the STE for m is a SymbolTableEntryOriginal (i.e. there is an originalTarget for m)
		val propertySTE = accExpr.property_IM as SymbolTableEntryOriginal;

		// in both cases we need the super target, so prepare this here:
		//
		//     S.prototype          (non-static case)
		//     S                    (static case)
		// (with S being the immediate super class)
		val containingFunctionOrAccessor = EcoreUtil2.getContainerOfType(accExpr, N4MemberDeclaration);
		val isStatic = containingFunctionOrAccessor!==null && containingFunctionOrAccessor.static;
		val superTarget = if(propertySTE.isMethod) {
			delegationAssistant.createAccessToSuperClass(classDecl, isStatic)
		} else {
			delegationAssistant.createAccessToSuperClassBequestingMember(classDecl, isStatic, propertySTE)
		};

		// now proceed depending on case A or B:
		if(propertySTE.isMethod) {
			// CASE A
			transformSuperMethodAccess(accExpr, superTarget);
		} else if(propertySTE.isGetter || propertySTE.isSetter) {
			// CASE B
			transformSuperFieldAccessorAccess(accExpr, superTarget, propertySTE);
		} else {
			// neither CASE A nor CASE B -> error
			throw new IllegalStateException("super member access to a SymbolTableEntry that could not be identified as a method or field accessor")
		}
	}

	def private void transformSuperMethodAccess(ParameterizedPropertyAccessExpression_IM accExpr, Expression superTarget) {
		// CASE A)
		// transform:
		//     super.m(args)
		// -->
		//     S.prototype.m.call(this, args)          (non-static case)
		//     S.m.call(this, args)                    (static case)
		// (with S being the immediate super class)

		// step 1: replace "super" by "S.prototype" (non-static) or "S" (static)
		setTarget(accExpr, superTarget); // --> S.prototype.m | S.m

		// usually we have a parent ParameterizedCallExpression
		val parent = accExpr.eContainer;
		if(parent instanceof ParameterizedCallExpression) {
			// current situation: S.prototype.m(args) | S.m(args)
			val callSTE = getSymbolTableEntryForMember(state.G.functionType, "call", false, false, true);
			// step 2: replace "S.prototype.m" by "S.prototype.m.call"
			setTarget(parent, _PropertyAccessExpr(accExpr, callSTE)); // --> S.prototype.m.call(args)
			// step 3: add "this" to beginning of arguments list
			addArgument(parent, 0, _ThisLiteral()); // --> S.prototype.m.call(this, args)
		}
	}

	def private void transformSuperFieldAccessorAccess(ParameterizedPropertyAccessExpression_IM accExpr,
		Expression superTarget, SymbolTableEntryOriginal propertySTE) {
		// CASE B)
		// transform:
		//     super.x  			                                                     (x can be setter or getter)
		// -->
		//     Object.getOwnPropertyDescriptor(S.prototype, 'x').get.call(this)          (non-static case)
		//     Object.getOwnPropertyDescriptor(S, 'x').get.call(this)                    (static case)
		// or
		//     Object.getOwnPropertyDescriptor(S.prototype, 'x').set.call(this, value)   (non-static case)
		//     Object.getOwnPropertyDescriptor(S, 'x').set.call(this, value)             (static case)
		// (with S being the immediate super class)

		val isSetter = propertySTE.isSetter
		val ObjectSTE = getSymbolTableEntryOriginal(state.G.objectType, true);
		val getOwnPropertyDescriptorSTE = getSymbolTableEntryForMember(state.G.objectType, "getOwnPropertyDescriptor", false, true, true);
		val callSTE = getSymbolTableEntryForMember(state.G.functionType, "call", false, false, true);

		// create:
		// Object.getOwnPropertyDescriptor(S.prototype, 'x').get|set.call(this)
		val replacement = _CallExpr(
			// Object.getOwnPropertyDescriptor(S.prototype, 'x').get.call
			_PropertyAccessExpr(
				// Object.getOwnPropertyDescriptor(S.prototype, 'x')
				_CallExpr(
					// Object.getOwnPropertyDescriptor
					_PropertyAccessExpr(ObjectSTE, getOwnPropertyDescriptorSTE),
					// S.prototype
					superTarget,
					// 'x'
					_StringLiteralForSTE(propertySTE)
				),
				// .get | .set
				if(isSetter) steFor_set else steFor_get,
				// .call
				callSTE
			),
			// (this)
			_ThisLiteral()
		);

		if(isSetter) {
			// we have a setter -> so we have a parent assignment expression
			val parent = accExpr.eContainer as AssignmentExpression;
			val value = parent.rhs;

			replacement.arguments += _Argument(value);
			replace(parent, replacement);
		} else {
			// we have a getter
			replace(accExpr, replacement);
		}
	}

	def private boolean isMethod(SymbolTableEntryOriginal ste) {
		ste.originalTarget instanceof TMethod || ste.elementsOfThisName.exists[it instanceof N4MethodDeclaration]
	}
	def private boolean isGetter(SymbolTableEntryOriginal ste) {
		ste.originalTarget instanceof TGetter || ste.elementsOfThisName.exists[it instanceof N4GetterDeclaration]
	}
	def private boolean isSetter(SymbolTableEntryOriginal ste) {
		ste.originalTarget instanceof TSetter || ste.elementsOfThisName.exists[it instanceof N4SetterDeclaration]
	}
}
