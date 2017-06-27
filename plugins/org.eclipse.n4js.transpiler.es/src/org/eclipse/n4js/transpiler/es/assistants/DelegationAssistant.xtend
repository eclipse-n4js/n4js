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
package org.eclipse.n4js.transpiler.es.assistants

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.im.DelegatingMember
import org.eclipse.n4js.transpiler.im.ImFactory
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.util.SuperInterfacesIterable
import org.eclipse.n4js.utils.RecursionGuard

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * This assistant provides helper methods to create members that delegate to some other target member (see
 * {@link DelegationAssistant#createDelegatingMember(TClassifier, TMember) createDelegatingMember(TClassifier, TMember)})
 * and to create the Javascript output code to actually implement these delegating members in the transpiler output
 * (see {@link DelegationAssistant#createDelegation(DelegatingMember) createDelegation(DelegatingMember)}).
 * <p>
 * Usually inherited members in a classifier do not require any special code, because they will be accessed via the
 * native prototype chain mechanism of Javascript. However, there are special cases when some special code has to be
 * generated for an inherited member in order to properly access that inherited member, because it is not available
 * via the ordinary prototype chain.
 */
class DelegationAssistant extends TransformationAssistant {

	@Inject private TypeAssistant typeAssistant;


	/**
	 * Creates a new delegating member intended to be inserted into classifier <code>origin</code> in order to delegate
	 * from <code>origin</code> to the given member <code>target</code>. The target member is assumed to be an inherited
	 * or consumed member of classifier <code>origin</code>, i.e. it is assumed to be located in one of the ancestor
	 * classes of <code>origin</code> or one of its directly or indirectly implemented interfaces (but not in origin
	 * itself!).
	 * <p>
	 * Throws exceptions in case of invalid arguments or an invalid internal state, see implementation for details.
	 */
	def public DelegatingMember createDelegatingMember(TClassifier origin, TMember target) {
		if(target.containingType===origin) {
			throw new IllegalArgumentException("no point in delegating to an owned member");
		}
		val result = switch(target) {
			TField: throw new IllegalArgumentException("delegation to fields not supported yet")
			TGetter: ImFactory.eINSTANCE.createDelegatingGetterDeclaration
			TSetter: ImFactory.eINSTANCE.createDelegatingSetterDeclaration
			TMethod: ImFactory.eINSTANCE.createDelegatingMethodDeclaration
		};
		// set simple properties
		result.declaredName = _LiteralOrComputedPropertyName(target.name);
		result.delegationTarget = getSymbolTableEntryOriginal(target, true);
		if(target.static) {
			result.declaredModifiers += N4Modifier.STATIC;
		}
		// set delegationBaseType and delegationSuperClassSteps
		if(origin instanceof TInterface) {
			// we are in an interface and the target must also be in an interface
			if(!(target.eContainer instanceof TInterface)) {
				throw new IllegalArgumentException("cannot delegate from an interface to member of a class");
			}
			val tSuper = getDirectSuperTypeBequestingMember(origin, target);
			// we know the STE of tSuper must already exist, because it is a direct super type and must therefore be
			// referenced in the declaration of classifier origin
			result.delegationBaseType = getSymbolTableEntryOriginal(tSuper, false);
			result.delegationSuperClassSteps = 0;
		} else if(origin instanceof TClass) {
			// we are in a class and the target may either be in a class or an interface
			val tAncestor = getAncestorClassBequestingMember(origin, target);
			if(tAncestor!==origin) {
				// we are inheriting 'target' from one of our ancestor classes -> delegate to that ancestor class
				// (note: this includes the case the 'target' is contained in an interface and one of our ancestor
				// classes implements that interface)
				val tSuper = origin.superClassRef.declaredType as TClass;
				result.delegationBaseType = getSymbolTableEntryOriginal(tSuper, false);
				result.delegationSuperClassSteps = origin.getDistanceToAncestorClass(tAncestor) - 1;
			} else if(tAncestor!==null) {
				// we are consuming 'target' from one of our directly implemented interfaces or its extended interfaces
				// (similar as case "origin instanceof TInterface" above)
				val tSuper = getDirectSuperTypeBequestingMember(origin, target);
				result.delegationBaseType = getSymbolTableEntryOriginal(tSuper, false);
				result.delegationSuperClassSteps = 0;
			} else {
				throw new IllegalStateException("cannot find target (probably not an inherited member)");
			}
		} else {
			throw new IllegalArgumentException("unsupported subtype of TClassifier: " + origin.eClass.name);
		}
		// set some properties to let this delegating member behave more like an ordinary member of the same type
		result.delegationTargetIsAbstract = target.isAbstract;
		if( ! target.isAbstract ) result.body = _Block();
		return result;
	}


	/**
	 * Creates code to establish the actual member delegation for the given delegating member. It returns an expression
	 * that evaluates to the delegation target from within the context of the delegation origin (here, "target" and
	 * "origin" refers to the two arguments of method {@link #createDelegatingMember(TClassifier, TMember)}). The code
	 * returned by this method is intended to be used in the member definitions passed to the <code>$makeClass</code>
	 * and <code>$makeInterface</code> calls.
	 */
	def public Expression createDelegationCode(DelegatingMember delegator) {
		val baseSTE = delegator.delegationBaseType;
		val baseIsInterface = baseSTE?.originalTarget instanceof TInterface;

		if(baseIsInterface) {
			// if target is non-static:
			//     I.$methods.t.value
			//
			// if target is static:
			//     I.t
			//
			// if target is a symbol, for example built-in symbol "iterator":
			//     I.$methods[Symbol.iterator].value
			//     I[Symbol.iterator]
			//

			val targetName = delegator.delegationTarget.name;
			val targetIsSymbol = targetName!==null && targetName.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX);
			val targetSTE = delegator.delegationTarget;

			return if(!delegator.static) {
				val $methodsSTE =  steFor_$methods;
				val valueSTE = getPropertyDescriptorValueProperty(delegator);
// ---------
// original code:
//				if(!targetIsSymbol) {
//					// I.$methods.t.value
//					__NSSafe_PropertyAccessExpr(baseSTE, $methodsSTE, targetSTE, valueSTE)
//				} else {
//					// I.$methods[Symbol.iterator].value
//					_PropertyAccessExpr(
//						_IndexAccessExpr(
//							__NSSafe_PropertyAccessExpr(baseSTE, $methodsSTE),
//							typeAssistant.getMemberNameAsSymbol(targetName) // something like: Symbol.iterator
//						),
//						valueSTE
//					)
//				}
// ---------
// The following is a temporary work-around for member consumption across files with cyclic dependencies
// (see test /org.eclipse.n4js.transpiler.es5.tests/testdata/circularDependency/consumedMembers/Main.n4js.xt)
// This problem can only be solved properly by a redesign of $makeClass, but the following is a temporary, partial fix:
				if(!targetIsSymbol) {
					// I.$methods.t.value
					_ConditionalExpr(
						__NSSafe_PropertyAccessExpr(baseSTE, $methodsSTE),
						__NSSafe_PropertyAccessExpr(baseSTE, $methodsSTE, targetSTE, valueSTE),
						_FunExpr(false, #[
							_ReturnStmnt(_CallExpr(
								__NSSafe_PropertyAccessExpr(
									baseSTE, $methodsSTE, targetSTE, valueSTE,
									getSymbolTableEntryForMember(state.G.functionType, "apply", false, false, true)
								),
								_ThisLiteral,
								_IdentRef(steFor_arguments)
							))
						])
					)
				} else {
					// I.$methods[Symbol.iterator].value
					_ConditionalExpr(
						__NSSafe_PropertyAccessExpr(baseSTE, $methodsSTE),
						_PropertyAccessExpr(
							_IndexAccessExpr(
								__NSSafe_PropertyAccessExpr(baseSTE, $methodsSTE),
								typeAssistant.getMemberNameAsSymbol(targetName) // something like: Symbol.iterator
							),
							valueSTE
						),
						_FunExpr(false, #[
							_ReturnStmnt(_CallExpr(
								_PropertyAccessExpr(
									_IndexAccessExpr(
										__NSSafe_PropertyAccessExpr(baseSTE, $methodsSTE),
										typeAssistant.getMemberNameAsSymbol(targetName) // something like: Symbol.iterator
									),
									valueSTE,
									getSymbolTableEntryForMember(state.G.functionType, "apply", false, false, true)
								),
								_ThisLiteral,
								_IdentRef(steFor_arguments)
							))
						])
					)
				}
// ---------
			} else {
				if(!targetIsSymbol) {
					// I.t
					__NSSafe_PropertyAccessExpr(baseSTE, targetSTE)
				} else {
					// I[Symbol.iterator]
					_IndexAccessExpr(__NSSafe_IdentRef(baseSTE), typeAssistant.getMemberNameAsSymbol(targetName))
				}
			};
		} else {
			// (A) case DelegatingMember#delegationSuperClassSteps === 0:
			//
			// if target is non-static:
			//     Object.getOwnPropertyDescriptor(C.prototype, 'acc').get
			//
			// if target is static:
			//     Object.getOwnPropertyDescriptor(C, 'acc').get
			//
			// (B) same examples with a DelegatingMember#delegationSuperClassSteps of 2 instead of 0:
			//
			//     Object.getOwnPropertyDescriptor(Object.getPrototypeOf(Object.getPrototypeOf(C)).prototype, 'acc').get
			//     Object.getOwnPropertyDescriptor(Object.getPrototypeOf(Object.getPrototypeOf(C)), 'acc').get
			//
			// support for symbols is easier this time: simply replace the string literal 'acc' by targetSymbol
			//

			// create an expression that will evaluate to the constructor of the containing class of
			// the delegation target:
			val ctorOfClassOfTarget = createAccessToClassConstructor(baseSTE, delegator.delegationSuperClassSteps);

			// based on that constructor expression, now create an expression that evaluates to the member function of
			// the target member:
			val result = createAccessToMemberFunction(ctorOfClassOfTarget, false, delegator);

			return result;
		}
	}

	/**
	 * Creates an expression that will evaluate to the constructor of the class denoted by the given symbol table
	 * entry or one of its super classes (depending on argument <code>superClassSteps</code>).
	 * <p>
	 * For example, if <code>classSTE</code> denotes a class "C", then this will produce an expression like
	 * <table>
	 * <tr><td><code>C</code></td><td>for <code>superClassSteps</code> == 0</td></tr>
	 * <tr><td><code>Object.getPrototypeOf(C)</code></td><td>for <code>superClassSteps</code> == 1</td></tr>
	 * <tr><td><code>Object.getPrototypeOf(Object.getPrototypeOf(C))</code></td><td>for <code>superClassSteps</code> == 2</td></tr>
	 * </table>
	 */
	def public Expression createAccessToClassConstructor(SymbolTableEntry classSTE, int superClassSteps) {
		val objectType = state.G.objectType;
		val objectSTE = getSymbolTableEntryOriginal(objectType, true);
		var Expression result = __NSSafe_IdentRef(classSTE); // this is the "C" in the above examples
		// for each super-class-step wrap 'result' into "Object.getPrototypeOf(result)"
		if(superClassSteps>0) {
			val getPrototypeOfSTE = getSymbolTableEntryForMember(objectType, "getPrototypeOf", false, true, true);
			for(n : 0..<superClassSteps) {
				result = _CallExpr(
					_PropertyAccessExpr(objectSTE, getPrototypeOfSTE),
					result
				);
			}
		}
		return result;
	}

	/**
	 * Same as {@link #createAccessToMemberDefinition(Expression, boolean, N4MemberDeclaration) createAccessToMemberDefinition()},
	 * but will add a property access to the Javascript function representing the member, which is stored in the member
	 * definition (by appending ".get", ".set", or ".value" depending on the type of member).
	 * <p>
	 * Since fields do not have such a function this will throw an exception if given member is a field.
	 */
	def public Expression createAccessToMemberFunction(Expression protoOrCtorExpr, boolean exprIsProto, N4MemberDeclaration member) {
		if(member instanceof N4FieldDeclaration) {
			throw new IllegalArgumentException("no member function available for fields");
		}
		val accessToMemberDefinition = createAccessToMemberDefinition(protoOrCtorExpr, exprIsProto, member);
		// append ".get", ".set", or ".value" depending on member type
		val result = _PropertyAccessExpr(accessToMemberDefinition, getPropertyDescriptorValueProperty(member));
		return result;
	}

	/**
	 * Given an expression that will evaluate to a prototype or constructor, this method returns an expression that
	 * will evaluate to the member definition of a particular member.
	 *
	 * @param protoOrCtorExpr
	 *         an expression that is expected to evaluate to a prototype or a constructor.
	 * @param exprIsProto
	 *         tells whether argument <code>protoOrCtorExpr</code> will evaluate to a prototype or a constructor:
	 *         if true, it will evaluate to a prototype, if false it will evaluate to a constructor.
	 * @param member
	 *         the member.
	 */
	def public Expression createAccessToMemberDefinition(Expression protoOrCtorExpr, boolean exprIsProto, N4MemberDeclaration member) {
		val memberName = member.name;
		val memberIsSymbol = memberName!==null && memberName.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX);
		val memberSTE = findSymbolTableEntryForElement(member, true);
		val objectType = state.G.objectType;
		val objectSTE = getSymbolTableEntryOriginal(objectType, true);
		val getOwnPropertyDescriptorSTE = getSymbolTableEntryForMember(objectType, "getOwnPropertyDescriptor", false, true, true);
		// compute first argument to the #getOwnPropertyDescriptor() call:
		val isStatic = member.static;
		var arg0 = protoOrCtorExpr;
		if(!exprIsProto && !isStatic) {
			// got a constructor, but need a prototype -> append ".prototype"
			val prototypeSTE = getSymbolTableEntryForMember(objectType, "prototype", false, true, true);
			arg0 = _PropertyAccessExpr(arg0, prototypeSTE);
		} else if(exprIsProto && isStatic) {
			// got a prototype, but need a constructor -> append ".constructor"
			val constructorSTE = getSymbolTableEntryForMember(objectType, "constructor", false, false, true);
			arg0 = _PropertyAccessExpr(arg0, constructorSTE);
		};
		// compute second argument to the #getOwnPropertyDescriptor() call:
		val arg1 = if(!memberIsSymbol) {
			_StringLiteralForSTE(memberSTE)
		} else {
			typeAssistant.getMemberNameAsSymbol(memberName)
		};
		// create #getOwnPropertyDescriptor() call
		val result = _CallExpr(_PropertyAccessExpr(objectSTE, getOwnPropertyDescriptorSTE), arg0, arg1);
		return result;
	}

	// TODO the next two methods could be aligned more closely with previous methods OR previous methods should be used instead!
	/**
	 * Creates a property access to the immediate super class of the given <code>baseClassDecl</code>, i.e. in the
	 * non-static case:
	 * <pre>
	 * S.prototype
	 * </pre>
	 * and in the static case:
	 * <pre>
	 * S
	 * </pre>
	 * (with S being the direct super class).
	 */
	def public Expression createAccessToSuperClass(N4ClassDeclaration baseClassDecl, boolean isStatic) {
		val superClassSTE = typeAssistant.getSuperClassSTE(baseClassDecl);
		val prototypeSTE = getSymbolTableEntryForMember(state.G.objectType, "prototype", false, true, true);
		return if(!isStatic) {
			__NSSafe_PropertyAccessExpr(superClassSTE, prototypeSTE) // S.prototype
		} else {
			__NSSafe_IdentRef(superClassSTE) // S
		};
	}
	/**
	 * Like {@link DelegationAssistant#createAccessToSuperClass(N4ClassDeclaration,boolean)}, but follows the property
	 * chain until the correct class for the given member is reached (either the containing class or, if the member is
	 * consumed from an interface, the first super class that consumed the member).
	 */
	def public Expression createAccessToSuperClassBequestingMember(N4ClassDeclaration baseClassDecl, boolean isStatic, SymbolTableEntryOriginal memberSTE) {
		val tClassBase = state.info.getOriginalDefinedType(baseClassDecl);
		val member = memberSTE.originalTarget as TMember;
		val tClassTarget = getAncestorClassBequestingMember(tClassBase, member);
		val dist = DelegationAssistant.getDistanceToAncestorClass(tClassBase, tClassTarget);
		val __proto__STE =  steFor___proto__;
		// start with immediate super class
		var superAccess = createAccessToSuperClass(baseClassDecl, isStatic);
		// continue along the prototype chain until correct target class is reached
		for(i : 1..<dist) {
			superAccess = _PropertyAccessExpr(superAccess, __proto__STE);
		}
		return superAccess;
	}

	/**
	 * Returns the direct super type (i.e. immediate super class or directly implemented interface) of the given
	 * classifier through which the given classifier inherits the given inherited member. Fails fast in case of
	 * inconsistencies.
	 */
	def private ContainerType<?> getDirectSuperTypeBequestingMember(TClassifier classifier, TMember inheritedMember) {
		return state.memberCollector.directSuperTypeBequestingMember(classifier, inheritedMember);
	}

	/**
	 * Returns the ancestor class (i.e. direct or indirect super class) of the given classifier that either contains
	 * the given inherited member (if given member is contained in a class) or consumes the given member (if given
	 * member is contained in an interface).
	 * <p>
	 * For example:
	 * <pre>
	 * interface I {
	 *     m() {}
	 * }
	 * class A implements I {}
	 * class B extends A {}
	 * class C extends B {}
	 * </pre>
	 * With the above type declarations, for arguments <code>C</code> and <code>m</code> this method would return class
	 * <code>A</code>.
	 */
	def private TClass getAncestorClassBequestingMember(TClass classifier, TMember inheritedOrConsumedMember) {
		val containingType = inheritedOrConsumedMember.containingType;
		if(containingType===classifier) {
			return classifier
		} else if(containingType instanceof TInterface) {
			SuperInterfacesIterable.of(classifier).findClassImplementingInterface(containingType)
		} else if(containingType instanceof TClass) {
			containingType // note: we do not check if containingType is actually an ancestor of 'classifier'
		} else {
			throw new IllegalArgumentException("unsupported subtype of TClassifier: " + containingType.eClass.name);
		}
	}

	/**
	 * Returns distance to given ancestor or 0 if second argument is 'base' or not an ancestor or <code>null</code>.
	 */
	def private static int getDistanceToAncestorClass(TClass base, TClass ancestorClass) {
		if(ancestorClass===null || ancestorClass===base) {
			return 0;
		}
		val guard = new RecursionGuard<TClass>();
		var result = 0;
		var curr = base;
		while(curr!==null && curr!==ancestorClass) {
			if(guard.tryNext(curr)) {
				result++;
				curr = curr.superClassRef?.declaredType as TClass;
				// no need to call guard.done()
			}
		}
		return if(curr!==null) result else 0;
	}

	/**
	 * Returns symbol table entry for the property in a Javascript property descriptor that holds the actual value,
	 * i.e. the function expression implementing the member.
	 */
	def private SymbolTableEntry getPropertyDescriptorValueProperty(N4MemberDeclaration delegator) {
		switch(delegator) {
			N4GetterDeclaration:  steFor_get
			N4SetterDeclaration:  steFor_set
			N4MethodDeclaration:  steFor_value
		}
	}
}
