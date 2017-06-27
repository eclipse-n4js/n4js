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
package org.eclipse.n4js.jsdoc2spec.adoc

import java.util.Collection
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.util.Arrays
import org.junit.runners.Parameterized.Parameter
import org.junit.Assert

/**
 * Tests {@link Html2ADocConverter#transformHTML(CharSequence).
 */
@RunWith(Parameterized)
public class Html2ADocConverterTest {

	/**
	 * Returns test data.
	 */
	@Parameters(name="html2ADoc Converter Test {index}: \"{1}\"")
	def static Collection<Object[]> data() {
		return Arrays.asList(#[
			#["", ""],
			#["x", "x"],
			#["A simple sentence.", "A simple sentence."],
			#["&", "&"],
			#["&x", "&x"],
			#["<", "&lt;"],
			#["&", "&amp;"],
			#["x&y", "x&amp;y"],
			#["<", "<"],
			#["<x", "<x"],
			#["union{x,y}", "union{x,y}"],
			#["* {}", "<li>{}"],
			#["\n\n", "<br>"],
			#["\n\n", "<br/>"],
			#["\n\n", "</br>"],
			#["++{++}", "<pre>{</pre>}"],
			#["\n[source]\n--\n{\n--\n}", "<pre>\n{</pre>}"],
			#["++<++}", "<pre><</pre>}"],
			#["++>++}", "<pre>></pre>}"],
			#["++x<y++}", "<pre>x<y</pre>}"],
			#["++x>y++}", "<pre>x>y</pre>}"],
			#["++{++}", "<pre class=\"pseudo\">{</pre>}"],
			#["``++hello++``", "<code>hello</code>"],
			#["``++if (start < 0): start = size() - |start|++``", "<code>if (start < 0): start = size() - |start|</code>"],
			#["****", "<b></b>"],
			#["**b**", "<b>b</b>"],
			#["**__bi__**", "<b><i>bi</i></b>"],
			#["y**x**z", "y<b>x</b>z"],
			#["Hello $\\forall$!", "Hello <tex>$\\forall$</tex>!"],
			#["``++x-y++``", "<code>x-y</code>"],
			#["\n[source,n4js]\n--\nx-y\n--\n", "<code>\nx-y</code>"],
			#["``++x<y++``", "<code>x<y</code>"],
			#["``++x>y++``", "<code>x>y</code>"],
			#["cite:[ECMA15a]", "<cite>ECMA15a</cite>"],
			#["cite:[ECMA15a(1.2.2)]", "<cite title=\"1.2.2\">ECMA15a</cite>"],
			#["<unknown>", "<unknown>"],
			#["</unknown>", "</unknown>"]
		]);
	}

	Html2ADocConverter html2ADocConverter = new Html2ADocConverter();
	/** The expected AsciiDoc output */
	@Parameter(value=0)
	public var String expected;
	/** The JSDoc snippet */
	@Parameter(value=1)
	public var String input;

	@Test
	public def void assertTransform() {
		val actual = html2ADocConverter.transformHTML(input).toString();
		Assert.assertEquals(expected, actual);
	}

}
