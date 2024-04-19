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
package org.eclipse.n4js.tooling.react;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.resource.N4JSCache;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.ElementExportDefinition;
import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.xbase.lib.StringExtensions;

import com.google.inject.Inject;

/**
 * This helper provides utilities for looking up React definitions such as React.Component or React.ReactElement or for
 * calculating types related to React (e.g. of props property) etc.
 */
public class ReactHelper {
	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private N4JSCache cache;
	@Inject
	private IScopeProvider scopeProvider;

	/***/
	public final static String REACT_PROJECT_ID = "react";
	/***/
	public final static String REACT_COMPONENT = "Component";
	/***/
	public final static String REACT_NODE = "ReactNode";
	/***/
	public final static String REACT_ELEMENT = "ReactElement";
	/***/
	public final static String REACT_FRAGMENT_NAME = "Fragment";

	/***/
	public final static String REACT_NAMESPACE_NAME = StringExtensions.toFirstUpper(REACT_PROJECT_ID);
	/***/
	public final static String REACT_JSX_TRANSFORM_NAME = "jsx";
	/***/
	public final static String REACT_JSXS_TRANSFORM_NAME = "jsxs";
	/***/
	public final static String REACT_ELEMENT_FACTORY_FUNCTION_NAME = "createElement";
	/***/
	public final static String REACT_ELEMENT_PROPERTY_KEY_NAME = "key";
	/***/
	public final static String REACT_ELEMENT_PROPERTY_CHILDREN_NAME = "children";
	/***/
	public final static String REACT_JSX_RUNTIME_NAME = "react/jsx-runtime";

	private final static String REACT_KEY = "KEY__" + REACT_PROJECT_ID;

	/**
	 * Returns the {@link TModule} which contains the definitions of the React JSX backend (e.g. {@code Component},
	 * {@code createElement}).
	 *
	 * Returns {@code null} if no valid React implementation can be found in the index.
	 */
	public TModule getJsxBackendModule(Resource resource) {
		String key = REACT_KEY + "." + "TMODULE";
		return cache.get(resource, () -> {
			IScope scope = ((N4JSScopeProvider) scopeProvider).getScopeForImplicitImports((N4JSResource) resource);
			Iterable<IEObjectDescription> matchingDescriptions = scope
					.getElements(QualifiedName.create(REACT_PROJECT_ID));
			// resolve all found 'react.js' files, until a valid react implementation is found
			for (IEObjectDescription desc : matchingDescriptions) {
				if (desc != null) {
					// filter for valid react modules only
					TModule module = (TModule) EcoreUtil.resolve(desc.getEObjectOrProxy(), resource);
					if (isValidReactModule(module)) {
						return module;
					}
				}
			}
			return null;
		}, key);
	}

	/**
	 * Returns the preferred name of the namespace used to import the JSX backend.
	 */
	public String getJsxBackendNamespaceName() {
		return REACT_NAMESPACE_NAME;
	}

	/**
	 * Returns the name of the element factory function to use with React as JSX backend.
	 */
	public String getJsxBackendElementFactoryFunctionName() {
		return REACT_ELEMENT_FACTORY_FUNCTION_NAME;
	}

	/**
	 * Calculate the type that an JSX element is binding to, usually class/function type
	 *
	 * @param jsxElem
	 *            the input JSX element
	 * @return the {@link TypeRef} that the JSX element is binding to and null if not found
	 */
	public TypeRef getJsxElementBindingType(JSXElement jsxElem) {
		Expression expr = jsxElem.getJsxElementName().getExpression();
		RuleEnvironment G = newRuleEnvironment(expr);
		TypeRef exprResult = ts.type(G, expr);
		return exprResult;
	}

	/**
	 * Returns the element factory function for JSX elements which can be extracted from the given {@link Resource}.
	 */
	public TFunction getJsxBackendElementFactoryFunction(Resource resource) {
		TModule module = this.getJsxBackendModule(resource);
		if (module != null) {
			return lookUpReactElementFactoryFunction(module);
		}
		return null;
	}

	/**
	 * Returns the fragment factory function for JSX elements which can be extracted from the given {@link Resource}.
	 */
	public IdentifiableElement getJsxBackendFragmentComponent(Resource resource) {
		TModule module = this.getJsxBackendModule(resource);
		if (module != null) {
			return lookUpReactFragmentComponent(module);
		}
		return null;
	}

	/**
	 * Look up React.Node in the index.
	 *
	 * @param context
	 *            the EObject serving the context to look for React.Node.
	 */
	public TClassifier lookUpReactNode(EObject context) {
		// val reactElement = lookUpReactClassifier(context, REACT_ELEMENT, TInterface)
		// if (reactElement !== null) {
		// return reactElement;
		// }
		return lookUpReactClassifier_OLD(context, REACT_NODE, TInterface.class);
	}

	/**
	 * Look up React.Element in the index.
	 *
	 * @param context
	 *            the EObject serving the context to look for React.Element.
	 */
	public TClassifier lookUpReactElement(EObject context) {
		// val reactElement = lookUpReactClassifier(context, REACT_ELEMENT, TInterface)
		// if (reactElement !== null) {
		// return reactElement;
		// }
		return lookUpReactClassifier_OLD(context, REACT_ELEMENT, TInterface.class);
	}

	/**
	 * Look up React.Component in the index.
	 *
	 * @param context
	 *            the EObject serving the context to look for React.Component.
	 */
	public TClass lookUpReactComponent(EObject context) {
		// val reactComponent = lookUpReactClassifier(context, REACT_COMPONENT, TClass);
		// if (reactComponent !== null) {
		// return reactComponent;
		// }
		return lookUpReactClassifier_OLD(context, REACT_COMPONENT, TClass.class);
	}

	/**
	 * Calculate the "props" type of an JSX element. It is either the first type parameter of React.Component class or
	 * the type of the first parameter of a functional React component
	 *
	 * @param jsxElem
	 *            the input JSX element
	 * @return the {@link TypeRef} if exists and null otherwise
	 */
	public TypeRef getPropsType(JSXElement jsxElem) {
		TypeRef exprTypeRef = getJsxElementBindingType(jsxElem);
		if (exprTypeRef == null) {
			return null;
		}

		RuleEnvironment G = newRuleEnvironment(jsxElem);
		if (exprTypeRef instanceof TypeTypeRef && ((TypeTypeRef) exprTypeRef).isConstructorRef()) {
			// The JSX element refers to a class
			Type tclass = tsh.getStaticType(G, (TypeTypeRef) exprTypeRef);
			TClass tComponentClass = lookUpReactComponent(jsxElem);
			if (tComponentClass == null || tComponentClass.getTypeVars().isEmpty()) {
				return null;
			}
			TypeVariable reactComponentProps = tComponentClass.getTypeVars().get(0);
			// Add type variable -> type argument mappings from the current and all super types
			tsh.addSubstitutions(G, TypeUtils.createTypeRef(tclass));
			// Substitute type variables in the 'props' and return the result
			// Note: after substTypeVariablesInTypeRef is called, the rule environment G is unchanged so do not ask G
			// for result as this caused bug IDE-2540
			TypeRef reactComponentPropsTypeRef = ts.substTypeVariables(G,
					TypeUtils.createTypeRef(reactComponentProps));
			return reactComponentPropsTypeRef;

		} else if (exprTypeRef instanceof FunctionTypeExprOrRef) {
			FunctionTypeExprOrRef fteor = (FunctionTypeExprOrRef) exprTypeRef;
			// The JSX elements refers to a function, assume that the first parameter is props
			if (fteor.getFpars().size() > 0) {
				TFormalParameter tPropsParam = fteor.getFpars().get(0);
				return tPropsParam.getTypeRef();
			}
		}
		return null;
	}

	/***/
	public TypeRef getConstructorFunctionType(JSXElement jsxElem) {
		RuleEnvironment G = newRuleEnvironment(jsxElem);
		TypeRef returnTypeRef = getJsxElementBindingType(jsxElem);
		if (returnTypeRef instanceof TypeTypeRef && ((TypeTypeRef) returnTypeRef).getTypeArg() instanceof TypeRef) {
			returnTypeRef = (TypeRef) ((TypeTypeRef) returnTypeRef).getTypeArg();
		}
		List<TypeRef> args = Collections.singletonList(anyTypeRef(G));
		return TypeUtils.createFunctionTypeExpression(args, returnTypeRef);
	}

	/**
	 * Return the type of a field or return type of a getter.
	 *
	 * @param member
	 *            MUST be either a field or getter (otherwise an exception is thrown).
	 */
	public TypeRef typeRefOfFieldOrGetter(TMember member, TypeRef context) {
		if (member instanceof TField || member instanceof TGetter)
			return ts.tau(member, context);

		throw new IllegalArgumentException(member + " must be either a TField or TGetter");
	}

	/**
	 * Lookup React component/element type. For increased efficiency, the found results are cached.
	 *
	 * @param context
	 *            the EObject serving the context to look for React classifiers.
	 * @param reactClassifierName
	 *            the name of React classifier.
	 */
	// TODO: continue work on use of TypeScript type definitions of React
	@SuppressWarnings("unchecked")
	private <T extends TClassifier> T lookUpReactClassifier(EObject context, String reactClassifierName,
			Class<T> clazz) {
		Resource resource = context.eResource();
		TModule tModule = getJsxBackendModule(resource);
		if (tModule == null || tModule.eResource() == null) {
			return null;
		}

		String key = REACT_KEY + "." + reactClassifierName;
		return cache.get(tModule.eResource(), () -> {
			// used for @types/react
			for (ExportDefinition expDef : tModule.getExportDefinitions()) {
				if (expDef instanceof ElementExportDefinition) {
					ElementExportDefinition eed = (ElementExportDefinition) expDef;
					if ("React".equals(eed.getExportedName()) && eed.getExportedElement() instanceof TNamespace) {
						for (Type type : ((TNamespace) eed.getExportedElement()).getTypes()) {
							if (clazz.isAssignableFrom(type.getClass()) && reactClassifierName.equals(type.getName())) {
								return (T) type;
							}
						}
					}
				}
			}
			return null;
		}, key);
	}

	private <T extends TClassifier> T lookUpReactClassifier_OLD(EObject context, String reactClassifierName,
			Class<T> clazz) {
		Resource resource = context.eResource();
		TModule tModule = getJsxBackendModule(resource);
		if (tModule == null || tModule.eResource() == null) {
			return null;
		}

		String key = REACT_KEY + "_OLD." + reactClassifierName;
		return cache.get(tModule.eResource(), () -> {
			// used for @n4jsd/react
			T tClassifier = findFirst(filter(tModule.getTypes(), clazz),
					c -> Objects.equals(c.getName(), reactClassifierName));
			return tClassifier;
		}, key);
	}

	private IdentifiableElement lookUpReactFragmentComponent(TModule module) {
		if (module != null) {
			// used for @types/react
			for (ExportDefinition expDef : module.getExportDefinitions()) {
				if (expDef instanceof ElementExportDefinition) {
					ElementExportDefinition eed = (ElementExportDefinition) expDef;
					if ("React".equals(eed.getExportedName()) && eed.getExportedElement() instanceof TNamespace) {
						for (TVariable currTopLevelVar : ((TNamespace) eed.getExportedElement())
								.getExportedVariables()) {
							if (REACT_FRAGMENT_NAME.equals(currTopLevelVar.getName())) {
								return currTopLevelVar;
							}
						}
					}
				}
			}
			// used for @n4jsd/react
			for (Type currTopLevelType : module.getTypes()) {
				if (currTopLevelType instanceof TClass
						&& REACT_FRAGMENT_NAME.equals(currTopLevelType.getName())) {
					return currTopLevelType;
				}
			}
		}
		return null;
	}

	/**
	 * Looks up the element factory function to use, assuming the react implementation in use is to be found in the
	 * given {@code module}.
	 *
	 * Returns {@code null} if no factory function can be found in the given module.
	 */
	private TFunction lookUpReactElementFactoryFunction(TModule module) {
		if (module != null) {
			// used for @types/react
			for (ExportDefinition expDef : module.getExportDefinitions()) {
				if (expDef instanceof ElementExportDefinition) {
					ElementExportDefinition eed = (ElementExportDefinition) expDef;
					if ("React".equals(eed.getExportedName()) && eed.getExportedElement() instanceof TNamespace) {
						for (TFunction currTopLevelType : ((TNamespace) eed.getExportedElement()).getFunctions()) {
							if (REACT_ELEMENT_FACTORY_FUNCTION_NAME.equals(currTopLevelType.getName())) {
								return currTopLevelType;
							}
						}
					}
				}
			}
			// used for @n4jsd/react
			for (TFunction currTopLevelType : module.getFunctions()) {
				if (REACT_ELEMENT_FACTORY_FUNCTION_NAME.equals(currTopLevelType.getName())) {
					return currTopLevelType;
				}
			}
		}
		return null;
	}

	/**
	 * Returns {@code true} if the given {@code module} is a {@link TModule} that represents a valid implementation of
	 * React.
	 *
	 * Returns {@code false} otherwise.
	 *
	 * Note that this requires type definitions to be available for the given {@link TModule}.
	 */
	private boolean isValidReactModule(TModule module) {
		// check for existence of the element factory function
		return lookUpReactElementFactoryFunction(module) != null;
	}
}
