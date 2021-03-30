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
package org.eclipse.n4js.transpiler.utils

import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.transpiler.InformationRegistry
import org.eclipse.n4js.transpiler.TranspilerState
import org.eclipse.n4js.transpiler.im.Script_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.xtext.EcoreUtil2

/**
 * Some utilities for transpiler debugging, mainly dumping a {@link TranspilerState} to {@code stdout}, etc.
 */
class TranspilerDebugUtils {

	/**
	 * Perform some consistency checks on the transpiler state. For example, this asserts that no node in the
	 * intermediate model has a direct cross-reference to the original AST or an original TModule element.
	 */
	def public void validateState(TranspilerState state, boolean allowDanglingSecondaryReferencesInSTEs) throws AssertionError {
		// IM should not contain entities from n4js.xcore / TypeRefs.xcore for which a replacement in IM.xcore exists
		val replacedEClasses = #[
			N4JSPackage.eINSTANCE.parameterizedPropertyAccessExpression,
			N4JSPackage.eINSTANCE.identifierRef,
			TypeRefsPackage.eINSTANCE.parameterizedTypeRef
		];
		val badObject = state.im.eAllContents.findFirst[replacedEClasses.contains(it.eClass)];
		assertNull("intermediate model should not contain objects of type " + badObject?.eClass?.name, badObject);
		// no cross reference in the IM to an element outside the IM (except in SymbolTableEntry)
		assertFalse(
			"intermediate model should not have a cross-reference to an element outside the intermediate model"
			+ " (except for SymbolTableEntry)",
			state.im.eAllContents.filter[!(allowedCrossRefToOutside(state.info))].exists[hasCrossRefToOutsideOf(state)]);
		// symbol table should exist
		val st = state.im.symbolTable;
		assertNotNull("intermediate model should have a symbol table", st);
		// check entries in symbol table
		for(ste : st.entries) {
			if(ste instanceof SymbolTableEntryOriginal) {
				assertNotNull("originalTarget should not be null", ste.originalTarget);
				assertFalse("originalTarget should not be an element in the intermediate model",
					ste.originalTarget.isElementInIntermediateModelOf(state));
			}
			if(allowDanglingSecondaryReferencesInSTEs) {
				// we allow dangling references in this case for the time being to make replacements in IM faster
				// -> no checks here
				// TODO consider disallowing dangling secondary references in symbol table entries
			} else {
				assertTrue("all elementsOfThisName should be elements in the intermediate model",
					ste.elementsOfThisName.forall[isElementInIntermediateModelOf(state)]);
				assertTrue("all referencingElements should be elements in the intermediate model",
					ste.referencingElements.forall[isElementInIntermediateModelOf(state)]);
				if(ste instanceof SymbolTableEntryOriginal) {
					if(ste.importSpecifier!==null) {
						assertTrue("importSpecifier should be an element in the intermediate model",
							ste.importSpecifier.isElementInIntermediateModelOf(state));
					}
				}
			}
		}
	}

	def private allowedCrossRefToOutside(EObject eobj, InformationRegistry info) {
		switch eobj {
			SymbolTableEntry: true
			default: false
		}
	}

	def private static boolean hasCrossRefToOutsideOf(EObject elementInIntermediateModel, TranspilerState state) {
		return elementInIntermediateModel.eCrossReferences.exists[
			if(!isElementInIntermediateModelOf(state)) {
				return true;
			}
			return false;
		];
	}

	def private static boolean isElementInIntermediateModelOf(EObject eobj, TranspilerState state) {
		return EcoreUtil2.getContainerOfType(eobj,Script_IM)===state.im
	}

	/** Asserts {@code value} to be <code>true</code> and throws an {@link AssertionError} otherwise. */
	def public static void assertTrue(String message, boolean value) throws AssertionError {
		if (!value) assertionFailure(message);
	}

	/** Asserts {@code value} to be <code>false</code> and throws an {@link AssertionError} otherwise. */
	def public static void assertFalse(String message, boolean value) throws AssertionError {
		if (value) assertionFailure(message);
	}

	/** Asserts {@code value} to be <code>null</code> and throws an {@link AssertionError} otherwise. */
	def public static void assertNull(String message, Object value) throws AssertionError {
		if (value!==null) assertionFailure(message);
	}

	/** Asserts {@code value} to be non-<code>null</code> and throws an {@link AssertionError} otherwise. */
	def public static void assertNotNull(String message, Object value) throws AssertionError {
		if (value===null) assertionFailure(message);
	}

	def private static void assertionFailure(String message) throws AssertionError {
		val ex = new AssertionError(message);
		ex.printStackTrace; // make sure we see this on the console even if exceptions are eaten up by someone
		throw ex;
	}

	def public static void dump(TranspilerState state) {
		println(dumpToString(state));
	}
	def public static String dumpToString(TranspilerState state) {
		val w = new StringWriter();
		dump(state, w);
		return w.toString;
	}
	/**
	 * Dumps the transpiler state to the given writer.
	 */
	def public static void dump(TranspilerState state, Writer out) {
		val w = new PrintWriter(out);
		w.dump(state.im, 0);
	}

	def private static void dump(PrintWriter w, EObject obj, int indentLevel) {
		w.indent(indentLevel);
		w.printObj(obj, true, indentLevel);
		w.println();

		for(child : obj.eContents) {
			w.dump(child, indentLevel+1);
		}
	}

	def private static void printObj(PrintWriter w, EObject obj, boolean includeCrossRefs, int indentLevel) {
		w.print(obj.eClass.name);
		if(obj instanceof IdentifiableElement || obj instanceof NamedElement || obj instanceof VariableDeclaration
			|| obj instanceof ImportSpecifier || obj instanceof SymbolTableEntry) {
			w.print(' @'+Integer.toHexString(obj.hashCode));
		}
		val objStr = obj.toString;
		val idx = objStr.indexOf('(');
		if(idx>=0) {
			w.print(' ' + objStr.substring(idx));
		}
		if(includeCrossRefs) {
			val crossRefs = obj.eClass.getEAllReferences.filter[!containment];
			if(!crossRefs.empty) {
				for(ref : crossRefs) {
					w.println();
					w.indent(indentLevel);
					w.print('--'+ref.name+'--> ');
					if(ref.many) {
						w.print('[');
						val targets = obj.eGet(ref) as EList<? extends EObject>;
						val iter = targets.iterator();
						while(iter.hasNext) {
							val currTarget = iter.next;
							if(currTarget!==null) {
								w.printObj(currTarget, false, indentLevel);
							} else {
								w.print('null');
							}
							if(iter.hasNext) {
								w.print(', ');
							}
						}
						w.print(']');
					} else {
						val target = obj.eGet(ref) as EObject;
						if(target!==null) {
							w.printObj(target, false, indentLevel);
						} else {
							w.print('null');
						}
					}
				}
			}
		}
	}

	def private static void indent(PrintWriter w, int indentLevel) {
		for(i : 0 ..< indentLevel) {
			w.print('\t');
		}
	}
}
