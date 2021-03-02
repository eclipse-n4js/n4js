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
public enum ProjectType implements KeywordEnum {

	//
	PLAINJS("plainjs"),
	//
	VALIDATION("validation"),
	//
	DEFINITION("definition"),
	//
	APPLICATION("application"),
	//
	PROCESSOR("processor"),
	//
	LIBRARY("library"),
	//
	API("api"),
	//
	RUNTIME_ENVIRONMENT("runtimeEnvironment"),
	//
	RUNTIME_LIBRARY("runtimeLibrary"),
	//
	TEST("test");

	private final String keyword;

	private ProjectType(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String getKeyword() {
		return keyword;
	}
}
