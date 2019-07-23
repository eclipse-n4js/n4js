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
package org.eclipse.n4js.ide.server.hover

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.ide.server.hover.CustomHoverLabelUtil
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.typesystem.N4JSTypeSystem

import static org.eclipse.n4js.utils.UtilN4.sanitizeForHTML

import static extension org.eclipse.n4js.n4JS.N4JSASTUtils.getCorrespondingTypeModelElement
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment

/**
 */
class N4JSElementSignatureProvider {

	@Inject
	private N4JSTypeSystem ts;

	def public String get(EObject o) {
		if (o instanceof LiteralOrComputedPropertyName) {
			return get(o.eContainer);
		}
		val id = getIdentifiableElement(o);
		val label = doGetLabel(id, o);
		sanitizeForHTML(label);
		return label;
	}

	def private EObject getIdentifiableElement(EObject o) {
		val result = switch (o) {
			IdentifierRef: o.id
			ParameterizedPropertyAccessExpression: o.property
			LiteralOrComputedPropertyName: o.eContainer
			default: o
		}
		return result;
	}

	def private dispatch doGetLabel(EObject o, EObject ref) {
		val tElem = o.getCorrespondingTypeModelElement;
		return CustomHoverLabelUtil.getLabel(tElem);
	}

	def private dispatch doGetLabel(IdentifierRef ir, EObject ref) {
		getLabelFromTypeSystem(ir, ir);
	}

	def private dispatch doGetLabel(TVariable tv, EObject ref) {
		getLabelFromTypeSystem(tv, ref);
	}

	def private dispatch doGetLabel(ParameterizedPropertyAccessExpression ppae, EObject ref) {
		getLabelFromTypeSystem(ppae, ppae);
	}

	def private dispatch doGetLabel(VariableDeclaration vd, EObject ref) {
		if (vd instanceof ExportedVariableDeclaration)
			_doGetLabel(vd as EObject, ref)
		else
			getLabelFromTypeSystem(vd, ref);
	}

	def private dispatch doGetLabel(PropertyNameValuePair nameValuePair, EObject ref) {
		getLabelFromTypeSystem(nameValuePair, ref);
	}

	def private dispatch doGetLabel(FormalParameter fp, EObject ref) {
		val String optinonalMarker = if (fp.hasInitializerAssignment) "=…" else "";
		getLabelFromTypeSystem(fp, ref) + optinonalMarker;
	}

	def private dispatch doGetLabel(FunctionExpression fe, EObject ref) {
		getLabelFromTypeSystem(fe, ref);
	}

	def private dispatch doGetLabel(LiteralOrComputedPropertyName name, EObject ref) {
		if (name.eContainer instanceof TypableElement) {
			return getLabelFromTypeSystem(name.eContainer as TypableElement, ref);
		}
		return name.name;
	}

	def private getLabelFromTypeSystem(TypableElement o, EObject ref) {
		if (null === o || null === o.eResource) {
			return null;
		}
		val elem = if (ref instanceof TypableElement) ref else o;
		val typeRef = ts.type(elem.newRuleEnvironment, elem);
		return '''«getName(o)»: «typeRef.typeRefAsString»''';
	}

	def private dispatch getName(EObject o) {
		'';
	}

	def private dispatch getName(NamedElement namedElement) {
		''' «namedElement.name»''';
	}

	def private dispatch getName(IdentifiableElement identifiableElement) {
		''' «identifiableElement.name»''';
	}

	def private dispatch getName(TVariable tVariable) {
		''' «if (tVariable.const) "const" else "var"» «tVariable.name»''';
	}

}
