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
package org.eclipse.n4js.jsdoc2spec.adoc;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests {@link Html2ADocConverter#transformHTML(CharSequence)}.
 */
@RunWith(Parameterized.class)

public class Html2ADocConverterTest {

	/**
	 * Returns test data.
	 */
	@Parameters(name = "html2ADoc Converter Test {index}: \"{1}\"")
	public static Collection<Object[]> data() {
		return List.of(
				new String[] { "", "" },
				new String[] { "x", "x" },
				new String[] { "A simple sentence.", "A simple sentence." },
				new String[] { "&", "&" },
				new String[] { "&x", "&x" },
				new String[] { "<", "&lt;" },
				new String[] { "&", "&amp;" },
				new String[] { "x&y", "x&amp;y" },
				new String[] { "<", "<" },
				new String[] { "<x", "<x" },
				new String[] { "union{x,y}", "union{x,y}" },
				new String[] { "* {}", "<li>{}" },
				new String[] { "\n\n", "<br>" },
				new String[] { "\n\n", "<br/>" },
				new String[] { "\n\n", "</br>" },
				new String[] { "++{++}", "<pre>{</pre>}" },
				new String[] { "\n[source]\n--\n{\n--\n}", "<pre>\n{</pre>}" },
				new String[] { "++<++}", "<pre><</pre>}" },
				new String[] { "++>++}", "<pre>></pre>}" },
				new String[] { "++x<y++}", "<pre>x<y</pre>}" },
				new String[] { "++x>y++}", "<pre>x>y</pre>}" },
				new String[] { "++{++}", "<pre class=\"pseudo\">{</pre>}" },
				new String[] { "``++hello++``", "<code>hello</code>" },
				new String[] { "``++if (start < 0): start = size() - |start|++``",
						"<code>if (start < 0): start = size() - |start|</code>" },
				new String[] { "****", "<b></b>" },
				new String[] { "**b**", "<b>b</b>" },
				new String[] { "**__bi__**", "<b><i>bi</i></b>" },
				new String[] { "y**x**z", "y<b>x</b>z" },
				new String[] { "Hello $\\forall$!", "Hello <tex>$\\forall$</tex>!" },
				new String[] { "``++x-y++``", "<code>x-y</code>" },
				new String[] { "\n[source,n4js]\n--\nx-y\n--\n", "<code>\nx-y</code>" },
				new String[] { "``++x<y++``", "<code>x<y</code>" },
				new String[] { "``++x>y++``", "<code>x>y</code>" },
				new String[] { "cite:[ECMA15a]", "<cite>ECMA15a</cite>" },
				new String[] { "cite:[ECMA15a(1.2.2)]", "<cite title=\"1.2.2\">ECMA15a</cite>" },
				new String[] { "<unknown>", "<unknown>" },
				new String[] { "</unknown>", "</unknown>" });
	}

	Html2ADocConverter html2ADocConverter = new Html2ADocConverter();
	/** The expected AsciiDoc output */
	@Parameter(value = 0)
	public String expected;
	/** The JSDoc snippet */
	@Parameter(value = 1)
	public String input;

	@Test
	public void assertTransform() {
		String actual = html2ADocConverter.transformHTML(input).toString();
		Assert.assertEquals(expected, actual);
	}

}
