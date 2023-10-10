/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.testing.util.ParseHelper;

import com.google.inject.Singleton;

/**
 * A parse helper for JSON parsing tests.
 */
@Singleton
public class JSONParseHelper extends ParseHelper<JSONDocument> {

	/**
	 * Asserts that the given {@code json} character sequence can be parsed correctly. Returns the resulting
	 * {@link JSONDocument} instance.
	 */
	public JSONDocument parseSuccessfully(CharSequence json) throws Exception {
		JSONDocument doc = parse(json);
		List<Diagnostic> errors = doc.eResource().getErrors();
		String msg = "\"" + json + "\"" + Strings.join("\n", err -> err.getLine() + ": " + err.getMessage(), errors);
		assertTrue(msg, !errors.iterator().hasNext());
		return doc;
	}

	/**
	 * Asserts that the given {@code json} character sequences cannot be parsed correctly.
	 */
	public void parseUnsuccessfully(CharSequence json) throws Exception {
		JSONDocument doc = parse(json);
		Iterable<XtextSyntaxDiagnostic> errs = filter(doc.eResource().getErrors(), XtextSyntaxDiagnostic.class);
		assertFalse("The following JSON text did not cause any syntax errors as expected: \"" + json + "\"",
				!errs.iterator().hasNext());
	}
}
