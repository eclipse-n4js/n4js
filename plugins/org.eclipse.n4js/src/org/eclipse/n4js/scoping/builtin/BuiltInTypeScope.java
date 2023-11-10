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
package org.eclipse.n4js.scoping.builtin;

import static org.eclipse.n4js.ts.types.util.PrimitiveTypeBuilder.createPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.BuiltInType;
import org.eclipse.n4js.ts.types.NullType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.UndefinedType;
import org.eclipse.n4js.ts.types.VoidType;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

/**
 * This scope provides access to the built in JS types. It is recommended to use {@link BuiltInTypeScopeAccess} directly
 * to get the instance via {@link #get(ResourceSet)}.
 *
 * The scope basically decorates the resource set and provides strongly typed accessors to an enumerated set of built-in
 * types.
 */
public final class BuiltInTypeScope extends EnumerableScope {

	/***/
	public static final String PRIMITIVES_N4JSD = "primitives.n4jsd";
	/***/
	public static final String BUILTIN_JS_N4JSD = "builtin_js.n4jsd";
	/***/
	public static final String BUILTIN_N4_N4JSD = "builtin_n4.n4jsd";
	/***/
	public static final String CONSOLE_N4JSD = "console.n4jsd";

	/**
	 * Names of files containing built-in type definitions.
	 */
	public static final String[] FILE_NAMES = {
			PRIMITIVES_N4JSD,
			BUILTIN_JS_N4JSD,
			BUILTIN_N4_N4JSD,
			CONSOLE_N4JSD
	};

	/**
	 * Obtains an instance in the context of the given resourceSet.
	 * <p>
	 * This is the preferred method of creating a BuiltInTypeScope if code needs to access built-in types. But note that
	 * there are convenience methods available in RuleEnvironmentExtensions; so, if a RuleEnvironment is already
	 * available those methods should be used.
	 */
	public static BuiltInTypeScope get(ResourceSet resourceSet) {
		BuiltInTypeScopeAccess result = (BuiltInTypeScopeAccess) EcoreUtil.getAdapter(resourceSet.eAdapters(),
				BuiltInTypeScope.class);
		if (result == null) {
			throw new IllegalStateException("Missing adapter for BuiltInTypeScope");
		}
		return result.getScope();
	}

	/** @return <code>true</code> iff the given resource is the built-in type resource {@value #PRIMITIVES_N4JSD}. */
	public static boolean isPrimitivesResource(Resource resource) {
		return isBuiltInTypeResource(resource, BuiltInTypeScope.PRIMITIVES_N4JSD);
	}

	/** @return <code>true</code> iff the given resource is the built-in type resource of the given name. */
	public static boolean isBuiltInTypeResource(Resource resource, String name) {
		if (resource != null && N4Scheme.isResourceWithN4Scheme(resource)) {
			URI uri = resource.getURI();
			return uri.segmentCount() == 1 && name.equals(uri.segment(0));
		}
		return false;
	}

	/**
	 * Maximum number for N in <code>IterableN&lt;T1,...TN></code>.
	 */
	public static final int ITERABLE_N__MAX_LEN = 9;

	/**
	 * The primitive name {@code typeName}
	 */
	public static final QualifiedName QN_TYPENAME = QualifiedName.create("typeName");
	/**
	 * The primitive name {@code i18nKey}
	 */
	public static final QualifiedName QN_I18NKEY = QualifiedName.create("i18nKey");
	/**
	 * The primitive name {@code pathSelector}
	 */
	public static final QualifiedName QN_PATHSELECTOR = QualifiedName.create("pathSelector");
	/**
	 * The primitive name {@code number}
	 */
	public static final QualifiedName QN_NUMBER = QualifiedName.create("number");
	/**
	 * The object type name {@code Number}
	 */
	public static final QualifiedName QN_NUMBER_OBJECT = QualifiedName.create("Number");
	/**
	 * The primitive name {@code int}
	 */
	public static final QualifiedName QN_INT = QualifiedName.create("int");
	/**
	 * The primitive name {@code string}
	 */
	public static final QualifiedName QN_STRING = QualifiedName.create("string");
	/**
	 * The object type name {@code String}
	 */
	public static final QualifiedName QN_STRING_OBJECT = QualifiedName.create("String");
	/**
	 * The primitive name {@code boolean}
	 */
	public static final QualifiedName QN_BOOLEAN = QualifiedName.create("boolean");
	/**
	 * The object type name {@code Boolean}
	 */
	public static final QualifiedName QN_BOOLEAN_OBJECT = QualifiedName.create("Boolean");
	/**
	 * The primitive name {@code symbol}
	 */
	public static final QualifiedName QN_SYMBOL = QualifiedName.create("symbol");
	/**
	 * The object name {@code Symbol}
	 */
	public static final QualifiedName QN_SYMBOL_OBJECT = QualifiedName.create("Symbol");
	/**
	 * The primitive name {@code undefined}
	 */
	public static final QualifiedName QN_UNDEFINED = QualifiedName.create("undefined");
	/**
	 * The primitive name {@code void}
	 */
	public static final QualifiedName QN_VOID = QualifiedName.create("void");
	/**
	 * The primitive name {@code null}
	 */
	public static final QualifiedName QN_NULL = QualifiedName.create("null");
	/**
	 * The primitive name {@code any}
	 */
	public static final QualifiedName QN_ANY = QualifiedName.create("any");
	/**
	 * The built-in name {@code RegExp}
	 */
	public static final QualifiedName QN_REGEXP = QualifiedName.create("RegExp");
	/**
	 * The built-in name {@code Array}
	 */
	public static final QualifiedName QN_ARRAY = QualifiedName.create("Array");

	/**
	 * The built-in name {@code Object}
	 */
	public static final QualifiedName QN_OBJECT = QualifiedName.create("Object");

	/**
	 * The built-in name {@code Function}
	 */
	public static final QualifiedName QN_FUNCTION = QualifiedName.create("Function");

	/**
	 * The built-in name {@code IArguments}
	 */
	public static final QualifiedName QN_I_ARGUMENTS = QualifiedName.create("IArguments");

	/**
	 * The built-in name {@code Iterator}
	 */
	public static final QualifiedName QN_ITERATOR = QualifiedName.create("Iterator");

	/**
	 * The built-in name {@code IteratorEntry}
	 */
	public static final QualifiedName QN_ITERATOR_ENTRY = QualifiedName.create("IteratorEntry");

	/**
	 * The built-in name {@code Iterable}
	 */
	public static final QualifiedName QN_ITERABLE = QualifiedName.create("Iterable");

	/**
	 * The built-in name {@code AsyncIterable}
	 */
	public static final QualifiedName QN_ASYNC_ITERABLE = QualifiedName.create("AsyncIterable");

	/**
	 * The built-in name {@code Generator}
	 */
	public static final QualifiedName QN_GENERATOR = QualifiedName.create("Generator");

	/**
	 * The built-in name {@code AsyncGenerator}
	 */
	public static final QualifiedName QN_ASYNC_GENERATOR = QualifiedName.create("AsyncGenerator");

	/**
	 * The built-in name {@code Promise}
	 */
	public static final QualifiedName QN_PROMISE = QualifiedName.create("Promise");

	/**
	 * The built-in name {@code N4Object}
	 */
	public static final QualifiedName QN_N4OBJECT = QualifiedName.create("N4Object");

	/**
	 * The built-in name {@code N4Enum}
	 */
	public static final QualifiedName QN_N4ENUM = QualifiedName.create("N4Enum");

	/**
	 * The built-in name {@code N4NumberBasedEnum}
	 */
	public static final QualifiedName QN_N4NUMBERBASEDENUM = QualifiedName.create("N4NumberBasedEnum");

	/**
	 * The built-in name {@code N4StringBasedEnum}
	 */
	public static final QualifiedName QN_N4STRINGBASEDENUM = QualifiedName.create("N4StringBasedEnum");

	/**
	 * The object name {@code Error}.
	 */
	public static final QualifiedName QN_ERROR = QualifiedName.create("Error");

	/**
	 * The built-in name {@code N4Provider}.
	 */
	public static final QualifiedName QN_N4PROVIDER = QualifiedName.create("N4Provider");

	// Built-in classes related to reflection:
	private static final QualifiedName QN_N4ELEMENT = QualifiedName.create("N4Element");
	private static final QualifiedName QN_N4NAMEDELEMENT = QualifiedName.create("N4NamedElement");
	private static final QualifiedName QN_N4TYPE = QualifiedName.create("N4Type");

	private List<ParameterizedTypeRef> n4classifiersAllImplicitSuperTypeRefs;
	private List<ParameterizedTypeRef> objectPrototypesAllImplicitSuperTypeRefs;
	private List<ParameterizedTypeRef> functionTypesAllImplicitSuperTypeRefs;

	/**
	 * Creates a new scope for built in types in the given resource set.
	 */
	public BuiltInTypeScope(ExecutionEnvironmentDescriptor descriptor) {
		super(descriptor);
	}

	/**
	 * Returns the built-in type "any".
	 */
	public final AnyType getAnyType() {
		return getEObjectOrProxy(QN_ANY);
	}

	/**
	 * Returns reference to the built-in type "any".
	 */
	public final ParameterizedTypeRef getAnyTypeRef() {
		return TypeUtils.createTypeRef(getAnyType());
	}

	/**
	 * Returns the built-in type "null".
	 */
	public final NullType getNullType() {
		return getEObjectOrProxy(QN_NULL);
	}

	/**
	 * Returns the built-in type "void".
	 */
	public final VoidType getVoidType() {
		return getEObjectOrProxy(QN_VOID);
	}

	/**
	 * Returns a newly created reference to the built-in type "void".
	 */
	public final ParameterizedTypeRef getVoidTypeRef() {
		return TypeUtils.createTypeRef(getVoidType());
	}

	/**
	 * Returns the built-in type "undefined".
	 */
	public final UndefinedType getUndefinedType() {
		return getEObjectOrProxy(QN_UNDEFINED);
	}

	/**
	 * Returns a newly created reference to the built-in type "undefined".
	 */
	public final ParameterizedTypeRef getUndefinedTypeRef() {
		return TypeUtils.createTypeRef(getUndefinedType());
	}

	/**
	 * Returns the primitive built-in type "boolean" (lower case!).
	 */
	public final PrimitiveType getBooleanType() {
		return getEObjectOrProxy(QN_BOOLEAN);
	}

	/**
	 * Returns the built-in object type "Boolean" (upper case!).
	 */
	public final TClass getBooleanObjectType() {
		return getEObjectOrProxy(QN_BOOLEAN_OBJECT);
	}

	/**
	 * Returns the primitive built-in type "string" (lower case!).
	 */
	public final PrimitiveType getStringType() {
		return getEObjectOrProxy(QN_STRING);
	}

	/**
	 * Returns the built-in object type "String" (upper case!).
	 */
	public final TClass getStringObjectType() {
		return getEObjectOrProxy(QN_STRING_OBJECT);
	}

	/**
	 * Returns the primitive built-in type "number" (lower case!).
	 */
	public final PrimitiveType getNumberType() {
		return getEObjectOrProxy(QN_NUMBER);
	}

	/**
	 * Returns the built-in object type "Number" (upper case!).
	 */
	public final TClass getNumberObjectType() {
		return getEObjectOrProxy(QN_NUMBER_OBJECT);
	}

	/**
	 * Returns the primitive built-in type "int".
	 */
	public final PrimitiveType getIntType() {
		return getEObjectOrProxy(QN_INT);
	}

	/**
	 * Returns the primitive built-in type "symbol" (lower case!).
	 */
	public final PrimitiveType getSymbolType() {
		return getEObjectOrProxy(QN_SYMBOL);
	}

	/**
	 * Returns the built-in object type "Symbol" (upper case!).
	 */
	public final TClass getSymbolObjectType() {
		return getEObjectOrProxy(QN_SYMBOL_OBJECT);
	}

	/**
	 * Returns the primitive built-in type "pathSelector" (lower case!).
	 */
	public final PrimitiveType getPathSelectorType() {
		return getEObjectOrProxy(QN_PATHSELECTOR);
	}

	/**
	 * Returns the primitive built-in type "i18nKey" (lower case!).
	 */
	public final PrimitiveType getI18nKeyType() {
		return getEObjectOrProxy(QN_I18NKEY);
	}

	/**
	 * Returns the primitive built-in type "typeName" (lower case!).
	 */
	public final PrimitiveType getTypeNameType() {
		return getEObjectOrProxy(QN_TYPENAME);
	}

	/**
	 * Returns the built-in type "RegExp".
	 */
	public final TClass getRegexpType() {
		return getEObjectOrProxy(QN_REGEXP);
	}

	/**
	 * Returns the built-in type "Array".
	 */
	public final TClass getArrayType() {
		return getEObjectOrProxy(QN_ARRAY);
	}

	/**
	 * Returns the built-in type "Object", implicit base class for all JavaScript built-in objects, object literals and
	 * via constructor call created elements. It is also the base class for N4Object.
	 */
	public final TClassifier getObjectType() {
		return getEObjectOrProxy(QN_OBJECT);
	}

	/**
	 * Returns the built-in type "Function", implicit base class for all functions.
	 */
	public final TClass getFunctionType() {
		return getEObjectOrProxy(QN_FUNCTION);
	}

	/**
	 * Returns the built-in type "IArguments" of the implicit, array-like 'arguments' variable.
	 */
	public final TInterface getArgumentsType() {
		return getEObjectOrProxy(QN_I_ARGUMENTS);
	}

	/**
	 * Returns the built-in type "Iterator".
	 */
	public final TInterface getIteratorType() {
		return getEObjectOrProxy(QN_ITERATOR);
	}

	/**
	 * Returns the built-in type "IteratorEntry".
	 */
	public final TInterface getIteratorEntryType() {
		return getEObjectOrProxy(QN_ITERATOR_ENTRY);
	}

	/**
	 * Returns the built-in type "Iterable".
	 */
	public final TInterface getIterableType() {
		return getEObjectOrProxy(QN_ITERABLE);
	}

	/**
	 * Returns the built-in type "AsyncIterable".
	 */
	public final TInterface getAsyncIterableType() {
		return getEObjectOrProxy(QN_ASYNC_ITERABLE);
	}

	/**
	 * Returns the built-in type "Generator".
	 */
	public final TInterface getGeneratorType() {
		return getEObjectOrProxy(QN_GENERATOR);
	}

	/**
	 * Returns the built-in type "AsyncGenerator".
	 */
	public final TInterface getAsyncGeneratorType() {
		return getEObjectOrProxy(QN_ASYNC_GENERATOR);
	}

	/**
	 * Returns the built-in type "IterableN" for 2 &leq; N &leq; {@link #ITERABLE_N__MAX_LEN}.
	 */
	public final TInterface getIterableNType(int n) {
		if (n < 2 || n > ITERABLE_N__MAX_LEN)
			throw new IllegalArgumentException("n must lie between 2 and " + ITERABLE_N__MAX_LEN + " (inclusive)");
		return getEObjectOrProxy(QualifiedName.create("Iterable" + n));
	}

	private Set<TInterface> cachedIterableNTypes = null;

	/**
	 * Returns all built-in types "IterableN" for 2 &leq; N &leq; {@link #ITERABLE_N__MAX_LEN}.
	 */
	public final Set<TInterface> getIterableNTypes() {
		if (cachedIterableNTypes == null) {
			cachedIterableNTypes = new LinkedHashSet<>();
			for (int n = 2; n <= ITERABLE_N__MAX_LEN; n++)
				cachedIterableNTypes.add(getIterableNType(n));
			cachedIterableNTypes = Collections.unmodifiableSet(cachedIterableNTypes);
		}
		return cachedIterableNTypes;
	}

	/**
	 * Returns the built-in type "ArrayN" for 2 &leq; N &leq; {@link #ITERABLE_N__MAX_LEN}.
	 */
	public final TClass getArrayNType(int n) {
		if (n < 2 || n > ITERABLE_N__MAX_LEN)
			throw new IllegalArgumentException("n must lie between 2 and " + ITERABLE_N__MAX_LEN + " (inclusive)");
		return getEObjectOrProxy(QualifiedName.create("Array" + n));
	}

	private Set<TClass> cachedArrayNTypes = null;

	/**
	 * Returns all built-in types "ArrayN" for 2 &leq; N &leq; {@link #ITERABLE_N__MAX_LEN}.
	 */
	public final Set<TClass> getArrayNTypes() {
		if (cachedArrayNTypes == null) {
			cachedArrayNTypes = new LinkedHashSet<>();
			for (int n = 2; n <= ITERABLE_N__MAX_LEN; n++)
				cachedArrayNTypes.add(getArrayNType(n));
			cachedArrayNTypes = Collections.unmodifiableSet(cachedArrayNTypes);
		}
		return cachedArrayNTypes;
	}

	/**
	 * Returns the built-in type "N4Object", implicit base class for all N4 classifiers.
	 */
	public final TClass getN4ObjectType() {
		return getEObjectOrProxy(QN_N4OBJECT);
	}

	/**
	 * Returns the built-in type {@code Error} object prototype.
	 *
	 * @return the object prototype for the built-in error.
	 */
	public final TClass getErrorType() {
		return getEObjectOrProxy(QN_ERROR);
	}

	/**
	 * Returns the built-in type "N4Enum", implicit base class for all N4 enumerations.
	 */
	public final TClass getN4EnumType() {
		return getEObjectOrProxy(QN_N4ENUM);
	}

	/**
	 * Returns the built-in type "N4NumberBasedEnum", implicit base class for all N4 number based enumerations, enums
	 * annotated with @NumberBased.
	 */
	public final TClass getN4NumberBasedEnumType() {
		return getEObjectOrProxy(QN_N4NUMBERBASEDENUM);
	}

	/**
	 * Returns the built-in type "N4StringBasedEnum", implicit base class for all N4 string based enumerations, enums
	 * annotated with @StringBased.
	 */
	public final TClass getN4StringBasedEnumType() {
		return getEObjectOrProxy(QN_N4STRINGBASEDENUM);
	}

	/**
	 * Returns the built-in type "Promise".
	 */
	public final TClass getPromiseType() {
		return getEObjectOrProxy(QN_PROMISE);
	}

	/**
	 * Returns the built-in type "N4Provider".
	 */
	public final TInterface getN4ProviderType() {
		return getEObjectOrProxy(QN_N4PROVIDER);
	}

	/**
	 * Returns the built-in type "N4Element".
	 */
	public final TClass getN4ElementType() {
		return getEObjectOrProxy(QN_N4ELEMENT);
	}

	/**
	 * Returns the built-in type "N4NamedElement".
	 */
	public final TClass getN4NamedElementType() {
		return getEObjectOrProxy(QN_N4NAMEDELEMENT);
	}

	/**
	 * Returns the built-in type "N4Type".
	 */
	public final TClass getN4TypeType() {
		return getEObjectOrProxy(QN_N4TYPE);
	}

	/**
	 * Returns true iff the given type is of String type, i.e {@link #getStringType() string}.
	 */
	public final boolean isString(Type type) {
		return type == getStringType();
	}

	/**
	 * Returns true iff the given type is of Boolean type, i.e {@link #getBooleanType() boolean}.
	 */
	public final boolean isBoolean(Type type) {
		return type == getBooleanType();
	}

	/**
	 * Returns true iff the given type is one of the two numeric types, i.e {@link #getNumberType() number}, or
	 * {@link #getIntType() int}.
	 */
	public final boolean isNumeric(Type type) {
		return type == getNumberType() || type == getIntType();
	}

	/**
	 * Returns true iff the given type is number, int, boolean, null or even undefined.
	 */
	public final boolean isNumericOperand(Type type) {
		return type == getNumberType()
				|| type == getIntType()
				|| type == getBooleanType()
				|| type == getNullType()
				|| type == getUndefinedType()
				|| type == getN4NumberBasedEnumType();
	}

	/**
	 * Returns unmodifiable list of type references to implicit super types of all N4 classes, roles, and interfaces,
	 * that is to {@code N4Object} and {@code Object}. NB that {@code N4Object} is a N4Classifier itself, that is,
	 * client code has to check that case explicitly.
	 */
	public synchronized List<ParameterizedTypeRef> getN4ClassifiersAllImplicitSuperTypeRefs() {
		if (n4classifiersAllImplicitSuperTypeRefs == null) {
			n4classifiersAllImplicitSuperTypeRefs = toTypeReferences(getN4ObjectType(), getObjectType());
		}
		return n4classifiersAllImplicitSuperTypeRefs;
	}

	/**
	 * Returns unmodifiable list of type references to implicit super types of all built-in JavaScript object types,
	 * object literals and via constructor created elements: {@code Object}. NB that {@code Object} is a object
	 * prototype itself, that is, client code has to check that case explicitly.
	 */
	public synchronized List<ParameterizedTypeRef> getBuiltInTypesAllImplicitSuperTypeRefs() {
		if (objectPrototypesAllImplicitSuperTypeRefs == null) {
			objectPrototypesAllImplicitSuperTypeRefs = toTypeReferences(getObjectType());
		}
		return objectPrototypesAllImplicitSuperTypeRefs;
	}

	/**
	 * Returns unmodifiable list of type references to implicit super types of all function types (expressions):
	 * {@code Function} and {@code Object}. Note that concrete functions are actually instances of {@code Function}, but
	 * we model them as expressions, and the parameters are handles similar to type arguments with co- and
	 * contra-variance.
	 */
	public synchronized List<ParameterizedTypeRef> getFunctionTypesAllImplicitSuperTypeRefs() {
		if (functionTypesAllImplicitSuperTypeRefs == null) {
			functionTypesAllImplicitSuperTypeRefs = toTypeReferences(getFunctionType(), getObjectType());
		}
		return functionTypesAllImplicitSuperTypeRefs;
	}

	@Override
	protected String[] getFileNames() {
		return FILE_NAMES;
	}

	@Override
	protected void buildMap(Resource resourceRaw, Map<QualifiedName, IEObjectDescription> elements) {
		N4JSResource resource = (N4JSResource) resourceRaw;
		String fileName = resource.getURI().lastSegment();
		TModule module = (TModule) resource.getContents().get(1); // do not change this to resource.getModule()

		beforeRegistration(fileName, module);

		// register exported elements
		for (TExportableElement elem : Iterables.concat(module.getTypesAndFunctions(), module.getExportedVariables())) {
			IEObjectDescription description = EObjectDescription.create(elem.getName(), elem);
			elements.put(description.getName(), description);
		}

		// trigger post-processing
		try {
			resource.performPostProcessing();
		} catch (Throwable th) {
			throw new IllegalStateException("exception while post-processing built-in type definition file: "
					+ resource.getURI().lastSegment(), th);
		}

		afterPostProcessing(fileName);
	}

	private void beforeRegistration(String fileName, TModule module) {
		if (PRIMITIVES_N4JSD.equals(fileName)) {
			List<Type> coreTypes = createCoreTypes();
			EcoreUtilN4.doWithDeliver(false, () -> {
				module.getTypes().addAll(coreTypes);
			}, module);
		}
	}

	private void afterPostProcessing(String fileName) {
		if (BUILTIN_JS_N4JSD.equals(fileName)) {
			// set the index element type
			Type anyType = getAnyType();
			PrimitiveType stringType = getStringType();
			TClass stringObjectType = getStringObjectType();
			TClass arrayType = getArrayType();
			TInterface argumentsType = getArgumentsType();
			EcoreUtilN4.doWithDeliver(false, () -> {
				stringObjectType.setDeclaredElementType(TypeUtils.createTypeRef(stringType));
				arrayType.setDeclaredElementType(TypeUtils.createTypeRef(arrayType.getTypeVars().get(0)));
				argumentsType.setDeclaredElementType(TypeUtils.createTypeRef(anyType));
			}, stringObjectType, arrayType, argumentsType);

			// set the autoboxedType property of primitive types
			setAutoboxedType(QN_BOOLEAN, QN_BOOLEAN_OBJECT);
			setAutoboxedType(QN_NUMBER, QN_NUMBER_OBJECT);
			setAutoboxedType(QN_INT, QN_NUMBER_OBJECT);
			setAutoboxedType(QN_STRING, QN_STRING_OBJECT);
			setAutoboxedType(QN_PATHSELECTOR, QN_STRING_OBJECT);
			setAutoboxedType(QN_I18NKEY, QN_STRING_OBJECT);
			setAutoboxedType(QN_TYPENAME, QN_STRING_OBJECT);
		}
	}

	private void setAutoboxedType(QualifiedName primitiveTypeName, QualifiedName autoboxedTypeName) {
		PrimitiveType type = (PrimitiveType) getEObjectOrProxy(primitiveTypeName);
		TClassifier autoboxedType = (TClassifier) getEObjectOrProxy(autoboxedTypeName);
		EcoreUtilN4.doWithDeliver(false, () -> {
			type.setAutoboxedType(autoboxedType);
		}, type);
	}

	private List<Type> createCoreTypes() {
		final List<Type> result = new ArrayList<>();

		// EcmaScript
		PrimitiveType stringType;
		result.add(createBuiltIn(TypesPackage.Literals.UNDEFINED_TYPE));
		result.add(createBuiltIn(TypesPackage.Literals.NULL_TYPE));
		result.add(createPrimitive(QN_BOOLEAN).done());
		result.add(createPrimitive(QN_NUMBER).done());
		result.add(stringType = createPrimitive(QN_STRING).done());
		result.add(createPrimitive(QN_SYMBOL).done());

		stringType.setDeclaredElementType(TypeUtils.createTypeRef(stringType));

		// N4JS
		result.add(createBuiltIn(TypesPackage.Literals.ANY_TYPE));
		result.add(createBuiltIn(TypesPackage.Literals.VOID_TYPE));
		result.add(createPrimitive(QN_INT).done());
		result.add(createPrimitive(QN_PATHSELECTOR).typeParam("T").assignmentCompatible(stringType).done());
		result.add(createPrimitive(QN_I18NKEY).assignmentCompatible(stringType).done());
		result.add(createPrimitive(QN_TYPENAME).typeParam("T").assignmentCompatible(stringType).done());

		return result;
	}

	private BuiltInType createBuiltIn(EClass eClass) {
		BuiltInType result = (BuiltInType) TypesFactory.eINSTANCE.create(eClass);
		String eClassName = eClass.getName();
		Preconditions.checkState(eClassName.endsWith("Type"));
		String name = eClassName.substring(0, eClassName.length() - "Type".length()).toLowerCase();
		result.setName(name);
		return result;
	}
}
