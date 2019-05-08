/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.n4idl;

import org.eclipse.n4js.n4idl.transpiler.utils.N4IDLTranspilerUtils;
import org.eclipse.n4js.transpiler.es.transform.ModuleWrappingTransformationNEW;

/**
 * Module/Script wrapping transformation for transpiling N4IDL modules to ECMAScript.
 *
 * This subclass of the original {@link ModuleWrappingTransformationNEW} implements custom behavior for exporting
 * versioned types in N4IDL. To avoid name conflicts at runtime, different versions are exported under their versioned
 * internal names (cf. {@link N4IDLTranspilerUtils#getVersionedInternalName}).
 */
public class N4IDLModuleWrappingTransformation extends ModuleWrappingTransformationNEW {

	// FIXME GH-1281 !!! adjust N4IDL to ES6 imports / exports
// @formatter:off
//	@Override
//	protected ParameterizedCallExpression createExportExpression(SymbolTableEntry entry) {
//		// check if the entry points to a versionable element
//		if (entry instanceof SymbolTableEntryOriginal) {
//			final IdentifiableElement originalTarget = ((SymbolTableEntryOriginal) entry).getOriginalTarget();
//
//			if (VersionableUtils.isTVersionable(originalTarget)) {
//				return createVersionedExportExpression(originalTarget, TranspilerBuilderBlocks._IdentRef(entry));
//			}
//		}
//		// otherwise fall-back to super implementation
//		return super.createExportExpression(entry);
//	}
//
//	/**
//	 * Creates an export expression that exports an versioned element under its versioned internal name.
//	 *
//	 * @param element
//	 *            The versioned internal name
//	 * @param expression
//	 *            The expression to export.
//	 */
//	private ParameterizedCallExpression createVersionedExportExpression(IdentifiableElement element,
//			Expression expression) {
//		if (!VersionableUtils.isTVersionable(element)) {
//			throw new IllegalArgumentException("Cannot export non-versionable element " + element + " as versionable.");
//		}
//
//		final ParameterizedCallExpression callExpression = TranspilerBuilderBlocks._CallExpr();
//
//		callExpression.setTarget(TranspilerBuilderBlocks._IdentRef(steFor_$n4Export()));
//		final EList<Argument> arguments = callExpression.getArguments();
//
//		arguments.add(TranspilerBuilderBlocks._Argument(
//				TranspilerBuilderBlocks._StringLiteral(N4IDLTranspilerUtils.getVersionedInternalName(element))));
//		arguments.add(TranspilerBuilderBlocks._Argument(expression));
//
//		return callExpression;
//	}
// @formatter:on
}
