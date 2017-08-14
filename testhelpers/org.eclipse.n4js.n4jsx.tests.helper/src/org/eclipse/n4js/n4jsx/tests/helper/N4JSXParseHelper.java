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
package org.eclipse.n4js.n4jsx.tests.helper;

import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.util.ResourceHelper;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.junit.Assert;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.n4JS.Script;

/**
 * Replacement for ParseHelper in order to define mode in which file is parsed,
 * also see {@link N4JSParseHelper}.
 */
public class N4JSXParseHelper extends ParseHelper<Script> {

	@Inject
	private ResourceHelper resourceHelper;

	/**
	 * Parses n4jsx file (using n4jsx as file extension).
	 */
	public Script parseN4JSX(CharSequence text) throws Exception {
		setFileExtension("n4jsx");
		Script script = parse(text);
		return script;
	}

	/**
	 * Asserts that the given script does not have any parse errors. Checks only
	 * for parse errors, does not perform a validation of the given script.
	 */
	public void assertNoParseErrors(Script script) {
		Assert.assertTrue(Joiner.on('\n').join(script.eResource().getErrors()),
				script.eResource().getErrors().isEmpty());
	}

	private void setFileExtension(String ext) {
		FileExtensionProvider dummy = new FileExtensionProvider() {
			{
				setExtensions(ext);
			}
		};
		resourceHelper.setFileExtensionProvider(dummy);
	}

}
