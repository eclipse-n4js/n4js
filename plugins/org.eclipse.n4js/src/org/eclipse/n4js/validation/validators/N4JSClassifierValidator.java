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

import static com.google.common.collect.Iterables.concat;
import static java.util.Collections.singletonList;
import static org.eclipse.n4js.validation.IssueCodes.CLF_DEF_SITE_VARIANCE_ONLY_IN_CLASSIFIER;
import static org.eclipse.n4js.validation.IssueCodes.CLF_DUP_CTOR;
import static org.eclipse.n4js.validation.IssueCodes.CLF_DUP_MEMBER;
import static org.eclipse.n4js.validation.IssueCodes.CLF_IMPLEMENT_EXTEND_WITH_WILDCARD;
import static org.eclipse.n4js.validation.IssueCodes.CLF_MULTIPLE_ROLE_CONSUME;
import static org.eclipse.n4js.validation.IssueCodes.CLF_TYPE_VARIABLE_AT_INVALID_POSITION;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.tail;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.scoping.utils.QualifiedNameUtils;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.collect.Multimaps;
import com.google.inject.Inject;

/**
 * Validation of rules that apply to all classifiers w/o examining members of the classifiers.
 * <p>
 */
public class N4JSClassifierValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

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
	 * Constraints 38.3: Wildcards may not be used as type argument when binding a supertype’s type parameters.
	 */
	@Check
	public void checkWildcardInExtendsImplements(N4ClassifierDefinition n4ClassifierDef) {
		Iterable<TypeReferenceNode<ParameterizedTypeRef>> superTypeRefNodes = null;

		if (n4ClassifierDef instanceof N4ClassDefinition) {
			N4ClassDefinition cd = (N4ClassDefinition) n4ClassifierDef;
			superTypeRefNodes = concat(singletonList(cd.getSuperClassRef()), cd.getImplementedInterfaceRefs());
		}
		if (n4ClassifierDef instanceof N4InterfaceDeclaration) {
			superTypeRefNodes = ((N4InterfaceDeclaration) n4ClassifierDef).getSuperInterfaceRefs();
		}

		Iterable<ParameterizedTypeRef> superTypeRefs = filterNull(
				map(filterNull(superTypeRefNodes), trn -> trn.getTypeRefInAST()));

		for (TypeRef typeRefInAST : superTypeRefs) {
			for (TypeArgument typeArgInAST : typeRefInAST.getDeclaredTypeArgs()) {
				if (typeArgInAST instanceof Wildcard) {
					addIssue(typeArgInAST, CLF_IMPLEMENT_EXTEND_WITH_WILDCARD.toIssueItem());
				}
			}
		}
	}

	/**
	 * Constraints 32 (Consuming Roles)
	 */
	@Check
	public void checkConsumingRoles(N4ClassifierDefinition n4ClassifierDefinition) {
		// some preparations common for all the below checks
		TClassifier tClassifier = (TClassifier) n4ClassifierDefinition.getDefinedType();
		if (tClassifier == null) {
			return;
		}

		// check
		internalCheckDuplicatedConsumedInterfaces(tClassifier);
	}

	/**
	 * Delegates further to perform check if classifier is {@link TClass} or {@link TInterface} otherwise does nothing
	 */
	private void internalCheckDuplicatedConsumedInterfaces(TClassifier classifier) {
		if (classifier instanceof TClass) {
			List<QualifiedName> fqns = toList(filterNull(
					map(((TClass) classifier).getImplementedInterfaceRefs(), iir -> iir.getDeclaredType() == null ? null
							: qualifiedNameProvider.getFullyQualifiedName(iir.getDeclaredType()))));
			doInternalCheckDuplicatedConsumedRoles(
					fqns, classifier,
					N4JSPackage.Literals.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS);
		}
		if (classifier instanceof TInterface) {

			List<QualifiedName> fqns = toList(filterNull(
					map(((TInterface) classifier).getSuperInterfaceRefs(), sir -> sir.getDeclaredType() == null ? null
							: qualifiedNameProvider.getFullyQualifiedName(sir.getDeclaredType()))));
			doInternalCheckDuplicatedConsumedRoles(fqns, classifier,
					N4JSPackage.Literals.N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS);

		}
	}

	private void doInternalCheckDuplicatedConsumedRoles(List<QualifiedName> names, SyntaxRelatedTElement source,
			EReference eref) {

		if (names == null || names.isEmpty()) {
			return;
		}

		Iterable<org.eclipse.xtext.xbase.lib.Pair<QualifiedName, Integer>> duplicates = filter(
				computeStringOccurance(names), pair -> pair.getValue() > 1);

		for (org.eclipse.xtext.xbase.lib.Pair<QualifiedName, Integer> dupe : duplicates) {
			addIssue(source.getAstElement(), eref,
					CLF_MULTIPLE_ROLE_CONSUME.toIssueItem(QualifiedNameUtils.toHumanReadableString(dupe.getKey())));
		}
	}

	/**
	 * Computes occurrence of every qualified name in the Collection. Returns Iterable&lt;Pair&lt;QualifiedName,
	 * Integer>>, where {@link Pair} keys are items of original collection and values are number of occurrences in
	 * collection
	 */
	static private Iterable<org.eclipse.xtext.xbase.lib.Pair<QualifiedName, Integer>> computeStringOccurance(
			Collection<QualifiedName> collection) {

		Map<QualifiedName, Integer> acc = new HashMap<>();

		for (QualifiedName entry : collection) {
			if (acc.containsKey(entry)) {
				acc.put(entry, acc.get(entry).intValue() + 1);
			} else {
				acc.put(entry, 1);
			}
		}

		Map<QualifiedName, Integer> finalAcc = acc;
		return map(acc.keySet(), it -> org.eclipse.xtext.xbase.lib.Pair.of(it, finalAcc.get(it)));
	}

	/**
	 * @apiNote N4JSSpec, 4.18. Members, Constraints 33 (Member Names)
	 */
	@Check
	public void checkUniqueOwenedMemberNames(N4ClassifierDefinition n4ClassifierDefinition) {
		// wrong parsed
		if (!(n4ClassifierDefinition.getDefinedType() instanceof TClassifier)) {
			return;
		}

		TClassifier tClassifier = (TClassifier) n4ClassifierDefinition.getDefinedType();

		Map<Pair<String, Boolean>, Collection<TMember>> ownedMembersByNameAndStatic = Multimaps
				.index(tClassifier.getOwnedMembers(), m -> Tuples.pair(m.getName(), m.isStatic())).asMap();

		for (Collection<TMember> it : ownedMembersByNameAndStatic.values()) {
			if (it.size() > 1) {
				createErrorsForDuplicateOwnedMembers(it);
				// IDEBUG-332:
				// not done yet; in a list like #[ TGetter, TSetter, TSetter ] we will miss error messages for the
				// two setters, because method #createErrorsForDuplicateOwnedMembers() compares all tail elements to
				// the head so it sees just getter/setter pairs (which are legal, see call to #isFieldAccessorPair()
				// below)
				TMember firstTM = it.iterator().next();
				if (firstTM instanceof TGetter) {
					createErrorsForDuplicateOwnedMembers(filter(it, TSetter.class));
				} else if (firstTM instanceof TSetter) {
					createErrorsForDuplicateOwnedMembers(filter(it, TGetter.class));
				}
			}
		}
	}

	/**
	 * Assumes that all TMembers in argument 'members' have the same name and will create error messages for them,
	 * except for getter/setter pairs.
	 */
	private void createErrorsForDuplicateOwnedMembers(Iterable<? extends TMember> members) {
		TMember firstDup = head(members);
		boolean createErrorForFirst = true;
		for (TMember otherDup : tail(members)) {
			if (!isFieldAccessorPair(firstDup, otherDup)) {
				if (firstDup.isConstructor()) {
					if (createErrorForFirst) {
						IssueItem issueItem = CLF_DUP_CTOR.toIssueItem(
								NodeModelUtils.getNode(firstDup.getAstElement()).getStartLine(),
								NodeModelUtils.getNode(otherDup.getAstElement()).getStartLine());
						addIssue(firstDup.getAstElement(), N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
								issueItem);
						createErrorForFirst = false;
					}
					IssueItem issueItem = CLF_DUP_CTOR.toIssueItem(
							NodeModelUtils.getNode(otherDup.getAstElement()).getStartLine(),
							NodeModelUtils.getNode(firstDup.getAstElement()).getStartLine());
					addIssue(otherDup.getAstElement(), N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
							issueItem);
				} else {
					if (createErrorForFirst) {
						IssueItem issueItem = CLF_DUP_MEMBER.toIssueItem(
								validatorMessageHelper.descriptionWithLine(firstDup),
								validatorMessageHelper.descriptionWithLine(otherDup));
						addIssue(firstDup.getAstElement(), N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
								issueItem);
						createErrorForFirst = false;
					}
					IssueItem issueItem = CLF_DUP_MEMBER.toIssueItem(
							validatorMessageHelper.descriptionWithLine(otherDup),
							validatorMessageHelper.descriptionWithLine(firstDup));
					addIssue(otherDup.getAstElement(), N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
							issueItem);
				}
			}
		}
	}

	/***/
	@Check
	public void checkUseOfDefinitionSiteVariance(N4TypeVariable n4TypeVar) {
		if ((n4TypeVar.isDeclaredCovariant() || n4TypeVar.isDeclaredContravariant()) &&
				!(n4TypeVar.eContainer() instanceof N4ClassifierDeclaration
						&& n4TypeVar.eContainmentFeature() == N4JSPackage.eINSTANCE.getGenericDeclaration_TypeVars())) {
			EAttribute feature = (n4TypeVar.isDeclaredCovariant())
					? N4JSPackage.eINSTANCE.getN4TypeVariable_DeclaredCovariant()
					: N4JSPackage.eINSTANCE.getN4TypeVariable_DeclaredContravariant();
			addIssue(n4TypeVar, feature, CLF_DEF_SITE_VARIANCE_ONLY_IN_CLASSIFIER.toIssueItem());
		}
	}

	/**
	 * Ensures that type variables declared as covariant only appear in covariant positions and type variables declared
	 * as contravariant only appear in contravariant positions. Type variables declared to be invariant do not require
	 * such a check.
	 */
	@Check
	public void checkTypeVariableVariance(ParameterizedTypeRef typeRefInAST) {
		Type tv = typeRefInAST.getDeclaredType();
		if (tv instanceof TypeVariable) {
			Variance variance = ((TypeVariable) tv).getVariance();
			if (variance != Variance.INV) {
				Variance varianceOfPos = N4JSLanguageUtils.getVarianceOfPosition(typeRefInAST);
				if (varianceOfPos != null && variance != varianceOfPos) {
					IssueItem issueItem = CLF_TYPE_VARIABLE_AT_INVALID_POSITION.toIssueItem(
							variance.getDescriptiveString(true),
							varianceOfPos.getDescriptiveString(false));
					addIssue(typeRefInAST, issueItem);
				}
			}
		}
	}
}
