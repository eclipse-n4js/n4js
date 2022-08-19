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

import java.util.Objects;

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
		String prefix = context.getPrefix();
		prefix = prefix.startsWith("'") || prefix.startsWith("\"") ? prefix.substring(1) : prefix;

		N4JSResource resourceCasted = (N4JSResource) resource;
		N4JSWorkspaceConfigSnapshot wc = workspaceAccess.getWorkspaceConfig(resourceCasted);
		N4JSProjectConfigSnapshot ctxPrj = wc.findProjectContaining(model.eResource().getURI());

		for (String depId : ctxPrj.getDependencies()) {
			N4JSProjectConfigSnapshot depPrj = wc.findProjectByID(depId);
			if (depPrj != null) {
				String packageName = depPrj.getN4JSPackageName().toString();
				if (prefixMatcher.isCandidateMatchingPrefix(packageName, prefix)) {
					ContentAssistEntry cae = getProjectImportContentAssistEntry(context, depId, depPrj);
					acceptor.accept(cae, 0);
				}
			}
		}

		IScope scope = scopeProvider.getScope(model, reference);
		if (scope != null) {
			for (IEObjectDescription elem : scope.getAllElements()) {
				String fileExtension = URIUtils.fileExtension(elem.getEObjectURI());
				String moduleSpecifier = elem.getQualifiedName() == null ? null : elem.getQualifiedName().toString("/");
				if (!N4JSGlobals.ALL_JS_FILE_EXTENSIONS.contains(fileExtension) &&
						moduleSpecifier != null && prefixMatcher.isCandidateMatchingPrefix(moduleSpecifier, prefix)) {
					ContentAssistEntry cae = getModuleImportContentAssistEntry(context, wc, elem, moduleSpecifier,
							ctxPrj);
					acceptor.accept(cae, 0);
				}
			}
		}
	}

	private ContentAssistEntry getProjectImportContentAssistEntry(ContentAssistContext context,
			String depId, N4JSProjectConfigSnapshot depPrj) {

		String packageName = depPrj.getN4JSPackageName().toString();

		ContentAssistEntry cae = new ContentAssistEntry();
		cae.setPrefix(context.getPrefix().substring(1));
		cae.setProposal(packageName);
		cae.setLabel(packageName);
		cae.setDescription(depId);
		cae.setKind(ContentAssistEntry.KIND_MODULE);
		cae.setDocumentation("Project Import");
		return cae;
	}

	private ContentAssistEntry getModuleImportContentAssistEntry(ContentAssistContext context,
			N4JSWorkspaceConfigSnapshot wc,
			IEObjectDescription elem, String moduleSpecifier, N4JSProjectConfigSnapshot ctxPrj) {

		N4JSProjectConfigSnapshot prj = wc.findProjectContaining(elem.getEObjectURI());
		String prjName = prj == null ? "" : prj.getName();
		boolean projectImport = Objects.equals(moduleSpecifier, prjName);
		boolean makeCompleteImport = ctxPrj != null && prj != null && ctxPrj != prj && !projectImport;
		String proposal = (makeCompleteImport && prj != null ? prj.getPackageName() + "/" : "") + moduleSpecifier;

		ContentAssistEntry cae = new ContentAssistEntry();
		cae.setPrefix(context.getPrefix().substring(1));
		cae.setProposal(proposal);
		cae.setLabel(moduleSpecifier);
		cae.setDescription(prjName);
		cae.setKind(ContentAssistEntry.KIND_MODULE);
		cae.setDocumentation("Module Import");
		return cae;
	}
}
