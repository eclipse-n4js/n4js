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
package org.eclipse.n4js.npmexporter

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import static extension org.junit.Assert.*

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class PackageJasonTemplateTest {

		@Inject
		PackageJsonTemplate pack;

		@Test
		def void templateSimple() {
				val out = setupData();
				val expected = '''
				{
				  "name" : "project",
				  "version" : "0.0.1",
				  "description" : "Describe some thing.",
				  "main" : "index.js",
				  "scripts" : {
				    "test" : "echo('done')"
				  },
				  "author" : "Me.",
				  "license" : "ABC"
				}''';
				assertEquals(expected,out);
		}

		/** Reads in a real-world package-json and writes it back. */
		@Test
		def void readWriteTemplate() {
			val generatedDataJson = setupData;
			val collectedRejections = newArrayList();
			val (String)=>void messageConsumer = [String msg| collectedRejections+= msg ];
			val merged = PackageJsonTemplate.merge(generatedDataJson, testDataComplexExisting,messageConsumer);

			"Name should not be overwritten".assertTrue( merged.indexOf("\"name\" : \"project\"") > 0);
			"Other name should be overwritten".assertFalse( merged.indexOf("\"name\" : \"undertest\"") > 0);

			assertEquals( "Expected number of rejections", 7, collectedRejections.size  )
			assertEquals( "Expect the rejection message.","rejected existing property scripts.test, rejected value: \"tap test/*.js\"", collectedRejections.get(4));

		}

		@Test
		def void doesNotWriteNullValues() {
			// only the name is set:
			val data = new PackageJsonData=>[name="Xy"];
			val out = pack.generateTemplate(data);
			val expected = '''
			{
			  "name" : "Xy"
			}''';
			assertEquals(expected,out);
		}



		/////////// TEST DATA BELOW //////////////////
		private def setupData(){
			 return pack.generateTemplate(
						 new PackageJsonData => [
							name = "project";
							version = "0.0.1";
							description = "Describe some thing."
							main="index.js"
							scripts= new PackageJsonData.Scripts()=>[ test = "echo('done')" ]
							author="Me."
							license="ABC"
						]
				);
		}


		val testDataComplexExisting='''
		{
		  "name": "undertest",
		  "version": "0.3.0",
		  "description": "Helps a module find its package.json undertest.",
		  "main": "index.js",
		  "scripts": {
		    "test": "tap test/*.js"
		  },
		  "repository": {
		    "type": "git",
		    "url": "git://github.com/xyxyxy/undertest.git"
		  },
		  "homepage": "https://github.com/xyxyxy/undertest",
		  "dependencies": {
		    "find-parent-dir": "~0.3.0"
		  },
		  "devDependencies": {
		    "tap": "~0.4.3"
		  },
		  "keywords": [
		    "find",
		    "resolve",
		    "package.json",
		    "condition",
		    "predicate",
		    "root"
		  ],
		  "author": {
		    "name": "Xxx Yyy",
		    "email": "xyxyxy@gmx.de",
		    "url": "http://xyxyxy.com"
		  },
		  "license": {
		    "type": "MIT",
		    "url": "https://github.com/xyxyxy/undertest/blob/master/LICENSE"
		  },
		  "engine": {
		    "node": ">=0.6"
		  }
		}
		'''




}
