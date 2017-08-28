/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.hlc.base.testing;

import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_TESTER_NOT_FOUND;
import static org.eclipse.n4js.hlc.base.ErrorExitCode.EXITCODE_TESTER_STOPPED_WITH_ERROR;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.tester.CliTestTreeXMLTransformer;
import org.eclipse.n4js.tester.TestConfiguration;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.TesterFacade;
import org.eclipse.n4js.tester.TesterFrontEnd;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.extension.ITesterDescriptor;
import org.eclipse.n4js.tester.extension.TesterRegistry;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.inject.Inject;

/**
 * Headless tester creates external process that executes tests in the provided location.
 */
public class HeadlessTester {
	/** Name of the generated report file. */
	private static final String TEST_REPORT_NAME = "test-report.xml";

	@Inject
	private TesterFrontEnd testerFrontEnd;

	@Inject
	private TesterFacade testerFacade;

	@Inject
	private TesterRegistry testerRegistry;

	@Inject
	private TesterEventBus testerEventBus;

	@Inject
	private IHeadlessLogger logger;

	@Inject
	private CliTestTreeXMLTransformer testTreeXmlTransformer;

	/**
	 * Actually start the requested tester to test provided location. Workspace from headlesCompiler should be
	 * configured before calling this method.
	 *
	 * @param tester
	 *            the tester to be used
	 * @param implementationId
	 *            to be used for API-IMPL projects
	 * @param locationToTest
	 *            location of the test code
	 * @throws ExitCodeException
	 *             in cases of errors
	 */
	public void runTests(String tester, String implementationId, URI locationToTest, File testReportRoot)
			throws ExitCodeException {

		ITesterDescriptor testerDescriptor = checkTester(tester);
		logger.info("Using tester :" + testerDescriptor.getId());

		TestConfiguration testConfiguration = null;
		try {
			testConfiguration = testerFrontEnd.createConfiguration(testerDescriptor.getId(),
					implementationId, locationToTest);
		} catch (java.lang.IllegalStateException e2) {
			logger.error(Throwables.getStackTraceAsString(e2));
			throw new ExitCodeException(EXITCODE_TESTER_STOPPED_WITH_ERROR,
					"Cannot create test configuration.", e2);
		}

		try {
			TestTree testTree = testConfiguration.getTestTree();

			LoggingTestListener testListener = new LoggingTestListener(testerEventBus, logger, testTree);
			Process process = testerFrontEnd.test(testConfiguration);

			int exit = process.waitFor();

			String errors = "";

			if (exit != 0) {
				errors += "The spawned tester '" + testerDescriptor.getId()
						+ "' exited with code=" + exit + "\n";
			}

			if (testReportRoot != null) {
				if (testReportRoot.isDirectory() && testReportRoot.canWrite()) {
					File report = new File(testReportRoot, TEST_REPORT_NAME);
					if (report.exists()) {
						try {
							FileDeleter.delete(report);
						} catch (IOException e) {
							throw new ExitCodeException(EXITCODE_TESTER_STOPPED_WITH_ERROR,
									"Test report location cannot be cleared at: "
											+ report.getAbsolutePath() + ".",
									e);
						}
					}
					if (testListener.finished()) {
						TestReport testReport = new TestReport((StringBuilder) testTreeXmlTransformer.apply(testTree));
						try {
							testReport.dump(report);
						} catch (IOException e) {
							throw new ExitCodeException(EXITCODE_TESTER_STOPPED_WITH_ERROR,
									"Test report location cannot be generated at: "
											+ report.getAbsolutePath() + ".",
									e);
						}
						System.out.println("_____________");
					} else {
						// thread.sleep to wait for the test event busy?
						errors += "test session still in progress.\n";
					}
				} else {
					// thread.sleep to wait for the test event busy?
					errors += "cannot write test report to " + testReportRoot + "\n";
				}

			}

			if (!testListener.isOK()) {
				errors += "There were test errors, see console logs and/or test report for details.\n";
			}

			if (!errors.isEmpty()) {
				throw new ExitCodeException(EXITCODE_TESTER_STOPPED_WITH_ERROR,
						errors);
			}

		} catch (InterruptedException e1) {
			logger.error(Throwables.getStackTraceAsString(e1));
			throw new ExitCodeException(EXITCODE_TESTER_STOPPED_WITH_ERROR,
					"The spawned tester exited by throwing an exception", e1);
		} finally {
			testerFacade.shutdownFramework();
		}
	}

	private ITesterDescriptor checkTester(String tester) throws ExitCodeException {
		final List<ITesterDescriptor> matchingTesterDescs = findTesterById(tester);

		if (matchingTesterDescs.isEmpty()) {
			throw new ExitCodeException(EXITCODE_TESTER_NOT_FOUND, "no tester found for id: " + tester);
		} else if (matchingTesterDescs.size() > 1) {
			throw new ExitCodeException(EXITCODE_TESTER_NOT_FOUND, "several testers match given id \"" + tester
					+ "\":\n\t" + Joiner.on("\n\t").join(matchingTesterDescs));
		}

		return matchingTesterDescs.get(0);
	}

	private List<ITesterDescriptor> findTesterById(String testerId) {
		if (testerId == null || testerId.trim().isEmpty())
			return Collections.emptyList();
		// 1st attempt: look for exact match
		final ITesterDescriptor td = testerRegistry.getDescriptors().get(testerId);
		if (td != null)
			return Collections.singletonList(td);

		logger.warn("Could not find tester by ID: " + testerId + ", switching to fuzzy search.");

		// 2nd attempt: look for sloppy match (but full segments required!)
		final int t_len = testerId.length();
		final List<ITesterDescriptor> matchingTDs = testerRegistry.getDescriptors().values().stream()
				.map(descriptor -> descriptor.getId())
				.filter(id -> {
					final int id_len = id.length();
					return id_len >= t_len
							// a) id ends with runnerId (ignore case)
							&& id.substring(id_len - t_len, id_len).equalsIgnoreCase(testerId)
					// b) full segment match (either full string or previous char is .)
							&& (id_len == t_len || id.charAt(id_len - t_len - 1) == '.');
				})
				.peek(id -> logger.debug("Candidate tester ID: " + id))
				.map(id -> testerRegistry.getDescriptor(id))
				.collect(Collectors.toList());
		return matchingTDs;
	}

}
