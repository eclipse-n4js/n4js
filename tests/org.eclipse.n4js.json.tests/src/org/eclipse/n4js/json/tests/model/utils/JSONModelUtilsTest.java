/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.tests.model.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.json.JSONGlobals;
import org.eclipse.n4js.json.JSONInjectorProvider;
import org.eclipse.n4js.json.JSONParseHelper;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Test for {@link JSONModelUtils}.
 */
@RunWith(XtextRunner.class)
@InjectWith(JSONInjectorProvider.class)
@SuppressWarnings("javadoc")
public class JSONModelUtilsTest extends Assert {

	@Inject
	JSONParseHelper jsonParserHelper;

	@Test
	public void testMergeTrivial() throws Exception {
		assertCorrectMerge(
				"""
						{ "prop1": "value1", "prop2": 2, "prop3": 3 }""",
				"""
						{ "prop2": "value2", "prop4": [ "value3" ] }""",
				"""
						{ "prop1": "value1", "prop2": "value2", "prop3": 3, "prop4": [ "value3" ] }""");
	}

	@Test
	public void testMergeNested() throws Exception {
		assertCorrectMerge(
				"""
							{
								"propA": {
									"prop": {
										"prop1": "value1", "prop2": 42
									}
								},
								"propB": {
									"prop": "value"
								},
								"propC": [ "value" ]
							}
						""",
				"""
							{
								"propA": {
									"prop": {
										"prop2": "value2", "prop3": [ "value3" ]
									}
								},
								"propB": [ 42 ],
								"propC": {
									"prop": "value"
								}
							}
						""",
				"""
							{
								"propA": {
									"prop": {
										"prop1": "value1", "prop2": "value2", "prop3": [ "value3" ]
									}
								},
								"propB": [ 42 ],
								"propC": {
									"prop": "value"
								}
							}
						""");
	}

	private void assertCorrectMerge(CharSequence target, CharSequence source, CharSequence expected) throws Exception {
		JSONObject targetRoot = (JSONObject) jsonParserHelper.parse(target).getContent();
		JSONObject sourceRoot = (JSONObject) jsonParserHelper.parse(source).getContent();
		JSONObject expectedRoot = (JSONObject) jsonParserHelper.parse(expected).getContent();

		JSONObject mergedRoot = EcoreUtil.copy(targetRoot);
		JSONModelUtils.merge(mergedRoot, sourceRoot, false, true);
		String mergedStr = serializeJSON(mergedRoot);
		String expectedStr = serializeJSON(expectedRoot);

		if (!Objects.equals(expectedStr, mergedStr)) {
			System.out.println("==== EXPECTED:");
			System.out.println(expectedStr);
			System.out.println("==== ACTUAL:");
			System.out.println(mergedStr);
		}
		assertEquals(expectedStr, mergedStr);
	}

	private String serializeJSON(JSONValue value) {
		JSONDocument doc = JSONFactory.eINSTANCE.createJSONDocument();
		doc.setContent(value);
		return serializeJSON(doc);
	}

	// FIXME use the helper method instead of the following!!!!
	private String serializeJSON(JSONDocument document) {
		ISerializer jsonSerializer = N4LanguageUtils.getServiceForContext(JSONGlobals.FILE_EXTENSION, ISerializer.class)
				.get();
		ResourceSet resourceSet = N4LanguageUtils.getServiceForContext(JSONGlobals.FILE_EXTENSION, ResourceSet.class)
				.get();
		// Use temporary Resource as AbstractFormatter2 implementations can only format
		// semantic elements that are contained in a Resource.
		Resource temporaryResource = resourceSet.createResource(URI.createURI("__synthetic.json"));
		temporaryResource.getContents().add(document);
		// create string writer as serialization output
		StringWriter writer = new StringWriter();
		// enable formatting as serialization option
		SaveOptions serializerOptions = SaveOptions.newBuilder().format().getOptions();
		try {
			jsonSerializer.serialize(document, writer, serializerOptions);
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException("Failed to serialize JSONDocument " + document, e);
		}
	}
}
