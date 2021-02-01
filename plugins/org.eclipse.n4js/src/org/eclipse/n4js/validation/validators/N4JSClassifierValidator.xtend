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
package org.eclipse.n4js.validation.validators

import com.google.common.collect.Multimaps
import com.google.inject.Inject
import java.util.Collection
import java.util.HashMap
import java.util.List
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.n4JS.N4ClassDefinition
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.util.Pair
import org.eclipse.xtext.util.Tuples
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.validation.IssueCodes.*

/**
 * Validation of rules that apply to all classifiers w/o examining members of the classifiers.<p>
 */
class N4JSClassifierValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private extension IQualifiedNameProvider qualifiedNameProvider;
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Constraints 38.3: Wildcards may not be used as type argument when binding a supertype’s type parameters.
	 */
	@Check
	def checkWildcardInExtendsImplements(N4ClassifierDefinition n4ClassifierDef) {
		val superTypeRefs = switch(n4ClassifierDef) {
			N4ClassDefinition: #[ n4ClassifierDef.superClassRef ] + n4ClassifierDef.implementedInterfaceRefs
			N4InterfaceDeclaration: n4ClassifierDef.superInterfaceRefs
		}.filterNull.map[typeRef].filterNull;
		for(typeRef : superTypeRefs) {
			for(typeArg : typeRef.typeArgs) {
				if(typeArg instanceof Wildcard) {
					addIssue(getMessageForCLF_IMPLEMENT_EXTEND_WITH_WILDCARD, typeArg, CLF_IMPLEMENT_EXTEND_WITH_WILDCARD);
				}
			}
		}
	}

	/**
	 * Constraints 32 (Consuming Roles)
	 */
	@Check
	def checkConsumingRoles(N4ClassifierDefinition n4ClassifierDefinition) {

		// some preparations common for all the below checks
		val tClassifier = n4ClassifierDefinition.definedType as TClassifier
		if (tClassifier === null) {
			return;
		}

		// check
		tClassifier.internalCheckDuplicatedConsumedInterfaces
	}


	/**
	 * Delegates further to perform check if classifier is {@link TClass} or {@link TInterface}
	 * otherwise does nothing
	 */
	def private internalCheckDuplicatedConsumedInterfaces(TClassifier classifier) {

		switch classifier {
			TClass:
				classifier.implementedInterfaceRefs.map[declaredType?.fullyQualifiedName].filterNull.toList.
					doInternalCheckDuplicatedConsumedRoles(classifier,
						N4JSPackage.Literals.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS)
			TInterface:
				classifier.superInterfaceRefs.map[declaredType?.fullyQualifiedName].filterNull.toList.
					doInternalCheckDuplicatedConsumedRoles(classifier,
						N4JSPackage.Literals.N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS)
			default:
				return
		}
	}

	def private doInternalCheckDuplicatedConsumedRoles(List<QualifiedName> names, SyntaxRelatedTElement source,
		EReference eref) {

		if (names.nullOrEmpty) return;

		val duplicates = names.map[qualifiedNameConverter.toString(it)].computeStringOccurance.filter[value > 1]

		for (dupe : duplicates) {
			val message = getMessageForCLF_MULTIPLE_ROLE_CONSUME(dupe.key)
			addIssue(message, source.astElement, eref, CLF_MULTIPLE_ROLE_CONSUME)
		}
	}

	/**
	 * Computes occurrence of every String in the Collection.
	 * Returns Iterable&lt;Pair&lt;String, Integer>>, where {@link Pair} keys are
	 * items of original collection and values are number of occurrences in collection
	 */
	def static private computeStringOccurance(Collection<String> collection) {

		var acc = new HashMap<String, Integer>()

		for (entry : collection) {
			if (acc.containsKey(entry)) {
				acc.put(entry, acc.get(entry).intValue + 1)
			} else {
				acc.put(entry, 1)
			}
		}

		val finalAcc = acc
		return acc.keySet.map[it -> finalAcc.get(it)]
	}

	/**
	 * @see N4JSSpec, 4.18. Members, Constraints 33 (Member Names)
	 */
	@Check
	def checkUniqueOwenedMemberNames(N4ClassifierDefinition n4ClassifierDefinition) {

		// wrong parsed
		if (!(n4ClassifierDefinition.definedType instanceof TClassifier)) {
			return
		}

		val tClassifier = n4ClassifierDefinition.definedType as TClassifier

		val ownedMembersByNameAndStatic = Multimaps.index(tClassifier.ownedMembers, [Tuples.pair(name, static)]).asMap();
		ownedMembersByNameAndStatic.values.forEach [
			if (size() > 1) {
				createErrorsForDuplicateOwnedMembers(it)
				// IDEBUG-332:
				// not done yet; in a list like #[ TGetter, TSetter, TSetter ] we will miss error messages for the
				// two setters, because method #createErrorsForDuplicateOwnedMembers() compares all tail elements to
				// the head so it sees just getter/setter pairs (which are legal, see call to #isFieldAccessorPair() below)
				if(it.head instanceof TGetter)
					createErrorsForDuplicateOwnedMembers(it.filter(TSetter))
				else if(it.head instanceof TSetter)
					createErrorsForDuplicateOwnedMembers(it.filter(TGetter))
			}
		]
	}

	/**
	 * Assumes that all TMembers in argument 'members' have the same name and will create error messages for them,
	 * except for getter/setter pairs.
	 */
	def private void createErrorsForDuplicateOwnedMembers(Iterable<? extends TMember> members) {
		val firstDup = members.head;
		var createErrorForFirst = true;
		for (TMember otherDup : members.tail) {
			if (! isFieldAccessorPair(firstDup, otherDup)) {
				if (firstDup.constructor) {
					if (createErrorForFirst) {
						val message = getMessageForCLF_DUP_CTOR(
							NodeModelUtils::getNode(firstDup.astElement).startLine,
							NodeModelUtils::getNode(otherDup.astElement).startLine
						);
						addIssue(message, firstDup.astElement, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
							CLF_DUP_CTOR)
						createErrorForFirst = false;
					}
					val message = getMessageForCLF_DUP_CTOR(
						NodeModelUtils::getNode(otherDup.astElement).startLine,
						NodeModelUtils::getNode(firstDup.astElement).startLine);
					addIssue(message, otherDup.astElement, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_DUP_CTOR)
				} else {
					if (createErrorForFirst) {
						val message = getMessageForCLF_DUP_MEMBER(firstDup.descriptionWithLine(),
							otherDup.descriptionWithLine());
						addIssue(message, firstDup.astElement, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
							CLF_DUP_MEMBER)
						createErrorForFirst = false;
					}
					val message = getMessageForCLF_DUP_MEMBER(otherDup.descriptionWithLine(),
						firstDup.descriptionWithLine());
					addIssue(message, otherDup.astElement, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_DUP_MEMBER)
				}
			}
		}
	}

	@Check
	def void checkUseOfDefinitionSiteVariance(TypeVariable typeVar) {
		if((typeVar.declaredCovariant || typeVar.declaredContravariant) &&
			!(typeVar.eContainer instanceof N4ClassifierDeclaration
				&& typeVar.eContainmentFeature===N4JSPackage.eINSTANCE.genericDeclaration_TypeVars)) {
			val message = messageForCLF_DEF_SITE_VARIANCE_ONLY_IN_CLASSIFIER;
			val feature = if(typeVar.declaredCovariant) {
				TypesPackage.eINSTANCE.typeVariable_DeclaredCovariant
			} else {
				TypesPackage.eINSTANCE.typeVariable_DeclaredContravariant
			};
			addIssue(message, typeVar, feature, CLF_DEF_SITE_VARIANCE_ONLY_IN_CLASSIFIER);
		}
	}

	/**
	 * Ensures that type variables declared as covariant only appear in covariant positions and type variables declared
	 * as contravariant only appear in contravariant positions. Type variables declared to be invariant do not require
	 * such a check.
	 */
	@Check
	def void checkTypeVariableVariance(ParameterizedTypeRef typeRefInAST) {
		val tv = typeRefInAST.declaredType;
		if(tv instanceof TypeVariable) {
			val variance = tv.variance;
			if(variance!==Variance.INV) {
				val varianceOfPos = N4JSLanguageUtils.getVarianceOfPosition(typeRefInAST);
				if(varianceOfPos!==null && variance!==varianceOfPos) {
					val msg = getMessageForCLF_TYPE_VARIABLE_AT_INVALID_POSITION(variance.getDescriptiveString(true),
						varianceOfPos.getDescriptiveString(false));
					addIssue(msg, typeRefInAST, CLF_TYPE_VARIABLE_AT_INVALID_POSITION);
				}
			}
		}
	}
}
