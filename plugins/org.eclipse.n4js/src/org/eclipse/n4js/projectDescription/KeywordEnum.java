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

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

/**
 * An enum that represents a value given in source code or other artifacts (e.g. <code>package.json</code> files), where
 * each enum literal corresponds to a particular unique keyword, as returned by {@link #getKeyword()}.
 */
public interface KeywordEnum {

	/**
	 * Returns the string used to represent this enum literal in source code or other artifacts (e.g. a
	 * <code>package.json</code> file).
	 */
	public String getKeyword();

	/**
	 * Creates and returns a map from {@link #getKeyword() keyword} to enum literal.
	 */
	public static <T extends KeywordEnum> Map<String, T> createKeywordToLiteralMap(Class<T> enumClass) {
		Preconditions.checkArgument(enumClass.isEnum(), "enumClass must represent an enum");
		Map<String, T> result = new HashMap<>();
		for (T literal : enumClass.getEnumConstants()) {
			result.put(literal.getKeyword(), literal);
		}
		return result;
	}
}
