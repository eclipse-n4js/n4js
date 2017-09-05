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
import com.google.common.base.Stopwatch
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
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.MemberAccess
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.organize.imports.ImportStateCalculator
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TExportableElement
import org.eclipse.n4js.ui.contentassist.N4JSCandidateFilter
import org.eclipse.n4js.utils.Log
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.IScopeProvider

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
	private IScopeProvider scopeProvider;
//	@Inject
//	private N4JSScopeProvider scopeProvider;

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

		return sb.toString();
	}

	/** Calculate new Imports. */
	private def ArrayList<ImportDeclaration> resolveMissingImports(Script script, Set<String> namesThatWeBroke,
		Interaction interaction) throws BreakException {
			println("\u2503\t\u250F resolveMissingImports  ")
			val sw = Stopwatch.createStarted();
			
			
			val prj = core.findProject(script.eResource.URI).orNull

		val scopeTypeRef = scopeProvider.getScope(script, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
//		val scopeIdRef = scopeProvider.getScopeForContentAssist(script, N4JSPackage.Literals.IDENTIFIER_REF__ID);
	val scopeIdRef = IScope.NULLSCOPE
		
		println("\u2503\t\u2503\t\u250F createResolutionsFromSopes  ")
		val sw3 = Stopwatch.createStarted();
		val Multimap<String, ImportableObject> resolutions = createResolutionsForUnresolvedCrossRefs(script, scopeIdRef, scopeTypeRef, namesThatWeBroke)
		sw3.stop();
		println("\u2503\t\u2503\t\u2517 createResolutionsFromSopes took " + sw3)
		

		val solutions = resolutions.asMap.filter[p1, p2|p2.size == 1]

		val ret = <ImportDeclaration>newArrayList();

		solutions.forEach [ name, importable |
			val imp = importable.head;
			ret.add(importsFactory.createImport(imp, prj,nodelessMarker));
		]

		// ignore broken names, for which imports will be added due to unresolved refs
		namesThatWeBroke.removeAll(solutions.keySet)

		// Ask user to disambiguate things:
		val Map<String, Collection<ImportableObject>> ambiguousSolution = resolutions.asMap.filter[p1, p2|p2.size > 1];
		
		val Multimap<String, ImportableObject> forDisambiguation = LinkedHashMultimap.create();
		ambiguousSolution.forEach[p1, p2|forDisambiguation.putAll(p1, p2)];

		// add potential solutions for still broken names
		namesThatWeBroke.forEach [ brokenName |
			// if there is disambiguation pending for a given name, don't duplicate solutions
			if (!forDisambiguation.keySet.contains(brokenName)) {
				val idSolutions = new HashSet<ImportableObject>();
				idSolutions.addAll(scopeIdRef.mapToImportableObjects(brokenName));
				idSolutions.addAll(scopeTypeRef.mapToImportableObjects(brokenName));
				//nothing to disambiguate if there is only one solution
				if(idSolutions.size == 1){
					ret.add(importsFactory.createImport(idSolutions.head, prj,nodelessMarker))
					return
				}
				
				val containsOnlyNamespaces = idSolutions.filter[!it.asNamespace].empty
				
				if(containsOnlyNamespaces){
					ret.add(importsFactory.createImport(idSolutions.head, prj,nodelessMarker))
					return
				}
				
				
				forDisambiguation.putAll(brokenName, idSolutions);
			}
		];
		
		//try to automatically disambiguate
		val resolved = new HashSet<String>();
		forDisambiguation.keySet.forEach[key|
			val multiSolutions = forDisambiguation.get(key);
			val containsOnlyNamespaces = multiSolutions.filter[!it.asNamespace].empty
				if(containsOnlyNamespaces){
					ret.add(importsFactory.createImport(multiSolutions.head, prj,nodelessMarker))
					resolved.add(key)
				}
		]
		resolved.forEach[key|
			forDisambiguation.removeAll(key)
		]
		
		
		val chosenSolutions = DisambiguateUtil.disambiguate(forDisambiguation, interaction, importProvidedElementLabelprovider);

		chosenSolutions.forEach[ret.add(importsFactory.createImport(it, prj, nodelessMarker))];

		sw.stop();
		println("\u2503\t\u2517 resolveMissingImports took " + sw)
		return ret;
	}
	
	/** Finds unresolved cross references in this script and finds candidate imports based on the provided scopes. */
	private def Multimap<String, ImportableObject> createResolutionsForUnresolvedCrossRefs(Script script,
		IScope scopeIdRef, IScope scopeTypeRef, Set<String> namesThatWeBroke) {
		val Multimap<String, ImportableObject> resolutions = LinkedHashMultimap.create();
		val Multimap<String, TExportableElement> resolutions2 = LinkedHashMultimap.create();
		
		// the following are named imports, that did not resolve. The issue lies in the Project-configuration and
		// cannot be solved here. Candidate for quick fix.
		val Iterable<ReferenceProxyInfo> unresolved = script.findProxyCrossRefInfo.filter[referenceFilter.test(it)].filter[it.eobject instanceof MemberAccess === false]
//		val Iterable<ReferenceProxyInfo> unresolvedIdRefs = unresolved.filter[it.eobject instanceof IdentifierRef]
		
		val brokenNames = new HashSet<String>();
		brokenNames.addAll(namesThatWeBroke)
		brokenNames.addAll(unresolved.map[it.name]);
		
		// in situations like "new A()" at the position of A an IdentifierRef is unresolved.
		// The solution is provided as a TypeRef. So TypeRef-solutions can be used in places where an IDref is desired.
		// --> obj IdRef :  scopeIdRef & scopeTypeRef
		// --> obj TypeRef : only TypeRef
		
//		addResolutionsFromScope(resolutions, scopeIdRef, unresolvedIdRefs, script.eResource);
		addResolutionsFromScope2(resolutions2, scopeTypeRef, brokenNames, script.eResource);
		addResolutionsFromScope(resolutions, scopeTypeRef, unresolved, script.eResource);

		return resolutions

	}
	
		private def void addResolutionsFromScope2(Multimap<String, TExportableElement> resolution, IScope scope,
		Iterable<String> unresolved, Resource contextResource) {
		println("\u2503\t\u2503\t\u250F\t\u250F index  ")
		val sw3 = Stopwatch.createStarted();
		val pr = Optional.fromNullable(core.findProject(contextResource.URI).orNull)
		val resSet = core.createResourceSet(pr)
		val index = core.getXtextIndex(resSet)
		val resources = index.allResourceDescriptions
		resources.forEach [ res |
			var potentialMatch = false
			val exportedIEODs = res.exportedObjects.iterator
			while (exportedIEODs.hasNext && potentialMatch === false) {
				val ieod = exportedIEODs.next
				if (checkNotGlobal(ieod.qualifiedName)) {
					potentialMatch = unresolved.findFirst [rpe|
						isCandidate(ieod, rpe) && candidateFilter.apply(ieod)
					] !== null
				}
			}

			if (potentialMatch) {
				val tmod = core.loadModuleFromIndex(resSet, res, false);
				tmod.topLevelTypes.filter[it.isExported].forEach [ te |
					unresolved.forEach [ proxyInfo |
						val String usedName = proxyInfo
						if (isCandidate(te, usedName)) {
							resolution.add(usedName, te, contextResource)
						}
					]
				]
				tmod.variables.filter[it.isExported].forEach [ te |
					unresolved.forEach [ proxyInfo |
						val String usedName = proxyInfo
						if (isCandidate(te, usedName)) {
							resolution.add(usedName, te, contextResource)
						}
					]
				]
			}
		]
		sw3.stop();
		println("\u2503\t\u2503\t\u2517\t\u2517 index took " + sw3)
		println("index resolutions " + resolution.size)
		resolution.keySet.forEach[key|
			println(" key " + key)
			resolution.get(key).forEach[itm|
				println("  -> val " + itm.name + " : " + itm.exportedName + " :: " + itm.eResource.URI)
			]
		]
		
	
			
		
	}
	
	private def void addResolutionsFromScope(Multimap<String, ImportableObject> resolution, IScope scope,
		Iterable<ReferenceProxyInfo> unresolved, Resource contextResource) {
			
		if (!unresolved.empty) {
		println("\u2503\t\u2503\t\u250F\t\u250F scope  ")
		val sw3 = Stopwatch.createStarted();
			val unprocessedIDRefNames = new HashSet<String>
			unresolved.forEach[unprocessedIDRefNames.add(it.name)]
			val idscopeIter = scope.allElements.iterator
			while (idscopeIter.hasNext) {
				val ieod = idscopeIter.next
				if (checkNotGlobal(ieod.qualifiedName)) {
					unresolved.forEach [ proxyInfo |
						val String usedName = proxyInfo.name
						if (isCandidate(ieod, usedName) && candidateFilter.apply(ieod)) {
							resolution.add(usedName, ieod, contextResource)
						}
					]
				}
			}
		
		sw3.stop();
		println("\u2503\t\u2503\t\u2517\t\u2517 scope took " + sw3)
		println("scope resolutions " + resolution.size)
		resolution.keySet.forEach[key|
			println(" key " + key)
			resolution.get(key).forEach[itm|
				println("  -> val " + itm.name + " : " + itm.eobj.EObjectURI)
			]
		]
		}
	}
	
	/** Filters scope by given name and maps result to importable objects. */
	private def Iterable<ImportableObject> mapToImportableObjects(IScope scopeIdRef, String brokenName) {
		scopeIdRef
			.allElements
			.filter[candidateFilter.apply(it)]
			.filter[it.name.lastSegment == brokenName]
			.map[new ImportableObject(brokenName, it, it.isDefaultExport)]
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

			return resolution.put(segments.head,new ImportableObject(segments.head, ieoDescription, ieoDescription.isDefaultExport, true))
		} else {
			return resolution.put(usedName,
				new ImportableObject(usedName, ieoDescription, ieoDescription.isDefaultExport))
		}
	}
	
	/** Creates {@link ImportableObject} from provided name and object description. Result is added to the collection. */
	private def boolean add(Multimap<String, TExportableElement> resolution, String usedName,
		TExportableElement te, Resource contextResource) {
			if(te.exportedName === null)
				throw new RuntimeException("NULLLLLLL")

		// potential match via namespace
		if (usedName.contains(".")) {
			val segments = usedName.split("\\.")

			if (segments.size != 2)
				return false // does not look like namespace

			if (te.exportedName != segments.last)
				return false // name does not match

			return resolution.put(segments.head,te)
		} else {
			return resolution.put(usedName,
				te)
		}
	}

	private def boolean isDefaultExport(IEObjectDescription description) {
		val eo = description.getEObjectOrProxy;
		if(eo instanceof TExportableElement)
			return eo.exportedName  == EXPORT_DEFAULT_NAME
		return false
		
	}

	/** return true if {@code description} is a possible candidate for an element with name {@code usedName}. */
	private def boolean isCandidate(IEObjectDescription description, String usedName) {
		val qName = description.name;
		
		//normal match
		if(qName.lastSegment == usedName)
			return true
		
		//potential match via namespace
		if(usedName.contains(".")){
			val segments = usedName.split("\\.")
			// 2 segments, potential namespace access
			if(segments.size  == 2 && (segments.last == qName.lastSegment))
				return true
		}
		
		return qName.lastSegment == EXPORT_DEFAULT_NAME && qName.segmentCount > 1 && qName.getSegment(qName.segmentCount - 2) == usedName
	}
	
	/** return true if {@code description} is a possible candidate for an element with name {@code usedName}. */
	private def boolean isCandidate(TExportableElement te, String usedName) {
		val qName = te.exportedName
		val qSegments = qName.split("\\.")
		
		//normal match
		if(qSegments.last == usedName)
			return true
		
		//potential match via namespace
		if(usedName.contains(".")){
			val segments = usedName.split("\\.")
			// 2 segments, potential namespace access
			if(segments.size  == 2 && (segments.last == qSegments.last))
				return true
		}
		
		return qSegments.last == EXPORT_DEFAULT_NAME && qSegments.size > 1 && qSegments.get(qSegments.size - 2) == usedName
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

	/** We do not add imports for global elements. */
	private def boolean checkNotGlobal(QualifiedName qualifiedName) {
		return !(qualifiedName.segmentCount == 2 &&
			N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT.equals(qualifiedName.getFirstSegment()))
	}
}
