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
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.core.runtime.FileLocator
import org.eclipse.core.runtime.Path
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.text.IRegion
import org.eclipse.n4js.ide.server.hover.N4JSElementSignatureProvider
import org.eclipse.n4js.jsdoc.JSDoc2HoverSerializer
import org.eclipse.n4js.jsdoc.N4JSDocletParser
import org.eclipse.n4js.jsdoc.dom.Doclet
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.ui.labeling.TypesHoverProvider
import org.eclipse.n4js.ui.internal.N4JSActivator
import org.eclipse.n4js.ui.labeling.helper.ImageFileNameCalculationHelper
import org.eclipse.n4js.validation.N4JSElementKeywordProvider
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider
import org.eclipse.xtext.ui.editor.hover.html.XtextBrowserInformationControlInput

import static org.eclipse.n4js.ts.ui.labeling.TypesHoverProvider.composeFirstLine
import static org.eclipse.n4js.utils.UtilN4.sanitizeForHTML

import static extension org.eclipse.n4js.n4JS.N4JSASTUtils.getCorrespondingTypeModelElement

/**
 */
class N4JSHoverProvider extends DefaultEObjectHoverProvider {

	@Inject
	private extension N4JSElementKeywordProvider;

	@Inject
	private N4JSElementSignatureProvider signatureProvider;

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
		val id = getIdentifiableElement(o);
		val image = getImageURL(id);
		val keyword = keyword(id);
		val label = getLabel(o);
		return composeFirstLine(image, keyword, label);
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

	override protected String getLabel(EObject o) {
		val label = signatureProvider.get(o);
		sanitizeForHTML(label);
	}

	override protected getDocumentation(EObject o) {
		try {
			val id = getIdentifiableElement(o);
			var String jsdocString = super.getDocumentation(id);
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



	override protected hasHover(EObject o) {
		doHasHover(o);
	}

	def private dispatch doHasHover(EObject o) {
		val tElem = o.getCorrespondingTypeModelElement;
		return if (null === tElem) super.hasHover(o) else typesHoverProvider.hasHover(tElem);
	}

	def private dispatch doHasHover(ParameterizedPropertyAccessExpression ppae) {
		true;
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


	@Inject
	private ImageFileNameCalculationHelper h;

	def private URL getImageURL(EObject obj) {

		val String fn = h.getImageFileName(obj);
		if (fn !== null) {
			val lastDotIndex = fn.lastIndexOf(".");
			val name = fn.substring(0, lastDotIndex);
			val extn = fn.substring(lastDotIndex);
			val fnHighRes = name + "@2x" + extn;
			
			val bundle = N4JSActivator.getInstance().getBundle()
			val folder = new Path("icons/");

			// try to load high resolution image first
			val pathHighRes = folder.append(fnHighRes);
			val URL urlHighRes = FileLocator.find(bundle, pathHighRes, null);
			if (urlHighRes !== null) {
				try {
					val file = FileLocator.toFileURL(urlHighRes);
					if (Files.exists(Paths.get(file.toURI))) {
						return file;
					}
				} catch (Exception e) {
					//
				}
			}

			// fallback image
			val path = folder.append(fn);
			val URL url = FileLocator.find(bundle, path, null);
			if (url !== null) {
				try {
					return FileLocator.toFileURL(url);
				} catch (IOException e) {
					//
				}
			}
		}
		return null;
	}
}
