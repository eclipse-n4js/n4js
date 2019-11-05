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
package org.eclipse.n4js.external;

import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.xtext.util.Strings;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Holds information how a npm library changed
 */
public class LibraryChange {

	/** The {@link LibraryChangeType} tells in which way an npm library changes */
	static public enum LibraryChangeType {
		/** An npm package shall be installed. The {@link #location} is null. */
		Install,
		/** An npm package shall be uninstalled. The {@link #version} can be empty. */
		Uninstall,
		/** An npm package was newly added to the {@code node_modules} folder */
		Added,
		/** An npm package was removed from the {@code node_modules} folder */
		Removed,
		/** An npm package in the {@code node_modules} folder was updated */
		Updated
	}

	/** The type of the npm change */
	public final LibraryChangeType type;
	/** The location of the npm package */
	public final FileURI location;
	/** The name of the npm package that */
	public final N4JSProjectName name;
	/** The new version to be installed. Not defined when {@link #type} is {@link LibraryChangeType#Removed}. */
	public final String version;

	/** Constructor */
	public LibraryChange(LibraryChangeType type, FileURI location, N4JSProjectName name, String version) {
		Preconditions.checkArgument(name != null && !name.isEmpty());
		this.type = type;
		this.location = location;
		this.name = name;
		this.version = Strings.emptyIfNull(version);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LibraryChange)) {
			return false;
		}
		LibraryChange lc = (LibraryChange) o;

		if (version != null && lc.version != null && version.equals(lc.version)) {
			return true;
		} else if (version == null || lc.version == null) {
			return false;
		}
		return location.equals(lc.location);
	}

	@Override
	public int hashCode() {
		if (version != null) {
			return Objects.hashCode(location, version);
		}
		return location.hashCode();
	}

	@Override
	public String toString() {
		String versionString = (version.isEmpty()) ? "" : "@" + version;
		return type.name() + ":" + name + versionString;
	}

}
