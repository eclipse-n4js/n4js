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
package org.eclipse.n4js.transpiler

import java.util.Collection
import java.util.stream.Collectors
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.transpiler.im.ImFactory
import org.eclipse.n4js.transpiler.im.ImPackage
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryIMOnly
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.NameAndAccess
import org.eclipse.n4js.ts.types.TClassifier

/**
 */
class SymbolTableManagement {

	/**
	 * Create a symbol table entry for a given original target (either a TModule element OR a variable in the original
	 * AST, in case of non-exported top-level variables, local variables, formal parameters, etc.).
	 */
	def public static SymbolTableEntryOriginal createSymbolTableEntryOriginal(TranspilerState state, IdentifiableElement originalTarget) {
		if(originalTarget===null) {
			throw new IllegalArgumentException("original target may not be null");
		}
		val newEntry = ImFactory.eINSTANCE.createSymbolTableEntryOriginal;
		newEntry.name = originalTarget.name;
		newEntry.originalTarget = originalTarget;
		if(originalTarget instanceof NamedElement) {
			newEntry.elementsOfThisName += originalTarget as NamedElement;
		}
		state.addOriginal(newEntry)

		return newEntry;
	}

	/** add a {@link SymbolTableEntryOriginal} */
	def static public void addOriginal(TranspilerState state, SymbolTableEntryOriginal steOriginal) {
		addOriginal ( state.steCache, steOriginal ) ;
	}

	/** NOTE: Internal usage in preparation step, please call {@link #addOriginal(TranspilerState,SymbolTableEntryOriginal)}  */
	def static public void addOriginal(TranspilerState.STECache steCache, SymbolTableEntryOriginal steOriginal) {

		val SymbolTableEntryOriginal old = steCache.mapOriginal.put(steOriginal.getOriginalTarget(), steOriginal);
		if (old !== null)
			throw new IllegalStateException(
					"It is not allowed to register more then one STEOriginal for the same original Target. Already had: "
							+ old);
		steCache.im.getSymbolTable().getEntries().add(steOriginal);
		steCache.inverseMap(steOriginal);
	}


	def static private void inverseMap(TranspilerState.STECache steManager, SymbolTableEntryOriginal steOriginal) {
		// register elements of this name.
		steOriginal.getElementsOfThisName().forEach[ele | steManager.mapNamedElement_2_STE.put(ele, steOriginal)];
	}


	/**
	 * Create a symbol table entry for an element in the intermediate model. This should only be used if the element
	 * in the IM does <b>not</b> have a corresponding original target (either a TModule element or an element
	 * in the original AST, in case of non-exported variables), for example because it was newly created by an AST
	 * transformation.
	 */
	def public static SymbolTableEntryIMOnly createSymbolTableEntryIMOnly(TranspilerState state, NamedElement elementInIM) {
		if(elementInIM===null) {
			throw new IllegalArgumentException("element in intermediate model may not be null");
		}
		val newEntry = ImFactory.eINSTANCE.createSymbolTableEntryIMOnly;
		newEntry.name = elementInIM.name;
		newEntry.elementsOfThisName += elementInIM;
		state.addIMOnly( newEntry );
		return newEntry;
	}

	/**
	 * Create an <em>internal</em> symbol table entry. They are special and should be used only in rare exception cases.
	 * See {@link SymbolTableEntryInternal} for details.
	 */
	def public static SymbolTableEntryInternal createSymbolTableEntryInternal(TranspilerState state, String name) {
		if(name===null) {
			throw new IllegalArgumentException("name may not be null");
		}
		val newEntry = ImFactory.eINSTANCE.createSymbolTableEntryInternal;
		newEntry.name = name;
		state.addInteral( newEntry );
		return newEntry;
	}



	/** add a {@link SymbolTableEntryInternal} */
	def private static void addInteral(TranspilerState state,  SymbolTableEntryInternal ste) {
		val SymbolTableEntryInternal old = state.steCache.mapInternal.put(ste.getName(), ste);
		if (old !== null)
			throw new IllegalStateException(
					"It is not allowed to put the same SymbolTableEntryInternal twice into the Symboltable " + old);
		state.im.getSymbolTable().getEntries().add(ste);
	}



	/**
	 * Search an STE by original target and create it if not found.
	 */
	def public static SymbolTableEntryOriginal getSymbolTableEntryOriginal(TranspilerState state, IdentifiableElement originalTarget, boolean create) {
		if(originalTarget===null) {
			throw new IllegalArgumentException("original target may not be null");
		}
		val existingEntry = state.getSteOriginal(originalTarget);

		if(existingEntry!==null) {
			return existingEntry;
		}
		if(create) {
			return createSymbolTableEntryOriginal(state, originalTarget);
		}
		return null;
	}

	/**
	 * Convenience method for {@link #getSymbolTableEntryOriginal(TranspilerState, IdentifiableElement, boolean},
	 * allowing to retrieve the member by name and access from its parent classifier.
	 */
	def public static SymbolTableEntryOriginal getSymbolTableEntryForMember(TranspilerState state, TClassifier type,
		String memberName, boolean writeAccess, boolean staticAccess, boolean create) {
		if(type===null || memberName===null || memberName.empty) {
			throw new IllegalArgumentException("type may not be null and memberName may not be null or empty");
		}
		val m = type.findOwnedMember(memberName, writeAccess, staticAccess);
		if(m===null) {
			val nameAndAccess = new NameAndAccess(memberName, writeAccess, staticAccess);
			throw new IllegalArgumentException("no such member found in given type: " + nameAndAccess);
		}
		return getSymbolTableEntryOriginal(state, m, create);
	}

	/**
	 * Search an internal STE by name and create it if not found.
	 */
	def public static SymbolTableEntryInternal getSymbolTableEntryInternal(TranspilerState state, String name, boolean create) {
		if(name===null || name.empty) {
			throw new IllegalArgumentException("name may not be null or empty");
		}

		val existingEntry = state.getSteInternal(name);

		if(existingEntry!==null) {
			return existingEntry;
		}
		if(create) {
			return createSymbolTableEntryInternal(state, name);
		}
		return null;
	}

	/**
	 * Will look up the STE for the given named element in the IM. If not found and <code>create</code> is set to
	 * <code>true</code> a {@code SymbolTableEntryIMOnly} is created, otherwise <code>null</code> is returned.
	 * <p>
	 * <b>WARNING:</b> during look up it will find both {@link SymbolTableEntryOriginal}s and {@link SymbolTableEntryIMOnly}s,
	 * but when creating a new STE, it will always create a {@code SymbolTableEntryIMOnly} which is invalid if there
	 * exists an original target for the given <code>elementInIM</code> (then a {@link SymbolTableEntryOriginal} would
	 * have to be created)!. In such a case, this method must not be used.<br>
	 * Most of the time, this won't be the case and it is safe to use this method, because all
	 * {@code SymbolTableEntryOriginal}s will be created up-front during the {@link PreparationStep}; in some special
	 * cases, however, a new element is introduced into the IM that actually has an original target (so far, static
	 * polyfills are the only case of this).
	 */
	def public static SymbolTableEntry findSymbolTableEntryForElement(TranspilerState state, NamedElement elementInIM, boolean create) {
		if(elementInIM===null) {
			throw new IllegalArgumentException("element in intermediate model may not be null");
		}
		val existingEntry = state.byElementsOfThisName(elementInIM);

		if(existingEntry!==null) {
			return existingEntry;
		}
		if(create) {
			return createSymbolTableEntryIMOnly(state, elementInIM);
		}
		return null;
	}

	/**
	 * Search STE for the given name space import.
	 */
	def public static SymbolTableEntryOriginal findSymbolTableEntryForNamespaceImport(TranspilerState state, NamespaceImportSpecifier importspec) {
		// 1. linear version:
		//		state.im.symbolTable.entries.filter(SymbolTableEntryOriginal)
		//			.filter[it.importSpecifier === importspec]
		//			.filter[it.originalTarget instanceof ModuleNamespaceVirtualType]
		//			.head

		// 2. parallel version:
		//			return state.im.symbolTable.entries.parallelStream()
		//				.filter[it instanceof SymbolTableEntryOriginal].map[ it as SymbolTableEntryOriginal]
		//				.filter[it.importSpecifier === importspec]
		//				.filter[it.originalTarget instanceof ModuleNamespaceVirtualType]
		//				.findAny().orElse(null);

		// 3. only the originals:
		// Should be safe to use the cache.
		return state.steCache.mapOriginal.values.parallelStream()
			.filter[it.importSpecifier === importspec]
			.filter[it.originalTarget instanceof ModuleNamespaceVirtualType]
			.findAny().orElse(null);
	}



	// let's try to keep this at "package" visibility for now (but there'll probably be special cases when a
	// transformation needs to rewire something special by calling this directly)
	def /*package*/ static void rewireSymbolTable(TranspilerState state, EObject from, EObject to) {
		if(!from.requiresRewiringOfSymbolTable && !to.requiresRewiringOfSymbolTable) {
			return; // nothing to rewire!
		}
		if(from instanceof ReferencingElement_IM && to instanceof ReferencingElement_IM) {
			// case 1
			val eRefThatMightPointToOriginal = ImPackage.eINSTANCE.symbolTableEntry_ReferencingElements;
			// TODO can be speed up
			state.im.symbolTable.entries.parallelStream
				.forEach[
					replaceInEReference(it, eRefThatMightPointToOriginal, from, to);
				];

		} else if(from instanceof ImportSpecifier && to instanceof ImportSpecifier) {
			// case 2
			val eRefThatMightPointToOriginal = ImPackage.eINSTANCE.symbolTableEntryOriginal_ImportSpecifier;
			// TODO can be speed up
			state.im.symbolTable.entries.parallelStream.filter[it instanceof SymbolTableEntryOriginal]
				.forEach[
					replaceInEReference(it, eRefThatMightPointToOriginal, from, to);
				];

		} else if(from instanceof NamedElement && to instanceof NamedElement) {
			// case 3  // Most relevant case according to profiler
			val eRefThatMightPointToOriginal = ImPackage.eINSTANCE.symbolTableEntry_ElementsOfThisName;
			// Slow version:
			//			state.im.symbolTable.entries_.forEach[
			//				replaceInEReference(it, x, from, to);
			//			];

			val steFrom = state.byElementsOfThisName(from as NamedElement);
			if( steFrom !== null ) {
				replaceInEReference(steFrom, eRefThatMightPointToOriginal, from , to );
				// update STECache:
				state.replacedElementOfThisName( steFrom, from as NamedElement, to as NamedElement )
			}
		} else {
			throw new IllegalArgumentException("rewiring symbol table entries from type " + from.eClass.name +
				" to type " + to.eClass.name + " is not supported yet");
		}
	}

	def private static boolean requiresRewiringOfSymbolTable(EObject obj) {
		return obj instanceof ReferencingElement_IM || obj instanceof ImportSpecifier || obj instanceof NamedElement;
	}

	def private static <T extends EObject, TN extends T>  void replaceInEReference(EObject obj, EReference eRef, T original, TN replacement) {
		// note: cannot use EcoreUtil#replace() here, because it throws exceptions if original is not in reference!
		if(eRef.many) {
			val l = obj.eGet(eRef) as EList<? super T>;
			for(idx : 0..<l.size) {
				if(l.get(idx)===original) {
					l.set(idx, replacement);
				}
			}
		} else {
			if(obj.eGet(eRef)===original) {
				obj.eSet(eRef, replacement);
			}
		}
	}


	/** add a {@link SymbolTableEntryIMOnly} */
	def static public void addIMOnly(TranspilerState state, SymbolTableEntryIMOnly only) {
		// assumption 1: freshly generated  - always connected to a named element. (IDEBUG-777)
		if( only.elementsOfThisName.size !== 1 ) throw new IllegalArgumentException("got a STEImOnly with elmentsOfThisName != 1 : "+only.elementsOfThisName.size);
		// assumption 2: there are no other things registered by this name. (IDEBUG-777)
		val old = state.steCache.mapNamedElement_2_STE.put(only.elementsOfThisName.get(0), only)
		if( old !== null ) throw new IllegalStateException("tries to install STEImOnly but already had one for the NamedElmeent = "+ only.elementsOfThisName.get(0));
		state.im.getSymbolTable().getEntries().add(only);
	}

	/** lookup a {@link SymbolTableEntryIMOnly} associated to an {@link IdentifiableElement} */
	def static public SymbolTableEntryOriginal getSteOriginal(TranspilerState state, IdentifiableElement element) {
		return state.steCache.mapOriginal.get(element);
	}

	/** lookup an {@link SymbolTableEntryInternal} based on a plain name ({@link String}) */
	def static public SymbolTableEntryInternal getSteInternal(TranspilerState state, String name) {
		return state.steCache.mapInternal.get(name);
	}

	/** lookup a {@link SymbolTableEntry} based on a {@link NamedElement} contained in the IM */
	def static public SymbolTableEntry byElementsOfThisName(TranspilerState state, NamedElement elementInIM) {

		val SymbolTableEntry lookup = state.steCache.mapNamedElement_2_STE.get(elementInIM);
		if (lookup !== null) {
			if (lookup.getElementsOfThisName().contains(elementInIM)) {
				return lookup;
			}
			throw new IllegalStateException("Did find STE by NamedElement which is not contained in the list STE.elementsOfThisName. elementInIM="+elementInIM+"  found wrong STE="+lookup);
		}

		return null;
	}

	/**
	 * Update data structure for NamedElements after the list of {@link SymbolTableEntry#getElementsOfThisName()} of
	 * {@code entry} has been modified
	 *
	 * @param entry
	 *            the updated STE (wherein elmentsOfThisName has been modified to contain {@code to} instead of
	 *            {@code from}
	 * @param from
	 *            old NamedElement
	 * @param to
	 *            new NamedElement
	 */
	def static public void replacedElementOfThisName(TranspilerState state, SymbolTableEntry entry, NamedElement from, NamedElement to) {
		// internal check:
		val SymbolTableEntry steRegisteredWithFrom = state.steCache.mapNamedElement_2_STE.get(from);
		if (steRegisteredWithFrom != entry)
			throw new IllegalArgumentException(
					"This method must be called directly after the replacement and only once."
							+ "Expected from=" + from + " to be related to entry=" + entry
							+ " in mapNamedElement_2_STE but found: " + steRegisteredWithFrom);
		// repair map:
		state.steCache.mapNamedElement_2_STE.remove(from);
		state.steCache.mapNamedElement_2_STE.put(to, entry);
	}



	def static public SymbolTableEntryOriginal findSymbolTableEntryForNamedImport(TranspilerState state, NamedImportSpecifier importspec) {
		return state.steCache.mapOriginal.values().parallelStream()
				.filter[getImportSpecifier() == importspec]
				.findAny().orElse(null);
	}

	/**
	 * Finds and returns all STEs that hold a reference to the given {@link VersionedNamedImportSpecifier_IM}
	 *
	 * In case of the import of an unversioned type, this method defaults
	 * to {@link SymbolTableManagement.findSymbolTableEntryForNamedImport(TranspilerState, NamedImportSpecifier)}.
	 */
	def static public Collection<SymbolTableEntryOriginal> findSymbolTableEntriesForVersionedTypeImport(TranspilerState state, VersionedNamedImportSpecifier_IM importspec) {
		// avoid expensive computation for unversioned imports
		if (!importspec.isVersionedTypeImport) {
			return #[findSymbolTableEntryForNamedImport(state, importspec)];
		}

		// Since we need to know about the complete set of used versions of the
		// imported type, we need to look at least at importspec.importedTypeVersion.size many
		// distinct STEs with the importSpecifier set to importspec.
		return state.steCache.mapOriginal.values().parallelStream()
				.unordered // order is not of importance
				.filter[getImportSpecifier() == importspec]
				.distinct // This is comparatively expensive but allows us to exit earlier
						  // due to the following limit-condition
				.limit(importspec.importedTypeVersions.size)
				.collect(Collectors.toList);
	}


	def static public void rename(TranspilerState state, SymbolTableEntry entry, String name) {

		if (entry instanceof SymbolTableEntryInternal) {
			throw new UnsupportedOperationException("cannot rename internal STEs " + entry);

		} else if (entry instanceof SymbolTableEntryIMOnly) {
			entry.setName(name);

		} else if (entry instanceof SymbolTableEntryOriginal) {

			entry.setName(name);

			// should do something like the following:
			// (not possible at the moment, because NamedElement does not have a setter for property 'name')
			// entry.elementsOfThisName.forEach[it.name=newName];

			if (entry.getImportSpecifier() !== null)
				throw new UnsupportedOperationException(
						"renaming of symbol table entries not tested yet for imported elements!");
			// should be something like the following:
			// switch(impSpec) {
			// NamedImportSpecifier: if(impSpec.alias!==null) impSpec.alias = newName
			// NamespaceImportSpecifier: if(impSpec.alias!==null) impSpec.alias = newName
			// }
		} else {
			throw new UnsupportedOperationException(
					"Rename request for SymboltableEntries of unkown type : " + entry);
		}

	}
}
