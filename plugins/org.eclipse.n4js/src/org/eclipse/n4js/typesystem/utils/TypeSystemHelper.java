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
package org.eclipse.n4js.typesystem.utils;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRefDynamic;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.functionTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.generatorType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getContextResource;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getPredefinedTypes;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isAnyDynamic;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.nullType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.setThisBinding;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.structuralFunctionTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.voidType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedAccess;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.util.TypeExtensions;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.TypeConstraint;
import org.eclipse.n4js.typesystem.utils.StructuralTypingComputer.StructTypingInfo;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.StructuralTypesHelper;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Utility methods used in the XSemantics type system. Must be injected.
 *
 * <p>
 * Simple implementations are directly contained here. Complex operations such as join or meet are to be implemented in
 * strategy classes. For those operations, this class acts as a facade.
 * </p>
 *
 * <p>
 * EObject Reference Note: All methods prefer using {@link TypeUtils#copyIfContained(EObject)} instead of
 * {@link TypeUtils#copy(EObject)}. So, clients should follow this pattern as well.
 * </p>
 *
 * Bibliography: <a name="Pierce02a">[Pierce02a]</a> B. C. Pierce: Types and Programming Languages. The MIT Press, 1,
 * 2002.
 */
@Singleton
@SuppressWarnings("javadoc")
public class TypeSystemHelper {

	@Inject
	private N4JSTypeSystem ts;

	// *****************************************************************************************************
	// forwarding of utility methods implemented in strategy classes
	// *****************************************************************************************************

	@Inject
	private GenericsComputer genericsComputer;

	@Inject
	private SimplifyComputer simplifyComputer;
	@Inject
	private JoinComputer joinComputer;
	@Inject
	private MeetComputer meetComputer;

	@Inject
	private SubtypeComputer subtypeComputer;
	@Inject
	private ExpectedTypeComputer expectedTypeCompuer;
	@Inject
	private StructuralTypingComputer structuralTypingComputer;
	@Inject
	private ThisTypeComputer thisTypeComputer;
	@Inject
	private IterableComputer iterableComputer;
	@Inject
	private TypeAliasComputer typeAliasComputer;

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Inject
	private StructuralTypesHelper structuralTypesHelper;

	public StructuralTypesHelper getStructuralTypesHelper() {
		return structuralTypesHelper;
	}

	public void addSubstitutions(RuleEnvironment G, TypeRef typeRef) {
		genericsComputer.addSubstitutions(G, typeRef);
	}

	public void addSubstitutions(RuleEnvironment G, ParameterizedCallExpression callExpr,
			FunctionTypeExprOrRef targetTypeRef, boolean defaultsTypeArgsToAny) {
		genericsComputer.addSubstitutions(G, callExpr, targetTypeRef, defaultsTypeArgsToAny);
	}

	public void addSubstitutions(RuleEnvironment G, NewExpression newExpr, TMethod constructSignature) {
		genericsComputer.addSubstitutions(G, newExpr, constructSignature);
	}

	public void addSubstitutions(RuleEnvironment G, ParameterizedPropertyAccessExpression accessExpr) {
		genericsComputer.addSubstitutions(G, accessExpr);
	}

	public TypeRef substTypeVariablesInStructuralMembers(RuleEnvironment G, StructuralTypeRef typeRef) {
		return genericsComputer.substTypeVariablesInStructuralMembers(G, typeRef);
	}

	public void storePostponedSubstitutionsIn(RuleEnvironment G, StructuralTypeRef typeRef) {
		genericsComputer.storePostponedSubstitutionsIn(G, typeRef);
	}

	public void restorePostponedSubstitutionsFrom(RuleEnvironment G, StructuralTypeRef typeRef) {
		genericsComputer.restorePostponedSubstitutionsFrom(G, typeRef);
	}

	/**
	 * See
	 * {@link GenericsComputer#checkTypeArgumentCompatibility(RuleEnvironment, TypeArgument, TypeArgument, Optional, boolean)}.
	 */
	public Result checkTypeArgumentCompatibility(RuleEnvironment G, TypeArgument leftArg, TypeArgument rightArg,
			Optional<Variance> varianceOpt, boolean useFancyErrMsg) {

		return genericsComputer.checkTypeArgumentCompatibility(G, leftArg, rightArg, varianceOpt, useFancyErrMsg);
	}

	/**
	 * See
	 * {@link GenericsComputer#reduceTypeArgumentCompatibilityCheck(RuleEnvironment, TypeArgument, TypeArgument, Optional, boolean)}.
	 */
	public List<TypeConstraint> reduceTypeArgumentCompatibilityCheck(RuleEnvironment G, TypeArgument leftArg,
			TypeArgument rightArg,
			Optional<Variance> varianceOpt, boolean useFancyConstraints) {

		return genericsComputer.reduceTypeArgumentCompatibilityCheck(G, leftArg, rightArg, varianceOpt,
				useFancyConstraints);
	}

	public TypeRef createUnionType(RuleEnvironment G, TypeRef... elements) {
		return simplifyComputer.createUnionType(G, elements);
	}

	public TypeRef createIntersectionType(RuleEnvironment G, TypeRef... elements) {
		return simplifyComputer.createIntersectionType(G, elements);
	}

	public <T extends ComposedTypeRef> TypeRef simplify(RuleEnvironment G, T composedType) {
		return simplify(G, composedType, true);
	}

	public <T extends ComposedTypeRef> TypeRef simplify(RuleEnvironment G, T composedType, boolean checkSubtypes) {
		return simplifyComputer.simplify(G, composedType, checkSubtypes);
	}

	/**
	 * Convenience method calling {@link #join(RuleEnvironment, Iterable)} with type references inside an array.
	 */
	public TypeRef join(RuleEnvironment G, TypeRef... typeRefs) {
		return joinComputer.join(G, Arrays.asList(typeRefs));
	}

	/**
	 * Returns the join, sometimes called least common super type (LCST), of the given types.
	 *
	 * @see JoinComputer#join(RuleEnvironment, Iterable)
	 */
	public TypeRef join(RuleEnvironment G, Iterable<? extends TypeRef> typeRefsToJoin) {
		return joinComputer.join(G, typeRefsToJoin);
	}

	/**
	 * Convenience method calling {@link #meet(RuleEnvironment, Iterable)} with type references inside an array.
	 */
	public TypeRef meet(RuleEnvironment G, TypeRef... typeRefs) {
		return meetComputer.meet(G, Arrays.asList(typeRefs));
	}

	/**
	 * Returns the meet (first common sub type) of the given types
	 *
	 * @see MeetComputer#meet(RuleEnvironment, Iterable)
	 */
	public TypeRef meet(RuleEnvironment G, Iterable<? extends TypeRef> typeRefs) {
		return meetComputer.meet(G, typeRefs);
	}

	public boolean isSubtypeFunction(RuleEnvironment G, FunctionTypeExprOrRef left, FunctionTypeExprOrRef right) {
		return subtypeComputer.isSubtypeFunction(G, left, right);
	}

	public Result checkSameDeclaredTypes(RuleEnvironment G, TypeRef left, TypeRef right, Type rightDeclType) {
		return subtypeComputer.checkSameDeclaredTypes(G, left, right, rightDeclType);
	}

	public Result checkDeclaredSubtypes(RuleEnvironment G, ParameterizedTypeRef left, TypeRef right,
			Type rightDeclType) {
		return subtypeComputer.checkDeclaredSubtypes(G, left, right, rightDeclType);
	}

	public StructuralTypingResult isStructuralSubtype(RuleEnvironment G, TypeRef left, TypeRef right) {
		return structuralTypingComputer.isStructuralSubtype(G, left, right);
	}

	public List<TypeConstraint> reduceMembers(RuleEnvironment G, TypeRef leftTypeRef, TMember left, TMember right,
			StructTypingInfo info) {
		return structuralTypingComputer.reduceMembers(G, leftTypeRef, left, right, info);
	}

	/** @see ExpectedTypeComputer#getExpectedTypeOfReturnValueExpression(RuleEnvironment,Expression) */
	public TypeRef getExpectedTypeOfReturnValueExpression(RuleEnvironment G, Expression returnValueExpr) {
		return expectedTypeCompuer.getExpectedTypeOfReturnValueExpression(G, returnValueExpr);
	}

	/** @see ExpectedTypeComputer#getExpectedTypeOfYieldValueExpression(RuleEnvironment,YieldExpression,TypeRef) */
	public TypeRef getExpectedTypeOfYieldValueExpression(RuleEnvironment G, YieldExpression yieldExpr,
			TypeRef exprTypeRef) {
		return expectedTypeCompuer.getExpectedTypeOfYieldValueExpression(G, yieldExpr, exprTypeRef);
	}

	/** @see ExpectedTypeComputer#getExpectedTypeOfYieldValueExpression(RuleEnvironment,YieldExpression,TypeRef) */
	public TypeRef getExpectedTypeOfFunctionOrFieldAccessor(RuleEnvironment G, FunctionOrFieldAccessor fofa) {
		return expectedTypeCompuer.getExpectedTypeOfFunctionOrFieldAccessor(G, fofa);
	}

	/** @see ThisTypeComputer#getThisTypeAtLocation(RuleEnvironment,EObject) */
	public TypeRef getThisTypeAtLocation(RuleEnvironment G, EObject location) {
		return thisTypeComputer.getThisTypeAtLocation(G, location);
	}

	/** @see IterableComputer#extractIterableElementTypes(RuleEnvironment, TypeRef) */
	public List<TypeRef> extractIterableElementTypes(RuleEnvironment G, TypeRef typeRef) {
		return iterableComputer.extractIterableElementTypes(G, typeRef);
	}

	/** @see IterableComputer#extractIterableElementType(RuleEnvironment, TypeRef, boolean) */
	public TypeRef extractIterableElementType(RuleEnvironment G, TypeRef typeRef, boolean includeAsyncIterable) {
		return iterableComputer.extractIterableElementType(G, typeRef, includeAsyncIterable);
	}

	/** @see TypeAliasComputer#resolveTypeAliasFlat(RuleEnvironment, TypeRef) */
	public TypeRef resolveTypeAliasFlat(RuleEnvironment G, TypeRef typeRef) {
		return typeAliasComputer.resolveTypeAliasFlat(G, typeRef);
	}

	/** @see TypeAliasComputer#resolveTypeAliases(RuleEnvironment, TypeRef) */
	public TypeRef resolveTypeAliases(RuleEnvironment G, TypeRef typeRef) {
		return typeAliasComputer.resolveTypeAliases(G, typeRef);
	}

	/** @see TypeAliasComputer#resolveTypeAliases(RuleEnvironment, TypeArgument) */
	public TypeArgument resolveTypeAliases(RuleEnvironment G, TypeArgument typeArg) {
		return typeAliasComputer.resolveTypeAliases(G, typeArg);
	}

	// *****************************************************************************************************
	// small utility methods that do not have their own strategy class
	// *****************************************************************************************************

	public boolean allEqualType(RuleEnvironment G, TypeRef... typeRefs) {
		if (typeRefs != null) {
			int len = typeRefs.length;
			if (len >= 2) {
				TypeRef firstRef = typeRefs[0];
				for (var i = 1; i < len; i++) {
					if (!ts.equaltypeSucceeded(G, firstRef, typeRefs[i]))
						return false;
				}
			}
		}
		return true;
	}

	public TypeRef sanitizeTypeOfVariableFieldPropertyParameter(RuleEnvironment G, TypeArgument typeRaw,
			boolean resolveLiteralTypes) {
		if (typeRaw == null || typeRaw instanceof UnknownTypeRef) {
			return anyTypeRef(G);
		}
		// take upper bound to get rid of wildcards, etc. (if any)
		TypeRef typeUB = (resolveLiteralTypes)
				? // ... and also replace literal types by their base type
				ts.upperBoundWithReopenAndResolveLiteralTypes(G, typeRaw)
				: ts.upperBoundWithReopen(G, typeRaw);
		// replace silly types
		Type declType = typeUB.getDeclaredType();
		if (declType == undefinedType(G) || declType == nullType(G) || declType == voidType(G)) {
			return anyTypeRef(G);
		}
		return typeUB;
	}

	public TreeIterator<EObject> returnStatements(FunctionDefinition definition) {
		return EcoreUtilN4.getAllContentsFiltered(definition,
				it -> (it instanceof ReturnStatement && ((ReturnStatement) it).getExpression() != null));
	}

	/**
	 * If possible, a dynamic version of the given type ref is returned. If the type ref is already dynamic, it is
	 * returned. This is used for making all type refs dynamic in JavaScript mode.
	 */
	public TypeRef makeDynamic(TypeRef typeRef) {
		if (!typeRef.isDynamic()) {
			if (typeRef instanceof ParameterizedTypeRef) {
				ParameterizedTypeRef dyn = TypeUtils.copyIfContained((ParameterizedTypeRef) typeRef);
				dyn.setDynamic(true);
				return dyn;
			}
		}
		return typeRef;
	}

	/**
	 * Returns the explicitly declared this type, or <code>null</code>.
	 *
	 * @param type
	 *            either subtype of TFunction, of FieldAccessor, or of FunctionTypeExprOrRef can have a declared this
	 *            type ("@This")
	 * @return declaredThisType if any, null in other cases.
	 */
	public static TypeRef getDeclaredThisType(IdentifiableElement type) {
		if (type instanceof TFunction) {
			return ((TFunction) type).getDeclaredThisType();
		}
		if (type instanceof TGetter) {
			return ((TGetter) type).getDeclaredThisType();
		}
		if (type instanceof TSetter) {
			return ((TSetter) type).getDeclaredThisType();
		}
		if (type instanceof FunctionTypeExprOrRef) {
			return ((FunctionTypeExprOrRef) type).getDeclaredThisType();
		}
		return null;
	}

	/**
	 * Binds and substitutes the given {@link ThisTypeRef this type reference} after wrapping the given rule
	 * environment.
	 *
	 * <p>
	 * For instance after passing a {@code ~~this} type reference into a method in the context of container
	 * {@code class A}, the type reference argument will be bound to {@code this[A]} and finally will be substituted
	 * with {@code ~~this[A]} type reference. That will be the return value of the method.
	 *
	 * @param G
	 *            the rule environment that will be wrapped for the operation.
	 * @param location
	 *            location within the AST for which to create a BoundThisTypeRef. Same as the argument 'location' of
	 *            judgment 'thisTypeRef' in Xsemantics.
	 * @param typeRef
	 *            type reference to substitute; this can either be an unbound ThisTypeRef or any other kind of TypeRef
	 *            that contains one or more unbound ThisTypeRefs. Need not be contained in the AST (as usual for type
	 *            references).
	 */
	public TypeRef bindAndSubstituteThisTypeRef(RuleEnvironment G, EObject location, TypeRef typeRef) {
		// create a BoundThisTypeRef for given location
		TypeRef boundThisTypeRef = getThisTypeAtLocation(G, location);
		RuleEnvironment localG = wrap(G);
		setThisBinding(localG, boundThisTypeRef);
		// substitute all unbound ThisTypeRefs with the newly created BoundThisTypeRef
		return ts.substTypeVariables(localG, typeRef);
	}

	public static class Callable {
		/**
		 * The actual type reference that contributed function/method. Used for error reporting.
		 */
		private final TypeRef callableTypeRef;

		/**
		 * The signature of the function/method being invoked. Used for argument checking and to obtain the type of the
		 * return value. If absent, the invocation is possible, but no information about the parameters or the return
		 * value is available (e.g. when invoking values of built-in type {@code Function}).
		 */
		private final Optional<? extends FunctionTypeExprOrRef> signatureTypeRef;

		/**
		 * Tells whether the function/method being invoked is {@link TypeRef#isDynamic() dynamic}.
		 */
		private final boolean dynamic;

		public Callable(final TypeRef callableTypeRef, final Optional<? extends FunctionTypeExprOrRef> signatureTypeRef,
				final boolean dynamic) {
			super();
			this.callableTypeRef = callableTypeRef;
			this.signatureTypeRef = signatureTypeRef;
			this.dynamic = dynamic;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.callableTypeRef == null) ? 0 : this.callableTypeRef.hashCode());
			result = prime * result + ((this.signatureTypeRef == null) ? 0 : this.signatureTypeRef.hashCode());
			return prime * result + (this.dynamic ? 1231 : 1237);
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TypeSystemHelper.Callable other = (TypeSystemHelper.Callable) obj;
			if (this.callableTypeRef == null) {
				if (other.callableTypeRef != null)
					return false;
			} else if (!this.callableTypeRef.equals(other.callableTypeRef))
				return false;
			if (this.signatureTypeRef == null) {
				if (other.signatureTypeRef != null)
					return false;
			} else if (!this.signatureTypeRef.equals(other.signatureTypeRef))
				return false;
			if (other.dynamic != this.dynamic)
				return false;
			return true;
		}

		@Override
		public String toString() {
			ToStringBuilder b = new ToStringBuilder(this);
			b.add("callableTypeRef", this.callableTypeRef);
			b.add("signatureTypeRef", this.signatureTypeRef);
			b.add("dynamic", this.dynamic);
			return b.toString();
		}

		public TypeRef getCallableTypeRef() {
			return this.callableTypeRef;
		}

		public Optional<? extends FunctionTypeExprOrRef> getSignatureTypeRef() {
			return this.signatureTypeRef;
		}

		public boolean isDynamic() {
			return this.dynamic;
		}
	}

	@Data
	public static class Newable {
		/**
		 * The actual type reference that contributed constructor or construct signature. Used for error reporting.
		 */
		private final TypeRef newableTypeRef;

		/**
		 * The constructor or construct signature. Used for argument checking. Can be null.
		 */
		private final TMethod ctorOrConstructSig;

		/**
		 * The type of the newly created instance.
		 */
		private final TypeRef instanceTypeRef;

		public Newable(final TypeRef newableTypeRef, final TMethod ctorOrConstructSig, final TypeRef instanceTypeRef) {
			super();
			this.newableTypeRef = newableTypeRef;
			this.ctorOrConstructSig = ctorOrConstructSig;
			this.instanceTypeRef = instanceTypeRef;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.newableTypeRef == null) ? 0 : this.newableTypeRef.hashCode());
			result = prime * result + ((this.ctorOrConstructSig == null) ? 0 : this.ctorOrConstructSig.hashCode());
			return prime * result + ((this.instanceTypeRef == null) ? 0 : this.instanceTypeRef.hashCode());
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TypeSystemHelper.Newable other = (TypeSystemHelper.Newable) obj;
			if (this.newableTypeRef == null) {
				if (other.newableTypeRef != null)
					return false;
			} else if (!this.newableTypeRef.equals(other.newableTypeRef))
				return false;
			if (this.ctorOrConstructSig == null) {
				if (other.ctorOrConstructSig != null)
					return false;
			} else if (!this.ctorOrConstructSig.equals(other.ctorOrConstructSig))
				return false;
			if (this.instanceTypeRef == null) {
				if (other.instanceTypeRef != null)
					return false;
			} else if (!this.instanceTypeRef.equals(other.instanceTypeRef))
				return false;
			return true;
		}

		@Override
		public String toString() {
			ToStringBuilder b = new ToStringBuilder(this);
			b.add("newableTypeRef", this.newableTypeRef);
			b.add("ctorOrConstructSig", this.ctorOrConstructSig);
			b.add("instanceTypeRef", this.instanceTypeRef);
			return b.toString();
		}

		public TypeRef getNewableTypeRef() {
			return this.newableTypeRef;
		}

		public TMethod getCtorOrConstructSig() {
			return this.ctorOrConstructSig;
		}

		public TypeRef getInstanceTypeRef() {
			return this.instanceTypeRef;
		}
	}

	/**
	 * Checks if a value of the given type is "callable" (i.e. can be invoked with a call expression). If so, returns an
	 * instance of class {@link Callable} with further information; if not, returns <code>null</code>.
	 */
	public Callable getCallableTypeRef(RuleEnvironment G, TypeRef typeRef) {
		List<Callable> result = getCallableTypeRefs(G, typeRef);
		if (result.size() == 1) {
			return result.get(0);
		}
		return null;
	}

	public List<Callable> getCallableTypeRefs(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof UnionTypeExpression) {
			// TODO implement special handling for unions
		} else if (typeRef instanceof IntersectionTypeExpression) {
			// TODO improve special handling for intersections
			List<Callable> result = new ArrayList<>();
			var foundWithoutSignature = false;
			var foundWithoutSignatureDynamic = false;
			for (TypeRef currTypeRef : ((IntersectionTypeExpression) typeRef).getTypeRefs()) {
				Callable currCallable = internalGetCallableTypeRef(G, currTypeRef);
				if (currCallable != null) {
					if (!currCallable.signatureTypeRef.isPresent()) {
						foundWithoutSignature = true;
						foundWithoutSignatureDynamic = foundWithoutSignatureDynamic || currCallable.dynamic;
					} else {
						result.add(currCallable);
					}
				}
			}
			if (result.isEmpty() && foundWithoutSignature) {
				return Collections
						.singletonList(new Callable(typeRef, Optional.absent(), foundWithoutSignatureDynamic));
			}
			return result;
		}
		Callable result = internalGetCallableTypeRef(G, typeRef);
		if (result != null) {
			return Collections.singletonList(result);
		}
		return Collections.emptyList();
	}

	private Callable internalGetCallableTypeRef(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof UnknownTypeRef) {
			return null;
		}
		if (typeRef instanceof FunctionTypeExprOrRef) {
			return new Callable(typeRef, Optional.of((FunctionTypeExprOrRef) typeRef), typeRef.isDynamic());
		}
		if (isClassConstructorFunction(G, typeRef)) {
			// don't allow direct invocation of class constructors
			TMethod callableCtor = getCallableClassConstructorFunction(G, typeRef);
			if (callableCtor != null) {
				// exception: this is a class that provides a call signature
				return new Callable(typeRef, Optional.of((FunctionTypeRef) TypeUtils.createTypeRef(callableCtor)),
						typeRef.isDynamic());
			}
			return null;
		}
		TMethod callSig = getCallSignature(G, typeRef);
		if (callSig != null) {
			return new Callable(typeRef, Optional.of((FunctionTypeRef) TypeUtils.createTypeRef(callSig)),
					typeRef.isDynamic());
		}
		if (ts.subtypeSucceeded(G, typeRef, functionTypeRef(G))
				|| ts.subtypeSucceeded(G, typeRef, structuralFunctionTypeRef(G))
				|| (typeRef.isDynamic() && ts.subtypeSucceeded(G, functionTypeRef(G), typeRef))) {
			return new Callable(typeRef, Optional.absent(), typeRef.isDynamic());
		}
		return null;
	}

	public Newable getNewableTypeRef(RuleEnvironment G, NewExpression newExpr, boolean ignoreConstructSignatures) {
		TypeRef calleeTypeRef = ts.type(G, newExpr.getCallee());
		return getNewableTypeRef(G, calleeTypeRef, newExpr, ignoreConstructSignatures);
	}

	/**
	 * Checks if a value of the given type is "newable" (i.e. can be instantiated with keyword "new"). If so, returns an
	 * instance of class {@link Newable} with further information; if not, returns <code>null</code>.
	 */
	public Newable getNewableTypeRef(RuleEnvironment G, TypeRef typeRef, NewExpression newExpr,
			boolean ignoreConstructSignatures) {
		List<Newable> result = getNewableTypeRefs(G, typeRef, newExpr, ignoreConstructSignatures);
		if (result.size() == 1) {
			return result.get(0);
		}
		return null;
	}

	public List<Newable> getNewableTypeRefs(RuleEnvironment G, TypeRef typeRef, NewExpression newExpr,
			boolean ignoreConstructSignatures) {

		if (typeRef instanceof UnionTypeExpression) {
			List<Newable> newables = new ArrayList<>();
			for (TypeRef currTypeRef : ((UnionTypeExpression) typeRef).getTypeRefs()) {
				Newable curr = internalGetNewableTypeRef(G, currTypeRef, newExpr, ignoreConstructSignatures);
				if (curr != null) {
					newables.add(curr);
				}
			}
			List<TypeRef> constructSigsReturn = new ArrayList<>();
			List<TypeRef> resTypeRefs = new ArrayList<>();
			for (Newable newable : newables) {
				resTypeRefs.add(newable.getInstanceTypeRef());
				if (newable.ctorOrConstructSig != null) {
					constructSigsReturn.add(newable.ctorOrConstructSig.getReturnTypeRef());
				}
			}

			// use non-simplified because union with any+ will remove other types that would give additional info to the
			// user
			TypeRef resTypeRef = TypeUtils.createNonSimplifiedUnionType(resTypeRefs);
			TMethod constructSig = TypesFactory.eINSTANCE.createTMethod();
			constructSig.setReturnTypeRef(TypeUtils.createNonSimplifiedUnionType(constructSigsReturn));
			TFormalParameter vArgs = TypesFactory.eINSTANCE.createTFormalParameter();
			constructSig.getFpars().add(vArgs);
			vArgs.setVariadic(true);
			vArgs.setTypeRef(anyTypeRefDynamic(G));
			// TODO improve merging of construct signatures

			return Collections.singletonList(new Newable(typeRef, constructSig, resTypeRef));

		} else if (typeRef instanceof IntersectionTypeExpression) {
			// TODO improve special handling for intersections
			TypeRef typeRef2 = simplifyComputer.simplify(G, (IntersectionTypeExpression) typeRef, true);
			if (typeRef2 instanceof IntersectionTypeExpression) {
				List<Newable> result = new ArrayList<>();
				for (TypeRef currTypeRef : ((IntersectionTypeExpression) typeRef2).getTypeRefs()) {
					Newable curr = internalGetNewableTypeRef(G, currTypeRef, newExpr, ignoreConstructSignatures);
					if (curr != null) {
						result.add(curr);
					}
				}
				return result;
			} else {
				return getNewableTypeRefs(G, typeRef2, newExpr, ignoreConstructSignatures);
			}
		}
		Newable result = internalGetNewableTypeRef(G, typeRef, newExpr, ignoreConstructSignatures);
		if (result != null) {
			return Collections.singletonList(result);
		}
		return Collections.emptyList();
	}

	private Newable internalGetNewableTypeRef(RuleEnvironment G, TypeRef calleeTypeRef, NewExpression newExpr,
			boolean ignoreConstructSignatures) {
		if (calleeTypeRef instanceof TypeTypeRef) {
			TMethod ctor = null;
			Type staticType = getStaticType(G, (TypeTypeRef) calleeTypeRef, true);
			if (staticType instanceof ContainerType<?>) {
				ctor = containerTypesHelper.fromContext(getContextResource(G))
						.findConstructor((ContainerType<?>) staticType);
			}
			TypeRef instanceTypeRef = createTypeRefFromStaticType(G, (TypeTypeRef) calleeTypeRef, newExpr);
			return new Newable(calleeTypeRef, ctor, instanceTypeRef);
		}
		if (isAnyDynamic(G, calleeTypeRef)) {
			TMethod constructSig = TypesFactory.eINSTANCE.createTMethod();
			constructSig.setReturnTypeRef(anyTypeRefDynamic(G));
			TFormalParameter vArgs = TypesFactory.eINSTANCE.createTFormalParameter();
			constructSig.getFpars().add(vArgs);
			vArgs.setVariadic(true);
			vArgs.setTypeRef(anyTypeRefDynamic(G));

			return new Newable(calleeTypeRef, constructSig, anyTypeRefDynamic(G));
		}
		if (!ignoreConstructSignatures) {
			TMethod constructSig = getConstructSignature(G, calleeTypeRef);
			if (constructSig != null) {
				TypeRef returnTypeRef = constructSig.getReturnTypeRef();
				if (returnTypeRef != null && !TypeUtils.isVoid(returnTypeRef)) {
					RuleEnvironment G2 = wrap(G);
					addSubstitutions(G2, newExpr, constructSig);
					TypeRef returnTypeRefSubst = ts.substTypeVariablesWithFullCapture(G2, returnTypeRef);
					return new Newable(calleeTypeRef, constructSig, returnTypeRefSubst);
				}
			}
		}
		return null;
	}

	/**
	 * Checks if a value of type <code>typeRef</code> is a class constructor function.
	 */
	public boolean isClassConstructorFunction(RuleEnvironment G, TypeRef typeRef) {
		Type declaredType = typeRef.getDeclaredType();
		if (declaredType instanceof TMethod) {
			if (((TMethod) declaredType).isConstructor()) {
				return true;
			}
		}
		if (typeRef instanceof FunctionTypeExprOrRef) {
			TFunction ft = ((FunctionTypeExprOrRef) typeRef).getFunctionType();
			if (ft instanceof TMethod) {
				if (((TMethod) ft).isConstructor()) {
					return true;
				}
			}
		}
		if (typeRef instanceof TypeTypeRef) {
			Type cls = getStaticType(G, (TypeTypeRef) typeRef);
			if (cls instanceof TClass) {
				return true;
			}
		}
		return false;
	}

	public TMethod getCallableClassConstructorFunction(RuleEnvironment G, TypeRef typeRef) {
		Type type = null;
		Type declaredType = typeRef.getDeclaredType();
		if (declaredType instanceof TMethod) {
			if (((TMethod) declaredType).isConstructor()) {
				type = ((TMethod) declaredType).getContainingType();
			}
		}
		if (typeRef instanceof FunctionTypeExprOrRef) {
			TFunction ft = ((FunctionTypeExprOrRef) typeRef).getFunctionType();
			if (ft instanceof TMethod) {
				if (((TMethod) ft).isConstructor()) {
					type = ((TMethod) ft).getContainingType();
				}
			}
		}
		if (typeRef instanceof TypeTypeRef) {
			Type cls = getStaticType(G, (TypeTypeRef) typeRef);
			if (cls instanceof TClass) {
				type = cls;
			}
		}
		if (type instanceof TClass) {
			// note: "callable constructors" (i.e. call signatures in classes) are not inherited
			// and cannot appear in StructuralTypeRefs, so no need for ContainerTypesHelper or
			// checking for TStructuralType here:
			return ((TClass) type).getCallSignature();
		}
		return null;
	}

	public TMethod getCallSignature(RuleEnvironment G, TypeRef calleeTypeRef) {
		return getCallSignature(getContextResource(G), calleeTypeRef);
	}

	public TMethod getCallSignature(Resource context, TypeRef calleeTypeRef) {
		return getCallConstructSignature(context, calleeTypeRef, false);
	}

	public TMethod getConstructSignature(RuleEnvironment G, TypeRef calleeTypeRef) {
		return getConstructSignature(getContextResource(G), calleeTypeRef);
	}

	public TMethod getConstructSignature(Resource context, TypeRef calleeTypeRef) {
		return getCallConstructSignature(context, calleeTypeRef, true);
	}

	/**
	 * NOTE: does not cover "callable constructors" (i.e. call signatures in classes); use method
	 * {@link #getCallableClassConstructorFunction(RuleEnvironment,TypeRef)} for this purpose.
	 */
	private TMethod getCallConstructSignature(Resource context, TypeRef calleeTypeRef, boolean searchConstructSig) {
		Type declType = calleeTypeRef.getDeclaredType();
		if (declType instanceof TInterface) {
			if (searchConstructSig) {
				return containerTypesHelper.fromContext(context).findConstructSignature((TInterface) declType);
			} else {
				return containerTypesHelper.fromContext(context).findCallSignature((TInterface) declType);
			}
		}
		if (calleeTypeRef instanceof StructuralTypeRef) {
			TStructuralType structType = ((StructuralTypeRef) calleeTypeRef).getStructuralType();
			if (structType != null) {
				if (searchConstructSig) {
					return structType.getConstructSignature();
				} else {
					return structType.getCallSignature();
				}
			}
		}
		return null;
	}

	public FunctionTypeExprOrRef getFunctionTypeExprOrRef(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof FunctionTypeExprOrRef) {
			return (FunctionTypeExprOrRef) typeRef;
		}
		Callable callable = getCallableTypeRef(G, typeRef);
		if (callable != null) {
			return callable.signatureTypeRef.orNull();
		}
		return null;
	}

	/** Same as {@link #getStaticType(RuleEnvironment, TypeTypeRef, boolean)} without resolving type variables. */
	public Type getStaticType(RuleEnvironment G, TypeTypeRef typeTypeRef) {
		return getStaticType(G, typeTypeRef, false);
	}

	/**
	 * Returns the so-called "static type" of the given {@link TypeTypeRef} or <code>null</code> if not available. Iff
	 * {@code resolveTypeVariables} is <code>true</code>, then type variables will be resolved (i.e. replaced by their
	 * explicit or implicit upper bound).
	 * <p>
	 * Formerly, this was a utility operation in {@code TypeRefs.xcore} but since the introduction of wildcards in
	 * {@code TypeTypeRef}s the 'upperBound' judgment (and thus a RuleEnvironment) is required to compute this and hence
	 * it was moved here.
	 */
	public Type getStaticType(RuleEnvironment G, TypeTypeRef typeTypeRef, boolean resolveTypeVariables) {
		TypeRef staticTypeRef = getStaticTypeRef(G, typeTypeRef, resolveTypeVariables);
		if (staticTypeRef == null) {
			return null;
		}
		return staticTypeRef.getDeclaredType(); // will return null if #getStaticTypeRef() is not of type
												// ParameterizedTypeRef
	}

	public TypeRef getStaticTypeRef(RuleEnvironment G, TypeTypeRef typeTypeRef) {
		return getStaticTypeRef(G, typeTypeRef, false);
	}

	public TypeRef getStaticTypeRef(RuleEnvironment G, TypeTypeRef typeTypeRef, boolean resolveTypeVariables) {
		TypeArgument typeArg = typeTypeRef.getTypeArg();
		TypeRef typeArgUB = resolveTypeVariables
				? ts.upperBoundWithReopenAndResolveTypeVars(G, typeArg)
				: ts.upperBoundWithReopen(G, typeArg);
		return typeArgUB;
	}

	public TypeRef createTypeRefFromStaticType(RuleEnvironment G, TypeTypeRef ctr, ParameterizedAccess paramAccess) {

		return createTypeRefFromStaticType(G, ctr,
				toList(map(paramAccess.getTypeArgs(), ta -> ta.getTypeRef())).toArray(new TypeArgument[0]));
	}

	/**
	 * Creates a parameterized type ref to the wrapped static type of a TypeTypeRef, configured with the given
	 * TypeArguments. Returns UnknownTypeRef if the static type could not be retrieved (e.g. unbound This-Type).
	 */
	public TypeRef createTypeRefFromStaticType(RuleEnvironment G, TypeTypeRef ctr, TypeArgument... typeArgs) {
		TypeRef typeRef = getStaticTypeRef(G, ctr);
		Type type = typeRef == null ? null : typeRef.getDeclaredType();
		if (type != null) {
			return TypeExtensions.ref(type, typeArgs);
		}
		return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
	}

	/**
	 * This method computes the set of all subtypes in the set of TypeRefs. It does not copy the TypeRefs!
	 */
	public List<TypeRef> getSubtypesOnly(RuleEnvironment G, TypeRef... typeRefs) {
		List<TypeRef> intersectTRs = new LinkedList<>();

		for (TypeRef s : typeRefs) {
			if (!exists(intersectTRs, it -> ts.subtypeSucceeded(G, it, s))) {
				Iterables.removeIf(intersectTRs, it -> ts.subtypeSucceeded(G, s, it));
				intersectTRs.add(s);
			}
		}

		return intersectTRs;
	}

	/**
	 * This method computes the set of all super types in the set of TypeRefs. It does not copy the TypeRefs!
	 */
	public List<TypeRef> getSuperTypesOnly(RuleEnvironment G, TypeRef... typeRefs) {
		List<TypeRef> unionTRs = new LinkedList<>();

		for (TypeRef s : typeRefs) {
			if (!exists(unionTRs, it -> ts.subtypeSucceeded(G, s, it))) {
				Iterables.removeIf(unionTRs, it -> ts.subtypeSucceeded(G, s, it));
				unionTRs.add(s);
			}
		}

		return unionTRs;
	}

	/**
	 * From any expression within a generator function or method, the type TNext is returned (referring to the actual
	 * (outer) return type, which is {@code [Async]Generator<TYield,TReturn,TNext>}).
	 */
	public TypeRef getActualGeneratorReturnType(RuleEnvironment G, Expression expr) {
		if (expr == null) {
			return null;
		}
		FunctionDefinition funDef = EcoreUtil2.getContainerOfType(expr.eContainer(), FunctionDefinition.class);
		RuleEnvironment G2 = wrap(G);
		TypeRef myThisTypeRef = getThisTypeAtLocation(G, expr);
		setThisBinding(G2, myThisTypeRef); // takes the real-this type even if it is a type{this} reference.

		if (funDef == null || !funDef.isGenerator()) {
			return null; // yield only occurs in generator functions
		}

		Type tFun = funDef.getDefinedType();
		if (tFun instanceof TFunction) {
			TypeRef actualReturnTypeRef = ((TFunction) tFun).getReturnTypeRef();
			BuiltInTypeScope scope = getPredefinedTypes(G).builtInTypeScope;
			if (TypeUtils.isGeneratorOrAsyncGenerator(actualReturnTypeRef, scope)) {
				return actualReturnTypeRef;
			}
		}
		return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
	}

	/**
	 * Given a {@link TypeRef} to a {@code [Async]Generator<TYield,TReturn,TNext>} class, this method returns TYield, if
	 * existent.
	 */
	public TypeRef getGeneratorTYield(RuleEnvironment G, TypeRef generatorTypeRef) {
		TypeRef yieldTypeRef = null;
		if (generatorTypeRef.getDeclaredTypeArgs().size() >= 1) {
			TypeArgument yieldTypeArg = generatorTypeRef.getDeclaredTypeArgs().get(0);
			if (yieldTypeArg != null)
				yieldTypeRef = ts.upperBound(G, yieldTypeArg); // take upper bound to get rid of Wildcard, etc.
		} else {
			yieldTypeRef = generatorType(G).getTypeVars().get(0).getDefaultArgument();
		}
		return yieldTypeRef;
	}

	/**
	 * Given a {@link TypeRef} to a {@code [Async]Generator<TYield,TReturn,TNext>} class, this method returns TReturn,
	 * if existent.
	 */
	public TypeRef getGeneratorTReturn(RuleEnvironment G, TypeRef generatorTypeRef) {
		TypeRef returnTypeRef = null;
		if (generatorTypeRef.getDeclaredTypeArgs().size() >= 2) {
			TypeArgument returnTypeArg = generatorTypeRef.getDeclaredTypeArgs().get(1);
			if (returnTypeArg != null)
				returnTypeRef = ts.upperBound(G, returnTypeArg); // take upper bound to get rid of Wildcard, etc.
		} else {
			returnTypeRef = generatorType(G).getTypeVars().get(1).getDefaultArgument();
		}
		return returnTypeRef;
	}

	/**
	 * Given a {@link TypeRef} to a {@code [Async]Generator<TYield,TReturn,TNext>} class, this method returns TNext, if
	 * existent.
	 */
	public TypeRef getGeneratorTNext(RuleEnvironment G, TypeRef generatorTypeRef) {
		TypeRef nextTypeRef = null;
		if (generatorTypeRef.getDeclaredTypeArgs().size() >= 3) {
			TypeArgument nextTypeArg = generatorTypeRef.getDeclaredTypeArgs().get(2);
			if (nextTypeArg != null)
				nextTypeRef = ts.upperBound(G, nextTypeArg); // take upper bound to get rid of Wildcard, etc.
		} else {
			nextTypeRef = generatorType(G).getTypeVars().get(2).getDefaultArgument();
		}
		return nextTypeRef;
	}
}
