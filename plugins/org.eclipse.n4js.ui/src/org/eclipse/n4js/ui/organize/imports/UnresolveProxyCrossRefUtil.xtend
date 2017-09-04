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

import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension org.eclipse.n4js.organize.imports.RefNameUtil.*
import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

/**
 * Finds unresolved cross proxy references via {@link EcoreUtil.UnresolvedProxyCrossReferencer}.
 */
class UnresolveProxyCrossRefUtil {

	/** Finds unresolved cross proxies and returns tuples of reference object and used name. */
	public static def Iterable<ReferenceProxyInfo> findProxyCrossRefInfo(Script script) {
		EcoreUtil.UnresolvedProxyCrossReferencer.find(script).values.flatten.map[it.EObject].map [
			new ReferenceProxyInfo(it, it.getRefName)
		]
	}

	private static def String getRefName(EObject obj) {
		switch (obj) {
			IdentifierRef:
				obj.findIdentifierName
			ParameterizedTypeRef:{
				val name = obj.findTypeName
				if(!obj.isParameterized)
					return name
				else{
					val index = name.indexOf('<')
						if(index > -1)
							return name.substring(0, index)
						else
							return name
				}
			}
				
			default:
				obj.node.tokenText
		}
	}
}
