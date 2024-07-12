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
package org.eclipse.n4js.utils;

import static org.eclipse.n4js.N4JSLanguageConstants.CONSTRUCTOR;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.booleanTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getGlobalObjectScope;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.intType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.intTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.numberType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.numberTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.symbolObjectType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.common.unicode.CharTypes;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.n4JS.AbstractAnnotationList;
import org.eclipse.n4js.n4JS.AbstractVariable;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AssignmentOperator;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalOperator;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4MemberAnnotationList;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedAccess;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.parser.conversion.IdentifierValueConverter;
import org.eclipse.n4js.postprocessing.ASTMetaInfoCache;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.utils.UnresolvableObjectDescription;
import org.eclipse.n4js.ts.typeRefs.BooleanLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StringLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.NameAndAccess;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.util.ExtendedClassesIterable;
import org.eclipse.n4js.ts.types.util.TypeModelUtils;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeCompareUtils;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.AllSuperTypesCollector;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.SuperTypesMapper;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Optional;

/**
 * Intended for small, static utility methods that
 * <ul>
 * <li>need both the AST and types model or code from the main n4js bundle, and can therefore not be put into
 * {@link N4JSASTUtils} and {@link TypeUtils}.
 * <li>implement a fundamental rule or logic of the core language that defines an important part of N4JS semantics.
 * </ul>
 *
 * @see N4JSASTUtils
 * @see TypeUtils
 */
@SuppressWarnings("javadoc")
public class N4JSLanguageUtils {

	/**
	 * Tells whether the {@link #isOpaqueModule(ProjectType, URI) "opaque modules"} feature is turned on (i.e.
	 * processing of opaque modules is suppressed).
	 * <p>
	 * WARNING: this field is set via reflection from {@code JSActivationUtil}.
	 */
	public static boolean OPAQUE_JS_MODULES = true;

	/**
	 * Special name used for {@link TMethod}s that represent a call signature. Only used in type model (not in AST). Do
	 * not use this name to check if a {@code TMethod} is a call signature; use {@link TFunction#isCallSignature()
	 * #isCallSignature()} for this purpose.
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
	 * The default language version returned by method {@link #getLanguageVersion()} in case no actual language
	 * version/commit was set during the build. See {@link #getLanguageVersion()} for details.
	 */
	public static final String DEFAULT_LANGUAGE_VERSION = "0.0.0.v19990101_0000";

	/**
	 * The default language commit hash returned by method {@link #getLanguageCommit()} in case no actual language
	 * version/commit was set during the build. See {@link #getLanguageVersion()} for details.
	 */
	public static final String DEFAULT_LANGUAGE_COMMIT = "0000000000000000000000000000000000000000";

	private static final String LANGUAGE_VERSION_PROPERTIES_FILE_NAME = "language-version.properties";

	private static String languageVersionStr = null;
	private static String languageCommitStr = null;

	/**
	 * Returns the N4JS language version as defined in file {@value #LANGUAGE_VERSION_PROPERTIES_FILE_NAME}.
	 * <p>
	 * An actual, meaningful version is only returned if it was set during the build by script
	 * <code>compute-version.sh</code>; otherwise the default version {@link #DEFAULT_LANGUAGE_VERSION} is returned.
	 * Since only publishing builds set the version, this default version will be returned in all non-production cases,
	 * e.g. during debugging, local testing, testing during maven builds (both in CI builds and the nightly builds).
	 */
	public static String getLanguageVersion() {
		if (languageVersionStr == null) {
			languageVersionStr = readLanguageVersionProperty("language.version");
		}
		return languageVersionStr;
	}

	/**
	 * Returns true iff the current version is the default version. True indicates that this version is running locally
	 * or was created from a local workspace (instead on Gitlab).
	 */
	public static boolean isDefaultLanguageVersion() {
		return DEFAULT_LANGUAGE_VERSION == getLanguageVersion();
	}

	/** Like {@link #getLanguageVersion()}, but for the git commit hash the language version was built from. */
	public static String getLanguageCommit() {
		if (languageCommitStr == null) {
			languageCommitStr = readLanguageVersionProperty("language.commit");
		}
		return languageCommitStr;
	}

	private static String readLanguageVersionProperty(String propertyId) {
		Properties properties;
		try (InputStream in = N4JSLanguageUtils.class.getClassLoader()
				.getResourceAsStream(LANGUAGE_VERSION_PROPERTIES_FILE_NAME)) {
			if (in == null) {
				throw new RuntimeException("unable to find properties file " + LANGUAGE_VERSION_PROPERTIES_FILE_NAME);
			}
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			throw new RuntimeException("unable to load properties file " + LANGUAGE_VERSION_PROPERTIES_FILE_NAME, e);
		}
		String value = properties.getProperty(propertyId);
		if (value == null) {
			throw new RuntimeException("properties file " + LANGUAGE_VERSION_PROPERTIES_FILE_NAME
					+ " does not contain property " + propertyId);
		}
		return value;
	}

	/** Tells whether .d.ts file generation is <em>actually</em> active in a given project. */
	public static boolean isDtsGenerationActive(ProjectDescription pd) {
		return pd != null && pd.isGeneratorEnabledDts()
				&& !N4JSGlobals.PROJECT_TYPES_WITHOUT_DTS_GENERATION.contains(pd.getProjectType());
	}

	/**
	 * Tells whether the given module is the {@link ProjectDescription#getMainModule() main module} of the given
	 * project.
	 */
	public static boolean isMainModule(N4JSProjectConfigSnapshot project, TModule module) {
		String qn = module.getQualifiedName();
		return isMainModule(project, qn);
	}

	/**
	 * Tells whether the module with the given qualified name is the {@link ProjectDescription#getMainModule() main
	 * module} of the given project.
	 */
	public static boolean isMainModule(N4JSProjectConfigSnapshot project, String moduleQualifiedName) {
		return project.getMainModule() == moduleQualifiedName;
	}

	/** Convenience method for {@link #isOpaqueModule(ProjectType, URI)}. */
	public static boolean isOpaqueModule(N4JSWorkspaceConfigSnapshot workspaceConfig, URI resourceURI) {
		if (workspaceConfig == null) {
			return false;
		}
		N4JSProjectConfigSnapshot project = workspaceConfig.findProjectContaining(resourceURI);
		if (project == null) {
			return false;
		}
		ProjectType projectType = project.getType();
		return N4JSLanguageUtils.isOpaqueModule(projectType, resourceURI);
	}

	/**
	 * Tells whether a module with the given properties is an "opaque module". These modules are neither post-processed
	 * nor validated. The transpiler will only wrap opaque resources.
	 * <p>
	 * Note that if dependency injection is available, this more convenient method can be used:
	 * {@link N4JSLanguageHelper#isOpaqueModule(org.eclipse.emf.ecore.resource.Resource)}.
	 */
	public static boolean isOpaqueModule(ProjectType typeOfContainingProject, URI resourceURI) {
		ResourceType resourceType = ResourceType.getResourceType(resourceURI);

		if (resourceType == ResourceType.JS
				|| resourceType == ResourceType.JSX) {

			return OPAQUE_JS_MODULES; // JavaScript modules are not processed iff OPAQUE_JS_MODULES is true

		} else if (resourceType == ResourceType.N4JS
				|| resourceType == ResourceType.N4JSX) {

			if (typeOfContainingProject == null) {
				return false; // happens in tests
			}
			// N4JS files of definition projects are not processed.
			return typeOfContainingProject == ProjectType.DEFINITION;

		} else if (resourceType == ResourceType.N4JSD
				|| resourceType == ResourceType.UNKOWN
				|| resourceType == ResourceType.XT) {

			// default
		}

		// default: process file
		return false;
	}

	/**
	 * Some {@link IEObjectDescription}s returned by our {@link IScope scope} implementations do not represent actual,
	 * valid elements in the scope but are used only for error reporting, etc. This method returns <code>true</code> iff
	 * the given description represents an actual, existing element.
	 */
	public static boolean isActualElementInScope(IEObjectDescription desc) {
		if (desc instanceof IEObjectDescriptionWithError
				|| desc instanceof UnresolvableObjectDescription) {
			return false;
		}
		return true;
	}

	public static enum EnumKind {
		Normal, NumberBased, StringBased
	}

	public static EnumKind getEnumKind(N4EnumDeclaration enumDecl) {
		if (AnnotationDefinition.NUMBER_BASED.hasAnnotation(enumDecl)) {
			return EnumKind.NumberBased;
		} else if (AnnotationDefinition.STRING_BASED.hasAnnotation(enumDecl)) {
			return EnumKind.StringBased;
		}
		return EnumKind.Normal;
	}

	public static EnumKind getEnumKind(TEnum tEnum) {
		if (AnnotationDefinition.NUMBER_BASED.hasAnnotation(tEnum)) {
			return EnumKind.NumberBased;
		} else if (AnnotationDefinition.STRING_BASED.hasAnnotation(tEnum)) {
			return EnumKind.StringBased;
		}
		return EnumKind.Normal;
	}

	/**
	 * Tells whether the given enum literal has a valid value expression (i.e. a string literal or a, possibly negated,
	 * number literal).
	 */
	public static boolean isEnumLiteralValueExpressionValid(N4EnumLiteral n4EnumLiteral) {
		return n4EnumLiteral == null || n4EnumLiteral.getValueExpression() == null
				|| getEnumLiteralValue(n4EnumLiteral) != null;
	}

	/**
	 * Obtains from the AST the value of the given enum literal. Returns <code>null</code> iff the enum literal has an
	 * invalid value expression.
	 */
	public static Object getEnumLiteralValue(N4EnumLiteral n4EnumLiteral) {
		Expression valueExpr = n4EnumLiteral == null ? null : n4EnumLiteral.getValueExpression();
		if (valueExpr instanceof StringLiteral) {
			return ((StringLiteral) valueExpr).getValueAsString();
		} else if (valueExpr instanceof NumericLiteral) {
			return ((NumericLiteral) valueExpr).getValue();
		} else if (valueExpr instanceof UnaryExpression) {
			Expression expr = ((UnaryExpression) valueExpr).getExpression();
			if (expr instanceof NumericLiteral) {
				NumericLiteral nlit = (NumericLiteral) expr;
				BigDecimal value = nlit.getValue();
				if (((UnaryExpression) valueExpr).getOp() == UnaryOperator.NEG) {
					return value == null ? null : value.negate();
				} else if (((UnaryExpression) valueExpr).getOp() == UnaryOperator.POS) {
					return value;
				}
			}
		}
		return null;
	}

	/**
	 * If the given function definition is asynchronous, will wrap given return type into a Promise. Otherwise, returns
	 * given return type unchanged. A return type of <code>void</code> is changed to <code>undefined</code>.
	 */
	public static TypeRef makePromiseIfAsync(FunctionDefinition funDef, TypeRef returnTypeRef,
			BuiltInTypeScope builtInTypeScope) {
		if (funDef != null && returnTypeRef != null) {
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
	public static TypeRef makeGeneratorIfGeneratorFunction(FunctionDefinition funDef, TypeRef returnTypeRef,
			BuiltInTypeScope builtInTypeScope) {
		if (funDef != null && returnTypeRef != null) {
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
	public static boolean hasExpectedSpecialReturnType(TypeRef typeRef, FunctionDefinition funDef,
			BuiltInTypeScope scope) {
		Type expectedType = getExpectedSpecialReturnType(funDef, scope);
		return expectedType == null || typeRef.getDeclaredType() == expectedType;
	}

	/**
	 * If the given function is an asynchronous and/or generator function, this method returns the expected special
	 * return type; otherwise <code>null</code> is returned.
	 */
	public static Type getExpectedSpecialReturnType(FunctionDefinition funDef, BuiltInTypeScope scope) {
		return getExpectedSpecialReturnType(funDef.isAsync(), funDef.isGenerator(), scope);
	}

	/**
	 * Returns the expected special return type of an asynchronous and/or generator function, or <code>null</code> if
	 * both 'isAsync' and 'isGenerator' are <code>false</code>.
	 */
	public static Type getExpectedSpecialReturnType(boolean isAsync, boolean isGenerator, BuiltInTypeScope scope) {
		if (isGenerator) {
			if (isAsync) {
				return scope.getAsyncGeneratorType();
			} else {
				return scope.getGeneratorType();
			}
		} else {
			if (isAsync) {
				return scope.getPromiseType();
			} else {
				return null;
			}
		}
	}

	/** See {@link N4JSASTUtils#isASTNode(EObject)}. */
	public static boolean isASTNode(EObject obj) {
		return N4JSASTUtils.isASTNode(obj);
	}

	/**
	 * Tells if given expression denotes the value 'undefined'.
	 */
	public static boolean isUndefinedLiteral(RuleEnvironment G, Expression expr) {
		if (expr instanceof IdentifierRef) {
			return ((IdentifierRef) expr).getId() == getGlobalObjectScope(G).getFieldUndefined();
		}
		return false;
	}

	/**
	 * Tells if given expression denotes the value 'null'.
	 */
	public static boolean isNullLiteral(Expression expr) {
		return (expr instanceof NullLiteral);
	}

	/**
	 * Tells if given object is a <em>type model element</em>, i.e. is contained below a {@link TModule} element.
	 * <p>
	 * Note that it is not possible to tell AST nodes from type model elements only based on the object's type, because
	 * there exist type model entities that may appear as a node in the AST (e.g. some TypeRefs, TStructField).
	 */
	public static boolean isTypeModelElement(EObject obj) {
		// note: despite its name, #getContainerOfType() returns 'obj' if instance of TModule
		return EcoreUtil2.getContainerOfType(obj, TModule.class) != null;
	}

	/**
	 * Tells if given AST node is a typable AST node, i.e. a node that has an (actual) type that can be inferred using
	 * the type system. When <code>true</code> is returned, the given AST node can safely be casted to
	 * {@link TypableElement}.
	 * <p>
	 * For performance reasons, this method will simply assume {@code astNode} to be an AST node (i.e. contained below a
	 * {@link Script} element) and will not check this again.
	 */
	public static boolean isTypableNode(EObject astNode) {
		return astNode instanceof TypableElement && !(astNode instanceof AbstractAnnotationList);
	}

	public static boolean isIdentifiableSubtree(EObject astNode) {
		return astNode instanceof IdentifiableElement
				|| getDefinedTypeModelElement(astNode) instanceof IdentifiableElement
				|| astNode instanceof FunctionDeclaration
				// classes with compile error, e.g. missing name, do not necessarily have DefinedTypeModelElement
				// thus the additional check below
				|| astNode instanceof N4ClassDeclaration;
	}

	public static boolean isTypeModelElementDefiningASTNode(EObject astNode) {
		return astNode instanceof VariableDeclaration
				|| astNode instanceof TypeDefiningElement
				|| (astNode instanceof N4MemberDeclaration && !(astNode instanceof N4MemberAnnotationList))
				|| (astNode instanceof PropertyAssignment && !(astNode instanceof PropertyAssignmentAnnotationList))
				|| astNode instanceof FormalParameter
				|| astNode instanceof TStructMember // they can play the role of AST nodes!
				|| astNode instanceof N4EnumLiteral
				|| astNode instanceof N4TypeVariable;
	}

	public static EObject getDefinedTypeModelElement(EObject astNode) {
		if (astNode instanceof AbstractVariable) {
			return ((AbstractVariable<?>) astNode).getDefinedVariable();
		}
		if (astNode instanceof PropertyMethodDeclaration) {
			return ((PropertyMethodDeclaration) astNode).getDefinedMember();
		}
		if (astNode instanceof TypeDefiningElement) {
			return ((TypeDefiningElement) astNode).getDefinedType();
		}
		if (astNode instanceof N4MemberDeclaration) {
			if (!(astNode instanceof N4MemberAnnotationList)) {
				return ((N4MemberDeclaration) astNode).getDefinedTypeElement();
			}
		}
		if (astNode instanceof PropertyAssignment) {
			if (!(astNode instanceof PropertyAssignmentAnnotationList)) {
				return ((PropertyAssignment) astNode).getDefinedMember();
			}
		}
		if (astNode instanceof TStructMember) {
			if (isASTNode(astNode)) {
				// note: a TStructMember may be an AST node or types model element!
				return ((TStructMember) astNode).getDefinedMember();
			}
		}
		if (astNode instanceof N4EnumLiteral) {
			return ((N4EnumLiteral) astNode).getDefinedLiteral();
		}
		if (astNode instanceof N4TypeVariable) {
			return ((N4TypeVariable) astNode).getDefinedTypeVariable();
		}
		return null;
	}

	/**
	 * Returns with {@code true} if the {@link TMember member} argument represents a constructor. More precisely, when
	 * the argument is an instance of {@link TMethod} and its {@link TMethod#getName() name} is {@code constructor}.
	 * Otherwise returns with {@code false}.
	 */
	public static boolean isConstructor(TMember it) {
		return it instanceof TMethod && CONSTRUCTOR.equals(it.getName());
	}

	/**
	 * Returns with {@code true} if the member argument is a {@link TField} instance and the field is
	 * {@link TField#isWriteable() writable}, otherwise returns with {@code false}.
	 */
	public static boolean isWriteableField(TMember m) {
		return m instanceof TField && m.isWriteable();
	}

	/**
	 * Returns with {@code true} if the member argument is a {@link TField} instance and the field is <b>NOT</b>
	 * {@link TField#isWriteable() writable}, otherwise returns with {@code false}.
	 */
	public static boolean isReadOnlyField(TMember m) {
		return m instanceof TField && !m.isWriteable();
	}

	/**
	 * Tells whether the given element is global. Works for AST nodes and TModule elements.
	 */
	public static boolean isGlobal(EObject elem) {
		if (elem == null) {
			return false;
		}
		if (BuiltInTypeScope.isPrimitivesResource(elem.eResource())) {
			// primitives act like global types, but their module does not contain @@Global:
			return true;
		}
		EObject root = EcoreUtil.getRootContainer(elem);
		if (root instanceof TModule) {
			return AnnotationDefinition.GLOBAL.hasAnnotation((TModule) root);
		} else if (root instanceof Script) {
			return AnnotationDefinition.GLOBAL.hasAnnotation((Script) root);
		}
		return false;
	}

	/**
	 * Tells if the given identifiable element is exported.
	 */
	public static boolean isDirectlyExported(IdentifiableElement elem) {
		if (elem instanceof TVariable) {
			return ((TVariable) elem).isDirectlyExported();
		}
		if (elem instanceof Type) {
			return ((Type) elem).isDirectlyExported();
		}
		return false;
	}

	/**
	 * Is the given TFunction tagged ASYNC, and moreover does it return Promise?
	 */
	public static boolean isAsync(TFunction tfunction, BuiltInTypeScope scope) {
		if (tfunction.isDeclaredAsync()) {
			if (tfunction.getReturnTypeRef() instanceof ParameterizedTypeRef) {
				return TypeUtils.isPromise(tfunction.getReturnTypeRef(), scope);
			}
		}
		return false;
	}

	/**
	 * Does the given function-type denote an async function? (two cases: declared type available or not, in the latter
	 * case heuristically assume Promise-returning implies async).
	 * <p>
	 * The declared type (ie, a TFunction) is usually but not always available.
	 */
	public static boolean isAsync(FunctionTypeExprOrRef fteor, RuleEnvironment G) {
		TFunction tfunction = fteor.getFunctionType();
		BuiltInTypeScope tscope = RuleEnvironmentExtensions.getPredefinedTypes(G).builtInTypeScope;
		if (null == tfunction) {
			return TypeUtils.isPromise(fteor.getReturnTypeRef(), tscope);
		} else {
			return N4JSLanguageUtils.isAsync(tfunction, tscope);
		}
	}

	/**
	 * Tells if a value of the given type can be instantiated, i.e. whether
	 *
	 * <pre>
	 * new value();
	 * </pre>
	 *
	 * is legal, given a variable {@code value} of type {@code ctorTypeRef}.
	 */
	public static boolean isInstantiable(TypeTypeRef typeTypeRef) {
		TypeArgument typeArg = typeTypeRef.getTypeArg();
		if (typeArg instanceof Wildcard || typeArg instanceof ExistentialTypeRef) {
			return false;
		}
		Type pseudoStaticType = ((TypeRef) typeArg).getDeclaredType();
		return pseudoStaticType instanceof TN4Classifier;
	}

	/**
	 * Returns the variance of the given type reference's position within its containing classifier declaration or
	 * <code>null</code> if
	 * <ol>
	 * <li>it is located at a position where type variables of any variance may be located, i.e. a position that need
	 * not be checked (e.g. type of a local variable in a method, type of a private field),
	 * <li>it is not contained in a classifier declaration,
	 * <li>it is <code>null</code>, or
	 * <li>some error occurred, e.g. invalid TModule, broken AST.
	 * </ol>
	 */
	public static Variance getVarianceOfPosition(TypeRef typeRefInAST) {
		// note: we have commutativity, so normally order would not matter below; however, due to the quick exit on INV
		// together with the special cases of private members and final fields (handled via a return type of null)
		// we must check position in classifier first!
		Variance v1 = getVarianceOfPositionInClassifier(typeRefInAST);
		if (v1 == null || v1 == Variance.INV) {
			return v1;
		}
		Variance v2 = getVarianceOfPositionRelativeToItsRoot(typeRefInAST);
		return v1.mult(v2);
	}

	/**
	 * Most client code should use {@link #getVarianceOfPosition(TypeRef)}!
	 * <p>
	 * Same as {@link #getVarianceOfPosition(TypeRef)}, but <b>does not take into account nesting of type references
	 * within other type references.</b> This is covered by method
	 * {@link #getVarianceOfPositionRelativeToItsRoot(TypeRef)}.
	 */
	public static Variance getVarianceOfPositionInClassifier(TypeRef typeRefInAST) {
		if (typeRefInAST == null) {
			return null;
		}
		TypeRef rootTypeRefInAST = TypeUtils.getRootTypeRef(typeRefInAST);
		N4ClassifierDeclaration container = EcoreUtil2.getContainerOfType(rootTypeRefInAST,
				N4ClassifierDeclaration.class);
		TClassifier tClassifier = container == null ? null : (TClassifier) container.getDefinedType();
		if (tClassifier == null) {
			return null; // not contained in a class/interface declaration with a properly defined type in TModule
		}
		EObject parent0 = rootTypeRefInAST.eContainer();
		EObject parent = parent0 == null ? null : parent0.eContainer(); // note: skipping the TypeReferenceNode<?> here!
		EObject grandParent = parent == null ? null : parent.eContainer();
		if (parent instanceof FormalParameter) {
			if (((FormalParameter) parent).getDeclaredTypeRefInAST() == rootTypeRefInAST
					&& isNonPrivateMemberOf(grandParent, tClassifier)) {
				return Variance.CONTRA;
			}
		}
		if (parent instanceof N4MethodDeclaration) {
			if (((N4MethodDeclaration) parent).getDeclaredReturnTypeRefInAST() == rootTypeRefInAST
					&& isNonPrivateMemberOf(parent, tClassifier)) {
				return Variance.CO;
			}
		}
		if (parent instanceof N4GetterDeclaration) {
			if (((N4GetterDeclaration) parent).getDeclaredTypeRefInAST() == rootTypeRefInAST
					&& isNonPrivateMemberOf(parent, tClassifier)) {
				return Variance.CO;
			}
		}
		if (parent instanceof N4FieldDeclaration) {
			if (((N4FieldDeclaration) parent).getDeclaredTypeRefInAST() == rootTypeRefInAST
					&& isNonPrivateMemberOf(parent, tClassifier)) {
				TField tField = ((N4FieldDeclaration) parent).getDefinedField();
				if (tField.isFinal()) {
					return Variance.CO; // final field is like a getter
				} else {
					return Variance.INV;
				}
			}
		}
		if (parent instanceof N4ClassifierDeclaration) {
			if (exists(((N4ClassifierDeclaration) parent).getSuperClassifierRefs(),
					it -> it.getTypeRefInAST() == rootTypeRefInAST)) {
				// typeRef is used in the "extends" or "implements" clause of the declaration of tClassifier
				// -> this mainly depends on the variance of the classifier being extended
				return Variance.CO;
			}
		}
		return null;
	}

	/**
	 * Most client code should use {@link #getVarianceOfPosition(TypeRef)}!
	 * <p>
	 * Returns variance of the given type reference's position relative to its root type reference as defined by
	 * {@link TypeUtils#getRootTypeRef(TypeRef)}. In case of error, a best effort is made. Never returns
	 * <code>null</code>.
	 */
	public static Variance getVarianceOfPositionRelativeToItsRoot(TypeRef typeRefInAST) {
		Variance v = Variance.CO;
		TypeRef curr = typeRefInAST;
		while (curr != null) {
			TypeRef parent = EcoreUtil2.getContainerOfType(curr.eContainer(), TypeRef.class);
			if (parent != null) {
				Variance vFactor = null;
				// case #1: curr is nested in parent's type arguments
				Type parentDeclType = parent.getDeclaredType();
				EList<TypeArgument> parentTypeArgs = parent.getDeclaredTypeArgs();
				int parentTypeArgsSize = parentTypeArgs.size();
				for (int idx = 0; vFactor == null && idx < parentTypeArgsSize; idx++) {
					TypeArgument arg = parentTypeArgs.get(idx);
					if (arg == curr) {
						TypeVariable correspondingTypeVar = (idx >= 0 && idx < parentDeclType.getTypeVars().size())
								? parentDeclType.getTypeVars().get(idx)
								: null;
						// if null then ignore, i.e. use CO (that error will be covered elsewhere)
						Variance variance = correspondingTypeVar == null ? null : correspondingTypeVar.getVariance();
						Variance incomingVariance = variance == null ? Variance.CO : variance;
						vFactor = incomingVariance;
					} else if (arg instanceof Wildcard) {
						if (((Wildcard) arg).getDeclaredUpperBound() == curr) {
							vFactor = Variance.CO;
						} else if (((Wildcard) arg).getDeclaredLowerBound() == curr) {
							vFactor = Variance.CONTRA;
						}
					}
					// note: will break as soon as vFactor!=null
				}
				// other cases:
				if (vFactor == null) {
					TypeRef currFixed = curr; // only required to allow using 'curr' in closures
					if (parent instanceof ComposedTypeRef) {
						if (((ComposedTypeRef) parent).getTypeRefs().contains(curr)) {
							vFactor = Variance.CO;
						}
					}
					if (parent instanceof TypeTypeRef) {
						if (((TypeTypeRef) parent).getTypeArg() == curr) {
							if (((TypeTypeRef) parent).isConstructorRef()) {
								vFactor = Variance.INV;// constructor{T}
							} else {
								vFactor = Variance.CO;// type{T}
							}
						}
						if (((TypeTypeRef) parent).getTypeArg() instanceof Wildcard) {
							Wildcard wc = (Wildcard) ((TypeTypeRef) parent).getTypeArg();
							if (wc.getDeclaredUpperBound() == curr) {
								vFactor = Variance.CO;// type{? extends T} OR constructor{? extends T}
							} else if (wc.getDeclaredLowerBound() == curr) {
								vFactor = Variance.CONTRA; // type{? super T} OR constructor{? super T}
							}
						}
					}

					if (parent instanceof BoundThisTypeRef) {
						if (((BoundThisTypeRef) parent).getActualThisTypeRef() == curr) {
							vFactor = Variance.CO;// note: this should never happen in the typical use cases of this
													// method,
							// because BoundThisTypeRefs do not appear in AST but are created programmatically
						}
					}
					if (parent instanceof FunctionTypeExpression) {
						if (((FunctionTypeExpression) parent).getReturnTypeRef() == curr) {
							vFactor = Variance.CO;
						}
					}
					if (parent instanceof FunctionTypeExpression) {
						if (exists(((FunctionTypeExpression) parent).getFpars(), it -> it.getTypeRef() == currFixed)) {
							vFactor = Variance.CONTRA;
						}
					}
					// *X* this is one of the rare cases where we have to use FunctionTypeExpression and not its
					// super class FunctionTypeExprOrRef!
				}
				if (vFactor == null) {
					// note: there should not be any other cases of containment of one type reference in another
					// (many type references cannot contain other type references at all, e.g. FunctionTypeRef,
					// ParameterizedTypeRefStructural, EnumTypeRef)
					throw new IllegalStateException(
							"internal error: unsupported case of containment of one typeRef in another (maybe types model has changed?)");
				}
				// apply vFactor to v
				v = v.mult(vFactor);
				if (v == Variance.INV) {
					return v; // won't change anymore
				}
			}
			curr = parent;
		}
		return v;
	}

	private static boolean isNonPrivateMemberOf(EObject member, TClassifier tClassifier) {
		if (member instanceof N4MemberDeclaration) {
			TMember tMember = ((N4MemberDeclaration) member).getDefinedTypeElement();
			return tMember != null
					&& !tMember.isConstructor()
					&& tMember.getMemberAccessModifier() != MemberAccessModifier.PRIVATE
					&& tMember.getContainingType() == tClassifier;
		}
		return false;
	}

	/**
	 * Tells whether 'baseTypeRefCandidate' is the given literal type's
	 * {@link #getLiteralTypeBase(RuleEnvironment, TypeRef) base type}, without requiring a rule environment and with
	 * considering the semantic equality of 'int' and 'number'.
	 */
	public static boolean isLiteralTypeBase(LiteralTypeRef literalTypeRef, TypeRef baseTypeRefCandidate) {
		// the only chance for success is that 'baseTypeRefCandidate' is a type reference pointing to a primitive
		// type, so on the success path we can assume that 'baseTypeRefCandidate' will give us a declared type we
		// can use for building a rule environment (to avoid the need for clients to pass in a rule environment):
		Type declType = baseTypeRefCandidate.getDeclaredType();
		if (declType != null) {
			RuleEnvironment G = newRuleEnvironment(declType);
			TypeRef baseTypeRef = getLiteralTypeBase(G, literalTypeRef);
			if (TypeCompareUtils.isEqual(baseTypeRef, baseTypeRefCandidate)) {
				return true;
			}
			PrimitiveType tInt = intType(G);
			PrimitiveType tNumber = numberType(G);
			if ((declType == tInt && baseTypeRef.getDeclaredType() == tNumber)
					|| (declType == tNumber && baseTypeRef.getDeclaredType() == tInt)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Same as {@link #getLiteralTypeBase(RuleEnvironment, LiteralTypeRef)}, but accepts type references of any kind and
	 * converts them only if they are {@link LiteralTypeRef}s.
	 * <p>
	 * Note that this does not support nesting, i.e. literal type references nested within a type reference of another
	 * kind won't be converted!
	 */
	public static TypeRef getLiteralTypeBase(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof LiteralTypeRef) {
			return getLiteralTypeBase(G, (LiteralTypeRef) typeRef);
		}
		return typeRef;
	}

	/**
	 * Returns the "base type" for the given literal type, e.g. type string for literal type "hello".
	 */
	public static TypeRef getLiteralTypeBase(RuleEnvironment G, LiteralTypeRef literalTypeRef) {
		if (literalTypeRef instanceof BooleanLiteralTypeRef) {
			return booleanTypeRef(G);
		}
		if (literalTypeRef instanceof NumericLiteralTypeRef) {
			return getLiteralTypeBase(G, (NumericLiteralTypeRef) literalTypeRef);
		}

		if (literalTypeRef instanceof StringLiteralTypeRef) {
			return stringTypeRef(G);
		}

		if (literalTypeRef instanceof EnumLiteralTypeRef) {
			return getLiteralTypeBase(G, (EnumLiteralTypeRef) literalTypeRef);
		}

		throw new UnsupportedOperationException("unknown subclass of " + LiteralTypeRef.class.getSimpleName());
	}

	/**
	 * Same as {@link #getLiteralTypeBase(RuleEnvironment, LiteralTypeRef)}, but accepts only numeric literal type
	 * references.
	 */
	public static TypeRef getLiteralTypeBase(RuleEnvironment G, NumericLiteralTypeRef literalTypeRef) {
		if (isInt32(literalTypeRef.getValue()))
			return intTypeRef(G);
		else
			return numberTypeRef(G);
	}

	/**
	 * Same as {@link #getLiteralTypeBase(RuleEnvironment, LiteralTypeRef)}, but accepts only enum literal type
	 * references.
	 */
	public static TypeRef getLiteralTypeBase(@SuppressWarnings("unused") RuleEnvironment G,
			EnumLiteralTypeRef literalTypeRef) {
		TEnum enumType = literalTypeRef.getEnumType();
		if (enumType != null)
			return TypeUtils.createTypeRef(enumType);
		else
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
	}

	/**
	 * Tells whether the given {@link BigDecimal} represents a Javascript int32.
	 * <p>
	 * Some notes:
	 * <ol>
	 * <li>the range of int32 is asymmetric: [ -2147483648, 2147483647 ]
	 * <li>in Java, literals such as 1E0 etc. are always of type double, but we cannot follow the same rule here,
	 * because the literal is not available and on the basis of the given {@code BigDecimal} we cannot distinguish this.
	 * <li>hexadecimal and octal literals are always interpreted as positive integers (important difference to Java).
	 * </ol>
	 * See N4JS Specification, Section 8.1.3.1 for details.
	 */
	public static boolean isInt32(BigDecimal value) {
		if (value == null) {
			return false;
		}
		// note: normally the correct way of telling whether a BigDecimal is a whole number would be:
		// boolean isWholeNumber = value.stripTrailingZeros().scale() <= 0;
		// However, we here want BigDecimals like 0.0, 1.00, -42.0 to *NOT* be treated as a whole numbers,
		// so we instead go with:
		boolean isWholeNumber = value.scale() <= 0;
		return isWholeNumber
				&& N4JSGlobals.INT32_MIN_VALUE_BD.compareTo(value) <= 0
				&& value.compareTo(N4JSGlobals.INT32_MAX_VALUE_BD) <= 0;
	}

	/**
	 * Checks presence of {@link AnnotationDefinition#POLYFILL} annotation. See also
	 * {@link N4JSLanguageUtils#isStaticPolyfill(AnnotableElement) }
	 */
	public static boolean isNonStaticPolyfill(AnnotableElement astElement) {
		return AnnotationDefinition.POLYFILL.hasAnnotation(astElement);
	}

	/**
	 * Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL} annotation. See also
	 * {@link N4JSLanguageUtils#isStaticPolyfill(AnnotableElement) }
	 */
	public static boolean isStaticPolyfill(AnnotableElement astElement) {
		return AnnotationDefinition.STATIC_POLYFILL.hasAnnotation(astElement);
	}

	/**
	 * Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL_MODULE} annotation on the containing module. See
	 * also {@link N4JSLanguageUtils#isContainedInStaticPolyfillAware(AnnotableElement) }
	 */
	public static boolean isContainedInStaticPolyfillModule(AnnotableElement astElement) {
		return AnnotationDefinition.STATIC_POLYFILL_MODULE.hasAnnotation(astElement); // transitively inherited
	}

	/**
	 * Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL_MODULE} annotation on the containing module. See
	 * also {@link N4JSLanguageUtils#isContainedInStaticPolyfillAware(TAnnotableElement) }
	 */
	public static boolean isContainedInStaticPolyfillModule(TAnnotableElement tsElement) {
		return AnnotationDefinition.STATIC_POLYFILL_MODULE.hasAnnotation(tsElement); // transitively inherited
	}

	/**
	 * Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL_AWARE} annotation on the containing module. See
	 * also {@link N4JSLanguageUtils#isContainedInStaticPolyfillModule(AnnotableElement) }
	 */
	public static boolean isContainedInStaticPolyfillAware(AnnotableElement astElement) {
		return AnnotationDefinition.STATIC_POLYFILL_AWARE.hasAnnotation(astElement); // transitively inherited
	}

	/**
	 * Checks presence of {@link AnnotationDefinition#STATIC_POLYFILL_AWARE} annotation on the containing module. See
	 * also {@link N4JSLanguageUtils#isContainedInStaticPolyfillModule(TAnnotableElement) }
	 */
	public static boolean isContainedInStaticPolyfillAware(TAnnotableElement tsElement) {
		return AnnotationDefinition.STATIC_POLYFILL_AWARE.hasAnnotation(tsElement); // transitively inherited
	}

	/**
	 * Checks presence of {@link AnnotationDefinition#CONTAINS_INDEX_SIGNATURE} annotation on the declaredType of the
	 * given type reference and all its direct and indirect super types.
	 */
	public static boolean hasIndexSignature(TypeRef typeRef) {
		Type declType = typeRef == null ? null : typeRef.getDeclaredType();
		if (declType instanceof ContainerType<?>) {
			return AnnotationDefinition.CONTAINS_INDEX_SIGNATURE.hasAnnotation(declType)
					|| SuperTypesMapper.exists((ContainerType<?>) declType,
							it -> AnnotationDefinition.CONTAINS_INDEX_SIGNATURE.hasAnnotation(it));
		}
		return false;
	}

	/**
	 * checks if the qualifiedName has a last segment named 'default' {@link N4JSLanguageConstants#EXPORT_DEFAULT_NAME}
	 */
	public static boolean isDefaultExport(QualifiedName qualifiedName) {
		return (qualifiedName != null
				&& qualifiedName.getSegmentCount() > 1
				&& qualifiedName.getLastSegment() == N4JSLanguageConstants.EXPORT_DEFAULT_NAME);
	}

	/**
	 * Returns the semantically important last part of a qualified name. This is commonly the last segment except for
	 * 'default' exports, where it is the second last segment.
	 */
	public static String lastSegmentOrDefaultHost(QualifiedName qualifiedName) {
		if (isDefaultExport(qualifiedName))
			return qualifiedName.getSegment(qualifiedName.getSegmentCount() - 2);
		return qualifiedName.getLastSegment();
	}

	/**
	 * Returns <code>true</code> if the character {@code c} is a valid JS identifier start.
	 *
	 * Moved from {@link IdentifierValueConverter}.
	 */
	public static boolean isValidIdentifierStart(char c) {
		return CharTypes.isLetter(c) || isChar(c, "_") || isChar(c, "$");
	}

	/**
	 * Returns <code>true</code> if the character {@code c} is a valid JS identifier part.
	 *
	 * Moved from {@link IdentifierValueConverter}.
	 */
	public static boolean isValidIdentifierPart(char c) {
		return N4JSLanguageUtils.isValidIdentifierStart(c) || CharTypes.isDigit(c)
				|| CharTypes.isConnectorPunctuation(c)
				|| CharTypes.isCombiningMark(c) || isChar(c, "\u200C") || isChar(c, "\u200D");
	}

	/**
	 * Returns <code>true</code> if the given identifier is a valid N4JS identifier.
	 */
	public static boolean isValidIdentifier(String identifier) {
		char[] characters = identifier.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			char c = characters[i];
			if (i == 0) {
				if (!isValidIdentifierStart(c)) {
					return false;
				}
			} else {
				if (!isValidIdentifierPart(c)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Helper method to overcome missing xtend support for character literals
	 */
	private static boolean isChar(char c1, String c2) {
		return c1 == c2.charAt(0);
	}

	/**
	 * If the given expression is a property access to one of the fields in {@code Symbol}, then this method returns the
	 * referenced field, otherwise <code>null</code>. This method may perform proxy resolution.
	 */
	public static TMember getAccessedBuiltInSymbol(RuleEnvironment G, Expression expr) {
		return getAccessedBuiltInSymbol(G, expr, true);
	}

	/**
	 * Same as {@link #getAccessedBuiltInSymbol(RuleEnvironment, Expression)}, but proxy resolution can be disallowed.
	 * However, if proxy resolution is disallowed, this method will only support a "direct access" to built-in symbols,
	 * i.e. the target must be an {@link IdentifierRef} directly pointing to built-in object 'Symbol'.
	 */
	public static TMember getAccessedBuiltInSymbol(RuleEnvironment G, Expression expr, boolean allowProxyResolution) {
		if (expr instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) expr;
			TClass sym = symbolObjectType(G);
			if (allowProxyResolution) {
				// mode #1: we may resolve proxies
				IdentifiableElement prop = ppae.getProperty();
				if ((prop instanceof TField || prop instanceof TMethod) && prop.eContainer() == sym) {
					return (TField) prop;
				}
			} else {
				// mode #2: we must avoid proxy resolution
				// (NOTE: this mode only supports direct access to built-in symbols, i.e. target must be IdentiferRef
				// to built-in object 'Symbol')
				Expression targetExpr = ppae.getTarget();
				IdentifiableElement targetElem = null;
				if (targetExpr instanceof IdentifierRef) {
					targetElem = ((IdentifierRef) targetExpr).getId();
					// n.b.: only supports direct access
				}
				if (targetElem == sym) {
					String propName = ppae.getPropertyAsText(); // do NOT use expr.property!
					NameAndAccess naaField = new NameAndAccess(propName, true, true);
					if (sym.getOrCreateOwnedMembersByNameAndAccess().containsKey(naaField)) {
						return sym.getOwnedMembersByNameAndAccess().get(naaField);
					}
					NameAndAccess naaMethod = new NameAndAccess(propName, false, true);
					if (sym.getOrCreateOwnedMembersByNameAndAccess().containsKey(naaMethod)) {
						return sym.getOwnedMembersByNameAndAccess().get(naaMethod);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Tells if the given class has a covariant constructor, cf. {@link AnnotationDefinition#COVARIANT_CONSTRUCTOR}, or
	 * the given interface requires all implementing classes to have a covariant constructor.
	 */
	public static boolean hasCovariantConstructor(TClassifier tClassifier, DeclMergingHelper declMergingHelper) {
		// NOTE: ignoring implicit super types, because none of them declares @CovariantConstructor
		return tClassifier.isDeclaredCovariantConstructor()
				|| exists(AllSuperTypesCollector.collect(tClassifier, declMergingHelper),
						it -> it.isDeclaredCovariantConstructor());
	}

	/**
	 * Returns the nearest super class that is itself explicitly annotated with &#64;CovariantConstructor or has an
	 * owned constructor explicitly annotated with &#64;CovariantConstructor.
	 */
	public static TClass findCovariantConstructorDeclarator(TClass tClass) {
		// NOTE: ignoring implicit super types, because none of them declares @CovariantConstructor
		return findFirst(new ExtendedClassesIterable(tClass), it -> it.isDeclaredCovariantConstructor());
	}

	/**
	 * Creates a new type reference representing the implicit upper bound to be used for type variables without an
	 * explicitly declared upper bound.
	 */
	public static TypeRef getTypeVariableImplicitUpperBound(RuleEnvironment G) {
		return anyTypeRef(G);
	}

	/**
	 * Tells if the given expression is evaluated during post-processing as a compile-time expression and has its
	 * {@link CompileTimeValue value} stored in the {@link ASTMetaInfoCache}.
	 * <p>
	 * Note that every expression may be a compile-time expression (in fact, every number or string literal is a
	 * compile-time expression) but being a compile-time expression only has a special effect in case of certain
	 * expressions. This method returns <code>true</code> for these expressions.
	 * <p>
	 * IMPORTANT: this method will return <code>true</code> only for root expressions directly processed as compile-time
	 * expressions, not for expressions directly or indirectly nested in such an expression.
	 */
	public static boolean isProcessedAsCompileTimeExpression(Expression expr) {
		// cases of expressions that are required to be a compile-time expression:
		if (isMandatoryCompileTimeExpression(expr)) {
			return true;
		}
		// cases of expressions that may or may not be a compile-time expression:
		EObject parent = expr.eContainer();
		return parent instanceof VariableDeclaration
				|| parent instanceof N4FieldDeclaration
				|| parent instanceof N4MethodDeclaration;
	}

	/**
	 * Tells if the given expression is required to be a compile-time expression, according to the N4JS language
	 * specification.
	 * <p>
	 * IMPORTANT: this method will return <code>true</code> only for root expressions directly required to be
	 * compile-time expressions, not for expressions directly or indirectly nested in such an expression.
	 */
	public static boolean isMandatoryCompileTimeExpression(Expression expr) {
		EObject parent = expr.eContainer();
		if (parent instanceof LiteralOrComputedPropertyName) {
			LiteralOrComputedPropertyName locpn = (LiteralOrComputedPropertyName) parent;
			return locpn.getKind() == PropertyNameKind.COMPUTED && locpn.getExpression() == expr;
		} else if (parent instanceof IndexedAccessExpression) {
			IndexedAccessExpression iae = (IndexedAccessExpression) parent;
			return iae.getIndex() == expr;
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
	 *     console.log( obj[undefined] == obj['undefined']); // will print true!
	 *     console.log( obj[null]      == obj['null']     ); // will print true!
	 *     console.log( obj[41]        == obj['41']       ); // will print true!
	 *     console.log( obj[42]        == obj['42']       ); // will print true!
	 *     console.log( obj[false]     == obj['false']    ); // will print true!
	 *     console.log( obj[NaN]       == obj['NaN']      ); // will print true!
	 *     console.log( obj[Infinity]  == obj['Infinity'] ); // will print true!
	 * </pre>
	 * <p>
	 * For symbols: the #toString() method in ValueSymbol prepends {@link N4JSLanguageUtils#SYMBOL_IDENTIFIER_PREFIX},
	 * so we can simply use that.
	 */
	public static String derivePropertyNameFromCompileTimeValue(CompileTimeValue value) {
		if (value != null && value.isValid()) {
			return value.toString(); // see API doc for why we can simply use #toString() here
		}
		return null;
	}

	/**
	 * Calculate the optional field strategy of the given expression
	 *
	 * @param expr
	 *            the expression to calculate.
	 * @return the optional field strategy of the expression.
	 */
	public static OptionalFieldStrategy calculateOptionalFieldStrategy(N4JSTypeSystem ts, RuleEnvironment G,
			TypableElement expr, TypeRef typeRef) {
		if (isConstTransitiveObjectLiteral(G, expr)) {
			// Req. IDE-240500, case 1, 2, 5a, 5b
			return OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL;
		}

		if (isConstTransitiveNewExpressionOrFinalNominalClassInstanceOrObjectLiteral(ts, G, expr, typeRef)) {
			// Req. IDE-240500, case 3, 4, 5c, 5d
			return OptionalFieldStrategy.GETTERS_OPTIONAL;
		}

		if (expr instanceof ConditionalExpression) {
			// GHOLD-411: Special handling of optionality in ternary expressions.
			// e.g. const conditionalThing: Thing = true ? {something: 1} : null as Thing;
			ConditionalExpression ce = (ConditionalExpression) expr;
			OptionalFieldStrategy optionalStrategyForTrueExpr = calculateOptionalFieldStrategy(ts, G,
					ce.getTrueExpression(), typeRef);
			OptionalFieldStrategy optionalStrategyForFalseExpr = calculateOptionalFieldStrategy(ts, G,
					ce.getFalseExpression(), typeRef);
			return minOptionalityFieldStrategy(optionalStrategyForTrueExpr, optionalStrategyForFalseExpr);
		}

		// Otherwise both are mandatory
		return OptionalFieldStrategy.OFF;
	}

	/**
	 * Checks whether the given expression is an object literal or references an object literal transitively through a
	 * const variable.
	 *
	 * @param expr
	 *            the expression to check.
	 * @return true if the expression is an object literal or references an object literal transitively through a const
	 *         variable.
	 */
	private static boolean isConstTransitiveObjectLiteral(RuleEnvironment G, TypableElement expr) {
		if (expr instanceof NullLiteral) {
			return true;
		}

		if (expr instanceof ObjectLiteral) {
			return true;
		}

		if (expr instanceof ArrowFunction) {
			return true;
		}

		if (expr instanceof FunctionExpression) {
			return true;
		}

		if (expr instanceof IdentifierRef) {
			IdentifierRef ir = (IdentifierRef) expr;
			IdentifiableElement idElem = ir.getId();
			if (isUndefinedLiteral(G, ir)) {
				return true;
			}
			if (idElem instanceof TVariable) {
				TVariable tv = (TVariable) idElem;
				if (tv.isConst()) {
					if (tv.isObjectLiteral()) {
						return true;
					}
					// transitivity disabled
					// if (idElem.astElement != null && idElem.astElement instanceof VariableDeclaration) {
					// return isConstTransitiveObjectLiteral((idElem.astElement as VariableDeclaration).expression);
					// }
				}
			}
		}

		if (expr instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) expr;
			return ae.getOp() == AssignmentOperator.ASSIGN && isConstTransitiveObjectLiteral(G, ae.getRhs());
		}

		if (expr instanceof ConditionalExpression) {
			ConditionalExpression ce = (ConditionalExpression) expr;
			return isConstTransitiveObjectLiteral(G, ce.getTrueExpression())
					&& isConstTransitiveObjectLiteral(G, ce.getFalseExpression());
		}

		if (expr instanceof BinaryLogicalExpression) {
			BinaryLogicalExpression ble = (BinaryLogicalExpression) expr;
			return ble.getOp() == BinaryLogicalOperator.OR
					&& isConstTransitiveObjectLiteral(G, ble.getLhs())
					&& isConstTransitiveObjectLiteral(G, ble.getRhs());
		}

		return false;
	}

	/**
	 * Checks whether the given expression is a new expression, an expression of a final and nominal type, or references
	 * these expressions transitively through a const variable.
	 *
	 * @param expr
	 *            the expression to check.
	 * @return true if the expression is a new expression, an expression of a final and nominal type, or references
	 *         these expressions transitively through a const variable.
	 */
	private static boolean isConstTransitiveNewExpressionOrFinalNominalClassInstanceOrObjectLiteral(N4JSTypeSystem ts,
			RuleEnvironment G, TypableElement expr, TypeRef typeRef) {
		if (expr instanceof NullLiteral) {
			return true;
		}

		if (expr instanceof NewExpression) {
			return true;
		}

		if (expr instanceof ObjectLiteral) {
			return true;
		}

		if (expr instanceof ArrowFunction) {
			return true;
		}

		if (expr instanceof FunctionExpression) {
			return true;
		}

		if (typeRef != null) {
			Type declType = typeRef.getDeclaredType();
			if (declType != null && declType.isFinal() && typeRef.getTypingStrategy() == TypingStrategy.NOMINAL) {
				return true;
			}
		}

		if (expr instanceof IdentifierRef) {
			IdentifiableElement idElem = ((IdentifierRef) expr).getId();
			if (idElem instanceof TVariable) {
				TVariable tv = (TVariable) idElem;
				if (tv.isConst()) {
					if (tv.isObjectLiteral()) {
						return true;
					}
					if (tv.isNewExpression()) {
						return true;
					}
					// transitivity disabled
					// if (idElem.astElement != null && idElem.astElement instanceof VariableDeclaration) {
					// return isConstTransitiveNewExpressionOrFinalNominalClassInstance((idElem.astElement as
					// VariableDeclaration).expression, null);
					// }
				}
			}
		}

		if (expr instanceof ConditionalExpression) {
			ConditionalExpression ce = (ConditionalExpression) expr;
			return isConstTransitiveNewExpressionOrFinalNominalClassInstanceOrObjectLiteral(ts, G,
					ce.getTrueExpression(), ts.type(G, ce.getTrueExpression()))
					&& isConstTransitiveNewExpressionOrFinalNominalClassInstanceOrObjectLiteral(ts, G,
							ce.getFalseExpression(), ts.type(G, ce.getFalseExpression()));
		}

		if (expr instanceof BinaryLogicalExpression) {
			BinaryLogicalExpression ble = (BinaryLogicalExpression) expr;
			return ble.getOp() == BinaryLogicalOperator.OR
					&& isConstTransitiveNewExpressionOrFinalNominalClassInstanceOrObjectLiteral(ts, G, ble.getLhs(),
							ts.type(G, ble.getLhs()))
					&& isConstTransitiveNewExpressionOrFinalNominalClassInstanceOrObjectLiteral(ts, G, ble.getRhs(),
							ts.type(G, ble.getRhs()));
		}

		return false;
	}

	/**
	 * Tells whether the given declaration will have a representation in the transpiled output code. If this method
	 * returns <code>false</code>, the declaration and its type exists only at compile type.
	 * <p>
	 * The implementation of this method does *NOT* rely on type model elements and can therefore be used in early
	 * stages before the types builder has run and in the transpiler!
	 */
	public static boolean hasRuntimeRepresentation(N4TypeDeclaration typeDecl) {
		boolean isNumberOrStringBasedEnum = typeDecl instanceof N4EnumDeclaration
				&& getEnumKind((N4EnumDeclaration) typeDecl) != EnumKind.Normal;
		return typeDecl != null && !isNumberOrStringBasedEnum && !isHollowElement(typeDecl);
	}

	/**
	 * Tells whether the given identifiable element will have a representation in the transpiled output code. If this
	 * method returns <code>false</code>, the element exists only at compile type.
	 * <p>
	 * The implementation of this method does *NOT* rely on the AST and can therefore be used in resources that were
	 * loaded from the Xtext index.
	 */
	public static boolean hasRuntimeRepresentation(IdentifiableElement element) {
		boolean isNumberOrStringBasedEnum = element instanceof TEnum
				&& getEnumKind((TEnum) element) != EnumKind.Normal;
		return element != null && !isNumberOrStringBasedEnum && !isHollowElement(element);
	}

	/**
	 * Tells whether the given element should be included (usually in a scope), based on the given includeHollows /
	 * includeValueOnlyElements configuration.
	 */
	public static boolean checkInclude(TExportableElement elem, boolean includeHollows,
			boolean includeValueOnlyElements) {
		boolean include = (includeHollows || !N4JSLanguageUtils.isHollowElement(elem))
				&& (includeValueOnlyElements || !N4JSLanguageUtils.isValueOnlyElement(elem));
		return include;
	}

	/**
	 * Elements can have a type-only semantic (called 'hollow'), a value-only semantic, or can have both of them. A
	 * typical example for a hollow element is a shape, a typical example for a value-only element is a const variable,
	 * and a typical example for an element that has both semantics is a class.
	 *
	 * @return {@code true} iff the given element is hollow.
	 */
	public static boolean isHollowElement(TypableElement typableElem) {
		boolean isHollow = typableElem instanceof NamespaceElement && ((NamespaceElement) typableElem).isHollow()
				|| typableElem instanceof Type && ((Type) typableElem).isHollow();
		return isHollow;
	}

	/**
	 * @apiNote #isHollowElement(TypeDefiningElement, JavaScriptVariantHelper)
	 *
	 * @return true iff the given element is value-only.
	 */
	public static boolean isValueOnlyElement(TypableElement typableElem) {
		boolean isValueOnly = typableElem instanceof VariableDeclaration
				|| typableElem instanceof TVariable
				|| typableElem instanceof FunctionDefinition
				|| typableElem instanceof TFunction;
		return isValueOnly;
	}

	/**
	 * @apiNote {@link N4JSLanguageUtils#builtInOrProvidedByRuntimeOrShape(TInterface)}
	 */
	public static boolean builtInOrProvidedByRuntimeOrShape(TMember member) {
		if (member.eContainer() instanceof TInterface) {
			return N4JSLanguageUtils.builtInOrProvidedByRuntimeOrShape((TInterface) member.eContainer());
		}
		return false;
	}

	/**
	 * @apiNote {@link N4JSLanguageUtils#builtInOrProvidedByRuntime(TInterface)}
	 */
	public static boolean builtInOrProvidedByRuntime(TMember member) {
		if (member.eContainer() instanceof TInterface) {
			return N4JSLanguageUtils.builtInOrProvidedByRuntime((TInterface) member.eContainer());
		}
		return false;
	}

	/**
	 * Check if the interface is built-in, provided by runtime, or a shape.
	 *
	 * Note: GHOLD388
	 *
	 * @param tinf
	 *            The interface.
	 * @return true if the interface is either built-in, provided by runtime, or a shape. Return false otherwise.
	 */
	public static boolean builtInOrProvidedByRuntimeOrShape(TInterface tinf) {
		return builtInOrProvidedByRuntime(tinf) || TypeModelUtils.isStructural(tinf.getTypingStrategy());
	}

	/**
	 * Check if the interface is built-in, provided by runtime.
	 *
	 * Note: GHOLD388
	 *
	 * @param tinf
	 *            The interface.
	 * @return true if the interface is either built-in, provided by runtime. Return false otherwise.
	 */
	public static boolean builtInOrProvidedByRuntime(TInterface tinf) {
		return TypeUtils.isBuiltIn(tinf) || tinf.isProvidedByRuntime();
	}

	/**
	 * @return true iff the given classifier is either a shape or a class annotated with ECMASCRIPT
	 */
	public static boolean isShapeOrEcmaScript(N4ClassifierDefinition classifier) {
		if (classifier instanceof N4ClassDefinition) {
			return AnnotationDefinition.ECMASCRIPT.hasAnnotation(classifier);
		} else if (classifier instanceof N4InterfaceDeclaration) {
			return TypeModelUtils.isStructural(((N4InterfaceDeclaration) classifier).getTypingStrategy());
		}
		return false;
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
	public static boolean isOptionalityLessRestrictedOrEqual(OptionalFieldStrategy s1, OptionalFieldStrategy s2) {
		if (s1 == s2) {
			return true;
		}

		if (s1 == OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL) {
			return true;// Always true in this case
		}
		if (s1 == OptionalFieldStrategy.OFF) {
			return (s2 == OptionalFieldStrategy.OFF);
		}

		if (s1 == OptionalFieldStrategy.GETTERS_OPTIONAL) {
			return (s2 == OptionalFieldStrategy.GETTERS_OPTIONAL) || (s2 == OptionalFieldStrategy.OFF);
		}

		throw new RuntimeException("Invalid enum value " + s1);
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
	public static OptionalFieldStrategy minOptionalityFieldStrategy(OptionalFieldStrategy s1,
			OptionalFieldStrategy s2) {
		if ((s1 == OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL)
				&& (s2 == OptionalFieldStrategy.FIELDS_AND_ACCESSORS_OPTIONAL)) {
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
	public static boolean hasValidLHS(AssignmentExpression assignExpr) {
		Expression lhs = assignExpr.getLhs();
		return lhs != null && (lhs.isValidSimpleAssignmentTarget()
				|| (assignExpr.getOp() == AssignmentOperator.ASSIGN
						&& DestructureUtils.isTopOfDestructuringAssignment(assignExpr)));
	}

	/**
	 * Tells whether the given AST node is at a valid location for an await expression or a for-await-of loop. Does not
	 * check whether the given node is actually an await expression/statement.
	 */
	public static boolean isValidLocationForAwait(EObject astNode) {
		FunctionDefinition containingFunDef = EcoreUtil2.getContainerOfType(astNode, FunctionDefinition.class);
		return containingFunDef != null && containingFunDef.isAsync();
	}

	/** Tells whether the given type may be referenced structurally, i.e. with modifiers '~', '~~', '~r~', etc. */
	public static boolean mayBeReferencedStructurally(Type type) {
		return !(type instanceof PrimitiveType);
	}

	/** Tells whether the given type may be referenced dynamically, i.e. with modifier '+'. */
	public static boolean mayBeReferencedDynamically(Type type) {
		return !(type instanceof PrimitiveType) || type instanceof AnyType;
	}

	/** Returns the arguments of the given dynamic import AST element */
	public static Optional<EList<Argument>> getDynamicImportArguments(EObject astElement) {
		if (!(astElement instanceof ParameterizedCallExpression)) {
			return Optional.absent();
		}
		ParameterizedCallExpression pce = (ParameterizedCallExpression) astElement;
		Expression target = pce.getTarget();
		if (!(target instanceof IdentifierRef)) {
			return Optional.absent();
		}
		String targetName = ((IdentifierRef) target).getIdAsText();
		if (N4JSLanguageConstants.IMPORT_KEYWORD != targetName) {
			return Optional.absent();
		}
		return Optional.of(pce.getArguments());
	}

	/** Returns true iff the given AST element is a dynamic import */
	public static boolean isDynamicImportCall(EObject astElement) {
		return getDynamicImportArguments(astElement).isPresent();
	}

	public static boolean isPoly(FunctionTypeExprOrRef signatureTypeRef, ParameterizedAccess pAccess) {
		if (signatureTypeRef == null || !signatureTypeRef.isGeneric()) {
			return false;
		}

		for (var i = 0; i < signatureTypeRef.getTypeVars().size(); i++) {
			TypeVariable dtVar = signatureTypeRef.getTypeVars().get(i);
			if (dtVar.getDefaultArgument() == null && pAccess.getTypeArgs().size() <= i) {
				// we need either a default type argument or an explicit type argument
				return true;
			}
		}
		// all type variables are defined either by default or by explicit type arguments
		return false;
	}
}
