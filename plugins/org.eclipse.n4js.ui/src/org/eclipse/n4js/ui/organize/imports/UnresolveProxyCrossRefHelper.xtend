/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.organize.imports

import com.google.common.base.Strings
import com.google.common.collect.Iterables
import javax.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.JSXElementName
import org.eclipse.n4js.n4JS.JSXPropertyAttribute
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4jsx.ReactHelper
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.utils.ResourceType

import static extension org.eclipse.n4js.organize.imports.RefNameUtil.*
import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

/**
 * Finds unresolved cross proxy references via {@link EcoreUtil.UnresolvedProxyCrossReferencer}.
 * Also handles {@link JSXElement} bindings
 */
class UnresolveProxyCrossRefHelper {

	@Inject private ReactHelper reactHelper;

	/** Finds unresolved cross proxies and returns tuples of reference object and used name. */
	public def Iterable<ReferenceProxyInfo> findProxyCrossRefInfo(Script script) {
		val resourceType = ResourceType.getResourceType(script);
		val unresolvedCrossRefs = findBrokenCrossRefs(script, resourceType)
		val proxyInfos = unresolvedCrossRefs.map [new ReferenceProxyInfo(it, it.getRefName)]
		return filter(proxyInfos, resourceType)
	}
	
	private def findBrokenCrossRefs(Script script, ResourceType resourceType){
		val list = newArrayList

		list.add(EcoreUtil.UnresolvedProxyCrossReferencer.find(script).values.flatten.map[it.EObject])

		if(resourceType === ResourceType.N4JSX)
			list.add(findBrokenJSXBindings(script))

		return Iterables.concat(list)
	}
	
	/** Does additional filtering of proxies based on the resource type. */
	private def filter(Iterable<ReferenceProxyInfo> references, ResourceType resourceType) {
		switch (resourceType) {
		case N4JSX	: return references.filter[!n4jxIsToBeIgnored(it)]
		default		: return references
		}
	}

	/**
	 * If reference is using name that starts with lower case it looks like HTML tag, if on top of that it is part of
	 * {@link JSXElementName} or {@link JSXPropertyAttribute} than this reference should not be subject to Organize
	 * Imports.
	 */
	private def n4jxIsToBeIgnored(ReferenceProxyInfo proxyInfo) {
		// only if reference is looks like HTML tag
		val usedName = proxyInfo.name;
		val boolean isLikeHTML = if(Strings.isNullOrEmpty(usedName)){false} else Character.isLowerCase(usedName.charAt(0));
		
		if(isLikeHTML)
			return false

		// is reference part of JSX element
		val refContainer = proxyInfo.eobject.eContainer();
		if(refContainer === null)
			return false

		return refContainer instanceof JSXElementName || refContainer instanceof JSXPropertyAttribute
	}

	/** Returns list of {@link JSXElementName} that have broken bindings */
	private def Iterable<JSXElementName> findBrokenJSXBindings(Script script){
		return script
					.eAllContents
					.filter(JSXElement)
					.map[jsxe |jsxe->reactHelper.getJsxElementBindingType(jsxe)]
					.filter[value instanceof UnknownTypeRef]
					.map[key.jsxElementName]
					.toIterable
	}

	private def String getRefName(EObject obj) {
		switch (obj) {
			IdentifierRef:
				return obj.findIdentifierName
			ParameterizedTypeRef: {
				val name = obj.findTypeName
				if (!obj.isParameterized)
					return name
				else {
					val index = name.indexOf('<')
					if (index > -1)
						return name.substring(0, index)
					else
						return name
				}
			}
			default:
				return obj.node.tokenText
		}
	}
}
