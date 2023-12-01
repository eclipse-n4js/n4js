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
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.Conversions.doWrapArray;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;

import com.google.inject.Inject;

/**
 * Helper for type system tests, base class for assembler classes generating script under test.
 */
abstract public class AbstractScriptAssembler {

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	ValidationTestHelper valTestHelper;

	// set in setupTest / setupScript
	Script script;
	Map<String, TypeRef> typeRefByName;

	CharSequence scriptPrefix = "";

	/**
	 * Get the type reference for a given type expression, requires
	 * {@link TypeRefsToVariablesAssembler#prepareScriptAndCreateRuleEnvironment(String...)} to having been called
	 * before.
	 */
	public TypeRef getTypeRef(String typeExpression) {
		TypeRef typeRef = typeRefByName.get(createVarNameForTypeRef(typeExpression));
		if (typeRef == null) {
			throw new IllegalArgumentException(
					"no variable for type expression " + typeExpression + " found in test script");
		}
		return typeRef;
	}

	@SuppressWarnings("unchecked")
	protected String createVariables(final String... typeExpressions) {
		String _xblockexpression = null;
		final Map<String, Integer> nameCount = new HashMap<>();
		final Function1<String, Boolean> _function = (String it) -> {
			boolean _startsWith = it.startsWith("local");
			return Boolean.valueOf((!_startsWith));
		};
		final Function1<String, Pair<String, String>> _function_1 = (String it) -> {
			return Pair.<String, String> of(it, it);
		};
		final Function1<Pair<String, String>, Pair<String, String>> _function_2 = (Pair<String, String> it) -> {
			String name = it.getValue();
			boolean _containsKey = nameCount.containsKey(name);
			boolean _equals = (_containsKey == false);
			if (_equals) {
				nameCount.put(name, Integer.valueOf(0));
				String _key = it.getKey();
				return Pair.<String, String> of(_key, name);
			} else {
				int v = nameCount.get(name).intValue();
				nameCount.put(name, Integer.valueOf((v + 1)));
				String _key_1 = it.getKey();
				return Pair.<String, String> of(_key_1, (name + Integer.valueOf(v)));
			}
		};
		final Function1<Pair<String, String>, String> _function_3 = (Pair<String, String> it) -> {
			String _createVarNameForTypeRef = this.createVarNameForTypeRef(it.getValue());
			String _plus = ("var " + _createVarNameForTypeRef);
			String _plus_1 = (_plus + ": ");
			String _key = it.getKey();
			String _plus_2 = (_plus_1 + _key);
			return (_plus_2 + ";");
		};
		_xblockexpression = join(map(map(map(filter(((Iterable<String>) doWrapArray(typeExpressions)),
				_function),
				_function_1),
				_function_2),
				_function_3),
				"\n");
		return _xblockexpression;
	}

	protected String createVarNameForTypeRef(String s) {
		if (s.startsWith("local")) {
			return s;
		} else {
			return "_" + s.replaceAll("\\W", "_");
		}
	}

	protected Script setupScript(CharSequence scriptSrc, JavaScriptVariant variant, int expectedIssueCount) {
		return setupScript(scriptSrc, variant, null, expectedIssueCount);
	}

	protected Script setupScript(CharSequence scriptSrc, JavaScriptVariant variant, IssueCodes[] expectedMessages) {
		return setupScript(scriptSrc, variant, expectedMessages, -1);
	}

	protected Script setupScript(CharSequence scriptSrc, JavaScriptVariant variant, IssueCodes[] expectedMessages,
			int expectedIssueCount) {

		try {
			script = createScript(scriptSrc, variant);
			List<Issue> issues = valTestHelper.validate(script);

			if (expectedMessages == null) {
				assertEquals(join(",", map(issues, Issue::toString)) + "\nin\n" + scriptSrc, expectedIssueCount,
						issues.size());
			} else {
				assertEquals(Strings.join(",", ic -> ic.name(), expectedMessages),
						join(",", map(issues, Issue::getCode)));
			}

			// newly created top level vars and
			// nested variables starting with "local":
			List<VariableStatement> vars = new ArrayList<>();
			vars.addAll(toList(filter(script.getScriptElements(), VariableStatement.class)));
			vars.addAll(toList(filter(EcoreUtil2.eAllOfType(script, VariableStatement.class),
					vs -> vs.getVarDecl().get(0).getName().startsWith("local"))));

			typeRefByName = new HashMap<>();

			for (VariableDeclaration varDecl : flatten(map(vars, VariableStatement::getVarDecl))) {
				typeRefByName.put(varDecl.getName(), varDecl.getDeclaredTypeRef());
			}
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
			return null;
		}
	}

	@SuppressWarnings("hiding")
	Script createScript(CharSequence src, JavaScriptVariant variant) throws Exception {
		switch (variant) {
		case n4js: {
			String oldFileExt = parseHelper.fileExtension;
			parseHelper.fileExtension = "n4js";
			Script script = parseHelper.parse(src);
			parseHelper.fileExtension = oldFileExt;
			return script;
		}
		case strict: {
			String oldFileExt = parseHelper.fileExtension;
			parseHelper.fileExtension = "js";
			Script script = parseHelper.parse("""
					"strict mode"
					""" + src);
			parseHelper.fileExtension = oldFileExt;
			return script;
		}
		default: {
			String oldFileExt = parseHelper.fileExtension;
			parseHelper.fileExtension = "js";
			Script script = parseHelper.parse(src);
			parseHelper.fileExtension = oldFileExt;
			return script;
		}
		}
	}

	/**
	 * Usually called in the test's before method in order to set the script prefix with the type definitions (used
	 * later in the type expressions).
	 */
	public void setScriptPrefix(CharSequence scriptPrefix) {
		this.scriptPrefix = scriptPrefix;
	}

	public CharSequence getScriptPrefix() {
		return this.scriptPrefix;
	}

	public Script getScript() {
		return this.script;
	}

}
