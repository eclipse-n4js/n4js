/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.ide.contentassist;

import java.util.List;

import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;

import com.google.inject.Inject;

/**
 * Provides completion proposals specific to N4JS package json files.
 */
public class JSONIdeContentProposalProvider extends IdeContentProposalProvider {

	@Inject
	private JSONGrammarAccess grammarAccess;

	@Inject
	private JSONIdeTemplateProposalProvider templateProposalProvider;

	@Inject
	private IN4JSCore n4jsCore;

	@Override
	protected void _createProposals(Keyword keyword, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {
		if (keyword.getValue().length() < 4)
			return; // filter out short keywords but still propose true, false, null
		super._createProposals(keyword, context, acceptor);
	}

	@Override
	protected void _createProposals(Assignment assignment, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {
		AbstractElement terminal = assignment.getTerminal();
		if (terminal instanceof CrossReference) {
			createProposals(terminal, context, acceptor);
		}
		if (assignment == grammarAccess.getNameValuePairAccess().getNameAssignment_0()) {
			List<String> namePath = CompletionUtils.getJsonPathNames(context.getCurrentModel());
			proposeLocalPackages(context, acceptor, namePath);
		}
		if (assignment == grammarAccess.getNameValuePairAccess().getValueAssignment_2()
				&& context.getCurrentModel() instanceof NameValuePair) {
			List<String> namePath = CompletionUtils.getJsonPathNames(context.getCurrentModel());
			proposeVersions(context, acceptor, namePath);
			proposeProjectTypes(context, acceptor, namePath);
		}
	}

	private void proposeLocalPackages(ContentAssistContext context, IIdeContentProposalAcceptor acceptor,
			List<String> namePath) {
		if (!namePath.isEmpty()) {
			// somewhat poor heuristic: propose all projects that are known in the current workspace
			String last = namePath.get(namePath.size() - 1);
			if (PackageJsonProperties.DEPENDENCIES.name.equals(last)
					|| PackageJsonProperties.DEV_DEPENDENCIES.name.equals(last)) {
				for (IN4JSProject project : n4jsCore.findAllProjects()) {
					N4JSProjectName projectName = project.getProjectName();
					ContentAssistEntry entryForModule = getProposalCreator().createProposal(
							'"' + projectName.getRawName() + '"', context, ContentAssistEntry.KIND_MODULE, null);
					if (entryForModule != null) {
						acceptor.accept(entryForModule, getProposalPriorities().getDefaultPriority(entryForModule));
					}
				}
			}
		}
	}

	private void proposeProjectTypes(ContentAssistContext context, IIdeContentProposalAcceptor acceptor,
			List<String> namePath) {
		if (namePath.size() >= 2) {
			String n4js = namePath.get(namePath.size() - 2);
			String projectType = namePath.get(namePath.size() - 1);
			if (PackageJsonProperties.PROJECT_TYPE.name.equals(projectType)
					|| PackageJsonProperties.N4JS.name.equals(n4js)) {

				for (ProjectType type : ProjectType.values()) {
					String asString = PackageJsonUtils.getProjectTypeStringRepresentation(type);
					if (asString.equals(asString.toUpperCase())) {
						asString = asString.toLowerCase();
					}
					ContentAssistEntry entryForProjectType = getProposalCreator().createProposal(
							'"' + asString + '"', context, ContentAssistEntry.KIND_KEYWORD, null);
					if (entryForProjectType != null) {
						acceptor.accept(entryForProjectType,
								getProposalPriorities().getDefaultPriority(entryForProjectType));
					}
				}
			}
		}
	}

	private void proposeVersions(ContentAssistContext context, IIdeContentProposalAcceptor acceptor,
			List<String> namePath) {
		if (namePath.size() >= 2) {
			// somewhat poor heuristic: propose all projects that are known in the current workspace
			String devOrDep = namePath.get(namePath.size() - 2);
			if (PackageJsonProperties.DEPENDENCIES.name.equals(devOrDep)
					|| PackageJsonProperties.DEV_DEPENDENCIES.name.equals(devOrDep)) {

				NameValuePair pair = (NameValuePair) context.getCurrentModel();
				IN4JSProject project = n4jsCore.findProject(new N4JSProjectName(pair.getName())).orNull();
				if (project != null) {
					VersionNumber version = project.getVersion();
					ContentAssistEntry versionEntry = getProposalCreator().createProposal(
							'"' + version.toString() + '"', context, ContentAssistEntry.KIND_VALUE, null);
					acceptor.accept(versionEntry, getProposalPriorities().getDefaultPriority(versionEntry));
				}
				ContentAssistEntry wildcard = getProposalCreator().createProposal(
						"\"*\"", context, ContentAssistEntry.KIND_VALUE, null);
				acceptor.accept(wildcard, getProposalPriorities().getDefaultPriority(wildcard));
			}
		}
	}

	@Override
	protected void _createProposals(RuleCall ruleCall, ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {
		if (grammarAccess.getNameValuePairRule() == ruleCall.getRule()) {
			templateProposalProvider.createNameValueProposals(context, acceptor);
		}
	}

}
