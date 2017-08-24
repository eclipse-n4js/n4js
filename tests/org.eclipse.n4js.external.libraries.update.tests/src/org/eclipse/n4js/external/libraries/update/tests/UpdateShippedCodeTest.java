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
package org.eclipse.n4js.external.libraries.update.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;
import org.eclipse.n4js.external.libraries.update.UpdateShippedCode;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.utils.io.IDirectoryDiffAcceptor.CollectingDirectoryDiffAcceptor;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Optional;

/**
 * See {@link #ensureShippedCodeIsUpToDate()}.
 */
@Ignore("IDE-2844")
public class UpdateShippedCodeTest {

	private static final String MY_NAME = UpdateShippedCodeTest.class.getName();

	/**
	 * Test to ensure the checked-in code in folder "shipped-code" of bundle "org.eclipse.n4js.external.libraries" is
	 * up-to-date. If this test fails, run MWE2 work flow "UpdateShippedCode.mwe2" and commit the changes.
	 */
	@Test
	public void ensureShippedCodeIsUpToDate() throws IOException {
		println("TEST STARTING: " + MY_NAME);

		// update shipped code into a temporary target folder
		final File tempTargetFolder = new File("target/updateShippedCodeTest/"
				+ ExternalLibrariesActivator.SHIPPED_CODE_FOLDER_NAME);
		tempTargetFolder.mkdirs();
		println("START of running UpdateShippedCode into temporary folder " + tempTargetFolder.getAbsolutePath());
		UpdateShippedCode.updateShippedCode(Optional.of(tempTargetFolder.toPath()));
		println("END of running UpdateShippedCode");

		// compare official, checked-in shipped code to what we just created in the temporary target folder
		final CollectingDirectoryDiffAcceptor diff = new CollectingDirectoryDiffAcceptor();
		final Path shippedCodeFolderPath = ExternalLibrariesActivator.getShippedCodeFolderPath();
		println("START of comparing folders:\n"
				+ "    EXPECTED shipped code: " + tempTargetFolder.getAbsolutePath() + "\n"
				+ "    ACTUAL shipped code  : " + shippedCodeFolderPath);
		FileUtils.compareDirectories(tempTargetFolder, shippedCodeFolderPath.toFile(), diff);
		println("END of comparing folders");

		// complain & fail if we found differences
		if (!diff.isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			sb.append("TEST " + MY_NAME
					+ " FAILED: shipped code does not seem to be up-to-date; run work flow UpdateShippedCode.mwe2!\n");
			sb.append("Differences found in the checked-in shipped code:\n");
			sb.append(diff);
			final String msg = sb.toString();
			println(msg); // log differences for easier debugging
			Assert.fail(msg);
		} else {
			println("No differences found.");
			println("TEST COMPLETED SUCCESSFULLY: " + MY_NAME);
		}
	}

	/**
	 * Don't use a log4j logger here. See {@link UpdateShippedCode#println(String)} for rationale.
	 */
	private static void println(String message) {
		System.out.println(message);
	}
}
