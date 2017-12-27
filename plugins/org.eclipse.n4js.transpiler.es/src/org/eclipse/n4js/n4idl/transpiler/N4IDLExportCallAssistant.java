package org.eclipse.n4js.n4idl.transpiler;

/**
 * An N4IDL specific export call assistant, that makes sure the
 * versioned name of a versioned type is used when exporting.
 */
public class N4IDLExportCallAssistant /*extends ExportCallAssistant */ {
//	@Override
//	protected ParameterizedCallExpression createExportExpression(SymbolTableEntry entry, Expression expression) {
//		// check if the entry points to a versionable element
//		if (entry instanceof SymbolTableEntryOriginal) {
//			IdentifiableElement originalTarget = ((SymbolTableEntryOriginal) entry).getOriginalTarget();
//
//			if (VersionUtils.isVersionable(originalTarget)) {
//				return createVersionedExportExpression(originalTarget, expression);
//			}
//		}
//		// otherwise fall-back to super implementation
//		return super.createExportExpression(entry, expression);
//	}
//
//	/**
//	 * Creates an export expression that exports an versioned element under
//	 * its versioned internal name.
//	 *
//	 * @param element The versioned internal name
//	 * @param expression The expression to export.
//	 */
//	protected ParameterizedCallExpression createVersionedExportExpression(IdentifiableElement element, Expression expression) {
//		if (!VersionUtils.isVersionable(element)) {
//			throw new IllegalArgumentException("Cannot export non-versionable element " + element + " as versionable.");
//		}
//
//		ParameterizedCallExpression callExpression = TranspilerBuilderBlocks._CallExpr();
//
//		callExpression.setTarget(TranspilerBuilderBlocks._IdentRef(steFor_$n4Export()));
//		EList<Argument> arguments = callExpression.getArguments();
//
//		arguments.add(TranspilerBuilderBlocks._Argument(TranspilerBuilderBlocks._StringLiteral(N4IDLTranspilerUtils.getVersionedInternalName(element))));
//		arguments.add(TranspilerBuilderBlocks._Argument(expression));
//
//		return callExpression;
//	}
}
