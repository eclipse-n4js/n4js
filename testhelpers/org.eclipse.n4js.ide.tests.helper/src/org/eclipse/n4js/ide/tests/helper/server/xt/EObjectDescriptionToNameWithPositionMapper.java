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
package org.eclipse.n4js.ide.tests.helper.server.xt;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Function;

import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;

/**
 * Maps an {code IEObjectDescription} to a string with name and position (resource, and optionally line number), with
 * special handling for syntax related elements (i.e. Types). This mapper can be used as a function in a Guava transform
 * method, or the static methods can be called directly. The exact mapping is described in
 * {@link #descriptionToNameWithPosition(URI, boolean,IEObjectDescription)}.
 *
 * approach)
 */
public class EObjectDescriptionToNameWithPositionMapper implements Function<IEObjectDescription, String> {

	private static final String SEPARATOR = " - ";
	private final URI currentResourceURI;
	private final boolean withLineNumber;

	/**
	 * @param currentResourceURI
	 *            -- resource's URI in which the expectation is defined, in order to simplify position information, may
	 *            be null
	 */
	public EObjectDescriptionToNameWithPositionMapper(URI currentResourceURI, boolean withLineNumber) {
		this.currentResourceURI = currentResourceURI;
		this.withLineNumber = withLineNumber;
	}

	@Override
	public String apply(IEObjectDescription desc) {
		return EObjectDescriptionToNameWithPositionMapper.descriptionToNameWithPosition(currentResourceURI,
				withLineNumber, desc);
		// desc.getName() + SEPARATOR + desc.getEObjectURI().fragment();
	}

	/**
	 * Returns a string with name and position of the described object. The position is specified by line number (if
	 * possible, otherwise the uri fragment of the proxy is used). If the object is a {@link SyntaxRelatedTElement}, a
	 * "T" is used as a prefix of the line number.
	 *
	 * The following examples shows different mappings, depending on the described object:
	 * <table>
	 * <tr>
	 * <th>Mapping</th>
	 * <th>Described Object</th>
	 * </tr>
	 * <tr>
	 * <td><code>bar - 42</code></td>
	 * <td>Some element "bar", located in same resource on line 42</td>
	 * </tr>
	 * <tr>
	 * <td><code>foo - T23</code></td>
	 * <td>A type "foo" (or other syntax related element, a function is a type) which syntax related element (from which
	 * the type is build) is located in same file on line 23</td>
	 * </tr>
	 * <tr>
	 * <td><code>Infinity - global.n4ts:3</code></td>
	 * <td>An element "Infinity", located in another resource "global.n4ts" on line 3.</td>
	 * </tr>
	 * <tr>
	 * <td><code>decodeURI - global.n4ts:11</code></td>
	 * <td>An element "decodeURI", located in another resource "global.n4ts" on line 11. Although the element may be a
	 * type, there is no syntax related element because "n4ts" directly describes types.</td>
	 * </tr>
	 * </table>
	 *
	 * @param currentURI
	 *            the current resource's URI, if described object is in same resource, resource name is omitted
	 * @param desc
	 *            the object descriptor
	 */
	public static String descriptionToNameWithPosition(URI currentURI, boolean withLineNumber,
			IEObjectDescription desc) {
		String name = desc.getName().toString();

		EObject eobj = desc.getEObjectOrProxy();

		if (eobj == null) {
			return "No EObject or proxy for " + name + " at URI " + desc.getEObjectURI();
		}

		String location = "";

		if (eobj instanceof SyntaxRelatedTElement) {
			EObject syntaxElement = ((SyntaxRelatedTElement) eobj).getAstElement();
			if (syntaxElement != null) {
				location += "T";
				eobj = syntaxElement;
			}
		}

		Resource eobjRes = eobj.eResource();
		URI uri = eobjRes == null ? null : eobjRes.getURI();
		if (uri != currentURI && uri != null) {
			location = uri.lastSegment();
			if (eobj.eIsProxy() || withLineNumber) {
				location += ":";
			}
		}
		if (eobj.eIsProxy()) {
			URI proxyUri = desc.getEObjectURI();
			location += "proxy:" + simpleURIString(proxyUri);
		} else if (withLineNumber) {
			INode node = NodeModelUtils.findActualNodeFor(eobj);
			if (node == null) {
				location += "no node:" + simpleURIString(desc.getEObjectURI());
			} else {
				location += node.getStartLine();
			}
		}

		return name + SEPARATOR + location;
	}

	static String simpleURIString(URI uri) {
		if (uri == null)
			return "!!null!!";
		return uri.lastSegment() + "#" + uri.fragment();
	}

	/**
	 * Returns the name, that is everything before the {@link #SEPARATOR}.
	 */
	public static String getNameFromNameWithPosition(String nameWithPosition) {
		int index = nameWithPosition.indexOf(SEPARATOR);
		if (index > 0) {
			return nameWithPosition.substring(0, index);
		}

		return nameWithPosition;
	}

	/**
	 * Returns the position only, that is everyting after the {@link #SEPARATOR}.
	 */
	public static String getPositionFromNameWithPosition(String nameWithPosition) {
		int index = nameWithPosition.indexOf(SEPARATOR);
		if (index >= 0) {
			return nameWithPosition.substring(index + SEPARATOR.length());
		}

		return "";
	}

}
