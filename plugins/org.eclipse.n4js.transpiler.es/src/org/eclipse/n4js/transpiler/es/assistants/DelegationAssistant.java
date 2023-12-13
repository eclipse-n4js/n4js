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
package org.eclipse.n4js.transpiler.es.assistants;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Block;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ExprStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Fpar;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IndexAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._LiteralOrComputedPropertyName;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4GetterDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4MethodDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4SetterDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ReturnStmnt;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteralForSTE;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ThisLiteral;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.functionType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.transpiler.TransformationAssistant;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.im.DelegatingGetterDeclaration;
import org.eclipse.n4js.transpiler.im.DelegatingMember;
import org.eclipse.n4js.transpiler.im.DelegatingMethodDeclaration;
import org.eclipse.n4js.transpiler.im.DelegatingSetterDeclaration;
import org.eclipse.n4js.transpiler.im.ImFactory;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.util.SuperInterfacesIterable;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.RecursionGuard;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * This assistant provides helper methods to create members that delegate to some other target member (see
 * {@link DelegationAssistant#createDelegatingMember(TClassifier, TMember) createDelegatingMember(TClassifier,
 * TMember)}) and to create the Javascript output code to actually implement these delegating members in the transpiler
 * output (see {@link DelegationAssistant#createOrdinaryMemberForDelegatingMember(DelegatingMember) }).
 * <p>
 * Usually inherited members in a classifier do not require any special code, because they will be accessed via the
 * native prototype chain mechanism of Javascript. However, there are special cases when some special code has to be
 * generated for an inherited member in order to properly access that inherited member, because it is not available via
 * the ordinary prototype chain.
 */
public class DelegationAssistant extends TransformationAssistant {

	@Inject
	private TypeAssistant typeAssistant;

	/**
	 * Creates a new delegating member intended to be inserted into classifier <code>origin</code> in order to delegate
	 * from <code>origin</code> to the given member <code>target</code>. The target member is assumed to be an inherited
	 * or consumed member of classifier <code>origin</code>, i.e. it is assumed to be located in one of the ancestor
	 * classes of <code>origin</code> or one of its directly or indirectly implemented interfaces (but not in origin
	 * itself!).
	 * <p>
	 * Throws exceptions in case of invalid arguments or an invalid internal getState(), see implementation for details.
	 */
	public DelegatingMember createDelegatingMember(TClassifier origin, TMember target) {
		if (target.getContainingType() == origin) {
			throw new IllegalArgumentException("no point in delegating to an owned member");
		}
		DelegatingMember result = null;
		if (target instanceof TGetter) {
			result = ImFactory.eINSTANCE.createDelegatingGetterDeclaration();
			((DelegatingGetterDeclaration) result).setDeclaredName(_LiteralOrComputedPropertyName(target.getName()));
		}
		if (target instanceof TSetter) {
			result = ImFactory.eINSTANCE.createDelegatingSetterDeclaration();
			((DelegatingSetterDeclaration) result).setDeclaredName(_LiteralOrComputedPropertyName(target.getName()));
		}
		if (target instanceof TMethod) {
			result = ImFactory.eINSTANCE.createDelegatingMethodDeclaration();
			((DelegatingMethodDeclaration) result).setDeclaredName(_LiteralOrComputedPropertyName(target.getName()));
		}
		if (target instanceof TField || result == null) {
			throw new IllegalArgumentException("delegation to fields not supported yet");
		}

		// set simple properties
		result.setDelegationTarget(getSymbolTableEntryOriginal(target, true));
		if (target.isStatic()) {
			result.getDeclaredModifiers().add(N4Modifier.STATIC);
		}
		// set delegationBaseType and delegationSuperClassSteps
		if (origin instanceof TInterface) {
			// we are in an interface and the target must also be in an interface
			if (!(target.eContainer() instanceof TInterface)) {
				throw new IllegalArgumentException("cannot delegate from an interface to member of a class");
			}
			ContainerType<?> tSuper = getDirectSuperTypeBequestingMember(origin, target);
			// we know the STE of tSuper must already exist, because it is a direct super type and must therefore be
			// referenced in the declaration of classifier origin
			result.setDelegationBaseType(getSymbolTableEntryOriginal(tSuper, true));
			result.setDelegationSuperClassSteps(0);
		} else if (origin instanceof TClass) {
			// we are in a class and the target may either be in a class or an interface
			TClass tAncestor = getAncestorClassBequestingMember((TClass) origin, target);
			if (tAncestor != origin) {
				// we are inheriting 'target' from one of our ancestor classes -> delegate to that ancestor class
				// (note: this includes the case the 'target' is contained in an interface and one of our ancestor
				// classes implements that interface)
				TClass tSuper = (TClass) ((TClass) origin).getSuperClassRef().getDeclaredType();
				result.setDelegationBaseType(getSymbolTableEntryOriginal(tSuper, true));
				result.setDelegationSuperClassSteps(getDistanceToAncestorClass((TClass) origin, tAncestor) - 1);
			} else if (tAncestor != null) {
				// we are consuming 'target' from one of our directly implemented interfaces or its extended interfaces
				// (similar as case "origin instanceof TInterface" above)
				ContainerType<?> tSuper = getDirectSuperTypeBequestingMember(origin, target);
				result.setDelegationBaseType(getSymbolTableEntryOriginal(tSuper, true));
				result.setDelegationSuperClassSteps(0);
			} else {
				throw new IllegalStateException("cannot find target (probably not an inherited member)");
			}
		} else {
			throw new IllegalArgumentException("unsupported subtype of TClassifier: " + origin.eClass().getName());
		}
		// set some properties to let this delegating member behave more like an ordinary member of the same type
		result.setDelegationTargetIsAbstract(target.isAbstract());
		if (!target.isAbstract()) {
			((FunctionOrFieldAccessor) result).setBody(_Block());
		}
		return result;
	}

	/**
	 * Convenience method for replacing each delegating member in the given declaration by an ordinary member created
	 * with method {@link #createOrdinaryMemberForDelegatingMember(DelegatingMember)}. Will modify the given classifier
	 * declaration.
	 */
	public void replaceDelegatingMembersByOrdinaryMembers(N4ClassifierDeclaration classifierDecl) {
		for (N4MemberDeclaration currMember : Lists.newArrayList(classifierDecl.getOwnedMembersRaw())) {
			if (currMember instanceof DelegatingMember) {
				N4MemberDeclaration resolvedDelegatingMember = createOrdinaryMemberForDelegatingMember(
						(DelegatingMember) currMember);
				replace(currMember, resolvedDelegatingMember);
			}
		}
	}

	/** Creates a {@link N4MemberDeclaration} for the given delegator */
	public N4MemberDeclaration createOrdinaryMemberForDelegatingMember(DelegatingMember delegator) {
		String targetNameStr = delegator.getDelegationTarget().getName();
		LiteralOrComputedPropertyName targetName;
		if (targetNameStr != null && targetNameStr.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX)) {
			targetName = _LiteralOrComputedPropertyName(typeAssistant.getMemberNameAsSymbol(targetNameStr),
					targetNameStr);
		} else {
			targetName = _LiteralOrComputedPropertyName(targetNameStr);
		}

		Block body = createBodyForDelegatingMember(delegator);
		if (delegator instanceof DelegatingGetterDeclaration) {
			return _N4GetterDecl(targetName, body);
		}
		if (delegator instanceof DelegatingSetterDeclaration) {
			return _N4SetterDecl(targetName, _Fpar("value"), body);
		}
		if (delegator instanceof DelegatingMethodDeclaration) {
			return _N4MethodDecl(targetName, body);
		}
		return null;
	}

	private Block createBodyForDelegatingMember(DelegatingMember delegator) {
		SymbolTableEntryOriginal baseSTE = delegator.getDelegationBaseType();
		boolean baseIsInterface = baseSTE == null ? false : baseSTE.getOriginalTarget() instanceof TInterface;

		Expression targetAccess;
		if (baseIsInterface) {
			boolean targetIsStatic = delegator.isStatic();
			Expression objOfInterfaceOfTargetExpr = createAccessToInterfaceObject(baseSTE, targetIsStatic);
			targetAccess = createAccessToMemberFunction(objOfInterfaceOfTargetExpr, !targetIsStatic, delegator);
		} else {
			Expression ctorOfClassOfTarget = createAccessToClassConstructor(baseSTE,
					delegator.getDelegationSuperClassSteps());

			// based on that constructor expression, now create an expression that evaluates to the member function of
			// the target member:
			targetAccess = createAccessToMemberFunction(ctorOfClassOfTarget, false, delegator);
		}

		ParameterizedCallExpression callExpr = _CallExpr(
				_PropertyAccessExpr(
						targetAccess,
						getSymbolTableEntryForMember(functionType(getState().G), "apply", false, false, true)),
				_ThisLiteral(),
				_IdentRef(steFor_arguments()));

		if (delegator instanceof DelegatingSetterDeclaration) {
			return _Block(
					_ExprStmnt(callExpr));
		} else {
			return _Block(
					_ReturnStmnt(callExpr));
		}
	}

	/**
	 * Creates an expression that will evaluate to the constructor of the class denoted by the given symbol table entry
	 * or one of its super classes (depending on argument <code>superClassSteps</code>).
	 * <p>
	 * For example, if <code>classSTE</code> denotes a class "C", then this will produce an expression like
	 * <table>
	 * <tr>
	 * <td><code>C</code></td>
	 * <td>for <code>superClassSteps</code> == 0</td>
	 * </tr>
	 * <tr>
	 * <td><code>Object.getPrototypeOf(C)</code></td>
	 * <td>for <code>superClassSteps</code> == 1</td>
	 * </tr>
	 * <tr>
	 * <td><code>Object.getPrototypeOf(Object.getPrototypeOf(C))</code></td>
	 * <td>for <code>superClassSteps</code> == 2</td>
	 * </tr>
	 * </table>
	 */
	private Expression createAccessToClassConstructor(SymbolTableEntry classSTE, int superClassSteps) {
		TClassifier objectType = objectType(getState().G);
		SymbolTableEntryOriginal objectSTE = getSymbolTableEntryOriginal(objectType, true);
		Expression result = __NSSafe_IdentRef(classSTE); // this is the "C" in the above examples
		// for each super-class-step wrap 'result' into "Object.getPrototypeOf(result)"
		if (superClassSteps > 0) {
			SymbolTableEntryOriginal getPrototypeOfSTE = getSymbolTableEntryForMember(objectType, "getPrototypeOf",
					false, true, true);
			for (int n = 0; n < superClassSteps; n++) {
				result = _CallExpr(
						_PropertyAccessExpr(objectSTE, getPrototypeOfSTE),
						result);
			}
		}
		return result;
	}

	private Expression createAccessToInterfaceObject(SymbolTableEntry ifcSTE, boolean targetIsStatic) {
		Expression result = __NSSafe_IdentRef(ifcSTE);
		if (!targetIsStatic) {
			result = _PropertyAccessExpr(result, steFor_$defaultMembers());
		}
		return result;
	}

	/**
	 * Same as {@link #createAccessToMemberDescriptor(Expression, boolean, N4MemberDeclaration)}, but will add a
	 * property access to the Javascript function representing the member, which is stored in the member definition (by
	 * appending ".get", ".set", or ".value" depending on the type of member).
	 * <p>
	 * Since fields do not have such a function this will throw an exception if given member is a field.
	 */
	private Expression createAccessToMemberFunction(Expression protoOrCtorExpr, boolean exprIsProto,
			N4MemberDeclaration member) {
		if (member instanceof N4FieldDeclaration) {
			throw new IllegalArgumentException("no member function available for fields");
		}
		if (member instanceof N4MethodDeclaration) {
			// for methods we can use a simple property access instead of Object.getOwnPropertyDescriptor()
			String memberName = member.getName();
			boolean memberIsSymbol = memberName != null
					&& memberName.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX);
			SymbolTableEntry memberSTE = findSymbolTableEntryForElement(member, true);
			return (!memberIsSymbol) ? _PropertyAccessExpr(protoOrCtorExpr, memberSTE)
					: _IndexAccessExpr(protoOrCtorExpr, typeAssistant.getMemberNameAsSymbol(memberName));
		}
		// for other members (i.e. getters and setters) we need to retrieve the property descriptor:
		Expression accessToMemberDefinition = createAccessToMemberDescriptor(protoOrCtorExpr, exprIsProto, member);
		// append ".get", ".set", or ".value" depending on member type
		ParameterizedPropertyAccessExpression_IM result = _PropertyAccessExpr(accessToMemberDefinition,
				getPropertyDescriptorValueProperty(member));
		return result;
	}

	/**
	 * Given an expression that will evaluate to a prototype or constructor, this method returns an expression that will
	 * evaluate to the property descriptor of a particular member.
	 *
	 * @param protoOrCtorExpr
	 *            an expression that is expected to evaluate to a prototype or a constructor.
	 * @param exprIsProto
	 *            tells whether argument <code>protoOrCtorExpr</code> will evaluate to a prototype or a constructor: if
	 *            true, it will evaluate to a prototype, if false it will evaluate to a constructor.
	 * @param member
	 *            the member.
	 */
	private Expression createAccessToMemberDescriptor(Expression protoOrCtorExpr, boolean exprIsProto,
			N4MemberDeclaration member) {
		String memberName = member.getName();
		boolean memberIsSymbol = memberName != null
				&& memberName.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX);
		SymbolTableEntry memberSTE = findSymbolTableEntryForElement(member, true);
		TClassifier objectType = objectType(getState().G);
		SymbolTableEntryOriginal objectSTE = getSymbolTableEntryOriginal(objectType, true);
		SymbolTableEntryOriginal getOwnPropertyDescriptorSTE = getSymbolTableEntryForMember(objectType,
				"getOwnPropertyDescriptor", false, true, true);
		// compute first argument to the #getOwnPropertyDescriptor() call:
		boolean isStatic = member.isStatic();
		var arg0 = protoOrCtorExpr;
		if (!exprIsProto && !isStatic) {
			// got a constructor, but need a prototype -> append ".prototype"
			SymbolTableEntryOriginal prototypeSTE = getSymbolTableEntryForMember(objectType, "prototype", false, true,
					true);
			arg0 = _PropertyAccessExpr(arg0, prototypeSTE);
		} else if (exprIsProto && isStatic) {
			// got a prototype, but need a constructor -> append ".constructor"
			SymbolTableEntryOriginal constructorSTE = getSymbolTableEntryForMember(objectType, "constructor", false,
					false, true);
			arg0 = _PropertyAccessExpr(arg0, constructorSTE);
		}

		// compute second argument to the #getOwnPropertyDescriptor() call:
		Expression arg1 = (!memberIsSymbol) ? _StringLiteralForSTE(memberSTE)
				: typeAssistant.getMemberNameAsSymbol(memberName);
		// create #getOwnPropertyDescriptor() call
		ParameterizedCallExpression result = _CallExpr(_PropertyAccessExpr(objectSTE, getOwnPropertyDescriptorSTE),
				arg0, arg1);
		return result;
	}

	/**
	 * Returns the direct super type (i.e. immediate super class or directly implemented interface) of the given
	 * classifier through which the given classifier inherits the given inherited member. Fails fast in case of
	 * inconsistencies.
	 */
	private ContainerType<?> getDirectSuperTypeBequestingMember(TClassifier classifier, TMember inheritedMember) {
		return getState().memberCollector.directSuperTypeBequestingMember(classifier, inheritedMember);
	}

	/**
	 * Returns the ancestor class (i.e. direct or indirect super class) of the given classifier that either contains the
	 * given inherited member (if given member is contained in a class) or consumes the given member (if given member is
	 * contained in an interface).
	 * <p>
	 * For example:
	 *
	 * <pre>
	 * interface I {
	 * 	m(){}
	 * }
	 * class A implements I {
	 * }
	 * class B extends A {
	 * }
	 * class C extends B {
	 * }
	 * </pre>
	 *
	 * With the above type declarations, for arguments <code>C</code> and <code>m</code> this method would return class
	 * <code>A</code>.
	 */
	private TClass getAncestorClassBequestingMember(TClass classifier, TMember inheritedOrConsumedMember) {
		ContainerType<?> containingType = inheritedOrConsumedMember.getContainingType();
		if (containingType == classifier) {
			return classifier;
		} else if (containingType instanceof TInterface) {
			return SuperInterfacesIterable.of(classifier).findClassImplementingInterface((TInterface) containingType);
		} else if (containingType instanceof TClass) {
			return (TClass) containingType;// note: we do not check if containingType is actually an ancestor of
											// 'classifier'
		} else {
			throw new IllegalArgumentException(
					"unsupported subtype of TClassifier: " + containingType.eClass().getName());
		}
	}

	/**
	 * Returns distance to given ancestor or 0 if second argument is 'base' or not an ancestor or <code>null</code>.
	 */
	private static int getDistanceToAncestorClass(TClass base, TClass ancestorClass) {
		if (ancestorClass == null || ancestorClass == base) {
			return 0;
		}
		RecursionGuard<TClass> guard = new RecursionGuard<>();
		int result = 0;
		TClass curr = base;
		while (curr != null && curr != ancestorClass) {
			if (guard.tryNext(curr)) {
				result++;
				curr = curr.getSuperClassRef() == null ? null : (TClass) curr.getSuperClassRef().getDeclaredType();
				// no need to call guard.done()
			}
		}
		return (curr != null) ? result : 0;
	}

	/**
	 * Returns symbol table entry for the property in a Javascript property descriptor that holds the actual value, i.e.
	 * the function expression implementing the member.
	 */
	private SymbolTableEntry getPropertyDescriptorValueProperty(N4MemberDeclaration delegator) {
		if (delegator instanceof N4GetterDeclaration) {
			return steFor_get();
		}
		if (delegator instanceof N4SetterDeclaration) {
			return steFor_set();
		}
		if (delegator instanceof N4MethodDeclaration) {
			return steFor_value();
		}
		return null;
	}
}
