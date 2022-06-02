/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.hover;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.UtilN4;

import com.google.inject.Inject;

/**
 */
public class N4JSElementSignatureProvider {

	@Inject
	private N4JSTypeSystem ts;

	/***/
	public String get(EObject o) {
		if (o instanceof LiteralOrComputedPropertyName) {
			return get(o.eContainer());
		} else if (o instanceof ParameterizedTypeRef) {
			return ((ParameterizedTypeRef) o).getTypeRefAsString();
		}
		EObject id = getIdentifiableElement(o);
		if (id == null) {
			return null;
		}
		String label = doGetLabel(id, o);
		UtilN4.sanitizeForHTML(label);
		return label;
	}

	private EObject getIdentifiableElement(EObject o) {
		if (o instanceof IdentifierRef) {
			return ((IdentifierRef) o).getId();
		}
		if (o instanceof ParameterizedPropertyAccessExpression) {
			return ((ParameterizedPropertyAccessExpression) o).getProperty();
		}
		if (o instanceof LiteralOrComputedPropertyName) {
			return ((LiteralOrComputedPropertyName) o).eContainer();
		}
		return o;
	}

	private String doGetLabel(EObject obj, EObject ref) {
		if (obj instanceof IdentifierRef) {
			return getLabelFromTypeSystem((IdentifierRef) obj, obj);

		} else if (obj instanceof TVariable) {
			return getLabelFromTypeSystem((TVariable) obj, ref);

		} else if (obj instanceof ParameterizedPropertyAccessExpression) {
			return getLabelFromTypeSystem((ParameterizedPropertyAccessExpression) obj, obj);

		} else if (obj instanceof PropertyNameValuePair) {
			return getLabelFromTypeSystem((PropertyNameValuePair) obj, ref);

		} else if (obj instanceof FormalParameter) {
			FormalParameter fp = (FormalParameter) obj;
			String optinonalMarker = fp.isHasInitializerAssignment() ? "=…" : "";
			return getLabelFromTypeSystem(fp, ref) + optinonalMarker;

		} else if (obj instanceof FunctionExpression) {
			return getLabelFromTypeSystem((FunctionExpression) obj, ref);

		} else if (obj instanceof LiteralOrComputedPropertyName) {
			if (obj.eContainer() instanceof TypableElement) {
				return getLabelFromTypeSystem((TypableElement) obj.eContainer(), ref);

			} else {
				return ((LiteralOrComputedPropertyName) obj).getName();

			}
		} else {
			EObject tElem = N4JSASTUtils.getCorrespondingTypeModelElement(obj);
			return CustomHoverLabelUtil.getLabel(tElem);
		}
	}

	private String getLabelFromTypeSystem(TypableElement o, EObject ref) {
		if (null == o || null == o.eResource()) {
			return null;
		}
		TypableElement elem = ref instanceof TypableElement ? (TypableElement) ref : o;
		TypeRef typeRef = ts.type(RuleEnvironmentExtensions.newRuleEnvironment(elem), elem);

		String name = "";
		if (o instanceof NamedElement) {
			name = ((NamedElement) o).getName();
		} else if (o instanceof IdentifiableElement) {
			name = ((IdentifiableElement) o).getName();
		} else if (o instanceof TVariable) {
			name = ((TVariable) o).isConst() ? "const" : "var";
		}

		String str = name + ": " + typeRef.getTypeRefAsStringWithAliasExpansion();
		return str;
	}

}
