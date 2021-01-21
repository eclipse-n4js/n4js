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
package org.eclipse.n4js.typesystem.utils

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ImmutableList
import com.google.common.collect.ListMultimap
import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.scoping.builtin.GlobalObjectScope
import org.eclipse.n4js.scoping.builtin.VirtualBaseTypeScope
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.types.AnyType
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TN4Classifier
import org.eclipse.n4js.ts.types.TObjectPrototype
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.types.UndefinedType
import org.eclipse.n4js.ts.types.VoidType
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.RecursionGuard
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.util.CancelIndicator

import static extension org.eclipse.n4js.ts.utils.TypeUtils.*

/**
 * Extensions of class RuleEnvironment for handling substitutions and
 * retrieving build in types.
 */
class RuleEnvironmentExtensions {

	/**
	 * Key used for storing a cancel indicator in a rule environment. Client code should not use this constant
	 * directly, but instead use methods
	 * {@link RuleEnvironmentExtensions#addCancelIndicator(RuleEnvironment,CancelIndicator)} and
	 * {@link RuleEnvironmentExtensions#getCancelIndicator(RuleEnvironment)}.
	 */
	private static final String KEY__CANCEL_INDICATOR = "cancelIndicator";

	/**
	 * Key used for storing a 'this' binding in a rule environment. Client code should not use this constant
	 * directly, but instead use methods
	 * {@link RuleEnvironmentExtensions#addThisType(RuleEnvironment,TypeRef) addThisType(RuleEnvironment G, TypeRef actualThisTypeRef)} and
	 * {@link RuleEnvironmentExtensions#getThisType(RuleEnvironment) getThisType(RuleEnvironment G)}.
	 */
	private static final String KEY__THIS_BINDING = "this";

	/**
	 * Key used for storing inconsistent substitutions in a rule environment. Client code should not use this constant
	 * directly, but instead use methods
	 * {@link RuleEnvironmentExtensions#recordInconsistentSubstitutions(RuleEnvironment)},
	 * {@link RuleEnvironmentExtensions#addInconsistentSubstitutions(RuleEnvironment,TypeVariable,Collection)}, and
	 * {@link RuleEnvironmentExtensions#getInconsistentSubstitutions(RuleEnvironment,TypeVariable)}.
	 */
	private static final String KEY__INCONSISTENT_SUBSTITUTIONS = "inconsistentSubstitutions";

	/**
	 * Key for storing an ITypeReplacementProvider defining a replacement of some types by other types within
	 * the context of a rule environment. Used when dealing with replacing an API by its implementation project.
	 */
	private static final String KEY__TYPE_REPLACEMENT = "typeReplacement";

	/**
	 * Key for storing IDs of captures that should *not* be reopened by method
	 * {@link N4JSTypeSystem#upperBoundWithReopen(RuleEnvironment, TypeArgument)}, and similar methods.
	 * <p>
	 * Client code should not use this constant directly, but should instead use methods
	 * {@link #addFixedCapture(RuleEnvironment, ExistentialTypeRef)} and
	 * {@link #isFixedCapture(RuleEnvironment, ExistentialTypeRef)}.
	 */
	private static final String KEY__FIXED_CAPTURE_ID = "fixedCaptureId";

	public static final String GUARD_VARIABLE_DECLARATION = "varDecl";
	public static final String GUARD_TYPE_CALL_EXPRESSION = "typeCallExpression";
	public static final String GUARD_TYPE_PROPERTY_ACCESS_EXPRESSION = "typePropertyAccessExpression";
	public static final String GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__STRUCT = "subtypeRefParameterizedTypeRef__struct";
	public static final String GUARD_SUBST_TYPE_VARS = "substTypeVariablesInParameterizedTypeRef";
	public static final String GUARD_SUBST_TYPE_VARS__IMPLICIT_UPPER_BOUND_OF_WILDCARD = "substTypeVars_implicitUpperBoundOfWildcard";
	public static final String GUARD_STRUCTURAL_TYPING_COMPUTER__IN_PROGRESS = "StructuralTypingComputer__inProgress";
	public static final String GUARD_STRUCTURAL_TYPING_COMPUTER__IN_PROGRESS_FOR_TYPE_REF = "StructuralTypingComputer__inProgressForTypeRef";
	public static final String GUARD_CHECK_TYPE_ARGUMENT_COMPATIBILITY = "N4JSTypeSystem#checkTypeArgumentCompatibility";
	public static final String GUARD_REDUCER__IS_SUBTYPE_OF = "Reducer#isSubtypeOf";
	public static final String GUARD_REDUCER__REDUCE_STRUCTURAL_TYPE_REF = "Reducer#reduceStructuralTypeRef";
	public static final String GUARD_TYPE_REFS_NESTED_MODIFICATION_SWITCH__MODIFY_BOUNDS_OF_WILDCARD = "TypeRefsNestedModificationSwitch#modifyBoundsOfWildcard";

	/**
	 * Returns a new {@code RuleEnvironment}; we need this because of the
	 * {@code BuiltInTypeScope} and we cannot simply create a new empty environment.
	 *
	 * @param context must not be null!
	 */
	public def static RuleEnvironment newRuleEnvironment(EObject context) {
		var res = context.eResource;
		if (res === null) {
			if (context instanceof BoundThisTypeRef) {
				res = context.actualThisTypeRef.declaredType.eResource
			}
			// maybe we can derive the resource set from other object as well..
		}

		var G = new RuleEnvironment();
		G.setPredefinedTypesFromObjectsResourceSet(res.resourceSet);
		G.put(Resource, res);
		return G;
	}

	/**
	 * Returns a new {@code RuleEnvironment} with a given resource to provide context information and xtext index access.
	 */
	public def static RuleEnvironment newRuleEnvironment(Resource resource) {
		var G = new RuleEnvironment();
		G.setPredefinedTypesFromObjectsResourceSet(resource.resourceSet);
		G.put(Resource, resource)
		return G;
	}

	/**
	 * Returns a new {@code RuleEnvironment} for the same predefined types, resource, and cancel indicator as the given
	 * rule environment.
	 * <p>
	 * IMPORTANT: other key/value pairs from G will not be available in the returned rule environment! Compare this with
	 * method {@link #wrap(RuleEnvironment)}.
	 */
	public def static RuleEnvironment newRuleEnvironment(RuleEnvironment G) {
		var Gnew = new RuleEnvironment();
		Gnew.setPredefinedTypes(G.getPredefinedTypes());
		Gnew.put(Resource, G.get(Resource));
		Gnew.addCancelIndicator(G.getCancelIndicator());
		return Gnew;
	}

	/**
	 * Return a new rule environment wrapping the given rule environment 'G', i.e. the all key/value pairs of 'G'
	 * will be readable in the returned environment, but changes to the returned environment will not affect 'G'.
	 */
	public def static RuleEnvironment wrap(RuleEnvironment G) {
		new RuleEnvironment(G);
	}

	def static setPredefinedTypesFromObjectsResourceSet(RuleEnvironment G, ResourceSet resourceSet) {
		if (resourceSet === null) {
			throw new IllegalArgumentException("Resource set used to load predefined types must not be null at org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.setPredefinedTypesFromObjectsResourceSet(RuleEnvironment, ResourceSet)");
		}
		val builtInTypeScope = BuiltInTypeScope.get(resourceSet);
		val globalObjectTypeScope = GlobalObjectScope.get(resourceSet);
		val virtualBaseTypeScope = VirtualBaseTypeScope.get(resourceSet);
		G.put(PredefinedTypes.PREDEFINED_TYPES_KEY,
			new PredefinedTypes(builtInTypeScope, globalObjectTypeScope, virtualBaseTypeScope));
	}

	def static setPredefinedTypes(RuleEnvironment G, PredefinedTypes predefinedTypes) {
		G.put(PredefinedTypes.PREDEFINED_TYPES_KEY, predefinedTypes);
	}

	def static PredefinedTypes getPredefinedTypes(RuleEnvironment G) {
		val predefinedTypes = G.get(PredefinedTypes.PREDEFINED_TYPES_KEY) as PredefinedTypes;
		if (predefinedTypes === null) {
			throw new IllegalStateException(
				"Predefined types not set, call type system with configured rule environment")
		}
		return predefinedTypes;
	}

	/**
	 * Convenience method returning the {@link BuiltInTypeScope} via this rule environment's {@link PredefinedTypes}.
	 */
	def static BuiltInTypeScope getBuiltInTypeScope(RuleEnvironment G) {
		return G?.predefinedTypes?.builtInTypeScope;
	}

	/**
	 * Convenience method returning the {@link GlobalObjectScope} via this rule environment's {@link PredefinedTypes}.
	 */
	def static GlobalObjectScope getGlobalObjectScope(RuleEnvironment G) {
		return G?.predefinedTypes?.globalObjectScope;
	}

	/**
	 * Returns the resource used to load built-in types and to resolve proxies. This is the resource of the object
	 */
	public def static Resource getContextResource(RuleEnvironment G) {
		return G.get(Resource) as Resource;
	}

	/**
	 * Add a cancel indicator to the given rule environment.
	 */
	def static void addCancelIndicator(RuleEnvironment G, CancelIndicator cancelIndicator) {
		G.put(KEY__CANCEL_INDICATOR, cancelIndicator);
	}

	/**
	 * Returns the cancel indicator of this rule environment or <code>null</code> if none has been added, yet.
	 */
	def static CancelIndicator getCancelIndicator(RuleEnvironment G) {
		return G.get(KEY__CANCEL_INDICATOR) as CancelIndicator;
	}

	/**
	 * <b>IMPORTANT:</b><br>
	 * use this only for rare special cases (e.g. logging); ordinary cancellation handling should be done by invoking
	 * {@link OperationCanceledManager#checkCanceled(CancelIndicator)} with the cancel indicator returned by
	 * {@link #getCancelIndicator(RuleEnvironment)}!
	 * <p>
	 * Tells if the given rule environment has a cancel indicator AND that indicator is canceled.
	 */
	def static boolean isCanceled(RuleEnvironment G) {
		val cancelIndicator = G.getCancelIndicator;
		return cancelIndicator!==null && cancelIndicator.isCanceled();
	}

	/*
	 * Adds the actual this type to the rule environment if the actual this type is either
	 * a  ParameterizedTypeRef or a BoundThisTypeRef. The latter case happens if the receiver
	 * of a function call is a function call itself, returning a this type.
	 */
	def static void setThisBinding(RuleEnvironment G, TypeRef actualThisTypeRef) {
		switch (actualThisTypeRef) {
			TypeTypeRef: // IDE-785 decompose
				if (actualThisTypeRef.getTypeArg instanceof TypeRef) {
					setThisBinding(G,actualThisTypeRef.getTypeArg as TypeRef)
				}
			ParameterizedTypeRef:
				G.put(KEY__THIS_BINDING, TypeUtils.createBoundThisTypeRef(actualThisTypeRef))
			BoundThisTypeRef:
				G.put(KEY__THIS_BINDING, actualThisTypeRef)
		}
	}

	/**
	 * Returns the current this type, this must have been added before via
	 * {@link #addThisType(RuleEnvironment, TypeRef)}
	 */
	def static TypeRef getThisBinding(RuleEnvironment G) {
		G.get(KEY__THIS_BINDING) as TypeRef;
	}

	/**
	 * Turn on recording of inconsistent substitutions in Xsemantics judgment {@code substTypeVariables}.
	 * This is used by a validation which will then produce a corresponding error.
	 */
	def static void recordInconsistentSubstitutions(RuleEnvironment G) {
		G.put(KEY__INCONSISTENT_SUBSTITUTIONS, ArrayListMultimap.<TypeVariable,TypeRef>create());
	}

	/**
	 * Iff recording of inconsistent substitutions has been turned on for the given rule environment (see method
	 * {@link #recordInconsistentSubstitutions(RuleEnvironment)}), then this method will store such substitutions
	 * in the given rule environment.
	 */
	def static void addInconsistentSubstitutions(RuleEnvironment G, TypeVariable typeVar,
		Collection<? extends TypeRef> substitutions) {
		val storage = G.get(KEY__INCONSISTENT_SUBSTITUTIONS) as ListMultimap<TypeVariable,TypeRef>;
		if(storage!==null) {
			storage.putAll(typeVar, substitutions);
		}
	}

	/**
	 * Iff recording of inconsistent substitutions has been turned on for the given rule environment (see method
	 * {@link #recordInconsistentSubstitutions(RuleEnvironment)}), then this method will return those substitutions.
	 * Otherwise, an empty list is returned.
	 */
	def static List<TypeRef> getInconsistentSubstitutions(RuleEnvironment G, TypeVariable typeVar) {
		val storage = G.get(KEY__INCONSISTENT_SUBSTITUTIONS) as ListMultimap<TypeVariable,TypeRef>;
		return if(storage!==null) storage.get(typeVar) else Collections.emptyList();
	}

	def static boolean hasReplacements(RuleEnvironment G) {
		return G.get(KEY__TYPE_REPLACEMENT) !== null;
	}

	def static void setTypeReplacement(RuleEnvironment G, ITypeReplacementProvider replacementProvider) {
		G.put(KEY__TYPE_REPLACEMENT, replacementProvider);
	}

	def static TypeRef getReplacement(RuleEnvironment G, TypeRef typeRef) {
		if(typeRef instanceof ParameterizedTypeRef) {
			if(!(typeRef instanceof FunctionTypeRef)) {
				val type = typeRef.declaredType;
				val replacement = getReplacement(G,type);
				if(replacement!==type) { // identity compare is ok here
					val cpy = TypeUtils.copyWithProxies(typeRef); // do not resolve proxies
					cpy.declaredType = replacement;
					return cpy;
				}
			}
		} else {
			// no replacement required for other kinds of TypeRef (e.g. TypeTypeRef, UnionTypeExpression),
			// because in those cases the subtype check - and replacement is supposed to only affect subtype
			// checking - will boil down to nested subtype checks of ParameterizedTypeRef
		}
		return typeRef;
	}

	def static <T extends Type> T getReplacement(RuleEnvironment G, T type) {
		val replacementProvider = G.get(KEY__TYPE_REPLACEMENT) as ITypeReplacementProvider;
		val replacement = replacementProvider?.getReplacement(type);
		return if(replacement!==null) replacement else type;
	}

	/**
	 * The {@link ExistentialTypeRef}s with the given IDs will *not* be reopened by method
	 * {@link N4JSTypeSystem#upperBoundWithReopen(RuleEnvironment, TypeArgument)}, and similar methods.
	 */
	def static void addFixedCapture(RuleEnvironment G, ExistentialTypeRef etr) {
		val id = etr.id;
		if (id !== null) {
			G.put(KEY__FIXED_CAPTURE_ID -> id, Boolean.TRUE);
		}
	}

	/**
	 * Tells whether the given {@link ExistentialTypeRef}s has an ID that was registered via
	 * {@link #addFixedCapture(RuleEnvironment, ExistentialTypeRef)} to avoid it to be reopened by method
	 * {@link N4JSTypeSystem#upperBoundWithReopen(RuleEnvironment, TypeArgument)}, and similar methods.
	 */
	def static boolean isFixedCapture(RuleEnvironment G, ExistentialTypeRef etr) {
		return G.get(KEY__FIXED_CAPTURE_ID -> etr.id) !== null;
	}

	def static TypeRef createTypeRefFromUpperBound(TypeVariable typeVar) {
		TypeUtils.copyIfContained(typeVar.declaredUpperBound)
	}

	/* Returns the top type (which is currently 'any' but may change in the future). */
	public def static AnyType topType(RuleEnvironment G) {
		G.anyType
	}

	/* Returns newly created reference to the top type (which is currently 'any' but may change in the future). */
	public def static ParameterizedTypeRef topTypeRef(RuleEnvironment G) {
		G.anyTypeRef
	}

	/* Returns the bottom type (which is currently 'undefined' but may change in the future). */
	public def static UndefinedType bottomType(RuleEnvironment G) {
		G.undefinedType
	}

	/* Returns newly created reference to the bottom type (which is currently 'undefined' but may change in the future). */
	public def static ParameterizedTypeRef bottomTypeRef(RuleEnvironment G) {
		G.undefinedTypeRef
	}

	/* Returns built-in type {@code boolean} */
	public def static booleanType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.booleanType
	}

	/* Returns newly created reference to built-in type {@code boolean} */
	public def static booleanTypeRef(RuleEnvironment G) {
		G.booleanType.createTypeRef
	}

	/* Returns built-in type {@code string} */
	public def static stringType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.stringType
	}

	/* Returns newly created reference to built-in type {@code string} */
	public def static stringTypeRef(RuleEnvironment G) {
		G.stringType.createTypeRef
	}

	/* Returns built-in object type {@code String} */
	public def static stringObjectType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.stringObjectType
	}

	/* Returns newly created reference to built-in object type {@code String} */
	public def static stringObjectTypeRef(RuleEnvironment G) {
		G.stringObjectType.createTypeRef
	}

	/* Returns built-in type {@code number} */
	public def static numberType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.numberType
	}

	/* Returns newly created reference to built-in type {@code number} */
	public def static numberTypeRef(RuleEnvironment G) {
		G.numberType.createTypeRef
	}

	/* Returns built-in object type {@code Number} */
	public def static numberObjectType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.numberObjectType
	}

	/* Returns newly created reference to built-in object type {@code Number} */
	public def static numberObjectTypeRef(RuleEnvironment G) {
		G.numberObjectType.createTypeRef
	}

	/* Returns built-in type {@code int} */
	public def static intType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.intType
	}

	/* Returns newly created reference to built-in type {@code int} */
	public def static intTypeRef(RuleEnvironment G) {
		G.intType.createTypeRef
	}

	/* Returns built-in type {@code symbol} */
	public def static symbolType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.symbolType
	}

	/* Returns newly created reference to built-in type {@code symbol} */
	public def static symbolTypeRef(RuleEnvironment G) {
		G.symbolType.createTypeRef
	}

	/* Returns built-in object type {@code Symbol} */
	public def static symbolObjectType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.symbolObjectType
	}

	/* Returns newly created reference to built-in object type {@code Symbol} */
	public def static symbolObjectTypeRef(RuleEnvironment G) {
		G.symbolObjectType.createTypeRef
	}

	/** Returns built-in type {@code any} */
	public def static anyType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.anyType
	}

	/* Returns newly created reference to built-in type {@code any} */
	public def static anyTypeRef(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.anyTypeRef
	}

	/* Returns newly created dynamic reference to built-in type {@code any}, that is {@code any+}.
	 * This is the default type used in JavaScript modes.
	 */
	public def static anyTypeRefDynamic(RuleEnvironment G) {
		val ParameterizedTypeRef result = G.anyType.createTypeRef
		result.dynamic = true;
		return result;
	}

	/* Returns newly created reference to built-in type {@code null} */
	public def static nullTypeRef(RuleEnvironment G) {
		G.nullType.createTypeRef
	}

	/* Returns built-in type {@code undefined} */
	public def static undefinedType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.undefinedType
	}

	/* Returns built-in type {@code null} */
	public def static nullType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.nullType
	}

	/* Returns newly created reference to built-in type {@code undefined} */
	public def static undefinedTypeRef(RuleEnvironment G) {
		G.undefinedType.createTypeRef
	}

	/* Returns built-in type {@code void} */
	public def static voidType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.voidType
	}

	/* Returns newly created reference to built-in type {@code void} */
	public def static voidTypeRef(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.voidTypeRef
	}

	/* Returns built-in type {@code RegExp} */
	public def static regexpType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.regexpType
	}

	/* Returns newly created reference to built-in type {@code RegExp} */
	public def static regexpTypeRef(RuleEnvironment G) {
		G.regexpType.createTypeRef
	}

	/* Returns built-in type {@code Array<T>} */
	public def static arrayType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.arrayType
	}

	/* Returns newly created reference to built-in type {@code Array<T>} */
	public def static arrayTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		G.arrayType.createTypeRef(typeArgs)
	}

	/* Returns built-in type {@code Object} */
	public def static objectType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.objectType
	}

	/* 	Returns newly created reference to built-in type {@code Object} */
	public def static objectTypeRef(RuleEnvironment G) {
		G.objectType.createTypeRef
	}

	/* 	Returns newly created reference to built-in global object type */
	public def static globalObjectType(RuleEnvironment G) {
		return G.getPredefinedTypes().globalObjectScope.globalObject;
	}

	/* 	Returns newly created reference to built-in global object type */
	public def static globalObjectTypeRef(RuleEnvironment G) {
		G.globalObjectType.createTypeRef
	}

	/* Returns built-in type {@code Function} */
	public def static functionType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.functionType
	}

 	/* Returns newly created structural reference to built-in type {@code Function} */
 	public def static structuralFunctionTypeRef(RuleEnvironment G) {
 		G.functionType.createTypeRef(TypingStrategy.STRUCTURAL)
 	}

	/* 	Returns newly created reference to built-in type {@code Function} */
	public def static functionTypeRef(RuleEnvironment G) {
		G.functionType.createTypeRef
	}

	/* Returns built-in type {@code N4Object} */
	public def static n4ObjectType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.n4ObjectType
	}

	/* 	Returns newly created reference to built-in type {@code N4Object} */
	public def static n4ObjectTypeRef(RuleEnvironment G) {
		G.n4ObjectType.createTypeRef
	}

	/* Returns built-in type {@code N4Enum} */
	public def static n4EnumType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.n4EnumType
	}

	/* 	Returns newly created reference to built-in type {@code N4Enum} */
	public def static n4EnumTypeRef(RuleEnvironment G) {
		G.n4EnumType.createTypeRef
	}

	/* Returns built-in type {@code N4NumberBasedEnum} */
	public def static n4NumberBasedEnumType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.n4NumberBasedEnumType
	}
	/* Returns a newly created reference to the built-in type {@code N4NumberBasedEnum} */
	public def static n4NumberBasedEnumTypeRef(RuleEnvironment G) {
		G.n4NumberBasedEnumType.createTypeRef
	}

	/* Returns built-in type {@code N4StringBasedEnum} */
	public def static n4StringBasedEnumType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.n4StringBasedEnumType
	}
	/* Returns a newly created reference to the  built-in type {@code N4StringBasedEnum} */
	public def static n4StringBasedEnumTypeRef(RuleEnvironment G) {
		G.n4StringBasedEnumType.createTypeRef
	}

	/* Returns built-in type {@code i18nKey} */
	public def static i18nKeyType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.i18nKeyType
	}

	/* Returns built-in type {@code pathSelector} */
	public def static pathSelectorType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.pathSelectorType
	}

	/* Returns built-in type {@code typeName} */
	public def static typeNameType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.typeNameType
	}

	/* Returns built-in type {@code N4Provider} */
	public def static n4ProviderType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.n4ProviderType
	}

	/* Returns built-in type {@code Error}. */
	public def static errorType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.errorType
	}

	/* Returns newly created reference to built-in type {@code Error}. */
	public def static errorTypeRef(RuleEnvironment G) {
		G.errorType.createTypeRef
	}

	/* Returns built-in type {@code ArgumentsType} */
	public def static argumentsType(RuleEnvironment G) {
		G.getPredefinedTypes().virtualBaseTypeScope.argumentsType
	}

	/* Returns newly created reference to built-in type {@code ArgumentsType} */
	public def static argumentsTypeRef(RuleEnvironment G) {
		G.argumentsType.createTypeRef
	}
	
	/* Returns built-in type {@code MigrationContext} */
	public def static migrationContextType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.migrationContextType;
	}
	
	/* Returns newly created reference to built-in type {@code MigrationContext} */
	public def static migrationContextTypeRef(RuleEnvironment G) {
		G.migrationContextType.createTypeRef;
	}

	/* Returns built-in type {@code Iterable<T>} */
	public def static iterableType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.iterableType
	}

	/* Returns newly created reference to built-in type {@code Iterable<T>} */
	public def static iterableTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		createTypeRef(G.iterableType, typeArgs);
	}

	/* Returns built-in type {@code AsyncIterable<T>} */
	public def static asyncIterableType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.asyncIterableType
	}

	/* Returns newly created reference to built-in type {@code AsyncIterable<T>} */
	public def static asyncIterableTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		createTypeRef(G.asyncIterableType, typeArgs);
	}

	/* Returns built-in type {@code IterableN<T1...TN>} */
	public def static iterableNType(RuleEnvironment G, int n) {
		G.getPredefinedTypes().builtInTypeScope.getIterableNType(n)
	}

	/* Returns newly created reference to built-in type {@code IterableN<T1...TN>} */
	public def static iterableNTypeRef(RuleEnvironment G, int n, TypeArgument... typeArgs) {
		createTypeRef(G.iterableNType(n), typeArgs);
	}

	/* Returns built-in type {@code IterableN<T1...TN>} */
	public def static iterableNTypes(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.getIterableNTypes
	}

	/**
	 * Returns true iff <code>obj</code> is a {@link Type} or {@link TypeRef} and is or points to
	 * one of the <code>IterableN&lt;...></code> built-in types. Does <b>not</b> check for the
	 * built-in type <code>Iterable&lt;T></code>.
	 */
	public def static boolean isIterableN(RuleEnvironment G, EObject obj) {
		val type = switch(obj) {
			Type: obj
			TypeRef: obj.declaredType
		};
		return type!==null && G.iterableNTypes.contains(type);
	}

	/* Returns built-in type {@code Promise<S,F>} */
	public def static promiseType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.promiseType
	}

	/* Returns newly created reference to built-in type {@code Promise<S,F>} */
	public def static promiseTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		createTypeRef(G.promiseType, typeArgs);
	}

	/* Returns built-in type {@code Generator<Y,R,N>} */
	public def static generatorType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.generatorType
	}

	/* Returns newly created reference to built-in type {@code Generator<Y,R,N>} */
	public def static generatorTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		createTypeRef(G.generatorType, typeArgs);
	}

	/* Returns built-in type {@code AsyncGenerator<Y,R,N>} */
	public def static asyncGeneratorType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.asyncGeneratorType
	}

	/* Returns newly created reference to built-in type {@code AsyncGenerator<Y,R,N>} */
	public def static asyncGeneratorTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		createTypeRef(G.asyncGeneratorType, typeArgs);
	}

	/* Returns built-in type {@code N4Element} */
	public def static TClass n4ElementType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.n4ElementType
	}

	/* Returns built-in type {@code N4NamedElement} */
	public def static TClass n4NamedElementType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.n4NamedElementType
	}

	/* Returns built-in type {@code N4Type} */
	public def static TClass n4TypeType(RuleEnvironment G) {
		G.getPredefinedTypes().builtInTypeScope.n4TypeType
	}

	/**
	 * Returns true if the given type is any.
	 */
	public def static boolean isAny(RuleEnvironment G, TypeArgument typeArg) {
		return typeArg!==null && typeArg.declaredType == anyType(G) && isNominal(typeArg);
	}

	/**
	 * Returns true if the given type reference refers to the built-in type {@link #objectType(RuleEnvironment) Object}.
	 */
	public def static boolean isObject(RuleEnvironment G, TypeArgument typeArg) {
		return typeArg!==null && typeArg.declaredType == objectType(G) && isNominal(typeArg);
	}

	/**
	 * Returns true if the given type reference refers to the built-in type {@link #functionType(RuleEnvironment) Function}.
	 */
	public def static boolean isFunction(RuleEnvironment G, TypeArgument typeArg) {
		return typeArg!==null && typeArg.declaredType == functionType(G) && isNominal(typeArg);
	}

	/**
	 * Returns true if the given type is symbol.
	 */
	public def static boolean isSymbol(RuleEnvironment G, TypeArgument typeArg) {
		return typeArg!==null && typeArg.declaredType == symbolType(G) && isNominal(typeArg);
	}

	private def static boolean isNominal(TypeArgument typeArg) {
		return typeArg.isTypeRef() && !TypeUtils.isStructural(typeArg as TypeRef);
	}

	/**
	 * Returns true if the given type is one of the {@link BuiltInTypeScope#isNumeric(Type) numeric} primitive
	 * built-in types.
	 */
	public def static boolean isNumeric(RuleEnvironment G, Type type) {
		G.predefinedTypes.builtInTypeScope.isNumeric(type)
	}

	/**
	 * Returns true if the given type reference points to one of the {@link BuiltInTypeScope#isNumeric(Type) numeric}
	 * primitive built-in types.
	 */
	public def static boolean isNumeric(RuleEnvironment G, TypeArgument typeArg) {
		if (typeArg===null) {
			return false;
		}
		if (G.predefinedTypes.builtInTypeScope.isNumeric(typeArg.declaredType)) {
			return true;
		}
		if (typeArg instanceof UnionTypeExpression) {
			return typeArg.typeRefs.forall[e|isNumeric(G, e)];
		}
		if (typeArg instanceof IntersectionTypeExpression) {
			return typeArg.typeRefs.exists[e|isNumeric(G, e)];
		}
		return false;
	}

	/**
	 * Returns true iff typeRef is a union type and one if its elements
	 * is numeric, boolean, null or undefined or contains one of these types.
	 * Note that this method returns false for number types -- the
	 * typeref needs to be a union type!
	 */
	public def static boolean containsNumericOperand(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof UnionTypeExpression) {
			return typeRef.typeRefs.exists[e | 
				G.predefinedTypes.builtInTypeScope.isNumericOperand(e.declaredType)
				|| containsNumericOperand(G, e)
			]
		}
		return false;		
	}
	
	/**
	 * Returns true if the given type reference can be used in a numeric
	 * operation as operand leading to a numeric result. This is true for
	 * number, int, boolean, null, or even undefined, for unions of these types,
	 * and for intersections containing any of these types.
	 */
	public def static boolean isNumericOperand(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef===null) {
			return false;
		}
		if (G.predefinedTypes.builtInTypeScope.isNumericOperand(typeRef.declaredType)) {
			return true;
		}
		if (typeRef instanceof UnionTypeExpression) {
			return typeRef.typeRefs.forall[e|isNumericOperand(G, e)];
		}
		if (typeRef instanceof IntersectionTypeExpression) {
			return typeRef.typeRefs.exists[e|isNumericOperand(G, e)];
		}
		return false;
	}
	
	/**
	 * Same as {@link TypeUtils#wrapInTypeRef(BuiltInTypeScope,Type,TypeArgument...)}, but will obtain
	 * the required {@code BuiltInTypeScope} from the given rule environment.
	 */
	public def static TypeRef wrapTypeInTypeRef(RuleEnvironment G, Type type, TypeArgument... typeArgs) {
		return TypeUtils.wrapTypeInTypeRef(G.predefinedTypes.builtInTypeScope, type, typeArgs);
	}

	/**
	 * Returns all type variables for which a type mapping is defined in the given rule environment.
	 */
	public static def Set<TypeVariable> getTypeMappingKeys(RuleEnvironment G) {
		val result = newLinkedHashSet;
		var env = G;
		while(env!==null) {
			result += env.getEnvironment().keySet().filter(TypeVariable);
			env = env.getNext();
		}
		return result;
	}
	/**
	 * Convenience method. Same as {@link #addTypeMapping(RuleEnvironment,TypeVariable,TypeArgument)},
	 * but for adding several mappings.
	 */
	public static def void addTypeMappings(RuleEnvironment G, List<? extends TypeVariable> keys, List<? extends TypeArgument> values) {
		if(keys===null || values===null)
			return;
		val size = Math.min(keys.size(),values.size());
		for(var idx=0;idx<size;idx++) {
			G.addTypeMapping(keys.get(idx), values.get(idx));
		}
	}
	/**
	 * Low-level method for adding a type variable -> type argument mapping to a rule environment.
	 * Use this method only if you know the exact mapping and you do not need support for handling
	 * existing mappings.
	 * <p>
	 * An existing mapping for type variable 'key' will be overwritten. If the given mapping is
	 * invalid, i.e. {@link #isValidMapping(RulenEnvironment,TypeVariable,TypeArgument isValidMapping()}
	 * returns false, then this method will do nothing.
	 */
	public static def void addTypeMapping(RuleEnvironment G, TypeVariable key, TypeArgument value) {
		// ignore invalid type mappings
		if(!G.isValidTypeMapping(key,value))
			return;
		G.put(key, value);
	}

	/**
	 * Checks if rule environment G defines an actual, i.e. non-reflexive, type variable
	 * substitution for <code>typeVariable</code>. Argument <code>typeVariable</code> may
	 * either be a {@link TypeVariable} itself or a {@link TypeRef} with a type variable
	 * as its declared type.<p>
	 * For convenience, this methods takes arguments of any type but will always return
	 * <code>false</code> if the argument is neither an instance of {@link TypeVariable}
	 * nor an instance of {@link TypeRef} with a declared type that is an instance of
	 * {@link TypeVariable}.
	 */
	public def static boolean hasSubstitutionFor(RuleEnvironment G, Object typeVariable) {
		val key = if(typeVariable instanceof TypeRef) typeVariable.declaredType else typeVariable;
		if(key instanceof TypeVariable) {
			val value = G.get(key)
			return value!==null && !(value instanceof TypeRef && (value as TypeRef).declaredType===key)
		}
		return false;
	}

	public def static boolean isValidTypeMapping(RuleEnvironment G, TypeVariable key, TypeArgument value) {
		// ignore reflexive mappings, e.g. T -> T, T -> G<T>, etc.
		if (TypeUtils.isOrContainsRefToTypeVar(value,key))
			return false;
		// ignore DeferredTypeRefs
		if (value instanceof DeferredTypeRef)
			return false;
		// ignore void (type 'void' is never a valid substitution for a type variable)
		if (value instanceof ParameterizedTypeRef)
			if (value.declaredType instanceof VoidType)
				return false;
		return true;
	}

	/**
	 * Returns the declared or implicit super type of a class. This might be a TClass or, in case
	 * of implicit super types and external classes, a TObjectPrototype (i.e. "Object").
	 */
	public def static TClassifier getDeclaredOrImplicitSuperType(RuleEnvironment G, TClass tClass) {
		// this method is called by validator, AST and type model may be corrupt
		// thus the super type maybe not a classifier.
		if (tClass.superClassRef !== null && tClass.superClassRef.declaredType instanceof TClassifier)
			return tClass.superClassRef.declaredType as TClassifier
		else if (tClass.external)
			return G.objectType
		else
			return G.n4ObjectType
	}

	/**
	 * Returns transitive, non-reflexive closure of implicit super types. All implicit super types are non-generic, so
	 * type arguments can be ignored here savely.
	 */
	public def static List<ParameterizedTypeRef> collectAllImplicitSuperTypesOfType(RuleEnvironment G, Type declaredType) {
		collectAllImplicitSuperTypesOfType(G, declaredType, new RecursionGuard<Type>());
	}

	private def static List<ParameterizedTypeRef> collectAllImplicitSuperTypesOfType(RuleEnvironment G, Type declaredType, RecursionGuard<Type> guard) {

		// Type argument is null.
		if (null === declaredType) {
			return emptyList;
		}

		if (!guard.tryNext(declaredType)) {
			if (declaredType instanceof TClass) {
				if (declaredType == G.n4ObjectType || (declaredType.external && !declaredType.declaredN4JS) ||
						declaredType.typingStrategy==TypingStrategy.STRUCTURAL) {
							return G.objectPrototypesAllImplicitSuperTypeRefs;
				} else {
					return G.n4ClassifiersAllImplicitSuperTypeRefs;
				}
			} else {
				return emptyList; // recursion can happen only in case of TClasses.
			}
		}

		switch (declaredType) {
			TClass:
				if (declaredType == G.n4ObjectType || (declaredType.external && !declaredType.declaredN4JS) ||
					declaredType.typingStrategy==TypingStrategy.STRUCTURAL)
					G.objectPrototypesAllImplicitSuperTypeRefs
				else {
					if (declaredType.superClassRef===null) {
						G.n4ClassifiersAllImplicitSuperTypeRefs
					} else {
						G.collectAllImplicitSuperTypes(declaredType.superClassRef, guard);
					}
				}
			TN4Classifier:
				G.n4ClassifiersAllImplicitSuperTypeRefs
			TObjectPrototype:
				if (declaredType == G.objectType)
					emptyList
				else
					G.objectPrototypesAllImplicitSuperTypeRefs
			TEnum:
				switch (N4JSLanguageUtils.getEnumKind(declaredType)) {
					case Normal: #[G.objectTypeRef]
					case NumberBased: #[G.n4NumberBasedEnumTypeRef /* , G.numberTypeRef */]
					case StringBased: #[G.n4StringBasedEnumTypeRef /* , G.stringTypeRef */]
				}
			default:
				emptyList // quick exit
		}
	}

	/**
	 * Returns transitive, non-reflexive closure of implicit super types, delegates to
	 * {@link #collectAllImplicitSuperTypesOfType((RuleEnvironment , Type )}.
	 */
	public def static dispatch List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
		TypeRef typeRef) {

		return collectAllImplicitSuperTypes(G, typeRef, new RecursionGuard<Type>());
	}

	public def static dispatch List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
		IntersectionTypeExpression typeRef) {

		return collectAllImplicitSuperTypes(G, typeRef, new RecursionGuard<Type>());
	}

	public def static dispatch List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
		FunctionTypeExprOrRef typeRef) {

		return collectAllImplicitSuperTypes(G, typeRef, new RecursionGuard<Type>());
	}

	private def static dispatch List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
		TypeRef typeRef, RecursionGuard<Type> guard) {

		return collectAllImplicitSuperTypesOfType(G, typeRef?.declaredType, guard);
	}

	private def static dispatch List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
		IntersectionTypeExpression typeRef, RecursionGuard<Type> guard) {

		return typeRef.typeRefs.map[G.collectAllImplicitSuperTypes(it, guard)].flatten.toList
	}

	private def static dispatch List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
		FunctionTypeExprOrRef typeRef, /*unused*/ RecursionGuard<Type> guard) {

		return G.functionTypesAllImplicitSuperTypeRefs;
	}

	/** returns an iterable of the assignment-compatible types, up to now only primitives have this concept. */
	public def static Iterable<TypeRef> assignmentCompatibleTypes(RuleEnvironment G, TypeRef typeRef) {
		val declaredType = typeRef.declaredType
		switch (declaredType) {
			PrimitiveType:
				G.assignmentCompatibleTypes(declaredType)
			default:
				emptyList
		}
	}

	public def static Iterable<TypeRef> assignmentCompatibleTypes(RuleEnvironment G, PrimitiveType pt) {

		// Handling primitives with assignment compatible set.
		if (pt.assignmentCompatible !== null)
			ImmutableList.<TypeRef>of(createTypeRef(pt.assignmentCompatible))
		else {
			emptyList
		}
	}

	/**
	 * Returns unmodifiable list of type references to all function types (expressions and concrete functions):
	 * {@code Function} and {@code Object}.
	 */
	public def static getFunctionTypesAllImplicitSuperTypeRefs(RuleEnvironment G) {
		return G.getPredefinedTypes().builtInTypeScope.functionTypesAllImplicitSuperTypeRefs
	}

	/**
	 * Returns unmodifiable list of type references to all implicit super types of all built-in JavaScript object types,
	 * object literals and via constructor created elements: {@code Object}.
	 */
	public def static getObjectPrototypesAllImplicitSuperTypeRefs(RuleEnvironment G) {
		return G.getPredefinedTypes().builtInTypeScope.objectPrototypesAllImplicitSuperTypeRefs
	}

	/**
	 * Returns unmodifiable list of type references to all implicit super types of all N4 classes, roles, and interfaces,
	 * that is to {@code N4Object} and {@code Object}.
	 */
	public def static List<ParameterizedTypeRef> getN4ClassifiersAllImplicitSuperTypeRefs(RuleEnvironment G) {
		return G.getPredefinedTypes().builtInTypeScope.n4ClassifiersAllImplicitSuperTypeRefs
	}

	public def static String ruleEnvAsString(RuleEnvironment G) {
		val INDENT = "    ";
		val result = new StringBuffer
		result.append("RuleEnvironment@")
		result.append(Integer.toHexString(System.identityHashCode(G)))
		result.append(" {\n")
		result.append(G.environment.typeVariableSubstitutionsAsString(INDENT));
		if (G.next !== null)
			result.append(INDENT + ruleEnvAsString(G.next).replaceAll("\\n", "\n" + INDENT));
		result.append("}")
		return result.toString
	}

	protected def static String typeVariableSubstitutionsAsString(Map<?, ?> substitutions, String indent) {
		val pairs = newArrayList
		for (currKey : substitutions.keySet)
			pairs.add(
				indent + typeRefOrVariableAsString(currKey) + ' -> ' +
					typeRefOrVariableAsString(substitutions.get(currKey)) + '\n');
		Collections.sort(pairs);
		return pairs.join;
	}

	protected def static String typeRefOrVariableAsString(Object obj) {
		if (obj instanceof Collection<?>)
			'[ ' + obj.map[typeRefOrVariableAsString].join(', ') + ' ]'
		else if (obj instanceof TypeVariable) {
			val parent = obj.eContainer;
			if (parent instanceof IdentifiableElement)
				parent.name + '#' + obj.name
			else if (parent !== null && parent.eClass !== null)
				parent.eClass.name + '#' + obj.name
			else
				'#' + obj.name
		} else if (obj instanceof TypeRef && (obj as TypeRef).declaredType instanceof TypeVariable)
			typeRefOrVariableAsString((obj as TypeRef).declaredType)
		else if (obj instanceof TypeRef)
			obj.typeRefAsString
		else
			obj.toString
	}


	/**
	 * Check if {@code locationToCheck} is contained in the return part of {@code container}.
	 */
	public def static boolean isInReturnDeclaration_Of_StaticMethod(EObject locationToCheck,N4MethodDeclaration container) {
		if( ! container.isStatic ) return false;
		val isInReturn = EcoreUtil2.isAncestor(container.returnTypeRef,locationToCheck)
		return isInReturn;
	}

	/**
	 * Check if {@code locationToCheck} is contained in the body part of {@code container}.
	 */
	public def static boolean isInBody_Of_StaticMethod(EObject locationToCheck,N4MethodDeclaration container) {
		if( ! container.isStatic ) return false;
		val isInBody = EcoreUtil2.isAncestor(container.body,locationToCheck)
		return isInBody;
	}
}
