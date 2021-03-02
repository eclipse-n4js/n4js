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

import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableList;

/**
 * Lists the filters that add special treatment to some of the files regarding validation, compilation and wrapping of
 * output code.
 */
@SuppressWarnings("javadoc")
public class ModuleFilter {

	private final ModuleFilterType moduleFilterType;
	private final ImmutableList<ModuleFilterSpecifier> moduleSpecifiers;

	public ModuleFilter(ModuleFilterType moduleFilterType, Iterable<ModuleFilterSpecifier> moduleSpecifiers) {
		this.moduleFilterType = moduleFilterType;
		this.moduleSpecifiers = ImmutableList.copyOf(moduleSpecifiers);
	}

	public ModuleFilterType getModuleFilterType() {
		return moduleFilterType;
	}

	public List<ModuleFilterSpecifier> getModuleSpecifiers() {
		return moduleSpecifiers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				moduleFilterType,
				moduleSpecifiers);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ModuleFilter))
			return false;
		ModuleFilter other = (ModuleFilter) obj;
		return moduleFilterType == other.moduleFilterType
				&& Objects.equals(moduleSpecifiers, other.moduleSpecifiers);
	}
}
