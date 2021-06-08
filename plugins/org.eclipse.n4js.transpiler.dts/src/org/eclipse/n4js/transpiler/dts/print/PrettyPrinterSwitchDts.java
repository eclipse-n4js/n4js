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
package org.eclipse.n4js.transpiler.dts.print;

import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.isLegalIdentifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportSpecifier;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ExportedVariableBinding;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.LocalArgumentsVariable;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.TypeProvidingElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;
import org.eclipse.n4js.parser.conversion.ValueConverterUtils;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.Script_IM;
import org.eclipse.n4js.transpiler.print.LineColTrackingAppendable;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

/**
 * Traverses an intermediate model and serializes it to a {@link LineColTrackingAppendable}. Client code should only use
 * the static method {@link #append(LineColTrackingAppendable, TranspilerState, Optional)}.
 */
public final class PrettyPrinterSwitchDts extends N4JSSwitch<Boolean> {

	/**
	 * Appends the given transpiler state's intermediate model to the given {@link LineColTrackingAppendable}.
	 */
	public static void append(LineColTrackingAppendable out, TranspilerState state, Optional<String> optPreamble) {
		final PrettyPrinterSwitchDts theSwitch = new PrettyPrinterSwitchDts(out, optPreamble);
		theSwitch.doSwitch(state.im);
	}

	/** Value to be returned from case-methods to indicate that processing is completed and should not be continued. */
	private static final Boolean DONE = Boolean.TRUE;

	private final LineColTrackingAppendable out;
	private final Optional<String> optPreamble;

	private PrettyPrinterSwitchDts(LineColTrackingAppendable out, Optional<String> optPreamble) {
		this.out = out;
		this.optPreamble = optPreamble;
	}

	@Override
	protected Boolean doSwitch(EClass eClass, EObject eObject) {
		// here we can check for entities of IM.xcore that do not have a super-class in n4js.xcore
		// if (eClass == ImPackage.eINSTANCE.getSnippet()) {
		// return caseSnippet((Snippet) eObject);
		// }
		return super.doSwitch(eClass, eObject);
	}

	@Override
	public Boolean defaultCase(EObject object) {
		// throw new IllegalStateException(
		// "PrettyPrinterSwitch missing a case for objects of type " + object.eClass().getName());
		return DONE;
	}

	@Override
	public Boolean caseScript(Script original) {
		final Script_IM original_IM = (Script_IM) original;
		processHashbang(original_IM.getHashbang());
		processPreamble();
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
	public Boolean caseN4ClassDeclaration(N4ClassDeclaration original) {
		writeIf("export ", original.isExported());
		writeIf("default ", original.isExportedAsDefault());
		processModifiers(original.getDeclaredModifiers(), " ");
		write("class ");
		write(original.getName());
		if (!original.getTypeVars().isEmpty()) {
			write("<");
			for (int i = 0; i < original.getTypeVars().size(); i++) {
				if (i > 0) {
					write(", ");
				}
				N4TypeVariable typeVar = original.getTypeVars().get(i);
				process(typeVar);
			}
			write(">");
		}
		write(' ');

		final TypeReferenceNode<ParameterizedTypeRef> superClassRef = original.getSuperClassRef();
		final Expression superClassExpression = original.getSuperClassExpression();
		if (superClassRef != null) {
			write("extends ");
			process(superClassRef);
			write(' ');
		} else if (superClassExpression != null) {
			write("extends ");
			process(superClassExpression);
			write(' ');
		}

		if (!original.getImplementedInterfaceRefs().isEmpty()) {
			write("implements ");
			for (int i = 0; i < original.getImplementedInterfaceRefs().size(); i++) {
				if (i > 0) {
					write(", ");
				}
				TypeReferenceNode<ParameterizedTypeRef> superInterfRef = original.getImplementedInterfaceRefs().get(i);
				process(superInterfRef);
			}
			write(' ');
		}

		processBlockLike(original.getOwnedMembersRaw(), '{', null, null, '}');
		return DONE;
	}

	@Override
	public Boolean caseN4InterfaceDeclaration(N4InterfaceDeclaration original) {
		writeIf("export ", original.isExported());
		writeIf("default ", original.isExportedAsDefault());
		processModifiers(original.getDeclaredModifiers(), " ");
		write("interface ");
		write(original.getName());
		if (!original.getTypeVars().isEmpty()) {
			write("<");
			for (int i = 0; i < original.getTypeVars().size(); i++) {
				if (i > 0) {
					write(", ");
				}
				N4TypeVariable typeVar = original.getTypeVars().get(i);
				process(typeVar);
			}
			write(">");
		}
		write(' ');

		if (!original.getSuperInterfaceRefs().isEmpty()) {
			write("extends ");
			for (int i = 0; i < original.getSuperInterfaceRefs().size(); i++) {
				if (i > 0) {
					write(", ");
				}
				TypeReferenceNode<ParameterizedTypeRef> superInterfRef = original.getSuperInterfaceRefs().get(i);
				process(superInterfRef);
			}
			write(' ');
		}

		// Workaround since TypeScript interfaces do not support static methods

		List<N4MemberDeclaration> staticMembers = new ArrayList<>();
		List<N4MemberDeclaration> nonStaticMembers = new ArrayList<>();
		for (N4MemberDeclaration member : original.getOwnedMembersRaw()) {
			if (member.isStatic()) {
				staticMembers.add(member);
			} else {
				nonStaticMembers.add(member);
			}
		}
		processBlockLike(nonStaticMembers, '{', null, null, '}');

		if (!staticMembers.isEmpty()) {
			writeIf("export ", original.isExported());
			write("namespace ");
			write(original.getName());

			// TODO rewrite static methods to functions
			processBlockLike(staticMembers, '{', null, null, '}');
		}
		return DONE;
	}

	@Override
	public Boolean caseN4EnumDeclaration(N4EnumDeclaration original) {
		writeIf("export ", original.isExported());
		writeIf("default ", original.isExportedAsDefault());
		processModifiers(original.getDeclaredModifiers(), " ");
		write("enum ");
		write(original.getName());

		processBlockLike(original.getLiterals(), '{', null, null, '}');
		return DONE;
	}

	@Override
	public Boolean caseN4FieldDeclaration(N4FieldDeclaration original) {
		processAnnotations(original.getAnnotations());
		processModifiers(original.getDeclaredModifiers(), " ");
		processPropertyName(original);
		processDeclaredTypeRef(original, " ");
		return DONE;
	}

	@Override
	public Boolean caseN4GetterDeclaration(N4GetterDeclaration original) {
		processAnnotations(original.getAnnotations());
		processModifiers(original.getDeclaredModifiers(), " ");
		write("get ");
		processPropertyName(original);
		write("() ");
		processDeclaredTypeRef(original, " ");
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean caseN4SetterDeclaration(N4SetterDeclaration original) {
		processAnnotations(original.getAnnotations());
		processModifiers(original.getDeclaredModifiers(), " ");
		write("set ");
		processPropertyName(original);
		write('(');
		process(original.getFpar());
		write(") ");
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean caseN4MethodDeclaration(N4MethodDeclaration original) {
		processAnnotations(original.getAnnotations());
		processModifiers(original.getDeclaredModifiers(), " ");
		if (original.isAsync()) {
			write("async ");
		}
		if (!original.getTypeVars().isEmpty()) {
			processTypeParams(original.getTypeVars());
			write(' ');
		}
		if (original.isGenerator()) {
			write("* ");
		}
		processPropertyName(original);
		write('(');
		process(original.getFpars(), ", ");
		write(") ");
		processReturnTypeRef(original, " ");
		process(original.getBody());
		return DONE;
	}

	@Override
	public Boolean caseLiteralOrComputedPropertyName(LiteralOrComputedPropertyName original) {
		processPropertyName(original);
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
		processReturnTypeRef(original, " ");
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
		processReturnTypeRef(original, " ");
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
		processReturnTypeRef(original, "");
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
		processDeclaredTypeRef(original, "");
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
	public Boolean caseExportedVariableDeclaration(ExportedVariableDeclaration original) {
		caseVariableDeclaration(original);
		return DONE;
	}

	@Override
	public Boolean caseVariableDeclaration(VariableDeclaration original) {
		processAnnotations(original.getAnnotations());
		write(original.getName());
		processDeclaredTypeRef(original, "");
		if (original.getExpression() != null) {
			write(" = ");
			process(original.getExpression());
		}
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

	private void writeIf(CharSequence csq, boolean condition) {
		if (condition) {
			write(csq);
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

	private void processHashbang(String hashbang) {
		if (!Strings.isNullOrEmpty(hashbang)) {
			write(prependHashbang(hashbang));
			newLine();
		}
	}

	private void processPreamble() {
		if (optPreamble.isPresent()) {
			write(optPreamble.get()); // #append(CharSequence) will convert '\n' to correct line separator
			newLine();
		}
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
		processPropertyName(name);
	}

	private void processPropertyName(LiteralOrComputedPropertyName name) {
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

	private void processModifiers(EList<N4Modifier> modifiers, String suffix) {
		processModifiers(modifiers);
		if (!modifiers.isEmpty()) {
			write(suffix);
		}
	}

	private void processReturnTypeRef(FunctionDefinition funDef, String suffix) {
		TypeRef declaredTypeRef = funDef.getDeclaredReturnTypeRef();
		if (declaredTypeRef == null)
			declaredTypeRef = funDef.getDeclaredReturnTypeRefInAST();
		if (declaredTypeRef == null)
			return;

		write(" : ");
		process(declaredTypeRef);
		write(suffix);
	}

	private void processDeclaredTypeRef(TypeProvidingElement elem, String suffix) {
		TypeRef declaredTypeRef = elem.getDeclaredTypeRef();
		if (declaredTypeRef == null)
			declaredTypeRef = elem.getDeclaredTypeRefInAST();
		if (declaredTypeRef == null)
			return;

		write(" : ");
		process(declaredTypeRef);
		write(suffix);
	}

	private void processTypeParams(EList<N4TypeVariable> typeParams) {
		if (typeParams.isEmpty())
			return;

		// In case of plain-JS output no types will be written
		throw new IllegalStateException("Type reference still left in code. typeParams=" + typeParams);
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

	private String quote(String txt) {
		return '\'' + ValueConverterUtils.convertToEscapedString(txt, false) + '\'';
	}

	private String prependHashbang(String txt) {
		return "#!" + txt;
	}

	/**
	 * We call this method in methods that we do not want to delete but aren't used and tests for now.
	 */
	private void throwUnsupportedSyntax() {
		throw new UnsupportedOperationException("syntax not supported by pretty printer");
	}
}
