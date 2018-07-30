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
package org.eclipse.n4js.preferences;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.EXTERNAL_LIBRARIES_SUPPLIER;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.requiresInfrastructureForLibraryManager;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;

import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/**
 * Represents an external library preference model.
 */
public class ExternalLibraryPreferenceModel {

	/** Name of JSON property that corresponds to field {@link #externalLibraryLocations}. */
	private static final String PROP_EXTERNAL_LIBRARY_LOCATIONS = "externalLibraryLocations";

	private final List<String> externalLibraryLocations = newArrayList();

	/**
	 * Creates and returns a new external library preference model that has initially one single element pointing to the
	 * user's home directory.
	 *
	 * @return a new default instance.
	 */
	public static ExternalLibraryPreferenceModel createDefault() {
		final URI homeFolderUri = new File(StandardSystemProperty.USER_HOME.value()).toURI();
		return new ExternalLibraryPreferenceModel(homeFolderUri);
	}

	/**
	 * Creates and returns with a new external library preference model that points to the built-in external N4JS
	 * libraries if the {@link Platform platform} is {@link Platform#isRunning() running} and the underlying
	 * {@link IProduct product} is the N4JS IDE product. Otherwise returns with a model instance that does not have any
	 * external library folder locations.
	 * <p>
	 * When the N4JS product is running and it has the property {@code includesBuiltInLibraries} with {@code true}
	 * value, then the built-in libraries will be considered to be included; For more details about the product specific
	 * property check the N4JS IDE application configuration in the {@code org.eclipse.n4js.product} bundle.
	 * <p>
	 * When the platform is running either in {@link Platform#inDebugMode() debug mode} or in
	 * {@link Platform#inDevelopmentMode() development mode}, one has to explicitly configure the
	 * {@code -Dorg.eclipse.n4js.includesBuiltInLibraries=true} VM argument to include the built-ins, otherwise those
	 * will be omitted.
	 * <p>
	 * This factory method requires a running {@link Platform platform}, otherwise a {@link IllegalStateException} will
	 * be thrown.
	 *
	 * @return a new model instance with the default external built-in libraries.
	 */
	public static ExternalLibraryPreferenceModel createDefaultForN4Product() {
		checkState(Platform.isRunning(), "Expected running platform.");
		if (requiresInfrastructureForLibraryManager()) {
			final List<URI> rootLocations = newArrayList(EXTERNAL_LIBRARIES_SUPPLIER.get().keySet());
			return new ExternalLibraryPreferenceModel(rootLocations);
		}
		return new ExternalLibraryPreferenceModel();
	}

	/**
	 * Creates and return a new external library preference model that was initialized from the JSON string argument.
	 *
	 * @param jsonString
	 *            the JSON string representation of an external preference model instance.
	 * @return a new preference model instance.
	 */
	public static ExternalLibraryPreferenceModel createFromJson(final String jsonString) {
		JSONDocument document = JSONModelUtils.parseJSON(jsonString);
		if (document == null) {
			throw new RuntimeException("Error occurred while trying to parse JSON string.");
		}
		JSONValue extLibLocsValue = JSONModelUtils.getProperty(document, PROP_EXTERNAL_LIBRARY_LOCATIONS).orElse(null);
		List<String> extLibLocs = JSONModelUtils.asStringsInArrayOrEmpty(extLibLocsValue);
		ExternalLibraryPreferenceModel result = new ExternalLibraryPreferenceModel();
		result.externalLibraryLocations.addAll(extLibLocs);
		return result;
	}

	/**
	 * Creates a new model instance with zero locations.
	 * <p>
	 * This empty constructor is mandatory for the serialization.
	 */
	public ExternalLibraryPreferenceModel() {
		this(emptyList());
	}

	/**
	 * Creates a new model instance with the given location arguments.
	 *
	 * @param firstLocation
	 *            the external library folder location.
	 * @param restLocations
	 *            other external library folder locations.
	 */
	public ExternalLibraryPreferenceModel(final URI firstLocation, final URI... restLocations) {
		this(Lists.asList(firstLocation, restLocations));
	}

	/**
	 * Creates a new model instance with a list of external library folder locations.
	 *
	 * @param locations
	 *            the folder locations for external libraries.
	 */
	public ExternalLibraryPreferenceModel(final List<URI> locations) {
		for (final URI location : locations) {
			checkUri(location);
			final String path = new File(location).getAbsolutePath();
			if (!this.externalLibraryLocations.contains(path)) {
				this.externalLibraryLocations.add(path);
			}
		}
	}

	/**
	 * Returns with a list of external library folder locations.
	 *
	 * @return a list of external library folder locations.
	 */
	public List<String> getExternalLibraryLocations() {
		return externalLibraryLocations;
	}

	/**
	 * Adds the external library folder location. Returns with {@code true} if the addition was successful and the
	 * content of the underlying collection has been modified. Otherwise return with {@code false}. Has no effect if the
	 * argument is {@code null}. Such cases this method returns with {@code false}.
	 *
	 * @param location
	 *            the absolute file {@link URI} pointing to the external library folder.
	 * @return {@code true} if the addition was successful, hence the state of the current instance has changed.
	 *         Otherwise {@code false}.
	 */
	public boolean add(final URI location) {
		if (null == location) {
			return false;
		}

		final String path = new File(checkUri(location)).getAbsolutePath();
		if (externalLibraryLocations.contains(path)) {
			return false;
		}

		return externalLibraryLocations.add(path);
	}

	/**
	 * Removes the external library folder. Returns with {code true} if the removal was successful, otherwise
	 * {@code false}. Has no effect if the argument is {@code null}. In such cases this method always returns with
	 * {@code false}.
	 *
	 * @param location
	 *            the location to remove.
	 * @return {@code true} if the location was removed, otherwise {@code false}.
	 */
	public boolean remove(final URI location) {
		if (null == location) {
			return false;
		}

		final String path = new File(checkUri(location)).getAbsolutePath();
		return externalLibraryLocations.remove(path);
	}

	/**
	 * Moves the external library folder up in the ordered list. Has no effect if the location is already the first
	 * element or if the element does not exist.
	 *
	 * @param location
	 *            to move up.
	 */
	public void moveUp(final URI location) {
		if (null != location) {
			final String path = new File(checkUri(location)).getAbsolutePath();
			int indexOf = externalLibraryLocations.indexOf(path);
			if (indexOf > 0) { // 0 is intentionally exclusive. Cannot move 'up' further,
				externalLibraryLocations.remove(indexOf);
				externalLibraryLocations.add(indexOf - 1, path);
			}
		}
	}

	/**
	 * Moves the external library folder down in the ordered list. Has no effect if the location is already the last
	 * element or if the element does not exist.
	 *
	 * @param location
	 *            to move down.
	 */
	public void moveDown(final URI location) {
		if (null != location) {
			final String path = new File(checkUri(location)).getAbsolutePath();
			int indexOf = externalLibraryLocations.indexOf(path);
			if (indexOf >= 0 && indexOf < externalLibraryLocations.size() - 1) {
				externalLibraryLocations.remove(indexOf);
				externalLibraryLocations.add(indexOf + 1, path);
			}
		}
	}

	/**
	 * Returns with a view to the external library folder locations given as absolute file {@link URI}s.
	 *
	 * @return a list of external library folder location URIs.
	 */
	public List<URI> getExternalLibraryLocationsAsUris() {
		return from(externalLibraryLocations).transform(path -> new File(path).toURI()).toList();
	}

	/**
	 * Converts the current instance into a JSON string.
	 *
	 * @return the JSON string representation of the current instance.
	 */
	public String toJsonString() {
		final JSONArray extLibLocsValue = JSONModelUtils.createStringArray(this.externalLibraryLocations);
		final JSONObject obj = JSONModelUtils.createObject(
				ImmutableMap.of(PROP_EXTERNAL_LIBRARY_LOCATIONS, extLibLocsValue));
		final JSONDocument doc = JSONModelUtils.createDocument(obj);
		return JSONModelUtils.serializeJSON(doc);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((externalLibraryLocations == null) ? 0 : externalLibraryLocations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ExternalLibraryPreferenceModel)) {
			return false;
		}
		ExternalLibraryPreferenceModel other = (ExternalLibraryPreferenceModel) obj;
		if (externalLibraryLocations == null) {
			if (other.externalLibraryLocations != null) {
				return false;
			}
		} else if (!externalLibraryLocations.equals(other.externalLibraryLocations)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return toJsonString();
	}

	/** Validates the URI. */
	private URI checkUri(final URI uri) {
		try {
			return new File(uri).toURI();
		} catch (final Exception e) {
			final String message = "Illegal URI: '" + uri + "'." + (null != e.getMessage() ? " " + e.getMessage() : "");
			throw new IllegalArgumentException(message, e);
		}
	}

}
