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
package org.eclipse.n4js.ui.labeling

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.text.IRegion
import org.eclipse.n4js.jsdoc.JSDoc2HoverSerializer
import org.eclipse.n4js.jsdoc.N4JSDocletParser
import org.eclipse.n4js.jsdoc.dom.Doclet
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.ui.labeling.TypesHoverProvider
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.validation.N4JSElementKeywordProvider
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider
import org.eclipse.xtext.ui.editor.hover.html.XtextBrowserInformationControlInput

import static org.eclipse.n4js.ts.ui.labeling.TypesHoverProvider.composeFirstLine
import static org.eclipse.n4js.utils.UtilN4.sanitizeForHTML

import static extension org.eclipse.n4js.n4JS.N4JSASTUtils.getCorrespondingTypeModelElement
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment

/**
 */
class N4JSHoverProvider extends DefaultEObjectHoverProvider {

	@Inject
	private extension N4JSTypeSystem;

	@Inject
	private extension N4JSElementKeywordProvider;

	@Inject
	private TypesHoverProvider typesHoverProvider;

	@Inject
	private OperationCanceledManager cancelManager;

	@Inject
	private N4JSDocletParser docletParser;

	override protected getFirstLine(EObject o) {
		if (o instanceof LiteralOrComputedPropertyName) {
			return getFirstLine(o.eContainer);
		}
		val keyword = keyword(if (o instanceof IdentifierRef) o.id else o);
		val label = getLabel(o);
		return composeFirstLine(keyword, label);
	}

	override protected String getLabel(EObject o) {
		val label = if (o instanceof IdentifierRef) {
			doGetLabel(o.id, o);
		} else {
			doGetLabel(o, null);
		}
		sanitizeForHTML(label);
	}

	override protected getDocumentation(EObject o) {
		try {
			var String jsdocString = super.getDocumentation(o);
			if (jsdocString === null) {
				return null;
			}
			val Doclet doclet = docletParser.parse(jsdocString);
			val String hoverHTML = JSDoc2HoverSerializer.toJSDocString(doclet);
			return hoverHTML;
		} catch (Exception ex) {
			// Make it robust.
			return "Error generating documentation:  " + ex;
		}
	}

	def private dispatch doGetLabel(EObject o, IdentifierRef identifierRef) {
		val tElem = o.getCorrespondingTypeModelElement;
		return if (null === tElem) super.getLabel(o) else typesHoverProvider.getLabel(tElem);
	}

	def private dispatch doGetLabel(IdentifierRef ir, IdentifierRef identifierRef) {
		getLabelFromTypeSystem(ir, identifierRef);
	}

	def private dispatch doGetLabel(VariableDeclaration vd, IdentifierRef identifierRef) {
		if (vd instanceof ExportedVariableDeclaration)
			_doGetLabel(vd as EObject, identifierRef)
		else
			getLabelFromTypeSystem(vd, identifierRef);
	}

	def private dispatch doGetLabel(PropertyNameValuePair nameValuePair, IdentifierRef identifierRef) {
		getLabelFromTypeSystem(nameValuePair, identifierRef);
	}

	def private dispatch doGetLabel(FormalParameter fp, IdentifierRef identifierRef) {
		val String optinonalMarker = if (fp.hasInitializerAssignment) "=…" else "";
		getLabelFromTypeSystem(fp, identifierRef) + optinonalMarker;
	}

	def private dispatch doGetLabel(FunctionExpression fe, IdentifierRef identifierRef) {
		getLabelFromTypeSystem(fe, identifierRef);
	}

	def private dispatch doGetLabel(LiteralOrComputedPropertyName name, IdentifierRef identifierRef) {
		if (name.eContainer instanceof TypableElement) {
			return getLabelFromTypeSystem(name.eContainer as TypableElement, identifierRef);
		}
		return name.name;
	}

	def private getLabelFromTypeSystem(TypableElement o, IdentifierRef identifierRef) {
		if (null === o || null === o.eResource) {
			return null;
		}
		val elem = if (identifierRef !== null) identifierRef else o;
		val typeRef = elem.newRuleEnvironment.type(elem);
		return '''«getName(o)»: «typeRef.typeRefAsString»''';
	}

	def private dispatch getName(EObject o) {
		'';
	}

	def private dispatch getName(NamedElement namedElement) {
		''' «namedElement.name»''';
	}

	override protected hasHover(EObject o) {
		doHasHover(o);
	}

	def private dispatch doHasHover(EObject o) {
		val tElem = o.getCorrespondingTypeModelElement;
		return if (null === tElem) super.hasHover(o) else typesHoverProvider.hasHover(tElem);
	}

	def private dispatch doHasHover(IdentifierRef identifierRef) {
		true;
	}

	def private dispatch doHasHover(VariableDeclaration vd) {
		true;
	}

	def private dispatch doHasHover(PropertyNameValuePair nameValuePair) {
		true;
	}

	def private dispatch doHasHover(FormalParameter fp) {
		true;
	}

	def private dispatch doHasHover(FunctionExpression fe) {
		true;
	}

	def private dispatch doHasHover(N4TypeDeclaration md) {
		true;
	}

	def private dispatch doHasHover(LiteralOrComputedPropertyName name) {
		return name.eContainer instanceof N4MemberDeclaration
	}

	override protected getHoverInfo(EObject element, IRegion hoverRegion,
		XtextBrowserInformationControlInput previous) {
		try {
			return super.getHoverInfo(element, hoverRegion, previous)
		} catch (Throwable t) {
			if (!cancelManager.isOperationCanceledException(t)) {
				throw Exceptions.sneakyThrow(t);
			}
		}
	}

//
// the following code can be used to show images in hovers
// (but is not used yet because this would also be required in
// TypesHoverProvider, but no access to the images from that class/bundle!)
//
// private String getImageTag(EObject obj) {
// final URL url = getImageURL(obj);
// if (url != null)
// return "<image align='middle' src=\"" + url.toExternalForm() + "\"/>";
// return null;
// }
//
// @Inject
// private ImageFileNameCalculationHelper h;
//
// private URL getImageURL(EObject obj) {
// final String fn = h.getImageFileName(obj);
// if (fn != null) {
// final URL url = FileLocator.find(N4JSActivator.getInstance().getBundle(), new Path("icons/" + fn), null);
// if (url != null) {
// try {
// return FileLocator.toFileURL(url);
// } catch (IOException e) {
// //
// }
// }
// }
// return null;
// }
}
