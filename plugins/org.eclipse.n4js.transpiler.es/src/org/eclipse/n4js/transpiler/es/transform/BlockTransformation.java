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
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableStatement;

import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.types.TVariable;

/**
 */
public class BlockTransformation extends Transformation {

	/** Name for capturing local-arguments-environment in distinct variable on entering a block. */
	public static final String $CAPTURE_ARGS = "$capturedArgs";

	@Override
	public void assertPreConditions() {
		// true
	}

	@Override
	public void assertPostConditions() {
		// true
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		for (FunctionOrFieldAccessor fofa : collectNodes(getState().im, FunctionOrFieldAccessor.class, true)) {
			transformArguments(fofa);
		}
	}

	/** capture arguments-variable to be accessible in re-written arrow-functions */
	private void transformArguments(FunctionOrFieldAccessor funcOrAccess) {
		FunctionOrFieldAccessor fofa = getState().tracer.getOriginalASTNodeOfSameType(funcOrAccess, false);
		if (fofa == null) {
			return;
		}
		TVariable argsVar = fofa.getImplicitArgumentsVariable();
		if (argsVar == null) {
			return;
		}

		// 1. rename old IdentifierRef_IM pointing to --> arguments with fresh name.
		// 2. introduce new IdentifierRef_IM with (new) name 'arguments'
		// 3. wire up freshname to new arguments in first line of block.
		String newName = $CAPTURE_ARGS;

		SymbolTableEntryOriginal arguments_STE = getSymbolTableEntryOriginal(argsVar, false);
		if (arguments_STE == null || arguments_STE.getReferencingElements().isEmpty()) {
			// no references to this local arguments variable -> no capturing required
			return;
		}

		// arguments_STE.referencingElements.empty // + also need to check if we contain a arrow-function accessing
		// arguments.

		// 1.) RENAME
		rename(arguments_STE, newName);

		// skip ArrowFunctions: (this is the main reason for the capturing here :-)
		if (funcOrAccess instanceof ArrowFunction)
			return;
		// skip empty bodies:
		if (funcOrAccess.getBody().getStatements().isEmpty())
			return;

		// 2 + 3.) CAPTURE arguments:

		Statement bodyFirstStatement = funcOrAccess.getBody().getStatements().get(0);
		// note, there must be something in the body otherwise the 'arguments' variable could not have been accessed!

		// new SymbolTableEntry for 'real' arguments ( the other was renamed above )
		SymbolTableEntryInternal argumentsSTE = steFor_arguments();

		insertBefore(bodyFirstStatement,
				_VariableStatement(_VariableDeclaration(
						newName, _IdentRef(argumentsSTE))));
	}
}
