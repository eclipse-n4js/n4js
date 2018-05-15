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
package org.eclipse.n4js.n4mf.ui.contentassist

import com.google.common.base.Predicate
import com.google.common.base.Predicates
import com.google.common.cache.CacheBuilder
import com.google.common.cache.LoadingCache
import com.google.inject.Inject
import com.google.inject.Singleton
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4mf.ModuleFilterType
import org.eclipse.n4js.n4mf.N4mfPackage
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess
import org.eclipse.n4js.n4mf.utils.ProjectTypePredicate
import org.eclipse.xtext.Assignment
import org.eclipse.xtext.EnumLiteralDeclaration
import org.eclipse.xtext.Group
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor

import static java.util.Collections.singleton
import static org.eclipse.n4js.n4mf.ProjectType.*
import static org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy.*
import static org.eclipse.n4js.n4mf.utils.ProjectTypePredicate.*

import static extension com.google.common.base.Strings.nullToEmpty
import static extension com.google.common.base.Strings.repeat
import static extension com.google.common.collect.Iterables.concat
import org.eclipse.n4js.n4mf.SourceContainerType

/**
 * Content assist proposal provider for N4JS manifest.
 */
@Singleton
class N4MFProposalProvider extends AbstractN4MFProposalProvider {

	static val LOGGER = Logger.getLogger(N4MFProposalProvider);

	static val WHITESPACE = ' ';
	static val NUMBER_OF_SPACES_PER_TAB = 4;
	static val TAB = WHITESPACE.repeat(NUMBER_OF_SPACES_PER_TAB);

	static val API_TYPE = anyOf(API);
	static val RE_TYPE = anyOf(RUNTIME_ENVIRONMENT);
	static val RL_TYPE = anyOf(RUNTIME_LIBRARY);
	static val TEST_TYPE = anyOf(TEST);
	static val RE_OR_RL_TYPE = anyOf(RUNTIME_ENVIRONMENT, RUNTIME_LIBRARY);

	val LoadingCache<Keyword, Pair<String, Integer>> keywordCache = CacheBuilder.newBuilder.build([
		// for groups
		if (eContainer instanceof Group) {
			val group = eContainer as Group;
			val groupKeywordValues = group.elements.filter(Keyword).map[value].toList;
			val featureName = group.collectAssignments.head?.feature.nullToEmpty;
			val feature = N4mfPackage.eINSTANCE.projectDescription.getEStructuralFeature(featureName);

			if (null !== feature) {
				if (groupKeywordValues.contains(':')) {
					return '''«value»: ''' -> 0;
				} else if (groupKeywordValues.contains('{') && groupKeywordValues.contains('}')) {
					return createBlockProposal(0) -> 2;
				}
			}
		// less generic case for enumerations
		} else if (eContainer instanceof EnumLiteralDeclaration) {
			val enumDecl = eContainer as EnumLiteralDeclaration;
			val instance = enumDecl.enumLiteral.instance;
			if (instance instanceof SourceContainerType || instance instanceof ModuleFilterType) {
				return createBlockProposal(1) -> NUMBER_OF_SPACES_PER_TAB + 2;
			}
		}
		if (LOGGER.debugEnabled) {
			LOGGER.warn('''Cannot find and cache modified proposal for keyword: «value».''');
		}
		return value -> 0;
	])

	@Inject private extension ResourceDescriptionsProvider;
	@Inject private extension N4MFGrammarAccess
	private extension N4mfPackage = N4mfPackage.eINSTANCE;

	override completeKeyword(Keyword it, ContentAssistContext ctx, extension ICompletionProposalAcceptor acceptor) {
		val pair = keywordCache.getUnchecked(it);
		val proposal = createCompletionProposal(pair.key, keywordDisplayString, image, ctx);
		if (proposal instanceof ConfigurableCompletionProposal) {
			// reset the cursor position if block proposal
			proposal.cursorPosition = proposal.cursorPosition - pair.value;
		}
		priorityHelper.adjustKeywordPriority(proposal, ctx.prefix);
		accept(proposal);
	}

	override complete_ProjectDependency(EObject eObject, RuleCall ruleCall, ContentAssistContext ctx, ICompletionProposalAcceptor acceptor) {
		val desc = eObject.findProjectDescription;
		if (null !== desc) {
			val ignoredIds = desc.projectDependencies.map[projectId];
			val predicate = switch(desc.projectType) {
				case TEST: not(RE_OR_RL_TYPE).forDescriptionMinis
				case API: createProjectPredicateForAPIs
				default: not(or(RE_OR_RL_TYPE, TEST_TYPE)).forDescriptionMinis
			}
			completeProposal(desc, ctx, acceptor, predicate, ignoredIds);
		}
	}

	override complete_ProjectReference(EObject eObject, RuleCall ruleCall, ContentAssistContext ctx, ICompletionProposalAcceptor acceptor) {
		if (ruleCall?.eContainer instanceof Assignment) {
			val desc = eObject.findProjectDescription;
			if (null === desc) {
				return;
			}
			val assignment = ruleCall?.eContainer as Assignment;
			switch(assignment.feature) {
				case projectDescriptionAccess.providedRuntimeLibrariesAssignment_7_2_0.feature: {
					val ignoredIds = desc.providedRuntimeLibraries.map[projectId];
					completeProposal(desc, ctx, acceptor, RL_TYPE.forDescriptionMinis, ignoredIds);
				}
				case projectDescriptionAccess.requiredRuntimeLibrariesAssignment_8_2_0.feature: {
					val ignoredIds = desc.requiredRuntimeLibraries.map[projectId];
					completeProposal(desc, ctx, acceptor, RL_TYPE.forDescriptionMinis, ignoredIds);
				}
				case projectDescriptionAccess.testedProjectsAssignment_19_2_0.feature: {
					val ignoredIds = desc.testedProjects.map[projectId];
					// If no dependencies yet, do not be so harsh... allow anything but test project
					val predicate = if (ignoredIds.nullOrEmpty) {
						not(TEST_TYPE);
					} else {
						// If there is at least one tested project, try to stick to that project type.
						val type = eObject.getProjectType(ignoredIds.head);
						if (null === type) not(TEST_TYPE) else anyOf(type);
					}
					completeProposal(desc, ctx, acceptor, predicate.forDescriptionMinis, ignoredIds);
				}
				case projectDescriptionAccess.extendedRuntimeEnvironmentAssignment_6_2.feature: {
					val currentExtendedREId = desc?.extendedRuntimeEnvironment?.projectId;
					completeProposal(desc, ctx, acceptor, RE_TYPE.forDescriptionMinis, singleton(currentExtendedREId));
				}
				case projectDescriptionAccess.implementedProjectsAssignment_11_2_0.feature: {
					val ignoredIds = desc.implementedProjects.map[projectId];
					val predicate = if (TEST == projectType) not(RE_OR_RL_TYPE) else not(or(RE_OR_RL_TYPE, TEST_TYPE));
					completeProposal(desc, ctx, acceptor, predicate.forDescriptionMinis, ignoredIds);
				}
			}
		}
	}

	private def findProjectDescription(EObject it) {

		if (it instanceof ProjectDescription) {
			return it;
		}

		var container = it?.eContainer;
		while (null !== container) {
			if (container instanceof ProjectDescription) {
				return container;
			}
			container = container.eContainer;
		}
		return null;
	}

	private def getProjectType(EObject anyObjectInTheResource, String projectId) {
		val desc = anyObjectInTheResource.allProjectDescriptions.findFirst[projectId == getUserData(PROJECT_ID_KEY)];
		return ProjectType.get(desc?.getUserData(PROJECT_TYPE_KEY))
	}

	private def completeProposal(ProjectDescription desc, ContentAssistContext ctx,
		extension ICompletionProposalAcceptor acceptor, Predicate<ProjectDescriptionMini> predicate, Iterable<String> ignoredIds) {

		val ownerId = desc.projectId;
		val ignoredIdsSet = ignoredIds.filterNull.toSet;
		if (null !== desc.eResource) {
			desc.allProjectDescriptions.map[
				val descriptionMini = new ProjectDescriptionMini(it);
				// TODO: Consider offering project project IDs loaded from the libraries.
				if (!descriptionMini.canBeReferenced
					|| !predicate.apply(descriptionMini)
					|| ownerId == descriptionMini.id
					|| ignoredIdsSet.contains(descriptionMini.id)) {
					return null;
				}

				createCompletionProposal(descriptionMini.id, ctx);
			].filterNull.forEach[accept(it)];
		}
	}

	/** Returns with all the project descriptions. */
	private def Iterable<IEObjectDescription> getAllProjectDescriptions(EObject it) {
		getResourceDescriptions(eResource).getExportedObjectsByType(projectDescription).filterNull
	}

	/** Collects the contained assignments and recursively the assignments of the contained groups (if any). */
	private def Iterable<Assignment> collectAssignments(Group it) {
		elements.filter(Assignment).concat(elements.filter(Group).map[collectAssignments].flatten);
	}

	/** Creates a block proposal with the 0 index indentation depth. 0 if top level element in the file.*/
	private def createBlockProposal(Keyword it, int indentationDepth) {
		value + ' {\n' + (indentationDepth + 1).tab + '\n' + indentationDepth.tab + '}';
	}

	private def tab(int times) {
		TAB.repeat(times);
	}

	/** Transforms a {@link ProjectTypePredicate} into a predicate for {@link ProjectDescriptionMini}. */
	private def Predicate<ProjectDescriptionMini> forDescriptionMinis(ProjectTypePredicate predicate) {
		return [predicate.apply(type)];
	}

	/**
	 * Returns with a new predicate instance that provides {@code true} only and if only the followings are true:
	 * <ul>
	 * <li>The project type is API or</li>
	 * <li>The project type is library and no implementation ID is specified for the library.</li>
	 * </ul>
	 * Otherwise the predicate provides {@code false} value.
	 */
	private def createProjectPredicateForAPIs() {
		return Predicates.or(API_TYPE.forDescriptionMinis, [LIBRARY == it.type && it.implementationId.nullOrEmpty]);
	}

	/**
	 * Bare minimum immutable representation of a {@link ProjectDescription}.
	 */
	private static final class ProjectDescriptionMini {

		val ProjectType type;
		val String id;
		val String implementationId;

		new(IEObjectDescription it) {
			type = 	ProjectType.get(getUserData(PROJECT_TYPE_KEY));
			id = getUserData(PROJECT_ID_KEY).nullToEmpty;
			implementationId = getUserData(IMPLEMENTATION_ID_KEY).nullToEmpty;
		}

		/** {@code true} if project type can be referenced and the project project ID is neither {@code null} nor empty string. */
		private def canBeReferenced() {
			return !id.nullOrEmpty && null !== type;
		}

		override toString() {
			'''«IF canBeReferenced»ID: «id» | Type: «type» «IF !implementationId.nullOrEmpty» Implementation ID: «implementationId»«ENDIF»«ELSE»Missing«ENDIF»''';
		}

	}


}
