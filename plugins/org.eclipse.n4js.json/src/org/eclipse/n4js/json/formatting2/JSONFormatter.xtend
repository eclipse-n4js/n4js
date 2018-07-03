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
package org.eclipse.n4js.json.formatting2

import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.JSON.NameValuePair
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument

/**
 * A simple formatter for JSON files.
 * 
 * Generally, puts name-value-pairs of objects and array elements 
 * on a separate line.
 */
class JSONFormatter extends AbstractFormatter2 {

	/**  */
	def dispatch void format(JSONDocument jSONDocument, extension IFormattableDocument document) {
		// make sure all document elements are formatted
		jSONDocument.getContent.format;
		jSONDocument.allSemanticRegions.forEach[it.append[indent]]
	}

	/** Put both brackets and every element of a JSONArray on a separate line. */
	def dispatch void format(JSONArray al, extension IFormattableDocument document) {
		// avoid comma-only lines
		al.configureCommas(document);

		val bracketPair = al.regionFor.keywordPairs("[", "]").head;
		
		// if bracePair can be determined
		if (bracketPair !== null) {
			// indent array elements
			bracketPair.interior[indent];
	
			// format empty arrays to be a bracket pair without space (nor newline) in between
			if (al.elements.empty) {
				bracketPair.key.append[noSpace]
				bracketPair.value.prepend[noSpace]
				return;
			}
			
			// put closing bracket on a separate line
			bracketPair.value.prepend[newLine];
		}

		// put every array element on a separate line
		al.elements.forEach[it, num|prepend[newLine]];
		// recursively format each element
		al.elements.forEach[it|format(it)]

		
	}

	/** On the direct level of an semantic Object enforce commas to ", " with autoWrap option. */
	private def void configureCommas(EObject semEObject, extension IFormattableDocument document) {
		semEObject.regionFor.keywords(",").forEach [
			prepend[noSpace];
			append[oneSpace; autowrap];
		];
	}

	/** Put both curly braces and every name-value-pair of a JSONObject on a separate line. */
	def dispatch void format(JSONObject ol, extension IFormattableDocument document) {
		ol.configureCommas(document);

		val bracePair = ol.regionFor.keywordPairs("{", "}").head;
		
		// if bracePair can be determined
		if (bracePair !== null) {
			bracePair?.interior[indent];
			
			// format empty objects to be a brace pair without space (nor newline) in between
			if (ol.nameValuePairs.empty) {
				bracePair.key.append[noSpace]
				bracePair.value.prepend[noSpace]
				return;
			}
	
			bracePair?.value.prepend[newLine]; // format WS in front of closing brace
			ol.nameValuePairs.forEach[it, num|prepend[newLine]];
	
			if (bracePair?.key?.nextSemanticRegion == bracePair?.value) {
				// empty multiline, trigger formatting:
				bracePair.key.append[newLine];
			}
		}

		// recursively format each name-value pair
		ol.nameValuePairs.forEach[format(it)];
	}

	def dispatch void format(NameValuePair nameValuePair, extension IFormattableDocument document) {
		val colon = nameValuePair.regionFor.keyword(":");
		val value = nameValuePair.value;

		colon.prepend[noSpace];
		colon.append[oneSpace];

		format(value)
	}
}
