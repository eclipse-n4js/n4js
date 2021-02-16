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
package org.eclipse.n4js.jsdoc2spec.xpect;

import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.jsdoc2spec.adoc.ADocFactory;
import org.eclipse.n4js.jsdoc2spec.adoc.RepoRelativePathHolder;
import org.eclipse.n4js.jsdoc2spec.adoc.SpecIdentifiableElementSection;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.XtextStandaloneSetup;

import com.google.inject.Inject;

/**
 */
@XpectImport({ N4JSOffsetAdapter.class, XtextStandaloneSetup.class })
public class SpecADocXpectMethod {

	@Inject
	private ADocFactory formatter;
	@Inject
	private RepoRelativePathHolder repoPathHolder;

	/**
	 * Checks that an element/expression produces a certain specification string. Usage:
	 *
	 * <pre>
	 * /* X-PECT specADoc at 'location' ---
	 * expected spec output
	 * --- * /
	 * </pre>
	 *
	 * The location (at) is optional.
	 *
	 * Note that the X-PECT comment must be written <b>before</b> the JSDoc comment of the element.
	 *
	 * @param arg1
	 *            the location identified by the offset.
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg1=STRING)?")
	public void specADoc(
			@StringExpectation(caseSensitive = true) IStringExpectation expectation,
			IEObjectCoveringRegion arg1) {
		if (expectation == null) {
			throw new IllegalStateException("No expectation specified, add '--- expected expectation ---");
		}

		IdentifiableElement element = getIdentifiableElement(arg1);
		SpecIdentifiableElementSection src = new SpecIdentifiableElementSection(element, repoPathHolder);

		String actual = formatter.createSpecRegionString(src, Collections.emptyMap()).toString();
		expectation.assertEquals(actual);
	}

	private IdentifiableElement getIdentifiableElement(IEObjectCoveringRegion arg1) {
		EObject eobj = arg1.getEObject();
		if (eobj instanceof ExportDeclaration) {
			eobj = ((ExportDeclaration) eobj).getExportedElement();
		}
		if (eobj instanceof ExportedVariableStatement) {
			EList<VariableDeclaration> decls = ((ExportedVariableStatement) eobj).getVarDecl();
			if (decls.size() != 1) {
				throw new IllegalStateException("JSDoc for var statements required exactly one declaration.");
			}
			return decls.get(0);
		}
		/** For variables using the new type notation and spec comment after export modifier */
		if (eobj instanceof ExportedVariableDeclaration) {
			return (ExportedVariableDeclaration) eobj;
		}
		/** For variables using the old type notation and spec comment after export modifier */
		if (eobj instanceof TypeRef && eobj.eContainer() instanceof ExportedVariableDeclaration) {
			return (ExportedVariableDeclaration) eobj.eContainer();
		}
		if (eobj instanceof TypeDefiningElement) {
			return ((TypeDefiningElement) eobj).getDefinedType();
		}
		if (eobj instanceof N4TypeVariable) {
			return ((N4TypeVariable) eobj).getDefinedTypeVariable();
		}
		throw new IllegalStateException("No type defining element found at location.");
	}
}
