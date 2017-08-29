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
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.hlc.base.ExitCodeException;
import org.eclipse.n4js.hlc.base.running.RunnableLookupHelper;
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

	@Inject
	RunnableLookupHelper runnerLookup;

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
		final List<ITesterDescriptor> matchingTesterDescs = runnerLookup.<ITesterDescriptor> findRunnableById(
				tester,
				testerRegistry.getDescriptors());

		if (matchingTesterDescs.isEmpty()) {
			throw new ExitCodeException(EXITCODE_TESTER_NOT_FOUND, "no tester found for id: " + tester);
		} else if (matchingTesterDescs.size() > 1) {
			throw new ExitCodeException(EXITCODE_TESTER_NOT_FOUND, "several testers match given id \"" + tester
					+ "\":\n\t" + Joiner.on("\n\t").join(matchingTesterDescs));
		}

		return matchingTesterDescs.get(0);
	}

}
