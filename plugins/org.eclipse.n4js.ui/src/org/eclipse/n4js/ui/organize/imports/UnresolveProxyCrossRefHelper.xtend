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

import com.google.common.collect.Iterables
import javax.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.JSXElementName
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
		val unresolvedCrossRefs = EcoreUtil.UnresolvedProxyCrossReferencer.find(script).values.flatten.map[it.EObject]
		val unresolvedJSX = findBrokenJSXBindings(script)

		return Iterables
						.concat(unresolvedCrossRefs,unresolvedJSX)
						.map [new ReferenceProxyInfo(it, it.getRefName)]
	}

	private def Iterable<JSXElementName> findBrokenJSXBindings(Script script){
		val resType = ResourceType.getResourceType(script);

		if(resType !== ResourceType.N4JSX)
			return #[]

		return script
					.eAllContents
					.filter(JSXElement)
					.map[jsxe |jsxe->reactHelper.getJSXElementBindingType(jsxe)]
					.filter[value instanceof UnknownTypeRef]
					.map[key.jsxElementName]
					.toIterable
	}

	public def String getRefName(EObject obj) {
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
