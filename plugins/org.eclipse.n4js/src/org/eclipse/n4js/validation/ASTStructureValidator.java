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
package org.eclipse.n4js.validation;

import static org.eclipse.n4js.N4JSLanguageConstants.EVAL_NAME;
import static org.eclipse.n4js.N4JSLanguageConstants.IMPORT_KEYWORD;
import static org.eclipse.n4js.N4JSLanguageConstants.LOCAL_ARGUMENTS_VARIABLE_NAME;
import static org.eclipse.n4js.N4JSLanguageConstants.RESERVED_WORDS_IN_STRICT_MODE;
import static org.eclipse.n4js.N4JSLanguageConstants.YIELD_KEYWORD;
import static org.eclipse.n4js.n4JS.DestructureUtils.isTopOfDestructuringForStatement;
import static org.eclipse.n4js.parser.conversion.AbstractN4JSStringValueConverter.WARN_ISSUE_CODE;
import static org.eclipse.n4js.parser.conversion.AbstractN4JSStringValueConverter.hasOctalEscapeSequence;
import static org.eclipse.n4js.validation.helper.FunctionValidationHelper.internalCheckFormalParameter;
import static org.eclipse.xtext.xbase.lib.IntegerExtensions.bitwiseAnd;
import static org.eclipse.xtext.xbase.lib.IntegerExtensions.bitwiseNot;
import static org.eclipse.xtext.xbase.lib.IntegerExtensions.bitwiseOr;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.AbstractVariable;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.CoalesceExpression;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.IterationStatement;
import org.eclipse.n4js.n4JS.LabelRef;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.LegacyOctalIntLiteral;
import org.eclipse.n4js.n4JS.MethodDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldAccessor;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NewTarget;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PostfixOperator;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.StrictModeRelevant;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.n4JS.WithStatement;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.parser.InternalSemicolonInjectingParser;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.StringLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtend.lib.annotations.ToString;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * A utility that validates the structure of the AST in one pass.
 *
 * Note: The validations here are important for using plain JavaScript, especially the EcmaScript test suite relies on
 * validations here. Validations that are in the package 'validators' are not considered when the EcmaScript test suite
 * is executed.
 */
@SuppressWarnings({ "javadoc", "unused" })
public class ASTStructureValidator {

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private N4JSLanguageHelper languageHelper;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	@ToString
	protected static class Constraints {
		static int BUILT_IN_TYPE_DEFINITION = 1;
		static int STRICT = BUILT_IN_TYPE_DEFINITION << 1;
		static int N4JS = STRICT << 1;
		static int EXTERNAL = N4JS << 1;
		static int ALLOW_NESTED_FUNCTION_DECLARATION = EXTERNAL << 1;
		static int ALLOW_RETURN = ALLOW_NESTED_FUNCTION_DECLARATION << 1;
		static int ALLOW_CONTINUE = ALLOW_RETURN << 1;
		static int ALLOW_BREAK = ALLOW_CONTINUE << 1;// whether we are in "break-allowed-with/without-label"
														// (for||while||switch-case||labelled-block) area
		static int ALLOW_BREAK_WITHOUT_LABEL = ALLOW_BREAK << 1; // whether we are in (for||while||switch-case) area
		static int ALLOW_VAR_WITHOUT_INITIALIZER = ALLOW_BREAK_WITHOUT_LABEL << 1;
		static int ALLOW_YIELD_EXPRESSION = ALLOW_VAR_WITHOUT_INITIALIZER << 1;
		static int ALLOW_SUPER = ALLOW_YIELD_EXPRESSION << 1;
		static int ALLOW_SUPER_CALL = ALLOW_SUPER << 1;
		static int IN_FUNCTION_DECLARATION = ALLOW_SUPER_CALL << 1;

		private final int bits;

		private static int getIf(int value, boolean b) {
			return (b) ? value : 0;
		}

		public Constraints(boolean builtInTypeDefinition, boolean n4js, boolean external) {
			this(
					bitwiseOr(getIf(BUILT_IN_TYPE_DEFINITION, builtInTypeDefinition),
							bitwiseOr(getIf(N4JS, n4js),
									bitwiseOr(getIf(EXTERNAL, external),
											bitwiseOr(ALLOW_VAR_WITHOUT_INITIALIZER,
													ALLOW_YIELD_EXPRESSION)))));
		}

		public Constraints(int bits) {
			this.bits = bits;
		}

		private boolean is(int bit) {
			return bitwiseAnd(this.bits, bit) != 0;
		}

		public boolean isBuiltInTypeDefinition() {
			return is(BUILT_IN_TYPE_DEFINITION);
		}

		public boolean isN4JS() {
			return is(N4JS);
		}

		public boolean isStrict() {
			return is(N4JS) || is(STRICT);
		}

		public boolean isExternal() {
			return is(EXTERNAL);
		}

		public boolean isNestedFunctionAllowed() {
			return is(ALLOW_NESTED_FUNCTION_DECLARATION);
		}

		public boolean isInFunctionDeclaration() {
			return is(IN_FUNCTION_DECLARATION);
		}

		public boolean isReturnAllowed() {
			return is(ALLOW_RETURN);
		}

		public boolean isBreakAllowed() {
			return is(ALLOW_BREAK);
		}

		public boolean isBreakAllowedWithoutLabel() {
			return is(ALLOW_BREAK_WITHOUT_LABEL);
		}

		public boolean isContinueAllowed() {
			return is(ALLOW_CONTINUE);
		}

		public boolean isVarInitializerRequired() {
			return !is(ALLOW_VAR_WITHOUT_INITIALIZER);
		}

		public boolean isYieldExpressionAllowed() {
			return is(ALLOW_YIELD_EXPRESSION);
		}

		public boolean isSuperLiteralAllowed() {
			return is(ALLOW_SUPER);
		}

		public boolean isSuperCallAllowed() {
			return is(ALLOW_SUPER_CALL);
		}

		public Constraints with(int bit, boolean set) {
			int newBits = (set) ? bitwiseOr(this.bits, bit)
					: bitwiseAnd(this.bits, bitwiseNot(bit));
			if (newBits == this.bits) {
				return this;
			}
			return new Constraints(newBits);
		}

		public Constraints strict(boolean strict) {
			return with(STRICT, strict);
		}

		public Constraints allowNestedFunctions(boolean allow) {
			return with(ALLOW_NESTED_FUNCTION_DECLARATION, allow);
		}

		public Constraints allowBreak(boolean allow) {
			return with(ALLOW_BREAK, allow);
		}

		public Constraints allowBreakWithoutLabel(boolean allow) {
			return with(ALLOW_BREAK_WITHOUT_LABEL, allow);
		}

		public Constraints allowContinue(boolean allow) {
			return with(ALLOW_CONTINUE, allow);
		}

		public Constraints allowReturn(boolean allow) {
			return with(ALLOW_RETURN, allow);
		}

		public Constraints allowVarWithoutInitializer(boolean allow) {
			return with(ALLOW_VAR_WITHOUT_INITIALIZER, allow);
		}

		public Constraints allowYieldExpression(boolean allow) {
			return with(ALLOW_YIELD_EXPRESSION, allow);
		}

		public Constraints allowSuperLiteral(boolean allow) {
			if (!allow) {
				return allowSuperCall(false).with(ALLOW_SUPER, allow);
			} else {
				return with(ALLOW_SUPER, allow);
			}
		}

		public Constraints allowSuperCall(boolean allow) {
			return with(ALLOW_SUPER_CALL, allow);
		}

		public Constraints enterFunctionDeclaration() {
			return with(IN_FUNCTION_DECLARATION, true);
		}

	}

	public void validate(EObject model, IDiagnosticConsumer consumer) {
		Resource resource = model == null ? null : model.eResource();
		if (resource != null && !workspaceAccess.isNoValidate(resource, resource.getURI())) {
			ASTStructureDiagnosticProducer producer = new ASTStructureDiagnosticProducer(consumer);
			validateASTStructure(model, producer, Sets.newHashSetWithExpectedSize(2),
					new Constraints(
							N4Scheme.isResourceWithN4Scheme(resource),
							jsVariantHelper.isN4JSMode(model),
							jsVariantHelper.isExternalMode(model)));
		}
	}

	private void recursiveValidateASTStructure(
			EObject model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		Iterator<EObject> content = model.eContents().iterator();
		boolean newStrict = constraints.isStrict();
		boolean first = true;
		while (content.hasNext()) {
			EObject next = content.next();

			// TODO this looks wrong: "strict mode" must be defined as first statement only!
			// see IDE-163, also see JavaScriptVariant
			// SZ: "use strict" must not be the first entry in the prolog, see
			// test/language/directive-prologue/14.1-5gs.js
			newStrict = newStrict || (first && isUseStrictProlog(model, next));
			first = first && isProlog(next);
			if (next instanceof StrictModeRelevant) {
				((StrictModeRelevant) next).setStrictMode(newStrict);
			}
			validateASTStructure(
					next,
					producer,
					validLabels,
					constraints.strict(newStrict));
		}
	}

	private boolean isProlog(EObject object) {
		if (object instanceof ExpressionStatement) {
			return ((ExpressionStatement) object).getExpression() instanceof StringLiteral;
		}
		return false;
	}

	private boolean isUseStrictProlog(EObject model, EObject next) {
		if (model instanceof Script || model instanceof Block && model.eContainer() instanceof FunctionDefinition) {
			if (next instanceof ExpressionStatement) {
				Expression expression = ((ExpressionStatement) next).getExpression();
				if (expression instanceof StringLiteral) {
					StringLiteral slit = (StringLiteral) expression;
					return BaseJavaScriptVariantHelper.STRICT_MODE_LITERAL_VALUE.equals(slit.getValue());
				}
			}
		}
		return false;
	}

	private void validateASTStructure(final EObject model, final ASTStructureDiagnosticProducer producer,
			final Set<LabelledStatement> validLabels, final ASTStructureValidator.Constraints constraints) {
		if (model instanceof LegacyOctalIntLiteral) {
			_validateASTStructure((LegacyOctalIntLiteral) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof N4EnumDeclaration) {
			_validateASTStructure((N4EnumDeclaration) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof PropertyMethodDeclaration) {
			_validateASTStructure((PropertyMethodDeclaration) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof PropertyNameValuePair) {
			_validateASTStructure((PropertyNameValuePair) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof StringLiteral) {
			_validateASTStructure((StringLiteral) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof TemplateSegment) {
			_validateASTStructure((TemplateSegment) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof ThisTypeRef) {
			_validateASTStructure((ThisTypeRef) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof TypeVariable) {
			_validateASTStructure((TypeVariable) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof ForStatement) {
			_validateASTStructure((ForStatement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof FormalParameter) {
			_validateASTStructure((FormalParameter) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof FunctionDeclaration) {
			_validateASTStructure((FunctionDeclaration) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof FunctionExpression) {
			_validateASTStructure((FunctionExpression) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof IdentifierRef) {
			_validateASTStructure((IdentifierRef) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof MethodDeclaration) {
			_validateASTStructure((MethodDeclaration) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof N4ClassifierDefinition) {
			_validateASTStructure((N4ClassifierDefinition) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof N4FieldAccessor) {
			_validateASTStructure((N4FieldAccessor) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof ParameterizedCallExpression) {
			_validateASTStructure((ParameterizedCallExpression) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof SuperLiteral) {
			_validateASTStructure((SuperLiteral) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof VariableDeclaration) {
			_validateASTStructure((VariableDeclaration) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof VariableStatement) {
			_validateASTStructure((VariableStatement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof NumericLiteralTypeRef) {
			_validateASTStructure((NumericLiteralTypeRef) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof StringLiteralTypeRef) {
			_validateASTStructure((StringLiteralTypeRef) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof AbstractVariable) {
			_validateASTStructure((AbstractVariable<?>) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof AssignmentExpression) {
			_validateASTStructure((AssignmentExpression) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof BinaryLogicalExpression) {
			_validateASTStructure((BinaryLogicalExpression) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof Block) {
			_validateASTStructure((Block) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof BreakStatement) {
			_validateASTStructure((BreakStatement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof CoalesceExpression) {
			_validateASTStructure((CoalesceExpression) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof ContinueStatement) {
			_validateASTStructure((ContinueStatement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof FieldAccessor) {
			_validateASTStructure((FieldAccessor) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof IfStatement) {
			_validateASTStructure((IfStatement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof IterationStatement) {
			_validateASTStructure((IterationStatement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof LabelledStatement) {
			_validateASTStructure((LabelledStatement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof NewTarget) {
			_validateASTStructure((NewTarget) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof PostfixExpression) {
			_validateASTStructure((PostfixExpression) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof PropertyAssignment) {
			_validateASTStructure((PropertyAssignment) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof ReturnStatement) {
			_validateASTStructure((ReturnStatement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof UnaryExpression) {
			_validateASTStructure((UnaryExpression) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof VariableBinding) {
			_validateASTStructure((VariableBinding) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof WithStatement) {
			_validateASTStructure((WithStatement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof YieldExpression) {
			_validateASTStructure((YieldExpression) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof AbstractCaseClause) {
			_validateASTStructure((AbstractCaseClause) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof Annotation) {
			_validateASTStructure((Annotation) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof ArrayElement) {
			_validateASTStructure((ArrayElement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof BindingElement) {
			_validateASTStructure((BindingElement) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof Expression) {
			_validateASTStructure((Expression) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof N4EnumLiteral) {
			_validateASTStructure((N4EnumLiteral) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof N4TypeVariable) {
			_validateASTStructure((N4TypeVariable) model, producer, validLabels, constraints);
			return;
		} else if (model instanceof Script) {
			_validateASTStructure((Script) model, producer, validLabels, constraints);
			return;
		} else if (model != null) {
			_validateASTStructure(model, producer, validLabels, constraints);
			return;
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.<Object> asList(model, producer, validLabels, constraints).toString());
		}
	}

	private void _validateASTStructure(
			EObject model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			Script model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.strict(false).allowNestedFunctions(true).allowReturn(false).allowContinue(false)
						.allowBreak(false).allowBreakWithoutLabel(false));
	}

	private void _validateASTStructure(
			CoalesceExpression model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		EObject container = model.eContainer();
		if (container instanceof BinaryLogicalExpression) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			producer.setNode(target);
			producer.addDiagnostic(
					newDiagnosticMessage(IssueCodes.AST_INVALID_COALESCE_PARENT,
							((BinaryLogicalExpression) container).getOp().getLiteral()));
		} else if (model.getExpression() instanceof BinaryLogicalExpression) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model.getExpression());
			producer.setNode(target);
			producer.addDiagnostic(
					newDiagnosticMessage(IssueCodes.AST_INVALID_COALESCE_CHILD,
							((BinaryLogicalExpression) model.getExpression()).getOp().getLiteral()));
		} else if (model.getDefaultExpression() instanceof BinaryLogicalExpression) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model.getDefaultExpression());
			producer.setNode(target);
			producer.addDiagnostic(
					newDiagnosticMessage(IssueCodes.AST_INVALID_COALESCE_CHILD,
							((BinaryLogicalExpression) model.getDefaultExpression()).getOp().getLiteral()));
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			N4ClassifierDefinition model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (model instanceof N4ClassifierDeclaration) {
			N4ClassifierDeclaration cd = (N4ClassifierDeclaration) model;
			if (cd.getName() == null && !cd.isExportedAsDefault()) {
				ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
				producer.setNode(target);
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_TYPE_DECL_MISSING_NAME));
			}
		}

		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				// according to ecma6 spec, class bodies are always strict
				constraints.strict(true).allowNestedFunctions(true).allowReturn(false).allowContinue(false)
						.allowBreak(false).allowBreakWithoutLabel(false));
	}

	private void _validateASTStructure(
			N4EnumDeclaration model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (model.getName() == null && !model.isExportedAsDefault()) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_TYPE_DECL_MISSING_NAME));
		}

		if (model.getLiterals().isEmpty()) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.ENM_WITHOUT_LITERALS));
		}

		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				// according to ecma6 spec, class bodies are always strict
				constraints.strict(true).allowNestedFunctions(true).allowReturn(false).allowContinue(false)
						.allowBreak(false).allowBreakWithoutLabel(false));
	}

	private void _validateASTStructure(
			N4EnumLiteral model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (!N4JSLanguageUtils.isEnumLiteralValueExpressionValid(model)) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(model,
					N4JSPackage.eINSTANCE.getN4EnumLiteral_ValueExpression());
			producer.setNode(head(nodes));
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.ENM_INVALID_VALUE_EXPRESSION));
		}

		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			LegacyOctalIntLiteral model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (constraints.isStrict()) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_STR_NO_OCTALS));
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			NumericLiteralTypeRef model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (constraints.isStrict()) {
			INode node = head(
					NodeModelUtils.findNodesForFeature(model, TypeRefsPackage.Literals.LITERAL_TYPE_REF__AST_VALUE));
			if (node != null) {
				String text = NodeModelUtils.getTokenText(node);
				if (text.length() >= 2 && text.startsWith("0") && Character.isDigit(text.charAt(1))) {
					producer.setNode(node);
					producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_STR_NO_OCTALS));
				}
			}
		}

		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			StringLiteral model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (constraints.isStrict()) {
			addErrorForOctalEscapeSequence(model.getRawValue(), model, N4JSPackage.Literals.STRING_LITERAL__VALUE,
					producer);
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			TemplateSegment model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		addErrorForOctalEscapeSequence(model.getRawValue(), model, N4JSPackage.Literals.TEMPLATE_SEGMENT__VALUE,
				producer);

		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			StringLiteralTypeRef model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		INode node = head(
				NodeModelUtils.findNodesForFeature(model, TypeRefsPackage.Literals.LITERAL_TYPE_REF__AST_VALUE));
		if (node != null) {
			String text = NodeModelUtils.getTokenText(node);
			addErrorForOctalEscapeSequence(text, model, TypeRefsPackage.Literals.LITERAL_TYPE_REF__AST_VALUE, producer);
		}

		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void addErrorForOctalEscapeSequence(String rawValue, EObject model, EAttribute valueEAttribute,
			ASTStructureDiagnosticProducer producer) {
		List<INode> nodes = NodeModelUtils.findNodesForFeature(model, valueEAttribute);
		INode target = head(nodes);
		SyntaxErrorMessage syntaxError = target.getSyntaxErrorMessage();
		if ((syntaxError == null || syntaxError.getIssueCode() == WARN_ISSUE_CODE
				|| syntaxError.getIssueCode() == InternalSemicolonInjectingParser.SEMICOLON_INSERTED)
				&& hasOctalEscapeSequence(rawValue)) {
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_STR_NO_OCTALS));
		}
	}

	private void _validateASTStructure(
			PostfixExpression model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
		Expression child = model.getExpression();
		if (!child.isValidSimpleAssignmentTarget()) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(model,
					N4JSPackage.Literals.POSTFIX_EXPRESSION__EXPRESSION);
			INode target = head(nodes);
			producer.setNode(target);
			String operand = (model.getOp() == PostfixOperator.DEC) ? "decrement" : "increment";
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_OPERAND, operand));
		}
	}

	private void _validateASTStructure(
			UnaryExpression model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
		if (model.getOp() == UnaryOperator.DEC || model.getOp() == UnaryOperator.INC) {
			Expression child = model.getExpression();
			if (child != null && !child.isValidSimpleAssignmentTarget()) {
				List<INode> nodes = NodeModelUtils.findNodesForFeature(model,
						N4JSPackage.Literals.POSTFIX_EXPRESSION__EXPRESSION);
				INode target = head(nodes);
				producer.setNode(target);
				String operand = (model.getOp() == UnaryOperator.DEC) ? "decrement" : "increment";
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_OPERAND, operand));
			}
		}
	}

	private void _validateASTStructure(
			YieldExpression model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (!constraints.isYieldExpressionAllowed()) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_YIELD_EXPRESSION));
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			AssignmentExpression model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		// first validate the children to make sure strictMode flag was set
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
		if (model.getLhs() != null && !N4JSLanguageUtils.hasValidLHS(model)) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(model,
					N4JSPackage.Literals.ASSIGNMENT_EXPRESSION__LHS);
			INode target = head(nodes);
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_EXP_INVALID_LHS_ASS));
		}
	}

	private void _validateASTStructure(
			ParameterizedCallExpression model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (N4JSLanguageUtils.isDynamicImportCall(model)) {
			if (!model.getArguments().isEmpty() && model.getArguments().get(0).isSpread()) {
				ICompositeNode target = NodeModelUtils.findActualNodeFor(model.getArguments().get(0));
				producer.setNode(target);
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_IMPORT_CALL_SPREAD));
			}
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			IdentifierRef model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		String name = model.getIdAsText();
		if (name != null) {
			if (constraints.isStrict() && (RESERVED_WORDS_IN_STRICT_MODE.contains(name))) {
				if (IMPORT_KEYWORD.equals(name)
						&& model.eContainingFeature() == N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET) {
					// allow use of 'import' here
				} else {
					issueNameDiagnostic(model, producer, name, N4JSPackage.Literals.IDENTIFIER_REF__ID, Severity.ERROR);
				}
			}
			if (model.eContainingFeature() == N4JSPackage.Literals.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT
					&& N4JSLanguageConstants.EXPORT_DEFAULT_NAME.equals(name)) {
				EObject grandParent = model.eContainer().eContainer();
				if (grandParent instanceof ExportDeclaration) {
					if (((ExportDeclaration) grandParent).getExportedElement() == null
							&& !((ExportDeclaration) grandParent).isReexport()) {
						ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
						producer.setNode(target);
						producer.addDiagnostic(
								newDiagnosticMessage(IssueCodes.AST_SEPARATE_DEFAULT_EXPORT_WITHOUT_FROM));
					}
				}
			}
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			AbstractVariable<?> model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		String name = model.getName();
		if (name != null) {
			if (LOCAL_ARGUMENTS_VARIABLE_NAME.equals(name)) {
				boolean isFparInN4jsd = constraints.isExternal()// here: isExternal <==> file extension is ".n4jsd"
						&& (model instanceof FormalParameter);
				if (!isFparInN4jsd) {
					issueArgumentsError(model, name, constraints.isStrict(), producer);
				}
			} else {
				if (!YIELD_KEYWORD.equals(name) && (languageHelper.getECMAKeywords().contains(name)
						|| "enum".equals(name) || "await".equals(name)
						|| "true".equals(name) || "false".equals(name) || "null".equals(name))) {

					if (constraints.isBuiltInTypeDefinition() && "import".equals(name)) {
						// ignore
					} else {
						issueNameDiagnostic(model, producer, name);
					}
				} else if (constraints.isStrict()) {
					if (RESERVED_WORDS_IN_STRICT_MODE.contains(name) || EVAL_NAME.equals(name)) {
						issueNameDiagnostic(model, producer, name);
						model.setName(name); // do not pollute scope
					}
				}
			}
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			WithStatement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (constraints.isStrict()) {
			ILeafNode node = findFirst(NodeModelUtils.findActualNodeFor(model).getLeafNodes(),
					n -> n.getGrammarElement() == grammarAccess.getWithStatementAccess().getWithKeyword_0());
			producer.setNode(node);
			if (node != null) {
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_STR_NO_WITH_STMT));
			}
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			LabelledStatement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		String name = model.getName();
		if (name != null) {
			if (constraints.isStrict() && (RESERVED_WORDS_IN_STRICT_MODE.contains(name))) {
				issueNameDiagnostic(model, producer, name);
				model.setName(null); // do not pollute scope
			}
		}
		try {
			validLabels.add(model);
			recursiveValidateASTStructure(
					model,
					producer,
					validLabels,
					constraints.allowNestedFunctions(!constraints.isStrict()).allowBreak(true));
		} finally {
			validLabels.remove(model);
		}
	}

	private void _validateASTStructure(
			Block model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		validateBlockStructure(
				model.eContainer(),
				model,
				producer,
				validLabels,
				constraints);
	}

	private void validateBlockStructure(final EObject container, final Block model,
			final ASTStructureDiagnosticProducer producer, final Set<LabelledStatement> validLabels,
			final ASTStructureValidator.Constraints constraints) {
		if (container instanceof FunctionDefinition) {
			_validateBlockStructure((FunctionDefinition) container, model, producer, validLabels, constraints);
			return;
		} else if (container instanceof IfStatement) {
			_validateBlockStructure((IfStatement) container, model, producer, validLabels, constraints);
			return;
		} else if (container instanceof IterationStatement) {
			_validateBlockStructure((IterationStatement) container, model, producer, validLabels, constraints);
			return;
		} else if (container instanceof CatchBlock) {
			_validateBlockStructure((CatchBlock) container, model, producer, validLabels, constraints);
			return;
		} else if (container != null) {
			_validateBlockStructure(container, model, producer, validLabels, constraints);
			return;
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.<Object> asList(container, model, producer, validLabels, constraints).toString());
		}
	}

	private void _validateBlockStructure(
			IfStatement container,
			Block model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateBlockStructure(
			IterationStatement container,
			Block model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowNestedFunctions(!constraints.isStrict() && !constraints.isInFunctionDeclaration()));
	}

	private void _validateBlockStructure(
			FunctionDefinition container,
			Block model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowNestedFunctions(true));
	}

	private void _validateBlockStructure(
			CatchBlock container,
			Block model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowNestedFunctions(true));
	}

	private void _validateBlockStructure(
			EObject container,
			Block model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowNestedFunctions(!constraints.isStrict()));
	}

	private void _validateASTStructure(
			IfStatement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowNestedFunctions(!constraints.isStrict() && !constraints.isInFunctionDeclaration()));
	}

	private void _validateASTStructure(
			AbstractCaseClause model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowBreak(true).allowBreakWithoutLabel(true));
	}

	private void _validateASTStructure(
			ForStatement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (model.isAwait() && !model.isForOf()) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(model, N4JSPackage.Literals.FOR_STATEMENT__AWAIT);
			INode target = nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(model) : nodes.get(0);
			if (target != null) {
				producer.setNode(target);
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_FOR_AWAIT));
			}
		}
		if (!model.isForPlain()) {
			if (!model.getVarDeclsOrBindings().isEmpty()) {
				for (VariableDeclaration varDecl : model.getVarDecl()) {
					if (varDecl.getExpression() != null && !(varDecl.eContainer() instanceof BindingElement)
							&& (constraints.isStrict() || model.getVarStmtKeyword() == VariableStatementKeyword.LET)) {
						List<INode> nodes = NodeModelUtils.findNodesForFeature(varDecl,
								N4JSPackage.Literals.VARIABLE_DECLARATION__EXPRESSION);
						INode target = !nodes.isEmpty() ? nodes.get(0) : NodeModelUtils.findActualNodeFor(varDecl);
						producer.setNode(target);
						if (target != null) {
							Severity severity = (constraints.isStrict() || model.isForIn())
									? IssueCodes.AST_VAR_DECL_IN_FOR_INVALID_INIT.severity
									: Severity.WARNING;
							producer.addDiagnostic(
									new DiagnosticMessage(IssueCodes.AST_VAR_DECL_IN_FOR_INVALID_INIT.getMessage(),
											severity,
											IssueCodes.AST_VAR_DECL_IN_FOR_INVALID_INIT.name()));
						}
					} else if (model.getVarStmtKeyword() == VariableStatementKeyword.LET
							&& "let".equals(varDecl.getName())) {
						List<INode> nodes = NodeModelUtils.findNodesForFeature(varDecl,
								N4JSPackage.Literals.ABSTRACT_VARIABLE__NAME);
						INode target = !nodes.isEmpty() ? nodes.get(0) : NodeModelUtils.findActualNodeFor(varDecl);
						producer.setNode(target);
						if (target != null) {
							producer.addDiagnostic(
									newDiagnosticMessage(IssueCodes.AST_RESERVED_IDENTIFIER, varDecl.getName()));
						}
					}
				}
			} else if (model.getInitExpr() != null) {
				Expression initExpr = model.getInitExpr();
				if (initExpr instanceof AssignmentExpression) {
					List<INode> nodes = NodeModelUtils.findNodesForFeature(initExpr,
							TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME);
					INode target = !nodes.isEmpty() ? nodes.get(0) : NodeModelUtils.findActualNodeFor(initExpr);
					producer.setNode(target);
					if (target != null) {
						producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_VAR_DECL_IN_FOR_INVALID_INIT));
					}
				} else if (!initExpr.isValidSimpleAssignmentTarget() && !isTopOfDestructuringForStatement(model)) {
					List<INode> nodes = NodeModelUtils.findNodesForFeature(model,
							N4JSPackage.Literals.FOR_STATEMENT__INIT_EXPR);
					INode target = !nodes.isEmpty() ? nodes.get(0) : NodeModelUtils.findActualNodeFor(initExpr);
					producer.setNode(target);
					if (target != null) {
						producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_EXP_INVALID_LHS_ASS));
					}
				}
			}
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowNestedFunctions(!constraints.isStrict() && !constraints.isInFunctionDeclaration())
						.allowBreak(true).allowContinue(true).allowBreakWithoutLabel(true));
	}

	private void _validateASTStructure(
			IterationStatement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowNestedFunctions(!constraints.isStrict() && !constraints.isInFunctionDeclaration())
						.allowBreak(true).allowContinue(true).allowBreakWithoutLabel(true));
	}

	private void _validateASTStructure(
			FormalParameter model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		EObject container = model.eContainer();
		boolean allowYieldInInit = false;
		if (container instanceof FunctionDefinition) {
			allowYieldInInit = !((FunctionDefinition) container).isGenerator();

			internalCheckFormalParameter(
					((FunctionDefinition) container).getFpars(),
					model,
					(fp) -> fp.isVariadic(),
					(fp) -> fp.isHasInitializerAssignment(),
					(String msg, String id, EObject eObj) -> {
						producer.setNode(NodeModelUtils.findActualNodeFor(eObj));
						producer.addDiagnostic(new DiagnosticMessage(msg, IssueCodes.getSeverityForName(id), id));
					});
		}

		_validateASTStructure(
				(AbstractVariable<?>) model,
				producer,
				validLabels,
				constraints.allowYieldExpression(allowYieldInInit));
	}

	private void _validateASTStructure(
			NewTarget model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (EcoreUtil2.getContainerOfType(model, FunctionDefinition.class) == null) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			if (target != null) {
				producer.setNode(target);
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_NEW_TARGET));
			}
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			SuperLiteral model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (isInvalidSuperLiteral(model, constraints)) {

			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			if (target != null) {
				producer.setNode(target);
				if (model.eContainingFeature() == N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET
						&& model.eContainer() instanceof ParameterizedCallExpression) {
					producer.addDiagnostic(newDiagnosticMessage(IssueCodes.KEY_SUP_CTOR_INVALID_LOC));
				} else if (EcoreUtil2.getContainerOfType(model, N4MethodDeclaration.class) == null) {
					producer.addDiagnostic(newDiagnosticMessage(IssueCodes.KEY_SUP_ACCESS_INVALID_LOC));
				} else {
					N4ClassifierDeclaration containingClass = EcoreUtil2.getContainerOfType(model,
							N4ClassifierDeclaration.class);
					if (containingClass instanceof N4InterfaceDeclaration) {
						producer.addDiagnostic(newDiagnosticMessage(IssueCodes.KEY_SUP_ACCESS_INVALID_LOC_INTERFACE));
					} else if (containingClass != null) {
						if (!constraints.isN4JS()) { // implicit super type only available in n4js
							producer.addDiagnostic(newDiagnosticMessage(IssueCodes.KEY_SUP_ACCESS_NO_EXTENDS));
						}
					} else {
						throw new IllegalStateException("a");
					}
				}
			}

		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private boolean isInvalidSuperLiteral(SuperLiteral model, Constraints constraints) {
		if (!isValidContainment(model)) {
			return true;
		}
		if (!constraints.isSuperLiteralAllowed()) {
			return true;
		}
		if (!constraints.isSuperCallAllowed()) {
			return model.eContainer() instanceof ParameterizedCallExpression;
		}
		return false;
	}

	private boolean isValidContainment(SuperLiteral literal) {
		EObject container = literal.eContainer();
		return container instanceof IndexedAccessExpression || container instanceof ParameterizedCallExpression
				|| container instanceof ParameterizedPropertyAccessExpression;
	}

	private void _validateASTStructure(
			FunctionDeclaration model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		validateFunctionDefinition(
				model,
				N4JSPackage.Literals.FUNCTION_DECLARATION__NAME,
				constraints.enterFunctionDeclaration(),
				model.getName(),
				producer,
				validLabels);
	}

	private void _validateASTStructure(
			FunctionExpression model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		String name = model.getName();
		if (name != null) {
			if (LOCAL_ARGUMENTS_VARIABLE_NAME.equals(name)) {
				issueArgumentsError(model, name, constraints.isStrict(), producer);
			} else if (constraints.isStrict()) {
				if (RESERVED_WORDS_IN_STRICT_MODE.contains(name) || EVAL_NAME.equals(name)) {
					issueNameDiagnostic(model, producer, name);
					model.setName(null); // do not pollute scope
				}
			}
		}
		recursiveValidateASTStructure(
				model,
				producer,
				Sets.newHashSetWithExpectedSize(2),
				constraints.allowNestedFunctions(true).allowReturn(true).allowBreak(false).allowContinue(false)
						.allowBreakWithoutLabel(false));
	}

	private void validateFunctionDefinition(
			FunctionDefinition model,
			EAttribute attribute,
			Constraints constraints,
			String name,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels) {
		if (!constraints.isNestedFunctionAllowed()) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(model, attribute);
			INode target = nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(model) : nodes.get(0);
			producer.setNode(target);

			// TODO improve error message
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_STR_FUN_NOT_NESTED));
		} else if (name != null) {
			if (LOCAL_ARGUMENTS_VARIABLE_NAME.equals(name)) {
				issueArgumentsError(model, name, constraints.isStrict(), producer);
			} else if (constraints.isStrict()) {
				if (RESERVED_WORDS_IN_STRICT_MODE.contains(name) || EVAL_NAME.equals(name)) {
					issueNameDiagnostic(model, producer, name);
				}
			}
		}
		recursiveValidateASTStructure(
				model,
				producer,
				Sets.newHashSetWithExpectedSize(2),
				constraints.allowNestedFunctions(true).allowReturn(true).allowContinue(false).allowBreak(false)
						.allowYieldExpression(true).allowBreakWithoutLabel(false));
	}

	private void validateName(PropertyNameOwner model, Constraints constraints,
			ASTStructureDiagnosticProducer producer) {
		String name = model.getName();
		if (name != null) {
			if (!model.isValidName()
					&& !constraints.isBuiltInTypeDefinition()) {
				issueNameDiagnostic(model, producer, name);
			} else {
				if (constraints.isN4JS()) {
					if (RESERVED_WORDS_IN_STRICT_MODE.contains(name)) {
						issueNameDiagnostic(model, producer, name);
					}
				} else if (constraints.isStrict()) {
					if (RESERVED_WORDS_IN_STRICT_MODE.contains(name)) {
						issueNameDiagnostic(model, producer, name, getNameFeature(model), Severity.WARNING);
					}
				}
			}
		}
	}

	private void issueArgumentsError(EObject model, String name, boolean strict,
			ASTStructureDiagnosticProducer producer) {
		issueNameDiagnostic(model, producer, name, getNameFeature(model), (strict) ? Severity.ERROR : Severity.WARNING);
	}

	private void issueNameDiagnostic(EObject model, ASTStructureDiagnosticProducer producer, String name) {
		issueNameDiagnostic(model, producer, name, getNameFeature(model), IssueCodes.AST_RESERVED_IDENTIFIER.severity);
	}

	private void issueNameDiagnostic(EObject model, ASTStructureDiagnosticProducer producer, String name,
			EStructuralFeature feature, Severity severity) {
		List<INode> nodes = NodeModelUtils.findNodesForFeature(model, feature);
		INode target = nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(model) : nodes.get(0);
		producer.setNode(target);
		if (target != null) {
			producer.addDiagnostic(
					new DiagnosticMessage(IssueCodes.AST_RESERVED_IDENTIFIER.getMessage(name),
							severity,
							IssueCodes.AST_RESERVED_IDENTIFIER.name()));
		}
	}

	private EStructuralFeature getNameFeature(EObject model) {
		EStructuralFeature eStructuralFeature = model.eClass().getEStructuralFeature("name");
		return eStructuralFeature == null ? model.eClass().getEStructuralFeature("declaredName") : eStructuralFeature;
	}

	private void _validateASTStructure(
			N4FieldAccessor model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		validateName(model, constraints, producer);
		recursiveValidateASTStructure(
				model,
				producer,
				Sets.newHashSetWithExpectedSize(2),
				constraints.allowReturn(true).allowSuperLiteral(true).allowSuperCall(false));
	}

	private void _validateASTStructure(
			MethodDeclaration model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		validateName(model, constraints, producer);
		recursiveValidateASTStructure(
				model,
				producer,
				Sets.newHashSetWithExpectedSize(2),
				constraints.allowReturn(true).allowSuperLiteral(true)
						.allowSuperCall("constructor".equals(model.getName()) && !model.isStatic()
								&& canCallSuperConstructor(model, constraints)));
	}

	private boolean canCallSuperConstructor(MethodDeclaration declaration, Constraints constraints) {
		EObject container = declaration.eContainer();
		if (container instanceof N4ClassDefinition) {
			if (constraints.isN4JS()) {
				return true;
			}
			N4ClassDefinition cd = (N4ClassDefinition) container;
			return cd.getSuperClassRef() != null || cd.getSuperClassExpression() != null;
		}
		return false;
	}

	private void _validateASTStructure(
			FieldAccessor model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		validateName(model, constraints, producer);
		recursiveValidateASTStructure(
				model,
				producer,
				Sets.newHashSetWithExpectedSize(2),
				constraints.allowReturn(true).allowSuperLiteral(true).allowSuperCall(false));
	}

	private void _validateASTStructure(
			PropertyAssignment model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				Sets.newHashSetWithExpectedSize(2),
				constraints.allowNestedFunctions(false).allowReturn(true).allowContinue(false).allowBreak(false)
						.allowYieldExpression(true).allowBreakWithoutLabel(false));
	}

	private void _validateASTStructure(
			ReturnStatement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (!constraints.isReturnAllowed()) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_RETURN));
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			ContinueStatement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (!constraints.isContinueAllowed()) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_CONTINUE));
		} else {
			validateLabelRef(model, producer, validLabels);
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			BreakStatement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (!constraints.isBreakAllowed()
				|| (!validateLabelRef(model, producer, validLabels) && !constraints.isBreakAllowedWithoutLabel())) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_BREAK));
		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	/*
	 * @returns whether there is a label
	 */
	private boolean validateLabelRef(LabelRef model, ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels) {
		String labelAsText = model.getLabelAsText(); // cannot use model.label, because we aren't allowed to resolve
														// proxies in this phase!
		if (labelAsText != null && !exists(validLabels, it -> Objects.equal(it.getName(), labelAsText))) {
			ICompositeNode target = NodeModelUtils.findActualNodeFor(model);
			producer.setNode(target);
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_LABEL));
		}
		return labelAsText != null;
	}

	private void _validateASTStructure(
			Expression model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			ArrayElement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);

		validateExpressionInArrayOrObjectLiteral(model, constraints, producer);
		validateSpreadInArrayLiteral(model, producer);
	}

	/**
	 * @param elem
	 *            must be an ArrayElement or PropertyNameValuePair.
	 */
	private void validateExpressionInArrayOrObjectLiteral(EObject elem, Constraints constraints,
			ASTStructureDiagnosticProducer producer) {
		if (!(elem instanceof ArrayElement || elem instanceof PropertyNameValuePair
				|| elem instanceof PropertyMethodDeclaration)) {
			throw new IllegalArgumentException();
		}
		if (elem instanceof PropertyNameValuePairSingleName) {
			IdentifierRef identifier = ((PropertyNameValuePairSingleName) elem).getIdentifierRef();
			if (identifier != null && !identifier.isValidSimpleAssignmentTarget()) {
				producer.setNode(NodeModelUtils.findActualNodeFor(identifier));
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_EXPR_IN_LHS_DESTRUCTURING_PATTERN));
			}
			// more validation not required in this case, because expression has a different meaning and problem cannot
			// occur
			return;
		}
		if (elem != null && DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(elem.eContainer())) {
			if (elem instanceof PropertyMethodDeclaration) {
				// methods are not allowed at all in a destructuring pattern
				producer.setNode(NodeModelUtils.findActualNodeFor(elem));
				producer.addDiagnostic(
						newDiagnosticMessage(IssueCodes.AST_INVALID_PROPERTY_METHOD_IN_LHS_DESTRUCTURING_PATTERN));
			} else {
				Expression expr = null;
				if (elem instanceof ArrayElement) {
					expr = ((ArrayElement) elem).getExpression();
				}
				if (elem instanceof PropertyNameValuePair) {
					expr = ((PropertyNameValuePair) elem).getExpression();
				}
				if (expr != null && !isValidBindingElement(expr, constraints)) {
					producer.setNode(NodeModelUtils.findActualNodeFor(expr));
					producer.addDiagnostic(
							newDiagnosticMessage(IssueCodes.AST_INVALID_EXPR_IN_LHS_DESTRUCTURING_PATTERN));
				}
			}
		}
	}

	private boolean isValidBindingElement(Expression expr, Constraints constraints) {
		if (constraints.isN4JS()) {
			return (expr.isValidSimpleAssignmentTarget() && expr instanceof IdentifierRef)
					|| expr instanceof AssignmentExpression
					|| expr instanceof ArrayLiteral
					|| expr instanceof ObjectLiteral;
		} else {
			return expr.isValidSimpleAssignmentTarget()
					|| expr instanceof AssignmentExpression
					|| expr instanceof ArrayLiteral
					|| expr instanceof ObjectLiteral;
		}
	}

	private void validateSpreadInArrayLiteral(ArrayElement elem, ASTStructureDiagnosticProducer producer) {
		if (elem != null && elem.isSpread()) {
			if (!DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(elem.eContainer())) {
				// use of spread in an array literal that is *not* used as a destructuring pattern
				// --> valid at any position
			} else {
				// use of spread in an array literal that *is* used as a destructuring pattern
				// --> error if not at end of array literal
				EObject lit = elem.eContainer();
				if (lit instanceof ArrayLiteral) {
					ArrayLiteral al = (ArrayLiteral) lit;
					if (last(al.getElements()) != elem || al.isTrailingComma()) {
						List<INode> nodes = NodeModelUtils.findNodesForFeature(elem,
								N4JSPackage.eINSTANCE.getArrayElement_Spread());
						producer.setNode(nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(elem) : nodes.get(0));
						producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_REST_MUST_APPEAR_AT_END));
					}

					if (elem.getExpression() instanceof AssignmentExpression) {
						List<INode> nodes = NodeModelUtils.findNodesForFeature(elem.getExpression(),
								N4JSPackage.eINSTANCE.getAssignmentExpression_Rhs());
						producer.setNode(nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(elem.getExpression())
								: nodes.get(0));
						producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_REST_WITH_INITIALIZER));
					}
				}
			}
		}
	}

	private void _validateASTStructure(
			PropertyNameValuePair model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowYieldExpression(true));

		validateExpressionInArrayOrObjectLiteral(model, constraints, producer);
		validateSingleNameInObjectLiteral(model, producer);
	}

	private void _validateASTStructure(
			PropertyMethodDeclaration model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowReturn(true).allowSuperLiteral(true).allowSuperCall(false).allowYieldExpression(true));

		validateExpressionInArrayOrObjectLiteral(model, constraints, producer);
	}

	private void validateSingleNameInObjectLiteral(PropertyNameValuePair elem,
			ASTStructureDiagnosticProducer producer) {
		if (elem instanceof PropertyNameValuePairSingleName
				&& !DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(elem.eContainer())
				&& elem.getExpression() instanceof AssignmentExpression) {

			List<INode> nodes = NodeModelUtils.findNodesForFeature(elem.getExpression(),
					N4JSPackage.Literals.ASSIGNMENT_EXPRESSION__RHS);
			producer.setNode(nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(elem.getExpression()) : nodes.get(0));
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_INVALID_DEFAULT_EXPR_SINGLE_NAME_PROPERTY));
		}
	}

	private void _validateASTStructure(
			VariableStatement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (model.getVarStmtKeyword() == VariableStatementKeyword.CONST) {
			if (!isValidConstOrLetPosition(model)) {
				List<INode> nodes = NodeModelUtils.findNodesForFeature(model,
						N4JSPackage.Literals.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD);
				producer.setNode(nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(model) : nodes.get(0));
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_CONST_IN_STATEMENT_POSITION));
			}
		} else if (model.getVarStmtKeyword() == VariableStatementKeyword.LET) {
			if (!isValidConstOrLetPosition(model)) {
				List<INode> nodes = NodeModelUtils.findNodesForFeature(model,
						N4JSPackage.Literals.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD);
				producer.setNode(nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(model) : nodes.get(0));
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_LET_IN_STATEMENT_POSITION));
			}
		}
		if (model.getVarDeclsOrBindings().isEmpty()) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(model,
					N4JSPackage.Literals.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD);
			producer.setNode(nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(model) : nodes.get(0));
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_VAR_STMT_NO_DECL));
		}
		EObject directParent = model.eContainer();
		EObject parent = (directParent instanceof ExportDeclaration) ? directParent.eContainer() : directParent;
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints.allowVarWithoutInitializer(model.getVarStmtKeyword() == VariableStatementKeyword.VAR
						|| model.getVarStmtKeyword() == VariableStatementKeyword.LET &&
								(parent instanceof Block || parent instanceof Script
										|| parent instanceof AbstractCaseClause)));
	}

	private boolean isValidConstOrLetPosition(EObject model) {
		if (model.eContainer() instanceof Block
				|| model.eContainer() instanceof Script
				|| model.eContainer() instanceof AbstractCaseClause
				|| model.eContainer() instanceof N4NamespaceDeclaration) {
			return true;
		}
		if (model.eContainer() instanceof ExportDeclaration) {
			return isValidConstOrLetPosition(model.eContainer());
		}
		return false;
	}

	private void _validateASTStructure(
			VariableDeclaration model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (model.getExpression() == null && constraints.isVarInitializerRequired() && !constraints.isExternal()) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(model, N4JSPackage.Literals.ABSTRACT_VARIABLE__NAME);
			producer.setNode(nodes.isEmpty() ? NodeModelUtils.findActualNodeFor(model) : nodes.get(0));
			producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_CONST_HAS_NO_INITIALIZER, model.getName()));
		}
		_validateASTStructure((AbstractVariable<?>) model, producer, validLabels,
				constraints.allowVarWithoutInitializer(true));
	}

	private void _validateASTStructure(
			ThisTypeRef thisTypeRef,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		// note: validity of location of ThisTypeRef was checked here; now moved to N4JSTypeValidator#checkThisTypeRef()

		recursiveValidateASTStructure(
				thisTypeRef,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			VariableBinding model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		// no need to assert model.expression!=null (it is enforced by the grammar)

		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				// initializers of variable declarations below a VariableBinding are always optional, even if we are in
				// the
				// context of a const declaration, e.g. the following should not show an error about missing
				// initializer:
				// const [c1, c2] = [10, 20];
				// Requiring an initializer of variable declarations below VariableBindings would render the above code
				// invalid and require something like:
				// const [c1=1, c2=2] = [10, 20];
				constraints.allowVarWithoutInitializer(true));
	}

	private void _validateASTStructure(
			BindingElement model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		validateRestInBindingPattern(model, producer);

		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			Annotation model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		// Add an issue for all script annotation (indicated by '@@') that do
		// not appear at the very top of the module.
		if (model.eContainer() instanceof Script) {
			Script script = (Script) model.eContainer();
			if (script.getScriptElements().size() > 0) {
				ICompositeNode annotationNode = NodeModelUtils.findActualNodeFor(model);
				int annotationOffset = annotationNode.getOffset();
				ScriptElement firstScriptElement = script.getScriptElements().get(0);
				int scriptElementOffset = NodeModelUtils.findActualNodeFor(firstScriptElement).getOffset();

				if (annotationOffset > scriptElementOffset) {
					producer.setNode(annotationNode);
					producer.addDiagnostic(
							newDiagnosticMessage(IssueCodes.AST_SCRIPT_ANNO_INVALID_PLACEMENT, model.getName()));
				}
			}
		}
	}

	private void validateRestInBindingPattern(BindingElement elem, ASTStructureDiagnosticProducer producer) {
		if (elem != null && elem.isRest()) {
			EObject pattern = elem.eContainer();
			if (pattern instanceof ArrayBindingPattern) {
				// note: the grammar ensures that BindingElement with rest==true will only appear
				// within an array binding pattern; we only have to assert that it appears at the end
				if (last(((ArrayBindingPattern) pattern).getElements()) != elem) {
					List<INode> nodes = NodeModelUtils.findNodesForFeature(elem,
							N4JSPackage.eINSTANCE.getBindingElement_Rest());
					producer.setNode(!nodes.isEmpty() ? nodes.get(0) : NodeModelUtils.findActualNodeFor(elem));
					producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_REST_MUST_APPEAR_AT_END));
				}
			}
			if (elem.getExpression() != null) {
				List<INode> nodes = NodeModelUtils.findNodesForFeature(elem,
						N4JSPackage.eINSTANCE.getBindingElement_Expression());
				producer.setNode(!nodes.isEmpty() ? nodes.get(0) : NodeModelUtils.findActualNodeFor(elem));
				producer.addDiagnostic(newDiagnosticMessage(IssueCodes.AST_REST_WITH_INITIALIZER));
			}
		}
	}

	private void _validateASTStructure(
			BinaryLogicalExpression model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		if (model.getLhs() == null) {
			producer.setNode(NodeModelUtils.findActualNodeFor(model));
			producer.addDiagnostic(
					newDiagnosticMessage(IssueCodes.AST_BINARY_LOGICAL_EXPRESSION_MISSING_PART, "left operand"));

		}
		if (model.getRhs() == null) {
			producer.setNode(NodeModelUtils.findActualNodeFor(model));
			producer.addDiagnostic(
					newDiagnosticMessage(IssueCodes.AST_BINARY_LOGICAL_EXPRESSION_MISSING_PART, "right operand"));

		}
		if (model.getOp() == null) {
			producer.setNode(NodeModelUtils.findActualNodeFor(model));
			producer.addDiagnostic(
					newDiagnosticMessage(IssueCodes.AST_BINARY_LOGICAL_EXPRESSION_MISSING_PART, "operator"));

		}
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			N4TypeVariable model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private void _validateASTStructure(
			TypeVariable model,
			ASTStructureDiagnosticProducer producer,
			Set<LabelledStatement> validLabels,
			Constraints constraints) {
		recursiveValidateASTStructure(
				model,
				producer,
				validLabels,
				constraints);
	}

	private DiagnosticMessage newDiagnosticMessage(IssueCodes issueCode, Object... msgValues) {
		return new DiagnosticMessage(issueCode.getMessage(msgValues), issueCode.severity, issueCode.name());
	}
}
