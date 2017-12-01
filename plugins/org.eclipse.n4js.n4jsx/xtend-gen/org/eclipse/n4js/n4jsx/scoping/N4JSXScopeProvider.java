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
package org.eclipse.n4js.n4jsx.scoping;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4jsx.helpers.ReactHelper;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElementName;
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.scoping.members.MemberScopingHelper;
import org.eclipse.n4js.scoping.utils.DynamicPseudoScope;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.xbase.lib.Extension;

/**
 * This class contains custom scoping for N4JSX language. The main difference compared to N4JSScopeProvider
 * is that we need to bind JSX property attributes to fields of "props" type
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping
 * on how and when to use it
 */
@SuppressWarnings("all")
public class N4JSXScopeProvider extends N4JSScopeProvider {
  @Inject
  private MemberScopingHelper memberScopingHelper;
  
  @Inject
  @Extension
  private ReactHelper reactHelper;
  
  @Override
  public IScope getScope(final EObject context, final EReference reference) {
    boolean _equals = Objects.equal(reference, N4JSXPackage.Literals.JSX_PROPERTY_ATTRIBUTE__PROPERTY);
    if (_equals) {
      if ((context instanceof JSXPropertyAttribute)) {
        EObject _eContainer = ((JSXPropertyAttribute)context).eContainer();
        final JSXElement jsxElem = ((JSXElement) _eContainer);
        final TypeRef propsTypeRef = this.reactHelper.getPropsType(jsxElem);
        final boolean checkVisibility = true;
        final boolean staticAccess = false;
        if ((propsTypeRef != null)) {
          final IScope memberScope = this.memberScopingHelper.createMemberScope(propsTypeRef, ((MemberAccess)context), checkVisibility, staticAccess);
          return new DynamicPseudoScope(memberScope);
        } else {
          final IScope scope = super.getScope(context, reference);
          return new DynamicPseudoScope(scope);
        }
      }
    }
    JSXElementName jsxElName = null;
    for (EObject ei = context; (ei != null); ei = ei.eContainer()) {
      if ((ei instanceof JSXElementName)) {
        jsxElName = ((JSXElementName)ei);
      }
    }
    if ((jsxElName != null)) {
      final IScope scope_1 = super.getScope(context, reference);
      return new DynamicPseudoScope(scope_1);
    }
    return super.getScope(context, reference);
  }
}
