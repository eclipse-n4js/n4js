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
package org.eclipse.n4js.utils

import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal
import java.util.Properties
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.common.unicode.CharTypes
import org.eclipse.n4js.compileTime.CompileTimeValue
import org.eclipse.n4js.n4JS.AbstractAnnotationList
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.AssignmentOperator
import org.eclipse.n4js.n4JS.ConditionalExpression
import org.eclipse.n4js.n4JS.DestructureUtils
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.IndexedAccessExpression
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberAnnotationList
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.N4TypeVariable
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.NullLiteral
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.PropertyAssignment
import org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration
import org.eclipse.n4js.n4JS.PropertyNameKind
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription
import org.eclipse.n4js.packagejson.projectDescription.ProjectType
import org.eclipse.n4js.parser.conversion.IdentifierValueConverter
import org.eclipse.n4js.postprocessing.ASTMetaInfoCache
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.scoping.utils.UnresolvableObjectDescription
import org.eclipse.n4js.ts.typeRefs.BooleanLiteralTypeRef
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef
import org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.StringLiteralTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.AnyType
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TAnnotableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TN4Classifier
import org.eclipse.n4js.ts.types.TStructMember
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeAlias
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.types.util.AllSuperTypesCollector
import org.eclipse.n4js.ts.types.util.ExtendedClassesIterable
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.types.utils.TypeCompareUtils
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope

import static org.eclipse.n4js.N4JSLanguageConstants.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Intended for small, static utility methods that
 * <ul>
 * <li>need both the AST and types model or code from the main n4js bundle, and can therefore not be put into
 *     {@link N4JSASTUtils} and {@link TypeUtils}.
 * <li>implement a fundamental rule or logic of the core language that defines an important part of N4JS semantics.
 * </ul>
 *
 * @see N4JSASTUtils
 * @see TypeUtils
 */
public class N4JSLanguageUtils {

	/**
	 * Tells whether the {@link #isOpaqueModule(ProjectType, URI) "opaque modules"} feature is turned on
	 * (i.e. processing of opaque modules is suppressed).
	 * <p>
	 * WARNING: this field is set via reflection from {@code JSActivationUtil}.
	 */
	public static boolean OPAQUE_JS_MODULES = true;

	/**
	 * Special name used for {@link TMethod}s that represent a call signature. Only used in type model
	 * (not in AST). Do not use this name to check if a {@code TMethod} is a call signature; use
	 * {@link TFunction#isCallSignature() #isCallSignature()} for this purpose.
	 */
	public static final String CALL_SIGNATURE_NAME = "()";

	/**
	 * Prefix used in names of members that are identified by a built-in symbol.
	 * <p>
	 * Take this example:
	 *
	 * <pre>
	 * class C&lt;T> {
	 *     Iterator&lt;T> [Symbol.iterator]() {
	 *         // ...
	 *     }
	 * }
	 * </pre>
	 *
	 * Here, class C has a single method identified by built-in symbol 'iterator'. Internally, this will be represented
	 * by a method with name {@link #SYMBOL_IDENTIFIER_PREFIX} + "iterator".
	 */
	public static final String SYMBOL_IDENTIFIER_PREFIX = "#";

	/**
	 * Internally special-casing [Symbol.iterator] as a member named hash-iterator.
	 */
	public static final String SYMBOL_ITERATOR_MANGLED = SYMBOL_IDENTIFIER_PREFIX + "iterator";

	/**
	 * Internally special-casing [Symbol.asyncIterator] as a member named hash-iterator.
	 */
	public static final String SYMBOL_ASYNC_ITERATOR_MANGLED = SYMBOL_IDENTIFIER_PREFIX + "asyncIterator";

	/**
	 * The default language version returned by method {@link #getLanguageVersion()} in case no actual
	 * language version/commit was set during the build. See {@link #getLanguageVersion()} for details.
	 */
	public static final String DEFAULT_LANGUAGE_VERSION = "0.0.0.v19990101_0000";

	/**
	 * The default language commit hash returned by method {@link #getLanguageCommit()} in case no actual
	 * language version/commit was set during the build. See {@link #getLanguageVersion()} for details.
	 */
	public static final String DEFAULT_LANGUAGE_COMMIT = "0000000000000000000000000000000000000000";

	private static final String LANGUAGE_VERSION_PROPERTIES_FILE_NAME = "language-version.properties";

	private static String languageVersionStr = null;
	private static String languageCommitStr = null;

	/**
	 * Returns the N4JS language version as defined in file {@value #LANGUAGE_VERSION_PROPERTIES_FILE_NAME}.
	 * <p>
	 * An actual, meaningful version is only returned if it was set during the build by script
	 * <code>compute-version.sh</code>; otherwise the default version {@link #DEFAULT_LANGUAGE_VERSION} is
	 * returned. Since only publishing builds set the version, this default version will be returned in all
	 * non-production cases, e.g. during debugging, local testing, testing during maven builds (both in CI
	 * builds and the nightly builds).
	 */
	def public static String getLanguageVersion() {
		if (languageVersionStr === null) {
			languageVersionStr = readLanguageVersionProperty("language.version");
		}
		return languageVersionStr;
	}

	/** Like {@link #getLanguageVersion()}, but for the git commit hash the language version was built from. */
	def public static String getLanguageCommit() {
		if (languageCommitStr === null) {
			languageCommitStr = readLanguageVersionProperty("language.commit");
		}
		return languageCommitStr;
	}

	def private static String readLanguageVersionProperty(String propertyId) {
		var Properties properties;
		try (val InputStream in = N4JSLanguageUtils.getClassLoader().getResourceAsStream(LANGUAGE_VERSION_PROPERTIES_FILE_NAME)) {
			if (in === null) {
				throw new RuntimeException("unable to find properties file " + LANGUAGE_VERSION_PROPERTIES_FILE_NAME);
			}
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			throw new RuntimeException("unable to load properties file " + LANGUAGE_VERSION_PROPERTIES_FILE_NAME, e);
		}
		val value = properties.getProperty(propertyId);
		if (value === null) {
			throw new RuntimeException("properties file " + LANGUAGE_VERSION_PROPERTIES_FILE_NAME + " does not contain property " + propertyId);
		}
		return value;
	}

	/** Tells whether .d.ts file generation is <em>actually</em> active in a given project. */
	def public static boolean isDtsGenerationActive(ProjectDescription pd) {
		return pd !== null && pd.isGeneratorEnabledDts() && !N4JSGlobals.PROJECT_TYPES_WITHOUT_DTS_GENERATION.contains(pd.type);
	}

	/** Tells whether the given module is the {@link ProjectDescription#getMainModule() main module} of the given project. */
	def public static boolean isMainModule(N4JSProjectConfigSnapshot project, TModule module) {
		val qn = module.qualifiedName;
		return isMainModule(project, qn);
	}

	/** Tells whether the module with the given qualified name is the {@link ProjectDescription#getMainModule() main module} of the given project. */
	def public static boolean isMainModule(N4JSProjectConfigSnapshot project, String moduleQualifiedName) {
		return project.mainModule == moduleQualifiedName;
	}

	/** Convenience method for {@link #isOpaqueModule(ProjectType, URI)}. */
	def public static boolean isOpaqueModule(N4JSWorkspaceConfigSnapshot workspaceConfig, URI resourceURI) {
		val project = workspaceConfig?.findProjectContaining(resourceURI);
		val projectType = project?.getType();
		return N4JSLanguageUtils.isOpaqueModule(projectType, resourceURI);
	}

	/**
	 * Tells whether a module with the given properties is an "opaque module". These modules
	 * are neither post-processed nor validated. The transpiler will only wrap opaque resources.
	 * <p>
	 * Note that if dependency injection is available, this more convenient method can be used:
	 * {@link N4JSLanguageHelper#isOpaqueModule(org.eclipse.emf.ecore.resource.Resource)}.
	 */
	def public static boolean isOpaqueModule(ProjectType typeOfContainingProject, URI resourceURI) {
		val ResourceType resourceType = ResourceType.getResourceType(resourceURI);

		if (resourceType === ResourceType.JS
			|| resourceType === ResourceType.JSX) {

			return OPAQUE_JS_MODULES; // JavaScript modules are not processed iff OPAQUE_JS_MODULES is true

		} else if (resourceType === ResourceType.N4JS
			|| resourceType === ResourceType.N4JSX) {

			if (typeOfContainingProject === null) {
				return false; // happens in tests
			}
			// N4JS files of definition projects are not processed.
			return typeOfContainingProject === ProjectType.DEFINITION;

		} else if (resourceType === ResourceType.N4JSD
			|| resourceType === ResourceType.UNKOWN
			|| resourceType === ResourceType.XT) {

			// default
		}

		// default: process file
		return false;
	}

	/**
	 * Some {@link IEObjectDescription}s returned by our {@link IScope scope} implementations do not represent actual,
	 * valid elements in the scope but are used only for error reporting, etc. This method returns <code>true</code>
	 * iff the given description represents an actual, existing element.
	 */
	def static boolean isActualElementInScope(IEObjectDescription desc) {
		if (desc instanceof IEObjectDescriptionWithError
			|| desc instanceof UnresolvableObjectDescription) {
			return false;
		}
		return true;
	}

	static enum EnumKind {
		Normal,
		NumberBased,
		StringBased
	}

	def static EnumKind getEnumKind(N4EnumDeclaration enumDecl) {
		if (AnnotationDefinition.NUMBER_BASED.hasAnnotation(enumDecl)) {
			return EnumKind.NumberBased;
		} else if (AnnotationDefinition.STRING_BASED.hasAnnotation(enumDecl)) {
			return EnumKind.StringBased;
		}
		return EnumKind.Normal;
	}

	def static EnumKind getEnumKind(TEnum tEnum) {
		if (AnnotationDefinition.NUMBER_BASED.hasAnnotation(tEnum)) {
			return EnumKind.NumberBased;
		} else if (AnnotationDefinition.STRING_BASED.hasAnnotation(tEnum)) {
			return EnumKind.StringBased;
		}
		return EnumKind.Normal;
	}

	/**
	 * Tells whether the given enum literal has a valid value expression (i.e. a string literal or
	 * a, possibly negated, number literal). */
	def static boolean isEnumLiteralValueExpressionValid(N4EnumLiteral n4EnumLiteral) {
		return n4EnumLiteral?.valueExpression === null || getEnumLiteralValue(n4EnumLiteral) !== null;
	}

	/**
	 * Obtains from the AST the value of the given enum literal. Returns <code>null</code> iff the
	 * enum literal has an invalid value expression.
	 */
	def static Object getEnumLiteralValue(N4EnumLiteral n4EnumLiteral) {
		val valueExpr = n4EnumLiteral?.valueExpression;
		if (valueExpr instanceof StringLiteral) {
			return valueExpr.valueAsString;
		} else if (valueExpr instanceof NumericLiteral) {
			return valueExpr.value;
		} else if (valueExpr instanceof UnaryExpression) {
			val expr = valueExpr.expression;
			if (expr instanceof NumericLiteral) {
				if (valueExpr.op === UnaryOperator.NEG) {
					return expr.value?.negate;
				} else if (valueExpr.op === UnaryOperator.POS) {
					return expr.value;
				}
			}
		}
		return null;
	}

	/**
	 * If the given function definition is asynchronous, will wrap given return type into a Promise.
	 * Otherwise, returns given return type unchanged. A return type of <code>void</code> is changed to
	 * <code>undefined</code>.
	 */
	def static TypeRef makePromiseIfAsync(FunctionDefinition funDef, TypeRef returnTypeRef,	BuiltInTypeScope builtInTypeScope) {
		if (funDef !== null && returnTypeRef !== null) {
			if (funDef.isAsync()) {
				// for async functions with declared return type R: actual return type is Promise<R,?>
				return TypeUtils.createPromiseTypeRef(builtInTypeScope, returnTypeRef, null);
			}
			return returnTypeRef;
		}
		return null;
	}

	/**
	 * If the given function definition is a generator function, will wrap given return type into a Generator.
	 * Otherwise, returns given return type unchanged. A return type of <code>void</code> is changed to
	 * <code>undefined</code>.
	 */
	def static TypeRef makeGeneratorIfGeneratorFunction(FunctionDefinition funDef, TypeRef returnTypeRef, BuiltInTypeScope builtInTypeScope) {
		if (funDef !== null && returnTypeRef !== null) {
			if (funDef.isGenerator()) {
				// for generator functions with declared return type R: actual return type is Generator<R,R,any>
				return TypeUtils.createGeneratorTypeRef(builtInTypeScope, funDef);
			}
			return returnTypeRef;
		}
		return null;
	}

	/**
	 * Returns <code>true</code> iff the given type reference points to the expected special return type of the given
	 * asynchronous and/or generator function OR the given function is neither asynchronous nor a generator.
	 * <p>
	 * WARNING: returns <code>false</code> for subtypes of the expected special return types!
	 */
	def static boolean hasExpectedSpecialReturnType(TypeRef typeRef, FunctionDefinition funDef, BuiltInTypeScope scope) {
		val expectedType = getExpectedSpecialReturnType(funDef, scope);
		return expectedType === null || typeRef.declaredType === expectedType;
	}

	/**
	 * If the given function is an asynchronous and/or generator function, this method returns the expected special return type;
	 * otherwise <code>null</code> is returned.
	 */
	def static Type getExpectedSpecialReturnType(FunctionDefinition funDef, BuiltInTypeScope scope) {
		return getExpectedSpecialReturnType(funDef.async, funDef.generator, scope);
	}

	/**
	 * Returns the expected special return type of an asynchronous and/or generator function, or <code>null</code> if both
	 * 'isAsync' and 'isGenerator' are <code>false</code>.
	 */
	def static Type getExpectedSpecialReturnType(boolean isAsync, boolean isGenerator, BuiltInTypeScope scope) {
		if (isGenerator) {
			if (isAsync) {
				return scope.asyncGeneratorType;
			} else {
				return scope.generatorType;
			}
		} else {
			if (isAsync) {
				return scope.promiseType;
			} else {
				return null;
			}
		}
	}

	/** See {@link N4JSASTUtils#isASTNode(EObject)}. */
	def static boolean isASTNode(EObject obj) {
		return N4JSASTUtils.isASTNode(obj);
	}

	/**
	 * Tells if given expression denotes the value 'undefined'.
	 */
	def static boolean isUndefinedLiteral(RuleEnvironment G, Expression expr) {
		if(expr instanceof IdentifierRef) {
			return expr.id===G.globalObjectScope.fieldUndefined;
		}
		return false;
	}

	/**
	 * Tells if given object is a <em>type model element</em>, i.e. is contained below a {@link TModule} element.
	 * <p>
	 * Note that it is not possible to tell AST nodes from type model elements only based on the object's type, because
	 * there exist type model entities that may appear as a node in the AST (e.g. some TypeRefs, TStructField).
	 */
	def static boolean isTypeModelElement(EObject obj) {
		// note: despite its name, #getContainerOfType() returns 'obj' if instance of TModule
		return EcoreUtil2.getContainerOfType(obj, TModule)!==null;
	}

	/**
	 * Tells if given AST node is a typable AST node, i.e. a node that has an (actual) type that can be inferred
	 * using the type system. When <code>true</code> is returned, the given AST node can safely be casted to
	 * {@link TypableElement}.
	 * <p>
	 * For performance reasons, this method will simply assume {@code astNode} to be an AST node (i.e. contained below
	 * a {@link Script} element) and will not check this again.
	 */
	def static boolean isTypableNode(EObject astNode) {
		astNode instanceof TypableElement && !(astNode instanceof AbstractAnnotationList)
	}

	def static boolean isIdentifiableSubtree(EObject astNode) {
		astNode instanceof IdentifiableElement
		|| astNode.getDefinedTypeModelElement instanceof IdentifiableElement
		|| astNode instanceof FunctionDeclaration
		// classes with compile error, e.g. missing name, do not necessarily have DefinedTypeModelElement
		// thus the additional check below
		|| astNode instanceof N4ClassDeclaration
	}

	def static boolean isTypeModelElementDefiningASTNode(EObject astNode) {
		astNode instanceof ExportedVariableDeclaration
		|| astNode instanceof TypeDefiningElement
		|| (astNode instanceof N4MemberDeclaration && !(astNode instanceof N4MemberAnnotationList))
		|| (astNode instanceof PropertyAssignment && !(astNode instanceof PropertyAssignmentAnnotationList))
		|| astNode instanceof FormalParameter
		|| astNode instanceof TStructMember // they can play the role of AST nodes!
		|| astNode instanceof N4EnumLiteral
		|| astNode instanceof N4TypeVariable
	}

	def static EObject getDefinedTypeModelElement(EObject astNode) {
		switch(astNode) {
			ExportedVariableDeclaration: astNode.definedVariable
			PropertyMethodDeclaration: astNode.definedMember
			TypeDefiningElement: astNode.definedType
			N4MemberDeclaration case !(astNode instanceof N4MemberAnnotationList): astNode.definedTypeElement
			PropertyAssignment case !(astNode instanceof PropertyAssignmentAnnotationList): astNode.definedMember
			FormalParameter: astNode.definedTypeElement
			TStructMember case astNode.isASTNode: astNode.definedMember // note: a TStructMember may be an AST node or types model element!
			N4EnumLiteral: astNode.definedLiteral
			N4TypeVariable: astNode.definedTypeVariable
		}
	}

	/**
	 * Returns with {@code true} if the {@link TMember member} argument represents a constructor.
	 * More precisely, when the argument is an instance of {@link TMethod} and its {@link TMethod#getName() name}
	 * is {@code constructor}. Otherwise returns with {@code false}.
	 */
	def static isConstructor(TMember it) {
		return it instanceof TMethod && CONSTRUCTOR == name;
	}

	/**
	 * Returns with {@code true} if the member argument is a {@link TField} instance and the field is
	 * {@link TField#isWriteable() writable}, otherwise returns with {@code false}.
	 */
	def static isWriteableField(TMember m) {
		return m instanceof TField && m.writeable;
	}

	/**
	 * Returns with {@code true} if the member argument is a {@link TField} instance and the field is
	 * <b>NOT</b> {@link TField#isWriteable() writable}, otherwise returns with {@code false}.
	 */
	def static isReadOnlyField(TMember m) {
		return m instanceof TField && !m.writeable;
	}

	/**
	 * Tells if the given identifiable element is exported.
	 */
	def static boolean isExported(IdentifiableElement elem) {
		return switch(elem) {
			ExportedVariableDeclaration: true
			TVariable: elem.exported
			Type: elem.exported
			default: false
		};
	}

	/**
	 * Is the given TFunction tagged ASYNC, and moreover does it return Promise?
	 */
	def static boolean isAsync(TFunction tfunction, BuiltInTypeScope scope) {
		if (tfunction.declaredAsync) {
			if (tfunction.returnTypeRef instanceof ParameterizedTypeRef) {
				return TypeUtils.isPromise(tfunction.returnTypeRef, scope)
			}
		}
		return false;
	}

	/**
	 * Does the given function-type denote an async function?
	 * (two cases: declared type available or not, in the latter case heuristically assume Promise-returning implies async).
	 * <p>
	 * The declared type (ie, a TFunction) is usually but not always available.
	 */
	def static boolean isAsync(FunctionTypeExprOrRef fteor, RuleEnvironment G) {
		val tfunction = fteor.functionType
		val tscope = RuleEnvironmentExtensions.getPredefinedTypes(G).builtInTypeScope
		if (null === tfunction) {
			return TypeUtils.isPromise(fteor.returnTypeRef, tscope)
		} else {
			return N4JSLanguageUtils.isAsync(tfunction, tscope)
		}
	}

	/**
	 * Tells if a value of the given type can be instantiated, i.e. whether
	 * <pre>
	 * new value();
	 * </pre>
	 * is legal, given a variable {@code value} of type {@code ctorTypeRef}.
	 */
	def public static boolean isInstantiable(TypeTypeRef typeTypeRef) {
		val typeArg = typeTypeRef.typeArg;
		if(typeArg instanceof Wildcard || typeArg instanceof ExistentialTypeRef) {
			return false;
		}
		val pseudoStaticType = (typeArg as TypeRef).declaredType;
		return pseudoStaticType instanceof TN4Classifier;
	}

	/**
	 * Returns the variance of the given type reference's position within its containing classifier declaration or
	 * <code>null</code> if
	 * <ol>
	 * <li>it is located at a position where type variables of any variance may be located, i.e. a position that need
	 *     not be checked (e.g. type of a local variable in a method, type of a private field),
	 * <li>it is not contained in a classifier declaration,
	 * <li>it is <code>null</code>, or
	 * <li>some error occurred, e.g. invalid TModule, broken AST.
	 * </ol>
	 */
	def public static Variance getVarianceOfPosition(TypeRef typeRefInAST) {
		// note: we have commutativity, so normally order would not matter below; however, due to the quick exit on INV
		// together with the special cases of private members and final fields (handled via a return type of null)
		// we must check position in classifier first!
		val v1 = getVarianceOfPositionInClassifier(typeRefInAST);
		if(v1===null || v1===Variance.INV) {
			return v1;
		}
		val v2 = getVarianceOfPositionRelativeToItsRoot(typeRefInAST);
		return v1.mult(v2);
	}
	/**
	 * Most client code should use {@link #getVarianceOfPosition(TypeRef)}!
	 * <p>
	 * Same as {@link #getVarianceOfPosition(TypeRef)}, but <b>does not take into account nesting of type references
	 * within other type references.</b> This is covered by method {@link #getVarianceOfPositionRelativeToItsRoot(TypeRef)}.
	 */
	def public static Variance getVarianceOfPositionInClassifier(TypeRef typeRefInAST) {
		if(typeRefInAST===null)
			return null;
		val rootTypeRefInAST = TypeUtils.getRootTypeRef(typeRefInAST);
		val tClassifier = EcoreUtil2.getContainerOfType(rootTypeRefInAST, N4ClassifierDeclaration)?.definedType as TClassifier;
		if(tClassifier===null)
			return null; // not contained in a class/interface declaration with a properly defined type in TModule
		val parent = rootTypeRefInAST.eContainer?.eContainer; // note: skipping the TypeReferenceNode<?> here!
		val grandParent = parent?.eContainer;
		return switch(parent) {
			FormalParameter case parent.declaredTypeRefInAST===rootTypeRefInAST && grandParent.isNonPrivateMemberOf(tClassifier):
				Variance.CONTRA
			N4MethodDeclaration case parent.declaredReturnTypeRefInAST===rootTypeRefInAST && parent.isNonPrivateMemberOf(tClassifier):
				Variance.CO
			N4GetterDeclaration case parent.declaredTypeRefInAST===rootTypeRefInAST && parent.isNonPrivateMemberOf(tClassifier):
				Variance.CO
			N4FieldDeclaration case parent.declaredTypeRefInAST===rootTypeRefInAST && parent.isNonPrivateMemberOf(tClassifier): {
				val tField = parent.definedField;
				if(tField.final) {
					Variance.CO // final field is like a getter
				} else {
					Variance.INV
				}
			}
			N4ClassifierDeclaration case parent.superClassifierRefs.exists[it?.typeRefInAST===rootTypeRefInAST]: {
				// typeRef is used in the "extends" or "implements" clause of the declaration of tClassifier
				// -> this mainly depends on the variance of the classifier being extended
				Variance.CO
			}
			default:
				null
		};
	}
	/**
	 * Most client code should use {@link #getVarianceOfPosition(TypeRef)}!
	 * <p>
	 * Returns variance of the given type reference's position relative to its root type reference as defined by
	 * {@link TypeUtils#getRootTypeRef(TypeRef)}. In case of error, a best effort is made. Never returns
	 * <code>null</code>.
	 */
	def public static Variance getVarianceOfPositionRelativeToItsRoot(TypeRef typeRefInAST) {
		var v = Variance.CO;
		var curr = typeRefInAST;
		while(curr!==null) {
			val parent = EcoreUtil2.getContainerOfType(curr.eContainer, TypeRef);
			if(parent!==null) {
				var Variance vFactor = null;
				// case #1: curr is nested in parent's type arguments
				val parentDeclType = parent.declaredType;
				val parentTypeArgs = parent.declaredTypeArgs;
				val parentTypeArgsSize = parentTypeArgs.size;
				for(var idx=0;vFactor===null && idx<parentTypeArgsSize;idx++) {
					val arg = parentTypeArgs.get(idx);
					vFactor = if(arg===curr) {
						val correspondingTypeVar = if(idx>=0 && idx<parentDeclType.typeVars.size) parentDeclType.typeVars.get(idx) else null;
						val incomingVariance = correspondingTypeVar?.variance ?: Variance.CO; // if null then ignore, i.e. use CO (that error will be covered elsewhere)
						incomingVariance
					} else if(arg instanceof Wildcard) {
						if(arg.declaredUpperBound===curr) {
							Variance.CO
						} else if(arg.declaredLowerBound===curr) {
							Variance.CONTRA
						}
					};
					// note: will break as soon as vFactor!=null
				}
				// other cases:
				if(vFactor===null) {
					val currFixed = curr; // only required to allow using 'curr' in closures
					vFactor = switch(parent) {
					ComposedTypeRef case parent.typeRefs.contains(curr):
						Variance.CO
					TypeTypeRef case parent.typeArg===curr:
						if (parent.isConstructorRef) {
							Variance.INV // constructor{T}
						} else {
							Variance.CO // type{T}
						}
					TypeTypeRef case parent.typeArg instanceof Wildcard: {
						val wc = parent.typeArg as Wildcard;
						if(wc.declaredUpperBound===curr) {
							Variance.CO // type{? extends T} OR constructor{? extends T}
						} else if(wc.declaredLowerBound===curr) {
							Variance.CONTRA // type{? super T} OR constructor{? super T}
						}
					}
					BoundThisTypeRef case parent.actualThisTypeRef===curr:
						Variance.CO // note: this should never happen in the typical use cases of this method,
						// because BoundThisTypeRefs do not appear in AST but are created programmatically
					FunctionTypeExpression /*X*/ case parent.returnTypeRef===curr:
						Variance.CO
					FunctionTypeExpression /*X*/ case parent.fpars.exists[it.typeRef===currFixed]:
						Variance.CONTRA
					};
					// *X* this is one of the rare cases where we have to use FunctionTypeExpression and not its
					// super class FunctionTypeExprOrRef!
				}
				if(vFactor===null) {
					// note: there should not be any other cases of containment of one type reference in another
					// (many type references cannot contain other type references at all, e.g. FunctionTypeRef,
					// ParameterizedTypeRefStructural, EnumTypeRef)
					throw new IllegalStateException("internal error: unsupported case of containment of one typeRef in another (maybe types model has changed?)")
				}
				// apply vFactor to v
				v = v.mult(vFactor);
				if(v===Variance.INV) {
					return v; // won't change anymore
				}
			}
			curr = parent;
		}
		return v;
	}

	def private static boolean isNonPrivateMemberOf(EObject member, TClassifier tClassifier) {
		if(member instanceof N4MemberDeclaration) {
			val tMember = member.definedTypeElement;
			return tMember!==null
				&& !tMember.isConstructor
				&& tMember.memberAccessModifier!==MemberAccessModifier.PRIVATE
				&& tMember.containingType === tClassifier;
		}
		return false;
	}


	/**
	 * Tells whether 'baseTypeRefCandidate' is the given literal type's {@link #getLiteralTypeBase(RuleEnvironment, TypeRef)
	 * base type}, without requiring a rule environment and with considering the semantic equality of 'int' and 'number'.
	 */
	def public static boolean isLiteralTypeBase(LiteralTypeRef literalTypeRef, TypeRef baseTypeRefCandidate) {
		// the only chance for success is that 'baseTypeRefCandidate' is a type reference pointing to a primitive
		// type, so on the success path we can assume that 'baseTypeRefCandidate' will give us a declared type we
		// can use for building a rule environment (to avoid the need for clients to pass in a rule environment):
		val declType = baseTypeRefCandidate.getDeclaredType();
		if (declType !== null) {
			val G = declType.newRuleEnvironment;
			val baseTypeRef = getLiteralTypeBase(G, literalTypeRef);
			if (TypeCompareUtils.isEqual(baseTypeRef, baseTypeRefCandidate)) {
				return true;
			}
			val tInt = G.intType;
			val tNumber = G.numberType;
			if ((declType === tInt && baseTypeRef.declaredType === tNumber)
				|| (declType === tNumber && baseTypeRef.declaredType === tInt)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Same as {@link #getLiteralTypeBase(RuleEnvironment, LiteralTypeRef)}, but accepts
	 * type references of any kind and converts them only if they are {@link LiteralTypeRef}s.
	 * <p>
	 * Note that this does not support nesting, i.e. literal type references nested within
	 * a type reference of another kind won't be converted!
	 */
	def public static TypeRef getLiteralTypeBase(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof LiteralTypeRef) {
			return getLiteralTypeBase(G, typeRef);
		}
		return typeRef;
	}

	/**
	 * Returns the "base type" for the given literal type, e.g. type string for literal type "hello".
	 */
	def public static TypeRef getLiteralTypeBase(RuleEnvironment G, LiteralTypeRef literalTypeRef) {
		return switch(literalTypeRef) {
			BooleanLiteralTypeRef: G.booleanTypeRef
			NumericLiteralTypeRef: getLiteralTypeBase(G, literalTypeRef)
			StringLiteralTypeRef: G.stringTypeRef
			EnumLiteralTypeRef: getLiteralTypeBase(G, literalTypeRef)
			default: throw new UnsupportedOperationException("unknown subclass of " + LiteralTypeRef.simpleName)
		};
	}

	/**
	 * Same as {@link #getLiteralTypeBase(RuleEnvironment, LiteralTypeRef)}, but accepts only numeric
	 * literal type references.
	 */
	def public static TypeRef getLiteralTypeBase(RuleEnvironment G, NumericLiteralTypeRef literalTypeRef) {
		return if (isInt32(literalTypeRef.value)) G.intTypeRef else G.numberTypeRef;
	}

	/**
	 * Same as {@link #getLiteralTypeBase(RuleEnvironment, LiteralTypeRef)}, but accepts only enum
	 * literal type references.
	 */
	def public static TypeRef getLiteralTypeBase(RuleEnvironment G, EnumLiteralTypeRef literalTypeRef) {
		val enumType = literalTypeRef.enumType;
		return if (enumType !== null) TypeUtils.createTypeRef(enumType) else TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
	}

	/**
	 * Tells whether the given {@link BigDecimal} represents a Javascript int32.
	 * <p>
	 * Some notes:
	 * <ol>
	 * <li>the range of int32 is asymmetric: [ -2147483648, 2147483647 ]
	 * <li>in Java, literals such as 1E0 etc. are always of type double, but we cannot follow the same rule here, because
	 * the literal is not available and on the basis of the given {@code BigDecimal} we cannot distinguish this.
	 * <li>hexadecimal and octal literals are always interpreted as positive integers (important difference to Java).
	 * </ol>
	 * See N4JS Specification, Section 8.1.3.1 for details.
	 */
	def static boolean isInt32(BigDecimal value) {
		if (value === null) {
			return false;
		}
		// note: normally the correct way of telling whether a BigDecimal is a whole number would be:
		// boolean isWholeNumber = value.stripTrailingZeros().scale() <= 0;
		// However, we here want BigDecimals like 0.0, 1.00, -42.0 to *NOT* be treated as a whole numbers,
		// so we instead go with:
		val isWholeNumber = value.scale() <= 0;
		return isWholeNumber
			&& N4JSGlobals.INT32_MIN_VALUE_BD.compareTo(value) <= 0
			&& value.compareTo(N4JSGlobals.INT32_MAX_VALUE_BD) <= 0;
	}

	/** Checks presence of {@link AnnotationDefinition#POLYFILL} annotation. See also {@link N4JSLanguageUtils#isStaticPolyfill(AnnotableElement) }*/
	def static boolean isNonStaticPolyfill(AnnotableElement astElement) {
		return AnnotationDefinition.POLYFILL.hasAnnotation( astElement );
	}

	/** Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL} annotation. See also {@link N4JSLanguageUtils#isPolyfill(AnnotableElement) }*/
	def static boolean isStaticPolyfill(AnnotableElement astElement) {
		return AnnotationDefinition.STATIC_POLYFILL.hasAnnotation( astElement );
	}

	/** Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL_MODULE} annotation on the containing module.
	 * See also {@link N4JSLanguageUtils#isContainedInStaticPolyfillAware(AnnotableElement) }*/
	def static boolean isContainedInStaticPolyfillModule(AnnotableElement astElement) {
		return AnnotationDefinition.STATIC_POLYFILL_MODULE.hasAnnotation( astElement ); // transitively inherited
	}

	/** Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL_MODULE} annotation on the containing module.
	 * See also {@link N4JSLanguageUtils#isContainedInStaticPolyfillAware(TAnnotableElement) }*/
	def static boolean isContainedInStaticPolyfillModule(TAnnotableElement tsElement) {
		return AnnotationDefinition.STATIC_POLYFILL_MODULE.hasAnnotation( tsElement ); // transitively inherited
	}

	/** Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL_AWARE} annotation on the containing module.
	 * See also {@link N4JSLanguageUtils#isContainedInStaticPolyfillModule(AnnotableElement) }*/
	def static boolean isContainedInStaticPolyfillAware(AnnotableElement astElement) {
		return AnnotationDefinition.STATIC_POLYFILL_AWARE.hasAnnotation( astElement ); // transitively inherited
	}

	/** Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL_AWARE} annotation on the containing module.
	 * See also {@link N4JSLanguageUtils#isContainedInStaticPolyfillModule(TAnnotableElement) }*/
	def static boolean isContainedInStaticPolyfillAware(TAnnotableElement tsElement) {
		return AnnotationDefinition.STATIC_POLYFILL_AWARE.hasAnnotation( tsElement ); // transitively inherited
	}

	/** checks if the qualifiedName has a last segment named 'default' {@link N4JSLanguageConstants#EXPORT_DEFAULT_NAME} */
	def static boolean isDefaultExport(QualifiedName qualifiedName) {
		return  ( qualifiedName !== null
				&& qualifiedName.getSegmentCount() > 1
				&& qualifiedName.getLastSegment() == N4JSLanguageConstants.EXPORT_DEFAULT_NAME );
	}

	/** Returns the semantically important last part of a qualified name. This is commonly the last segment except for 'default' exports, where it is the second last segment. */
	def static String lastSegmentOrDefaultHost(QualifiedName qualifiedName) {
		if( isDefaultExport(qualifiedName) ) return  qualifiedName.getSegment(qualifiedName.getSegmentCount() - 2)
		return qualifiedName.getLastSegment();
	}
	/**
	 * Returns <code>true</code> if the character {@code c} is a valid JS identifier start.
	 *
	 * Moved from {@link IdentifierValueConverter}.
	 */
	def static boolean isValidIdentifierStart(char c) {
		return CharTypes.isLetter(c) || c.isChar('_') || c.isChar('$');
	}

	/**
	 * Returns <code>true</code> if the character {@code c} is a valid JS identifier part.
	 *
	 * Moved from {@link IdentifierValueConverter}.
	 */
	def static boolean isValidIdentifierPart(char c) {
		return N4JSLanguageUtils.isValidIdentifierStart(c) || CharTypes.isDigit(c) || CharTypes.isConnectorPunctuation(c)
				|| CharTypes.isCombiningMark(c) || c.isChar('\u200C') || c.isChar('\u200D');
	}

	/**
	 * Returns <code>true</code> if the given identifier is a valid N4JS identifier.
	 */
	def static boolean isValidIdentifier(String identifier) {
		val characters = identifier.chars.toArray;
		for (i : 0..<characters.length) {
			val c = characters.get(i) as char;
			if (i==0) {
				if (!isValidIdentifierStart(c))
					return false;
			} else {
				if (!isValidIdentifierPart(c))
					return false;
			}
		}
		return true;
	}
	/**
	 * Helper method to overcome missing xtend support for character literals
	 */
	def private static boolean isChar(char c1, String c2) {
		c1 == c2.charAt(0);
	}

	/**
	 * If the given expression is a property access to one of the fields in {@code Symbol}, then this method returns the
	 * referenced field, otherwise <code>null</code>. This method may perform proxy resolution.
	 */
	def public static TField getAccessedBuiltInSymbol(RuleEnvironment G, Expression expr) {
		return getAccessedBuiltInSymbol(G, expr, true);
	}

	/**
	 * Same as {@link #getAccessedBuiltInSymbol(RuleEnvironment, Expression)}, but proxy resolution can be disallowed.
	 * However, if proxy resolution is disallowed, this method will only support a "direct access" to built-in symbols,
	 * i.e. the target must be an {@link IdentifierRef} directly pointing to built-in object 'Symbol'.
	 */
	def public static TField getAccessedBuiltInSymbol(RuleEnvironment G, Expression expr, boolean allowProxyResolution) {
		if (expr instanceof ParameterizedPropertyAccessExpression) {
			val sym = G.symbolObjectType;
			if (allowProxyResolution) {
				// mode #1: we may resolve proxies
				val prop = expr.property;
				if(prop instanceof TField && prop.eContainer===sym) {
					return prop as TField;
				}
			} else {
				// mode #2: we must avoid proxy resolution
				// (NOTE: this mode only supports direct access to built-in symbols, i.e. target must be IdentiferRef
				// to built-in object 'Symbol')
				val targetExpr = expr.target;
				val targetElem = if (targetExpr instanceof IdentifierRef) targetExpr.id; // n.b.: only supports direct access
				if (targetElem === sym) {
					val propName = expr.propertyAsText; // do NOT use expr.property!
					return sym.ownedMembers.filter(TField).findFirst[static && name == propName];
				}
			}
		}
		return null;
	}

	/**
	 * Tells if the given class has a covariant constructor, cf. {@link AnnotationDefinition#COVARIANT_CONSTRUCTOR}, or
	 * the given interface requires all implementing classes to have a covariant constructor.
	 */
	def static boolean hasCovariantConstructor(TClassifier tClassifier) {
		// NOTE: ignoring implicit super types, because none of them declares @CovariantConstructor
		return tClassifier.declaredCovariantConstructor
			|| AllSuperTypesCollector.collect(tClassifier).exists[declaredCovariantConstructor];
	}

	/**
	 * Returns the nearest super class that is itself explicitly annotated with &#64;CovariantConstructor or has an
	 * owned constructor explicitly annotated with &#64;CovariantConstructor.
	 */
	def static TClass findCovariantConstructorDeclarator(TClass tClass) {
		// NOTE: ignoring implicit super types, because none of them declares @CovariantConstructor
		return new ExtendedClassesIterable(tClass).findFirst[declaredCovariantConstructor];
	}

	/**
	 * Creates a new type reference representing the implicit upper bound to be used for type variables without an
	 * explicitly declared upper bound.
	 */
	def static TypeRef getTypeVariableImplicitUpperBound(RuleEnvironment G) {
		return G.anyTypeRef;
	}

	/**
	 * Tells if the given expression is evaluated during post-processing as a compile-time expression and has its
	 * {@link CompileTimeValue value} stored in the {@link ASTMetaInfoCache}.
	 * <p>
	 * Note that every expression may be a compile-time expression (in fact, every number or string literal is a
	 * compile-time expression) but being a compile-time expression only has a special effect in case of certain
	 * expressions. This method returns <code>true</code> for these expressions.
	 * <p>
	 * IMPORTANT: this method will return <code>true</code> only for root expressions directly processed as
	 * compile-time expressions, not for expressions directly or indirectly nested in such an expression.
	 */
	def static boolean isProcessedAsCompileTimeExpression(Expression expr) {
		// cases of expressions that are required to be a compile-time expression:
		if(isMandatoryCompileTimeExpression(expr)) {
			return true;
		}
		// cases of expressions that may or may not be a compile-time expression:
		val parent = expr.eContainer;
		return parent instanceof ExportedVariableDeclaration
			|| parent instanceof N4FieldDeclaration;
	}

	/**
	 * Tells if the given expression is required to be a compile-time expression, according to the N4JS language
	 * specification.
	 * <p>
	 * IMPORTANT: this method will return <code>true</code> only for root expressions directly required to be
	 * compile-time expressions, not for expressions directly or indirectly nested in such an expression.
	 */
	def static boolean isMandatoryCompileTimeExpression(Expression expr) {
		val parent = expr.eContainer;
		if(parent instanceof LiteralOrComputedPropertyName) {
			return parent.kind===PropertyNameKind.COMPUTED && parent.expression===expr;
		} else if(parent instanceof IndexedAccessExpression) {
			return parent.index===expr;
		}
		return false;
	}

	/**
	 * Returns the property/member name to use for the given compile-time value or <code>null</code> if the value is
	 * invalid. This is used to derive a property/member from a compile-time expression in computed property names and
	 * index access expressions.
	 * <p>
	 * IMPLEMENTATION NOTE: we can simply use #toString() on a valid value, even if we have a ValueBoolean, ValueNumber,
	 * or ValueSymbol.
	 * <p>
	 * For undefined, null, NaN, Infinity, booleans, and numbers: they are equivalent to their corresponding string
	 * literal, as illustrated in this snippet:
	 *
	 * <pre>
	 *     // plain Javascript
	 *
	 *     var obj = {
	 *         [undefined]: 'a',
	 *         [null]     : 'b',
	 *         41         : 'c',
	 *         [42]       : 'd',
	 *         [false]    : 'e',
	 *         [NaN]      : 'f',
	 *         [Infinity] : 'g'
	 *     };
	 *
	 *     console.log( obj[undefined] === obj['undefined']); // will print true!
	 *     console.log( obj[null]      === obj['null']     ); // will print true!
	 *     console.log( obj[41]        === obj['41']       ); // will print true!
	 *     console.log( obj[42]        === obj['42']       ); // will print true!
	 *     console.log( obj[false]     === obj['false']    ); // will print true!
	 *     console.log( obj[NaN]       === obj['NaN']      ); // will print true!
	 *     console.log( obj[Infinity]  === obj['Infinity'] ); // will print true!
	 * </pre>
	 * <p>
	 * For symbols: the #toString() method in ValueSymbol prepends {@link N4JSLanguageUtils#SYMBOL_IDENTIFIER_PREFIX},
	 * so we can simply use that.
	 */
	def public static String derivePropertyNameFromCompileTimeValue(CompileTimeValue value) {
		return if (value !== null && value.valid) {
			value.toString // see API doc for why we can simply use #toString() here
		} else {
			null
		}
	}

	/**
	 * Calculate the optional field strategy of the given expression
	 *
	 * @param expr
	 *			the expression to calculate.
	 * @return the optional field strategy of the expression.
	 */
	def public static OptionalFieldStrategy calculateOptionalFieldStrategy(TypableElement expr, TypeRef typeRef) {
		if (expr.constTransitiveObjectLiteral) {
			// Req. IDE-240500, case 1, 4a
			return OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL;
		}

		if (expr.isConstTransitiveNewExpressionOrFinalNominalClassInstance(typeRef)) {
			// Req. IDE-240500, case 2, 3, 4b, 4c
			return OptionalFieldStrategy.GETTERS_OPTIONAL;
		}

		if (expr instanceof ConditionalExpression) {
			// GHOLD-411: Special handling of optionality in ternary expressions.
			// e.g. const conditionalThing: Thing = true ? {something: 1} : null as Thing;
			val optionalStrategyForTrueExpr = expr.trueExpression.calculateOptionalFieldStrategy(typeRef);
			val optionalStrategyForFalseExpr = expr.falseExpression.calculateOptionalFieldStrategy(typeRef);
			return minOptionalityFieldStrategy(optionalStrategyForTrueExpr, optionalStrategyForFalseExpr);
		}

		// Otherwise both are mandatory
		return OptionalFieldStrategy.OFF;
	}

	/**
	 * Checks whether the given expression is an object literal or references an object literal
	 * transitively through a const variable.
	 *
	 * @param expr
	 *			the expression to check.
	 * @return true if the expression is an object literal or references an object literal
	 * 			transitively through a const variable.
	 */
	def private static boolean isConstTransitiveObjectLiteral(TypableElement expr) {
		if (expr instanceof NullLiteral) {
			return true;
		}

		if (expr instanceof ObjectLiteral)
			return true;

		if (expr instanceof IdentifierRef) {
			val idElem = expr.getId();
			if (idElem instanceof VariableDeclaration) {
				// Case 1: non-exported const, e.g. const ol = {}
				if (idElem.isConst()) {
					return idElem.expression instanceof ObjectLiteral;
				}
			} else if (idElem instanceof TVariable) {
				// Case 2: exported const, e.g. exported const ol = {}
				return idElem.objectLiteral;
			}
		}

		return false;
	}

	/**
	 * Checks whether the given expression is a new expression, an expression of a final and nominal type,
	 * or references these expressions transitively through a const variable.
	 *
	 * @param expr
	 *			the expression to check.
	 * @return true if the expression is a new expression, an expression of a final and nominal type,
	 * 			or references these expressions transitively through a const variable.
	 */
	def private static boolean isConstTransitiveNewExpressionOrFinalNominalClassInstance(TypableElement expr, TypeRef typeRef) {
		if (expr instanceof NullLiteral) {
			return true;
		}

		if (expr instanceof NewExpression)
			return true;

		if (typeRef !== null) {
			val declType = typeRef.declaredType;
			if (declType !== null && declType.isFinal && typeRef.typingStrategy == TypingStrategy.NOMINAL) {
				return true;
			}
		}

		if (expr instanceof IdentifierRef) {
			val idElem = expr.getId();
			if (idElem instanceof VariableDeclaration) {
				// Case 1: non-exported const, e.g. const ol = new A()
				if (idElem.isConst()) {
					return idElem.expression instanceof NewExpression;
				}
			} else if (idElem instanceof TVariable) {
				// Case 2: exported const, e.g. exported const ol = new A()
				return idElem.newExpression;
			}
		}

		return false;
	}

	/**
	 * Tells whether the given declaration will have a representation in the transpiled output code.
	 * If this method returns <code>false</code>, the declaration and its type exists only at compile type.
	 * <p>
	 * The implementation of this method does *NOT* rely on type model elements and can therefore be used
	 * in early stages before the types builder has run and in the transpiler!
	 */
	def static boolean hasRuntimeRepresentation(N4TypeDeclaration typeDecl, JavaScriptVariantHelper javaScriptVariantHelper) {
		val isNonN4JSInterfaceInN4JSD = typeDecl instanceof N4InterfaceDeclaration
			&& javaScriptVariantHelper.isExternalMode(typeDecl)
			&& !AnnotationDefinition.N4JS.hasAnnotation(typeDecl as N4InterfaceDeclaration);
		val isNumberOrStringBasedEnum = typeDecl instanceof N4EnumDeclaration
			&& getEnumKind(typeDecl as N4EnumDeclaration) !== EnumKind.Normal;
		val isTypeAlias = typeDecl instanceof N4TypeAliasDeclaration;
		return typeDecl !== null && !isNonN4JSInterfaceInN4JSD && !isNumberOrStringBasedEnum && !isTypeAlias;
	}

	/**
	 * Tells whether the given identifiable element will have a representation in the transpiled output code.
	 * If this method returns <code>false</code>, the element exists only at compile type.
	 * <p>
	 * The implementation of this method does *NOT* rely on the AST and can therefore be used in resources
	 * that were loaded from the Xtext index.
	 */
	def static boolean hasRuntimeRepresentation(IdentifiableElement element, JavaScriptVariantHelper javaScriptVariantHelper) {
		val isNonN4JSInterfaceInN4JSD = element instanceof TInterface
			&& javaScriptVariantHelper.isExternalMode(element)
			&& !AnnotationDefinition.N4JS.hasAnnotation(element as TInterface);
		val isNumberOrStringBasedEnum = element instanceof TEnum
			&& getEnumKind(element as TEnum) !== EnumKind.Normal;
		val isTypeAlias = element instanceof TypeAlias;
		return element !== null && !isNonN4JSInterfaceInN4JSD && !isNumberOrStringBasedEnum && !isTypeAlias;
	}
	
	
	/**
	 * Tells whether the given type like element is an element such as a {@Type} that can coexist
	 * with another value like identifiable element such as a {@link TVariable} despite having the same name.
	 */
	def static boolean isHollowElement(N4TypeDeclaration typeDecl, JavaScriptVariantHelper javaScriptVariantHelper) {
		val isNonN4JSInterfaceInN4JSD = typeDecl instanceof N4InterfaceDeclaration
			&& javaScriptVariantHelper.isExternalMode(typeDecl)
			&& !AnnotationDefinition.N4JS.hasAnnotation(typeDecl as N4InterfaceDeclaration);
		val isTypeAlias = typeDecl instanceof N4TypeAliasDeclaration;
		// TODO: namespace
		return typeDecl !== null && (isNonN4JSInterfaceInN4JSD || isTypeAlias);
	}
	
	
	/**
	 * Tells whether the given type like element is an element such as a {@Type} that can coexist
	 * with another value like identifiable element such as a {@link TVariable} despite having the same name.
	 */
	def static boolean isHollowElement(IdentifiableElement element, JavaScriptVariantHelper javaScriptVariantHelper) {
		val isNonN4JSInterfaceInN4JSD = element instanceof TInterface
			&& javaScriptVariantHelper.isExternalMode(element)
			&& !AnnotationDefinition.N4JS.hasAnnotation(element as TInterface);
		val isTypeAlias = element instanceof TypeAlias;
		// TODO: namespace
		return element !== null && (isNonN4JSInterfaceInN4JSD || isTypeAlias);
	}

	/**
	 * Check if the interface is built-in or an external without N4JS annotation.
	 *
	 * @param tinf
	 *            The interface.
	 * @return true if the interface is either built-in, provided by runtime or an external interface without N4JS annotation. Return false
	 *         otherwise.
	 */
	def static boolean builtInOrProvidedByRuntimeOrExternalWithoutN4JSAnnotation(TInterface tinf) {
		val hasN4JSAnnotation = tinf.annotations.exists[AnnotationDefinition.N4JS.name == name];
		val ts = tinf.typingStrategy;
		val isDefStructural = ts != TypingStrategy.NOMINAL && ts != TypingStrategy.DEFAULT;
		val fileExtensionCalculator = new XpectAwareFileExtensionCalculator;
		val fileExt = fileExtensionCalculator.getXpectAwareFileExtension(tinf);
		return TypeUtils.isBuiltIn(tinf) || tinf.providedByRuntime || (tinf.isExternal() && !hasN4JSAnnotation) || (isDefStructural && (fileExt == N4JSGlobals.N4JSD_FILE_EXTENSION));
	}

	/**
	 * Check if an optional field strategy is less restricted than or equal to another optional field strategy.
	 *
	 * @param s1
	 *            The first optional field strategy.
	 * @param s2
	 *            The second optional field strategy.
	 * @return true if the first optional field strategy is less restricted than or equal to the second optional field
	 *         strategy, false otherwise.
	 */
	def static boolean isOptionalityLessRestrictedOrEqual(OptionalFieldStrategy s1, OptionalFieldStrategy s2) {
		if (s1 == s2) {
			return true;
		}

		val result = switch (s1) {
			case OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL:
				true // Always true in this case
			case OptionalFieldStrategy.OFF:
				(s2 == OptionalFieldStrategy.OFF)
			case OptionalFieldStrategy.GETTERS_OPTIONAL:
				(s2 == OptionalFieldStrategy.GETTERS_OPTIONAL) || (s2 == OptionalFieldStrategy.OFF)
			default:
				throw new RuntimeException("Invalid enum value " + s1)
		}

		return result;
	}

	/**
	 * Calculate the minimum of two given optional field strategies.
	 *
	 * @param s1
	 *            The first optional field strategy.
	 * @param s2
	 *            The second optional field strategy.
	 * @return the minimum optional field strategy.
	 */
	def static OptionalFieldStrategy minOptionalityFieldStrategy(OptionalFieldStrategy s1, OptionalFieldStrategy s2) {
		if ((s1 == OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL) && (s2 == OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL)) {
			return OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL;
		}

		if ((s1 != OptionalFieldStrategy.OFF) && (s2 != OptionalFieldStrategy.OFF)) {
			return OptionalFieldStrategy.GETTERS_OPTIONAL;
		}

		return OptionalFieldStrategy.OFF;
	}

	/**
	 * Tells whether the given assignment expression has a valid left-hand side.
	 */
	def static boolean hasValidLHS(AssignmentExpression assignExpr) {
		val lhs = assignExpr.lhs;
		return lhs !== null && (
			lhs.isValidSimpleAssignmentTarget()
			|| (assignExpr.op === AssignmentOperator.ASSIGN && DestructureUtils.isTopOfDestructuringAssignment(assignExpr))
		);
	}

	/**
	 * Tells whether the given AST node is at a valid location for an await expression or a for-await-of loop.
	 * Does not check whether the given node is actually an await expression/statement.
	 */
	def static boolean isValidLocationForAwait(EObject astNode) {
		val containingFunDef = EcoreUtil2.getContainerOfType(astNode, FunctionDefinition);
		return containingFunDef !== null && containingFunDef.async;
	}

	/** Tells whether the given AST node and EReference is a valid location for an optional type parameter. */
	def static boolean isValidLocationForOptionalTypeParameter(EObject astNode, EReference reference) {
		// for now, type parameters may be optional only in class, interface, and type alias declarations
		// (not in function/method declarations or in FunctionTypeExpression or TStructMethod):
		return reference === N4JSPackage.Literals.GENERIC_DECLARATION__TYPE_VARS
			&& (astNode instanceof N4ClassifierDefinition || astNode instanceof N4TypeAliasDeclaration);
	}

	/** Tells whether the given type may be referenced structurally, i.e. with modifiers '~', '~~', '~r~', etc. */
	def static boolean mayBeReferencedStructurally(Type type) {
		return !(type instanceof PrimitiveType);
	}

	/** Tells whether the given type may be referenced dynamically, i.e. with modifier '+'. */
	def static boolean mayBeReferencedDynamically(Type type) {
		return !(type instanceof PrimitiveType) || type instanceof AnyType;
	}
}
