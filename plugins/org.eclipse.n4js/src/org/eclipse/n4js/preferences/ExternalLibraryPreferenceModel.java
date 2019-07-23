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
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.external.ExternalLibraryHelper;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/**
 * Represents an external library preference model.
 */
public class ExternalLibraryPreferenceModel {

	/** Name of JSON property that corresponds to field {@link #externalLibraryLocations}. */
	private static final String PROP_EXTERNAL_LIBRARY_LOCATIONS = "externalLibraryLocations";

	private final List<FileURI> externalLibraryLocations = newArrayList();
	private final LinkedHashSet<FileURI> externalLibraryLocationURIs = new LinkedHashSet<>();
	private final LinkedHashSet<FileURI> externalNodeModulesURIs = new LinkedHashSet<>();
	private long externalLibraryLocationURIsHash = 0;

	/**
	 * Creates and returns a new external library preference model that has initially one single element pointing to the
	 * user's home directory.
	 *
	 * @return a new default instance.
	 */
	public static ExternalLibraryPreferenceModel createDefault() {
		// FIXME suspicious!
		FileURI home = new FileURI(new File(StandardSystemProperty.USER_HOME.value()));
		return new ExternalLibraryPreferenceModel(home);
	}

	/**
	 * Creates a default ExternalLibraryPreferenceModel intended for use within the N4JS IDE product.
	 * <p>
	 * NOTE: this method used to include the n4js-libs bundled as "shipped code" in the IDE; however, shipped code was
	 * removed from the IDE, so this method always returns an empty preference model, for the time being.
	 * <p>
	 * This factory method requires a running {@link Platform platform}, otherwise a {@link IllegalStateException} will
	 * be thrown.
	 *
	 * @return a new model instance with the default external built-in libraries.
	 */
	public static ExternalLibraryPreferenceModel createDefaultForN4Product() {
		checkState(Platform.isRunning(), "Expected running platform.");
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
		synchronized (result) {
			for (String extLibLoc : extLibLocs) {
				FileURI fileUri = new FileURI(URIUtils.toFileUri(extLibLoc));
				result.externalLibraryLocations.add(fileUri);
			}
		}
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
	public ExternalLibraryPreferenceModel(final FileURI firstLocation,
			final FileURI... restLocations) {
		this(Lists.asList(firstLocation, restLocations));
	}

	/**
	 * Creates a new model instance with a list of external library folder locations.
	 *
	 * @param locations
	 *            the folder locations for external libraries.
	 */
	public ExternalLibraryPreferenceModel(final List<FileURI> locations) {
		for (final FileURI location : locations) {
			if (!this.externalLibraryLocations.contains(location)) {
				this.externalLibraryLocations.add(location);
			}
		}
	}

	/**
	 * Returns with a list of external library folder locations.
	 *
	 * @return a list of external library folder locations.
	 */
	public List<SafeURI<?>> getExternalLibraryLocations() {
		return Collections.unmodifiableList(externalLibraryLocations);
	}

	/**
	 * Adds the external library folder location. Returns with {@code true} if the addition was successful and the
	 * content of the underlying collection has been modified. Otherwise return with {@code false}. Has no effect if the
	 * argument is {@code null}. Such cases this method returns with {@code false}.
	 *
	 * @param location
	 *            the absolute file {@link SafeURI} pointing to the external library folder.
	 * @return {@code true} if the addition was successful, hence the state of the current instance has changed.
	 *         Otherwise {@code false}.
	 */
	synchronized public boolean add(final FileURI location) {
		if (null == location) {
			return false;
		}
		if (externalLibraryLocations.contains(location)) {
			return false;
		}
		return externalLibraryLocations.add(location);
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
	synchronized public boolean remove(final FileURI location) {
		if (null == location) {
			return false;
		}
		return externalLibraryLocations.removeAll(Collections.singleton(location));
	}

	/**
	 * Moves the external library folder up in the ordered list. Has no effect if the location is already the first
	 * element or if the element does not exist.
	 *
	 * @param location
	 *            to move up.
	 */
	synchronized public void moveUp(final FileURI location) {
		if (null != location) {
			int indexOf = externalLibraryLocations.indexOf(location);
			if (indexOf > 0) { // 0 is intentionally exclusive. Cannot move 'up' further,
				externalLibraryLocations.remove(indexOf);
				externalLibraryLocations.add(indexOf - 1, location);
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
	synchronized public void moveDown(final FileURI location) {
		if (null != location) {
			int indexOf = externalLibraryLocations.indexOf(location);
			if (indexOf >= 0 && indexOf < externalLibraryLocations.size() - 1) {
				externalLibraryLocations.remove(indexOf);
				externalLibraryLocations.add(indexOf + 1, location);
			}
		}
	}

	/**
	 * Returns with a view to the external library folder locations given as absolute file {@link SafeURI locations}.
	 *
	 * @return a list of external library folder location URIs.
	 */
	synchronized public LinkedHashSet<FileURI> getExternalLibraryLocationsAsUris() {
		int currentHash = externalLibraryLocations.hashCode();
		boolean needUpdate = currentHash != externalLibraryLocationURIsHash;
		if (needUpdate) {
			externalLibraryLocationURIsHash = currentHash;
			List<FileURI> locations = new LinkedList<>();
			for (FileURI projectLocation : externalLibraryLocations) {
				locations.add(projectLocation);
			}
			locations = ExternalLibraryHelper.sortByShadowing(locations);
			externalLibraryLocationURIs.clear();
			externalLibraryLocationURIs.addAll(locations);

			externalNodeModulesURIs.clear();
			for (FileURI location : locations) {
				if (isNodeModulesLocation(location)) {
					externalNodeModulesURIs.add(location);
				}
			}
		}
		return externalLibraryLocationURIs;
	}

	/**
	 * @return true of the URI points to a {@code node_modules} folder and false otherwise
	 */
	static public boolean isNodeModulesLocation(SafeURI<?> location) {
		String locStr = location.toString();
		if (locStr.endsWith("/")) {
			return locStr.endsWith(ExternalLibraryHelper.NPM_CATEGORY + "/");
		} else {
			return locStr.endsWith(ExternalLibraryHelper.NPM_CATEGORY);
		}
	}

	/**
	 * Returns with a view to the external library folder locations given as absolute file {@link SafeURI locations}.
	 *
	 * @return a list of external library folder location URIs.
	 */
	synchronized public Collection<FileURI> getNodeModulesLocationsAsUris() {
		return externalNodeModulesURIs;
	}

	/**
	 * Converts the current instance into a JSON string.
	 *
	 * @return the JSON string representation of the current instance.
	 */
	synchronized public String toJsonString() {
		final JSONArray extLibLocsValue = JSONModelUtils.createStringArray(
				() -> this.externalLibraryLocations.stream().map(Object::toString).iterator());
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
	synchronized public boolean equals(Object obj) {
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

}
