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
import static org.eclipse.n4js.validation.IssueCodes.ENM_INVALID_USE_OF_STRINGBASED_ENUM;
import static org.eclipse.n4js.validation.IssueCodes.ENM_LITERALS_HIDE_META;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForENM_DUPLICTAE_LITERALS;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForENM_INVALID_USE_OF_STRINGBASED_ENUM;
import static org.eclipse.n4js.validation.IssueCodes.getMessageForENM_LITERALS_HIDE_META;
import static org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.internalCheckNotInStaticPolyfillModule;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

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

	/**
	 * See N4JS Specification, Req. IDE-41, Nr. 6.
	 */
	@Check
	public void checkUsageOfStringBasedEnum(IdentifierRef identRef) {
		final IdentifiableElement id = identRef.getId();
		if (id == null || id.eIsProxy()) {
			return;
		}
		if (!(id instanceof TEnum)) {
			return;
		}
		final TEnum tEnum = (TEnum) id;
		if (!AnnotationDefinition.STRING_BASED.hasAnnotation(tEnum)) {
			return;
		}
		// we now have an IdentifierRef pointing to a string-based enum ...
		final EObject parent = N4JSASTUtils.skipParenExpressionUpward(identRef.eContainer());
		final ParameterizedPropertyAccessExpression parentPAE = parent instanceof ParameterizedPropertyAccessExpression
				? (ParameterizedPropertyAccessExpression) parent
				: null;
		final IdentifiableElement prop = parentPAE != null ? parentPAE.getProperty() : null;
		if (prop != null) {
			if (prop.eIsProxy()) {
				// there will be an error for the unresolved property access, so any error shown below would be an
				// unnecessary duplicate error
				return;
			}
			if (prop instanceof TEnumLiteral) {
				// reference to one of tEnum's literals -> valid usage!
				TEnumLiteral casted = (TEnumLiteral) prop;
				if (tEnum.getLiterals().contains(casted)) {
					return;
				}
			}
			final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(identRef);
			final TMember getterLiterals = RuleEnvironmentExtensions.n4StringBasedEnumType(G)
					.findOwnedMember("literals", false, true);
			if (prop == getterLiterals) {
				// reference to static getter 'literals' in N4StringBasedEnum -> valid usage!
				return;
			}
		}
		// invalid usage!
		addIssue(getMessageForENM_INVALID_USE_OF_STRINGBASED_ENUM(), identRef, ENM_INVALID_USE_OF_STRINGBASED_ENUM);
	}

	// publish
	@Override
	public void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
		super.addIssue(message, source, feature, issueCode, issueData);
	}
}
