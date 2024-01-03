/**
 * Copyright (c) 2024 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Multimap;

/**
 *
 */
public class YamlUtilTest {

	@Test
	public void testPnpmWorkspaceYaml() {
		Multimap<String, String> map = YamlUtil.loadYamlFromString("""
				# NOTE: Experimental, local packages from the workspace are preferred over packages from the registry
				prefer-workspace-packages: true
				packages:
				  - 'packages/*'
				""");

		String result = Strings.join("\n", e -> e.getKey() + " => " + e.getValue(), map.entries());

		Assert.assertEquals("""
				prefer-workspace-packages => true
				packages => packages/*""", result);
	}

	@Test
	public void testYaml1() {
		Multimap<String, String> map = YamlUtil.loadYamlFromString("""
				---
				# A sample yaml file
				company: spacelift
				domain:
				 - devops
				 - devsecops
				tutorial:
				  - yaml:
				      name: "YAML Ain't Markup Language"
				      type: awesome
				      born: 2001
				  - json:
				      name: JavaScript Object Notation
				      type: great
				      born: 2001
				  - xml:
				      name: Extensible Markup Language
				      type: good
				      born: 1996
				author: omkarbirade
				published: true
				""");

		String result = Strings.join("\n", e -> e.getKey() + " => " + e.getValue(), map.entries());

		Assert.assertEquals("""
				company => spacelift
				domain => devops
				domain => devsecops
				tutorial:yaml:name => YAML Ain't Markup Language
				tutorial:yaml:type => awesome
				tutorial:yaml:born => 2001
				tutorial:json:name => JavaScript Object Notation
				tutorial:json:type => great
				tutorial:json:born => 2001
				tutorial:xml:name => Extensible Markup Language
				tutorial:xml:type => good
				tutorial:xml:born => 1996
				author => omkarbirade
				published => true""", result);
	}

	@Test
	public void testYaml1WithComments() {
		Multimap<String, String> map = YamlUtil.loadYamlFromString("""
				---
				# key: value [mapping]
				company: spacelift
				# key: value is an array [sequence]
				domain:
				 - devops
				 - devsecops
				tutorial:
				  - yaml:
				      name: "YAML Ain't Markup Language" #string [literal]
				      type: awesome #string [literal]
				      born: 2001 #number [literal]
				  - json:
				      name: JavaScript Object Notation #string [literal]
				      type: great #string [literal]
				      born: 2001 #number [literal]
				  - xml:
				      name: Extensible Markup Language #string [literal]
				      type: good #string [literal]
				      born: 1996 #number [literal]
				author: omkarbirade
				published: true
				""");

		String result = Strings.join("\n", e -> e.getKey() + " => " + e.getValue(), map.entries());

		Assert.assertEquals("""
				company => spacelift
				domain => devops
				domain => devsecops
				tutorial:yaml:name => YAML Ain't Markup Language
				tutorial:yaml:type => awesome
				tutorial:yaml:born => 2001
				tutorial:json:name => JavaScript Object Notation
				tutorial:json:type => great
				tutorial:json:born => 2001
				tutorial:xml:name => Extensible Markup Language
				tutorial:xml:type => good
				tutorial:xml:born => 1996
				author => omkarbirade
				published => true""", result);
	}

	/** Actually this is not correct since the multi line string should be a single entry */
	@Test
	public void testYamlSimpleMultiline() {
		Multimap<String, String> map = YamlUtil.loadYamlFromString("""
				message: this is
				 a real multiline
				 message
				""");

		String result = Strings.join("\n", e -> e.getKey() + " => " + e.getValue(), map.entries());

		Assert.assertEquals("""
				message => this is
				message => a real multiline
				message => message""", result);
	}

	@Test
	public void testYamlBacktrack() {
		Multimap<String, String> map = YamlUtil.loadYamlFromString("""
				tutorial:
				  - yaml:
				    - one: empty
				    - two: empty
				  - json: empty
				  - xml: empty
				""");

		String result = Strings.join("\n", e -> e.getKey() + " => " + e.getValue(), map.entries());

		Assert.assertEquals("""
				tutorial:yaml:one => empty
				tutorial:yaml:two => empty
				tutorial:json => empty
				tutorial:xml => empty""", result);
	}
}
