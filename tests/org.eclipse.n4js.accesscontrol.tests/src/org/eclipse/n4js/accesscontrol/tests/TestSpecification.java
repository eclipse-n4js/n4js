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
package org.eclipse.n4js.accesscontrol.tests;

import org.eclipse.n4js.tests.codegen.Classifier;
import org.eclipse.n4js.tests.codegen.Member;

/**
 * A specification of one test scenario that should be created and checked by this test case.
 */
class TestSpecification {
	private final int index;
	private final int row;
	private final int column;
	private final Scenario scenario;
	private final ClassifierType supplierType;
	private final ClassifierType clientType;
	private final ClientLocation clientLocation;
	private final UsageType usageType;
	private final Classifier.Visibility supplierVisibility;
	private final Member.Visibility memberVisibility;
	private final Member.Static memberStatic;
	private final Expectation expectation;

	/**
	 * Creates a new instance with the given parameters.
	 *
	 * @param index
	 *            the index of this specification
	 * @param row
	 *            the table row containing this specification
	 * @param column
	 *            the table column containing this specification
	 * @param scenario
	 *            the test scenario
	 * @param supplierType
	 *            the type of the supplier
	 * @param clientType
	 *            the type of the client
	 * @param clientLocation
	 *            the location of the generated client in relation to the generated supplier
	 * @param usageType
	 *            the type of usage intended by the client
	 * @param supplierVisibility
	 *            the visibility of the generated supplier type
	 * @param memberVisibility
	 *            the visibility of the generated member the client is attempting to access
	 * @param memberStatic
	 *            whether or not the accessed member is static
	 * @param expectation
	 *            the expected test result
	 */
	public TestSpecification(int index, int row, int column, Scenario scenario, ClassifierType supplierType,
			ClassifierType clientType,
			ClientLocation clientLocation,
			UsageType usageType,
			Classifier.Visibility supplierVisibility,
			Member.Visibility memberVisibility, Member.Static memberStatic, Expectation expectation) {
		this.index = index;
		this.row = row;
		this.column = column;
		this.scenario = scenario;
		this.supplierType = supplierType;
		this.clientType = clientType;
		this.clientLocation = clientLocation;
		this.usageType = usageType;
		this.supplierVisibility = supplierVisibility;
		this.memberVisibility = memberVisibility;
		this.memberStatic = memberStatic;
		this.expectation = expectation;
	}

	/**
	 * Indicates whether this specification has the given row and column index.
	 *
	 * @param row
	 *            the row index
	 * @param column
	 *            the column index
	 * @return <code>true</code> if this specification has the given indices and <code>false</code> otherwise
	 */
	@SuppressWarnings("hiding")
	public boolean hasPosition(int row, int column) {
		return this.row == row && this.column == column;
	}

	/**
	 * Returns the scenario
	 *
	 * @return the scenario
	 */
	public Scenario getScenario() {
		return scenario;
	}

	/**
	 * Returns the type of the supplier.
	 *
	 * @return the type of the supplier
	 */
	public ClassifierType getSupplierType() {
		return supplierType;
	}

	/**
	 * Returns the type of the client.
	 *
	 * @return the type of the client
	 */
	public ClassifierType getClientType() {
		return clientType;
	}

	/**
	 * Returns the usage type by which the client intends to access the generated supplier's members.
	 *
	 * @return the usage type
	 */
	public UsageType getUsageType() {
		return usageType;
	}

	/**
	 * Returns the location of the generated client in relation to the generated supplier.
	 *
	 * @return the client location
	 */
	public ClientLocation getClientLocation() {
		return clientLocation;
	}

	/**
	 * Returns the visibility of the generated supplier type.
	 *
	 * @return the supplier visibility
	 */
	public Classifier.Visibility getSupplierVisibility() {
		return supplierVisibility;
	}

	/**
	 * Returns the visibility of the generated member that the client is attempting to access.
	 *
	 * @return the member visibility
	 */
	public Member.Visibility getMemberVisibility() {
		return memberVisibility;
	}

	/**
	 * Returns whether or not the generated member that the client is attempting to access is static.
	 *
	 * @return whether or not the member is static
	 */
	public Member.Static getMemberStatic() {
		return memberStatic;
	}

	/**
	 * Returns the test expectation.
	 *
	 * @return the expectation
	 */
	public Expectation getExpectation() {
		return expectation;
	}

	@Override
	public String toString() {
		return (index + 1) + ": [" + (row + 1) + ", " + getColumnName(column) + "]: " + scenario + " scenario with "
				+ supplierVisibility + " supplier "
				+ supplierType
				+ " and client "
				+ clientType
				+ " at " + clientLocation
				+ ": "
				+ usageType + " to "
				+ memberVisibility + (memberStatic == Member.Static.YES ? " STATIC " : " INSTANCE ")
				+ "member with expectation: "
				+ expectation;
	}

	private static String getColumnName(int columnIndex) {
		String result = "";

		int outerIndex = columnIndex / 26;
		if (outerIndex > 0)
			result += getChar(outerIndex - 1);

		int innerIndex = columnIndex % 26;
		result += getChar(innerIndex);

		return result;
	}

	private static char getChar(int index) {
		return Character.toChars(index + 65)[0]; // 65 is the ASCII offset of 'A'
	}
}
