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
package org.eclipse.n4js.n4jsx

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.util.IResourceScopeCache

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * This helper provides utilities for looking up React definitions such as React.Component or React.Element or
 * for calculating types related to React (e.g. of props property) etc.
 */
class ReactHelper {
	@Inject private N4JSTypeSystem ts
	@Inject private TypeSystemHelper tsh
	@Inject private IResourceScopeCache resourceScopeCacheHelper
	@Inject private IScopeProvider scopeProvider;

	public final static String REACT_PROJECT_ID = "react"
	public final static String REACT_COMPONENT = "Component"
	public final static String REACT_ELEMENT = "Element"

	public final static String REACT_NAMESPACE_NAME = REACT_PROJECT_ID.toFirstUpper;
	public final static String REACT_ELEMENT_FACTORY_FUNCTION_NAME = "createElement";

	private final static String REACT_KEY = "KEY__" + REACT_PROJECT_ID

	/**
	 * Returns the {@link TModule} which contains the definitions of the React 
	 * JSX backend (e.g. {@code Component}, {@code createElement}).
	 * 
	 * Returns {@code null} if no valid React implementation can be found in the index.  
	 */
	def public TModule getJsxBackendModule(Resource resource) {
		val String key = REACT_KEY + "." + "TMODULE";
		return resourceScopeCacheHelper.get(key, resource, [
			val scope = (scopeProvider as N4JSScopeProvider).getScopeForImplicitImports(resource as N4JSResource);
			val matchingDescriptions = scope.getElements(QualifiedName.create(REACT_PROJECT_ID));
			// resolve all found 'react.js' files, until a valid react implementation is found
			return matchingDescriptions.map[desc |
				if(desc === null)
				return null
				return EcoreUtil2.resolve(desc.EObjectOrProxy, resource) as TModule
			]
			// filter for valid react modules only
			.filter[module | module.isValidReactModule].head;
		]);
	}
	
	/**
	 * Returns the preferred name of the namespace used to import the JSX backend.
	 */
	def public String getJsxBackendNamespaceName() {
		return REACT_NAMESPACE_NAME;
	}

	/**
	 * Returns the name of the element factory function to use with React as JSX backend.
	 */
	def getJsxBackendElementFactoryFunctionName() {
		return REACT_ELEMENT_FACTORY_FUNCTION_NAME;
	}

	/**
	 * Calculate the type that an JSX element is binding to, usually class/function type
	 * 
	 * @param jsxElem the input JSX element
	 * @return the typeref that the JSX element is binding to and null if not found
	 */
	def public TypeRef getJsxElementBindingType(JSXElement jsxElem) {
		val expr = jsxElem.jsxElementName.expression;
		val G = expr.newRuleEnvironment;
		val exprResult = ts.type(G, expr);
		if (exprResult.failure)
			return null;

		return exprResult.value;
	}
	
	/**
	 * Returns the element factory function for JSX elements which can be extracted
	 * from the given {@link Resource}.
	 */
	def public TFunction getJsxBackendElementFactoryFunction(Resource resource) {
		val module = this.getJsxBackendModule(resource);
		if (module !== null) {
			return lookUpReactElementFactoryFunction(module);
		}
		return null;
	}

	/**
	 * Look up React.Element in the index.
	 * 
	 * @param context the EObject serving the context to look for React.Element.
	 */
	def public TClassifier lookUpReactElement(EObject context) {
		val reactElement = lookUpReactClassifier(context, REACT_ELEMENT)
		return reactElement;
	}

	/**
	 * Look up React.Component in the index.
	 * 
	 * @param context the EObject serving the context to look for React.Component.
	 */
	def public TClassifier lookUpReactComponent(EObject context) {
		val reactComponent = lookUpReactClassifier(context, REACT_COMPONENT)
		return reactComponent;
	}

	/**
	 * Calculate the "props" type of an JSX element. It is either the first type parameter of React.Component class or
	 * the type of the first parameter of a functional React component
	 * 
	 * @param jsxElement the input JSX element
	 * @return the typeref if exists and null otherwise
	 */
	def public TypeRef getPropsType(JSXElement jsxElem) {
		val TypeRef exprTypeRef = jsxElem.jsxElementBindingType
		if (exprTypeRef === null)
			return null;

		val G = jsxElem.newRuleEnvironment;
		if (exprTypeRef instanceof TypeTypeRef && (exprTypeRef as TypeTypeRef).constructorRef) {
			// The JSX elements refers to a class
			val tclass = tsh.getStaticType(G, exprTypeRef as TypeTypeRef);
			val tComponentClassifier = lookUpReactClassifier(jsxElem, ReactHelper.REACT_COMPONENT);
			if (tComponentClassifier === null || tComponentClassifier.typeVars.isEmpty) {
				return null;
			}
			val reactComponentProps = tComponentClassifier.typeVars.get(0);
			// Add type variable -> type argument mappings from the current and all super types
			tsh.addSubstitutions(G, TypeUtils.createTypeRef(tclass));
			// Substitute type variables in the 'props' and return the result
			// Note: after substTypeVariablesInTypeRef is called, the rule environment G is unchanged so do not ask G for result as this caused bug IDE-2540
			val reactComponentPropsTypeRef = ts.substTypeVariablesInTypeRef(G,
				TypeUtils.createTypeRef(reactComponentProps));
			return reactComponentPropsTypeRef;

		} else if (exprTypeRef instanceof FunctionTypeExprOrRef) {
			// The JSX elements refers to a function, assume that the first parameter is props
			if (exprTypeRef.fpars.length > 0) {
				val tPropsParam = exprTypeRef.fpars.get(0);
				return tPropsParam.typeRef
			}
		}
		return null;
	}

	/**
	 * Return the type of a field or return type of a getter.
	 * 
	 * @param member MUST be either a field or getter (otherwise an exception is thrown).
	 */
	def public TypeRef typeRefOfFieldOrGetter(TMember member, TypeRef context) {
		if (member instanceof TField || member instanceof TGetter)
			return ts.tau(member, context);

		throw new IllegalArgumentException(member + " must be either a TField or TGetter");
	}
	
	/**
	 * Lookup React component/element type. For increased efficiency, the found results are cached.
	 * 
	 * @param context the EObject serving the context to look for React classifiers.
	 * @param reactClassifierName the name of React classifier.
	 */
	def private TClassifier lookUpReactClassifier(EObject context, String reactClassifierName) {
		val resource = context.eResource;
		val String key = REACT_KEY + "." + reactClassifierName;
		return resourceScopeCacheHelper.get(key, resource, [
			val tModule = getJsxBackendModule(resource);
			if (tModule === null)
				return null;

			val tClassifier = tModule.topLevelTypes.filter(TClassifier).findFirst[name == reactClassifierName];
			return tClassifier;
		]);
	}
	
	/**
	 * Looks up the element factory function to use, assuming the react implementation in use
	 * is to be found in the given {@code module}.
	 * 
	 * Returns {@code null} if no factory function can be found in the given module.
	 */
	def private TFunction lookUpReactElementFactoryFunction(TModule module) {
		if (module !== null) {
			for (Type currTopLevelType : module.getTopLevelTypes()) {
				if (currTopLevelType instanceof TFunction
						&& REACT_ELEMENT_FACTORY_FUNCTION_NAME.equals(currTopLevelType.getName())) {
					return currTopLevelType as TFunction;
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns {@code true} if the given {@code module} is a {@link TModule}
	 * that represents a valid implementation of React.
	 * 
	 * Returns {@code false} otherwise.
	 * 
	 * Note that this requires type definitions to be available for the given {@link TModule}.
	 */
	def private boolean isValidReactModule(TModule module) {
		// check for existence of the element factory function
		return lookUpReactElementFactoryFunction(module) !== null;
	}
}
