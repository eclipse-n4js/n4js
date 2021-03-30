/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.workspace.utils;

import static org.eclipse.n4js.utils.ProjectDescriptionUtils.NPM_SCOPE_PREFIX;
import static org.eclipse.n4js.utils.ProjectDescriptionUtils.NPM_SCOPE_SEPARATOR;
import static org.eclipse.n4js.utils.ProjectDescriptionUtils.NPM_SCOPE_SEPARATOR_ECLIPSE;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Preconditions;

/**
 * A project name of the shape `(@scope/)?name`.
 */
public final class N4JSProjectName implements Comparable<N4JSProjectName> {

	private final String name;

	/**
	 * Constructor
	 */
	@SuppressWarnings("deprecation")
	public N4JSProjectName(String name) {
		this.name = Preconditions.checkNotNull(name);
		Preconditions.checkArgument(name.indexOf(NPM_SCOPE_SEPARATOR_ECLIPSE) < 0, name);
		if (name.indexOf(NPM_SCOPE_SEPARATOR) >= 0) {
			Preconditions.checkArgument(name.startsWith(NPM_SCOPE_PREFIX), name);
		}
	}

	/**
	 * Constructor
	 */
	public N4JSProjectName(Path path) {
		this(ProjectDescriptionUtils.deriveN4JSProjectNameFromPath(path));
	}

	/**
	 * Constructor
	 */
	public N4JSProjectName(File file) {
		this(ProjectDescriptionUtils.deriveN4JSProjectNameFromFile(file));
	}

	/**
	 * Constructor
	 */
	public N4JSProjectName(String scopeName, String plainName) {
		this(Preconditions.checkNotNull(scopeName) + NPM_SCOPE_SEPARATOR + Preconditions.checkNotNull(plainName));
	}

	/**
	 * Returns the plain project name of the project, i.e. without scope prefix.
	 */
	public String getPlainName() {
		return ProjectDescriptionUtils.getPlainProjectName(name);
	}

	/**
	 * Returns the scope name of the project or <code>null</code> if this project name does not include a scope prefix.
	 */
	public String getScopeName() {
		return ProjectDescriptionUtils.getScopeName(name);
	}

	/**
	 * Returns the raw name of this project, i.e. including scope prefix (if any) and using separator the
	 * {@link ProjectDescriptionUtils#NPM_SCOPE_SEPARATOR ordinary separator}.
	 */
	public String getRawName() {
		return name;
	}

	/** Returns this project name as a relative path */
	public Path getProjectNameAsRelativePath() {
		return getScopeName() != null
				? Path.of(getScopeName(), getPlainName())
				: Path.of(getRawName());
	}

	/** Returns the resolved project directory {@link File} against a given base directory */
	public File getLocation(Path baseDir) {
		return baseDir.resolve(getProjectNameAsRelativePath()).toFile();
	}

	/**
	 * Convert this project name to an Xtext qualified name.
	 * <p>
	 * As explained {@link ProjectDescriptionUtils#isProjectNameWithScope(String) here}, a scope name, if present, and
	 * the plain project name are expected to be represented as a <em>single</em> segment.
	 * <p>
	 * See also {@link N4JSQualifiedNameConverter#toQualifiedName(String)}.
	 */
	public QualifiedName toQualifiedName() {
		return QualifiedName.create(name);
	}

	/**
	 * Return true, if this name is empty.
	 */
	public boolean isEmpty() {
		return name.isEmpty();
	}

	/**
	 * Return true, if this name starts with {@link N4JSGlobals#N4JSD_SCOPE} followed by a slash.
	 */
	public boolean isScopeN4jsd() {
		return name.startsWith(N4JSGlobals.N4JSD_SCOPE + "/");
	}

	@Override
	public int compareTo(N4JSProjectName o) {
		return name.compareTo(o.getRawName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			throw new IllegalArgumentException("Cannot compare to type " + obj.getClass().getName());
		N4JSProjectName other = (N4JSProjectName) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
