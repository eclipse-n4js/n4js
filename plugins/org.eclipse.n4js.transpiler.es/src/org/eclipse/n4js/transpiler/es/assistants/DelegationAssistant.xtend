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

import com.google.common.collect.Lists
import com.google.inject.Inject
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.im.DelegatingGetterDeclaration
import org.eclipse.n4js.transpiler.im.DelegatingMember
import org.eclipse.n4js.transpiler.im.DelegatingMethodDeclaration
import org.eclipse.n4js.transpiler.im.DelegatingSetterDeclaration
import org.eclipse.n4js.transpiler.im.ImFactory
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
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
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.RecursionGuard

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

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
	 * Convenience method for replacing each delegating member in the given declaration by an ordinary member
	 * created with method {@link #createOrdinaryMemberForDelegatingMember(DelegatingMember)}. Will modify the
	 * given classifier declaration.
	 */
	def public void replaceDelegatingMembersByOrdinaryMembers(N4ClassifierDeclaration classifierDecl) {
		for (currMember : Lists.newArrayList(classifierDecl.ownedMembersRaw)) {
			if (currMember instanceof DelegatingMember) {
				val resolvedDelegatingMember = createOrdinaryMemberForDelegatingMember(currMember);
				replace(currMember, resolvedDelegatingMember);
			}
		}
	}

	def public N4MemberDeclaration createOrdinaryMemberForDelegatingMember(DelegatingMember delegator) {
		val targetNameStr = delegator.delegationTarget.name;
		val targetName = if (targetNameStr!==null && targetNameStr.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX)) {
			_LiteralOrComputedPropertyName(typeAssistant.getMemberNameAsSymbol(targetNameStr), targetNameStr)
		} else {
			_LiteralOrComputedPropertyName(targetNameStr)
		};

		val body = createBodyForDelegatingMember(delegator);

		return switch(delegator) {
			DelegatingGetterDeclaration:
				_N4GetterDecl(targetName, body)
			DelegatingSetterDeclaration:
				_N4SetterDecl(targetName, _Fpar("value"), body)
			DelegatingMethodDeclaration:
				_N4MethodDecl(targetName, body)
		};
	}

	def private Block createBodyForDelegatingMember(DelegatingMember delegator) {
		val baseSTE = delegator.delegationBaseType;
		val baseIsInterface = baseSTE?.originalTarget instanceof TInterface;

		val targetAccess = if(baseIsInterface) {
			val targetIsStatic = delegator.static;
			val objOfInterfaceOfTargetExpr = createAccessToInterfaceObject(baseSTE, targetIsStatic);
			createAccessToMemberFunction(objOfInterfaceOfTargetExpr, !targetIsStatic, delegator);
		} else {
			val ctorOfClassOfTarget = createAccessToClassConstructor(baseSTE, delegator.delegationSuperClassSteps);

			// based on that constructor expression, now create an expression that evaluates to the member function of
			// the target member:
			createAccessToMemberFunction(ctorOfClassOfTarget, false, delegator)
		};

		return _Block(
			_ReturnStmnt(_CallExpr(
				_PropertyAccessExpr(
					targetAccess,
					getSymbolTableEntryForMember(state.G.functionType, "apply", false, false, true)
				),
				_ThisLiteral,
				_IdentRef(steFor_arguments)
			))
		);
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
	def private Expression createAccessToClassConstructor(SymbolTableEntry classSTE, int superClassSteps) {
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

	def private Expression createAccessToInterfaceObject(SymbolTableEntry ifcSTE, boolean targetIsStatic) {
		var Expression result = __NSSafe_IdentRef(ifcSTE);
		if (!targetIsStatic) {
			result = _PropertyAccessExpr(result, steFor_$defaultMembers);
		}
		return result;
	}

	/**
	 * Same as {@link #createAccessToMemberDefinition(Expression, boolean, N4MemberDeclaration)},
	 * but will add a property access to the Javascript function representing the member, which is stored in the member
	 * definition (by appending ".get", ".set", or ".value" depending on the type of member).
	 * <p>
	 * Since fields do not have such a function this will throw an exception if given member is a field.
	 */
	def private Expression createAccessToMemberFunction(Expression protoOrCtorExpr, boolean exprIsProto, N4MemberDeclaration member) {
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
	def private Expression createAccessToMemberDefinition(Expression protoOrCtorExpr, boolean exprIsProto, N4MemberDeclaration member) {
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
