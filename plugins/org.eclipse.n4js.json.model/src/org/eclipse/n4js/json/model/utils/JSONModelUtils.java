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
package org.eclipse.n4js.json.model.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.n4js.utils.languages.N4LanguageUtils.ParseResult;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.ISerializer;

import com.google.common.base.Strings;

/**
 * Utility methods for more convenient access to elements of the {@link JSONPackage} model.
 */
public class JSONModelUtils {

	/** The JSON file extension. */
	public static final String FILE_EXTENSION = "json";

	/**
	 * If the given JSON value is a {@link JSONBooleanLiteral} with a value of <code>true</code>, returns
	 * <code>true</code>, otherwise <code>false</code>.
	 */
	public static boolean asBooleanOrFalse(JSONValue jsonValue) {
		return jsonValue instanceof JSONBooleanLiteral ? ((JSONBooleanLiteral) jsonValue).isBooleanValue() : false;
	}

	/**
	 * If given JSON value is a {@link JSONStringLiteral}, returns its value (possibly the empty string), otherwise
	 * <code>null</code>.
	 */
	public static String asStringOrNull(JSONValue jsonValue) {
		return jsonValue instanceof JSONStringLiteral ? ((JSONStringLiteral) jsonValue).getValue() : null;
	}

	/**
	 * If given JSON value is a {@link JSONArray}, returns its elements converted to strings with
	 * {@link #asStringOrNull(JSONValue)}; otherwise an empty list is returned.
	 */
	public static List<String> asStringsInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(JSONModelUtils::asStringOrNull)
				.filter(str -> !Strings.isNullOrEmpty(str))
				.collect(Collectors.toList());
	}

	/**
	 * If given JSON value is a {@link JSONStringLiteral} with a non-empty string as value, returns its value, otherwise
	 * <code>null</code>.
	 */
	public static String asNonEmptyStringOrNull(JSONValue jsonValue) {
		final String strValue = jsonValue instanceof JSONStringLiteral ? ((JSONStringLiteral) jsonValue).getValue()
				: null;
		if (Strings.isNullOrEmpty(strValue)) {
			return null;
		}
		return strValue;
	}

	/**
	 * If given JSON value is a {@link JSONArray}, returns its elements converted to strings with
	 * {@link #asNonEmptyStringOrNull(JSONValue)}; otherwise an empty list is returned.
	 */
	public static List<String> asNonEmptyStringsInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(JSONModelUtils::asNonEmptyStringOrNull)
				.filter(str -> !Strings.isNullOrEmpty(str))
				.collect(Collectors.toList());
	}

	/**
	 * If given JSON value is a {@link JSONArray}, returns its elements, otherwise an empty list.
	 */
	public static List<JSONValue> asArrayElementsOrEmpty(JSONValue jsonValue) {
		return jsonValue instanceof JSONArray ? ((JSONArray) jsonValue).getElements() : Collections.emptyList();
	}

	/**
	 * If given JSON value is a {@link JSONObject}, returns its name/value pairs, otherwise an empty list.
	 */
	public static List<NameValuePair> asNameValuePairsOrEmpty(JSONValue jsonValue) {
		return jsonValue instanceof JSONObject ? ((JSONObject) jsonValue).getNameValuePairs() : Collections.emptyList();
	}

	/**
	 * Returns the {@link JSONDocument} instance that is contained in the given {@link Resource}.
	 *
	 * @throws IllegalArgumentException
	 *             if the given resource is not a valid JSON resource.
	 */
	public static JSONDocument getDocument(Resource resource) {
		if (resource.getContents().isEmpty()) {
			throw new IllegalArgumentException(
					"The given empty resource is not a valid JSON resource (URI=" + resource.getURI() + ").");
		}

		final EObject firstSlot = resource.getContents().get(0);

		if (!(firstSlot instanceof JSONDocument)) {
			throw new IllegalArgumentException(
					"The given resource is not a valid JSON resource (URI=" + resource.getURI() + ").");
		}

		return (JSONDocument) firstSlot;
	}

	/**
	 * Like {@link #getContent(JSONDocument, Class)}, but accepts a {@link Resource}.
	 *
	 * See {@link #getDocument(Resource)}, {@link #getContent(JSONDocument, Class)}.
	 */
	public static <T extends JSONValue> T getContent(Resource resource, Class<T> expectedType) {
		return getContent(getDocument(resource), expectedType);
	}

	/**
	 * If the given document is non-null, its content is non-null and of the given expected type, then this method
	 * returns the content; otherwise <code>null</code>.
	 */
	public static <T extends JSONValue> T getContent(JSONDocument document, Class<T> expectedType) {
		JSONValue content = document.getContent();
		if (content != null && expectedType.isInstance(content)) {
			@SuppressWarnings("unchecked")
			T contentCasted = (T) content;
			return contentCasted;
		}
		return null;
	}

	/**
	 * Same as {@link #getPath(JSONObject, List)}, but accepts a {@link JSONDocument} with a {@link JSONObject} as
	 * content.
	 */
	public static Optional<JSONValue> getPath(JSONDocument document, List<String> path) {
		final JSONValue content = document.getContent();
		if (content instanceof JSONObject) {
			return getPath((JSONObject) content, path);
		}
		return Optional.empty();
	}

	/**
	 * Returns the {@link JSONValue} that can be found under the given property path starting from the given
	 * {@code object}.
	 *
	 * Returns an absent {@link Optional} in case the path cannot be resolved (e.g. non-existing properties or values of
	 * non-object type).
	 *
	 * @throws JSONPropertyPathException
	 *             if the given path cannot be resolve on {@code object}.
	 */
	public static Optional<JSONValue> getPath(JSONObject object, List<String> path) {
		if (path.isEmpty()) {
			return Optional.empty();
		}
		final String currentProperty = path.get(0);
		final JSONValue propertyValue = getProperty(object, currentProperty).orElse(null);

		// check that the current property can be resolved
		if (propertyValue == null) {
			return Optional.empty();
		}

		// in case of the last segment
		if (path.size() == 1) {
			// simply return the value
			return Optional.ofNullable(propertyValue);
		}

		// otherwise, check that the property resolves to an JSONObject
		if (!(propertyValue instanceof JSONObject)) {
			return Optional.empty();
		}

		final JSONObject targetObject = (JSONObject) propertyValue;
		// recursively get sub-path of path on targetObject
		return getPath(targetObject, path.subList(1, path.size()));
	}

	/**
	 * Sets the string {@code value} for the given property (dot-delimited) {@code path} starting from {@code objec†}.
	 */
	public static JSONStringLiteral setPath(JSONObject object, String path, String value) {
		return setPath(object, Arrays.asList(path.split("\\.")), createStringLiteral(value));
	}

	/**
	 * Sets the {@code value} for the given property (dot-delimited) {@code path} starting from {@code objec†}.
	 */
	public static <V extends JSONValue> V setPath(JSONObject object, String path, V value) {
		return setPath(object, Arrays.asList(path.split("\\.")), value);
	}

	/**
	 * Sets the {@code value} for the given property {@code path} starting from {@code objec†}.
	 */
	public static <V extends JSONValue> V setPath(JSONObject object, List<String> path, V value) {
		try {
			return setPath(object, path, path, value);
		} catch (JSONPropertyPathException e) {
			throw new JSONPropertyPathException("Failed to resolve JSON property path " + path, e);
		}
	}

	private static <V extends JSONValue> V setPath(JSONObject object, List<String> currentPath,
			List<String> fullPath, V value) {
		if (currentPath.size() == 0) {
			return null;
		}

		final String currentProperty = currentPath.get(0);
		final int pathLength = currentPath.size();

		// if we are at the end of the path
		if (pathLength == 1) {
			// set the value on 'object'
			setProperty(object, currentProperty, value);
			return value;
		}

		// obtain NameValuePair that matches the first segment in propertyPath
		final Optional<NameValuePair> pair = object.getNameValuePairs().stream()
				.filter(p -> p.getName().equals(currentProperty)).findAny();

		// if pair already exists
		if (pair.isPresent()) {
			final JSONValue pathValue = pair.get().getValue();

			// check whether the value is an object
			if (!(pathValue instanceof JSONObject)) {
				// if not, the property path is invalid
				throw new JSONPropertyPathException("Cannot resolve JSON property path further then " +
						fullPath.subList(0, fullPath.size() - pathLength).stream()
								.collect(Collectors.joining("."))
						+ ". " + pathValue + " is not a JSONObject.", null);
			}
			// setPath recursively on the (object) value of the existing pair
			return setPath((JSONObject) pathValue, currentPath.subList(1, pathLength), fullPath, value);
		} else {
			// add new object name-value-pair for current property
			final JSONObject nextObject = addProperty(object, currentProperty,
					JSONFactory.eINSTANCE.createJSONObject());
			return setPath(nextObject, currentPath.subList(1, pathLength), fullPath, value);
		}
	}

	/**
	 * Same as {@link #getProperty(JSONObject, String)}, but accepts a {@link JSONDocument} with a {@link JSONObject} as
	 * content.
	 */
	public static Optional<JSONValue> getProperty(JSONDocument document, String property) {
		final JSONValue content = document.getContent();
		if (content instanceof JSONObject) {
			return getProperty((JSONObject) content, property);
		}
		return Optional.empty();
	}

	/**
	 * Same as {@link #getProperty(JSONObject, String)}, but invokes {@link #asNonEmptyStringOrNull(JSONValue)} on the
	 * property value before returning it.
	 */
	public static String getPropertyAsStringOrNull(JSONObject object, String property) {
		return asStringOrNull(JSONModelUtils.getProperty(object, property).orElse(null));
	}

	/**
	 * Returns the value of the given {@code property} of {@code object}, or an absent optional if no value has been set
	 * for the given {@code property}.
	 */
	public static Optional<JSONValue> getProperty(JSONObject object, String property) {
		return object.getNameValuePairs().stream()
				.filter(pair -> pair.getName().equals(property))
				.findFirst()
				.map(pair -> pair.getValue());
	}

	/**
	 * Adds a new {@link NameValuePair} to the {@code object}, with given {@code name} and {@code value}.
	 *
	 * Does not check {@code object} for duplicate {@link NameValuePair} with the same name.
	 *
	 * @returns The newly set value.
	 */
	public static <V extends JSONValue> V addProperty(JSONObject object, String name, V value) {
		final NameValuePair nameValuePair = JSONFactory.eINSTANCE.createNameValuePair();
		nameValuePair.setName(name);
		nameValuePair.setValue(value);

		object.getNameValuePairs().add(nameValuePair);

		return value;
	}

	/**
	 * Sets property {@code name} to {@code value}.
	 *
	 * Looks for a name-value-pair in {@link JSONObject#getNameValuePairs()} with the given {@code name} and replaces
	 * its value.
	 *
	 * Adds a new name-value-pair if no such existing pair can be found.
	 *
	 * @returns The newly set value.
	 */
	public static <V extends JSONValue> V setProperty(JSONObject object, String name, V value) {
		// find existing pair
		final Optional<NameValuePair> existingPair = object.getNameValuePairs().stream()
				.filter(pair -> pair.getName().equals(name))
				.findAny();

		if (existingPair.isPresent()) {
			// change existing pair value
			existingPair.get().setValue(value);
		} else {
			// add new pair
			addProperty(object, name, value);
		}
		return value;
	}

	/**
	 * Sets property {@code name} to a JSON representation of string {@code value}.
	 *
	 * See {@link #addProperty(JSONObject, String, JSONValue)} and {@link #createStringLiteral(String)}
	 */
	public static JSONStringLiteral addProperty(JSONObject object, String name, String value) {
		return addProperty(object, name, createStringLiteral(value));
	}

	/**
	 * Removes all {@link NameValuePair}s with the given name.
	 *
	 * @returns true iff any {@code NameValuePair}s were removed.
	 */
	public static boolean removeProperty(JSONObject object, String name) {
		return object.getNameValuePairs().removeIf(pair -> name.equals(pair.getName()));
	}

	/**
	 * Sets property {@code name} to a JSON representation of string {@code value}.
	 *
	 * See {@link #setProperty(JSONObject, String, JSONValue)} and {@link #createStringLiteral(String)}
	 */
	public static JSONStringLiteral setProperty(JSONObject object, String name, String value) {
		return setProperty(object, name, createStringLiteral(value));
	}

	/**
	 * Returns a map relating property names to the corresponding {@link NameValuePair}. Duplicate name-value pairs with
	 * the same name will be ignored.
	 *
	 * @param useLinked
	 *            iff true, a {@link LinkedHashMap} will be used instead of a simple one.
	 */
	public static Map<String, NameValuePair> getPropertiesAsMap(JSONObject object, boolean useLinked) {
		return object.getNameValuePairs().stream().collect(Collectors.toMap(
				pair -> pair.getName(),
				pair -> pair,
				(pair1, pair2) -> pair1,
				() -> useLinked ? new LinkedHashMap<>() : new HashMap<>()));
	}

	/**
	 * Like {@link #merge(JSONObject, JSONObject, boolean, boolean)}, but for {@link JSONDocument}.
	 */
	public static void merge(JSONDocument target, JSONDocument source, boolean copy, boolean recursive) {
		JSONValue targetContent = target.getContent();
		JSONValue sourceContent = source.getContent();
		if (sourceContent instanceof JSONObject && targetContent instanceof JSONObject) {
			merge((JSONObject) targetContent, (JSONObject) sourceContent, copy, recursive);
		} else {
			target.setContent(copy ? EcoreUtil.copy(sourceContent) : sourceContent);
		}
	}

	/**
	 * Moves or copies all {@link NameValuePair}s from object 'source' to object 'target', replacing any
	 * {@code NameValuePair}s of same name present in 'target'. The order of properties is preserved.
	 *
	 * @param target
	 *            target object; will be changed in place.
	 * @param source
	 *            source object; won't be changed iff 'copy' is set to <code>true</code>.
	 * @param copy
	 *            tells if {@link NameValuePair}s should be copied over, instead of being moved.
	 * @param recursive
	 *            tells if a recursive merge is to be performed in case an object value in 'target' is overwritten by an
	 *            object value in 'source' (i.e. in case of nested objects on both sides).
	 */
	public static void merge(JSONObject target, JSONObject source, boolean copy, boolean recursive) {
		final Map<String, NameValuePair> targetPairsPerName = JSONModelUtils.getPropertiesAsMap(target, true);
		for (NameValuePair sourcePair : source.getNameValuePairs()) {
			final String sourcePairName = sourcePair.getName();
			final JSONValue sourcePairValue = sourcePair.getValue();
			if (recursive && sourcePairValue instanceof JSONObject) {
				final NameValuePair targetPair = targetPairsPerName.get(sourcePairName);
				final JSONValue targetPairValue = targetPair != null ? targetPair.getValue() : null;
				if (targetPairValue instanceof JSONObject) {
					merge((JSONObject) targetPairValue, (JSONObject) sourcePairValue, copy, recursive);
					continue;
				}
			}
			targetPairsPerName.put(sourcePairName, copy ? EcoreUtil.copy(sourcePair) : sourcePair);
		}
		final List<NameValuePair> targetList = target.getNameValuePairs();
		targetList.clear();
		targetList.addAll(targetPairsPerName.values());
	}

	/**
	 * Creates a new {@link JSONBooleanLiteral} with the given boolean {@code value}.
	 */
	public static JSONBooleanLiteral createBooleanLiteral(boolean value) {
		final JSONBooleanLiteral literal = JSONFactory.eINSTANCE.createJSONBooleanLiteral();
		literal.setBooleanValue(value);
		return literal;
	}

	/**
	 * Creates a new {@link JSONStringLiteral} with the given string {@code value}.
	 */
	public static JSONStringLiteral createStringLiteral(String value) {
		final JSONStringLiteral literal = JSONFactory.eINSTANCE.createJSONStringLiteral();
		literal.setValue(value);
		return literal;
	}

	/**
	 * Creates a new {@link JSONArray} with the given {@code values} as elements.
	 */
	public static JSONArray createArray(Collection<JSONValue> values) {
		JSONArray result = JSONFactory.eINSTANCE.createJSONArray();
		result.getElements().addAll(values);
		return result;
	}

	/**
	 * Creates a new {@link JSONArray} with the given string {@code values} as elements.
	 *
	 * See {@link #createArray(Collection)} and {@link #createStringLiteral(String)}.
	 */
	public static JSONArray createStringArray(Iterable<String> values) {
		JSONArray result = JSONFactory.eINSTANCE.createJSONArray();
		values.forEach(v -> result.getElements().add(createStringLiteral(v)));
		return result;
	}

	/**
	 * Creates a new {@link JSONObject}. Be sure to pass in a {@link LinkedHashMap} or something similar if you want a
	 * particular order of properties.
	 */
	public static JSONObject createObject(Map<String, JSONValue> properties) {
		JSONObject result = JSONFactory.eINSTANCE.createJSONObject();
		for (Entry<String, JSONValue> entry : properties.entrySet()) {
			result.getNameValuePairs().add(createNameValuePair(entry.getKey(), entry.getValue()));
		}
		return result;
	}

	/**
	 * Like {@link #createObject(Map)}, but accepts a map of any type together with two functions for converting the
	 * map's original key/value to the name/value of the {@link NameValuePair}s of the {@link JSONObject} to be created.
	 */
	public static <K, V> JSONObject createObject(Map<K, V> properties,
			Function<K, String> fnKey, Function<V, JSONValue> fnValue) {
		JSONObject result = JSONFactory.eINSTANCE.createJSONObject();
		for (Entry<K, V> entry : properties.entrySet()) {
			result.getNameValuePairs().add(createNameValuePair(
					fnKey.apply(entry.getKey()),
					fnValue.apply(entry.getValue())));
		}
		return result;
	}

	/**
	 * Creates a new {@link NameValuePair}.
	 */
	public static NameValuePair createNameValuePair(String name, JSONValue value) {
		NameValuePair result = JSONFactory.eINSTANCE.createNameValuePair();
		result.setName(name);
		result.setValue(value);
		return result;
	}

	/**
	 * Creates a new {@link JSONDocument}.
	 */
	public static JSONDocument createDocument(JSONValue content) {
		JSONDocument result = JSONFactory.eINSTANCE.createJSONDocument();
		result.setContent(content);
		return result;
	}

	/**
	 * Same as {@link #parseJSON(String)}, but reads the source from a file on disk at the given path.
	 */
	public static JSONDocument loadJSON(Path path, Charset cs) throws IOException {
		try (BufferedReader in = Files.newBufferedReader(path, cs)) {
			ParseResult<JSONDocument> result = N4LanguageUtils.parseXtextLanguage(FILE_EXTENSION, null,
					JSONDocument.class, in);
			return result.errors.isEmpty() ? result.ast : null;
		}
	}

	/**
	 * Parses the given string as a JSON document. Returns <code>null</code> in case of syntax errors. If more
	 * fine-grained error handling is needed, use {@link N4LanguageUtils#parseXtextLanguage(String, Class, String)}
	 * instead.
	 */
	public static JSONDocument parseJSON(String jsonString) {
		ParseResult<JSONDocument> result = N4LanguageUtils.parseXtextLanguage(FILE_EXTENSION, JSONDocument.class,
				jsonString);
		return result.errors.isEmpty() ? result.ast : null;
	}

	/**
	 * Like {@link #serializeJSON(JSONDocument)}, but accepts any kind of {@link JSONValue}.
	 */
	public static String serializeJSON(JSONValue value) {
		return serializeJSON(createDocument(value));
	}

	/**
	 * Serializes the given {@link JSONDocument} using the Xtext serialization facilities provided by the JSON language.
	 */
	public static String serializeJSON(JSONDocument document) {
		ISerializer jsonSerializer = N4LanguageUtils.getServiceForContext(FILE_EXTENSION, ISerializer.class).get();
		ResourceSet resourceSet = N4LanguageUtils.getServiceForContext(FILE_EXTENSION, ResourceSet.class).get();

		// Use temporary Resource as AbstractFormatter2 implementations can only format
		// semantic elements that are contained in a Resource.
		Resource temporaryResource = resourceSet.createResource(URIUtils.toFileUri("__synthetic." + FILE_EXTENSION));
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

	/**
	 * Designated exception that may be raised when using property path based methods with regard to {@link JSONObject}
	 * instances.
	 *
	 * @See {@link JSONModelUtils#setPath(JSONObject, List, JSONValue)}
	 */
	public static final class JSONPropertyPathException extends RuntimeException {
		/**
		 * Instantiates a new {@link JSONPropertyPathException} with the given message.
		 */
		public JSONPropertyPathException(String message) {
			super(message);
		}

		/**
		 * Instantiates a new {@link JSONPropertyPathException} with the given message wrapping around
		 * {@code exception}.
		 */
		public JSONPropertyPathException(String message, Exception exception) {
			super(message, exception);
		}
	}
}
