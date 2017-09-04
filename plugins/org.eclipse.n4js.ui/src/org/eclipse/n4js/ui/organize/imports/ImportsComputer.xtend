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

import com.google.common.base.Stopwatch
import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import com.google.inject.Inject
import java.util.ArrayList
import java.util.Collection
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.common.notify.Adapter
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.jface.window.Window
import org.eclipse.n4js.n4JS.DefaultImportSpecifier
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.MemberAccess
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.organize.imports.ImportStateCalculator
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.TExportableElement
import org.eclipse.n4js.ui.contentassist.N4JSCandidateFilter
import org.eclipse.n4js.ui.organize.imports.BreakException.UserCanceledBreakException
import org.eclipse.n4js.ui.utils.ImportSpacerUserPreferenceHelper
import org.eclipse.n4js.utils.UtilN4
import org.eclipse.n4js.utils.collections.Multimaps3
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage
import org.eclipse.xtext.nodemodel.impl.LeafNodeWithSyntaxError
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.scoping.IScope

import static org.eclipse.n4js.parser.InternalSemicolonInjectingParser.SEMICOLON_INSERTED
import static org.eclipse.n4js.ui.organize.imports.XtextResourceUtils.*
import static org.eclipse.n4js.validation.helper.N4JSLanguageConstants.EXPORT_DEFAULT_NAME
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

import static extension org.eclipse.n4js.ui.organize.imports.UnresolveProxyCrossRefUtil.*
import org.eclipse.emf.ecore.EObject

/**
 * Computes imports required by the given resource. In principle removes unused imports, adds missing imports, sorts imports - all in one go.
 * Does not actually change the resource, but prepares final state that callers can apply to the resource.
 */
public class ImportsComputer {

	@Inject
	private ImportStateCalculator importStateCalculator;

	@Inject
	private N4JSScopeProvider scopeProvider;

	@Inject
	private ImportsFactory importsFactory;

	@Inject
	private IReferenceFilter referenceFilter;

	@Inject
	private ImportProvidedElementLabelprovider importProvidedElementLabelprovider;

	/** Adapter used to mark programmatically created AST-Elements without a corresponding parse tree node. */
	private final Adapter nodelessMarker = new AdapterImpl();

	@Inject
	private ImportSpacerUserPreferenceHelper spacerPreference;

	@Inject
	private N4JSCandidateFilter candidateFilter;
	
	@Inject
	private IN4JSCore core;
	

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
			val text = extractPureText(xtextResource);
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
		val scopeIdRef = scopeProvider.getScopeForContentAssist(script, N4JSPackage.Literals.IDENTIFIER_REF__ID);
		
		println("\u2503\t\u2503\t\u250F createResolutionsFromSopes  ")
		val sw3 = Stopwatch.createStarted();
		val Multimap<String, ImportableObject> resolutions = createResolutionsForUnresolvedCrossRefs(script, scopeIdRef, scopeTypeRef)
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
				if(idSolutions.size == 1)
					ret.add(importsFactory.createImport(idSolutions.head, prj,nodelessMarker))
				else
					forDisambiguation.putAll(brokenName, idSolutions);
			}
		];

		val chosenSolutions = disambiguate(forDisambiguation, interaction);

		chosenSolutions.forEach[ret.add(importsFactory.createImport(it, prj, nodelessMarker))];

		sw.stop();
		println("\u2503\t\u2517 resolveMissingImports took " + sw)
		return ret;
	}
	
	/** Finds unresolved cross references in this script and finds candidate imports based on the provided scopes. */
	private def Multimap<String, ImportableObject> createResolutionsForUnresolvedCrossRefs(Script script,
		IScope scopeIdRef, IScope scopeTypeRef) {
		val Multimap<String, ImportableObject> resolutions = LinkedHashMultimap.create();
		
		
		val Iterable<ReferenceProxyInfo> unresolvedS = script.findProxyCrossRefInfo.filter[referenceFilter.test(it)]
		unresolvedS.forEach[if(it.eobject instanceof MemberAccess === true)
			println(it.name  + " -> " + it.eobject)
		]
		
		// the following are named imports, that did not resolve. The issue lies in the Project-configuration and
		// cannot be solved here. Candidate for quick fix.
		val Iterable<ReferenceProxyInfo> unresolved = script.findProxyCrossRefInfo.filter[referenceFilter.test(it)].filter[it.eobject instanceof MemberAccess === false]
		val Iterable<ReferenceProxyInfo> unresolvedIdRefs = unresolved.filter[it.eobject instanceof IdentifierRef]
		
		// in situations like "new A()" at the position of A an IdentifierRef is unresolved.
		// The solution is provided as a TypeRef. So TypeRef-solutions can be used in places where an IDref is desired.
		// --> obj IdRef :  scopeIdRef & scopeTypeRef
		// --> obj TypeRef : only TypeRef
		addResolutionsFromScope(resolutions, scopeIdRef, unresolvedIdRefs);
		addResolutionsFromScope(resolutions, scopeTypeRef, unresolved);

		return resolutions

	}
	
	private def void addResolutionsFromScope(Multimap<String, ImportableObject> resolution, IScope scope,
		Iterable<ReferenceProxyInfo> unresolved) {
		if (!unresolved.empty) {
			val unprocessedIDRefNames = new HashSet<String>
			unresolved.forEach[unprocessedIDRefNames.add(it.name)]
			val idscopeIter = scope.allElements.iterator
			while (idscopeIter.hasNext) {
				val ieod = idscopeIter.next
				println("# " + ieod)
				//cannot compute imports from proxies
				if (ieod.EObjectOrProxy.eIsProxy){
					//consider trying to resolve the proxy
					//In batch mode user with have interaction dialog
					//TODO add interaction in the editor mode 
					throw new RuntimeException("Cannot resolve imports from eProxy " + ieod.EObjectOrProxy)
				}
				unresolved.forEach [ proxyInfo |
					val String usedName = proxyInfo.name
					if (isCandidate(ieod, usedName) && candidateFilter.apply(ieod)) {
						println("add " + usedName + " >> " + ieod)
						resolution.add(usedName, ieod)
					}
				]
			}
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
		IEObjectDescription ieoDescription) {
		// potential match via namespace
		if (usedName.contains(".")) {
			val segments = usedName.split("\\.")
			// 2 segments, potential namespace access
			if (segments.size == 2) {
				println(13123);
				val eo = ieoDescription.EObjectOrProxy;
				checkNotProxy(eo)
				if (eo instanceof TExportableElement) {
					if (eo.exportedName == segments.last) {
						return resolution.put(
							segments.head,
							new ImportableObject(segments.head, ieoDescription, ieoDescription.isDefaultExport, true)
						)
					}
				}
			}
		}
		return resolution.put(usedName, new ImportableObject(usedName, ieoDescription, ieoDescription.isDefaultExport))
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
		
		val sQN = qName.toString
		
		if(sQN.contains('.') && usedName.contains('.'))
			println(312)
		
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

	/** Extracts the token text for existing import-declaration or creates new textual representation for
	 * a new generated import declaration.
	 */
	private def String extractPureText(ImportDeclaration declaration, XtextResource resource) {
		// formatting decision: curly braces with whitespace
		val spacer = spacerPreference.getSpacingPreference(resource)

		if (declaration.eAdapters.contains(nodelessMarker)) {
			// wrap importSpecifiers in new ArrayList, to support sorting (GHOLD-48)
			val impSpec = new ArrayList(declaration.importSpecifiers);
			val module = declaration.module.moduleSpecifier;

			if (impSpec.size === 1) {
				// create own string. from single Named Adapter:
				val onlyImpSpec = impSpec.get(0)
				if(onlyImpSpec instanceof NamespaceImportSpecifier){
					return '''import * as «onlyImpSpec.alias» from "«module»";'''
				}
				val namedSpec = onlyImpSpec as NamedImportSpecifier
				if (namedSpec instanceof DefaultImportSpecifier) {
					'''import «namedSpec.importedElement.name» from "«module»";'''

				} else {
					'''import {«spacer»«namedSpec.importedElement.name»«IF (namedSpec.alias !== null)» as «namedSpec.alias»«ENDIF»«spacer»} from "«module»";'''
				}
			} else {
				// more then one, sort them:
				ImportsSorter.sortByName(impSpec)
				val defImp = impSpec.filter(DefaultImportSpecifier).head; // only one is possible
				val nameImp = impSpec.filter(NamespaceImportSpecifier).head; // only one is possible
				val rest = impSpec.filter[it instanceof DefaultImportSpecifier === false].filter[it instanceof NamespaceImportSpecifier === false]
				val normalImports = !rest.isEmpty
				val defaultImport = if (defImp === null) "" else '''«defImp.importedElement.name»''';
				val spacerDefName = '''«IF defImp !== null && nameImp !== null», «ENDIF»'''
				val namespaceImport = if (nameImp === null) "" else '''* as «nameImp.alias»''';

				'''import «
						defaultImport»«
						spacerDefName»«
						namespaceImport»«
						IF normalImports
							»{«spacer»«
								FOR a : impSpec SEPARATOR ', '»«
									(a as NamedImportSpecifier).importedElement.name»«
										IF ((a as NamedImportSpecifier).alias !== null)» as « (a as NamedImportSpecifier).alias »«
										ENDIF»«
								ENDFOR»«spacer
							»}«
						ENDIF» from "«module»";'''
			}
		} else {
			val importNode = findActualNodeFor(declaration);
			return rewriteTokenText(importNode, spacer, SEMICOLON_INSERTED);
		}
	}

	/**
	 * Rewrites the node-content without comments.
	 *
	 * Inspired by {@link NodeModelUtils#getTokenText(INode)} but can treat any {@link LeafNodeWithSyntaxError syntax error}
	 * nodes as a hidden one and reformats White-space after opening and before closing curly brace according to spacer-policy
	 *
	 * @param node the node to reformat.
	 * @param spacer append after "{" and prepend before "}" can be empty string, then no white-space is inserted after
	 * @param ignoredSyntaxErrorIssues - nodes of type LeafNodeWithSyntaxError having one of this issues are treated as ignored/hidden leafs
	 * @returns modified textual form.
	 */
	private static def String rewriteTokenText(ICompositeNode node, String spacer,
		String... ignoredSyntaxErrorIssues) {
		val StringBuilder builder = new StringBuilder(Math.max(node.getTotalLength(), 1));

		var boolean hiddenSeen = false;
		var boolean openingCurlySeen = false;
		var boolean fixedASI = false;

		for (ILeafNode leaf : node.getLeafNodes()) {
			if(UtilN4.isIgnoredSyntaxErrorNode(leaf, SEMICOLON_INSERTED)){
				fixedASI = true
			}
			if (!isHiddenOrIgnoredSyntaxError(leaf, ignoredSyntaxErrorIssues)) {
				val text = leaf.getText();
				if (builder.length() > 0) { // do not insert space before any content.
					if (openingCurlySeen) {
						// last real text was "{"
						builder.append(spacer);
					} else if ("}" == text) {
						// this text is "}"
						builder.append(spacer);
					} else if ("," == text) {
						// drop WS in front of commas.
					} else {
						// standard-WS handling.
						if (hiddenSeen) {
							builder.append(' ');
						}
					}
				}
				// record state of opening curly brace:
				openingCurlySeen = "{" == text;
				builder.append(text);
				hiddenSeen = false;
			} else {
				hiddenSeen = true;
			}
		}

		if(fixedASI) builder.append(";");

		return builder.toString();
	}

	/**
	 * Returns with {@code true} if the leaf node argument is either hidden, or represents a
	 * {@link LeafNodeWithSyntaxError syntax error} where the {@link SyntaxErrorMessage#getIssueCode() issue code} of
	 * the syntax error matches with any of the given ignored syntax error issue codes. Otherwise returns with
	 * {@code false}.
	 */
	private static def boolean isHiddenOrIgnoredSyntaxError(ILeafNode leaf, String... ignoredSyntaxErrorIssues) {
		return leaf.isHidden() || UtilN4.isIgnoredSyntaxErrorNode(leaf, ignoredSyntaxErrorIssues);
	}

	private def <T> List<T> takefirst(Multimap<String, T> multimap) {
		val result = <T>newArrayList();

		for (name : multimap.keySet) {

			// TODO the first must be actually determined from the error-state-list given by the scoping, see {@link ImportProvidedElement#ambiguityList}
			// The first Identifiable in there is bound to the first thing the scoping encountered. For the time being, lets take any:
			result += multimap.get(name).get(0)
		}

		return result;
	}

	/**
	 * Depending on interaction-mode:
	 * Present a user dialog and let the user choose a distinct import for each unresolved problem.
	 *
	 * For each key in {@code multiMap} exactly one solution is returned.
	 *
	 */
	private def <T> List<T> disambiguate(Multimap<String, T> multiMapName2Candidates,
		Interaction interaction) throws BreakException {
		// for each name exactly one solution must be picked:
		val result = <T>newArrayList();
		if (multiMapName2Candidates.empty) return result;

		switch (interaction) {
			case breakBuild: {
				throw new BreakException("Cannot automatically disambiguate the imports of " +
					multiMapName2Candidates.keySet.toList)
			}
			case takeFirst: {
				return takefirst(multiMapName2Candidates);
			}
			case queryUser: {
			} // follows
		}

		val ILabelProvider labelProvider = importProvidedElementLabelprovider;

		val Object[][] openChoices = Multimaps3.createOptions(multiMapName2Candidates);

		val MultiElementListSelectionDialog dialog = new MultiElementListSelectionDialog(null, labelProvider);

		dialog.setTitle("Organize Imports");
		dialog.setMessage("Choose type to import:");
		dialog.setElements(openChoices);

		if (dialog.open() == Window.OK) {
			val Object[] res = dialog.getResult();

			for (var int i = 0; i < res.length; i++) {
				val Object[] array = res.get(i) as Object[];
				if (array.length > 0) {
					result.add(array.get(0) as T)
				}
			}
		} else {
			throw new UserCanceledBreakException("User canceled.");
		}
		return result;
	}
	
	/**
	 * In most cases we cannot compute imports from proxy objects, as essential information like declared name, exported name etc.
	 * are not available.
	 * This method will throw exception that:
	 *  - in case of user using OrganizeImports should be handled in dialogs presented to the user
	 *  - in case of transpiler should break compilation process
	 */
	private def checkNotProxy(EObject eo){
		//TODO add interaction in the editor mode 
		// (not here, in the document organizer which will call this class)
		if (eo.eIsProxy)
			//TODO consider trying to resolve the proxy?
			throw new RuntimeException("Cannot resolve imports from eProxy " + eo)
	}
}
