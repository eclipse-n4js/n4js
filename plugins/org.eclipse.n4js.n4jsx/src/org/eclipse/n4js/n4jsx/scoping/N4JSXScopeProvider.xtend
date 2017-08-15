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
package org.eclipse.n4js.n4jsx.scoping

import com.google.inject.Inject
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.scoping.members.MemberScopingHelper
import org.eclipse.n4js.scoping.utils.DynamicPseudoScope
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.n4jsx.helpers.ReactHelper
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement
import org.eclipse.n4js.n4jsx.n4JSX.JSXElementName
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute
import org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference

/**
 * This class contains custom scoping for N4JSX language. The main difference compared to N4JSScopeProvider
 * is that we need to bind JSX property attributes to fields of "props" type
 *
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping
 * on how and when to use it
 */
class N4JSXScopeProvider extends N4JSScopeProvider {
	@Inject MemberScopingHelper memberScopingHelper
	@Inject extension ReactHelper reactHelper;

	override getScope(EObject context, EReference reference) {
		if (reference == N4JSXPackage.Literals.JSX_PROPERTY_ATTRIBUTE__PROPERTY) {
			if (context instanceof JSXPropertyAttribute) {
				val jsxElem = (context.eContainer as JSXElement);
				val TypeRef propsTypeRef = jsxElem.getPropsType();
				val checkVisibility = true;
				val staticAccess = false;
				if (propsTypeRef !== null) {
					// Prevent "Cannot resolve to element" error message of unknown attributes since
					// we want to issue a warning instead
					val memberScope = memberScopingHelper.createMemberScope(propsTypeRef, context,
						checkVisibility, staticAccess);
					return new DynamicPseudoScope(memberScope);
				} else {
					val scope = super.getScope(context, reference);
					return new DynamicPseudoScope(scope);
				}
			}
		}

		// Otherwise, if we are within a JSX element context but cannot bind JSX element, ignore binding for now
		var JSXElementName jsxElName;
		for (var ei = context; ei !== null; ei = ei.eContainer) {
			if (ei instanceof JSXElementName) {
				jsxElName = ei;
			}
		}
		if (jsxElName !== null) {
			val scope = super.getScope(context, reference);
			return new DynamicPseudoScope(scope);
		}

		return super.getScope(context, reference);
	}
}
