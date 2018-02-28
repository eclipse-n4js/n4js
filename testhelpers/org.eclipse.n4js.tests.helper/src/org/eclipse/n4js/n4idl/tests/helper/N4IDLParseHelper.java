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
package org.eclipse.n4js.n4idl.tests.helper;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.util.ResourceHelper;
import org.junit.Assert;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

/**
 * Replacement for ParseHelper in order to define mode in which file is parsed, also see {@link N4IDLParseHelper}.
 */
public class N4IDLParseHelper extends ParseHelper<Script> {

	@Inject
	private ResourceHelper resourceHelper;

	/**
	 * Parses n4idl file (using n4idl as file extension).
	 */
	public Script parseN4IDL(CharSequence text) throws Exception {
		setFileExtension("n4idl");
		Script script = parse(text);
		return script;
	}

	/**
	 * Asserts that the given script does not have any parse errors. Checks only for parse errors, does not perform a
	 * validation of the given script.
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
		this.setFileExtensionProvider(dummy);
		resourceHelper.setFileExtensionProvider(dummy);
	}

}
