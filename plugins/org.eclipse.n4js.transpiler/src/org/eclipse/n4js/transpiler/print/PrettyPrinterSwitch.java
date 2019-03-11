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
package org.eclipse.n4js.transpiler.print;

import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.isLegalIdentifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.conversion.ValueConverterUtils;
import org.eclipse.n4js.n4JS.*;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.Script_IM;
import org.eclipse.n4js.transpiler.im.Snippet;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Traverses an intermediate model and serializes it to a {@link SourceMapAwareAppendable}. Client code should only use
 * the static method {@link #append(SourceMapAwareAppendable, TranspilerState)}.
 */
/* package */ final class PrettyPrinterSwitch extends N4JSSwitch<Boolean> {

	/**
	 * Appends the given transpiler state's intermediate model to the given {@link SourceMapAwareAppendable}.
	 */
	public static void append(SourceMapAwareAppendable out, TranspilerState state) {
		final PrettyPrinterSwitch theSwitch = new PrettyPrinterSwitch(out);
		theSwitch.doSwitch(state.im);
	}

	/** Value to be returned from case-methods to indicate that processing is completed and should not be continued. */
	private static final Boolean DONE = Boolean.TRUE;

	private final SourceMapAwareAppendable out;

	private PrettyPrinterSwitch(SourceMapAwareAppendable out) {
		this.out = out;
	}

	@Override
	protected Boolean doSwitch(EClass eClass, EObject eObject) {
		// here we can check for entities of IM.xcore that do not have a super-class in n4js.xcore
		if (eClass == ImPackage.eINSTANCE.getSnippet()) {
			return caseSnippet((Snippet) eObject);
		}
		return super.doSwitch(eClass, eObject);
	}

	@Override
	protected Boolean doSwitch(int classifierID, EObject elemInIM) {
		out.openRegion(elemInIM);
		try {
			final Boolean result = super.doSwitch(classifierID, elemInIM);
			return result;
		} finally {
			out.closeRegion(elemInIM);
		}
	}

	@Override
	public Boolean defaultCase(EObject object) {
		throw new IllegalStateException(
				"PrettyPrinterSwitch missing a case for objects of type " + object.eClass().getName());
	}

	@Override
	public Boolean caseScript(Script original) {
		final Script_IM original_IM = (Script_IM) original;
		processAnnotations(original_IM.getAnnotations());
		process(original_IM.getScriptElements(), () -> {
			newLine();
		});
		return DONE;
	}

	@Override
	public Boolean caseExportDeclaration(ExportDeclaration original) {
		if (original.getReexportedFrom() != null) {
			throwUnsupportedSyntax();
		}
		processAnnotations(original.getAnnotations());
		write("export ");
		final List<ExportSpecifier> namedExports = original.getNamedExports();
		if (!namedExports.isEmpty()) {
			write("{ ");
			process(namedExports, ", ");
			write(" }");
		} else {
			if (original.isDefaultExport()) {
				write("default ");
			}
			final ExportableElement exportedElement = original.getExportedElement();
			if (exportedElement != null) {
				process(exportedElement);
			} else {
				final Expression exportedExpression = original.getDefaultExportedExpression();
				if (exportedExpression != null && original.isDefaultExport()) {
					process(exportedExpression);
					write(';');
				}
			}
		}
		return DONE;
	}

	@Override
	public Boolean caseExportSpecifier(ExportSpecifier original) {
		process(original.getElement());
		final String alias = original.getAlias();
		if (alias != null) {
			write(" as ");
			write(alias);
		}
		return DONE;
	}

	@Override
	public Boolean caseImportDeclaration(ImportDeclaration original) {
		processAnnotations(original.getAnnotations());
		write("import ");
		// 1) import specifiers
		List<ImportSpecifier> importSpecifiers = new ArrayList<>(original.getImportSpecifiers());
		if (!importSpecifiers.isEmpty() && importSpecifiers.get(0) instanceof DefaultImportSpecifier) {
			process(importSpecifiers.remove(0));
			if (!importSpecifiers.isEmpty()) {
				write(", ");
			}
		}
		if (!importSpecifiers.isEmpty()) {
			final boolean isNamespaceImport = importSpecifiers.get(0) instanceof NamespaceImportSpecifier;
			if (isNamespaceImport) {
				process(importSpecifiers.get(0)); // syntax does not allow more than one namespace import
			} else {
				write('{');
				process(importSpecifiers, ", ");
				write('}');
			}
		}
		// 2) "from"
		if (original.isImportFrom()) {
			write(" from ");
		}
		// 3) module specifier
		String moduleSpecifier = original.getModuleSpecifierAsText() != null
				? original.getModuleSpecifierAsText()
				: original.getModule().getQualifiedName();
		write(quote(moduleSpecifier));
		// 4) empty line after block of imports
		boolean isLastImport = !(EcoreUtil2.getNextSibling(original) instanceof ImportDeclaration);
		if (isLastImport) {
			newLine();
		}
		return DONE;
	}

	/** Also handles DefaultImportSpecifier (which is a subclass of NamedImportSpecifier). */
	@Override
	public Boolean caseNamedImportSpecifier(NamedImportSpecifier original) {
		write(original.getImportedElementAsText());
		final String alias = original.getAlias();
		if (alias != null && !original.isDefaultImport()) {
			write(" as ");
			write(alias);
		}
		return DONE;
	}

	@Override
	public Boolean caseNamespaceImportSpecifier(NamespaceImportSpecifier original) {
		write("* as ");
		write(original.getAlias());
		return DONE;
	}

	@Override
	public Boolean caseFunctionDeclaration(FunctionDeclaration original) {
		processAnnotations(original.getAnnotations());
		if (!original.getDeclaredModifiers().isEmpty()) {
			processModifiers(original.getDeclaredModifiers());
			write(' ');
		}
		if (original.isAsync()) {
			write("async ");
		}
		write("function ");
		if (!original.getTypeVars().isEmpty()) {
			processTypeParams(original.getTypeVars());
			write(' ');
		}
		if (original.isGenerator()) {
			write("* ");
		}
		write(original.getName());
		write('(');
		process(original.getFpars(), ", ");
		write(") ");
		if (original.getReturnTypeRef() != null) {
			processReturnTypeRef(original.getReturnTypeRef());
			write(' ');
		}
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean caseFunctionExpression(FunctionExpression original) {
		processAnnotations(original.getAnnotations());
		if (original.isAsync()) {
			write("async ");
		}
		write("function");
		if (!original.getTypeVars().isEmpty()) {
			write(' ');
			processTypeParams(original.getTypeVars());
		}
		if (original.isGenerator()) {
			write(" *");
		}
		if (original.getName() != null) {
			write(' ');
			write(original.getName());
		}
		write('(');
		process(original.getFpars(), ", ");
		write(") ");
		if (original.getReturnTypeRef() != null) {
			processReturnTypeRef(original.getReturnTypeRef());
			write(' ');
		}
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean caseArrowFunction(ArrowFunction original) {
		if (original.isAsync()) {
			write("async");
		}
		write('(');
		process(original.getFpars(), ", ");
		write(')');
		processReturnTypeRef(original.getReturnTypeRef());
		write("=>");
		if (original.isHasBracesAroundBody()) {
			process(original.getBody());
		} else {
			if (!original.isSingleExprImplicitReturn()) {
				throw new IllegalStateException(
						"arrow function without braces must be a valid single-expression arrow function");
			}
			final Expression singleExpr = original.getSingleExpression();
			process(singleExpr);
		}
		return DONE;
	}

	@Override
	public Boolean caseLocalArgumentsVariable(LocalArgumentsVariable original) {
		// ignore
		return DONE;
	}

	@Override
	public Boolean caseFormalParameter(FormalParameter original) {
		processAnnotations(original.getAnnotations(), false);
		if (original.isVariadic()) {
			write("...");
		}
		write(original.getName());
		processTypeRef(original.getDeclaredTypeRef());
		if (original.getInitializer() != null) {
			write("=");
			process(original.getInitializer());
		}
		return DONE;
	}

	@Override
	public Boolean caseBlock(Block original) {
		processBlock(original.getStatements());
		return DONE;
	}

	@Override
	public Boolean caseVariableStatement(VariableStatement original) {
		write(keyword(original.getVarStmtKeyword()));
		write(' ');
		process(original.getVarDeclsOrBindings(), ", ");
		// alternative to previous line would be:
		// out.indent();
		// process(original.getVarDeclsOrBindings(), () -> {
		// write(',');
		// newLine();
		// });
		// out.undent();
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseExportedVariableStatement(ExportedVariableStatement original) {
		// note: an ExportedVariableStatement is always a child of an ExportDeclaration and the "export" keyword is
		// emitted there; so, no need to emit "export" in this method!
		if (!original.getDeclaredModifiers().isEmpty()) {
			processModifiers(original.getDeclaredModifiers());
			write(' ');
		}
		caseVariableStatement(original);
		return DONE;
	}

	private String keyword(VariableStatementKeyword varStmtKeyword) {
		switch (varStmtKeyword) {
		case LET:
			return "let";
		case CONST:
			return "const";
		case VAR:
			return "var";
		default:
			throw new UnsupportedOperationException("unsupported variable statement keyword");
		}
	}

	@Override
	public Boolean caseVariableDeclaration(VariableDeclaration original) {
		processAnnotations(original.getAnnotations());
		write(original.getName());
		processTypeRef(original.getDeclaredTypeRef());
		if (original.getExpression() != null) {
			write(" = ");
			process(original.getExpression());
		}
		return DONE;
	}

	@Override
	public Boolean caseExportedVariableDeclaration(ExportedVariableDeclaration original) {
		caseVariableDeclaration(original);
		return DONE;
	}

	@Override
	public Boolean caseVariableBinding(VariableBinding original) {
		process(original.getPattern());
		if (original.getExpression() != null) {
			write(" = ");
			process(original.getExpression());
		}
		return DONE;
	}

	@Override
	public Boolean caseExportedVariableBinding(ExportedVariableBinding original) {
		caseExportedVariableBinding(original);
		return DONE;
	}

	@Override
	public Boolean caseEmptyStatement(EmptyStatement original) {
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseExpressionStatement(ExpressionStatement original) {
		process(original.getExpression());
		if (!(original.getExpression() instanceof Snippet))
			write(';');
		return DONE;
	}

	@Override
	public Boolean caseIfStatement(IfStatement original) {
		write("if (");
		process(original.getExpression());
		write(") ");
		final Statement ifStmnt = original.getIfStmt();
		processInBlock(ifStmnt);
		final Statement elseStmnt = original.getElseStmt();
		if (elseStmnt != null) {
			write(" else ");
			if (elseStmnt instanceof IfStatement) {
				process(elseStmnt); // don't enforce block in this case to better support "else if"
			} else {
				processInBlock(elseStmnt);
			}
		}
		return DONE;
	}

	@Override
	public Boolean caseDoStatement(DoStatement original) {
		write("do ");
		processInBlock(original.getStatement());
		write(" while(");
		process(original.getExpression());
		write(");");
		return DONE;
	}

	@Override
	public Boolean caseWhileStatement(WhileStatement original) {
		write("while(");
		process(original.getExpression());
		write(") ");
		processInBlock(original.getStatement());
		return DONE;
	}

	@Override
	public Boolean caseForStatement(ForStatement original) {
		write("for(");
		if (!original.getVarDeclsOrBindings().isEmpty()) {
			write(keyword(original.getVarStmtKeyword()));
			write(' ');
			process(original.getVarDeclsOrBindings(), ", ");
		} else if (original.getInitExpr() != null) {
			process(original.getInitExpr());
		}
		if (original.isForPlain()) {
			write(';');
			processIfNonNull(original.getExpression());
			write(';');
			processIfNonNull(original.getUpdateExpr());
		} else {
			write(original.isForOf() ? " of " : " in ");
			process(original.getExpression());
		}
		write(") ");
		processInBlock(original.getStatement());
		return DONE;
	}

	@Override
	public Boolean caseContinueStatement(ContinueStatement original) {
		write("continue");
		if (original.getLabel() != null) {
			write(' ');
			write(original.getLabel().getName());
		}
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseBreakStatement(BreakStatement original) {
		write("break");
		if (original.getLabel() != null) {
			write(' ');
			write(original.getLabel().getName());
		}
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseReturnStatement(ReturnStatement original) {
		write("return");
		if (original.getExpression() != null) {
			write(' ');
			process(original.getExpression());
		}
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseWithStatement(WithStatement original) {
		write("with (");
		process(original.getExpression());
		write(") ");
		processInBlock(original.getStatement());
		return DONE;
	}

	@Override
	public Boolean caseSwitchStatement(SwitchStatement original) {
		write("switch(");
		process(original.getExpression());
		write(") ");
		processBlockLike(original.getCases(), '{', null, null, '}');
		return DONE;
	}

	@Override
	public Boolean caseCaseClause(CaseClause original) {
		write("case ");
		process(original.getExpression());
		write(':');
		final boolean isFallthrough = original.getStatements().isEmpty();
		if (!isFallthrough) {
			out.indent();
			newLine();
			process(original.getStatements(), () -> {
				newLine();
			});
			out.undent(); // don't add a NL here!
		}
		return DONE;
	}

	@Override
	public Boolean caseDefaultClause(DefaultClause original) {
		write("default:");
		if (!original.getStatements().isEmpty()) {
			out.indent();
			newLine();
			process(original.getStatements(), () -> {
				newLine();
			});
			out.undent(); // don't add a NL here!
		}
		return DONE;
	}

	@Override
	public Boolean caseLabelledStatement(LabelledStatement original) {
		write(original.getName());
		write(": ");
		process(original.getStatement());
		return DONE;
	}

	@Override
	public Boolean caseThrowStatement(ThrowStatement original) {
		write("throw ");
		process(original.getExpression());
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseTryStatement(TryStatement original) {
		write("try ");
		process(original.getBlock());
		processIfNonNull(original.getCatch());
		processIfNonNull(original.getFinally());
		return DONE;
	}

	@Override
	public Boolean caseCatchBlock(CatchBlock original) {
		write(" catch(");
		process(original.getCatchVariable());
		write(") ");
		process(original.getBlock());
		return DONE;
	}

	@Override
	public Boolean caseCatchVariable(CatchVariable original) {
		write(original.getName());
		return DONE;
	}

	@Override
	public Boolean caseFinallyBlock(FinallyBlock original) {
		write(" finally ");
		process(original.getBlock());
		return DONE;
	}

	@Override
	public Boolean caseDebuggerStatement(DebuggerStatement original) {
		write("debugger");
		write(';');
		return DONE;
	}

	@Override
	public Boolean caseParenExpression(ParenExpression original) {
		write('(');
		process(original.getExpression());
		write(')');
		return DONE;
	}

	@Override
	public Boolean caseIdentifierRef(IdentifierRef original) {
		final IdentifierRef_IM original_IM = (IdentifierRef_IM) original;
		final SymbolTableEntry ste = original_IM.getId_IM();
		write(ste.getName());
		return DONE;
	}

	@Override
	public Boolean caseSuperLiteral(SuperLiteral original) {
		write("super");
		return DONE;
	}

	@Override
	public Boolean caseThisLiteral(ThisLiteral original) {
		write("this");
		return DONE;
	}

	@Override
	public Boolean caseArrayLiteral(ArrayLiteral original) {
		final List<ArrayElement> elements = original.getElements();
		final boolean lastIsPadding = !elements.isEmpty() && elements.get(elements.size() - 1) instanceof ArrayPadding;
		final String lastLineEnd = lastIsPadding || original.isTrailingComma() ? "," : null;
		processBlockLike(original.getElements(), '[', ",", lastLineEnd, ']');
		return DONE;
	}

	@Override
	public Boolean caseArrayElement(ArrayElement original) {
		if (original.isSpread())
			write("...");
		process(original.getExpression());
		return DONE;
	}

	@Override
	public Boolean caseArrayPadding(ArrayPadding original) {
		// nothing to emit here (separators are taken care of in #caseArrayLiteral())
		return DONE;
	}

	@Override
	public Boolean caseObjectLiteral(ObjectLiteral original) {
		processBlockLike(original.getPropertyAssignments(), '{', ",", null, '}');
		return DONE;
	}

	@Override
	public Boolean casePropertyAssignmentAnnotationList(PropertyAssignmentAnnotationList original) {
		processAnnotations(original.getAnnotations());
		return DONE;
	}

	@Override
	public Boolean casePropertyNameValuePair(PropertyNameValuePair original) {
		if (original.getDeclaredName() != null) {
			processPropertyName(original);
			write(": ");
		} else {
			// FIXME PNVP without name only legal in destructuring pattern!!
		}
		process(original.getExpression());
		return DONE;
	}

	@Override
	public Boolean casePropertyNameValuePairSingleName(PropertyNameValuePairSingleName original) {
		process(original.getIdentifierRef());
		if (original.getExpression() != null) {
			write(" = ");
			process(original.getExpression());
		}
		return DONE;
	}

	@Override
	public Boolean casePropertyGetterDeclaration(PropertyGetterDeclaration original) {
		write("get ");
		processPropertyName(original);
		write("() ");
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean casePropertySetterDeclaration(PropertySetterDeclaration original) {
		write("set ");
		processPropertyName(original);
		write('(');
		process(original.getFpar());
		write(") ");
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean casePropertyMethodDeclaration(PropertyMethodDeclaration original) {
		if (original.isGenerator()) {
			write("* ");
		}
		processPropertyName(original);
		write('(');
		process(original.getFpars(), ", ");
		write(") ");
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean caseNewExpression(NewExpression original) {
		write("new ");
		process(original.getCallee());
		processTypeArgs(original.getTypeArgs());
		write('(');
		process(original.getArguments(), ", ");
		write(')');
		return DONE;
	}

	@Override
	public Boolean caseParameterizedCallExpression(ParameterizedCallExpression original) {
		processTypeArgs(original.getTypeArgs());
		process(original.getTarget());
		write('(');
		process(original.getArguments(), ", ");
		write(')');
		return DONE;
	}

	@Override
	public Boolean caseArgument(Argument original) {
		if (original.isSpread()) {
			write("... ");
		}
		process(original.getExpression());
		return DONE;
	}

	@Override
	public Boolean caseIndexedAccessExpression(IndexedAccessExpression original) {
		process(original.getTarget());
		write('[');
		process(original.getIndex());
		write(']');
		return DONE;
	}

	@Override
	public Boolean caseParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression original) {
		final ParameterizedPropertyAccessExpression_IM original_IM = (ParameterizedPropertyAccessExpression_IM) original;
		final String propName = original_IM.getPropertyName();
		process(original_IM.getTarget());
		if (isLegalIdentifier(propName)) {
			write('.');
			processTypeArgs(original.getTypeArgs());
			write(propName);
		} else {
			// NOTE: re-writing a property access into an index access, here, is not 100% clean; instead, we could
			// throw an exception here and require an additional (late) AST transformation that transforms all property
			// access expression without legal identifier into access expressions; but this would be overkill.
			write('[');
			writeQuoted(propName);
			write(']');
		}
		return DONE;
	}

	@Override
	public Boolean caseYieldExpression(YieldExpression original) {
		// IDE-2004: parenthesis added w.r.t Firefox-implementation, where yield - used as subexpression e.g. in a list
		// of formal parameters - requires parenthesis around the whole yield-expression.
		write('(');

		write("yield ");
		if (original.isMany())
			write("* ");

		if (original.getExpression() != null)
			process(original.getExpression());

		write(')');
		return DONE;
	}

	@Override
	public Boolean caseNullLiteral(NullLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseBooleanLiteral(BooleanLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseDoubleLiteral(DoubleLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseIntLiteral(IntLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseOctalIntLiteral(OctalIntLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseHexIntLiteral(HexIntLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseScientificIntLiteral(ScientificIntLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseNumericLiteral(NumericLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean caseStringLiteral(StringLiteral original) {
		if (original.getRawValue() != null) {
			write(original.getRawValue());
		} else {
			write(quote(original.getValueAsString()));
		}
		return DONE;
	}

	@Override
	public Boolean caseTemplateLiteral(TemplateLiteral original) {
		final int indentLevelOLD = out.getIndentLevel();
		try {
			out.setIndentLevel(0);
			for (Expression segment : original.getSegments()) {
				process(segment);
			}
		} finally {
			out.setIndentLevel(indentLevelOLD);
		}
		return DONE;
	}

	@Override
	public Boolean caseTemplateSegment(TemplateSegment original) {
		if (out.getIndentLevel() != 0) {
			// note: if the segment contains new line characters, the indent level must be 0 because otherwise 'out'
			// would add incorrect indentation inside the template segment; we could reset it to 0 here, but since
			// TemplateSegments may only appear as children of TemplateLiterals and #caseTemplateLiteral() is also
			// resetting the indent level, we can here simply rely on our parent having already done this.
			throw new IllegalStateException("parent TemplateLiteral did not reset the indent level to 0");
		}
		if (original.getRawValue() != null) {
			write(original.getRawValue());
		} else {
			final TemplateLiteral parent = (TemplateLiteral) original.eContainer();
			final List<Expression> segments = parent.getSegments();
			final int len = segments.size();
			final Expression first = segments.get(0);
			final Expression last = segments.get(len - 1);
			if (original == first) {
				write("`");
			} else {
				write("}");
			}
			final String rawValue = ValueConverterUtils.convertToEscapedString(original.getValueAsString(), false);
			write(rawValue);
			if (original == last) {
				write("`");
			} else {
				write("${");
			}
		}
		return DONE;
	}

	@Override
	public Boolean caseRegularExpressionLiteral(RegularExpressionLiteral original) {
		write(original.getValueAsString());
		return DONE;
	}

	@Override
	public Boolean casePostfixExpression(PostfixExpression original) {
		process(original.getExpression());
		write(original.getOp().getLiteral());
		return DONE;
	}

	@Override
	public Boolean caseUnaryExpression(UnaryExpression original) {
		final UnaryOperator op = original.getOp();
		write(op.getLiteral());
		// the following 3 unary operators require an additional space between operator and operand
		if (op == UnaryOperator.TYPEOF || op == UnaryOperator.DELETE || op == UnaryOperator.VOID) {
			write(' ');
		}
		process(original.getExpression());
		return DONE;
	}

	@Override
	public Boolean caseCastExpression(CastExpression original) {
		process(original.getExpression());
		write(" as ");
		write(original.getTargetTypeRef().getTypeRefAsString());
		return DONE;
	}

	@Override
	public Boolean caseMultiplicativeExpression(MultiplicativeExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseAdditiveExpression(AdditiveExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseShiftExpression(ShiftExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseRelationalExpression(RelationalExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseEqualityExpression(EqualityExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseBinaryBitwiseExpression(BinaryBitwiseExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseBinaryLogicalExpression(BinaryLogicalExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseAssignmentExpression(AssignmentExpression original) {
		processBinaryExpression(original.getLhs(), original.getOp().getLiteral(), original.getRhs());
		return DONE;
	}

	@Override
	public Boolean caseConditionalExpression(ConditionalExpression original) {
		process(original.getExpression());
		write(" ? ");
		process(original.getTrueExpression());
		write(" : ");
		process(original.getFalseExpression());
		return DONE;
	}

	@Override
	public Boolean caseCommaExpression(CommaExpression original) {
		process(original.getExprs(), ", ");
		return DONE;
	}

	@Override
	public Boolean caseAwaitExpression(AwaitExpression original) {
		write("await ");
		process(original.getExpression());
		return DONE;
	}

	@Override
	public Boolean caseObjectBindingPattern(ObjectBindingPattern original) {
		processBlockLike(original.getProperties(), '{', ",", null, '}');
		return DONE;
	}

	@Override
	public Boolean caseArrayBindingPattern(ArrayBindingPattern original) {
		final List<BindingElement> elements = original.getElements();
		final BindingElement last = !elements.isEmpty() ? elements.get(elements.size() - 1) : null;
		final boolean lastIsElision = last != null && last.isElision();
		final String lastLineEnd = lastIsElision ? "," : null;
		processBlockLike(original.getElements(), '[', ",", lastLineEnd, ']');
		return DONE;
	}

	@Override
	public Boolean caseBindingProperty(BindingProperty original) {
		if (original.getDeclaredName() == null) {
			// single-name binding
			process(original.getValue().getVarDecl());
		} else {
			write(original.getName());
			write(": ");
			process(original.getValue());
		}
		return DONE;
	}

	@Override
	public Boolean caseBindingElement(BindingElement original) {
		if (original.isRest()) {
			write("... ");
		}
		if (original.getNestedPattern() != null) {
			process(original.getNestedPattern());
			if (original.getExpression() != null) {
				write(" = ");
				process(original.getExpression());
			}
		} else if (original.getVarDecl() != null) {
			process(original.getVarDecl());
		} else {
			// elision:
			// nothing to emit here (separators are taken care of in #caseArrayBindingPattern())
			// (similar as with ArrayPadding elements)
		}
		return DONE;
	}

	public Boolean caseSnippet(Snippet original) {
		String code = original.getCode();
		if (code.endsWith("\n"))
			code = code.substring(0, code.length() - 1);
		write(code);
		return DONE;
	}

	// ###############################################################################################################
	// UTILITY AND CONVENIENCE METHODS

	private void write(char c) {
		try {
			out.append(c);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	private void write(CharSequence csq) {
		try {
			out.append(csq);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	private void writeQuoted(String csq) {
		write(quote(csq));
	}

	private void writeQuotedIfNonIdentifier(String csq) {
		if (!isLegalIdentifier(csq)) {
			writeQuoted(csq);
		} else {
			write(csq);
		}
	}

	private void newLine() {
		try {
			out.newLine();
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	private void process(Iterable<? extends EObject> elemsInIM, String separator) {
		final Iterator<? extends EObject> iter = elemsInIM.iterator();
		while (iter.hasNext()) {
			doSwitch(iter.next());
			if (separator != null && iter.hasNext()) {
				write(separator);
			}
		}
	}

	private void process(Iterable<? extends EObject> elemsInIM, Runnable separator) {
		final Iterator<? extends EObject> iter = elemsInIM.iterator();
		while (iter.hasNext()) {
			process(iter.next());
			if (separator != null && iter.hasNext()) {
				separator.run();
			}
		}
	}

	private void processIfNonNull(EObject elemInIM) {
		if (elemInIM != null) {
			doSwitch(elemInIM);
		}
	}

	private void process(EObject elemInIM) {
		if (elemInIM == null) {
			throw new IllegalArgumentException("element to process may not be null");
		}
		doSwitch(elemInIM);
	}

	private void processAnnotations(Iterable<? extends Annotation> annotations) {
		processAnnotations(annotations, true);
	}

	private void processAnnotations(@SuppressWarnings("unused") Iterable<? extends Annotation> annotations,
			@SuppressWarnings("unused") boolean multiLine) {
		// throw exception if
		// if (annotations.iterator().hasNext()) {
		// throw new IllegalStateException("Annotations left in the code: " + Joiner.on(",").join(annotations));
		// }
	}

	private void processPropertyName(PropertyNameOwner owner) {
		final LiteralOrComputedPropertyName name = owner.getDeclaredName();
		final PropertyNameKind kind = name.getKind();
		if (kind == PropertyNameKind.COMPUTED) {
			// computed property names:
			write('[');
			process(name.getExpression());
			write(']');
		} else {
			// all other cases than computed property names: IDENTIFIER, STRING, NUMBER
			final String propName = name.getName();
			if (propName.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX)) {
				// we have a name like "#iterator" that represents a Symbol --> emit as: "[Symbol.iterator]"
				// (note: we have to do this special handling here in the pretty printer because there is, at the
				// moment, no way to represent a property assignment with a Symbol as name other than using a name
				// starting with the SYMBOL_IDENTIFIER_PREFIX)
				write("[Symbol.");
				write(propName.substring(1));
				write(']');
			} else {
				// standard case:
				writeQuotedIfNonIdentifier(propName);
			}
		}
	}

	private void processModifiers(EList<N4Modifier> modifiers) {
		final int len = modifiers.size();
		for (int idx = 0; idx < len; idx++) {
			if (idx > 0) {
				write(' ');
			}
			write(modifiers.get(idx).getName());
		}
	}

	private void processReturnTypeRef(TypeRef returnTypeRef) {
		if (returnTypeRef == null)
			return;

		// In case of plain-JS output no types will be written
		throw new IllegalStateException("Return type reference still left in code. typeref=" + returnTypeRef + " in "
				+ EcoreUtil2.getContainerOfType(returnTypeRef, FunctionOrFieldAccessor.class));

		// if(returnTypeRef!=null) {
		// write(" : ");
		// process(returnTypeRef);
		// write(' ');
		// }
	}

	private void processTypeRef(TypeRef declaredTypeRef) {
		if (declaredTypeRef == null)
			return;

		// In case of plain-JS output no types will be written
		throw new IllegalStateException("Type reference still left in code. typeRef=" + declaredTypeRef);
	}

	private void processTypeParams(EList<TypeVariable> typeParams) {
		if (typeParams.isEmpty())
			return;

		// In case of plain-JS output no types will be written
		throw new IllegalStateException("Type reference still left in code. typeParams=" + typeParams);
	}

	private void processTypeArgs(EList<? extends TypeArgument> typeArgs) {
		if (typeArgs.isEmpty())
			return;

		// In case of plain-JS output no types will be written
		throw new IllegalStateException("Type arguments still left in code. typeArgs=" + typeArgs);
	}

	private void processInBlock(Statement statement) {
		if (statement instanceof Block) {
			processBlock(((Block) statement).getStatements());
		} else {
			processBlock(Collections.singletonList(statement));
		}
	}

	private void processBlock(Collection<? extends Statement> statements) {
		processBlockLike(statements, '{', null, null, '}');
	}

	/**
	 * Process and indent the given elements in the same way as blocks are indented but using the given characters for
	 * opening and closing the code section.
	 */
	private void processBlockLike(Collection<? extends EObject> elemsInIM, char open, String lineEnd,
			String lastLineEnd, char close) {
		if (elemsInIM.isEmpty()) {
			write(open);
			write(close);
			return;
		}
		write(open);
		out.indent();
		newLine();
		process(elemsInIM, () -> {
			if (lineEnd != null)
				write(lineEnd);
			newLine();
		});
		if (lastLineEnd != null)
			write(lineEnd);
		out.undent();
		newLine();
		write(close);
	}

	private void processBinaryExpression(Expression lhs, String op, Expression rhs) {
		process(lhs);
		write(' ');
		write(op);
		write(' ');
		process(rhs);
	}

	private String quote(String txt) {
		return '\'' + ValueConverterUtils.convertToEscapedString(txt, false) + '\'';
	}

	/**
	 * We call this method in methods that we do not want to delete but aren't used and tests for now.
	 */
	private void throwUnsupportedSyntax() {
		throw new UnsupportedOperationException("syntax not supported by pretty printer");
	}
}
