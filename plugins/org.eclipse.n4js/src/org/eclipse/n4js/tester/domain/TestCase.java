/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tester.domain;

import static com.google.common.base.Strings.nullToEmpty;

import org.eclipse.emf.common.util.URI;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class for representing a test case.
 */
@JsonAutoDetect
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TestCase implements TestElement, Comparable<TestCase> {

	@JsonProperty
	private ID id;
	@JsonProperty
	private String className;
	@JsonProperty
	private String origin;
	@JsonProperty
	private String name;
	@JsonProperty
	private String displayName;

	/** URI to the test method, module URI + method fragment */
	private URI uri;

	/* default */static TestCase copyOf(final TestCase testCase) {
		final TestCase copy = new TestCase(ID.copyOf(testCase.id), testCase.className, testCase.origin,
				testCase.name, testCase.displayName, testCase.uri);
		return copy;
	}

	/** Sole constructor. Used for serialization. */
	@SuppressWarnings("unused")
	private TestCase() {
	}

	/**
	 * Creates a new test case.
	 *
	 * @param id
	 *            the unique ID of the test case.
	 * @param className
	 *            the associated test class name.
	 * @param origin
	 *            the origin of the class. The container module name and its version.
	 * @param name
	 *            the name of the test case.
	 * @param displayName
	 *            the display name of the test case.
	 * @param methodURI
	 *            URI to the test method, module URI + method fragment
	 *
	 */
	public TestCase(final ID id, final String className, final String origin, final String name,
			final String displayName, URI methodURI) {
		this.id = id;
		this.className = className;
		this.origin = origin;
		this.name = name;
		this.displayName = displayName;
		this.uri = methodURI;
	}

	/**
	 * sets URI to the test method that serves as TC basis
	 *
	 * @return URI of the containing module with fragment pointing to the test method
	 */
	public URI getURI() {
		return this.uri;
	}

	/**
	 * Returns with the unique identifier of the test case.
	 *
	 * @return the ID.
	 */
	public ID getId() {
		return id;
	}

	/**
	 * Returns the class name associated with the test case.
	 *
	 * @return the class name of the test case.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Returns with the origin of corresponding test class. Preferably the module name and the module ID.
	 *
	 * @return the origin of the corresponding test class.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Returns with the name of the test case.
	 *
	 * @return the name of the test case.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns with the display name of the test case.
	 *
	 * @return the display name of the test case.
	 */
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public TestCase clone() throws CloneNotSupportedException {
		return copyOf(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 1;
		hash = prime * hash + ((id == null) ? 0 : id.hashCode());
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TestCase other = (TestCase) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" [ID: ");
		sb.append(id);
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int compareTo(final TestCase o) {
		return o == null ? -1 : nullToEmpty(name).compareTo(nullToEmpty(o.name));
	}

}
