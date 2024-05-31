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

import static org.eclipse.n4js.types.utils.TypeUtils.createTypeRef;
import static org.eclipse.n4js.types.utils.TypeUtils.sanitizeRawTypeRef;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forall;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.builtin.GlobalObjectScope;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.typeRefs.BaseTypeRef;
import org.eclipse.n4js.ts.typeRefs.BooleanLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.NullType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.UndefinedType;
import org.eclipse.n4js.ts.types.VoidType;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

/**
 * Extensions of class RuleEnvironment for handling substitutions and retrieving build in types.
 */
public class RuleEnvironmentExtensions {

	/**
	 * Key used for storing a cancel indicator in a rule environment. Client code should not use this constant directly,
	 * but instead use methods {@link RuleEnvironmentExtensions#addCancelIndicator(RuleEnvironment,CancelIndicator)} and
	 * {@link RuleEnvironmentExtensions#getCancelIndicator(RuleEnvironment)}.
	 */
	private static final String KEY__CANCEL_INDICATOR = "cancelIndicator";

	/** Key used for storing a "this" binding in a rule environment. */
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
	 * Key for storing an ITypeReplacementProvider defining a replacement of some types by other types within the
	 * context of a rule environment. Used when dealing with replacing an API by its implementation project.
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
	/***/
	public static final String GUARD_VARIABLE_DECLARATION = "varDecl";
	/***/
	public static final String GUARD_TYPE_CALL_EXPRESSION = "typeCallExpression";
	/***/
	public static final String GUARD_TYPE_PROPERTY_ACCESS_EXPRESSION = "typePropertyAccessExpression";
	/***/
	public static final String GUARD_SUBTYPE__REPLACE_BOOLEAN_BY_UNION = "subtypeRef__replaceBooleanByUnion";
	/***/
	public static final String GUARD_SUBTYPE__REPLACE_ENUM_TYPE_BY_UNION = "subtypeRef__replaceEnumTypeByUnion";
	/***/
	public static final String GUARD_SUBTYPE_PARAMETERIZED_TYPE_REF__STRUCT = "subtypeRefParameterizedTypeRef__struct";
	/***/
	public static final String GUARD_SUBST_TYPE_VARS = "substTypeVariablesInParameterizedTypeRef";
	/***/
	public static final String GUARD_SUBST_TYPE_VARS__IMPLICIT_UPPER_BOUND_OF_WILDCARD = "substTypeVars_implicitUpperBoundOfWildcard";
	/***/
	public static final String GUARD_RESOLVE_TYPE_ALIASES_SWITCH = "ResolveTypeAliasesSwitch";
	/***/
	public static final String GUARD_NESTED_TYPE_ALIASES_IN_TYPE_ARGS = "NestedTypeAliasesInTypeArgs";
	/***/
	public static final String GUARD_STRUCTURAL_TYPING_COMPUTER__IN_PROGRESS_FOR_TYPE_REF = "StructuralTypingComputer__inProgressForTypeRef";
	/***/
	public static final String GUARD_CHECK_TYPE_ARGUMENT_COMPATIBILITY = "N4JSTypeSystem#checkTypeArgumentCompatibility";
	/***/
	public static final String GUARD_REDUCER__IS_SUBTYPE_OF = "Reducer#isSubtypeOf";
	/***/
	public static final String GUARD_REDUCER__REDUCE_STRUCTURAL_TYPE_REF = "Reducer#reduceStructuralTypeRef";
	/***/
	public static final String GUARD_TYPE_REFS_NESTED_MODIFICATION_SWITCH__MODIFY_BOUNDS_OF_WILDCARD = "TypeRefsNestedModificationSwitch#modifyBoundsOfWildcard";

	/**
	 * Returns a new {@code RuleEnvironment}; we need this because of the {@code BuiltInTypeScope} and we cannot simply
	 * create a new empty environment.
	 *
	 * @param context
	 *            must not be null!
	 */
	public static RuleEnvironment newRuleEnvironment(EObject context) {
		Resource res = context.eResource();
		if (res == null) {
			if (context instanceof BoundThisTypeRef) {
				res = ((BoundThisTypeRef) context).getActualThisTypeRef().getDeclaredType().eResource();
			}
			// maybe we can derive the resource set from other object as well..
		}

		RuleEnvironment G = new RuleEnvironment();
		if (res != null) {
			setPredefinedTypesFromObjectsResourceSet(G, res.getResourceSet());
			G.put(Resource.class, res);
		}
		return G;
	}

	/**
	 * Returns a new {@code RuleEnvironment} with a given resource to provide context information and xtext index
	 * access.
	 */
	public static RuleEnvironment newRuleEnvironment(Resource resource) {
		RuleEnvironment G = new RuleEnvironment();
		setPredefinedTypesFromObjectsResourceSet(G, resource.getResourceSet());
		G.put(Resource.class, resource);
		return G;
	}

	/**
	 * Returns a new {@code RuleEnvironment} for the same predefined types, resource, and cancel indicator as the given
	 * rule environment.
	 * <p>
	 * IMPORTANT: other key/value pairs from G will not be available in the returned rule environment! Compare this with
	 * method {@link #wrap(RuleEnvironment)}.
	 */
	public static RuleEnvironment newRuleEnvironment(RuleEnvironment G) {
		RuleEnvironment Gnew = new RuleEnvironment();
		setPredefinedTypes(Gnew, getPredefinedTypes(G));
		Gnew.put(Resource.class, G.get(Resource.class));
		addCancelIndicator(Gnew, getCancelIndicator(G));
		return Gnew;
	}

	/**
	 * Return a new rule environment wrapping the given rule environment "G", i.e. the all key/value pairs of "G" will
	 * be readable in the returned environment, but changes to the returned environment will not affect "G".
	 */
	public static RuleEnvironment wrap(RuleEnvironment G) {
		return new RuleEnvironment(G);
	}

	/***/
	public static void setPredefinedTypesFromObjectsResourceSet(RuleEnvironment G, ResourceSet resourceSet) {
		if (resourceSet == null) {
			throw new IllegalArgumentException(
					"Resource set used to load predefined types must not be null at org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.setPredefinedTypesFromObjectsResourceSet(RuleEnvironment, ResourceSet)");
		}
		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(resourceSet);
		GlobalObjectScope globalObjectTypeScope = GlobalObjectScope.get(resourceSet);
		G.put(PredefinedTypes.PREDEFINED_TYPES_KEY,
				new PredefinedTypes(builtInTypeScope, globalObjectTypeScope));
	}

	/***/
	public static void setPredefinedTypes(RuleEnvironment G, PredefinedTypes predefinedTypes) {
		G.put(PredefinedTypes.PREDEFINED_TYPES_KEY, predefinedTypes);
	}

	/***/
	public static PredefinedTypes getPredefinedTypes(RuleEnvironment G) {
		PredefinedTypes predefinedTypes = (PredefinedTypes) G.get(PredefinedTypes.PREDEFINED_TYPES_KEY);
		if (predefinedTypes == null) {
			throw new IllegalStateException(
					"Predefined types not set, call type system with configured rule environment");
		}
		return predefinedTypes;
	}

	/**
	 * Convenience method returning the {@link BuiltInTypeScope} via this rule environment"s {@link PredefinedTypes}.
	 */
	public static BuiltInTypeScope getBuiltInTypeScope(RuleEnvironment G) {
		if (G == null) {
			return null;
		}
		PredefinedTypes predefinedTypes = getPredefinedTypes(G);
		if (predefinedTypes == null) {
			return null;
		}
		return predefinedTypes.builtInTypeScope;
	}

	/**
	 * Convenience method returning the {@link GlobalObjectScope} via this rule environment"s {@link PredefinedTypes}.
	 */
	public static GlobalObjectScope getGlobalObjectScope(RuleEnvironment G) {
		if (G == null) {
			return null;
		}
		PredefinedTypes predefinedTypes = getPredefinedTypes(G);
		if (predefinedTypes == null) {
			return null;
		}
		return predefinedTypes.globalObjectScope;
	}

	/**
	 * Returns the resource used to load built-in types and to resolve proxies. This is the resource of the object
	 */
	public static Resource getContextResource(RuleEnvironment G) {
		return (Resource) G.get(Resource.class);
	}

	/**
	 * Add a cancel indicator to the given rule environment.
	 */
	public static void addCancelIndicator(RuleEnvironment G, CancelIndicator cancelIndicator) {
		G.put(KEY__CANCEL_INDICATOR, cancelIndicator);
	}

	/**
	 * Returns the cancel indicator of this rule environment or <code>null</code> if none has been added, yet.
	 */
	public static CancelIndicator getCancelIndicator(RuleEnvironment G) {
		return (CancelIndicator) G.get(KEY__CANCEL_INDICATOR);
	}

	/**
	 * <b>IMPORTANT:</b><br>
	 * use this only for rare special cases (e.g. logging); ordinary cancellation handling should be done by invoking
	 * {@link OperationCanceledManager#checkCanceled(CancelIndicator)} with the cancel indicator returned by
	 * {@link #getCancelIndicator(RuleEnvironment)}!
	 * <p>
	 * Tells if the given rule environment has a cancel indicator AND that indicator is canceled.
	 */
	public static boolean isCanceled(RuleEnvironment G) {
		CancelIndicator cancelIndicator = getCancelIndicator(G);
		return cancelIndicator != null && cancelIndicator.isCanceled();
	}

	/**
	 * Adds the actual this type to the rule environment if the actual this type is either a ParameterizedTypeRef or a
	 * BoundThisTypeRef. The latter case happens if the receiver of a function call is a function call itself, returning
	 * a this type.
	 */
	public static void setThisBinding(RuleEnvironment G, TypeRef actualThisTypeRef) {
		if (actualThisTypeRef instanceof TypeTypeRef) {
			TypeTypeRef ttr = (TypeTypeRef) actualThisTypeRef;
			// IDE-785 decompose
			if (ttr.getTypeArg() instanceof TypeRef) {
				TypeRef typeArg = TypeUtils.copy((TypeRef) ttr.getTypeArg());
				sanitizeRawTypeRef(typeArg);
				setThisBinding(G, typeArg);
			}
		}
		if (actualThisTypeRef instanceof ParameterizedTypeRef) {
			G.put(KEY__THIS_BINDING, TypeUtils.createBoundThisTypeRef((ParameterizedTypeRef) actualThisTypeRef));
		}
		if (actualThisTypeRef instanceof BoundThisTypeRef) {
			G.put(KEY__THIS_BINDING, actualThisTypeRef);
		}
	}

	/** Returns the current this type */
	public static TypeRef getThisBinding(RuleEnvironment G) {
		return (TypeRef) G.get(KEY__THIS_BINDING);
	}

	/**
	 * Turn on recording of inconsistent substitutions in Xsemantics judgment {@code substTypeVariables}. This is used
	 * by a validation which will then produce a corresponding error.
	 */
	public static void recordInconsistentSubstitutions(RuleEnvironment G) {
		G.put(KEY__INCONSISTENT_SUBSTITUTIONS, ArrayListMultimap.<TypeVariable, TypeRef> create());
	}

	/**
	 * Iff recording of inconsistent substitutions has been turned on for the given rule environment (see method
	 * {@link #recordInconsistentSubstitutions(RuleEnvironment)}), then this method will store such substitutions in the
	 * given rule environment.
	 */
	public static void addInconsistentSubstitutions(RuleEnvironment G, TypeVariable typeVar,
			Collection<? extends TypeRef> substitutions) {
		@SuppressWarnings("unchecked")
		ListMultimap<TypeVariable, TypeRef> storage = (ListMultimap<TypeVariable, TypeRef>) G
				.get(KEY__INCONSISTENT_SUBSTITUTIONS);
		if (storage != null) {
			storage.putAll(typeVar, substitutions);
		}
	}

	/**
	 * Iff recording of inconsistent substitutions has been turned on for the given rule environment (see method
	 * {@link #recordInconsistentSubstitutions(RuleEnvironment)}), then this method will return those substitutions.
	 * Otherwise, an empty list is returned.
	 */
	public static List<TypeRef> getInconsistentSubstitutions(RuleEnvironment G, TypeVariable typeVar) {
		@SuppressWarnings("unchecked")
		ListMultimap<TypeVariable, TypeRef> storage = (ListMultimap<TypeVariable, TypeRef>) G
				.get(KEY__INCONSISTENT_SUBSTITUTIONS);
		return (storage != null) ? storage.get(typeVar) : Collections.emptyList();
	}

	/***/
	public static boolean hasReplacements(RuleEnvironment G) {
		return G.get(KEY__TYPE_REPLACEMENT) != null;
	}

	/***/
	public static void setTypeReplacement(RuleEnvironment G, ITypeReplacementProvider replacementProvider) {
		G.put(KEY__TYPE_REPLACEMENT, replacementProvider);
	}

	/***/
	public static TypeRef getReplacement(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof ParameterizedTypeRef) {
			if (!(typeRef instanceof FunctionTypeRef)) {
				Type type = typeRef.getDeclaredType();
				Type replacement = getReplacement(G, type);
				if (replacement != type) { // identity compare is ok here
					// do not resolve proxies
					ParameterizedTypeRef cpy = TypeUtils.copyWithProxies((ParameterizedTypeRef) typeRef);
					cpy.setDeclaredType(replacement);
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

	/***/
	public static <T extends Type> T getReplacement(RuleEnvironment G, T type) {
		ITypeReplacementProvider replacementProvider = (ITypeReplacementProvider) G.get(KEY__TYPE_REPLACEMENT);
		T replacement = replacementProvider == null ? null : replacementProvider.getReplacement(type);
		return (replacement != null) ? replacement : type;
	}

	/**
	 * The {@link ExistentialTypeRef}s with the given IDs will *not* be reopened by method
	 * {@link N4JSTypeSystem#upperBoundWithReopen(RuleEnvironment, TypeArgument)}, and similar methods.
	 */
	public static void addFixedCapture(RuleEnvironment G, ExistentialTypeRef etr) {
		String id = etr.getId();
		if (id != null) {
			G.put(Pair.of(KEY__FIXED_CAPTURE_ID, id), Boolean.TRUE);
		}
	}

	/**
	 * Tells whether the given {@link ExistentialTypeRef}s has an ID that was registered via
	 * {@link #addFixedCapture(RuleEnvironment, ExistentialTypeRef)} to avoid it to be reopened by method
	 * {@link N4JSTypeSystem#upperBoundWithReopen(RuleEnvironment, TypeArgument)}, and similar methods.
	 */
	public static boolean isFixedCapture(RuleEnvironment G, ExistentialTypeRef etr) {
		return G.get(Pair.of(KEY__FIXED_CAPTURE_ID, etr.getId())) != null;
	}

	/***/
	public static TypeRef createTypeRefFromUpperBound(TypeVariable typeVar) {
		return TypeUtils.copyIfContained(typeVar.getDeclaredUpperBound());
	}

	/** Returns the top type (which is currently "any" but may change in the future). */
	public static AnyType topType(RuleEnvironment G) {
		return anyType(G);
	}

	/** Returns newly created reference to the top type (which is currently "any" but may change in the future). */
	public static ParameterizedTypeRef topTypeRef(RuleEnvironment G) {
		return anyTypeRef(G);
	}

	/** Returns the bottom type (which is currently "undefined" but may change in the future). */
	public static UndefinedType bottomType(RuleEnvironment G) {
		return undefinedType(G);
	}

	/**
	 * Returns newly created reference to the bottom type (which is currently "undefined" but may change in the future).
	 */
	public static ParameterizedTypeRef bottomTypeRef(RuleEnvironment G) {
		return undefinedTypeRef(G);
	}

	/** Returns built-in type {@code boolean} */
	public static PrimitiveType booleanType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getBooleanType();
	}

	/** Returns newly created reference to built-in type {@code boolean} */
	public static ParameterizedTypeRef booleanTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(booleanType(G));
	}

	/** Returns built-in type {@code string} */
	public static PrimitiveType stringType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getStringType();
	}

	/** Returns newly created reference to built-in type {@code string} */
	public static ParameterizedTypeRef stringTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(stringType(G));
	}

	/** Returns built-in object type {@code String} */
	public static TClass stringObjectType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getStringObjectType();
	}

	/** Returns newly created reference to built-in object type {@code String} */
	public static ParameterizedTypeRef stringObjectTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(stringObjectType(G));
	}

	/** Returns built-in type {@code number} */
	public static PrimitiveType numberType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getNumberType();
	}

	/** Returns newly created reference to built-in type {@code number} */
	public static ParameterizedTypeRef numberTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(numberType(G));
	}

	/** Returns built-in object type {@code Number} */
	public static TClass numberObjectType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getNumberObjectType();
	}

	/** Returns newly created reference to built-in object type {@code Number} */
	public static ParameterizedTypeRef numberObjectTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(numberObjectType(G));
	}

	/** Returns built-in type {@code int} */
	public static PrimitiveType intType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getIntType();
	}

	/** Returns newly created reference to built-in type {@code int} */
	public static ParameterizedTypeRef intTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(intType(G));
	}

	/** Returns built-in type {@code symbol} */
	public static Type symbolType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getSymbolType();
	}

	/** Returns newly created reference to built-in type {@code symbol} */
	public static ParameterizedTypeRef symbolTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(symbolType(G));
	}

	/** Returns built-in object type {@code Symbol} */
	public static TClass symbolObjectType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getSymbolObjectType();
	}

	/** Returns newly created reference to built-in object type {@code Symbol} */
	public static ParameterizedTypeRef symbolObjectTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(symbolObjectType(G));
	}

	/** Returns built-in type {@code any} */
	public static AnyType anyType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getAnyType();
	}

	/** Returns newly created reference to built-in type {@code any} */
	public static ParameterizedTypeRef anyTypeRef(RuleEnvironment G) {
		return anyTypeRef(G, false);
	}

	/**
	 * Returns newly created dynamic reference to built-in type {@code any}, that is {@code any+}. This is the default
	 * type used in JavaScript modes.
	 */
	public static ParameterizedTypeRef anyTypeRefDynamic(RuleEnvironment G) {
		return anyTypeRef(G, true);
	}

	/** Returns newly created reference to built-in type {@code any} */
	public static ParameterizedTypeRef anyTypeRef(RuleEnvironment G, boolean dynamic) {
		ParameterizedTypeRef result = getPredefinedTypes(G).builtInTypeScope.getAnyTypeRef();
		result.setDynamic(dynamic);
		return result;
	}

	/** Returns newly created reference to built-in type {@code null} */
	public static ParameterizedTypeRef nullTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(nullType(G));
	}

	/** Returns built-in type {@code undefined} */
	public static UndefinedType undefinedType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getUndefinedType();
	}

	/** Returns built-in type {@code null} */
	public static NullType nullType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getNullType();
	}

	/** Returns newly created reference to built-in type {@code undefined} */
	public static ParameterizedTypeRef undefinedTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(undefinedType(G));
	}

	/** Returns built-in type {@code void} */
	public static VoidType voidType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getVoidType();
	}

	/** Returns newly created reference to built-in type {@code void} */
	public static ParameterizedTypeRef voidTypeRef(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getVoidTypeRef();
	}

	/** Returns built-in type {@code RegExp} */
	public static TClass regexpType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getRegexpType();
	}

	/** Returns newly created reference to built-in type {@code RegExp} */
	public static ParameterizedTypeRef regexpTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(regexpType(G));
	}

	/** Returns built-in type {@code Array<T>} */
	public static TClass arrayType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getArrayType();
	}

	/** Returns newly created reference to built-in type {@code Array<T>} */
	public static ParameterizedTypeRef arrayTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		return TypeUtils.createTypeRef(arrayType(G), typeArgs);
	}

	/** Returns built-in type {@code Object} */
	public static TClassifier objectType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getObjectType();
	}

	/** Returns newly created reference to built-in type {@code Object} */
	public static ParameterizedTypeRef objectTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(objectType(G));
	}

	/** Returns newly created reference to built-in type {@code Object} */
	public static ParameterizedTypeRef structuralObjectTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(objectType(G), TypingStrategy.STRUCTURAL);
	}

	/** Returns newly created reference to built-in global object type */
	public static TClass globalObjectType(RuleEnvironment G) {
		return getPredefinedTypes(G).globalObjectScope.getGlobalObject();
	}

	/** Returns newly created reference to built-in global object type */
	public static ParameterizedTypeRef globalObjectTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(globalObjectType(G));
	}

	/** Returns built-in type {@code Function} */
	public static TClass functionType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getFunctionType();
	}

	/** Returns newly created structural reference to built-in type {@code Function} */
	public static ParameterizedTypeRef structuralFunctionTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(functionType(G), TypingStrategy.STRUCTURAL);
	}

	/** Returns newly created reference to built-in type {@code Function} */
	public static ParameterizedTypeRef functionTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(functionType(G));
	}

	/** Returns built-in type {@code IArguments} */
	public static TInterface argumentsType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getArgumentsType();
	}

	/** Returns newly created reference to built-in type {@code IArguments} */
	public static ParameterizedTypeRef argumentsTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(argumentsType(G));
	}

	/** Returns built-in type {@code N4Object} */
	public static TClass n4ObjectType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getN4ObjectType();
	}

	/** Returns newly created reference to built-in type {@code N4Object} */
	public static ParameterizedTypeRef n4ObjectTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(n4ObjectType(G));
	}

	/** Returns built-in type {@code N4Enum} */
	public static TClass n4EnumType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getN4EnumType();
	}

	/** Returns newly created reference to built-in type {@code N4Enum} */
	public static ParameterizedTypeRef n4EnumTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(n4EnumType(G));
	}

	/** Returns built-in type {@code N4NumberBasedEnum} */
	public static TClass n4NumberBasedEnumType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getN4NumberBasedEnumType();
	}

	/** Returns a newly created reference to the built-in type {@code N4NumberBasedEnum} */
	public static ParameterizedTypeRef n4NumberBasedEnumTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(n4NumberBasedEnumType(G));
	}

	/** Returns built-in type {@code N4StringBasedEnum} */
	public static TClass n4StringBasedEnumType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getN4StringBasedEnumType();
	}

	/** Returns a newly created reference to the built-in type {@code N4StringBasedEnum} */
	public static ParameterizedTypeRef n4StringBasedEnumTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(n4StringBasedEnumType(G));
	}

	/** Returns built-in type {@code i18nKey} */
	public static PrimitiveType i18nKeyType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getI18nKeyType();
	}

	/** Returns built-in type {@code pathSelector} */
	public static PrimitiveType pathSelectorType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getPathSelectorType();
	}

	/** Returns built-in type {@code typeName} */
	public static PrimitiveType typeNameType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getTypeNameType();
	}

	/** Returns built-in type {@code N4Provider} */
	public static TInterface n4ProviderType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getN4ProviderType();
	}

	/** Returns built-in type {@code Error}. */
	public static TClass errorType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getErrorType();
	}

	/** Returns newly created reference to built-in type {@code Error}. */
	public static ParameterizedTypeRef errorTypeRef(RuleEnvironment G) {
		return TypeUtils.createTypeRef(errorType(G));
	}

	/** Returns built-in type {@code Iterator<T>} */
	public static TInterface iteratorType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getIteratorType();
	}

	/** Returns newly created reference to built-in type {@code Iterator<T>} */
	public static ParameterizedTypeRef iteratorTypeRef(RuleEnvironment G, TypeArgument typeArg) {
		return createTypeRef(iteratorType(G), typeArg);
	}

	/** Returns built-in type {@code IteratorEntry<T>} */
	public static TInterface iteratorEntryType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getIteratorEntryType();
	}

	/** Returns newly created reference to built-in type {@code IteratorEntry<T>} */
	public static ParameterizedTypeRef iteratorEntryTypeRef(RuleEnvironment G, TypeArgument typeArg) {
		return createTypeRef(iteratorEntryType(G), typeArg);
	}

	/** Returns built-in type {@code Iterable<T>} */
	public static TInterface iterableType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getIterableType();
	}

	/** Returns newly created reference to built-in type {@code Iterable<T>} */
	public static ParameterizedTypeRef iterableTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		return createTypeRef(iterableType(G), typeArgs);
	}

	/** Returns built-in type {@code AsyncIterable<T>} */
	public static TInterface asyncIterableType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getAsyncIterableType();
	}

	/** Returns newly created reference to built-in type {@code AsyncIterable<T>} */
	public static ParameterizedTypeRef asyncIterableTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		return createTypeRef(asyncIterableType(G), typeArgs);
	}

	/** Returns built-in type {@code IterableN<T1...TN>} */
	public static Type iterableNType(RuleEnvironment G, int n) {
		return getPredefinedTypes(G).builtInTypeScope.getIterableNType(n);
	}

	/** Returns newly created reference to built-in type {@code IterableN<T1...TN>} */
	public static ParameterizedTypeRef iterableNTypeRef(RuleEnvironment G, int n, TypeArgument... typeArgs) {
		return createTypeRef(iterableNType(G, n), typeArgs);
	}

	/** Returns built-in type {@code IterableN<T1...TN>} */
	public static Set<TInterface> iterableNTypes(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getIterableNTypes();
	}

	/**
	 * Returns true iff <code>obj</code> is a {@link Type} or {@link TypeRef} and is or points to one of the
	 * <code>IterableN&lt;...></code> built-in types. Does <b>not</b> check for the built-in type
	 * <code>Iterable&lt;T></code>.
	 */
	public static boolean isIterableN(RuleEnvironment G, EObject obj) {
		if (obj instanceof Type) {
			return isIterableN(G, (Type) obj);
		}
		if (obj instanceof TypeRef) {
			return isIterableN(G, ((TypeRef) obj).getDeclaredType());
		}
		return false;
	}

	/***/
	public static boolean isIterableN(RuleEnvironment G, Type type) {
		return type != null && iterableNTypes(G).contains(type);
	}

	/** Returns built-in type {@code ArrayN<T1...TN>} */
	public static Type arrayNType(RuleEnvironment G, int n) {
		return getPredefinedTypes(G).builtInTypeScope.getArrayNType(n);
	}

	/** Returns newly created reference to built-in type {@code ArrayN<T1...TN>} */
	public static ParameterizedTypeRef arrayNTypeRef(RuleEnvironment G, int n, TypeArgument... typeArgs) {
		return createTypeRef(arrayNType(G, n), typeArgs);
	}

	/** Returns built-in type {@code ArrayN<T1...TN>} */
	public static Set<TClass> arrayNTypes(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getArrayNTypes();
	}

	/**
	 * Returns true iff <code>obj</code> is a {@link Type} or {@link TypeRef} and is or points to one of the
	 * <code>Array&lt;T></code> built-in type. Does <b>not</b> check for the built-in types <code>ArrayN&lt;...></code>.
	 */
	public static boolean isArray(RuleEnvironment G, EObject obj) {
		if (obj instanceof Type) {
			return isArray(G, (Type) obj);
		}
		if (obj instanceof TypeRef) {
			return isArray(G, ((TypeRef) obj).getDeclaredType());
		}
		return false;
	}

	/***/
	public static boolean isArray(RuleEnvironment G, Type type) {
		return type != null && arrayType(G) == type;
	}

	/**
	 * Returns true iff <code>obj</code> is a {@link Type} or {@link TypeRef} and is or points to one of the
	 * <code>ArrayN&lt;...></code> built-in types. Does <b>not</b> check for the built-in type <code>Array&lt;T></code>.
	 */
	public static boolean isArrayN(RuleEnvironment G, EObject obj) {
		if (obj instanceof Type) {
			return isArrayN(G, (Type) obj);
		}
		if (obj instanceof TypeRef) {
			return isArrayN(G, ((TypeRef) obj).getDeclaredType());
		}
		return false;
	}

	/***/
	public static boolean isArrayN(RuleEnvironment G, Type type) {
		return type != null && arrayNTypes(G).contains(type);
	}

	/***/
	public static int getArrayNNumber(RuleEnvironment G, Type type) {
		if (!isArrayN(G, type)) {
			return -1;
		}
		return Integer.parseInt(type.getName().substring("Array".length()));
	}

	/** Returns built-in type {@code Promise<S,F>} */
	public static TClass promiseType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getPromiseType();
	}

	/** Returns newly created reference to built-in type {@code Promise<S,F>} */
	public static ParameterizedTypeRef promiseTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		return createTypeRef(promiseType(G), typeArgs);
	}

	/** Returns built-in type {@code Generator<Y,R,N>} */
	public static TInterface generatorType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getGeneratorType();
	}

	/** Returns newly created reference to built-in type {@code Generator<Y,R,N>} */
	public static ParameterizedTypeRef generatorTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		return createTypeRef(generatorType(G), typeArgs);
	}

	/** Returns built-in type {@code AsyncGenerator<Y,R,N>} */
	public static TInterface asyncGeneratorType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getAsyncGeneratorType();
	}

	/** Returns newly created reference to built-in type {@code AsyncGenerator<Y,R,N>} */
	public static ParameterizedTypeRef asyncGeneratorTypeRef(RuleEnvironment G, TypeArgument... typeArgs) {
		return createTypeRef(asyncGeneratorType(G), typeArgs);
	}

	/** Returns built-in type {@code N4Element} */
	public static TClass n4ElementType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getN4ElementType();
	}

	/** Returns built-in type {@code N4NamedElement} */
	public static TClass n4NamedElementType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getN4NamedElementType();
	}

	/** Returns built-in type {@code N4Type} */
	public static TClass n4TypeType(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getN4TypeType();
	}

	/**
	 * Returns true if the given type is any.
	 */
	public static boolean isAny(RuleEnvironment G, TypeArgument typeArg) {
		return typeArg != null && typeArg.getDeclaredType() == anyType(G) && isNominal(typeArg);
	}

	/**
	 * Returns true if the given type is any+.
	 */
	public static boolean isAnyDynamic(RuleEnvironment G, TypeArgument typeArg) {
		return isAny(G, typeArg) && typeArg instanceof BaseTypeRef && ((BaseTypeRef) typeArg).isDynamic();
	}

	/**
	 * Returns true if the given type reference refers to the built-in type {@link #objectType(RuleEnvironment) Object}.
	 */
	public static boolean isObject(RuleEnvironment G, TypeArgument typeArg) {
		return typeArg != null && typeArg.getDeclaredType() == objectType(G) && isNominal(typeArg);
	}

	/**
	 * Returns true if the given type reference refers to the built-in type {@link #objectType(RuleEnvironment) Object}.
	 */
	public static boolean isObjectStructural(RuleEnvironment G, TypeArgument typeArg) {
		return typeArg != null && typeArg.getDeclaredType() == objectType(G) && isStructural(typeArg);
	}

	/**
	 * Returns true if the given type reference refers to the built-in type {@link #functionType(RuleEnvironment)
	 * Function}.
	 */
	public static boolean isFunction(RuleEnvironment G, TypeArgument typeArg) {
		return typeArg != null && typeArg.getDeclaredType() == functionType(G) && isNominal(typeArg);
	}

	/**
	 * Returns true if the given type is symbol.
	 */
	public static boolean isSymbol(RuleEnvironment G, TypeArgument typeArg) {
		return typeArg != null && typeArg.getDeclaredType() == symbolType(G) && isNominal(typeArg);
	}

	private static boolean isNominal(TypeArgument typeArg) {
		return typeArg.isTypeRef() && !TypeUtils.isStructural((TypeRef) typeArg);
	}

	private static boolean isStructural(TypeArgument typeArg) {
		return typeArg.isTypeRef() && TypeUtils.isStructural((TypeRef) typeArg);
	}

	/**
	 * Returns true iff the given type is of Boolean type, i.e {@link #booleanType(RuleEnvironment) boolean}.
	 */
	public static boolean isBoolean(RuleEnvironment G, Type type) {
		return getPredefinedTypes(G).builtInTypeScope.isBoolean(type);
	}

	/**
	 * Returns true iff the given type is of Boolean type, i.e {@link #booleanType(RuleEnvironment) boolean}.
	 */
	public static boolean isBoolean(RuleEnvironment G, TypeArgument typeArg) {
		if (typeArg == null) {
			return false;
		}
		if (getPredefinedTypes(G).builtInTypeScope.isBoolean(typeArg.getDeclaredType())) {
			return true;
		}
		if (typeArg instanceof UnionTypeExpression) {
			return forall(((UnionTypeExpression) typeArg).getTypeRefs(), e -> isBoolean(G, e));
		}
		if (typeArg instanceof IntersectionTypeExpression) {
			return exists(((IntersectionTypeExpression) typeArg).getTypeRefs(), e -> isBoolean(G, e));
		}
		return false;
	}

	/**
	 * Returns true iff the given type is of String type, i.e {@link #stringType(RuleEnvironment) string}.
	 */
	public static boolean isString(RuleEnvironment G, Type type) {
		return getPredefinedTypes(G).builtInTypeScope.isString(type);
	}

	/**
	 * Returns true iff the given type is of String type, i.e {@link #stringType(RuleEnvironment) string}.
	 */
	public static boolean isString(RuleEnvironment G, TypeArgument typeArg) {
		if (typeArg == null) {
			return false;
		}
		if (getPredefinedTypes(G).builtInTypeScope.isString(typeArg.getDeclaredType())) {
			return true;
		}
		if (typeArg instanceof UnionTypeExpression) {
			return forall(((UnionTypeExpression) typeArg).getTypeRefs(), e -> isString(G, e));
		}
		if (typeArg instanceof IntersectionTypeExpression) {
			return exists(((IntersectionTypeExpression) typeArg).getTypeRefs(), e -> isString(G, e));
		}
		return false;
	}

	/**
	 * Returns true if the given type is one of the {@link BuiltInTypeScope#isNumeric(Type) numeric} primitive built-in
	 * types.
	 */
	public static boolean isNumeric(RuleEnvironment G, Type type) {
		return getPredefinedTypes(G).builtInTypeScope.isNumeric(type);
	}

	/**
	 * Returns true if the given type reference points to one of the {@link BuiltInTypeScope#isNumeric(Type) numeric}
	 * primitive built-in types.
	 */
	public static boolean isNumeric(RuleEnvironment G, TypeArgument typeArg) {
		if (typeArg == null) {
			return false;
		}
		if (getPredefinedTypes(G).builtInTypeScope.isNumeric(typeArg.getDeclaredType())) {
			return true;
		}
		if (typeArg instanceof UnionTypeExpression) {
			return forall(((UnionTypeExpression) typeArg).getTypeRefs(), e -> isNumeric(G, e));
		}
		if (typeArg instanceof IntersectionTypeExpression) {
			return exists(((IntersectionTypeExpression) typeArg).getTypeRefs(), e -> isNumeric(G, e));
		}
		return false;
	}

	/**
	 * Returns true iff typeRef is a union type and one if its elements is numeric, boolean, null or undefined or
	 * contains one of these types. Note that this method returns false for number types -- the typeref needs to be a
	 * union type!
	 */
	public static boolean containsNumericOperand(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof UnionTypeExpression) {
			return exists(((UnionTypeExpression) typeRef).getTypeRefs(),
					e -> getPredefinedTypes(G).builtInTypeScope.isNumericOperand(e.getDeclaredType())
							|| containsNumericOperand(G, e));
		}
		return false;
	}

	/**
	 * Returns true if the given type reference can be used in a numeric operation as operand leading to a numeric
	 * result. This is true for number, int, boolean, null, or even undefined, for unions of these types, and for
	 * intersections containing any of these types.
	 */
	public static boolean isNumericOperand(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef == null) {
			return false;
		}
		if (typeRef instanceof BooleanLiteralTypeRef
				|| typeRef instanceof NumericLiteralTypeRef) {
			return true;
		}
		if (getPredefinedTypes(G).builtInTypeScope.isNumericOperand(typeRef.getDeclaredType())) {
			return true;
		}
		if (typeRef instanceof UnionTypeExpression) {
			return forall(((UnionTypeExpression) typeRef).getTypeRefs(), e -> isNumericOperand(G, e));
		}
		if (typeRef instanceof IntersectionTypeExpression) {
			return exists(((IntersectionTypeExpression) typeRef).getTypeRefs(), e -> isNumericOperand(G, e));
		}
		if (typeRef instanceof EnumLiteralTypeRef) {
			TEnumLiteral value = ((EnumLiteralTypeRef) typeRef).getValue();
			return value == null ? false : value.getValueNumber() != null;
		}
		return false;
	}

	/**
	 * Same as {@link TypeUtils#wrapTypeInTypeRef(BuiltInTypeScope, Type, TypeArgument...)}, but will obtain the
	 * required {@code BuiltInTypeScope} from the given rule environment.
	 */
	public static TypeRef wrapTypeInTypeRef(RuleEnvironment G, Type type, TypeArgument... typeArgs) {
		return TypeUtils.wrapTypeInTypeRef(getPredefinedTypes(G).builtInTypeScope, type, typeArgs);
	}

	/**
	 * Returns all type variables for which a type mapping is defined in the given rule environment.
	 */
	public static Set<TypeVariable> getTypeMappingKeys(RuleEnvironment G) {
		Set<TypeVariable> result = new LinkedHashSet<>();
		RuleEnvironment env = G;
		while (env != null) {
			result.addAll(toList(filter(env.getEnvironment().keySet(), TypeVariable.class)));
			env = env.getNext();
		}
		return result;
	}

	/**
	 * Convenience method. Same as {@link #addTypeMapping(RuleEnvironment,TypeVariable,TypeArgument)}, but for adding
	 * several mappings.
	 */
	public static void addTypeMappings(RuleEnvironment G, List<? extends TypeVariable> keys,
			List<? extends TypeArgument> values) {
		if (keys == null || values == null) {
			return;
		}
		int size = Math.min(keys.size(), values.size());
		for (int idx = 0; idx < size; idx++) {
			addTypeMapping(G, keys.get(idx), values.get(idx));
		}
	}

	/**
	 * Low-level method for adding a type variable -> type argument mapping to a rule environment. Use this method only
	 * if you know the exact mapping and you do not need support for handling existing mappings.
	 * <p>
	 * An existing mapping for type variable "key" will be overwritten. If the given mapping is invalid, i.e.
	 * {@link #isValidTypeMapping(RuleEnvironment,TypeVariable,TypeArgument) isValidMapping()} returns false, then this
	 * method will do nothing.
	 */
	public static void addTypeMapping(RuleEnvironment G, TypeVariable key, TypeArgument value) {
		// ignore invalid type mappings
		if (!isValidTypeMapping(G, key, value)) {
			return;
		}
		G.put(key, value);
	}

	/**
	 * Checks if rule environment G defines an actual, i.e. non-reflexive, type variable substitution for
	 * <code>typeVariable</code>. Argument <code>typeVariable</code> may either be a {@link TypeVariable} itself or a
	 * {@link TypeRef} with a type variable as its declared type.
	 * <p>
	 * For convenience, this methods takes arguments of any type but will always return <code>false</code> if the
	 * argument is neither an instance of {@link TypeVariable} nor an instance of {@link TypeRef} with a declared type
	 * that is an instance of {@link TypeVariable}.
	 */
	public static boolean hasSubstitutionFor(RuleEnvironment G, Object typeVariable) {
		Object key = (typeVariable instanceof TypeRef) ? ((TypeRef) typeVariable).getDeclaredType() : typeVariable;
		if (key instanceof TypeVariable) {
			Object value = G.get(key);
			return value != null && !(value instanceof TypeRef && ((TypeRef) value).getDeclaredType() == key);
		}
		return false;
	}

	/***/
	public static boolean isValidTypeMapping(@SuppressWarnings("unused") RuleEnvironment G, TypeVariable key,
			TypeArgument value) {
		// ignore reflexive mappings, e.g. T -> T, T -> G<T>, etc.
		if (TypeUtils.isOrContainsRefToTypeVar(value, key)) {
			return false;
		}
		// ignore DeferredTypeRefs
		if (value instanceof DeferredTypeRef) {
			return false;
		}
		// ignore void (type "void" is never a valid substitution for a type variable)
		if (value instanceof ParameterizedTypeRef) {
			if (((ParameterizedTypeRef) value).getDeclaredType() instanceof VoidType) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the declared or implicit super type of a class. This might be a TClassifier or, in case of implicit super
	 * types and external classes, a TClass (i.e. "Object").
	 */
	public static TClassifier getDeclaredOrImplicitSuperType(RuleEnvironment G, TClass tClass) {
		// this method is called by validator, AST and type model may be corrupt
		// thus the super type maybe not a classifier.
		if (tClass.getSuperClassRef() != null && tClass.getSuperClassRef().getDeclaredType() instanceof TClassifier) {
			return (TClassifier) tClass.getSuperClassRef().getDeclaredType();
		}
		if (tClass.isExternal()) {
			return objectType(G);
		}
		return n4ObjectType(G);
	}

	/**
	 * Returns transitive, non-reflexive closure of implicit super types. All implicit super types are non-generic, so
	 * type arguments can be ignored here savely.
	 */
	public static List<ParameterizedTypeRef> collectAllImplicitSuperTypesOfType(RuleEnvironment G, Type declaredType) {
		return collectAllImplicitSuperTypesOfType(G, declaredType, new RecursionGuard<Type>());
	}

	private static List<ParameterizedTypeRef> collectAllImplicitSuperTypesOfType(RuleEnvironment G, Type declaredType,
			RecursionGuard<Type> guard) {
		// Type argument is null.
		if (null == declaredType) {
			return Collections.emptyList();
		}

		if (!guard.tryNext(declaredType)) {
			if (declaredType instanceof TClass) {
				TClass tc = (TClass) declaredType;
				if (declaredType == n4ObjectType(G) || (tc.isExternal() && tc.isDeclaredEcmaScript())) {
					return getBuiltInTypesAllImplicitSuperTypeRefs(G);
				} else {
					return getN4ClassifiersAllImplicitSuperTypeRefs(G);
				}
			} else {
				return Collections.emptyList(); // recursion can happen only in case of TClasses.
			}
		}

		if (declaredType instanceof TClass) {
			TClass tc = (TClass) declaredType;

			if (tc == objectType(G)) {
				return Collections.emptyList();
			} else if ((tc.isExternal() && tc.isDeclaredEcmaScript())
					|| N4Scheme.isFromResourceWithN4Scheme(tc)
					|| tc == n4ObjectType(G)) {

				return getBuiltInTypesAllImplicitSuperTypeRefs(G);
			} else {
				if (tc.getSuperClassRef() == null) {
					return getN4ClassifiersAllImplicitSuperTypeRefs(G);
				} else {
					return collectAllImplicitSuperTypes(G, tc.getSuperClassRef(), guard);
				}
			}

		} else if (declaredType instanceof TInterface) {
			TInterface ti = (TInterface) declaredType;
			if (ti.getTypingStrategy() == TypingStrategy.STRUCTURAL) {
				return getBuiltInTypesAllImplicitSuperTypeRefs(G);
			} else {
				return getN4ClassifiersAllImplicitSuperTypeRefs(G);
			}
		} else if (declaredType instanceof TN4Classifier) {
			return getN4ClassifiersAllImplicitSuperTypeRefs(G);

		} else if (declaredType instanceof TEnum) {
			switch (N4JSLanguageUtils.getEnumKind((TEnum) declaredType)) {
			case Normal:
				return List.of(objectTypeRef(G));
			case NumberBased:
				return List.of(n4NumberBasedEnumTypeRef(G) /* , G.numberTypeRef */);
			case StringBased:
				return List.of(n4StringBasedEnumTypeRef(G) /* , G.stringTypeRef */);
			}
		}

		return Collections.emptyList(); // quick exit
	}

	/**
	 * Returns transitive, non-reflexive closure of implicit super types, delegates to
	 * {@link #collectAllImplicitSuperTypesOfType( RuleEnvironment , Type )}.
	 */
	public static List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
			TypeRef typeRef) {

		return collectAllImplicitSuperTypes(G, typeRef, new RecursionGuard<Type>());
	}

	private static List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
			TypeRef typeRef, RecursionGuard<Type> guard) {

		if (typeRef instanceof IntersectionTypeExpression) {
			return collectAllImplicitSuperTypes(G, (IntersectionTypeExpression) typeRef, guard);
		} else if (typeRef instanceof FunctionTypeExprOrRef) {
			return collectAllImplicitSuperTypes(G, (FunctionTypeExprOrRef) typeRef, guard);
		} else {
			return collectAllImplicitSuperTypesOfType(G, typeRef == null ? null : typeRef.getDeclaredType(), guard);
		}
	}

	private static List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
			IntersectionTypeExpression typeRef, RecursionGuard<Type> guard) {

		EList<TypeRef> typeRefs = typeRef.getTypeRefs();
		return toList(flatten(map(typeRefs, tr -> collectAllImplicitSuperTypes(G, tr, guard))));
	}

	/***/
	private static List<ParameterizedTypeRef> collectAllImplicitSuperTypes(RuleEnvironment G,
			@SuppressWarnings("unused") FunctionTypeExprOrRef typeRef,
			@SuppressWarnings("unused") RecursionGuard<Type> guard) {

		return getFunctionTypesAllImplicitSuperTypeRefs(G);
	}

	/** returns an iterable of the assignment-compatible types, up to now only primitives have this concept. */
	public static Iterable<TypeRef> assignmentCompatibleTypes(RuleEnvironment G, TypeRef typeRef) {
		Type declaredType = typeRef.getDeclaredType();
		if (declaredType instanceof PrimitiveType) {
			return assignmentCompatibleTypes(G, (PrimitiveType) declaredType);
		}
		return Collections.emptyList();
	}

	/***/
	public static Iterable<TypeRef> assignmentCompatibleTypes(@SuppressWarnings("unused") RuleEnvironment G,
			PrimitiveType pt) {
		// Handling primitives with assignment compatible set.
		if (pt.getAssignmentCompatible() != null)
			return ImmutableList.<TypeRef> of(createTypeRef(pt.getAssignmentCompatible()));
		else {
			return Collections.emptyList();
		}
	}

	/**
	 * Returns unmodifiable list of type references to all function types (expressions and concrete functions):
	 * {@code Function} and {@code Object}.
	 */
	public static List<ParameterizedTypeRef> getFunctionTypesAllImplicitSuperTypeRefs(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getFunctionTypesAllImplicitSuperTypeRefs();
	}

	/**
	 * Returns unmodifiable list of type references to all implicit super types of all built-in JavaScript object types,
	 * object literals and via constructor created elements: {@code Object}.
	 */
	public static List<ParameterizedTypeRef> getBuiltInTypesAllImplicitSuperTypeRefs(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getBuiltInTypesAllImplicitSuperTypeRefs();
	}

	/**
	 * Returns unmodifiable list of type references to all implicit super types of all N4 classes, roles, and
	 * interfaces, that is to {@code N4Object} and {@code Object}.
	 */
	public static List<ParameterizedTypeRef> getN4ClassifiersAllImplicitSuperTypeRefs(RuleEnvironment G) {
		return getPredefinedTypes(G).builtInTypeScope.getN4ClassifiersAllImplicitSuperTypeRefs();
	}

	/***/
	public static String ruleEnvAsString(RuleEnvironment G) {
		String INDENT = "    ";
		StringBuffer result = new StringBuffer();
		result.append("RuleEnvironment@");
		result.append(Integer.toHexString(System.identityHashCode(G)));
		result.append(" {\n");
		result.append(typeVariableSubstitutionsAsString(G.getEnvironment(), INDENT));
		if (G.getNext() != null) {
			result.append(INDENT + ruleEnvAsString(G.getNext()).replaceAll("\\n", "\n" + INDENT));
		}
		result.append("}");
		return result.toString();
	}

	/***/
	protected static String typeVariableSubstitutionsAsString(Map<?, ?> substitutions, String indent) {
		List<String> pairs = new ArrayList<>();
		for (Object currKey : substitutions.keySet()) {
			pairs.add(
					indent + typeRefOrVariableAsString(currKey) + " -> " +
							typeRefOrVariableAsString(substitutions.get(currKey)) + "\n");
		}
		Collections.sort(pairs);
		return Strings.join("", pairs);
	}

	/***/
	protected static String typeRefOrVariableAsString(Object obj) {
		if (obj instanceof Collection<?>) {
			Collection<?> col = (Collection<?>) obj;
			return "[ " + Strings.join(", ", map(col, (elem) -> typeRefOrVariableAsString(elem))) + " ]";
		} else if (obj instanceof TypeVariable) {
			TypeVariable tv = (TypeVariable) obj;
			EObject parent = tv.eContainer();
			if (parent instanceof IdentifiableElement) {
				return ((IdentifiableElement) parent).getName() + "#" + tv.getName();
			} else if (parent != null && parent.eClass() != null) {
				return parent.eClass().getName() + "#" + tv.getName();
			} else {
				return "#" + tv.getName();
			}
		} else if (obj instanceof TypeRef && ((TypeRef) obj).getDeclaredType() instanceof TypeVariable) {
			return typeRefOrVariableAsString(((TypeRef) obj).getDeclaredType());
		} else if (obj instanceof TypeRef) {
			return ((TypeRef) obj).getTypeRefAsString();
		} else {
			return obj.toString();
		}
	}

	/**
	 * Check if {@code locationToCheck} is contained in the return part of {@code container}.
	 */
	public static boolean isInReturnDeclaration_Of_StaticMethod(EObject locationToCheck,
			N4MethodDeclaration container) {
		if (!container.isStatic()) {
			return false;
		}
		boolean isInReturn = EcoreUtil.isAncestor(container.getDeclaredReturnTypeRefInAST(), locationToCheck);
		return isInReturn;
	}

	/**
	 * Check if {@code locationToCheck} is contained in the body part of {@code container}.
	 */
	public static boolean isInBody_Of_StaticMethod(EObject locationToCheck, N4MethodDeclaration container) {
		if (!container.isStatic()) {
			return false;
		}
		boolean isInBody = EcoreUtil.isAncestor(container.getBody(), locationToCheck);
		return isInBody;
	}
}
