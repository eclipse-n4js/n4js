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
package org.eclipse.n4js.formatting2;

import static org.eclipse.n4js.formatting2.N4JSFormatterPreferenceKeys.FORMAT_AUTO_WRAP_IN_FRONT_OF_LOGICAL_OPERATOR;
import static org.eclipse.n4js.formatting2.N4JSFormatterPreferenceKeys.FORMAT_MAX_CONSECUTIVE_NEWLINES;
import static org.eclipse.n4js.formatting2.N4JSFormatterPreferenceKeys.FORMAT_PARENTHESIS;
import static org.eclipse.n4js.formatting2.N4JSFormatterPreferenceKeys.FORMAT_SURROUND_IMPORT_LIST_WITH_SPACE;
import static org.eclipse.n4js.formatting2.N4JSGenericFormatter.PRIO_3;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.tail;

import java.util.function.Function;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.formatting2.N4JSGenericFormatter.IndentHandlingTextReplaceMerger;
import org.eclipse.n4js.n4JS.AbstractAnnotationList;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.AnnotableExpression;
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration;
import org.eclipse.n4js.n4JS.AnnotablePropertyAssignment;
import org.eclipse.n4js.n4JS.AnnotableScriptElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StaticBaseTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting2.IAutowrapFormatter;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatting;
import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.internal.SinglelineCodeCommentReplacer;
import org.eclipse.xtext.formatting2.internal.SinglelineDocCommentReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;
import org.eclipse.xtext.formatting2.regionaccess.IEObjectRegion;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion;
import org.eclipse.xtext.formatting2.regionaccess.ILineRegion;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;
import org.eclipse.xtext.formatting2.regionaccess.ISequentialRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xtext.generator.parser.antlr.splitting.simpleExpressions.NumberLiteral;

import com.google.inject.Inject;

/***/
@SuppressWarnings("restriction")
public class N4JSFormatter extends TypeExpressionsFormatter {
	private final static Logger LOGGER = Logger.getLogger(TypeExpressionsFormatter.class);

	/** Debug switch */
	private static boolean debug = false;

	@Inject
	N4JSGrammarAccess grammarAccess;

	/**
	 * PRIO_4 = -7 - still very low. Standard priorities in the formatter are:
	 * <ul>
	 * <li>lowPriority = -1; == PRIO_10
	 * <li>normal priority = 0; == PRIO_11
	 * <li>high priority = +1; == PRIO_12
	 * </ul>
	 */
	static int PRIO_4 = PRIO_3 + 1;
	static int PRIO_13 = IHiddenRegionFormatter.HIGH_PRIORITY + 1;

	@Override
	public void format(Object clazz, IFormattableDocument document) {
		if (clazz instanceof N4ClassDeclaration) {
			_format((N4ClassDeclaration) clazz, document);
			return;
		} else if (clazz instanceof N4InterfaceDeclaration) {
			_format((N4InterfaceDeclaration) clazz, document);
			return;
		} else if (clazz instanceof ArrowFunction) {
			_format((ArrowFunction) clazz, document);
			return;
		} else if (clazz instanceof N4EnumDeclaration) {
			_format((N4EnumDeclaration) clazz, document);
			return;
		} else if (clazz instanceof TemplateSegment) {
			_format((TemplateSegment) clazz, document);
			return;
		} else if (clazz instanceof IntersectionTypeExpression) {
			_format((IntersectionTypeExpression) clazz, document);
			return;
		} else if (clazz instanceof ParameterizedTypeRef) {
			_format((ParameterizedTypeRef) clazz, document);
			return;
		} else if (clazz instanceof ThisTypeRef) {
			_format((ThisTypeRef) clazz, document);
			return;
		} else if (clazz instanceof UnionTypeExpression) {
			_format((UnionTypeExpression) clazz, document);
			return;
		} else if (clazz instanceof TStructMember) {
			_format((TStructMember) clazz, document);
			return;
		} else if (clazz instanceof ArrayLiteral) {
			_format((ArrayLiteral) clazz, document);
			return;
		} else if (clazz instanceof ForStatement) {
			_format((ForStatement) clazz, document);
			return;
		} else if (clazz instanceof FunctionExpression) {
			_format((FunctionExpression) clazz, document);
			return;
		} else if (clazz instanceof IndexedAccessExpression) {
			_format((IndexedAccessExpression) clazz, document);
			return;
		} else if (clazz instanceof N4FieldDeclaration) {
			_format((N4FieldDeclaration) clazz, document);
			return;
		} else if (clazz instanceof ObjectLiteral) {
			_format((ObjectLiteral) clazz, document);
			return;
		} else if (clazz instanceof ParameterizedCallExpression) {
			_format((ParameterizedCallExpression) clazz, document);
			return;
		} else if (clazz instanceof ParameterizedPropertyAccessExpression) {
			_format((ParameterizedPropertyAccessExpression) clazz, document);
			return;
		} else if (clazz instanceof ParenExpression) {
			_format((ParenExpression) clazz, document);
			return;
		} else if (clazz instanceof TaggedTemplateString) {
			_format((TaggedTemplateString) clazz, document);
			return;
		} else if (clazz instanceof TemplateLiteral) {
			_format((TemplateLiteral) clazz, document);
			return;
		} else if (clazz instanceof VariableDeclaration) {
			_format((VariableDeclaration) clazz, document);
			return;
		} else if (clazz instanceof VariableStatement) {
			_format((VariableStatement) clazz, document);
			return;
		} else if (clazz instanceof AdditiveExpression) {
			_format((AdditiveExpression) clazz, document);
			return;
		} else if (clazz instanceof AssignmentExpression) {
			_format((AssignmentExpression) clazz, document);
			return;
		} else if (clazz instanceof AwaitExpression) {
			_format((AwaitExpression) clazz, document);
			return;
		} else if (clazz instanceof BinaryBitwiseExpression) {
			_format((BinaryBitwiseExpression) clazz, document);
			return;
		} else if (clazz instanceof BinaryLogicalExpression) {
			_format((BinaryLogicalExpression) clazz, document);
			return;
		} else if (clazz instanceof Block) {
			_format((Block) clazz, document);
			return;
		} else if (clazz instanceof CastExpression) {
			_format((CastExpression) clazz, document);
			return;
		} else if (clazz instanceof CommaExpression) {
			_format((CommaExpression) clazz, document);
			return;
		} else if (clazz instanceof ConditionalExpression) {
			_format((ConditionalExpression) clazz, document);
			return;
		} else if (clazz instanceof EqualityExpression) {
			_format((EqualityExpression) clazz, document);
			return;
		} else if (clazz instanceof ExportDeclaration) {
			_format((ExportDeclaration) clazz, document);
			return;
		} else if (clazz instanceof ExpressionStatement) {
			_format((ExpressionStatement) clazz, document);
			return;
		} else if (clazz instanceof IfStatement) {
			_format((IfStatement) clazz, document);
			return;
		} else if (clazz instanceof ImportDeclaration) {
			_format((ImportDeclaration) clazz, document);
			return;
		} else if (clazz instanceof MultiplicativeExpression) {
			_format((MultiplicativeExpression) clazz, document);
			return;
		} else if (clazz instanceof NamespaceImportSpecifier) {
			_format((NamespaceImportSpecifier) clazz, document);
			return;
		} else if (clazz instanceof NewExpression) {
			_format((NewExpression) clazz, document);
			return;
		} else if (clazz instanceof PostfixExpression) {
			_format((PostfixExpression) clazz, document);
			return;
		} else if (clazz instanceof PromisifyExpression) {
			_format((PromisifyExpression) clazz, document);
			return;
		} else if (clazz instanceof RelationalExpression) {
			_format((RelationalExpression) clazz, document);
			return;
		} else if (clazz instanceof ReturnStatement) {
			_format((ReturnStatement) clazz, document);
			return;
		} else if (clazz instanceof ShiftExpression) {
			_format((ShiftExpression) clazz, document);
			return;
		} else if (clazz instanceof SwitchStatement) {
			_format((SwitchStatement) clazz, document);
			return;
		} else if (clazz instanceof ThrowStatement) {
			_format((ThrowStatement) clazz, document);
			return;
		} else if (clazz instanceof UnaryExpression) {
			_format((UnaryExpression) clazz, document);
			return;
		} else if (clazz instanceof VariableBinding) {
			_format((VariableBinding) clazz, document);
			return;
		} else if (clazz instanceof YieldExpression) {
			_format((YieldExpression) clazz, document);
			return;
		} else if (clazz instanceof XtextResource) {
			_format((XtextResource) clazz, document);
			return;
		} else if (clazz instanceof AbstractCaseClause) {
			_format((AbstractCaseClause) clazz, document);
			return;
		} else if (clazz instanceof BindingPattern) {
			_format((BindingPattern) clazz, document);
			return;
		} else if (clazz instanceof CatchBlock) {
			_format((CatchBlock) clazz, document);
			return;
		} else if (clazz instanceof Expression) {
			_format((Expression) clazz, document);
			return;
		} else if (clazz instanceof FinallyBlock) {
			_format((FinallyBlock) clazz, document);
			return;
		} else if (clazz instanceof FunctionOrFieldAccessor) {
			_format((FunctionOrFieldAccessor) clazz, document);
			return;
		} else if (clazz instanceof N4TypeVariable) {
			_format((N4TypeVariable) clazz, document);
			return;
		} else if (clazz instanceof NamedImportSpecifier) {
			_format((NamedImportSpecifier) clazz, document);
			return;
		} else if (clazz instanceof Script) {
			_format((Script) clazz, document);
			return;
		} else if (clazz instanceof EObject) {
			_format((EObject) clazz, document);
			return;
		} else if (clazz == null) {
			_format((Void) null, document);
			return;
		} else {
			_format(clazz, document);
			return;
		}
	}

	private void configureAnnotations(final Object semEObject, final IFormattableDocument document) {
		if (semEObject instanceof AnnotablePropertyAssignment) {
			_configureAnnotations((AnnotablePropertyAssignment) semEObject, document);
			return;
		} else if (semEObject instanceof AnnotableExpression) {
			_configureAnnotations((AnnotableExpression) semEObject, document);
			return;
		} else if (semEObject instanceof AnnotableN4MemberDeclaration) {
			_configureAnnotations((AnnotableN4MemberDeclaration) semEObject, document);
			return;
		} else if (semEObject instanceof AnnotableScriptElement) {
			_configureAnnotations((AnnotableScriptElement) semEObject, document);
			return;
		} else if (semEObject instanceof AbstractAnnotationList) {
			_configureAnnotations((AbstractAnnotationList) semEObject, document);
			return;
		} else if (semEObject == null) {
			_configureAnnotations((Void) null, document);
			return;
		} else {
			_configureAnnotations(semEObject, document);
			return;
		}
	}

	private Integer maxConsecutiveNewLines() {
		// let's stick to 2
		return getPreference(FORMAT_MAX_CONSECUTIVE_NEWLINES);
	}

	void _format(Script script, IFormattableDocument document) {
		N4JSGenericFormatter generic = new N4JSGenericFormatter(grammarAccess, textRegionExtensions);
		if (getPreference(FORMAT_PARENTHESIS)) {
			// script.formatParenthesisBracketsAndBraces(document)
		}

		// TODO the following line requires more conflict handling with semicolons:
		// script.interior[noIndentation;];

		generic.formatSemicolons(script, document);
		generic.formatColon(script, document);

		formatScriptAnnotations(script, document);

		for (ScriptElement element : script.getScriptElements()) {
			// element.append[setNewLines(1, 1, maxConsecutiveNewLines);hrf.noSpace(); autowrap].prepend,
			// hrf->hrf.noSpace();
			document.append(element, hrf -> {
				hrf.setNewLines(1, 1, maxConsecutiveNewLines());
				hrf.autowrap();
			});

			document.format(element);
		}

		// format last import, overrides default newLines:
		document.append(last(filter(script.getScriptElements(), ImportDeclaration.class)), hfr -> {
			hfr.setNewLines(2, 2, 3);
			hfr.highPriority();
		});
	}

	/** put modifiers into a single line separated by one space, last modifier has one space to following element. */
	void configureModifiers(EObject semObject, IFormattableDocument document) {
		for (ISemanticRegion sr : textRegionExtensions.regionFor(semObject)
				.ruleCallsTo(grammarAccess.getN4ModifierRule())) {

			document.append(sr, hrf -> hrf.oneSpace());
		}
	}

	void configureTypingStrategy(EObject semObject, IFormattableDocument document) {
		for (ISemanticRegion sr : textRegionExtensions.regionFor(semObject)
				.ruleCallsTo(grammarAccess.getTypingStrategyDefSiteOperatorRule(),
						grammarAccess.getTypingStrategyUseSiteOperatorRule())) {

			document.append(sr, hrf -> hrf.noSpace());
		}
	}

	void formatTypeVariables(GenericDeclaration semObject, IFormattableDocument document) {
		if (semObject.getTypeVars().isEmpty()) {
			return;
		}
		// to "<":
		ISemanticRegion sr = document.prepend(textRegionExtensions.regionFor(semObject).keyword("<"), hrf -> {
			hrf.oneSpace();
			hrf.setNewLines(0);
		});
		document.append(sr, hrf -> hrf.noSpace());

		document.prepend(textRegionExtensions.regionFor(semObject).keyword(">"), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(0);
		});

		for (N4TypeVariable typeVar : semObject.getTypeVars()) {
			N4TypeVariable sr2 = document.append(typeVar, hrf -> hrf.noSpace());
			document.append(textRegionExtensions.immediatelyFollowing(sr2).keyword(","), hrf -> hrf.oneSpace());
			format(typeVar, document);
		}
	}

	void _format(N4ClassDeclaration clazz, IFormattableDocument document) {

		configureAnnotations(clazz, document);
		insertSpaceInFrontOfCurlyBlockOpener(clazz, document);
		indentExcludingAnnotations(clazz, document);

		configureTypingStrategy(clazz, document);
		configureModifiers(clazz, document);

		formatTypeVariables(clazz, document);

		// val semRegModifier = textRegionExtensions.regionFor(clazz).feature(
		// N4JSPackage.Literals.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS);
		// if( semRegModifier != null ) { // only if exists.
		// val beginModifierHR = semRegModifier.previousHiddenRegion;
		// val endModifierHR = semRegModifier.nextHiddenRegion;
		// // go over all semantic regions in the modifier location.
		// var currHR = beginModifierHR.nextHiddenRegion;
		// while( currHR != endModifierHR ){
		// currHR.set, hrf->hrf.oneSpace();
		// currHR = currHR.nextHiddenRegion;
		// }
		// endModifierHR.set, hrf->hrf.oneSpace();
		// } // end modifier formatting TODO extract into method.

		// TODO revise the following pattern of call-back implementations.
		// Define lambda for callback & normal use:
		Function<Integer, N4MemberDeclaration> twolinesBeforeFirstMember = (prio) -> {
			return document.prepend(clazz.getOwnedMembersRaw().get(0), hrf -> {
				hrf.setNewLines(2);
				hrf.setPriority(prio);
			});
		};

		// Defines CallBack for autoWrap:
		IAutowrapFormatter callBackOnAutoWrap = new IAutowrapFormatter() { // callback for auto-wrapping with implements
			boolean didReconfigure = false; // track to only execute once.

			@SuppressWarnings("hiding")
			@Override
			public void format(ITextSegment region, IHiddenRegionFormatting wrapped, IFormattableDocument document) {
				if (!didReconfigure) {
					twolinesBeforeFirstMember.apply(IHiddenRegionFormatter.HIGH_PRIORITY); // reformat with higher
																							// priority
					didReconfigure = true; // keep state.
				}
			}
		};

		// 2nd version of implementing the callback:
		StateTrack state2 = new StateTrack();
		IAutowrapFormatter callBackOnAutoWrap2 = (region, hrFormatting, document2) -> {
			if (state2.shouldDoThenDone())
				twolinesBeforeFirstMember.apply(IHiddenRegionFormatter.HIGH_PRIORITY);
		};

		suppressUnusedWarnings(callBackOnAutoWrap2);

		// Allow for lineBreaks in front of keywords:
		document.append(document.prepend(textRegionExtensions.regionFor(clazz).keyword("extends"), hrf -> {
			hrf.setNewLines(0, 0, 1); // allow line break in front.
			hrf.autowrap();
		}), hrf -> {
			hrf.oneSpace();
			hrf.autowrap();
		});

		document.append(document.prepend(textRegionExtensions.regionFor(clazz).keyword("implements"), hrf -> {
			hrf.setNewLines(0, 0, 1);
			hrf.autowrap();
			hrf.setPriority(IHiddenRegionFormatter.LOW_PRIORITY);
			hrf.setOnAutowrap(callBackOnAutoWrap);
		}), hrf -> {
			hrf.oneSpace();
			hrf.autowrap();
		});

		for (TypeReferenceNode<ParameterizedTypeRef> trn : tail(clazz.getImplementedInterfaceRefs())) {
			document.prepend(trn, hrf -> {
				hrf.autowrap();
				hrf.setPriority(IHiddenRegionFormatter.LOW_PRIORITY);
				hrf.setOnAutowrap(callBackOnAutoWrap);
			});
		}

		// special case if the header of the class spans multiple lines, then insert extra line break.

		ISemanticRegion kwClass = textRegionExtensions.regionFor(clazz).keyword("class");
		ISemanticRegion kwBrace = textRegionExtensions.regionFor(clazz).keyword("{"); // autowrap-listener ?
		if (!kwClass.getLineRegions().get(0).contains(kwBrace)) {
			twolinesBeforeFirstMember.apply(IHiddenRegionFormatter.NORMAL_PRIORITY);
		} else {
			document.prepend(clazz.getOwnedMembersRaw().get(0), hrf -> {
				hrf.setNewLines(1, 1, maxConsecutiveNewLines());
				hrf.autowrap();
			});
		}

		document.append(kwClass, hrf -> hrf.oneSpace());

		for (N4MemberDeclaration member : clazz.getOwnedMembersRaw()) {
			document.append(member, hrf -> hrf.setNewLines(1, 1, maxConsecutiveNewLines()));
			document.format(member);
		}

		// Collapse empty block:
		if (clazz.getOwnedMembersRaw().isEmpty()) {
			// Empty body:
			document.append(kwBrace, hrf -> {
				hrf.noSpace();
				hrf.setNewLines(0);
			});
		}
	}

	void _format(N4InterfaceDeclaration interf, IFormattableDocument document) {
		configureAnnotations(interf, document);
		configureModifiers(interf, document);
		insertSpaceInFrontOfCurlyBlockOpener(interf, document);
		indentExcludingAnnotations(interf, document);// .interiorBUGFIX(, hrf->hrf.indent(),document);
														// //interf.interior,
														// hrf->hrf.indent();

		document.prepend(interf.getOwnedMembersRaw().get(0), hrf -> hrf.setNewLines(1, 1, maxConsecutiveNewLines()));
		for (N4MemberDeclaration member : interf.getOwnedMembersRaw()) {
			document.append(member, hrf -> hrf.setNewLines(1, 1, maxConsecutiveNewLines()));
			document.format(member);
		}
	}

	// void _format(N4MemberDeclaration member, IFormattableDocument document) {
	// textRegionExtensions.regionFor(member).keyword("(").prepend[hrf.noSpace(); hrf.setNewLines(1);].append,
	// hrf->hrf.noSpace()
	//
	// member.insertSpaceInfrontOfPropertyNames(document);
	// for (c : member.eContents) {
	// document.format(// c);
	// }
	// }

	void _format(N4FieldDeclaration field, IFormattableDocument document) {
		configureAnnotations(field, document);
		configureModifiers(field, document);

		indentExcludingAnnotations(field, document);

		configureOptionality(field, document);
		document.append(document.prepend(textRegionExtensions.regionFor(field).keyword("="), hrf -> hrf.oneSpace()),
				hrf -> hrf.oneSpace());
		document.format(field.getExpression());
		document.format(field.getDeclaredTypeRefInAST());
	}

	// format(N4MethodDeclaration method, IFormattableDocument document) {
	// configureAnnotations(method, document);
	// method.insertSpaceInfrontOfPropertyNames(document);
	//
	// textRegionExtensions.regionFor(method).keyword("(").prepend[hrf.noSpace(); hrf.setNewLines(1);]
	//
	// method.textRegionExtensions.regionFor(body).keyword("{").prepend[hrf.oneSpace(); newLines = 0]
	// for (child : method.eContents) {
	// document.format(// child);
	// }
	// }
	//
	void _format(FunctionExpression funE, IFormattableDocument document) {
		configureAnnotations(funE, document);
		configureModifiers(funE, document);

		if (funE.isArrowFunction()) {
			throw new IllegalStateException("Arrow functions should be formated differently.");
		}

		configureFormalParameters(funE.getFpars(), document,
				(ITextSegment $0, IHiddenRegionFormatting $1, IFormattableDocument $2) -> {
					/* n.t.d. */});

		Pair<ISemanticRegion, ISemanticRegion> parenPair = textRegionExtensions.regionFor(funE).keywordPairs("(", ")")
				.get(0);

		document.append(parenPair.getKey(), hrf -> hrf.noSpace());
		document.prepend(parenPair.getValue(), hrf -> hrf.noSpace());
		document.format(funE.getBody());
	}

	void _format(FunctionOrFieldAccessor fDecl, IFormattableDocument document) {
		configureAnnotations(fDecl, document);
		configureModifiers(fDecl, document);

		// State-keeper to avoid clashing reconfigurations if multiple auto-wraps get triggered.
		final StateTrack state = new StateTrack(); // use state to only trigger one change, even if called multiple
													// times.

		// Callback to introduce an additional line in body-block.
		IAutowrapFormatter cbInsertEmptyLineInBody = (
				ITextSegment ts, IHiddenRegionFormatting hrf, IFormattableDocument fd) -> {

			if (state.shouldDoThenDone() && fDecl.getBody() != null && fDecl.getBody().getStatements() != null
					&& !fDecl.getBody().getStatements().isEmpty()) {
				document.prepend(fDecl.getBody().getStatements().get(0), hrf2 -> {
					hrf2.setNewLines(2, 2, maxConsecutiveNewLines());
					hrf2.highPriority();
				});
			}
		};

		// Formal parameters
		if (fDecl instanceof FunctionDefinition) {
			configureFormalParameters(((FunctionDefinition) fDecl).getFpars(), document, cbInsertEmptyLineInBody);
		} else if (fDecl instanceof N4SetterDeclaration) {
			/* no autowrap for setters: cbInsertEmptyLineInBody */
			document.append(document.prepend(((N4SetterDeclaration) fDecl).getFpar(), hrf -> hrf.noSpace()),
					hrf -> hrf.noSpace());
		}

		// Type Variables
		if (fDecl instanceof FunctionDeclaration) {
			formatTypeVariables((FunctionDeclaration) fDecl, document);
		}

		// special case for accessors: get / set keywords
		if (fDecl instanceof FieldAccessor) {
			configureGetSetKeyword((FieldAccessor) fDecl, document);
			configureOptionality((FieldAccessor) fDecl, document);
		}

		Pair<ISemanticRegion, ISemanticRegion> parenPair = textRegionExtensions.regionFor(fDecl).keywordPairs("(",
				")").get(0);

		document.append(document.prepend(parenPair.getKey(), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(0);
		}), hrf -> hrf.noSpace());

		document.interior(parenPair, hrf -> hrf.indent());

		if (isMultiLine(parenPair) && !(fDecl instanceof FieldAccessor)) {
			// it is already a multiline, insert the newLine immediately.
			// cbInsertEmptyLineInBody.apply(null,null,null); // TODO re-think, if all will be collapsed this assumption
			// does not hold an
		} else {
			// single line parameter block
		}
		document.prepend(parenPair.getValue(), hrf -> hrf.noSpace());

		for (EObject child : fDecl.eContents()) {
			document.format(child);
		}
	}

	/** to be used by FunctionDefintiions and SetterDeclarations */
	void configureFormalParameters(EList<FormalParameter> list, IFormattableDocument document, IAutowrapFormatter x) {
		if (list == null || list.isEmpty()) {
			return;
		}
		for (int idx = 0; idx < list.size(); idx++) {
			FormalParameter it = list.get(idx);
			if (idx != 0) {
				document.prepend(it, hrf -> {
					hrf.oneSpace();
					hrf.setNewLines(0, 0, 1);
					hrf.setOnAutowrap(x);
				});
			}
			document.append(it, hrf -> hrf.noSpace());
			configureAnnotationsInLine(it, document); // TODO maybe we need some in-line-annotation config here.
			// textRegionExtensions.regionFor(it).ruleCallTo( bindingIdentifierAsFormalParameterRule ) //
			// feature(N4JSPackage.Literals.FORMAL_PARAMETER__NAME)
			// .prepend[hrf.oneSpace();hrf.setNewLines(1);].append[]
			format(it.getDeclaredTypeRefInAST(), document);
			if (it.isVariadic()) {
				document.append(document.prepend(textRegionExtensions.regionFor(it).keyword("..."),
						hrf -> hrf.setNewLines(0)/* hrf.oneSpace(); */), hrf -> {
							hrf.setNewLines(0);
							hrf.noSpace();
						});
			}
			if (it.isHasInitializerAssignment()) {
				document.append(document.prepend(textRegionExtensions.regionFor(it).keyword("="), hrf -> {
					hrf.setNewLines(0);
					hrf.noSpace();
				}), hrf -> {
					hrf.setNewLines(0);
					hrf.noSpace();
				});
				if (it.getInitializer() != null) {
					format(it.getInitializer(), document);
				}
			}
		}
	}

	/** Check if key and value are in different lines. Defined for non-overlapping Regions, e.g. Keyword-pairs. */
	static boolean isMultiLine(Pair<ISemanticRegion, ISemanticRegion> pair) {
		return !last(pair.getKey().getLineRegions()).contains(pair.getValue());
	}

	// format(FunctionOrFieldAccessor fofAccessor, IFormattableDocument document) {
	// val begin = fofAccessor.body.semanticRegions.head
	// val end = fofAccessor.body.semanticRegions.last
	// if (begin?.lineRegions?.head?.contains(end?.endOffset)) {
	// // same line
	// } else {
	// // body spans multiple lines
	// begin.append[newLine;];
	// end.prepend[newLine;];
	// // fofAccessor.body.interior, hrf->hrf.indent(); // already by parenthesis?
	// }
	//
	// document.format(// fofAccessor.body?);
	//
	// }

	void _format(N4EnumDeclaration enumDecl, IFormattableDocument document) {
		configureAnnotations(enumDecl, document);
		configureModifiers(enumDecl, document);
		insertSpaceInFrontOfCurlyBlockOpener(enumDecl, document);
		indentExcludingAnnotations(enumDecl, document);// .interiorBUGFIX(, hrf->hrf.indent(),document);
														// //enumDecl.interior, hrf->hrf.indent();
		configureCommas(enumDecl, document);

		Pair<ISemanticRegion, ISemanticRegion> braces = textRegionExtensions.regionFor(enumDecl).keywordPairs("{", "}")
				.get(0);

		boolean multiLine = textRegionExtensions.isMultiline(enumDecl);

		for (N4EnumLiteral it : enumDecl.getLiterals()) {
			format(it, document);
			if (multiLine) {
				if (textRegionExtensions.regionForEObject(it).getPreviousHiddenRegion().containsComment()) {
					// comment above
					document.prepend(it, hrf -> hrf.setNewLines(2));
				} else { // no comment above
					document.prepend(it, hrf -> hrf.newLine());
				}
			}
		}
		if (multiLine) {
			document.prepend(braces.getValue(), hrf -> hrf.newLine());
		}
	}

	void _format(ParameterizedPropertyAccessExpression exp, IFormattableDocument document) {
		ISemanticRegion dotKW = textRegionExtensions.regionFor(exp).keyword(".");
		document.append(document.prepend(dotKW, hrf -> {
			hrf.noSpace();
			hrf.autowrap();
			hrf.setNewLines(0, 0, 1);
		}), hrf -> hrf.noSpace());
		if (exp.eContainer() instanceof ExpressionStatement) {
			// top-level PPA, indent one level.
			interiorBUGFIX(exp, hrf -> hrf.indent(), document); // exp.interior, hrf->hrf.indent();
		}
		document.format(exp.getTarget());
	}

	void _format(ParameterizedCallExpression exp, IFormattableDocument document) {
		// FIXME process typeArgs !!!
		ISemanticRegion dotKW = textRegionExtensions.regionFor(exp).keyword(".");
		document.append(document.prepend(dotKW, hrf -> {
			hrf.noSpace();
			hrf.autowrap();
		}), hrf -> hrf.noSpace());
		document.append(document.prepend(textRegionExtensions.regionFor(exp).keyword("("), hrf -> hrf.noSpace()),
				hrf -> hrf.noSpace());
		document.prepend(textRegionExtensions.regionFor(exp).keyword(")"), hrf -> hrf.noSpace());
		configureCommas(exp, document);

		for (Argument arg : tail(exp.getArguments())) {
			document.prepend(arg, hrf -> {
				hrf.oneSpace();
				hrf.autowrap();
			});
		}

		for (Argument arg : exp.getArguments()) {
			format(arg, document);
		}

		if (exp.eContainer() instanceof ExpressionStatement) {
			// top-level PPA, indent one level.
			interiorBUGFIX(exp, hrf -> hrf.indent(), document); // exp.interior, hrf->hrf.indent();
		}
		document.format(exp.getTarget());
	}

	void _format(ImportDeclaration decl, IFormattableDocument document) {
		// read configuration:
		boolean extraSpace = getPreference(FORMAT_SURROUND_IMPORT_LIST_WITH_SPACE);

		document.append(document.prepend(textRegionExtensions.regionFor(decl).keyword("{"), hrf -> hrf.noSpace()),
				hrf -> {
					if (extraSpace)
						hrf.oneSpace();
					else
						hrf.noSpace();
				});
		document.append(document.prepend(textRegionExtensions.regionFor(decl).keyword("}"), hrf -> {
			if (extraSpace)
				hrf.oneSpace();
			else
				hrf.noSpace();
		}), hrf -> {
			hrf.oneSpace();
			hrf.setNewLines(0);
		});
		document.surround(textRegionExtensions.regionFor(decl).keyword("from"), hrf -> hrf.oneSpace());
		configureCommas(decl, document);
		for (EObject eobj : decl.eContents()) {
			format(eobj, document);
		}
	}

	void _format(NamedImportSpecifier namedImp, IFormattableDocument document) {
		document.append(document.prepend(textRegionExtensions.regionFor(namedImp).keyword("as"), hrf -> hrf.oneSpace()),
				hrf -> hrf.oneSpace());
		// "+"-KW after alias-name
		document.append(document.prepend(
				textRegionExtensions.regionFor(namedImp)
						.feature(N4JSPackage.Literals.IMPORT_SPECIFIER__DECLARED_DYNAMIC),
				hrf -> hrf.noSpace()),
				hrf -> hrf.oneSpace());
	}

	void _format(NamespaceImportSpecifier nsImp, IFormattableDocument document) {
		document.append(textRegionExtensions.regionFor(nsImp).keyword("*"), hrf -> hrf.oneSpace());
		document.append(textRegionExtensions.regionFor(nsImp).keyword("as"), hrf -> hrf.oneSpace());
		// "+"-KW after alias-name
		document.append(document.prepend(textRegionExtensions.regionFor(nsImp)
				.feature(N4JSPackage.Literals.IMPORT_SPECIFIER__DECLARED_DYNAMIC),
				hrf -> hrf.noSpace()),
				hrf -> hrf.oneSpace());
	}

	void _format(ExportDeclaration export, IFormattableDocument document) {
		document.append(textRegionExtensions.regionFor(export).keyword("export"), hrf -> {
			hrf.oneSpace();
			hrf.setNewLines(1);
			// Apply prioritization to catch cases of 'trapped' annotations e.g. "export @Final public class" which
			// could also be reordered to "@Final export public class.."
			hrf.setPriority(PRIO_13); // Priority higher then highPriority used in AnnotationList.
		});

		for (EObject eo : export.eContents()) {
			format(eo, document);
		}

		// Fix Trapped annotations:
		ExportableElement exported = export.getExportedElement();
		if (exported instanceof AnnotableScriptElement) {
			AnnotationList annoList = ((AnnotableScriptElement) exported).getAnnotationList();
			if (annoList != null && !annoList.getAnnotations().isEmpty()) {
				document.append(last(annoList.getAnnotations()), hrf -> {
					hrf.setNewLines(0);
					hrf.oneSpace();
					hrf.setPriority(PRIO_13);
				});
			}
		}
	}

	void _format(IfStatement stmt, IFormattableDocument document) {
		Pair<ISemanticRegion, ISemanticRegion> parenPair = textRegionExtensions.regionFor(stmt).keywordPairs("(", ")")
				.get(0);
		document.interior(parenPair, hrf -> {
			hrf.noSpace();
			hrf.indent();
		});
		document.prepend(parenPair.getKey(), hrf -> hrf.oneSpace());
		document.append(parenPair.getValue(), hrf -> hrf.oneSpace());

		document.append(document.prepend(textRegionExtensions.regionFor(stmt).keyword("else"), hrf -> {
			hrf.autowrap();
			hrf.oneSpace();
		}), hrf -> hrf.oneSpace());

		document.prepend(stmt.getElseStmt(), hrf -> {
			hrf.oneSpace();
			hrf.setNewLines(0);
		});

		document.format(stmt.getExpression());
		document.format(stmt.getIfStmt());
		document.format(stmt.getElseStmt());

	}

	void _format(SwitchStatement swStmt, IFormattableDocument document) {
		insertSpaceInFrontOfCurlyBlockOpener(swStmt, document);
		interiorBUGFIX(swStmt, hrf -> hrf.indent(), document); // swStmt.interior, hrf->hrf.indent();
		document.format(swStmt.getExpression());
		document.prepend(swStmt.getCases().get(0), hrf -> hrf.newLine());

		for (EObject eo : swStmt.getCases()) {
			format(eo, document);
		}
	}

	/** Formats DefaultCaseClause + CaseClause */
	void _format(AbstractCaseClause caseClause, IFormattableDocument document) {
		interiorBUGFIX(caseClause, hrf -> hrf.indent(), document); // caseClause.interior, hrf->hrf.indent();

		EList<Statement> stmts = caseClause.getStatements();

		if (stmts.size() == 1) {
			if (stmts.get(0) instanceof Block) {
				document.prepend(stmts.get(0), hrf -> hrf.setNewLines(0, 0, 0));
			} else {
				document.prepend(stmts.get(0), hrf -> hrf.setNewLines(0, 1, 1));
			}
		} else {
			document.prepend(stmts.get(0), hrf -> hrf.setNewLines(1, 1, 1));
		}

		// textRegionExtensions.regionFor(caseClause).keyword(":").prepend, hrf->hrf.oneSpace(); // In case one space
		// before the colon is desired
		for (EObject eo : stmts) {
			format(eo, document);
		}
		for (EObject eo : stmts) {
			document.append(eo, hrf -> hrf.setNewLines(1, 1, maxConsecutiveNewLines()));
		}

		document.append(caseClause, hrf -> hrf.setNewLines(1, 1, maxConsecutiveNewLines()));
	}

	void _format(CastExpression expr, IFormattableDocument document) {
		document.append(document.prepend(textRegionExtensions.regionFor(expr).keyword("as"), hrf -> {
			hrf.setNewLines(0);
			hrf.oneSpace();
		}), hrf -> {
			hrf.setNewLines(0);
			hrf.oneSpace();
		});
		document.format(expr.getExpression());
		document.format(expr.getTargetTypeRefNode());
	}

	void _format(Block block, IFormattableDocument document) {
		if (debug) {
			LOGGER.debug("Formatting block " + containmentStructure(block));
		}

		// Beware there are blocks in the grammar, that are not surrounded by curly braces. (e.g. FunctionExpression)

		// Block not nested in other blocks usually are bodies. We want them separated by a space:
		if (!(block.eContainer() instanceof Block || block.eContainer() instanceof Script)) {
			// TODO maybe invert the control here, since the block is formatting the outside.
			document.prepend(textRegionExtensions.regionFor(block).keyword("{"), hrf -> hrf.oneSpace());
		}

		interiorBUGFIX(block, hrf -> hrf.indent(), document); // block.interior, hrf->hrf.indent();

		document.prepend(block.getStatements().get(0), hrf -> hrf.setNewLines(1, 1, maxConsecutiveNewLines()));

		for (Statement s : block.getStatements()) {
			document.append(s, hrf -> hrf.setNewLines(1, 1, maxConsecutiveNewLines()));
		}

		for (Statement s : block.getStatements()) {
			document.format(s);
		}

		// Format empty curly blocks, necessary for comments inside:
		Pair<ISemanticRegion, ISemanticRegion> braces = textRegionExtensions.regionFor(block).keywordPairs("{", "}")
				.get(0);
		if (braces != null && braces.getKey().getNextSemanticRegion() == braces.getValue()) {
			// empty block:
			if (braces.getKey().getNextHiddenRegion().containsComment()) {
				document.append(braces.getKey(), hrf -> hrf.setNewLines(1, 1, maxConsecutiveNewLines()));
			} else {
				document.append(braces.getKey(), hrf -> {
					hrf.setNewLines(1);
					hrf.noSpace();
				});
			}
		}
	}

	void _format(ReturnStatement ret, IFormattableDocument document) {
		interiorBUGFIX(ret, hrf -> hrf.indent(), document); // ret.interior[indent;]
		document.prepend(ret.getExpression(), hrf -> {
			hrf.oneSpace();
			hrf.setNewLines(0);
		});
		document.format(ret.getExpression());
	}

	void _format(AdditiveExpression add, IFormattableDocument document) {
		document.append(document.surround(
				textRegionExtensions.regionFor(add).feature(N4JSPackage.Literals.ADDITIVE_EXPRESSION__OP),
				hrf -> hrf.oneSpace()), hrf -> hrf.autowrap());
		document.format(add.getLhs());
		document.format(add.getRhs());
	}

	void _format(MultiplicativeExpression mul, IFormattableDocument document) {
		document.append(document.surround(
				textRegionExtensions.regionFor(mul).feature(N4JSPackage.Literals.MULTIPLICATIVE_EXPRESSION__OP),
				hrf -> hrf.oneSpace()), hrf -> hrf.autowrap());
		document.format(mul.getLhs());
		document.format(mul.getRhs());
	}

	void _format(BinaryBitwiseExpression binbit, IFormattableDocument document) {
		document.surround(textRegionExtensions.regionFor(binbit)
				.feature(N4JSPackage.Literals.BINARY_BITWISE_EXPRESSION__OP), hrf -> hrf.oneSpace());
		document.format(binbit.getLhs());
		document.format(binbit.getRhs());
	}

	void _format(BinaryLogicalExpression binLog, IFormattableDocument document) {
		ISemanticRegion opReg = textRegionExtensions.regionFor(binLog)
				.feature(N4JSPackage.Literals.BINARY_LOGICAL_EXPRESSION__OP);
		document.surround(opReg, hrf -> hrf.oneSpace());
		document.format(binLog.getLhs());
		document.format(binLog.getRhs());
		// auto-wrap:
		boolean autoWrapInFront = getPreference(FORMAT_AUTO_WRAP_IN_FRONT_OF_LOGICAL_OPERATOR);
		if (autoWrapInFront) {
			document.prepend(opReg, hrf -> {
				hrf.autowrap();
				hrf.lowPriority();
				hrf.setNewLines(0, 0, 1);
			});
		} else {
			document.append(opReg, hrf -> {
				hrf.autowrap();
				hrf.lowPriority();
				hrf.setNewLines(0, 0, 1);
			});
		}
	}

	void _format(EqualityExpression eqExpr, IFormattableDocument document) {
		document.append(document.surround(
				textRegionExtensions.regionFor(eqExpr).feature(N4JSPackage.Literals.EQUALITY_EXPRESSION__OP),
				hrf -> hrf.oneSpace()), hrf -> hrf.autowrap());
		document.format(eqExpr.getLhs());
		document.format(eqExpr.getRhs());
	}

	void _format(RelationalExpression relExpr, IFormattableDocument document) {
		document.append(document.surround(
				textRegionExtensions.regionFor(relExpr).feature(N4JSPackage.Literals.RELATIONAL_EXPRESSION__OP),
				hrf -> hrf.oneSpace()), hrf -> hrf.autowrap());
		document.format(relExpr.getLhs());
		document.format(relExpr.getRhs());
	}

	void _format(ShiftExpression shiftExpr, IFormattableDocument document) {
		document.append(document.surround(
				textRegionExtensions.regionFor(shiftExpr).feature(N4JSPackage.Literals.SHIFT_EXPRESSION__OP),
				hrf -> hrf.oneSpace()), hrf -> hrf.autowrap());
		document.format(shiftExpr.getLhs());
		document.format(shiftExpr.getRhs());
	}

	void _format(CommaExpression comma, IFormattableDocument document) {
		configureCommas(comma, document);
		for (EObject eo : comma.eContents()) {
			document.format(eo);
		}
	}

	void _format(ConditionalExpression cond, IFormattableDocument document) {
		document.append(document.surround(textRegionExtensions.regionFor(cond).keyword("?"), hrf -> hrf.oneSpace()),
				hrf -> {
					hrf.autowrap();
					hrf.lowPriority();
					hrf.setNewLines(0, 0, 1);
				});
		document.append(document.surround(textRegionExtensions.regionFor(cond).keyword(":"), hrf -> hrf.oneSpace()),
				hrf -> {
					hrf.autowrap();
					hrf.lowPriority();
					hrf.setNewLines(0, 0, 1);
				});
		document.format(cond.getExpression());
		document.format(cond.getTrueExpression());
		document.format(cond.getFalseExpression());
	}

	void _format(AwaitExpression await, IFormattableDocument document) {
		document.append(document.prepend(textRegionExtensions.regionFor(await).keyword("await"), hrf -> hrf.oneSpace()),
				hrf -> {
					hrf.oneSpace();
					hrf.setNewLines(1);
				});
		document.format(await.getExpression());
	}

	void _format(PromisifyExpression promify, IFormattableDocument document) {
		noSpaceAfterAT(promify, document);
		document.append(textRegionExtensions.regionFor(promify).keyword("Promisify"), hrf -> hrf.oneSpace());
		document.format(promify.getExpression());
	}

	void _format(IndexedAccessExpression idxAcc, IFormattableDocument document) {
		IEObjectRegion indexRegion = textRegionExtensions.regionForEObject(idxAcc.getIndex());
		document.append(document.prepend(indexRegion.getPreviousSemanticRegion(), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(1);
		}), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(1);
		});
		document.prepend(indexRegion.getNextSemanticRegion(), hrf -> hrf.noSpace());

		document.format(idxAcc.getIndex());
		document.format(idxAcc.getTarget());
	}

	void _format(NewExpression newExp, IFormattableDocument document) {
		document.append(document.prepend(textRegionExtensions.regionFor(newExp).keyword("new"), hrf -> hrf.oneSpace()),
				hrf -> {
					hrf.oneSpace();
					hrf.setNewLines(1);
				});
		document.format(newExp.getCallee());
		// Watch out, commas are used in Type-args and in argument list ! If necessary distinguish by offset.
		ISemanticRegion commas = textRegionExtensions.regionFor(newExp).keyword(",");
		document.append(document.prepend(commas, hrf -> hrf.noSpace()), hrf -> hrf.oneSpace());

		// TODO maybe factor out TypeArgs formatting.
		Pair<ISemanticRegion, ISemanticRegion> typeArgsAngle = textRegionExtensions.regionFor(newExp)
				.keywordPairs("<", ">").get(0);

		if (typeArgsAngle != null) {
			document.append(document.prepend(typeArgsAngle.getKey(), hrf -> {
				hrf.noSpace();
				hrf.setNewLines(1);
			}), hrf -> {
				hrf.noSpace();
				hrf.setNewLines(1);
			});
			document.prepend(typeArgsAngle.getValue(), hrf -> {
				hrf.noSpace();
				hrf.setNewLines(1);
			});
		}
		for (TypeReferenceNode<TypeRef> trn : newExp.getTypeArgs()) {
			document.format(trn);
		}

		if (newExp.isWithArgs()) {
			Pair<ISemanticRegion, ISemanticRegion> argParen = textRegionExtensions.regionFor(newExp)
					.keywordPairs("(", ")").get(0);
			document.append(document.prepend(argParen.getKey(), hrf -> {
				hrf.setNewLines(1);
				hrf.noSpace();
			}), hrf -> hrf.noSpace());
			document.prepend(argParen.getValue(), hrf -> hrf.noSpace());
			for (Argument arg : newExp.getArguments()) {
				document.format(arg);
			}
		}
	}

	void _format(PostfixExpression postFix, IFormattableDocument document) {
		// no line break allowed between Expression and operator !
		document.append(document.prepend(
				textRegionExtensions.regionFor(postFix).feature(N4JSPackage.Literals.POSTFIX_EXPRESSION__OP), hrf -> {
					hrf.setNewLines(1);
					hrf.noSpace();
				}), hrf -> {
					hrf.oneSpace();
					hrf.lowPriority();
				});
		// giving low priority for situations of closing parenthesis: "(a++)"
		document.format(postFix.getExpression());
	}

	void _format(TaggedTemplateString taggedTemplate, IFormattableDocument document) {
		document.append(textRegionExtensions.regionFor(taggedTemplate)
				.feature(N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET), hrf -> {
					hrf.setNewLines(1);
					hrf.oneSpace();
				});
		document.format(taggedTemplate.getTarget());
		document.format(taggedTemplate.getTemplate());
	}

	void _format(UnaryExpression unaryExpr, IFormattableDocument document) {
		// The operators 'void' 'delete' and 'typeof' must be separated from operand.
		boolean requireSpace = (unaryExpr.getOp().ordinal() <= UnaryOperator.TYPEOF_VALUE);
		document.append(textRegionExtensions.regionFor(unaryExpr).feature(N4JSPackage.Literals.UNARY_EXPRESSION__OP),
				hrf -> {
					if (requireSpace)
						hrf.oneSpace();
					else
						hrf.noSpace();
					hrf.setNewLines(1);
				});

		document.format(unaryExpr.getExpression());
	}

	void _format(YieldExpression yieldExpr, IFormattableDocument document) {
		// " yield " or " yield* "
		document.append(document.prepend(textRegionExtensions.regionFor(yieldExpr).keyword("yield"),
				hrf -> hrf.oneSpace()),
				hrf -> {
					if (yieldExpr.isMany()) {
						hrf.noSpace();
					} else {
						hrf.oneSpace();
					}
				});

		if (yieldExpr.isMany()) {
			document.append(document.prepend(textRegionExtensions.regionFor(yieldExpr).keyword("*"),
					hrf -> {
						hrf.noSpace();
						hrf.setNewLines(1);
					}),
					hrf -> hrf.oneSpace());
		}
		document.format(yieldExpr.getExpression());
	}

	void _format(ParenExpression parenE, IFormattableDocument document) {
		document.append(head(textRegionExtensions.semanticRegions(parenE)), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(1);
			hrf.autowrap();
		});
		document.prepend(last(textRegionExtensions.semanticRegions(parenE)), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(1);
			hrf.autowrap();
		});
		interiorBUGFIX(parenE, hrf -> hrf.indent(), document); // parenE.interior, hrf->hrf.indent();
		document.format(parenE.getExpression());
	}

	void _format(ArrowFunction arrowF, IFormattableDocument document) {
		configureCommas(arrowF, document);
		document.surround(textRegionExtensions.regionFor(arrowF).ruleCallTo(grammarAccess.getArrowRule()),
				hrf -> hrf.oneSpace());
		document.interior(head(textRegionExtensions.regionFor(arrowF).keywordPairs("(", ")")), hrf -> hrf.noSpace());
		// too lax: arrowF.fpars.configureFormalParameters(document,[/*NTD*/]);

		if (arrowF.isHasBracesAroundBody()) {

			// format body as block. NOTE: this block differs from other blocks, since the curly braces are defined in
			// the ArrowExpression. special handling of indentation in inside the braces.
			Pair<ISemanticRegion, ISemanticRegion> bracesPair = textRegionExtensions.regionFor(arrowF).keywordPairs("{",
					"}").get(0);
			document.interior(bracesPair, hrf -> hrf.indent());

			if (last(bracesPair.getKey().getLineRegions()).contains(bracesPair.getValue())
					// one line '{ do; stuff; }'
					|| last(bracesPair.getKey().getLineRegions()).contains(bracesPair.getKey().getNextSemanticRegion())
			// no line-break after braces e.g. '{ do; \n stuff; }'
			) {
				// one line
				if (arrowF.getBody() != null) {
					for (int idx = 0; idx < arrowF.getBody().getStatements().size(); idx++) {
						Statement it = arrowF.getBody().getStatements().get(idx);
						document.format(it);
						if (idx != 0) {
							document.prepend(it, hrf -> {
								hrf.oneSpace();
								hrf.autowrap();
								hrf.setNewLines(1);
							});
						}
					}
				}

				// do not autowrap after "{" to keep wrap-semantic
				document.append(bracesPair.getKey(), hrf -> hrf.oneSpace());
				document.prepend(bracesPair.getValue(), hrf -> hrf.oneSpace());
			}

			else {
				// multi-line
				if (arrowF.getBody() != null && !arrowF.getBody().getStatements().isEmpty()) {
					document.prepend(arrowF.getBody().getStatements().get(0), hrf -> hrf.setNewLines(1));
					for (Statement it : arrowF.getBody().getStatements()) {
						document.format(it);
						document.append(it, hrf -> hrf.setNewLines(1));
					}

				} else {
					// empty block, squash interior.
					document.append(bracesPair.getKey(), hrf -> {
						hrf.noSpace();
						hrf.setNewLines(1);
					});
					document.prepend(bracesPair.getValue(), hrf -> {
						hrf.noSpace();
						hrf.setNewLines(1);
					});
				}
			}

		} else if (arrowF.getBody() != null) {
			// no braces Around the implicit return statement.
			document.format(arrowF.getBody().getStatements().get(0));
		}
	}

	void _format(ArrayLiteral al, IFormattableDocument document) {
		Pair<ISemanticRegion, ISemanticRegion> bracketPair = textRegionExtensions.regionFor(al).keywordPairs("[", "]")
				.get(0);
		document.interior(bracketPair, hrf -> hrf.indent());
		boolean sameLine = bracketPair.getKey().getLineRegions().get(0).contains(bracketPair.getValue());
		// auto wrap in front of AL-Elements, to preserve comma at end.
		if (!sameLine) {
			document.append(last(al.getElements()), hrf -> hrf.autowrap());
			for (int num = 0; num < al.getElements().size(); num++) {
				ArrayElement it = al.getElements().get(num);
				final int numF = num;
				document.append(document.prepend(it, hrf -> {
					hrf.autowrap();
					hrf.setNewLines(0, 0, 1);
					if (numF != 0) {
						hrf.oneSpace();
					}
				}), hrf -> hrf.noSpace());
			}
			// format last bracket if in single.line.
			if (!last(bracketPair.getValue().getPreviousSemanticRegion().getLineRegions())
					.contains(bracketPair.getValue())) {
				document.prepend(bracketPair.getValue(), hrf -> hrf.newLine());
			}
		} else {
			for (int num = 0; num < al.getElements().size(); num++) {
				ArrayElement it = al.getElements().get(num);
				final int numF = num;
				document.prepend(it, hrf -> {
					hrf.autowrap();
					if (numF != 0) {
						hrf.oneSpace();
					}
				});
			}
		}
	}

	void _format(ObjectLiteral ol, IFormattableDocument document) {
		configureCommas(ol, document);

		Pair<ISemanticRegion, ISemanticRegion> bracePair = textRegionExtensions.regionFor(ol).keywordPairs("{",
				"}").get(0);
		document.interior(bracePair, hrf -> hrf.indent());

		// Decide on multiline or not.
		// Rule: if opening brace is preceded by a line break, then go multiline.
		boolean sameLine = bracePair.getKey().getLineRegions().get(0)
				.contains(bracePair.getKey().getNextSemanticRegion().getLineRegions().get(0));
		// OLD: val sameLine = bracePair.key.lineRegions.head.contains( bracePair.value );

		if (!sameLine) {
			// format WS in front of closing brace
			document.prepend(bracePair.getValue(), hrf -> hrf.newLine());
			for (PropertyAssignment it : ol.getPropertyAssignments()) {
				document.prepend(it, hrf -> hrf.newLine());
			}

			if ((bracePair.getKey().getNextSemanticRegion()) == bracePair.getValue()) {
				// empty multiline, trigger formatting:
				document.append(bracePair.getKey(), hrf -> hrf.newLine());
			}

		} else { // in one line
			document.append(bracePair.getKey(), hrf -> hrf.setNewLines(1));
			for (int num = 0; num < ol.getPropertyAssignments().size(); num++) {
				PropertyAssignment it = ol.getPropertyAssignments().get(num);

				final int numF = num;
				document.prepend(it, hrf -> {
					hrf.setNewLines(1);
					if (numF != 0) {
						hrf.autowrap();
						hrf.oneSpace();
					} else {
						hrf.noSpace();
					}
				});
			}
			// low priority to avoid conflict with dangling commas
			document.prepend(bracePair.getValue(), hrf -> {
				hrf.setNewLines(1);
				hrf.noSpace();
				hrf.lowPriority();
			});
		}

		for (EObject eo : ol.eContents()) {
			format(eo, document);
		}
	}

	void _format(ForStatement fst, IFormattableDocument document) {

		document.append(textRegionExtensions.regionFor(fst).keyword("for"), hrf -> {
			hrf.oneSpace();
			hrf.setNewLines(1);
			hrf.autowrap();
		});

		Pair<ISemanticRegion, ISemanticRegion> parenPair = textRegionExtensions.regionFor(fst).keywordPairs("(",
				")").get(0);
		document.append(parenPair.getKey(), hrf -> {
			hrf.noSpace();
			hrf.autowrap();
			hrf.setNewLines(1);
		});
		document.append(document.prepend(parenPair.getValue(), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(1);
		}), hrf -> {
			hrf.oneSpace();
			hrf.setNewLines(1);
			hrf.autowrap();
		});

		for (ISemanticRegion it : textRegionExtensions.regionFor(fst).keywords("in", "of")) {
			document.surround(it, hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(1);
				hrf.autowrap();
			});
		}

		for (ISemanticRegion it : textRegionExtensions.regionFor(fst).keywords(";")) {
			document.append(document.prepend(it, hrf -> {
				hrf.noSpace();
				hrf.setNewLines(1);
			}), hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(1);
				hrf.autowrap();
			});
		}

		for (EObject eo : fst.eContents()) {
			format(eo, document);
		}
	}

	void _format(TemplateLiteral tl, IFormattableDocument document) {
		interiorBUGFIX(tl, hrf -> hrf.indent(), document); // tl.interior[indent;];
		for (Expression it : tl.getSegments()) {
			if (it instanceof TemplateSegment) {
				noOp();
			} else {
				document.surround(it, hrf -> {
					hrf.oneSpace();
					hrf.autowrap();
				});
			}
			document.format(it);
		}
	}

	private void noOp() {
		// empty
	}

	@SuppressWarnings("unused")
	void _format(TemplateSegment tl, IFormattableDocument document) {
		// just leave as is.
	}

	void _format(N4TypeVariable tv, IFormattableDocument document) {
		// "out"
		if (tv.isDeclaredCovariant()) {
			document.append(textRegionExtensions.regionFor(tv)
					.feature(N4JSPackage.Literals.N4_TYPE_VARIABLE__DECLARED_COVARIANT), hrf -> hrf.oneSpace());
		}
		// "in"
		if (tv.isDeclaredContravariant()) {
			document.append(textRegionExtensions.regionFor(tv)
					.feature(N4JSPackage.Literals.N4_TYPE_VARIABLE__DECLARED_CONTRAVARIANT), hrf -> hrf.oneSpace());
		}

		TypeReferenceNode<TypeRef> upperBoundNode = tv.getDeclaredUpperBoundNode();

		if (upperBoundNode != null) {
			// "extends"
			document.surround(textRegionExtensions.regionFor(tv).keyword("extends"), hrf -> hrf.oneSpace());
			document.surround(textRegionExtensions.immediatelyFollowing(upperBoundNode).keyword("&"),
					hrf -> hrf.oneSpace());
			format(upperBoundNode, document);
		}

	}

	@SuppressWarnings("unused")
	void _format(Expression exp, IFormattableDocument document) {
		// Things not to format:
		if (exp instanceof BooleanLiteral
				|| exp instanceof IdentifierRef
				|| exp instanceof IntLiteral
				|| exp instanceof NullLiteral
				|| exp instanceof NumberLiteral
				|| exp instanceof RegularExpressionLiteral
				|| exp instanceof StringLiteral
				|| exp instanceof ThisLiteral
				|| exp instanceof SuperLiteral
				|| exp instanceof JSXElement) {
			return;
		}

		throw new UnsupportedOperationException(
				"expression " + exp.getClass().getSimpleName() + " not yet implemented.");
	}

	/** simply formats all content */
	void genericFormat(Expression exp, IFormattableDocument document) {
		for (EObject eo : exp.eContents()) {
			format(eo, document);
		}
	}

	void _format(AssignmentExpression ass, IFormattableDocument document) {
		document.append(ass.getLhs(), hrf -> hrf.oneSpace());
		document.prepend(ass.getRhs(), hrf -> hrf.oneSpace());
		document.format(ass.getLhs());
		document.format(ass.getRhs());
	}

	void _format(ExpressionStatement eStmt, IFormattableDocument document) {
		format(eStmt.getExpression(), document);
	}

	/** var,let,const */
	void _format(VariableStatement vStmt, IFormattableDocument document) {

		configureModifiers(vStmt, document);

		// "let", "var" or "const"
		document.append(textRegionExtensions.regionFor(vStmt)
				.feature(N4JSPackage.Literals.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD), hrf -> hrf.oneSpace());

		configureCommas(vStmt, document);

		interiorBUGFIX(vStmt, hrf -> hrf.indent(), document); // vStmt.interior, hrf->hrf.indent();
		int lastIdx = vStmt.getVarDeclsOrBindings().size() - 1;

		for (int i = 0; i < vStmt.getVarDeclsOrBindings().size(); i++) {
			VariableDeclarationOrBinding e = vStmt.getVarDeclsOrBindings().get(i);
			document.format(e);

			if (i > 0) { // assignments start in separate lines.
				if (e instanceof VariableDeclaration) {
					if (e.getExpression() != null) {
						document.prepend(e, hrf -> hrf.newLine());
					} else {
						document.prepend(e, hrf -> {
							hrf.setNewLines(0, 1, 1);
							hrf.lowPriority();
						});
					}
				} else if (e instanceof VariableBinding) {
					if (e.getExpression() != null) {
						document.prepend(e, hrf -> hrf.newLine());
					} else {
						document.prepend(e, hrf -> {
							hrf.setNewLines(0, 1, 1);
							hrf.lowPriority();
						});
					}
				}
			}

			if (i < lastIdx) {
				// assignments start let following continue in separate lines.
				if (e instanceof VariableDeclaration) {
					if (e.getExpression() != null) {
						document.append(textRegionExtensions.immediatelyFollowing(e).keyword(","),
								hrf -> hrf.newLine());
					} else {
						document.prepend(e, hrf -> {
							hrf.setNewLines(0, 1, 1);
							hrf.lowPriority();
						});
					}

				} else if (e instanceof VariableBinding) {
					if (e.getExpression() != null) {
						document.append(textRegionExtensions.immediatelyFollowing(e).keyword(","),
								hrf -> hrf.newLine());
					} else {
						document.prepend(e, hrf -> {
							hrf.setNewLines(0, 1, 1);
							hrf.lowPriority();
						});
					}
				}
			}
		}
	}

	void _format(VariableDeclaration vDecl, IFormattableDocument document) {
		document.set(textRegionExtensions.previousHiddenRegion(vDecl), hrf -> hrf.oneSpace());
		document.surround(textRegionExtensions.regionFor(vDecl).keyword("="), hrf -> hrf.oneSpace());
		document.format(vDecl.getExpression());
		document.format(vDecl.getDeclaredTypeRefInAST());
	}

	void _format(VariableBinding vBind, IFormattableDocument document) {
		document.set(textRegionExtensions.previousHiddenRegion(vBind), hrf -> hrf.oneSpace());
		document.surround(textRegionExtensions.regionFor(vBind).keyword("="), hrf -> hrf.oneSpace());
		document.format(vBind.getPattern());
		document.format(vBind.getExpression());
		document.format(vBind.getPattern());
	}

	void _format(BindingPattern bp, IFormattableDocument document) {
		// ObjectBindingPattern
		// ArrayBindingPattern

		// '{' or '['
		document.append(head(textRegionExtensions.semanticRegions(bp)), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(1);
			hrf.autowrap();
		});
		document.prepend(last(textRegionExtensions.semanticRegions(bp)), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(1);
			hrf.autowrap();
		});
		configureCommas(bp, document); // doesn't handle elision.

		for (EObject eo : bp.eContents()) {
			format(eo, document);
		}
	}

	void _format(ThrowStatement thrStmt, IFormattableDocument document) {
		// No autowrap, otherwise ASI
		document.prepend(thrStmt.getExpression(), hrf -> {
			hrf.setNewLines(0, 0, 0);
			hrf.oneSpace();
		});
		document.format(thrStmt.getExpression());

	}

	void _format(CatchBlock ctch, IFormattableDocument document) {
		document.prepend(ctch, hrf -> {
			hrf.setNewLines(0, 0, 0);
			hrf.oneSpace();
		});
		document.format(ctch.getCatchVariable());
		document.format(ctch.getBlock());

	}

	void _format(FinallyBlock finlly, IFormattableDocument document) {
		document.set(textRegionExtensions.previousHiddenRegion(finlly), hrf -> {
			hrf.setNewLines(1);
			hrf.oneSpace();
		});
		document.format(finlly.getBlock());
	}

	/**
	 * Insert one space in front of first '{' in the direct content of the element. semEObject is a semanticObject, e.g.
	 * N4EnumDecl, N4Classifier ...
	 */
	private void insertSpaceInFrontOfCurlyBlockOpener(EObject semEObject, IFormattableDocument document) {
		document.prepend(textRegionExtensions.regionFor(semEObject).keyword("{"), hrf -> hrf.oneSpace());
	}

	/** force: " @" and no newLine after '@' */
	private void noSpaceAfterAT(EObject semEObject, IFormattableDocument document) {
		document.prepend(document.append(textRegionExtensions.regionFor(semEObject).keyword("@"), hrf -> {
			hrf.noSpace();
			hrf.setNewLines(1);
		}), hrf -> hrf.oneSpace());
	}

	/** On the direct level of an semantic Object enforce commas to ", " with autoWrap option. */
	private void configureCommas(EObject semEObject, IFormattableDocument document) {
		for (ISemanticRegion sr : textRegionExtensions.regionFor(semEObject).keywords(",")) {
			document.prepend(sr, hrf -> hrf.noSpace());
			document.append(sr, hrf -> {
				hrf.oneSpace();
				hrf.autowrap();
			});
		}
	}

	void indentExcludingAnnotations(EObject semObject, IFormattableDocument document) {
		// Exclude Annotations from indentation field.interior, hrf->hrf.indent();
		ISemanticRegion begin = findFirst(textRegionExtensions.semanticRegions(semObject),
				sr -> !(sr instanceof Annotation));
		ISemanticRegion end = last(textRegionExtensions.semanticRegions(semObject));

		if (begin != end) { // guard to prevent wrong indentation
			document.interior(begin, end, hrf -> hrf.indent());
		}
	}

	private void _configureAnnotations(AnnotableN4MemberDeclaration semEObject, IFormattableDocument document) {
		configureAnnotations(semEObject.getAnnotationList(), document);
	}

	@SuppressWarnings("unused")
	private void _configureAnnotations(AnnotablePropertyAssignment semEObject, IFormattableDocument document) {
		configureAnnotations(semEObject.getAnnotationList(), document);
	}

	private void _configureAnnotations(AnnotableScriptElement semEObject, IFormattableDocument document) {
		configureAnnotations(semEObject.getAnnotationList(), document);
	}

	private void _configureAnnotations(AnnotableExpression semEObject, IFormattableDocument document) {
		configureAnnotations(semEObject.getAnnotationList(), document);
	}

	private void _configureAnnotations(AbstractAnnotationList aList, IFormattableDocument document) {
		if (aList == null || aList.getAnnotations().isEmpty()) {
			return;
		}

		// TODO in case of trapped in Annotation like 'export @Final public class A{}' - a reorder would be necessary
		// (see format for export)
		document.prepend(aList, hrf -> {
			hrf.setNewLines(2, 2, 2);
			hrf.highPriority();
		});

		// TODO special annotations like @Internal ? --> together with public, reorder to be in same line?
		document.append(aList, hrf -> hrf.newLine());

		for (int idx = 0; idx < aList.getAnnotations().size(); idx++) {
			Annotation it = aList.getAnnotations().get(idx);
			configureAnnotation(it, document, true, idx == 0);
		}
	}

	/**
	 *
	 * @param withLineWraps
	 *            <code>true</code> do line-wrapping
	 * @param isFirstAnnotation
	 *            if this is the first annotation in a sequence ( used with line-wrapping in front of '@')
	 */
	private void configureAnnotation(Annotation ann, IFormattableDocument document, boolean withLineWraps,
			boolean isFirstAnnotation) {
		// configure arguments
		Pair<ISemanticRegion, ISemanticRegion> parens = textRegionExtensions.regionFor(ann).keywordPairs("(", ")")
				.get(0);
		if (parens != null) {
			document.append(document.prepend(parens.getKey(), hrf -> hrf.noSpace()), hrf -> hrf.noSpace());
			document.append(document.prepend(parens.getValue(), hrf -> hrf.noSpace()), hrf -> {
				if (withLineWraps) {
					hrf.noSpace();
					hrf.setNewLines(1);
				} else {
					hrf.oneSpace();
					hrf.setNewLines(0);
				}
			});
			document.interior(parens, hrf -> hrf.indent());
			// line break before "@":
			if (withLineWraps && !isFirstAnnotation) {
				document.prepend(parens.getKey().getPreviousSemanticRegion().getPreviousSemanticRegion(),
						hrf -> hrf.setNewLines(1));
			}

			configureCommas(ann, document);
		}

		// Configure @-Syntax
		// Special case here: for "@XY" we can either get "@" or "XY" as the first semantic element
		ISemanticRegion sr = head(textRegionExtensions.semanticRegions(ann));
		if (sr.getGrammarElement() instanceof Keyword) {
			// assume '@'
			document.append(sr, hrf -> {
				hrf.noSpace();
				hrf.setNewLines(0);
			});
		} else {
			// for "@Final" "Final" will be the first semantic region in case of exported classes,
			document.prepend(sr, hrf -> {
				hrf.noSpace();
				hrf.setNewLines(0);
			});
		}
	}

	@SuppressWarnings("unused")
	private void _configureAnnotations(Object semEObject, IFormattableDocument document) {
		// no annotations to be configured.
	}

	@SuppressWarnings("unused")
	private void _configureAnnotations(Void x, IFormattableDocument document) {
		// no annotations to be configured.
	}

	private void configureAnnotationsInLine(FormalParameter fpar, IFormattableDocument document) {
		if (fpar.getAnnotations().isEmpty()) {
			return;
		}
		// (@x @y("") bogus a:typ)
		Annotation fann = fpar.getAnnotations().get(0);

		configureAnnotation(fann, document, false, true);
		document.prepend(fann, hrf -> {
			hrf.noSpace();
			hrf.setNewLines(0);
			hrf.autowrap();
		});

		for (Annotation ann : tail(fpar.getAnnotations())) {
			configureAnnotation(ann, document, false, false);
			document.prepend(ann, hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
				hrf.autowrap();
			});
		}

		document.append(last(fpar.getAnnotations()), hrf -> {
			hrf.oneSpace();
			hrf.setNewLines(0);
			hrf.autowrap();
		});
	}

	/** only script-level annotations '@@' */
	private void formatScriptAnnotations(Script script, IFormattableDocument document) {
		if (script.getAnnotations().isEmpty()) {
			return;
		}

		if (textRegionExtensions.previousHiddenRegion(script.getAnnotations().get(0)).containsComment()) {
			document.prepend(script.getAnnotations().get(0), hrf -> hrf.noSpace());
		} else {
			document.prepend(script.getAnnotations().get(0), hrf -> {
				hrf.noSpace();
				hrf.setNewLines(0);
			});
		}
		document.append(last(script.getAnnotations()), hrf -> hrf.setNewLines(2, 2, 2));

		for (int idx = 0; idx < script.getAnnotations().size(); idx++) {
			Annotation it = script.getAnnotations().get(idx);
			if (idx != 0) {
				document.prepend(it, hrf -> {
					hrf.setNewLines(1);
					hrf.noSpace();
				});
			}
			// its an '@@'
			ISemanticRegion sr = head(textRegionExtensions.semanticRegions(it));
			document.append(sr, hrf -> {
				hrf.noSpace();
				hrf.setNewLines(0);
			});
		}
	}

	@Override
	public ITextReplacer createCommentReplacer(IComment comment) {
		// Overridden to distinguish between JSDOC-style, standard ML, formatter-off ML-comment.
		EObject grammarElement = comment.getGrammarElement();
		if (grammarElement instanceof AbstractRule) {
			String ruleName = ((AbstractRule) grammarElement).getName();
			if (ruleName.startsWith("ML")) {
				String cText = comment.getText();
				if (cText.startsWith("/**") && !cText.startsWith("/***")) { // JSDOC
					return new N4MultilineCommentReplacer(comment, '*');
				} else if (cText.startsWith("/*-")) { // Turn-off formatting.
					return new OffMultilineCommentReplacer(comment, !isNotFirstInLine(comment));
				} else { // All other
					return new FixedMultilineCommentReplacer(comment);
				}
			}
			if (ruleName.startsWith("SL")) {
				if (isNotFirstInLine(comment)) {
					return new SinglelineDocCommentReplacer(comment, "//");
				} else {
					return new SinglelineCodeCommentReplacer(comment, "//");
				}
			}
		}

		// fall back to super-impl.
		return super.createCommentReplacer(comment);
	}

	private static boolean isNotFirstInLine(IComment comment) {
		ILineRegion lineRegion = comment.getLineRegions().get(0);

		return !comment.contains(lineRegion.getOffset());
	}

	@Override
	public IndentHandlingTextReplaceMerger createTextReplacerMerger() {
		return new IndentHandlingTextReplaceMerger(this);
	}

	/** DEBUG-helper */
	private static String containmentStructure(EObject eo) {
		String name = eo.getClass().getSimpleName();
		if (eo.eContainer() != null) {
			return containmentStructure(eo.eContainer()) + "." + eo.eContainingFeature().getName() + "->" + name;
		}
		return name;
	}

	/** HELPER to avoid Warnings in code, since @SuppressWarnings("unused") is not active in xtend code. */
	@SuppressWarnings("unused")
	int suppressUnusedWarnings(Object... e) {
		return PRIO_4;
	}

	/**
	 * Simple tracker that only gives exactly one time the value {@code true} when calling
	 * {@link StateTrack#shouldDoThenDone()}
	 */
	private final static class StateTrack {
		private boolean done = false;

		/**
		 * This method returns {@code true} exactly on it's first invocation. Proceeding calls always return
		 * {@code false}.
		 *
		 * @return Returns {@code true} if not done, immediately switches {@link #done} to {@code true}; returns
		 *         {@code false} if already done.
		 */
		boolean shouldDoThenDone() {
			boolean ret = !done;
			done = true;
			return ret;
		}
	}

	/****************************************************************************************************************
	 *
	 * Type Expression
	 *
	 ***************************************************************************************************************/
	void _format(UnionTypeExpression ute, IFormattableDocument document) {
		for (ISemanticRegion sr : textRegionExtensions.regionFor(ute).keywords("|")) {
			document.prepend(document.surround(sr, hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
			}), hrf -> {
				hrf.autowrap();
				hrf.highPriority();
			});
		}

		for (TypeRef tr : ute.getTypeRefs()) {
			format(tr, document);
		}

		// OLD syntax:
		ISemanticRegion kwUnion = textRegionExtensions.regionFor(ute).keyword("union");
		if (kwUnion != null) {
			ISequentialRegion sr = document.append(document.prepend(kwUnion, hrf -> hrf.oneSpace()), hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
			});

			/* '{' */
			document.append(sr.getNextSemanticRegion(), hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
			});
			/* '}' */
			document.prepend(last(textRegionExtensions.semanticRegions(ute)), hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
			});
		}
	}

	void _format(IntersectionTypeExpression ite, IFormattableDocument document) {
		for (ISemanticRegion sr : textRegionExtensions.regionFor(ite).keywords("&")) {
			document.prepend(document.surround(sr, hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
			}), hrf -> {
				hrf.autowrap();
				hrf.highPriority();
			});
		}

		for (TypeRef tr : ite.getTypeRefs()) {
			format(tr, document);
		}
		// OLD syntax
		ISemanticRegion kwInersection = textRegionExtensions.regionFor(ite).keyword("intersection");
		if (kwInersection != null) {
			ISequentialRegion sr = document.append(document.prepend(kwInersection, hrf -> hrf.oneSpace()), hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
			});
			/* '{' */
			document.append(sr.getNextSemanticRegion(), hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
			});
			/* '}' */
			document.prepend(last(textRegionExtensions.semanticRegions(ite)), hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
			});
		}
	}

	void _format(TStructMember tsm, IFormattableDocument document) {
		if (tsm instanceof TField) {
			configureOptionality((TField) tsm, document);
		} else if (tsm instanceof org.eclipse.n4js.ts.types.FieldAccessor) {
			configureGetSetKeyword((org.eclipse.n4js.ts.types.FieldAccessor) tsm, document);
			configureOptionality((org.eclipse.n4js.ts.types.FieldAccessor) tsm, document);

			Pair<ISemanticRegion, ISemanticRegion> parenPair = textRegionExtensions.regionFor(tsm)
					.keywordPairs("(", ")").get(0);
			document.append(document.prepend(parenPair.getKey(), hrf -> {
				hrf.noSpace();
				hrf.setNewLines(0);
			}), hrf -> hrf.noSpace());
		}
		// get, set, method, field

		for (EObject eo : tsm.eContents()) {
			format(eo, document);
		}
		// TODO format TStruct* more thoroughly
		// (note: added some TStructMember formatting while working on IDE-2405, but it is still incomplete!)
	}

	private void configureUndefModifier(StaticBaseTypeRef sbtr, IFormattableDocument document) {
		// UndefModifier "?"
		document.prepend(textRegionExtensions.regionFor(sbtr)
				.feature(TypeRefsPackage.Literals.TYPE_REF__FOLLOWED_BY_QUESTION_MARK), hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
				});
	}

	void _format(ThisTypeRef ttr, IFormattableDocument document) {
		configureUndefModifier(ttr, document);
		if (ttr instanceof ThisTypeRefStructural) {
			interiorBUGFIX(ttr, hrf -> hrf.indent(), document);
			configureStructuralAdditions(ttr, document);
			for (EObject eo : ttr.eContents()) {
				format(eo, document);
			}
		}
	}

	void _format(ParameterizedTypeRef ptr, IFormattableDocument document) {
		interiorBUGFIX(ptr, hrf -> hrf.indent(), document); // ptr.interior, hrf->hrf.indent();
		configureUndefModifier(ptr, document);

		// Union / Intersection
		for (ISemanticRegion sr : textRegionExtensions.regionFor(ptr).keywords("&", "|")) {
			document.append(document.surround(sr, hrf -> {
				hrf.oneSpace();
				hrf.setNewLines(0);
			}), hrf -> {
				hrf.autowrap();
				hrf.highPriority();
			});
		}
		// Short-Hand Syntax for Arrays
		if (ptr.isArrayTypeExpression()) {
			document.append(textRegionExtensions.regionFor(ptr).keyword("["), hrf -> hrf.noSpace());
			document.append(textRegionExtensions.regionFor(ptr).keyword("]"), hrf -> hrf.noSpace());
		}
		// Short-Hand Syntax for IterableN
		if (ptr.isArrayNTypeExpression()) {
			document.append(textRegionExtensions.regionFor(ptr).keyword("["), hrf -> hrf.noSpace());
			document.append(textRegionExtensions.regionFor(ptr).keyword("]"), hrf -> hrf.noSpace());
		}
		formatTypeArguments(ptr, document);

		// ParameterizedTypeRefStructural :
		configureStructuralAdditions(ptr, document);

		// generically format content:
		for (EObject eo : ptr.eContents()) {
			format(eo, document);
		}
	}

	/** used for "~X with {}" except for the 'X' part. */
	void configureStructuralAdditions(TypeRef ptr, IFormattableDocument document) {
		ISemanticRegion semRegTypingStrategy = textRegionExtensions.regionFor(ptr)
				.ruleCallTo(grammarAccess.getTypingStrategyUseSiteOperatorRule());
		if (semRegTypingStrategy != null) {
			document.append(document.prepend(semRegTypingStrategy, hrf -> hrf.oneSpace()), hrf -> {
				hrf.noSpace();
				hrf.setNewLines(0);
			});

			// declaredType
			document.append(semRegTypingStrategy.getNextSemanticRegion(), null);
			ISemanticRegion kwWith = textRegionExtensions.regionFor(ptr).keyword("with");

			if (kwWith != null) {
				document.surround(kwWith, hrf -> {
					hrf.oneSpace();
					hrf.setNewLines(0);
					hrf.autowrap();
				});

				Pair<ISemanticRegion, ISemanticRegion> bracesPair = textRegionExtensions.regionFor(ptr)
						.keywordPairs("{", "}").get(0);

				document.append(bracesPair.getKey(), hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
					hrf.autowrap();
				});

				document.prepend(bracesPair.getValue(), hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
					hrf.autowrap();
				});

				// textRegionExtensions.regionFor(ptr).keywords(",",";").forEach[
				// prepend[hrf.noSpace();hrf.setNewLines(1);].append[hrf.oneSpace();hrf.setNewLines(1); hrf.autowrap();
				// } ]
				for (ISemanticRegion sr : textRegionExtensions.regionFor(ptr).keywords(";")) {
					document.append(document.prepend(sr, hrf -> {
						hrf.noSpace();
						hrf.setNewLines(0);
					}), hrf -> {
						hrf.oneSpace();
						hrf.setNewLines(0);
						hrf.autowrap();
						hrf.lowPriority();
					});
				}

				for (TStructMember sm : tail((((StructuralTypeRef) ptr).getAstStructuralMembers()))) {
					document.set(textRegionExtensions.regionForEObject(sm).getPreviousHiddenRegion(), hrf -> {
						hrf.oneSpace();
						hrf.setNewLines(0);
						hrf.autowrap();
					});
				}
			}
		}
	}

	/** formats type argument section including outside border. */
	void formatTypeArguments(ParameterizedTypeRef semObject, IFormattableDocument document) {
		if (semObject.getDeclaredTypeArgs().isEmpty()) {
			return;
		}
		// to "<":
		document.prepend(document.append(textRegionExtensions.regionFor(semObject).keyword("<"),
				hrf -> hrf.noSpace()),
				hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
					hrf.lowPriority();
				});
		document.append(document.prepend(textRegionExtensions.regionFor(semObject).keyword(">"),
				hrf -> hrf.noSpace()),
				hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
					hrf.lowPriority();
				});
		for (TypeArgument typeArg : semObject.getDeclaredTypeArgs()) {
			document.append(textRegionExtensions.immediatelyFollowing(document.append(typeArg, hrf -> hrf.noSpace()))
					.keyword(","), hrf -> hrf.oneSpace());
			format(typeArg, document);
		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Configure Methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void configureGetSetKeyword(FieldAccessor fieldAccessor, IFormattableDocument document) {
		String kw = (fieldAccessor instanceof GetterDeclaration) ? "get" : "set";

		document.append(document.prepend(textRegionExtensions.regionFor(fieldAccessor).keyword(kw),
				hrf -> hrf.oneSpace()),
				hrf -> {
					hrf.oneSpace();
					hrf.setNewLines(0);
					hrf.autowrap();
				});
	}

	private void configureGetSetKeyword(org.eclipse.n4js.ts.types.FieldAccessor tFieldAccessor,
			IFormattableDocument document) {

		String kw = (tFieldAccessor instanceof TGetter) ? "get" : "set";
		document.append(document.prepend(textRegionExtensions.regionFor(tFieldAccessor).keyword(kw),
				hrf -> hrf.oneSpace()),
				hrf -> {
					hrf.oneSpace();
					hrf.setNewLines(0);
					hrf.autowrap();
				});
	}

	private void configureOptionality(N4FieldDeclaration fieldDecl, IFormattableDocument document) {
		document.prepend(
				textRegionExtensions.regionFor(fieldDecl)
						.feature(N4JSPackage.Literals.N4_FIELD_DECLARATION__DECLARED_OPTIONAL),
				hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
				});
	}

	private void configureOptionality(FieldAccessor fieldAccessor, IFormattableDocument document) {
		document.prepend(
				textRegionExtensions.regionFor(fieldAccessor)
						.feature(N4JSPackage.Literals.FIELD_ACCESSOR__DECLARED_OPTIONAL),
				hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
				});
	}

	private void configureOptionality(TField tField, IFormattableDocument document) {
		document.prepend(
				textRegionExtensions.regionFor(tField).feature(TypesPackage.Literals.TFIELD__OPTIONAL),
				hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
				});
	}

	private void configureOptionality(org.eclipse.n4js.ts.types.FieldAccessor tFieldAccessor,
			IFormattableDocument document) {
		document.prepend(
				textRegionExtensions.regionFor(tFieldAccessor).feature(TypesPackage.Literals.FIELD_ACCESSOR__OPTIONAL),
				hrf -> {
					hrf.noSpace();
					hrf.setNewLines(0);
				});
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Bug-workarounds
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Temporarily used method to replace document.interior(EObject, Procedure1) to prevent wrong indentations.
	 *
	 * Main pattern replace document-extension method call:
	 *
	 * <pre>
	 * object.interior, hrf->hrf.indent()
	 * </pre>
	 *
	 * by
	 *
	 * <pre>
	 * object.interiorBUGFIX(, hrf->hrf.indent(),document);
	 *
	 * <pre>
	 *
	 */
	void interiorBUGFIX(EObject object, Procedure1<? super IHiddenRegionFormatter> init,
			IFormattableDocument document) {

		IEObjectRegion objRegion = getTextRegionAccess().regionForEObject(object);
		if (objRegion != null) {
			IHiddenRegion previous = objRegion.getPreviousHiddenRegion();
			IHiddenRegion next = objRegion.getNextHiddenRegion();
			if (previous != null && next != null && previous != next) {
				ISemanticRegion nsr = previous.getNextSemanticRegion();
				ISemanticRegion psr = next.getPreviousSemanticRegion();
				if (nsr != psr) { // standard
									// case
					document.interior(nsr, psr, init);
				} else {
					// former error-case:
					// there is no interior --> don't do anything!
					//
					// applying to the next HiddenRegion is a bad idea,
					// since it could wrongly indent a multiline-comment (c.f. GHOLD-260)
				}
			}
		}
	}

	/**
	 * Dummy method to prevent accidentally calling interior - extension method form document. You should call
	 * interiorBUGFIX instead !
	 */
	@SuppressWarnings("unused")
	Procedure1<? super IFormattableDocument> interior(EObject eo, Procedure1<? super IHiddenRegionFormatter> init) {
		throw new IllegalStateException("Method should not be called.");
	}

}
