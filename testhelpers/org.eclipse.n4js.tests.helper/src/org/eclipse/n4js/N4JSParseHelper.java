/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.testing.util.ResourceHelper
 *	in bundle org.eclipse.xtext.testing
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.util.ResourceHelper;
import org.junit.Assert;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

/**
 * Replacement for ParseHelper in order to define mode in which file is parsed.
 */
public class N4JSParseHelper extends ParseHelper<Script> {

	@Inject
	private ResourceHelper resourceHelper;
	@Inject
	private FileExtensionProvider fileExtensionProvider;

	/**
	 * Convenience method, delegates to {@link #parse(CharSequence, JavaScriptVariant) parse(CharSequence,
	 * JavaScriptVariant.unrestricted)}.
	 */
	public Script parseUnrestricted(CharSequence text) throws Exception {
		return parse(text, JavaScriptVariant.unrestricted);
	}

	/**
	 * Convenience method, delegates to {@link #parse(CharSequence, JavaScriptVariant) parse(CharSequence,
	 * JavaScriptVariant.strict)}.
	 */
	public Script parseStrict(CharSequence text) throws Exception {
		return parse(text, JavaScriptVariant.strict);
	}

	/**
	 * Convenience method, delegates to {@link #parse(CharSequence, JavaScriptVariant) parse(CharSequence,
	 * JavaScriptVariant.n4js)}.
	 */
	public Script parseN4js(CharSequence text) throws Exception {
		return parse(text, JavaScriptVariant.n4js);
	}

	/**
	 * Preferred way of parsing a JavaScript of N4JS file, as it defines the correct mode in which the script is parsed.
	 * This is important for tests checking for different validation and this-binginds.
	 */
	public Script parse(CharSequence text, JavaScriptVariant variant) throws Exception {
		try {
			switch (variant) {
			case external: {
				setFileExtension("n4jsd");
				Script script = parse(text);
				return script;
			}
			case n4js: {
				setFileExtension("n4js");
				Script script = parse(text);
				return script;
			}
			case strict: {
				setFileExtension("js");
				Script script = parse("\"scrict mode\"\n" + text);
				return script;
			}
			case n4idl: {
				setFileExtension(N4IDLGlobals.N4IDL_FILE_EXTENSION);
				Script script = parse(text);
				return script;
			}
			case unrestricted:
			default: {
				setFileExtension("js");
				Script script = parse(text);
				return script;
			}

			}
		} finally {
			resourceHelper.setFileExtensionProvider(fileExtensionProvider);
		}
	}

	@Override
	protected URI computeUnusedUri(ResourceSet resourceSet) {
		/*
		 * Copied from {@link ResourceHelper} since the super class ParseHelper has a bug in that it does not set the
		 * file extensions before computing an unused URI. This results in an offset setting of the file extension after
		 * the parsing already took place and thus triggers the wrong set of validations (wrong variant).
		 */
		String name = "__synthetic";
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			URI syntheticUri = URI.createURI(name + i + "." + fileExtension);
			if (resourceSet.getResource(syntheticUri, false) == null)
				return syntheticUri;
		}
		throw new IllegalStateException();
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
		resourceHelper.setFileExtensionProvider(dummy);
		this.fileExtension = ext;
	}

}
