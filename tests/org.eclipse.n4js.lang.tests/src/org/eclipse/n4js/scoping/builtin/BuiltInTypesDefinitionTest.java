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
package org.eclipse.n4js.scoping.builtin;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Ensures all predefined type definitions contain no parse/validation errors. This is also checked indirectly in other
 * tests, but this test better marks the initial error.
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class BuiltInTypesDefinitionTest {

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	ValidationTestHelper validationTestHelper;

	@Test
	public void validatePredefinedTypesScopeInitialized() {
		XtextResourceSet rs = resourceSetProvider.get();
		// trigger lazy computation
		BuiltInTypeScope.get(rs).getAllElements();
		GlobalObjectScope.get(rs).getAllElements();
		doValidatePredefinedTypes(rs, false);
	}

	@Test
	public void validatePredefinedTypesScopeNotInitialized() {
		XtextResourceSet rs = resourceSetProvider.get();
		doValidatePredefinedTypes(rs, true);
	}

	private void doValidatePredefinedTypes(XtextResourceSet rs, boolean loadOnDemand) {
		List<String> predefined = new ArrayList<>();
		predefined.addAll(Arrays.asList(BuiltInTypeScope.FILE_NAMES));
		predefined.addAll(Arrays.asList(GlobalObjectScope.FILE_NAMES));

		for (String filename : predefined) {
			URI uri = N4Scheme.N4URI.create(filename);
			Resource resource = rs.getResource(uri, loadOnDemand);
			String resourceName = "built-in types resource for \"" + filename + "\"";

			assertNotNull(uri.toString() + " not in " + map(rs.getResources(), r -> r.getURI()), resource);
			assertEquals(resourceName + " must contain two objects", 2, resource.getContents().size());

			EObject ast = resource.getContents().get(0);
			assertTrue(resourceName + " must contain a Script as AST root, but was " + ast.eClass().getName(),
					ast instanceof Script);
			assertTrue(resourceName + " must not contain syntax errors, but got:\n    "
					+ errorsAsString(resource, "\n    "), resource.getErrors().isEmpty());

			((N4JSResource) resource).performPostProcessing();

			List<Issue> allIssues = validationTestHelper.validate(resource);
			if (filename == "builtin_js.n4jsd") {
				// unfortunately this file contains an error we cannot get rid of:
				assertTrue(
						resourceName + " is expected to contain only a single warning, but got:\n    "
								+ join("\n    ", allIssues),
						allIssues.size() == 1 && allIssues.get(0).getSeverity() == Severity.WARNING);
				validationTestHelper.assertWarning(resource, N4JSPackage.Literals.N4_FIELD_DECLARATION,
						IssueCodes.CLF_NO_FINAL_INTERFACE_MEMBER.name(),
						"In interfaces, only methods may be declared final.");
			} else {
				assertTrue(resourceName + " must not contain any validation issues, but got:\n    "
						+ join("\n    ", allIssues), allIssues.isEmpty());
			}
		}
	}

	private String errorsAsString(Resource resource, String separator) {
		return join(separator, map(resource.getErrors(),
				diagnostic -> "line " + diagnostic.getLine() + ": " + diagnostic.getMessage()));
	}
}
