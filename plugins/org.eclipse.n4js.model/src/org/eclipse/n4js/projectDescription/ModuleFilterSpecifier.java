/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.projectDescription;

import java.util.Objects;

@SuppressWarnings("javadoc")
public class ModuleFilterSpecifier {

	private final String moduleSpecifierWithWildcard;
	private final String sourcePath;

	public ModuleFilterSpecifier(String moduleSpecifierWithWildcard, String sourcePath) {
		this.moduleSpecifierWithWildcard = moduleSpecifierWithWildcard;
		this.sourcePath = sourcePath;
	}

	public String getModuleSpecifierWithWildcard() {
		return moduleSpecifierWithWildcard;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				moduleSpecifierWithWildcard,
				sourcePath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ModuleFilterSpecifier))
			return false;
		ModuleFilterSpecifier other = (ModuleFilterSpecifier) obj;
		return Objects.equals(moduleSpecifierWithWildcard, other.moduleSpecifierWithWildcard)
				&& Objects.equals(sourcePath, other.sourcePath);
	}
}
