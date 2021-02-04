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
package org.eclipse.n4js.ide.tests.helper.server.xt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.AbstractStructuredIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.Position;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.xpect.runner.Xpect;

import com.google.common.base.Preconditions;

/**
 *
 */
public class XtIdeTest extends AbstractIdeTest {
	static final String CURSOR = AbstractStructuredIdeTest.CURSOR_SYMBOL;

	XtFileData xtData;

	/**
	 */
	public void initializeXtFile(XtFileData newXtData) throws IOException {
		Preconditions.checkNotNull(newXtData);
		xtData = newXtData;

		cleanupTestDataFolder();
		testWorkspaceManager.createTestOnDisk(xtData.project);

		for (MethodData startupMethod : xtData.startupMethodData) {
			switch (startupMethod.name) {
			case "startAndWaitForLspServer":
				startAndWaitForLspServer();
				break;
			default:
				throw new IllegalArgumentException("Unknown method: " + startupMethod.name);
			}
		}
	}

	/**
	 */
	public void invokeTestMethod(MethodData testMethodData) throws InterruptedException, ExecutionException {
		switch (testMethodData.name) {
		case "definition":
			definition(testMethodData);
			break;
		default:
			throw new IllegalArgumentException("Unknown method: " + testMethodData.name);
		}

	}

	/** 	 */
	public void teardown() throws IOException {
		deleteTestProject();
	}

	/** Calls LSP endpoint 'definition'. Converts {@link MethodData} to inputs and compares outputs to expectations. */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void definition(MethodData data) throws InterruptedException, ExecutionException {
		Preconditions.checkArgument(data.name.equals("definition"));
		Preconditions.checkArgument(data.args.length > 1);
		Preconditions.checkArgument(data.args[0].equals("at"));
		Preconditions.checkArgument(data.args[1].startsWith("'"));
		Preconditions.checkArgument(data.args[1].endsWith("'"));
		String locationStr = data.args[1].substring(1, data.args[1].length() - 2);
		int relLocationIdx = locationStr.contains(CURSOR) ? locationStr.indexOf(CURSOR) : 0;
		locationStr = locationStr.replace(CURSOR, "");
		int locationIdx = xtData.content.indexOf(locationStr, data.offset) + relLocationIdx;
		Position position = xtData.getPosition(locationIdx);
		FileURI uri = getFileURIFromModuleName(xtData.getModuleName());

		CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> future = callDefinition(
				uri.toString(), position.line, position.column);

		Either<List<? extends Location>, List<? extends LocationLink>> definitions = future.get();

		String actualSignatureHelp = getStringLSP4J().toString4(definitions);
		assertEquals(data.expectation, actualSignatureHelp.trim());
	}
}
