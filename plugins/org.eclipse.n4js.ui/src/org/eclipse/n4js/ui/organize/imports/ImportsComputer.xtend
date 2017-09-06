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
package org.eclipse.n4js.ui.organize.imports;

import com.google.common.base.Optional
import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import com.google.inject.Inject
import java.util.ArrayList
import java.util.Collection
import java.util.HashSet
import java.util.Map
import java.util.Set
import org.eclipse.emf.common.notify.Adapter
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.MemberAccess
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.organize.imports.ImportStateCalculator
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker
import org.eclipse.n4js.scoping.utils.ImportSpecifierUtil
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TExportableElement
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ui.contentassist.N4JSCandidateFilter
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.utils.StopWatchPrintUtil
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.XtextResource

import static org.eclipse.n4js.ui.organize.imports.XtextResourceUtils.*
import static org.eclipse.n4js.validation.helper.N4JSLanguageConstants.EXPORT_DEFAULT_NAME

import static extension org.eclipse.n4js.ui.organize.imports.UnresolveProxyCrossRefUtil.*

/**
 * Computes imports required by the given resource. In principle removes unused imports, adds missing imports, sorts imports - all in one go.
 * Does not actually change the resource, but prepares final state that callers can apply to the resource.
 */
@Log
public class ImportsComputer {

	@Inject
	private ImportStateCalculator importStateCalculator;

	@Inject
	private ImportsFactory importsFactory;

	@Inject
	private IReferenceFilter referenceFilter;

	@Inject
	private ImportProvidedElementLabelprovider importProvidedElementLabelprovider;

	/** Adapter used to mark programmatically created AST-Elements without a corresponding parse tree node. */
	private final Adapter nodelessMarker = new AdapterImpl();

	@Inject
	private N4JSCandidateFilter candidateFilter;

	@Inject
	private IN4JSCore core;

	@Inject
	private ImportDeclarationTextHelper declarationTextHelper

	@Inject
	private TypeVisibilityChecker typeVisibilityChecker;

	@Inject
	private VariableVisibilityChecker varVisibilityChecker;

	/**
	 * Calculate the real content of the new import section in the file header.
	 * 
	 * @param xtextResource the resource to organize
	 * @param lineEnding current active line ending in file
	 * @param interaction mode of handling ambiguous situations
	 * @return new import section, might be an empty string
	 * @throws BreakException when import resolution is ambiguous and mode is {@link Interaction#breakBuild}
	 */
	public def String getOrganizedImportSection(XtextResource xtextResource, String lineEnding,
		Interaction interaction) throws BreakException {
		val sw0 = new StopWatchPrintUtil("getOrganizedImportSection", 1, 100);

		val StringBuilder sb = new StringBuilder();
		val Script script = getScript(xtextResource);

		// Use original import list as base for result of organizing
		val resultingImports = script.getScriptElements().filter(ImportDeclaration).toList

		// use validation algorithm to check for problems.
		val reg = importStateCalculator.calculateImportstate(script);

		// Strict&Naive : remove all problems
		reg.removeDuplicatedImportDeclarations(resultingImports)
		reg.removeLocalNameCollisions(resultingImports, nodelessMarker)
		reg.removeDuplicatedImportsOfSameelement(resultingImports, nodelessMarker)
		reg.removeBrokenImports(resultingImports, nodelessMarker)
		reg.removeUnusedImports(resultingImports, nodelessMarker)

		// collect names for which we have removed imports
		val brokenNames = reg.calcRemovedImportedNames()

		// determine things to import (unresolved imports and things we broke)
		resultingImports += resolveMissingImports(script, brokenNames, interaction)

		// resolve all resulting.
		resultingImports.forEach[EcoreUtil.resolveAll(it)]

		// Sort all import
		ImportsSorter.sortByImport(resultingImports)

		// Add to output.
		resultingImports.forEach [
			val text = declarationTextHelper.extractPureText(it, xtextResource, nodelessMarker);
			sb.append(text).append(lineEnding);
		]

//		 remove last line feed:
		val length = sb.length
		if (length > lineEnding.length) {
			// ret.deleteCharAt(length-1)
			sb.delete(length - lineEnding.length, length)
		}

		sw0.stop();
		return sb.toString();
	}

	/** Calculate new Imports. */
	private def ArrayList<ImportDeclaration> resolveMissingImports(Script script, Set<String> namesThatWeBroke,
		Interaction interaction) throws BreakException {
		val sw1 = new StopWatchPrintUtil("resolveMissingImports", 2, 100);

		val prj = core.findProject(script.eResource.URI).orNull

		val sw2 =  new StopWatchPrintUtil("createResolutionsForBrokenNames", 3, 100);
		val Multimap<String, ImportableObject> resolutions = createResolutionsForBrokenNames(script, namesThatWeBroke);
		sw2.stop();

		val solutions = resolutions.asMap.filter[p1, p2|p2.size == 1]

		val ret = <ImportDeclaration>newArrayList();

		solutions.forEach [ name, importable |
			val imp = importable.head;
			ret.add(importsFactory.createImport(imp, prj, nodelessMarker));
		]

		// ignore broken names, for which imports will be added due to unresolved refs
		namesThatWeBroke.removeAll(solutions.keySet)

		// Ask user to disambiguate things:
		val Map<String, Collection<ImportableObject>> ambiguousSolution = resolutions.asMap.filter[p1, p2|p2.size > 1];

		val Multimap<String, ImportableObject> forDisambiguation = LinkedHashMultimap.create();
		ambiguousSolution.forEach[p1, p2|forDisambiguation.putAll(p1, p2)];

		// try to automatically disambiguate
		val resolved = new HashSet<String>();
		forDisambiguation.keySet.forEach [ key |
			val multiSolutions = forDisambiguation.get(key);
			val containsOnlyNamespaces = multiSolutions.filter[!it.asNamespace].empty
			if (containsOnlyNamespaces) {
				ret.add(importsFactory.createImport(multiSolutions.head, prj, nodelessMarker))
				resolved.add(key)
			}
		]
		resolved.forEach [ key |
			forDisambiguation.removeAll(key)
		]

		val chosenSolutions = DisambiguateUtil.disambiguate(forDisambiguation, interaction,
			importProvidedElementLabelprovider);

		chosenSolutions.forEach[ret.add(importsFactory.createImport(it, prj, nodelessMarker))];

		sw1.stop();
		return ret;
	}

	/** Finds unresolved cross references in this script and combines them with provided broken names. 
	 * @returns list of resolutions for all broken names.
	 */
	private def Multimap<String, ImportableObject> createResolutionsForBrokenNames(Script script,
		Set<String> namesThatWeBroke) {
		val Multimap<String, ImportableObject> resolutions2 = LinkedHashMultimap.create();

		val Iterable<ReferenceProxyInfo> unresolved = script.findProxyCrossRefInfo.filter[referenceFilter.test(it)].
			filter[it.eobject instanceof MemberAccess === false]

		val brokenNames = new HashSet<String>();
		brokenNames.addAll(namesThatWeBroke)
		brokenNames.addAll(unresolved.map[it.name]);

		addResolutionsFromIndex(resolutions2, brokenNames, script.eResource);

		return resolutions2
	}

	/**
	 * Obtains index for based on the provided resource. Matches all broken names against object descriptions in the index.
	 * Those that pass checks are added to the resolutions map as potential solutions.
	 */
	private def void addResolutionsFromIndex(Multimap<String, ImportableObject> resolution,
		Iterable<String> brokenNames, Resource contextResource) {
		val sw = new StopWatchPrintUtil( "index", 4, 100);

		val contextProject = core.findProject(contextResource.URI).orNull
		val resourceSet = core.createResourceSet(Optional.fromNullable(contextProject))
		val resources = core.getXtextIndex(resourceSet).allResourceDescriptions
		resources.forEach [ res |
			val candidateProject = core.findProject(res.URI).orNull
			if (candidateProject !== null) {
				val isInDeps = ImportSpecifierUtil.findProject(candidateProject.projectId, contextProject) !== null
				if (isInDeps) {
					val exportedIEODs = res.exportedObjects.iterator
					while (exportedIEODs.hasNext) {
						val ieod = exportedIEODs.next
						if (isNotModule(ieod.EObjectURI)) {
							brokenNames.forEach [ usedName |
								if (isCandidate(ieod, usedName) && candidateFilter.apply(ieod) &&
									isVisible(ieod, contextResource)) {
									resolution.add(usedName, ieod, contextResource)
								}
							]
						}
					}
				}
			}
		]
		
		sw.stop
	}

	/** Creates {@link ImportableObject} from provided name and object description. Result is added to the collection. */
	private def boolean add(Multimap<String, ImportableObject> resolution, String usedName,
		IEObjectDescription ieoDescription, Resource contextResource) {

		// potential match via namespace
		if (usedName.contains(".")) {
			val segments = usedName.split("\\.")

			if (segments.size != 2)
				return false // does not look like namespace
			val eo = tryGetEObjectOrProxy(ieoDescription.EObjectOrProxy, contextResource);
			if (eo instanceof TExportableElement === false)
				return false // not exported element
			if ((eo as TExportableElement).exportedName != segments.last)
				return false // name does not match
			return resolution.put(segments.head,
				new ImportableObject(segments.head, eo as TExportableElement,
					isDefaultExport(ieoDescription, contextResource), true))
		} else {
			val eo = tryGetEObjectOrProxy(ieoDescription.EObjectOrProxy, contextResource);
			return resolution.put(usedName,
				new ImportableObject(usedName, eo as TExportableElement,
					isDefaultExport(ieoDescription, contextResource)))
		}
	}

	private def boolean isDefaultExport(IEObjectDescription description, Resource contextResource) {
		val eo = tryGetEObjectOrProxy(description.getEObjectOrProxy, contextResource)
		if (eo instanceof TExportableElement)
			return eo.exportedName == EXPORT_DEFAULT_NAME
		return false

	}

	/** return true if {@code description} is a possible candidate for an element with name {@code usedName}. */
	private def boolean isCandidate(IEObjectDescription description, String usedName) {
		val qName = description.name;

		// normal match
		if (qName.lastSegment == usedName)
			return true

		// potential match via namespace
		if (usedName.contains(".")) {
			val segments = usedName.split("\\.")
			// 2 segments, potential namespace access
			if (segments.size == 2 && (segments.last == qName.lastSegment))
				return true
		}

		return qName.lastSegment == EXPORT_DEFAULT_NAME && qName.segmentCount > 1 &&
			qName.getSegment(qName.segmentCount - 2) == usedName
	}

	/**
	 * In most cases we cannot compute imports from proxy objects, as essential information like declared name, exported name etc.
	 * are not available. If the provided object is a proxy, tries to resolve it. If object is still a proxy logs a warning. In either case returns result of resolving a proxy
	 * @returns resolved EObject or proxy
	 */
	private def EObject tryGetEObjectOrProxy(EObject eobject, Resource contextResource) {
		var eo = eobject
		if (eo.eIsProxy) {
			eo = EcoreUtil.resolve(eo, contextResource)
			var data = "";
			if (eo.eIsProxy) {
				if (eo instanceof TClass) {
					data = eo.name + " "
				}
				logger.warn("Cannot resolve proxy " + data + eo)
			}
		}
		return eo;
	}

	/**
	 * Checks if the provided uri does not point to a module. For {@link IEObjectDescription} instances
	 * the URI for modules (files) will be in form {@code some/path/1} while for the AST elements inside the module
	 * it will be {@code some/path/1/@topLevelTypes.4} or {@code some/path/1/@variables.7}
	 */
	private def boolean isNotModule(URI uri) {
		val res = uri?.fragment.endsWith("/1")
		return !res
	}

	private def boolean isVisible(IEObjectDescription ieod, Resource contextResource) {
		val eo = tryGetEObjectOrProxy(ieod.EObjectOrProxy, contextResource)
		if (eo instanceof Type && isSubtypeOfType(eo.eClass))
			return typeVisibilityChecker.isVisible(contextResource, eo as Type).visibility

		if (eo instanceof TVariable && isSubtypeOfIdentifiable(eo.eClass))
			return varVisibilityChecker.isVisible(contextResource, eo as TVariable).visibility

		return false

	}

	/**
	 * Returns <code>true</code> if the given {@code type} is a subtype of {@link IdentifiableElement}.
	 * @see org.eclipse.n4js.scoping.N4JSGlobalScopeProvider.isSubtypeOfIdentifiable(EClass)
	 */
	protected def boolean isSubtypeOfIdentifiable(EClass type) {
		return type === TypesPackage.Literals.IDENTIFIABLE_ELEMENT ||
			type.getEPackage() === TypesPackage.eINSTANCE &&
				TypesPackage.Literals.IDENTIFIABLE_ELEMENT.isSuperTypeOf(type);
	}

	/**
	 * Returns <code>true</code> if the given {@code type} is a subtype of {@link Type}.
	 * @see org.eclipse.n4js.ts.scoping.builtin.DefaultN4GlobalScopeProvider.isSubtypeOfType(EClass)
	 */
	protected def boolean isSubtypeOfType(EClass type) {
		return type === TypesPackage.Literals.TYPE ||
			type.getEPackage() === TypesPackage.eINSTANCE && TypesPackage.Literals.TYPE.isSuperTypeOf(type);
	}
}
