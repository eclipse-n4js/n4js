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
package org.eclipse.n4js;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.util.LazyStringInputStream;

import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;

import org.eclipse.n4js.n4JS.Script;


public class SmokeTestWriter extends N4JSParseHelper {

	
	public static boolean active = Boolean.getBoolean("SmokeTestWriter");

	static int counter = 1;

	static Set<String> seen = Sets.newHashSet();

	@Override
	public Script parse(InputStream in, URI uriToUse, Map<?, ?> options, ResourceSet resourceSet) {
		if (active) {
			if (in instanceof LazyStringInputStream) {
				try {
					String string = ((LazyStringInputStream) in).getString();
					if (string.length() < 1000 && seen.add(string)) {
						List<String> lines = CharStreams.readLines(new StringReader(string));
						if (lines.size() < 50) {
							System.out.println("\t@Test");
							System.out.format("\tdef void test_%04d() {", counter++);
							System.out.println();
							System.out.println("\t\t'''");
							for (String s : lines) {
								System.out.print("\t\t\t");
								System.out.println(s);
							}
							System.out.println("\t\t'''.assertNoException");
							System.out.println("\t}");
							System.out.println();
						}
					}
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return super.parse(in, uriToUse, options, resourceSet);
	}
}
