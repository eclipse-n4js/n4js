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
public final class N4JSPackageName implements Comparable<N4JSPackageName> {

	private final String name;

	/**
	 * Constructor
	 */
	public N4JSPackageName(String name) {
		Preconditions.checkNotNull(name);
		if (name.indexOf(NPM_SCOPE_SEPARATOR) < 0) {
			Preconditions.checkArgument(!name.startsWith(NPM_SCOPE_PREFIX), name);
			this.name = name;
		} else if (name.startsWith(NPM_SCOPE_PREFIX)) {
			Preconditions.checkArgument(name.indexOf(NPM_SCOPE_SEPARATOR) >= 0, name);
			this.name = name;
		} else {
			Path path = Path.of(name);
			this.name = ProjectDescriptionUtils.deriveN4JSPackageNameFromPath(path);
		}
	}

	/**
	 * Constructor
	 */
	public N4JSPackageName(Path path) {
		this(ProjectDescriptionUtils.deriveN4JSPackageNameFromPath(path));
	}

	/**
	 * Constructor
	 */
	public N4JSPackageName(File file) {
		this(ProjectDescriptionUtils.deriveN4JSPackageNameFromFile(file));
	}

	/**
	 * Constructor
	 */
	public N4JSPackageName(String scopeName, String plainName) {
		this(Preconditions.checkNotNull(scopeName) + NPM_SCOPE_SEPARATOR + Preconditions.checkNotNull(plainName));
	}

	/**
	 * Returns the plain project name of the project, i.e. without scope prefix.
	 */
	public String getPlainName() {
		return ProjectDescriptionUtils.getPlainPackageName(name);
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
	 * As explained {@link ProjectDescriptionUtils#isPackageNameWithScope(String) here}, a scope name, if present, and
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

	/** Return true iff this project name has the scope {@code @types/}. */
	public boolean isScopeTypes() {
		return getRawName().startsWith(N4JSGlobals.TYPES_SCOPE + "/");
	}

	/** Return the current project name with the scope {@code @types/}. */
	public N4JSPackageName toTypeScriptDefinition() {
		if (isScopeTypes()) {
			return this;
		}
		return new N4JSPackageName(N4JSGlobals.TYPES_SCOPE + " /" + getRawName());
	}

	@Override
	public int compareTo(N4JSPackageName o) {
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
		N4JSPackageName other = (N4JSPackageName) obj;
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
