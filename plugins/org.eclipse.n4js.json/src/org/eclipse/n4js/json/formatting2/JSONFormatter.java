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
package org.eclipse.n4js.json.formatting2;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.xtext.formatting2.AbstractFormatter2;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * A simple formatter for JSON files.
 * 
 * Generally, puts name-value-pairs of objects and array elements on a separate line.
 */
public class JSONFormatter extends AbstractFormatter2 {

	@Override
	public void format(Object al, IFormattableDocument document) {
		if (al instanceof XtextResource) {
			super._format((XtextResource) al, document);
			return;
		} else if (al instanceof JSONArray) {
			format((JSONArray) al, document);
			return;
		} else if (al instanceof JSONObject) {
			format((JSONObject) al, document);
			return;
		} else if (al instanceof JSONDocument) {
			format((JSONDocument) al, document);
			return;
		} else if (al instanceof NameValuePair) {
			format((NameValuePair) al, document);
			return;
		} else if (al instanceof EObject) {
			super._format((EObject) al, document);
			return;
		} else if (al == null) {
			super._format((Void) null, document);
			return;
		} else {
			super._format(al, document);
			return;
		}
	}

	/**  */
	public void format(JSONDocument jSONDocument, IFormattableDocument document) {
		// make sure all document elements are formatted
		document.format(jSONDocument.getContent());
		for (ISemanticRegion sr : textRegionExtensions.allSemanticRegions(jSONDocument)) {
			document.append(sr, f -> f.indent());
		}
	}

	/** Put both brackets and every element of a JSONArray on a separate line. */
	public void format(JSONArray al, IFormattableDocument document) {
		// avoid comma-only lines
		configureCommas(al, document);

		Pair<ISemanticRegion, ISemanticRegion> bracketPair = textRegionExtensions.regionFor(al).keywordPairs("[", "]")
				.get(0);

		// if bracePair can be determined
		if (bracketPair != null) {
			// indent array elements
			document.interior(bracketPair, f -> f.indent());

			// format empty arrays to be a bracket pair without space (nor newline) in between
			if (al.getElements().isEmpty()) {
				document.append(bracketPair.getKey(), f -> f.noSpace());
				document.prepend(bracketPair.getValue(), f -> f.noSpace());
				return;
			}

			// put closing bracket on a separate line
			document.prepend(bracketPair.getValue(), f -> f.newLine());
		}

		// put every array element on a separate line
		for (JSONValue val : al.getElements()) {
			document.prepend(val, f -> f.newLine());
		}
		// recursively format each element
		for (JSONValue val : al.getElements()) {
			document.format(val);
		}
	}

	/** On the direct level of an semantic Object enforce commas to ", " with autoWrap option. */
	private void configureCommas(EObject semEObject, IFormattableDocument document) {
		for (ISemanticRegion sr : textRegionExtensions.regionFor(semEObject).keywords(",")) {
			document.prepend(sr, f -> f.noSpace());
			document.append(sr, f -> {
				f.oneSpace();
				f.autowrap();
			});
		}
	}

	/** Put both curly braces and every name-value-pair of a JSONObject on a separate line. */
	public void format(JSONObject ol, IFormattableDocument document) {
		configureCommas(ol, document);

		Pair<ISemanticRegion, ISemanticRegion> bracePair = textRegionExtensions.regionFor(ol).keywordPairs("{", "}")
				.get(0);

		// if bracePair can be determined
		if (bracePair != null) {
			document.interior(bracePair, f -> f.indent());

			// format empty objects to be a brace pair without space (nor newline) in between
			if (ol.getNameValuePairs().isEmpty()) {
				document.append(bracePair.getKey(), f -> f.noSpace());
				document.prepend(bracePair.getValue(), f -> f.noSpace());
				return;
			}

			document.prepend(bracePair.getValue(), f -> f.newLine()); // format WS in front of closing brace
			for (NameValuePair nvp : ol.getNameValuePairs()) {
				document.prepend(nvp, f -> f.newLine());
			}

			if (bracePair.getKey() != null
					&& bracePair.getKey().getNextSemanticRegion() == bracePair.getValue()) {
				// empty multiline, trigger formatting:
				document.append(bracePair.getKey(), f -> f.newLine());
			}
		}

		// recursively format each name-value pair
		for (NameValuePair nvp : ol.getNameValuePairs()) {
			document.format(nvp);
		}
	}

	/***/
	public void format(NameValuePair nameValuePair, IFormattableDocument document) {
		ISemanticRegion colon = textRegionExtensions.regionFor(nameValuePair).keyword(":");
		JSONValue value = nameValuePair.getValue();

		document.prepend(colon, f -> f.noSpace());
		document.append(colon, f -> f.oneSpace());

		document.format(value);
	}
}
