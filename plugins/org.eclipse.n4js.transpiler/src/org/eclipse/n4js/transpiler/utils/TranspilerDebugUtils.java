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
package org.eclipse.n4js.transpiler.utils;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forall;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.findFirst;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.Script_IM;
import org.eclipse.n4js.transpiler.im.SymbolTable;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Some utilities for transpiler debugging, mainly dumping a {@link TranspilerState} to {@code stdout}, etc.
 */
public class TranspilerDebugUtils {

	/**
	 * Perform some consistency checks on the transpiler state. For example, this asserts that no node in the
	 * intermediate model has a direct cross-reference to the original AST or an original TModule element.
	 */
	public void validateState(TranspilerState state, boolean allowDanglingSecondaryReferencesInSTEs)
			throws AssertionError {
		// IM should not contain entities from n4js.xcore / TypeRefs.xcore for which a replacement in IM.xcore exists
		List<EClass> replacedEClasses = List.of(
				N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression(),
				N4JSPackage.eINSTANCE.getIdentifierRef(),
				TypeRefsPackage.eINSTANCE.getParameterizedTypeRef());
		EObject badObject = findFirst(state.im.eAllContents(), elem -> replacedEClasses.contains(elem.eClass()));
		assertNull("intermediate model should not contain objects of type " +
				(badObject != null
						? badObject.eClass() != null ? badObject.eClass().getName() : "null"
						: "null"),
				badObject);
		// no cross reference in the IM to an element outside the IM (except in SymbolTableEntry)
		assertFalse(
				"intermediate model should not have a cross-reference to an element outside the intermediate model"
						+ " (except for SymbolTableEntry)",
				exists(filter(state.im.eAllContents(), elem -> !(allowedCrossRefToOutside(elem))),
						elem -> hasCrossRefToOutsideOf(elem, state)));

		// symbol table should exist
		SymbolTable st = state.im.getSymbolTable();
		assertNotNull("intermediate model should have a symbol table", st);
		// check entries in symbol table
		for (SymbolTableEntry ste : st.getEntries()) {
			if (ste instanceof SymbolTableEntryOriginal) {
				assertNotNull("originalTarget should not be null",
						((SymbolTableEntryOriginal) ste).getOriginalTarget());
				assertFalse("originalTarget should not be an element in the intermediate model",
						isElementInIntermediateModelOf(((SymbolTableEntryOriginal) ste).getOriginalTarget(), state));
			}
			if (allowDanglingSecondaryReferencesInSTEs) {
				// we allow dangling references in this case for the time being to make replacements in IM faster
				// -> no checks here
				// TODO consider disallowing dangling secondary references in symbol table entries
			} else {
				assertTrue("all elementsOfThisName should be elements in the intermediate model",
						forall(ste.getElementsOfThisName(), elem -> isElementInIntermediateModelOf(elem, state)));
				assertTrue("all referencingElements should be elements in the intermediate model",
						forall(ste.getReferencingElements(), elem -> isElementInIntermediateModelOf(elem, state)));
				if (ste instanceof SymbolTableEntryOriginal) {
					if (((SymbolTableEntryOriginal) ste).getImportSpecifier() != null) {
						assertTrue("importSpecifier should be an element in the intermediate model",
								isElementInIntermediateModelOf(((SymbolTableEntryOriginal) ste).getImportSpecifier(),
										state));
					}
				}
			}
		}
	}

	private boolean allowedCrossRefToOutside(EObject eobj) {
		if (eobj instanceof SymbolTableEntry) {
			return true;
		}
		return false;
	}

	private static boolean hasCrossRefToOutsideOf(EObject elementInIntermediateModel, TranspilerState state) {
		return exists(elementInIntermediateModel.eCrossReferences(),
				cref -> !isElementInIntermediateModelOf(cref, state));
	}

	private static boolean isElementInIntermediateModelOf(EObject eobj, TranspilerState state) {
		return EcoreUtil2.getContainerOfType(eobj, Script_IM.class) == state.im;
	}

	/** Asserts {@code value} to be <code>true</code> and throws an {@link AssertionError} otherwise. */
	public static void assertTrue(String message, boolean value) throws AssertionError {
		if (!value)
			assertionFailure(message);
	}

	/** Asserts {@code value} to be <code>false</code> and throws an {@link AssertionError} otherwise. */
	public static void assertFalse(String message, boolean value) throws AssertionError {
		if (value)
			assertionFailure(message);
	}

	/** Asserts {@code value} to be <code>null</code> and throws an {@link AssertionError} otherwise. */
	public static void assertNull(String message, Object value) throws AssertionError {
		if (value != null)
			assertionFailure(message);
	}

	/** Asserts {@code value} to be non-<code>null</code> and throws an {@link AssertionError} otherwise. */
	public static void assertNotNull(String message, Object value) throws AssertionError {
		if (value == null)
			assertionFailure(message);
	}

	private static void assertionFailure(String message) throws AssertionError {
		AssertionError ex = new AssertionError(message);
		ex.printStackTrace(); // make sure we see this on the console even if exceptions are eaten up by someone
		throw ex;
	}

	/***/
	public static void dump(TranspilerState state) {
		System.out.println(dumpToString(state));
	}

	/***/
	public static String dumpToString(TranspilerState state) {
		StringWriter w = new StringWriter();
		dump(state, w);
		return w.toString();
	}

	/**
	 * Dumps the transpiler state to the given writer.
	 */
	public static void dump(TranspilerState state, Writer out) {
		PrintWriter w = new PrintWriter(out);
		dump(w, state.im, 0);
	}

	private static void dump(PrintWriter w, EObject obj, int indentLevel) {
		indent(w, indentLevel);
		printObj(w, obj, true, indentLevel);
		w.println();

		for (EObject child : obj.eContents()) {
			dump(w, child, indentLevel + 1);
		}
	}

	private static void printObj(PrintWriter w, EObject obj, boolean includeCrossRefs, int indentLevel) {
		w.print(obj.eClass().getName());
		if (obj instanceof IdentifiableElement || obj instanceof NamedElement || obj instanceof VariableDeclaration
				|| obj instanceof ImportSpecifier || obj instanceof SymbolTableEntry) {
			w.print(" @" + Integer.toHexString(obj.hashCode()));
		}
		String objStr = obj.toString();
		int idx = objStr.indexOf("(");
		if (idx >= 0) {
			w.print(" " + objStr.substring(idx));
		}
		if (includeCrossRefs) {
			List<EReference> crossRefs = toList(filter(obj.eClass().getEAllReferences(), ref -> !ref.isContainment()));
			if (!crossRefs.isEmpty()) {
				for (EReference ref : crossRefs) {
					w.println();
					indent(w, indentLevel);
					w.print("--" + ref.getName() + "--> ");
					if (ref.isMany()) {
						w.print("[");
						@SuppressWarnings("unchecked")
						EList<? extends EObject> targets = (EList<? extends EObject>) obj.eGet(ref);
						Iterator<? extends EObject> iter = targets.iterator();
						while (iter.hasNext()) {
							EObject currTarget = iter.next();
							if (currTarget != null) {
								printObj(w, currTarget, false, indentLevel);
							} else {
								w.print("null");
							}
							if (iter.hasNext()) {
								w.print(", ");
							}
						}
						w.print("]");
					} else {
						EObject target = (EObject) obj.eGet(ref);
						if (target != null) {
							printObj(w, target, false, indentLevel);
						} else {
							w.print("null");
						}
					}
				}
			}
		}
	}

	private static void indent(PrintWriter w, int indentLevel) {
		for (int i = 0; i < indentLevel; i++) {
			w.print("\t");
		}
	}
}
