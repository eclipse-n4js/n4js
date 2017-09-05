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
package org.eclipse.n4js.n4jsx.helpers

import com.google.inject.Inject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.TypeSystemHelper
import org.eclipse.xtext.util.IResourceScopeCache

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * This helper provides utilities for looking up React definitions such as React.Component or React.Element or
 * for calculating types related to React (e.g. of props property) etc.
 */
class ReactHelper {
	@Inject	protected N4JSTypeSystem ts
	@Inject protected TypeSystemHelper tsh
	@Inject	private IResourceScopeCache resourceScopeCacheHelper
	@Inject private IN4JSCore n4jsCore

	public final static String REACT_MODULE = "react"
	public final static String REACT_KEY = "KEY__" + REACT_MODULE
	public final static String REACT_COMPONENT = "Component"
	public final static String REACT_ELEMENT = "Element"
	public final static String REACT_DEFINITION_FILE = REACT_MODULE + "." + N4JSGlobals.N4JSD_FILE_EXTENSION

	/**
	 * Look up React.Element in the index.
	 *
	 * @param context the EObject serving the context to look for React.Element.
	 */
	def public TClassifier lookUpReactElement(EObject context) {
		return lookUpReactClassifier(context, REACT_ELEMENT)
	}

	/**
	 * Look up React.Component in the index.
	 *
	 * @param context the EObject serving the context to look for React.Component.
	 */
	def public TClassifier lookUpReactComponent(EObject context) {
		return lookUpReactClassifier(context, REACT_COMPONENT);
	}

	/**
	 * Lookup React component/element type. For increased efficiency, the found results are cached.
	 *
	 * @param context the EObject serving the context to look for React classifiers.
	 * @param reactClassifierName the name of React classifier.
	 */
	def private TClassifier lookUpReactClassifier(EObject context, String reactClassifierName) {
		val String key = REACT_KEY + "." + reactClassifierName;
		return resourceScopeCacheHelper.get(key, context.eResource, [
			val project = n4jsCore.findProject(context.eResource.URI).get;

			var URI reactURI = null
			// Lookup react.n4jsd in source folders
			for (sourceContainer : project.sourceContainers) {
				if (reactURI === null) {
					// srcContainer is an iterable URIs
					val ret = sourceContainer.findFirst[trimFragment.lastSegment == REACT_DEFINITION_FILE]
					if (ret !== null) {
						reactURI = ret
					}
				}
			}

			if (reactURI === null) {
				return null;
			}
			// Look up React classifier in the TModule
			val tmodule = context.eResource.resourceSet.getEObject(reactURI.appendFragment("/1"), true);
			val reactClassifier = tmodule.eAllContents.filter(TClassifier).findFirst[name == reactClassifierName]
			return reactClassifier
		])
	}

	/**
	 * Calculate the type that an JSX element is binding to, usually class/function type
	 *
	 * @param jsxElem the input JSX element
	 * @return the typeref that the JSX element is binding to and null if not found
	 */
	def public TypeRef getJSXElementBindingType(JSXElement jsxElem) {
		val expr = jsxElem.jsxElementName.expression;
		val G = expr.newRuleEnvironment;
		val exprResult = ts.type(G, expr);
		if (exprResult.failed) {
			return null;
		} else {
			return exprResult.value;
		}
	}

	/**
	 * Calculate the "props" type of an JSX element. It is either the first type parameter of React.Component class or
	 * the type of the first parameter of a functional React component
	 *
	 * @param jsxElement the input JSX element
	 * @return the typeref if exists and null otherwise
	 */
	def public TypeRef getPropsType(JSXElement jsxElem) {
		val TypeRef exprTypeRef = jsxElem.JSXElementBindingType
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
			val reactComponentPropsTypeRef = ts.substTypeVariablesInTypeRef(G, TypeUtils.createTypeRef(reactComponentProps));
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
		if (member instanceof TField || member instanceof TGetter) {
			return ts.tau(member, context);
		} else {
			throw new IllegalArgumentException(member + " must be either a TField or TGetter");
		}
	}
}
