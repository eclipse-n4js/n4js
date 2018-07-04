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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;

/**
 * Utility methods for more convenient access to elements of the {@link JSONPackage} model.
 */
public class JSONModelUtils {

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
