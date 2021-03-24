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
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;

import com.google.inject.Inject;

/**
 * Provides completion proposals specific to N4JS package json files.
 */
public class JSONIdeContentProposalProvider extends IdeContentProposalProvider {

	@Inject
	private JSONGrammarAccess grammarAccess;

	@Inject
	private IPrefixMatcher prefixMatcher;

	@Inject
	private WorkspaceAccess workspaceAccess;

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
				for (N4JSProjectConfigSnapshot project : workspaceAccess.findAllProjects(context.getResource())) {
					N4JSProjectName projectName = project.getN4JSProjectName();
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
				N4JSProjectConfigSnapshot project = workspaceAccess.findProject(context.getResource(),
						new N4JSProjectName(pair.getName())).orNull();
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
			createNameValueProposals(context, acceptor);
		}
	}

	private void createNameValueProposals(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		EObject model = context.getCurrentModel();
		List<String> namePath = CompletionUtils.getJsonPathNames(model);
		Set<String> alreadyUsedNames = CompletionUtils.getAlreadyUsedNames(model);
		List<PackageJsonProperties> pathProps = PackageJsonProperties.valuesOfPath(namePath);
		for (PackageJsonProperties pathProp : pathProps) {
			String name = pathProp.name;
			String label = name;
			if (!alreadyUsedNames.contains(name) && this.isMatchingPairPrefix(context, name)) {
				if (context.getPrefix().startsWith("\"")) {
					label = '"' + name + '"';
				}
				String proposal = null;
				String kind = null;
				if (pathProp.valueType == JSONStringLiteral.class) {
					proposal = String.format("\"%s\": \"$1\"$0", name);
					kind = ContentAssistEntry.KIND_PROPERTY;
				} else if (pathProp.valueType == JSONArray.class) {
					proposal = String.format("\"%s\": [\n\t$1\n]$0", name);
					kind = ContentAssistEntry.KIND_VALUE;
				} else if (pathProp.valueType == JSONObject.class) {
					proposal = String.format("\"%s\": {\n\t$1\n}$0", name);
					kind = ContentAssistEntry.KIND_CLASS;
				}
				if (proposal != null) {
					addTemplateProposal(proposal, label, pathProp.description, kind, context, acceptor);
				}
			}
		}
		if (pathProps.isEmpty()) {
			addTemplateProposal("\"${1:name}\": \"$2\"$0", "<value>", "Generic name value pair",
					ContentAssistEntry.KIND_PROPERTY, context, acceptor);
			addTemplateProposal("\"${1:name}\": [\n\t$2\n]$0", "<array>", "Generic name array pair",
					ContentAssistEntry.KIND_VALUE, context, acceptor);
			addTemplateProposal("\"${1:name}\": {\n\t$2\n}$0", "<object>", "Generic name object pair",
					ContentAssistEntry.KIND_CLASS, context, acceptor);
		}
	}

	private void addTemplateProposal(
			String proposal,
			String label,
			String description,
			String kind,
			ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor) {
		if (getProposalCreator().isValidProposal(label, context.getPrefix(), context)) {
			ContentAssistEntry entry = new ContentAssistEntry();
			entry.setProposal(proposal);
			entry.setPrefix(context.getPrefix());
			entry.setKind(ContentAssistEntry.KIND_SNIPPET + ":" + kind);
			entry.setLabel(label);
			entry.setDescription(description);
			acceptor.accept(entry, getProposalPriorities().getDefaultPriority(entry));
		}
	}

	private boolean isMatchingPairPrefix(ContentAssistContext context, String name) {
		return (this.prefixMatcher.isCandidateMatchingPrefix(name, context.getPrefix()) ||
				this.prefixMatcher.isCandidateMatchingPrefix(("\"" + name), context.getPrefix()));
	}

}
