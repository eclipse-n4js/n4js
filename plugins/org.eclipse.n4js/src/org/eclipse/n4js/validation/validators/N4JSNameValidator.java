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

import static org.eclipse.n4js.N4JSLanguageConstants.ACCESS_MODIFIERS;
import static org.eclipse.n4js.N4JSLanguageConstants.BASE_TYPES;
import static org.eclipse.n4js.N4JSLanguageConstants.BOOLEAN_LITERALS;
import static org.eclipse.n4js.N4JSLanguageConstants.CONSTRUCTOR;
import static org.eclipse.n4js.N4JSLanguageConstants.DISCOURAGED_CHARACTERS;
import static org.eclipse.n4js.N4JSLanguageConstants.FUTURE_RESERVED_WORDS;
import static org.eclipse.n4js.N4JSLanguageConstants.GETTER_SETTER;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.ABSTRACT_VARIABLE__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NAME_CONFLICTS_WITH_CONSTRUCTOR;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NAME_CONTAINS_DISCOURAGED_CHARACTER;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NAME_DIFFERS_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NAME_DOES_NOT_START_LOWERCASE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NAME_DOES_NOT_START_UPPERCASE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NAME_DOLLAR;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NAME_INDISTINGUISHABLE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NAME_RESERVED;

import java.util.Collection;

import org.eclipse.n4js.n4JS.AbstractVariable;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSMetaModelUtils;
import org.eclipse.n4js.n4JS.N4MemberAnnotationList;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * Validation of names, cf N4JS Spec, Chapter 3.4., Constraints 3 and 4
 */
public class N4JSNameValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSLanguageHelper languageHelper;

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Constraints 3 and 4.2
	 */
	@Check
	public void checkN4TypeDeclaration(N4TypeDeclaration n4TypeDeclaration) {
		if (isNotChecked(n4TypeDeclaration)) {
			return;
		}

		// quick exit:
		if (Character.isUpperCase(n4TypeDeclaration.getName().charAt(0))) {
			return;
		}
		if (holdsTypeNameNotIndistinguishable(n4TypeDeclaration, "getter/setter", GETTER_SETTER) //
				&& holdsTypeNameNotIndistinguishable(n4TypeDeclaration, "access modifier", ACCESS_MODIFIERS) //
				&& holdsTypeNameNotIndistinguishable(n4TypeDeclaration, "boolean literal", BOOLEAN_LITERALS) //
				&& holdsTypeNameNotIndistinguishable(n4TypeDeclaration, "base type", BASE_TYPES) //
				&& holdsTypeNameNotIndistinguishable(n4TypeDeclaration, "keyword",
						languageHelper.getECMAKeywords())
				&& holdsDoesNotStartWithLowerCaseLetter(n4TypeDeclaration)) {
			// error messages are created with in constraint validation
		}
	}

	private boolean isNotChecked(N4TypeDeclaration n4TypeDeclaration) {
		if (n4TypeDeclaration.getDefinedType() == null) {
			return true;
		}
		if (Strings.isEmpty(n4TypeDeclaration.getName())) {
			return true; // invalid AST
		}
		if (!jsVariantHelper.checkTypeDeclaration(n4TypeDeclaration)) {
			return true;
		}
		return n4TypeDeclaration.isExternal();
	}

	private boolean holdsTypeNameNotIndistinguishable(N4TypeDeclaration element, String suffix,
			Collection<String> category) {
		if (category.contains(element.getName()) && !"yield".equals(element.getName())) {
			final IssueItem issueItem = CLF_NAME_INDISTINGUISHABLE.toIssueItem(
					Strings.toFirstUpper(validatorMessageHelper.description(element)),
					suffix);
			addIssue(element, N4_TYPE_DECLARATION__NAME, issueItem);
			return false;
		}
		return true;
	}

	private boolean holdsDoesNotStartWithLowerCaseLetter(N4TypeDeclaration declaration) {
		if (Character.isLowerCase(declaration.getName().charAt(0))) {
			final IssueItem issueItem = CLF_NAME_DOES_NOT_START_UPPERCASE.toIssueItem(
					Strings.toFirstUpper(keywordProvider.keyword(declaration)));
			addIssue(declaration, N4_TYPE_DECLARATION__NAME, issueItem);
			return false;
		}
		return true;
	}

	/**
	 * Constraints 4.1
	 */
	@Check
	public void checkN4MemberDeclaration(N4MemberDeclaration n4Member) {
		if (isNotChecked(n4Member)) {
			return;
		}

		if (
		// IDEBUG-304 allow member names to be keywords
		// holdsNameMayNotBeConfusedWith(n4Member, "keyword", KEYWORDS) //
		// &&
		holdsDoesNotEqualWithConstructor(n4Member) //
				&& holdsNameMayNotBeConfusedWith(n4Member, "future reserved word", FUTURE_RESERVED_WORDS) //
				&& holdsNameMayNotBeConfusedWith(n4Member, "boolean literal", BOOLEAN_LITERALS) //
				&& //
				(!(n4Member instanceof AbstractVariable) // avoid redundant checks
						&& holdsDoesNotStartWithUpperCaseLetter(n4Member) //
						&& holdsNoTypeNameOrNameEqualsType(n4Member) //
						&& holdsDoesNotContainDiscouragedCharacter(n4Member) //
						&& holdsNameMayNotBeConfusedWith(n4Member, "access modifier", ACCESS_MODIFIERS) //
				)) {
			// error messages are created with in constraint validation
		}

	}

	private boolean holdsDoesNotEqualWithConstructor(final N4MemberDeclaration n4Member) {
		if (n4Member.isStatic() && CONSTRUCTOR.equals(getDeclarationName(n4Member))) {
			final IssueItem issueItem = CLF_NAME_CONFLICTS_WITH_CONSTRUCTOR.toIssueItem();
			addIssue(n4Member, N4JSMetaModelUtils.getElementNameFeature(n4Member), issueItem);
			return false;
		}
		return true;
	}

	private boolean holdsDoesNotContainDiscouragedCharacter(final N4MemberDeclaration n4Member) {

		for (final String discouragedCharacter : DISCOURAGED_CHARACTERS.keySet()) {
			final String declarationName = getDeclarationName(n4Member);
			if (!declarationName.startsWith("$") && declarationName.contains(discouragedCharacter)) {
				final String discouragedCharacterLabel = DISCOURAGED_CHARACTERS.get(discouragedCharacter);
				final IssueItem issueItem = CLF_NAME_CONTAINS_DISCOURAGED_CHARACTER
						.toIssueItem(discouragedCharacterLabel);
				addIssue(n4Member, N4JSMetaModelUtils.getElementNameFeature(n4Member), issueItem);
				return false;
			}
		}

		return true;
	}

	private String getDeclarationName(final N4MemberDeclaration n4Member) {
		return com.google.common.base.Strings.nullToEmpty(n4Member.getName());
	}

	private boolean isNotChecked(N4MemberDeclaration n4Member) {
		if (n4Member.getDefinedTypeElement() == null) {
			return true;
		}
		if (n4Member instanceof N4MemberAnnotationList) {
			return true; // parent already checked
		}
		if (Strings.isEmpty(n4Member.getName()) || n4Member.getOwner() == null) {
			return true; // infinalid AST
		}
		if (!jsVariantHelper.checkMemberDeclaration(n4Member)) {
			return true;
		}
		return n4Member.getOwner().isExternal();
	}

	private boolean holdsDoesNotStartWithUpperCaseLetter(N4MemberDeclaration n4Member) {
		if (n4Member.isStatic()) {
			return true;
		}

		if (isConstFieldDeclaration(n4Member)) {
			return true;
		}

		if (Character.isUpperCase(n4Member.getName().charAt(0))) {
			final IssueItem issueItem = CLF_NAME_DOES_NOT_START_LOWERCASE.toIssueItem(
					Strings.toFirstUpper(keywordProvider.keyword(n4Member)));
			addIssue(n4Member, N4JSMetaModelUtils.getElementNameFeature(n4Member), issueItem);
			return false;
		}
		return true;
	}

	/**
	 * Returns with {@code true} only and if only the member declaration argument is a {@link N4FieldDeclaration field
	 * declaration} instance and {@link N4FieldDeclaration#isConst() const}. Otherwise returns with {@code false}.
	 *
	 * @param memberDeclaration
	 *            the declaration to check.
	 * @return {@code true} if a constant field declaration, otherwise {@code false}.
	 */
	private boolean isConstFieldDeclaration(final N4MemberDeclaration memberDeclaration) {
		return memberDeclaration instanceof N4FieldDeclaration
				? ((N4FieldDeclaration) memberDeclaration).isConst()
				: false;
	}

	/**
	 * Constraints 4.3
	 */
	@Check
	public void checkVariable(AbstractVariable<?> variable) {
		if (isNotChecked(variable)) {
			return;
		}

		if (holdsDoesNotStartWithDollarSign(variable) //
				&& holdsDoesNotEqualWithConstructor(variable) //
				&& holdsStartWithLowercaseLetter(variable) //
				&& holdsNameMayNotBeConfusedWith(variable, "access modifier", ACCESS_MODIFIERS) //
				&& holdsNoTypeNameOrNameEqualsType(variable) //
				&& holdsDoesNotContainDiscouragedCharacter(variable)) {
			// error messages are created with in constraint validation
		}
	}

	private boolean holdsDoesNotEqualWithConstructor(final AbstractVariable<?> variable) {
		if (CONSTRUCTOR.equals(getVariableName(variable))) {
			final IssueItem issueItem = CLF_NAME_CONFLICTS_WITH_CONSTRUCTOR.toIssueItem();
			addIssue(variable, ABSTRACT_VARIABLE__NAME, issueItem);
			return false;
		}
		return true;
	}

	private boolean holdsDoesNotStartWithDollarSign(final AbstractVariable<?> variable) {
		// name may be null (invalid file), we do not need an NPE here
		if (getVariableName(variable).startsWith("$")) {
			final IssueItem issueItem = CLF_NAME_DOLLAR.toIssueItem();
			addIssue(variable, ABSTRACT_VARIABLE__NAME, issueItem);
			return false;
		}
		return true;
	}

	private String getVariableName(final AbstractVariable<?> variable) {
		return com.google.common.base.Strings.nullToEmpty(variable.getName());
	}

	private boolean isNotChecked(AbstractVariable<?> variable) {
		if (Strings.isEmpty(variable.getName())) {
			return true; // invalid AST
		}
		if (!jsVariantHelper.checkVariable(variable)) {
			return true;
		}
		return false;
	}

	private boolean holdsStartWithLowercaseLetter(AbstractVariable<?> variable) {

		if (variable.isConst()) {
			return true;
		}

		final char first = variable.getName().charAt(0);
		if (Character.isLetter(first) && !Character.isLowerCase(first)) {
			final IssueItem issueItem = CLF_NAME_DOES_NOT_START_LOWERCASE.toIssueItem(
					Strings.toFirstUpper(keywordProvider.keyword(variable)));
			addIssue(variable, ABSTRACT_VARIABLE__NAME, issueItem);
			return false;
		}
		return true;
	}

	private boolean holdsNoTypeNameOrNameEqualsType(NamedElement namedElement) {
		if (namedElement instanceof TypableElement) {
			String name = namedElement.getName();
			if (BASE_TYPES.contains(name)) {
				TypeRef typeRef = ts.tau((TypableElement) namedElement);
				if (typeRef != null && typeRef.getDeclaredType() != null) {
					String typeName = typeRef.getDeclaredType().getName();
					if (!Strings.isEmpty(typeName) && !name.equals(typeName)) {
						final IssueItem issueItem = CLF_NAME_DIFFERS_TYPE.toIssueItem(
								Strings.toFirstUpper(validatorMessageHelper.description(namedElement)), name, typeName);
						addIssue(namedElement, N4JSMetaModelUtils.getElementNameFeature(namedElement), issueItem);
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean holdsNameMayNotBeConfusedWith(NamedElement element, String suffix, Collection<String> category) {
		// N4JSFV#checkFunctionName is responsible for the validation of function definitions
		if (category.contains(element.getName()) && !(element instanceof FunctionDefinition)) {
			final IssueItem issueItem = CLF_NAME_RESERVED.toIssueItem(
					Strings.toFirstUpper(validatorMessageHelper.description(element)),
					suffix);
			addIssue(element, N4JSMetaModelUtils.getElementNameFeature(element), issueItem);
			return false;
		}
		return true;
	}

	private boolean holdsDoesNotContainDiscouragedCharacter(final AbstractVariable<?> variable) {

		for (final String discouragedCharacter : DISCOURAGED_CHARACTERS.keySet()) {
			final String declarationName = getVariableName(variable);
			if (!declarationName.startsWith(discouragedCharacter) && declarationName.contains(discouragedCharacter)) {
				final String discouragedCharacterLabel = DISCOURAGED_CHARACTERS.get(discouragedCharacter);
				final IssueItem issueItem = CLF_NAME_CONTAINS_DISCOURAGED_CHARACTER
						.toIssueItem(discouragedCharacterLabel);
				addIssue(variable, ABSTRACT_VARIABLE__NAME, issueItem);
				return false;
			}
		}

		return true;
	}

}
