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
package org.eclipse.n4js.packagejson.projectDescription;

import java.util.Objects;

import org.eclipse.n4js.utils.ImmutableDataClass;

@SuppressWarnings("javadoc")
public class ModuleFilterSpecifier extends ImmutableDataClass {

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
	protected int computeHashCode() {
		return Objects.hash(
				moduleSpecifierWithWildcard,
				sourcePath);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		ModuleFilterSpecifier other = (ModuleFilterSpecifier) obj;
		return Objects.equals(moduleSpecifierWithWildcard, other.moduleSpecifierWithWildcard)
				&& Objects.equals(sourcePath, other.sourcePath);
	}
}
