/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.methods.output;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.generator.ISubGenerator;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;
import org.eclipse.n4js.transpiler.es.n4idl.N4IDLSubGenerator;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

/**
 * Xpect helper that acts as on-demand generator. Delegates to {@link EcmaScriptSubGenerator} generator to generate
 * compiled code.
 */
public class XpectN4JSES5GeneratorHelper {
	private final static String NL = "\n";

	@Inject
	private IResourceValidator resourceValidator;

	/**
	 * Compiles provided Script. Can replace backslashes with single quote (for windows command line issues)
	 *
	 * @param depRoot
	 *            script to transpile
	 * @param options
	 *            the {@link GeneratorOption}s to use during compilation.
	 * @param replaceQuotes
	 *            should replace quotes (only for windows)
	 * @return string representation of compilation result
	 */
	public String compile(Script depRoot, GeneratorOption[] options, boolean replaceQuotes) {
		final Resource resource = depRoot.eResource();
		EcoreUtil2.resolveLazyCrossReferences(resource, CancelIndicator.NullImpl);
		final ISubGenerator generator = getGeneratorForResource(resource);
		String compileResultStr = generator.getCompileResultAsText(depRoot, options);
		if (replaceQuotes) {
			// Windows Node.js has problems with " as it interprets it as ending of script to execute
			compileResultStr = compileResultStr.replace("\"", "'");
		}
		return compileResultStr;
	}

	/**
	 * Checks if given resource can be compiled. During the process validators for that resource are executed.
	 *
	 * @param resource
	 *            {@link Resource} that will check
	 * @param errorResult
	 *            {@link StringBuilder} to which potential validation issues will be appended
	 * @return {@code true} if there are no validation issues for resource and it can be compiled
	 */
	public boolean isCompilable(Resource resource, StringBuilder errorResult) {
		final ISubGenerator generator = getGeneratorForResource(resource);
		// shouldBeCompiled already calls the resource validator, so
		// registerErrors should only be called when there have been errors found before
		return generator.shouldBeCompiled(resource, CancelIndicator.NullImpl) || !registerErrors(resource, errorResult);
	}

	private boolean registerErrors(Resource dep, StringBuilder errorResult) {
		boolean hasErrors = false;
		List<Issue> issues = resourceValidator.validate(dep, CheckMode.ALL, CancelIndicator.NullImpl);
		List<Issue> errorIssues = new ArrayList<>();
		for (Issue issue : issues) {
			if (Severity.ERROR == issue.getSeverity()) {
				errorIssues.add(issue);
			}
		}
		hasErrors = !errorIssues.isEmpty();
		if (hasErrors) {
			errorResult.append("Couldn't compile resource " + dep.getURI() + " because it contains errors: ");
			for (Issue errorIssue : errorIssues) {
				errorResult
						.append(NL + errorIssue.getMessage() + " at line " + errorIssue.getLineNumber());
			}
		}
		return hasErrors;
	}

	/**
	 * Returns the desired generator instance for the resource argument. For now, will always return an instance of
	 * {@link EcmaScriptSubGenerator}, except for N4IDL resources an instance of {@link N4IDLSubGenerator} will be
	 * returned.
	 * <p>
	 * NOTE: it would be good to use {@link org.eclipse.n4js.generator.SubGeneratorRegistry}, here, but this registry is
	 * not available in this code.
	 */
	private ISubGenerator getGeneratorForResource(Resource resource) {
		checkState(resource instanceof XtextResource, "Expected XtextResource was " + resource);
		ResourceType resourceType = ResourceType.getResourceType(resource);
		Class<? extends ISubGenerator> generatorType;
		if (resourceType == ResourceType.N4IDL) {
			generatorType = N4IDLSubGenerator.class;
		} else {
			generatorType = EcmaScriptSubGenerator.class;
		}
		return ((XtextResource) resource).getResourceServiceProvider().get(generatorType);
	}

}
