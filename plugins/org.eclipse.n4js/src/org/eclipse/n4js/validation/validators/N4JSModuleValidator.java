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

import static org.eclipse.n4js.utils.N4JSLanguageUtils.isNonStaticPolyfill;
import static org.eclipse.n4js.validation.IssueCodes.CLF_DUP_DEF_MODULE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_DUP_MODULE;
import static org.eclipse.n4js.validation.IssueCodes.SCR_HASHBANG_WRONG_LOCATION;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.drop;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toMap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.N4JSResourceValidator;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.MapExtensions;

import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Contains module-level validations, i.e. validations that need to be checked once per module / file. For example:
 * unique module names.
 * <p>
 * In addition to this class, some module-level validations are also implemented in method
 * {@link N4JSResourceValidator#validate(Resource, org.eclipse.xtext.validation.CheckMode, org.eclipse.xtext.util.CancelIndicator)}.
 */
public class N4JSModuleValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	IResourceDescription.Manager resourceDescriptionManager;

	@Inject
	IContainer.Manager containerManager;

	@Inject
	IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	WorkspaceAccess workspaceAccess;

	/**
	 * NEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 *
	 */
	@Check
	public void checkHashbang(Script script) {
		if (!Strings.isNullOrEmpty(script.getHashbang())) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(script, N4JSPackage.eINSTANCE.getScript_Hashbang());
			if (nodes.size() > 0) {
				int offset = nodes.get(0).getOffset();
				if (offset != 0) {
					addIssue(script, N4JSPackage.eINSTANCE.getScript_Hashbang(),
							SCR_HASHBANG_WRONG_LOCATION.toIssueItem());
				}
			}
		}
	}

	/**
	 * Validates the module name that is derived from the given script.
	 *
	 * This validation is defined for the script itself since we need it for the error marker anyway
	 */
	@Check
	public void checkUniqueName(Script script) {
		if (!N4JSGlobals.ALL_JS_FILE_EXTENSIONS.contains(URIUtils.fileExtension(script.eResource().getURI()))) {
			checkUniqueName(script, script.getModule());
		}
	}

	/**
	 * If the module exists, obtain its name and validate it for uniqueness.
	 */
	private void checkUniqueName(Script script, TModule module) {
		if (module == null) {
			return;
		}
		QualifiedName name = qualifiedNameConverter.toQualifiedName(module.getQualifiedName());
		if (name != null) {
			doCheckUniqueName(script, module, name);
		}
	}

	/**
	 * Find all other modules in the index with the same name and check all the obtained candidates for reachable
	 * duplicates.
	 */
	private void doCheckUniqueName(Script script, TModule module, QualifiedName name) {
		XtextResource resource = (XtextResource) module.eResource();
		IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(resource);
		Iterable<IEObjectDescription> others = index.getExportedObjects(TypesPackage.Literals.TMODULE, name, false);
		checkUniqueInIndex(script, module, others, () -> {
			IResourceDescription description = index.getResourceDescription(resource.getURI());
			if (description == null) {
				description = resourceDescriptionManager.getResourceDescription(resource);
			}
			if (description == null) {
				return Collections.emptyList();
			}
			return containerManager.getVisibleContainers(description, index);
		});
	}

	/**
	 * Check the found candidates for reach ability via the visible containers.
	 */
	private void checkUniqueInIndex(Script script, TModule module, Iterable<IEObjectDescription> descriptions,
			Provider<List<IContainer>> lazyContainersList) {
		Resource resource = module.eResource();

		Map<URI, IEObjectDescription> resourceURIs = MapExtensions.filter(
				toMap(descriptions, d -> d.getEObjectURI().trimFragment()),
				(uri, descr) -> uri != EcoreUtil2.getPlatformResourceOrNormalizedURI(resource));

		if (resourceURIs.size() > 0) {
			N4JSWorkspaceConfigSnapshot ws = workspaceAccess.getWorkspaceConfig(resource);
			N4JSProjectConfigSnapshot pr = ws.findProjectByNestedLocation(resource.getURI());
			Set<URI> visibleResourceURIs = new HashSet<>();
			for (IContainer container : lazyContainersList.get()) {
				visibleResourceURIs.addAll(toList(filter(resourceURIs.keySet(), uri -> {
					if (!container.hasResourceDescription(uri)) {
						return false;
					}
					N4JSProjectConfigSnapshot pr2 = workspaceAccess.findProjectByNestedLocation(script, uri);
					if (pr != null && pr2 != null) {
						return Objects.equals(pr.getName(), pr2.getName());
					}
					return false;
				})));
			}

			if (visibleResourceURIs.size() > 0) {
				// there can only be one static_polyfill_aware resource
				if (module.isStaticPolyfillAware()) {
					// we are the Aware. --> err, if others are as well
					// IDE-1735
					// for now: allow one other modules with same name.
					if (visibleResourceURIs.size() == 1)
						return;
				} else {
					if (module.isStaticPolyfillModule()) {
						// maybe one of the others is the Aware --> all OK, if none, then Error
						// IDE-1735 unless checking other files of @PolyfillAware, for now just don't issue
						if (visibleResourceURIs.size() == 1)
							return;
					}
				}

				// Normal Polyfill
				if (hasNonStaticPolyfill(script) && !module.isStaticPolyfillModule()) {
					// IDE-1735 in case of normal Polyfill this can't be mixed with static polyfills.
					return;
				}

				if (visibleResourceURIs.size() == 1) {
					URI uri1 = resource.getURI();
					URI uri2 = head(visibleResourceURIs);
					String qName1 = module.getQualifiedName();
					String qName2 = qualifiedNameConverter.toString(resourceURIs.get(uri2).getName());
					if (Objects.equals(qName1, qName2)) {
						String ext1 = URIUtils.fileExtension(uri1);
						String ext2 = URIUtils.fileExtension(uri2);

						URI jsUri = null;
						URI nonJsUri = null;
						String nonJsExt = null;
						if (N4JSGlobals.ALL_JS_FILE_EXTENSIONS.contains(ext1)) {
							jsUri = uri1;
							nonJsUri = uri2;
							nonJsExt = ext2;
						} else if (N4JSGlobals.ALL_JS_FILE_EXTENSIONS.contains(ext2)) {
							jsUri = uri2;
							nonJsUri = uri1;
							nonJsExt = ext1;
						}

						if (jsUri != null && nonJsUri != null && nonJsExt != null
								&& (nonJsExt == N4JSGlobals.N4JSD_FILE_EXTENSION
										|| nonJsExt == N4JSGlobals.DTS_FILE_EXTENSION)) {
							// it is allowed that a js module has an n4jsd or d.ts module with the same fqn
							return;
						}
					}
				}

				Set<URI> filteredMutVisibleResourceURIs = toSet(
						map(visibleResourceURIs, it -> it.deresolve(ws.getPath())));

				// non MainModules are follow normal visibility check
				// but MainModules have checks relaxed:
				if (module.isMainModule()) {
					if (pr == null) {
						return;
					}
					filteredMutVisibleResourceURIs = toSet(filter(filteredMutVisibleResourceURIs, u -> {
						if (pr == ws.findProjectByPath(u)) {
							// the same project, MainModule is hidden within the project
							// if other file with the same source container relative path
							// (also across different containers)
							String baseModuleSrcCon = ws.findSourceFolderContaining(resource.getURI()).getPath()
									.toString();
							String otherModuleSrcCon = ws.findSourceFolderContaining(u).getPath().toString();

							String baseModuleSrcContainerRelativePath = resource.getURI().toString()
									.substring(baseModuleSrcCon.length());
							String otherModuleSrcContainerRelativePath = u.toString()
									.substring(otherModuleSrcCon.length());
							return baseModuleSrcContainerRelativePath == otherModuleSrcContainerRelativePath;
						} else {
							// main modules are not hidden by main module in other projects
							// (resolved by project import)
							// and are not hidden by non main modules, as module import
							// will always resolve to local module (MainModule)
							return false;
						}
					}));
				}

				if (filteredMutVisibleResourceURIs.isEmpty()) {
					return;
				}
				List<URI> sortedMutVisibleResourceURIs = new ArrayList<>(filteredMutVisibleResourceURIs);
				Collections.sort(sortedMutVisibleResourceURIs, Comparator.comparing(URI::toString));

				// note: we know that the current TModule is never of a JS file since those are not validated
				Set<String> n4DefiExts = Set.of(N4JSGlobals.N4JSD_FILE_EXTENSION, N4JSGlobals.DTS_FILE_EXTENSION);
				Set<String> n4ImplExts = Set.of(N4JSGlobals.N4JS_FILE_EXTENSION, N4JSGlobals.N4JSX_FILE_EXTENSION);
				boolean curIsDef = n4DefiExts.contains(URIUtils.fileExtension(resource.getURI()));
				List<URI> n4DefiURIs = toList(
						filter(resourceURIs.keySet(), uri -> n4DefiExts.contains(URIUtils.fileExtension(uri))));
				List<URI> jsImplURIs = toList(filter(resourceURIs.keySet(),
						uri -> N4JSGlobals.ALL_JS_FILE_EXTENSIONS.contains(URIUtils.fileExtension(uri))));
				List<URI> n4ImplURIs = toList(
						filter(resourceURIs.keySet(), uri -> n4ImplExts.contains(URIUtils.fileExtension(uri))));

				if (curIsDef && n4DefiURIs.isEmpty() && n4ImplURIs.isEmpty()) {
					Multimap<String, URI> jsImplURIsPerExt = ArrayListMultimap.create();
					for (URI uri : jsImplURIs) {
						jsImplURIsPerExt.put(replaceJSXByJS(URIUtils.fileExtension(uri)), uri);
					}

					if (jsImplURIsPerExt.get(N4JSGlobals.JS_FILE_EXTENSION).size() <= 1
							&& jsImplURIsPerExt.get(N4JSGlobals.CJS_FILE_EXTENSION).size() <= 1
							&& jsImplURIsPerExt.get(N4JSGlobals.MJS_FILE_EXTENSION).size() <= 1) {
						// special case: it is legal to have an .n4jsd file with a combination of .js/.cjs/.mjs files
						// (but at most one per plain-JS extension)
						return;
					}
				}

				if (n4ImplURIs.isEmpty() && jsImplURIs.size() < 2 && curIsDef && n4DefiURIs.size() > 0) {
					// collision of definition modules
					URI implModule = (jsImplURIs.isEmpty()) ? null : jsImplURIs.get(0).deresolve(ws.getPath());
					String implModuleStr = (implModule == null) ? "unknown js module"
							: org.eclipse.n4js.utils.Strings.join("/", drop(implModule.segmentsList(), 1));
					String filePathStr = org.eclipse.n4js.utils.Strings.join("; ",
							map(filter(sortedMutVisibleResourceURIs,
									it -> implModule != it),
									it -> org.eclipse.n4js.utils.Strings.join("/", drop(it.segmentsList(), 1))));
					addIssue(script,
							CLF_DUP_DEF_MODULE.toIssueItem(module.getQualifiedName(), implModuleStr, filePathStr));
				} else {
					// collision of implementation modules
					// list all locations - give the user the possibility to check by himself.
					String filePathStr = org.eclipse.n4js.utils.Strings.join("; ", map(sortedMutVisibleResourceURIs,
							it -> org.eclipse.n4js.utils.Strings.join("/", drop(it.segmentsList(), 1))));
					addIssue(script, CLF_DUP_MODULE.toIssueItem(module.getQualifiedName(), filePathStr));
				}
			}
		}
	}

	/** @returns true if the script contains a toplevel-type annotated with a {@code @Polyfill}. */
	private boolean hasNonStaticPolyfill(Script script) {
		for (ScriptElement se : script.getScriptElements()) {
			if (se instanceof AnnotableElement) {
				if (isNonStaticPolyfill((AnnotableElement) se))
					return true;
			}
		}
		return false;
	}

	static private Pattern nonEmpty = Pattern.compile("^.+$", Pattern.MULTILINE);

	/**
	 * Annotates the script with a an error marker on the first AST element or the first none-empty line.
	 */
	private void addIssue(Script script, IssueItem issueItem) {

		ScriptElement first = head(script.getScriptElements());
		if (first != null) {
			addIssue(first, issueItem);
			return;
		}
		XtextResource resource = (XtextResource) script.eResource();
		IParseResult parseResult = resource.getParseResult();
		ICompositeNode rootNode = parseResult.getRootNode();
		String text = rootNode.getText();
		Matcher matcher = nonEmpty.matcher(text);
		int start = 0;
		int end = text.length();
		if (matcher.find()) {
			start = matcher.start();
			end = matcher.end();
		}
		addIssue(script, start, end - start, issueItem);
	}

	private static String replaceJSXByJS(String ext) {
		if (ext == N4JSGlobals.JSX_FILE_EXTENSION) {
			return N4JSGlobals.JS_FILE_EXTENSION;
		}
		return ext;
	}
}
