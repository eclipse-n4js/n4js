/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
parser grammar InternalN4JSParser;

options {
	tokenVocab=InternalN4JSLexer;
	superClass=AbstractInternalHighlightingAntlrParser;
}

@header {
package org.eclipse.n4js.ui.editor.syntaxcoloring;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ui.editor.syntaxcoloring.AbstractInternalHighlightingAntlrParser;
}

@members {

 	private N4JSGrammarAccess grammarAccess;

    public InternalN4JSParser(TokenStream input, N4JSGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
    }

	protected boolean forcedRewind(int marker) { return true; } // overridden in subtype
	protected void promoteEOL() {} // overridden in subtype
	protected boolean hasDisallowedEOL() { return false; } // overridden in subtype
	protected boolean isTypeRefNoTrailingLineBreak() { return true; } // overridden in subtype
	protected void setInRegularExpression() {} // overridden in subtype
	protected void setInTemplateSegment() {} // overridden in subtype
	protected void announce(Token token, AbstractElement element) {} // overridden in subtype
	protected void announce(Token start, Token stop, AbstractElement element) {} // overridden in subtype
}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
    }
}

// Entry rule entryRuleScript
entryRuleScript
	:
	ruleScript
	EOF;

// Rule Script
ruleScript
@init {
}:
(
	(
		(
			(
				AnnotationsScriptAnnotationParserRuleCall_1_0_0=ruleScriptAnnotation{
					announce($AnnotationsScriptAnnotationParserRuleCall_1_0_0.start, $AnnotationsScriptAnnotationParserRuleCall_1_0_0.stop, grammarAccess.getScriptAccess().getAnnotationsAssignment_1_0());
				}
			)
		)
		    |
		(
			(
				ScriptElementsScriptElementParserRuleCall_1_1_0=ruleScriptElement{
					announce($ScriptElementsScriptElementParserRuleCall_1_1_0.start, $ScriptElementsScriptElementParserRuleCall_1_1_0.stop, grammarAccess.getScriptAccess().getScriptElementsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleScriptElement
entryRuleScriptElement
	:
	ruleScriptElement
	EOF;

// Rule ScriptElement
ruleScriptElement
@init {
}:
(
	(
		((
			CommercialAt
			(
				This
				    |
				Target
				    |
				RULE_IDENTIFIER
			)
		)
		)=>
		AnnotatedScriptElementParserRuleCall_0=ruleAnnotatedScriptElement{ announce($AnnotatedScriptElementParserRuleCall_0.start, $AnnotatedScriptElementParserRuleCall_0.stop, grammarAccess.getScriptElementAccess().getAnnotatedScriptElementParserRuleCall_0()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Class
			(
				(
					ruleTypingStrategyDefSiteOperator
				)
			)?
			(
				(
					ruleBindingIdentifier
				)
			)?
			(
				ruleVersionDeclaration
			)?
		)
		)=>
		N4ClassDeclarationParserRuleCall_1=ruleN4ClassDeclaration{ announce($N4ClassDeclarationParserRuleCall_1.start, $N4ClassDeclarationParserRuleCall_1.stop, grammarAccess.getScriptElementAccess().getN4ClassDeclarationParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Interface
			(
				(
					ruleTypingStrategyDefSiteOperator
				)
			)?
			(
				(
					ruleBindingIdentifier
				)
			)?
			(
				ruleVersionDeclaration
			)?
		)
		)=>
		N4InterfaceDeclarationParserRuleCall_2=ruleN4InterfaceDeclaration{ announce($N4InterfaceDeclarationParserRuleCall_2.start, $N4InterfaceDeclarationParserRuleCall_2.stop, grammarAccess.getScriptElementAccess().getN4InterfaceDeclarationParserRuleCall_2()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Enum
			(
				(
					ruleBindingIdentifier
				)
			)?
			(
				ruleVersionDeclaration
			)?
		)
		)=>
		N4EnumDeclarationParserRuleCall_3=ruleN4EnumDeclaration{ announce($N4EnumDeclarationParserRuleCall_3.start, $N4EnumDeclarationParserRuleCall_3.stop, grammarAccess.getScriptElementAccess().getN4EnumDeclarationParserRuleCall_3()); }
	)
	    |
	(
		(ruleImportDeclaration)=>
		ImportDeclarationParserRuleCall_4=ruleImportDeclaration{ announce($ImportDeclarationParserRuleCall_4.start, $ImportDeclarationParserRuleCall_4.stop, grammarAccess.getScriptElementAccess().getImportDeclarationParserRuleCall_4()); }
	)
	    |
	ExportDeclarationParserRuleCall_5=ruleExportDeclaration{ announce($ExportDeclarationParserRuleCall_5.start, $ExportDeclarationParserRuleCall_5.stop, grammarAccess.getScriptElementAccess().getExportDeclarationParserRuleCall_5()); }
	    |
	RootStatementParserRuleCall_6=ruleRootStatement{ announce($RootStatementParserRuleCall_6.start, $RootStatementParserRuleCall_6.stop, grammarAccess.getScriptElementAccess().getRootStatementParserRuleCall_6()); }
)
;

// Entry rule entryRuleAnnotatedScriptElement
entryRuleAnnotatedScriptElement
	:
	ruleAnnotatedScriptElement
	EOF;

// Rule AnnotatedScriptElement
ruleAnnotatedScriptElement
@init {
}:
(
	(
		((
			CommercialAt
			(
				This
				    |
				Target
				    |
				RULE_IDENTIFIER
			)
		)
		)=>
		AnnotationListParserRuleCall_0=ruleAnnotationList{ announce($AnnotationListParserRuleCall_0.start, $AnnotationListParserRuleCall_0.stop, grammarAccess.getAnnotatedScriptElementAccess().getAnnotationListParserRuleCall_0()); }
	)
	(
		(
			ExportDeclarationImplParserRuleCall_1_0_1=ruleExportDeclarationImpl{ announce($ExportDeclarationImplParserRuleCall_1_0_1.start, $ExportDeclarationImplParserRuleCall_1_0_1.stop, grammarAccess.getAnnotatedScriptElementAccess().getExportDeclarationImplParserRuleCall_1_0_1()); }
		)
		    |
		(
			ImportDeclarationImplParserRuleCall_1_1_1=ruleImportDeclarationImpl{ announce($ImportDeclarationImplParserRuleCall_1_1_1.start, $ImportDeclarationImplParserRuleCall_1_1_1.stop, grammarAccess.getAnnotatedScriptElementAccess().getImportDeclarationImplParserRuleCall_1_1_1()); }
		)
		    |
		(
			(
				((
					(
						(
							ruleN4Modifier
						)
					)*
					ruleAsyncNoTrailingLineBreak
					Function
				)
				)=>
				(
					(
						(
							ruleN4Modifier
						)
					)*
					AsyncNoTrailingLineBreakParserRuleCall_1_2_1_0_1=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_1_2_1_0_1.start, $AsyncNoTrailingLineBreakParserRuleCall_1_2_1_0_1.stop, grammarAccess.getAnnotatedScriptElementAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_2_1_0_1()); }
					(
						(Function
						)=>
						FunctionImplParserRuleCall_1_2_1_0_2=ruleFunctionImpl{ announce($FunctionImplParserRuleCall_1_2_1_0_2.start, $FunctionImplParserRuleCall_1_2_1_0_2.stop, grammarAccess.getAnnotatedScriptElementAccess().getFunctionImplParserRuleCall_1_2_1_0_2()); }
					)
				)
			)
		)
		    |
		(
			(
				(
					(
						(
							ruleN4Modifier
						)
					)*
					ClassKeyword_1_3_0_0_2=Class
					 {
						announce($ClassKeyword_1_3_0_0_2, grammarAccess.getAnnotatedScriptElementAccess().getClassKeyword_1_3_0_0_2());
					}
					(
						(
							TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_0_3_0=ruleTypingStrategyDefSiteOperator{
								announce($TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_0_3_0.start, $TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_0_3_0.stop, grammarAccess.getAnnotatedScriptElementAccess().getTypingStrategyAssignment_1_3_0_0_3());
							}
						)
					)?
					(
						(
							NameBindingIdentifierParserRuleCall_1_3_0_0_4_0=ruleBindingIdentifier{
								announce($NameBindingIdentifierParserRuleCall_1_3_0_0_4_0.start, $NameBindingIdentifierParserRuleCall_1_3_0_0_4_0.stop, grammarAccess.getAnnotatedScriptElementAccess().getNameAssignment_1_3_0_0_4());
							}
						)
					)
					(
						VersionDeclarationParserRuleCall_1_3_0_0_5=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_1_3_0_0_5.start, $VersionDeclarationParserRuleCall_1_3_0_0_5.stop, grammarAccess.getAnnotatedScriptElementAccess().getVersionDeclarationParserRuleCall_1_3_0_0_5()); }
					)?
					(
						TypeVariablesParserRuleCall_1_3_0_0_6=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1_3_0_0_6.start, $TypeVariablesParserRuleCall_1_3_0_0_6.stop, grammarAccess.getAnnotatedScriptElementAccess().getTypeVariablesParserRuleCall_1_3_0_0_6()); }
					)?
					(
						ClassExtendsImplementsParserRuleCall_1_3_0_0_7=ruleClassExtendsImplements{ announce($ClassExtendsImplementsParserRuleCall_1_3_0_0_7.start, $ClassExtendsImplementsParserRuleCall_1_3_0_0_7.stop, grammarAccess.getAnnotatedScriptElementAccess().getClassExtendsImplementsParserRuleCall_1_3_0_0_7()); }
					)?
				)
				    |
				(
					(
						(
							ruleN4Modifier
						)
					)*
					InterfaceKeyword_1_3_0_1_2=Interface
					 {
						announce($InterfaceKeyword_1_3_0_1_2, grammarAccess.getAnnotatedScriptElementAccess().getInterfaceKeyword_1_3_0_1_2());
					}
					(
						(
							TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_1_3_0=ruleTypingStrategyDefSiteOperator{
								announce($TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_1_3_0.start, $TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_1_3_0.stop, grammarAccess.getAnnotatedScriptElementAccess().getTypingStrategyAssignment_1_3_0_1_3());
							}
						)
					)?
					(
						(
							NameBindingIdentifierParserRuleCall_1_3_0_1_4_0=ruleBindingIdentifier{
								announce($NameBindingIdentifierParserRuleCall_1_3_0_1_4_0.start, $NameBindingIdentifierParserRuleCall_1_3_0_1_4_0.stop, grammarAccess.getAnnotatedScriptElementAccess().getNameAssignment_1_3_0_1_4());
							}
						)
					)
					(
						VersionDeclarationParserRuleCall_1_3_0_1_5=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_1_3_0_1_5.start, $VersionDeclarationParserRuleCall_1_3_0_1_5.stop, grammarAccess.getAnnotatedScriptElementAccess().getVersionDeclarationParserRuleCall_1_3_0_1_5()); }
					)?
					(
						TypeVariablesParserRuleCall_1_3_0_1_6=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1_3_0_1_6.start, $TypeVariablesParserRuleCall_1_3_0_1_6.stop, grammarAccess.getAnnotatedScriptElementAccess().getTypeVariablesParserRuleCall_1_3_0_1_6()); }
					)?
					(
						InterfaceExtendsListParserRuleCall_1_3_0_1_7=ruleInterfaceExtendsList{ announce($InterfaceExtendsListParserRuleCall_1_3_0_1_7.start, $InterfaceExtendsListParserRuleCall_1_3_0_1_7.stop, grammarAccess.getAnnotatedScriptElementAccess().getInterfaceExtendsListParserRuleCall_1_3_0_1_7()); }
					)?
				)
			)
			MembersParserRuleCall_1_3_1=ruleMembers{ announce($MembersParserRuleCall_1_3_1.start, $MembersParserRuleCall_1_3_1.stop, grammarAccess.getAnnotatedScriptElementAccess().getMembersParserRuleCall_1_3_1()); }
		)
		    |
		(
			(
				(
					ruleN4Modifier
				)
			)*
			EnumKeyword_1_4_2=Enum
			 {
				announce($EnumKeyword_1_4_2, grammarAccess.getAnnotatedScriptElementAccess().getEnumKeyword_1_4_2());
			}
			(
				(
					NameBindingIdentifierParserRuleCall_1_4_3_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_4_3_0.start, $NameBindingIdentifierParserRuleCall_1_4_3_0.stop, grammarAccess.getAnnotatedScriptElementAccess().getNameAssignment_1_4_3());
					}
				)
			)
			(
				VersionDeclarationParserRuleCall_1_4_4=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_1_4_4.start, $VersionDeclarationParserRuleCall_1_4_4.stop, grammarAccess.getAnnotatedScriptElementAccess().getVersionDeclarationParserRuleCall_1_4_4()); }
			)?
			LeftCurlyBracketKeyword_1_4_5=LeftCurlyBracket
			 {
				announce($LeftCurlyBracketKeyword_1_4_5, grammarAccess.getAnnotatedScriptElementAccess().getLeftCurlyBracketKeyword_1_4_5());
			}
			(
				(
					LiteralsN4EnumLiteralParserRuleCall_1_4_6_0=ruleN4EnumLiteral{
						announce($LiteralsN4EnumLiteralParserRuleCall_1_4_6_0.start, $LiteralsN4EnumLiteralParserRuleCall_1_4_6_0.stop, grammarAccess.getAnnotatedScriptElementAccess().getLiteralsAssignment_1_4_6());
					}
				)
			)
			(
				CommaKeyword_1_4_7_0=Comma
				 {
					announce($CommaKeyword_1_4_7_0, grammarAccess.getAnnotatedScriptElementAccess().getCommaKeyword_1_4_7_0());
				}
				(
					(
						LiteralsN4EnumLiteralParserRuleCall_1_4_7_1_0=ruleN4EnumLiteral{
							announce($LiteralsN4EnumLiteralParserRuleCall_1_4_7_1_0.start, $LiteralsN4EnumLiteralParserRuleCall_1_4_7_1_0.stop, grammarAccess.getAnnotatedScriptElementAccess().getLiteralsAssignment_1_4_7_1());
						}
					)
				)
			)*
			RightCurlyBracketKeyword_1_4_8=RightCurlyBracket
			 {
				announce($RightCurlyBracketKeyword_1_4_8, grammarAccess.getAnnotatedScriptElementAccess().getRightCurlyBracketKeyword_1_4_8());
			}
		)
	)
)
;

// Entry rule entryRuleExportDeclaration
entryRuleExportDeclaration
	:
	ruleExportDeclaration
	EOF;

// Rule ExportDeclaration
ruleExportDeclaration
@init {
}:
(
	ExportDeclarationImplParserRuleCall_1=ruleExportDeclarationImpl{ announce($ExportDeclarationImplParserRuleCall_1.start, $ExportDeclarationImplParserRuleCall_1.stop, grammarAccess.getExportDeclarationAccess().getExportDeclarationImplParserRuleCall_1()); }
)
;


// Rule ExportDeclarationImpl
ruleExportDeclarationImpl
@init {
}:
(
	ExportKeyword_0=Export
	 {
		announce($ExportKeyword_0, grammarAccess.getExportDeclarationImplAccess().getExportKeyword_0());
	}
	(
		(
			(
				(
					WildcardExportAsteriskKeyword_1_0_0_0=Asterisk
					 {
						announce($WildcardExportAsteriskKeyword_1_0_0_0, grammarAccess.getExportDeclarationImplAccess().getWildcardExportAsteriskKeyword_1_0_0_0());
					}
				)
			)
			ExportFromClauseParserRuleCall_1_0_1=ruleExportFromClause{ announce($ExportFromClauseParserRuleCall_1_0_1.start, $ExportFromClauseParserRuleCall_1_0_1.stop, grammarAccess.getExportDeclarationImplAccess().getExportFromClauseParserRuleCall_1_0_1()); }
			SemiParserRuleCall_1_0_2=ruleSemi{ announce($SemiParserRuleCall_1_0_2.start, $SemiParserRuleCall_1_0_2.stop, grammarAccess.getExportDeclarationImplAccess().getSemiParserRuleCall_1_0_2()); }
		)
		    |
		(
			ExportClauseParserRuleCall_1_1_0=ruleExportClause{ announce($ExportClauseParserRuleCall_1_1_0.start, $ExportClauseParserRuleCall_1_1_0.stop, grammarAccess.getExportDeclarationImplAccess().getExportClauseParserRuleCall_1_1_0()); }
			(
				(From
				)=>
				ExportFromClauseParserRuleCall_1_1_1=ruleExportFromClause{ announce($ExportFromClauseParserRuleCall_1_1_1.start, $ExportFromClauseParserRuleCall_1_1_1.stop, grammarAccess.getExportDeclarationImplAccess().getExportFromClauseParserRuleCall_1_1_1()); }
			)?
			SemiParserRuleCall_1_1_2=ruleSemi{ announce($SemiParserRuleCall_1_1_2.start, $SemiParserRuleCall_1_1_2.stop, grammarAccess.getExportDeclarationImplAccess().getSemiParserRuleCall_1_1_2()); }
		)
		    |
		(
			(
				ExportedElementExportableElementParserRuleCall_1_2_0=ruleExportableElement{
					announce($ExportedElementExportableElementParserRuleCall_1_2_0.start, $ExportedElementExportableElementParserRuleCall_1_2_0.stop, grammarAccess.getExportDeclarationImplAccess().getExportedElementAssignment_1_2());
				}
			)
		)
		    |
		(
			(
				(
					DefaultExportDefaultKeyword_1_3_0_0=Default
					 {
						announce($DefaultExportDefaultKeyword_1_3_0_0, grammarAccess.getExportDeclarationImplAccess().getDefaultExportDefaultKeyword_1_3_0_0());
					}
				)
			)
			(
				(
					(CommercialAt | 
					Private | 
					Project | 
					Protected | 
					Public | 
					External | 
					Abstract | 
					Static | 
					Const | 
					Class | 
					Interface | 
					Enum | 
					Async | 
					Function | 
					Var | 
					Let
					)=>
					(
						ExportedElementExportableElementParserRuleCall_1_3_1_0_0=ruleExportableElement{
							announce($ExportedElementExportableElementParserRuleCall_1_3_1_0_0.start, $ExportedElementExportableElementParserRuleCall_1_3_1_0_0.stop, grammarAccess.getExportDeclarationImplAccess().getExportedElementAssignment_1_3_1_0());
						}
					)
				)
				    |
				(
					(
						(
							DefaultExportedExpressionAssignmentExpressionParserRuleCall_1_3_1_1_0_0=norm1_AssignmentExpression{
								announce($DefaultExportedExpressionAssignmentExpressionParserRuleCall_1_3_1_1_0_0.start, $DefaultExportedExpressionAssignmentExpressionParserRuleCall_1_3_1_1_0_0.stop, grammarAccess.getExportDeclarationImplAccess().getDefaultExportedExpressionAssignment_1_3_1_1_0());
							}
						)
					)
					SemiParserRuleCall_1_3_1_1_1=ruleSemi{ announce($SemiParserRuleCall_1_3_1_1_1.start, $SemiParserRuleCall_1_3_1_1_1.stop, grammarAccess.getExportDeclarationImplAccess().getSemiParserRuleCall_1_3_1_1_1()); }
				)
			)
		)
	)
)
;


// Rule ExportFromClause
ruleExportFromClause
@init {
}:
(
	FromKeyword_0=From
	 {
		announce($FromKeyword_0, grammarAccess.getExportFromClauseAccess().getFromKeyword_0());
	}
	(
		(
			ReexportedFromTModuleModuleSpecifierParserRuleCall_1_0_1=ruleModuleSpecifier{
				announce($ReexportedFromTModuleModuleSpecifierParserRuleCall_1_0_1.start, $ReexportedFromTModuleModuleSpecifierParserRuleCall_1_0_1.stop, grammarAccess.getExportFromClauseAccess().getReexportedFromAssignment_1());
			}
		)
	)
)
;


// Rule ExportClause
ruleExportClause
@init {
}:
(
	LeftCurlyBracketKeyword_0=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_0, grammarAccess.getExportClauseAccess().getLeftCurlyBracketKeyword_0());
	}
	(
		(
			(
				NamedExportsExportSpecifierParserRuleCall_1_0_0=ruleExportSpecifier{
					announce($NamedExportsExportSpecifierParserRuleCall_1_0_0.start, $NamedExportsExportSpecifierParserRuleCall_1_0_0.stop, grammarAccess.getExportClauseAccess().getNamedExportsAssignment_1_0());
				}
			)
		)
		(
			CommaKeyword_1_1_0=Comma
			 {
				announce($CommaKeyword_1_1_0, grammarAccess.getExportClauseAccess().getCommaKeyword_1_1_0());
			}
			(
				(
					NamedExportsExportSpecifierParserRuleCall_1_1_1_0=ruleExportSpecifier{
						announce($NamedExportsExportSpecifierParserRuleCall_1_1_1_0.start, $NamedExportsExportSpecifierParserRuleCall_1_1_1_0.stop, grammarAccess.getExportClauseAccess().getNamedExportsAssignment_1_1_1());
					}
				)
			)
		)*
		(
			CommaKeyword_1_2=Comma
			 {
				announce($CommaKeyword_1_2, grammarAccess.getExportClauseAccess().getCommaKeyword_1_2());
			}
		)?
	)?
	RightCurlyBracketKeyword_2=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_2, grammarAccess.getExportClauseAccess().getRightCurlyBracketKeyword_2());
	}
)
;

// Entry rule entryRuleExportSpecifier
entryRuleExportSpecifier
	:
	ruleExportSpecifier
	EOF;

// Rule ExportSpecifier
ruleExportSpecifier
@init {
}:
(
	(
		(
			ElementIdentifierRefParserRuleCall_0_0=ruleIdentifierRef{
				announce($ElementIdentifierRefParserRuleCall_0_0.start, $ElementIdentifierRefParserRuleCall_0_0.stop, grammarAccess.getExportSpecifierAccess().getElementAssignment_0());
			}
		)
	)
	(
		AsKeyword_1_0=As
		 {
			announce($AsKeyword_1_0, grammarAccess.getExportSpecifierAccess().getAsKeyword_1_0());
		}
		(
			(
				AliasIdentifierNameParserRuleCall_1_1_0=ruleIdentifierName{
					announce($AliasIdentifierNameParserRuleCall_1_1_0.start, $AliasIdentifierNameParserRuleCall_1_1_0.stop, grammarAccess.getExportSpecifierAccess().getAliasAssignment_1_1());
				}
			)
		)
	)?
)
;

// Entry rule entryRuleExportableElement
entryRuleExportableElement
	:
	ruleExportableElement
	EOF;

// Rule ExportableElement
ruleExportableElement
@init {
}:
(
	(
		((
			CommercialAt
			(
				This
				    |
				Target
				    |
				RULE_IDENTIFIER
			)
		)
		)=>
		AnnotatedExportableElementParserRuleCall_0=ruleAnnotatedExportableElement{ announce($AnnotatedExportableElementParserRuleCall_0.start, $AnnotatedExportableElementParserRuleCall_0.stop, grammarAccess.getExportableElementAccess().getAnnotatedExportableElementParserRuleCall_0()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Class
			(
				(
					ruleTypingStrategyDefSiteOperator
				)
			)?
			(
				(
					ruleBindingIdentifier
				)
			)?
			(
				ruleVersionDeclaration
			)?
		)
		)=>
		N4ClassDeclarationParserRuleCall_1=ruleN4ClassDeclaration{ announce($N4ClassDeclarationParserRuleCall_1.start, $N4ClassDeclarationParserRuleCall_1.stop, grammarAccess.getExportableElementAccess().getN4ClassDeclarationParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Interface
			(
				(
					ruleTypingStrategyDefSiteOperator
				)
			)?
			(
				(
					ruleBindingIdentifier
				)
			)?
			(
				ruleVersionDeclaration
			)?
		)
		)=>
		N4InterfaceDeclarationParserRuleCall_2=ruleN4InterfaceDeclaration{ announce($N4InterfaceDeclarationParserRuleCall_2.start, $N4InterfaceDeclarationParserRuleCall_2.stop, grammarAccess.getExportableElementAccess().getN4InterfaceDeclarationParserRuleCall_2()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Enum
			(
				(
					ruleBindingIdentifier
				)
			)?
			(
				ruleVersionDeclaration
			)?
		)
		)=>
		N4EnumDeclarationParserRuleCall_3=ruleN4EnumDeclaration{ announce($N4EnumDeclarationParserRuleCall_3.start, $N4EnumDeclarationParserRuleCall_3.stop, grammarAccess.getExportableElementAccess().getN4EnumDeclarationParserRuleCall_3()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			ruleAsyncNoTrailingLineBreak
			Function
		)
		)=>
		FunctionDeclarationParserRuleCall_4=ruleFunctionDeclaration{ announce($FunctionDeclarationParserRuleCall_4.start, $FunctionDeclarationParserRuleCall_4.stop, grammarAccess.getExportableElementAccess().getFunctionDeclarationParserRuleCall_4()); }
	)
	    |
	ExportedVariableStatementParserRuleCall_5=ruleExportedVariableStatement{ announce($ExportedVariableStatementParserRuleCall_5.start, $ExportedVariableStatementParserRuleCall_5.stop, grammarAccess.getExportableElementAccess().getExportedVariableStatementParserRuleCall_5()); }
)
;

// Entry rule entryRuleAnnotatedExportableElement
entryRuleAnnotatedExportableElement
	:
	ruleAnnotatedExportableElement
	EOF;

// Rule AnnotatedExportableElement
ruleAnnotatedExportableElement
@init {
}:
(
	(
		((
			CommercialAt
			(
				This
				    |
				Target
				    |
				RULE_IDENTIFIER
			)
		)
		)=>
		AnnotationListParserRuleCall_0=ruleAnnotationList{ announce($AnnotationListParserRuleCall_0.start, $AnnotationListParserRuleCall_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getAnnotationListParserRuleCall_0()); }
	)
	(
		(
			(
				(
					ruleN4Modifier
				)
			)*
			AsyncNoTrailingLineBreakParserRuleCall_1_0_2=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_1_0_2.start, $AsyncNoTrailingLineBreakParserRuleCall_1_0_2.stop, grammarAccess.getAnnotatedExportableElementAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_0_2()); }
			FunctionImplParserRuleCall_1_0_3=ruleFunctionImpl{ announce($FunctionImplParserRuleCall_1_0_3.start, $FunctionImplParserRuleCall_1_0_3.stop, grammarAccess.getAnnotatedExportableElementAccess().getFunctionImplParserRuleCall_1_0_3()); }
		)
		    |
		(
			(
				(
					ruleN4Modifier
				)
			)*
			(
				(
					ruleVariableStatementKeyword
				)
			)
			(
				(
					VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_3_0=ruleExportedVariableDeclarationOrBinding{
						announce($VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_3_0.start, $VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_3_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getVarDeclsOrBindingsAssignment_1_1_3());
					}
				)
			)
			(
				CommaKeyword_1_1_4_0=Comma
				 {
					announce($CommaKeyword_1_1_4_0, grammarAccess.getAnnotatedExportableElementAccess().getCommaKeyword_1_1_4_0());
				}
				(
					(
						VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_4_1_0=ruleExportedVariableDeclarationOrBinding{
							announce($VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_4_1_0.start, $VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_4_1_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getVarDeclsOrBindingsAssignment_1_1_4_1());
						}
					)
				)
			)*
			SemiParserRuleCall_1_1_5=ruleSemi{ announce($SemiParserRuleCall_1_1_5.start, $SemiParserRuleCall_1_1_5.stop, grammarAccess.getAnnotatedExportableElementAccess().getSemiParserRuleCall_1_1_5()); }
		)
		    |
		(
			(
				(
					(
						(
							ruleN4Modifier
						)
					)*
					ClassKeyword_1_2_0_0_2=Class
					 {
						announce($ClassKeyword_1_2_0_0_2, grammarAccess.getAnnotatedExportableElementAccess().getClassKeyword_1_2_0_0_2());
					}
					(
						(
							TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_0_3_0=ruleTypingStrategyDefSiteOperator{
								announce($TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_0_3_0.start, $TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_0_3_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getTypingStrategyAssignment_1_2_0_0_3());
							}
						)
					)?
					(
						(
							NameBindingIdentifierParserRuleCall_1_2_0_0_4_0=ruleBindingIdentifier{
								announce($NameBindingIdentifierParserRuleCall_1_2_0_0_4_0.start, $NameBindingIdentifierParserRuleCall_1_2_0_0_4_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getNameAssignment_1_2_0_0_4());
							}
						)
					)
					(
						TypeVariablesParserRuleCall_1_2_0_0_5=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1_2_0_0_5.start, $TypeVariablesParserRuleCall_1_2_0_0_5.stop, grammarAccess.getAnnotatedExportableElementAccess().getTypeVariablesParserRuleCall_1_2_0_0_5()); }
					)?
					(
						ClassExtendsImplementsParserRuleCall_1_2_0_0_6=ruleClassExtendsImplements{ announce($ClassExtendsImplementsParserRuleCall_1_2_0_0_6.start, $ClassExtendsImplementsParserRuleCall_1_2_0_0_6.stop, grammarAccess.getAnnotatedExportableElementAccess().getClassExtendsImplementsParserRuleCall_1_2_0_0_6()); }
					)?
				)
				    |
				(
					(
						(
							ruleN4Modifier
						)
					)*
					InterfaceKeyword_1_2_0_1_2=Interface
					 {
						announce($InterfaceKeyword_1_2_0_1_2, grammarAccess.getAnnotatedExportableElementAccess().getInterfaceKeyword_1_2_0_1_2());
					}
					(
						(
							TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_1_3_0=ruleTypingStrategyDefSiteOperator{
								announce($TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_1_3_0.start, $TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_1_3_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getTypingStrategyAssignment_1_2_0_1_3());
							}
						)
					)?
					(
						(
							NameBindingIdentifierParserRuleCall_1_2_0_1_4_0=ruleBindingIdentifier{
								announce($NameBindingIdentifierParserRuleCall_1_2_0_1_4_0.start, $NameBindingIdentifierParserRuleCall_1_2_0_1_4_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getNameAssignment_1_2_0_1_4());
							}
						)
					)
					(
						TypeVariablesParserRuleCall_1_2_0_1_5=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1_2_0_1_5.start, $TypeVariablesParserRuleCall_1_2_0_1_5.stop, grammarAccess.getAnnotatedExportableElementAccess().getTypeVariablesParserRuleCall_1_2_0_1_5()); }
					)?
					(
						InterfaceExtendsListParserRuleCall_1_2_0_1_6=ruleInterfaceExtendsList{ announce($InterfaceExtendsListParserRuleCall_1_2_0_1_6.start, $InterfaceExtendsListParserRuleCall_1_2_0_1_6.stop, grammarAccess.getAnnotatedExportableElementAccess().getInterfaceExtendsListParserRuleCall_1_2_0_1_6()); }
					)?
				)
			)
			MembersParserRuleCall_1_2_1=ruleMembers{ announce($MembersParserRuleCall_1_2_1.start, $MembersParserRuleCall_1_2_1.stop, grammarAccess.getAnnotatedExportableElementAccess().getMembersParserRuleCall_1_2_1()); }
		)
		    |
		(
			(
				(
					ruleN4Modifier
				)
			)*
			EnumKeyword_1_3_2=Enum
			 {
				announce($EnumKeyword_1_3_2, grammarAccess.getAnnotatedExportableElementAccess().getEnumKeyword_1_3_2());
			}
			(
				(
					NameBindingIdentifierParserRuleCall_1_3_3_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_3_3_0.start, $NameBindingIdentifierParserRuleCall_1_3_3_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getNameAssignment_1_3_3());
					}
				)
			)
			LeftCurlyBracketKeyword_1_3_4=LeftCurlyBracket
			 {
				announce($LeftCurlyBracketKeyword_1_3_4, grammarAccess.getAnnotatedExportableElementAccess().getLeftCurlyBracketKeyword_1_3_4());
			}
			(
				(
					LiteralsN4EnumLiteralParserRuleCall_1_3_5_0=ruleN4EnumLiteral{
						announce($LiteralsN4EnumLiteralParserRuleCall_1_3_5_0.start, $LiteralsN4EnumLiteralParserRuleCall_1_3_5_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getLiteralsAssignment_1_3_5());
					}
				)
			)
			(
				CommaKeyword_1_3_6_0=Comma
				 {
					announce($CommaKeyword_1_3_6_0, grammarAccess.getAnnotatedExportableElementAccess().getCommaKeyword_1_3_6_0());
				}
				(
					(
						LiteralsN4EnumLiteralParserRuleCall_1_3_6_1_0=ruleN4EnumLiteral{
							announce($LiteralsN4EnumLiteralParserRuleCall_1_3_6_1_0.start, $LiteralsN4EnumLiteralParserRuleCall_1_3_6_1_0.stop, grammarAccess.getAnnotatedExportableElementAccess().getLiteralsAssignment_1_3_6_1());
						}
					)
				)
			)*
			RightCurlyBracketKeyword_1_3_7=RightCurlyBracket
			 {
				announce($RightCurlyBracketKeyword_1_3_7, grammarAccess.getAnnotatedExportableElementAccess().getRightCurlyBracketKeyword_1_3_7());
			}
		)
	)
)
;

// Entry rule entryRuleImportDeclaration
entryRuleImportDeclaration
	:
	ruleImportDeclaration
	EOF;

// Rule ImportDeclaration
ruleImportDeclaration
@init {
}:
(
	ImportDeclarationImplParserRuleCall_1=ruleImportDeclarationImpl{ announce($ImportDeclarationImplParserRuleCall_1.start, $ImportDeclarationImplParserRuleCall_1.stop, grammarAccess.getImportDeclarationAccess().getImportDeclarationImplParserRuleCall_1()); }
)
;


// Rule ImportDeclarationImpl
ruleImportDeclarationImpl
@init {
}:
(
	ImportKeyword_0=Import
	 {
		announce($ImportKeyword_0, grammarAccess.getImportDeclarationImplAccess().getImportKeyword_0());
	}
	(
		ImportClauseParserRuleCall_1_0=ruleImportClause{ announce($ImportClauseParserRuleCall_1_0.start, $ImportClauseParserRuleCall_1_0.stop, grammarAccess.getImportDeclarationImplAccess().getImportClauseParserRuleCall_1_0()); }
		(
			(
				ImportFromFromKeyword_1_1_0=From
				 {
					announce($ImportFromFromKeyword_1_1_0, grammarAccess.getImportDeclarationImplAccess().getImportFromFromKeyword_1_1_0());
				}
			)
		)
	)?
	(
		(
			ModuleTModuleModuleSpecifierParserRuleCall_2_0_1=ruleModuleSpecifier{
				announce($ModuleTModuleModuleSpecifierParserRuleCall_2_0_1.start, $ModuleTModuleModuleSpecifierParserRuleCall_2_0_1.stop, grammarAccess.getImportDeclarationImplAccess().getModuleAssignment_2());
			}
		)
	)
	SemiParserRuleCall_3=ruleSemi{ announce($SemiParserRuleCall_3.start, $SemiParserRuleCall_3.stop, grammarAccess.getImportDeclarationImplAccess().getSemiParserRuleCall_3()); }
)
;


// Rule ImportClause
ruleImportClause
@init {
}:
(
	(
		(
			(
				ImportSpecifiersDefaultImportSpecifierParserRuleCall_0_0_0=ruleDefaultImportSpecifier{
					announce($ImportSpecifiersDefaultImportSpecifierParserRuleCall_0_0_0.start, $ImportSpecifiersDefaultImportSpecifierParserRuleCall_0_0_0.stop, grammarAccess.getImportClauseAccess().getImportSpecifiersAssignment_0_0());
				}
			)
		)
		(
			CommaKeyword_0_1_0=Comma
			 {
				announce($CommaKeyword_0_1_0, grammarAccess.getImportClauseAccess().getCommaKeyword_0_1_0());
			}
			ImportSpecifiersExceptDefaultParserRuleCall_0_1_1=ruleImportSpecifiersExceptDefault{ announce($ImportSpecifiersExceptDefaultParserRuleCall_0_1_1.start, $ImportSpecifiersExceptDefaultParserRuleCall_0_1_1.stop, grammarAccess.getImportClauseAccess().getImportSpecifiersExceptDefaultParserRuleCall_0_1_1()); }
		)?
	)
	    |
	ImportSpecifiersExceptDefaultParserRuleCall_1=ruleImportSpecifiersExceptDefault{ announce($ImportSpecifiersExceptDefaultParserRuleCall_1.start, $ImportSpecifiersExceptDefaultParserRuleCall_1.stop, grammarAccess.getImportClauseAccess().getImportSpecifiersExceptDefaultParserRuleCall_1()); }
)
;


// Rule ImportSpecifiersExceptDefault
ruleImportSpecifiersExceptDefault
@init {
}:
(
	(
		(
			ImportSpecifiersNamespaceImportSpecifierParserRuleCall_0_0=ruleNamespaceImportSpecifier{
				announce($ImportSpecifiersNamespaceImportSpecifierParserRuleCall_0_0.start, $ImportSpecifiersNamespaceImportSpecifierParserRuleCall_0_0.stop, grammarAccess.getImportSpecifiersExceptDefaultAccess().getImportSpecifiersAssignment_0());
			}
		)
	)
	    |
	(
		LeftCurlyBracketKeyword_1_0=LeftCurlyBracket
		 {
			announce($LeftCurlyBracketKeyword_1_0, grammarAccess.getImportSpecifiersExceptDefaultAccess().getLeftCurlyBracketKeyword_1_0());
		}
		(
			(
				(
					ImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_0_0=ruleNamedImportSpecifier{
						announce($ImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_0_0.start, $ImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_0_0.stop, grammarAccess.getImportSpecifiersExceptDefaultAccess().getImportSpecifiersAssignment_1_1_0());
					}
				)
			)
			(
				CommaKeyword_1_1_1_0=Comma
				 {
					announce($CommaKeyword_1_1_1_0, grammarAccess.getImportSpecifiersExceptDefaultAccess().getCommaKeyword_1_1_1_0());
				}
				(
					(
						ImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_1_1_0=ruleNamedImportSpecifier{
							announce($ImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_1_1_0.start, $ImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_1_1_0.stop, grammarAccess.getImportSpecifiersExceptDefaultAccess().getImportSpecifiersAssignment_1_1_1_1());
						}
					)
				)
			)*
			(
				CommaKeyword_1_1_2=Comma
				 {
					announce($CommaKeyword_1_1_2, grammarAccess.getImportSpecifiersExceptDefaultAccess().getCommaKeyword_1_1_2());
				}
			)?
		)?
		RightCurlyBracketKeyword_1_2=RightCurlyBracket
		 {
			announce($RightCurlyBracketKeyword_1_2, grammarAccess.getImportSpecifiersExceptDefaultAccess().getRightCurlyBracketKeyword_1_2());
		}
	)
)
;

// Entry rule entryRuleNamedImportSpecifier
entryRuleNamedImportSpecifier
	:
	ruleNamedImportSpecifier
	EOF;

// Rule NamedImportSpecifier
ruleNamedImportSpecifier
@init {
}:
(
	(
		(
			ImportedElementTExportableElementBindingIdentifierParserRuleCall_0_0_1=ruleBindingIdentifier{
				announce($ImportedElementTExportableElementBindingIdentifierParserRuleCall_0_0_1.start, $ImportedElementTExportableElementBindingIdentifierParserRuleCall_0_0_1.stop, grammarAccess.getNamedImportSpecifierAccess().getImportedElementAssignment_0());
			}
		)
	)
	    |
	(
		(
			(
				ImportedElementTExportableElementIdentifierNameParserRuleCall_1_0_0_1=ruleIdentifierName{
					announce($ImportedElementTExportableElementIdentifierNameParserRuleCall_1_0_0_1.start, $ImportedElementTExportableElementIdentifierNameParserRuleCall_1_0_0_1.stop, grammarAccess.getNamedImportSpecifierAccess().getImportedElementAssignment_1_0());
				}
			)
		)
		AsKeyword_1_1=As
		 {
			announce($AsKeyword_1_1, grammarAccess.getNamedImportSpecifierAccess().getAsKeyword_1_1());
		}
		(
			(
				AliasBindingIdentifierParserRuleCall_1_2_0=ruleBindingIdentifier{
					announce($AliasBindingIdentifierParserRuleCall_1_2_0.start, $AliasBindingIdentifierParserRuleCall_1_2_0.stop, grammarAccess.getNamedImportSpecifierAccess().getAliasAssignment_1_2());
				}
			)
		)
	)
)
;

// Entry rule entryRuleDefaultImportSpecifier
entryRuleDefaultImportSpecifier
	:
	ruleDefaultImportSpecifier
	EOF;

// Rule DefaultImportSpecifier
ruleDefaultImportSpecifier
@init {
}:
(
	(
		ImportedElementTExportableElementBindingIdentifierParserRuleCall_0_1=ruleBindingIdentifier{
			announce($ImportedElementTExportableElementBindingIdentifierParserRuleCall_0_1.start, $ImportedElementTExportableElementBindingIdentifierParserRuleCall_0_1.stop, grammarAccess.getDefaultImportSpecifierAccess().getImportedElementAssignment());
		}
	)
)
;

// Entry rule entryRuleNamespaceImportSpecifier
entryRuleNamespaceImportSpecifier
	:
	ruleNamespaceImportSpecifier
	EOF;

// Rule NamespaceImportSpecifier
ruleNamespaceImportSpecifier
@init {
}:
(
	AsteriskKeyword_1=Asterisk
	 {
		announce($AsteriskKeyword_1, grammarAccess.getNamespaceImportSpecifierAccess().getAsteriskKeyword_1());
	}
	AsKeyword_2=As
	 {
		announce($AsKeyword_2, grammarAccess.getNamespaceImportSpecifierAccess().getAsKeyword_2());
	}
	(
		(
			AliasBindingIdentifierParserRuleCall_3_0=ruleBindingIdentifier{
				announce($AliasBindingIdentifierParserRuleCall_3_0.start, $AliasBindingIdentifierParserRuleCall_3_0.stop, grammarAccess.getNamespaceImportSpecifierAccess().getAliasAssignment_3());
			}
		)
	)
	(
		(
			DeclaredDynamicPlusSignKeyword_4_0=PlusSign
			 {
				announce($DeclaredDynamicPlusSignKeyword_4_0, grammarAccess.getNamespaceImportSpecifierAccess().getDeclaredDynamicPlusSignKeyword_4_0());
			}
		)
	)?
)
;

// Entry rule entryRuleModuleSpecifier
entryRuleModuleSpecifier
	:
	ruleModuleSpecifier
	EOF;

// Rule ModuleSpecifier
ruleModuleSpecifier
@init {
}
:
RULE_STRING
;

// Entry rule entryRuleFunctionDeclaration
entryRuleFunctionDeclaration
	:
	ruleFunctionDeclaration
	EOF;

// Rule FunctionDeclaration
ruleFunctionDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			ruleAsyncNoTrailingLineBreak
			Function
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			AsyncNoTrailingLineBreakParserRuleCall_0_0_2=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_0_0_2.start, $AsyncNoTrailingLineBreakParserRuleCall_0_0_2.stop, grammarAccess.getFunctionDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_0_0_2()); }
			(
				(Function
				)=>
				FunctionImplParserRuleCall_0_0_3=ruleFunctionImpl{ announce($FunctionImplParserRuleCall_0_0_3.start, $FunctionImplParserRuleCall_0_0_3.stop, grammarAccess.getFunctionDeclarationAccess().getFunctionImplParserRuleCall_0_0_3()); }
			)
		)
	)
	(
		(ruleSemi)=>
		SemiParserRuleCall_1=ruleSemi{ announce($SemiParserRuleCall_1.start, $SemiParserRuleCall_1.stop, grammarAccess.getFunctionDeclarationAccess().getSemiParserRuleCall_1()); }
	)?
)
;


// Rule FunctionDeclaration
norm1_FunctionDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			ruleAsyncNoTrailingLineBreak
			Function
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			AsyncNoTrailingLineBreakParserRuleCall_0_0_2=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_0_0_2.start, $AsyncNoTrailingLineBreakParserRuleCall_0_0_2.stop, grammarAccess.getFunctionDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_0_0_2()); }
			(
				(Function
				)=>
				FunctionImplParserRuleCall_0_0_3=norm3_FunctionImpl{ announce($FunctionImplParserRuleCall_0_0_3.start, $FunctionImplParserRuleCall_0_0_3.stop, grammarAccess.getFunctionDeclarationAccess().getFunctionImplParserRuleCall_0_0_3()); }
			)
		)
	)
	(
		(ruleSemi)=>
		SemiParserRuleCall_1=ruleSemi{ announce($SemiParserRuleCall_1.start, $SemiParserRuleCall_1.stop, grammarAccess.getFunctionDeclarationAccess().getSemiParserRuleCall_1()); }
	)?
)
;


// Rule AsyncNoTrailingLineBreak
ruleAsyncNoTrailingLineBreak
@init {
}:
(
	(
		(
			DeclaredAsyncAsyncKeyword_0_0=Async
			 {
				announce($DeclaredAsyncAsyncKeyword_0_0, grammarAccess.getAsyncNoTrailingLineBreakAccess().getDeclaredAsyncAsyncKeyword_0_0());
			}
		)
	)
	NoLineTerminatorParserRuleCall_1=ruleNoLineTerminator{ announce($NoLineTerminatorParserRuleCall_1.start, $NoLineTerminatorParserRuleCall_1.stop, grammarAccess.getAsyncNoTrailingLineBreakAccess().getNoLineTerminatorParserRuleCall_1()); }
)?
;


// Rule FunctionImpl
ruleFunctionImpl
@init {
}:
(
	FunctionKeyword_0=Function
	 {
		announce($FunctionKeyword_0, grammarAccess.getFunctionImplAccess().getFunctionKeyword_0());
	}
	(
		(
			(
				(
					GeneratorAsteriskKeyword_1_0_0_0=Asterisk
					 {
						announce($GeneratorAsteriskKeyword_1_0_0_0, grammarAccess.getFunctionImplAccess().getGeneratorAsteriskKeyword_1_0_0_0());
					}
				)
			)
			FunctionHeaderParserRuleCall_1_0_1=norm2_FunctionHeader{ announce($FunctionHeaderParserRuleCall_1_0_1.start, $FunctionHeaderParserRuleCall_1_0_1.stop, grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_0_1()); }
			FunctionBodyParserRuleCall_1_0_2=norm1_FunctionBody{ announce($FunctionBodyParserRuleCall_1_0_2.start, $FunctionBodyParserRuleCall_1_0_2.stop, grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_0_2()); }
		)
		    |
		(
			FunctionHeaderParserRuleCall_1_1_0=ruleFunctionHeader{ announce($FunctionHeaderParserRuleCall_1_1_0.start, $FunctionHeaderParserRuleCall_1_1_0.stop, grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_1_0()); }
			FunctionBodyParserRuleCall_1_1_1=ruleFunctionBody{ announce($FunctionBodyParserRuleCall_1_1_1.start, $FunctionBodyParserRuleCall_1_1_1.stop, grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_1_1()); }
		)
	)
)
;


// Rule FunctionImpl
norm3_FunctionImpl
@init {
}:
(
	FunctionKeyword_0=Function
	 {
		announce($FunctionKeyword_0, grammarAccess.getFunctionImplAccess().getFunctionKeyword_0());
	}
	(
		(
			(
				(
					GeneratorAsteriskKeyword_1_0_0_0=Asterisk
					 {
						announce($GeneratorAsteriskKeyword_1_0_0_0, grammarAccess.getFunctionImplAccess().getGeneratorAsteriskKeyword_1_0_0_0());
					}
				)
			)
			FunctionHeaderParserRuleCall_1_0_1=norm3_FunctionHeader{ announce($FunctionHeaderParserRuleCall_1_0_1.start, $FunctionHeaderParserRuleCall_1_0_1.stop, grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_0_1()); }
			FunctionBodyParserRuleCall_1_0_2=norm1_FunctionBody{ announce($FunctionBodyParserRuleCall_1_0_2.start, $FunctionBodyParserRuleCall_1_0_2.stop, grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_0_2()); }
		)
		    |
		(
			FunctionHeaderParserRuleCall_1_1_0=norm1_FunctionHeader{ announce($FunctionHeaderParserRuleCall_1_1_0.start, $FunctionHeaderParserRuleCall_1_1_0.stop, grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_1_0()); }
			FunctionBodyParserRuleCall_1_1_1=ruleFunctionBody{ announce($FunctionBodyParserRuleCall_1_1_1.start, $FunctionBodyParserRuleCall_1_1_1.stop, grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_1_1()); }
		)
	)
)
;


// Rule FunctionImpl
norm6_FunctionImpl
@init {
}:
(
	FunctionKeyword_0=Function
	 {
		announce($FunctionKeyword_0, grammarAccess.getFunctionImplAccess().getFunctionKeyword_0());
	}
	(
		(
			(
				(
					GeneratorAsteriskKeyword_1_0_0_0=Asterisk
					 {
						announce($GeneratorAsteriskKeyword_1_0_0_0, grammarAccess.getFunctionImplAccess().getGeneratorAsteriskKeyword_1_0_0_0());
					}
				)
			)
			FunctionHeaderParserRuleCall_1_0_1=norm3_FunctionHeader{ announce($FunctionHeaderParserRuleCall_1_0_1.start, $FunctionHeaderParserRuleCall_1_0_1.stop, grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_0_1()); }
			FunctionBodyParserRuleCall_1_0_2=norm3_FunctionBody{ announce($FunctionBodyParserRuleCall_1_0_2.start, $FunctionBodyParserRuleCall_1_0_2.stop, grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_0_2()); }
		)
		    |
		(
			FunctionHeaderParserRuleCall_1_1_0=ruleFunctionHeader{ announce($FunctionHeaderParserRuleCall_1_1_0.start, $FunctionHeaderParserRuleCall_1_1_0.stop, grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_1_0()); }
			FunctionBodyParserRuleCall_1_1_1=norm2_FunctionBody{ announce($FunctionBodyParserRuleCall_1_1_1.start, $FunctionBodyParserRuleCall_1_1_1.stop, grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_1_1()); }
		)
	)
)
;


// Rule FunctionHeader
ruleFunctionHeader
@init {
}:
(
	(
		TypeVariablesParserRuleCall_0=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_0.start, $TypeVariablesParserRuleCall_0.stop, grammarAccess.getFunctionHeaderAccess().getTypeVariablesParserRuleCall_0()); }
	)?
	(
		(
			NameBindingIdentifierParserRuleCall_1_0=ruleBindingIdentifier{
				announce($NameBindingIdentifierParserRuleCall_1_0.start, $NameBindingIdentifierParserRuleCall_1_0.stop, grammarAccess.getFunctionHeaderAccess().getNameAssignment_1());
			}
		)
	)?
	(
		VersionDeclarationParserRuleCall_2=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_2.start, $VersionDeclarationParserRuleCall_2.stop, grammarAccess.getFunctionHeaderAccess().getVersionDeclarationParserRuleCall_2()); }
	)?
	StrictFormalParametersParserRuleCall_3=ruleStrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_3.start, $StrictFormalParametersParserRuleCall_3.stop, grammarAccess.getFunctionHeaderAccess().getStrictFormalParametersParserRuleCall_3()); }
	(
		(Colon
		)=>
		ColonSepReturnTypeRefParserRuleCall_4=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_4.start, $ColonSepReturnTypeRefParserRuleCall_4.stop, grammarAccess.getFunctionHeaderAccess().getColonSepReturnTypeRefParserRuleCall_4()); }
	)?
)
;


// Rule FunctionHeader
norm1_FunctionHeader
@init {
}:
(
	(
		TypeVariablesParserRuleCall_0=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_0.start, $TypeVariablesParserRuleCall_0.stop, grammarAccess.getFunctionHeaderAccess().getTypeVariablesParserRuleCall_0()); }
	)?
	(
		(
			NameBindingIdentifierParserRuleCall_1_0=norm1_BindingIdentifier{
				announce($NameBindingIdentifierParserRuleCall_1_0.start, $NameBindingIdentifierParserRuleCall_1_0.stop, grammarAccess.getFunctionHeaderAccess().getNameAssignment_1());
			}
		)
	)?
	(
		VersionDeclarationParserRuleCall_2=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_2.start, $VersionDeclarationParserRuleCall_2.stop, grammarAccess.getFunctionHeaderAccess().getVersionDeclarationParserRuleCall_2()); }
	)?
	StrictFormalParametersParserRuleCall_3=ruleStrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_3.start, $StrictFormalParametersParserRuleCall_3.stop, grammarAccess.getFunctionHeaderAccess().getStrictFormalParametersParserRuleCall_3()); }
	(
		(Colon
		)=>
		ColonSepReturnTypeRefParserRuleCall_4=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_4.start, $ColonSepReturnTypeRefParserRuleCall_4.stop, grammarAccess.getFunctionHeaderAccess().getColonSepReturnTypeRefParserRuleCall_4()); }
	)?
)
;


// Rule FunctionHeader
norm2_FunctionHeader
@init {
}:
(
	(
		TypeVariablesParserRuleCall_0=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_0.start, $TypeVariablesParserRuleCall_0.stop, grammarAccess.getFunctionHeaderAccess().getTypeVariablesParserRuleCall_0()); }
	)?
	(
		(
			NameBindingIdentifierParserRuleCall_1_0=ruleBindingIdentifier{
				announce($NameBindingIdentifierParserRuleCall_1_0.start, $NameBindingIdentifierParserRuleCall_1_0.stop, grammarAccess.getFunctionHeaderAccess().getNameAssignment_1());
			}
		)
	)?
	(
		VersionDeclarationParserRuleCall_2=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_2.start, $VersionDeclarationParserRuleCall_2.stop, grammarAccess.getFunctionHeaderAccess().getVersionDeclarationParserRuleCall_2()); }
	)?
	StrictFormalParametersParserRuleCall_3=norm1_StrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_3.start, $StrictFormalParametersParserRuleCall_3.stop, grammarAccess.getFunctionHeaderAccess().getStrictFormalParametersParserRuleCall_3()); }
	(
		(Colon
		)=>
		ColonSepReturnTypeRefParserRuleCall_4=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_4.start, $ColonSepReturnTypeRefParserRuleCall_4.stop, grammarAccess.getFunctionHeaderAccess().getColonSepReturnTypeRefParserRuleCall_4()); }
	)?
)
;


// Rule FunctionHeader
norm3_FunctionHeader
@init {
}:
(
	(
		TypeVariablesParserRuleCall_0=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_0.start, $TypeVariablesParserRuleCall_0.stop, grammarAccess.getFunctionHeaderAccess().getTypeVariablesParserRuleCall_0()); }
	)?
	(
		(
			NameBindingIdentifierParserRuleCall_1_0=norm1_BindingIdentifier{
				announce($NameBindingIdentifierParserRuleCall_1_0.start, $NameBindingIdentifierParserRuleCall_1_0.stop, grammarAccess.getFunctionHeaderAccess().getNameAssignment_1());
			}
		)
	)?
	(
		VersionDeclarationParserRuleCall_2=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_2.start, $VersionDeclarationParserRuleCall_2.stop, grammarAccess.getFunctionHeaderAccess().getVersionDeclarationParserRuleCall_2()); }
	)?
	StrictFormalParametersParserRuleCall_3=norm1_StrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_3.start, $StrictFormalParametersParserRuleCall_3.stop, grammarAccess.getFunctionHeaderAccess().getStrictFormalParametersParserRuleCall_3()); }
	(
		(Colon
		)=>
		ColonSepReturnTypeRefParserRuleCall_4=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_4.start, $ColonSepReturnTypeRefParserRuleCall_4.stop, grammarAccess.getFunctionHeaderAccess().getColonSepReturnTypeRefParserRuleCall_4()); }
	)?
)
;


// Rule FunctionBody
ruleFunctionBody
@init {
}:
(
	((
		LeftCurlyBracket
	)
	)=>
	(
		BodyBlockParserRuleCall_1_0_0=ruleBlock{
			announce($BodyBlockParserRuleCall_1_0_0.start, $BodyBlockParserRuleCall_1_0_0.stop, grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0());
		}
	)
)?
;


// Rule FunctionBody
norm1_FunctionBody
@init {
}:
(
	((
		LeftCurlyBracket
	)
	)=>
	(
		BodyBlockParserRuleCall_1_0_0=norm1_Block{
			announce($BodyBlockParserRuleCall_1_0_0.start, $BodyBlockParserRuleCall_1_0_0.stop, grammarAccess.getFunctionBodyAccess().getBodyAssignment_1_0());
		}
	)
)?
;


// Rule FunctionBody
norm2_FunctionBody
@init {
}:
(
	((
		LeftCurlyBracket
	)
	)=>
	(
		BodyBlockParserRuleCall_0_0_0=ruleBlock{
			announce($BodyBlockParserRuleCall_0_0_0.start, $BodyBlockParserRuleCall_0_0_0.stop, grammarAccess.getFunctionBodyAccess().getBodyAssignment_0_0());
		}
	)
)
;


// Rule FunctionBody
norm3_FunctionBody
@init {
}:
(
	((
		LeftCurlyBracket
	)
	)=>
	(
		BodyBlockParserRuleCall_0_0_0=norm1_Block{
			announce($BodyBlockParserRuleCall_0_0_0.start, $BodyBlockParserRuleCall_0_0_0.stop, grammarAccess.getFunctionBodyAccess().getBodyAssignment_0_0());
		}
	)
)
;

// Entry rule entryRuleAnnotatedFunctionDeclaration
entryRuleAnnotatedFunctionDeclaration
	:
	ruleAnnotatedFunctionDeclaration
	EOF;

// Rule AnnotatedFunctionDeclaration
ruleAnnotatedFunctionDeclaration
@init {
}:
(
	(
		((
			CommercialAt
			(
				This
				    |
				Target
				    |
				RULE_IDENTIFIER
			)
		)
		)=>
		(
			AnnotationListAnnotationListParserRuleCall_0_0=ruleAnnotationList{
				announce($AnnotationListAnnotationListParserRuleCall_0_0.start, $AnnotationListAnnotationListParserRuleCall_0_0.stop, grammarAccess.getAnnotatedFunctionDeclarationAccess().getAnnotationListAssignment_0());
			}
		)
	)
	(
		(
			ruleN4Modifier
		)
	)*
	AsyncNoTrailingLineBreakParserRuleCall_2=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_2.start, $AsyncNoTrailingLineBreakParserRuleCall_2.stop, grammarAccess.getAnnotatedFunctionDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_2()); }
	FunctionImplParserRuleCall_3=ruleFunctionImpl{ announce($FunctionImplParserRuleCall_3.start, $FunctionImplParserRuleCall_3.stop, grammarAccess.getAnnotatedFunctionDeclarationAccess().getFunctionImplParserRuleCall_3()); }
)
;


// Rule AnnotatedFunctionDeclaration
norm1_AnnotatedFunctionDeclaration
@init {
}:
(
	(
		((
			CommercialAt
			(
				This
				    |
				Target
				    |
				RULE_IDENTIFIER
			)
		)
		)=>
		(
			AnnotationListAnnotationListParserRuleCall_0_0=ruleAnnotationList{
				announce($AnnotationListAnnotationListParserRuleCall_0_0.start, $AnnotationListAnnotationListParserRuleCall_0_0.stop, grammarAccess.getAnnotatedFunctionDeclarationAccess().getAnnotationListAssignment_0());
			}
		)
	)
	(
		(
			ruleN4Modifier
		)
	)*
	AsyncNoTrailingLineBreakParserRuleCall_2=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_2.start, $AsyncNoTrailingLineBreakParserRuleCall_2.stop, grammarAccess.getAnnotatedFunctionDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_2()); }
	FunctionImplParserRuleCall_3=norm3_FunctionImpl{ announce($FunctionImplParserRuleCall_3.start, $FunctionImplParserRuleCall_3.stop, grammarAccess.getAnnotatedFunctionDeclarationAccess().getFunctionImplParserRuleCall_3()); }
)
;

// Entry rule entryRuleFunctionExpression
entryRuleFunctionExpression
	:
	ruleFunctionExpression
	EOF;

// Rule FunctionExpression
ruleFunctionExpression
@init {
}:
(
	FunctionImplParserRuleCall_1=norm6_FunctionImpl{ announce($FunctionImplParserRuleCall_1.start, $FunctionImplParserRuleCall_1.stop, grammarAccess.getFunctionExpressionAccess().getFunctionImplParserRuleCall_1()); }
)
;

// Entry rule entryRuleAsyncFunctionExpression
entryRuleAsyncFunctionExpression
	:
	ruleAsyncFunctionExpression
	EOF;

// Rule AsyncFunctionExpression
ruleAsyncFunctionExpression
@init {
}:
(
	(
		((
			(
				(
					Async
				)
			)
			ruleNoLineTerminator
			Function
		)
		)=>
		(
			(
				(
					DeclaredAsyncAsyncKeyword_0_0_0_0=Async
					 {
						announce($DeclaredAsyncAsyncKeyword_0_0_0_0, grammarAccess.getAsyncFunctionExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_0());
					}
				)
			)
			NoLineTerminatorParserRuleCall_0_0_1=ruleNoLineTerminator{ announce($NoLineTerminatorParserRuleCall_0_0_1.start, $NoLineTerminatorParserRuleCall_0_0_1.stop, grammarAccess.getAsyncFunctionExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_1()); }
			FunctionKeyword_0_0_2=Function
			 {
				announce($FunctionKeyword_0_0_2, grammarAccess.getAsyncFunctionExpressionAccess().getFunctionKeyword_0_0_2());
			}
		)
	)
	FunctionHeaderParserRuleCall_1=ruleFunctionHeader{ announce($FunctionHeaderParserRuleCall_1.start, $FunctionHeaderParserRuleCall_1.stop, grammarAccess.getAsyncFunctionExpressionAccess().getFunctionHeaderParserRuleCall_1()); }
	FunctionBodyParserRuleCall_2=norm2_FunctionBody{ announce($FunctionBodyParserRuleCall_2.start, $FunctionBodyParserRuleCall_2.stop, grammarAccess.getAsyncFunctionExpressionAccess().getFunctionBodyParserRuleCall_2()); }
)
;

// Entry rule entryRuleArrowExpression
entryRuleArrowExpression
	:
	ruleArrowExpression
	EOF;

// Rule ArrowExpression
ruleArrowExpression
@init {
}:
(
	(
		((
			(
				(
					ruleStrictFormalParameters
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							(
								(LeftParenthesis
								)=>
								ruleStrictFormalParameters
							)
						)
					)
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						ruleBindingIdentifierAsFormalParameter
					)
				)
			)
			EqualsSignGreaterThanSign
		)
		)=>
		(
			(
				(
					StrictFormalParametersParserRuleCall_0_0_0_0_0=ruleStrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0_0_0_0_0.start, $StrictFormalParametersParserRuleCall_0_0_0_0_0.stop, grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_0_0()); }
					(
						ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1.start, $ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1.stop, grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_0_1()); }
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									DeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0=Async
									 {
										announce($DeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0, grammarAccess.getArrowExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0());
									}
								)
							)
							NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1=ruleNoLineTerminator{ announce($NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1.start, $NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1.stop, grammarAccess.getArrowExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1()); }
							(
								(LeftParenthesis
								)=>
								StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2=ruleStrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2.start, $StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2.stop, grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2()); }
							)
						)
					)
					(
						ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1.start, $ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1.stop, grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_1_1()); }
					)?
				)
				    |
				(
					(
						FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0=ruleBindingIdentifierAsFormalParameter{
							announce($FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0.start, $FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0.stop, grammarAccess.getArrowExpressionAccess().getFparsAssignment_0_0_0_2());
						}
					)
				)
			)
			EqualsSignGreaterThanSignKeyword_0_0_1=EqualsSignGreaterThanSign
			 {
				announce($EqualsSignGreaterThanSignKeyword_0_0_1, grammarAccess.getArrowExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_1());
			}
		)
	)
	(
		(
			(
				(LeftCurlyBracket
				)=>
				(
					HasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0=LeftCurlyBracket
					 {
						announce($HasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0, grammarAccess.getArrowExpressionAccess().getHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0());
					}
				)
			)
			(
				(
					BodyBlockMinusBracesParserRuleCall_1_0_1_0=ruleBlockMinusBraces{
						announce($BodyBlockMinusBracesParserRuleCall_1_0_1_0.start, $BodyBlockMinusBracesParserRuleCall_1_0_1_0.stop, grammarAccess.getArrowExpressionAccess().getBodyAssignment_1_0_1());
					}
				)
			)
			RightCurlyBracketKeyword_1_0_2=RightCurlyBracket
			 {
				announce($RightCurlyBracketKeyword_1_0_2, grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2());
			}
		)
		    |
		(
			(
				BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0=ruleExpressionDisguisedAsBlock{
					announce($BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0.start, $BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0.stop, grammarAccess.getArrowExpressionAccess().getBodyAssignment_1_1());
				}
			)
		)
	)
)
;


// Rule ArrowExpression
norm1_ArrowExpression
@init {
}:
(
	(
		((
			(
				(
					ruleStrictFormalParameters
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							(
								(LeftParenthesis
								)=>
								ruleStrictFormalParameters
							)
						)
					)
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						ruleBindingIdentifierAsFormalParameter
					)
				)
			)
			EqualsSignGreaterThanSign
		)
		)=>
		(
			(
				(
					StrictFormalParametersParserRuleCall_0_0_0_0_0=ruleStrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0_0_0_0_0.start, $StrictFormalParametersParserRuleCall_0_0_0_0_0.stop, grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_0_0()); }
					(
						ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1.start, $ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1.stop, grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_0_1()); }
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									DeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0=Async
									 {
										announce($DeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0, grammarAccess.getArrowExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0());
									}
								)
							)
							NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1=ruleNoLineTerminator{ announce($NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1.start, $NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1.stop, grammarAccess.getArrowExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1()); }
							(
								(LeftParenthesis
								)=>
								StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2=ruleStrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2.start, $StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2.stop, grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2()); }
							)
						)
					)
					(
						ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1.start, $ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1.stop, grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_1_1()); }
					)?
				)
				    |
				(
					(
						FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0=ruleBindingIdentifierAsFormalParameter{
							announce($FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0.start, $FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0.stop, grammarAccess.getArrowExpressionAccess().getFparsAssignment_0_0_0_2());
						}
					)
				)
			)
			EqualsSignGreaterThanSignKeyword_0_0_1=EqualsSignGreaterThanSign
			 {
				announce($EqualsSignGreaterThanSignKeyword_0_0_1, grammarAccess.getArrowExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_1());
			}
		)
	)
	(
		(
			(
				(LeftCurlyBracket
				)=>
				(
					HasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0=LeftCurlyBracket
					 {
						announce($HasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0, grammarAccess.getArrowExpressionAccess().getHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0());
					}
				)
			)
			(
				(
					BodyBlockMinusBracesParserRuleCall_1_0_1_0=ruleBlockMinusBraces{
						announce($BodyBlockMinusBracesParserRuleCall_1_0_1_0.start, $BodyBlockMinusBracesParserRuleCall_1_0_1_0.stop, grammarAccess.getArrowExpressionAccess().getBodyAssignment_1_0_1());
					}
				)
			)
			RightCurlyBracketKeyword_1_0_2=RightCurlyBracket
			 {
				announce($RightCurlyBracketKeyword_1_0_2, grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2());
			}
		)
		    |
		(
			(
				BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0=norm1_ExpressionDisguisedAsBlock{
					announce($BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0.start, $BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0.stop, grammarAccess.getArrowExpressionAccess().getBodyAssignment_1_1());
				}
			)
		)
	)
)
;


// Rule ArrowExpression
norm2_ArrowExpression
@init {
}:
(
	(
		((
			(
				(
					norm1_StrictFormalParameters
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							(
								(LeftParenthesis
								)=>
								norm1_StrictFormalParameters
							)
						)
					)
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						norm1_BindingIdentifierAsFormalParameter
					)
				)
			)
			EqualsSignGreaterThanSign
		)
		)=>
		(
			(
				(
					StrictFormalParametersParserRuleCall_0_0_0_0_0=norm1_StrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0_0_0_0_0.start, $StrictFormalParametersParserRuleCall_0_0_0_0_0.stop, grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_0_0()); }
					(
						ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1.start, $ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1.stop, grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_0_1()); }
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									DeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0=Async
									 {
										announce($DeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0, grammarAccess.getArrowExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0());
									}
								)
							)
							NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1=ruleNoLineTerminator{ announce($NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1.start, $NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1.stop, grammarAccess.getArrowExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1()); }
							(
								(LeftParenthesis
								)=>
								StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2=norm1_StrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2.start, $StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2.stop, grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2()); }
							)
						)
					)
					(
						ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1.start, $ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1.stop, grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_1_1()); }
					)?
				)
				    |
				(
					(
						FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0=norm1_BindingIdentifierAsFormalParameter{
							announce($FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0.start, $FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0.stop, grammarAccess.getArrowExpressionAccess().getFparsAssignment_0_0_0_2());
						}
					)
				)
			)
			EqualsSignGreaterThanSignKeyword_0_0_1=EqualsSignGreaterThanSign
			 {
				announce($EqualsSignGreaterThanSignKeyword_0_0_1, grammarAccess.getArrowExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_1());
			}
		)
	)
	(
		(
			(
				(LeftCurlyBracket
				)=>
				(
					HasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0=LeftCurlyBracket
					 {
						announce($HasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0, grammarAccess.getArrowExpressionAccess().getHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0());
					}
				)
			)
			(
				(
					BodyBlockMinusBracesParserRuleCall_1_0_1_0=norm1_BlockMinusBraces{
						announce($BodyBlockMinusBracesParserRuleCall_1_0_1_0.start, $BodyBlockMinusBracesParserRuleCall_1_0_1_0.stop, grammarAccess.getArrowExpressionAccess().getBodyAssignment_1_0_1());
					}
				)
			)
			RightCurlyBracketKeyword_1_0_2=RightCurlyBracket
			 {
				announce($RightCurlyBracketKeyword_1_0_2, grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2());
			}
		)
		    |
		(
			(
				BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0=ruleExpressionDisguisedAsBlock{
					announce($BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0.start, $BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0.stop, grammarAccess.getArrowExpressionAccess().getBodyAssignment_1_1());
				}
			)
		)
	)
)
;


// Rule ArrowExpression
norm3_ArrowExpression
@init {
}:
(
	(
		((
			(
				(
					norm1_StrictFormalParameters
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							(
								(LeftParenthesis
								)=>
								norm1_StrictFormalParameters
							)
						)
					)
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						norm1_BindingIdentifierAsFormalParameter
					)
				)
			)
			EqualsSignGreaterThanSign
		)
		)=>
		(
			(
				(
					StrictFormalParametersParserRuleCall_0_0_0_0_0=norm1_StrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0_0_0_0_0.start, $StrictFormalParametersParserRuleCall_0_0_0_0_0.stop, grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_0_0()); }
					(
						ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1.start, $ColonSepReturnTypeRefParserRuleCall_0_0_0_0_1.stop, grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_0_1()); }
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									DeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0=Async
									 {
										announce($DeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0, grammarAccess.getArrowExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0());
									}
								)
							)
							NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1=ruleNoLineTerminator{ announce($NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1.start, $NoLineTerminatorParserRuleCall_0_0_0_1_0_0_1.stop, grammarAccess.getArrowExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1()); }
							(
								(LeftParenthesis
								)=>
								StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2=norm1_StrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2.start, $StrictFormalParametersParserRuleCall_0_0_0_1_0_0_2.stop, grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2()); }
							)
						)
					)
					(
						ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1.start, $ColonSepReturnTypeRefParserRuleCall_0_0_0_1_1.stop, grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_1_1()); }
					)?
				)
				    |
				(
					(
						FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0=norm1_BindingIdentifierAsFormalParameter{
							announce($FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0.start, $FparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0.stop, grammarAccess.getArrowExpressionAccess().getFparsAssignment_0_0_0_2());
						}
					)
				)
			)
			EqualsSignGreaterThanSignKeyword_0_0_1=EqualsSignGreaterThanSign
			 {
				announce($EqualsSignGreaterThanSignKeyword_0_0_1, grammarAccess.getArrowExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_1());
			}
		)
	)
	(
		(
			(
				(LeftCurlyBracket
				)=>
				(
					HasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0=LeftCurlyBracket
					 {
						announce($HasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0, grammarAccess.getArrowExpressionAccess().getHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0());
					}
				)
			)
			(
				(
					BodyBlockMinusBracesParserRuleCall_1_0_1_0=norm1_BlockMinusBraces{
						announce($BodyBlockMinusBracesParserRuleCall_1_0_1_0.start, $BodyBlockMinusBracesParserRuleCall_1_0_1_0.stop, grammarAccess.getArrowExpressionAccess().getBodyAssignment_1_0_1());
					}
				)
			)
			RightCurlyBracketKeyword_1_0_2=RightCurlyBracket
			 {
				announce($RightCurlyBracketKeyword_1_0_2, grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2());
			}
		)
		    |
		(
			(
				BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0=norm1_ExpressionDisguisedAsBlock{
					announce($BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0.start, $BodyExpressionDisguisedAsBlockParserRuleCall_1_1_0.stop, grammarAccess.getArrowExpressionAccess().getBodyAssignment_1_1());
				}
			)
		)
	)
)
;


// Rule StrictFormalParameters
ruleStrictFormalParameters
@init {
}:
(
	LeftParenthesisKeyword_0=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_0, grammarAccess.getStrictFormalParametersAccess().getLeftParenthesisKeyword_0());
	}
	(
		(
			(
				FparsFormalParameterParserRuleCall_1_0_0=ruleFormalParameter{
					announce($FparsFormalParameterParserRuleCall_1_0_0.start, $FparsFormalParameterParserRuleCall_1_0_0.stop, grammarAccess.getStrictFormalParametersAccess().getFparsAssignment_1_0());
				}
			)
		)
		(
			CommaKeyword_1_1_0=Comma
			 {
				announce($CommaKeyword_1_1_0, grammarAccess.getStrictFormalParametersAccess().getCommaKeyword_1_1_0());
			}
			(
				(
					FparsFormalParameterParserRuleCall_1_1_1_0=ruleFormalParameter{
						announce($FparsFormalParameterParserRuleCall_1_1_1_0.start, $FparsFormalParameterParserRuleCall_1_1_1_0.stop, grammarAccess.getStrictFormalParametersAccess().getFparsAssignment_1_1_1());
					}
				)
			)
		)*
	)?
	RightParenthesisKeyword_2=RightParenthesis
	 {
		announce($RightParenthesisKeyword_2, grammarAccess.getStrictFormalParametersAccess().getRightParenthesisKeyword_2());
	}
)
;


// Rule StrictFormalParameters
norm1_StrictFormalParameters
@init {
}:
(
	LeftParenthesisKeyword_0=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_0, grammarAccess.getStrictFormalParametersAccess().getLeftParenthesisKeyword_0());
	}
	(
		(
			(
				FparsFormalParameterParserRuleCall_1_0_0=norm1_FormalParameter{
					announce($FparsFormalParameterParserRuleCall_1_0_0.start, $FparsFormalParameterParserRuleCall_1_0_0.stop, grammarAccess.getStrictFormalParametersAccess().getFparsAssignment_1_0());
				}
			)
		)
		(
			CommaKeyword_1_1_0=Comma
			 {
				announce($CommaKeyword_1_1_0, grammarAccess.getStrictFormalParametersAccess().getCommaKeyword_1_1_0());
			}
			(
				(
					FparsFormalParameterParserRuleCall_1_1_1_0=norm1_FormalParameter{
						announce($FparsFormalParameterParserRuleCall_1_1_1_0.start, $FparsFormalParameterParserRuleCall_1_1_1_0.stop, grammarAccess.getStrictFormalParametersAccess().getFparsAssignment_1_1_1());
					}
				)
			)
		)*
	)?
	RightParenthesisKeyword_2=RightParenthesis
	 {
		announce($RightParenthesisKeyword_2, grammarAccess.getStrictFormalParametersAccess().getRightParenthesisKeyword_2());
	}
)
;

// Entry rule entryRuleBindingIdentifierAsFormalParameter
entryRuleBindingIdentifierAsFormalParameter
	:
	ruleBindingIdentifierAsFormalParameter
	EOF;

// Rule BindingIdentifierAsFormalParameter
ruleBindingIdentifierAsFormalParameter
@init {
}:
(
	(
		NameBindingIdentifierParserRuleCall_0=ruleBindingIdentifier{
			announce($NameBindingIdentifierParserRuleCall_0.start, $NameBindingIdentifierParserRuleCall_0.stop, grammarAccess.getBindingIdentifierAsFormalParameterAccess().getNameAssignment());
		}
	)
)
;


// Rule BindingIdentifierAsFormalParameter
norm1_BindingIdentifierAsFormalParameter
@init {
}:
(
	(
		NameBindingIdentifierParserRuleCall_0=norm1_BindingIdentifier{
			announce($NameBindingIdentifierParserRuleCall_0.start, $NameBindingIdentifierParserRuleCall_0.stop, grammarAccess.getBindingIdentifierAsFormalParameterAccess().getNameAssignment());
		}
	)
)
;

// Entry rule entryRuleBlockMinusBraces
entryRuleBlockMinusBraces
	:
	ruleBlockMinusBraces
	EOF;

// Rule BlockMinusBraces
ruleBlockMinusBraces
@init {
}:
(
	(
		(
			StatementsStatementParserRuleCall_1_0=ruleStatement{
				announce($StatementsStatementParserRuleCall_1_0.start, $StatementsStatementParserRuleCall_1_0.stop, grammarAccess.getBlockMinusBracesAccess().getStatementsAssignment_1());
			}
		)
	)*
)
;


// Rule BlockMinusBraces
norm1_BlockMinusBraces
@init {
}:
(
	(
		(
			StatementsStatementParserRuleCall_1_0=norm1_Statement{
				announce($StatementsStatementParserRuleCall_1_0.start, $StatementsStatementParserRuleCall_1_0.stop, grammarAccess.getBlockMinusBracesAccess().getStatementsAssignment_1());
			}
		)
	)*
)
;

// Entry rule entryRuleExpressionDisguisedAsBlock
entryRuleExpressionDisguisedAsBlock
	:
	ruleExpressionDisguisedAsBlock
	EOF;

// Rule ExpressionDisguisedAsBlock
ruleExpressionDisguisedAsBlock
@init {
}:
(
	(
		(
			StatementsAssignmentExpressionStatementParserRuleCall_1_0=ruleAssignmentExpressionStatement{
				announce($StatementsAssignmentExpressionStatementParserRuleCall_1_0.start, $StatementsAssignmentExpressionStatementParserRuleCall_1_0.stop, grammarAccess.getExpressionDisguisedAsBlockAccess().getStatementsAssignment_1());
			}
		)
	)
)
;


// Rule ExpressionDisguisedAsBlock
norm1_ExpressionDisguisedAsBlock
@init {
}:
(
	(
		(
			StatementsAssignmentExpressionStatementParserRuleCall_1_0=norm1_AssignmentExpressionStatement{
				announce($StatementsAssignmentExpressionStatementParserRuleCall_1_0.start, $StatementsAssignmentExpressionStatementParserRuleCall_1_0.stop, grammarAccess.getExpressionDisguisedAsBlockAccess().getStatementsAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleAssignmentExpressionStatement
entryRuleAssignmentExpressionStatement
	:
	ruleAssignmentExpressionStatement
	EOF;

// Rule AssignmentExpressionStatement
ruleAssignmentExpressionStatement
@init {
}:
(
	(
		ExpressionAssignmentExpressionParserRuleCall_0=ruleAssignmentExpression{
			announce($ExpressionAssignmentExpressionParserRuleCall_0.start, $ExpressionAssignmentExpressionParserRuleCall_0.stop, grammarAccess.getAssignmentExpressionStatementAccess().getExpressionAssignment());
		}
	)
)
;


// Rule AssignmentExpressionStatement
norm1_AssignmentExpressionStatement
@init {
}:
(
	(
		ExpressionAssignmentExpressionParserRuleCall_0=norm1_AssignmentExpression{
			announce($ExpressionAssignmentExpressionParserRuleCall_0.start, $ExpressionAssignmentExpressionParserRuleCall_0.stop, grammarAccess.getAssignmentExpressionStatementAccess().getExpressionAssignment());
		}
	)
)
;

// Entry rule entryRuleAnnotatedExpression
entryRuleAnnotatedExpression
	:
	ruleAnnotatedExpression
	EOF;

// Rule AnnotatedExpression
ruleAnnotatedExpression
@init {
}:
(
	ExpressionAnnotationListParserRuleCall_0=ruleExpressionAnnotationList{ announce($ExpressionAnnotationListParserRuleCall_0.start, $ExpressionAnnotationListParserRuleCall_0.stop, grammarAccess.getAnnotatedExpressionAccess().getExpressionAnnotationListParserRuleCall_0()); }
	(
		(
			ClassKeyword_1_0_1=Class
			 {
				announce($ClassKeyword_1_0_1, grammarAccess.getAnnotatedExpressionAccess().getClassKeyword_1_0_1());
			}
			(
				(
					NameBindingIdentifierParserRuleCall_1_0_2_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_0_2_0.start, $NameBindingIdentifierParserRuleCall_1_0_2_0.stop, grammarAccess.getAnnotatedExpressionAccess().getNameAssignment_1_0_2());
					}
				)
			)?
			(
				ClassExtendsImplementsParserRuleCall_1_0_3=ruleClassExtendsImplements{ announce($ClassExtendsImplementsParserRuleCall_1_0_3.start, $ClassExtendsImplementsParserRuleCall_1_0_3.stop, grammarAccess.getAnnotatedExpressionAccess().getClassExtendsImplementsParserRuleCall_1_0_3()); }
			)?
			MembersParserRuleCall_1_0_4=ruleMembers{ announce($MembersParserRuleCall_1_0_4.start, $MembersParserRuleCall_1_0_4.stop, grammarAccess.getAnnotatedExpressionAccess().getMembersParserRuleCall_1_0_4()); }
		)
		    |
		(
			AsyncNoTrailingLineBreakParserRuleCall_1_1_1=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_1_1_1.start, $AsyncNoTrailingLineBreakParserRuleCall_1_1_1.stop, grammarAccess.getAnnotatedExpressionAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_1_1()); }
			FunctionImplParserRuleCall_1_1_2=norm6_FunctionImpl{ announce($FunctionImplParserRuleCall_1_1_2.start, $FunctionImplParserRuleCall_1_1_2.stop, grammarAccess.getAnnotatedExpressionAccess().getFunctionImplParserRuleCall_1_1_2()); }
		)
	)
)
;


// Rule AnnotatedExpression
norm1_AnnotatedExpression
@init {
}:
(
	ExpressionAnnotationListParserRuleCall_0=ruleExpressionAnnotationList{ announce($ExpressionAnnotationListParserRuleCall_0.start, $ExpressionAnnotationListParserRuleCall_0.stop, grammarAccess.getAnnotatedExpressionAccess().getExpressionAnnotationListParserRuleCall_0()); }
	(
		(
			ClassKeyword_1_0_1=Class
			 {
				announce($ClassKeyword_1_0_1, grammarAccess.getAnnotatedExpressionAccess().getClassKeyword_1_0_1());
			}
			(
				(
					NameBindingIdentifierParserRuleCall_1_0_2_0=norm1_BindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_0_2_0.start, $NameBindingIdentifierParserRuleCall_1_0_2_0.stop, grammarAccess.getAnnotatedExpressionAccess().getNameAssignment_1_0_2());
					}
				)
			)?
			(
				ClassExtendsImplementsParserRuleCall_1_0_3=norm1_ClassExtendsImplements{ announce($ClassExtendsImplementsParserRuleCall_1_0_3.start, $ClassExtendsImplementsParserRuleCall_1_0_3.stop, grammarAccess.getAnnotatedExpressionAccess().getClassExtendsImplementsParserRuleCall_1_0_3()); }
			)?
			MembersParserRuleCall_1_0_4=norm1_Members{ announce($MembersParserRuleCall_1_0_4.start, $MembersParserRuleCall_1_0_4.stop, grammarAccess.getAnnotatedExpressionAccess().getMembersParserRuleCall_1_0_4()); }
		)
		    |
		(
			AsyncNoTrailingLineBreakParserRuleCall_1_1_1=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_1_1_1.start, $AsyncNoTrailingLineBreakParserRuleCall_1_1_1.stop, grammarAccess.getAnnotatedExpressionAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_1_1()); }
			FunctionImplParserRuleCall_1_1_2=norm6_FunctionImpl{ announce($FunctionImplParserRuleCall_1_1_2.start, $FunctionImplParserRuleCall_1_1_2.stop, grammarAccess.getAnnotatedExpressionAccess().getFunctionImplParserRuleCall_1_1_2()); }
		)
	)
)
;

// Entry rule entryRuleTypeVariable
entryRuleTypeVariable
	:
	ruleTypeVariable
	EOF;

// Rule TypeVariable
ruleTypeVariable
@init {
}:
(
	(
		(
			(
				DeclaredCovariantOutKeyword_0_0_0=Out
				 {
					announce($DeclaredCovariantOutKeyword_0_0_0, grammarAccess.getTypeVariableAccess().getDeclaredCovariantOutKeyword_0_0_0());
				}
			)
		)
		    |
		(
			(
				DeclaredContravariantInKeyword_0_1_0=In
				 {
					announce($DeclaredContravariantInKeyword_0_1_0, grammarAccess.getTypeVariableAccess().getDeclaredContravariantInKeyword_0_1_0());
				}
			)
		)
	)?
	(
		(
			NameIdentifierOrThisParserRuleCall_1_0=ruleIdentifierOrThis{
				announce($NameIdentifierOrThisParserRuleCall_1_0.start, $NameIdentifierOrThisParserRuleCall_1_0.stop, grammarAccess.getTypeVariableAccess().getNameAssignment_1());
			}
		)
	)
	(
		ExtendsKeyword_2_0=Extends
		 {
			announce($ExtendsKeyword_2_0, grammarAccess.getTypeVariableAccess().getExtendsKeyword_2_0());
		}
		(
			(
				DeclaredUpperBoundTypeRefParserRuleCall_2_1_0=ruleTypeRef{
					announce($DeclaredUpperBoundTypeRefParserRuleCall_2_1_0.start, $DeclaredUpperBoundTypeRefParserRuleCall_2_1_0.stop, grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundAssignment_2_1());
				}
			)
		)
	)?
)
;

// Entry rule entryRuleFormalParameter
entryRuleFormalParameter
	:
	ruleFormalParameter
	EOF;

// Rule FormalParameter
ruleFormalParameter
@init {
}:
(
	BindingElementFragmentParserRuleCall_1=ruleBindingElementFragment{ announce($BindingElementFragmentParserRuleCall_1.start, $BindingElementFragmentParserRuleCall_1.stop, grammarAccess.getFormalParameterAccess().getBindingElementFragmentParserRuleCall_1()); }
)
;


// Rule FormalParameter
norm1_FormalParameter
@init {
}:
(
	BindingElementFragmentParserRuleCall_1=norm1_BindingElementFragment{ announce($BindingElementFragmentParserRuleCall_1.start, $BindingElementFragmentParserRuleCall_1.stop, grammarAccess.getFormalParameterAccess().getBindingElementFragmentParserRuleCall_1()); }
)
;


// Rule BindingElementFragment
ruleBindingElementFragment
@init {
}:
(
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			(
				BindingPatternBindingPatternParserRuleCall_0_0_0=ruleBindingPattern{
					announce($BindingPatternBindingPatternParserRuleCall_0_0_0.start, $BindingPatternBindingPatternParserRuleCall_0_0_0.stop, grammarAccess.getBindingElementFragmentAccess().getBindingPatternAssignment_0_0());
				}
			)
		)
		    |
		(
			(
				(
					AnnotationsAnnotationParserRuleCall_0_1_0_0=ruleAnnotation{
						announce($AnnotationsAnnotationParserRuleCall_0_1_0_0.start, $AnnotationsAnnotationParserRuleCall_0_1_0_0.stop, grammarAccess.getBindingElementFragmentAccess().getAnnotationsAssignment_0_1_0());
					}
				)
			)*
			(
				BogusTypeRefFragmentParserRuleCall_0_1_1=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_0_1_1.start, $BogusTypeRefFragmentParserRuleCall_0_1_1.stop, grammarAccess.getBindingElementFragmentAccess().getBogusTypeRefFragmentParserRuleCall_0_1_1()); }
			)?
			(
				(
					VariadicFullStopFullStopFullStopKeyword_0_1_2_0=FullStopFullStopFullStop
					 {
						announce($VariadicFullStopFullStopFullStopKeyword_0_1_2_0, grammarAccess.getBindingElementFragmentAccess().getVariadicFullStopFullStopFullStopKeyword_0_1_2_0());
					}
				)
			)?
			(
				(
					NameBindingIdentifierParserRuleCall_0_1_3_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_0_1_3_0.start, $NameBindingIdentifierParserRuleCall_0_1_3_0.stop, grammarAccess.getBindingElementFragmentAccess().getNameAssignment_0_1_3());
					}
				)
			)
			(
				ColonSepDeclaredTypeRefParserRuleCall_0_1_4=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_0_1_4.start, $ColonSepDeclaredTypeRefParserRuleCall_0_1_4.stop, grammarAccess.getBindingElementFragmentAccess().getColonSepDeclaredTypeRefParserRuleCall_0_1_4()); }
			)?
		)
	)
	(
		(
			(
				HasInitializerAssignmentEqualsSignKeyword_1_0_0=EqualsSign
				 {
					announce($HasInitializerAssignmentEqualsSignKeyword_1_0_0, grammarAccess.getBindingElementFragmentAccess().getHasInitializerAssignmentEqualsSignKeyword_1_0_0());
				}
			)
		)
		(
			(
				InitializerAssignmentExpressionParserRuleCall_1_1_0=norm1_AssignmentExpression{
					announce($InitializerAssignmentExpressionParserRuleCall_1_1_0.start, $InitializerAssignmentExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBindingElementFragmentAccess().getInitializerAssignment_1_1());
				}
			)
		)?
	)?
)
;


// Rule BindingElementFragment
norm1_BindingElementFragment
@init {
}:
(
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			(
				BindingPatternBindingPatternParserRuleCall_0_0_0=norm1_BindingPattern{
					announce($BindingPatternBindingPatternParserRuleCall_0_0_0.start, $BindingPatternBindingPatternParserRuleCall_0_0_0.stop, grammarAccess.getBindingElementFragmentAccess().getBindingPatternAssignment_0_0());
				}
			)
		)
		    |
		(
			(
				(
					AnnotationsAnnotationParserRuleCall_0_1_0_0=ruleAnnotation{
						announce($AnnotationsAnnotationParserRuleCall_0_1_0_0.start, $AnnotationsAnnotationParserRuleCall_0_1_0_0.stop, grammarAccess.getBindingElementFragmentAccess().getAnnotationsAssignment_0_1_0());
					}
				)
			)*
			(
				BogusTypeRefFragmentParserRuleCall_0_1_1=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_0_1_1.start, $BogusTypeRefFragmentParserRuleCall_0_1_1.stop, grammarAccess.getBindingElementFragmentAccess().getBogusTypeRefFragmentParserRuleCall_0_1_1()); }
			)?
			(
				(
					VariadicFullStopFullStopFullStopKeyword_0_1_2_0=FullStopFullStopFullStop
					 {
						announce($VariadicFullStopFullStopFullStopKeyword_0_1_2_0, grammarAccess.getBindingElementFragmentAccess().getVariadicFullStopFullStopFullStopKeyword_0_1_2_0());
					}
				)
			)?
			(
				(
					NameBindingIdentifierParserRuleCall_0_1_3_0=norm1_BindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_0_1_3_0.start, $NameBindingIdentifierParserRuleCall_0_1_3_0.stop, grammarAccess.getBindingElementFragmentAccess().getNameAssignment_0_1_3());
					}
				)
			)
			(
				ColonSepDeclaredTypeRefParserRuleCall_0_1_4=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_0_1_4.start, $ColonSepDeclaredTypeRefParserRuleCall_0_1_4.stop, grammarAccess.getBindingElementFragmentAccess().getColonSepDeclaredTypeRefParserRuleCall_0_1_4()); }
			)?
		)
	)
	(
		(
			(
				HasInitializerAssignmentEqualsSignKeyword_1_0_0=EqualsSign
				 {
					announce($HasInitializerAssignmentEqualsSignKeyword_1_0_0, grammarAccess.getBindingElementFragmentAccess().getHasInitializerAssignmentEqualsSignKeyword_1_0_0());
				}
			)
		)
		(
			(
				InitializerAssignmentExpressionParserRuleCall_1_1_0=norm3_AssignmentExpression{
					announce($InitializerAssignmentExpressionParserRuleCall_1_1_0.start, $InitializerAssignmentExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBindingElementFragmentAccess().getInitializerAssignment_1_1());
				}
			)
		)?
	)?
)
;


// Rule BogusTypeRefFragment
ruleBogusTypeRefFragment
@init {
}:
(
	(
		BogusTypeRefTypeRefWithModifiersParserRuleCall_0=ruleTypeRefWithModifiers{
			announce($BogusTypeRefTypeRefWithModifiersParserRuleCall_0.start, $BogusTypeRefTypeRefWithModifiersParserRuleCall_0.stop, grammarAccess.getBogusTypeRefFragmentAccess().getBogusTypeRefAssignment());
		}
	)
)
;

// Entry rule entryRuleBlock
entryRuleBlock
	:
	ruleBlock
	EOF;

// Rule Block
ruleBlock
@init {
}:
(
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			LeftCurlyBracketKeyword_0_0_1=LeftCurlyBracket
			 {
				announce($LeftCurlyBracketKeyword_0_0_1, grammarAccess.getBlockAccess().getLeftCurlyBracketKeyword_0_0_1());
			}
		)
	)
	(
		(
			StatementsStatementParserRuleCall_1_0=ruleStatement{
				announce($StatementsStatementParserRuleCall_1_0.start, $StatementsStatementParserRuleCall_1_0.stop, grammarAccess.getBlockAccess().getStatementsAssignment_1());
			}
		)
	)*
	RightCurlyBracketKeyword_2=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_2, grammarAccess.getBlockAccess().getRightCurlyBracketKeyword_2());
	}
)
;


// Rule Block
norm1_Block
@init {
}:
(
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			LeftCurlyBracketKeyword_0_0_1=LeftCurlyBracket
			 {
				announce($LeftCurlyBracketKeyword_0_0_1, grammarAccess.getBlockAccess().getLeftCurlyBracketKeyword_0_0_1());
			}
		)
	)
	(
		(
			StatementsStatementParserRuleCall_1_0=norm1_Statement{
				announce($StatementsStatementParserRuleCall_1_0.start, $StatementsStatementParserRuleCall_1_0.stop, grammarAccess.getBlockAccess().getStatementsAssignment_1());
			}
		)
	)*
	RightCurlyBracketKeyword_2=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_2, grammarAccess.getBlockAccess().getRightCurlyBracketKeyword_2());
	}
)
;

// Entry rule entryRuleRootStatement
entryRuleRootStatement
	:
	ruleRootStatement
	EOF;

// Rule RootStatement
ruleRootStatement
@init {
}:
(
	(
		((
			LeftCurlyBracket
		)
		)=>
		BlockParserRuleCall_0=ruleBlock{ announce($BlockParserRuleCall_0.start, $BlockParserRuleCall_0.stop, grammarAccess.getRootStatementAccess().getBlockParserRuleCall_0()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			ruleAsyncNoTrailingLineBreak
			Function
		)
		)=>
		FunctionDeclarationParserRuleCall_1=ruleFunctionDeclaration{ announce($FunctionDeclarationParserRuleCall_1.start, $FunctionDeclarationParserRuleCall_1.stop, grammarAccess.getRootStatementAccess().getFunctionDeclarationParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					ruleVariableStatementKeyword
				)
			)
		)
		)=>
		VariableStatementParserRuleCall_2=norm1_VariableStatement{ announce($VariableStatementParserRuleCall_2.start, $VariableStatementParserRuleCall_2.stop, grammarAccess.getRootStatementAccess().getVariableStatementParserRuleCall_2()); }
	)
	    |
	EmptyStatementParserRuleCall_3=ruleEmptyStatement{ announce($EmptyStatementParserRuleCall_3.start, $EmptyStatementParserRuleCall_3.stop, grammarAccess.getRootStatementAccess().getEmptyStatementParserRuleCall_3()); }
	    |
	(
		((
			(
				(
					ruleBindingIdentifier
				)
			)
			Colon
		)
		)=>
		LabelledStatementParserRuleCall_4=ruleLabelledStatement{ announce($LabelledStatementParserRuleCall_4.start, $LabelledStatementParserRuleCall_4.stop, grammarAccess.getRootStatementAccess().getLabelledStatementParserRuleCall_4()); }
	)
	    |
	ExpressionStatementParserRuleCall_5=ruleExpressionStatement{ announce($ExpressionStatementParserRuleCall_5.start, $ExpressionStatementParserRuleCall_5.stop, grammarAccess.getRootStatementAccess().getExpressionStatementParserRuleCall_5()); }
	    |
	IfStatementParserRuleCall_6=ruleIfStatement{ announce($IfStatementParserRuleCall_6.start, $IfStatementParserRuleCall_6.stop, grammarAccess.getRootStatementAccess().getIfStatementParserRuleCall_6()); }
	    |
	IterationStatementParserRuleCall_7=ruleIterationStatement{ announce($IterationStatementParserRuleCall_7.start, $IterationStatementParserRuleCall_7.stop, grammarAccess.getRootStatementAccess().getIterationStatementParserRuleCall_7()); }
	    |
	ContinueStatementParserRuleCall_8=ruleContinueStatement{ announce($ContinueStatementParserRuleCall_8.start, $ContinueStatementParserRuleCall_8.stop, grammarAccess.getRootStatementAccess().getContinueStatementParserRuleCall_8()); }
	    |
	BreakStatementParserRuleCall_9=ruleBreakStatement{ announce($BreakStatementParserRuleCall_9.start, $BreakStatementParserRuleCall_9.stop, grammarAccess.getRootStatementAccess().getBreakStatementParserRuleCall_9()); }
	    |
	ReturnStatementParserRuleCall_10=ruleReturnStatement{ announce($ReturnStatementParserRuleCall_10.start, $ReturnStatementParserRuleCall_10.stop, grammarAccess.getRootStatementAccess().getReturnStatementParserRuleCall_10()); }
	    |
	WithStatementParserRuleCall_11=ruleWithStatement{ announce($WithStatementParserRuleCall_11.start, $WithStatementParserRuleCall_11.stop, grammarAccess.getRootStatementAccess().getWithStatementParserRuleCall_11()); }
	    |
	SwitchStatementParserRuleCall_12=ruleSwitchStatement{ announce($SwitchStatementParserRuleCall_12.start, $SwitchStatementParserRuleCall_12.stop, grammarAccess.getRootStatementAccess().getSwitchStatementParserRuleCall_12()); }
	    |
	ThrowStatementParserRuleCall_13=ruleThrowStatement{ announce($ThrowStatementParserRuleCall_13.start, $ThrowStatementParserRuleCall_13.stop, grammarAccess.getRootStatementAccess().getThrowStatementParserRuleCall_13()); }
	    |
	TryStatementParserRuleCall_14=ruleTryStatement{ announce($TryStatementParserRuleCall_14.start, $TryStatementParserRuleCall_14.stop, grammarAccess.getRootStatementAccess().getTryStatementParserRuleCall_14()); }
	    |
	DebuggerStatementParserRuleCall_15=ruleDebuggerStatement{ announce($DebuggerStatementParserRuleCall_15.start, $DebuggerStatementParserRuleCall_15.stop, grammarAccess.getRootStatementAccess().getDebuggerStatementParserRuleCall_15()); }
)
;


// Rule RootStatement
norm1_RootStatement
@init {
}:
(
	(
		((
			LeftCurlyBracket
		)
		)=>
		BlockParserRuleCall_0=norm1_Block{ announce($BlockParserRuleCall_0.start, $BlockParserRuleCall_0.stop, grammarAccess.getRootStatementAccess().getBlockParserRuleCall_0()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			ruleAsyncNoTrailingLineBreak
			Function
		)
		)=>
		FunctionDeclarationParserRuleCall_1=norm1_FunctionDeclaration{ announce($FunctionDeclarationParserRuleCall_1.start, $FunctionDeclarationParserRuleCall_1.stop, grammarAccess.getRootStatementAccess().getFunctionDeclarationParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					ruleVariableStatementKeyword
				)
			)
		)
		)=>
		VariableStatementParserRuleCall_2=norm3_VariableStatement{ announce($VariableStatementParserRuleCall_2.start, $VariableStatementParserRuleCall_2.stop, grammarAccess.getRootStatementAccess().getVariableStatementParserRuleCall_2()); }
	)
	    |
	EmptyStatementParserRuleCall_3=ruleEmptyStatement{ announce($EmptyStatementParserRuleCall_3.start, $EmptyStatementParserRuleCall_3.stop, grammarAccess.getRootStatementAccess().getEmptyStatementParserRuleCall_3()); }
	    |
	(
		((
			(
				(
					norm1_BindingIdentifier
				)
			)
			Colon
		)
		)=>
		LabelledStatementParserRuleCall_4=norm1_LabelledStatement{ announce($LabelledStatementParserRuleCall_4.start, $LabelledStatementParserRuleCall_4.stop, grammarAccess.getRootStatementAccess().getLabelledStatementParserRuleCall_4()); }
	)
	    |
	ExpressionStatementParserRuleCall_5=norm1_ExpressionStatement{ announce($ExpressionStatementParserRuleCall_5.start, $ExpressionStatementParserRuleCall_5.stop, grammarAccess.getRootStatementAccess().getExpressionStatementParserRuleCall_5()); }
	    |
	IfStatementParserRuleCall_6=norm1_IfStatement{ announce($IfStatementParserRuleCall_6.start, $IfStatementParserRuleCall_6.stop, grammarAccess.getRootStatementAccess().getIfStatementParserRuleCall_6()); }
	    |
	IterationStatementParserRuleCall_7=norm1_IterationStatement{ announce($IterationStatementParserRuleCall_7.start, $IterationStatementParserRuleCall_7.stop, grammarAccess.getRootStatementAccess().getIterationStatementParserRuleCall_7()); }
	    |
	ContinueStatementParserRuleCall_8=norm1_ContinueStatement{ announce($ContinueStatementParserRuleCall_8.start, $ContinueStatementParserRuleCall_8.stop, grammarAccess.getRootStatementAccess().getContinueStatementParserRuleCall_8()); }
	    |
	BreakStatementParserRuleCall_9=norm1_BreakStatement{ announce($BreakStatementParserRuleCall_9.start, $BreakStatementParserRuleCall_9.stop, grammarAccess.getRootStatementAccess().getBreakStatementParserRuleCall_9()); }
	    |
	ReturnStatementParserRuleCall_10=norm1_ReturnStatement{ announce($ReturnStatementParserRuleCall_10.start, $ReturnStatementParserRuleCall_10.stop, grammarAccess.getRootStatementAccess().getReturnStatementParserRuleCall_10()); }
	    |
	WithStatementParserRuleCall_11=norm1_WithStatement{ announce($WithStatementParserRuleCall_11.start, $WithStatementParserRuleCall_11.stop, grammarAccess.getRootStatementAccess().getWithStatementParserRuleCall_11()); }
	    |
	SwitchStatementParserRuleCall_12=norm1_SwitchStatement{ announce($SwitchStatementParserRuleCall_12.start, $SwitchStatementParserRuleCall_12.stop, grammarAccess.getRootStatementAccess().getSwitchStatementParserRuleCall_12()); }
	    |
	ThrowStatementParserRuleCall_13=norm1_ThrowStatement{ announce($ThrowStatementParserRuleCall_13.start, $ThrowStatementParserRuleCall_13.stop, grammarAccess.getRootStatementAccess().getThrowStatementParserRuleCall_13()); }
	    |
	TryStatementParserRuleCall_14=norm1_TryStatement{ announce($TryStatementParserRuleCall_14.start, $TryStatementParserRuleCall_14.stop, grammarAccess.getRootStatementAccess().getTryStatementParserRuleCall_14()); }
	    |
	DebuggerStatementParserRuleCall_15=ruleDebuggerStatement{ announce($DebuggerStatementParserRuleCall_15.start, $DebuggerStatementParserRuleCall_15.stop, grammarAccess.getRootStatementAccess().getDebuggerStatementParserRuleCall_15()); }
)
;

// Entry rule entryRuleStatement
entryRuleStatement
	:
	ruleStatement
	EOF;

// Rule Statement
ruleStatement
@init {
}:
(
	(
		((
			CommercialAt
			(
				This
				    |
				Target
				    |
				RULE_IDENTIFIER
			)
		)
		)=>
		AnnotatedFunctionDeclarationParserRuleCall_0=ruleAnnotatedFunctionDeclaration{ announce($AnnotatedFunctionDeclarationParserRuleCall_0.start, $AnnotatedFunctionDeclarationParserRuleCall_0.stop, grammarAccess.getStatementAccess().getAnnotatedFunctionDeclarationParserRuleCall_0()); }
	)
	    |
	RootStatementParserRuleCall_1=ruleRootStatement{ announce($RootStatementParserRuleCall_1.start, $RootStatementParserRuleCall_1.stop, grammarAccess.getStatementAccess().getRootStatementParserRuleCall_1()); }
)
;


// Rule Statement
norm1_Statement
@init {
}:
(
	(
		((
			CommercialAt
			(
				This
				    |
				Target
				    |
				RULE_IDENTIFIER
			)
		)
		)=>
		AnnotatedFunctionDeclarationParserRuleCall_0=norm1_AnnotatedFunctionDeclaration{ announce($AnnotatedFunctionDeclarationParserRuleCall_0.start, $AnnotatedFunctionDeclarationParserRuleCall_0.stop, grammarAccess.getStatementAccess().getAnnotatedFunctionDeclarationParserRuleCall_0()); }
	)
	    |
	RootStatementParserRuleCall_1=norm1_RootStatement{ announce($RootStatementParserRuleCall_1.start, $RootStatementParserRuleCall_1.stop, grammarAccess.getStatementAccess().getRootStatementParserRuleCall_1()); }
)
;


// Rule VariableStatement
norm1_VariableStatement
@init {
}:
(
	(
		((
			(
				(
					ruleVariableStatementKeyword
				)
			)
		)
		)=>
		(
			(
				(
					ruleVariableStatementKeyword
				)
			)
		)
	)
	(
		(
			VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0=norm1_VariableDeclarationOrBinding{
				announce($VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0.start, $VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0.stop, grammarAccess.getVariableStatementAccess().getVarDeclsOrBindingsAssignment_1());
			}
		)
	)
	(
		CommaKeyword_2_0=Comma
		 {
			announce($CommaKeyword_2_0, grammarAccess.getVariableStatementAccess().getCommaKeyword_2_0());
		}
		(
			(
				VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0=norm1_VariableDeclarationOrBinding{
					announce($VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0.start, $VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0.stop, grammarAccess.getVariableStatementAccess().getVarDeclsOrBindingsAssignment_2_1());
				}
			)
		)
	)*
	SemiParserRuleCall_3=ruleSemi{ announce($SemiParserRuleCall_3.start, $SemiParserRuleCall_3.stop, grammarAccess.getVariableStatementAccess().getSemiParserRuleCall_3()); }
)
;


// Rule VariableStatement
norm3_VariableStatement
@init {
}:
(
	(
		((
			(
				(
					ruleVariableStatementKeyword
				)
			)
		)
		)=>
		(
			(
				(
					ruleVariableStatementKeyword
				)
			)
		)
	)
	(
		(
			VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0=norm3_VariableDeclarationOrBinding{
				announce($VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0.start, $VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0.stop, grammarAccess.getVariableStatementAccess().getVarDeclsOrBindingsAssignment_1());
			}
		)
	)
	(
		CommaKeyword_2_0=Comma
		 {
			announce($CommaKeyword_2_0, grammarAccess.getVariableStatementAccess().getCommaKeyword_2_0());
		}
		(
			(
				VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0=norm3_VariableDeclarationOrBinding{
					announce($VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0.start, $VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0.stop, grammarAccess.getVariableStatementAccess().getVarDeclsOrBindingsAssignment_2_1());
				}
			)
		)
	)*
	SemiParserRuleCall_3=ruleSemi{ announce($SemiParserRuleCall_3.start, $SemiParserRuleCall_3.stop, grammarAccess.getVariableStatementAccess().getSemiParserRuleCall_3()); }
)
;

// Entry rule entryRuleExportedVariableStatement
entryRuleExportedVariableStatement
	:
	ruleExportedVariableStatement
	EOF;

// Rule ExportedVariableStatement
ruleExportedVariableStatement
@init {
}:
(
	(
		(
			ruleN4Modifier
		)
	)*
	(
		(
			ruleVariableStatementKeyword
		)
	)
	(
		(
			VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_3_0=ruleExportedVariableDeclarationOrBinding{
				announce($VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_3_0.start, $VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_3_0.stop, grammarAccess.getExportedVariableStatementAccess().getVarDeclsOrBindingsAssignment_3());
			}
		)
	)
	(
		CommaKeyword_4_0=Comma
		 {
			announce($CommaKeyword_4_0, grammarAccess.getExportedVariableStatementAccess().getCommaKeyword_4_0());
		}
		(
			(
				VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_4_1_0=ruleExportedVariableDeclarationOrBinding{
					announce($VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_4_1_0.start, $VarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_4_1_0.stop, grammarAccess.getExportedVariableStatementAccess().getVarDeclsOrBindingsAssignment_4_1());
				}
			)
		)
	)*
	SemiParserRuleCall_5=ruleSemi{ announce($SemiParserRuleCall_5.start, $SemiParserRuleCall_5.stop, grammarAccess.getExportedVariableStatementAccess().getSemiParserRuleCall_5()); }
)
;

// Entry rule entryRuleVariableDeclarationOrBinding
entryRuleVariableDeclarationOrBinding
	:
	ruleVariableDeclarationOrBinding
	EOF;

// Rule VariableDeclarationOrBinding
ruleVariableDeclarationOrBinding
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		VariableBindingParserRuleCall_0=ruleVariableBinding{ announce($VariableBindingParserRuleCall_0.start, $VariableBindingParserRuleCall_0.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0()); }
	)
	    |
	VariableDeclarationParserRuleCall_1=norm4_VariableDeclaration{ announce($VariableDeclarationParserRuleCall_1.start, $VariableDeclarationParserRuleCall_1.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1()); }
)
;


// Rule VariableDeclarationOrBinding
norm1_VariableDeclarationOrBinding
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		VariableBindingParserRuleCall_0=norm1_VariableBinding{ announce($VariableBindingParserRuleCall_0.start, $VariableBindingParserRuleCall_0.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0()); }
	)
	    |
	VariableDeclarationParserRuleCall_1=norm5_VariableDeclaration{ announce($VariableDeclarationParserRuleCall_1.start, $VariableDeclarationParserRuleCall_1.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1()); }
)
;


// Rule VariableDeclarationOrBinding
norm2_VariableDeclarationOrBinding
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		VariableBindingParserRuleCall_0=norm2_VariableBinding{ announce($VariableBindingParserRuleCall_0.start, $VariableBindingParserRuleCall_0.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0()); }
	)
	    |
	VariableDeclarationParserRuleCall_1=norm6_VariableDeclaration{ announce($VariableDeclarationParserRuleCall_1.start, $VariableDeclarationParserRuleCall_1.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1()); }
)
;


// Rule VariableDeclarationOrBinding
norm3_VariableDeclarationOrBinding
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		VariableBindingParserRuleCall_0=norm3_VariableBinding{ announce($VariableBindingParserRuleCall_0.start, $VariableBindingParserRuleCall_0.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0()); }
	)
	    |
	VariableDeclarationParserRuleCall_1=norm7_VariableDeclaration{ announce($VariableDeclarationParserRuleCall_1.start, $VariableDeclarationParserRuleCall_1.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1()); }
)
;


// Rule VariableDeclarationOrBinding
norm4_VariableDeclarationOrBinding
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		VariableBindingParserRuleCall_0=norm4_VariableBinding{ announce($VariableBindingParserRuleCall_0.start, $VariableBindingParserRuleCall_0.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0()); }
	)
	    |
	VariableDeclarationParserRuleCall_1=norm4_VariableDeclaration{ announce($VariableDeclarationParserRuleCall_1.start, $VariableDeclarationParserRuleCall_1.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1()); }
)
;


// Rule VariableDeclarationOrBinding
norm6_VariableDeclarationOrBinding
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		VariableBindingParserRuleCall_0=norm6_VariableBinding{ announce($VariableBindingParserRuleCall_0.start, $VariableBindingParserRuleCall_0.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0()); }
	)
	    |
	VariableDeclarationParserRuleCall_1=norm6_VariableDeclaration{ announce($VariableDeclarationParserRuleCall_1.start, $VariableDeclarationParserRuleCall_1.stop, grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1()); }
)
;

// Entry rule entryRuleVariableBinding
entryRuleVariableBinding
	:
	ruleVariableBinding
	EOF;

// Rule VariableBinding
ruleVariableBinding
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=ruleBindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	(
		EqualsSignKeyword_1_1_0=EqualsSign
		 {
			announce($EqualsSignKeyword_1_1_0, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_1_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_1_1_1_0=ruleAssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_1_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_1_1_0.stop, grammarAccess.getVariableBindingAccess().getExpressionAssignment_1_1_1());
				}
			)
		)
	)
)
;


// Rule VariableBinding
norm1_VariableBinding
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=ruleBindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	(
		EqualsSignKeyword_1_1_0=EqualsSign
		 {
			announce($EqualsSignKeyword_1_1_0, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_1_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_1_1_1_0=norm1_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_1_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_1_1_0.stop, grammarAccess.getVariableBindingAccess().getExpressionAssignment_1_1_1());
				}
			)
		)
	)
)
;


// Rule VariableBinding
norm2_VariableBinding
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=norm1_BindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	(
		EqualsSignKeyword_1_1_0=EqualsSign
		 {
			announce($EqualsSignKeyword_1_1_0, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_1_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_1_1_1_0=norm2_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_1_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_1_1_0.stop, grammarAccess.getVariableBindingAccess().getExpressionAssignment_1_1_1());
				}
			)
		)
	)
)
;


// Rule VariableBinding
norm3_VariableBinding
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=norm1_BindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	(
		EqualsSignKeyword_1_1_0=EqualsSign
		 {
			announce($EqualsSignKeyword_1_1_0, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_1_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_1_1_1_0=norm3_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_1_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_1_1_0.stop, grammarAccess.getVariableBindingAccess().getExpressionAssignment_1_1_1());
				}
			)
		)
	)
)
;


// Rule VariableBinding
norm4_VariableBinding
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=ruleBindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	(
		EqualsSignKeyword_1_0_0_0=EqualsSign
		 {
			announce($EqualsSignKeyword_1_0_0_0, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_0_0_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0=ruleAssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0.stop, grammarAccess.getVariableBindingAccess().getExpressionAssignment_1_0_0_1());
				}
			)
		)
	)?
)
;


// Rule VariableBinding
norm5_VariableBinding
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=ruleBindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	(
		EqualsSignKeyword_1_0_0_0=EqualsSign
		 {
			announce($EqualsSignKeyword_1_0_0_0, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_0_0_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0=norm1_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0.stop, grammarAccess.getVariableBindingAccess().getExpressionAssignment_1_0_0_1());
				}
			)
		)
	)?
)
;


// Rule VariableBinding
norm6_VariableBinding
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=norm1_BindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	(
		EqualsSignKeyword_1_0_0_0=EqualsSign
		 {
			announce($EqualsSignKeyword_1_0_0_0, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_0_0_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0=norm2_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0.stop, grammarAccess.getVariableBindingAccess().getExpressionAssignment_1_0_0_1());
				}
			)
		)
	)?
)
;


// Rule VariableBinding
norm7_VariableBinding
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=norm1_BindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	(
		EqualsSignKeyword_1_0_0_0=EqualsSign
		 {
			announce($EqualsSignKeyword_1_0_0_0, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_0_0_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0=norm3_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0.stop, grammarAccess.getVariableBindingAccess().getExpressionAssignment_1_0_0_1());
				}
			)
		)
	)?
)
;


// Rule VariableDeclaration
norm1_VariableDeclaration
@init {
}:
(
	VariableDeclarationImplParserRuleCall_1=norm1_VariableDeclarationImpl{ announce($VariableDeclarationImplParserRuleCall_1.start, $VariableDeclarationImplParserRuleCall_1.stop, grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1()); }
)
;


// Rule VariableDeclaration
norm3_VariableDeclaration
@init {
}:
(
	VariableDeclarationImplParserRuleCall_1=norm3_VariableDeclarationImpl{ announce($VariableDeclarationImplParserRuleCall_1.start, $VariableDeclarationImplParserRuleCall_1.stop, grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1()); }
)
;


// Rule VariableDeclaration
norm4_VariableDeclaration
@init {
}:
(
	VariableDeclarationImplParserRuleCall_1=norm4_VariableDeclarationImpl{ announce($VariableDeclarationImplParserRuleCall_1.start, $VariableDeclarationImplParserRuleCall_1.stop, grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1()); }
)
;


// Rule VariableDeclaration
norm5_VariableDeclaration
@init {
}:
(
	VariableDeclarationImplParserRuleCall_1=norm5_VariableDeclarationImpl{ announce($VariableDeclarationImplParserRuleCall_1.start, $VariableDeclarationImplParserRuleCall_1.stop, grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1()); }
)
;


// Rule VariableDeclaration
norm6_VariableDeclaration
@init {
}:
(
	VariableDeclarationImplParserRuleCall_1=norm6_VariableDeclarationImpl{ announce($VariableDeclarationImplParserRuleCall_1.start, $VariableDeclarationImplParserRuleCall_1.stop, grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1()); }
)
;


// Rule VariableDeclaration
norm7_VariableDeclaration
@init {
}:
(
	VariableDeclarationImplParserRuleCall_1=norm7_VariableDeclarationImpl{ announce($VariableDeclarationImplParserRuleCall_1.start, $VariableDeclarationImplParserRuleCall_1.stop, grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1()); }
)
;


// Rule VariableDeclarationImpl
ruleVariableDeclarationImpl
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_0_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_0_0.start, $AnnotationsAnnotationParserRuleCall_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAssignment_0());
			}
		)
	)*
	(
		(
			((
				(
					ruleBindingIdentifier
				)
			)
			)=>
			(
				(
					NameBindingIdentifierParserRuleCall_1_1_0_0_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_1_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_1_0_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getNameAssignment_1_1_0_0());
					}
				)
			)
		)
		(
			EqualsSignKeyword_1_1_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_1_1_1_0, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_1_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0=ruleAssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0.stop, grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignment_1_1_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule VariableDeclarationImpl
norm1_VariableDeclarationImpl
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_0_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_0_0.start, $AnnotationsAnnotationParserRuleCall_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAssignment_0());
			}
		)
	)*
	(
		(
			((
				(
					ruleBindingIdentifier
				)
			)
			)=>
			(
				(
					NameBindingIdentifierParserRuleCall_1_1_0_0_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_1_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_1_0_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getNameAssignment_1_1_0_0());
					}
				)
			)
		)
		(
			EqualsSignKeyword_1_1_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_1_1_1_0, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_1_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0=norm1_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0.stop, grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignment_1_1_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule VariableDeclarationImpl
norm2_VariableDeclarationImpl
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_0_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_0_0.start, $AnnotationsAnnotationParserRuleCall_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAssignment_0());
			}
		)
	)*
	(
		(
			((
				(
					norm1_BindingIdentifier
				)
			)
			)=>
			(
				(
					NameBindingIdentifierParserRuleCall_1_1_0_0_0=norm1_BindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_1_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_1_0_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getNameAssignment_1_1_0_0());
					}
				)
			)
		)
		(
			EqualsSignKeyword_1_1_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_1_1_1_0, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_1_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0=norm2_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0.stop, grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignment_1_1_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule VariableDeclarationImpl
norm3_VariableDeclarationImpl
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_0_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_0_0.start, $AnnotationsAnnotationParserRuleCall_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAssignment_0());
			}
		)
	)*
	(
		(
			((
				(
					norm1_BindingIdentifier
				)
			)
			)=>
			(
				(
					NameBindingIdentifierParserRuleCall_1_1_0_0_0=norm1_BindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_1_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_1_0_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getNameAssignment_1_1_0_0());
					}
				)
			)
		)
		(
			EqualsSignKeyword_1_1_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_1_1_1_0, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_1_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0=norm3_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0.stop, grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignment_1_1_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule VariableDeclarationImpl
norm4_VariableDeclarationImpl
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_0_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_0_0.start, $AnnotationsAnnotationParserRuleCall_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAssignment_0());
			}
		)
	)*
	(
		(
			((
				(
					(
						ruleBindingIdentifier
					)
				)
				(
					ruleColonSepDeclaredTypeRef
				)?
			)
			)=>
			(
				(
					(
						NameBindingIdentifierParserRuleCall_1_0_0_0_0_0=ruleBindingIdentifier{
							announce($NameBindingIdentifierParserRuleCall_1_0_0_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_0_0_0_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getNameAssignment_1_0_0_0_0());
						}
					)
				)
				(
					ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1.start, $ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1.stop, grammarAccess.getVariableDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1()); }
				)?
			)
		)
		(
			EqualsSignKeyword_1_0_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_1_0_1_0, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_0_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0=ruleAssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0.stop, grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignment_1_0_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule VariableDeclarationImpl
norm5_VariableDeclarationImpl
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_0_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_0_0.start, $AnnotationsAnnotationParserRuleCall_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAssignment_0());
			}
		)
	)*
	(
		(
			((
				(
					(
						ruleBindingIdentifier
					)
				)
				(
					ruleColonSepDeclaredTypeRef
				)?
			)
			)=>
			(
				(
					(
						NameBindingIdentifierParserRuleCall_1_0_0_0_0_0=ruleBindingIdentifier{
							announce($NameBindingIdentifierParserRuleCall_1_0_0_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_0_0_0_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getNameAssignment_1_0_0_0_0());
						}
					)
				)
				(
					ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1.start, $ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1.stop, grammarAccess.getVariableDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1()); }
				)?
			)
		)
		(
			EqualsSignKeyword_1_0_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_1_0_1_0, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_0_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0=norm1_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0.stop, grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignment_1_0_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule VariableDeclarationImpl
norm6_VariableDeclarationImpl
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_0_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_0_0.start, $AnnotationsAnnotationParserRuleCall_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAssignment_0());
			}
		)
	)*
	(
		(
			((
				(
					(
						norm1_BindingIdentifier
					)
				)
				(
					ruleColonSepDeclaredTypeRef
				)?
			)
			)=>
			(
				(
					(
						NameBindingIdentifierParserRuleCall_1_0_0_0_0_0=norm1_BindingIdentifier{
							announce($NameBindingIdentifierParserRuleCall_1_0_0_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_0_0_0_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getNameAssignment_1_0_0_0_0());
						}
					)
				)
				(
					ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1.start, $ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1.stop, grammarAccess.getVariableDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1()); }
				)?
			)
		)
		(
			EqualsSignKeyword_1_0_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_1_0_1_0, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_0_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0=norm2_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0.stop, grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignment_1_0_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule VariableDeclarationImpl
norm7_VariableDeclarationImpl
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_0_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_0_0.start, $AnnotationsAnnotationParserRuleCall_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAssignment_0());
			}
		)
	)*
	(
		(
			((
				(
					(
						norm1_BindingIdentifier
					)
				)
				(
					ruleColonSepDeclaredTypeRef
				)?
			)
			)=>
			(
				(
					(
						NameBindingIdentifierParserRuleCall_1_0_0_0_0_0=norm1_BindingIdentifier{
							announce($NameBindingIdentifierParserRuleCall_1_0_0_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_0_0_0_0_0.stop, grammarAccess.getVariableDeclarationImplAccess().getNameAssignment_1_0_0_0_0());
						}
					)
				)
				(
					ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1.start, $ColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1.stop, grammarAccess.getVariableDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1()); }
				)?
			)
		)
		(
			EqualsSignKeyword_1_0_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_1_0_1_0, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_0_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0=norm3_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0.stop, grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignment_1_0_1_1());
					}
				)
			)
		)?
	)
)
;

// Entry rule entryRuleExportedVariableDeclarationOrBinding
entryRuleExportedVariableDeclarationOrBinding
	:
	ruleExportedVariableDeclarationOrBinding
	EOF;

// Rule ExportedVariableDeclarationOrBinding
ruleExportedVariableDeclarationOrBinding
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		ExportedVariableBindingParserRuleCall_0=ruleExportedVariableBinding{ announce($ExportedVariableBindingParserRuleCall_0.start, $ExportedVariableBindingParserRuleCall_0.stop, grammarAccess.getExportedVariableDeclarationOrBindingAccess().getExportedVariableBindingParserRuleCall_0()); }
	)
	    |
	ExportedVariableDeclarationParserRuleCall_1=ruleExportedVariableDeclaration{ announce($ExportedVariableDeclarationParserRuleCall_1.start, $ExportedVariableDeclarationParserRuleCall_1.stop, grammarAccess.getExportedVariableDeclarationOrBindingAccess().getExportedVariableDeclarationParserRuleCall_1()); }
)
;


// Rule ExportedVariableDeclarationOrBinding
norm1_ExportedVariableDeclarationOrBinding
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		ExportedVariableBindingParserRuleCall_0=norm1_ExportedVariableBinding{ announce($ExportedVariableBindingParserRuleCall_0.start, $ExportedVariableBindingParserRuleCall_0.stop, grammarAccess.getExportedVariableDeclarationOrBindingAccess().getExportedVariableBindingParserRuleCall_0()); }
	)
	    |
	ExportedVariableDeclarationParserRuleCall_1=norm1_ExportedVariableDeclaration{ announce($ExportedVariableDeclarationParserRuleCall_1.start, $ExportedVariableDeclarationParserRuleCall_1.stop, grammarAccess.getExportedVariableDeclarationOrBindingAccess().getExportedVariableDeclarationParserRuleCall_1()); }
)
;

// Entry rule entryRuleExportedVariableBinding
entryRuleExportedVariableBinding
	:
	ruleExportedVariableBinding
	EOF;

// Rule ExportedVariableBinding
ruleExportedVariableBinding
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=ruleBindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getExportedVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	EqualsSignKeyword_1=EqualsSign
	 {
		announce($EqualsSignKeyword_1, grammarAccess.getExportedVariableBindingAccess().getEqualsSignKeyword_1());
	}
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_2_0=norm1_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_2_0.start, $ExpressionAssignmentExpressionParserRuleCall_2_0.stop, grammarAccess.getExportedVariableBindingAccess().getExpressionAssignment_2());
			}
		)
	)
)
;


// Rule ExportedVariableBinding
norm1_ExportedVariableBinding
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		(
			PatternBindingPatternParserRuleCall_0_0=norm1_BindingPattern{
				announce($PatternBindingPatternParserRuleCall_0_0.start, $PatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getExportedVariableBindingAccess().getPatternAssignment_0());
			}
		)
	)
	EqualsSignKeyword_1=EqualsSign
	 {
		announce($EqualsSignKeyword_1, grammarAccess.getExportedVariableBindingAccess().getEqualsSignKeyword_1());
	}
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_2_0=norm3_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_2_0.start, $ExpressionAssignmentExpressionParserRuleCall_2_0.stop, grammarAccess.getExportedVariableBindingAccess().getExpressionAssignment_2());
			}
		)
	)
)
;

// Entry rule entryRuleExportedVariableDeclaration
entryRuleExportedVariableDeclaration
	:
	ruleExportedVariableDeclaration
	EOF;

// Rule ExportedVariableDeclaration
ruleExportedVariableDeclaration
@init {
}:
(
	VariableDeclarationImplParserRuleCall_1=norm5_VariableDeclarationImpl{ announce($VariableDeclarationImplParserRuleCall_1.start, $VariableDeclarationImplParserRuleCall_1.stop, grammarAccess.getExportedVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1()); }
)
;


// Rule ExportedVariableDeclaration
norm1_ExportedVariableDeclaration
@init {
}:
(
	VariableDeclarationImplParserRuleCall_1=norm7_VariableDeclarationImpl{ announce($VariableDeclarationImplParserRuleCall_1.start, $VariableDeclarationImplParserRuleCall_1.stop, grammarAccess.getExportedVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1()); }
)
;

// Entry rule entryRuleEmptyStatement
entryRuleEmptyStatement
	:
	ruleEmptyStatement
	EOF;

// Rule EmptyStatement
ruleEmptyStatement
@init {
}:
(
	SemicolonKeyword_1=Semicolon
	 {
		announce($SemicolonKeyword_1, grammarAccess.getEmptyStatementAccess().getSemicolonKeyword_1());
	}
)
;

// Entry rule entryRuleExpressionStatement
entryRuleExpressionStatement
	:
	ruleExpressionStatement
	EOF;

// Rule ExpressionStatement
ruleExpressionStatement
@init {
}:
(
	(
		(
			ExpressionExpressionParserRuleCall_0_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_0_0.start, $ExpressionExpressionParserRuleCall_0_0.stop, grammarAccess.getExpressionStatementAccess().getExpressionAssignment_0());
			}
		)
	)
	SemiParserRuleCall_1=ruleSemi{ announce($SemiParserRuleCall_1.start, $SemiParserRuleCall_1.stop, grammarAccess.getExpressionStatementAccess().getSemiParserRuleCall_1()); }
)
;


// Rule ExpressionStatement
norm1_ExpressionStatement
@init {
}:
(
	(
		(
			ExpressionExpressionParserRuleCall_0_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_0_0.start, $ExpressionExpressionParserRuleCall_0_0.stop, grammarAccess.getExpressionStatementAccess().getExpressionAssignment_0());
			}
		)
	)
	SemiParserRuleCall_1=ruleSemi{ announce($SemiParserRuleCall_1.start, $SemiParserRuleCall_1.stop, grammarAccess.getExpressionStatementAccess().getSemiParserRuleCall_1()); }
)
;

// Entry rule entryRuleIfStatement
entryRuleIfStatement
	:
	ruleIfStatement
	EOF;

// Rule IfStatement
ruleIfStatement
@init {
}:
(
	IfKeyword_0=If
	 {
		announce($IfKeyword_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
	}
	LeftParenthesisKeyword_1=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_1, grammarAccess.getIfStatementAccess().getLeftParenthesisKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getIfStatementAccess().getExpressionAssignment_2());
			}
		)
	)
	RightParenthesisKeyword_3=RightParenthesis
	 {
		announce($RightParenthesisKeyword_3, grammarAccess.getIfStatementAccess().getRightParenthesisKeyword_3());
	}
	(
		(
			IfStmtStatementParserRuleCall_4_0=ruleStatement{
				announce($IfStmtStatementParserRuleCall_4_0.start, $IfStmtStatementParserRuleCall_4_0.stop, grammarAccess.getIfStatementAccess().getIfStmtAssignment_4());
			}
		)
	)
	(
		(
			(Else
			)=>
			ElseKeyword_5_0=Else
			 {
				announce($ElseKeyword_5_0, grammarAccess.getIfStatementAccess().getElseKeyword_5_0());
			}
		)
		(
			(
				ElseStmtStatementParserRuleCall_5_1_0=ruleStatement{
					announce($ElseStmtStatementParserRuleCall_5_1_0.start, $ElseStmtStatementParserRuleCall_5_1_0.stop, grammarAccess.getIfStatementAccess().getElseStmtAssignment_5_1());
				}
			)
		)
	)?
)
;


// Rule IfStatement
norm1_IfStatement
@init {
}:
(
	IfKeyword_0=If
	 {
		announce($IfKeyword_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
	}
	LeftParenthesisKeyword_1=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_1, grammarAccess.getIfStatementAccess().getLeftParenthesisKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getIfStatementAccess().getExpressionAssignment_2());
			}
		)
	)
	RightParenthesisKeyword_3=RightParenthesis
	 {
		announce($RightParenthesisKeyword_3, grammarAccess.getIfStatementAccess().getRightParenthesisKeyword_3());
	}
	(
		(
			IfStmtStatementParserRuleCall_4_0=norm1_Statement{
				announce($IfStmtStatementParserRuleCall_4_0.start, $IfStmtStatementParserRuleCall_4_0.stop, grammarAccess.getIfStatementAccess().getIfStmtAssignment_4());
			}
		)
	)
	(
		(
			(Else
			)=>
			ElseKeyword_5_0=Else
			 {
				announce($ElseKeyword_5_0, grammarAccess.getIfStatementAccess().getElseKeyword_5_0());
			}
		)
		(
			(
				ElseStmtStatementParserRuleCall_5_1_0=norm1_Statement{
					announce($ElseStmtStatementParserRuleCall_5_1_0.start, $ElseStmtStatementParserRuleCall_5_1_0.stop, grammarAccess.getIfStatementAccess().getElseStmtAssignment_5_1());
				}
			)
		)
	)?
)
;

// Entry rule entryRuleIterationStatement
entryRuleIterationStatement
	:
	ruleIterationStatement
	EOF;

// Rule IterationStatement
ruleIterationStatement
@init {
}:
(
	DoStatementParserRuleCall_0=ruleDoStatement{ announce($DoStatementParserRuleCall_0.start, $DoStatementParserRuleCall_0.stop, grammarAccess.getIterationStatementAccess().getDoStatementParserRuleCall_0()); }
	    |
	WhileStatementParserRuleCall_1=ruleWhileStatement{ announce($WhileStatementParserRuleCall_1.start, $WhileStatementParserRuleCall_1.stop, grammarAccess.getIterationStatementAccess().getWhileStatementParserRuleCall_1()); }
	    |
	ForStatementParserRuleCall_2=ruleForStatement{ announce($ForStatementParserRuleCall_2.start, $ForStatementParserRuleCall_2.stop, grammarAccess.getIterationStatementAccess().getForStatementParserRuleCall_2()); }
)
;


// Rule IterationStatement
norm1_IterationStatement
@init {
}:
(
	DoStatementParserRuleCall_0=norm1_DoStatement{ announce($DoStatementParserRuleCall_0.start, $DoStatementParserRuleCall_0.stop, grammarAccess.getIterationStatementAccess().getDoStatementParserRuleCall_0()); }
	    |
	WhileStatementParserRuleCall_1=norm1_WhileStatement{ announce($WhileStatementParserRuleCall_1.start, $WhileStatementParserRuleCall_1.stop, grammarAccess.getIterationStatementAccess().getWhileStatementParserRuleCall_1()); }
	    |
	ForStatementParserRuleCall_2=norm1_ForStatement{ announce($ForStatementParserRuleCall_2.start, $ForStatementParserRuleCall_2.stop, grammarAccess.getIterationStatementAccess().getForStatementParserRuleCall_2()); }
)
;

// Entry rule entryRuleDoStatement
entryRuleDoStatement
	:
	ruleDoStatement
	EOF;

// Rule DoStatement
ruleDoStatement
@init {
}:
(
	DoKeyword_0=Do
	 {
		announce($DoKeyword_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
	}
	(
		(
			StatementStatementParserRuleCall_1_0=ruleStatement{
				announce($StatementStatementParserRuleCall_1_0.start, $StatementStatementParserRuleCall_1_0.stop, grammarAccess.getDoStatementAccess().getStatementAssignment_1());
			}
		)
	)
	WhileKeyword_2=While
	 {
		announce($WhileKeyword_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
	}
	LeftParenthesisKeyword_3=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_3, grammarAccess.getDoStatementAccess().getLeftParenthesisKeyword_3());
	}
	(
		(
			ExpressionExpressionParserRuleCall_4_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_4_0.start, $ExpressionExpressionParserRuleCall_4_0.stop, grammarAccess.getDoStatementAccess().getExpressionAssignment_4());
			}
		)
	)
	RightParenthesisKeyword_5=RightParenthesis
	 {
		announce($RightParenthesisKeyword_5, grammarAccess.getDoStatementAccess().getRightParenthesisKeyword_5());
	}
	(
		(ruleSemi)=>
		SemiParserRuleCall_6=ruleSemi{ announce($SemiParserRuleCall_6.start, $SemiParserRuleCall_6.stop, grammarAccess.getDoStatementAccess().getSemiParserRuleCall_6()); }
	)?
)
;


// Rule DoStatement
norm1_DoStatement
@init {
}:
(
	DoKeyword_0=Do
	 {
		announce($DoKeyword_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
	}
	(
		(
			StatementStatementParserRuleCall_1_0=norm1_Statement{
				announce($StatementStatementParserRuleCall_1_0.start, $StatementStatementParserRuleCall_1_0.stop, grammarAccess.getDoStatementAccess().getStatementAssignment_1());
			}
		)
	)
	WhileKeyword_2=While
	 {
		announce($WhileKeyword_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
	}
	LeftParenthesisKeyword_3=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_3, grammarAccess.getDoStatementAccess().getLeftParenthesisKeyword_3());
	}
	(
		(
			ExpressionExpressionParserRuleCall_4_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_4_0.start, $ExpressionExpressionParserRuleCall_4_0.stop, grammarAccess.getDoStatementAccess().getExpressionAssignment_4());
			}
		)
	)
	RightParenthesisKeyword_5=RightParenthesis
	 {
		announce($RightParenthesisKeyword_5, grammarAccess.getDoStatementAccess().getRightParenthesisKeyword_5());
	}
	(
		(ruleSemi)=>
		SemiParserRuleCall_6=ruleSemi{ announce($SemiParserRuleCall_6.start, $SemiParserRuleCall_6.stop, grammarAccess.getDoStatementAccess().getSemiParserRuleCall_6()); }
	)?
)
;

// Entry rule entryRuleWhileStatement
entryRuleWhileStatement
	:
	ruleWhileStatement
	EOF;

// Rule WhileStatement
ruleWhileStatement
@init {
}:
(
	WhileKeyword_0=While
	 {
		announce($WhileKeyword_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
	}
	LeftParenthesisKeyword_1=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_1, grammarAccess.getWhileStatementAccess().getLeftParenthesisKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getWhileStatementAccess().getExpressionAssignment_2());
			}
		)
	)
	RightParenthesisKeyword_3=RightParenthesis
	 {
		announce($RightParenthesisKeyword_3, grammarAccess.getWhileStatementAccess().getRightParenthesisKeyword_3());
	}
	(
		(
			StatementStatementParserRuleCall_4_0=ruleStatement{
				announce($StatementStatementParserRuleCall_4_0.start, $StatementStatementParserRuleCall_4_0.stop, grammarAccess.getWhileStatementAccess().getStatementAssignment_4());
			}
		)
	)
)
;


// Rule WhileStatement
norm1_WhileStatement
@init {
}:
(
	WhileKeyword_0=While
	 {
		announce($WhileKeyword_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
	}
	LeftParenthesisKeyword_1=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_1, grammarAccess.getWhileStatementAccess().getLeftParenthesisKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getWhileStatementAccess().getExpressionAssignment_2());
			}
		)
	)
	RightParenthesisKeyword_3=RightParenthesis
	 {
		announce($RightParenthesisKeyword_3, grammarAccess.getWhileStatementAccess().getRightParenthesisKeyword_3());
	}
	(
		(
			StatementStatementParserRuleCall_4_0=norm1_Statement{
				announce($StatementStatementParserRuleCall_4_0.start, $StatementStatementParserRuleCall_4_0.stop, grammarAccess.getWhileStatementAccess().getStatementAssignment_4());
			}
		)
	)
)
;

// Entry rule entryRuleForStatement
entryRuleForStatement
	:
	ruleForStatement
	EOF;

// Rule ForStatement
ruleForStatement
@init {
}:
(
	ForKeyword_1=For
	 {
		announce($ForKeyword_1, grammarAccess.getForStatementAccess().getForKeyword_1());
	}
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getForStatementAccess().getLeftParenthesisKeyword_2());
	}
	(
		(
			((
				(
					(
						ruleLetIdentifierRef
					)
				)
				(
					(
						In
					)
				)
				(
					(
						norm1_Expression
					)
				)
				RightParenthesis
			)
			)=>
			(
				(
					(
						InitExprLetIdentifierRefParserRuleCall_3_0_0_0_0=ruleLetIdentifierRef{
							announce($InitExprLetIdentifierRefParserRuleCall_3_0_0_0_0.start, $InitExprLetIdentifierRefParserRuleCall_3_0_0_0_0.stop, grammarAccess.getForStatementAccess().getInitExprAssignment_3_0_0_0());
						}
					)
				)
				(
					(
						ForInInKeyword_3_0_0_1_0=In
						 {
							announce($ForInInKeyword_3_0_0_1_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_0_0_1_0());
						}
					)
				)
				(
					(
						ExpressionExpressionParserRuleCall_3_0_0_2_0=norm1_Expression{
							announce($ExpressionExpressionParserRuleCall_3_0_0_2_0.start, $ExpressionExpressionParserRuleCall_3_0_0_2_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_0_0_2());
						}
					)
				)
				RightParenthesisKeyword_3_0_0_3=RightParenthesis
				 {
					announce($RightParenthesisKeyword_3_0_0_3, grammarAccess.getForStatementAccess().getRightParenthesisKeyword_3_0_0_3());
				}
			)
		)
		    |
		(
			(
				(
					(
						(Var | 
						Const | 
						Let
						)=>
						(
							ruleVariableStatementKeyword
						)
					)
					(
						(
							((
								(
									(
										ruleBindingIdentifierAsVariableDeclaration
									)
								)
								(
									(
										(
											In
										)
									)
									    |
									(
										(
											Of
										)
									)
								)
								(
									Await
									    |
									CommercialAt
									    |
									LeftParenthesis
									    |
									Async
									    |
									Yield
									    |
									Get
									    |
									Set
									    |
									Let
									    |
									Project
									    |
									External
									    |
									Abstract
									    |
									Static
									    |
									As
									    |
									From
									    |
									Constructor
									    |
									Of
									    |
									Target
									    |
									Type
									    |
									Union
									    |
									Intersection
									    |
									This
									    |
									Promisify
									    |
									Implements
									    |
									Interface
									    |
									Private
									    |
									Protected
									    |
									Public
									    |
									Out
									    |
									New
									    |
									This_1
									    |
									Super
									    |
									LessThanSign
									    |
									Import
									    |
									True
									    |
									False
									    |
									Null
									    |
									Solidus
									    |
									SolidusEqualsSign
									    |
									LeftSquareBracket
									    |
									LeftCurlyBracket
									    |
									Function
									    |
									Class
									    |
									Delete
									    |
									Void
									    |
									Typeof
									    |
									PlusSignPlusSign
									    |
									HyphenMinusHyphenMinus
									    |
									PlusSign
									    |
									HyphenMinus
									    |
									Tilde
									    |
									ExclamationMark
									    |
									RULE_IDENTIFIER
									    |
									RULE_DOUBLE
									    |
									RULE_INT
									    |
									RULE_BINARY_INT
									    |
									RULE_OCTAL_INT
									    |
									RULE_LEGACY_OCTAL_INT
									    |
									RULE_HEX_INT
									    |
									RULE_SCIENTIFIC_INT
									    |
									RULE_STRING
									    |
									RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
									    |
									RULE_TEMPLATE_HEAD
								)?
							)
							)=>
							(
								(
									(
										VarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0=ruleBindingIdentifierAsVariableDeclaration{
											announce($VarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0.start, $VarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0.stop, grammarAccess.getForStatementAccess().getVarDeclsOrBindingsAssignment_3_1_0_0_1_0_0_0());
										}
									)
								)
								(
									(
										(
											ForInInKeyword_3_1_0_0_1_0_0_1_0_0=In
											 {
												announce($ForInInKeyword_3_1_0_0_1_0_0_1_0_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_0_1_0_0_1_0_0());
											}
										)
									)
									    |
									(
										(
											ForOfOfKeyword_3_1_0_0_1_0_0_1_1_0=Of
											 {
												announce($ForOfOfKeyword_3_1_0_0_1_0_0_1_1_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_0_1_0_0_1_1_0());
											}
										)
									)
								)
								(
									(Await | 
									CommercialAt | 
									LeftParenthesis | 
									Async | 
									Yield | 
									Get | 
									Set | 
									Let | 
									Project | 
									External | 
									Abstract | 
									Static | 
									As | 
									From | 
									Constructor | 
									Of | 
									Target | 
									Type | 
									Union | 
									Intersection | 
									This | 
									Promisify | 
									Implements | 
									Interface | 
									Private | 
									Protected | 
									Public | 
									Out | 
									New | 
									This_1 | 
									Super | 
									LessThanSign | 
									Import | 
									True | 
									False | 
									Null | 
									Solidus | 
									SolidusEqualsSign | 
									LeftSquareBracket | 
									LeftCurlyBracket | 
									Function | 
									Class | 
									Delete | 
									Void | 
									Typeof | 
									PlusSignPlusSign | 
									HyphenMinusHyphenMinus | 
									PlusSign | 
									HyphenMinus | 
									Tilde | 
									ExclamationMark | 
									RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
									(
										ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0=norm1_AssignmentExpression{
											announce($ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_0_1_0_0_2());
										}
									)
								)?
							)
						)
						    |
						(
							(
								(
									VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0=norm4_VariableDeclarationOrBinding{
										announce($VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0.start, $VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0.stop, grammarAccess.getForStatementAccess().getVarDeclsOrBindingsAssignment_3_1_0_0_1_1_0());
									}
								)
							)
							(
								(
									(
										CommaKeyword_3_1_0_0_1_1_1_0_0_0=Comma
										 {
											announce($CommaKeyword_3_1_0_0_1_1_1_0_0_0, grammarAccess.getForStatementAccess().getCommaKeyword_3_1_0_0_1_1_1_0_0_0());
										}
										(
											(
												VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0=ruleVariableDeclarationOrBinding{
													announce($VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0.start, $VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0.stop, grammarAccess.getForStatementAccess().getVarDeclsOrBindingsAssignment_3_1_0_0_1_1_1_0_0_1());
												}
											)
										)
									)*
									SemicolonKeyword_3_1_0_0_1_1_1_0_1=Semicolon
									 {
										announce($SemicolonKeyword_3_1_0_0_1_1_1_0_1, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_0_1_1_1_0_1());
									}
									(
										(
											ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0=norm1_Expression{
												announce($ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0.start, $ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_0_1_1_1_0_2());
											}
										)
									)?
									SemicolonKeyword_3_1_0_0_1_1_1_0_3=Semicolon
									 {
										announce($SemicolonKeyword_3_1_0_0_1_1_1_0_3, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_0_1_1_1_0_3());
									}
									(
										(
											UpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0=norm1_Expression{
												announce($UpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0.start, $UpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0.stop, grammarAccess.getForStatementAccess().getUpdateExprAssignment_3_1_0_0_1_1_1_0_4());
											}
										)
									)?
								)
								    |
								(
									(
										(
											ForInInKeyword_3_1_0_0_1_1_1_1_0_0=In
											 {
												announce($ForInInKeyword_3_1_0_0_1_1_1_1_0_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_0_1_1_1_1_0_0());
											}
										)
									)
									(
										(
											ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0=norm1_Expression{
												announce($ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0.start, $ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_0_1_1_1_1_1());
											}
										)
									)?
								)
								    |
								(
									(
										(
											ForOfOfKeyword_3_1_0_0_1_1_1_2_0_0=Of
											 {
												announce($ForOfOfKeyword_3_1_0_0_1_1_1_2_0_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_0_1_1_1_2_0_0());
											}
										)
									)
									(
										(
											ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0=norm1_AssignmentExpression{
												announce($ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_0_1_1_1_2_1());
											}
										)
									)?
								)
							)
						)
					)
				)
				    |
				(
					(
						(
							InitExprExpressionParserRuleCall_3_1_0_1_0_0=ruleExpression{
								announce($InitExprExpressionParserRuleCall_3_1_0_1_0_0.start, $InitExprExpressionParserRuleCall_3_1_0_1_0_0.stop, grammarAccess.getForStatementAccess().getInitExprAssignment_3_1_0_1_0());
							}
						)
					)
					(
						(
							SemicolonKeyword_3_1_0_1_1_0_0=Semicolon
							 {
								announce($SemicolonKeyword_3_1_0_1_1_0_0, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_1_1_0_0());
							}
							(
								(
									ExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0=norm1_Expression{
										announce($ExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0.start, $ExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_1_1_0_1());
									}
								)
							)?
							SemicolonKeyword_3_1_0_1_1_0_2=Semicolon
							 {
								announce($SemicolonKeyword_3_1_0_1_1_0_2, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_1_1_0_2());
							}
							(
								(
									UpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0=norm1_Expression{
										announce($UpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0.start, $UpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0.stop, grammarAccess.getForStatementAccess().getUpdateExprAssignment_3_1_0_1_1_0_3());
									}
								)
							)?
						)
						    |
						(
							(
								(
									ForInInKeyword_3_1_0_1_1_1_0_0=In
									 {
										announce($ForInInKeyword_3_1_0_1_1_1_0_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_1_1_1_0_0());
									}
								)
							)
							(
								(
									ExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0=norm1_Expression{
										announce($ExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0.start, $ExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_1_1_1_1());
									}
								)
							)?
						)
						    |
						(
							(
								(
									ForOfOfKeyword_3_1_0_1_1_2_0_0=Of
									 {
										announce($ForOfOfKeyword_3_1_0_1_1_2_0_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_1_1_2_0_0());
									}
								)
							)
							(
								(
									ExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0=norm1_AssignmentExpression{
										announce($ExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_1_1_2_1());
									}
								)
							)?
						)
					)
				)
				    |
				(
					SemicolonKeyword_3_1_0_2_0=Semicolon
					 {
						announce($SemicolonKeyword_3_1_0_2_0, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_2_0());
					}
					(
						(
							ExpressionExpressionParserRuleCall_3_1_0_2_1_0=norm1_Expression{
								announce($ExpressionExpressionParserRuleCall_3_1_0_2_1_0.start, $ExpressionExpressionParserRuleCall_3_1_0_2_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_2_1());
							}
						)
					)?
					SemicolonKeyword_3_1_0_2_2=Semicolon
					 {
						announce($SemicolonKeyword_3_1_0_2_2, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_2_2());
					}
					(
						(
							UpdateExprExpressionParserRuleCall_3_1_0_2_3_0=norm1_Expression{
								announce($UpdateExprExpressionParserRuleCall_3_1_0_2_3_0.start, $UpdateExprExpressionParserRuleCall_3_1_0_2_3_0.stop, grammarAccess.getForStatementAccess().getUpdateExprAssignment_3_1_0_2_3());
							}
						)
					)?
				)
			)
			RightParenthesisKeyword_3_1_1=RightParenthesis
			 {
				announce($RightParenthesisKeyword_3_1_1, grammarAccess.getForStatementAccess().getRightParenthesisKeyword_3_1_1());
			}
		)
	)
	(
		(
			StatementStatementParserRuleCall_4_0=ruleStatement{
				announce($StatementStatementParserRuleCall_4_0.start, $StatementStatementParserRuleCall_4_0.stop, grammarAccess.getForStatementAccess().getStatementAssignment_4());
			}
		)
	)
)
;


// Rule ForStatement
norm1_ForStatement
@init {
}:
(
	ForKeyword_1=For
	 {
		announce($ForKeyword_1, grammarAccess.getForStatementAccess().getForKeyword_1());
	}
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getForStatementAccess().getLeftParenthesisKeyword_2());
	}
	(
		(
			((
				(
					(
						ruleLetIdentifierRef
					)
				)
				(
					(
						In
					)
				)
				(
					(
						norm3_Expression
					)
				)
				RightParenthesis
			)
			)=>
			(
				(
					(
						InitExprLetIdentifierRefParserRuleCall_3_0_0_0_0=ruleLetIdentifierRef{
							announce($InitExprLetIdentifierRefParserRuleCall_3_0_0_0_0.start, $InitExprLetIdentifierRefParserRuleCall_3_0_0_0_0.stop, grammarAccess.getForStatementAccess().getInitExprAssignment_3_0_0_0());
						}
					)
				)
				(
					(
						ForInInKeyword_3_0_0_1_0=In
						 {
							announce($ForInInKeyword_3_0_0_1_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_0_0_1_0());
						}
					)
				)
				(
					(
						ExpressionExpressionParserRuleCall_3_0_0_2_0=norm3_Expression{
							announce($ExpressionExpressionParserRuleCall_3_0_0_2_0.start, $ExpressionExpressionParserRuleCall_3_0_0_2_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_0_0_2());
						}
					)
				)
				RightParenthesisKeyword_3_0_0_3=RightParenthesis
				 {
					announce($RightParenthesisKeyword_3_0_0_3, grammarAccess.getForStatementAccess().getRightParenthesisKeyword_3_0_0_3());
				}
			)
		)
		    |
		(
			(
				(
					(
						(Var | 
						Const | 
						Let
						)=>
						(
							ruleVariableStatementKeyword
						)
					)
					(
						(
							((
								(
									(
										norm2_BindingIdentifierAsVariableDeclaration
									)
								)
								(
									(
										(
											In
										)
									)
									    |
									(
										(
											Of
										)
									)
								)
								(
									Await
									    |
									CommercialAt
									    |
									LeftParenthesis
									    |
									Async
									    |
									Get
									    |
									Set
									    |
									Let
									    |
									Project
									    |
									External
									    |
									Abstract
									    |
									Static
									    |
									As
									    |
									From
									    |
									Constructor
									    |
									Of
									    |
									Target
									    |
									Type
									    |
									Union
									    |
									Intersection
									    |
									This
									    |
									Promisify
									    |
									Implements
									    |
									Interface
									    |
									Private
									    |
									Protected
									    |
									Public
									    |
									Out
									    |
									Yield
									    |
									New
									    |
									This_1
									    |
									Super
									    |
									LessThanSign
									    |
									Import
									    |
									True
									    |
									False
									    |
									Null
									    |
									Solidus
									    |
									SolidusEqualsSign
									    |
									LeftSquareBracket
									    |
									LeftCurlyBracket
									    |
									Function
									    |
									Class
									    |
									Delete
									    |
									Void
									    |
									Typeof
									    |
									PlusSignPlusSign
									    |
									HyphenMinusHyphenMinus
									    |
									PlusSign
									    |
									HyphenMinus
									    |
									Tilde
									    |
									ExclamationMark
									    |
									RULE_IDENTIFIER
									    |
									RULE_DOUBLE
									    |
									RULE_INT
									    |
									RULE_BINARY_INT
									    |
									RULE_OCTAL_INT
									    |
									RULE_LEGACY_OCTAL_INT
									    |
									RULE_HEX_INT
									    |
									RULE_SCIENTIFIC_INT
									    |
									RULE_STRING
									    |
									RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
									    |
									RULE_TEMPLATE_HEAD
								)?
							)
							)=>
							(
								(
									(
										VarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0=norm2_BindingIdentifierAsVariableDeclaration{
											announce($VarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0.start, $VarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0.stop, grammarAccess.getForStatementAccess().getVarDeclsOrBindingsAssignment_3_1_0_0_1_0_0_0());
										}
									)
								)
								(
									(
										(
											ForInInKeyword_3_1_0_0_1_0_0_1_0_0=In
											 {
												announce($ForInInKeyword_3_1_0_0_1_0_0_1_0_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_0_1_0_0_1_0_0());
											}
										)
									)
									    |
									(
										(
											ForOfOfKeyword_3_1_0_0_1_0_0_1_1_0=Of
											 {
												announce($ForOfOfKeyword_3_1_0_0_1_0_0_1_1_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_0_1_0_0_1_1_0());
											}
										)
									)
								)
								(
									(Await | 
									CommercialAt | 
									LeftParenthesis | 
									Async | 
									Get | 
									Set | 
									Let | 
									Project | 
									External | 
									Abstract | 
									Static | 
									As | 
									From | 
									Constructor | 
									Of | 
									Target | 
									Type | 
									Union | 
									Intersection | 
									This | 
									Promisify | 
									Implements | 
									Interface | 
									Private | 
									Protected | 
									Public | 
									Out | 
									Yield | 
									New | 
									This_1 | 
									Super | 
									LessThanSign | 
									Import | 
									True | 
									False | 
									Null | 
									Solidus | 
									SolidusEqualsSign | 
									LeftSquareBracket | 
									LeftCurlyBracket | 
									Function | 
									Class | 
									Delete | 
									Void | 
									Typeof | 
									PlusSignPlusSign | 
									HyphenMinusHyphenMinus | 
									PlusSign | 
									HyphenMinus | 
									Tilde | 
									ExclamationMark | 
									RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
									(
										ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0=norm3_AssignmentExpression{
											announce($ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_0_1_0_0_2());
										}
									)
								)?
							)
						)
						    |
						(
							(
								(
									VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0=norm6_VariableDeclarationOrBinding{
										announce($VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0.start, $VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0.stop, grammarAccess.getForStatementAccess().getVarDeclsOrBindingsAssignment_3_1_0_0_1_1_0());
									}
								)
							)
							(
								(
									(
										CommaKeyword_3_1_0_0_1_1_1_0_0_0=Comma
										 {
											announce($CommaKeyword_3_1_0_0_1_1_1_0_0_0, grammarAccess.getForStatementAccess().getCommaKeyword_3_1_0_0_1_1_1_0_0_0());
										}
										(
											(
												VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0=norm2_VariableDeclarationOrBinding{
													announce($VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0.start, $VarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0.stop, grammarAccess.getForStatementAccess().getVarDeclsOrBindingsAssignment_3_1_0_0_1_1_1_0_0_1());
												}
											)
										)
									)*
									SemicolonKeyword_3_1_0_0_1_1_1_0_1=Semicolon
									 {
										announce($SemicolonKeyword_3_1_0_0_1_1_1_0_1, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_0_1_1_1_0_1());
									}
									(
										(
											ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0=norm3_Expression{
												announce($ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0.start, $ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_0_1_1_1_0_2());
											}
										)
									)?
									SemicolonKeyword_3_1_0_0_1_1_1_0_3=Semicolon
									 {
										announce($SemicolonKeyword_3_1_0_0_1_1_1_0_3, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_0_1_1_1_0_3());
									}
									(
										(
											UpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0=norm3_Expression{
												announce($UpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0.start, $UpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0.stop, grammarAccess.getForStatementAccess().getUpdateExprAssignment_3_1_0_0_1_1_1_0_4());
											}
										)
									)?
								)
								    |
								(
									(
										(
											ForInInKeyword_3_1_0_0_1_1_1_1_0_0=In
											 {
												announce($ForInInKeyword_3_1_0_0_1_1_1_1_0_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_0_1_1_1_1_0_0());
											}
										)
									)
									(
										(
											ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0=norm3_Expression{
												announce($ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0.start, $ExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_0_1_1_1_1_1());
											}
										)
									)?
								)
								    |
								(
									(
										(
											ForOfOfKeyword_3_1_0_0_1_1_1_2_0_0=Of
											 {
												announce($ForOfOfKeyword_3_1_0_0_1_1_1_2_0_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_0_1_1_1_2_0_0());
											}
										)
									)
									(
										(
											ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0=norm3_AssignmentExpression{
												announce($ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_0_1_1_1_2_1());
											}
										)
									)?
								)
							)
						)
					)
				)
				    |
				(
					(
						(
							InitExprExpressionParserRuleCall_3_1_0_1_0_0=norm2_Expression{
								announce($InitExprExpressionParserRuleCall_3_1_0_1_0_0.start, $InitExprExpressionParserRuleCall_3_1_0_1_0_0.stop, grammarAccess.getForStatementAccess().getInitExprAssignment_3_1_0_1_0());
							}
						)
					)
					(
						(
							SemicolonKeyword_3_1_0_1_1_0_0=Semicolon
							 {
								announce($SemicolonKeyword_3_1_0_1_1_0_0, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_1_1_0_0());
							}
							(
								(
									ExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0=norm3_Expression{
										announce($ExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0.start, $ExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_1_1_0_1());
									}
								)
							)?
							SemicolonKeyword_3_1_0_1_1_0_2=Semicolon
							 {
								announce($SemicolonKeyword_3_1_0_1_1_0_2, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_1_1_0_2());
							}
							(
								(
									UpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0=norm3_Expression{
										announce($UpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0.start, $UpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0.stop, grammarAccess.getForStatementAccess().getUpdateExprAssignment_3_1_0_1_1_0_3());
									}
								)
							)?
						)
						    |
						(
							(
								(
									ForInInKeyword_3_1_0_1_1_1_0_0=In
									 {
										announce($ForInInKeyword_3_1_0_1_1_1_0_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_1_1_1_0_0());
									}
								)
							)
							(
								(
									ExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0=norm3_Expression{
										announce($ExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0.start, $ExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_1_1_1_1());
									}
								)
							)?
						)
						    |
						(
							(
								(
									ForOfOfKeyword_3_1_0_1_1_2_0_0=Of
									 {
										announce($ForOfOfKeyword_3_1_0_1_1_2_0_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_1_1_2_0_0());
									}
								)
							)
							(
								(
									ExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0=norm3_AssignmentExpression{
										announce($ExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_1_1_2_1());
									}
								)
							)?
						)
					)
				)
				    |
				(
					SemicolonKeyword_3_1_0_2_0=Semicolon
					 {
						announce($SemicolonKeyword_3_1_0_2_0, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_2_0());
					}
					(
						(
							ExpressionExpressionParserRuleCall_3_1_0_2_1_0=norm3_Expression{
								announce($ExpressionExpressionParserRuleCall_3_1_0_2_1_0.start, $ExpressionExpressionParserRuleCall_3_1_0_2_1_0.stop, grammarAccess.getForStatementAccess().getExpressionAssignment_3_1_0_2_1());
							}
						)
					)?
					SemicolonKeyword_3_1_0_2_2=Semicolon
					 {
						announce($SemicolonKeyword_3_1_0_2_2, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_2_2());
					}
					(
						(
							UpdateExprExpressionParserRuleCall_3_1_0_2_3_0=norm3_Expression{
								announce($UpdateExprExpressionParserRuleCall_3_1_0_2_3_0.start, $UpdateExprExpressionParserRuleCall_3_1_0_2_3_0.stop, grammarAccess.getForStatementAccess().getUpdateExprAssignment_3_1_0_2_3());
							}
						)
					)?
				)
			)
			RightParenthesisKeyword_3_1_1=RightParenthesis
			 {
				announce($RightParenthesisKeyword_3_1_1, grammarAccess.getForStatementAccess().getRightParenthesisKeyword_3_1_1());
			}
		)
	)
	(
		(
			StatementStatementParserRuleCall_4_0=norm1_Statement{
				announce($StatementStatementParserRuleCall_4_0.start, $StatementStatementParserRuleCall_4_0.stop, grammarAccess.getForStatementAccess().getStatementAssignment_4());
			}
		)
	)
)
;

// Entry rule entryRuleLetIdentifierRef
entryRuleLetIdentifierRef
	:
	ruleLetIdentifierRef
	EOF;

// Rule LetIdentifierRef
ruleLetIdentifierRef
@init {
}:
(
	(
		IdIdentifiableElementLetAsIdentifierParserRuleCall_0_1=ruleLetAsIdentifier{
			announce($IdIdentifiableElementLetAsIdentifierParserRuleCall_0_1.start, $IdIdentifiableElementLetAsIdentifierParserRuleCall_0_1.stop, grammarAccess.getLetIdentifierRefAccess().getIdAssignment());
		}
	)
)
;

// Entry rule entryRuleLetAsIdentifier
entryRuleLetAsIdentifier
	:
	ruleLetAsIdentifier
	EOF;

// Rule LetAsIdentifier
ruleLetAsIdentifier
@init {
}
:
LetKeyword=Let {
	announce($LetKeyword, grammarAccess.getLetAsIdentifierAccess().getLetKeyword());
}
;

// Entry rule entryRuleBindingIdentifierAsVariableDeclaration
entryRuleBindingIdentifierAsVariableDeclaration
	:
	ruleBindingIdentifierAsVariableDeclaration
	EOF;

// Rule BindingIdentifierAsVariableDeclaration
ruleBindingIdentifierAsVariableDeclaration
@init {
}:
(
	(
		NameBindingIdentifierParserRuleCall_0=ruleBindingIdentifier{
			announce($NameBindingIdentifierParserRuleCall_0.start, $NameBindingIdentifierParserRuleCall_0.stop, grammarAccess.getBindingIdentifierAsVariableDeclarationAccess().getNameAssignment());
		}
	)
)
;


// Rule BindingIdentifierAsVariableDeclaration
norm2_BindingIdentifierAsVariableDeclaration
@init {
}:
(
	(
		NameBindingIdentifierParserRuleCall_0=norm1_BindingIdentifier{
			announce($NameBindingIdentifierParserRuleCall_0.start, $NameBindingIdentifierParserRuleCall_0.stop, grammarAccess.getBindingIdentifierAsVariableDeclarationAccess().getNameAssignment());
		}
	)
)
;

// Entry rule entryRuleContinueStatement
entryRuleContinueStatement
	:
	ruleContinueStatement
	EOF;

// Rule ContinueStatement
ruleContinueStatement
@init {
}:
(
	ContinueKeyword_1=Continue
	 {
		promoteEOL();
		announce($ContinueKeyword_1, grammarAccess.getContinueStatementAccess().getContinueKeyword_1());
	}
	(
		(
			LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1=ruleBindingIdentifier{
				announce($LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1.start, $LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1.stop, grammarAccess.getContinueStatementAccess().getLabelAssignment_2());
			}
		)
	)?
	SemiParserRuleCall_3=ruleSemi{ announce($SemiParserRuleCall_3.start, $SemiParserRuleCall_3.stop, grammarAccess.getContinueStatementAccess().getSemiParserRuleCall_3()); }
)
;


// Rule ContinueStatement
norm1_ContinueStatement
@init {
}:
(
	ContinueKeyword_1=Continue
	 {
		promoteEOL();
		announce($ContinueKeyword_1, grammarAccess.getContinueStatementAccess().getContinueKeyword_1());
	}
	(
		(
			LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1=norm1_BindingIdentifier{
				announce($LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1.start, $LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1.stop, grammarAccess.getContinueStatementAccess().getLabelAssignment_2());
			}
		)
	)?
	SemiParserRuleCall_3=ruleSemi{ announce($SemiParserRuleCall_3.start, $SemiParserRuleCall_3.stop, grammarAccess.getContinueStatementAccess().getSemiParserRuleCall_3()); }
)
;

// Entry rule entryRuleBreakStatement
entryRuleBreakStatement
	:
	ruleBreakStatement
	EOF;

// Rule BreakStatement
ruleBreakStatement
@init {
}:
(
	BreakKeyword_1=Break
	 {
		promoteEOL();
		announce($BreakKeyword_1, grammarAccess.getBreakStatementAccess().getBreakKeyword_1());
	}
	(
		(
			LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1=ruleBindingIdentifier{
				announce($LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1.start, $LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1.stop, grammarAccess.getBreakStatementAccess().getLabelAssignment_2());
			}
		)
	)?
	SemiParserRuleCall_3=ruleSemi{ announce($SemiParserRuleCall_3.start, $SemiParserRuleCall_3.stop, grammarAccess.getBreakStatementAccess().getSemiParserRuleCall_3()); }
)
;


// Rule BreakStatement
norm1_BreakStatement
@init {
}:
(
	BreakKeyword_1=Break
	 {
		promoteEOL();
		announce($BreakKeyword_1, grammarAccess.getBreakStatementAccess().getBreakKeyword_1());
	}
	(
		(
			LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1=norm1_BindingIdentifier{
				announce($LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1.start, $LabelLabelledStatementBindingIdentifierParserRuleCall_2_0_1.stop, grammarAccess.getBreakStatementAccess().getLabelAssignment_2());
			}
		)
	)?
	SemiParserRuleCall_3=ruleSemi{ announce($SemiParserRuleCall_3.start, $SemiParserRuleCall_3.stop, grammarAccess.getBreakStatementAccess().getSemiParserRuleCall_3()); }
)
;

// Entry rule entryRuleReturnStatement
entryRuleReturnStatement
	:
	ruleReturnStatement
	EOF;

// Rule ReturnStatement
ruleReturnStatement
@init {
}:
(
	ReturnKeyword_1=Return
	 {
		promoteEOL();
		announce($ReturnKeyword_1, grammarAccess.getReturnStatementAccess().getReturnKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getReturnStatementAccess().getExpressionAssignment_2());
			}
		)
	)?
	SemiParserRuleCall_3=ruleSemi{ announce($SemiParserRuleCall_3.start, $SemiParserRuleCall_3.stop, grammarAccess.getReturnStatementAccess().getSemiParserRuleCall_3()); }
)
;


// Rule ReturnStatement
norm1_ReturnStatement
@init {
}:
(
	ReturnKeyword_1=Return
	 {
		promoteEOL();
		announce($ReturnKeyword_1, grammarAccess.getReturnStatementAccess().getReturnKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getReturnStatementAccess().getExpressionAssignment_2());
			}
		)
	)?
	SemiParserRuleCall_3=ruleSemi{ announce($SemiParserRuleCall_3.start, $SemiParserRuleCall_3.stop, grammarAccess.getReturnStatementAccess().getSemiParserRuleCall_3()); }
)
;

// Entry rule entryRuleWithStatement
entryRuleWithStatement
	:
	ruleWithStatement
	EOF;

// Rule WithStatement
ruleWithStatement
@init {
}:
(
	WithKeyword_0=With
	 {
		announce($WithKeyword_0, grammarAccess.getWithStatementAccess().getWithKeyword_0());
	}
	LeftParenthesisKeyword_1=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_1, grammarAccess.getWithStatementAccess().getLeftParenthesisKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getWithStatementAccess().getExpressionAssignment_2());
			}
		)
	)
	RightParenthesisKeyword_3=RightParenthesis
	 {
		announce($RightParenthesisKeyword_3, grammarAccess.getWithStatementAccess().getRightParenthesisKeyword_3());
	}
	(
		(
			StatementStatementParserRuleCall_4_0=ruleStatement{
				announce($StatementStatementParserRuleCall_4_0.start, $StatementStatementParserRuleCall_4_0.stop, grammarAccess.getWithStatementAccess().getStatementAssignment_4());
			}
		)
	)
)
;


// Rule WithStatement
norm1_WithStatement
@init {
}:
(
	WithKeyword_0=With
	 {
		announce($WithKeyword_0, grammarAccess.getWithStatementAccess().getWithKeyword_0());
	}
	LeftParenthesisKeyword_1=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_1, grammarAccess.getWithStatementAccess().getLeftParenthesisKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getWithStatementAccess().getExpressionAssignment_2());
			}
		)
	)
	RightParenthesisKeyword_3=RightParenthesis
	 {
		announce($RightParenthesisKeyword_3, grammarAccess.getWithStatementAccess().getRightParenthesisKeyword_3());
	}
	(
		(
			StatementStatementParserRuleCall_4_0=norm1_Statement{
				announce($StatementStatementParserRuleCall_4_0.start, $StatementStatementParserRuleCall_4_0.stop, grammarAccess.getWithStatementAccess().getStatementAssignment_4());
			}
		)
	)
)
;

// Entry rule entryRuleSwitchStatement
entryRuleSwitchStatement
	:
	ruleSwitchStatement
	EOF;

// Rule SwitchStatement
ruleSwitchStatement
@init {
}:
(
	SwitchKeyword_0=Switch
	 {
		announce($SwitchKeyword_0, grammarAccess.getSwitchStatementAccess().getSwitchKeyword_0());
	}
	LeftParenthesisKeyword_1=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_1, grammarAccess.getSwitchStatementAccess().getLeftParenthesisKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getSwitchStatementAccess().getExpressionAssignment_2());
			}
		)
	)
	RightParenthesisKeyword_3=RightParenthesis
	 {
		announce($RightParenthesisKeyword_3, grammarAccess.getSwitchStatementAccess().getRightParenthesisKeyword_3());
	}
	LeftCurlyBracketKeyword_4=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_4, grammarAccess.getSwitchStatementAccess().getLeftCurlyBracketKeyword_4());
	}
	(
		(
			CasesCaseClauseParserRuleCall_5_0=ruleCaseClause{
				announce($CasesCaseClauseParserRuleCall_5_0.start, $CasesCaseClauseParserRuleCall_5_0.stop, grammarAccess.getSwitchStatementAccess().getCasesAssignment_5());
			}
		)
	)*
	(
		(
			(
				CasesDefaultClauseParserRuleCall_6_0_0=ruleDefaultClause{
					announce($CasesDefaultClauseParserRuleCall_6_0_0.start, $CasesDefaultClauseParserRuleCall_6_0_0.stop, grammarAccess.getSwitchStatementAccess().getCasesAssignment_6_0());
				}
			)
		)
		(
			(
				CasesCaseClauseParserRuleCall_6_1_0=ruleCaseClause{
					announce($CasesCaseClauseParserRuleCall_6_1_0.start, $CasesCaseClauseParserRuleCall_6_1_0.stop, grammarAccess.getSwitchStatementAccess().getCasesAssignment_6_1());
				}
			)
		)*
	)?
	RightCurlyBracketKeyword_7=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_7, grammarAccess.getSwitchStatementAccess().getRightCurlyBracketKeyword_7());
	}
)
;


// Rule SwitchStatement
norm1_SwitchStatement
@init {
}:
(
	SwitchKeyword_0=Switch
	 {
		announce($SwitchKeyword_0, grammarAccess.getSwitchStatementAccess().getSwitchKeyword_0());
	}
	LeftParenthesisKeyword_1=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_1, grammarAccess.getSwitchStatementAccess().getLeftParenthesisKeyword_1());
	}
	(
		(
			ExpressionExpressionParserRuleCall_2_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_2_0.start, $ExpressionExpressionParserRuleCall_2_0.stop, grammarAccess.getSwitchStatementAccess().getExpressionAssignment_2());
			}
		)
	)
	RightParenthesisKeyword_3=RightParenthesis
	 {
		announce($RightParenthesisKeyword_3, grammarAccess.getSwitchStatementAccess().getRightParenthesisKeyword_3());
	}
	LeftCurlyBracketKeyword_4=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_4, grammarAccess.getSwitchStatementAccess().getLeftCurlyBracketKeyword_4());
	}
	(
		(
			CasesCaseClauseParserRuleCall_5_0=norm1_CaseClause{
				announce($CasesCaseClauseParserRuleCall_5_0.start, $CasesCaseClauseParserRuleCall_5_0.stop, grammarAccess.getSwitchStatementAccess().getCasesAssignment_5());
			}
		)
	)*
	(
		(
			(
				CasesDefaultClauseParserRuleCall_6_0_0=norm1_DefaultClause{
					announce($CasesDefaultClauseParserRuleCall_6_0_0.start, $CasesDefaultClauseParserRuleCall_6_0_0.stop, grammarAccess.getSwitchStatementAccess().getCasesAssignment_6_0());
				}
			)
		)
		(
			(
				CasesCaseClauseParserRuleCall_6_1_0=norm1_CaseClause{
					announce($CasesCaseClauseParserRuleCall_6_1_0.start, $CasesCaseClauseParserRuleCall_6_1_0.stop, grammarAccess.getSwitchStatementAccess().getCasesAssignment_6_1());
				}
			)
		)*
	)?
	RightCurlyBracketKeyword_7=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_7, grammarAccess.getSwitchStatementAccess().getRightCurlyBracketKeyword_7());
	}
)
;

// Entry rule entryRuleCaseClause
entryRuleCaseClause
	:
	ruleCaseClause
	EOF;

// Rule CaseClause
ruleCaseClause
@init {
}:
(
	CaseKeyword_0=Case
	 {
		announce($CaseKeyword_0, grammarAccess.getCaseClauseAccess().getCaseKeyword_0());
	}
	(
		(
			ExpressionExpressionParserRuleCall_1_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_1_0.start, $ExpressionExpressionParserRuleCall_1_0.stop, grammarAccess.getCaseClauseAccess().getExpressionAssignment_1());
			}
		)
	)
	ColonKeyword_2=Colon
	 {
		announce($ColonKeyword_2, grammarAccess.getCaseClauseAccess().getColonKeyword_2());
	}
	(
		(
			StatementsStatementParserRuleCall_3_0=ruleStatement{
				announce($StatementsStatementParserRuleCall_3_0.start, $StatementsStatementParserRuleCall_3_0.stop, grammarAccess.getCaseClauseAccess().getStatementsAssignment_3());
			}
		)
	)*
)
;


// Rule CaseClause
norm1_CaseClause
@init {
}:
(
	CaseKeyword_0=Case
	 {
		announce($CaseKeyword_0, grammarAccess.getCaseClauseAccess().getCaseKeyword_0());
	}
	(
		(
			ExpressionExpressionParserRuleCall_1_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_1_0.start, $ExpressionExpressionParserRuleCall_1_0.stop, grammarAccess.getCaseClauseAccess().getExpressionAssignment_1());
			}
		)
	)
	ColonKeyword_2=Colon
	 {
		announce($ColonKeyword_2, grammarAccess.getCaseClauseAccess().getColonKeyword_2());
	}
	(
		(
			StatementsStatementParserRuleCall_3_0=norm1_Statement{
				announce($StatementsStatementParserRuleCall_3_0.start, $StatementsStatementParserRuleCall_3_0.stop, grammarAccess.getCaseClauseAccess().getStatementsAssignment_3());
			}
		)
	)*
)
;

// Entry rule entryRuleDefaultClause
entryRuleDefaultClause
	:
	ruleDefaultClause
	EOF;

// Rule DefaultClause
ruleDefaultClause
@init {
}:
(
	DefaultKeyword_1=Default
	 {
		announce($DefaultKeyword_1, grammarAccess.getDefaultClauseAccess().getDefaultKeyword_1());
	}
	ColonKeyword_2=Colon
	 {
		announce($ColonKeyword_2, grammarAccess.getDefaultClauseAccess().getColonKeyword_2());
	}
	(
		(
			StatementsStatementParserRuleCall_3_0=ruleStatement{
				announce($StatementsStatementParserRuleCall_3_0.start, $StatementsStatementParserRuleCall_3_0.stop, grammarAccess.getDefaultClauseAccess().getStatementsAssignment_3());
			}
		)
	)*
)
;


// Rule DefaultClause
norm1_DefaultClause
@init {
}:
(
	DefaultKeyword_1=Default
	 {
		announce($DefaultKeyword_1, grammarAccess.getDefaultClauseAccess().getDefaultKeyword_1());
	}
	ColonKeyword_2=Colon
	 {
		announce($ColonKeyword_2, grammarAccess.getDefaultClauseAccess().getColonKeyword_2());
	}
	(
		(
			StatementsStatementParserRuleCall_3_0=norm1_Statement{
				announce($StatementsStatementParserRuleCall_3_0.start, $StatementsStatementParserRuleCall_3_0.stop, grammarAccess.getDefaultClauseAccess().getStatementsAssignment_3());
			}
		)
	)*
)
;

// Entry rule entryRuleLabelledStatement
entryRuleLabelledStatement
	:
	ruleLabelledStatement
	EOF;

// Rule LabelledStatement
ruleLabelledStatement
@init {
}:
(
	(
		((
			(
				(
					ruleBindingIdentifier
				)
			)
			Colon
		)
		)=>
		(
			(
				(
					NameBindingIdentifierParserRuleCall_0_0_0_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_0_0_0_0.start, $NameBindingIdentifierParserRuleCall_0_0_0_0.stop, grammarAccess.getLabelledStatementAccess().getNameAssignment_0_0_0());
					}
				)
			)
			ColonKeyword_0_0_1=Colon
			 {
				announce($ColonKeyword_0_0_1, grammarAccess.getLabelledStatementAccess().getColonKeyword_0_0_1());
			}
		)
	)
	(
		(
			StatementStatementParserRuleCall_1_0=ruleStatement{
				announce($StatementStatementParserRuleCall_1_0.start, $StatementStatementParserRuleCall_1_0.stop, grammarAccess.getLabelledStatementAccess().getStatementAssignment_1());
			}
		)
	)
)
;


// Rule LabelledStatement
norm1_LabelledStatement
@init {
}:
(
	(
		((
			(
				(
					norm1_BindingIdentifier
				)
			)
			Colon
		)
		)=>
		(
			(
				(
					NameBindingIdentifierParserRuleCall_0_0_0_0=norm1_BindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_0_0_0_0.start, $NameBindingIdentifierParserRuleCall_0_0_0_0.stop, grammarAccess.getLabelledStatementAccess().getNameAssignment_0_0_0());
					}
				)
			)
			ColonKeyword_0_0_1=Colon
			 {
				announce($ColonKeyword_0_0_1, grammarAccess.getLabelledStatementAccess().getColonKeyword_0_0_1());
			}
		)
	)
	(
		(
			StatementStatementParserRuleCall_1_0=norm1_Statement{
				announce($StatementStatementParserRuleCall_1_0.start, $StatementStatementParserRuleCall_1_0.stop, grammarAccess.getLabelledStatementAccess().getStatementAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleThrowStatement
entryRuleThrowStatement
	:
	ruleThrowStatement
	EOF;

// Rule ThrowStatement
ruleThrowStatement
@init {
}:
(
	ThrowKeyword_0=Throw
	 {
		promoteEOL();
		announce($ThrowKeyword_0, grammarAccess.getThrowStatementAccess().getThrowKeyword_0());
	}
	(
		(
			ExpressionExpressionParserRuleCall_1_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_1_0.start, $ExpressionExpressionParserRuleCall_1_0.stop, grammarAccess.getThrowStatementAccess().getExpressionAssignment_1());
			}
		)
	)
	SemiParserRuleCall_2=ruleSemi{ announce($SemiParserRuleCall_2.start, $SemiParserRuleCall_2.stop, grammarAccess.getThrowStatementAccess().getSemiParserRuleCall_2()); }
)
;


// Rule ThrowStatement
norm1_ThrowStatement
@init {
}:
(
	ThrowKeyword_0=Throw
	 {
		promoteEOL();
		announce($ThrowKeyword_0, grammarAccess.getThrowStatementAccess().getThrowKeyword_0());
	}
	(
		(
			ExpressionExpressionParserRuleCall_1_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_1_0.start, $ExpressionExpressionParserRuleCall_1_0.stop, grammarAccess.getThrowStatementAccess().getExpressionAssignment_1());
			}
		)
	)
	SemiParserRuleCall_2=ruleSemi{ announce($SemiParserRuleCall_2.start, $SemiParserRuleCall_2.stop, grammarAccess.getThrowStatementAccess().getSemiParserRuleCall_2()); }
)
;

// Entry rule entryRuleTryStatement
entryRuleTryStatement
	:
	ruleTryStatement
	EOF;

// Rule TryStatement
ruleTryStatement
@init {
}:
(
	TryKeyword_0=Try
	 {
		announce($TryKeyword_0, grammarAccess.getTryStatementAccess().getTryKeyword_0());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BlockBlockParserRuleCall_1_0=ruleBlock{
				announce($BlockBlockParserRuleCall_1_0.start, $BlockBlockParserRuleCall_1_0.stop, grammarAccess.getTryStatementAccess().getBlockAssignment_1());
			}
		)
	)
	(
		(
			(
				(
					CatchCatchBlockParserRuleCall_2_0_0_0=ruleCatchBlock{
						announce($CatchCatchBlockParserRuleCall_2_0_0_0.start, $CatchCatchBlockParserRuleCall_2_0_0_0.stop, grammarAccess.getTryStatementAccess().getCatchAssignment_2_0_0());
					}
				)
			)
			(
				(
					FinallyFinallyBlockParserRuleCall_2_0_1_0=ruleFinallyBlock{
						announce($FinallyFinallyBlockParserRuleCall_2_0_1_0.start, $FinallyFinallyBlockParserRuleCall_2_0_1_0.stop, grammarAccess.getTryStatementAccess().getFinallyAssignment_2_0_1());
					}
				)
			)?
		)
		    |
		(
			(
				FinallyFinallyBlockParserRuleCall_2_1_0=ruleFinallyBlock{
					announce($FinallyFinallyBlockParserRuleCall_2_1_0.start, $FinallyFinallyBlockParserRuleCall_2_1_0.stop, grammarAccess.getTryStatementAccess().getFinallyAssignment_2_1());
				}
			)
		)
	)
)
;


// Rule TryStatement
norm1_TryStatement
@init {
}:
(
	TryKeyword_0=Try
	 {
		announce($TryKeyword_0, grammarAccess.getTryStatementAccess().getTryKeyword_0());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BlockBlockParserRuleCall_1_0=norm1_Block{
				announce($BlockBlockParserRuleCall_1_0.start, $BlockBlockParserRuleCall_1_0.stop, grammarAccess.getTryStatementAccess().getBlockAssignment_1());
			}
		)
	)
	(
		(
			(
				(
					CatchCatchBlockParserRuleCall_2_0_0_0=norm1_CatchBlock{
						announce($CatchCatchBlockParserRuleCall_2_0_0_0.start, $CatchCatchBlockParserRuleCall_2_0_0_0.stop, grammarAccess.getTryStatementAccess().getCatchAssignment_2_0_0());
					}
				)
			)
			(
				(
					FinallyFinallyBlockParserRuleCall_2_0_1_0=norm1_FinallyBlock{
						announce($FinallyFinallyBlockParserRuleCall_2_0_1_0.start, $FinallyFinallyBlockParserRuleCall_2_0_1_0.stop, grammarAccess.getTryStatementAccess().getFinallyAssignment_2_0_1());
					}
				)
			)?
		)
		    |
		(
			(
				FinallyFinallyBlockParserRuleCall_2_1_0=norm1_FinallyBlock{
					announce($FinallyFinallyBlockParserRuleCall_2_1_0.start, $FinallyFinallyBlockParserRuleCall_2_1_0.stop, grammarAccess.getTryStatementAccess().getFinallyAssignment_2_1());
				}
			)
		)
	)
)
;

// Entry rule entryRuleCatchBlock
entryRuleCatchBlock
	:
	ruleCatchBlock
	EOF;

// Rule CatchBlock
ruleCatchBlock
@init {
}:
(
	CatchKeyword_1=Catch
	 {
		announce($CatchKeyword_1, grammarAccess.getCatchBlockAccess().getCatchKeyword_1());
	}
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getCatchBlockAccess().getLeftParenthesisKeyword_2());
	}
	(
		(
			CatchVariableCatchVariableParserRuleCall_3_0=ruleCatchVariable{
				announce($CatchVariableCatchVariableParserRuleCall_3_0.start, $CatchVariableCatchVariableParserRuleCall_3_0.stop, grammarAccess.getCatchBlockAccess().getCatchVariableAssignment_3());
			}
		)
	)
	RightParenthesisKeyword_4=RightParenthesis
	 {
		announce($RightParenthesisKeyword_4, grammarAccess.getCatchBlockAccess().getRightParenthesisKeyword_4());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BlockBlockParserRuleCall_5_0=ruleBlock{
				announce($BlockBlockParserRuleCall_5_0.start, $BlockBlockParserRuleCall_5_0.stop, grammarAccess.getCatchBlockAccess().getBlockAssignment_5());
			}
		)
	)
)
;


// Rule CatchBlock
norm1_CatchBlock
@init {
}:
(
	CatchKeyword_1=Catch
	 {
		announce($CatchKeyword_1, grammarAccess.getCatchBlockAccess().getCatchKeyword_1());
	}
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getCatchBlockAccess().getLeftParenthesisKeyword_2());
	}
	(
		(
			CatchVariableCatchVariableParserRuleCall_3_0=norm1_CatchVariable{
				announce($CatchVariableCatchVariableParserRuleCall_3_0.start, $CatchVariableCatchVariableParserRuleCall_3_0.stop, grammarAccess.getCatchBlockAccess().getCatchVariableAssignment_3());
			}
		)
	)
	RightParenthesisKeyword_4=RightParenthesis
	 {
		announce($RightParenthesisKeyword_4, grammarAccess.getCatchBlockAccess().getRightParenthesisKeyword_4());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BlockBlockParserRuleCall_5_0=norm1_Block{
				announce($BlockBlockParserRuleCall_5_0.start, $BlockBlockParserRuleCall_5_0.stop, grammarAccess.getCatchBlockAccess().getBlockAssignment_5());
			}
		)
	)
)
;

// Entry rule entryRuleCatchVariable
entryRuleCatchVariable
	:
	ruleCatchVariable
	EOF;

// Rule CatchVariable
ruleCatchVariable
@init {
}:
(
	(
		((
			ruleBindingPattern
		)
		)=>
		(
			BindingPatternBindingPatternParserRuleCall_0_0=ruleBindingPattern{
				announce($BindingPatternBindingPatternParserRuleCall_0_0.start, $BindingPatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getCatchVariableAccess().getBindingPatternAssignment_0());
			}
		)
	)
	    |
	(
		((
			(
				(
					ruleBindingIdentifier
				)
			)
			Colon
		)
		)=>
		(
			(
				(
					NameBindingIdentifierParserRuleCall_1_0_0_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_0_0_0.stop, grammarAccess.getCatchVariableAccess().getNameAssignment_1_0_0());
					}
				)
			)
			(
				(Colon
				)=>
				ColonSepDeclaredTypeRefParserRuleCall_1_0_1=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_1_0_1.start, $ColonSepDeclaredTypeRefParserRuleCall_1_0_1.stop, grammarAccess.getCatchVariableAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_1()); }
			)
		)
	)
	    |
	(
		(
			BogusTypeRefFragmentParserRuleCall_2_0=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_2_0.start, $BogusTypeRefFragmentParserRuleCall_2_0.stop, grammarAccess.getCatchVariableAccess().getBogusTypeRefFragmentParserRuleCall_2_0()); }
		)?
		(
			(
				NameBindingIdentifierParserRuleCall_2_1_0=ruleBindingIdentifier{
					announce($NameBindingIdentifierParserRuleCall_2_1_0.start, $NameBindingIdentifierParserRuleCall_2_1_0.stop, grammarAccess.getCatchVariableAccess().getNameAssignment_2_1());
				}
			)
		)
	)
)
;


// Rule CatchVariable
norm1_CatchVariable
@init {
}:
(
	(
		((
			norm1_BindingPattern
		)
		)=>
		(
			BindingPatternBindingPatternParserRuleCall_0_0=norm1_BindingPattern{
				announce($BindingPatternBindingPatternParserRuleCall_0_0.start, $BindingPatternBindingPatternParserRuleCall_0_0.stop, grammarAccess.getCatchVariableAccess().getBindingPatternAssignment_0());
			}
		)
	)
	    |
	(
		((
			(
				(
					norm1_BindingIdentifier
				)
			)
			Colon
		)
		)=>
		(
			(
				(
					NameBindingIdentifierParserRuleCall_1_0_0_0=norm1_BindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_1_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_0_0_0.stop, grammarAccess.getCatchVariableAccess().getNameAssignment_1_0_0());
					}
				)
			)
			(
				(Colon
				)=>
				ColonSepDeclaredTypeRefParserRuleCall_1_0_1=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_1_0_1.start, $ColonSepDeclaredTypeRefParserRuleCall_1_0_1.stop, grammarAccess.getCatchVariableAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_1()); }
			)
		)
	)
	    |
	(
		(
			BogusTypeRefFragmentParserRuleCall_2_0=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_2_0.start, $BogusTypeRefFragmentParserRuleCall_2_0.stop, grammarAccess.getCatchVariableAccess().getBogusTypeRefFragmentParserRuleCall_2_0()); }
		)?
		(
			(
				NameBindingIdentifierParserRuleCall_2_1_0=norm1_BindingIdentifier{
					announce($NameBindingIdentifierParserRuleCall_2_1_0.start, $NameBindingIdentifierParserRuleCall_2_1_0.stop, grammarAccess.getCatchVariableAccess().getNameAssignment_2_1());
				}
			)
		)
	)
)
;

// Entry rule entryRuleFinallyBlock
entryRuleFinallyBlock
	:
	ruleFinallyBlock
	EOF;

// Rule FinallyBlock
ruleFinallyBlock
@init {
}:
(
	FinallyKeyword_1=Finally
	 {
		announce($FinallyKeyword_1, grammarAccess.getFinallyBlockAccess().getFinallyKeyword_1());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BlockBlockParserRuleCall_2_0=ruleBlock{
				announce($BlockBlockParserRuleCall_2_0.start, $BlockBlockParserRuleCall_2_0.stop, grammarAccess.getFinallyBlockAccess().getBlockAssignment_2());
			}
		)
	)
)
;


// Rule FinallyBlock
norm1_FinallyBlock
@init {
}:
(
	FinallyKeyword_1=Finally
	 {
		announce($FinallyKeyword_1, grammarAccess.getFinallyBlockAccess().getFinallyKeyword_1());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BlockBlockParserRuleCall_2_0=norm1_Block{
				announce($BlockBlockParserRuleCall_2_0.start, $BlockBlockParserRuleCall_2_0.stop, grammarAccess.getFinallyBlockAccess().getBlockAssignment_2());
			}
		)
	)
)
;

// Entry rule entryRuleDebuggerStatement
entryRuleDebuggerStatement
	:
	ruleDebuggerStatement
	EOF;

// Rule DebuggerStatement
ruleDebuggerStatement
@init {
}:
(
	DebuggerKeyword_1=Debugger
	 {
		announce($DebuggerKeyword_1, grammarAccess.getDebuggerStatementAccess().getDebuggerKeyword_1());
	}
	SemiParserRuleCall_2=ruleSemi{ announce($SemiParserRuleCall_2.start, $SemiParserRuleCall_2.stop, grammarAccess.getDebuggerStatementAccess().getSemiParserRuleCall_2()); }
)
;

// Entry rule entryRulePrimaryExpression
entryRulePrimaryExpression
	:
	rulePrimaryExpression
	EOF;

// Rule PrimaryExpression
rulePrimaryExpression
@init {
}:
(
	ThisLiteralParserRuleCall_0=ruleThisLiteral{ announce($ThisLiteralParserRuleCall_0.start, $ThisLiteralParserRuleCall_0.stop, grammarAccess.getPrimaryExpressionAccess().getThisLiteralParserRuleCall_0()); }
	    |
	SuperLiteralParserRuleCall_1=ruleSuperLiteral{ announce($SuperLiteralParserRuleCall_1.start, $SuperLiteralParserRuleCall_1.stop, grammarAccess.getPrimaryExpressionAccess().getSuperLiteralParserRuleCall_1()); }
	    |
	IdentifierRefParserRuleCall_2=ruleIdentifierRef{ announce($IdentifierRefParserRuleCall_2.start, $IdentifierRefParserRuleCall_2.stop, grammarAccess.getPrimaryExpressionAccess().getIdentifierRefParserRuleCall_2()); }
	    |
	JSXFragmentParserRuleCall_3=ruleJSXFragment{ announce($JSXFragmentParserRuleCall_3.start, $JSXFragmentParserRuleCall_3.stop, grammarAccess.getPrimaryExpressionAccess().getJSXFragmentParserRuleCall_3()); }
	    |
	JSXElementParserRuleCall_4=ruleJSXElement{ announce($JSXElementParserRuleCall_4.start, $JSXElementParserRuleCall_4.stop, grammarAccess.getPrimaryExpressionAccess().getJSXElementParserRuleCall_4()); }
	    |
	ImportCallExpressionParserRuleCall_5=ruleImportCallExpression{ announce($ImportCallExpressionParserRuleCall_5.start, $ImportCallExpressionParserRuleCall_5.stop, grammarAccess.getPrimaryExpressionAccess().getImportCallExpressionParserRuleCall_5()); }
	    |
	ParameterizedCallExpressionParserRuleCall_6=ruleParameterizedCallExpression{ announce($ParameterizedCallExpressionParserRuleCall_6.start, $ParameterizedCallExpressionParserRuleCall_6.stop, grammarAccess.getPrimaryExpressionAccess().getParameterizedCallExpressionParserRuleCall_6()); }
	    |
	LiteralParserRuleCall_7=ruleLiteral{ announce($LiteralParserRuleCall_7.start, $LiteralParserRuleCall_7.stop, grammarAccess.getPrimaryExpressionAccess().getLiteralParserRuleCall_7()); }
	    |
	ArrayLiteralParserRuleCall_8=ruleArrayLiteral{ announce($ArrayLiteralParserRuleCall_8.start, $ArrayLiteralParserRuleCall_8.stop, grammarAccess.getPrimaryExpressionAccess().getArrayLiteralParserRuleCall_8()); }
	    |
	ObjectLiteralParserRuleCall_9=ruleObjectLiteral{ announce($ObjectLiteralParserRuleCall_9.start, $ObjectLiteralParserRuleCall_9.stop, grammarAccess.getPrimaryExpressionAccess().getObjectLiteralParserRuleCall_9()); }
	    |
	ParenExpressionParserRuleCall_10=ruleParenExpression{ announce($ParenExpressionParserRuleCall_10.start, $ParenExpressionParserRuleCall_10.stop, grammarAccess.getPrimaryExpressionAccess().getParenExpressionParserRuleCall_10()); }
	    |
	AnnotatedExpressionParserRuleCall_11=ruleAnnotatedExpression{ announce($AnnotatedExpressionParserRuleCall_11.start, $AnnotatedExpressionParserRuleCall_11.stop, grammarAccess.getPrimaryExpressionAccess().getAnnotatedExpressionParserRuleCall_11()); }
	    |
	FunctionExpressionParserRuleCall_12=ruleFunctionExpression{ announce($FunctionExpressionParserRuleCall_12.start, $FunctionExpressionParserRuleCall_12.stop, grammarAccess.getPrimaryExpressionAccess().getFunctionExpressionParserRuleCall_12()); }
	    |
	(
		((
			(
				(
					Async
				)
			)
			ruleNoLineTerminator
			Function
		)
		)=>
		AsyncFunctionExpressionParserRuleCall_13=ruleAsyncFunctionExpression{ announce($AsyncFunctionExpressionParserRuleCall_13.start, $AsyncFunctionExpressionParserRuleCall_13.stop, grammarAccess.getPrimaryExpressionAccess().getAsyncFunctionExpressionParserRuleCall_13()); }
	)
	    |
	N4ClassExpressionParserRuleCall_14=ruleN4ClassExpression{ announce($N4ClassExpressionParserRuleCall_14.start, $N4ClassExpressionParserRuleCall_14.stop, grammarAccess.getPrimaryExpressionAccess().getN4ClassExpressionParserRuleCall_14()); }
	    |
	TemplateLiteralParserRuleCall_15=ruleTemplateLiteral{ announce($TemplateLiteralParserRuleCall_15.start, $TemplateLiteralParserRuleCall_15.stop, grammarAccess.getPrimaryExpressionAccess().getTemplateLiteralParserRuleCall_15()); }
)
;


// Rule PrimaryExpression
norm1_PrimaryExpression
@init {
}:
(
	ThisLiteralParserRuleCall_0=ruleThisLiteral{ announce($ThisLiteralParserRuleCall_0.start, $ThisLiteralParserRuleCall_0.stop, grammarAccess.getPrimaryExpressionAccess().getThisLiteralParserRuleCall_0()); }
	    |
	SuperLiteralParserRuleCall_1=ruleSuperLiteral{ announce($SuperLiteralParserRuleCall_1.start, $SuperLiteralParserRuleCall_1.stop, grammarAccess.getPrimaryExpressionAccess().getSuperLiteralParserRuleCall_1()); }
	    |
	IdentifierRefParserRuleCall_2=norm1_IdentifierRef{ announce($IdentifierRefParserRuleCall_2.start, $IdentifierRefParserRuleCall_2.stop, grammarAccess.getPrimaryExpressionAccess().getIdentifierRefParserRuleCall_2()); }
	    |
	JSXFragmentParserRuleCall_3=ruleJSXFragment{ announce($JSXFragmentParserRuleCall_3.start, $JSXFragmentParserRuleCall_3.stop, grammarAccess.getPrimaryExpressionAccess().getJSXFragmentParserRuleCall_3()); }
	    |
	JSXElementParserRuleCall_4=ruleJSXElement{ announce($JSXElementParserRuleCall_4.start, $JSXElementParserRuleCall_4.stop, grammarAccess.getPrimaryExpressionAccess().getJSXElementParserRuleCall_4()); }
	    |
	ImportCallExpressionParserRuleCall_5=norm1_ImportCallExpression{ announce($ImportCallExpressionParserRuleCall_5.start, $ImportCallExpressionParserRuleCall_5.stop, grammarAccess.getPrimaryExpressionAccess().getImportCallExpressionParserRuleCall_5()); }
	    |
	ParameterizedCallExpressionParserRuleCall_6=norm1_ParameterizedCallExpression{ announce($ParameterizedCallExpressionParserRuleCall_6.start, $ParameterizedCallExpressionParserRuleCall_6.stop, grammarAccess.getPrimaryExpressionAccess().getParameterizedCallExpressionParserRuleCall_6()); }
	    |
	LiteralParserRuleCall_7=ruleLiteral{ announce($LiteralParserRuleCall_7.start, $LiteralParserRuleCall_7.stop, grammarAccess.getPrimaryExpressionAccess().getLiteralParserRuleCall_7()); }
	    |
	ArrayLiteralParserRuleCall_8=norm1_ArrayLiteral{ announce($ArrayLiteralParserRuleCall_8.start, $ArrayLiteralParserRuleCall_8.stop, grammarAccess.getPrimaryExpressionAccess().getArrayLiteralParserRuleCall_8()); }
	    |
	ObjectLiteralParserRuleCall_9=norm1_ObjectLiteral{ announce($ObjectLiteralParserRuleCall_9.start, $ObjectLiteralParserRuleCall_9.stop, grammarAccess.getPrimaryExpressionAccess().getObjectLiteralParserRuleCall_9()); }
	    |
	ParenExpressionParserRuleCall_10=norm1_ParenExpression{ announce($ParenExpressionParserRuleCall_10.start, $ParenExpressionParserRuleCall_10.stop, grammarAccess.getPrimaryExpressionAccess().getParenExpressionParserRuleCall_10()); }
	    |
	AnnotatedExpressionParserRuleCall_11=norm1_AnnotatedExpression{ announce($AnnotatedExpressionParserRuleCall_11.start, $AnnotatedExpressionParserRuleCall_11.stop, grammarAccess.getPrimaryExpressionAccess().getAnnotatedExpressionParserRuleCall_11()); }
	    |
	FunctionExpressionParserRuleCall_12=ruleFunctionExpression{ announce($FunctionExpressionParserRuleCall_12.start, $FunctionExpressionParserRuleCall_12.stop, grammarAccess.getPrimaryExpressionAccess().getFunctionExpressionParserRuleCall_12()); }
	    |
	(
		((
			(
				(
					Async
				)
			)
			ruleNoLineTerminator
			Function
		)
		)=>
		AsyncFunctionExpressionParserRuleCall_13=ruleAsyncFunctionExpression{ announce($AsyncFunctionExpressionParserRuleCall_13.start, $AsyncFunctionExpressionParserRuleCall_13.stop, grammarAccess.getPrimaryExpressionAccess().getAsyncFunctionExpressionParserRuleCall_13()); }
	)
	    |
	N4ClassExpressionParserRuleCall_14=norm1_N4ClassExpression{ announce($N4ClassExpressionParserRuleCall_14.start, $N4ClassExpressionParserRuleCall_14.stop, grammarAccess.getPrimaryExpressionAccess().getN4ClassExpressionParserRuleCall_14()); }
	    |
	TemplateLiteralParserRuleCall_15=norm1_TemplateLiteral{ announce($TemplateLiteralParserRuleCall_15.start, $TemplateLiteralParserRuleCall_15.stop, grammarAccess.getPrimaryExpressionAccess().getTemplateLiteralParserRuleCall_15()); }
)
;

// Entry rule entryRuleParenExpression
entryRuleParenExpression
	:
	ruleParenExpression
	EOF;

// Rule ParenExpression
ruleParenExpression
@init {
}:
(
	LeftParenthesisKeyword_0=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_0, grammarAccess.getParenExpressionAccess().getLeftParenthesisKeyword_0());
	}
	(
		(
			ExpressionExpressionParserRuleCall_1_0=norm1_Expression{
				announce($ExpressionExpressionParserRuleCall_1_0.start, $ExpressionExpressionParserRuleCall_1_0.stop, grammarAccess.getParenExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
	RightParenthesisKeyword_2=RightParenthesis
	 {
		announce($RightParenthesisKeyword_2, grammarAccess.getParenExpressionAccess().getRightParenthesisKeyword_2());
	}
)
;


// Rule ParenExpression
norm1_ParenExpression
@init {
}:
(
	LeftParenthesisKeyword_0=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_0, grammarAccess.getParenExpressionAccess().getLeftParenthesisKeyword_0());
	}
	(
		(
			ExpressionExpressionParserRuleCall_1_0=norm3_Expression{
				announce($ExpressionExpressionParserRuleCall_1_0.start, $ExpressionExpressionParserRuleCall_1_0.stop, grammarAccess.getParenExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
	RightParenthesisKeyword_2=RightParenthesis
	 {
		announce($RightParenthesisKeyword_2, grammarAccess.getParenExpressionAccess().getRightParenthesisKeyword_2());
	}
)
;

// Entry rule entryRuleIdentifierRef
entryRuleIdentifierRef
	:
	ruleIdentifierRef
	EOF;

// Rule IdentifierRef
ruleIdentifierRef
@init {
}:
(
	(
		(
			IdIdentifiableElementBindingIdentifierParserRuleCall_0_0_1=ruleBindingIdentifier{
				announce($IdIdentifiableElementBindingIdentifierParserRuleCall_0_0_1.start, $IdIdentifiableElementBindingIdentifierParserRuleCall_0_0_1.stop, grammarAccess.getIdentifierRefAccess().getIdAssignment_0());
			}
		)
	)
	    |
	(
		(
			(
				IdIdentifiableElementBindingIdentifierParserRuleCall_1_1_0_1=ruleBindingIdentifier{
					announce($IdIdentifiableElementBindingIdentifierParserRuleCall_1_1_0_1.start, $IdIdentifiableElementBindingIdentifierParserRuleCall_1_1_0_1.stop, grammarAccess.getIdentifierRefAccess().getIdAssignment_1_1());
				}
			)
		)
		VersionRequestParserRuleCall_1_2=ruleVersionRequest{ announce($VersionRequestParserRuleCall_1_2.start, $VersionRequestParserRuleCall_1_2.stop, grammarAccess.getIdentifierRefAccess().getVersionRequestParserRuleCall_1_2()); }
	)
)
;


// Rule IdentifierRef
norm1_IdentifierRef
@init {
}:
(
	(
		(
			IdIdentifiableElementBindingIdentifierParserRuleCall_0_0_1=norm1_BindingIdentifier{
				announce($IdIdentifiableElementBindingIdentifierParserRuleCall_0_0_1.start, $IdIdentifiableElementBindingIdentifierParserRuleCall_0_0_1.stop, grammarAccess.getIdentifierRefAccess().getIdAssignment_0());
			}
		)
	)
	    |
	(
		(
			(
				IdIdentifiableElementBindingIdentifierParserRuleCall_1_1_0_1=norm1_BindingIdentifier{
					announce($IdIdentifiableElementBindingIdentifierParserRuleCall_1_1_0_1.start, $IdIdentifiableElementBindingIdentifierParserRuleCall_1_1_0_1.stop, grammarAccess.getIdentifierRefAccess().getIdAssignment_1_1());
				}
			)
		)
		VersionRequestParserRuleCall_1_2=ruleVersionRequest{ announce($VersionRequestParserRuleCall_1_2.start, $VersionRequestParserRuleCall_1_2.stop, grammarAccess.getIdentifierRefAccess().getVersionRequestParserRuleCall_1_2()); }
	)
)
;

// Entry rule entryRuleSuperLiteral
entryRuleSuperLiteral
	:
	ruleSuperLiteral
	EOF;

// Rule SuperLiteral
ruleSuperLiteral
@init {
}:
(
	SuperKeyword_1=Super
	 {
		announce($SuperKeyword_1, grammarAccess.getSuperLiteralAccess().getSuperKeyword_1());
	}
)
;

// Entry rule entryRuleThisLiteral
entryRuleThisLiteral
	:
	ruleThisLiteral
	EOF;

// Rule ThisLiteral
ruleThisLiteral
@init {
}:
(
	ThisKeyword_1=This_1
	 {
		announce($ThisKeyword_1, grammarAccess.getThisLiteralAccess().getThisKeyword_1());
	}
)
;

// Entry rule entryRuleArrayLiteral
entryRuleArrayLiteral
	:
	ruleArrayLiteral
	EOF;

// Rule ArrayLiteral
ruleArrayLiteral
@init {
}:
(
	LeftSquareBracketKeyword_1=LeftSquareBracket
	 {
		announce($LeftSquareBracketKeyword_1, grammarAccess.getArrayLiteralAccess().getLeftSquareBracketKeyword_1());
	}
	(
		(
			ElementsArrayPaddingParserRuleCall_2_0=ruleArrayPadding{
				announce($ElementsArrayPaddingParserRuleCall_2_0.start, $ElementsArrayPaddingParserRuleCall_2_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_2());
			}
		)
	)*
	(
		(
			(
				ElementsArrayElementParserRuleCall_3_0_0=ruleArrayElement{
					announce($ElementsArrayElementParserRuleCall_3_0_0.start, $ElementsArrayElementParserRuleCall_3_0_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_3_0());
				}
			)
		)
		(
			CommaKeyword_3_1_0=Comma
			 {
				announce($CommaKeyword_3_1_0, grammarAccess.getArrayLiteralAccess().getCommaKeyword_3_1_0());
			}
			(
				(
					ElementsArrayPaddingParserRuleCall_3_1_1_0=ruleArrayPadding{
						announce($ElementsArrayPaddingParserRuleCall_3_1_1_0.start, $ElementsArrayPaddingParserRuleCall_3_1_1_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_3_1_1());
					}
				)
			)*
			(
				(
					ElementsArrayElementParserRuleCall_3_1_2_0=ruleArrayElement{
						announce($ElementsArrayElementParserRuleCall_3_1_2_0.start, $ElementsArrayElementParserRuleCall_3_1_2_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_3_1_2());
					}
				)
			)
		)*
		(
			(
				(
					TrailingCommaCommaKeyword_3_2_0_0=Comma
					 {
						announce($TrailingCommaCommaKeyword_3_2_0_0, grammarAccess.getArrayLiteralAccess().getTrailingCommaCommaKeyword_3_2_0_0());
					}
				)
			)
			(
				(
					ElementsArrayPaddingParserRuleCall_3_2_1_0=ruleArrayPadding{
						announce($ElementsArrayPaddingParserRuleCall_3_2_1_0.start, $ElementsArrayPaddingParserRuleCall_3_2_1_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_3_2_1());
					}
				)
			)*
		)?
	)?
	RightSquareBracketKeyword_4=RightSquareBracket
	 {
		announce($RightSquareBracketKeyword_4, grammarAccess.getArrayLiteralAccess().getRightSquareBracketKeyword_4());
	}
)
;


// Rule ArrayLiteral
norm1_ArrayLiteral
@init {
}:
(
	LeftSquareBracketKeyword_1=LeftSquareBracket
	 {
		announce($LeftSquareBracketKeyword_1, grammarAccess.getArrayLiteralAccess().getLeftSquareBracketKeyword_1());
	}
	(
		(
			ElementsArrayPaddingParserRuleCall_2_0=ruleArrayPadding{
				announce($ElementsArrayPaddingParserRuleCall_2_0.start, $ElementsArrayPaddingParserRuleCall_2_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_2());
			}
		)
	)*
	(
		(
			(
				ElementsArrayElementParserRuleCall_3_0_0=norm1_ArrayElement{
					announce($ElementsArrayElementParserRuleCall_3_0_0.start, $ElementsArrayElementParserRuleCall_3_0_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_3_0());
				}
			)
		)
		(
			CommaKeyword_3_1_0=Comma
			 {
				announce($CommaKeyword_3_1_0, grammarAccess.getArrayLiteralAccess().getCommaKeyword_3_1_0());
			}
			(
				(
					ElementsArrayPaddingParserRuleCall_3_1_1_0=ruleArrayPadding{
						announce($ElementsArrayPaddingParserRuleCall_3_1_1_0.start, $ElementsArrayPaddingParserRuleCall_3_1_1_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_3_1_1());
					}
				)
			)*
			(
				(
					ElementsArrayElementParserRuleCall_3_1_2_0=norm1_ArrayElement{
						announce($ElementsArrayElementParserRuleCall_3_1_2_0.start, $ElementsArrayElementParserRuleCall_3_1_2_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_3_1_2());
					}
				)
			)
		)*
		(
			(
				(
					TrailingCommaCommaKeyword_3_2_0_0=Comma
					 {
						announce($TrailingCommaCommaKeyword_3_2_0_0, grammarAccess.getArrayLiteralAccess().getTrailingCommaCommaKeyword_3_2_0_0());
					}
				)
			)
			(
				(
					ElementsArrayPaddingParserRuleCall_3_2_1_0=ruleArrayPadding{
						announce($ElementsArrayPaddingParserRuleCall_3_2_1_0.start, $ElementsArrayPaddingParserRuleCall_3_2_1_0.stop, grammarAccess.getArrayLiteralAccess().getElementsAssignment_3_2_1());
					}
				)
			)*
		)?
	)?
	RightSquareBracketKeyword_4=RightSquareBracket
	 {
		announce($RightSquareBracketKeyword_4, grammarAccess.getArrayLiteralAccess().getRightSquareBracketKeyword_4());
	}
)
;

// Entry rule entryRuleArrayPadding
entryRuleArrayPadding
	:
	ruleArrayPadding
	EOF;

// Rule ArrayPadding
ruleArrayPadding
@init {
}:
(
	CommaKeyword_1=Comma
	 {
		announce($CommaKeyword_1, grammarAccess.getArrayPaddingAccess().getCommaKeyword_1());
	}
)
;

// Entry rule entryRuleArrayElement
entryRuleArrayElement
	:
	ruleArrayElement
	EOF;

// Rule ArrayElement
ruleArrayElement
@init {
}:
(
	(
		(
			SpreadFullStopFullStopFullStopKeyword_1_0=FullStopFullStopFullStop
			 {
				announce($SpreadFullStopFullStopFullStopKeyword_1_0, grammarAccess.getArrayElementAccess().getSpreadFullStopFullStopFullStopKeyword_1_0());
			}
		)
	)?
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_2_0=norm1_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_2_0.start, $ExpressionAssignmentExpressionParserRuleCall_2_0.stop, grammarAccess.getArrayElementAccess().getExpressionAssignment_2());
			}
		)
	)
)
;


// Rule ArrayElement
norm1_ArrayElement
@init {
}:
(
	(
		(
			SpreadFullStopFullStopFullStopKeyword_1_0=FullStopFullStopFullStop
			 {
				announce($SpreadFullStopFullStopFullStopKeyword_1_0, grammarAccess.getArrayElementAccess().getSpreadFullStopFullStopFullStopKeyword_1_0());
			}
		)
	)?
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_2_0=norm3_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_2_0.start, $ExpressionAssignmentExpressionParserRuleCall_2_0.stop, grammarAccess.getArrayElementAccess().getExpressionAssignment_2());
			}
		)
	)
)
;

// Entry rule entryRuleObjectLiteral
entryRuleObjectLiteral
	:
	ruleObjectLiteral
	EOF;

// Rule ObjectLiteral
ruleObjectLiteral
@init {
}:
(
	LeftCurlyBracketKeyword_1=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_1, grammarAccess.getObjectLiteralAccess().getLeftCurlyBracketKeyword_1());
	}
	(
		(
			(
				PropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0=rulePropertyAssignment{
					announce($PropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0.start, $PropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0.stop, grammarAccess.getObjectLiteralAccess().getPropertyAssignmentsAssignment_2_0());
				}
			)
		)
		(
			CommaKeyword_2_1_0=Comma
			 {
				announce($CommaKeyword_2_1_0, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_1_0());
			}
			(
				(
					PropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0=rulePropertyAssignment{
						announce($PropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0.start, $PropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0.stop, grammarAccess.getObjectLiteralAccess().getPropertyAssignmentsAssignment_2_1_1());
					}
				)
			)
		)*
		(
			CommaKeyword_2_2=Comma
			 {
				announce($CommaKeyword_2_2, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_2());
			}
		)?
	)?
	RightCurlyBracketKeyword_3=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_3, grammarAccess.getObjectLiteralAccess().getRightCurlyBracketKeyword_3());
	}
)
;


// Rule ObjectLiteral
norm1_ObjectLiteral
@init {
}:
(
	LeftCurlyBracketKeyword_1=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_1, grammarAccess.getObjectLiteralAccess().getLeftCurlyBracketKeyword_1());
	}
	(
		(
			(
				PropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0=norm1_PropertyAssignment{
					announce($PropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0.start, $PropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0.stop, grammarAccess.getObjectLiteralAccess().getPropertyAssignmentsAssignment_2_0());
				}
			)
		)
		(
			CommaKeyword_2_1_0=Comma
			 {
				announce($CommaKeyword_2_1_0, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_1_0());
			}
			(
				(
					PropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0=norm1_PropertyAssignment{
						announce($PropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0.start, $PropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0.stop, grammarAccess.getObjectLiteralAccess().getPropertyAssignmentsAssignment_2_1_1());
					}
				)
			)
		)*
		(
			CommaKeyword_2_2=Comma
			 {
				announce($CommaKeyword_2_2, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_2());
			}
		)?
	)?
	RightCurlyBracketKeyword_3=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_3, grammarAccess.getObjectLiteralAccess().getRightCurlyBracketKeyword_3());
	}
)
;

// Entry rule entryRulePropertyAssignment
entryRulePropertyAssignment
	:
	rulePropertyAssignment
	EOF;

// Rule PropertyAssignment
rulePropertyAssignment
@init {
}:
(
	AnnotatedPropertyAssignmentParserRuleCall_0=ruleAnnotatedPropertyAssignment{ announce($AnnotatedPropertyAssignmentParserRuleCall_0.start, $AnnotatedPropertyAssignmentParserRuleCall_0.stop, grammarAccess.getPropertyAssignmentAccess().getAnnotatedPropertyAssignmentParserRuleCall_0()); }
	    |
	(
		((
			(
				(
					ruleTypeRefWithModifiers
				)
			)?
			(
				(
					ruleLiteralOrComputedPropertyName
				)
			)
			(
				(
					QuestionMark
				)
			)?
			Colon
		)
		)=>
		PropertyNameValuePairParserRuleCall_1=rulePropertyNameValuePair{ announce($PropertyNameValuePairParserRuleCall_1.start, $PropertyNameValuePairParserRuleCall_1.stop, grammarAccess.getPropertyAssignmentAccess().getPropertyNameValuePairParserRuleCall_1()); }
	)
	    |
	(
		((
			ruleGetterHeader
		)
		)=>
		PropertyGetterDeclarationParserRuleCall_2=rulePropertyGetterDeclaration{ announce($PropertyGetterDeclarationParserRuleCall_2.start, $PropertyGetterDeclarationParserRuleCall_2.stop, grammarAccess.getPropertyAssignmentAccess().getPropertyGetterDeclarationParserRuleCall_2()); }
	)
	    |
	(
		((
			Set
			(
				Break
				    |
				Case
				    |
				Catch
				    |
				Class
				    |
				Const
				    |
				Continue
				    |
				Debugger
				    |
				Default
				    |
				Delete
				    |
				Do
				    |
				Else
				    |
				Export
				    |
				Extends
				    |
				Finally
				    |
				For
				    |
				Function
				    |
				If
				    |
				Import
				    |
				In
				    |
				Instanceof
				    |
				New
				    |
				Return
				    |
				Super
				    |
				Switch
				    |
				This_1
				    |
				Throw
				    |
				Try
				    |
				Typeof
				    |
				Var
				    |
				Void
				    |
				While
				    |
				With
				    |
				Yield
				    |
				Null
				    |
				True
				    |
				False
				    |
				Enum
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LeftSquareBracket
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
		)
		)=>
		PropertySetterDeclarationParserRuleCall_3=rulePropertySetterDeclaration{ announce($PropertySetterDeclarationParserRuleCall_3.start, $PropertySetterDeclarationParserRuleCall_3.stop, grammarAccess.getPropertyAssignmentAccess().getPropertySetterDeclarationParserRuleCall_3()); }
	)
	    |
	(
		((
			(
				ruleTypeVariables
			)?
			(
				(
					ruleTypeRefWithModifiers
				)
			)?
			(
				(
					(
						(
							Asterisk
						)
					)
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
				    |
				(
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
			)
		)
		)=>
		PropertyMethodDeclarationParserRuleCall_4=rulePropertyMethodDeclaration{ announce($PropertyMethodDeclarationParserRuleCall_4.start, $PropertyMethodDeclarationParserRuleCall_4.stop, grammarAccess.getPropertyAssignmentAccess().getPropertyMethodDeclarationParserRuleCall_4()); }
	)
	    |
	PropertyNameValuePairSingleNameParserRuleCall_5=rulePropertyNameValuePairSingleName{ announce($PropertyNameValuePairSingleNameParserRuleCall_5.start, $PropertyNameValuePairSingleNameParserRuleCall_5.stop, grammarAccess.getPropertyAssignmentAccess().getPropertyNameValuePairSingleNameParserRuleCall_5()); }
)
;


// Rule PropertyAssignment
norm1_PropertyAssignment
@init {
}:
(
	AnnotatedPropertyAssignmentParserRuleCall_0=norm1_AnnotatedPropertyAssignment{ announce($AnnotatedPropertyAssignmentParserRuleCall_0.start, $AnnotatedPropertyAssignmentParserRuleCall_0.stop, grammarAccess.getPropertyAssignmentAccess().getAnnotatedPropertyAssignmentParserRuleCall_0()); }
	    |
	(
		((
			(
				(
					ruleTypeRefWithModifiers
				)
			)?
			(
				(
					norm1_LiteralOrComputedPropertyName
				)
			)
			(
				(
					QuestionMark
				)
			)?
			Colon
		)
		)=>
		PropertyNameValuePairParserRuleCall_1=norm1_PropertyNameValuePair{ announce($PropertyNameValuePairParserRuleCall_1.start, $PropertyNameValuePairParserRuleCall_1.stop, grammarAccess.getPropertyAssignmentAccess().getPropertyNameValuePairParserRuleCall_1()); }
	)
	    |
	(
		((
			norm1_GetterHeader
		)
		)=>
		PropertyGetterDeclarationParserRuleCall_2=norm1_PropertyGetterDeclaration{ announce($PropertyGetterDeclarationParserRuleCall_2.start, $PropertyGetterDeclarationParserRuleCall_2.stop, grammarAccess.getPropertyAssignmentAccess().getPropertyGetterDeclarationParserRuleCall_2()); }
	)
	    |
	(
		((
			Set
			(
				Break
				    |
				Case
				    |
				Catch
				    |
				Class
				    |
				Const
				    |
				Continue
				    |
				Debugger
				    |
				Default
				    |
				Delete
				    |
				Do
				    |
				Else
				    |
				Export
				    |
				Extends
				    |
				Finally
				    |
				For
				    |
				Function
				    |
				If
				    |
				Import
				    |
				In
				    |
				Instanceof
				    |
				New
				    |
				Return
				    |
				Super
				    |
				Switch
				    |
				This_1
				    |
				Throw
				    |
				Try
				    |
				Typeof
				    |
				Var
				    |
				Void
				    |
				While
				    |
				With
				    |
				Yield
				    |
				Null
				    |
				True
				    |
				False
				    |
				Enum
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LeftSquareBracket
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
		)
		)=>
		PropertySetterDeclarationParserRuleCall_3=norm1_PropertySetterDeclaration{ announce($PropertySetterDeclarationParserRuleCall_3.start, $PropertySetterDeclarationParserRuleCall_3.stop, grammarAccess.getPropertyAssignmentAccess().getPropertySetterDeclarationParserRuleCall_3()); }
	)
	    |
	(
		((
			(
				ruleTypeVariables
			)?
			(
				(
					ruleTypeRefWithModifiers
				)
			)?
			(
				(
					(
						(
							Asterisk
						)
					)
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
				    |
				(
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
			)
		)
		)=>
		PropertyMethodDeclarationParserRuleCall_4=norm1_PropertyMethodDeclaration{ announce($PropertyMethodDeclarationParserRuleCall_4.start, $PropertyMethodDeclarationParserRuleCall_4.stop, grammarAccess.getPropertyAssignmentAccess().getPropertyMethodDeclarationParserRuleCall_4()); }
	)
	    |
	PropertyNameValuePairSingleNameParserRuleCall_5=norm1_PropertyNameValuePairSingleName{ announce($PropertyNameValuePairSingleNameParserRuleCall_5.start, $PropertyNameValuePairSingleNameParserRuleCall_5.stop, grammarAccess.getPropertyAssignmentAccess().getPropertyNameValuePairSingleNameParserRuleCall_5()); }
)
;

// Entry rule entryRuleAnnotatedPropertyAssignment
entryRuleAnnotatedPropertyAssignment
	:
	ruleAnnotatedPropertyAssignment
	EOF;

// Rule AnnotatedPropertyAssignment
ruleAnnotatedPropertyAssignment
@init {
}:
(
	PropertyAssignmentAnnotationListParserRuleCall_0=rulePropertyAssignmentAnnotationList{ announce($PropertyAssignmentAnnotationListParserRuleCall_0.start, $PropertyAssignmentAnnotationListParserRuleCall_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyAssignmentAnnotationListParserRuleCall_0()); }
	(
		(
			(
				((
					(
						(
							ruleTypeRefWithModifiers
						)
					)?
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					Colon
				)
				)=>
				(
					(
						(
							DeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0=ruleTypeRefWithModifiers{
								announce($DeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0.start, $DeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredTypeRefAssignment_1_0_0_0_1());
							}
						)
					)?
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0=ruleLiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameAssignment_1_0_0_0_2());
							}
						)
					)
					ColonKeyword_1_0_0_0_3=Colon
					 {
						announce($ColonKeyword_1_0_0_0_3, grammarAccess.getAnnotatedPropertyAssignmentAccess().getColonKeyword_1_0_0_0_3());
					}
				)
			)
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_0_1_0=norm1_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_0_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getExpressionAssignment_1_0_1());
					}
				)
			)
		)
		    |
		(
			(
				((
					ruleGetterHeader
				)
				)=>
				(
					GetterHeaderParserRuleCall_1_1_0_0_1=ruleGetterHeader{ announce($GetterHeaderParserRuleCall_1_1_0_0_1.start, $GetterHeaderParserRuleCall_1_1_0_0_1.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getGetterHeaderParserRuleCall_1_1_0_0_1()); }
				)
			)
			(
				((
					LeftCurlyBracket
				)
				)=>
				(
					BodyBlockParserRuleCall_1_1_1_0=ruleBlock{
						announce($BodyBlockParserRuleCall_1_1_1_0.start, $BodyBlockParserRuleCall_1_1_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getBodyAssignment_1_1_1());
					}
				)
			)
		)
		    |
		(
			(
				((
					Set
					(
						Break
						    |
						Case
						    |
						Catch
						    |
						Class
						    |
						Const
						    |
						Continue
						    |
						Debugger
						    |
						Default
						    |
						Delete
						    |
						Do
						    |
						Else
						    |
						Export
						    |
						Extends
						    |
						Finally
						    |
						For
						    |
						Function
						    |
						If
						    |
						Import
						    |
						In
						    |
						Instanceof
						    |
						New
						    |
						Return
						    |
						Super
						    |
						Switch
						    |
						This_1
						    |
						Throw
						    |
						Try
						    |
						Typeof
						    |
						Var
						    |
						Void
						    |
						While
						    |
						With
						    |
						Yield
						    |
						Null
						    |
						True
						    |
						False
						    |
						Enum
						    |
						Get
						    |
						Set
						    |
						Let
						    |
						Project
						    |
						External
						    |
						Abstract
						    |
						Static
						    |
						As
						    |
						From
						    |
						Constructor
						    |
						Of
						    |
						Target
						    |
						Type
						    |
						Union
						    |
						Intersection
						    |
						This
						    |
						Promisify
						    |
						Await
						    |
						Async
						    |
						Implements
						    |
						Interface
						    |
						Private
						    |
						Protected
						    |
						Public
						    |
						Out
						    |
						LeftSquareBracket
						    |
						RULE_IDENTIFIER
						    |
						RULE_STRING
						    |
						RULE_DOUBLE
						    |
						RULE_INT
						    |
						RULE_OCTAL_INT
						    |
						RULE_HEX_INT
						    |
						RULE_SCIENTIFIC_INT
					)
				)
				)=>
				(
					SetKeyword_1_2_0_0_1=Set
					 {
						announce($SetKeyword_1_2_0_0_1, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSetKeyword_1_2_0_0_1());
					}
					(
						(Break | 
						Case | 
						Catch | 
						Class | 
						Const | 
						Continue | 
						Debugger | 
						Default | 
						Delete | 
						Do | 
						Else | 
						Export | 
						Extends | 
						Finally | 
						For | 
						Function | 
						If | 
						Import | 
						In | 
						Instanceof | 
						New | 
						Return | 
						Super | 
						Switch | 
						This_1 | 
						Throw | 
						Try | 
						Typeof | 
						Var | 
						Void | 
						While | 
						With | 
						Yield | 
						Null | 
						True | 
						False | 
						Enum | 
						Get | 
						Set | 
						Let | 
						Project | 
						External | 
						Abstract | 
						Static | 
						As | 
						From | 
						Constructor | 
						Of | 
						Target | 
						Type | 
						Union | 
						Intersection | 
						This | 
						Promisify | 
						Await | 
						Async | 
						Implements | 
						Interface | 
						Private | 
						Protected | 
						Public | 
						Out | 
						LeftSquareBracket | 
						RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0=ruleLiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameAssignment_1_2_0_0_2());
							}
						)
					)
				)
			)
			(
				(
					DeclaredOptionalQuestionMarkKeyword_1_2_1_0=QuestionMark
					 {
						announce($DeclaredOptionalQuestionMarkKeyword_1_2_1_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredOptionalQuestionMarkKeyword_1_2_1_0());
					}
				)
			)?
			LeftParenthesisKeyword_1_2_2=LeftParenthesis
			 {
				announce($LeftParenthesisKeyword_1_2_2, grammarAccess.getAnnotatedPropertyAssignmentAccess().getLeftParenthesisKeyword_1_2_2());
			}
			(
				(
					FparFormalParameterParserRuleCall_1_2_3_0=ruleFormalParameter{
						announce($FparFormalParameterParserRuleCall_1_2_3_0.start, $FparFormalParameterParserRuleCall_1_2_3_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getFparAssignment_1_2_3());
					}
				)
			)
			RightParenthesisKeyword_1_2_4=RightParenthesis
			 {
				announce($RightParenthesisKeyword_1_2_4, grammarAccess.getAnnotatedPropertyAssignmentAccess().getRightParenthesisKeyword_1_2_4());
			}
			(
				((
					LeftCurlyBracket
				)
				)=>
				(
					BodyBlockParserRuleCall_1_2_5_0=ruleBlock{
						announce($BodyBlockParserRuleCall_1_2_5_0.start, $BodyBlockParserRuleCall_1_2_5_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getBodyAssignment_1_2_5());
					}
				)
			)
		)
		    |
		(
			(
				((
					(
						ruleTypeVariables
					)?
					(
						(
							ruleTypeRefWithModifiers
						)
					)?
					(
						(
							(
								(
									Asterisk
								)
							)
							(
								(
									ruleLiteralOrComputedPropertyName
								)
							)
							LeftParenthesis
						)
						    |
						(
							(
								(
									ruleLiteralOrComputedPropertyName
								)
							)
							LeftParenthesis
						)
					)
				)
				)=>
				(
					(
						TypeVariablesParserRuleCall_1_3_0_0_1=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1_3_0_0_1.start, $TypeVariablesParserRuleCall_1_3_0_0_1.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getTypeVariablesParserRuleCall_1_3_0_0_1()); }
					)?
					(
						(
							ReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0=ruleTypeRefWithModifiers{
								announce($ReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0.start, $ReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getReturnTypeRefAssignment_1_3_0_0_2());
							}
						)
					)?
					(
						(
							(
								(
									GeneratorAsteriskKeyword_1_3_0_0_3_0_0_0=Asterisk
									 {
										announce($GeneratorAsteriskKeyword_1_3_0_0_3_0_0_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getGeneratorAsteriskKeyword_1_3_0_0_3_0_0_0());
									}
								)
							)
							(
								(
									DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0=ruleLiteralOrComputedPropertyName{
										announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameAssignment_1_3_0_0_3_0_1());
									}
								)
							)
							(
								(LeftParenthesis
								)=>
								MethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2=norm1_MethodParamsAndBody{ announce($MethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2.start, $MethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2()); }
							)
						)
						    |
						(
							(
								(
									DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0=ruleLiteralOrComputedPropertyName{
										announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameAssignment_1_3_0_0_3_1_0());
									}
								)
							)
							(
								(LeftParenthesis
								)=>
								MethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1=ruleMethodParamsAndBody{ announce($MethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1.start, $MethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1()); }
							)
						)
					)
				)
			)
			(
				SemicolonKeyword_1_3_1=Semicolon
				 {
					announce($SemicolonKeyword_1_3_1, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSemicolonKeyword_1_3_1());
				}
			)?
		)
		    |
		(
			(
				(
					DeclaredTypeRefTypeRefParserRuleCall_1_4_1_0=ruleTypeRef{
						announce($DeclaredTypeRefTypeRefParserRuleCall_1_4_1_0.start, $DeclaredTypeRefTypeRefParserRuleCall_1_4_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredTypeRefAssignment_1_4_1());
					}
				)
			)?
			(
				(
					IdentifierRefIdentifierRefParserRuleCall_1_4_2_0=ruleIdentifierRef{
						announce($IdentifierRefIdentifierRefParserRuleCall_1_4_2_0.start, $IdentifierRefIdentifierRefParserRuleCall_1_4_2_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getIdentifierRefAssignment_1_4_2());
					}
				)
			)
			(
				EqualsSignKeyword_1_4_3_0=EqualsSign
				 {
					announce($EqualsSignKeyword_1_4_3_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getEqualsSignKeyword_1_4_3_0());
				}
				(
					(
						ExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0=norm1_AssignmentExpression{
							announce($ExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getExpressionAssignment_1_4_3_1());
						}
					)
				)
			)?
		)
	)
)
;


// Rule AnnotatedPropertyAssignment
norm1_AnnotatedPropertyAssignment
@init {
}:
(
	PropertyAssignmentAnnotationListParserRuleCall_0=rulePropertyAssignmentAnnotationList{ announce($PropertyAssignmentAnnotationListParserRuleCall_0.start, $PropertyAssignmentAnnotationListParserRuleCall_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyAssignmentAnnotationListParserRuleCall_0()); }
	(
		(
			(
				((
					(
						(
							ruleTypeRefWithModifiers
						)
					)?
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					Colon
				)
				)=>
				(
					(
						(
							DeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0=ruleTypeRefWithModifiers{
								announce($DeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0.start, $DeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredTypeRefAssignment_1_0_0_0_1());
							}
						)
					)?
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0=norm1_LiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameAssignment_1_0_0_0_2());
							}
						)
					)
					ColonKeyword_1_0_0_0_3=Colon
					 {
						announce($ColonKeyword_1_0_0_0_3, grammarAccess.getAnnotatedPropertyAssignmentAccess().getColonKeyword_1_0_0_0_3());
					}
				)
			)
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_1_0_1_0=norm3_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_1_0_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getExpressionAssignment_1_0_1());
					}
				)
			)
		)
		    |
		(
			(
				((
					norm1_GetterHeader
				)
				)=>
				(
					GetterHeaderParserRuleCall_1_1_0_0_1=norm1_GetterHeader{ announce($GetterHeaderParserRuleCall_1_1_0_0_1.start, $GetterHeaderParserRuleCall_1_1_0_0_1.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getGetterHeaderParserRuleCall_1_1_0_0_1()); }
				)
			)
			(
				((
					LeftCurlyBracket
				)
				)=>
				(
					BodyBlockParserRuleCall_1_1_1_0=ruleBlock{
						announce($BodyBlockParserRuleCall_1_1_1_0.start, $BodyBlockParserRuleCall_1_1_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getBodyAssignment_1_1_1());
					}
				)
			)
		)
		    |
		(
			(
				((
					Set
					(
						Break
						    |
						Case
						    |
						Catch
						    |
						Class
						    |
						Const
						    |
						Continue
						    |
						Debugger
						    |
						Default
						    |
						Delete
						    |
						Do
						    |
						Else
						    |
						Export
						    |
						Extends
						    |
						Finally
						    |
						For
						    |
						Function
						    |
						If
						    |
						Import
						    |
						In
						    |
						Instanceof
						    |
						New
						    |
						Return
						    |
						Super
						    |
						Switch
						    |
						This_1
						    |
						Throw
						    |
						Try
						    |
						Typeof
						    |
						Var
						    |
						Void
						    |
						While
						    |
						With
						    |
						Yield
						    |
						Null
						    |
						True
						    |
						False
						    |
						Enum
						    |
						Get
						    |
						Set
						    |
						Let
						    |
						Project
						    |
						External
						    |
						Abstract
						    |
						Static
						    |
						As
						    |
						From
						    |
						Constructor
						    |
						Of
						    |
						Target
						    |
						Type
						    |
						Union
						    |
						Intersection
						    |
						This
						    |
						Promisify
						    |
						Await
						    |
						Async
						    |
						Implements
						    |
						Interface
						    |
						Private
						    |
						Protected
						    |
						Public
						    |
						Out
						    |
						LeftSquareBracket
						    |
						RULE_IDENTIFIER
						    |
						RULE_STRING
						    |
						RULE_DOUBLE
						    |
						RULE_INT
						    |
						RULE_OCTAL_INT
						    |
						RULE_HEX_INT
						    |
						RULE_SCIENTIFIC_INT
					)
				)
				)=>
				(
					SetKeyword_1_2_0_0_1=Set
					 {
						announce($SetKeyword_1_2_0_0_1, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSetKeyword_1_2_0_0_1());
					}
					(
						(Break | 
						Case | 
						Catch | 
						Class | 
						Const | 
						Continue | 
						Debugger | 
						Default | 
						Delete | 
						Do | 
						Else | 
						Export | 
						Extends | 
						Finally | 
						For | 
						Function | 
						If | 
						Import | 
						In | 
						Instanceof | 
						New | 
						Return | 
						Super | 
						Switch | 
						This_1 | 
						Throw | 
						Try | 
						Typeof | 
						Var | 
						Void | 
						While | 
						With | 
						Yield | 
						Null | 
						True | 
						False | 
						Enum | 
						Get | 
						Set | 
						Let | 
						Project | 
						External | 
						Abstract | 
						Static | 
						As | 
						From | 
						Constructor | 
						Of | 
						Target | 
						Type | 
						Union | 
						Intersection | 
						This | 
						Promisify | 
						Await | 
						Async | 
						Implements | 
						Interface | 
						Private | 
						Protected | 
						Public | 
						Out | 
						LeftSquareBracket | 
						RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0=norm1_LiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameAssignment_1_2_0_0_2());
							}
						)
					)
				)
			)
			(
				(
					DeclaredOptionalQuestionMarkKeyword_1_2_1_0=QuestionMark
					 {
						announce($DeclaredOptionalQuestionMarkKeyword_1_2_1_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredOptionalQuestionMarkKeyword_1_2_1_0());
					}
				)
			)?
			LeftParenthesisKeyword_1_2_2=LeftParenthesis
			 {
				announce($LeftParenthesisKeyword_1_2_2, grammarAccess.getAnnotatedPropertyAssignmentAccess().getLeftParenthesisKeyword_1_2_2());
			}
			(
				(
					FparFormalParameterParserRuleCall_1_2_3_0=norm1_FormalParameter{
						announce($FparFormalParameterParserRuleCall_1_2_3_0.start, $FparFormalParameterParserRuleCall_1_2_3_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getFparAssignment_1_2_3());
					}
				)
			)
			RightParenthesisKeyword_1_2_4=RightParenthesis
			 {
				announce($RightParenthesisKeyword_1_2_4, grammarAccess.getAnnotatedPropertyAssignmentAccess().getRightParenthesisKeyword_1_2_4());
			}
			(
				((
					LeftCurlyBracket
				)
				)=>
				(
					BodyBlockParserRuleCall_1_2_5_0=ruleBlock{
						announce($BodyBlockParserRuleCall_1_2_5_0.start, $BodyBlockParserRuleCall_1_2_5_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getBodyAssignment_1_2_5());
					}
				)
			)
		)
		    |
		(
			(
				((
					(
						ruleTypeVariables
					)?
					(
						(
							ruleTypeRefWithModifiers
						)
					)?
					(
						(
							(
								(
									Asterisk
								)
							)
							(
								(
									norm1_LiteralOrComputedPropertyName
								)
							)
							LeftParenthesis
						)
						    |
						(
							(
								(
									norm1_LiteralOrComputedPropertyName
								)
							)
							LeftParenthesis
						)
					)
				)
				)=>
				(
					(
						TypeVariablesParserRuleCall_1_3_0_0_1=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1_3_0_0_1.start, $TypeVariablesParserRuleCall_1_3_0_0_1.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getTypeVariablesParserRuleCall_1_3_0_0_1()); }
					)?
					(
						(
							ReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0=ruleTypeRefWithModifiers{
								announce($ReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0.start, $ReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getReturnTypeRefAssignment_1_3_0_0_2());
							}
						)
					)?
					(
						(
							(
								(
									GeneratorAsteriskKeyword_1_3_0_0_3_0_0_0=Asterisk
									 {
										announce($GeneratorAsteriskKeyword_1_3_0_0_3_0_0_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getGeneratorAsteriskKeyword_1_3_0_0_3_0_0_0());
									}
								)
							)
							(
								(
									DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0=norm1_LiteralOrComputedPropertyName{
										announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameAssignment_1_3_0_0_3_0_1());
									}
								)
							)
							(
								(LeftParenthesis
								)=>
								MethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2=norm1_MethodParamsAndBody{ announce($MethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2.start, $MethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2()); }
							)
						)
						    |
						(
							(
								(
									DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0=norm1_LiteralOrComputedPropertyName{
										announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameAssignment_1_3_0_0_3_1_0());
									}
								)
							)
							(
								(LeftParenthesis
								)=>
								MethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1=ruleMethodParamsAndBody{ announce($MethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1.start, $MethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1()); }
							)
						)
					)
				)
			)
			(
				SemicolonKeyword_1_3_1=Semicolon
				 {
					announce($SemicolonKeyword_1_3_1, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSemicolonKeyword_1_3_1());
				}
			)?
		)
		    |
		(
			(
				(
					DeclaredTypeRefTypeRefParserRuleCall_1_4_1_0=ruleTypeRef{
						announce($DeclaredTypeRefTypeRefParserRuleCall_1_4_1_0.start, $DeclaredTypeRefTypeRefParserRuleCall_1_4_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredTypeRefAssignment_1_4_1());
					}
				)
			)?
			(
				(
					IdentifierRefIdentifierRefParserRuleCall_1_4_2_0=norm1_IdentifierRef{
						announce($IdentifierRefIdentifierRefParserRuleCall_1_4_2_0.start, $IdentifierRefIdentifierRefParserRuleCall_1_4_2_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getIdentifierRefAssignment_1_4_2());
					}
				)
			)
			(
				EqualsSignKeyword_1_4_3_0=EqualsSign
				 {
					announce($EqualsSignKeyword_1_4_3_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getEqualsSignKeyword_1_4_3_0());
				}
				(
					(
						ExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0=norm3_AssignmentExpression{
							announce($ExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0.stop, grammarAccess.getAnnotatedPropertyAssignmentAccess().getExpressionAssignment_1_4_3_1());
						}
					)
				)
			)?
		)
	)
)
;

// Entry rule entryRulePropertyMethodDeclaration
entryRulePropertyMethodDeclaration
	:
	rulePropertyMethodDeclaration
	EOF;

// Rule PropertyMethodDeclaration
rulePropertyMethodDeclaration
@init {
}:
(
	(
		((
			(
				ruleTypeVariables
			)?
			(
				(
					ruleTypeRefWithModifiers
				)
			)?
			(
				(
					(
						(
							Asterisk
						)
					)
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
				    |
				(
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
			)
		)
		)=>
		(
			(
				TypeVariablesParserRuleCall_0_0_1=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_0_0_1.start, $TypeVariablesParserRuleCall_0_0_1.stop, grammarAccess.getPropertyMethodDeclarationAccess().getTypeVariablesParserRuleCall_0_0_1()); }
			)?
			(
				(
					ReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0=ruleTypeRefWithModifiers{
						announce($ReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0.start, $ReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0.stop, grammarAccess.getPropertyMethodDeclarationAccess().getReturnTypeRefAssignment_0_0_2());
					}
				)
			)?
			(
				(
					(
						(
							GeneratorAsteriskKeyword_0_0_3_0_0_0=Asterisk
							 {
								announce($GeneratorAsteriskKeyword_0_0_3_0_0_0, grammarAccess.getPropertyMethodDeclarationAccess().getGeneratorAsteriskKeyword_0_0_3_0_0_0());
							}
						)
					)
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0=ruleLiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0.stop, grammarAccess.getPropertyMethodDeclarationAccess().getDeclaredNameAssignment_0_0_3_0_1());
							}
						)
					)
					(
						(LeftParenthesis
						)=>
						MethodParamsAndBodyParserRuleCall_0_0_3_0_2=norm1_MethodParamsAndBody{ announce($MethodParamsAndBodyParserRuleCall_0_0_3_0_2.start, $MethodParamsAndBodyParserRuleCall_0_0_3_0_2.stop, grammarAccess.getPropertyMethodDeclarationAccess().getMethodParamsAndBodyParserRuleCall_0_0_3_0_2()); }
					)
				)
				    |
				(
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0=ruleLiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0.stop, grammarAccess.getPropertyMethodDeclarationAccess().getDeclaredNameAssignment_0_0_3_1_0());
							}
						)
					)
					(
						(LeftParenthesis
						)=>
						MethodParamsAndBodyParserRuleCall_0_0_3_1_1=ruleMethodParamsAndBody{ announce($MethodParamsAndBodyParserRuleCall_0_0_3_1_1.start, $MethodParamsAndBodyParserRuleCall_0_0_3_1_1.stop, grammarAccess.getPropertyMethodDeclarationAccess().getMethodParamsAndBodyParserRuleCall_0_0_3_1_1()); }
					)
				)
			)
		)
	)
	(
		SemicolonKeyword_1=Semicolon
		 {
			announce($SemicolonKeyword_1, grammarAccess.getPropertyMethodDeclarationAccess().getSemicolonKeyword_1());
		}
	)?
)
;


// Rule PropertyMethodDeclaration
norm1_PropertyMethodDeclaration
@init {
}:
(
	(
		((
			(
				ruleTypeVariables
			)?
			(
				(
					ruleTypeRefWithModifiers
				)
			)?
			(
				(
					(
						(
							Asterisk
						)
					)
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
				    |
				(
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
			)
		)
		)=>
		(
			(
				TypeVariablesParserRuleCall_0_0_1=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_0_0_1.start, $TypeVariablesParserRuleCall_0_0_1.stop, grammarAccess.getPropertyMethodDeclarationAccess().getTypeVariablesParserRuleCall_0_0_1()); }
			)?
			(
				(
					ReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0=ruleTypeRefWithModifiers{
						announce($ReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0.start, $ReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0.stop, grammarAccess.getPropertyMethodDeclarationAccess().getReturnTypeRefAssignment_0_0_2());
					}
				)
			)?
			(
				(
					(
						(
							GeneratorAsteriskKeyword_0_0_3_0_0_0=Asterisk
							 {
								announce($GeneratorAsteriskKeyword_0_0_3_0_0_0, grammarAccess.getPropertyMethodDeclarationAccess().getGeneratorAsteriskKeyword_0_0_3_0_0_0());
							}
						)
					)
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0=norm1_LiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0.stop, grammarAccess.getPropertyMethodDeclarationAccess().getDeclaredNameAssignment_0_0_3_0_1());
							}
						)
					)
					(
						(LeftParenthesis
						)=>
						MethodParamsAndBodyParserRuleCall_0_0_3_0_2=norm1_MethodParamsAndBody{ announce($MethodParamsAndBodyParserRuleCall_0_0_3_0_2.start, $MethodParamsAndBodyParserRuleCall_0_0_3_0_2.stop, grammarAccess.getPropertyMethodDeclarationAccess().getMethodParamsAndBodyParserRuleCall_0_0_3_0_2()); }
					)
				)
				    |
				(
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0=norm1_LiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0.stop, grammarAccess.getPropertyMethodDeclarationAccess().getDeclaredNameAssignment_0_0_3_1_0());
							}
						)
					)
					(
						(LeftParenthesis
						)=>
						MethodParamsAndBodyParserRuleCall_0_0_3_1_1=ruleMethodParamsAndBody{ announce($MethodParamsAndBodyParserRuleCall_0_0_3_1_1.start, $MethodParamsAndBodyParserRuleCall_0_0_3_1_1.stop, grammarAccess.getPropertyMethodDeclarationAccess().getMethodParamsAndBodyParserRuleCall_0_0_3_1_1()); }
					)
				)
			)
		)
	)
	(
		SemicolonKeyword_1=Semicolon
		 {
			announce($SemicolonKeyword_1, grammarAccess.getPropertyMethodDeclarationAccess().getSemicolonKeyword_1());
		}
	)?
)
;

// Entry rule entryRulePropertyNameValuePair
entryRulePropertyNameValuePair
	:
	rulePropertyNameValuePair
	EOF;

// Rule PropertyNameValuePair
rulePropertyNameValuePair
@init {
}:
(
	(
		((
			(
				(
					ruleTypeRefWithModifiers
				)
			)?
			(
				(
					ruleLiteralOrComputedPropertyName
				)
			)
			(
				(
					QuestionMark
				)
			)?
			Colon
		)
		)=>
		(
			(
				(
					DeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0=ruleTypeRefWithModifiers{
						announce($DeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0.start, $DeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0.stop, grammarAccess.getPropertyNameValuePairAccess().getDeclaredTypeRefAssignment_0_0_1());
					}
				)
			)?
			(
				(
					DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0=ruleLiteralOrComputedPropertyName{
						announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0.stop, grammarAccess.getPropertyNameValuePairAccess().getDeclaredNameAssignment_0_0_2());
					}
				)
			)
			(
				(
					DeclaredOptionalQuestionMarkKeyword_0_0_3_0=QuestionMark
					 {
						announce($DeclaredOptionalQuestionMarkKeyword_0_0_3_0, grammarAccess.getPropertyNameValuePairAccess().getDeclaredOptionalQuestionMarkKeyword_0_0_3_0());
					}
				)
			)?
			ColonKeyword_0_0_4=Colon
			 {
				announce($ColonKeyword_0_0_4, grammarAccess.getPropertyNameValuePairAccess().getColonKeyword_0_0_4());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm1_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getPropertyNameValuePairAccess().getExpressionAssignment_1());
			}
		)
	)
)
;


// Rule PropertyNameValuePair
norm1_PropertyNameValuePair
@init {
}:
(
	(
		((
			(
				(
					ruleTypeRefWithModifiers
				)
			)?
			(
				(
					norm1_LiteralOrComputedPropertyName
				)
			)
			(
				(
					QuestionMark
				)
			)?
			Colon
		)
		)=>
		(
			(
				(
					DeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0=ruleTypeRefWithModifiers{
						announce($DeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0.start, $DeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0.stop, grammarAccess.getPropertyNameValuePairAccess().getDeclaredTypeRefAssignment_0_0_1());
					}
				)
			)?
			(
				(
					DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0=norm1_LiteralOrComputedPropertyName{
						announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0.stop, grammarAccess.getPropertyNameValuePairAccess().getDeclaredNameAssignment_0_0_2());
					}
				)
			)
			(
				(
					DeclaredOptionalQuestionMarkKeyword_0_0_3_0=QuestionMark
					 {
						announce($DeclaredOptionalQuestionMarkKeyword_0_0_3_0, grammarAccess.getPropertyNameValuePairAccess().getDeclaredOptionalQuestionMarkKeyword_0_0_3_0());
					}
				)
			)?
			ColonKeyword_0_0_4=Colon
			 {
				announce($ColonKeyword_0_0_4, grammarAccess.getPropertyNameValuePairAccess().getColonKeyword_0_0_4());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm3_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getPropertyNameValuePairAccess().getExpressionAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRulePropertyNameValuePairSingleName
entryRulePropertyNameValuePairSingleName
	:
	rulePropertyNameValuePairSingleName
	EOF;

// Rule PropertyNameValuePairSingleName
rulePropertyNameValuePairSingleName
@init {
}:
(
	(
		(
			DeclaredTypeRefTypeRefParserRuleCall_0_0=ruleTypeRef{
				announce($DeclaredTypeRefTypeRefParserRuleCall_0_0.start, $DeclaredTypeRefTypeRefParserRuleCall_0_0.stop, grammarAccess.getPropertyNameValuePairSingleNameAccess().getDeclaredTypeRefAssignment_0());
			}
		)
	)?
	(
		(
			IdentifierRefIdentifierRefParserRuleCall_1_0=ruleIdentifierRef{
				announce($IdentifierRefIdentifierRefParserRuleCall_1_0.start, $IdentifierRefIdentifierRefParserRuleCall_1_0.stop, grammarAccess.getPropertyNameValuePairSingleNameAccess().getIdentifierRefAssignment_1());
			}
		)
	)
	(
		EqualsSignKeyword_2_0=EqualsSign
		 {
			announce($EqualsSignKeyword_2_0, grammarAccess.getPropertyNameValuePairSingleNameAccess().getEqualsSignKeyword_2_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_2_1_0=norm1_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_2_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_2_1_0.stop, grammarAccess.getPropertyNameValuePairSingleNameAccess().getExpressionAssignment_2_1());
				}
			)
		)
	)?
)
;


// Rule PropertyNameValuePairSingleName
norm1_PropertyNameValuePairSingleName
@init {
}:
(
	(
		(
			DeclaredTypeRefTypeRefParserRuleCall_0_0=ruleTypeRef{
				announce($DeclaredTypeRefTypeRefParserRuleCall_0_0.start, $DeclaredTypeRefTypeRefParserRuleCall_0_0.stop, grammarAccess.getPropertyNameValuePairSingleNameAccess().getDeclaredTypeRefAssignment_0());
			}
		)
	)?
	(
		(
			IdentifierRefIdentifierRefParserRuleCall_1_0=norm1_IdentifierRef{
				announce($IdentifierRefIdentifierRefParserRuleCall_1_0.start, $IdentifierRefIdentifierRefParserRuleCall_1_0.stop, grammarAccess.getPropertyNameValuePairSingleNameAccess().getIdentifierRefAssignment_1());
			}
		)
	)
	(
		EqualsSignKeyword_2_0=EqualsSign
		 {
			announce($EqualsSignKeyword_2_0, grammarAccess.getPropertyNameValuePairSingleNameAccess().getEqualsSignKeyword_2_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_2_1_0=norm3_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_2_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_2_1_0.stop, grammarAccess.getPropertyNameValuePairSingleNameAccess().getExpressionAssignment_2_1());
				}
			)
		)
	)?
)
;

// Entry rule entryRulePropertyGetterDeclaration
entryRulePropertyGetterDeclaration
	:
	rulePropertyGetterDeclaration
	EOF;

// Rule PropertyGetterDeclaration
rulePropertyGetterDeclaration
@init {
}:
(
	(
		((
			ruleGetterHeader
		)
		)=>
		(
			GetterHeaderParserRuleCall_0_0_1=ruleGetterHeader{ announce($GetterHeaderParserRuleCall_0_0_1.start, $GetterHeaderParserRuleCall_0_0_1.stop, grammarAccess.getPropertyGetterDeclarationAccess().getGetterHeaderParserRuleCall_0_0_1()); }
		)
	)
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_1_0=ruleBlock{
				announce($BodyBlockParserRuleCall_1_0.start, $BodyBlockParserRuleCall_1_0.stop, grammarAccess.getPropertyGetterDeclarationAccess().getBodyAssignment_1());
			}
		)
	)
)
;


// Rule PropertyGetterDeclaration
norm1_PropertyGetterDeclaration
@init {
}:
(
	(
		((
			norm1_GetterHeader
		)
		)=>
		(
			GetterHeaderParserRuleCall_0_0_1=norm1_GetterHeader{ announce($GetterHeaderParserRuleCall_0_0_1.start, $GetterHeaderParserRuleCall_0_0_1.stop, grammarAccess.getPropertyGetterDeclarationAccess().getGetterHeaderParserRuleCall_0_0_1()); }
		)
	)
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_1_0=ruleBlock{
				announce($BodyBlockParserRuleCall_1_0.start, $BodyBlockParserRuleCall_1_0.stop, grammarAccess.getPropertyGetterDeclarationAccess().getBodyAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRulePropertySetterDeclaration
entryRulePropertySetterDeclaration
	:
	rulePropertySetterDeclaration
	EOF;

// Rule PropertySetterDeclaration
rulePropertySetterDeclaration
@init {
}:
(
	(
		((
			Set
			(
				Break
				    |
				Case
				    |
				Catch
				    |
				Class
				    |
				Const
				    |
				Continue
				    |
				Debugger
				    |
				Default
				    |
				Delete
				    |
				Do
				    |
				Else
				    |
				Export
				    |
				Extends
				    |
				Finally
				    |
				For
				    |
				Function
				    |
				If
				    |
				Import
				    |
				In
				    |
				Instanceof
				    |
				New
				    |
				Return
				    |
				Super
				    |
				Switch
				    |
				This_1
				    |
				Throw
				    |
				Try
				    |
				Typeof
				    |
				Var
				    |
				Void
				    |
				While
				    |
				With
				    |
				Yield
				    |
				Null
				    |
				True
				    |
				False
				    |
				Enum
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LeftSquareBracket
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
		)
		)=>
		(
			SetKeyword_0_0_1=Set
			 {
				announce($SetKeyword_0_0_1, grammarAccess.getPropertySetterDeclarationAccess().getSetKeyword_0_0_1());
			}
			(
				(Break | 
				Case | 
				Catch | 
				Class | 
				Const | 
				Continue | 
				Debugger | 
				Default | 
				Delete | 
				Do | 
				Else | 
				Export | 
				Extends | 
				Finally | 
				For | 
				Function | 
				If | 
				Import | 
				In | 
				Instanceof | 
				New | 
				Return | 
				Super | 
				Switch | 
				This_1 | 
				Throw | 
				Try | 
				Typeof | 
				Var | 
				Void | 
				While | 
				With | 
				Yield | 
				Null | 
				True | 
				False | 
				Enum | 
				Get | 
				Set | 
				Let | 
				Project | 
				External | 
				Abstract | 
				Static | 
				As | 
				From | 
				Constructor | 
				Of | 
				Target | 
				Type | 
				Union | 
				Intersection | 
				This | 
				Promisify | 
				Await | 
				Async | 
				Implements | 
				Interface | 
				Private | 
				Protected | 
				Public | 
				Out | 
				LeftSquareBracket | 
				RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
				(
					DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0=ruleLiteralOrComputedPropertyName{
						announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0.stop, grammarAccess.getPropertySetterDeclarationAccess().getDeclaredNameAssignment_0_0_2());
					}
				)
			)
		)
	)
	(
		(
			DeclaredOptionalQuestionMarkKeyword_1_0=QuestionMark
			 {
				announce($DeclaredOptionalQuestionMarkKeyword_1_0, grammarAccess.getPropertySetterDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_0());
			}
		)
	)?
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getPropertySetterDeclarationAccess().getLeftParenthesisKeyword_2());
	}
	(
		(
			FparFormalParameterParserRuleCall_3_0=ruleFormalParameter{
				announce($FparFormalParameterParserRuleCall_3_0.start, $FparFormalParameterParserRuleCall_3_0.stop, grammarAccess.getPropertySetterDeclarationAccess().getFparAssignment_3());
			}
		)
	)
	RightParenthesisKeyword_4=RightParenthesis
	 {
		announce($RightParenthesisKeyword_4, grammarAccess.getPropertySetterDeclarationAccess().getRightParenthesisKeyword_4());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_5_0=ruleBlock{
				announce($BodyBlockParserRuleCall_5_0.start, $BodyBlockParserRuleCall_5_0.stop, grammarAccess.getPropertySetterDeclarationAccess().getBodyAssignment_5());
			}
		)
	)
)
;


// Rule PropertySetterDeclaration
norm1_PropertySetterDeclaration
@init {
}:
(
	(
		((
			Set
			(
				Break
				    |
				Case
				    |
				Catch
				    |
				Class
				    |
				Const
				    |
				Continue
				    |
				Debugger
				    |
				Default
				    |
				Delete
				    |
				Do
				    |
				Else
				    |
				Export
				    |
				Extends
				    |
				Finally
				    |
				For
				    |
				Function
				    |
				If
				    |
				Import
				    |
				In
				    |
				Instanceof
				    |
				New
				    |
				Return
				    |
				Super
				    |
				Switch
				    |
				This_1
				    |
				Throw
				    |
				Try
				    |
				Typeof
				    |
				Var
				    |
				Void
				    |
				While
				    |
				With
				    |
				Yield
				    |
				Null
				    |
				True
				    |
				False
				    |
				Enum
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LeftSquareBracket
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
		)
		)=>
		(
			SetKeyword_0_0_1=Set
			 {
				announce($SetKeyword_0_0_1, grammarAccess.getPropertySetterDeclarationAccess().getSetKeyword_0_0_1());
			}
			(
				(Break | 
				Case | 
				Catch | 
				Class | 
				Const | 
				Continue | 
				Debugger | 
				Default | 
				Delete | 
				Do | 
				Else | 
				Export | 
				Extends | 
				Finally | 
				For | 
				Function | 
				If | 
				Import | 
				In | 
				Instanceof | 
				New | 
				Return | 
				Super | 
				Switch | 
				This_1 | 
				Throw | 
				Try | 
				Typeof | 
				Var | 
				Void | 
				While | 
				With | 
				Yield | 
				Null | 
				True | 
				False | 
				Enum | 
				Get | 
				Set | 
				Let | 
				Project | 
				External | 
				Abstract | 
				Static | 
				As | 
				From | 
				Constructor | 
				Of | 
				Target | 
				Type | 
				Union | 
				Intersection | 
				This | 
				Promisify | 
				Await | 
				Async | 
				Implements | 
				Interface | 
				Private | 
				Protected | 
				Public | 
				Out | 
				LeftSquareBracket | 
				RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
				(
					DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0=norm1_LiteralOrComputedPropertyName{
						announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0.stop, grammarAccess.getPropertySetterDeclarationAccess().getDeclaredNameAssignment_0_0_2());
					}
				)
			)
		)
	)
	(
		(
			DeclaredOptionalQuestionMarkKeyword_1_0=QuestionMark
			 {
				announce($DeclaredOptionalQuestionMarkKeyword_1_0, grammarAccess.getPropertySetterDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_0());
			}
		)
	)?
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getPropertySetterDeclarationAccess().getLeftParenthesisKeyword_2());
	}
	(
		(
			FparFormalParameterParserRuleCall_3_0=norm1_FormalParameter{
				announce($FparFormalParameterParserRuleCall_3_0.start, $FparFormalParameterParserRuleCall_3_0.stop, grammarAccess.getPropertySetterDeclarationAccess().getFparAssignment_3());
			}
		)
	)
	RightParenthesisKeyword_4=RightParenthesis
	 {
		announce($RightParenthesisKeyword_4, grammarAccess.getPropertySetterDeclarationAccess().getRightParenthesisKeyword_4());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_5_0=ruleBlock{
				announce($BodyBlockParserRuleCall_5_0.start, $BodyBlockParserRuleCall_5_0.stop, grammarAccess.getPropertySetterDeclarationAccess().getBodyAssignment_5());
			}
		)
	)
)
;

// Entry rule entryRuleParameterizedCallExpression
entryRuleParameterizedCallExpression
	:
	ruleParameterizedCallExpression
	EOF;

// Rule ParameterizedCallExpression
ruleParameterizedCallExpression
@init {
}:
(
	ConcreteTypeArgumentsParserRuleCall_0=ruleConcreteTypeArguments{ announce($ConcreteTypeArgumentsParserRuleCall_0.start, $ConcreteTypeArgumentsParserRuleCall_0.stop, grammarAccess.getParameterizedCallExpressionAccess().getConcreteTypeArgumentsParserRuleCall_0()); }
	(
		(
			TargetIdentifierRefParserRuleCall_1_0=ruleIdentifierRef{
				announce($TargetIdentifierRefParserRuleCall_1_0.start, $TargetIdentifierRefParserRuleCall_1_0.stop, grammarAccess.getParameterizedCallExpressionAccess().getTargetAssignment_1());
			}
		)
	)
	ArgumentsWithParenthesesParserRuleCall_2=ruleArgumentsWithParentheses{ announce($ArgumentsWithParenthesesParserRuleCall_2.start, $ArgumentsWithParenthesesParserRuleCall_2.stop, grammarAccess.getParameterizedCallExpressionAccess().getArgumentsWithParenthesesParserRuleCall_2()); }
)
;


// Rule ParameterizedCallExpression
norm1_ParameterizedCallExpression
@init {
}:
(
	ConcreteTypeArgumentsParserRuleCall_0=ruleConcreteTypeArguments{ announce($ConcreteTypeArgumentsParserRuleCall_0.start, $ConcreteTypeArgumentsParserRuleCall_0.stop, grammarAccess.getParameterizedCallExpressionAccess().getConcreteTypeArgumentsParserRuleCall_0()); }
	(
		(
			TargetIdentifierRefParserRuleCall_1_0=norm1_IdentifierRef{
				announce($TargetIdentifierRefParserRuleCall_1_0.start, $TargetIdentifierRefParserRuleCall_1_0.stop, grammarAccess.getParameterizedCallExpressionAccess().getTargetAssignment_1());
			}
		)
	)
	ArgumentsWithParenthesesParserRuleCall_2=norm1_ArgumentsWithParentheses{ announce($ArgumentsWithParenthesesParserRuleCall_2.start, $ArgumentsWithParenthesesParserRuleCall_2.stop, grammarAccess.getParameterizedCallExpressionAccess().getArgumentsWithParenthesesParserRuleCall_2()); }
)
;


// Rule ConcreteTypeArguments
ruleConcreteTypeArguments
@init {
}:
(
	LessThanSignKeyword_0=LessThanSign
	 {
		announce($LessThanSignKeyword_0, grammarAccess.getConcreteTypeArgumentsAccess().getLessThanSignKeyword_0());
	}
	(
		(
			TypeArgsTypeRefParserRuleCall_1_0=ruleTypeRef{
				announce($TypeArgsTypeRefParserRuleCall_1_0.start, $TypeArgsTypeRefParserRuleCall_1_0.stop, grammarAccess.getConcreteTypeArgumentsAccess().getTypeArgsAssignment_1());
			}
		)
	)
	(
		CommaKeyword_2_0=Comma
		 {
			announce($CommaKeyword_2_0, grammarAccess.getConcreteTypeArgumentsAccess().getCommaKeyword_2_0());
		}
		(
			(
				TypeArgsTypeRefParserRuleCall_2_1_0=ruleTypeRef{
					announce($TypeArgsTypeRefParserRuleCall_2_1_0.start, $TypeArgsTypeRefParserRuleCall_2_1_0.stop, grammarAccess.getConcreteTypeArgumentsAccess().getTypeArgsAssignment_2_1());
				}
			)
		)
	)*
	GreaterThanSignKeyword_3=GreaterThanSign
	 {
		announce($GreaterThanSignKeyword_3, grammarAccess.getConcreteTypeArgumentsAccess().getGreaterThanSignKeyword_3());
	}
)
;

// Entry rule entryRuleImportCallExpression
entryRuleImportCallExpression
	:
	ruleImportCallExpression
	EOF;

// Rule ImportCallExpression
ruleImportCallExpression
@init {
}:
(
	ImportKeyword_0=Import
	 {
		announce($ImportKeyword_0, grammarAccess.getImportCallExpressionAccess().getImportKeyword_0());
	}
	ArgumentsWithParenthesesParserRuleCall_1=ruleArgumentsWithParentheses{ announce($ArgumentsWithParenthesesParserRuleCall_1.start, $ArgumentsWithParenthesesParserRuleCall_1.stop, grammarAccess.getImportCallExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1()); }
)
;


// Rule ImportCallExpression
norm1_ImportCallExpression
@init {
}:
(
	ImportKeyword_0=Import
	 {
		announce($ImportKeyword_0, grammarAccess.getImportCallExpressionAccess().getImportKeyword_0());
	}
	ArgumentsWithParenthesesParserRuleCall_1=norm1_ArgumentsWithParentheses{ announce($ArgumentsWithParenthesesParserRuleCall_1.start, $ArgumentsWithParenthesesParserRuleCall_1.stop, grammarAccess.getImportCallExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1()); }
)
;

// Entry rule entryRuleLeftHandSideExpression
entryRuleLeftHandSideExpression
	:
	ruleLeftHandSideExpression
	EOF;

// Rule LeftHandSideExpression
ruleLeftHandSideExpression
@init {
}:
(
	MemberExpressionParserRuleCall_0=ruleMemberExpression{ announce($MemberExpressionParserRuleCall_0.start, $MemberExpressionParserRuleCall_0.stop, grammarAccess.getLeftHandSideExpressionAccess().getMemberExpressionParserRuleCall_0()); }
	(
		ArgumentsWithParenthesesParserRuleCall_1_1=ruleArgumentsWithParentheses{ announce($ArgumentsWithParenthesesParserRuleCall_1_1.start, $ArgumentsWithParenthesesParserRuleCall_1_1.stop, grammarAccess.getLeftHandSideExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1_1()); }
		(
			(
				ArgumentsWithParenthesesParserRuleCall_1_2_0_1=ruleArgumentsWithParentheses{ announce($ArgumentsWithParenthesesParserRuleCall_1_2_0_1.start, $ArgumentsWithParenthesesParserRuleCall_1_2_0_1.stop, grammarAccess.getLeftHandSideExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1_2_0_1()); }
			)
			    |
			(
				IndexedAccessExpressionTailParserRuleCall_1_2_1_1=ruleIndexedAccessExpressionTail{ announce($IndexedAccessExpressionTailParserRuleCall_1_2_1_1.start, $IndexedAccessExpressionTailParserRuleCall_1_2_1_1.stop, grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_1_2_1_1()); }
			)
			    |
			(
				ParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1=ruleParameterizedPropertyAccessExpressionTail{ announce($ParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1.start, $ParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1.stop, grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1()); }
			)
			    |
			(
				(RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
				(
					(
						(
							TemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0=ruleTemplateLiteral{
								announce($TemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0.start, $TemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0.stop, grammarAccess.getLeftHandSideExpressionAccess().getTemplateAssignment_1_2_3_0_1());
							}
						)
					)
				)
			)
		)*
	)?
)
;


// Rule LeftHandSideExpression
norm1_LeftHandSideExpression
@init {
}:
(
	MemberExpressionParserRuleCall_0=norm1_MemberExpression{ announce($MemberExpressionParserRuleCall_0.start, $MemberExpressionParserRuleCall_0.stop, grammarAccess.getLeftHandSideExpressionAccess().getMemberExpressionParserRuleCall_0()); }
	(
		ArgumentsWithParenthesesParserRuleCall_1_1=norm1_ArgumentsWithParentheses{ announce($ArgumentsWithParenthesesParserRuleCall_1_1.start, $ArgumentsWithParenthesesParserRuleCall_1_1.stop, grammarAccess.getLeftHandSideExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1_1()); }
		(
			(
				ArgumentsWithParenthesesParserRuleCall_1_2_0_1=norm1_ArgumentsWithParentheses{ announce($ArgumentsWithParenthesesParserRuleCall_1_2_0_1.start, $ArgumentsWithParenthesesParserRuleCall_1_2_0_1.stop, grammarAccess.getLeftHandSideExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1_2_0_1()); }
			)
			    |
			(
				IndexedAccessExpressionTailParserRuleCall_1_2_1_1=norm1_IndexedAccessExpressionTail{ announce($IndexedAccessExpressionTailParserRuleCall_1_2_1_1.start, $IndexedAccessExpressionTailParserRuleCall_1_2_1_1.stop, grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_1_2_1_1()); }
			)
			    |
			(
				ParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1=norm1_ParameterizedPropertyAccessExpressionTail{ announce($ParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1.start, $ParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1.stop, grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1()); }
			)
			    |
			(
				(RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
				(
					(
						(
							TemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0=norm1_TemplateLiteral{
								announce($TemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0.start, $TemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0.stop, grammarAccess.getLeftHandSideExpressionAccess().getTemplateAssignment_1_2_3_0_1());
							}
						)
					)
				)
			)
		)*
	)?
)
;


// Rule ArgumentsWithParentheses
ruleArgumentsWithParentheses
@init {
}:
(
	LeftParenthesisKeyword_0=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_0, grammarAccess.getArgumentsWithParenthesesAccess().getLeftParenthesisKeyword_0());
	}
	(
		ArgumentsParserRuleCall_1=ruleArguments{ announce($ArgumentsParserRuleCall_1.start, $ArgumentsParserRuleCall_1.stop, grammarAccess.getArgumentsWithParenthesesAccess().getArgumentsParserRuleCall_1()); }
	)?
	RightParenthesisKeyword_2=RightParenthesis
	 {
		announce($RightParenthesisKeyword_2, grammarAccess.getArgumentsWithParenthesesAccess().getRightParenthesisKeyword_2());
	}
)
;


// Rule ArgumentsWithParentheses
norm1_ArgumentsWithParentheses
@init {
}:
(
	LeftParenthesisKeyword_0=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_0, grammarAccess.getArgumentsWithParenthesesAccess().getLeftParenthesisKeyword_0());
	}
	(
		ArgumentsParserRuleCall_1=norm1_Arguments{ announce($ArgumentsParserRuleCall_1.start, $ArgumentsParserRuleCall_1.stop, grammarAccess.getArgumentsWithParenthesesAccess().getArgumentsParserRuleCall_1()); }
	)?
	RightParenthesisKeyword_2=RightParenthesis
	 {
		announce($RightParenthesisKeyword_2, grammarAccess.getArgumentsWithParenthesesAccess().getRightParenthesisKeyword_2());
	}
)
;


// Rule Arguments
ruleArguments
@init {
}:
(
	(
		(
			ArgumentsArgumentParserRuleCall_0_0=ruleArgument{
				announce($ArgumentsArgumentParserRuleCall_0_0.start, $ArgumentsArgumentParserRuleCall_0_0.stop, grammarAccess.getArgumentsAccess().getArgumentsAssignment_0());
			}
		)
	)
	(
		CommaKeyword_1_0=Comma
		 {
			announce($CommaKeyword_1_0, grammarAccess.getArgumentsAccess().getCommaKeyword_1_0());
		}
		(
			(
				ArgumentsArgumentParserRuleCall_1_1_0=ruleArgument{
					announce($ArgumentsArgumentParserRuleCall_1_1_0.start, $ArgumentsArgumentParserRuleCall_1_1_0.stop, grammarAccess.getArgumentsAccess().getArgumentsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule Arguments
norm1_Arguments
@init {
}:
(
	(
		(
			ArgumentsArgumentParserRuleCall_0_0=norm1_Argument{
				announce($ArgumentsArgumentParserRuleCall_0_0.start, $ArgumentsArgumentParserRuleCall_0_0.stop, grammarAccess.getArgumentsAccess().getArgumentsAssignment_0());
			}
		)
	)
	(
		CommaKeyword_1_0=Comma
		 {
			announce($CommaKeyword_1_0, grammarAccess.getArgumentsAccess().getCommaKeyword_1_0());
		}
		(
			(
				ArgumentsArgumentParserRuleCall_1_1_0=norm1_Argument{
					announce($ArgumentsArgumentParserRuleCall_1_1_0.start, $ArgumentsArgumentParserRuleCall_1_1_0.stop, grammarAccess.getArgumentsAccess().getArgumentsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleArgument
entryRuleArgument
	:
	ruleArgument
	EOF;

// Rule Argument
ruleArgument
@init {
}:
(
	(
		(
			SpreadFullStopFullStopFullStopKeyword_0_0=FullStopFullStopFullStop
			 {
				announce($SpreadFullStopFullStopFullStopKeyword_0_0, grammarAccess.getArgumentAccess().getSpreadFullStopFullStopFullStopKeyword_0_0());
			}
		)
	)?
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm1_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getArgumentAccess().getExpressionAssignment_1());
			}
		)
	)
)
;


// Rule Argument
norm1_Argument
@init {
}:
(
	(
		(
			SpreadFullStopFullStopFullStopKeyword_0_0=FullStopFullStopFullStop
			 {
				announce($SpreadFullStopFullStopFullStopKeyword_0_0, grammarAccess.getArgumentAccess().getSpreadFullStopFullStopFullStopKeyword_0_0());
			}
		)
	)?
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm3_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getArgumentAccess().getExpressionAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleMemberExpression
entryRuleMemberExpression
	:
	ruleMemberExpression
	EOF;

// Rule MemberExpression
ruleMemberExpression
@init {
}:
(
	(
		(
			((
				New
				FullStop
			)
			)=>
			(
				NewKeyword_0_0_0_1=New
				 {
					announce($NewKeyword_0_0_0_1, grammarAccess.getMemberExpressionAccess().getNewKeyword_0_0_0_1());
				}
				FullStopKeyword_0_0_0_2=FullStop
				 {
					announce($FullStopKeyword_0_0_0_2, grammarAccess.getMemberExpressionAccess().getFullStopKeyword_0_0_0_2());
				}
			)
		)
		TargetKeyword_0_1=Target
		 {
			announce($TargetKeyword_0_1, grammarAccess.getMemberExpressionAccess().getTargetKeyword_0_1());
		}
	)
	    |
	(
		(
			((
				New
			)
			)=>
			(
				NewKeyword_1_0_0_1=New
				 {
					announce($NewKeyword_1_0_0_1, grammarAccess.getMemberExpressionAccess().getNewKeyword_1_0_0_1());
				}
			)
		)
		(
			(
				CalleeMemberExpressionParserRuleCall_1_1_0=ruleMemberExpression{
					announce($CalleeMemberExpressionParserRuleCall_1_1_0.start, $CalleeMemberExpressionParserRuleCall_1_1_0.stop, grammarAccess.getMemberExpressionAccess().getCalleeAssignment_1_1());
				}
			)
		)
		(
			(LessThanSign
			)=>
			ConcreteTypeArgumentsParserRuleCall_1_2=ruleConcreteTypeArguments{ announce($ConcreteTypeArgumentsParserRuleCall_1_2.start, $ConcreteTypeArgumentsParserRuleCall_1_2.stop, grammarAccess.getMemberExpressionAccess().getConcreteTypeArgumentsParserRuleCall_1_2()); }
		)?
		(
			(
				((
					LeftParenthesis
				)
				)=>
				(
					WithArgsLeftParenthesisKeyword_1_3_0_0=LeftParenthesis
					 {
						announce($WithArgsLeftParenthesisKeyword_1_3_0_0, grammarAccess.getMemberExpressionAccess().getWithArgsLeftParenthesisKeyword_1_3_0_0());
					}
				)
			)
			(
				ArgumentsParserRuleCall_1_3_1=ruleArguments{ announce($ArgumentsParserRuleCall_1_3_1.start, $ArgumentsParserRuleCall_1_3_1.stop, grammarAccess.getMemberExpressionAccess().getArgumentsParserRuleCall_1_3_1()); }
			)?
			RightParenthesisKeyword_1_3_2=RightParenthesis
			 {
				announce($RightParenthesisKeyword_1_3_2, grammarAccess.getMemberExpressionAccess().getRightParenthesisKeyword_1_3_2());
			}
			(
				(
					IndexedAccessExpressionTailParserRuleCall_1_3_3_0_1=ruleIndexedAccessExpressionTail{ announce($IndexedAccessExpressionTailParserRuleCall_1_3_3_0_1.start, $IndexedAccessExpressionTailParserRuleCall_1_3_3_0_1.stop, grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_1_3_3_0_1()); }
				)
				    |
				(
					ParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1=ruleParameterizedPropertyAccessExpressionTail{ announce($ParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1.start, $ParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1.stop, grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1()); }
				)
				    |
				(
					(
						(
							TemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0=ruleTemplateLiteral{
								announce($TemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0.start, $TemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0.stop, grammarAccess.getMemberExpressionAccess().getTemplateAssignment_1_3_3_2_1());
							}
						)
					)
				)
			)*
		)?
	)
	    |
	(
		PrimaryExpressionParserRuleCall_2_0=rulePrimaryExpression{ announce($PrimaryExpressionParserRuleCall_2_0.start, $PrimaryExpressionParserRuleCall_2_0.stop, grammarAccess.getMemberExpressionAccess().getPrimaryExpressionParserRuleCall_2_0()); }
		(
			(
				IndexedAccessExpressionTailParserRuleCall_2_1_0_1=ruleIndexedAccessExpressionTail{ announce($IndexedAccessExpressionTailParserRuleCall_2_1_0_1.start, $IndexedAccessExpressionTailParserRuleCall_2_1_0_1.stop, grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_2_1_0_1()); }
			)
			    |
			(
				ParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1=ruleParameterizedPropertyAccessExpressionTail{ announce($ParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1.start, $ParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1.stop, grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1()); }
			)
			    |
			(
				(
					(
						TemplateTemplateLiteralParserRuleCall_2_1_2_1_0=ruleTemplateLiteral{
							announce($TemplateTemplateLiteralParserRuleCall_2_1_2_1_0.start, $TemplateTemplateLiteralParserRuleCall_2_1_2_1_0.stop, grammarAccess.getMemberExpressionAccess().getTemplateAssignment_2_1_2_1());
						}
					)
				)
			)
		)*
	)
)
;


// Rule MemberExpression
norm1_MemberExpression
@init {
}:
(
	(
		(
			((
				New
				FullStop
			)
			)=>
			(
				NewKeyword_0_0_0_1=New
				 {
					announce($NewKeyword_0_0_0_1, grammarAccess.getMemberExpressionAccess().getNewKeyword_0_0_0_1());
				}
				FullStopKeyword_0_0_0_2=FullStop
				 {
					announce($FullStopKeyword_0_0_0_2, grammarAccess.getMemberExpressionAccess().getFullStopKeyword_0_0_0_2());
				}
			)
		)
		TargetKeyword_0_1=Target
		 {
			announce($TargetKeyword_0_1, grammarAccess.getMemberExpressionAccess().getTargetKeyword_0_1());
		}
	)
	    |
	(
		(
			((
				New
			)
			)=>
			(
				NewKeyword_1_0_0_1=New
				 {
					announce($NewKeyword_1_0_0_1, grammarAccess.getMemberExpressionAccess().getNewKeyword_1_0_0_1());
				}
			)
		)
		(
			(
				CalleeMemberExpressionParserRuleCall_1_1_0=norm1_MemberExpression{
					announce($CalleeMemberExpressionParserRuleCall_1_1_0.start, $CalleeMemberExpressionParserRuleCall_1_1_0.stop, grammarAccess.getMemberExpressionAccess().getCalleeAssignment_1_1());
				}
			)
		)
		(
			(LessThanSign
			)=>
			ConcreteTypeArgumentsParserRuleCall_1_2=ruleConcreteTypeArguments{ announce($ConcreteTypeArgumentsParserRuleCall_1_2.start, $ConcreteTypeArgumentsParserRuleCall_1_2.stop, grammarAccess.getMemberExpressionAccess().getConcreteTypeArgumentsParserRuleCall_1_2()); }
		)?
		(
			(
				((
					LeftParenthesis
				)
				)=>
				(
					WithArgsLeftParenthesisKeyword_1_3_0_0=LeftParenthesis
					 {
						announce($WithArgsLeftParenthesisKeyword_1_3_0_0, grammarAccess.getMemberExpressionAccess().getWithArgsLeftParenthesisKeyword_1_3_0_0());
					}
				)
			)
			(
				ArgumentsParserRuleCall_1_3_1=norm1_Arguments{ announce($ArgumentsParserRuleCall_1_3_1.start, $ArgumentsParserRuleCall_1_3_1.stop, grammarAccess.getMemberExpressionAccess().getArgumentsParserRuleCall_1_3_1()); }
			)?
			RightParenthesisKeyword_1_3_2=RightParenthesis
			 {
				announce($RightParenthesisKeyword_1_3_2, grammarAccess.getMemberExpressionAccess().getRightParenthesisKeyword_1_3_2());
			}
			(
				(
					IndexedAccessExpressionTailParserRuleCall_1_3_3_0_1=norm1_IndexedAccessExpressionTail{ announce($IndexedAccessExpressionTailParserRuleCall_1_3_3_0_1.start, $IndexedAccessExpressionTailParserRuleCall_1_3_3_0_1.stop, grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_1_3_3_0_1()); }
				)
				    |
				(
					ParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1=norm1_ParameterizedPropertyAccessExpressionTail{ announce($ParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1.start, $ParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1.stop, grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1()); }
				)
				    |
				(
					(
						(
							TemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0=norm1_TemplateLiteral{
								announce($TemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0.start, $TemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0.stop, grammarAccess.getMemberExpressionAccess().getTemplateAssignment_1_3_3_2_1());
							}
						)
					)
				)
			)*
		)?
	)
	    |
	(
		PrimaryExpressionParserRuleCall_2_0=norm1_PrimaryExpression{ announce($PrimaryExpressionParserRuleCall_2_0.start, $PrimaryExpressionParserRuleCall_2_0.stop, grammarAccess.getMemberExpressionAccess().getPrimaryExpressionParserRuleCall_2_0()); }
		(
			(
				IndexedAccessExpressionTailParserRuleCall_2_1_0_1=norm1_IndexedAccessExpressionTail{ announce($IndexedAccessExpressionTailParserRuleCall_2_1_0_1.start, $IndexedAccessExpressionTailParserRuleCall_2_1_0_1.stop, grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_2_1_0_1()); }
			)
			    |
			(
				ParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1=norm1_ParameterizedPropertyAccessExpressionTail{ announce($ParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1.start, $ParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1.stop, grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1()); }
			)
			    |
			(
				(
					(
						TemplateTemplateLiteralParserRuleCall_2_1_2_1_0=norm1_TemplateLiteral{
							announce($TemplateTemplateLiteralParserRuleCall_2_1_2_1_0.start, $TemplateTemplateLiteralParserRuleCall_2_1_2_1_0.stop, grammarAccess.getMemberExpressionAccess().getTemplateAssignment_2_1_2_1());
						}
					)
				)
			)
		)*
	)
)
;


// Rule IndexedAccessExpressionTail
ruleIndexedAccessExpressionTail
@init {
}:
(
	LeftSquareBracketKeyword_0=LeftSquareBracket
	 {
		announce($LeftSquareBracketKeyword_0, grammarAccess.getIndexedAccessExpressionTailAccess().getLeftSquareBracketKeyword_0());
	}
	(
		(
			IndexExpressionParserRuleCall_1_0=norm1_Expression{
				announce($IndexExpressionParserRuleCall_1_0.start, $IndexExpressionParserRuleCall_1_0.stop, grammarAccess.getIndexedAccessExpressionTailAccess().getIndexAssignment_1());
			}
		)
	)
	RightSquareBracketKeyword_2=RightSquareBracket
	 {
		announce($RightSquareBracketKeyword_2, grammarAccess.getIndexedAccessExpressionTailAccess().getRightSquareBracketKeyword_2());
	}
)
;


// Rule IndexedAccessExpressionTail
norm1_IndexedAccessExpressionTail
@init {
}:
(
	LeftSquareBracketKeyword_0=LeftSquareBracket
	 {
		announce($LeftSquareBracketKeyword_0, grammarAccess.getIndexedAccessExpressionTailAccess().getLeftSquareBracketKeyword_0());
	}
	(
		(
			IndexExpressionParserRuleCall_1_0=norm3_Expression{
				announce($IndexExpressionParserRuleCall_1_0.start, $IndexExpressionParserRuleCall_1_0.stop, grammarAccess.getIndexedAccessExpressionTailAccess().getIndexAssignment_1());
			}
		)
	)
	RightSquareBracketKeyword_2=RightSquareBracket
	 {
		announce($RightSquareBracketKeyword_2, grammarAccess.getIndexedAccessExpressionTailAccess().getRightSquareBracketKeyword_2());
	}
)
;


// Rule ParameterizedPropertyAccessExpressionTail
ruleParameterizedPropertyAccessExpressionTail
@init {
}:
(
	FullStopKeyword_0=FullStop
	 {
		announce($FullStopKeyword_0, grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getFullStopKeyword_0());
	}
	(
		ConcreteTypeArgumentsParserRuleCall_1=ruleConcreteTypeArguments{ announce($ConcreteTypeArgumentsParserRuleCall_1.start, $ConcreteTypeArgumentsParserRuleCall_1.stop, grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getConcreteTypeArgumentsParserRuleCall_1()); }
	)?
	(
		(
			PropertyIdentifiableElementIdentifierNameParserRuleCall_2_0_1=ruleIdentifierName{
				announce($PropertyIdentifiableElementIdentifierNameParserRuleCall_2_0_1.start, $PropertyIdentifiableElementIdentifierNameParserRuleCall_2_0_1.stop, grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getPropertyAssignment_2());
			}
		)
	)
)
;


// Rule ParameterizedPropertyAccessExpressionTail
norm1_ParameterizedPropertyAccessExpressionTail
@init {
}:
(
	FullStopKeyword_0=FullStop
	 {
		announce($FullStopKeyword_0, grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getFullStopKeyword_0());
	}
	(
		ConcreteTypeArgumentsParserRuleCall_1=ruleConcreteTypeArguments{ announce($ConcreteTypeArgumentsParserRuleCall_1.start, $ConcreteTypeArgumentsParserRuleCall_1.stop, grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getConcreteTypeArgumentsParserRuleCall_1()); }
	)?
	(
		(
			PropertyIdentifiableElementIdentifierNameParserRuleCall_2_0_1=ruleIdentifierName{
				announce($PropertyIdentifiableElementIdentifierNameParserRuleCall_2_0_1.start, $PropertyIdentifiableElementIdentifierNameParserRuleCall_2_0_1.stop, grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getPropertyAssignment_2());
			}
		)
	)
)
;

// Entry rule entryRulePostfixExpression
entryRulePostfixExpression
	:
	rulePostfixExpression
	EOF;

// Rule PostfixExpression
rulePostfixExpression
@init {
}:
(
	LeftHandSideExpressionParserRuleCall_0=ruleLeftHandSideExpression
	{
		if (input.LA(1) == PlusSignPlusSign || input.LA(1) == HyphenMinusHyphenMinus) {
			promoteEOL();
		}
		announce($LeftHandSideExpressionParserRuleCall_0.start, $LeftHandSideExpressionParserRuleCall_0.stop, grammarAccess.getPostfixExpressionAccess().getLeftHandSideExpressionParserRuleCall_0());
	}
	(
		((
			(
				(
					rulePostfixOperator
				)
			)
		)
		)=>
		(
			(
				(
					rulePostfixOperator
				)
			)
		)
	)?
)
;


// Rule PostfixExpression
norm1_PostfixExpression
@init {
}:
(
	LeftHandSideExpressionParserRuleCall_0=norm1_LeftHandSideExpression
	{
		if (input.LA(1) == PlusSignPlusSign || input.LA(1) == HyphenMinusHyphenMinus) {
			promoteEOL();
		}
		announce($LeftHandSideExpressionParserRuleCall_0.start, $LeftHandSideExpressionParserRuleCall_0.stop, grammarAccess.getPostfixExpressionAccess().getLeftHandSideExpressionParserRuleCall_0());
	}
	(
		((
			(
				(
					rulePostfixOperator
				)
			)
		)
		)=>
		(
			(
				(
					rulePostfixOperator
				)
			)
		)
	)?
)
;

// Entry rule entryRuleCastExpression
entryRuleCastExpression
	:
	ruleCastExpression
	EOF;

// Rule CastExpression
ruleCastExpression
@init {
}:
(
	PostfixExpressionParserRuleCall_0=rulePostfixExpression{ announce($PostfixExpressionParserRuleCall_0.start, $PostfixExpressionParserRuleCall_0.stop, grammarAccess.getCastExpressionAccess().getPostfixExpressionParserRuleCall_0()); }
	(
		(
			((
				As
			)
			)=>
			(
				AsKeyword_1_0_0_1=As
				 {
					announce($AsKeyword_1_0_0_1, grammarAccess.getCastExpressionAccess().getAsKeyword_1_0_0_1());
				}
			)
		)
		(
			(
				TargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0=ruleArrayTypeExpression{
					announce($TargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0.start, $TargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0.stop, grammarAccess.getCastExpressionAccess().getTargetTypeRefAssignment_1_1());
				}
			)
		)
	)?
)
;


// Rule CastExpression
norm1_CastExpression
@init {
}:
(
	PostfixExpressionParserRuleCall_0=norm1_PostfixExpression{ announce($PostfixExpressionParserRuleCall_0.start, $PostfixExpressionParserRuleCall_0.stop, grammarAccess.getCastExpressionAccess().getPostfixExpressionParserRuleCall_0()); }
	(
		(
			((
				As
			)
			)=>
			(
				AsKeyword_1_0_0_1=As
				 {
					announce($AsKeyword_1_0_0_1, grammarAccess.getCastExpressionAccess().getAsKeyword_1_0_0_1());
				}
			)
		)
		(
			(
				TargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0=ruleArrayTypeExpression{
					announce($TargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0.start, $TargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0.stop, grammarAccess.getCastExpressionAccess().getTargetTypeRefAssignment_1_1());
				}
			)
		)
	)?
)
;

// Entry rule entryRuleUnaryExpression
entryRuleUnaryExpression
	:
	ruleUnaryExpression
	EOF;

// Rule UnaryExpression
ruleUnaryExpression
@init {
}:
(
	CastExpressionParserRuleCall_0=ruleCastExpression{ announce($CastExpressionParserRuleCall_0.start, $CastExpressionParserRuleCall_0.stop, grammarAccess.getUnaryExpressionAccess().getCastExpressionParserRuleCall_0()); }
	    |
	(
		(
			(
				ruleUnaryOperator
			)
		)
		(
			(
				ExpressionUnaryExpressionParserRuleCall_1_2_0=ruleUnaryExpression{
					announce($ExpressionUnaryExpressionParserRuleCall_1_2_0.start, $ExpressionUnaryExpressionParserRuleCall_1_2_0.stop, grammarAccess.getUnaryExpressionAccess().getExpressionAssignment_1_2());
				}
			)
		)
	)
)
;


// Rule UnaryExpression
norm1_UnaryExpression
@init {
}:
(
	CastExpressionParserRuleCall_0=norm1_CastExpression{ announce($CastExpressionParserRuleCall_0.start, $CastExpressionParserRuleCall_0.stop, grammarAccess.getUnaryExpressionAccess().getCastExpressionParserRuleCall_0()); }
	    |
	(
		(
			(
				ruleUnaryOperator
			)
		)
		(
			(
				ExpressionUnaryExpressionParserRuleCall_1_2_0=norm1_UnaryExpression{
					announce($ExpressionUnaryExpressionParserRuleCall_1_2_0.start, $ExpressionUnaryExpressionParserRuleCall_1_2_0.stop, grammarAccess.getUnaryExpressionAccess().getExpressionAssignment_1_2());
				}
			)
		)
	)
)
;

// Entry rule entryRuleMultiplicativeExpression
entryRuleMultiplicativeExpression
	:
	ruleMultiplicativeExpression
	EOF;

// Rule MultiplicativeExpression
ruleMultiplicativeExpression
@init {
}:
(
	UnaryExpressionParserRuleCall_0=ruleUnaryExpression{ announce($UnaryExpressionParserRuleCall_0.start, $UnaryExpressionParserRuleCall_0.stop, grammarAccess.getMultiplicativeExpressionAccess().getUnaryExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleMultiplicativeOperator
					)
				)
			)
			)=>
			(
				(
					(
						ruleMultiplicativeOperator
					)
				)
			)
		)
		(
			(
				RhsUnaryExpressionParserRuleCall_1_1_0=ruleUnaryExpression{
					announce($RhsUnaryExpressionParserRuleCall_1_1_0.start, $RhsUnaryExpressionParserRuleCall_1_1_0.stop, grammarAccess.getMultiplicativeExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule MultiplicativeExpression
norm1_MultiplicativeExpression
@init {
}:
(
	UnaryExpressionParserRuleCall_0=norm1_UnaryExpression{ announce($UnaryExpressionParserRuleCall_0.start, $UnaryExpressionParserRuleCall_0.stop, grammarAccess.getMultiplicativeExpressionAccess().getUnaryExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleMultiplicativeOperator
					)
				)
			)
			)=>
			(
				(
					(
						ruleMultiplicativeOperator
					)
				)
			)
		)
		(
			(
				RhsUnaryExpressionParserRuleCall_1_1_0=norm1_UnaryExpression{
					announce($RhsUnaryExpressionParserRuleCall_1_1_0.start, $RhsUnaryExpressionParserRuleCall_1_1_0.stop, grammarAccess.getMultiplicativeExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleAdditiveExpression
entryRuleAdditiveExpression
	:
	ruleAdditiveExpression
	EOF;

// Rule AdditiveExpression
ruleAdditiveExpression
@init {
}:
(
	MultiplicativeExpressionParserRuleCall_0=ruleMultiplicativeExpression{ announce($MultiplicativeExpressionParserRuleCall_0.start, $MultiplicativeExpressionParserRuleCall_0.stop, grammarAccess.getAdditiveExpressionAccess().getMultiplicativeExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleAdditiveOperator
					)
				)
			)
			)=>
			(
				(
					(
						ruleAdditiveOperator
					)
				)
			)
		)
		(
			(
				RhsMultiplicativeExpressionParserRuleCall_1_1_0=ruleMultiplicativeExpression{
					announce($RhsMultiplicativeExpressionParserRuleCall_1_1_0.start, $RhsMultiplicativeExpressionParserRuleCall_1_1_0.stop, grammarAccess.getAdditiveExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule AdditiveExpression
norm1_AdditiveExpression
@init {
}:
(
	MultiplicativeExpressionParserRuleCall_0=norm1_MultiplicativeExpression{ announce($MultiplicativeExpressionParserRuleCall_0.start, $MultiplicativeExpressionParserRuleCall_0.stop, grammarAccess.getAdditiveExpressionAccess().getMultiplicativeExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleAdditiveOperator
					)
				)
			)
			)=>
			(
				(
					(
						ruleAdditiveOperator
					)
				)
			)
		)
		(
			(
				RhsMultiplicativeExpressionParserRuleCall_1_1_0=norm1_MultiplicativeExpression{
					announce($RhsMultiplicativeExpressionParserRuleCall_1_1_0.start, $RhsMultiplicativeExpressionParserRuleCall_1_1_0.stop, grammarAccess.getAdditiveExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleShiftExpression
entryRuleShiftExpression
	:
	ruleShiftExpression
	EOF;

// Rule ShiftExpression
ruleShiftExpression
@init {
}:
(
	AdditiveExpressionParserRuleCall_0=ruleAdditiveExpression{ announce($AdditiveExpressionParserRuleCall_0.start, $AdditiveExpressionParserRuleCall_0.stop, grammarAccess.getShiftExpressionAccess().getAdditiveExpressionParserRuleCall_0()); }
	(
		((
			(
				(
					ruleShiftOperator
				)
			)
			(
				(
					ruleAdditiveExpression
				)
			)
		)
		)=>
		(
			(
				(
					OpShiftOperatorParserRuleCall_1_0_1_0=ruleShiftOperator{
						announce($OpShiftOperatorParserRuleCall_1_0_1_0.start, $OpShiftOperatorParserRuleCall_1_0_1_0.stop, grammarAccess.getShiftExpressionAccess().getOpAssignment_1_0_1());
					}
				)
			)
			(
				(
					RhsAdditiveExpressionParserRuleCall_1_0_2_0=ruleAdditiveExpression{
						announce($RhsAdditiveExpressionParserRuleCall_1_0_2_0.start, $RhsAdditiveExpressionParserRuleCall_1_0_2_0.stop, grammarAccess.getShiftExpressionAccess().getRhsAssignment_1_0_2());
					}
				)
			)
		)
	)*
)
;


// Rule ShiftExpression
norm1_ShiftExpression
@init {
}:
(
	AdditiveExpressionParserRuleCall_0=norm1_AdditiveExpression{ announce($AdditiveExpressionParserRuleCall_0.start, $AdditiveExpressionParserRuleCall_0.stop, grammarAccess.getShiftExpressionAccess().getAdditiveExpressionParserRuleCall_0()); }
	(
		((
			(
				(
					ruleShiftOperator
				)
			)
			(
				(
					norm1_AdditiveExpression
				)
			)
		)
		)=>
		(
			(
				(
					OpShiftOperatorParserRuleCall_1_0_1_0=ruleShiftOperator{
						announce($OpShiftOperatorParserRuleCall_1_0_1_0.start, $OpShiftOperatorParserRuleCall_1_0_1_0.stop, grammarAccess.getShiftExpressionAccess().getOpAssignment_1_0_1());
					}
				)
			)
			(
				(
					RhsAdditiveExpressionParserRuleCall_1_0_2_0=norm1_AdditiveExpression{
						announce($RhsAdditiveExpressionParserRuleCall_1_0_2_0.start, $RhsAdditiveExpressionParserRuleCall_1_0_2_0.stop, grammarAccess.getShiftExpressionAccess().getRhsAssignment_1_0_2());
					}
				)
			)
		)
	)*
)
;

// Entry rule entryRuleShiftOperator
entryRuleShiftOperator
	:
	ruleShiftOperator
	EOF;

// Rule ShiftOperator
ruleShiftOperator
@init {
}
:
(
	(
		GreaterThanSignKeyword_0_0=GreaterThanSign {
			announce($GreaterThanSignKeyword_0_0, grammarAccess.getShiftOperatorAccess().getGreaterThanSignKeyword_0_0());
		}
		GreaterThanSignKeyword_0_1=GreaterThanSign {
			announce($GreaterThanSignKeyword_0_1, grammarAccess.getShiftOperatorAccess().getGreaterThanSignKeyword_0_1());
		}
		(
			GreaterThanSignKeyword_0_2=GreaterThanSign {
				announce($GreaterThanSignKeyword_0_2, grammarAccess.getShiftOperatorAccess().getGreaterThanSignKeyword_0_2());
			}
		)?
	)
	    |
	LessThanSignLessThanSignKeyword_1=LessThanSignLessThanSign {
		announce($LessThanSignLessThanSignKeyword_1, grammarAccess.getShiftOperatorAccess().getLessThanSignLessThanSignKeyword_1());
	}
)
;

// Entry rule entryRuleRelationalExpression
entryRuleRelationalExpression
	:
	ruleRelationalExpression
	EOF;

// Rule RelationalExpression
ruleRelationalExpression
@init {
}:
(
	ShiftExpressionParserRuleCall_0=ruleShiftExpression{ announce($ShiftExpressionParserRuleCall_0.start, $ShiftExpressionParserRuleCall_0.stop, grammarAccess.getRelationalExpressionAccess().getShiftExpressionParserRuleCall_0()); }
	(
		((
			(
				(
					ruleRelationalOperator
				)
			)
			(
				New
				    |
				This_1
				    |
				Super
				    |
				Yield
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LessThanSign
				    |
				Import
				    |
				True
				    |
				False
				    |
				Null
				    |
				Solidus
				    |
				SolidusEqualsSign
				    |
				LeftSquareBracket
				    |
				LeftCurlyBracket
				    |
				LeftParenthesis
				    |
				CommercialAt
				    |
				Function
				    |
				Class
				    |
				Delete
				    |
				Void
				    |
				Typeof
				    |
				PlusSignPlusSign
				    |
				HyphenMinusHyphenMinus
				    |
				PlusSign
				    |
				HyphenMinus
				    |
				Tilde
				    |
				ExclamationMark
				    |
				RULE_IDENTIFIER
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_BINARY_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_LEGACY_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
				    |
				RULE_STRING
				    |
				RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
				    |
				RULE_TEMPLATE_HEAD
			)
		)
		)=>
		(
			(
				(
					OpRelationalOperatorParserRuleCall_1_0_1_0=ruleRelationalOperator{
						announce($OpRelationalOperatorParserRuleCall_1_0_1_0.start, $OpRelationalOperatorParserRuleCall_1_0_1_0.stop, grammarAccess.getRelationalExpressionAccess().getOpAssignment_1_0_1());
					}
				)
			)
			(
				(New | 
				This_1 | 
				Super | 
				Yield | 
				Get | 
				Set | 
				Let | 
				Project | 
				External | 
				Abstract | 
				Static | 
				As | 
				From | 
				Constructor | 
				Of | 
				Target | 
				Type | 
				Union | 
				Intersection | 
				This | 
				Promisify | 
				Await | 
				Async | 
				Implements | 
				Interface | 
				Private | 
				Protected | 
				Public | 
				Out | 
				LessThanSign | 
				Import | 
				True | 
				False | 
				Null | 
				Solidus | 
				SolidusEqualsSign | 
				LeftSquareBracket | 
				LeftCurlyBracket | 
				LeftParenthesis | 
				CommercialAt | 
				Function | 
				Class | 
				Delete | 
				Void | 
				Typeof | 
				PlusSignPlusSign | 
				HyphenMinusHyphenMinus | 
				PlusSign | 
				HyphenMinus | 
				Tilde | 
				ExclamationMark | 
				RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
				(
					RhsShiftExpressionParserRuleCall_1_0_2_0=ruleShiftExpression{
						announce($RhsShiftExpressionParserRuleCall_1_0_2_0.start, $RhsShiftExpressionParserRuleCall_1_0_2_0.stop, grammarAccess.getRelationalExpressionAccess().getRhsAssignment_1_0_2());
					}
				)
			)
		)
	)*
)
;


// Rule RelationalExpression
norm1_RelationalExpression
@init {
}:
(
	ShiftExpressionParserRuleCall_0=ruleShiftExpression{ announce($ShiftExpressionParserRuleCall_0.start, $ShiftExpressionParserRuleCall_0.stop, grammarAccess.getRelationalExpressionAccess().getShiftExpressionParserRuleCall_0()); }
	(
		((
			(
				(
					norm1_RelationalOperator
				)
			)
			(
				New
				    |
				This_1
				    |
				Super
				    |
				Yield
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LessThanSign
				    |
				Import
				    |
				True
				    |
				False
				    |
				Null
				    |
				Solidus
				    |
				SolidusEqualsSign
				    |
				LeftSquareBracket
				    |
				LeftCurlyBracket
				    |
				LeftParenthesis
				    |
				CommercialAt
				    |
				Function
				    |
				Class
				    |
				Delete
				    |
				Void
				    |
				Typeof
				    |
				PlusSignPlusSign
				    |
				HyphenMinusHyphenMinus
				    |
				PlusSign
				    |
				HyphenMinus
				    |
				Tilde
				    |
				ExclamationMark
				    |
				RULE_IDENTIFIER
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_BINARY_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_LEGACY_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
				    |
				RULE_STRING
				    |
				RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
				    |
				RULE_TEMPLATE_HEAD
			)
		)
		)=>
		(
			(
				(
					OpRelationalOperatorParserRuleCall_1_0_1_0=norm1_RelationalOperator{
						announce($OpRelationalOperatorParserRuleCall_1_0_1_0.start, $OpRelationalOperatorParserRuleCall_1_0_1_0.stop, grammarAccess.getRelationalExpressionAccess().getOpAssignment_1_0_1());
					}
				)
			)
			(
				(New | 
				This_1 | 
				Super | 
				Yield | 
				Get | 
				Set | 
				Let | 
				Project | 
				External | 
				Abstract | 
				Static | 
				As | 
				From | 
				Constructor | 
				Of | 
				Target | 
				Type | 
				Union | 
				Intersection | 
				This | 
				Promisify | 
				Await | 
				Async | 
				Implements | 
				Interface | 
				Private | 
				Protected | 
				Public | 
				Out | 
				LessThanSign | 
				Import | 
				True | 
				False | 
				Null | 
				Solidus | 
				SolidusEqualsSign | 
				LeftSquareBracket | 
				LeftCurlyBracket | 
				LeftParenthesis | 
				CommercialAt | 
				Function | 
				Class | 
				Delete | 
				Void | 
				Typeof | 
				PlusSignPlusSign | 
				HyphenMinusHyphenMinus | 
				PlusSign | 
				HyphenMinus | 
				Tilde | 
				ExclamationMark | 
				RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
				(
					RhsShiftExpressionParserRuleCall_1_0_2_0=ruleShiftExpression{
						announce($RhsShiftExpressionParserRuleCall_1_0_2_0.start, $RhsShiftExpressionParserRuleCall_1_0_2_0.stop, grammarAccess.getRelationalExpressionAccess().getRhsAssignment_1_0_2());
					}
				)
			)
		)
	)*
)
;


// Rule RelationalExpression
norm2_RelationalExpression
@init {
}:
(
	ShiftExpressionParserRuleCall_0=norm1_ShiftExpression{ announce($ShiftExpressionParserRuleCall_0.start, $ShiftExpressionParserRuleCall_0.stop, grammarAccess.getRelationalExpressionAccess().getShiftExpressionParserRuleCall_0()); }
	(
		((
			(
				(
					ruleRelationalOperator
				)
			)
			(
				New
				    |
				This_1
				    |
				Super
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LessThanSign
				    |
				Import
				    |
				True
				    |
				False
				    |
				Null
				    |
				Solidus
				    |
				SolidusEqualsSign
				    |
				LeftSquareBracket
				    |
				LeftCurlyBracket
				    |
				LeftParenthesis
				    |
				CommercialAt
				    |
				Function
				    |
				Class
				    |
				Delete
				    |
				Void
				    |
				Typeof
				    |
				PlusSignPlusSign
				    |
				HyphenMinusHyphenMinus
				    |
				PlusSign
				    |
				HyphenMinus
				    |
				Tilde
				    |
				ExclamationMark
				    |
				RULE_IDENTIFIER
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_BINARY_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_LEGACY_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
				    |
				RULE_STRING
				    |
				RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
				    |
				RULE_TEMPLATE_HEAD
			)
		)
		)=>
		(
			(
				(
					OpRelationalOperatorParserRuleCall_1_0_1_0=ruleRelationalOperator{
						announce($OpRelationalOperatorParserRuleCall_1_0_1_0.start, $OpRelationalOperatorParserRuleCall_1_0_1_0.stop, grammarAccess.getRelationalExpressionAccess().getOpAssignment_1_0_1());
					}
				)
			)
			(
				(New | 
				This_1 | 
				Super | 
				Get | 
				Set | 
				Let | 
				Project | 
				External | 
				Abstract | 
				Static | 
				As | 
				From | 
				Constructor | 
				Of | 
				Target | 
				Type | 
				Union | 
				Intersection | 
				This | 
				Promisify | 
				Await | 
				Async | 
				Implements | 
				Interface | 
				Private | 
				Protected | 
				Public | 
				Out | 
				LessThanSign | 
				Import | 
				True | 
				False | 
				Null | 
				Solidus | 
				SolidusEqualsSign | 
				LeftSquareBracket | 
				LeftCurlyBracket | 
				LeftParenthesis | 
				CommercialAt | 
				Function | 
				Class | 
				Delete | 
				Void | 
				Typeof | 
				PlusSignPlusSign | 
				HyphenMinusHyphenMinus | 
				PlusSign | 
				HyphenMinus | 
				Tilde | 
				ExclamationMark | 
				RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
				(
					RhsShiftExpressionParserRuleCall_1_0_2_0=norm1_ShiftExpression{
						announce($RhsShiftExpressionParserRuleCall_1_0_2_0.start, $RhsShiftExpressionParserRuleCall_1_0_2_0.stop, grammarAccess.getRelationalExpressionAccess().getRhsAssignment_1_0_2());
					}
				)
			)
		)
	)*
)
;


// Rule RelationalExpression
norm3_RelationalExpression
@init {
}:
(
	ShiftExpressionParserRuleCall_0=norm1_ShiftExpression{ announce($ShiftExpressionParserRuleCall_0.start, $ShiftExpressionParserRuleCall_0.stop, grammarAccess.getRelationalExpressionAccess().getShiftExpressionParserRuleCall_0()); }
	(
		((
			(
				(
					norm1_RelationalOperator
				)
			)
			(
				New
				    |
				This_1
				    |
				Super
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LessThanSign
				    |
				Import
				    |
				True
				    |
				False
				    |
				Null
				    |
				Solidus
				    |
				SolidusEqualsSign
				    |
				LeftSquareBracket
				    |
				LeftCurlyBracket
				    |
				LeftParenthesis
				    |
				CommercialAt
				    |
				Function
				    |
				Class
				    |
				Delete
				    |
				Void
				    |
				Typeof
				    |
				PlusSignPlusSign
				    |
				HyphenMinusHyphenMinus
				    |
				PlusSign
				    |
				HyphenMinus
				    |
				Tilde
				    |
				ExclamationMark
				    |
				RULE_IDENTIFIER
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_BINARY_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_LEGACY_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
				    |
				RULE_STRING
				    |
				RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
				    |
				RULE_TEMPLATE_HEAD
			)
		)
		)=>
		(
			(
				(
					OpRelationalOperatorParserRuleCall_1_0_1_0=norm1_RelationalOperator{
						announce($OpRelationalOperatorParserRuleCall_1_0_1_0.start, $OpRelationalOperatorParserRuleCall_1_0_1_0.stop, grammarAccess.getRelationalExpressionAccess().getOpAssignment_1_0_1());
					}
				)
			)
			(
				(New | 
				This_1 | 
				Super | 
				Get | 
				Set | 
				Let | 
				Project | 
				External | 
				Abstract | 
				Static | 
				As | 
				From | 
				Constructor | 
				Of | 
				Target | 
				Type | 
				Union | 
				Intersection | 
				This | 
				Promisify | 
				Await | 
				Async | 
				Implements | 
				Interface | 
				Private | 
				Protected | 
				Public | 
				Out | 
				LessThanSign | 
				Import | 
				True | 
				False | 
				Null | 
				Solidus | 
				SolidusEqualsSign | 
				LeftSquareBracket | 
				LeftCurlyBracket | 
				LeftParenthesis | 
				CommercialAt | 
				Function | 
				Class | 
				Delete | 
				Void | 
				Typeof | 
				PlusSignPlusSign | 
				HyphenMinusHyphenMinus | 
				PlusSign | 
				HyphenMinus | 
				Tilde | 
				ExclamationMark | 
				RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
				(
					RhsShiftExpressionParserRuleCall_1_0_2_0=norm1_ShiftExpression{
						announce($RhsShiftExpressionParserRuleCall_1_0_2_0.start, $RhsShiftExpressionParserRuleCall_1_0_2_0.stop, grammarAccess.getRelationalExpressionAccess().getRhsAssignment_1_0_2());
					}
				)
			)
		)
	)*
)
;

// Entry rule entryRuleRelationalOperator
entryRuleRelationalOperator
	:
	ruleRelationalOperator
	EOF;

// Rule RelationalOperator
ruleRelationalOperator
@init {
}
:
(
	LessThanSignKeyword_0=LessThanSign {
		announce($LessThanSignKeyword_0, grammarAccess.getRelationalOperatorAccess().getLessThanSignKeyword_0());
	}
	    |
	(
		GreaterThanSignKeyword_1_0=GreaterThanSign {
			announce($GreaterThanSignKeyword_1_0, grammarAccess.getRelationalOperatorAccess().getGreaterThanSignKeyword_1_0());
		}
		(
			EqualsSignKeyword_1_1=EqualsSign {
				announce($EqualsSignKeyword_1_1, grammarAccess.getRelationalOperatorAccess().getEqualsSignKeyword_1_1());
			}
		)?
	)
	    |
	LessThanSignEqualsSignKeyword_2=LessThanSignEqualsSign {
		announce($LessThanSignEqualsSignKeyword_2, grammarAccess.getRelationalOperatorAccess().getLessThanSignEqualsSignKeyword_2());
	}
	    |
	InstanceofKeyword_3=Instanceof {
		announce($InstanceofKeyword_3, grammarAccess.getRelationalOperatorAccess().getInstanceofKeyword_3());
	}
)
;


// Rule RelationalOperator
norm1_RelationalOperator
@init {
}
:
(
	LessThanSignKeyword_0=LessThanSign {
		announce($LessThanSignKeyword_0, grammarAccess.getRelationalOperatorAccess().getLessThanSignKeyword_0());
	}
	    |
	(
		GreaterThanSignKeyword_1_0=GreaterThanSign {
			announce($GreaterThanSignKeyword_1_0, grammarAccess.getRelationalOperatorAccess().getGreaterThanSignKeyword_1_0());
		}
		(
			EqualsSignKeyword_1_1=EqualsSign {
				announce($EqualsSignKeyword_1_1, grammarAccess.getRelationalOperatorAccess().getEqualsSignKeyword_1_1());
			}
		)?
	)
	    |
	LessThanSignEqualsSignKeyword_2=LessThanSignEqualsSign {
		announce($LessThanSignEqualsSignKeyword_2, grammarAccess.getRelationalOperatorAccess().getLessThanSignEqualsSignKeyword_2());
	}
	    |
	InstanceofKeyword_3=Instanceof {
		announce($InstanceofKeyword_3, grammarAccess.getRelationalOperatorAccess().getInstanceofKeyword_3());
	}
	    |
	InKeyword_4_0=In {
		announce($InKeyword_4_0, grammarAccess.getRelationalOperatorAccess().getInKeyword_4_0());
	}
)
;

// Entry rule entryRuleEqualityExpression
entryRuleEqualityExpression
	:
	ruleEqualityExpression
	EOF;

// Rule EqualityExpression
ruleEqualityExpression
@init {
}:
(
	RelationalExpressionParserRuleCall_0=ruleRelationalExpression{ announce($RelationalExpressionParserRuleCall_0.start, $RelationalExpressionParserRuleCall_0.stop, grammarAccess.getEqualityExpressionAccess().getRelationalExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleEqualityOperator
					)
				)
			)
			)=>
			(
				(
					(
						ruleEqualityOperator
					)
				)
			)
		)
		(
			(
				RhsRelationalExpressionParserRuleCall_1_1_0=ruleRelationalExpression{
					announce($RhsRelationalExpressionParserRuleCall_1_1_0.start, $RhsRelationalExpressionParserRuleCall_1_1_0.stop, grammarAccess.getEqualityExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule EqualityExpression
norm1_EqualityExpression
@init {
}:
(
	RelationalExpressionParserRuleCall_0=norm1_RelationalExpression{ announce($RelationalExpressionParserRuleCall_0.start, $RelationalExpressionParserRuleCall_0.stop, grammarAccess.getEqualityExpressionAccess().getRelationalExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleEqualityOperator
					)
				)
			)
			)=>
			(
				(
					(
						ruleEqualityOperator
					)
				)
			)
		)
		(
			(
				RhsRelationalExpressionParserRuleCall_1_1_0=norm1_RelationalExpression{
					announce($RhsRelationalExpressionParserRuleCall_1_1_0.start, $RhsRelationalExpressionParserRuleCall_1_1_0.stop, grammarAccess.getEqualityExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule EqualityExpression
norm2_EqualityExpression
@init {
}:
(
	RelationalExpressionParserRuleCall_0=norm2_RelationalExpression{ announce($RelationalExpressionParserRuleCall_0.start, $RelationalExpressionParserRuleCall_0.stop, grammarAccess.getEqualityExpressionAccess().getRelationalExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleEqualityOperator
					)
				)
			)
			)=>
			(
				(
					(
						ruleEqualityOperator
					)
				)
			)
		)
		(
			(
				RhsRelationalExpressionParserRuleCall_1_1_0=norm2_RelationalExpression{
					announce($RhsRelationalExpressionParserRuleCall_1_1_0.start, $RhsRelationalExpressionParserRuleCall_1_1_0.stop, grammarAccess.getEqualityExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule EqualityExpression
norm3_EqualityExpression
@init {
}:
(
	RelationalExpressionParserRuleCall_0=norm3_RelationalExpression{ announce($RelationalExpressionParserRuleCall_0.start, $RelationalExpressionParserRuleCall_0.stop, grammarAccess.getEqualityExpressionAccess().getRelationalExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleEqualityOperator
					)
				)
			)
			)=>
			(
				(
					(
						ruleEqualityOperator
					)
				)
			)
		)
		(
			(
				RhsRelationalExpressionParserRuleCall_1_1_0=norm3_RelationalExpression{
					announce($RhsRelationalExpressionParserRuleCall_1_1_0.start, $RhsRelationalExpressionParserRuleCall_1_1_0.stop, grammarAccess.getEqualityExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleBitwiseANDExpression
entryRuleBitwiseANDExpression
	:
	ruleBitwiseANDExpression
	EOF;

// Rule BitwiseANDExpression
ruleBitwiseANDExpression
@init {
}:
(
	EqualityExpressionParserRuleCall_0=ruleEqualityExpression{ announce($EqualityExpressionParserRuleCall_0.start, $EqualityExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getEqualityExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseANDOperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0=ruleBitwiseANDOperator{
							announce($OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsEqualityExpressionParserRuleCall_1_1_0=ruleEqualityExpression{
					announce($RhsEqualityExpressionParserRuleCall_1_1_0.start, $RhsEqualityExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule BitwiseANDExpression
norm1_BitwiseANDExpression
@init {
}:
(
	EqualityExpressionParserRuleCall_0=norm1_EqualityExpression{ announce($EqualityExpressionParserRuleCall_0.start, $EqualityExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getEqualityExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseANDOperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0=ruleBitwiseANDOperator{
							announce($OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsEqualityExpressionParserRuleCall_1_1_0=norm1_EqualityExpression{
					announce($RhsEqualityExpressionParserRuleCall_1_1_0.start, $RhsEqualityExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule BitwiseANDExpression
norm2_BitwiseANDExpression
@init {
}:
(
	EqualityExpressionParserRuleCall_0=norm2_EqualityExpression{ announce($EqualityExpressionParserRuleCall_0.start, $EqualityExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getEqualityExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseANDOperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0=ruleBitwiseANDOperator{
							announce($OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsEqualityExpressionParserRuleCall_1_1_0=norm2_EqualityExpression{
					announce($RhsEqualityExpressionParserRuleCall_1_1_0.start, $RhsEqualityExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule BitwiseANDExpression
norm3_BitwiseANDExpression
@init {
}:
(
	EqualityExpressionParserRuleCall_0=norm3_EqualityExpression{ announce($EqualityExpressionParserRuleCall_0.start, $EqualityExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getEqualityExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseANDOperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0=ruleBitwiseANDOperator{
							announce($OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseANDOperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsEqualityExpressionParserRuleCall_1_1_0=norm3_EqualityExpression{
					announce($RhsEqualityExpressionParserRuleCall_1_1_0.start, $RhsEqualityExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseANDExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleBitwiseANDOperator
entryRuleBitwiseANDOperator
	:
	ruleBitwiseANDOperator
	EOF;

// Rule BitwiseANDOperator
ruleBitwiseANDOperator
@init {
}
:
AmpersandKeyword=Ampersand {
	announce($AmpersandKeyword, grammarAccess.getBitwiseANDOperatorAccess().getAmpersandKeyword());
}
;

// Entry rule entryRuleBitwiseXORExpression
entryRuleBitwiseXORExpression
	:
	ruleBitwiseXORExpression
	EOF;

// Rule BitwiseXORExpression
ruleBitwiseXORExpression
@init {
}:
(
	BitwiseANDExpressionParserRuleCall_0=ruleBitwiseANDExpression{ announce($BitwiseANDExpressionParserRuleCall_0.start, $BitwiseANDExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getBitwiseANDExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseXOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0=ruleBitwiseXOROperator{
							announce($OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseANDExpressionParserRuleCall_1_1_0=ruleBitwiseANDExpression{
					announce($RhsBitwiseANDExpressionParserRuleCall_1_1_0.start, $RhsBitwiseANDExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule BitwiseXORExpression
norm1_BitwiseXORExpression
@init {
}:
(
	BitwiseANDExpressionParserRuleCall_0=norm1_BitwiseANDExpression{ announce($BitwiseANDExpressionParserRuleCall_0.start, $BitwiseANDExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getBitwiseANDExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseXOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0=ruleBitwiseXOROperator{
							announce($OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseANDExpressionParserRuleCall_1_1_0=norm1_BitwiseANDExpression{
					announce($RhsBitwiseANDExpressionParserRuleCall_1_1_0.start, $RhsBitwiseANDExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule BitwiseXORExpression
norm2_BitwiseXORExpression
@init {
}:
(
	BitwiseANDExpressionParserRuleCall_0=norm2_BitwiseANDExpression{ announce($BitwiseANDExpressionParserRuleCall_0.start, $BitwiseANDExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getBitwiseANDExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseXOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0=ruleBitwiseXOROperator{
							announce($OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseANDExpressionParserRuleCall_1_1_0=norm2_BitwiseANDExpression{
					announce($RhsBitwiseANDExpressionParserRuleCall_1_1_0.start, $RhsBitwiseANDExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule BitwiseXORExpression
norm3_BitwiseXORExpression
@init {
}:
(
	BitwiseANDExpressionParserRuleCall_0=norm3_BitwiseANDExpression{ announce($BitwiseANDExpressionParserRuleCall_0.start, $BitwiseANDExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getBitwiseANDExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseXOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0=ruleBitwiseXOROperator{
							announce($OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseXOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseANDExpressionParserRuleCall_1_1_0=norm3_BitwiseANDExpression{
					announce($RhsBitwiseANDExpressionParserRuleCall_1_1_0.start, $RhsBitwiseANDExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseXORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleBitwiseXOROperator
entryRuleBitwiseXOROperator
	:
	ruleBitwiseXOROperator
	EOF;

// Rule BitwiseXOROperator
ruleBitwiseXOROperator
@init {
}
:
CircumflexAccentKeyword=CircumflexAccent {
	announce($CircumflexAccentKeyword, grammarAccess.getBitwiseXOROperatorAccess().getCircumflexAccentKeyword());
}
;

// Entry rule entryRuleBitwiseORExpression
entryRuleBitwiseORExpression
	:
	ruleBitwiseORExpression
	EOF;

// Rule BitwiseORExpression
ruleBitwiseORExpression
@init {
}:
(
	BitwiseXORExpressionParserRuleCall_0=ruleBitwiseXORExpression{ announce($BitwiseXORExpressionParserRuleCall_0.start, $BitwiseXORExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseORExpressionAccess().getBitwiseXORExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseOROperatorParserRuleCall_1_0_0_1_0=ruleBitwiseOROperator{
							announce($OpBitwiseOROperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseXORExpressionParserRuleCall_1_1_0=ruleBitwiseXORExpression{
					announce($RhsBitwiseXORExpressionParserRuleCall_1_1_0.start, $RhsBitwiseXORExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule BitwiseORExpression
norm1_BitwiseORExpression
@init {
}:
(
	BitwiseXORExpressionParserRuleCall_0=norm1_BitwiseXORExpression{ announce($BitwiseXORExpressionParserRuleCall_0.start, $BitwiseXORExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseORExpressionAccess().getBitwiseXORExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseOROperatorParserRuleCall_1_0_0_1_0=ruleBitwiseOROperator{
							announce($OpBitwiseOROperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseXORExpressionParserRuleCall_1_1_0=norm1_BitwiseXORExpression{
					announce($RhsBitwiseXORExpressionParserRuleCall_1_1_0.start, $RhsBitwiseXORExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule BitwiseORExpression
norm2_BitwiseORExpression
@init {
}:
(
	BitwiseXORExpressionParserRuleCall_0=norm2_BitwiseXORExpression{ announce($BitwiseXORExpressionParserRuleCall_0.start, $BitwiseXORExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseORExpressionAccess().getBitwiseXORExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseOROperatorParserRuleCall_1_0_0_1_0=ruleBitwiseOROperator{
							announce($OpBitwiseOROperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseXORExpressionParserRuleCall_1_1_0=norm2_BitwiseXORExpression{
					announce($RhsBitwiseXORExpressionParserRuleCall_1_1_0.start, $RhsBitwiseXORExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule BitwiseORExpression
norm3_BitwiseORExpression
@init {
}:
(
	BitwiseXORExpressionParserRuleCall_0=norm3_BitwiseXORExpression{ announce($BitwiseXORExpressionParserRuleCall_0.start, $BitwiseXORExpressionParserRuleCall_0.stop, grammarAccess.getBitwiseORExpressionAccess().getBitwiseXORExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleBitwiseOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpBitwiseOROperatorParserRuleCall_1_0_0_1_0=ruleBitwiseOROperator{
							announce($OpBitwiseOROperatorParserRuleCall_1_0_0_1_0.start, $OpBitwiseOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getBitwiseORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseXORExpressionParserRuleCall_1_1_0=norm3_BitwiseXORExpression{
					announce($RhsBitwiseXORExpressionParserRuleCall_1_1_0.start, $RhsBitwiseXORExpressionParserRuleCall_1_1_0.stop, grammarAccess.getBitwiseORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleBitwiseOROperator
entryRuleBitwiseOROperator
	:
	ruleBitwiseOROperator
	EOF;

// Rule BitwiseOROperator
ruleBitwiseOROperator
@init {
}
:
VerticalLineKeyword=VerticalLine {
	announce($VerticalLineKeyword, grammarAccess.getBitwiseOROperatorAccess().getVerticalLineKeyword());
}
;

// Entry rule entryRuleLogicalANDExpression
entryRuleLogicalANDExpression
	:
	ruleLogicalANDExpression
	EOF;

// Rule LogicalANDExpression
ruleLogicalANDExpression
@init {
}:
(
	BitwiseORExpressionParserRuleCall_0=ruleBitwiseORExpression{ announce($BitwiseORExpressionParserRuleCall_0.start, $BitwiseORExpressionParserRuleCall_0.stop, grammarAccess.getLogicalANDExpressionAccess().getBitwiseORExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleLogicalANDOperator
					)
				)
			)
			)=>
			(
				(
					(
						OpLogicalANDOperatorParserRuleCall_1_0_0_1_0=ruleLogicalANDOperator{
							announce($OpLogicalANDOperatorParserRuleCall_1_0_0_1_0.start, $OpLogicalANDOperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getLogicalANDExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseORExpressionParserRuleCall_1_1_0=ruleBitwiseORExpression{
					announce($RhsBitwiseORExpressionParserRuleCall_1_1_0.start, $RhsBitwiseORExpressionParserRuleCall_1_1_0.stop, grammarAccess.getLogicalANDExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule LogicalANDExpression
norm1_LogicalANDExpression
@init {
}:
(
	BitwiseORExpressionParserRuleCall_0=norm1_BitwiseORExpression{ announce($BitwiseORExpressionParserRuleCall_0.start, $BitwiseORExpressionParserRuleCall_0.stop, grammarAccess.getLogicalANDExpressionAccess().getBitwiseORExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleLogicalANDOperator
					)
				)
			)
			)=>
			(
				(
					(
						OpLogicalANDOperatorParserRuleCall_1_0_0_1_0=ruleLogicalANDOperator{
							announce($OpLogicalANDOperatorParserRuleCall_1_0_0_1_0.start, $OpLogicalANDOperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getLogicalANDExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseORExpressionParserRuleCall_1_1_0=norm1_BitwiseORExpression{
					announce($RhsBitwiseORExpressionParserRuleCall_1_1_0.start, $RhsBitwiseORExpressionParserRuleCall_1_1_0.stop, grammarAccess.getLogicalANDExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule LogicalANDExpression
norm2_LogicalANDExpression
@init {
}:
(
	BitwiseORExpressionParserRuleCall_0=norm2_BitwiseORExpression{ announce($BitwiseORExpressionParserRuleCall_0.start, $BitwiseORExpressionParserRuleCall_0.stop, grammarAccess.getLogicalANDExpressionAccess().getBitwiseORExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleLogicalANDOperator
					)
				)
			)
			)=>
			(
				(
					(
						OpLogicalANDOperatorParserRuleCall_1_0_0_1_0=ruleLogicalANDOperator{
							announce($OpLogicalANDOperatorParserRuleCall_1_0_0_1_0.start, $OpLogicalANDOperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getLogicalANDExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseORExpressionParserRuleCall_1_1_0=norm2_BitwiseORExpression{
					announce($RhsBitwiseORExpressionParserRuleCall_1_1_0.start, $RhsBitwiseORExpressionParserRuleCall_1_1_0.stop, grammarAccess.getLogicalANDExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule LogicalANDExpression
norm3_LogicalANDExpression
@init {
}:
(
	BitwiseORExpressionParserRuleCall_0=norm3_BitwiseORExpression{ announce($BitwiseORExpressionParserRuleCall_0.start, $BitwiseORExpressionParserRuleCall_0.stop, grammarAccess.getLogicalANDExpressionAccess().getBitwiseORExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleLogicalANDOperator
					)
				)
			)
			)=>
			(
				(
					(
						OpLogicalANDOperatorParserRuleCall_1_0_0_1_0=ruleLogicalANDOperator{
							announce($OpLogicalANDOperatorParserRuleCall_1_0_0_1_0.start, $OpLogicalANDOperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getLogicalANDExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsBitwiseORExpressionParserRuleCall_1_1_0=norm3_BitwiseORExpression{
					announce($RhsBitwiseORExpressionParserRuleCall_1_1_0.start, $RhsBitwiseORExpressionParserRuleCall_1_1_0.stop, grammarAccess.getLogicalANDExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleLogicalANDOperator
entryRuleLogicalANDOperator
	:
	ruleLogicalANDOperator
	EOF;

// Rule LogicalANDOperator
ruleLogicalANDOperator
@init {
}
:
AmpersandAmpersandKeyword=AmpersandAmpersand {
	announce($AmpersandAmpersandKeyword, grammarAccess.getLogicalANDOperatorAccess().getAmpersandAmpersandKeyword());
}
;

// Entry rule entryRuleLogicalORExpression
entryRuleLogicalORExpression
	:
	ruleLogicalORExpression
	EOF;

// Rule LogicalORExpression
ruleLogicalORExpression
@init {
}:
(
	LogicalANDExpressionParserRuleCall_0=ruleLogicalANDExpression{ announce($LogicalANDExpressionParserRuleCall_0.start, $LogicalANDExpressionParserRuleCall_0.stop, grammarAccess.getLogicalORExpressionAccess().getLogicalANDExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleLogicalOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpLogicalOROperatorParserRuleCall_1_0_0_1_0=ruleLogicalOROperator{
							announce($OpLogicalOROperatorParserRuleCall_1_0_0_1_0.start, $OpLogicalOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getLogicalORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsLogicalANDExpressionParserRuleCall_1_1_0=ruleLogicalANDExpression{
					announce($RhsLogicalANDExpressionParserRuleCall_1_1_0.start, $RhsLogicalANDExpressionParserRuleCall_1_1_0.stop, grammarAccess.getLogicalORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule LogicalORExpression
norm1_LogicalORExpression
@init {
}:
(
	LogicalANDExpressionParserRuleCall_0=norm1_LogicalANDExpression{ announce($LogicalANDExpressionParserRuleCall_0.start, $LogicalANDExpressionParserRuleCall_0.stop, grammarAccess.getLogicalORExpressionAccess().getLogicalANDExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleLogicalOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpLogicalOROperatorParserRuleCall_1_0_0_1_0=ruleLogicalOROperator{
							announce($OpLogicalOROperatorParserRuleCall_1_0_0_1_0.start, $OpLogicalOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getLogicalORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsLogicalANDExpressionParserRuleCall_1_1_0=norm1_LogicalANDExpression{
					announce($RhsLogicalANDExpressionParserRuleCall_1_1_0.start, $RhsLogicalANDExpressionParserRuleCall_1_1_0.stop, grammarAccess.getLogicalORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule LogicalORExpression
norm2_LogicalORExpression
@init {
}:
(
	LogicalANDExpressionParserRuleCall_0=norm2_LogicalANDExpression{ announce($LogicalANDExpressionParserRuleCall_0.start, $LogicalANDExpressionParserRuleCall_0.stop, grammarAccess.getLogicalORExpressionAccess().getLogicalANDExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleLogicalOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpLogicalOROperatorParserRuleCall_1_0_0_1_0=ruleLogicalOROperator{
							announce($OpLogicalOROperatorParserRuleCall_1_0_0_1_0.start, $OpLogicalOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getLogicalORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsLogicalANDExpressionParserRuleCall_1_1_0=norm2_LogicalANDExpression{
					announce($RhsLogicalANDExpressionParserRuleCall_1_1_0.start, $RhsLogicalANDExpressionParserRuleCall_1_1_0.stop, grammarAccess.getLogicalORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;


// Rule LogicalORExpression
norm3_LogicalORExpression
@init {
}:
(
	LogicalANDExpressionParserRuleCall_0=norm3_LogicalANDExpression{ announce($LogicalANDExpressionParserRuleCall_0.start, $LogicalANDExpressionParserRuleCall_0.stop, grammarAccess.getLogicalORExpressionAccess().getLogicalANDExpressionParserRuleCall_0()); }
	(
		(
			((
				(
					(
						ruleLogicalOROperator
					)
				)
			)
			)=>
			(
				(
					(
						OpLogicalOROperatorParserRuleCall_1_0_0_1_0=ruleLogicalOROperator{
							announce($OpLogicalOROperatorParserRuleCall_1_0_0_1_0.start, $OpLogicalOROperatorParserRuleCall_1_0_0_1_0.stop, grammarAccess.getLogicalORExpressionAccess().getOpAssignment_1_0_0_1());
						}
					)
				)
			)
		)
		(
			(
				RhsLogicalANDExpressionParserRuleCall_1_1_0=norm3_LogicalANDExpression{
					announce($RhsLogicalANDExpressionParserRuleCall_1_1_0.start, $RhsLogicalANDExpressionParserRuleCall_1_1_0.stop, grammarAccess.getLogicalORExpressionAccess().getRhsAssignment_1_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleLogicalOROperator
entryRuleLogicalOROperator
	:
	ruleLogicalOROperator
	EOF;

// Rule LogicalOROperator
ruleLogicalOROperator
@init {
}
:
VerticalLineVerticalLineKeyword=VerticalLineVerticalLine {
	announce($VerticalLineVerticalLineKeyword, grammarAccess.getLogicalOROperatorAccess().getVerticalLineVerticalLineKeyword());
}
;

// Entry rule entryRuleConditionalExpression
entryRuleConditionalExpression
	:
	ruleConditionalExpression
	EOF;

// Rule ConditionalExpression
ruleConditionalExpression
@init {
}:
(
	LogicalORExpressionParserRuleCall_0=ruleLogicalORExpression{ announce($LogicalORExpressionParserRuleCall_0.start, $LogicalORExpressionParserRuleCall_0.stop, grammarAccess.getConditionalExpressionAccess().getLogicalORExpressionParserRuleCall_0()); }
	(
		(
			((
				QuestionMark
			)
			)=>
			(
				QuestionMarkKeyword_1_0_0_1=QuestionMark
				 {
					announce($QuestionMarkKeyword_1_0_0_1, grammarAccess.getConditionalExpressionAccess().getQuestionMarkKeyword_1_0_0_1());
				}
			)
		)
		(
			(
				TrueExpressionAssignmentExpressionParserRuleCall_1_1_0=norm1_AssignmentExpression{
					announce($TrueExpressionAssignmentExpressionParserRuleCall_1_1_0.start, $TrueExpressionAssignmentExpressionParserRuleCall_1_1_0.stop, grammarAccess.getConditionalExpressionAccess().getTrueExpressionAssignment_1_1());
				}
			)
		)
		ColonKeyword_1_2=Colon
		 {
			announce($ColonKeyword_1_2, grammarAccess.getConditionalExpressionAccess().getColonKeyword_1_2());
		}
		(
			(
				FalseExpressionAssignmentExpressionParserRuleCall_1_3_0=ruleAssignmentExpression{
					announce($FalseExpressionAssignmentExpressionParserRuleCall_1_3_0.start, $FalseExpressionAssignmentExpressionParserRuleCall_1_3_0.stop, grammarAccess.getConditionalExpressionAccess().getFalseExpressionAssignment_1_3());
				}
			)
		)
	)?
)
;


// Rule ConditionalExpression
norm1_ConditionalExpression
@init {
}:
(
	LogicalORExpressionParserRuleCall_0=norm1_LogicalORExpression{ announce($LogicalORExpressionParserRuleCall_0.start, $LogicalORExpressionParserRuleCall_0.stop, grammarAccess.getConditionalExpressionAccess().getLogicalORExpressionParserRuleCall_0()); }
	(
		(
			((
				QuestionMark
			)
			)=>
			(
				QuestionMarkKeyword_1_0_0_1=QuestionMark
				 {
					announce($QuestionMarkKeyword_1_0_0_1, grammarAccess.getConditionalExpressionAccess().getQuestionMarkKeyword_1_0_0_1());
				}
			)
		)
		(
			(
				TrueExpressionAssignmentExpressionParserRuleCall_1_1_0=norm1_AssignmentExpression{
					announce($TrueExpressionAssignmentExpressionParserRuleCall_1_1_0.start, $TrueExpressionAssignmentExpressionParserRuleCall_1_1_0.stop, grammarAccess.getConditionalExpressionAccess().getTrueExpressionAssignment_1_1());
				}
			)
		)
		ColonKeyword_1_2=Colon
		 {
			announce($ColonKeyword_1_2, grammarAccess.getConditionalExpressionAccess().getColonKeyword_1_2());
		}
		(
			(
				FalseExpressionAssignmentExpressionParserRuleCall_1_3_0=norm1_AssignmentExpression{
					announce($FalseExpressionAssignmentExpressionParserRuleCall_1_3_0.start, $FalseExpressionAssignmentExpressionParserRuleCall_1_3_0.stop, grammarAccess.getConditionalExpressionAccess().getFalseExpressionAssignment_1_3());
				}
			)
		)
	)?
)
;


// Rule ConditionalExpression
norm2_ConditionalExpression
@init {
}:
(
	LogicalORExpressionParserRuleCall_0=norm2_LogicalORExpression{ announce($LogicalORExpressionParserRuleCall_0.start, $LogicalORExpressionParserRuleCall_0.stop, grammarAccess.getConditionalExpressionAccess().getLogicalORExpressionParserRuleCall_0()); }
	(
		(
			((
				QuestionMark
			)
			)=>
			(
				QuestionMarkKeyword_1_0_0_1=QuestionMark
				 {
					announce($QuestionMarkKeyword_1_0_0_1, grammarAccess.getConditionalExpressionAccess().getQuestionMarkKeyword_1_0_0_1());
				}
			)
		)
		(
			(
				TrueExpressionAssignmentExpressionParserRuleCall_1_1_0=norm3_AssignmentExpression{
					announce($TrueExpressionAssignmentExpressionParserRuleCall_1_1_0.start, $TrueExpressionAssignmentExpressionParserRuleCall_1_1_0.stop, grammarAccess.getConditionalExpressionAccess().getTrueExpressionAssignment_1_1());
				}
			)
		)
		ColonKeyword_1_2=Colon
		 {
			announce($ColonKeyword_1_2, grammarAccess.getConditionalExpressionAccess().getColonKeyword_1_2());
		}
		(
			(
				FalseExpressionAssignmentExpressionParserRuleCall_1_3_0=norm2_AssignmentExpression{
					announce($FalseExpressionAssignmentExpressionParserRuleCall_1_3_0.start, $FalseExpressionAssignmentExpressionParserRuleCall_1_3_0.stop, grammarAccess.getConditionalExpressionAccess().getFalseExpressionAssignment_1_3());
				}
			)
		)
	)?
)
;


// Rule ConditionalExpression
norm3_ConditionalExpression
@init {
}:
(
	LogicalORExpressionParserRuleCall_0=norm3_LogicalORExpression{ announce($LogicalORExpressionParserRuleCall_0.start, $LogicalORExpressionParserRuleCall_0.stop, grammarAccess.getConditionalExpressionAccess().getLogicalORExpressionParserRuleCall_0()); }
	(
		(
			((
				QuestionMark
			)
			)=>
			(
				QuestionMarkKeyword_1_0_0_1=QuestionMark
				 {
					announce($QuestionMarkKeyword_1_0_0_1, grammarAccess.getConditionalExpressionAccess().getQuestionMarkKeyword_1_0_0_1());
				}
			)
		)
		(
			(
				TrueExpressionAssignmentExpressionParserRuleCall_1_1_0=norm3_AssignmentExpression{
					announce($TrueExpressionAssignmentExpressionParserRuleCall_1_1_0.start, $TrueExpressionAssignmentExpressionParserRuleCall_1_1_0.stop, grammarAccess.getConditionalExpressionAccess().getTrueExpressionAssignment_1_1());
				}
			)
		)
		ColonKeyword_1_2=Colon
		 {
			announce($ColonKeyword_1_2, grammarAccess.getConditionalExpressionAccess().getColonKeyword_1_2());
		}
		(
			(
				FalseExpressionAssignmentExpressionParserRuleCall_1_3_0=norm3_AssignmentExpression{
					announce($FalseExpressionAssignmentExpressionParserRuleCall_1_3_0.start, $FalseExpressionAssignmentExpressionParserRuleCall_1_3_0.stop, grammarAccess.getConditionalExpressionAccess().getFalseExpressionAssignment_1_3());
				}
			)
		)
	)?
)
;

// Entry rule entryRuleAssignmentExpression
entryRuleAssignmentExpression
	:
	ruleAssignmentExpression
	EOF;

// Rule AssignmentExpression
ruleAssignmentExpression
@init {
}:
(
	(
		((
			Await
		)
		)=>
		AwaitExpressionParserRuleCall_0=ruleAwaitExpression{ announce($AwaitExpressionParserRuleCall_0.start, $AwaitExpressionParserRuleCall_0.stop, grammarAccess.getAssignmentExpressionAccess().getAwaitExpressionParserRuleCall_0()); }
	)
	    |
	(
		((
			CommercialAt
			Promisify
		)
		)=>
		PromisifyExpressionParserRuleCall_1=rulePromisifyExpression{ announce($PromisifyExpressionParserRuleCall_1.start, $PromisifyExpressionParserRuleCall_1.stop, grammarAccess.getAssignmentExpressionAccess().getPromisifyExpressionParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					ruleStrictFormalParameters
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							(
								(LeftParenthesis
								)=>
								ruleStrictFormalParameters
							)
						)
					)
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						ruleBindingIdentifierAsFormalParameter
					)
				)
			)
			EqualsSignGreaterThanSign
		)
		)=>
		ArrowExpressionParserRuleCall_2=ruleArrowExpression{ announce($ArrowExpressionParserRuleCall_2.start, $ArrowExpressionParserRuleCall_2.stop, grammarAccess.getAssignmentExpressionAccess().getArrowExpressionParserRuleCall_2()); }
	)
	    |
	(
		ConditionalExpressionParserRuleCall_4_0=ruleConditionalExpression{ announce($ConditionalExpressionParserRuleCall_4_0.start, $ConditionalExpressionParserRuleCall_4_0.stop, grammarAccess.getAssignmentExpressionAccess().getConditionalExpressionParserRuleCall_4_0()); }
		(
			(
				((
					(
						(
							ruleAssignmentOperator
						)
					)
				)
				)=>
				(
					(
						(
							OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0=ruleAssignmentOperator{
								announce($OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0.start, $OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0.stop, grammarAccess.getAssignmentExpressionAccess().getOpAssignment_4_1_0_0_1());
							}
						)
					)
				)
			)
			(
				(
					RhsAssignmentExpressionParserRuleCall_4_1_1_0=ruleAssignmentExpression{
						announce($RhsAssignmentExpressionParserRuleCall_4_1_1_0.start, $RhsAssignmentExpressionParserRuleCall_4_1_1_0.stop, grammarAccess.getAssignmentExpressionAccess().getRhsAssignment_4_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule AssignmentExpression
norm1_AssignmentExpression
@init {
}:
(
	(
		((
			Await
		)
		)=>
		AwaitExpressionParserRuleCall_0=norm1_AwaitExpression{ announce($AwaitExpressionParserRuleCall_0.start, $AwaitExpressionParserRuleCall_0.stop, grammarAccess.getAssignmentExpressionAccess().getAwaitExpressionParserRuleCall_0()); }
	)
	    |
	(
		((
			CommercialAt
			Promisify
		)
		)=>
		PromisifyExpressionParserRuleCall_1=norm1_PromisifyExpression{ announce($PromisifyExpressionParserRuleCall_1.start, $PromisifyExpressionParserRuleCall_1.stop, grammarAccess.getAssignmentExpressionAccess().getPromisifyExpressionParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					ruleStrictFormalParameters
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							(
								(LeftParenthesis
								)=>
								ruleStrictFormalParameters
							)
						)
					)
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						ruleBindingIdentifierAsFormalParameter
					)
				)
			)
			EqualsSignGreaterThanSign
		)
		)=>
		ArrowExpressionParserRuleCall_2=norm1_ArrowExpression{ announce($ArrowExpressionParserRuleCall_2.start, $ArrowExpressionParserRuleCall_2.stop, grammarAccess.getAssignmentExpressionAccess().getArrowExpressionParserRuleCall_2()); }
	)
	    |
	(
		ConditionalExpressionParserRuleCall_4_0=norm1_ConditionalExpression{ announce($ConditionalExpressionParserRuleCall_4_0.start, $ConditionalExpressionParserRuleCall_4_0.stop, grammarAccess.getAssignmentExpressionAccess().getConditionalExpressionParserRuleCall_4_0()); }
		(
			(
				((
					(
						(
							ruleAssignmentOperator
						)
					)
				)
				)=>
				(
					(
						(
							OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0=ruleAssignmentOperator{
								announce($OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0.start, $OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0.stop, grammarAccess.getAssignmentExpressionAccess().getOpAssignment_4_1_0_0_1());
							}
						)
					)
				)
			)
			(
				(
					RhsAssignmentExpressionParserRuleCall_4_1_1_0=norm1_AssignmentExpression{
						announce($RhsAssignmentExpressionParserRuleCall_4_1_1_0.start, $RhsAssignmentExpressionParserRuleCall_4_1_1_0.stop, grammarAccess.getAssignmentExpressionAccess().getRhsAssignment_4_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule AssignmentExpression
norm2_AssignmentExpression
@init {
}:
(
	(
		((
			Await
		)
		)=>
		AwaitExpressionParserRuleCall_0=norm2_AwaitExpression{ announce($AwaitExpressionParserRuleCall_0.start, $AwaitExpressionParserRuleCall_0.stop, grammarAccess.getAssignmentExpressionAccess().getAwaitExpressionParserRuleCall_0()); }
	)
	    |
	(
		((
			CommercialAt
			Promisify
		)
		)=>
		PromisifyExpressionParserRuleCall_1=norm2_PromisifyExpression{ announce($PromisifyExpressionParserRuleCall_1.start, $PromisifyExpressionParserRuleCall_1.stop, grammarAccess.getAssignmentExpressionAccess().getPromisifyExpressionParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					norm1_StrictFormalParameters
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							(
								(LeftParenthesis
								)=>
								norm1_StrictFormalParameters
							)
						)
					)
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						norm1_BindingIdentifierAsFormalParameter
					)
				)
			)
			EqualsSignGreaterThanSign
		)
		)=>
		ArrowExpressionParserRuleCall_2=norm2_ArrowExpression{ announce($ArrowExpressionParserRuleCall_2.start, $ArrowExpressionParserRuleCall_2.stop, grammarAccess.getAssignmentExpressionAccess().getArrowExpressionParserRuleCall_2()); }
	)
	    |
	YieldExpressionParserRuleCall_3_0=ruleYieldExpression{ announce($YieldExpressionParserRuleCall_3_0.start, $YieldExpressionParserRuleCall_3_0.stop, grammarAccess.getAssignmentExpressionAccess().getYieldExpressionParserRuleCall_3_0()); }
	    |
	(
		ConditionalExpressionParserRuleCall_4_0=norm2_ConditionalExpression{ announce($ConditionalExpressionParserRuleCall_4_0.start, $ConditionalExpressionParserRuleCall_4_0.stop, grammarAccess.getAssignmentExpressionAccess().getConditionalExpressionParserRuleCall_4_0()); }
		(
			(
				((
					(
						(
							ruleAssignmentOperator
						)
					)
				)
				)=>
				(
					(
						(
							OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0=ruleAssignmentOperator{
								announce($OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0.start, $OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0.stop, grammarAccess.getAssignmentExpressionAccess().getOpAssignment_4_1_0_0_1());
							}
						)
					)
				)
			)
			(
				(
					RhsAssignmentExpressionParserRuleCall_4_1_1_0=norm2_AssignmentExpression{
						announce($RhsAssignmentExpressionParserRuleCall_4_1_1_0.start, $RhsAssignmentExpressionParserRuleCall_4_1_1_0.stop, grammarAccess.getAssignmentExpressionAccess().getRhsAssignment_4_1_1());
					}
				)
			)
		)?
	)
)
;


// Rule AssignmentExpression
norm3_AssignmentExpression
@init {
}:
(
	(
		((
			Await
		)
		)=>
		AwaitExpressionParserRuleCall_0=norm3_AwaitExpression{ announce($AwaitExpressionParserRuleCall_0.start, $AwaitExpressionParserRuleCall_0.stop, grammarAccess.getAssignmentExpressionAccess().getAwaitExpressionParserRuleCall_0()); }
	)
	    |
	(
		((
			CommercialAt
			Promisify
		)
		)=>
		PromisifyExpressionParserRuleCall_1=norm3_PromisifyExpression{ announce($PromisifyExpressionParserRuleCall_1.start, $PromisifyExpressionParserRuleCall_1.stop, grammarAccess.getAssignmentExpressionAccess().getPromisifyExpressionParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					norm1_StrictFormalParameters
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						((
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							LeftParenthesis
						)
						)=>
						(
							(
								(
									Async
								)
							)
							ruleNoLineTerminator
							(
								(LeftParenthesis
								)=>
								norm1_StrictFormalParameters
							)
						)
					)
					(
						ruleColonSepReturnTypeRef
					)?
				)
				    |
				(
					(
						norm1_BindingIdentifierAsFormalParameter
					)
				)
			)
			EqualsSignGreaterThanSign
		)
		)=>
		ArrowExpressionParserRuleCall_2=norm3_ArrowExpression{ announce($ArrowExpressionParserRuleCall_2.start, $ArrowExpressionParserRuleCall_2.stop, grammarAccess.getAssignmentExpressionAccess().getArrowExpressionParserRuleCall_2()); }
	)
	    |
	YieldExpressionParserRuleCall_3_0=norm1_YieldExpression{ announce($YieldExpressionParserRuleCall_3_0.start, $YieldExpressionParserRuleCall_3_0.stop, grammarAccess.getAssignmentExpressionAccess().getYieldExpressionParserRuleCall_3_0()); }
	    |
	(
		ConditionalExpressionParserRuleCall_4_0=norm3_ConditionalExpression{ announce($ConditionalExpressionParserRuleCall_4_0.start, $ConditionalExpressionParserRuleCall_4_0.stop, grammarAccess.getAssignmentExpressionAccess().getConditionalExpressionParserRuleCall_4_0()); }
		(
			(
				((
					(
						(
							ruleAssignmentOperator
						)
					)
				)
				)=>
				(
					(
						(
							OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0=ruleAssignmentOperator{
								announce($OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0.start, $OpAssignmentOperatorParserRuleCall_4_1_0_0_1_0.stop, grammarAccess.getAssignmentExpressionAccess().getOpAssignment_4_1_0_0_1());
							}
						)
					)
				)
			)
			(
				(
					RhsAssignmentExpressionParserRuleCall_4_1_1_0=norm3_AssignmentExpression{
						announce($RhsAssignmentExpressionParserRuleCall_4_1_1_0.start, $RhsAssignmentExpressionParserRuleCall_4_1_1_0.stop, grammarAccess.getAssignmentExpressionAccess().getRhsAssignment_4_1_1());
					}
				)
			)
		)?
	)
)
;

// Entry rule entryRuleYieldExpression
entryRuleYieldExpression
	:
	ruleYieldExpression
	EOF;

// Rule YieldExpression
ruleYieldExpression
@init {
}:
(
	YieldKeyword_1=Yield
	 {
		announce($YieldKeyword_1, grammarAccess.getYieldExpressionAccess().getYieldKeyword_1());
	}
	(
		((
			Asterisk
		)
		)=>
		(
			ManyAsteriskKeyword_2_0=Asterisk
			 {
				announce($ManyAsteriskKeyword_2_0, grammarAccess.getYieldExpressionAccess().getManyAsteriskKeyword_2_0());
			}
		)
	)?
	(
		(Await | 
		CommercialAt | 
		LeftParenthesis | 
		Async | 
		Get | 
		Set | 
		Let | 
		Project | 
		External | 
		Abstract | 
		Static | 
		As | 
		From | 
		Constructor | 
		Of | 
		Target | 
		Type | 
		Union | 
		Intersection | 
		This | 
		Promisify | 
		Implements | 
		Interface | 
		Private | 
		Protected | 
		Public | 
		Out | 
		Yield | 
		New | 
		This_1 | 
		Super | 
		LessThanSign | 
		Import | 
		True | 
		False | 
		Null | 
		Solidus | 
		SolidusEqualsSign | 
		LeftSquareBracket | 
		LeftCurlyBracket | 
		Function | 
		Class | 
		Delete | 
		Void | 
		Typeof | 
		PlusSignPlusSign | 
		HyphenMinusHyphenMinus | 
		PlusSign | 
		HyphenMinus | 
		Tilde | 
		ExclamationMark | 
		RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
		(
			ExpressionAssignmentExpressionParserRuleCall_3_0=norm2_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_3_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_0.stop, grammarAccess.getYieldExpressionAccess().getExpressionAssignment_3());
			}
		)
	)?
)
;


// Rule YieldExpression
norm1_YieldExpression
@init {
}:
(
	YieldKeyword_1=Yield
	 {
		announce($YieldKeyword_1, grammarAccess.getYieldExpressionAccess().getYieldKeyword_1());
	}
	(
		((
			Asterisk
		)
		)=>
		(
			ManyAsteriskKeyword_2_0=Asterisk
			 {
				announce($ManyAsteriskKeyword_2_0, grammarAccess.getYieldExpressionAccess().getManyAsteriskKeyword_2_0());
			}
		)
	)?
	(
		(Await | 
		CommercialAt | 
		LeftParenthesis | 
		Async | 
		Get | 
		Set | 
		Let | 
		Project | 
		External | 
		Abstract | 
		Static | 
		As | 
		From | 
		Constructor | 
		Of | 
		Target | 
		Type | 
		Union | 
		Intersection | 
		This | 
		Promisify | 
		Implements | 
		Interface | 
		Private | 
		Protected | 
		Public | 
		Out | 
		Yield | 
		New | 
		This_1 | 
		Super | 
		LessThanSign | 
		Import | 
		True | 
		False | 
		Null | 
		Solidus | 
		SolidusEqualsSign | 
		LeftSquareBracket | 
		LeftCurlyBracket | 
		Function | 
		Class | 
		Delete | 
		Void | 
		Typeof | 
		PlusSignPlusSign | 
		HyphenMinusHyphenMinus | 
		PlusSign | 
		HyphenMinus | 
		Tilde | 
		ExclamationMark | 
		RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
		(
			ExpressionAssignmentExpressionParserRuleCall_3_0=norm3_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_3_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_0.stop, grammarAccess.getYieldExpressionAccess().getExpressionAssignment_3());
			}
		)
	)?
)
;

// Entry rule entryRuleAssignmentOperator
entryRuleAssignmentOperator
	:
	ruleAssignmentOperator
	EOF;

// Rule AssignmentOperator
ruleAssignmentOperator
@init {
}
:
(
	EqualsSignKeyword_0=EqualsSign {
		announce($EqualsSignKeyword_0, grammarAccess.getAssignmentOperatorAccess().getEqualsSignKeyword_0());
	}
	    |
	AsteriskEqualsSignKeyword_1=AsteriskEqualsSign {
		announce($AsteriskEqualsSignKeyword_1, grammarAccess.getAssignmentOperatorAccess().getAsteriskEqualsSignKeyword_1());
	}
	    |
	SolidusEqualsSignKeyword_2=SolidusEqualsSign {
		announce($SolidusEqualsSignKeyword_2, grammarAccess.getAssignmentOperatorAccess().getSolidusEqualsSignKeyword_2());
	}
	    |
	PercentSignEqualsSignKeyword_3=PercentSignEqualsSign {
		announce($PercentSignEqualsSignKeyword_3, grammarAccess.getAssignmentOperatorAccess().getPercentSignEqualsSignKeyword_3());
	}
	    |
	PlusSignEqualsSignKeyword_4=PlusSignEqualsSign {
		announce($PlusSignEqualsSignKeyword_4, grammarAccess.getAssignmentOperatorAccess().getPlusSignEqualsSignKeyword_4());
	}
	    |
	HyphenMinusEqualsSignKeyword_5=HyphenMinusEqualsSign {
		announce($HyphenMinusEqualsSignKeyword_5, grammarAccess.getAssignmentOperatorAccess().getHyphenMinusEqualsSignKeyword_5());
	}
	    |
	LessThanSignLessThanSignEqualsSignKeyword_6=LessThanSignLessThanSignEqualsSign {
		announce($LessThanSignLessThanSignEqualsSignKeyword_6, grammarAccess.getAssignmentOperatorAccess().getLessThanSignLessThanSignEqualsSignKeyword_6());
	}
	    |
	(
		GreaterThanSignKeyword_7_0=GreaterThanSign {
			announce($GreaterThanSignKeyword_7_0, grammarAccess.getAssignmentOperatorAccess().getGreaterThanSignKeyword_7_0());
		}
		GreaterThanSignKeyword_7_1=GreaterThanSign {
			announce($GreaterThanSignKeyword_7_1, grammarAccess.getAssignmentOperatorAccess().getGreaterThanSignKeyword_7_1());
		}
		(
			GreaterThanSignKeyword_7_2=GreaterThanSign {
				announce($GreaterThanSignKeyword_7_2, grammarAccess.getAssignmentOperatorAccess().getGreaterThanSignKeyword_7_2());
			}
		)?
		EqualsSignKeyword_7_3=EqualsSign {
			announce($EqualsSignKeyword_7_3, grammarAccess.getAssignmentOperatorAccess().getEqualsSignKeyword_7_3());
		}
	)
	    |
	AmpersandEqualsSignKeyword_8=AmpersandEqualsSign {
		announce($AmpersandEqualsSignKeyword_8, grammarAccess.getAssignmentOperatorAccess().getAmpersandEqualsSignKeyword_8());
	}
	    |
	CircumflexAccentEqualsSignKeyword_9=CircumflexAccentEqualsSign {
		announce($CircumflexAccentEqualsSignKeyword_9, grammarAccess.getAssignmentOperatorAccess().getCircumflexAccentEqualsSignKeyword_9());
	}
	    |
	VerticalLineEqualsSignKeyword_10=VerticalLineEqualsSign {
		announce($VerticalLineEqualsSignKeyword_10, grammarAccess.getAssignmentOperatorAccess().getVerticalLineEqualsSignKeyword_10());
	}
)
;

// Entry rule entryRuleAwaitExpression
entryRuleAwaitExpression
	:
	ruleAwaitExpression
	EOF;

// Rule AwaitExpression
ruleAwaitExpression
@init {
}:
(
	(
		((
			Await
		)
		)=>
		(
			AwaitKeyword_0_0_1=Await
			 {
				announce($AwaitKeyword_0_0_1, grammarAccess.getAwaitExpressionAccess().getAwaitKeyword_0_0_1());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=ruleAssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getAwaitExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
)
;


// Rule AwaitExpression
norm1_AwaitExpression
@init {
}:
(
	(
		((
			Await
		)
		)=>
		(
			AwaitKeyword_0_0_1=Await
			 {
				announce($AwaitKeyword_0_0_1, grammarAccess.getAwaitExpressionAccess().getAwaitKeyword_0_0_1());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm1_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getAwaitExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
)
;


// Rule AwaitExpression
norm2_AwaitExpression
@init {
}:
(
	(
		((
			Await
		)
		)=>
		(
			AwaitKeyword_0_0_1=Await
			 {
				announce($AwaitKeyword_0_0_1, grammarAccess.getAwaitExpressionAccess().getAwaitKeyword_0_0_1());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm2_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getAwaitExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
)
;


// Rule AwaitExpression
norm3_AwaitExpression
@init {
}:
(
	(
		((
			Await
		)
		)=>
		(
			AwaitKeyword_0_0_1=Await
			 {
				announce($AwaitKeyword_0_0_1, grammarAccess.getAwaitExpressionAccess().getAwaitKeyword_0_0_1());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm3_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getAwaitExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRulePromisifyExpression
entryRulePromisifyExpression
	:
	rulePromisifyExpression
	EOF;

// Rule PromisifyExpression
rulePromisifyExpression
@init {
}:
(
	(
		((
			CommercialAt
			Promisify
		)
		)=>
		(
			CommercialAtKeyword_0_0_1=CommercialAt
			 {
				announce($CommercialAtKeyword_0_0_1, grammarAccess.getPromisifyExpressionAccess().getCommercialAtKeyword_0_0_1());
			}
			PromisifyKeyword_0_0_2=Promisify
			 {
				announce($PromisifyKeyword_0_0_2, grammarAccess.getPromisifyExpressionAccess().getPromisifyKeyword_0_0_2());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=ruleAssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getPromisifyExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
)
;


// Rule PromisifyExpression
norm1_PromisifyExpression
@init {
}:
(
	(
		((
			CommercialAt
			Promisify
		)
		)=>
		(
			CommercialAtKeyword_0_0_1=CommercialAt
			 {
				announce($CommercialAtKeyword_0_0_1, grammarAccess.getPromisifyExpressionAccess().getCommercialAtKeyword_0_0_1());
			}
			PromisifyKeyword_0_0_2=Promisify
			 {
				announce($PromisifyKeyword_0_0_2, grammarAccess.getPromisifyExpressionAccess().getPromisifyKeyword_0_0_2());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm1_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getPromisifyExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
)
;


// Rule PromisifyExpression
norm2_PromisifyExpression
@init {
}:
(
	(
		((
			CommercialAt
			Promisify
		)
		)=>
		(
			CommercialAtKeyword_0_0_1=CommercialAt
			 {
				announce($CommercialAtKeyword_0_0_1, grammarAccess.getPromisifyExpressionAccess().getCommercialAtKeyword_0_0_1());
			}
			PromisifyKeyword_0_0_2=Promisify
			 {
				announce($PromisifyKeyword_0_0_2, grammarAccess.getPromisifyExpressionAccess().getPromisifyKeyword_0_0_2());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm2_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getPromisifyExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
)
;


// Rule PromisifyExpression
norm3_PromisifyExpression
@init {
}:
(
	(
		((
			CommercialAt
			Promisify
		)
		)=>
		(
			CommercialAtKeyword_0_0_1=CommercialAt
			 {
				announce($CommercialAtKeyword_0_0_1, grammarAccess.getPromisifyExpressionAccess().getCommercialAtKeyword_0_0_1());
			}
			PromisifyKeyword_0_0_2=Promisify
			 {
				announce($PromisifyKeyword_0_0_2, grammarAccess.getPromisifyExpressionAccess().getPromisifyKeyword_0_0_2());
			}
		)
	)
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=norm3_AssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getPromisifyExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleExpression
entryRuleExpression
	:
	ruleExpression
	EOF;

// Rule Expression
ruleExpression
@init {
}:
(
	AssignmentExpressionParserRuleCall_0=ruleAssignmentExpression{ announce($AssignmentExpressionParserRuleCall_0.start, $AssignmentExpressionParserRuleCall_0.stop, grammarAccess.getExpressionAccess().getAssignmentExpressionParserRuleCall_0()); }
	(
		CommaKeyword_1_1=Comma
		 {
			announce($CommaKeyword_1_1, grammarAccess.getExpressionAccess().getCommaKeyword_1_1());
		}
		(
			(
				ExprsAssignmentExpressionParserRuleCall_1_2_0=ruleAssignmentExpression{
					announce($ExprsAssignmentExpressionParserRuleCall_1_2_0.start, $ExprsAssignmentExpressionParserRuleCall_1_2_0.stop, grammarAccess.getExpressionAccess().getExprsAssignment_1_2());
				}
			)
		)
		(
			CommaKeyword_1_3_0=Comma
			 {
				announce($CommaKeyword_1_3_0, grammarAccess.getExpressionAccess().getCommaKeyword_1_3_0());
			}
			(
				(
					ExprsAssignmentExpressionParserRuleCall_1_3_1_0=ruleAssignmentExpression{
						announce($ExprsAssignmentExpressionParserRuleCall_1_3_1_0.start, $ExprsAssignmentExpressionParserRuleCall_1_3_1_0.stop, grammarAccess.getExpressionAccess().getExprsAssignment_1_3_1());
					}
				)
			)
		)*
	)?
)
;


// Rule Expression
norm1_Expression
@init {
}:
(
	AssignmentExpressionParserRuleCall_0=norm1_AssignmentExpression{ announce($AssignmentExpressionParserRuleCall_0.start, $AssignmentExpressionParserRuleCall_0.stop, grammarAccess.getExpressionAccess().getAssignmentExpressionParserRuleCall_0()); }
	(
		CommaKeyword_1_1=Comma
		 {
			announce($CommaKeyword_1_1, grammarAccess.getExpressionAccess().getCommaKeyword_1_1());
		}
		(
			(
				ExprsAssignmentExpressionParserRuleCall_1_2_0=norm1_AssignmentExpression{
					announce($ExprsAssignmentExpressionParserRuleCall_1_2_0.start, $ExprsAssignmentExpressionParserRuleCall_1_2_0.stop, grammarAccess.getExpressionAccess().getExprsAssignment_1_2());
				}
			)
		)
		(
			CommaKeyword_1_3_0=Comma
			 {
				announce($CommaKeyword_1_3_0, grammarAccess.getExpressionAccess().getCommaKeyword_1_3_0());
			}
			(
				(
					ExprsAssignmentExpressionParserRuleCall_1_3_1_0=norm1_AssignmentExpression{
						announce($ExprsAssignmentExpressionParserRuleCall_1_3_1_0.start, $ExprsAssignmentExpressionParserRuleCall_1_3_1_0.stop, grammarAccess.getExpressionAccess().getExprsAssignment_1_3_1());
					}
				)
			)
		)*
	)?
)
;


// Rule Expression
norm2_Expression
@init {
}:
(
	AssignmentExpressionParserRuleCall_0=norm2_AssignmentExpression{ announce($AssignmentExpressionParserRuleCall_0.start, $AssignmentExpressionParserRuleCall_0.stop, grammarAccess.getExpressionAccess().getAssignmentExpressionParserRuleCall_0()); }
	(
		CommaKeyword_1_1=Comma
		 {
			announce($CommaKeyword_1_1, grammarAccess.getExpressionAccess().getCommaKeyword_1_1());
		}
		(
			(
				ExprsAssignmentExpressionParserRuleCall_1_2_0=norm2_AssignmentExpression{
					announce($ExprsAssignmentExpressionParserRuleCall_1_2_0.start, $ExprsAssignmentExpressionParserRuleCall_1_2_0.stop, grammarAccess.getExpressionAccess().getExprsAssignment_1_2());
				}
			)
		)
		(
			CommaKeyword_1_3_0=Comma
			 {
				announce($CommaKeyword_1_3_0, grammarAccess.getExpressionAccess().getCommaKeyword_1_3_0());
			}
			(
				(
					ExprsAssignmentExpressionParserRuleCall_1_3_1_0=norm2_AssignmentExpression{
						announce($ExprsAssignmentExpressionParserRuleCall_1_3_1_0.start, $ExprsAssignmentExpressionParserRuleCall_1_3_1_0.stop, grammarAccess.getExpressionAccess().getExprsAssignment_1_3_1());
					}
				)
			)
		)*
	)?
)
;


// Rule Expression
norm3_Expression
@init {
}:
(
	AssignmentExpressionParserRuleCall_0=norm3_AssignmentExpression{ announce($AssignmentExpressionParserRuleCall_0.start, $AssignmentExpressionParserRuleCall_0.stop, grammarAccess.getExpressionAccess().getAssignmentExpressionParserRuleCall_0()); }
	(
		CommaKeyword_1_1=Comma
		 {
			announce($CommaKeyword_1_1, grammarAccess.getExpressionAccess().getCommaKeyword_1_1());
		}
		(
			(
				ExprsAssignmentExpressionParserRuleCall_1_2_0=norm3_AssignmentExpression{
					announce($ExprsAssignmentExpressionParserRuleCall_1_2_0.start, $ExprsAssignmentExpressionParserRuleCall_1_2_0.stop, grammarAccess.getExpressionAccess().getExprsAssignment_1_2());
				}
			)
		)
		(
			CommaKeyword_1_3_0=Comma
			 {
				announce($CommaKeyword_1_3_0, grammarAccess.getExpressionAccess().getCommaKeyword_1_3_0());
			}
			(
				(
					ExprsAssignmentExpressionParserRuleCall_1_3_1_0=norm3_AssignmentExpression{
						announce($ExprsAssignmentExpressionParserRuleCall_1_3_1_0.start, $ExprsAssignmentExpressionParserRuleCall_1_3_1_0.stop, grammarAccess.getExpressionAccess().getExprsAssignment_1_3_1());
					}
				)
			)
		)*
	)?
)
;

// Entry rule entryRuleTemplateLiteral
entryRuleTemplateLiteral
	:
	ruleTemplateLiteral
	EOF;

// Rule TemplateLiteral
ruleTemplateLiteral
@init {
}:
(
	(
		(
			(
				SegmentsNoSubstitutionTemplateParserRuleCall_1_0_0=ruleNoSubstitutionTemplate{
					announce($SegmentsNoSubstitutionTemplateParserRuleCall_1_0_0.start, $SegmentsNoSubstitutionTemplateParserRuleCall_1_0_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_0());
				}
			)
		)
		    |
		(
			(
				(
					SegmentsTemplateHeadParserRuleCall_1_1_0_0=ruleTemplateHead{
						announce($SegmentsTemplateHeadParserRuleCall_1_1_0_0.start, $SegmentsTemplateHeadParserRuleCall_1_1_0_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_0());
					}
				)
			)
			(
				(
					SegmentsExpressionParserRuleCall_1_1_1_0=norm1_Expression{
						announce($SegmentsExpressionParserRuleCall_1_1_1_0.start, $SegmentsExpressionParserRuleCall_1_1_1_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_1());
					}
				)
			)?
			TemplateExpressionEndParserRuleCall_1_1_2=ruleTemplateExpressionEnd{ announce($TemplateExpressionEndParserRuleCall_1_1_2.start, $TemplateExpressionEndParserRuleCall_1_1_2.stop, grammarAccess.getTemplateLiteralAccess().getTemplateExpressionEndParserRuleCall_1_1_2()); }
			(
				(
					(
						SegmentsTemplateMiddleParserRuleCall_1_1_3_0_0=ruleTemplateMiddle{
							announce($SegmentsTemplateMiddleParserRuleCall_1_1_3_0_0.start, $SegmentsTemplateMiddleParserRuleCall_1_1_3_0_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_3_0());
						}
					)
				)
				(
					(
						SegmentsExpressionParserRuleCall_1_1_3_1_0=norm1_Expression{
							announce($SegmentsExpressionParserRuleCall_1_1_3_1_0.start, $SegmentsExpressionParserRuleCall_1_1_3_1_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_3_1());
						}
					)
				)?
				TemplateExpressionEndParserRuleCall_1_1_3_2=ruleTemplateExpressionEnd{ announce($TemplateExpressionEndParserRuleCall_1_1_3_2.start, $TemplateExpressionEndParserRuleCall_1_1_3_2.stop, grammarAccess.getTemplateLiteralAccess().getTemplateExpressionEndParserRuleCall_1_1_3_2()); }
			)*
			(
				(
					SegmentsTemplateTailParserRuleCall_1_1_4_0=ruleTemplateTail{
						announce($SegmentsTemplateTailParserRuleCall_1_1_4_0.start, $SegmentsTemplateTailParserRuleCall_1_1_4_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_4());
					}
				)
			)
		)
	)
)
;


// Rule TemplateLiteral
norm1_TemplateLiteral
@init {
}:
(
	(
		(
			(
				SegmentsNoSubstitutionTemplateParserRuleCall_1_0_0=ruleNoSubstitutionTemplate{
					announce($SegmentsNoSubstitutionTemplateParserRuleCall_1_0_0.start, $SegmentsNoSubstitutionTemplateParserRuleCall_1_0_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_0());
				}
			)
		)
		    |
		(
			(
				(
					SegmentsTemplateHeadParserRuleCall_1_1_0_0=ruleTemplateHead{
						announce($SegmentsTemplateHeadParserRuleCall_1_1_0_0.start, $SegmentsTemplateHeadParserRuleCall_1_1_0_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_0());
					}
				)
			)
			(
				(
					SegmentsExpressionParserRuleCall_1_1_1_0=norm3_Expression{
						announce($SegmentsExpressionParserRuleCall_1_1_1_0.start, $SegmentsExpressionParserRuleCall_1_1_1_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_1());
					}
				)
			)?
			TemplateExpressionEndParserRuleCall_1_1_2=ruleTemplateExpressionEnd{ announce($TemplateExpressionEndParserRuleCall_1_1_2.start, $TemplateExpressionEndParserRuleCall_1_1_2.stop, grammarAccess.getTemplateLiteralAccess().getTemplateExpressionEndParserRuleCall_1_1_2()); }
			(
				(
					(
						SegmentsTemplateMiddleParserRuleCall_1_1_3_0_0=ruleTemplateMiddle{
							announce($SegmentsTemplateMiddleParserRuleCall_1_1_3_0_0.start, $SegmentsTemplateMiddleParserRuleCall_1_1_3_0_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_3_0());
						}
					)
				)
				(
					(
						SegmentsExpressionParserRuleCall_1_1_3_1_0=norm3_Expression{
							announce($SegmentsExpressionParserRuleCall_1_1_3_1_0.start, $SegmentsExpressionParserRuleCall_1_1_3_1_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_3_1());
						}
					)
				)?
				TemplateExpressionEndParserRuleCall_1_1_3_2=ruleTemplateExpressionEnd{ announce($TemplateExpressionEndParserRuleCall_1_1_3_2.start, $TemplateExpressionEndParserRuleCall_1_1_3_2.stop, grammarAccess.getTemplateLiteralAccess().getTemplateExpressionEndParserRuleCall_1_1_3_2()); }
			)*
			(
				(
					SegmentsTemplateTailParserRuleCall_1_1_4_0=ruleTemplateTail{
						announce($SegmentsTemplateTailParserRuleCall_1_1_4_0.start, $SegmentsTemplateTailParserRuleCall_1_1_4_0.stop, grammarAccess.getTemplateLiteralAccess().getSegmentsAssignment_1_1_4());
					}
				)
			)
		)
	)
)
;

// Entry rule entryRuleTemplateExpressionEnd
entryRuleTemplateExpressionEnd
	:
	ruleTemplateExpressionEnd
	EOF;

// Rule TemplateExpressionEnd
ruleTemplateExpressionEnd
@init {
	setInTemplateSegment();
}
:
RightCurlyBracketKeyword=RightCurlyBracket {
	announce($RightCurlyBracketKeyword, grammarAccess.getTemplateExpressionEndAccess().getRightCurlyBracketKeyword());
}
;

// Entry rule entryRuleNoSubstitutionTemplate
entryRuleNoSubstitutionTemplate
	:
	ruleNoSubstitutionTemplate
	EOF;

// Rule NoSubstitutionTemplate
ruleNoSubstitutionTemplate
@init {
}:
(
	(
		(
			ValueNO_SUBSTITUTION_TEMPLATE_LITERALTerminalRuleCall_1_0=RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL{
				announce($ValueNO_SUBSTITUTION_TEMPLATE_LITERALTerminalRuleCall_1_0, grammarAccess.getNoSubstitutionTemplateAccess().getValueAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleTemplateHead
entryRuleTemplateHead
	:
	ruleTemplateHead
	EOF;

// Rule TemplateHead
ruleTemplateHead
@init {
}:
(
	(
		(
			ValueTEMPLATE_HEADTerminalRuleCall_1_0=RULE_TEMPLATE_HEAD{
				announce($ValueTEMPLATE_HEADTerminalRuleCall_1_0, grammarAccess.getTemplateHeadAccess().getValueAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleTemplateTail
entryRuleTemplateTail
	:
	ruleTemplateTail
	EOF;

// Rule TemplateTail
ruleTemplateTail
@init {
}:
(
	(
		(
			ValueTemplateTailLiteralParserRuleCall_1_0=ruleTemplateTailLiteral{
				announce($ValueTemplateTailLiteralParserRuleCall_1_0.start, $ValueTemplateTailLiteralParserRuleCall_1_0.stop, grammarAccess.getTemplateTailAccess().getValueAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleTemplateMiddle
entryRuleTemplateMiddle
	:
	ruleTemplateMiddle
	EOF;

// Rule TemplateMiddle
ruleTemplateMiddle
@init {
}:
(
	(
		(
			ValueTemplateMiddleLiteralParserRuleCall_1_0=ruleTemplateMiddleLiteral{
				announce($ValueTemplateMiddleLiteralParserRuleCall_1_0.start, $ValueTemplateMiddleLiteralParserRuleCall_1_0.stop, grammarAccess.getTemplateMiddleAccess().getValueAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleLiteral
entryRuleLiteral
	:
	ruleLiteral
	EOF;

// Rule Literal
ruleLiteral
@init {
}:
(
	NumericLiteralParserRuleCall_0=ruleNumericLiteral{ announce($NumericLiteralParserRuleCall_0.start, $NumericLiteralParserRuleCall_0.stop, grammarAccess.getLiteralAccess().getNumericLiteralParserRuleCall_0()); }
	    |
	BooleanLiteralParserRuleCall_1=ruleBooleanLiteral{ announce($BooleanLiteralParserRuleCall_1.start, $BooleanLiteralParserRuleCall_1.stop, grammarAccess.getLiteralAccess().getBooleanLiteralParserRuleCall_1()); }
	    |
	StringLiteralParserRuleCall_2=ruleStringLiteral{ announce($StringLiteralParserRuleCall_2.start, $StringLiteralParserRuleCall_2.stop, grammarAccess.getLiteralAccess().getStringLiteralParserRuleCall_2()); }
	    |
	NullLiteralParserRuleCall_3=ruleNullLiteral{ announce($NullLiteralParserRuleCall_3.start, $NullLiteralParserRuleCall_3.stop, grammarAccess.getLiteralAccess().getNullLiteralParserRuleCall_3()); }
	    |
	RegularExpressionLiteralParserRuleCall_4=ruleRegularExpressionLiteral{ announce($RegularExpressionLiteralParserRuleCall_4.start, $RegularExpressionLiteralParserRuleCall_4.stop, grammarAccess.getLiteralAccess().getRegularExpressionLiteralParserRuleCall_4()); }
)
;

// Entry rule entryRuleNullLiteral
entryRuleNullLiteral
	:
	ruleNullLiteral
	EOF;

// Rule NullLiteral
ruleNullLiteral
@init {
}:
(
	NullKeyword_1=Null
	 {
		announce($NullKeyword_1, grammarAccess.getNullLiteralAccess().getNullKeyword_1());
	}
)
;

// Entry rule entryRuleBooleanLiteral
entryRuleBooleanLiteral
	:
	ruleBooleanLiteral
	EOF;

// Rule BooleanLiteral
ruleBooleanLiteral
@init {
}:
(
	(
		(
			(
				TrueTrueKeyword_1_0_0=True
				 {
					announce($TrueTrueKeyword_1_0_0, grammarAccess.getBooleanLiteralAccess().getTrueTrueKeyword_1_0_0());
				}
			)
		)
		    |
		FalseKeyword_1_1=False
		 {
			announce($FalseKeyword_1_1, grammarAccess.getBooleanLiteralAccess().getFalseKeyword_1_1());
		}
	)
)
;

// Entry rule entryRuleStringLiteral
entryRuleStringLiteral
	:
	ruleStringLiteral
	EOF;

// Rule StringLiteral
ruleStringLiteral
@init {
}:
(
	(
		ValueSTRINGTerminalRuleCall_0=RULE_STRING{
			announce($ValueSTRINGTerminalRuleCall_0, grammarAccess.getStringLiteralAccess().getValueAssignment());
		}
	)
)
;

// Entry rule entryRuleNumericLiteral
entryRuleNumericLiteral
	:
	ruleNumericLiteral
	EOF;

// Rule NumericLiteral
ruleNumericLiteral
@init {
}:
(
	DoubleLiteralParserRuleCall_0=ruleDoubleLiteral{ announce($DoubleLiteralParserRuleCall_0.start, $DoubleLiteralParserRuleCall_0.stop, grammarAccess.getNumericLiteralAccess().getDoubleLiteralParserRuleCall_0()); }
	    |
	IntLiteralParserRuleCall_1=ruleIntLiteral{ announce($IntLiteralParserRuleCall_1.start, $IntLiteralParserRuleCall_1.stop, grammarAccess.getNumericLiteralAccess().getIntLiteralParserRuleCall_1()); }
	    |
	BinaryIntLiteralParserRuleCall_2=ruleBinaryIntLiteral{ announce($BinaryIntLiteralParserRuleCall_2.start, $BinaryIntLiteralParserRuleCall_2.stop, grammarAccess.getNumericLiteralAccess().getBinaryIntLiteralParserRuleCall_2()); }
	    |
	OctalIntLiteralParserRuleCall_3=ruleOctalIntLiteral{ announce($OctalIntLiteralParserRuleCall_3.start, $OctalIntLiteralParserRuleCall_3.stop, grammarAccess.getNumericLiteralAccess().getOctalIntLiteralParserRuleCall_3()); }
	    |
	LegacyOctalIntLiteralParserRuleCall_4=ruleLegacyOctalIntLiteral{ announce($LegacyOctalIntLiteralParserRuleCall_4.start, $LegacyOctalIntLiteralParserRuleCall_4.stop, grammarAccess.getNumericLiteralAccess().getLegacyOctalIntLiteralParserRuleCall_4()); }
	    |
	HexIntLiteralParserRuleCall_5=ruleHexIntLiteral{ announce($HexIntLiteralParserRuleCall_5.start, $HexIntLiteralParserRuleCall_5.stop, grammarAccess.getNumericLiteralAccess().getHexIntLiteralParserRuleCall_5()); }
	    |
	ScientificIntLiteralParserRuleCall_6=ruleScientificIntLiteral{ announce($ScientificIntLiteralParserRuleCall_6.start, $ScientificIntLiteralParserRuleCall_6.stop, grammarAccess.getNumericLiteralAccess().getScientificIntLiteralParserRuleCall_6()); }
)
;

// Entry rule entryRuleDoubleLiteral
entryRuleDoubleLiteral
	:
	ruleDoubleLiteral
	EOF;

// Rule DoubleLiteral
ruleDoubleLiteral
@init {
}:
(
	(
		ValueDOUBLETerminalRuleCall_0=RULE_DOUBLE{
			announce($ValueDOUBLETerminalRuleCall_0, grammarAccess.getDoubleLiteralAccess().getValueAssignment());
		}
	)
)
;

// Entry rule entryRuleIntLiteral
entryRuleIntLiteral
	:
	ruleIntLiteral
	EOF;

// Rule IntLiteral
ruleIntLiteral
@init {
}:
(
	(
		ValueINTTerminalRuleCall_0=RULE_INT{
			announce($ValueINTTerminalRuleCall_0, grammarAccess.getIntLiteralAccess().getValueAssignment());
		}
	)
)
;

// Entry rule entryRuleOctalIntLiteral
entryRuleOctalIntLiteral
	:
	ruleOctalIntLiteral
	EOF;

// Rule OctalIntLiteral
ruleOctalIntLiteral
@init {
}:
(
	(
		ValueOCTAL_INTTerminalRuleCall_0=RULE_OCTAL_INT{
			announce($ValueOCTAL_INTTerminalRuleCall_0, grammarAccess.getOctalIntLiteralAccess().getValueAssignment());
		}
	)
)
;

// Entry rule entryRuleLegacyOctalIntLiteral
entryRuleLegacyOctalIntLiteral
	:
	ruleLegacyOctalIntLiteral
	EOF;

// Rule LegacyOctalIntLiteral
ruleLegacyOctalIntLiteral
@init {
}:
(
	(
		ValueLEGACY_OCTAL_INTTerminalRuleCall_0=RULE_LEGACY_OCTAL_INT{
			announce($ValueLEGACY_OCTAL_INTTerminalRuleCall_0, grammarAccess.getLegacyOctalIntLiteralAccess().getValueAssignment());
		}
	)
)
;

// Entry rule entryRuleHexIntLiteral
entryRuleHexIntLiteral
	:
	ruleHexIntLiteral
	EOF;

// Rule HexIntLiteral
ruleHexIntLiteral
@init {
}:
(
	(
		ValueHEX_INTTerminalRuleCall_0=RULE_HEX_INT{
			announce($ValueHEX_INTTerminalRuleCall_0, grammarAccess.getHexIntLiteralAccess().getValueAssignment());
		}
	)
)
;

// Entry rule entryRuleBinaryIntLiteral
entryRuleBinaryIntLiteral
	:
	ruleBinaryIntLiteral
	EOF;

// Rule BinaryIntLiteral
ruleBinaryIntLiteral
@init {
}:
(
	(
		ValueBINARY_INTTerminalRuleCall_0=RULE_BINARY_INT{
			announce($ValueBINARY_INTTerminalRuleCall_0, grammarAccess.getBinaryIntLiteralAccess().getValueAssignment());
		}
	)
)
;

// Entry rule entryRuleScientificIntLiteral
entryRuleScientificIntLiteral
	:
	ruleScientificIntLiteral
	EOF;

// Rule ScientificIntLiteral
ruleScientificIntLiteral
@init {
}:
(
	(
		ValueSCIENTIFIC_INTTerminalRuleCall_0=RULE_SCIENTIFIC_INT{
			announce($ValueSCIENTIFIC_INTTerminalRuleCall_0, grammarAccess.getScientificIntLiteralAccess().getValueAssignment());
		}
	)
)
;

// Entry rule entryRuleRegularExpressionLiteral
entryRuleRegularExpressionLiteral
	:
	ruleRegularExpressionLiteral
	EOF;

// Rule RegularExpressionLiteral
ruleRegularExpressionLiteral
@init {
}:
(
	(
		ValueREGEX_LITERALParserRuleCall_0=ruleREGEX_LITERAL{
			announce($ValueREGEX_LITERALParserRuleCall_0.start, $ValueREGEX_LITERALParserRuleCall_0.stop, grammarAccess.getRegularExpressionLiteralAccess().getValueAssignment());
		}
	)
)
;

// Entry rule entryRuleNumericLiteralAsString
entryRuleNumericLiteralAsString
	:
	ruleNumericLiteralAsString
	EOF;

// Rule NumericLiteralAsString
ruleNumericLiteralAsString
@init {
}
:
(
	RULE_DOUBLE
	    |
	RULE_INT
	    |
	RULE_OCTAL_INT
	    |
	RULE_HEX_INT
	    |
	RULE_SCIENTIFIC_INT
)
;

// Entry rule entryRuleIdentifierOrThis
entryRuleIdentifierOrThis
	:
	ruleIdentifierOrThis
	EOF;

// Rule IdentifierOrThis
ruleIdentifierOrThis
@init {
}
:
(
	RULE_IDENTIFIER
	    |
	ThisKeyword_1=This {
		announce($ThisKeyword_1, grammarAccess.getIdentifierOrThisAccess().getThisKeyword_1());
	}
	    |
	PromisifyKeyword_2=Promisify {
		announce($PromisifyKeyword_2, grammarAccess.getIdentifierOrThisAccess().getPromisifyKeyword_2());
	}
	    |
	TargetKeyword_3=Target {
		announce($TargetKeyword_3, grammarAccess.getIdentifierOrThisAccess().getTargetKeyword_3());
	}
)
;

// Entry rule entryRuleAnnotationName
entryRuleAnnotationName
	:
	ruleAnnotationName
	EOF;

// Rule AnnotationName
ruleAnnotationName
@init {
}
:
(
	RULE_IDENTIFIER
	    |
	ThisKeyword_1=This {
		announce($ThisKeyword_1, grammarAccess.getAnnotationNameAccess().getThisKeyword_1());
	}
	    |
	TargetKeyword_2=Target {
		announce($TargetKeyword_2, grammarAccess.getAnnotationNameAccess().getTargetKeyword_2());
	}
)
;

// Entry rule entryRuleREGEX_LITERAL
entryRuleREGEX_LITERAL
	:
	ruleREGEX_LITERAL
	EOF;

// Rule REGEX_LITERAL
ruleREGEX_LITERAL
@init {
	setInRegularExpression();
}
:
(
	(
		SolidusKeyword_0_0=Solidus {
			announce($SolidusKeyword_0_0, grammarAccess.getREGEX_LITERALAccess().getSolidusKeyword_0_0());
		}
		    |
		SolidusEqualsSignKeyword_0_1=SolidusEqualsSign {
			announce($SolidusEqualsSignKeyword_0_1, grammarAccess.getREGEX_LITERALAccess().getSolidusEqualsSignKeyword_0_1());
		}
	)
	(
		RULE_REGEX_TAIL
	)?
)
;

// Entry rule entryRuleTemplateTailLiteral
entryRuleTemplateTailLiteral
	:
	ruleTemplateTailLiteral
	EOF;

// Rule TemplateTailLiteral
ruleTemplateTailLiteral
@init {
}
:
(
	RULE_TEMPLATE_END
)?
;

// Entry rule entryRuleTemplateMiddleLiteral
entryRuleTemplateMiddleLiteral
	:
	ruleTemplateMiddleLiteral
	EOF;

// Rule TemplateMiddleLiteral
ruleTemplateMiddleLiteral
@init {
}
:
RULE_TEMPLATE_MIDDLE
;

// Entry rule entryRuleSemi
entryRuleSemi
	:
	ruleSemi
	EOF;

// Rule Semi
ruleSemi
@init {
	int marker = input.mark();
	// Promote EOL if appropriate
	promoteEOL();
}
:
SemicolonKeyword=Semicolon {
	announce($SemicolonKeyword, grammarAccess.getSemiAccess().getSemicolonKeyword());
}
| EOF
| RULE_EOL
| RULE_ML_COMMENT
| RightCurlyBracket { forcedRewind(marker) }?
;


// Rule NoLineTerminator
ruleNoLineTerminator
@init {
}:
(
	NO_LINE_TERMINATORTerminalRuleCall=RULE_NO_LINE_TERMINATOR
	{ announce($NO_LINE_TERMINATORTerminalRuleCall, grammarAccess.getNoLineTerminatorAccess().getNO_LINE_TERMINATORTerminalRuleCall()); }
)?
;

// Entry rule entryRuleAnnotation
entryRuleAnnotation
	:
	ruleAnnotation
	EOF;

// Rule Annotation
ruleAnnotation
@init {
}:
(
	CommercialAtKeyword_0=CommercialAt
	 {
		announce($CommercialAtKeyword_0, grammarAccess.getAnnotationAccess().getCommercialAtKeyword_0());
	}
	AnnotationNoAtSignParserRuleCall_1=ruleAnnotationNoAtSign{ announce($AnnotationNoAtSignParserRuleCall_1.start, $AnnotationNoAtSignParserRuleCall_1.stop, grammarAccess.getAnnotationAccess().getAnnotationNoAtSignParserRuleCall_1()); }
)
;

// Entry rule entryRuleScriptAnnotation
entryRuleScriptAnnotation
	:
	ruleScriptAnnotation
	EOF;

// Rule ScriptAnnotation
ruleScriptAnnotation
@init {
}:
(
	CommercialAtCommercialAtKeyword_0=CommercialAtCommercialAt
	 {
		announce($CommercialAtCommercialAtKeyword_0, grammarAccess.getScriptAnnotationAccess().getCommercialAtCommercialAtKeyword_0());
	}
	AnnotationNoAtSignParserRuleCall_1=ruleAnnotationNoAtSign{ announce($AnnotationNoAtSignParserRuleCall_1.start, $AnnotationNoAtSignParserRuleCall_1.stop, grammarAccess.getScriptAnnotationAccess().getAnnotationNoAtSignParserRuleCall_1()); }
)
;

// Entry rule entryRuleAnnotationNoAtSign
entryRuleAnnotationNoAtSign
	:
	ruleAnnotationNoAtSign
	EOF;

// Rule AnnotationNoAtSign
ruleAnnotationNoAtSign
@init {
}:
(
	(
		(
			NameAnnotationNameParserRuleCall_0_0=ruleAnnotationName{
				announce($NameAnnotationNameParserRuleCall_0_0.start, $NameAnnotationNameParserRuleCall_0_0.stop, grammarAccess.getAnnotationNoAtSignAccess().getNameAssignment_0());
			}
		)
	)
	(
		(
			(LeftParenthesis
			)=>
			LeftParenthesisKeyword_1_0=LeftParenthesis
			 {
				announce($LeftParenthesisKeyword_1_0, grammarAccess.getAnnotationNoAtSignAccess().getLeftParenthesisKeyword_1_0());
			}
		)
		(
			(
				(
					ArgsAnnotationArgumentParserRuleCall_1_1_0_0=ruleAnnotationArgument{
						announce($ArgsAnnotationArgumentParserRuleCall_1_1_0_0.start, $ArgsAnnotationArgumentParserRuleCall_1_1_0_0.stop, grammarAccess.getAnnotationNoAtSignAccess().getArgsAssignment_1_1_0());
					}
				)
			)
			(
				CommaKeyword_1_1_1_0=Comma
				 {
					announce($CommaKeyword_1_1_1_0, grammarAccess.getAnnotationNoAtSignAccess().getCommaKeyword_1_1_1_0());
				}
				(
					(
						ArgsAnnotationArgumentParserRuleCall_1_1_1_1_0=ruleAnnotationArgument{
							announce($ArgsAnnotationArgumentParserRuleCall_1_1_1_1_0.start, $ArgsAnnotationArgumentParserRuleCall_1_1_1_1_0.stop, grammarAccess.getAnnotationNoAtSignAccess().getArgsAssignment_1_1_1_1());
						}
					)
				)
			)*
		)?
		RightParenthesisKeyword_1_2=RightParenthesis
		 {
			announce($RightParenthesisKeyword_1_2, grammarAccess.getAnnotationNoAtSignAccess().getRightParenthesisKeyword_1_2());
		}
	)?
)
;

// Entry rule entryRuleAnnotationArgument
entryRuleAnnotationArgument
	:
	ruleAnnotationArgument
	EOF;

// Rule AnnotationArgument
ruleAnnotationArgument
@init {
}:
(
	LiteralAnnotationArgumentParserRuleCall_0=ruleLiteralAnnotationArgument{ announce($LiteralAnnotationArgumentParserRuleCall_0.start, $LiteralAnnotationArgumentParserRuleCall_0.stop, grammarAccess.getAnnotationArgumentAccess().getLiteralAnnotationArgumentParserRuleCall_0()); }
	    |
	TypeRefAnnotationArgumentParserRuleCall_1=ruleTypeRefAnnotationArgument{ announce($TypeRefAnnotationArgumentParserRuleCall_1.start, $TypeRefAnnotationArgumentParserRuleCall_1.stop, grammarAccess.getAnnotationArgumentAccess().getTypeRefAnnotationArgumentParserRuleCall_1()); }
)
;

// Entry rule entryRuleLiteralAnnotationArgument
entryRuleLiteralAnnotationArgument
	:
	ruleLiteralAnnotationArgument
	EOF;

// Rule LiteralAnnotationArgument
ruleLiteralAnnotationArgument
@init {
}:
(
	(
		LiteralLiteralParserRuleCall_0=ruleLiteral{
			announce($LiteralLiteralParserRuleCall_0.start, $LiteralLiteralParserRuleCall_0.stop, grammarAccess.getLiteralAnnotationArgumentAccess().getLiteralAssignment());
		}
	)
)
;

// Entry rule entryRuleTypeRefAnnotationArgument
entryRuleTypeRefAnnotationArgument
	:
	ruleTypeRefAnnotationArgument
	EOF;

// Rule TypeRefAnnotationArgument
ruleTypeRefAnnotationArgument
@init {
}:
(
	(
		TypeRefTypeRefParserRuleCall_0=ruleTypeRef{
			announce($TypeRefTypeRefParserRuleCall_0.start, $TypeRefTypeRefParserRuleCall_0.stop, grammarAccess.getTypeRefAnnotationArgumentAccess().getTypeRefAssignment());
		}
	)
)
;

// Entry rule entryRuleAnnotationList
entryRuleAnnotationList
	:
	ruleAnnotationList
	EOF;

// Rule AnnotationList
ruleAnnotationList
@init {
}:
(
	(
		((
			CommercialAt
			(
				This
				    |
				Target
				    |
				RULE_IDENTIFIER
			)
		)
		)=>
		(
			CommercialAtKeyword_0_0_1=CommercialAt
			 {
				announce($CommercialAtKeyword_0_0_1, grammarAccess.getAnnotationListAccess().getCommercialAtKeyword_0_0_1());
			}
			(
				(This | 
				Target | 
				RULE_IDENTIFIER)=>
				(
					AnnotationsAnnotationNoAtSignParserRuleCall_0_0_2_0=ruleAnnotationNoAtSign{
						announce($AnnotationsAnnotationNoAtSignParserRuleCall_0_0_2_0.start, $AnnotationsAnnotationNoAtSignParserRuleCall_0_0_2_0.stop, grammarAccess.getAnnotationListAccess().getAnnotationsAssignment_0_0_2());
					}
				)
			)
		)
	)
	(
		(
			AnnotationsAnnotationParserRuleCall_1_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_1_0.start, $AnnotationsAnnotationParserRuleCall_1_0.stop, grammarAccess.getAnnotationListAccess().getAnnotationsAssignment_1());
			}
		)
	)*
)
;

// Entry rule entryRuleExpressionAnnotationList
entryRuleExpressionAnnotationList
	:
	ruleExpressionAnnotationList
	EOF;

// Rule ExpressionAnnotationList
ruleExpressionAnnotationList
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_1_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_1_0.start, $AnnotationsAnnotationParserRuleCall_1_0.stop, grammarAccess.getExpressionAnnotationListAccess().getAnnotationsAssignment_1());
			}
		)
	)+
)
;

// Entry rule entryRulePropertyAssignmentAnnotationList
entryRulePropertyAssignmentAnnotationList
	:
	rulePropertyAssignmentAnnotationList
	EOF;

// Rule PropertyAssignmentAnnotationList
rulePropertyAssignmentAnnotationList
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_1_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_1_0.start, $AnnotationsAnnotationParserRuleCall_1_0.stop, grammarAccess.getPropertyAssignmentAnnotationListAccess().getAnnotationsAssignment_1());
			}
		)
	)+
)
;

// Entry rule entryRuleN4MemberAnnotationList
entryRuleN4MemberAnnotationList
	:
	ruleN4MemberAnnotationList
	EOF;

// Rule N4MemberAnnotationList
ruleN4MemberAnnotationList
@init {
}:
(
	(
		(
			AnnotationsAnnotationParserRuleCall_1_0=ruleAnnotation{
				announce($AnnotationsAnnotationParserRuleCall_1_0.start, $AnnotationsAnnotationParserRuleCall_1_0.stop, grammarAccess.getN4MemberAnnotationListAccess().getAnnotationsAssignment_1());
			}
		)
	)+
)
;


// Rule TypeReference
ruleTypeReference
@init {
}:
(
	(
		(
			(
				AstNamespaceModuleNamespaceVirtualTypeTypeReferenceNameParserRuleCall_0_0_0_1=ruleTypeReferenceName{
					announce($AstNamespaceModuleNamespaceVirtualTypeTypeReferenceNameParserRuleCall_0_0_0_1.start, $AstNamespaceModuleNamespaceVirtualTypeTypeReferenceNameParserRuleCall_0_0_0_1.stop, grammarAccess.getTypeReferenceAccess().getAstNamespaceAssignment_0_0());
				}
			)
		)
		FullStopKeyword_0_1=FullStop
		 {
			announce($FullStopKeyword_0_1, grammarAccess.getTypeReferenceAccess().getFullStopKeyword_0_1());
		}
	)?
	(
		(
			DeclaredTypeTypeTypeReferenceNameParserRuleCall_1_0_1=ruleTypeReferenceName{
				announce($DeclaredTypeTypeTypeReferenceNameParserRuleCall_1_0_1.start, $DeclaredTypeTypeTypeReferenceNameParserRuleCall_1_0_1.stop, grammarAccess.getTypeReferenceAccess().getDeclaredTypeAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleTypeReferenceName
entryRuleTypeReferenceName
	:
	ruleTypeReferenceName
	EOF;

// Rule TypeReferenceName
ruleTypeReferenceName
@init {
}
:
(
	VoidKeyword_0=Void {
		announce($VoidKeyword_0, grammarAccess.getTypeReferenceNameAccess().getVoidKeyword_0());
	}
	    |
	ThisKeyword_1=This {
		announce($ThisKeyword_1, grammarAccess.getTypeReferenceNameAccess().getThisKeyword_1());
	}
	    |
	AwaitKeyword_2=Await {
		announce($AwaitKeyword_2, grammarAccess.getTypeReferenceNameAccess().getAwaitKeyword_2());
	}
	    |
	PromisifyKeyword_3=Promisify {
		announce($PromisifyKeyword_3, grammarAccess.getTypeReferenceNameAccess().getPromisifyKeyword_3());
	}
	    |
	TargetKeyword_4=Target {
		announce($TargetKeyword_4, grammarAccess.getTypeReferenceNameAccess().getTargetKeyword_4());
	}
	    |
	DefaultKeyword_5=Default {
		announce($DefaultKeyword_5, grammarAccess.getTypeReferenceNameAccess().getDefaultKeyword_5());
	}
	    |
	RULE_IDENTIFIER
)
;

// Entry rule entryRuleN4ClassDeclaration
entryRuleN4ClassDeclaration
	:
	ruleN4ClassDeclaration
	EOF;

// Rule N4ClassDeclaration
ruleN4ClassDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Class
			(
				(
					ruleTypingStrategyDefSiteOperator
				)
			)?
			(
				(
					ruleBindingIdentifier
				)
			)?
			(
				ruleVersionDeclaration
			)?
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			ClassKeyword_0_0_1=Class
			 {
				announce($ClassKeyword_0_0_1, grammarAccess.getN4ClassDeclarationAccess().getClassKeyword_0_0_1());
			}
			(
				(
					TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0=ruleTypingStrategyDefSiteOperator{
						announce($TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0.start, $TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0.stop, grammarAccess.getN4ClassDeclarationAccess().getTypingStrategyAssignment_0_0_2());
					}
				)
			)?
			(
				(
					NameBindingIdentifierParserRuleCall_0_0_3_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_0_0_3_0.start, $NameBindingIdentifierParserRuleCall_0_0_3_0.stop, grammarAccess.getN4ClassDeclarationAccess().getNameAssignment_0_0_3());
					}
				)
			)?
			(
				VersionDeclarationParserRuleCall_0_0_4=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_0_0_4.start, $VersionDeclarationParserRuleCall_0_0_4.stop, grammarAccess.getN4ClassDeclarationAccess().getVersionDeclarationParserRuleCall_0_0_4()); }
			)?
		)
	)
	(
		TypeVariablesParserRuleCall_1=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1.start, $TypeVariablesParserRuleCall_1.stop, grammarAccess.getN4ClassDeclarationAccess().getTypeVariablesParserRuleCall_1()); }
	)?
	(
		ClassExtendsImplementsParserRuleCall_2=ruleClassExtendsImplements{ announce($ClassExtendsImplementsParserRuleCall_2.start, $ClassExtendsImplementsParserRuleCall_2.stop, grammarAccess.getN4ClassDeclarationAccess().getClassExtendsImplementsParserRuleCall_2()); }
	)?
	MembersParserRuleCall_3=ruleMembers{ announce($MembersParserRuleCall_3.start, $MembersParserRuleCall_3.stop, grammarAccess.getN4ClassDeclarationAccess().getMembersParserRuleCall_3()); }
)
;


// Rule Members
ruleMembers
@init {
}:
(
	LeftCurlyBracketKeyword_0=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_0, grammarAccess.getMembersAccess().getLeftCurlyBracketKeyword_0());
	}
	(
		(
			OwnedMembersRawN4MemberDeclarationParserRuleCall_1_0=ruleN4MemberDeclaration{
				announce($OwnedMembersRawN4MemberDeclarationParserRuleCall_1_0.start, $OwnedMembersRawN4MemberDeclarationParserRuleCall_1_0.stop, grammarAccess.getMembersAccess().getOwnedMembersRawAssignment_1());
			}
		)
	)*
	RightCurlyBracketKeyword_2=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_2, grammarAccess.getMembersAccess().getRightCurlyBracketKeyword_2());
	}
)
;


// Rule Members
norm1_Members
@init {
}:
(
	LeftCurlyBracketKeyword_0=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_0, grammarAccess.getMembersAccess().getLeftCurlyBracketKeyword_0());
	}
	(
		(
			OwnedMembersRawN4MemberDeclarationParserRuleCall_1_0=norm1_N4MemberDeclaration{
				announce($OwnedMembersRawN4MemberDeclarationParserRuleCall_1_0.start, $OwnedMembersRawN4MemberDeclarationParserRuleCall_1_0.stop, grammarAccess.getMembersAccess().getOwnedMembersRawAssignment_1());
			}
		)
	)*
	RightCurlyBracketKeyword_2=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_2, grammarAccess.getMembersAccess().getRightCurlyBracketKeyword_2());
	}
)
;


// Rule ClassExtendsImplements
ruleClassExtendsImplements
@init {
}:
(
	(
		ClassExtendsClauseParserRuleCall_0_0=ruleClassExtendsClause{ announce($ClassExtendsClauseParserRuleCall_0_0.start, $ClassExtendsClauseParserRuleCall_0_0.stop, grammarAccess.getClassExtendsImplementsAccess().getClassExtendsClauseParserRuleCall_0_0()); }
		(
			ClassImplementsListParserRuleCall_0_1=ruleClassImplementsList{ announce($ClassImplementsListParserRuleCall_0_1.start, $ClassImplementsListParserRuleCall_0_1.stop, grammarAccess.getClassExtendsImplementsAccess().getClassImplementsListParserRuleCall_0_1()); }
		)?
	)
	    |
	(
		ClassImplementsListParserRuleCall_1_0=ruleClassImplementsList{ announce($ClassImplementsListParserRuleCall_1_0.start, $ClassImplementsListParserRuleCall_1_0.stop, grammarAccess.getClassExtendsImplementsAccess().getClassImplementsListParserRuleCall_1_0()); }
		(
			ClassExtendsClauseParserRuleCall_1_1=ruleClassExtendsClause{ announce($ClassExtendsClauseParserRuleCall_1_1.start, $ClassExtendsClauseParserRuleCall_1_1.stop, grammarAccess.getClassExtendsImplementsAccess().getClassExtendsClauseParserRuleCall_1_1()); }
		)?
	)
)
;


// Rule ClassExtendsImplements
norm1_ClassExtendsImplements
@init {
}:
(
	(
		ClassExtendsClauseParserRuleCall_0_0=norm1_ClassExtendsClause{ announce($ClassExtendsClauseParserRuleCall_0_0.start, $ClassExtendsClauseParserRuleCall_0_0.stop, grammarAccess.getClassExtendsImplementsAccess().getClassExtendsClauseParserRuleCall_0_0()); }
		(
			ClassImplementsListParserRuleCall_0_1=ruleClassImplementsList{ announce($ClassImplementsListParserRuleCall_0_1.start, $ClassImplementsListParserRuleCall_0_1.stop, grammarAccess.getClassExtendsImplementsAccess().getClassImplementsListParserRuleCall_0_1()); }
		)?
	)
	    |
	(
		ClassImplementsListParserRuleCall_1_0=ruleClassImplementsList{ announce($ClassImplementsListParserRuleCall_1_0.start, $ClassImplementsListParserRuleCall_1_0.stop, grammarAccess.getClassExtendsImplementsAccess().getClassImplementsListParserRuleCall_1_0()); }
		(
			ClassExtendsClauseParserRuleCall_1_1=norm1_ClassExtendsClause{ announce($ClassExtendsClauseParserRuleCall_1_1.start, $ClassExtendsClauseParserRuleCall_1_1.stop, grammarAccess.getClassExtendsImplementsAccess().getClassExtendsClauseParserRuleCall_1_1()); }
		)?
	)
)
;


// Rule ClassExtendsClause
ruleClassExtendsClause
@init {
}:
(
	ExtendsKeyword_0=Extends
	 {
		announce($ExtendsKeyword_0, grammarAccess.getClassExtendsClauseAccess().getExtendsKeyword_0());
	}
	(
		(
			((
				ruleParameterizedTypeRefNominal
			)
			)=>
			(
				SuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0=ruleParameterizedTypeRefNominal{
					announce($SuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0.start, $SuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0.stop, grammarAccess.getClassExtendsClauseAccess().getSuperClassRefAssignment_1_0());
				}
			)
		)
		    |
		(
			(
				SuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0=ruleLeftHandSideExpression{
					announce($SuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0.start, $SuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0.stop, grammarAccess.getClassExtendsClauseAccess().getSuperClassExpressionAssignment_1_1());
				}
			)
		)
	)
)
;


// Rule ClassExtendsClause
norm1_ClassExtendsClause
@init {
}:
(
	ExtendsKeyword_0=Extends
	 {
		announce($ExtendsKeyword_0, grammarAccess.getClassExtendsClauseAccess().getExtendsKeyword_0());
	}
	(
		(
			((
				ruleParameterizedTypeRefNominal
			)
			)=>
			(
				SuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0=ruleParameterizedTypeRefNominal{
					announce($SuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0.start, $SuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0.stop, grammarAccess.getClassExtendsClauseAccess().getSuperClassRefAssignment_1_0());
				}
			)
		)
		    |
		(
			(
				SuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0=norm1_LeftHandSideExpression{
					announce($SuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0.start, $SuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0.stop, grammarAccess.getClassExtendsClauseAccess().getSuperClassExpressionAssignment_1_1());
				}
			)
		)
	)
)
;


// Rule ClassImplementsList
ruleClassImplementsList
@init {
}:
(
	ImplementsKeyword_0=Implements
	 {
		announce($ImplementsKeyword_0, grammarAccess.getClassImplementsListAccess().getImplementsKeyword_0());
	}
	(
		(
			ImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0=ruleParameterizedTypeRefNominal{
				announce($ImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0.start, $ImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0.stop, grammarAccess.getClassImplementsListAccess().getImplementedInterfaceRefsAssignment_1());
			}
		)
	)
	(
		CommaKeyword_2_0=Comma
		 {
			announce($CommaKeyword_2_0, grammarAccess.getClassImplementsListAccess().getCommaKeyword_2_0());
		}
		(
			(
				ImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0=ruleParameterizedTypeRefNominal{
					announce($ImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0.start, $ImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0.stop, grammarAccess.getClassImplementsListAccess().getImplementedInterfaceRefsAssignment_2_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleN4ClassExpression
entryRuleN4ClassExpression
	:
	ruleN4ClassExpression
	EOF;

// Rule N4ClassExpression
ruleN4ClassExpression
@init {
}:
(
	ClassKeyword_1=Class
	 {
		announce($ClassKeyword_1, grammarAccess.getN4ClassExpressionAccess().getClassKeyword_1());
	}
	(
		(
			NameBindingIdentifierParserRuleCall_2_0=ruleBindingIdentifier{
				announce($NameBindingIdentifierParserRuleCall_2_0.start, $NameBindingIdentifierParserRuleCall_2_0.stop, grammarAccess.getN4ClassExpressionAccess().getNameAssignment_2());
			}
		)
	)?
	(
		ClassExtendsImplementsParserRuleCall_3=ruleClassExtendsImplements{ announce($ClassExtendsImplementsParserRuleCall_3.start, $ClassExtendsImplementsParserRuleCall_3.stop, grammarAccess.getN4ClassExpressionAccess().getClassExtendsImplementsParserRuleCall_3()); }
	)?
	MembersParserRuleCall_4=ruleMembers{ announce($MembersParserRuleCall_4.start, $MembersParserRuleCall_4.stop, grammarAccess.getN4ClassExpressionAccess().getMembersParserRuleCall_4()); }
)
;


// Rule N4ClassExpression
norm1_N4ClassExpression
@init {
}:
(
	ClassKeyword_1=Class
	 {
		announce($ClassKeyword_1, grammarAccess.getN4ClassExpressionAccess().getClassKeyword_1());
	}
	(
		(
			NameBindingIdentifierParserRuleCall_2_0=norm1_BindingIdentifier{
				announce($NameBindingIdentifierParserRuleCall_2_0.start, $NameBindingIdentifierParserRuleCall_2_0.stop, grammarAccess.getN4ClassExpressionAccess().getNameAssignment_2());
			}
		)
	)?
	(
		ClassExtendsImplementsParserRuleCall_3=norm1_ClassExtendsImplements{ announce($ClassExtendsImplementsParserRuleCall_3.start, $ClassExtendsImplementsParserRuleCall_3.stop, grammarAccess.getN4ClassExpressionAccess().getClassExtendsImplementsParserRuleCall_3()); }
	)?
	MembersParserRuleCall_4=norm1_Members{ announce($MembersParserRuleCall_4.start, $MembersParserRuleCall_4.stop, grammarAccess.getN4ClassExpressionAccess().getMembersParserRuleCall_4()); }
)
;

// Entry rule entryRuleN4InterfaceDeclaration
entryRuleN4InterfaceDeclaration
	:
	ruleN4InterfaceDeclaration
	EOF;

// Rule N4InterfaceDeclaration
ruleN4InterfaceDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Interface
			(
				(
					ruleTypingStrategyDefSiteOperator
				)
			)?
			(
				(
					ruleBindingIdentifier
				)
			)?
			(
				ruleVersionDeclaration
			)?
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			InterfaceKeyword_0_0_1=Interface
			 {
				announce($InterfaceKeyword_0_0_1, grammarAccess.getN4InterfaceDeclarationAccess().getInterfaceKeyword_0_0_1());
			}
			(
				(
					TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0=ruleTypingStrategyDefSiteOperator{
						announce($TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0.start, $TypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0.stop, grammarAccess.getN4InterfaceDeclarationAccess().getTypingStrategyAssignment_0_0_2());
					}
				)
			)?
			(
				(
					NameBindingIdentifierParserRuleCall_0_0_3_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_0_0_3_0.start, $NameBindingIdentifierParserRuleCall_0_0_3_0.stop, grammarAccess.getN4InterfaceDeclarationAccess().getNameAssignment_0_0_3());
					}
				)
			)?
			(
				VersionDeclarationParserRuleCall_0_0_4=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_0_0_4.start, $VersionDeclarationParserRuleCall_0_0_4.stop, grammarAccess.getN4InterfaceDeclarationAccess().getVersionDeclarationParserRuleCall_0_0_4()); }
			)?
		)
	)
	(
		TypeVariablesParserRuleCall_1=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1.start, $TypeVariablesParserRuleCall_1.stop, grammarAccess.getN4InterfaceDeclarationAccess().getTypeVariablesParserRuleCall_1()); }
	)?
	(
		InterfaceExtendsListParserRuleCall_2=ruleInterfaceExtendsList{ announce($InterfaceExtendsListParserRuleCall_2.start, $InterfaceExtendsListParserRuleCall_2.stop, grammarAccess.getN4InterfaceDeclarationAccess().getInterfaceExtendsListParserRuleCall_2()); }
	)?
	MembersParserRuleCall_3=ruleMembers{ announce($MembersParserRuleCall_3.start, $MembersParserRuleCall_3.stop, grammarAccess.getN4InterfaceDeclarationAccess().getMembersParserRuleCall_3()); }
)
;


// Rule InterfaceExtendsList
ruleInterfaceExtendsList
@init {
}:
(
	(
		ExtendsKeyword_0_0=Extends
		 {
			announce($ExtendsKeyword_0_0, grammarAccess.getInterfaceExtendsListAccess().getExtendsKeyword_0_0());
		}
		    |
		ImplementsKeyword_0_1=Implements
		 {
			announce($ImplementsKeyword_0_1, grammarAccess.getInterfaceExtendsListAccess().getImplementsKeyword_0_1());
		}
	)
	(
		(
			SuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0=ruleParameterizedTypeRefNominal{
				announce($SuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0.start, $SuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0.stop, grammarAccess.getInterfaceExtendsListAccess().getSuperInterfaceRefsAssignment_1());
			}
		)
	)
	(
		CommaKeyword_2_0=Comma
		 {
			announce($CommaKeyword_2_0, grammarAccess.getInterfaceExtendsListAccess().getCommaKeyword_2_0());
		}
		(
			(
				SuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0=ruleParameterizedTypeRefNominal{
					announce($SuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0.start, $SuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0.stop, grammarAccess.getInterfaceExtendsListAccess().getSuperInterfaceRefsAssignment_2_1());
				}
			)
		)
	)*
)
;

// Entry rule entryRuleN4EnumDeclaration
entryRuleN4EnumDeclaration
	:
	ruleN4EnumDeclaration
	EOF;

// Rule N4EnumDeclaration
ruleN4EnumDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Enum
			(
				(
					ruleBindingIdentifier
				)
			)?
			(
				ruleVersionDeclaration
			)?
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			EnumKeyword_0_0_2=Enum
			 {
				announce($EnumKeyword_0_0_2, grammarAccess.getN4EnumDeclarationAccess().getEnumKeyword_0_0_2());
			}
			(
				(
					NameBindingIdentifierParserRuleCall_0_0_3_0=ruleBindingIdentifier{
						announce($NameBindingIdentifierParserRuleCall_0_0_3_0.start, $NameBindingIdentifierParserRuleCall_0_0_3_0.stop, grammarAccess.getN4EnumDeclarationAccess().getNameAssignment_0_0_3());
					}
				)
			)?
			(
				VersionDeclarationParserRuleCall_0_0_4=ruleVersionDeclaration{ announce($VersionDeclarationParserRuleCall_0_0_4.start, $VersionDeclarationParserRuleCall_0_0_4.stop, grammarAccess.getN4EnumDeclarationAccess().getVersionDeclarationParserRuleCall_0_0_4()); }
			)?
		)
	)
	LeftCurlyBracketKeyword_1=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_1, grammarAccess.getN4EnumDeclarationAccess().getLeftCurlyBracketKeyword_1());
	}
	(
		(
			(
				LiteralsN4EnumLiteralParserRuleCall_2_0_0=ruleN4EnumLiteral{
					announce($LiteralsN4EnumLiteralParserRuleCall_2_0_0.start, $LiteralsN4EnumLiteralParserRuleCall_2_0_0.stop, grammarAccess.getN4EnumDeclarationAccess().getLiteralsAssignment_2_0());
				}
			)
		)
		(
			CommaKeyword_2_1_0=Comma
			 {
				announce($CommaKeyword_2_1_0, grammarAccess.getN4EnumDeclarationAccess().getCommaKeyword_2_1_0());
			}
			(
				(
					LiteralsN4EnumLiteralParserRuleCall_2_1_1_0=ruleN4EnumLiteral{
						announce($LiteralsN4EnumLiteralParserRuleCall_2_1_1_0.start, $LiteralsN4EnumLiteralParserRuleCall_2_1_1_0.stop, grammarAccess.getN4EnumDeclarationAccess().getLiteralsAssignment_2_1_1());
					}
				)
			)
		)*
	)?
	RightCurlyBracketKeyword_3=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_3, grammarAccess.getN4EnumDeclarationAccess().getRightCurlyBracketKeyword_3());
	}
)
;

// Entry rule entryRuleN4EnumLiteral
entryRuleN4EnumLiteral
	:
	ruleN4EnumLiteral
	EOF;

// Rule N4EnumLiteral
ruleN4EnumLiteral
@init {
}:
(
	(
		(
			NameIdentifierNameParserRuleCall_0_0=ruleIdentifierName{
				announce($NameIdentifierNameParserRuleCall_0_0.start, $NameIdentifierNameParserRuleCall_0_0.stop, grammarAccess.getN4EnumLiteralAccess().getNameAssignment_0());
			}
		)
	)
	(
		ColonKeyword_1_0=Colon
		 {
			announce($ColonKeyword_1_0, grammarAccess.getN4EnumLiteralAccess().getColonKeyword_1_0());
		}
		(
			(
				ValueSTRINGTerminalRuleCall_1_1_0=RULE_STRING{
					announce($ValueSTRINGTerminalRuleCall_1_1_0, grammarAccess.getN4EnumLiteralAccess().getValueAssignment_1_1());
				}
			)
		)
	)?
)
;

// Entry rule entryRuleN4MemberDeclaration
entryRuleN4MemberDeclaration
	:
	ruleN4MemberDeclaration
	EOF;

// Rule N4MemberDeclaration
ruleN4MemberDeclaration
@init {
}:
(
	AnnotatedN4MemberDeclarationParserRuleCall_0=ruleAnnotatedN4MemberDeclaration{ announce($AnnotatedN4MemberDeclarationParserRuleCall_0.start, $AnnotatedN4MemberDeclarationParserRuleCall_0.stop, grammarAccess.getN4MemberDeclarationAccess().getAnnotatedN4MemberDeclarationParserRuleCall_0()); }
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			ruleGetterHeader
		)
		)=>
		N4GetterDeclarationParserRuleCall_1=ruleN4GetterDeclaration{ announce($N4GetterDeclarationParserRuleCall_1.start, $N4GetterDeclarationParserRuleCall_1.stop, grammarAccess.getN4MemberDeclarationAccess().getN4GetterDeclarationParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Set
			(
				Break
				    |
				Case
				    |
				Catch
				    |
				Class
				    |
				Const
				    |
				Continue
				    |
				Debugger
				    |
				Default
				    |
				Delete
				    |
				Do
				    |
				Else
				    |
				Export
				    |
				Extends
				    |
				Finally
				    |
				For
				    |
				Function
				    |
				If
				    |
				Import
				    |
				In
				    |
				Instanceof
				    |
				New
				    |
				Return
				    |
				Super
				    |
				Switch
				    |
				This_1
				    |
				Throw
				    |
				Try
				    |
				Typeof
				    |
				Var
				    |
				Void
				    |
				While
				    |
				With
				    |
				Yield
				    |
				Null
				    |
				True
				    |
				False
				    |
				Enum
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LeftSquareBracket
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
		)
		)=>
		N4SetterDeclarationParserRuleCall_2=ruleN4SetterDeclaration{ announce($N4SetterDeclarationParserRuleCall_2.start, $N4SetterDeclarationParserRuleCall_2.stop, grammarAccess.getN4MemberDeclarationAccess().getN4SetterDeclarationParserRuleCall_2()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			(
				ruleTypeVariables
			)?
			(
				ruleBogusTypeRefFragment
			)?
			(
				(
					(
						(
							Asterisk
						)
					)
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
				    |
				(
					ruleAsyncNoTrailingLineBreak
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
			)
		)
		)=>
		N4MethodDeclarationParserRuleCall_3=ruleN4MethodDeclaration{ announce($N4MethodDeclarationParserRuleCall_3.start, $N4MethodDeclarationParserRuleCall_3.stop, grammarAccess.getN4MemberDeclarationAccess().getN4MethodDeclarationParserRuleCall_3()); }
	)
	    |
	N4FieldDeclarationParserRuleCall_4=ruleN4FieldDeclaration{ announce($N4FieldDeclarationParserRuleCall_4.start, $N4FieldDeclarationParserRuleCall_4.stop, grammarAccess.getN4MemberDeclarationAccess().getN4FieldDeclarationParserRuleCall_4()); }
	    |
	N4CallableConstructorDeclarationParserRuleCall_5=ruleN4CallableConstructorDeclaration{ announce($N4CallableConstructorDeclarationParserRuleCall_5.start, $N4CallableConstructorDeclarationParserRuleCall_5.stop, grammarAccess.getN4MemberDeclarationAccess().getN4CallableConstructorDeclarationParserRuleCall_5()); }
)
;


// Rule N4MemberDeclaration
norm1_N4MemberDeclaration
@init {
}:
(
	AnnotatedN4MemberDeclarationParserRuleCall_0=norm1_AnnotatedN4MemberDeclaration{ announce($AnnotatedN4MemberDeclarationParserRuleCall_0.start, $AnnotatedN4MemberDeclarationParserRuleCall_0.stop, grammarAccess.getN4MemberDeclarationAccess().getAnnotatedN4MemberDeclarationParserRuleCall_0()); }
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			norm1_GetterHeader
		)
		)=>
		N4GetterDeclarationParserRuleCall_1=norm1_N4GetterDeclaration{ announce($N4GetterDeclarationParserRuleCall_1.start, $N4GetterDeclarationParserRuleCall_1.stop, grammarAccess.getN4MemberDeclarationAccess().getN4GetterDeclarationParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Set
			(
				Break
				    |
				Case
				    |
				Catch
				    |
				Class
				    |
				Const
				    |
				Continue
				    |
				Debugger
				    |
				Default
				    |
				Delete
				    |
				Do
				    |
				Else
				    |
				Export
				    |
				Extends
				    |
				Finally
				    |
				For
				    |
				Function
				    |
				If
				    |
				Import
				    |
				In
				    |
				Instanceof
				    |
				New
				    |
				Return
				    |
				Super
				    |
				Switch
				    |
				This_1
				    |
				Throw
				    |
				Try
				    |
				Typeof
				    |
				Var
				    |
				Void
				    |
				While
				    |
				With
				    |
				Yield
				    |
				Null
				    |
				True
				    |
				False
				    |
				Enum
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LeftSquareBracket
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
		)
		)=>
		N4SetterDeclarationParserRuleCall_2=norm1_N4SetterDeclaration{ announce($N4SetterDeclarationParserRuleCall_2.start, $N4SetterDeclarationParserRuleCall_2.stop, grammarAccess.getN4MemberDeclarationAccess().getN4SetterDeclarationParserRuleCall_2()); }
	)
	    |
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			(
				ruleTypeVariables
			)?
			(
				ruleBogusTypeRefFragment
			)?
			(
				(
					(
						(
							Asterisk
						)
					)
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
				    |
				(
					ruleAsyncNoTrailingLineBreak
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
			)
		)
		)=>
		N4MethodDeclarationParserRuleCall_3=norm1_N4MethodDeclaration{ announce($N4MethodDeclarationParserRuleCall_3.start, $N4MethodDeclarationParserRuleCall_3.stop, grammarAccess.getN4MemberDeclarationAccess().getN4MethodDeclarationParserRuleCall_3()); }
	)
	    |
	N4FieldDeclarationParserRuleCall_4=norm1_N4FieldDeclaration{ announce($N4FieldDeclarationParserRuleCall_4.start, $N4FieldDeclarationParserRuleCall_4.stop, grammarAccess.getN4MemberDeclarationAccess().getN4FieldDeclarationParserRuleCall_4()); }
	    |
	N4CallableConstructorDeclarationParserRuleCall_5=norm1_N4CallableConstructorDeclaration{ announce($N4CallableConstructorDeclarationParserRuleCall_5.start, $N4CallableConstructorDeclarationParserRuleCall_5.stop, grammarAccess.getN4MemberDeclarationAccess().getN4CallableConstructorDeclarationParserRuleCall_5()); }
)
;

// Entry rule entryRuleAnnotatedN4MemberDeclaration
entryRuleAnnotatedN4MemberDeclaration
	:
	ruleAnnotatedN4MemberDeclaration
	EOF;

// Rule AnnotatedN4MemberDeclaration
ruleAnnotatedN4MemberDeclaration
@init {
}:
(
	N4MemberAnnotationListParserRuleCall_0=ruleN4MemberAnnotationList{ announce($N4MemberAnnotationListParserRuleCall_0.start, $N4MemberAnnotationListParserRuleCall_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4MemberAnnotationListParserRuleCall_0()); }
	(
		(
			(
				((
					(
						(
							ruleN4Modifier
						)
					)*
					ruleGetterHeader
				)
				)=>
				(
					(
						(
							ruleN4Modifier
						)
					)*
					GetterHeaderParserRuleCall_1_0_0_0_2=ruleGetterHeader{ announce($GetterHeaderParserRuleCall_1_0_0_0_2.start, $GetterHeaderParserRuleCall_1_0_0_0_2.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getGetterHeaderParserRuleCall_1_0_0_0_2()); }
				)
			)
			(
				((
					LeftCurlyBracket
				)
				)=>
				(
					BodyBlockParserRuleCall_1_0_1_0=ruleBlock{
						announce($BodyBlockParserRuleCall_1_0_1_0.start, $BodyBlockParserRuleCall_1_0_1_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBodyAssignment_1_0_1());
					}
				)
			)?
			(
				SemicolonKeyword_1_0_2=Semicolon
				 {
					announce($SemicolonKeyword_1_0_2, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_0_2());
				}
			)?
		)
		    |
		(
			(
				((
					(
						(
							ruleN4Modifier
						)
					)*
					Set
					(
						Break
						    |
						Case
						    |
						Catch
						    |
						Class
						    |
						Const
						    |
						Continue
						    |
						Debugger
						    |
						Default
						    |
						Delete
						    |
						Do
						    |
						Else
						    |
						Export
						    |
						Extends
						    |
						Finally
						    |
						For
						    |
						Function
						    |
						If
						    |
						Import
						    |
						In
						    |
						Instanceof
						    |
						New
						    |
						Return
						    |
						Super
						    |
						Switch
						    |
						This_1
						    |
						Throw
						    |
						Try
						    |
						Typeof
						    |
						Var
						    |
						Void
						    |
						While
						    |
						With
						    |
						Yield
						    |
						Null
						    |
						True
						    |
						False
						    |
						Enum
						    |
						Get
						    |
						Set
						    |
						Let
						    |
						Project
						    |
						External
						    |
						Abstract
						    |
						Static
						    |
						As
						    |
						From
						    |
						Constructor
						    |
						Of
						    |
						Target
						    |
						Type
						    |
						Union
						    |
						Intersection
						    |
						This
						    |
						Promisify
						    |
						Await
						    |
						Async
						    |
						Implements
						    |
						Interface
						    |
						Private
						    |
						Protected
						    |
						Public
						    |
						Out
						    |
						LeftSquareBracket
						    |
						RULE_IDENTIFIER
						    |
						RULE_STRING
						    |
						RULE_DOUBLE
						    |
						RULE_INT
						    |
						RULE_OCTAL_INT
						    |
						RULE_HEX_INT
						    |
						RULE_SCIENTIFIC_INT
					)
				)
				)=>
				(
					(
						(
							ruleN4Modifier
						)
					)*
					SetKeyword_1_1_0_0_2=Set
					 {
						announce($SetKeyword_1_1_0_0_2, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSetKeyword_1_1_0_0_2());
					}
					(
						(Break | 
						Case | 
						Catch | 
						Class | 
						Const | 
						Continue | 
						Debugger | 
						Default | 
						Delete | 
						Do | 
						Else | 
						Export | 
						Extends | 
						Finally | 
						For | 
						Function | 
						If | 
						Import | 
						In | 
						Instanceof | 
						New | 
						Return | 
						Super | 
						Switch | 
						This_1 | 
						Throw | 
						Try | 
						Typeof | 
						Var | 
						Void | 
						While | 
						With | 
						Yield | 
						Null | 
						True | 
						False | 
						Enum | 
						Get | 
						Set | 
						Let | 
						Project | 
						External | 
						Abstract | 
						Static | 
						As | 
						From | 
						Constructor | 
						Of | 
						Target | 
						Type | 
						Union | 
						Intersection | 
						This | 
						Promisify | 
						Await | 
						Async | 
						Implements | 
						Interface | 
						Private | 
						Protected | 
						Public | 
						Out | 
						LeftSquareBracket | 
						RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0=ruleLiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameAssignment_1_1_0_0_3());
							}
						)
					)
				)
			)
			(
				(
					DeclaredOptionalQuestionMarkKeyword_1_1_1_0=QuestionMark
					 {
						announce($DeclaredOptionalQuestionMarkKeyword_1_1_1_0, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_1_1_0());
					}
				)
			)?
			LeftParenthesisKeyword_1_1_2=LeftParenthesis
			 {
				announce($LeftParenthesisKeyword_1_1_2, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getLeftParenthesisKeyword_1_1_2());
			}
			(
				(
					FparFormalParameterParserRuleCall_1_1_3_0=ruleFormalParameter{
						announce($FparFormalParameterParserRuleCall_1_1_3_0.start, $FparFormalParameterParserRuleCall_1_1_3_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getFparAssignment_1_1_3());
					}
				)
			)
			RightParenthesisKeyword_1_1_4=RightParenthesis
			 {
				announce($RightParenthesisKeyword_1_1_4, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getRightParenthesisKeyword_1_1_4());
			}
			(
				((
					LeftCurlyBracket
				)
				)=>
				(
					BodyBlockParserRuleCall_1_1_5_0=ruleBlock{
						announce($BodyBlockParserRuleCall_1_1_5_0.start, $BodyBlockParserRuleCall_1_1_5_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBodyAssignment_1_1_5());
					}
				)
			)?
			(
				SemicolonKeyword_1_1_6=Semicolon
				 {
					announce($SemicolonKeyword_1_1_6, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_1_6());
				}
			)?
		)
		    |
		(
			(
				((
					(
						(
							ruleN4Modifier
						)
					)*
					(
						ruleTypeVariables
					)?
					(
						ruleBogusTypeRefFragment
					)?
					(
						(
							(
								(
									Asterisk
								)
							)
							(
								(
									ruleLiteralOrComputedPropertyName
								)
							)
							LeftParenthesis
						)
						    |
						(
							ruleAsyncNoTrailingLineBreak
							(
								(
									ruleLiteralOrComputedPropertyName
								)
							)
							LeftParenthesis
						)
					)
				)
				)=>
				(
					(
						(
							ruleN4Modifier
						)
					)*
					(
						TypeVariablesParserRuleCall_1_2_0_0_2=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1_2_0_0_2.start, $TypeVariablesParserRuleCall_1_2_0_0_2.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getTypeVariablesParserRuleCall_1_2_0_0_2()); }
					)?
					(
						BogusTypeRefFragmentParserRuleCall_1_2_0_0_3=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_1_2_0_0_3.start, $BogusTypeRefFragmentParserRuleCall_1_2_0_0_3.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBogusTypeRefFragmentParserRuleCall_1_2_0_0_3()); }
					)?
					(
						(
							(
								(
									GeneratorAsteriskKeyword_1_2_0_0_4_0_0_0=Asterisk
									 {
										announce($GeneratorAsteriskKeyword_1_2_0_0_4_0_0_0, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getGeneratorAsteriskKeyword_1_2_0_0_4_0_0_0());
									}
								)
							)
							(
								(
									DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0=ruleLiteralOrComputedPropertyName{
										announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameAssignment_1_2_0_0_4_0_1());
									}
								)
							)
							(
								(LeftParenthesis
								)=>
								MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2=norm1_MethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2.start, $MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2()); }
							)
						)
						    |
						(
							AsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0.start, $AsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0()); }
							(
								(
									DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0=ruleLiteralOrComputedPropertyName{
										announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameAssignment_1_2_0_0_4_1_1());
									}
								)
							)
							(
								(LeftParenthesis
								)=>
								MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2=ruleMethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2.start, $MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2()); }
							)
						)
					)
				)
			)
			(
				SemicolonKeyword_1_2_1=Semicolon
				 {
					announce($SemicolonKeyword_1_2_1, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_2_1());
				}
			)?
		)
		    |
		(
			FieldDeclarationImplParserRuleCall_1_3_1=ruleFieldDeclarationImpl{ announce($FieldDeclarationImplParserRuleCall_1_3_1.start, $FieldDeclarationImplParserRuleCall_1_3_1.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getFieldDeclarationImplParserRuleCall_1_3_1()); }
		)
	)
)
;


// Rule AnnotatedN4MemberDeclaration
norm1_AnnotatedN4MemberDeclaration
@init {
}:
(
	N4MemberAnnotationListParserRuleCall_0=ruleN4MemberAnnotationList{ announce($N4MemberAnnotationListParserRuleCall_0.start, $N4MemberAnnotationListParserRuleCall_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4MemberAnnotationListParserRuleCall_0()); }
	(
		(
			(
				((
					(
						(
							ruleN4Modifier
						)
					)*
					norm1_GetterHeader
				)
				)=>
				(
					(
						(
							ruleN4Modifier
						)
					)*
					GetterHeaderParserRuleCall_1_0_0_0_2=norm1_GetterHeader{ announce($GetterHeaderParserRuleCall_1_0_0_0_2.start, $GetterHeaderParserRuleCall_1_0_0_0_2.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getGetterHeaderParserRuleCall_1_0_0_0_2()); }
				)
			)
			(
				((
					LeftCurlyBracket
				)
				)=>
				(
					BodyBlockParserRuleCall_1_0_1_0=norm1_Block{
						announce($BodyBlockParserRuleCall_1_0_1_0.start, $BodyBlockParserRuleCall_1_0_1_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBodyAssignment_1_0_1());
					}
				)
			)?
			(
				SemicolonKeyword_1_0_2=Semicolon
				 {
					announce($SemicolonKeyword_1_0_2, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_0_2());
				}
			)?
		)
		    |
		(
			(
				((
					(
						(
							ruleN4Modifier
						)
					)*
					Set
					(
						Break
						    |
						Case
						    |
						Catch
						    |
						Class
						    |
						Const
						    |
						Continue
						    |
						Debugger
						    |
						Default
						    |
						Delete
						    |
						Do
						    |
						Else
						    |
						Export
						    |
						Extends
						    |
						Finally
						    |
						For
						    |
						Function
						    |
						If
						    |
						Import
						    |
						In
						    |
						Instanceof
						    |
						New
						    |
						Return
						    |
						Super
						    |
						Switch
						    |
						This_1
						    |
						Throw
						    |
						Try
						    |
						Typeof
						    |
						Var
						    |
						Void
						    |
						While
						    |
						With
						    |
						Yield
						    |
						Null
						    |
						True
						    |
						False
						    |
						Enum
						    |
						Get
						    |
						Set
						    |
						Let
						    |
						Project
						    |
						External
						    |
						Abstract
						    |
						Static
						    |
						As
						    |
						From
						    |
						Constructor
						    |
						Of
						    |
						Target
						    |
						Type
						    |
						Union
						    |
						Intersection
						    |
						This
						    |
						Promisify
						    |
						Await
						    |
						Async
						    |
						Implements
						    |
						Interface
						    |
						Private
						    |
						Protected
						    |
						Public
						    |
						Out
						    |
						LeftSquareBracket
						    |
						RULE_IDENTIFIER
						    |
						RULE_STRING
						    |
						RULE_DOUBLE
						    |
						RULE_INT
						    |
						RULE_OCTAL_INT
						    |
						RULE_HEX_INT
						    |
						RULE_SCIENTIFIC_INT
					)
				)
				)=>
				(
					(
						(
							ruleN4Modifier
						)
					)*
					SetKeyword_1_1_0_0_2=Set
					 {
						announce($SetKeyword_1_1_0_0_2, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSetKeyword_1_1_0_0_2());
					}
					(
						(Break | 
						Case | 
						Catch | 
						Class | 
						Const | 
						Continue | 
						Debugger | 
						Default | 
						Delete | 
						Do | 
						Else | 
						Export | 
						Extends | 
						Finally | 
						For | 
						Function | 
						If | 
						Import | 
						In | 
						Instanceof | 
						New | 
						Return | 
						Super | 
						Switch | 
						This_1 | 
						Throw | 
						Try | 
						Typeof | 
						Var | 
						Void | 
						While | 
						With | 
						Yield | 
						Null | 
						True | 
						False | 
						Enum | 
						Get | 
						Set | 
						Let | 
						Project | 
						External | 
						Abstract | 
						Static | 
						As | 
						From | 
						Constructor | 
						Of | 
						Target | 
						Type | 
						Union | 
						Intersection | 
						This | 
						Promisify | 
						Await | 
						Async | 
						Implements | 
						Interface | 
						Private | 
						Protected | 
						Public | 
						Out | 
						LeftSquareBracket | 
						RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0=norm1_LiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameAssignment_1_1_0_0_3());
							}
						)
					)
				)
			)
			(
				(
					DeclaredOptionalQuestionMarkKeyword_1_1_1_0=QuestionMark
					 {
						announce($DeclaredOptionalQuestionMarkKeyword_1_1_1_0, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_1_1_0());
					}
				)
			)?
			LeftParenthesisKeyword_1_1_2=LeftParenthesis
			 {
				announce($LeftParenthesisKeyword_1_1_2, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getLeftParenthesisKeyword_1_1_2());
			}
			(
				(
					FparFormalParameterParserRuleCall_1_1_3_0=norm1_FormalParameter{
						announce($FparFormalParameterParserRuleCall_1_1_3_0.start, $FparFormalParameterParserRuleCall_1_1_3_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getFparAssignment_1_1_3());
					}
				)
			)
			RightParenthesisKeyword_1_1_4=RightParenthesis
			 {
				announce($RightParenthesisKeyword_1_1_4, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getRightParenthesisKeyword_1_1_4());
			}
			(
				((
					LeftCurlyBracket
				)
				)=>
				(
					BodyBlockParserRuleCall_1_1_5_0=norm1_Block{
						announce($BodyBlockParserRuleCall_1_1_5_0.start, $BodyBlockParserRuleCall_1_1_5_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBodyAssignment_1_1_5());
					}
				)
			)?
			(
				SemicolonKeyword_1_1_6=Semicolon
				 {
					announce($SemicolonKeyword_1_1_6, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_1_6());
				}
			)?
		)
		    |
		(
			(
				((
					(
						(
							ruleN4Modifier
						)
					)*
					(
						ruleTypeVariables
					)?
					(
						ruleBogusTypeRefFragment
					)?
					(
						(
							(
								(
									Asterisk
								)
							)
							(
								(
									norm1_LiteralOrComputedPropertyName
								)
							)
							LeftParenthesis
						)
						    |
						(
							ruleAsyncNoTrailingLineBreak
							(
								(
									norm1_LiteralOrComputedPropertyName
								)
							)
							LeftParenthesis
						)
					)
				)
				)=>
				(
					(
						(
							ruleN4Modifier
						)
					)*
					(
						TypeVariablesParserRuleCall_1_2_0_0_2=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_1_2_0_0_2.start, $TypeVariablesParserRuleCall_1_2_0_0_2.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getTypeVariablesParserRuleCall_1_2_0_0_2()); }
					)?
					(
						BogusTypeRefFragmentParserRuleCall_1_2_0_0_3=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_1_2_0_0_3.start, $BogusTypeRefFragmentParserRuleCall_1_2_0_0_3.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBogusTypeRefFragmentParserRuleCall_1_2_0_0_3()); }
					)?
					(
						(
							(
								(
									GeneratorAsteriskKeyword_1_2_0_0_4_0_0_0=Asterisk
									 {
										announce($GeneratorAsteriskKeyword_1_2_0_0_4_0_0_0, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getGeneratorAsteriskKeyword_1_2_0_0_4_0_0_0());
									}
								)
							)
							(
								(
									DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0=norm1_LiteralOrComputedPropertyName{
										announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameAssignment_1_2_0_0_4_0_1());
									}
								)
							)
							(
								(LeftParenthesis
								)=>
								MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2=norm1_MethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2.start, $MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2()); }
							)
						)
						    |
						(
							AsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0.start, $AsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0()); }
							(
								(
									DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0=norm1_LiteralOrComputedPropertyName{
										announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameAssignment_1_2_0_0_4_1_1());
									}
								)
							)
							(
								(LeftParenthesis
								)=>
								MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2=ruleMethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2.start, $MethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2()); }
							)
						)
					)
				)
			)
			(
				SemicolonKeyword_1_2_1=Semicolon
				 {
					announce($SemicolonKeyword_1_2_1, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_2_1());
				}
			)?
		)
		    |
		(
			FieldDeclarationImplParserRuleCall_1_3_1=norm1_FieldDeclarationImpl{ announce($FieldDeclarationImplParserRuleCall_1_3_1.start, $FieldDeclarationImplParserRuleCall_1_3_1.stop, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getFieldDeclarationImplParserRuleCall_1_3_1()); }
		)
	)
)
;


// Rule FieldDeclarationImpl
ruleFieldDeclarationImpl
@init {
}:
(
	(
		(
			ruleN4Modifier
		)
	)*
	(
		BogusTypeRefFragmentParserRuleCall_1=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_1.start, $BogusTypeRefFragmentParserRuleCall_1.stop, grammarAccess.getFieldDeclarationImplAccess().getBogusTypeRefFragmentParserRuleCall_1()); }
	)?
	(
		(
			DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0=ruleLiteralOrComputedPropertyName{
				announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0.stop, grammarAccess.getFieldDeclarationImplAccess().getDeclaredNameAssignment_2());
			}
		)
	)
	(
		(
			DeclaredOptionalQuestionMarkKeyword_3_0=QuestionMark
			 {
				announce($DeclaredOptionalQuestionMarkKeyword_3_0, grammarAccess.getFieldDeclarationImplAccess().getDeclaredOptionalQuestionMarkKeyword_3_0());
			}
		)
	)?
	(
		ColonSepDeclaredTypeRefParserRuleCall_4=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_4.start, $ColonSepDeclaredTypeRefParserRuleCall_4.stop, grammarAccess.getFieldDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_4()); }
	)?
	(
		EqualsSignKeyword_5_0=EqualsSign
		 {
			announce($EqualsSignKeyword_5_0, grammarAccess.getFieldDeclarationImplAccess().getEqualsSignKeyword_5_0());
		}
		(
			(
				ExpressionExpressionParserRuleCall_5_1_0=norm1_Expression{
					announce($ExpressionExpressionParserRuleCall_5_1_0.start, $ExpressionExpressionParserRuleCall_5_1_0.stop, grammarAccess.getFieldDeclarationImplAccess().getExpressionAssignment_5_1());
				}
			)
		)
	)?
	SemiParserRuleCall_6=ruleSemi{ announce($SemiParserRuleCall_6.start, $SemiParserRuleCall_6.stop, grammarAccess.getFieldDeclarationImplAccess().getSemiParserRuleCall_6()); }
)
;


// Rule FieldDeclarationImpl
norm1_FieldDeclarationImpl
@init {
}:
(
	(
		(
			ruleN4Modifier
		)
	)*
	(
		BogusTypeRefFragmentParserRuleCall_1=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_1.start, $BogusTypeRefFragmentParserRuleCall_1.stop, grammarAccess.getFieldDeclarationImplAccess().getBogusTypeRefFragmentParserRuleCall_1()); }
	)?
	(
		(
			DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0=norm1_LiteralOrComputedPropertyName{
				announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0.stop, grammarAccess.getFieldDeclarationImplAccess().getDeclaredNameAssignment_2());
			}
		)
	)
	(
		(
			DeclaredOptionalQuestionMarkKeyword_3_0=QuestionMark
			 {
				announce($DeclaredOptionalQuestionMarkKeyword_3_0, grammarAccess.getFieldDeclarationImplAccess().getDeclaredOptionalQuestionMarkKeyword_3_0());
			}
		)
	)?
	(
		ColonSepDeclaredTypeRefParserRuleCall_4=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_4.start, $ColonSepDeclaredTypeRefParserRuleCall_4.stop, grammarAccess.getFieldDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_4()); }
	)?
	(
		EqualsSignKeyword_5_0=EqualsSign
		 {
			announce($EqualsSignKeyword_5_0, grammarAccess.getFieldDeclarationImplAccess().getEqualsSignKeyword_5_0());
		}
		(
			(
				ExpressionExpressionParserRuleCall_5_1_0=norm3_Expression{
					announce($ExpressionExpressionParserRuleCall_5_1_0.start, $ExpressionExpressionParserRuleCall_5_1_0.stop, grammarAccess.getFieldDeclarationImplAccess().getExpressionAssignment_5_1());
				}
			)
		)
	)?
	SemiParserRuleCall_6=ruleSemi{ announce($SemiParserRuleCall_6.start, $SemiParserRuleCall_6.stop, grammarAccess.getFieldDeclarationImplAccess().getSemiParserRuleCall_6()); }
)
;

// Entry rule entryRuleN4FieldDeclaration
entryRuleN4FieldDeclaration
	:
	ruleN4FieldDeclaration
	EOF;

// Rule N4FieldDeclaration
ruleN4FieldDeclaration
@init {
}:
(
	FieldDeclarationImplParserRuleCall_1=ruleFieldDeclarationImpl{ announce($FieldDeclarationImplParserRuleCall_1.start, $FieldDeclarationImplParserRuleCall_1.stop, grammarAccess.getN4FieldDeclarationAccess().getFieldDeclarationImplParserRuleCall_1()); }
)
;


// Rule N4FieldDeclaration
norm1_N4FieldDeclaration
@init {
}:
(
	FieldDeclarationImplParserRuleCall_1=norm1_FieldDeclarationImpl{ announce($FieldDeclarationImplParserRuleCall_1.start, $FieldDeclarationImplParserRuleCall_1.stop, grammarAccess.getN4FieldDeclarationAccess().getFieldDeclarationImplParserRuleCall_1()); }
)
;

// Entry rule entryRuleN4MethodDeclaration
entryRuleN4MethodDeclaration
	:
	ruleN4MethodDeclaration
	EOF;

// Rule N4MethodDeclaration
ruleN4MethodDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			(
				ruleTypeVariables
			)?
			(
				ruleBogusTypeRefFragment
			)?
			(
				(
					(
						(
							Asterisk
						)
					)
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
				    |
				(
					ruleAsyncNoTrailingLineBreak
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
			)
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			(
				TypeVariablesParserRuleCall_0_0_2=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_0_0_2.start, $TypeVariablesParserRuleCall_0_0_2.stop, grammarAccess.getN4MethodDeclarationAccess().getTypeVariablesParserRuleCall_0_0_2()); }
			)?
			(
				BogusTypeRefFragmentParserRuleCall_0_0_3=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_0_0_3.start, $BogusTypeRefFragmentParserRuleCall_0_0_3.stop, grammarAccess.getN4MethodDeclarationAccess().getBogusTypeRefFragmentParserRuleCall_0_0_3()); }
			)?
			(
				(
					(
						(
							GeneratorAsteriskKeyword_0_0_4_0_0_0=Asterisk
							 {
								announce($GeneratorAsteriskKeyword_0_0_4_0_0_0, grammarAccess.getN4MethodDeclarationAccess().getGeneratorAsteriskKeyword_0_0_4_0_0_0());
							}
						)
					)
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0=ruleLiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0.stop, grammarAccess.getN4MethodDeclarationAccess().getDeclaredNameAssignment_0_0_4_0_1());
							}
						)
					)
					(
						(LeftParenthesis
						)=>
						MethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2=norm1_MethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2.start, $MethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2.stop, grammarAccess.getN4MethodDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2()); }
					)
				)
				    |
				(
					AsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0.start, $AsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0.stop, grammarAccess.getN4MethodDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0()); }
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0=ruleLiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0.stop, grammarAccess.getN4MethodDeclarationAccess().getDeclaredNameAssignment_0_0_4_1_1());
							}
						)
					)
					(
						(LeftParenthesis
						)=>
						MethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2=ruleMethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2.start, $MethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2.stop, grammarAccess.getN4MethodDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2()); }
					)
				)
			)
		)
	)
	(
		SemicolonKeyword_1=Semicolon
		 {
			announce($SemicolonKeyword_1, grammarAccess.getN4MethodDeclarationAccess().getSemicolonKeyword_1());
		}
	)?
)
;


// Rule N4MethodDeclaration
norm1_N4MethodDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			(
				ruleTypeVariables
			)?
			(
				ruleBogusTypeRefFragment
			)?
			(
				(
					(
						(
							Asterisk
						)
					)
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
				    |
				(
					ruleAsyncNoTrailingLineBreak
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					LeftParenthesis
				)
			)
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			(
				TypeVariablesParserRuleCall_0_0_2=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_0_0_2.start, $TypeVariablesParserRuleCall_0_0_2.stop, grammarAccess.getN4MethodDeclarationAccess().getTypeVariablesParserRuleCall_0_0_2()); }
			)?
			(
				BogusTypeRefFragmentParserRuleCall_0_0_3=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_0_0_3.start, $BogusTypeRefFragmentParserRuleCall_0_0_3.stop, grammarAccess.getN4MethodDeclarationAccess().getBogusTypeRefFragmentParserRuleCall_0_0_3()); }
			)?
			(
				(
					(
						(
							GeneratorAsteriskKeyword_0_0_4_0_0_0=Asterisk
							 {
								announce($GeneratorAsteriskKeyword_0_0_4_0_0_0, grammarAccess.getN4MethodDeclarationAccess().getGeneratorAsteriskKeyword_0_0_4_0_0_0());
							}
						)
					)
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0=norm1_LiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0.stop, grammarAccess.getN4MethodDeclarationAccess().getDeclaredNameAssignment_0_0_4_0_1());
							}
						)
					)
					(
						(LeftParenthesis
						)=>
						MethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2=norm1_MethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2.start, $MethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2.stop, grammarAccess.getN4MethodDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2()); }
					)
				)
				    |
				(
					AsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0=ruleAsyncNoTrailingLineBreak{ announce($AsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0.start, $AsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0.stop, grammarAccess.getN4MethodDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0()); }
					(
						(
							DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0=norm1_LiteralOrComputedPropertyName{
								announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0.stop, grammarAccess.getN4MethodDeclarationAccess().getDeclaredNameAssignment_0_0_4_1_1());
							}
						)
					)
					(
						(LeftParenthesis
						)=>
						MethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2=ruleMethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2.start, $MethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2.stop, grammarAccess.getN4MethodDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2()); }
					)
				)
			)
		)
	)
	(
		SemicolonKeyword_1=Semicolon
		 {
			announce($SemicolonKeyword_1, grammarAccess.getN4MethodDeclarationAccess().getSemicolonKeyword_1());
		}
	)?
)
;

// Entry rule entryRuleN4CallableConstructorDeclaration
entryRuleN4CallableConstructorDeclaration
	:
	ruleN4CallableConstructorDeclaration
	EOF;

// Rule N4CallableConstructorDeclaration
ruleN4CallableConstructorDeclaration
@init {
}:
(
	MethodParamsReturnAndBodyParserRuleCall_0=ruleMethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_0.start, $MethodParamsReturnAndBodyParserRuleCall_0.stop, grammarAccess.getN4CallableConstructorDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0()); }
	(
		SemicolonKeyword_1=Semicolon
		 {
			announce($SemicolonKeyword_1, grammarAccess.getN4CallableConstructorDeclarationAccess().getSemicolonKeyword_1());
		}
	)?
)
;


// Rule N4CallableConstructorDeclaration
norm1_N4CallableConstructorDeclaration
@init {
}:
(
	MethodParamsReturnAndBodyParserRuleCall_0=ruleMethodParamsReturnAndBody{ announce($MethodParamsReturnAndBodyParserRuleCall_0.start, $MethodParamsReturnAndBodyParserRuleCall_0.stop, grammarAccess.getN4CallableConstructorDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0()); }
	(
		SemicolonKeyword_1=Semicolon
		 {
			announce($SemicolonKeyword_1, grammarAccess.getN4CallableConstructorDeclarationAccess().getSemicolonKeyword_1());
		}
	)?
)
;


// Rule MethodParamsAndBody
ruleMethodParamsAndBody
@init {
}:
(
	StrictFormalParametersParserRuleCall_0=ruleStrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0.start, $StrictFormalParametersParserRuleCall_0.stop, grammarAccess.getMethodParamsAndBodyAccess().getStrictFormalParametersParserRuleCall_0()); }
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_1_0=ruleBlock{
				announce($BodyBlockParserRuleCall_1_0.start, $BodyBlockParserRuleCall_1_0.stop, grammarAccess.getMethodParamsAndBodyAccess().getBodyAssignment_1());
			}
		)
	)?
)
;


// Rule MethodParamsAndBody
norm1_MethodParamsAndBody
@init {
}:
(
	StrictFormalParametersParserRuleCall_0=norm1_StrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0.start, $StrictFormalParametersParserRuleCall_0.stop, grammarAccess.getMethodParamsAndBodyAccess().getStrictFormalParametersParserRuleCall_0()); }
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_1_0=norm1_Block{
				announce($BodyBlockParserRuleCall_1_0.start, $BodyBlockParserRuleCall_1_0.stop, grammarAccess.getMethodParamsAndBodyAccess().getBodyAssignment_1());
			}
		)
	)?
)
;


// Rule MethodParamsReturnAndBody
ruleMethodParamsReturnAndBody
@init {
}:
(
	StrictFormalParametersParserRuleCall_0=ruleStrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0.start, $StrictFormalParametersParserRuleCall_0.stop, grammarAccess.getMethodParamsReturnAndBodyAccess().getStrictFormalParametersParserRuleCall_0()); }
	(
		ColonSepReturnTypeRefParserRuleCall_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_1.start, $ColonSepReturnTypeRefParserRuleCall_1.stop, grammarAccess.getMethodParamsReturnAndBodyAccess().getColonSepReturnTypeRefParserRuleCall_1()); }
	)?
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_2_0=ruleBlock{
				announce($BodyBlockParserRuleCall_2_0.start, $BodyBlockParserRuleCall_2_0.stop, grammarAccess.getMethodParamsReturnAndBodyAccess().getBodyAssignment_2());
			}
		)
	)?
)
;


// Rule MethodParamsReturnAndBody
norm1_MethodParamsReturnAndBody
@init {
}:
(
	StrictFormalParametersParserRuleCall_0=norm1_StrictFormalParameters{ announce($StrictFormalParametersParserRuleCall_0.start, $StrictFormalParametersParserRuleCall_0.stop, grammarAccess.getMethodParamsReturnAndBodyAccess().getStrictFormalParametersParserRuleCall_0()); }
	(
		ColonSepReturnTypeRefParserRuleCall_1=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_1.start, $ColonSepReturnTypeRefParserRuleCall_1.stop, grammarAccess.getMethodParamsReturnAndBodyAccess().getColonSepReturnTypeRefParserRuleCall_1()); }
	)?
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_2_0=norm1_Block{
				announce($BodyBlockParserRuleCall_2_0.start, $BodyBlockParserRuleCall_2_0.stop, grammarAccess.getMethodParamsReturnAndBodyAccess().getBodyAssignment_2());
			}
		)
	)?
)
;

// Entry rule entryRuleN4GetterDeclaration
entryRuleN4GetterDeclaration
	:
	ruleN4GetterDeclaration
	EOF;

// Rule N4GetterDeclaration
ruleN4GetterDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			ruleGetterHeader
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			GetterHeaderParserRuleCall_0_0_2=ruleGetterHeader{ announce($GetterHeaderParserRuleCall_0_0_2.start, $GetterHeaderParserRuleCall_0_0_2.stop, grammarAccess.getN4GetterDeclarationAccess().getGetterHeaderParserRuleCall_0_0_2()); }
		)
	)
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_1_0=ruleBlock{
				announce($BodyBlockParserRuleCall_1_0.start, $BodyBlockParserRuleCall_1_0.stop, grammarAccess.getN4GetterDeclarationAccess().getBodyAssignment_1());
			}
		)
	)?
	(
		SemicolonKeyword_2=Semicolon
		 {
			announce($SemicolonKeyword_2, grammarAccess.getN4GetterDeclarationAccess().getSemicolonKeyword_2());
		}
	)?
)
;


// Rule N4GetterDeclaration
norm1_N4GetterDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			norm1_GetterHeader
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			GetterHeaderParserRuleCall_0_0_2=norm1_GetterHeader{ announce($GetterHeaderParserRuleCall_0_0_2.start, $GetterHeaderParserRuleCall_0_0_2.stop, grammarAccess.getN4GetterDeclarationAccess().getGetterHeaderParserRuleCall_0_0_2()); }
		)
	)
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_1_0=norm1_Block{
				announce($BodyBlockParserRuleCall_1_0.start, $BodyBlockParserRuleCall_1_0.stop, grammarAccess.getN4GetterDeclarationAccess().getBodyAssignment_1());
			}
		)
	)?
	(
		SemicolonKeyword_2=Semicolon
		 {
			announce($SemicolonKeyword_2, grammarAccess.getN4GetterDeclarationAccess().getSemicolonKeyword_2());
		}
	)?
)
;


// Rule GetterHeader
ruleGetterHeader
@init {
}:
(
	(
		BogusTypeRefFragmentParserRuleCall_0=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_0.start, $BogusTypeRefFragmentParserRuleCall_0.stop, grammarAccess.getGetterHeaderAccess().getBogusTypeRefFragmentParserRuleCall_0()); }
	)?
	GetKeyword_1=Get
	 {
		announce($GetKeyword_1, grammarAccess.getGetterHeaderAccess().getGetKeyword_1());
	}
	(
		(Break | 
		Case | 
		Catch | 
		Class | 
		Const | 
		Continue | 
		Debugger | 
		Default | 
		Delete | 
		Do | 
		Else | 
		Export | 
		Extends | 
		Finally | 
		For | 
		Function | 
		If | 
		Import | 
		In | 
		Instanceof | 
		New | 
		Return | 
		Super | 
		Switch | 
		This_1 | 
		Throw | 
		Try | 
		Typeof | 
		Var | 
		Void | 
		While | 
		With | 
		Yield | 
		Null | 
		True | 
		False | 
		Enum | 
		Get | 
		Set | 
		Let | 
		Project | 
		External | 
		Abstract | 
		Static | 
		As | 
		From | 
		Constructor | 
		Of | 
		Target | 
		Type | 
		Union | 
		Intersection | 
		This | 
		Promisify | 
		Await | 
		Async | 
		Implements | 
		Interface | 
		Private | 
		Protected | 
		Public | 
		Out | 
		LeftSquareBracket | 
		RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
		(
			DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0=ruleLiteralOrComputedPropertyName{
				announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0.stop, grammarAccess.getGetterHeaderAccess().getDeclaredNameAssignment_2());
			}
		)
	)
	(
		(
			DeclaredOptionalQuestionMarkKeyword_3_0=QuestionMark
			 {
				announce($DeclaredOptionalQuestionMarkKeyword_3_0, grammarAccess.getGetterHeaderAccess().getDeclaredOptionalQuestionMarkKeyword_3_0());
			}
		)
	)?
	LeftParenthesisKeyword_4=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_4, grammarAccess.getGetterHeaderAccess().getLeftParenthesisKeyword_4());
	}
	RightParenthesisKeyword_5=RightParenthesis
	 {
		announce($RightParenthesisKeyword_5, grammarAccess.getGetterHeaderAccess().getRightParenthesisKeyword_5());
	}
	(
		ColonSepDeclaredTypeRefParserRuleCall_6=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_6.start, $ColonSepDeclaredTypeRefParserRuleCall_6.stop, grammarAccess.getGetterHeaderAccess().getColonSepDeclaredTypeRefParserRuleCall_6()); }
	)?
)
;


// Rule GetterHeader
norm1_GetterHeader
@init {
}:
(
	(
		BogusTypeRefFragmentParserRuleCall_0=ruleBogusTypeRefFragment{ announce($BogusTypeRefFragmentParserRuleCall_0.start, $BogusTypeRefFragmentParserRuleCall_0.stop, grammarAccess.getGetterHeaderAccess().getBogusTypeRefFragmentParserRuleCall_0()); }
	)?
	GetKeyword_1=Get
	 {
		announce($GetKeyword_1, grammarAccess.getGetterHeaderAccess().getGetKeyword_1());
	}
	(
		(Break | 
		Case | 
		Catch | 
		Class | 
		Const | 
		Continue | 
		Debugger | 
		Default | 
		Delete | 
		Do | 
		Else | 
		Export | 
		Extends | 
		Finally | 
		For | 
		Function | 
		If | 
		Import | 
		In | 
		Instanceof | 
		New | 
		Return | 
		Super | 
		Switch | 
		This_1 | 
		Throw | 
		Try | 
		Typeof | 
		Var | 
		Void | 
		While | 
		With | 
		Yield | 
		Null | 
		True | 
		False | 
		Enum | 
		Get | 
		Set | 
		Let | 
		Project | 
		External | 
		Abstract | 
		Static | 
		As | 
		From | 
		Constructor | 
		Of | 
		Target | 
		Type | 
		Union | 
		Intersection | 
		This | 
		Promisify | 
		Await | 
		Async | 
		Implements | 
		Interface | 
		Private | 
		Protected | 
		Public | 
		Out | 
		LeftSquareBracket | 
		RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
		(
			DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0=norm1_LiteralOrComputedPropertyName{
				announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0.stop, grammarAccess.getGetterHeaderAccess().getDeclaredNameAssignment_2());
			}
		)
	)
	(
		(
			DeclaredOptionalQuestionMarkKeyword_3_0=QuestionMark
			 {
				announce($DeclaredOptionalQuestionMarkKeyword_3_0, grammarAccess.getGetterHeaderAccess().getDeclaredOptionalQuestionMarkKeyword_3_0());
			}
		)
	)?
	LeftParenthesisKeyword_4=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_4, grammarAccess.getGetterHeaderAccess().getLeftParenthesisKeyword_4());
	}
	RightParenthesisKeyword_5=RightParenthesis
	 {
		announce($RightParenthesisKeyword_5, grammarAccess.getGetterHeaderAccess().getRightParenthesisKeyword_5());
	}
	(
		ColonSepDeclaredTypeRefParserRuleCall_6=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_6.start, $ColonSepDeclaredTypeRefParserRuleCall_6.stop, grammarAccess.getGetterHeaderAccess().getColonSepDeclaredTypeRefParserRuleCall_6()); }
	)?
)
;

// Entry rule entryRuleN4SetterDeclaration
entryRuleN4SetterDeclaration
	:
	ruleN4SetterDeclaration
	EOF;

// Rule N4SetterDeclaration
ruleN4SetterDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Set
			(
				Break
				    |
				Case
				    |
				Catch
				    |
				Class
				    |
				Const
				    |
				Continue
				    |
				Debugger
				    |
				Default
				    |
				Delete
				    |
				Do
				    |
				Else
				    |
				Export
				    |
				Extends
				    |
				Finally
				    |
				For
				    |
				Function
				    |
				If
				    |
				Import
				    |
				In
				    |
				Instanceof
				    |
				New
				    |
				Return
				    |
				Super
				    |
				Switch
				    |
				This_1
				    |
				Throw
				    |
				Try
				    |
				Typeof
				    |
				Var
				    |
				Void
				    |
				While
				    |
				With
				    |
				Yield
				    |
				Null
				    |
				True
				    |
				False
				    |
				Enum
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LeftSquareBracket
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			SetKeyword_0_0_2=Set
			 {
				announce($SetKeyword_0_0_2, grammarAccess.getN4SetterDeclarationAccess().getSetKeyword_0_0_2());
			}
			(
				(Break | 
				Case | 
				Catch | 
				Class | 
				Const | 
				Continue | 
				Debugger | 
				Default | 
				Delete | 
				Do | 
				Else | 
				Export | 
				Extends | 
				Finally | 
				For | 
				Function | 
				If | 
				Import | 
				In | 
				Instanceof | 
				New | 
				Return | 
				Super | 
				Switch | 
				This_1 | 
				Throw | 
				Try | 
				Typeof | 
				Var | 
				Void | 
				While | 
				With | 
				Yield | 
				Null | 
				True | 
				False | 
				Enum | 
				Get | 
				Set | 
				Let | 
				Project | 
				External | 
				Abstract | 
				Static | 
				As | 
				From | 
				Constructor | 
				Of | 
				Target | 
				Type | 
				Union | 
				Intersection | 
				This | 
				Promisify | 
				Await | 
				Async | 
				Implements | 
				Interface | 
				Private | 
				Protected | 
				Public | 
				Out | 
				LeftSquareBracket | 
				RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
				(
					DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0=ruleLiteralOrComputedPropertyName{
						announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0.stop, grammarAccess.getN4SetterDeclarationAccess().getDeclaredNameAssignment_0_0_3());
					}
				)
			)
		)
	)
	(
		(
			DeclaredOptionalQuestionMarkKeyword_1_0=QuestionMark
			 {
				announce($DeclaredOptionalQuestionMarkKeyword_1_0, grammarAccess.getN4SetterDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_0());
			}
		)
	)?
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getN4SetterDeclarationAccess().getLeftParenthesisKeyword_2());
	}
	(
		(
			FparFormalParameterParserRuleCall_3_0=ruleFormalParameter{
				announce($FparFormalParameterParserRuleCall_3_0.start, $FparFormalParameterParserRuleCall_3_0.stop, grammarAccess.getN4SetterDeclarationAccess().getFparAssignment_3());
			}
		)
	)
	RightParenthesisKeyword_4=RightParenthesis
	 {
		announce($RightParenthesisKeyword_4, grammarAccess.getN4SetterDeclarationAccess().getRightParenthesisKeyword_4());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_5_0=ruleBlock{
				announce($BodyBlockParserRuleCall_5_0.start, $BodyBlockParserRuleCall_5_0.stop, grammarAccess.getN4SetterDeclarationAccess().getBodyAssignment_5());
			}
		)
	)?
	(
		SemicolonKeyword_6=Semicolon
		 {
			announce($SemicolonKeyword_6, grammarAccess.getN4SetterDeclarationAccess().getSemicolonKeyword_6());
		}
	)?
)
;


// Rule N4SetterDeclaration
norm1_N4SetterDeclaration
@init {
}:
(
	(
		((
			(
				(
					ruleN4Modifier
				)
			)*
			Set
			(
				Break
				    |
				Case
				    |
				Catch
				    |
				Class
				    |
				Const
				    |
				Continue
				    |
				Debugger
				    |
				Default
				    |
				Delete
				    |
				Do
				    |
				Else
				    |
				Export
				    |
				Extends
				    |
				Finally
				    |
				For
				    |
				Function
				    |
				If
				    |
				Import
				    |
				In
				    |
				Instanceof
				    |
				New
				    |
				Return
				    |
				Super
				    |
				Switch
				    |
				This_1
				    |
				Throw
				    |
				Try
				    |
				Typeof
				    |
				Var
				    |
				Void
				    |
				While
				    |
				With
				    |
				Yield
				    |
				Null
				    |
				True
				    |
				False
				    |
				Enum
				    |
				Get
				    |
				Set
				    |
				Let
				    |
				Project
				    |
				External
				    |
				Abstract
				    |
				Static
				    |
				As
				    |
				From
				    |
				Constructor
				    |
				Of
				    |
				Target
				    |
				Type
				    |
				Union
				    |
				Intersection
				    |
				This
				    |
				Promisify
				    |
				Await
				    |
				Async
				    |
				Implements
				    |
				Interface
				    |
				Private
				    |
				Protected
				    |
				Public
				    |
				Out
				    |
				LeftSquareBracket
				    |
				RULE_IDENTIFIER
				    |
				RULE_STRING
				    |
				RULE_DOUBLE
				    |
				RULE_INT
				    |
				RULE_OCTAL_INT
				    |
				RULE_HEX_INT
				    |
				RULE_SCIENTIFIC_INT
			)
		)
		)=>
		(
			(
				(
					ruleN4Modifier
				)
			)*
			SetKeyword_0_0_2=Set
			 {
				announce($SetKeyword_0_0_2, grammarAccess.getN4SetterDeclarationAccess().getSetKeyword_0_0_2());
			}
			(
				(Break | 
				Case | 
				Catch | 
				Class | 
				Const | 
				Continue | 
				Debugger | 
				Default | 
				Delete | 
				Do | 
				Else | 
				Export | 
				Extends | 
				Finally | 
				For | 
				Function | 
				If | 
				Import | 
				In | 
				Instanceof | 
				New | 
				Return | 
				Super | 
				Switch | 
				This_1 | 
				Throw | 
				Try | 
				Typeof | 
				Var | 
				Void | 
				While | 
				With | 
				Yield | 
				Null | 
				True | 
				False | 
				Enum | 
				Get | 
				Set | 
				Let | 
				Project | 
				External | 
				Abstract | 
				Static | 
				As | 
				From | 
				Constructor | 
				Of | 
				Target | 
				Type | 
				Union | 
				Intersection | 
				This | 
				Promisify | 
				Await | 
				Async | 
				Implements | 
				Interface | 
				Private | 
				Protected | 
				Public | 
				Out | 
				LeftSquareBracket | 
				RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
				(
					DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0=norm1_LiteralOrComputedPropertyName{
						announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0.stop, grammarAccess.getN4SetterDeclarationAccess().getDeclaredNameAssignment_0_0_3());
					}
				)
			)
		)
	)
	(
		(
			DeclaredOptionalQuestionMarkKeyword_1_0=QuestionMark
			 {
				announce($DeclaredOptionalQuestionMarkKeyword_1_0, grammarAccess.getN4SetterDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_0());
			}
		)
	)?
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getN4SetterDeclarationAccess().getLeftParenthesisKeyword_2());
	}
	(
		(
			FparFormalParameterParserRuleCall_3_0=norm1_FormalParameter{
				announce($FparFormalParameterParserRuleCall_3_0.start, $FparFormalParameterParserRuleCall_3_0.stop, grammarAccess.getN4SetterDeclarationAccess().getFparAssignment_3());
			}
		)
	)
	RightParenthesisKeyword_4=RightParenthesis
	 {
		announce($RightParenthesisKeyword_4, grammarAccess.getN4SetterDeclarationAccess().getRightParenthesisKeyword_4());
	}
	(
		((
			LeftCurlyBracket
		)
		)=>
		(
			BodyBlockParserRuleCall_5_0=norm1_Block{
				announce($BodyBlockParserRuleCall_5_0.start, $BodyBlockParserRuleCall_5_0.stop, grammarAccess.getN4SetterDeclarationAccess().getBodyAssignment_5());
			}
		)
	)?
	(
		SemicolonKeyword_6=Semicolon
		 {
			announce($SemicolonKeyword_6, grammarAccess.getN4SetterDeclarationAccess().getSemicolonKeyword_6());
		}
	)?
)
;

// Entry rule entryRuleBindingPattern
entryRuleBindingPattern
	:
	ruleBindingPattern
	EOF;

// Rule BindingPattern
ruleBindingPattern
@init {
}:
(
	ObjectBindingPatternParserRuleCall_0=ruleObjectBindingPattern{ announce($ObjectBindingPatternParserRuleCall_0.start, $ObjectBindingPatternParserRuleCall_0.stop, grammarAccess.getBindingPatternAccess().getObjectBindingPatternParserRuleCall_0()); }
	    |
	ArrayBindingPatternParserRuleCall_1=ruleArrayBindingPattern{ announce($ArrayBindingPatternParserRuleCall_1.start, $ArrayBindingPatternParserRuleCall_1.stop, grammarAccess.getBindingPatternAccess().getArrayBindingPatternParserRuleCall_1()); }
)
;


// Rule BindingPattern
norm1_BindingPattern
@init {
}:
(
	ObjectBindingPatternParserRuleCall_0=norm1_ObjectBindingPattern{ announce($ObjectBindingPatternParserRuleCall_0.start, $ObjectBindingPatternParserRuleCall_0.stop, grammarAccess.getBindingPatternAccess().getObjectBindingPatternParserRuleCall_0()); }
	    |
	ArrayBindingPatternParserRuleCall_1=norm1_ArrayBindingPattern{ announce($ArrayBindingPatternParserRuleCall_1.start, $ArrayBindingPatternParserRuleCall_1.stop, grammarAccess.getBindingPatternAccess().getArrayBindingPatternParserRuleCall_1()); }
)
;

// Entry rule entryRuleObjectBindingPattern
entryRuleObjectBindingPattern
	:
	ruleObjectBindingPattern
	EOF;

// Rule ObjectBindingPattern
ruleObjectBindingPattern
@init {
}:
(
	LeftCurlyBracketKeyword_1=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_1, grammarAccess.getObjectBindingPatternAccess().getLeftCurlyBracketKeyword_1());
	}
	(
		(
			(
				PropertiesBindingPropertyParserRuleCall_2_0_0=ruleBindingProperty{
					announce($PropertiesBindingPropertyParserRuleCall_2_0_0.start, $PropertiesBindingPropertyParserRuleCall_2_0_0.stop, grammarAccess.getObjectBindingPatternAccess().getPropertiesAssignment_2_0());
				}
			)
		)
		(
			CommaKeyword_2_1_0=Comma
			 {
				announce($CommaKeyword_2_1_0, grammarAccess.getObjectBindingPatternAccess().getCommaKeyword_2_1_0());
			}
			(
				(
					PropertiesBindingPropertyParserRuleCall_2_1_1_0=ruleBindingProperty{
						announce($PropertiesBindingPropertyParserRuleCall_2_1_1_0.start, $PropertiesBindingPropertyParserRuleCall_2_1_1_0.stop, grammarAccess.getObjectBindingPatternAccess().getPropertiesAssignment_2_1_1());
					}
				)
			)
		)*
	)?
	RightCurlyBracketKeyword_3=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_3, grammarAccess.getObjectBindingPatternAccess().getRightCurlyBracketKeyword_3());
	}
)
;


// Rule ObjectBindingPattern
norm1_ObjectBindingPattern
@init {
}:
(
	LeftCurlyBracketKeyword_1=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_1, grammarAccess.getObjectBindingPatternAccess().getLeftCurlyBracketKeyword_1());
	}
	(
		(
			(
				PropertiesBindingPropertyParserRuleCall_2_0_0=norm1_BindingProperty{
					announce($PropertiesBindingPropertyParserRuleCall_2_0_0.start, $PropertiesBindingPropertyParserRuleCall_2_0_0.stop, grammarAccess.getObjectBindingPatternAccess().getPropertiesAssignment_2_0());
				}
			)
		)
		(
			CommaKeyword_2_1_0=Comma
			 {
				announce($CommaKeyword_2_1_0, grammarAccess.getObjectBindingPatternAccess().getCommaKeyword_2_1_0());
			}
			(
				(
					PropertiesBindingPropertyParserRuleCall_2_1_1_0=norm1_BindingProperty{
						announce($PropertiesBindingPropertyParserRuleCall_2_1_1_0.start, $PropertiesBindingPropertyParserRuleCall_2_1_1_0.stop, grammarAccess.getObjectBindingPatternAccess().getPropertiesAssignment_2_1_1());
					}
				)
			)
		)*
	)?
	RightCurlyBracketKeyword_3=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_3, grammarAccess.getObjectBindingPatternAccess().getRightCurlyBracketKeyword_3());
	}
)
;

// Entry rule entryRuleArrayBindingPattern
entryRuleArrayBindingPattern
	:
	ruleArrayBindingPattern
	EOF;

// Rule ArrayBindingPattern
ruleArrayBindingPattern
@init {
}:
(
	LeftSquareBracketKeyword_1=LeftSquareBracket
	 {
		announce($LeftSquareBracketKeyword_1, grammarAccess.getArrayBindingPatternAccess().getLeftSquareBracketKeyword_1());
	}
	(
		(
			ElementsElisionParserRuleCall_2_0=ruleElision{
				announce($ElementsElisionParserRuleCall_2_0.start, $ElementsElisionParserRuleCall_2_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_2());
			}
		)
	)*
	(
		(
			(
				ElementsBindingRestElementParserRuleCall_3_0_0=ruleBindingRestElement{
					announce($ElementsBindingRestElementParserRuleCall_3_0_0.start, $ElementsBindingRestElementParserRuleCall_3_0_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_3_0());
				}
			)
		)
		(
			CommaKeyword_3_1_0=Comma
			 {
				announce($CommaKeyword_3_1_0, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_1_0());
			}
			(
				(
					ElementsElisionParserRuleCall_3_1_1_0=ruleElision{
						announce($ElementsElisionParserRuleCall_3_1_1_0.start, $ElementsElisionParserRuleCall_3_1_1_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_3_1_1());
					}
				)
			)*
			(
				(
					ElementsBindingRestElementParserRuleCall_3_1_2_0=ruleBindingRestElement{
						announce($ElementsBindingRestElementParserRuleCall_3_1_2_0.start, $ElementsBindingRestElementParserRuleCall_3_1_2_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_3_1_2());
					}
				)
			)
		)*
		(
			CommaKeyword_3_2_0=Comma
			 {
				announce($CommaKeyword_3_2_0, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_2_0());
			}
			(
				(
					ElementsElisionParserRuleCall_3_2_1_0=ruleElision{
						announce($ElementsElisionParserRuleCall_3_2_1_0.start, $ElementsElisionParserRuleCall_3_2_1_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_3_2_1());
					}
				)
			)*
		)?
	)?
	RightSquareBracketKeyword_4=RightSquareBracket
	 {
		announce($RightSquareBracketKeyword_4, grammarAccess.getArrayBindingPatternAccess().getRightSquareBracketKeyword_4());
	}
)
;


// Rule ArrayBindingPattern
norm1_ArrayBindingPattern
@init {
}:
(
	LeftSquareBracketKeyword_1=LeftSquareBracket
	 {
		announce($LeftSquareBracketKeyword_1, grammarAccess.getArrayBindingPatternAccess().getLeftSquareBracketKeyword_1());
	}
	(
		(
			ElementsElisionParserRuleCall_2_0=ruleElision{
				announce($ElementsElisionParserRuleCall_2_0.start, $ElementsElisionParserRuleCall_2_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_2());
			}
		)
	)*
	(
		(
			(
				ElementsBindingRestElementParserRuleCall_3_0_0=norm1_BindingRestElement{
					announce($ElementsBindingRestElementParserRuleCall_3_0_0.start, $ElementsBindingRestElementParserRuleCall_3_0_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_3_0());
				}
			)
		)
		(
			CommaKeyword_3_1_0=Comma
			 {
				announce($CommaKeyword_3_1_0, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_1_0());
			}
			(
				(
					ElementsElisionParserRuleCall_3_1_1_0=ruleElision{
						announce($ElementsElisionParserRuleCall_3_1_1_0.start, $ElementsElisionParserRuleCall_3_1_1_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_3_1_1());
					}
				)
			)*
			(
				(
					ElementsBindingRestElementParserRuleCall_3_1_2_0=norm1_BindingRestElement{
						announce($ElementsBindingRestElementParserRuleCall_3_1_2_0.start, $ElementsBindingRestElementParserRuleCall_3_1_2_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_3_1_2());
					}
				)
			)
		)*
		(
			CommaKeyword_3_2_0=Comma
			 {
				announce($CommaKeyword_3_2_0, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_2_0());
			}
			(
				(
					ElementsElisionParserRuleCall_3_2_1_0=ruleElision{
						announce($ElementsElisionParserRuleCall_3_2_1_0.start, $ElementsElisionParserRuleCall_3_2_1_0.stop, grammarAccess.getArrayBindingPatternAccess().getElementsAssignment_3_2_1());
					}
				)
			)*
		)?
	)?
	RightSquareBracketKeyword_4=RightSquareBracket
	 {
		announce($RightSquareBracketKeyword_4, grammarAccess.getArrayBindingPatternAccess().getRightSquareBracketKeyword_4());
	}
)
;

// Entry rule entryRuleBindingProperty
entryRuleBindingProperty
	:
	ruleBindingProperty
	EOF;

// Rule BindingProperty
ruleBindingProperty
@init {
}:
(
	(
		(
			((
				(
					(
						ruleLiteralOrComputedPropertyName
					)
				)
				Colon
			)
			)=>
			(
				(
					(
						DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0=ruleLiteralOrComputedPropertyName{
							announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0.stop, grammarAccess.getBindingPropertyAccess().getDeclaredNameAssignment_0_0_0_0());
						}
					)
				)
				ColonKeyword_0_0_0_1=Colon
				 {
					announce($ColonKeyword_0_0_0_1, grammarAccess.getBindingPropertyAccess().getColonKeyword_0_0_0_1());
				}
			)
		)
		(
			(
				ValueBindingElementParserRuleCall_0_1_0=ruleBindingElement{
					announce($ValueBindingElementParserRuleCall_0_1_0.start, $ValueBindingElementParserRuleCall_0_1_0.stop, grammarAccess.getBindingPropertyAccess().getValueAssignment_0_1());
				}
			)
		)
	)
	    |
	(
		(
			ValueSingleNameBindingParserRuleCall_1_0=ruleSingleNameBinding{
				announce($ValueSingleNameBindingParserRuleCall_1_0.start, $ValueSingleNameBindingParserRuleCall_1_0.stop, grammarAccess.getBindingPropertyAccess().getValueAssignment_1());
			}
		)
	)
)
;


// Rule BindingProperty
norm1_BindingProperty
@init {
}:
(
	(
		(
			((
				(
					(
						norm1_LiteralOrComputedPropertyName
					)
				)
				Colon
			)
			)=>
			(
				(
					(
						DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0=norm1_LiteralOrComputedPropertyName{
							announce($DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0.start, $DeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0.stop, grammarAccess.getBindingPropertyAccess().getDeclaredNameAssignment_0_0_0_0());
						}
					)
				)
				ColonKeyword_0_0_0_1=Colon
				 {
					announce($ColonKeyword_0_0_0_1, grammarAccess.getBindingPropertyAccess().getColonKeyword_0_0_0_1());
				}
			)
		)
		(
			(
				ValueBindingElementParserRuleCall_0_1_0=norm1_BindingElement{
					announce($ValueBindingElementParserRuleCall_0_1_0.start, $ValueBindingElementParserRuleCall_0_1_0.stop, grammarAccess.getBindingPropertyAccess().getValueAssignment_0_1());
				}
			)
		)
	)
	    |
	(
		(
			ValueSingleNameBindingParserRuleCall_1_0=norm1_SingleNameBinding{
				announce($ValueSingleNameBindingParserRuleCall_1_0.start, $ValueSingleNameBindingParserRuleCall_1_0.stop, grammarAccess.getBindingPropertyAccess().getValueAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleSingleNameBinding
entryRuleSingleNameBinding
	:
	ruleSingleNameBinding
	EOF;

// Rule SingleNameBinding
ruleSingleNameBinding
@init {
}:
(
	(
		VarDeclVariableDeclarationParserRuleCall_0=norm1_VariableDeclaration{
			announce($VarDeclVariableDeclarationParserRuleCall_0.start, $VarDeclVariableDeclarationParserRuleCall_0.stop, grammarAccess.getSingleNameBindingAccess().getVarDeclAssignment());
		}
	)
)
;


// Rule SingleNameBinding
norm1_SingleNameBinding
@init {
}:
(
	(
		VarDeclVariableDeclarationParserRuleCall_0=norm3_VariableDeclaration{
			announce($VarDeclVariableDeclarationParserRuleCall_0.start, $VarDeclVariableDeclarationParserRuleCall_0.stop, grammarAccess.getSingleNameBindingAccess().getVarDeclAssignment());
		}
	)
)
;


// Rule SingleNameBinding
norm2_SingleNameBinding
@init {
}:
(
	(
		VarDeclVariableDeclarationParserRuleCall_0=norm5_VariableDeclaration{
			announce($VarDeclVariableDeclarationParserRuleCall_0.start, $VarDeclVariableDeclarationParserRuleCall_0.stop, grammarAccess.getSingleNameBindingAccess().getVarDeclAssignment());
		}
	)
)
;


// Rule SingleNameBinding
norm3_SingleNameBinding
@init {
}:
(
	(
		VarDeclVariableDeclarationParserRuleCall_0=norm7_VariableDeclaration{
			announce($VarDeclVariableDeclarationParserRuleCall_0.start, $VarDeclVariableDeclarationParserRuleCall_0.stop, grammarAccess.getSingleNameBindingAccess().getVarDeclAssignment());
		}
	)
)
;

// Entry rule entryRuleBindingElement
entryRuleBindingElement
	:
	ruleBindingElement
	EOF;

// Rule BindingElement
ruleBindingElement
@init {
}:
BindingElementImplParserRuleCall=ruleBindingElementImpl{ announce($BindingElementImplParserRuleCall.start, $BindingElementImplParserRuleCall.stop, grammarAccess.getBindingElementAccess().getBindingElementImplParserRuleCall()); }
;


// Rule BindingElement
norm1_BindingElement
@init {
}:
BindingElementImplParserRuleCall=norm1_BindingElementImpl{ announce($BindingElementImplParserRuleCall.start, $BindingElementImplParserRuleCall.stop, grammarAccess.getBindingElementAccess().getBindingElementImplParserRuleCall()); }
;

// Entry rule entryRuleBindingRestElement
entryRuleBindingRestElement
	:
	ruleBindingRestElement
	EOF;

// Rule BindingRestElement
ruleBindingRestElement
@init {
}:
(
	(
		(
			RestFullStopFullStopFullStopKeyword_0_0=FullStopFullStopFullStop
			 {
				announce($RestFullStopFullStopFullStopKeyword_0_0, grammarAccess.getBindingRestElementAccess().getRestFullStopFullStopFullStopKeyword_0_0());
			}
		)
	)?
	BindingElementImplParserRuleCall_1=ruleBindingElementImpl{ announce($BindingElementImplParserRuleCall_1.start, $BindingElementImplParserRuleCall_1.stop, grammarAccess.getBindingRestElementAccess().getBindingElementImplParserRuleCall_1()); }
)
;


// Rule BindingRestElement
norm1_BindingRestElement
@init {
}:
(
	(
		(
			RestFullStopFullStopFullStopKeyword_0_0=FullStopFullStopFullStop
			 {
				announce($RestFullStopFullStopFullStopKeyword_0_0, grammarAccess.getBindingRestElementAccess().getRestFullStopFullStopFullStopKeyword_0_0());
			}
		)
	)?
	BindingElementImplParserRuleCall_1=norm1_BindingElementImpl{ announce($BindingElementImplParserRuleCall_1.start, $BindingElementImplParserRuleCall_1.stop, grammarAccess.getBindingRestElementAccess().getBindingElementImplParserRuleCall_1()); }
)
;


// Rule BindingElementImpl
ruleBindingElementImpl
@init {
}:
(
	(
		(
			((
				(
					ruleBindingPattern
				)
			)
			)=>
			(
				(
					NestedPatternBindingPatternParserRuleCall_0_0_0_0=ruleBindingPattern{
						announce($NestedPatternBindingPatternParserRuleCall_0_0_0_0.start, $NestedPatternBindingPatternParserRuleCall_0_0_0_0.stop, grammarAccess.getBindingElementImplAccess().getNestedPatternAssignment_0_0_0());
					}
				)
			)
		)
		(
			EqualsSignKeyword_0_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_0_1_0, grammarAccess.getBindingElementImplAccess().getEqualsSignKeyword_0_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_0_1_1_0=norm1_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_0_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_0_1_1_0.stop, grammarAccess.getBindingElementImplAccess().getExpressionAssignment_0_1_1());
					}
				)
			)
		)?
	)
	    |
	(
		(
			VarDeclVariableDeclarationParserRuleCall_1_0=norm5_VariableDeclaration{
				announce($VarDeclVariableDeclarationParserRuleCall_1_0.start, $VarDeclVariableDeclarationParserRuleCall_1_0.stop, grammarAccess.getBindingElementImplAccess().getVarDeclAssignment_1());
			}
		)
	)
)
;


// Rule BindingElementImpl
norm1_BindingElementImpl
@init {
}:
(
	(
		(
			((
				(
					norm1_BindingPattern
				)
			)
			)=>
			(
				(
					NestedPatternBindingPatternParserRuleCall_0_0_0_0=norm1_BindingPattern{
						announce($NestedPatternBindingPatternParserRuleCall_0_0_0_0.start, $NestedPatternBindingPatternParserRuleCall_0_0_0_0.stop, grammarAccess.getBindingElementImplAccess().getNestedPatternAssignment_0_0_0());
					}
				)
			)
		)
		(
			EqualsSignKeyword_0_1_0=EqualsSign
			 {
				announce($EqualsSignKeyword_0_1_0, grammarAccess.getBindingElementImplAccess().getEqualsSignKeyword_0_1_0());
			}
			(
				(
					ExpressionAssignmentExpressionParserRuleCall_0_1_1_0=norm3_AssignmentExpression{
						announce($ExpressionAssignmentExpressionParserRuleCall_0_1_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_0_1_1_0.stop, grammarAccess.getBindingElementImplAccess().getExpressionAssignment_0_1_1());
					}
				)
			)
		)?
	)
	    |
	(
		(
			VarDeclVariableDeclarationParserRuleCall_1_0=norm7_VariableDeclaration{
				announce($VarDeclVariableDeclarationParserRuleCall_1_0.start, $VarDeclVariableDeclarationParserRuleCall_1_0.stop, grammarAccess.getBindingElementImplAccess().getVarDeclAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleElision
entryRuleElision
	:
	ruleElision
	EOF;

// Rule Elision
ruleElision
@init {
}:
(
	CommaKeyword_1=Comma
	 {
		announce($CommaKeyword_1, grammarAccess.getElisionAccess().getCommaKeyword_1());
	}
)
;

// Entry rule entryRuleLiteralOrComputedPropertyName
entryRuleLiteralOrComputedPropertyName
	:
	ruleLiteralOrComputedPropertyName
	EOF;

// Rule LiteralOrComputedPropertyName
ruleLiteralOrComputedPropertyName
@init {
}:
(
	(
		(
			LiteralNameIdentifierNameParserRuleCall_0_0=ruleIdentifierName{
				announce($LiteralNameIdentifierNameParserRuleCall_0_0.start, $LiteralNameIdentifierNameParserRuleCall_0_0.stop, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameAssignment_0());
			}
		)
	)
	    |
	(
		(
			LiteralNameSTRINGTerminalRuleCall_1_0=RULE_STRING{
				announce($LiteralNameSTRINGTerminalRuleCall_1_0, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameAssignment_1());
			}
		)
	)
	    |
	(
		(
			LiteralNameNumericLiteralAsStringParserRuleCall_2_0=ruleNumericLiteralAsString{
				announce($LiteralNameNumericLiteralAsStringParserRuleCall_2_0.start, $LiteralNameNumericLiteralAsStringParserRuleCall_2_0.stop, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameAssignment_2());
			}
		)
	)
	    |
	(
		LeftSquareBracketKeyword_3_0=LeftSquareBracket
		 {
			announce($LeftSquareBracketKeyword_3_0, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLeftSquareBracketKeyword_3_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_3_1_0=norm1_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_3_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_1_0.stop, grammarAccess.getLiteralOrComputedPropertyNameAccess().getExpressionAssignment_3_1());
				}
			)
		)
		RightSquareBracketKeyword_3_2=RightSquareBracket
		 {
			announce($RightSquareBracketKeyword_3_2, grammarAccess.getLiteralOrComputedPropertyNameAccess().getRightSquareBracketKeyword_3_2());
		}
	)
)
;


// Rule LiteralOrComputedPropertyName
norm1_LiteralOrComputedPropertyName
@init {
}:
(
	(
		(
			LiteralNameIdentifierNameParserRuleCall_0_0=ruleIdentifierName{
				announce($LiteralNameIdentifierNameParserRuleCall_0_0.start, $LiteralNameIdentifierNameParserRuleCall_0_0.stop, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameAssignment_0());
			}
		)
	)
	    |
	(
		(
			LiteralNameSTRINGTerminalRuleCall_1_0=RULE_STRING{
				announce($LiteralNameSTRINGTerminalRuleCall_1_0, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameAssignment_1());
			}
		)
	)
	    |
	(
		(
			LiteralNameNumericLiteralAsStringParserRuleCall_2_0=ruleNumericLiteralAsString{
				announce($LiteralNameNumericLiteralAsStringParserRuleCall_2_0.start, $LiteralNameNumericLiteralAsStringParserRuleCall_2_0.stop, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameAssignment_2());
			}
		)
	)
	    |
	(
		LeftSquareBracketKeyword_3_0=LeftSquareBracket
		 {
			announce($LeftSquareBracketKeyword_3_0, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLeftSquareBracketKeyword_3_0());
		}
		(
			(
				ExpressionAssignmentExpressionParserRuleCall_3_1_0=norm3_AssignmentExpression{
					announce($ExpressionAssignmentExpressionParserRuleCall_3_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_3_1_0.stop, grammarAccess.getLiteralOrComputedPropertyNameAccess().getExpressionAssignment_3_1());
				}
			)
		)
		RightSquareBracketKeyword_3_2=RightSquareBracket
		 {
			announce($RightSquareBracketKeyword_3_2, grammarAccess.getLiteralOrComputedPropertyNameAccess().getRightSquareBracketKeyword_3_2());
		}
	)
)
;

// Entry rule entryRuleJSXElement
entryRuleJSXElement
	:
	ruleJSXElement
	EOF;

// Rule JSXElement
ruleJSXElement
@init {
}:
(
	LessThanSignKeyword_0=LessThanSign
	 {
		announce($LessThanSignKeyword_0, grammarAccess.getJSXElementAccess().getLessThanSignKeyword_0());
	}
	(
		(
			JsxElementNameJSXElementNameParserRuleCall_1_0=ruleJSXElementName{
				announce($JsxElementNameJSXElementNameParserRuleCall_1_0.start, $JsxElementNameJSXElementNameParserRuleCall_1_0.stop, grammarAccess.getJSXElementAccess().getJsxElementNameAssignment_1());
			}
		)
	)
	JSXAttributesParserRuleCall_2=ruleJSXAttributes{ announce($JSXAttributesParserRuleCall_2.start, $JSXAttributesParserRuleCall_2.stop, grammarAccess.getJSXElementAccess().getJSXAttributesParserRuleCall_2()); }
	(
		(
			GreaterThanSignKeyword_3_0_0=GreaterThanSign
			 {
				announce($GreaterThanSignKeyword_3_0_0, grammarAccess.getJSXElementAccess().getGreaterThanSignKeyword_3_0_0());
			}
			(
				(
					JsxChildrenJSXChildParserRuleCall_3_0_1_0=ruleJSXChild{
						announce($JsxChildrenJSXChildParserRuleCall_3_0_1_0.start, $JsxChildrenJSXChildParserRuleCall_3_0_1_0.stop, grammarAccess.getJSXElementAccess().getJsxChildrenAssignment_3_0_1());
					}
				)
			)*
			LessThanSignKeyword_3_0_2=LessThanSign
			 {
				announce($LessThanSignKeyword_3_0_2, grammarAccess.getJSXElementAccess().getLessThanSignKeyword_3_0_2());
			}
			SolidusKeyword_3_0_3=Solidus
			 {
				announce($SolidusKeyword_3_0_3, grammarAccess.getJSXElementAccess().getSolidusKeyword_3_0_3());
			}
			(
				(
					JsxClosingNameJSXElementNameParserRuleCall_3_0_4_0=ruleJSXElementName{
						announce($JsxClosingNameJSXElementNameParserRuleCall_3_0_4_0.start, $JsxClosingNameJSXElementNameParserRuleCall_3_0_4_0.stop, grammarAccess.getJSXElementAccess().getJsxClosingNameAssignment_3_0_4());
					}
				)
			)
			GreaterThanSignKeyword_3_0_5=GreaterThanSign
			 {
				announce($GreaterThanSignKeyword_3_0_5, grammarAccess.getJSXElementAccess().getGreaterThanSignKeyword_3_0_5());
			}
		)
		    |
		(
			SolidusKeyword_3_1_0=Solidus
			 {
				announce($SolidusKeyword_3_1_0, grammarAccess.getJSXElementAccess().getSolidusKeyword_3_1_0());
			}
			GreaterThanSignKeyword_3_1_1=GreaterThanSign
			 {
				announce($GreaterThanSignKeyword_3_1_1, grammarAccess.getJSXElementAccess().getGreaterThanSignKeyword_3_1_1());
			}
		)
	)
)
;

// Entry rule entryRuleJSXFragment
entryRuleJSXFragment
	:
	ruleJSXFragment
	EOF;

// Rule JSXFragment
ruleJSXFragment
@init {
}:
(
	LessThanSignKeyword_1=LessThanSign
	 {
		announce($LessThanSignKeyword_1, grammarAccess.getJSXFragmentAccess().getLessThanSignKeyword_1());
	}
	GreaterThanSignKeyword_2=GreaterThanSign
	 {
		announce($GreaterThanSignKeyword_2, grammarAccess.getJSXFragmentAccess().getGreaterThanSignKeyword_2());
	}
	(
		(
			JsxChildrenJSXChildParserRuleCall_3_0=ruleJSXChild{
				announce($JsxChildrenJSXChildParserRuleCall_3_0.start, $JsxChildrenJSXChildParserRuleCall_3_0.stop, grammarAccess.getJSXFragmentAccess().getJsxChildrenAssignment_3());
			}
		)
	)*
	LessThanSignKeyword_4=LessThanSign
	 {
		announce($LessThanSignKeyword_4, grammarAccess.getJSXFragmentAccess().getLessThanSignKeyword_4());
	}
	SolidusKeyword_5=Solidus
	 {
		announce($SolidusKeyword_5, grammarAccess.getJSXFragmentAccess().getSolidusKeyword_5());
	}
	GreaterThanSignKeyword_6=GreaterThanSign
	 {
		announce($GreaterThanSignKeyword_6, grammarAccess.getJSXFragmentAccess().getGreaterThanSignKeyword_6());
	}
)
;

// Entry rule entryRuleJSXChild
entryRuleJSXChild
	:
	ruleJSXChild
	EOF;

// Rule JSXChild
ruleJSXChild
@init {
}:
(
	JSXElementParserRuleCall_0=ruleJSXElement{ announce($JSXElementParserRuleCall_0.start, $JSXElementParserRuleCall_0.stop, grammarAccess.getJSXChildAccess().getJSXElementParserRuleCall_0()); }
	    |
	JSXFragmentParserRuleCall_1=ruleJSXFragment{ announce($JSXFragmentParserRuleCall_1.start, $JSXFragmentParserRuleCall_1.stop, grammarAccess.getJSXChildAccess().getJSXFragmentParserRuleCall_1()); }
	    |
	JSXExpressionParserRuleCall_2=ruleJSXExpression{ announce($JSXExpressionParserRuleCall_2.start, $JSXExpressionParserRuleCall_2.stop, grammarAccess.getJSXChildAccess().getJSXExpressionParserRuleCall_2()); }
)
;

// Entry rule entryRuleJSXExpression
entryRuleJSXExpression
	:
	ruleJSXExpression
	EOF;

// Rule JSXExpression
ruleJSXExpression
@init {
}:
(
	LeftCurlyBracketKeyword_0=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_0, grammarAccess.getJSXExpressionAccess().getLeftCurlyBracketKeyword_0());
	}
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_1_0=ruleAssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_1_0.start, $ExpressionAssignmentExpressionParserRuleCall_1_0.stop, grammarAccess.getJSXExpressionAccess().getExpressionAssignment_1());
			}
		)
	)
	RightCurlyBracketKeyword_2=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_2, grammarAccess.getJSXExpressionAccess().getRightCurlyBracketKeyword_2());
	}
)
;

// Entry rule entryRuleJSXElementName
entryRuleJSXElementName
	:
	ruleJSXElementName
	EOF;

// Rule JSXElementName
ruleJSXElementName
@init {
}:
(
	(
		ExpressionJSXElementNameExpressionParserRuleCall_0=ruleJSXElementNameExpression{
			announce($ExpressionJSXElementNameExpressionParserRuleCall_0.start, $ExpressionJSXElementNameExpressionParserRuleCall_0.stop, grammarAccess.getJSXElementNameAccess().getExpressionAssignment());
		}
	)
)
;

// Entry rule entryRuleJSXElementNameExpression
entryRuleJSXElementNameExpression
	:
	ruleJSXElementNameExpression
	EOF;

// Rule JSXElementNameExpression
ruleJSXElementNameExpression
@init {
}:
(
	IdentifierRefParserRuleCall_0=ruleIdentifierRef{ announce($IdentifierRefParserRuleCall_0.start, $IdentifierRefParserRuleCall_0.stop, grammarAccess.getJSXElementNameExpressionAccess().getIdentifierRefParserRuleCall_0()); }
	(
		ParameterizedPropertyAccessExpressionTailParserRuleCall_1_1=ruleParameterizedPropertyAccessExpressionTail{ announce($ParameterizedPropertyAccessExpressionTailParserRuleCall_1_1.start, $ParameterizedPropertyAccessExpressionTailParserRuleCall_1_1.stop, grammarAccess.getJSXElementNameExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_1()); }
	)*
)
;


// Rule JSXAttributes
ruleJSXAttributes
@init {
}:
(
	(
		JsxAttributesJSXAttributeParserRuleCall_0=ruleJSXAttribute{
			announce($JsxAttributesJSXAttributeParserRuleCall_0.start, $JsxAttributesJSXAttributeParserRuleCall_0.stop, grammarAccess.getJSXAttributesAccess().getJsxAttributesAssignment());
		}
	)
)*
;

// Entry rule entryRuleJSXAttribute
entryRuleJSXAttribute
	:
	ruleJSXAttribute
	EOF;

// Rule JSXAttribute
ruleJSXAttribute
@init {
}:
(
	JSXSpreadAttributeParserRuleCall_0=ruleJSXSpreadAttribute{ announce($JSXSpreadAttributeParserRuleCall_0.start, $JSXSpreadAttributeParserRuleCall_0.stop, grammarAccess.getJSXAttributeAccess().getJSXSpreadAttributeParserRuleCall_0()); }
	    |
	JSXPropertyAttributeParserRuleCall_1=ruleJSXPropertyAttribute{ announce($JSXPropertyAttributeParserRuleCall_1.start, $JSXPropertyAttributeParserRuleCall_1.stop, grammarAccess.getJSXAttributeAccess().getJSXPropertyAttributeParserRuleCall_1()); }
)
;

// Entry rule entryRuleJSXSpreadAttribute
entryRuleJSXSpreadAttribute
	:
	ruleJSXSpreadAttribute
	EOF;

// Rule JSXSpreadAttribute
ruleJSXSpreadAttribute
@init {
}:
(
	LeftCurlyBracketKeyword_0=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_0, grammarAccess.getJSXSpreadAttributeAccess().getLeftCurlyBracketKeyword_0());
	}
	FullStopFullStopFullStopKeyword_1=FullStopFullStopFullStop
	 {
		announce($FullStopFullStopFullStopKeyword_1, grammarAccess.getJSXSpreadAttributeAccess().getFullStopFullStopFullStopKeyword_1());
	}
	(
		(
			ExpressionAssignmentExpressionParserRuleCall_2_0=ruleAssignmentExpression{
				announce($ExpressionAssignmentExpressionParserRuleCall_2_0.start, $ExpressionAssignmentExpressionParserRuleCall_2_0.stop, grammarAccess.getJSXSpreadAttributeAccess().getExpressionAssignment_2());
			}
		)
	)
	RightCurlyBracketKeyword_3=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_3, grammarAccess.getJSXSpreadAttributeAccess().getRightCurlyBracketKeyword_3());
	}
)
;

// Entry rule entryRuleJSXPropertyAttribute
entryRuleJSXPropertyAttribute
	:
	ruleJSXPropertyAttribute
	EOF;

// Rule JSXPropertyAttribute
ruleJSXPropertyAttribute
@init {
}:
(
	(
		(
			PropertyIdentifiableElementIdentifierNameParserRuleCall_0_0_1=ruleIdentifierName{
				announce($PropertyIdentifiableElementIdentifierNameParserRuleCall_0_0_1.start, $PropertyIdentifiableElementIdentifierNameParserRuleCall_0_0_1.stop, grammarAccess.getJSXPropertyAttributeAccess().getPropertyAssignment_0());
			}
		)
	)
	(
		EqualsSignKeyword_1_0=EqualsSign
		 {
			announce($EqualsSignKeyword_1_0, grammarAccess.getJSXPropertyAttributeAccess().getEqualsSignKeyword_1_0());
		}
		(
			(
				(
					JsxAttributeValueStringLiteralParserRuleCall_1_1_0_0=ruleStringLiteral{
						announce($JsxAttributeValueStringLiteralParserRuleCall_1_1_0_0.start, $JsxAttributeValueStringLiteralParserRuleCall_1_1_0_0.stop, grammarAccess.getJSXPropertyAttributeAccess().getJsxAttributeValueAssignment_1_1_0());
					}
				)
			)
			    |
			(
				LeftCurlyBracketKeyword_1_1_1_0=LeftCurlyBracket
				 {
					announce($LeftCurlyBracketKeyword_1_1_1_0, grammarAccess.getJSXPropertyAttributeAccess().getLeftCurlyBracketKeyword_1_1_1_0());
				}
				(
					(
						JsxAttributeValueAssignmentExpressionParserRuleCall_1_1_1_1_0=ruleAssignmentExpression{
							announce($JsxAttributeValueAssignmentExpressionParserRuleCall_1_1_1_1_0.start, $JsxAttributeValueAssignmentExpressionParserRuleCall_1_1_1_1_0.stop, grammarAccess.getJSXPropertyAttributeAccess().getJsxAttributeValueAssignment_1_1_1_1());
						}
					)
				)
				RightCurlyBracketKeyword_1_1_1_2=RightCurlyBracket
				 {
					announce($RightCurlyBracketKeyword_1_1_1_2, grammarAccess.getJSXPropertyAttributeAccess().getRightCurlyBracketKeyword_1_1_1_2());
				}
			)
		)
	)?
)
;


// Rule VersionDeclaration
ruleVersionDeclaration
@init {
}:
(
	(
		DeclaredVersionVERSIONTerminalRuleCall_0=RULE_VERSION{
			announce($DeclaredVersionVERSIONTerminalRuleCall_0, grammarAccess.getVersionDeclarationAccess().getDeclaredVersionAssignment());
		}
	)
)
;

// Entry rule entryRuleTypeRef
entryRuleTypeRef
	:
	ruleTypeRef
	EOF;

// Rule TypeRef
ruleTypeRef
@init {
}:
(
	IntersectionTypeExpressionParserRuleCall_0=ruleIntersectionTypeExpression{ announce($IntersectionTypeExpressionParserRuleCall_0.start, $IntersectionTypeExpressionParserRuleCall_0.stop, grammarAccess.getTypeRefAccess().getIntersectionTypeExpressionParserRuleCall_0()); }
	(
		(
			VerticalLineKeyword_1_1_0=VerticalLine
			 {
				announce($VerticalLineKeyword_1_1_0, grammarAccess.getTypeRefAccess().getVerticalLineKeyword_1_1_0());
			}
			(
				(
					TypeRefsIntersectionTypeExpressionParserRuleCall_1_1_1_0=ruleIntersectionTypeExpression{
						announce($TypeRefsIntersectionTypeExpressionParserRuleCall_1_1_1_0.start, $TypeRefsIntersectionTypeExpressionParserRuleCall_1_1_1_0.stop, grammarAccess.getTypeRefAccess().getTypeRefsAssignment_1_1_1());
					}
				)
			)
		)+
	)?
)
;

// Entry rule entryRuleIntersectionTypeExpression
entryRuleIntersectionTypeExpression
	:
	ruleIntersectionTypeExpression
	EOF;

// Rule IntersectionTypeExpression
ruleIntersectionTypeExpression
@init {
}:
(
	ArrayTypeExpressionParserRuleCall_0=ruleArrayTypeExpression{ announce($ArrayTypeExpressionParserRuleCall_0.start, $ArrayTypeExpressionParserRuleCall_0.stop, grammarAccess.getIntersectionTypeExpressionAccess().getArrayTypeExpressionParserRuleCall_0()); }
	(
		(
			AmpersandKeyword_1_1_0=Ampersand
			 {
				announce($AmpersandKeyword_1_1_0, grammarAccess.getIntersectionTypeExpressionAccess().getAmpersandKeyword_1_1_0());
			}
			(
				(
					TypeRefsArrayTypeExpressionParserRuleCall_1_1_1_0=ruleArrayTypeExpression{
						announce($TypeRefsArrayTypeExpressionParserRuleCall_1_1_1_0.start, $TypeRefsArrayTypeExpressionParserRuleCall_1_1_1_0.stop, grammarAccess.getIntersectionTypeExpressionAccess().getTypeRefsAssignment_1_1_1());
					}
				)
			)
		)+
	)?
)
;

// Entry rule entryRuleArrayTypeExpression
entryRuleArrayTypeExpression
	:
	ruleArrayTypeExpression
	EOF;

// Rule ArrayTypeExpression
ruleArrayTypeExpression
@init {
}:
(
	(
		(
			(
				TypeArgsWildcardOldNotationWithoutBoundParserRuleCall_0_1_0=ruleWildcardOldNotationWithoutBound{
					announce($TypeArgsWildcardOldNotationWithoutBoundParserRuleCall_0_1_0.start, $TypeArgsWildcardOldNotationWithoutBoundParserRuleCall_0_1_0.stop, grammarAccess.getArrayTypeExpressionAccess().getTypeArgsAssignment_0_1());
				}
			)
		)
		(
			(
				ArrayTypeExpressionLeftSquareBracketKeyword_0_2_0=LeftSquareBracket
				 {
					announce($ArrayTypeExpressionLeftSquareBracketKeyword_0_2_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_2_0());
				}
			)
		)
		RightSquareBracketKeyword_0_3=RightSquareBracket
		 {
			announce($RightSquareBracketKeyword_0_3, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_0_3());
		}
		(
			((
				(
					(
						LeftSquareBracket
					)
				)
				RightSquareBracket
			)
			)=>
			(
				(
					(
						ArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0=LeftSquareBracket
						 {
							announce($ArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0());
						}
					)
				)
				RightSquareBracketKeyword_0_4_0_2=RightSquareBracket
				 {
					announce($RightSquareBracketKeyword_0_4_0_2, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_0_4_0_2());
				}
			)
		)*
	)
	    |
	(
		LeftParenthesisKeyword_1_1=LeftParenthesis
		 {
			announce($LeftParenthesisKeyword_1_1, grammarAccess.getArrayTypeExpressionAccess().getLeftParenthesisKeyword_1_1());
		}
		(
			(
				TypeArgsWildcardParserRuleCall_1_2_0=ruleWildcard{
					announce($TypeArgsWildcardParserRuleCall_1_2_0.start, $TypeArgsWildcardParserRuleCall_1_2_0.stop, grammarAccess.getArrayTypeExpressionAccess().getTypeArgsAssignment_1_2());
				}
			)
		)
		RightParenthesisKeyword_1_3=RightParenthesis
		 {
			announce($RightParenthesisKeyword_1_3, grammarAccess.getArrayTypeExpressionAccess().getRightParenthesisKeyword_1_3());
		}
		(
			(
				ArrayTypeExpressionLeftSquareBracketKeyword_1_4_0=LeftSquareBracket
				 {
					announce($ArrayTypeExpressionLeftSquareBracketKeyword_1_4_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_4_0());
				}
			)
		)
		RightSquareBracketKeyword_1_5=RightSquareBracket
		 {
			announce($RightSquareBracketKeyword_1_5, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_1_5());
		}
		(
			((
				(
					(
						LeftSquareBracket
					)
				)
				RightSquareBracket
			)
			)=>
			(
				(
					(
						ArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0=LeftSquareBracket
						 {
							announce($ArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0());
						}
					)
				)
				RightSquareBracketKeyword_1_6_0_2=RightSquareBracket
				 {
					announce($RightSquareBracketKeyword_1_6_0_2, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_1_6_0_2());
				}
			)
		)*
	)
	    |
	(
		PrimaryTypeExpressionParserRuleCall_2_0=rulePrimaryTypeExpression{ announce($PrimaryTypeExpressionParserRuleCall_2_0.start, $PrimaryTypeExpressionParserRuleCall_2_0.stop, grammarAccess.getArrayTypeExpressionAccess().getPrimaryTypeExpressionParserRuleCall_2_0()); }
		(
			((
				(
					(
						LeftSquareBracket
					)
				)
				RightSquareBracket
			)
			)=>
			(
				(
					(
						ArrayTypeExpressionLeftSquareBracketKeyword_2_1_0_1_0=LeftSquareBracket
						 {
							announce($ArrayTypeExpressionLeftSquareBracketKeyword_2_1_0_1_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_2_1_0_1_0());
						}
					)
				)
				RightSquareBracketKeyword_2_1_0_2=RightSquareBracket
				 {
					announce($RightSquareBracketKeyword_2_1_0_2, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_2_1_0_2());
				}
			)
		)*
	)
)
;

// Entry rule entryRulePrimaryTypeExpression
entryRulePrimaryTypeExpression
	:
	rulePrimaryTypeExpression
	EOF;

// Rule PrimaryTypeExpression
rulePrimaryTypeExpression
@init {
}:
(
	(
		((
			LeftParenthesis
			ruleTAnonymousFormalParameterList
			RightParenthesis
			EqualsSignGreaterThanSign
		)
		)=>
		ArrowFunctionTypeExpressionParserRuleCall_0=ruleArrowFunctionTypeExpression{ announce($ArrowFunctionTypeExpressionParserRuleCall_0.start, $ArrowFunctionTypeExpressionParserRuleCall_0.stop, grammarAccess.getPrimaryTypeExpressionAccess().getArrowFunctionTypeExpressionParserRuleCall_0()); }
	)
	    |
	IterableTypeExpressionParserRuleCall_1=ruleIterableTypeExpression{ announce($IterableTypeExpressionParserRuleCall_1.start, $IterableTypeExpressionParserRuleCall_1.stop, grammarAccess.getPrimaryTypeExpressionAccess().getIterableTypeExpressionParserRuleCall_1()); }
	    |
	TypeRefWithModifiersParserRuleCall_2=ruleTypeRefWithModifiers{ announce($TypeRefWithModifiersParserRuleCall_2.start, $TypeRefWithModifiersParserRuleCall_2.stop, grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefWithModifiersParserRuleCall_2()); }
	    |
	(
		LeftParenthesisKeyword_3_0=LeftParenthesis
		 {
			announce($LeftParenthesisKeyword_3_0, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_3_0());
		}
		TypeRefParserRuleCall_3_1=ruleTypeRef{ announce($TypeRefParserRuleCall_3_1.start, $TypeRefParserRuleCall_3_1.stop, grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefParserRuleCall_3_1()); }
		RightParenthesisKeyword_3_2=RightParenthesis
		 {
			announce($RightParenthesisKeyword_3_2, grammarAccess.getPrimaryTypeExpressionAccess().getRightParenthesisKeyword_3_2());
		}
	)
)
;

// Entry rule entryRuleTypeRefWithModifiers
entryRuleTypeRefWithModifiers
	:
	ruleTypeRefWithModifiers
	EOF;

// Rule TypeRefWithModifiers
ruleTypeRefWithModifiers
@init {
}:
(
	TypeRefWithoutModifiersParserRuleCall_0=ruleTypeRefWithoutModifiers{ announce($TypeRefWithoutModifiersParserRuleCall_0.start, $TypeRefWithoutModifiersParserRuleCall_0.stop, grammarAccess.getTypeRefWithModifiersAccess().getTypeRefWithoutModifiersParserRuleCall_0()); }
	(
		((
			QuestionMark
		)
		)=>
		(
			FollowedByQuestionMarkQuestionMarkKeyword_1_0=QuestionMark
			 {
				announce($FollowedByQuestionMarkQuestionMarkKeyword_1_0, grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0());
			}
		)
	)?
)
;

// Entry rule entryRuleTypeRefWithoutModifiers
entryRuleTypeRefWithoutModifiers
	:
	ruleTypeRefWithoutModifiers
	EOF;

// Rule TypeRefWithoutModifiers
ruleTypeRefWithoutModifiers
@init {
}:
(
	(
		(
			ParameterizedTypeRefParserRuleCall_0_0_0=ruleParameterizedTypeRef{ announce($ParameterizedTypeRefParserRuleCall_0_0_0.start, $ParameterizedTypeRefParserRuleCall_0_0_0.stop, grammarAccess.getTypeRefWithoutModifiersAccess().getParameterizedTypeRefParserRuleCall_0_0_0()); }
			    |
			ThisTypeRefParserRuleCall_0_0_1=ruleThisTypeRef{ announce($ThisTypeRefParserRuleCall_0_0_1.start, $ThisTypeRefParserRuleCall_0_0_1.stop, grammarAccess.getTypeRefWithoutModifiersAccess().getThisTypeRefParserRuleCall_0_0_1()); }
		)
		(
			((
				PlusSign
			)
			)=>
			(
				DynamicPlusSignKeyword_0_1_0=PlusSign
				 {
					announce($DynamicPlusSignKeyword_0_1_0, grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicPlusSignKeyword_0_1_0());
				}
			)
		)?
	)
	    |
	TypeTypeRefParserRuleCall_1=ruleTypeTypeRef{ announce($TypeTypeRefParserRuleCall_1.start, $TypeTypeRefParserRuleCall_1.stop, grammarAccess.getTypeRefWithoutModifiersAccess().getTypeTypeRefParserRuleCall_1()); }
	    |
	FunctionTypeExpressionOLDParserRuleCall_2=ruleFunctionTypeExpressionOLD{ announce($FunctionTypeExpressionOLDParserRuleCall_2.start, $FunctionTypeExpressionOLDParserRuleCall_2.stop, grammarAccess.getTypeRefWithoutModifiersAccess().getFunctionTypeExpressionOLDParserRuleCall_2()); }
	    |
	UnionTypeExpressionOLDParserRuleCall_3=ruleUnionTypeExpressionOLD{ announce($UnionTypeExpressionOLDParserRuleCall_3.start, $UnionTypeExpressionOLDParserRuleCall_3.stop, grammarAccess.getTypeRefWithoutModifiersAccess().getUnionTypeExpressionOLDParserRuleCall_3()); }
	    |
	IntersectionTypeExpressionOLDParserRuleCall_4=ruleIntersectionTypeExpressionOLD{ announce($IntersectionTypeExpressionOLDParserRuleCall_4.start, $IntersectionTypeExpressionOLDParserRuleCall_4.stop, grammarAccess.getTypeRefWithoutModifiersAccess().getIntersectionTypeExpressionOLDParserRuleCall_4()); }
)
;

// Entry rule entryRuleTypeRefFunctionTypeExpression
entryRuleTypeRefFunctionTypeExpression
	:
	ruleTypeRefFunctionTypeExpression
	EOF;

// Rule TypeRefFunctionTypeExpression
ruleTypeRefFunctionTypeExpression
@init {
}:
(
	ParameterizedTypeRefParserRuleCall_0=ruleParameterizedTypeRef{ announce($ParameterizedTypeRefParserRuleCall_0.start, $ParameterizedTypeRefParserRuleCall_0.stop, grammarAccess.getTypeRefFunctionTypeExpressionAccess().getParameterizedTypeRefParserRuleCall_0()); }
	    |
	IterableTypeExpressionParserRuleCall_1=ruleIterableTypeExpression{ announce($IterableTypeExpressionParserRuleCall_1.start, $IterableTypeExpressionParserRuleCall_1.stop, grammarAccess.getTypeRefFunctionTypeExpressionAccess().getIterableTypeExpressionParserRuleCall_1()); }
	    |
	TypeTypeRefParserRuleCall_2=ruleTypeTypeRef{ announce($TypeTypeRefParserRuleCall_2.start, $TypeTypeRefParserRuleCall_2.stop, grammarAccess.getTypeRefFunctionTypeExpressionAccess().getTypeTypeRefParserRuleCall_2()); }
	    |
	UnionTypeExpressionOLDParserRuleCall_3=ruleUnionTypeExpressionOLD{ announce($UnionTypeExpressionOLDParserRuleCall_3.start, $UnionTypeExpressionOLDParserRuleCall_3.stop, grammarAccess.getTypeRefFunctionTypeExpressionAccess().getUnionTypeExpressionOLDParserRuleCall_3()); }
	    |
	IntersectionTypeExpressionOLDParserRuleCall_4=ruleIntersectionTypeExpressionOLD{ announce($IntersectionTypeExpressionOLDParserRuleCall_4.start, $IntersectionTypeExpressionOLDParserRuleCall_4.stop, grammarAccess.getTypeRefFunctionTypeExpressionAccess().getIntersectionTypeExpressionOLDParserRuleCall_4()); }
)
;

// Entry rule entryRuleTypeArgInTypeTypeRef
entryRuleTypeArgInTypeTypeRef
	:
	ruleTypeArgInTypeTypeRef
	EOF;

// Rule TypeArgInTypeTypeRef
ruleTypeArgInTypeTypeRef
@init {
}:
(
	ParameterizedTypeRefNominalParserRuleCall_0=ruleParameterizedTypeRefNominal{ announce($ParameterizedTypeRefNominalParserRuleCall_0.start, $ParameterizedTypeRefNominalParserRuleCall_0.stop, grammarAccess.getTypeArgInTypeTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0()); }
	    |
	ThisTypeRefNominalParserRuleCall_1=ruleThisTypeRefNominal{ announce($ThisTypeRefNominalParserRuleCall_1.start, $ThisTypeRefNominalParserRuleCall_1.stop, grammarAccess.getTypeArgInTypeTypeRefAccess().getThisTypeRefNominalParserRuleCall_1()); }
	    |
	(
		((
			QuestionMark
		)
		)=>
		WildcardOldNotationParserRuleCall_2=ruleWildcardOldNotation{ announce($WildcardOldNotationParserRuleCall_2.start, $WildcardOldNotationParserRuleCall_2.stop, grammarAccess.getTypeArgInTypeTypeRefAccess().getWildcardOldNotationParserRuleCall_2()); }
	)
)
;

// Entry rule entryRuleThisTypeRef
entryRuleThisTypeRef
	:
	ruleThisTypeRef
	EOF;

// Rule ThisTypeRef
ruleThisTypeRef
@init {
}:
(
	ThisTypeRefNominalParserRuleCall_0=ruleThisTypeRefNominal{ announce($ThisTypeRefNominalParserRuleCall_0.start, $ThisTypeRefNominalParserRuleCall_0.stop, grammarAccess.getThisTypeRefAccess().getThisTypeRefNominalParserRuleCall_0()); }
	    |
	ThisTypeRefStructuralParserRuleCall_1=ruleThisTypeRefStructural{ announce($ThisTypeRefStructuralParserRuleCall_1.start, $ThisTypeRefStructuralParserRuleCall_1.stop, grammarAccess.getThisTypeRefAccess().getThisTypeRefStructuralParserRuleCall_1()); }
)
;

// Entry rule entryRuleThisTypeRefNominal
entryRuleThisTypeRefNominal
	:
	ruleThisTypeRefNominal
	EOF;

// Rule ThisTypeRefNominal
ruleThisTypeRefNominal
@init {
}:
(
	ThisKeyword_1=This_1
	 {
		announce($ThisKeyword_1, grammarAccess.getThisTypeRefNominalAccess().getThisKeyword_1());
	}
)
;

// Entry rule entryRuleThisTypeRefStructural
entryRuleThisTypeRefStructural
	:
	ruleThisTypeRefStructural
	EOF;

// Rule ThisTypeRefStructural
ruleThisTypeRefStructural
@init {
}:
(
	(
		(
			DefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0=ruleTypingStrategyUseSiteOperator{
				announce($DefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0.start, $DefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0.stop, grammarAccess.getThisTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0());
			}
		)
	)
	ThisKeyword_1=This_1
	 {
		announce($ThisKeyword_1, grammarAccess.getThisTypeRefStructuralAccess().getThisKeyword_1());
	}
	(
		WithKeyword_2_0=With
		 {
			announce($WithKeyword_2_0, grammarAccess.getThisTypeRefStructuralAccess().getWithKeyword_2_0());
		}
		TStructMemberListParserRuleCall_2_1=ruleTStructMemberList{ announce($TStructMemberListParserRuleCall_2_1.start, $TStructMemberListParserRuleCall_2_1.stop, grammarAccess.getThisTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1()); }
	)?
)
;

// Entry rule entryRuleFunctionTypeExpressionOLD
entryRuleFunctionTypeExpressionOLD
	:
	ruleFunctionTypeExpressionOLD
	EOF;

// Rule FunctionTypeExpressionOLD
ruleFunctionTypeExpressionOLD
@init {
}:
(
	LeftCurlyBracketKeyword_1=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_1, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_1());
	}
	(
		CommercialAtKeyword_2_0=CommercialAt
		 {
			announce($CommercialAtKeyword_2_0, grammarAccess.getFunctionTypeExpressionOLDAccess().getCommercialAtKeyword_2_0());
		}
		ThisKeyword_2_1=This
		 {
			announce($ThisKeyword_2_1, grammarAccess.getFunctionTypeExpressionOLDAccess().getThisKeyword_2_1());
		}
		LeftParenthesisKeyword_2_2=LeftParenthesis
		 {
			announce($LeftParenthesisKeyword_2_2, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_2_2());
		}
		(
			(
				DeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0=ruleTypeRefFunctionTypeExpression{
					announce($DeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0.start, $DeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0.stop, grammarAccess.getFunctionTypeExpressionOLDAccess().getDeclaredThisTypeAssignment_2_3());
				}
			)
		)
		RightParenthesisKeyword_2_4=RightParenthesis
		 {
			announce($RightParenthesisKeyword_2_4, grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_2_4());
		}
	)?
	FunctionKeyword_3=Function
	 {
		announce($FunctionKeyword_3, grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionKeyword_3());
	}
	(
		LessThanSignKeyword_4_0=LessThanSign
		 {
			announce($LessThanSignKeyword_4_0, grammarAccess.getFunctionTypeExpressionOLDAccess().getLessThanSignKeyword_4_0());
		}
		(
			(
				OwnedTypeVarsTypeVariableParserRuleCall_4_1_0=ruleTypeVariable{
					announce($OwnedTypeVarsTypeVariableParserRuleCall_4_1_0.start, $OwnedTypeVarsTypeVariableParserRuleCall_4_1_0.stop, grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsAssignment_4_1());
				}
			)
		)
		(
			CommaKeyword_4_2_0=Comma
			 {
				announce($CommaKeyword_4_2_0, grammarAccess.getFunctionTypeExpressionOLDAccess().getCommaKeyword_4_2_0());
			}
			(
				(
					OwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0=ruleTypeVariable{
						announce($OwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0.start, $OwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0.stop, grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsAssignment_4_2_1());
					}
				)
			)
		)*
		GreaterThanSignKeyword_4_3=GreaterThanSign
		 {
			announce($GreaterThanSignKeyword_4_3, grammarAccess.getFunctionTypeExpressionOLDAccess().getGreaterThanSignKeyword_4_3());
		}
	)?
	LeftParenthesisKeyword_5=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_5, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_5());
	}
	TAnonymousFormalParameterListParserRuleCall_6=ruleTAnonymousFormalParameterList{ announce($TAnonymousFormalParameterListParserRuleCall_6.start, $TAnonymousFormalParameterListParserRuleCall_6.stop, grammarAccess.getFunctionTypeExpressionOLDAccess().getTAnonymousFormalParameterListParserRuleCall_6()); }
	RightParenthesisKeyword_7=RightParenthesis
	 {
		announce($RightParenthesisKeyword_7, grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_7());
	}
	(
		ColonSepReturnTypeRefParserRuleCall_8=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_8.start, $ColonSepReturnTypeRefParserRuleCall_8.stop, grammarAccess.getFunctionTypeExpressionOLDAccess().getColonSepReturnTypeRefParserRuleCall_8()); }
	)?
	RightCurlyBracketKeyword_9=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_9, grammarAccess.getFunctionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_9());
	}
)
;

// Entry rule entryRuleArrowFunctionTypeExpression
entryRuleArrowFunctionTypeExpression
	:
	ruleArrowFunctionTypeExpression
	EOF;

// Rule ArrowFunctionTypeExpression
ruleArrowFunctionTypeExpression
@init {
}:
(
	(
		((
			LeftParenthesis
			ruleTAnonymousFormalParameterList
			RightParenthesis
			EqualsSignGreaterThanSign
		)
		)=>
		(
			LeftParenthesisKeyword_0_0_1=LeftParenthesis
			 {
				announce($LeftParenthesisKeyword_0_0_1, grammarAccess.getArrowFunctionTypeExpressionAccess().getLeftParenthesisKeyword_0_0_1());
			}
			TAnonymousFormalParameterListParserRuleCall_0_0_2=ruleTAnonymousFormalParameterList{ announce($TAnonymousFormalParameterListParserRuleCall_0_0_2.start, $TAnonymousFormalParameterListParserRuleCall_0_0_2.stop, grammarAccess.getArrowFunctionTypeExpressionAccess().getTAnonymousFormalParameterListParserRuleCall_0_0_2()); }
			RightParenthesisKeyword_0_0_3=RightParenthesis
			 {
				announce($RightParenthesisKeyword_0_0_3, grammarAccess.getArrowFunctionTypeExpressionAccess().getRightParenthesisKeyword_0_0_3());
			}
			EqualsSignGreaterThanSignKeyword_0_0_4=EqualsSignGreaterThanSign
			 {
				announce($EqualsSignGreaterThanSignKeyword_0_0_4, grammarAccess.getArrowFunctionTypeExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_4());
			}
		)
	)
	(
		(
			ReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0=rulePrimaryTypeExpression{
				announce($ReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0.start, $ReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0.stop, grammarAccess.getArrowFunctionTypeExpressionAccess().getReturnTypeRefAssignment_1());
			}
		)
	)
)
;


// Rule TAnonymousFormalParameterList
ruleTAnonymousFormalParameterList
@init {
}:
(
	(
		(
			FparsTAnonymousFormalParameterParserRuleCall_0_0=ruleTAnonymousFormalParameter{
				announce($FparsTAnonymousFormalParameterParserRuleCall_0_0.start, $FparsTAnonymousFormalParameterParserRuleCall_0_0.stop, grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_0());
			}
		)
	)
	(
		CommaKeyword_1_0=Comma
		 {
			announce($CommaKeyword_1_0, grammarAccess.getTAnonymousFormalParameterListAccess().getCommaKeyword_1_0());
		}
		(
			(
				FparsTAnonymousFormalParameterParserRuleCall_1_1_0=ruleTAnonymousFormalParameter{
					announce($FparsTAnonymousFormalParameterParserRuleCall_1_1_0.start, $FparsTAnonymousFormalParameterParserRuleCall_1_1_0.stop, grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_1_1());
				}
			)
		)
	)*
)?
;

// Entry rule entryRuleTAnonymousFormalParameter
entryRuleTAnonymousFormalParameter
	:
	ruleTAnonymousFormalParameter
	EOF;

// Rule TAnonymousFormalParameter
ruleTAnonymousFormalParameter
@init {
}:
(
	(
		(
			VariadicFullStopFullStopFullStopKeyword_0_0=FullStopFullStopFullStop
			 {
				announce($VariadicFullStopFullStopFullStopKeyword_0_0, grammarAccess.getTAnonymousFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0());
			}
		)
	)?
	(
		(
			((
				(
					(
						ruleBindingIdentifier
					)
				)
				Colon
			)
			)=>
			(
				(
					(
						NameBindingIdentifierParserRuleCall_1_0_0_0_0=ruleBindingIdentifier{
							announce($NameBindingIdentifierParserRuleCall_1_0_0_0_0.start, $NameBindingIdentifierParserRuleCall_1_0_0_0_0.stop, grammarAccess.getTAnonymousFormalParameterAccess().getNameAssignment_1_0_0_0());
						}
					)
				)
				(
					(Colon
					)=>
					ColonSepTypeRefParserRuleCall_1_0_0_1=ruleColonSepTypeRef{ announce($ColonSepTypeRefParserRuleCall_1_0_0_1.start, $ColonSepTypeRefParserRuleCall_1_0_0_1.stop, grammarAccess.getTAnonymousFormalParameterAccess().getColonSepTypeRefParserRuleCall_1_0_0_1()); }
				)
			)
		)
		    |
		(
			(
				TypeRefTypeRefParserRuleCall_1_1_0=ruleTypeRef{
					announce($TypeRefTypeRefParserRuleCall_1_1_0.start, $TypeRefTypeRefParserRuleCall_1_1_0.stop, grammarAccess.getTAnonymousFormalParameterAccess().getTypeRefAssignment_1_1());
				}
			)
		)
	)
	DefaultFormalParameterParserRuleCall_2=ruleDefaultFormalParameter{ announce($DefaultFormalParameterParserRuleCall_2.start, $DefaultFormalParameterParserRuleCall_2.stop, grammarAccess.getTAnonymousFormalParameterAccess().getDefaultFormalParameterParserRuleCall_2()); }
)
;


// Rule DefaultFormalParameter
ruleDefaultFormalParameter
@init {
}:
(
	(
		(
			HasInitializerAssignmentEqualsSignKeyword_0_0=EqualsSign
			 {
				announce($HasInitializerAssignmentEqualsSignKeyword_0_0, grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentEqualsSignKeyword_0_0());
			}
		)
	)
	(
		(
			AstInitializerTypeReferenceNameParserRuleCall_1_0=ruleTypeReferenceName{
				announce($AstInitializerTypeReferenceNameParserRuleCall_1_0.start, $AstInitializerTypeReferenceNameParserRuleCall_1_0.stop, grammarAccess.getDefaultFormalParameterAccess().getAstInitializerAssignment_1());
			}
		)
	)?
)?
;

// Entry rule entryRuleUnionTypeExpressionOLD
entryRuleUnionTypeExpressionOLD
	:
	ruleUnionTypeExpressionOLD
	EOF;

// Rule UnionTypeExpressionOLD
ruleUnionTypeExpressionOLD
@init {
}:
(
	UnionKeyword_1=Union
	 {
		announce($UnionKeyword_1, grammarAccess.getUnionTypeExpressionOLDAccess().getUnionKeyword_1());
	}
	LeftCurlyBracketKeyword_2=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_2, grammarAccess.getUnionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2());
	}
	(
		(
			TypeRefsTypeRefParserRuleCall_3_0=ruleTypeRef{
				announce($TypeRefsTypeRefParserRuleCall_3_0.start, $TypeRefsTypeRefParserRuleCall_3_0.stop, grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsAssignment_3());
			}
		)
	)
	(
		CommaKeyword_4_0=Comma
		 {
			announce($CommaKeyword_4_0, grammarAccess.getUnionTypeExpressionOLDAccess().getCommaKeyword_4_0());
		}
		(
			(
				TypeRefsTypeRefParserRuleCall_4_1_0=ruleTypeRef{
					announce($TypeRefsTypeRefParserRuleCall_4_1_0.start, $TypeRefsTypeRefParserRuleCall_4_1_0.stop, grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsAssignment_4_1());
				}
			)
		)
	)*
	RightCurlyBracketKeyword_5=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_5, grammarAccess.getUnionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5());
	}
)
;

// Entry rule entryRuleIntersectionTypeExpressionOLD
entryRuleIntersectionTypeExpressionOLD
	:
	ruleIntersectionTypeExpressionOLD
	EOF;

// Rule IntersectionTypeExpressionOLD
ruleIntersectionTypeExpressionOLD
@init {
}:
(
	IntersectionKeyword_1=Intersection
	 {
		announce($IntersectionKeyword_1, grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionKeyword_1());
	}
	LeftCurlyBracketKeyword_2=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_2, grammarAccess.getIntersectionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2());
	}
	(
		(
			TypeRefsTypeRefParserRuleCall_3_0=ruleTypeRef{
				announce($TypeRefsTypeRefParserRuleCall_3_0.start, $TypeRefsTypeRefParserRuleCall_3_0.stop, grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsAssignment_3());
			}
		)
	)
	(
		CommaKeyword_4_0=Comma
		 {
			announce($CommaKeyword_4_0, grammarAccess.getIntersectionTypeExpressionOLDAccess().getCommaKeyword_4_0());
		}
		(
			(
				TypeRefsTypeRefParserRuleCall_4_1_0=ruleTypeRef{
					announce($TypeRefsTypeRefParserRuleCall_4_1_0.start, $TypeRefsTypeRefParserRuleCall_4_1_0.stop, grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsAssignment_4_1());
				}
			)
		)
	)*
	RightCurlyBracketKeyword_5=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_5, grammarAccess.getIntersectionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5());
	}
)
;

// Entry rule entryRuleParameterizedTypeRef
entryRuleParameterizedTypeRef
	:
	ruleParameterizedTypeRef
	EOF;

// Rule ParameterizedTypeRef
ruleParameterizedTypeRef
@init {
}:
(
	ParameterizedTypeRefNominalParserRuleCall_0=ruleParameterizedTypeRefNominal{ announce($ParameterizedTypeRefNominalParserRuleCall_0.start, $ParameterizedTypeRefNominalParserRuleCall_0.stop, grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0()); }
	    |
	ParameterizedTypeRefStructuralParserRuleCall_1=ruleParameterizedTypeRefStructural{ announce($ParameterizedTypeRefStructuralParserRuleCall_1.start, $ParameterizedTypeRefStructuralParserRuleCall_1.stop, grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefStructuralParserRuleCall_1()); }
)
;

// Entry rule entryRuleParameterizedTypeRefNominal
entryRuleParameterizedTypeRefNominal
	:
	ruleParameterizedTypeRefNominal
	EOF;

// Rule ParameterizedTypeRefNominal
ruleParameterizedTypeRefNominal
@init {
}:
(
	(
		TypeReferenceParserRuleCall_0_0=ruleTypeReference{ announce($TypeReferenceParserRuleCall_0_0.start, $TypeReferenceParserRuleCall_0_0.stop, grammarAccess.getParameterizedTypeRefNominalAccess().getTypeReferenceParserRuleCall_0_0()); }
		    |
		(
			TypeReferenceParserRuleCall_0_1_1=ruleTypeReference{ announce($TypeReferenceParserRuleCall_0_1_1.start, $TypeReferenceParserRuleCall_0_1_1.stop, grammarAccess.getParameterizedTypeRefNominalAccess().getTypeReferenceParserRuleCall_0_1_1()); }
			VersionRequestParserRuleCall_0_1_2=ruleVersionRequest{ announce($VersionRequestParserRuleCall_0_1_2.start, $VersionRequestParserRuleCall_0_1_2.stop, grammarAccess.getParameterizedTypeRefNominalAccess().getVersionRequestParserRuleCall_0_1_2()); }
		)
	)
	(
		(LessThanSign
		)=>
		TypeArgumentsParserRuleCall_1=ruleTypeArguments{ announce($TypeArgumentsParserRuleCall_1.start, $TypeArgumentsParserRuleCall_1.stop, grammarAccess.getParameterizedTypeRefNominalAccess().getTypeArgumentsParserRuleCall_1()); }
	)?
)
;

// Entry rule entryRuleParameterizedTypeRefStructural
entryRuleParameterizedTypeRefStructural
	:
	ruleParameterizedTypeRefStructural
	EOF;

// Rule ParameterizedTypeRefStructural
ruleParameterizedTypeRefStructural
@init {
}:
(
	(
		(
			(
				(
					DefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0_0_0=ruleTypingStrategyUseSiteOperator{
						announce($DefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0_0_0.start, $DefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0_0_0.stop, grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0_0_0());
					}
				)
			)
			TypeReferenceParserRuleCall_0_0_1=ruleTypeReference{ announce($TypeReferenceParserRuleCall_0_0_1.start, $TypeReferenceParserRuleCall_0_0_1.stop, grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeReferenceParserRuleCall_0_0_1()); }
		)
		    |
		(
			(
				(
					DefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_1_1_0=ruleTypingStrategyUseSiteOperator{
						announce($DefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_1_1_0.start, $DefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_1_1_0.stop, grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0_1_1());
					}
				)
			)
			TypeReferenceParserRuleCall_0_1_2=ruleTypeReference{ announce($TypeReferenceParserRuleCall_0_1_2.start, $TypeReferenceParserRuleCall_0_1_2.stop, grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeReferenceParserRuleCall_0_1_2()); }
			VersionRequestParserRuleCall_0_1_3=ruleVersionRequest{ announce($VersionRequestParserRuleCall_0_1_3.start, $VersionRequestParserRuleCall_0_1_3.stop, grammarAccess.getParameterizedTypeRefStructuralAccess().getVersionRequestParserRuleCall_0_1_3()); }
		)
	)
	(
		(LessThanSign
		)=>
		TypeArgumentsParserRuleCall_1=ruleTypeArguments{ announce($TypeArgumentsParserRuleCall_1.start, $TypeArgumentsParserRuleCall_1.stop, grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeArgumentsParserRuleCall_1()); }
	)?
	(
		WithKeyword_2_0=With
		 {
			announce($WithKeyword_2_0, grammarAccess.getParameterizedTypeRefStructuralAccess().getWithKeyword_2_0());
		}
		TStructMemberListParserRuleCall_2_1=ruleTStructMemberList{ announce($TStructMemberListParserRuleCall_2_1.start, $TStructMemberListParserRuleCall_2_1.stop, grammarAccess.getParameterizedTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1()); }
	)?
)
;

// Entry rule entryRuleIterableTypeExpression
entryRuleIterableTypeExpression
	:
	ruleIterableTypeExpression
	EOF;

// Rule IterableTypeExpression
ruleIterableTypeExpression
@init {
}:
(
	(
		(
			IterableTypeExpressionLeftSquareBracketKeyword_0_0=LeftSquareBracket
			 {
				announce($IterableTypeExpressionLeftSquareBracketKeyword_0_0, grammarAccess.getIterableTypeExpressionAccess().getIterableTypeExpressionLeftSquareBracketKeyword_0_0());
			}
		)
	)
	(
		(
			(
				TypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0=ruleEmptyIterableTypeExpressionTail{
					announce($TypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0.start, $TypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0.stop, grammarAccess.getIterableTypeExpressionAccess().getTypeArgsAssignment_1_0());
				}
			)
		)
		    |
		(
			(
				(
					TypeArgsTypeArgumentParserRuleCall_1_1_0_0=ruleTypeArgument{
						announce($TypeArgsTypeArgumentParserRuleCall_1_1_0_0.start, $TypeArgsTypeArgumentParserRuleCall_1_1_0_0.stop, grammarAccess.getIterableTypeExpressionAccess().getTypeArgsAssignment_1_1_0());
					}
				)
			)
			(
				CommaKeyword_1_1_1_0=Comma
				 {
					announce($CommaKeyword_1_1_1_0, grammarAccess.getIterableTypeExpressionAccess().getCommaKeyword_1_1_1_0());
				}
				(
					(
						TypeArgsTypeArgumentParserRuleCall_1_1_1_1_0=ruleTypeArgument{
							announce($TypeArgsTypeArgumentParserRuleCall_1_1_1_1_0.start, $TypeArgsTypeArgumentParserRuleCall_1_1_1_1_0.stop, grammarAccess.getIterableTypeExpressionAccess().getTypeArgsAssignment_1_1_1_1());
						}
					)
				)
			)*
			RightSquareBracketKeyword_1_1_2=RightSquareBracket
			 {
				announce($RightSquareBracketKeyword_1_1_2, grammarAccess.getIterableTypeExpressionAccess().getRightSquareBracketKeyword_1_1_2());
			}
		)
	)
)
;

// Entry rule entryRuleEmptyIterableTypeExpressionTail
entryRuleEmptyIterableTypeExpressionTail
	:
	ruleEmptyIterableTypeExpressionTail
	EOF;

// Rule EmptyIterableTypeExpressionTail
ruleEmptyIterableTypeExpressionTail
@init {
}:
(
	RightSquareBracketKeyword_1=RightSquareBracket
	 {
		announce($RightSquareBracketKeyword_1, grammarAccess.getEmptyIterableTypeExpressionTailAccess().getRightSquareBracketKeyword_1());
	}
)
;


// Rule VersionRequest
ruleVersionRequest
@init {
}:
(
	(
		RequestedVersionVERSIONTerminalRuleCall_0=RULE_VERSION{
			announce($RequestedVersionVERSIONTerminalRuleCall_0, grammarAccess.getVersionRequestAccess().getRequestedVersionAssignment());
		}
	)
)
;


// Rule TypeArguments
ruleTypeArguments
@init {
}:
(
	LessThanSignKeyword_0=LessThanSign
	 {
		announce($LessThanSignKeyword_0, grammarAccess.getTypeArgumentsAccess().getLessThanSignKeyword_0());
	}
	(
		(
			TypeArgsTypeArgumentParserRuleCall_1_0=ruleTypeArgument{
				announce($TypeArgsTypeArgumentParserRuleCall_1_0.start, $TypeArgsTypeArgumentParserRuleCall_1_0.stop, grammarAccess.getTypeArgumentsAccess().getTypeArgsAssignment_1());
			}
		)
	)
	(
		CommaKeyword_2_0=Comma
		 {
			announce($CommaKeyword_2_0, grammarAccess.getTypeArgumentsAccess().getCommaKeyword_2_0());
		}
		(
			(
				TypeArgsTypeArgumentParserRuleCall_2_1_0=ruleTypeArgument{
					announce($TypeArgsTypeArgumentParserRuleCall_2_1_0.start, $TypeArgsTypeArgumentParserRuleCall_2_1_0.stop, grammarAccess.getTypeArgumentsAccess().getTypeArgsAssignment_2_1());
				}
			)
		)
	)*
	GreaterThanSignKeyword_3=GreaterThanSign
	 {
		announce($GreaterThanSignKeyword_3, grammarAccess.getTypeArgumentsAccess().getGreaterThanSignKeyword_3());
	}
)
;


// Rule TStructMemberList
ruleTStructMemberList
@init {
}:
(
	LeftCurlyBracketKeyword_0=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_0, grammarAccess.getTStructMemberListAccess().getLeftCurlyBracketKeyword_0());
	}
	(
		(
			(
				AstStructuralMembersTStructMemberParserRuleCall_1_0_0=ruleTStructMember{
					announce($AstStructuralMembersTStructMemberParserRuleCall_1_0_0.start, $AstStructuralMembersTStructMemberParserRuleCall_1_0_0.stop, grammarAccess.getTStructMemberListAccess().getAstStructuralMembersAssignment_1_0());
				}
			)
		)
		(
			SemicolonKeyword_1_1_0=Semicolon
			 {
				announce($SemicolonKeyword_1_1_0, grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0());
			}
			    |
			CommaKeyword_1_1_1=Comma
			 {
				announce($CommaKeyword_1_1_1, grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1());
			}
		)?
	)*
	RightCurlyBracketKeyword_2=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_2, grammarAccess.getTStructMemberListAccess().getRightCurlyBracketKeyword_2());
	}
)
;

// Entry rule entryRuleTStructMember
entryRuleTStructMember
	:
	ruleTStructMember
	EOF;

// Rule TStructMember
ruleTStructMember
@init {
}:
(
	(
		((
			Get
			(
				(
					ruleIdentifierName
				)
			)
		)
		)=>
		TStructGetterParserRuleCall_0=ruleTStructGetter{ announce($TStructGetterParserRuleCall_0.start, $TStructGetterParserRuleCall_0.stop, grammarAccess.getTStructMemberAccess().getTStructGetterParserRuleCall_0()); }
	)
	    |
	(
		((
			Set
			(
				(
					ruleIdentifierName
				)
			)
		)
		)=>
		TStructSetterParserRuleCall_1=ruleTStructSetter{ announce($TStructSetterParserRuleCall_1.start, $TStructSetterParserRuleCall_1.stop, grammarAccess.getTStructMemberAccess().getTStructSetterParserRuleCall_1()); }
	)
	    |
	(
		((
			(
				ruleTypeVariables
			)?
			(
				(
					ruleIdentifierName
				)
			)
			LeftParenthesis
		)
		)=>
		TStructMethodParserRuleCall_2=ruleTStructMethod{ announce($TStructMethodParserRuleCall_2.start, $TStructMethodParserRuleCall_2.stop, grammarAccess.getTStructMemberAccess().getTStructMethodParserRuleCall_2()); }
	)
	    |
	TStructFieldParserRuleCall_3=ruleTStructField{ announce($TStructFieldParserRuleCall_3.start, $TStructFieldParserRuleCall_3.stop, grammarAccess.getTStructMemberAccess().getTStructFieldParserRuleCall_3()); }
)
;

// Entry rule entryRuleTStructMethod
entryRuleTStructMethod
	:
	ruleTStructMethod
	EOF;

// Rule TStructMethod
ruleTStructMethod
@init {
}:
(
	(
		((
			(
				ruleTypeVariables
			)?
			(
				(
					ruleIdentifierName
				)
			)
			LeftParenthesis
		)
		)=>
		(
			(
				TypeVariablesParserRuleCall_0_0_1=ruleTypeVariables{ announce($TypeVariablesParserRuleCall_0_0_1.start, $TypeVariablesParserRuleCall_0_0_1.stop, grammarAccess.getTStructMethodAccess().getTypeVariablesParserRuleCall_0_0_1()); }
			)?
			(
				(
					NameIdentifierNameParserRuleCall_0_0_2_0=ruleIdentifierName{
						announce($NameIdentifierNameParserRuleCall_0_0_2_0.start, $NameIdentifierNameParserRuleCall_0_0_2_0.stop, grammarAccess.getTStructMethodAccess().getNameAssignment_0_0_2());
					}
				)
			)
			LeftParenthesisKeyword_0_0_3=LeftParenthesis
			 {
				announce($LeftParenthesisKeyword_0_0_3, grammarAccess.getTStructMethodAccess().getLeftParenthesisKeyword_0_0_3());
			}
		)
	)
	TAnonymousFormalParameterListParserRuleCall_1=ruleTAnonymousFormalParameterList{ announce($TAnonymousFormalParameterListParserRuleCall_1.start, $TAnonymousFormalParameterListParserRuleCall_1.stop, grammarAccess.getTStructMethodAccess().getTAnonymousFormalParameterListParserRuleCall_1()); }
	RightParenthesisKeyword_2=RightParenthesis
	 {
		announce($RightParenthesisKeyword_2, grammarAccess.getTStructMethodAccess().getRightParenthesisKeyword_2());
	}
	(
		ColonSepReturnTypeRefParserRuleCall_3=ruleColonSepReturnTypeRef{ announce($ColonSepReturnTypeRefParserRuleCall_3.start, $ColonSepReturnTypeRefParserRuleCall_3.stop, grammarAccess.getTStructMethodAccess().getColonSepReturnTypeRefParserRuleCall_3()); }
	)?
)
;


// Rule TypeVariables
ruleTypeVariables
@init {
}:
(
	LessThanSignKeyword_0=LessThanSign
	 {
		announce($LessThanSignKeyword_0, grammarAccess.getTypeVariablesAccess().getLessThanSignKeyword_0());
	}
	(
		(
			TypeVarsTypeVariableParserRuleCall_1_0=ruleTypeVariable{
				announce($TypeVarsTypeVariableParserRuleCall_1_0.start, $TypeVarsTypeVariableParserRuleCall_1_0.stop, grammarAccess.getTypeVariablesAccess().getTypeVarsAssignment_1());
			}
		)
	)
	(
		CommaKeyword_2_0=Comma
		 {
			announce($CommaKeyword_2_0, grammarAccess.getTypeVariablesAccess().getCommaKeyword_2_0());
		}
		(
			(
				TypeVarsTypeVariableParserRuleCall_2_1_0=ruleTypeVariable{
					announce($TypeVarsTypeVariableParserRuleCall_2_1_0.start, $TypeVarsTypeVariableParserRuleCall_2_1_0.stop, grammarAccess.getTypeVariablesAccess().getTypeVarsAssignment_2_1());
				}
			)
		)
	)*
	GreaterThanSignKeyword_3=GreaterThanSign
	 {
		announce($GreaterThanSignKeyword_3, grammarAccess.getTypeVariablesAccess().getGreaterThanSignKeyword_3());
	}
)
;


// Rule ColonSepDeclaredTypeRef
ruleColonSepDeclaredTypeRef
@init {
}:
(
	ColonKeyword_0=Colon
	 {
		announce($ColonKeyword_0, grammarAccess.getColonSepDeclaredTypeRefAccess().getColonKeyword_0());
	}
	(
		(
			DeclaredTypeRefTypeRefParserRuleCall_1_0=ruleTypeRef{
				announce($DeclaredTypeRefTypeRefParserRuleCall_1_0.start, $DeclaredTypeRefTypeRefParserRuleCall_1_0.stop, grammarAccess.getColonSepDeclaredTypeRefAccess().getDeclaredTypeRefAssignment_1());
			}
		)
	)
)
;


// Rule ColonSepTypeRef
ruleColonSepTypeRef
@init {
}:
(
	ColonKeyword_0=Colon
	 {
		announce($ColonKeyword_0, grammarAccess.getColonSepTypeRefAccess().getColonKeyword_0());
	}
	(
		(
			TypeRefTypeRefParserRuleCall_1_0=ruleTypeRef{
				announce($TypeRefTypeRefParserRuleCall_1_0.start, $TypeRefTypeRefParserRuleCall_1_0.stop, grammarAccess.getColonSepTypeRefAccess().getTypeRefAssignment_1());
			}
		)
	)
)
;


// Rule ColonSepReturnTypeRef
ruleColonSepReturnTypeRef
@init {
}:
(
	ColonKeyword_0=Colon
	 {
		announce($ColonKeyword_0, grammarAccess.getColonSepReturnTypeRefAccess().getColonKeyword_0());
	}
	(
		(
			ReturnTypeRefTypeRefParserRuleCall_1_0=ruleTypeRef{
				announce($ReturnTypeRefTypeRefParserRuleCall_1_0.start, $ReturnTypeRefTypeRefParserRuleCall_1_0.stop, grammarAccess.getColonSepReturnTypeRefAccess().getReturnTypeRefAssignment_1());
			}
		)
	)
)
;

// Entry rule entryRuleTStructField
entryRuleTStructField
	:
	ruleTStructField
	EOF;

// Rule TStructField
ruleTStructField
@init {
}:
(
	(
		(
			NameIdentifierNameParserRuleCall_0_0=ruleIdentifierName{
				announce($NameIdentifierNameParserRuleCall_0_0.start, $NameIdentifierNameParserRuleCall_0_0.stop, grammarAccess.getTStructFieldAccess().getNameAssignment_0());
			}
		)
	)
	(
		(
			OptionalQuestionMarkKeyword_1_0=QuestionMark
			 {
				announce($OptionalQuestionMarkKeyword_1_0, grammarAccess.getTStructFieldAccess().getOptionalQuestionMarkKeyword_1_0());
			}
		)
	)?
	(
		ColonSepTypeRefParserRuleCall_2=ruleColonSepTypeRef{ announce($ColonSepTypeRefParserRuleCall_2.start, $ColonSepTypeRefParserRuleCall_2.stop, grammarAccess.getTStructFieldAccess().getColonSepTypeRefParserRuleCall_2()); }
	)?
)
;

// Entry rule entryRuleTStructGetter
entryRuleTStructGetter
	:
	ruleTStructGetter
	EOF;

// Rule TStructGetter
ruleTStructGetter
@init {
}:
(
	(
		((
			Get
			(
				(
					ruleIdentifierName
				)
			)
		)
		)=>
		(
			GetKeyword_0_0_1=Get
			 {
				announce($GetKeyword_0_0_1, grammarAccess.getTStructGetterAccess().getGetKeyword_0_0_1());
			}
			(
				(
					NameIdentifierNameParserRuleCall_0_0_2_0=ruleIdentifierName{
						announce($NameIdentifierNameParserRuleCall_0_0_2_0.start, $NameIdentifierNameParserRuleCall_0_0_2_0.stop, grammarAccess.getTStructGetterAccess().getNameAssignment_0_0_2());
					}
				)
			)
		)
	)
	(
		(
			OptionalQuestionMarkKeyword_1_0=QuestionMark
			 {
				announce($OptionalQuestionMarkKeyword_1_0, grammarAccess.getTStructGetterAccess().getOptionalQuestionMarkKeyword_1_0());
			}
		)
	)?
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getTStructGetterAccess().getLeftParenthesisKeyword_2());
	}
	RightParenthesisKeyword_3=RightParenthesis
	 {
		announce($RightParenthesisKeyword_3, grammarAccess.getTStructGetterAccess().getRightParenthesisKeyword_3());
	}
	(
		ColonSepDeclaredTypeRefParserRuleCall_4=ruleColonSepDeclaredTypeRef{ announce($ColonSepDeclaredTypeRefParserRuleCall_4.start, $ColonSepDeclaredTypeRefParserRuleCall_4.stop, grammarAccess.getTStructGetterAccess().getColonSepDeclaredTypeRefParserRuleCall_4()); }
	)?
)
;

// Entry rule entryRuleTStructSetter
entryRuleTStructSetter
	:
	ruleTStructSetter
	EOF;

// Rule TStructSetter
ruleTStructSetter
@init {
}:
(
	(
		((
			Set
			(
				(
					ruleIdentifierName
				)
			)
		)
		)=>
		(
			SetKeyword_0_0_1=Set
			 {
				announce($SetKeyword_0_0_1, grammarAccess.getTStructSetterAccess().getSetKeyword_0_0_1());
			}
			(
				(
					NameIdentifierNameParserRuleCall_0_0_2_0=ruleIdentifierName{
						announce($NameIdentifierNameParserRuleCall_0_0_2_0.start, $NameIdentifierNameParserRuleCall_0_0_2_0.stop, grammarAccess.getTStructSetterAccess().getNameAssignment_0_0_2());
					}
				)
			)
		)
	)
	(
		(
			OptionalQuestionMarkKeyword_1_0=QuestionMark
			 {
				announce($OptionalQuestionMarkKeyword_1_0, grammarAccess.getTStructSetterAccess().getOptionalQuestionMarkKeyword_1_0());
			}
		)
	)?
	LeftParenthesisKeyword_2=LeftParenthesis
	 {
		announce($LeftParenthesisKeyword_2, grammarAccess.getTStructSetterAccess().getLeftParenthesisKeyword_2());
	}
	(
		(
			FparTAnonymousFormalParameterParserRuleCall_3_0=ruleTAnonymousFormalParameter{
				announce($FparTAnonymousFormalParameterParserRuleCall_3_0.start, $FparTAnonymousFormalParameterParserRuleCall_3_0.stop, grammarAccess.getTStructSetterAccess().getFparAssignment_3());
			}
		)
	)
	RightParenthesisKeyword_4=RightParenthesis
	 {
		announce($RightParenthesisKeyword_4, grammarAccess.getTStructSetterAccess().getRightParenthesisKeyword_4());
	}
)
;

// Entry rule entryRuleTypingStrategyUseSiteOperator
entryRuleTypingStrategyUseSiteOperator
	:
	ruleTypingStrategyUseSiteOperator
	EOF;

// Rule TypingStrategyUseSiteOperator
ruleTypingStrategyUseSiteOperator
@init {
}
:
(
	TildeKeyword_0=Tilde {
		announce($TildeKeyword_0, grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_0());
	}
	(
		TildeKeyword_1_0=Tilde {
			announce($TildeKeyword_1_0, grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_1_0());
		}
		    |
		RULE_STRUCTMODSUFFIX
	)?
)
;

// Entry rule entryRuleTypingStrategyDefSiteOperator
entryRuleTypingStrategyDefSiteOperator
	:
	ruleTypingStrategyDefSiteOperator
	EOF;

// Rule TypingStrategyDefSiteOperator
ruleTypingStrategyDefSiteOperator
@init {
}
:
TildeKeyword=Tilde {
	announce($TildeKeyword, grammarAccess.getTypingStrategyDefSiteOperatorAccess().getTildeKeyword());
}
;

// Entry rule entryRuleTypeTypeRef
entryRuleTypeTypeRef
	:
	ruleTypeTypeRef
	EOF;

// Rule TypeTypeRef
ruleTypeTypeRef
@init {
}:
(
	(
		TypeKeyword_1_0=Type
		 {
			announce($TypeKeyword_1_0, grammarAccess.getTypeTypeRefAccess().getTypeKeyword_1_0());
		}
		    |
		(
			(
				ConstructorRefConstructorKeyword_1_1_0=Constructor
				 {
					announce($ConstructorRefConstructorKeyword_1_1_0, grammarAccess.getTypeTypeRefAccess().getConstructorRefConstructorKeyword_1_1_0());
				}
			)
		)
	)
	LeftCurlyBracketKeyword_2=LeftCurlyBracket
	 {
		announce($LeftCurlyBracketKeyword_2, grammarAccess.getTypeTypeRefAccess().getLeftCurlyBracketKeyword_2());
	}
	(
		(
			TypeArgTypeArgInTypeTypeRefParserRuleCall_3_0=ruleTypeArgInTypeTypeRef{
				announce($TypeArgTypeArgInTypeTypeRefParserRuleCall_3_0.start, $TypeArgTypeArgInTypeTypeRefParserRuleCall_3_0.stop, grammarAccess.getTypeTypeRefAccess().getTypeArgAssignment_3());
			}
		)
	)
	RightCurlyBracketKeyword_4=RightCurlyBracket
	 {
		announce($RightCurlyBracketKeyword_4, grammarAccess.getTypeTypeRefAccess().getRightCurlyBracketKeyword_4());
	}
)
;

// Entry rule entryRuleTypeArgument
entryRuleTypeArgument
	:
	ruleTypeArgument
	EOF;

// Rule TypeArgument
ruleTypeArgument
@init {
}:
(
	WildcardParserRuleCall_0=ruleWildcard{ announce($WildcardParserRuleCall_0.start, $WildcardParserRuleCall_0.stop, grammarAccess.getTypeArgumentAccess().getWildcardParserRuleCall_0()); }
	    |
	TypeRefParserRuleCall_1=ruleTypeRef{ announce($TypeRefParserRuleCall_1.start, $TypeRefParserRuleCall_1.stop, grammarAccess.getTypeArgumentAccess().getTypeRefParserRuleCall_1()); }
)
;

// Entry rule entryRuleWildcard
entryRuleWildcard
	:
	ruleWildcard
	EOF;

// Rule Wildcard
ruleWildcard
@init {
}:
(
	(
		((
			QuestionMark
		)
		)=>
		WildcardOldNotationParserRuleCall_0=ruleWildcardOldNotation{ announce($WildcardOldNotationParserRuleCall_0.start, $WildcardOldNotationParserRuleCall_0.stop, grammarAccess.getWildcardAccess().getWildcardOldNotationParserRuleCall_0()); }
	)
	    |
	WildcardNewNotationParserRuleCall_1=ruleWildcardNewNotation{ announce($WildcardNewNotationParserRuleCall_1.start, $WildcardNewNotationParserRuleCall_1.stop, grammarAccess.getWildcardAccess().getWildcardNewNotationParserRuleCall_1()); }
)
;

// Entry rule entryRuleWildcardOldNotation
entryRuleWildcardOldNotation
	:
	ruleWildcardOldNotation
	EOF;

// Rule WildcardOldNotation
ruleWildcardOldNotation
@init {
}:
(
	(
		((
			QuestionMark
		)
		)=>
		(
			QuestionMarkKeyword_0_0_1=QuestionMark
			 {
				announce($QuestionMarkKeyword_0_0_1, grammarAccess.getWildcardOldNotationAccess().getQuestionMarkKeyword_0_0_1());
			}
		)
	)
	(
		(
			ExtendsKeyword_1_0_0=Extends
			 {
				announce($ExtendsKeyword_1_0_0, grammarAccess.getWildcardOldNotationAccess().getExtendsKeyword_1_0_0());
			}
			(
				(
					DeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0=ruleTypeRef{
						announce($DeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0.start, $DeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0.stop, grammarAccess.getWildcardOldNotationAccess().getDeclaredUpperBoundAssignment_1_0_1());
					}
				)
			)
		)
		    |
		(
			SuperKeyword_1_1_0=Super
			 {
				announce($SuperKeyword_1_1_0, grammarAccess.getWildcardOldNotationAccess().getSuperKeyword_1_1_0());
			}
			(
				(
					DeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0=ruleTypeRef{
						announce($DeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0.start, $DeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0.stop, grammarAccess.getWildcardOldNotationAccess().getDeclaredLowerBoundAssignment_1_1_1());
					}
				)
			)
		)
	)?
)
;

// Entry rule entryRuleWildcardOldNotationWithoutBound
entryRuleWildcardOldNotationWithoutBound
	:
	ruleWildcardOldNotationWithoutBound
	EOF;

// Rule WildcardOldNotationWithoutBound
ruleWildcardOldNotationWithoutBound
@init {
}:
(
	QuestionMarkKeyword_1=QuestionMark
	 {
		announce($QuestionMarkKeyword_1, grammarAccess.getWildcardOldNotationWithoutBoundAccess().getQuestionMarkKeyword_1());
	}
)
;

// Entry rule entryRuleWildcardNewNotation
entryRuleWildcardNewNotation
	:
	ruleWildcardNewNotation
	EOF;

// Rule WildcardNewNotation
ruleWildcardNewNotation
@init {
}:
(
	(
		(
			(
				UsingInOutNotationOutKeyword_0_0_0=Out
				 {
					announce($UsingInOutNotationOutKeyword_0_0_0, grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationOutKeyword_0_0_0());
				}
			)
		)
		(
			(
				DeclaredUpperBoundTypeRefParserRuleCall_0_1_0=ruleTypeRef{
					announce($DeclaredUpperBoundTypeRefParserRuleCall_0_1_0.start, $DeclaredUpperBoundTypeRefParserRuleCall_0_1_0.stop, grammarAccess.getWildcardNewNotationAccess().getDeclaredUpperBoundAssignment_0_1());
				}
			)
		)
	)
	    |
	(
		(
			(
				UsingInOutNotationInKeyword_1_0_0=In
				 {
					announce($UsingInOutNotationInKeyword_1_0_0, grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationInKeyword_1_0_0());
				}
			)
		)
		(
			(
				DeclaredLowerBoundTypeRefParserRuleCall_1_1_0=ruleTypeRef{
					announce($DeclaredLowerBoundTypeRefParserRuleCall_1_1_0.start, $DeclaredLowerBoundTypeRefParserRuleCall_1_1_0.stop, grammarAccess.getWildcardNewNotationAccess().getDeclaredLowerBoundAssignment_1_1());
				}
			)
		)
	)
)
;

// Entry rule entryRuleBindingIdentifier
entryRuleBindingIdentifier
	:
	ruleBindingIdentifier
	EOF;

// Rule BindingIdentifier
ruleBindingIdentifier
@init {
}
:
(
	RULE_IDENTIFIER
	    |
	YieldKeyword_1_0=Yield {
		announce($YieldKeyword_1_0, grammarAccess.getBindingIdentifierAccess().getYieldKeyword_1_0());
	}
	    |
	ruleN4Keyword
)
;


// Rule BindingIdentifier
norm1_BindingIdentifier
@init {
}
:
(
	RULE_IDENTIFIER
	    |
	ruleN4Keyword
)
;

// Entry rule entryRuleIdentifierName
entryRuleIdentifierName
	:
	ruleIdentifierName
	EOF;

// Rule IdentifierName
ruleIdentifierName
@init {
}
:
(
	RULE_IDENTIFIER
	    |
	ruleReservedWord
	    |
	ruleN4Keyword
)
;

// Entry rule entryRuleReservedWord
entryRuleReservedWord
	:
	ruleReservedWord
	EOF;

// Rule ReservedWord
ruleReservedWord
@init {
}
:
(
	BreakKeyword_0=Break {
		announce($BreakKeyword_0, grammarAccess.getReservedWordAccess().getBreakKeyword_0());
	}
	    |
	CaseKeyword_1=Case {
		announce($CaseKeyword_1, grammarAccess.getReservedWordAccess().getCaseKeyword_1());
	}
	    |
	CatchKeyword_2=Catch {
		announce($CatchKeyword_2, grammarAccess.getReservedWordAccess().getCatchKeyword_2());
	}
	    |
	ClassKeyword_3=Class {
		announce($ClassKeyword_3, grammarAccess.getReservedWordAccess().getClassKeyword_3());
	}
	    |
	ConstKeyword_4=Const {
		announce($ConstKeyword_4, grammarAccess.getReservedWordAccess().getConstKeyword_4());
	}
	    |
	ContinueKeyword_5=Continue {
		announce($ContinueKeyword_5, grammarAccess.getReservedWordAccess().getContinueKeyword_5());
	}
	    |
	DebuggerKeyword_6=Debugger {
		announce($DebuggerKeyword_6, grammarAccess.getReservedWordAccess().getDebuggerKeyword_6());
	}
	    |
	DefaultKeyword_7=Default {
		announce($DefaultKeyword_7, grammarAccess.getReservedWordAccess().getDefaultKeyword_7());
	}
	    |
	DeleteKeyword_8=Delete {
		announce($DeleteKeyword_8, grammarAccess.getReservedWordAccess().getDeleteKeyword_8());
	}
	    |
	DoKeyword_9=Do {
		announce($DoKeyword_9, grammarAccess.getReservedWordAccess().getDoKeyword_9());
	}
	    |
	ElseKeyword_10=Else {
		announce($ElseKeyword_10, grammarAccess.getReservedWordAccess().getElseKeyword_10());
	}
	    |
	ExportKeyword_11=Export {
		announce($ExportKeyword_11, grammarAccess.getReservedWordAccess().getExportKeyword_11());
	}
	    |
	ExtendsKeyword_12=Extends {
		announce($ExtendsKeyword_12, grammarAccess.getReservedWordAccess().getExtendsKeyword_12());
	}
	    |
	FinallyKeyword_13=Finally {
		announce($FinallyKeyword_13, grammarAccess.getReservedWordAccess().getFinallyKeyword_13());
	}
	    |
	ForKeyword_14=For {
		announce($ForKeyword_14, grammarAccess.getReservedWordAccess().getForKeyword_14());
	}
	    |
	FunctionKeyword_15=Function {
		announce($FunctionKeyword_15, grammarAccess.getReservedWordAccess().getFunctionKeyword_15());
	}
	    |
	IfKeyword_16=If {
		announce($IfKeyword_16, grammarAccess.getReservedWordAccess().getIfKeyword_16());
	}
	    |
	ImportKeyword_17=Import {
		announce($ImportKeyword_17, grammarAccess.getReservedWordAccess().getImportKeyword_17());
	}
	    |
	InKeyword_18=In {
		announce($InKeyword_18, grammarAccess.getReservedWordAccess().getInKeyword_18());
	}
	    |
	InstanceofKeyword_19=Instanceof {
		announce($InstanceofKeyword_19, grammarAccess.getReservedWordAccess().getInstanceofKeyword_19());
	}
	    |
	NewKeyword_20=New {
		announce($NewKeyword_20, grammarAccess.getReservedWordAccess().getNewKeyword_20());
	}
	    |
	ReturnKeyword_21=Return {
		announce($ReturnKeyword_21, grammarAccess.getReservedWordAccess().getReturnKeyword_21());
	}
	    |
	SuperKeyword_22=Super {
		announce($SuperKeyword_22, grammarAccess.getReservedWordAccess().getSuperKeyword_22());
	}
	    |
	SwitchKeyword_23=Switch {
		announce($SwitchKeyword_23, grammarAccess.getReservedWordAccess().getSwitchKeyword_23());
	}
	    |
	ThisKeyword_24=This_1 {
		announce($ThisKeyword_24, grammarAccess.getReservedWordAccess().getThisKeyword_24());
	}
	    |
	ThrowKeyword_25=Throw {
		announce($ThrowKeyword_25, grammarAccess.getReservedWordAccess().getThrowKeyword_25());
	}
	    |
	TryKeyword_26=Try {
		announce($TryKeyword_26, grammarAccess.getReservedWordAccess().getTryKeyword_26());
	}
	    |
	TypeofKeyword_27=Typeof {
		announce($TypeofKeyword_27, grammarAccess.getReservedWordAccess().getTypeofKeyword_27());
	}
	    |
	VarKeyword_28=Var {
		announce($VarKeyword_28, grammarAccess.getReservedWordAccess().getVarKeyword_28());
	}
	    |
	VoidKeyword_29=Void {
		announce($VoidKeyword_29, grammarAccess.getReservedWordAccess().getVoidKeyword_29());
	}
	    |
	WhileKeyword_30=While {
		announce($WhileKeyword_30, grammarAccess.getReservedWordAccess().getWhileKeyword_30());
	}
	    |
	WithKeyword_31=With {
		announce($WithKeyword_31, grammarAccess.getReservedWordAccess().getWithKeyword_31());
	}
	    |
	YieldKeyword_32=Yield {
		announce($YieldKeyword_32, grammarAccess.getReservedWordAccess().getYieldKeyword_32());
	}
	    |
	NullKeyword_33=Null {
		announce($NullKeyword_33, grammarAccess.getReservedWordAccess().getNullKeyword_33());
	}
	    |
	TrueKeyword_34=True {
		announce($TrueKeyword_34, grammarAccess.getReservedWordAccess().getTrueKeyword_34());
	}
	    |
	FalseKeyword_35=False {
		announce($FalseKeyword_35, grammarAccess.getReservedWordAccess().getFalseKeyword_35());
	}
	    |
	EnumKeyword_36=Enum {
		announce($EnumKeyword_36, grammarAccess.getReservedWordAccess().getEnumKeyword_36());
	}
)
;

// Entry rule entryRuleN4Keyword
entryRuleN4Keyword
	:
	ruleN4Keyword
	EOF;

// Rule N4Keyword
ruleN4Keyword
@init {
}
:
(
	GetKeyword_0=Get {
		announce($GetKeyword_0, grammarAccess.getN4KeywordAccess().getGetKeyword_0());
	}
	    |
	SetKeyword_1=Set {
		announce($SetKeyword_1, grammarAccess.getN4KeywordAccess().getSetKeyword_1());
	}
	    |
	LetKeyword_2=Let {
		announce($LetKeyword_2, grammarAccess.getN4KeywordAccess().getLetKeyword_2());
	}
	    |
	ProjectKeyword_3=Project {
		announce($ProjectKeyword_3, grammarAccess.getN4KeywordAccess().getProjectKeyword_3());
	}
	    |
	ExternalKeyword_4=External {
		announce($ExternalKeyword_4, grammarAccess.getN4KeywordAccess().getExternalKeyword_4());
	}
	    |
	AbstractKeyword_5=Abstract {
		announce($AbstractKeyword_5, grammarAccess.getN4KeywordAccess().getAbstractKeyword_5());
	}
	    |
	StaticKeyword_6=Static {
		announce($StaticKeyword_6, grammarAccess.getN4KeywordAccess().getStaticKeyword_6());
	}
	    |
	AsKeyword_7=As {
		announce($AsKeyword_7, grammarAccess.getN4KeywordAccess().getAsKeyword_7());
	}
	    |
	FromKeyword_8=From {
		announce($FromKeyword_8, grammarAccess.getN4KeywordAccess().getFromKeyword_8());
	}
	    |
	ConstructorKeyword_9=Constructor {
		announce($ConstructorKeyword_9, grammarAccess.getN4KeywordAccess().getConstructorKeyword_9());
	}
	    |
	OfKeyword_10=Of {
		announce($OfKeyword_10, grammarAccess.getN4KeywordAccess().getOfKeyword_10());
	}
	    |
	TargetKeyword_11=Target {
		announce($TargetKeyword_11, grammarAccess.getN4KeywordAccess().getTargetKeyword_11());
	}
	    |
	TypeKeyword_12=Type {
		announce($TypeKeyword_12, grammarAccess.getN4KeywordAccess().getTypeKeyword_12());
	}
	    |
	UnionKeyword_13=Union {
		announce($UnionKeyword_13, grammarAccess.getN4KeywordAccess().getUnionKeyword_13());
	}
	    |
	IntersectionKeyword_14=Intersection {
		announce($IntersectionKeyword_14, grammarAccess.getN4KeywordAccess().getIntersectionKeyword_14());
	}
	    |
	ThisKeyword_15=This {
		announce($ThisKeyword_15, grammarAccess.getN4KeywordAccess().getThisKeyword_15());
	}
	    |
	PromisifyKeyword_16=Promisify {
		announce($PromisifyKeyword_16, grammarAccess.getN4KeywordAccess().getPromisifyKeyword_16());
	}
	    |
	AwaitKeyword_17=Await {
		announce($AwaitKeyword_17, grammarAccess.getN4KeywordAccess().getAwaitKeyword_17());
	}
	    |
	AsyncKeyword_18=Async {
		announce($AsyncKeyword_18, grammarAccess.getN4KeywordAccess().getAsyncKeyword_18());
	}
	    |
	ImplementsKeyword_19=Implements {
		announce($ImplementsKeyword_19, grammarAccess.getN4KeywordAccess().getImplementsKeyword_19());
	}
	    |
	InterfaceKeyword_20=Interface {
		announce($InterfaceKeyword_20, grammarAccess.getN4KeywordAccess().getInterfaceKeyword_20());
	}
	    |
	PrivateKeyword_21=Private {
		announce($PrivateKeyword_21, grammarAccess.getN4KeywordAccess().getPrivateKeyword_21());
	}
	    |
	ProtectedKeyword_22=Protected {
		announce($ProtectedKeyword_22, grammarAccess.getN4KeywordAccess().getProtectedKeyword_22());
	}
	    |
	PublicKeyword_23=Public {
		announce($PublicKeyword_23, grammarAccess.getN4KeywordAccess().getPublicKeyword_23());
	}
	    |
	OutKeyword_24=Out {
		announce($OutKeyword_24, grammarAccess.getN4KeywordAccess().getOutKeyword_24());
	}
)
;

// Rule VariableStatementKeyword
ruleVariableStatementKeyword
:
(
	(
		VarVarKeyword_0_0=Var {
			announce($VarVarKeyword_0_0, grammarAccess.getVariableStatementKeywordAccess().getVarEnumLiteralDeclaration_0());
		}
	)
	    |
	(
		ConstConstKeyword_1_0=Const {
			announce($ConstConstKeyword_1_0, grammarAccess.getVariableStatementKeywordAccess().getConstEnumLiteralDeclaration_1());
		}
	)
	    |
	(
		LetLetKeyword_2_0=Let {
			announce($LetLetKeyword_2_0, grammarAccess.getVariableStatementKeywordAccess().getLetEnumLiteralDeclaration_2());
		}
	)
)
;

// Rule PostfixOperator
rulePostfixOperator
:
(
	(
		IncPlusSignPlusSignKeyword_0_0=PlusSignPlusSign {
			announce($IncPlusSignPlusSignKeyword_0_0, grammarAccess.getPostfixOperatorAccess().getIncEnumLiteralDeclaration_0());
		}
	)
	    |
	(
		DecHyphenMinusHyphenMinusKeyword_1_0=HyphenMinusHyphenMinus {
			announce($DecHyphenMinusHyphenMinusKeyword_1_0, grammarAccess.getPostfixOperatorAccess().getDecEnumLiteralDeclaration_1());
		}
	)
)
;

// Rule UnaryOperator
ruleUnaryOperator
:
(
	(
		DeleteDeleteKeyword_0_0=Delete {
			announce($DeleteDeleteKeyword_0_0, grammarAccess.getUnaryOperatorAccess().getDeleteEnumLiteralDeclaration_0());
		}
	)
	    |
	(
		VoidVoidKeyword_1_0=Void {
			announce($VoidVoidKeyword_1_0, grammarAccess.getUnaryOperatorAccess().getVoidEnumLiteralDeclaration_1());
		}
	)
	    |
	(
		TypeofTypeofKeyword_2_0=Typeof {
			announce($TypeofTypeofKeyword_2_0, grammarAccess.getUnaryOperatorAccess().getTypeofEnumLiteralDeclaration_2());
		}
	)
	    |
	(
		IncPlusSignPlusSignKeyword_3_0=PlusSignPlusSign {
			announce($IncPlusSignPlusSignKeyword_3_0, grammarAccess.getUnaryOperatorAccess().getIncEnumLiteralDeclaration_3());
		}
	)
	    |
	(
		DecHyphenMinusHyphenMinusKeyword_4_0=HyphenMinusHyphenMinus {
			announce($DecHyphenMinusHyphenMinusKeyword_4_0, grammarAccess.getUnaryOperatorAccess().getDecEnumLiteralDeclaration_4());
		}
	)
	    |
	(
		PosPlusSignKeyword_5_0=PlusSign {
			announce($PosPlusSignKeyword_5_0, grammarAccess.getUnaryOperatorAccess().getPosEnumLiteralDeclaration_5());
		}
	)
	    |
	(
		NegHyphenMinusKeyword_6_0=HyphenMinus {
			announce($NegHyphenMinusKeyword_6_0, grammarAccess.getUnaryOperatorAccess().getNegEnumLiteralDeclaration_6());
		}
	)
	    |
	(
		InvTildeKeyword_7_0=Tilde {
			announce($InvTildeKeyword_7_0, grammarAccess.getUnaryOperatorAccess().getInvEnumLiteralDeclaration_7());
		}
	)
	    |
	(
		NotExclamationMarkKeyword_8_0=ExclamationMark {
			announce($NotExclamationMarkKeyword_8_0, grammarAccess.getUnaryOperatorAccess().getNotEnumLiteralDeclaration_8());
		}
	)
)
;

// Rule MultiplicativeOperator
ruleMultiplicativeOperator
:
(
	(
		TimesAsteriskKeyword_0_0=Asterisk {
			announce($TimesAsteriskKeyword_0_0, grammarAccess.getMultiplicativeOperatorAccess().getTimesEnumLiteralDeclaration_0());
		}
	)
	    |
	(
		DivSolidusKeyword_1_0=Solidus {
			announce($DivSolidusKeyword_1_0, grammarAccess.getMultiplicativeOperatorAccess().getDivEnumLiteralDeclaration_1());
		}
	)
	    |
	(
		ModPercentSignKeyword_2_0=PercentSign {
			announce($ModPercentSignKeyword_2_0, grammarAccess.getMultiplicativeOperatorAccess().getModEnumLiteralDeclaration_2());
		}
	)
)
;

// Rule AdditiveOperator
ruleAdditiveOperator
:
(
	(
		AddPlusSignKeyword_0_0=PlusSign {
			announce($AddPlusSignKeyword_0_0, grammarAccess.getAdditiveOperatorAccess().getAddEnumLiteralDeclaration_0());
		}
	)
	    |
	(
		SubHyphenMinusKeyword_1_0=HyphenMinus {
			announce($SubHyphenMinusKeyword_1_0, grammarAccess.getAdditiveOperatorAccess().getSubEnumLiteralDeclaration_1());
		}
	)
)
;

// Rule EqualityOperator
ruleEqualityOperator
:
(
	(
		SameEqualsSignEqualsSignEqualsSignKeyword_0_0=EqualsSignEqualsSignEqualsSign {
			announce($SameEqualsSignEqualsSignEqualsSignKeyword_0_0, grammarAccess.getEqualityOperatorAccess().getSameEnumLiteralDeclaration_0());
		}
	)
	    |
	(
		NsameExclamationMarkEqualsSignEqualsSignKeyword_1_0=ExclamationMarkEqualsSignEqualsSign {
			announce($NsameExclamationMarkEqualsSignEqualsSignKeyword_1_0, grammarAccess.getEqualityOperatorAccess().getNsameEnumLiteralDeclaration_1());
		}
	)
	    |
	(
		EqEqualsSignEqualsSignKeyword_2_0=EqualsSignEqualsSign {
			announce($EqEqualsSignEqualsSignKeyword_2_0, grammarAccess.getEqualityOperatorAccess().getEqEnumLiteralDeclaration_2());
		}
	)
	    |
	(
		NeqExclamationMarkEqualsSignKeyword_3_0=ExclamationMarkEqualsSign {
			announce($NeqExclamationMarkEqualsSignKeyword_3_0, grammarAccess.getEqualityOperatorAccess().getNeqEnumLiteralDeclaration_3());
		}
	)
)
;

// Rule N4Modifier
ruleN4Modifier
:
(
	(
		PrivatePrivateKeyword_0_0=Private {
			announce($PrivatePrivateKeyword_0_0, grammarAccess.getN4ModifierAccess().getPrivateEnumLiteralDeclaration_0());
		}
	)
	    |
	(
		ProjectProjectKeyword_1_0=Project {
			announce($ProjectProjectKeyword_1_0, grammarAccess.getN4ModifierAccess().getProjectEnumLiteralDeclaration_1());
		}
	)
	    |
	(
		ProtectedProtectedKeyword_2_0=Protected {
			announce($ProtectedProtectedKeyword_2_0, grammarAccess.getN4ModifierAccess().getProtectedEnumLiteralDeclaration_2());
		}
	)
	    |
	(
		PublicPublicKeyword_3_0=Public {
			announce($PublicPublicKeyword_3_0, grammarAccess.getN4ModifierAccess().getPublicEnumLiteralDeclaration_3());
		}
	)
	    |
	(
		ExternalExternalKeyword_4_0=External {
			announce($ExternalExternalKeyword_4_0, grammarAccess.getN4ModifierAccess().getExternalEnumLiteralDeclaration_4());
		}
	)
	    |
	(
		AbstractAbstractKeyword_5_0=Abstract {
			announce($AbstractAbstractKeyword_5_0, grammarAccess.getN4ModifierAccess().getAbstractEnumLiteralDeclaration_5());
		}
	)
	    |
	(
		StaticStaticKeyword_6_0=Static {
			announce($StaticStaticKeyword_6_0, grammarAccess.getN4ModifierAccess().getStaticEnumLiteralDeclaration_6());
		}
	)
	    |
	(
		ConstConstKeyword_7_0=Const {
			announce($ConstConstKeyword_7_0, grammarAccess.getN4ModifierAccess().getConstEnumLiteralDeclaration_7());
		}
	)
)
;
