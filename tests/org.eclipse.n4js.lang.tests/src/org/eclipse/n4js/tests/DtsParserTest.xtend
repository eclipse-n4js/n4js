/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests

import java.nio.file.FileVisitOption
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.eclipse.n4js.validation.ASTStructureValidator
import org.eclipse.n4js.validation.JavaScriptVariant
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

import static org.eclipse.n4js.validation.ASTStructureValidator.*

/**
 *
 */
class DtsParserTest extends AbstractParserTest {

	private Path FILE_TEST = Path.of("/Users/mark-oliver.reiser/Desktop/dtsParser/test.d.ts");
	private Path FILE_ES5 = Path.of("/Users/mark-oliver.reiser/Desktop/TTT-IDE-3540/ts-libs/es5.d.ts");
	private Path FILE_TEMP = Path.of("/Users/mark-oliver.reiser/Desktop/TTT-IDE-3540/ts-libs/es2020.intl.d.ts");
	private Path TYPE_SCRIPT_LIBS = Path.of("/Users/mark-oliver.reiser/Desktop/TTT-IDE-3540/ts-libs");
	private Path DEFINITELY_TYPED = Path.of("/Users/mark-oliver.reiser/Desktop/dtsParser/DefinitelyTyped/types");
	private Path NODE_API = Path.of("/Users/mark-oliver.reiser/Desktop/dtsParser/DefinitelyTyped/types/node");

	@BeforeClass
	def static void turnOffASTStructureValidator() {
		ASTStructureValidator.SUPPRESS_AST_STRUCTURE_VALIDATION = true;
	}

	@AfterClass
	def static void turnOnASTStructureValidator() {
		ASTStructureValidator.SUPPRESS_AST_STRUCTURE_VALIDATION = false;
	}

	@Test
	def void testSingleFile() {
//		val file = FILE_TEST;
//		val file = FILE_ES5;
		val file = FILE_TEMP;
		val code = Files.readString(file);
		val idx = code.indexOf("%%END");
		val codeTrimmed = if (idx>=0) code.substring(0, idx) else code;
		val script = codeTrimmed.parseN4jsdSuccessfully;
	}

	@Test
	def void testFolder() {
		val folder = TYPE_SCRIPT_LIBS;
		val files = Files.walk(folder, FileVisitOption.FOLLOW_LINKS).filter[fileName.toString.endsWith(".d.ts")].collect(Collectors.toList);
		for (file : files) {
			val code = Files.readString(file);
			val script = code.parse(JavaScriptVariant.external);
			val diags = script.eResource.errors;
			if (!diags.empty) {
				println("PARSE ERROR in " + file);
				println("    " + diags.map[line + ',' + column + ': ' + message].head)
			} else {
				println("ok: " + file);
			}
		}
	}
}
