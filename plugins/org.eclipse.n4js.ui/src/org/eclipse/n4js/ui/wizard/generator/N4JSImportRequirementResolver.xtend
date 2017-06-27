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
package org.eclipse.n4js.ui.wizard.generator

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.scoping.imports.PlainAccessOfAliasedImportDescription
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ui.changes.IAtomicChange
import org.eclipse.n4js.ui.changes.Replacement
import org.eclipse.n4js.ui.organize.imports.ImportsRegionHelper
import org.eclipse.n4js.ui.wizard.model.ClassifierReference
import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.XtextResource

/**
 * The N4JSImportRequirementResolver provides functionality to resolve import name conflicts and requirements.
 *
 * Using N4JSImportRequirementResolver one can compute the remaining imports to be made when inserting code
 * into an existing module. One can also computer alias naming conflicts and how to resolve them.
 */
class N4JSImportRequirementResolver {

	@Inject
	private N4JSScopeProvider scopeProvider;

	@Inject
	private ImportsRegionHelper hImportsRegion;

	/**
	 * Analyzes a list of demanded import requirements and a resource in which the requirements should be available.
	 *
	 * This includes matching required and existing imports and alias bindings if required imports are aliased
	 * in the given resource.
	 *
	 * @param demandedImports A list of all import requirements demanded to be included in the resource
	 * @param resource The resource to work on.
	 *
	 * @return An {@link ImportAnalysis}
	 */
	def ImportAnalysis analyzeImportRequirements(List<ImportRequirement> demandedImports, XtextResource resource) {

		//Copy demanded import requirements
		var importRequirements = new ArrayList<ImportRequirement>(demandedImports);
		val Map<URI,String> aliasBindings = new HashMap<URI,String>();

		val Script script = getScript(resource);

		//Get existing import statements
		val List<ImportDeclaration> resourceImportStatements = script.eAllContents.filter(ImportDeclaration).toList;

		//Get scopes
		val typeScope = scopeProvider.getScope(script,TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
		val scopeIdRef = scopeProvider.getScope(script, N4JSPackage.Literals.IDENTIFIER_REF__ID)

		//Match existing import statement with the requirements
		for ( var i=importRequirements.size-1; i >= 0 ; i-- ) {

			val dependency = importRequirements.get(i);
			var fullfilled = false;

			// Check if dependency is fulfilled by an element in the scope

			val Object typeScopeElement = typeScope.getSingleElement(QualifiedName.create(dependency.typeName));

			if ( typeScopeElement instanceof IEObjectDescription ) {
				if (typeScopeElement.getEObjectURI.equals(dependency.typeUri ) && !(typeScopeElement instanceof PlainAccessOfAliasedImportDescription) ) {
					importRequirements.remove(i);
					fullfilled = true;
				}
			}

			// If the dependency wasn't fulfilled yet iterate through the import statements
			// to check for alias imported elements
			if ( !fullfilled ) {

				for ( var j=resourceImportStatements.size-1;j>=0 && !fullfilled ;j-- ) {
					val ImportDeclaration declaration = resourceImportStatements.get(j);

					// Check whether the declaration fulfills the dependency
					val ImportSpecifier fulfillingSpecifier = findFulfillingImportSpecifier(declaration, dependency);
					if ( fulfillingSpecifier !== null ) {

						if ( fulfillingSpecifier instanceof NamedImportSpecifier ) {

							//If the import statement has an alias, add an alias binding
							if ( fulfillingSpecifier.alias !== null && !fulfillingSpecifier.alias.empty ) {
								aliasBindings.put(dependency.typeUri,fulfillingSpecifier.alias)
							}

							//Remove the dependency as it is fulfilled by this declaration
							importRequirements.remove(i);
							fullfilled = true;
						}

					}
				}

			}
		}

		//Resolve remaining name import name conflicts
		//Use the scopes as additional used-name-validation
		val nameResolvingAliasBindings = resolveImportNameConflicts(importRequirements,[ name |
			val qualifiedName = QualifiedName.create(name);
			//Look for elements in the type scope
			val Object typeScopeElement = typeScope.getSingleElement(qualifiedName);
			//If the element is of type PlainAccessOfAliasedImportDescription the name is still available
			if ( typeScopeElement !== null && typeScopeElement instanceof PlainAccessOfAliasedImportDescription ) {
				return false;
			}

			val Object idScopeElement = scopeIdRef.getSingleElement(qualifiedName);

			//Return true if type or identifier element is found and aliased isn't used yet
			return  typeScopeElement !== null ||
					 idScopeElement !== null ||
					aliasBindings.containsValue(name)
		]);

		aliasBindings.putAll(nameResolvingAliasBindings);


		return new ImportAnalysis(importRequirements, aliasBindings);
	}

	/**
	 * Resolves name conflicts inside the import requirements list, which are name conflicts between elements of the list.
	 * Additional conditions are checked through the isUsedNamePredicate to allow for example advanced scope checking.
	 *
	 * The method returns a map of alias bindings which is a possible solution to resolve the naming conflicts.
	 *
	 * <p>Note: If isUsedNamePredicate is null only inner list name conflicts are resolved</p>
	 *
	 * @param importRequirements A list of import requirements
	 * @param isUsedNamePredicate Predicate for advanced name checking. May be null.
	 *
	 * @return The alias bindings.
	 */
	public def Map<URI,String> resolveImportNameConflicts(Collection<ImportRequirement> importRequirements, (String)=>boolean isUsedNamePredicate ) {
		val HashMap<String,Boolean> usedNames = new HashMap<String,Boolean>();

		//Save used alias bindings
		var aliasBindings = new HashMap<URI,String>();

		for (requirement : importRequirements) {
			if ( requirement.alias.empty ) {
				var alias = requirement.typeName;

				//As long as the name check function returns false or the name already was used add another "Alias"-prefix
				while ( (isUsedNamePredicate !== null && isUsedNamePredicate.apply(alias) ) ||
						usedNames.containsKey(alias) ||
						aliasBindings.containsValue(alias) ) {
					alias = "Alias" + alias;
				}

				if ( !requirement.typeName.equals(alias) ) {
					requirement.alias = alias;
					aliasBindings.put(requirement.typeUri,alias);
					usedNames.put(alias, true);
				} else{
					usedNames.put(requirement.typeName,true);
				}

			}
		}

		return aliasBindings
	}

	/**
	 * Return the fulfilling import specifier in the given ImportDeclaration or null if the declaration doesn't fulfill the requirement.
	 *
	 * @param declaration The import declaration to search in
	 * @param requirement The ImportRequirement to fulfill
	 *
	 * @return The fulfilling specifier or null
	 *
	 */
	private def ImportSpecifier findFulfillingImportSpecifier(ImportDeclaration declaration, ImportRequirement requirement) {
		// Use EcoreUtil to handle unresolved proxies
		val declarationModuleURI = EcoreUtil.getURI(declaration.module).toContainingModuleURI;

		if (!declarationModuleURI.equals(requirement.typeUri.toContainingModuleURI)) {
			return null
		}
		for ( ImportSpecifier specifier : declaration.importSpecifiers ) {
			if ( specifier instanceof NamedImportSpecifier ) {
				var importedElement = specifier.importedElement;
				if ( importedElement instanceof TClassifier ) {
					if( requirement.typeUri.equals(importedElement.uriOfEObject) ) {
						return specifier;
					}
				}
			}
		}
		null
	}

	/**
	 * Returns an absolute URI which consists of the resource part and the
	 * path inside of the resource separated with a '#' character.
	 */
	private def URI uriOfEObject(EObject object) {
		object.eResource.getURI.appendFragment(object.eResource.getURIFragment(object))

	}

	/**
	 * Returns the URI of the module containing the element with the given URI.
	 */
	private def URI toContainingModuleURI(URI uri) {
		URI.createURI(uri.toString.split("#").get(0));
	}

	/**
	 * Return the {@link IAtomicChange} to insert the import statement for requirements into the the resource.
	 *
	 * @param resource The resource to modify
	 * @param requirements The import requirements
	 *
	 * @return The change to perform
	 */
	public def IAtomicChange getImportStatementChanges(XtextResource resource, List<ImportRequirement> requirements) {

		var importStatements = requirements.generateImportStatements;
		if ( ! importStatements.empty ) {
			importStatements += WizardGeneratorHelper.LINEBREAK
		}

		new Replacement(resource.getURI,
						getImportStatementOffset(resource),
						0,
						importStatements
		);
	}

	/**
	 * Returns the offset of import statements in the given resource.
	 */
	public def int getImportStatementOffset(XtextResource resource) {
		hImportsRegion.getImportOffset(resource);
	}

	/**
	 * Generate the code for the given importRequirements
	 * */
	public def String generateImportStatements(List<ImportRequirement> importRequirements) {
		importRequirements.map[dep |
			'''import {«dep.typeName»«IF !dep.alias.empty» as «dep.alias»«ENDIF»} from "«dep.moduleSpecifier»"'''
		].join(WizardGeneratorHelper.LINEBREAK)
	}

	/**
	 * @param xtextResource
	 *            the resource to process.
	 * @return Script instance or null
	 */
	private static def Script getScript(XtextResource xtextResource) {
		if (!xtextResource.getContents().isEmpty()) {
			val EObject eo = xtextResource.getContents().get(0);
			if (eo instanceof Script) {
				return eo;
			}
		}
		return null;
	}

	/** Convert {@link ClassifierReference}s to import requirements without alias */
	public static def List<ImportRequirement> classifierReferencesToImportRequirements(List<ClassifierReference> refs) {
		refs.map[ ClassifierReference ref | ref.classifierReferenceToImportRequirement ]
	}
	/** Convert a {@link ClassifierReference} to an import requirement without alias */
	public static def ImportRequirement classifierReferenceToImportRequirement(ClassifierReference reference) {
		new ImportRequirement(reference.classifierName,"",reference.classifierModuleSpecifier,reference.uri)
	}
}
