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
package org.eclipse.n4js.ide.editor.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.editor.contentassist.N4JSIdeContentProposalProvider.N4JSCandidateFilter;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

/**
 * Creates proposals for content assist and also adds imports of the proposed element if necessary.
 */
public class ModuleSpecifierProposalCreator {

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private IScopeProvider scopeProvider;

	final private CamelCasePrefixMatcher prefixMatcher = new CamelCasePrefixMatcher();

	/**
	 * Retrieves possible reference targets from scope, including erroneous solutions (e.g., not visible targets). This
	 * list is further filtered here. This is a general pattern: Do not change or modify scoping for special content
	 * assist requirements, instead filter here.
	 *
	 * @param filter
	 *            by default an instance of {@link N4JSCandidateFilter} will be provided here.
	 */
	public void lookupCrossReference(
			EObject model,
			EReference reference,
			ContentAssistContext context,
			IIdeContentProposalAcceptor acceptor,
			Predicate<IEObjectDescription> filter) {

		if (model == null) {
			return;
		}
		Resource resource = model.eResource();
		if (!(resource instanceof N4JSResource)) {
			return;
		}
		N4JSResource resourceCasted = (N4JSResource) resource;
		N4JSWorkspaceConfigSnapshot wc = workspaceAccess.getWorkspaceConfig(resourceCasted);

		IScope scope = scopeProvider.getScope(model, reference);
		if (scope != null) {
			String prefix = context.getPrefix();
			prefix = prefix.startsWith("\"") ? prefix.substring(1) : prefix;
			for (IEObjectDescription elem : scope.getAllElements()) {
				String fileExtension = URIUtils.fileExtension(elem.getEObjectURI());
				String moduleSpecifier = elem.getQualifiedName() == null ? null : elem.getQualifiedName().toString("/");
				String msForMatching = replaceQNSeparators(moduleSpecifier);
				if (!N4JSGlobals.ALL_JS_FILE_EXTENSIONS.contains(fileExtension) &&
						moduleSpecifier != null && prefixMatcher.isCandidateMatchingPrefix(msForMatching, prefix)) {
					ContentAssistEntry cae = convertResolutionToContentAssistEntry(context, wc, elem, moduleSpecifier);
					acceptor.accept(cae, 0);
				}
			}
		}
	}

	private String replaceQNSeparators(String moduleSpecifier) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < moduleSpecifier.length(); i++) {
			char c = moduleSpecifier.charAt(i);
			if (c == '/') {
				if (i + 1 < moduleSpecifier.length()) {
					i++;
					char cn = moduleSpecifier.charAt(i);
					sb.append(Character.toUpperCase(cn));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private ContentAssistEntry convertResolutionToContentAssistEntry(ContentAssistContext context,
			N4JSWorkspaceConfigSnapshot wc,
			IEObjectDescription elem, String moduleSpecifier) {

		N4JSProjectConfigSnapshot prj = wc.findProjectContaining(elem.getEObjectURI());
		String prjName = prj == null ? "" : prj.getName();

		ContentAssistEntry cae = new ContentAssistEntry();
		cae.setPrefix(context.getPrefix().substring(1));
		cae.setProposal(moduleSpecifier);
		cae.setLabel(moduleSpecifier);
		cae.setDescription(prjName);
		// if (resolution.importToBeAdded != null) {
		// ReplaceRegion textReplacement = importHelper.getReplacementForImport(resource.getScript(),
		// resolution.importToBeAdded);
		// cae.getTextReplacements().add(textReplacement);
		// }
		// ReplaceRegion repl = new ReplaceRegion(context.getOffset(), context.getPrefix().length(), "replacement");
		// cae.getTextReplacements().add(repl);
		cae.setKind(ContentAssistEntry.KIND_MODULE);
		cae.setDocumentation("docu");
		return cae;
	}
}
