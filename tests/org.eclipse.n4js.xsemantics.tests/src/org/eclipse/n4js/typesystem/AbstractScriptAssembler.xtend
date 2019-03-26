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
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.validation.JavaScriptVariant
import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper

import static org.junit.Assert.*
import java.util.Arrays

/**
 * Helper for type system tests, base class for assembler classes generating script under test.
 */
abstract class AbstractScriptAssembler {

	@Inject
	extension ParseHelper<Script>

	@Inject
	extension ValidationTestHelper

	// set in setupTest / setupScript
	Script script;
	Map<String, TypeRef> typeRefByName;

	CharSequence scriptPrefix = "";

	/**
	 * Get the type reference for a given type expression, requires  {@link #prepareScriptAndCreateRuleEnvironment(TypeRefs...)}
	 * to having been called before.
	 */
	def TypeRef getTypeRef(String typeExpression) {
		val typeRef = typeRefByName.get(createVarNameForTypeRef(typeExpression));
		if (typeRef === null) {
			throw new IllegalArgumentException(
				"no variable for type expression " + typeExpression + " found in test script")
		}
		typeRef;
	}

	protected def createVariables(String... typeExpressions) {
		val Map<String, Integer> nameCount = new HashMap

		typeExpressions.filter[! startsWith("local")]
		//map to Type->Name pair
		.map[it->it]
		//transform Type->Name pairs to Type->Name pairs, where names are unique
		.map[
			var name = it.value
			if(nameCount.containsKey(name) == false){
				nameCount.put(name, 0)
				return it.key->name
			}else{
				var v = nameCount.get(name).intValue
				nameCount.put(name, v+1)
				return it.key->name+v
			}
		].map["var " + createVarNameForTypeRef(it.value) + ": " +it.key + ";"].join("\n");
	}

	protected def createVarNameForTypeRef(String s) {
		if (s.startsWith("local")) {
			s;
		} else {
			"_" + s.replaceAll("\\W", "_");
		}
	}

	protected def setupScript(CharSequence scriptSrc, JavaScriptVariant variant, int expectedIssueCount) {
		return setupScript(scriptSrc, variant, null, expectedIssueCount);
	}

	protected def setupScript(CharSequence scriptSrc, JavaScriptVariant variant, String[] expectedMessages) {
		return setupScript(scriptSrc, variant, expectedMessages, -1);
	}

	protected def setupScript(CharSequence scriptSrc, JavaScriptVariant variant, String[] expectedMessages, int expectedIssueCount) {
		script = createScript(scriptSrc, variant)
		val issues = script.validate();

		if (expectedMessages === null) {
			assertEquals(Arrays.toString(issues.toArray) + "\nin\n" + scriptSrc, expectedIssueCount, issues.size);
		} else {
			assertEquals(String.join(",", expectedMessages), issues.map[code].join(","));
		}

		// newly created top level vars and
		// nested variables starting with "local":
		val vars = script.scriptElements.filter(VariableStatement) +
			EcoreUtil2.eAllOfType(script, VariableStatement).filter[varDecl.head.name.startsWith("local")]
		typeRefByName = newHashMap();
		for (varDecl : vars.map[it.varDecl].flatten) {
			typeRefByName.put(varDecl.name, varDecl.declaredTypeRef)
		}
		return script;
	}

	def Script createScript(CharSequence src, JavaScriptVariant variant) {
		switch (variant) {
			case JavaScriptVariant.n4js: {
				val oldFileExt = fileExtension;
				fileExtension = "n4js";
				val script = src.parse();
				fileExtension = oldFileExt;
				script;
			}
			case JavaScriptVariant.strict: {
				val oldFileExt = fileExtension;
				fileExtension = "js";
				val script = ('''
						"strict mode"
				'''+src).parse();
				fileExtension = oldFileExt;
				script;
			}
			default: {
				val oldFileExt = fileExtension;
				fileExtension = "js";
				val script = src.parse();
				fileExtension = oldFileExt;
				script;
			}
		}
	}

	/**
	 * Usually called in the test's before method in order to set
	 * the script prefix with the type definitions (used later in the
	 * type expressios).
	 */
	public def void setScriptPrefix(CharSequence scriptPrefix) {
		this.scriptPrefix = scriptPrefix;
	}

	public def getScriptPrefix() {
		return this.scriptPrefix;
	}

	public def getScript() {
		return this.script;
	}

}
