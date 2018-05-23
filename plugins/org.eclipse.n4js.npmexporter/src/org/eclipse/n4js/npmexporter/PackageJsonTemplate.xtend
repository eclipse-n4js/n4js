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
package org.eclipse.n4js.npmexporter

import com.fasterxml.jackson.core.JsonGenerationException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.google.common.collect.Iterables
import org.eclipse.n4js.utils.JsonPrettyPrinterFactory
import java.io.IOException

/**
 */
class PackageJsonTemplate {


	/** Writes data to a simple string. */
	def String generateTemplate(PackageJsonData data)throws IOException, JsonGenerationException, JsonMappingException {

		val om = new ObjectMapper(new JsonPrettyPrinterFactory());

		val jsonString = om.writeValueAsString(data)

		return jsonString
	}

	/**
	 * Merges all additional things from jsonSecondary into jsonPrimary. If a field with a non object-literal value
	 * exists in jsonPrimary, the field from jsconSecondary will be rejected. Messages of rejections can be retrieved by
	 * providing a rejectionMessageConsumer.
	 * Merges object-literal values recursively.
	 * @param rejectionMessageConsumer consumes messages, can be null.
	 */
	def static String merge(String jsonPrimary, String jsonSecondary, (String)=>void rejectionMessageConsumer) {
		val om = new ObjectMapper(new JsonPrettyPrinterFactory());
		try {
			val primNode = om.readTree(jsonPrimary)
			val secondNode = om.readTree( jsonSecondary );

			val result =  merge( primNode, secondNode, emptyList , rejectionMessageConsumer);

			return om.writeValueAsString( result );

		} catch (IOException exc) {
			throw Exceptions.sneakyThrow( exc );
		}
	}


	/** Merges updateNode into mainNode - only if the field was not already given in mainNode.
	 * keeps track of the property-path in the object literal to issue user-strings if rejecting properties.
	 * */
	def static JsonNode merge(JsonNode mainNode, JsonNode updateNode, Iterable<String> objecPath, (String)=>void messageConsumer) {

		updateNode.fieldNames.forEach[ fieldName  |
			val JsonNode jsonNode = mainNode.get(fieldName);
			// if field exists and is an embedded object
			if (jsonNode !== null && jsonNode.isObject()) {

				merge(jsonNode, updateNode.get(fieldName),
					Iterables.concat(objecPath, #[fieldName] ),
					messageConsumer
				);
			}
			else {
				if (mainNode instanceof ObjectNode) {
					// Don't Overwrite field
					val JsonNode value = updateNode.get(fieldName);
					if( jsonNode === null ) {
						 // add if nothing was given in mainNode.
						 mainNode.set(fieldName, value);
					} else {
						// rejected entry.
						if( messageConsumer !== null ) {
							// propagate the message.
							val finalPath = objecPath + #[fieldName];
							messageConsumer.apply( "rejected existing property "+ finalPath.join(".")+", rejected value: "+value );
						}
					}
				}
			}
		];
		return mainNode;
	}



}
