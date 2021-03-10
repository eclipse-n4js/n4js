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

@SuppressWarnings("javadoc")
public enum SourceContainerType implements KeywordEnum {

	// @formatter:off
	SOURCE("source"),
	TEST("test"),
	EXTERNAL("external");
	// @formatter:on

	private final String keyword;

	private SourceContainerType(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String getKeyword() {
		return keyword;
	}
}
