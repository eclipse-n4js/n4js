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

import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.utils.ImmutableDataClass;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * Lists the filters that add special treatment to some of the files regarding validation, compilation and wrapping of
 * output code.
 */
@SuppressWarnings("javadoc")
public class ModuleFilter extends ImmutableDataClass {

	private final ModuleFilterType type;
	private final ImmutableList<ModuleFilterSpecifier> specifiers;

	public ModuleFilter(ModuleFilterType type, Iterable<ModuleFilterSpecifier> specifiers) {
		this.type = type;
		this.specifiers = ImmutableList.copyOf(specifiers);
	}

	public ModuleFilterType getType() {
		return type;
	}

	public List<ModuleFilterSpecifier> getSpecifiers() {
		return specifiers;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				type,
				specifiers);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		ModuleFilter other = (ModuleFilter) obj;
		return type == other.type
				&& Objects.equals(specifiers, other.specifiers);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " { type: " + type
				+ ", specifiers: " + (specifiers.isEmpty() ? "[]" : "[ " + Joiner.on(", ").join(specifiers) + " ]")
				+ " }";
	}
}
