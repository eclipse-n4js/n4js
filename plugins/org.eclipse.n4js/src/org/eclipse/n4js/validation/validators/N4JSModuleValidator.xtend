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

import com.google.common.base.Strings
import com.google.inject.Inject
import com.google.inject.Provider
import java.util.List
import java.util.Set
import java.util.regex.Pattern
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.N4JSResourceValidator
import org.eclipse.n4js.workspace.WorkspaceAccess
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Contains module-level validations, i.e. validations that need to be checked once per module / file.
 * For example: unique module names.
 * <p>
 * In addition to this class, some module-level validations are also implemented in method
 * {@link N4JSResourceValidator#validate(Resource,CheckMode,CancelIndicator)}.
 */
class N4JSModuleValidator extends AbstractN4JSDeclarativeValidator {

	@Inject ResourceDescriptionsProvider resourceDescriptionsProvider

	@Inject IResourceDescription.Manager resourceDescriptionManager

	@Inject IContainer.Manager containerManager

	@Inject IQualifiedNameConverter qualifiedNameConverter

	@Inject WorkspaceAccess workspaceAccess;

	/**
	 * NEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * 
	 */
	@Check
	def void checkHashbang(Script script) {
		if (!Strings.isNullOrEmpty(script.hashbang)) {
			val nodes = NodeModelUtils.findNodesForFeature(script, N4JSPackage.eINSTANCE.script_Hashbang);
			if (nodes.size > 0) {
				val offset = nodes.get(0).offset;
				if (offset !== 0) {
					val message = IssueCodes.getMessageForSCR_HASHBANG_WRONG_LOCATION();
					addIssue(message, script, N4JSPackage.eINSTANCE.script_Hashbang, IssueCodes.SCR_HASHBANG_WRONG_LOCATION);
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
	def void checkUniqueName(Script script) {
		if (script.eResource.URI.fileExtension != N4JSGlobals.JS_FILE_EXTENSION) {
			script.checkUniqueName(script.module);
		}
	}

	/**
	 * If the module exists, obtain its name and validate it for uniqueness.
	 */
	private def void checkUniqueName(Script script, TModule module) {
		if (module === null) {
			return;
		}
		val name = qualifiedNameConverter.toQualifiedName(module.qualifiedName)
		if (name !== null) {
			script.doCheckUniqueName(module, name);
		}
	}

	/**
	 * Find all other modules in the index with the same name and check all the obtained
	 * candidates for reachable duplicates.
	 */
	private def void doCheckUniqueName(Script script, TModule module, QualifiedName name) {
		val resource = module.eResource as XtextResource
		val index = resourceDescriptionsProvider.getResourceDescriptions(resource)
		val others = index.getExportedObjects(TypesPackage.Literals.TMODULE, name, false)
		script.checkUniqueInIndex(module, others) [
			val description = index.getResourceDescription(resource.URI) ?: resourceDescriptionManager.getResourceDescription(resource)
			if (description === null) {
				return emptyList
			}
			return containerManager.getVisibleContainers(description, index)
		];
	}

	/**
	 * Check the found candidates for reach	ability via the visible containers.
	 */
	private def void checkUniqueInIndex(Script script, TModule module, Iterable<IEObjectDescription> descriptions, Provider<List<IContainer>> lazyContainersList) {
		val resource = module.eResource;

		val resourceURIs = descriptions.map[
			EObjectURI.trimFragment
		].filter[
			it != EcoreUtil2.getPlatformResourceOrNormalizedURI(resource) && fileExtension != N4JSGlobals.JS_FILE_EXTENSION
		].toSet;

		if (resourceURIs.size > 0) {
			val visibleResourceURIs = newHashSet;
			lazyContainersList.get.forEach[ container |
				visibleResourceURIs += resourceURIs.filter[ uri | container.hasResourceDescription(uri); ];
			];

			if (visibleResourceURIs.size > 0) {
				// there can only be one static_polyfill_aware resource
				if( module.isStaticPolyfillAware ) {
					// we are the Aware. --> err, if others are as well
					// IDE-1735
					// for now: allow one other modules with same name.
					if( visibleResourceURIs.size == 1) return;
				} else {
					if ( module.isStaticPolyfillModule ) {
						// maybe one of the others is the Aware --> all OK, if none, then Error
						// IDE-1735 unless checking other files of @PolyfillAware, for now just don't issue
						if( visibleResourceURIs.size == 1) return;
					}
				}

				// Normal Polyfill
				if( script.hasPolyfill && ! module.isStaticPolyfillModule ) {
					// IDE-1735 in case of normal Polyfill this can't be mixed with static polyfills.
					return;
				}

				var Set<URI>filteredMutVisibleResourceURIs = visibleResourceURIs;

				// non MainModules are follow normal visibility check
				// but MainModules have checks relaxed:
				if(module.isMainModule){
					val ws = workspaceAccess.getWorkspaceConfig(resource);
					val pr = ws.findProjectByPath(resource.URI);
					if (pr === null) {
						return;
					}
					filteredMutVisibleResourceURIs = filteredMutVisibleResourceURIs.filter[u| {
							if(pr == ws.findProjectByPath(u)){
								// the same project, MainModule is hidden within the project
								//if other file with the same source container relative path
								//(also across different containers)
								val baseModuleSrcCon = ws.findSourceFolderContaining(resource.URI).path.toString;
								val otherModuleSrcCon = ws.findSourceFolderContaining(u).path.toString;

								val baseModuleSrcContainerRelativePath = resource.URI.toString.substring(baseModuleSrcCon.length);
								val otherModuleSrcContainerRelativePath = u.toString.substring(otherModuleSrcCon.length);
								return baseModuleSrcContainerRelativePath == otherModuleSrcContainerRelativePath;
							}else{
								//main modules are not hidden by main module in other projects
								//(resolved by project import)
								//and are not hidden by non main modules, as module import
								//will always resolve to local module (MainModule)
								return false;
							}
						}].toSet;
				}

				if(filteredMutVisibleResourceURIs.isEmpty) return;

				// list all locations - give the user the possibility to check by himself.
				val filePathStr = filteredMutVisibleResourceURIs.map[segmentsList.drop(1).join('/')].join("; ");
				val message = IssueCodes.getMessageForCLF_DUP_MODULE(module.qualifiedName, filePathStr);
				addIssue(message, script, IssueCodes.CLF_DUP_MODULE);
			}
		}
	}


	/** @returns true if the script contains a toplevel-type annotated with a {@code @Polyfill}. */
	private def boolean hasPolyfill(Script script) {
		for (se:script.scriptElements){
			switch (se) {
				AnnotableElement : { if( se.isPolyfill  ) return true;}
			}
		}
		return false;
	}

	static private val nonEmpty = Pattern.compile('^.+$', Pattern.MULTILINE)

	/**
	 * Annotates the script with a an error marker on the first AST element or the first none-empty line.
	 */
	private def void addIssue(String message, Script script, String issueCode) {

		val first = script.scriptElements.head;
		if(first !== null){
			addIssue(message, first, issueCode);
			return;
		}
		val resource = script.eResource as XtextResource;
		val parseResult = resource.parseResult;
		val rootNode = parseResult.rootNode;
		val text = rootNode.text;
		val matcher = nonEmpty.matcher(text);
		var start = 0;
		var end = text.length;
		if (matcher.find) {
			start = matcher.start;
			end = matcher.end;
		}
		addIssue(message, script, start, end - start, issueCode);
	}
}
