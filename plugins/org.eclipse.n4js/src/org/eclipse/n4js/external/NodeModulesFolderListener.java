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

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.Strings;

import com.google.common.base.Preconditions;

/**
 * Use this listener to get updated whenever npm libraries in the {@code node_modules} folder change.
 */
public interface NodeModulesFolderListener {

	/** The {@link LibraryChangeType} tells in which way an npm library changes */
	public enum LibraryChangeType {
		/** An npm package was newly added to the {@code node_modules} folder */
		Added,
		/** An npm package was removed from the {@code node_modules} folder */
		Removed,
		/** An npm package in the {@code node_modules} folder was updated */
		Updated
	}

	/** Holds information how a npm library changed */
	public class LibraryChange {
		/** The type of the npm change */
		public final LibraryChangeType type;
		/** The location of the npm package */
		public final URI location;
		/** The name of the npm package that */
		public final String name;
		/** The new version to be installed. Not defined when {@link #type} is {@link LibraryChangeType#Removed}. */
		public final String version;

		LibraryChange(LibraryChangeType type, URI location, String name, String version) {
			Preconditions.checkArgument(name != null && !name.isEmpty());
			this.type = type;
			this.location = location;
			this.name = name;
			this.version = Strings.emptyIfNull(version);
		}

		@Override
		public String toString() {
			return type.name() + ":" + name + "@" + Strings.emptyIfNull(version);
		}
	}

	/**
	 * Gets triggered after a change in the folder structure of the {@code node_modules} folder was detected that
	 * affected one or more npm libraries.
	 */
	public void librariesChanged(IProgressMonitor monitor, List<LibraryChange> changeSet);

}
