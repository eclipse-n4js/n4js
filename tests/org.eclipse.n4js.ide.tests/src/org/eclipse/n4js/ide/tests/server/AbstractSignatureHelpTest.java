/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ide.tests.server;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureInformation;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.testing.SignatureHelpConfiguration;
import org.junit.Assert;

/**
 * Signature help test class
 */
abstract public class AbstractSignatureHelpTest extends AbstractIdeTest<SignatureHelpConfiguration> {

	protected void test(SignatureHelpConfiguration shc) throws Exception {
		test(shc.getFilePath(), shc.getModel(), shc);
	}

	@Override
	protected void performTest(File root, Project project, SignatureHelpConfiguration shc)
			throws InterruptedException, ExecutionException {

		TextDocumentPositionParams textDocumentPositionParams = new TextDocumentPositionParams();
		String completeFileUri = getFileUriFromModuleName(root, shc.getFilePath()).toString();
		textDocumentPositionParams.setTextDocument(new TextDocumentIdentifier(completeFileUri));
		textDocumentPositionParams.setPosition(new Position(shc.getLine(), shc.getColumn()));
		CompletableFuture<SignatureHelp> signatureHelpFuture = languageServer.signatureHelp(textDocumentPositionParams);

		SignatureHelp signatureHelp = signatureHelpFuture.get();
		if (shc.getAssertSignatureHelp() != null) {
			shc.getAssertSignatureHelp().apply(signatureHelp);
		} else {
			String actualSignatureHelp = toExpectation(signatureHelp);
			assertEquals(shc.getExpectedSignatureHelp().trim(), actualSignatureHelp.trim());
		}
	}

	private String toExpectation(SignatureHelp signatureHelp) {
		Integer activeSignature = signatureHelp.getActiveSignature();
		List<SignatureInformation> signatures = signatureHelp.getSignatures();

		if (signatures.size() == 0) {
			Assert.assertNull(
					"Signature index is expected to be null when no signatures are available. Was: " + activeSignature,
					activeSignature);
			return "<empty>";
		}
		Assert.assertNotNull("Active signature index must not be null when signatures are available.", activeSignature);

		Integer activeParameter = signatureHelp.getActiveParameter();
		String param = (activeParameter == null) ? "<empty>"
				: signatures.get(activeSignature).getParameters().get(activeParameter).getLabel().getLeft();

		String allSignatureStr = signatures.stream().map(s -> s.getLabel()).reduce("", (a, b) -> a + " | " + b);
		return allSignatureStr + param;
	}

}
