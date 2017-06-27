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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.validation.IssueCodes.ENM_DUPLICTAE_LITERALS;
import static org.eclipse.n4js.validation.IssueCodes.ENM_LITERALS_HIDE_META;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForENM_DUPLICTAE_LITERALS;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForENM_LITERALS_HIDE_META;
import static org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.internalCheckNotInStaticPolyfillModule;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;

/**
 * Validation for N4Enums.
 */
public class N4JSEnumValidator extends AbstractN4JSDeclarativeValidator {

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		/* nop */
	}

	/**
	 * Composed check for {@link EnumLiteralDeclaration}.
	 *
	 * @param n4EnumDeclaration
	 *            whose literals will be validated
	 */
	@Check
	public void checkEnumLiterals(N4EnumDeclaration n4EnumDeclaration) {
		Set<String> builtInEnumMembersNames = findBuiltInN4EnumMembers(n4EnumDeclaration);

		internalCheckNotInStaticPolyfillModule(n4EnumDeclaration, this);

		n4EnumDeclaration
				.getLiterals()
				.stream()
				.filter(it -> it.getName() != null)
				.collect(Collectors.groupingBy(N4EnumLiteral::getName))
				.forEach(
						(name, literals) -> {
							// reduce number of issues, not all checks may be triggered

							// check enum literals duplicates
							if (literals.size() > 1) {
								addIssue(getMessageForENM_DUPLICTAE_LITERALS(name), literals.get(0),
										N4JSPackage.Literals.N4_ENUM_LITERAL__NAME,
										ENM_DUPLICTAE_LITERALS);

								return;// one issue at the time!
							}

							// check enum literal name clash with meta property
							if (builtInEnumMembersNames.contains(name)) {
								addIssue(getMessageForENM_LITERALS_HIDE_META(name), literals.get(0),
										N4JSPackage.Literals.N4_ENUM_LITERAL__NAME,
										ENM_LITERALS_HIDE_META);
							}
						});
	}

	/**
	 * Based on provided {@link N4EnumDeclaration} determines built in members of that type. Uses
	 * {@link BuiltInTypeScope} to get (meta) type of given enum declaration.
	 *
	 * @param n4EnumDeclaration
	 *            whose meta type will be inspected
	 * @return {@link Set} of built in enum members names
	 */
	private Set<String> findBuiltInN4EnumMembers(N4EnumDeclaration n4EnumDeclaration) {
		return
		// get built enum type from given enum built in scope
		BuiltInTypeScope.get(n4EnumDeclaration.eResource().getResourceSet())
				// get members
				.getN4EnumType().getOwnedMembers().stream()
				// get set of names
				.map(tm -> {
					return tm.getName();
				}).collect(Collectors.toSet());
	}

	// publish
	@Override
	public void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
		super.addIssue(message, source, feature, issueCode, issueData);
	}
}
