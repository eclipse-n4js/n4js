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

import com.google.inject.Inject
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.AbstractAnnotationList
import org.eclipse.n4js.n4JS.AbstractCaseClause
import org.eclipse.n4js.n4JS.AdditiveExpression
import org.eclipse.n4js.n4JS.AnnotableExpression
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration
import org.eclipse.n4js.n4JS.AnnotablePropertyAssignment
import org.eclipse.n4js.n4JS.AnnotableScriptElement
import org.eclipse.n4js.n4JS.Annotation
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.AwaitExpression
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression
import org.eclipse.n4js.n4JS.BinaryLogicalExpression
import org.eclipse.n4js.n4JS.BindingPattern
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.BooleanLiteral
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.CatchBlock
import org.eclipse.n4js.n4JS.CommaExpression
import org.eclipse.n4js.n4JS.ConditionalExpression
import org.eclipse.n4js.n4JS.EqualityExpression
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FieldAccessor
import org.eclipse.n4js.n4JS.FinallyBlock
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.GetterDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.IfStatement
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.IndexedAccessExpression
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.ModifiableElement
import org.eclipse.n4js.n4JS.MultiplicativeExpression
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.NullLiteral
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.PostfixExpression
import org.eclipse.n4js.n4JS.PromisifyExpression
import org.eclipse.n4js.n4JS.RegularExpressionLiteral
import org.eclipse.n4js.n4JS.RelationalExpression
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ShiftExpression
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.SuperLiteral
import org.eclipse.n4js.n4JS.SwitchStatement
import org.eclipse.n4js.n4JS.TaggedTemplateString
import org.eclipse.n4js.n4JS.TemplateLiteral
import org.eclipse.n4js.n4JS.TemplateSegment
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.n4JS.ThrowStatement
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.YieldExpression
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.eclipse.n4js.ts.formatting2.TypeExpressionsFormatter
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.StaticBaseTypeRef
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TStructMember
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.xtext.AbstractRule
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.formatting2.IAutowrapFormatter
import org.eclipse.xtext.formatting2.IFormattableDocument
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter
import org.eclipse.xtext.formatting2.IHiddenRegionFormatting
import org.eclipse.xtext.formatting2.ITextReplacer
import org.eclipse.xtext.formatting2.internal.SinglelineCodeCommentReplacer
import org.eclipse.xtext.formatting2.internal.SinglelineDocCommentReplacer
import org.eclipse.xtext.formatting2.regionaccess.IComment
import org.eclipse.xtext.formatting2.regionaccess.IEObjectRegion
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1
import org.eclipse.xtext.xtext.generator.parser.antlr.splitting.simpleExpressions.NumberLiteral

import static org.eclipse.n4js.formatting2.N4JSFormatterPreferenceKeys.*
import static org.eclipse.n4js.formatting2.N4JSGenericFormatter.*

class N4JSFormatter extends TypeExpressionsFormatter {

	/** Debug switch */
	private static var debug = false;

	@Inject extension N4JSGrammarAccess

	/** PRIO_4 = -7 - still very low.
	 * Standard priorities in the formatter are:
	 * <ul>
	 * <li>lowPriority = -1;  == PRIO_10
	 * <li>normal priority = 0; == PRIO_11
	 * <li>high priority = +1; == PRIO_12
	 * </ul>
	 */
	static val PRIO_4 = PRIO_3 + 1;
	static val PRIO_13 = IHiddenRegionFormatter.HIGH_PRIORITY + 1;


	private def maxConsecutiveNewLines() {
		// let's stick to 2
		getPreference(FORMAT_MAX_CONSECUTIVE_NEWLINES);
	}


	def dispatch void format(Script script, extension IFormattableDocument document) {

		val extension generic = new N4JSGenericFormatter(_n4JSGrammarAccess, textRegionExtensions)
		if (getPreference(FORMAT_PARENTHESIS)) {
//			script.formatParenthesisBracketsAndBraces(document)
		}

		// TODO the following line requires more conflict handling with semicolons:
		// script.interior[noIndentation;];

		script.formatSemicolons(document);
		script.formatColon(document);

		formatScriptAnnotations(script,document);

		for (element : script.scriptElements) {
			// element.append[setNewLines(1, 1, maxConsecutiveNewLines);noSpace; autowrap].prepend[noSpace];
			element.append[setNewLines(1, 1, maxConsecutiveNewLines); autowrap];
			element.format;
		}

		// format last import, overrides default newLines:
		script.scriptElements.filter(ImportDeclaration).last?.append[setNewLines(2, 2, 3); highPriority];

	}

	/** put modifiers into a single line separated by one space, last modifier has one space to following element. */
	def void configureModifiers(EObject semObject, extension IFormattableDocument document){
		semObject.regionFor.ruleCallsTo( n4ModifierRule ).forEach[
			append[oneSpace];
		];
	}

	def void configureTypingStrategy(EObject semObject, extension IFormattableDocument document) {
		semObject.regionFor.ruleCallsTo( typingStrategyDefSiteOperatorRule, typingStrategyUseSiteOperatorRule).forEach[
			append[noSpace];
		]
	}

	def void formatTypeVariables(GenericDeclaration semObject, extension IFormattableDocument document) {
		if( semObject.typeVars.isEmpty ) return;
		// to "<":
		semObject.regionFor.keyword("<").prepend[oneSpace;newLines=0].append[noSpace];
		semObject.regionFor.keyword(">").prepend[noSpace;newLines=0];
		for( typeVar: semObject.typeVars ){
			typeVar.append[noSpace].immediatelyFollowing.keyword(",").append[oneSpace];
			typeVar.format(document);
		}

	}

	def dispatch format(N4ClassDeclaration clazz, extension IFormattableDocument document) {

		clazz.configureAnnotations(document);
		clazz.insertSpaceInFrontOfCurlyBlockOpener(document);
		clazz.indentExcludingAnnotations(document);


		clazz.configureTypingStrategy(document);
		clazz.configureModifiers(document);

		clazz.formatTypeVariables(document);

//		val semRegModifier = clazz.regionFor.feature( N4JSPackage.Literals.MODIFIABLE_ELEMENT__DECLARED_MODIFIERS);
//		if( semRegModifier !== null ) { // only if exists.
//			val beginModifierHR = semRegModifier.previousHiddenRegion;
//			val endModifierHR = semRegModifier.nextHiddenRegion;
//			// go over all semantic regions in the modifier location.
//			var currHR = beginModifierHR.nextHiddenRegion;
//			while( currHR != endModifierHR ){
//				currHR.set[oneSpace];
//				currHR = currHR.nextHiddenRegion;
//			}
//			endModifierHR.set[oneSpace];
//		} // end modifier formatting TODO extract into method.

		// TODO revise the following pattern of call-back implementations.
		// Define lambda for callback & normal use:
		val twolinesBeforeFirstMember = [int prio | clazz.ownedMembersRaw.head.prepend[newLines=2;priority = prio]];

		// Defines CallBack for autoWrap:
		val callBackOnAutoWrap = new IAutowrapFormatter{ // callback for auto-wrapping with implements
				var didReconfigure = false; // track to only execute once.
				override format(ITextSegment region, IHiddenRegionFormatting wrapped, IFormattableDocument document) {
					if( !didReconfigure ) {
						twolinesBeforeFirstMember.apply(IHiddenRegionFormatter.HIGH_PRIORITY); // reformat with higher priority
						didReconfigure=true; // keep state.
					}
				}
			};

		// 2nd version of implementing the callback:
		val StateTrack state2 = new StateTrack;
		val IAutowrapFormatter callBackOnAutoWrap2 = [region, hrFormatting, document2 |
			if( state2.shouldDoThenDone ) twolinesBeforeFirstMember.apply(IHiddenRegionFormatter.HIGH_PRIORITY);
		];
		suppressUnusedWarnings( callBackOnAutoWrap2 );

		// Allow for lineBreaks in front of keywords:
		clazz.regionFor.keyword("extends").prepend[
			setNewLines(0,0,1); // allow line break in front.
			autowrap;
		].append[oneSpace; autowrap;];

		clazz.regionFor.keyword("implements").prepend[
			setNewLines(0,0,1);
			autowrap;
			priority = IHiddenRegionFormatter.LOW_PRIORITY;
			onAutowrap=callBackOnAutoWrap;
		].append[oneSpace; autowrap;];

		clazz.implementedInterfaceRefs.tail.forEach[prepend[
			autowrap;
			priority = IHiddenRegionFormatter.LOW_PRIORITY;
			onAutowrap=callBackOnAutoWrap;
		]];

		// special case if the header of the class spans multiple lines, then insert extra line break.
		val kwClass = clazz.regionFor.keyword("class");
		val kwBrace = clazz.regionFor.keyword("{"); // autowrap-listener ?
		if( ! kwClass.lineRegions.head.contains( kwBrace ) ) {
			twolinesBeforeFirstMember.apply(IHiddenRegionFormatter.NORMAL_PRIORITY);
		} else {
			clazz.ownedMembersRaw.head.prepend[setNewLines(1,1,maxConsecutiveNewLines);autowrap;];
		}

		kwClass.append[oneSpace];

		for (member : clazz.ownedMembersRaw) {
			member.append[setNewLines(1, 1, maxConsecutiveNewLines)]
			member.format
		}

		// Collapse empty block:
		if( clazz.ownedMembersRaw.isEmpty) {
			// Empty body:
			kwBrace.append[noSpace;newLines=0];
		}
	}

	def dispatch format(N4InterfaceDeclaration interf, extension IFormattableDocument document) {
		interf.configureAnnotations(document);
		interf.configureModifiers(document);
		interf.insertSpaceInFrontOfCurlyBlockOpener(document);
		interf.indentExcludingAnnotations(document);// .interiorBUGFIX([indent],document);	//interf.interior[indent];

		interf.ownedMembersRaw.head.prepend[setNewLines(1, 1, maxConsecutiveNewLines)]
		for (member : interf.ownedMembersRaw) {
			member.append[setNewLines(1, 1, maxConsecutiveNewLines)]
			member.format
		}
	}

//	def dispatch void format(N4MemberDeclaration member, extension IFormattableDocument document) {
//		member.regionFor.keyword("(").prepend[noSpace; newLines=0].append[noSpace]
//
//		member.insertSpaceInfrontOfPropertyNames(document);
//		for (c : member.eContents) {
//			c.format;
//		}
//	}

	def dispatch void format(N4FieldDeclaration field, extension IFormattableDocument document) {
		field.configureAnnotations(document);
		field.configureModifiers(document);

		field.indentExcludingAnnotations(document);

		field.configureOptionality(document);
		field.regionFor.keyword("=").prepend[oneSpace].append[oneSpace];
		field.expression.format;
		field.declaredTypeRef.format;

		field.bogusTypeRef.format;
	}




//	def dispatch format(N4MethodDeclaration method, extension IFormattableDocument document) {
//		method.configureAnnotations(document);
//		method.insertSpaceInfrontOfPropertyNames(document);
//
//		method.regionFor.keyword("(").prepend[noSpace; newLines=0]
//
//		method.body.regionFor.keyword("{").prepend[oneSpace; newLines = 0]
//		for (child : method.eContents) {
//			child.format;
//		}
//	}
//
	def dispatch void format(FunctionExpression funE, extension IFormattableDocument document) {
		funE.configureAnnotations(document);
		funE.configureModifiers(document);
		if (funE.isArrowFunction) {
			throw new IllegalStateException("Arrow functions should be formated differently.")
		}
		funE.fpars.configureFormalParameters(document,[/*n.t.d.*/]);
		val parenPair = funE.regionFor.keywordPairs("(",")").head;
		parenPair.key.append[noSpace];
		parenPair.value.prepend[noSpace];
		funE.body.format;
	}



	def dispatch format(FunctionOrFieldAccessor fDecl, extension IFormattableDocument document) {
		fDecl.configureAnnotations(document);
		fDecl.configureModifiers(document);

		// State-keeper to avoid clashing reconfigurations if multiple auto-wraps get triggered.
		val state = new StateTrack; // use state to only trigger one change, even if called multiple times.

		// Callback to introduce an additional line in body-block.
		val (ITextSegment , IHiddenRegionFormatting , IFormattableDocument )=>void cbInsertEmptyLineInBody  = [
			if(state.shouldDoThenDone){
				fDecl.body?.statements?.head.prepend[ setNewLines(2,2,maxConsecutiveNewLines); highPriority];
			}
		];

		// Formal parameters
		switch( fDecl) {
			FunctionDefinition :
				fDecl.fpars.configureFormalParameters(document, cbInsertEmptyLineInBody )
			N4SetterDeclaration:
				fDecl.fpar.prepend[noSpace].append[noSpace] /* no autowrap for setters: cbInsertEmptyLineInBody */
		}

		// Type Variables
		switch( fDecl) {
			FunctionDeclaration :
				fDecl.formatTypeVariables(document)
		}

		// special case for accessors: get / set keywords
		if(fDecl instanceof FieldAccessor){
			fDecl.configureGetSetKeyword(document);
			fDecl.configureOptionality(document);
		}



		val parenPair = fDecl.regionFor.keywordPairs("(",")").head;
		parenPair.key.prepend[noSpace; newLines = 0].append[noSpace];
		parenPair.interior[indent];
		if( parenPair.isMultiLine && ! (fDecl instanceof FieldAccessor)) {
			// it is already a multiline, insert the newLine immediately.
			// cbInsertEmptyLineInBody.apply(null,null,null); // TODO re-think, if all will be collapsed this assumption does not hold an
		} else {
			// single line parameter block
		}
		parenPair.value.prepend[noSpace]

		for (child : fDecl.eContents) {
			child.format;
		}
	}

	/** to be used by FunctionDefintiions and SetterDeclarations */
	def void configureFormalParameters(EList<FormalParameter> list, extension IFormattableDocument document, (ITextSegment , IHiddenRegionFormatting , IFormattableDocument )=>void x){
		if( list === null ||  list.isEmpty ) return;
		list.forEach[it,idx|
			if(idx !== 0) it.prepend[oneSpace;setNewLines(0,0,1);onAutowrap=x];
			it.append[noSpace];
			it.configureAnnotationsInLine(document); // TODO maybe we need some in-line-annotation config here.
//			it.regionFor.ruleCallTo( bindingIdentifierAsFormalParameterRule  ) // feature(N4JSPackage.Literals.FORMAL_PARAMETER__NAME)
//				.prepend[oneSpace;newLines=0].append[]
			it.declaredTypeRef.format(document);
			if( it.isVariadic )
				it.regionFor.keyword("...").prepend[newLines=0;/*oneSpace;*/].append[newLines=0;noSpace];
			if( it.hasInitializerAssignment ) {
				it.regionFor.keyword("=").prepend[newLines=0;noSpace;].append[newLines=0;noSpace];
				if (it.initializer !== null) {
					it.initializer.format(document);
				}
			}
		];
	}

	/** Check if key and value are in different lines. Defined for non-overlapping Regions, e.g. Keyword-pairs.*/
	def static boolean isMultiLine(Pair<ISemanticRegion, ISemanticRegion> pair) {
		! pair.key.lineRegions.last.contains(  pair.value )
	}

//	def dispatch format(FunctionOrFieldAccessor fofAccessor, extension IFormattableDocument document) {
//		val begin = fofAccessor.body.semanticRegions.head
//		val end = fofAccessor.body.semanticRegions.last
//		if (begin?.lineRegions?.head?.contains(end?.endOffset)) {
//			// same line
//		} else {
//			// body spans multiple lines
//			begin.append[newLine;];
//			end.prepend[newLine;];
//			// fofAccessor.body.interior[indent]; // already by parenthesis?
//		}
//
//		fofAccessor.body?.format;
//
//	}

	def dispatch void format(N4EnumDeclaration enumDecl, extension IFormattableDocument document) {
		enumDecl.configureAnnotations(document);
		enumDecl.configureModifiers(document);
		enumDecl.insertSpaceInFrontOfCurlyBlockOpener(document);
		enumDecl.indentExcludingAnnotations(document);//.interiorBUGFIX([indent],document);	//enumDecl.interior[indent];
		enumDecl.configureCommas(document);

		val braces = enumDecl.regionFor.keywordPairs("{","}").head;

		val multiLine = enumDecl.isMultiline;

		enumDecl.literals.forEach[
			format;
			if( multiLine ) {
				if( it.regionForEObject.previousHiddenRegion.containsComment )
				{ // comment above
					it.prepend[newLines=2];
				} else { // no comment above
					it.prepend[newLine];
				}
			}
		];
		if( multiLine ) {
			braces.value.prepend[newLine];
		}
	}

	def dispatch void format(ParameterizedPropertyAccessExpression exp, extension IFormattableDocument document) {
		val dotKW = exp.regionFor.keyword(".");
		dotKW.prepend[noSpace; autowrap; setNewLines(0,0,1)].append[noSpace;];
		if( exp.eContainer instanceof ExpressionStatement) {
			// top-level PPA, indent one level.
			exp.interiorBUGFIX([indent],document);	//exp.interior[indent];
		}
		exp.target.format;
	}

	def dispatch void format(ParameterizedCallExpression exp, extension IFormattableDocument document) {
		// FIXME process typeArgs !!!
		val dotKW = exp.regionFor.keyword(".");
		dotKW.prepend[noSpace; autowrap;].append[noSpace;]
		exp.regionFor.keyword("(").prepend[noSpace].append[noSpace];
		exp.regionFor.keyword(")").prepend[noSpace];
		exp.configureCommas(document);

		exp.arguments.tail.forEach[prepend[oneSpace;autowrap]];
		exp.arguments.forEach[format];

		if( exp.eContainer instanceof ExpressionStatement) {
			// top-level PPA, indent one level.
			exp.interiorBUGFIX([indent],document);	//exp.interior[indent];
		}
		exp.target.format;
	}



	def dispatch void format(ImportDeclaration decl, extension IFormattableDocument document) {

		// read configuration:
		val extraSpace = getPreference(FORMAT_SURROUND_IMPORT_LIST_WITH_SPACE)

		decl.regionFor.keyword("{").prepend[oneSpace].append[if(extraSpace) oneSpace else noSpace];
		decl.regionFor.keyword("}").prepend[if(extraSpace) oneSpace else noSpace].append[oneSpace; newLines = 0];
		decl.regionFor.keyword("from").surround[oneSpace];
		decl.configureCommas(document);
		decl.eContents.forEach[format];
	}

	def dispatch void format(NamedImportSpecifier namedImp, extension IFormattableDocument document){
		namedImp.regionFor.keyword("as").prepend[oneSpace].append[oneSpace];
		namedImp.regionFor.feature(N4JSPackage.Literals.NAMESPACE_IMPORT_SPECIFIER__DECLARED_DYNAMIC).prepend[noSpace].append[oneSpace]; // "+"-KW after alias-name
	}

	def dispatch void format(NamespaceImportSpecifier nsImp, extension IFormattableDocument document){
		nsImp.regionFor.keyword("*").append[oneSpace];
		nsImp.regionFor.keyword("as").append[oneSpace];
		nsImp.regionFor.feature(N4JSPackage.Literals.NAMESPACE_IMPORT_SPECIFIER__DECLARED_DYNAMIC).prepend[noSpace].append[oneSpace]; // "+"-KW after alias-name
	}

	def dispatch void format(ExportDeclaration export, extension IFormattableDocument document){
		export.regionFor.keyword("export").append[
			oneSpace;
			newLines=0;
			// Apply prioritization to catch cases of 'trapped' annotations e.g. "export @Final public class" which
			// could also be reordered to "@Final export public class.."
			priority=PRIO_13; // Priority higher then highPriority used in AnnotationList.
		];

		export.eContents.forEach[format];

		// Fix Trapped annotations:
		val exported = export.exportedElement;
		if( exported instanceof AnnotableScriptElement ){
			val annoList = exported.annotationList;
			if( annoList !== null && !annoList.annotations.isEmpty) {
				annoList.annotations.last.append[
					newLines = 0; oneSpace; priority = PRIO_13;
				]
			}
		}
	}

	def dispatch void format(IfStatement stmt, extension IFormattableDocument document) {
		val parenPair = stmt.regionFor.keywordPairs("(",")").head;
		parenPair.interior[noSpace;indent];
		parenPair.key.prepend[oneSpace];
		parenPair.value.append[oneSpace];

		stmt.regionFor.keyword("else").prepend[autowrap;oneSpace].append[oneSpace];

		stmt.elseStmt.prepend[oneSpace; newLines = 0];

		stmt.expression.format;
		stmt.ifStmt.format;
		stmt.elseStmt.format;
	}

	def dispatch void format(SwitchStatement swStmt, extension IFormattableDocument document) {
		swStmt.insertSpaceInFrontOfCurlyBlockOpener(document);
		swStmt.interiorBUGFIX([indent],document);	//swStmt.interior[indent];
		swStmt.expression.format;
		swStmt.cases.head.prepend[newLine];
		swStmt.cases.forEach[format];
	}

	/** Formats DefaultCaseClause + CaseClause */
	def dispatch void format(AbstractCaseClause caseClause, extension IFormattableDocument document) {
		caseClause.interiorBUGFIX([indent],document);	//caseClause.interior[indent];

		if (caseClause.statements.size == 1) {
			if (caseClause.statements.head instanceof Block) {
				caseClause.statements.head.prepend[setNewLines(0,0,0)];
			} else {
				caseClause.statements.head.prepend[setNewLines(0,1,1)];
			}
		} else {
			caseClause.statements.head.prepend[setNewLines(1,1,1)];
		}

		// caseClause.regionFor.keyword(":").prepend[oneSpace]; // In case one space before the colon is desired
		caseClause.statements.forEach[format];
		caseClause.statements.forEach[append[setNewLines(1,1,maxConsecutiveNewLines)]];
		caseClause.append[setNewLines(1, 1, maxConsecutiveNewLines)];
	}

	def dispatch void format(CastExpression expr, extension IFormattableDocument document) {
		expr.regionFor.keyword("as").prepend[newLines = 0; oneSpace].append[newLines = 0; oneSpace];
		expr.expression.format;
		expr.targetTypeRef.format;
	}

	def dispatch void format(Block block, extension IFormattableDocument document) {
		if( debug ) println("Formatting block "+containmentStructure(block));

		// Beware there are blocks in the grammar, that are not surrounded by curly braces. (e.g. FunctionExpression)

		// Block not nested in other blocks usually are bodies. We want them separated by a space:
		if (! (block.eContainer instanceof Block || block.eContainer instanceof Script)) { // TODO maybe invert the control here, since the block is formatting the outside.
			block.regionFor.keyword("{").prepend[oneSpace];
		}

		block.interiorBUGFIX([indent],document);	//block.interior[indent];

		block.statements.head.prepend[setNewLines(1,1,maxConsecutiveNewLines)];
		block.statements.forEach[append[setNewLines(1,1,maxConsecutiveNewLines)]];

		block.statements.forEach[format];

		// Format empty curly blocks, necessary for comments inside:
		val braces = block.regionFor.keywordPairs("{","}").head
		if( braces !== null
			&& braces.key.nextSemanticRegion == braces.value
		) {
			// empty block:
			if( braces.key.nextHiddenRegion.containsComment ) {
				braces.key.append[setNewLines(1,1,maxConsecutiveNewLines)];
			} else {
				braces.key.append[newLines=0;noSpace];
			}
		}
	}


	def dispatch void format(ReturnStatement ret, extension IFormattableDocument document) {
		ret.interiorBUGFIX([indent],document);	//ret.interior[indent;]
		ret.expression.prepend[oneSpace; newLines = 0];
		ret.expression.format;
	}

	def dispatch void format(AdditiveExpression add, extension IFormattableDocument document) {
		add.regionFor.feature(N4JSPackage.Literals.ADDITIVE_EXPRESSION__OP).surround[oneSpace].append[autowrap];
		add.lhs.format
		add.rhs.format
	}

	def dispatch void format(MultiplicativeExpression mul, extension IFormattableDocument document) {
		mul.regionFor.feature(N4JSPackage.Literals.MULTIPLICATIVE_EXPRESSION__OP).surround[oneSpace].append[autowrap];
		mul.lhs.format
		mul.rhs.format
	}

	def dispatch void format(BinaryBitwiseExpression binbit, extension IFormattableDocument document) {
		binbit.regionFor.feature(N4JSPackage.Literals.BINARY_BITWISE_EXPRESSION__OP).surround[oneSpace];
		binbit.lhs.format
		binbit.rhs.format
	}

	def dispatch void format(BinaryLogicalExpression binLog, extension IFormattableDocument document) {
		val opReg = binLog.regionFor.feature(N4JSPackage.Literals.BINARY_LOGICAL_EXPRESSION__OP);
		opReg.surround[oneSpace];
		binLog.lhs.format
		binLog.rhs.format
		// auto-wrap:
		val autoWrapInFront = getPreference(FORMAT_AUTO_WRAP_IN_FRONT_OF_LOGICAL_OPERATOR);
		if( autoWrapInFront ) {
			opReg.prepend[autowrap; lowPriority; setNewLines(0,0,1);]
		} else {
			opReg.append[autowrap; lowPriority; setNewLines(0,0,1);]
		};
	}

	def dispatch void format(EqualityExpression eqExpr, extension IFormattableDocument document) {
		eqExpr.regionFor.feature(N4JSPackage.Literals.EQUALITY_EXPRESSION__OP).surround[oneSpace].append[autowrap];
		eqExpr.lhs.format
		eqExpr.rhs.format
	}

	def dispatch void format(RelationalExpression relExpr, extension IFormattableDocument document) {
		relExpr.regionFor.feature(N4JSPackage.Literals.RELATIONAL_EXPRESSION__OP).surround[oneSpace].append[autowrap];
		relExpr.lhs.format
		relExpr.rhs.format
	}

	def dispatch void format(ShiftExpression shiftExpr, extension IFormattableDocument document) {
		shiftExpr.regionFor.feature(N4JSPackage.Literals.SHIFT_EXPRESSION__OP).surround[oneSpace].append[autowrap];
		shiftExpr.lhs.format
		shiftExpr.rhs.format
	}

	def dispatch void format(CommaExpression comma, extension IFormattableDocument document) {
		comma.configureCommas(document);
		comma.eContents.forEach[format];
	}

	def dispatch void format(ConditionalExpression cond, extension IFormattableDocument document) {
		cond.regionFor.keyword("?").surround[oneSpace].append[autowrap; lowPriority; setNewLines(0,0,1);];
		cond.regionFor.keyword(":").surround[oneSpace].append[autowrap; lowPriority; setNewLines(0,0,1);];
		cond.expression.format;
		cond.trueExpression.format;
		cond.falseExpression.format;
	}

	def dispatch void format(AwaitExpression await, extension IFormattableDocument document) {
		await.regionFor.keyword("await").prepend[oneSpace].append[oneSpace; newLines = 0];
		await.expression.format
	}

	def dispatch void format(PromisifyExpression promify, extension IFormattableDocument document) {
		promify.noSpaceAfterAT(document);
		promify.regionFor.keyword("Promisify").append[oneSpace];
		promify.expression.format
	}

	def dispatch void format(IndexedAccessExpression idxAcc, extension IFormattableDocument document) {
		val indexRegion = idxAcc.index.regionForEObject();
		indexRegion.previousSemanticRegion.prepend[noSpace;newLines=0].append[noSpace;newLines = 0];
		indexRegion.nextSemanticRegion.prepend[noSpace];

		idxAcc.index.format;
		idxAcc.target.format;
	}

	def dispatch void format(NewExpression newExp, extension IFormattableDocument document) {
		newExp.regionFor.keyword("new").prepend[oneSpace].append[oneSpace;newLines=0];
		newExp.callee.format;
		// Watch out, commas are used in Type-args and in argument list ! If necessary distinguish by offset.
		val commas = newExp.regionFor.keyword(",");
		commas.prepend[noSpace].append[oneSpace];

		// TODO maybe factor out TypeArgs formatting.
		val typeArgsAngle = newExp.regionFor.keywordPairs("<",">").head;
		if( typeArgsAngle !== null ) {
			typeArgsAngle.key.append[noSpace;newLines=0].prepend[noSpace;newLines=0];
			typeArgsAngle.value.prepend[noSpace;newLines=0];
		}
		newExp.typeArgs.forEach[format];


		if( newExp.isWithArgs ) {
			val argParen = newExp.regionFor.keywordPairs("(",")").head;
			argParen.key.prepend[newLines=0;noSpace].append[noSpace];
			argParen.value.prepend[noSpace];
			newExp.arguments.forEach[format];
		}
	}

	def dispatch void format(PostfixExpression postFix, extension IFormattableDocument document) {
		// no line break allowed between Expression and operator !
		postFix.regionFor.feature(N4JSPackage.Literals.POSTFIX_EXPRESSION__OP)
			.prepend[newLines=0;noSpace;].append[oneSpace;lowPriority]; // giving low priority for situations of closing parenthesis: "(a++)"
		postFix.expression.format;
	}

	def dispatch void format(TaggedTemplateString taggedTemplate, extension IFormattableDocument document) {
		taggedTemplate.regionFor.feature(N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET).append[ newLines = 0; oneSpace ];
		taggedTemplate.target.format;
		taggedTemplate.template.format;
	}

	def dispatch void format(UnaryExpression unaryExpr, extension IFormattableDocument document) {
		// The operators 'void' 'delete' and 'typeof' must be separated from operand.
		val boolean requireSpace=(unaryExpr.op.ordinal <= UnaryOperator.TYPEOF_VALUE);
		unaryExpr.regionFor.feature(N4JSPackage.Literals.UNARY_EXPRESSION__OP)
			.append[if(requireSpace) oneSpace else noSpace; newLines = 0;];
		unaryExpr.expression.format;
	}

	def dispatch void format(YieldExpression yieldExpr, extension IFormattableDocument document) {
		// " yield " or " yield* "
		yieldExpr.regionFor.keyword("yield")
			.prepend[oneSpace;]
			.append[if( yieldExpr.isMany ) noSpace else oneSpace];
		if( yieldExpr.isMany ){
			yieldExpr.regionFor.keyword("*").prepend[noSpace;newLines=0].append[oneSpace]
		}
		yieldExpr.expression.format;
	}

	def dispatch void format(ParenExpression parenE, extension IFormattableDocument document) {
		parenE.semanticRegions.head.append[noSpace;newLines=0;autowrap];
		parenE.semanticRegions.last.prepend[noSpace;newLines=0;autowrap];
		parenE.interiorBUGFIX([indent],document);	//parenE.interior[indent];
		parenE.expression.format;
	}

	def dispatch void format(ArrowFunction arrowF, extension IFormattableDocument document) {
		arrowF.configureCommas(document);
		arrowF.regionFor.keyword("=>").surround[oneSpace];
		arrowF.regionFor.keywordPairs("(",")").head?.interior[noSpace];
		// too lax: arrowF.fpars.configureFormalParameters(document,[/*NTD*/]);

		if( arrowF.isHasBracesAroundBody ) {
			// format body as block. NOTE: this block differs from other blocks, since the curly braces are defined in the ArrowExpression.
			// special handling of indentation in inside the braces.
			val bracesPair = arrowF.regionFor.keywordPairs("{","}").head;
			bracesPair.interior[indent];
			if( bracesPair.key.lineRegions.last.contains( bracesPair.value) // one line '{ do; stuff; }'
				|| bracesPair.key.lineRegions.last.contains( bracesPair.key.nextSemanticRegion ) // no line-break after braces e.g. '{ do; \n stuff; }'
			) {
				// one line
				arrowF.body?.statements.forEach[ it,idx|
					format; if( idx !==0 ) {it.prepend[oneSpace; autowrap; newLines=0;]}
				];
				bracesPair.key.append[oneSpace]; // do not autowrap after "{" to keep wrap-semantic
				bracesPair.value.prepend[oneSpace];
			} else {
				// multi-line
				if( arrowF.body !== null && !arrowF.body.statements.empty ) {
					arrowF.body?.statements.head.prepend[newLines=1;];
					arrowF.body?.statements.forEach[format;append[newLines=1]];
				} else {
					// empty block, squash interior.
					bracesPair.key.append[noSpace;newLines=1;];
					bracesPair.value.prepend[noSpace;newLines=1];
				}
			}
		} else {
			// no braces Around the implicit return statement.
			arrowF.body?.statements.head.format;
		}
	}


	def dispatch void format(ArrayLiteral al, extension IFormattableDocument document) {
		val bracketPair = al.regionFor.keywordPairs("[","]").head;
		bracketPair.interior[indent];
		val sameLine = bracketPair.key.lineRegions.head.contains( bracketPair.value );
		// auto wrap in front of AL-Elements, to preserve comma at end.
		if( ! sameLine) {
			al.elements.last.append[autowrap];
			al.elements.forEach[it,num|prepend[autowrap;setNewLines(0,0,1);if(num!==0)oneSpace;].append[noSpace]];
			// format last bracket if in single.line.
			if( ! bracketPair.value.previousSemanticRegion.lineRegions.last.contains( bracketPair.value ) ) {
				bracketPair.value.prepend[newLine];
			}
		} else {
			al.elements.forEach[it,num|prepend[autowrap;if(num!==0)oneSpace;]];
		}
	}

	def dispatch void format(ObjectLiteral ol, extension IFormattableDocument document) {
		ol.configureCommas(document);

		val bracePair = ol.regionFor.keywordPairs("{","}").head;
		bracePair.interior[indent];


		// Decide on multiline or not.
		// Rule: if opening brace is preceded by a line break, then go multiline.
		val sameLine = bracePair.key.lineRegions.head.contains( bracePair.key.nextSemanticRegion.lineRegions.head );
		// OLD: val sameLine = bracePair.key.lineRegions.head.contains( bracePair.value );
		if( ! sameLine) {
			bracePair.value.prepend[newLine]; // format WS in front of closing brace
			ol.propertyAssignments.forEach[it,num|prepend[newLine]];
			if( bracePair.key.nextSemanticRegion == bracePair.value ) {
				// empty multiline, trigger formatting:
				bracePair.key.append[newLine];
			}
		} else { // in one line
			bracePair.key.append[newLines=0];
			ol.propertyAssignments.forEach[it,num|prepend[newLines=0;if(num!==0) { autowrap; oneSpace; } else {noSpace;}]];
			bracePair.value.prepend[newLines=0; noSpace; lowPriority]; // low priority to avoid conflict with dangling commas
		}

		ol.eContents.forEach[format];
	}

	def dispatch void format( ForStatement fst, extension IFormattableDocument document){

		fst.regionFor.keyword("for").append[oneSpace;newLines=0; autowrap];

		val parenPair = fst.regionFor.keywordPairs("(",")").head;
		parenPair.key.append[noSpace;autowrap;newLines=0];
		parenPair.value.prepend[noSpace;newLines=0].append[oneSpace;newLines=0;autowrap;];

		fst.regionFor.keywords("in","of").forEach[ it.surround[oneSpace; newLines=0; autowrap] ];
		fst.regionFor.keywords(";").forEach[it.prepend[noSpace;newLines=0;].append[oneSpace;newLines=0;autowrap]];

		fst.eContents.forEach[format];
	}

	def dispatch void format(TemplateLiteral tl, extension IFormattableDocument document) {
		tl.interiorBUGFIX([indent],document);	//tl.interior[indent;];
		tl.segments.forEach[
			switch(it) {
				TemplateSegment: noOp
				default: it.surround[oneSpace; autowrap;]
			};
			it.format;
		];
	}
	private def noOp (){}

	def dispatch void format(TemplateSegment tl, extension IFormattableDocument document) {
		// just leave as is.
	}

	def dispatch void format(TypeVariable tv, extension IFormattableDocument document) {
		// "out"
		if( tv.declaredCovariant ) { tv.regionFor.feature(TypesPackage.Literals.TYPE_VARIABLE__DECLARED_COVARIANT).append[oneSpace]; }
		// "in"
		if( tv.declaredContravariant ) {tv.regionFor.feature(TypesPackage.Literals.TYPE_VARIABLE__DECLARED_CONTRAVARIANT).append[oneSpace];}

		if( tv.declaredUpperBound!==null ) {
			// "extends"
			tv.regionFor.keyword("extends").surround[oneSpace];
			val upperBound = tv.declaredUpperBound;
			upperBound.immediatelyFollowing.keyword("&").surround[oneSpace];
			upperBound.format(document);
		}

	}



	def dispatch void format(Expression exp, extension IFormattableDocument document) {
		switch(exp) {
			// Things not to format:
			BooleanLiteral,
			IdentifierRef,
			IntLiteral,
			NullLiteral,
			NumberLiteral,
			RegularExpressionLiteral,
			StringLiteral,
			ThisLiteral,
			SuperLiteral,
			JSXElement
			: return
		}
		throw new UnsupportedOperationException("expression "+exp.class.simpleName+" not yet implemented.");
	}

		/** simply formats all content */
	def void genericFormat(Expression exp, extension IFormattableDocument document) {
		exp.eContents.forEach[format];
	}


	def dispatch void format(AssignmentExpression ass, extension IFormattableDocument document) {
		ass.lhs.append[oneSpace]
		ass.rhs.prepend[oneSpace]
		ass.lhs.format;
		ass.rhs.format;
	}

	def dispatch void format( ExpressionStatement eStmt, extension IFormattableDocument docuemt){
		eStmt.expression.format;
	}

	/** var,let,const  */
	def dispatch void format(VariableStatement vStmt, extension IFormattableDocument document) {

		 // ExportedVariableStatements:
		if( vStmt instanceof ModifiableElement) vStmt.configureModifiers(document);


		vStmt.regionFor.feature(
			N4JSPackage.Literals.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD).append [
			oneSpace;
		]; // "let", "var" or "const"

		vStmt.configureCommas(document);

		vStmt.interiorBUGFIX([indent],document);	//vStmt.interior[indent];
		val lastIdx = vStmt.varDeclsOrBindings.size - 1;

		vStmt.varDeclsOrBindings.forEach [ e, int i |
			e.format;
			if (i > 0) { // assignments start in separate lines.
				if (e instanceof VariableDeclaration) {
					if (e.expression !== null) e.prepend[newLine]
					else e.prepend[setNewLines(0,1,1); lowPriority];
				} else if (e instanceof VariableBinding) {
					if (e.expression !== null) e.prepend[newLine]
					else e.prepend[setNewLines(0,1,1); lowPriority];
				}
			}
			if (i < lastIdx) { // assignments start let following continue in separate lines.
				if (e instanceof VariableDeclaration) {
					if (e.expression !== null) e.immediatelyFollowing.keyword(",").append[newLine]
					else e.prepend[setNewLines(0,1,1); lowPriority];
				} else if (e instanceof VariableBinding) {
					if (e.expression !== null) e.immediatelyFollowing.keyword(",").append[newLine]
					else e.prepend[setNewLines(0,1,1); lowPriority];
				}

			}
		];
	}

	def dispatch void format(VariableDeclaration vDecl, extension IFormattableDocument document) {
		vDecl.previousHiddenRegion.set[oneSpace];
		vDecl.regionFor.keyword("=").surround[oneSpace];
		vDecl.expression.format;
		vDecl.declaredTypeRef.format;
	}

	def dispatch void format(VariableBinding vBind, extension IFormattableDocument document) {
		vBind.previousHiddenRegion.set[oneSpace];
		vBind.regionFor.keyword("=").surround[oneSpace];
		vBind.pattern.format;
		vBind.expression.format;
		vBind.pattern.format;
	}

	def dispatch void format( BindingPattern bp, extension IFormattableDocument document) {
		// ObjectBindingPattern
		// ArrayBindingPattern

		// '{' or '['
		bp.semanticRegions.head.append[noSpace;newLines=0;autowrap];
		bp.semanticRegions.last.prepend[noSpace;newLines=0;autowrap];
		bp.configureCommas(document); // doesn't handle elision.

		bp.eContents.forEach[format];
	}


	def dispatch void format(ThrowStatement thrStmt, extension IFormattableDocument document) {
		thrStmt.expression.prepend[setNewLines(0, 0, 0); oneSpace]; // No autowrap, otherwise ASI
		thrStmt.expression.format;
	}


	def dispatch void format(CatchBlock ctch, extension IFormattableDocument document) {
		ctch.prepend[setNewLines(0, 0, 0); oneSpace];
		ctch.catchVariable.format;
		ctch.block.format;
	}

	def dispatch void format(FinallyBlock finlly, extension IFormattableDocument document) {
		finlly.previousHiddenRegion.set[newLines = 0; oneSpace];
		finlly.block.format;
	}

	/** Insert one space in front of first '{' in the direct content of the element.
	 * semEObject is a semanticObject, e.g. N4EnumDecl, N4Classifier ...*/
	private def void insertSpaceInFrontOfCurlyBlockOpener(EObject semEObject, extension IFormattableDocument document) {
		semEObject.regionFor.keyword("{").prepend[oneSpace];
	}

	/** force: " @" and no newLine after '@' */
	private def void noSpaceAfterAT(EObject semEObject, extension IFormattableDocument document) {
		semEObject.regionFor.keyword("@").append[noSpace;newLines=0].prepend[oneSpace];
	}

	/** On the direct level of an semantic Object enforce commas to ", " with autoWrap option. */
	private def void configureCommas(EObject semEObject, extension IFormattableDocument document) {
		semEObject.regionFor.keywords(",").forEach [
			prepend[noSpace];
			append[oneSpace; autowrap];
		];
	}

	def void indentExcludingAnnotations(EObject semObject, extension IFormattableDocument document) {
		//Exclude Annotations from indentation	field.interior[indent];
		val begin = semObject.semanticRegions.findFirst[!(semanticElement instanceof Annotation)];
		val end = semObject.semanticRegions.last;
		if( begin !== end )	{ // guard to prevent wrong indentation
			interior(begin,end,[indent]);
		}
	}


	private def dispatch void configureAnnotations(AnnotableN4MemberDeclaration semEObject, extension IFormattableDocument document) {
		configureAnnotations( semEObject.annotationList, document );
	}

	private def dispatch void configureAnnotations(AnnotablePropertyAssignment semEObject, extension IFormattableDocument document) {
		configureAnnotations( semEObject.annotationList, document );
	}

	private def dispatch void configureAnnotations(AnnotableScriptElement semEObject, extension IFormattableDocument document) {
		configureAnnotations( semEObject.annotationList, document );
	}

	private def dispatch void configureAnnotations(AnnotableExpression semEObject, extension IFormattableDocument document) {
		configureAnnotations( semEObject.annotationList, document );
	}

	private def dispatch void configureAnnotations(AbstractAnnotationList aList, extension IFormattableDocument document) {
		if( aList === null || aList.annotations.isEmpty ) return;

		aList.prepend[setNewLines(2,2,2);highPriority]; // TODO in case of trapped in Annotation like 'export @Final public class A{}' - a reorder would be necessary (see format for export)
		aList.append[newLine]; // TODO special annotations like @Internal ? --> together with public, reorder to be in same line?
		aList.annotations.forEach[it, idx |
			it.configureAnnotation(document,true,idx ===0);
		];
	}

	/**
	 *
	 * @param withLineWraps  <code>true</code> do line-wrapping
	 * @param isFirstenAnnotation if this is the first annotation in a sequence ( used with line-wrapping in front of '@')
	 */
	private def configureAnnotation(Annotation it, extension IFormattableDocument document, boolean withLineWraps, boolean isFirstAnnotation ){
			// configure arguments
			val parens = it.regionFor.keywordPairs("(",")").head;
			if( parens !== null ) {
				parens=>[
					it.key.prepend[noSpace].append[noSpace];
					it.value.prepend[noSpace].append[if( withLineWraps ) {noSpace; newLines=1;} else {oneSpace; newLines=0;}];
					it.interior[indent];
					// line break before "@":
					if( withLineWraps && !isFirstAnnotation ) {
						it.key.previousSemanticRegion.previousSemanticRegion.prepend[newLines = 1];
					}
				];
				it.configureCommas(document);
			}

			// Configure @-Syntax
			// Special case here: for "@XY" we can either get "@" or "XY" as the first semantic element
			it.semanticRegions.head =>[
				if( it.grammarElement instanceof Keyword) {
					// assume '@'
					it.append[ noSpace; newLines=0	];
				} else {
					it.prepend[ // for "@Final" "Final" will be the first semantic region in case of exported classes,
						noSpace; newLines=0
					];
				}
			];
	}

	private def dispatch void configureAnnotations(Object semEObject, extension IFormattableDocument document) {
		// no annotations to be configured.
	}

	private def dispatch void configureAnnotations(Void x, extension IFormattableDocument document) {
		// no annotations to be configured.
	}

	private def void configureAnnotationsInLine(FormalParameter fpar, extension IFormattableDocument document) {
		if( fpar.annotations.isEmpty ) return;
		// (@x @y("") bogus a:typ)
		fpar.annotations.head=>[
			it.configureAnnotation(document,false,true);
			prepend[noSpace;newLines=0;autowrap;];
		]
		fpar.annotations.tail.forEach[
			configureAnnotation(document,false,false);
			prepend[oneSpace;newLines=0;autowrap;];
		]
		fpar.annotations.last.append[oneSpace;newLines=0;autowrap;];
	}


	/** only script-level annotations '@@' */
	private def void formatScriptAnnotations(Script script, extension IFormattableDocument document) {
		if( script.annotations.isEmpty ) return;

		if (script.annotations.head.previousHiddenRegion.containsComment) {
			script.annotations.head.prepend[noSpace;];
		} else {
			script.annotations.head.prepend[noSpace;newLines=0;];
		}
		script.annotations.last.append[setNewLines(2,2,2)];

		script.annotations.forEach[it,idx|
			if( idx !==0 ) it.prepend[newLines=1;noSpace];
			it.semanticRegions.head=>[ // its an '@@'
				append[noSpace;newLines=0]
			]
		]

	}




	public override ITextReplacer createCommentReplacer(IComment comment) {
		// Overridden to distinguish between JSDOC-style, standard ML, formatter-off ML-comment.
		val EObject grammarElement = comment.getGrammarElement();
		if (grammarElement instanceof AbstractRule) {
			val String ruleName = (/*(AbstractRule)*/ grammarElement).getName();
			if (ruleName.startsWith("ML")) {
				val cText = comment.text;
				if (cText.startsWith("/**") && !cText.startsWith("/***")) { // JSDOC
					return new N4MultilineCommentReplacer(comment, '*');
				} else if (cText.startsWith("/*-")) { // Turn-off formatting.
					return new OffMultilineCommentReplacer(comment, !comment.isNotFirstInLine);
				} else { // All other
					return new FixedMultilineCommentReplacer(comment);
				}
			}
			if (ruleName.startsWith("SL")) {
				if (comment.isNotFirstInLine) {
					return new SinglelineDocCommentReplacer(comment, "//");
				} else {
					return new SinglelineCodeCommentReplacer(comment, "//");
				}
			}
		}

		// fall back to super-impl.
		super.createCommentReplacer(comment);
	}

	private static def boolean isNotFirstInLine(IComment comment) {
		val lineRegion = comment.getLineRegions().get(0);

		return !comment.contains( lineRegion.offset );
	}


	public override createTextReplacerMerger(){
		return new IndentHandlingTextReplaceMerger(this);
	}

	/** DEBUG-helper */
	private def static String containmentStructure(EObject eo) {
		val name = eo.class.simpleName;
		if( eo.eContainer !== null )
		return '''«eo.eContainer.containmentStructure».«eo.eContainingFeature.name»-> «name»'''
		return name
	}

	/** HELPER to avoid Warnings in code, since @SuppressWarnings("unused") is not active in xtend code.*/
	def suppressUnusedWarnings(Object ... e)
	{
		PRIO_4;
	}

	/**
	 * Simple tracker that only gives exactly one time the value {@code true}
	 * when calling {@link StateTrack#shouldDoThenDone()}
	 */
	private final static class StateTrack {
		private boolean done=false;

		/**
		 * This method returns {@code true} exactly on it's first invocation. Proceeding calls always return {@code false}.
		 *
		 * @return Returns {@code true} if not done, immediately switches {@link #done} to {@code true};
		 * returns {@code false} if already done.
		 */
		def boolean shouldDoThenDone(){
			val ret = !done;
			done = true;
			return ret;
		}
	}


	/****************************************************************************************************************
	 *
	 * Type Expression
	 *
	 ***************************************************************************************************************/
	def dispatch void format(UnionTypeExpression ute, extension IFormattableDocument document) {
		ute.regionFor.keywords("|").forEach[ surround[oneSpace;newLines=0].prepend[autowrap;highPriority] ];
		ute.typeRefs.forEach[format];
		// OLD syntax:
		val kwUnion = ute.regionFor.keyword("union");
		if( kwUnion !== null ) {
			kwUnion.prepend[oneSpace].append[oneSpace;newLines=0].nextSemanticRegion/*'{'*/.append[oneSpace;newLines=0];
			ute.semanticRegions.last/*'}'*/.prepend[oneSpace;newLines=0];
		}
	}

	def dispatch void format(IntersectionTypeExpression ite, extension IFormattableDocument document) {
		ite.regionFor.keywords("&").forEach[surround[oneSpace;newLines=0].prepend[autowrap;highPriority]];
		ite.typeRefs.forEach[format];
		// OLD syntax
		val kwInersection = ite.regionFor.keyword("intersection");
		if( kwInersection !== null ) {
			kwInersection.prepend[oneSpace].append[oneSpace;newLines=0].nextSemanticRegion/*'{'*/.append[oneSpace;newLines=0];
			ite.semanticRegions.last/*'}'*/.prepend[oneSpace;newLines=0];
		}
	}

	def dispatch void format( TStructMember tsm, extension IFormattableDocument document) {
		if(tsm instanceof TField) {
			tsm.configureOptionality(document);
		} else if(tsm instanceof org.eclipse.n4js.ts.types.FieldAccessor) {
			tsm.configureGetSetKeyword(document);
			tsm.configureOptionality(document);

			val parenPair = tsm.regionFor.keywordPairs("(",")").head;
			parenPair.key.prepend[noSpace; newLines = 0].append[noSpace];
		}
		// get, set, method, field
		tsm.eContents.forEach[format;];
		// TODO format TStruct* more thoroughly
		// (note: added some TStructMember formatting while working on IDE-2405, but it is still incomplete!)
	}



	def private void configureUndefModifier( StaticBaseTypeRef sbtr,  extension IFormattableDocument document){
		// UndefModifier "?"
		sbtr.regionFor.feature(TypeRefsPackage.Literals.TYPE_REF__FOLLOWED_BY_QUESTION_MARK).prepend[noSpace;newLines=0;];
	}

	def dispatch void format( ThisTypeRef ttr, extension IFormattableDocument document) {
		ttr.configureUndefModifier(document);
		if( ttr instanceof ThisTypeRefStructural) {
			ttr.interiorBUGFIX([indent],document)
			configureStructuralAdditions(ttr,document);
			ttr.eContents.forEach[
				format;
			]
		}
	}

	def dispatch void format( ParameterizedTypeRef ptr, extension IFormattableDocument document) {
		ptr.interiorBUGFIX([indent],document);	//ptr.interior[indent];
		ptr.configureUndefModifier(document);

		// Union / Intersection
		ptr.regionFor.keywords("&","|").forEach[ surround[oneSpace;newLines=0].append[autowrap;highPriority]];
		// Short-Hand Syntax for Arrays
		if( ptr.isArrayTypeExpression ) {
			ptr.regionFor.keyword("[").append[noSpace];
			ptr.regionFor.keyword("]").append[noSpace];
		}
		// Short-Hand Syntax for IterableN
		if( ptr.isIterableTypeExpression ) {
			ptr.regionFor.keyword("[").append[noSpace];
			ptr.regionFor.keyword("]").append[noSpace];
		}
		ptr.formatTypeArguments(document);

		// ParameterizedTypeRefStructural :
		configureStructuralAdditions(ptr,document);

		// generically format content:
		ptr.eContents.forEach[format]
	}

	/** used for "~X with {}" except for the 'X' part. */
	def void configureStructuralAdditions( TypeRef ptr, extension IFormattableDocument document) {
		val semRegTypingStrategy = ptr.regionFor.ruleCallTo(typingStrategyUseSiteOperatorRule);
		if( semRegTypingStrategy!== null) {
			semRegTypingStrategy.prepend[oneSpace].append[noSpace;newLines=0;];

			// declaredType
			semRegTypingStrategy.nextSemanticRegion.append[];

			val kwWith = ptr.regionFor.keyword("with");
			if( kwWith !== null ) {
				kwWith.surround[oneSpace;newLines=0;autowrap];
				val bracesPair = ptr.regionFor.keywordPairs("{","}").head;
				bracesPair.key.append[noSpace;newLines=0;autowrap];
				bracesPair.value.prepend[noSpace;newLines=0;autowrap];
				//ptr.regionFor.keywords(",",";").forEach[ prepend[noSpace;newLines=0].append[oneSpace;newLines=0;autowrap] ]
				ptr.regionFor.keywords(";").forEach[ prepend[noSpace;newLines=0].append[oneSpace;newLines=0;autowrap;lowPriority] ]
				((ptr as StructuralTypeRef).astStructuralMembers.tail
					.forEach[ regionForEObject.previousHiddenRegion.set[oneSpace;newLines=0;autowrap]]
				);
			}
		}
	}

	/** formats type argument section including outside border. */
	def void formatTypeArguments(ParameterizedTypeRef semObject, extension IFormattableDocument document) {
		if( semObject.typeArgs.isEmpty ) return;
		// to "<":
		semObject.regionFor.keyword("<").append[noSpace].prepend[noSpace; newLines=0; lowPriority];
		semObject.regionFor.keyword(">").prepend[noSpace].append[noSpace; newLines=0; lowPriority];
		for( typeArg: semObject.typeArgs ){
			typeArg.append[noSpace].immediatelyFollowing.keyword(",").append[oneSpace];
			typeArg.format(document);
		}

	}



	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Configure Methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	def private void configureGetSetKeyword(FieldAccessor fieldAccessor, extension IFormattableDocument document) {
		val kw = if(fieldAccessor instanceof GetterDeclaration) "get" else "set";
		fieldAccessor.regionFor.keyword(kw).prepend[oneSpace].append[oneSpace; newLines=0; autowrap];
	}
	def private void configureGetSetKeyword(org.eclipse.n4js.ts.types.FieldAccessor tFieldAccessor, extension IFormattableDocument document) {
		val kw = if(tFieldAccessor instanceof TGetter) "get" else "set";
		tFieldAccessor.regionFor.keyword(kw).prepend[oneSpace].append[oneSpace; newLines=0; autowrap];
	}

	def private void configureOptionality(N4FieldDeclaration fieldDecl, extension IFormattableDocument document) {
		fieldDecl.regionFor.feature(N4JSPackage.Literals.N4_FIELD_DECLARATION__DECLARED_OPTIONAL).prepend[noSpace;newLines=0;];
	}
	def private void configureOptionality(FieldAccessor fieldAccessor, extension IFormattableDocument document) {
		fieldAccessor.regionFor.feature(N4JSPackage.Literals.FIELD_ACCESSOR__DECLARED_OPTIONAL).prepend[noSpace;newLines=0;];
	}
	def private void configureOptionality(TField tField, extension IFormattableDocument document) {
		tField.regionFor.feature(TypesPackage.Literals.TFIELD__OPTIONAL).prepend[noSpace;newLines=0;];
	}
	def private void configureOptionality(org.eclipse.n4js.ts.types.FieldAccessor tFieldAccessor, extension IFormattableDocument document) {
		tFieldAccessor.regionFor.feature(TypesPackage.Literals.FIELD_ACCESSOR__OPTIONAL).prepend[noSpace;newLines=0;];
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Bug-workarounds
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/** Temporarily used method to replace document.interior(EObject, Procedure1) to prevent wrong indentations.
	 *
	 * Main pattern replace document-extension method call:
	 * <pre>
	 * object.interior[indent]
	 * </pre>
	 * by
	 * <pre>
	 * object.interiorBUGFIX([indent],document);
	 * <pre>
	 *
	 */
	def void interiorBUGFIX(EObject object, Procedure1<? super IHiddenRegionFormatter> init,IFormattableDocument document ){
		val IEObjectRegion objRegion = getTextRegionAccess().regionForEObject(object);
		if (objRegion !== null) {
			val IHiddenRegion previous = objRegion.getPreviousHiddenRegion();
			val IHiddenRegion next = objRegion.getNextHiddenRegion();
			if (previous !== null && next !== null && previous != next) {
				val nsr = previous.getNextSemanticRegion();
				val psr = next.getPreviousSemanticRegion();
				if( nsr != psr )
				{ // standard case
					document.interior(nsr, psr , init);
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


	/** Dummy method to prevent accidentally calling interior - extension method form document. You should call interiorBUGFIX instead ! */
	def Procedure1<? super IFormattableDocument> interior( EObject eo, Procedure1<? super IHiddenRegionFormatter> init ){
		throw new IllegalStateException("Method should not be called.")
	}

}
