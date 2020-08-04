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
package org.eclipse.n4js;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.tryFind;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.eclipse.n4js.AnnotationDefinition.RetentionPolicy.RUNTIME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.BOOLEAN_LITERAL;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.EXPORTABLE_ELEMENT;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.EXPORT_DECLARATION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FORMAL_PARAMETER;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FUNCTION_DECLARATION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FUNCTION_DEFINITION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FUNCTION_OR_FIELD_ACCESSOR;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.INT_LITERAL;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_CLASSIFIER_DECLARATION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_CLASS_DECLARATION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_CLASS_DEFINITION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_FIELD_ACCESSOR;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_FIELD_DECLARATION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_INTERFACE_DECLARATION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_MEMBER_DECLARATION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_METHOD_DECLARATION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_SETTER_DECLARATION;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.SCRIPT;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.STRING_LITERAL;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.TYPE_DEFINING_ELEMENT;
import static org.eclipse.n4js.ts.typeRefs.TypeRefsPackage.Literals.TYPE_REF;
import static org.eclipse.xtext.EcoreUtil2.getContainerOfType;
import static org.eclipse.xtext.util.SimpleAttributeResolver.newResolver;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.validation.N4JSJavaScriptVariantHelper;

import com.google.common.base.Function;
import com.google.common.base.Optional;

/**
 * Defined annotations, will be replaces (or at least extended) by system allowing customized annotations.
 * <p>
 * Remark about {@link ExportDeclaration}: These declarations are handled transparently, that is, annotations attached
 * to export declarations are handled as if they were attached to the exported element in
 * {@link #getAnnotation(AnnotableElement)}. However, {@link #getOwnedAnnotation(AnnotableElement)} still only returns
 * the directly attached ones.
 */
public final class AnnotationDefinition {

	private final static Map<String, AnnotationDefinition> DEFINED_ANNOTATIONS = new HashMap<>();
	private final static Map<String, AnnotationDefinition> ANNOTATIONS_IN_TYPEMODEL = new HashMap<>();

	// /////////////////////////////////////////////////////////////////////////

	/**
	 * To be removed in the future, added while we have a couple of validation bugs IDE-1104
	 */
	public final static AnnotationDefinition IDEBUG = define("IDEBUG")
			.targets(SCRIPT, EXPORT_DECLARATION, EXPORTABLE_ELEMENT,
					TYPE_DEFINING_ELEMENT, N4_MEMBER_DECLARATION, FUNCTION_OR_FIELD_ACCESSOR)
			.transitive()
			.repeatable()
			.args(INT_LITERAL, STRING_LITERAL)
			.end();

	/**
	 * 3.2. Accessibility of Types, Top-Level Variables and Function Declarations, 3.3. Accessibility of Members
	 */
	public final static AnnotationDefinition INTERNAL = define("Internal")
			.targets(TYPE_DEFINING_ELEMENT, N4_MEMBER_DECLARATION, FUNCTION_OR_FIELD_ACCESSOR,
					EXPORTABLE_ELEMENT,
					EXPORT_DECLARATION)
			.end();

	/**
	 * 4.14.2 StringBased Enumeration Type (subtype of string)
	 */
	public final static AnnotationDefinition STRING_BASED = define("StringBased")
			.targets(N4JSPackage.Literals.N4_ENUM_DECLARATION).retention(RetentionPolicy.TYPE)
			.end();

	/**
	 * 5.1.1.1. Final/Extensibility Modifier
	 */
	public final static AnnotationDefinition FINAL = define("Final")
			.targets(N4_CLASS_DECLARATION, N4_MEMBER_DECLARATION)
			.targetsWithCustomError(N4_INTERFACE_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * 5.2.2.3. Covariant Constructors
	 */
	public final static AnnotationDefinition COVARIANT_CONSTRUCTOR = define("CovariantConstructor")
			.targets(N4_CLASS_DECLARATION, N4_INTERFACE_DECLARATION, N4_MEMBER_DECLARATION).end();

	/**
	 * @TODO IDE-1661 cf. ECMA 2015 6.1.7.1 Property Attributes
	 */
	public final static AnnotationDefinition WRITABLE = define("Writable")
			.args(BOOLEAN_LITERAL)
			.targets(N4_FIELD_DECLARATION)
			.retention(RetentionPolicy.TYPE).end();

	/**
	 * @TODO IDE-1661 cf. ECMA 2015 6.1.7.1 Property Attributes
	 */
	public final static AnnotationDefinition ENUMERABLE = define("Enumerable")
			.args(BOOLEAN_LITERAL)
			.targets(N4_FIELD_DECLARATION, N4_FIELD_ACCESSOR)
			.retention(RetentionPolicy.TYPE).end();

	/**
	 * @TODO IDE-1661 cf. ECMA 2015 6.1.7.1 Property Attributes
	 */
	public final static AnnotationDefinition CONFIGURABLE = define("Configurable")
			.args(BOOLEAN_LITERAL)
			.targets(N4_FIELD_DECLARATION, N4_FIELD_ACCESSOR)
			.retention(RetentionPolicy.TYPE).end();

	/**
	 * 5.1.2.2. Observable Classes
	 */
	public final static AnnotationDefinition OBSERVABLE = define("Observable")
			.targets(N4_FIELD_DECLARATION, N4_CLASS_DEFINITION, EXPORT_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * 5.2.2.1. Structural This Type in Constructor and Spec Parameter
	 */
	public final static AnnotationDefinition SPEC = define("Spec").targets(FORMAL_PARAMETER)
			.retention(RetentionPolicy.TYPE).end();

	/**
	 * 5.2.3.2. NFON
	 */
	public final static AnnotationDefinition NFON = define("Nfon").targets(N4_FIELD_DECLARATION)
			.args(STRING_LITERAL, STRING_LITERAL).argsOptional()
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * 5.4. Redefinition of Members
	 */
	public final static AnnotationDefinition OVERRIDE = define("Override").targets(N4_MEMBER_DECLARATION).end();

	/**
	 * 7.1.1. The this Keyword, Constraints 91 (Valid Target and Argument of This Annotation)
	 */
	public final static AnnotationDefinition THIS = define("This")
			.targets(FUNCTION_DEFINITION /* includes method! */, N4_FIELD_ACCESSOR).args(TYPE_REF).end();

	/**
	 * 11.1.1. Declaring externals
	 */
	public final static AnnotationDefinition N4JS = define("N4JS").targets(N4_CLASS_DECLARATION, EXPORT_DECLARATION)
			.retention(RetentionPolicy.TYPE).end();

	/**
	 * 11.1.3. Implementation of External Declarations
	 */
	public final static AnnotationDefinition IGNORE_IMPLEMENTATION = define("IgnoreImplementation")
			.targets(SCRIPT, EXPORT_DECLARATION, EXPORTABLE_ELEMENT).transitive().end();

	/**
	 * 11.2. Global Definitions
	 */
	public final static AnnotationDefinition GLOBAL = define("Global").targets(SCRIPT).transitive()
			.retention(RetentionPolicy.TYPE).end();

	/**
	 * 11.3. Runtime Definitions
	 */
	public final static AnnotationDefinition PROVIDED_BY_RUNTIME = define("ProvidedByRuntime")
			.targets(SCRIPT, EXPORT_DECLARATION, EXPORTABLE_ELEMENT).transitive()
			.end();

	/**
	 * 12.4. Polyfill Definitions
	 */
	public final static AnnotationDefinition POLYFILL = define("Polyfill")
			.targets(EXPORT_DECLARATION, EXPORTABLE_ELEMENT)
			.end();

	/**
	 * 11.4. Static Polyfill Definitions
	 */
	public final static AnnotationDefinition STATIC_POLYFILL = define("StaticPolyfill")
			.targets(N4_CLASS_DECLARATION)
			.end();

	/**
	 * 11.4. Static Polyfill Aware
	 */
	public final static AnnotationDefinition STATIC_POLYFILL_AWARE = define("StaticPolyfillAware")
			.targets(SCRIPT)
			.transitive()
			.retention(RUNTIME)
			.end();

	/**
	 * 11.4. Static Polyfill Module
	 */
	public final static AnnotationDefinition STATIC_POLYFILL_MODULE = define("StaticPolyfillModule")
			.targets(SCRIPT)
			.transitive()
			.retention(RUNTIME)
			.end();

	/**
	 * 13.1 ExternalDeclarations Constraints 138 (External class/interface members).
	 * <p>
	 * A "raw" reference to the name is used in {@link org.eclipse.n4js.n4JS.N4MethodDeclaration#isAbstract()} and
	 * {@link org.eclipse.n4js.n4JS.N4FieldAccessor#isAbstract()}, as there must be no references from the model to
	 * n4js.
	 */
	public final static AnnotationDefinition PROVIDES_DEFAULT_IMPLEMENTATION = define("ProvidesDefaultImplementation")
			.targets(N4_METHOD_DECLARATION, N4_FIELD_ACCESSOR).transitive()
			.retention(RetentionPolicy.TYPE).end();

	/**
	 * 13.1 ExternalDeclarations Constraints 138 (External class/interface members).
	 */
	public final static AnnotationDefinition PROVIDES_INITIALZER = define("ProvidesInitializer")
			.targets(N4_FIELD_DECLARATION, N4_SETTER_DECLARATION).transitive()
			.retention(RetentionPolicy.TYPE).end();

	/**
	 * 6.5.1 Promisifiable functions and methods.
	 */
	public final static AnnotationDefinition PROMISIFIABLE = define("Promisifiable")
			.targets(FUNCTION_DECLARATION, N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.TYPE).end();

	// <---- TEST support annotations (at some point should be moved to concrete test plugin) ---->

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition TEST_GROUP = define("Group").targets(N4_CLASS_DECLARATION,
			N4_METHOD_DECLARATION).retention(RetentionPolicy.RUNTIME).transitive().repeatable().args(STRING_LITERAL)
			.end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition TEST_METHOD = define("Test").targets(N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition PARAMETERS = define("Parameters").targets(N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).args(STRING_LITERAL).argsOptional().end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition PARAMETER = define("Parameter").targets(N4_FIELD_DECLARATION,
			N4_SETTER_DECLARATION).retention(RetentionPolicy.RUNTIME).args(INT_LITERAL).argsOptional().end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition BEFOREALL_SETUP = define("BeforeAll").targets(N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition BEFORE_SETUP = define("Before").targets(N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition AFTERALL_TEARDOWN = define("AfterAll").targets(N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition AFTER_TEARDOWN = define("After").targets(N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition TEST_IGNORE = define("Ignore")
			.targets(N4_CLASS_DECLARATION, N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).transitive().args(STRING_LITERAL).argsOptional().end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition TEST_FIXME = define("Fixme")
			.targets(N4_CLASS_DECLARATION, N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).transitive().args(STRING_LITERAL, STRING_LITERAL).argsOptional().end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition TEST_TIMEOUT = define("Timeout")
			.targets(N4_CLASS_DECLARATION, N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).transitive().args(INT_LITERAL).end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition DESCRIPTION = define("Description")
			.targets(N4_CLASS_DECLARATION, N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).args(STRING_LITERAL).end();

	/**
	 * N4JSIDESpec : chapter 9 Testing
	 */
	public final static AnnotationDefinition EXCLUDE_FROM_TEST_CATALOG = define("ExcludeFromTestCatalog")
			.targets(N4_CLASS_DECLARATION, N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).transitive().args(STRING_LITERAL).argsOptional().end();

	/**
	 * N4JSSpec : chapter 11.2 Test Support, Constraints 128: TestAPI
	 */
	public final static AnnotationDefinition TEST_API = define("TestAPI")
			.targets(N4_CLASSIFIER_DECLARATION, N4_MEMBER_DECLARATION)
			.retention(RetentionPolicy.TYPE).end();

	// <---- Dependency Injection annotations ---->

	/**
	 * N4JS IDE Spec, Chapter 11
	 */
	public final static AnnotationDefinition INJECTED = define("Injected")
			.targets(N4_CLASS_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JS IDE Spec, Chapter 11
	 */
	public final static AnnotationDefinition GENERATE_INJECTOR = define("GenerateInjector")
			.targets(N4_CLASS_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JS IDE Spec, Chapter 11
	 */
	public final static AnnotationDefinition WITH_PARENT_INJECTOR = define("WithParentInjector")
			.targets(N4_CLASS_DECLARATION)
			.args(TYPE_REF).retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JS IDE Spec, Chapter 11
	 */
	public final static AnnotationDefinition PROVIDES = define("Provides").targets(N4_METHOD_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JS IDE Spec, Chapter 11
	 */
	public final static AnnotationDefinition BINDER = define("Binder").targets(N4_CLASS_DECLARATION)
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JS IDE Spec, Chapter 11
	 */
	public final static AnnotationDefinition BIND = define("Bind").targets(N4_CLASS_DECLARATION)
			.args(TYPE_REF, TYPE_REF).retention(RetentionPolicy.RUNTIME).repeatable().end();

	/**
	 * N4JS IDE Spec, Chapter 11
	 */
	public final static AnnotationDefinition USE_BINDER = define("UseBinder").targets(N4_CLASS_DECLARATION)
			.args(TYPE_REF).retention(RetentionPolicy.RUNTIME).repeatable().end();

	/**
	 * N4JS IDE Spec, Chapter 11
	 */
	public final static AnnotationDefinition INJECT = define("Inject")
			.targets(N4_FIELD_DECLARATION, N4_METHOD_DECLARATION) // note: also on constructor!
			.retention(RetentionPolicy.RUNTIME).end();

	/**
	 * N4JS IDE Spec, Chapter 11
	 */
	public final static AnnotationDefinition SINGLETON = define("Singleton").targets(N4_CLASS_DECLARATION)
			.retention(RUNTIME).end();

	// <---- N4IDL specific ---->

	/**
	 * Annotation to mark function declarations as migrations in the context of N4IDL.
	 */
	public final static AnnotationDefinition MIGRATION = define("Migration").targets(FUNCTION_DECLARATION)
			.javaScriptVariants(N4JSJavaScriptVariantHelper.EXT_N4IDL)
			.args(N4JSPackage.Literals.NUMERIC_LITERAL, N4JSPackage.Literals.NUMERIC_LITERAL).argsOptional()
			.retention(RUNTIME).end();

	/**
	 * Annotation to mark classifier and function declarations as version aware in the context of N4IDL.
	 */
	public final static AnnotationDefinition VERSION_AWARE = define("VersionAware")
			.targets(FUNCTION_DECLARATION, N4_CLASSIFIER_DECLARATION)
			.javaScriptVariants(N4JSJavaScriptVariantHelper.EXT_N4IDL)
			.retention(RUNTIME).end();

	// <---- miscellaneous ---->

	/**
	 * Annotation for internal use.
	 */
	public final static AnnotationDefinition TRANSIENT = define("Transient").targets(N4_FIELD_DECLARATION)
			.retention(RUNTIME).end();

	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Finds the definition for a given annotation name.
	 */
	public static AnnotationDefinition find(final String name) {
		return DEFINED_ANNOTATIONS.get(name);
	}

	/**
	 * RetentionPolicy defines where and when the annotation is accessible, that is, in the AST, in the type model, or
	 * at runtime. AST, TYPE, and RUNTIME are transitive, that is, RUNTIME includes AST and TYPE. In some rare cases an
	 * annotation may be present at runtime but not in the type model---this is usually the case only if the information
	 * stored in the annotation is made available in the type model by a field (e.g., a boolean flag). This last case is
	 * modeled via RUTIME_TYPEFIELD.
	 * <p>
	 * The following table illustrates the effect of the policy:
	 * <table border="1">
	 * <tr>
	 * <th>Policy</th>
	 * <th>AST model</th>
	 * <th>Type Model</th>
	 * <th>Runtime (N4Annotation)</th>
	 * </tr>
	 * <tr>
	 * <td>AST</td>
	 * <td>yes</td>
	 * <td>no</td>
	 * <td>no</td>
	 * </tr>
	 * <tr>
	 * <td>TYPE</td>
	 * <td>yes</td>
	 * <td>yes</td>
	 * <td>no</td>
	 * </tr>
	 * <tr>
	 * <td>RUNTIME</td>
	 * <td>yes</td>
	 * <td>yes</td>
	 * <td>yes</td>
	 * </tr>
	 * <tr>
	 * <td>RUNTIME_TYPEFIELD</td>
	 * <td>yes</td>
	 * <td><b>no</b><small>(modeled as field)</small></td>
	 * <td>yes</td>
	 * </tr>
	 * </table>
	 */
	public enum RetentionPolicy {
		/** Annotations are only available in the AST */
		AST,
		/** Annotations are to be recorded in the type, but are not available at runtime */
		TYPE,
		/** Annotations are to be available at runtime -- this also assumes that they are recorded in the type */
		RUNTIME,
		/**
		 * Annotations are to be available at runtime but are not recorded in the type (as they are represented via
		 * fields)
		 */
		RUNTIME_TYPEFIELD
	}

	/** Name of the annotation */
	public final String name;
	/** Possible targets (AST node types) of the annotation, if empty annotation can be used everywhere */
	public final EClass[] targets;
	/** Possible wrong targets (AST node types) of the annotation for which a special error message is created. */
	public final EClass[] targetsWithCustomError;
	/**
	 * JavaScript Variants in which this annotation applies (list of file extensions according to
	 * {@link N4JSJavaScriptVariantHelper}). If empty, the annotation can be used everywhere
	 */
	public final String[] javaScriptVariants;
	/** Flag whether annotation is repeatable per element, false by default */
	public final boolean repeatable;
	/** Retention policy, not used yet */
	public final RetentionPolicy retention;
	/** Types of arguments */
	public final EClass[] argtypes;
	/** Flag indicating whether ALL arguments are optional, false by default */
	public final boolean argsOptional;
	/**
	 * Flag indicating that the annotation is transitive, that is, it is active if the annotated element or a container
	 * contains the annotation. Note that {@link ExportDeclaration} are automatically handled transparently, that is
	 * annotations on export declaration apply to the exported element (even if annotations is not transitive). False by
	 * default
	 */
	public final boolean transitive;
	/**
	 * Flag indicating whether the last element of the argument list variadic or not. This flag has any meaning if the
	 * argument list array is not empty.
	 */
	public final boolean argsVariadic;

	static AnnotationDefinitionBuilder define(final String name) {
		final AnnotationDefinitionBuilder adb = new AnnotationDefinitionBuilder();
		adb.name = name;
		return adb;
	}

	static class AnnotationDefinitionBuilder {
		String name;
		EClass[] targets;
		String[] javaScriptVariants;
		EClass[] targetsWithCustomError = {};
		boolean repeatable = false;
		RetentionPolicy retention = RetentionPolicy.AST;
		EClass[] argtypes = {};
		boolean argsOptional = false;
		boolean transitive = false;
		boolean argsVariadic = false;

		AnnotationDefinitionBuilder targets(final EClass... _targets) {
			this.targets = _targets;
			return this;
		}

		AnnotationDefinitionBuilder javaScriptVariants(final String... variants) {
			this.javaScriptVariants = variants;
			return this;
		}

		AnnotationDefinitionBuilder targetsWithCustomError(final EClass... _targetsWithCustomError) {
			this.targetsWithCustomError = _targetsWithCustomError;
			return this;
		}

		AnnotationDefinitionBuilder repeatable() {
			this.repeatable = true;
			return this;
		}

		/**
		 * Use TypeRef, StringLiteral, BooleanLiteral, or NumberLiteral
		 */
		AnnotationDefinitionBuilder args(final EClass... argTypes) {
			this.argtypes = argTypes;
			return this;
		}

		/**
		 * Declares the final argument of all arguments (if any) as variadic.
		 */
		AnnotationDefinitionBuilder argsVariadic() {
			this.argsVariadic = true;
			return this;
		}

		/**
		 * Declares the arguments (all) as optional.
		 */
		AnnotationDefinitionBuilder argsOptional() {
			this.argsOptional = true;
			return this;
		}

		/**
		 * Declares the annotation as transitive.
		 */
		AnnotationDefinitionBuilder transitive() {
			this.transitive = true;
			return this;
		}

		AnnotationDefinitionBuilder retention(final RetentionPolicy _retention) {
			this.retention = _retention;
			return this;
		}

		AnnotationDefinition end() {
			return new AnnotationDefinition(name, targets, targetsWithCustomError, javaScriptVariants,
					transitive, repeatable, retention,
					argtypes, argsOptional, argsVariadic);
		}

	}

	/**
	 * Creates a definition, called only ba {@link AnnotationDefinitionBuilder#end}
	 */
	private AnnotationDefinition(final String name, final EClass[] targets,
			final EClass[] targetsWithCustomError,
			final String[] javaScriptVariants,
			final boolean transitive,
			final boolean repeatable,
			final RetentionPolicy retention,
			final EClass[] argtypes, final boolean argsOptional, final boolean argsVariadic) {
		this.name = name;
		this.targets = targets;
		this.targetsWithCustomError = targetsWithCustomError;
		this.javaScriptVariants = javaScriptVariants;
		this.transitive = transitive;
		this.repeatable = repeatable;
		this.retention = retention;
		this.argsOptional = argsOptional;
		this.argtypes = argtypes;
		this.argsVariadic = argsVariadic;

		if (this.argsVariadic && (null == this.argtypes || 0 == this.argtypes.length)) {
			throw new IllegalArgumentException(
					"Variadic argument flag can be true if the annotation definition has any defined argument types.");
		}

		DEFINED_ANNOTATIONS.put(name, this);
		if (retention == RetentionPolicy.TYPE || retention == RetentionPolicy.RUNTIME) {
			ANNOTATIONS_IN_TYPEMODEL.put(name, this);
		}
	}

	/**
	 * Returns true if the annotation identified by given name is to be copied to the type model, that is, its retention
	 * policy is either {@link RetentionPolicy#TYPE} or {@link RetentionPolicy#RUNTIME}.
	 */
	public static boolean isInTypeModel(final String name) {
		return ANNOTATIONS_IN_TYPEMODEL.containsKey(name);
	}

	/**
	 * Returns (first) AST annotation either defined by the element directly, or, if the annotation is transitive, by a
	 * container. Note that {@link ExportDeclaration}s are transparent, that is, annotations to these declarations are
	 * handled as if they were directly attached to the element itself.
	 */
	public Annotation getAnnotation(final AnnotableElement element) {
		final Annotation annotation = getOwnedAnnotation(element);
		if (annotation == null && element != null && transitive) {
			final AnnotableElement container = getContainerOfType(element.eContainer(), AnnotableElement.class);
			if (container instanceof ExportDeclaration) {
				return getAnnotation(getContainerOfType(container.eContainer(), AnnotableElement.class));
			}
			return getAnnotation(container);
		}
		return annotation;
	}

	/**
	 * Returns (first) type annotation either defined by the element directly, or, if the annotation is transitive, by a
	 * container. Note that {@link ExportDeclaration}s are transparent, that is, annotations to these declarations are
	 * handled as if they were directly attached to the element itself.
	 */
	public TAnnotation getAnnotation(final TAnnotableElement element) {
		final TAnnotation annotation = getOwnedAnnotation(element);
		if (annotation == null && element != null && transitive) {
			return getAnnotation(getContainerOfType(element.eContainer(), TAnnotableElement.class));
		}
		return annotation;
	}

	/**
	 * Returns all AST annotation defined by the element directly or its containers if annotation is transitive. This
	 * method only returns more than one annotation if the annotation is marked as repeatable. Note that
	 * {@link ExportDeclaration}s are transparent, that is, annotations to these declarations are handled as if they
	 * were directly attached to the element itself.
	 *
	 * @return stream with annotations, maybe empty but not null
	 */
	public Iterable<Annotation> getAllAnnotations(final AnnotableElement element) {
		return null == element ? emptyList() : getAllAnnotations(new N4JSAnnotatableWrapper(element));
	}

	/**
	 * Returns all type model annotation defined by the element directly or its containers if annotation is transitive.
	 * This method only returns more than one annotation if the annotation is marked as repeatable. Note that
	 * {@link ExportDeclaration}s are transparent, that is, annotations to these declarations are handled as if they
	 * were directly attached to the element itself.
	 *
	 * @return iterable with annotations, maybe empty but not null
	 */
	public Iterable<TAnnotation> getAllAnnotations(final TAnnotableElement element) {
		return null == element ? emptyList() : getAllAnnotations(new TAnnotatableWrapper(element));
	}

	private <T extends EObject> Iterable<T> getAllAnnotations(final EAnnotatableWrapper<T> element) {

		final Iterable<T> annotations = getAllOwnedAnnotations(element);
		if (!transitive || null == element) {
			return annotations;
		}

		EAnnotatableWrapper<T> container = element.getContainerAnnotatable();
		if (container == null) {
			return annotations;
		}
		if (container.annotatable instanceof ExportDeclaration) {
			container = container.getContainerAnnotatable();
		}

		if (repeatable) {
			return concat(annotations, getAllAnnotations(container));
		} else {
			final T annotation = getFirst(annotations, null);
			return null != annotation ? singletonList(annotation) : getAllAnnotations(container);
		}
	}

	/**
	 * Returns (first) AST annotation defined by the element directly, or null, if no such annotation is defined at the
	 * element.
	 */
	public Annotation getOwnedAnnotation(final AnnotableElement element) {
		if (element == null) {
			return null;
		}

		Annotation ann = getOwnedAnnotation(new N4JSAnnotatableWrapper(element));

		if (ann == null) {
			AnnotableElement contianer = getContainerOfType(element.eContainer(), AnnotableElement.class);
			if (contianer != null && contianer instanceof ExportDeclaration) {
				return getOwnedAnnotation(new N4JSAnnotatableWrapper(contianer));
			}
		}

		return ann;
	}

	/**
	 * Returns (first) type model annotation defined by the element directly, or null, if no such annotation is defined
	 * at the element.
	 */
	public TAnnotation getOwnedAnnotation(final TAnnotableElement element) {
		return element == null ? null : getOwnedAnnotation(new TAnnotatableWrapper(element));
	}

	private <T extends EObject> T getOwnedAnnotation(final EAnnotatableWrapper<T> element) {
		if (null == element) {
			return null;
		}
		final Optional<EAnnotationWrapper<T>> result = element.findFirstAnnotationByName(name);
		if (result.isPresent()) {
			return result.get().getWrappedItem();
		}
		return null;
	}

	/**
	 * Returns all AST annotation defined by the element directly, or null, if no such annotation is defined at the
	 * element. This method only returns more than one annotation if the annotation is marked as repeatable.
	 *
	 * @return iterable with annotations, maybe empty but not null
	 */
	public Iterable<Annotation> getAllOwnedAnnotations(final AnnotableElement element) {
		return null == element ? emptyList() : getAllOwnedAnnotations(new N4JSAnnotatableWrapper(element));
	}

	/**
	 * Returns all type model annotation defined by the element directly, or null, if no such annotation is defined at
	 * the element. This method only returns more than one annotation if the annotation is marked as repeatable.
	 *
	 * @return iterable with annotations, maybe empty but not null.
	 */
	public Iterable<TAnnotation> getAllOwnedAnnotations(final TAnnotableElement element) {
		return null == element ? emptyList() : getAllOwnedAnnotations(new TAnnotatableWrapper(element));
	}

	private <T extends EObject> Iterable<T> getAllOwnedAnnotations(final EAnnotatableWrapper<T> element) {
		if (element == null) {
			return emptyList();
		}
		if (!repeatable) {
			final T first = getOwnedAnnotation(element);
			if (first == null) {
				return emptyList();
			}
			return singletonList(first);
		}
		return from(element.findAnnotationsByName(name)).transform(a -> a.getWrappedItem());
	}

	/**
	 * Returns true if either this annotation is attached directly to the element, or, if the annotation is transitive,
	 * to a container. This works for type elements.
	 */
	public boolean hasOwnedAnnotation(final TAnnotableElement element) {
		return getOwnedAnnotation(element) != null;
	}

	/**
	 * Returns true if either this annotation is attached directly to the element. This works for type model elements.
	 */
	public boolean hasOwnedAnnotation(final AnnotableElement element) {
		return getOwnedAnnotation(element) != null;
	}

	/**
	 * Returns true if either this annotation is attached directly to the element. This works for type elements.
	 */
	public boolean hasAnnotation(final TAnnotableElement element) {
		return getAnnotation(element) != null;
	}

	/**
	 * Returns true if either this annotation is attached directly to the element, or, if the annotation is transitive,
	 * to a container. This works for type model elements.
	 */
	public boolean hasAnnotation(final AnnotableElement element) {
		return getAnnotation(element) != null;
	}

	/**
	 * Wrapper class to avoid code duplication between the instances of type {@link AnnotableElement} and
	 * {@link TAnnotableElement}.
	 */
	/* default */static class EAnnotatableWrapper<A extends EObject> {

		private final EObject annotatable;
		private final Function<EObject, Iterable<EAnnotationWrapper<A>>> annotationResolver;
		private final Class<? extends EObject> containerTypeClass;

		/* default */ EAnnotatableWrapper(final EObject annotatable,
				final Function<EObject, Iterable<EAnnotationWrapper<A>>> annotationSupplier,
				final Class<? extends EObject> containerTypeClass) {

			this.annotatable = annotatable;
			this.annotationResolver = annotationSupplier;
			this.containerTypeClass = containerTypeClass;
		}

		/**
		 * Returns with the first annotation of the annotatable element which name equals with the name argument.
		 *
		 * @return the annotation of the annotatable element which name matches with the specified one. May return with
		 *         an {@link Optional#absent() absent} instance if no annotation matches with the argument but never
		 *         {@code null}.
		 */
		public Optional<EAnnotationWrapper<A>> findFirstAnnotationByName(final String name) {
			checkNotNull(name, "name");
			return tryFind(annotationResolver.apply(annotatable), input -> name.equals(input.getName()));
		}

		/**
		 * Returns with all the annotations of the annotatable element which name equals with the name argument.
		 *
		 * @return the annotations of the annotatable element which name matches with the specified one. May return with
		 *         an an empty iterable but never {@code null}.
		 */
		public Iterable<EAnnotationWrapper<A>> findAnnotationsByName(final String name) {
			checkNotNull(name, "name");
			return from(annotationResolver.apply(annotatable)).filter(input -> name.equals(input.getName()));
		}

		/**
		 * Returns with the annotatable element associated as the container of the wrapped annotatable.
		 *
		 * @return the annotatable element as the container of the wrapped annotatable or {@code null} if the wrapped
		 *         annotatable does not have a container.
		 */
		public EAnnotatableWrapper<A> getContainerAnnotatable() {
			final EObject container = getContainerOfType(getContainer(), containerTypeClass);
			return null == container ? null
					: new EAnnotatableWrapper<>(container, annotationResolver,
							containerTypeClass);
		}

		/**
		 * Delegates into the {@link EObject#eContainer()} of the wrapped object only and if only the wrapped
		 * annotatable is an instance of {@link EObject}.
		 *
		 * @return the container of the wrapped annotatable or {@code null} if the wrapped annotatable does not have a
		 *         container or the container property cannot be interpreted for the wrapped element.
		 */
		public EObject getContainer() {
			return annotatable.eContainer();
		}

		/**
		 * Returns with annotations of the annotatable element.
		 *
		 * @return the annotations of the element. Can be empty but never {@code null}.
		 */
		public Iterable<EAnnotationWrapper<A>> getAnnotations() {
			return annotationResolver.apply(annotatable);
		}

	}

	/**
	 * Wrapper class for avoiding code duplication for type {@link Annotation} and {@link TAnnotation}.
	 */
	/* default */static class EAnnotationWrapper<T extends EObject> {

		private static final Function<EObject, String> NAME_RESOLVER = newResolver(String.class, "name");

		private final T annotation;

		/* default */ EAnnotationWrapper(final T annotation) {
			this.annotation = checkNotNull(annotation, "annotation");
		}

		/**
		 * Returns with the name of of the wrapped annotation.
		 *
		 * @return the name of the annotation.
		 */
		public String getName() {
			return NAME_RESOLVER.apply(annotation);
		}

		/**
		 * Returns with the wrapped annotation;
		 *
		 * @return the wrapped annotation. Never {@code null}.
		 */
		public T getWrappedItem() {
			return annotation;
		}

	}

	/**
	 * Wrapper for the AST {@link AnnotableElement annotatable element}.
	 */
	/* default */static class N4JSAnnotatableWrapper extends EAnnotatableWrapper<Annotation> {

		private static final Function<EObject, Iterable<EAnnotationWrapper<Annotation>>> SUPPLIER = element -> from(
				getASTAnnotations(element)).transform(annotation -> new N4JSAnnotationWrapper(annotation));

		/* default */ N4JSAnnotatableWrapper(final AnnotableElement annotatable) {
			super(annotatable, SUPPLIER, AnnotableElement.class);
		}

		/**
		 * take care for {@link ExportDeclaration}s to be included. Note that {@link TAnnotatableWrapper} does nto have
		 * to do this as types builder is taking care of that.
		 *
		 * @param element
		 *            from which we want to obtain annotations
		 * @return iterable with annotations attached to the provided element
		 */
		private static final Iterable<Annotation> getASTAnnotations(EObject element) {
			Iterable<Annotation> anns = ((AnnotableElement) element).getAnnotations();
			if (element.eContainer() != null && element.eContainer() instanceof ExportDeclaration) {
				return concat(anns, ((AnnotableElement) element.eContainer()).getAnnotations());
			}
			return anns;
		}
	}

	/**
	 * Wrapper for the type model {@link TAnnotableElement annotatable element}.
	 */
	/* default */static class TAnnotatableWrapper extends EAnnotatableWrapper<TAnnotation> {

		private static final Function<EObject, Iterable<EAnnotationWrapper<TAnnotation>>> SUPPLIER = element -> from(
				((TAnnotableElement) element).getAnnotations())
						.transform(annotation -> new TAnnotationWrapper(annotation));

		/* default */ TAnnotatableWrapper(final TAnnotableElement annotatable) {
			super(annotatable, SUPPLIER, TAnnotableElement.class);
		}

	}

	/**
	 * Wrapper for the AST {@link Annotation annotation}.
	 */
	/* default */static class N4JSAnnotationWrapper extends EAnnotationWrapper<Annotation> {

		/* default */ N4JSAnnotationWrapper(final Annotation annotation) {
			super(annotation);
		}

	}

	/**
	 * Wrapper for the type model {@link TAnnotation annotation}.
	 */
	/* default */static class TAnnotationWrapper extends EAnnotationWrapper<TAnnotation> {

		/* default */ TAnnotationWrapper(final TAnnotation annotation) {
			super(annotation);
		}

	}

}
