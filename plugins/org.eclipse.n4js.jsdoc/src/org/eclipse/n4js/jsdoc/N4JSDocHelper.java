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
package org.eclipse.n4js.jsdoc;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.DomFactory;
import org.eclipse.xtext.documentation.IEObjectDocumentationProviderExtension;
import org.eclipse.xtext.nodemodel.INode;

import com.google.inject.Inject;

/**
 * Main front-end for parsing N4JSDoc comments.
 */
public class N4JSDocHelper {

	@Inject
	private IEObjectDocumentationProviderExtension documentationProviderExt;

	/**
	 * Get JSDoc comment for the given element. The element may be an AST node such as <code>N4MethodDeclaration</code>
	 * or a type model element such as <code>TMethod</code>. In the latter case, this method will follow the link to the
	 * AST which may cause a load of the N4JS resource if it is not fully loaded (i.e. if only the TModule was loaded
	 * from the Xtext index).
	 * <p>
	 * Thus, <b>this method may have a side effect on the containing resource of the given element</b>. If that is not
	 * desired, use method {@link #getDocSafely(ResourceSet, EObject)} instead.
	 */
	public String getDoc(EObject element) {
		if (element == null)
			throw new IllegalArgumentException("element must not be null");
		if (element.eIsProxy()) {
			return null;
			// throw new IllegalArgumentException("element must not be proxy: " + element.toString());
		}

		final List<INode> docNodes = documentationProviderExt.getDocumentationNodes(element);
		if (!docNodes.isEmpty()) {
			final StringBuilder sb = new StringBuilder(docNodes.get(0).getText());
			for (int idx = 1; idx < docNodes.size(); idx++) {
				sb.append("\n").append(docNodes.get(idx).getText());
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * Same as {@link #getDoc(EObject)}, but will never change the containing resource of the given element.
	 * <p>
	 * If the containing resource of the given element is not fully loaded (i.e. AST not loaded yet because only the
	 * TModule was loaded from the Xtext index), then the given resource set will be used to load the resource, then the
	 * corresponding EObject for element will be searched in that temporary resource, and the documentation will be
	 * retrieved from there.
	 */
	public String getDocSafely(ResourceSet resourceSetForDocRetrieval, EObject element) {
		if (resourceSetForDocRetrieval == null)
			throw new IllegalArgumentException("resourceSetForDocRetrieval may not be null");
		if (element == null || element.eIsProxy())
			throw new IllegalArgumentException("element may not be null or a proxy");
		final Resource res = element.eResource();
		final ResourceSet resSet = res != null ? res.getResourceSet() : null;
		if (resSet == null || res == null)
			throw new IllegalArgumentException("element must be contained in a resource set");
		if (resourceSetForDocRetrieval != resSet) {
			final Resource resSafe = resourceSetForDocRetrieval.getResource(res.getURI(), true);
			final EObject elementSafe = resSafe.getEObject(res.getURIFragment(element));
			return elementSafe != null ? getDoc(elementSafe) : null;
		} else {
			return getDoc(element);
		}
	}

	/**
	 * Same as {@link #getDoc(EObject)} but will parse the documentation and return a {@link Doclet}.
	 * <p>
	 * <b>This method may have a side effect on the containing resource of the given element</b>. If that is not
	 * desired, use method {@link #getDocletSafely(ResourceSet, EObject)} instead (for more details, see
	 * {@link #getDoc(EObject)}).
	 */
	public Doclet getDoclet(EObject element) {
		return getDoclet(getDoc(element));
	}

	/**
	 * Same as {@link #getDocSafely(ResourceSet, EObject)} but will parse the documentation and return a {@link Doclet}.
	 */
	public Doclet getDocletSafely(ResourceSet resourceSetForDocRetrieval, EObject element) {
		return getDoclet(getDocSafely(resourceSetForDocRetrieval, element));
	}

	/**
	 * Will parse the given string to a {@link Doclet}. The string should include the full JSdoc comment, including the
	 * leading <code>&#47;**</code> and trailing <code>*&#47;</code> character sequences.
	 */
	public Doclet getDoclet(String docStr) {
		if (docStr == null || docStr.trim().length() == 0) {
			return DomFactory.eINSTANCE.createDoclet();
		}
		final DocletParser docletParser = new N4JSDocletParser();
		return docletParser.parse(docStr);
	}
}
