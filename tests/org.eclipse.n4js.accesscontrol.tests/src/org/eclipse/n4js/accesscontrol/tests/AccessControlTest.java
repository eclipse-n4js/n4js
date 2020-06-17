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

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.cli.N4jscExitCode;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.cli.helper.CliTools;
import org.eclipse.n4js.csv.CSVData;
import org.eclipse.n4js.csv.CSVParser;
import org.eclipse.n4js.csv.CSVRecord;
import org.eclipse.n4js.ide.xtext.server.DiagnosticIssueConverter;
import org.eclipse.n4js.tests.codegen.Classifier;
import org.eclipse.n4js.tests.codegen.Member;
import org.eclipse.n4js.tests.issues.IssueExpectations;
import org.eclipse.n4js.utils.SimpleParserException;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

// @formatter:off
/**
 * A parameterized test that reads its parameters from a CSV export of the file 'Matrix.xlsx'.
 *
 * The test reads the table in the {@link #data()} method. See the javadoc comment for that method for more details on
 * the expected structure of the table.
 *
 * This test generates a large number of test cases from the table. For each expectation in the table, four tests are
 * performed; one for each member type (field, getter, setter, and method). In each test, two classifiers (a classifier is either a class or an interface) are generated: A <b>supplier</b> contains a member that a <b>client</b> is attempting to access. Thereby, accessing a member means that the member is being read, written, called, or overridden.
 * The details of each specific tests are specified using the following parameters:
 *
 * <ul>
 *     <li>
 *         <b>Scenario</b> one of
 *         <ul>
 *             <li><b>Extends</b> The client extends the supplier, which implies that the supplier and the client are either both classes or both interfaces.</li>
 *             <li><b>Implements</b> The client implements the supplier, which implies that the supplier is an interface and the client is a class.</li>
 *             <li><b>References</b> The client has a reference to an instance of the supplier. Here, the client must be a class while the supplier can be either a class or an interface. If the supplier is abstract, then an implementing class is automatically generated.</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Supplier Type</b> The actual type of the supplier, which is one of
 *         <ul>
 *             <li><b>Class</b> A concrete, instantiable class.</li>
 *             <li><b>Abstract Class</b> An abstract class. The generated member is also abstract unless it's a field.</li>
 *             <li><b>Interface</b> An abstract interface. The generated member is also abstract unless it's a field.</li>
 *             <li><b>Default Interface</b> An interface where all members have default implementations.</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Client Type</b> The actual type of the client, which has the same possible values as <b>Supplier Type</b>.
 *     </li>
 *     <li>
 *         <b>Client Location</b> The location of the client in relation to the supplier, one of
 *         <ul>
 *             <li><b>Same Type</b> Client and supplier are the same type.</li>
 *             <li><b>Same Module</b> Client and supplier are generated into the same module. This implies that only one project has to be generated.</li>
 *             <li><b>Same Project</b> Client and supplier are in separate modules in the same project. Again, only one project is generated.</li>
 *             <li><b>Same Vendor</b> Client and supplier are in different projects that share the same vendor ID.</li>
 *             <li><b>Other</b> Client and supplier are in different projects with different vendor IDs.</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Member Static</b> Whether or not the member being accessed is an instance member or a static member, one of <b>Instance</b> or <b>Static</b></li>
 *     </li>
 *     <li>
 *         <b>Usage Type</b> The type of usage that the client is attempting, one of
 *         <ul>
 *             <li><b>Access</b> The client is attempting to read, write, or call a member of the supplier.</li>
 *             <li><b>Override</b> The client is attempting to override a member of the supplier.</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Supplier Visibility</b> The top level visibility modifier for the supplier classifier, one of
 *         <ul>
 *             <li><b>pub</b> public visibility</li>
 *             <li><b>pub@</b> public internal visibility</li>
 *             <li><b>proj</b> project visibility</li>
 *             <li><b>priv</b> (module-) private visibility (no modifier added)</li>
 *         </ul>
 *         For the first three visibilities, the keyword <code>export</code> is automatically added in the generated code.
 *     </li>
 *     <li>
 *         <b>Member Visibility</b> The visibility of the generated member of the supplier that is being accessed by the client, one of
 *         <ul>
 *             <li><b>pub</b> public visibility</li>
 *             <li><b>pub@</b> public internal visibility</li>
 *             <li><b>prot</b> protected visibility</li>
 *             <li><b>prot@</b> protected internal visibility</li>
 *             <li><b>proj</b> project visibility</li>
 *             <li><b>priv</b> private visibility</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Expectation</b> The expectation of the test's outcome, one of
 *         <ul>
 *             <li><b>y</b> The test is expected to succeed without warnings or errors.</li>
 *             <li><b>n</b> The test is expected to fail with an error</li>
 *             <li>
 *                 <b>u</b> The test is expected to fail with two errors:
 *                 <ul>
 *                     <li>One error at the client classifier level regarding the unusability of the supplier.</li>
 *                     <li>Another error at the member level of the client.</li>
 *             </li>
 *         </ul>
 *     </li>
 * </ul>
 */
// TODO: actually support the "Same Type" client location
// @formatter:on
@RunWith(Parameterized.class)
public class AccessControlTest {
	// @formatter:off
	/**
	 * Returns the parameters for this test by parsing the appropriate CSV file. Expects the following structure of the table in the CSV:
	 *
	 * <br /><br />
	 * <table border="1">
	 *     <tr>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center" colspan="48"><b>Instance | Static</b></td>
	 *         <td align="center" colspan="48"><b>Instance | Static</b></td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center" colspan="24"><b>Override | Access</b></td>
	 *         <td align="center" colspan="24"><b>Override | Access</b></td>
	 *         <td align="center" colspan="24"><b>Override | Access</b></td>
	 *         <td align="center" colspan="24"><b>Override | Access</b></td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center" colspan="6"><b>pub</b></td>
	 *         <td align="center" colspan="6"><b>pub@</b></td>
	 *         <td align="center" colspan="6"><b>proj</b></td>
	 *         <td align="center" colspan="6"><b>priv</b></td>
	 *         <td align="center" colspan="6"><b>pub</b></td>
	 *         <td align="center" colspan="6"><b>pub@</b></td>
	 *         <td align="center" colspan="6"><b>proj</b></td>
	 *         <td align="center" colspan="6"><b>priv</b></td>
	 *         <td align="center" colspan="6"><b>pub</b></td>
	 *         <td align="center" colspan="6"><b>pub@</b></td>
	 *         <td align="center" colspan="6"><b>proj</b></td>
	 *         <td align="center" colspan="6"><b>priv</b></td>
	 *         <td align="center" colspan="6"><b>pub</b></td>
	 *         <td align="center" colspan="6"><b>pub@</b></td>
	 *         <td align="center" colspan="6"><b>proj</b></td>
	 *         <td align="center" colspan="6"><b>priv</b></td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *         <td><b>pub</b></td>
	 *         <td><b>pub@</b></td>
	 *         <td><b>prot</b></td>
	 *         <td><b>prot@</b></td>
	 *         <td><b>proj</b></td>
	 *         <td><b>priv</b></td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center"><i>ignored</i></td>
	 *         <td align="center" colspan="96"><i>ignored</i></td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center" rowspan="5"><b>Extends&nbsp;| Implements&nbsp;| References</b></td>
	 *         <td align="center" rowspan="5"><b>Class&nbsp;| Abstract&nbsp;Class&nbsp;| Interface&nbsp;| Default&nbsp;Interface</b></td>
	 *         <td align="center" rowspan="5"><b>Class&nbsp;| Abstract&nbsp;Class&nbsp;| Interface&nbsp;| Default&nbsp;Interface</b></td>
	 *         <td align="center"><b>Same&nbsp;Type</b></td>
	 *         <td align="center">y|n|u</td>
	 *         <td align="center" colspan="95">...</td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center"><b>Same&nbsp;Module</b></td>
	 *         <td align="center" rowspan="4">...</td>
	 *         <td align="center" rowspan="4" colspan="95">...</td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center"><b>Same&nbsp;Project</b></td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center"><b>Same&nbsp;Vendor</b></td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center"><b>Other</b></td>
	 *     </tr>
	 *     <tr>
	 *         <td align="center">...</td>
	 *         <td align="center">...</td>
	 *         <td align="center">...</td>
	 *         <td align="center">...</td>
	 *         <td align="center" colspan="96">...</td>
	 *     </tr>
	 * </table>
	 * <br />
	 *
	 * Note each cell within the range between rows 6,...,10 and the columns 5,... corresponds to four scenarios that only differ by the actual member type being generated in the supplier type, however the expectation is the same in each case. More test cases can be specified with different parameters for the scenario, supplier type, and client type. Each combination of these three parameters is followed by 5 x 96 cells of expectations that correspond to the different client location, visibilities, usage type and static specifiers.
	 * @return the parameters
	 * @throws SimpleParserException if an error occurs while parsing the CSV file
	 */
    // @formatter:on
	@Parameters(name = "{0}")
	public static List<TestSpecification> data() throws IOException, SimpleParserException {
		CSVData csvData = CSVParser.parse("testdata/Matrix.csv", StandardCharsets.UTF_8);

		List<TestSpecification> result = new LinkedList<>();

		CSVData accessSpec = csvData.getRange(0, 4, 4, -1);
		CSVData scenarios = csvData.getRange(5, 0, -1, -1);

		for (int row = 0; row < scenarios.getSize() / 5; row++) {
			CSVData scenario = scenarios.getRange(row * 5, 0, 5, -1);
			int indexOffset = result.size();
			int rowOffset = row * 5 + 5;
			int columnOffset = 4;
			result.addAll(createTestSpecifications(accessSpec, scenario, indexOffset, rowOffset, columnOffset));
		}

		return result;
	}

	/**
	 * Creates the test specifications for the scenarios in the given portion of the CSV input table.
	 *
	 * @param accessSpec
	 *            the portion of the CSV table that contains the access specifications (top four rows)
	 * @param data
	 *            the actual test data
	 * @param rowOffset
	 *            the current row offset
	 * @param columnOffset
	 *            the current column offset
	 * @return a list of test specifications corresponding to the scenarios in the given portion of the table
	 */
	private static List<TestSpecification> createTestSpecifications(CSVData accessSpec, CSVData data, int indexOffset,
			int rowOffset,
			int columnOffset) {
		List<TestSpecification> result = new LinkedList<>();

		CSVData testSpec = data.getRange(0, 0, -1, 4);

		Scenario scenario = Scenario.parse(testSpec.get(0, 0));
		ClassifierType supplierType = ClassifierType.parse(testSpec.get(0, 1));
		ClassifierType clientType = ClassifierType.parse(testSpec.get(0, 2));

		int count = 0;

		for (int row = 0; row < testSpec.getSize(); row++) {
			ClientLocation clientLocation = ClientLocation.parse(testSpec.get(row, 3));
			CSVRecord expectations = data.get(row).getRange(4, -1);

			for (int col = 0; col < expectations.getSize(); col++) {
				Member.Static memberStatic = parseStatic(accessSpec.get(0, getFieldIndex(col, 2 * 4 * 6)));
				UsageType usageType = UsageType.parse(accessSpec.get(1, getFieldIndex(col, 4 * 6)));
				Classifier.Visibility supplierVisibility = parseClassifierVisibility(
						accessSpec.get(2, getFieldIndex(col, 6)));
				Member.Visibility memberVisibility = parseMemberVisibility(accessSpec.get(3, col));
				Expectation expectation = Expectation.parse(expectations.get(col));

				if (expectation != Expectation.SKIP) {
					TestSpecification specification = new TestSpecification(count + indexOffset, row + rowOffset,
							col + columnOffset,
							scenario, supplierType, clientType,
							clientLocation,
							usageType, supplierVisibility, memberVisibility, memberStatic, expectation);
					result.add(specification);
					count++;
				}
			}
		}

		return result;
	}

	/**
	 * Returns the index of a field in a row of multiple fields having a fixed column span. Consider the following
	 * example.
	 *
	 * <br />
	 * <br />
	 * <table border="1">
	 * <tr>
	 * <td>1</td>
	 * <td>2</td>
	 * <td>3</td>
	 * <td>4</td>
	 * <td>5</td>
	 * <td>6</td>
	 * <td>7</td>
	 * <td>8</td>
	 * <td>9</td>
	 * </tr>
	 * <tr>
	 * <td colspan="3">field 1</td>
	 * <td colspan="3">field 2</td>
	 * <td colspan="3">field 3</td>
	 * </tr>
	 * </table>
	 * <br />
	 * The CSV representation of the table above looks as follows
	 *
	 * <br />
	 * <br />
	 * <code>1,2,3,4,5,6,7,8,9</code> <br />
	 * <code>field 1,,,field 2,,,field 3,,</code> <br />
	 * <br />
	 *
	 * In CVS, multi-column fields are not considered in any special way, so the second row would be stored as if there
	 * were no multi-column fields. This method helps in calculcating the actual column index of the fields in CSV where
	 * the multi-column fields are stored.
	 *
	 * In the example table, we could retrieve the actual index of the second multi-column field in the CSV data,
	 * <i>field 2</i>>, by passing calling <code>getFieldIndex(1, 3)</code> (note that the first parameter is zero
	 * based).
	 *
	 * @param index
	 *            the zero based index of the field of interest
	 * @param colSpan
	 *            the number of columns that are spanned by the fields
	 * @return the zero based index of the field of interest
	 */
	private static int getFieldIndex(int index, int colSpan) {
		return getFieldIndex(index, colSpan, 0);
	}

	/**
	 * Like {@link #getFieldIndex(int, int)}, this method retrieves the actual index of a multi-column field, but here,
	 * the field can have an offset that should be ignored. This is useful for tables where a multi-column field is
	 * prepended by fields with different column spans.
	 *
	 * @param index
	 *            the zero based index of the field of interest
	 * @param colSpan
	 *            the number of columns that are spanned by the fields
	 * @param colOffset
	 *            the number of columns to ignore
	 * @return the zero based index of the field of interest
	 */
	private static int getFieldIndex(int index, int colSpan, int colOffset) {
		return ((index - colOffset) / colSpan) * colSpan + colOffset;
	}

	/**
	 * Parses the given string that indicates whether or not a member should be static.
	 *
	 * @param str
	 *            the string to parse
	 *
	 * @return the {@link org.eclipse.n4js.tests.codegen.Member.Static} enum value that corresponds to the given string
	 */
	private static Member.Static parseStatic(String str) {
		switch (str) {
		case "Instance":
			return Member.Static.NO;
		case "Static":
			return Member.Static.YES;
		default:
			throw new IllegalArgumentException("Unexpected member type: '" + str + "'");
		}
	}

	/**
	 * Parses the given string that represents the visibility of a classifier.
	 *
	 * @param str
	 *            the string to parse
	 * @return the {@link org.eclipse.n4js.tests.codegen.Classifier.Visibility} enum value that corresponds to the given
	 *         string
	 */
	private static Classifier.Visibility parseClassifierVisibility(String str) {
		switch (str) {
		case "pub":
			return Classifier.Visibility.PUBLIC;
		case "pub@":
			return Classifier.Visibility.PUBLIC_INTERNAL;
		case "proj":
			return Classifier.Visibility.PROJECT;
		case "priv":
			return Classifier.Visibility.PRIVATE;
		default:
			throw new IllegalArgumentException("Unexpected type visibility: '" + str + "'");
		}
	}

	/**
	 * Parses the given string that represents the visibility of a member.
	 *
	 * @param str
	 *            the string to parse
	 * @return the {@link org.eclipse.n4js.tests.codegen.Member.Visibility} enum value that corresponds to the given
	 *         string
	 */
	private static Member.Visibility parseMemberVisibility(String str) {
		switch (str) {
		case "pub":
			return Member.Visibility.PUBLIC;
		case "pub@":
			return Member.Visibility.PUBLIC_INTERNAL;
		case "prot":
			return Member.Visibility.PROTECTED;
		case "prot@":
			return Member.Visibility.PROTECTED_INTERNAL;
		case "proj":
			return Member.Visibility.PROJECT;
		case "priv":
			return Member.Visibility.PRIVATE;
		default:
			throw new IllegalArgumentException("Unexpected member visibility: '" + str + "'");
		}
	}

	/**
	 * The specification of the currently executed test scenario.
	 */
	private final TestSpecification specification;

	/**
	 * Creates a new instance of this test that runs the given test specification.
	 *
	 * @param specification
	 *            the specification to run
	 */
	public AccessControlTest(TestSpecification specification) {
		this.specification = specification;
	}

	/**
	 * Performs the actual test according to the specification.
	 */
	@Test
	public void test() throws IOException {
		executeSpecification(specification);
	}

	/**
	 * The directory to store the test fixture (the generated projects) in.
	 */
	private static String FIXTURE_ROOT = "n4js-gen";

	private static void executeSpecification(TestSpecification specification)
			throws IOException {

		deleteFixtureDirectory();

		boolean failOnExit = specification.getExpectation().isFixMe();
		Map<MemberType, List<String>> fixMeMessages = new HashMap<>();

		for (MemberType memberType : MemberType.values()) {
			createFixtureDirectory(memberType);

			ScenarioGenerator generator = new ScenarioGenerator(specification, memberType);
			generator.generateScenario(Paths.get(FIXTURE_ROOT, memberType.name()));
			List<Issue> issues = new ArrayList<>(compile(memberType));
			issues.removeIf(issue -> N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS
					.contains(issue.getCode()));
			IssueExpectations expectations = generator.createIssues();

			if (specification.getExpectation().isFixMe()) {
				// We want the entire test case to fail only if none of the tested scenarios match their
				// expectations.
				List<String> messages = new ArrayList<>();
				final boolean fixMeFailed = !expectations.matchesExactly(issues, messages);
				if (fixMeFailed)
					fixMeMessages.put(memberType, messages);
				failOnExit &= fixMeFailed;
			} else {
				List<String> messages = new ArrayList<>();
				boolean result = expectations.matchesExactly(issues, messages);
				assertTrue(Joiner.on(", ").join(messages), result);
				messages.clear();
			}
		}

		if (failOnExit) {
			StringBuilder message = new StringBuilder();
			for (Map.Entry<MemberType, List<String>> entry : fixMeMessages.entrySet()) {
				final MemberType memberType = entry.getKey();
				final List<String> messages = entry.getValue();

				message.append("FixMe test failed for member type ").append(memberType).append(": \n");
				for (String line : messages)
					message.append("    ").append(line).append("\n");
			}

			fail(message.toString());
		}
	}

	/**
	 * Compiles the projects generated into the path at {@link #FIXTURE_ROOT}, which in this test case the projects
	 * representing the currently tested scenario and returns the generated issues.
	 *
	 * @return the generated issues
	 */
	private static Collection<Issue> compile(MemberType memberType) {
		File projectRoot = Paths.get(FIXTURE_ROOT, memberType.name()).toFile();

		CliTools cliTools = new CliTools();
		// cliTools.setIsMirrorSystemOut(true); // print directly to console
		cliTools.setIgnoreFailure(true); // some test cases contain compile errors => custom failure checking required
		cliTools.setEnvironmentVariable("NPM_TOKEN", "dummy");
		CliCompileResult compileResult = new CliCompileResult();
		cliTools.callN4jscInprocess(COMPILE(projectRoot), false, compileResult);

		Exception compileException = compileResult.getException();
		if (compileException != null) {
			compileException.printStackTrace();
			fail("exception during compilation: " + compileResult);
		}
		if (compileResult.getExitCode() != 0) {
			if (compileResult.getExitCode() == N4jscExitCode.VALIDATION_ERRORS.getExitCodeValue()
					&& compileResult.getErrMsgs().isEmpty()) {

				fail("non-zero exit code from compilation: " + compileResult.getExitCode() + System.lineSeparator()
						+ compileResult);
			}
		}

		DiagnosticIssueConverter converter = new DiagnosticIssueConverter();
		List<Issue> issues = new ArrayList<>();
		for (Map.Entry<String, Diagnostic> uriDiagnostic : compileResult.getIssues().entries()) {
			URI uri = URI.createURI(uriDiagnostic.getKey());
			Diagnostic diagnostic = uriDiagnostic.getValue();
			issues.add(converter.toIssue(uri, diagnostic, Optional.absent()));
		}

		return issues;
	}

	/**
	 * Recreates the test fixture root directory. If the directory exists, it will be deleted before it is created
	 * again.
	 *
	 * @throws IOException
	 *             if the directory cannot be created
	 */
	private static void createFixtureDirectory(MemberType memberType) throws IOException {
		final Path path = Paths.get(FIXTURE_ROOT, memberType.name());
		if (Files.exists(path))
			deleteFixtureDirectory();
		Files.createDirectories(path);
	}

	/**
	 * Deletes the test fixture root directory.
	 *
	 * @throws IOException
	 *             if the directory cannot be deleted
	 */
	private static void deleteFixtureDirectory() throws IOException {
		FileDeleter.delete(new File(FIXTURE_ROOT));
	}
}
